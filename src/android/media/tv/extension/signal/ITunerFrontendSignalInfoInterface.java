package android.media.tv.extension.signal;

import android.media.tv.extension.signal.ITunerFrontendSignalInfoListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITunerFrontendSignalInfoInterface extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.signal.ITunerFrontendSignalInfoInterface";

    public static class Default implements ITunerFrontendSignalInfoInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.signal.ITunerFrontendSignalInfoInterface
        public Bundle getFrontendSignalInfo(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.signal.ITunerFrontendSignalInfoInterface
        public void setFrontendSignalInfoListener(ITunerFrontendSignalInfoListener iTunerFrontendSignalInfoListener) throws RemoteException {
        }
    }

    Bundle getFrontendSignalInfo(String str) throws RemoteException;

    void setFrontendSignalInfoListener(ITunerFrontendSignalInfoListener iTunerFrontendSignalInfoListener) throws RemoteException;

    public static abstract class Stub extends Binder implements ITunerFrontendSignalInfoInterface {
        static final int TRANSACTION_getFrontendSignalInfo = 1;
        static final int TRANSACTION_setFrontendSignalInfoListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.signal.ITunerFrontendSignalInfoInterface");
        }

        public static ITunerFrontendSignalInfoInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.signal.ITunerFrontendSignalInfoInterface");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITunerFrontendSignalInfoInterface)) {
                return (ITunerFrontendSignalInfoInterface) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getFrontendSignalInfo";
            }
            if (i != 2) {
                return null;
            }
            return "setFrontendSignalInfoListener";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.signal.ITunerFrontendSignalInfoInterface");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.signal.ITunerFrontendSignalInfoInterface");
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                Bundle frontendSignalInfo = getFrontendSignalInfo(string);
                parcel2.writeNoException();
                parcel2.writeTypedObject(frontendSignalInfo, 1);
            } else if (i == 2) {
                ITunerFrontendSignalInfoListener iTunerFrontendSignalInfoListenerAsInterface = ITunerFrontendSignalInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setFrontendSignalInfoListener(iTunerFrontendSignalInfoListenerAsInterface);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITunerFrontendSignalInfoInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.signal.ITunerFrontendSignalInfoInterface";
            }

            @Override // android.media.tv.extension.signal.ITunerFrontendSignalInfoInterface
            public Bundle getFrontendSignalInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.ITunerFrontendSignalInfoInterface");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.ITunerFrontendSignalInfoInterface
            public void setFrontendSignalInfoListener(ITunerFrontendSignalInfoListener iTunerFrontendSignalInfoListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.ITunerFrontendSignalInfoInterface");
                    parcelObtain.writeStrongInterface(iTunerFrontendSignalInfoListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
