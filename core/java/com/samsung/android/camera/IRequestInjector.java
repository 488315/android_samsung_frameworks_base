package com.samsung.android.camera;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;

public interface IRequestInjector extends IInterface {
    public static final String BUNDLE_KEY_I32 = "key.i32";
    public static final String BUNDLE_KEY_TAG_NAME = "key.tagName";
    public static final String BUNDLE_KEY_U8 = "key.u8";
    public static final String DESCRIPTOR = "com.samsung.android.camera.IRequestInjector";
    public static final String UNIHAL_VIDEO_MODE = "samsung.android.unihal.videoMode";
    public static final int UNIHAL_VIDEO_MODE_AUTO_FRAMING = 40;
    public static final int UNIHAL_VIDEO_MODE_BEAUTY = 50;
    public static final int UNIHAL_VIDEO_MODE_BOKEH = 20;
    public static final int UNIHAL_VIDEO_MODE_OFF = 0;
    public static final int UNIHAL_VIDEO_MODE_SEGMENTATION = 30;
    public static final int UNIHAL_VIDEO_MODE_VDIS = 10;
    public static final String UNIHAL_VIDEO_SEGMENTATION_BG_IMG_NUM =
            "samsung.android.unihal.videoSegmentationBgImgNum";
    public static final String UNIHAL_VIDEO_SEGMENTATION_BLUR_LEVEL =
            "samsung.android.unihal.videoSegmentationBlurLevel";
    public static final String UNIHAL_VIDEO_SEGMENTATION_MODE =
            "samsung.android.unihal.videoSegmentationMode";
    public static final int UNIHAL_VIDEO_SEGMENTATION_MODE_BACKGROUND = 4;
    public static final int UNIHAL_VIDEO_SEGMENTATION_MODE_BLUR = 3;
    public static final int UNIHAL_VIDEO_SEGMENTATION_MODE_COLOR = 1;
    public static final int UNIHAL_VIDEO_SEGMENTATION_MODE_COLOR_PICKER = 2;
    public static final int UNIHAL_VIDEO_SEGMENTATION_MODE_OFF = 0;
    public static final String UNIHAL_VIDEO_SEGMENTATION_RGB_VALUE =
            "samsung.android.unihal.videoSegmentationRgbValue";

    void applyRequests(PersistableBundle[] persistableBundleArr) throws RemoteException;

    void clearRequests() throws RemoteException;

    void registerCallback(IBinder iBinder) throws RemoteException;

    void unregisterCallback(IBinder iBinder) throws RemoteException;

    public static class Default implements IRequestInjector {
        @Override // com.samsung.android.camera.IRequestInjector
        public void applyRequests(PersistableBundle[] requests) throws RemoteException {}

        @Override // com.samsung.android.camera.IRequestInjector
        public void clearRequests() throws RemoteException {}

        @Override // com.samsung.android.camera.IRequestInjector
        public void registerCallback(IBinder callback) throws RemoteException {}

        @Override // com.samsung.android.camera.IRequestInjector
        public void unregisterCallback(IBinder callback) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRequestInjector {
        static final int TRANSACTION_applyRequests = 1;
        static final int TRANSACTION_clearRequests = 2;
        static final int TRANSACTION_registerCallback = 3;
        static final int TRANSACTION_unregisterCallback = 4;

        public Stub() {
            attachInterface(this, IRequestInjector.DESCRIPTOR);
        }

        public static IRequestInjector asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRequestInjector.DESCRIPTOR);
            if (iin != null && (iin instanceof IRequestInjector)) {
                return (IRequestInjector) iin;
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
                    return "applyRequests";
                case 2:
                    return "clearRequests";
                case 3:
                    return "registerCallback";
                case 4:
                    return "unregisterCallback";
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
                data.enforceInterface(IRequestInjector.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRequestInjector.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    PersistableBundle[] _arg0 =
                            (PersistableBundle[]) data.createTypedArray(PersistableBundle.CREATOR);
                    data.enforceNoDataAvail();
                    applyRequests(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    clearRequests();
                    reply.writeNoException();
                    return true;
                case 3:
                    IBinder _arg02 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    registerCallback(_arg02);
                    reply.writeNoException();
                    return true;
                case 4:
                    IBinder _arg03 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    unregisterCallback(_arg03);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRequestInjector {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRequestInjector.DESCRIPTOR;
            }

            @Override // com.samsung.android.camera.IRequestInjector
            public void applyRequests(PersistableBundle[] requests) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRequestInjector.DESCRIPTOR);
                    _data.writeTypedArray(requests, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.IRequestInjector
            public void clearRequests() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRequestInjector.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.IRequestInjector
            public void registerCallback(IBinder callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRequestInjector.DESCRIPTOR);
                    _data.writeStrongBinder(callback);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.IRequestInjector
            public void unregisterCallback(IBinder callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRequestInjector.DESCRIPTOR);
                    _data.writeStrongBinder(callback);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
