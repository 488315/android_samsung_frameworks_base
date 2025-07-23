package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.telephony.TelephonyManager;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.BuildScopeKt$$ExternalSyntheticLambda3;
import com.android.systemui.kairos.BuildScopeKt$$ExternalSyntheticLambda6;
import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.EventsInit;
import com.android.systemui.kairos.EventsKt;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.Transactional;
import com.android.systemui.kairos.TransactionalKt;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.BuildScopeImpl$$ExternalSyntheticLambda3;
import com.android.systemui.kairos.internal.StateScopeImpl;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.model.DataConnectionState;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SystemUiCarrierConfig;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import java.util.Collections;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileConnectionRepositoryKairosImpl implements MobileConnectionRepositoryKairos, KairosBuilder {
    public final /* synthetic */ KairosBuilderImpl $$delegate_0;
    public final State allowNetworkSliceIndicator;
    public final CoroutineDispatcher bgDispatcher;
    public final Events callbackEvents;
    public final State carrierId;
    public final StateInit carrierName;
    public final State carrierNetworkChangeActive;
    public final State carrierRoamingNtnActive;
    public final Transactional cdmaEnhancedRoamingIndicatorDisplayNumber;
    public final StateInit cdmaLevel;
    public final State cdmaRoaming;
    public final Context context;
    public final State dataActivityDirection;
    public final State dataConnectionState;
    public final State dataEnabled;
    public final State hasPrioritizedNetworkCapabilities;
    public final State inflateSignalStrength;
    public final StateInit isAllowedDuringAirplaneMode;
    public final StateInit isEmergencyOnly;
    public final StateInit isGsm;
    public final State isInEcmMode;
    public final StateInit isInService;
    public final StateInit isRoaming;
    public final MobileMappingsProxy mobileMappingsProxy;
    public final State networkName;
    public final NetworkRequest networkSliceRequest;
    public final StateInit numberOfLevels;
    public final StateInit operatorAlphaShort;
    public final StateInit primaryLevel;
    public final StateInit resolvedNetworkType;
    public final State satelliteLevel;
    public final int subId;
    public final TableLogBuffer tableLogBuffer;
    public final TelephonyManager telephonyManager;
    public final EventsInit telephonyPollingEvent;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        MobileConnectionRepositoryKairosImpl create(int i, TableLogBuffer tableLogBuffer, State state, NetworkNameModel networkNameModel, String str, SystemUiCarrierConfig systemUiCarrierConfig, TelephonyManager telephonyManager);
    }

    public MobileConnectionRepositoryKairosImpl(int i, Context context, State state, final NetworkNameModel networkNameModel, final String str, final ConnectivityManager connectivityManager, TelephonyManager telephonyManager, final SystemUiCarrierConfig systemUiCarrierConfig, BroadcastDispatcher broadcastDispatcher, MobileMappingsProxy mobileMappingsProxy, CoroutineDispatcher coroutineDispatcher, final MobileInputLogger mobileInputLogger, TableLogBuffer tableLogBuffer, FeatureFlagsClassic featureFlagsClassic) {
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        this.subId = i;
        this.context = context;
        this.telephonyManager = telephonyManager;
        this.mobileMappingsProxy = mobileMappingsProxy;
        this.bgDispatcher = coroutineDispatcher;
        this.tableLogBuffer = tableLogBuffer;
        if (telephonyManager.getSubscriptionId() != i) {
            throw new IllegalStateException(MutableVectorKt$$ExternalSyntheticOutline0.m(i, telephonyManager.getSubscriptionId(), "MobileRepo: TelephonyManager should be created with subId(", "). Found ", " instead."));
        }
        Events buildEvents = kairosBuilderImpl.buildEvents(new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda0(this, mobileInputLogger));
        this.callbackEvents = buildEvents;
        final int i2 = 1;
        State buildState = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ MobileConnectionRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i2) {
                    case 0:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(14)), 0);
                    case 1:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl2 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl2.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(12)), null);
                    case 2:
                        try {
                            return Integer.valueOf(this.f$0.telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber());
                        } catch (UnsupportedOperationException unused) {
                            return null;
                        }
                    case 3:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl3 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.map(mobileConnectionRepositoryKairosImpl3.telephonyPollingEvent, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(mobileConnectionRepositoryKairosImpl3, 1)), Boolean.FALSE);
                    case 4:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl4 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl4.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(13)), Boolean.valueOf(mobileConnectionRepositoryKairosImpl4.telephonyManager.isDataConnectionAllowed()));
                    case 5:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl5 = this.f$0;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                        State foldState = buildScopeImpl.stateScope.foldState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl5.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(10)), EmptySet.INSTANCE, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda33());
                        MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 = new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(11);
                        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                        stateScopeImpl.getClass();
                        return stateScopeImpl.sampleTransactionals(StateKt.map(foldState, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1, 0)));
                    case 6:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl6 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl6.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(9)), null);
                    case 7:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl7 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl7.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(20)), Boolean.FALSE);
                    case 8:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl8 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl8.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(17)), null);
                    case 9:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl9 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl9.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(15)), DataConnectionState.Disconnected);
                    case 10:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl10 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl10.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(18)), new DataActivityModel(false, false));
                    case 11:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl11 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl11.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(19)), Boolean.FALSE);
                    default:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl12 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl12.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(21)), null);
                }
            }
        });
        this.isEmergencyOnly = StateKt.map(buildState, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(3));
        final int i3 = 6;
        State buildState2 = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ MobileConnectionRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i3) {
                    case 0:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(14)), 0);
                    case 1:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl2 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl2.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(12)), null);
                    case 2:
                        try {
                            return Integer.valueOf(this.f$0.telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber());
                        } catch (UnsupportedOperationException unused) {
                            return null;
                        }
                    case 3:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl3 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.map(mobileConnectionRepositoryKairosImpl3.telephonyPollingEvent, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(mobileConnectionRepositoryKairosImpl3, 1)), Boolean.FALSE);
                    case 4:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl4 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl4.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(13)), Boolean.valueOf(mobileConnectionRepositoryKairosImpl4.telephonyManager.isDataConnectionAllowed()));
                    case 5:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl5 = this.f$0;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                        State foldState = buildScopeImpl.stateScope.foldState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl5.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(10)), EmptySet.INSTANCE, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda33());
                        MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 = new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(11);
                        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                        stateScopeImpl.getClass();
                        return stateScopeImpl.sampleTransactionals(StateKt.map(foldState, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1, 0)));
                    case 6:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl6 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl6.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(9)), null);
                    case 7:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl7 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl7.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(20)), Boolean.FALSE);
                    case 8:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl8 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl8.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(17)), null);
                    case 9:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl9 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl9.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(15)), DataConnectionState.Disconnected);
                    case 10:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl10 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl10.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(18)), new DataActivityModel(false, false));
                    case 11:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl11 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl11.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(19)), Boolean.FALSE);
                    default:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl12 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl12.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(21)), null);
                }
            }
        });
        Flags.INSTANCE.getClass();
        this.isRoaming = ((FeatureFlagsClassicRelease) featureFlagsClassic).isEnabled(Flags.ROAMING_INDICATOR_VIA_DISPLAY_INFO) ? StateKt.map(buildState2, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(4)) : StateKt.map(buildState, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(5));
        this.operatorAlphaShort = StateKt.map(buildState, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(6));
        this.isInService = StateKt.map(buildState, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(7));
        final int i4 = 7;
        this.carrierRoamingNtnActive = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ MobileConnectionRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i4) {
                    case 0:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(14)), 0);
                    case 1:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl2 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl2.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(12)), null);
                    case 2:
                        try {
                            return Integer.valueOf(this.f$0.telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber());
                        } catch (UnsupportedOperationException unused) {
                            return null;
                        }
                    case 3:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl3 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.map(mobileConnectionRepositoryKairosImpl3.telephonyPollingEvent, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(mobileConnectionRepositoryKairosImpl3, 1)), Boolean.FALSE);
                    case 4:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl4 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl4.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(13)), Boolean.valueOf(mobileConnectionRepositoryKairosImpl4.telephonyManager.isDataConnectionAllowed()));
                    case 5:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl5 = this.f$0;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                        State foldState = buildScopeImpl.stateScope.foldState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl5.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(10)), EmptySet.INSTANCE, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda33());
                        MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 = new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(11);
                        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                        stateScopeImpl.getClass();
                        return stateScopeImpl.sampleTransactionals(StateKt.map(foldState, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1, 0)));
                    case 6:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl6 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl6.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(9)), null);
                    case 7:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl7 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl7.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(20)), Boolean.FALSE);
                    case 8:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl8 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl8.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(17)), null);
                    case 9:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl9 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl9.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(15)), DataConnectionState.Disconnected);
                    case 10:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl10 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl10.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(18)), new DataActivityModel(false, false));
                    case 11:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl11 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl11.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(19)), Boolean.FALSE);
                    default:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl12 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl12.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(21)), null);
                }
            }
        });
        final int i5 = 8;
        State buildState3 = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ MobileConnectionRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i5) {
                    case 0:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(14)), 0);
                    case 1:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl2 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl2.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(12)), null);
                    case 2:
                        try {
                            return Integer.valueOf(this.f$0.telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber());
                        } catch (UnsupportedOperationException unused) {
                            return null;
                        }
                    case 3:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl3 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.map(mobileConnectionRepositoryKairosImpl3.telephonyPollingEvent, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(mobileConnectionRepositoryKairosImpl3, 1)), Boolean.FALSE);
                    case 4:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl4 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl4.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(13)), Boolean.valueOf(mobileConnectionRepositoryKairosImpl4.telephonyManager.isDataConnectionAllowed()));
                    case 5:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl5 = this.f$0;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                        State foldState = buildScopeImpl.stateScope.foldState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl5.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(10)), EmptySet.INSTANCE, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda33());
                        MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 = new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(11);
                        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                        stateScopeImpl.getClass();
                        return stateScopeImpl.sampleTransactionals(StateKt.map(foldState, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1, 0)));
                    case 6:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl6 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl6.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(9)), null);
                    case 7:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl7 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl7.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(20)), Boolean.FALSE);
                    case 8:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl8 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl8.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(17)), null);
                    case 9:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl9 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl9.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(15)), DataConnectionState.Disconnected);
                    case 10:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl10 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl10.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(18)), new DataActivityModel(false, false));
                    case 11:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl11 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl11.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(19)), Boolean.FALSE);
                    default:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl12 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl12.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(21)), null);
                }
            }
        });
        this.isGsm = StateKt.map(buildState3, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(0));
        this.cdmaLevel = StateKt.map(buildState3, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(8));
        this.primaryLevel = StateKt.map(buildState3, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(16));
        final int i6 = 0;
        this.satelliteLevel = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ MobileConnectionRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i6) {
                    case 0:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(14)), 0);
                    case 1:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl2 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl2.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(12)), null);
                    case 2:
                        try {
                            return Integer.valueOf(this.f$0.telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber());
                        } catch (UnsupportedOperationException unused) {
                            return null;
                        }
                    case 3:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl3 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.map(mobileConnectionRepositoryKairosImpl3.telephonyPollingEvent, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(mobileConnectionRepositoryKairosImpl3, 1)), Boolean.FALSE);
                    case 4:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl4 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl4.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(13)), Boolean.valueOf(mobileConnectionRepositoryKairosImpl4.telephonyManager.isDataConnectionAllowed()));
                    case 5:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl5 = this.f$0;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                        State foldState = buildScopeImpl.stateScope.foldState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl5.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(10)), EmptySet.INSTANCE, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda33());
                        MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 = new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(11);
                        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                        stateScopeImpl.getClass();
                        return stateScopeImpl.sampleTransactionals(StateKt.map(foldState, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1, 0)));
                    case 6:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl6 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl6.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(9)), null);
                    case 7:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl7 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl7.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(20)), Boolean.FALSE);
                    case 8:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl8 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl8.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(17)), null);
                    case 9:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl9 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl9.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(15)), DataConnectionState.Disconnected);
                    case 10:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl10 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl10.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(18)), new DataActivityModel(false, false));
                    case 11:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl11 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl11.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(19)), Boolean.FALSE);
                    default:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl12 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl12.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(21)), null);
                }
            }
        });
        final int i7 = 9;
        this.dataConnectionState = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ MobileConnectionRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i7) {
                    case 0:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(14)), 0);
                    case 1:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl2 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl2.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(12)), null);
                    case 2:
                        try {
                            return Integer.valueOf(this.f$0.telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber());
                        } catch (UnsupportedOperationException unused) {
                            return null;
                        }
                    case 3:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl3 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.map(mobileConnectionRepositoryKairosImpl3.telephonyPollingEvent, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(mobileConnectionRepositoryKairosImpl3, 1)), Boolean.FALSE);
                    case 4:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl4 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl4.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(13)), Boolean.valueOf(mobileConnectionRepositoryKairosImpl4.telephonyManager.isDataConnectionAllowed()));
                    case 5:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl5 = this.f$0;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                        State foldState = buildScopeImpl.stateScope.foldState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl5.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(10)), EmptySet.INSTANCE, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda33());
                        MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 = new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(11);
                        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                        stateScopeImpl.getClass();
                        return stateScopeImpl.sampleTransactionals(StateKt.map(foldState, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1, 0)));
                    case 6:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl6 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl6.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(9)), null);
                    case 7:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl7 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl7.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(20)), Boolean.FALSE);
                    case 8:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl8 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl8.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(17)), null);
                    case 9:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl9 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl9.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(15)), DataConnectionState.Disconnected);
                    case 10:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl10 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl10.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(18)), new DataActivityModel(false, false));
                    case 11:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl11 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl11.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(19)), Boolean.FALSE);
                    default:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl12 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl12.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(21)), null);
                }
            }
        });
        final int i8 = 10;
        this.dataActivityDirection = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ MobileConnectionRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i8) {
                    case 0:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(14)), 0);
                    case 1:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl2 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl2.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(12)), null);
                    case 2:
                        try {
                            return Integer.valueOf(this.f$0.telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber());
                        } catch (UnsupportedOperationException unused) {
                            return null;
                        }
                    case 3:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl3 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.map(mobileConnectionRepositoryKairosImpl3.telephonyPollingEvent, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(mobileConnectionRepositoryKairosImpl3, 1)), Boolean.FALSE);
                    case 4:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl4 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl4.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(13)), Boolean.valueOf(mobileConnectionRepositoryKairosImpl4.telephonyManager.isDataConnectionAllowed()));
                    case 5:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl5 = this.f$0;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                        State foldState = buildScopeImpl.stateScope.foldState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl5.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(10)), EmptySet.INSTANCE, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda33());
                        MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 = new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(11);
                        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                        stateScopeImpl.getClass();
                        return stateScopeImpl.sampleTransactionals(StateKt.map(foldState, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1, 0)));
                    case 6:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl6 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl6.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(9)), null);
                    case 7:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl7 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl7.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(20)), Boolean.FALSE);
                    case 8:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl8 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl8.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(17)), null);
                    case 9:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl9 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl9.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(15)), DataConnectionState.Disconnected);
                    case 10:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl10 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl10.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(18)), new DataActivityModel(false, false));
                    case 11:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl11 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl11.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(19)), Boolean.FALSE);
                    default:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl12 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl12.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(21)), null);
                }
            }
        });
        final int i9 = 11;
        this.carrierNetworkChangeActive = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ MobileConnectionRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i9) {
                    case 0:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(14)), 0);
                    case 1:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl2 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl2.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(12)), null);
                    case 2:
                        try {
                            return Integer.valueOf(this.f$0.telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber());
                        } catch (UnsupportedOperationException unused) {
                            return null;
                        }
                    case 3:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl3 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.map(mobileConnectionRepositoryKairosImpl3.telephonyPollingEvent, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(mobileConnectionRepositoryKairosImpl3, 1)), Boolean.FALSE);
                    case 4:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl4 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl4.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(13)), Boolean.valueOf(mobileConnectionRepositoryKairosImpl4.telephonyManager.isDataConnectionAllowed()));
                    case 5:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl5 = this.f$0;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                        State foldState = buildScopeImpl.stateScope.foldState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl5.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(10)), EmptySet.INSTANCE, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda33());
                        MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 = new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(11);
                        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                        stateScopeImpl.getClass();
                        return stateScopeImpl.sampleTransactionals(StateKt.map(foldState, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1, 0)));
                    case 6:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl6 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl6.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(9)), null);
                    case 7:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl7 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl7.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(20)), Boolean.FALSE);
                    case 8:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl8 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl8.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(17)), null);
                    case 9:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl9 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl9.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(15)), DataConnectionState.Disconnected);
                    case 10:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl10 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl10.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(18)), new DataActivityModel(false, false));
                    case 11:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl11 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl11.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(19)), Boolean.FALSE);
                    default:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl12 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl12.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(21)), null);
                }
            }
        });
        final int i10 = 12;
        this.resolvedNetworkType = StateKt.map(kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ MobileConnectionRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i10) {
                    case 0:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(14)), 0);
                    case 1:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl2 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl2.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(12)), null);
                    case 2:
                        try {
                            return Integer.valueOf(this.f$0.telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber());
                        } catch (UnsupportedOperationException unused) {
                            return null;
                        }
                    case 3:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl3 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.map(mobileConnectionRepositoryKairosImpl3.telephonyPollingEvent, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(mobileConnectionRepositoryKairosImpl3, 1)), Boolean.FALSE);
                    case 4:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl4 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl4.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(13)), Boolean.valueOf(mobileConnectionRepositoryKairosImpl4.telephonyManager.isDataConnectionAllowed()));
                    case 5:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl5 = this.f$0;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                        State foldState = buildScopeImpl.stateScope.foldState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl5.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(10)), EmptySet.INSTANCE, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda33());
                        MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 = new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(11);
                        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                        stateScopeImpl.getClass();
                        return stateScopeImpl.sampleTransactionals(StateKt.map(foldState, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1, 0)));
                    case 6:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl6 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl6.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(9)), null);
                    case 7:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl7 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl7.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(20)), Boolean.FALSE);
                    case 8:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl8 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl8.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(17)), null);
                    case 9:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl9 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl9.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(15)), DataConnectionState.Disconnected);
                    case 10:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl10 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl10.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(18)), new DataActivityModel(false, false));
                    case 11:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl11 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl11.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(19)), Boolean.FALSE);
                    default:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl12 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl12.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(21)), null);
                }
            }
        }), new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(this, 0));
        final int i11 = 0;
        State buildState4 = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i11) {
                    case 0:
                        ReadonlyStateFlow readonlyStateFlow = systemUiCarrierConfig.shouldInflateSignalStrength;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl, readonlyStateFlow);
                    default:
                        ReadonlyStateFlow readonlyStateFlow2 = systemUiCarrierConfig.allowNetworkSliceIndicator;
                        BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) buildScope;
                        buildScopeImpl2.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl2, readonlyStateFlow2);
                }
            }
        });
        this.inflateSignalStrength = buildState4;
        final int i12 = 1;
        this.allowNetworkSliceIndicator = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i12) {
                    case 0:
                        ReadonlyStateFlow readonlyStateFlow = systemUiCarrierConfig.shouldInflateSignalStrength;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
                        buildScopeImpl.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl, readonlyStateFlow);
                    default:
                        ReadonlyStateFlow readonlyStateFlow2 = systemUiCarrierConfig.allowNetworkSliceIndicator;
                        BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) buildScope;
                        buildScopeImpl2.getClass();
                        return BuildScope.DefaultImpls.toState(buildScopeImpl2, readonlyStateFlow2);
                }
            }
        });
        this.numberOfLevels = StateKt.map(buildState4, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(1));
        this.carrierName = StateKt.map(state, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(networkNameModel, 2));
        this.telephonyPollingEvent = EventsKt.map(buildEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(2));
        final int i13 = 2;
        this.cdmaEnhancedRoamingIndicatorDisplayNumber = TransactionalKt.transactionally(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ MobileConnectionRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i13) {
                    case 0:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(14)), 0);
                    case 1:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl2 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl2.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(12)), null);
                    case 2:
                        try {
                            return Integer.valueOf(this.f$0.telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber());
                        } catch (UnsupportedOperationException unused) {
                            return null;
                        }
                    case 3:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl3 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.map(mobileConnectionRepositoryKairosImpl3.telephonyPollingEvent, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(mobileConnectionRepositoryKairosImpl3, 1)), Boolean.FALSE);
                    case 4:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl4 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl4.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(13)), Boolean.valueOf(mobileConnectionRepositoryKairosImpl4.telephonyManager.isDataConnectionAllowed()));
                    case 5:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl5 = this.f$0;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                        State foldState = buildScopeImpl.stateScope.foldState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl5.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(10)), EmptySet.INSTANCE, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda33());
                        MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 = new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(11);
                        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                        stateScopeImpl.getClass();
                        return stateScopeImpl.sampleTransactionals(StateKt.map(foldState, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1, 0)));
                    case 6:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl6 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl6.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(9)), null);
                    case 7:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl7 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl7.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(20)), Boolean.FALSE);
                    case 8:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl8 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl8.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(17)), null);
                    case 9:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl9 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl9.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(15)), DataConnectionState.Disconnected);
                    case 10:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl10 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl10.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(18)), new DataActivityModel(false, false));
                    case 11:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl11 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl11.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(19)), Boolean.FALSE);
                    default:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl12 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl12.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(21)), null);
                }
            }
        });
        final int i14 = 3;
        this.cdmaRoaming = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ MobileConnectionRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i14) {
                    case 0:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(14)), 0);
                    case 1:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl2 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl2.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(12)), null);
                    case 2:
                        try {
                            return Integer.valueOf(this.f$0.telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber());
                        } catch (UnsupportedOperationException unused) {
                            return null;
                        }
                    case 3:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl3 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.map(mobileConnectionRepositoryKairosImpl3.telephonyPollingEvent, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(mobileConnectionRepositoryKairosImpl3, 1)), Boolean.FALSE);
                    case 4:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl4 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl4.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(13)), Boolean.valueOf(mobileConnectionRepositoryKairosImpl4.telephonyManager.isDataConnectionAllowed()));
                    case 5:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl5 = this.f$0;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                        State foldState = buildScopeImpl.stateScope.foldState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl5.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(10)), EmptySet.INSTANCE, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda33());
                        MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 = new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(11);
                        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                        stateScopeImpl.getClass();
                        return stateScopeImpl.sampleTransactionals(StateKt.map(foldState, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1, 0)));
                    case 6:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl6 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl6.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(9)), null);
                    case 7:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl7 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl7.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(20)), Boolean.FALSE);
                    case 8:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl8 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl8.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(17)), null);
                    case 9:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl9 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl9.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(15)), DataConnectionState.Disconnected);
                    case 10:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl10 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl10.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(18)), new DataActivityModel(false, false));
                    case 11:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl11 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl11.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(19)), Boolean.FALSE);
                    default:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl12 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl12.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(21)), null);
                }
            }
        });
        this.carrierId = kairosBuilderImpl.buildState(new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda0(broadcastDispatcher, this));
        this.networkName = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this;
                MobileInputLogger mobileInputLogger2 = mobileInputLogger;
                String str2 = str;
                NetworkNameModel networkNameModel2 = NetworkNameModel.this;
                MobileConnectionRepositoryKairosImpl$networkName$1$1 mobileConnectionRepositoryKairosImpl$networkName$1$1 = new MobileConnectionRepositoryKairosImpl$networkName$1$1(mobileConnectionRepositoryKairosImpl, mobileInputLogger2, str2, networkNameModel2, null);
                Object obj2 = new Object();
                BuildScopeKt$$ExternalSyntheticLambda3 buildScopeKt$$ExternalSyntheticLambda3 = new BuildScopeKt$$ExternalSyntheticLambda3(0);
                BuildScopeKt$$ExternalSyntheticLambda6 buildScopeKt$$ExternalSyntheticLambda6 = new BuildScopeKt$$ExternalSyntheticLambda6(obj2);
                BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                buildScopeImpl.getClass();
                return buildScopeImpl.stateScope.holdState(EventsKt.mapCheap(BuildScopeImpl.buildEvents$default(buildScopeImpl, new BuildScopeImpl$$ExternalSyntheticLambda3(buildScopeImpl, buildScopeKt$$ExternalSyntheticLambda6, buildScopeKt$$ExternalSyntheticLambda3), mobileConnectionRepositoryKairosImpl$networkName$1$1), new BuildScopeKt$$ExternalSyntheticLambda3(1)), networkNameModel2);
            }
        });
        final int i15 = 4;
        this.dataEnabled = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ MobileConnectionRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i15) {
                    case 0:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(14)), 0);
                    case 1:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl2 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl2.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(12)), null);
                    case 2:
                        try {
                            return Integer.valueOf(this.f$0.telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber());
                        } catch (UnsupportedOperationException unused) {
                            return null;
                        }
                    case 3:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl3 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.map(mobileConnectionRepositoryKairosImpl3.telephonyPollingEvent, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(mobileConnectionRepositoryKairosImpl3, 1)), Boolean.FALSE);
                    case 4:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl4 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl4.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(13)), Boolean.valueOf(mobileConnectionRepositoryKairosImpl4.telephonyManager.isDataConnectionAllowed()));
                    case 5:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl5 = this.f$0;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                        State foldState = buildScopeImpl.stateScope.foldState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl5.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(10)), EmptySet.INSTANCE, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda33());
                        MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 = new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(11);
                        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                        stateScopeImpl.getClass();
                        return stateScopeImpl.sampleTransactionals(StateKt.map(foldState, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1, 0)));
                    case 6:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl6 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl6.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(9)), null);
                    case 7:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl7 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl7.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(20)), Boolean.FALSE);
                    case 8:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl8 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl8.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(17)), null);
                    case 9:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl9 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl9.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(15)), DataConnectionState.Disconnected);
                    case 10:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl10 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl10.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(18)), new DataActivityModel(false, false));
                    case 11:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl11 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl11.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(19)), Boolean.FALSE);
                    default:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl12 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl12.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(21)), null);
                }
            }
        });
        final int i16 = 5;
        this.isInEcmMode = kairosBuilderImpl.buildState(new Function1(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ MobileConnectionRepositoryKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                switch (i16) {
                    case 0:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(14)), 0);
                    case 1:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl2 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl2.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(12)), null);
                    case 2:
                        try {
                            return Integer.valueOf(this.f$0.telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber());
                        } catch (UnsupportedOperationException unused) {
                            return null;
                        }
                    case 3:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl3 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.map(mobileConnectionRepositoryKairosImpl3.telephonyPollingEvent, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda9(mobileConnectionRepositoryKairosImpl3, 1)), Boolean.FALSE);
                    case 4:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl4 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl4.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(13)), Boolean.valueOf(mobileConnectionRepositoryKairosImpl4.telephonyManager.isDataConnectionAllowed()));
                    case 5:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl5 = this.f$0;
                        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                        State foldState = buildScopeImpl.stateScope.foldState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl5.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(10)), EmptySet.INSTANCE, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda33());
                        MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 = new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(11);
                        StateScopeImpl stateScopeImpl = buildScopeImpl.stateScope;
                        stateScopeImpl.getClass();
                        return stateScopeImpl.sampleTransactionals(StateKt.map(foldState, new StateScope$DefaultImpls$$ExternalSyntheticLambda0(mobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1, 0)));
                    case 6:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl6 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl6.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(9)), null);
                    case 7:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl7 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl7.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(20)), Boolean.FALSE);
                    case 8:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl8 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl8.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(17)), null);
                    case 9:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl9 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl9.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(15)), DataConnectionState.Disconnected);
                    case 10:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl10 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl10.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(18)), new DataActivityModel(false, false));
                    case 11:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl11 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl11.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(19)), Boolean.FALSE);
                    default:
                        MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl12 = this.f$0;
                        return ((BuildScopeImpl) ((BuildScope) obj)).stateScope.holdState(EventsKt.mapNotNull(mobileConnectionRepositoryKairosImpl12.callbackEvents, new MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(21)), null);
                }
            }
        });
        this.isAllowedDuringAirplaneMode = StateKt.stateOf(Boolean.FALSE);
        this.networkSliceRequest = new NetworkRequest.Builder().addCapability(34).setSubscriptionIds(Collections.singleton(Integer.valueOf(i))).build();
        this.hasPrioritizedNetworkCapabilities = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                MobileConnectionRepositoryKairosImpl$hasPrioritizedNetworkCapabilities$1$1 mobileConnectionRepositoryKairosImpl$hasPrioritizedNetworkCapabilities$1$1 = new MobileConnectionRepositoryKairosImpl$hasPrioritizedNetworkCapabilities$1$1(connectivityManager, this, mobileInputLogger, null);
                Object obj2 = new Object();
                BuildScopeKt$$ExternalSyntheticLambda3 buildScopeKt$$ExternalSyntheticLambda3 = new BuildScopeKt$$ExternalSyntheticLambda3(0);
                BuildScopeKt$$ExternalSyntheticLambda6 buildScopeKt$$ExternalSyntheticLambda6 = new BuildScopeKt$$ExternalSyntheticLambda6(obj2);
                BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                buildScopeImpl.getClass();
                return buildScopeImpl.stateScope.holdState(EventsKt.mapCheap(BuildScopeImpl.buildEvents$default(buildScopeImpl, new BuildScopeImpl$$ExternalSyntheticLambda3(buildScopeImpl, buildScopeKt$$ExternalSyntheticLambda6, buildScopeKt$$ExternalSyntheticLambda3), mobileConnectionRepositoryKairosImpl$hasPrioritizedNetworkCapabilities$1$1), new BuildScopeKt$$ExternalSyntheticLambda3(1)), Boolean.FALSE);
            }
        });
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        this.$$delegate_0.activate(buildScope);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getAllowNetworkSliceIndicator() {
        return this.allowNetworkSliceIndicator;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCarrierId() {
        return this.carrierId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCarrierName() {
        return this.carrierName;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCarrierNetworkChangeActive() {
        return this.carrierNetworkChangeActive;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCdmaLevel() {
        return this.cdmaLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getCdmaRoaming() {
        return this.cdmaRoaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getDataActivityDirection() {
        return this.dataActivityDirection;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getDataConnectionState() {
        return this.dataConnectionState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getDataEnabled() {
        return this.dataEnabled;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getHasPrioritizedNetworkCapabilities() {
        return this.hasPrioritizedNetworkCapabilities;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getInflateSignalStrength() {
        return this.inflateSignalStrength;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getNetworkName() {
        return this.networkName;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getNumberOfLevels() {
        return this.numberOfLevels;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getOperatorAlphaShort() {
        return this.operatorAlphaShort;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getPrimaryLevel() {
        return this.primaryLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getResolvedNetworkType() {
        return this.resolvedNetworkType;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State getSatelliteLevel() {
        return this.satelliteLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final int getSubId() {
        return this.subId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final TableLogBuffer getTableLogBuffer() {
        return this.tableLogBuffer;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isAllowedDuringAirplaneMode() {
        return this.isAllowedDuringAirplaneMode;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isEmergencyOnly() {
        return this.isEmergencyOnly;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isGsm() {
        return this.isGsm;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isInEcmMode() {
        return this.isInEcmMode;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isInService() {
        return this.isInService;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isNonTerrestrial() {
        return this.carrierRoamingNtnActive;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos
    public final State isRoaming() {
        return this.isRoaming;
    }

    @Override // com.android.systemui.KairosBuilder
    public final void onActivated(Function1 function1) {
        this.$$delegate_0.onActivated(function1);
    }
}
