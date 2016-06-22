Introduction
------------
Test runner for JUnit 4 that performs or ignores test cases depending on the current Operation System.

Usage
-----
Usage is pretty simple. Just add the OSDependentJUnitRunner to your test class and the DependsOnPlatform annotation to
your test method.


    @RunWith(OSDependentJUnitRunner.class)
    public class TestClass {
    
        @Test
        @DependsOnPlatform(LINUX)
        public void testMethod() {
            assertThat(System.getProperty("os.name")).isEqualToIgnoringCase("linux");
        }
    }
