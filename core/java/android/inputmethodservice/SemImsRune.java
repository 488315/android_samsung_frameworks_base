package android.inputmethodservice;

final class SemImsRune {
    static final boolean disableRenderGesturalNavButtons = true;
    static final boolean enableCtsWorkaround = true;
    static final boolean supportPreferredMinDisplayRefreshRate;

    SemImsRune() {}

    static {
        supportPreferredMinDisplayRefreshRate = Integer.parseInt("3") != 4;
    }
}
