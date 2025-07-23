package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.IncrementalKt;
import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.kairos.ToColdFlowKt;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos;
import kotlin.Lazy;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MobileIconsInteractorKairosAdapter$$ExternalSyntheticLambda4 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MobileIconsInteractorKairosAdapter$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SafeFlow safeFlow = new SafeFlow(new MobileIconInteractorKairosAdapterKt$MobileIconInteractorKairosAdapter$1$dummyBooleanFlow$1(null));
                SafeFlow safeFlow2 = new SafeFlow(new MobileIconInteractorKairosAdapterKt$MobileIconInteractorKairosAdapter$1$dummyIntFlow$1(null));
                SafeFlow safeFlow3 = new SafeFlow(new MobileIconInteractorKairosAdapterKt$MobileIconInteractorKairosAdapter$1$dummyMobileServiceStateFlow$1(null));
                SafeFlow safeFlow4 = new SafeFlow(new MobileIconInteractorKairosAdapterKt$MobileIconInteractorKairosAdapter$1$dummyDisabledDataIconModelFlow$1(null));
                SafeFlow safeFlow5 = new SafeFlow(new MobileIconInteractorKairosAdapterKt$MobileIconInteractorKairosAdapter$1$dummyImsRegStatelFlow$1(null));
                MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl = (MobileIconInteractorKairosImpl) ((MobileIconInteractorKairos) this.f$0);
                TableLogBuffer tableLogBuffer = mobileIconInteractorKairosImpl.connectionRepository.getTableLogBuffer();
                MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos = mobileIconInteractorKairosImpl.connectionRepository;
                BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                Flow coldConflatedFlow = ToColdFlowKt.toColdConflatedFlow(mobileConnectionRepositoryKairos.getDataActivityDirection(), (KairosNetwork) buildScopeImpl.kairosNetwork$delegate.getValue());
                Lazy lazy = buildScopeImpl.kairosNetwork$delegate;
                return new MobileIconInteractorKairosAdapter(tableLogBuffer, coldConflatedFlow, ToColdFlowKt.toColdConflatedFlow(mobileIconInteractorKairosImpl.mobileIsDefault, (KairosNetwork) lazy.getValue()), buildScopeImpl.toStateFlow(mobileIconInteractorKairosImpl.isDataConnected), buildScopeImpl.toStateFlow(mobileConnectionRepositoryKairos.isInService()), buildScopeImpl.toStateFlow(mobileConnectionRepositoryKairos.isEmergencyOnly()), buildScopeImpl.toStateFlow(mobileIconInteractorKairosImpl.isDataEnabled), buildScopeImpl.toStateFlow(mobileIconInteractorKairosImpl.alwaysShowDataRatIcon), buildScopeImpl.toStateFlow(mobileIconInteractorKairosImpl.signalLevelIcon), buildScopeImpl.toStateFlow(mobileIconInteractorKairosImpl.networkTypeIconGroup), buildScopeImpl.toStateFlow(mobileIconInteractorKairosImpl.showSliceAttribution), buildScopeImpl.toStateFlow(mobileConnectionRepositoryKairos.isNonTerrestrial()), buildScopeImpl.toStateFlow(mobileIconInteractorKairosImpl.networkName), buildScopeImpl.toStateFlow(mobileIconInteractorKairosImpl.carrierName), buildScopeImpl.toStateFlow(mobileIconInteractorKairosImpl.isSingleCarrier), buildScopeImpl.toStateFlow(mobileIconInteractorKairosImpl.isRoaming), ToColdFlowKt.toColdConflatedFlow(mobileIconInteractorKairosImpl.isForceHidden, (KairosNetwork) lazy.getValue()), buildScopeImpl.toStateFlow(mobileConnectionRepositoryKairos.isAllowedDuringAirplaneMode()), buildScopeImpl.toStateFlow(mobileConnectionRepositoryKairos.getCarrierNetworkChangeActive()), safeFlow2, safeFlow, safeFlow, safeFlow3, safeFlow4, safeFlow2, safeFlow, safeFlow2, safeFlow5, safeFlow, 0);
            default:
                return BuildScope.DefaultImpls.applyLatestSpecForKey$default((BuildScope) obj, IncrementalKt.mapValues(((MobileIconsInteractorKairosImpl) ((MobileIconsInteractorKairosAdapter) this.f$0).kairosInteractor).icons, new MobileIconsInteractorKairosAdapter$$ExternalSyntheticLambda1(1)));
        }
    }
}
