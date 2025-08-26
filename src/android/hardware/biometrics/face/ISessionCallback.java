package android.hardware.biometrics.face;

import android.hardware.keymaster.HardwareAuthToken;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$biometrics$face$ISessionCallback".replace('$', '.');
    public static final String HASH = "c43fbb9be4a662cc9ace640dba21cccdb84c6c21";
    public static final int VERSION = 4;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void onAuthenticationFailed() throws RemoteException;

    void onAuthenticationFrame(AuthenticationFrame authenticationFrame) throws RemoteException;

    void onAuthenticationSucceeded(int i, HardwareAuthToken hardwareAuthToken) throws RemoteException;

    void onAuthenticatorIdInvalidated(long j) throws RemoteException;

    void onAuthenticatorIdRetrieved(long j) throws RemoteException;

    void onChallengeGenerated(long j) throws RemoteException;

    void onChallengeRevoked(long j) throws RemoteException;

    void onEnrollmentFrame(EnrollmentFrame enrollmentFrame) throws RemoteException;

    void onEnrollmentProgress(int i, int i2) throws RemoteException;

    void onEnrollmentsEnumerated(int[] iArr) throws RemoteException;

    void onEnrollmentsRemoved(int[] iArr) throws RemoteException;

    void onError(byte b, int i) throws RemoteException;

    void onFeatureSet(byte b) throws RemoteException;

    void onFeaturesRetrieved(byte[] bArr) throws RemoteException;

    void onInteractionDetected() throws RemoteException;

    void onLockoutCleared() throws RemoteException;

    void onLockoutPermanent() throws RemoteException;

    void onLockoutTimed(long j) throws RemoteException;

    void onSessionClosed() throws RemoteException;

    public static class Default implements ISessionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticationFailed() throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticationFrame(AuthenticationFrame authenticationFrame) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticationSucceeded(int i, HardwareAuthToken hardwareAuthToken) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticatorIdInvalidated(long j) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onAuthenticatorIdRetrieved(long j) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onChallengeGenerated(long j) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onChallengeRevoked(long j) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onEnrollmentFrame(EnrollmentFrame enrollmentFrame) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onEnrollmentProgress(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onEnrollmentsEnumerated(int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onEnrollmentsRemoved(int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onError(byte b, int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onFeatureSet(byte b) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onFeaturesRetrieved(byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onInteractionDetected() throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onLockoutCleared() throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onLockoutPermanent() throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onLockoutTimed(long j) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public void onSessionClosed() throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.ISessionCallback
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISessionCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onAuthenticationFailed = 8;
        static final int TRANSACTION_onAuthenticationFrame = 3;
        static final int TRANSACTION_onAuthenticationSucceeded = 7;
        static final int TRANSACTION_onAuthenticatorIdInvalidated = 18;
        static final int TRANSACTION_onAuthenticatorIdRetrieved = 17;
        static final int TRANSACTION_onChallengeGenerated = 1;
        static final int TRANSACTION_onChallengeRevoked = 2;
        static final int TRANSACTION_onEnrollmentFrame = 4;
        static final int TRANSACTION_onEnrollmentProgress = 6;
        static final int TRANSACTION_onEnrollmentsEnumerated = 13;
        static final int TRANSACTION_onEnrollmentsRemoved = 16;
        static final int TRANSACTION_onError = 5;
        static final int TRANSACTION_onFeatureSet = 15;
        static final int TRANSACTION_onFeaturesRetrieved = 14;
        static final int TRANSACTION_onInteractionDetected = 12;
        static final int TRANSACTION_onLockoutCleared = 11;
        static final int TRANSACTION_onLockoutPermanent = 10;
        static final int TRANSACTION_onLockoutTimed = 9;
        static final int TRANSACTION_onSessionClosed = 19;

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

        public static ISessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISessionCallback)) {
                return (ISessionCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onChallengeGenerated";
                case 2:
                    return "onChallengeRevoked";
                case 3:
                    return "onAuthenticationFrame";
                case 4:
                    return "onEnrollmentFrame";
                case 5:
                    return "onError";
                case 6:
                    return "onEnrollmentProgress";
                case 7:
                    return "onAuthenticationSucceeded";
                case 8:
                    return "onAuthenticationFailed";
                case 9:
                    return "onLockoutTimed";
                case 10:
                    return "onLockoutPermanent";
                case 11:
                    return "onLockoutCleared";
                case 12:
                    return "onInteractionDetected";
                case 13:
                    return "onEnrollmentsEnumerated";
                case 14:
                    return "onFeaturesRetrieved";
                case 15:
                    return "onFeatureSet";
                case 16:
                    return "onEnrollmentsRemoved";
                case 17:
                    return "onAuthenticatorIdRetrieved";
                case 18:
                    return "onAuthenticatorIdInvalidated";
                case 19:
                    return "onSessionClosed";
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
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onChallengeGenerated(j);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onChallengeRevoked(j2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    AuthenticationFrame authenticationFrame = (AuthenticationFrame) parcel.readTypedObject(AuthenticationFrame.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAuthenticationFrame(authenticationFrame);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    EnrollmentFrame enrollmentFrame = (EnrollmentFrame) parcel.readTypedObject(EnrollmentFrame.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEnrollmentFrame(enrollmentFrame);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    byte b = parcel.readByte();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(b, i3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollmentProgress(i4, i5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i6 = parcel.readInt();
                    HardwareAuthToken hardwareAuthToken = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAuthenticationSucceeded(i6, hardwareAuthToken);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    onAuthenticationFailed();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onLockoutTimed(j3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    onLockoutPermanent();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    onLockoutCleared();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    onInteractionDetected();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onEnrollmentsEnumerated(iArrCreateIntArray);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onFeaturesRetrieved(bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    byte b2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    onFeatureSet(b2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onEnrollmentsRemoved(iArrCreateIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onAuthenticatorIdRetrieved(j4);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    long j5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onAuthenticatorIdInvalidated(j5);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    onSessionClosed();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISessionCallback {
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

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onChallengeGenerated(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onChallengeGenerated is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onChallengeRevoked(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onChallengeRevoked is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onAuthenticationFrame(AuthenticationFrame authenticationFrame) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(authenticationFrame, 0);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onAuthenticationFrame is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onEnrollmentFrame(EnrollmentFrame enrollmentFrame) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(enrollmentFrame, 0);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onEnrollmentFrame is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onError(byte b, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onError is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onEnrollmentProgress(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onEnrollmentProgress is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onAuthenticationSucceeded(int i, HardwareAuthToken hardwareAuthToken) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(hardwareAuthToken, 0);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onAuthenticationSucceeded is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onAuthenticationFailed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onAuthenticationFailed is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onLockoutTimed(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onLockoutTimed is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onLockoutPermanent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onLockoutPermanent is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onLockoutCleared() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onLockoutCleared is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onInteractionDetected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onInteractionDetected is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onEnrollmentsEnumerated(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(13, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onEnrollmentsEnumerated is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onFeaturesRetrieved(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(14, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onFeaturesRetrieved is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onFeatureSet(byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    if (!this.mRemote.transact(15, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onFeatureSet is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onEnrollmentsRemoved(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(16, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onEnrollmentsRemoved is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onAuthenticatorIdRetrieved(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    if (!this.mRemote.transact(17, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onAuthenticatorIdRetrieved is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onAuthenticatorIdInvalidated(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    if (!this.mRemote.transact(18, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onAuthenticatorIdInvalidated is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
            public void onSessionClosed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(19, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method onSessionClosed is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.ISessionCallback
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

            @Override // android.hardware.biometrics.face.ISessionCallback
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
