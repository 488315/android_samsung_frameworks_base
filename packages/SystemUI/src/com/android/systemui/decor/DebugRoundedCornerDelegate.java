package com.android.systemui.decor;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Size;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public final class DebugRoundedCornerDelegate implements RoundedCornerResDelegate {
    public PathDrawable bottomRoundedDrawable;
    public boolean hasBottom;
    public boolean hasTop;
    public final Paint paint;
    public PathDrawable topRoundedDrawable;
    public Size topRoundedSize = new Size(0, 0);
    public Size bottomRoundedSize = new Size(0, 0);
    public float physicalPixelDisplaySizeRatio = 1.0f;
    public int color = -65536;

    public DebugRoundedCornerDelegate() {
        Paint paint = new Paint();
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.FILL);
        this.paint = paint;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("DebugRoundedCornerDelegate state:");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  hasTop=", this.hasTop);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  hasBottom=", this.hasBottom);
        printWriter.println(MutableVectorKt$$ExternalSyntheticOutline0.m(this.topRoundedSize.getWidth(), this.topRoundedSize.getHeight(), "  topRoundedSize(w,h)=(", ",", ")"));
        printWriter.println(MutableVectorKt$$ExternalSyntheticOutline0.m(this.bottomRoundedSize.getWidth(), this.bottomRoundedSize.getHeight(), "  bottomRoundedSize(w,h)=(", ",", ")"));
        printWriter.println("  physicalPixelDisplaySizeRatio=" + this.physicalPixelDisplaySizeRatio);
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final Drawable getBottomRoundedDrawable() {
        return this.bottomRoundedDrawable;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final Size getBottomRoundedSize() {
        return this.bottomRoundedSize;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final boolean getHasBottom() {
        return this.hasBottom;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final boolean getHasTop() {
        return this.hasTop;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final Drawable getTopRoundedDrawable() {
        return this.topRoundedDrawable;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final Size getTopRoundedSize() {
        return this.topRoundedSize;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final void setPhysicalPixelDisplaySizeRatio(float f) {
        if (this.physicalPixelDisplaySizeRatio == f) {
            return;
        }
        this.physicalPixelDisplaySizeRatio = f;
        PathDrawable pathDrawable = this.topRoundedDrawable;
        if (pathDrawable != null) {
            this.topRoundedSize = new Size(pathDrawable.width, pathDrawable.height);
        }
        PathDrawable pathDrawable2 = this.bottomRoundedDrawable;
        if (pathDrawable2 != null) {
            this.bottomRoundedSize = new Size(pathDrawable2.width, pathDrawable2.height);
        }
        if (this.physicalPixelDisplaySizeRatio == 1.0f) {
            return;
        }
        if (this.topRoundedSize.getWidth() != 0) {
            this.topRoundedSize = new Size((int) ((this.physicalPixelDisplaySizeRatio * this.topRoundedSize.getWidth()) + 0.5f), (int) ((this.physicalPixelDisplaySizeRatio * this.topRoundedSize.getHeight()) + 0.5f));
        }
        if (this.bottomRoundedSize.getWidth() != 0) {
            this.bottomRoundedSize = new Size((int) ((this.physicalPixelDisplaySizeRatio * this.bottomRoundedSize.getWidth()) + 0.5f), (int) ((this.physicalPixelDisplaySizeRatio * this.bottomRoundedSize.getHeight()) + 0.5f));
        }
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final void updateDisplayUniqueId(String str, Integer num) {
    }
}
