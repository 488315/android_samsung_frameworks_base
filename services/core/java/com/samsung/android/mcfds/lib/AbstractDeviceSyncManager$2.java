package com.samsung.android.mcfds.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;

import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AbstractDeviceSyncManager$2 extends Binder implements IInterface {
    public final /* synthetic */ DeviceSyncManager this$0;

    public AbstractDeviceSyncManager$2(DeviceSyncManager deviceSyncManager) {
        this.this$0 = deviceSyncManager;
        attachInterface(this, "com.samsung.android.mcfds.lib.common.ISimpleCallback");
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.samsung.android.mcfds.lib.common.ISimpleCallback");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.samsung.android.mcfds.lib.common.ISimpleCallback");
            return true;
        }
        if (i != 1) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        Message message =
                (Message) (parcel.readInt() != 0 ? Message.CREATOR.createFromParcel(parcel) : null);
        if (message.what == 10000) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("mServiceStateListener - "),
                    message.arg1,
                    "[MCF_DS_LIB]_DeviceSyncManager");
            if (message.arg1 == 1) {
                DeviceSyncManager deviceSyncManager = this.this$0;
                deviceSyncManager.mServiceState = 5;
                DeviceSyncManager.access$200(deviceSyncManager, 5);
            } else {
                DeviceSyncManager deviceSyncManager2 = this.this$0;
                if (deviceSyncManager2.mServiceState != 5) {
                    deviceSyncManager2.mServiceState = 4;
                } else {
                    deviceSyncManager2.mServiceState = 4;
                    DeviceSyncManager.access$200(deviceSyncManager2, 4);
                }
            }
        }
        return true;
    }
}
