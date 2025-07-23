package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IShortcutChangeCallback extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IShortcutChangeCallback";

    public static class Default implements IShortcutChangeCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IShortcutChangeCallback
        public void onShortcutsAddedOrUpdated(String str, List<ShortcutInfo> list, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutChangeCallback
        public void onShortcutsRemoved(String str, List<ShortcutInfo> list, UserHandle userHandle) throws RemoteException {
        }
    }

    void onShortcutsAddedOrUpdated(String str, List<ShortcutInfo> list, UserHandle userHandle) throws RemoteException;

    void onShortcutsRemoved(String str, List<ShortcutInfo> list, UserHandle userHandle) throws RemoteException;

    public static abstract class Stub extends Binder implements IShortcutChangeCallback {
        static final int TRANSACTION_onShortcutsAddedOrUpdated = 1;
        static final int TRANSACTION_onShortcutsRemoved = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IShortcutChangeCallback.DESCRIPTOR);
        }

        public static IShortcutChangeCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IShortcutChangeCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IShortcutChangeCallback)) {
                return (IShortcutChangeCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onShortcutsAddedOrUpdated";
            }
            if (i != 2) {
                return null;
            }
            return "onShortcutsRemoved";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IShortcutChangeCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IShortcutChangeCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                ArrayList createTypedArrayList = parcel.createTypedArrayList(ShortcutInfo.CREATOR);
                UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                parcel.enforceNoDataAvail();
                onShortcutsAddedOrUpdated(readString, createTypedArrayList, userHandle);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                ArrayList createTypedArrayList2 = parcel.createTypedArrayList(ShortcutInfo.CREATOR);
                UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                parcel.enforceNoDataAvail();
                onShortcutsRemoved(readString2, createTypedArrayList2, userHandle2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IShortcutChangeCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IShortcutChangeCallback.DESCRIPTOR;
            }

            @Override // android.content.pm.IShortcutChangeCallback
            public void onShortcutsAddedOrUpdated(String str, List<ShortcutInfo> list, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IShortcutChangeCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutChangeCallback
            public void onShortcutsRemoved(String str, List<ShortcutInfo> list, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IShortcutChangeCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
