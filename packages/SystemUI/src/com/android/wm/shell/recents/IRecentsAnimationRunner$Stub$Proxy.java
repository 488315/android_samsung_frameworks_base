package com.android.wm.shell.recents;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.view.RemoteAnimationTarget;
import android.window.TaskSnapshot;
import android.window.TransitionInfo;

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
        Parcel parcelObtain = Parcel.obtain(this.mRemote);
        try {
            parcelObtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentsAnimationRunner");
            parcelObtain.writeIntArray(iArr);
            parcelObtain.writeTypedArray(taskSnapshotArr, 0);
            this.mRemote.transact(2, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }

    public final void onAnimationStart(IRecentsAnimationController iRecentsAnimationController, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, Rect rect, Rect rect2, Bundle bundle, TransitionInfo transitionInfo) {
        Parcel parcelObtain = Parcel.obtain(this.mRemote);
        try {
            parcelObtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentsAnimationRunner");
            parcelObtain.writeStrongInterface(iRecentsAnimationController);
            parcelObtain.writeTypedArray(remoteAnimationTargetArr, 0);
            parcelObtain.writeTypedArray(remoteAnimationTargetArr2, 0);
            parcelObtain.writeTypedObject(rect, 0);
            parcelObtain.writeTypedObject(rect2, 0);
            parcelObtain.writeTypedObject(bundle, 0);
            parcelObtain.writeTypedObject(transitionInfo, 0);
            this.mRemote.transact(3, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }
}
