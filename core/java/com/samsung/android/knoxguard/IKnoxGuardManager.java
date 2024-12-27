package com.samsung.android.knoxguard;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface IKnoxGuardManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knoxguard.IKnoxGuardManager";

    void bindToLockScreen() throws RemoteException;

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

    int resetRPMB() throws RemoteException;

    void setAirplaneMode(boolean z) throws RemoteException;

    int setCheckingState() throws RemoteException;

    int setClientData(String str) throws RemoteException;

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

    public static class Default implements IKnoxGuardManager {
        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public void registerIntent(String preFix, List<String> actionList) throws RemoteException {}

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public void setAirplaneMode(boolean enabled) throws RemoteException {}

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
        public void bindToLockScreen() throws RemoteException {}

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int getKGServiceVersion() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public void unRegisterIntent() throws RemoteException {}

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
        public String verifyRegistrationInfo(String regInfo, String regInfoSig)
                throws RemoteException {
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
        public int provisionCert(
                String enrollCert, String hotpCert, String policyCert, String blCert)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public boolean isVpnExceptionRequired() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public Bundle getKGServiceInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public int verifySfPolicy(String sfPolicy, String signature) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public String getSfPolicy() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public boolean isKGAllowDO() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knoxguard.IKnoxGuardManager
        public boolean isKGAllowADB() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IKnoxGuardManager {
        static final int TRANSACTION_bindToLockScreen = 9;
        static final int TRANSACTION_generateHotpDHRequest = 18;
        static final int TRANSACTION_getClientData = 25;
        static final int TRANSACTION_getHotpChallenge = 19;
        static final int TRANSACTION_getKGID = 27;
        static final int TRANSACTION_getKGPolicy = 15;
        static final int TRANSACTION_getKGServiceInfo = 37;
        static final int TRANSACTION_getKGServiceVersion = 10;
        static final int TRANSACTION_getLockAction = 24;
        static final int TRANSACTION_getNonce = 33;
        static final int TRANSACTION_getPBAUniqueNumber = 6;
        static final int TRANSACTION_getSfPolicy = 39;
        static final int TRANSACTION_getStringSystemProperty = 31;
        static final int TRANSACTION_getTAError = 32;
        static final int TRANSACTION_getTAInfo = 34;
        static final int TRANSACTION_getTAState = 13;
        static final int TRANSACTION_getTAStateSetError = 14;
        static final int TRANSACTION_isKGAllowADB = 41;
        static final int TRANSACTION_isKGAllowDO = 40;
        static final int TRANSACTION_isSkipSupportContainerSupported = 5;
        static final int TRANSACTION_isVpnExceptionRequired = 36;
        static final int TRANSACTION_lockScreen = 23;
        static final int TRANSACTION_provisionCert = 35;
        static final int TRANSACTION_registerIntent = 1;
        static final int TRANSACTION_resetRPMB = 28;
        static final int TRANSACTION_setAirplaneMode = 2;
        static final int TRANSACTION_setCheckingState = 29;
        static final int TRANSACTION_setClientData = 26;
        static final int TRANSACTION_setRemoteLockToLockscreen = 3;
        static final int TRANSACTION_setRemoteLockToLockscreenWithSkipSupport = 4;
        static final int TRANSACTION_shouldBlockCustomRom = 8;
        static final int TRANSACTION_showInstallmentStatus = 7;
        static final int TRANSACTION_unRegisterIntent = 11;
        static final int TRANSACTION_unlockScreen = 22;
        static final int TRANSACTION_verifyCompleteToken = 17;
        static final int TRANSACTION_verifyHOTPDHChallenge = 16;
        static final int TRANSACTION_verifyHOTPPin = 12;
        static final int TRANSACTION_verifyKgRot = 30;
        static final int TRANSACTION_verifyPolicy = 21;
        static final int TRANSACTION_verifyRegistrationInfo = 20;
        static final int TRANSACTION_verifySfPolicy = 38;

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
                    return "verifyHOTPPin";
                case 13:
                    return "getTAState";
                case 14:
                    return "getTAStateSetError";
                case 15:
                    return "getKGPolicy";
                case 16:
                    return "verifyHOTPDHChallenge";
                case 17:
                    return "verifyCompleteToken";
                case 18:
                    return "generateHotpDHRequest";
                case 19:
                    return "getHotpChallenge";
                case 20:
                    return "verifyRegistrationInfo";
                case 21:
                    return "verifyPolicy";
                case 22:
                    return "unlockScreen";
                case 23:
                    return "lockScreen";
                case 24:
                    return "getLockAction";
                case 25:
                    return "getClientData";
                case 26:
                    return "setClientData";
                case 27:
                    return "getKGID";
                case 28:
                    return "resetRPMB";
                case 29:
                    return "setCheckingState";
                case 30:
                    return "verifyKgRot";
                case 31:
                    return "getStringSystemProperty";
                case 32:
                    return "getTAError";
                case 33:
                    return "getNonce";
                case 34:
                    return "getTAInfo";
                case 35:
                    return "provisionCert";
                case 36:
                    return "isVpnExceptionRequired";
                case 37:
                    return "getKGServiceInfo";
                case 38:
                    return "verifySfPolicy";
                case 39:
                    return "getSfPolicy";
                case 40:
                    return "isKGAllowDO";
                case 41:
                    return "isKGAllowADB";
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
            if (code == 1598968902) {
                reply.writeString(IKnoxGuardManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    List<String> _arg1 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    registerIntent(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 2:
                    boolean _arg02 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setAirplaneMode(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    boolean _arg12 = data.readBoolean();
                    String _arg2 = data.readString();
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
                            _arg03, _arg12, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9,
                            _arg10, _arg11);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    boolean _arg13 = data.readBoolean();
                    String _arg22 = data.readString();
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
                            _arg04, _arg13, _arg22, _arg32, _arg42, _arg52, _arg62, _arg72, _arg82,
                            _arg92, _arg102, _arg112, _arg122);
                    reply.writeNoException();
                    return true;
                case 5:
                    boolean _result = isSkipSupportContainerSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 6:
                    String _result2 = getPBAUniqueNumber();
                    reply.writeNoException();
                    reply.writeString(_result2);
                    return true;
                case 7:
                    boolean _result3 = showInstallmentStatus();
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 8:
                    boolean _result4 = shouldBlockCustomRom();
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 9:
                    bindToLockScreen();
                    reply.writeNoException();
                    return true;
                case 10:
                    int _result5 = getKGServiceVersion();
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 11:
                    unRegisterIntent();
                    reply.writeNoException();
                    return true;
                case 12:
                    String _arg05 = data.readString();
                    data.enforceNoDataAvail();
                    int _result6 = verifyHOTPPin(_arg05);
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 13:
                    int _result7 = getTAState();
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 14:
                    boolean _arg06 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result8 = getTAStateSetError(_arg06);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 15:
                    String _result9 = getKGPolicy();
                    reply.writeNoException();
                    reply.writeString(_result9);
                    return true;
                case 16:
                    String _arg07 = data.readString();
                    String _arg14 = data.readString();
                    String _arg23 = data.readString();
                    data.enforceNoDataAvail();
                    int _result10 = verifyHOTPDHChallenge(_arg07, _arg14, _arg23);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 17:
                    String _arg08 = data.readString();
                    data.enforceNoDataAvail();
                    int _result11 = verifyCompleteToken(_arg08);
                    reply.writeNoException();
                    reply.writeInt(_result11);
                    return true;
                case 18:
                    String _result12 = generateHotpDHRequest();
                    reply.writeNoException();
                    reply.writeString(_result12);
                    return true;
                case 19:
                    String _result13 = getHotpChallenge();
                    reply.writeNoException();
                    reply.writeString(_result13);
                    return true;
                case 20:
                    String _arg09 = data.readString();
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    String _result14 = verifyRegistrationInfo(_arg09, _arg15);
                    reply.writeNoException();
                    reply.writeString(_result14);
                    return true;
                case 21:
                    String _arg010 = data.readString();
                    String _arg16 = data.readString();
                    data.enforceNoDataAvail();
                    String _result15 = verifyPolicy(_arg010, _arg16);
                    reply.writeNoException();
                    reply.writeString(_result15);
                    return true;
                case 22:
                    int _result16 = unlockScreen();
                    reply.writeNoException();
                    reply.writeInt(_result16);
                    return true;
                case 23:
                    String _arg011 = data.readString();
                    String _arg17 = data.readString();
                    String _arg24 = data.readString();
                    String _arg33 = data.readString();
                    String _arg43 = data.readString();
                    boolean _arg53 = data.readBoolean();
                    boolean _arg63 = data.readBoolean();
                    Bundle _arg73 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    int _result17 =
                            lockScreen(
                                    _arg011, _arg17, _arg24, _arg33, _arg43, _arg53, _arg63,
                                    _arg73);
                    reply.writeNoException();
                    reply.writeInt(_result17);
                    return true;
                case 24:
                    String _result18 = getLockAction();
                    reply.writeNoException();
                    reply.writeString(_result18);
                    return true;
                case 25:
                    String _result19 = getClientData();
                    reply.writeNoException();
                    reply.writeString(_result19);
                    return true;
                case 26:
                    String _arg012 = data.readString();
                    data.enforceNoDataAvail();
                    int _result20 = setClientData(_arg012);
                    reply.writeNoException();
                    reply.writeInt(_result20);
                    return true;
                case 27:
                    String _result21 = getKGID();
                    reply.writeNoException();
                    reply.writeString(_result21);
                    return true;
                case 28:
                    int _result22 = resetRPMB();
                    reply.writeNoException();
                    reply.writeInt(_result22);
                    return true;
                case 29:
                    int _result23 = setCheckingState();
                    reply.writeNoException();
                    reply.writeInt(_result23);
                    return true;
                case 30:
                    String _result24 = verifyKgRot();
                    reply.writeNoException();
                    reply.writeString(_result24);
                    return true;
                case 31:
                    String _arg013 = data.readString();
                    String _arg18 = data.readString();
                    data.enforceNoDataAvail();
                    String _result25 = getStringSystemProperty(_arg013, _arg18);
                    reply.writeNoException();
                    reply.writeString(_result25);
                    return true;
                case 32:
                    int _result26 = getTAError();
                    reply.writeNoException();
                    reply.writeInt(_result26);
                    return true;
                case 33:
                    String _arg014 = data.readString();
                    String _arg19 = data.readString();
                    data.enforceNoDataAvail();
                    String _result27 = getNonce(_arg014, _arg19);
                    reply.writeNoException();
                    reply.writeString(_result27);
                    return true;
                case 34:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result28 = getTAInfo(_arg015);
                    reply.writeNoException();
                    reply.writeString(_result28);
                    return true;
                case 35:
                    String _arg016 = data.readString();
                    String _arg110 = data.readString();
                    String _arg25 = data.readString();
                    String _arg34 = data.readString();
                    data.enforceNoDataAvail();
                    int _result29 = provisionCert(_arg016, _arg110, _arg25, _arg34);
                    reply.writeNoException();
                    reply.writeInt(_result29);
                    return true;
                case 36:
                    boolean _result30 = isVpnExceptionRequired();
                    reply.writeNoException();
                    reply.writeBoolean(_result30);
                    return true;
                case 37:
                    Bundle _result31 = getKGServiceInfo();
                    reply.writeNoException();
                    reply.writeTypedObject(_result31, 1);
                    return true;
                case 38:
                    String _arg017 = data.readString();
                    String _arg111 = data.readString();
                    data.enforceNoDataAvail();
                    int _result32 = verifySfPolicy(_arg017, _arg111);
                    reply.writeNoException();
                    reply.writeInt(_result32);
                    return true;
                case 39:
                    String _result33 = getSfPolicy();
                    reply.writeNoException();
                    reply.writeString(_result33);
                    return true;
                case 40:
                    boolean _result34 = isKGAllowDO();
                    reply.writeNoException();
                    reply.writeBoolean(_result34);
                    return true;
                case 41:
                    boolean _result35 = isKGAllowADB();
                    reply.writeNoException();
                    reply.writeBoolean(_result35);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
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
            public void registerIntent(String preFix, List<String> actionList)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    _data.writeString(preFix);
                    _data.writeStringList(actionList);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
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
                    this.mRemote.transact(2, _data, _reply, 0);
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
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(msg);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(number);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(email);
                    try {
                        _data.writeBoolean(emergencycallbutton);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(name);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(failcount);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeLong(timeout);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(blockcount);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeBoolean(skippin);
                    } catch (Throwable th9) {
                        th = th9;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(bundle, 0);
                        this.mRemote.transact(3, _data, _reply, 0);
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
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
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(msg);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(number);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(email);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeBoolean(emergencycallbutton);
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(name);
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(failcount);
                    try {
                        _data.writeLong(timeout);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(blockcount);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeBoolean(skippin);
                    } catch (Throwable th9) {
                        th = th9;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(bundle, 0);
                        _data.writeBoolean(skipSupportContainer);
                        this.mRemote.transact(4, _data, _reply, 0);
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
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
            public String getPBAUniqueNumber() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
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
            public boolean shouldBlockCustomRom() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
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
            public void bindToLockScreen() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
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
                    this.mRemote.transact(10, _data, _reply, 0);
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
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
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
                    this.mRemote.transact(12, _data, _reply, 0);
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
                    this.mRemote.transact(13, _data, _reply, 0);
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
                    this.mRemote.transact(14, _data, _reply, 0);
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
                    this.mRemote.transact(15, _data, _reply, 0);
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
                    this.mRemote.transact(16, _data, _reply, 0);
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
                    this.mRemote.transact(17, _data, _reply, 0);
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
            public String getHotpChallenge() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
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
                    this.mRemote.transact(20, _data, _reply, 0);
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
                    this.mRemote.transact(21, _data, _reply, 0);
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
                    this.mRemote.transact(22, _data, _reply, 0);
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
            public String getLockAction() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(24, _data, _reply, 0);
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
                    this.mRemote.transact(25, _data, _reply, 0);
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
            public String getKGID() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(27, _data, _reply, 0);
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
            public int setCheckingState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(29, _data, _reply, 0);
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
                    this.mRemote.transact(30, _data, _reply, 0);
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
                    this.mRemote.transact(31, _data, _reply, 0);
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
                    this.mRemote.transact(32, _data, _reply, 0);
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
            public String getTAInfo(int infoFlag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    _data.writeInt(infoFlag);
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
            public int provisionCert(
                    String enrollCert, String hotpCert, String policyCert, String blCert)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    _data.writeString(enrollCert);
                    _data.writeString(hotpCert);
                    _data.writeString(policyCert);
                    _data.writeString(blCert);
                    this.mRemote.transact(35, _data, _reply, 0);
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
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
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
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public int verifySfPolicy(String sfPolicy, String signature) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    _data.writeString(sfPolicy);
                    _data.writeString(signature);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public String getSfPolicy() throws RemoteException {
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
            public boolean isKGAllowDO() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knoxguard.IKnoxGuardManager
            public boolean isKGAllowADB() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxGuardManager.DESCRIPTOR);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 40;
        }
    }
}
