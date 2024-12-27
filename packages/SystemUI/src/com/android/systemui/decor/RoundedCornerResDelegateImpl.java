package com.android.systemui.decor;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.DisplayUtils;
import android.util.Size;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import java.io.PrintWriter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RoundedCornerResDelegateImpl implements RoundedCornerResDelegate, Dumpable {
    public Drawable bottomRoundedDrawable;
    public String displayUniqueId;
    public boolean hasBottom;
    public boolean hasTop;
    public int reloadToken;
    public final Resources res;
    public Drawable topRoundedDrawable;
    public Size topRoundedSize = new Size(0, 0);
    public Size bottomRoundedSize = new Size(0, 0);
    public float physicalPixelDisplaySizeRatio = 1.0f;

    public RoundedCornerResDelegateImpl(Resources resources, String str) {
        this.res = resources;
        this.displayUniqueId = str;
        reloadRes();
        reloadMeasures();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("RoundedCornerResDelegate state:");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  hasTop=", this.hasTop, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  hasBottom=", this.hasBottom, printWriter);
        printWriter.println(HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(this.topRoundedSize.getWidth(), this.topRoundedSize.getHeight(), "  topRoundedSize(w,h)=(", ",", ")"));
        printWriter.println(HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(this.bottomRoundedSize.getWidth(), this.bottomRoundedSize.getHeight(), "  bottomRoundedSize(w,h)=(", ",", ")"));
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

    public final void reloadMeasures() {
        Drawable drawable = this.topRoundedDrawable;
        if (drawable != null) {
            this.topRoundedSize = new Size(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        Drawable drawable2 = this.bottomRoundedDrawable;
        if (drawable2 != null) {
            this.bottomRoundedSize = new Size(drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
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

    public final void reloadRes() {
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(this.res, this.displayUniqueId);
        boolean z = this.res.getBoolean(R.bool.config_enableRoundedCorner);
        this.hasTop = z;
        this.hasBottom = z;
        TypedArray obtainTypedArray = this.res.obtainTypedArray(R.array.config_roundedCornerTopDrawableArray);
        Drawable drawable = (displayUniqueIdConfigIndex < 0 || displayUniqueIdConfigIndex >= obtainTypedArray.length()) ? this.res.getDrawable(R.drawable.rounded_corner_top, null) : obtainTypedArray.getDrawable(displayUniqueIdConfigIndex);
        obtainTypedArray.recycle();
        this.topRoundedDrawable = drawable;
        TypedArray obtainTypedArray2 = this.res.obtainTypedArray(R.array.config_roundedCornerBottomDrawableArray);
        Drawable drawable2 = (displayUniqueIdConfigIndex < 0 || displayUniqueIdConfigIndex >= obtainTypedArray2.length()) ? this.res.getDrawable(R.drawable.rounded_corner_bottom, null) : obtainTypedArray2.getDrawable(displayUniqueIdConfigIndex);
        obtainTypedArray2.recycle();
        this.bottomRoundedDrawable = drawable2;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final void updateDisplayUniqueId(String str, Integer num) {
        if (Intrinsics.areEqual(this.displayUniqueId, str)) {
            if (num == null || this.reloadToken == num.intValue()) {
                return;
            }
            this.reloadToken = num.intValue();
            reloadMeasures();
            return;
        }
        this.displayUniqueId = str;
        if (num != null) {
            this.reloadToken = num.intValue();
        }
        reloadRes();
        reloadMeasures();
    }
}
