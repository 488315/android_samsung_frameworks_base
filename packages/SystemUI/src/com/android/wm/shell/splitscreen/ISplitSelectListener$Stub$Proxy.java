package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

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
        Parcel parcelObtain = Parcel.obtain(this.mRemote);
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.android.wm.shell.splitscreen.ISplitSelectListener");
            parcelObtain.writeTypedObject(runningTaskInfo, 0);
            parcelObtain.writeInt(i);
            parcelObtain.writeTypedObject(rect, 0);
            this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readBoolean();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
