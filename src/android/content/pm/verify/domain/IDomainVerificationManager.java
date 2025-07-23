package android.content.pm.verify.domain;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IDomainVerificationManager extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.verify.domain.IDomainVerificationManager";

    public static class Default implements IDomainVerificationManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public DomainVerificationInfo getDomainVerificationInfo(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public DomainVerificationUserState getDomainVerificationUserState(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public List<DomainOwner> getOwnersForDomain(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public Bundle getUriRelativeFilterGroups(String str, List<String> list) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public List<String> queryValidVerificationPackageNames() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public void setDomainVerificationLinkHandlingAllowed(String str, boolean z, int i) throws RemoteException {
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public int setDomainVerificationStatus(String str, DomainSet domainSet, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public int setDomainVerificationUserSelection(String str, DomainSet domainSet, boolean z, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.verify.domain.IDomainVerificationManager
        public void setUriRelativeFilterGroups(String str, Bundle bundle) throws RemoteException {
        }
    }

    DomainVerificationInfo getDomainVerificationInfo(String str) throws RemoteException;

    DomainVerificationUserState getDomainVerificationUserState(String str, int i) throws RemoteException;

    List<DomainOwner> getOwnersForDomain(String str, int i) throws RemoteException;

    Bundle getUriRelativeFilterGroups(String str, List<String> list) throws RemoteException;

    List<String> queryValidVerificationPackageNames() throws RemoteException;

    void setDomainVerificationLinkHandlingAllowed(String str, boolean z, int i) throws RemoteException;

    int setDomainVerificationStatus(String str, DomainSet domainSet, int i) throws RemoteException;

    int setDomainVerificationUserSelection(String str, DomainSet domainSet, boolean z, int i) throws RemoteException;

    void setUriRelativeFilterGroups(String str, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IDomainVerificationManager {
        static final int TRANSACTION_getDomainVerificationInfo = 2;
        static final int TRANSACTION_getDomainVerificationUserState = 3;
        static final int TRANSACTION_getOwnersForDomain = 4;
        static final int TRANSACTION_getUriRelativeFilterGroups = 9;
        static final int TRANSACTION_queryValidVerificationPackageNames = 1;
        static final int TRANSACTION_setDomainVerificationLinkHandlingAllowed = 6;
        static final int TRANSACTION_setDomainVerificationStatus = 5;
        static final int TRANSACTION_setDomainVerificationUserSelection = 7;
        static final int TRANSACTION_setUriRelativeFilterGroups = 8;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, IDomainVerificationManager.DESCRIPTOR);
        }

        public static IDomainVerificationManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDomainVerificationManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDomainVerificationManager)) {
                return (IDomainVerificationManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "queryValidVerificationPackageNames";
                case 2:
                    return "getDomainVerificationInfo";
                case 3:
                    return "getDomainVerificationUserState";
                case 4:
                    return "getOwnersForDomain";
                case 5:
                    return "setDomainVerificationStatus";
                case 6:
                    return "setDomainVerificationLinkHandlingAllowed";
                case 7:
                    return "setDomainVerificationUserSelection";
                case 8:
                    return "setUriRelativeFilterGroups";
                case 9:
                    return "getUriRelativeFilterGroups";
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
                parcel.enforceInterface(IDomainVerificationManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDomainVerificationManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    List<String> queryValidVerificationPackageNames = queryValidVerificationPackageNames();
                    parcel2.writeNoException();
                    parcel2.writeStringList(queryValidVerificationPackageNames);
                    return true;
                case 2:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    DomainVerificationInfo domainVerificationInfo = getDomainVerificationInfo(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(domainVerificationInfo, 1);
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    DomainVerificationUserState domainVerificationUserState = getDomainVerificationUserState(readString2, readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(domainVerificationUserState, 1);
                    return true;
                case 4:
                    String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<DomainOwner> ownersForDomain = getOwnersForDomain(readString3, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(ownersForDomain, 1);
                    return true;
                case 5:
                    String readString4 = parcel.readString();
                    DomainSet domainSet = (DomainSet) parcel.readTypedObject(DomainSet.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int domainVerificationStatus = setDomainVerificationStatus(readString4, domainSet, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(domainVerificationStatus);
                    return true;
                case 6:
                    String readString5 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDomainVerificationLinkHandlingAllowed(readString5, readBoolean, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString6 = parcel.readString();
                    DomainSet domainSet2 = (DomainSet) parcel.readTypedObject(DomainSet.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int domainVerificationUserSelection = setDomainVerificationUserSelection(readString6, domainSet2, readBoolean2, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(domainVerificationUserSelection);
                    return true;
                case 8:
                    String readString7 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUriRelativeFilterGroups(readString7, bundle);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString8 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    Bundle uriRelativeFilterGroups = getUriRelativeFilterGroups(readString8, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(uriRelativeFilterGroups, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDomainVerificationManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDomainVerificationManager.DESCRIPTOR;
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public List<String> queryValidVerificationPackageNames() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public DomainVerificationInfo getDomainVerificationInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (DomainVerificationInfo) obtain2.readTypedObject(DomainVerificationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public DomainVerificationUserState getDomainVerificationUserState(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (DomainVerificationUserState) obtain2.readTypedObject(DomainVerificationUserState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public List<DomainOwner> getOwnersForDomain(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(DomainOwner.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public int setDomainVerificationStatus(String str, DomainSet domainSet, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(domainSet, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public void setDomainVerificationLinkHandlingAllowed(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public int setDomainVerificationUserSelection(String str, DomainSet domainSet, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(domainSet, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public void setUriRelativeFilterGroups(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public Bundle getUriRelativeFilterGroups(String str, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
