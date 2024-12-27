package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;

import java.util.List;

/* loaded from: classes.dex */
public interface IShortcutChangeCallback extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IShortcutChangeCallback";

    void onShortcutsAddedOrUpdated(String str, List<ShortcutInfo> list, UserHandle userHandle)
            throws RemoteException;

    void onShortcutsRemoved(String str, List<ShortcutInfo> list, UserHandle userHandle)
            throws RemoteException;

    public static class Default implements IShortcutChangeCallback {
        @Override // android.content.pm.IShortcutChangeCallback
        public void onShortcutsAddedOrUpdated(
                String packageName, List<ShortcutInfo> shortcuts, UserHandle user)
                throws RemoteException {}

        @Override // android.content.pm.IShortcutChangeCallback
        public void onShortcutsRemoved(
                String packageName, List<ShortcutInfo> shortcuts, UserHandle user)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IShortcutChangeCallback {
        static final int TRANSACTION_onShortcutsAddedOrUpdated = 1;
        static final int TRANSACTION_onShortcutsRemoved = 2;

        public Stub() {
            attachInterface(this, IShortcutChangeCallback.DESCRIPTOR);
        }

        public static IShortcutChangeCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IShortcutChangeCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IShortcutChangeCallback)) {
                return (IShortcutChangeCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onShortcutsAddedOrUpdated";
                case 2:
                    return "onShortcutsRemoved";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IShortcutChangeCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IShortcutChangeCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    List<ShortcutInfo> _arg1 = data.createTypedArrayList(ShortcutInfo.CREATOR);
                    UserHandle _arg2 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    onShortcutsAddedOrUpdated(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    List<ShortcutInfo> _arg12 = data.createTypedArrayList(ShortcutInfo.CREATOR);
                    UserHandle _arg22 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    onShortcutsRemoved(_arg02, _arg12, _arg22);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IShortcutChangeCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IShortcutChangeCallback.DESCRIPTOR;
            }

            @Override // android.content.pm.IShortcutChangeCallback
            public void onShortcutsAddedOrUpdated(
                    String packageName, List<ShortcutInfo> shortcuts, UserHandle user)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IShortcutChangeCallback.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedList(shortcuts, 0);
                    _data.writeTypedObject(user, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutChangeCallback
            public void onShortcutsRemoved(
                    String packageName, List<ShortcutInfo> shortcuts, UserHandle user)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IShortcutChangeCallback.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedList(shortcuts, 0);
                    _data.writeTypedObject(user, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
