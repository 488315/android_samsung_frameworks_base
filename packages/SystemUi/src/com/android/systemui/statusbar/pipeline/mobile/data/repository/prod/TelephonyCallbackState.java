package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CallbackEvent;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TelephonyCallbackState {
    public final CallbackEvent.OnCallStateChanged onCallStateChanged;
    public final CallbackEvent.OnCarrierNetworkChange onCarrierNetworkChange;
    public final CallbackEvent.OnDataActivity onDataActivity;
    public final CallbackEvent.OnDataConnectionStateChanged onDataConnectionStateChanged;
    public final CallbackEvent.OnDataEnabledChanged onDataEnabledChanged;
    public final CallbackEvent.OnDisplayInfoChanged onDisplayInfoChanged;
    public final CallbackEvent.OnServiceStateChanged onServiceStateChanged;
    public final CallbackEvent.OnSignalStrengthChanged onSignalStrengthChanged;

    public TelephonyCallbackState() {
        this(null, null, null, null, null, null, null, null, 255, null);
    }

    public static TelephonyCallbackState copy$default(TelephonyCallbackState telephonyCallbackState, CallbackEvent.OnDataActivity onDataActivity, CallbackEvent.OnCarrierNetworkChange onCarrierNetworkChange, CallbackEvent.OnDataConnectionStateChanged onDataConnectionStateChanged, CallbackEvent.OnDataEnabledChanged onDataEnabledChanged, CallbackEvent.OnDisplayInfoChanged onDisplayInfoChanged, CallbackEvent.OnServiceStateChanged onServiceStateChanged, CallbackEvent.OnSignalStrengthChanged onSignalStrengthChanged, CallbackEvent.OnCallStateChanged onCallStateChanged, int i) {
        return new TelephonyCallbackState((i & 1) != 0 ? telephonyCallbackState.onDataActivity : onDataActivity, (i & 2) != 0 ? telephonyCallbackState.onCarrierNetworkChange : onCarrierNetworkChange, (i & 4) != 0 ? telephonyCallbackState.onDataConnectionStateChanged : onDataConnectionStateChanged, (i & 8) != 0 ? telephonyCallbackState.onDataEnabledChanged : onDataEnabledChanged, (i & 16) != 0 ? telephonyCallbackState.onDisplayInfoChanged : onDisplayInfoChanged, (i & 32) != 0 ? telephonyCallbackState.onServiceStateChanged : onServiceStateChanged, (i & 64) != 0 ? telephonyCallbackState.onSignalStrengthChanged : onSignalStrengthChanged, (i & 128) != 0 ? telephonyCallbackState.onCallStateChanged : onCallStateChanged);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TelephonyCallbackState)) {
            return false;
        }
        TelephonyCallbackState telephonyCallbackState = (TelephonyCallbackState) obj;
        return Intrinsics.areEqual(this.onDataActivity, telephonyCallbackState.onDataActivity) && Intrinsics.areEqual(this.onCarrierNetworkChange, telephonyCallbackState.onCarrierNetworkChange) && Intrinsics.areEqual(this.onDataConnectionStateChanged, telephonyCallbackState.onDataConnectionStateChanged) && Intrinsics.areEqual(this.onDataEnabledChanged, telephonyCallbackState.onDataEnabledChanged) && Intrinsics.areEqual(this.onDisplayInfoChanged, telephonyCallbackState.onDisplayInfoChanged) && Intrinsics.areEqual(this.onServiceStateChanged, telephonyCallbackState.onServiceStateChanged) && Intrinsics.areEqual(this.onSignalStrengthChanged, telephonyCallbackState.onSignalStrengthChanged) && Intrinsics.areEqual(this.onCallStateChanged, telephonyCallbackState.onCallStateChanged);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int i;
        CallbackEvent.OnDataActivity onDataActivity = this.onDataActivity;
        int hashCode = (onDataActivity == null ? 0 : onDataActivity.hashCode()) * 31;
        int i2 = 1;
        CallbackEvent.OnCarrierNetworkChange onCarrierNetworkChange = this.onCarrierNetworkChange;
        if (onCarrierNetworkChange == null) {
            i = 0;
        } else {
            boolean z = onCarrierNetworkChange.active;
            i = z;
            if (z != 0) {
                i = 1;
            }
        }
        int i3 = (hashCode + i) * 31;
        CallbackEvent.OnDataConnectionStateChanged onDataConnectionStateChanged = this.onDataConnectionStateChanged;
        int hashCode2 = (i3 + (onDataConnectionStateChanged == null ? 0 : onDataConnectionStateChanged.hashCode())) * 31;
        CallbackEvent.OnDataEnabledChanged onDataEnabledChanged = this.onDataEnabledChanged;
        if (onDataEnabledChanged == null) {
            i2 = 0;
        } else {
            boolean z2 = onDataEnabledChanged.enabled;
            if (!z2) {
                i2 = z2 ? 1 : 0;
            }
        }
        int i4 = (hashCode2 + i2) * 31;
        CallbackEvent.OnDisplayInfoChanged onDisplayInfoChanged = this.onDisplayInfoChanged;
        int hashCode3 = (i4 + (onDisplayInfoChanged == null ? 0 : onDisplayInfoChanged.hashCode())) * 31;
        CallbackEvent.OnServiceStateChanged onServiceStateChanged = this.onServiceStateChanged;
        int hashCode4 = (hashCode3 + (onServiceStateChanged == null ? 0 : onServiceStateChanged.hashCode())) * 31;
        CallbackEvent.OnSignalStrengthChanged onSignalStrengthChanged = this.onSignalStrengthChanged;
        int hashCode5 = (hashCode4 + (onSignalStrengthChanged == null ? 0 : onSignalStrengthChanged.hashCode())) * 31;
        CallbackEvent.OnCallStateChanged onCallStateChanged = this.onCallStateChanged;
        return hashCode5 + (onCallStateChanged != null ? onCallStateChanged.hashCode() : 0);
    }

    public final String toString() {
        return "TelephonyCallbackState(onDataActivity=" + this.onDataActivity + ", onCarrierNetworkChange=" + this.onCarrierNetworkChange + ", onDataConnectionStateChanged=" + this.onDataConnectionStateChanged + ", onDataEnabledChanged=" + this.onDataEnabledChanged + ", onDisplayInfoChanged=" + this.onDisplayInfoChanged + ", onServiceStateChanged=" + this.onServiceStateChanged + ", onSignalStrengthChanged=" + this.onSignalStrengthChanged + ", onCallStateChanged=" + this.onCallStateChanged + ")";
    }

    public TelephonyCallbackState(CallbackEvent.OnDataActivity onDataActivity, CallbackEvent.OnCarrierNetworkChange onCarrierNetworkChange, CallbackEvent.OnDataConnectionStateChanged onDataConnectionStateChanged, CallbackEvent.OnDataEnabledChanged onDataEnabledChanged, CallbackEvent.OnDisplayInfoChanged onDisplayInfoChanged, CallbackEvent.OnServiceStateChanged onServiceStateChanged, CallbackEvent.OnSignalStrengthChanged onSignalStrengthChanged, CallbackEvent.OnCallStateChanged onCallStateChanged) {
        this.onDataActivity = onDataActivity;
        this.onCarrierNetworkChange = onCarrierNetworkChange;
        this.onDataConnectionStateChanged = onDataConnectionStateChanged;
        this.onDataEnabledChanged = onDataEnabledChanged;
        this.onDisplayInfoChanged = onDisplayInfoChanged;
        this.onServiceStateChanged = onServiceStateChanged;
        this.onSignalStrengthChanged = onSignalStrengthChanged;
        this.onCallStateChanged = onCallStateChanged;
    }

    public /* synthetic */ TelephonyCallbackState(CallbackEvent.OnDataActivity onDataActivity, CallbackEvent.OnCarrierNetworkChange onCarrierNetworkChange, CallbackEvent.OnDataConnectionStateChanged onDataConnectionStateChanged, CallbackEvent.OnDataEnabledChanged onDataEnabledChanged, CallbackEvent.OnDisplayInfoChanged onDisplayInfoChanged, CallbackEvent.OnServiceStateChanged onServiceStateChanged, CallbackEvent.OnSignalStrengthChanged onSignalStrengthChanged, CallbackEvent.OnCallStateChanged onCallStateChanged, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : onDataActivity, (i & 2) != 0 ? null : onCarrierNetworkChange, (i & 4) != 0 ? null : onDataConnectionStateChanged, (i & 8) != 0 ? null : onDataEnabledChanged, (i & 16) != 0 ? null : onDisplayInfoChanged, (i & 32) != 0 ? null : onServiceStateChanged, (i & 64) != 0 ? null : onSignalStrengthChanged, (i & 128) != 0 ? null : onCallStateChanged);
    }
}
