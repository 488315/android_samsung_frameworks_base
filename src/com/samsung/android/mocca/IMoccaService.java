package com.samsung.android.mocca;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.mocca.IMoccaEventListener;
import java.util.List;

/* loaded from: classes6.dex */
public interface IMoccaService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.mocca.IMoccaService";

    public static class Default implements IMoccaService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.mocca.IMoccaService
        public List<String> getSupportedTypes() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.mocca.IMoccaService
        public boolean hasContextAvailabilityListener(IMoccaEventListener iMoccaEventListener) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.mocca.IMoccaService
        public boolean hasContextListener(IMoccaEventListener iMoccaEventListener) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.mocca.IMoccaService
        public boolean isAvailableType(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.mocca.IMoccaService
        public boolean registerContextAvailabilityListener(IMoccaEventListener iMoccaEventListener, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.mocca.IMoccaService
        public boolean registerContextListener(IMoccaEventListener iMoccaEventListener, String str, ContextParam contextParam) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.mocca.IMoccaService
        public void unregisterContextAvailabilityListener(IMoccaEventListener iMoccaEventListener, String str) throws RemoteException {
        }

        @Override // com.samsung.android.mocca.IMoccaService
        public void unregisterContextListener(IMoccaEventListener iMoccaEventListener, String str) throws RemoteException {
        }
    }

    List<String> getSupportedTypes() throws RemoteException;

    boolean hasContextAvailabilityListener(IMoccaEventListener iMoccaEventListener) throws RemoteException;

    boolean hasContextListener(IMoccaEventListener iMoccaEventListener) throws RemoteException;

    boolean isAvailableType(String str) throws RemoteException;

    boolean registerContextAvailabilityListener(IMoccaEventListener iMoccaEventListener, String str) throws RemoteException;

    boolean registerContextListener(IMoccaEventListener iMoccaEventListener, String str, ContextParam contextParam) throws RemoteException;

    void unregisterContextAvailabilityListener(IMoccaEventListener iMoccaEventListener, String str) throws RemoteException;

    void unregisterContextListener(IMoccaEventListener iMoccaEventListener, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IMoccaService {
        static final int TRANSACTION_getSupportedTypes = 1;
        static final int TRANSACTION_hasContextAvailabilityListener = 5;
        static final int TRANSACTION_hasContextListener = 8;
        static final int TRANSACTION_isAvailableType = 2;
        static final int TRANSACTION_registerContextAvailabilityListener = 3;
        static final int TRANSACTION_registerContextListener = 6;
        static final int TRANSACTION_unregisterContextAvailabilityListener = 4;
        static final int TRANSACTION_unregisterContextListener = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, IMoccaService.DESCRIPTOR);
        }

        public static IMoccaService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMoccaService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMoccaService)) {
                return (IMoccaService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSupportedTypes";
                case 2:
                    return "isAvailableType";
                case 3:
                    return "registerContextAvailabilityListener";
                case 4:
                    return "unregisterContextAvailabilityListener";
                case 5:
                    return "hasContextAvailabilityListener";
                case 6:
                    return "registerContextListener";
                case 7:
                    return "unregisterContextListener";
                case 8:
                    return "hasContextListener";
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
                parcel.enforceInterface(IMoccaService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMoccaService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    List<String> supportedTypes = getSupportedTypes();
                    parcel2.writeNoException();
                    parcel2.writeStringList(supportedTypes);
                    return true;
                case 2:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAvailableType = isAvailableType(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAvailableType);
                    return true;
                case 3:
                    IMoccaEventListener asInterface = IMoccaEventListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean registerContextAvailabilityListener = registerContextAvailabilityListener(asInterface, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerContextAvailabilityListener);
                    return true;
                case 4:
                    IMoccaEventListener asInterface2 = IMoccaEventListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterContextAvailabilityListener(asInterface2, readString3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IMoccaEventListener asInterface3 = IMoccaEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean hasContextAvailabilityListener = hasContextAvailabilityListener(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasContextAvailabilityListener);
                    return true;
                case 6:
                    IMoccaEventListener asInterface4 = IMoccaEventListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString4 = parcel.readString();
                    ContextParam contextParam = (ContextParam) parcel.readTypedObject(ContextParam.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean registerContextListener = registerContextListener(asInterface4, readString4, contextParam);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerContextListener);
                    return true;
                case 7:
                    IMoccaEventListener asInterface5 = IMoccaEventListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterContextListener(asInterface5, readString5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IMoccaEventListener asInterface6 = IMoccaEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean hasContextListener = hasContextListener(asInterface6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasContextListener);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMoccaService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMoccaService.DESCRIPTOR;
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public List<String> getSupportedTypes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public boolean isAvailableType(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public boolean registerContextAvailabilityListener(IMoccaEventListener iMoccaEventListener, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMoccaEventListener);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public void unregisterContextAvailabilityListener(IMoccaEventListener iMoccaEventListener, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMoccaEventListener);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public boolean hasContextAvailabilityListener(IMoccaEventListener iMoccaEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMoccaEventListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public boolean registerContextListener(IMoccaEventListener iMoccaEventListener, String str, ContextParam contextParam) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMoccaEventListener);
                    obtain.writeString(str);
                    obtain.writeTypedObject(contextParam, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public void unregisterContextListener(IMoccaEventListener iMoccaEventListener, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMoccaEventListener);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public boolean hasContextListener(IMoccaEventListener iMoccaEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMoccaEventListener);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
