package android.app.ondeviceintelligence;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;

import com.android.internal.infra.AndroidFuture;

import java.util.List;

public interface IOnDeviceIntelligenceManager extends IInterface {
    public static final String DESCRIPTOR =
            "android.app.ondeviceintelligence.IOnDeviceIntelligenceManager";

    void getFeature(int i, IFeatureCallback iFeatureCallback) throws RemoteException;

    void getFeatureDetails(Feature feature, IFeatureDetailsCallback iFeatureDetailsCallback)
            throws RemoteException;

    List<InferenceInfo> getLatestInferenceInfo(long j) throws RemoteException;

    String getRemoteServicePackageName() throws RemoteException;

    void getVersion(RemoteCallback remoteCallback) throws RemoteException;

    void listFeatures(IListFeaturesCallback iListFeaturesCallback) throws RemoteException;

    void processRequest(
            Feature feature,
            Bundle bundle,
            int i,
            AndroidFuture androidFuture,
            AndroidFuture androidFuture2,
            IResponseCallback iResponseCallback)
            throws RemoteException;

    void processRequestStreaming(
            Feature feature,
            Bundle bundle,
            int i,
            AndroidFuture androidFuture,
            AndroidFuture androidFuture2,
            IStreamingResponseCallback iStreamingResponseCallback)
            throws RemoteException;

    void requestFeatureDownload(
            Feature feature, AndroidFuture androidFuture, IDownloadCallback iDownloadCallback)
            throws RemoteException;

    void requestTokenInfo(
            Feature feature,
            Bundle bundle,
            AndroidFuture androidFuture,
            ITokenInfoCallback iTokenInfoCallback)
            throws RemoteException;

    public static class Default implements IOnDeviceIntelligenceManager {
        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void getVersion(RemoteCallback remoteCallback) throws RemoteException {}

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void getFeature(int featureId, IFeatureCallback remoteCallback)
                throws RemoteException {}

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void listFeatures(IListFeaturesCallback listFeaturesCallback)
                throws RemoteException {}

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void getFeatureDetails(
                Feature feature, IFeatureDetailsCallback featureDetailsCallback)
                throws RemoteException {}

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void requestFeatureDownload(
                Feature feature, AndroidFuture cancellationSignalFuture, IDownloadCallback callback)
                throws RemoteException {}

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void requestTokenInfo(
                Feature feature,
                Bundle requestBundle,
                AndroidFuture cancellationSignalFuture,
                ITokenInfoCallback tokenInfocallback)
                throws RemoteException {}

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void processRequest(
                Feature feature,
                Bundle requestBundle,
                int requestType,
                AndroidFuture cancellationSignalFuture,
                AndroidFuture processingSignalFuture,
                IResponseCallback responseCallback)
                throws RemoteException {}

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public void processRequestStreaming(
                Feature feature,
                Bundle requestBundle,
                int requestType,
                AndroidFuture cancellationSignalFuture,
                AndroidFuture processingSignalFuture,
                IStreamingResponseCallback streamingCallback)
                throws RemoteException {}

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public String getRemoteServicePackageName() throws RemoteException {
            return null;
        }

