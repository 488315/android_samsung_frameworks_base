package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.shared.model.ClockSizeSetting;
import com.android.systemui.statusbar.ui.SystemBarUtilsProxy;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class KeyguardPreviewSmartspaceViewModel {
    public final StateFlowImpl overrideClockSize;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 previewingClockSize;
    public final KeyguardPreviewSmartspaceViewModel$special$$inlined$map$1 shouldHideSmartspace;
    public final SystemBarUtilsProxy systemBarUtils;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClockSizeSetting.values().length];
            try {
                iArr[ClockSizeSetting.DYNAMIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClockSizeSetting.SMALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public KeyguardPreviewSmartspaceViewModel(KeyguardClockInteractor keyguardClockInteractor, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, KeyguardClockViewModel keyguardClockViewModel, SystemBarUtilsProxy systemBarUtilsProxy) {
        this.systemBarUtils = systemBarUtilsProxy;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.overrideClockSize = stateFlowImplMutableStateFlow;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlowImplMutableStateFlow, keyguardClockInteractor.selectedClockSize, new KeyguardPreviewSmartspaceViewModel$previewingClockSize$1(null));
        this.previewingClockSize = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.shouldHideSmartspace = new KeyguardPreviewSmartspaceViewModel$special$$inlined$map$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, keyguardClockInteractor.currentClockId, KeyguardPreviewSmartspaceViewModel$shouldHideSmartspace$3.INSTANCE));
    }
}
