package com.android.internal.inputmethod;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.view.inputmethod.ImeTracker;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes5.dex */
public interface IImeTracker extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IImeTracker";

    public static class Default implements IImeTracker {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void finishTrackingPendingImeVisibilityRequests(AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public boolean hasPendingImeVisibilityRequests() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onCancelled(ImeTracker.Token token, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onDispatched(ImeTracker.Token token) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onFailed(ImeTracker.Token token, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onHidden(ImeTracker.Token token) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onProgress(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onShown(ImeTracker.Token token) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public ImeTracker.Token onStart(String str, int i, int i2, int i3, int i4, boolean z) throws RemoteException {
            return null;
        }
    }

    void finishTrackingPendingImeVisibilityRequests(AndroidFuture androidFuture) throws RemoteException;

    boolean hasPendingImeVisibilityRequests() throws RemoteException;

    void onCancelled(ImeTracker.Token token, int i) throws RemoteException;

    void onDispatched(ImeTracker.Token token) throws RemoteException;

    void onFailed(ImeTracker.Token token, int i) throws RemoteException;

    void onHidden(ImeTracker.Token token) throws RemoteException;

    void onProgress(IBinder iBinder, int i) throws RemoteException;

    void onShown(ImeTracker.Token token) throws RemoteException;

    ImeTracker.Token onStart(String str, int i, int i2, int i3, int i4, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IImeTracker {
        static final int TRANSACTION_finishTrackingPendingImeVisibilityRequests = 9;
        static final int TRANSACTION_hasPendingImeVisibilityRequests = 8;
        static final int TRANSACTION_onCancelled = 4;
        static final int TRANSACTION_onDispatched = 7;
        static final int TRANSACTION_onFailed = 3;
        static final int TRANSACTION_onHidden = 6;
        static final int TRANSACTION_onProgress = 2;
        static final int TRANSACTION_onShown = 5;
        static final int TRANSACTION_onStart = 1;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IImeTracker.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IImeTracker asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImeTracker.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IImeTracker)) {
                return (IImeTracker) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStart";
                case 2:
                    return "onProgress";
                case 3:
                    return "onFailed";
                case 4:
                    return "onCancelled";
                case 5:
                    return "onShown";
                case 6:
                    return "onHidden";
                case 7:
                    return "onDispatched";
                case 8:
                    return "hasPendingImeVisibilityRequests";
                case 9:
                    return "finishTrackingPendingImeVisibilityRequests";
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
                parcel.enforceInterface(IImeTracker.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImeTracker.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ImeTracker.Token tokenOnStart = onStart(string, i3, i4, i5, i6, z);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(tokenOnStart, 1);
                    return true;
                case 2:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onProgress(strongBinder, i7);
                    return true;
                case 3:
                    ImeTracker.Token token = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFailed(token, i8);
                    return true;
                case 4:
                    ImeTracker.Token token2 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCancelled(token2, i9);
                    return true;
                case 5:
                    ImeTracker.Token token3 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    onShown(token3);
                    return true;
                case 6:
                    ImeTracker.Token token4 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    onHidden(token4);
                    return true;
                case 7:
                    ImeTracker.Token token5 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDispatched(token5);
                    return true;
                case 8:
                    boolean zHasPendingImeVisibilityRequests = hasPendingImeVisibilityRequests();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasPendingImeVisibilityRequests);
                    return true;
                case 9:
                    AndroidFuture androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    finishTrackingPendingImeVisibilityRequests(androidFuture);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImeTracker {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImeTracker.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public ImeTracker.Token onStart(String str, int i, int i2, int i3, int i4, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ImeTracker.Token) parcelObtain2.readTypedObject(ImeTracker.Token.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onProgress(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onFailed(ImeTracker.Token token, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    parcelObtain.writeTypedObject(token, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onCancelled(ImeTracker.Token token, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    parcelObtain.writeTypedObject(token, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onShown(ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onHidden(ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onDispatched(ImeTracker.Token token) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    parcelObtain.writeTypedObject(token, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public boolean hasPendingImeVisibilityRequests() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void finishTrackingPendingImeVisibilityRequests(AndroidFuture androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImeTracker.DESCRIPTOR);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        protected void hasPendingImeVisibilityRequests_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        protected void finishTrackingPendingImeVisibilityRequests_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }
    }
}
