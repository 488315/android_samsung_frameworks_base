package android.webkit;

import android.content.pm.PackageInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IWebViewUpdateService extends IInterface {

    public static class Default implements IWebViewUpdateService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.webkit.IWebViewUpdateService
        public String changeProviderAndSetting(String str) throws RemoteException {
            return null;
        }

        @Override // android.webkit.IWebViewUpdateService
        public WebViewProviderInfo[] getAllWebViewPackages() throws RemoteException {
            return null;
        }

        @Override // android.webkit.IWebViewUpdateService
        public PackageInfo getCurrentWebViewPackage() throws RemoteException {
            return null;
        }

        @Override // android.webkit.IWebViewUpdateService
        public String getCurrentWebViewPackageName() throws RemoteException {
            return null;
        }

        @Override // android.webkit.IWebViewUpdateService
        public WebViewProviderInfo getDefaultWebViewPackage() throws RemoteException {
            return null;
        }

        @Override // android.webkit.IWebViewUpdateService
        public WebViewProviderInfo[] getValidWebViewPackages() throws RemoteException {
            return null;
        }

        @Override // android.webkit.IWebViewUpdateService
        public void notifyRelroCreationCompleted() throws RemoteException {
        }

        @Override // android.webkit.IWebViewUpdateService
        public WebViewProviderResponse waitForAndGetProvider() throws RemoteException {
            return null;
        }
    }

    String changeProviderAndSetting(String str) throws RemoteException;

    WebViewProviderInfo[] getAllWebViewPackages() throws RemoteException;

    PackageInfo getCurrentWebViewPackage() throws RemoteException;

    String getCurrentWebViewPackageName() throws RemoteException;

    WebViewProviderInfo getDefaultWebViewPackage() throws RemoteException;

    WebViewProviderInfo[] getValidWebViewPackages() throws RemoteException;

    void notifyRelroCreationCompleted() throws RemoteException;

    WebViewProviderResponse waitForAndGetProvider() throws RemoteException;

    public static abstract class Stub extends Binder implements IWebViewUpdateService {
        public static final String DESCRIPTOR = "android.webkit.IWebViewUpdateService";
        static final int TRANSACTION_changeProviderAndSetting = 3;
        static final int TRANSACTION_getAllWebViewPackages = 5;
        static final int TRANSACTION_getCurrentWebViewPackage = 7;
        static final int TRANSACTION_getCurrentWebViewPackageName = 6;
        static final int TRANSACTION_getDefaultWebViewPackage = 8;
        static final int TRANSACTION_getValidWebViewPackages = 4;
        static final int TRANSACTION_notifyRelroCreationCompleted = 1;
        static final int TRANSACTION_waitForAndGetProvider = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWebViewUpdateService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWebViewUpdateService)) {
                return (IWebViewUpdateService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "notifyRelroCreationCompleted";
                case 2:
                    return "waitForAndGetProvider";
                case 3:
                    return "changeProviderAndSetting";
                case 4:
                    return "getValidWebViewPackages";
                case 5:
                    return "getAllWebViewPackages";
                case 6:
                    return "getCurrentWebViewPackageName";
                case 7:
                    return "getCurrentWebViewPackage";
                case 8:
                    return "getDefaultWebViewPackage";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    notifyRelroCreationCompleted();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    WebViewProviderResponse waitForAndGetProvider = waitForAndGetProvider();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(waitForAndGetProvider, 1);
                    return true;
                case 3:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String changeProviderAndSetting = changeProviderAndSetting(readString);
                    parcel2.writeNoException();
                    parcel2.writeString(changeProviderAndSetting);
                    return true;
                case 4:
                    WebViewProviderInfo[] validWebViewPackages = getValidWebViewPackages();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(validWebViewPackages, 1);
                    return true;
                case 5:
                    WebViewProviderInfo[] allWebViewPackages = getAllWebViewPackages();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allWebViewPackages, 1);
                    return true;
                case 6:
                    String currentWebViewPackageName = getCurrentWebViewPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(currentWebViewPackageName);
                    return true;
                case 7:
                    PackageInfo currentWebViewPackage = getCurrentWebViewPackage();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentWebViewPackage, 1);
                    return true;
                case 8:
                    WebViewProviderInfo defaultWebViewPackage = getDefaultWebViewPackage();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultWebViewPackage, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IWebViewUpdateService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.webkit.IWebViewUpdateService
            public void notifyRelroCreationCompleted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public WebViewProviderResponse waitForAndGetProvider() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WebViewProviderResponse) obtain2.readTypedObject(WebViewProviderResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public String changeProviderAndSetting(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public WebViewProviderInfo[] getValidWebViewPackages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WebViewProviderInfo[]) obtain2.createTypedArray(WebViewProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public WebViewProviderInfo[] getAllWebViewPackages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WebViewProviderInfo[]) obtain2.createTypedArray(WebViewProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public String getCurrentWebViewPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public PackageInfo getCurrentWebViewPackage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PackageInfo) obtain2.readTypedObject(PackageInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.webkit.IWebViewUpdateService
            public WebViewProviderInfo getDefaultWebViewPackage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WebViewProviderInfo) obtain2.readTypedObject(WebViewProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
