package android.service.wearable;

import android.app.ambientcontext.AmbientContextEventRequest;
import android.app.wearable.IWearableSensingCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SharedMemory;

/* loaded from: classes3.dex */
public interface IWearableSensingService extends IInterface {
    public static final String DESCRIPTOR = "android.service.wearable.IWearableSensingService";

    public static class Default implements IWearableSensingService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.wearable.IWearableSensingService
        public void killProcess() throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void onValidatedByHotwordDetectionService() throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideConcurrentSecureConnection(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideData(PersistableBundle persistableBundle, SharedMemory sharedMemory, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideReadOnlyParcelFileDescriptor(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideSecureConnection(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void queryServiceStatus(int[] iArr, String str, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void registerDataRequestObserver(int i, RemoteCallback remoteCallback, int i2, String str, RemoteCallback remoteCallback2) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void startDetection(AmbientContextEventRequest ambientContextEventRequest, String str, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void startHotwordRecognition(RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopActiveHotwordAudio() throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopDetection(String str) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopHotwordRecognition(RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void unregisterDataRequestObserver(int i, int i2, String str, RemoteCallback remoteCallback) throws RemoteException {
        }
    }

    void killProcess() throws RemoteException;

    void onValidatedByHotwordDetectionService() throws RemoteException;

    void provideConcurrentSecureConnection(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException;

    void provideData(PersistableBundle persistableBundle, SharedMemory sharedMemory, RemoteCallback remoteCallback) throws RemoteException;

    void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException;

    void provideReadOnlyParcelFileDescriptor(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, RemoteCallback remoteCallback) throws RemoteException;

    void provideSecureConnection(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException;

    void queryServiceStatus(int[] iArr, String str, RemoteCallback remoteCallback) throws RemoteException;

    void registerDataRequestObserver(int i, RemoteCallback remoteCallback, int i2, String str, RemoteCallback remoteCallback2) throws RemoteException;

    void startDetection(AmbientContextEventRequest ambientContextEventRequest, String str, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void startHotwordRecognition(RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void stopActiveHotwordAudio() throws RemoteException;

    void stopDetection(String str) throws RemoteException;

    void stopHotwordRecognition(RemoteCallback remoteCallback) throws RemoteException;

    void unregisterDataRequestObserver(int i, int i2, String str, RemoteCallback remoteCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IWearableSensingService {
        static final int TRANSACTION_killProcess = 15;
        static final int TRANSACTION_onValidatedByHotwordDetectionService = 10;
        static final int TRANSACTION_provideConcurrentSecureConnection = 2;
        static final int TRANSACTION_provideData = 5;
        static final int TRANSACTION_provideDataStream = 4;
        static final int TRANSACTION_provideReadOnlyParcelFileDescriptor = 3;
        static final int TRANSACTION_provideSecureConnection = 1;
        static final int TRANSACTION_queryServiceStatus = 14;
        static final int TRANSACTION_registerDataRequestObserver = 6;
        static final int TRANSACTION_startDetection = 12;
        static final int TRANSACTION_startHotwordRecognition = 8;
        static final int TRANSACTION_stopActiveHotwordAudio = 11;
        static final int TRANSACTION_stopDetection = 13;
        static final int TRANSACTION_stopHotwordRecognition = 9;
        static final int TRANSACTION_unregisterDataRequestObserver = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }

        public Stub() {
            attachInterface(this, IWearableSensingService.DESCRIPTOR);
        }

        public static IWearableSensingService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWearableSensingService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWearableSensingService)) {
                return (IWearableSensingService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "provideSecureConnection";
                case 2:
                    return "provideConcurrentSecureConnection";
                case 3:
                    return "provideReadOnlyParcelFileDescriptor";
                case 4:
                    return "provideDataStream";
                case 5:
                    return "provideData";
                case 6:
                    return "registerDataRequestObserver";
                case 7:
                    return "unregisterDataRequestObserver";
                case 8:
                    return "startHotwordRecognition";
                case 9:
                    return "stopHotwordRecognition";
                case 10:
                    return "onValidatedByHotwordDetectionService";
                case 11:
                    return "stopActiveHotwordAudio";
                case 12:
                    return "startDetection";
                case 13:
                    return "stopDetection";
                case 14:
                    return "queryServiceStatus";
                case 15:
                    return "killProcess";
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
                parcel.enforceInterface(IWearableSensingService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWearableSensingService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IWearableSensingCallback asInterface = IWearableSensingCallback.Stub.asInterface(parcel.readStrongBinder());
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideSecureConnection(parcelFileDescriptor, asInterface, remoteCallback);
                    return true;
                case 2:
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    IWearableSensingCallback asInterface2 = IWearableSensingCallback.Stub.asInterface(parcel.readStrongBinder());
                    RemoteCallback remoteCallback2 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideConcurrentSecureConnection(parcelFileDescriptor2, persistableBundle, asInterface2, remoteCallback2);
                    return true;
                case 3:
                    ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    PersistableBundle persistableBundle2 = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    RemoteCallback remoteCallback3 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideReadOnlyParcelFileDescriptor(parcelFileDescriptor3, persistableBundle2, remoteCallback3);
                    return true;
                case 4:
                    ParcelFileDescriptor parcelFileDescriptor4 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IWearableSensingCallback asInterface3 = IWearableSensingCallback.Stub.asInterface(parcel.readStrongBinder());
                    RemoteCallback remoteCallback4 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideDataStream(parcelFileDescriptor4, asInterface3, remoteCallback4);
                    return true;
                case 5:
                    PersistableBundle persistableBundle3 = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    SharedMemory sharedMemory = (SharedMemory) parcel.readTypedObject(SharedMemory.CREATOR);
                    RemoteCallback remoteCallback5 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideData(persistableBundle3, sharedMemory, remoteCallback5);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    RemoteCallback remoteCallback6 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    int readInt2 = parcel.readInt();
                    String readString = parcel.readString();
                    RemoteCallback remoteCallback7 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerDataRequestObserver(readInt, remoteCallback6, readInt2, readString, remoteCallback7);
                    return true;
                case 7:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    String readString2 = parcel.readString();
                    RemoteCallback remoteCallback8 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    unregisterDataRequestObserver(readInt3, readInt4, readString2, remoteCallback8);
                    return true;
                case 8:
                    RemoteCallback remoteCallback9 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    RemoteCallback remoteCallback10 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    startHotwordRecognition(remoteCallback9, remoteCallback10);
                    return true;
                case 9:
                    RemoteCallback remoteCallback11 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopHotwordRecognition(remoteCallback11);
                    return true;
                case 10:
                    onValidatedByHotwordDetectionService();
                    return true;
                case 11:
                    stopActiveHotwordAudio();
                    return true;
                case 12:
                    AmbientContextEventRequest ambientContextEventRequest = (AmbientContextEventRequest) parcel.readTypedObject(AmbientContextEventRequest.CREATOR);
                    String readString3 = parcel.readString();
                    RemoteCallback remoteCallback12 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    RemoteCallback remoteCallback13 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    startDetection(ambientContextEventRequest, readString3, remoteCallback12, remoteCallback13);
                    return true;
                case 13:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopDetection(readString4);
                    return true;
                case 14:
                    int[] createIntArray = parcel.createIntArray();
                    String readString5 = parcel.readString();
                    RemoteCallback remoteCallback14 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    queryServiceStatus(createIntArray, readString5, remoteCallback14);
                    return true;
                case 15:
                    killProcess();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IWearableSensingService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWearableSensingService.DESCRIPTOR;
            }

            @Override // android.service.wearable.IWearableSensingService
            public void provideSecureConnection(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongInterface(iWearableSensingCallback);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void provideConcurrentSecureConnection(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeStrongInterface(iWearableSensingCallback);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void provideReadOnlyParcelFileDescriptor(ParcelFileDescriptor parcelFileDescriptor, PersistableBundle persistableBundle, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongInterface(iWearableSensingCallback);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void provideData(PersistableBundle persistableBundle, SharedMemory sharedMemory, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeTypedObject(sharedMemory, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void registerDataRequestObserver(int i, RemoteCallback remoteCallback, int i2, String str, RemoteCallback remoteCallback2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void unregisterDataRequestObserver(int i, int i2, String str, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void startHotwordRecognition(RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void stopHotwordRecognition(RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void onValidatedByHotwordDetectionService() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void stopActiveHotwordAudio() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void startDetection(AmbientContextEventRequest ambientContextEventRequest, String str, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    obtain.writeTypedObject(ambientContextEventRequest, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void stopDetection(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void queryServiceStatus(int[] iArr, String str, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void killProcess() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
