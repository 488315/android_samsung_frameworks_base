package androidx.core.os;

import android.os.ext.SdkExtensions;

/* loaded from: classes.dex */
public final class BuildCompat {
    public static final /* synthetic */ int $r8$clinit = 0;

    public final class Api30Impl {
        public static final Api30Impl INSTANCE = new Api30Impl();

        private Api30Impl() {
        }
    }

    static {
        new BuildCompat();
        Api30Impl.INSTANCE.getClass();
        SdkExtensions.getExtensionVersion(30);
        SdkExtensions.getExtensionVersion(31);
        SdkExtensions.getExtensionVersion(33);
        SdkExtensions.getExtensionVersion(1000000);
    }

    private BuildCompat() {
    }
}
