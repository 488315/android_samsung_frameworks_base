package com.android.systemui.screenshot.sep;

import android.graphics.Bitmap;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

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
        int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.failedReason, (bitmap == null ? 0 : bitmap.hashCode()) * 31, 31);
        String str = this.targetWindowName;
        int iHashCode = (iM + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.secureWindowName;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public final String toString() {
        Bitmap bitmap = this.bitmap;
        StringBuilder sb = new StringBuilder("SemScreenshotResult(bitmap=");
        sb.append(bitmap);
        sb.append(", failedReason=");
        sb.append(this.failedReason);
        sb.append(", targetWindowName=");
        sb.append(this.targetWindowName);
        sb.append(", secureWindowName=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.secureWindowName, ")");
    }
}
