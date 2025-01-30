package com.android.internal.carlife;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import com.android.internal.view.AppearanceRegion;

/* loaded from: classes4.dex */
public interface IStatusBarCarLife extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.carlife.IStatusBarCarLife";

    void abortTransient(int i, int i2) throws RemoteException;

    void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str) throws RemoteException;

    void showTransient(int i, int i2, boolean z) throws RemoteException;

    public static class Default implements IStatusBarCarLife {
        @Override // com.android.internal.carlife.IStatusBarCarLife
        public void onSystemBarAttributesChanged(int displayId, int appearance, AppearanceRegion[] appearanceRegions, boolean navbarColorManagedByIme, int behavior, int requestedVisibleTypes, String packageName) throws RemoteException {
        }

        @Override // com.android.internal.carlife.IStatusBarCarLife
        public void showTransient(int displayId, int types, boolean isGestureOnSystemBar) throws RemoteException {
        }

        @Override // com.android.internal.carlife.IStatusBarCarLife
        public void abortTransient(int displayId, int types) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IStatusBarCarLife {
        static final int TRANSACTION_abortTransient = 3;
        static final int TRANSACTION_onSystemBarAttributesChanged = 1;
        static final int TRANSACTION_showTransient = 2;

        public Stub() {
            attachInterface(this, IStatusBarCarLife.DESCRIPTOR);
        }

        public static IStatusBarCarLife asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IStatusBarCarLife.DESCRIPTOR);
            if (iin != null && (iin instanceof IStatusBarCarLife)) {
                return (IStatusBarCarLife) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onSystemBarAttributesChanged";
                case 2:
                    return "showTransient";
                case 3:
                    return "abortTransient";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IStatusBarCarLife.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IStatusBarCarLife.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            AppearanceRegion[] _arg2 = (AppearanceRegion[]) data.createTypedArray(AppearanceRegion.CREATOR);
                            boolean _arg3 = data.readBoolean();
                            int _arg4 = data.readInt();
                            int _arg5 = data.readInt();
                            String _arg6 = data.readString();
                            data.enforceNoDataAvail();
                            onSystemBarAttributesChanged(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            int _arg12 = data.readInt();
                            boolean _arg22 = data.readBoolean();
                            data.enforceNoDataAvail();
                            showTransient(_arg02, _arg12, _arg22);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            int _arg13 = data.readInt();
                            data.enforceNoDataAvail();
                            abortTransient(_arg03, _arg13);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IStatusBarCarLife {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStatusBarCarLife.DESCRIPTOR;
            }

            @Override // com.android.internal.carlife.IStatusBarCarLife
            public void onSystemBarAttributesChanged(int displayId, int appearance, AppearanceRegion[] appearanceRegions, boolean navbarColorManagedByIme, int behavior, int requestedVisibleTypes, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStatusBarCarLife.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(appearance);
                    _data.writeTypedArray(appearanceRegions, 0);
                    _data.writeBoolean(navbarColorManagedByIme);
                    _data.writeInt(behavior);
                    _data.writeInt(requestedVisibleTypes);
                    _data.writeString(packageName);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.carlife.IStatusBarCarLife
            public void showTransient(int displayId, int types, boolean isGestureOnSystemBar) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStatusBarCarLife.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(types);
                    _data.writeBoolean(isGestureOnSystemBar);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.carlife.IStatusBarCarLife
            public void abortTransient(int displayId, int types) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStatusBarCarLife.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(types);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
