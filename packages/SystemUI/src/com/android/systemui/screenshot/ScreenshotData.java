package com.android.systemui.screenshot;

import android.content.ComponentName;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Rect;
import android.net.Uri;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.WindowManager;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenshotData {
    public static final Companion Companion = new Companion(null);
    public Bitmap bitmap;
    public final Uri contextUrl;
    public boolean disableCapture;
    public final int displayId;
    public final Insets insets;
    public Rect screenBounds;
    public boolean secureLayer;
    public final int source;
    public int taskId;
    public ComponentName topComponent;
    public int type;
    public UserHandle userHandle;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public final ScreenshotData forTesting() {
            return new ScreenshotData(0, 0, null, null, null, 0, Insets.NONE, null, 0, null, false, false, 3584, null);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ScreenshotData(@WindowManager.ScreenshotType int i, @WindowManager.ScreenshotSource int i2, UserHandle userHandle, ComponentName componentName, Rect rect, int i3, Insets insets, Bitmap bitmap, int i4, Uri uri, boolean z, boolean z2) {
        this.type = i;
        this.source = i2;
        this.userHandle = userHandle;
        this.topComponent = componentName;
        this.screenBounds = rect;
        this.taskId = i3;
        this.insets = insets;
        this.bitmap = bitmap;
        this.displayId = i4;
        this.contextUrl = uri;
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
        return this.type == screenshotData.type && this.source == screenshotData.source && Intrinsics.areEqual(this.userHandle, screenshotData.userHandle) && Intrinsics.areEqual(this.topComponent, screenshotData.topComponent) && Intrinsics.areEqual(this.screenBounds, screenshotData.screenBounds) && this.taskId == screenshotData.taskId && Intrinsics.areEqual(this.insets, screenshotData.insets) && Intrinsics.areEqual(this.bitmap, screenshotData.bitmap) && this.displayId == screenshotData.displayId && Intrinsics.areEqual(this.contextUrl, screenshotData.contextUrl) && this.disableCapture == screenshotData.disableCapture && this.secureLayer == screenshotData.secureLayer;
    }

    public final String getPackageNameString() {
        ComponentName componentName = this.topComponent;
        if (componentName == null) {
            return "";
        }
        Intrinsics.checkNotNull(componentName);
        return componentName.getPackageName();
    }

    public final int hashCode() {
        int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.source, Integer.hashCode(this.type) * 31, 31);
        UserHandle userHandle = this.userHandle;
        int hashCode = (m + (userHandle == null ? 0 : userHandle.hashCode())) * 31;
        ComponentName componentName = this.topComponent;
        int hashCode2 = (hashCode + (componentName == null ? 0 : componentName.hashCode())) * 31;
        Rect rect = this.screenBounds;
        int hashCode3 = (this.insets.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.taskId, (hashCode2 + (rect == null ? 0 : rect.hashCode())) * 31, 31)) * 31;
        Bitmap bitmap = this.bitmap;
        int m2 = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.displayId, (hashCode3 + (bitmap == null ? 0 : bitmap.hashCode())) * 31, 31);
        Uri uri = this.contextUrl;
        return Boolean.hashCode(this.secureLayer) + TransitionData$$ExternalSyntheticOutline0.m((m2 + (uri != null ? uri.hashCode() : 0)) * 31, 31, this.disableCapture);
    }

    public final String toString() {
        int i = this.type;
        UserHandle userHandle = this.userHandle;
        ComponentName componentName = this.topComponent;
        Rect rect = this.screenBounds;
        int i2 = this.taskId;
        Insets insets = this.insets;
        Bitmap bitmap = this.bitmap;
        Uri uri = this.contextUrl;
        boolean z = this.disableCapture;
        boolean z2 = this.secureLayer;
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "ScreenshotData(type=", ", source=");
        m.append(this.source);
        m.append(", userHandle=");
        m.append(userHandle);
        m.append(", topComponent=");
        m.append(componentName);
        m.append(", screenBounds=");
        m.append(rect);
        m.append(", taskId=");
        m.append(i2);
        m.append(", insets=");
        m.append(insets);
        m.append(", bitmap=");
        m.append(bitmap);
        m.append(", displayId=");
        m.append(this.displayId);
        m.append(", contextUrl=");
        m.append(uri);
        m.append(", disableCapture=");
        m.append(z);
        m.append(", secureLayer=");
        m.append(z2);
        m.append(")");
        return m.toString();
    }

    public /* synthetic */ ScreenshotData(int i, int i2, UserHandle userHandle, ComponentName componentName, Rect rect, int i3, Insets insets, Bitmap bitmap, int i4, Uri uri, boolean z, boolean z2, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, userHandle, componentName, rect, i3, insets, bitmap, i4, (i5 & 512) != 0 ? null : uri, (i5 & 1024) != 0 ? false : z, (i5 & 2048) != 0 ? false : z2);
    }
}
