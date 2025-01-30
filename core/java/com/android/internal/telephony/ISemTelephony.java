package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.telephony.CellIdentity;
import android.telephony.CellInfo;
import android.telephony.ServiceState;
import android.telephony.VendorConfigurationState;
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

  byte[] getAtr(int i) throws RemoteException;

  String getCdmaMinForOtasp(int i) throws RemoteException;

  CellIdentity getCellLocationBySubId(int i, String str, String str2) throws RemoteException;

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

  int getNrMode(int i) throws RemoteException;

  boolean getSdnAvailable() throws RemoteException;

  String getSecondaryImei(String str, String str2) throws RemoteException;

  ServiceState getServiceStateForPhoneId(int i, String str, String str2) throws RemoteException;

  int getSimPinRetryForSubscriber(int i) throws RemoteException;

  int getSimPukRetryForSubscriber(int i) throws RemoteException;

  int getSupportUacType(int i) throws RemoteException;

  boolean getSupportedNrca(int i) throws RemoteException;

  String getUaUap(String str) throws RemoteException;

  VendorConfigurationState getVendorConfigState(int i) throws RemoteException;

  int getVoNRMode(int i) throws RemoteException;

  int invokeOemRilRequestRawForSubscriber(int i, byte[] bArr, byte[] bArr2) throws RemoteException;

  boolean isEmergencyNumberBySubId(int i, String str, boolean z) throws RemoteException;

  boolean isMmiForSubscriber(int i, String str) throws RemoteException;

  boolean isSimFDNEnabledForSubscriber(int i) throws RemoteException;

  boolean isSupportLteCapaOptionC(int i) throws RemoteException;

  boolean isVideoCall() throws RemoteException;

  void reloadTestEmergencyNumber() throws RemoteException;

  void requestModemActivityInfo(ResultReceiver resultReceiver, String str) throws RemoteException;

  int sendRequestToRIL(byte[] bArr, byte[] bArr2, int i, int i2) throws RemoteException;

  void sendVolteState(int i, boolean z) throws RemoteException;

  void setAllowDataDuringCall(int i) throws RemoteException;

  boolean setDisable2g(int i) throws RemoteException;

  void setEPSLOCI(byte[] bArr) throws RemoteException;

  void setGbaBootstrappingParams(int i, byte[] bArr, String str, String str2)
      throws RemoteException;

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
    public CellIdentity getCellLocationBySubId(
        int subId, String callingPackage, String callingFeatureId) throws RemoteException {
      return null;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public List<CellInfo> getAllCellInfoBySubId(
        int subId, String callingPackage, String callingFeatureId) throws RemoteException {
      return null;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public void dialForSubscriber(int subId, String number) throws RemoteException {}

    @Override // com.android.internal.telephony.ISemTelephony
    public boolean isEmergencyNumberBySubId(int subId, String number, boolean exactMatch)
        throws RemoteException {
      return false;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public void reloadTestEmergencyNumber() throws RemoteException {}

    @Override // com.android.internal.telephony.ISemTelephony
    public void setAllowDataDuringCall(int enable) throws RemoteException {}

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
    public String getActivationDay(String callingPackage, String callingFeatureId)
        throws RemoteException {
      return null;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public int getNetworkStatusDisplayOption(String callingPackage, String callingFeatureId)
        throws RemoteException {
      return 0;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public boolean isMmiForSubscriber(int subId, String dialString) throws RemoteException {
      return false;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public ServiceState getServiceStateForPhoneId(
        int phoneId, String callingPackage, String callingFeatureId) throws RemoteException {
      return null;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public int getLteCsCapa(int phoneId) throws RemoteException {
      return 0;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public boolean getSdnAvailable() throws RemoteException {
      return false;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public int sendRequestToRIL(byte[] data, byte[] response, int what, int phoneId)
        throws RemoteException {
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
    public void setEPSLOCI(byte[] newEpsloci) throws RemoteException {}

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
    public boolean changeIccSimPersoPassword(String oldPassword, String newPassword)
        throws RemoteException {
      return false;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public boolean changeIccSimPersoPasswordForSubId(
        int subId, String oldPassword, String newPassword) throws RemoteException {
      return false;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public boolean setIccSimPersoEnabled(boolean enabled, String password) throws RemoteException {
      return false;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public boolean setIccSimPersoEnabledForSubId(int subId, boolean enabled, String password)
        throws RemoteException {
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
    public int invokeOemRilRequestRawForSubscriber(int subId, byte[] request, byte[] response)
        throws RemoteException {
      return 0;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public boolean setSimOnOffForSlot(int slotIndex, int mode) throws RemoteException {
      return false;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public String getSecondaryImei(String callingPackage, String callingFeatureId)
        throws RemoteException {
      return null;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public String getUaUap(String type) throws RemoteException {
      return null;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public byte[] sms_NSRI_encryptsms(int in_len, String potherphonenumber, byte[] message)
        throws RemoteException {
      return null;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public byte[] sms_NSRI_decryptsms(int in_len, byte[] message) throws RemoteException {
      return null;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public byte[] sms_NSRI_decryptsmsintxside(int in_len, String potherphonenumber, byte[] message)
        throws RemoteException {
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
    public void setGbaBootstrappingParams(int subId, byte[] rand, String btid, String keyLifetime)
        throws RemoteException {}

    @Override // com.android.internal.telephony.ISemTelephony
    public byte[] getCurrentUATI() throws RemoteException {
      return null;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public String getMobileQualityInformation(
        int phoneId, String callingPackage, String callingFeatureId) throws RemoteException {
      return null;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public String getIpAddressFromLinkProp(String nwkType) throws RemoteException {
      return null;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public boolean setNrMode(int phoneId, int mode, boolean force, String callingPackage)
        throws RemoteException {
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
    public VendorConfigurationState getVendorConfigState(int phoneId) throws RemoteException {
      return null;
    }

    @Override // com.android.internal.telephony.ISemTelephony
    public void sendVolteState(int subId, boolean isVolteOn) throws RemoteException {}

    @Override // com.android.internal.telephony.ISemTelephony
    public void requestModemActivityInfo(ResultReceiver result, String callingPackage)
        throws RemoteException {}

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
    public byte[] simCheck(int slotIndex) throws RemoteException {
      return null;
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

  public abstract static class Stub extends Binder implements ISemTelephony {
    static final int TRANSACTION_NSRI_requestProc = 45;
    static final int TRANSACTION_changeIccSimPersoPassword = 28;
    static final int TRANSACTION_changeIccSimPersoPasswordForSubId = 29;
    static final int TRANSACTION_checkCallControl = 35;
    static final int TRANSACTION_checkNSRIUSIMstate_int = 44;
    static final int TRANSACTION_dialForSubscriber = 3;
    static final int TRANSACTION_getActivationDay = 13;
    static final int TRANSACTION_getAllCellInfoBySubId = 2;
    static final int TRANSACTION_getAtr = 34;
    static final int TRANSACTION_getCdmaMinForOtasp = 33;
    static final int TRANSACTION_getCellLocationBySubId = 1;
    static final int TRANSACTION_getCurrentUATI = 48;
    static final int TRANSACTION_getDataRoamingEnabled = 46;
    static final int TRANSACTION_getDisable2g = 8;
    static final int TRANSACTION_getEuimid = 32;
    static final int TRANSACTION_getFDNavailable = 36;
    static final int TRANSACTION_getIccUsimPersoEnabled = 26;
    static final int TRANSACTION_getIccUsimPersoEnabledForSubId = 27;
    static final int TRANSACTION_getIpAddressFromLinkProp = 50;
    static final int TRANSACTION_getLastNetworkCountryIsoForPhone = 62;
    static final int TRANSACTION_getLteCsCapa = 17;
    static final int TRANSACTION_getMobileQualityInformation = 49;
    static final int TRANSACTION_getNetworkStatusDisplayOption = 14;
    static final int TRANSACTION_getNrMode = 52;
    static final int TRANSACTION_getSdnAvailable = 18;
    static final int TRANSACTION_getSecondaryImei = 39;
    static final int TRANSACTION_getServiceStateForPhoneId = 16;
    static final int TRANSACTION_getSimPinRetryForSubscriber = 24;
    static final int TRANSACTION_getSimPukRetryForSubscriber = 25;
    static final int TRANSACTION_getSupportUacType = 60;
    static final int TRANSACTION_getSupportedNrca = 58;
    static final int TRANSACTION_getUaUap = 40;
    static final int TRANSACTION_getVendorConfigState = 55;
    static final int TRANSACTION_getVoNRMode = 54;
    static final int TRANSACTION_invokeOemRilRequestRawForSubscriber = 37;
    static final int TRANSACTION_isEmergencyNumberBySubId = 4;
    static final int TRANSACTION_isMmiForSubscriber = 15;
    static final int TRANSACTION_isSimFDNEnabledForSubscriber = 23;
    static final int TRANSACTION_isSupportLteCapaOptionC = 59;
    static final int TRANSACTION_isVideoCall = 7;
    static final int TRANSACTION_reloadTestEmergencyNumber = 5;
    static final int TRANSACTION_requestModemActivityInfo = 57;
    static final int TRANSACTION_sendRequestToRIL = 19;
    static final int TRANSACTION_sendVolteState = 56;
    static final int TRANSACTION_setAllowDataDuringCall = 6;
    static final int TRANSACTION_setDisable2g = 9;
    static final int TRANSACTION_setEPSLOCI = 22;
    static final int TRANSACTION_setGbaBootstrappingParams = 47;
    static final int TRANSACTION_setIccSimPersoEnabled = 30;
    static final int TRANSACTION_setIccSimPersoEnabledForSubId = 31;
    static final int TRANSACTION_setNrMode = 51;
    static final int TRANSACTION_setSimOnOffForSlot = 38;
    static final int TRANSACTION_setTransmitPowerExt = 12;
    static final int TRANSACTION_setTransmitPowerWithDSI = 11;
    static final int TRANSACTION_setTransmitPowerWithFlag = 10;
    static final int TRANSACTION_setVoNRMode = 53;
    static final int TRANSACTION_simCheck = 61;
    static final int TRANSACTION_sms_NSRI_decryptsms = 42;
    static final int TRANSACTION_sms_NSRI_decryptsmsintxside = 43;
    static final int TRANSACTION_sms_NSRI_encryptsms = 41;
    static final int TRANSACTION_supplyPerso = 20;
    static final int TRANSACTION_supplyPersoForSubId = 21;

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
          return "getAllCellInfoBySubId";
        case 3:
          return "dialForSubscriber";
        case 4:
          return "isEmergencyNumberBySubId";
        case 5:
          return "reloadTestEmergencyNumber";
        case 6:
          return "setAllowDataDuringCall";
        case 7:
          return "isVideoCall";
        case 8:
          return "getDisable2g";
        case 9:
          return "setDisable2g";
        case 10:
          return "setTransmitPowerWithFlag";
        case 11:
          return "setTransmitPowerWithDSI";
        case 12:
          return "setTransmitPowerExt";
        case 13:
          return "getActivationDay";
        case 14:
          return "getNetworkStatusDisplayOption";
        case 15:
          return "isMmiForSubscriber";
        case 16:
          return "getServiceStateForPhoneId";
        case 17:
          return "getLteCsCapa";
        case 18:
          return "getSdnAvailable";
        case 19:
          return "sendRequestToRIL";
        case 20:
          return "supplyPerso";
        case 21:
          return "supplyPersoForSubId";
        case 22:
          return "setEPSLOCI";
        case 23:
          return "isSimFDNEnabledForSubscriber";
        case 24:
          return "getSimPinRetryForSubscriber";
        case 25:
          return "getSimPukRetryForSubscriber";
        case 26:
          return "getIccUsimPersoEnabled";
        case 27:
          return "getIccUsimPersoEnabledForSubId";
        case 28:
          return "changeIccSimPersoPassword";
        case 29:
          return "changeIccSimPersoPasswordForSubId";
        case 30:
          return "setIccSimPersoEnabled";
        case 31:
          return "setIccSimPersoEnabledForSubId";
        case 32:
          return "getEuimid";
        case 33:
          return "getCdmaMinForOtasp";
        case 34:
          return "getAtr";
        case 35:
          return "checkCallControl";
        case 36:
          return "getFDNavailable";
        case 37:
          return "invokeOemRilRequestRawForSubscriber";
        case 38:
          return "setSimOnOffForSlot";
        case 39:
          return "getSecondaryImei";
        case 40:
          return "getUaUap";
        case 41:
          return "sms_NSRI_encryptsms";
        case 42:
          return "sms_NSRI_decryptsms";
        case 43:
          return "sms_NSRI_decryptsmsintxside";
        case 44:
          return "checkNSRIUSIMstate_int";
        case 45:
          return "NSRI_requestProc";
        case 46:
          return "getDataRoamingEnabled";
        case 47:
          return "setGbaBootstrappingParams";
        case 48:
          return "getCurrentUATI";
        case 49:
          return "getMobileQualityInformation";
        case 50:
          return "getIpAddressFromLinkProp";
        case 51:
          return "setNrMode";
        case 52:
          return "getNrMode";
        case 53:
          return "setVoNRMode";
        case 54:
          return "getVoNRMode";
        case 55:
          return "getVendorConfigState";
        case 56:
          return "sendVolteState";
        case 57:
          return "requestModemActivityInfo";
        case 58:
          return "getSupportedNrca";
        case 59:
          return "isSupportLteCapaOptionC";
        case 60:
          return "getSupportUacType";
        case 61:
          return "simCheck";
        case 62:
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
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      byte[] _arg2;
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(ISemTelephony.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISemTelephony.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              String _arg1 = data.readString();
              String _arg22 = data.readString();
              data.enforceNoDataAvail();
              CellIdentity _result = getCellLocationBySubId(_arg0, _arg1, _arg22);
              reply.writeNoException();
              reply.writeTypedObject(_result, 1);
              return true;
            case 2:
              int _arg02 = data.readInt();
              String _arg12 = data.readString();
              String _arg23 = data.readString();
              data.enforceNoDataAvail();
              List<CellInfo> _result2 = getAllCellInfoBySubId(_arg02, _arg12, _arg23);
              reply.writeNoException();
              reply.writeTypedList(_result2, 1);
              return true;
            case 3:
              int _arg03 = data.readInt();
              String _arg13 = data.readString();
              data.enforceNoDataAvail();
              dialForSubscriber(_arg03, _arg13);
              reply.writeNoException();
              return true;
            case 4:
              int _arg04 = data.readInt();
              String _arg14 = data.readString();
              boolean _arg24 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result3 = isEmergencyNumberBySubId(_arg04, _arg14, _arg24);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 5:
              reloadTestEmergencyNumber();
              reply.writeNoException();
              return true;
            case 6:
              int _arg05 = data.readInt();
              data.enforceNoDataAvail();
              setAllowDataDuringCall(_arg05);
              reply.writeNoException();
              return true;
            case 7:
              boolean _result4 = isVideoCall();
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 8:
              int _result5 = getDisable2g();
              reply.writeNoException();
              reply.writeInt(_result5);
              return true;
            case 9:
              int _arg06 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result6 = setDisable2g(_arg06);
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            case 10:
              int _arg07 = data.readInt();
              boolean _arg15 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result7 = setTransmitPowerWithFlag(_arg07, _arg15);
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 11:
              int _arg08 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result8 = setTransmitPowerWithDSI(_arg08);
              reply.writeNoException();
              reply.writeBoolean(_result8);
              return true;
            case 12:
              long _arg09 = data.readLong();
              boolean _arg16 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result9 = setTransmitPowerExt(_arg09, _arg16);
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            case 13:
              String _arg010 = data.readString();
              String _arg17 = data.readString();
              data.enforceNoDataAvail();
              String _result10 = getActivationDay(_arg010, _arg17);
              reply.writeNoException();
              reply.writeString(_result10);
              return true;
            case 14:
              String _arg011 = data.readString();
              String _arg18 = data.readString();
              data.enforceNoDataAvail();
              int _result11 = getNetworkStatusDisplayOption(_arg011, _arg18);
              reply.writeNoException();
              reply.writeInt(_result11);
              return true;
            case 15:
              int _arg012 = data.readInt();
              String _arg19 = data.readString();
              data.enforceNoDataAvail();
              boolean _result12 = isMmiForSubscriber(_arg012, _arg19);
              reply.writeNoException();
              reply.writeBoolean(_result12);
              return true;
            case 16:
              int _arg013 = data.readInt();
              String _arg110 = data.readString();
              String _arg25 = data.readString();
              data.enforceNoDataAvail();
              ServiceState _result13 = getServiceStateForPhoneId(_arg013, _arg110, _arg25);
              reply.writeNoException();
              reply.writeTypedObject(_result13, 1);
              return true;
            case 17:
              int _arg014 = data.readInt();
              data.enforceNoDataAvail();
              int _result14 = getLteCsCapa(_arg014);
              reply.writeNoException();
              reply.writeInt(_result14);
              return true;
            case 18:
              boolean _result15 = getSdnAvailable();
              reply.writeNoException();
              reply.writeBoolean(_result15);
              return true;
            case 19:
              byte[] _arg015 = data.createByteArray();
              byte[] _arg111 = data.createByteArray();
              int _arg26 = data.readInt();
              int _arg3 = data.readInt();
              data.enforceNoDataAvail();
              int _result16 = sendRequestToRIL(_arg015, _arg111, _arg26, _arg3);
              reply.writeNoException();
              reply.writeInt(_result16);
              reply.writeByteArray(_arg111);
              return true;
            case 20:
              String _arg016 = data.readString();
              data.enforceNoDataAvail();
              boolean _result17 = supplyPerso(_arg016);
              reply.writeNoException();
              reply.writeBoolean(_result17);
              return true;
            case 21:
              int _arg017 = data.readInt();
              String _arg112 = data.readString();
              data.enforceNoDataAvail();
              boolean _result18 = supplyPersoForSubId(_arg017, _arg112);
              reply.writeNoException();
              reply.writeBoolean(_result18);
              return true;
            case 22:
              byte[] _arg018 = data.createByteArray();
              data.enforceNoDataAvail();
              setEPSLOCI(_arg018);
              reply.writeNoException();
              return true;
            case 23:
              int _arg019 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result19 = isSimFDNEnabledForSubscriber(_arg019);
              reply.writeNoException();
              reply.writeBoolean(_result19);
              return true;
            case 24:
              int _arg020 = data.readInt();
              data.enforceNoDataAvail();
              int _result20 = getSimPinRetryForSubscriber(_arg020);
              reply.writeNoException();
              reply.writeInt(_result20);
              return true;
            case 25:
              int _arg021 = data.readInt();
              data.enforceNoDataAvail();
              int _result21 = getSimPukRetryForSubscriber(_arg021);
              reply.writeNoException();
              reply.writeInt(_result21);
              return true;
            case 26:
              boolean _result22 = getIccUsimPersoEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result22);
              return true;
            case 27:
              int _arg022 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result23 = getIccUsimPersoEnabledForSubId(_arg022);
              reply.writeNoException();
              reply.writeBoolean(_result23);
              return true;
            case 28:
              String _arg023 = data.readString();
              String _arg113 = data.readString();
              data.enforceNoDataAvail();
              boolean _result24 = changeIccSimPersoPassword(_arg023, _arg113);
              reply.writeNoException();
              reply.writeBoolean(_result24);
              return true;
            case 29:
              int _arg024 = data.readInt();
              String _arg114 = data.readString();
              String _arg27 = data.readString();
              data.enforceNoDataAvail();
              boolean _result25 = changeIccSimPersoPasswordForSubId(_arg024, _arg114, _arg27);
              reply.writeNoException();
              reply.writeBoolean(_result25);
              return true;
            case 30:
              boolean _arg025 = data.readBoolean();
              String _arg115 = data.readString();
              data.enforceNoDataAvail();
              boolean _result26 = setIccSimPersoEnabled(_arg025, _arg115);
              reply.writeNoException();
              reply.writeBoolean(_result26);
              return true;
            case 31:
              int _arg026 = data.readInt();
              boolean _arg116 = data.readBoolean();
              String _arg28 = data.readString();
              data.enforceNoDataAvail();
              boolean _result27 = setIccSimPersoEnabledForSubId(_arg026, _arg116, _arg28);
              reply.writeNoException();
              reply.writeBoolean(_result27);
              return true;
            case 32:
              String _result28 = getEuimid();
              reply.writeNoException();
              reply.writeString(_result28);
              return true;
            case 33:
              int _arg027 = data.readInt();
              data.enforceNoDataAvail();
              String _result29 = getCdmaMinForOtasp(_arg027);
              reply.writeNoException();
              reply.writeString(_result29);
              return true;
            case 34:
              int _arg028 = data.readInt();
              data.enforceNoDataAvail();
              byte[] _result30 = getAtr(_arg028);
              reply.writeNoException();
              reply.writeByteArray(_result30);
              return true;
            case 35:
              int _arg029 = data.readInt();
              String _arg117 = data.readString();
              data.enforceNoDataAvail();
              String _result31 = checkCallControl(_arg029, _arg117);
              reply.writeNoException();
              reply.writeString(_result31);
              return true;
            case 36:
              int _arg030 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result32 = getFDNavailable(_arg030);
              reply.writeNoException();
              reply.writeBoolean(_result32);
              return true;
            case 37:
              int _arg031 = data.readInt();
              byte[] _arg118 = data.createByteArray();
              int _arg2_length = data.readInt();
              if (_arg2_length < 0) {
                _arg2 = null;
              } else {
                _arg2 = new byte[_arg2_length];
              }
              data.enforceNoDataAvail();
              int _result33 = invokeOemRilRequestRawForSubscriber(_arg031, _arg118, _arg2);
              reply.writeNoException();
              reply.writeInt(_result33);
              reply.writeByteArray(_arg2);
              return true;
            case 38:
              int _arg032 = data.readInt();
              int _arg119 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result34 = setSimOnOffForSlot(_arg032, _arg119);
              reply.writeNoException();
              reply.writeBoolean(_result34);
              return true;
            case 39:
              String _arg033 = data.readString();
              String _arg120 = data.readString();
              data.enforceNoDataAvail();
              String _result35 = getSecondaryImei(_arg033, _arg120);
              reply.writeNoException();
              reply.writeString(_result35);
              return true;
            case 40:
              String _arg034 = data.readString();
              data.enforceNoDataAvail();
              String _result36 = getUaUap(_arg034);
              reply.writeNoException();
              reply.writeString(_result36);
              return true;
            case 41:
              int _arg035 = data.readInt();
              String _arg121 = data.readString();
              byte[] _arg29 = data.createByteArray();
              data.enforceNoDataAvail();
              byte[] _result37 = sms_NSRI_encryptsms(_arg035, _arg121, _arg29);
              reply.writeNoException();
              reply.writeByteArray(_result37);
              return true;
            case 42:
              int _arg036 = data.readInt();
              byte[] _arg122 = data.createByteArray();
              data.enforceNoDataAvail();
              byte[] _result38 = sms_NSRI_decryptsms(_arg036, _arg122);
              reply.writeNoException();
              reply.writeByteArray(_result38);
              return true;
            case 43:
              int _arg037 = data.readInt();
              String _arg123 = data.readString();
              byte[] _arg210 = data.createByteArray();
              data.enforceNoDataAvail();
              byte[] _result39 = sms_NSRI_decryptsmsintxside(_arg037, _arg123, _arg210);
              reply.writeNoException();
              reply.writeByteArray(_result39);
              return true;
            case 44:
              int _result40 = checkNSRIUSIMstate_int();
              reply.writeNoException();
              reply.writeInt(_result40);
              return true;
            case 45:
              int _arg038 = data.readInt();
              byte[] _arg124 = data.createByteArray();
              data.enforceNoDataAvail();
              byte[] _result41 = NSRI_requestProc(_arg038, _arg124);
              reply.writeNoException();
              reply.writeByteArray(_result41);
              return true;
            case 46:
              boolean _result42 = getDataRoamingEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result42);
              return true;
            case 47:
              int _arg039 = data.readInt();
              byte[] _arg125 = data.createByteArray();
              String _arg211 = data.readString();
              String _arg32 = data.readString();
              data.enforceNoDataAvail();
              setGbaBootstrappingParams(_arg039, _arg125, _arg211, _arg32);
              reply.writeNoException();
              return true;
            case 48:
              byte[] _result43 = getCurrentUATI();
              reply.writeNoException();
              reply.writeByteArray(_result43);
              return true;
            case 49:
              int _arg040 = data.readInt();
              String _arg126 = data.readString();
              String _arg212 = data.readString();
              data.enforceNoDataAvail();
              String _result44 = getMobileQualityInformation(_arg040, _arg126, _arg212);
              reply.writeNoException();
              reply.writeString(_result44);
              return true;
            case 50:
              String _arg041 = data.readString();
              data.enforceNoDataAvail();
              String _result45 = getIpAddressFromLinkProp(_arg041);
              reply.writeNoException();
              reply.writeString(_result45);
              return true;
            case 51:
              int _arg042 = data.readInt();
              int _arg127 = data.readInt();
              boolean _arg213 = data.readBoolean();
              String _arg33 = data.readString();
              data.enforceNoDataAvail();
              boolean _result46 = setNrMode(_arg042, _arg127, _arg213, _arg33);
              reply.writeNoException();
              reply.writeBoolean(_result46);
              return true;
            case 52:
              int _arg043 = data.readInt();
              data.enforceNoDataAvail();
              int _result47 = getNrMode(_arg043);
              reply.writeNoException();
              reply.writeInt(_result47);
              return true;
            case 53:
              int _arg044 = data.readInt();
              int _arg128 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result48 = setVoNRMode(_arg044, _arg128);
              reply.writeNoException();
              reply.writeBoolean(_result48);
              return true;
            case 54:
              int _arg045 = data.readInt();
              data.enforceNoDataAvail();
              int _result49 = getVoNRMode(_arg045);
              reply.writeNoException();
              reply.writeInt(_result49);
              return true;
            case 55:
              int _arg046 = data.readInt();
              data.enforceNoDataAvail();
              VendorConfigurationState _result50 = getVendorConfigState(_arg046);
              reply.writeNoException();
              reply.writeTypedObject(_result50, 1);
              return true;
            case 56:
              int _arg047 = data.readInt();
              boolean _arg129 = data.readBoolean();
              data.enforceNoDataAvail();
              sendVolteState(_arg047, _arg129);
              reply.writeNoException();
              return true;
            case 57:
              ResultReceiver _arg048 =
                  (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
              String _arg130 = data.readString();
              data.enforceNoDataAvail();
              requestModemActivityInfo(_arg048, _arg130);
              return true;
            case 58:
              int _arg049 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result51 = getSupportedNrca(_arg049);
              reply.writeNoException();
              reply.writeBoolean(_result51);
              return true;
            case 59:
              int _arg050 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result52 = isSupportLteCapaOptionC(_arg050);
              reply.writeNoException();
              reply.writeBoolean(_result52);
              return true;
            case 60:
              int _arg051 = data.readInt();
              data.enforceNoDataAvail();
              int _result53 = getSupportUacType(_arg051);
              reply.writeNoException();
              reply.writeInt(_result53);
              return true;
            case 61:
              int _arg052 = data.readInt();
              data.enforceNoDataAvail();
              byte[] _result54 = simCheck(_arg052);
              reply.writeNoException();
              reply.writeByteArray(_result54);
              return true;
            case 62:
              int _arg053 = data.readInt();
              data.enforceNoDataAvail();
              String _result55 = getLastNetworkCountryIsoForPhone(_arg053);
              reply.writeNoException();
              reply.writeString(_result55);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
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
      public CellIdentity getCellLocationBySubId(
          int subId, String callingPackage, String callingFeatureId) throws RemoteException {
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
      public List<CellInfo> getAllCellInfoBySubId(
          int subId, String callingPackage, String callingFeatureId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(subId);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
          this.mRemote.transact(2, _data, _reply, 0);
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
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public boolean isEmergencyNumberBySubId(int subId, String number, boolean exactMatch)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(subId);
          _data.writeString(number);
          _data.writeBoolean(exactMatch);
          this.mRemote.transact(4, _data, _reply, 0);
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
          this.mRemote.transact(5, _data, _reply, 0);
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
          this.mRemote.transact(6, _data, _reply, 0);
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
          this.mRemote.transact(7, _data, _reply, 0);
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
          this.mRemote.transact(8, _data, _reply, 0);
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
      public boolean setTransmitPowerWithFlag(int powerLevel, boolean enable)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(powerLevel);
          _data.writeBoolean(enable);
          this.mRemote.transact(10, _data, _reply, 0);
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
      public boolean setTransmitPowerExt(long powerLevel, boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeLong(powerLevel);
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
      public String getActivationDay(String callingPackage, String callingFeatureId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public int getNetworkStatusDisplayOption(String callingPackage, String callingFeatureId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
          this.mRemote.transact(14, _data, _reply, 0);
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
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public ServiceState getServiceStateForPhoneId(
          int phoneId, String callingPackage, String callingFeatureId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          ServiceState _result = (ServiceState) _reply.readTypedObject(ServiceState.CREATOR);
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
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
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
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public int sendRequestToRIL(byte[] data, byte[] response, int what, int phoneId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeByteArray(data);
          _data.writeByteArray(response);
          _data.writeInt(what);
          _data.writeInt(phoneId);
          this.mRemote.transact(19, _data, _reply, 0);
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
      public boolean supplyPersoForSubId(int subId, String passwd) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(subId);
          _data.writeString(passwd);
          this.mRemote.transact(21, _data, _reply, 0);
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
          this.mRemote.transact(22, _data, _reply, 0);
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
          this.mRemote.transact(23, _data, _reply, 0);
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
          this.mRemote.transact(24, _data, _reply, 0);
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
          this.mRemote.transact(25, _data, _reply, 0);
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
          this.mRemote.transact(26, _data, _reply, 0);
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
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public boolean changeIccSimPersoPassword(String oldPassword, String newPassword)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeString(oldPassword);
          _data.writeString(newPassword);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public boolean changeIccSimPersoPasswordForSubId(
          int subId, String oldPassword, String newPassword) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(subId);
          _data.writeString(oldPassword);
          _data.writeString(newPassword);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public boolean setIccSimPersoEnabled(boolean enabled, String password)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeBoolean(enabled);
          _data.writeString(password);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public boolean setIccSimPersoEnabledForSubId(int subId, boolean enabled, String password)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(subId);
          _data.writeBoolean(enabled);
          _data.writeString(password);
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
      public String getEuimid() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          this.mRemote.transact(32, _data, _reply, 0);
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
          this.mRemote.transact(33, _data, _reply, 0);
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
          this.mRemote.transact(34, _data, _reply, 0);
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
          this.mRemote.transact(35, _data, _reply, 0);
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
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public int invokeOemRilRequestRawForSubscriber(int subId, byte[] request, byte[] response)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(subId);
          _data.writeByteArray(request);
          _data.writeInt(response.length);
          this.mRemote.transact(37, _data, _reply, 0);
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
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public String getSecondaryImei(String callingPackage, String callingFeatureId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
          this.mRemote.transact(39, _data, _reply, 0);
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
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public byte[] sms_NSRI_encryptsms(int in_len, String potherphonenumber, byte[] message)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(in_len);
          _data.writeString(potherphonenumber);
          _data.writeByteArray(message);
          this.mRemote.transact(41, _data, _reply, 0);
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
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public byte[] sms_NSRI_decryptsmsintxside(
          int in_len, String potherphonenumber, byte[] message) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(in_len);
          _data.writeString(potherphonenumber);
          _data.writeByteArray(message);
          this.mRemote.transact(43, _data, _reply, 0);
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
          this.mRemote.transact(44, _data, _reply, 0);
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
          this.mRemote.transact(45, _data, _reply, 0);
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
          this.mRemote.transact(46, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public void setGbaBootstrappingParams(int subId, byte[] rand, String btid, String keyLifetime)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(subId);
          _data.writeByteArray(rand);
          _data.writeString(btid);
          _data.writeString(keyLifetime);
          this.mRemote.transact(47, _data, _reply, 0);
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
      public String getMobileQualityInformation(
          int phoneId, String callingPackage, String callingFeatureId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
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
      public String getIpAddressFromLinkProp(String nwkType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeString(nwkType);
          this.mRemote.transact(50, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public boolean setNrMode(int phoneId, int mode, boolean force, String callingPackage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(phoneId);
          _data.writeInt(mode);
          _data.writeBoolean(force);
          _data.writeString(callingPackage);
          this.mRemote.transact(51, _data, _reply, 0);
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
          this.mRemote.transact(52, _data, _reply, 0);
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
      public int getVoNRMode(int phoneId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(phoneId);
          this.mRemote.transact(54, _data, _reply, 0);
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
          this.mRemote.transact(55, _data, _reply, 0);
          _reply.readException();
          VendorConfigurationState _result =
              (VendorConfigurationState) _reply.readTypedObject(VendorConfigurationState.CREATOR);
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
          this.mRemote.transact(56, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.internal.telephony.ISemTelephony
      public void requestModemActivityInfo(ResultReceiver result, String callingPackage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeTypedObject(result, 0);
          _data.writeString(callingPackage);
          this.mRemote.transact(57, _data, null, 1);
        } finally {
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
          this.mRemote.transact(58, _data, _reply, 0);
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
          this.mRemote.transact(59, _data, _reply, 0);
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
      public byte[] simCheck(int slotIndex) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(slotIndex);
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
      public String getLastNetworkCountryIsoForPhone(int phoneId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemTelephony.DESCRIPTOR);
          _data.writeInt(phoneId);
          this.mRemote.transact(62, _data, _reply, 0);
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
      return 61;
    }
  }
}
