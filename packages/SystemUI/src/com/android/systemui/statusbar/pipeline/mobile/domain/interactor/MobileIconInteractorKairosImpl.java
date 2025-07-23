package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.content.Context;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileIconCarrierIdOverrides;
import com.android.settingslib.mobile.MobileIconCarrierIdOverridesImpl;
import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.CombineKt;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.StateScopeImpl;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.statusbar.pipeline.mobile.data.model.DataConnectionState;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepositoryKairos;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda16;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileIconInteractorKairosImpl implements MobileIconInteractorKairos, KairosBuilder {
    public final /* synthetic */ KairosBuilderImpl $$delegate_0;
    public final State alwaysShowDataRatIcon;
    public final StateInit carrierIdIconOverrideExists;
    public final MobileIconCarrierIdOverrides carrierIdOverrides;
    public final StateInit carrierName;
    public final StateInit cellularIcon;
    public final StateInit cellularShownLevel;
    public final MobileConnectionRepositoryKairos connectionRepository;
    public final Context context;
    public final StateInit defaultNetworkType;
    public final StateInit isDataConnected;
    public final State isDataEnabled;
    public final State isForceHidden;
    public final StateInit isRoaming;
    public final State isSingleCarrier;
    public final StateInit level;
    public final State mobileIsDefault;
    public final StateInit networkName;
    public final State networkTypeIconGroup;
    public final StateInit satelliteIcon;
    public final StateInit showExclamationMark;
    public final StateInit showSliceAttribution;
    public final StateInit signalLevelIcon;

    public MobileIconInteractorKairosImpl(State state, State state2, State state3, State state4, State state5, State state6, State state7, State state8, State state9, MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos, Context context, MobileIconCarrierIdOverrides mobileIconCarrierIdOverrides) {
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        this.alwaysShowDataRatIcon = state2;
        this.isSingleCarrier = state4;
        this.mobileIsDefault = state5;
        this.isForceHidden = state9;
        this.connectionRepository = mobileConnectionRepositoryKairos;
        this.context = context;
        this.carrierIdOverrides = mobileIconCarrierIdOverrides;
        this.isDataEnabled = mobileConnectionRepositoryKairos.getDataEnabled();
        final int i = 0;
        this.carrierIdIconOverrideExists = StateKt.map(mobileConnectionRepositoryKairos.getCarrierId(), new Function2(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ MobileIconInteractorKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i) {
                    case 0:
                        Integer num = (Integer) obj2;
                        num.intValue();
                        ((MobileIconCarrierIdOverridesImpl) this.f$0.carrierIdOverrides).getClass();
                        Map map = MobileIconCarrierIdOverridesImpl.MAPPING;
                        MobileIconCarrierIdOverridesImpl.Companion.getClass();
                        return Boolean.valueOf(map.containsKey(num));
                    default:
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl = this.f$0;
                        return booleanValue ? mobileIconInteractorKairosImpl.satelliteIcon : mobileIconInteractorKairosImpl.cellularIcon;
                }
            }
        });
        final int i2 = 0;
        this.networkName = CombineKt.combine(mobileConnectionRepositoryKairos.getOperatorAlphaShort(), mobileConnectionRepositoryKairos.getNetworkName(), new Function3() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i2) {
                    case 0:
                        String str = (String) obj2;
                        NetworkNameModel networkNameModel = (NetworkNameModel) obj3;
                        return (!(networkNameModel instanceof NetworkNameModel.Default) || str == null) ? networkNameModel : new NetworkNameModel.IntentDerived(str);
                    case 1:
                        return Boolean.valueOf(((Boolean) obj2).booleanValue() && ((Boolean) obj3).booleanValue());
                    default:
                        String str2 = (String) obj2;
                        NetworkNameModel networkNameModel2 = (NetworkNameModel) obj3;
                        return (!(networkNameModel2 instanceof NetworkNameModel.Default) || str2 == null) ? networkNameModel2.getName() : str2;
                }
            }
        });
        final int i3 = 2;
        this.carrierName = CombineKt.combine(mobileConnectionRepositoryKairos.getOperatorAlphaShort(), mobileConnectionRepositoryKairos.getCarrierName(), new Function3() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i3) {
                    case 0:
                        String str = (String) obj2;
                        NetworkNameModel networkNameModel = (NetworkNameModel) obj3;
                        return (!(networkNameModel instanceof NetworkNameModel.Default) || str == null) ? networkNameModel : new NetworkNameModel.IntentDerived(str);
                    case 1:
                        return Boolean.valueOf(((Boolean) obj2).booleanValue() && ((Boolean) obj3).booleanValue());
                    default:
                        String str2 = (String) obj2;
                        NetworkNameModel networkNameModel2 = (NetworkNameModel) obj3;
                        return (!(networkNameModel2 instanceof NetworkNameModel.Default) || str2 == null) ? networkNameModel2.getName() : str2;
                }
            }
        });
        final int i4 = 2;
        this.defaultNetworkType = CombineKt.combine(mobileConnectionRepositoryKairos.getResolvedNetworkType(), state6, state7, new Function4() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                switch (i4) {
                    case 0:
                        return Boolean.valueOf((((Boolean) obj2).booleanValue() && !((Boolean) obj3).booleanValue() && ((Boolean) obj4).booleanValue()) ? false : true);
                    case 1:
                        int intValue = ((Integer) obj2).intValue();
                        boolean booleanValue = ((Boolean) obj3).booleanValue();
                        boolean booleanValue2 = ((Boolean) obj4).booleanValue();
                        if (!booleanValue) {
                            intValue = 0;
                        } else if (booleanValue2) {
                            intValue++;
                        }
                        return Integer.valueOf(intValue);
                    default:
                        ResolvedNetworkType resolvedNetworkType = (ResolvedNetworkType) obj2;
                        Map map = (Map) obj3;
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = (SignalIcon$MobileIconGroup) obj4;
                        if (resolvedNetworkType instanceof ResolvedNetworkType.CarrierMergedNetworkType) {
                            ((ResolvedNetworkType.CarrierMergedNetworkType) resolvedNetworkType).getClass();
                            return ResolvedNetworkType.CarrierMergedNetworkType.iconGroupOverride;
                        }
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = (SignalIcon$MobileIconGroup) map.get(resolvedNetworkType.getLookupKey());
                        return signalIcon$MobileIconGroup2 == null ? signalIcon$MobileIconGroup : signalIcon$MobileIconGroup2;
                }
            }
        });
        this.networkTypeIconGroup = kairosBuilderImpl.buildState(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl = MobileIconInteractorKairosImpl.this;
                final MobileIconInteractorKairosImpl$$ExternalSyntheticLambda16 mobileIconInteractorKairosImpl$$ExternalSyntheticLambda16 = new MobileIconInteractorKairosImpl$$ExternalSyntheticLambda16(mobileIconInteractorKairosImpl);
                StateScopeImpl stateScopeImpl = ((BuildScopeImpl) buildScope).stateScope;
                stateScopeImpl.getClass();
                StateInit sampleTransactionals = stateScopeImpl.sampleTransactionals(CombineKt.combine(mobileIconInteractorKairosImpl.defaultNetworkType, mobileIconInteractorKairosImpl.carrierIdIconOverrideExists, new Function3() { // from class: com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda12
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj2, final Object obj3, final Object obj4) {
                        final MobileIconInteractorKairosImpl$$ExternalSyntheticLambda16 mobileIconInteractorKairosImpl$$ExternalSyntheticLambda162 = MobileIconInteractorKairosImpl$$ExternalSyntheticLambda16.this;
                        return TransactionalKt.transactionally(new Function1() { // from class: com.android.systemui.kairos.StateScope$DefaultImpls$$ExternalSyntheticLambda13
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj5) {
                                return MobileIconInteractorKairosImpl$$ExternalSyntheticLambda16.this.invoke((TransactionScope) obj5, obj3, obj4);
                            }
                        });
                    }
                }));
                DiffableKt.logDiffsForTable(buildScope, sampleTransactionals, mobileIconInteractorKairosImpl.connectionRepository.getTableLogBuffer(), "");
                return sampleTransactionals;
            }
        });
        final int i5 = 1;
        this.showSliceAttribution = CombineKt.combine(mobileConnectionRepositoryKairos.getAllowNetworkSliceIndicator(), mobileConnectionRepositoryKairos.getHasPrioritizedNetworkCapabilities(), new Function3() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i5) {
                    case 0:
                        String str = (String) obj2;
                        NetworkNameModel networkNameModel = (NetworkNameModel) obj3;
                        return (!(networkNameModel instanceof NetworkNameModel.Default) || str == null) ? networkNameModel : new NetworkNameModel.IntentDerived(str);
                    case 1:
                        return Boolean.valueOf(((Boolean) obj2).booleanValue() && ((Boolean) obj3).booleanValue());
                    default:
                        String str2 = (String) obj2;
                        NetworkNameModel networkNameModel2 = (NetworkNameModel) obj3;
                        return (!(networkNameModel2 instanceof NetworkNameModel.Default) || str2 == null) ? networkNameModel2.getName() : str2;
                }
            }
        });
        final int i6 = 1;
        this.isRoaming = CombineKt.combine(mobileConnectionRepositoryKairos.getCarrierNetworkChangeActive(), mobileConnectionRepositoryKairos.isGsm(), mobileConnectionRepositoryKairos.isRoaming(), mobileConnectionRepositoryKairos.getCdmaRoaming(), new Function5() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function5
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                switch (i6) {
                    case 0:
                        return new SignalIconModel.Cellular(((Integer) obj2).intValue(), ((Integer) obj3).intValue(), ((Boolean) obj4).booleanValue(), ((Boolean) obj5).booleanValue(), 0, 16, null);
                    case 1:
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
                        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
                        boolean booleanValue4 = ((Boolean) obj5).booleanValue();
                        if (booleanValue) {
                            booleanValue3 = false;
                        } else if (!booleanValue2) {
                            booleanValue3 = booleanValue4;
                        }
                        return Boolean.valueOf(booleanValue3);
                    default:
                        boolean booleanValue5 = ((Boolean) obj2).booleanValue();
                        int intValue = ((Integer) obj3).intValue();
                        int intValue2 = ((Integer) obj4).intValue();
                        boolean booleanValue6 = ((Boolean) obj5).booleanValue();
                        if (!booleanValue5 && booleanValue6) {
                            intValue = intValue2;
                        }
                        return Integer.valueOf(intValue);
                }
            }
        });
        final int i7 = 2;
        StateInit combine = CombineKt.combine(mobileConnectionRepositoryKairos.isGsm(), mobileConnectionRepositoryKairos.getPrimaryLevel(), mobileConnectionRepositoryKairos.getCdmaLevel(), state3, new Function5() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function5
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                switch (i7) {
                    case 0:
                        return new SignalIconModel.Cellular(((Integer) obj2).intValue(), ((Integer) obj3).intValue(), ((Boolean) obj4).booleanValue(), ((Boolean) obj5).booleanValue(), 0, 16, null);
                    case 1:
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
                        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
                        boolean booleanValue4 = ((Boolean) obj5).booleanValue();
                        if (booleanValue) {
                            booleanValue3 = false;
                        } else if (!booleanValue2) {
                            booleanValue3 = booleanValue4;
                        }
                        return Boolean.valueOf(booleanValue3);
                    default:
                        boolean booleanValue5 = ((Boolean) obj2).booleanValue();
                        int intValue = ((Integer) obj3).intValue();
                        int intValue2 = ((Integer) obj4).intValue();
                        boolean booleanValue6 = ((Boolean) obj5).booleanValue();
                        if (!booleanValue5 && booleanValue6) {
                            intValue = intValue2;
                        }
                        return Integer.valueOf(intValue);
                }
            }
        });
        this.level = combine;
        final int i8 = 1;
        final StateInit map = StateKt.map(mobileConnectionRepositoryKairos.getDataConnectionState(), new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i8) {
                    case 0:
                        int intValue = ((Integer) obj2).intValue();
                        SatelliteIconModel.INSTANCE.getClass();
                        Icon.Resource fromSignalStrength = SatelliteIconModel.fromSignalStrength(intValue);
                        if (fromSignalStrength == null) {
                            fromSignalStrength = SatelliteIconModel.fromSignalStrength(0);
                            fromSignalStrength.getClass();
                        }
                        return new SignalIconModel.Satellite(intValue, fromSignalStrength);
                    default:
                        return Boolean.valueOf(((DataConnectionState) obj2) == DataConnectionState.Connected);
                }
            }
        });
        final int i9 = 1;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i9) {
                    case 0:
                        DiffableKt.logDiffsForTable(buildScope, map, this.connectionRepository.getTableLogBuffer(), "icon");
                        break;
                    default:
                        DiffableKt.logBooleanDiffsForTable(buildScope, map, this.connectionRepository.getTableLogBuffer(), "icon", "isDataConnected");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.isDataConnected = map;
        final int i10 = 0;
        StateInit combine2 = CombineKt.combine(state, state8, mobileConnectionRepositoryKairos.isInService(), new Function4() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                switch (i10) {
                    case 0:
                        return Boolean.valueOf((((Boolean) obj2).booleanValue() && !((Boolean) obj3).booleanValue() && ((Boolean) obj4).booleanValue()) ? false : true);
                    case 1:
                        int intValue = ((Integer) obj2).intValue();
                        boolean booleanValue = ((Boolean) obj3).booleanValue();
                        boolean booleanValue2 = ((Boolean) obj4).booleanValue();
                        if (!booleanValue) {
                            intValue = 0;
                        } else if (booleanValue2) {
                            intValue++;
                        }
                        return Integer.valueOf(intValue);
                    default:
                        ResolvedNetworkType resolvedNetworkType = (ResolvedNetworkType) obj2;
                        Map map2 = (Map) obj3;
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = (SignalIcon$MobileIconGroup) obj4;
                        if (resolvedNetworkType instanceof ResolvedNetworkType.CarrierMergedNetworkType) {
                            ((ResolvedNetworkType.CarrierMergedNetworkType) resolvedNetworkType).getClass();
                            return ResolvedNetworkType.CarrierMergedNetworkType.iconGroupOverride;
                        }
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = (SignalIcon$MobileIconGroup) map2.get(resolvedNetworkType.getLookupKey());
                        return signalIcon$MobileIconGroup2 == null ? signalIcon$MobileIconGroup : signalIcon$MobileIconGroup2;
                }
            }
        });
        this.showExclamationMark = combine2;
        final int i11 = 1;
        StateInit combine3 = CombineKt.combine(combine, mobileConnectionRepositoryKairos.isInService(), mobileConnectionRepositoryKairos.getInflateSignalStrength(), new Function4() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                switch (i11) {
                    case 0:
                        return Boolean.valueOf((((Boolean) obj2).booleanValue() && !((Boolean) obj3).booleanValue() && ((Boolean) obj4).booleanValue()) ? false : true);
                    case 1:
                        int intValue = ((Integer) obj2).intValue();
                        boolean booleanValue = ((Boolean) obj3).booleanValue();
                        boolean booleanValue2 = ((Boolean) obj4).booleanValue();
                        if (!booleanValue) {
                            intValue = 0;
                        } else if (booleanValue2) {
                            intValue++;
                        }
                        return Integer.valueOf(intValue);
                    default:
                        ResolvedNetworkType resolvedNetworkType = (ResolvedNetworkType) obj2;
                        Map map2 = (Map) obj3;
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = (SignalIcon$MobileIconGroup) obj4;
                        if (resolvedNetworkType instanceof ResolvedNetworkType.CarrierMergedNetworkType) {
                            ((ResolvedNetworkType.CarrierMergedNetworkType) resolvedNetworkType).getClass();
                            return ResolvedNetworkType.CarrierMergedNetworkType.iconGroupOverride;
                        }
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = (SignalIcon$MobileIconGroup) map2.get(resolvedNetworkType.getLookupKey());
                        return signalIcon$MobileIconGroup2 == null ? signalIcon$MobileIconGroup : signalIcon$MobileIconGroup2;
                }
            }
        });
        this.cellularShownLevel = combine3;
        State satelliteLevel = mobileConnectionRepositoryKairos.getSatelliteLevel();
        final int i12 = 0;
        this.cellularIcon = CombineKt.combine(combine3, mobileConnectionRepositoryKairos.getNumberOfLevels(), combine2, mobileConnectionRepositoryKairos.getCarrierNetworkChangeActive(), new Function5() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function5
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                switch (i12) {
                    case 0:
                        return new SignalIconModel.Cellular(((Integer) obj2).intValue(), ((Integer) obj3).intValue(), ((Boolean) obj4).booleanValue(), ((Boolean) obj5).booleanValue(), 0, 16, null);
                    case 1:
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
                        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
                        boolean booleanValue4 = ((Boolean) obj5).booleanValue();
                        if (booleanValue) {
                            booleanValue3 = false;
                        } else if (!booleanValue2) {
                            booleanValue3 = booleanValue4;
                        }
                        return Boolean.valueOf(booleanValue3);
                    default:
                        boolean booleanValue5 = ((Boolean) obj2).booleanValue();
                        int intValue = ((Integer) obj3).intValue();
                        int intValue2 = ((Integer) obj4).intValue();
                        boolean booleanValue6 = ((Boolean) obj5).booleanValue();
                        if (!booleanValue5 && booleanValue6) {
                            intValue = intValue2;
                        }
                        return Integer.valueOf(intValue);
                }
            }
        });
        final int i13 = 0;
        this.satelliteIcon = StateKt.map(satelliteLevel, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i13) {
                    case 0:
                        int intValue = ((Integer) obj2).intValue();
                        SatelliteIconModel.INSTANCE.getClass();
                        Icon.Resource fromSignalStrength = SatelliteIconModel.fromSignalStrength(intValue);
                        if (fromSignalStrength == null) {
                            fromSignalStrength = SatelliteIconModel.fromSignalStrength(0);
                            fromSignalStrength.getClass();
                        }
                        return new SignalIconModel.Satellite(intValue, fromSignalStrength);
                    default:
                        return Boolean.valueOf(((DataConnectionState) obj2) == DataConnectionState.Connected);
                }
            }
        });
        final int i14 = 1;
        final StateInit flatMap = StateKt.flatMap(mobileConnectionRepositoryKairos.isNonTerrestrial(), new Function2(this) { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ MobileIconInteractorKairosImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i14) {
                    case 0:
                        Integer num = (Integer) obj2;
                        num.intValue();
                        ((MobileIconCarrierIdOverridesImpl) this.f$0.carrierIdOverrides).getClass();
                        Map map2 = MobileIconCarrierIdOverridesImpl.MAPPING;
                        MobileIconCarrierIdOverridesImpl.Companion.getClass();
                        return Boolean.valueOf(map2.containsKey(num));
                    default:
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        MobileIconInteractorKairosImpl mobileIconInteractorKairosImpl = this.f$0;
                        return booleanValue ? mobileIconInteractorKairosImpl.satelliteIcon : mobileIconInteractorKairosImpl.cellularIcon;
                }
            }
        });
        final int i15 = 0;
        onActivated(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BuildScope buildScope = (BuildScope) obj;
                switch (i15) {
                    case 0:
                        DiffableKt.logDiffsForTable(buildScope, flatMap, this.connectionRepository.getTableLogBuffer(), "icon");
                        break;
                    default:
                        DiffableKt.logBooleanDiffsForTable(buildScope, flatMap, this.connectionRepository.getTableLogBuffer(), "icon", "isDataConnected");
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.signalLevelIcon = flatMap;
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        this.$$delegate_0.activate(buildScope);
    }

    @Override // com.android.systemui.KairosBuilder
    public final void onActivated(Function1 function1) {
        this.$$delegate_0.onActivated(function1);
    }

    public /* synthetic */ MobileIconInteractorKairosImpl(State state, State state2, State state3, State state4, State state5, State state6, State state7, State state8, State state9, MobileConnectionRepositoryKairos mobileConnectionRepositoryKairos, Context context, MobileIconCarrierIdOverrides mobileIconCarrierIdOverrides, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(state, state2, state3, state4, state5, state6, state7, state8, state9, mobileConnectionRepositoryKairos, context, (i & 2048) != 0 ? new MobileIconCarrierIdOverridesImpl() : mobileIconCarrierIdOverrides);
    }
}
