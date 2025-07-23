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
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.biometrics.shared.model.PromptKind;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Utils {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new Utils();
    }

    private Utils() {
    }

    public static final String ellipsize(int i, String str) {
        return str.length() <= i ? str : StringsKt__StringsKt.replaceRange(str, i, str.length(), "...").toString();
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
        int credentialTypeForUser = lockPatternUtils.getCredentialTypeForUser(i);
        return credentialTypeForUser != 1 ? credentialTypeForUser != 3 ? credentialTypeForUser != 4 ? PromptKind.Password.INSTANCE : PromptKind.Password.INSTANCE : PromptKind.Pin.INSTANCE : PromptKind.Pattern.INSTANCE;
    }

    public static final Insets getNavbarInsets(Context context) {
        return WindowManagerUtils.getWindowManager(context).getMaximumWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.navigationBars());
    }

    public static final boolean isDeviceCredentialAllowed(PromptInfo promptInfo) {
        return (promptInfo.getAuthenticators() & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0;
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
            createBitmap.getClass();
        } else {
            createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            createBitmap.getClass();
        }
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }
}
