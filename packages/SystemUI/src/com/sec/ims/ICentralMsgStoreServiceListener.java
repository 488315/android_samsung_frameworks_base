package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ICentralMsgStoreServiceListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.ICentralMsgStoreServiceListener";

    void onCmsAccountInfoDelivered(String str, String str2, int i) throws RemoteException;

    void onCmsDeRegistrationCompleted(int i) throws RemoteException;

    void onCmsDeRegistrationCompletedWithDetails(int i, int i2) throws RemoteException;

    void onCmsPushMessageReceived(String str, String str2, String str3) throws RemoteException;

    void onCmsRegistrationCompleted(int i, int i2) throws RemoteException;

    void onCmsSdChanged(boolean z, String str, int i) throws RemoteException;

    void onCmsSdManagementCompleted(int i, String str, int i2, int i3) throws RemoteException;

    public abstract class Stub extends Binder implements ICentralMsgStoreServiceListener {
        static final int TRANSACTION_onCmsAccountInfoDelivered = 5;
        static final int TRANSACTION_onCmsDeRegistrationCompleted = 2;
        static final int TRANSACTION_onCmsDeRegistrationCompletedWithDetails = 7;
        static final int TRANSACTION_onCmsPushMessageReceived = 6;
        static final int TRANSACTION_onCmsRegistrationCompleted = 1;
        static final int TRANSACTION_onCmsSdChanged = 4;
        static final int TRANSACTION_onCmsSdManagementCompleted = 3;

        class Proxy implements ICentralMsgStoreServiceListener {
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
            public void onCmsAccountInfoDelivered(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreServiceListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreServiceListener
            public void onCmsDeRegistrationCompleted(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreServiceListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreServiceListener
            public void onCmsDeRegistrationCompletedWithDetails(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreServiceListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreServiceListener
            public void onCmsPushMessageReceived(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreServiceListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreServiceListener
            public void onCmsRegistrationCompleted(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreServiceListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreServiceListener
            public void onCmsSdChanged(boolean z, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreServiceListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreServiceListener
            public void onCmsSdManagementCompleted(int i, String str, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreServiceListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICentralMsgStoreServiceListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICentralMsgStoreServiceListener)) ? new Proxy(iBinder) : (ICentralMsgStoreServiceListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICentralMsgStoreServiceListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICentralMsgStoreServiceListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCmsRegistrationCompleted(i3, i4);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCmsDeRegistrationCompleted(i5);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i6 = parcel.readInt();
                    String string = parcel.readString();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCmsSdManagementCompleted(i6, string, i7, i8);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean z = parcel.readBoolean();
                    String string2 = parcel.readString();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCmsSdChanged(z, string2, i9);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCmsAccountInfoDelivered(string3, string4, i10);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onCmsPushMessageReceived(string5, string6, string7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCmsDeRegistrationCompletedWithDetails(i11, i12);
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

    public class Default implements ICentralMsgStoreServiceListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.ICentralMsgStoreServiceListener
        public void onCmsDeRegistrationCompleted(int i) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreServiceListener
        public void onCmsDeRegistrationCompletedWithDetails(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreServiceListener
        public void onCmsRegistrationCompleted(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreServiceListener
        public void onCmsAccountInfoDelivered(String str, String str2, int i) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreServiceListener
        public void onCmsPushMessageReceived(String str, String str2, String str3) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreServiceListener
        public void onCmsSdChanged(boolean z, String str, int i) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreServiceListener
        public void onCmsSdManagementCompleted(int i, String str, int i2, int i3) throws RemoteException {
        }
    }
}
