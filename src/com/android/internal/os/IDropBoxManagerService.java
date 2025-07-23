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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDropBoxManagerService)) {
                return (IDropBoxManagerService) queryLocalInterface;
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
                String readString = parcel.readString();
                byte[] createByteArray = parcel.createByteArray();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                addData(readString, createByteArray, readInt);
                parcel2.writeNoException();
            } else if (i == 2) {
                String readString2 = parcel.readString();
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                addFile(readString2, parcelFileDescriptor, readInt2);
                parcel2.writeNoException();
            } else if (i == 3) {
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean isTagEnabled = isTagEnabled(readString3);
                parcel2.writeNoException();
                parcel2.writeBoolean(isTagEnabled);
            } else if (i == 4) {
                String readString4 = parcel.readString();
                long readLong = parcel.readLong();
                String readString5 = parcel.readString();
                parcel.enforceNoDataAvail();
                DropBoxManager.Entry nextEntry = getNextEntry(readString4, readLong, readString5);
                parcel2.writeNoException();
                parcel2.writeTypedObject(nextEntry, 1);
            } else if (i == 5) {
                String readString6 = parcel.readString();
                long readLong2 = parcel.readLong();
                String readString7 = parcel.readString();
                String readString8 = parcel.readString();
                parcel.enforceNoDataAvail();
                DropBoxManager.Entry nextEntryWithAttribution = getNextEntryWithAttribution(readString6, readLong2, readString7, readString8);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public void addFile(String str, ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public boolean isTagEnabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public DropBoxManager.Entry getNextEntry(String str, long j, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (DropBoxManager.Entry) obtain2.readTypedObject(DropBoxManager.Entry.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public DropBoxManager.Entry getNextEntryWithAttribution(String str, long j, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (DropBoxManager.Entry) obtain2.readTypedObject(DropBoxManager.Entry.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
