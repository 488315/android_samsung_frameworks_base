package androidx.window;

import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.window.core.ExtensionsUtil;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class WindowSdkExtensions {
    public static final Companion Companion = new Companion(null);
    public static final EmptyDecoratorWindowSdk decorator = EmptyDecoratorWindowSdk.INSTANCE;
    public final int extensionVersion;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Type inference failed for: r1v0, types: [androidx.window.WindowSdkExtensions$Companion$getInstance$1] */
        public static WindowSdkExtensions$Companion$getInstance$1 getInstance() {
            EmptyDecoratorWindowSdk emptyDecoratorWindowSdk = WindowSdkExtensions.decorator;
            ?? r1 = new WindowSdkExtensions() { // from class: androidx.window.WindowSdkExtensions$Companion$getInstance$1
            };
            emptyDecoratorWindowSdk.getClass();
            return r1;
        }

        private Companion() {
        }
    }

    public WindowSdkExtensions() {
        ExtensionsUtil.INSTANCE.getClass();
        this.extensionVersion = ExtensionsUtil.getSafeVendorApiLevel();
    }

    public final void requireExtensionVersion$window_release(int i) {
        int i2 = this.extensionVersion;
        if (i2 < i) {
            throw new UnsupportedOperationException(ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "This API requires extension version ", ", but the device is on "));
        }
    }
}
