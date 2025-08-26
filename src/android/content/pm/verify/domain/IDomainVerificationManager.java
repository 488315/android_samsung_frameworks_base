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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDomainVerificationManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDomainVerificationManager)) {
                return (IDomainVerificationManager) iInterfaceQueryLocalInterface;
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
                    List<String> listQueryValidVerificationPackageNames = queryValidVerificationPackageNames();
                    parcel2.writeNoException();
                    parcel2.writeStringList(listQueryValidVerificationPackageNames);
                    return true;
                case 2:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    DomainVerificationInfo domainVerificationInfo = getDomainVerificationInfo(string);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(domainVerificationInfo, 1);
                    return true;
                case 3:
                    String string2 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    DomainVerificationUserState domainVerificationUserState = getDomainVerificationUserState(string2, i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(domainVerificationUserState, 1);
                    return true;
                case 4:
                    String string3 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<DomainOwner> ownersForDomain = getOwnersForDomain(string3, i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(ownersForDomain, 1);
                    return true;
                case 5:
                    String string4 = parcel.readString();
                    DomainSet domainSet = (DomainSet) parcel.readTypedObject(DomainSet.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int domainVerificationStatus = setDomainVerificationStatus(string4, domainSet, i5);
                    parcel2.writeNoException();
                    parcel2.writeInt(domainVerificationStatus);
                    return true;
                case 6:
                    String string5 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDomainVerificationLinkHandlingAllowed(string5, z, i6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string6 = parcel.readString();
                    DomainSet domainSet2 = (DomainSet) parcel.readTypedObject(DomainSet.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int domainVerificationUserSelection = setDomainVerificationUserSelection(string6, domainSet2, z2, i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(domainVerificationUserSelection);
                    return true;
                case 8:
                    String string7 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUriRelativeFilterGroups(string7, bundle);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string8 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    Bundle uriRelativeFilterGroups = getUriRelativeFilterGroups(string8, arrayListCreateStringArrayList);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public DomainVerificationInfo getDomainVerificationInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DomainVerificationInfo) parcelObtain2.readTypedObject(DomainVerificationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public DomainVerificationUserState getDomainVerificationUserState(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DomainVerificationUserState) parcelObtain2.readTypedObject(DomainVerificationUserState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public List<DomainOwner> getOwnersForDomain(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(DomainOwner.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public int setDomainVerificationStatus(String str, DomainSet domainSet, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(domainSet, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public void setDomainVerificationLinkHandlingAllowed(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public int setDomainVerificationUserSelection(String str, DomainSet domainSet, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(domainSet, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public void setUriRelativeFilterGroups(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.verify.domain.IDomainVerificationManager
            public Bundle getUriRelativeFilterGroups(String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDomainVerificationManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
