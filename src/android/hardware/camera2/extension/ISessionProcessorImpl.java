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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISessionProcessorImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISessionProcessorImpl)) {
                return (ISessionProcessorImpl) queryLocalInterface;
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
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    final HashMap hashMap = readInt < 0 ? null : new HashMap();
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: android.hardware.camera2.extension.ISessionProcessorImpl$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            hashMap.put(r0.readString(), (CameraMetadataNative) Parcel.this.readTypedObject(CameraMetadataNative.CREATOR));
                        }
                    });
                    OutputSurface outputSurface = (OutputSurface) parcel.readTypedObject(OutputSurface.CREATOR);
                    OutputSurface outputSurface2 = (OutputSurface) parcel.readTypedObject(OutputSurface.CREATOR);
                    OutputSurface outputSurface3 = (OutputSurface) parcel.readTypedObject(OutputSurface.CREATOR);
                    parcel.enforceNoDataAvail();
                    CameraSessionConfig initSession = initSession(readStrongBinder, readString, hashMap, outputSurface, outputSurface2, outputSurface3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(initSession, 1);
                    return true;
                case 2:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    deInitSession(readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IRequestProcessorImpl asInterface = IRequestProcessorImpl.Stub.asInterface(parcel.readStrongBinder());
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onCaptureSessionStart(asInterface, readString2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    onCaptureSessionEnd();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ICaptureCallback asInterface2 = ICaptureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startRepeating = startRepeating(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(startRepeating);
                    return true;
                case 6:
                    stopRepeating();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    ICaptureCallback asInterface3 = ICaptureCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int startCapture = startCapture(asInterface3, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(startCapture);
                    return true;
                case 8:
                    CaptureRequest captureRequest = (CaptureRequest) parcel.readTypedObject(CaptureRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    setParameters(captureRequest);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    CaptureRequest captureRequest2 = (CaptureRequest) parcel.readTypedObject(CaptureRequest.CREATOR);
                    ICaptureCallback asInterface4 = ICaptureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startTrigger = startTrigger(captureRequest2, asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeInt(startTrigger);
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
                final Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    if (map == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.hardware.camera2.extension.ISessionProcessorImpl$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                ISessionProcessorImpl.Stub.Proxy.lambda$initSession$0(Parcel.this, (String) obj, (CameraMetadataNative) obj2);
                            }
                        });
                    }
                    obtain.writeTypedObject(outputSurface, 0);
                    obtain.writeTypedObject(outputSurface2, 0);
                    obtain.writeTypedObject(outputSurface3, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraSessionConfig) obtain2.readTypedObject(CameraSessionConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            static /* synthetic */ void lambda$initSession$0(Parcel parcel, String str, CameraMetadataNative cameraMetadataNative) {
                parcel.writeString(str);
                parcel.writeTypedObject(cameraMetadataNative, 0);
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void deInitSession(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void onCaptureSessionStart(IRequestProcessorImpl iRequestProcessorImpl, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    obtain.writeStrongInterface(iRequestProcessorImpl);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void onCaptureSessionEnd() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public int startRepeating(ICaptureCallback iCaptureCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    obtain.writeStrongInterface(iCaptureCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void stopRepeating() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public int startCapture(ICaptureCallback iCaptureCallback, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    obtain.writeStrongInterface(iCaptureCallback);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void setParameters(CaptureRequest captureRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(captureRequest, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public int startTrigger(CaptureRequest captureRequest, ICaptureCallback iCaptureCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(captureRequest, 0);
                    obtain.writeStrongInterface(iCaptureCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public LatencyPair getRealtimeCaptureLatency() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LatencyPair) obtain2.readTypedObject(LatencyPair.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
