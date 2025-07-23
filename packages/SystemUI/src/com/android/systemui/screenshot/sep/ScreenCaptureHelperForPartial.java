package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Slog;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenCaptureHelperForPartial extends ScreenCaptureHelper {
    public static final String TAG;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TAG = "ScreenCaptureHelperForPartial";
    }

    @Override // com.android.systemui.screenshot.sep.ScreenCaptureHelper
    public final void initializeCaptureType() {
        this.screenCaptureType = 2;
    }

    @Override // com.android.systemui.screenshot.sep.ScreenCaptureHelper
    public final boolean isShowScreenshotAnimation(Context context) {
        return false;
    }

    @Override // com.android.systemui.screenshot.sep.ScreenCaptureHelper
    public final Bitmap onPostScreenshot(Bitmap bitmap) {
        Bundle bundle = this.mBundle;
        bundle.getClass();
        Rect rect = (Rect) bundle.getParcelable("rect");
        Slog.d(TAG, "rect : " + rect + " bitmap.getWidth() : " + bitmap.getWidth() + " bitmap.getHeight() : " + bitmap.getHeight());
        rect.getClass();
        rect.left = Math.max(0, rect.left);
        rect.top = Math.max(0, rect.top);
        rect.right = Math.min(rect.right, bitmap.getWidth());
        rect.bottom = Math.min(rect.bottom, bitmap.getHeight());
        return Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height());
    }
}
