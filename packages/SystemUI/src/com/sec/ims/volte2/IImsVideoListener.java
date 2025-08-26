package com.sec.ims.volte2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IImsVideoListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IImsVideoListener";

    void onCallDownGraded(int i) throws RemoteException;

    void onCameraEvent(int i, boolean z) throws RemoteException;

    void onCameraFirstFrameReady(int i) throws RemoteException;

    void onCameraStopEvent(int i, boolean z) throws RemoteException;

    void onCameraSwitchFailure(int i, int i2) throws RemoteException;

    void onCameraSwitchSuccess(int i, int i2) throws RemoteException;

    void onCaptureFailure(int i, boolean z) throws RemoteException;

    void onCaptureSuccess(int i, boolean z, String str) throws RemoteException;

    void onNoFarFrame(int i) throws RemoteException;

    void onRecordEvent(int i, boolean z, boolean z2) throws RemoteException;

    void onVideoAttemped(int i) throws RemoteException;

    void onVideoAvailable(int i) throws RemoteException;

    void onVideoHeld(int i) throws RemoteException;

    void onVideoResumed(int i) throws RemoteException;

    public abstract class Stub extends Binder implements IImsVideoListener {
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

        class Proxy implements IImsVideoListener {
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
            public void onCallDownGraded(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCameraEvent(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCameraFirstFrameReady(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCameraStopEvent(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCameraSwitchFailure(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCameraSwitchSuccess(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCaptureFailure(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onCaptureSuccess(int i, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onNoFarFrame(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onRecordEvent(int i, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onVideoAttemped(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onVideoAvailable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onVideoHeld(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsVideoListener
            public void onVideoResumed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsVideoListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsVideoListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IImsVideoListener)) ? new Proxy(iBinder) : (IImsVideoListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsVideoListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsVideoListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoAvailable(i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCameraEvent(i4, z);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraFirstFrameReady(i5);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onCaptureSuccess(i6, z2, string);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCaptureFailure(i7, z3);
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraSwitchSuccess(i8, i9);
                    return true;
                case 7:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraSwitchFailure(i10, i11);
                    return true;
                case 8:
                    int i12 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCameraStopEvent(i12, z4);
                    return true;
                case 9:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoHeld(i13);
                    return true;
                case 10:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoResumed(i14);
                    return true;
                case 11:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallDownGraded(i15);
                    return true;
                case 12:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNoFarFrame(i16);
                    return true;
                case 13:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoAttemped(i17);
                    return true;
                case 14:
                    int i18 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onRecordEvent(i18, z5, z6);
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

    public class Default implements IImsVideoListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCallDownGraded(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCameraFirstFrameReady(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onNoFarFrame(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onVideoAttemped(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onVideoAvailable(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onVideoHeld(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onVideoResumed(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCameraEvent(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCameraStopEvent(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCameraSwitchFailure(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCameraSwitchSuccess(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCaptureFailure(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onCaptureSuccess(int i, boolean z, String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsVideoListener
        public void onRecordEvent(int i, boolean z, boolean z2) throws RemoteException {
        }
    }
}
