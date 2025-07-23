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

    public static class Default implements IWearableSensingManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.wearable.IWearableSensingManager
        public int getAvailableConnectionCount() throws RemoteException {
            return 0;
        }

        @Override // android.app.wearable.IWearableSensingManager
        public int provideConcurrentConnection(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException {
            return 0;
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void provideConnection(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void provideData(PersistableBundle persistableBundle, SharedMemory sharedMemory, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void provideReadOnlyParcelFileDescriptor(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void registerDataRequestObserver(int i, PendingIntent pendingIntent, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void removeAllConnections() throws RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public boolean removeConnection(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void startHotwordRecognition(ComponentName componentName, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void stopHotwordRecognition(RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.app.wearable.IWearableSensingManager
        public void unregisterDataRequestObserver(int i, PendingIntent pendingIntent, RemoteCallback remoteCallback) throws RemoteException {
        }
    }

    int getAvailableConnectionCount() throws RemoteException;

    int provideConcurrentConnection(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException;

    void provideConnection(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException;

    void provideData(PersistableBundle persistableBundle, SharedMemory sharedMemory, RemoteCallback remoteCallback) throws RemoteException;

    void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException;

    void provideReadOnlyParcelFileDescriptor(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, RemoteCallback remoteCallback) throws RemoteException;

    void registerDataRequestObserver(int i, PendingIntent pendingIntent, RemoteCallback remoteCallback) throws RemoteException;

    void removeAllConnections() throws RemoteException;

    boolean removeConnection(int i) throws RemoteException;

    void startHotwordRecognition(ComponentName componentName, RemoteCallback remoteCallback) throws RemoteException;

    void stopHotwordRecognition(RemoteCallback remoteCallback) throws RemoteException;

    void unregisterDataRequestObserver(int i, PendingIntent pendingIntent, RemoteCallback remoteCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IWearableSensingManager {
        static final int TRANSACTION_getAvailableConnectionCount = 1;
        static final int TRANSACTION_provideConcurrentConnection = 3;
        static final int TRANSACTION_provideConnection = 2;
        static final int TRANSACTION_provideData = 8;
        static final int TRANSACTION_provideDataStream = 7;
        static final int TRANSACTION_provideReadOnlyParcelFileDescriptor = 6;
        static final int TRANSACTION_registerDataRequestObserver = 9;
        static final int TRANSACTION_removeAllConnections = 5;
        static final int TRANSACTION_removeConnection = 4;
        static final int TRANSACTION_startHotwordRecognition = 11;
        static final int TRANSACTION_stopHotwordRecognition = 12;
        static final int TRANSACTION_unregisterDataRequestObserver = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, IWearableSensingManager.DESCRIPTOR);
        }

        public static IWearableSensingManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWearableSensingManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWearableSensingManager)) {
                return (IWearableSensingManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAvailableConnectionCount";
                case 2:
                    return "provideConnection";
                case 3:
                    return "provideConcurrentConnection";
                case 4:
                    return "removeConnection";
                case 5:
                    return "removeAllConnections";
                case 6:
                    return "provideReadOnlyParcelFileDescriptor";
                case 7:
                    return "provideDataStream";
                case 8:
                    return "provideData";
                case 9:
                    return "registerDataRequestObserver";
                case 10:
                    return "unregisterDataRequestObserver";
                case 11:
                    return "startHotwordRecognition";
                case 12:
                    return "stopHotwordRecognition";
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
                parcel.enforceInterface(IWearableSensingManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWearableSensingManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int availableConnectionCount = getAvailableConnectionCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(availableConnectionCount);
                    return true;
                case 2:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IWearableSensingCallback asInterface = IWearableSensingCallback.Stub.asInterface(parcel.readStrongBinder());
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideConnection(parcelFileDescriptor, asInterface, remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    IWearableSensingCallback asInterface2 = IWearableSensingCallback.Stub.asInterface(parcel.readStrongBinder());
                    RemoteCallback remoteCallback2 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    int provideConcurrentConnection = provideConcurrentConnection(parcelFileDescriptor2, persistableBundle, asInterface2, remoteCallback2);
                    parcel2.writeNoException();
                    parcel2.writeInt(provideConcurrentConnection);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeConnection = removeConnection(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeConnection);
                    return true;
                case 5:
                    removeAllConnections();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    PersistableBundle persistableBundle2 = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    RemoteCallback remoteCallback3 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideReadOnlyParcelFileDescriptor(parcelFileDescriptor3, persistableBundle2, remoteCallback3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    ParcelFileDescriptor parcelFileDescriptor4 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IWearableSensingCallback asInterface3 = IWearableSensingCallback.Stub.asInterface(parcel.readStrongBinder());
                    RemoteCallback remoteCallback4 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideDataStream(parcelFileDescriptor4, asInterface3, remoteCallback4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    PersistableBundle persistableBundle3 = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    SharedMemory sharedMemory = (SharedMemory) parcel.readTypedObject(SharedMemory.CREATOR);
                    RemoteCallback remoteCallback5 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideData(persistableBundle3, sharedMemory, remoteCallback5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt2 = parcel.readInt();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    RemoteCallback remoteCallback6 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerDataRequestObserver(readInt2, pendingIntent, remoteCallback6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt3 = parcel.readInt();
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    RemoteCallback remoteCallback7 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    unregisterDataRequestObserver(readInt3, pendingIntent2, remoteCallback7);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    RemoteCallback remoteCallback8 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    startHotwordRecognition(componentName, remoteCallback8);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    RemoteCallback remoteCallback9 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopHotwordRecognition(remoteCallback9);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IWearableSensingManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWearableSensingManager.DESCRIPTOR;
            }

            @Override // android.app.wearable.IWearableSensingManager
            public int getAvailableConnectionCount() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void provideConnection(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongInterface(iWearableSensingCallback);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public int provideConcurrentConnection(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeStrongInterface(iWearableSensingCallback);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public boolean removeConnection(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void removeAllConnections() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void provideReadOnlyParcelFileDescriptor(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongInterface(iWearableSensingCallback);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void provideData(PersistableBundle persistableBundle, SharedMemory sharedMemory, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeTypedObject(sharedMemory, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void registerDataRequestObserver(int i, PendingIntent pendingIntent, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void unregisterDataRequestObserver(int i, PendingIntent pendingIntent, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void startHotwordRecognition(ComponentName componentName, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.wearable.IWearableSensingManager
            public void stopHotwordRecognition(RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWearableSensingManager.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
