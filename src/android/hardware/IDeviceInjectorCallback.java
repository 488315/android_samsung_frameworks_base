package android.hardware;

import android.hardware.IDeviceInjectorSession;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDeviceInjectorCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.IDeviceInjectorCallback";
    public static final int ERROR_INJECTION_INVALID_ERROR = -1;
    public static final int ERROR_INJECTION_SERVICE = 1;
    public static final int ERROR_INJECTION_SESSION = 0;
    public static final int ERROR_INJECTION_UNSUPPORTED = 2;

    public static class Default implements IDeviceInjectorCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onError(int i) throws RemoteException {
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionPendingStarted(String str, String str2) throws RemoteException {
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionPendingStopped(String str, String str2) throws RemoteException {
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionStarted(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionStopped(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onSessionCreated(IDeviceInjectorSession iDeviceInjectorSession) throws RemoteException {
        }
    }

    void onError(int i) throws RemoteException;

    void onInjectionPendingStarted(String str, String str2) throws RemoteException;

    void onInjectionPendingStopped(String str, String str2) throws RemoteException;

    void onInjectionStarted(String str, String str2, String str3) throws RemoteException;

    void onInjectionStopped(String str, String str2, String str3) throws RemoteException;

    void onSessionCreated(IDeviceInjectorSession iDeviceInjectorSession) throws RemoteException;

    public static abstract class Stub extends Binder implements IDeviceInjectorCallback {
        static final int TRANSACTION_onError = 6;
        static final int TRANSACTION_onInjectionPendingStarted = 4;
        static final int TRANSACTION_onInjectionPendingStopped = 5;
        static final int TRANSACTION_onInjectionStarted = 2;
        static final int TRANSACTION_onInjectionStopped = 3;
        static final int TRANSACTION_onSessionCreated = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IDeviceInjectorCallback.DESCRIPTOR);
        }

        public static IDeviceInjectorCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDeviceInjectorCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDeviceInjectorCallback)) {
                return (IDeviceInjectorCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSessionCreated";
                case 2:
                    return "onInjectionStarted";
                case 3:
                    return "onInjectionStopped";
                case 4:
                    return "onInjectionPendingStarted";
                case 5:
                    return "onInjectionPendingStopped";
                case 6:
                    return "onError";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDeviceInjectorCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDeviceInjectorCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IDeviceInjectorSession iDeviceInjectorSessionAsInterface = IDeviceInjectorSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSessionCreated(iDeviceInjectorSessionAsInterface);
                    return true;
                case 2:
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onInjectionStarted(string, string2, string3);
                    return true;
                case 3:
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onInjectionStopped(string4, string5, string6);
                    return true;
                case 4:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onInjectionPendingStarted(string7, string8);
                    return true;
                case 5:
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onInjectionPendingStopped(string9, string10);
                    return true;
                case 6:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(i3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDeviceInjectorCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDeviceInjectorCallback.DESCRIPTOR;
            }

            @Override // android.hardware.IDeviceInjectorCallback
            public void onSessionCreated(IDeviceInjectorSession iDeviceInjectorSession) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInjectorCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDeviceInjectorSession);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IDeviceInjectorCallback
            public void onInjectionStarted(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInjectorCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IDeviceInjectorCallback
            public void onInjectionStopped(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInjectorCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IDeviceInjectorCallback
            public void onInjectionPendingStarted(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInjectorCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IDeviceInjectorCallback
            public void onInjectionPendingStopped(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInjectorCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IDeviceInjectorCallback
            public void onError(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInjectorCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
