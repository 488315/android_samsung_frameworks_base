package com.android.systemui.screenshot.sep;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Slog;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenCaptureHelperForPartial extends ScreenCaptureHelper {
    public static final String TAG;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
    public final Bitmap onPostScreenshot(Bitmap bitmap) {
        Bundle bundle = this.mBundle;
        Intrinsics.checkNotNull(bundle);
        Rect rect = (Rect) bundle.getParcelable("rect");
        Slog.d(TAG, "rect : " + rect + " bitmap.getWidth() : " + bitmap.getWidth() + " bitmap.getHeight() : " + bitmap.getHeight());
        Intrinsics.checkNotNull(rect);
        rect.left = Math.max(0, rect.left);
        rect.top = Math.max(0, rect.top);
        rect.right = Math.min(rect.right, bitmap.getWidth());
        rect.bottom = Math.min(rect.bottom, bitmap.getHeight());
        return Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height());
    }
}
