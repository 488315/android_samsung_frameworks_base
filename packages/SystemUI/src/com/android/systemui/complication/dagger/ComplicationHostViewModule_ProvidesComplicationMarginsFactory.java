package com.android.systemui.complication.dagger;

import android.content.Context;
import android.content.res.Resources;
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;
import com.android.systemui.R;
import com.android.systemui.communal.util.WindowSizeUtils;
import com.android.systemui.complication.ComplicationLayoutEngine;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComplicationHostViewModule_ProvidesComplicationMarginsFactory implements Provider {
    public final Provider contextProvider;
    public final Provider resourcesProvider;

    public ComplicationHostViewModule_ProvidesComplicationMarginsFactory(Provider provider, Provider provider2) {
        this.resourcesProvider = provider;
        this.contextProvider = provider2;
    }

    public static ComplicationLayoutEngine.Margins providesComplicationMargins(Context context, Resources resources) {
        WindowSizeUtils windowSizeUtils = WindowSizeUtils.INSTANCE;
        WindowMetricsCalculator.Companion.getClass();
        WindowMetrics computeCurrentWindowMetrics = WindowMetricsCalculator.Companion.getOrCreate().computeCurrentWindowMetrics(context);
        return ((float) computeCurrentWindowMetrics._bounds.toRect().width()) / computeCurrentWindowMetrics.density < WindowSizeUtils.COMPACT_WIDTH ? new ComplicationLayoutEngine.Margins(resources.getDimensionPixelSize(R.dimen.dream_overlay_container_small_padding_start), resources.getDimensionPixelSize(R.dimen.dream_overlay_container_small_padding_top), resources.getDimensionPixelSize(R.dimen.dream_overlay_container_small_padding_end), resources.getDimensionPixelSize(R.dimen.dream_overlay_container_small_padding_bottom)) : new ComplicationLayoutEngine.Margins(resources.getDimensionPixelSize(R.dimen.dream_overlay_container_padding_start), resources.getDimensionPixelSize(R.dimen.dream_overlay_container_padding_top), resources.getDimensionPixelSize(R.dimen.dream_overlay_container_padding_end), resources.getDimensionPixelSize(R.dimen.dream_overlay_container_padding_bottom));
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesComplicationMargins((Context) this.contextProvider.get(), (Resources) this.resourcesProvider.get());
    }
}
