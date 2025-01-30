package com.samsung.android.app.telephonyui.netsettings.p044ui.simcardmanager.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.util.Log;
import com.android.systemui.settings.multisim.MultiSIMController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ISimCardManagerServiceCallback extends IInterface {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Stub extends Binder implements ISimCardManagerServiceCallback {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Proxy implements ISimCardManagerServiceCallback {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerServiceCallback");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            MultiSIMController multiSIMController;
            MultiSIMController.HandlerC240412 handlerC240412;
            if (i != 1) {
                if (i != 1598968902) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeString("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerServiceCallback");
                return true;
            }
            parcel.enforceInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerServiceCallback");
            String readString = parcel.readString();
            boolean z = parcel.readInt() != 0;
            Log.d("SimCardManagerServiceProvider$ServiceBindHelper", "mSimCardManagerServiceCallback: requestResult action = " + readString + ", success = " + z);
            if (SimCardManagerServiceProvider.sSimCardManagerServiceCallback != null && !SimCardManagerServiceProvider.mIsServiceClose) {
                MultiSIMController.C240513 c240513 = SimCardManagerServiceProvider.sSimCardManagerServiceCallback;
                c240513.getClass();
                Log.d("MultiSIMController", "registerSimCardManagerCallback : requestResult action = " + readString + ", success = " + z);
                if ("dataSlotChangedFinish".equals(readString) && (handlerC240412 = (multiSIMController = MultiSIMController.this).mUIHandler) != null) {
                    handlerC240412.removeMessages(1000);
                    MultiSIMController.HandlerC240412 handlerC2404122 = multiSIMController.mUIHandler;
                    handlerC2404122.sendMessage(handlerC2404122.obtainMessage(1000));
                }
            }
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
