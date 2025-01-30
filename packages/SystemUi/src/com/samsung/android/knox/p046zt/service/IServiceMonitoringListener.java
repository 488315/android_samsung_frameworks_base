package com.samsung.android.knox.p046zt.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IServiceMonitoringListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.service.IServiceMonitoringListener";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class _Parcel {
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
            }
        }
    }

    int checkUrlReputation(String str);

    void onEvent(int i, Bundle bundle);

    void onEventGeneralized(int i, String str);

    void onEventSimplified(int i, String str);

    void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str, String str2);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IServiceMonitoringListener {
        public static final int TRANSACTION_checkUrlReputation = 1;
        public static final int TRANSACTION_onEvent = 5;
        public static final int TRANSACTION_onEventGeneralized = 4;
        public static final int TRANSACTION_onEventSimplified = 3;
        public static final int TRANSACTION_onUnauthorizedAccessDetected = 2;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IServiceMonitoringListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.p046zt.service.IServiceMonitoringListener
            public final int checkUrlReputation(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceMonitoringListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IServiceMonitoringListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.p046zt.service.IServiceMonitoringListener
            public final void onEvent(int i, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceMonitoringListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    _Parcel.writeTypedObject(obtain, bundle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IServiceMonitoringListener
            public final void onEventGeneralized(int i, String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceMonitoringListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IServiceMonitoringListener
            public final void onEventSimplified(int i, String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceMonitoringListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IServiceMonitoringListener
            public final void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str, String str2) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceMonitoringListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IServiceMonitoringListener.DESCRIPTOR);
        }

        public static IServiceMonitoringListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IServiceMonitoringListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IServiceMonitoringListener)) ? new Proxy(iBinder) : (IServiceMonitoringListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IServiceMonitoringListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IServiceMonitoringListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int checkUrlReputation = checkUrlReputation(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(checkUrlReputation);
            } else if (i == 2) {
                onUnauthorizedAccessDetected(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readLong(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
            } else if (i == 3) {
                onEventSimplified(parcel.readInt(), parcel.readString());
                parcel2.writeNoException();
            } else if (i == 4) {
                onEventGeneralized(parcel.readInt(), parcel.readString());
                parcel2.writeNoException();
            } else {
                if (i != 5) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onEvent(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
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
    public static class Default implements IServiceMonitoringListener {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.service.IServiceMonitoringListener
        public final int checkUrlReputation(String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.p046zt.service.IServiceMonitoringListener
        public final void onEvent(int i, Bundle bundle) {
        }

        @Override // com.samsung.android.knox.p046zt.service.IServiceMonitoringListener
        public final void onEventGeneralized(int i, String str) {
        }

        @Override // com.samsung.android.knox.p046zt.service.IServiceMonitoringListener
        public final void onEventSimplified(int i, String str) {
        }

        @Override // com.samsung.android.knox.p046zt.service.IServiceMonitoringListener
        public final void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str, String str2) {
        }
    }
}
