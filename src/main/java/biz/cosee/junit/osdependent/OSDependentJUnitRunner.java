package biz.cosee.junit.osdependent;

import org.junit.AssumptionViolatedException;
import org.junit.Ignore;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * OSDependentJUnitRunner determines the Operation System on load time of the class and performs all test methods
 * having whether no {@link Platform} annotation present or a matching operation system.
 */
public class OSDependentJUnitRunner extends BlockJUnit4ClassRunner {

    private static Platform currentPlatform = determineCurrentPlatform();

    public OSDependentJUnitRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    private static Platform determineCurrentPlatform() {
        String osNameInLowerCase = System.getProperty("os.name").toLowerCase();

        for (Platform platform : Platform.values()) {
            if ((platform.javaOsNameExpression != null) && (osNameInLowerCase.contains(platform.javaOsNameExpression))) {
                return platform;
            }
        }
        return Platform.OTHER;
    }

    @Override
    public void run(RunNotifier notifier) {
        notifier.addListener(new NotMatchingOSMessagePrinter(currentPlatform));
        super.run(notifier);
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        EachTestNotifier eachNotifier = makeNotifier(method, notifier);
        if (method.getAnnotation(Ignore.class) != null) {
            runIgnored(eachNotifier);
        } else {
            DependsOnPlatform dependsOnPlatform = method.getAnnotation(DependsOnPlatform.class);
            if ((dependsOnPlatform != null) && (dependsOnPlatform.value() != currentPlatform)) {
                runIgnored(eachNotifier);
                return;
            }
            runNotIgnored(method, eachNotifier);
        }
    }

    private void runNotIgnored(FrameworkMethod method, EachTestNotifier eachNotifier) {
        eachNotifier.fireTestStarted();
        try {
            methodBlock(method).evaluate();
        } catch (AssumptionViolatedException e) {
            eachNotifier.addFailedAssumption(e);
        } catch (Throwable e) {
            eachNotifier.addFailure(e);
        } finally {
            eachNotifier.fireTestFinished();
        }
    }

    private void runIgnored(EachTestNotifier eachNotifier) {
        eachNotifier.fireTestIgnored();
    }

    private EachTestNotifier makeNotifier(FrameworkMethod method, RunNotifier notifier) {
        Description description = describeChild(method);
        return new EachTestNotifier(notifier, description);
    }
}
