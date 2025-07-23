package com.android.systemui.qs.panels.data.repository;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.ui.data.repository.ConfigurationRepository;
import com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSColumnsRepository {
    public final ChannelFlowTransformLatest columns;
    public final int defaultColumns;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 dualShadeColumns;
    public final Resources resources;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 splitShadeColumns;

    public QSColumnsRepository(Resources resources, ConfigurationRepository configurationRepository) {
        this.resources = resources;
        this.splitShadeColumns = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Integer.valueOf(resources.getInteger(R.integer.quick_settings_split_shade_num_columns)));
        this.dualShadeColumns = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Integer.valueOf(resources.getInteger(R.integer.quick_settings_dual_shade_num_columns)));
        this.columns = FlowKt.mapLatest(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), ((ConfigurationRepositoryImpl) configurationRepository).onConfigurationChange), new QSColumnsRepository$columns$1(this, null));
        this.defaultColumns = resources.getInteger(R.integer.quick_settings_infinite_grid_num_columns);
    }
}
