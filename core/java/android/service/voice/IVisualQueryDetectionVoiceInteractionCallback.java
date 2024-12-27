package android.service.voice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IVisualQueryDetectionVoiceInteractionCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.service.voice.IVisualQueryDetectionVoiceInteractionCallback";

    void onQueryDetected(String str) throws RemoteException;

    void onQueryFinished() throws RemoteException;

    void onQueryRejected() throws RemoteException;

    void onResultDetected(VisualQueryDetectedResult visualQueryDetectedResult)
            throws RemoteException;

    void onVisualQueryDetectionServiceFailure(
            VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure)
            throws RemoteException;

    public static class Default implements IVisualQueryDetectionVoiceInteractionCallback {
        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryDetected(String partialQuery) throws RemoteException {}

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onResultDetected(VisualQueryDetectedResult partialResult)
                throws RemoteException {}

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryFinished() throws RemoteException {}

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryRejected() throws RemoteException {}

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onVisualQueryDetectionServiceFailure(
                VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder
            implements IVisualQueryDetectionVoiceInteractionCallback {
        static final int TRANSACTION_onQueryDetected = 1;
        static final int TRANSACTION_onQueryFinished = 3;
        static final int TRANSACTION_onQueryRejected = 4;
        static final int TRANSACTION_onResultDetected = 2;
        static final int TRANSACTION_onVisualQueryDetectionServiceFailure = 5;

        public Stub() {
            attachInterface(this, IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
        }

        public static IVisualQueryDetectionVoiceInteractionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin =
                    obj.queryLocalInterface(
                            IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IVisualQueryDetectionVoiceInteractionCallback)) {
                return (IVisualQueryDetectionVoiceInteractionCallback) iin;
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
                    return "onQueryDetected";
                case 2:
                    return "onResultDetected";
                case 3:
                    return "onQueryFinished";
                case 4:
                    return "onQueryRejected";
                case 5:
                    return "onVisualQueryDetectionServiceFailure";
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
                data.enforceInterface(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    onQueryDetected(_arg0);
                    return true;
                case 2:
                    VisualQueryDetectedResult _arg02 =
                            (VisualQueryDetectedResult)
                                    data.readTypedObject(VisualQueryDetectedResult.CREATOR);
                    data.enforceNoDataAvail();
                    onResultDetected(_arg02);
                    return true;
                case 3:
                    onQueryFinished();
                    return true;
                case 4:
                    onQueryRejected();
                    return true;
                case 5:
                    VisualQueryDetectionServiceFailure _arg03 =
                            (VisualQueryDetectionServiceFailure)
                                    data.readTypedObject(
                                            VisualQueryDetectionServiceFailure.CREATOR);
                    data.enforceNoDataAvail();
                    onVisualQueryDetectionServiceFailure(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVisualQueryDetectionVoiceInteractionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR;
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onQueryDetected(String partialQuery) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(
                            IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    _data.writeString(partialQuery);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onResultDetected(VisualQueryDetectedResult partialResult)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(
                            IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    _data.writeTypedObject(partialResult, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onQueryFinished() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(
                            IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onQueryRejected() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(
                            IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
            public void onVisualQueryDetectionServiceFailure(
                    VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(
                            IVisualQueryDetectionVoiceInteractionCallback.DESCRIPTOR);
                    _data.writeTypedObject(visualQueryDetectionServiceFailure, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
