package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ISplitSelectListener$Stub$Proxy implements IInterface {
    public final IBinder mRemote;

    public ISplitSelectListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final boolean onRequestSplitSelect(ActivityManager.RunningTaskInfo runningTaskInfo, int i, Rect rect) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.android.wm.shell.splitscreen.ISplitSelectListener");
            obtain.writeTypedObject(runningTaskInfo, 0);
            obtain.writeInt(i);
            obtain.writeTypedObject(rect, 0);
            this.mRemote.transact(1, obtain, obtain2, 0);
            obtain2.readException();
            return obtain2.readBoolean();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
