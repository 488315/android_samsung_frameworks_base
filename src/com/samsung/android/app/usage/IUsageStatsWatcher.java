package com.samsung.android.app.usage;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IUsageStatsWatcher extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.app.usage.IUsageStatsWatcher";

    public static class Default implements IUsageStatsWatcher {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.app.usage.IUsageStatsWatcher
        public void notePauseComponent(ComponentName componentName, Intent intent, int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.app.usage.IUsageStatsWatcher
        public void noteResumeComponent(ComponentName componentName, Intent intent, int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.app.usage.IUsageStatsWatcher
        public void noteStopComponent(ComponentName componentName, Intent intent, int i, int i2) throws RemoteException {
        }
    }

    void notePauseComponent(ComponentName componentName, Intent intent, int i, int i2) throws RemoteException;

    void noteResumeComponent(ComponentName componentName, Intent intent, int i, int i2) throws RemoteException;

    void noteStopComponent(ComponentName componentName, Intent intent, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IUsageStatsWatcher {
        static final int TRANSACTION_notePauseComponent = 2;
        static final int TRANSACTION_noteResumeComponent = 1;
        static final int TRANSACTION_noteStopComponent = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IUsageStatsWatcher.DESCRIPTOR);
        }

        public static IUsageStatsWatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUsageStatsWatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUsageStatsWatcher)) {
                return (IUsageStatsWatcher) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "noteResumeComponent";
            }
            if (i == 2) {
                return "notePauseComponent";
            }
            if (i != 3) {
                return null;
            }
            return "noteStopComponent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUsageStatsWatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUsageStatsWatcher.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                noteResumeComponent(componentName, intent, readInt, readInt2);
            } else if (i == 2) {
                ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                notePauseComponent(componentName2, intent2, readInt3, readInt4);
            } else if (i == 3) {
                ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                int readInt5 = parcel.readInt();
                int readInt6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                noteStopComponent(componentName3, intent3, readInt5, readInt6);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IUsageStatsWatcher {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUsageStatsWatcher.DESCRIPTOR;
            }

            @Override // com.samsung.android.app.usage.IUsageStatsWatcher
            public void noteResumeComponent(ComponentName componentName, Intent intent, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUsageStatsWatcher.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.usage.IUsageStatsWatcher
            public void notePauseComponent(ComponentName componentName, Intent intent, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUsageStatsWatcher.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.usage.IUsageStatsWatcher
            public void noteStopComponent(ComponentName componentName, Intent intent, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUsageStatsWatcher.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
