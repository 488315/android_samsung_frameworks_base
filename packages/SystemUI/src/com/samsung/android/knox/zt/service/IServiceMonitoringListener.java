package com.samsung.android.knox.zt.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IServiceMonitoringListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.service.IServiceMonitoringListener";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class _Parcel {
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

    int checkUrlReputation(String str) throws RemoteException;

    void onEvent(int i, Bundle bundle) throws RemoteException;

    void onEventGeneralized(int i, String str) throws RemoteException;

    void onEventSimplified(int i, String str) throws RemoteException;

    int onSignal(String str) throws RemoteException;

    void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str, String str2) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IServiceMonitoringListener {
        public static final int TRANSACTION_checkUrlReputation = 1;
        public static final int TRANSACTION_onEvent = 5;
        public static final int TRANSACTION_onEventGeneralized = 4;
        public static final int TRANSACTION_onEventSimplified = 3;
        public static final int TRANSACTION_onSignal = 6;
        public static final int TRANSACTION_onUnauthorizedAccessDetected = 2;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IServiceMonitoringListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public int checkUrlReputation(String str) throws RemoteException {
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

            public String getInterfaceDescriptor() {
                return IServiceMonitoringListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onEvent(int i, Bundle bundle) throws RemoteException {
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

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onEventGeneralized(int i, String str) throws RemoteException {
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

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onEventSimplified(int i, String str) throws RemoteException {
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

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public int onSignal(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceMonitoringListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str, String str2) throws RemoteException {
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
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IServiceMonitoringListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IServiceMonitoringListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int checkUrlReputation = checkUrlReputation(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(checkUrlReputation);
                    return true;
                case 2:
                    onUnauthorizedAccessDetected(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readLong(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 3:
                    onEventSimplified(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    onEventGeneralized(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    onEvent(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int onSignal = onSignal(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(onSignal);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IServiceMonitoringListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
        public int checkUrlReputation(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
        public int onSignal(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
        public void onEvent(int i, Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
        public void onEventGeneralized(int i, String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
        public void onEventSimplified(int i, String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
        public void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str, String str2) throws RemoteException {
        }
    }
}
