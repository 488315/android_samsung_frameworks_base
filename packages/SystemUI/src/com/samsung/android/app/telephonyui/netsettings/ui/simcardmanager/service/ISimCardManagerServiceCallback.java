package com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.util.Log;
import com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$simCardCallback$1;

/* loaded from: classes4.dex */
public interface ISimCardManagerServiceCallback extends IInterface {

    public abstract class Stub extends Binder implements ISimCardManagerServiceCallback {

        public class Proxy implements ISimCardManagerServiceCallback {
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
            if (i != 1) {
                if (i != 1598968902) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeString("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerServiceCallback");
                return true;
            }
            parcel.enforceInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerServiceCallback");
            String string = parcel.readString();
            boolean z = parcel.readInt() != 0;
            Log.d("SimCardManagerServiceProvider$ServiceBindHelper", "mSimCardManagerServiceCallback: requestResult action = " + string + ", success = " + z);
            if (SimCardManagerServiceProvider.sSimCardManagerServiceCallback != null && !SimCardManagerServiceProvider.mIsServiceClose) {
                SimInfoRepositoryImpl$simCardCallback$1 simInfoRepositoryImpl$simCardCallback$1 = SimCardManagerServiceProvider.sSimCardManagerServiceCallback;
                simInfoRepositoryImpl$simCardCallback$1.getClass();
                Log.d("MULTISIM-PROD-REPO", "registerSimCardManagerCallback : requestResult action = " + string + ", success = " + z);
                if ("dataSlotChangedFinish".equals(string)) {
                    simInfoRepositoryImpl$simCardCallback$1.this$0.sendToUpdateDataHandler(1000, 0L);
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
