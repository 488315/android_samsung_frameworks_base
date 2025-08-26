package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IEpdgListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.IEpdgListener";

    void onEpdgAvailable(int i, int i2, int i3) throws RemoteException;

    void onEpdgDeregister(int i) throws RemoteException;

    void onEpdgHandoverEnableChanged(int i, boolean z) throws RemoteException;

    void onEpdgHandoverResult(int i, int i2, int i3, String str) throws RemoteException;

    void onEpdgIpsecConnection(int i, String str, int i2, int i3) throws RemoteException;

    void onEpdgIpsecDisconnection(int i, String str) throws RemoteException;

    void onEpdgRegister(int i, boolean z) throws RemoteException;

    void onEpdgReleaseCall(int i) throws RemoteException;

    void onEpdgShowPopup(int i, int i2) throws RemoteException;

    public abstract class Stub extends Binder implements IEpdgListener {
        static final int TRANSACTION_onEpdgAvailable = 1;
        static final int TRANSACTION_onEpdgDeregister = 4;
        static final int TRANSACTION_onEpdgHandoverEnableChanged = 9;
        static final int TRANSACTION_onEpdgHandoverResult = 2;
        static final int TRANSACTION_onEpdgIpsecConnection = 5;
        static final int TRANSACTION_onEpdgIpsecDisconnection = 6;
        static final int TRANSACTION_onEpdgRegister = 3;
        static final int TRANSACTION_onEpdgReleaseCall = 8;
        static final int TRANSACTION_onEpdgShowPopup = 7;

        class Proxy implements IEpdgListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEpdgListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgAvailable(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgDeregister(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgHandoverEnableChanged(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgHandoverResult(int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgIpsecConnection(int i, String str, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgIpsecDisconnection(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgRegister(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgReleaseCall(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgShowPopup(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IEpdgListener.DESCRIPTOR);
        }

        public static IEpdgListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEpdgListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IEpdgListener)) ? new Proxy(iBinder) : (IEpdgListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEpdgListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEpdgListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEpdgAvailable(i3, i4, i5);
                    return true;
                case 2:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onEpdgHandoverResult(i6, i7, i8, string);
                    return true;
                case 3:
                    int i9 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onEpdgRegister(i9, z);
                    return true;
                case 4:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEpdgDeregister(i10);
                    return true;
                case 5:
                    int i11 = parcel.readInt();
                    String string2 = parcel.readString();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEpdgIpsecConnection(i11, string2, i12, i13);
                    return true;
                case 6:
                    int i14 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onEpdgIpsecDisconnection(i14, string3);
                    return true;
                case 7:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEpdgShowPopup(i15, i16);
                    return true;
                case 8:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEpdgReleaseCall(i17);
                    return true;
                case 9:
                    int i18 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onEpdgHandoverEnableChanged(i18, z2);
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

    public class Default implements IEpdgListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgDeregister(int i) throws RemoteException {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgReleaseCall(int i) throws RemoteException {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgHandoverEnableChanged(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgIpsecDisconnection(int i, String str) throws RemoteException {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgRegister(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgShowPopup(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgAvailable(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgHandoverResult(int i, int i2, int i3, String str) throws RemoteException {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgIpsecConnection(int i, String str, int i2, int i3) throws RemoteException {
        }
    }
}
