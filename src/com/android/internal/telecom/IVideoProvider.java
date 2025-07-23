package com.android.internal.telecom;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.VideoProfile;
import android.view.Surface;

/* loaded from: classes4.dex */
public interface IVideoProvider extends IInterface {

    public static class Default implements IVideoProvider {
        @Override // com.android.internal.telecom.IVideoProvider
        public void addVideoCallback(IBinder iBinder) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void removeVideoCallback(IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void requestCallDataUsage() throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void requestCameraCapabilities() throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void sendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void sendSessionModifyResponse(VideoProfile videoProfile) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void setCamera(String str, String str2, int i) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void setDeviceOrientation(int i) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void setDisplaySurface(Surface surface) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void setPauseImage(Uri uri) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void setPreviewSurface(Surface surface) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void setZoom(float f) throws RemoteException {
        }
    }

    void addVideoCallback(IBinder iBinder) throws RemoteException;

    void removeVideoCallback(IBinder iBinder) throws RemoteException;

    void requestCallDataUsage() throws RemoteException;

    void requestCameraCapabilities() throws RemoteException;

    void sendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2) throws RemoteException;

    void sendSessionModifyResponse(VideoProfile videoProfile) throws RemoteException;

    void setCamera(String str, String str2, int i) throws RemoteException;

    void setDeviceOrientation(int i) throws RemoteException;

    void setDisplaySurface(Surface surface) throws RemoteException;

    void setPauseImage(Uri uri) throws RemoteException;

    void setPreviewSurface(Surface surface) throws RemoteException;

    void setZoom(float f) throws RemoteException;

    public static abstract class Stub extends Binder implements IVideoProvider {
        public static final String DESCRIPTOR = "com.android.internal.telecom.IVideoProvider";
        static final int TRANSACTION_addVideoCallback = 1;
        static final int TRANSACTION_removeVideoCallback = 2;
        static final int TRANSACTION_requestCallDataUsage = 11;
        static final int TRANSACTION_requestCameraCapabilities = 10;
        static final int TRANSACTION_sendSessionModifyRequest = 8;
        static final int TRANSACTION_sendSessionModifyResponse = 9;
        static final int TRANSACTION_setCamera = 3;
        static final int TRANSACTION_setDeviceOrientation = 6;
        static final int TRANSACTION_setDisplaySurface = 5;
        static final int TRANSACTION_setPauseImage = 12;
        static final int TRANSACTION_setPreviewSurface = 4;
        static final int TRANSACTION_setZoom = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVideoProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVideoProvider)) {
                return (IVideoProvider) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addVideoCallback";
                case 2:
                    return "removeVideoCallback";
                case 3:
                    return "setCamera";
                case 4:
                    return "setPreviewSurface";
                case 5:
                    return "setDisplaySurface";
                case 6:
                    return "setDeviceOrientation";
                case 7:
                    return "setZoom";
                case 8:
                    return "sendSessionModifyRequest";
                case 9:
                    return "sendSessionModifyResponse";
                case 10:
                    return "requestCameraCapabilities";
                case 11:
                    return "requestCallDataUsage";
                case 12:
                    return "setPauseImage";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    addVideoCallback(readStrongBinder);
                    return true;
                case 2:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    removeVideoCallback(readStrongBinder2);
                    return true;
                case 3:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCamera(readString, readString2, readInt);
                    return true;
                case 4:
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreviewSurface(surface);
                    return true;
                case 5:
                    Surface surface2 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDisplaySurface(surface2);
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDeviceOrientation(readInt2);
                    return true;
                case 7:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setZoom(readFloat);
                    return true;
                case 8:
                    VideoProfile videoProfile = (VideoProfile) parcel.readTypedObject(VideoProfile.CREATOR);
                    VideoProfile videoProfile2 = (VideoProfile) parcel.readTypedObject(VideoProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSessionModifyRequest(videoProfile, videoProfile2);
                    return true;
                case 9:
                    VideoProfile videoProfile3 = (VideoProfile) parcel.readTypedObject(VideoProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSessionModifyResponse(videoProfile3);
                    return true;
                case 10:
                    requestCameraCapabilities();
                    return true;
                case 11:
                    requestCallDataUsage();
                    return true;
                case 12:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPauseImage(uri);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVideoProvider {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void addVideoCallback(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void removeVideoCallback(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setCamera(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setPreviewSurface(Surface surface) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setDisplaySurface(Surface surface) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setDeviceOrientation(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setZoom(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void sendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(videoProfile, 0);
                    obtain.writeTypedObject(videoProfile2, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void sendSessionModifyResponse(VideoProfile videoProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(videoProfile, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void requestCameraCapabilities() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void requestCallDataUsage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setPauseImage(Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
