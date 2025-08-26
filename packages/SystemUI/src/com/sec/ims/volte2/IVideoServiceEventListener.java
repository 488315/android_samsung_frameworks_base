package com.sec.ims.volte2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.sec.ims.volte2.IImsCallSession;
import com.sec.ims.volte2.data.CallProfile;

/* loaded from: classes4.dex */
public interface IVideoServiceEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IVideoServiceEventListener";

    void changeCameraCapabilities(int i, int i2, int i3) throws RemoteException;

    IImsCallSession getSession() throws RemoteException;

    void onCameraState(int i, int i2) throws RemoteException;

    void onChangeCallDataUsage(int i, long j) throws RemoteException;

    void onChangePeerDimension(int i, int i2, int i3) throws RemoteException;

    void onEmojiState(int i, int i2) throws RemoteException;

    void onRecordState(int i, int i2) throws RemoteException;

    void onVideoOrientChanged(int i) throws RemoteException;

    void onVideoQualityChanged(int i, int i2) throws RemoteException;

    void onVideoState(int i, int i2) throws RemoteException;

    void receiveSessionModifyRequest(int i, CallProfile callProfile) throws RemoteException;

    void receiveSessionModifyResponse(int i, int i2, CallProfile callProfile, CallProfile callProfile2) throws RemoteException;

    void setVideoPause(int i, boolean z) throws RemoteException;

    public abstract class Stub extends Binder implements IVideoServiceEventListener {
        static final int TRANSACTION_changeCameraCapabilities = 10;
        static final int TRANSACTION_getSession = 1;
        static final int TRANSACTION_onCameraState = 2;
        static final int TRANSACTION_onChangeCallDataUsage = 13;
        static final int TRANSACTION_onChangePeerDimension = 8;
        static final int TRANSACTION_onEmojiState = 12;
        static final int TRANSACTION_onRecordState = 11;
        static final int TRANSACTION_onVideoOrientChanged = 7;
        static final int TRANSACTION_onVideoQualityChanged = 4;
        static final int TRANSACTION_onVideoState = 3;
        static final int TRANSACTION_receiveSessionModifyRequest = 5;
        static final int TRANSACTION_receiveSessionModifyResponse = 6;
        static final int TRANSACTION_setVideoPause = 9;

        class Proxy implements IVideoServiceEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void changeCameraCapabilities(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IVideoServiceEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public IImsCallSession getSession() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsCallSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onCameraState(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onChangeCallDataUsage(int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onChangePeerDimension(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onEmojiState(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onRecordState(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onVideoOrientChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onVideoQualityChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onVideoState(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void receiveSessionModifyRequest(int i, CallProfile callProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(callProfile, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void receiveSessionModifyResponse(int i, int i2, CallProfile callProfile, CallProfile callProfile2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(callProfile, 0);
                    parcelObtain.writeTypedObject(callProfile2, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void setVideoPause(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IVideoServiceEventListener.DESCRIPTOR);
        }

        public static IVideoServiceEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVideoServiceEventListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IVideoServiceEventListener)) ? new Proxy(iBinder) : (IVideoServiceEventListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVideoServiceEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVideoServiceEventListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IImsCallSession session = getSession();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(session);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraState(i3, i4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoState(i5, i6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoQualityChanged(i7, i8);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i9 = parcel.readInt();
                    CallProfile callProfile = (CallProfile) parcel.readTypedObject(CallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    receiveSessionModifyRequest(i9, callProfile);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    Parcelable.Creator<CallProfile> creator = CallProfile.CREATOR;
                    CallProfile callProfile2 = (CallProfile) parcel.readTypedObject(creator);
                    CallProfile callProfile3 = (CallProfile) parcel.readTypedObject(creator);
                    parcel.enforceNoDataAvail();
                    receiveSessionModifyResponse(i10, i11, callProfile2, callProfile3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoOrientChanged(i12);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onChangePeerDimension(i13, i14, i15);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i16 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVideoPause(i16, z);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeCameraCapabilities(i17, i18, i19);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRecordState(i20, i21);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEmojiState(i22, i23);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i24 = parcel.readInt();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onChangeCallDataUsage(i24, j);
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

    public class Default implements IVideoServiceEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public IImsCallSession getSession() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onVideoOrientChanged(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onCameraState(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onChangeCallDataUsage(int i, long j) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onEmojiState(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onRecordState(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onVideoQualityChanged(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onVideoState(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void receiveSessionModifyRequest(int i, CallProfile callProfile) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void setVideoPause(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void changeCameraCapabilities(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onChangePeerDimension(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void receiveSessionModifyResponse(int i, int i2, CallProfile callProfile, CallProfile callProfile2) throws RemoteException {
        }
    }
}
