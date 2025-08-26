package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.BarringInfo;
import android.telephony.CallState;
import android.telephony.CellIdentity;
import android.telephony.CellInfo;
import android.telephony.CellularIdentifierDisclosure;
import android.telephony.DataConnectionRealTimeInfo;
import android.telephony.LinkCapacityEstimate;
import android.telephony.PhoneCapability;
import android.telephony.PhysicalChannelConfig;
import android.telephony.PreciseCallState;
import android.telephony.PreciseDataConnectionState;
import android.telephony.SecurityAlgorithmUpdate;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.MediaQualityStatus;
import android.telephony.satellite.NtnSignalStrength;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public interface IPhoneStateListener extends IInterface {

    public static class Default implements IPhoneStateListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onActiveDataSubIdChanged(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onAllowedNetworkTypesChanged(int i, long j) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onBarringInfoChanged(BarringInfo barringInfo) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallDisconnectCauseChanged(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallForwardingIndicatorChanged(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStateChanged(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStatesChanged(List<CallState> list) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallbackModeRestarted(int i, long j, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallbackModeStarted(int i, long j, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallbackModeStopped(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierNetworkChange(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierRoamingNtnAvailableServicesChanged(int[] iArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierRoamingNtnEligibleStateChanged(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierRoamingNtnModeChanged(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierRoamingNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellInfoChanged(List<CellInfo> list) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellLocationChanged(CellIdentity cellIdentity) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellularIdentifierDisclosedChanged(CellularIdentifierDisclosure cellularIdentifierDisclosure) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCpaiDataGatheringNotified(int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCpaiDevAppMessageNotified(int i, int i2, int i3, byte[] bArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCpaiFeatureInfoNotified(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCpaiModelUpdateNotified(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivationStateChanged(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivity(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dataConnectionRealTimeInfo) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionStateChanged(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataEnabledChanged(boolean z, int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onEmergencyNumberListChanged(Map map) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onImsCallDisconnectCauseChanged(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onLegacyCallStateChanged(int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onLinkCapacityEstimateChanged(List<LinkCapacityEstimate> list) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onMediaQualityStatusChanged(MediaQualityStatus mediaQualityStatus) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onMessageWaitingIndicatorChanged(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOemHookRawEvent(byte[] bArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOutgoingEmergencyCall(EmergencyNumber emergencyNumber, int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOutgoingEmergencySms(EmergencyNumber emergencyNumber, int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhoneCapabilityChanged(PhoneCapability phoneCapability) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhysicalChannelConfigChanged(List<PhysicalChannelConfig> list) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseCallStateChanged(PreciseCallState preciseCallState) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState preciseDataConnectionState) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRadioPowerStateChanged(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRegistrationFailed(CellIdentity cellIdentity, String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSecurityAlgorithmsChanged(SecurityAlgorithmUpdate securityAlgorithmUpdate) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSemSatelliteServiceStateChanged(SemSatelliteServiceState semSatelliteServiceState) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSemSatelliteSignalStrengthChanged(SemSatelliteSignalStrength semSatelliteSignalStrength) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onServiceStateChanged(ServiceState serviceState) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthChanged(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthsChanged(SignalStrength signalStrength) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSimultaneousCallingStateChanged(int[] iArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSrvccStateChanged(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onUserMobileDataStateChanged(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onVoiceActivationStateChanged(int i) throws RemoteException {
        }
    }

    void onActiveDataSubIdChanged(int i) throws RemoteException;

    void onAllowedNetworkTypesChanged(int i, long j) throws RemoteException;

    void onBarringInfoChanged(BarringInfo barringInfo) throws RemoteException;

    void onCallDisconnectCauseChanged(int i, int i2) throws RemoteException;

    void onCallForwardingIndicatorChanged(boolean z) throws RemoteException;

    void onCallStateChanged(int i) throws RemoteException;

    void onCallStatesChanged(List<CallState> list) throws RemoteException;

    void onCallbackModeRestarted(int i, long j, int i2) throws RemoteException;

    void onCallbackModeStarted(int i, long j, int i2) throws RemoteException;

    void onCallbackModeStopped(int i, int i2, int i3) throws RemoteException;

    void onCarrierNetworkChange(boolean z) throws RemoteException;

    void onCarrierRoamingNtnAvailableServicesChanged(int[] iArr) throws RemoteException;

    void onCarrierRoamingNtnEligibleStateChanged(boolean z) throws RemoteException;

    void onCarrierRoamingNtnModeChanged(boolean z) throws RemoteException;

    void onCarrierRoamingNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) throws RemoteException;

    void onCellInfoChanged(List<CellInfo> list) throws RemoteException;

    void onCellLocationChanged(CellIdentity cellIdentity) throws RemoteException;

    void onCellularIdentifierDisclosedChanged(CellularIdentifierDisclosure cellularIdentifierDisclosure) throws RemoteException;

    void onCpaiDataGatheringNotified(int i, int i2, byte[] bArr) throws RemoteException;

    void onCpaiDevAppMessageNotified(int i, int i2, int i3, byte[] bArr) throws RemoteException;

    void onCpaiFeatureInfoNotified(int i, int i2) throws RemoteException;

    void onCpaiModelUpdateNotified(int i, int i2) throws RemoteException;

    void onDataActivationStateChanged(int i) throws RemoteException;

    void onDataActivity(int i) throws RemoteException;

    void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dataConnectionRealTimeInfo) throws RemoteException;

    void onDataConnectionStateChanged(int i, int i2) throws RemoteException;

    void onDataEnabledChanged(boolean z, int i) throws RemoteException;

    void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) throws RemoteException;

    void onEmergencyNumberListChanged(Map map) throws RemoteException;

    void onImsCallDisconnectCauseChanged(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void onLegacyCallStateChanged(int i, String str) throws RemoteException;

    void onLinkCapacityEstimateChanged(List<LinkCapacityEstimate> list) throws RemoteException;

    void onMediaQualityStatusChanged(MediaQualityStatus mediaQualityStatus) throws RemoteException;

    void onMessageWaitingIndicatorChanged(boolean z) throws RemoteException;

    void onOemHookRawEvent(byte[] bArr) throws RemoteException;

    void onOutgoingEmergencyCall(EmergencyNumber emergencyNumber, int i) throws RemoteException;

    void onOutgoingEmergencySms(EmergencyNumber emergencyNumber, int i) throws RemoteException;

    void onPhoneCapabilityChanged(PhoneCapability phoneCapability) throws RemoteException;

    void onPhysicalChannelConfigChanged(List<PhysicalChannelConfig> list) throws RemoteException;

    void onPreciseCallStateChanged(PreciseCallState preciseCallState) throws RemoteException;

    void onPreciseDataConnectionStateChanged(PreciseDataConnectionState preciseDataConnectionState) throws RemoteException;

    void onRadioPowerStateChanged(int i) throws RemoteException;

    void onRegistrationFailed(CellIdentity cellIdentity, String str, int i, int i2, int i3) throws RemoteException;

    void onSecurityAlgorithmsChanged(SecurityAlgorithmUpdate securityAlgorithmUpdate) throws RemoteException;

    void onSemSatelliteServiceStateChanged(SemSatelliteServiceState semSatelliteServiceState) throws RemoteException;

    void onSemSatelliteSignalStrengthChanged(SemSatelliteSignalStrength semSatelliteSignalStrength) throws RemoteException;

    void onServiceStateChanged(ServiceState serviceState) throws RemoteException;

    void onSignalStrengthChanged(int i) throws RemoteException;

    void onSignalStrengthsChanged(SignalStrength signalStrength) throws RemoteException;

    void onSimultaneousCallingStateChanged(int[] iArr) throws RemoteException;

    void onSrvccStateChanged(int i) throws RemoteException;

    void onUserMobileDataStateChanged(boolean z) throws RemoteException;

    void onVoiceActivationStateChanged(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IPhoneStateListener {
        public static final String DESCRIPTOR = "com.android.internal.telephony.IPhoneStateListener";
        static final int TRANSACTION_onActiveDataSubIdChanged = 23;
        static final int TRANSACTION_onAllowedNetworkTypesChanged = 35;
        static final int TRANSACTION_onBarringInfoChanged = 32;
        static final int TRANSACTION_onCallDisconnectCauseChanged = 29;
        static final int TRANSACTION_onCallForwardingIndicatorChanged = 4;
        static final int TRANSACTION_onCallStateChanged = 7;
        static final int TRANSACTION_onCallStatesChanged = 25;
        static final int TRANSACTION_onCallbackModeRestarted = 39;
        static final int TRANSACTION_onCallbackModeStarted = 38;
        static final int TRANSACTION_onCallbackModeStopped = 40;
        static final int TRANSACTION_onCarrierNetworkChange = 19;
        static final int TRANSACTION_onCarrierRoamingNtnAvailableServicesChanged = 44;
        static final int TRANSACTION_onCarrierRoamingNtnEligibleStateChanged = 43;
        static final int TRANSACTION_onCarrierRoamingNtnModeChanged = 42;
        static final int TRANSACTION_onCarrierRoamingNtnSignalStrengthChanged = 45;
        static final int TRANSACTION_onCellInfoChanged = 11;
        static final int TRANSACTION_onCellLocationChanged = 5;
        static final int TRANSACTION_onCellularIdentifierDisclosedChanged = 47;
        static final int TRANSACTION_onCpaiDataGatheringNotified = 52;
        static final int TRANSACTION_onCpaiDevAppMessageNotified = 53;
        static final int TRANSACTION_onCpaiFeatureInfoNotified = 51;
        static final int TRANSACTION_onCpaiModelUpdateNotified = 50;
        static final int TRANSACTION_onDataActivationStateChanged = 17;
        static final int TRANSACTION_onDataActivity = 9;
        static final int TRANSACTION_onDataConnectionRealTimeInfoChanged = 14;
        static final int TRANSACTION_onDataConnectionStateChanged = 8;
        static final int TRANSACTION_onDataEnabledChanged = 34;
        static final int TRANSACTION_onDisplayInfoChanged = 21;
        static final int TRANSACTION_onEmergencyNumberListChanged = 26;
        static final int TRANSACTION_onImsCallDisconnectCauseChanged = 30;
        static final int TRANSACTION_onLegacyCallStateChanged = 6;
        static final int TRANSACTION_onLinkCapacityEstimateChanged = 36;
        static final int TRANSACTION_onMediaQualityStatusChanged = 37;
        static final int TRANSACTION_onMessageWaitingIndicatorChanged = 3;
        static final int TRANSACTION_onOemHookRawEvent = 18;
        static final int TRANSACTION_onOutgoingEmergencyCall = 27;
        static final int TRANSACTION_onOutgoingEmergencySms = 28;
        static final int TRANSACTION_onPhoneCapabilityChanged = 22;
        static final int TRANSACTION_onPhysicalChannelConfigChanged = 33;
        static final int TRANSACTION_onPreciseCallStateChanged = 12;
        static final int TRANSACTION_onPreciseDataConnectionStateChanged = 13;
        static final int TRANSACTION_onRadioPowerStateChanged = 24;
        static final int TRANSACTION_onRegistrationFailed = 31;
        static final int TRANSACTION_onSecurityAlgorithmsChanged = 46;
        static final int TRANSACTION_onSemSatelliteServiceStateChanged = 48;
        static final int TRANSACTION_onSemSatelliteSignalStrengthChanged = 49;
        static final int TRANSACTION_onServiceStateChanged = 1;
        static final int TRANSACTION_onSignalStrengthChanged = 2;
        static final int TRANSACTION_onSignalStrengthsChanged = 10;
        static final int TRANSACTION_onSimultaneousCallingStateChanged = 41;
        static final int TRANSACTION_onSrvccStateChanged = 15;
        static final int TRANSACTION_onUserMobileDataStateChanged = 20;
        static final int TRANSACTION_onVoiceActivationStateChanged = 16;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 52;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPhoneStateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPhoneStateListener)) {
                return (IPhoneStateListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onServiceStateChanged";
                case 2:
                    return "onSignalStrengthChanged";
                case 3:
                    return "onMessageWaitingIndicatorChanged";
                case 4:
                    return "onCallForwardingIndicatorChanged";
                case 5:
                    return "onCellLocationChanged";
                case 6:
                    return "onLegacyCallStateChanged";
                case 7:
                    return "onCallStateChanged";
                case 8:
                    return "onDataConnectionStateChanged";
                case 9:
                    return "onDataActivity";
                case 10:
                    return "onSignalStrengthsChanged";
                case 11:
                    return "onCellInfoChanged";
                case 12:
                    return "onPreciseCallStateChanged";
                case 13:
                    return "onPreciseDataConnectionStateChanged";
                case 14:
                    return "onDataConnectionRealTimeInfoChanged";
                case 15:
                    return "onSrvccStateChanged";
                case 16:
                    return "onVoiceActivationStateChanged";
                case 17:
                    return "onDataActivationStateChanged";
                case 18:
                    return "onOemHookRawEvent";
                case 19:
                    return "onCarrierNetworkChange";
                case 20:
                    return "onUserMobileDataStateChanged";
                case 21:
                    return "onDisplayInfoChanged";
                case 22:
                    return "onPhoneCapabilityChanged";
                case 23:
                    return "onActiveDataSubIdChanged";
                case 24:
                    return "onRadioPowerStateChanged";
                case 25:
                    return "onCallStatesChanged";
                case 26:
                    return "onEmergencyNumberListChanged";
                case 27:
                    return "onOutgoingEmergencyCall";
                case 28:
                    return "onOutgoingEmergencySms";
                case 29:
                    return "onCallDisconnectCauseChanged";
                case 30:
                    return "onImsCallDisconnectCauseChanged";
                case 31:
                    return "onRegistrationFailed";
                case 32:
                    return "onBarringInfoChanged";
                case 33:
                    return "onPhysicalChannelConfigChanged";
                case 34:
                    return "onDataEnabledChanged";
                case 35:
                    return "onAllowedNetworkTypesChanged";
                case 36:
                    return "onLinkCapacityEstimateChanged";
                case 37:
                    return "onMediaQualityStatusChanged";
                case 38:
                    return "onCallbackModeStarted";
                case 39:
                    return "onCallbackModeRestarted";
                case 40:
                    return "onCallbackModeStopped";
                case 41:
                    return "onSimultaneousCallingStateChanged";
                case 42:
                    return "onCarrierRoamingNtnModeChanged";
                case 43:
                    return "onCarrierRoamingNtnEligibleStateChanged";
                case 44:
                    return "onCarrierRoamingNtnAvailableServicesChanged";
                case 45:
                    return "onCarrierRoamingNtnSignalStrengthChanged";
                case 46:
                    return "onSecurityAlgorithmsChanged";
                case 47:
                    return "onCellularIdentifierDisclosedChanged";
                case 48:
                    return "onSemSatelliteServiceStateChanged";
                case 49:
                    return "onSemSatelliteSignalStrengthChanged";
                case 50:
                    return "onCpaiModelUpdateNotified";
                case 51:
                    return "onCpaiFeatureInfoNotified";
                case 52:
                    return "onCpaiDataGatheringNotified";
                case 53:
                    return "onCpaiDevAppMessageNotified";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ServiceState serviceState = (ServiceState) parcel.readTypedObject(ServiceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onServiceStateChanged(serviceState);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSignalStrengthChanged(i3);
                    return true;
                case 3:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onMessageWaitingIndicatorChanged(z);
                    return true;
                case 4:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCallForwardingIndicatorChanged(z2);
                    return true;
                case 5:
                    CellIdentity cellIdentity = (CellIdentity) parcel.readTypedObject(CellIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCellLocationChanged(cellIdentity);
                    return true;
                case 6:
                    int i4 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onLegacyCallStateChanged(i4, string);
                    return true;
                case 7:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallStateChanged(i5);
                    return true;
                case 8:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDataConnectionStateChanged(i6, i7);
                    return true;
                case 9:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDataActivity(i8);
                    return true;
                case 10:
                    SignalStrength signalStrength = (SignalStrength) parcel.readTypedObject(SignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSignalStrengthsChanged(signalStrength);
                    return true;
                case 11:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(CellInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCellInfoChanged(arrayListCreateTypedArrayList);
                    return true;
                case 12:
                    PreciseCallState preciseCallState = (PreciseCallState) parcel.readTypedObject(PreciseCallState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPreciseCallStateChanged(preciseCallState);
                    return true;
                case 13:
                    PreciseDataConnectionState preciseDataConnectionState = (PreciseDataConnectionState) parcel.readTypedObject(PreciseDataConnectionState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPreciseDataConnectionStateChanged(preciseDataConnectionState);
                    return true;
                case 14:
                    DataConnectionRealTimeInfo dataConnectionRealTimeInfo = (DataConnectionRealTimeInfo) parcel.readTypedObject(DataConnectionRealTimeInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDataConnectionRealTimeInfoChanged(dataConnectionRealTimeInfo);
                    return true;
                case 15:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSrvccStateChanged(i9);
                    return true;
                case 16:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVoiceActivationStateChanged(i10);
                    return true;
                case 17:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDataActivationStateChanged(i11);
                    return true;
                case 18:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onOemHookRawEvent(bArrCreateByteArray);
                    return true;
                case 19:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCarrierNetworkChange(z3);
                    return true;
                case 20:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onUserMobileDataStateChanged(z4);
                    return true;
                case 21:
                    TelephonyDisplayInfo telephonyDisplayInfo = (TelephonyDisplayInfo) parcel.readTypedObject(TelephonyDisplayInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDisplayInfoChanged(telephonyDisplayInfo);
                    return true;
                case 22:
                    PhoneCapability phoneCapability = (PhoneCapability) parcel.readTypedObject(PhoneCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPhoneCapabilityChanged(phoneCapability);
                    return true;
                case 23:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActiveDataSubIdChanged(i12);
                    return true;
                case 24:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRadioPowerStateChanged(i13);
                    return true;
                case 25:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(CallState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallStatesChanged(arrayListCreateTypedArrayList2);
                    return true;
                case 26:
                    HashMap hashMap = parcel.readHashMap(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    onEmergencyNumberListChanged(hashMap);
                    return true;
                case 27:
                    EmergencyNumber emergencyNumber = (EmergencyNumber) parcel.readTypedObject(EmergencyNumber.CREATOR);
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onOutgoingEmergencyCall(emergencyNumber, i14);
                    return true;
                case 28:
                    EmergencyNumber emergencyNumber2 = (EmergencyNumber) parcel.readTypedObject(EmergencyNumber.CREATOR);
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onOutgoingEmergencySms(emergencyNumber2, i15);
                    return true;
                case 29:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallDisconnectCauseChanged(i16, i17);
                    return true;
                case 30:
                    ImsReasonInfo imsReasonInfo = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onImsCallDisconnectCauseChanged(imsReasonInfo);
                    return true;
                case 31:
                    CellIdentity cellIdentity2 = (CellIdentity) parcel.readTypedObject(CellIdentity.CREATOR);
                    String string2 = parcel.readString();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRegistrationFailed(cellIdentity2, string2, i18, i19, i20);
                    return true;
                case 32:
                    BarringInfo barringInfo = (BarringInfo) parcel.readTypedObject(BarringInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBarringInfoChanged(barringInfo);
                    return true;
                case 33:
                    ArrayList arrayListCreateTypedArrayList3 = parcel.createTypedArrayList(PhysicalChannelConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPhysicalChannelConfigChanged(arrayListCreateTypedArrayList3);
                    return true;
                case 34:
                    boolean z5 = parcel.readBoolean();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDataEnabledChanged(z5, i21);
                    return true;
                case 35:
                    int i22 = parcel.readInt();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onAllowedNetworkTypesChanged(i22, j);
                    return true;
                case 36:
                    ArrayList arrayListCreateTypedArrayList4 = parcel.createTypedArrayList(LinkCapacityEstimate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onLinkCapacityEstimateChanged(arrayListCreateTypedArrayList4);
                    return true;
                case 37:
                    MediaQualityStatus mediaQualityStatus = (MediaQualityStatus) parcel.readTypedObject(MediaQualityStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMediaQualityStatusChanged(mediaQualityStatus);
                    return true;
                case 38:
                    int i23 = parcel.readInt();
                    long j2 = parcel.readLong();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallbackModeStarted(i23, j2, i24);
                    return true;
                case 39:
                    int i25 = parcel.readInt();
                    long j3 = parcel.readLong();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallbackModeRestarted(i25, j3, i26);
                    return true;
                case 40:
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallbackModeStopped(i27, i28, i29);
                    return true;
                case 41:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onSimultaneousCallingStateChanged(iArrCreateIntArray);
                    return true;
                case 42:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCarrierRoamingNtnModeChanged(z6);
                    return true;
                case 43:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCarrierRoamingNtnEligibleStateChanged(z7);
                    return true;
                case 44:
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onCarrierRoamingNtnAvailableServicesChanged(iArrCreateIntArray2);
                    return true;
                case 45:
                    NtnSignalStrength ntnSignalStrength = (NtnSignalStrength) parcel.readTypedObject(NtnSignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCarrierRoamingNtnSignalStrengthChanged(ntnSignalStrength);
                    return true;
                case 46:
                    SecurityAlgorithmUpdate securityAlgorithmUpdate = (SecurityAlgorithmUpdate) parcel.readTypedObject(SecurityAlgorithmUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSecurityAlgorithmsChanged(securityAlgorithmUpdate);
                    return true;
                case 47:
                    CellularIdentifierDisclosure cellularIdentifierDisclosure = (CellularIdentifierDisclosure) parcel.readTypedObject(CellularIdentifierDisclosure.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCellularIdentifierDisclosedChanged(cellularIdentifierDisclosure);
                    return true;
                case 48:
                    SemSatelliteServiceState semSatelliteServiceState = (SemSatelliteServiceState) parcel.readTypedObject(SemSatelliteServiceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSemSatelliteServiceStateChanged(semSatelliteServiceState);
                    return true;
                case 49:
                    SemSatelliteSignalStrength semSatelliteSignalStrength = (SemSatelliteSignalStrength) parcel.readTypedObject(SemSatelliteSignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSemSatelliteSignalStrengthChanged(semSatelliteSignalStrength);
                    return true;
                case 50:
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCpaiModelUpdateNotified(i30, i31);
                    return true;
                case 51:
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCpaiFeatureInfoNotified(i32, i33);
                    return true;
                case 52:
                    int i34 = parcel.readInt();
                    int i35 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onCpaiDataGatheringNotified(i34, i35, bArrCreateByteArray2);
                    return true;
                case 53:
                    int i36 = parcel.readInt();
                    int i37 = parcel.readInt();
                    int i38 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onCpaiDevAppMessageNotified(i36, i37, i38, bArrCreateByteArray3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPhoneStateListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onServiceStateChanged(ServiceState serviceState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(serviceState, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onSignalStrengthChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onMessageWaitingIndicatorChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCallForwardingIndicatorChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCellLocationChanged(CellIdentity cellIdentity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cellIdentity, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onLegacyCallStateChanged(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCallStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onDataConnectionStateChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onDataActivity(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onSignalStrengthsChanged(SignalStrength signalStrength) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(signalStrength, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCellInfoChanged(List<CellInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onPreciseCallStateChanged(PreciseCallState preciseCallState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(preciseCallState, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState preciseDataConnectionState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(preciseDataConnectionState, 0);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dataConnectionRealTimeInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(dataConnectionRealTimeInfo, 0);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onSrvccStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onVoiceActivationStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onDataActivationStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onOemHookRawEvent(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCarrierNetworkChange(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onUserMobileDataStateChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(telephonyDisplayInfo, 0);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onPhoneCapabilityChanged(PhoneCapability phoneCapability) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneCapability, 0);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onActiveDataSubIdChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onRadioPowerStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCallStatesChanged(List<CallState> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onEmergencyNumberListChanged(Map map) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeMap(map);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onOutgoingEmergencyCall(EmergencyNumber emergencyNumber, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(emergencyNumber, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onOutgoingEmergencySms(EmergencyNumber emergencyNumber, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(emergencyNumber, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCallDisconnectCauseChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(29, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onImsCallDisconnectCauseChanged(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(30, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onRegistrationFailed(CellIdentity cellIdentity, String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cellIdentity, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(31, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onBarringInfoChanged(BarringInfo barringInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(barringInfo, 0);
                    this.mRemote.transact(32, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onPhysicalChannelConfigChanged(List<PhysicalChannelConfig> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(33, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onDataEnabledChanged(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onAllowedNetworkTypesChanged(int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(35, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onLinkCapacityEstimateChanged(List<LinkCapacityEstimate> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(36, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onMediaQualityStatusChanged(MediaQualityStatus mediaQualityStatus) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(mediaQualityStatus, 0);
                    this.mRemote.transact(37, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCallbackModeStarted(int i, long j, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(38, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCallbackModeRestarted(int i, long j, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(39, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCallbackModeStopped(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(40, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onSimultaneousCallingStateChanged(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(41, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCarrierRoamingNtnModeChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(42, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCarrierRoamingNtnEligibleStateChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCarrierRoamingNtnAvailableServicesChanged(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(44, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCarrierRoamingNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(ntnSignalStrength, 0);
                    this.mRemote.transact(45, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onSecurityAlgorithmsChanged(SecurityAlgorithmUpdate securityAlgorithmUpdate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(securityAlgorithmUpdate, 0);
                    this.mRemote.transact(46, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCellularIdentifierDisclosedChanged(CellularIdentifierDisclosure cellularIdentifierDisclosure) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cellularIdentifierDisclosure, 0);
                    this.mRemote.transact(47, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onSemSatelliteServiceStateChanged(SemSatelliteServiceState semSatelliteServiceState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(semSatelliteServiceState, 0);
                    this.mRemote.transact(48, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onSemSatelliteSignalStrengthChanged(SemSatelliteSignalStrength semSatelliteSignalStrength) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(semSatelliteSignalStrength, 0);
                    this.mRemote.transact(49, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCpaiModelUpdateNotified(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(50, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCpaiFeatureInfoNotified(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(51, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCpaiDataGatheringNotified(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(52, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCpaiDevAppMessageNotified(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(53, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
