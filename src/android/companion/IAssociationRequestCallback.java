package android.companion;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes.dex */
public interface IAssociationRequestCallback extends IInterface {
    public static final String DESCRIPTOR = "android.companion.IAssociationRequestCallback";

    public static class Default implements IAssociationRequestCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.IAssociationRequestCallback
        public void onAssociationCreated(AssociationInfo associationInfo) throws RemoteException {
        }

        @Override // android.companion.IAssociationRequestCallback
        public void onAssociationPending(PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // android.companion.IAssociationRequestCallback
        public void onFailure(int i, CharSequence charSequence) throws RemoteException {
        }
    }

    void onAssociationCreated(AssociationInfo associationInfo) throws RemoteException;

    void onAssociationPending(PendingIntent pendingIntent) throws RemoteException;

    void onFailure(int i, CharSequence charSequence) throws RemoteException;

    public static abstract class Stub extends Binder implements IAssociationRequestCallback {
        static final int TRANSACTION_onAssociationCreated = 2;
        static final int TRANSACTION_onAssociationPending = 1;
        static final int TRANSACTION_onFailure = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IAssociationRequestCallback.DESCRIPTOR);
        }

        public static IAssociationRequestCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAssociationRequestCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAssociationRequestCallback)) {
                return (IAssociationRequestCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onAssociationPending";
            }
            if (i == 2) {
                return "onAssociationCreated";
            }
            if (i != 3) {
                return null;
            }
            return "onFailure";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAssociationRequestCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAssociationRequestCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                parcel.enforceNoDataAvail();
                onAssociationPending(pendingIntent);
            } else if (i == 2) {
                AssociationInfo associationInfo = (AssociationInfo) parcel.readTypedObject(AssociationInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onAssociationCreated(associationInfo);
            } else if (i == 3) {
                int i3 = parcel.readInt();
                CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                parcel.enforceNoDataAvail();
                onFailure(i3, charSequence);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAssociationRequestCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAssociationRequestCallback.DESCRIPTOR;
            }

            @Override // android.companion.IAssociationRequestCallback
            public void onAssociationPending(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAssociationRequestCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.IAssociationRequestCallback
            public void onAssociationCreated(AssociationInfo associationInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAssociationRequestCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(associationInfo, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.IAssociationRequestCallback
            public void onFailure(int i, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAssociationRequestCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
