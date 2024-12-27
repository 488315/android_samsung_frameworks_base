package android.speech;

import android.content.AttributionSource;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.speech.IModelDownloadListener;
import android.speech.IRecognitionListener;
import android.speech.IRecognitionSupportCallback;

/* loaded from: classes3.dex */
public interface IRecognitionService extends IInterface {
    void cancel(IRecognitionListener iRecognitionListener, boolean z) throws RemoteException;

    void checkRecognitionSupport(Intent intent, AttributionSource attributionSource, IRecognitionSupportCallback iRecognitionSupportCallback) throws RemoteException;

    void startListening(Intent intent, IRecognitionListener iRecognitionListener, AttributionSource attributionSource) throws RemoteException;

    void stopListening(IRecognitionListener iRecognitionListener) throws RemoteException;

    void triggerModelDownload(Intent intent, AttributionSource attributionSource, IModelDownloadListener iModelDownloadListener) throws RemoteException;

    public static class Default implements IRecognitionService {
        @Override // android.speech.IRecognitionService
        public void startListening(Intent recognizerIntent, IRecognitionListener listener, AttributionSource attributionSource) throws RemoteException {
        }

        @Override // android.speech.IRecognitionService
        public void stopListening(IRecognitionListener listener) throws RemoteException {
        }

        @Override // android.speech.IRecognitionService
        public void cancel(IRecognitionListener listener, boolean isShutdown) throws RemoteException {
        }

        @Override // android.speech.IRecognitionService
        public void checkRecognitionSupport(Intent recognizerIntent, AttributionSource attributionSource, IRecognitionSupportCallback listener) throws RemoteException {
        }

        @Override // android.speech.IRecognitionService
        public void triggerModelDownload(Intent recognizerIntent, AttributionSource attributionSource, IModelDownloadListener listener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRecognitionService {
        public static final String DESCRIPTOR = "android.speech.IRecognitionService";
        static final int TRANSACTION_cancel = 3;
        static final int TRANSACTION_checkRecognitionSupport = 4;
        static final int TRANSACTION_startListening = 1;
        static final int TRANSACTION_stopListening = 2;
        static final int TRANSACTION_triggerModelDownload = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRecognitionService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRecognitionService)) {
                return (IRecognitionService) iin;
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
                    return "startListening";
                case 2:
                    return "stopListening";
                case 3:
                    return "cancel";
                case 4:
                    return "checkRecognitionSupport";
                case 5:
                    return "triggerModelDownload";
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
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Intent _arg0 = (Intent) data.readTypedObject(Intent.CREATOR);
                    IRecognitionListener _arg1 = IRecognitionListener.Stub.asInterface(data.readStrongBinder());
                    AttributionSource _arg2 = (AttributionSource) data.readTypedObject(AttributionSource.CREATOR);
                    data.enforceNoDataAvail();
                    startListening(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    IRecognitionListener _arg02 = IRecognitionListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    stopListening(_arg02);
                    return true;
                case 3:
                    IRecognitionListener _arg03 = IRecognitionListener.Stub.asInterface(data.readStrongBinder());
                    boolean _arg12 = data.readBoolean();
                    data.enforceNoDataAvail();
                    cancel(_arg03, _arg12);
                    return true;
                case 4:
                    Intent _arg04 = (Intent) data.readTypedObject(Intent.CREATOR);
                    AttributionSource _arg13 = (AttributionSource) data.readTypedObject(AttributionSource.CREATOR);
                    IRecognitionSupportCallback _arg22 = IRecognitionSupportCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    checkRecognitionSupport(_arg04, _arg13, _arg22);
                    return true;
                case 5:
                    Intent _arg05 = (Intent) data.readTypedObject(Intent.CREATOR);
                    AttributionSource _arg14 = (AttributionSource) data.readTypedObject(AttributionSource.CREATOR);
                    IModelDownloadListener _arg23 = IModelDownloadListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    triggerModelDownload(_arg05, _arg14, _arg23);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRecognitionService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.speech.IRecognitionService
            public void startListening(Intent recognizerIntent, IRecognitionListener listener, AttributionSource attributionSource) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(recognizerIntent, 0);
                    _data.writeStrongInterface(listener);
                    _data.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.speech.IRecognitionService
            public void stopListening(IRecognitionListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.speech.IRecognitionService
            public void cancel(IRecognitionListener listener, boolean isShutdown) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeBoolean(isShutdown);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.speech.IRecognitionService
            public void checkRecognitionSupport(Intent recognizerIntent, AttributionSource attributionSource, IRecognitionSupportCallback listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(recognizerIntent, 0);
                    _data.writeTypedObject(attributionSource, 0);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.speech.IRecognitionService
            public void triggerModelDownload(Intent recognizerIntent, AttributionSource attributionSource, IModelDownloadListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(recognizerIntent, 0);
                    _data.writeTypedObject(attributionSource, 0);
                    _data.writeStrongInterface(listener);
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
