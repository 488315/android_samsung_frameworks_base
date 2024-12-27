package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.PromptInfo;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.biometrics.shared.model.PromptKind;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Utils {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new Utils();
    }

    private Utils() {
    }

    public static final SensorPropertiesInternal findFirstSensorProperties(List list, int[] iArr) {
        Object obj = null;
        if (list == null) {
            return null;
        }
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            if (ArraysKt___ArraysKt.contains(((SensorPropertiesInternal) next).sensorId, iArr)) {
                obj = next;
                break;
            }
        }
        return (SensorPropertiesInternal) obj;
    }

    public static final PromptKind getCredentialType(LockPatternUtils lockPatternUtils, int i) {
        int keyguardStoredPasswordQuality = lockPatternUtils.getKeyguardStoredPasswordQuality(i);
        return keyguardStoredPasswordQuality != 65536 ? (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) ? PromptKind.Pin.INSTANCE : (keyguardStoredPasswordQuality == 262144 || keyguardStoredPasswordQuality == 327680 || keyguardStoredPasswordQuality == 393216 || keyguardStoredPasswordQuality == 524288) ? PromptKind.Password.INSTANCE : PromptKind.Password.INSTANCE : PromptKind.Pattern.INSTANCE;
    }

    public static final Insets getNavbarInsets(Context context) {
        WindowInsets windowInsets;
        WindowManager windowManager = (WindowManager) context.getSystemService(WindowManager.class);
        Insets insets = null;
        WindowMetrics maximumWindowMetrics = windowManager != null ? windowManager.getMaximumWindowMetrics() : null;
        if (maximumWindowMetrics != null && (windowInsets = maximumWindowMetrics.getWindowInsets()) != null) {
            insets = windowInsets.getInsets(WindowInsets.Type.navigationBars());
        }
        return insets == null ? Insets.NONE : insets;
    }

    public static final boolean isDeviceCredentialAllowed(PromptInfo promptInfo) {
        return (promptInfo.getAuthenticators() & 32768) != 0;
    }

    public static final Bitmap toBitmap(Drawable drawable) {
        Bitmap createBitmap;
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            createBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNull(createBitmap);
        } else {
            createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNull(createBitmap);
        }
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }
}
