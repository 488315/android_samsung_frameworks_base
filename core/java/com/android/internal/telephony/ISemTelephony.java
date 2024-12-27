package com.android.internal.telephony;

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

/* loaded from: classes5.dex */
public interface ISemTelephony extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ISemTelephony";

    byte[] NSRI_requestProc(int i, byte[] bArr) throws RemoteException;

    boolean changeIccSimPersoPassword(String str, String str2) throws RemoteException;

    boolean changeIccSimPersoPasswordForSubId(int i, String str, String str2) throws RemoteException;

    String checkCallControl(int i, String str) throws RemoteException;

    int checkNSRIUSIMstate_int() throws RemoteException;

    void dialForSubscriber(int i, String str) throws RemoteException;

    String getActivationDay(String str, String str2) throws RemoteException;

    List<CellInfo> getAllCellInfoBySubId(int i, String str, String str2) throws RemoteException;

    List<CellInfo> getAllCellInfoForPhone(int i, String str, String str2) throws RemoteException;

    byte[] getAtr(int i) throws RemoteException;

    String getCdmaMinForOtasp(int i) throws RemoteException;

    CellIdentity getCellLocationBySubId(int i, String str, String str2) throws RemoteException;

    CellIdentity getCellLocationForPhone(int i, String str, String str2) throws RemoteException;

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

    String getSatelliteImei(String str, String str2) throws RemoteException;

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

    SemSatelliteState semGetSatelliteState(int i) throws RemoteException;

    void semRequestSatelliteMode(int i, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    int sendRequestToRIL(byte[] bArr, byte[] bArr2, int i, int i2) throws RemoteException;

    void sendVolteState(int i, boolean z) throws RemoteException;

    void setAllowDataDuringCall(int i) throws RemoteException;

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

    public static class Default implements ISemTelephony {
        @Override // com.android.internal.telephony.ISemTelephony
        public CellIdentity getCellLocationBySubId(int subId, String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public CellIdentity getCellLocationForPhone(int phoneId, String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public List<CellInfo> getAllCellInfoBySubId(int subId, String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public List<CellInfo> getAllCellInfoForPhone(int phoneId, String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void dialForSubscriber(int subId, String number) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean isEmergencyNumberBySubId(int subId, String number, boolean exactMatch) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void reloadTestEmergencyNumber() throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void setAllowDataDuringCall(int enable) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean isVideoCall() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getDisable2g() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setDisable2g(int state) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setTransmitPowerWithFlag(int powerLevel, boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setTransmitPowerWithDSI(int dsiLevel) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setTransmitPowerExt(long powerLevel, boolean enable) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getActivationDay(String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getNetworkStatusDisplayOption(String callingPackage, String callingFeatureId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean isMmiForSubscriber(int subId, String dialString) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getLteCsCapa(int phoneId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void sendVolteState(int subId, boolean isVolteOn) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setNrMode(int phoneId, int mode, boolean force, String callingPackage) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getNrMode(int phoneId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setVoNRMode(int phoneId, int mode) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getVoNRMode(int phoneId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean getSupportedNrca(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean isSupportLteCapaOptionC(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getSupportUacType(int phoneId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public VendorConfigurationState getVendorConfigState(int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void requestModemActivityInfo(ResultReceiver result, String callingPackage) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void semRequestSatelliteMode(int phoneId, boolean enable, IIntegerConsumer callback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public SemSatelliteState semGetSatelliteState(int phoneId) throws RemoteException {
            return null;
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
        public int sendRequestToRIL(byte[] data, byte[] response, int what, int phoneId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean supplyPerso(String passwd) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean supplyPersoForSubId(int subId, String passwd) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void setEPSLOCI(byte[] newEpsloci) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean isSimFDNEnabledForSubscriber(int subId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getSimPinRetryForSubscriber(int subId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int getSimPukRetryForSubscriber(int subId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean getIccUsimPersoEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean getIccUsimPersoEnabledForSubId(int subId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean changeIccSimPersoPassword(String oldPassword, String newPassword) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean changeIccSimPersoPasswordForSubId(int subId, String oldPassword, String newPassword) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setIccSimPersoEnabled(boolean enabled, String password) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setIccSimPersoEnabledForSubId(int subId, boolean enabled, String password) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getEuimid() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getCdmaMinForOtasp(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public byte[] getAtr(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String checkCallControl(int subId, String dialNum) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean getFDNavailable(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int invokeOemRilRequestRawForPhone(int phoneId, byte[] request, byte[] response) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int invokeOemRilRequestRawForSubscriber(int subId, byte[] request, byte[] response) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean setSimOnOffForSlot(int slotIndex, int mode) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getSecondaryImei(String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getSatelliteImei(String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getUaUap(String type) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public byte[] sms_NSRI_encryptsms(int in_len, String potherphonenumber, byte[] message) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public byte[] sms_NSRI_decryptsms(int in_len, byte[] message) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public byte[] sms_NSRI_decryptsmsintxside(int in_len, String potherphonenumber, byte[] message) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public int checkNSRIUSIMstate_int() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public byte[] NSRI_requestProc(int datalen, byte[] adata) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public boolean getDataRoamingEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public void setGbaBootstrappingParams(int subId, byte[] rand, String btid, String keyLifetime) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public byte[] getCurrentUATI() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getMobileQualityInformation(int phoneId, String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getIpAddressFromLinkProp(String nwkType) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public byte[] simCheck(int slotIndex) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public long getNextRetryTime() throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.telephony.ISemTelephony
        public String getLastNetworkCountryIsoForPhone(int phoneId) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemTelephony {
        static final int TRANSACTION_NSRI_requestProc = 61;
        static final int TRANSACTION_changeIccSimPersoPassword = 42;
        static final int TRANSACTION_changeIccSimPersoPasswordForSubId = 43;
        static final int TRANSACTION_checkCallControl = 49;
        static final int TRANSACTION_checkNSRIUSIMstate_int = 60;
        static final int TRANSACTION_dialForSubscriber = 5;
        static final int TRANSACTION_getActivationDay = 15;
        static final int TRANSACTION_getAllCellInfoBySubId = 3;
        static final int TRANSACTION_getAllCellInfoForPhone = 4;
        static final int TRANSACTION_getAtr = 48;
        static final int TRANSACTION_getCdmaMinForOtasp = 47;
        static final int TRANSACTION_getCellLocationBySubId = 1;
        static final int TRANSACTION_getCellLocationForPhone = 2;
        static final int TRANSACTION_getCurrentUATI = 64;
        static final int TRANSACTION_getDataRoamingEnabled = 62;
        static final int TRANSACTION_getDisable2g = 10;
        static final int TRANSACTION_getEuimid = 46;
        static final int TRANSACTION_getFDNavailable = 50;
        static final int TRANSACTION_getIccUsimPersoEnabled = 40;
        static final int TRANSACTION_getIccUsimPersoEnabledForSubId = 41;
        static final int TRANSACTION_getIpAddressFromLinkProp = 66;
        static final int TRANSACTION_getLastNetworkCountryIsoForPhone = 69;
        static final int TRANSACTION_getLteCsCapa = 18;
        static final int TRANSACTION_getMobileQualityInformation = 65;
        static final int TRANSACTION_getNetworkStatusDisplayOption = 16;
        static final int TRANSACTION_getNextRetryTime = 68;
        static final int TRANSACTION_getNrMode = 21;
        static final int TRANSACTION_getNtnSmsSupported = 31;
        static final int TRANSACTION_getSatelliteImei = 55;
        static final int TRANSACTION_getSdnAvailable = 32;
        static final int TRANSACTION_getSecondaryImei = 54;
        static final int TRANSACTION_getSimPinRetryForSubscriber = 38;
        static final int TRANSACTION_getSimPukRetryForSubscriber = 39;
        static final int TRANSACTION_getSupportUacType = 26;
        static final int TRANSACTION_getSupportedNrca = 24;
        static final int TRANSACTION_getUaUap = 56;
        static final int TRANSACTION_getVendorConfigState = 27;
        static final int TRANSACTION_getVoNRMode = 23;
        static final int TRANSACTION_invokeOemRilRequestRawForPhone = 51;
        static final int TRANSACTION_invokeOemRilRequestRawForSubscriber = 52;
        static final int TRANSACTION_isEmergencyNumberBySubId = 6;
        static final int TRANSACTION_isMmiForSubscriber = 17;
        static final int TRANSACTION_isSimFDNEnabledForSubscriber = 37;
        static final int TRANSACTION_isSupportLteCapaOptionC = 25;
        static final int TRANSACTION_isVideoCall = 9;
        static final int TRANSACTION_reloadTestEmergencyNumber = 7;
        static final int TRANSACTION_requestModemActivityInfo = 28;
        static final int TRANSACTION_semGetSatelliteState = 30;
        static final int TRANSACTION_semRequestSatelliteMode = 29;
        static final int TRANSACTION_sendRequestToRIL = 33;
        static final int TRANSACTION_sendVolteState = 19;
        static final int TRANSACTION_setAllowDataDuringCall = 8;
        static final int TRANSACTION_setDisable2g = 11;
        static final int TRANSACTION_setEPSLOCI = 36;
        static final int TRANSACTION_setGbaBootstrappingParams = 63;
        static final int TRANSACTION_setIccSimPersoEnabled = 44;
        static final int TRANSACTION_setIccSimPersoEnabledForSubId = 45;
        static final int TRANSACTION_setNrMode = 20;
        static final int TRANSACTION_setSimOnOffForSlot = 53;
        static final int TRANSACTION_setTransmitPowerExt = 14;
        static final int TRANSACTION_setTransmitPowerWithDSI = 13;
        static final int TRANSACTION_setTransmitPowerWithFlag = 12;
        static final int TRANSACTION_setVoNRMode = 22;
        static final int TRANSACTION_simCheck = 67;
        static final int TRANSACTION_sms_NSRI_decryptsms = 58;
        static final int TRANSACTION_sms_NSRI_decryptsmsintxside = 59;
        static final int TRANSACTION_sms_NSRI_encryptsms = 57;
        static final int TRANSACTION_supplyPerso = 34;
        static final int TRANSACTION_supplyPersoForSubId = 35;

        public Stub() {
            attachInterface(this, ISemTelephony.DESCRIPTOR);
        }

        public static ISemTelephony asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemTelephony.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemTelephony)) {
                return (ISemTelephony) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
                    return "getSdnAvailable";
                case 33:
                    return "sendRequestToRIL";
                case 34:
                    return "supplyPerso";
                case 35:
                    return "supplyPersoForSubId";
                case 36:
                    return "setEPSLOCI";
                case 37:
                    return "isSimFDNEnabledForSubscriber";
                case 38:
                    return "getSimPinRetryForSubscriber";
                case 39:
                    return "getSimPukRetryForSubscriber";
                case 40:
                    return "getIccUsimPersoEnabled";
                case 41:
                    return "getIccUsimPersoEnabledForSubId";
                case 42:
                    return "changeIccSimPersoPassword";
                case 43:
                    return "changeIccSimPersoPasswordForSubId";
                case 44:
                    return "setIccSimPersoEnabled";
                case 45:
                    return "setIccSimPersoEnabledForSubId";
                case 46:
                    return "getEuimid";
                case 47:
                    return "getCdmaMinForOtasp";
                case 48:
                    return "getAtr";
                case 49:
                    return "checkCallControl";
                case 50:
                    return "getFDNavailable";
                case 51:
                    return "invokeOemRilRequestRawForPhone";
                case 52:
                    return "invokeOemRilRequestRawForSubscriber";
                case 53:
                    return "setSimOnOffForSlot";
                case 54:
                    return "getSecondaryImei";
                case 55:
                    return "getSatelliteImei";
                case 56:
                    return "getUaUap";
                case 57:
                    return "sms_NSRI_encryptsms";
                case 58:
                    return "sms_NSRI_decryptsms";
                case 59:
                    return "sms_NSRI_decryptsmsintxside";
                case 60:
                    return "checkNSRIUSIMstate_int";
                case 61:
                    return "NSRI_requestProc";
                case 62:
                    return "getDataRoamingEnabled";
                case 63:
                    return "setGbaBootstrappingParams";
                case 64:
                    return "getCurrentUATI";
                case 65:
                    return "getMobileQualityInformation";
                case 66:
                    return "getIpAddressFromLinkProp";
                case 67:
                    return "simCheck";
                case 68:
                    return "getNextRetryTime";
                case 69:
                    return "getLastNetworkCountryIsoForPhone";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            byte[] _arg2;
            byte[] _arg22;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemTelephony.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemTelephony.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    String _arg23 = data.readString();
                    data.enforceNoDataAvail();
                    CellIdentity _result = getCellLocationBySubId(_arg0, _arg1, _arg23);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    String _arg12 = data.readString();
                    String _arg24 = data.readString();
                    data.enforceNoDataAvail();
                    CellIdentity _result2 = getCellLocationForPhone(_arg02, _arg12, _arg24);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    String _arg13 = data.readString();
                    String _arg25 = data.readString();
                    data.enforceNoDataAvail();
                    List<CellInfo> _result3 = getAllCellInfoBySubId(_arg03, _arg13, _arg25);
                    reply.writeNoException();
                    reply.writeTypedList(_result3, 1);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    String _arg14 = data.readString();
                    String _arg26 = data.readString();
                    data.enforceNoDataAvail();
                    List<CellInfo> _result4 = getAllCellInfoForPhone(_arg04, _arg14, _arg26);
                    reply.writeNoException();
                    reply.writeTypedList(_result4, 1);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    dialForSubscriber(_arg05, _arg15);
                    reply.writeNoException();
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    String _arg16 = data.readString();
                    boolean _arg27 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result5 = isEmergencyNumberBySubId(_arg06, _arg16, _arg27);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 7:
                    reloadTestEmergencyNumber();
                    reply.writeNoException();
                    return true;
                case 8:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    setAllowDataDuringCall(_arg07);
                    reply.writeNoException();
                    return true;
                case 9:
                    boolean _result6 = isVideoCall();
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 10:
                    int _result7 = getDisable2g();
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 11:
                    int _arg08 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 = setDisable2g(_arg08);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 12:
                    int _arg09 = data.readInt();
                    boolean _arg17 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result9 = setTransmitPowerWithFlag(_arg09, _arg17);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 13:
                    int _arg010 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result10 = setTransmitPowerWithDSI(_arg010);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 14:
                    long _arg011 = data.readLong();
                    boolean _arg18 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result11 = setTransmitPowerExt(_arg011, _arg18);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 15:
                    String _arg012 = data.readString();
                    String _arg19 = data.readString();
                    data.enforceNoDataAvail();
                    String _result12 = getActivationDay(_arg012, _arg19);
                    reply.writeNoException();
                    reply.writeString(_result12);
                    return true;
                case 16:
                    String _arg013 = data.readString();
                    String _arg110 = data.readString();
                    data.enforceNoDataAvail();
                    int _result13 = getNetworkStatusDisplayOption(_arg013, _arg110);
                    reply.writeNoException();
                    reply.writeInt(_result13);
                    return true;
                case 17:
                    int _arg014 = data.readInt();
                    String _arg111 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result14 = isMmiForSubscriber(_arg014, _arg111);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 18:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result15 = getLteCsCapa(_arg015);
                    reply.writeNoException();
                    reply.writeInt(_result15);
                    return true;
                case 19:
                    int _arg016 = data.readInt();
                    boolean _arg112 = data.readBoolean();
                    data.enforceNoDataAvail();
                    sendVolteState(_arg016, _arg112);
                    reply.writeNoException();
                    return true;
                case 20:
                    int _arg017 = data.readInt();
                    int _arg113 = data.readInt();
                    boolean _arg28 = data.readBoolean();
                    String _arg3 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result16 = setNrMode(_arg017, _arg113, _arg28, _arg3);
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                case 21:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result17 = getNrMode(_arg018);
                    reply.writeNoException();
                    reply.writeInt(_result17);
                    return true;
                case 22:
                    int _arg019 = data.readInt();
                    int _arg114 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result18 = setVoNRMode(_arg019, _arg114);
                    reply.writeNoException();
                    reply.writeBoolean(_result18);
                    return true;
                case 23:
                    int _arg020 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result19 = getVoNRMode(_arg020);
                    reply.writeNoException();
                    reply.writeInt(_result19);
                    return true;
                case 24:
                    int _arg021 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result20 = getSupportedNrca(_arg021);
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 25:
                    int _arg022 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result21 = isSupportLteCapaOptionC(_arg022);
                    reply.writeNoException();
                    reply.writeBoolean(_result21);
                    return true;
                case 26:
                    int _arg023 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result22 = getSupportUacType(_arg023);
                    reply.writeNoException();
                    reply.writeInt(_result22);
                    return true;
                case 27:
                    int _arg024 = data.readInt();
                    data.enforceNoDataAvail();
                    VendorConfigurationState _result23 = getVendorConfigState(_arg024);
                    reply.writeNoException();
                    reply.writeTypedObject(_result23, 1);
                    return true;
                case 28:
                    ResultReceiver _arg025 = (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    String _arg115 = data.readString();
                    data.enforceNoDataAvail();
                    requestModemActivityInfo(_arg025, _arg115);
                    return true;
                case 29:
                    int _arg026 = data.readInt();
                    boolean _arg116 = data.readBoolean();
                    IIntegerConsumer _arg29 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    semRequestSatelliteMode(_arg026, _arg116, _arg29);
                    reply.writeNoException();
                    return true;
                case 30:
                    int _arg027 = data.readInt();
                    data.enforceNoDataAvail();
                    SemSatelliteState _result24 = semGetSatelliteState(_arg027);
                    reply.writeNoException();
                    reply.writeTypedObject(_result24, 1);
                    return true;
                case 31:
                    boolean _result25 = getNtnSmsSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 32:
                    boolean _result26 = getSdnAvailable();
                    reply.writeNoException();
                    reply.writeBoolean(_result26);
                    return true;
                case 33:
                    byte[] _arg028 = data.createByteArray();
                    byte[] _arg117 = data.createByteArray();
                    int _arg210 = data.readInt();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result27 = sendRequestToRIL(_arg028, _arg117, _arg210, _arg32);
                    reply.writeNoException();
                    reply.writeInt(_result27);
                    reply.writeByteArray(_arg117);
                    return true;
                case 34:
                    String _arg029 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result28 = supplyPerso(_arg029);
                    reply.writeNoException();
                    reply.writeBoolean(_result28);
                    return true;
                case 35:
                    int _arg030 = data.readInt();
                    String _arg118 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result29 = supplyPersoForSubId(_arg030, _arg118);
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                case 36:
                    byte[] _arg031 = data.createByteArray();
                    data.enforceNoDataAvail();
                    setEPSLOCI(_arg031);
                    reply.writeNoException();
                    return true;
                case 37:
                    int _arg032 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result30 = isSimFDNEnabledForSubscriber(_arg032);
                    reply.writeNoException();
                    reply.writeBoolean(_result30);
                    return true;
                case 38:
                    int _arg033 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result31 = getSimPinRetryForSubscriber(_arg033);
                    reply.writeNoException();
                    reply.writeInt(_result31);
                    return true;
                case 39:
                    int _arg034 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result32 = getSimPukRetryForSubscriber(_arg034);
                    reply.writeNoException();
                    reply.writeInt(_result32);
                    return true;
                case 40:
                    boolean _result33 = getIccUsimPersoEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result33);
                    return true;
                case 41:
                    int _arg035 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result34 = getIccUsimPersoEnabledForSubId(_arg035);
                    reply.writeNoException();
                    reply.writeBoolean(_result34);
                    return true;
                case 42:
                    String _arg036 = data.readString();
                    String _arg119 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result35 = changeIccSimPersoPassword(_arg036, _arg119);
                    reply.writeNoException();
                    reply.writeBoolean(_result35);
                    return true;
                case 43:
                    int _arg037 = data.readInt();
                    String _arg120 = data.readString();
                    String _arg211 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result36 = changeIccSimPersoPasswordForSubId(_arg037, _arg120, _arg211);
                    reply.writeNoException();
                    reply.writeBoolean(_result36);
                    return true;
                case 44:
                    boolean _arg038 = data.readBoolean();
                    String _arg121 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result37 = setIccSimPersoEnabled(_arg038, _arg121);
                    reply.writeNoException();
                    reply.writeBoolean(_result37);
                    return true;
                case 45:
                    int _arg039 = data.readInt();
                    boolean _arg122 = data.readBoolean();
                    String _arg212 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result38 = setIccSimPersoEnabledForSubId(_arg039, _arg122, _arg212);
                    reply.writeNoException();
                    reply.writeBoolean(_result38);
                    return true;
                case 46:
                    String _result39 = getEuimid();
                    reply.writeNoException();
                    reply.writeString(_result39);
                    return true;
                case 47:
                    int _arg040 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result40 = getCdmaMinForOtasp(_arg040);
                    reply.writeNoException();
                    reply.writeString(_result40);
                    return true;
                case 48:
                    int _arg041 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result41 = getAtr(_arg041);
                    reply.writeNoException();
                    reply.writeByteArray(_result41);
                    return true;
                case 49:
                    int _arg042 = data.readInt();
                    String _arg123 = data.readString();
                    data.enforceNoDataAvail();
                    String _result42 = checkCallControl(_arg042, _arg123);
                    reply.writeNoException();
                    reply.writeString(_result42);
                    return true;
                case 50:
                    int _arg043 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result43 = getFDNavailable(_arg043);
                    reply.writeNoException();
                    reply.writeBoolean(_result43);
                    return true;
                case 51:
                    int _arg044 = data.readInt();
                    byte[] _arg124 = data.createByteArray();
                    int _arg2_length = data.readInt();
                    if (_arg2_length < 0) {
                        _arg2 = null;
                    } else {
                        _arg2 = new byte[_arg2_length];
                    }
                    data.enforceNoDataAvail();
                    int _result44 = invokeOemRilRequestRawForPhone(_arg044, _arg124, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result44);
                    reply.writeByteArray(_arg2);
                    return true;
                case 52:
                    int _arg045 = data.readInt();
                    byte[] _arg125 = data.createByteArray();
                    int _arg2_length2 = data.readInt();
                    if (_arg2_length2 < 0) {
                        _arg22 = null;
                    } else {
                        _arg22 = new byte[_arg2_length2];
                    }
                    data.enforceNoDataAvail();
                    int _result45 = invokeOemRilRequestRawForSubscriber(_arg045, _arg125, _arg22);
                    reply.writeNoException();
                    reply.writeInt(_result45);
                    reply.writeByteArray(_arg22);
                    return true;
                case 53:
                    int _arg046 = data.readInt();
                    int _arg126 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result46 = setSimOnOffForSlot(_arg046, _arg126);
                    reply.writeNoException();
                    reply.writeBoolean(_result46);
                    return true;
                case 54:
                    String _arg047 = data.readString();
                    String _arg127 = data.readString();
                    data.enforceNoDataAvail();
                    String _result47 = getSecondaryImei(_arg047, _arg127);
                    reply.writeNoException();
                    reply.writeString(_result47);
                    return true;
                case 55:
                    String _arg048 = data.readString();
                    String _arg128 = data.readString();
                    data.enforceNoDataAvail();
                    String _result48 = getSatelliteImei(_arg048, _arg128);
                    reply.writeNoException();
                    reply.writeString(_result48);
                    return true;
                case 56:
                    String _arg049 = data.readString();
                    data.enforceNoDataAvail();
                    String _result49 = getUaUap(_arg049);
                    reply.writeNoException();
                    reply.writeString(_result49);
                    return true;
                case 57:
                    int _arg050 = data.readInt();
                    String _arg129 = data.readString();
                    byte[] _arg213 = data.createByteArray();
                    data.enforceNoDataAvail();
                    byte[] _result50 = sms_NSRI_encryptsms(_arg050, _arg129, _arg213);
                    reply.writeNoException();
                    reply.writeByteArray(_result50);
                    return true;
                case 58:
                    int _arg051 = data.readInt();
                    byte[] _arg130 = data.createByteArray();
                    data.enforceNoDataAvail();
                    byte[] _result51 = sms_NSRI_decryptsms(_arg051, _arg130);
                    reply.writeNoException();
                    reply.writeByteArray(_result51);
                    return true;
                case 59:
                    int _arg052 = data.readInt();
                    String _arg131 = data.readString();
                    byte[] _arg214 = data.createByteArray();
                    data.enforceNoDataAvail();
                    byte[] _result52 = sms_NSRI_decryptsmsintxside(_arg052, _arg131, _arg214);
                    reply.writeNoException();
                    reply.writeByteArray(_result52);
                    return true;
                case 60:
                    int _result53 = checkNSRIUSIMstate_int();
                    reply.writeNoException();
                    reply.writeInt(_result53);
                    return true;
                case 61:
                    int _arg053 = data.readInt();
                    byte[] _arg132 = data.createByteArray();
                    data.enforceNoDataAvail();
                    byte[] _result54 = NSRI_requestProc(_arg053, _arg132);
                    reply.writeNoException();
                    reply.writeByteArray(_result54);
                    return true;
                case 62:
                    boolean _result55 = getDataRoamingEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result55);
                    return true;
                case 63:
                    int _arg054 = data.readInt();
                    byte[] _arg133 = data.createByteArray();
                    String _arg215 = data.readString();
                    String _arg33 = data.readString();
                    data.enforceNoDataAvail();
                    setGbaBootstrappingParams(_arg054, _arg133, _arg215, _arg33);
                    reply.writeNoException();
                    return true;
                case 64:
                    byte[] _result56 = getCurrentUATI();
                    reply.writeNoException();
                    reply.writeByteArray(_result56);
                    return true;
                case 65:
                    int _arg055 = data.readInt();
                    String _arg134 = data.readString();
                    String _arg216 = data.readString();
                    data.enforceNoDataAvail();
                    String _result57 = getMobileQualityInformation(_arg055, _arg134, _arg216);
                    reply.writeNoException();
                    reply.writeString(_result57);
                    return true;
                case 66:
                    String _arg056 = data.readString();
                    data.enforceNoDataAvail();
                    String _result58 = getIpAddressFromLinkProp(_arg056);
                    reply.writeNoException();
                    reply.writeString(_result58);
                    return true;
                case 67:
                    int _arg057 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result59 = simCheck(_arg057);
                    reply.writeNoException();
                    reply.writeByteArray(_result59);
                    return true;
                case 68:
                    long _result60 = getNextRetryTime();
                    reply.writeNoException();
                    reply.writeLong(_result60);
                    return true;
                case 69:
                    int _arg058 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result61 = getLastNetworkCountryIsoForPhone(_arg058);
                    reply.writeNoException();
                    reply.writeString(_result61);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemTelephony {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemTelephony.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public CellIdentity getCellLocationBySubId(int subId, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    CellIdentity _result = (CellIdentity) _reply.readTypedObject(CellIdentity.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public CellIdentity getCellLocationForPhone(int phoneId, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    CellIdentity _result = (CellIdentity) _reply.readTypedObject(CellIdentity.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public List<CellInfo> getAllCellInfoBySubId(int subId, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    List<CellInfo> _result = _reply.createTypedArrayList(CellInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public List<CellInfo> getAllCellInfoForPhone(int phoneId, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    List<CellInfo> _result = _reply.createTypedArrayList(CellInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void dialForSubscriber(int subId, String number) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(number);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isEmergencyNumberBySubId(int subId, String number, boolean exactMatch) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(number);
                    _data.writeBoolean(exactMatch);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void reloadTestEmergencyNumber() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void setAllowDataDuringCall(int enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(enable);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isVideoCall() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getDisable2g() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setDisable2g(int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(state);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setTransmitPowerWithFlag(int powerLevel, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(powerLevel);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setTransmitPowerWithDSI(int dsiLevel) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(dsiLevel);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setTransmitPowerExt(long powerLevel, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeLong(powerLevel);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getActivationDay(String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getNetworkStatusDisplayOption(String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isMmiForSubscriber(int subId, String dialString) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(dialString);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getLteCsCapa(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void sendVolteState(int subId, boolean isVolteOn) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeBoolean(isVolteOn);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setNrMode(int phoneId, int mode, boolean force, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(mode);
                    _data.writeBoolean(force);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getNrMode(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setVoNRMode(int phoneId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(mode);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getVoNRMode(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getSupportedNrca(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isSupportLteCapaOptionC(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getSupportUacType(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public VendorConfigurationState getVendorConfigState(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    VendorConfigurationState _result = (VendorConfigurationState) _reply.readTypedObject(VendorConfigurationState.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void requestModemActivityInfo(ResultReceiver result, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(28, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void semRequestSatelliteMode(int phoneId, boolean enable, IIntegerConsumer callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeBoolean(enable);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public SemSatelliteState semGetSatelliteState(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    SemSatelliteState _result = (SemSatelliteState) _reply.readTypedObject(SemSatelliteState.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getNtnSmsSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getSdnAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int sendRequestToRIL(byte[] data, byte[] response, int what, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeByteArray(data);
                    _data.writeByteArray(response);
                    _data.writeInt(what);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(response);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean supplyPerso(String passwd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeString(passwd);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean supplyPersoForSubId(int subId, String passwd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(passwd);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void setEPSLOCI(byte[] newEpsloci) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeByteArray(newEpsloci);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isSimFDNEnabledForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getSimPinRetryForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getSimPukRetryForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getIccUsimPersoEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getIccUsimPersoEnabledForSubId(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean changeIccSimPersoPassword(String oldPassword, String newPassword) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeString(oldPassword);
                    _data.writeString(newPassword);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean changeIccSimPersoPasswordForSubId(int subId, String oldPassword, String newPassword) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(oldPassword);
                    _data.writeString(newPassword);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setIccSimPersoEnabled(boolean enabled, String password) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    _data.writeString(password);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setIccSimPersoEnabledForSubId(int subId, boolean enabled, String password) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeBoolean(enabled);
                    _data.writeString(password);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getEuimid() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getCdmaMinForOtasp(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] getAtr(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String checkCallControl(int subId, String dialNum) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(dialNum);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getFDNavailable(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int invokeOemRilRequestRawForPhone(int phoneId, byte[] request, byte[] response) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeByteArray(request);
                    _data.writeInt(response.length);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(response);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int invokeOemRilRequestRawForSubscriber(int subId, byte[] request, byte[] response) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeByteArray(request);
                    _data.writeInt(response.length);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(response);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setSimOnOffForSlot(int slotIndex, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    _data.writeInt(mode);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getSecondaryImei(String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getSatelliteImei(String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getUaUap(String type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeString(type);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] sms_NSRI_encryptsms(int in_len, String potherphonenumber, byte[] message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(in_len);
                    _data.writeString(potherphonenumber);
                    _data.writeByteArray(message);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] sms_NSRI_decryptsms(int in_len, byte[] message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(in_len);
                    _data.writeByteArray(message);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] sms_NSRI_decryptsmsintxside(int in_len, String potherphonenumber, byte[] message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(in_len);
                    _data.writeString(potherphonenumber);
                    _data.writeByteArray(message);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int checkNSRIUSIMstate_int() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] NSRI_requestProc(int datalen, byte[] adata) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(datalen);
                    _data.writeByteArray(adata);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getDataRoamingEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void setGbaBootstrappingParams(int subId, byte[] rand, String btid, String keyLifetime) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeByteArray(rand);
                    _data.writeString(btid);
                    _data.writeString(keyLifetime);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] getCurrentUATI() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getMobileQualityInformation(int phoneId, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getIpAddressFromLinkProp(String nwkType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeString(nwkType);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] simCheck(int slotIndex) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public long getNextRetryTime() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getLastNetworkCountryIsoForPhone(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 68;
        }
    }
}
