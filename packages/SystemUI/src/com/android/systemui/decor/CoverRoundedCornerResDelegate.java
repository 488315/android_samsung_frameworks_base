package com.android.systemui.decor;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Size;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CoverRoundedCornerResDelegate implements RoundedCornerResDelegate {
    public final Size bottomRoundedSize;
    public final boolean hasTop;
    public final Drawable topRoundedDrawable;
    public final Size topRoundedSize;

    public CoverRoundedCornerResDelegate(Resources resources) {
        this.hasTop = resources.getBoolean(R.bool.config_enableCoverScreenRoundedCorner);
        Drawable drawable = resources.getDrawable(R.drawable.rounded_corner_cover, null);
        this.topRoundedDrawable = drawable;
        this.topRoundedSize = drawable != null ? new Size(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()) : new Size(0, 0);
        this.bottomRoundedSize = new Size(0, 0);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "CoverRoundedCornerResDelegate state:", "  hasRoundedCorner="), this.hasTop, printWriter);
        printWriter.println(MutableVectorKt$$ExternalSyntheticOutline0.m(this.topRoundedSize.getWidth(), this.topRoundedSize.getHeight(), "  roundedSize(w,h)=(", ",", ")"));
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final Drawable getBottomRoundedDrawable() {
        return null;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final Size getBottomRoundedSize() {
        return this.bottomRoundedSize;
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final boolean getHasBottom() {
        return false;
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
    }

    @Override // com.android.systemui.decor.RoundedCornerResDelegate
    public final void updateDisplayUniqueId(String str, Integer num) {
    }
}
