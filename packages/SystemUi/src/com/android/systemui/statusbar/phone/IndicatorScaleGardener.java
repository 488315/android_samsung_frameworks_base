package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.Log;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import java.io.PrintWriter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class IndicatorScaleGardener implements Dumpable {
    public final int ICON_MAX_HEIGHT;
    public final float baseSmallestWidth;
    public ScaleModel latestScaleModel;
    public final boolean logEnabled = DeviceType.isEngOrUTBinary();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            return Integer.hashCode(this.currentSmallestWidth) + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.displayDeviceType, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.iconSize, Float.hashCode(this.ratio) * 31, 31), 31);
        }

        public final String toString() {
            return "ScaleModel(ratio=" + this.ratio + ", iconSize=" + this.iconSize + ", displayDeviceType=" + this.displayDeviceType + ", currentSmallestWidth=" + this.currentSmallestWidth + ")";
        }
    }

    public IndicatorScaleGardener(Context context, DumpManager dumpManager) {
        this.ICON_MAX_HEIGHT = context.getResources().getInteger(R.integer.status_bar_maximum_icon_height_to_be_able_to_display);
        this.baseSmallestWidth = context.getResources().getInteger(R.integer.status_bar_scale_base_smallest_width);
        this.latestScaleModel = new ScaleModel(1.0f, context.getResources().getDimensionPixelSize(17106184), context.getResources().getConfiguration().semDisplayDeviceType, context.getResources().getConfiguration().smallestScreenWidthDp);
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
        Point point = DeviceState.sDisplaySize;
        float f = 1.0f;
        if (!DeviceType.isTablet()) {
            if (!BasicRune.BASIC_FOLDABLE_TYPE_FOLD || configuration.semDisplayDeviceType != 0) {
                f = Math.min(i / this.baseSmallestWidth, 1.0f);
            } else if (context.getResources().getDimensionPixelSize(17106184) > this.ICON_MAX_HEIGHT) {
                f = 0.9f;
            }
        }
        ScaleModel scaleModel = new ScaleModel(f, (int) (context.getResources().getDimensionPixelSize(17106184) * f), configuration.semDisplayDeviceType, i);
        if (!Intrinsics.areEqual(scaleModel, this.latestScaleModel)) {
            if (this.logEnabled) {
                Log.d("IndicatorScaleGardener", "Scale model changed from=" + this.latestScaleModel + " to " + scaleModel);
            }
            this.latestScaleModel = scaleModel;
        }
        return this.latestScaleModel;
    }
}
