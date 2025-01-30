package com.android.systemui.decor;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.FactoryTest;
import android.util.DisplayUtils;
import android.util.Size;
import android.view.RoundedCorners;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.util.DeviceType;
import java.io.PrintWriter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RoundedCornerResDelegate implements Dumpable {
    public Drawable bottomRoundedDrawable;
    public boolean displayAspectRatioChanged;
    public String displayUniqueId;
    public boolean hasBottom;
    public boolean hasTop;
    public int reloadToken;
    public final Resources res;
    public Drawable topRoundedDrawable;
    public Integer tuningSizeFactor;
    public Size topRoundedSize = new Size(0, 0);
    public Size bottomRoundedSize = new Size(0, 0);
    public float physicalPixelDisplaySizeRatio = 1.0f;

    public RoundedCornerResDelegate(Resources resources, String str) {
        this.res = resources;
        this.displayUniqueId = str;
        reloadRes();
        reloadMeasures();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("RoundedCornerResDelegate state:");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  hasTop=", this.hasTop, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  hasBottom=", this.hasBottom, printWriter);
        printWriter.println(SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("  topRoundedSize(w,h)=(", this.topRoundedSize.getWidth(), ",", this.topRoundedSize.getHeight(), ")"));
        printWriter.println(SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("  bottomRoundedSize(w,h)=(", this.bottomRoundedSize.getWidth(), ",", this.bottomRoundedSize.getHeight(), ")"));
        printWriter.println("  physicalPixelDisplaySizeRatio=" + this.physicalPixelDisplaySizeRatio);
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
        Integer num = this.tuningSizeFactor;
        if (num != null) {
            int intValue = num.intValue();
            if (intValue <= 0) {
                return;
            }
            int i = (int) (intValue * this.res.getDisplayMetrics().density);
            if (this.topRoundedSize.getWidth() > 0) {
                this.topRoundedSize = new Size(i, i);
            }
            if (this.bottomRoundedSize.getWidth() > 0) {
                this.bottomRoundedSize = new Size(i, i);
            }
        }
        if (!(this.physicalPixelDisplaySizeRatio == 1.0f)) {
            if (this.topRoundedSize.getWidth() != 0) {
                this.topRoundedSize = new Size((int) ((this.physicalPixelDisplaySizeRatio * this.topRoundedSize.getWidth()) + 0.5f), (int) ((this.physicalPixelDisplaySizeRatio * this.topRoundedSize.getHeight()) + 0.5f));
            }
            if (this.bottomRoundedSize.getWidth() != 0) {
                this.bottomRoundedSize = new Size((int) ((this.physicalPixelDisplaySizeRatio * this.bottomRoundedSize.getWidth()) + 0.5f), (int) ((this.physicalPixelDisplaySizeRatio * this.bottomRoundedSize.getHeight()) + 0.5f));
            }
        }
        if (this.displayAspectRatioChanged) {
            this.topRoundedSize = new Size(0, 0);
            this.bottomRoundedSize = new Size(0, 0);
        }
    }

    public final void reloadRes() {
        String str = this.displayUniqueId;
        Resources resources = this.res;
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        boolean z = RoundedCorners.getRoundedCornerRadius(resources, this.displayUniqueId) > 0;
        int i = DeviceType.supportTablet;
        this.hasTop = !FactoryTest.isFactoryBinary() && z;
        this.hasBottom = !FactoryTest.isFactoryBinary() && z;
        TypedArray obtainTypedArray = resources.obtainTypedArray(R.array.config_roundedCornerTopDrawableArray);
        Drawable drawable = (displayUniqueIdConfigIndex < 0 || displayUniqueIdConfigIndex >= obtainTypedArray.length()) ? resources.getDrawable(R.drawable.rounded_corner_top, null) : obtainTypedArray.getDrawable(displayUniqueIdConfigIndex);
        obtainTypedArray.recycle();
        this.topRoundedDrawable = drawable;
        TypedArray obtainTypedArray2 = resources.obtainTypedArray(R.array.config_roundedCornerBottomDrawableArray);
        Drawable drawable2 = (displayUniqueIdConfigIndex < 0 || displayUniqueIdConfigIndex >= obtainTypedArray2.length()) ? resources.getDrawable(R.drawable.rounded_corner_bottom, null) : obtainTypedArray2.getDrawable(displayUniqueIdConfigIndex);
        obtainTypedArray2.recycle();
        this.bottomRoundedDrawable = drawable2;
    }

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
