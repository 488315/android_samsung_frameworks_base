package android.view;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IDisplayWindowListener extends IInterface {
    public static final String DESCRIPTOR = "android.view.IDisplayWindowListener";

    public static class Default implements IDisplayWindowListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IDisplayWindowListener
        public void onDesktopModeEligibleChanged(int i) throws RemoteException {
        }

        @Override // android.view.IDisplayWindowListener
        public void onDisplayAdded(int i) throws RemoteException {
        }

        @Override // android.view.IDisplayWindowListener
        public void onDisplayConfigurationChanged(int i, Configuration configuration) throws RemoteException {
        }

        @Override // android.view.IDisplayWindowListener
        public void onDisplayRemoved(int i) throws RemoteException {
        }

        @Override // android.view.IDisplayWindowListener
        public void onFixedRotationFinished(int i) throws RemoteException {
        }

        @Override // android.view.IDisplayWindowListener
        public void onFixedRotationStarted(int i, int i2) throws RemoteException {
        }

        @Override // android.view.IDisplayWindowListener
        public void onKeepClearAreasChanged(int i, List<Rect> list, List<Rect> list2) throws RemoteException {
        }
    }

    void onDesktopModeEligibleChanged(int i) throws RemoteException;

    void onDisplayAdded(int i) throws RemoteException;

    void onDisplayConfigurationChanged(int i, Configuration configuration) throws RemoteException;

    void onDisplayRemoved(int i) throws RemoteException;

    void onFixedRotationFinished(int i) throws RemoteException;

    void onFixedRotationStarted(int i, int i2) throws RemoteException;

    void onKeepClearAreasChanged(int i, List<Rect> list, List<Rect> list2) throws RemoteException;

    public static abstract class Stub extends Binder implements IDisplayWindowListener {
        static final int TRANSACTION_onDesktopModeEligibleChanged = 7;
        static final int TRANSACTION_onDisplayAdded = 1;
        static final int TRANSACTION_onDisplayConfigurationChanged = 2;
        static final int TRANSACTION_onDisplayRemoved = 3;
        static final int TRANSACTION_onFixedRotationFinished = 5;
        static final int TRANSACTION_onFixedRotationStarted = 4;
        static final int TRANSACTION_onKeepClearAreasChanged = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, IDisplayWindowListener.DESCRIPTOR);
        }

        public static IDisplayWindowListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDisplayWindowListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDisplayWindowListener)) {
                return (IDisplayWindowListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDisplayAdded";
                case 2:
                    return "onDisplayConfigurationChanged";
                case 3:
                    return "onDisplayRemoved";
                case 4:
                    return "onFixedRotationStarted";
                case 5:
                    return "onFixedRotationFinished";
                case 6:
                    return "onKeepClearAreasChanged";
                case 7:
                    return "onDesktopModeEligibleChanged";
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
                parcel.enforceInterface(IDisplayWindowListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDisplayWindowListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayAdded(i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    Configuration configuration = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDisplayConfigurationChanged(i4, configuration);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayRemoved(i5);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFixedRotationStarted(i6, i7);
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFixedRotationFinished(i8);
                    return true;
                case 6:
                    int i9 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(Rect.CREATOR);
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    onKeepClearAreasChanged(i9, arrayListCreateTypedArrayList, arrayListCreateTypedArrayList2);
                    return true;
                case 7:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDesktopModeEligibleChanged(i10);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDisplayWindowListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDisplayWindowListener.DESCRIPTOR;
            }

            @Override // android.view.IDisplayWindowListener
            public void onDisplayAdded(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayWindowListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowListener
            public void onDisplayConfigurationChanged(int i, Configuration configuration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayWindowListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowListener
            public void onDisplayRemoved(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayWindowListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowListener
            public void onFixedRotationStarted(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayWindowListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowListener
            public void onFixedRotationFinished(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayWindowListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowListener
            public void onKeepClearAreasChanged(int i, List<Rect> list, List<Rect> list2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayWindowListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedList(list2, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IDisplayWindowListener
            public void onDesktopModeEligibleChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayWindowListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
