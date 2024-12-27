package com.android.systemui.screenshot.sep;

import android.graphics.Bitmap;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SemScreenshotResult {
    public final Bitmap bitmap;
    public final int failedReason;
    public final String secureWindowName;
    public final String targetWindowName;

    public SemScreenshotResult(Bitmap bitmap, int i, String str, String str2) {
        this.bitmap = bitmap;
        this.failedReason = i;
        this.targetWindowName = str;
        this.secureWindowName = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SemScreenshotResult)) {
            return false;
        }
        SemScreenshotResult semScreenshotResult = (SemScreenshotResult) obj;
        return Intrinsics.areEqual(this.bitmap, semScreenshotResult.bitmap) && this.failedReason == semScreenshotResult.failedReason && Intrinsics.areEqual(this.targetWindowName, semScreenshotResult.targetWindowName) && Intrinsics.areEqual(this.secureWindowName, semScreenshotResult.secureWindowName);
    }

    public final int hashCode() {
        Bitmap bitmap = this.bitmap;
        int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.failedReason, (bitmap == null ? 0 : bitmap.hashCode()) * 31, 31);
        String str = this.targetWindowName;
        int hashCode = (m + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.secureWindowName;
        return hashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public final String toString() {
        return "SemScreenshotResult(bitmap=" + this.bitmap + ", failedReason=" + this.failedReason + ", targetWindowName=" + this.targetWindowName + ", secureWindowName=" + this.secureWindowName + ")";
    }
}
