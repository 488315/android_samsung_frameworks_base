package android.hardware.biometrics;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.text.TextUtils;

import java.util.List;

/* loaded from: classes2.dex */
public interface IAuthService extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.IAuthService";

    long authenticate(
            IBinder iBinder,
            long j,
            int i,
            IBiometricServiceReceiver iBiometricServiceReceiver,
            String str,
            PromptInfo promptInfo)
            throws RemoteException;

    int canAuthenticate(String str, int i, int i2) throws RemoteException;

    void cancelAuthentication(IBinder iBinder, String str, long j) throws RemoteException;

    ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str)
            throws RemoteException;

    long[] getAuthenticatorIds(int i) throws RemoteException;

    CharSequence getButtonLabel(int i, String str, int i2) throws RemoteException;

    long getLastAuthenticationTime(int i, int i2) throws RemoteException;

    CharSequence getPromptMessage(int i, String str, int i2) throws RemoteException;

    List<SensorPropertiesInternal> getSensorProperties(String str) throws RemoteException;

    CharSequence getSettingName(int i, String str, int i2) throws RemoteException;

    String getUiPackage() throws RemoteException;

    boolean hasEnrolledBiometrics(int i, String str) throws RemoteException;

    void invalidateAuthenticatorIds(int i, int i2, IInvalidationCallback iInvalidationCallback)
            throws RemoteException;

    void registerAuthenticationStateListener(
            AuthenticationStateListener authenticationStateListener) throws RemoteException;

    void registerEnabledOnKeyguardCallback(
            IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback)
            throws RemoteException;

    void resetLockout(int i, byte[] bArr) throws RemoteException;

    void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr)
            throws RemoteException;

    void unregisterAuthenticationStateListener(
            AuthenticationStateListener authenticationStateListener) throws RemoteException;

    public static class Default implements IAuthService {
        @Override // android.hardware.biometrics.IAuthService
        public ITestSession createTestSession(
                int sensorId, ITestSessionCallback callback, String opPackageName)
                throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public List<SensorPropertiesInternal> getSensorProperties(String opPackageName)
                throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public String getUiPackage() throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public long authenticate(
                IBinder token,
                long sessionId,
                int userId,
                IBiometricServiceReceiver receiver,
                String opPackageName,
                PromptInfo promptInfo)
                throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.biometrics.IAuthService
        public void cancelAuthentication(IBinder token, String opPackageName, long requestId)
                throws RemoteException {}

        @Override // android.hardware.biometrics.IAuthService
        public int canAuthenticate(String opPackageName, int userId, int authenticators)
                throws RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IAuthService
        public long getLastAuthenticationTime(int userId, int authenticators)
                throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.biometrics.IAuthService
        public boolean hasEnrolledBiometrics(int userId, String opPackageName)
                throws RemoteException {
            return false;
        }

        @Override // android.hardware.biometrics.IAuthService
        public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback callback)
                throws RemoteException {}

        @Override // android.hardware.biometrics.IAuthService
        public void registerAuthenticationStateListener(AuthenticationStateListener listener)
                throws RemoteException {}

        @Override // android.hardware.biometrics.IAuthService
        public void unregisterAuthenticationStateListener(AuthenticationStateListener listener)
                throws RemoteException {}

        @Override // android.hardware.biometrics.IAuthService
        public void invalidateAuthenticatorIds(
                int userId, int fromSensorId, IInvalidationCallback callback)
                throws RemoteException {}

        @Override // android.hardware.biometrics.IAuthService
        public long[] getAuthenticatorIds(int userId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public void resetLockoutTimeBound(
                IBinder token,
                String opPackageName,
                int fromSensorId,
                int userId,
                byte[] hardwareAuthToken)
                throws RemoteException {}

        @Override // android.hardware.biometrics.IAuthService
        public void resetLockout(int userId, byte[] hardwareAuthToken) throws RemoteException {}

        @Override // android.hardware.biometrics.IAuthService
        public CharSequence getButtonLabel(int userId, String opPackageName, int authenticators)
                throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public CharSequence getPromptMessage(int userId, String opPackageName, int authenticators)
                throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public CharSequence getSettingName(int userId, String opPackageName, int authenticators)
                throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IAuthService {
        static final int TRANSACTION_authenticate = 4;
        static final int TRANSACTION_canAuthenticate = 6;
        static final int TRANSACTION_cancelAuthentication = 5;
        static final int TRANSACTION_createTestSession = 1;
        static final int TRANSACTION_getAuthenticatorIds = 13;
        static final int TRANSACTION_getButtonLabel = 16;
        static final int TRANSACTION_getLastAuthenticationTime = 7;
        static final int TRANSACTION_getPromptMessage = 17;
        static final int TRANSACTION_getSensorProperties = 2;
        static final int TRANSACTION_getSettingName = 18;
        static final int TRANSACTION_getUiPackage = 3;
        static final int TRANSACTION_hasEnrolledBiometrics = 8;
        static final int TRANSACTION_invalidateAuthenticatorIds = 12;
        static final int TRANSACTION_registerAuthenticationStateListener = 10;
        static final int TRANSACTION_registerEnabledOnKeyguardCallback = 9;
        static final int TRANSACTION_resetLockout = 15;
        static final int TRANSACTION_resetLockoutTimeBound = 14;
        static final int TRANSACTION_unregisterAuthenticationStateListener = 11;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, IAuthService.DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(
                    PermissionEnforcer.fromContext(
                            ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IAuthService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAuthService.DESCRIPTOR);
            if (iin != null && (iin instanceof IAuthService)) {
                return (IAuthService) iin;
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
                    return "createTestSession";
                case 2:
                    return "getSensorProperties";
                case 3:
                    return "getUiPackage";
                case 4:
                    return "authenticate";
                case 5:
                    return "cancelAuthentication";
                case 6:
                    return "canAuthenticate";
                case 7:
                    return "getLastAuthenticationTime";
                case 8:
                    return "hasEnrolledBiometrics";
                case 9:
                    return "registerEnabledOnKeyguardCallback";
                case 10:
                    return "registerAuthenticationStateListener";
                case 11:
                    return "unregisterAuthenticationStateListener";
                case 12:
                    return "invalidateAuthenticatorIds";
                case 13:
                    return "getAuthenticatorIds";
                case 14:
                    return "resetLockoutTimeBound";
                case 15:
                    return "resetLockout";
                case 16:
                    return "getButtonLabel";
                case 17:
                    return "getPromptMessage";
                case 18:
                    return "getSettingName";
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
                data.enforceInterface(IAuthService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAuthService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    ITestSessionCallback _arg1 =
                            ITestSessionCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg2 = data.readString();
                    data.enforceNoDataAvail();
                    ITestSession _result = createTestSession(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    data.enforceNoDataAvail();
                    List<SensorPropertiesInternal> _result2 = getSensorProperties(_arg02);
                    reply.writeNoException();
                    reply.writeTypedList(_result2, 1);
                    return true;
                case 3:
                    String _result3 = getUiPackage();
                    reply.writeNoException();
                    reply.writeString(_result3);
                    return true;
                case 4:
                    IBinder _arg03 = data.readStrongBinder();
                    long _arg12 = data.readLong();
                    int _arg22 = data.readInt();
                    IBiometricServiceReceiver _arg3 =
                            IBiometricServiceReceiver.Stub.asInterface(data.readStrongBinder());
                    String _arg4 = data.readString();
                    PromptInfo _arg5 = (PromptInfo) data.readTypedObject(PromptInfo.CREATOR);
                    data.enforceNoDataAvail();
                    long _result4 = authenticate(_arg03, _arg12, _arg22, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    reply.writeLong(_result4);
                    return true;
                case 5:
                    IBinder _arg04 = data.readStrongBinder();
                    String _arg13 = data.readString();
                    long _arg23 = data.readLong();
                    data.enforceNoDataAvail();
                    cancelAuthentication(_arg04, _arg13, _arg23);
                    reply.writeNoException();
                    return true;
                case 6:
                    String _arg05 = data.readString();
                    int _arg14 = data.readInt();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result5 = canAuthenticate(_arg05, _arg14, _arg24);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 7:
                    int _arg06 = data.readInt();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result6 = getLastAuthenticationTime(_arg06, _arg15);
                    reply.writeNoException();
                    reply.writeLong(_result6);
                    return true;
                case 8:
                    int _arg07 = data.readInt();
                    String _arg16 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result7 = hasEnrolledBiometrics(_arg07, _arg16);
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 9:
                    IBiometricEnabledOnKeyguardCallback _arg08 =
                            IBiometricEnabledOnKeyguardCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerEnabledOnKeyguardCallback(_arg08);
                    reply.writeNoException();
                    return true;
                case 10:
                    AuthenticationStateListener _arg09 =
                            AuthenticationStateListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerAuthenticationStateListener(_arg09);
                    reply.writeNoException();
                    return true;
                case 11:
                    AuthenticationStateListener _arg010 =
                            AuthenticationStateListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterAuthenticationStateListener(_arg010);
                    reply.writeNoException();
                    return true;
                case 12:
                    int _arg011 = data.readInt();
                    int _arg17 = data.readInt();
                    IInvalidationCallback _arg25 =
                            IInvalidationCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    invalidateAuthenticatorIds(_arg011, _arg17, _arg25);
                    reply.writeNoException();
                    return true;
                case 13:
                    int _arg012 = data.readInt();
                    data.enforceNoDataAvail();
                    long[] _result8 = getAuthenticatorIds(_arg012);
                    reply.writeNoException();
                    reply.writeLongArray(_result8);
                    return true;
                case 14:
                    IBinder _arg013 = data.readStrongBinder();
                    String _arg18 = data.readString();
                    int _arg26 = data.readInt();
                    int _arg32 = data.readInt();
                    byte[] _arg42 = data.createByteArray();
                    data.enforceNoDataAvail();
                    resetLockoutTimeBound(_arg013, _arg18, _arg26, _arg32, _arg42);
                    reply.writeNoException();
                    return true;
                case 15:
                    int _arg014 = data.readInt();
                    byte[] _arg19 = data.createByteArray();
                    data.enforceNoDataAvail();
                    resetLockout(_arg014, _arg19);
                    reply.writeNoException();
                    return true;
                case 16:
                    int _arg015 = data.readInt();
                    String _arg110 = data.readString();
                    int _arg27 = data.readInt();
                    data.enforceNoDataAvail();
                    CharSequence _result9 = getButtonLabel(_arg015, _arg110, _arg27);
                    reply.writeNoException();
                    if (_result9 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result9, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 17:
                    int _arg016 = data.readInt();
                    String _arg111 = data.readString();
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    CharSequence _result10 = getPromptMessage(_arg016, _arg111, _arg28);
                    reply.writeNoException();
                    if (_result10 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result10, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 18:
                    int _arg017 = data.readInt();
                    String _arg112 = data.readString();
                    int _arg29 = data.readInt();
                    data.enforceNoDataAvail();
                    CharSequence _result11 = getSettingName(_arg017, _arg112, _arg29);
                    reply.writeNoException();
                    if (_result11 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result11, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAuthService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAuthService.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IAuthService
            public ITestSession createTestSession(
                    int sensorId, ITestSessionCallback callback, String opPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeInt(sensorId);
                    _data.writeStrongInterface(callback);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    ITestSession _result = ITestSession.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public List<SensorPropertiesInternal> getSensorProperties(String opPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    List<SensorPropertiesInternal> _result =
                            _reply.createTypedArrayList(SensorPropertiesInternal.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public String getUiPackage() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public long authenticate(
                    IBinder token,
                    long sessionId,
                    int userId,
                    IBiometricServiceReceiver receiver,
                    String opPackageName,
                    PromptInfo promptInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeLong(sessionId);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(receiver);
                    _data.writeString(opPackageName);
                    _data.writeTypedObject(promptInfo, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void cancelAuthentication(IBinder token, String opPackageName, long requestId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(opPackageName);
                    _data.writeLong(requestId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public int canAuthenticate(String opPackageName, int userId, int authenticators)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeString(opPackageName);
                    _data.writeInt(userId);
                    _data.writeInt(authenticators);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public long getLastAuthenticationTime(int userId, int authenticators)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(authenticators);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public boolean hasEnrolledBiometrics(int userId, String opPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void registerEnabledOnKeyguardCallback(
                    IBiometricEnabledOnKeyguardCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void registerAuthenticationStateListener(AuthenticationStateListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void unregisterAuthenticationStateListener(AuthenticationStateListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void invalidateAuthenticatorIds(
                    int userId, int fromSensorId, IInvalidationCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(fromSensorId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public long[] getAuthenticatorIds(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    long[] _result = _reply.createLongArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void resetLockoutTimeBound(
                    IBinder token,
                    String opPackageName,
                    int fromSensorId,
                    int userId,
                    byte[] hardwareAuthToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(opPackageName);
                    _data.writeInt(fromSensorId);
                    _data.writeInt(userId);
                    _data.writeByteArray(hardwareAuthToken);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void resetLockout(int userId, byte[] hardwareAuthToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeByteArray(hardwareAuthToken);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public CharSequence getButtonLabel(int userId, String opPackageName, int authenticators)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    _data.writeInt(authenticators);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result =
                            (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public CharSequence getPromptMessage(
                    int userId, String opPackageName, int authenticators) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    _data.writeInt(authenticators);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result =
                            (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public CharSequence getSettingName(int userId, String opPackageName, int authenticators)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    _data.writeInt(authenticators);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result =
                            (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void createTestSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void getSensorProperties_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void getUiPackage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }
    }
}
