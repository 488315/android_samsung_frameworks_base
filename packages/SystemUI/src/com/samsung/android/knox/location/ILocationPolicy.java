package com.samsung.android.knox.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface ILocationPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.location.ILocationPolicy";

    public class Default implements ILocationPolicy {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public List<String> getAllLocationProviders(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean getIndividualLocationProvider(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean isGPSOn(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean isGPSStateChangeAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean isLocationProviderBlocked(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean isLocationProviderBlockedAsUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean setGPSStateChangeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean setIndividualLocationProvider(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean startGPS(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }
    }

    List<String> getAllLocationProviders(ContextInfo contextInfo) throws RemoteException;

    boolean getIndividualLocationProvider(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isGPSOn(ContextInfo contextInfo) throws RemoteException;

    boolean isGPSStateChangeAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isLocationProviderBlocked(String str) throws RemoteException;

    boolean isLocationProviderBlockedAsUser(String str, int i) throws RemoteException;

    boolean setGPSStateChangeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setIndividualLocationProvider(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    boolean startGPS(ContextInfo contextInfo, boolean z) throws RemoteException;

    public abstract class Stub extends Binder implements ILocationPolicy {
        public static final int TRANSACTION_getAllLocationProviders = 5;
        public static final int TRANSACTION_getIndividualLocationProvider = 2;
        public static final int TRANSACTION_isGPSOn = 9;
        public static final int TRANSACTION_isGPSStateChangeAllowed = 7;
        public static final int TRANSACTION_isLocationProviderBlocked = 3;
        public static final int TRANSACTION_isLocationProviderBlockedAsUser = 4;
        public static final int TRANSACTION_setGPSStateChangeAllowed = 6;
        public static final int TRANSACTION_setIndividualLocationProvider = 1;
        public static final int TRANSACTION_startGPS = 8;

        class Proxy implements ILocationPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public List<String> getAllLocationProviders(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean getIndividualLocationProvider(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ILocationPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean isGPSOn(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean isGPSStateChangeAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean isLocationProviderBlocked(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean isLocationProviderBlockedAsUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean setGPSStateChangeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean setIndividualLocationProvider(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean startGPS(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ILocationPolicy.DESCRIPTOR);
        }

        public static ILocationPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ILocationPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ILocationPolicy)) ? new Proxy(iBinder) : (ILocationPolicy) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setIndividualLocationProvider";
                case 2:
                    return "getIndividualLocationProvider";
                case 3:
                    return "isLocationProviderBlocked";
                case 4:
                    return "isLocationProviderBlockedAsUser";
                case 5:
                    return "getAllLocationProviders";
                case 6:
                    return "setGPSStateChangeAllowed";
                case 7:
                    return "isGPSStateChangeAllowed";
                case 8:
                    return "startGPS";
                case 9:
                    return "isGPSOn";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 8;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILocationPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILocationPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean individualLocationProvider = setIndividualLocationProvider(contextInfo, string, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(individualLocationProvider);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean individualLocationProvider2 = getIndividualLocationProvider(contextInfo2, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(individualLocationProvider2);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsLocationProviderBlocked = isLocationProviderBlocked(string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLocationProviderBlocked);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsLocationProviderBlockedAsUser = isLocationProviderBlockedAsUser(string4, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLocationProviderBlockedAsUser);
                    return true;
                case 5:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> allLocationProviders = getAllLocationProviders(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allLocationProviders);
                    return true;
                case 6:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean gPSStateChangeAllowed = setGPSStateChangeAllowed(contextInfo4, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(gPSStateChangeAllowed);
                    return true;
                case 7:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsGPSStateChangeAllowed = isGPSStateChangeAllowed(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGPSStateChangeAllowed);
                    return true;
                case 8:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zStartGPS = startGPS(contextInfo6, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartGPS);
                    return true;
                case 9:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsGPSOn = isGPSOn(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGPSOn);
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
