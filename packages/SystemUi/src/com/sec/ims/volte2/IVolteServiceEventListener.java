package com.sec.ims.volte2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.sec.ims.DialogEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IVolteServiceEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IVolteServiceEventListener";

    void onCdpnInfo(String str, int i);

    void onDialogEvent(DialogEvent dialogEvent);

    void onIncomingCall(int i);

    void onPullingCall(int i);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IVolteServiceEventListener {
        static final int TRANSACTION_onCdpnInfo = 2;
        static final int TRANSACTION_onDialogEvent = 4;
        static final int TRANSACTION_onIncomingCall = 1;
        static final int TRANSACTION_onPullingCall = 3;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IVolteServiceEventListener {
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
            public void onCdpnInfo(String str, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteServiceEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteServiceEventListener
            public void onDialogEvent(DialogEvent dialogEvent) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteServiceEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(dialogEvent, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteServiceEventListener
            public void onIncomingCall(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteServiceEventListener
            public void onPullingCall(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVolteServiceEventListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IVolteServiceEventListener)) ? new Proxy(iBinder) : (IVolteServiceEventListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVolteServiceEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVolteServiceEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onIncomingCall(readInt);
                parcel2.writeNoException();
            } else if (i == 2) {
                String readString = parcel.readString();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCdpnInfo(readString, readInt2);
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onPullingCall(readInt3);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IVolteServiceEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteServiceEventListener
        public void onDialogEvent(DialogEvent dialogEvent) {
        }

        @Override // com.sec.ims.volte2.IVolteServiceEventListener
        public void onIncomingCall(int i) {
        }

        @Override // com.sec.ims.volte2.IVolteServiceEventListener
        public void onPullingCall(int i) {
        }

        @Override // com.sec.ims.volte2.IVolteServiceEventListener
        public void onCdpnInfo(String str, int i) {
        }
    }
}
