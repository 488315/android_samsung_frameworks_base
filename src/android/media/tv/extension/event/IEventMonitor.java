package android.media.tv.extension.event;

import android.media.tv.extension.event.IEventMonitorListener;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IEventMonitor extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.event.IEventMonitor";

    public static class Default implements IEventMonitor {
        @Override // android.media.tv.extension.event.IEventMonitor
        public void addFollowingEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException {
        }

        @Override // android.media.tv.extension.event.IEventMonitor
        public void addPresentEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.event.IEventMonitor
        public Bundle getFollowingEventInfo(long j) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.event.IEventMonitor
        public Bundle getPresentEventInfo(long j) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.event.IEventMonitor
        public Bundle getSdtGuidanceInfo(long j) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.event.IEventMonitor
        public void removeFollowingEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException {
        }

        @Override // android.media.tv.extension.event.IEventMonitor
        public void removePresentEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException {
        }

        @Override // android.media.tv.extension.event.IEventMonitor
        public void setBgmTuneChannelInfo(Uri[] uriArr) throws RemoteException {
        }
    }

    void addFollowingEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException;

    void addPresentEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException;

    Bundle getFollowingEventInfo(long j) throws RemoteException;

    Bundle getPresentEventInfo(long j) throws RemoteException;

    Bundle getSdtGuidanceInfo(long j) throws RemoteException;

    void removeFollowingEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException;

    void removePresentEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException;

    void setBgmTuneChannelInfo(Uri[] uriArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IEventMonitor {
        static final int TRANSACTION_addFollowingEventInfoListener = 5;
        static final int TRANSACTION_addPresentEventInfoListener = 2;
        static final int TRANSACTION_getFollowingEventInfo = 4;
        static final int TRANSACTION_getPresentEventInfo = 1;
        static final int TRANSACTION_getSdtGuidanceInfo = 7;
        static final int TRANSACTION_removeFollowingEventInfoListener = 6;
        static final int TRANSACTION_removePresentEventInfoListener = 3;
        static final int TRANSACTION_setBgmTuneChannelInfo = 8;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.event.IEventMonitor");
        }

        public static IEventMonitor asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.event.IEventMonitor");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEventMonitor)) {
                return (IEventMonitor) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getPresentEventInfo";
                case 2:
                    return "addPresentEventInfoListener";
                case 3:
                    return "removePresentEventInfoListener";
                case 4:
                    return "getFollowingEventInfo";
                case 5:
                    return "addFollowingEventInfoListener";
                case 6:
                    return "removeFollowingEventInfoListener";
                case 7:
                    return "getSdtGuidanceInfo";
                case 8:
                    return "setBgmTuneChannelInfo";
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
                parcel.enforceInterface("android.media.tv.extension.event.IEventMonitor");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.event.IEventMonitor");
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    Bundle presentEventInfo = getPresentEventInfo(readLong);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(presentEventInfo, 1);
                    return true;
                case 2:
                    IEventMonitorListener asInterface = IEventMonitorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addPresentEventInfoListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IEventMonitorListener asInterface2 = IEventMonitorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removePresentEventInfoListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    Bundle followingEventInfo = getFollowingEventInfo(readLong2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(followingEventInfo, 1);
                    return true;
                case 5:
                    IEventMonitorListener asInterface3 = IEventMonitorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addFollowingEventInfoListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IEventMonitorListener asInterface4 = IEventMonitorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeFollowingEventInfoListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    Bundle sdtGuidanceInfo = getSdtGuidanceInfo(readLong3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sdtGuidanceInfo, 1);
                    return true;
                case 8:
                    Uri[] uriArr = (Uri[]) parcel.createTypedArray(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    setBgmTuneChannelInfo(uriArr);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IEventMonitor {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.event.IEventMonitor";
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public Bundle getPresentEventInfo(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public void addPresentEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    obtain.writeStrongInterface(iEventMonitorListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public void removePresentEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    obtain.writeStrongInterface(iEventMonitorListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public Bundle getFollowingEventInfo(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    obtain.writeLong(j);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public void addFollowingEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    obtain.writeStrongInterface(iEventMonitorListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public void removeFollowingEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    obtain.writeStrongInterface(iEventMonitorListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public Bundle getSdtGuidanceInfo(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    obtain.writeLong(j);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public void setBgmTuneChannelInfo(Uri[] uriArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    obtain.writeTypedArray(uriArr, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
