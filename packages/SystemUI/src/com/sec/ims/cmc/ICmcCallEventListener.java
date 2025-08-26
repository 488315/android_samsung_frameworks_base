package com.sec.ims.cmc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ICmcCallEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.cmc.ICmcCallEventListener";

    void onCallEnded(CmcCallEventInfo cmcCallEventInfo) throws RemoteException;

    void onCallError(CmcCallEventInfo cmcCallEventInfo) throws RemoteException;

    void onCallEstablished(CmcCallEventInfo cmcCallEventInfo) throws RemoteException;

    void onCallHeld(CmcCallEventInfo cmcCallEventInfo, int i) throws RemoteException;

    void onCallRecording(CmcRecordEventInfo cmcRecordEventInfo) throws RemoteException;

    void onCallResumed(CmcCallEventInfo cmcCallEventInfo, int i) throws RemoteException;

    void onCallRingingBack(CmcCallEventInfo cmcCallEventInfo) throws RemoteException;

    void onCalling(CmcCallEventInfo cmcCallEventInfo) throws RemoteException;

    void onEarlyMediaStarted(CmcCallEventInfo cmcCallEventInfo) throws RemoteException;

    void onIncomingCall(CmcCallEventInfo cmcCallEventInfo) throws RemoteException;

    public abstract class Stub extends Binder implements ICmcCallEventListener {
        static final int TRANSACTION_onCallEnded = 6;
        static final int TRANSACTION_onCallError = 7;
        static final int TRANSACTION_onCallEstablished = 5;
        static final int TRANSACTION_onCallHeld = 8;
        static final int TRANSACTION_onCallRecording = 10;
        static final int TRANSACTION_onCallResumed = 9;
        static final int TRANSACTION_onCallRingingBack = 3;
        static final int TRANSACTION_onCalling = 2;
        static final int TRANSACTION_onEarlyMediaStarted = 4;
        static final int TRANSACTION_onIncomingCall = 1;

        class Proxy implements ICmcCallEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICmcCallEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.cmc.ICmcCallEventListener
            public void onCallEnded(CmcCallEventInfo cmcCallEventInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmcCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cmcCallEventInfo, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.cmc.ICmcCallEventListener
            public void onCallError(CmcCallEventInfo cmcCallEventInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmcCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cmcCallEventInfo, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.cmc.ICmcCallEventListener
            public void onCallEstablished(CmcCallEventInfo cmcCallEventInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmcCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cmcCallEventInfo, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.cmc.ICmcCallEventListener
            public void onCallHeld(CmcCallEventInfo cmcCallEventInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmcCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cmcCallEventInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.cmc.ICmcCallEventListener
            public void onCallRecording(CmcRecordEventInfo cmcRecordEventInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmcCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cmcRecordEventInfo, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.cmc.ICmcCallEventListener
            public void onCallResumed(CmcCallEventInfo cmcCallEventInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmcCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cmcCallEventInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.cmc.ICmcCallEventListener
            public void onCallRingingBack(CmcCallEventInfo cmcCallEventInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmcCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cmcCallEventInfo, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.cmc.ICmcCallEventListener
            public void onCalling(CmcCallEventInfo cmcCallEventInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmcCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cmcCallEventInfo, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.cmc.ICmcCallEventListener
            public void onEarlyMediaStarted(CmcCallEventInfo cmcCallEventInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmcCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cmcCallEventInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.cmc.ICmcCallEventListener
            public void onIncomingCall(CmcCallEventInfo cmcCallEventInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICmcCallEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cmcCallEventInfo, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICmcCallEventListener.DESCRIPTOR);
        }

        public static ICmcCallEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICmcCallEventListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICmcCallEventListener)) ? new Proxy(iBinder) : (ICmcCallEventListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICmcCallEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICmcCallEventListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    CmcCallEventInfo cmcCallEventInfo = (CmcCallEventInfo) parcel.readTypedObject(CmcCallEventInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onIncomingCall(cmcCallEventInfo);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    CmcCallEventInfo cmcCallEventInfo2 = (CmcCallEventInfo) parcel.readTypedObject(CmcCallEventInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCalling(cmcCallEventInfo2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    CmcCallEventInfo cmcCallEventInfo3 = (CmcCallEventInfo) parcel.readTypedObject(CmcCallEventInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallRingingBack(cmcCallEventInfo3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    CmcCallEventInfo cmcCallEventInfo4 = (CmcCallEventInfo) parcel.readTypedObject(CmcCallEventInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEarlyMediaStarted(cmcCallEventInfo4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    CmcCallEventInfo cmcCallEventInfo5 = (CmcCallEventInfo) parcel.readTypedObject(CmcCallEventInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallEstablished(cmcCallEventInfo5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    CmcCallEventInfo cmcCallEventInfo6 = (CmcCallEventInfo) parcel.readTypedObject(CmcCallEventInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallEnded(cmcCallEventInfo6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    CmcCallEventInfo cmcCallEventInfo7 = (CmcCallEventInfo) parcel.readTypedObject(CmcCallEventInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallError(cmcCallEventInfo7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    CmcCallEventInfo cmcCallEventInfo8 = (CmcCallEventInfo) parcel.readTypedObject(CmcCallEventInfo.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallHeld(cmcCallEventInfo8, i3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    CmcCallEventInfo cmcCallEventInfo9 = (CmcCallEventInfo) parcel.readTypedObject(CmcCallEventInfo.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallResumed(cmcCallEventInfo9, i4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    CmcRecordEventInfo cmcRecordEventInfo = (CmcRecordEventInfo) parcel.readTypedObject(CmcRecordEventInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallRecording(cmcRecordEventInfo);
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

    public class Default implements ICmcCallEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.cmc.ICmcCallEventListener
        public void onCallEnded(CmcCallEventInfo cmcCallEventInfo) throws RemoteException {
        }

        @Override // com.sec.ims.cmc.ICmcCallEventListener
        public void onCallError(CmcCallEventInfo cmcCallEventInfo) throws RemoteException {
        }

        @Override // com.sec.ims.cmc.ICmcCallEventListener
        public void onCallEstablished(CmcCallEventInfo cmcCallEventInfo) throws RemoteException {
        }

        @Override // com.sec.ims.cmc.ICmcCallEventListener
        public void onCallRecording(CmcRecordEventInfo cmcRecordEventInfo) throws RemoteException {
        }

        @Override // com.sec.ims.cmc.ICmcCallEventListener
        public void onCallRingingBack(CmcCallEventInfo cmcCallEventInfo) throws RemoteException {
        }

        @Override // com.sec.ims.cmc.ICmcCallEventListener
        public void onCalling(CmcCallEventInfo cmcCallEventInfo) throws RemoteException {
        }

        @Override // com.sec.ims.cmc.ICmcCallEventListener
        public void onEarlyMediaStarted(CmcCallEventInfo cmcCallEventInfo) throws RemoteException {
        }

        @Override // com.sec.ims.cmc.ICmcCallEventListener
        public void onIncomingCall(CmcCallEventInfo cmcCallEventInfo) throws RemoteException {
        }

        @Override // com.sec.ims.cmc.ICmcCallEventListener
        public void onCallHeld(CmcCallEventInfo cmcCallEventInfo, int i) throws RemoteException {
        }

        @Override // com.sec.ims.cmc.ICmcCallEventListener
        public void onCallResumed(CmcCallEventInfo cmcCallEventInfo, int i) throws RemoteException {
        }
    }
}
