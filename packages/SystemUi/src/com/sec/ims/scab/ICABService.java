package com.sec.ims.scab;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ICABService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.scab.ICABService";

    void addBatchOfContacts(List<String> list);

    void businessLineReadyForSync(String str, boolean z);

    void deleteBatchOfContacts(List<String> list);

    void disableService();

    void enableService();

    boolean isPendingUploadContactsExists();

    void onBufferDBReadResult(long j, boolean z);

    void processUndownloadedBusinessContacts(String str);

    void updateBatchOfContacts(List<String> list);

    void uploadAddressBook(List<String> list);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements ICABService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.scab.ICABService
        public boolean isPendingUploadContactsExists() {
            return false;
        }

        @Override // com.sec.ims.scab.ICABService
        public void disableService() {
        }

        @Override // com.sec.ims.scab.ICABService
        public void enableService() {
        }

        @Override // com.sec.ims.scab.ICABService
        public void addBatchOfContacts(List<String> list) {
        }

        @Override // com.sec.ims.scab.ICABService
        public void deleteBatchOfContacts(List<String> list) {
        }

        @Override // com.sec.ims.scab.ICABService
        public void processUndownloadedBusinessContacts(String str) {
        }

        @Override // com.sec.ims.scab.ICABService
        public void updateBatchOfContacts(List<String> list) {
        }

        @Override // com.sec.ims.scab.ICABService
        public void uploadAddressBook(List<String> list) {
        }

        @Override // com.sec.ims.scab.ICABService
        public void businessLineReadyForSync(String str, boolean z) {
        }

        @Override // com.sec.ims.scab.ICABService
        public void onBufferDBReadResult(long j, boolean z) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements ICABService {
        static final int TRANSACTION_addBatchOfContacts = 5;
        static final int TRANSACTION_businessLineReadyForSync = 2;
        static final int TRANSACTION_deleteBatchOfContacts = 6;
        static final int TRANSACTION_disableService = 10;
        static final int TRANSACTION_enableService = 9;
        static final int TRANSACTION_isPendingUploadContactsExists = 8;
        static final int TRANSACTION_onBufferDBReadResult = 1;
        static final int TRANSACTION_processUndownloadedBusinessContacts = 3;
        static final int TRANSACTION_updateBatchOfContacts = 7;
        static final int TRANSACTION_uploadAddressBook = 4;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements ICABService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.sec.ims.scab.ICABService
            public void addBatchOfContacts(List<String> list) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.scab.ICABService
            public void businessLineReadyForSync(String str, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.scab.ICABService
            public void deleteBatchOfContacts(List<String> list) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.scab.ICABService
            public void disableService() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.scab.ICABService
            public void enableService() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ICABService.DESCRIPTOR;
            }

            @Override // com.sec.ims.scab.ICABService
            public boolean isPendingUploadContactsExists() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.scab.ICABService
            public void onBufferDBReadResult(long j, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.scab.ICABService
            public void processUndownloadedBusinessContacts(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.scab.ICABService
            public void updateBatchOfContacts(List<String> list) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.scab.ICABService
            public void uploadAddressBook(List<String> list) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICABService.DESCRIPTOR);
        }

        public static ICABService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICABService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ICABService)) ? new Proxy(iBinder) : (ICABService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICABService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICABService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onBufferDBReadResult(readLong, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    businessLineReadyForSync(readString, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    processUndownloadedBusinessContacts(readString2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    uploadAddressBook(createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    addBatchOfContacts(createStringArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    deleteBatchOfContacts(createStringArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    updateBatchOfContacts(createStringArrayList4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    boolean isPendingUploadContactsExists = isPendingUploadContactsExists();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPendingUploadContactsExists);
                    return true;
                case 9:
                    enableService();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    disableService();
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
}
