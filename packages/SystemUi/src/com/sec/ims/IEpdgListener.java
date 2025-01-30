package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IEpdgListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.IEpdgListener";

    void onEpdgAvailable(int i, int i2, int i3);

    void onEpdgDeregister(int i);

    void onEpdgHandoverEnableChanged(int i, boolean z);

    void onEpdgHandoverResult(int i, int i2, int i3, String str);

    void onEpdgIpsecConnection(int i, String str, int i2, int i3);

    void onEpdgIpsecDisconnection(int i, String str);

    void onEpdgRegister(int i, boolean z);

    void onEpdgReleaseCall(int i);

    void onEpdgShowPopup(int i, int i2);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IEpdgListener {
        static final int TRANSACTION_onEpdgAvailable = 1;
        static final int TRANSACTION_onEpdgDeregister = 4;
        static final int TRANSACTION_onEpdgHandoverEnableChanged = 9;
        static final int TRANSACTION_onEpdgHandoverResult = 2;
        static final int TRANSACTION_onEpdgIpsecConnection = 5;
        static final int TRANSACTION_onEpdgIpsecDisconnection = 6;
        static final int TRANSACTION_onEpdgRegister = 3;
        static final int TRANSACTION_onEpdgReleaseCall = 8;
        static final int TRANSACTION_onEpdgShowPopup = 7;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IEpdgListener {
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
            public void onEpdgAvailable(int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgDeregister(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgHandoverEnableChanged(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgHandoverResult(int i, int i2, int i3, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgIpsecConnection(int i, String str, int i2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgIpsecDisconnection(int i, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgRegister(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgReleaseCall(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IEpdgListener
            public void onEpdgShowPopup(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEpdgListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEpdgListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IEpdgListener)) ? new Proxy(iBinder) : (IEpdgListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEpdgListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEpdgListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEpdgAvailable(readInt, readInt2, readInt3);
                    return true;
                case 2:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onEpdgHandoverResult(readInt4, readInt5, readInt6, readString);
                    return true;
                case 3:
                    int readInt7 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onEpdgRegister(readInt7, readBoolean);
                    return true;
                case 4:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEpdgDeregister(readInt8);
                    return true;
                case 5:
                    int readInt9 = parcel.readInt();
                    String readString2 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEpdgIpsecConnection(readInt9, readString2, readInt10, readInt11);
                    return true;
                case 6:
                    int readInt12 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onEpdgIpsecDisconnection(readInt12, readString3);
                    return true;
                case 7:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEpdgShowPopup(readInt13, readInt14);
                    return true;
                case 8:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEpdgReleaseCall(readInt15);
                    return true;
                case 9:
                    int readInt16 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onEpdgHandoverEnableChanged(readInt16, readBoolean2);
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
    public static class Default implements IEpdgListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgDeregister(int i) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgReleaseCall(int i) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgHandoverEnableChanged(int i, boolean z) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgIpsecDisconnection(int i, String str) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgRegister(int i, boolean z) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgShowPopup(int i, int i2) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgAvailable(int i, int i2, int i3) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgHandoverResult(int i, int i2, int i3, String str) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgIpsecConnection(int i, String str, int i2, int i3) {
        }
    }
}
