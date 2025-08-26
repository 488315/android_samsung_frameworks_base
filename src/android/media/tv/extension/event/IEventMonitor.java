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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.event.IEventMonitor");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEventMonitor)) {
                return (IEventMonitor) iInterfaceQueryLocalInterface;
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
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    Bundle presentEventInfo = getPresentEventInfo(j);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(presentEventInfo, 1);
                    return true;
                case 2:
                    IEventMonitorListener iEventMonitorListenerAsInterface = IEventMonitorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addPresentEventInfoListener(iEventMonitorListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IEventMonitorListener iEventMonitorListenerAsInterface2 = IEventMonitorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removePresentEventInfoListener(iEventMonitorListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    Bundle followingEventInfo = getFollowingEventInfo(j2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(followingEventInfo, 1);
                    return true;
                case 5:
                    IEventMonitorListener iEventMonitorListenerAsInterface3 = IEventMonitorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addFollowingEventInfoListener(iEventMonitorListenerAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IEventMonitorListener iEventMonitorListenerAsInterface4 = IEventMonitorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeFollowingEventInfoListener(iEventMonitorListenerAsInterface4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    Bundle sdtGuidanceInfo = getSdtGuidanceInfo(j3);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public void addPresentEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    parcelObtain.writeStrongInterface(iEventMonitorListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public void removePresentEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    parcelObtain.writeStrongInterface(iEventMonitorListener);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public Bundle getFollowingEventInfo(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public void addFollowingEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    parcelObtain.writeStrongInterface(iEventMonitorListener);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public void removeFollowingEventInfoListener(IEventMonitorListener iEventMonitorListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    parcelObtain.writeStrongInterface(iEventMonitorListener);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public Bundle getSdtGuidanceInfo(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventMonitor
            public void setBgmTuneChannelInfo(Uri[] uriArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitor");
                    parcelObtain.writeTypedArray(uriArr, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
