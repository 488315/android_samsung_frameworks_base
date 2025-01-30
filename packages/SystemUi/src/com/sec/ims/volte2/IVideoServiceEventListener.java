package com.sec.ims.volte2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.sec.ims.volte2.IImsCallSession;
import com.sec.ims.volte2.data.CallProfile;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IVideoServiceEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IVideoServiceEventListener";

    void changeCameraCapabilities(int i, int i2, int i3);

    IImsCallSession getSession();

    void onCameraState(int i, int i2);

    void onChangeCallDataUsage(int i, long j);

    void onChangePeerDimension(int i, int i2, int i3);

    void onEmojiState(int i, int i2);

    void onRecordState(int i, int i2);

    void onVideoOrientChanged(int i);

    void onVideoQualityChanged(int i, int i2);

    void onVideoState(int i, int i2);

    void receiveSessionModifyRequest(int i, CallProfile callProfile);

    void receiveSessionModifyResponse(int i, int i2, CallProfile callProfile, CallProfile callProfile2);

    void setVideoPause(int i, boolean z);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IVideoServiceEventListener {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IVideoServiceEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void changeCameraCapabilities(int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IVideoServiceEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public IImsCallSession getSession() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsCallSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onCameraState(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onChangeCallDataUsage(int i, long j) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onChangePeerDimension(int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onEmojiState(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onRecordState(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onVideoOrientChanged(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onVideoQualityChanged(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void onVideoState(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void receiveSessionModifyRequest(int i, CallProfile callProfile) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(callProfile, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void receiveSessionModifyResponse(int i, int i2, CallProfile callProfile, CallProfile callProfile2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(callProfile, 0);
                    obtain.writeTypedObject(callProfile2, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVideoServiceEventListener
            public void setVideoPause(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVideoServiceEventListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IVideoServiceEventListener)) ? new Proxy(iBinder) : (IVideoServiceEventListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraState(readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoState(readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoQualityChanged(readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    CallProfile callProfile = (CallProfile) parcel.readTypedObject(CallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    receiveSessionModifyRequest(readInt7, callProfile);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    Parcelable.Creator<CallProfile> creator = CallProfile.CREATOR;
                    CallProfile callProfile2 = (CallProfile) parcel.readTypedObject(creator);
                    CallProfile callProfile3 = (CallProfile) parcel.readTypedObject(creator);
                    parcel.enforceNoDataAvail();
                    receiveSessionModifyResponse(readInt8, readInt9, callProfile2, callProfile3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoOrientChanged(readInt10);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onChangePeerDimension(readInt11, readInt12, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt14 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVideoPause(readInt14, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeCameraCapabilities(readInt15, readInt16, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRecordState(readInt18, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEmojiState(readInt20, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt22 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onChangeCallDataUsage(readInt22, readLong);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IVideoServiceEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public IImsCallSession getSession() {
            return null;
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onVideoOrientChanged(int i) {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onCameraState(int i, int i2) {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onChangeCallDataUsage(int i, long j) {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onEmojiState(int i, int i2) {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onRecordState(int i, int i2) {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onVideoQualityChanged(int i, int i2) {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onVideoState(int i, int i2) {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void receiveSessionModifyRequest(int i, CallProfile callProfile) {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void setVideoPause(int i, boolean z) {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void changeCameraCapabilities(int i, int i2, int i3) {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void onChangePeerDimension(int i, int i2, int i3) {
        }

        @Override // com.sec.ims.volte2.IVideoServiceEventListener
        public void receiveSessionModifyResponse(int i, int i2, CallProfile callProfile, CallProfile callProfile2) {
        }
    }
}
