package com.android.wm.shell.windowdecor;

import android.os.RemoteException;
import com.android.wm.shell.windowdecor.DragResizeInputListener;

/* loaded from: classes3.dex */
public final /* synthetic */ class DragResizeInputListener$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DragResizeInputListener$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                DragResizeInputListener dragResizeInputListener = (DragResizeInputListener) obj;
                dragResizeInputListener.getClass();
                try {
                    dragResizeInputListener.mWindowSession.remove(dragResizeInputListener.mClientToken);
                    dragResizeInputListener.mWindowSession.remove(dragResizeInputListener.mSinkClientToken);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
                dragResizeInputListener.mDecorationSurface.release();
                break;
            default:
                DragResizeInputListener.TaskResizeInputEventReceiver taskResizeInputEventReceiver = (DragResizeInputListener.TaskResizeInputEventReceiver) obj;
                taskResizeInputEventReceiver.mConsumeBatchEventScheduled = false;
                if (taskResizeInputEventReceiver.consumeBatchedInputEvents(taskResizeInputEventReceiver.mChoreographer.getFrameTimeNanos()) && !taskResizeInputEventReceiver.mConsumeBatchEventScheduled) {
                    taskResizeInputEventReceiver.mChoreographer.postCallback(0, taskResizeInputEventReceiver.mConsumeBatchEventRunnable, null);
                    taskResizeInputEventReceiver.mConsumeBatchEventScheduled = true;
                    break;
                }
                break;
        }
    }
}
