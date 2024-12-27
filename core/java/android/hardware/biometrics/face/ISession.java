package android.hardware.biometrics.face;

import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.common.NativeHandle;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISession extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$biometrics$face$ISession".replace('$', '.');
    public static final String HASH = "c43fbb9be4a662cc9ace640dba21cccdb84c6c21";
    public static final int VERSION = 4;

    ICancellationSignal authenticate(long j) throws RemoteException;

    ICancellationSignal authenticateWithContext(long j, OperationContext operationContext) throws RemoteException;

    void close() throws RemoteException;

    ICancellationSignal detectInteraction() throws RemoteException;

    ICancellationSignal detectInteractionWithContext(OperationContext operationContext) throws RemoteException;

    @Deprecated
    ICancellationSignal enroll(HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, NativeHandle nativeHandle) throws RemoteException;

    @Deprecated
    ICancellationSignal enrollWithContext(HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, NativeHandle nativeHandle, OperationContext operationContext) throws RemoteException;

    ICancellationSignal enrollWithOptions(FaceEnrollOptions faceEnrollOptions) throws RemoteException;

    void enumerateEnrollments() throws RemoteException;

    void generateChallenge() throws RemoteException;

    void getAuthenticatorId() throws RemoteException;

    EnrollmentStageConfig[] getEnrollmentConfig(byte b) throws RemoteException;

    void getFeatures() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void invalidateAuthenticatorId() throws RemoteException;

    void onContextChanged(OperationContext operationContext) throws RemoteException;

    void removeEnrollments(int[] iArr) throws RemoteException;

    void resetLockout(HardwareAuthToken hardwareAuthToken) throws RemoteException;

    void revokeChallenge(long j) throws RemoteException;

    void setFeature(HardwareAuthToken hardwareAuthToken, byte b, boolean z) throws RemoteException;

    public static class Default implements ISession {
        @Override // android.hardware.biometrics.face.ISession
        public void generateChallenge() throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void revokeChallenge(long challenge) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public EnrollmentStageConfig[] getEnrollmentConfig(byte enrollmentType) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public ICancellationSignal enroll(HardwareAuthToken hat, byte type, byte[] features, NativeHandle previewSurface) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public ICancellationSignal authenticate(long operationId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public ICancellationSignal detectInteraction() throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public void enumerateEnrollments() throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void removeEnrollments(int[] enrollmentIds) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void getFeatures() throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void setFeature(HardwareAuthToken hat, byte feature, boolean enabled) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void getAuthenticatorId() throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void invalidateAuthenticatorId() throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void resetLockout(HardwareAuthToken hat) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void close() throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public ICancellationSignal authenticateWithContext(long operationId, OperationContext context) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public ICancellationSignal enrollWithContext(HardwareAuthToken hat, byte type, byte[] features, NativeHandle previewSurface, OperationContext context) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public ICancellationSignal detectInteractionWithContext(OperationContext context) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public void onContextChanged(OperationContext context) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISession
        public ICancellationSignal enrollWithOptions(FaceEnrollOptions options) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.biometrics.face.ISession
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISession {
        static final int TRANSACTION_authenticate = 5;
        static final int TRANSACTION_authenticateWithContext = 15;
        static final int TRANSACTION_close = 14;
        static final int TRANSACTION_detectInteraction = 6;
        static final int TRANSACTION_detectInteractionWithContext = 17;
        static final int TRANSACTION_enroll = 4;
        static final int TRANSACTION_enrollWithContext = 16;
        static final int TRANSACTION_enrollWithOptions = 19;
        static final int TRANSACTION_enumerateEnrollments = 7;
        static final int TRANSACTION_generateChallenge = 1;
        static final int TRANSACTION_getAuthenticatorId = 11;
        static final int TRANSACTION_getEnrollmentConfig = 3;
        static final int TRANSACTION_getFeatures = 9;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_invalidateAuthenticatorId = 12;
        static final int TRANSACTION_onContextChanged = 18;
        static final int TRANSACTION_removeEnrollments = 8;
        static final int TRANSACTION_resetLockout = 13;
        static final int TRANSACTION_revokeChallenge = 2;
        static final int TRANSACTION_setFeature = 10;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISession)) {
                return (ISession) iin;
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
                    return "generateChallenge";
                case 2:
                    return "revokeChallenge";
                case 3:
                    return "getEnrollmentConfig";
                case 4:
                    return "enroll";
                case 5:
                    return "authenticate";
                case 6:
                    return "detectInteraction";
                case 7:
                    return "enumerateEnrollments";
                case 8:
                    return "removeEnrollments";
                case 9:
                    return "getFeatures";
                case 10:
                    return "setFeature";
                case 11:
                    return "getAuthenticatorId";
                case 12:
                    return "invalidateAuthenticatorId";
                case 13:
                    return "resetLockout";
                case 14:
                    return "close";
                case 15:
                    return "authenticateWithContext";
                case 16:
                    return "enrollWithContext";
                case 17:
                    return "detectInteractionWithContext";
                case 18:
                    return "onContextChanged";
                case 19:
                    return "enrollWithOptions";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            if (code == 1598968902) {
                reply.writeString(descriptor);
                return true;
            }
            if (code == 16777215) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == 16777214) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            switch (code) {
                case 1:
                    generateChallenge();
                    reply.writeNoException();
                    return true;
                case 2:
                    long _arg0 = data.readLong();
                    data.enforceNoDataAvail();
                    revokeChallenge(_arg0);
                    reply.writeNoException();
                    return true;
                case 3:
                    byte _arg02 = data.readByte();
                    data.enforceNoDataAvail();
                    EnrollmentStageConfig[] _result = getEnrollmentConfig(_arg02);
                    reply.writeNoException();
                    reply.writeTypedArray(_result, 1);
                    return true;
                case 4:
                    HardwareAuthToken _arg03 = (HardwareAuthToken) data.readTypedObject(HardwareAuthToken.CREATOR);
                    byte _arg1 = data.readByte();
                    byte[] _arg2 = data.createByteArray();
                    NativeHandle _arg3 = (NativeHandle) data.readTypedObject(NativeHandle.CREATOR);
                    data.enforceNoDataAvail();
                    ICancellationSignal _result2 = enroll(_arg03, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result2);
                    return true;
                case 5:
                    long _arg04 = data.readLong();
                    data.enforceNoDataAvail();
                    ICancellationSignal _result3 = authenticate(_arg04);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result3);
                    return true;
                case 6:
                    ICancellationSignal _result4 = detectInteraction();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result4);
                    return true;
                case 7:
                    enumerateEnrollments();
                    reply.writeNoException();
                    return true;
                case 8:
                    int[] _arg05 = data.createIntArray();
                    data.enforceNoDataAvail();
                    removeEnrollments(_arg05);
                    reply.writeNoException();
                    return true;
                case 9:
                    getFeatures();
                    reply.writeNoException();
                    return true;
                case 10:
                    HardwareAuthToken _arg06 = (HardwareAuthToken) data.readTypedObject(HardwareAuthToken.CREATOR);
                    byte _arg12 = data.readByte();
                    boolean _arg22 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setFeature(_arg06, _arg12, _arg22);
                    reply.writeNoException();
                    return true;
                case 11:
                    getAuthenticatorId();
                    reply.writeNoException();
                    return true;
                case 12:
                    invalidateAuthenticatorId();
                    reply.writeNoException();
                    return true;
                case 13:
                    HardwareAuthToken _arg07 = (HardwareAuthToken) data.readTypedObject(HardwareAuthToken.CREATOR);
                    data.enforceNoDataAvail();
                    resetLockout(_arg07);
                    reply.writeNoException();
                    return true;
                case 14:
                    close();
                    reply.writeNoException();
                    return true;
                case 15:
                    long _arg08 = data.readLong();
                    OperationContext _arg13 = (OperationContext) data.readTypedObject(OperationContext.CREATOR);
                    data.enforceNoDataAvail();
                    ICancellationSignal _result5 = authenticateWithContext(_arg08, _arg13);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result5);
                    return true;
                case 16:
                    HardwareAuthToken _arg09 = (HardwareAuthToken) data.readTypedObject(HardwareAuthToken.CREATOR);
                    byte _arg14 = data.readByte();
                    byte[] _arg23 = data.createByteArray();
                    NativeHandle _arg32 = (NativeHandle) data.readTypedObject(NativeHandle.CREATOR);
                    OperationContext _arg4 = (OperationContext) data.readTypedObject(OperationContext.CREATOR);
                    data.enforceNoDataAvail();
                    ICancellationSignal _result6 = enrollWithContext(_arg09, _arg14, _arg23, _arg32, _arg4);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result6);
                    return true;
                case 17:
                    OperationContext _arg010 = (OperationContext) data.readTypedObject(OperationContext.CREATOR);
                    data.enforceNoDataAvail();
                    ICancellationSignal _result7 = detectInteractionWithContext(_arg010);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result7);
                    return true;
                case 18:
                    OperationContext _arg011 = (OperationContext) data.readTypedObject(OperationContext.CREATOR);
                    data.enforceNoDataAvail();
                    onContextChanged(_arg011);
                    reply.writeNoException();
                    return true;
                case 19:
                    FaceEnrollOptions _arg012 = (FaceEnrollOptions) data.readTypedObject(FaceEnrollOptions.CREATOR);
                    data.enforceNoDataAvail();
                    ICancellationSignal _result8 = enrollWithOptions(_arg012);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result8);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISession {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.face.ISession
            public void generateChallenge() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method generateChallenge is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void revokeChallenge(long challenge) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeLong(challenge);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method revokeChallenge is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public EnrollmentStageConfig[] getEnrollmentConfig(byte enrollmentType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(enrollmentType);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getEnrollmentConfig is unimplemented.");
                    }
                    _reply.readException();
                    EnrollmentStageConfig[] _result = (EnrollmentStageConfig[]) _reply.createTypedArray(EnrollmentStageConfig.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal enroll(HardwareAuthToken hat, byte type, byte[] features, NativeHandle previewSurface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(hat, 0);
                    _data.writeByte(type);
                    _data.writeByteArray(features);
                    _data.writeTypedObject(previewSurface, 0);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method enroll is unimplemented.");
                    }
                    _reply.readException();
                    ICancellationSignal _result = ICancellationSignal.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal authenticate(long operationId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeLong(operationId);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method authenticate is unimplemented.");
                    }
                    _reply.readException();
                    ICancellationSignal _result = ICancellationSignal.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal detectInteraction() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method detectInteraction is unimplemented.");
                    }
                    _reply.readException();
                    ICancellationSignal _result = ICancellationSignal.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void enumerateEnrollments() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method enumerateEnrollments is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void removeEnrollments(int[] enrollmentIds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeIntArray(enrollmentIds);
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method removeEnrollments is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void getFeatures() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getFeatures is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void setFeature(HardwareAuthToken hat, byte feature, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(hat, 0);
                    _data.writeByte(feature);
                    _data.writeBoolean(enabled);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setFeature is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void getAuthenticatorId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method getAuthenticatorId is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void invalidateAuthenticatorId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method invalidateAuthenticatorId is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void resetLockout(HardwareAuthToken hat) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(hat, 0);
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method resetLockout is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void close() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal authenticateWithContext(long operationId, OperationContext context) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeLong(operationId);
                    _data.writeTypedObject(context, 0);
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method authenticateWithContext is unimplemented.");
                    }
                    _reply.readException();
                    ICancellationSignal _result = ICancellationSignal.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal enrollWithContext(HardwareAuthToken hat, byte type, byte[] features, NativeHandle previewSurface, OperationContext context) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(hat, 0);
                    _data.writeByte(type);
                    _data.writeByteArray(features);
                    _data.writeTypedObject(previewSurface, 0);
                    _data.writeTypedObject(context, 0);
                    boolean _status = this.mRemote.transact(16, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method enrollWithContext is unimplemented.");
                    }
                    _reply.readException();
                    ICancellationSignal _result = ICancellationSignal.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal detectInteractionWithContext(OperationContext context) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(context, 0);
                    boolean _status = this.mRemote.transact(17, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method detectInteractionWithContext is unimplemented.");
                    }
                    _reply.readException();
                    ICancellationSignal _result = ICancellationSignal.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void onContextChanged(OperationContext context) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(context, 0);
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method onContextChanged is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal enrollWithOptions(FaceEnrollOptions options) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(options, 0);
                    boolean _status = this.mRemote.transact(19, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method enrollWithOptions is unimplemented.");
                    }
                    _reply.readException();
                    ICancellationSignal _result = ICancellationSignal.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.biometrics.face.ISession
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, data, reply, 0);
                        reply.readException();
                        this.mCachedHash = reply.readString();
                        reply.recycle();
                        data.recycle();
                    } catch (Throwable th) {
                        reply.recycle();
                        data.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }
}
