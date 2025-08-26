package com.android.internal.telephony;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.telephony.CellIdentity;
import android.telephony.CellInfo;
import android.telephony.VendorConfigurationState;
import android.telephony.satellite.SemSatelliteState;
import com.android.internal.telephony.IIntegerConsumer;
import java.util.List;

/* loaded from: classes4.dex */
public interface ISemTelephony extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ISemTelephony";

    public static class Default implements ISemTelephony {
        @Override // com.android.internal.telephony.ISemTelephony
        public byte[] NSRI_requestProc(int i, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int cfrmCpaiFeatureInfo(int i, int i2, int i3, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean changeIccSimPersoPassword(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean changeIccSimPersoPasswordForSubId(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String checkCallControl(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int checkNSRIUSIMstate_int() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void dialForSubscriber(int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int evtCpaiDataGathering(int i, int i2, byte[] bArr) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int execCpaiModelUpdate(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getActivationDay(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public List<CellInfo> getAllCellInfoBySubId(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public List<CellInfo> getAllCellInfoForPhone(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public byte[] getAtr(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getCdmaMinForOtasp(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public CellIdentity getCellLocationBySubId(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public CellIdentity getCellLocationForPhone(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getCpaiFeatureInfo(int i, int i2, byte[] bArr) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getCpaiModelVersion(int i, byte[] bArr) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public byte[] getCurrentUATI() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean getDataRoamingEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getDisable2g() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getEuimid() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean getFDNavailable(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean getIccUsimPersoEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean getIccUsimPersoEnabledForSubId(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getIpAddressFromLinkProp(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getLastNetworkCountryIsoForPhone(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getLteCsCapa(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getMobileQualityInformation(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getNetworkStatusDisplayOption(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public long getNextRetryTime() throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getNrMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean getNtnSmsSupported() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean getSdnAvailable() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getSecondaryImei(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getSimPinRetryForSubscriber(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getSimPukRetryForSubscriber(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getSupportUacType(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean getSupportedNrca(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getUaUap(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public VendorConfigurationState getVendorConfigState(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getVoNRMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int invokeOemRilRequestRawForPhone(int i, byte[] bArr, byte[] bArr2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int invokeOemRilRequestRawForSubscriber(int i, byte[] bArr, byte[] bArr2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean isEmergencyNumberBySubId(int i, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean isMmiForSubscriber(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean isSimFDNEnabledForSubscriber(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean isSupportLteCapaOptionC(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean isVideoCall() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void reloadTestEmergencyNumber() throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void requestModemActivityInfo(ResultReceiver resultReceiver, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void resetNetworkSettings(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String semGetSatelliteImei(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public SemSatelliteState semGetSatelliteState(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void semRequestSatelliteMode(int i, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int sendRequestToRIL(byte[] bArr, byte[] bArr2, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void sendVolteState(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void setAllowDataDuringCall(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int setCpaiDataGathering(int i, int i2, int i3, int i4, int i5) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int setCpaiDevAppMessage(int i, int i2, int i3, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setDisable2g(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void setEPSLOCI(byte[] bArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void setGbaBootstrappingParams(int i, byte[] bArr, String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setIccSimPersoEnabled(boolean z, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setIccSimPersoEnabledForSubId(int i, boolean z, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setNrMode(int i, int i2, boolean z, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setSimOnOffForSlot(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setTransmitPowerExt(long j, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setTransmitPowerWithDSI(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setTransmitPowerWithFlag(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setVoNRMode(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public byte[] simCheck(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public byte[] sms_NSRI_decryptsms(int i, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public byte[] sms_NSRI_decryptsmsintxside(int i, String str, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public byte[] sms_NSRI_encryptsms(int i, String str, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean supplyPerso(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean supplyPersoForSubId(int i, String str) throws RemoteException {
            return false;
        }
    }

    byte[] NSRI_requestProc(int i, byte[] bArr) throws RemoteException;

    int cfrmCpaiFeatureInfo(int i, int i2, int i3, String str) throws RemoteException;

    boolean changeIccSimPersoPassword(String str, String str2) throws RemoteException;

    boolean changeIccSimPersoPasswordForSubId(int i, String str, String str2) throws RemoteException;

    String checkCallControl(int i, String str) throws RemoteException;

    int checkNSRIUSIMstate_int() throws RemoteException;

    void dialForSubscriber(int i, String str) throws RemoteException;

    int evtCpaiDataGathering(int i, int i2, byte[] bArr) throws RemoteException;

    int execCpaiModelUpdate(int i, int i2) throws RemoteException;

    String getActivationDay(String str, String str2) throws RemoteException;

    List<CellInfo> getAllCellInfoBySubId(int i, String str, String str2) throws RemoteException;

    List<CellInfo> getAllCellInfoForPhone(int i, String str, String str2) throws RemoteException;

    byte[] getAtr(int i) throws RemoteException;

    String getCdmaMinForOtasp(int i) throws RemoteException;

    CellIdentity getCellLocationBySubId(int i, String str, String str2) throws RemoteException;

    CellIdentity getCellLocationForPhone(int i, String str, String str2) throws RemoteException;

    int getCpaiFeatureInfo(int i, int i2, byte[] bArr) throws RemoteException;

    int getCpaiModelVersion(int i, byte[] bArr) throws RemoteException;

    byte[] getCurrentUATI() throws RemoteException;

    boolean getDataRoamingEnabled() throws RemoteException;

    int getDisable2g() throws RemoteException;

    String getEuimid() throws RemoteException;

    boolean getFDNavailable(int i) throws RemoteException;

    boolean getIccUsimPersoEnabled() throws RemoteException;

    boolean getIccUsimPersoEnabledForSubId(int i) throws RemoteException;

    String getIpAddressFromLinkProp(String str) throws RemoteException;

    String getLastNetworkCountryIsoForPhone(int i) throws RemoteException;

    int getLteCsCapa(int i) throws RemoteException;

    String getMobileQualityInformation(int i, String str, String str2) throws RemoteException;

    int getNetworkStatusDisplayOption(String str, String str2) throws RemoteException;

    long getNextRetryTime() throws RemoteException;

    int getNrMode(int i) throws RemoteException;

    boolean getNtnSmsSupported() throws RemoteException;

    boolean getSdnAvailable() throws RemoteException;

    String getSecondaryImei(String str, String str2) throws RemoteException;

    int getSimPinRetryForSubscriber(int i) throws RemoteException;

    int getSimPukRetryForSubscriber(int i) throws RemoteException;

    int getSupportUacType(int i) throws RemoteException;

    boolean getSupportedNrca(int i) throws RemoteException;

    String getUaUap(String str) throws RemoteException;

    VendorConfigurationState getVendorConfigState(int i) throws RemoteException;

    int getVoNRMode(int i) throws RemoteException;

    int invokeOemRilRequestRawForPhone(int i, byte[] bArr, byte[] bArr2) throws RemoteException;

    int invokeOemRilRequestRawForSubscriber(int i, byte[] bArr, byte[] bArr2) throws RemoteException;

    boolean isEmergencyNumberBySubId(int i, String str, boolean z) throws RemoteException;

    boolean isMmiForSubscriber(int i, String str) throws RemoteException;

    boolean isSimFDNEnabledForSubscriber(int i) throws RemoteException;

    boolean isSupportLteCapaOptionC(int i) throws RemoteException;

    boolean isVideoCall() throws RemoteException;

    void reloadTestEmergencyNumber() throws RemoteException;

    void requestModemActivityInfo(ResultReceiver resultReceiver, String str) throws RemoteException;

    void resetNetworkSettings(int i) throws RemoteException;

    String semGetSatelliteImei(String str, String str2) throws RemoteException;

    SemSatelliteState semGetSatelliteState(int i) throws RemoteException;

    void semRequestSatelliteMode(int i, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    int sendRequestToRIL(byte[] bArr, byte[] bArr2, int i, int i2) throws RemoteException;

    void sendVolteState(int i, boolean z) throws RemoteException;

    void setAllowDataDuringCall(int i) throws RemoteException;

    int setCpaiDataGathering(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    int setCpaiDevAppMessage(int i, int i2, int i3, String str) throws RemoteException;

    boolean setDisable2g(int i) throws RemoteException;

    void setEPSLOCI(byte[] bArr) throws RemoteException;

    void setGbaBootstrappingParams(int i, byte[] bArr, String str, String str2) throws RemoteException;

    boolean setIccSimPersoEnabled(boolean z, String str) throws RemoteException;

    boolean setIccSimPersoEnabledForSubId(int i, boolean z, String str) throws RemoteException;

    boolean setNrMode(int i, int i2, boolean z, String str) throws RemoteException;

    boolean setSimOnOffForSlot(int i, int i2) throws RemoteException;

    boolean setTransmitPowerExt(long j, boolean z) throws RemoteException;

    boolean setTransmitPowerWithDSI(int i) throws RemoteException;

    boolean setTransmitPowerWithFlag(int i, boolean z) throws RemoteException;

    boolean setVoNRMode(int i, int i2) throws RemoteException;

    byte[] simCheck(int i) throws RemoteException;

    byte[] sms_NSRI_decryptsms(int i, byte[] bArr) throws RemoteException;

    byte[] sms_NSRI_decryptsmsintxside(int i, String str, byte[] bArr) throws RemoteException;

    byte[] sms_NSRI_encryptsms(int i, String str, byte[] bArr) throws RemoteException;

    boolean supplyPerso(String str) throws RemoteException;

    boolean supplyPersoForSubId(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemTelephony {
        static final int TRANSACTION_NSRI_requestProc = 62;
        static final int TRANSACTION_cfrmCpaiFeatureInfo = 69;
        static final int TRANSACTION_changeIccSimPersoPassword = 43;
        static final int TRANSACTION_changeIccSimPersoPasswordForSubId = 44;
        static final int TRANSACTION_checkCallControl = 50;
        static final int TRANSACTION_checkNSRIUSIMstate_int = 61;
        static final int TRANSACTION_dialForSubscriber = 5;
        static final int TRANSACTION_evtCpaiDataGathering = 71;
        static final int TRANSACTION_execCpaiModelUpdate = 67;
        static final int TRANSACTION_getActivationDay = 15;
        static final int TRANSACTION_getAllCellInfoBySubId = 3;
        static final int TRANSACTION_getAllCellInfoForPhone = 4;
        static final int TRANSACTION_getAtr = 49;
        static final int TRANSACTION_getCdmaMinForOtasp = 48;
        static final int TRANSACTION_getCellLocationBySubId = 1;
        static final int TRANSACTION_getCellLocationForPhone = 2;
        static final int TRANSACTION_getCpaiFeatureInfo = 68;
        static final int TRANSACTION_getCpaiModelVersion = 66;
        static final int TRANSACTION_getCurrentUATI = 65;
        static final int TRANSACTION_getDataRoamingEnabled = 63;
        static final int TRANSACTION_getDisable2g = 10;
        static final int TRANSACTION_getEuimid = 47;
        static final int TRANSACTION_getFDNavailable = 51;
        static final int TRANSACTION_getIccUsimPersoEnabled = 41;
        static final int TRANSACTION_getIccUsimPersoEnabledForSubId = 42;
        static final int TRANSACTION_getIpAddressFromLinkProp = 74;
        static final int TRANSACTION_getLastNetworkCountryIsoForPhone = 77;
        static final int TRANSACTION_getLteCsCapa = 18;
        static final int TRANSACTION_getMobileQualityInformation = 73;
        static final int TRANSACTION_getNetworkStatusDisplayOption = 16;
        static final int TRANSACTION_getNextRetryTime = 76;
        static final int TRANSACTION_getNrMode = 21;
        static final int TRANSACTION_getNtnSmsSupported = 31;
        static final int TRANSACTION_getSdnAvailable = 33;
        static final int TRANSACTION_getSecondaryImei = 55;
        static final int TRANSACTION_getSimPinRetryForSubscriber = 39;
        static final int TRANSACTION_getSimPukRetryForSubscriber = 40;
        static final int TRANSACTION_getSupportUacType = 26;
        static final int TRANSACTION_getSupportedNrca = 24;
        static final int TRANSACTION_getUaUap = 57;
        static final int TRANSACTION_getVendorConfigState = 27;
        static final int TRANSACTION_getVoNRMode = 23;
        static final int TRANSACTION_invokeOemRilRequestRawForPhone = 52;
        static final int TRANSACTION_invokeOemRilRequestRawForSubscriber = 53;
        static final int TRANSACTION_isEmergencyNumberBySubId = 6;
        static final int TRANSACTION_isMmiForSubscriber = 17;
        static final int TRANSACTION_isSimFDNEnabledForSubscriber = 38;
        static final int TRANSACTION_isSupportLteCapaOptionC = 25;
        static final int TRANSACTION_isVideoCall = 9;
        static final int TRANSACTION_reloadTestEmergencyNumber = 7;
        static final int TRANSACTION_requestModemActivityInfo = 28;
        static final int TRANSACTION_resetNetworkSettings = 32;
        static final int TRANSACTION_semGetSatelliteImei = 56;
        static final int TRANSACTION_semGetSatelliteState = 30;
        static final int TRANSACTION_semRequestSatelliteMode = 29;
        static final int TRANSACTION_sendRequestToRIL = 34;
        static final int TRANSACTION_sendVolteState = 19;
        static final int TRANSACTION_setAllowDataDuringCall = 8;
        static final int TRANSACTION_setCpaiDataGathering = 70;
        static final int TRANSACTION_setCpaiDevAppMessage = 72;
        static final int TRANSACTION_setDisable2g = 11;
        static final int TRANSACTION_setEPSLOCI = 37;
        static final int TRANSACTION_setGbaBootstrappingParams = 64;
        static final int TRANSACTION_setIccSimPersoEnabled = 45;
        static final int TRANSACTION_setIccSimPersoEnabledForSubId = 46;
        static final int TRANSACTION_setNrMode = 20;
        static final int TRANSACTION_setSimOnOffForSlot = 54;
        static final int TRANSACTION_setTransmitPowerExt = 14;
        static final int TRANSACTION_setTransmitPowerWithDSI = 13;
        static final int TRANSACTION_setTransmitPowerWithFlag = 12;
        static final int TRANSACTION_setVoNRMode = 22;
        static final int TRANSACTION_simCheck = 75;
        static final int TRANSACTION_sms_NSRI_decryptsms = 59;
        static final int TRANSACTION_sms_NSRI_decryptsmsintxside = 60;
        static final int TRANSACTION_sms_NSRI_encryptsms = 58;
        static final int TRANSACTION_supplyPerso = 35;
        static final int TRANSACTION_supplyPersoForSubId = 36;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 76;
        }

        public Stub() {
            attachInterface(this, ISemTelephony.DESCRIPTOR);
        }

        public static ISemTelephony asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemTelephony.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemTelephony)) {
                return (ISemTelephony) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getCellLocationBySubId";
                case 2:
                    return "getCellLocationForPhone";
                case 3:
                    return "getAllCellInfoBySubId";
                case 4:
                    return "getAllCellInfoForPhone";
                case 5:
                    return "dialForSubscriber";
                case 6:
                    return "isEmergencyNumberBySubId";
                case 7:
                    return "reloadTestEmergencyNumber";
                case 8:
                    return "setAllowDataDuringCall";
                case 9:
                    return "isVideoCall";
                case 10:
                    return "getDisable2g";
                case 11:
                    return "setDisable2g";
                case 12:
                    return "setTransmitPowerWithFlag";
                case 13:
                    return "setTransmitPowerWithDSI";
                case 14:
                    return "setTransmitPowerExt";
                case 15:
                    return "getActivationDay";
                case 16:
                    return "getNetworkStatusDisplayOption";
                case 17:
                    return "isMmiForSubscriber";
                case 18:
                    return "getLteCsCapa";
                case 19:
                    return "sendVolteState";
                case 20:
                    return "setNrMode";
                case 21:
                    return "getNrMode";
                case 22:
                    return "setVoNRMode";
                case 23:
                    return "getVoNRMode";
                case 24:
                    return "getSupportedNrca";
                case 25:
                    return "isSupportLteCapaOptionC";
                case 26:
                    return "getSupportUacType";
                case 27:
                    return "getVendorConfigState";
                case 28:
                    return "requestModemActivityInfo";
                case 29:
                    return "semRequestSatelliteMode";
                case 30:
                    return "semGetSatelliteState";
                case 31:
                    return "getNtnSmsSupported";
                case 32:
                    return "resetNetworkSettings";
                case 33:
                    return "getSdnAvailable";
                case 34:
                    return "sendRequestToRIL";
                case 35:
                    return "supplyPerso";
                case 36:
                    return "supplyPersoForSubId";
                case 37:
                    return "setEPSLOCI";
                case 38:
                    return "isSimFDNEnabledForSubscriber";
                case 39:
                    return "getSimPinRetryForSubscriber";
                case 40:
                    return "getSimPukRetryForSubscriber";
                case 41:
                    return "getIccUsimPersoEnabled";
                case 42:
                    return "getIccUsimPersoEnabledForSubId";
                case 43:
                    return "changeIccSimPersoPassword";
                case 44:
                    return "changeIccSimPersoPasswordForSubId";
                case 45:
                    return "setIccSimPersoEnabled";
                case 46:
                    return "setIccSimPersoEnabledForSubId";
                case 47:
                    return "getEuimid";
                case 48:
                    return "getCdmaMinForOtasp";
                case 49:
                    return "getAtr";
                case 50:
                    return "checkCallControl";
                case 51:
                    return "getFDNavailable";
                case 52:
                    return "invokeOemRilRequestRawForPhone";
                case 53:
                    return "invokeOemRilRequestRawForSubscriber";
                case 54:
                    return "setSimOnOffForSlot";
                case 55:
                    return "getSecondaryImei";
                case 56:
                    return "semGetSatelliteImei";
                case 57:
                    return "getUaUap";
                case 58:
                    return "sms_NSRI_encryptsms";
                case 59:
                    return "sms_NSRI_decryptsms";
                case 60:
                    return "sms_NSRI_decryptsmsintxside";
                case 61:
                    return "checkNSRIUSIMstate_int";
                case 62:
                    return "NSRI_requestProc";
                case 63:
                    return "getDataRoamingEnabled";
                case 64:
                    return "setGbaBootstrappingParams";
                case 65:
                    return "getCurrentUATI";
                case 66:
                    return "getCpaiModelVersion";
                case 67:
                    return "execCpaiModelUpdate";
                case 68:
                    return "getCpaiFeatureInfo";
                case 69:
                    return "cfrmCpaiFeatureInfo";
                case 70:
                    return "setCpaiDataGathering";
                case 71:
                    return "evtCpaiDataGathering";
                case 72:
                    return "setCpaiDevAppMessage";
                case 73:
                    return "getMobileQualityInformation";
                case 74:
                    return "getIpAddressFromLinkProp";
                case 75:
                    return "simCheck";
                case 76:
                    return "getNextRetryTime";
                case 77:
                    return "getLastNetworkCountryIsoForPhone";
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
            byte[] bArr;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemTelephony.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemTelephony.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CellIdentity cellLocationBySubId = getCellLocationBySubId(i3, string, string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cellLocationBySubId, 1);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CellIdentity cellLocationForPhone = getCellLocationForPhone(i4, string3, string4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cellLocationForPhone, 1);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<CellInfo> allCellInfoBySubId = getAllCellInfoBySubId(i5, string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allCellInfoBySubId, 1);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<CellInfo> allCellInfoForPhone = getAllCellInfoForPhone(i6, string7, string8);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allCellInfoForPhone, 1);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dialForSubscriber(i7, string9);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    String string10 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsEmergencyNumberBySubId = isEmergencyNumberBySubId(i8, string10, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEmergencyNumberBySubId);
                    return true;
                case 7:
                    reloadTestEmergencyNumber();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAllowDataDuringCall(i9);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    boolean zIsVideoCall = isVideoCall();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVideoCall);
                    return true;
                case 10:
                    int disable2g = getDisable2g();
                    parcel2.writeNoException();
                    parcel2.writeInt(disable2g);
                    return true;
                case 11:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean disable2g2 = setDisable2g(i10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disable2g2);
                    return true;
                case 12:
                    int i11 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean transmitPowerWithFlag = setTransmitPowerWithFlag(i11, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(transmitPowerWithFlag);
                    return true;
                case 13:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean transmitPowerWithDSI = setTransmitPowerWithDSI(i12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(transmitPowerWithDSI);
                    return true;
                case 14:
                    long j = parcel.readLong();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean transmitPowerExt = setTransmitPowerExt(j, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(transmitPowerExt);
                    return true;
                case 15:
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String activationDay = getActivationDay(string11, string12);
                    parcel2.writeNoException();
                    parcel2.writeString(activationDay);
                    return true;
                case 16:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int networkStatusDisplayOption = getNetworkStatusDisplayOption(string13, string14);
                    parcel2.writeNoException();
                    parcel2.writeInt(networkStatusDisplayOption);
                    return true;
                case 17:
                    int i13 = parcel.readInt();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsMmiForSubscriber = isMmiForSubscriber(i13, string15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMmiForSubscriber);
                    return true;
                case 18:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lteCsCapa = getLteCsCapa(i14);
                    parcel2.writeNoException();
                    parcel2.writeInt(lteCsCapa);
                    return true;
                case 19:
                    int i15 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendVolteState(i15, z4);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean nrMode = setNrMode(i16, i17, z5, string16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nrMode);
                    return true;
                case 21:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int nrMode2 = getNrMode(i18);
                    parcel2.writeNoException();
                    parcel2.writeInt(nrMode2);
                    return true;
                case 22:
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean voNRMode = setVoNRMode(i19, i20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(voNRMode);
                    return true;
                case 23:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int voNRMode2 = getVoNRMode(i21);
                    parcel2.writeNoException();
                    parcel2.writeInt(voNRMode2);
                    return true;
                case 24:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean supportedNrca = getSupportedNrca(i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportedNrca);
                    return true;
                case 25:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSupportLteCapaOptionC = isSupportLteCapaOptionC(i23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSupportLteCapaOptionC);
                    return true;
                case 26:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int supportUacType = getSupportUacType(i24);
                    parcel2.writeNoException();
                    parcel2.writeInt(supportUacType);
                    return true;
                case 27:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VendorConfigurationState vendorConfigState = getVendorConfigState(i25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vendorConfigState, 1);
                    return true;
                case 28:
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestModemActivityInfo(resultReceiver, string17);
                    return true;
                case 29:
                    int i26 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    semRequestSatelliteMode(i26, z6, iIntegerConsumerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemSatelliteState semSatelliteStateSemGetSatelliteState = semGetSatelliteState(i27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semSatelliteStateSemGetSatelliteState, 1);
                    return true;
                case 31:
                    boolean ntnSmsSupported = getNtnSmsSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ntnSmsSupported);
                    return true;
                case 32:
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetNetworkSettings(i28);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    boolean sdnAvailable = getSdnAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sdnAvailable);
                    return true;
                case 34:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iSendRequestToRIL = sendRequestToRIL(bArrCreateByteArray, bArrCreateByteArray2, i29, i30);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSendRequestToRIL);
                    parcel2.writeByteArray(bArrCreateByteArray2);
                    return true;
                case 35:
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zSupplyPerso = supplyPerso(string18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSupplyPerso);
                    return true;
                case 36:
                    int i31 = parcel.readInt();
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zSupplyPersoForSubId = supplyPersoForSubId(i31, string19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSupplyPersoForSubId);
                    return true;
                case 37:
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setEPSLOCI(bArrCreateByteArray3);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSimFDNEnabledForSubscriber = isSimFDNEnabledForSubscriber(i32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSimFDNEnabledForSubscriber);
                    return true;
                case 39:
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int simPinRetryForSubscriber = getSimPinRetryForSubscriber(i33);
                    parcel2.writeNoException();
                    parcel2.writeInt(simPinRetryForSubscriber);
                    return true;
                case 40:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int simPukRetryForSubscriber = getSimPukRetryForSubscriber(i34);
                    parcel2.writeNoException();
                    parcel2.writeInt(simPukRetryForSubscriber);
                    return true;
                case 41:
                    boolean iccUsimPersoEnabled = getIccUsimPersoEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(iccUsimPersoEnabled);
                    return true;
                case 42:
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean iccUsimPersoEnabledForSubId = getIccUsimPersoEnabledForSubId(i35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(iccUsimPersoEnabledForSubId);
                    return true;
                case 43:
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zChangeIccSimPersoPassword = changeIccSimPersoPassword(string20, string21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zChangeIccSimPersoPassword);
                    return true;
                case 44:
                    int i36 = parcel.readInt();
                    String string22 = parcel.readString();
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zChangeIccSimPersoPasswordForSubId = changeIccSimPersoPasswordForSubId(i36, string22, string23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zChangeIccSimPersoPasswordForSubId);
                    return true;
                case 45:
                    boolean z7 = parcel.readBoolean();
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean iccSimPersoEnabled = setIccSimPersoEnabled(z7, string24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(iccSimPersoEnabled);
                    return true;
                case 46:
                    int i37 = parcel.readInt();
                    boolean z8 = parcel.readBoolean();
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean iccSimPersoEnabledForSubId = setIccSimPersoEnabledForSubId(i37, z8, string25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(iccSimPersoEnabledForSubId);
                    return true;
                case 47:
                    String euimid = getEuimid();
                    parcel2.writeNoException();
                    parcel2.writeString(euimid);
                    return true;
                case 48:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String cdmaMinForOtasp = getCdmaMinForOtasp(i38);
                    parcel2.writeNoException();
                    parcel2.writeString(cdmaMinForOtasp);
                    return true;
                case 49:
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] atr = getAtr(i39);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(atr);
                    return true;
                case 50:
                    int i40 = parcel.readInt();
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strCheckCallControl = checkCallControl(i40, string26);
                    parcel2.writeNoException();
                    parcel2.writeString(strCheckCallControl);
                    return true;
                case 51:
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean fDNavailable = getFDNavailable(i41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(fDNavailable);
                    return true;
                case 52:
                    int i42 = parcel.readInt();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    int i43 = parcel.readInt();
                    if (i43 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i43);
                    }
                    bArr = i43 >= 0 ? new byte[i43] : null;
                    parcel.enforceNoDataAvail();
                    int iInvokeOemRilRequestRawForPhone = invokeOemRilRequestRawForPhone(i42, bArrCreateByteArray4, bArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(iInvokeOemRilRequestRawForPhone);
                    parcel2.writeByteArray(bArr);
                    return true;
                case 53:
                    int i44 = parcel.readInt();
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    int i45 = parcel.readInt();
                    if (i45 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i45);
                    }
                    bArr = i45 >= 0 ? new byte[i45] : null;
                    parcel.enforceNoDataAvail();
                    int iInvokeOemRilRequestRawForSubscriber = invokeOemRilRequestRawForSubscriber(i44, bArrCreateByteArray5, bArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(iInvokeOemRilRequestRawForSubscriber);
                    parcel2.writeByteArray(bArr);
                    return true;
                case 54:
                    int i46 = parcel.readInt();
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean simOnOffForSlot = setSimOnOffForSlot(i46, i47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(simOnOffForSlot);
                    return true;
                case 55:
                    String string27 = parcel.readString();
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String secondaryImei = getSecondaryImei(string27, string28);
                    parcel2.writeNoException();
                    parcel2.writeString(secondaryImei);
                    return true;
                case 56:
                    String string29 = parcel.readString();
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strSemGetSatelliteImei = semGetSatelliteImei(string29, string30);
                    parcel2.writeNoException();
                    parcel2.writeString(strSemGetSatelliteImei);
                    return true;
                case 57:
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String uaUap = getUaUap(string31);
                    parcel2.writeNoException();
                    parcel2.writeString(uaUap);
                    return true;
                case 58:
                    int i48 = parcel.readInt();
                    String string32 = parcel.readString();
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] bArrSms_NSRI_encryptsms = sms_NSRI_encryptsms(i48, string32, bArrCreateByteArray6);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrSms_NSRI_encryptsms);
                    return true;
                case 59:
                    int i49 = parcel.readInt();
                    byte[] bArrCreateByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] bArrSms_NSRI_decryptsms = sms_NSRI_decryptsms(i49, bArrCreateByteArray7);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrSms_NSRI_decryptsms);
                    return true;
                case 60:
                    int i50 = parcel.readInt();
                    String string33 = parcel.readString();
                    byte[] bArrCreateByteArray8 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] bArrSms_NSRI_decryptsmsintxside = sms_NSRI_decryptsmsintxside(i50, string33, bArrCreateByteArray8);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrSms_NSRI_decryptsmsintxside);
                    return true;
                case 61:
                    int iCheckNSRIUSIMstate_int = checkNSRIUSIMstate_int();
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckNSRIUSIMstate_int);
                    return true;
                case 62:
                    int i51 = parcel.readInt();
                    byte[] bArrCreateByteArray9 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] bArrNSRI_requestProc = NSRI_requestProc(i51, bArrCreateByteArray9);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrNSRI_requestProc);
                    return true;
                case 63:
                    boolean dataRoamingEnabled = getDataRoamingEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataRoamingEnabled);
                    return true;
                case 64:
                    int i52 = parcel.readInt();
                    byte[] bArrCreateByteArray10 = parcel.createByteArray();
                    String string34 = parcel.readString();
                    String string35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setGbaBootstrappingParams(i52, bArrCreateByteArray10, string34, string35);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    byte[] currentUATI = getCurrentUATI();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(currentUATI);
                    return true;
                case 66:
                    int i53 = parcel.readInt();
                    int i54 = parcel.readInt();
                    if (i54 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i54);
                    }
                    bArr = i54 >= 0 ? new byte[i54] : null;
                    parcel.enforceNoDataAvail();
                    int cpaiModelVersion = getCpaiModelVersion(i53, bArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(cpaiModelVersion);
                    parcel2.writeByteArray(bArr);
                    return true;
                case 67:
                    int i55 = parcel.readInt();
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iExecCpaiModelUpdate = execCpaiModelUpdate(i55, i56);
                    parcel2.writeNoException();
                    parcel2.writeInt(iExecCpaiModelUpdate);
                    return true;
                case 68:
                    int i57 = parcel.readInt();
                    int i58 = parcel.readInt();
                    int i59 = parcel.readInt();
                    if (i59 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i59);
                    }
                    bArr = i59 >= 0 ? new byte[i59] : null;
                    parcel.enforceNoDataAvail();
                    int cpaiFeatureInfo = getCpaiFeatureInfo(i57, i58, bArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(cpaiFeatureInfo);
                    parcel2.writeByteArray(bArr);
                    return true;
                case 69:
                    int i60 = parcel.readInt();
                    int i61 = parcel.readInt();
                    int i62 = parcel.readInt();
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCfrmCpaiFeatureInfo = cfrmCpaiFeatureInfo(i60, i61, i62, string36);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCfrmCpaiFeatureInfo);
                    return true;
                case 70:
                    int i63 = parcel.readInt();
                    int i64 = parcel.readInt();
                    int i65 = parcel.readInt();
                    int i66 = parcel.readInt();
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int cpaiDataGathering = setCpaiDataGathering(i63, i64, i65, i66, i67);
                    parcel2.writeNoException();
                    parcel2.writeInt(cpaiDataGathering);
                    return true;
                case 71:
                    int i68 = parcel.readInt();
                    int i69 = parcel.readInt();
                    byte[] bArrCreateByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int iEvtCpaiDataGathering = evtCpaiDataGathering(i68, i69, bArrCreateByteArray11);
                    parcel2.writeNoException();
                    parcel2.writeInt(iEvtCpaiDataGathering);
                    return true;
                case 72:
                    int i70 = parcel.readInt();
                    int i71 = parcel.readInt();
                    int i72 = parcel.readInt();
                    String string37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cpaiDevAppMessage = setCpaiDevAppMessage(i70, i71, i72, string37);
                    parcel2.writeNoException();
                    parcel2.writeInt(cpaiDevAppMessage);
                    return true;
                case 73:
                    int i73 = parcel.readInt();
                    String string38 = parcel.readString();
                    String string39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String mobileQualityInformation = getMobileQualityInformation(i73, string38, string39);
                    parcel2.writeNoException();
                    parcel2.writeString(mobileQualityInformation);
                    return true;
                case 74:
                    String string40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String ipAddressFromLinkProp = getIpAddressFromLinkProp(string40);
                    parcel2.writeNoException();
                    parcel2.writeString(ipAddressFromLinkProp);
                    return true;
                case 75:
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] bArrSimCheck = simCheck(i74);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrSimCheck);
                    return true;
                case 76:
                    long nextRetryTime = getNextRetryTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(nextRetryTime);
                    return true;
                case 77:
                    int i75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String lastNetworkCountryIsoForPhone = getLastNetworkCountryIsoForPhone(i75);
                    parcel2.writeNoException();
                    parcel2.writeString(lastNetworkCountryIsoForPhone);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemTelephony {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemTelephony.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public CellIdentity getCellLocationBySubId(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CellIdentity) parcelObtain2.readTypedObject(CellIdentity.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public CellIdentity getCellLocationForPhone(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CellIdentity) parcelObtain2.readTypedObject(CellIdentity.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public List<CellInfo> getAllCellInfoBySubId(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(CellInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public List<CellInfo> getAllCellInfoForPhone(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(CellInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void dialForSubscriber(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isEmergencyNumberBySubId(int i, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void reloadTestEmergencyNumber() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void setAllowDataDuringCall(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isVideoCall() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getDisable2g() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setDisable2g(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setTransmitPowerWithFlag(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setTransmitPowerWithDSI(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setTransmitPowerExt(long j, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getActivationDay(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getNetworkStatusDisplayOption(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isMmiForSubscriber(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getLteCsCapa(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void sendVolteState(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setNrMode(int i, int i2, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getNrMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setVoNRMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
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

            @Override // com.android.internal.telephony.ISemTelephony
            public int getVoNRMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getSupportedNrca(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isSupportLteCapaOptionC(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getSupportUacType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public VendorConfigurationState getVendorConfigState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VendorConfigurationState) parcelObtain2.readTypedObject(VendorConfigurationState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void requestModemActivityInfo(ResultReceiver resultReceiver, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void semRequestSatelliteMode(int i, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public SemSatelliteState semGetSatelliteState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemSatelliteState) parcelObtain2.readTypedObject(SemSatelliteState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getNtnSmsSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void resetNetworkSettings(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getSdnAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int sendRequestToRIL(byte[] bArr, byte[] bArr2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i3 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr2);
                    return i3;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean supplyPerso(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean supplyPersoForSubId(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void setEPSLOCI(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isSimFDNEnabledForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getSimPinRetryForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getSimPukRetryForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getIccUsimPersoEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getIccUsimPersoEnabledForSubId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean changeIccSimPersoPassword(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean changeIccSimPersoPasswordForSubId(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setIccSimPersoEnabled(boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setIccSimPersoEnabledForSubId(int i, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getEuimid() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getCdmaMinForOtasp(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] getAtr(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String checkCallControl(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getFDNavailable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int invokeOemRilRequestRawForPhone(int i, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(bArr2.length);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i2 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr2);
                    return i2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int invokeOemRilRequestRawForSubscriber(int i, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(bArr2.length);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i2 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr2);
                    return i2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setSimOnOffForSlot(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getSecondaryImei(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String semGetSatelliteImei(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getUaUap(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] sms_NSRI_encryptsms(int i, String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] sms_NSRI_decryptsms(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] sms_NSRI_decryptsmsintxside(int i, String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int checkNSRIUSIMstate_int() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] NSRI_requestProc(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getDataRoamingEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void setGbaBootstrappingParams(int i, byte[] bArr, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] getCurrentUATI() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getCpaiModelVersion(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(bArr.length);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i2 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr);
                    return i2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int execCpaiModelUpdate(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getCpaiFeatureInfo(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(bArr.length);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i3 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr);
                    return i3;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int cfrmCpaiFeatureInfo(int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int setCpaiDataGathering(int i, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int evtCpaiDataGathering(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int setCpaiDevAppMessage(int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getMobileQualityInformation(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getIpAddressFromLinkProp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] simCheck(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public long getNextRetryTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getLastNetworkCountryIsoForPhone(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
