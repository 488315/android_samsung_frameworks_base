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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IFaceServiceReceiver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFaceServiceReceiver)) {
                return (IFaceServiceReceiver) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollResult(face, readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAcquired(readInt2, readInt3);
                    return true;
                case 3:
                    Face face2 = (Face) parcel.readTypedObject(Face.CREATOR);
                    int readInt4 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAuthenticationSucceeded(face2, readInt4, readBoolean);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onFaceDetected(readInt5, readInt6, readBoolean2);
                    return true;
                case 5:
                    onAuthenticationFailed();
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readInt7, readInt8);
                    return true;
                case 7:
                    Face face3 = (Face) parcel.readTypedObject(Face.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRemoved(face3, readInt9);
                    return true;
                case 8:
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFeatureSet(readBoolean3, readInt10);
                    return true;
                case 9:
                    boolean readBoolean4 = parcel.readBoolean();
                    int[] createIntArray = parcel.createIntArray();
                    boolean[] createBooleanArray = parcel.createBooleanArray();
                    parcel.enforceNoDataAvail();
                    onFeatureGet(readBoolean4, createIntArray, createBooleanArray);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onChallengeGenerated(readInt11, readInt12, readLong);
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
                    int readInt13 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onSemAuthenticationSucceeded(face4, readInt13, readBoolean5, createByteArray);
                    return true;
                case 14:
                    Face face5 = (Face) parcel.readTypedObject(Face.CREATOR);
                    int readInt14 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSemAuthenticationSucceededWithBundle(face5, readInt14, readBoolean6, bundle);
                    return true;
                case 15:
                    byte[] createByteArray2 = parcel.createByteArray();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSemImageProcessed(createByteArray2, readInt15, readInt16, readInt17, readInt18, bundle2);
                    return true;
                case 16:
                    int readInt19 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onSemStatusUpdate(readInt19, readString);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeTypedObject(face, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onAcquired(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onAuthenticationSucceeded(Face face, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeTypedObject(face, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onFaceDetected(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onAuthenticationFailed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onError(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onRemoved(Face face, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeTypedObject(face, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onFeatureSet(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeIntArray(iArr);
                    obtain.writeBooleanArray(zArr);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onChallengeGenerated(int i, int i2, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onAuthenticationFrame(FaceAuthenticationFrame faceAuthenticationFrame) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeTypedObject(faceAuthenticationFrame, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onEnrollmentFrame(FaceEnrollFrame faceEnrollFrame) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeTypedObject(faceEnrollFrame, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onSemAuthenticationSucceeded(Face face, int i, boolean z, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeTypedObject(face, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onSemAuthenticationSucceededWithBundle(Face face, int i, boolean z, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeTypedObject(face, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onSemImageProcessed(byte[] bArr, int i, int i2, int i3, int i4, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.face.IFaceServiceReceiver
            public void onSemStatusUpdate(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFaceServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
