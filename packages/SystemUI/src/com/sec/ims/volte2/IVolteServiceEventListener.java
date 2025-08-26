package com.sec.ims.volte2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.sec.ims.DialogEvent;

/* loaded from: classes4.dex */
public interface IVolteServiceEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IVolteServiceEventListener";

    void onCdpnInfo(String str, int i) throws RemoteException;

    void onDialogEvent(DialogEvent dialogEvent) throws RemoteException;

    void onIncomingCall(int i) throws RemoteException;

    void onPullingCall(int i) throws RemoteException;

    public abstract class Stub extends Binder implements IVolteServiceEventListener {
        static final int TRANSACTION_onCdpnInfo = 2;
        static final int TRANSACTION_onDialogEvent = 4;
        static final int TRANSACTION_onIncomingCall = 1;
        static final int TRANSACTION_onPullingCall = 3;

        class Proxy implements IVolteServiceEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVolteServiceEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.volte2.IVolteServiceEventListener
            public void onCdpnInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteServiceEventListener
            public void onDialogEvent(DialogEvent dialogEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(dialogEvent, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteServiceEventListener
            public void onIncomingCall(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteServiceEventListener
            public void onPullingCall(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IVolteServiceEventListener.DESCRIPTOR);
        }

        public static IVolteServiceEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVolteServiceEventListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IVolteServiceEventListener)) ? new Proxy(iBinder) : (IVolteServiceEventListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVolteServiceEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVolteServiceEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onIncomingCall(i3);
                parcel2.writeNoException();
            } else if (i == 2) {
                String string = parcel.readString();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCdpnInfo(string, i4);
                parcel2.writeNoException();
            } else if (i == 3) {
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onPullingCall(i5);
                parcel2.writeNoException();
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                DialogEvent dialogEvent = (DialogEvent) parcel.readTypedObject(DialogEvent.CREATOR);
                parcel.enforceNoDataAvail();
                onDialogEvent(dialogEvent);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IVolteServiceEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteServiceEventListener
        public void onDialogEvent(DialogEvent dialogEvent) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteServiceEventListener
        public void onIncomingCall(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteServiceEventListener
        public void onPullingCall(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteServiceEventListener
        public void onCdpnInfo(String str, int i) throws RemoteException {
        }
    }
}
