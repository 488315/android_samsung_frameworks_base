package android.hardware.camera2.extension;

import android.hardware.camera2.extension.ICaptureProcessorImpl;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IImageCaptureExtenderImpl extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.IImageCaptureExtenderImpl";

    public static class Default implements IImageCaptureExtenderImpl {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public CameraMetadataNative getAvailableCaptureRequestKeys() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public CameraMetadataNative getAvailableCaptureResultKeys() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public ICaptureProcessorImpl getCaptureProcessor() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public List<CaptureStageImpl> getCaptureStages() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public LatencyRange getEstimatedCaptureLatencyRange(Size size) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public int getMaxCaptureStage() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public LatencyPair getRealtimeCaptureLatency() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public int getSessionType() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public List<SizeList> getSupportedPostviewResolutions(Size size) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public List<SizeList> getSupportedResolutions() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public void init(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public boolean isCaptureProcessProgressAvailable() throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public boolean isExtensionAvailable(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public boolean isPostviewAvailable() throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public void onDeInit(IBinder iBinder) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public CaptureStageImpl onDisableSession() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public CaptureStageImpl onEnableSession() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public void onInit(IBinder iBinder, String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public CaptureStageImpl onPresetSession() throws RemoteException {
            return null;
        }
    }

    CameraMetadataNative getAvailableCaptureRequestKeys() throws RemoteException;

    CameraMetadataNative getAvailableCaptureResultKeys() throws RemoteException;

    ICaptureProcessorImpl getCaptureProcessor() throws RemoteException;

    List<CaptureStageImpl> getCaptureStages() throws RemoteException;

    LatencyRange getEstimatedCaptureLatencyRange(Size size) throws RemoteException;

    int getMaxCaptureStage() throws RemoteException;

    LatencyPair getRealtimeCaptureLatency() throws RemoteException;

    int getSessionType() throws RemoteException;

    List<SizeList> getSupportedPostviewResolutions(Size size) throws RemoteException;

    List<SizeList> getSupportedResolutions() throws RemoteException;

    void init(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException;

    boolean isCaptureProcessProgressAvailable() throws RemoteException;

    boolean isExtensionAvailable(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException;

    boolean isPostviewAvailable() throws RemoteException;

    void onDeInit(IBinder iBinder) throws RemoteException;

    CaptureStageImpl onDisableSession() throws RemoteException;

    CaptureStageImpl onEnableSession() throws RemoteException;

    void onInit(IBinder iBinder, String str, CameraMetadataNative cameraMetadataNative) throws RemoteException;

    CaptureStageImpl onPresetSession() throws RemoteException;

    public static abstract class Stub extends Binder implements IImageCaptureExtenderImpl {
        static final int TRANSACTION_getAvailableCaptureRequestKeys = 15;
        static final int TRANSACTION_getAvailableCaptureResultKeys = 16;
        static final int TRANSACTION_getCaptureProcessor = 9;
        static final int TRANSACTION_getCaptureStages = 10;
        static final int TRANSACTION_getEstimatedCaptureLatencyRange = 14;
        static final int TRANSACTION_getMaxCaptureStage = 11;
        static final int TRANSACTION_getRealtimeCaptureLatency = 18;
        static final int TRANSACTION_getSessionType = 6;
        static final int TRANSACTION_getSupportedPostviewResolutions = 13;
        static final int TRANSACTION_getSupportedResolutions = 12;
        static final int TRANSACTION_init = 8;
        static final int TRANSACTION_isCaptureProcessProgressAvailable = 17;
        static final int TRANSACTION_isExtensionAvailable = 7;
        static final int TRANSACTION_isPostviewAvailable = 19;
        static final int TRANSACTION_onDeInit = 2;
        static final int TRANSACTION_onDisableSession = 5;
        static final int TRANSACTION_onEnableSession = 4;
        static final int TRANSACTION_onInit = 1;
        static final int TRANSACTION_onPresetSession = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 18;
        }

        public Stub() {
            attachInterface(this, IImageCaptureExtenderImpl.DESCRIPTOR);
        }

        public static IImageCaptureExtenderImpl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImageCaptureExtenderImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImageCaptureExtenderImpl)) {
                return (IImageCaptureExtenderImpl) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onInit";
                case 2:
                    return "onDeInit";
                case 3:
                    return "onPresetSession";
                case 4:
                    return "onEnableSession";
                case 5:
                    return "onDisableSession";
                case 6:
                    return "getSessionType";
                case 7:
                    return "isExtensionAvailable";
                case 8:
                    return "init";
                case 9:
                    return "getCaptureProcessor";
                case 10:
                    return "getCaptureStages";
                case 11:
                    return "getMaxCaptureStage";
                case 12:
                    return "getSupportedResolutions";
                case 13:
                    return "getSupportedPostviewResolutions";
                case 14:
                    return "getEstimatedCaptureLatencyRange";
                case 15:
                    return "getAvailableCaptureRequestKeys";
                case 16:
                    return "getAvailableCaptureResultKeys";
                case 17:
                    return "isCaptureProcessProgressAvailable";
                case 18:
                    return "getRealtimeCaptureLatency";
                case 19:
                    return "isPostviewAvailable";
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
                parcel.enforceInterface(IImageCaptureExtenderImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImageCaptureExtenderImpl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    String readString = parcel.readString();
                    CameraMetadataNative cameraMetadataNative = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    onInit(readStrongBinder, readString, cameraMetadataNative);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onDeInit(readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    CaptureStageImpl onPresetSession = onPresetSession();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(onPresetSession, 1);
                    return true;
                case 4:
                    CaptureStageImpl onEnableSession = onEnableSession();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(onEnableSession, 1);
                    return true;
                case 5:
                    CaptureStageImpl onDisableSession = onDisableSession();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(onDisableSession, 1);
                    return true;
                case 6:
                    int sessionType = getSessionType();
                    parcel2.writeNoException();
                    parcel2.writeInt(sessionType);
                    return true;
                case 7:
                    String readString2 = parcel.readString();
                    CameraMetadataNative cameraMetadataNative2 = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isExtensionAvailable = isExtensionAvailable(readString2, cameraMetadataNative2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isExtensionAvailable);
                    return true;
                case 8:
                    String readString3 = parcel.readString();
                    CameraMetadataNative cameraMetadataNative3 = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    init(readString3, cameraMetadataNative3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ICaptureProcessorImpl captureProcessor = getCaptureProcessor();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(captureProcessor);
                    return true;
                case 10:
                    List<CaptureStageImpl> captureStages = getCaptureStages();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(captureStages, 1);
                    return true;
                case 11:
                    int maxCaptureStage = getMaxCaptureStage();
                    parcel2.writeNoException();
                    parcel2.writeInt(maxCaptureStage);
                    return true;
                case 12:
                    List<SizeList> supportedResolutions = getSupportedResolutions();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(supportedResolutions, 1);
                    return true;
                case 13:
                    Size size = (Size) parcel.readTypedObject(Size.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<SizeList> supportedPostviewResolutions = getSupportedPostviewResolutions(size);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(supportedPostviewResolutions, 1);
                    return true;
                case 14:
                    Size size2 = (Size) parcel.readTypedObject(Size.CREATOR);
                    parcel.enforceNoDataAvail();
                    LatencyRange estimatedCaptureLatencyRange = getEstimatedCaptureLatencyRange(size2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(estimatedCaptureLatencyRange, 1);
                    return true;
                case 15:
                    CameraMetadataNative availableCaptureRequestKeys = getAvailableCaptureRequestKeys();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(availableCaptureRequestKeys, 1);
                    return true;
                case 16:
                    CameraMetadataNative availableCaptureResultKeys = getAvailableCaptureResultKeys();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(availableCaptureResultKeys, 1);
                    return true;
                case 17:
                    boolean isCaptureProcessProgressAvailable = isCaptureProcessProgressAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCaptureProcessProgressAvailable);
                    return true;
                case 18:
                    LatencyPair realtimeCaptureLatency = getRealtimeCaptureLatency();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(realtimeCaptureLatency, 1);
                    return true;
                case 19:
                    boolean isPostviewAvailable = isPostviewAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPostviewAvailable);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImageCaptureExtenderImpl {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImageCaptureExtenderImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public void onInit(IBinder iBinder, String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public void onDeInit(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public CaptureStageImpl onPresetSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CaptureStageImpl) obtain2.readTypedObject(CaptureStageImpl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public CaptureStageImpl onEnableSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CaptureStageImpl) obtain2.readTypedObject(CaptureStageImpl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public CaptureStageImpl onDisableSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CaptureStageImpl) obtain2.readTypedObject(CaptureStageImpl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public int getSessionType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public boolean isExtensionAvailable(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public void init(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public ICaptureProcessorImpl getCaptureProcessor() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICaptureProcessorImpl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public List<CaptureStageImpl> getCaptureStages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(CaptureStageImpl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public int getMaxCaptureStage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public List<SizeList> getSupportedResolutions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SizeList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public List<SizeList> getSupportedPostviewResolutions(Size size) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    obtain.writeTypedObject(size, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SizeList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public LatencyRange getEstimatedCaptureLatencyRange(Size size) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    obtain.writeTypedObject(size, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LatencyRange) obtain2.readTypedObject(LatencyRange.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public CameraMetadataNative getAvailableCaptureRequestKeys() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraMetadataNative) obtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public CameraMetadataNative getAvailableCaptureResultKeys() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraMetadataNative) obtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public boolean isCaptureProcessProgressAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public LatencyPair getRealtimeCaptureLatency() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LatencyPair) obtain2.readTypedObject(LatencyPair.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public boolean isPostviewAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
