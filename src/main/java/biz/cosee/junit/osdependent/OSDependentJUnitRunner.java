package biz.cosee.junit.osdependent;

import org.junit.AssumptionViolatedException;
import org.junit.Ignore;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class OSDependentJUnitRunner extends BlockJUnit4ClassRunner {

    private static Platform thisMachinesPlatform;

    static {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.toLowerCase().contains("linux")) {
            thisMachinesPlatform = Platform.LINUX;
        } else {
            thisMachinesPlatform = Platform.OTHER;
        }
    }

    public OSDependentJUnitRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        EachTestNotifier eachNotifier = makeNotifier(method, notifier);
        if (method.getAnnotation(Ignore.class) != null) {
            runIgnored(eachNotifier);
        } else {
            DependsOnPlatform dependsOnPlatform = method.getAnnotation(DependsOnPlatform.class);
            if (dependsOnPlatform != null && dependsOnPlatform.value() != thisMachinesPlatform) {
                runIgnored(eachNotifier);
                return;
            }
            runNotIgnored(method, eachNotifier);
        }
    }

    // copy of BlockJUnit4ClassRunner#runNotIgnored
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

    // copy of BlockJUnit4ClassRunner#runIgnored
    private void runIgnored(EachTestNotifier eachNotifier) {
        eachNotifier.fireTestIgnored();
    }

    // copy of BlockJUnit4ClassRunner#makeNotifier
    private EachTestNotifier makeNotifier(FrameworkMethod method, RunNotifier notifier) {
        Description description = describeChild(method);
        return new EachTestNotifier(notifier, description);
    }
}
