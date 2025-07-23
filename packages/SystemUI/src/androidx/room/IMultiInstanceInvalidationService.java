package androidx.room;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import androidx.room.IMultiInstanceInvalidationCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface IMultiInstanceInvalidationService extends IInterface {
    public static final String DESCRIPTOR = "androidx$room$IMultiInstanceInvalidationService".replace('$', '.');

    void broadcastInvalidation(String[] strArr, int i);

    int registerCallback(IMultiInstanceInvalidationCallback iMultiInstanceInvalidationCallback, String str);

    void unregisterCallback(IMultiInstanceInvalidationCallback iMultiInstanceInvalidationCallback, int i);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IMultiInstanceInvalidationService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Proxy implements IMultiInstanceInvalidationService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // androidx.room.IMultiInstanceInvalidationService
            public final void broadcastInvalidation(String[] strArr, int i) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiInstanceInvalidationService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // androidx.room.IMultiInstanceInvalidationService
            public final int registerCallback(IMultiInstanceInvalidationCallback iMultiInstanceInvalidationCallback, String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiInstanceInvalidationService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMultiInstanceInvalidationCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // androidx.room.IMultiInstanceInvalidationService
            public final void unregisterCallback(IMultiInstanceInvalidationCallback iMultiInstanceInvalidationCallback, int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiInstanceInvalidationService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMultiInstanceInvalidationCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IMultiInstanceInvalidationService.DESCRIPTOR);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = IMultiInstanceInvalidationService.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            IMultiInstanceInvalidationCallback iMultiInstanceInvalidationCallback = null;
            if (i == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface(IMultiInstanceInvalidationCallback.DESCRIPTOR);
                    iMultiInstanceInvalidationCallback = (queryLocalInterface == null || !(queryLocalInterface instanceof IMultiInstanceInvalidationCallback)) ? new IMultiInstanceInvalidationCallback.Stub.Proxy(readStrongBinder) : (IMultiInstanceInvalidationCallback) queryLocalInterface;
                }
                int registerCallback = ((MultiInstanceInvalidationService$binder$1) this).registerCallback(iMultiInstanceInvalidationCallback, parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(registerCallback);
                return true;
            }
            if (i != 2) {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                ((MultiInstanceInvalidationService$binder$1) this).broadcastInvalidation(parcel.createStringArray(), parcel.readInt());
                return true;
            }
            IBinder readStrongBinder2 = parcel.readStrongBinder();
            if (readStrongBinder2 != null) {
                IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface(IMultiInstanceInvalidationCallback.DESCRIPTOR);
                iMultiInstanceInvalidationCallback = (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof IMultiInstanceInvalidationCallback)) ? new IMultiInstanceInvalidationCallback.Stub.Proxy(readStrongBinder2) : (IMultiInstanceInvalidationCallback) queryLocalInterface2;
            }
            ((MultiInstanceInvalidationService$binder$1) this).unregisterCallback(iMultiInstanceInvalidationCallback, parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
