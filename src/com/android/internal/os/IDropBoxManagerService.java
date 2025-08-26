package com.android.internal.os;

import android.os.Binder;
import android.os.DropBoxManager;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IDropBoxManagerService extends IInterface {

    public static class Default implements IDropBoxManagerService {
        @Override // com.android.internal.os.IDropBoxManagerService
        public void addData(String str, byte[] bArr, int i) throws RemoteException {
        }

        @Override // com.android.internal.os.IDropBoxManagerService
        public void addFile(String str, ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.os.IDropBoxManagerService
        public DropBoxManager.Entry getNextEntry(String str, long j, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.os.IDropBoxManagerService
        public DropBoxManager.Entry getNextEntryWithAttribution(String str, long j, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.os.IDropBoxManagerService
        public boolean isTagEnabled(String str) throws RemoteException {
            return false;
        }
    }

    void addData(String str, byte[] bArr, int i) throws RemoteException;

    void addFile(String str, ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException;

    DropBoxManager.Entry getNextEntry(String str, long j, String str2) throws RemoteException;

    DropBoxManager.Entry getNextEntryWithAttribution(String str, long j, String str2, String str3) throws RemoteException;

    boolean isTagEnabled(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IDropBoxManagerService {
        public static final String DESCRIPTOR = "com.android.internal.os.IDropBoxManagerService";
        static final int TRANSACTION_addData = 1;
        static final int TRANSACTION_addFile = 2;
        static final int TRANSACTION_getNextEntry = 4;
        static final int TRANSACTION_getNextEntryWithAttribution = 5;
        static final int TRANSACTION_isTagEnabled = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDropBoxManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDropBoxManagerService)) {
                return (IDropBoxManagerService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addData";
            }
            if (i == 2) {
                return "addFile";
            }
            if (i == 3) {
                return "isTagEnabled";
            }
            if (i == 4) {
                return "getNextEntry";
            }
            if (i != 5) {
                return null;
            }
            return "getNextEntryWithAttribution";
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
            if (i == 1) {
                String string = parcel.readString();
                byte[] bArrCreateByteArray = parcel.createByteArray();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                addData(string, bArrCreateByteArray, i3);
                parcel2.writeNoException();
            } else if (i == 2) {
                String string2 = parcel.readString();
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                addFile(string2, parcelFileDescriptor, i4);
                parcel2.writeNoException();
            } else if (i == 3) {
                String string3 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean zIsTagEnabled = isTagEnabled(string3);
                parcel2.writeNoException();
                parcel2.writeBoolean(zIsTagEnabled);
            } else if (i == 4) {
                String string4 = parcel.readString();
                long j = parcel.readLong();
                String string5 = parcel.readString();
                parcel.enforceNoDataAvail();
                DropBoxManager.Entry nextEntry = getNextEntry(string4, j, string5);
                parcel2.writeNoException();
                parcel2.writeTypedObject(nextEntry, 1);
            } else if (i == 5) {
                String string6 = parcel.readString();
                long j2 = parcel.readLong();
                String string7 = parcel.readString();
                String string8 = parcel.readString();
                parcel.enforceNoDataAvail();
                DropBoxManager.Entry nextEntryWithAttribution = getNextEntryWithAttribution(string6, j2, string7, string8);
                parcel2.writeNoException();
                parcel2.writeTypedObject(nextEntryWithAttribution, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDropBoxManagerService {
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

            @Override // com.android.internal.os.IDropBoxManagerService
            public void addData(String str, byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public void addFile(String str, ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public boolean isTagEnabled(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public DropBoxManager.Entry getNextEntry(String str, long j, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DropBoxManager.Entry) parcelObtain2.readTypedObject(DropBoxManager.Entry.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public DropBoxManager.Entry getNextEntryWithAttribution(String str, long j, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DropBoxManager.Entry) parcelObtain2.readTypedObject(DropBoxManager.Entry.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
