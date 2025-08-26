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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IUsageStatsWatcher.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IUsageStatsWatcher)) {
                return (IUsageStatsWatcher) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                noteResumeComponent(componentName, intent, i3, i4);
            } else if (i == 2) {
                ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                notePauseComponent(componentName2, intent2, i5, i6);
            } else if (i == 3) {
                ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                int i7 = parcel.readInt();
                int i8 = parcel.readInt();
                parcel.enforceNoDataAvail();
                noteStopComponent(componentName3, intent3, i7, i8);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUsageStatsWatcher.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.app.usage.IUsageStatsWatcher
            public void notePauseComponent(ComponentName componentName, Intent intent, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUsageStatsWatcher.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.app.usage.IUsageStatsWatcher
            public void noteStopComponent(ComponentName componentName, Intent intent, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUsageStatsWatcher.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
