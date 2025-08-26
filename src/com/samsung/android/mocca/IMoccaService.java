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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMoccaService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMoccaService)) {
                return (IMoccaService) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAvailableType = isAvailableType(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAvailableType);
                    return true;
                case 3:
                    IMoccaEventListener iMoccaEventListenerAsInterface = IMoccaEventListener.Stub.asInterface(parcel.readStrongBinder());
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRegisterContextAvailabilityListener = registerContextAvailabilityListener(iMoccaEventListenerAsInterface, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterContextAvailabilityListener);
                    return true;
                case 4:
                    IMoccaEventListener iMoccaEventListenerAsInterface2 = IMoccaEventListener.Stub.asInterface(parcel.readStrongBinder());
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterContextAvailabilityListener(iMoccaEventListenerAsInterface2, string3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IMoccaEventListener iMoccaEventListenerAsInterface3 = IMoccaEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zHasContextAvailabilityListener = hasContextAvailabilityListener(iMoccaEventListenerAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasContextAvailabilityListener);
                    return true;
                case 6:
                    IMoccaEventListener iMoccaEventListenerAsInterface4 = IMoccaEventListener.Stub.asInterface(parcel.readStrongBinder());
                    String string4 = parcel.readString();
                    ContextParam contextParam = (ContextParam) parcel.readTypedObject(ContextParam.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRegisterContextListener = registerContextListener(iMoccaEventListenerAsInterface4, string4, contextParam);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterContextListener);
                    return true;
                case 7:
                    IMoccaEventListener iMoccaEventListenerAsInterface5 = IMoccaEventListener.Stub.asInterface(parcel.readStrongBinder());
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterContextListener(iMoccaEventListenerAsInterface5, string5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IMoccaEventListener iMoccaEventListenerAsInterface6 = IMoccaEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zHasContextListener = hasContextListener(iMoccaEventListenerAsInterface6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasContextListener);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public boolean isAvailableType(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public boolean registerContextAvailabilityListener(IMoccaEventListener iMoccaEventListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMoccaEventListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public void unregisterContextAvailabilityListener(IMoccaEventListener iMoccaEventListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMoccaEventListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public boolean hasContextAvailabilityListener(IMoccaEventListener iMoccaEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMoccaEventListener);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public boolean registerContextListener(IMoccaEventListener iMoccaEventListener, String str, ContextParam contextParam) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMoccaEventListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(contextParam, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public void unregisterContextListener(IMoccaEventListener iMoccaEventListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMoccaEventListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaService
            public boolean hasContextListener(IMoccaEventListener iMoccaEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMoccaService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMoccaEventListener);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
