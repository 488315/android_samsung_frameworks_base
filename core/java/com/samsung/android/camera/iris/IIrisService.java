package com.samsung.android.camera.iris;

import android.hardware.biometrics.IBiometricSensorReceiver;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface IIrisService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.camera.iris.IIrisService";

    void addLockoutResetCallback(IIrisServiceLockoutResetCallback iIrisServiceLockoutResetCallback)
            throws RemoteException;

    void authenticate(
            IBinder iBinder,
            IBinder iBinder2,
            int i,
            int i2,
            int i3,
            int i4,
            long j,
            int i5,
            IIrisServiceReceiver iIrisServiceReceiver,
            int i6,
            String str,
            Bundle bundle,
            byte[] bArr)
            throws RemoteException;

    void cancelAuthentication(IBinder iBinder, String str) throws RemoteException;

    void cancelAuthenticationFromService(
            IBinder iBinder, String str, int i, int i2, int i3, boolean z) throws RemoteException;

    void cancelEnrollment(IBinder iBinder) throws RemoteException;

    void enableIRImageCallback(int i, String str, int i2) throws RemoteException;

    void enroll(
            IBinder iBinder,
            IBinder iBinder2,
            int i,
            int i2,
            int i3,
            int i4,
            byte[] bArr,
            int i5,
            IIrisServiceReceiver iIrisServiceReceiver,
            int i6,
            String str,
            Bundle bundle)
            throws RemoteException;

    long getAuthenticatorId(String str) throws RemoteException;

    List<Iris> getEnrolledIrises(int i, String str) throws RemoteException;

    boolean hasDisabledIris(int i, String str) throws RemoteException;

    boolean hasEnrolledIrises(int i, String str) throws RemoteException;

    boolean isHardwareDetected(long j, String str) throws RemoteException;

    int postEnroll(IBinder iBinder) throws RemoteException;

    long preEnroll(IBinder iBinder) throws RemoteException;

    void prepareForAuthentication(
            boolean z,
            IBinder iBinder,
            long j,
            int i,
            IBiometricSensorReceiver iBiometricSensorReceiver,
            String str,
            int i2,
            int i3,
            int i4,
            int i5,
            Bundle bundle)
            throws RemoteException;

    void prompt_authenticate(
            IBinder iBinder,
            IBinder iBinder2,
            int i,
            int i2,
            int i3,
            int i4,
            long j,
            int i5,
            IIrisServiceReceiver iIrisServiceReceiver,
            int i6,
            String str,
            Bundle bundle,
            byte[] bArr)
            throws RemoteException;

    void remove(IBinder iBinder, int i, int i2, int i3, IIrisServiceReceiver iIrisServiceReceiver)
            throws RemoteException;

    void rename(int i, int i2, String str) throws RemoteException;

    int request(
            IBinder iBinder,
            int i,
            byte[] bArr,
            byte[] bArr2,
            int i2,
            int i3,
            IIrisServiceReceiver iIrisServiceReceiver)
            throws RemoteException;

    void resetTimeout(byte[] bArr) throws RemoteException;

    void setActiveUser(int i) throws RemoteException;

    void setIrisViewType(int i, String str, int i2) throws RemoteException;

    void startPreparedClient(int i) throws RemoteException;

    public static class Default implements IIrisService {
        @Override // com.samsung.android.camera.iris.IIrisService
        public void authenticate(
                IBinder token,
                IBinder windownToken,
                int x,
                int y,
                int width,
                int height,
                long sessionId,
                int userId,
                IIrisServiceReceiver receiver,
                int flags,
                String opPackageName,
                Bundle attr,
                byte[] fidoRequestData)
                throws RemoteException {}

        @Override // com.samsung.android.camera.iris.IIrisService
        public void prompt_authenticate(
                IBinder token,
                IBinder windownToken,
                int x,
                int y,
                int width,
                int height,
                long sessionId,
                int userId,
                IIrisServiceReceiver receiver,
                int flags,
                String opPackageName,
                Bundle attr,
                byte[] fidoRequestData)
                throws RemoteException {}

        @Override // com.samsung.android.camera.iris.IIrisService
        public void cancelAuthentication(IBinder token, String opPackageName)
                throws RemoteException {}

        @Override // com.samsung.android.camera.iris.IIrisService
        public void enroll(
                IBinder token,
                IBinder windownToken,
                int x,
                int y,
                int width,
                int height,
                byte[] cryptoToken,
                int groupId,
                IIrisServiceReceiver receiver,
                int flags,
                String opPackageName,
                Bundle attr)
                throws RemoteException {}

        @Override // com.samsung.android.camera.iris.IIrisService
        public void cancelEnrollment(IBinder token) throws RemoteException {}

        @Override // com.samsung.android.camera.iris.IIrisService
        public void remove(
                IBinder token, int irisId, int groupId, int userId, IIrisServiceReceiver receiver)
                throws RemoteException {}

        @Override // com.samsung.android.camera.iris.IIrisService
        public void rename(int irisId, int groupId, String name) throws RemoteException {}

        @Override // com.samsung.android.camera.iris.IIrisService
        public List<Iris> getEnrolledIrises(int groupId, String opPackageName)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public boolean isHardwareDetected(long deviceId, String opPackageName)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public long preEnroll(IBinder token) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public int postEnroll(IBinder token) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public boolean hasEnrolledIrises(int groupId, String opPackageName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public boolean hasDisabledIris(int groupId, String opPackageName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public long getAuthenticatorId(String opPackageName) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public int request(
                IBinder token,
                int cmd,
                byte[] inputBuf,
                byte[] outputBuf,
                int inParam,
                int groupId,
                IIrisServiceReceiver receiver)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void resetTimeout(byte[] cryptoToken) throws RemoteException {}

        @Override // com.samsung.android.camera.iris.IIrisService
        public void addLockoutResetCallback(IIrisServiceLockoutResetCallback callback)
                throws RemoteException {}

        @Override // com.samsung.android.camera.iris.IIrisService
        public void setIrisViewType(int groupId, String opPackageName, int irisViewType)
                throws RemoteException {}

        @Override // com.samsung.android.camera.iris.IIrisService
        public void enableIRImageCallback(int groupId, String opPackageName, int callbackType)
                throws RemoteException {}

        @Override // com.samsung.android.camera.iris.IIrisService
        public void setActiveUser(int uid) throws RemoteException {}

        @Override // com.samsung.android.camera.iris.IIrisService
        public void prepareForAuthentication(
                boolean requireConfirmation,
                IBinder token,
                long sessionId,
                int userId,
                IBiometricSensorReceiver wrapperReceiver,
                String opPackageName,
                int cookie,
                int callingUid,
                int callingPid,
                int callingUserId,
                Bundle bundle)
                throws RemoteException {}

        @Override // com.samsung.android.camera.iris.IIrisService
        public void startPreparedClient(int cookie) throws RemoteException {}

        @Override // com.samsung.android.camera.iris.IIrisService
        public void cancelAuthenticationFromService(
                IBinder token,
                String opPackageName,
                int callingUid,
                int callingPid,
                int callingUserId,
                boolean fromClient)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IIrisService {
        static final int TRANSACTION_addLockoutResetCallback = 17;
        static final int TRANSACTION_authenticate = 1;
        static final int TRANSACTION_cancelAuthentication = 3;
        static final int TRANSACTION_cancelAuthenticationFromService = 23;
        static final int TRANSACTION_cancelEnrollment = 5;
        static final int TRANSACTION_enableIRImageCallback = 19;
        static final int TRANSACTION_enroll = 4;
        static final int TRANSACTION_getAuthenticatorId = 14;
        static final int TRANSACTION_getEnrolledIrises = 8;
        static final int TRANSACTION_hasDisabledIris = 13;
        static final int TRANSACTION_hasEnrolledIrises = 12;
        static final int TRANSACTION_isHardwareDetected = 9;
        static final int TRANSACTION_postEnroll = 11;
        static final int TRANSACTION_preEnroll = 10;
        static final int TRANSACTION_prepareForAuthentication = 21;
        static final int TRANSACTION_prompt_authenticate = 2;
        static final int TRANSACTION_remove = 6;
        static final int TRANSACTION_rename = 7;
        static final int TRANSACTION_request = 15;
        static final int TRANSACTION_resetTimeout = 16;
        static final int TRANSACTION_setActiveUser = 20;
        static final int TRANSACTION_setIrisViewType = 18;
        static final int TRANSACTION_startPreparedClient = 22;

        public Stub() {
            attachInterface(this, IIrisService.DESCRIPTOR);
        }

        public static IIrisService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IIrisService.DESCRIPTOR);
            if (iin != null && (iin instanceof IIrisService)) {
                return (IIrisService) iin;
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
                    return "authenticate";
                case 2:
                    return "prompt_authenticate";
                case 3:
                    return "cancelAuthentication";
                case 4:
                    return "enroll";
                case 5:
                    return "cancelEnrollment";
                case 6:
                    return "remove";
                case 7:
                    return "rename";
                case 8:
                    return "getEnrolledIrises";
                case 9:
                    return "isHardwareDetected";
                case 10:
                    return "preEnroll";
                case 11:
                    return "postEnroll";
                case 12:
                    return "hasEnrolledIrises";
                case 13:
                    return "hasDisabledIris";
                case 14:
                    return "getAuthenticatorId";
                case 15:
                    return "request";
                case 16:
                    return "resetTimeout";
                case 17:
                    return "addLockoutResetCallback";
                case 18:
                    return "setIrisViewType";
                case 19:
                    return "enableIRImageCallback";
                case 20:
                    return "setActiveUser";
                case 21:
                    return "prepareForAuthentication";
                case 22:
                    return "startPreparedClient";
                case 23:
                    return "cancelAuthenticationFromService";
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
            byte[] _arg3;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IIrisService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IIrisService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    IBinder _arg1 = data.readStrongBinder();
                    int _arg2 = data.readInt();
                    int _arg32 = data.readInt();
                    int _arg4 = data.readInt();
                    int _arg5 = data.readInt();
                    long _arg6 = data.readLong();
                    int _arg7 = data.readInt();
                    IIrisServiceReceiver _arg8 =
                            IIrisServiceReceiver.Stub.asInterface(data.readStrongBinder());
                    int _arg9 = data.readInt();
                    String _arg10 = data.readString();
                    Bundle _arg11 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    byte[] _arg12 = data.createByteArray();
                    data.enforceNoDataAvail();
                    authenticate(
                            _arg0, _arg1, _arg2, _arg32, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9,
                            _arg10, _arg11, _arg12);
                    reply.writeNoException();
                    return true;
                case 2:
                    IBinder _arg02 = data.readStrongBinder();
                    IBinder _arg13 = data.readStrongBinder();
                    int _arg22 = data.readInt();
                    int _arg33 = data.readInt();
                    int _arg42 = data.readInt();
                    int _arg52 = data.readInt();
                    long _arg62 = data.readLong();
                    int _arg72 = data.readInt();
                    IIrisServiceReceiver _arg82 =
                            IIrisServiceReceiver.Stub.asInterface(data.readStrongBinder());
                    int _arg92 = data.readInt();
                    String _arg102 = data.readString();
                    Bundle _arg112 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    byte[] _arg122 = data.createByteArray();
                    data.enforceNoDataAvail();
                    prompt_authenticate(
                            _arg02, _arg13, _arg22, _arg33, _arg42, _arg52, _arg62, _arg72, _arg82,
                            _arg92, _arg102, _arg112, _arg122);
                    reply.writeNoException();
                    return true;
                case 3:
                    IBinder _arg03 = data.readStrongBinder();
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    cancelAuthentication(_arg03, _arg14);
                    reply.writeNoException();
                    return true;
                case 4:
                    IBinder _arg04 = data.readStrongBinder();
                    IBinder _arg15 = data.readStrongBinder();
                    int _arg23 = data.readInt();
                    int _arg34 = data.readInt();
                    int _arg43 = data.readInt();
                    int _arg53 = data.readInt();
                    byte[] _arg63 = data.createByteArray();
                    int _arg73 = data.readInt();
                    IIrisServiceReceiver _arg83 =
                            IIrisServiceReceiver.Stub.asInterface(data.readStrongBinder());
                    int _arg93 = data.readInt();
                    String _arg103 = data.readString();
                    Bundle _arg113 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    enroll(
                            _arg04, _arg15, _arg23, _arg34, _arg43, _arg53, _arg63, _arg73, _arg83,
                            _arg93, _arg103, _arg113);
                    reply.writeNoException();
                    return true;
                case 5:
                    IBinder _arg05 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    cancelEnrollment(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    IBinder _arg06 = data.readStrongBinder();
                    int _arg16 = data.readInt();
                    int _arg24 = data.readInt();
                    int _arg35 = data.readInt();
                    IIrisServiceReceiver _arg44 =
                            IIrisServiceReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    remove(_arg06, _arg16, _arg24, _arg35, _arg44);
                    reply.writeNoException();
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    int _arg17 = data.readInt();
                    String _arg25 = data.readString();
                    data.enforceNoDataAvail();
                    rename(_arg07, _arg17, _arg25);
                    reply.writeNoException();
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    String _arg18 = data.readString();
                    data.enforceNoDataAvail();
                    List<Iris> _result = getEnrolledIrises(_arg08, _arg18);
                    reply.writeNoException();
                    reply.writeTypedList(_result, 1);
                    return true;
                case 9:
                    long _arg09 = data.readLong();
                    String _arg19 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result2 = isHardwareDetected(_arg09, _arg19);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 10:
                    IBinder _arg010 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    long _result3 = preEnroll(_arg010);
                    reply.writeNoException();
                    reply.writeLong(_result3);
                    return true;
                case 11:
                    IBinder _arg011 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    int _result4 = postEnroll(_arg011);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    String _arg110 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result5 = hasEnrolledIrises(_arg012, _arg110);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 13:
                    int _arg013 = data.readInt();
                    String _arg111 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result6 = hasDisabledIris(_arg013, _arg111);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 14:
                    String _arg014 = data.readString();
                    data.enforceNoDataAvail();
                    long _result7 = getAuthenticatorId(_arg014);
                    reply.writeNoException();
                    reply.writeLong(_result7);
                    return true;
                case 15:
                    IBinder _arg015 = data.readStrongBinder();
                    int _arg114 = data.readInt();
                    byte[] _arg26 = data.createByteArray();
                    int _arg3_length = data.readInt();
                    if (_arg3_length < 0) {
                        _arg3 = null;
                    } else {
                        byte[] _arg36 = new byte[_arg3_length];
                        _arg3 = _arg36;
                    }
                    int _arg45 = data.readInt();
                    int _arg54 = data.readInt();
                    IIrisServiceReceiver _arg64 =
                            IIrisServiceReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result8 = request(_arg015, _arg114, _arg26, _arg3, _arg45, _arg54, _arg64);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    reply.writeByteArray(_arg3);
                    return true;
                case 16:
                    byte[] _arg016 = data.createByteArray();
                    data.enforceNoDataAvail();
                    resetTimeout(_arg016);
                    reply.writeNoException();
                    return true;
                case 17:
                    IIrisServiceLockoutResetCallback _arg017 =
                            IIrisServiceLockoutResetCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addLockoutResetCallback(_arg017);
                    reply.writeNoException();
                    return true;
                case 18:
                    int _arg018 = data.readInt();
                    String _arg115 = data.readString();
                    int _arg27 = data.readInt();
                    data.enforceNoDataAvail();
                    setIrisViewType(_arg018, _arg115, _arg27);
                    reply.writeNoException();
                    return true;
                case 19:
                    int _arg019 = data.readInt();
                    String _arg116 = data.readString();
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    enableIRImageCallback(_arg019, _arg116, _arg28);
                    reply.writeNoException();
                    return true;
                case 20:
                    int _arg020 = data.readInt();
                    data.enforceNoDataAvail();
                    setActiveUser(_arg020);
                    reply.writeNoException();
                    return true;
                case 21:
                    boolean _arg021 = data.readBoolean();
                    IBinder _arg117 = data.readStrongBinder();
                    long _arg29 = data.readLong();
                    int _arg37 = data.readInt();
                    IBiometricSensorReceiver _arg46 =
                            IBiometricSensorReceiver.Stub.asInterface(data.readStrongBinder());
                    String _arg55 = data.readString();
                    int _arg65 = data.readInt();
                    int _arg74 = data.readInt();
                    int _arg84 = data.readInt();
                    int _arg94 = data.readInt();
                    Bundle _arg104 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    prepareForAuthentication(
                            _arg021, _arg117, _arg29, _arg37, _arg46, _arg55, _arg65, _arg74,
                            _arg84, _arg94, _arg104);
                    reply.writeNoException();
                    return true;
                case 22:
                    int _arg022 = data.readInt();
                    data.enforceNoDataAvail();
                    startPreparedClient(_arg022);
                    reply.writeNoException();
                    return true;
                case 23:
                    IBinder _arg023 = data.readStrongBinder();
                    String _arg118 = data.readString();
                    int _arg210 = data.readInt();
                    int _arg38 = data.readInt();
                    int _arg47 = data.readInt();
                    boolean _arg56 = data.readBoolean();
                    data.enforceNoDataAvail();
                    cancelAuthenticationFromService(
                            _arg023, _arg118, _arg210, _arg38, _arg47, _arg56);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IIrisService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIrisService.DESCRIPTOR;
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void authenticate(
                    IBinder token,
                    IBinder windownToken,
                    int x,
                    int y,
                    int width,
                    int height,
                    long sessionId,
                    int userId,
                    IIrisServiceReceiver receiver,
                    int flags,
                    String opPackageName,
                    Bundle attr,
                    byte[] fidoRequestData)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeStrongBinder(windownToken);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(x);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(y);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(width);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(height);
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeLong(sessionId);
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(userId);
                    try {
                        _data.writeStrongInterface(receiver);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(flags);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(opPackageName);
                    } catch (Throwable th9) {
                        th = th9;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(attr, 0);
                        _data.writeByteArray(fidoRequestData);
                        this.mRemote.transact(1, _data, _reply, 0);
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

            @Override // com.samsung.android.camera.iris.IIrisService
            public void prompt_authenticate(
                    IBinder token,
                    IBinder windownToken,
                    int x,
                    int y,
                    int width,
                    int height,
                    long sessionId,
                    int userId,
                    IIrisServiceReceiver receiver,
                    int flags,
                    String opPackageName,
                    Bundle attr,
                    byte[] fidoRequestData)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeStrongBinder(windownToken);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(x);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(y);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(width);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(height);
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeLong(sessionId);
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(userId);
                    try {
                        _data.writeStrongInterface(receiver);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(flags);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(opPackageName);
                    } catch (Throwable th9) {
                        th = th9;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(attr, 0);
                        _data.writeByteArray(fidoRequestData);
                        this.mRemote.transact(2, _data, _reply, 0);
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

            @Override // com.samsung.android.camera.iris.IIrisService
            public void cancelAuthentication(IBinder token, String opPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void enroll(
                    IBinder token,
                    IBinder windownToken,
                    int x,
                    int y,
                    int width,
                    int height,
                    byte[] cryptoToken,
                    int groupId,
                    IIrisServiceReceiver receiver,
                    int flags,
                    String opPackageName,
                    Bundle attr)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeStrongBinder(windownToken);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(x);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(y);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(width);
                    try {
                        _data.writeInt(height);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeByteArray(cryptoToken);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(groupId);
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
                try {
                    _data.writeStrongInterface(receiver);
                    try {
                        _data.writeInt(flags);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(opPackageName);
                        try {
                            _data.writeTypedObject(attr, 0);
                        } catch (Throwable th9) {
                            th = th9;
                        }
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(4, _data, _reply, 0);
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th11) {
                        th = th11;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void cancelEnrollment(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void remove(
                    IBinder token,
                    int irisId,
                    int groupId,
                    int userId,
                    IIrisServiceReceiver receiver)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(irisId);
                    _data.writeInt(groupId);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(receiver);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void rename(int irisId, int groupId, String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeInt(irisId);
                    _data.writeInt(groupId);
                    _data.writeString(name);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public List<Iris> getEnrolledIrises(int groupId, String opPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeInt(groupId);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    List<Iris> _result = _reply.createTypedArrayList(Iris.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public boolean isHardwareDetected(long deviceId, String opPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeLong(deviceId);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public long preEnroll(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public int postEnroll(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public boolean hasEnrolledIrises(int groupId, String opPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeInt(groupId);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public boolean hasDisabledIris(int groupId, String opPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeInt(groupId);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public long getAuthenticatorId(String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public int request(
                    IBinder token,
                    int cmd,
                    byte[] inputBuf,
                    byte[] outputBuf,
                    int inParam,
                    int groupId,
                    IIrisServiceReceiver receiver)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(cmd);
                    _data.writeByteArray(inputBuf);
                    _data.writeInt(outputBuf.length);
                    _data.writeInt(inParam);
                    _data.writeInt(groupId);
                    _data.writeStrongInterface(receiver);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(outputBuf);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void resetTimeout(byte[] cryptoToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeByteArray(cryptoToken);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void addLockoutResetCallback(IIrisServiceLockoutResetCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void setIrisViewType(int groupId, String opPackageName, int irisViewType)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeInt(groupId);
                    _data.writeString(opPackageName);
                    _data.writeInt(irisViewType);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void enableIRImageCallback(int groupId, String opPackageName, int callbackType)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeInt(groupId);
                    _data.writeString(opPackageName);
                    _data.writeInt(callbackType);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void setActiveUser(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void prepareForAuthentication(
                    boolean requireConfirmation,
                    IBinder token,
                    long sessionId,
                    int userId,
                    IBiometricSensorReceiver wrapperReceiver,
                    String opPackageName,
                    int cookie,
                    int callingUid,
                    int callingPid,
                    int callingUserId,
                    Bundle bundle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeBoolean(requireConfirmation);
                    _data.writeStrongBinder(token);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeLong(sessionId);
                    try {
                        _data.writeInt(userId);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStrongInterface(wrapperReceiver);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(opPackageName);
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
                try {
                    _data.writeInt(cookie);
                    try {
                        _data.writeInt(callingUid);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(callingPid);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(callingUserId);
                        try {
                            _data.writeTypedObject(bundle, 0);
                            try {
                                this.mRemote.transact(21, _data, _reply, 0);
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

            @Override // com.samsung.android.camera.iris.IIrisService
            public void startPreparedClient(int cookie) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeInt(cookie);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void cancelAuthenticationFromService(
                    IBinder token,
                    String opPackageName,
                    int callingUid,
                    int callingPid,
                    int callingUserId,
                    boolean fromClient)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(opPackageName);
                    _data.writeInt(callingUid);
                    _data.writeInt(callingPid);
                    _data.writeInt(callingUserId);
                    _data.writeBoolean(fromClient);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 22;
        }
    }
}
