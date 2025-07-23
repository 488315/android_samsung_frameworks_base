package com.android.systemui.dreams.homecontrols.shared;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener;
import com.android.systemui.dreams.homecontrols.system.HomeControlsRemoteServiceBinder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface IHomeControlsRemoteProxy extends IInterface {
    void registerListenerForCurrentUser(IOnControlsSettingsChangeListener iOnControlsSettingsChangeListener);

    void unregisterListenerForCurrentUser(IOnControlsSettingsChangeListener iOnControlsSettingsChangeListener);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IHomeControlsRemoteProxy {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxy");
                    obtain.writeStrongInterface(iOnControlsSettingsChangeListener);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxy
            public final void unregisterListenerForCurrentUser(IOnControlsSettingsChangeListener iOnControlsSettingsChangeListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxy");
                    obtain.writeStrongInterface(iOnControlsSettingsChangeListener);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
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
            IOnControlsSettingsChangeListener iOnControlsSettingsChangeListener = null;
            if (i == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener");
                    iOnControlsSettingsChangeListener = (queryLocalInterface == null || !(queryLocalInterface instanceof IOnControlsSettingsChangeListener)) ? new IOnControlsSettingsChangeListener.Stub.Proxy(readStrongBinder) : (IOnControlsSettingsChangeListener) queryLocalInterface;
                }
                parcel.enforceNoDataAvail();
                ((HomeControlsRemoteServiceBinder) this).registerListenerForCurrentUser(iOnControlsSettingsChangeListener);
                return true;
            }
            if (i != 2) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            IBinder readStrongBinder2 = parcel.readStrongBinder();
            if (readStrongBinder2 != null) {
                IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener");
                iOnControlsSettingsChangeListener = (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof IOnControlsSettingsChangeListener)) ? new IOnControlsSettingsChangeListener.Stub.Proxy(readStrongBinder2) : (IOnControlsSettingsChangeListener) queryLocalInterface2;
            }
            parcel.enforceNoDataAvail();
            ((HomeControlsRemoteServiceBinder) this).unregisterListenerForCurrentUser(iOnControlsSettingsChangeListener);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
