package com.android.systemui.shared.recents.model;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.util.Log;
import android.window.TaskSnapshot;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ThumbnailData {
    public final Rect letterboxInsets;
    public final int rotation;
    public final float scale;
    public final Bitmap thumbnail;
    public final int windowingMode;

    public ThumbnailData() {
        this.thumbnail = null;
        this.rotation = -1;
        new Rect();
        this.letterboxInsets = new Rect();
        this.scale = 1.0f;
        this.windowingMode = 0;
    }

    public ThumbnailData(TaskSnapshot taskSnapshot) {
        Bitmap bitmap = null;
        try {
            HardwareBuffer hardwareBuffer = taskSnapshot.getHardwareBuffer();
            if (hardwareBuffer != null) {
                try {
                    bitmap = Bitmap.wrapHardwareBuffer(hardwareBuffer, taskSnapshot.getColorSpace());
                } finally {
                }
            }
            if (hardwareBuffer != null) {
                hardwareBuffer.close();
            }
        } catch (IllegalArgumentException e) {
            Log.e("ThumbnailData", "Unexpected snapshot without USAGE_GPU_SAMPLED_IMAGE: " + taskSnapshot.getHardwareBuffer(), e);
        }
        if (bitmap == null) {
            Point taskSize = taskSnapshot.getTaskSize();
            bitmap = Bitmap.createBitmap(taskSize.x, taskSize.y, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(EmergencyPhoneWidget.BG_COLOR);
        }
        this.thumbnail = bitmap;
        Rect rect = new Rect(taskSnapshot.getContentInsets());
        Rect rect2 = new Rect(taskSnapshot.getCutoutInsets());
        rect.left = Math.max(rect.left, rect2.left);
        rect.top = Math.max(rect.top, rect2.top);
        rect.right = Math.max(rect.right, rect2.right);
        rect.bottom = Math.max(rect.bottom, rect2.bottom);
        this.letterboxInsets = new Rect(taskSnapshot.getLetterboxInsets());
        taskSnapshot.getOrientation();
        this.rotation = taskSnapshot.getRotation();
        taskSnapshot.isLowResolution();
        this.scale = bitmap.getWidth() / taskSnapshot.getTaskSize().x;
        taskSnapshot.isRealSnapshot();
        taskSnapshot.isTranslucent();
        this.windowingMode = taskSnapshot.getWindowingMode();
        taskSnapshot.getAppearance();
        taskSnapshot.getId();
    }
}
