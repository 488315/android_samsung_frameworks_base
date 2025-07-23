package com.android.ims.internal.uce.uceservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.uce.common.UceLong;
import com.android.ims.internal.uce.options.IOptionsListener;
import com.android.ims.internal.uce.options.IOptionsService;
import com.android.ims.internal.uce.presence.IPresenceListener;
import com.android.ims.internal.uce.presence.IPresenceService;
import com.android.ims.internal.uce.uceservice.IUceListener;

/* loaded from: classes5.dex */
public interface IUceService extends IInterface {

    public static class Default implements IUceService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createOptionsService(IOptionsListener iOptionsListener, UceLong uceLong) throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createOptionsServiceForSubscription(IOptionsListener iOptionsListener, UceLong uceLong, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createPresenceService(IPresenceListener iPresenceListener, UceLong uceLong) throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createPresenceServiceForSubscription(IPresenceListener iPresenceListener, UceLong uceLong, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public void destroyOptionsService(int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public void destroyPresenceService(int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public IOptionsService getOptionsService() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public IOptionsService getOptionsServiceForSubscription(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public IPresenceService getPresenceService() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public IPresenceService getPresenceServiceForSubscription(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean getServiceStatus() throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean isServiceStarted() throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean startService(IUceListener iUceListener) throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean stopService() throws RemoteException {
            return false;
        }
    }

    @Deprecated
    int createOptionsService(IOptionsListener iOptionsListener, UceLong uceLong) throws RemoteException;

    int createOptionsServiceForSubscription(IOptionsListener iOptionsListener, UceLong uceLong, String str) throws RemoteException;

    @Deprecated
    int createPresenceService(IPresenceListener iPresenceListener, UceLong uceLong) throws RemoteException;

    int createPresenceServiceForSubscription(IPresenceListener iPresenceListener, UceLong uceLong, String str) throws RemoteException;

    void destroyOptionsService(int i) throws RemoteException;

    void destroyPresenceService(int i) throws RemoteException;

    @Deprecated
    IOptionsService getOptionsService() throws RemoteException;

    IOptionsService getOptionsServiceForSubscription(String str) throws RemoteException;

    @Deprecated
    IPresenceService getPresenceService() throws RemoteException;

    IPresenceService getPresenceServiceForSubscription(String str) throws RemoteException;

    boolean getServiceStatus() throws RemoteException;

    boolean isServiceStarted() throws RemoteException;

    boolean startService(IUceListener iUceListener) throws RemoteException;

    boolean stopService() throws RemoteException;

    public static abstract class Stub extends Binder implements IUceService {
        public static final String DESCRIPTOR = "com.android.ims.internal.uce.uceservice.IUceService";
        static final int TRANSACTION_createOptionsService = 4;
        static final int TRANSACTION_createOptionsServiceForSubscription = 5;
        static final int TRANSACTION_createPresenceService = 7;
        static final int TRANSACTION_createPresenceServiceForSubscription = 8;
        static final int TRANSACTION_destroyOptionsService = 6;
        static final int TRANSACTION_destroyPresenceService = 9;
        static final int TRANSACTION_getOptionsService = 13;
        static final int TRANSACTION_getOptionsServiceForSubscription = 14;
        static final int TRANSACTION_getPresenceService = 11;
        static final int TRANSACTION_getPresenceServiceForSubscription = 12;
        static final int TRANSACTION_getServiceStatus = 10;
        static final int TRANSACTION_isServiceStarted = 3;
        static final int TRANSACTION_startService = 1;
        static final int TRANSACTION_stopService = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUceService)) {
                return (IUceService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startService";
                case 2:
                    return "stopService";
                case 3:
                    return "isServiceStarted";
                case 4:
                    return "createOptionsService";
                case 5:
                    return "createOptionsServiceForSubscription";
                case 6:
                    return "destroyOptionsService";
                case 7:
                    return "createPresenceService";
                case 8:
                    return "createPresenceServiceForSubscription";
                case 9:
                    return "destroyPresenceService";
                case 10:
                    return "getServiceStatus";
                case 11:
                    return "getPresenceService";
                case 12:
                    return "getPresenceServiceForSubscription";
                case 13:
                    return "getOptionsService";
                case 14:
                    return "getOptionsServiceForSubscription";
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
                    IUceListener asInterface = IUceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean startService = startService(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startService);
                    return true;
                case 2:
                    boolean stopService = stopService();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopService);
                    return true;
                case 3:
                    boolean isServiceStarted = isServiceStarted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isServiceStarted);
                    return true;
                case 4:
                    IOptionsListener asInterface2 = IOptionsListener.Stub.asInterface(parcel.readStrongBinder());
                    UceLong uceLong = (UceLong) parcel.readTypedObject(UceLong.CREATOR);
                    parcel.enforceNoDataAvail();
                    int createOptionsService = createOptionsService(asInterface2, uceLong);
                    parcel2.writeNoException();
                    parcel2.writeInt(createOptionsService);
                    parcel2.writeTypedObject(uceLong, 1);
                    return true;
                case 5:
                    IOptionsListener asInterface3 = IOptionsListener.Stub.asInterface(parcel.readStrongBinder());
                    UceLong uceLong2 = (UceLong) parcel.readTypedObject(UceLong.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int createOptionsServiceForSubscription = createOptionsServiceForSubscription(asInterface3, uceLong2, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(createOptionsServiceForSubscription);
                    parcel2.writeTypedObject(uceLong2, 1);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyOptionsService(readInt);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IPresenceListener asInterface4 = IPresenceListener.Stub.asInterface(parcel.readStrongBinder());
                    UceLong uceLong3 = (UceLong) parcel.readTypedObject(UceLong.CREATOR);
                    parcel.enforceNoDataAvail();
                    int createPresenceService = createPresenceService(asInterface4, uceLong3);
                    parcel2.writeNoException();
                    parcel2.writeInt(createPresenceService);
                    parcel2.writeTypedObject(uceLong3, 1);
                    return true;
                case 8:
                    IPresenceListener asInterface5 = IPresenceListener.Stub.asInterface(parcel.readStrongBinder());
                    UceLong uceLong4 = (UceLong) parcel.readTypedObject(UceLong.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int createPresenceServiceForSubscription = createPresenceServiceForSubscription(asInterface5, uceLong4, readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(createPresenceServiceForSubscription);
                    parcel2.writeTypedObject(uceLong4, 1);
                    return true;
                case 9:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyPresenceService(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean serviceStatus = getServiceStatus();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(serviceStatus);
                    return true;
                case 11:
                    IPresenceService presenceService = getPresenceService();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(presenceService);
                    return true;
                case 12:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IPresenceService presenceServiceForSubscription = getPresenceServiceForSubscription(readString3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(presenceServiceForSubscription);
                    return true;
                case 13:
                    IOptionsService optionsService = getOptionsService();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(optionsService);
                    return true;
                case 14:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IOptionsService optionsServiceForSubscription = getOptionsServiceForSubscription(readString4);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(optionsServiceForSubscription);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IUceService {
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

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public boolean startService(IUceListener iUceListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUceListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public boolean stopService() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public boolean isServiceStarted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public int createOptionsService(IOptionsListener iOptionsListener, UceLong uceLong) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOptionsListener);
                    obtain.writeTypedObject(uceLong, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        uceLong.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public int createOptionsServiceForSubscription(IOptionsListener iOptionsListener, UceLong uceLong, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOptionsListener);
                    obtain.writeTypedObject(uceLong, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        uceLong.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public void destroyOptionsService(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public int createPresenceService(IPresenceListener iPresenceListener, UceLong uceLong) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPresenceListener);
                    obtain.writeTypedObject(uceLong, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        uceLong.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public int createPresenceServiceForSubscription(IPresenceListener iPresenceListener, UceLong uceLong, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPresenceListener);
                    obtain.writeTypedObject(uceLong, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        uceLong.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public void destroyPresenceService(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public boolean getServiceStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public IPresenceService getPresenceService() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return IPresenceService.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public IPresenceService getPresenceServiceForSubscription(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return IPresenceService.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public IOptionsService getOptionsService() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return IOptionsService.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.uceservice.IUceService
            public IOptionsService getOptionsServiceForSubscription(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return IOptionsService.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
