package com.android.systemui.screenshot;

import android.content.ComponentName;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Rect;
import android.net.Uri;
import android.os.UserHandle;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.view.WindowManager;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenshotData {
    public static final Companion Companion = new Companion(null);
    public Bitmap bitmap;
    public final Uri contextUrl;
    public boolean disableCapture;
    public int displayId;
    public final Insets insets;
    public Rect screenBounds;
    public boolean secureLayer;
    public final int source;
    public int taskId;
    public ComponentName topComponent;
    public int type;
    public UserHandle userHandle;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ScreenshotData forTesting() {
            return new ScreenshotData(0, 0, null, null, null, 0, Insets.NONE, null, null, 0, false, false, 3840, null);
        }
    }

    public ScreenshotData(@WindowManager.ScreenshotType int i, @WindowManager.ScreenshotSource int i2, UserHandle userHandle, ComponentName componentName, Rect rect, int i3, Insets insets, Bitmap bitmap, Uri uri, int i4, boolean z, boolean z2) {
        this.type = i;
        this.source = i2;
        this.userHandle = userHandle;
        this.topComponent = componentName;
        this.screenBounds = rect;
        this.taskId = i3;
        this.insets = insets;
        this.bitmap = bitmap;
        this.contextUrl = uri;
        this.displayId = i4;
        this.disableCapture = z;
        this.secureLayer = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ScreenshotData)) {
            return false;
        }
        ScreenshotData screenshotData = (ScreenshotData) obj;
        return this.type == screenshotData.type && this.source == screenshotData.source && Intrinsics.areEqual(this.userHandle, screenshotData.userHandle) && Intrinsics.areEqual(this.topComponent, screenshotData.topComponent) && Intrinsics.areEqual(this.screenBounds, screenshotData.screenBounds) && this.taskId == screenshotData.taskId && Intrinsics.areEqual(this.insets, screenshotData.insets) && Intrinsics.areEqual(this.bitmap, screenshotData.bitmap) && Intrinsics.areEqual(this.contextUrl, screenshotData.contextUrl) && this.displayId == screenshotData.displayId && this.disableCapture == screenshotData.disableCapture && this.secureLayer == screenshotData.secureLayer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.source, Integer.hashCode(this.type) * 31, 31);
        UserHandle userHandle = this.userHandle;
        int hashCode = (m42m + (userHandle == null ? 0 : userHandle.hashCode())) * 31;
        ComponentName componentName = this.topComponent;
        int hashCode2 = (hashCode + (componentName == null ? 0 : componentName.hashCode())) * 31;
        Rect rect = this.screenBounds;
        int hashCode3 = (this.insets.hashCode() + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.taskId, (hashCode2 + (rect == null ? 0 : rect.hashCode())) * 31, 31)) * 31;
        Bitmap bitmap = this.bitmap;
        int hashCode4 = (hashCode3 + (bitmap == null ? 0 : bitmap.hashCode())) * 31;
        Uri uri = this.contextUrl;
        int m42m2 = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.displayId, (hashCode4 + (uri != null ? uri.hashCode() : 0)) * 31, 31);
        boolean z = this.disableCapture;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (m42m2 + i) * 31;
        boolean z2 = this.secureLayer;
        return i2 + (z2 ? 1 : z2 ? 1 : 0);
    }

    public final String toString() {
        int i = this.type;
        UserHandle userHandle = this.userHandle;
        ComponentName componentName = this.topComponent;
        Rect rect = this.screenBounds;
        int i2 = this.taskId;
        Bitmap bitmap = this.bitmap;
        int i3 = this.displayId;
        boolean z = this.disableCapture;
        boolean z2 = this.secureLayer;
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("ScreenshotData(type=", i, ", source=");
        m1m.append(this.source);
        m1m.append(", userHandle=");
        m1m.append(userHandle);
        m1m.append(", topComponent=");
        m1m.append(componentName);
        m1m.append(", screenBounds=");
        m1m.append(rect);
        m1m.append(", taskId=");
        m1m.append(i2);
        m1m.append(", insets=");
        m1m.append(this.insets);
        m1m.append(", bitmap=");
        m1m.append(bitmap);
        m1m.append(", contextUrl=");
        m1m.append(this.contextUrl);
        m1m.append(", displayId=");
        m1m.append(i3);
        m1m.append(", disableCapture=");
        m1m.append(z);
        m1m.append(", secureLayer=");
        m1m.append(z2);
        m1m.append(")");
        return m1m.toString();
    }

    public /* synthetic */ ScreenshotData(int i, int i2, UserHandle userHandle, ComponentName componentName, Rect rect, int i3, Insets insets, Bitmap bitmap, Uri uri, int i4, boolean z, boolean z2, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, userHandle, componentName, rect, i3, insets, bitmap, (i5 & 256) != 0 ? null : uri, (i5 & 512) != 0 ? 0 : i4, (i5 & 1024) != 0 ? false : z, (i5 & 2048) != 0 ? false : z2);
    }
}
