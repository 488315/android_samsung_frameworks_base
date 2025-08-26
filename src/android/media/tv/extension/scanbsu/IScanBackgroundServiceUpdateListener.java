package android.media.tv.extension.scanbsu;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IScanBackgroundServiceUpdateListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener";

    public static class Default implements IScanBackgroundServiceUpdateListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener
        public void onChannelListUpdate(String str, Bundle[] bundleArr) throws RemoteException {
        }

        @Override // android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener
        public void onNetworkListUpdate(String str, Bundle[] bundleArr) throws RemoteException {
        }

        @Override // android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener
        public void onTransportStreamingListUpdate(String str, Bundle[] bundleArr) throws RemoteException {
        }
    }

    void onChannelListUpdate(String str, Bundle[] bundleArr) throws RemoteException;

    void onNetworkListUpdate(String str, Bundle[] bundleArr) throws RemoteException;

    void onTransportStreamingListUpdate(String str, Bundle[] bundleArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IScanBackgroundServiceUpdateListener {
        static final int TRANSACTION_onChannelListUpdate = 1;
        static final int TRANSACTION_onNetworkListUpdate = 2;
        static final int TRANSACTION_onTransportStreamingListUpdate = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener");
        }

        public static IScanBackgroundServiceUpdateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IScanBackgroundServiceUpdateListener)) {
                return (IScanBackgroundServiceUpdateListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onChannelListUpdate";
            }
            if (i == 2) {
                return "onNetworkListUpdate";
            }
            if (i != 3) {
                return null;
            }
            return "onTransportStreamingListUpdate";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            Bundle[] bundleArr;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener");
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                int i3 = parcel.readInt();
                if (i3 > 1000000) {
                    throw new BadParcelableException("Array too large: " + i3);
                }
                bundleArr = i3 >= 0 ? new Bundle[i3] : null;
                parcel.enforceNoDataAvail();
                onChannelListUpdate(string, bundleArr);
                parcel2.writeNoException();
                parcel2.writeTypedArray(bundleArr, 1);
            } else if (i == 2) {
                String string2 = parcel.readString();
                int i4 = parcel.readInt();
                if (i4 > 1000000) {
                    throw new BadParcelableException("Array too large: " + i4);
                }
                bundleArr = i4 >= 0 ? new Bundle[i4] : null;
                parcel.enforceNoDataAvail();
                onNetworkListUpdate(string2, bundleArr);
                parcel2.writeNoException();
                parcel2.writeTypedArray(bundleArr, 1);
            } else if (i == 3) {
                String string3 = parcel.readString();
                int i5 = parcel.readInt();
                if (i5 > 1000000) {
                    throw new BadParcelableException("Array too large: " + i5);
                }
                bundleArr = i5 >= 0 ? new Bundle[i5] : null;
                parcel.enforceNoDataAvail();
                onTransportStreamingListUpdate(string3, bundleArr);
                parcel2.writeNoException();
                parcel2.writeTypedArray(bundleArr, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IScanBackgroundServiceUpdateListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener";
            }

            @Override // android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener
            public void onChannelListUpdate(String str, Bundle[] bundleArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(bundleArr.length);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.readTypedArray(bundleArr, Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener
            public void onNetworkListUpdate(String str, Bundle[] bundleArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(bundleArr.length);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.readTypedArray(bundleArr, Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener
            public void onTransportStreamingListUpdate(String str, Bundle[] bundleArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scanbsu.IScanBackgroundServiceUpdateListener");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(bundleArr.length);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.readTypedArray(bundleArr, Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
