package com.android.internal.backup;

import android.app.backup.IBackupManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IObbBackupService extends IInterface {

    public static class Default implements IObbBackupService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.backup.IObbBackupService
        public void backupObbs(String str, ParcelFileDescriptor parcelFileDescriptor, int i, IBackupManager iBackupManager) throws RemoteException {
        }

        @Override // com.android.internal.backup.IObbBackupService
        public void restoreObbFile(String str, ParcelFileDescriptor parcelFileDescriptor, long j, int i, String str2, long j2, long j3, int i2, IBackupManager iBackupManager) throws RemoteException {
        }
    }

    void backupObbs(String str, ParcelFileDescriptor parcelFileDescriptor, int i, IBackupManager iBackupManager) throws RemoteException;

    void restoreObbFile(String str, ParcelFileDescriptor parcelFileDescriptor, long j, int i, String str2, long j2, long j3, int i2, IBackupManager iBackupManager) throws RemoteException;

    public static abstract class Stub extends Binder implements IObbBackupService {
        public static final String DESCRIPTOR = "com.android.internal.backup.IObbBackupService";
        static final int TRANSACTION_backupObbs = 1;
        static final int TRANSACTION_restoreObbFile = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IObbBackupService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IObbBackupService)) {
                return (IObbBackupService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "backupObbs";
            }
            if (i != 2) {
                return null;
            }
            return "restoreObbFile";
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
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                int readInt = parcel.readInt();
                IBackupManager asInterface = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                backupObbs(readString, parcelFileDescriptor, readInt, asInterface);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                long readLong = parcel.readLong();
                int readInt2 = parcel.readInt();
                String readString3 = parcel.readString();
                long readLong2 = parcel.readLong();
                long readLong3 = parcel.readLong();
                int readInt3 = parcel.readInt();
                IBackupManager asInterface2 = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                restoreObbFile(readString2, parcelFileDescriptor2, readLong, readInt2, readString3, readLong2, readLong3, readInt3, asInterface2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IObbBackupService {
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

            @Override // com.android.internal.backup.IObbBackupService
            public void backupObbs(String str, ParcelFileDescriptor parcelFileDescriptor, int i, IBackupManager iBackupManager) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBackupManager);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IObbBackupService
            public void restoreObbFile(String str, ParcelFileDescriptor parcelFileDescriptor, long j, int i, String str2, long j2, long j3, int i2, IBackupManager iBackupManager) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iBackupManager);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
