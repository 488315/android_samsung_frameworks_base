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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITelephonyRegistry)) {
                return (ITelephonyRegistry) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    IOnSubscriptionsChangedListener iOnSubscriptionsChangedListenerAsInterface = IOnSubscriptionsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnSubscriptionsChangedListener(string, string2, iOnSubscriptionsChangedListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    IOnSubscriptionsChangedListener iOnSubscriptionsChangedListenerAsInterface2 = IOnSubscriptionsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnOpportunisticSubscriptionsChangedListener(string3, string4, iOnSubscriptionsChangedListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string5 = parcel.readString();
                    IOnSubscriptionsChangedListener iOnSubscriptionsChangedListenerAsInterface3 = IOnSubscriptionsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnSubscriptionsChangedListener(string5, iOnSubscriptionsChangedListenerAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    int i3 = parcel.readInt();
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    IPhoneStateListener iPhoneStateListenerAsInterface = IPhoneStateListener.Stub.asInterface(parcel.readStrongBinder());
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    listenWithEventList(z, z2, i3, string6, string7, iPhoneStateListenerAsInterface, iArrCreateIntArray, z3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i4 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyCallStateForAllSubs(i4, string8);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyCallState(i5, i6, i7, string9);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    ServiceState serviceState = (ServiceState) parcel.readTypedObject(ServiceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyServiceStateForPhoneId(i8, i9, serviceState);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    SignalStrength signalStrength = (SignalStrength) parcel.readTypedObject(SignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySignalStrengthForPhoneId(i10, i11, signalStrength);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyMessageWaitingChangedForPhoneId(i12, i13, z4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCallForwardingChanged(z5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i14 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCallForwardingChangedForSubscriber(i14, z6);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyDataActivityForSubscriber(i15, i16);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyDataActivityForSubscriberWithSlot(i17, i18, i19);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    PreciseDataConnectionState preciseDataConnectionState = (PreciseDataConnectionState) parcel.readTypedObject(PreciseDataConnectionState.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyDataConnectionForSubscriber(i20, i21, preciseDataConnectionState);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i22 = parcel.readInt();
                    CellIdentity cellIdentity = (CellIdentity) parcel.readTypedObject(CellIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCellLocationForSubscriber(i22, cellIdentity);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(CellInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCellInfo(arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    int[] iArrCreateIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifyPreciseCallState(i23, i24, iArrCreateIntArray2, strArrCreateStringArray, iArrCreateIntArray3, iArrCreateIntArray4);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyDisconnectCause(i25, i26, i27, i28);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i29 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(CellInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCellInfoForSubscriber(i29, arrayListCreateTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifySrvccStateChanged(i30, i31);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifySimActivationStateChangedForPhoneId(i32, i33, i34, i35);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i36 = parcel.readInt();
                    int i37 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    notifyOemHookRawEventForSubscriber(i36, i37, bArrCreateByteArray);
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
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCarrierNetworkChange(z7);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int i38 = parcel.readInt();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCarrierNetworkChangeWithSubId(i38, z8);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int i39 = parcel.readInt();
                    int i40 = parcel.readInt();
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyUserMobileDataStateChangedForPhoneId(i39, i40, z9);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int i41 = parcel.readInt();
                    int i42 = parcel.readInt();
                    TelephonyDisplayInfo telephonyDisplayInfo = (TelephonyDisplayInfo) parcel.readTypedObject(TelephonyDisplayInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyDisplayInfoChanged(i41, i42, telephonyDisplayInfo);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    PhoneCapability phoneCapability = (PhoneCapability) parcel.readTypedObject(PhoneCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyPhoneCapabilityChanged(phoneCapability);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyActiveDataSubIdChanged(i43);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    int i46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRadioPowerStateChanged(i44, i45, i46);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int i47 = parcel.readInt();
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyEmergencyNumberList(i47, i48);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int i49 = parcel.readInt();
                    int i50 = parcel.readInt();
                    EmergencyNumber emergencyNumber = (EmergencyNumber) parcel.readTypedObject(EmergencyNumber.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyOutgoingEmergencyCall(i49, i50, emergencyNumber);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int i51 = parcel.readInt();
                    int i52 = parcel.readInt();
                    EmergencyNumber emergencyNumber2 = (EmergencyNumber) parcel.readTypedObject(EmergencyNumber.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyOutgoingEmergencySms(i51, i52, emergencyNumber2);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    CallQuality callQuality = (CallQuality) parcel.readTypedObject(CallQuality.CREATOR);
                    int i53 = parcel.readInt();
                    int i54 = parcel.readInt();
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCallQualityChanged(callQuality, i53, i54, i55);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int i56 = parcel.readInt();
                    int i57 = parcel.readInt();
                    MediaQualityStatus mediaQualityStatus = (MediaQualityStatus) parcel.readTypedObject(MediaQualityStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyMediaQualityStatusChanged(i56, i57, mediaQualityStatus);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int i58 = parcel.readInt();
                    ImsReasonInfo imsReasonInfo = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyImsDisconnectCause(i58, imsReasonInfo);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int i59 = parcel.readInt();
                    int i60 = parcel.readInt();
                    CellIdentity cellIdentity2 = (CellIdentity) parcel.readTypedObject(CellIdentity.CREATOR);
                    String string10 = parcel.readString();
                    int i61 = parcel.readInt();
                    int i62 = parcel.readInt();
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRegistrationFailed(i59, i60, cellIdentity2, string10, i61, i62, i63);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int i64 = parcel.readInt();
                    int i65 = parcel.readInt();
                    BarringInfo barringInfo = (BarringInfo) parcel.readTypedObject(BarringInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyBarringInfoChanged(i64, i65, barringInfo);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int i66 = parcel.readInt();
                    int i67 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList3 = parcel.createTypedArrayList(PhysicalChannelConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyPhysicalChannelConfigForSubscriber(i66, i67, arrayListCreateTypedArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int i68 = parcel.readInt();
                    int i69 = parcel.readInt();
                    boolean z10 = parcel.readBoolean();
                    int i70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyDataEnabled(i68, i69, z10, i70);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int i71 = parcel.readInt();
                    int i72 = parcel.readInt();
                    int i73 = parcel.readInt();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyAllowedNetworkTypesChanged(i71, i72, i73, j);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int i74 = parcel.readInt();
                    int i75 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList4 = parcel.createTypedArrayList(LinkCapacityEstimate.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyLinkCapacityEstimateChanged(i74, i75, arrayListCreateTypedArrayList4);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int[] iArrCreateIntArray5 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifySimultaneousCellularCallingSubscriptionsChanged(iArrCreateIntArray5);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int i76 = parcel.readInt();
                    ICarrierPrivilegesCallback iCarrierPrivilegesCallbackAsInterface = ICarrierPrivilegesCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addCarrierPrivilegesCallback(i76, iCarrierPrivilegesCallbackAsInterface, string11, string12);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    ICarrierPrivilegesCallback iCarrierPrivilegesCallbackAsInterface2 = ICarrierPrivilegesCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeCarrierPrivilegesCallback(iCarrierPrivilegesCallbackAsInterface2, string13);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int i77 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    int[] iArrCreateIntArray6 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifyCarrierPrivilegesChanged(i77, arrayListCreateStringArrayList, iArrCreateIntArray6);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int i78 = parcel.readInt();
                    String string14 = parcel.readString();
                    int i79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCarrierServiceChanged(i78, string14, i79);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    ICarrierConfigChangeListener iCarrierConfigChangeListenerAsInterface = ICarrierConfigChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addCarrierConfigChangeListener(iCarrierConfigChangeListenerAsInterface, string15, string16);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    ICarrierConfigChangeListener iCarrierConfigChangeListenerAsInterface2 = ICarrierConfigChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeCarrierConfigChangeListener(iCarrierConfigChangeListenerAsInterface2, string17);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int i80 = parcel.readInt();
                    int i81 = parcel.readInt();
                    int i82 = parcel.readInt();
                    int i83 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCarrierConfigChanged(i80, i81, i82, i83);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int i84 = parcel.readInt();
                    int i85 = parcel.readInt();
                    int i86 = parcel.readInt();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyCallbackModeStarted(i84, i85, i86, j2);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    int i87 = parcel.readInt();
                    int i88 = parcel.readInt();
                    int i89 = parcel.readInt();
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyCallbackModeRestarted(i87, i88, i89, j3);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    int i90 = parcel.readInt();
                    int i91 = parcel.readInt();
                    int i92 = parcel.readInt();
                    int i93 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCallbackModeStopped(i90, i91, i92, i93);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    int i94 = parcel.readInt();
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCarrierRoamingNtnModeChanged(i94, z11);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    int i95 = parcel.readInt();
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCarrierRoamingNtnEligibleStateChanged(i95, z12);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    int i96 = parcel.readInt();
                    int[] iArrCreateIntArray7 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifyCarrierRoamingNtnAvailableServicesChanged(i96, iArrCreateIntArray7);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int i97 = parcel.readInt();
                    NtnSignalStrength ntnSignalStrength = (NtnSignalStrength) parcel.readTypedObject(NtnSignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCarrierRoamingNtnSignalStrengthChanged(i97, ntnSignalStrength);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    ISatelliteStateChangeListener iSatelliteStateChangeListenerAsInterface = ISatelliteStateChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    String string18 = parcel.readString();
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSatelliteStateChangeListener(iSatelliteStateChangeListenerAsInterface, string18, string19);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    ISatelliteStateChangeListener iSatelliteStateChangeListenerAsInterface2 = ISatelliteStateChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSatelliteStateChangeListener(iSatelliteStateChangeListenerAsInterface2, string20);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifySatelliteStateChanged(z13);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int i98 = parcel.readInt();
                    int i99 = parcel.readInt();
                    SecurityAlgorithmUpdate securityAlgorithmUpdate = (SecurityAlgorithmUpdate) parcel.readTypedObject(SecurityAlgorithmUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySecurityAlgorithmsChanged(i98, i99, securityAlgorithmUpdate);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    int i100 = parcel.readInt();
                    int i101 = parcel.readInt();
                    CellularIdentifierDisclosure cellularIdentifierDisclosure = (CellularIdentifierDisclosure) parcel.readTypedObject(CellularIdentifierDisclosure.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCellularIdentifierDisclosedChanged(i100, i101, cellularIdentifierDisclosure);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    int i102 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPreciseDataConnectionStates(i102);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int i103 = parcel.readInt();
                    int i104 = parcel.readInt();
                    SemSatelliteServiceState semSatelliteServiceState = (SemSatelliteServiceState) parcel.readTypedObject(SemSatelliteServiceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySemSatelliteServiceStateChanged(i103, i104, semSatelliteServiceState);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int i105 = parcel.readInt();
                    int i106 = parcel.readInt();
                    SemSatelliteSignalStrength semSatelliteSignalStrength = (SemSatelliteSignalStrength) parcel.readTypedObject(SemSatelliteSignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySemSatelliteSignalStrengthChanged(i105, i106, semSatelliteSignalStrength);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    int i107 = parcel.readInt();
                    int i108 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCpaiModelUpdate(i107, i108);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    int i109 = parcel.readInt();
                    int i110 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCpaiFeatureInfo(i109, i110);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    int i111 = parcel.readInt();
                    int i112 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    notifyCpaiDataGathering(i111, i112, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    int i113 = parcel.readInt();
                    int i114 = parcel.readInt();
                    int i115 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    notifyCpaiDevAppMessage(i113, i114, i115, bArrCreateByteArray3);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iOnSubscriptionsChangedListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void addOnOpportunisticSubscriptionsChangedListener(String str, String str2, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iOnSubscriptionsChangedListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void removeOnSubscriptionsChangedListener(String str, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iOnSubscriptionsChangedListener);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void listenWithEventList(boolean z, boolean z2, int i, String str, String str2, IPhoneStateListener iPhoneStateListener, int[] iArr, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iPhoneStateListener);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallStateForAllSubs(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallState(int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyServiceStateForPhoneId(int i, int i2, ServiceState serviceState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(serviceState, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySignalStrengthForPhoneId(int i, int i2, SignalStrength signalStrength) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(signalStrength, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyMessageWaitingChangedForPhoneId(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallForwardingChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallForwardingChangedForSubscriber(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDataActivityForSubscriber(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDataActivityForSubscriberWithSlot(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDataConnectionForSubscriber(int i, int i2, PreciseDataConnectionState preciseDataConnectionState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(preciseDataConnectionState, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCellLocationForSubscriber(int i, CellIdentity cellIdentity) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(cellIdentity, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCellInfo(List<CellInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyPreciseCallState(int i, int i2, int[] iArr, String[] strArr, int[] iArr2, int[] iArr3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeIntArray(iArr2);
                    parcelObtain.writeIntArray(iArr3);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDisconnectCause(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCellInfoForSubscriber(int i, List<CellInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySrvccStateChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySimActivationStateChangedForPhoneId(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyOemHookRawEventForSubscriber(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySubscriptionInfoChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyOpportunisticSubscriptionInfoChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierNetworkChange(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierNetworkChangeWithSubId(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyUserMobileDataStateChangedForPhoneId(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDisplayInfoChanged(int i, int i2, TelephonyDisplayInfo telephonyDisplayInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(telephonyDisplayInfo, 0);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyPhoneCapabilityChanged(PhoneCapability phoneCapability) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneCapability, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyActiveDataSubIdChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyRadioPowerStateChanged(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyEmergencyNumberList(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyOutgoingEmergencyCall(int i, int i2, EmergencyNumber emergencyNumber) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(emergencyNumber, 0);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyOutgoingEmergencySms(int i, int i2, EmergencyNumber emergencyNumber) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(emergencyNumber, 0);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallQualityChanged(CallQuality callQuality, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(callQuality, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyMediaQualityStatusChanged(int i, int i2, MediaQualityStatus mediaQualityStatus) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(mediaQualityStatus, 0);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyImsDisconnectCause(int i, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyRegistrationFailed(int i, int i2, CellIdentity cellIdentity, String str, int i3, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(cellIdentity, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyBarringInfoChanged(int i, int i2, BarringInfo barringInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(barringInfo, 0);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyPhysicalChannelConfigForSubscriber(int i, int i2, List<PhysicalChannelConfig> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDataEnabled(int i, int i2, boolean z, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyAllowedNetworkTypesChanged(int i, int i2, int i3, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyLinkCapacityEstimateChanged(int i, int i2, List<LinkCapacityEstimate> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySimultaneousCellularCallingSubscriptionsChanged(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void addCarrierPrivilegesCallback(int i, ICarrierPrivilegesCallback iCarrierPrivilegesCallback, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iCarrierPrivilegesCallback);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void removeCarrierPrivilegesCallback(ICarrierPrivilegesCallback iCarrierPrivilegesCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCarrierPrivilegesCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierPrivilegesChanged(int i, List<String> list, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierServiceChanged(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void addCarrierConfigChangeListener(ICarrierConfigChangeListener iCarrierConfigChangeListener, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCarrierConfigChangeListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void removeCarrierConfigChangeListener(ICarrierConfigChangeListener iCarrierConfigChangeListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCarrierConfigChangeListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierConfigChanged(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallbackModeStarted(int i, int i2, int i3, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallbackModeRestarted(int i, int i2, int i3, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallbackModeStopped(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierRoamingNtnModeChanged(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierRoamingNtnEligibleStateChanged(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierRoamingNtnAvailableServicesChanged(int i, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierRoamingNtnSignalStrengthChanged(int i, NtnSignalStrength ntnSignalStrength) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(ntnSignalStrength, 0);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void addSatelliteStateChangeListener(ISatelliteStateChangeListener iSatelliteStateChangeListener, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSatelliteStateChangeListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void removeSatelliteStateChangeListener(ISatelliteStateChangeListener iSatelliteStateChangeListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSatelliteStateChangeListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySatelliteStateChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySecurityAlgorithmsChanged(int i, int i2, SecurityAlgorithmUpdate securityAlgorithmUpdate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(securityAlgorithmUpdate, 0);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCellularIdentifierDisclosedChanged(int i, int i2, CellularIdentifierDisclosure cellularIdentifierDisclosure) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(cellularIdentifierDisclosure, 0);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void clearPreciseDataConnectionStates(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySemSatelliteServiceStateChanged(int i, int i2, SemSatelliteServiceState semSatelliteServiceState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(semSatelliteServiceState, 0);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySemSatelliteSignalStrengthChanged(int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(semSatelliteSignalStrength, 0);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCpaiModelUpdate(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCpaiFeatureInfo(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCpaiDataGathering(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCpaiDevAppMessage(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
