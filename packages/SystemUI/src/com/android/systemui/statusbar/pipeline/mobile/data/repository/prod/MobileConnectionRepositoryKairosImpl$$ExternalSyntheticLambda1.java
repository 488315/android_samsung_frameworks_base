package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.CellSignalStrengthCdma;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.satellite.NtnSignalStrength;
import com.android.settingslib.Utils;
import com.android.systemui.statusbar.pipeline.mobile.data.model.DataConnectionState;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CallbackEvent;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModelKt;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0138  */
    @Override // kotlin.jvm.functions.Function2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invoke(Object obj, Object obj2) {
        boolean z;
        int i;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        List cellSignalStrengths;
        CellSignalStrengthCdma cellSignalStrengthCdma;
        NtnSignalStrength ntnSignalStrength;
        switch (this.$r8$classId) {
            case 0:
                SignalStrength signalStrength = (SignalStrength) obj2;
                if (signalStrength != null) {
                    z = signalStrength.isGsm();
                }
                return Boolean.valueOf(z);
            case 1:
                if (((Boolean) obj2).booleanValue()) {
                    MobileConnectionRepository.Companion.getClass();
                    i = MobileConnectionRepository.Companion.DEFAULT_NUM_LEVELS + 1;
                } else {
                    MobileConnectionRepository.Companion.getClass();
                    i = MobileConnectionRepository.Companion.DEFAULT_NUM_LEVELS;
                }
                return Integer.valueOf(i);
            case 2:
                return Unit.INSTANCE;
            case 3:
                ServiceState serviceState = (ServiceState) obj2;
                if (serviceState != null) {
                    z2 = serviceState.isEmergencyOnly();
                }
                return Boolean.valueOf(z2);
            case 4:
                TelephonyDisplayInfo telephonyDisplayInfo = (TelephonyDisplayInfo) obj2;
                if (telephonyDisplayInfo != null) {
                    z3 = telephonyDisplayInfo.isRoaming();
                }
                return Boolean.valueOf(z3);
            case 5:
                ServiceState serviceState2 = (ServiceState) obj2;
                if (serviceState2 != null) {
                    z4 = serviceState2.getRoaming();
                }
                return Boolean.valueOf(z4);
            case 6:
                ServiceState serviceState3 = (ServiceState) obj2;
                if (serviceState3 != null) {
                    return serviceState3.getOperatorAlphaShort();
                }
                return null;
            case 7:
                ServiceState serviceState4 = (ServiceState) obj2;
                if (serviceState4 != null) {
                    z5 = Utils.isInService(serviceState4);
                }
                return Boolean.valueOf(z5);
            case 8:
                SignalStrength signalStrength2 = (SignalStrength) obj2;
                return Integer.valueOf((signalStrength2 == null || (cellSignalStrengths = signalStrength2.getCellSignalStrengths(CellSignalStrengthCdma.class)) == null || (cellSignalStrengthCdma = (CellSignalStrengthCdma) CollectionsKt___CollectionsKt.firstOrNull(cellSignalStrengths)) == null) ? 0 : cellSignalStrengthCdma.getLevel());
            case 9:
                CallbackEvent.OnDisplayInfoChanged onDisplayInfoChanged = ((TelephonyCallbackState) obj2).onDisplayInfoChanged;
                if (onDisplayInfoChanged != null) {
                    return onDisplayInfoChanged.telephonyDisplayInfo;
                }
                return null;
            case 10:
                TelephonyCallbackState telephonyCallbackState = (TelephonyCallbackState) obj2;
                Pair pair = new Pair(telephonyCallbackState.addedCallbackModes, telephonyCallbackState.removedCallbackModes);
                Set set = (Set) pair.component1();
                Set set2 = (Set) pair.component2();
                if (set.isEmpty() && set2.isEmpty()) {
                    return null;
                }
                return pair;
            case 11:
                return Boolean.valueOf(!((Set) obj2).isEmpty());
            case 12:
                CallbackEvent.OnServiceStateChanged onServiceStateChanged = ((TelephonyCallbackState) obj2).onServiceStateChanged;
                if (onServiceStateChanged != null) {
                    return onServiceStateChanged.serviceState;
                }
                return null;
            case 13:
                CallbackEvent.OnDataEnabledChanged onDataEnabledChanged = ((TelephonyCallbackState) obj2).onDataEnabledChanged;
                if (onDataEnabledChanged != null) {
                    return Boolean.valueOf(onDataEnabledChanged.enabled);
                }
                return null;
            case 14:
                CallbackEvent.OnCarrierRoamingNtnSignalStrengthChanged onCarrierRoamingNtnSignalStrengthChanged = ((TelephonyCallbackState) obj2).onCarrierRoamingNtnSignalStrengthChanged;
                if (onCarrierRoamingNtnSignalStrengthChanged == null || (ntnSignalStrength = onCarrierRoamingNtnSignalStrengthChanged.signalStrength) == null) {
                    return null;
                }
                return Integer.valueOf(ntnSignalStrength.getLevel());
            case 15:
                CallbackEvent.OnDataConnectionStateChanged onDataConnectionStateChanged = ((TelephonyCallbackState) obj2).onDataConnectionStateChanged;
                if (onDataConnectionStateChanged == null) {
                    return null;
                }
                switch (onDataConnectionStateChanged.dataState) {
                    case -1:
                        return DataConnectionState.Unknown;
                    case 0:
                        return DataConnectionState.Disconnected;
                    case 1:
                        return DataConnectionState.Connecting;
                    case 2:
                        return DataConnectionState.Connected;
                    case 3:
                        return DataConnectionState.Suspended;
                    case 4:
                        return DataConnectionState.Disconnecting;
                    case 5:
                        return DataConnectionState.HandoverInProgress;
                    default:
                        return DataConnectionState.Invalid;
                }
            case 16:
                SignalStrength signalStrength3 = (SignalStrength) obj2;
                return Integer.valueOf(signalStrength3 != null ? signalStrength3.getLevel() : 0);
            case 17:
                CallbackEvent.OnSignalStrengthChanged onSignalStrengthChanged = ((TelephonyCallbackState) obj2).onSignalStrengthChanged;
                if (onSignalStrengthChanged != null) {
                    return onSignalStrengthChanged.signalStrength;
                }
                return null;
            case 18:
                CallbackEvent.OnDataActivity onDataActivity = ((TelephonyCallbackState) obj2).onDataActivity;
                if (onDataActivity != null) {
                    return DataActivityModelKt.toMobileDataActivityModel(onDataActivity.direction);
                }
                return null;
            case 19:
                CallbackEvent.OnCarrierNetworkChange onCarrierNetworkChange = ((TelephonyCallbackState) obj2).onCarrierNetworkChange;
                if (onCarrierNetworkChange != null) {
                    return Boolean.valueOf(onCarrierNetworkChange.active);
                }
                return null;
            case 20:
                CallbackEvent.OnCarrierRoamingNtnModeChanged onCarrierRoamingNtnModeChanged = ((TelephonyCallbackState) obj2).onCarrierRoamingNtnModeChanged;
                if (onCarrierRoamingNtnModeChanged != null) {
                    return Boolean.valueOf(onCarrierRoamingNtnModeChanged.active);
                }
                return null;
            default:
                CallbackEvent.OnDisplayInfoChanged onDisplayInfoChanged2 = ((TelephonyCallbackState) obj2).onDisplayInfoChanged;
                if (onDisplayInfoChanged2 != null) {
                    return onDisplayInfoChanged2.telephonyDisplayInfo;
                }
                return null;
        }
    }
}
