package com.android.systemui.shared.recents.model;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ThumbnailData() {
        this(null, 0, 0, null, null, false, false, false, 0, 0, 0.0f, 0L, 4095, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final com.android.systemui.shared.recents.model.ThumbnailData fromSnapshot(android.window.TaskSnapshot r16) {
        /*
            com.android.systemui.shared.recents.model.ThumbnailData$Companion r0 = com.android.systemui.shared.recents.model.ThumbnailData.Companion
            r0.getClass()
            r1 = 0
            android.hardware.HardwareBuffer r2 = r16.getHardwareBuffer()     // Catch: java.lang.IllegalArgumentException -> L2b
            if (r2 == 0) goto L43
            android.graphics.ColorSpace r0 = r16.getColorSpace()     // Catch: java.lang.Throwable -> L22
            android.graphics.Bitmap r3 = android.graphics.Bitmap.wrapHardwareBuffer(r2, r0)     // Catch: java.lang.Throwable -> L22
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1e
            kotlin.jdk7.AutoCloseableKt.closeFinally(r2, r1)     // Catch: java.lang.IllegalArgumentException -> L1b
            r1 = r3
            goto L43
        L1b:
            r0 = move-exception
            r1 = r3
            goto L2c
        L1e:
            r0 = move-exception
            r1 = r3
        L20:
            r3 = r0
            goto L24
        L22:
            r0 = move-exception
            goto L20
        L24:
            throw r3     // Catch: java.lang.Throwable -> L25
        L25:
            r0 = move-exception
            r4 = r0
            kotlin.jdk7.AutoCloseableKt.closeFinally(r2, r3)     // Catch: java.lang.IllegalArgumentException -> L2b
            throw r4     // Catch: java.lang.IllegalArgumentException -> L2b
        L2b:
            r0 = move-exception
        L2c:
            android.hardware.HardwareBuffer r2 = r16.getHardwareBuffer()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Unexpected snapshot without USAGE_GPU_SAMPLED_IMAGE: "
            r3.<init>(r4)
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            java.lang.String r3 = "ThumbnailData"
            android.util.Log.e(r3, r2, r0)
        L43:
            if (r1 != 0) goto L5c
            android.graphics.Point r0 = r16.getTaskSize()
            int r0 = r0.x
            android.graphics.Point r1 = r16.getTaskSize()
            int r1 = r1.y
            android.graphics.Bitmap$Config r2 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r1 = android.graphics.Bitmap.createBitmap(r0, r1, r2)
            r0 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r1.eraseColor(r0)
        L5c:
            r3 = r1
            android.graphics.Rect r6 = new android.graphics.Rect
            android.graphics.Rect r0 = r16.getContentInsets()
            r6.<init>(r0)
            android.graphics.Rect r0 = new android.graphics.Rect     // Catch: java.lang.NoSuchMethodError -> L93
            android.graphics.Rect r1 = r16.getCutoutInsets()     // Catch: java.lang.NoSuchMethodError -> L93
            r0.<init>(r1)     // Catch: java.lang.NoSuchMethodError -> L93
            int r1 = r6.left     // Catch: java.lang.NoSuchMethodError -> L93
            int r2 = r0.left     // Catch: java.lang.NoSuchMethodError -> L93
            if (r1 >= r2) goto L76
            r1 = r2
        L76:
            r6.left = r1     // Catch: java.lang.NoSuchMethodError -> L93
            int r1 = r6.top     // Catch: java.lang.NoSuchMethodError -> L93
            int r2 = r0.top     // Catch: java.lang.NoSuchMethodError -> L93
            if (r1 >= r2) goto L7f
            r1 = r2
        L7f:
            r6.top = r1     // Catch: java.lang.NoSuchMethodError -> L93
            int r1 = r6.right     // Catch: java.lang.NoSuchMethodError -> L93
            int r2 = r0.right     // Catch: java.lang.NoSuchMethodError -> L93
            if (r1 >= r2) goto L88
            r1 = r2
        L88:
            r6.right = r1     // Catch: java.lang.NoSuchMethodError -> L93
            int r1 = r6.bottom     // Catch: java.lang.NoSuchMethodError -> L93
            int r0 = r0.bottom     // Catch: java.lang.NoSuchMethodError -> L93
            if (r1 >= r0) goto L91
            r1 = r0
        L91:
            r6.bottom = r1     // Catch: java.lang.NoSuchMethodError -> L93
        L93:
            android.graphics.Rect r7 = new android.graphics.Rect
            android.graphics.Rect r0 = r16.getLetterboxInsets()
            r7.<init>(r0)
            int r4 = r16.getOrientation()
            int r5 = r16.getRotation()
            boolean r8 = r16.isLowResolution()
            int r0 = r3.getWidth()
            float r0 = (float) r0
            android.graphics.Point r1 = r16.getTaskSize()
            int r1 = r1.x
            float r1 = (float) r1
            float r13 = r0 / r1
            boolean r9 = r16.isRealSnapshot()
            boolean r10 = r16.isTranslucent()
            int r11 = r16.getWindowingMode()
            int r12 = r16.getAppearance()
            long r14 = r16.getId()
            com.android.systemui.shared.recents.model.ThumbnailData r0 = new com.android.systemui.shared.recents.model.ThumbnailData
            r2 = r0
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shared.recents.model.ThumbnailData.fromSnapshot(android.window.TaskSnapshot):com.android.systemui.shared.recents.model.ThumbnailData");
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
        return Long.hashCode(this.snapshotId) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scale, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.appearance, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.windowingMode, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((this.letterboxInsets.hashCode() + ((this.insets.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.rotation, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.orientation, (bitmap == null ? 0 : bitmap.hashCode()) * 31, 31), 31)) * 31)) * 31, 31, this.reducedResolution), 31, this.isRealSnapshot), 31, this.isTranslucent), 31), 31), 31);
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
