package android.hardware.camera2.extension;

import android.hardware.camera2.extension.ICaptureProcessorImpl;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IImageCaptureExtenderImpl extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.IImageCaptureExtenderImpl";

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

    public static class Default implements IImageCaptureExtenderImpl {
        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public void onInit(IBinder token, String cameraId, CameraMetadataNative cameraCharacteristics) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public void onDeInit(IBinder token) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public CaptureStageImpl onPresetSession() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public CaptureStageImpl onEnableSession() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public CaptureStageImpl onDisableSession() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public int getSessionType() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public boolean isExtensionAvailable(String cameraId, CameraMetadataNative chars) throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public void init(String cameraId, CameraMetadataNative chars) throws RemoteException {
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
        public int getMaxCaptureStage() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public List<SizeList> getSupportedResolutions() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public List<SizeList> getSupportedPostviewResolutions(Size captureSize) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public LatencyRange getEstimatedCaptureLatencyRange(Size outputSize) throws RemoteException {
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
        public boolean isCaptureProcessProgressAvailable() throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public LatencyPair getRealtimeCaptureLatency() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public boolean isPostviewAvailable() throws RemoteException {
            return false;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

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

        public Stub() {
            attachInterface(this, IImageCaptureExtenderImpl.DESCRIPTOR);
        }

        public static IImageCaptureExtenderImpl asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IImageCaptureExtenderImpl.DESCRIPTOR);
            if (iin != null && (iin instanceof IImageCaptureExtenderImpl)) {
                return (IImageCaptureExtenderImpl) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IImageCaptureExtenderImpl.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IImageCaptureExtenderImpl.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            IBinder _arg0 = data.readStrongBinder();
                            String _arg1 = data.readString();
                            CameraMetadataNative _arg2 = (CameraMetadataNative) data.readTypedObject(CameraMetadataNative.CREATOR);
                            data.enforceNoDataAvail();
                            onInit(_arg0, _arg1, _arg2);
                            reply.writeNoException();
                            return true;
                        case 2:
                            IBinder _arg02 = data.readStrongBinder();
                            data.enforceNoDataAvail();
                            onDeInit(_arg02);
                            reply.writeNoException();
                            return true;
                        case 3:
                            CaptureStageImpl _result = onPresetSession();
                            reply.writeNoException();
                            reply.writeTypedObject(_result, 1);
                            return true;
                        case 4:
                            CaptureStageImpl _result2 = onEnableSession();
                            reply.writeNoException();
                            reply.writeTypedObject(_result2, 1);
                            return true;
                        case 5:
                            CaptureStageImpl _result3 = onDisableSession();
                            reply.writeNoException();
                            reply.writeTypedObject(_result3, 1);
                            return true;
                        case 6:
                            int _result4 = getSessionType();
                            reply.writeNoException();
                            reply.writeInt(_result4);
                            return true;
                        case 7:
                            String _arg03 = data.readString();
                            CameraMetadataNative _arg12 = (CameraMetadataNative) data.readTypedObject(CameraMetadataNative.CREATOR);
                            data.enforceNoDataAvail();
                            boolean _result5 = isExtensionAvailable(_arg03, _arg12);
                            reply.writeNoException();
                            reply.writeBoolean(_result5);
                            return true;
                        case 8:
                            String _arg04 = data.readString();
                            CameraMetadataNative _arg13 = (CameraMetadataNative) data.readTypedObject(CameraMetadataNative.CREATOR);
                            data.enforceNoDataAvail();
                            init(_arg04, _arg13);
                            reply.writeNoException();
                            return true;
                        case 9:
                            ICaptureProcessorImpl _result6 = getCaptureProcessor();
                            reply.writeNoException();
                            reply.writeStrongInterface(_result6);
                            return true;
                        case 10:
                            List<CaptureStageImpl> _result7 = getCaptureStages();
                            reply.writeNoException();
                            reply.writeTypedList(_result7, 1);
                            return true;
                        case 11:
                            int _result8 = getMaxCaptureStage();
                            reply.writeNoException();
                            reply.writeInt(_result8);
                            return true;
                        case 12:
                            List<SizeList> _result9 = getSupportedResolutions();
                            reply.writeNoException();
                            reply.writeTypedList(_result9, 1);
                            return true;
                        case 13:
                            Size _arg05 = (Size) data.readTypedObject(Size.CREATOR);
                            data.enforceNoDataAvail();
                            List<SizeList> _result10 = getSupportedPostviewResolutions(_arg05);
                            reply.writeNoException();
                            reply.writeTypedList(_result10, 1);
                            return true;
                        case 14:
                            Size _arg06 = (Size) data.readTypedObject(Size.CREATOR);
                            data.enforceNoDataAvail();
                            LatencyRange _result11 = getEstimatedCaptureLatencyRange(_arg06);
                            reply.writeNoException();
                            reply.writeTypedObject(_result11, 1);
                            return true;
                        case 15:
                            CameraMetadataNative _result12 = getAvailableCaptureRequestKeys();
                            reply.writeNoException();
                            reply.writeTypedObject(_result12, 1);
                            return true;
                        case 16:
                            CameraMetadataNative _result13 = getAvailableCaptureResultKeys();
                            reply.writeNoException();
                            reply.writeTypedObject(_result13, 1);
                            return true;
                        case 17:
                            boolean _result14 = isCaptureProcessProgressAvailable();
                            reply.writeNoException();
                            reply.writeBoolean(_result14);
                            return true;
                        case 18:
                            LatencyPair _result15 = getRealtimeCaptureLatency();
                            reply.writeNoException();
                            reply.writeTypedObject(_result15, 1);
                            return true;
                        case 19:
                            boolean _result16 = isPostviewAvailable();
                            reply.writeNoException();
                            reply.writeBoolean(_result16);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IImageCaptureExtenderImpl {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImageCaptureExtenderImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public void onInit(IBinder token, String cameraId, CameraMetadataNative cameraCharacteristics) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(cameraId);
                    _data.writeTypedObject(cameraCharacteristics, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public void onDeInit(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public CaptureStageImpl onPresetSession() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    CaptureStageImpl _result = (CaptureStageImpl) _reply.readTypedObject(CaptureStageImpl.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public CaptureStageImpl onEnableSession() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    CaptureStageImpl _result = (CaptureStageImpl) _reply.readTypedObject(CaptureStageImpl.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public CaptureStageImpl onDisableSession() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    CaptureStageImpl _result = (CaptureStageImpl) _reply.readTypedObject(CaptureStageImpl.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public int getSessionType() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public boolean isExtensionAvailable(String cameraId, CameraMetadataNative chars) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeTypedObject(chars, 0);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public void init(String cameraId, CameraMetadataNative chars) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeTypedObject(chars, 0);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public ICaptureProcessorImpl getCaptureProcessor() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    ICaptureProcessorImpl _result = ICaptureProcessorImpl.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public List<CaptureStageImpl> getCaptureStages() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    List<CaptureStageImpl> _result = _reply.createTypedArrayList(CaptureStageImpl.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public int getMaxCaptureStage() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public List<SizeList> getSupportedResolutions() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    List<SizeList> _result = _reply.createTypedArrayList(SizeList.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public List<SizeList> getSupportedPostviewResolutions(Size captureSize) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    _data.writeTypedObject(captureSize, 0);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    List<SizeList> _result = _reply.createTypedArrayList(SizeList.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public LatencyRange getEstimatedCaptureLatencyRange(Size outputSize) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    _data.writeTypedObject(outputSize, 0);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    LatencyRange _result = (LatencyRange) _reply.readTypedObject(LatencyRange.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public CameraMetadataNative getAvailableCaptureRequestKeys() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    CameraMetadataNative _result = (CameraMetadataNative) _reply.readTypedObject(CameraMetadataNative.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public CameraMetadataNative getAvailableCaptureResultKeys() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    CameraMetadataNative _result = (CameraMetadataNative) _reply.readTypedObject(CameraMetadataNative.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public boolean isCaptureProcessProgressAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public LatencyPair getRealtimeCaptureLatency() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    LatencyPair _result = (LatencyPair) _reply.readTypedObject(LatencyPair.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public boolean isPostviewAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 18;
        }
    }
}
