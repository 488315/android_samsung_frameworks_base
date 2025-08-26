package com.android.systemui.screenshot.proxy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.systemui.screenshot.proxy.IOnDoneCallback;

/* loaded from: classes2.dex */
public interface IScreenshotProxy extends IInterface {
    void dismissKeyguard(IOnDoneCallback iOnDoneCallback);

    boolean isNotificationShadeExpanded();

    public abstract class Stub extends Binder implements IScreenshotProxy {
        public static final /* synthetic */ int $r8$clinit = 0;

        public class Proxy implements IScreenshotProxy {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.screenshot.proxy.IScreenshotProxy
            public final void dismissKeyguard(IOnDoneCallback iOnDoneCallback) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.screenshot.proxy.IScreenshotProxy");
                    parcelObtain.writeStrongInterface(iOnDoneCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.screenshot.proxy.IScreenshotProxy
            public final boolean isNotificationShadeExpanded() {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.screenshot.proxy.IScreenshotProxy");
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.screenshot.proxy.IScreenshotProxy");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            IOnDoneCallback proxy;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.screenshot.proxy.IScreenshotProxy");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.screenshot.proxy.IScreenshotProxy");
                return true;
            }
            if (i == 1) {
                boolean zIsNotificationShadeExpanded = ((ScreenshotProxyService$mBinder$1) this).isNotificationShadeExpanded();
                parcel2.writeNoException();
                parcel2.writeBoolean(zIsNotificationShadeExpanded);
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                IBinder strongBinder = parcel.readStrongBinder();
                if (strongBinder == null) {
                    proxy = null;
                } else {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.android.systemui.screenshot.proxy.IOnDoneCallback");
                    proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IOnDoneCallback)) ? new IOnDoneCallback.Stub.Proxy(strongBinder) : (IOnDoneCallback) iInterfaceQueryLocalInterface;
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
