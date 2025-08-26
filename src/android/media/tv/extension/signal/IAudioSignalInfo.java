package android.media.tv.extension.signal;

import android.media.tv.extension.signal.IAudioSignalInfoListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IAudioSignalInfo extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.signal.IAudioSignalInfo";

    public static class Default implements IAudioSignalInfo {
        @Override // android.media.tv.extension.signal.IAudioSignalInfo
        public void addAudioSignalInfoListener(String str, IAudioSignalInfoListener iAudioSignalInfoListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.signal.IAudioSignalInfo
        public Bundle getAudioSignalInfo(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.signal.IAudioSignalInfo
        public String getMtsSelectedTrackId() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.signal.IAudioSignalInfo
        public void notifyMtsSelectTrackFlag(boolean z) throws RemoteException {
        }

        @Override // android.media.tv.extension.signal.IAudioSignalInfo
        public void removeAudioSignalInfoListener(IAudioSignalInfoListener iAudioSignalInfoListener) throws RemoteException {
        }
    }

    void addAudioSignalInfoListener(String str, IAudioSignalInfoListener iAudioSignalInfoListener) throws RemoteException;

    Bundle getAudioSignalInfo(String str) throws RemoteException;

    String getMtsSelectedTrackId() throws RemoteException;

    void notifyMtsSelectTrackFlag(boolean z) throws RemoteException;

    void removeAudioSignalInfoListener(IAudioSignalInfoListener iAudioSignalInfoListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IAudioSignalInfo {
        static final int TRANSACTION_addAudioSignalInfoListener = 4;
        static final int TRANSACTION_getAudioSignalInfo = 1;
        static final int TRANSACTION_getMtsSelectedTrackId = 3;
        static final int TRANSACTION_notifyMtsSelectTrackFlag = 2;
        static final int TRANSACTION_removeAudioSignalInfoListener = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.signal.IAudioSignalInfo");
        }

        public static IAudioSignalInfo asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.signal.IAudioSignalInfo");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAudioSignalInfo)) {
                return (IAudioSignalInfo) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getAudioSignalInfo";
            }
            if (i == 2) {
                return "notifyMtsSelectTrackFlag";
            }
            if (i == 3) {
                return "getMtsSelectedTrackId";
            }
            if (i == 4) {
                return "addAudioSignalInfoListener";
            }
            if (i != 5) {
                return null;
            }
            return "removeAudioSignalInfoListener";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.signal.IAudioSignalInfo");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.signal.IAudioSignalInfo");
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                Bundle audioSignalInfo = getAudioSignalInfo(string);
                parcel2.writeNoException();
                parcel2.writeTypedObject(audioSignalInfo, 1);
            } else if (i == 2) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                notifyMtsSelectTrackFlag(z);
                parcel2.writeNoException();
            } else if (i == 3) {
                String mtsSelectedTrackId = getMtsSelectedTrackId();
                parcel2.writeNoException();
                parcel2.writeString(mtsSelectedTrackId);
            } else if (i == 4) {
                String string2 = parcel.readString();
                IAudioSignalInfoListener iAudioSignalInfoListenerAsInterface = IAudioSignalInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addAudioSignalInfoListener(string2, iAudioSignalInfoListenerAsInterface);
                parcel2.writeNoException();
            } else if (i == 5) {
                IAudioSignalInfoListener iAudioSignalInfoListenerAsInterface2 = IAudioSignalInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeAudioSignalInfoListener(iAudioSignalInfoListenerAsInterface2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAudioSignalInfo {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.signal.IAudioSignalInfo";
            }

            @Override // android.media.tv.extension.signal.IAudioSignalInfo
            public Bundle getAudioSignalInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IAudioSignalInfo");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IAudioSignalInfo
            public void notifyMtsSelectTrackFlag(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IAudioSignalInfo");
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IAudioSignalInfo
            public String getMtsSelectedTrackId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IAudioSignalInfo");
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IAudioSignalInfo
            public void addAudioSignalInfoListener(String str, IAudioSignalInfoListener iAudioSignalInfoListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IAudioSignalInfo");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iAudioSignalInfoListener);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IAudioSignalInfo
            public void removeAudioSignalInfoListener(IAudioSignalInfoListener iAudioSignalInfoListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IAudioSignalInfo");
                    parcelObtain.writeStrongInterface(iAudioSignalInfoListener);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
