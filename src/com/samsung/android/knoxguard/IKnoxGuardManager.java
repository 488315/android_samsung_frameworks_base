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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKnoxGuardManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKnoxGuardManager)) {
                return (IKnoxGuardManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    registerIntent(string, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAirplaneMode(z);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    String string5 = parcel.readString();
                    int i4 = parcel.readInt();
                    long j = parcel.readLong();
                    int i5 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRemoteLockToLockscreen(i3, z2, string2, string3, string4, z3, string5, i4, j, i5, z4, bundle);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    boolean z6 = parcel.readBoolean();
                    String string9 = parcel.readString();
                    int i7 = parcel.readInt();
                    long j2 = parcel.readLong();
                    int i8 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRemoteLockToLockscreenWithSkipSupport(i6, z5, string6, string7, string8, z6, string9, i7, j2, i8, z7, bundle2, z8);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean zIsSkipSupportContainerSupported = isSkipSupportContainerSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSkipSupportContainerSupported);
                    break;
                case 6:
                    String pBAUniqueNumber = getPBAUniqueNumber();
                    parcel2.writeNoException();
                    parcel2.writeString(pBAUniqueNumber);
                    break;
                case 7:
                    boolean zShowInstallmentStatus = showInstallmentStatus();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShowInstallmentStatus);
                    break;
                case 8:
                    boolean zShouldBlockCustomRom = shouldBlockCustomRom();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldBlockCustomRom);
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
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iVerifyHOTPPin = verifyHOTPPin(string10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iVerifyHOTPPin);
                    break;
                case 14:
                    int tAState = getTAState();
                    parcel2.writeNoException();
                    parcel2.writeInt(tAState);
                    break;
                case 15:
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int tAStateSetError = getTAStateSetError(z9);
                    parcel2.writeNoException();
                    parcel2.writeInt(tAStateSetError);
                    break;
                case 16:
                    String kGPolicy = getKGPolicy();
                    parcel2.writeNoException();
                    parcel2.writeString(kGPolicy);
                    break;
                case 17:
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iVerifyHOTPDHChallenge = verifyHOTPDHChallenge(string11, string12, string13);
                    parcel2.writeNoException();
                    parcel2.writeInt(iVerifyHOTPDHChallenge);
                    break;
                case 18:
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iVerifyCompleteToken = verifyCompleteToken(string14);
                    parcel2.writeNoException();
                    parcel2.writeInt(iVerifyCompleteToken);
                    break;
                case 19:
                    String strGenerateHotpDHRequest = generateHotpDHRequest();
                    parcel2.writeNoException();
                    parcel2.writeString(strGenerateHotpDHRequest);
                    break;
                case 20:
                    String hotpChallenge = getHotpChallenge();
                    parcel2.writeNoException();
                    parcel2.writeString(hotpChallenge);
                    break;
                case 21:
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strVerifyRegistrationInfo = verifyRegistrationInfo(string15, string16);
                    parcel2.writeNoException();
                    parcel2.writeString(strVerifyRegistrationInfo);
                    break;
                case 22:
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strVerifyPolicy = verifyPolicy(string17, string18);
                    parcel2.writeNoException();
                    parcel2.writeString(strVerifyPolicy);
                    break;
                case 23:
                    int iUnlockScreen = unlockScreen();
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnlockScreen);
                    break;
                case 24:
                    String string19 = parcel.readString();
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    String string23 = parcel.readString();
                    boolean z10 = parcel.readBoolean();
                    boolean z11 = parcel.readBoolean();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iLockScreen = lockScreen(string19, string20, string21, string22, string23, z10, z11, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iLockScreen);
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
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int clientData2 = setClientData(string24);
                    parcel2.writeNoException();
                    parcel2.writeInt(clientData2);
                    break;
                case 28:
                    String kgid = getKGID();
                    parcel2.writeNoException();
                    parcel2.writeString(kgid);
                    break;
                case 29:
                    int iResetRPMB = resetRPMB();
                    parcel2.writeNoException();
                    parcel2.writeInt(iResetRPMB);
                    break;
                case 30:
                    int checkingState = setCheckingState();
                    parcel2.writeNoException();
                    parcel2.writeInt(checkingState);
                    break;
                case 31:
                    String strVerifyKgRot = verifyKgRot();
                    parcel2.writeNoException();
                    parcel2.writeString(strVerifyKgRot);
                    break;
                case 32:
                    String string25 = parcel.readString();
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String stringSystemProperty = getStringSystemProperty(string25, string26);
                    parcel2.writeNoException();
                    parcel2.writeString(stringSystemProperty);
                    break;
                case 33:
                    int tAError = getTAError();
                    parcel2.writeNoException();
                    parcel2.writeInt(tAError);
                    break;
                case 34:
                    String string27 = parcel.readString();
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String nonce = getNonce(string27, string28);
                    parcel2.writeNoException();
                    parcel2.writeString(nonce);
                    break;
                case 35:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String tAInfo = getTAInfo(i9);
                    parcel2.writeNoException();
                    parcel2.writeString(tAInfo);
                    break;
                case 36:
                    String string29 = parcel.readString();
                    String string30 = parcel.readString();
                    String string31 = parcel.readString();
                    String string32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iProvisionCert = provisionCert(string29, string30, string31, string32);
                    parcel2.writeNoException();
                    parcel2.writeInt(iProvisionCert);
                    break;
                case 37:
                    boolean zIsVpnExceptionRequired = isVpnExceptionRequired();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVpnExceptionRequired);
                    break;
                case 38:
                    Bundle kGServiceInfo = getKGServiceInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(kGServiceInfo, 1);
                    break;
                case 39:
                    String string33 = parcel.readString();
                    String string34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iVerifySfPolicy = verifySfPolicy(string33, string34);
                    parcel2.writeNoException();
                    parcel2.writeInt(iVerifySfPolicy);
                    break;
                case 40:
                    String sfPolicy = getSfPolicy();
                    parcel2.writeNoException();
                    parcel2.writeString(sfPolicy);
                    break;
                case 41:
                    boolean zIsKGAllowDO = isKGAllowDO();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKGAllowDO);
                    break;
                case 42:
                    boolean zIsKGAllowADB = isKGAllowADB();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKGAllowADB);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public void setAirplaneMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public void setRemoteLockToLockscreen(int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public void setRemoteLockToLockscreenWithSkipSupport(int i, boolean z, String str, String str2, String str3, boolean z2, String str4, int i2, long j, int i3, boolean z3, Bundle bundle, boolean z4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeBoolean(z4);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public boolean isSkipSupportContainerSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getPBAUniqueNumber() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public boolean showInstallmentStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public boolean shouldBlockCustomRom() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public void bindToLockScreen() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int getKGServiceVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public void unRegisterIntent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getKGPolicyCompany() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int verifyHOTPPin(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int getTAState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int getTAStateSetError(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getKGPolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int verifyHOTPDHChallenge(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int verifyCompleteToken(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String generateHotpDHRequest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getHotpChallenge() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String verifyRegistrationInfo(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String verifyPolicy(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int unlockScreen() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int lockScreen(String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getLockAction() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getClientData() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int setClientData(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getKGID() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int resetRPMB() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int setCheckingState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String verifyKgRot() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getStringSystemProperty(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int getTAError() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getNonce(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getTAInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int provisionCert(String str, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public boolean isVpnExceptionRequired() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public Bundle getKGServiceInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int verifySfPolicy(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getSfPolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public boolean isKGAllowDO() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public boolean isKGAllowADB() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
