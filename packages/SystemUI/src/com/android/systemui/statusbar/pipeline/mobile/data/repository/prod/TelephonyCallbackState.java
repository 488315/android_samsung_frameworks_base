package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CallbackEvent;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class TelephonyCallbackState {
    public final CallbackEvent.OnCallStateChanged onCallStateChanged;
    public final CallbackEvent.OnCarrierNetworkChange onCarrierNetworkChange;
    public final CallbackEvent.OnCarrierRoamingNtnModeChanged onCarrierRoamingNtnModeChanged;
    public final CallbackEvent.OnCarrierRoamingNtnSignalStrengthChanged onCarrierRoamingNtnSignalStrengthChanged;
    public final CallbackEvent.OnDataActivity onDataActivity;
    public final CallbackEvent.OnDataConnectionStateChanged onDataConnectionStateChanged;
    public final CallbackEvent.OnDataEnabledChanged onDataEnabledChanged;
    public final CallbackEvent.OnDisplayInfoChanged onDisplayInfoChanged;
    public final CallbackEvent.onSemSatelliteServiceStateChanged onSemSatelliteServiceStateChanged;
    public final CallbackEvent.onSemSatelliteSignalStrengthChanged onSemSatelliteSignalStrengthChanged;
    public final CallbackEvent.OnServiceStateChanged onServiceStateChanged;
    public final CallbackEvent.OnSignalStrengthChanged onSignalStrengthChanged;

    public TelephonyCallbackState() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, 4095, null);
    }

    public static TelephonyCallbackState copy$default(TelephonyCallbackState telephonyCallbackState, CallbackEvent.OnDataActivity onDataActivity, CallbackEvent.OnCarrierNetworkChange onCarrierNetworkChange, CallbackEvent.OnCarrierRoamingNtnModeChanged onCarrierRoamingNtnModeChanged, CallbackEvent.OnDataConnectionStateChanged onDataConnectionStateChanged, CallbackEvent.OnDataEnabledChanged onDataEnabledChanged, CallbackEvent.OnDisplayInfoChanged onDisplayInfoChanged, CallbackEvent.OnServiceStateChanged onServiceStateChanged, CallbackEvent.OnSignalStrengthChanged onSignalStrengthChanged, CallbackEvent.OnCarrierRoamingNtnSignalStrengthChanged onCarrierRoamingNtnSignalStrengthChanged, CallbackEvent.OnCallStateChanged onCallStateChanged, CallbackEvent.onSemSatelliteServiceStateChanged onsemsatelliteservicestatechanged, CallbackEvent.onSemSatelliteSignalStrengthChanged onsemsatellitesignalstrengthchanged, int i) {
        CallbackEvent.OnDataActivity onDataActivity2 = (i & 1) != 0 ? telephonyCallbackState.onDataActivity : onDataActivity;
        CallbackEvent.OnCarrierNetworkChange onCarrierNetworkChange2 = (i & 2) != 0 ? telephonyCallbackState.onCarrierNetworkChange : onCarrierNetworkChange;
        CallbackEvent.OnCarrierRoamingNtnModeChanged onCarrierRoamingNtnModeChanged2 = (i & 4) != 0 ? telephonyCallbackState.onCarrierRoamingNtnModeChanged : onCarrierRoamingNtnModeChanged;
        CallbackEvent.OnDataConnectionStateChanged onDataConnectionStateChanged2 = (i & 8) != 0 ? telephonyCallbackState.onDataConnectionStateChanged : onDataConnectionStateChanged;
        CallbackEvent.OnDataEnabledChanged onDataEnabledChanged2 = (i & 16) != 0 ? telephonyCallbackState.onDataEnabledChanged : onDataEnabledChanged;
        CallbackEvent.OnDisplayInfoChanged onDisplayInfoChanged2 = (i & 32) != 0 ? telephonyCallbackState.onDisplayInfoChanged : onDisplayInfoChanged;
        CallbackEvent.OnServiceStateChanged onServiceStateChanged2 = (i & 64) != 0 ? telephonyCallbackState.onServiceStateChanged : onServiceStateChanged;
        CallbackEvent.OnSignalStrengthChanged onSignalStrengthChanged2 = (i & 128) != 0 ? telephonyCallbackState.onSignalStrengthChanged : onSignalStrengthChanged;
        CallbackEvent.OnCarrierRoamingNtnSignalStrengthChanged onCarrierRoamingNtnSignalStrengthChanged2 = (i & 256) != 0 ? telephonyCallbackState.onCarrierRoamingNtnSignalStrengthChanged : onCarrierRoamingNtnSignalStrengthChanged;
        CallbackEvent.OnCallStateChanged onCallStateChanged2 = (i & 512) != 0 ? telephonyCallbackState.onCallStateChanged : onCallStateChanged;
        CallbackEvent.onSemSatelliteServiceStateChanged onsemsatelliteservicestatechanged2 = (i & 1024) != 0 ? telephonyCallbackState.onSemSatelliteServiceStateChanged : onsemsatelliteservicestatechanged;
        CallbackEvent.onSemSatelliteSignalStrengthChanged onsemsatellitesignalstrengthchanged2 = (i & 2048) != 0 ? telephonyCallbackState.onSemSatelliteSignalStrengthChanged : onsemsatellitesignalstrengthchanged;
        telephonyCallbackState.getClass();
        return new TelephonyCallbackState(onDataActivity2, onCarrierNetworkChange2, onCarrierRoamingNtnModeChanged2, onDataConnectionStateChanged2, onDataEnabledChanged2, onDisplayInfoChanged2, onServiceStateChanged2, onSignalStrengthChanged2, onCarrierRoamingNtnSignalStrengthChanged2, onCallStateChanged2, onsemsatelliteservicestatechanged2, onsemsatellitesignalstrengthchanged2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TelephonyCallbackState)) {
            return false;
        }
        TelephonyCallbackState telephonyCallbackState = (TelephonyCallbackState) obj;
        return Intrinsics.areEqual(this.onDataActivity, telephonyCallbackState.onDataActivity) && Intrinsics.areEqual(this.onCarrierNetworkChange, telephonyCallbackState.onCarrierNetworkChange) && Intrinsics.areEqual(this.onCarrierRoamingNtnModeChanged, telephonyCallbackState.onCarrierRoamingNtnModeChanged) && Intrinsics.areEqual(this.onDataConnectionStateChanged, telephonyCallbackState.onDataConnectionStateChanged) && Intrinsics.areEqual(this.onDataEnabledChanged, telephonyCallbackState.onDataEnabledChanged) && Intrinsics.areEqual(this.onDisplayInfoChanged, telephonyCallbackState.onDisplayInfoChanged) && Intrinsics.areEqual(this.onServiceStateChanged, telephonyCallbackState.onServiceStateChanged) && Intrinsics.areEqual(this.onSignalStrengthChanged, telephonyCallbackState.onSignalStrengthChanged) && Intrinsics.areEqual(this.onCarrierRoamingNtnSignalStrengthChanged, telephonyCallbackState.onCarrierRoamingNtnSignalStrengthChanged) && Intrinsics.areEqual(this.onCallStateChanged, telephonyCallbackState.onCallStateChanged) && Intrinsics.areEqual(this.onSemSatelliteServiceStateChanged, telephonyCallbackState.onSemSatelliteServiceStateChanged) && Intrinsics.areEqual(this.onSemSatelliteSignalStrengthChanged, telephonyCallbackState.onSemSatelliteSignalStrengthChanged);
    }

    public final int hashCode() {
        CallbackEvent.OnDataActivity onDataActivity = this.onDataActivity;
        int hashCode = (onDataActivity == null ? 0 : Integer.hashCode(onDataActivity.direction)) * 31;
        CallbackEvent.OnCarrierNetworkChange onCarrierNetworkChange = this.onCarrierNetworkChange;
        int hashCode2 = (hashCode + (onCarrierNetworkChange == null ? 0 : Boolean.hashCode(onCarrierNetworkChange.active))) * 31;
        CallbackEvent.OnCarrierRoamingNtnModeChanged onCarrierRoamingNtnModeChanged = this.onCarrierRoamingNtnModeChanged;
        int hashCode3 = (hashCode2 + (onCarrierRoamingNtnModeChanged == null ? 0 : Boolean.hashCode(onCarrierRoamingNtnModeChanged.active))) * 31;
        CallbackEvent.OnDataConnectionStateChanged onDataConnectionStateChanged = this.onDataConnectionStateChanged;
        int hashCode4 = (hashCode3 + (onDataConnectionStateChanged == null ? 0 : Integer.hashCode(onDataConnectionStateChanged.dataState))) * 31;
        CallbackEvent.OnDataEnabledChanged onDataEnabledChanged = this.onDataEnabledChanged;
        int hashCode5 = (hashCode4 + (onDataEnabledChanged == null ? 0 : Boolean.hashCode(onDataEnabledChanged.enabled))) * 31;
        CallbackEvent.OnDisplayInfoChanged onDisplayInfoChanged = this.onDisplayInfoChanged;
        int hashCode6 = (hashCode5 + (onDisplayInfoChanged == null ? 0 : onDisplayInfoChanged.telephonyDisplayInfo.hashCode())) * 31;
        CallbackEvent.OnServiceStateChanged onServiceStateChanged = this.onServiceStateChanged;
        int hashCode7 = (hashCode6 + (onServiceStateChanged == null ? 0 : onServiceStateChanged.serviceState.hashCode())) * 31;
        CallbackEvent.OnSignalStrengthChanged onSignalStrengthChanged = this.onSignalStrengthChanged;
        int hashCode8 = (hashCode7 + (onSignalStrengthChanged == null ? 0 : onSignalStrengthChanged.signalStrength.hashCode())) * 31;
        CallbackEvent.OnCarrierRoamingNtnSignalStrengthChanged onCarrierRoamingNtnSignalStrengthChanged = this.onCarrierRoamingNtnSignalStrengthChanged;
        int hashCode9 = (hashCode8 + (onCarrierRoamingNtnSignalStrengthChanged == null ? 0 : onCarrierRoamingNtnSignalStrengthChanged.signalStrength.hashCode())) * 31;
        CallbackEvent.OnCallStateChanged onCallStateChanged = this.onCallStateChanged;
        int hashCode10 = (hashCode9 + (onCallStateChanged == null ? 0 : Integer.hashCode(onCallStateChanged.callState))) * 31;
        CallbackEvent.onSemSatelliteServiceStateChanged onsemsatelliteservicestatechanged = this.onSemSatelliteServiceStateChanged;
        int hashCode11 = (hashCode10 + (onsemsatelliteservicestatechanged == null ? 0 : onsemsatelliteservicestatechanged.serviceState.hashCode())) * 31;
        CallbackEvent.onSemSatelliteSignalStrengthChanged onsemsatellitesignalstrengthchanged = this.onSemSatelliteSignalStrengthChanged;
        return hashCode11 + (onsemsatellitesignalstrengthchanged != null ? onsemsatellitesignalstrengthchanged.signalStrength.hashCode() : 0);
    }

    public final String toString() {
        return "TelephonyCallbackState(onDataActivity=" + this.onDataActivity + ", onCarrierNetworkChange=" + this.onCarrierNetworkChange + ", onCarrierRoamingNtnModeChanged=" + this.onCarrierRoamingNtnModeChanged + ", onDataConnectionStateChanged=" + this.onDataConnectionStateChanged + ", onDataEnabledChanged=" + this.onDataEnabledChanged + ", onDisplayInfoChanged=" + this.onDisplayInfoChanged + ", onServiceStateChanged=" + this.onServiceStateChanged + ", onSignalStrengthChanged=" + this.onSignalStrengthChanged + ", onCarrierRoamingNtnSignalStrengthChanged=" + this.onCarrierRoamingNtnSignalStrengthChanged + ", onCallStateChanged=" + this.onCallStateChanged + ", onSemSatelliteServiceStateChanged=" + this.onSemSatelliteServiceStateChanged + ", onSemSatelliteSignalStrengthChanged=" + this.onSemSatelliteSignalStrengthChanged + ")";
    }

    public TelephonyCallbackState(CallbackEvent.OnDataActivity onDataActivity, CallbackEvent.OnCarrierNetworkChange onCarrierNetworkChange, CallbackEvent.OnCarrierRoamingNtnModeChanged onCarrierRoamingNtnModeChanged, CallbackEvent.OnDataConnectionStateChanged onDataConnectionStateChanged, CallbackEvent.OnDataEnabledChanged onDataEnabledChanged, CallbackEvent.OnDisplayInfoChanged onDisplayInfoChanged, CallbackEvent.OnServiceStateChanged onServiceStateChanged, CallbackEvent.OnSignalStrengthChanged onSignalStrengthChanged, CallbackEvent.OnCarrierRoamingNtnSignalStrengthChanged onCarrierRoamingNtnSignalStrengthChanged, CallbackEvent.OnCallStateChanged onCallStateChanged, CallbackEvent.onSemSatelliteServiceStateChanged onsemsatelliteservicestatechanged, CallbackEvent.onSemSatelliteSignalStrengthChanged onsemsatellitesignalstrengthchanged) {
        this.onDataActivity = onDataActivity;
        this.onCarrierNetworkChange = onCarrierNetworkChange;
        this.onCarrierRoamingNtnModeChanged = onCarrierRoamingNtnModeChanged;
        this.onDataConnectionStateChanged = onDataConnectionStateChanged;
        this.onDataEnabledChanged = onDataEnabledChanged;
        this.onDisplayInfoChanged = onDisplayInfoChanged;
        this.onServiceStateChanged = onServiceStateChanged;
        this.onSignalStrengthChanged = onSignalStrengthChanged;
        this.onCarrierRoamingNtnSignalStrengthChanged = onCarrierRoamingNtnSignalStrengthChanged;
        this.onCallStateChanged = onCallStateChanged;
        this.onSemSatelliteServiceStateChanged = onsemsatelliteservicestatechanged;
        this.onSemSatelliteSignalStrengthChanged = onsemsatellitesignalstrengthchanged;
    }

    public /* synthetic */ TelephonyCallbackState(CallbackEvent.OnDataActivity onDataActivity, CallbackEvent.OnCarrierNetworkChange onCarrierNetworkChange, CallbackEvent.OnCarrierRoamingNtnModeChanged onCarrierRoamingNtnModeChanged, CallbackEvent.OnDataConnectionStateChanged onDataConnectionStateChanged, CallbackEvent.OnDataEnabledChanged onDataEnabledChanged, CallbackEvent.OnDisplayInfoChanged onDisplayInfoChanged, CallbackEvent.OnServiceStateChanged onServiceStateChanged, CallbackEvent.OnSignalStrengthChanged onSignalStrengthChanged, CallbackEvent.OnCarrierRoamingNtnSignalStrengthChanged onCarrierRoamingNtnSignalStrengthChanged, CallbackEvent.OnCallStateChanged onCallStateChanged, CallbackEvent.onSemSatelliteServiceStateChanged onsemsatelliteservicestatechanged, CallbackEvent.onSemSatelliteSignalStrengthChanged onsemsatellitesignalstrengthchanged, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : onDataActivity, (i & 2) != 0 ? null : onCarrierNetworkChange, (i & 4) != 0 ? null : onCarrierRoamingNtnModeChanged, (i & 8) != 0 ? null : onDataConnectionStateChanged, (i & 16) != 0 ? null : onDataEnabledChanged, (i & 32) != 0 ? null : onDisplayInfoChanged, (i & 64) != 0 ? null : onServiceStateChanged, (i & 128) != 0 ? null : onSignalStrengthChanged, (i & 256) != 0 ? null : onCarrierRoamingNtnSignalStrengthChanged, (i & 512) != 0 ? null : onCallStateChanged, (i & 1024) != 0 ? null : onsemsatelliteservicestatechanged, (i & 2048) != 0 ? null : onsemsatellitesignalstrengthchanged);
    }
}
