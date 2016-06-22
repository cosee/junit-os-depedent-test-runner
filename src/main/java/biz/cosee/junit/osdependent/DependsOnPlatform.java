package biz.cosee.junit.osdependent;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Add DependsOnPlatform annotation to your platform dependent test methods.
 */
@Retention(RUNTIME)
public @interface DependsOnPlatform {
    Platform value();
}