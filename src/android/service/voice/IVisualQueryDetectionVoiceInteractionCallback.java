package android.service.voice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IVisualQueryDetectionVoiceInteractionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.voice.IVisualQueryDetectionVoiceInteractionCallback";

    public static class Default implements IVisualQueryDetectionVoiceInteractionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryDetected(String str) throws RemoteException {
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryFinished() throws RemoteException {
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryRejected() throws RemoteException {
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onResultDetected(VisualQueryDetectedResult visualQueryDetectedResult) throws RemoteException {
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onVisualQueryDetectionServiceFailure(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws RemoteException {
        }
    }

    void onQueryDetected(String str) throws RemoteException;

    void onQueryFinished() throws RemoteException;

    void onQueryRejected() throws RemoteException;

    void onResultDetected(VisualQueryDetectedResult visualQueryDetectedResult) throws RemoteException;

    void onVisualQueryDetectionServiceFailure(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws RemoteException;

    public static abstract class Stub extends Binder implements IVisualQueryDetectionVoiceInteractionCallback {
        static final int TRANSACTION_onQueryDetected = 1;
        static final int TRANSACTION_onQueryFinished = 3;
        static final int TRANSACTION_onQueryRejected = 4;
        static final int TRANSACTION_onResultDetected = 2;
        static final int TRANSACTION_onVisualQueryDetectionServiceFailure = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
        }

        public static IVisualQueryDetectionVoiceInteractionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVisualQueryDetectionVoiceInteractionCallback)) {
                return (IVisualQueryDetectionVoiceInteractionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onQueryDetected";
            }
            if (i == 2) {
                return "onResultDetected";
            }
            if (i == 3) {
                return "onQueryFinished";
            }
            if (i == 4) {
                return "onQueryRejected";
            }
            if (i != 5) {
                return null;
            }
            return "onVisualQueryDetectionServiceFailure";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onQueryDetected(readString);
            } else if (i == 2) {
                VisualQueryDetectedResult visualQueryDetectedResult = (VisualQueryDetectedResult) parcel.readTypedObject(VisualQueryDetectedResult.CREATOR);
                parcel.enforceNoDataAvail();
                onResultDetected(visualQueryDetectedResult);
            } else if (i == 3) {
                onQueryFinished();
            } else if (i == 4) {
                onQueryRejected();
            } else if (i == 5) {
                VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure = (VisualQueryDetectionServiceFailure) parcel.readTypedObject(VisualQueryDetectionServiceFailure.CREATOR);
                parcel.enforceNoDataAvail();
                onVisualQueryDetectionServiceFailure(visualQueryDetectionServiceFailure);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVisualQueryDetectionVoiceInteractionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR;
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onQueryDetected(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onResultDetected(VisualQueryDetectedResult visualQueryDetectedResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(visualQueryDetectedResult, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onQueryFinished() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onQueryRejected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onVisualQueryDetectionServiceFailure(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(visualQueryDetectionServiceFailure, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
