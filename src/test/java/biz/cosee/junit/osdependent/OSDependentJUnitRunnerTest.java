package biz.cosee.junit.osdependent;

import org.junit.Test;
import org.junit.runner.RunWith;

import static biz.cosee.junit.osdependent.Platform.LINUX;
import static biz.cosee.junit.osdependent.Platform.OTHER;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(OSDependentJUnitRunner.class)
public class OSDependentJUnitRunnerTest {

    @Test
    @DependsOnPlatform(LINUX)
    public void assertThatPlatformIsLinux() {
        assertThat(System.getProperty("os.name")).isEqualToIgnoringCase("linux");
    }

    @Test
    @DependsOnPlatform(OTHER)
    public void assertThatPlatformIsNotLinux() {
        assertThat(System.getProperty("os.name").toLowerCase()).isNotEqualTo("linux");
    }
}