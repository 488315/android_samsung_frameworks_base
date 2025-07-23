package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.DeviceType;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class IndicatorScaleGardener implements Dumpable {
    public final Context context;
    public ScaleModel latestScaleModel;
    public final boolean logEnabled = DeviceType.isEngOrUTBinary();
    public float baseSmallestWidth = 411.0f;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            return Integer.hashCode(this.currentSmallestWidth) + ReorderTile$$ExternalSyntheticOutline0.m(this.displayDeviceType, ReorderTile$$ExternalSyntheticOutline0.m(this.iconSize, Float.hashCode(this.ratio) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ScaleModel(ratio=");
            sb.append(this.ratio);
            sb.append(", iconSize=");
            sb.append(this.iconSize);
            sb.append(", displayDeviceType=");
            sb.append(this.displayDeviceType);
            sb.append(", currentSmallestWidth=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.currentSmallestWidth, ")", sb);
        }
    }

    public IndicatorScaleGardener(Context context, DumpManager dumpManager) {
        this.context = context;
        this.latestScaleModel = new ScaleModel(1.0f, context.getResources().getDimensionPixelSize(17106386), context.getResources().getConfiguration().semDisplayDeviceType, context.getResources().getConfiguration().smallestScreenWidthDp);
        dumpManager.registerNormalDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "IndicatorScaleGardener ", getLogText());
    }

    public final ScaleModel getLatestScaleModel(Context context) {
        float f;
        Configuration configuration = context.getResources().getConfiguration();
        int i = configuration.smallestScreenWidthDp;
        Resources resources = context.getResources();
        if (resources != null) {
            f = (BasicRune.BASIC_FOLDABLE_TYPE_FOLD && this.context.getResources().getConfiguration().semDisplayDeviceType == 5) ? resources.getInteger(R.integer.status_bar_scale_base_smallest_width_for_fold_cover) : resources.getInteger(R.integer.status_bar_scale_base_smallest_width);
        } else {
            f = 411.0f;
        }
        this.baseSmallestWidth = f;
        float min = (DeviceType.isTablet() || (BasicRune.BASIC_FOLDABLE_TYPE_FOLD && this.context.getResources().getConfiguration().semDisplayDeviceType == 0)) ? 1.0f : Math.min(i / this.baseSmallestWidth, 1.3f);
        ScaleModel scaleModel = new ScaleModel(min, (int) (context.getResources().getDimensionPixelSize(17106386) * min), configuration.semDisplayDeviceType, i);
        if (!scaleModel.equals(this.latestScaleModel)) {
            this.latestScaleModel = scaleModel;
            if (this.logEnabled) {
                Log.d("IndicatorScaleGardener", getLogText());
            }
        }
        return this.latestScaleModel;
    }

    public final String getLogText() {
        StringBuilder sb = new StringBuilder(" model:" + this.latestScaleModel);
        sb.append(", baseSmallestWidth:" + this.baseSmallestWidth);
        return sb.toString();
    }
}
