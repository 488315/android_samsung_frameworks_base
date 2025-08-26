package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.content.res.Resources;
import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.BuildScope$DefaultImpls$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.BuildScope$DefaultImpls$$ExternalSyntheticLambda10;
import com.android.systemui.kairos.BuildScope$DefaultImpls$$ExternalSyntheticLambda7;
import com.android.systemui.kairos.BuildScopeKt;
import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.EventsInit;
import com.android.systemui.kairos.EventsKt;
import com.android.systemui.kairos.FilterKt;
import com.android.systemui.kairos.FilterKt$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.IncrementalInit;
import com.android.systemui.kairos.IncrementalKt;
import com.android.systemui.kairos.MergeKt;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.StateKt$$ExternalSyntheticLambda8;
import com.android.systemui.kairos.SwitchKt;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.StateScopeImpl;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda5;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Pair;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda8 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MobileConnectionsRepositoryKairosImpl f$0;

    public /* synthetic */ MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda8(MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = mobileConnectionsRepositoryKairosImpl;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
        int i = 9;
        MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl = this.f$0;
        BuildScope buildScope = (BuildScope) obj;
        switch (this.$r8$classId) {
            case 0:
                int i2 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                Flow flowFlowOn = FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new MobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1(mobileConnectionsRepositoryKairosImpl, null)), mobileConnectionsRepositoryKairosImpl.mainDispatcher);
                Boolean bool = Boolean.FALSE;
                BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                buildScopeImpl.getClass();
                StateInit stateInitHoldState = buildScopeImpl.stateScope.holdState(BuildScope.DefaultImpls.toEvents(buildScopeImpl, flowFlowOn), bool);
                DiffableKt.logBooleanDiffsForTable(buildScopeImpl, stateInitHoldState, mobileConnectionsRepositoryKairosImpl.tableLogger, "Repo", "isAnySimSecure");
                return stateInitHoldState;
            case 1:
                int i3 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                Flow flowFlowOn2 = FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new MobileConnectionsRepositoryKairosImpl$mobileSubscriptionsChangeEvent$1$1(mobileConnectionsRepositoryKairosImpl, null)), mobileConnectionsRepositoryKairosImpl.bgDispatcher);
                BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) buildScope;
                buildScopeImpl2.getClass();
                return BuildScope.DefaultImpls.toEvents(buildScopeImpl2, flowFlowOn2);
            case 2:
                StateInit stateInit = mobileConnectionsRepositoryKairosImpl.activeMobileDataSubscriptionId;
                EventsInit eventsInitMapNotNull = EventsKt.mapNotNull(EventsKt.map(StateKt.getChanges(stateInit), new StateKt$$ExternalSyntheticLambda8(stateInit, 0)), new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda10(i));
                MobileConnectionsRepositoryKairosImpl$activeSubChangedInGroupEvent$1$2 mobileConnectionsRepositoryKairosImpl$activeSubChangedInGroupEvent$1$2 = new MobileConnectionsRepositoryKairosImpl$activeSubChangedInGroupEvent$1$2(mobileConnectionsRepositoryKairosImpl, null);
                BuildScopeImpl buildScopeImpl3 = (BuildScopeImpl) buildScope;
                buildScopeImpl3.getClass();
                Events events = (Events) buildScopeImpl3.applyLatestSpec(EventsKt.mapCheap(eventsInitMapNotNull, new BuildScope$DefaultImpls$$ExternalSyntheticLambda7(2, new BuildScope$DefaultImpls$$ExternalSyntheticLambda7(0, mobileConnectionsRepositoryKairosImpl$activeSubChangedInGroupEvent$1$2))), new BuildScope$DefaultImpls$$ExternalSyntheticLambda10()).getFirst();
                StateScopeImpl stateScopeImpl = buildScopeImpl3.stateScope;
                stateScopeImpl.getClass();
                return FilterKt.filterPresent(EventsKt.mapCheap(SwitchKt.switchEvents(stateScopeImpl.holdState(events, EventsKt.emptyEvents)), new FilterKt$$ExternalSyntheticLambda0()));
            case 3:
                int i4 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                Flow flowFlowOn3 = FlowKt.flowOn(FlowKt.callbackFlow(new MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1(mobileConnectionsRepositoryKairosImpl, null)), mobileConnectionsRepositoryKairosImpl.bgDispatcher);
                Pair pair = new Pair(null, EmptySet.INSTANCE);
                BuildScopeImpl buildScopeImpl4 = (BuildScopeImpl) buildScope;
                buildScopeImpl4.getClass();
                return BuildScope.DefaultImpls.scanToState(buildScopeImpl4, flowFlowOn3, pair, new BuildScope$DefaultImpls$$ExternalSyntheticLambda0(3));
            case 4:
                int i5 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                return BuildScopeKt.asyncEvent(buildScope, new MobileConnectionsRepositoryKairosImpl$isDeviceEmergencyCallCapable$1$1$1(mobileConnectionsRepositoryKairosImpl, null));
            case 5:
                int i6 = MobileConnectionsRepositoryKairosImpl.$r8$clinit;
                return BuildScopeKt.asyncEvent(buildScope, new MobileConnectionsRepositoryKairosImpl$subscriptions$1$1$1(mobileConnectionsRepositoryKairosImpl, null));
            case 6:
                StateInit stateInitHoldState2 = ((BuildScopeImpl) buildScope).stateScope.holdState(SwitchKt.switchEvents(BuildScopeKt.rebuildOn(buildScope, mobileConnectionsRepositoryKairosImpl.serviceStateChangedEvent, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda8(mobileConnectionsRepositoryKairosImpl, 4))), Boolean.FALSE);
                DiffableKt.logBooleanDiffsForTable(buildScope, stateInitHoldState2, mobileConnectionsRepositoryKairosImpl.tableLogger, "Repo", "deviceEmergencyOnly");
                return stateInitHoldState2;
            case 7:
                BuildScopeImpl buildScopeImpl5 = (BuildScopeImpl) buildScope;
                StateInit stateInitHoldState3 = buildScopeImpl5.stateScope.holdState(SwitchKt.switchEvents(BuildScopeKt.rebuildOn(buildScope, MergeKt.mergeLeft(mobileConnectionsRepositoryKairosImpl.mobileSubscriptionsChangeEvent, StateKt.getChanges(mobileConnectionsRepositoryKairosImpl.carrierMergedSubId)), new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda8(mobileConnectionsRepositoryKairosImpl, 5))), EmptyList.INSTANCE);
                Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                ref$BooleanRef.element = true;
                buildScopeImpl5.observe(stateInitHoldState3, new DiffableKt$$ExternalSyntheticLambda5(mobileConnectionsRepositoryKairosImpl.tableLogger, "Repo", "subscriptions", ref$BooleanRef, 2));
                return stateInitHoldState3;
            case 8:
                IncrementalInit incrementalInitApplyLatestSpecForKey$default = BuildScope.DefaultImpls.applyLatestSpecForKey$default(buildScope, IncrementalKt.mapValues(IncrementalKt.asIncremental(mobileConnectionsRepositoryKairosImpl.subscriptionsById), new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda4(mobileConnectionsRepositoryKairosImpl, 2)));
                ((BuildScopeImpl) buildScope).observe(incrementalInitApplyLatestSpecForKey$default, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda4(mobileConnectionsRepositoryKairosImpl, 3));
                return incrementalInitApplyLatestSpecForKey$default;
            case 9:
                MobileMappings.Config config = MobileMappings.Config.readConfig(mobileConnectionsRepositoryKairosImpl.context);
                BuildScopeKt.effect$default(buildScope, new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda2(mobileConnectionsRepositoryKairosImpl, config));
                return config;
            default:
                return BuildScopeKt.rebuildOn(buildScope, MergeKt.mergeLeft(StateKt.getChanges(mobileConnectionsRepositoryKairosImpl.defaultDataSubId), mobileConnectionsRepositoryKairosImpl.carrierConfigChangedEvent), new MobileConnectionsRepositoryKairosImpl$$ExternalSyntheticLambda8(mobileConnectionsRepositoryKairosImpl, i));
        }
    }
}
