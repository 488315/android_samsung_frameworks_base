package com.android.internal.telephony;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.WorkSource;
import android.service.carrier.CarrierIdentifier;
import android.telecom.PhoneAccountHandle;
import android.telephony.CallForwardingInfo;
import android.telephony.CarrierRestrictionRules;
import android.telephony.CellBroadcastIdRange;
import android.telephony.CellIdentity;
import android.telephony.CellInfo;
import android.telephony.ClientRequestStats;
import android.telephony.IBootstrapAuthenticationCallback;
import android.telephony.ICellInfoCallback;
import android.telephony.IccOpenLogicalChannelResponse;
import android.telephony.NeighboringCellInfo;
import android.telephony.NetworkScanRequest;
import android.telephony.PhoneCapability;
import android.telephony.PhoneNumberRange;
import android.telephony.RadioAccessSpecifier;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SignalStrengthUpdateRequest;
import android.telephony.TelephonyHistogram;
import android.telephony.ThermalMitigationRequest;
import android.telephony.UiccCardInfo;
import android.telephony.UiccSlotInfo;
import android.telephony.UiccSlotMapping;
import android.telephony.VisualVoicemailSmsFilterSettings;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.gba.UaSecurityProtocolIdentifier;
import android.telephony.ims.RcsClientConfiguration;
import android.telephony.ims.RcsContactUceCapability;
import android.telephony.ims.aidl.IFeatureProvisioningCallback;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsConfigCallback;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.telephony.ims.aidl.IRcsConfigCallback;
import android.telephony.satellite.INtnSignalStrengthCallback;
import android.telephony.satellite.ISatelliteCapabilitiesCallback;
import android.telephony.satellite.ISatelliteCommunicationAccessStateCallback;
import android.telephony.satellite.ISatelliteDatagramCallback;
import android.telephony.satellite.ISatelliteDisallowedReasonsCallback;
import android.telephony.satellite.ISatelliteModemStateCallback;
import android.telephony.satellite.ISatelliteProvisionStateCallback;
import android.telephony.satellite.ISatelliteTransmissionUpdateCallback;
import android.telephony.satellite.ISelectedNbIotSatelliteSubscriptionCallback;
import android.telephony.satellite.SatelliteDatagram;
import android.telephony.satellite.SatelliteSubscriberInfo;
import com.android.ims.internal.IImsServiceFeatureCallback;
import com.android.internal.telephony.IBooleanConsumer;
import com.android.internal.telephony.ICallForwardingInfoCallback;
import com.android.internal.telephony.IImsStateCallback;
import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.telephony.INumberVerificationCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public interface ITelephony extends IInterface {

    public static class Default implements ITelephony {
        @Override // com.android.internal.telephony.ITelephony
        public void addAttachRestrictionForCarrier(int i, int i2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public RcsContactUceCapability addUceRegistrationOverrideShell(int i, List<String> list) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void bootstrapAuthenticationRequest(int i, int i2, Uri uri, UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier, boolean z, IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void call(String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean canChangeDtmfToneLength(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean canConnectTo5GInDsdsMode() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void carrierActionReportDefaultNetworkStatus(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void carrierActionResetAll(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void carrierActionSetRadioEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int changeIccLockPassword(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int checkCarrierPrivilegesForPackage(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int checkCarrierPrivilegesForPackageAnyPhone(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean clearCarrierImsServiceOverride(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean clearDomainSelectionServiceOverride() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean clearRadioPowerOffForReason(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void clearSignalStrengthUpdateRequest(int i, SignalStrengthUpdateRequest signalStrengthUpdateRequest, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public RcsContactUceCapability clearUceRegistrationOverrideShell(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void deprovisionSatellite(List<SatelliteSubscriberInfo> list, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void deprovisionSatelliteService(String str, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void dial(String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean disableDataConnectivity(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void disableIms(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void disableLocationUpdates() throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void disableVisualVoicemailSmsFilter(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean doesSwitchMultiSimConfigTriggerReboot(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean enableDataConnectivity(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void enableIms(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void enableLocationUpdates() throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean enableModemForSlot(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void enableVideoCalling(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void enableVisualVoicemailSmsFilter(String str, int i, VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void enqueueSmsPickResult(String str, String str2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void factoryReset(int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getActivePhoneType() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getActivePhoneTypeForSlot(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getAidForAppType(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<CellInfo> getAllCellInfo(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public CarrierRestrictionRules getAllowedCarriers() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getAllowedNetworkTypesBitmask(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public long getAllowedNetworkTypesForReason(int i, int i2) throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int[] getAttachRestrictionReasonsForCarrier(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getBoundGbaService(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getBoundImsServicePackage(int i, boolean z, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCallComposerStatus(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void getCallForwarding(int i, int i2, ICallForwardingInfoCallback iCallForwardingInfoCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCallState() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCallStateForSubscription(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void getCallWaitingStatus(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getCapabilityFromEab(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCardIdForDefaultEuicc(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCarrierIdFromIdentifier(CarrierIdentifier carrierIdentifier) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCarrierIdFromMccMnc(int i, String str, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCarrierIdListVersion(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<String> getCarrierPackageNamesForIntentAndPhone(Intent intent, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCarrierPrivilegeStatus(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCarrierPrivilegeStatusForUid(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void getCarrierRestrictionStatus(IIntegerConsumer iIntegerConsumer, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getCarrierServicePackageNameForLogicalSlot(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getCarrierSingleRegistrationEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCdmaEriIconIndex(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCdmaEriIconIndexForSubscriber(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCdmaEriIconMode(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCdmaEriIconModeForSubscriber(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getCdmaEriText(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getCdmaEriTextForSubscriber(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getCdmaMdn(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getCdmaMin(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getCdmaPrlVersion(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCdmaRoamingMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCdmaSubscriptionMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<CellBroadcastIdRange> getCellBroadcastIdRanges(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public CellIdentity getCellLocation(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public CellNetworkScanResult getCellNetworkScanResults(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<String> getCertsFromCarrierPrivilegeAccessRules(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<ClientRequestStats> getClientRequestStats(String str, String str2, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getContactFromEab(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getCurrentPackageName() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getDataActivationState(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getDataActivity() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getDataActivityForSubId(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getDataEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getDataNetworkType(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getDataNetworkTypeForSubscriber(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getDataState() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getDataStateForSubId(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public ComponentName getDefaultRespondViaMessageApplication(int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getDeviceId(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getDeviceIdWithFeature(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getDeviceSingleRegistrationEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getDeviceSoftwareVersionForSlot(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getDeviceUceEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getEmergencyCallbackMode(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getEmergencyNumberDbVersion(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public Map getEmergencyNumberList(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<String> getEmergencyNumberListTestMode() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<String> getEquivalentHomePlmns(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getEsn(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String[] getForbiddenPlmns(int i, int i2, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getGbaReleaseTime(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getHalVersion(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getImeiForSlot(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public IImsConfig getImsConfig(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getImsFeatureValidationOverride(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void getImsMmTelFeatureState(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void getImsMmTelRegistrationState(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void getImsMmTelRegistrationTransportType(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getImsProvisioningInt(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getImsProvisioningStatusForCapability(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getImsProvisioningString(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getImsRegTechnologyForMmTel(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public IImsRegistration getImsRegistration(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public CellIdentity getLastKnownCellIdentity(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getLastUcePidfXmlShell(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public RcsContactUceCapability getLatestRcsContactUceCapabilityShell(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getLine1AlphaTagForDisplay(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getLine1NumberForDisplay(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getLteOnCdmaMode(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getLteOnCdmaModeForSubscriber(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getManualNetworkSelectionPlmn(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getManufacturerCodeForSlot(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getMeidForSlot(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String[] getMergedImsisFromGroup(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String[] getMergedSubscriberIds(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getMmsUAProfUrl(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getMmsUserAgent(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getMobileProvisioningUrl() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getModemService() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<NeighboringCellInfo> getNeighboringCellInfo(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getNetworkCountryIsoForPhone(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getNetworkSelectionMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getNetworkTypeForSubscriber(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getNumberOfModemsWithSimultaneousDataConnections(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<String> getPackagesWithCarrierPrivileges(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<String> getPackagesWithCarrierPrivilegesForAllPhones() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public PhoneCapability getPhoneCapability() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getPrimaryImei(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getRadioAccessFamily(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getRadioHalVersion() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List getRadioPowerOffReasons(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getRadioPowerState(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getRcsProvisioningStatusForCapability(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getRcsSingleRegistrationTestModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<String> getSatelliteDataOptimizedApps() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getSatelliteDataSupportMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int[] getSatelliteDisallowedReasons() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<String> getSatellitePlmnsForCarrier(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public ServiceState getServiceStateForSlot(int i, boolean z, boolean z2, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<String> getShaIdFromAllowList(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public SignalStrength getSignalStrength(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getSimLocaleForSubscriber(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getSimStateForSlotIndex(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void getSlicingConfig(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<UiccSlotMapping> getSlotsMapping(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getSubIdForPhoneAccountHandle(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getSubscriptionCarrierId(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getSubscriptionCarrierName(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getSubscriptionSpecificCarrierId(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getSubscriptionSpecificCarrierName(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<RadioAccessSpecifier> getSystemSelectionChannels(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<TelephonyHistogram> getTelephonyHistograms() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public ComponentName getTestEuiccUiComponent() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getTypeAllocationCodeForSlot(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public List<UiccCardInfo> getUiccCardsInfo(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public UiccSlotInfo[] getUiccSlotsInfo(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String getVisualVoicemailPackageName(String str, String str2, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public Bundle getVisualVoicemailSettings(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getVoWiFiModeSetting(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getVoWiFiRoamingModeSetting(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getVoiceActivationState(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getVoiceMessageCountForSubscriber(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getVoiceNetworkTypeForSubscriber(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public Uri getVoicemailRingtoneUri(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean handlePinMmi(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean handlePinMmiForSubscriber(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void handleUssdRequest(int i, String str, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean hasIccCard() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean hasIccCardUsingSlotIndex(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean iccCloseLogicalChannel(IccLogicalChannelRequest iccLogicalChannelRequest) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public byte[] iccExchangeSimIO(int i, int i2, int i3, int i4, int i5, int i6, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public IccOpenLogicalChannelResponse iccOpenLogicalChannel(IccLogicalChannelRequest iccLogicalChannelRequest) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String iccTransmitApduBasicChannel(int i, String str, int i2, int i3, int i4, int i5, int i6, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String iccTransmitApduBasicChannelByPort(int i, int i2, String str, int i3, int i4, int i5, int i6, int i7, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String iccTransmitApduLogicalChannel(int i, int i2, int i3, int i4, int i5, int i6, int i7, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public String iccTransmitApduLogicalChannelByPort(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isAdvancedCallingSettingEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isAospDomainSelectionService() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isApnMetered(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isApplicationOnUicc(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isAvailable(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isCapable(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isCellularIdentifierDisclosureNotificationsEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isConcurrentVoiceAndDataAllowed(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isCrossSimCallingEnabledByUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isDataConnectivityPossible(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isDataEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isDataEnabledForApn(int i, int i2, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isDataEnabledForReason(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isDataRoamingEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isDomainSelectionSupported() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isEmergencyNumber(String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isHearingAidCompatibilitySupported() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isIccLockEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isImsRegistered(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isInEmergencySmsMode() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isManualNetworkSelectionAllowed(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void isMmTelCapabilitySupported(int i, IIntegerConsumer iIntegerConsumer, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isMobileDataPolicyEnabled(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isModemEnabledForSlot(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int isMultiSimSupported(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isMvnoMatched(int i, int i2, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isNrDualConnectivityEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isNullCipherAndIntegrityPreferenceEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isNullCipherNotificationsEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isPremiumCapabilityAvailableForPurchase(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isProvisioningRequiredForCapability(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRadioInterfaceCapabilitySupported(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRadioOn(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRadioOnForSubscriber(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRadioOnForSubscriberWithFeature(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRadioOnWithFeature(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRcsProvisioningRequiredForCapability(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRcsVolteSingleRegistrationCapable(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRemovableEsimDefaultEuicc(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRttSupported(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isTetheringApnRequiredForSubscriber(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isTtyModeSupported() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isTtyOverVolteEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isUserDataEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isVideoCallingEnabled(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isVideoTelephonyAvailable(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isVoNrEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isVoWiFiRoamingSettingEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isVoWiFiSettingEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isVoicemailVibrationEnabled(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isVtSettingEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isWifiCallingAvailable(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isWorldPhone(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean needMobileRadioShutdown() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean needsOtaServiceProvisioning() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void notifyOtaEmergencyNumberDbInstalled() throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void notifyRcsAutoConfigurationReceived(int i, byte[] bArr, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public String nvReadItem(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean nvWriteCdmaPrl(byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean nvWriteItem(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean overrideCarrierRoamingNtnEligibilityChanged(boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean overrideConfigDataVersion(boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void persistEmergencyCallDiagnosticData(String str, boolean z, long j, boolean z2, boolean z3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void pollPendingDatagrams(IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int prepareForUnattendedReboot() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void provisionSatellite(List<SatelliteSubscriberInfo> list, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public ICancellationSignal provisionSatelliteService(String str, byte[] bArr, IIntegerConsumer iIntegerConsumer) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void purchasePremiumCapability(int i, IIntegerConsumer iIntegerConsumer, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean rebootModem(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void refreshUiccProfile(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerFeatureProvisioningChangedCallback(int i, IFeatureProvisioningCallback iFeatureProvisioningCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int registerForCapabilitiesChanged(ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallback) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int registerForCommunicationAccessStateChanged(int i, ISatelliteCommunicationAccessStateCallback iSatelliteCommunicationAccessStateCallback) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int registerForIncomingDatagram(ISatelliteDatagramCallback iSatelliteDatagramCallback) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerForNtnSignalStrengthChanged(INtnSignalStrengthCallback iNtnSignalStrengthCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerForSatelliteDisallowedReasonsChanged(ISatelliteDisallowedReasonsCallback iSatelliteDisallowedReasonsCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int registerForSatelliteModemStateChanged(ISatelliteModemStateCallback iSatelliteModemStateCallback) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int registerForSatelliteProvisionStateChanged(ISatelliteProvisionStateCallback iSatelliteProvisionStateCallback) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int registerForSatelliteSupportedStateChanged(IBooleanConsumer iBooleanConsumer) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int registerForSelectedNbIotSatelliteSubscriptionChanged(ISelectedNbIotSatelliteSubscriptionCallback iSelectedNbIotSatelliteSubscriptionCallback) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerImsEmergencyRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerImsProvisioningChangedCallback(int i, IImsConfigCallback iImsConfigCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerImsStateCallback(int i, int i2, IImsStateCallback iImsStateCallback, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerMmTelCapabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerMmTelFeatureCallback(int i, IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerRcsProvisioningCallback(int i, IRcsConfigCallback iRcsConfigCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void removeAttachRestrictionForCarrier(int i, int i2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int removeContactFromEab(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public RcsContactUceCapability removeUceRegistrationOverrideShell(int i, List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean removeUceRequestDisallowedStatus(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestCellInfoUpdate(int i, ICellInfoCallback iCellInfoCallback, String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestCellInfoUpdateWithWorkSource(int i, ICellInfoCallback iCellInfoCallback, String str, String str2, WorkSource workSource) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestIsCommunicationAllowedForCurrentLocation(int i, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestIsDemoModeEnabled(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestIsEmergencyModeEnabled(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestIsSatelliteEnabled(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestIsSatelliteProvisioned(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestIsSatelliteSupported(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestModemActivityInfo(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int requestNetworkScan(int i, boolean z, NetworkScanRequest networkScanRequest, Messenger messenger, IBinder iBinder, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestNtnSignalStrength(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestNumberVerification(PhoneNumberRange phoneNumberRange, long j, INumberVerificationCallback iNumberVerificationCallback, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean requestRadioPowerOffForReason(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestSatelliteAccessConfigurationForCurrentLocation(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestSatelliteCapabilities(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestSatelliteDisplayName(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestSatelliteEnabled(boolean z, boolean z2, boolean z3, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestSatelliteSessionStats(int i, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestSatelliteSubscriberProvisionStatus(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestSelectedNbIotSatelliteSubscriptionId(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestTimeForNextSatelliteVisibility(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestUserActivityNotification() throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void resetIms(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean resetModemConfig(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void resetOtaEmergencyNumberDbFilePath() throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void sendDatagram(int i, SatelliteDatagram satelliteDatagram, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void sendDeviceToDeviceMessage(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void sendDialerSpecialCode(String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public String sendEnvelopeWithStatus(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int sendThermalMitigationRequest(int i, ThermalMitigationRequest thermalMitigationRequest, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void sendVisualVoicemailSmsForSubscriber(String str, String str2, int i, String str3, int i2, String str4, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setActiveDeviceToDeviceTransport(String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setAdvancedCallingSettingEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int setAllowedCarriers(CarrierRestrictionRules carrierRestrictionRules) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setAllowedNetworkTypesForReason(int i, int i2, long j) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setBoundGbaServiceOverride(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setBoundImsServiceOverride(int i, int i2, boolean z, int[] iArr, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCallComposerStatus(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCallForwarding(int i, CallForwardingInfo callForwardingInfo, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCallWaitingStatus(int i, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setCapabilitiesRequestTimeout(int i, long j) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCarrierServicePackageOverride(int i, String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setCarrierSingleRegistrationEnabledOverride(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCarrierTestOverride(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setCdmaRoamingMode(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setCdmaSubscriptionMode(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCellBroadcastIdRanges(int i, List<CellBroadcastIdRange> list, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCellInfoListRate(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCepEnabled(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setCountryCodes(boolean z, List<String> list, Map map, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCrossSimCallingEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setDataActivationState(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setDataEnabledForReason(int i, int i2, boolean z, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setDataRoamingEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setDatagramControllerBooleanConfig(boolean z, int i, boolean z2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setDatagramControllerTimeoutDuration(boolean z, int i, long j) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setDeviceAlignedWithSatellite(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setDeviceSingleRegistrationEnabledOverride(String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setDeviceToDeviceForceEnabled(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setDeviceUceEnabled(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setDomainSelectionServiceOverride(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setEmergencyCallToSatelliteHandoverType(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setEnableCellularIdentifierDisclosureNotifications(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int setForbiddenPlmns(int i, int i2, List<String> list, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setGbaReleaseTimeOverride(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int setIccLockEnabled(int i, boolean z, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setImsFeatureValidationOverride(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int setImsProvisioningInt(int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setImsProvisioningStatusForCapability(int i, int i2, int i3, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int setImsProvisioningString(int i, int i2, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setImsRegistrationState(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setIsSatelliteCommunicationAllowedForCurrentLocationCache(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setLine1NumberForDisplayForSubscriber(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setMobileDataPolicyEnabled(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setModemService(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setMultiSimCarrierRestriction(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setNetworkSelectionModeAutomatic(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setNetworkSelectionModeManual(int i, OperatorInfo operatorInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int setNrDualConnectivityState(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setNtnSmsSupported(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setNullCipherAndIntegrityEnabled(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setNullCipherNotificationsEnabled(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setOemEnabledSatelliteProvisionStatus(boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setOperatorBrandOverride(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setRadio(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setRadioForSubscriber(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setRadioPower(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setRcsClientConfiguration(int i, RcsClientConfiguration rcsClientConfiguration) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setRcsProvisioningStatusForCapability(int i, int i2, int i3, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setRcsSingleRegistrationTestModeEnabled(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setRemovableEsimAsDefaultEuicc(boolean z, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setRoamingOverride(int i, List<String> list, List<String> list2, List<String> list3, List<String> list4) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setRttCapabilitySetting(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatelliteAccessAllowedForSubscriptions(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatelliteAccessControlOverlayConfigs(boolean z, boolean z2, String str, long j, List<String> list, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatelliteControllerTimeoutDuration(boolean z, int i, long j) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatelliteGatewayServicePackageName(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatelliteIgnoreCellularServiceState(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatelliteIgnorePlmnListFromStorage(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatelliteListeningTimeoutDuration(long j) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatellitePointingUiClassName(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatelliteServicePackageName(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatelliteSubscriberIdListChangedIntentComponent(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setShouldSendDatagramToModemInDemoMode(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setSignalStrengthUpdateRequest(int i, SignalStrengthUpdateRequest signalStrengthUpdateRequest, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setSimPowerStateForSlot(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setSimPowerStateForSlotWithCallback(int i, int i2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSimSlotMapping(List<UiccSlotMapping> list) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSupportDisableSatelliteWhileEnableInProgress(boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setSystemSelectionChannels(List<RadioAccessSpecifier> list, int i, IBooleanConsumer iBooleanConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setTestEuiccUiComponent(ComponentName componentName) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setTnScanningSupport(boolean z, boolean z2, boolean z3) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int setVoNrEnabled(int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoWiFiModeSetting(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoWiFiNonPersistent(int i, boolean z, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoWiFiRoamingModeSetting(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoWiFiRoamingSettingEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoWiFiSettingEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoiceActivationState(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setVoiceMailNumber(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoiceServiceStateOverride(int i, boolean z, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoicemailRingtoneUri(String str, PhoneAccountHandle phoneAccountHandle, Uri uri) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoicemailVibrationEnabled(String str, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVtSettingEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void showSwitchToManagedProfileDialog() throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void shutdownMobileRadios() throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void startEmergencyCallbackMode() throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void startSatelliteTransmissionUpdates(IIntegerConsumer iIntegerConsumer, ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void stopNetworkScan(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void stopSatelliteTransmissionUpdates(IIntegerConsumer iIntegerConsumer, ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean supplyPinForSubscriber(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int[] supplyPinReportResultForSubscriber(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean supplyPukForSubscriber(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int[] supplyPukReportResultForSubscriber(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void switchMultiSimConfig(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean switchSlots(int[] iArr) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void toggleRadioOnOff() throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void toggleRadioOnOffForSubscriber(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void triggerRcsReconfiguration(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterFeatureProvisioningChangedCallback(int i, IFeatureProvisioningCallback iFeatureProvisioningCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterForCapabilitiesChanged(ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterForCommunicationAccessStateChanged(int i, ISatelliteCommunicationAccessStateCallback iSatelliteCommunicationAccessStateCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterForIncomingDatagram(ISatelliteDatagramCallback iSatelliteDatagramCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterForModemStateChanged(ISatelliteModemStateCallback iSatelliteModemStateCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterForNtnSignalStrengthChanged(INtnSignalStrengthCallback iNtnSignalStrengthCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterForSatelliteDisallowedReasonsChanged(ISatelliteDisallowedReasonsCallback iSatelliteDisallowedReasonsCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterForSatelliteProvisionStateChanged(ISatelliteProvisionStateCallback iSatelliteProvisionStateCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterForSatelliteSupportedStateChanged(IBooleanConsumer iBooleanConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterForSelectedNbIotSatelliteSubscriptionChanged(ISelectedNbIotSatelliteSubscriptionCallback iSelectedNbIotSatelliteSubscriptionCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterImsEmergencyRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterImsFeatureCallback(IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterImsProvisioningChangedCallback(int i, IImsConfigCallback iImsConfigCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterImsStateCallback(IImsStateCallback iImsStateCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterMmTelCapabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterRcsProvisioningCallback(int i, IRcsConfigCallback iRcsConfigCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void updateEmergencyNumberListTestMode(int i, EmergencyNumber emergencyNumber) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void updateOtaEmergencyNumberDbFilePath(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void updateServiceLocation() throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void updateServiceLocationWithPackageName(String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void uploadCallComposerPicture(int i, String str, String str2, ParcelFileDescriptor parcelFileDescriptor, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void userActivity() throws RemoteException {
        }
    }

    void addAttachRestrictionForCarrier(int i, int i2, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    RcsContactUceCapability addUceRegistrationOverrideShell(int i, List<String> list) throws RemoteException;

    void bootstrapAuthenticationRequest(int i, int i2, Uri uri, UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier, boolean z, IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback) throws RemoteException;

    void call(String str, String str2) throws RemoteException;

    boolean canChangeDtmfToneLength(int i, String str, String str2) throws RemoteException;

    boolean canConnectTo5GInDsdsMode() throws RemoteException;

    void carrierActionReportDefaultNetworkStatus(int i, boolean z) throws RemoteException;

    void carrierActionResetAll(int i) throws RemoteException;

    void carrierActionSetRadioEnabled(int i, boolean z) throws RemoteException;

    int changeIccLockPassword(int i, String str, String str2) throws RemoteException;

    int checkCarrierPrivilegesForPackage(int i, String str) throws RemoteException;

    int checkCarrierPrivilegesForPackageAnyPhone(String str) throws RemoteException;

    boolean clearCarrierImsServiceOverride(int i) throws RemoteException;

    boolean clearDomainSelectionServiceOverride() throws RemoteException;

    boolean clearRadioPowerOffForReason(int i, int i2) throws RemoteException;

    void clearSignalStrengthUpdateRequest(int i, SignalStrengthUpdateRequest signalStrengthUpdateRequest, String str) throws RemoteException;

    RcsContactUceCapability clearUceRegistrationOverrideShell(int i) throws RemoteException;

    void deprovisionSatellite(List<SatelliteSubscriberInfo> list, ResultReceiver resultReceiver) throws RemoteException;

    void deprovisionSatelliteService(String str, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void dial(String str) throws RemoteException;

    boolean disableDataConnectivity(String str) throws RemoteException;

    void disableIms(int i) throws RemoteException;

    void disableLocationUpdates() throws RemoteException;

    void disableVisualVoicemailSmsFilter(String str, int i) throws RemoteException;

    boolean doesSwitchMultiSimConfigTriggerReboot(int i, String str, String str2) throws RemoteException;

    boolean enableDataConnectivity(String str) throws RemoteException;

    void enableIms(int i) throws RemoteException;

    void enableLocationUpdates() throws RemoteException;

    boolean enableModemForSlot(int i, boolean z) throws RemoteException;

    void enableVideoCalling(boolean z) throws RemoteException;

    void enableVisualVoicemailSmsFilter(String str, int i, VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) throws RemoteException;

    void enqueueSmsPickResult(String str, String str2, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void factoryReset(int i, String str) throws RemoteException;

    int getActivePhoneType() throws RemoteException;

    int getActivePhoneTypeForSlot(int i) throws RemoteException;

    VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int i) throws RemoteException;

    String getAidForAppType(int i, int i2) throws RemoteException;

    List<CellInfo> getAllCellInfo(String str, String str2) throws RemoteException;

    CarrierRestrictionRules getAllowedCarriers() throws RemoteException;

    int getAllowedNetworkTypesBitmask(int i) throws RemoteException;

    long getAllowedNetworkTypesForReason(int i, int i2) throws RemoteException;

    int[] getAttachRestrictionReasonsForCarrier(int i) throws RemoteException;

    String getBoundGbaService(int i) throws RemoteException;

    String getBoundImsServicePackage(int i, boolean z, int i2) throws RemoteException;

    int getCallComposerStatus(int i) throws RemoteException;

    void getCallForwarding(int i, int i2, ICallForwardingInfoCallback iCallForwardingInfoCallback) throws RemoteException;

    int getCallState() throws RemoteException;

    int getCallStateForSubscription(int i, String str, String str2) throws RemoteException;

    void getCallWaitingStatus(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    String getCapabilityFromEab(String str) throws RemoteException;

    int getCardIdForDefaultEuicc(int i, String str) throws RemoteException;

    int getCarrierIdFromIdentifier(CarrierIdentifier carrierIdentifier) throws RemoteException;

    int getCarrierIdFromMccMnc(int i, String str, boolean z) throws RemoteException;

    int getCarrierIdListVersion(int i) throws RemoteException;

    List<String> getCarrierPackageNamesForIntentAndPhone(Intent intent, int i) throws RemoteException;

    int getCarrierPrivilegeStatus(int i) throws RemoteException;

    int getCarrierPrivilegeStatusForUid(int i, int i2) throws RemoteException;

    void getCarrierRestrictionStatus(IIntegerConsumer iIntegerConsumer, String str) throws RemoteException;

    String getCarrierServicePackageNameForLogicalSlot(int i) throws RemoteException;

    boolean getCarrierSingleRegistrationEnabled(int i) throws RemoteException;

    @Deprecated
    int getCdmaEriIconIndex(String str, String str2) throws RemoteException;

    @Deprecated
    int getCdmaEriIconIndexForSubscriber(int i, String str, String str2) throws RemoteException;

    @Deprecated
    int getCdmaEriIconMode(String str, String str2) throws RemoteException;

    @Deprecated
    int getCdmaEriIconModeForSubscriber(int i, String str, String str2) throws RemoteException;

    @Deprecated
    String getCdmaEriText(String str, String str2) throws RemoteException;

    @Deprecated
    String getCdmaEriTextForSubscriber(int i, String str, String str2) throws RemoteException;

    @Deprecated
    String getCdmaMdn(int i) throws RemoteException;

    @Deprecated
    String getCdmaMin(int i) throws RemoteException;

    @Deprecated
    String getCdmaPrlVersion(int i) throws RemoteException;

    @Deprecated
    int getCdmaRoamingMode(int i) throws RemoteException;

    @Deprecated
    int getCdmaSubscriptionMode(int i) throws RemoteException;

    List<CellBroadcastIdRange> getCellBroadcastIdRanges(int i) throws RemoteException;

    CellIdentity getCellLocation(String str, String str2) throws RemoteException;

    CellNetworkScanResult getCellNetworkScanResults(int i, String str, String str2) throws RemoteException;

    List<String> getCertsFromCarrierPrivilegeAccessRules(int i) throws RemoteException;

    List<ClientRequestStats> getClientRequestStats(String str, String str2, int i) throws RemoteException;

    String getContactFromEab(String str) throws RemoteException;

    String getCurrentPackageName() throws RemoteException;

    int getDataActivationState(int i, String str) throws RemoteException;

    int getDataActivity() throws RemoteException;

    int getDataActivityForSubId(int i) throws RemoteException;

    boolean getDataEnabled(int i) throws RemoteException;

    int getDataNetworkType(String str, String str2) throws RemoteException;

    int getDataNetworkTypeForSubscriber(int i, String str, String str2) throws RemoteException;

    int getDataState() throws RemoteException;

    int getDataStateForSubId(int i) throws RemoteException;

    ComponentName getDefaultRespondViaMessageApplication(int i, boolean z) throws RemoteException;

    @Deprecated
    String getDeviceId(String str) throws RemoteException;

    String getDeviceIdWithFeature(String str, String str2) throws RemoteException;

    boolean getDeviceSingleRegistrationEnabled() throws RemoteException;

    String getDeviceSoftwareVersionForSlot(int i, String str, String str2) throws RemoteException;

    boolean getDeviceUceEnabled() throws RemoteException;

    boolean getEmergencyCallbackMode(int i) throws RemoteException;

    int getEmergencyNumberDbVersion(int i) throws RemoteException;

    Map getEmergencyNumberList(String str, String str2) throws RemoteException;

    List<String> getEmergencyNumberListTestMode() throws RemoteException;

    List<String> getEquivalentHomePlmns(int i, String str, String str2) throws RemoteException;

    String getEsn(int i) throws RemoteException;

    String[] getForbiddenPlmns(int i, int i2, String str, String str2) throws RemoteException;

    int getGbaReleaseTime(int i) throws RemoteException;

    int getHalVersion(int i) throws RemoteException;

    String getImeiForSlot(int i, String str, String str2) throws RemoteException;

    IImsConfig getImsConfig(int i, int i2) throws RemoteException;

    boolean getImsFeatureValidationOverride(int i) throws RemoteException;

    void getImsMmTelFeatureState(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void getImsMmTelRegistrationState(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void getImsMmTelRegistrationTransportType(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    int getImsProvisioningInt(int i, int i2) throws RemoteException;

    boolean getImsProvisioningStatusForCapability(int i, int i2, int i3) throws RemoteException;

    String getImsProvisioningString(int i, int i2) throws RemoteException;

    int getImsRegTechnologyForMmTel(int i) throws RemoteException;

    IImsRegistration getImsRegistration(int i, int i2) throws RemoteException;

    CellIdentity getLastKnownCellIdentity(int i, String str, String str2) throws RemoteException;

    String getLastUcePidfXmlShell(int i) throws RemoteException;

    RcsContactUceCapability getLatestRcsContactUceCapabilityShell(int i) throws RemoteException;

    String getLine1AlphaTagForDisplay(int i, String str, String str2) throws RemoteException;

    String getLine1NumberForDisplay(int i, String str, String str2) throws RemoteException;

    int getLteOnCdmaMode(String str, String str2) throws RemoteException;

    int getLteOnCdmaModeForSubscriber(int i, String str, String str2) throws RemoteException;

    String getManualNetworkSelectionPlmn(int i) throws RemoteException;

    String getManufacturerCodeForSlot(int i) throws RemoteException;

    String getMeidForSlot(int i, String str, String str2) throws RemoteException;

    String[] getMergedImsisFromGroup(int i, String str) throws RemoteException;

    String[] getMergedSubscriberIds(int i, String str, String str2) throws RemoteException;

    String getMmsUAProfUrl(int i) throws RemoteException;

    String getMmsUserAgent(int i) throws RemoteException;

    String getMobileProvisioningUrl() throws RemoteException;

    String getModemService() throws RemoteException;

    List<NeighboringCellInfo> getNeighboringCellInfo(String str, String str2) throws RemoteException;

    String getNetworkCountryIsoForPhone(int i) throws RemoteException;

    int getNetworkSelectionMode(int i) throws RemoteException;

    int getNetworkTypeForSubscriber(int i, String str, String str2) throws RemoteException;

    int getNumberOfModemsWithSimultaneousDataConnections(int i, String str, String str2) throws RemoteException;

    List<String> getPackagesWithCarrierPrivileges(int i) throws RemoteException;

    List<String> getPackagesWithCarrierPrivilegesForAllPhones() throws RemoteException;

    PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int i) throws RemoteException;

    PhoneCapability getPhoneCapability() throws RemoteException;

    String getPrimaryImei(String str, String str2) throws RemoteException;

    int getRadioAccessFamily(int i, String str) throws RemoteException;

    int getRadioHalVersion() throws RemoteException;

    List getRadioPowerOffReasons(int i, String str, String str2) throws RemoteException;

    int getRadioPowerState(int i, String str, String str2) throws RemoteException;

    boolean getRcsProvisioningStatusForCapability(int i, int i2, int i3) throws RemoteException;

    boolean getRcsSingleRegistrationTestModeEnabled() throws RemoteException;

    List<String> getSatelliteDataOptimizedApps() throws RemoteException;

    int getSatelliteDataSupportMode(int i) throws RemoteException;

    int[] getSatelliteDisallowedReasons() throws RemoteException;

    List<String> getSatellitePlmnsForCarrier(int i) throws RemoteException;

    ServiceState getServiceStateForSlot(int i, boolean z, boolean z2, String str, String str2) throws RemoteException;

    List<String> getShaIdFromAllowList(String str, int i) throws RemoteException;

    SignalStrength getSignalStrength(int i) throws RemoteException;

    String getSimLocaleForSubscriber(int i) throws RemoteException;

    int getSimStateForSlotIndex(int i) throws RemoteException;

    void getSlicingConfig(ResultReceiver resultReceiver) throws RemoteException;

    List<UiccSlotMapping> getSlotsMapping(String str) throws RemoteException;

    int getSubIdForPhoneAccountHandle(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException;

    int getSubscriptionCarrierId(int i) throws RemoteException;

    String getSubscriptionCarrierName(int i) throws RemoteException;

    int getSubscriptionSpecificCarrierId(int i) throws RemoteException;

    String getSubscriptionSpecificCarrierName(int i) throws RemoteException;

    List<RadioAccessSpecifier> getSystemSelectionChannels(int i) throws RemoteException;

    List<TelephonyHistogram> getTelephonyHistograms() throws RemoteException;

    ComponentName getTestEuiccUiComponent() throws RemoteException;

    String getTypeAllocationCodeForSlot(int i) throws RemoteException;

    List<UiccCardInfo> getUiccCardsInfo(String str) throws RemoteException;

    UiccSlotInfo[] getUiccSlotsInfo(String str) throws RemoteException;

    String getVisualVoicemailPackageName(String str, String str2, int i) throws RemoteException;

    Bundle getVisualVoicemailSettings(String str, int i) throws RemoteException;

    VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(String str, int i) throws RemoteException;

    int getVoWiFiModeSetting(int i) throws RemoteException;

    int getVoWiFiRoamingModeSetting(int i) throws RemoteException;

    int getVoiceActivationState(int i, String str) throws RemoteException;

    int getVoiceMessageCountForSubscriber(int i, String str, String str2) throws RemoteException;

    int getVoiceNetworkTypeForSubscriber(int i, String str, String str2) throws RemoteException;

    Uri getVoicemailRingtoneUri(PhoneAccountHandle phoneAccountHandle) throws RemoteException;

    boolean handlePinMmi(String str) throws RemoteException;

    boolean handlePinMmiForSubscriber(int i, String str) throws RemoteException;

    void handleUssdRequest(int i, String str, ResultReceiver resultReceiver) throws RemoteException;

    boolean hasIccCard() throws RemoteException;

    boolean hasIccCardUsingSlotIndex(int i) throws RemoteException;

    boolean iccCloseLogicalChannel(IccLogicalChannelRequest iccLogicalChannelRequest) throws RemoteException;

    byte[] iccExchangeSimIO(int i, int i2, int i3, int i4, int i5, int i6, String str) throws RemoteException;

    IccOpenLogicalChannelResponse iccOpenLogicalChannel(IccLogicalChannelRequest iccLogicalChannelRequest) throws RemoteException;

    String iccTransmitApduBasicChannel(int i, String str, int i2, int i3, int i4, int i5, int i6, String str2) throws RemoteException;

    String iccTransmitApduBasicChannelByPort(int i, int i2, String str, int i3, int i4, int i5, int i6, int i7, String str2) throws RemoteException;

    String iccTransmitApduLogicalChannel(int i, int i2, int i3, int i4, int i5, int i6, int i7, String str) throws RemoteException;

    String iccTransmitApduLogicalChannelByPort(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, String str) throws RemoteException;

    boolean isAdvancedCallingSettingEnabled(int i) throws RemoteException;

    boolean isAospDomainSelectionService() throws RemoteException;

    boolean isApnMetered(int i, int i2) throws RemoteException;

    boolean isApplicationOnUicc(int i, int i2) throws RemoteException;

    boolean isAvailable(int i, int i2, int i3) throws RemoteException;

    boolean isCapable(int i, int i2, int i3) throws RemoteException;

    boolean isCellularIdentifierDisclosureNotificationsEnabled() throws RemoteException;

    boolean isConcurrentVoiceAndDataAllowed(int i) throws RemoteException;

    boolean isCrossSimCallingEnabledByUser(int i) throws RemoteException;

    boolean isDataConnectivityPossible(int i) throws RemoteException;

    boolean isDataEnabled(int i) throws RemoteException;

    boolean isDataEnabledForApn(int i, int i2, String str) throws RemoteException;

    boolean isDataEnabledForReason(int i, int i2) throws RemoteException;

    boolean isDataRoamingEnabled(int i) throws RemoteException;

    boolean isDomainSelectionSupported() throws RemoteException;

    boolean isEmergencyNumber(String str, boolean z) throws RemoteException;

    boolean isHearingAidCompatibilitySupported() throws RemoteException;

    boolean isIccLockEnabled(int i) throws RemoteException;

    boolean isImsRegistered(int i) throws RemoteException;

    boolean isInEmergencySmsMode() throws RemoteException;

    boolean isManualNetworkSelectionAllowed(int i) throws RemoteException;

    void isMmTelCapabilitySupported(int i, IIntegerConsumer iIntegerConsumer, int i2, int i3) throws RemoteException;

    boolean isMobileDataPolicyEnabled(int i, int i2) throws RemoteException;

    boolean isModemEnabledForSlot(int i, String str, String str2) throws RemoteException;

    int isMultiSimSupported(String str, String str2) throws RemoteException;

    boolean isMvnoMatched(int i, int i2, String str) throws RemoteException;

    boolean isNrDualConnectivityEnabled(int i) throws RemoteException;

    boolean isNullCipherAndIntegrityPreferenceEnabled() throws RemoteException;

    boolean isNullCipherNotificationsEnabled() throws RemoteException;

    boolean isPremiumCapabilityAvailableForPurchase(int i, int i2) throws RemoteException;

    boolean isProvisioningRequiredForCapability(int i, int i2, int i3) throws RemoteException;

    boolean isRadioInterfaceCapabilitySupported(String str) throws RemoteException;

    @Deprecated
    boolean isRadioOn(String str) throws RemoteException;

    @Deprecated
    boolean isRadioOnForSubscriber(int i, String str) throws RemoteException;

    boolean isRadioOnForSubscriberWithFeature(int i, String str, String str2) throws RemoteException;

    boolean isRadioOnWithFeature(String str, String str2) throws RemoteException;

    boolean isRcsProvisioningRequiredForCapability(int i, int i2, int i3) throws RemoteException;

    boolean isRcsVolteSingleRegistrationCapable(int i) throws RemoteException;

    boolean isRemovableEsimDefaultEuicc(String str) throws RemoteException;

    boolean isRttSupported(int i) throws RemoteException;

    boolean isTetheringApnRequiredForSubscriber(int i) throws RemoteException;

    boolean isTtyModeSupported() throws RemoteException;

    boolean isTtyOverVolteEnabled(int i) throws RemoteException;

    boolean isUserDataEnabled(int i) throws RemoteException;

    boolean isVideoCallingEnabled(String str, String str2) throws RemoteException;

    boolean isVideoTelephonyAvailable(int i) throws RemoteException;

    boolean isVoNrEnabled(int i) throws RemoteException;

    boolean isVoWiFiRoamingSettingEnabled(int i) throws RemoteException;

    boolean isVoWiFiSettingEnabled(int i) throws RemoteException;

    boolean isVoicemailVibrationEnabled(PhoneAccountHandle phoneAccountHandle) throws RemoteException;

    boolean isVtSettingEnabled(int i) throws RemoteException;

    boolean isWifiCallingAvailable(int i) throws RemoteException;

    boolean isWorldPhone(int i, String str, String str2) throws RemoteException;

    boolean needMobileRadioShutdown() throws RemoteException;

    boolean needsOtaServiceProvisioning() throws RemoteException;

    void notifyOtaEmergencyNumberDbInstalled() throws RemoteException;

    void notifyRcsAutoConfigurationReceived(int i, byte[] bArr, boolean z) throws RemoteException;

    @Deprecated
    String nvReadItem(int i) throws RemoteException;

    @Deprecated
    boolean nvWriteCdmaPrl(byte[] bArr) throws RemoteException;

    @Deprecated
    boolean nvWriteItem(int i, String str) throws RemoteException;

    boolean overrideCarrierRoamingNtnEligibilityChanged(boolean z, boolean z2) throws RemoteException;

    boolean overrideConfigDataVersion(boolean z, int i) throws RemoteException;

    void persistEmergencyCallDiagnosticData(String str, boolean z, long j, boolean z2, boolean z3) throws RemoteException;

    void pollPendingDatagrams(IIntegerConsumer iIntegerConsumer) throws RemoteException;

    int prepareForUnattendedReboot() throws RemoteException;

    void provisionSatellite(List<SatelliteSubscriberInfo> list, ResultReceiver resultReceiver) throws RemoteException;

    ICancellationSignal provisionSatelliteService(String str, byte[] bArr, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void purchasePremiumCapability(int i, IIntegerConsumer iIntegerConsumer, int i2) throws RemoteException;

    boolean rebootModem(int i) throws RemoteException;

    void refreshUiccProfile(int i) throws RemoteException;

    void registerFeatureProvisioningChangedCallback(int i, IFeatureProvisioningCallback iFeatureProvisioningCallback) throws RemoteException;

    int registerForCapabilitiesChanged(ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallback) throws RemoteException;

    int registerForCommunicationAccessStateChanged(int i, ISatelliteCommunicationAccessStateCallback iSatelliteCommunicationAccessStateCallback) throws RemoteException;

    int registerForIncomingDatagram(ISatelliteDatagramCallback iSatelliteDatagramCallback) throws RemoteException;

    void registerForNtnSignalStrengthChanged(INtnSignalStrengthCallback iNtnSignalStrengthCallback) throws RemoteException;

    void registerForSatelliteDisallowedReasonsChanged(ISatelliteDisallowedReasonsCallback iSatelliteDisallowedReasonsCallback) throws RemoteException;

    int registerForSatelliteModemStateChanged(ISatelliteModemStateCallback iSatelliteModemStateCallback) throws RemoteException;

    int registerForSatelliteProvisionStateChanged(ISatelliteProvisionStateCallback iSatelliteProvisionStateCallback) throws RemoteException;

    int registerForSatelliteSupportedStateChanged(IBooleanConsumer iBooleanConsumer) throws RemoteException;

    int registerForSelectedNbIotSatelliteSubscriptionChanged(ISelectedNbIotSatelliteSubscriptionCallback iSelectedNbIotSatelliteSubscriptionCallback) throws RemoteException;

    void registerImsEmergencyRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException;

    void registerImsProvisioningChangedCallback(int i, IImsConfigCallback iImsConfigCallback) throws RemoteException;

    void registerImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException;

    void registerImsStateCallback(int i, int i2, IImsStateCallback iImsStateCallback, String str) throws RemoteException;

    void registerMmTelCapabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    void registerMmTelFeatureCallback(int i, IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException;

    void registerRcsProvisioningCallback(int i, IRcsConfigCallback iRcsConfigCallback) throws RemoteException;

    void removeAttachRestrictionForCarrier(int i, int i2, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    int removeContactFromEab(int i, String str) throws RemoteException;

    RcsContactUceCapability removeUceRegistrationOverrideShell(int i, List<String> list) throws RemoteException;

    boolean removeUceRequestDisallowedStatus(int i) throws RemoteException;

    void requestCellInfoUpdate(int i, ICellInfoCallback iCellInfoCallback, String str, String str2) throws RemoteException;

    void requestCellInfoUpdateWithWorkSource(int i, ICellInfoCallback iCellInfoCallback, String str, String str2, WorkSource workSource) throws RemoteException;

    void requestIsCommunicationAllowedForCurrentLocation(int i, ResultReceiver resultReceiver) throws RemoteException;

    void requestIsDemoModeEnabled(ResultReceiver resultReceiver) throws RemoteException;

    void requestIsEmergencyModeEnabled(ResultReceiver resultReceiver) throws RemoteException;

    void requestIsSatelliteEnabled(ResultReceiver resultReceiver) throws RemoteException;

    void requestIsSatelliteProvisioned(ResultReceiver resultReceiver) throws RemoteException;

    void requestIsSatelliteSupported(ResultReceiver resultReceiver) throws RemoteException;

    void requestModemActivityInfo(ResultReceiver resultReceiver) throws RemoteException;

    int requestNetworkScan(int i, boolean z, NetworkScanRequest networkScanRequest, Messenger messenger, IBinder iBinder, String str, String str2) throws RemoteException;

    void requestNtnSignalStrength(ResultReceiver resultReceiver) throws RemoteException;

    void requestNumberVerification(PhoneNumberRange phoneNumberRange, long j, INumberVerificationCallback iNumberVerificationCallback, String str) throws RemoteException;

    boolean requestRadioPowerOffForReason(int i, int i2) throws RemoteException;

    void requestSatelliteAccessConfigurationForCurrentLocation(ResultReceiver resultReceiver) throws RemoteException;

    void requestSatelliteCapabilities(ResultReceiver resultReceiver) throws RemoteException;

    void requestSatelliteDisplayName(ResultReceiver resultReceiver) throws RemoteException;

    void requestSatelliteEnabled(boolean z, boolean z2, boolean z3, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void requestSatelliteSessionStats(int i, ResultReceiver resultReceiver) throws RemoteException;

    void requestSatelliteSubscriberProvisionStatus(ResultReceiver resultReceiver) throws RemoteException;

    void requestSelectedNbIotSatelliteSubscriptionId(ResultReceiver resultReceiver) throws RemoteException;

    void requestTimeForNextSatelliteVisibility(ResultReceiver resultReceiver) throws RemoteException;

    void requestUserActivityNotification() throws RemoteException;

    void resetIms(int i) throws RemoteException;

    @Deprecated
    boolean resetModemConfig(int i) throws RemoteException;

    void resetOtaEmergencyNumberDbFilePath() throws RemoteException;

    void sendDatagram(int i, SatelliteDatagram satelliteDatagram, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void sendDeviceToDeviceMessage(int i, int i2) throws RemoteException;

    void sendDialerSpecialCode(String str, String str2) throws RemoteException;

    String sendEnvelopeWithStatus(int i, String str) throws RemoteException;

    int sendThermalMitigationRequest(int i, ThermalMitigationRequest thermalMitigationRequest, String str) throws RemoteException;

    void sendVisualVoicemailSmsForSubscriber(String str, String str2, int i, String str3, int i2, String str4, PendingIntent pendingIntent) throws RemoteException;

    void setActiveDeviceToDeviceTransport(String str) throws RemoteException;

    void setAdvancedCallingSettingEnabled(int i, boolean z) throws RemoteException;

    int setAllowedCarriers(CarrierRestrictionRules carrierRestrictionRules) throws RemoteException;

    boolean setAllowedNetworkTypesForReason(int i, int i2, long j) throws RemoteException;

    boolean setBoundGbaServiceOverride(int i, String str) throws RemoteException;

    boolean setBoundImsServiceOverride(int i, int i2, boolean z, int[] iArr, String str) throws RemoteException;

    void setCallComposerStatus(int i, int i2) throws RemoteException;

    void setCallForwarding(int i, CallForwardingInfo callForwardingInfo, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void setCallWaitingStatus(int i, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    boolean setCapabilitiesRequestTimeout(int i, long j) throws RemoteException;

    void setCarrierServicePackageOverride(int i, String str, String str2) throws RemoteException;

    boolean setCarrierSingleRegistrationEnabledOverride(int i, String str) throws RemoteException;

    void setCarrierTestOverride(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) throws RemoteException;

    @Deprecated
    boolean setCdmaRoamingMode(int i, int i2) throws RemoteException;

    @Deprecated
    boolean setCdmaSubscriptionMode(int i, int i2) throws RemoteException;

    void setCellBroadcastIdRanges(int i, List<CellBroadcastIdRange> list, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void setCellInfoListRate(int i, int i2) throws RemoteException;

    void setCepEnabled(boolean z) throws RemoteException;

    boolean setCountryCodes(boolean z, List<String> list, Map map, String str, long j) throws RemoteException;

    void setCrossSimCallingEnabled(int i, boolean z) throws RemoteException;

    void setDataActivationState(int i, int i2) throws RemoteException;

    void setDataEnabledForReason(int i, int i2, boolean z, String str) throws RemoteException;

    void setDataRoamingEnabled(int i, boolean z) throws RemoteException;

    boolean setDatagramControllerBooleanConfig(boolean z, int i, boolean z2) throws RemoteException;

    boolean setDatagramControllerTimeoutDuration(boolean z, int i, long j) throws RemoteException;

    void setDeviceAlignedWithSatellite(boolean z) throws RemoteException;

    void setDeviceSingleRegistrationEnabledOverride(String str) throws RemoteException;

    void setDeviceToDeviceForceEnabled(boolean z) throws RemoteException;

    void setDeviceUceEnabled(boolean z) throws RemoteException;

    boolean setDomainSelectionServiceOverride(ComponentName componentName) throws RemoteException;

    boolean setEmergencyCallToSatelliteHandoverType(int i, int i2) throws RemoteException;

    void setEnableCellularIdentifierDisclosureNotifications(boolean z) throws RemoteException;

    int setForbiddenPlmns(int i, int i2, List<String> list, String str, String str2) throws RemoteException;

    boolean setGbaReleaseTimeOverride(int i, int i2) throws RemoteException;

    int setIccLockEnabled(int i, boolean z, String str) throws RemoteException;

    boolean setImsFeatureValidationOverride(int i, String str) throws RemoteException;

    int setImsProvisioningInt(int i, int i2, int i3) throws RemoteException;

    void setImsProvisioningStatusForCapability(int i, int i2, int i3, boolean z) throws RemoteException;

    int setImsProvisioningString(int i, int i2, String str) throws RemoteException;

    void setImsRegistrationState(boolean z) throws RemoteException;

    boolean setIsSatelliteCommunicationAllowedForCurrentLocationCache(String str) throws RemoteException;

    boolean setLine1NumberForDisplayForSubscriber(int i, String str, String str2) throws RemoteException;

    void setMobileDataPolicyEnabled(int i, int i2, boolean z) throws RemoteException;

    boolean setModemService(String str) throws RemoteException;

    void setMultiSimCarrierRestriction(boolean z) throws RemoteException;

    void setNetworkSelectionModeAutomatic(int i) throws RemoteException;

    boolean setNetworkSelectionModeManual(int i, OperatorInfo operatorInfo, boolean z) throws RemoteException;

    int setNrDualConnectivityState(int i, int i2) throws RemoteException;

    void setNtnSmsSupported(boolean z) throws RemoteException;

    void setNullCipherAndIntegrityEnabled(boolean z) throws RemoteException;

    void setNullCipherNotificationsEnabled(boolean z) throws RemoteException;

    boolean setOemEnabledSatelliteProvisionStatus(boolean z, boolean z2) throws RemoteException;

    boolean setOperatorBrandOverride(int i, String str) throws RemoteException;

    boolean setRadio(boolean z) throws RemoteException;

    boolean setRadioForSubscriber(int i, boolean z) throws RemoteException;

    boolean setRadioPower(boolean z) throws RemoteException;

    void setRcsClientConfiguration(int i, RcsClientConfiguration rcsClientConfiguration) throws RemoteException;

    void setRcsProvisioningStatusForCapability(int i, int i2, int i3, boolean z) throws RemoteException;

    void setRcsSingleRegistrationTestModeEnabled(boolean z) throws RemoteException;

    void setRemovableEsimAsDefaultEuicc(boolean z, String str) throws RemoteException;

    boolean setRoamingOverride(int i, List<String> list, List<String> list2, List<String> list3, List<String> list4) throws RemoteException;

    void setRttCapabilitySetting(int i, boolean z) throws RemoteException;

    boolean setSatelliteAccessAllowedForSubscriptions(String str) throws RemoteException;

    boolean setSatelliteAccessControlOverlayConfigs(boolean z, boolean z2, String str, long j, List<String> list, String str2) throws RemoteException;

    boolean setSatelliteControllerTimeoutDuration(boolean z, int i, long j) throws RemoteException;

    boolean setSatelliteGatewayServicePackageName(String str) throws RemoteException;

    boolean setSatelliteIgnoreCellularServiceState(boolean z) throws RemoteException;

    boolean setSatelliteIgnorePlmnListFromStorage(boolean z) throws RemoteException;

    boolean setSatelliteListeningTimeoutDuration(long j) throws RemoteException;

    boolean setSatellitePointingUiClassName(String str, String str2) throws RemoteException;

    boolean setSatelliteServicePackageName(String str, String str2) throws RemoteException;

    boolean setSatelliteSubscriberIdListChangedIntentComponent(String str) throws RemoteException;

    boolean setShouldSendDatagramToModemInDemoMode(boolean z) throws RemoteException;

    void setSignalStrengthUpdateRequest(int i, SignalStrengthUpdateRequest signalStrengthUpdateRequest, String str) throws RemoteException;

    void setSimPowerStateForSlot(int i, int i2) throws RemoteException;

    void setSimPowerStateForSlotWithCallback(int i, int i2, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    boolean setSimSlotMapping(List<UiccSlotMapping> list) throws RemoteException;

    boolean setSupportDisableSatelliteWhileEnableInProgress(boolean z, boolean z2) throws RemoteException;

    void setSystemSelectionChannels(List<RadioAccessSpecifier> list, int i, IBooleanConsumer iBooleanConsumer) throws RemoteException;

    void setTestEuiccUiComponent(ComponentName componentName) throws RemoteException;

    boolean setTnScanningSupport(boolean z, boolean z2, boolean z3) throws RemoteException;

    int setVoNrEnabled(int i, boolean z) throws RemoteException;

    void setVoWiFiModeSetting(int i, int i2) throws RemoteException;

    void setVoWiFiNonPersistent(int i, boolean z, int i2) throws RemoteException;

    void setVoWiFiRoamingModeSetting(int i, int i2) throws RemoteException;

    void setVoWiFiRoamingSettingEnabled(int i, boolean z) throws RemoteException;

    void setVoWiFiSettingEnabled(int i, boolean z) throws RemoteException;

    void setVoiceActivationState(int i, int i2) throws RemoteException;

    boolean setVoiceMailNumber(int i, String str, String str2) throws RemoteException;

    void setVoiceServiceStateOverride(int i, boolean z, String str) throws RemoteException;

    void setVoicemailRingtoneUri(String str, PhoneAccountHandle phoneAccountHandle, Uri uri) throws RemoteException;

    void setVoicemailVibrationEnabled(String str, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException;

    void setVtSettingEnabled(int i, boolean z) throws RemoteException;

    void showSwitchToManagedProfileDialog() throws RemoteException;

    void shutdownMobileRadios() throws RemoteException;

    void startEmergencyCallbackMode() throws RemoteException;

    void startSatelliteTransmissionUpdates(IIntegerConsumer iIntegerConsumer, ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallback) throws RemoteException;

    void stopNetworkScan(int i, int i2) throws RemoteException;

    void stopSatelliteTransmissionUpdates(IIntegerConsumer iIntegerConsumer, ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallback) throws RemoteException;

    boolean supplyPinForSubscriber(int i, String str) throws RemoteException;

    int[] supplyPinReportResultForSubscriber(int i, String str) throws RemoteException;

    boolean supplyPukForSubscriber(int i, String str, String str2) throws RemoteException;

    int[] supplyPukReportResultForSubscriber(int i, String str, String str2) throws RemoteException;

    void switchMultiSimConfig(int i) throws RemoteException;

    @Deprecated
    boolean switchSlots(int[] iArr) throws RemoteException;

    void toggleRadioOnOff() throws RemoteException;

    void toggleRadioOnOffForSubscriber(int i) throws RemoteException;

    void triggerRcsReconfiguration(int i) throws RemoteException;

    void unregisterFeatureProvisioningChangedCallback(int i, IFeatureProvisioningCallback iFeatureProvisioningCallback) throws RemoteException;

    void unregisterForCapabilitiesChanged(ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallback) throws RemoteException;

    void unregisterForCommunicationAccessStateChanged(int i, ISatelliteCommunicationAccessStateCallback iSatelliteCommunicationAccessStateCallback) throws RemoteException;

    void unregisterForIncomingDatagram(ISatelliteDatagramCallback iSatelliteDatagramCallback) throws RemoteException;

    void unregisterForModemStateChanged(ISatelliteModemStateCallback iSatelliteModemStateCallback) throws RemoteException;

    void unregisterForNtnSignalStrengthChanged(INtnSignalStrengthCallback iNtnSignalStrengthCallback) throws RemoteException;

    void unregisterForSatelliteDisallowedReasonsChanged(ISatelliteDisallowedReasonsCallback iSatelliteDisallowedReasonsCallback) throws RemoteException;

    void unregisterForSatelliteProvisionStateChanged(ISatelliteProvisionStateCallback iSatelliteProvisionStateCallback) throws RemoteException;

    void unregisterForSatelliteSupportedStateChanged(IBooleanConsumer iBooleanConsumer) throws RemoteException;

    void unregisterForSelectedNbIotSatelliteSubscriptionChanged(ISelectedNbIotSatelliteSubscriptionCallback iSelectedNbIotSatelliteSubscriptionCallback) throws RemoteException;

    void unregisterImsEmergencyRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException;

    void unregisterImsFeatureCallback(IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException;

    void unregisterImsProvisioningChangedCallback(int i, IImsConfigCallback iImsConfigCallback) throws RemoteException;

    void unregisterImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException;

    void unregisterImsStateCallback(IImsStateCallback iImsStateCallback) throws RemoteException;

    void unregisterMmTelCapabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    void unregisterRcsProvisioningCallback(int i, IRcsConfigCallback iRcsConfigCallback) throws RemoteException;

    void updateEmergencyNumberListTestMode(int i, EmergencyNumber emergencyNumber) throws RemoteException;

    void updateOtaEmergencyNumberDbFilePath(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void updateServiceLocation() throws RemoteException;

    void updateServiceLocationWithPackageName(String str) throws RemoteException;

    void uploadCallComposerPicture(int i, String str, String str2, ParcelFileDescriptor parcelFileDescriptor, ResultReceiver resultReceiver) throws RemoteException;

    void userActivity() throws RemoteException;

    public static abstract class Stub extends Binder implements ITelephony {
        public static final String DESCRIPTOR = "com.android.internal.telephony.ITelephony";
        static final int TRANSACTION_addAttachRestrictionForCarrier = 404;
        static final int TRANSACTION_addUceRegistrationOverrideShell = 325;
        static final int TRANSACTION_bootstrapAuthenticationRequest = 298;
        static final int TRANSACTION_call = 2;
        static final int TRANSACTION_canChangeDtmfToneLength = 137;
        static final int TRANSACTION_canConnectTo5GInDsdsMode = 290;
        static final int TRANSACTION_carrierActionReportDefaultNetworkStatus = 178;
        static final int TRANSACTION_carrierActionResetAll = 179;
        static final int TRANSACTION_carrierActionSetRadioEnabled = 177;
        static final int TRANSACTION_changeIccLockPassword = 286;
        static final int TRANSACTION_checkCarrierPrivilegesForPackage = 121;
        static final int TRANSACTION_checkCarrierPrivilegesForPackageAnyPhone = 122;
        static final int TRANSACTION_clearCarrierImsServiceOverride = 99;
        static final int TRANSACTION_clearDomainSelectionServiceOverride = 414;
        static final int TRANSACTION_clearRadioPowerOffForReason = 22;
        static final int TRANSACTION_clearSignalStrengthUpdateRequest = 333;
        static final int TRANSACTION_clearUceRegistrationOverrideShell = 327;
        static final int TRANSACTION_deprovisionSatellite = 435;
        static final int TRANSACTION_deprovisionSatelliteService = 368;
        static final int TRANSACTION_dial = 1;
        static final int TRANSACTION_disableDataConnectivity = 29;
        static final int TRANSACTION_disableIms = 92;
        static final int TRANSACTION_disableLocationUpdates = 27;
        static final int TRANSACTION_disableVisualVoicemailSmsFilter = 59;
        static final int TRANSACTION_doesSwitchMultiSimConfigTriggerReboot = 264;
        static final int TRANSACTION_enableDataConnectivity = 28;
        static final int TRANSACTION_enableIms = 91;
        static final int TRANSACTION_enableLocationUpdates = 26;
        static final int TRANSACTION_enableModemForSlot = 260;
        static final int TRANSACTION_enableVideoCalling = 135;
        static final int TRANSACTION_enableVisualVoicemailSmsFilter = 58;
        static final int TRANSACTION_enqueueSmsPickResult = 276;
        static final int TRANSACTION_factoryReset = 156;
        static final int TRANSACTION_getActivePhoneType = 40;
        static final int TRANSACTION_getActivePhoneTypeForSlot = 41;
        static final int TRANSACTION_getActiveVisualVoicemailSmsFilterSettings = 61;
        static final int TRANSACTION_getAidForAppType = 166;
        static final int TRANSACTION_getAllCellInfo = 72;
        static final int TRANSACTION_getAllowedCarriers = 171;
        static final int TRANSACTION_getAllowedNetworkTypesBitmask = 89;
        static final int TRANSACTION_getAllowedNetworkTypesForReason = 107;
        static final int TRANSACTION_getAttachRestrictionReasonsForCarrier = 406;
        static final int TRANSACTION_getBoundGbaService = 300;
        static final int TRANSACTION_getBoundImsServicePackage = 100;
        static final int TRANSACTION_getCallComposerStatus = 8;
        static final int TRANSACTION_getCallForwarding = 180;
        static final int TRANSACTION_getCallState = 34;
        static final int TRANSACTION_getCallStateForSubscription = 35;
        static final int TRANSACTION_getCallWaitingStatus = 182;
        static final int TRANSACTION_getCapabilityFromEab = 322;
        static final int TRANSACTION_getCardIdForDefaultEuicc = 191;
        static final int TRANSACTION_getCarrierIdFromIdentifier = 437;
        static final int TRANSACTION_getCarrierIdFromMccMnc = 176;
        static final int TRANSACTION_getCarrierIdListVersion = 204;
        static final int TRANSACTION_getCarrierPackageNamesForIntentAndPhone = 123;
        static final int TRANSACTION_getCarrierPrivilegeStatus = 119;
        static final int TRANSACTION_getCarrierPrivilegeStatusForUid = 120;
        static final int TRANSACTION_getCarrierRestrictionStatus = 358;
        static final int TRANSACTION_getCarrierServicePackageNameForLogicalSlot = 347;
        static final int TRANSACTION_getCarrierSingleRegistrationEnabled = 316;
        static final int TRANSACTION_getCdmaEriIconIndex = 42;
        static final int TRANSACTION_getCdmaEriIconIndexForSubscriber = 43;
        static final int TRANSACTION_getCdmaEriIconMode = 44;
        static final int TRANSACTION_getCdmaEriIconModeForSubscriber = 45;
        static final int TRANSACTION_getCdmaEriText = 46;
        static final int TRANSACTION_getCdmaEriTextForSubscriber = 47;
        static final int TRANSACTION_getCdmaMdn = 116;
        static final int TRANSACTION_getCdmaMin = 117;
        static final int TRANSACTION_getCdmaPrlVersion = 168;
        static final int TRANSACTION_getCdmaRoamingMode = 198;
        static final int TRANSACTION_getCdmaSubscriptionMode = 200;
        static final int TRANSACTION_getCellBroadcastIdRanges = 355;
        static final int TRANSACTION_getCellLocation = 31;
        static final int TRANSACTION_getCellNetworkScanResults = 103;
        static final int TRANSACTION_getCertsFromCarrierPrivilegeAccessRules = 240;
        static final int TRANSACTION_getClientRequestStats = 184;
        static final int TRANSACTION_getContactFromEab = 321;
        static final int TRANSACTION_getCurrentPackageName = 268;
        static final int TRANSACTION_getDataActivationState = 53;
        static final int TRANSACTION_getDataActivity = 36;
        static final int TRANSACTION_getDataActivityForSubId = 37;
        static final int TRANSACTION_getDataEnabled = 109;
        static final int TRANSACTION_getDataNetworkType = 65;
        static final int TRANSACTION_getDataNetworkTypeForSubscriber = 66;
        static final int TRANSACTION_getDataState = 38;
        static final int TRANSACTION_getDataStateForSubId = 39;
        static final int TRANSACTION_getDefaultRespondViaMessageApplication = 350;
        static final int TRANSACTION_getDeviceId = 146;
        static final int TRANSACTION_getDeviceIdWithFeature = 147;
        static final int TRANSACTION_getDeviceSingleRegistrationEnabled = 311;
        static final int TRANSACTION_getDeviceSoftwareVersionForSlot = 153;
        static final int TRANSACTION_getDeviceUceEnabled = 323;
        static final int TRANSACTION_getEmergencyCallbackMode = 189;
        static final int TRANSACTION_getEmergencyNumberDbVersion = 256;
        static final int TRANSACTION_getEmergencyNumberList = 238;
        static final int TRANSACTION_getEmergencyNumberListTestMode = 255;
        static final int TRANSACTION_getEquivalentHomePlmns = 291;
        static final int TRANSACTION_getEsn = 167;
        static final int TRANSACTION_getForbiddenPlmns = 187;
        static final int TRANSACTION_getGbaReleaseTime = 302;
        static final int TRANSACTION_getHalVersion = 267;
        static final int TRANSACTION_getImeiForSlot = 148;
        static final int TRANSACTION_getImsConfig = 97;
        static final int TRANSACTION_getImsFeatureValidationOverride = 318;
        static final int TRANSACTION_getImsMmTelFeatureState = 101;
        static final int TRANSACTION_getImsMmTelRegistrationState = 214;
        static final int TRANSACTION_getImsMmTelRegistrationTransportType = 215;
        static final int TRANSACTION_getImsProvisioningInt = 249;
        static final int TRANSACTION_getImsProvisioningStatusForCapability = 246;
        static final int TRANSACTION_getImsProvisioningString = 250;
        static final int TRANSACTION_getImsRegTechnologyForMmTel = 145;
        static final int TRANSACTION_getImsRegistration = 96;
        static final int TRANSACTION_getLastKnownCellIdentity = 341;
        static final int TRANSACTION_getLastUcePidfXmlShell = 329;
        static final int TRANSACTION_getLatestRcsContactUceCapabilityShell = 328;
        static final int TRANSACTION_getLine1AlphaTagForDisplay = 126;
        static final int TRANSACTION_getLine1NumberForDisplay = 125;
        static final int TRANSACTION_getLteOnCdmaMode = 70;
        static final int TRANSACTION_getLteOnCdmaModeForSubscriber = 71;
        static final int TRANSACTION_getManualNetworkSelectionPlmn = 289;
        static final int TRANSACTION_getManufacturerCodeForSlot = 152;
        static final int TRANSACTION_getMeidForSlot = 151;
        static final int TRANSACTION_getMergedImsisFromGroup = 128;
        static final int TRANSACTION_getMergedSubscriberIds = 127;
        static final int TRANSACTION_getMmsUAProfUrl = 279;
        static final int TRANSACTION_getMmsUserAgent = 278;
        static final int TRANSACTION_getMobileProvisioningUrl = 319;
        static final int TRANSACTION_getModemService = 343;
        static final int TRANSACTION_getNeighboringCellInfo = 33;
        static final int TRANSACTION_getNetworkCountryIsoForPhone = 32;
        static final int TRANSACTION_getNetworkSelectionMode = 207;
        static final int TRANSACTION_getNetworkTypeForSubscriber = 64;
        static final int TRANSACTION_getNumberOfModemsWithSimultaneousDataConnections = 206;
        static final int TRANSACTION_getPackagesWithCarrierPrivileges = 164;
        static final int TRANSACTION_getPackagesWithCarrierPrivilegesForAllPhones = 165;
        static final int TRANSACTION_getPhoneAccountHandleForSubscriptionId = 155;
        static final int TRANSACTION_getPhoneCapability = 334;
        static final int TRANSACTION_getPrimaryImei = 149;
        static final int TRANSACTION_getRadioAccessFamily = 133;
        static final int TRANSACTION_getRadioHalVersion = 266;
        static final int TRANSACTION_getRadioPowerOffReasons = 23;
        static final int TRANSACTION_getRadioPowerState = 209;
        static final int TRANSACTION_getRcsProvisioningStatusForCapability = 247;
        static final int TRANSACTION_getRcsSingleRegistrationTestModeEnabled = 309;
        static final int TRANSACTION_getSatelliteDataOptimizedApps = 438;
        static final int TRANSACTION_getSatelliteDataSupportMode = 439;
        static final int TRANSACTION_getSatelliteDisallowedReasons = 378;
        static final int TRANSACTION_getSatellitePlmnsForCarrier = 420;
        static final int TRANSACTION_getServiceStateForSlot = 159;
        static final int TRANSACTION_getShaIdFromAllowList = 403;
        static final int TRANSACTION_getSignalStrength = 190;
        static final int TRANSACTION_getSimLocaleForSubscriber = 157;
        static final int TRANSACTION_getSimStateForSlotIndex = 351;
        static final int TRANSACTION_getSlicingConfig = 336;
        static final int TRANSACTION_getSlotsMapping = 265;
        static final int TRANSACTION_getSubIdForPhoneAccountHandle = 154;
        static final int TRANSACTION_getSubscriptionCarrierId = 172;
        static final int TRANSACTION_getSubscriptionCarrierName = 173;
        static final int TRANSACTION_getSubscriptionSpecificCarrierId = 174;
        static final int TRANSACTION_getSubscriptionSpecificCarrierName = 175;
        static final int TRANSACTION_getSystemSelectionChannels = 274;
        static final int TRANSACTION_getTelephonyHistograms = 169;
        static final int TRANSACTION_getTestEuiccUiComponent = 433;
        static final int TRANSACTION_getTypeAllocationCodeForSlot = 150;
        static final int TRANSACTION_getUiccCardsInfo = 192;
        static final int TRANSACTION_getUiccSlotsInfo = 193;
        static final int TRANSACTION_getVisualVoicemailPackageName = 57;
        static final int TRANSACTION_getVisualVoicemailSettings = 56;
        static final int TRANSACTION_getVisualVoicemailSmsFilterSettings = 60;
        static final int TRANSACTION_getVoWiFiModeSetting = 232;
        static final int TRANSACTION_getVoWiFiRoamingModeSetting = 234;
        static final int TRANSACTION_getVoiceActivationState = 52;
        static final int TRANSACTION_getVoiceMessageCountForSubscriber = 54;
        static final int TRANSACTION_getVoiceNetworkTypeForSubscriber = 67;
        static final int TRANSACTION_getVoicemailRingtoneUri = 160;
        static final int TRANSACTION_handlePinMmi = 13;
        static final int TRANSACTION_handlePinMmiForSubscriber = 15;
        static final int TRANSACTION_handleUssdRequest = 14;
        static final int TRANSACTION_hasIccCard = 68;
        static final int TRANSACTION_hasIccCardUsingSlotIndex = 69;
        static final int TRANSACTION_iccCloseLogicalChannel = 77;
        static final int TRANSACTION_iccExchangeSimIO = 82;
        static final int TRANSACTION_iccOpenLogicalChannel = 76;
        static final int TRANSACTION_iccTransmitApduBasicChannel = 81;
        static final int TRANSACTION_iccTransmitApduBasicChannelByPort = 80;
        static final int TRANSACTION_iccTransmitApduLogicalChannel = 79;
        static final int TRANSACTION_iccTransmitApduLogicalChannelByPort = 78;
        static final int TRANSACTION_isAdvancedCallingSettingEnabled = 221;
        static final int TRANSACTION_isAospDomainSelectionService = 415;
        static final int TRANSACTION_isApnMetered = 272;
        static final int TRANSACTION_isApplicationOnUicc = 269;
        static final int TRANSACTION_isAvailable = 219;
        static final int TRANSACTION_isCapable = 218;
        static final int TRANSACTION_isCellularIdentifierDisclosureNotificationsEnabled = 417;
        static final int TRANSACTION_isConcurrentVoiceAndDataAllowed = 55;
        static final int TRANSACTION_isCrossSimCallingEnabledByUser = 227;
        static final int TRANSACTION_isDataConnectivityPossible = 30;
        static final int TRANSACTION_isDataEnabled = 111;
        static final int TRANSACTION_isDataEnabledForApn = 271;
        static final int TRANSACTION_isDataEnabledForReason = 113;
        static final int TRANSACTION_isDataRoamingEnabled = 196;
        static final int TRANSACTION_isDomainSelectionSupported = 357;
        static final int TRANSACTION_isEmergencyNumber = 239;
        static final int TRANSACTION_isHearingAidCompatibilitySupported = 141;
        static final int TRANSACTION_isIccLockEnabled = 284;
        static final int TRANSACTION_isImsRegistered = 142;
        static final int TRANSACTION_isInEmergencySmsMode = 208;
        static final int TRANSACTION_isManualNetworkSelectionAllowed = 114;
        static final int TRANSACTION_isMmTelCapabilitySupported = 220;
        static final int TRANSACTION_isMobileDataPolicyEnabled = 281;
        static final int TRANSACTION_isModemEnabledForSlot = 270;
        static final int TRANSACTION_isMultiSimSupported = 262;
        static final int TRANSACTION_isMvnoMatched = 275;
        static final int TRANSACTION_isNrDualConnectivityEnabled = 295;
        static final int TRANSACTION_isNullCipherAndIntegrityPreferenceEnabled = 354;
        static final int TRANSACTION_isNullCipherNotificationsEnabled = 419;
        static final int TRANSACTION_isPremiumCapabilityAvailableForPurchase = 337;
        static final int TRANSACTION_isProvisioningRequiredForCapability = 344;
        static final int TRANSACTION_isRadioInterfaceCapabilitySupported = 296;
        static final int TRANSACTION_isRadioOn = 3;
        static final int TRANSACTION_isRadioOnForSubscriber = 5;
        static final int TRANSACTION_isRadioOnForSubscriberWithFeature = 6;
        static final int TRANSACTION_isRadioOnWithFeature = 4;
        static final int TRANSACTION_isRcsProvisioningRequiredForCapability = 345;
        static final int TRANSACTION_isRcsVolteSingleRegistrationCapable = 304;
        static final int TRANSACTION_isRemovableEsimDefaultEuicc = 349;
        static final int TRANSACTION_isRttSupported = 140;
        static final int TRANSACTION_isTetheringApnRequiredForSubscriber = 90;
        static final int TRANSACTION_isTtyModeSupported = 139;
        static final int TRANSACTION_isTtyOverVolteEnabled = 237;
        static final int TRANSACTION_isUserDataEnabled = 110;
        static final int TRANSACTION_isVideoCallingEnabled = 136;
        static final int TRANSACTION_isVideoTelephonyAvailable = 144;
        static final int TRANSACTION_isVoNrEnabled = 293;
        static final int TRANSACTION_isVoWiFiRoamingSettingEnabled = 229;
        static final int TRANSACTION_isVoWiFiSettingEnabled = 225;
        static final int TRANSACTION_isVoicemailVibrationEnabled = 162;
        static final int TRANSACTION_isVtSettingEnabled = 223;
        static final int TRANSACTION_isWifiCallingAvailable = 143;
        static final int TRANSACTION_isWorldPhone = 138;
        static final int TRANSACTION_needMobileRadioShutdown = 131;
        static final int TRANSACTION_needsOtaServiceProvisioning = 48;
        static final int TRANSACTION_notifyOtaEmergencyNumberDbInstalled = 257;
        static final int TRANSACTION_notifyRcsAutoConfigurationReceived = 283;
        static final int TRANSACTION_nvReadItem = 84;
        static final int TRANSACTION_nvWriteCdmaPrl = 86;
        static final int TRANSACTION_nvWriteItem = 85;
        static final int TRANSACTION_overrideCarrierRoamingNtnEligibilityChanged = 434;
        static final int TRANSACTION_overrideConfigDataVersion = 402;
        static final int TRANSACTION_persistEmergencyCallDiagnosticData = 352;
        static final int TRANSACTION_pollPendingDatagrams = 376;
        static final int TRANSACTION_prepareForUnattendedReboot = 335;
        static final int TRANSACTION_provisionSatellite = 430;
        static final int TRANSACTION_provisionSatelliteService = 367;
        static final int TRANSACTION_purchasePremiumCapability = 338;
        static final int TRANSACTION_rebootModem = 88;
        static final int TRANSACTION_refreshUiccProfile = 205;
        static final int TRANSACTION_registerFeatureProvisioningChangedCallback = 243;
        static final int TRANSACTION_registerForCapabilitiesChanged = 410;
        static final int TRANSACTION_registerForCommunicationAccessStateChanged = 423;
        static final int TRANSACTION_registerForIncomingDatagram = 374;
        static final int TRANSACTION_registerForNtnSignalStrengthChanged = 408;
        static final int TRANSACTION_registerForSatelliteDisallowedReasonsChanged = 379;
        static final int TRANSACTION_registerForSatelliteModemStateChanged = 372;
        static final int TRANSACTION_registerForSatelliteProvisionStateChanged = 369;
        static final int TRANSACTION_registerForSatelliteSupportedStateChanged = 421;
        static final int TRANSACTION_registerForSelectedNbIotSatelliteSubscriptionChanged = 385;
        static final int TRANSACTION_registerImsEmergencyRegistrationCallback = 212;
        static final int TRANSACTION_registerImsProvisioningChangedCallback = 241;
        static final int TRANSACTION_registerImsRegistrationCallback = 210;
        static final int TRANSACTION_registerImsStateCallback = 339;
        static final int TRANSACTION_registerMmTelCapabilityCallback = 216;
        static final int TRANSACTION_registerMmTelFeatureCallback = 94;
        static final int TRANSACTION_registerRcsProvisioningCallback = 305;
        static final int TRANSACTION_removeAttachRestrictionForCarrier = 405;
        static final int TRANSACTION_removeContactFromEab = 320;
        static final int TRANSACTION_removeUceRegistrationOverrideShell = 326;
        static final int TRANSACTION_removeUceRequestDisallowedStatus = 330;
        static final int TRANSACTION_requestCellInfoUpdate = 73;
        static final int TRANSACTION_requestCellInfoUpdateWithWorkSource = 74;
        static final int TRANSACTION_requestIsCommunicationAllowedForCurrentLocation = 381;
        static final int TRANSACTION_requestIsDemoModeEnabled = 361;
        static final int TRANSACTION_requestIsEmergencyModeEnabled = 362;
        static final int TRANSACTION_requestIsSatelliteEnabled = 360;
        static final int TRANSACTION_requestIsSatelliteProvisioned = 371;
        static final int TRANSACTION_requestIsSatelliteSupported = 363;
        static final int TRANSACTION_requestModemActivityInfo = 158;
        static final int TRANSACTION_requestNetworkScan = 104;
        static final int TRANSACTION_requestNtnSignalStrength = 407;
        static final int TRANSACTION_requestNumberVerification = 118;
        static final int TRANSACTION_requestRadioPowerOffForReason = 21;
        static final int TRANSACTION_requestSatelliteAccessConfigurationForCurrentLocation = 382;
        static final int TRANSACTION_requestSatelliteCapabilities = 364;
        static final int TRANSACTION_requestSatelliteDisplayName = 429;
        static final int TRANSACTION_requestSatelliteEnabled = 359;
        static final int TRANSACTION_requestSatelliteSessionStats = 427;
        static final int TRANSACTION_requestSatelliteSubscriberProvisionStatus = 428;
        static final int TRANSACTION_requestSelectedNbIotSatelliteSubscriptionId = 384;
        static final int TRANSACTION_requestTimeForNextSatelliteVisibility = 383;
        static final int TRANSACTION_requestUserActivityNotification = 287;
        static final int TRANSACTION_resetIms = 93;
        static final int TRANSACTION_resetModemConfig = 87;
        static final int TRANSACTION_resetOtaEmergencyNumberDbFilePath = 259;
        static final int TRANSACTION_sendDatagram = 377;
        static final int TRANSACTION_sendDeviceToDeviceMessage = 313;
        static final int TRANSACTION_sendDialerSpecialCode = 63;
        static final int TRANSACTION_sendEnvelopeWithStatus = 83;
        static final int TRANSACTION_sendThermalMitigationRequest = 297;
        static final int TRANSACTION_sendVisualVoicemailSmsForSubscriber = 62;
        static final int TRANSACTION_setActiveDeviceToDeviceTransport = 314;
        static final int TRANSACTION_setAdvancedCallingSettingEnabled = 222;
        static final int TRANSACTION_setAllowedCarriers = 170;
        static final int TRANSACTION_setAllowedNetworkTypesForReason = 108;
        static final int TRANSACTION_setBoundGbaServiceOverride = 299;
        static final int TRANSACTION_setBoundImsServiceOverride = 98;
        static final int TRANSACTION_setCallComposerStatus = 7;
        static final int TRANSACTION_setCallForwarding = 181;
        static final int TRANSACTION_setCallWaitingStatus = 183;
        static final int TRANSACTION_setCapabilitiesRequestTimeout = 331;
        static final int TRANSACTION_setCarrierServicePackageOverride = 203;
        static final int TRANSACTION_setCarrierSingleRegistrationEnabledOverride = 312;
        static final int TRANSACTION_setCarrierTestOverride = 202;
        static final int TRANSACTION_setCdmaRoamingMode = 199;
        static final int TRANSACTION_setCdmaSubscriptionMode = 201;
        static final int TRANSACTION_setCellBroadcastIdRanges = 356;
        static final int TRANSACTION_setCellInfoListRate = 75;
        static final int TRANSACTION_setCepEnabled = 282;
        static final int TRANSACTION_setCountryCodes = 397;
        static final int TRANSACTION_setCrossSimCallingEnabled = 228;
        static final int TRANSACTION_setDataActivationState = 51;
        static final int TRANSACTION_setDataEnabledForReason = 112;
        static final int TRANSACTION_setDataRoamingEnabled = 197;
        static final int TRANSACTION_setDatagramControllerBooleanConfig = 425;
        static final int TRANSACTION_setDatagramControllerTimeoutDuration = 394;
        static final int TRANSACTION_setDeviceAlignedWithSatellite = 387;
        static final int TRANSACTION_setDeviceSingleRegistrationEnabledOverride = 310;
        static final int TRANSACTION_setDeviceToDeviceForceEnabled = 315;
        static final int TRANSACTION_setDeviceUceEnabled = 324;
        static final int TRANSACTION_setDomainSelectionServiceOverride = 413;
        static final int TRANSACTION_setEmergencyCallToSatelliteHandoverType = 396;
        static final int TRANSACTION_setEnableCellularIdentifierDisclosureNotifications = 416;
        static final int TRANSACTION_setForbiddenPlmns = 188;
        static final int TRANSACTION_setGbaReleaseTimeOverride = 301;
        static final int TRANSACTION_setIccLockEnabled = 285;
        static final int TRANSACTION_setImsFeatureValidationOverride = 317;
        static final int TRANSACTION_setImsProvisioningInt = 251;
        static final int TRANSACTION_setImsProvisioningStatusForCapability = 245;
        static final int TRANSACTION_setImsProvisioningString = 252;
        static final int TRANSACTION_setImsRegistrationState = 115;
        static final int TRANSACTION_setIsSatelliteCommunicationAllowedForCurrentLocationCache = 426;
        static final int TRANSACTION_setLine1NumberForDisplayForSubscriber = 124;
        static final int TRANSACTION_setMobileDataPolicyEnabled = 280;
        static final int TRANSACTION_setModemService = 342;
        static final int TRANSACTION_setMultiSimCarrierRestriction = 261;
        static final int TRANSACTION_setNetworkSelectionModeAutomatic = 102;
        static final int TRANSACTION_setNetworkSelectionModeManual = 106;
        static final int TRANSACTION_setNrDualConnectivityState = 294;
        static final int TRANSACTION_setNtnSmsSupported = 436;
        static final int TRANSACTION_setNullCipherAndIntegrityEnabled = 353;
        static final int TRANSACTION_setNullCipherNotificationsEnabled = 418;
        static final int TRANSACTION_setOemEnabledSatelliteProvisionStatus = 401;
        static final int TRANSACTION_setOperatorBrandOverride = 129;
        static final int TRANSACTION_setRadio = 18;
        static final int TRANSACTION_setRadioForSubscriber = 19;
        static final int TRANSACTION_setRadioPower = 20;
        static final int TRANSACTION_setRcsClientConfiguration = 303;
        static final int TRANSACTION_setRcsProvisioningStatusForCapability = 248;
        static final int TRANSACTION_setRcsSingleRegistrationTestModeEnabled = 308;
        static final int TRANSACTION_setRemovableEsimAsDefaultEuicc = 348;
        static final int TRANSACTION_setRoamingOverride = 130;
        static final int TRANSACTION_setRttCapabilitySetting = 236;
        static final int TRANSACTION_setSatelliteAccessAllowedForSubscriptions = 399;
        static final int TRANSACTION_setSatelliteAccessControlOverlayConfigs = 398;
        static final int TRANSACTION_setSatelliteControllerTimeoutDuration = 395;
        static final int TRANSACTION_setSatelliteGatewayServicePackageName = 389;
        static final int TRANSACTION_setSatelliteIgnoreCellularServiceState = 391;
        static final int TRANSACTION_setSatelliteIgnorePlmnListFromStorage = 440;
        static final int TRANSACTION_setSatelliteListeningTimeoutDuration = 390;
        static final int TRANSACTION_setSatellitePointingUiClassName = 393;
        static final int TRANSACTION_setSatelliteServicePackageName = 388;
        static final int TRANSACTION_setSatelliteSubscriberIdListChangedIntentComponent = 431;
        static final int TRANSACTION_setShouldSendDatagramToModemInDemoMode = 412;
        static final int TRANSACTION_setSignalStrengthUpdateRequest = 332;
        static final int TRANSACTION_setSimPowerStateForSlot = 185;
        static final int TRANSACTION_setSimPowerStateForSlotWithCallback = 186;
        static final int TRANSACTION_setSimSlotMapping = 195;
        static final int TRANSACTION_setSupportDisableSatelliteWhileEnableInProgress = 392;
        static final int TRANSACTION_setSystemSelectionChannels = 273;
        static final int TRANSACTION_setTestEuiccUiComponent = 432;
        static final int TRANSACTION_setTnScanningSupport = 400;
        static final int TRANSACTION_setVoNrEnabled = 292;
        static final int TRANSACTION_setVoWiFiModeSetting = 233;
        static final int TRANSACTION_setVoWiFiNonPersistent = 231;
        static final int TRANSACTION_setVoWiFiRoamingModeSetting = 235;
        static final int TRANSACTION_setVoWiFiRoamingSettingEnabled = 230;
        static final int TRANSACTION_setVoWiFiSettingEnabled = 226;
        static final int TRANSACTION_setVoiceActivationState = 50;
        static final int TRANSACTION_setVoiceMailNumber = 49;
        static final int TRANSACTION_setVoiceServiceStateOverride = 346;
        static final int TRANSACTION_setVoicemailRingtoneUri = 161;
        static final int TRANSACTION_setVoicemailVibrationEnabled = 163;
        static final int TRANSACTION_setVtSettingEnabled = 224;
        static final int TRANSACTION_showSwitchToManagedProfileDialog = 277;
        static final int TRANSACTION_shutdownMobileRadios = 132;
        static final int TRANSACTION_startEmergencyCallbackMode = 253;
        static final int TRANSACTION_startSatelliteTransmissionUpdates = 365;
        static final int TRANSACTION_stopNetworkScan = 105;
        static final int TRANSACTION_stopSatelliteTransmissionUpdates = 366;
        static final int TRANSACTION_supplyPinForSubscriber = 9;
        static final int TRANSACTION_supplyPinReportResultForSubscriber = 11;
        static final int TRANSACTION_supplyPukForSubscriber = 10;
        static final int TRANSACTION_supplyPukReportResultForSubscriber = 12;
        static final int TRANSACTION_switchMultiSimConfig = 263;
        static final int TRANSACTION_switchSlots = 194;
        static final int TRANSACTION_toggleRadioOnOff = 16;
        static final int TRANSACTION_toggleRadioOnOffForSubscriber = 17;
        static final int TRANSACTION_triggerRcsReconfiguration = 307;
        static final int TRANSACTION_unregisterFeatureProvisioningChangedCallback = 244;
        static final int TRANSACTION_unregisterForCapabilitiesChanged = 411;
        static final int TRANSACTION_unregisterForCommunicationAccessStateChanged = 424;
        static final int TRANSACTION_unregisterForIncomingDatagram = 375;
        static final int TRANSACTION_unregisterForModemStateChanged = 373;
        static final int TRANSACTION_unregisterForNtnSignalStrengthChanged = 409;
        static final int TRANSACTION_unregisterForSatelliteDisallowedReasonsChanged = 380;
        static final int TRANSACTION_unregisterForSatelliteProvisionStateChanged = 370;
        static final int TRANSACTION_unregisterForSatelliteSupportedStateChanged = 422;
        static final int TRANSACTION_unregisterForSelectedNbIotSatelliteSubscriptionChanged = 386;
        static final int TRANSACTION_unregisterImsEmergencyRegistrationCallback = 213;
        static final int TRANSACTION_unregisterImsFeatureCallback = 95;
        static final int TRANSACTION_unregisterImsProvisioningChangedCallback = 242;
        static final int TRANSACTION_unregisterImsRegistrationCallback = 211;
        static final int TRANSACTION_unregisterImsStateCallback = 340;
        static final int TRANSACTION_unregisterMmTelCapabilityCallback = 217;
        static final int TRANSACTION_unregisterRcsProvisioningCallback = 306;
        static final int TRANSACTION_updateEmergencyNumberListTestMode = 254;
        static final int TRANSACTION_updateOtaEmergencyNumberDbFilePath = 258;
        static final int TRANSACTION_updateServiceLocation = 24;
        static final int TRANSACTION_updateServiceLocationWithPackageName = 25;
        static final int TRANSACTION_uploadCallComposerPicture = 134;
        static final int TRANSACTION_userActivity = 288;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 439;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITelephony asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITelephony)) {
                return (ITelephony) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dial";
                case 2:
                    return "call";
                case 3:
                    return "isRadioOn";
                case 4:
                    return "isRadioOnWithFeature";
                case 5:
                    return "isRadioOnForSubscriber";
                case 6:
                    return "isRadioOnForSubscriberWithFeature";
                case 7:
                    return "setCallComposerStatus";
                case 8:
                    return "getCallComposerStatus";
                case 9:
                    return "supplyPinForSubscriber";
                case 10:
                    return "supplyPukForSubscriber";
                case 11:
                    return "supplyPinReportResultForSubscriber";
                case 12:
                    return "supplyPukReportResultForSubscriber";
                case 13:
                    return "handlePinMmi";
                case 14:
                    return "handleUssdRequest";
                case 15:
                    return "handlePinMmiForSubscriber";
                case 16:
                    return "toggleRadioOnOff";
                case 17:
                    return "toggleRadioOnOffForSubscriber";
                case 18:
                    return "setRadio";
                case 19:
                    return "setRadioForSubscriber";
                case 20:
                    return "setRadioPower";
                case 21:
                    return "requestRadioPowerOffForReason";
                case 22:
                    return "clearRadioPowerOffForReason";
                case 23:
                    return "getRadioPowerOffReasons";
                case 24:
                    return "updateServiceLocation";
                case 25:
                    return "updateServiceLocationWithPackageName";
                case 26:
                    return "enableLocationUpdates";
                case 27:
                    return "disableLocationUpdates";
                case 28:
                    return "enableDataConnectivity";
                case 29:
                    return "disableDataConnectivity";
                case 30:
                    return "isDataConnectivityPossible";
                case 31:
                    return "getCellLocation";
                case 32:
                    return "getNetworkCountryIsoForPhone";
                case 33:
                    return "getNeighboringCellInfo";
                case 34:
                    return "getCallState";
                case 35:
                    return "getCallStateForSubscription";
                case 36:
                    return "getDataActivity";
                case 37:
                    return "getDataActivityForSubId";
                case 38:
                    return "getDataState";
                case 39:
                    return "getDataStateForSubId";
                case 40:
                    return "getActivePhoneType";
                case 41:
                    return "getActivePhoneTypeForSlot";
                case 42:
                    return "getCdmaEriIconIndex";
                case 43:
                    return "getCdmaEriIconIndexForSubscriber";
                case 44:
                    return "getCdmaEriIconMode";
                case 45:
                    return "getCdmaEriIconModeForSubscriber";
                case 46:
                    return "getCdmaEriText";
                case 47:
                    return "getCdmaEriTextForSubscriber";
                case 48:
                    return "needsOtaServiceProvisioning";
                case 49:
                    return "setVoiceMailNumber";
                case 50:
                    return "setVoiceActivationState";
                case 51:
                    return "setDataActivationState";
                case 52:
                    return "getVoiceActivationState";
                case 53:
                    return "getDataActivationState";
                case 54:
                    return "getVoiceMessageCountForSubscriber";
                case 55:
                    return "isConcurrentVoiceAndDataAllowed";
                case 56:
                    return "getVisualVoicemailSettings";
                case 57:
                    return "getVisualVoicemailPackageName";
                case 58:
                    return "enableVisualVoicemailSmsFilter";
                case 59:
                    return "disableVisualVoicemailSmsFilter";
                case 60:
                    return "getVisualVoicemailSmsFilterSettings";
                case 61:
                    return "getActiveVisualVoicemailSmsFilterSettings";
                case 62:
                    return "sendVisualVoicemailSmsForSubscriber";
                case 63:
                    return "sendDialerSpecialCode";
                case 64:
                    return "getNetworkTypeForSubscriber";
                case 65:
                    return "getDataNetworkType";
                case 66:
                    return "getDataNetworkTypeForSubscriber";
                case 67:
                    return "getVoiceNetworkTypeForSubscriber";
                case 68:
                    return "hasIccCard";
                case 69:
                    return "hasIccCardUsingSlotIndex";
                case 70:
                    return "getLteOnCdmaMode";
                case 71:
                    return "getLteOnCdmaModeForSubscriber";
                case 72:
                    return "getAllCellInfo";
                case 73:
                    return "requestCellInfoUpdate";
                case 74:
                    return "requestCellInfoUpdateWithWorkSource";
                case 75:
                    return "setCellInfoListRate";
                case 76:
                    return "iccOpenLogicalChannel";
                case 77:
                    return "iccCloseLogicalChannel";
                case 78:
                    return "iccTransmitApduLogicalChannelByPort";
                case 79:
                    return "iccTransmitApduLogicalChannel";
                case 80:
                    return "iccTransmitApduBasicChannelByPort";
                case 81:
                    return "iccTransmitApduBasicChannel";
                case 82:
                    return "iccExchangeSimIO";
                case 83:
                    return "sendEnvelopeWithStatus";
                case 84:
                    return "nvReadItem";
                case 85:
                    return "nvWriteItem";
                case 86:
                    return "nvWriteCdmaPrl";
                case 87:
                    return "resetModemConfig";
                case 88:
                    return "rebootModem";
                case 89:
                    return "getAllowedNetworkTypesBitmask";
                case 90:
                    return "isTetheringApnRequiredForSubscriber";
                case 91:
                    return "enableIms";
                case 92:
                    return "disableIms";
                case 93:
                    return "resetIms";
                case 94:
                    return "registerMmTelFeatureCallback";
                case 95:
                    return "unregisterImsFeatureCallback";
                case 96:
                    return "getImsRegistration";
                case 97:
                    return "getImsConfig";
                case 98:
                    return "setBoundImsServiceOverride";
                case 99:
                    return "clearCarrierImsServiceOverride";
                case 100:
                    return "getBoundImsServicePackage";
                case 101:
                    return "getImsMmTelFeatureState";
                case 102:
                    return "setNetworkSelectionModeAutomatic";
                case 103:
                    return "getCellNetworkScanResults";
                case 104:
                    return "requestNetworkScan";
                case 105:
                    return "stopNetworkScan";
                case 106:
                    return "setNetworkSelectionModeManual";
                case 107:
                    return "getAllowedNetworkTypesForReason";
                case 108:
                    return "setAllowedNetworkTypesForReason";
                case 109:
                    return "getDataEnabled";
                case 110:
                    return "isUserDataEnabled";
                case 111:
                    return "isDataEnabled";
                case 112:
                    return "setDataEnabledForReason";
                case 113:
                    return "isDataEnabledForReason";
                case 114:
                    return "isManualNetworkSelectionAllowed";
                case 115:
                    return "setImsRegistrationState";
                case 116:
                    return "getCdmaMdn";
                case 117:
                    return "getCdmaMin";
                case 118:
                    return "requestNumberVerification";
                case 119:
                    return "getCarrierPrivilegeStatus";
                case 120:
                    return "getCarrierPrivilegeStatusForUid";
                case 121:
                    return "checkCarrierPrivilegesForPackage";
                case 122:
                    return "checkCarrierPrivilegesForPackageAnyPhone";
                case 123:
                    return "getCarrierPackageNamesForIntentAndPhone";
                case 124:
                    return "setLine1NumberForDisplayForSubscriber";
                case 125:
                    return "getLine1NumberForDisplay";
                case 126:
                    return "getLine1AlphaTagForDisplay";
                case 127:
                    return "getMergedSubscriberIds";
                case 128:
                    return "getMergedImsisFromGroup";
                case 129:
                    return "setOperatorBrandOverride";
                case 130:
                    return "setRoamingOverride";
                case 131:
                    return "needMobileRadioShutdown";
                case 132:
                    return "shutdownMobileRadios";
                case 133:
                    return "getRadioAccessFamily";
                case 134:
                    return "uploadCallComposerPicture";
                case 135:
                    return "enableVideoCalling";
                case 136:
                    return "isVideoCallingEnabled";
                case 137:
                    return "canChangeDtmfToneLength";
                case 138:
                    return "isWorldPhone";
                case 139:
                    return "isTtyModeSupported";
                case 140:
                    return "isRttSupported";
                case 141:
                    return "isHearingAidCompatibilitySupported";
                case 142:
                    return "isImsRegistered";
                case 143:
                    return "isWifiCallingAvailable";
                case 144:
                    return "isVideoTelephonyAvailable";
                case 145:
                    return "getImsRegTechnologyForMmTel";
                case 146:
                    return "getDeviceId";
                case 147:
                    return "getDeviceIdWithFeature";
                case 148:
                    return "getImeiForSlot";
                case 149:
                    return "getPrimaryImei";
                case 150:
                    return "getTypeAllocationCodeForSlot";
                case 151:
                    return "getMeidForSlot";
                case 152:
                    return "getManufacturerCodeForSlot";
                case 153:
                    return "getDeviceSoftwareVersionForSlot";
                case 154:
                    return "getSubIdForPhoneAccountHandle";
                case 155:
                    return "getPhoneAccountHandleForSubscriptionId";
                case 156:
                    return "factoryReset";
                case 157:
                    return "getSimLocaleForSubscriber";
                case 158:
                    return "requestModemActivityInfo";
                case 159:
                    return "getServiceStateForSlot";
                case 160:
                    return "getVoicemailRingtoneUri";
                case 161:
                    return "setVoicemailRingtoneUri";
                case 162:
                    return "isVoicemailVibrationEnabled";
                case 163:
                    return "setVoicemailVibrationEnabled";
                case 164:
                    return "getPackagesWithCarrierPrivileges";
                case 165:
                    return "getPackagesWithCarrierPrivilegesForAllPhones";
                case 166:
                    return "getAidForAppType";
                case 167:
                    return "getEsn";
                case 168:
                    return "getCdmaPrlVersion";
                case 169:
                    return "getTelephonyHistograms";
                case 170:
                    return "setAllowedCarriers";
                case 171:
                    return "getAllowedCarriers";
                case 172:
                    return "getSubscriptionCarrierId";
                case 173:
                    return "getSubscriptionCarrierName";
                case 174:
                    return "getSubscriptionSpecificCarrierId";
                case 175:
                    return "getSubscriptionSpecificCarrierName";
                case 176:
                    return "getCarrierIdFromMccMnc";
                case 177:
                    return "carrierActionSetRadioEnabled";
                case 178:
                    return "carrierActionReportDefaultNetworkStatus";
                case 179:
                    return "carrierActionResetAll";
                case 180:
                    return "getCallForwarding";
                case 181:
                    return "setCallForwarding";
                case 182:
                    return "getCallWaitingStatus";
                case 183:
                    return "setCallWaitingStatus";
                case 184:
                    return "getClientRequestStats";
                case 185:
                    return "setSimPowerStateForSlot";
                case 186:
                    return "setSimPowerStateForSlotWithCallback";
                case 187:
                    return "getForbiddenPlmns";
                case 188:
                    return "setForbiddenPlmns";
                case 189:
                    return "getEmergencyCallbackMode";
                case 190:
                    return "getSignalStrength";
                case 191:
                    return "getCardIdForDefaultEuicc";
                case 192:
                    return "getUiccCardsInfo";
                case 193:
                    return "getUiccSlotsInfo";
                case 194:
                    return "switchSlots";
                case 195:
                    return "setSimSlotMapping";
                case 196:
                    return "isDataRoamingEnabled";
                case 197:
                    return "setDataRoamingEnabled";
                case 198:
                    return "getCdmaRoamingMode";
                case 199:
                    return "setCdmaRoamingMode";
                case 200:
                    return "getCdmaSubscriptionMode";
                case 201:
                    return "setCdmaSubscriptionMode";
                case 202:
                    return "setCarrierTestOverride";
                case 203:
                    return "setCarrierServicePackageOverride";
                case 204:
                    return "getCarrierIdListVersion";
                case 205:
                    return "refreshUiccProfile";
                case 206:
                    return "getNumberOfModemsWithSimultaneousDataConnections";
                case 207:
                    return "getNetworkSelectionMode";
                case 208:
                    return "isInEmergencySmsMode";
                case 209:
                    return "getRadioPowerState";
                case 210:
                    return "registerImsRegistrationCallback";
                case 211:
                    return "unregisterImsRegistrationCallback";
                case 212:
                    return "registerImsEmergencyRegistrationCallback";
                case 213:
                    return "unregisterImsEmergencyRegistrationCallback";
                case 214:
                    return "getImsMmTelRegistrationState";
                case 215:
                    return "getImsMmTelRegistrationTransportType";
                case 216:
                    return "registerMmTelCapabilityCallback";
                case 217:
                    return "unregisterMmTelCapabilityCallback";
                case 218:
                    return "isCapable";
                case 219:
                    return "isAvailable";
                case 220:
                    return "isMmTelCapabilitySupported";
                case 221:
                    return "isAdvancedCallingSettingEnabled";
                case 222:
                    return "setAdvancedCallingSettingEnabled";
                case 223:
                    return "isVtSettingEnabled";
                case 224:
                    return "setVtSettingEnabled";
                case 225:
                    return "isVoWiFiSettingEnabled";
                case 226:
                    return "setVoWiFiSettingEnabled";
                case 227:
                    return "isCrossSimCallingEnabledByUser";
                case 228:
                    return "setCrossSimCallingEnabled";
                case 229:
                    return "isVoWiFiRoamingSettingEnabled";
                case 230:
                    return "setVoWiFiRoamingSettingEnabled";
                case 231:
                    return "setVoWiFiNonPersistent";
                case 232:
                    return "getVoWiFiModeSetting";
                case 233:
                    return "setVoWiFiModeSetting";
                case 234:
                    return "getVoWiFiRoamingModeSetting";
                case 235:
                    return "setVoWiFiRoamingModeSetting";
                case 236:
                    return "setRttCapabilitySetting";
                case 237:
                    return "isTtyOverVolteEnabled";
                case 238:
                    return "getEmergencyNumberList";
                case 239:
                    return "isEmergencyNumber";
                case 240:
                    return "getCertsFromCarrierPrivilegeAccessRules";
                case 241:
                    return "registerImsProvisioningChangedCallback";
                case 242:
                    return "unregisterImsProvisioningChangedCallback";
                case 243:
                    return "registerFeatureProvisioningChangedCallback";
                case 244:
                    return "unregisterFeatureProvisioningChangedCallback";
                case 245:
                    return "setImsProvisioningStatusForCapability";
                case 246:
                    return "getImsProvisioningStatusForCapability";
                case 247:
                    return "getRcsProvisioningStatusForCapability";
                case 248:
                    return "setRcsProvisioningStatusForCapability";
                case 249:
                    return "getImsProvisioningInt";
                case 250:
                    return "getImsProvisioningString";
                case 251:
                    return "setImsProvisioningInt";
                case 252:
                    return "setImsProvisioningString";
                case 253:
                    return "startEmergencyCallbackMode";
                case 254:
                    return "updateEmergencyNumberListTestMode";
                case 255:
                    return "getEmergencyNumberListTestMode";
                case 256:
                    return "getEmergencyNumberDbVersion";
                case 257:
                    return "notifyOtaEmergencyNumberDbInstalled";
                case 258:
                    return "updateOtaEmergencyNumberDbFilePath";
                case 259:
                    return "resetOtaEmergencyNumberDbFilePath";
                case 260:
                    return "enableModemForSlot";
                case 261:
                    return "setMultiSimCarrierRestriction";
                case 262:
                    return "isMultiSimSupported";
                case 263:
                    return "switchMultiSimConfig";
                case 264:
                    return "doesSwitchMultiSimConfigTriggerReboot";
                case 265:
                    return "getSlotsMapping";
                case 266:
                    return "getRadioHalVersion";
                case 267:
                    return "getHalVersion";
                case 268:
                    return "getCurrentPackageName";
                case 269:
                    return "isApplicationOnUicc";
                case 270:
                    return "isModemEnabledForSlot";
                case 271:
                    return "isDataEnabledForApn";
                case 272:
                    return "isApnMetered";
                case 273:
                    return "setSystemSelectionChannels";
                case 274:
                    return "getSystemSelectionChannels";
                case 275:
                    return "isMvnoMatched";
                case 276:
                    return "enqueueSmsPickResult";
                case 277:
                    return "showSwitchToManagedProfileDialog";
                case 278:
                    return "getMmsUserAgent";
                case 279:
                    return "getMmsUAProfUrl";
                case 280:
                    return "setMobileDataPolicyEnabled";
                case 281:
                    return "isMobileDataPolicyEnabled";
                case 282:
                    return "setCepEnabled";
                case 283:
                    return "notifyRcsAutoConfigurationReceived";
                case 284:
                    return "isIccLockEnabled";
                case 285:
                    return "setIccLockEnabled";
                case 286:
                    return "changeIccLockPassword";
                case 287:
                    return "requestUserActivityNotification";
                case 288:
                    return "userActivity";
                case 289:
                    return "getManualNetworkSelectionPlmn";
                case 290:
                    return "canConnectTo5GInDsdsMode";
                case 291:
                    return "getEquivalentHomePlmns";
                case 292:
                    return "setVoNrEnabled";
                case 293:
                    return "isVoNrEnabled";
                case 294:
                    return "setNrDualConnectivityState";
                case 295:
                    return "isNrDualConnectivityEnabled";
                case 296:
                    return "isRadioInterfaceCapabilitySupported";
                case 297:
                    return "sendThermalMitigationRequest";
                case 298:
                    return "bootstrapAuthenticationRequest";
                case 299:
                    return "setBoundGbaServiceOverride";
                case 300:
                    return "getBoundGbaService";
                case 301:
                    return "setGbaReleaseTimeOverride";
                case 302:
                    return "getGbaReleaseTime";
                case 303:
                    return "setRcsClientConfiguration";
                case 304:
                    return "isRcsVolteSingleRegistrationCapable";
                case 305:
                    return "registerRcsProvisioningCallback";
                case 306:
                    return "unregisterRcsProvisioningCallback";
                case 307:
                    return "triggerRcsReconfiguration";
                case 308:
                    return "setRcsSingleRegistrationTestModeEnabled";
                case 309:
                    return "getRcsSingleRegistrationTestModeEnabled";
                case 310:
                    return "setDeviceSingleRegistrationEnabledOverride";
                case 311:
                    return "getDeviceSingleRegistrationEnabled";
                case 312:
                    return "setCarrierSingleRegistrationEnabledOverride";
                case 313:
                    return "sendDeviceToDeviceMessage";
                case 314:
                    return "setActiveDeviceToDeviceTransport";
                case 315:
                    return "setDeviceToDeviceForceEnabled";
                case 316:
                    return "getCarrierSingleRegistrationEnabled";
                case 317:
                    return "setImsFeatureValidationOverride";
                case 318:
                    return "getImsFeatureValidationOverride";
                case 319:
                    return "getMobileProvisioningUrl";
                case 320:
                    return "removeContactFromEab";
                case 321:
                    return "getContactFromEab";
                case 322:
                    return "getCapabilityFromEab";
                case 323:
                    return "getDeviceUceEnabled";
                case 324:
                    return "setDeviceUceEnabled";
                case 325:
                    return "addUceRegistrationOverrideShell";
                case 326:
                    return "removeUceRegistrationOverrideShell";
                case 327:
                    return "clearUceRegistrationOverrideShell";
                case 328:
                    return "getLatestRcsContactUceCapabilityShell";
                case 329:
                    return "getLastUcePidfXmlShell";
                case 330:
                    return "removeUceRequestDisallowedStatus";
                case 331:
                    return "setCapabilitiesRequestTimeout";
                case 332:
                    return "setSignalStrengthUpdateRequest";
                case 333:
                    return "clearSignalStrengthUpdateRequest";
                case 334:
                    return "getPhoneCapability";
                case 335:
                    return "prepareForUnattendedReboot";
                case 336:
                    return "getSlicingConfig";
                case 337:
                    return "isPremiumCapabilityAvailableForPurchase";
                case 338:
                    return "purchasePremiumCapability";
                case 339:
                    return "registerImsStateCallback";
                case 340:
                    return "unregisterImsStateCallback";
                case 341:
                    return "getLastKnownCellIdentity";
                case 342:
                    return "setModemService";
                case 343:
                    return "getModemService";
                case 344:
                    return "isProvisioningRequiredForCapability";
                case 345:
                    return "isRcsProvisioningRequiredForCapability";
                case 346:
                    return "setVoiceServiceStateOverride";
                case 347:
                    return "getCarrierServicePackageNameForLogicalSlot";
                case 348:
                    return "setRemovableEsimAsDefaultEuicc";
                case 349:
                    return "isRemovableEsimDefaultEuicc";
                case 350:
                    return "getDefaultRespondViaMessageApplication";
                case 351:
                    return "getSimStateForSlotIndex";
                case 352:
                    return "persistEmergencyCallDiagnosticData";
                case 353:
                    return "setNullCipherAndIntegrityEnabled";
                case 354:
                    return "isNullCipherAndIntegrityPreferenceEnabled";
                case 355:
                    return "getCellBroadcastIdRanges";
                case 356:
                    return "setCellBroadcastIdRanges";
                case 357:
                    return "isDomainSelectionSupported";
                case 358:
                    return "getCarrierRestrictionStatus";
                case 359:
                    return "requestSatelliteEnabled";
                case 360:
                    return "requestIsSatelliteEnabled";
                case 361:
                    return "requestIsDemoModeEnabled";
                case 362:
                    return "requestIsEmergencyModeEnabled";
                case 363:
                    return "requestIsSatelliteSupported";
                case 364:
                    return "requestSatelliteCapabilities";
                case 365:
                    return "startSatelliteTransmissionUpdates";
                case 366:
                    return "stopSatelliteTransmissionUpdates";
                case 367:
                    return "provisionSatelliteService";
                case 368:
                    return "deprovisionSatelliteService";
                case 369:
                    return "registerForSatelliteProvisionStateChanged";
                case 370:
                    return "unregisterForSatelliteProvisionStateChanged";
                case 371:
                    return "requestIsSatelliteProvisioned";
                case 372:
                    return "registerForSatelliteModemStateChanged";
                case 373:
                    return "unregisterForModemStateChanged";
                case 374:
                    return "registerForIncomingDatagram";
                case 375:
                    return "unregisterForIncomingDatagram";
                case 376:
                    return "pollPendingDatagrams";
                case 377:
                    return "sendDatagram";
                case 378:
                    return "getSatelliteDisallowedReasons";
                case 379:
                    return "registerForSatelliteDisallowedReasonsChanged";
                case 380:
                    return "unregisterForSatelliteDisallowedReasonsChanged";
                case 381:
                    return "requestIsCommunicationAllowedForCurrentLocation";
                case 382:
                    return "requestSatelliteAccessConfigurationForCurrentLocation";
                case 383:
                    return "requestTimeForNextSatelliteVisibility";
                case 384:
                    return "requestSelectedNbIotSatelliteSubscriptionId";
                case 385:
                    return "registerForSelectedNbIotSatelliteSubscriptionChanged";
                case 386:
                    return "unregisterForSelectedNbIotSatelliteSubscriptionChanged";
                case 387:
                    return "setDeviceAlignedWithSatellite";
                case 388:
                    return "setSatelliteServicePackageName";
                case 389:
                    return "setSatelliteGatewayServicePackageName";
                case 390:
                    return "setSatelliteListeningTimeoutDuration";
                case 391:
                    return "setSatelliteIgnoreCellularServiceState";
                case 392:
                    return "setSupportDisableSatelliteWhileEnableInProgress";
                case 393:
                    return "setSatellitePointingUiClassName";
                case 394:
                    return "setDatagramControllerTimeoutDuration";
                case 395:
                    return "setSatelliteControllerTimeoutDuration";
                case 396:
                    return "setEmergencyCallToSatelliteHandoverType";
                case 397:
                    return "setCountryCodes";
                case 398:
                    return "setSatelliteAccessControlOverlayConfigs";
                case 399:
                    return "setSatelliteAccessAllowedForSubscriptions";
                case 400:
                    return "setTnScanningSupport";
                case 401:
                    return "setOemEnabledSatelliteProvisionStatus";
                case 402:
                    return "overrideConfigDataVersion";
                case 403:
                    return "getShaIdFromAllowList";
                case 404:
                    return "addAttachRestrictionForCarrier";
                case 405:
                    return "removeAttachRestrictionForCarrier";
                case 406:
                    return "getAttachRestrictionReasonsForCarrier";
                case 407:
                    return "requestNtnSignalStrength";
                case 408:
                    return "registerForNtnSignalStrengthChanged";
                case 409:
                    return "unregisterForNtnSignalStrengthChanged";
                case 410:
                    return "registerForCapabilitiesChanged";
                case 411:
                    return "unregisterForCapabilitiesChanged";
                case 412:
                    return "setShouldSendDatagramToModemInDemoMode";
                case 413:
                    return "setDomainSelectionServiceOverride";
                case 414:
                    return "clearDomainSelectionServiceOverride";
                case 415:
                    return "isAospDomainSelectionService";
                case 416:
                    return "setEnableCellularIdentifierDisclosureNotifications";
                case 417:
                    return "isCellularIdentifierDisclosureNotificationsEnabled";
                case 418:
                    return "setNullCipherNotificationsEnabled";
                case 419:
                    return "isNullCipherNotificationsEnabled";
                case 420:
                    return "getSatellitePlmnsForCarrier";
                case 421:
                    return "registerForSatelliteSupportedStateChanged";
                case 422:
                    return "unregisterForSatelliteSupportedStateChanged";
                case 423:
                    return "registerForCommunicationAccessStateChanged";
                case 424:
                    return "unregisterForCommunicationAccessStateChanged";
                case 425:
                    return "setDatagramControllerBooleanConfig";
                case 426:
                    return "setIsSatelliteCommunicationAllowedForCurrentLocationCache";
                case 427:
                    return "requestSatelliteSessionStats";
                case 428:
                    return "requestSatelliteSubscriberProvisionStatus";
                case 429:
                    return "requestSatelliteDisplayName";
                case 430:
                    return "provisionSatellite";
                case 431:
                    return "setSatelliteSubscriberIdListChangedIntentComponent";
                case 432:
                    return "setTestEuiccUiComponent";
                case 433:
                    return "getTestEuiccUiComponent";
                case 434:
                    return "overrideCarrierRoamingNtnEligibilityChanged";
                case 435:
                    return "deprovisionSatellite";
                case 436:
                    return "setNtnSmsSupported";
                case 437:
                    return "getCarrierIdFromIdentifier";
                case 438:
                    return "getSatelliteDataOptimizedApps";
                case 439:
                    return "getSatelliteDataSupportMode";
                case 440:
                    return "setSatelliteIgnorePlmnListFromStorage";
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
                    parcel.enforceNoDataAvail();
                    dial(string);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    call(string2, string3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsRadioOn = isRadioOn(string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRadioOn);
                    return true;
                case 4:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsRadioOnWithFeature = isRadioOnWithFeature(string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRadioOnWithFeature);
                    return true;
                case 5:
                    int i3 = parcel.readInt();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsRadioOnForSubscriber = isRadioOnForSubscriber(i3, string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRadioOnForSubscriber);
                    return true;
                case 6:
                    return onTransact$isRadioOnForSubscriberWithFeature$(parcel, parcel2);
                case 7:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCallComposerStatus(i4, i5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int callComposerStatus = getCallComposerStatus(i6);
                    parcel2.writeNoException();
                    parcel2.writeInt(callComposerStatus);
                    return true;
                case 9:
                    int i7 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zSupplyPinForSubscriber = supplyPinForSubscriber(i7, string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSupplyPinForSubscriber);
                    return true;
                case 10:
                    return onTransact$supplyPukForSubscriber$(parcel, parcel2);
                case 11:
                    int i8 = parcel.readInt();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] iArrSupplyPinReportResultForSubscriber = supplyPinReportResultForSubscriber(i8, string9);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(iArrSupplyPinReportResultForSubscriber);
                    return true;
                case 12:
                    return onTransact$supplyPukReportResultForSubscriber$(parcel, parcel2);
                case 13:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHandlePinMmi = handlePinMmi(string10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHandlePinMmi);
                    return true;
                case 14:
                    return onTransact$handleUssdRequest$(parcel, parcel2);
                case 15:
                    int i9 = parcel.readInt();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHandlePinMmiForSubscriber = handlePinMmiForSubscriber(i9, string11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHandlePinMmiForSubscriber);
                    return true;
                case 16:
                    toggleRadioOnOff();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    toggleRadioOnOffForSubscriber(i10);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean radio = setRadio(z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(radio);
                    return true;
                case 19:
                    int i11 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean radioForSubscriber = setRadioForSubscriber(i11, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(radioForSubscriber);
                    return true;
                case 20:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean radioPower = setRadioPower(z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(radioPower);
                    return true;
                case 21:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRequestRadioPowerOffForReason = requestRadioPowerOffForReason(i12, i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestRadioPowerOffForReason);
                    return true;
                case 22:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zClearRadioPowerOffForReason = clearRadioPowerOffForReason(i14, i15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearRadioPowerOffForReason);
                    return true;
                case 23:
                    return onTransact$getRadioPowerOffReasons$(parcel, parcel2);
                case 24:
                    updateServiceLocation();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateServiceLocationWithPackageName(string12);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    enableLocationUpdates();
                    parcel2.writeNoException();
                    return true;
                case 27:
                    disableLocationUpdates();
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zEnableDataConnectivity = enableDataConnectivity(string13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableDataConnectivity);
                    return true;
                case 29:
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDisableDataConnectivity = disableDataConnectivity(string14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableDataConnectivity);
                    return true;
                case 30:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDataConnectivityPossible = isDataConnectivityPossible(i16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDataConnectivityPossible);
                    return true;
                case 31:
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CellIdentity cellLocation = getCellLocation(string15, string16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cellLocation, 1);
                    return true;
                case 32:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String networkCountryIsoForPhone = getNetworkCountryIsoForPhone(i17);
                    parcel2.writeNoException();
                    parcel2.writeString(networkCountryIsoForPhone);
                    return true;
                case 33:
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<NeighboringCellInfo> neighboringCellInfo = getNeighboringCellInfo(string17, string18);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(neighboringCellInfo, 1);
                    return true;
                case 34:
                    int callState = getCallState();
                    parcel2.writeNoException();
                    parcel2.writeInt(callState);
                    return true;
                case 35:
                    return onTransact$getCallStateForSubscription$(parcel, parcel2);
                case 36:
                    int dataActivity = getDataActivity();
                    parcel2.writeNoException();
                    parcel2.writeInt(dataActivity);
                    return true;
                case 37:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int dataActivityForSubId = getDataActivityForSubId(i18);
                    parcel2.writeNoException();
                    parcel2.writeInt(dataActivityForSubId);
                    return true;
                case 38:
                    int dataState = getDataState();
                    parcel2.writeNoException();
                    parcel2.writeInt(dataState);
                    return true;
                case 39:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int dataStateForSubId = getDataStateForSubId(i19);
                    parcel2.writeNoException();
                    parcel2.writeInt(dataStateForSubId);
                    return true;
                case 40:
                    int activePhoneType = getActivePhoneType();
                    parcel2.writeNoException();
                    parcel2.writeInt(activePhoneType);
                    return true;
                case 41:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int activePhoneTypeForSlot = getActivePhoneTypeForSlot(i20);
                    parcel2.writeNoException();
                    parcel2.writeInt(activePhoneTypeForSlot);
                    return true;
                case 42:
                    String string19 = parcel.readString();
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cdmaEriIconIndex = getCdmaEriIconIndex(string19, string20);
                    parcel2.writeNoException();
                    parcel2.writeInt(cdmaEriIconIndex);
                    return true;
                case 43:
                    return onTransact$getCdmaEriIconIndexForSubscriber$(parcel, parcel2);
                case 44:
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cdmaEriIconMode = getCdmaEriIconMode(string21, string22);
                    parcel2.writeNoException();
                    parcel2.writeInt(cdmaEriIconMode);
                    return true;
                case 45:
                    return onTransact$getCdmaEriIconModeForSubscriber$(parcel, parcel2);
                case 46:
                    String string23 = parcel.readString();
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String cdmaEriText = getCdmaEriText(string23, string24);
                    parcel2.writeNoException();
                    parcel2.writeString(cdmaEriText);
                    return true;
                case 47:
                    return onTransact$getCdmaEriTextForSubscriber$(parcel, parcel2);
                case 48:
                    boolean zNeedsOtaServiceProvisioning = needsOtaServiceProvisioning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zNeedsOtaServiceProvisioning);
                    return true;
                case 49:
                    return onTransact$setVoiceMailNumber$(parcel, parcel2);
                case 50:
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVoiceActivationState(i21, i22);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDataActivationState(i23, i24);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int i25 = parcel.readInt();
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int voiceActivationState = getVoiceActivationState(i25, string25);
                    parcel2.writeNoException();
                    parcel2.writeInt(voiceActivationState);
                    return true;
                case 53:
                    int i26 = parcel.readInt();
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int dataActivationState = getDataActivationState(i26, string26);
                    parcel2.writeNoException();
                    parcel2.writeInt(dataActivationState);
                    return true;
                case 54:
                    return onTransact$getVoiceMessageCountForSubscriber$(parcel, parcel2);
                case 55:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsConcurrentVoiceAndDataAllowed = isConcurrentVoiceAndDataAllowed(i27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsConcurrentVoiceAndDataAllowed);
                    return true;
                case 56:
                    String string27 = parcel.readString();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle visualVoicemailSettings = getVisualVoicemailSettings(string27, i28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(visualVoicemailSettings, 1);
                    return true;
                case 57:
                    return onTransact$getVisualVoicemailPackageName$(parcel, parcel2);
                case 58:
                    return onTransact$enableVisualVoicemailSmsFilter$(parcel, parcel2);
                case 59:
                    String string28 = parcel.readString();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableVisualVoicemailSmsFilter(string28, i29);
                    return true;
                case 60:
                    String string29 = parcel.readString();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings = getVisualVoicemailSmsFilterSettings(string29, i30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(visualVoicemailSmsFilterSettings, 1);
                    return true;
                case 61:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VisualVoicemailSmsFilterSettings activeVisualVoicemailSmsFilterSettings = getActiveVisualVoicemailSmsFilterSettings(i31);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeVisualVoicemailSmsFilterSettings, 1);
                    return true;
                case 62:
                    return onTransact$sendVisualVoicemailSmsForSubscriber$(parcel, parcel2);
                case 63:
                    String string30 = parcel.readString();
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendDialerSpecialCode(string30, string31);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    return onTransact$getNetworkTypeForSubscriber$(parcel, parcel2);
                case 65:
                    String string32 = parcel.readString();
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int dataNetworkType = getDataNetworkType(string32, string33);
                    parcel2.writeNoException();
                    parcel2.writeInt(dataNetworkType);
                    return true;
                case 66:
                    return onTransact$getDataNetworkTypeForSubscriber$(parcel, parcel2);
                case 67:
                    return onTransact$getVoiceNetworkTypeForSubscriber$(parcel, parcel2);
                case 68:
                    boolean zHasIccCard = hasIccCard();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasIccCard);
                    return true;
                case 69:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasIccCardUsingSlotIndex = hasIccCardUsingSlotIndex(i32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasIccCardUsingSlotIndex);
                    return true;
                case 70:
                    String string34 = parcel.readString();
                    String string35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int lteOnCdmaMode = getLteOnCdmaMode(string34, string35);
                    parcel2.writeNoException();
                    parcel2.writeInt(lteOnCdmaMode);
                    return true;
                case 71:
                    return onTransact$getLteOnCdmaModeForSubscriber$(parcel, parcel2);
                case 72:
                    String string36 = parcel.readString();
                    String string37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<CellInfo> allCellInfo = getAllCellInfo(string36, string37);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allCellInfo, 1);
                    return true;
                case 73:
                    return onTransact$requestCellInfoUpdate$(parcel, parcel2);
                case 74:
                    return onTransact$requestCellInfoUpdateWithWorkSource$(parcel, parcel2);
                case 75:
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCellInfoListRate(i33, i34);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    IccLogicalChannelRequest iccLogicalChannelRequest = (IccLogicalChannelRequest) parcel.readTypedObject(IccLogicalChannelRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    IccOpenLogicalChannelResponse iccOpenLogicalChannelResponseIccOpenLogicalChannel = iccOpenLogicalChannel(iccLogicalChannelRequest);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(iccOpenLogicalChannelResponseIccOpenLogicalChannel, 1);
                    return true;
                case 77:
                    IccLogicalChannelRequest iccLogicalChannelRequest2 = (IccLogicalChannelRequest) parcel.readTypedObject(IccLogicalChannelRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIccCloseLogicalChannel = iccCloseLogicalChannel(iccLogicalChannelRequest2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIccCloseLogicalChannel);
                    return true;
                case 78:
                    return onTransact$iccTransmitApduLogicalChannelByPort$(parcel, parcel2);
                case 79:
                    return onTransact$iccTransmitApduLogicalChannel$(parcel, parcel2);
                case 80:
                    return onTransact$iccTransmitApduBasicChannelByPort$(parcel, parcel2);
                case 81:
                    return onTransact$iccTransmitApduBasicChannel$(parcel, parcel2);
                case 82:
                    return onTransact$iccExchangeSimIO$(parcel, parcel2);
                case 83:
                    int i35 = parcel.readInt();
                    String string38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strSendEnvelopeWithStatus = sendEnvelopeWithStatus(i35, string38);
                    parcel2.writeNoException();
                    parcel2.writeString(strSendEnvelopeWithStatus);
                    return true;
                case 84:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strNvReadItem = nvReadItem(i36);
                    parcel2.writeNoException();
                    parcel2.writeString(strNvReadItem);
                    return true;
                case 85:
                    int i37 = parcel.readInt();
                    String string39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zNvWriteItem = nvWriteItem(i37, string39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zNvWriteItem);
                    return true;
                case 86:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean zNvWriteCdmaPrl = nvWriteCdmaPrl(bArrCreateByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zNvWriteCdmaPrl);
                    return true;
                case 87:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zResetModemConfig = resetModemConfig(i38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetModemConfig);
                    return true;
                case 88:
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRebootModem = rebootModem(i39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRebootModem);
                    return true;
                case 89:
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int allowedNetworkTypesBitmask = getAllowedNetworkTypesBitmask(i40);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowedNetworkTypesBitmask);
                    return true;
                case 90:
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsTetheringApnRequiredForSubscriber = isTetheringApnRequiredForSubscriber(i41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTetheringApnRequiredForSubscriber);
                    return true;
                case 91:
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableIms(i42);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableIms(i43);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetIms(i44);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    int i45 = parcel.readInt();
                    IImsServiceFeatureCallback iImsServiceFeatureCallbackAsInterface = IImsServiceFeatureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerMmTelFeatureCallback(i45, iImsServiceFeatureCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    IImsServiceFeatureCallback iImsServiceFeatureCallbackAsInterface2 = IImsServiceFeatureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsFeatureCallback(iImsServiceFeatureCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    int i46 = parcel.readInt();
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsRegistration imsRegistration = getImsRegistration(i46, i47);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(imsRegistration);
                    return true;
                case 97:
                    int i48 = parcel.readInt();
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsConfig imsConfig = getImsConfig(i48, i49);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(imsConfig);
                    return true;
                case 98:
                    return onTransact$setBoundImsServiceOverride$(parcel, parcel2);
                case 99:
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zClearCarrierImsServiceOverride = clearCarrierImsServiceOverride(i50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearCarrierImsServiceOverride);
                    return true;
                case 100:
                    return onTransact$getBoundImsServicePackage$(parcel, parcel2);
                case 101:
                    int i51 = parcel.readInt();
                    IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getImsMmTelFeatureState(i51, iIntegerConsumerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNetworkSelectionModeAutomatic(i52);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    return onTransact$getCellNetworkScanResults$(parcel, parcel2);
                case 104:
                    return onTransact$requestNetworkScan$(parcel, parcel2);
                case 105:
                    int i53 = parcel.readInt();
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopNetworkScan(i53, i54);
                    parcel2.writeNoException();
                    return true;
                case 106:
                    return onTransact$setNetworkSelectionModeManual$(parcel, parcel2);
                case 107:
                    int i55 = parcel.readInt();
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long allowedNetworkTypesForReason = getAllowedNetworkTypesForReason(i55, i56);
                    parcel2.writeNoException();
                    parcel2.writeLong(allowedNetworkTypesForReason);
                    return true;
                case 108:
                    return onTransact$setAllowedNetworkTypesForReason$(parcel, parcel2);
                case 109:
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dataEnabled = getDataEnabled(i57);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataEnabled);
                    return true;
                case 110:
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserDataEnabled = isUserDataEnabled(i58);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserDataEnabled);
                    return true;
                case 111:
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDataEnabled = isDataEnabled(i59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDataEnabled);
                    return true;
                case 112:
                    return onTransact$setDataEnabledForReason$(parcel, parcel2);
                case 113:
                    int i60 = parcel.readInt();
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDataEnabledForReason = isDataEnabledForReason(i60, i61);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDataEnabledForReason);
                    return true;
                case 114:
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsManualNetworkSelectionAllowed = isManualNetworkSelectionAllowed(i62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsManualNetworkSelectionAllowed);
                    return true;
                case 115:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImsRegistrationState(z4);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String cdmaMdn = getCdmaMdn(i63);
                    parcel2.writeNoException();
                    parcel2.writeString(cdmaMdn);
                    return true;
                case 117:
                    int i64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String cdmaMin = getCdmaMin(i64);
                    parcel2.writeNoException();
                    parcel2.writeString(cdmaMin);
                    return true;
                case 118:
                    return onTransact$requestNumberVerification$(parcel, parcel2);
                case 119:
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int carrierPrivilegeStatus = getCarrierPrivilegeStatus(i65);
                    parcel2.writeNoException();
                    parcel2.writeInt(carrierPrivilegeStatus);
                    return true;
                case 120:
                    int i66 = parcel.readInt();
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int carrierPrivilegeStatusForUid = getCarrierPrivilegeStatusForUid(i66, i67);
                    parcel2.writeNoException();
                    parcel2.writeInt(carrierPrivilegeStatusForUid);
                    return true;
                case 121:
                    int i68 = parcel.readInt();
                    String string40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCheckCarrierPrivilegesForPackage = checkCarrierPrivilegesForPackage(i68, string40);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckCarrierPrivilegesForPackage);
                    return true;
                case 122:
                    String string41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCheckCarrierPrivilegesForPackageAnyPhone = checkCarrierPrivilegesForPackageAnyPhone(string41);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckCarrierPrivilegesForPackageAnyPhone);
                    return true;
                case 123:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int i69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> carrierPackageNamesForIntentAndPhone = getCarrierPackageNamesForIntentAndPhone(intent, i69);
                    parcel2.writeNoException();
                    parcel2.writeStringList(carrierPackageNamesForIntentAndPhone);
                    return true;
                case 124:
                    return onTransact$setLine1NumberForDisplayForSubscriber$(parcel, parcel2);
                case 125:
                    return onTransact$getLine1NumberForDisplay$(parcel, parcel2);
                case 126:
                    return onTransact$getLine1AlphaTagForDisplay$(parcel, parcel2);
                case 127:
                    return onTransact$getMergedSubscriberIds$(parcel, parcel2);
                case 128:
                    int i70 = parcel.readInt();
                    String string42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] mergedImsisFromGroup = getMergedImsisFromGroup(i70, string42);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(mergedImsisFromGroup);
                    return true;
                case 129:
                    int i71 = parcel.readInt();
                    String string43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean operatorBrandOverride = setOperatorBrandOverride(i71, string43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(operatorBrandOverride);
                    return true;
                case 130:
                    return onTransact$setRoamingOverride$(parcel, parcel2);
                case 131:
                    boolean zNeedMobileRadioShutdown = needMobileRadioShutdown();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zNeedMobileRadioShutdown);
                    return true;
                case 132:
                    shutdownMobileRadios();
                    parcel2.writeNoException();
                    return true;
                case 133:
                    int i72 = parcel.readInt();
                    String string44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int radioAccessFamily = getRadioAccessFamily(i72, string44);
                    parcel2.writeNoException();
                    parcel2.writeInt(radioAccessFamily);
                    return true;
                case 134:
                    return onTransact$uploadCallComposerPicture$(parcel, parcel2);
                case 135:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableVideoCalling(z5);
                    parcel2.writeNoException();
                    return true;
                case 136:
                    String string45 = parcel.readString();
                    String string46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsVideoCallingEnabled = isVideoCallingEnabled(string45, string46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVideoCallingEnabled);
                    return true;
                case 137:
                    return onTransact$canChangeDtmfToneLength$(parcel, parcel2);
                case 138:
                    return onTransact$isWorldPhone$(parcel, parcel2);
                case 139:
                    boolean zIsTtyModeSupported = isTtyModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTtyModeSupported);
                    return true;
                case 140:
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsRttSupported = isRttSupported(i73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRttSupported);
                    return true;
                case 141:
                    boolean zIsHearingAidCompatibilitySupported = isHearingAidCompatibilitySupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHearingAidCompatibilitySupported);
                    return true;
                case 142:
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsImsRegistered = isImsRegistered(i74);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsImsRegistered);
                    return true;
                case 143:
                    int i75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsWifiCallingAvailable = isWifiCallingAvailable(i75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWifiCallingAvailable);
                    return true;
                case 144:
                    int i76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVideoTelephonyAvailable = isVideoTelephonyAvailable(i76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVideoTelephonyAvailable);
                    return true;
                case 145:
                    int i77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int imsRegTechnologyForMmTel = getImsRegTechnologyForMmTel(i77);
                    parcel2.writeNoException();
                    parcel2.writeInt(imsRegTechnologyForMmTel);
                    return true;
                case 146:
                    String string47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String deviceId = getDeviceId(string47);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    return true;
                case 147:
                    String string48 = parcel.readString();
                    String string49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String deviceIdWithFeature = getDeviceIdWithFeature(string48, string49);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceIdWithFeature);
                    return true;
                case 148:
                    return onTransact$getImeiForSlot$(parcel, parcel2);
                case 149:
                    String string50 = parcel.readString();
                    String string51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String primaryImei = getPrimaryImei(string50, string51);
                    parcel2.writeNoException();
                    parcel2.writeString(primaryImei);
                    return true;
                case 150:
                    int i78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String typeAllocationCodeForSlot = getTypeAllocationCodeForSlot(i78);
                    parcel2.writeNoException();
                    parcel2.writeString(typeAllocationCodeForSlot);
                    return true;
                case 151:
                    return onTransact$getMeidForSlot$(parcel, parcel2);
                case 152:
                    int i79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String manufacturerCodeForSlot = getManufacturerCodeForSlot(i79);
                    parcel2.writeNoException();
                    parcel2.writeString(manufacturerCodeForSlot);
                    return true;
                case 153:
                    return onTransact$getDeviceSoftwareVersionForSlot$(parcel, parcel2);
                case 154:
                    return onTransact$getSubIdForPhoneAccountHandle$(parcel, parcel2);
                case 155:
                    int i80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PhoneAccountHandle phoneAccountHandleForSubscriptionId = getPhoneAccountHandleForSubscriptionId(i80);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(phoneAccountHandleForSubscriptionId, 1);
                    return true;
                case 156:
                    int i81 = parcel.readInt();
                    String string52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    factoryReset(i81, string52);
                    parcel2.writeNoException();
                    return true;
                case 157:
                    int i82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String simLocaleForSubscriber = getSimLocaleForSubscriber(i82);
                    parcel2.writeNoException();
                    parcel2.writeString(simLocaleForSubscriber);
                    return true;
                case 158:
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestModemActivityInfo(resultReceiver);
                    return true;
                case 159:
                    return onTransact$getServiceStateForSlot$(parcel, parcel2);
                case 160:
                    PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Uri voicemailRingtoneUri = getVoicemailRingtoneUri(phoneAccountHandle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(voicemailRingtoneUri, 1);
                    return true;
                case 161:
                    return onTransact$setVoicemailRingtoneUri$(parcel, parcel2);
                case 162:
                    PhoneAccountHandle phoneAccountHandle2 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsVoicemailVibrationEnabled = isVoicemailVibrationEnabled(phoneAccountHandle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVoicemailVibrationEnabled);
                    return true;
                case 163:
                    return onTransact$setVoicemailVibrationEnabled$(parcel, parcel2);
                case 164:
                    int i83 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> packagesWithCarrierPrivileges = getPackagesWithCarrierPrivileges(i83);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesWithCarrierPrivileges);
                    return true;
                case 165:
                    List<String> packagesWithCarrierPrivilegesForAllPhones = getPackagesWithCarrierPrivilegesForAllPhones();
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesWithCarrierPrivilegesForAllPhones);
                    return true;
                case 166:
                    int i84 = parcel.readInt();
                    int i85 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String aidForAppType = getAidForAppType(i84, i85);
                    parcel2.writeNoException();
                    parcel2.writeString(aidForAppType);
                    return true;
                case 167:
                    int i86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String esn = getEsn(i86);
                    parcel2.writeNoException();
                    parcel2.writeString(esn);
                    return true;
                case 168:
                    int i87 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String cdmaPrlVersion = getCdmaPrlVersion(i87);
                    parcel2.writeNoException();
                    parcel2.writeString(cdmaPrlVersion);
                    return true;
                case 169:
                    List<TelephonyHistogram> telephonyHistograms = getTelephonyHistograms();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(telephonyHistograms, 1);
                    return true;
                case 170:
                    CarrierRestrictionRules carrierRestrictionRules = (CarrierRestrictionRules) parcel.readTypedObject(CarrierRestrictionRules.CREATOR);
                    parcel.enforceNoDataAvail();
                    int allowedCarriers = setAllowedCarriers(carrierRestrictionRules);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowedCarriers);
                    return true;
                case 171:
                    CarrierRestrictionRules allowedCarriers2 = getAllowedCarriers();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allowedCarriers2, 1);
                    return true;
                case 172:
                    int i88 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int subscriptionCarrierId = getSubscriptionCarrierId(i88);
                    parcel2.writeNoException();
                    parcel2.writeInt(subscriptionCarrierId);
                    return true;
                case 173:
                    int i89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String subscriptionCarrierName = getSubscriptionCarrierName(i89);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriptionCarrierName);
                    return true;
                case 174:
                    int i90 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int subscriptionSpecificCarrierId = getSubscriptionSpecificCarrierId(i90);
                    parcel2.writeNoException();
                    parcel2.writeInt(subscriptionSpecificCarrierId);
                    return true;
                case 175:
                    int i91 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String subscriptionSpecificCarrierName = getSubscriptionSpecificCarrierName(i91);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriptionSpecificCarrierName);
                    return true;
                case 176:
                    return onTransact$getCarrierIdFromMccMnc$(parcel, parcel2);
                case 177:
                    int i92 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    carrierActionSetRadioEnabled(i92, z6);
                    parcel2.writeNoException();
                    return true;
                case 178:
                    int i93 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    carrierActionReportDefaultNetworkStatus(i93, z7);
                    parcel2.writeNoException();
                    return true;
                case 179:
                    int i94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    carrierActionResetAll(i94);
                    parcel2.writeNoException();
                    return true;
                case 180:
                    return onTransact$getCallForwarding$(parcel, parcel2);
                case 181:
                    return onTransact$setCallForwarding$(parcel, parcel2);
                case 182:
                    int i95 = parcel.readInt();
                    IIntegerConsumer iIntegerConsumerAsInterface2 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getCallWaitingStatus(i95, iIntegerConsumerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 183:
                    return onTransact$setCallWaitingStatus$(parcel, parcel2);
                case 184:
                    return onTransact$getClientRequestStats$(parcel, parcel2);
                case 185:
                    int i96 = parcel.readInt();
                    int i97 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSimPowerStateForSlot(i96, i97);
                    parcel2.writeNoException();
                    return true;
                case 186:
                    return onTransact$setSimPowerStateForSlotWithCallback$(parcel, parcel2);
                case 187:
                    return onTransact$getForbiddenPlmns$(parcel, parcel2);
                case 188:
                    return onTransact$setForbiddenPlmns$(parcel, parcel2);
                case 189:
                    int i98 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean emergencyCallbackMode = getEmergencyCallbackMode(i98);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(emergencyCallbackMode);
                    return true;
                case 190:
                    int i99 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SignalStrength signalStrength = getSignalStrength(i99);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(signalStrength, 1);
                    return true;
                case 191:
                    int i100 = parcel.readInt();
                    String string53 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cardIdForDefaultEuicc = getCardIdForDefaultEuicc(i100, string53);
                    parcel2.writeNoException();
                    parcel2.writeInt(cardIdForDefaultEuicc);
                    return true;
                case 192:
                    String string54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<UiccCardInfo> uiccCardsInfo = getUiccCardsInfo(string54);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(uiccCardsInfo, 1);
                    return true;
                case 193:
                    String string55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UiccSlotInfo[] uiccSlotsInfo = getUiccSlotsInfo(string55);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(uiccSlotsInfo, 1);
                    return true;
                case 194:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    boolean zSwitchSlots = switchSlots(iArrCreateIntArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSwitchSlots);
                    return true;
                case 195:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(UiccSlotMapping.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean simSlotMapping = setSimSlotMapping(arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(simSlotMapping);
                    return true;
                case 196:
                    int i101 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDataRoamingEnabled = isDataRoamingEnabled(i101);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDataRoamingEnabled);
                    return true;
                case 197:
                    int i102 = parcel.readInt();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDataRoamingEnabled(i102, z8);
                    parcel2.writeNoException();
                    return true;
                case 198:
                    int i103 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int cdmaRoamingMode = getCdmaRoamingMode(i103);
                    parcel2.writeNoException();
                    parcel2.writeInt(cdmaRoamingMode);
                    return true;
                case 199:
                    int i104 = parcel.readInt();
                    int i105 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean cdmaRoamingMode2 = setCdmaRoamingMode(i104, i105);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cdmaRoamingMode2);
                    return true;
                case 200:
                    int i106 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int cdmaSubscriptionMode = getCdmaSubscriptionMode(i106);
                    parcel2.writeNoException();
                    parcel2.writeInt(cdmaSubscriptionMode);
                    return true;
                case 201:
                    int i107 = parcel.readInt();
                    int i108 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean cdmaSubscriptionMode2 = setCdmaSubscriptionMode(i107, i108);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cdmaSubscriptionMode2);
                    return true;
                case 202:
                    return onTransact$setCarrierTestOverride$(parcel, parcel2);
                case 203:
                    return onTransact$setCarrierServicePackageOverride$(parcel, parcel2);
                case 204:
                    int i109 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int carrierIdListVersion = getCarrierIdListVersion(i109);
                    parcel2.writeNoException();
                    parcel2.writeInt(carrierIdListVersion);
                    return true;
                case 205:
                    int i110 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    refreshUiccProfile(i110);
                    parcel2.writeNoException();
                    return true;
                case 206:
                    return onTransact$getNumberOfModemsWithSimultaneousDataConnections$(parcel, parcel2);
                case 207:
                    int i111 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int networkSelectionMode = getNetworkSelectionMode(i111);
                    parcel2.writeNoException();
                    parcel2.writeInt(networkSelectionMode);
                    return true;
                case 208:
                    boolean zIsInEmergencySmsMode = isInEmergencySmsMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInEmergencySmsMode);
                    return true;
                case 209:
                    return onTransact$getRadioPowerState$(parcel, parcel2);
                case 210:
                    int i112 = parcel.readInt();
                    IImsRegistrationCallback iImsRegistrationCallbackAsInterface = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerImsRegistrationCallback(i112, iImsRegistrationCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 211:
                    int i113 = parcel.readInt();
                    IImsRegistrationCallback iImsRegistrationCallbackAsInterface2 = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsRegistrationCallback(i113, iImsRegistrationCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 212:
                    int i114 = parcel.readInt();
                    IImsRegistrationCallback iImsRegistrationCallbackAsInterface3 = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerImsEmergencyRegistrationCallback(i114, iImsRegistrationCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 213:
                    int i115 = parcel.readInt();
                    IImsRegistrationCallback iImsRegistrationCallbackAsInterface4 = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsEmergencyRegistrationCallback(i115, iImsRegistrationCallbackAsInterface4);
                    parcel2.writeNoException();
                    return true;
                case 214:
                    return onTransact$getImsMmTelRegistrationState$(parcel, parcel2);
                case 215:
                    return onTransact$getImsMmTelRegistrationTransportType$(parcel, parcel2);
                case 216:
                    return onTransact$registerMmTelCapabilityCallback$(parcel, parcel2);
                case 217:
                    return onTransact$unregisterMmTelCapabilityCallback$(parcel, parcel2);
                case 218:
                    return onTransact$isCapable$(parcel, parcel2);
                case 219:
                    return onTransact$isAvailable$(parcel, parcel2);
                case 220:
                    return onTransact$isMmTelCapabilitySupported$(parcel, parcel2);
                case 221:
                    int i116 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAdvancedCallingSettingEnabled = isAdvancedCallingSettingEnabled(i116);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAdvancedCallingSettingEnabled);
                    return true;
                case 222:
                    return onTransact$setAdvancedCallingSettingEnabled$(parcel, parcel2);
                case 223:
                    int i117 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVtSettingEnabled = isVtSettingEnabled(i117);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVtSettingEnabled);
                    return true;
                case 224:
                    return onTransact$setVtSettingEnabled$(parcel, parcel2);
                case 225:
                    int i118 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVoWiFiSettingEnabled = isVoWiFiSettingEnabled(i118);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVoWiFiSettingEnabled);
                    return true;
                case 226:
                    return onTransact$setVoWiFiSettingEnabled$(parcel, parcel2);
                case 227:
                    int i119 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCrossSimCallingEnabledByUser = isCrossSimCallingEnabledByUser(i119);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCrossSimCallingEnabledByUser);
                    return true;
                case 228:
                    return onTransact$setCrossSimCallingEnabled$(parcel, parcel2);
                case 229:
                    int i120 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVoWiFiRoamingSettingEnabled = isVoWiFiRoamingSettingEnabled(i120);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVoWiFiRoamingSettingEnabled);
                    return true;
                case 230:
                    return onTransact$setVoWiFiRoamingSettingEnabled$(parcel, parcel2);
                case 231:
                    return onTransact$setVoWiFiNonPersistent$(parcel, parcel2);
                case 232:
                    int i121 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int voWiFiModeSetting = getVoWiFiModeSetting(i121);
                    parcel2.writeNoException();
                    parcel2.writeInt(voWiFiModeSetting);
                    return true;
                case 233:
                    return onTransact$setVoWiFiModeSetting$(parcel, parcel2);
                case 234:
                    int i122 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int voWiFiRoamingModeSetting = getVoWiFiRoamingModeSetting(i122);
                    parcel2.writeNoException();
                    parcel2.writeInt(voWiFiRoamingModeSetting);
                    return true;
                case 235:
                    return onTransact$setVoWiFiRoamingModeSetting$(parcel, parcel2);
                case 236:
                    return onTransact$setRttCapabilitySetting$(parcel, parcel2);
                case 237:
                    int i123 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsTtyOverVolteEnabled = isTtyOverVolteEnabled(i123);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTtyOverVolteEnabled);
                    return true;
                case 238:
                    return onTransact$getEmergencyNumberList$(parcel, parcel2);
                case 239:
                    return onTransact$isEmergencyNumber$(parcel, parcel2);
                case 240:
                    int i124 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> certsFromCarrierPrivilegeAccessRules = getCertsFromCarrierPrivilegeAccessRules(i124);
                    parcel2.writeNoException();
                    parcel2.writeStringList(certsFromCarrierPrivilegeAccessRules);
                    return true;
                case 241:
                    return onTransact$registerImsProvisioningChangedCallback$(parcel, parcel2);
                case 242:
                    return onTransact$unregisterImsProvisioningChangedCallback$(parcel, parcel2);
                case 243:
                    return onTransact$registerFeatureProvisioningChangedCallback$(parcel, parcel2);
                case 244:
                    return onTransact$unregisterFeatureProvisioningChangedCallback$(parcel, parcel2);
                case 245:
                    return onTransact$setImsProvisioningStatusForCapability$(parcel, parcel2);
                case 246:
                    return onTransact$getImsProvisioningStatusForCapability$(parcel, parcel2);
                case 247:
                    return onTransact$getRcsProvisioningStatusForCapability$(parcel, parcel2);
                case 248:
                    return onTransact$setRcsProvisioningStatusForCapability$(parcel, parcel2);
                case 249:
                    return onTransact$getImsProvisioningInt$(parcel, parcel2);
                case 250:
                    return onTransact$getImsProvisioningString$(parcel, parcel2);
                case 251:
                    return onTransact$setImsProvisioningInt$(parcel, parcel2);
                case 252:
                    return onTransact$setImsProvisioningString$(parcel, parcel2);
                case 253:
                    startEmergencyCallbackMode();
                    parcel2.writeNoException();
                    return true;
                case 254:
                    return onTransact$updateEmergencyNumberListTestMode$(parcel, parcel2);
                case 255:
                    List<String> emergencyNumberListTestMode = getEmergencyNumberListTestMode();
                    parcel2.writeNoException();
                    parcel2.writeStringList(emergencyNumberListTestMode);
                    return true;
                case 256:
                    int i125 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int emergencyNumberDbVersion = getEmergencyNumberDbVersion(i125);
                    parcel2.writeNoException();
                    parcel2.writeInt(emergencyNumberDbVersion);
                    return true;
                case 257:
                    notifyOtaEmergencyNumberDbInstalled();
                    parcel2.writeNoException();
                    return true;
                case 258:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateOtaEmergencyNumberDbFilePath(parcelFileDescriptor);
                    parcel2.writeNoException();
                    return true;
                case 259:
                    resetOtaEmergencyNumberDbFilePath();
                    parcel2.writeNoException();
                    return true;
                case 260:
                    return onTransact$enableModemForSlot$(parcel, parcel2);
                case 261:
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMultiSimCarrierRestriction(z9);
                    parcel2.writeNoException();
                    return true;
                case 262:
                    return onTransact$isMultiSimSupported$(parcel, parcel2);
                case 263:
                    int i126 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    switchMultiSimConfig(i126);
                    parcel2.writeNoException();
                    return true;
                case 264:
                    return onTransact$doesSwitchMultiSimConfigTriggerReboot$(parcel, parcel2);
                case 265:
                    String string56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<UiccSlotMapping> slotsMapping = getSlotsMapping(string56);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(slotsMapping, 1);
                    return true;
                case 266:
                    int radioHalVersion = getRadioHalVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(radioHalVersion);
                    return true;
                case 267:
                    int i127 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int halVersion = getHalVersion(i127);
                    parcel2.writeNoException();
                    parcel2.writeInt(halVersion);
                    return true;
                case 268:
                    String currentPackageName = getCurrentPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(currentPackageName);
                    return true;
                case 269:
                    return onTransact$isApplicationOnUicc$(parcel, parcel2);
                case 270:
                    return onTransact$isModemEnabledForSlot$(parcel, parcel2);
                case 271:
                    return onTransact$isDataEnabledForApn$(parcel, parcel2);
                case 272:
                    return onTransact$isApnMetered$(parcel, parcel2);
                case 273:
                    return onTransact$setSystemSelectionChannels$(parcel, parcel2);
                case 274:
                    int i128 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<RadioAccessSpecifier> systemSelectionChannels = getSystemSelectionChannels(i128);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(systemSelectionChannels, 1);
                    return true;
                case 275:
                    return onTransact$isMvnoMatched$(parcel, parcel2);
                case 276:
                    return onTransact$enqueueSmsPickResult$(parcel, parcel2);
                case 277:
                    showSwitchToManagedProfileDialog();
                    return true;
                case 278:
                    int i129 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String mmsUserAgent = getMmsUserAgent(i129);
                    parcel2.writeNoException();
                    parcel2.writeString(mmsUserAgent);
                    return true;
                case 279:
                    int i130 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String mmsUAProfUrl = getMmsUAProfUrl(i130);
                    parcel2.writeNoException();
                    parcel2.writeString(mmsUAProfUrl);
                    return true;
                case 280:
                    return onTransact$setMobileDataPolicyEnabled$(parcel, parcel2);
                case 281:
                    return onTransact$isMobileDataPolicyEnabled$(parcel, parcel2);
                case 282:
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCepEnabled(z10);
                    return true;
                case 283:
                    return onTransact$notifyRcsAutoConfigurationReceived$(parcel, parcel2);
                case 284:
                    int i131 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsIccLockEnabled = isIccLockEnabled(i131);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIccLockEnabled);
                    return true;
                case 285:
                    return onTransact$setIccLockEnabled$(parcel, parcel2);
                case 286:
                    return onTransact$changeIccLockPassword$(parcel, parcel2);
                case 287:
                    requestUserActivityNotification();
                    return true;
                case 288:
                    userActivity();
                    return true;
                case 289:
                    int i132 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String manualNetworkSelectionPlmn = getManualNetworkSelectionPlmn(i132);
                    parcel2.writeNoException();
                    parcel2.writeString(manualNetworkSelectionPlmn);
                    return true;
                case 290:
                    boolean zCanConnectTo5GInDsdsMode = canConnectTo5GInDsdsMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanConnectTo5GInDsdsMode);
                    return true;
                case 291:
                    return onTransact$getEquivalentHomePlmns$(parcel, parcel2);
                case 292:
                    return onTransact$setVoNrEnabled$(parcel, parcel2);
                case 293:
                    int i133 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVoNrEnabled = isVoNrEnabled(i133);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVoNrEnabled);
                    return true;
                case 294:
                    return onTransact$setNrDualConnectivityState$(parcel, parcel2);
                case 295:
                    int i134 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsNrDualConnectivityEnabled = isNrDualConnectivityEnabled(i134);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNrDualConnectivityEnabled);
                    return true;
                case 296:
                    String string57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsRadioInterfaceCapabilitySupported = isRadioInterfaceCapabilitySupported(string57);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRadioInterfaceCapabilitySupported);
                    return true;
                case 297:
                    return onTransact$sendThermalMitigationRequest$(parcel, parcel2);
                case 298:
                    return onTransact$bootstrapAuthenticationRequest$(parcel, parcel2);
                case 299:
                    return onTransact$setBoundGbaServiceOverride$(parcel, parcel2);
                case 300:
                    int i135 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String boundGbaService = getBoundGbaService(i135);
                    parcel2.writeNoException();
                    parcel2.writeString(boundGbaService);
                    return true;
                case 301:
                    return onTransact$setGbaReleaseTimeOverride$(parcel, parcel2);
                case 302:
                    int i136 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int gbaReleaseTime = getGbaReleaseTime(i136);
                    parcel2.writeNoException();
                    parcel2.writeInt(gbaReleaseTime);
                    return true;
                case 303:
                    return onTransact$setRcsClientConfiguration$(parcel, parcel2);
                case 304:
                    int i137 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsRcsVolteSingleRegistrationCapable = isRcsVolteSingleRegistrationCapable(i137);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRcsVolteSingleRegistrationCapable);
                    return true;
                case 305:
                    return onTransact$registerRcsProvisioningCallback$(parcel, parcel2);
                case 306:
                    return onTransact$unregisterRcsProvisioningCallback$(parcel, parcel2);
                case 307:
                    int i138 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    triggerRcsReconfiguration(i138);
                    parcel2.writeNoException();
                    return true;
                case 308:
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRcsSingleRegistrationTestModeEnabled(z11);
                    parcel2.writeNoException();
                    return true;
                case 309:
                    boolean rcsSingleRegistrationTestModeEnabled = getRcsSingleRegistrationTestModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(rcsSingleRegistrationTestModeEnabled);
                    return true;
                case 310:
                    String string58 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setDeviceSingleRegistrationEnabledOverride(string58);
                    parcel2.writeNoException();
                    return true;
                case 311:
                    boolean deviceSingleRegistrationEnabled = getDeviceSingleRegistrationEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deviceSingleRegistrationEnabled);
                    return true;
                case 312:
                    return onTransact$setCarrierSingleRegistrationEnabledOverride$(parcel, parcel2);
                case 313:
                    return onTransact$sendDeviceToDeviceMessage$(parcel, parcel2);
                case 314:
                    String string59 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setActiveDeviceToDeviceTransport(string59);
                    parcel2.writeNoException();
                    return true;
                case 315:
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceToDeviceForceEnabled(z12);
                    parcel2.writeNoException();
                    return true;
                case 316:
                    int i139 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean carrierSingleRegistrationEnabled = getCarrierSingleRegistrationEnabled(i139);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(carrierSingleRegistrationEnabled);
                    return true;
                case 317:
                    return onTransact$setImsFeatureValidationOverride$(parcel, parcel2);
                case 318:
                    int i140 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean imsFeatureValidationOverride = getImsFeatureValidationOverride(i140);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(imsFeatureValidationOverride);
                    return true;
                case 319:
                    String mobileProvisioningUrl = getMobileProvisioningUrl();
                    parcel2.writeNoException();
                    parcel2.writeString(mobileProvisioningUrl);
                    return true;
                case 320:
                    return onTransact$removeContactFromEab$(parcel, parcel2);
                case 321:
                    String string60 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String contactFromEab = getContactFromEab(string60);
                    parcel2.writeNoException();
                    parcel2.writeString(contactFromEab);
                    return true;
                case 322:
                    String string61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String capabilityFromEab = getCapabilityFromEab(string61);
                    parcel2.writeNoException();
                    parcel2.writeString(capabilityFromEab);
                    return true;
                case 323:
                    boolean deviceUceEnabled = getDeviceUceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deviceUceEnabled);
                    return true;
                case 324:
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceUceEnabled(z13);
                    parcel2.writeNoException();
                    return true;
                case 325:
                    return onTransact$addUceRegistrationOverrideShell$(parcel, parcel2);
                case 326:
                    return onTransact$removeUceRegistrationOverrideShell$(parcel, parcel2);
                case 327:
                    int i141 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    RcsContactUceCapability rcsContactUceCapabilityClearUceRegistrationOverrideShell = clearUceRegistrationOverrideShell(i141);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rcsContactUceCapabilityClearUceRegistrationOverrideShell, 1);
                    return true;
                case 328:
                    int i142 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    RcsContactUceCapability latestRcsContactUceCapabilityShell = getLatestRcsContactUceCapabilityShell(i142);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(latestRcsContactUceCapabilityShell, 1);
                    return true;
                case 329:
                    int i143 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String lastUcePidfXmlShell = getLastUcePidfXmlShell(i143);
                    parcel2.writeNoException();
                    parcel2.writeString(lastUcePidfXmlShell);
                    return true;
                case 330:
                    int i144 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveUceRequestDisallowedStatus = removeUceRequestDisallowedStatus(i144);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveUceRequestDisallowedStatus);
                    return true;
                case 331:
                    return onTransact$setCapabilitiesRequestTimeout$(parcel, parcel2);
                case 332:
                    return onTransact$setSignalStrengthUpdateRequest$(parcel, parcel2);
                case 333:
                    return onTransact$clearSignalStrengthUpdateRequest$(parcel, parcel2);
                case 334:
                    PhoneCapability phoneCapability = getPhoneCapability();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(phoneCapability, 1);
                    return true;
                case 335:
                    int iPrepareForUnattendedReboot = prepareForUnattendedReboot();
                    parcel2.writeNoException();
                    parcel2.writeInt(iPrepareForUnattendedReboot);
                    return true;
                case 336:
                    ResultReceiver resultReceiver2 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSlicingConfig(resultReceiver2);
                    parcel2.writeNoException();
                    return true;
                case 337:
                    return onTransact$isPremiumCapabilityAvailableForPurchase$(parcel, parcel2);
                case 338:
                    return onTransact$purchasePremiumCapability$(parcel, parcel2);
                case 339:
                    return onTransact$registerImsStateCallback$(parcel, parcel2);
                case 340:
                    IImsStateCallback iImsStateCallbackAsInterface = IImsStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsStateCallback(iImsStateCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 341:
                    return onTransact$getLastKnownCellIdentity$(parcel, parcel2);
                case 342:
                    String string62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean modemService = setModemService(string62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(modemService);
                    return true;
                case 343:
                    String modemService2 = getModemService();
                    parcel2.writeNoException();
                    parcel2.writeString(modemService2);
                    return true;
                case 344:
                    return onTransact$isProvisioningRequiredForCapability$(parcel, parcel2);
                case 345:
                    return onTransact$isRcsProvisioningRequiredForCapability$(parcel, parcel2);
                case 346:
                    return onTransact$setVoiceServiceStateOverride$(parcel, parcel2);
                case 347:
                    int i145 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String carrierServicePackageNameForLogicalSlot = getCarrierServicePackageNameForLogicalSlot(i145);
                    parcel2.writeNoException();
                    parcel2.writeString(carrierServicePackageNameForLogicalSlot);
                    return true;
                case 348:
                    return onTransact$setRemovableEsimAsDefaultEuicc$(parcel, parcel2);
                case 349:
                    String string63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsRemovableEsimDefaultEuicc = isRemovableEsimDefaultEuicc(string63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRemovableEsimDefaultEuicc);
                    return true;
                case 350:
                    return onTransact$getDefaultRespondViaMessageApplication$(parcel, parcel2);
                case 351:
                    int i146 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int simStateForSlotIndex = getSimStateForSlotIndex(i146);
                    parcel2.writeNoException();
                    parcel2.writeInt(simStateForSlotIndex);
                    return true;
                case 352:
                    return onTransact$persistEmergencyCallDiagnosticData$(parcel, parcel2);
                case 353:
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNullCipherAndIntegrityEnabled(z14);
                    parcel2.writeNoException();
                    return true;
                case 354:
                    boolean zIsNullCipherAndIntegrityPreferenceEnabled = isNullCipherAndIntegrityPreferenceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNullCipherAndIntegrityPreferenceEnabled);
                    return true;
                case 355:
                    int i147 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<CellBroadcastIdRange> cellBroadcastIdRanges = getCellBroadcastIdRanges(i147);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(cellBroadcastIdRanges, 1);
                    return true;
                case 356:
                    return onTransact$setCellBroadcastIdRanges$(parcel, parcel2);
                case 357:
                    boolean zIsDomainSelectionSupported = isDomainSelectionSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDomainSelectionSupported);
                    return true;
                case 358:
                    return onTransact$getCarrierRestrictionStatus$(parcel, parcel2);
                case 359:
                    return onTransact$requestSatelliteEnabled$(parcel, parcel2);
                case 360:
                    ResultReceiver resultReceiver3 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteEnabled(resultReceiver3);
                    parcel2.writeNoException();
                    return true;
                case 361:
                    ResultReceiver resultReceiver4 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestIsDemoModeEnabled(resultReceiver4);
                    parcel2.writeNoException();
                    return true;
                case 362:
                    ResultReceiver resultReceiver5 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestIsEmergencyModeEnabled(resultReceiver5);
                    parcel2.writeNoException();
                    return true;
                case 363:
                    ResultReceiver resultReceiver6 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteSupported(resultReceiver6);
                    parcel2.writeNoException();
                    return true;
                case 364:
                    ResultReceiver resultReceiver7 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestSatelliteCapabilities(resultReceiver7);
                    parcel2.writeNoException();
                    return true;
                case 365:
                    return onTransact$startSatelliteTransmissionUpdates$(parcel, parcel2);
                case 366:
                    return onTransact$stopSatelliteTransmissionUpdates$(parcel, parcel2);
                case 367:
                    return onTransact$provisionSatelliteService$(parcel, parcel2);
                case 368:
                    return onTransact$deprovisionSatelliteService$(parcel, parcel2);
                case 369:
                    ISatelliteProvisionStateCallback iSatelliteProvisionStateCallbackAsInterface = ISatelliteProvisionStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRegisterForSatelliteProvisionStateChanged = registerForSatelliteProvisionStateChanged(iSatelliteProvisionStateCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterForSatelliteProvisionStateChanged);
                    return true;
                case 370:
                    ISatelliteProvisionStateCallback iSatelliteProvisionStateCallbackAsInterface2 = ISatelliteProvisionStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForSatelliteProvisionStateChanged(iSatelliteProvisionStateCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 371:
                    ResultReceiver resultReceiver8 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteProvisioned(resultReceiver8);
                    parcel2.writeNoException();
                    return true;
                case 372:
                    ISatelliteModemStateCallback iSatelliteModemStateCallbackAsInterface = ISatelliteModemStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRegisterForSatelliteModemStateChanged = registerForSatelliteModemStateChanged(iSatelliteModemStateCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterForSatelliteModemStateChanged);
                    return true;
                case 373:
                    ISatelliteModemStateCallback iSatelliteModemStateCallbackAsInterface2 = ISatelliteModemStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForModemStateChanged(iSatelliteModemStateCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 374:
                    ISatelliteDatagramCallback iSatelliteDatagramCallbackAsInterface = ISatelliteDatagramCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRegisterForIncomingDatagram = registerForIncomingDatagram(iSatelliteDatagramCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterForIncomingDatagram);
                    return true;
                case 375:
                    ISatelliteDatagramCallback iSatelliteDatagramCallbackAsInterface2 = ISatelliteDatagramCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForIncomingDatagram(iSatelliteDatagramCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 376:
                    IIntegerConsumer iIntegerConsumerAsInterface3 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    pollPendingDatagrams(iIntegerConsumerAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 377:
                    return onTransact$sendDatagram$(parcel, parcel2);
                case 378:
                    int[] satelliteDisallowedReasons = getSatelliteDisallowedReasons();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(satelliteDisallowedReasons);
                    return true;
                case 379:
                    ISatelliteDisallowedReasonsCallback iSatelliteDisallowedReasonsCallbackAsInterface = ISatelliteDisallowedReasonsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForSatelliteDisallowedReasonsChanged(iSatelliteDisallowedReasonsCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 380:
                    ISatelliteDisallowedReasonsCallback iSatelliteDisallowedReasonsCallbackAsInterface2 = ISatelliteDisallowedReasonsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForSatelliteDisallowedReasonsChanged(iSatelliteDisallowedReasonsCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 381:
                    return onTransact$requestIsCommunicationAllowedForCurrentLocation$(parcel, parcel2);
                case 382:
                    ResultReceiver resultReceiver9 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestSatelliteAccessConfigurationForCurrentLocation(resultReceiver9);
                    parcel2.writeNoException();
                    return true;
                case 383:
                    ResultReceiver resultReceiver10 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestTimeForNextSatelliteVisibility(resultReceiver10);
                    parcel2.writeNoException();
                    return true;
                case 384:
                    ResultReceiver resultReceiver11 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestSelectedNbIotSatelliteSubscriptionId(resultReceiver11);
                    parcel2.writeNoException();
                    return true;
                case 385:
                    ISelectedNbIotSatelliteSubscriptionCallback iSelectedNbIotSatelliteSubscriptionCallbackAsInterface = ISelectedNbIotSatelliteSubscriptionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRegisterForSelectedNbIotSatelliteSubscriptionChanged = registerForSelectedNbIotSatelliteSubscriptionChanged(iSelectedNbIotSatelliteSubscriptionCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterForSelectedNbIotSatelliteSubscriptionChanged);
                    return true;
                case 386:
                    ISelectedNbIotSatelliteSubscriptionCallback iSelectedNbIotSatelliteSubscriptionCallbackAsInterface2 = ISelectedNbIotSatelliteSubscriptionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForSelectedNbIotSatelliteSubscriptionChanged(iSelectedNbIotSatelliteSubscriptionCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 387:
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceAlignedWithSatellite(z15);
                    parcel2.writeNoException();
                    return true;
                case 388:
                    return onTransact$setSatelliteServicePackageName$(parcel, parcel2);
                case 389:
                    String string64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean satelliteGatewayServicePackageName = setSatelliteGatewayServicePackageName(string64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(satelliteGatewayServicePackageName);
                    return true;
                case 390:
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean satelliteListeningTimeoutDuration = setSatelliteListeningTimeoutDuration(j);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(satelliteListeningTimeoutDuration);
                    return true;
                case 391:
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean satelliteIgnoreCellularServiceState = setSatelliteIgnoreCellularServiceState(z16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(satelliteIgnoreCellularServiceState);
                    return true;
                case 392:
                    return onTransact$setSupportDisableSatelliteWhileEnableInProgress$(parcel, parcel2);
                case 393:
                    return onTransact$setSatellitePointingUiClassName$(parcel, parcel2);
                case 394:
                    return onTransact$setDatagramControllerTimeoutDuration$(parcel, parcel2);
                case 395:
                    return onTransact$setSatelliteControllerTimeoutDuration$(parcel, parcel2);
                case 396:
                    return onTransact$setEmergencyCallToSatelliteHandoverType$(parcel, parcel2);
                case 397:
                    return onTransact$setCountryCodes$(parcel, parcel2);
                case 398:
                    return onTransact$setSatelliteAccessControlOverlayConfigs$(parcel, parcel2);
                case 399:
                    String string65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean satelliteAccessAllowedForSubscriptions = setSatelliteAccessAllowedForSubscriptions(string65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(satelliteAccessAllowedForSubscriptions);
                    return true;
                case 400:
                    return onTransact$setTnScanningSupport$(parcel, parcel2);
                case 401:
                    return onTransact$setOemEnabledSatelliteProvisionStatus$(parcel, parcel2);
                case 402:
                    return onTransact$overrideConfigDataVersion$(parcel, parcel2);
                case 403:
                    return onTransact$getShaIdFromAllowList$(parcel, parcel2);
                case 404:
                    return onTransact$addAttachRestrictionForCarrier$(parcel, parcel2);
                case 405:
                    return onTransact$removeAttachRestrictionForCarrier$(parcel, parcel2);
                case 406:
                    int i148 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] attachRestrictionReasonsForCarrier = getAttachRestrictionReasonsForCarrier(i148);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(attachRestrictionReasonsForCarrier);
                    return true;
                case 407:
                    ResultReceiver resultReceiver12 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestNtnSignalStrength(resultReceiver12);
                    parcel2.writeNoException();
                    return true;
                case 408:
                    INtnSignalStrengthCallback iNtnSignalStrengthCallbackAsInterface = INtnSignalStrengthCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForNtnSignalStrengthChanged(iNtnSignalStrengthCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 409:
                    INtnSignalStrengthCallback iNtnSignalStrengthCallbackAsInterface2 = INtnSignalStrengthCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForNtnSignalStrengthChanged(iNtnSignalStrengthCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 410:
                    ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallbackAsInterface = ISatelliteCapabilitiesCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRegisterForCapabilitiesChanged = registerForCapabilitiesChanged(iSatelliteCapabilitiesCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterForCapabilitiesChanged);
                    return true;
                case 411:
                    ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallbackAsInterface2 = ISatelliteCapabilitiesCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForCapabilitiesChanged(iSatelliteCapabilitiesCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 412:
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean shouldSendDatagramToModemInDemoMode = setShouldSendDatagramToModemInDemoMode(z17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldSendDatagramToModemInDemoMode);
                    return true;
                case 413:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean domainSelectionServiceOverride = setDomainSelectionServiceOverride(componentName);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(domainSelectionServiceOverride);
                    return true;
                case 414:
                    boolean zClearDomainSelectionServiceOverride = clearDomainSelectionServiceOverride();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearDomainSelectionServiceOverride);
                    return true;
                case 415:
                    boolean zIsAospDomainSelectionService = isAospDomainSelectionService();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAospDomainSelectionService);
                    return true;
                case 416:
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEnableCellularIdentifierDisclosureNotifications(z18);
                    parcel2.writeNoException();
                    return true;
                case 417:
                    boolean zIsCellularIdentifierDisclosureNotificationsEnabled = isCellularIdentifierDisclosureNotificationsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCellularIdentifierDisclosureNotificationsEnabled);
                    return true;
                case 418:
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNullCipherNotificationsEnabled(z19);
                    parcel2.writeNoException();
                    return true;
                case 419:
                    boolean zIsNullCipherNotificationsEnabled = isNullCipherNotificationsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNullCipherNotificationsEnabled);
                    return true;
                case 420:
                    int i149 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> satellitePlmnsForCarrier = getSatellitePlmnsForCarrier(i149);
                    parcel2.writeNoException();
                    parcel2.writeStringList(satellitePlmnsForCarrier);
                    return true;
                case 421:
                    IBooleanConsumer iBooleanConsumerAsInterface = IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRegisterForSatelliteSupportedStateChanged = registerForSatelliteSupportedStateChanged(iBooleanConsumerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterForSatelliteSupportedStateChanged);
                    return true;
                case 422:
                    IBooleanConsumer iBooleanConsumerAsInterface2 = IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForSatelliteSupportedStateChanged(iBooleanConsumerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 423:
                    return onTransact$registerForCommunicationAccessStateChanged$(parcel, parcel2);
                case 424:
                    return onTransact$unregisterForCommunicationAccessStateChanged$(parcel, parcel2);
                case 425:
                    return onTransact$setDatagramControllerBooleanConfig$(parcel, parcel2);
                case 426:
                    String string66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSatelliteCommunicationAllowedForCurrentLocationCache = setIsSatelliteCommunicationAllowedForCurrentLocationCache(string66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSatelliteCommunicationAllowedForCurrentLocationCache);
                    return true;
                case 427:
                    return onTransact$requestSatelliteSessionStats$(parcel, parcel2);
                case 428:
                    ResultReceiver resultReceiver13 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestSatelliteSubscriberProvisionStatus(resultReceiver13);
                    parcel2.writeNoException();
                    return true;
                case 429:
                    ResultReceiver resultReceiver14 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestSatelliteDisplayName(resultReceiver14);
                    parcel2.writeNoException();
                    return true;
                case 430:
                    return onTransact$provisionSatellite$(parcel, parcel2);
                case 431:
                    String string67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean satelliteSubscriberIdListChangedIntentComponent = setSatelliteSubscriberIdListChangedIntentComponent(string67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(satelliteSubscriberIdListChangedIntentComponent);
                    return true;
                case 432:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTestEuiccUiComponent(componentName2);
                    parcel2.writeNoException();
                    return true;
                case 433:
                    ComponentName testEuiccUiComponent = getTestEuiccUiComponent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(testEuiccUiComponent, 1);
                    return true;
                case 434:
                    return onTransact$overrideCarrierRoamingNtnEligibilityChanged$(parcel, parcel2);
                case 435:
                    return onTransact$deprovisionSatellite$(parcel, parcel2);
                case 436:
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNtnSmsSupported(z20);
                    parcel2.writeNoException();
                    return true;
                case 437:
                    CarrierIdentifier carrierIdentifier = (CarrierIdentifier) parcel.readTypedObject(CarrierIdentifier.CREATOR);
                    parcel.enforceNoDataAvail();
                    int carrierIdFromIdentifier = getCarrierIdFromIdentifier(carrierIdentifier);
                    parcel2.writeNoException();
                    parcel2.writeInt(carrierIdFromIdentifier);
                    return true;
                case 438:
                    List<String> satelliteDataOptimizedApps = getSatelliteDataOptimizedApps();
                    parcel2.writeNoException();
                    parcel2.writeStringList(satelliteDataOptimizedApps);
                    return true;
                case 439:
                    int i150 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int satelliteDataSupportMode = getSatelliteDataSupportMode(i150);
                    parcel2.writeNoException();
                    parcel2.writeInt(satelliteDataSupportMode);
                    return true;
                case 440:
                    boolean z21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean satelliteIgnorePlmnListFromStorage = setSatelliteIgnorePlmnListFromStorage(z21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(satelliteIgnorePlmnListFromStorage);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITelephony {
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

            @Override // com.android.internal.telephony.ITelephony
            public void dial(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void call(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRadioOn(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRadioOnWithFeature(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRadioOnForSubscriber(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRadioOnForSubscriberWithFeature(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCallComposerStatus(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCallComposerStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean supplyPinForSubscriber(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean supplyPukForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int[] supplyPinReportResultForSubscriber(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int[] supplyPukReportResultForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean handlePinMmi(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void handleUssdRequest(int i, String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean handlePinMmiForSubscriber(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void toggleRadioOnOff() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void toggleRadioOnOffForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setRadio(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setRadioForSubscriber(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setRadioPower(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean requestRadioPowerOffForReason(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean clearRadioPowerOffForReason(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List getRadioPowerOffReasons(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void updateServiceLocation() throws RemoteException {
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

            @Override // com.android.internal.telephony.ITelephony
            public void updateServiceLocationWithPackageName(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void enableLocationUpdates() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void disableLocationUpdates() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean enableDataConnectivity(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean disableDataConnectivity(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isDataConnectivityPossible(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public CellIdentity getCellLocation(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CellIdentity) parcelObtain2.readTypedObject(CellIdentity.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getNetworkCountryIsoForPhone(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<NeighboringCellInfo> getNeighboringCellInfo(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(NeighboringCellInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCallState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCallStateForSubscription(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getDataActivity() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getDataActivityForSubId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getDataState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getDataStateForSubId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getActivePhoneType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getActivePhoneTypeForSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCdmaEriIconIndex(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCdmaEriIconIndexForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCdmaEriIconMode(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCdmaEriIconModeForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getCdmaEriText(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getCdmaEriTextForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean needsOtaServiceProvisioning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setVoiceMailNumber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoiceActivationState(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setDataActivationState(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getVoiceActivationState(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getDataActivationState(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getVoiceMessageCountForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isConcurrentVoiceAndDataAllowed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public Bundle getVisualVoicemailSettings(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getVisualVoicemailPackageName(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void enableVisualVoicemailSmsFilter(String str, int i, VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(visualVoicemailSmsFilterSettings, 0);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void disableVisualVoicemailSmsFilter(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(59, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VisualVoicemailSmsFilterSettings) parcelObtain2.readTypedObject(VisualVoicemailSmsFilterSettings.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VisualVoicemailSmsFilterSettings) parcelObtain2.readTypedObject(VisualVoicemailSmsFilterSettings.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void sendVisualVoicemailSmsForSubscriber(String str, String str2, int i, String str3, int i2, String str4, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void sendDialerSpecialCode(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getNetworkTypeForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getDataNetworkType(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getDataNetworkTypeForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getVoiceNetworkTypeForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean hasIccCard() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean hasIccCardUsingSlotIndex(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getLteOnCdmaMode(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getLteOnCdmaModeForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<CellInfo> getAllCellInfo(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(CellInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestCellInfoUpdate(int i, ICellInfoCallback iCellInfoCallback, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iCellInfoCallback);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestCellInfoUpdateWithWorkSource(int i, ICellInfoCallback iCellInfoCallback, String str, String str2, WorkSource workSource) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iCellInfoCallback);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCellInfoListRate(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public IccOpenLogicalChannelResponse iccOpenLogicalChannel(IccLogicalChannelRequest iccLogicalChannelRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(iccLogicalChannelRequest, 0);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (IccOpenLogicalChannelResponse) parcelObtain2.readTypedObject(IccOpenLogicalChannelResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean iccCloseLogicalChannel(IccLogicalChannelRequest iccLogicalChannelRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(iccLogicalChannelRequest, 0);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String iccTransmitApduLogicalChannelByPort(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    parcelObtain.writeInt(i7);
                    parcelObtain.writeInt(i8);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String iccTransmitApduLogicalChannel(int i, int i2, int i3, int i4, int i5, int i6, int i7, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    parcelObtain.writeInt(i7);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String iccTransmitApduBasicChannelByPort(int i, int i2, String str, int i3, int i4, int i5, int i6, int i7, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    parcelObtain.writeInt(i7);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String iccTransmitApduBasicChannel(int i, String str, int i2, int i3, int i4, int i5, int i6, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public byte[] iccExchangeSimIO(int i, int i2, int i3, int i4, int i5, int i6, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String sendEnvelopeWithStatus(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String nvReadItem(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean nvWriteItem(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean nvWriteCdmaPrl(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean resetModemConfig(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean rebootModem(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getAllowedNetworkTypesBitmask(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isTetheringApnRequiredForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void enableIms(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void disableIms(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void resetIms(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerMmTelFeatureCallback(int i, IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsServiceFeatureCallback);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterImsFeatureCallback(IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsServiceFeatureCallback);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public IImsRegistration getImsRegistration(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsRegistration.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public IImsConfig getImsConfig(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsConfig.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setBoundImsServiceOverride(int i, int i2, boolean z, int[] iArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean clearCarrierImsServiceOverride(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getBoundImsServicePackage(int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void getImsMmTelFeatureState(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setNetworkSelectionModeAutomatic(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public CellNetworkScanResult getCellNetworkScanResults(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CellNetworkScanResult) parcelObtain2.readTypedObject(CellNetworkScanResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int requestNetworkScan(int i, boolean z, NetworkScanRequest networkScanRequest, Messenger messenger, IBinder iBinder, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(networkScanRequest, 0);
                    parcelObtain.writeTypedObject(messenger, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void stopNetworkScan(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setNetworkSelectionModeManual(int i, OperatorInfo operatorInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(operatorInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public long getAllowedNetworkTypesForReason(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setAllowedNetworkTypesForReason(int i, int i2, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getDataEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isUserDataEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isDataEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setDataEnabledForReason(int i, int i2, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isDataEnabledForReason(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isManualNetworkSelectionAllowed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setImsRegistrationState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getCdmaMdn(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getCdmaMin(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestNumberVerification(PhoneNumberRange phoneNumberRange, long j, INumberVerificationCallback iNumberVerificationCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneNumberRange, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iNumberVerificationCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCarrierPrivilegeStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCarrierPrivilegeStatusForUid(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(120, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int checkCarrierPrivilegesForPackage(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(121, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int checkCarrierPrivilegesForPackageAnyPhone(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(122, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<String> getCarrierPackageNamesForIntentAndPhone(Intent intent, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(123, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setLine1NumberForDisplayForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(124, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getLine1NumberForDisplay(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(125, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getLine1AlphaTagForDisplay(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(126, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String[] getMergedSubscriberIds(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(127, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String[] getMergedImsisFromGroup(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(128, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setOperatorBrandOverride(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(129, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setRoamingOverride(int i, List<String> list, List<String> list2, List<String> list3, List<String> list4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeStringList(list3);
                    parcelObtain.writeStringList(list4);
                    this.mRemote.transact(130, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean needMobileRadioShutdown() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(131, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void shutdownMobileRadios() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(132, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getRadioAccessFamily(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(133, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void uploadCallComposerPicture(int i, String str, String str2, ParcelFileDescriptor parcelFileDescriptor, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(134, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void enableVideoCalling(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(135, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isVideoCallingEnabled(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(136, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean canChangeDtmfToneLength(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(137, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isWorldPhone(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(138, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isTtyModeSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(139, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRttSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(140, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isHearingAidCompatibilitySupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(141, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isImsRegistered(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(142, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isWifiCallingAvailable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(143, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isVideoTelephonyAvailable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(144, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getImsRegTechnologyForMmTel(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(145, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getDeviceId(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(146, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getDeviceIdWithFeature(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(147, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getImeiForSlot(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(148, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getPrimaryImei(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(149, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getTypeAllocationCodeForSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(150, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getMeidForSlot(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(151, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getManufacturerCodeForSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(152, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getDeviceSoftwareVersionForSlot(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(153, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getSubIdForPhoneAccountHandle(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(154, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(155, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PhoneAccountHandle) parcelObtain2.readTypedObject(PhoneAccountHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void factoryReset(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(156, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getSimLocaleForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(157, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestModemActivityInfo(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(158, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public ServiceState getServiceStateForSlot(int i, boolean z, boolean z2, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(159, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ServiceState) parcelObtain2.readTypedObject(ServiceState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public Uri getVoicemailRingtoneUri(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    this.mRemote.transact(160, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Uri) parcelObtain2.readTypedObject(Uri.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoicemailRingtoneUri(String str, PhoneAccountHandle phoneAccountHandle, Uri uri) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(161, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isVoicemailVibrationEnabled(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    this.mRemote.transact(162, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoicemailVibrationEnabled(String str, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(163, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<String> getPackagesWithCarrierPrivileges(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(164, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<String> getPackagesWithCarrierPrivilegesForAllPhones() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(165, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getAidForAppType(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(166, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getEsn(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(167, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getCdmaPrlVersion(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(168, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<TelephonyHistogram> getTelephonyHistograms() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(169, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(TelephonyHistogram.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int setAllowedCarriers(CarrierRestrictionRules carrierRestrictionRules) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(carrierRestrictionRules, 0);
                    this.mRemote.transact(170, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public CarrierRestrictionRules getAllowedCarriers() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(171, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CarrierRestrictionRules) parcelObtain2.readTypedObject(CarrierRestrictionRules.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getSubscriptionCarrierId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(172, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getSubscriptionCarrierName(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(173, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getSubscriptionSpecificCarrierId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(174, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getSubscriptionSpecificCarrierName(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(175, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCarrierIdFromMccMnc(int i, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(176, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void carrierActionSetRadioEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(177, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void carrierActionReportDefaultNetworkStatus(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(178, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void carrierActionResetAll(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(179, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void getCallForwarding(int i, int i2, ICallForwardingInfoCallback iCallForwardingInfoCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iCallForwardingInfoCallback);
                    this.mRemote.transact(180, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCallForwarding(int i, CallForwardingInfo callForwardingInfo, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(callForwardingInfo, 0);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(181, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void getCallWaitingStatus(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(182, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCallWaitingStatus(int i, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(183, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<ClientRequestStats> getClientRequestStats(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(184, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ClientRequestStats.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setSimPowerStateForSlot(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(185, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setSimPowerStateForSlotWithCallback(int i, int i2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(186, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String[] getForbiddenPlmns(int i, int i2, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(187, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int setForbiddenPlmns(int i, int i2, List<String> list, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(188, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getEmergencyCallbackMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(189, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public SignalStrength getSignalStrength(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(190, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SignalStrength) parcelObtain2.readTypedObject(SignalStrength.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCardIdForDefaultEuicc(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(191, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<UiccCardInfo> getUiccCardsInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(192, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(UiccCardInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public UiccSlotInfo[] getUiccSlotsInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(193, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UiccSlotInfo[]) parcelObtain2.createTypedArray(UiccSlotInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean switchSlots(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(194, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSimSlotMapping(List<UiccSlotMapping> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(195, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isDataRoamingEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(196, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setDataRoamingEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(197, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCdmaRoamingMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(198, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setCdmaRoamingMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(199, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCdmaSubscriptionMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(200, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setCdmaSubscriptionMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(201, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCarrierTestOverride(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeString(str6);
                    parcelObtain.writeString(str7);
                    parcelObtain.writeString(str8);
                    parcelObtain.writeString(str9);
                    this.mRemote.transact(202, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCarrierServicePackageOverride(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(203, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCarrierIdListVersion(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(204, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void refreshUiccProfile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(205, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getNumberOfModemsWithSimultaneousDataConnections(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(206, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getNetworkSelectionMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(207, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isInEmergencySmsMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(208, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getRadioPowerState(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(209, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(210, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(211, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerImsEmergencyRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(212, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterImsEmergencyRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(213, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void getImsMmTelRegistrationState(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(214, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void getImsMmTelRegistrationTransportType(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(215, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerMmTelCapabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(216, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterMmTelCapabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(217, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isCapable(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(218, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isAvailable(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(219, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void isMmTelCapabilitySupported(int i, IIntegerConsumer iIntegerConsumer, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(220, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isAdvancedCallingSettingEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(221, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setAdvancedCallingSettingEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(222, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isVtSettingEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(223, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVtSettingEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(224, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isVoWiFiSettingEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(225, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoWiFiSettingEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(226, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isCrossSimCallingEnabledByUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(227, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCrossSimCallingEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(228, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isVoWiFiRoamingSettingEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(229, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoWiFiRoamingSettingEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(230, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoWiFiNonPersistent(int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(231, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getVoWiFiModeSetting(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(232, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoWiFiModeSetting(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(233, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getVoWiFiRoamingModeSetting(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(234, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoWiFiRoamingModeSetting(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(235, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setRttCapabilitySetting(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(236, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isTtyOverVolteEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(237, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public Map getEmergencyNumberList(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(238, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isEmergencyNumber(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(239, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<String> getCertsFromCarrierPrivilegeAccessRules(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(240, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerImsProvisioningChangedCallback(int i, IImsConfigCallback iImsConfigCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsConfigCallback);
                    this.mRemote.transact(241, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterImsProvisioningChangedCallback(int i, IImsConfigCallback iImsConfigCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsConfigCallback);
                    this.mRemote.transact(242, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerFeatureProvisioningChangedCallback(int i, IFeatureProvisioningCallback iFeatureProvisioningCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iFeatureProvisioningCallback);
                    this.mRemote.transact(243, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterFeatureProvisioningChangedCallback(int i, IFeatureProvisioningCallback iFeatureProvisioningCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iFeatureProvisioningCallback);
                    this.mRemote.transact(244, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setImsProvisioningStatusForCapability(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(245, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getImsProvisioningStatusForCapability(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(246, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getRcsProvisioningStatusForCapability(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(247, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setRcsProvisioningStatusForCapability(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(248, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getImsProvisioningInt(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(249, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getImsProvisioningString(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(250, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int setImsProvisioningInt(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(251, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int setImsProvisioningString(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(252, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void startEmergencyCallbackMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(253, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void updateEmergencyNumberListTestMode(int i, EmergencyNumber emergencyNumber) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(emergencyNumber, 0);
                    this.mRemote.transact(254, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<String> getEmergencyNumberListTestMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(255, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getEmergencyNumberDbVersion(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(256, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void notifyOtaEmergencyNumberDbInstalled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(257, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void updateOtaEmergencyNumberDbFilePath(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(258, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void resetOtaEmergencyNumberDbFilePath() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(259, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean enableModemForSlot(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(260, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setMultiSimCarrierRestriction(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(261, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int isMultiSimSupported(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(262, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void switchMultiSimConfig(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(263, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean doesSwitchMultiSimConfigTriggerReboot(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(264, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<UiccSlotMapping> getSlotsMapping(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(265, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(UiccSlotMapping.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getRadioHalVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(266, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getHalVersion(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(267, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getCurrentPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(268, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isApplicationOnUicc(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(269, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isModemEnabledForSlot(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(270, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isDataEnabledForApn(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(271, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isApnMetered(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(272, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setSystemSelectionChannels(List<RadioAccessSpecifier> list, int i, IBooleanConsumer iBooleanConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(273, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<RadioAccessSpecifier> getSystemSelectionChannels(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(274, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(RadioAccessSpecifier.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isMvnoMatched(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(275, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void enqueueSmsPickResult(String str, String str2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(276, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void showSwitchToManagedProfileDialog() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(277, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getMmsUserAgent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(278, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getMmsUAProfUrl(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(279, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setMobileDataPolicyEnabled(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(280, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isMobileDataPolicyEnabled(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(281, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCepEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(282, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void notifyRcsAutoConfigurationReceived(int i, byte[] bArr, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(283, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isIccLockEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(284, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int setIccLockEnabled(int i, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(285, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int changeIccLockPassword(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(286, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestUserActivityNotification() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(287, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void userActivity() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(288, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getManualNetworkSelectionPlmn(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(289, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean canConnectTo5GInDsdsMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(290, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<String> getEquivalentHomePlmns(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(291, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int setVoNrEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(292, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isVoNrEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(293, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int setNrDualConnectivityState(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(294, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isNrDualConnectivityEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(295, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRadioInterfaceCapabilitySupported(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(296, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int sendThermalMitigationRequest(int i, ThermalMitigationRequest thermalMitigationRequest, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(thermalMitigationRequest, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(297, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void bootstrapAuthenticationRequest(int i, int i2, Uri uri, UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier, boolean z, IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeTypedObject(uaSecurityProtocolIdentifier, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iBootstrapAuthenticationCallback);
                    this.mRemote.transact(298, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setBoundGbaServiceOverride(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(299, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getBoundGbaService(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(300, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setGbaReleaseTimeOverride(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(301, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getGbaReleaseTime(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(302, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setRcsClientConfiguration(int i, RcsClientConfiguration rcsClientConfiguration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(rcsClientConfiguration, 0);
                    this.mRemote.transact(303, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRcsVolteSingleRegistrationCapable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(304, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerRcsProvisioningCallback(int i, IRcsConfigCallback iRcsConfigCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRcsConfigCallback);
                    this.mRemote.transact(305, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterRcsProvisioningCallback(int i, IRcsConfigCallback iRcsConfigCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRcsConfigCallback);
                    this.mRemote.transact(306, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void triggerRcsReconfiguration(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(307, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setRcsSingleRegistrationTestModeEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(308, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getRcsSingleRegistrationTestModeEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(309, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setDeviceSingleRegistrationEnabledOverride(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(310, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getDeviceSingleRegistrationEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(311, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setCarrierSingleRegistrationEnabledOverride(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(312, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void sendDeviceToDeviceMessage(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(313, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setActiveDeviceToDeviceTransport(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(314, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setDeviceToDeviceForceEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(315, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getCarrierSingleRegistrationEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(316, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setImsFeatureValidationOverride(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(317, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getImsFeatureValidationOverride(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(318, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getMobileProvisioningUrl() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(319, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int removeContactFromEab(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(320, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getContactFromEab(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(321, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getCapabilityFromEab(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(322, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getDeviceUceEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(323, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setDeviceUceEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(324, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public RcsContactUceCapability addUceRegistrationOverrideShell(int i, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(325, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RcsContactUceCapability) parcelObtain2.readTypedObject(RcsContactUceCapability.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public RcsContactUceCapability removeUceRegistrationOverrideShell(int i, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(326, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RcsContactUceCapability) parcelObtain2.readTypedObject(RcsContactUceCapability.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public RcsContactUceCapability clearUceRegistrationOverrideShell(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(327, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RcsContactUceCapability) parcelObtain2.readTypedObject(RcsContactUceCapability.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public RcsContactUceCapability getLatestRcsContactUceCapabilityShell(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(328, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RcsContactUceCapability) parcelObtain2.readTypedObject(RcsContactUceCapability.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getLastUcePidfXmlShell(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(329, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean removeUceRequestDisallowedStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(330, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setCapabilitiesRequestTimeout(int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(331, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setSignalStrengthUpdateRequest(int i, SignalStrengthUpdateRequest signalStrengthUpdateRequest, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(signalStrengthUpdateRequest, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(332, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void clearSignalStrengthUpdateRequest(int i, SignalStrengthUpdateRequest signalStrengthUpdateRequest, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(signalStrengthUpdateRequest, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(333, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public PhoneCapability getPhoneCapability() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(334, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PhoneCapability) parcelObtain2.readTypedObject(PhoneCapability.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int prepareForUnattendedReboot() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(335, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void getSlicingConfig(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(336, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isPremiumCapabilityAvailableForPurchase(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(337, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void purchasePremiumCapability(int i, IIntegerConsumer iIntegerConsumer, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(338, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerImsStateCallback(int i, int i2, IImsStateCallback iImsStateCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iImsStateCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(339, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterImsStateCallback(IImsStateCallback iImsStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsStateCallback);
                    this.mRemote.transact(340, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public CellIdentity getLastKnownCellIdentity(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(341, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CellIdentity) parcelObtain2.readTypedObject(CellIdentity.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setModemService(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(342, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getModemService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(343, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isProvisioningRequiredForCapability(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(344, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRcsProvisioningRequiredForCapability(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(345, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoiceServiceStateOverride(int i, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(346, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public String getCarrierServicePackageNameForLogicalSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(347, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setRemovableEsimAsDefaultEuicc(boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(348, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRemovableEsimDefaultEuicc(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(349, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public ComponentName getDefaultRespondViaMessageApplication(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(350, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getSimStateForSlotIndex(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(351, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void persistEmergencyCallDiagnosticData(String str, boolean z, long j, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(352, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setNullCipherAndIntegrityEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(353, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isNullCipherAndIntegrityPreferenceEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(354, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<CellBroadcastIdRange> getCellBroadcastIdRanges(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(355, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(CellBroadcastIdRange.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCellBroadcastIdRanges(int i, List<CellBroadcastIdRange> list, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(356, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isDomainSelectionSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(357, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void getCarrierRestrictionStatus(IIntegerConsumer iIntegerConsumer, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(358, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestSatelliteEnabled(boolean z, boolean z2, boolean z3, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(359, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestIsSatelliteEnabled(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(360, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestIsDemoModeEnabled(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(361, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestIsEmergencyModeEnabled(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(362, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestIsSatelliteSupported(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(363, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestSatelliteCapabilities(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(364, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void startSatelliteTransmissionUpdates(IIntegerConsumer iIntegerConsumer, ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    parcelObtain.writeStrongInterface(iSatelliteTransmissionUpdateCallback);
                    this.mRemote.transact(365, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void stopSatelliteTransmissionUpdates(IIntegerConsumer iIntegerConsumer, ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    parcelObtain.writeStrongInterface(iSatelliteTransmissionUpdateCallback);
                    this.mRemote.transact(366, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public ICancellationSignal provisionSatelliteService(String str, byte[] bArr, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(367, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void deprovisionSatelliteService(String str, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(368, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int registerForSatelliteProvisionStateChanged(ISatelliteProvisionStateCallback iSatelliteProvisionStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSatelliteProvisionStateCallback);
                    this.mRemote.transact(369, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterForSatelliteProvisionStateChanged(ISatelliteProvisionStateCallback iSatelliteProvisionStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSatelliteProvisionStateCallback);
                    this.mRemote.transact(370, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestIsSatelliteProvisioned(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(371, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int registerForSatelliteModemStateChanged(ISatelliteModemStateCallback iSatelliteModemStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSatelliteModemStateCallback);
                    this.mRemote.transact(372, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterForModemStateChanged(ISatelliteModemStateCallback iSatelliteModemStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSatelliteModemStateCallback);
                    this.mRemote.transact(373, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int registerForIncomingDatagram(ISatelliteDatagramCallback iSatelliteDatagramCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSatelliteDatagramCallback);
                    this.mRemote.transact(374, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterForIncomingDatagram(ISatelliteDatagramCallback iSatelliteDatagramCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSatelliteDatagramCallback);
                    this.mRemote.transact(375, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void pollPendingDatagrams(IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(376, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void sendDatagram(int i, SatelliteDatagram satelliteDatagram, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(satelliteDatagram, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(377, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int[] getSatelliteDisallowedReasons() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(378, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerForSatelliteDisallowedReasonsChanged(ISatelliteDisallowedReasonsCallback iSatelliteDisallowedReasonsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSatelliteDisallowedReasonsCallback);
                    this.mRemote.transact(379, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterForSatelliteDisallowedReasonsChanged(ISatelliteDisallowedReasonsCallback iSatelliteDisallowedReasonsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSatelliteDisallowedReasonsCallback);
                    this.mRemote.transact(380, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestIsCommunicationAllowedForCurrentLocation(int i, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(381, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestSatelliteAccessConfigurationForCurrentLocation(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(382, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestTimeForNextSatelliteVisibility(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(383, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestSelectedNbIotSatelliteSubscriptionId(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(384, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int registerForSelectedNbIotSatelliteSubscriptionChanged(ISelectedNbIotSatelliteSubscriptionCallback iSelectedNbIotSatelliteSubscriptionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSelectedNbIotSatelliteSubscriptionCallback);
                    this.mRemote.transact(385, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterForSelectedNbIotSatelliteSubscriptionChanged(ISelectedNbIotSatelliteSubscriptionCallback iSelectedNbIotSatelliteSubscriptionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSelectedNbIotSatelliteSubscriptionCallback);
                    this.mRemote.transact(386, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setDeviceAlignedWithSatellite(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(387, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatelliteServicePackageName(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(388, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatelliteGatewayServicePackageName(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(389, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatelliteListeningTimeoutDuration(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(390, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatelliteIgnoreCellularServiceState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(391, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSupportDisableSatelliteWhileEnableInProgress(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(392, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatellitePointingUiClassName(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(393, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setDatagramControllerTimeoutDuration(boolean z, int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(394, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatelliteControllerTimeoutDuration(boolean z, int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(395, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setEmergencyCallToSatelliteHandoverType(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(396, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setCountryCodes(boolean z, List<String> list, Map map, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeMap(map);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(397, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatelliteAccessControlOverlayConfigs(boolean z, boolean z2, String str, long j, List<String> list, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(398, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatelliteAccessAllowedForSubscriptions(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(399, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setTnScanningSupport(boolean z, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(400, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setOemEnabledSatelliteProvisionStatus(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(401, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean overrideConfigDataVersion(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(402, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<String> getShaIdFromAllowList(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(403, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void addAttachRestrictionForCarrier(int i, int i2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(404, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void removeAttachRestrictionForCarrier(int i, int i2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(405, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int[] getAttachRestrictionReasonsForCarrier(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(406, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestNtnSignalStrength(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(407, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerForNtnSignalStrengthChanged(INtnSignalStrengthCallback iNtnSignalStrengthCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNtnSignalStrengthCallback);
                    this.mRemote.transact(408, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterForNtnSignalStrengthChanged(INtnSignalStrengthCallback iNtnSignalStrengthCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNtnSignalStrengthCallback);
                    this.mRemote.transact(409, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int registerForCapabilitiesChanged(ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSatelliteCapabilitiesCallback);
                    this.mRemote.transact(410, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterForCapabilitiesChanged(ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSatelliteCapabilitiesCallback);
                    this.mRemote.transact(411, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setShouldSendDatagramToModemInDemoMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(412, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setDomainSelectionServiceOverride(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(413, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean clearDomainSelectionServiceOverride() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(414, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isAospDomainSelectionService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(415, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setEnableCellularIdentifierDisclosureNotifications(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(416, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isCellularIdentifierDisclosureNotificationsEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(417, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setNullCipherNotificationsEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(418, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isNullCipherNotificationsEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(419, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<String> getSatellitePlmnsForCarrier(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(420, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int registerForSatelliteSupportedStateChanged(IBooleanConsumer iBooleanConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(421, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterForSatelliteSupportedStateChanged(IBooleanConsumer iBooleanConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(422, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int registerForCommunicationAccessStateChanged(int i, ISatelliteCommunicationAccessStateCallback iSatelliteCommunicationAccessStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSatelliteCommunicationAccessStateCallback);
                    this.mRemote.transact(423, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterForCommunicationAccessStateChanged(int i, ISatelliteCommunicationAccessStateCallback iSatelliteCommunicationAccessStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSatelliteCommunicationAccessStateCallback);
                    this.mRemote.transact(424, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setDatagramControllerBooleanConfig(boolean z, int i, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(425, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setIsSatelliteCommunicationAllowedForCurrentLocationCache(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(426, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestSatelliteSessionStats(int i, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(427, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestSatelliteSubscriberProvisionStatus(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(428, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestSatelliteDisplayName(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(429, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void provisionSatellite(List<SatelliteSubscriberInfo> list, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(430, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatelliteSubscriberIdListChangedIntentComponent(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(431, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setTestEuiccUiComponent(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(432, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public ComponentName getTestEuiccUiComponent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(433, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean overrideCarrierRoamingNtnEligibilityChanged(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(434, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void deprovisionSatellite(List<SatelliteSubscriberInfo> list, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(435, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setNtnSmsSupported(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(436, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCarrierIdFromIdentifier(CarrierIdentifier carrierIdentifier) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(carrierIdentifier, 0);
                    this.mRemote.transact(437, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public List<String> getSatelliteDataOptimizedApps() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(438, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getSatelliteDataSupportMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(439, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatelliteIgnorePlmnListFromStorage(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(440, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        private boolean onTransact$isRadioOnForSubscriberWithFeature$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zIsRadioOnForSubscriberWithFeature = isRadioOnForSubscriberWithFeature(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsRadioOnForSubscriberWithFeature);
            return true;
        }

        private boolean onTransact$supplyPukForSubscriber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zSupplyPukForSubscriber = supplyPukForSubscriber(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zSupplyPukForSubscriber);
            return true;
        }

        private boolean onTransact$supplyPukReportResultForSubscriber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int[] iArrSupplyPukReportResultForSubscriber = supplyPukReportResultForSubscriber(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeIntArray(iArrSupplyPukReportResultForSubscriber);
            return true;
        }

        private boolean onTransact$handleUssdRequest$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            handleUssdRequest(i, string, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getRadioPowerOffReasons$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            List radioPowerOffReasons = getRadioPowerOffReasons(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeList(radioPowerOffReasons);
            return true;
        }

        private boolean onTransact$getCallStateForSubscription$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int callStateForSubscription = getCallStateForSubscription(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(callStateForSubscription);
            return true;
        }

        private boolean onTransact$getCdmaEriIconIndexForSubscriber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int cdmaEriIconIndexForSubscriber = getCdmaEriIconIndexForSubscriber(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(cdmaEriIconIndexForSubscriber);
            return true;
        }

        private boolean onTransact$getCdmaEriIconModeForSubscriber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int cdmaEriIconModeForSubscriber = getCdmaEriIconModeForSubscriber(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(cdmaEriIconModeForSubscriber);
            return true;
        }

        private boolean onTransact$getCdmaEriTextForSubscriber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            String cdmaEriTextForSubscriber = getCdmaEriTextForSubscriber(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeString(cdmaEriTextForSubscriber);
            return true;
        }

        private boolean onTransact$setVoiceMailNumber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean voiceMailNumber = setVoiceMailNumber(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(voiceMailNumber);
            return true;
        }

        private boolean onTransact$getVoiceMessageCountForSubscriber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int voiceMessageCountForSubscriber = getVoiceMessageCountForSubscriber(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(voiceMessageCountForSubscriber);
            return true;
        }

        private boolean onTransact$getVisualVoicemailPackageName$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            String visualVoicemailPackageName = getVisualVoicemailPackageName(string, string2, i);
            parcel2.writeNoException();
            parcel2.writeString(visualVoicemailPackageName);
            return true;
        }

        private boolean onTransact$enableVisualVoicemailSmsFilter$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings = (VisualVoicemailSmsFilterSettings) parcel.readTypedObject(VisualVoicemailSmsFilterSettings.CREATOR);
            parcel.enforceNoDataAvail();
            enableVisualVoicemailSmsFilter(string, i, visualVoicemailSmsFilterSettings);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$sendVisualVoicemailSmsForSubscriber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            int i = parcel.readInt();
            String string3 = parcel.readString();
            int i2 = parcel.readInt();
            String string4 = parcel.readString();
            PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
            parcel.enforceNoDataAvail();
            sendVisualVoicemailSmsForSubscriber(string, string2, i, string3, i2, string4, pendingIntent);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getNetworkTypeForSubscriber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int networkTypeForSubscriber = getNetworkTypeForSubscriber(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(networkTypeForSubscriber);
            return true;
        }

        private boolean onTransact$getDataNetworkTypeForSubscriber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int dataNetworkTypeForSubscriber = getDataNetworkTypeForSubscriber(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(dataNetworkTypeForSubscriber);
            return true;
        }

        private boolean onTransact$getVoiceNetworkTypeForSubscriber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int voiceNetworkTypeForSubscriber = getVoiceNetworkTypeForSubscriber(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(voiceNetworkTypeForSubscriber);
            return true;
        }

        private boolean onTransact$getLteOnCdmaModeForSubscriber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int lteOnCdmaModeForSubscriber = getLteOnCdmaModeForSubscriber(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(lteOnCdmaModeForSubscriber);
            return true;
        }

        private boolean onTransact$requestCellInfoUpdate$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            ICellInfoCallback iCellInfoCallbackAsInterface = ICellInfoCallback.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            requestCellInfoUpdate(i, iCellInfoCallbackAsInterface, string, string2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestCellInfoUpdateWithWorkSource$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            ICellInfoCallback iCellInfoCallbackAsInterface = ICellInfoCallback.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            String string2 = parcel.readString();
            WorkSource workSource = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
            parcel.enforceNoDataAvail();
            requestCellInfoUpdateWithWorkSource(i, iCellInfoCallbackAsInterface, string, string2, workSource);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$iccTransmitApduLogicalChannelByPort$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            int i5 = parcel.readInt();
            int i6 = parcel.readInt();
            int i7 = parcel.readInt();
            int i8 = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            String strIccTransmitApduLogicalChannelByPort = iccTransmitApduLogicalChannelByPort(i, i2, i3, i4, i5, i6, i7, i8, string);
            parcel2.writeNoException();
            parcel2.writeString(strIccTransmitApduLogicalChannelByPort);
            return true;
        }

        private boolean onTransact$iccTransmitApduLogicalChannel$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            int i5 = parcel.readInt();
            int i6 = parcel.readInt();
            int i7 = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            String strIccTransmitApduLogicalChannel = iccTransmitApduLogicalChannel(i, i2, i3, i4, i5, i6, i7, string);
            parcel2.writeNoException();
            parcel2.writeString(strIccTransmitApduLogicalChannel);
            return true;
        }

        private boolean onTransact$iccTransmitApduBasicChannelByPort$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            String string = parcel.readString();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            int i5 = parcel.readInt();
            int i6 = parcel.readInt();
            int i7 = parcel.readInt();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            String strIccTransmitApduBasicChannelByPort = iccTransmitApduBasicChannelByPort(i, i2, string, i3, i4, i5, i6, i7, string2);
            parcel2.writeNoException();
            parcel2.writeString(strIccTransmitApduBasicChannelByPort);
            return true;
        }

        private boolean onTransact$iccTransmitApduBasicChannel$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            int i5 = parcel.readInt();
            int i6 = parcel.readInt();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            String strIccTransmitApduBasicChannel = iccTransmitApduBasicChannel(i, string, i2, i3, i4, i5, i6, string2);
            parcel2.writeNoException();
            parcel2.writeString(strIccTransmitApduBasicChannel);
            return true;
        }

        private boolean onTransact$iccExchangeSimIO$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            int i5 = parcel.readInt();
            int i6 = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            byte[] bArrIccExchangeSimIO = iccExchangeSimIO(i, i2, i3, i4, i5, i6, string);
            parcel2.writeNoException();
            parcel2.writeByteArray(bArrIccExchangeSimIO);
            return true;
        }

        private boolean onTransact$setBoundImsServiceOverride$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            boolean z = parcel.readBoolean();
            int[] iArrCreateIntArray = parcel.createIntArray();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean boundImsServiceOverride = setBoundImsServiceOverride(i, i2, z, iArrCreateIntArray, string);
            parcel2.writeNoException();
            parcel2.writeBoolean(boundImsServiceOverride);
            return true;
        }

        private boolean onTransact$getBoundImsServicePackage$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            String boundImsServicePackage = getBoundImsServicePackage(i, z, i2);
            parcel2.writeNoException();
            parcel2.writeString(boundImsServicePackage);
            return true;
        }

        private boolean onTransact$getCellNetworkScanResults$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            CellNetworkScanResult cellNetworkScanResults = getCellNetworkScanResults(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeTypedObject(cellNetworkScanResults, 1);
            return true;
        }

        private boolean onTransact$requestNetworkScan$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            NetworkScanRequest networkScanRequest = (NetworkScanRequest) parcel.readTypedObject(NetworkScanRequest.CREATOR);
            Messenger messenger = (Messenger) parcel.readTypedObject(Messenger.CREATOR);
            IBinder strongBinder = parcel.readStrongBinder();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int iRequestNetworkScan = requestNetworkScan(i, z, networkScanRequest, messenger, strongBinder, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(iRequestNetworkScan);
            return true;
        }

        private boolean onTransact$setNetworkSelectionModeManual$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            OperatorInfo operatorInfo = (OperatorInfo) parcel.readTypedObject(OperatorInfo.CREATOR);
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean networkSelectionModeManual = setNetworkSelectionModeManual(i, operatorInfo, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(networkSelectionModeManual);
            return true;
        }

        private boolean onTransact$setAllowedNetworkTypesForReason$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            long j = parcel.readLong();
            parcel.enforceNoDataAvail();
            boolean allowedNetworkTypesForReason = setAllowedNetworkTypesForReason(i, i2, j);
            parcel2.writeNoException();
            parcel2.writeBoolean(allowedNetworkTypesForReason);
            return true;
        }

        private boolean onTransact$setDataEnabledForReason$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            boolean z = parcel.readBoolean();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setDataEnabledForReason(i, i2, z, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestNumberVerification$(Parcel parcel, Parcel parcel2) throws RemoteException {
            PhoneNumberRange phoneNumberRange = (PhoneNumberRange) parcel.readTypedObject(PhoneNumberRange.CREATOR);
            long j = parcel.readLong();
            INumberVerificationCallback iNumberVerificationCallbackAsInterface = INumberVerificationCallback.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            requestNumberVerification(phoneNumberRange, j, iNumberVerificationCallbackAsInterface, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setLine1NumberForDisplayForSubscriber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean line1NumberForDisplayForSubscriber = setLine1NumberForDisplayForSubscriber(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(line1NumberForDisplayForSubscriber);
            return true;
        }

        private boolean onTransact$getLine1NumberForDisplay$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            String line1NumberForDisplay = getLine1NumberForDisplay(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeString(line1NumberForDisplay);
            return true;
        }

        private boolean onTransact$getLine1AlphaTagForDisplay$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            String line1AlphaTagForDisplay = getLine1AlphaTagForDisplay(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeString(line1AlphaTagForDisplay);
            return true;
        }

        private boolean onTransact$getMergedSubscriberIds$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            String[] mergedSubscriberIds = getMergedSubscriberIds(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeStringArray(mergedSubscriberIds);
            return true;
        }

        private boolean onTransact$setRoamingOverride$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
            ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
            ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
            ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            boolean roamingOverride = setRoamingOverride(i, arrayListCreateStringArrayList, arrayListCreateStringArrayList2, arrayListCreateStringArrayList3, arrayListCreateStringArrayList4);
            parcel2.writeNoException();
            parcel2.writeBoolean(roamingOverride);
            return true;
        }

        private boolean onTransact$uploadCallComposerPicture$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
            ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            uploadCallComposerPicture(i, string, string2, parcelFileDescriptor, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$canChangeDtmfToneLength$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zCanChangeDtmfToneLength = canChangeDtmfToneLength(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zCanChangeDtmfToneLength);
            return true;
        }

        private boolean onTransact$isWorldPhone$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zIsWorldPhone = isWorldPhone(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsWorldPhone);
            return true;
        }

        private boolean onTransact$getImeiForSlot$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            String imeiForSlot = getImeiForSlot(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeString(imeiForSlot);
            return true;
        }

        private boolean onTransact$getMeidForSlot$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            String meidForSlot = getMeidForSlot(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeString(meidForSlot);
            return true;
        }

        private boolean onTransact$getDeviceSoftwareVersionForSlot$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            String deviceSoftwareVersionForSlot = getDeviceSoftwareVersionForSlot(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeString(deviceSoftwareVersionForSlot);
            return true;
        }

        private boolean onTransact$getSubIdForPhoneAccountHandle$(Parcel parcel, Parcel parcel2) throws RemoteException {
            PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int subIdForPhoneAccountHandle = getSubIdForPhoneAccountHandle(phoneAccountHandle, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(subIdForPhoneAccountHandle);
            return true;
        }

        private boolean onTransact$getServiceStateForSlot$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            ServiceState serviceStateForSlot = getServiceStateForSlot(i, z, z2, string, string2);
            parcel2.writeNoException();
            parcel2.writeTypedObject(serviceStateForSlot, 1);
            return true;
        }

        private boolean onTransact$setVoicemailRingtoneUri$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
            Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
            parcel.enforceNoDataAvail();
            setVoicemailRingtoneUri(string, phoneAccountHandle, uri);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setVoicemailVibrationEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setVoicemailVibrationEnabled(string, phoneAccountHandle, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getCarrierIdFromMccMnc$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int carrierIdFromMccMnc = getCarrierIdFromMccMnc(i, string, z);
            parcel2.writeNoException();
            parcel2.writeInt(carrierIdFromMccMnc);
            return true;
        }

        private boolean onTransact$getCallForwarding$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            ICallForwardingInfoCallback iCallForwardingInfoCallbackAsInterface = ICallForwardingInfoCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            getCallForwarding(i, i2, iCallForwardingInfoCallbackAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCallForwarding$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            CallForwardingInfo callForwardingInfo = (CallForwardingInfo) parcel.readTypedObject(CallForwardingInfo.CREATOR);
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            setCallForwarding(i, callForwardingInfo, iIntegerConsumerAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCallWaitingStatus$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            setCallWaitingStatus(i, z, iIntegerConsumerAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getClientRequestStats$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            List<ClientRequestStats> clientRequestStats = getClientRequestStats(string, string2, i);
            parcel2.writeNoException();
            parcel2.writeTypedList(clientRequestStats, 1);
            return true;
        }

        private boolean onTransact$setSimPowerStateForSlotWithCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            setSimPowerStateForSlotWithCallback(i, i2, iIntegerConsumerAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getForbiddenPlmns$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            String[] forbiddenPlmns = getForbiddenPlmns(i, i2, string, string2);
            parcel2.writeNoException();
            parcel2.writeStringArray(forbiddenPlmns);
            return true;
        }

        private boolean onTransact$setForbiddenPlmns$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int forbiddenPlmns = setForbiddenPlmns(i, i2, arrayListCreateStringArrayList, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(forbiddenPlmns);
            return true;
        }

        private boolean onTransact$setCarrierTestOverride$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            String string4 = parcel.readString();
            String string5 = parcel.readString();
            String string6 = parcel.readString();
            String string7 = parcel.readString();
            String string8 = parcel.readString();
            String string9 = parcel.readString();
            parcel.enforceNoDataAvail();
            setCarrierTestOverride(i, string, string2, string3, string4, string5, string6, string7, string8, string9);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCarrierServicePackageOverride$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            setCarrierServicePackageOverride(i, string, string2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getNumberOfModemsWithSimultaneousDataConnections$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int numberOfModemsWithSimultaneousDataConnections = getNumberOfModemsWithSimultaneousDataConnections(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(numberOfModemsWithSimultaneousDataConnections);
            return true;
        }

        private boolean onTransact$getRadioPowerState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int radioPowerState = getRadioPowerState(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(radioPowerState);
            return true;
        }

        private boolean onTransact$getImsMmTelRegistrationState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            getImsMmTelRegistrationState(i, iIntegerConsumerAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getImsMmTelRegistrationTransportType$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            getImsMmTelRegistrationTransportType(i, iIntegerConsumerAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$registerMmTelCapabilityCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            IImsCapabilityCallback iImsCapabilityCallbackAsInterface = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            registerMmTelCapabilityCallback(i, iImsCapabilityCallbackAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$unregisterMmTelCapabilityCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            IImsCapabilityCallback iImsCapabilityCallbackAsInterface = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            unregisterMmTelCapabilityCallback(i, iImsCapabilityCallbackAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$isCapable$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zIsCapable = isCapable(i, i2, i3);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsCapable);
            return true;
        }

        private boolean onTransact$isAvailable$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zIsAvailable = isAvailable(i, i2, i3);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsAvailable);
            return true;
        }

        private boolean onTransact$isMmTelCapabilitySupported$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            isMmTelCapabilitySupported(i, iIntegerConsumerAsInterface, i2, i3);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setAdvancedCallingSettingEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setAdvancedCallingSettingEnabled(i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setVtSettingEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setVtSettingEnabled(i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setVoWiFiSettingEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setVoWiFiSettingEnabled(i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCrossSimCallingEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setCrossSimCallingEnabled(i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setVoWiFiRoamingSettingEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setVoWiFiRoamingSettingEnabled(i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setVoWiFiNonPersistent$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            setVoWiFiNonPersistent(i, z, i2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setVoWiFiModeSetting$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            setVoWiFiModeSetting(i, i2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setVoWiFiRoamingModeSetting$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            setVoWiFiRoamingModeSetting(i, i2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setRttCapabilitySetting$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setRttCapabilitySetting(i, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getEmergencyNumberList$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            Map emergencyNumberList = getEmergencyNumberList(string, string2);
            parcel2.writeNoException();
            parcel2.writeMap(emergencyNumberList);
            return true;
        }

        private boolean onTransact$isEmergencyNumber$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean zIsEmergencyNumber = isEmergencyNumber(string, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsEmergencyNumber);
            return true;
        }

        private boolean onTransact$registerImsProvisioningChangedCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            IImsConfigCallback iImsConfigCallbackAsInterface = IImsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            registerImsProvisioningChangedCallback(i, iImsConfigCallbackAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$unregisterImsProvisioningChangedCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            IImsConfigCallback iImsConfigCallbackAsInterface = IImsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            unregisterImsProvisioningChangedCallback(i, iImsConfigCallbackAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$registerFeatureProvisioningChangedCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            IFeatureProvisioningCallback iFeatureProvisioningCallbackAsInterface = IFeatureProvisioningCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            registerFeatureProvisioningChangedCallback(i, iFeatureProvisioningCallbackAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$unregisterFeatureProvisioningChangedCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            IFeatureProvisioningCallback iFeatureProvisioningCallbackAsInterface = IFeatureProvisioningCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            unregisterFeatureProvisioningChangedCallback(i, iFeatureProvisioningCallbackAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setImsProvisioningStatusForCapability$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setImsProvisioningStatusForCapability(i, i2, i3, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getImsProvisioningStatusForCapability$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean imsProvisioningStatusForCapability = getImsProvisioningStatusForCapability(i, i2, i3);
            parcel2.writeNoException();
            parcel2.writeBoolean(imsProvisioningStatusForCapability);
            return true;
        }

        private boolean onTransact$getRcsProvisioningStatusForCapability$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean rcsProvisioningStatusForCapability = getRcsProvisioningStatusForCapability(i, i2, i3);
            parcel2.writeNoException();
            parcel2.writeBoolean(rcsProvisioningStatusForCapability);
            return true;
        }

        private boolean onTransact$setRcsProvisioningStatusForCapability$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setRcsProvisioningStatusForCapability(i, i2, i3, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getImsProvisioningInt$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int imsProvisioningInt = getImsProvisioningInt(i, i2);
            parcel2.writeNoException();
            parcel2.writeInt(imsProvisioningInt);
            return true;
        }

        private boolean onTransact$getImsProvisioningString$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            String imsProvisioningString = getImsProvisioningString(i, i2);
            parcel2.writeNoException();
            parcel2.writeString(imsProvisioningString);
            return true;
        }

        private boolean onTransact$setImsProvisioningInt$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int imsProvisioningInt = setImsProvisioningInt(i, i2, i3);
            parcel2.writeNoException();
            parcel2.writeInt(imsProvisioningInt);
            return true;
        }

        private boolean onTransact$setImsProvisioningString$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            int imsProvisioningString = setImsProvisioningString(i, i2, string);
            parcel2.writeNoException();
            parcel2.writeInt(imsProvisioningString);
            return true;
        }

        private boolean onTransact$updateEmergencyNumberListTestMode$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            EmergencyNumber emergencyNumber = (EmergencyNumber) parcel.readTypedObject(EmergencyNumber.CREATOR);
            parcel.enforceNoDataAvail();
            updateEmergencyNumberListTestMode(i, emergencyNumber);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$enableModemForSlot$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean zEnableModemForSlot = enableModemForSlot(i, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(zEnableModemForSlot);
            return true;
        }

        private boolean onTransact$isMultiSimSupported$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int iIsMultiSimSupported = isMultiSimSupported(string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(iIsMultiSimSupported);
            return true;
        }

        private boolean onTransact$doesSwitchMultiSimConfigTriggerReboot$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zDoesSwitchMultiSimConfigTriggerReboot = doesSwitchMultiSimConfigTriggerReboot(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zDoesSwitchMultiSimConfigTriggerReboot);
            return true;
        }

        private boolean onTransact$isApplicationOnUicc$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zIsApplicationOnUicc = isApplicationOnUicc(i, i2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsApplicationOnUicc);
            return true;
        }

        private boolean onTransact$isModemEnabledForSlot$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zIsModemEnabledForSlot = isModemEnabledForSlot(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsModemEnabledForSlot);
            return true;
        }

        private boolean onTransact$isDataEnabledForApn$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zIsDataEnabledForApn = isDataEnabledForApn(i, i2, string);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsDataEnabledForApn);
            return true;
        }

        private boolean onTransact$isApnMetered$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zIsApnMetered = isApnMetered(i, i2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsApnMetered);
            return true;
        }

        private boolean onTransact$setSystemSelectionChannels$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(RadioAccessSpecifier.CREATOR);
            int i = parcel.readInt();
            IBooleanConsumer iBooleanConsumerAsInterface = IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            setSystemSelectionChannels(arrayListCreateTypedArrayList, i, iBooleanConsumerAsInterface);
            return true;
        }

        private boolean onTransact$isMvnoMatched$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zIsMvnoMatched = isMvnoMatched(i, i2, string);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsMvnoMatched);
            return true;
        }

        private boolean onTransact$enqueueSmsPickResult$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            enqueueSmsPickResult(string, string2, iIntegerConsumerAsInterface);
            return true;
        }

        private boolean onTransact$setMobileDataPolicyEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setMobileDataPolicyEnabled(i, i2, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$isMobileDataPolicyEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zIsMobileDataPolicyEnabled = isMobileDataPolicyEnabled(i, i2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsMobileDataPolicyEnabled);
            return true;
        }

        private boolean onTransact$notifyRcsAutoConfigurationReceived$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            byte[] bArrCreateByteArray = parcel.createByteArray();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            notifyRcsAutoConfigurationReceived(i, bArrCreateByteArray, z);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setIccLockEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            int iccLockEnabled = setIccLockEnabled(i, z, string);
            parcel2.writeNoException();
            parcel2.writeInt(iccLockEnabled);
            return true;
        }

        private boolean onTransact$changeIccLockPassword$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int iChangeIccLockPassword = changeIccLockPassword(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(iChangeIccLockPassword);
            return true;
        }

        private boolean onTransact$getEquivalentHomePlmns$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            List<String> equivalentHomePlmns = getEquivalentHomePlmns(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeStringList(equivalentHomePlmns);
            return true;
        }

        private boolean onTransact$setVoNrEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int voNrEnabled = setVoNrEnabled(i, z);
            parcel2.writeNoException();
            parcel2.writeInt(voNrEnabled);
            return true;
        }

        private boolean onTransact$setNrDualConnectivityState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int nrDualConnectivityState = setNrDualConnectivityState(i, i2);
            parcel2.writeNoException();
            parcel2.writeInt(nrDualConnectivityState);
            return true;
        }

        private boolean onTransact$sendThermalMitigationRequest$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            ThermalMitigationRequest thermalMitigationRequest = (ThermalMitigationRequest) parcel.readTypedObject(ThermalMitigationRequest.CREATOR);
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            int iSendThermalMitigationRequest = sendThermalMitigationRequest(i, thermalMitigationRequest, string);
            parcel2.writeNoException();
            parcel2.writeInt(iSendThermalMitigationRequest);
            return true;
        }

        private boolean onTransact$bootstrapAuthenticationRequest$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
            UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier = (UaSecurityProtocolIdentifier) parcel.readTypedObject(UaSecurityProtocolIdentifier.CREATOR);
            boolean z = parcel.readBoolean();
            IBootstrapAuthenticationCallback iBootstrapAuthenticationCallbackAsInterface = IBootstrapAuthenticationCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            bootstrapAuthenticationRequest(i, i2, uri, uaSecurityProtocolIdentifier, z, iBootstrapAuthenticationCallbackAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setBoundGbaServiceOverride$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean boundGbaServiceOverride = setBoundGbaServiceOverride(i, string);
            parcel2.writeNoException();
            parcel2.writeBoolean(boundGbaServiceOverride);
            return true;
        }

        private boolean onTransact$setGbaReleaseTimeOverride$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean gbaReleaseTimeOverride = setGbaReleaseTimeOverride(i, i2);
            parcel2.writeNoException();
            parcel2.writeBoolean(gbaReleaseTimeOverride);
            return true;
        }

        private boolean onTransact$setRcsClientConfiguration$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            RcsClientConfiguration rcsClientConfiguration = (RcsClientConfiguration) parcel.readTypedObject(RcsClientConfiguration.CREATOR);
            parcel.enforceNoDataAvail();
            setRcsClientConfiguration(i, rcsClientConfiguration);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$registerRcsProvisioningCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            IRcsConfigCallback iRcsConfigCallbackAsInterface = IRcsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            registerRcsProvisioningCallback(i, iRcsConfigCallbackAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$unregisterRcsProvisioningCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            IRcsConfigCallback iRcsConfigCallbackAsInterface = IRcsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            unregisterRcsProvisioningCallback(i, iRcsConfigCallbackAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCarrierSingleRegistrationEnabledOverride$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean carrierSingleRegistrationEnabledOverride = setCarrierSingleRegistrationEnabledOverride(i, string);
            parcel2.writeNoException();
            parcel2.writeBoolean(carrierSingleRegistrationEnabledOverride);
            return true;
        }

        private boolean onTransact$sendDeviceToDeviceMessage$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            sendDeviceToDeviceMessage(i, i2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setImsFeatureValidationOverride$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean imsFeatureValidationOverride = setImsFeatureValidationOverride(i, string);
            parcel2.writeNoException();
            parcel2.writeBoolean(imsFeatureValidationOverride);
            return true;
        }

        private boolean onTransact$removeContactFromEab$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            int iRemoveContactFromEab = removeContactFromEab(i, string);
            parcel2.writeNoException();
            parcel2.writeInt(iRemoveContactFromEab);
            return true;
        }

        private boolean onTransact$addUceRegistrationOverrideShell$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            RcsContactUceCapability rcsContactUceCapabilityAddUceRegistrationOverrideShell = addUceRegistrationOverrideShell(i, arrayListCreateStringArrayList);
            parcel2.writeNoException();
            parcel2.writeTypedObject(rcsContactUceCapabilityAddUceRegistrationOverrideShell, 1);
            return true;
        }

        private boolean onTransact$removeUceRegistrationOverrideShell$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            RcsContactUceCapability rcsContactUceCapabilityRemoveUceRegistrationOverrideShell = removeUceRegistrationOverrideShell(i, arrayListCreateStringArrayList);
            parcel2.writeNoException();
            parcel2.writeTypedObject(rcsContactUceCapabilityRemoveUceRegistrationOverrideShell, 1);
            return true;
        }

        private boolean onTransact$setCapabilitiesRequestTimeout$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            long j = parcel.readLong();
            parcel.enforceNoDataAvail();
            boolean capabilitiesRequestTimeout = setCapabilitiesRequestTimeout(i, j);
            parcel2.writeNoException();
            parcel2.writeBoolean(capabilitiesRequestTimeout);
            return true;
        }

        private boolean onTransact$setSignalStrengthUpdateRequest$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            SignalStrengthUpdateRequest signalStrengthUpdateRequest = (SignalStrengthUpdateRequest) parcel.readTypedObject(SignalStrengthUpdateRequest.CREATOR);
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setSignalStrengthUpdateRequest(i, signalStrengthUpdateRequest, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$clearSignalStrengthUpdateRequest$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            SignalStrengthUpdateRequest signalStrengthUpdateRequest = (SignalStrengthUpdateRequest) parcel.readTypedObject(SignalStrengthUpdateRequest.CREATOR);
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            clearSignalStrengthUpdateRequest(i, signalStrengthUpdateRequest, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$isPremiumCapabilityAvailableForPurchase$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zIsPremiumCapabilityAvailableForPurchase = isPremiumCapabilityAvailableForPurchase(i, i2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsPremiumCapabilityAvailableForPurchase);
            return true;
        }

        private boolean onTransact$purchasePremiumCapability$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            purchasePremiumCapability(i, iIntegerConsumerAsInterface, i2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$registerImsStateCallback$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            IImsStateCallback iImsStateCallbackAsInterface = IImsStateCallback.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            registerImsStateCallback(i, i2, iImsStateCallbackAsInterface, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getLastKnownCellIdentity$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            CellIdentity lastKnownCellIdentity = getLastKnownCellIdentity(i, string, string2);
            parcel2.writeNoException();
            parcel2.writeTypedObject(lastKnownCellIdentity, 1);
            return true;
        }

        private boolean onTransact$isProvisioningRequiredForCapability$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zIsProvisioningRequiredForCapability = isProvisioningRequiredForCapability(i, i2, i3);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsProvisioningRequiredForCapability);
            return true;
        }

        private boolean onTransact$isRcsProvisioningRequiredForCapability$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zIsRcsProvisioningRequiredForCapability = isRcsProvisioningRequiredForCapability(i, i2, i3);
            parcel2.writeNoException();
            parcel2.writeBoolean(zIsRcsProvisioningRequiredForCapability);
            return true;
        }

        private boolean onTransact$setVoiceServiceStateOverride$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setVoiceServiceStateOverride(i, z, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setRemovableEsimAsDefaultEuicc$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            setRemovableEsimAsDefaultEuicc(z, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getDefaultRespondViaMessageApplication$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            ComponentName defaultRespondViaMessageApplication = getDefaultRespondViaMessageApplication(i, z);
            parcel2.writeNoException();
            parcel2.writeTypedObject(defaultRespondViaMessageApplication, 1);
            return true;
        }

        private boolean onTransact$persistEmergencyCallDiagnosticData$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            long j = parcel.readLong();
            boolean z2 = parcel.readBoolean();
            boolean z3 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            persistEmergencyCallDiagnosticData(string, z, j, z2, z3);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCellBroadcastIdRanges$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(CellBroadcastIdRange.CREATOR);
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            setCellBroadcastIdRanges(i, arrayListCreateTypedArrayList, iIntegerConsumerAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getCarrierRestrictionStatus$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            getCarrierRestrictionStatus(iIntegerConsumerAsInterface, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestSatelliteEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            boolean z3 = parcel.readBoolean();
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            requestSatelliteEnabled(z, z2, z3, iIntegerConsumerAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$startSatelliteTransmissionUpdates$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallbackAsInterface = ISatelliteTransmissionUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            startSatelliteTransmissionUpdates(iIntegerConsumerAsInterface, iSatelliteTransmissionUpdateCallbackAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$stopSatelliteTransmissionUpdates$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallbackAsInterface = ISatelliteTransmissionUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            stopSatelliteTransmissionUpdates(iIntegerConsumerAsInterface, iSatelliteTransmissionUpdateCallbackAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$provisionSatelliteService$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            byte[] bArrCreateByteArray = parcel.createByteArray();
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            ICancellationSignal iCancellationSignalProvisionSatelliteService = provisionSatelliteService(string, bArrCreateByteArray, iIntegerConsumerAsInterface);
            parcel2.writeNoException();
            parcel2.writeStrongInterface(iCancellationSignalProvisionSatelliteService);
            return true;
        }

        private boolean onTransact$deprovisionSatelliteService$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            deprovisionSatelliteService(string, iIntegerConsumerAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$sendDatagram$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            SatelliteDatagram satelliteDatagram = (SatelliteDatagram) parcel.readTypedObject(SatelliteDatagram.CREATOR);
            boolean z = parcel.readBoolean();
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            sendDatagram(i, satelliteDatagram, z, iIntegerConsumerAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestIsCommunicationAllowedForCurrentLocation$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            requestIsCommunicationAllowedForCurrentLocation(i, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSatelliteServicePackageName$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean satelliteServicePackageName = setSatelliteServicePackageName(string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(satelliteServicePackageName);
            return true;
        }

        private boolean onTransact$setSupportDisableSatelliteWhileEnableInProgress$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean supportDisableSatelliteWhileEnableInProgress = setSupportDisableSatelliteWhileEnableInProgress(z, z2);
            parcel2.writeNoException();
            parcel2.writeBoolean(supportDisableSatelliteWhileEnableInProgress);
            return true;
        }

        private boolean onTransact$setSatellitePointingUiClassName$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean satellitePointingUiClassName = setSatellitePointingUiClassName(string, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(satellitePointingUiClassName);
            return true;
        }

        private boolean onTransact$setDatagramControllerTimeoutDuration$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            int i = parcel.readInt();
            long j = parcel.readLong();
            parcel.enforceNoDataAvail();
            boolean datagramControllerTimeoutDuration = setDatagramControllerTimeoutDuration(z, i, j);
            parcel2.writeNoException();
            parcel2.writeBoolean(datagramControllerTimeoutDuration);
            return true;
        }

        private boolean onTransact$setSatelliteControllerTimeoutDuration$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            int i = parcel.readInt();
            long j = parcel.readLong();
            parcel.enforceNoDataAvail();
            boolean satelliteControllerTimeoutDuration = setSatelliteControllerTimeoutDuration(z, i, j);
            parcel2.writeNoException();
            parcel2.writeBoolean(satelliteControllerTimeoutDuration);
            return true;
        }

        private boolean onTransact$setEmergencyCallToSatelliteHandoverType$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean emergencyCallToSatelliteHandoverType = setEmergencyCallToSatelliteHandoverType(i, i2);
            parcel2.writeNoException();
            parcel2.writeBoolean(emergencyCallToSatelliteHandoverType);
            return true;
        }

        private boolean onTransact$setCountryCodes$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
            HashMap hashMap = parcel.readHashMap(getClass().getClassLoader());
            String string = parcel.readString();
            long j = parcel.readLong();
            parcel.enforceNoDataAvail();
            boolean countryCodes = setCountryCodes(z, arrayListCreateStringArrayList, hashMap, string, j);
            parcel2.writeNoException();
            parcel2.writeBoolean(countryCodes);
            return true;
        }

        private boolean onTransact$setSatelliteAccessControlOverlayConfigs$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            String string = parcel.readString();
            long j = parcel.readLong();
            ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean satelliteAccessControlOverlayConfigs = setSatelliteAccessControlOverlayConfigs(z, z2, string, j, arrayListCreateStringArrayList, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(satelliteAccessControlOverlayConfigs);
            return true;
        }

        private boolean onTransact$setTnScanningSupport$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            boolean z3 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean tnScanningSupport = setTnScanningSupport(z, z2, z3);
            parcel2.writeNoException();
            parcel2.writeBoolean(tnScanningSupport);
            return true;
        }

        private boolean onTransact$setOemEnabledSatelliteProvisionStatus$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean oemEnabledSatelliteProvisionStatus = setOemEnabledSatelliteProvisionStatus(z, z2);
            parcel2.writeNoException();
            parcel2.writeBoolean(oemEnabledSatelliteProvisionStatus);
            return true;
        }

        private boolean onTransact$overrideConfigDataVersion$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zOverrideConfigDataVersion = overrideConfigDataVersion(z, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zOverrideConfigDataVersion);
            return true;
        }

        private boolean onTransact$getShaIdFromAllowList$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            List<String> shaIdFromAllowList = getShaIdFromAllowList(string, i);
            parcel2.writeNoException();
            parcel2.writeStringList(shaIdFromAllowList);
            return true;
        }

        private boolean onTransact$addAttachRestrictionForCarrier$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            addAttachRestrictionForCarrier(i, i2, iIntegerConsumerAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$removeAttachRestrictionForCarrier$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            removeAttachRestrictionForCarrier(i, i2, iIntegerConsumerAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$registerForCommunicationAccessStateChanged$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            ISatelliteCommunicationAccessStateCallback iSatelliteCommunicationAccessStateCallbackAsInterface = ISatelliteCommunicationAccessStateCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            int iRegisterForCommunicationAccessStateChanged = registerForCommunicationAccessStateChanged(i, iSatelliteCommunicationAccessStateCallbackAsInterface);
            parcel2.writeNoException();
            parcel2.writeInt(iRegisterForCommunicationAccessStateChanged);
            return true;
        }

        private boolean onTransact$unregisterForCommunicationAccessStateChanged$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            ISatelliteCommunicationAccessStateCallback iSatelliteCommunicationAccessStateCallbackAsInterface = ISatelliteCommunicationAccessStateCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            unregisterForCommunicationAccessStateChanged(i, iSatelliteCommunicationAccessStateCallbackAsInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setDatagramControllerBooleanConfig$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            int i = parcel.readInt();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean datagramControllerBooleanConfig = setDatagramControllerBooleanConfig(z, i, z2);
            parcel2.writeNoException();
            parcel2.writeBoolean(datagramControllerBooleanConfig);
            return true;
        }

        private boolean onTransact$requestSatelliteSessionStats$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            requestSatelliteSessionStats(i, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$provisionSatellite$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SatelliteSubscriberInfo.CREATOR);
            ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            provisionSatellite(arrayListCreateTypedArrayList, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$overrideCarrierRoamingNtnEligibilityChanged$(Parcel parcel, Parcel parcel2) throws RemoteException {
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean zOverrideCarrierRoamingNtnEligibilityChanged = overrideCarrierRoamingNtnEligibilityChanged(z, z2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zOverrideCarrierRoamingNtnEligibilityChanged);
            return true;
        }

        private boolean onTransact$deprovisionSatellite$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SatelliteSubscriberInfo.CREATOR);
            ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            deprovisionSatellite(arrayListCreateTypedArrayList, resultReceiver);
            parcel2.writeNoException();
            return true;
        }
    }
}
