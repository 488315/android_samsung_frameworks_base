package android.hardware.biometrics.fingerprint;

import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISession extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$biometrics$fingerprint$ISession".replace('$', '.');
    public static final String HASH = "41a730a7a6b5aa9cebebce70ee5b5e509b0af6fb";
    public static final int VERSION = 4;

    ICancellationSignal authenticate(long j) throws RemoteException;

    ICancellationSignal authenticateWithContext(long j, OperationContext operationContext) throws RemoteException;

    void close() throws RemoteException;

    ICancellationSignal detectInteraction() throws RemoteException;

    ICancellationSignal detectInteractionWithContext(OperationContext operationContext) throws RemoteException;

    ICancellationSignal enroll(HardwareAuthToken hardwareAuthToken) throws RemoteException;

    ICancellationSignal enrollWithContext(HardwareAuthToken hardwareAuthToken, OperationContext operationContext) throws RemoteException;

    void enumerateEnrollments() throws RemoteException;

    void generateChallenge() throws RemoteException;

    void getAuthenticatorId() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void invalidateAuthenticatorId() throws RemoteException;

    void onContextChanged(OperationContext operationContext) throws RemoteException;

    void onPointerCancelWithContext(PointerContext pointerContext) throws RemoteException;

    @Deprecated
    void onPointerDown(int i, int i2, int i3, float f, float f2) throws RemoteException;

    void onPointerDownWithContext(PointerContext pointerContext) throws RemoteException;

    @Deprecated
    void onPointerUp(int i) throws RemoteException;

    void onPointerUpWithContext(PointerContext pointerContext) throws RemoteException;

    void onUiReady() throws RemoteException;

    void removeEnrollments(int[] iArr) throws RemoteException;

    void resetLockout(HardwareAuthToken hardwareAuthToken) throws RemoteException;

    void revokeChallenge(long j) throws RemoteException;

    @Deprecated
    void setIgnoreDisplayTouches(boolean z) throws RemoteException;

    public static class Default implements ISession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public ICancellationSignal authenticate(long j) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public ICancellationSignal authenticateWithContext(long j, OperationContext operationContext) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void close() throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public ICancellationSignal detectInteraction() throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public ICancellationSignal detectInteractionWithContext(OperationContext operationContext) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public ICancellationSignal enroll(HardwareAuthToken hardwareAuthToken) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public ICancellationSignal enrollWithContext(HardwareAuthToken hardwareAuthToken, OperationContext operationContext) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void enumerateEnrollments() throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void generateChallenge() throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void getAuthenticatorId() throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void invalidateAuthenticatorId() throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void onContextChanged(OperationContext operationContext) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void onPointerCancelWithContext(PointerContext pointerContext) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void onPointerDown(int i, int i2, int i3, float f, float f2) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void onPointerDownWithContext(PointerContext pointerContext) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void onPointerUp(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void onPointerUpWithContext(PointerContext pointerContext) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void onUiReady() throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void removeEnrollments(int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void resetLockout(HardwareAuthToken hardwareAuthToken) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void revokeChallenge(long j) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public void setIgnoreDisplayTouches(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.ISession
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISession {
        static final int TRANSACTION_authenticate = 4;
        static final int TRANSACTION_authenticateWithContext = 15;
        static final int TRANSACTION_close = 11;
        static final int TRANSACTION_detectInteraction = 5;
        static final int TRANSACTION_detectInteractionWithContext = 17;
        static final int TRANSACTION_enroll = 3;
        static final int TRANSACTION_enrollWithContext = 16;
        static final int TRANSACTION_enumerateEnrollments = 6;
        static final int TRANSACTION_generateChallenge = 1;
        static final int TRANSACTION_getAuthenticatorId = 8;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_invalidateAuthenticatorId = 9;
        static final int TRANSACTION_onContextChanged = 20;
        static final int TRANSACTION_onPointerCancelWithContext = 21;
        static final int TRANSACTION_onPointerDown = 12;
        static final int TRANSACTION_onPointerDownWithContext = 18;
        static final int TRANSACTION_onPointerUp = 13;
        static final int TRANSACTION_onPointerUpWithContext = 19;
        static final int TRANSACTION_onUiReady = 14;
        static final int TRANSACTION_removeEnrollments = 7;
        static final int TRANSACTION_resetLockout = 10;
        static final int TRANSACTION_revokeChallenge = 2;
        static final int TRANSACTION_setIgnoreDisplayTouches = 22;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISession)) {
                return (ISession) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "generateChallenge";
                case 2:
                    return "revokeChallenge";
                case 3:
                    return "enroll";
                case 4:
                    return "authenticate";
                case 5:
                    return "detectInteraction";
                case 6:
                    return "enumerateEnrollments";
                case 7:
                    return "removeEnrollments";
                case 8:
                    return "getAuthenticatorId";
                case 9:
                    return "invalidateAuthenticatorId";
                case 10:
                    return "resetLockout";
                case 11:
                    return "close";
                case 12:
                    return "onPointerDown";
                case 13:
                    return "onPointerUp";
                case 14:
                    return "onUiReady";
                case 15:
                    return "authenticateWithContext";
                case 16:
                    return "enrollWithContext";
                case 17:
                    return "detectInteractionWithContext";
                case 18:
                    return "onPointerDownWithContext";
                case 19:
                    return "onPointerUpWithContext";
                case 20:
                    return "onContextChanged";
                case 21:
                    return "onPointerCancelWithContext";
                case 22:
                    return "setIgnoreDisplayTouches";
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

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    generateChallenge();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    revokeChallenge(j);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    HardwareAuthToken hardwareAuthToken = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    ICancellationSignal iCancellationSignalEnroll = enroll(hardwareAuthToken);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCancellationSignalEnroll);
                    return true;
                case 4:
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    ICancellationSignal iCancellationSignalAuthenticate = authenticate(j2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCancellationSignalAuthenticate);
                    return true;
                case 5:
                    ICancellationSignal iCancellationSignalDetectInteraction = detectInteraction();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCancellationSignalDetectInteraction);
                    return true;
                case 6:
                    enumerateEnrollments();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    removeEnrollments(iArrCreateIntArray);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    getAuthenticatorId();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    invalidateAuthenticatorId();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    HardwareAuthToken hardwareAuthToken2 = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    resetLockout(hardwareAuthToken2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    float f = parcel.readFloat();
                    float f2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onPointerDown(i3, i4, i5, f, f2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPointerUp(i6);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    onUiReady();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    long j3 = parcel.readLong();
                    OperationContext operationContext = (OperationContext) parcel.readTypedObject(OperationContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    ICancellationSignal iCancellationSignalAuthenticateWithContext = authenticateWithContext(j3, operationContext);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCancellationSignalAuthenticateWithContext);
                    return true;
                case 16:
                    HardwareAuthToken hardwareAuthToken3 = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                    OperationContext operationContext2 = (OperationContext) parcel.readTypedObject(OperationContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    ICancellationSignal iCancellationSignalEnrollWithContext = enrollWithContext(hardwareAuthToken3, operationContext2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCancellationSignalEnrollWithContext);
                    return true;
                case 17:
                    OperationContext operationContext3 = (OperationContext) parcel.readTypedObject(OperationContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    ICancellationSignal iCancellationSignalDetectInteractionWithContext = detectInteractionWithContext(operationContext3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCancellationSignalDetectInteractionWithContext);
                    return true;
                case 18:
                    PointerContext pointerContext = (PointerContext) parcel.readTypedObject(PointerContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPointerDownWithContext(pointerContext);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    PointerContext pointerContext2 = (PointerContext) parcel.readTypedObject(PointerContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPointerUpWithContext(pointerContext2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    OperationContext operationContext4 = (OperationContext) parcel.readTypedObject(OperationContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onContextChanged(operationContext4);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    PointerContext pointerContext3 = (PointerContext) parcel.readTypedObject(PointerContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPointerCancelWithContext(pointerContext3);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setIgnoreDisplayTouches(z);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISession {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void generateChallenge() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method generateChallenge is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void revokeChallenge(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method revokeChallenge is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public ICancellationSignal enroll(HardwareAuthToken hardwareAuthToken) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(hardwareAuthToken, 0);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method enroll is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public ICancellationSignal authenticate(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method authenticate is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public ICancellationSignal detectInteraction() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method detectInteraction is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void enumerateEnrollments() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method enumerateEnrollments is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void removeEnrollments(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method removeEnrollments is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void getAuthenticatorId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getAuthenticatorId is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void invalidateAuthenticatorId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method invalidateAuthenticatorId is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void resetLockout(HardwareAuthToken hardwareAuthToken) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(hardwareAuthToken, 0);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method resetLockout is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void onPointerDown(int i, int i2, int i3, float f, float f2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onPointerDown is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void onPointerUp(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(13, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onPointerUp is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void onUiReady() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(14, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onUiReady is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public ICancellationSignal authenticateWithContext(long j, OperationContext operationContext) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(15, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method authenticateWithContext is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public ICancellationSignal enrollWithContext(HardwareAuthToken hardwareAuthToken, OperationContext operationContext) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(hardwareAuthToken, 0);
                    parcelObtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(16, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method enrollWithContext is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public ICancellationSignal detectInteractionWithContext(OperationContext operationContext) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(17, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method detectInteractionWithContext is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void onPointerDownWithContext(PointerContext pointerContext) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(pointerContext, 0);
                    if (!this.mRemote.transact(18, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onPointerDownWithContext is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void onPointerUpWithContext(PointerContext pointerContext) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(pointerContext, 0);
                    if (!this.mRemote.transact(19, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onPointerUpWithContext is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void onContextChanged(OperationContext operationContext) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(operationContext, 0);
                    if (!this.mRemote.transact(20, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onContextChanged is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void onPointerCancelWithContext(PointerContext pointerContext) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(pointerContext, 0);
                    if (!this.mRemote.transact(21, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onPointerCancelWithContext is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public void setIgnoreDisplayTouches(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    if (!this.mRemote.transact(22, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setIgnoreDisplayTouches is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.biometrics.fingerprint.ISession
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
