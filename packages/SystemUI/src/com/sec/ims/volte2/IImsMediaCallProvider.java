package com.sec.ims.volte2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;
import com.sec.ims.volte2.IVideoServiceEventListener;

/* loaded from: classes4.dex */
public interface IImsMediaCallProvider extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IImsMediaCallProvider";

    void changeCameraCapabilities(int i, int i2, int i3) throws RemoteException;

    void deinitSurface(boolean z) throws RemoteException;

    void getCameraInfo(int i) throws RemoteException;

    int getDefaultCameraId() throws RemoteException;

    void getMaxZoom() throws RemoteException;

    void getZoom() throws RemoteException;

    void registerForVideoServiceEvent(IVideoServiceEventListener iVideoServiceEventListener) throws RemoteException;

    void requestCallDataUsage() throws RemoteException;

    void resetCameraId() throws RemoteException;

    void sendGeneralEvent(int i, int i2, int i3, String str) throws RemoteException;

    void sendLiveVideo(int i) throws RemoteException;

    void sendStillImage(int i, String str, int i2, String str2, int i3) throws RemoteException;

    void setCamera(String str) throws RemoteException;

    void setCameraEffect(int i) throws RemoteException;

    void setDeviceOrientation(int i) throws RemoteException;

    void setDisplaySurface(int i, Surface surface) throws RemoteException;

    void setPreviewSurface(int i, Surface surface) throws RemoteException;

    void setZoom(float f) throws RemoteException;

    void startCamera(Surface surface) throws RemoteException;

    void startEmoji(String str) throws RemoteException;

    void startRecord(String str) throws RemoteException;

    void startRender(boolean z) throws RemoteException;

    void startVideoRenderer(Surface surface) throws RemoteException;

    void stopCamera() throws RemoteException;

    void stopEmoji(int i) throws RemoteException;

    void stopRecord() throws RemoteException;

    void stopVideoRenderer() throws RemoteException;

    void swipeVideoSurface() throws RemoteException;

    void switchCamera() throws RemoteException;

    void unregisterForVideoServiceEvent(IVideoServiceEventListener iVideoServiceEventListener) throws RemoteException;

    public class Default implements IImsMediaCallProvider {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public int getDefaultCameraId() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void getMaxZoom() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void getZoom() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void requestCallDataUsage() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void resetCameraId() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void stopCamera() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void stopRecord() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void stopVideoRenderer() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void swipeVideoSurface() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void switchCamera() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void deinitSurface(boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void getCameraInfo(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void registerForVideoServiceEvent(IVideoServiceEventListener iVideoServiceEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void sendLiveVideo(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setCamera(String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setCameraEffect(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setDeviceOrientation(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setZoom(float f) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startCamera(Surface surface) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startEmoji(String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startRecord(String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startRender(boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void startVideoRenderer(Surface surface) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void stopEmoji(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void unregisterForVideoServiceEvent(IVideoServiceEventListener iVideoServiceEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setDisplaySurface(int i, Surface surface) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void setPreviewSurface(int i, Surface surface) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void changeCameraCapabilities(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void sendGeneralEvent(int i, int i2, int i3, String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsMediaCallProvider
        public void sendStillImage(int i, String str, int i2, String str2, int i3) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IImsMediaCallProvider {
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

        class Proxy implements IImsMediaCallProvider {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void changeCameraCapabilities(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void deinitSurface(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void getCameraInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public int getDefaultCameraId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IImsMediaCallProvider.DESCRIPTOR;
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void getMaxZoom() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void getZoom() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void registerForVideoServiceEvent(IVideoServiceEventListener iVideoServiceEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVideoServiceEventListener);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void requestCallDataUsage() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void resetCameraId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void sendGeneralEvent(int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void sendLiveVideo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void sendStillImage(int i, String str, int i2, String str2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setCamera(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setCameraEffect(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setDeviceOrientation(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setDisplaySurface(int i, Surface surface) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setPreviewSurface(int i, Surface surface) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void setZoom(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startCamera(Surface surface) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startEmoji(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startRecord(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startRender(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void startVideoRenderer(Surface surface) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void stopCamera() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void stopEmoji(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void stopRecord() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void stopVideoRenderer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void swipeVideoSurface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void switchCamera() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsMediaCallProvider
            public void unregisterForVideoServiceEvent(IVideoServiceEventListener iVideoServiceEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMediaCallProvider.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVideoServiceEventListener);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsMediaCallProvider.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IImsMediaCallProvider)) ? new Proxy(iBinder) : (IImsMediaCallProvider) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsMediaCallProvider.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsMediaCallProvider.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCamera(string);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreviewSurface(i3, surface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i4 = parcel.readInt();
                    Surface surface2 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDisplaySurface(i4, surface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDeviceOrientation(i5);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setZoom(f);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    requestCallDataUsage();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeCameraCapabilities(i6, i7, i8);
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
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCameraInfo(i9);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startRender(z);
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
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    deinitSurface(z2);
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
                    int i10 = parcel.readInt();
                    String string2 = parcel.readString();
                    int i11 = parcel.readInt();
                    String string3 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendStillImage(i10, string2, i11, string3, i12);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendLiveVideo(i13);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IVideoServiceEventListener iVideoServiceEventListenerAsInterface = IVideoServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForVideoServiceEvent(iVideoServiceEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IVideoServiceEventListener iVideoServiceEventListenerAsInterface2 = IVideoServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForVideoServiceEvent(iVideoServiceEventListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCameraEffect(i14);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startRecord(string4);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    stopRecord();
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startEmoji(string5);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopEmoji(i15);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendGeneralEvent(i16, i17, i18, string6);
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
