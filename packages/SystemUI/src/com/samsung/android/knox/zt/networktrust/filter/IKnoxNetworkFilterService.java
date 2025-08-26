package com.samsung.android.knox.zt.networktrust.filter;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getConfig(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getConfigByUserId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int getInstanceValidation() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IKnoxNetworkFilterService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int getKnoxNwFilterHttpProxyPort(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getPkgNameForTcpV4Port(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getPkgNameForTcpV6Port(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getProfileForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int getProfileStatus(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getRegisteredListeners(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public List<String> getRegisteredPackageList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getTcpV4PortInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getTcpV6PortInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public String getUdpV6PortInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public boolean isAuthorized() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public boolean isUserLimitReached(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int pause(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int prepareFiltering(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int registerApplication(ContextInfo contextInfo, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int registerListeners(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public void removeConfigByEnduser() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int setConfig(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int start(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int stop(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService
            public int unregisterApplication(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxNetworkFilterService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKnoxNetworkFilterService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IKnoxNetworkFilterService)) ? new Proxy(iBinder) : (IKnoxNetworkFilterService) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iRegisterApplication = registerApplication(contextInfo, string, string2, bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterApplication);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iUnregisterApplication = unregisterApplication(contextInfo2, string3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnregisterApplication);
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
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int config = setConfig(string4, string5);
                    parcel2.writeNoException();
                    parcel2.writeInt(config);
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String config2 = getConfig(string6);
                    parcel2.writeNoException();
                    parcel2.writeString(config2);
                    return true;
                case 7:
                    List<String> allProfiles = getAllProfiles();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allProfiles);
                    return true;
                case 8:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRegisterListeners = registerListeners(string7, string8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterListeners);
                    return true;
                case 9:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String registeredListeners = getRegisteredListeners(string9);
                    parcel2.writeNoException();
                    parcel2.writeString(registeredListeners);
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iStart = start(string10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStart);
                    return true;
                case 11:
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iStop = stop(string11, string12);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStop);
                    return true;
                case 12:
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iPause = pause(string13);
                    parcel2.writeNoException();
                    parcel2.writeInt(iPause);
                    return true;
                case 13:
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int profileStatus = getProfileStatus(string14);
                    parcel2.writeNoException();
                    parcel2.writeInt(profileStatus);
                    return true;
                case 14:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String tcpV4PortInfo = getTcpV4PortInfo(i3);
                    parcel2.writeNoException();
                    parcel2.writeString(tcpV4PortInfo);
                    return true;
                case 15:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String tcpV6PortInfo = getTcpV6PortInfo(i4);
                    parcel2.writeNoException();
                    parcel2.writeString(tcpV6PortInfo);
                    return true;
                case 16:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String udpV6PortInfo = getUdpV6PortInfo(i5);
                    parcel2.writeNoException();
                    parcel2.writeString(udpV6PortInfo);
                    return true;
                case 17:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String pkgNameForTcpV4Port = getPkgNameForTcpV4Port(i6);
                    parcel2.writeNoException();
                    parcel2.writeString(pkgNameForTcpV4Port);
                    return true;
                case 18:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String pkgNameForTcpV6Port = getPkgNameForTcpV6Port(i7);
                    parcel2.writeNoException();
                    parcel2.writeString(pkgNameForTcpV6Port);
                    return true;
                case 19:
                    int i8 = parcel.readInt();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int knoxNwFilterHttpProxyPort = getKnoxNwFilterHttpProxyPort(i8, string15);
                    parcel2.writeNoException();
                    parcel2.writeInt(knoxNwFilterHttpProxyPort);
                    return true;
                case 20:
                    boolean zIsAuthorized = isAuthorized();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAuthorized);
                    return true;
                case 21:
                    String string16 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iPrepareFiltering = prepareFiltering(string16, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iPrepareFiltering);
                    return true;
                case 22:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String configByUserId = getConfigByUserId(i9);
                    parcel2.writeNoException();
                    parcel2.writeString(configByUserId);
                    return true;
                case 23:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String profileForUser = getProfileForUser(i10);
                    parcel2.writeNoException();
                    parcel2.writeString(profileForUser);
                    return true;
                case 24:
                    removeConfigByEnduser();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserLimitReached = isUserLimitReached(z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserLimitReached);
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
