package android.hardware.camera2.utils;

import android.hardware.camera2.utils.TaskDrainer;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class TaskSingleDrainer {
    private final Object mSingleTask = new Object();
    private final TaskDrainer<Object> mTaskDrainer;

    public TaskSingleDrainer(Executor executor, TaskDrainer.DrainListener drainListener) {
        this.mTaskDrainer = new TaskDrainer<>(executor, drainListener);
    }

    public TaskSingleDrainer(Executor executor, TaskDrainer.DrainListener drainListener, String str) {
        this.mTaskDrainer = new TaskDrainer<>(executor, drainListener, str);
    }

    public void taskStarted() {
        this.mTaskDrainer.taskStarted(this.mSingleTask);
    }

    public void beginDrain() {
        this.mTaskDrainer.beginDrain();
    }

    public void taskFinished() {
        this.mTaskDrainer.taskFinished(this.mSingleTask);
    }
}
