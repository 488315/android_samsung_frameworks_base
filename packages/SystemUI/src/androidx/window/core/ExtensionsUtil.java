package androidx.window.core;

import android.util.Log;
import androidx.window.extensions.WindowExtensionsProvider;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ExtensionsUtil {
    public static final ExtensionsUtil INSTANCE = new ExtensionsUtil();
    public static final String TAG = Reflection.getOrCreateKotlinClass(ExtensionsUtil.class).getSimpleName();

    private ExtensionsUtil() {
    }

    public static int getSafeVendorApiLevel() {
        String str = TAG;
        try {
            return WindowExtensionsProvider.getWindowExtensions().getVendorApiLevel();
        } catch (NoClassDefFoundError unused) {
            BuildConfig.INSTANCE.getClass();
            if (BuildConfig.verificationMode != VerificationMode.LOG) {
                return 0;
            }
            Log.d(str, "Embedding extension version not found");
            return 0;
        } catch (UnsupportedOperationException unused2) {
            BuildConfig.INSTANCE.getClass();
            if (BuildConfig.verificationMode != VerificationMode.LOG) {
                return 0;
            }
            Log.d(str, "Stub Extension");
            return 0;
        }
    }
}
