package android.hardware.camera2.extension;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.extension.ICaptureCallback;
import android.hardware.camera2.extension.IRequestProcessorImpl;
import android.hardware.camera2.extension.ISessionProcessorImpl;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes2.dex */
public interface ISessionProcessorImpl extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.ISessionProcessorImpl";

    public static class Default implements ISessionProcessorImpl {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void deInitSession(IBinder iBinder) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public LatencyPair getRealtimeCaptureLatency() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public CameraSessionConfig initSession(IBinder iBinder, String str, Map<String, CameraMetadataNative> map, OutputSurface outputSurface, OutputSurface outputSurface2, OutputSurface outputSurface3) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void onCaptureSessionEnd() throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void onCaptureSessionStart(IRequestProcessorImpl iRequestProcessorImpl, String str) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void setParameters(CaptureRequest captureRequest) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startCapture(ICaptureCallback iCaptureCallback, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startRepeating(ICaptureCallback iCaptureCallback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startTrigger(CaptureRequest captureRequest, ICaptureCallback iCaptureCallback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void stopRepeating() throws RemoteException {
        }
    }

    void deInitSession(IBinder iBinder) throws RemoteException;

    LatencyPair getRealtimeCaptureLatency() throws RemoteException;

    CameraSessionConfig initSession(IBinder iBinder, String str, Map<String, CameraMetadataNative> map, OutputSurface outputSurface, OutputSurface outputSurface2, OutputSurface outputSurface3) throws RemoteException;

    void onCaptureSessionEnd() throws RemoteException;

    void onCaptureSessionStart(IRequestProcessorImpl iRequestProcessorImpl, String str) throws RemoteException;

    void setParameters(CaptureRequest captureRequest) throws RemoteException;

    int startCapture(ICaptureCallback iCaptureCallback, boolean z) throws RemoteException;

    int startRepeating(ICaptureCallback iCaptureCallback) throws RemoteException;

    int startTrigger(CaptureRequest captureRequest, ICaptureCallback iCaptureCallback) throws RemoteException;

    void stopRepeating() throws RemoteException;

    public static abstract class Stub extends Binder implements ISessionProcessorImpl {
        static final int TRANSACTION_deInitSession = 2;
        static final int TRANSACTION_getRealtimeCaptureLatency = 10;
        static final int TRANSACTION_initSession = 1;
        static final int TRANSACTION_onCaptureSessionEnd = 4;
        static final int TRANSACTION_onCaptureSessionStart = 3;
        static final int TRANSACTION_setParameters = 8;
        static final int TRANSACTION_startCapture = 7;
        static final int TRANSACTION_startRepeating = 5;
        static final int TRANSACTION_startTrigger = 9;
        static final int TRANSACTION_stopRepeating = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, ISessionProcessorImpl.DESCRIPTOR);
        }

        public static ISessionProcessorImpl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISessionProcessorImpl.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISessionProcessorImpl)) {
                return (ISessionProcessorImpl) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "initSession";
                case 2:
                    return "deInitSession";
                case 3:
                    return "onCaptureSessionStart";
                case 4:
                    return "onCaptureSessionEnd";
                case 5:
                    return "startRepeating";
                case 6:
                    return "stopRepeating";
                case 7:
                    return "startCapture";
                case 8:
                    return "setParameters";
                case 9:
                    return "startTrigger";
                case 10:
                    return "getRealtimeCaptureLatency";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, final Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISessionProcessorImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISessionProcessorImpl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder strongBinder = parcel.readStrongBinder();
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    final HashMap map = i3 < 0 ? null : new HashMap();
                    IntStream.range(0, i3).forEach(new IntConsumer() { // from class: android.hardware.camera2.extension.ISessionProcessorImpl$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i4) {
                            Parcel parcel3 = parcel;
                            map.put(parcel3.readString(), (CameraMetadataNative) parcel3.readTypedObject(CameraMetadataNative.CREATOR));
                        }
                    });
                    OutputSurface outputSurface = (OutputSurface) parcel.readTypedObject(OutputSurface.CREATOR);
                    OutputSurface outputSurface2 = (OutputSurface) parcel.readTypedObject(OutputSurface.CREATOR);
                    OutputSurface outputSurface3 = (OutputSurface) parcel.readTypedObject(OutputSurface.CREATOR);
                    parcel.enforceNoDataAvail();
                    CameraSessionConfig cameraSessionConfigInitSession = initSession(strongBinder, string, map, outputSurface, outputSurface2, outputSurface3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraSessionConfigInitSession, 1);
                    return true;
                case 2:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    deInitSession(strongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IRequestProcessorImpl iRequestProcessorImplAsInterface = IRequestProcessorImpl.Stub.asInterface(parcel.readStrongBinder());
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onCaptureSessionStart(iRequestProcessorImplAsInterface, string2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    onCaptureSessionEnd();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ICaptureCallback iCaptureCallbackAsInterface = ICaptureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStartRepeating = startRepeating(iCaptureCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartRepeating);
                    return true;
                case 6:
                    stopRepeating();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    ICaptureCallback iCaptureCallbackAsInterface2 = ICaptureCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iStartCapture = startCapture(iCaptureCallbackAsInterface2, z);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartCapture);
                    return true;
                case 8:
                    CaptureRequest captureRequest = (CaptureRequest) parcel.readTypedObject(CaptureRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    setParameters(captureRequest);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    CaptureRequest captureRequest2 = (CaptureRequest) parcel.readTypedObject(CaptureRequest.CREATOR);
                    ICaptureCallback iCaptureCallbackAsInterface3 = ICaptureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStartTrigger = startTrigger(captureRequest2, iCaptureCallbackAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartTrigger);
                    return true;
                case 10:
                    LatencyPair realtimeCaptureLatency = getRealtimeCaptureLatency();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(realtimeCaptureLatency, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements ISessionProcessorImpl {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISessionProcessorImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public CameraSessionConfig initSession(IBinder iBinder, String str, Map<String, CameraMetadataNative> map, OutputSurface outputSurface, OutputSurface outputSurface2, OutputSurface outputSurface3) throws RemoteException {
                final Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    if (map == null) {
                        parcelObtain.writeInt(-1);
                    } else {
                        parcelObtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.hardware.camera2.extension.ISessionProcessorImpl$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                ISessionProcessorImpl.Stub.Proxy.lambda$initSession$0(parcelObtain, (String) obj, (CameraMetadataNative) obj2);
                            }
                        });
                    }
                    parcelObtain.writeTypedObject(outputSurface, 0);
                    parcelObtain.writeTypedObject(outputSurface2, 0);
                    parcelObtain.writeTypedObject(outputSurface3, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CameraSessionConfig) parcelObtain2.readTypedObject(CameraSessionConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            static /* synthetic */ void lambda$initSession$0(Parcel parcel, String str, CameraMetadataNative cameraMetadataNative) {
                parcel.writeString(str);
                parcel.writeTypedObject(cameraMetadataNative, 0);
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void deInitSession(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void onCaptureSessionStart(IRequestProcessorImpl iRequestProcessorImpl, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRequestProcessorImpl);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void onCaptureSessionEnd() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public int startRepeating(ICaptureCallback iCaptureCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCaptureCallback);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void stopRepeating() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public int startCapture(ICaptureCallback iCaptureCallback, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCaptureCallback);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void setParameters(CaptureRequest captureRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(captureRequest, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public int startTrigger(CaptureRequest captureRequest, ICaptureCallback iCaptureCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(captureRequest, 0);
                    parcelObtain.writeStrongInterface(iCaptureCallback);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public LatencyPair getRealtimeCaptureLatency() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (LatencyPair) parcelObtain2.readTypedObject(LatencyPair.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
