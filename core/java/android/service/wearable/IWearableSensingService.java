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

    void killProcess() throws RemoteException;

    void onValidatedByHotwordDetectionService() throws RemoteException;

    void provideData(PersistableBundle persistableBundle, SharedMemory sharedMemory, RemoteCallback remoteCallback) throws RemoteException;

    void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException;

    void provideSecureConnection(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback iWearableSensingCallback, RemoteCallback remoteCallback) throws RemoteException;

    void queryServiceStatus(int[] iArr, String str, RemoteCallback remoteCallback) throws RemoteException;

    void registerDataRequestObserver(int i, RemoteCallback remoteCallback, int i2, String str, RemoteCallback remoteCallback2) throws RemoteException;

    void startDetection(AmbientContextEventRequest ambientContextEventRequest, String str, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void startHotwordRecognition(RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void stopActiveHotwordAudio() throws RemoteException;

    void stopDetection(String str) throws RemoteException;

    void stopHotwordRecognition(RemoteCallback remoteCallback) throws RemoteException;

    void unregisterDataRequestObserver(int i, int i2, String str, RemoteCallback remoteCallback) throws RemoteException;

    public static class Default implements IWearableSensingService {
        @Override // android.service.wearable.IWearableSensingService
        public void provideSecureConnection(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback wearableSensingCallback, RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback wearableSensingCallback, RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideData(PersistableBundle data, SharedMemory sharedMemory, RemoteCallback callback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void registerDataRequestObserver(int dataType, RemoteCallback dataRequestCallback, int dataRequestObserverId, String packageName, RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void unregisterDataRequestObserver(int dataType, int dataRequestObserverId, String packageName, RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void startHotwordRecognition(RemoteCallback wearableHotwordCallback, RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopHotwordRecognition(RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void onValidatedByHotwordDetectionService() throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopActiveHotwordAudio() throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void startDetection(AmbientContextEventRequest request, String packageName, RemoteCallback detectionResultCallback, RemoteCallback statusCallback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopDetection(String packageName) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void queryServiceStatus(int[] eventTypes, String packageName, RemoteCallback callback) throws RemoteException {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void killProcess() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWearableSensingService {
        static final int TRANSACTION_killProcess = 13;
        static final int TRANSACTION_onValidatedByHotwordDetectionService = 8;
        static final int TRANSACTION_provideData = 3;
        static final int TRANSACTION_provideDataStream = 2;
        static final int TRANSACTION_provideSecureConnection = 1;
        static final int TRANSACTION_queryServiceStatus = 12;
        static final int TRANSACTION_registerDataRequestObserver = 4;
        static final int TRANSACTION_startDetection = 10;
        static final int TRANSACTION_startHotwordRecognition = 6;
        static final int TRANSACTION_stopActiveHotwordAudio = 9;
        static final int TRANSACTION_stopDetection = 11;
        static final int TRANSACTION_stopHotwordRecognition = 7;
        static final int TRANSACTION_unregisterDataRequestObserver = 5;

        public Stub() {
            attachInterface(this, IWearableSensingService.DESCRIPTOR);
        }

        public static IWearableSensingService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWearableSensingService.DESCRIPTOR);
            if (iin != null && (iin instanceof IWearableSensingService)) {
                return (IWearableSensingService) iin;
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
                    return "provideSecureConnection";
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
                case 8:
                    return "onValidatedByHotwordDetectionService";
                case 9:
                    return "stopActiveHotwordAudio";
                case 10:
                    return "startDetection";
                case 11:
                    return "stopDetection";
                case 12:
                    return "queryServiceStatus";
                case 13:
                    return "killProcess";
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
                data.enforceInterface(IWearableSensingService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IWearableSensingService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ParcelFileDescriptor _arg0 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IWearableSensingCallback _arg1 = IWearableSensingCallback.Stub.asInterface(data.readStrongBinder());
                    RemoteCallback _arg2 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    provideSecureConnection(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    ParcelFileDescriptor _arg02 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IWearableSensingCallback _arg12 = IWearableSensingCallback.Stub.asInterface(data.readStrongBinder());
                    RemoteCallback _arg22 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    provideDataStream(_arg02, _arg12, _arg22);
                    return true;
                case 3:
                    PersistableBundle _arg03 = (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    SharedMemory _arg13 = (SharedMemory) data.readTypedObject(SharedMemory.CREATOR);
                    RemoteCallback _arg23 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    provideData(_arg03, _arg13, _arg23);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    RemoteCallback _arg14 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    int _arg24 = data.readInt();
                    String _arg3 = data.readString();
                    RemoteCallback _arg4 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    registerDataRequestObserver(_arg04, _arg14, _arg24, _arg3, _arg4);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    int _arg15 = data.readInt();
                    String _arg25 = data.readString();
                    RemoteCallback _arg32 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    unregisterDataRequestObserver(_arg05, _arg15, _arg25, _arg32);
                    return true;
                case 6:
                    RemoteCallback _arg06 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    RemoteCallback _arg16 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    startHotwordRecognition(_arg06, _arg16);
                    return true;
                case 7:
                    RemoteCallback _arg07 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    stopHotwordRecognition(_arg07);
                    return true;
                case 8:
                    onValidatedByHotwordDetectionService();
                    return true;
                case 9:
                    stopActiveHotwordAudio();
                    return true;
                case 10:
                    AmbientContextEventRequest _arg08 = (AmbientContextEventRequest) data.readTypedObject(AmbientContextEventRequest.CREATOR);
                    String _arg17 = data.readString();
                    RemoteCallback _arg26 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    RemoteCallback _arg33 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    startDetection(_arg08, _arg17, _arg26, _arg33);
                    return true;
                case 11:
                    String _arg09 = data.readString();
                    data.enforceNoDataAvail();
                    stopDetection(_arg09);
                    return true;
                case 12:
                    int[] _arg010 = data.createIntArray();
                    String _arg18 = data.readString();
                    RemoteCallback _arg27 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    queryServiceStatus(_arg010, _arg18, _arg27);
                    return true;
                case 13:
                    killProcess();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWearableSensingService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWearableSensingService.DESCRIPTOR;
            }

            @Override // android.service.wearable.IWearableSensingService
            public void provideSecureConnection(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback wearableSensingCallback, RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    _data.writeTypedObject(parcelFileDescriptor, 0);
                    _data.writeStrongInterface(wearableSensingCallback);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void provideDataStream(ParcelFileDescriptor parcelFileDescriptor, IWearableSensingCallback wearableSensingCallback, RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    _data.writeTypedObject(parcelFileDescriptor, 0);
                    _data.writeStrongInterface(wearableSensingCallback);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void provideData(PersistableBundle data, SharedMemory sharedMemory, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    _data.writeTypedObject(data, 0);
                    _data.writeTypedObject(sharedMemory, 0);
                    _data.writeTypedObject(callback, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void registerDataRequestObserver(int dataType, RemoteCallback dataRequestCallback, int dataRequestObserverId, String packageName, RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    _data.writeInt(dataType);
                    _data.writeTypedObject(dataRequestCallback, 0);
                    _data.writeInt(dataRequestObserverId);
                    _data.writeString(packageName);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void unregisterDataRequestObserver(int dataType, int dataRequestObserverId, String packageName, RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    _data.writeInt(dataType);
                    _data.writeInt(dataRequestObserverId);
                    _data.writeString(packageName);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void startHotwordRecognition(RemoteCallback wearableHotwordCallback, RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    _data.writeTypedObject(wearableHotwordCallback, 0);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void stopHotwordRecognition(RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void onValidatedByHotwordDetectionService() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void stopActiveHotwordAudio() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void startDetection(AmbientContextEventRequest request, String packageName, RemoteCallback detectionResultCallback, RemoteCallback statusCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeString(packageName);
                    _data.writeTypedObject(detectionResultCallback, 0);
                    _data.writeTypedObject(statusCallback, 0);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void stopDetection(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void queryServiceStatus(int[] eventTypes, String packageName, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    _data.writeIntArray(eventTypes);
                    _data.writeString(packageName);
                    _data.writeTypedObject(callback, 0);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.wearable.IWearableSensingService
            public void killProcess() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWearableSensingService.DESCRIPTOR);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
