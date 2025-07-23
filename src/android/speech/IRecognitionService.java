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

    public static class Default implements IRecognitionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.speech.IRecognitionService
        public void cancel(IRecognitionListener iRecognitionListener, boolean z) throws RemoteException {
        }

        @Override // android.speech.IRecognitionService
        public void checkRecognitionSupport(Intent intent, AttributionSource attributionSource, IRecognitionSupportCallback iRecognitionSupportCallback) throws RemoteException {
        }

        @Override // android.speech.IRecognitionService
        public void startListening(Intent intent, IRecognitionListener iRecognitionListener, AttributionSource attributionSource) throws RemoteException {
        }

        @Override // android.speech.IRecognitionService
        public void stopListening(IRecognitionListener iRecognitionListener) throws RemoteException {
        }

        @Override // android.speech.IRecognitionService
        public void triggerModelDownload(Intent intent, AttributionSource attributionSource, IModelDownloadListener iModelDownloadListener) throws RemoteException {
        }
    }

    void cancel(IRecognitionListener iRecognitionListener, boolean z) throws RemoteException;

    void checkRecognitionSupport(Intent intent, AttributionSource attributionSource, IRecognitionSupportCallback iRecognitionSupportCallback) throws RemoteException;

    void startListening(Intent intent, IRecognitionListener iRecognitionListener, AttributionSource attributionSource) throws RemoteException;

    void stopListening(IRecognitionListener iRecognitionListener) throws RemoteException;

    void triggerModelDownload(Intent intent, AttributionSource attributionSource, IModelDownloadListener iModelDownloadListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IRecognitionService {
        public static final String DESCRIPTOR = "android.speech.IRecognitionService";
        static final int TRANSACTION_cancel = 3;
        static final int TRANSACTION_checkRecognitionSupport = 4;
        static final int TRANSACTION_startListening = 1;
        static final int TRANSACTION_stopListening = 2;
        static final int TRANSACTION_triggerModelDownload = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRecognitionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRecognitionService)) {
                return (IRecognitionService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "startListening";
            }
            if (i == 2) {
                return "stopListening";
            }
            if (i == 3) {
                return "cancel";
            }
            if (i == 4) {
                return "checkRecognitionSupport";
            }
            if (i != 5) {
                return null;
            }
            return "triggerModelDownload";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                IRecognitionListener asInterface = IRecognitionListener.Stub.asInterface(parcel.readStrongBinder());
                AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                parcel.enforceNoDataAvail();
                startListening(intent, asInterface, attributionSource);
            } else if (i == 2) {
                IRecognitionListener asInterface2 = IRecognitionListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                stopListening(asInterface2);
            } else if (i == 3) {
                IRecognitionListener asInterface3 = IRecognitionListener.Stub.asInterface(parcel.readStrongBinder());
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                cancel(asInterface3, readBoolean);
            } else if (i == 4) {
                Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                AttributionSource attributionSource2 = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                IRecognitionSupportCallback asInterface4 = IRecognitionSupportCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                checkRecognitionSupport(intent2, attributionSource2, asInterface4);
            } else if (i == 5) {
                Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                AttributionSource attributionSource3 = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                IModelDownloadListener asInterface5 = IModelDownloadListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                triggerModelDownload(intent3, attributionSource3, asInterface5);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRecognitionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.speech.IRecognitionService
            public void startListening(Intent intent, IRecognitionListener iRecognitionListener, AttributionSource attributionSource) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeStrongInterface(iRecognitionListener);
                    obtain.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionService
            public void stopListening(IRecognitionListener iRecognitionListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRecognitionListener);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionService
            public void cancel(IRecognitionListener iRecognitionListener, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRecognitionListener);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionService
            public void checkRecognitionSupport(Intent intent, AttributionSource attributionSource, IRecognitionSupportCallback iRecognitionSupportCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeStrongInterface(iRecognitionSupportCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionService
            public void triggerModelDownload(Intent intent, AttributionSource attributionSource, IModelDownloadListener iModelDownloadListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeStrongInterface(iModelDownloadListener);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
