package com.samsung.android.knox.net.nap;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface INetworkAnalytics extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.nap.INetworkAnalytics";

    public class Default implements INetworkAnalytics {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
        public String getNPAVersion() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
        public List<String> getNetworkMonitorProfiles(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
        public List<Profile> getProfiles(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
        public int handleNAPClientCall(String str, Bundle bundle, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
        public int isProfileActivatedForUser(ContextInfo contextInfo, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
        public int registerNetworkMonitorProfile(ContextInfo contextInfo, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
        public int unregisterNetworkMonitorProfile(ContextInfo contextInfo, String str) throws RemoteException {
            return 0;
        }
    }

    String getNPAVersion() throws RemoteException;

    List<String> getNetworkMonitorProfiles(ContextInfo contextInfo) throws RemoteException;

    List<Profile> getProfiles(ContextInfo contextInfo) throws RemoteException;

    int handleNAPClientCall(String str, Bundle bundle, boolean z) throws RemoteException;

    int isProfileActivatedForUser(ContextInfo contextInfo, String str) throws RemoteException;

    int registerNetworkMonitorProfile(ContextInfo contextInfo, String str) throws RemoteException;

    int unregisterNetworkMonitorProfile(ContextInfo contextInfo, String str) throws RemoteException;

    public abstract class Stub extends Binder implements INetworkAnalytics {
        public static final int TRANSACTION_getNPAVersion = 7;
        public static final int TRANSACTION_getNetworkMonitorProfiles = 4;
        public static final int TRANSACTION_getProfiles = 3;
        public static final int TRANSACTION_handleNAPClientCall = 2;
        public static final int TRANSACTION_isProfileActivatedForUser = 6;
        public static final int TRANSACTION_registerNetworkMonitorProfile = 1;
        public static final int TRANSACTION_unregisterNetworkMonitorProfile = 5;

        class Proxy implements INetworkAnalytics {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INetworkAnalytics.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
            public String getNPAVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkAnalytics.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
            public List<String> getNetworkMonitorProfiles(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkAnalytics.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
            public List<Profile> getProfiles(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkAnalytics.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(Profile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
            public int handleNAPClientCall(String str, Bundle bundle, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkAnalytics.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
            public int isProfileActivatedForUser(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkAnalytics.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
            public int registerNetworkMonitorProfile(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkAnalytics.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
            public int unregisterNetworkMonitorProfile(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INetworkAnalytics.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, INetworkAnalytics.DESCRIPTOR);
        }

        public static INetworkAnalytics asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(INetworkAnalytics.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof INetworkAnalytics)) ? new Proxy(iBinder) : (INetworkAnalytics) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(INetworkAnalytics.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(INetworkAnalytics.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRegisterNetworkMonitorProfile = registerNetworkMonitorProfile(contextInfo, string);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterNetworkMonitorProfile);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iHandleNAPClientCall = handleNAPClientCall(string2, bundle, z);
                    parcel2.writeNoException();
                    parcel2.writeInt(iHandleNAPClientCall);
                    return true;
                case 3:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<Profile> profiles = getProfiles(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(profiles, 1);
                    return true;
                case 4:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> networkMonitorProfiles = getNetworkMonitorProfiles(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(networkMonitorProfiles);
                    return true;
                case 5:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iUnregisterNetworkMonitorProfile = unregisterNetworkMonitorProfile(contextInfo4, string3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnregisterNetworkMonitorProfile);
                    return true;
                case 6:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iIsProfileActivatedForUser = isProfileActivatedForUser(contextInfo5, string4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsProfileActivatedForUser);
                    return true;
                case 7:
                    String nPAVersion = getNPAVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(nPAVersion);
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
