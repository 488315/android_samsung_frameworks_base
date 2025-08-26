package com.sec.ims.sms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISmsServiceEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.sms.ISmsServiceEventListener";

    void onReceiveIncomingSMS(int i, String str, byte[] bArr) throws RemoteException;

    void onReceiveSMSAck(int i, int i2, String str, byte[] bArr, int i3) throws RemoteException;

    void onReceiveSMSDeliveryReportAck(int i, int i2, int i3) throws RemoteException;

    public abstract class Stub extends Binder implements ISmsServiceEventListener {
        static final int TRANSACTION_onReceiveIncomingSMS = 1;
        static final int TRANSACTION_onReceiveSMSAck = 2;
        static final int TRANSACTION_onReceiveSMSDeliveryReportAck = 3;

        class Proxy implements ISmsServiceEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISmsServiceEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.sms.ISmsServiceEventListener
            public void onReceiveIncomingSMS(int i, String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISmsServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.sms.ISmsServiceEventListener
            public void onReceiveSMSAck(int i, int i2, String str, byte[] bArr, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISmsServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.sms.ISmsServiceEventListener
            public void onReceiveSMSDeliveryReportAck(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISmsServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
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
            attachInterface(this, ISmsServiceEventListener.DESCRIPTOR);
        }

        public static ISmsServiceEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISmsServiceEventListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ISmsServiceEventListener)) ? new Proxy(iBinder) : (ISmsServiceEventListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISmsServiceEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISmsServiceEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                String string = parcel.readString();
                byte[] bArrCreateByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onReceiveIncomingSMS(i3, string, bArrCreateByteArray);
                parcel2.writeNoException();
            } else if (i == 2) {
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                String string2 = parcel.readString();
                byte[] bArrCreateByteArray2 = parcel.createByteArray();
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onReceiveSMSAck(i4, i5, string2, bArrCreateByteArray2, i6);
                parcel2.writeNoException();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int i7 = parcel.readInt();
                int i8 = parcel.readInt();
                int i9 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onReceiveSMSDeliveryReportAck(i7, i8, i9);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements ISmsServiceEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.sms.ISmsServiceEventListener
        public void onReceiveIncomingSMS(int i, String str, byte[] bArr) throws RemoteException {
        }

        @Override // com.sec.ims.sms.ISmsServiceEventListener
        public void onReceiveSMSDeliveryReportAck(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.sec.ims.sms.ISmsServiceEventListener
        public void onReceiveSMSAck(int i, int i2, String str, byte[] bArr, int i3) throws RemoteException {
        }
    }
}
