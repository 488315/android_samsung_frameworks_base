package android.service.ondeviceintelligence;

import android.app.ondeviceintelligence.Feature;
import android.app.ondeviceintelligence.IDownloadCallback;
import android.app.ondeviceintelligence.IFeatureCallback;
import android.app.ondeviceintelligence.IFeatureDetailsCallback;
import android.app.ondeviceintelligence.IListFeaturesCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;

import com.android.internal.infra.AndroidFuture;

/* loaded from: classes3.dex */
public interface IOnDeviceIntelligenceService extends IInterface {
    public static final String DESCRIPTOR =
            "android.service.ondeviceintelligence.IOnDeviceIntelligenceService";

    void getFeature(int i, int i2, IFeatureCallback iFeatureCallback) throws RemoteException;

    void getFeatureDetails(int i, Feature feature, IFeatureDetailsCallback iFeatureDetailsCallback)
            throws RemoteException;

    void getReadOnlyFeatureFileDescriptorMap(Feature feature, RemoteCallback remoteCallback)
            throws RemoteException;

    void getReadOnlyFileDescriptor(String str, AndroidFuture<ParcelFileDescriptor> androidFuture)
            throws RemoteException;

    void getVersion(RemoteCallback remoteCallback) throws RemoteException;

    void listFeatures(int i, IListFeaturesCallback iListFeaturesCallback) throws RemoteException;

    void notifyInferenceServiceConnected() throws RemoteException;

    void notifyInferenceServiceDisconnected() throws RemoteException;

    void ready() throws RemoteException;

    void registerRemoteServices(IRemoteProcessingService iRemoteProcessingService)
            throws RemoteException;

    void requestFeatureDownload(
            int i,
            Feature feature,
            AndroidFuture androidFuture,
            IDownloadCallback iDownloadCallback)
            throws RemoteException;

