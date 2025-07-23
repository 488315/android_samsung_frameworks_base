package com.samsung.android.knoxguard;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface IKnoxGuardManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knoxguard.IKnoxGuardManager";

    public static class Default implements IKnoxGuardManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public void bindToLockScreen() throws RemoteException {
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String generateHotpDHRequest() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String getClientData() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String getHotpChallenge() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String getKGID() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String getKGPolicy() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String getKGPolicyCompany() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public Bundle getKGServiceInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int getKGServiceVersion() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String getLockAction() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String getNonce(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String getPBAUniqueNumber() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String getSfPolicy() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String getStringSystemProperty(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int getTAError() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String getTAInfo(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int getTAState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int getTAStateSetError(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public boolean isKGAllowADB() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public boolean isKGAllowDO() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public boolean isSkipSupportContainerSupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public boolean isVpnExceptionRequired() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int lockScreen(String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int provisionCert(String str, String str2, String str3, String str4) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public void registerIntent(String str, List<String> list) throws RemoteException {
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int resetRPMB() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public void setAirplaneMode(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int setCheckingState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int setClientData(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public void setRemoteLockToLockscreen(int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public void setRemoteLockToLockscreenWithSkipSupport(int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle, boolean z4) throws RemoteException {
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public boolean shouldBlockCustomRom() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public boolean showInstallmentStatus() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public void unRegisterIntent() throws RemoteException {
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int unlockScreen() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int verifyCompleteToken(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int verifyHOTPDHChallenge(String str, String str2, String str3) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int verifyHOTPPin(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String verifyKgRot() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String verifyPolicy(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String verifyRegistrationInfo(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int verifySfPolicy(String str, String str2) throws RemoteException {
            return 0;
        }
    }

    void bindToLockScreen() throws RemoteException;

    String generateHotpDHRequest() throws RemoteException;

    String getClientData() throws RemoteException;

    String getHotpChallenge() throws RemoteException;

    String getKGID() throws RemoteException;

    String getKGPolicy() throws RemoteException;

    String getKGPolicyCompany() throws RemoteException;

    Bundle getKGServiceInfo() throws RemoteException;

    int getKGServiceVersion() throws RemoteException;

    String getLockAction() throws RemoteException;

    String getNonce(String str, String str2) throws RemoteException;

    String getPBAUniqueNumber() throws RemoteException;

    String getSfPolicy() throws RemoteException;

    String getStringSystemProperty(String str, String str2) throws RemoteException;

    int getTAError() throws RemoteException;

    String getTAInfo(int i) throws RemoteException;

    int getTAState() throws RemoteException;

    int getTAStateSetError(boolean z) throws RemoteException;

    boolean isKGAllowADB() throws RemoteException;

    boolean isKGAllowDO() throws RemoteException;

    boolean isSkipSupportContainerSupported() throws RemoteException;

    boolean isVpnExceptionRequired() throws RemoteException;

    int lockScreen(String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, Bundle bundle) throws RemoteException;

    int provisionCert(String str, String str2, String str3, String str4) throws RemoteException;

    void registerIntent(String str, List<String> list) throws RemoteException;

    int resetRPMB() throws RemoteException;

    void setAirplaneMode(boolean z) throws RemoteException;

    int setCheckingState() throws RemoteException;

    int setClientData(String str) throws RemoteException;

    void setRemoteLockToLockscreen(int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle) throws RemoteException;

    void setRemoteLockToLockscreenWithSkipSupport(int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle, boolean z4) throws RemoteException;

    boolean shouldBlockCustomRom() throws RemoteException;

    boolean showInstallmentStatus() throws RemoteException;

    void unRegisterIntent() throws RemoteException;

    int unlockScreen() throws RemoteException;

    int verifyCompleteToken(String str) throws RemoteException;

    int verifyHOTPDHChallenge(String str, String str2, String str3) throws RemoteException;

    int verifyHOTPPin(String str) throws RemoteException;

    String verifyKgRot() throws RemoteException;

    String verifyPolicy(String str, String str2) throws RemoteException;

    String verifyRegistrationInfo(String str, String str2) throws RemoteException;

    int verifySfPolicy(String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IKnoxGuardManager {
        static final int TRANSACTION_bindToLockScreen = 9;
        static final int TRANSACTION_generateHotpDHRequest = 19;
        static final int TRANSACTION_getClientData = 26;
        static final int TRANSACTION_getHotpChallenge = 20;
        static final int TRANSACTION_getKGID = 28;
        static final int TRANSACTION_getKGPolicy = 16;
        static final int TRANSACTION_getKGPolicyCompany = 12;
        static final int TRANSACTION_getKGServiceInfo = 38;
        static final int TRANSACTION_getKGServiceVersion = 10;
        static final int TRANSACTION_getLockAction = 25;
        static final int TRANSACTION_getNonce = 34;
        static final int TRANSACTION_getPBAUniqueNumber = 6;
        static final int TRANSACTION_getSfPolicy = 40;
        static final int TRANSACTION_getStringSystemProperty = 32;
        static final int TRANSACTION_getTAError = 33;
        static final int TRANSACTION_getTAInfo = 35;
        static final int TRANSACTION_getTAState = 14;
        static final int TRANSACTION_getTAStateSetError = 15;
        static final int TRANSACTION_isKGAllowADB = 42;
        static final int TRANSACTION_isKGAllowDO = 41;
        static final int TRANSACTION_isSkipSupportContainerSupported = 5;
        static final int TRANSACTION_isVpnExceptionRequired = 37;
        static final int TRANSACTION_lockScreen = 24;
        static final int TRANSACTION_provisionCert = 36;
        static final int TRANSACTION_registerIntent = 1;
        static final int TRANSACTION_resetRPMB = 29;
        static final int TRANSACTION_setAirplaneMode = 2;
        static final int TRANSACTION_setCheckingState = 30;
        static final int TRANSACTION_setClientData = 27;
        static final int TRANSACTION_setRemoteLockToLockscreen = 3;
        static final int TRANSACTION_setRemoteLockToLockscreenWithSkipSupport = 4;
        static final int TRANSACTION_shouldBlockCustomRom = 8;
        static final int TRANSACTION_showInstallmentStatus = 7;
        static final int TRANSACTION_unRegisterIntent = 11;
        static final int TRANSACTION_unlockScreen = 23;
        static final int TRANSACTION_verifyCompleteToken = 18;
        static final int TRANSACTION_verifyHOTPDHChallenge = 17;
        static final int TRANSACTION_verifyHOTPPin = 13;
        static final int TRANSACTION_verifyKgRot = 31;
        static final int TRANSACTION_verifyPolicy = 22;
        static final int TRANSACTION_verifyRegistrationInfo = 21;
        static final int TRANSACTION_verifySfPolicy = 39;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 41;
        }

        public Stub() {
            attachInterface(this, IKnoxGuardManager.DESCRIPTOR);
        }

        public static IKnoxGuardManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKnoxGuardManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IKnoxGuardManager)) {
                return (IKnoxGuardManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerIntent";
                case 2:
                    return "setAirplaneMode";
                case 3:
                    return "setRemoteLockToLockscreen";
                case 4:
                    return "setRemoteLockToLockscreenWithSkipSupport";
                case 5:
                    return "isSkipSupportContainerSupported";
                case 6:
                    return "getPBAUniqueNumber";
                case 7:
                    return "showInstallmentStatus";
                case 8:
                    return "shouldBlockCustomRom";
                case 9:
                    return "bindToLockScreen";
                case 10:
                    return "getKGServiceVersion";
                case 11:
                    return "unRegisterIntent";
                case 12:
                    return "getKGPolicyCompany";
                case 13:
                    return "verifyHOTPPin";
                case 14:
                    return "getTAState";
                case 15:
                    return "getTAStateSetError";
                case 16:
                    return "getKGPolicy";
                case 17:
                    return "verifyHOTPDHChallenge";
                case 18:
                    return "verifyCompleteToken";
                case 19:
                    return "generateHotpDHRequest";
                case 20:
                    return "getHotpChallenge";
                case 21:
                    return "verifyRegistrationInfo";
                case 22:
                    return "verifyPolicy";
                case 23:
                    return "unlockScreen";
                case 24:
                    return "lockScreen";
                case 25:
                    return "getLockAction";
                case 26:
                    return "getClientData";
                case 27:
                    return "setClientData";
                case 28:
                    return "getKGID";
                case 29:
                    return "resetRPMB";
                case 30:
                    return "setCheckingState";
                case 31:
                    return "verifyKgRot";
                case 32:
                    return "getStringSystemProperty";
                case 33:
                    return "getTAError";
                case 34:
                    return "getNonce";
                case 35:
                    return "getTAInfo";
                case 36:
                    return "provisionCert";
                case 37:
                    return "isVpnExceptionRequired";
                case 38:
                    return "getKGServiceInfo";
                case 39:
                    return "verifySfPolicy";
                case 40:
                    return "getSfPolicy";
                case 41:
                    return "isKGAllowDO";
                case 42:
                    return "isKGAllowADB";
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
                parcel.enforceInterface(IKnoxGuardManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxGuardManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    registerIntent(readString, createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAirplaneMode(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    String readString5 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    long readLong = parcel.readLong();
                    int readInt3 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRemoteLockToLockscreen(readInt, readBoolean2, readString2, readString3, readString4, readBoolean3, readString5, readInt2, readLong, readInt3, readBoolean4, bundle);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    String readString9 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRemoteLockToLockscreenWithSkipSupport(readInt4, readBoolean5, readString6, readString7, readString8, readBoolean6, readString9, readInt5, readLong2, readInt6, readBoolean7, bundle2, readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean isSkipSupportContainerSupported = isSkipSupportContainerSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSkipSupportContainerSupported);
                    break;
                case 6:
                    String pBAUniqueNumber = getPBAUniqueNumber();
                    parcel2.writeNoException();
                    parcel2.writeString(pBAUniqueNumber);
                    break;
                case 7:
                    boolean showInstallmentStatus = showInstallmentStatus();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(showInstallmentStatus);
                    break;
                case 8:
                    boolean shouldBlockCustomRom = shouldBlockCustomRom();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldBlockCustomRom);
                    break;
                case 9:
                    bindToLockScreen();
                    parcel2.writeNoException();
                    break;
                case 10:
                    int kGServiceVersion = getKGServiceVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(kGServiceVersion);
                    break;
                case 11:
                    unRegisterIntent();
                    parcel2.writeNoException();
                    break;
                case 12:
                    String kGPolicyCompany = getKGPolicyCompany();
                    parcel2.writeNoException();
                    parcel2.writeString(kGPolicyCompany);
                    break;
                case 13:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int verifyHOTPPin = verifyHOTPPin(readString10);
                    parcel2.writeNoException();
                    parcel2.writeInt(verifyHOTPPin);
                    break;
                case 14:
                    int tAState = getTAState();
                    parcel2.writeNoException();
                    parcel2.writeInt(tAState);
                    break;
                case 15:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int tAStateSetError = getTAStateSetError(readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeInt(tAStateSetError);
                    break;
                case 16:
                    String kGPolicy = getKGPolicy();
                    parcel2.writeNoException();
                    parcel2.writeString(kGPolicy);
                    break;
                case 17:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int verifyHOTPDHChallenge = verifyHOTPDHChallenge(readString11, readString12, readString13);
                    parcel2.writeNoException();
                    parcel2.writeInt(verifyHOTPDHChallenge);
                    break;
                case 18:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int verifyCompleteToken = verifyCompleteToken(readString14);
                    parcel2.writeNoException();
                    parcel2.writeInt(verifyCompleteToken);
                    break;
                case 19:
                    String generateHotpDHRequest = generateHotpDHRequest();
                    parcel2.writeNoException();
                    parcel2.writeString(generateHotpDHRequest);
                    break;
                case 20:
                    String hotpChallenge = getHotpChallenge();
                    parcel2.writeNoException();
                    parcel2.writeString(hotpChallenge);
                    break;
                case 21:
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String verifyRegistrationInfo = verifyRegistrationInfo(readString15, readString16);
                    parcel2.writeNoException();
                    parcel2.writeString(verifyRegistrationInfo);
                    break;
                case 22:
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String verifyPolicy = verifyPolicy(readString17, readString18);
                    parcel2.writeNoException();
                    parcel2.writeString(verifyPolicy);
                    break;
                case 23:
                    int unlockScreen = unlockScreen();
                    parcel2.writeNoException();
                    parcel2.writeInt(unlockScreen);
                    break;
                case 24:
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    String readString23 = parcel.readString();
                    boolean readBoolean10 = parcel.readBoolean();
                    boolean readBoolean11 = parcel.readBoolean();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int lockScreen = lockScreen(readString19, readString20, readString21, readString22, readString23, readBoolean10, readBoolean11, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockScreen);
                    break;
                case 25:
                    String lockAction = getLockAction();
                    parcel2.writeNoException();
                    parcel2.writeString(lockAction);
                    break;
                case 26:
                    String clientData = getClientData();
                    parcel2.writeNoException();
                    parcel2.writeString(clientData);
                    break;
                case 27:
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int clientData2 = setClientData(readString24);
                    parcel2.writeNoException();
                    parcel2.writeInt(clientData2);
                    break;
                case 28:
                    String kgid = getKGID();
                    parcel2.writeNoException();
                    parcel2.writeString(kgid);
                    break;
                case 29:
                    int resetRPMB = resetRPMB();
                    parcel2.writeNoException();
                    parcel2.writeInt(resetRPMB);
                    break;
                case 30:
                    int checkingState = setCheckingState();
                    parcel2.writeNoException();
                    parcel2.writeInt(checkingState);
                    break;
                case 31:
                    String verifyKgRot = verifyKgRot();
                    parcel2.writeNoException();
                    parcel2.writeString(verifyKgRot);
                    break;
                case 32:
                    String readString25 = parcel.readString();
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String stringSystemProperty = getStringSystemProperty(readString25, readString26);
                    parcel2.writeNoException();
                    parcel2.writeString(stringSystemProperty);
                    break;
                case 33:
                    int tAError = getTAError();
                    parcel2.writeNoException();
                    parcel2.writeInt(tAError);
                    break;
                case 34:
                    String readString27 = parcel.readString();
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String nonce = getNonce(readString27, readString28);
                    parcel2.writeNoException();
                    parcel2.writeString(nonce);
                    break;
                case 35:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String tAInfo = getTAInfo(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeString(tAInfo);
                    break;
                case 36:
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    String readString31 = parcel.readString();
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int provisionCert = provisionCert(readString29, readString30, readString31, readString32);
                    parcel2.writeNoException();
                    parcel2.writeInt(provisionCert);
                    break;
                case 37:
                    boolean isVpnExceptionRequired = isVpnExceptionRequired();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVpnExceptionRequired);
                    break;
                case 38:
                    Bundle kGServiceInfo = getKGServiceInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(kGServiceInfo, 1);
                    break;
                case 39:
                    String readString33 = parcel.readString();
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int verifySfPolicy = verifySfPolicy(readString33, readString34);
                    parcel2.writeNoException();
                    parcel2.writeInt(verifySfPolicy);
                    break;
                case 40:
                    String sfPolicy = getSfPolicy();
                    parcel2.writeNoException();
                    parcel2.writeString(sfPolicy);
                    break;
                case 41:
                    boolean isKGAllowDO = isKGAllowDO();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKGAllowDO);
                    break;
                case 42:
                    boolean isKGAllowADB = isKGAllowADB();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKGAllowADB);
                    break;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IKnoxGuardManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKnoxGuardManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public void registerIntent(String str, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public void setAirplaneMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public void setRemoteLockToLockscreen(int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str4);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z3);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public void setRemoteLockToLockscreenWithSkipSupport(int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle, boolean z4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str4);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z3);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z4);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public boolean isSkipSupportContainerSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getPBAUniqueNumber() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public boolean showInstallmentStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public boolean shouldBlockCustomRom() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public void bindToLockScreen() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int getKGServiceVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public void unRegisterIntent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getKGPolicyCompany() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int verifyHOTPPin(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int getTAState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int getTAStateSetError(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getKGPolicy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int verifyHOTPDHChallenge(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int verifyCompleteToken(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String generateHotpDHRequest() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getHotpChallenge() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String verifyRegistrationInfo(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String verifyPolicy(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int unlockScreen() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int lockScreen(String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getLockAction() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getClientData() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int setClientData(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getKGID() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int resetRPMB() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int setCheckingState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String verifyKgRot() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getStringSystemProperty(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int getTAError() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getNonce(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getTAInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int provisionCert(String str, String str2, String str3, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public boolean isVpnExceptionRequired() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public Bundle getKGServiceInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int verifySfPolicy(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getSfPolicy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public boolean isKGAllowDO() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public boolean isKGAllowADB() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
