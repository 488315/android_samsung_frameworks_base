package android.hardware.camera2.extension;

import android.hardware.camera2.extension.IPreviewImageProcessorImpl;
import android.hardware.camera2.extension.IRequestUpdateProcessorImpl;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IPreviewExtenderImpl extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.IPreviewExtenderImpl";
    public static final int PROCESSOR_TYPE_IMAGE_PROCESSOR = 1;
    public static final int PROCESSOR_TYPE_NONE = 2;
    public static final int PROCESSOR_TYPE_REQUEST_UPDATE_ONLY = 0;

    public static class Default implements IPreviewExtenderImpl {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public CaptureStageImpl getCaptureStage() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public IPreviewImageProcessorImpl getPreviewImageProcessor() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public int getProcessorType() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public IRequestUpdateProcessorImpl getRequestUpdateProcessor() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public int getSessionType() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public List<SizeList> getSupportedResolutions() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public void init(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public boolean isExtensionAvailable(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public void onDeInit(IBinder iBinder) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public CaptureStageImpl onDisableSession() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public CaptureStageImpl onEnableSession() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public void onInit(IBinder iBinder, String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public CaptureStageImpl onPresetSession() throws RemoteException {
            return null;
        }
    }

    CaptureStageImpl getCaptureStage() throws RemoteException;

    IPreviewImageProcessorImpl getPreviewImageProcessor() throws RemoteException;

    int getProcessorType() throws RemoteException;

    IRequestUpdateProcessorImpl getRequestUpdateProcessor() throws RemoteException;

    int getSessionType() throws RemoteException;

    List<SizeList> getSupportedResolutions() throws RemoteException;

    void init(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException;

    boolean isExtensionAvailable(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException;

    void onDeInit(IBinder iBinder) throws RemoteException;

    CaptureStageImpl onDisableSession() throws RemoteException;

    CaptureStageImpl onEnableSession() throws RemoteException;

    void onInit(IBinder iBinder, String str, CameraMetadataNative cameraMetadataNative) throws RemoteException;

    CaptureStageImpl onPresetSession() throws RemoteException;

    public static abstract class Stub extends Binder implements IPreviewExtenderImpl {
        static final int TRANSACTION_getCaptureStage = 8;
        static final int TRANSACTION_getPreviewImageProcessor = 11;
        static final int TRANSACTION_getProcessorType = 10;
        static final int TRANSACTION_getRequestUpdateProcessor = 12;
        static final int TRANSACTION_getSessionType = 9;
        static final int TRANSACTION_getSupportedResolutions = 13;
        static final int TRANSACTION_init = 6;
        static final int TRANSACTION_isExtensionAvailable = 7;
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
            return 12;
        }

        public Stub() {
            attachInterface(this, IPreviewExtenderImpl.DESCRIPTOR);
        }

        public static IPreviewExtenderImpl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPreviewExtenderImpl.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPreviewExtenderImpl)) {
                return (IPreviewExtenderImpl) iInterfaceQueryLocalInterface;
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
                    return "init";
                case 7:
                    return "isExtensionAvailable";
                case 8:
                    return "getCaptureStage";
                case 9:
                    return "getSessionType";
                case 10:
                    return "getProcessorType";
                case 11:
                    return "getPreviewImageProcessor";
                case 12:
                    return "getRequestUpdateProcessor";
                case 13:
                    return "getSupportedResolutions";
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
                parcel.enforceInterface(IPreviewExtenderImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPreviewExtenderImpl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder strongBinder = parcel.readStrongBinder();
                    String string = parcel.readString();
                    CameraMetadataNative cameraMetadataNative = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    onInit(strongBinder, string, cameraMetadataNative);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onDeInit(strongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    CaptureStageImpl captureStageImplOnPresetSession = onPresetSession();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(captureStageImplOnPresetSession, 1);
                    return true;
                case 4:
                    CaptureStageImpl captureStageImplOnEnableSession = onEnableSession();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(captureStageImplOnEnableSession, 1);
                    return true;
                case 5:
                    CaptureStageImpl captureStageImplOnDisableSession = onDisableSession();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(captureStageImplOnDisableSession, 1);
                    return true;
                case 6:
                    String string2 = parcel.readString();
                    CameraMetadataNative cameraMetadataNative2 = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    init(string2, cameraMetadataNative2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string3 = parcel.readString();
                    CameraMetadataNative cameraMetadataNative3 = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsExtensionAvailable = isExtensionAvailable(string3, cameraMetadataNative3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsExtensionAvailable);
                    return true;
                case 8:
                    CaptureStageImpl captureStage = getCaptureStage();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(captureStage, 1);
                    return true;
                case 9:
                    int sessionType = getSessionType();
                    parcel2.writeNoException();
                    parcel2.writeInt(sessionType);
                    return true;
                case 10:
                    int processorType = getProcessorType();
                    parcel2.writeNoException();
                    parcel2.writeInt(processorType);
                    return true;
                case 11:
                    IPreviewImageProcessorImpl previewImageProcessor = getPreviewImageProcessor();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(previewImageProcessor);
                    return true;
                case 12:
                    IRequestUpdateProcessorImpl requestUpdateProcessor = getRequestUpdateProcessor();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(requestUpdateProcessor);
                    return true;
                case 13:
                    List<SizeList> supportedResolutions = getSupportedResolutions();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(supportedResolutions, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPreviewExtenderImpl {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPreviewExtenderImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public void onInit(IBinder iBinder, String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewExtenderImpl.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public void onDeInit(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewExtenderImpl.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public CaptureStageImpl onPresetSession() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CaptureStageImpl) parcelObtain2.readTypedObject(CaptureStageImpl.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public CaptureStageImpl onEnableSession() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CaptureStageImpl) parcelObtain2.readTypedObject(CaptureStageImpl.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public CaptureStageImpl onDisableSession() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CaptureStageImpl) parcelObtain2.readTypedObject(CaptureStageImpl.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public void init(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewExtenderImpl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public boolean isExtensionAvailable(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewExtenderImpl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public CaptureStageImpl getCaptureStage() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CaptureStageImpl) parcelObtain2.readTypedObject(CaptureStageImpl.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public int getSessionType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public int getProcessorType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public IPreviewImageProcessorImpl getPreviewImageProcessor() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IPreviewImageProcessorImpl.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public IRequestUpdateProcessorImpl getRequestUpdateProcessor() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IRequestUpdateProcessorImpl.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public List<SizeList> getSupportedResolutions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SizeList.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