    public static class Default implements IOnDeviceIntelligenceService {
        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getVersion(RemoteCallback remoteCallback) throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getFeature(int callerUid, int featureId, IFeatureCallback featureCallback)
                throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void listFeatures(int callerUid, IListFeaturesCallback listFeaturesCallback)
                throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getFeatureDetails(
                int callerUid, Feature feature, IFeatureDetailsCallback featureDetailsCallback)
                throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getReadOnlyFileDescriptor(
                String fileName, AndroidFuture<ParcelFileDescriptor> future)
                throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getReadOnlyFeatureFileDescriptorMap(
                Feature feature, RemoteCallback remoteCallback) throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void requestFeatureDownload(
                int callerUid,
                Feature feature,
                AndroidFuture cancellationSignal,
                IDownloadCallback downloadCallback)
                throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void registerRemoteServices(IRemoteProcessingService remoteProcessingService)
                throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void notifyInferenceServiceConnected() throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void notifyInferenceServiceDisconnected() throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void ready() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IOnDeviceIntelligenceService {
        static final int TRANSACTION_getFeature = 2;
        static final int TRANSACTION_getFeatureDetails = 4;
        static final int TRANSACTION_getReadOnlyFeatureFileDescriptorMap = 6;
        static final int TRANSACTION_getReadOnlyFileDescriptor = 5;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_listFeatures = 3;
        static final int TRANSACTION_notifyInferenceServiceConnected = 9;
        static final int TRANSACTION_notifyInferenceServiceDisconnected = 10;
        static final int TRANSACTION_ready = 11;
        static final int TRANSACTION_registerRemoteServices = 8;
        static final int TRANSACTION_requestFeatureDownload = 7;

        public Stub() {
            attachInterface(this, IOnDeviceIntelligenceService.DESCRIPTOR);
        }

        public static IOnDeviceIntelligenceService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOnDeviceIntelligenceService.DESCRIPTOR);
            if (iin != null && (iin instanceof IOnDeviceIntelligenceService)) {
                return (IOnDeviceIntelligenceService) iin;
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
                    return "getVersion";
                case 2:
                    return "getFeature";
                case 3:
                    return "listFeatures";
                case 4:
                    return "getFeatureDetails";
                case 5:
                    return "getReadOnlyFileDescriptor";
                case 6:
                    return "getReadOnlyFeatureFileDescriptorMap";
                case 7:
                    return "requestFeatureDownload";
                case 8:
                    return "registerRemoteServices";
                case 9:
                    return "notifyInferenceServiceConnected";
                case 10:
                    return "notifyInferenceServiceDisconnected";
                case 11:
                    return "ready";
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
                data.enforceInterface(IOnDeviceIntelligenceService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IOnDeviceIntelligenceService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    RemoteCallback _arg0 =
                            (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    getVersion(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg1 = data.readInt();
                    IFeatureCallback _arg2 =
                            IFeatureCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getFeature(_arg02, _arg1, _arg2);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    IListFeaturesCallback _arg12 =
                            IListFeaturesCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    listFeatures(_arg03, _arg12);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    Feature _arg13 = (Feature) data.readTypedObject(Feature.CREATOR);
                    IFeatureDetailsCallback _arg22 =
                            IFeatureDetailsCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getFeatureDetails(_arg04, _arg13, _arg22);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    AndroidFuture<ParcelFileDescriptor> _arg14 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    data.enforceNoDataAvail();
                    getReadOnlyFileDescriptor(_arg05, _arg14);
                    return true;
                case 6:
                    Feature _arg06 = (Feature) data.readTypedObject(Feature.CREATOR);
                    RemoteCallback _arg15 =
                            (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    getReadOnlyFeatureFileDescriptorMap(_arg06, _arg15);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    Feature _arg16 = (Feature) data.readTypedObject(Feature.CREATOR);
                    AndroidFuture _arg23 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    IDownloadCallback _arg3 =
                            IDownloadCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    requestFeatureDownload(_arg07, _arg16, _arg23, _arg3);
                    return true;
                case 8:
                    IRemoteProcessingService _arg08 =
                            IRemoteProcessingService.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerRemoteServices(_arg08);
                    return true;
                case 9:
                    notifyInferenceServiceConnected();
                    return true;
                case 10:
                    notifyInferenceServiceDisconnected();
                    return true;
                case 11:
                    ready();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOnDeviceIntelligenceService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnDeviceIntelligenceService.DESCRIPTOR;
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void getVersion(RemoteCallback remoteCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceService.DESCRIPTOR);
                    _data.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void getFeature(int callerUid, int featureId, IFeatureCallback featureCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceService.DESCRIPTOR);
                    _data.writeInt(callerUid);
                    _data.writeInt(featureId);
                    _data.writeStrongInterface(featureCallback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void listFeatures(int callerUid, IListFeaturesCallback listFeaturesCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceService.DESCRIPTOR);
                    _data.writeInt(callerUid);
                    _data.writeStrongInterface(listFeaturesCallback);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void getFeatureDetails(
                    int callerUid, Feature feature, IFeatureDetailsCallback featureDetailsCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceService.DESCRIPTOR);
                    _data.writeInt(callerUid);
                    _data.writeTypedObject(feature, 0);
                    _data.writeStrongInterface(featureDetailsCallback);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void getReadOnlyFileDescriptor(
                    String fileName, AndroidFuture<ParcelFileDescriptor> future)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceService.DESCRIPTOR);
                    _data.writeString(fileName);
                    _data.writeTypedObject(future, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void getReadOnlyFeatureFileDescriptorMap(
                    Feature feature, RemoteCallback remoteCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceService.DESCRIPTOR);
                    _data.writeTypedObject(feature, 0);
                    _data.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void requestFeatureDownload(
                    int callerUid,
                    Feature feature,
                    AndroidFuture cancellationSignal,
                    IDownloadCallback downloadCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceService.DESCRIPTOR);
                    _data.writeInt(callerUid);
                    _data.writeTypedObject(feature, 0);
                    _data.writeTypedObject(cancellationSignal, 0);
                    _data.writeStrongInterface(downloadCallback);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void registerRemoteServices(IRemoteProcessingService remoteProcessingService)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceService.DESCRIPTOR);
                    _data.writeStrongInterface(remoteProcessingService);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void notifyInferenceServiceConnected() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceService.DESCRIPTOR);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void notifyInferenceServiceDisconnected() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceService.DESCRIPTOR);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
            public void ready() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceService.DESCRIPTOR);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
