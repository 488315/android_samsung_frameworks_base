package com.android.wm.shell.recents;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.view.RemoteAnimationTarget;
import android.window.TaskSnapshot;
import android.window.TransitionInfo;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class IRecentsAnimationRunner$Stub$Proxy implements IRecentsAnimationRunner {
    public final IBinder mRemote;

    public IRecentsAnimationRunner$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final void onAnimationCanceled(int[] iArr, TaskSnapshot[] taskSnapshotArr) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentsAnimationRunner");
            obtain.writeIntArray(iArr);
            obtain.writeTypedArray(taskSnapshotArr, 0);
            this.mRemote.transact(2, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final void onAnimationStart(IRecentsAnimationController iRecentsAnimationController, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, Rect rect, Rect rect2, Bundle bundle, TransitionInfo transitionInfo) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentsAnimationRunner");
            obtain.writeStrongInterface(iRecentsAnimationController);
            obtain.writeTypedArray(remoteAnimationTargetArr, 0);
            obtain.writeTypedArray(remoteAnimationTargetArr2, 0);
            obtain.writeTypedObject(rect, 0);
            obtain.writeTypedObject(rect2, 0);
            obtain.writeTypedObject(bundle, 0);
            obtain.writeTypedObject(transitionInfo, 0);
            this.mRemote.transact(3, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }
}