        @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
        public List<InferenceInfo> getLatestInferenceInfo(long startTimeEpochMillis)
                throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IOnDeviceIntelligenceManager {
        static final int TRANSACTION_getFeature = 3;
        static final int TRANSACTION_getFeatureDetails = 5;
        static final int TRANSACTION_getLatestInferenceInfo = 11;
        static final int TRANSACTION_getRemoteServicePackageName = 10;
        static final int TRANSACTION_getVersion = 2;
        static final int TRANSACTION_listFeatures = 4;
        static final int TRANSACTION_processRequest = 8;
        static final int TRANSACTION_processRequestStreaming = 9;
        static final int TRANSACTION_requestFeatureDownload = 6;
        static final int TRANSACTION_requestTokenInfo = 7;

        public Stub() {
            attachInterface(this, IOnDeviceIntelligenceManager.DESCRIPTOR);
        }

        public static IOnDeviceIntelligenceManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOnDeviceIntelligenceManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IOnDeviceIntelligenceManager)) {
                return (IOnDeviceIntelligenceManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 2:
                    return "getVersion";
                case 3:
                    return "getFeature";
                case 4:
                    return "listFeatures";
                case 5:
                    return "getFeatureDetails";
                case 6:
                    return "requestFeatureDownload";
                case 7:
                    return "requestTokenInfo";
                case 8:
                    return "processRequest";
                case 9:
                    return "processRequestStreaming";
                case 10:
                    return "getRemoteServicePackageName";
                case 11:
                    return "getLatestInferenceInfo";
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
                data.enforceInterface(IOnDeviceIntelligenceManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IOnDeviceIntelligenceManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 2:
                    RemoteCallback _arg0 =
                            (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    getVersion(_arg0);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg02 = data.readInt();
                    IFeatureCallback _arg1 =
                            IFeatureCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getFeature(_arg02, _arg1);
                    reply.writeNoException();
                    return true;
                case 4:
                    IListFeaturesCallback _arg03 =
                            IListFeaturesCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    listFeatures(_arg03);
                    reply.writeNoException();
                    return true;
                case 5:
                    Feature _arg04 = (Feature) data.readTypedObject(Feature.CREATOR);
                    IFeatureDetailsCallback _arg12 =
                            IFeatureDetailsCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getFeatureDetails(_arg04, _arg12);
                    reply.writeNoException();
                    return true;
                case 6:
                    Feature _arg05 = (Feature) data.readTypedObject(Feature.CREATOR);
                    AndroidFuture _arg13 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    IDownloadCallback _arg2 =
                            IDownloadCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    requestFeatureDownload(_arg05, _arg13, _arg2);
                    reply.writeNoException();
                    return true;
                case 7:
                    Feature _arg06 = (Feature) data.readTypedObject(Feature.CREATOR);
                    Bundle _arg14 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    AndroidFuture _arg22 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    ITokenInfoCallback _arg3 =
                            ITokenInfoCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    requestTokenInfo(_arg06, _arg14, _arg22, _arg3);
                    reply.writeNoException();
                    return true;
                case 8:
                    Feature _arg07 = (Feature) data.readTypedObject(Feature.CREATOR);
                    Bundle _arg15 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg23 = data.readInt();
                    AndroidFuture _arg32 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    AndroidFuture _arg4 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    IResponseCallback _arg5 =
                            IResponseCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    processRequest(_arg07, _arg15, _arg23, _arg32, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 9:
                    Feature _arg08 = (Feature) data.readTypedObject(Feature.CREATOR);
                    Bundle _arg16 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg24 = data.readInt();
                    AndroidFuture _arg33 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    AndroidFuture _arg42 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    IStreamingResponseCallback _arg52 =
                            IStreamingResponseCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    processRequestStreaming(_arg08, _arg16, _arg24, _arg33, _arg42, _arg52);
                    reply.writeNoException();
                    return true;
                case 10:
                    String _result = getRemoteServicePackageName();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 11:
                    long _arg09 = data.readLong();
                    data.enforceNoDataAvail();
                    List<InferenceInfo> _result2 = getLatestInferenceInfo(_arg09);
                    reply.writeNoException();
                    reply.writeTypedList(_result2, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOnDeviceIntelligenceManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnDeviceIntelligenceManager.DESCRIPTOR;
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void getVersion(RemoteCallback remoteCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceManager.DESCRIPTOR);
                    _data.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void getFeature(int featureId, IFeatureCallback remoteCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceManager.DESCRIPTOR);
                    _data.writeInt(featureId);
                    _data.writeStrongInterface(remoteCallback);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void listFeatures(IListFeaturesCallback listFeaturesCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceManager.DESCRIPTOR);
                    _data.writeStrongInterface(listFeaturesCallback);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void getFeatureDetails(
                    Feature feature, IFeatureDetailsCallback featureDetailsCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceManager.DESCRIPTOR);
                    _data.writeTypedObject(feature, 0);
                    _data.writeStrongInterface(featureDetailsCallback);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void requestFeatureDownload(
                    Feature feature,
                    AndroidFuture cancellationSignalFuture,
                    IDownloadCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceManager.DESCRIPTOR);
                    _data.writeTypedObject(feature, 0);
                    _data.writeTypedObject(cancellationSignalFuture, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void requestTokenInfo(
                    Feature feature,
                    Bundle requestBundle,
                    AndroidFuture cancellationSignalFuture,
                    ITokenInfoCallback tokenInfocallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceManager.DESCRIPTOR);
                    _data.writeTypedObject(feature, 0);
                    _data.writeTypedObject(requestBundle, 0);
                    _data.writeTypedObject(cancellationSignalFuture, 0);
                    _data.writeStrongInterface(tokenInfocallback);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void processRequest(
                    Feature feature,
                    Bundle requestBundle,
                    int requestType,
                    AndroidFuture cancellationSignalFuture,
                    AndroidFuture processingSignalFuture,
                    IResponseCallback responseCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceManager.DESCRIPTOR);
                    _data.writeTypedObject(feature, 0);
                    _data.writeTypedObject(requestBundle, 0);
                    _data.writeInt(requestType);
                    _data.writeTypedObject(cancellationSignalFuture, 0);
                    _data.writeTypedObject(processingSignalFuture, 0);
                    _data.writeStrongInterface(responseCallback);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public void processRequestStreaming(
                    Feature feature,
                    Bundle requestBundle,
                    int requestType,
                    AndroidFuture cancellationSignalFuture,
                    AndroidFuture processingSignalFuture,
                    IStreamingResponseCallback streamingCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceManager.DESCRIPTOR);
                    _data.writeTypedObject(feature, 0);
                    _data.writeTypedObject(requestBundle, 0);
                    _data.writeInt(requestType);
                    _data.writeTypedObject(cancellationSignalFuture, 0);
                    _data.writeTypedObject(processingSignalFuture, 0);
                    _data.writeStrongInterface(streamingCallback);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public String getRemoteServicePackageName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceManager.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IOnDeviceIntelligenceManager
            public List<InferenceInfo> getLatestInferenceInfo(long startTimeEpochMillis)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOnDeviceIntelligenceManager.DESCRIPTOR);
                    _data.writeLong(startTimeEpochMillis);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    List<InferenceInfo> _result =
                            _reply.createTypedArrayList(InferenceInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
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
