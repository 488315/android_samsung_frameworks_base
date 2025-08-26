package android.permission;

import android.content.AttributionSourceState;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IPermissionChecker extends IInterface {
    public static final String DESCRIPTOR = "android.permission.IPermissionChecker";
    public static final int PERMISSION_GRANTED = 0;
    public static final int PERMISSION_HARD_DENIED = 2;
    public static final int PERMISSION_SOFT_DENIED = 1;

    public static class Default implements IPermissionChecker {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.permission.IPermissionChecker
        public int checkOp(int i, AttributionSourceState attributionSourceState, String str, boolean z, boolean z2) throws RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionChecker
        public int checkPermission(String str, AttributionSourceState attributionSourceState, String str2, boolean z, boolean z2, boolean z3, int i) throws RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionChecker
        public void finishDataDelivery(int i, AttributionSourceState attributionSourceState, boolean z) throws RemoteException {
        }
    }

    int checkOp(int i, AttributionSourceState attributionSourceState, String str, boolean z, boolean z2) throws RemoteException;

    int checkPermission(String str, AttributionSourceState attributionSourceState, String str2, boolean z, boolean z2, boolean z3, int i) throws RemoteException;

    void finishDataDelivery(int i, AttributionSourceState attributionSourceState, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IPermissionChecker {
        static final int TRANSACTION_checkOp = 3;
        static final int TRANSACTION_checkPermission = 1;
        static final int TRANSACTION_finishDataDelivery = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IPermissionChecker.DESCRIPTOR);
        }

        public static IPermissionChecker asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPermissionChecker.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPermissionChecker)) {
                return (IPermissionChecker) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPermissionChecker.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPermissionChecker.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                AttributionSourceState attributionSourceState = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                String string2 = parcel.readString();
                boolean z = parcel.readBoolean();
                boolean z2 = parcel.readBoolean();
                boolean z3 = parcel.readBoolean();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int iCheckPermission = checkPermission(string, attributionSourceState, string2, z, z2, z3, i3);
                parcel2.writeNoException();
                parcel2.writeInt(iCheckPermission);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                AttributionSourceState attributionSourceState2 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                boolean z4 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                finishDataDelivery(i4, attributionSourceState2, z4);
                parcel2.writeNoException();
            } else if (i == 3) {
                int i5 = parcel.readInt();
                AttributionSourceState attributionSourceState3 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                String string3 = parcel.readString();
                boolean z5 = parcel.readBoolean();
                boolean z6 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                int iCheckOp = checkOp(i5, attributionSourceState3, string3, z5, z6);
                parcel2.writeNoException();
                parcel2.writeInt(iCheckOp);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPermissionChecker {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPermissionChecker.DESCRIPTOR;
            }

            @Override // android.permission.IPermissionChecker
            public int checkPermission(String str, AttributionSourceState attributionSourceState, String str2, boolean z, boolean z2, boolean z3, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionChecker.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionChecker
            public void finishDataDelivery(int i, AttributionSourceState attributionSourceState, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionChecker.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionChecker
            public int checkOp(int i, AttributionSourceState attributionSourceState, String str, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionChecker.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
