package com.samsung.android.knox.dar;

import android.hardware.scontext.SContextConstants;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IZtdListener;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.dar.sdp.ISdpListener;
import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;
import com.samsung.android.knox.sdp.core.SdpCreationParam;
import com.samsung.android.knox.sdp.core.SdpEngineInfo;
import java.util.List;

/* loaded from: classes5.dex */
public interface IDarManagerService extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.knox.dar.IDarManagerService";

  void addBlockedClearablePackages(int i, String str) throws RemoteException;

  int addEngine(SdpCreationParam sdpCreationParam, String str, String str2) throws RemoteException;

  int allow(String str, String str2) throws RemoteException;

  boolean clearResetPasswordToken(int i) throws RemoteException;

  int createEncPkgDir(int i, String str) throws RemoteException;

  int deleteToeknFromTrusted(String str) throws RemoteException;

  int disallow(String str, String str2) throws RemoteException;

  int exists(String str) throws RemoteException;

  int getAvailableUserId() throws RemoteException;

  List<String> getBlockedClearablePackages(int i) throws RemoteException;

  SdpEngineInfo getEngineInfo(String str) throws RemoteException;

  int getInnerAuthUserId(int i) throws RemoteException;

  int getMainUserId(int i) throws RemoteException;

  List<String> getPackageListForDualDarPolicy(String str) throws RemoteException;

  int getPasswordMinimumLengthForInner() throws RemoteException;

  int getReservedUserIdForSystem() throws RemoteException;

  double getSupportedSDKVersion() throws RemoteException;

  boolean isDarSupported() throws RemoteException;

  boolean isDefaultPathUser(int i) throws RemoteException;

  boolean isDeviceRootKeyInstalled() throws RemoteException;

  boolean isInnerAuthRequired(int i) throws RemoteException;

  boolean isKnoxKeyInstallable() throws RemoteException;

  int isLicensed() throws RemoteException;

  boolean isResetPasswordTokenActive(int i) throws RemoteException;

  boolean isSDPEnabled(int i) throws RemoteException;

  boolean isSdpSupported() throws RemoteException;

  boolean isSdpSupportedSecureFolder(int i) throws RemoteException;

  boolean isSensitive(String str) throws RemoteException;

  int lock(String str) throws RemoteException;

  int migrate(String str) throws RemoteException;

  void onBiometricsAuthenticated(int i) throws RemoteException;

  void onDeviceOwnerLocked(int i) throws RemoteException;

  void registerClient(int i, ISdpListener iSdpListener) throws RemoteException;

  int registerListener(String str, ISdpListener iSdpListener) throws RemoteException;

  int removeEngine(String str) throws RemoteException;

  int reserveUserIdForSystem() throws RemoteException;

  int resetPassword(String str, String str2, String str3) throws RemoteException;

  boolean resetPasswordWithToken(String str, byte[] bArr, int i) throws RemoteException;

  int saveTokenIntoTrusted(String str, String str2) throws RemoteException;

  boolean setDualDarInfo(int i, int i2) throws RemoteException;

  void setInnerAuthUserId(int i, int i2) throws RemoteException;

  void setMainUserId(int i, int i2) throws RemoteException;

  int setPassword(String str, String str2) throws RemoteException;

  boolean setResetPasswordToken(byte[] bArr, int i) throws RemoteException;

  boolean setSensitive(int i, String str) throws RemoteException;

  void startMonitoringDomains(int i, int[] iArr, List<String> list, IZtdListener iZtdListener)
      throws RemoteException;

  void startMonitoringFiles(
      int i, int[] iArr, List<String> list, List<String> list2, IZtdListener iZtdListener)
      throws RemoteException;

  int startTracing(int i, int i2, Bundle bundle, IEndpointMonitorListener iEndpointMonitorListener)
      throws RemoteException;

  void stopMonitoringDomains(int i) throws RemoteException;

  void stopMonitoringFiles(int i) throws RemoteException;

  int stopTracing(int i, int i2) throws RemoteException;

  void systemReady() throws RemoteException;

  int unlock(String str, String str2) throws RemoteException;

  int unlockViaTrusted(String str, String str2) throws RemoteException;

  void unregisterClient(int i, ISdpListener iSdpListener) throws RemoteException;

  int unregisterListener(String str, ISdpListener iSdpListener) throws RemoteException;

  public static class Default implements IDarManagerService {
    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean isDarSupported() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public void systemReady() throws RemoteException {}

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean isDeviceRootKeyInstalled() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean isKnoxKeyInstallable() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int reserveUserIdForSystem() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int getReservedUserIdForSystem() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int getAvailableUserId() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean setResetPasswordToken(byte[] token, int userId) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean clearResetPasswordToken(int userId) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean isResetPasswordTokenActive(int userId) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean resetPasswordWithToken(String password, byte[] token, int userId)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean isSDPEnabled(int personaId) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean isSdpSupported() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean isSdpSupportedSecureFolder(int userId) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int unlock(String alias, String password) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int lock(String alias) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int setPassword(String alias, String password) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int resetPassword(String alias, String resetToken, String password)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int migrate(String password) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int registerListener(String alias, ISdpListener listener) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int unregisterListener(String alias, ISdpListener listener) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int isLicensed() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int exists(String alias) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int allow(String alias, String packageName) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int disallow(String alias, String packageName) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public double getSupportedSDKVersion() throws RemoteException {
      return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int addEngine(SdpCreationParam param, String password, String resetToken)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int removeEngine(String alias) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public SdpEngineInfo getEngineInfo(String alias) throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean setSensitive(int engineId, String path) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean isSensitive(String path) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int createEncPkgDir(int userId, String packageName) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int saveTokenIntoTrusted(String nameTag, String resetToken) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int deleteToeknFromTrusted(String nameTag) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int unlockViaTrusted(String nameTag, String engineAlias) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public void onBiometricsAuthenticated(int userId) throws RemoteException {}

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public void onDeviceOwnerLocked(int userId) throws RemoteException {}

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public void registerClient(int engineId, ISdpListener client) throws RemoteException {}

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public void unregisterClient(int engineId, ISdpListener client) throws RemoteException {}

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean isDefaultPathUser(int userId) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean setDualDarInfo(int userId, int flag) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public boolean isInnerAuthRequired(int userId) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public void setInnerAuthUserId(int innerAuthUserId, int userId) throws RemoteException {}

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int getInnerAuthUserId(int userId) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public void setMainUserId(int mainUserId, int innerAuthUserId) throws RemoteException {}

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int getMainUserId(int innerAuthUserId) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public void addBlockedClearablePackages(int userId, String packageName)
        throws RemoteException {}

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public List<String> getBlockedClearablePackages(int userId) throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public List<String> getPackageListForDualDarPolicy(String packageType) throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int getPasswordMinimumLengthForInner() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int startTracing(
        int type, int requestorUid, Bundle options, IEndpointMonitorListener listener)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public int stopTracing(int type, int requestorUid) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public void startMonitoringFiles(
        int requestorUid,
        int[] allowedUids,
        List<String> files,
        List<String> inodes,
        IZtdListener listener)
        throws RemoteException {}

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public void stopMonitoringFiles(int requestorUid) throws RemoteException {}

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public void startMonitoringDomains(
        int requestorUid, int[] allowedUids, List<String> domains, IZtdListener listener)
        throws RemoteException {}

    @Override // com.samsung.android.knox.dar.IDarManagerService
    public void stopMonitoringDomains(int requestorUid) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IDarManagerService {
    static final int TRANSACTION_addBlockedClearablePackages = 47;
    static final int TRANSACTION_addEngine = 27;
    static final int TRANSACTION_allow = 24;
    static final int TRANSACTION_clearResetPasswordToken = 9;
    static final int TRANSACTION_createEncPkgDir = 32;
    static final int TRANSACTION_deleteToeknFromTrusted = 34;
    static final int TRANSACTION_disallow = 25;
    static final int TRANSACTION_exists = 23;
    static final int TRANSACTION_getAvailableUserId = 7;
    static final int TRANSACTION_getBlockedClearablePackages = 48;
    static final int TRANSACTION_getEngineInfo = 29;
    static final int TRANSACTION_getInnerAuthUserId = 44;
    static final int TRANSACTION_getMainUserId = 46;
    static final int TRANSACTION_getPackageListForDualDarPolicy = 49;
    static final int TRANSACTION_getPasswordMinimumLengthForInner = 50;
    static final int TRANSACTION_getReservedUserIdForSystem = 6;
    static final int TRANSACTION_getSupportedSDKVersion = 26;
    static final int TRANSACTION_isDarSupported = 1;
    static final int TRANSACTION_isDefaultPathUser = 40;
    static final int TRANSACTION_isDeviceRootKeyInstalled = 3;
    static final int TRANSACTION_isInnerAuthRequired = 42;
    static final int TRANSACTION_isKnoxKeyInstallable = 4;
    static final int TRANSACTION_isLicensed = 22;
    static final int TRANSACTION_isResetPasswordTokenActive = 10;
    static final int TRANSACTION_isSDPEnabled = 12;
    static final int TRANSACTION_isSdpSupported = 13;
    static final int TRANSACTION_isSdpSupportedSecureFolder = 14;
    static final int TRANSACTION_isSensitive = 31;
    static final int TRANSACTION_lock = 16;
    static final int TRANSACTION_migrate = 19;
    static final int TRANSACTION_onBiometricsAuthenticated = 36;
    static final int TRANSACTION_onDeviceOwnerLocked = 37;
    static final int TRANSACTION_registerClient = 38;
    static final int TRANSACTION_registerListener = 20;
    static final int TRANSACTION_removeEngine = 28;
    static final int TRANSACTION_reserveUserIdForSystem = 5;
    static final int TRANSACTION_resetPassword = 18;
    static final int TRANSACTION_resetPasswordWithToken = 11;
    static final int TRANSACTION_saveTokenIntoTrusted = 33;
    static final int TRANSACTION_setDualDarInfo = 41;
    static final int TRANSACTION_setInnerAuthUserId = 43;
    static final int TRANSACTION_setMainUserId = 45;
    static final int TRANSACTION_setPassword = 17;
    static final int TRANSACTION_setResetPasswordToken = 8;
    static final int TRANSACTION_setSensitive = 30;
    static final int TRANSACTION_startMonitoringDomains = 55;
    static final int TRANSACTION_startMonitoringFiles = 53;
    static final int TRANSACTION_startTracing = 51;
    static final int TRANSACTION_stopMonitoringDomains = 56;
    static final int TRANSACTION_stopMonitoringFiles = 54;
    static final int TRANSACTION_stopTracing = 52;
    static final int TRANSACTION_systemReady = 2;
    static final int TRANSACTION_unlock = 15;
    static final int TRANSACTION_unlockViaTrusted = 35;
    static final int TRANSACTION_unregisterClient = 39;
    static final int TRANSACTION_unregisterListener = 21;

    public Stub() {
      attachInterface(this, IDarManagerService.DESCRIPTOR);
    }

    public static IDarManagerService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IDarManagerService.DESCRIPTOR);
      if (iin != null && (iin instanceof IDarManagerService)) {
        return (IDarManagerService) iin;
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
          return "isDarSupported";
        case 2:
          return "systemReady";
        case 3:
          return "isDeviceRootKeyInstalled";
        case 4:
          return "isKnoxKeyInstallable";
        case 5:
          return "reserveUserIdForSystem";
        case 6:
          return "getReservedUserIdForSystem";
        case 7:
          return "getAvailableUserId";
        case 8:
          return "setResetPasswordToken";
        case 9:
          return "clearResetPasswordToken";
        case 10:
          return "isResetPasswordTokenActive";
        case 11:
          return "resetPasswordWithToken";
        case 12:
          return "isSDPEnabled";
        case 13:
          return "isSdpSupported";
        case 14:
          return "isSdpSupportedSecureFolder";
        case 15:
          return "unlock";
        case 16:
          return "lock";
        case 17:
          return "setPassword";
        case 18:
          return "resetPassword";
        case 19:
          return "migrate";
        case 20:
          return "registerListener";
        case 21:
          return "unregisterListener";
        case 22:
          return "isLicensed";
        case 23:
          return "exists";
        case 24:
          return "allow";
        case 25:
          return "disallow";
        case 26:
          return "getSupportedSDKVersion";
        case 27:
          return "addEngine";
        case 28:
          return "removeEngine";
        case 29:
          return "getEngineInfo";
        case 30:
          return "setSensitive";
        case 31:
          return "isSensitive";
        case 32:
          return "createEncPkgDir";
        case 33:
          return "saveTokenIntoTrusted";
        case 34:
          return "deleteToeknFromTrusted";
        case 35:
          return "unlockViaTrusted";
        case 36:
          return "onBiometricsAuthenticated";
        case 37:
          return "onDeviceOwnerLocked";
        case 38:
          return "registerClient";
        case 39:
          return "unregisterClient";
        case 40:
          return "isDefaultPathUser";
        case 41:
          return "setDualDarInfo";
        case 42:
          return "isInnerAuthRequired";
        case 43:
          return "setInnerAuthUserId";
        case 44:
          return "getInnerAuthUserId";
        case 45:
          return "setMainUserId";
        case 46:
          return "getMainUserId";
        case 47:
          return "addBlockedClearablePackages";
        case 48:
          return "getBlockedClearablePackages";
        case 49:
          return "getPackageListForDualDarPolicy";
        case 50:
          return "getPasswordMinimumLengthForInner";
        case 51:
          return "startTracing";
        case 52:
          return "stopTracing";
        case 53:
          return "startMonitoringFiles";
        case 54:
          return "stopMonitoringFiles";
        case 55:
          return "startMonitoringDomains";
        case 56:
          return "stopMonitoringDomains";
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
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(IDarManagerService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IDarManagerService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _result = isDarSupported();
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              systemReady();
              reply.writeNoException();
              return true;
            case 3:
              boolean _result2 = isDeviceRootKeyInstalled();
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 4:
              boolean _result3 = isKnoxKeyInstallable();
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 5:
              int _result4 = reserveUserIdForSystem();
              reply.writeNoException();
              reply.writeInt(_result4);
              return true;
            case 6:
              int _result5 = getReservedUserIdForSystem();
              reply.writeNoException();
              reply.writeInt(_result5);
              return true;
            case 7:
              int _result6 = getAvailableUserId();
              reply.writeNoException();
              reply.writeInt(_result6);
              return true;
            case 8:
              byte[] _arg0 = data.createByteArray();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result7 = setResetPasswordToken(_arg0, _arg1);
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 9:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result8 = clearResetPasswordToken(_arg02);
              reply.writeNoException();
              reply.writeBoolean(_result8);
              return true;
            case 10:
              int _arg03 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result9 = isResetPasswordTokenActive(_arg03);
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            case 11:
              String _arg04 = data.readString();
              byte[] _arg12 = data.createByteArray();
              int _arg2 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result10 = resetPasswordWithToken(_arg04, _arg12, _arg2);
              reply.writeNoException();
              reply.writeBoolean(_result10);
              return true;
            case 12:
              int _arg05 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result11 = isSDPEnabled(_arg05);
              reply.writeNoException();
              reply.writeBoolean(_result11);
              return true;
            case 13:
              boolean _result12 = isSdpSupported();
              reply.writeNoException();
              reply.writeBoolean(_result12);
              return true;
            case 14:
              int _arg06 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result13 = isSdpSupportedSecureFolder(_arg06);
              reply.writeNoException();
              reply.writeBoolean(_result13);
              return true;
            case 15:
              String _arg07 = data.readString();
              String _arg13 = data.readString();
              data.enforceNoDataAvail();
              int _result14 = unlock(_arg07, _arg13);
              reply.writeNoException();
              reply.writeInt(_result14);
              return true;
            case 16:
              String _arg08 = data.readString();
              data.enforceNoDataAvail();
              int _result15 = lock(_arg08);
              reply.writeNoException();
              reply.writeInt(_result15);
              return true;
            case 17:
              String _arg09 = data.readString();
              String _arg14 = data.readString();
              data.enforceNoDataAvail();
              int _result16 = setPassword(_arg09, _arg14);
              reply.writeNoException();
              reply.writeInt(_result16);
              return true;
            case 18:
              String _arg010 = data.readString();
              String _arg15 = data.readString();
              String _arg22 = data.readString();
              data.enforceNoDataAvail();
              int _result17 = resetPassword(_arg010, _arg15, _arg22);
              reply.writeNoException();
              reply.writeInt(_result17);
              return true;
            case 19:
              String _arg011 = data.readString();
              data.enforceNoDataAvail();
              int _result18 = migrate(_arg011);
              reply.writeNoException();
              reply.writeInt(_result18);
              return true;
            case 20:
              String _arg012 = data.readString();
              ISdpListener _arg16 = ISdpListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result19 = registerListener(_arg012, _arg16);
              reply.writeNoException();
              reply.writeInt(_result19);
              return true;
            case 21:
              String _arg013 = data.readString();
              ISdpListener _arg17 = ISdpListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result20 = unregisterListener(_arg013, _arg17);
              reply.writeNoException();
              reply.writeInt(_result20);
              return true;
            case 22:
              int _result21 = isLicensed();
              reply.writeNoException();
              reply.writeInt(_result21);
              return true;
            case 23:
              String _arg014 = data.readString();
              data.enforceNoDataAvail();
              int _result22 = exists(_arg014);
              reply.writeNoException();
              reply.writeInt(_result22);
              return true;
            case 24:
              String _arg015 = data.readString();
              String _arg18 = data.readString();
              data.enforceNoDataAvail();
              int _result23 = allow(_arg015, _arg18);
              reply.writeNoException();
              reply.writeInt(_result23);
              return true;
            case 25:
              String _arg016 = data.readString();
              String _arg19 = data.readString();
              data.enforceNoDataAvail();
              int _result24 = disallow(_arg016, _arg19);
              reply.writeNoException();
              reply.writeInt(_result24);
              return true;
            case 26:
              double _result25 = getSupportedSDKVersion();
              reply.writeNoException();
              reply.writeDouble(_result25);
              return true;
            case 27:
              SdpCreationParam _arg017 =
                  (SdpCreationParam) data.readTypedObject(SdpCreationParam.CREATOR);
              String _arg110 = data.readString();
              String _arg23 = data.readString();
              data.enforceNoDataAvail();
              int _result26 = addEngine(_arg017, _arg110, _arg23);
              reply.writeNoException();
              reply.writeInt(_result26);
              return true;
            case 28:
              String _arg018 = data.readString();
              data.enforceNoDataAvail();
              int _result27 = removeEngine(_arg018);
              reply.writeNoException();
              reply.writeInt(_result27);
              return true;
            case 29:
              String _arg019 = data.readString();
              data.enforceNoDataAvail();
              SdpEngineInfo _result28 = getEngineInfo(_arg019);
              reply.writeNoException();
              reply.writeTypedObject(_result28, 1);
              return true;
            case 30:
              int _arg020 = data.readInt();
              String _arg111 = data.readString();
              data.enforceNoDataAvail();
              boolean _result29 = setSensitive(_arg020, _arg111);
              reply.writeNoException();
              reply.writeBoolean(_result29);
              return true;
            case 31:
              String _arg021 = data.readString();
              data.enforceNoDataAvail();
              boolean _result30 = isSensitive(_arg021);
              reply.writeNoException();
              reply.writeBoolean(_result30);
              return true;
            case 32:
              int _arg022 = data.readInt();
              String _arg112 = data.readString();
              data.enforceNoDataAvail();
              int _result31 = createEncPkgDir(_arg022, _arg112);
              reply.writeNoException();
              reply.writeInt(_result31);
              return true;
            case 33:
              String _arg023 = data.readString();
              String _arg113 = data.readString();
              data.enforceNoDataAvail();
              int _result32 = saveTokenIntoTrusted(_arg023, _arg113);
              reply.writeNoException();
              reply.writeInt(_result32);
              return true;
            case 34:
              String _arg024 = data.readString();
              data.enforceNoDataAvail();
              int _result33 = deleteToeknFromTrusted(_arg024);
              reply.writeNoException();
              reply.writeInt(_result33);
              return true;
            case 35:
              String _arg025 = data.readString();
              String _arg114 = data.readString();
              data.enforceNoDataAvail();
              int _result34 = unlockViaTrusted(_arg025, _arg114);
              reply.writeNoException();
              reply.writeInt(_result34);
              return true;
            case 36:
              int _arg026 = data.readInt();
              data.enforceNoDataAvail();
              onBiometricsAuthenticated(_arg026);
              reply.writeNoException();
              return true;
            case 37:
              int _arg027 = data.readInt();
              data.enforceNoDataAvail();
              onDeviceOwnerLocked(_arg027);
              reply.writeNoException();
              return true;
            case 38:
              int _arg028 = data.readInt();
              ISdpListener _arg115 = ISdpListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerClient(_arg028, _arg115);
              return true;
            case 39:
              int _arg029 = data.readInt();
              ISdpListener _arg116 = ISdpListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterClient(_arg029, _arg116);
              return true;
            case 40:
              int _arg030 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result35 = isDefaultPathUser(_arg030);
              reply.writeNoException();
              reply.writeBoolean(_result35);
              return true;
            case 41:
              int _arg031 = data.readInt();
              int _arg117 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result36 = setDualDarInfo(_arg031, _arg117);
              reply.writeNoException();
              reply.writeBoolean(_result36);
              return true;
            case 42:
              int _arg032 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result37 = isInnerAuthRequired(_arg032);
              reply.writeNoException();
              reply.writeBoolean(_result37);
              return true;
            case 43:
              int _arg033 = data.readInt();
              int _arg118 = data.readInt();
              data.enforceNoDataAvail();
              setInnerAuthUserId(_arg033, _arg118);
              reply.writeNoException();
              return true;
            case 44:
              int _arg034 = data.readInt();
              data.enforceNoDataAvail();
              int _result38 = getInnerAuthUserId(_arg034);
              reply.writeNoException();
              reply.writeInt(_result38);
              return true;
            case 45:
              int _arg035 = data.readInt();
              int _arg119 = data.readInt();
              data.enforceNoDataAvail();
              setMainUserId(_arg035, _arg119);
              reply.writeNoException();
              return true;
            case 46:
              int _arg036 = data.readInt();
              data.enforceNoDataAvail();
              int _result39 = getMainUserId(_arg036);
              reply.writeNoException();
              reply.writeInt(_result39);
              return true;
            case 47:
              int _arg037 = data.readInt();
              String _arg120 = data.readString();
              data.enforceNoDataAvail();
              addBlockedClearablePackages(_arg037, _arg120);
              reply.writeNoException();
              return true;
            case 48:
              int _arg038 = data.readInt();
              data.enforceNoDataAvail();
              List<String> _result40 = getBlockedClearablePackages(_arg038);
              reply.writeNoException();
              reply.writeStringList(_result40);
              return true;
            case 49:
              String _arg039 = data.readString();
              data.enforceNoDataAvail();
              List<String> _result41 = getPackageListForDualDarPolicy(_arg039);
              reply.writeNoException();
              reply.writeStringList(_result41);
              return true;
            case 50:
              int _result42 = getPasswordMinimumLengthForInner();
              reply.writeNoException();
              reply.writeInt(_result42);
              return true;
            case 51:
              int _arg040 = data.readInt();
              int _arg121 = data.readInt();
              Bundle _arg24 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              IEndpointMonitorListener _arg3 =
                  IEndpointMonitorListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result43 = startTracing(_arg040, _arg121, _arg24, _arg3);
              reply.writeNoException();
              reply.writeInt(_result43);
              return true;
            case 52:
              int _arg041 = data.readInt();
              int _arg122 = data.readInt();
              data.enforceNoDataAvail();
              int _result44 = stopTracing(_arg041, _arg122);
              reply.writeNoException();
              reply.writeInt(_result44);
              return true;
            case 53:
              int _arg042 = data.readInt();
              int[] _arg123 = data.createIntArray();
              List<String> _arg25 = data.createStringArrayList();
              List<String> _arg32 = data.createStringArrayList();
              IZtdListener _arg4 = IZtdListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              startMonitoringFiles(_arg042, _arg123, _arg25, _arg32, _arg4);
              reply.writeNoException();
              return true;
            case 54:
              int _arg043 = data.readInt();
              data.enforceNoDataAvail();
              stopMonitoringFiles(_arg043);
              reply.writeNoException();
              return true;
            case 55:
              int _arg044 = data.readInt();
              int[] _arg124 = data.createIntArray();
              List<String> _arg26 = data.createStringArrayList();
              IZtdListener _arg33 = IZtdListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              startMonitoringDomains(_arg044, _arg124, _arg26, _arg33);
              reply.writeNoException();
              return true;
            case 56:
              int _arg045 = data.readInt();
              data.enforceNoDataAvail();
              stopMonitoringDomains(_arg045);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IDarManagerService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IDarManagerService.DESCRIPTOR;
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean isDarSupported() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public void systemReady() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean isDeviceRootKeyInstalled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean isKnoxKeyInstallable() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int reserveUserIdForSystem() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int getReservedUserIdForSystem() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int getAvailableUserId() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean setResetPasswordToken(byte[] token, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeByteArray(token);
          _data.writeInt(userId);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean clearResetPasswordToken(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean isResetPasswordTokenActive(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean resetPasswordWithToken(String password, byte[] token, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(password);
          _data.writeByteArray(token);
          _data.writeInt(userId);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean isSDPEnabled(int personaId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(personaId);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean isSdpSupported() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean isSdpSupportedSecureFolder(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int unlock(String alias, String password) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(alias);
          _data.writeString(password);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int lock(String alias) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(alias);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int setPassword(String alias, String password) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(alias);
          _data.writeString(password);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int resetPassword(String alias, String resetToken, String password)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(alias);
          _data.writeString(resetToken);
          _data.writeString(password);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int migrate(String password) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(password);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int registerListener(String alias, ISdpListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(alias);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int unregisterListener(String alias, ISdpListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(alias);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int isLicensed() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int exists(String alias) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(alias);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int allow(String alias, String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(alias);
          _data.writeString(packageName);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int disallow(String alias, String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(alias);
          _data.writeString(packageName);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public double getSupportedSDKVersion() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
          double _result = _reply.readDouble();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int addEngine(SdpCreationParam param, String password, String resetToken)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeTypedObject(param, 0);
          _data.writeString(password);
          _data.writeString(resetToken);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int removeEngine(String alias) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(alias);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public SdpEngineInfo getEngineInfo(String alias) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(alias);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
          SdpEngineInfo _result = (SdpEngineInfo) _reply.readTypedObject(SdpEngineInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean setSensitive(int engineId, String path) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(engineId);
          _data.writeString(path);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean isSensitive(String path) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(path);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int createEncPkgDir(int userId, String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int saveTokenIntoTrusted(String nameTag, String resetToken) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(nameTag);
          _data.writeString(resetToken);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int deleteToeknFromTrusted(String nameTag) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(nameTag);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int unlockViaTrusted(String nameTag, String engineAlias) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(nameTag);
          _data.writeString(engineAlias);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public void onBiometricsAuthenticated(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public void onDeviceOwnerLocked(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public void registerClient(int engineId, ISdpListener client) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(engineId);
          _data.writeStrongInterface(client);
          this.mRemote.transact(38, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public void unregisterClient(int engineId, ISdpListener client) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(engineId);
          _data.writeStrongInterface(client);
          this.mRemote.transact(39, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean isDefaultPathUser(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean setDualDarInfo(int userId, int flag) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeInt(flag);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public boolean isInnerAuthRequired(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public void setInnerAuthUserId(int innerAuthUserId, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(innerAuthUserId);
          _data.writeInt(userId);
          this.mRemote.transact(43, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int getInnerAuthUserId(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public void setMainUserId(int mainUserId, int innerAuthUserId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(mainUserId);
          _data.writeInt(innerAuthUserId);
          this.mRemote.transact(45, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int getMainUserId(int innerAuthUserId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(innerAuthUserId);
          this.mRemote.transact(46, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public void addBlockedClearablePackages(int userId, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          this.mRemote.transact(47, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public List<String> getBlockedClearablePackages(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(48, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public List<String> getPackageListForDualDarPolicy(String packageType)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeString(packageType);
          this.mRemote.transact(49, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int getPasswordMinimumLengthForInner() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          this.mRemote.transact(50, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int startTracing(
          int type, int requestorUid, Bundle options, IEndpointMonitorListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeInt(requestorUid);
          _data.writeTypedObject(options, 0);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(51, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public int stopTracing(int type, int requestorUid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeInt(requestorUid);
          this.mRemote.transact(52, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public void startMonitoringFiles(
          int requestorUid,
          int[] allowedUids,
          List<String> files,
          List<String> inodes,
          IZtdListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(requestorUid);
          _data.writeIntArray(allowedUids);
          _data.writeStringList(files);
          _data.writeStringList(inodes);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(53, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public void stopMonitoringFiles(int requestorUid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(requestorUid);
          this.mRemote.transact(54, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public void startMonitoringDomains(
          int requestorUid, int[] allowedUids, List<String> domains, IZtdListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(requestorUid);
          _data.writeIntArray(allowedUids);
          _data.writeStringList(domains);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(55, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.IDarManagerService
      public void stopMonitoringDomains(int requestorUid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
          _data.writeInt(requestorUid);
          this.mRemote.transact(56, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 55;
    }
  }
}
