package com.sec.ims.scab;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ICABService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.scab.ICABService";

    void addBatchOfContacts(List<String> list) throws RemoteException;

    void businessLineReadyForSync(String str, boolean z) throws RemoteException;

    void deleteBatchOfContacts(List<String> list) throws RemoteException;

    void disableService() throws RemoteException;

    void enableService() throws RemoteException;

    boolean isPendingUploadContactsExists() throws RemoteException;

    void onBufferDBReadResult(long j, boolean z) throws RemoteException;

    void processUndownloadedBusinessContacts(String str) throws RemoteException;

    void updateBatchOfContacts(List<String> list) throws RemoteException;

    void uploadAddressBook(List<String> list) throws RemoteException;

    public class Default implements ICABService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.scab.ICABService
        public boolean isPendingUploadContactsExists() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.scab.ICABService
        public void disableService() throws RemoteException {
        }

        @Override // com.sec.ims.scab.ICABService
        public void enableService() throws RemoteException {
        }

        @Override // com.sec.ims.scab.ICABService
        public void addBatchOfContacts(List<String> list) throws RemoteException {
        }

        @Override // com.sec.ims.scab.ICABService
        public void deleteBatchOfContacts(List<String> list) throws RemoteException {
        }

        @Override // com.sec.ims.scab.ICABService
        public void processUndownloadedBusinessContacts(String str) throws RemoteException {
        }

        @Override // com.sec.ims.scab.ICABService
        public void updateBatchOfContacts(List<String> list) throws RemoteException {
        }

        @Override // com.sec.ims.scab.ICABService
        public void uploadAddressBook(List<String> list) throws RemoteException {
        }

        @Override // com.sec.ims.scab.ICABService
        public void businessLineReadyForSync(String str, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.scab.ICABService
        public void onBufferDBReadResult(long j, boolean z) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements ICABService {
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

        class Proxy implements ICABService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.sec.ims.scab.ICABService
            public void addBatchOfContacts(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.scab.ICABService
            public void businessLineReadyForSync(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.scab.ICABService
            public void deleteBatchOfContacts(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.scab.ICABService
            public void disableService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.scab.ICABService
            public void enableService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ICABService.DESCRIPTOR;
            }

            @Override // com.sec.ims.scab.ICABService
            public boolean isPendingUploadContactsExists() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.scab.ICABService
            public void onBufferDBReadResult(long j, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.scab.ICABService
            public void processUndownloadedBusinessContacts(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.scab.ICABService
            public void updateBatchOfContacts(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.scab.ICABService
            public void uploadAddressBook(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICABService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICABService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICABService)) ? new Proxy(iBinder) : (ICABService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICABService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICABService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long j = parcel.readLong();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onBufferDBReadResult(j, z);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String string = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    businessLineReadyForSync(string, z2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    processUndownloadedBusinessContacts(string2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    uploadAddressBook(arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    addBatchOfContacts(arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    deleteBatchOfContacts(arrayListCreateStringArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    updateBatchOfContacts(arrayListCreateStringArrayList4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    boolean zIsPendingUploadContactsExists = isPendingUploadContactsExists();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPendingUploadContactsExists);
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
