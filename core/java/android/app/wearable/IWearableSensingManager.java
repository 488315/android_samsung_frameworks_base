package android.app.wearable;

import android.app.PendingIntent;
import android.app.wearable.IWearableSensingCallback;
import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SharedMemory;

/* loaded from: classes.dex */
public interface IWearableSensingManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.wearable.IWearableSensingManager";

    void provideConnection(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException;

    void provideData(PersistableBundle persistableBundle, SharedMemory sharedMemory, RemoteCallback remoteCallback) throws RemoteException;

    void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException;

    void registerDataRequestObserver(int i, PendingIntent pendingIntent, RemoteCallback remoteCallback) throws RemoteException;

    void startHotwordRecognition(ComponentName componentName, RemoteCallback remoteCallback) throws RemoteException;

    void stopHotwordRecognition(RemoteCallback remoteCallback) throws RemoteException;

    void unregisterDataRequestObserver(int i, PendingIntent pendingIntent, RemoteCallback remoteCallback) throws RemoteException;

    public static class Default implements IWearableSensingManager {
        @Override // android.app.wearable.IWearableSensingManager
        public void provideConnection(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback wearableSensingCallback, RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback wearableSensingCallback, RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void provideData(PersistableBundle data, SharedMemory sharedMemory, RemoteCallback callback) throws RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void registerDataRequestObserver(int dataType, PendingIntent dataRequestPendingIntent, RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void unregisterDataRequestObserver(int dataType, PendingIntent dataRequestPendingIntent, RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void startHotwordRecognition(ComponentName targetVisComponentName, RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void stopHotwordRecognition(RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWearableSensingManager {
        static final int TRANSACTION_provideConnection = 1;
        static final int TRANSACTION_provideData = 3;
        static final int TRANSACTION_provideDataStream = 2;
        static final int TRANSACTION_registerDataRequestObserver = 4;
        static final int TRANSACTION_startHotwordRecognition = 6;
        static final int TRANSACTION_stopHotwordRecognition = 7;
        static final int TRANSACTION_unregisterDataRequestObserver = 5;

        public Stub() {
            attachInterface(this, IWearableSensingManager.DESCRIPTOR);
        }

        public static IWearableSensingManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWearableSensingManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IWearableSensingManager)) {
                return (IWearableSensingManager) iin;
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
                    return "provideConnection";
                case 2:
                    return "provideDataStream";
                case 3:
                    return "provideData";
                case 4:
                    return "registerDataRequestObserver";
                case 5:
                    return "unregisterDataRequestObserver";
                case 6:
                    return "startHotwordRecognition";
                case 7:
                    return "stopHotwordRecognition";
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
                data.enforceInterface(IWearableSensingManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IWearableSensingManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ParcelFileDescriptor _arg0 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IWearableSensingCallback _arg1 = IWearableSensingCallback.Stub.asInterface(data.readStrongBinder());
                    RemoteCallback _arg2 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    provideConnection(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    ParcelFileDescriptor _arg02 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IWearableSensingCallback _arg12 = IWearableSensingCallback.Stub.asInterface(data.readStrongBinder());
                    RemoteCallback _arg22 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    provideDataStream(_arg02, _arg12, _arg22);
                    reply.writeNoException();
                    return true;
                case 3:
                    PersistableBundle _arg03 = (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    SharedMemory _arg13 = (SharedMemory) data.readTypedObject(SharedMemory.CREATOR);
                    RemoteCallback _arg23 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    provideData(_arg03, _arg13, _arg23);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    PendingIntent _arg14 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    RemoteCallback _arg24 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    registerDataRequestObserver(_arg04, _arg14, _arg24);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    PendingIntent _arg15 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    RemoteCallback _arg25 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    unregisterDataRequestObserver(_arg05, _arg15, _arg25);
                    reply.writeNoException();
                    return true;
                case 6:
                    ComponentName _arg06 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    RemoteCallback _arg16 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    startHotwordRecognition(_arg06, _arg16);
                    reply.writeNoException();
                    return true;
                case 7:
                    RemoteCallback _arg07 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    stopHotwordRecognition(_arg07);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWearableSensingManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWearableSensingManager.DESCRIPTOR;
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void provideConnection(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback wearableSensingCallback, RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    _data.writeTypedObject(parcelFileDescriptor, 0);
                    _data.writeStrongInterface(wearableSensingCallback);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback wearableSensingCallback, RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    _data.writeTypedObject(parcelFileDescriptor, 0);
                    _data.writeStrongInterface(wearableSensingCallback);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void provideData(PersistableBundle data, SharedMemory sharedMemory, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    _data.writeTypedObject(data, 0);
                    _data.writeTypedObject(sharedMemory, 0);
                    _data.writeTypedObject(callback, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void registerDataRequestObserver(int dataType, PendingIntent dataRequestPendingIntent, RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    _data.writeInt(dataType);
                    _data.writeTypedObject(dataRequestPendingIntent, 0);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void unregisterDataRequestObserver(int dataType, PendingIntent dataRequestPendingIntent, RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    _data.writeInt(dataType);
                    _data.writeTypedObject(dataRequestPendingIntent, 0);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void startHotwordRecognition(ComponentName targetVisComponentName, RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    _data.writeTypedObject(targetVisComponentName, 0);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void stopHotwordRecognition(RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
