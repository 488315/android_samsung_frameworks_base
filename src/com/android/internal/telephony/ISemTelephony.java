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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemTelephony.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemTelephony)) {
                return (ISemTelephony) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CellIdentity cellLocationBySubId = getCellLocationBySubId(readInt, readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cellLocationBySubId, 1);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CellIdentity cellLocationForPhone = getCellLocationForPhone(readInt2, readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cellLocationForPhone, 1);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<CellInfo> allCellInfoBySubId = getAllCellInfoBySubId(readInt3, readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allCellInfoBySubId, 1);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<CellInfo> allCellInfoForPhone = getAllCellInfoForPhone(readInt4, readString7, readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allCellInfoForPhone, 1);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dialForSubscriber(readInt5, readString9);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    String readString10 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isEmergencyNumberBySubId = isEmergencyNumberBySubId(readInt6, readString10, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEmergencyNumberBySubId);
                    return true;
                case 7:
                    reloadTestEmergencyNumber();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAllowDataDuringCall(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    boolean isVideoCall = isVideoCall();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVideoCall);
                    return true;
                case 10:
                    int disable2g = getDisable2g();
                    parcel2.writeNoException();
                    parcel2.writeInt(disable2g);
                    return true;
                case 11:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean disable2g2 = setDisable2g(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disable2g2);
                    return true;
                case 12:
                    int readInt9 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean transmitPowerWithFlag = setTransmitPowerWithFlag(readInt9, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(transmitPowerWithFlag);
                    return true;
                case 13:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean transmitPowerWithDSI = setTransmitPowerWithDSI(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(transmitPowerWithDSI);
                    return true;
                case 14:
                    long readLong = parcel.readLong();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean transmitPowerExt = setTransmitPowerExt(readLong, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(transmitPowerExt);
                    return true;
                case 15:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String activationDay = getActivationDay(readString11, readString12);
                    parcel2.writeNoException();
                    parcel2.writeString(activationDay);
                    return true;
                case 16:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int networkStatusDisplayOption = getNetworkStatusDisplayOption(readString13, readString14);
                    parcel2.writeNoException();
                    parcel2.writeInt(networkStatusDisplayOption);
                    return true;
                case 17:
                    int readInt11 = parcel.readInt();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isMmiForSubscriber = isMmiForSubscriber(readInt11, readString15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMmiForSubscriber);
                    return true;
                case 18:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lteCsCapa = getLteCsCapa(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeInt(lteCsCapa);
                    return true;
                case 19:
                    int readInt13 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendVolteState(readInt13, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean nrMode = setNrMode(readInt14, readInt15, readBoolean5, readString16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nrMode);
                    return true;
                case 21:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int nrMode2 = getNrMode(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeInt(nrMode2);
                    return true;
                case 22:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean voNRMode = setVoNRMode(readInt17, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(voNRMode);
                    return true;
                case 23:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int voNRMode2 = getVoNRMode(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeInt(voNRMode2);
                    return true;
                case 24:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean supportedNrca = getSupportedNrca(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportedNrca);
                    return true;
                case 25:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSupportLteCapaOptionC = isSupportLteCapaOptionC(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupportLteCapaOptionC);
                    return true;
                case 26:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int supportUacType = getSupportUacType(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeInt(supportUacType);
                    return true;
                case 27:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VendorConfigurationState vendorConfigState = getVendorConfigState(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vendorConfigState, 1);
                    return true;
                case 28:
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestModemActivityInfo(resultReceiver, readString17);
                    return true;
                case 29:
                    int readInt24 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    IIntegerConsumer asInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    semRequestSatelliteMode(readInt24, readBoolean6, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemSatelliteState semGetSatelliteState = semGetSatelliteState(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semGetSatelliteState, 1);
                    return true;
                case 31:
                    boolean ntnSmsSupported = getNtnSmsSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ntnSmsSupported);
                    return true;
                case 32:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetNetworkSettings(readInt26);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    boolean sdnAvailable = getSdnAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sdnAvailable);
                    return true;
                case 34:
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int sendRequestToRIL = sendRequestToRIL(createByteArray, createByteArray2, readInt27, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeInt(sendRequestToRIL);
                    parcel2.writeByteArray(createByteArray2);
                    return true;
                case 35:
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean supplyPerso = supplyPerso(readString18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supplyPerso);
                    return true;
                case 36:
                    int readInt29 = parcel.readInt();
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean supplyPersoForSubId = supplyPersoForSubId(readInt29, readString19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supplyPersoForSubId);
                    return true;
                case 37:
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setEPSLOCI(createByteArray3);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSimFDNEnabledForSubscriber = isSimFDNEnabledForSubscriber(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSimFDNEnabledForSubscriber);
                    return true;
                case 39:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int simPinRetryForSubscriber = getSimPinRetryForSubscriber(readInt31);
                    parcel2.writeNoException();
                    parcel2.writeInt(simPinRetryForSubscriber);
                    return true;
                case 40:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int simPukRetryForSubscriber = getSimPukRetryForSubscriber(readInt32);
                    parcel2.writeNoException();
                    parcel2.writeInt(simPukRetryForSubscriber);
                    return true;
                case 41:
                    boolean iccUsimPersoEnabled = getIccUsimPersoEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(iccUsimPersoEnabled);
                    return true;
                case 42:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean iccUsimPersoEnabledForSubId = getIccUsimPersoEnabledForSubId(readInt33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(iccUsimPersoEnabledForSubId);
                    return true;
                case 43:
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean changeIccSimPersoPassword = changeIccSimPersoPassword(readString20, readString21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(changeIccSimPersoPassword);
                    return true;
                case 44:
                    int readInt34 = parcel.readInt();
                    String readString22 = parcel.readString();
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean changeIccSimPersoPasswordForSubId = changeIccSimPersoPasswordForSubId(readInt34, readString22, readString23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(changeIccSimPersoPasswordForSubId);
                    return true;
                case 45:
                    boolean readBoolean7 = parcel.readBoolean();
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean iccSimPersoEnabled = setIccSimPersoEnabled(readBoolean7, readString24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(iccSimPersoEnabled);
                    return true;
                case 46:
                    int readInt35 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean iccSimPersoEnabledForSubId = setIccSimPersoEnabledForSubId(readInt35, readBoolean8, readString25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(iccSimPersoEnabledForSubId);
                    return true;
                case 47:
                    String euimid = getEuimid();
                    parcel2.writeNoException();
                    parcel2.writeString(euimid);
                    return true;
                case 48:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String cdmaMinForOtasp = getCdmaMinForOtasp(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeString(cdmaMinForOtasp);
                    return true;
                case 49:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] atr = getAtr(readInt37);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(atr);
                    return true;
                case 50:
                    int readInt38 = parcel.readInt();
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String checkCallControl = checkCallControl(readInt38, readString26);
                    parcel2.writeNoException();
                    parcel2.writeString(checkCallControl);
                    return true;
                case 51:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean fDNavailable = getFDNavailable(readInt39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(fDNavailable);
                    return true;
                case 52:
                    int readInt40 = parcel.readInt();
                    byte[] createByteArray4 = parcel.createByteArray();
                    int readInt41 = parcel.readInt();
                    if (readInt41 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt41);
                    }
                    bArr = readInt41 >= 0 ? new byte[readInt41] : null;
                    parcel.enforceNoDataAvail();
                    int invokeOemRilRequestRawForPhone = invokeOemRilRequestRawForPhone(readInt40, createByteArray4, bArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(invokeOemRilRequestRawForPhone);
                    parcel2.writeByteArray(bArr);
                    return true;
                case 53:
                    int readInt42 = parcel.readInt();
                    byte[] createByteArray5 = parcel.createByteArray();
                    int readInt43 = parcel.readInt();
                    if (readInt43 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt43);
                    }
                    bArr = readInt43 >= 0 ? new byte[readInt43] : null;
                    parcel.enforceNoDataAvail();
                    int invokeOemRilRequestRawForSubscriber = invokeOemRilRequestRawForSubscriber(readInt42, createByteArray5, bArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(invokeOemRilRequestRawForSubscriber);
                    parcel2.writeByteArray(bArr);
                    return true;
                case 54:
                    int readInt44 = parcel.readInt();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean simOnOffForSlot = setSimOnOffForSlot(readInt44, readInt45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(simOnOffForSlot);
                    return true;
                case 55:
                    String readString27 = parcel.readString();
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String secondaryImei = getSecondaryImei(readString27, readString28);
                    parcel2.writeNoException();
                    parcel2.writeString(secondaryImei);
                    return true;
                case 56:
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String semGetSatelliteImei = semGetSatelliteImei(readString29, readString30);
                    parcel2.writeNoException();
                    parcel2.writeString(semGetSatelliteImei);
                    return true;
                case 57:
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String uaUap = getUaUap(readString31);
                    parcel2.writeNoException();
                    parcel2.writeString(uaUap);
                    return true;
                case 58:
                    int readInt46 = parcel.readInt();
                    String readString32 = parcel.readString();
                    byte[] createByteArray6 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] sms_NSRI_encryptsms = sms_NSRI_encryptsms(readInt46, readString32, createByteArray6);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(sms_NSRI_encryptsms);
                    return true;
                case 59:
                    int readInt47 = parcel.readInt();
                    byte[] createByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] sms_NSRI_decryptsms = sms_NSRI_decryptsms(readInt47, createByteArray7);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(sms_NSRI_decryptsms);
                    return true;
                case 60:
                    int readInt48 = parcel.readInt();
                    String readString33 = parcel.readString();
                    byte[] createByteArray8 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] sms_NSRI_decryptsmsintxside = sms_NSRI_decryptsmsintxside(readInt48, readString33, createByteArray8);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(sms_NSRI_decryptsmsintxside);
                    return true;
                case 61:
                    int checkNSRIUSIMstate_int = checkNSRIUSIMstate_int();
                    parcel2.writeNoException();
                    parcel2.writeInt(checkNSRIUSIMstate_int);
                    return true;
                case 62:
                    int readInt49 = parcel.readInt();
                    byte[] createByteArray9 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] NSRI_requestProc = NSRI_requestProc(readInt49, createByteArray9);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(NSRI_requestProc);
                    return true;
                case 63:
                    boolean dataRoamingEnabled = getDataRoamingEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataRoamingEnabled);
                    return true;
                case 64:
                    int readInt50 = parcel.readInt();
                    byte[] createByteArray10 = parcel.createByteArray();
                    String readString34 = parcel.readString();
                    String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setGbaBootstrappingParams(readInt50, createByteArray10, readString34, readString35);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    byte[] currentUATI = getCurrentUATI();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(currentUATI);
                    return true;
                case 66:
                    int readInt51 = parcel.readInt();
                    int readInt52 = parcel.readInt();
                    if (readInt52 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt52);
                    }
                    bArr = readInt52 >= 0 ? new byte[readInt52] : null;
                    parcel.enforceNoDataAvail();
                    int cpaiModelVersion = getCpaiModelVersion(readInt51, bArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(cpaiModelVersion);
                    parcel2.writeByteArray(bArr);
                    return true;
                case 67:
                    int readInt53 = parcel.readInt();
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int execCpaiModelUpdate = execCpaiModelUpdate(readInt53, readInt54);
                    parcel2.writeNoException();
                    parcel2.writeInt(execCpaiModelUpdate);
                    return true;
                case 68:
                    int readInt55 = parcel.readInt();
                    int readInt56 = parcel.readInt();
                    int readInt57 = parcel.readInt();
                    if (readInt57 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt57);
                    }
                    bArr = readInt57 >= 0 ? new byte[readInt57] : null;
                    parcel.enforceNoDataAvail();
                    int cpaiFeatureInfo = getCpaiFeatureInfo(readInt55, readInt56, bArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(cpaiFeatureInfo);
                    parcel2.writeByteArray(bArr);
                    return true;
                case 69:
                    int readInt58 = parcel.readInt();
                    int readInt59 = parcel.readInt();
                    int readInt60 = parcel.readInt();
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cfrmCpaiFeatureInfo = cfrmCpaiFeatureInfo(readInt58, readInt59, readInt60, readString36);
                    parcel2.writeNoException();
                    parcel2.writeInt(cfrmCpaiFeatureInfo);
                    return true;
                case 70:
                    int readInt61 = parcel.readInt();
                    int readInt62 = parcel.readInt();
                    int readInt63 = parcel.readInt();
                    int readInt64 = parcel.readInt();
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int cpaiDataGathering = setCpaiDataGathering(readInt61, readInt62, readInt63, readInt64, readInt65);
                    parcel2.writeNoException();
                    parcel2.writeInt(cpaiDataGathering);
                    return true;
                case 71:
                    int readInt66 = parcel.readInt();
                    int readInt67 = parcel.readInt();
                    byte[] createByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int evtCpaiDataGathering = evtCpaiDataGathering(readInt66, readInt67, createByteArray11);
                    parcel2.writeNoException();
                    parcel2.writeInt(evtCpaiDataGathering);
                    return true;
                case 72:
                    int readInt68 = parcel.readInt();
                    int readInt69 = parcel.readInt();
                    int readInt70 = parcel.readInt();
                    String readString37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cpaiDevAppMessage = setCpaiDevAppMessage(readInt68, readInt69, readInt70, readString37);
                    parcel2.writeNoException();
                    parcel2.writeInt(cpaiDevAppMessage);
                    return true;
                case 73:
                    int readInt71 = parcel.readInt();
                    String readString38 = parcel.readString();
                    String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String mobileQualityInformation = getMobileQualityInformation(readInt71, readString38, readString39);
                    parcel2.writeNoException();
                    parcel2.writeString(mobileQualityInformation);
                    return true;
                case 74:
                    String readString40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String ipAddressFromLinkProp = getIpAddressFromLinkProp(readString40);
                    parcel2.writeNoException();
                    parcel2.writeString(ipAddressFromLinkProp);
                    return true;
                case 75:
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] simCheck = simCheck(readInt72);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(simCheck);
                    return true;
                case 76:
                    long nextRetryTime = getNextRetryTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(nextRetryTime);
                    return true;
                case 77:
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String lastNetworkCountryIsoForPhone = getLastNetworkCountryIsoForPhone(readInt73);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CellIdentity) obtain2.readTypedObject(CellIdentity.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public CellIdentity getCellLocationForPhone(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CellIdentity) obtain2.readTypedObject(CellIdentity.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public List<CellInfo> getAllCellInfoBySubId(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(CellInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public List<CellInfo> getAllCellInfoForPhone(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(CellInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void dialForSubscriber(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isEmergencyNumberBySubId(int i, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void reloadTestEmergencyNumber() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void setAllowDataDuringCall(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isVideoCall() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getDisable2g() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setDisable2g(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setTransmitPowerWithFlag(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setTransmitPowerWithDSI(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setTransmitPowerExt(long j, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getActivationDay(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getNetworkStatusDisplayOption(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isMmiForSubscriber(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getLteCsCapa(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void sendVolteState(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setNrMode(int i, int i2, boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getNrMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setVoNRMode(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getVoNRMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getSupportedNrca(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isSupportLteCapaOptionC(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getSupportUacType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public VendorConfigurationState getVendorConfigState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VendorConfigurationState) obtain2.readTypedObject(VendorConfigurationState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void requestModemActivityInfo(ResultReceiver resultReceiver, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeTypedObject(resultReceiver, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void semRequestSatelliteMode(int i, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public SemSatelliteState semGetSatelliteState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemSatelliteState) obtain2.readTypedObject(SemSatelliteState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getNtnSmsSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void resetNetworkSettings(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getSdnAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int sendRequestToRIL(byte[] bArr, byte[] bArr2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr2);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean supplyPerso(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean supplyPersoForSubId(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void setEPSLOCI(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean isSimFDNEnabledForSubscriber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getSimPinRetryForSubscriber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getSimPukRetryForSubscriber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getIccUsimPersoEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getIccUsimPersoEnabledForSubId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean changeIccSimPersoPassword(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean changeIccSimPersoPasswordForSubId(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setIccSimPersoEnabled(boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setIccSimPersoEnabledForSubId(int i, boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getEuimid() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getCdmaMinForOtasp(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] getAtr(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String checkCallControl(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getFDNavailable(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int invokeOemRilRequestRawForPhone(int i, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(bArr2.length);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr2);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int invokeOemRilRequestRawForSubscriber(int i, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(bArr2.length);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr2);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean setSimOnOffForSlot(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getSecondaryImei(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String semGetSatelliteImei(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getUaUap(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] sms_NSRI_encryptsms(int i, String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] sms_NSRI_decryptsms(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] sms_NSRI_decryptsmsintxside(int i, String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int checkNSRIUSIMstate_int() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] NSRI_requestProc(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public boolean getDataRoamingEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public void setGbaBootstrappingParams(int i, byte[] bArr, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] getCurrentUATI() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getCpaiModelVersion(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(bArr.length);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int execCpaiModelUpdate(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int getCpaiFeatureInfo(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(bArr.length);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int cfrmCpaiFeatureInfo(int i, int i2, int i3, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int setCpaiDataGathering(int i, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int evtCpaiDataGathering(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public int setCpaiDevAppMessage(int i, int i2, int i3, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getMobileQualityInformation(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getIpAddressFromLinkProp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public byte[] simCheck(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public long getNextRetryTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephony
            public String getLastNetworkCountryIsoForPhone(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
