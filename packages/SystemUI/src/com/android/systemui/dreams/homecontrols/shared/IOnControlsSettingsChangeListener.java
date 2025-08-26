package com.android.systemui.dreams.homecontrols.shared;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes2.dex */
public interface IOnControlsSettingsChangeListener extends IInterface {
    void onControlsSettingsChanged(ComponentName componentName, boolean z);

    public abstract class Stub extends Binder implements IOnControlsSettingsChangeListener {

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
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.dreams.homecontrols.shared.IOnControlsSettingsChangeListener");
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
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
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            ((IHomeControlsRemoteProxyExtKt$controlsSettings$1$listener$1) this).onControlsSettingsChanged(componentName, z);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
