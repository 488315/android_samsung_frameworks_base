package com.android.wm.shell.taskview;

import android.graphics.Rect;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class TaskViewRepository {
    public final ArrayList mTaskViews = new ArrayList();

    public class TaskViewState {
        public final Rect mBounds = new Rect();
        public final WeakReference mTaskView;
        public boolean mVisible;

        public TaskViewState(TaskViewTaskController taskViewTaskController) {
            this.mTaskView = new WeakReference(taskViewTaskController);
        }
    }

    public final int findAndPrune(TaskViewTaskController taskViewTaskController) {
        for (int size = this.mTaskViews.size() - 1; size >= 0; size--) {
            TaskViewTaskController taskViewTaskController2 = (TaskViewTaskController) ((TaskViewState) this.mTaskViews.get(size)).mTaskView.get();
            if (taskViewTaskController2 == null) {
                this.mTaskViews.remove(size);
            } else if (taskViewTaskController2 == taskViewTaskController) {
                return size;
            }
        }
        return -1;
    }
}
