package android.net.wifi.nl80211;

import android.net.wifi.nl80211.IApInterface;
import android.net.wifi.nl80211.IClientInterface;
import android.net.wifi.nl80211.IInterfaceEventCallback;
import android.net.wifi.nl80211.IWificondEventCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes3.dex */
public interface IWificond extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.nl80211.IWificond";

    public static class Default implements IWificond {
        @Override // android.net.wifi.nl80211.IWificond
        public List<IBinder> GetApInterfaces() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public List<IBinder> GetClientInterfaces() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public void RegisterCallback(IInterfaceEventCallback iInterfaceEventCallback) throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWificond
        public void UnregisterCallback(IInterfaceEventCallback iInterfaceEventCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public IApInterface createApInterface(String str) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public IClientInterface createClientInterface(String str) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public int[] getAvailable2gChannels() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public int[] getAvailable5gNonDFSChannels() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public int[] getAvailable60gChannels() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public int[] getAvailable6gChannels() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public int[] getAvailableDFSChannels() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public DeviceWiphyCapabilities getDeviceWiphyCapabilities(String str) throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public void notifyCountryCodeChanged() throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWificond
        public void registerWificondEventCallback(IWificondEventCallback iWificondEventCallback) throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWificond
        public boolean tearDownApInterface(String str) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public boolean tearDownClientInterface(String str) throws RemoteException {
            return false;
        }

        @Override // android.net.wifi.nl80211.IWificond
        public void tearDownInterfaces() throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IWificond
        public void unregisterWificondEventCallback(IWificondEventCallback iWificondEventCallback) throws RemoteException {
        }
    }

    List<IBinder> GetApInterfaces() throws RemoteException;

    List<IBinder> GetClientInterfaces() throws RemoteException;

    void RegisterCallback(IInterfaceEventCallback iInterfaceEventCallback) throws RemoteException;

    void UnregisterCallback(IInterfaceEventCallback iInterfaceEventCallback) throws RemoteException;

    IApInterface createApInterface(String str) throws RemoteException;

    IClientInterface createClientInterface(String str) throws RemoteException;

    int[] getAvailable2gChannels() throws RemoteException;

    int[] getAvailable5gNonDFSChannels() throws RemoteException;

    int[] getAvailable60gChannels() throws RemoteException;

    int[] getAvailable6gChannels() throws RemoteException;

    int[] getAvailableDFSChannels() throws RemoteException;

    DeviceWiphyCapabilities getDeviceWiphyCapabilities(String str) throws RemoteException;

    void notifyCountryCodeChanged() throws RemoteException;

    void registerWificondEventCallback(IWificondEventCallback iWificondEventCallback) throws RemoteException;

    boolean tearDownApInterface(String str) throws RemoteException;

    boolean tearDownClientInterface(String str) throws RemoteException;

    void tearDownInterfaces() throws RemoteException;

    void unregisterWificondEventCallback(IWificondEventCallback iWificondEventCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IWificond {
        static final int TRANSACTION_GetApInterfaces = 7;
        static final int TRANSACTION_GetClientInterfaces = 6;
        static final int TRANSACTION_RegisterCallback = 13;
        static final int TRANSACTION_UnregisterCallback = 14;
        static final int TRANSACTION_createApInterface = 1;
        static final int TRANSACTION_createClientInterface = 2;
        static final int TRANSACTION_getAvailable2gChannels = 8;
        static final int TRANSACTION_getAvailable5gNonDFSChannels = 9;
        static final int TRANSACTION_getAvailable60gChannels = 12;
        static final int TRANSACTION_getAvailable6gChannels = 11;
        static final int TRANSACTION_getAvailableDFSChannels = 10;
        static final int TRANSACTION_getDeviceWiphyCapabilities = 17;
        static final int TRANSACTION_notifyCountryCodeChanged = 18;
        static final int TRANSACTION_registerWificondEventCallback = 15;
        static final int TRANSACTION_tearDownApInterface = 3;
        static final int TRANSACTION_tearDownClientInterface = 4;
        static final int TRANSACTION_tearDownInterfaces = 5;
        static final int TRANSACTION_unregisterWificondEventCallback = 16;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }

        public Stub() {
            attachInterface(this, IWificond.DESCRIPTOR);
        }

        public static IWificond asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IWificond.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWificond)) {
                return (IWificond) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createApInterface";
                case 2:
                    return "createClientInterface";
                case 3:
                    return "tearDownApInterface";
                case 4:
                    return "tearDownClientInterface";
                case 5:
                    return "tearDownInterfaces";
                case 6:
                    return "GetClientInterfaces";
                case 7:
                    return "GetApInterfaces";
                case 8:
                    return "getAvailable2gChannels";
                case 9:
                    return "getAvailable5gNonDFSChannels";
                case 10:
                    return "getAvailableDFSChannels";
                case 11:
                    return "getAvailable6gChannels";
                case 12:
                    return "getAvailable60gChannels";
                case 13:
                    return "RegisterCallback";
                case 14:
                    return "UnregisterCallback";
                case 15:
                    return "registerWificondEventCallback";
                case 16:
                    return "unregisterWificondEventCallback";
                case 17:
                    return "getDeviceWiphyCapabilities";
                case 18:
                    return "notifyCountryCodeChanged";
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
                parcel.enforceInterface(IWificond.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWificond.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IApInterface iApInterfaceCreateApInterface = createApInterface(string);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iApInterfaceCreateApInterface);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IClientInterface iClientInterfaceCreateClientInterface = createClientInterface(string2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iClientInterfaceCreateClientInterface);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zTearDownApInterface = tearDownApInterface(string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zTearDownApInterface);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zTearDownClientInterface = tearDownClientInterface(string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zTearDownClientInterface);
                    return true;
                case 5:
                    tearDownInterfaces();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    List<IBinder> listGetClientInterfaces = GetClientInterfaces();
                    parcel2.writeNoException();
                    parcel2.writeBinderList(listGetClientInterfaces);
                    return true;
                case 7:
                    List<IBinder> listGetApInterfaces = GetApInterfaces();
                    parcel2.writeNoException();
                    parcel2.writeBinderList(listGetApInterfaces);
                    return true;
                case 8:
                    int[] available2gChannels = getAvailable2gChannels();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(available2gChannels);
                    return true;
                case 9:
                    int[] available5gNonDFSChannels = getAvailable5gNonDFSChannels();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(available5gNonDFSChannels);
                    return true;
                case 10:
                    int[] availableDFSChannels = getAvailableDFSChannels();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(availableDFSChannels);
                    return true;
                case 11:
                    int[] available6gChannels = getAvailable6gChannels();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(available6gChannels);
                    return true;
                case 12:
                    int[] available60gChannels = getAvailable60gChannels();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(available60gChannels);
                    return true;
                case 13:
                    IInterfaceEventCallback iInterfaceEventCallbackAsInterface = IInterfaceEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    RegisterCallback(iInterfaceEventCallbackAsInterface);
                    return true;
                case 14:
                    IInterfaceEventCallback iInterfaceEventCallbackAsInterface2 = IInterfaceEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    UnregisterCallback(iInterfaceEventCallbackAsInterface2);
                    return true;
                case 15:
                    IWificondEventCallback iWificondEventCallbackAsInterface = IWificondEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerWificondEventCallback(iWificondEventCallbackAsInterface);
                    return true;
                case 16:
                    IWificondEventCallback iWificondEventCallbackAsInterface2 = IWificondEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterWificondEventCallback(iWificondEventCallbackAsInterface2);
                    return true;
                case 17:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    DeviceWiphyCapabilities deviceWiphyCapabilities = getDeviceWiphyCapabilities(string5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceWiphyCapabilities, 1);
                    return true;
                case 18:
                    notifyCountryCodeChanged();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IWificond {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWificond.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IWificond
            public IApInterface createApInterface(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IApInterface.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public IClientInterface createClientInterface(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IClientInterface.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public boolean tearDownApInterface(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public boolean tearDownClientInterface(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public void tearDownInterfaces() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public List<IBinder> GetClientInterfaces() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createBinderArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public List<IBinder> GetApInterfaces() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createBinderArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public int[] getAvailable2gChannels() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public int[] getAvailable5gNonDFSChannels() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public int[] getAvailableDFSChannels() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public int[] getAvailable6gChannels() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public int[] getAvailable60gChannels() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public void RegisterCallback(IInterfaceEventCallback iInterfaceEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInterfaceEventCallback);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public void UnregisterCallback(IInterfaceEventCallback iInterfaceEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInterfaceEventCallback);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public void registerWificondEventCallback(IWificondEventCallback iWificondEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWificondEventCallback);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public void unregisterWificondEventCallback(IWificondEventCallback iWificondEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWificondEventCallback);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public DeviceWiphyCapabilities getDeviceWiphyCapabilities(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DeviceWiphyCapabilities) parcelObtain2.readTypedObject(DeviceWiphyCapabilities.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IWificond
            public void notifyCountryCodeChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWificond.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
