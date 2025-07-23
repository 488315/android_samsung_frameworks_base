package com.android.wm.shell.recents;

import android.app.ActivityTaskManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Slog;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.PictureInPictureSurfaceTransaction;
import android.window.WindowAnimationState;
import android.window.WindowContainerTransaction;
import com.android.internal.os.IResultReceiver;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.transition.Transitions;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface IRecentsAnimationController extends IInterface {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IRecentsAnimationController {
        public Stub() {
            attachInterface(this, "com.android.wm.shell.recents.IRecentsAnimationController");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.wm.shell.recents.IRecentsAnimationController");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.wm.shell.recents.IRecentsAnimationController");
                return true;
            }
            switch (i) {
                case 1:
                    final int readInt = parcel.readInt();
                    final PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction = (PictureInPictureSurfaceTransaction) parcel.readTypedObject(PictureInPictureSurfaceTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    final RecentsTransitionHandler.RecentsController recentsController = (RecentsTransitionHandler.RecentsController) this;
                    RecentsTransitionHandler.this.mExecutor.execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            RecentsTransitionHandler.RecentsController recentsController2 = RecentsTransitionHandler.RecentsController.this;
                            int i3 = readInt;
                            PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction2 = pictureInPictureSurfaceTransaction;
                            int i4 = RecentsTransitionHandler.RecentsController.$r8$clinit;
                            recentsController2.getClass();
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 518525023057359864L, 53, Long.valueOf(recentsController2.mInstanceId), Long.valueOf(i3), Boolean.valueOf(recentsController2.mFinishCB != null));
                            }
                            if (recentsController2.mFinishCB == null) {
                                return;
                            }
                            recentsController2.mPipTransaction = pictureInPictureSurfaceTransaction2;
                            recentsController2.mPipTaskId = i3;
                        }
                    });
                    parcel2.writeNoException();
                    return true;
                case 2:
                    final boolean readBoolean = parcel.readBoolean();
                    final boolean readBoolean2 = parcel.readBoolean();
                    final IResultReceiver asInterface = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    final RecentsTransitionHandler.RecentsController recentsController2 = (RecentsTransitionHandler.RecentsController) this;
                    RecentsTransitionHandler.this.mExecutor.execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            RecentsTransitionHandler.RecentsController recentsController3 = RecentsTransitionHandler.RecentsController.this;
                            boolean z = readBoolean;
                            boolean z2 = readBoolean2;
                            IResultReceiver iResultReceiver = asInterface;
                            int i3 = RecentsTransitionHandler.RecentsController.$r8$clinit;
                            recentsController3.finishInner(z, z2, iResultReceiver, "requested");
                        }
                    });
                    parcel2.writeNoException();
                    return true;
                case 3:
                    final boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    final RecentsTransitionHandler.RecentsController recentsController3 = (RecentsTransitionHandler.RecentsController) this;
                    final int i3 = 1;
                    RecentsTransitionHandler.this.mExecutor.execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i3) {
                                case 0:
                                    recentsController3.mWillForceFinishToHome = readBoolean3;
                                    break;
                                case 1:
                                    RecentsTransitionHandler.RecentsController recentsController4 = recentsController3;
                                    boolean z = readBoolean3;
                                    Transitions.TransitionFinishCallback transitionFinishCallback = recentsController4.mFinishCB;
                                    if (transitionFinishCallback != null && z) {
                                        int displayId = recentsController4.mInfo.getRootCount() > 0 ? recentsController4.mInfo.getRoot(0).getDisplayId() : 0;
                                        try {
                                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -4169896698222200348L, 1, Long.valueOf(recentsController4.mInstanceId));
                                            }
                                            ActivityTaskManager.getService().focusTopTask(displayId);
                                            break;
                                        } catch (RemoteException e) {
                                            Slog.e("RecentsTransitionHandler", "Failed to set focused task", e);
                                            return;
                                        }
                                    } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -7556875887646265289L, 15, Boolean.valueOf(transitionFinishCallback != null), Boolean.valueOf(z));
                                        break;
                                    }
                                    break;
                                default:
                                    recentsController3.mWillFinishToHome = readBoolean3;
                                    break;
                            }
                        }
                    });
                    parcel2.writeNoException();
                    return true;
                case 4:
                    final boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    final RecentsTransitionHandler.RecentsController recentsController4 = (RecentsTransitionHandler.RecentsController) this;
                    final int i4 = 2;
                    RecentsTransitionHandler.this.mExecutor.execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i4) {
                                case 0:
                                    recentsController4.mWillForceFinishToHome = readBoolean4;
                                    break;
                                case 1:
                                    RecentsTransitionHandler.RecentsController recentsController42 = recentsController4;
                                    boolean z = readBoolean4;
                                    Transitions.TransitionFinishCallback transitionFinishCallback = recentsController42.mFinishCB;
                                    if (transitionFinishCallback != null && z) {
                                        int displayId = recentsController42.mInfo.getRootCount() > 0 ? recentsController42.mInfo.getRoot(0).getDisplayId() : 0;
                                        try {
                                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -4169896698222200348L, 1, Long.valueOf(recentsController42.mInstanceId));
                                            }
                                            ActivityTaskManager.getService().focusTopTask(displayId);
                                            break;
                                        } catch (RemoteException e) {
                                            Slog.e("RecentsTransitionHandler", "Failed to set focused task", e);
                                            return;
                                        }
                                    } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -7556875887646265289L, 15, Boolean.valueOf(transitionFinishCallback != null), Boolean.valueOf(z));
                                        break;
                                    }
                                    break;
                                default:
                                    recentsController4.mWillFinishToHome = readBoolean4;
                                    break;
                            }
                        }
                    });
                    parcel2.writeNoException();
                    return true;
                case 5:
                    final boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    final RecentsTransitionHandler.RecentsController recentsController5 = (RecentsTransitionHandler.RecentsController) this;
                    final int i5 = 0;
                    RecentsTransitionHandler.this.mExecutor.execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i5) {
                                case 0:
                                    recentsController5.mWillForceFinishToHome = readBoolean5;
                                    break;
                                case 1:
                                    RecentsTransitionHandler.RecentsController recentsController42 = recentsController5;
                                    boolean z = readBoolean5;
                                    Transitions.TransitionFinishCallback transitionFinishCallback = recentsController42.mFinishCB;
                                    if (transitionFinishCallback != null && z) {
                                        int displayId = recentsController42.mInfo.getRootCount() > 0 ? recentsController42.mInfo.getRoot(0).getDisplayId() : 0;
                                        try {
                                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -4169896698222200348L, 1, Long.valueOf(recentsController42.mInstanceId));
                                            }
                                            ActivityTaskManager.getService().focusTopTask(displayId);
                                            break;
                                        } catch (RemoteException e) {
                                            Slog.e("RecentsTransitionHandler", "Failed to set focused task", e);
                                            return;
                                        }
                                    } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -7556875887646265289L, 15, Boolean.valueOf(transitionFinishCallback != null), Boolean.valueOf(z));
                                        break;
                                    }
                                    break;
                                default:
                                    recentsController5.mWillFinishToHome = readBoolean5;
                                    break;
                            }
                        }
                    });
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    RecentsTransitionHandler.RecentsController recentsController6 = (RecentsTransitionHandler.RecentsController) this;
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -8743103462975981981L, 1, Long.valueOf(recentsController6.mInstanceId));
                    }
                    RecentsTransitionHandler.this.mExecutor.execute(new RecentsTransitionHandler$$ExternalSyntheticLambda0(recentsController6, 3));
                    parcel2.writeNoException();
                    return true;
                case 7:
                    final RemoteAnimationTarget[] remoteAnimationTargetArr = (RemoteAnimationTarget[]) parcel.createTypedArray(RemoteAnimationTarget.CREATOR);
                    final WindowAnimationState[] windowAnimationStateArr = (WindowAnimationState[]) parcel.createTypedArray(WindowAnimationState.CREATOR);
                    parcel.enforceNoDataAvail();
                    final RecentsTransitionHandler.RecentsController recentsController7 = (RecentsTransitionHandler.RecentsController) this;
                    RecentsTransitionHandler.this.mExecutor.execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            final RecentsTransitionHandler.RecentsController recentsController8 = RecentsTransitionHandler.RecentsController.this;
                            RemoteAnimationTarget[] remoteAnimationTargetArr2 = remoteAnimationTargetArr;
                            WindowAnimationState[] windowAnimationStateArr2 = windowAnimationStateArr;
                            int i6 = RecentsTransitionHandler.RecentsController.$r8$clinit;
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 7080950910277792428L, 1, Long.valueOf(recentsController8.mInstanceId));
                            }
                            if (recentsController8.mTakeoverHandler == null) {
                                Slog.e("RecentsTransitionHandler", "Tried to hand off an animation without a valid takeover handler.");
                                return;
                            }
                            if (remoteAnimationTargetArr2.length != windowAnimationStateArr2.length) {
                                Slog.e("RecentsTransitionHandler", "Tried to hand off an animation, but the number of targets (" + remoteAnimationTargetArr2.length + ") doesn't match the number of states (" + windowAnimationStateArr2.length + ")");
                                return;
                            }
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 8368627614333417427L, 21, Long.valueOf(recentsController8.mInstanceId), Long.valueOf(windowAnimationStateArr2.length), Long.valueOf(recentsController8.mInfo.getChanges().size()));
                            }
                            int size = recentsController8.mInfo.getChanges().size();
                            WindowAnimationState[] windowAnimationStateArr3 = new WindowAnimationState[size];
                            for (int i7 = 0; i7 < remoteAnimationTargetArr2.length; i7++) {
                                windowAnimationStateArr3[size - remoteAnimationTargetArr2[i7].prefixOrderIndex] = windowAnimationStateArr2[i7];
                            }
                            final Transitions.TransitionFinishCallback transitionFinishCallback = recentsController8.mFinishCB;
                            recentsController8.mFinishCB = null;
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 5597521808764776610L, 5, Long.valueOf(recentsController8.mInstanceId), Long.valueOf(size));
                            }
                            recentsController8.mTakeoverHandler.takeOverAnimation(recentsController8.mTransition, recentsController8.mInfo, new SurfaceControl.Transaction(), new Transitions.TransitionFinishCallback() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda9
                                @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                                public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                                    int i8 = RecentsTransitionHandler.RecentsController.$r8$clinit;
                                    RecentsTransitionHandler.RecentsController recentsController9 = RecentsTransitionHandler.RecentsController.this;
                                    recentsController9.getClass();
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 6127017363858667288L, 1, Long.valueOf(recentsController9.mInstanceId));
                                    }
                                    recentsController9.mFinishCB = transitionFinishCallback;
                                    recentsController9.finishInner(true, false, null, "takeOverAnimation");
                                }
                            }, windowAnimationStateArr3);
                        }
                    });
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
