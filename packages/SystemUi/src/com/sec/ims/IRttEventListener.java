package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IRttEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.IRttEventListener";

    void onRttEvent(String str);

    void onRttEventBySession(int i, String str);

    void onSendRttSessionModifyRequest(int i, boolean z);

    void onSendRttSessionModifyResponse(int i, boolean z, boolean z2);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IRttEventListener {
        static final int TRANSACTION_onRttEvent = 1;
        static final int TRANSACTION_onRttEventBySession = 2;
        static final int TRANSACTION_onSendRttSessionModifyRequest = 3;
        static final int TRANSACTION_onSendRttSessionModifyResponse = 4;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IRttEventListener {
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
            public void onRttEvent(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRttEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IRttEventListener
            public void onRttEventBySession(int i, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRttEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IRttEventListener
            public void onSendRttSessionModifyRequest(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRttEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IRttEventListener
            public void onSendRttSessionModifyResponse(int i, boolean z, boolean z2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRttEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRttEventListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IRttEventListener)) ? new Proxy(iBinder) : (IRttEventListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRttEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRttEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onRttEvent(readString);
                parcel2.writeNoException();
            } else if (i == 2) {
                int readInt = parcel.readInt();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onRttEventBySession(readInt, readString2);
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt2 = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onSendRttSessionModifyRequest(readInt2, readBoolean);
                parcel2.writeNoException();
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int readInt3 = parcel.readInt();
                boolean readBoolean2 = parcel.readBoolean();
                boolean readBoolean3 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onSendRttSessionModifyResponse(readInt3, readBoolean2, readBoolean3);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IRttEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.IRttEventListener
        public void onRttEvent(String str) {
        }

        @Override // com.sec.ims.IRttEventListener
        public void onRttEventBySession(int i, String str) {
        }

        @Override // com.sec.ims.IRttEventListener
        public void onSendRttSessionModifyRequest(int i, boolean z) {
        }

        @Override // com.sec.ims.IRttEventListener
        public void onSendRttSessionModifyResponse(int i, boolean z, boolean z2) {
        }
    }
}
