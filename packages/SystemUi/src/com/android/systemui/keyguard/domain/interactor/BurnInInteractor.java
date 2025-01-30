package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.common.ui.data.repository.ConfigurationRepository;
import com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl;
import com.android.systemui.doze.util.BurnInHelperKt;
import com.android.systemui.doze.util.BurnInHelperWrapper;
import com.android.systemui.util.time.SystemClock;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BurnInInteractor {
    public final StateFlowImpl _dozeTimeTick;
    public final BurnInHelperWrapper burnInHelperWrapper;
    public final ConfigurationRepository configurationRepository;
    public final Context context;
    public final ReadonlyStateFlow dozeTimeTick;
    public final CoroutineScope scope;
    public final SystemClock systemClock;

    public BurnInInteractor(Context context, BurnInHelperWrapper burnInHelperWrapper, CoroutineScope coroutineScope, ConfigurationRepository configurationRepository, SystemClock systemClock) {
        this.context = context;
        this.burnInHelperWrapper = burnInHelperWrapper;
        this.scope = coroutineScope;
        this.configurationRepository = configurationRepository;
        this.systemClock = systemClock;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(0L);
        this._dozeTimeTick = MutableStateFlow;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow);
        this.dozeTimeTick = asStateFlow;
        burnInOffsetDefinedInPixels(R.dimen.udfps_burn_in_offset_x, true);
        burnInOffsetDefinedInPixels(R.dimen.udfps_burn_in_offset_y, false);
        ChannelFlowTransformLatest mapLatest = FlowKt.mapLatest(asStateFlow, new BurnInInteractor$udfpsBurnInProgress$1(this, null));
        SharingStarted.Companion.getClass();
        FlowKt.stateIn(mapLatest, coroutineScope, SharingStarted.Companion.Lazily, Float.valueOf(BurnInHelperKt.getBurnInProgressOffset()));
    }

    public final void burnInOffsetDefinedInPixels(int i, boolean z) {
        ConfigurationRepositoryImpl configurationRepositoryImpl = (ConfigurationRepositoryImpl) this.configurationRepository;
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(configurationRepositoryImpl.scaleForResolution, new C1647xcc7df8a1(null, this, i, z));
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion);
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(i);
        float resolutionScale = configurationRepositoryImpl.getResolutionScale();
        this.burnInHelperWrapper.getClass();
        FlowKt.stateIn(transformLatest, this.scope, WhileSubscribed$default, Integer.valueOf((int) (BurnInHelperKt.getBurnInOffset(dimensionPixelSize, z) * resolutionScale)));
    }
}
