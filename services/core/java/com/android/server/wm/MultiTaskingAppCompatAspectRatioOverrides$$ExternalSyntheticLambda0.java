package com.android.server.wm;

import android.app.AppGlobals;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class MultiTaskingAppCompatAspectRatioOverrides$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ MultiTaskingAppCompatAspectRatioOverrides f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ MultiTaskingAppCompatAspectRatioOverrides$$ExternalSyntheticLambda0(MultiTaskingAppCompatAspectRatioOverrides multiTaskingAppCompatAspectRatioOverrides, int i) {
        this.f$0 = multiTaskingAppCompatAspectRatioOverrides;
        this.f$1 = i;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        MultiTaskingAppCompatAspectRatioOverrides multiTaskingAppCompatAspectRatioOverrides = this.f$0;
        int i = this.f$1;
        String str = (String) obj;
        Float f = (Float) obj2;
        multiTaskingAppCompatAspectRatioOverrides.getClass();
        float floatValue = f.floatValue();
        if (floatValue != -1.0f && floatValue != FullScreenMagnificationGestureHandler.MAX_SCALE && floatValue != 1.7777778f && floatValue != 1.3333334f) {
            Slog.d("MultiTaskingAppCompat", "setUserMinAspectRatio:" + f + " is unknown min aspect ratio.");
            f = Float.valueOf(1.7777778f);
        }
        try {
            AppGlobals.getPackageManager().setUserMinAspectRatio(str, i, f.floatValue() == 1.7777778f ? 4 : f.floatValue() == 1.3333334f ? 3 : 7);
        } catch (RemoteException | IllegalArgumentException | SecurityException e) {
            Slog.w("MultiTaskingAppCompat", SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i, "Unable to set user min aspect ratio override. packageName=", str, ", userId="), e);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
