package com.android.systemui.dreams.homecontrols.shared;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener;
import com.android.systemui.dreams.homecontrols.system.HomeControlsRemoteServiceBinder;

/* loaded from: classes2.dex */
public interface IHomeControlsRemoteProxy extends IInterface {
    void registerListenerForCurrentUser(IOnControlsSettingsChangeListener iOnControlsSettingsChangeListener);

    void unregisterListenerForCurrentUser(IOnControlsSettingsChangeListener iOnControlsSettingsChangeListener);

    public abstract class Stub extends Binder implements IHomeControlsRemoteProxy {
        public static final /* synthetic */ int $r8$clinit = 0;

        public class Proxy implements IHomeControlsRemoteProxy {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxy
            public final void registerListenerForCurrentUser(IOnControlsSettingsChangeListener iOnControlsSettingsChangeListener) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxy");
                    parcelObtain.writeStrongInterface(iOnControlsSettingsChangeListener);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxy
            public final void unregisterListenerForCurrentUser(IOnControlsSettingsChangeListener iOnControlsSettingsChangeListener) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxy");
                    parcelObtain.writeStrongInterface(iOnControlsSettingsChangeListener);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxy");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxy");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxy");
                return true;
            }
            IOnControlsSettingsChangeListener proxy = null;
            if (i == 1) {
                IBinder strongBinder = parcel.readStrongBinder();
                if (strongBinder != null) {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener");
                    proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IOnControlsSettingsChangeListener)) ? new IOnControlsSettingsChangeListener.Stub.Proxy(strongBinder) : (IOnControlsSettingsChangeListener) iInterfaceQueryLocalInterface;
                }
                parcel.enforceNoDataAvail();
                ((HomeControlsRemoteServiceBinder) this).registerListenerForCurrentUser(proxy);
                return true;
            }
            if (i != 2) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            IBinder strongBinder2 = parcel.readStrongBinder();
            if (strongBinder2 != null) {
                IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener");
                proxy = (iInterfaceQueryLocalInterface2 == null || !(iInterfaceQueryLocalInterface2 instanceof IOnControlsSettingsChangeListener)) ? new IOnControlsSettingsChangeListener.Stub.Proxy(strongBinder2) : (IOnControlsSettingsChangeListener) iInterfaceQueryLocalInterface2;
            }
            parcel.enforceNoDataAvail();
            ((HomeControlsRemoteServiceBinder) this).unregisterListenerForCurrentUser(proxy);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
