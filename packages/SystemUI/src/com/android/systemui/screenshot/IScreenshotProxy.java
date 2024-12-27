package com.android.systemui.screenshot;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.systemui.screenshot.IOnDoneCallback;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface IScreenshotProxy extends IInterface {
    void dismissKeyguard(IOnDoneCallback iOnDoneCallback);

    boolean isNotificationShadeExpanded();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class Stub extends Binder implements IScreenshotProxy {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Proxy implements IScreenshotProxy {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.screenshot.IScreenshotProxy
            public final void dismissKeyguard(IOnDoneCallback iOnDoneCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.screenshot.IScreenshotProxy");
                    obtain.writeStrongInterface(iOnDoneCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.screenshot.IScreenshotProxy
            public final boolean isNotificationShadeExpanded() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.screenshot.IScreenshotProxy");
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.screenshot.IScreenshotProxy");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            IOnDoneCallback proxy;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.screenshot.IScreenshotProxy");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.screenshot.IScreenshotProxy");
                return true;
            }
            if (i == 1) {
                boolean isNotificationShadeExpanded = ((ScreenshotProxyService$mBinder$1) this).isNotificationShadeExpanded();
                parcel2.writeNoException();
                parcel2.writeBoolean(isNotificationShadeExpanded);
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    proxy = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.systemui.screenshot.IOnDoneCallback");
                    proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof IOnDoneCallback)) ? new IOnDoneCallback.Stub.Proxy(readStrongBinder) : (IOnDoneCallback) queryLocalInterface;
                }
                parcel.enforceNoDataAvail();
                ((ScreenshotProxyService$mBinder$1) this).dismissKeyguard(proxy);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
