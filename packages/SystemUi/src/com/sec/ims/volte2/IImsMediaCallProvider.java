package com.sec.ims.volte2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.view.Surface;
import com.sec.ims.volte2.IVideoServiceEventListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IImsMediaCallProvider extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IImsMediaCallProvider";

    void changeCameraCapabilities(int i, int i2, int i3);

    void deinitSurface(boolean z);

    void getCameraInfo(int i);

    int getDefaultCameraId();

    void getMaxZoom();

    void getZoom();

    void registerForVideoServiceEvent(IVideoServiceEventListener iVideoServiceEventListener);

    void requestCallDataUsage();

    void resetCameraId();

    void sendGeneralEvent(int i, int i2, int i3, String str);

    void sendLiveVideo(int i);

    void sendStillImage(int i, String str, int i2, String str2, int i3);

    void setCamera(String str);

    void setCameraEffect(int i);

    void setDeviceOrientation(int i);

    void setDisplaySurface(int i, Surface surface);

    void setPreviewSurface(int i, Surface surface);

    void setZoom(float f);

    void startCamera(Surface surface);

    void startEmoji(String str);

    void startRecord(String str);

    void startRender(boolean z);

    void startVideoRenderer(Surface surface);

    void stopCamera();

    void stopEmoji(int i);

    void stopRecord();

    void stopVideoRenderer();

    void swipeVideoSurface();

    void switchCamera();

    void unregisterForVideoServiceEvent(IVideoServiceEventListener iVideoServiceEventListener);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IImsMediaCallProvider {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public int getDefaultCameraId() {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void getMaxZoom() {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void getZoom() {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void requestCallDataUsage() {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void resetCameraId() {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void stopCamera() {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void stopRecord() {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void stopVideoRenderer() {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void swipeVideoSurface() {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void switchCamera() {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void deinitSurface(boolean z) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void getCameraInfo(int i) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void registerForVideoServiceEvent(IVideoServiceEventListener iVideoServiceEventListener) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void sendLiveVideo(int i) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setCamera(String str) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setCameraEffect(int i) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setDeviceOrientation(int i) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setZoom(float f) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startCamera(Surface surface) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startEmoji(String str) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startRecord(String str) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startRender(boolean z) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startVideoRenderer(Surface surface) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void stopEmoji(int i) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void unregisterForVideoServiceEvent(IVideoServiceEventListener iVideoServiceEventListener) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setDisplaySurface(int i, Surface surface) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setPreviewSurface(int i, Surface surface) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void changeCameraCapabilities(int i, int i2, int i3) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void sendGeneralEvent(int i, int i2, int i3, String str) {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void sendStillImage(int i, String str, int i2, String str2, int i3) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IImsMediaCallProvider {
        static final int TRANSACTION_changeCameraCapabilities = 7;
        static final int TRANSACTION_deinitSurface = 17;
        static final int TRANSACTION_getCameraInfo = 12;
        static final int TRANSACTION_getDefaultCameraId = 20;
        static final int TRANSACTION_getMaxZoom = 18;
        static final int TRANSACTION_getZoom = 19;
        static final int TRANSACTION_registerForVideoServiceEvent = 23;
        static final int TRANSACTION_requestCallDataUsage = 6;
        static final int TRANSACTION_resetCameraId = 11;
        static final int TRANSACTION_sendGeneralEvent = 30;
        static final int TRANSACTION_sendLiveVideo = 22;
        static final int TRANSACTION_sendStillImage = 21;
        static final int TRANSACTION_setCamera = 1;
        static final int TRANSACTION_setCameraEffect = 25;
        static final int TRANSACTION_setDeviceOrientation = 4;
        static final int TRANSACTION_setDisplaySurface = 3;
        static final int TRANSACTION_setPreviewSurface = 2;
        static final int TRANSACTION_setZoom = 5;
        static final int TRANSACTION_startCamera = 8;
        static final int TRANSACTION_startEmoji = 28;
        static final int TRANSACTION_startRecord = 26;
        static final int TRANSACTION_startRender = 13;
        static final int TRANSACTION_startVideoRenderer = 14;
        static final int TRANSACTION_stopCamera = 9;
        static final int TRANSACTION_stopEmoji = 29;
        static final int TRANSACTION_stopRecord = 27;
        static final int TRANSACTION_stopVideoRenderer = 15;
        static final int TRANSACTION_swipeVideoSurface = 16;
        static final int TRANSACTION_switchCamera = 10;
        static final int TRANSACTION_unregisterForVideoServiceEvent = 24;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IImsMediaCallProvider {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void changeCameraCapabilities(int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void deinitSurface(boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void getCameraInfo(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public int getDefaultCameraId() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IImsMediaCallProvider.DESCRIPTOR;
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void getMaxZoom() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void getZoom() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void registerForVideoServiceEvent(IVideoServiceEventListener iVideoServiceEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iVideoServiceEventListener);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void requestCallDataUsage() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void resetCameraId() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void sendGeneralEvent(int i, int i2, int i3, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void sendLiveVideo(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void sendStillImage(int i, String str, int i2, String str2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setCamera(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setCameraEffect(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setDeviceOrientation(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setDisplaySurface(int i, Surface surface) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setPreviewSurface(int i, Surface surface) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setZoom(float f) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startCamera(Surface surface) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startEmoji(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startRecord(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startRender(boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startVideoRenderer(Surface surface) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void stopCamera() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void stopEmoji(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void stopRecord() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void stopVideoRenderer() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void swipeVideoSurface() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void switchCamera() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void unregisterForVideoServiceEvent(IVideoServiceEventListener iVideoServiceEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iVideoServiceEventListener);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsMediaCallProvider.DESCRIPTOR);
        }

        public static IImsMediaCallProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsMediaCallProvider.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IImsMediaCallProvider)) ? new Proxy(iBinder) : (IImsMediaCallProvider) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsMediaCallProvider.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsMediaCallProvider.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCamera(readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreviewSurface(readInt, surface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    Surface surface2 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDisplaySurface(readInt2, surface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDeviceOrientation(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setZoom(readFloat);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    requestCallDataUsage();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeCameraCapabilities(readInt4, readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    Surface surface3 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    startCamera(surface3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    stopCamera();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    switchCamera();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    resetCameraId();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCameraInfo(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startRender(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    Surface surface4 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    startVideoRenderer(surface4);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    stopVideoRenderer();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    swipeVideoSurface();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    deinitSurface(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    getMaxZoom();
                    parcel2.writeNoException();
                    return true;
                case 19:
                    getZoom();
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int defaultCameraId = getDefaultCameraId();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultCameraId);
                    return true;
                case 21:
                    int readInt8 = parcel.readInt();
                    String readString2 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    String readString3 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendStillImage(readInt8, readString2, readInt9, readString3, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendLiveVideo(readInt11);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IVideoServiceEventListener asInterface = IVideoServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForVideoServiceEvent(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IVideoServiceEventListener asInterface2 = IVideoServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForVideoServiceEvent(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCameraEffect(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startRecord(readString4);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    stopRecord();
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startEmoji(readString5);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopEmoji(readInt13);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendGeneralEvent(readInt14, readInt15, readInt16, readString6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
