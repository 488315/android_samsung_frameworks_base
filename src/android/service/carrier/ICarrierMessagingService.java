package android.service.carrier;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.carrier.ICarrierMessagingCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface ICarrierMessagingService extends IInterface {

    public static class Default implements ICarrierMessagingService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void downloadMms(Uri uri, int i, Uri uri2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void filterSms(MessagePdu messagePdu, String str, int i, int i2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendDataSms(byte[] bArr, int i, String str, int i2, int i3, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendMms(Uri uri, int i, Uri uri2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendMultipartTextSms(List<String> list, int i, String str, int i2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendTextSms(String str, int i, String str2, int i2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
        }
    }

    void downloadMms(Uri uri, int i, Uri uri2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException;

    void filterSms(MessagePdu messagePdu, String str, int i, int i2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException;

    void sendDataSms(byte[] bArr, int i, String str, int i2, int i3, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException;

    void sendMms(Uri uri, int i, Uri uri2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException;

    void sendMultipartTextSms(List<String> list, int i, String str, int i2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException;

    void sendTextSms(String str, int i, String str2, int i2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ICarrierMessagingService {
        public static final String DESCRIPTOR = "android.service.carrier.ICarrierMessagingService";
        static final int TRANSACTION_downloadMms = 6;
        static final int TRANSACTION_filterSms = 1;
        static final int TRANSACTION_sendDataSms = 3;
        static final int TRANSACTION_sendMms = 5;
        static final int TRANSACTION_sendMultipartTextSms = 4;
        static final int TRANSACTION_sendTextSms = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICarrierMessagingService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICarrierMessagingService)) {
                return (ICarrierMessagingService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "filterSms";
                case 2:
                    return "sendTextSms";
                case 3:
                    return "sendDataSms";
                case 4:
                    return "sendMultipartTextSms";
                case 5:
                    return "sendMms";
                case 6:
                    return "downloadMms";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    MessagePdu messagePdu = (MessagePdu) parcel.readTypedObject(MessagePdu.CREATOR);
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    ICarrierMessagingCallback asInterface = ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    filterSms(messagePdu, readString, readInt, readInt2, asInterface);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    String readString3 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    ICarrierMessagingCallback asInterface2 = ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendTextSms(readString2, readInt3, readString3, readInt4, asInterface2);
                    return true;
                case 3:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt5 = parcel.readInt();
                    String readString4 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    ICarrierMessagingCallback asInterface3 = ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendDataSms(createByteArray, readInt5, readString4, readInt6, readInt7, asInterface3);
                    return true;
                case 4:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    int readInt8 = parcel.readInt();
                    String readString5 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    ICarrierMessagingCallback asInterface4 = ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendMultipartTextSms(createStringArrayList, readInt8, readString5, readInt9, asInterface4);
                    return true;
                case 5:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt10 = parcel.readInt();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    ICarrierMessagingCallback asInterface5 = ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendMms(uri, readInt10, uri2, asInterface5);
                    return true;
                case 6:
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt11 = parcel.readInt();
                    Uri uri4 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    ICarrierMessagingCallback asInterface6 = ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    downloadMms(uri3, readInt11, uri4, asInterface6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICarrierMessagingService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.carrier.ICarrierMessagingService
            public void filterSms(MessagePdu messagePdu, String str, int i, int i2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(messagePdu, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iCarrierMessagingCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.carrier.ICarrierMessagingService
            public void sendTextSms(String str, int i, String str2, int i2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iCarrierMessagingCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.carrier.ICarrierMessagingService
            public void sendDataSms(byte[] bArr, int i, String str, int i2, int i3, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iCarrierMessagingCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.carrier.ICarrierMessagingService
            public void sendMultipartTextSms(List<String> list, int i, String str, int i2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iCarrierMessagingCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.carrier.ICarrierMessagingService
            public void sendMms(Uri uri, int i, Uri uri2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(uri2, 0);
                    obtain.writeStrongInterface(iCarrierMessagingCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.carrier.ICarrierMessagingService
            public void downloadMms(Uri uri, int i, Uri uri2, ICarrierMessagingCallback iCarrierMessagingCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(uri2, 0);
                    obtain.writeStrongInterface(iCarrierMessagingCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
