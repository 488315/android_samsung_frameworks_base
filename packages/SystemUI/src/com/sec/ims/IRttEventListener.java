package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IRttEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.IRttEventListener";

    void onRttEvent(String str) throws RemoteException;

    void onRttEventBySession(int i, String str) throws RemoteException;

    void onSendRttSessionModifyRequest(int i, boolean z) throws RemoteException;

    void onSendRttSessionModifyResponse(int i, boolean z, boolean z2) throws RemoteException;

    public abstract class Stub extends Binder implements IRttEventListener {
        static final int TRANSACTION_onRttEvent = 1;
        static final int TRANSACTION_onRttEventBySession = 2;
        static final int TRANSACTION_onSendRttSessionModifyRequest = 3;
        static final int TRANSACTION_onSendRttSessionModifyResponse = 4;

        class Proxy implements IRttEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRttEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.IRttEventListener
            public void onRttEvent(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRttEventListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IRttEventListener
            public void onRttEventBySession(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRttEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IRttEventListener
            public void onSendRttSessionModifyRequest(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRttEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IRttEventListener
            public void onSendRttSessionModifyResponse(int i, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRttEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IRttEventListener.DESCRIPTOR);
        }

        public static IRttEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRttEventListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IRttEventListener)) ? new Proxy(iBinder) : (IRttEventListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRttEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRttEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onRttEvent(string);
                parcel2.writeNoException();
            } else if (i == 2) {
                int i3 = parcel.readInt();
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onRttEventBySession(i3, string2);
                parcel2.writeNoException();
            } else if (i == 3) {
                int i4 = parcel.readInt();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onSendRttSessionModifyRequest(i4, z);
                parcel2.writeNoException();
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int i5 = parcel.readInt();
                boolean z2 = parcel.readBoolean();
                boolean z3 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onSendRttSessionModifyResponse(i5, z2, z3);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IRttEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.IRttEventListener
        public void onRttEvent(String str) throws RemoteException {
        }

        @Override // com.sec.ims.IRttEventListener
        public void onRttEventBySession(int i, String str) throws RemoteException {
        }

        @Override // com.sec.ims.IRttEventListener
        public void onSendRttSessionModifyRequest(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.IRttEventListener
        public void onSendRttSessionModifyResponse(int i, boolean z, boolean z2) throws RemoteException {
        }
    }
}
