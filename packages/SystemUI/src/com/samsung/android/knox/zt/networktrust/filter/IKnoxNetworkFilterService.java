package com.samsung.android.knox.zt.networktrust.filter;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IKnoxNetworkFilterService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService";

    List<String> getAllProfiles() throws RemoteException;

    String getConfig(String str) throws RemoteException;

    String getConfigByUserId(int i) throws RemoteException;

    int getInstanceValidation() throws RemoteException;

    int getKnoxNwFilterHttpProxyPort(int i, String str) throws RemoteException;

    String getPkgNameForTcpV4Port(int i) throws RemoteException;

    String getPkgNameForTcpV6Port(int i) throws RemoteException;

    String getProfileForUser(int i) throws RemoteException;

    int getProfileStatus(String str) throws RemoteException;

    String getRegisteredListeners(String str) throws RemoteException;

    List<String> getRegisteredPackageList(ContextInfo contextInfo) throws RemoteException;

    String getTcpV4PortInfo(int i) throws RemoteException;

    String getTcpV6PortInfo(int i) throws RemoteException;

    String getUdpV6PortInfo(int i) throws RemoteException;

    boolean isAuthorized() throws RemoteException;

    boolean isUserLimitReached(boolean z) throws RemoteException;

    int pause(String str) throws RemoteException;

    int prepareFiltering(String str, Bundle bundle) throws RemoteException;

    int registerApplication(ContextInfo contextInfo, String str, String str2, Bundle bundle) throws RemoteException;

    int registerListeners(String str, String str2) throws RemoteException;

    void removeConfigByEnduser() throws RemoteException;

    int setConfig(String str, String str2) throws RemoteException;

    int start(String str) throws RemoteException;

    int stop(String str, String str2) throws RemoteException;

    int unregisterApplication(ContextInfo contextInfo, String str) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IKnoxNetworkFilterService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public List<String> getAllProfiles() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public String getConfig(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public String getConfigByUserId(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public int getInstanceValidation() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public int getKnoxNwFilterHttpProxyPort(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public String getPkgNameForTcpV4Port(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public String getPkgNameForTcpV6Port(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public String getProfileForUser(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public int getProfileStatus(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public String getRegisteredListeners(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public List<String> getRegisteredPackageList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public String getTcpV4PortInfo(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public String getTcpV6PortInfo(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public String getUdpV6PortInfo(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public boolean isAuthorized() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public boolean isUserLimitReached(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public int pause(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public int prepareFiltering(String str, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public int registerApplication(ContextInfo contextInfo, String str, String str2, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public int registerListeners(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public int setConfig(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public int start(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public int stop(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public int unregisterApplication(ContextInfo contextInfo, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
        public void removeConfigByEnduser() throws RemoteException {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IKnoxNetworkFilterService {
        public static final int TRANSACTION_getAllProfiles = 7;
        public static final int TRANSACTION_getConfig = 6;
        public static final int TRANSACTION_getConfigByUserId = 22;
        public static final int TRANSACTION_getInstanceValidation = 4;
        public static final int TRANSACTION_getKnoxNwFilterHttpProxyPort = 19;
        public static final int TRANSACTION_getPkgNameForTcpV4Port = 17;
        public static final int TRANSACTION_getPkgNameForTcpV6Port = 18;
        public static final int TRANSACTION_getProfileForUser = 23;
        public static final int TRANSACTION_getProfileStatus = 13;
        public static final int TRANSACTION_getRegisteredListeners = 9;
        public static final int TRANSACTION_getRegisteredPackageList = 3;
        public static final int TRANSACTION_getTcpV4PortInfo = 14;
        public static final int TRANSACTION_getTcpV6PortInfo = 15;
        public static final int TRANSACTION_getUdpV6PortInfo = 16;
        public static final int TRANSACTION_isAuthorized = 20;
        public static final int TRANSACTION_isUserLimitReached = 25;
        public static final int TRANSACTION_pause = 12;
        public static final int TRANSACTION_prepareFiltering = 21;
        public static final int TRANSACTION_registerApplication = 1;
        public static final int TRANSACTION_registerListeners = 8;
        public static final int TRANSACTION_removeConfigByEnduser = 24;
        public static final int TRANSACTION_setConfig = 5;
        public static final int TRANSACTION_start = 10;
        public static final int TRANSACTION_stop = 11;
        public static final int TRANSACTION_unregisterApplication = 2;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IKnoxNetworkFilterService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public List<String> getAllProfiles() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getConfig(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getConfigByUserId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int getInstanceValidation() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IKnoxNetworkFilterService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int getKnoxNwFilterHttpProxyPort(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getPkgNameForTcpV4Port(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getPkgNameForTcpV6Port(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getProfileForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int getProfileStatus(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getRegisteredListeners(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public List<String> getRegisteredPackageList(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getTcpV4PortInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getTcpV6PortInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getUdpV6PortInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public boolean isAuthorized() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public boolean isUserLimitReached(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int pause(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int prepareFiltering(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int registerApplication(ContextInfo contextInfo, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int registerListeners(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public void removeConfigByEnduser() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int setConfig(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int start(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int stop(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int unregisterApplication(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKnoxNetworkFilterService.DESCRIPTOR);
        }

        public static IKnoxNetworkFilterService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKnoxNetworkFilterService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IKnoxNetworkFilterService)) ? new Proxy(iBinder) : (IKnoxNetworkFilterService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxNetworkFilterService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxNetworkFilterService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int registerApplication = registerApplication(contextInfo, readString, readString2, bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(registerApplication);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int unregisterApplication = unregisterApplication(contextInfo2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterApplication);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> registeredPackageList = getRegisteredPackageList(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(registeredPackageList);
                    return true;
                case 4:
                    int instanceValidation = getInstanceValidation();
                    parcel2.writeNoException();
                    parcel2.writeInt(instanceValidation);
                    return true;
                case 5:
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int config = setConfig(readString4, readString5);
                    parcel2.writeNoException();
                    parcel2.writeInt(config);
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String config2 = getConfig(readString6);
                    parcel2.writeNoException();
                    parcel2.writeString(config2);
                    return true;
                case 7:
                    List<String> allProfiles = getAllProfiles();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allProfiles);
                    return true;
                case 8:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int registerListeners = registerListeners(readString7, readString8);
                    parcel2.writeNoException();
                    parcel2.writeInt(registerListeners);
                    return true;
                case 9:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String registeredListeners = getRegisteredListeners(readString9);
                    parcel2.writeNoException();
                    parcel2.writeString(registeredListeners);
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int start = start(readString10);
                    parcel2.writeNoException();
                    parcel2.writeInt(start);
                    return true;
                case 11:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int stop = stop(readString11, readString12);
                    parcel2.writeNoException();
                    parcel2.writeInt(stop);
                    return true;
                case 12:
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int pause = pause(readString13);
                    parcel2.writeNoException();
                    parcel2.writeInt(pause);
                    return true;
                case 13:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int profileStatus = getProfileStatus(readString14);
                    parcel2.writeNoException();
                    parcel2.writeInt(profileStatus);
                    return true;
                case 14:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String tcpV4PortInfo = getTcpV4PortInfo(readInt);
                    parcel2.writeNoException();
                    parcel2.writeString(tcpV4PortInfo);
                    return true;
                case 15:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String tcpV6PortInfo = getTcpV6PortInfo(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeString(tcpV6PortInfo);
                    return true;
                case 16:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String udpV6PortInfo = getUdpV6PortInfo(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeString(udpV6PortInfo);
                    return true;
                case 17:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String pkgNameForTcpV4Port = getPkgNameForTcpV4Port(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeString(pkgNameForTcpV4Port);
                    return true;
                case 18:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String pkgNameForTcpV6Port = getPkgNameForTcpV6Port(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeString(pkgNameForTcpV6Port);
                    return true;
                case 19:
                    int readInt6 = parcel.readInt();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int knoxNwFilterHttpProxyPort = getKnoxNwFilterHttpProxyPort(readInt6, readString15);
                    parcel2.writeNoException();
                    parcel2.writeInt(knoxNwFilterHttpProxyPort);
                    return true;
                case 20:
                    boolean isAuthorized = isAuthorized();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAuthorized);
                    return true;
                case 21:
                    String readString16 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int prepareFiltering = prepareFiltering(readString16, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeInt(prepareFiltering);
                    return true;
                case 22:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String configByUserId = getConfigByUserId(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeString(configByUserId);
                    return true;
                case 23:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String profileForUser = getProfileForUser(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeString(profileForUser);
                    return true;
                case 24:
                    removeConfigByEnduser();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isUserLimitReached = isUserLimitReached(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserLimitReached);
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
