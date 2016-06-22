package biz.cosee.junit.osdependent;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface DependsOnPlatform {
    Platform platform();
}