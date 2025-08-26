package com.samsung.android.camera;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;

/* loaded from: classes6.dex */
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
    public static final String UNIHAL_VIDEO_SEGMENTATION_BG_IMG_NUM = "samsung.android.unihal.videoSegmentationBgImgNum";
    public static final String UNIHAL_VIDEO_SEGMENTATION_BLUR_LEVEL = "samsung.android.unihal.videoSegmentationBlurLevel";
    public static final String UNIHAL_VIDEO_SEGMENTATION_MODE = "samsung.android.unihal.videoSegmentationMode";
    public static final int UNIHAL_VIDEO_SEGMENTATION_MODE_BACKGROUND = 4;
    public static final int UNIHAL_VIDEO_SEGMENTATION_MODE_BLUR = 3;
    public static final int UNIHAL_VIDEO_SEGMENTATION_MODE_COLOR = 1;
    public static final int UNIHAL_VIDEO_SEGMENTATION_MODE_COLOR_PICKER = 2;
    public static final int UNIHAL_VIDEO_SEGMENTATION_MODE_OFF = 0;
    public static final String UNIHAL_VIDEO_SEGMENTATION_RGB_VALUE = "samsung.android.unihal.videoSegmentationRgbValue";

    public static class Default implements IRequestInjector {
        @Override // com.samsung.android.camera.IRequestInjector
        public void applyRequests(PersistableBundle[] persistableBundleArr) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.camera.IRequestInjector
        public void clearRequests() throws RemoteException {
        }

        @Override // com.samsung.android.camera.IRequestInjector
        public void registerCallback(IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.camera.IRequestInjector
        public void unregisterCallback(IBinder iBinder) throws RemoteException {
        }
    }

    void applyRequests(PersistableBundle[] persistableBundleArr) throws RemoteException;

    void clearRequests() throws RemoteException;

    void registerCallback(IBinder iBinder) throws RemoteException;

    void unregisterCallback(IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements IRequestInjector {
        static final int TRANSACTION_applyRequests = 1;
        static final int TRANSACTION_clearRequests = 2;
        static final int TRANSACTION_registerCallback = 3;
        static final int TRANSACTION_unregisterCallback = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IRequestInjector.DESCRIPTOR);
        }

        public static IRequestInjector asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRequestInjector.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRequestInjector)) {
                return (IRequestInjector) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "applyRequests";
            }
            if (i == 2) {
                return "clearRequests";
            }
            if (i == 3) {
                return "registerCallback";
            }
            if (i != 4) {
                return null;
            }
            return "unregisterCallback";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRequestInjector.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRequestInjector.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                PersistableBundle[] persistableBundleArr = (PersistableBundle[]) parcel.createTypedArray(PersistableBundle.CREATOR);
                parcel.enforceNoDataAvail();
                applyRequests(persistableBundleArr);
                parcel2.writeNoException();
            } else if (i == 2) {
                clearRequests();
                parcel2.writeNoException();
            } else if (i == 3) {
                IBinder strongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                registerCallback(strongBinder);
                parcel2.writeNoException();
            } else if (i == 4) {
                IBinder strongBinder2 = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                unregisterCallback(strongBinder2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRequestInjector {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRequestInjector.DESCRIPTOR;
            }

            @Override // com.samsung.android.camera.IRequestInjector
            public void applyRequests(PersistableBundle[] persistableBundleArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRequestInjector.DESCRIPTOR);
                    parcelObtain.writeTypedArray(persistableBundleArr, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.IRequestInjector
            public void clearRequests() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRequestInjector.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.IRequestInjector
            public void registerCallback(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRequestInjector.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.IRequestInjector
            public void unregisterCallback(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRequestInjector.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
