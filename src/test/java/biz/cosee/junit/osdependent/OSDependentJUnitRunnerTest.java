package biz.cosee.junit.osdependent;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(OSDependentJUnitRunner.class)
public class OSDependentJUnitRunnerTest {

    @Test
    @DependsOnPlatform(platform = Platform.LINUX)
    public void assertThatPlatformIsLinux() {
        assertThat(System.getProperty("os.name")).isEqualToIgnoringCase("linux");
    }

    @Test
    @DependsOnPlatform(platform = Platform.OTHER)
    public void assertThatPlatformIsNotLinux() {
        assertThat(System.getProperty("os.name").toLowerCase()).isNotEqualTo("linux");
    }
}