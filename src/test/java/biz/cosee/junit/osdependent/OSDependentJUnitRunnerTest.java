package biz.cosee.junit.osdependent;

import org.junit.Test;
import org.junit.runner.RunWith;

import static biz.cosee.junit.osdependent.Platform.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(OSDependentJUnitRunner.class)
public class OSDependentJUnitRunnerTest {

    private String osName = System.getProperty("os.name").toLowerCase();

    @Test
    @DependsOnPlatform(LINUX)
    public void assertThatPlatformIsLinux() {
        assertThat(osName).contains("linux");
    }

    @Test
    @DependsOnPlatform(WINDOWS)
    public void assertThatPlatformIsWindows() {
        assertThat(osName).contains("windows");
    }

    @Test
    @DependsOnPlatform(UNIX)
    public void assertThatPlatformIsUnix() {
        assertThat(osName).contains("mac os");
    }

    @Test
    @DependsOnPlatform(OTHER)
    public void assertThatPlatformIsOther() {
        assertThat(osName).doesNotContain("linux");
        assertThat(osName).doesNotContain("windows");
        assertThat(osName).doesNotContain("mac os");
    }
}