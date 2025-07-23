package android.app.pinner;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IPinnerService extends IInterface {
    public static final String DESCRIPTOR = "android.app.pinner.IPinnerService";

    public static class Default implements IPinnerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.pinner.IPinnerService
        public List<PinnedFileStat> getPinnerStats() throws RemoteException {
            return null;
        }
    }

    List<PinnedFileStat> getPinnerStats() throws RemoteException;

    public static abstract class Stub extends Binder implements IPinnerService {
        static final int TRANSACTION_getPinnerStats = 1;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IPinnerService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IPinnerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPinnerService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPinnerService)) {
                return (IPinnerService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getPinnerStats";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPinnerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPinnerService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                List<PinnedFileStat> pinnerStats = getPinnerStats();
                parcel2.writeNoException();
                parcel2.writeTypedList(pinnerStats, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IPinnerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPinnerService.DESCRIPTOR;
            }

            @Override // android.app.pinner.IPinnerService
            public List<PinnedFileStat> getPinnerStats() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPinnerService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(PinnedFileStat.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getPinnerStats_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DUMP, getCallingPid(), getCallingUid());
        }
    }
}
