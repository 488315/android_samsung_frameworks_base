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

/* loaded from: classes.dex */
public interface ISession extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$biometrics$face$ISession".replace('$', '.');

    public class Default implements ISession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public ICancellationSignal authenticate(long j) {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public ICancellationSignal authenticateWithContext(long j, OperationContext operationContext) {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public void close() {
        }

        @Override // android.hardware.biometrics.face.ISession
        public ICancellationSignal detectInteraction() {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public ICancellationSignal detectInteractionWithContext(OperationContext operationContext) {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public ICancellationSignal enroll(HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, NativeHandle nativeHandle) {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public ICancellationSignal enrollWithContext(HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, NativeHandle nativeHandle, OperationContext operationContext) {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISession
        public void enumerateEnrollments() {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void generateChallenge() {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void getAuthenticatorId() {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void getFeatures() {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void invalidateAuthenticatorId() {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void onContextChanged(OperationContext operationContext) {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void removeEnrollments(int[] iArr) {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void revokeChallenge(long j) {
        }

        @Override // android.hardware.biometrics.face.ISession
        public void setFeature(HardwareAuthToken hardwareAuthToken, byte b, boolean z) {
        }
    }

    ICancellationSignal authenticate(long j);

    ICancellationSignal authenticateWithContext(long j, OperationContext operationContext);

    void close();

    ICancellationSignal detectInteraction();

    ICancellationSignal detectInteractionWithContext(OperationContext operationContext);

    ICancellationSignal enroll(HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, NativeHandle nativeHandle);

    ICancellationSignal enrollWithContext(HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, NativeHandle nativeHandle, OperationContext operationContext);

    void enumerateEnrollments();

    void generateChallenge();

    void getAuthenticatorId();

    EnrollmentStageConfig[] getEnrollmentConfig(byte b);

    void getFeatures();

    String getInterfaceHash();

    int getInterfaceVersion();

    void invalidateAuthenticatorId();

    void onContextChanged(OperationContext operationContext);

    void removeEnrollments(int[] iArr);

    void resetLockout(HardwareAuthToken hardwareAuthToken);

    void revokeChallenge(long j);

    void setFeature(HardwareAuthToken hardwareAuthToken, byte b, boolean z);

    public abstract class Stub extends Binder implements ISession {
        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                default:
                    switch (i) {
                        case 16777214:
                            return "getInterfaceHash";
                        case 16777215:
                            return "getInterfaceVersion";
                        default:
                            return null;
                    }
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, ISession.DESCRIPTOR);
        }

        public static ISession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISession)) {
                return (ISession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ISession.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            switch (i) {
                case 16777214:
                    parcel2.writeNoException();
                    parcel2.writeString(getInterfaceHash());
                    return true;
                case 16777215:
                    parcel2.writeNoException();
                    parcel2.writeInt(getInterfaceVersion());
                    return true;
                case 1598968902:
                    parcel2.writeString(str);
                    return true;
                default:
                    switch (i) {
                        case 1:
                            generateChallenge();
                            parcel2.writeNoException();
                            return true;
                        case 2:
                            long readLong = parcel.readLong();
                            parcel.enforceNoDataAvail();
                            revokeChallenge(readLong);
                            parcel2.writeNoException();
                            return true;
                        case 3:
                            byte readByte = parcel.readByte();
                            parcel.enforceNoDataAvail();
                            EnrollmentStageConfig[] enrollmentConfig = getEnrollmentConfig(readByte);
                            parcel2.writeNoException();
                            parcel2.writeTypedArray(enrollmentConfig, 1);
                            return true;
                        case 4:
                            HardwareAuthToken hardwareAuthToken = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                            byte readByte2 = parcel.readByte();
                            byte[] createByteArray = parcel.createByteArray();
                            NativeHandle nativeHandle = (NativeHandle) parcel.readTypedObject(NativeHandle.CREATOR);
                            parcel.enforceNoDataAvail();
                            ICancellationSignal enroll = enroll(hardwareAuthToken, readByte2, createByteArray, nativeHandle);
                            parcel2.writeNoException();
                            parcel2.writeStrongInterface(enroll);
                            return true;
                        case 5:
                            long readLong2 = parcel.readLong();
                            parcel.enforceNoDataAvail();
                            ICancellationSignal authenticate = authenticate(readLong2);
                            parcel2.writeNoException();
                            parcel2.writeStrongInterface(authenticate);
                            return true;
                        case 6:
                            ICancellationSignal detectInteraction = detectInteraction();
                            parcel2.writeNoException();
                            parcel2.writeStrongInterface(detectInteraction);
                            return true;
                        case 7:
                            enumerateEnrollments();
                            parcel2.writeNoException();
                            return true;
                        case 8:
                            int[] createIntArray = parcel.createIntArray();
                            parcel.enforceNoDataAvail();
                            removeEnrollments(createIntArray);
                            parcel2.writeNoException();
                            return true;
                        case 9:
                            getFeatures();
                            parcel2.writeNoException();
                            return true;
                        case 10:
                            HardwareAuthToken hardwareAuthToken2 = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                            byte readByte3 = parcel.readByte();
                            boolean readBoolean = parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            setFeature(hardwareAuthToken2, readByte3, readBoolean);
                            parcel2.writeNoException();
                            return true;
                        case 11:
                            getAuthenticatorId();
                            parcel2.writeNoException();
                            return true;
                        case 12:
                            invalidateAuthenticatorId();
                            parcel2.writeNoException();
                            return true;
                        case 13:
                            HardwareAuthToken hardwareAuthToken3 = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                            parcel.enforceNoDataAvail();
                            resetLockout(hardwareAuthToken3);
                            parcel2.writeNoException();
                            return true;
                        case 14:
                            close();
                            parcel2.writeNoException();
                            return true;
                        case 15:
                            long readLong3 = parcel.readLong();
                            OperationContext operationContext = (OperationContext) parcel.readTypedObject(OperationContext.CREATOR);
                            parcel.enforceNoDataAvail();
                            ICancellationSignal authenticateWithContext = authenticateWithContext(readLong3, operationContext);
                            parcel2.writeNoException();
                            parcel2.writeStrongInterface(authenticateWithContext);
                            return true;
                        case 16:
                            HardwareAuthToken hardwareAuthToken4 = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                            byte readByte4 = parcel.readByte();
                            byte[] createByteArray2 = parcel.createByteArray();
                            NativeHandle nativeHandle2 = (NativeHandle) parcel.readTypedObject(NativeHandle.CREATOR);
                            OperationContext operationContext2 = (OperationContext) parcel.readTypedObject(OperationContext.CREATOR);
                            parcel.enforceNoDataAvail();
                            ICancellationSignal enrollWithContext = enrollWithContext(hardwareAuthToken4, readByte4, createByteArray2, nativeHandle2, operationContext2);
                            parcel2.writeNoException();
                            parcel2.writeStrongInterface(enrollWithContext);
                            return true;
                        case 17:
                            OperationContext operationContext3 = (OperationContext) parcel.readTypedObject(OperationContext.CREATOR);
                            parcel.enforceNoDataAvail();
                            ICancellationSignal detectInteractionWithContext = detectInteractionWithContext(operationContext3);
                            parcel2.writeNoException();
                            parcel2.writeStrongInterface(detectInteractionWithContext);
                            return true;
                        case 18:
                            OperationContext operationContext4 = (OperationContext) parcel.readTypedObject(OperationContext.CREATOR);
                            parcel.enforceNoDataAvail();
                            onContextChanged(operationContext4);
                            parcel2.writeNoException();
                            return true;
                        default:
                            return super.onTransact(i, parcel, parcel2, i2);
                    }
            }
        }

        public class Proxy implements ISession {
            public IBinder mRemote;
            public int mCachedVersion = -1;
            public String mCachedHash = "-1";

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.hardware.biometrics.face.ISession
            public void generateChallenge() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method generateChallenge is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void revokeChallenge(long j) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method revokeChallenge is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal enroll(HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, NativeHandle nativeHandle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    obtain.writeByte(b);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(nativeHandle, 0);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method enroll is unimplemented.");
                    }
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal authenticate(long j) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method authenticate is unimplemented.");
                    }
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal detectInteraction() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method detectInteraction is unimplemented.");
                    }
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void enumerateEnrollments() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method enumerateEnrollments is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void removeEnrollments(int[] iArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method removeEnrollments is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void getFeatures() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getFeatures is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void setFeature(HardwareAuthToken hardwareAuthToken, byte b, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    obtain.writeByte(b);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setFeature is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void getAuthenticatorId() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getAuthenticatorId is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void invalidateAuthenticatorId() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new RemoteException("Method invalidateAuthenticatorId is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void close() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal authenticateWithContext(long j, OperationContext operationContext) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new RemoteException("Method authenticateWithContext is unimplemented.");
                    }
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal enrollWithContext(HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, NativeHandle nativeHandle, OperationContext operationContext) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    obtain.writeTypedObject(hardwareAuthToken, 0);
                    obtain.writeByte(b);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(nativeHandle, 0);
                    obtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0)) {
                        throw new RemoteException("Method enrollWithContext is unimplemented.");
                    }
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public ICancellationSignal detectInteractionWithContext(OperationContext operationContext) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    obtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0)) {
                        throw new RemoteException("Method detectInteractionWithContext is unimplemented.");
                    }
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISession
            public void onContextChanged(OperationContext operationContext) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    obtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(18, obtain, obtain2, 0)) {
                        throw new RemoteException("Method onContextChanged is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
