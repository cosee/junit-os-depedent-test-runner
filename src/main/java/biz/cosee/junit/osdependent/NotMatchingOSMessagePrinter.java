package biz.cosee.junit.osdependent;

import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;

/**
 * A JUnit {@link RunListener} that prints a description for all ignored test methods (because of not matching
 * Operating System).
 */
class NotMatchingOSMessagePrinter extends RunListener {

    private final Platform currentPlatform;

    public NotMatchingOSMessagePrinter(Platform currentPlatform) {
        this.currentPlatform = currentPlatform;
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        super.testIgnored(description);
        DependsOnPlatform dependsOnPlatform = description.getAnnotation(DependsOnPlatform.class);
        if (dependsOnPlatform != null) {
            String ignoreMessage = String.format(
                    "Ignored test method '%s': expected Operating System '%s', current Operating System '%s'.",
                    description.getMethodName(), dependsOnPlatform.value(), currentPlatform);

            System.out.println(ignoreMessage);
        }
    }
}
