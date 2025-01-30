package com.sec.ims.volte2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IImsVideoListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IImsVideoListener";

    void onCallDownGraded(int i);

    void onCameraEvent(int i, boolean z);

    void onCameraFirstFrameReady(int i);

    void onCameraStopEvent(int i, boolean z);

    void onCameraSwitchFailure(int i, int i2);

    void onCameraSwitchSuccess(int i, int i2);

    void onCaptureFailure(int i, boolean z);

    void onCaptureSuccess(int i, boolean z, String str);

    void onNoFarFrame(int i);

    void onRecordEvent(int i, boolean z, boolean z2);

    void onVideoAttemped(int i);

    void onVideoAvailable(int i);

    void onVideoHeld(int i);

    void onVideoResumed(int i);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IImsVideoListener {
        static final int TRANSACTION_onCallDownGraded = 11;
        static final int TRANSACTION_onCameraEvent = 2;
        static final int TRANSACTION_onCameraFirstFrameReady = 3;
        static final int TRANSACTION_onCameraStopEvent = 8;
        static final int TRANSACTION_onCameraSwitchFailure = 7;
        static final int TRANSACTION_onCameraSwitchSuccess = 6;
        static final int TRANSACTION_onCaptureFailure = 5;
        static final int TRANSACTION_onCaptureSuccess = 4;
        static final int TRANSACTION_onNoFarFrame = 12;
        static final int TRANSACTION_onRecordEvent = 14;
        static final int TRANSACTION_onVideoAttemped = 13;
        static final int TRANSACTION_onVideoAvailable = 1;
        static final int TRANSACTION_onVideoHeld = 9;
        static final int TRANSACTION_onVideoResumed = 10;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IImsVideoListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsVideoListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCallDownGraded(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCameraEvent(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCameraFirstFrameReady(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCameraStopEvent(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCameraSwitchFailure(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCameraSwitchSuccess(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCaptureFailure(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCaptureSuccess(int i, boolean z, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onNoFarFrame(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onRecordEvent(int i, boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onVideoAttemped(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onVideoAvailable(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onVideoHeld(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onVideoResumed(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsVideoListener.DESCRIPTOR);
        }

        public static IImsVideoListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsVideoListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IImsVideoListener)) ? new Proxy(iBinder) : (IImsVideoListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsVideoListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsVideoListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoAvailable(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCameraEvent(readInt2, readBoolean);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraFirstFrameReady(readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onCaptureSuccess(readInt4, readBoolean2, readString);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCaptureFailure(readInt5, readBoolean3);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraSwitchSuccess(readInt6, readInt7);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraSwitchFailure(readInt8, readInt9);
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCameraStopEvent(readInt10, readBoolean4);
                    return true;
                case 9:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoHeld(readInt11);
                    return true;
                case 10:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoResumed(readInt12);
                    return true;
                case 11:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallDownGraded(readInt13);
                    return true;
                case 12:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNoFarFrame(readInt14);
                    return true;
                case 13:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoAttemped(readInt15);
                    return true;
                case 14:
                    int readInt16 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onRecordEvent(readInt16, readBoolean5, readBoolean6);
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
    public static class Default implements IImsVideoListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCallDownGraded(int i) {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCameraFirstFrameReady(int i) {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onNoFarFrame(int i) {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onVideoAttemped(int i) {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onVideoAvailable(int i) {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onVideoHeld(int i) {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onVideoResumed(int i) {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCameraEvent(int i, boolean z) {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCameraStopEvent(int i, boolean z) {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCameraSwitchFailure(int i, int i2) {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCameraSwitchSuccess(int i, int i2) {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCaptureFailure(int i, boolean z) {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCaptureSuccess(int i, boolean z, String str) {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onRecordEvent(int i, boolean z, boolean z2) {
        }
    }
}
