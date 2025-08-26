package android.service.trust;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes3.dex */
public interface ITrustAgentServiceCallback extends IInterface {

    public static class Default implements ITrustAgentServiceCallback {
        @Override // android.service.trust.ITrustAgentServiceCallback
        public void addEscrowToken(byte[] bArr, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void grantTrust(CharSequence charSequence, long j, int i, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void isEscrowTokenActive(long j, int i) throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void lockUser() throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void onConfigureCompleted(boolean z, IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void removeEscrowToken(long j, int i) throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void revokeTrust() throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void setManagingTrust(boolean z) throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void showKeyguardErrorMessage(CharSequence charSequence) throws RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void unlockUserWithToken(long j, byte[] bArr, int i) throws RemoteException {
        }
    }

    void addEscrowToken(byte[] bArr, int i) throws RemoteException;

    void grantTrust(CharSequence charSequence, long j, int i, AndroidFuture androidFuture) throws RemoteException;

    void isEscrowTokenActive(long j, int i) throws RemoteException;

    void lockUser() throws RemoteException;

    void onConfigureCompleted(boolean z, IBinder iBinder) throws RemoteException;

    void removeEscrowToken(long j, int i) throws RemoteException;

    void revokeTrust() throws RemoteException;

    void setManagingTrust(boolean z) throws RemoteException;

    void showKeyguardErrorMessage(CharSequence charSequence) throws RemoteException;

    void unlockUserWithToken(long j, byte[] bArr, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ITrustAgentServiceCallback {
        public static final String DESCRIPTOR = "android.service.trust.ITrustAgentServiceCallback";
        static final int TRANSACTION_addEscrowToken = 6;
        static final int TRANSACTION_grantTrust = 1;
        static final int TRANSACTION_isEscrowTokenActive = 7;
        static final int TRANSACTION_lockUser = 3;
        static final int TRANSACTION_onConfigureCompleted = 5;
        static final int TRANSACTION_removeEscrowToken = 8;
        static final int TRANSACTION_revokeTrust = 2;
        static final int TRANSACTION_setManagingTrust = 4;
        static final int TRANSACTION_showKeyguardErrorMessage = 10;
        static final int TRANSACTION_unlockUserWithToken = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITrustAgentServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITrustAgentServiceCallback)) {
                return (ITrustAgentServiceCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "grantTrust";
                case 2:
                    return "revokeTrust";
                case 3:
                    return "lockUser";
                case 4:
                    return "setManagingTrust";
                case 5:
                    return "onConfigureCompleted";
                case 6:
                    return "addEscrowToken";
                case 7:
                    return "isEscrowTokenActive";
                case 8:
                    return "removeEscrowToken";
                case 9:
                    return "unlockUserWithToken";
                case 10:
                    return "showKeyguardErrorMessage";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    long j = parcel.readLong();
                    int i3 = parcel.readInt();
                    AndroidFuture androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    grantTrust(charSequence, j, i3, androidFuture);
                    return true;
                case 2:
                    revokeTrust();
                    return true;
                case 3:
                    lockUser();
                    return true;
                case 4:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setManagingTrust(z);
                    return true;
                case 5:
                    boolean z2 = parcel.readBoolean();
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onConfigureCompleted(z2, strongBinder);
                    return true;
                case 6:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addEscrowToken(bArrCreateByteArray, i4);
                    return true;
                case 7:
                    long j2 = parcel.readLong();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isEscrowTokenActive(j2, i5);
                    return true;
                case 8:
                    long j3 = parcel.readLong();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeEscrowToken(j3, i6);
                    return true;
                case 9:
                    long j4 = parcel.readLong();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unlockUserWithToken(j4, bArrCreateByteArray2, i7);
                    return true;
                case 10:
                    CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    showKeyguardErrorMessage(charSequence2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITrustAgentServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void grantTrust(CharSequence charSequence, long j, int i, AndroidFuture androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void revokeTrust() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void lockUser() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void setManagingTrust(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void onConfigureCompleted(boolean z, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void addEscrowToken(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void isEscrowTokenActive(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void removeEscrowToken(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void unlockUserWithToken(long j, byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void showKeyguardErrorMessage(CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
