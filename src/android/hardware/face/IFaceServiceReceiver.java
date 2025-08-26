package android.hardware.face;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IFaceServiceReceiver extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.face.IFaceServiceReceiver";

    public static class Default implements IFaceServiceReceiver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAcquired(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationFailed() throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationFrame(FaceAuthenticationFrame faceAuthenticationFrame) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onAuthenticationSucceeded(Face face, int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onChallengeGenerated(int i, int i2, long j) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onEnrollResult(Face face, int i) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onEnrollmentFrame(FaceEnrollFrame faceEnrollFrame) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onError(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFaceDetected(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onFeatureSet(boolean z, int i) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onRemoved(Face face, int i) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemAuthenticationSucceeded(Face face, int i, boolean z, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemAuthenticationSucceededWithBundle(Face face, int i, boolean z, Bundle bundle) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemImageProcessed(byte[] bArr, int i, int i2, int i3, int i4, Bundle bundle) throws RemoteException {
        }

        @Override // android.hardware.face.IFaceServiceReceiver
        public void onSemStatusUpdate(int i, String str) throws RemoteException {
        }
    }

    void onAcquired(int i, int i2) throws RemoteException;

    void onAuthenticationFailed() throws RemoteException;

    void onAuthenticationFrame(FaceAuthenticationFrame faceAuthenticationFrame) throws RemoteException;

    void onAuthenticationSucceeded(Face face, int i, boolean z) throws RemoteException;

    void onChallengeGenerated(int i, int i2, long j) throws RemoteException;

    void onEnrollResult(Face face, int i) throws RemoteException;

    void onEnrollmentFrame(FaceEnrollFrame faceEnrollFrame) throws RemoteException;

    void onError(int i, int i2) throws RemoteException;

    void onFaceDetected(int i, int i2, boolean z) throws RemoteException;

    void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) throws RemoteException;

    void onFeatureSet(boolean z, int i) throws RemoteException;

    void onRemoved(Face face, int i) throws RemoteException;

    void onSemAuthenticationSucceeded(Face face, int i, boolean z, byte[] bArr) throws RemoteException;

    void onSemAuthenticationSucceededWithBundle(Face face, int i, boolean z, Bundle bundle) throws RemoteException;

    void onSemImageProcessed(byte[] bArr, int i, int i2, int i3, int i4, Bundle bundle) throws RemoteException;

    void onSemStatusUpdate(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IFaceServiceReceiver {
        static final int TRANSACTION_onAcquired = 2;
        static final int TRANSACTION_onAuthenticationFailed = 5;
        static final int TRANSACTION_onAuthenticationFrame = 11;
        static final int TRANSACTION_onAuthenticationSucceeded = 3;
        static final int TRANSACTION_onChallengeGenerated = 10;
        static final int TRANSACTION_onEnrollResult = 1;
        static final int TRANSACTION_onEnrollmentFrame = 12;
        static final int TRANSACTION_onError = 6;
        static final int TRANSACTION_onFaceDetected = 4;
        static final int TRANSACTION_onFeatureGet = 9;
        static final int TRANSACTION_onFeatureSet = 8;
        static final int TRANSACTION_onRemoved = 7;
        static final int TRANSACTION_onSemAuthenticationSucceeded = 13;
        static final int TRANSACTION_onSemAuthenticationSucceededWithBundle = 14;
        static final int TRANSACTION_onSemImageProcessed = 15;
        static final int TRANSACTION_onSemStatusUpdate = 16;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }

        public Stub() {
            attachInterface(this, IFaceServiceReceiver.DESCRIPTOR);
        }

        public static IFaceServiceReceiver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFaceServiceReceiver.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFaceServiceReceiver)) {
                return (IFaceServiceReceiver) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onEnrollResult";
                case 2:
                    return "onAcquired";
                case 3:
                    return "onAuthenticationSucceeded";
                case 4:
                    return "onFaceDetected";
                case 5:
                    return "onAuthenticationFailed";
                case 6:
                    return "onError";
                case 7:
                    return "onRemoved";
                case 8:
                    return "onFeatureSet";
                case 9:
                    return "onFeatureGet";
                case 10:
                    return "onChallengeGenerated";
                case 11:
                    return "onAuthenticationFrame";
                case 12:
                    return "onEnrollmentFrame";
                case 13:
                    return "onSemAuthenticationSucceeded";
                case 14:
                    return "onSemAuthenticationSucceededWithBundle";
                case 15:
                    return "onSemImageProcessed";
                case 16:
                    return "onSemStatusUpdate";
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
                parcel.enforceInterface(IFaceServiceReceiver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFaceServiceReceiver.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    Face face = (Face) parcel.readTypedObject(Face.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollResult(face, i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAcquired(i4, i5);
                    return true;
                case 3:
                    Face face2 = (Face) parcel.readTypedObject(Face.CREATOR);
                    int i6 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAuthenticationSucceeded(face2, i6, z);
                    return true;
                case 4:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onFaceDetected(i7, i8, z2);
                    return true;
                case 5:
                    onAuthenticationFailed();
                    return true;
                case 6:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(i9, i10);
                    return true;
                case 7:
                    Face face3 = (Face) parcel.readTypedObject(Face.CREATOR);
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRemoved(face3, i11);
                    return true;
                case 8:
                    boolean z3 = parcel.readBoolean();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFeatureSet(z3, i12);
                    return true;
                case 9:
                    boolean z4 = parcel.readBoolean();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    boolean[] zArrCreateBooleanArray = parcel.createBooleanArray();
                    parcel.enforceNoDataAvail();
                    onFeatureGet(z4, iArrCreateIntArray, zArrCreateBooleanArray);
                    return true;
                case 10:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onChallengeGenerated(i13, i14, j);
                    return true;
                case 11:
                    FaceAuthenticationFrame faceAuthenticationFrame = (FaceAuthenticationFrame) parcel.readTypedObject(FaceAuthenticationFrame.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAuthenticationFrame(faceAuthenticationFrame);
                    return true;
                case 12:
                    FaceEnrollFrame faceEnrollFrame = (FaceEnrollFrame) parcel.readTypedObject(FaceEnrollFrame.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEnrollmentFrame(faceEnrollFrame);
                    return true;
                case 13:
                    Face face4 = (Face) parcel.readTypedObject(Face.CREATOR);
                    int i15 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onSemAuthenticationSucceeded(face4, i15, z5, bArrCreateByteArray);
                    return true;
                case 14:
                    Face face5 = (Face) parcel.readTypedObject(Face.CREATOR);
                    int i16 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSemAuthenticationSucceededWithBundle(face5, i16, z6, bundle);
                    return true;
                case 15:
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSemImageProcessed(bArrCreateByteArray2, i17, i18, i19, i20, bundle2);
                    return true;
                case 16:
                    int i21 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onSemStatusUpdate(i21, string);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IFaceServiceReceiver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFaceServiceReceiver.DESCRIPTOR;
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onEnrollResult(Face face, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeTypedObject(face, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onAcquired(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onAuthenticationSucceeded(Face face, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeTypedObject(face, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onFaceDetected(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onAuthenticationFailed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onError(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onRemoved(Face face, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeTypedObject(face, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onFeatureSet(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeBooleanArray(zArr);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onChallengeGenerated(int i, int i2, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onAuthenticationFrame(FaceAuthenticationFrame faceAuthenticationFrame) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeTypedObject(faceAuthenticationFrame, 0);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onEnrollmentFrame(FaceEnrollFrame faceEnrollFrame) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeTypedObject(faceEnrollFrame, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onSemAuthenticationSucceeded(Face face, int i, boolean z, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeTypedObject(face, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onSemAuthenticationSucceededWithBundle(Face face, int i, boolean z, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeTypedObject(face, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onSemImageProcessed(byte[] bArr, int i, int i2, int i3, int i4, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onSemStatusUpdate(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
