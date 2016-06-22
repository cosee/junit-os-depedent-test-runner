package biz.cosee.junit.osdependent;

/**
 * Contains the supported platform types.
 */
public enum Platform {

    LINUX("linux"),

    OTHER,

    UNIX("mac os"),

    WINDOWS("windows");

    /**
     * Name to match with Java OS string returned by System.getProperty("os.name").
     */
    final String javaOsNameExpression;

    Platform() {
        javaOsNameExpression = null;
    }

    Platform(String javaOsNameExpression) {
        this.javaOsNameExpression = javaOsNameExpression;
    }
}
