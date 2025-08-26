package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.os.ParcelUuid;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.CombineKt;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda5;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionModel;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda11 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MobileIconsInteractorKairosImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda11(MobileIconsInteractorKairosImpl mobileIconsInteractorKairosImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = mobileIconsInteractorKairosImpl;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Object obj2 = this.f$1;
        final MobileIconsInteractorKairosImpl mobileIconsInteractorKairosImpl = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                State activeMobileDataSubscriptionId = mobileIconsInteractorKairosImpl.mobileConnectionsRepo.getActiveMobileDataSubscriptionId();
                ReadonlyStateFlow readonlyStateFlow = ((ConnectivityRepositoryImpl) ((ConnectivityRepository) obj2)).vcnSubId;
                BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                buildScopeImpl.getClass();
                StateInit stateInitCombine = CombineKt.combine(mobileIconsInteractorKairosImpl.subscriptionsBasedFilteredSubs, activeMobileDataSubscriptionId, BuildScope.DefaultImpls.toState(buildScopeImpl, readonlyStateFlow), new Function4() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl$$ExternalSyntheticLambda22
                    @Override // kotlin.jvm.functions.Function4
                    public final Object invoke(Object obj3, Object obj4, Object obj5, Object obj6) {
                        boolean z;
                        List list = (List) obj4;
                        Integer num = (Integer) obj5;
                        Integer num2 = (Integer) obj6;
                        int i = MobileIconsInteractorKairosImpl.$r8$clinit;
                        MobileIconsInteractorKairosImpl mobileIconsInteractorKairosImpl2 = mobileIconsInteractorKairosImpl;
                        mobileIconsInteractorKairosImpl2.getClass();
                        if (list.size() == 2) {
                            SubscriptionModel subscriptionModel = (SubscriptionModel) list.get(0);
                            SubscriptionModel subscriptionModel2 = (SubscriptionModel) list.get(1);
                            ParcelUuid parcelUuid = subscriptionModel.groupUuid;
                            if (parcelUuid != null && Intrinsics.areEqual(parcelUuid, subscriptionModel2.groupUuid) && ((z = subscriptionModel.isOpportunistic) || subscriptionModel2.isOpportunistic)) {
                                if (mobileIconsInteractorKairosImpl2.carrierConfigTracker.getAlwaysShowPrimarySignalBarInOpportunisticNetworkDefault()) {
                                    return z ? Collections.singletonList(subscriptionModel2) : Collections.singletonList(subscriptionModel);
                                }
                                if (num2 != null) {
                                    num = num2;
                                }
                                if (num != null) {
                                    if (subscriptionModel.subscriptionId == num.intValue()) {
                                        return Collections.singletonList(subscriptionModel);
                                    }
                                }
                                return Collections.singletonList(subscriptionModel2);
                            }
                        }
                        return list;
                    }
                });
                Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                ref$BooleanRef.element = true;
                buildScopeImpl.observe(stateInitCombine, new DiffableKt$$ExternalSyntheticLambda5(mobileIconsInteractorKairosImpl.tableLogger, "Intr", "filteredSubscriptions", ref$BooleanRef, 2));
                return stateInitCombine;
            default:
                int i = MobileIconsInteractorKairosImpl.$r8$clinit;
                MobileIconsInteractorKairosImpl$mobileConnection$1 mobileIconsInteractorKairosImpl$mobileConnection$1 = new MobileIconsInteractorKairosImpl$mobileConnection$1(mobileIconsInteractorKairosImpl, (MobileConnectionRepositoryKairos) obj2);
                MobileIconsInteractorKairosImpl mobileIconsInteractorKairosImpl2 = mobileIconsInteractorKairosImpl$mobileConnection$1.this$0;
                StateInit stateInit = mobileIconsInteractorKairosImpl2.activeDataConnectionHasDataEnabled;
                MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos = mobileIconsInteractorKairosImpl2.mobileConnectionsRepo;
                MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl = new MobileIconInteractorKairosImpl(stateInit, mobileIconsInteractorKairosImpl2.alwaysShowDataRatIcon, mobileIconsInteractorKairosImpl2.alwaysUseCdmaLevel, mobileIconsInteractorKairosImpl2.isSingleCarrier, mobileIconsInteractorKairosImpl2.mobileIsDefault, mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping(), mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup(), mobileIconsInteractorKairosImpl2.isDefaultConnectionFailed, mobileIconsInteractorKairosImpl2.isForceHidden, mobileIconsInteractorKairosImpl$mobileConnection$1.$repo, mobileIconsInteractorKairosImpl2.context, null, 2048, null);
                mobileIconInteractorKairosImpl.activate((BuildScope) obj);
                return mobileIconInteractorKairosImpl;
        }
    }
}
