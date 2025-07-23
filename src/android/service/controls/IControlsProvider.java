package android.service.controls;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.controls.IControlsActionCallback;
import android.service.controls.IControlsProviderInfoSubscriber;
import android.service.controls.IControlsSubscriber;
import android.service.controls.actions.ControlActionWrapper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IControlsProvider extends IInterface {
    public static final String DESCRIPTOR = "android.service.controls.IControlsProvider";

    public static class Default implements IControlsProvider {
        @Override // android.service.controls.IControlsProvider
        public void action(String str, ControlActionWrapper controlActionWrapper, IControlsActionCallback iControlsActionCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.controls.IControlsProvider
        public void load(IControlsSubscriber iControlsSubscriber) throws RemoteException {
        }

        @Override // android.service.controls.IControlsProvider
        public void loadControlsProviderInfo(IControlsProviderInfoSubscriber iControlsProviderInfoSubscriber) throws RemoteException {
        }

        @Override // android.service.controls.IControlsProvider
        public void loadSuggested(IControlsSubscriber iControlsSubscriber) throws RemoteException {
        }

        @Override // android.service.controls.IControlsProvider
        public void subscribe(List<String> list, IControlsSubscriber iControlsSubscriber) throws RemoteException {
        }
    }

    void action(String str, ControlActionWrapper controlActionWrapper, IControlsActionCallback iControlsActionCallback) throws RemoteException;

    void load(IControlsSubscriber iControlsSubscriber) throws RemoteException;

    void loadControlsProviderInfo(IControlsProviderInfoSubscriber iControlsProviderInfoSubscriber) throws RemoteException;

    void loadSuggested(IControlsSubscriber iControlsSubscriber) throws RemoteException;

    void subscribe(List<String> list, IControlsSubscriber iControlsSubscriber) throws RemoteException;

    public static abstract class Stub extends Binder implements IControlsProvider {
        static final int TRANSACTION_action = 4;
        static final int TRANSACTION_load = 1;
        static final int TRANSACTION_loadControlsProviderInfo = 5;
        static final int TRANSACTION_loadSuggested = 2;
        static final int TRANSACTION_subscribe = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IControlsProvider.DESCRIPTOR);
        }

        public static IControlsProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IControlsProvider.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IControlsProvider)) {
                return (IControlsProvider) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "load";
            }
            if (i == 2) {
                return "loadSuggested";
            }
            if (i == 3) {
                return "subscribe";
            }
            if (i == 4) {
                return "action";
            }
            if (i != 5) {
                return null;
            }
            return "loadControlsProviderInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IControlsProvider.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IControlsProvider.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IControlsSubscriber asInterface = IControlsSubscriber.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                load(asInterface);
            } else if (i == 2) {
                IControlsSubscriber asInterface2 = IControlsSubscriber.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                loadSuggested(asInterface2);
            } else if (i == 3) {
                ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                IControlsSubscriber asInterface3 = IControlsSubscriber.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                subscribe(createStringArrayList, asInterface3);
            } else if (i == 4) {
                String readString = parcel.readString();
                ControlActionWrapper controlActionWrapper = (ControlActionWrapper) parcel.readTypedObject(ControlActionWrapper.CREATOR);
                IControlsActionCallback asInterface4 = IControlsActionCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                action(readString, controlActionWrapper, asInterface4);
            } else if (i == 5) {
                IControlsProviderInfoSubscriber asInterface5 = IControlsProviderInfoSubscriber.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                loadControlsProviderInfo(asInterface5);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IControlsProvider {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IControlsProvider.DESCRIPTOR;
            }

            @Override // android.service.controls.IControlsProvider
            public void load(IControlsSubscriber iControlsSubscriber) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IControlsProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iControlsSubscriber);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsProvider
            public void loadSuggested(IControlsSubscriber iControlsSubscriber) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IControlsProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iControlsSubscriber);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsProvider
            public void subscribe(List<String> list, IControlsSubscriber iControlsSubscriber) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IControlsProvider.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iControlsSubscriber);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsProvider
            public void action(String str, ControlActionWrapper controlActionWrapper, IControlsActionCallback iControlsActionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IControlsProvider.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(controlActionWrapper, 0);
                    obtain.writeStrongInterface(iControlsActionCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsProvider
            public void loadControlsProviderInfo(IControlsProviderInfoSubscriber iControlsProviderInfoSubscriber) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IControlsProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iControlsProviderInfoSubscriber);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
