package com.android.systemui.screenshot;

import android.content.ComponentName;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.UserHandle;
import android.view.WindowManager;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ScreenshotData {
    public static final Companion Companion = new Companion(null);
    public Bitmap bitmap;
    public final boolean disableCapture;
    public final int displayId;
    public final Insets originalInsets;
    public final Rect originalScreenBounds;
    public final boolean secureLayer;
    public final int source;
    public final int taskId;
    public final ComponentName topComponent;
    public final int type;
    public final UserHandle userHandle;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ScreenshotData forTesting(UserHandle userHandle, int i, ComponentName componentName, Bitmap bitmap) {
            return new ScreenshotData(1, i, userHandle, componentName, 0, null, Insets.NONE, bitmap, 0, false, false, 1536, null);
        }

        private Companion() {
        }
    }

    public ScreenshotData(@WindowManager.ScreenshotType int i, @WindowManager.ScreenshotSource int i2, UserHandle userHandle, ComponentName componentName, int i3, Rect rect, Insets insets, Bitmap bitmap, int i4, boolean z, boolean z2) {
        this.type = i;
        this.source = i2;
        this.userHandle = userHandle;
        this.topComponent = componentName;
        this.taskId = i3;
        this.originalScreenBounds = rect;
        this.originalInsets = insets;
        this.bitmap = bitmap;
        this.displayId = i4;
        this.disableCapture = z;
        this.secureLayer = z2;
    }

    public static ScreenshotData copy$default(ScreenshotData screenshotData, int i, UserHandle userHandle, ComponentName componentName, int i2, Rect rect, Bitmap bitmap, boolean z, int i3) {
        int i4 = screenshotData.source;
        Insets insets = screenshotData.originalInsets;
        int i5 = screenshotData.displayId;
        boolean z2 = screenshotData.disableCapture;
        boolean z3 = (i3 & 1024) != 0 ? screenshotData.secureLayer : z;
        screenshotData.getClass();
        return new ScreenshotData(i, i4, userHandle, componentName, i2, rect, insets, bitmap, i5, z2, z3);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ScreenshotData)) {
            return false;
        }
        ScreenshotData screenshotData = (ScreenshotData) obj;
        return this.type == screenshotData.type && this.source == screenshotData.source && Intrinsics.areEqual(this.userHandle, screenshotData.userHandle) && Intrinsics.areEqual(this.topComponent, screenshotData.topComponent) && this.taskId == screenshotData.taskId && Intrinsics.areEqual(this.originalScreenBounds, screenshotData.originalScreenBounds) && Intrinsics.areEqual(this.originalInsets, screenshotData.originalInsets) && Intrinsics.areEqual(this.bitmap, screenshotData.bitmap) && this.displayId == screenshotData.displayId && this.disableCapture == screenshotData.disableCapture && this.secureLayer == screenshotData.secureLayer;
    }

    public final String getPackageNameString() {
        String packageName;
        ComponentName componentName = this.topComponent;
        return (componentName == null || (packageName = componentName.getPackageName()) == null) ? "" : packageName;
    }

    public final int hashCode() {
        int iHashCode = (this.userHandle.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.source, Integer.hashCode(this.type) * 31, 31)) * 31;
        ComponentName componentName = this.topComponent;
        int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.taskId, (iHashCode + (componentName == null ? 0 : componentName.hashCode())) * 31, 31);
        Rect rect = this.originalScreenBounds;
        int iHashCode2 = (this.originalInsets.hashCode() + ((iM + (rect == null ? 0 : rect.hashCode())) * 31)) * 31;
        Bitmap bitmap = this.bitmap;
        return Boolean.hashCode(this.secureLayer) + TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, (iHashCode2 + (bitmap != null ? bitmap.hashCode() : 0)) * 31, 31), 31, this.disableCapture);
    }

    public final String toString() {
        UserHandle userHandle = this.userHandle;
        ComponentName componentName = this.topComponent;
        Rect rect = this.originalScreenBounds;
        Insets insets = this.originalInsets;
        Bitmap bitmap = this.bitmap;
        StringBuilder sb = new StringBuilder("ScreenshotData(type=");
        sb.append(this.type);
        sb.append(", source=");
        sb.append(this.source);
        sb.append(", userHandle=");
        sb.append(userHandle);
        sb.append(", topComponent=");
        sb.append(componentName);
        sb.append(", taskId=");
        sb.append(this.taskId);
        sb.append(", originalScreenBounds=");
        sb.append(rect);
        sb.append(", originalInsets=");
        sb.append(insets);
        sb.append(", bitmap=");
        sb.append(bitmap);
        sb.append(", displayId=");
        sb.append(this.displayId);
        sb.append(", disableCapture=");
        sb.append(this.disableCapture);
        sb.append(", secureLayer=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.secureLayer, ")");
    }

    public /* synthetic */ ScreenshotData(int i, int i2, UserHandle userHandle, ComponentName componentName, int i3, Rect rect, Insets insets, Bitmap bitmap, int i4, boolean z, boolean z2, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, userHandle, componentName, i3, rect, insets, bitmap, i4, (i5 & 512) != 0 ? false : z, (i5 & 1024) != 0 ? false : z2);
    }
}
