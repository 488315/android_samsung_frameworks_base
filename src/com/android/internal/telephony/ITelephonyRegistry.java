package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.BarringInfo;
import android.telephony.CallQuality;
import android.telephony.CellIdentity;
import android.telephony.CellInfo;
import android.telephony.CellularIdentifierDisclosure;
import android.telephony.LinkCapacityEstimate;
import android.telephony.PhoneCapability;
import android.telephony.PhysicalChannelConfig;
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
import com.android.internal.telephony.ICarrierConfigChangeListener;
import com.android.internal.telephony.ICarrierPrivilegesCallback;
import com.android.internal.telephony.IOnSubscriptionsChangedListener;
import com.android.internal.telephony.IPhoneStateListener;
import com.android.internal.telephony.ISatelliteStateChangeListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ITelephonyRegistry extends IInterface {

    public static class Default implements ITelephonyRegistry {
        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void addCarrierConfigChangeListener(ICarrierConfigChangeListener iCarrierConfigChangeListener, String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void addCarrierPrivilegesCallback(int i, ICarrierPrivilegesCallback iCarrierPrivilegesCallback, String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void addOnOpportunisticSubscriptionsChangedListener(String str, String str2, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void addOnSubscriptionsChangedListener(String str, String str2, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void addSatelliteStateChangeListener(ISatelliteStateChangeListener iSatelliteStateChangeListener, String str, String str2) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void clearPreciseDataConnectionStates(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void listenWithEventList(boolean z, boolean z2, int i, String str, String str2, IPhoneStateListener iPhoneStateListener, int[] iArr, boolean z3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyActiveDataSubIdChanged(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyAllowedNetworkTypesChanged(int i, int i2, int i3, long j) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyBarringInfoChanged(int i, int i2, BarringInfo barringInfo) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallForwardingChanged(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallForwardingChangedForSubscriber(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallQualityChanged(CallQuality callQuality, int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallState(int i, int i2, int i3, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallStateForAllSubs(int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallbackModeRestarted(int i, int i2, int i3, long j) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallbackModeStarted(int i, int i2, int i3, long j) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallbackModeStopped(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCarrierConfigChanged(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCarrierNetworkChange(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCarrierNetworkChangeWithSubId(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCarrierPrivilegesChanged(int i, List<String> list, int[] iArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCarrierRoamingNtnAvailableServicesChanged(int i, int[] iArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCarrierRoamingNtnEligibleStateChanged(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCarrierRoamingNtnModeChanged(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCarrierRoamingNtnSignalStrengthChanged(int i, NtnSignalStrength ntnSignalStrength) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCarrierServiceChanged(int i, String str, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCellInfo(List<CellInfo> list) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCellInfoForSubscriber(int i, List<CellInfo> list) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCellLocationForSubscriber(int i, CellIdentity cellIdentity) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCellularIdentifierDisclosedChanged(int i, int i2, CellularIdentifierDisclosure cellularIdentifierDisclosure) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCpaiDataGathering(int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCpaiDevAppMessage(int i, int i2, int i3, byte[] bArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCpaiFeatureInfo(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCpaiModelUpdate(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyDataActivityForSubscriber(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyDataActivityForSubscriberWithSlot(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyDataConnectionForSubscriber(int i, int i2, PreciseDataConnectionState preciseDataConnectionState) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyDataEnabled(int i, int i2, boolean z, int i3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyDisconnectCause(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyDisplayInfoChanged(int i, int i2, TelephonyDisplayInfo telephonyDisplayInfo) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyEmergencyNumberList(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyImsDisconnectCause(int i, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyLinkCapacityEstimateChanged(int i, int i2, List<LinkCapacityEstimate> list) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyMediaQualityStatusChanged(int i, int i2, MediaQualityStatus mediaQualityStatus) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyMessageWaitingChangedForPhoneId(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyOemHookRawEventForSubscriber(int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyOpportunisticSubscriptionInfoChanged() throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyOutgoingEmergencyCall(int i, int i2, EmergencyNumber emergencyNumber) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyOutgoingEmergencySms(int i, int i2, EmergencyNumber emergencyNumber) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyPhoneCapabilityChanged(PhoneCapability phoneCapability) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyPhysicalChannelConfigForSubscriber(int i, int i2, List<PhysicalChannelConfig> list) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyPreciseCallState(int i, int i2, int[] iArr, String[] strArr, int[] iArr2, int[] iArr3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyRadioPowerStateChanged(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyRegistrationFailed(int i, int i2, CellIdentity cellIdentity, String str, int i3, int i4, int i5) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifySatelliteStateChanged(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifySecurityAlgorithmsChanged(int i, int i2, SecurityAlgorithmUpdate securityAlgorithmUpdate) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifySemSatelliteServiceStateChanged(int i, int i2, SemSatelliteServiceState semSatelliteServiceState) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifySemSatelliteSignalStrengthChanged(int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyServiceStateForPhoneId(int i, int i2, ServiceState serviceState) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifySignalStrengthForPhoneId(int i, int i2, SignalStrength signalStrength) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifySimActivationStateChangedForPhoneId(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifySimultaneousCellularCallingSubscriptionsChanged(int[] iArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifySrvccStateChanged(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifySubscriptionInfoChanged() throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyUserMobileDataStateChangedForPhoneId(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void removeCarrierConfigChangeListener(ICarrierConfigChangeListener iCarrierConfigChangeListener, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void removeCarrierPrivilegesCallback(ICarrierPrivilegesCallback iCarrierPrivilegesCallback, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void removeOnSubscriptionsChangedListener(String str, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void removeSatelliteStateChangeListener(ISatelliteStateChangeListener iSatelliteStateChangeListener, String str) throws RemoteException {
        }
    }

    void addCarrierConfigChangeListener(ICarrierConfigChangeListener iCarrierConfigChangeListener, String str, String str2) throws RemoteException;

    void addCarrierPrivilegesCallback(int i, ICarrierPrivilegesCallback iCarrierPrivilegesCallback, String str, String str2) throws RemoteException;

    void addOnOpportunisticSubscriptionsChangedListener(String str, String str2, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException;

    void addOnSubscriptionsChangedListener(String str, String str2, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException;

    void addSatelliteStateChangeListener(ISatelliteStateChangeListener iSatelliteStateChangeListener, String str, String str2) throws RemoteException;

    void clearPreciseDataConnectionStates(int i) throws RemoteException;

    void listenWithEventList(boolean z, boolean z2, int i, String str, String str2, IPhoneStateListener iPhoneStateListener, int[] iArr, boolean z3) throws RemoteException;

    void notifyActiveDataSubIdChanged(int i) throws RemoteException;

    void notifyAllowedNetworkTypesChanged(int i, int i2, int i3, long j) throws RemoteException;

    void notifyBarringInfoChanged(int i, int i2, BarringInfo barringInfo) throws RemoteException;

    void notifyCallForwardingChanged(boolean z) throws RemoteException;

    void notifyCallForwardingChangedForSubscriber(int i, boolean z) throws RemoteException;

    void notifyCallQualityChanged(CallQuality callQuality, int i, int i2, int i3) throws RemoteException;

    void notifyCallState(int i, int i2, int i3, String str) throws RemoteException;

    void notifyCallStateForAllSubs(int i, String str) throws RemoteException;

    void notifyCallbackModeRestarted(int i, int i2, int i3, long j) throws RemoteException;

    void notifyCallbackModeStarted(int i, int i2, int i3, long j) throws RemoteException;

    void notifyCallbackModeStopped(int i, int i2, int i3, int i4) throws RemoteException;

    void notifyCarrierConfigChanged(int i, int i2, int i3, int i4) throws RemoteException;

    void notifyCarrierNetworkChange(boolean z) throws RemoteException;

    void notifyCarrierNetworkChangeWithSubId(int i, boolean z) throws RemoteException;

    void notifyCarrierPrivilegesChanged(int i, List<String> list, int[] iArr) throws RemoteException;

    void notifyCarrierRoamingNtnAvailableServicesChanged(int i, int[] iArr) throws RemoteException;

    void notifyCarrierRoamingNtnEligibleStateChanged(int i, boolean z) throws RemoteException;

    void notifyCarrierRoamingNtnModeChanged(int i, boolean z) throws RemoteException;

    void notifyCarrierRoamingNtnSignalStrengthChanged(int i, NtnSignalStrength ntnSignalStrength) throws RemoteException;

    void notifyCarrierServiceChanged(int i, String str, int i2) throws RemoteException;

    void notifyCellInfo(List<CellInfo> list) throws RemoteException;

    void notifyCellInfoForSubscriber(int i, List<CellInfo> list) throws RemoteException;

    void notifyCellLocationForSubscriber(int i, CellIdentity cellIdentity) throws RemoteException;

    void notifyCellularIdentifierDisclosedChanged(int i, int i2, CellularIdentifierDisclosure cellularIdentifierDisclosure) throws RemoteException;

    void notifyCpaiDataGathering(int i, int i2, byte[] bArr) throws RemoteException;

    void notifyCpaiDevAppMessage(int i, int i2, int i3, byte[] bArr) throws RemoteException;

    void notifyCpaiFeatureInfo(int i, int i2) throws RemoteException;

    void notifyCpaiModelUpdate(int i, int i2) throws RemoteException;

    void notifyDataActivityForSubscriber(int i, int i2) throws RemoteException;

    void notifyDataActivityForSubscriberWithSlot(int i, int i2, int i3) throws RemoteException;

    void notifyDataConnectionForSubscriber(int i, int i2, PreciseDataConnectionState preciseDataConnectionState) throws RemoteException;

    void notifyDataEnabled(int i, int i2, boolean z, int i3) throws RemoteException;

    void notifyDisconnectCause(int i, int i2, int i3, int i4) throws RemoteException;

    void notifyDisplayInfoChanged(int i, int i2, TelephonyDisplayInfo telephonyDisplayInfo) throws RemoteException;

    void notifyEmergencyNumberList(int i, int i2) throws RemoteException;

    void notifyImsDisconnectCause(int i, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void notifyLinkCapacityEstimateChanged(int i, int i2, List<LinkCapacityEstimate> list) throws RemoteException;

    void notifyMediaQualityStatusChanged(int i, int i2, MediaQualityStatus mediaQualityStatus) throws RemoteException;

    void notifyMessageWaitingChangedForPhoneId(int i, int i2, boolean z) throws RemoteException;

    void notifyOemHookRawEventForSubscriber(int i, int i2, byte[] bArr) throws RemoteException;

    void notifyOpportunisticSubscriptionInfoChanged() throws RemoteException;

    void notifyOutgoingEmergencyCall(int i, int i2, EmergencyNumber emergencyNumber) throws RemoteException;

    void notifyOutgoingEmergencySms(int i, int i2, EmergencyNumber emergencyNumber) throws RemoteException;

    void notifyPhoneCapabilityChanged(PhoneCapability phoneCapability) throws RemoteException;

    void notifyPhysicalChannelConfigForSubscriber(int i, int i2, List<PhysicalChannelConfig> list) throws RemoteException;

    void notifyPreciseCallState(int i, int i2, int[] iArr, String[] strArr, int[] iArr2, int[] iArr3) throws RemoteException;

    void notifyRadioPowerStateChanged(int i, int i2, int i3) throws RemoteException;

    void notifyRegistrationFailed(int i, int i2, CellIdentity cellIdentity, String str, int i3, int i4, int i5) throws RemoteException;

    void notifySatelliteStateChanged(boolean z) throws RemoteException;

    void notifySecurityAlgorithmsChanged(int i, int i2, SecurityAlgorithmUpdate securityAlgorithmUpdate) throws RemoteException;

    void notifySemSatelliteServiceStateChanged(int i, int i2, SemSatelliteServiceState semSatelliteServiceState) throws RemoteException;

    void notifySemSatelliteSignalStrengthChanged(int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) throws RemoteException;

    void notifyServiceStateForPhoneId(int i, int i2, ServiceState serviceState) throws RemoteException;

    void notifySignalStrengthForPhoneId(int i, int i2, SignalStrength signalStrength) throws RemoteException;

    void notifySimActivationStateChangedForPhoneId(int i, int i2, int i3, int i4) throws RemoteException;

    void notifySimultaneousCellularCallingSubscriptionsChanged(int[] iArr) throws RemoteException;

    void notifySrvccStateChanged(int i, int i2) throws RemoteException;

    void notifySubscriptionInfoChanged() throws RemoteException;

    void notifyUserMobileDataStateChangedForPhoneId(int i, int i2, boolean z) throws RemoteException;

    void removeCarrierConfigChangeListener(ICarrierConfigChangeListener iCarrierConfigChangeListener, String str) throws RemoteException;

    void removeCarrierPrivilegesCallback(ICarrierPrivilegesCallback iCarrierPrivilegesCallback, String str) throws RemoteException;

    void removeOnSubscriptionsChangedListener(String str, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException;

    void removeSatelliteStateChangeListener(ISatelliteStateChangeListener iSatelliteStateChangeListener, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ITelephonyRegistry {
        public static final String DESCRIPTOR = "com.android.internal.telephony.ITelephonyRegistry";
        static final int TRANSACTION_addCarrierConfigChangeListener = 49;
        static final int TRANSACTION_addCarrierPrivilegesCallback = 45;
        static final int TRANSACTION_addOnOpportunisticSubscriptionsChangedListener = 2;
        static final int TRANSACTION_addOnSubscriptionsChangedListener = 1;
        static final int TRANSACTION_addSatelliteStateChangeListener = 59;
        static final int TRANSACTION_clearPreciseDataConnectionStates = 64;
        static final int TRANSACTION_listenWithEventList = 4;
        static final int TRANSACTION_notifyActiveDataSubIdChanged = 30;
        static final int TRANSACTION_notifyAllowedNetworkTypesChanged = 42;
        static final int TRANSACTION_notifyBarringInfoChanged = 39;
        static final int TRANSACTION_notifyCallForwardingChanged = 10;
        static final int TRANSACTION_notifyCallForwardingChangedForSubscriber = 11;
        static final int TRANSACTION_notifyCallQualityChanged = 35;
        static final int TRANSACTION_notifyCallState = 6;
        static final int TRANSACTION_notifyCallStateForAllSubs = 5;
        static final int TRANSACTION_notifyCallbackModeRestarted = 53;
        static final int TRANSACTION_notifyCallbackModeStarted = 52;
        static final int TRANSACTION_notifyCallbackModeStopped = 54;
        static final int TRANSACTION_notifyCarrierConfigChanged = 51;
        static final int TRANSACTION_notifyCarrierNetworkChange = 25;
        static final int TRANSACTION_notifyCarrierNetworkChangeWithSubId = 26;
        static final int TRANSACTION_notifyCarrierPrivilegesChanged = 47;
        static final int TRANSACTION_notifyCarrierRoamingNtnAvailableServicesChanged = 57;
        static final int TRANSACTION_notifyCarrierRoamingNtnEligibleStateChanged = 56;
        static final int TRANSACTION_notifyCarrierRoamingNtnModeChanged = 55;
        static final int TRANSACTION_notifyCarrierRoamingNtnSignalStrengthChanged = 58;
        static final int TRANSACTION_notifyCarrierServiceChanged = 48;
        static final int TRANSACTION_notifyCellInfo = 16;
        static final int TRANSACTION_notifyCellInfoForSubscriber = 19;
        static final int TRANSACTION_notifyCellLocationForSubscriber = 15;
        static final int TRANSACTION_notifyCellularIdentifierDisclosedChanged = 63;
        static final int TRANSACTION_notifyCpaiDataGathering = 69;
        static final int TRANSACTION_notifyCpaiDevAppMessage = 70;
        static final int TRANSACTION_notifyCpaiFeatureInfo = 68;
        static final int TRANSACTION_notifyCpaiModelUpdate = 67;
        static final int TRANSACTION_notifyDataActivityForSubscriber = 12;
        static final int TRANSACTION_notifyDataActivityForSubscriberWithSlot = 13;
        static final int TRANSACTION_notifyDataConnectionForSubscriber = 14;
        static final int TRANSACTION_notifyDataEnabled = 41;
        static final int TRANSACTION_notifyDisconnectCause = 18;
        static final int TRANSACTION_notifyDisplayInfoChanged = 28;
        static final int TRANSACTION_notifyEmergencyNumberList = 32;
        static final int TRANSACTION_notifyImsDisconnectCause = 37;
        static final int TRANSACTION_notifyLinkCapacityEstimateChanged = 43;
        static final int TRANSACTION_notifyMediaQualityStatusChanged = 36;
        static final int TRANSACTION_notifyMessageWaitingChangedForPhoneId = 9;
        static final int TRANSACTION_notifyOemHookRawEventForSubscriber = 22;
        static final int TRANSACTION_notifyOpportunisticSubscriptionInfoChanged = 24;
        static final int TRANSACTION_notifyOutgoingEmergencyCall = 33;
        static final int TRANSACTION_notifyOutgoingEmergencySms = 34;
        static final int TRANSACTION_notifyPhoneCapabilityChanged = 29;
        static final int TRANSACTION_notifyPhysicalChannelConfigForSubscriber = 40;
        static final int TRANSACTION_notifyPreciseCallState = 17;
        static final int TRANSACTION_notifyRadioPowerStateChanged = 31;
        static final int TRANSACTION_notifyRegistrationFailed = 38;
        static final int TRANSACTION_notifySatelliteStateChanged = 61;
        static final int TRANSACTION_notifySecurityAlgorithmsChanged = 62;
        static final int TRANSACTION_notifySemSatelliteServiceStateChanged = 65;
        static final int TRANSACTION_notifySemSatelliteSignalStrengthChanged = 66;
        static final int TRANSACTION_notifyServiceStateForPhoneId = 7;
        static final int TRANSACTION_notifySignalStrengthForPhoneId = 8;
        static final int TRANSACTION_notifySimActivationStateChangedForPhoneId = 21;
        static final int TRANSACTION_notifySimultaneousCellularCallingSubscriptionsChanged = 44;
        static final int TRANSACTION_notifySrvccStateChanged = 20;
        static final int TRANSACTION_notifySubscriptionInfoChanged = 23;
        static final int TRANSACTION_notifyUserMobileDataStateChangedForPhoneId = 27;
        static final int TRANSACTION_removeCarrierConfigChangeListener = 50;
        static final int TRANSACTION_removeCarrierPrivilegesCallback = 46;
        static final int TRANSACTION_removeOnSubscriptionsChangedListener = 3;
        static final int TRANSACTION_removeSatelliteStateChangeListener = 60;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 69;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITelephonyRegistry asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITelephonyRegistry)) {
                return (ITelephonyRegistry) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addOnSubscriptionsChangedListener";
                case 2:
                    return "addOnOpportunisticSubscriptionsChangedListener";
                case 3:
                    return "removeOnSubscriptionsChangedListener";
                case 4:
                    return "listenWithEventList";
                case 5:
                    return "notifyCallStateForAllSubs";
                case 6:
                    return "notifyCallState";
                case 7:
                    return "notifyServiceStateForPhoneId";
                case 8:
                    return "notifySignalStrengthForPhoneId";
                case 9:
                    return "notifyMessageWaitingChangedForPhoneId";
                case 10:
                    return "notifyCallForwardingChanged";
                case 11:
                    return "notifyCallForwardingChangedForSubscriber";
                case 12:
                    return "notifyDataActivityForSubscriber";
                case 13:
                    return "notifyDataActivityForSubscriberWithSlot";
                case 14:
                    return "notifyDataConnectionForSubscriber";
                case 15:
                    return "notifyCellLocationForSubscriber";
                case 16:
                    return "notifyCellInfo";
                case 17:
                    return "notifyPreciseCallState";
                case 18:
                    return "notifyDisconnectCause";
                case 19:
                    return "notifyCellInfoForSubscriber";
                case 20:
                    return "notifySrvccStateChanged";
                case 21:
                    return "notifySimActivationStateChangedForPhoneId";
                case 22:
                    return "notifyOemHookRawEventForSubscriber";
                case 23:
                    return "notifySubscriptionInfoChanged";
                case 24:
                    return "notifyOpportunisticSubscriptionInfoChanged";
                case 25:
                    return "notifyCarrierNetworkChange";
                case 26:
                    return "notifyCarrierNetworkChangeWithSubId";
                case 27:
                    return "notifyUserMobileDataStateChangedForPhoneId";
                case 28:
                    return "notifyDisplayInfoChanged";
                case 29:
                    return "notifyPhoneCapabilityChanged";
                case 30:
                    return "notifyActiveDataSubIdChanged";
                case 31:
                    return "notifyRadioPowerStateChanged";
                case 32:
                    return "notifyEmergencyNumberList";
                case 33:
                    return "notifyOutgoingEmergencyCall";
                case 34:
                    return "notifyOutgoingEmergencySms";
                case 35:
                    return "notifyCallQualityChanged";
                case 36:
                    return "notifyMediaQualityStatusChanged";
                case 37:
                    return "notifyImsDisconnectCause";
                case 38:
                    return "notifyRegistrationFailed";
                case 39:
                    return "notifyBarringInfoChanged";
                case 40:
                    return "notifyPhysicalChannelConfigForSubscriber";
                case 41:
                    return "notifyDataEnabled";
                case 42:
                    return "notifyAllowedNetworkTypesChanged";
                case 43:
                    return "notifyLinkCapacityEstimateChanged";
                case 44:
                    return "notifySimultaneousCellularCallingSubscriptionsChanged";
                case 45:
                    return "addCarrierPrivilegesCallback";
                case 46:
                    return "removeCarrierPrivilegesCallback";
                case 47:
                    return "notifyCarrierPrivilegesChanged";
                case 48:
                    return "notifyCarrierServiceChanged";
                case 49:
                    return "addCarrierConfigChangeListener";
                case 50:
                    return "removeCarrierConfigChangeListener";
                case 51:
                    return "notifyCarrierConfigChanged";
                case 52:
                    return "notifyCallbackModeStarted";
                case 53:
                    return "notifyCallbackModeRestarted";
                case 54:
                    return "notifyCallbackModeStopped";
                case 55:
                    return "notifyCarrierRoamingNtnModeChanged";
                case 56:
                    return "notifyCarrierRoamingNtnEligibleStateChanged";
                case 57:
                    return "notifyCarrierRoamingNtnAvailableServicesChanged";
                case 58:
                    return "notifyCarrierRoamingNtnSignalStrengthChanged";
                case 59:
                    return "addSatelliteStateChangeListener";
                case 60:
                    return "removeSatelliteStateChangeListener";
                case 61:
                    return "notifySatelliteStateChanged";
                case 62:
                    return "notifySecurityAlgorithmsChanged";
                case 63:
                    return "notifyCellularIdentifierDisclosedChanged";
                case 64:
                    return "clearPreciseDataConnectionStates";
                case 65:
                    return "notifySemSatelliteServiceStateChanged";
                case 66:
                    return "notifySemSatelliteSignalStrengthChanged";
                case 67:
                    return "notifyCpaiModelUpdate";
                case 68:
                    return "notifyCpaiFeatureInfo";
                case 69:
                    return "notifyCpaiDataGathering";
                case 70:
                    return "notifyCpaiDevAppMessage";
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
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    IOnSubscriptionsChangedListener asInterface = IOnSubscriptionsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnSubscriptionsChangedListener(readString, readString2, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    IOnSubscriptionsChangedListener asInterface2 = IOnSubscriptionsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnOpportunisticSubscriptionsChangedListener(readString3, readString4, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString5 = parcel.readString();
                    IOnSubscriptionsChangedListener asInterface3 = IOnSubscriptionsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnSubscriptionsChangedListener(readString5, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    IPhoneStateListener asInterface4 = IPhoneStateListener.Stub.asInterface(parcel.readStrongBinder());
                    int[] createIntArray = parcel.createIntArray();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    listenWithEventList(readBoolean, readBoolean2, readInt, readString6, readString7, asInterface4, createIntArray, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyCallStateForAllSubs(readInt2, readString8);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyCallState(readInt3, readInt4, readInt5, readString9);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    ServiceState serviceState = (ServiceState) parcel.readTypedObject(ServiceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyServiceStateForPhoneId(readInt6, readInt7, serviceState);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    SignalStrength signalStrength = (SignalStrength) parcel.readTypedObject(SignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySignalStrengthForPhoneId(readInt8, readInt9, signalStrength);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyMessageWaitingChangedForPhoneId(readInt10, readInt11, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCallForwardingChanged(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCallForwardingChangedForSubscriber(readInt12, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyDataActivityForSubscriber(readInt13, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyDataActivityForSubscriberWithSlot(readInt15, readInt16, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    PreciseDataConnectionState preciseDataConnectionState = (PreciseDataConnectionState) parcel.readTypedObject(PreciseDataConnectionState.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyDataConnectionForSubscriber(readInt18, readInt19, preciseDataConnectionState);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt20 = parcel.readInt();
                    CellIdentity cellIdentity = (CellIdentity) parcel.readTypedObject(CellIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCellLocationForSubscriber(readInt20, cellIdentity);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(CellInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCellInfo(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    String[] createStringArray = parcel.createStringArray();
                    int[] createIntArray3 = parcel.createIntArray();
                    int[] createIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifyPreciseCallState(readInt21, readInt22, createIntArray2, createStringArray, createIntArray3, createIntArray4);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyDisconnectCause(readInt23, readInt24, readInt25, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt27 = parcel.readInt();
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(CellInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCellInfoForSubscriber(readInt27, createTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifySrvccStateChanged(readInt28, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifySimActivationStateChangedForPhoneId(readInt30, readInt31, readInt32, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    notifyOemHookRawEventForSubscriber(readInt34, readInt35, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    notifySubscriptionInfoChanged();
                    parcel2.writeNoException();
                    return true;
                case 24:
                    notifyOpportunisticSubscriptionInfoChanged();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCarrierNetworkChange(readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt36 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCarrierNetworkChangeWithSubId(readInt36, readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyUserMobileDataStateChangedForPhoneId(readInt37, readInt38, readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    TelephonyDisplayInfo telephonyDisplayInfo = (TelephonyDisplayInfo) parcel.readTypedObject(TelephonyDisplayInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyDisplayInfoChanged(readInt39, readInt40, telephonyDisplayInfo);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    PhoneCapability phoneCapability = (PhoneCapability) parcel.readTypedObject(PhoneCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyPhoneCapabilityChanged(phoneCapability);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyActiveDataSubIdChanged(readInt41);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRadioPowerStateChanged(readInt42, readInt43, readInt44);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyEmergencyNumberList(readInt45, readInt46);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    EmergencyNumber emergencyNumber = (EmergencyNumber) parcel.readTypedObject(EmergencyNumber.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyOutgoingEmergencyCall(readInt47, readInt48, emergencyNumber);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int readInt49 = parcel.readInt();
                    int readInt50 = parcel.readInt();
                    EmergencyNumber emergencyNumber2 = (EmergencyNumber) parcel.readTypedObject(EmergencyNumber.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyOutgoingEmergencySms(readInt49, readInt50, emergencyNumber2);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    CallQuality callQuality = (CallQuality) parcel.readTypedObject(CallQuality.CREATOR);
                    int readInt51 = parcel.readInt();
                    int readInt52 = parcel.readInt();
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCallQualityChanged(callQuality, readInt51, readInt52, readInt53);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt54 = parcel.readInt();
                    int readInt55 = parcel.readInt();
                    MediaQualityStatus mediaQualityStatus = (MediaQualityStatus) parcel.readTypedObject(MediaQualityStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyMediaQualityStatusChanged(readInt54, readInt55, mediaQualityStatus);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt56 = parcel.readInt();
                    ImsReasonInfo imsReasonInfo = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyImsDisconnectCause(readInt56, imsReasonInfo);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt57 = parcel.readInt();
                    int readInt58 = parcel.readInt();
                    CellIdentity cellIdentity2 = (CellIdentity) parcel.readTypedObject(CellIdentity.CREATOR);
                    String readString10 = parcel.readString();
                    int readInt59 = parcel.readInt();
                    int readInt60 = parcel.readInt();
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRegistrationFailed(readInt57, readInt58, cellIdentity2, readString10, readInt59, readInt60, readInt61);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt62 = parcel.readInt();
                    int readInt63 = parcel.readInt();
                    BarringInfo barringInfo = (BarringInfo) parcel.readTypedObject(BarringInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyBarringInfoChanged(readInt62, readInt63, barringInfo);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt64 = parcel.readInt();
                    int readInt65 = parcel.readInt();
                    ArrayList createTypedArrayList3 = parcel.createTypedArrayList(PhysicalChannelConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyPhysicalChannelConfigForSubscriber(readInt64, readInt65, createTypedArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt66 = parcel.readInt();
                    int readInt67 = parcel.readInt();
                    boolean readBoolean10 = parcel.readBoolean();
                    int readInt68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyDataEnabled(readInt66, readInt67, readBoolean10, readInt68);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int readInt69 = parcel.readInt();
                    int readInt70 = parcel.readInt();
                    int readInt71 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyAllowedNetworkTypesChanged(readInt69, readInt70, readInt71, readLong);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int readInt72 = parcel.readInt();
                    int readInt73 = parcel.readInt();
                    ArrayList createTypedArrayList4 = parcel.createTypedArrayList(LinkCapacityEstimate.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyLinkCapacityEstimateChanged(readInt72, readInt73, createTypedArrayList4);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int[] createIntArray5 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifySimultaneousCellularCallingSubscriptionsChanged(createIntArray5);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int readInt74 = parcel.readInt();
                    ICarrierPrivilegesCallback asInterface5 = ICarrierPrivilegesCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addCarrierPrivilegesCallback(readInt74, asInterface5, readString11, readString12);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    ICarrierPrivilegesCallback asInterface6 = ICarrierPrivilegesCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeCarrierPrivilegesCallback(asInterface6, readString13);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt75 = parcel.readInt();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    int[] createIntArray6 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifyCarrierPrivilegesChanged(readInt75, createStringArrayList, createIntArray6);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int readInt76 = parcel.readInt();
                    String readString14 = parcel.readString();
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCarrierServiceChanged(readInt76, readString14, readInt77);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    ICarrierConfigChangeListener asInterface7 = ICarrierConfigChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addCarrierConfigChangeListener(asInterface7, readString15, readString16);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    ICarrierConfigChangeListener asInterface8 = ICarrierConfigChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeCarrierConfigChangeListener(asInterface8, readString17);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int readInt78 = parcel.readInt();
                    int readInt79 = parcel.readInt();
                    int readInt80 = parcel.readInt();
                    int readInt81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCarrierConfigChanged(readInt78, readInt79, readInt80, readInt81);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int readInt82 = parcel.readInt();
                    int readInt83 = parcel.readInt();
                    int readInt84 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyCallbackModeStarted(readInt82, readInt83, readInt84, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    int readInt85 = parcel.readInt();
                    int readInt86 = parcel.readInt();
                    int readInt87 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyCallbackModeRestarted(readInt85, readInt86, readInt87, readLong3);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    int readInt88 = parcel.readInt();
                    int readInt89 = parcel.readInt();
                    int readInt90 = parcel.readInt();
                    int readInt91 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCallbackModeStopped(readInt88, readInt89, readInt90, readInt91);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    int readInt92 = parcel.readInt();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCarrierRoamingNtnModeChanged(readInt92, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    int readInt93 = parcel.readInt();
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCarrierRoamingNtnEligibleStateChanged(readInt93, readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    int readInt94 = parcel.readInt();
                    int[] createIntArray7 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifyCarrierRoamingNtnAvailableServicesChanged(readInt94, createIntArray7);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int readInt95 = parcel.readInt();
                    NtnSignalStrength ntnSignalStrength = (NtnSignalStrength) parcel.readTypedObject(NtnSignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCarrierRoamingNtnSignalStrengthChanged(readInt95, ntnSignalStrength);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    ISatelliteStateChangeListener asInterface9 = ISatelliteStateChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSatelliteStateChangeListener(asInterface9, readString18, readString19);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    ISatelliteStateChangeListener asInterface10 = ISatelliteStateChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSatelliteStateChangeListener(asInterface10, readString20);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifySatelliteStateChanged(readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int readInt96 = parcel.readInt();
                    int readInt97 = parcel.readInt();
                    SecurityAlgorithmUpdate securityAlgorithmUpdate = (SecurityAlgorithmUpdate) parcel.readTypedObject(SecurityAlgorithmUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySecurityAlgorithmsChanged(readInt96, readInt97, securityAlgorithmUpdate);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    int readInt98 = parcel.readInt();
                    int readInt99 = parcel.readInt();
                    CellularIdentifierDisclosure cellularIdentifierDisclosure = (CellularIdentifierDisclosure) parcel.readTypedObject(CellularIdentifierDisclosure.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCellularIdentifierDisclosedChanged(readInt98, readInt99, cellularIdentifierDisclosure);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    int readInt100 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPreciseDataConnectionStates(readInt100);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int readInt101 = parcel.readInt();
                    int readInt102 = parcel.readInt();
                    SemSatelliteServiceState semSatelliteServiceState = (SemSatelliteServiceState) parcel.readTypedObject(SemSatelliteServiceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySemSatelliteServiceStateChanged(readInt101, readInt102, semSatelliteServiceState);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int readInt103 = parcel.readInt();
                    int readInt104 = parcel.readInt();
                    SemSatelliteSignalStrength semSatelliteSignalStrength = (SemSatelliteSignalStrength) parcel.readTypedObject(SemSatelliteSignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySemSatelliteSignalStrengthChanged(readInt103, readInt104, semSatelliteSignalStrength);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    int readInt105 = parcel.readInt();
                    int readInt106 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCpaiModelUpdate(readInt105, readInt106);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    int readInt107 = parcel.readInt();
                    int readInt108 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCpaiFeatureInfo(readInt107, readInt108);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    int readInt109 = parcel.readInt();
                    int readInt110 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    notifyCpaiDataGathering(readInt109, readInt110, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    int readInt111 = parcel.readInt();
                    int readInt112 = parcel.readInt();
                    int readInt113 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    notifyCpaiDevAppMessage(readInt111, readInt112, readInt113, createByteArray3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITelephonyRegistry {
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

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void addOnSubscriptionsChangedListener(String str, String str2, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iOnSubscriptionsChangedListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void addOnOpportunisticSubscriptionsChangedListener(String str, String str2, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iOnSubscriptionsChangedListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void removeOnSubscriptionsChangedListener(String str, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iOnSubscriptionsChangedListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void listenWithEventList(boolean z, boolean z2, int i, String str, String str2, IPhoneStateListener iPhoneStateListener, int[] iArr, boolean z3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iPhoneStateListener);
                    obtain.writeIntArray(iArr);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallStateForAllSubs(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallState(int i, int i2, int i3, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyServiceStateForPhoneId(int i, int i2, ServiceState serviceState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(serviceState, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySignalStrengthForPhoneId(int i, int i2, SignalStrength signalStrength) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(signalStrength, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyMessageWaitingChangedForPhoneId(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallForwardingChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallForwardingChangedForSubscriber(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDataActivityForSubscriber(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDataActivityForSubscriberWithSlot(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDataConnectionForSubscriber(int i, int i2, PreciseDataConnectionState preciseDataConnectionState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(preciseDataConnectionState, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCellLocationForSubscriber(int i, CellIdentity cellIdentity) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cellIdentity, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCellInfo(List<CellInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyPreciseCallState(int i, int i2, int[] iArr, String[] strArr, int[] iArr2, int[] iArr3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    obtain.writeStringArray(strArr);
                    obtain.writeIntArray(iArr2);
                    obtain.writeIntArray(iArr3);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDisconnectCause(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCellInfoForSubscriber(int i, List<CellInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySrvccStateChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySimActivationStateChangedForPhoneId(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyOemHookRawEventForSubscriber(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySubscriptionInfoChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyOpportunisticSubscriptionInfoChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierNetworkChange(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierNetworkChangeWithSubId(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyUserMobileDataStateChangedForPhoneId(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDisplayInfoChanged(int i, int i2, TelephonyDisplayInfo telephonyDisplayInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(telephonyDisplayInfo, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyPhoneCapabilityChanged(PhoneCapability phoneCapability) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneCapability, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyActiveDataSubIdChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyRadioPowerStateChanged(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyEmergencyNumberList(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyOutgoingEmergencyCall(int i, int i2, EmergencyNumber emergencyNumber) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(emergencyNumber, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyOutgoingEmergencySms(int i, int i2, EmergencyNumber emergencyNumber) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(emergencyNumber, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallQualityChanged(CallQuality callQuality, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(callQuality, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyMediaQualityStatusChanged(int i, int i2, MediaQualityStatus mediaQualityStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(mediaQualityStatus, 0);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyImsDisconnectCause(int i, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyRegistrationFailed(int i, int i2, CellIdentity cellIdentity, String str, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(cellIdentity, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyBarringInfoChanged(int i, int i2, BarringInfo barringInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(barringInfo, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyPhysicalChannelConfigForSubscriber(int i, int i2, List<PhysicalChannelConfig> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDataEnabled(int i, int i2, boolean z, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i3);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyAllowedNetworkTypesChanged(int i, int i2, int i3, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyLinkCapacityEstimateChanged(int i, int i2, List<LinkCapacityEstimate> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySimultaneousCellularCallingSubscriptionsChanged(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void addCarrierPrivilegesCallback(int i, ICarrierPrivilegesCallback iCarrierPrivilegesCallback, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCarrierPrivilegesCallback);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void removeCarrierPrivilegesCallback(ICarrierPrivilegesCallback iCarrierPrivilegesCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCarrierPrivilegesCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierPrivilegesChanged(int i, List<String> list, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierServiceChanged(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void addCarrierConfigChangeListener(ICarrierConfigChangeListener iCarrierConfigChangeListener, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCarrierConfigChangeListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void removeCarrierConfigChangeListener(ICarrierConfigChangeListener iCarrierConfigChangeListener, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCarrierConfigChangeListener);
                    obtain.writeString(str);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierConfigChanged(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallbackModeStarted(int i, int i2, int i3, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallbackModeRestarted(int i, int i2, int i3, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallbackModeStopped(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierRoamingNtnModeChanged(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierRoamingNtnEligibleStateChanged(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierRoamingNtnAvailableServicesChanged(int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierRoamingNtnSignalStrengthChanged(int i, NtnSignalStrength ntnSignalStrength) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(ntnSignalStrength, 0);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void addSatelliteStateChangeListener(ISatelliteStateChangeListener iSatelliteStateChangeListener, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSatelliteStateChangeListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void removeSatelliteStateChangeListener(ISatelliteStateChangeListener iSatelliteStateChangeListener, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSatelliteStateChangeListener);
                    obtain.writeString(str);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySatelliteStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySecurityAlgorithmsChanged(int i, int i2, SecurityAlgorithmUpdate securityAlgorithmUpdate) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(securityAlgorithmUpdate, 0);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCellularIdentifierDisclosedChanged(int i, int i2, CellularIdentifierDisclosure cellularIdentifierDisclosure) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(cellularIdentifierDisclosure, 0);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void clearPreciseDataConnectionStates(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySemSatelliteServiceStateChanged(int i, int i2, SemSatelliteServiceState semSatelliteServiceState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(semSatelliteServiceState, 0);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySemSatelliteSignalStrengthChanged(int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(semSatelliteSignalStrength, 0);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCpaiModelUpdate(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCpaiFeatureInfo(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCpaiDataGathering(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCpaiDevAppMessage(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
