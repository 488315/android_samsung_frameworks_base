package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class IndicatorScaleGardener implements Dumpable {
    public final float baseSmallestWidth;
    public ScaleModel latestScaleModel;
    public final boolean logEnabled = DeviceType.isEngOrUTBinary();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ScaleModel {
        public final int currentSmallestWidth;
        public final int displayDeviceType;
        public final int iconSize;
        public final float ratio;

        public ScaleModel(float f, int i, int i2, int i3) {
            this.ratio = f;
            this.iconSize = i;
            this.displayDeviceType = i2;
            this.currentSmallestWidth = i3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ScaleModel)) {
                return false;
            }
            ScaleModel scaleModel = (ScaleModel) obj;
            return Float.compare(this.ratio, scaleModel.ratio) == 0 && this.iconSize == scaleModel.iconSize && this.displayDeviceType == scaleModel.displayDeviceType && this.currentSmallestWidth == scaleModel.currentSmallestWidth;
        }

        public final int hashCode() {
            return Integer.hashCode(this.currentSmallestWidth) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.displayDeviceType, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.iconSize, Float.hashCode(this.ratio) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ScaleModel(ratio=");
            sb.append(this.ratio);
            sb.append(", iconSize=");
            sb.append(this.iconSize);
            sb.append(", displayDeviceType=");
            sb.append(this.displayDeviceType);
            sb.append(", currentSmallestWidth=");
            return Anchor$$ExternalSyntheticOutline0.m(this.currentSmallestWidth, ")", sb);
        }
    }

    public IndicatorScaleGardener(Context context, DumpManager dumpManager) {
        this.baseSmallestWidth = context.getResources().getInteger(R.integer.status_bar_scale_base_smallest_width);
        this.latestScaleModel = new ScaleModel(1.0f, context.getResources().getDimensionPixelSize(17106294), context.getResources().getConfiguration().semDisplayDeviceType, context.getResources().getConfiguration().smallestScreenWidthDp);
        dumpManager.registerNormalDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("   baseSmallestWidth=" + this.baseSmallestWidth);
        printWriter.println("   " + this.latestScaleModel);
        printWriter.println("   Display device type (-1: Undefined, 0: Main, 5: Sub)");
    }

    public final ScaleModel getLatestScaleModel(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        int i = configuration.smallestScreenWidthDp;
        float f = 1.0f;
        if (!DeviceState.isTablet() && (!BasicRune.BASIC_FOLDABLE_TYPE_FOLD || configuration.semDisplayDeviceType != 0)) {
            f = Math.min(i / this.baseSmallestWidth, 1.0f);
        }
        ScaleModel scaleModel = new ScaleModel(f, (int) (context.getResources().getDimensionPixelSize(17106294) * f), configuration.semDisplayDeviceType, i);
        if (!scaleModel.equals(this.latestScaleModel)) {
            if (this.logEnabled) {
                Log.d("IndicatorScaleGardener", "Scale model changed from=" + this.latestScaleModel + " to " + scaleModel);
            }
            this.latestScaleModel = scaleModel;
        }
        return this.latestScaleModel;
    }
}
