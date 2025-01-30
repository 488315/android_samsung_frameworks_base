package com.samsung.android.knox.p046zt.networktrust.filter;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IKnoxNetworkFilterService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IKnoxNetworkFilterService {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final List<String> getAllProfiles() {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final String getConfig(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final int getInstanceValidation() {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final int getKnoxNwFilterHttpProxyPort(int i, String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final String getPkgNameForTcpV4Port(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final String getPkgNameForTcpV6Port(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final int getProfileStatus(String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final String getRegisteredListeners(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final List<String> getRegisteredPackageList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final String getTcpV4PortInfo(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final String getTcpV6PortInfo(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final String getUdpV6PortInfo(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final int pause(String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final int registerApplication(ContextInfo contextInfo, String str, String str2, Bundle bundle) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final int registerListeners(String str, String str2) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final int setConfig(String str, String str2) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final int start(String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final int stop(String str, String str2) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
        public final int unregisterApplication(ContextInfo contextInfo, String str) {
            return 0;
        }
    }

    List<String> getAllProfiles();

    String getConfig(String str);

    int getInstanceValidation();

    int getKnoxNwFilterHttpProxyPort(int i, String str);

    String getPkgNameForTcpV4Port(int i);

    String getPkgNameForTcpV6Port(int i);

    int getProfileStatus(String str);

    String getRegisteredListeners(String str);

    List<String> getRegisteredPackageList(ContextInfo contextInfo);

    String getTcpV4PortInfo(int i);

    String getTcpV6PortInfo(int i);

    String getUdpV6PortInfo(int i);

    int pause(String str);

    int registerApplication(ContextInfo contextInfo, String str, String str2, Bundle bundle);

    int registerListeners(String str, String str2);

    int setConfig(String str, String str2);

    int start(String str);

    int stop(String str, String str2);

    int unregisterApplication(ContextInfo contextInfo, String str);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IKnoxNetworkFilterService {
        public static final int TRANSACTION_getAllProfiles = 7;
        public static final int TRANSACTION_getConfig = 6;
        public static final int TRANSACTION_getInstanceValidation = 4;
        public static final int TRANSACTION_getKnoxNwFilterHttpProxyPort = 19;
        public static final int TRANSACTION_getPkgNameForTcpV4Port = 17;
        public static final int TRANSACTION_getPkgNameForTcpV6Port = 18;
        public static final int TRANSACTION_getProfileStatus = 13;
        public static final int TRANSACTION_getRegisteredListeners = 9;
        public static final int TRANSACTION_getRegisteredPackageList = 3;
        public static final int TRANSACTION_getTcpV4PortInfo = 14;
        public static final int TRANSACTION_getTcpV6PortInfo = 15;
        public static final int TRANSACTION_getUdpV6PortInfo = 16;
        public static final int TRANSACTION_pause = 12;
        public static final int TRANSACTION_registerApplication = 1;
        public static final int TRANSACTION_registerListeners = 8;
        public static final int TRANSACTION_setConfig = 5;
        public static final int TRANSACTION_start = 10;
        public static final int TRANSACTION_stop = 11;
        public static final int TRANSACTION_unregisterApplication = 2;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IKnoxNetworkFilterService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final List<String> getAllProfiles() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final String getConfig(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final int getInstanceValidation() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            public final String getInterfaceDescriptor() {
                return IKnoxNetworkFilterService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final int getKnoxNwFilterHttpProxyPort(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final String getPkgNameForTcpV4Port(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final String getPkgNameForTcpV6Port(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final int getProfileStatus(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final String getRegisteredListeners(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final List<String> getRegisteredPackageList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final String getTcpV4PortInfo(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final String getTcpV6PortInfo(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final String getUdpV6PortInfo(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final int pause(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final int registerApplication(ContextInfo contextInfo, String str, String str2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final int registerListeners(String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final int setConfig(String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final int start(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final int stop(String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService
            public final int unregisterApplication(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
