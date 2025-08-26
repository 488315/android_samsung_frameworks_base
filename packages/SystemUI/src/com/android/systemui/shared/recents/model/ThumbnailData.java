package com.android.systemui.shared.recents.model;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import android.util.Log;
import android.window.TaskSnapshot;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.Unit;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class ThumbnailData {
    public static final Companion Companion = new Companion(null);
    public final int appearance;
    public final Rect insets;
    public final boolean isRealSnapshot;
    public final boolean isTranslucent;
    public final Rect letterboxInsets;
    public final int orientation;
    public final boolean reducedResolution;
    public final int rotation;
    public final float scale;
    public final long snapshotId;
    public final Bitmap thumbnail;
    public final int windowingMode;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public ThumbnailData() {
        this(null, 0, 0, null, null, false, false, false, 0, 0, 0.0f, 0L, 4095, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x008b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final ThumbnailData fromSnapshot(TaskSnapshot taskSnapshot) throws Exception {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        Companion.getClass();
        Bitmap bitmapCreateBitmap = null;
        try {
            HardwareBuffer hardwareBuffer = taskSnapshot.getHardwareBuffer();
            if (hardwareBuffer != null) {
                try {
                    bitmapCreateBitmap = Bitmap.wrapHardwareBuffer(hardwareBuffer, taskSnapshot.getColorSpace());
                    Unit unit = Unit.INSTANCE;
                    hardwareBuffer.close();
                } catch (Throwable th) {
                    Bitmap bitmap = bitmapCreateBitmap;
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        try {
                            AutoCloseableKt.closeFinally(hardwareBuffer, th);
                            throw th2;
                        } catch (IllegalArgumentException e) {
                            e = e;
                            bitmapCreateBitmap = bitmap;
                            Log.e("ThumbnailData", "Unexpected snapshot without USAGE_GPU_SAMPLED_IMAGE: " + taskSnapshot.getHardwareBuffer(), e);
                            if (bitmapCreateBitmap == null) {
                            }
                            Bitmap bitmap2 = bitmapCreateBitmap;
                            Rect rect = new Rect(taskSnapshot.getContentInsets());
                            Rect rect2 = new Rect(taskSnapshot.getCutoutInsets());
                            i = rect.left;
                            i2 = rect2.left;
                            if (i < i2) {
                            }
                            rect.left = i;
                            i3 = rect.top;
                            i4 = rect2.top;
                            if (i3 < i4) {
                            }
                            rect.top = i3;
                            i5 = rect.right;
                            i6 = rect2.right;
                            if (i5 < i6) {
                            }
                            rect.right = i5;
                            i7 = rect.bottom;
                            i8 = rect2.bottom;
                            if (i7 < i8) {
                            }
                            rect.bottom = i7;
                            return new ThumbnailData(bitmap2, taskSnapshot.getOrientation(), taskSnapshot.getRotation(), rect, new Rect(taskSnapshot.getLetterboxInsets()), taskSnapshot.isLowResolution(), taskSnapshot.isRealSnapshot(), taskSnapshot.isTranslucent(), taskSnapshot.getWindowingMode(), taskSnapshot.getAppearance(), bitmap2.getWidth() / taskSnapshot.getTaskSize().x, taskSnapshot.getId());
                        }
                    }
                }
            }
        } catch (IllegalArgumentException e2) {
            e = e2;
        }
        if (bitmapCreateBitmap == null) {
            bitmapCreateBitmap = Bitmap.createBitmap(taskSnapshot.getTaskSize().x, taskSnapshot.getTaskSize().y, Bitmap.Config.ARGB_8888);
            bitmapCreateBitmap.eraseColor(-16777216);
        }
        Bitmap bitmap22 = bitmapCreateBitmap;
        Rect rect3 = new Rect(taskSnapshot.getContentInsets());
        try {
            Rect rect22 = new Rect(taskSnapshot.getCutoutInsets());
            i = rect3.left;
            i2 = rect22.left;
            if (i < i2) {
                i = i2;
            }
            rect3.left = i;
            i3 = rect3.top;
            i4 = rect22.top;
            if (i3 < i4) {
                i3 = i4;
            }
            rect3.top = i3;
            i5 = rect3.right;
            i6 = rect22.right;
            if (i5 < i6) {
                i5 = i6;
            }
            rect3.right = i5;
            i7 = rect3.bottom;
            i8 = rect22.bottom;
            if (i7 < i8) {
                i7 = i8;
            }
            rect3.bottom = i7;
        } catch (NoSuchMethodError unused) {
        }
        return new ThumbnailData(bitmap22, taskSnapshot.getOrientation(), taskSnapshot.getRotation(), rect3, new Rect(taskSnapshot.getLetterboxInsets()), taskSnapshot.isLowResolution(), taskSnapshot.isRealSnapshot(), taskSnapshot.isTranslucent(), taskSnapshot.getWindowingMode(), taskSnapshot.getAppearance(), bitmap22.getWidth() / taskSnapshot.getTaskSize().x, taskSnapshot.getId());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ThumbnailData)) {
            return false;
        }
        ThumbnailData thumbnailData = (ThumbnailData) obj;
        return Intrinsics.areEqual(this.thumbnail, thumbnailData.thumbnail) && this.orientation == thumbnailData.orientation && this.rotation == thumbnailData.rotation && Intrinsics.areEqual(this.insets, thumbnailData.insets) && Intrinsics.areEqual(this.letterboxInsets, thumbnailData.letterboxInsets) && this.reducedResolution == thumbnailData.reducedResolution && this.isRealSnapshot == thumbnailData.isRealSnapshot && this.isTranslucent == thumbnailData.isTranslucent && this.windowingMode == thumbnailData.windowingMode && this.appearance == thumbnailData.appearance && Float.compare(this.scale, thumbnailData.scale) == 0 && this.snapshotId == thumbnailData.snapshotId;
    }

    public final int hashCode() {
        Bitmap bitmap = this.thumbnail;
        return Long.hashCode(this.snapshotId) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scale, ReorderTile$$ExternalSyntheticOutline0.m(this.appearance, ReorderTile$$ExternalSyntheticOutline0.m(this.windowingMode, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((this.letterboxInsets.hashCode() + ((this.insets.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.rotation, ReorderTile$$ExternalSyntheticOutline0.m(this.orientation, (bitmap == null ? 0 : bitmap.hashCode()) * 31, 31), 31)) * 31)) * 31, 31, this.reducedResolution), 31, this.isRealSnapshot), 31, this.isTranslucent), 31), 31), 31);
    }

    public final String toString() {
        Bitmap bitmap = this.thumbnail;
        Rect rect = this.insets;
        Rect rect2 = this.letterboxInsets;
        StringBuilder sb = new StringBuilder("ThumbnailData(thumbnail=");
        sb.append(bitmap);
        sb.append(", orientation=");
        sb.append(this.orientation);
        sb.append(", rotation=");
        sb.append(this.rotation);
        sb.append(", insets=");
        sb.append(rect);
        sb.append(", letterboxInsets=");
        sb.append(rect2);
        sb.append(", reducedResolution=");
        sb.append(this.reducedResolution);
        sb.append(", isRealSnapshot=");
        sb.append(this.isRealSnapshot);
        sb.append(", isTranslucent=");
        sb.append(this.isTranslucent);
        sb.append(", windowingMode=");
        sb.append(this.windowingMode);
        sb.append(", appearance=");
        sb.append(this.appearance);
        sb.append(", scale=");
        sb.append(this.scale);
        sb.append(", snapshotId=");
        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.snapshotId, ")", sb);
    }

    public ThumbnailData(Bitmap bitmap, int i, int i2, Rect rect, Rect rect2, boolean z, boolean z2, boolean z3, int i3, int i4, float f, long j) {
        this.thumbnail = bitmap;
        this.orientation = i;
        this.rotation = i2;
        this.insets = rect;
        this.letterboxInsets = rect2;
        this.reducedResolution = z;
        this.isRealSnapshot = z2;
        this.isTranslucent = z3;
        this.windowingMode = i3;
        this.appearance = i4;
        this.scale = f;
        this.snapshotId = j;
    }

    public /* synthetic */ ThumbnailData(Bitmap bitmap, int i, int i2, Rect rect, Rect rect2, boolean z, boolean z2, boolean z3, int i3, int i4, float f, long j, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? null : bitmap, (i5 & 2) != 0 ? 0 : i, (i5 & 4) != 0 ? -1 : i2, (i5 & 8) != 0 ? new Rect() : rect, (i5 & 16) != 0 ? new Rect() : rect2, (i5 & 32) != 0 ? false : z, (i5 & 64) != 0 ? true : z2, (i5 & 128) != 0 ? false : z3, (i5 & 256) != 0 ? 0 : i3, (i5 & 512) == 0 ? i4 : 0, (i5 & 1024) != 0 ? 1.0f : f, (i5 & 2048) != 0 ? 0L : j);
    }
}
