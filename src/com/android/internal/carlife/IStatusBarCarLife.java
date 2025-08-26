package com.android.internal.carlife;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.view.AppearanceRegion;

/* loaded from: classes5.dex */
public interface IStatusBarCarLife extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.carlife.IStatusBarCarLife";

    public static class Default implements IStatusBarCarLife {
        @Override // com.android.internal.carlife.IStatusBarCarLife
        public void abortTransient(int i, int i2) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.carlife.IStatusBarCarLife
        public void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str) throws RemoteException {
        }

        @Override // com.android.internal.carlife.IStatusBarCarLife
        public void showTransient(int i, int i2, boolean z) throws RemoteException {
        }
    }

    void abortTransient(int i, int i2) throws RemoteException;

    void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str) throws RemoteException;

    void showTransient(int i, int i2, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IStatusBarCarLife {
        static final int TRANSACTION_abortTransient = 3;
        static final int TRANSACTION_onSystemBarAttributesChanged = 1;
        static final int TRANSACTION_showTransient = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IStatusBarCarLife.DESCRIPTOR);
        }

        public static IStatusBarCarLife asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IStatusBarCarLife.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IStatusBarCarLife)) {
                return (IStatusBarCarLife) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSystemBarAttributesChanged";
            }
            if (i == 2) {
                return "showTransient";
            }
            if (i != 3) {
                return null;
            }
            return "abortTransient";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IStatusBarCarLife.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IStatusBarCarLife.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                AppearanceRegion[] appearanceRegionArr = (AppearanceRegion[]) parcel.createTypedArray(AppearanceRegion.CREATOR);
                boolean z = parcel.readBoolean();
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onSystemBarAttributesChanged(i3, i4, appearanceRegionArr, z, i5, i6, string);
            } else if (i == 2) {
                int i7 = parcel.readInt();
                int i8 = parcel.readInt();
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                showTransient(i7, i8, z2);
            } else if (i == 3) {
                int i9 = parcel.readInt();
                int i10 = parcel.readInt();
                parcel.enforceNoDataAvail();
                abortTransient(i9, i10);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IStatusBarCarLife {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStatusBarCarLife.DESCRIPTOR;
            }

            @Override // com.android.internal.carlife.IStatusBarCarLife
            public void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IStatusBarCarLife.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedArray(appearanceRegionArr, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.carlife.IStatusBarCarLife
            public void showTransient(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IStatusBarCarLife.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.carlife.IStatusBarCarLife
            public void abortTransient(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IStatusBarCarLife.DESCRIPTOR);
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
