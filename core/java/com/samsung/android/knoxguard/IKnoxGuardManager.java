package com.samsung.android.knoxguard;

import android.content.ComponentName;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import android.sec.enterprise.content.SecContentProviderURI;
import java.util.List;

/* loaded from: classes5.dex */
public interface IKnoxGuardManager extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.knoxguard.IKnoxGuardManager";

  boolean addPackagesToClearCacheBlockList(List<String> list) throws RemoteException;

  boolean addPackagesToForceStopBlockList(List<String> list) throws RemoteException;

  boolean allowFirmwareRecovery(boolean z) throws RemoteException;

  boolean allowOTAUpgrade(boolean z) throws RemoteException;

  boolean allowSafeMode(boolean z) throws RemoteException;

  void bindToLockScreen() throws RemoteException;

  void callKGsv() throws RemoteException;

  String generateHotpDHRequest() throws RemoteException;

  String getClientData() throws RemoteException;

  String getHotpChallenge() throws RemoteException;

  String getKGID() throws RemoteException;

  String getKGPolicy() throws RemoteException;

  Bundle getKGServiceInfo() throws RemoteException;

  int getKGServiceVersion() throws RemoteException;

  String getLockAction() throws RemoteException;

  String getNonce(String str, String str2) throws RemoteException;

  String getPBAUniqueNumber() throws RemoteException;

  String getStringSystemProperty(String str, String str2) throws RemoteException;

  int getTAError() throws RemoteException;

  String getTAInfo(int i) throws RemoteException;

  int getTAState() throws RemoteException;

  int getTAStateSetError(boolean z) throws RemoteException;

  boolean isSkipSupportContainerSupported() throws RemoteException;

  boolean isVpnExceptionRequired() throws RemoteException;

  int lockScreen(
      String str,
      String str2,
      String str3,
      String str4,
      String str5,
      boolean z,
      boolean z2,
      Bundle bundle)
      throws RemoteException;

  int provisionCert(String str, String str2, String str3, String str4) throws RemoteException;

  void registerIntent(String str, List<String> list) throws RemoteException;

  void removeActiveAdmin(ComponentName componentName) throws RemoteException;

  int resetRPMB() throws RemoteException;

  int resetRPMB2(String str) throws RemoteException;

  void revokeRuntimePermission(String str, String str2, UserHandle userHandle)
      throws RemoteException;

  void setActiveAdmin(ComponentName componentName) throws RemoteException;

  boolean setAdminRemovable(boolean z) throws RemoteException;

  void setAirplaneMode(boolean z) throws RemoteException;

  boolean setApplicationUninstallationDisabled(String str) throws RemoteException;

  int setCheckingState() throws RemoteException;

  int setClientData(String str) throws RemoteException;

  void setClientHealthOK() throws RemoteException;

  void setKnoxGuardExemptRule(boolean z) throws RemoteException;

  void setRemoteLockToLockscreen(
      int i,
      boolean z,
      String str,
      String str2,
      String str3,
      boolean z2,
      String str4,
      int i2,
      long j,
      int i3,
      boolean z3,
      Bundle bundle)
      throws RemoteException;

  void setRemoteLockToLockscreenWithSkipSupport(
      int i,
      boolean z,
      String str,
      String str2,
      String str3,
      boolean z2,
      String str4,
      int i2,
      long j,
      int i3,
      boolean z3,
      Bundle bundle,
      boolean z4)
      throws RemoteException;

  void setRuntimePermission(String str, String str2, UserHandle userHandle) throws RemoteException;

  boolean shouldBlockCustomRom() throws RemoteException;

  boolean showInstallmentStatus() throws RemoteException;

  void unRegisterIntent() throws RemoteException;

  int unlockScreen() throws RemoteException;

  int verifyCompleteToken(String str) throws RemoteException;

  int verifyHOTPDHChallenge(String str, String str2, String str3) throws RemoteException;

  int verifyHOTPPin(String str) throws RemoteException;

  int verifyHOTPsecret(String str) throws RemoteException;

  String verifyKgRot() throws RemoteException;

  String verifyPolicy(String str, String str2) throws RemoteException;

  String verifyRegistrationInfo(String str, String str2) throws RemoteException;

  public static class Default implements IKnoxGuardManager {
    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public void callKGsv() throws RemoteException {}

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public void registerIntent(String preFix, List<String> actionList) throws RemoteException {}

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public boolean setAdminRemovable(boolean removable) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public boolean allowFirmwareRecovery(boolean allow) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public boolean allowOTAUpgrade(boolean allow) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public boolean allowSafeMode(boolean allow) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public boolean addPackagesToForceStopBlockList(List<String> packageList)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public boolean addPackagesToClearCacheBlockList(List<String> packageList)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public boolean setApplicationUninstallationDisabled(String packageName) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public void setAirplaneMode(boolean enabled) throws RemoteException {}

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public void setActiveAdmin(ComponentName adminReceiver) throws RemoteException {}

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public void removeActiveAdmin(ComponentName adminReceiver) throws RemoteException {}

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public void setRuntimePermission(String packageName, String permission, UserHandle userhandle)
        throws RemoteException {}

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public void revokeRuntimePermission(
        String packageName, String permission, UserHandle userhandle) throws RemoteException {}

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public void setRemoteLockToLockscreen(
        int type,
        boolean state,
        String msg,
        String number,
        String email,
        boolean emergencycallbutton,
        String name,
        int failcount,
        long timeout,
        int blockcount,
        boolean skippin,
        Bundle bundle)
        throws RemoteException {}

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public void setRemoteLockToLockscreenWithSkipSupport(
        int type,
        boolean state,
        String msg,
        String number,
        String email,
        boolean emergencycallbutton,
        String name,
        int failcount,
        long timeout,
        int blockcount,
        boolean skippin,
        Bundle bundle,
        boolean skipSupportContainer)
        throws RemoteException {}

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public boolean isSkipSupportContainerSupported() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public String getPBAUniqueNumber() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public boolean showInstallmentStatus() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public boolean shouldBlockCustomRom() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public void setKnoxGuardExemptRule(boolean add) throws RemoteException {}

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public void bindToLockScreen() throws RemoteException {}

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int getKGServiceVersion() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public void unRegisterIntent() throws RemoteException {}

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int verifyHOTPsecret(String s) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int verifyHOTPPin(String pin) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int getTAState() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int getTAStateSetError(boolean setError) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public String getKGPolicy() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int verifyHOTPDHChallenge(String hub, String sign, String challenge)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int verifyCompleteToken(String serverResponse) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public String generateHotpDHRequest() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public String getHotpChallenge() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public String verifyRegistrationInfo(String regInfo, String regInfoSig) throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public String verifyPolicy(String policy, String signature) throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int unlockScreen() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int lockScreen(
        String actionName,
        String clientName,
        String phoneNumber,
        String emailAddress,
        String message,
        boolean skipPin,
        boolean skipSupport,
        Bundle bundle)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public String getLockAction() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public String getClientData() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int setClientData(String clientData) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public String getKGID() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int resetRPMB() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int resetRPMB2(String optional) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int setCheckingState() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public String verifyKgRot() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public String getStringSystemProperty(String key, String def) throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int getTAError() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public String getNonce(String serverMsg, String serverToken) throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public String getTAInfo(int infoFlag) throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public int provisionCert(String enrollCert, String hotpCert, String policyCert, String blCert)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public boolean isVpnExceptionRequired() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public void setClientHealthOK() throws RemoteException {}

    @Override // com.samsung.android.knoxguard.IKnoxGuardManager
    public Bundle getKGServiceInfo() throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IKnoxGuardManager {
    static final int TRANSACTION_addPackagesToClearCacheBlockList = 8;
    static final int TRANSACTION_addPackagesToForceStopBlockList = 7;
    static final int TRANSACTION_allowFirmwareRecovery = 4;
    static final int TRANSACTION_allowOTAUpgrade = 5;
    static final int TRANSACTION_allowSafeMode = 6;
    static final int TRANSACTION_bindToLockScreen = 22;
    static final int TRANSACTION_callKGsv = 1;
    static final int TRANSACTION_generateHotpDHRequest = 32;
    static final int TRANSACTION_getClientData = 39;
    static final int TRANSACTION_getHotpChallenge = 33;
    static final int TRANSACTION_getKGID = 41;
    static final int TRANSACTION_getKGPolicy = 29;
    static final int TRANSACTION_getKGServiceInfo = 53;
    static final int TRANSACTION_getKGServiceVersion = 23;
    static final int TRANSACTION_getLockAction = 38;
    static final int TRANSACTION_getNonce = 48;
    static final int TRANSACTION_getPBAUniqueNumber = 18;
    static final int TRANSACTION_getStringSystemProperty = 46;
    static final int TRANSACTION_getTAError = 47;
    static final int TRANSACTION_getTAInfo = 49;
    static final int TRANSACTION_getTAState = 27;
    static final int TRANSACTION_getTAStateSetError = 28;
    static final int TRANSACTION_isSkipSupportContainerSupported = 17;
    static final int TRANSACTION_isVpnExceptionRequired = 51;
    static final int TRANSACTION_lockScreen = 37;
    static final int TRANSACTION_provisionCert = 50;
    static final int TRANSACTION_registerIntent = 2;
    static final int TRANSACTION_removeActiveAdmin = 12;
    static final int TRANSACTION_resetRPMB = 42;
    static final int TRANSACTION_resetRPMB2 = 43;
    static final int TRANSACTION_revokeRuntimePermission = 14;
    static final int TRANSACTION_setActiveAdmin = 11;
    static final int TRANSACTION_setAdminRemovable = 3;
    static final int TRANSACTION_setAirplaneMode = 10;
    static final int TRANSACTION_setApplicationUninstallationDisabled = 9;
    static final int TRANSACTION_setCheckingState = 44;
    static final int TRANSACTION_setClientData = 40;
    static final int TRANSACTION_setClientHealthOK = 52;
    static final int TRANSACTION_setKnoxGuardExemptRule = 21;
    static final int TRANSACTION_setRemoteLockToLockscreen = 15;
    static final int TRANSACTION_setRemoteLockToLockscreenWithSkipSupport = 16;
    static final int TRANSACTION_setRuntimePermission = 13;
    static final int TRANSACTION_shouldBlockCustomRom = 20;
    static final int TRANSACTION_showInstallmentStatus = 19;
    static final int TRANSACTION_unRegisterIntent = 24;
    static final int TRANSACTION_unlockScreen = 36;
    static final int TRANSACTION_verifyCompleteToken = 31;
    static final int TRANSACTION_verifyHOTPDHChallenge = 30;
    static final int TRANSACTION_verifyHOTPPin = 26;
    static final int TRANSACTION_verifyHOTPsecret = 25;
    static final int TRANSACTION_verifyKgRot = 45;
    static final int TRANSACTION_verifyPolicy = 35;
    static final int TRANSACTION_verifyRegistrationInfo = 34;

    public Stub() {
      attachInterface(this, IKnoxGuardManager.DESCRIPTOR);
    }

    public static IKnoxGuardManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IKnoxGuardManager.DESCRIPTOR);
      if (iin != null && (iin instanceof IKnoxGuardManager)) {
        return (IKnoxGuardManager) iin;
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
          return "callKGsv";
        case 2:
          return "registerIntent";
        case 3:
          return SecContentProviderURI.ENTERPRISEDEVICEMANAGERPOLICY_SETADMINREMOVABLE_METHOD;
        case 4:
          return "allowFirmwareRecovery";
        case 5:
          return "allowOTAUpgrade";
        case 6:
          return "allowSafeMode";
        case 7:
          return "addPackagesToForceStopBlockList";
        case 8:
          return "addPackagesToClearCacheBlockList";
        case 9:
          return "setApplicationUninstallationDisabled";
        case 10:
          return "setAirplaneMode";
        case 11:
          return SecContentProviderURI.ENTERPRISEDEVICEMANAGERPOLICY_SETACTIVEADMIN_METHOD;
        case 12:
          return SecContentProviderURI.ENTERPRISEDEVICEMANAGERPOLICY_REMOVEACTIVEADMIN_METHOD;
        case 13:
          return "setRuntimePermission";
        case 14:
          return "revokeRuntimePermission";
        case 15:
          return "setRemoteLockToLockscreen";
        case 16:
          return "setRemoteLockToLockscreenWithSkipSupport";
        case 17:
          return "isSkipSupportContainerSupported";
        case 18:
          return "getPBAUniqueNumber";
        case 19:
          return "showInstallmentStatus";
        case 20:
          return "shouldBlockCustomRom";
        case 21:
          return "setKnoxGuardExemptRule";
        case 22:
          return "bindToLockScreen";
        case 23:
          return "getKGServiceVersion";
        case 24:
          return "unRegisterIntent";
        case 25:
          return "verifyHOTPsecret";
        case 26:
          return "verifyHOTPPin";
        case 27:
          return "getTAState";
        case 28:
          return "getTAStateSetError";
        case 29:
          return "getKGPolicy";
        case 30:
          return "verifyHOTPDHChallenge";
        case 31:
          return "verifyCompleteToken";
        case 32:
          return "generateHotpDHRequest";
        case 33:
          return "getHotpChallenge";
        case 34:
          return "verifyRegistrationInfo";
        case 35:
          return "verifyPolicy";
        case 36:
          return "unlockScreen";
        case 37:
          return "lockScreen";
        case 38:
          return "getLockAction";
        case 39:
          return "getClientData";
        case 40:
          return "setClientData";
        case 41:
          return "getKGID";
        case 42:
          return "resetRPMB";
        case 43:
          return "resetRPMB2";
        case 44:
          return "setCheckingState";
        case 45:
          return "verifyKgRot";
        case 46:
          return "getStringSystemProperty";
        case 47:
          return "getTAError";
        case 48:
          return "getNonce";
        case 49:
          return "getTAInfo";
        case 50:
          return "provisionCert";
        case 51:
          return "isVpnExceptionRequired";
        case 52:
          return "setClientHealthOK";
        case 53:
          return "getKGServiceInfo";
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
        data.enforceInterface(IKnoxGuardManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IKnoxGuardManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              callKGsv();
              reply.writeNoException();
              return true;
            case 2:
              String _arg0 = data.readString();
              List<String> _arg1 = data.createStringArrayList();
              data.enforceNoDataAvail();
              registerIntent(_arg0, _arg1);
              reply.writeNoException();
              return true;
            case 3:
              boolean _arg02 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result = setAdminRemovable(_arg02);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 4:
              boolean _arg03 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result2 = allowFirmwareRecovery(_arg03);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 5:
              boolean _arg04 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result3 = allowOTAUpgrade(_arg04);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 6:
              boolean _arg05 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result4 = allowSafeMode(_arg05);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 7:
              List<String> _arg06 = data.createStringArrayList();
              data.enforceNoDataAvail();
              boolean _result5 = addPackagesToForceStopBlockList(_arg06);
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 8:
              List<String> _arg07 = data.createStringArrayList();
              data.enforceNoDataAvail();
              boolean _result6 = addPackagesToClearCacheBlockList(_arg07);
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            case 9:
              String _arg08 = data.readString();
              data.enforceNoDataAvail();
              boolean _result7 = setApplicationUninstallationDisabled(_arg08);
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 10:
              boolean _arg09 = data.readBoolean();
              data.enforceNoDataAvail();
              setAirplaneMode(_arg09);
              reply.writeNoException();
              return true;
            case 11:
              ComponentName _arg010 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              setActiveAdmin(_arg010);
              reply.writeNoException();
              return true;
            case 12:
              ComponentName _arg011 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              removeActiveAdmin(_arg011);
              reply.writeNoException();
              return true;
            case 13:
              String _arg012 = data.readString();
              String _arg12 = data.readString();
              UserHandle _arg2 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
              data.enforceNoDataAvail();
              setRuntimePermission(_arg012, _arg12, _arg2);
              reply.writeNoException();
              return true;
            case 14:
              String _arg013 = data.readString();
              String _arg13 = data.readString();
              UserHandle _arg22 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
              data.enforceNoDataAvail();
              revokeRuntimePermission(_arg013, _arg13, _arg22);
              reply.writeNoException();
              return true;
            case 15:
              int _arg014 = data.readInt();
              boolean _arg14 = data.readBoolean();
              String _arg23 = data.readString();
              String _arg3 = data.readString();
              String _arg4 = data.readString();
              boolean _arg5 = data.readBoolean();
              String _arg6 = data.readString();
              int _arg7 = data.readInt();
              long _arg8 = data.readLong();
              int _arg9 = data.readInt();
              boolean _arg10 = data.readBoolean();
              Bundle _arg11 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              setRemoteLockToLockscreen(
                  _arg014, _arg14, _arg23, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9, _arg10,
                  _arg11);
              reply.writeNoException();
              return true;
            case 16:
              int _arg015 = data.readInt();
              boolean _arg15 = data.readBoolean();
              String _arg24 = data.readString();
              String _arg32 = data.readString();
              String _arg42 = data.readString();
              boolean _arg52 = data.readBoolean();
              String _arg62 = data.readString();
              int _arg72 = data.readInt();
              long _arg82 = data.readLong();
              int _arg92 = data.readInt();
              boolean _arg102 = data.readBoolean();
              Bundle _arg112 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              boolean _arg122 = data.readBoolean();
              data.enforceNoDataAvail();
              setRemoteLockToLockscreenWithSkipSupport(
                  _arg015, _arg15, _arg24, _arg32, _arg42, _arg52, _arg62, _arg72, _arg82, _arg92,
                  _arg102, _arg112, _arg122);
              reply.writeNoException();
              return true;
            case 17:
              boolean _result8 = isSkipSupportContainerSupported();
              reply.writeNoException();
              reply.writeBoolean(_result8);
              return true;
            case 18:
              String _result9 = getPBAUniqueNumber();
              reply.writeNoException();
              reply.writeString(_result9);
              return true;
            case 19:
              boolean _result10 = showInstallmentStatus();
              reply.writeNoException();
              reply.writeBoolean(_result10);
              return true;
            case 20:
              boolean _result11 = shouldBlockCustomRom();
              reply.writeNoException();
              reply.writeBoolean(_result11);
              return true;
            case 21:
              boolean _arg016 = data.readBoolean();
              data.enforceNoDataAvail();
              setKnoxGuardExemptRule(_arg016);
              reply.writeNoException();
              return true;
            case 22:
              bindToLockScreen();
              reply.writeNoException();
              return true;
            case 23:
              int _result12 = getKGServiceVersion();
              reply.writeNoException();
              reply.writeInt(_result12);
              return true;
            case 24:
              unRegisterIntent();
              reply.writeNoException();
              return true;
            case 25:
              String _arg017 = data.readString();
              data.enforceNoDataAvail();
              int _result13 = verifyHOTPsecret(_arg017);
              reply.writeNoException();
              reply.writeInt(_result13);
              return true;
            case 26:
              String _arg018 = data.readString();
              data.enforceNoDataAvail();
              int _result14 = verifyHOTPPin(_arg018);
              reply.writeNoException();
              reply.writeInt(_result14);
              return true;
            case 27:
              int _result15 = getTAState();
              reply.writeNoException();
              reply.writeInt(_result15);
              return true;
            case 28:
              boolean _arg019 = data.readBoolean();
              data.enforceNoDataAvail();
              int _result16 = getTAStateSetError(_arg019);
              reply.writeNoException();
              reply.writeInt(_result16);
              return true;
            case 29:
              String _result17 = getKGPolicy();
              reply.writeNoException();
              reply.writeString(_result17);
              return true;
            case 30:
              String _arg020 = data.readString();
              String _arg16 = data.readString();
              String _arg25 = data.readString();
              data.enforceNoDataAvail();
              int _result18 = verifyHOTPDHChallenge(_arg020, _arg16, _arg25);
              reply.writeNoException();
              reply.writeInt(_result18);
              return true;
            case 31:
              String _arg021 = data.readString();
              data.enforceNoDataAvail();
              int _result19 = verifyCompleteToken(_arg021);
              reply.writeNoException();
              reply.writeInt(_result19);
              return true;
            case 32:
              String _result20 = generateHotpDHRequest();
              reply.writeNoException();
              reply.writeString(_result20);
              return true;
            case 33:
              String _result21 = getHotpChallenge();
              reply.writeNoException();
              reply.writeString(_result21);
              return true;
            case 34:
              String _arg022 = data.readString();
              String _arg17 = data.readString();
              data.enforceNoDataAvail();
              String _result22 = verifyRegistrationInfo(_arg022, _arg17);
              reply.writeNoException();
              reply.writeString(_result22);
              return true;
            case 35:
              String _arg023 = data.readString();
              String _arg18 = data.readString();
              data.enforceNoDataAvail();
              String _result23 = verifyPolicy(_arg023, _arg18);
              reply.writeNoException();
              reply.writeString(_result23);
              return true;
            case 36:
              int _result24 = unlockScreen();
              reply.writeNoException();
              reply.writeInt(_result24);
              return true;
            case 37:
              String _arg024 = data.readString();
              String _arg19 = data.readString();
              String _arg26 = data.readString();
              String _arg33 = data.readString();
              String _arg43 = data.readString();
              boolean _arg53 = data.readBoolean();
              boolean _arg63 = data.readBoolean();
              Bundle _arg73 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              int _result25 =
                  lockScreen(_arg024, _arg19, _arg26, _arg33, _arg43, _arg53, _arg63, _arg73);
              reply.writeNoException();
              reply.writeInt(_result25);
              return true;
            case 38:
              String _result26 = getLockAction();
              reply.writeNoException();
              reply.writeString(_result26);
              return true;
            case 39:
              String _result27 = getClientData();
              reply.writeNoException();
              reply.writeString(_result27);
              return true;
            case 40:
              String _arg025 = data.readString();
              data.enforceNoDataAvail();
              int _result28 = setClientData(_arg025);
              reply.writeNoException();
              reply.writeInt(_result28);
              return true;
            case 41:
              String _result29 = getKGID();
              reply.writeNoException();
              reply.writeString(_result29);
              return true;
            case 42:
              int _result30 = resetRPMB();
              reply.writeNoException();
              reply.writeInt(_result30);
              return true;
            case 43:
              String _arg026 = data.readString();
              data.enforceNoDataAvail();
              int _result31 = resetRPMB2(_arg026);
              reply.writeNoException();
              reply.writeInt(_result31);
              return true;
            case 44:
              int _result32 = setCheckingState();
              reply.writeNoException();
              reply.writeInt(_result32);
              return true;
            case 45:
              String _result33 = verifyKgRot();
              reply.writeNoException();
              reply.writeString(_result33);
              return true;
            case 46:
              String _arg027 = data.readString();
              String _arg110 = data.readString();
              data.enforceNoDataAvail();
              String _result34 = getStringSystemProperty(_arg027, _arg110);
              reply.writeNoException();
              reply.writeString(_result34);
              return true;
            case 47:
              int _result35 = getTAError();
              reply.writeNoException();
              reply.writeInt(_result35);
              return true;
            case 48:
              String _arg028 = data.readString();
              String _arg111 = data.readString();
              data.enforceNoDataAvail();
              String _result36 = getNonce(_arg028, _arg111);
              reply.writeNoException();
              reply.writeString(_result36);
              return true;
            case 49:
              int _arg029 = data.readInt();
              data.enforceNoDataAvail();
              String _result37 = getTAInfo(_arg029);
              reply.writeNoException();
              reply.writeString(_result37);
              return true;
            case 50:
              String _arg030 = data.readString();
              String _arg113 = data.readString();
              String _arg27 = data.readString();
              String _arg34 = data.readString();
              data.enforceNoDataAvail();
              int _result38 = provisionCert(_arg030, _arg113, _arg27, _arg34);
              reply.writeNoException();
              reply.writeInt(_result38);
              return true;
            case 51:
              boolean _result39 = isVpnExceptionRequired();
              reply.writeNoException();
              reply.writeBoolean(_result39);
              return true;
            case 52:
              setClientHealthOK();
              reply.writeNoException();
              return true;
            case 53:
              Bundle _result40 = getKGServiceInfo();
              reply.writeNoException();
              reply.writeTypedObject(_result40, 1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IKnoxGuardManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IKnoxGuardManager.DESCRIPTOR;
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public void callKGsv() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public void registerIntent(String preFix, List<String> actionList) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(preFix);
          _data.writeStringList(actionList);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public boolean setAdminRemovable(boolean removable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeBoolean(removable);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public boolean allowFirmwareRecovery(boolean allow) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeBoolean(allow);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public boolean allowOTAUpgrade(boolean allow) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeBoolean(allow);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public boolean allowSafeMode(boolean allow) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeBoolean(allow);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public boolean addPackagesToForceStopBlockList(List<String> packageList)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeStringList(packageList);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public boolean addPackagesToClearCacheBlockList(List<String> packageList)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeStringList(packageList);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public boolean setApplicationUninstallationDisabled(String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public void setAirplaneMode(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public void setActiveAdmin(ComponentName adminReceiver) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeTypedObject(adminReceiver, 0);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public void removeActiveAdmin(ComponentName adminReceiver) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeTypedObject(adminReceiver, 0);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public void setRuntimePermission(String packageName, String permission, UserHandle userhandle)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeString(permission);
          _data.writeTypedObject(userhandle, 0);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public void revokeRuntimePermission(
          String packageName, String permission, UserHandle userhandle) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeString(permission);
          _data.writeTypedObject(userhandle, 0);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public void setRemoteLockToLockscreen(
          int type,
          boolean state,
          String msg,
          String number,
          String email,
          boolean emergencycallbutton,
          String name,
          int failcount,
          long timeout,
          int blockcount,
          boolean skippin,
          Bundle bundle)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeBoolean(state);
          try {
            _data.writeString(msg);
            try {
              _data.writeString(number);
              try {
                _data.writeString(email);
                try {
                  _data.writeBoolean(emergencycallbutton);
                } catch (Throwable th) {
                  th = th;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th2) {
                th = th2;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th3) {
              th = th3;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th4) {
            th = th4;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th5) {
          th = th5;
        }
        try {
          _data.writeString(name);
          try {
            _data.writeInt(failcount);
            try {
              _data.writeLong(timeout);
              try {
                _data.writeInt(blockcount);
                try {
                  _data.writeBoolean(skippin);
                  try {
                    _data.writeTypedObject(bundle, 0);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    _reply.recycle();
                    _data.recycle();
                  } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                  }
                } catch (Throwable th7) {
                  th = th7;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th8) {
                th = th8;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th9) {
              th = th9;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th10) {
            th = th10;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th11) {
          th = th11;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public void setRemoteLockToLockscreenWithSkipSupport(
          int type,
          boolean state,
          String msg,
          String number,
          String email,
          boolean emergencycallbutton,
          String name,
          int failcount,
          long timeout,
          int blockcount,
          boolean skippin,
          Bundle bundle,
          boolean skipSupportContainer)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeBoolean(state);
          try {
            _data.writeString(msg);
            try {
              _data.writeString(number);
              try {
                _data.writeString(email);
                try {
                  _data.writeBoolean(emergencycallbutton);
                  try {
                    _data.writeString(name);
                    try {
                      _data.writeInt(failcount);
                    } catch (Throwable th) {
                      th = th;
                      _reply.recycle();
                      _data.recycle();
                      throw th;
                    }
                  } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                  }
                } catch (Throwable th3) {
                  th = th3;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th4) {
                th = th4;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th5) {
              th = th5;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th6) {
            th = th6;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th7) {
          th = th7;
        }
        try {
          _data.writeLong(timeout);
          try {
            _data.writeInt(blockcount);
            try {
              _data.writeBoolean(skippin);
              try {
                _data.writeTypedObject(bundle, 0);
                _data.writeBoolean(skipSupportContainer);
                this.mRemote.transact(16, _data, _reply, 0);
                _reply.readException();
                _reply.recycle();
                _data.recycle();
              } catch (Throwable th8) {
                th = th8;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th9) {
              th = th9;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th10) {
            th = th10;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th11) {
          th = th11;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public boolean isSkipSupportContainerSupported() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public String getPBAUniqueNumber() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public boolean showInstallmentStatus() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public boolean shouldBlockCustomRom() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public void setKnoxGuardExemptRule(boolean add) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeBoolean(add);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public void bindToLockScreen() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int getKGServiceVersion() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public void unRegisterIntent() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int verifyHOTPsecret(String s) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(s);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int verifyHOTPPin(String pin) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(pin);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int getTAState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int getTAStateSetError(boolean setError) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeBoolean(setError);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public String getKGPolicy() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int verifyHOTPDHChallenge(String hub, String sign, String challenge)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(hub);
          _data.writeString(sign);
          _data.writeString(challenge);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int verifyCompleteToken(String serverResponse) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(serverResponse);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public String generateHotpDHRequest() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public String getHotpChallenge() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public String verifyRegistrationInfo(String regInfo, String regInfoSig)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(regInfo);
          _data.writeString(regInfoSig);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public String verifyPolicy(String policy, String signature) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(policy);
          _data.writeString(signature);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int unlockScreen() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int lockScreen(
          String actionName,
          String clientName,
          String phoneNumber,
          String emailAddress,
          String message,
          boolean skipPin,
          boolean skipSupport,
          Bundle bundle)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(actionName);
          _data.writeString(clientName);
          _data.writeString(phoneNumber);
          _data.writeString(emailAddress);
          _data.writeString(message);
          _data.writeBoolean(skipPin);
          _data.writeBoolean(skipSupport);
          _data.writeTypedObject(bundle, 0);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public String getLockAction() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public String getClientData() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(39, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int setClientData(String clientData) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(clientData);
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public String getKGID() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int resetRPMB() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int resetRPMB2(String optional) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(optional);
          this.mRemote.transact(43, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int setCheckingState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public String verifyKgRot() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(45, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public String getStringSystemProperty(String key, String def) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(key);
          _data.writeString(def);
          this.mRemote.transact(46, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int getTAError() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(47, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public String getNonce(String serverMsg, String serverToken) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(serverMsg);
          _data.writeString(serverToken);
          this.mRemote.transact(48, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public String getTAInfo(int infoFlag) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeInt(infoFlag);
          this.mRemote.transact(49, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public int provisionCert(String enrollCert, String hotpCert, String policyCert, String blCert)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          _data.writeString(enrollCert);
          _data.writeString(hotpCert);
          _data.writeString(policyCert);
          _data.writeString(blCert);
          this.mRemote.transact(50, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public boolean isVpnExceptionRequired() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(51, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public void setClientHealthOK() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(52, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knoxguard.IKnoxGuardManager
      public Bundle getKGServiceInfo() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
          this.mRemote.transact(53, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 52;
    }
  }
}
