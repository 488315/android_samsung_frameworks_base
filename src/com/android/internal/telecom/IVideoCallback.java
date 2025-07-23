package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.VideoProfile;

/* loaded from: classes4.dex */
public interface IVideoCallback extends IInterface {

    public static class Default implements IVideoCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changeCallDataUsage(long j) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changePeerDimensions(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void changeVideoQuality(int i) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void handleCallSessionEvent(int i) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void receiveSessionModifyRequest(VideoProfile videoProfile) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoCallback
        public void receiveSessionModifyResponse(int i, VideoProfile videoProfile, VideoProfile videoProfile2) throws RemoteException {
        }
    }

    void changeCallDataUsage(long j) throws RemoteException;

    void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) throws RemoteException;

    void changePeerDimensions(int i, int i2) throws RemoteException;

    void changeVideoQuality(int i) throws RemoteException;

    void handleCallSessionEvent(int i) throws RemoteException;

    void receiveSessionModifyRequest(VideoProfile videoProfile) throws RemoteException;

    void receiveSessionModifyResponse(int i, VideoProfile videoProfile, VideoProfile videoProfile2) throws RemoteException;

    public static abstract class Stub extends Binder implements IVideoCallback {
        public static final String DESCRIPTOR = "com.android.internal.telecom.IVideoCallback";
        static final int TRANSACTION_changeCallDataUsage = 5;
        static final int TRANSACTION_changeCameraCapabilities = 6;
        static final int TRANSACTION_changePeerDimensions = 4;
        static final int TRANSACTION_changeVideoQuality = 7;
        static final int TRANSACTION_handleCallSessionEvent = 3;
        static final int TRANSACTION_receiveSessionModifyRequest = 1;
        static final int TRANSACTION_receiveSessionModifyResponse = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVideoCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVideoCallback)) {
                return (IVideoCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "receiveSessionModifyRequest";
                case 2:
                    return "receiveSessionModifyResponse";
                case 3:
                    return "handleCallSessionEvent";
                case 4:
                    return "changePeerDimensions";
                case 5:
                    return "changeCallDataUsage";
                case 6:
                    return "changeCameraCapabilities";
                case 7:
                    return "changeVideoQuality";
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
                    VideoProfile videoProfile = (VideoProfile) parcel.readTypedObject(VideoProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    receiveSessionModifyRequest(videoProfile);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    VideoProfile videoProfile2 = (VideoProfile) parcel.readTypedObject(VideoProfile.CREATOR);
                    VideoProfile videoProfile3 = (VideoProfile) parcel.readTypedObject(VideoProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    receiveSessionModifyResponse(readInt, videoProfile2, videoProfile3);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    handleCallSessionEvent(readInt2);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changePeerDimensions(readInt3, readInt4);
                    return true;
                case 5:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    changeCallDataUsage(readLong);
                    return true;
                case 6:
                    VideoProfile.CameraCapabilities cameraCapabilities = (VideoProfile.CameraCapabilities) parcel.readTypedObject(VideoProfile.CameraCapabilities.CREATOR);
                    parcel.enforceNoDataAvail();
                    changeCameraCapabilities(cameraCapabilities);
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeVideoQuality(readInt5);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVideoCallback {
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

            @Override // com.android.internal.telecom.IVideoCallback
            public void receiveSessionModifyRequest(VideoProfile videoProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(videoProfile, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoCallback
            public void receiveSessionModifyResponse(int i, VideoProfile videoProfile, VideoProfile videoProfile2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(videoProfile, 0);
                    obtain.writeTypedObject(videoProfile2, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoCallback
            public void handleCallSessionEvent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoCallback
            public void changePeerDimensions(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoCallback
            public void changeCallDataUsage(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoCallback
            public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(cameraCapabilities, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoCallback
            public void changeVideoQuality(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
