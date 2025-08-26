package com.samsung.android.knox.dar;

import android.hardware.scontext.SContextConstants;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.dar.sdp.ISdpListener;
import com.samsung.android.knox.sdp.core.SdpCreationParam;
import com.samsung.android.knox.sdp.core.SdpEngineInfo;
import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;
import java.util.List;

/* loaded from: classes6.dex */
public interface IDarManagerService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.dar.IDarManagerService";

    public static class Default implements IDarManagerService {
        @Override // com.samsung.android.knox.dar.IDarManagerService
        public void addBlockedClearablePackages(int i, String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int addEngine(SdpCreationParam sdpCreationParam, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int allow(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean clearResetPasswordToken(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int createEncPkgDir(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int deleteToeknFromTrusted(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int disallow(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int exists(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int getAvailableUserId() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public List<String> getBlockedClearablePackages(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public SdpEngineInfo getEngineInfo(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int getInnerAuthUserId(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int getMainUserId(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public List<String> getPackageListForDualDarPolicy(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int getPasswordMinimumLengthForInner() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int getReservedUserIdForSystem() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public double getSupportedSDKVersion() throws RemoteException {
            return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean isDarSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean isDefaultPathUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean isDeviceRootKeyInstalled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean isInnerAuthRequired(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean isKnoxKeyInstallable() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int isLicensed() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean isResetPasswordTokenActive(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean isSDPEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean isSdpSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean isSdpSupportedSecureFolder(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean isSensitive(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int lock(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int migrate(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public void onBiometricsAuthenticated(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public void onDeviceOwnerLocked(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public void registerClient(int i, ISdpListener iSdpListener) throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int registerListener(String str, ISdpListener iSdpListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int removeEngine(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public void reportApplicationBinding(long j, int i, int i2, String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int reserveUserIdForSystem() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int resetPassword(String str, String str2, String str3) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean resetPasswordWithToken(String str, byte[] bArr, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int saveTokenIntoTrusted(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean setDualDarInfo(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public void setInnerAuthUserId(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public void setMainUserId(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int setPassword(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean setResetPasswordToken(byte[] bArr, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public boolean setSensitive(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int startMonitoring(int i, int i2, Bundle bundle, IEndpointMonitorListener iEndpointMonitorListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int startTracing(int i, int i2, Bundle bundle, IEndpointMonitorListener iEndpointMonitorListener) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int stopMonitoring(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int stopTracing(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public void systemReady() throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int unlock(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int unlockViaTrusted(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public void unregisterClient(int i, ISdpListener iSdpListener) throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.IDarManagerService
        public int unregisterListener(String str, ISdpListener iSdpListener) throws RemoteException {
            return 0;
        }
    }

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

    void reportApplicationBinding(long j, int i, int i2, String str, String str2) throws RemoteException;

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

    int startMonitoring(int i, int i2, Bundle bundle, IEndpointMonitorListener iEndpointMonitorListener) throws RemoteException;

    int startTracing(int i, int i2, Bundle bundle, IEndpointMonitorListener iEndpointMonitorListener) throws RemoteException;

    int stopMonitoring(int i, int i2) throws RemoteException;

    int stopTracing(int i, int i2) throws RemoteException;

    void systemReady() throws RemoteException;

    int unlock(String str, String str2) throws RemoteException;

    int unlockViaTrusted(String str, String str2) throws RemoteException;

    void unregisterClient(int i, ISdpListener iSdpListener) throws RemoteException;

    int unregisterListener(String str, ISdpListener iSdpListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IDarManagerService {
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
        static final int TRANSACTION_reportApplicationBinding = 55;
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
        static final int TRANSACTION_startMonitoring = 53;
        static final int TRANSACTION_startTracing = 51;
        static final int TRANSACTION_stopMonitoring = 54;
        static final int TRANSACTION_stopTracing = 52;
        static final int TRANSACTION_systemReady = 2;
        static final int TRANSACTION_unlock = 15;
        static final int TRANSACTION_unlockViaTrusted = 35;
        static final int TRANSACTION_unregisterClient = 39;
        static final int TRANSACTION_unregisterListener = 21;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 54;
        }

        public Stub() {
            attachInterface(this, IDarManagerService.DESCRIPTOR);
        }

        public static IDarManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDarManagerService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDarManagerService)) {
                return (IDarManagerService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "startMonitoring";
                case 54:
                    return "stopMonitoring";
                case 55:
                    return "reportApplicationBinding";
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
                parcel.enforceInterface(IDarManagerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDarManagerService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean zIsDarSupported = isDarSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDarSupported);
                    return true;
                case 2:
                    systemReady();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean zIsDeviceRootKeyInstalled = isDeviceRootKeyInstalled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceRootKeyInstalled);
                    return true;
                case 4:
                    boolean zIsKnoxKeyInstallable = isKnoxKeyInstallable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKnoxKeyInstallable);
                    return true;
                case 5:
                    int iReserveUserIdForSystem = reserveUserIdForSystem();
                    parcel2.writeNoException();
                    parcel2.writeInt(iReserveUserIdForSystem);
                    return true;
                case 6:
                    int reservedUserIdForSystem = getReservedUserIdForSystem();
                    parcel2.writeNoException();
                    parcel2.writeInt(reservedUserIdForSystem);
                    return true;
                case 7:
                    int availableUserId = getAvailableUserId();
                    parcel2.writeNoException();
                    parcel2.writeInt(availableUserId);
                    return true;
                case 8:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean resetPasswordToken = setResetPasswordToken(bArrCreateByteArray, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetPasswordToken);
                    return true;
                case 9:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zClearResetPasswordToken = clearResetPasswordToken(i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearResetPasswordToken);
                    return true;
                case 10:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsResetPasswordTokenActive = isResetPasswordTokenActive(i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsResetPasswordTokenActive);
                    return true;
                case 11:
                    String string = parcel.readString();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zResetPasswordWithToken = resetPasswordWithToken(string, bArrCreateByteArray2, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetPasswordWithToken);
                    return true;
                case 12:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSDPEnabled = isSDPEnabled(i7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSDPEnabled);
                    return true;
                case 13:
                    boolean zIsSdpSupported = isSdpSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSdpSupported);
                    return true;
                case 14:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSdpSupportedSecureFolder = isSdpSupportedSecureFolder(i8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSdpSupportedSecureFolder);
                    return true;
                case 15:
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iUnlock = unlock(string2, string3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnlock);
                    return true;
                case 16:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iLock = lock(string4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iLock);
                    return true;
                case 17:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int password = setPassword(string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeInt(password);
                    return true;
                case 18:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iResetPassword = resetPassword(string7, string8, string9);
                    parcel2.writeNoException();
                    parcel2.writeInt(iResetPassword);
                    return true;
                case 19:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iMigrate = migrate(string10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMigrate);
                    return true;
                case 20:
                    String string11 = parcel.readString();
                    ISdpListener iSdpListenerAsInterface = ISdpListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRegisterListener = registerListener(string11, iSdpListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterListener);
                    return true;
                case 21:
                    String string12 = parcel.readString();
                    ISdpListener iSdpListenerAsInterface2 = ISdpListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iUnregisterListener = unregisterListener(string12, iSdpListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnregisterListener);
                    return true;
                case 22:
                    int iIsLicensed = isLicensed();
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsLicensed);
                    return true;
                case 23:
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iExists = exists(string13);
                    parcel2.writeNoException();
                    parcel2.writeInt(iExists);
                    return true;
                case 24:
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iAllow = allow(string14, string15);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAllow);
                    return true;
                case 25:
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iDisallow = disallow(string16, string17);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDisallow);
                    return true;
                case 26:
                    double supportedSDKVersion = getSupportedSDKVersion();
                    parcel2.writeNoException();
                    parcel2.writeDouble(supportedSDKVersion);
                    return true;
                case 27:
                    SdpCreationParam sdpCreationParam = (SdpCreationParam) parcel.readTypedObject(SdpCreationParam.CREATOR);
                    String string18 = parcel.readString();
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iAddEngine = addEngine(sdpCreationParam, string18, string19);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddEngine);
                    return true;
                case 28:
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemoveEngine = removeEngine(string20);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveEngine);
                    return true;
                case 29:
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SdpEngineInfo engineInfo = getEngineInfo(string21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(engineInfo, 1);
                    return true;
                case 30:
                    int i9 = parcel.readInt();
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean sensitive = setSensitive(i9, string22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sensitive);
                    return true;
                case 31:
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsSensitive = isSensitive(string23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSensitive);
                    return true;
                case 32:
                    int i10 = parcel.readInt();
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCreateEncPkgDir = createEncPkgDir(i10, string24);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateEncPkgDir);
                    return true;
                case 33:
                    String string25 = parcel.readString();
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iSaveTokenIntoTrusted = saveTokenIntoTrusted(string25, string26);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSaveTokenIntoTrusted);
                    return true;
                case 34:
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iDeleteToeknFromTrusted = deleteToeknFromTrusted(string27);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDeleteToeknFromTrusted);
                    return true;
                case 35:
                    String string28 = parcel.readString();
                    String string29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iUnlockViaTrusted = unlockViaTrusted(string28, string29);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnlockViaTrusted);
                    return true;
                case 36:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiometricsAuthenticated(i11);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDeviceOwnerLocked(i12);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int i13 = parcel.readInt();
                    ISdpListener iSdpListenerAsInterface3 = ISdpListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerClient(i13, iSdpListenerAsInterface3);
                    return true;
                case 39:
                    int i14 = parcel.readInt();
                    ISdpListener iSdpListenerAsInterface4 = ISdpListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterClient(i14, iSdpListenerAsInterface4);
                    return true;
                case 40:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDefaultPathUser = isDefaultPathUser(i15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDefaultPathUser);
                    return true;
                case 41:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dualDarInfo = setDualDarInfo(i16, i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dualDarInfo);
                    return true;
                case 42:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsInnerAuthRequired = isInnerAuthRequired(i18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInnerAuthRequired);
                    return true;
                case 43:
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setInnerAuthUserId(i19, i20);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int innerAuthUserId = getInnerAuthUserId(i21);
                    parcel2.writeNoException();
                    parcel2.writeInt(innerAuthUserId);
                    return true;
                case 45:
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMainUserId(i22, i23);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int mainUserId = getMainUserId(i24);
                    parcel2.writeNoException();
                    parcel2.writeInt(mainUserId);
                    return true;
                case 47:
                    int i25 = parcel.readInt();
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addBlockedClearablePackages(i25, string30);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> blockedClearablePackages = getBlockedClearablePackages(i26);
                    parcel2.writeNoException();
                    parcel2.writeStringList(blockedClearablePackages);
                    return true;
                case 49:
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> packageListForDualDarPolicy = getPackageListForDualDarPolicy(string31);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packageListForDualDarPolicy);
                    return true;
                case 50:
                    int passwordMinimumLengthForInner = getPasswordMinimumLengthForInner();
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordMinimumLengthForInner);
                    return true;
                case 51:
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IEndpointMonitorListener iEndpointMonitorListenerAsInterface = IEndpointMonitorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStartTracing = startTracing(i27, i28, bundle, iEndpointMonitorListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartTracing);
                    return true;
                case 52:
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStopTracing = stopTracing(i29, i30);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopTracing);
                    return true;
                case 53:
                    int i31 = parcel.readInt();
                    int i32 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IEndpointMonitorListener iEndpointMonitorListenerAsInterface2 = IEndpointMonitorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStartMonitoring = startMonitoring(i31, i32, bundle2, iEndpointMonitorListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartMonitoring);
                    return true;
                case 54:
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStopMonitoring = stopMonitoring(i33, i34);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopMonitoring);
                    return true;
                case 55:
                    long j = parcel.readLong();
                    int i35 = parcel.readInt();
                    int i36 = parcel.readInt();
                    String string32 = parcel.readString();
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportApplicationBinding(j, i35, i36, string32, string33);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDarManagerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void systemReady() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isDeviceRootKeyInstalled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isKnoxKeyInstallable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int reserveUserIdForSystem() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int getReservedUserIdForSystem() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int getAvailableUserId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean setResetPasswordToken(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean clearResetPasswordToken(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isResetPasswordTokenActive(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean resetPasswordWithToken(String str, byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isSDPEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isSdpSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isSdpSupportedSecureFolder(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int unlock(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int lock(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int setPassword(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int resetPassword(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int migrate(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int registerListener(String str, ISdpListener iSdpListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iSdpListener);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int unregisterListener(String str, ISdpListener iSdpListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iSdpListener);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int isLicensed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int exists(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int allow(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int disallow(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public double getSupportedSDKVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readDouble();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int addEngine(SdpCreationParam sdpCreationParam, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(sdpCreationParam, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int removeEngine(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public SdpEngineInfo getEngineInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SdpEngineInfo) parcelObtain2.readTypedObject(SdpEngineInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean setSensitive(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isSensitive(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int createEncPkgDir(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int saveTokenIntoTrusted(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int deleteToeknFromTrusted(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int unlockViaTrusted(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void onBiometricsAuthenticated(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void onDeviceOwnerLocked(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void registerClient(int i, ISdpListener iSdpListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSdpListener);
                    this.mRemote.transact(38, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void unregisterClient(int i, ISdpListener iSdpListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSdpListener);
                    this.mRemote.transact(39, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isDefaultPathUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean setDualDarInfo(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isInnerAuthRequired(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void setInnerAuthUserId(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int getInnerAuthUserId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void setMainUserId(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int getMainUserId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void addBlockedClearablePackages(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public List<String> getBlockedClearablePackages(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public List<String> getPackageListForDualDarPolicy(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int getPasswordMinimumLengthForInner() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int startTracing(int i, int i2, Bundle bundle, IEndpointMonitorListener iEndpointMonitorListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iEndpointMonitorListener);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int stopTracing(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int startMonitoring(int i, int i2, Bundle bundle, IEndpointMonitorListener iEndpointMonitorListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iEndpointMonitorListener);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int stopMonitoring(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void reportApplicationBinding(long j, int i, int i2, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
