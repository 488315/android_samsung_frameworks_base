package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IImsSmsListener extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsSmsListener";

    public static class Default implements IImsSmsListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsSmsListener
        public void onMemoryAvailableResult(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsSmsListener
        public void onReceiveSmsDeliveryReportAck(int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsSmsListener
        public void onSendSmsResponse(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsSmsListener
        public void onSendSmsResult(int i, int i2, int i3, int i4, int i5) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsSmsListener
        public void onSmsReceived(int i, String str, byte[] bArr) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsSmsListener
        public void onSmsStatusReportReceived(int i, String str, byte[] bArr) throws RemoteException {
        }
    }

    void onMemoryAvailableResult(int i, int i2, int i3) throws RemoteException;

    void onReceiveSmsDeliveryReportAck(int i, int i2) throws RemoteException;

    void onSendSmsResponse(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    void onSendSmsResult(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void onSmsReceived(int i, String str, byte[] bArr) throws RemoteException;

    void onSmsStatusReportReceived(int i, String str, byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsSmsListener {
        static final int TRANSACTION_onMemoryAvailableResult = 6;
        static final int TRANSACTION_onReceiveSmsDeliveryReportAck = 5;
        static final int TRANSACTION_onSendSmsResponse = 4;
        static final int TRANSACTION_onSendSmsResult = 1;
        static final int TRANSACTION_onSmsReceived = 3;
        static final int TRANSACTION_onSmsStatusReportReceived = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IImsSmsListener.DESCRIPTOR);
        }

        public static IImsSmsListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsSmsListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsSmsListener)) {
                return (IImsSmsListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSendSmsResult";
                case 2:
                    return "onSmsStatusReportReceived";
                case 3:
                    return "onSmsReceived";
                case 4:
                    return "onSendSmsResponse";
                case 5:
                    return "onReceiveSmsDeliveryReportAck";
                case 6:
                    return "onMemoryAvailableResult";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsSmsListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsSmsListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSendSmsResult(readInt, readInt2, readInt3, readInt4, readInt5);
                    return true;
                case 2:
                    int readInt6 = parcel.readInt();
                    String readString = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onSmsStatusReportReceived(readInt6, readString, createByteArray);
                    return true;
                case 3:
                    int readInt7 = parcel.readInt();
                    String readString2 = parcel.readString();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onSmsReceived(readInt7, readString2, createByteArray2);
                    return true;
                case 4:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSendSmsResponse(readInt8, readInt9, readInt10, readInt11, readInt12, readInt13);
                    return true;
                case 5:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onReceiveSmsDeliveryReportAck(readInt14, readInt15);
                    return true;
                case 6:
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onMemoryAvailableResult(readInt16, readInt17, readInt18);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsSmsListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsSmsListener.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsSmsListener
            public void onSendSmsResult(int i, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsSmsListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsSmsListener
            public void onSmsStatusReportReceived(int i, String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsSmsListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsSmsListener
            public void onSmsReceived(int i, String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsSmsListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsSmsListener
            public void onSendSmsResponse(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsSmsListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsSmsListener
            public void onReceiveSmsDeliveryReportAck(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsSmsListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsSmsListener
            public void onMemoryAvailableResult(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsSmsListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
