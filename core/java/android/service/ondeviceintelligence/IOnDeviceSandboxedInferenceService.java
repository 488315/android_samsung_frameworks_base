package android.service.ondeviceintelligence;

import android.app.ondeviceintelligence.Feature;
import android.app.ondeviceintelligence.IResponseCallback;
import android.app.ondeviceintelligence.IStreamingResponseCallback;
import android.app.ondeviceintelligence.ITokenInfoCallback;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

import com.android.internal.infra.AndroidFuture;

/* loaded from: classes3.dex */
public interface IOnDeviceSandboxedInferenceService extends IInterface {
    public static final String DESCRIPTOR =
            "android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService";

    void processRequest(
            int i,
            Feature feature,
            Bundle bundle,
            int i2,
            AndroidFuture androidFuture,
            AndroidFuture androidFuture2,
            IResponseCallback iResponseCallback)
            throws RemoteException;

    void processRequestStreaming(
            int i,
            Feature feature,
            Bundle bundle,
            int i2,
            AndroidFuture androidFuture,
            AndroidFuture androidFuture2,
            IStreamingResponseCallback iStreamingResponseCallback)
            throws RemoteException;

    void registerRemoteStorageService(
            IRemoteStorageService iRemoteStorageService, IRemoteCallback iRemoteCallback)
            throws RemoteException;

    void requestTokenInfo(
            int i,
            Feature feature,
            Bundle bundle,
            AndroidFuture androidFuture,
            ITokenInfoCallback iTokenInfoCallback)
            throws RemoteException;

    void updateProcessingState(
            Bundle bundle, IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback)
            throws RemoteException;

    public static class Default implements IOnDeviceSandboxedInferenceService {
        @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
        public void registerRemoteStorageService(
                IRemoteStorageService storageService, IRemoteCallback remoteCallback)
                throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
        public void requestTokenInfo(
                int callerUid,
                Feature feature,
                Bundle request,
                AndroidFuture cancellationSignal,
                ITokenInfoCallback tokenInfoCallback)
                throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
        public void processRequest(
                int callerUid,
                Feature feature,
                Bundle request,
                int requestType,
                AndroidFuture cancellationSignal,
                AndroidFuture processingSignal,
                IResponseCallback callback)
                throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
        public void processRequestStreaming(
                int callerUid,
                Feature feature,
                Bundle request,
                int requestType,
                AndroidFuture cancellationSignal,
                AndroidFuture processingSignal,
                IStreamingResponseCallback callback)
                throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
        public void updateProcessingState(
                Bundle processingState, IProcessingUpdateStatusCallback callback)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IOnDeviceSandboxedInferenceService {
        static final int TRANSACTION_processRequest = 3;
        static final int TRANSACTION_processRequestStreaming = 4;
        static final int TRANSACTION_registerRemoteStorageService = 1;
        static final int TRANSACTION_requestTokenInfo = 2;
        static final int TRANSACTION_updateProcessingState = 5;

        public Stub() {
            attachInterface(this, IOnDeviceSandboxedInferenceService.DESCRIPTOR);
        }

        public static IOnDeviceSandboxedInferenceService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOnDeviceSandboxedInferenceService.DESCRIPTOR);
            if (iin != null && (iin instanceof IOnDeviceSandboxedInferenceService)) {
                return (IOnDeviceSandboxedInferenceService) iin;
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
                    return "registerRemoteStorageService";
                case 2:
                    return "requestTokenInfo";
                case 3:
                    return "processRequest";
                case 4:
                    return "processRequestStreaming";
                case 5:
                    return "updateProcessingState";
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
                data.enforceInterface(IOnDeviceSandboxedInferenceService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IOnDeviceSandboxedInferenceService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IRemoteStorageService _arg0 =
                            IRemoteStorageService.Stub.asInterface(data.readStrongBinder());
                    IRemoteCallback _arg1 =
                            IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerRemoteStorageService(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    Feature _arg12 = (Feature) data.readTypedObject(Feature.CREATOR);
                    Bundle _arg2 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    AndroidFuture _arg3 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    ITokenInfoCallback _arg4 =
                            ITokenInfoCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    requestTokenInfo(_arg02, _arg12, _arg2, _arg3, _arg4);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    Feature _arg13 = (Feature) data.readTypedObject(Feature.CREATOR);
                    Bundle _arg22 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg32 = data.readInt();
                    AndroidFuture _arg42 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    AndroidFuture _arg5 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    IResponseCallback _arg6 =
                            IResponseCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    processRequest(_arg03, _arg13, _arg22, _arg32, _arg42, _arg5, _arg6);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    Feature _arg14 = (Feature) data.readTypedObject(Feature.CREATOR);
                    Bundle _arg23 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg33 = data.readInt();
                    AndroidFuture _arg43 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    AndroidFuture _arg52 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    IStreamingResponseCallback _arg62 =
                            IStreamingResponseCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    processRequestStreaming(_arg04, _arg14, _arg23, _arg33, _arg43, _arg52, _arg62);
                    return true;
                case 5:
                    Bundle _arg05 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    IProcessingUpdateStatusCallback _arg15 =
                            IProcessingUpdateStatusCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    updateProcessingState(_arg05, _arg15);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOnDeviceSandboxedInferenceService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnDeviceSandboxedInferenceService.DESCRIPTOR;
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
            public void registerRemoteStorageService(
                    IRemoteStorageService storageService, IRemoteCallback remoteCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceSandboxedInferenceService.DESCRIPTOR);
                    _data.writeStrongInterface(storageService);
                    _data.writeStrongInterface(remoteCallback);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
            public void requestTokenInfo(
                    int callerUid,
                    Feature feature,
                    Bundle request,
                    AndroidFuture cancellationSignal,
                    ITokenInfoCallback tokenInfoCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceSandboxedInferenceService.DESCRIPTOR);
                    _data.writeInt(callerUid);
                    _data.writeTypedObject(feature, 0);
                    _data.writeTypedObject(request, 0);
                    _data.writeTypedObject(cancellationSignal, 0);
                    _data.writeStrongInterface(tokenInfoCallback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
            public void processRequest(
                    int callerUid,
                    Feature feature,
                    Bundle request,
                    int requestType,
                    AndroidFuture cancellationSignal,
                    AndroidFuture processingSignal,
                    IResponseCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceSandboxedInferenceService.DESCRIPTOR);
                    _data.writeInt(callerUid);
                    _data.writeTypedObject(feature, 0);
                    _data.writeTypedObject(request, 0);
                    _data.writeInt(requestType);
                    _data.writeTypedObject(cancellationSignal, 0);
                    _data.writeTypedObject(processingSignal, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
            public void processRequestStreaming(
                    int callerUid,
                    Feature feature,
                    Bundle request,
                    int requestType,
                    AndroidFuture cancellationSignal,
                    AndroidFuture processingSignal,
                    IStreamingResponseCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceSandboxedInferenceService.DESCRIPTOR);
                    _data.writeInt(callerUid);
                    _data.writeTypedObject(feature, 0);
                    _data.writeTypedObject(request, 0);
                    _data.writeInt(requestType);
                    _data.writeTypedObject(cancellationSignal, 0);
                    _data.writeTypedObject(processingSignal, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService
            public void updateProcessingState(
                    Bundle processingState, IProcessingUpdateStatusCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnDeviceSandboxedInferenceService.DESCRIPTOR);
                    _data.writeTypedObject(processingState, 0);
                    _data.writeStrongInterface(callback);
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
