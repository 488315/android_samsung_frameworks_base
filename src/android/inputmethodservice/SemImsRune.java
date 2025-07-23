package android.inputmethodservice;

/* loaded from: classes2.dex */
final class SemImsRune {
    static final boolean disableRenderGesturalNavButtons = true;
    static final boolean enableCtsWorkaround = true;
    static final boolean supportPreferredMinDisplayRefreshRate;

    SemImsRune() {
    }

    static {
        supportPreferredMinDisplayRefreshRate = Integer.parseInt("3") != 4;
    }
}
