package com.android.systemui.dreams.homecontrols.shared;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface IOnControlsSettingsChangeListener extends IInterface {
    void onControlsSettingsChanged(ComponentName componentName, boolean z);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IOnControlsSettingsChangeListener {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Proxy implements IOnControlsSettingsChangeListener {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener
            public final void onControlsSettingsChanged(ComponentName componentName, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener");
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            ((IHomeControlsRemoteProxyExtKt$controlsSettings$1$listener$1) this).onControlsSettingsChanged(componentName, readBoolean);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
