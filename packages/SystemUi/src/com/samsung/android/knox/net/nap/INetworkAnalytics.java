package com.samsung.android.knox.net.nap;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface INetworkAnalytics extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.nap.INetworkAnalytics";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements INetworkAnalytics {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
        public final String getNPAVersion() {
            return null;
        }

        @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
        public final List<String> getNetworkMonitorProfiles(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
        public final List<Profile> getProfiles(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
        public final int handleNAPClientCall(String str, Bundle bundle, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
        public final int isProfileActivatedForUser(ContextInfo contextInfo, String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
        public final int registerNetworkMonitorProfile(ContextInfo contextInfo, String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
        public final int unregisterNetworkMonitorProfile(ContextInfo contextInfo, String str) {
            return 0;
        }
    }

    String getNPAVersion();

    List<String> getNetworkMonitorProfiles(ContextInfo contextInfo);

    List<Profile> getProfiles(ContextInfo contextInfo);

    int handleNAPClientCall(String str, Bundle bundle, boolean z);

    int isProfileActivatedForUser(ContextInfo contextInfo, String str);

    int registerNetworkMonitorProfile(ContextInfo contextInfo, String str);

    int unregisterNetworkMonitorProfile(ContextInfo contextInfo, String str);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements INetworkAnalytics {
        public static final int TRANSACTION_getNPAVersion = 7;
        public static final int TRANSACTION_getNetworkMonitorProfiles = 4;
        public static final int TRANSACTION_getProfiles = 3;
        public static final int TRANSACTION_handleNAPClientCall = 2;
        public static final int TRANSACTION_isProfileActivatedForUser = 6;
        public static final int TRANSACTION_registerNetworkMonitorProfile = 1;
        public static final int TRANSACTION_unregisterNetworkMonitorProfile = 5;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements INetworkAnalytics {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return INetworkAnalytics.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
            public final String getNPAVersion() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkAnalytics.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
            public final List<String> getNetworkMonitorProfiles(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkAnalytics.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
            public final List<Profile> getProfiles(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkAnalytics.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Profile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
            public final int handleNAPClientCall(String str, Bundle bundle, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkAnalytics.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
            public final int isProfileActivatedForUser(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkAnalytics.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
            public final int registerNetworkMonitorProfile(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkAnalytics.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.INetworkAnalytics
            public final int unregisterNetworkMonitorProfile(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkAnalytics.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(INetworkAnalytics.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof INetworkAnalytics)) ? new Proxy(iBinder) : (INetworkAnalytics) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int registerNetworkMonitorProfile = registerNetworkMonitorProfile(contextInfo, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(registerNetworkMonitorProfile);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int handleNAPClientCall = handleNAPClientCall(readString2, bundle, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(handleNAPClientCall);
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
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int unregisterNetworkMonitorProfile = unregisterNetworkMonitorProfile(contextInfo4, readString3);
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterNetworkMonitorProfile);
                    return true;
                case 6:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int isProfileActivatedForUser = isProfileActivatedForUser(contextInfo5, readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(isProfileActivatedForUser);
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
        public final IBinder asBinder() {
            return this;
        }
    }
}
