package com.android.systemui.communal.ui.compose;

import android.content.Context;
import android.content.res.Configuration;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.ui.unit.Dp;
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;
import com.android.systemui.communal.util.DensityUtils;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class Dimensions {
    public static final PaddingValuesImpl ButtonPadding;
    public static final Companion Companion = new Companion(null);
    public static final float IconSize;
    public static final float SlideOffsetY;
    public final Configuration config;
    public final Context context;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getItemSpacing-D9Ej5fM, reason: not valid java name */
        public static float m1084getItemSpacingD9Ej5fM() {
            DensityUtils.Companion.getClass();
            return DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(50);
        }

        private Companion() {
        }
    }

    static {
        DensityUtils.Companion.getClass();
        ButtonPadding = PaddingKt.m121PaddingValuesYgX7TsA(DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(24), DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(16));
        IconSize = DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(40);
        SlideOffsetY = DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(30);
    }

    public Dimensions(Context context, Configuration configuration) {
        this.context = context;
        this.config = configuration;
    }

    /* renamed from: getGridTopSpacing-D9Ej5fM, reason: not valid java name */
    public final float m1083getGridTopSpacingD9Ej5fM() {
        if (this.config.orientation == 2) {
            float f = 114;
            Dp.Companion companion = Dp.Companion;
            return f;
        }
        WindowMetricsCalculator.Companion.getClass();
        WindowMetrics windowMetricsComputeCurrentWindowMetrics = WindowMetricsCalculator.Companion.getOrCreate().computeCurrentWindowMetrics(this.context);
        float fHeight = windowMetricsComputeCurrentWindowMetrics._bounds.toRect().height() / this.context.getResources().getDisplayMetrics().density;
        Dp.Companion companion2 = Dp.Companion;
        Companion.getClass();
        DensityUtils.Companion.getClass();
        return (fHeight - DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(530)) / 2;
    }
}
