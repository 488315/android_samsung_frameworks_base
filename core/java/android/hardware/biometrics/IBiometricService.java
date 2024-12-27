package android.hardware.biometrics;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

import java.util.List;

public interface IBiometricService extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricService";

    long authenticate(
            IBinder iBinder,
            long j,
            int i,
            IBiometricServiceReceiver iBiometricServiceReceiver,
            String str,
            PromptInfo promptInfo)
            throws RemoteException;

    int canAuthenticate(String str, int i, int i2, int i3) throws RemoteException;

    void cancelAuthentication(IBinder iBinder, String str, long j) throws RemoteException;

    ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str)
            throws RemoteException;

    long[] getAuthenticatorIds(int i) throws RemoteException;

    int getCurrentModality(String str, int i, int i2, int i3) throws RemoteException;

    int getCurrentStrength(int i) throws RemoteException;

    long getLastAuthenticationTime(int i, int i2) throws RemoteException;

    List<SensorPropertiesInternal> getSensorProperties(String str) throws RemoteException;

    int getSupportedModalities(int i) throws RemoteException;

    boolean hasEnrolledBiometrics(int i, String str) throws RemoteException;

    void invalidateAuthenticatorIds(int i, int i2, IInvalidationCallback iInvalidationCallback)
            throws RemoteException;

    void onReadyForAuthentication(long j, int i) throws RemoteException;

    void registerAuthenticator(
            int i, int i2, int i3, IBiometricAuthenticator iBiometricAuthenticator)
            throws RemoteException;

    void registerEnabledOnKeyguardCallback(
            IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback)
            throws RemoteException;

    void resetLockout(int i, byte[] bArr) throws RemoteException;

    void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr)
            throws RemoteException;

    PromptInfo semGetPromptInfo(int i) throws RemoteException;

    public static class Default implements IBiometricService {
        @Override // android.hardware.biometrics.IBiometricService
        public ITestSession createTestSession(
                int sensorId, ITestSessionCallback callback, String opPackageName)
                throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public List<SensorPropertiesInternal> getSensorProperties(String opPackageName)
                throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public long authenticate(
                IBinder token,
                long operationId,
                int userId,
                IBiometricServiceReceiver receiver,
                String opPackageName,
                PromptInfo promptInfo)
                throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void cancelAuthentication(IBinder token, String opPackageName, long requestId)
                throws RemoteException {}

        @Override // android.hardware.biometrics.IBiometricService
        public int canAuthenticate(
                String opPackageName, int userId, int callingUserId, int authenticators)
                throws RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public long getLastAuthenticationTime(int userId, int authenticators)
                throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public boolean hasEnrolledBiometrics(int userId, String opPackageName)
                throws RemoteException {
            return false;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void registerAuthenticator(
                int id, int modality, int strength, IBiometricAuthenticator authenticator)
                throws RemoteException {}

        @Override // android.hardware.biometrics.IBiometricService
        public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback callback)
                throws RemoteException {}

        @Override // android.hardware.biometrics.IBiometricService
        public void onReadyForAuthentication(long requestId, int cookie) throws RemoteException {}

        @Override // android.hardware.biometrics.IBiometricService
        public void invalidateAuthenticatorIds(
                int userId, int fromSensorId, IInvalidationCallback callback)
                throws RemoteException {}

        @Override // android.hardware.biometrics.IBiometricService
        public long[] getAuthenticatorIds(int callingUserId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void resetLockoutTimeBound(
                IBinder token,
                String opPackageName,
                int fromSensorId,
                int userId,
                byte[] hardwareAuthToken)
                throws RemoteException {}

        @Override // android.hardware.biometrics.IBiometricService
        public void resetLockout(int userId, byte[] hardwareAuthToken) throws RemoteException {}

        @Override // android.hardware.biometrics.IBiometricService
        public int getCurrentStrength(int sensorId) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public int getCurrentModality(
                String opPackageName, int userId, int callingUserId, int authenticators)
                throws RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public int getSupportedModalities(int authenticators) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public PromptInfo semGetPromptInfo(int cookie) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IBiometricService {
        static final int TRANSACTION_authenticate = 3;
        static final int TRANSACTION_canAuthenticate = 5;
        static final int TRANSACTION_cancelAuthentication = 4;
        static final int TRANSACTION_createTestSession = 1;
        static final int TRANSACTION_getAuthenticatorIds = 12;
        static final int TRANSACTION_getCurrentModality = 16;
        static final int TRANSACTION_getCurrentStrength = 15;
        static final int TRANSACTION_getLastAuthenticationTime = 6;
        static final int TRANSACTION_getSensorProperties = 2;
        static final int TRANSACTION_getSupportedModalities = 17;
        static final int TRANSACTION_hasEnrolledBiometrics = 7;
        static final int TRANSACTION_invalidateAuthenticatorIds = 11;
        static final int TRANSACTION_onReadyForAuthentication = 10;
        static final int TRANSACTION_registerAuthenticator = 8;
        static final int TRANSACTION_registerEnabledOnKeyguardCallback = 9;
        static final int TRANSACTION_resetLockout = 14;
        static final int TRANSACTION_resetLockoutTimeBound = 13;
        static final int TRANSACTION_semGetPromptInfo = 18;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, IBiometricService.DESCRIPTOR);
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

        public static IBiometricService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IBiometricService.DESCRIPTOR);
            if (iin != null && (iin instanceof IBiometricService)) {
                return (IBiometricService) iin;
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
                    return "authenticate";
                case 4:
                    return "cancelAuthentication";
                case 5:
                    return "canAuthenticate";
                case 6:
                    return "getLastAuthenticationTime";
                case 7:
                    return "hasEnrolledBiometrics";
                case 8:
                    return "registerAuthenticator";
                case 9:
                    return "registerEnabledOnKeyguardCallback";
                case 10:
                    return "onReadyForAuthentication";
                case 11:
                    return "invalidateAuthenticatorIds";
                case 12:
                    return "getAuthenticatorIds";
                case 13:
                    return "resetLockoutTimeBound";
                case 14:
                    return "resetLockout";
                case 15:
                    return "getCurrentStrength";
                case 16:
                    return "getCurrentModality";
                case 17:
                    return "getSupportedModalities";
                case 18:
                    return "semGetPromptInfo";
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
                data.enforceInterface(IBiometricService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IBiometricService.DESCRIPTOR);
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
                    IBinder _arg03 = data.readStrongBinder();
                    long _arg12 = data.readLong();
                    int _arg22 = data.readInt();
                    IBiometricServiceReceiver _arg3 =
                            IBiometricServiceReceiver.Stub.asInterface(data.readStrongBinder());
                    String _arg4 = data.readString();
                    PromptInfo _arg5 = (PromptInfo) data.readTypedObject(PromptInfo.CREATOR);
                    data.enforceNoDataAvail();
                    long _result3 = authenticate(_arg03, _arg12, _arg22, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    reply.writeLong(_result3);
                    return true;
                case 4:
                    IBinder _arg04 = data.readStrongBinder();
                    String _arg13 = data.readString();
                    long _arg23 = data.readLong();
                    data.enforceNoDataAvail();
                    cancelAuthentication(_arg04, _arg13, _arg23);
                    reply.writeNoException();
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    int _arg14 = data.readInt();
                    int _arg24 = data.readInt();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result4 = canAuthenticate(_arg05, _arg14, _arg24, _arg32);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result5 = getLastAuthenticationTime(_arg06, _arg15);
                    reply.writeNoException();
                    reply.writeLong(_result5);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    String _arg16 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result6 = hasEnrolledBiometrics(_arg07, _arg16);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    int _arg17 = data.readInt();
                    int _arg25 = data.readInt();
                    IBiometricAuthenticator _arg33 =
                            IBiometricAuthenticator.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerAuthenticator(_arg08, _arg17, _arg25, _arg33);
                    reply.writeNoException();
                    return true;
                case 9:
                    IBiometricEnabledOnKeyguardCallback _arg09 =
                            IBiometricEnabledOnKeyguardCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerEnabledOnKeyguardCallback(_arg09);
                    reply.writeNoException();
                    return true;
                case 10:
                    long _arg010 = data.readLong();
                    int _arg18 = data.readInt();
                    data.enforceNoDataAvail();
                    onReadyForAuthentication(_arg010, _arg18);
                    reply.writeNoException();
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    int _arg19 = data.readInt();
                    IInvalidationCallback _arg26 =
                            IInvalidationCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    invalidateAuthenticatorIds(_arg011, _arg19, _arg26);
                    reply.writeNoException();
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    data.enforceNoDataAvail();
                    long[] _result7 = getAuthenticatorIds(_arg012);
                    reply.writeNoException();
                    reply.writeLongArray(_result7);
                    return true;
                case 13:
                    IBinder _arg013 = data.readStrongBinder();
                    String _arg110 = data.readString();
                    int _arg27 = data.readInt();
                    int _arg34 = data.readInt();
                    byte[] _arg42 = data.createByteArray();
                    data.enforceNoDataAvail();
                    resetLockoutTimeBound(_arg013, _arg110, _arg27, _arg34, _arg42);
                    reply.writeNoException();
                    return true;
                case 14:
                    int _arg014 = data.readInt();
                    byte[] _arg111 = data.createByteArray();
                    data.enforceNoDataAvail();
                    resetLockout(_arg014, _arg111);
                    reply.writeNoException();
                    return true;
                case 15:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result8 = getCurrentStrength(_arg015);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 16:
                    String _arg016 = data.readString();
                    int _arg112 = data.readInt();
                    int _arg28 = data.readInt();
                    int _arg35 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result9 = getCurrentModality(_arg016, _arg112, _arg28, _arg35);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 17:
                    int _arg017 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result10 = getSupportedModalities(_arg017);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 18:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    PromptInfo _result11 = semGetPromptInfo(_arg018);
                    reply.writeNoException();
                    reply.writeTypedObject(_result11, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IBiometricService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBiometricService.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricService
            public ITestSession createTestSession(
                    int sensorId, ITestSessionCallback callback, String opPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
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

            @Override // android.hardware.biometrics.IBiometricService
            public List<SensorPropertiesInternal> getSensorProperties(String opPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
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

            @Override // android.hardware.biometrics.IBiometricService
            public long authenticate(
                    IBinder token,
                    long operationId,
                    int userId,
                    IBiometricServiceReceiver receiver,
                    String opPackageName,
                    PromptInfo promptInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeLong(operationId);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(receiver);
                    _data.writeString(opPackageName);
                    _data.writeTypedObject(promptInfo, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void cancelAuthentication(IBinder token, String opPackageName, long requestId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(opPackageName);
                    _data.writeLong(requestId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int canAuthenticate(
                    String opPackageName, int userId, int callingUserId, int authenticators)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeString(opPackageName);
                    _data.writeInt(userId);
                    _data.writeInt(callingUserId);
                    _data.writeInt(authenticators);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public long getLastAuthenticationTime(int userId, int authenticators)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(authenticators);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public boolean hasEnrolledBiometrics(int userId, String opPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void registerAuthenticator(
                    int id, int modality, int strength, IBiometricAuthenticator authenticator)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(modality);
                    _data.writeInt(strength);
                    _data.writeStrongInterface(authenticator);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void registerEnabledOnKeyguardCallback(
                    IBiometricEnabledOnKeyguardCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void onReadyForAuthentication(long requestId, int cookie)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeLong(requestId);
                    _data.writeInt(cookie);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void invalidateAuthenticatorIds(
                    int userId, int fromSensorId, IInvalidationCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(fromSensorId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public long[] getAuthenticatorIds(int callingUserId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeInt(callingUserId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    long[] _result = _reply.createLongArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
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
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(opPackageName);
                    _data.writeInt(fromSensorId);
                    _data.writeInt(userId);
                    _data.writeByteArray(hardwareAuthToken);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void resetLockout(int userId, byte[] hardwareAuthToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeByteArray(hardwareAuthToken);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int getCurrentStrength(int sensorId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeInt(sensorId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int getCurrentModality(
                    String opPackageName, int userId, int callingUserId, int authenticators)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeString(opPackageName);
                    _data.writeInt(userId);
                    _data.writeInt(callingUserId);
                    _data.writeInt(authenticators);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int getSupportedModalities(int authenticators) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeInt(authenticators);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public PromptInfo semGetPromptInfo(int cookie) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    _data.writeInt(cookie);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    PromptInfo _result = (PromptInfo) _reply.readTypedObject(PromptInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void createTestSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getSensorProperties_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void authenticate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void cancelAuthentication_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void canAuthenticate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getLastAuthenticationTime_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void hasEnrolledBiometrics_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void registerAuthenticator_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void registerEnabledOnKeyguardCallback_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void onReadyForAuthentication_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void invalidateAuthenticatorIds_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getAuthenticatorIds_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void resetLockoutTimeBound_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void resetLockout_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getCurrentStrength_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getCurrentModality_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getSupportedModalities_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void semGetPromptInfo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }
    }
}
