package android.service.voice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IDetectorSessionVisualQueryDetectionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.voice.IDetectorSessionVisualQueryDetectionCallback";

    public static class Default implements IDetectorSessionVisualQueryDetectionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
        public void onAttentionGained(VisualQueryAttentionResult visualQueryAttentionResult) throws RemoteException {
        }

        @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
        public void onAttentionLost(int i) throws RemoteException {
        }

        @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
        public void onQueryDetected(String str) throws RemoteException {
        }

        @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
        public void onQueryFinished() throws RemoteException {
        }

        @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
        public void onQueryRejected() throws RemoteException {
        }

        @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
        public void onResultDetected(VisualQueryDetectedResult visualQueryDetectedResult) throws RemoteException {
        }
    }

    void onAttentionGained(VisualQueryAttentionResult visualQueryAttentionResult) throws RemoteException;

    void onAttentionLost(int i) throws RemoteException;

    void onQueryDetected(String str) throws RemoteException;

    void onQueryFinished() throws RemoteException;

    void onQueryRejected() throws RemoteException;

    void onResultDetected(VisualQueryDetectedResult visualQueryDetectedResult) throws RemoteException;

    public static abstract class Stub extends Binder implements IDetectorSessionVisualQueryDetectionCallback {
        static final int TRANSACTION_onAttentionGained = 1;
        static final int TRANSACTION_onAttentionLost = 2;
        static final int TRANSACTION_onQueryDetected = 3;
        static final int TRANSACTION_onQueryFinished = 5;
        static final int TRANSACTION_onQueryRejected = 6;
        static final int TRANSACTION_onResultDetected = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
        }

        public static IDetectorSessionVisualQueryDetectionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDetectorSessionVisualQueryDetectionCallback)) {
                return (IDetectorSessionVisualQueryDetectionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAttentionGained";
                case 2:
                    return "onAttentionLost";
                case 3:
                    return "onQueryDetected";
                case 4:
                    return "onResultDetected";
                case 5:
                    return "onQueryFinished";
                case 6:
                    return "onQueryRejected";
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
                parcel.enforceInterface(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    VisualQueryAttentionResult visualQueryAttentionResult = (VisualQueryAttentionResult) parcel.readTypedObject(VisualQueryAttentionResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAttentionGained(visualQueryAttentionResult);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAttentionLost(readInt);
                    return true;
                case 3:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onQueryDetected(readString);
                    return true;
                case 4:
                    VisualQueryDetectedResult visualQueryDetectedResult = (VisualQueryDetectedResult) parcel.readTypedObject(VisualQueryDetectedResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResultDetected(visualQueryDetectedResult);
                    return true;
                case 5:
                    onQueryFinished();
                    return true;
                case 6:
                    onQueryRejected();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDetectorSessionVisualQueryDetectionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR;
            }

            @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
            public void onAttentionGained(VisualQueryAttentionResult visualQueryAttentionResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(visualQueryAttentionResult, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
            public void onAttentionLost(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
            public void onQueryDetected(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
            public void onResultDetected(VisualQueryDetectedResult visualQueryDetectedResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(visualQueryDetectedResult, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
            public void onQueryFinished() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IDetectorSessionVisualQueryDetectionCallback
            public void onQueryRejected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDetectorSessionVisualQueryDetectionCallback.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
