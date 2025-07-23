package com.sec.ims.sms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ISmsServiceEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.sms.ISmsServiceEventListener";

    void onReceiveIncomingSMS(int i, String str, byte[] bArr) throws RemoteException;

    void onReceiveSMSAck(int i, int i2, String str, byte[] bArr, int i3) throws RemoteException;

    void onReceiveSMSDeliveryReportAck(int i, int i2, int i3) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ISmsServiceEventListener {
        static final int TRANSACTION_onReceiveIncomingSMS = 1;
        static final int TRANSACTION_onReceiveSMSAck = 2;
        static final int TRANSACTION_onReceiveSMSDeliveryReportAck = 3;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISmsServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.sms.ISmsServiceEventListener
            public void onReceiveSMSAck(int i, int i2, String str, byte[] bArr, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISmsServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.sms.ISmsServiceEventListener
            public void onReceiveSMSDeliveryReportAck(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISmsServiceEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
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
            attachInterface(this, ISmsServiceEventListener.DESCRIPTOR);
        }

        public static ISmsServiceEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISmsServiceEventListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ISmsServiceEventListener)) ? new Proxy(iBinder) : (ISmsServiceEventListener) queryLocalInterface;
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
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onReceiveIncomingSMS(readInt, readString, createByteArray);
                parcel2.writeNoException();
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                String readString2 = parcel.readString();
                byte[] createByteArray2 = parcel.createByteArray();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onReceiveSMSAck(readInt2, readInt3, readString2, createByteArray2, readInt4);
                parcel2.writeNoException();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int readInt5 = parcel.readInt();
                int readInt6 = parcel.readInt();
                int readInt7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onReceiveSMSDeliveryReportAck(readInt5, readInt6, readInt7);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
