package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ICentralMsgStoreServiceListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.ICentralMsgStoreServiceListener";

    void onCmsAccountInfoDelivered(String str, String str2, int i);

    void onCmsDeRegistrationCompleted(int i);

    void onCmsPushMessageReceived(String str, String str2, String str3);

    void onCmsRegistrationCompleted(int i, int i2);

    void onCmsSdChanged(boolean z, String str, int i);

    void onCmsSdManagementCompleted(int i, String str, int i2, int i3);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements ICentralMsgStoreServiceListener {
        static final int TRANSACTION_onCmsAccountInfoDelivered = 5;
        static final int TRANSACTION_onCmsDeRegistrationCompleted = 2;
        static final int TRANSACTION_onCmsPushMessageReceived = 6;
        static final int TRANSACTION_onCmsRegistrationCompleted = 1;
        static final int TRANSACTION_onCmsSdChanged = 4;
        static final int TRANSACTION_onCmsSdManagementCompleted = 3;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements ICentralMsgStoreServiceListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICentralMsgStoreServiceListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.ICentralMsgStoreServiceListener
            public void onCmsAccountInfoDelivered(String str, String str2, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreServiceListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreServiceListener
            public void onCmsDeRegistrationCompleted(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreServiceListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreServiceListener
            public void onCmsPushMessageReceived(String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreServiceListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreServiceListener
            public void onCmsRegistrationCompleted(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreServiceListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreServiceListener
            public void onCmsSdChanged(boolean z, String str, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreServiceListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreServiceListener
            public void onCmsSdManagementCompleted(int i, String str, int i2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreServiceListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICentralMsgStoreServiceListener.DESCRIPTOR);
        }

        public static ICentralMsgStoreServiceListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICentralMsgStoreServiceListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ICentralMsgStoreServiceListener)) ? new Proxy(iBinder) : (ICentralMsgStoreServiceListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICentralMsgStoreServiceListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICentralMsgStoreServiceListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCmsRegistrationCompleted(readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCmsDeRegistrationCompleted(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    String readString = parcel.readString();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCmsSdManagementCompleted(readInt4, readString, readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean readBoolean = parcel.readBoolean();
                    String readString2 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCmsSdChanged(readBoolean, readString2, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCmsAccountInfoDelivered(readString3, readString4, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onCmsPushMessageReceived(readString5, readString6, readString7);
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
    public static class Default implements ICentralMsgStoreServiceListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.ICentralMsgStoreServiceListener
        public void onCmsDeRegistrationCompleted(int i) {
        }

        @Override // com.sec.ims.ICentralMsgStoreServiceListener
        public void onCmsRegistrationCompleted(int i, int i2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreServiceListener
        public void onCmsAccountInfoDelivered(String str, String str2, int i) {
        }

        @Override // com.sec.ims.ICentralMsgStoreServiceListener
        public void onCmsPushMessageReceived(String str, String str2, String str3) {
        }

        @Override // com.sec.ims.ICentralMsgStoreServiceListener
        public void onCmsSdChanged(boolean z, String str, int i) {
        }

        @Override // com.sec.ims.ICentralMsgStoreServiceListener
        public void onCmsSdManagementCompleted(int i, String str, int i2, int i3) {
        }
    }
}
