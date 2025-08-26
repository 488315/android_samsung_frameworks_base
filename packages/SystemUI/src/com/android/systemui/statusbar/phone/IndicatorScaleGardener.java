package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.util.Log;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.DeviceType;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes3.dex */
public final class IndicatorScaleGardener implements Dumpable {
    public final Context context;
    public ScaleModel latestScaleModel;
    public final boolean logEnabled = DeviceType.isEngOrUTBinary();
    public float baseSmallestWidth = 411.0f;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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
            StringBuilder sb = new StringBuilder("(");
            int i = StringCompanionObject.$r8$clinit;
            sb.append("ratio:".concat(String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(this.ratio)}, 1))));
            sb.append(", iconSize:" + this.iconSize);
            sb.append(", displayType:" + this.displayDeviceType);
            sb.append(", currentSW:" + this.currentSmallestWidth);
            sb.append(")");
            return sb.toString();
        }
    }

    static {
        new Companion(null);
    }

    public IndicatorScaleGardener(Context context, DumpManager dumpManager) {
        this.context = context;
        this.latestScaleModel = new ScaleModel(1.0f, context.getResources().getDimensionPixelSize(17106387), context.getResources().getConfiguration().semDisplayDeviceType, context.getResources().getConfiguration().smallestScreenWidthDp);
        dumpManager.registerNormalDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "IndicatorScaleGardener ", getLogText());
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ScaleModel getLatestScaleModel(Context context) {
        float integer;
        Configuration configuration = context.getResources().getConfiguration();
        int i = configuration.smallestScreenWidthDp;
        Resources resources = context.getResources();
        if (resources != null) {
            integer = (BasicRune.BASIC_FOLDABLE_TYPE_FOLD && this.context.getResources().getConfiguration().semDisplayDeviceType == 5) ? resources.getInteger(R.integer.status_bar_scale_base_smallest_width_for_fold_cover) : resources.getInteger(R.integer.status_bar_scale_base_smallest_width);
        } else {
            integer = 411.0f;
        }
        this.baseSmallestWidth = integer;
        float fMax = i / integer;
        if (DeviceType.isTablet()) {
            fMax = 1.0f;
        } else {
            String str = SystemProperties.get("ro.product.name", "");
            if ((str.startsWith("q6q") || str.startsWith("v6q")) && this.context.getResources().getConfiguration().semDisplayDeviceType == 0) {
                float fMin = Math.min(fMax, 1.0f);
                fMax = ((Math.max(fMax, 1.0f) - fMin) * 0.5f) + fMin;
            } else if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD && this.context.getResources().getConfiguration().semDisplayDeviceType == 0) {
            }
        }
        float fCoerceIn = RangesKt___RangesKt.coerceIn(fMax, 0.75f, 1.0f);
        ScaleModel scaleModel = new ScaleModel(fCoerceIn, (int) (context.getResources().getDimensionPixelSize(17106387) * fCoerceIn), configuration.semDisplayDeviceType, i);
        if (!scaleModel.equals(this.latestScaleModel)) {
            this.latestScaleModel = scaleModel;
            if (this.logEnabled) {
                Log.d("IndicatorScaleGardener", getLogText());
            }
        }
        return this.latestScaleModel;
    }

    public final String getLogText() throws Resources.NotFoundException {
        StringBuilder sb = new StringBuilder(" model" + this.latestScaleModel);
        sb.append(", baseSmallestWidth:" + this.baseSmallestWidth + "}");
        sb.append(", 24dp:" + this.context.getResources().getDimensionPixelSize(R.dimen.status_bar_default_height_24dp) + "px");
        return sb.toString();
    }
}
