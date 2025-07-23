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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDarManagerService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDarManagerService)) {
                return (IDarManagerService) queryLocalInterface;
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
                    boolean isDarSupported = isDarSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDarSupported);
                    return true;
                case 2:
                    systemReady();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean isDeviceRootKeyInstalled = isDeviceRootKeyInstalled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceRootKeyInstalled);
                    return true;
                case 4:
                    boolean isKnoxKeyInstallable = isKnoxKeyInstallable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKnoxKeyInstallable);
                    return true;
                case 5:
                    int reserveUserIdForSystem = reserveUserIdForSystem();
                    parcel2.writeNoException();
                    parcel2.writeInt(reserveUserIdForSystem);
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
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean resetPasswordToken = setResetPasswordToken(createByteArray, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetPasswordToken);
                    return true;
                case 9:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean clearResetPasswordToken = clearResetPasswordToken(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearResetPasswordToken);
                    return true;
                case 10:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isResetPasswordTokenActive = isResetPasswordTokenActive(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isResetPasswordTokenActive);
                    return true;
                case 11:
                    String readString = parcel.readString();
                    byte[] createByteArray2 = parcel.createByteArray();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean resetPasswordWithToken = resetPasswordWithToken(readString, createByteArray2, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetPasswordWithToken);
                    return true;
                case 12:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSDPEnabled = isSDPEnabled(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSDPEnabled);
                    return true;
                case 13:
                    boolean isSdpSupported = isSdpSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSdpSupported);
                    return true;
                case 14:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSdpSupportedSecureFolder = isSdpSupportedSecureFolder(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSdpSupportedSecureFolder);
                    return true;
                case 15:
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int unlock = unlock(readString2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeInt(unlock);
                    return true;
                case 16:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int lock = lock(readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(lock);
                    return true;
                case 17:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int password = setPassword(readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeInt(password);
                    return true;
                case 18:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int resetPassword = resetPassword(readString7, readString8, readString9);
                    parcel2.writeNoException();
                    parcel2.writeInt(resetPassword);
                    return true;
                case 19:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int migrate = migrate(readString10);
                    parcel2.writeNoException();
                    parcel2.writeInt(migrate);
                    return true;
                case 20:
                    String readString11 = parcel.readString();
                    ISdpListener asInterface = ISdpListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int registerListener = registerListener(readString11, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(registerListener);
                    return true;
                case 21:
                    String readString12 = parcel.readString();
                    ISdpListener asInterface2 = ISdpListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int unregisterListener = unregisterListener(readString12, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterListener);
                    return true;
                case 22:
                    int isLicensed = isLicensed();
                    parcel2.writeNoException();
                    parcel2.writeInt(isLicensed);
                    return true;
                case 23:
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int exists = exists(readString13);
                    parcel2.writeNoException();
                    parcel2.writeInt(exists);
                    return true;
                case 24:
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int allow = allow(readString14, readString15);
                    parcel2.writeNoException();
                    parcel2.writeInt(allow);
                    return true;
                case 25:
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int disallow = disallow(readString16, readString17);
                    parcel2.writeNoException();
                    parcel2.writeInt(disallow);
                    return true;
                case 26:
                    double supportedSDKVersion = getSupportedSDKVersion();
                    parcel2.writeNoException();
                    parcel2.writeDouble(supportedSDKVersion);
                    return true;
                case 27:
                    SdpCreationParam sdpCreationParam = (SdpCreationParam) parcel.readTypedObject(SdpCreationParam.CREATOR);
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int addEngine = addEngine(sdpCreationParam, readString18, readString19);
                    parcel2.writeNoException();
                    parcel2.writeInt(addEngine);
                    return true;
                case 28:
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int removeEngine = removeEngine(readString20);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeEngine);
                    return true;
                case 29:
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SdpEngineInfo engineInfo = getEngineInfo(readString21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(engineInfo, 1);
                    return true;
                case 30:
                    int readInt7 = parcel.readInt();
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean sensitive = setSensitive(readInt7, readString22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sensitive);
                    return true;
                case 31:
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSensitive = isSensitive(readString23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSensitive);
                    return true;
                case 32:
                    int readInt8 = parcel.readInt();
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int createEncPkgDir = createEncPkgDir(readInt8, readString24);
                    parcel2.writeNoException();
                    parcel2.writeInt(createEncPkgDir);
                    return true;
                case 33:
                    String readString25 = parcel.readString();
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int saveTokenIntoTrusted = saveTokenIntoTrusted(readString25, readString26);
                    parcel2.writeNoException();
                    parcel2.writeInt(saveTokenIntoTrusted);
                    return true;
                case 34:
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int deleteToeknFromTrusted = deleteToeknFromTrusted(readString27);
                    parcel2.writeNoException();
                    parcel2.writeInt(deleteToeknFromTrusted);
                    return true;
                case 35:
                    String readString28 = parcel.readString();
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int unlockViaTrusted = unlockViaTrusted(readString28, readString29);
                    parcel2.writeNoException();
                    parcel2.writeInt(unlockViaTrusted);
                    return true;
                case 36:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiometricsAuthenticated(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDeviceOwnerLocked(readInt10);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt11 = parcel.readInt();
                    ISdpListener asInterface3 = ISdpListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerClient(readInt11, asInterface3);
                    return true;
                case 39:
                    int readInt12 = parcel.readInt();
                    ISdpListener asInterface4 = ISdpListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterClient(readInt12, asInterface4);
                    return true;
                case 40:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDefaultPathUser = isDefaultPathUser(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDefaultPathUser);
                    return true;
                case 41:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dualDarInfo = setDualDarInfo(readInt14, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dualDarInfo);
                    return true;
                case 42:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isInnerAuthRequired = isInnerAuthRequired(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInnerAuthRequired);
                    return true;
                case 43:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setInnerAuthUserId(readInt17, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int innerAuthUserId = getInnerAuthUserId(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeInt(innerAuthUserId);
                    return true;
                case 45:
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMainUserId(readInt20, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int mainUserId = getMainUserId(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeInt(mainUserId);
                    return true;
                case 47:
                    int readInt23 = parcel.readInt();
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addBlockedClearablePackages(readInt23, readString30);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> blockedClearablePackages = getBlockedClearablePackages(readInt24);
                    parcel2.writeNoException();
                    parcel2.writeStringList(blockedClearablePackages);
                    return true;
                case 49:
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> packageListForDualDarPolicy = getPackageListForDualDarPolicy(readString31);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packageListForDualDarPolicy);
                    return true;
                case 50:
                    int passwordMinimumLengthForInner = getPasswordMinimumLengthForInner();
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordMinimumLengthForInner);
                    return true;
                case 51:
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IEndpointMonitorListener asInterface5 = IEndpointMonitorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startTracing = startTracing(readInt25, readInt26, bundle, asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeInt(startTracing);
                    return true;
                case 52:
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int stopTracing = stopTracing(readInt27, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopTracing);
                    return true;
                case 53:
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IEndpointMonitorListener asInterface6 = IEndpointMonitorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startMonitoring = startMonitoring(readInt29, readInt30, bundle2, asInterface6);
                    parcel2.writeNoException();
                    parcel2.writeInt(startMonitoring);
                    return true;
                case 54:
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int stopMonitoring = stopMonitoring(readInt31, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopMonitoring);
                    return true;
                case 55:
                    long readLong = parcel.readLong();
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    String readString32 = parcel.readString();
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportApplicationBinding(readLong, readInt33, readInt34, readString32, readString33);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void systemReady() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isDeviceRootKeyInstalled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isKnoxKeyInstallable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int reserveUserIdForSystem() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int getReservedUserIdForSystem() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int getAvailableUserId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean setResetPasswordToken(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean clearResetPasswordToken(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isResetPasswordTokenActive(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean resetPasswordWithToken(String str, byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isSDPEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isSdpSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isSdpSupportedSecureFolder(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int unlock(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int lock(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int setPassword(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int resetPassword(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int migrate(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int registerListener(String str, ISdpListener iSdpListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSdpListener);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int unregisterListener(String str, ISdpListener iSdpListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSdpListener);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int isLicensed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int exists(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int allow(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int disallow(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public double getSupportedSDKVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readDouble();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int addEngine(SdpCreationParam sdpCreationParam, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(sdpCreationParam, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int removeEngine(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public SdpEngineInfo getEngineInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SdpEngineInfo) obtain2.readTypedObject(SdpEngineInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean setSensitive(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isSensitive(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int createEncPkgDir(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int saveTokenIntoTrusted(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int deleteToeknFromTrusted(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int unlockViaTrusted(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void onBiometricsAuthenticated(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void onDeviceOwnerLocked(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void registerClient(int i, ISdpListener iSdpListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSdpListener);
                    this.mRemote.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void unregisterClient(int i, ISdpListener iSdpListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSdpListener);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isDefaultPathUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean setDualDarInfo(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public boolean isInnerAuthRequired(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void setInnerAuthUserId(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int getInnerAuthUserId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void setMainUserId(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int getMainUserId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void addBlockedClearablePackages(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public List<String> getBlockedClearablePackages(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public List<String> getPackageListForDualDarPolicy(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int getPasswordMinimumLengthForInner() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int startTracing(int i, int i2, Bundle bundle, IEndpointMonitorListener iEndpointMonitorListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iEndpointMonitorListener);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int stopTracing(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int startMonitoring(int i, int i2, Bundle bundle, IEndpointMonitorListener iEndpointMonitorListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iEndpointMonitorListener);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public int stopMonitoring(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.IDarManagerService
            public void reportApplicationBinding(long j, int i, int i2, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDarManagerService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
