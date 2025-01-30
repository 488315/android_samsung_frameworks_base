package com.samsung.android.knox.net.nap.serviceprovider;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface INetworkAnalyticsService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService";

    int onActivateProfile(String str, int i, String str2);

    void onDataAvailable(String str, List<String> list);

    int onDeactivateProfile(String str, int i);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements INetworkAnalyticsService {
        public static final int TRANSACTION_onActivateProfile = 1;
        public static final int TRANSACTION_onDataAvailable = 3;
        public static final int TRANSACTION_onDeactivateProfile = 2;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements INetworkAnalyticsService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return INetworkAnalyticsService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService
            public final int onActivateProfile(String str, int i, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkAnalyticsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService
            public final void onDataAvailable(String str, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkAnalyticsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService
            public final int onDeactivateProfile(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkAnalyticsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
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
            attachInterface(this, INetworkAnalyticsService.DESCRIPTOR);
        }

        public static INetworkAnalyticsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(INetworkAnalyticsService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof INetworkAnalyticsService)) ? new Proxy(iBinder) : (INetworkAnalyticsService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(INetworkAnalyticsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(INetworkAnalyticsService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                int onActivateProfile = onActivateProfile(readString, readInt, readString2);
                parcel2.writeNoException();
                parcel2.writeInt(onActivateProfile);
            } else if (i == 2) {
                String readString3 = parcel.readString();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int onDeactivateProfile = onDeactivateProfile(readString3, readInt2);
                parcel2.writeNoException();
                parcel2.writeInt(onDeactivateProfile);
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                String readString4 = parcel.readString();
                ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                parcel.enforceNoDataAvail();
                onDataAvailable(readString4, createStringArrayList);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements INetworkAnalyticsService {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService
        public final int onActivateProfile(String str, int i, String str2) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService
        public final int onDeactivateProfile(String str, int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService
        public final void onDataAvailable(String str, List<String> list) {
        }
    }
}
