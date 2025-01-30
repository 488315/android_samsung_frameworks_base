package com.android.server.wm;

import android.util.ArraySet;
import android.util.SparseArray;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class SnapshotController {
    public int mActivatedType;
    public final ActivitySnapshotController mActivitySnapshotController;
    public final SnapshotPersistQueue mSnapshotPersistQueue;
    public final TaskSnapshotController mTaskSnapshotController;
    public final ArraySet mTmpCloseTasks = new ArraySet();
    public final ArraySet mTmpOpenTasks = new ArraySet();
    public final SparseArray mTmpOpenCloseRecord = new SparseArray();
    public final ArraySet mTmpAnalysisRecord = new ArraySet();
    public final SparseArray mTransitionStateConsumer = new SparseArray();
    public final ActivityOrderCheck mActivityOrderCheck = new ActivityOrderCheck();
    public final ActivityOrderCheck.AnalysisResult mResultHandler = new ActivityOrderCheck.AnalysisResult() { // from class: com.android.server.wm.SnapshotController$$ExternalSyntheticLambda0
        @Override // com.android.server.wm.SnapshotController.ActivityOrderCheck.AnalysisResult
        public final void onCheckResult(int i, ActivityRecord activityRecord, ActivityRecord activityRecord2) {
            SnapshotController.this.lambda$new$0(i, activityRecord, activityRecord2);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, ActivityRecord activityRecord, ActivityRecord activityRecord2) {
        addTransitionRecord(i, true, activityRecord2);
        addTransitionRecord(i, false, activityRecord);
    }

    public class ActivityOrderCheck {
        public ActivityRecord mCloseActivity;
        public int mCloseIndex;
        public ActivityRecord mOpenActivity;
        public int mOpenIndex;

        public interface AnalysisResult {
            void onCheckResult(int i, ActivityRecord activityRecord, ActivityRecord activityRecord2);
        }

        public ActivityOrderCheck() {
            this.mOpenIndex = -1;
            this.mCloseIndex = -1;
        }

        public final void reset() {
            this.mOpenActivity = null;
            this.mCloseActivity = null;
            this.mOpenIndex = -1;
            this.mCloseIndex = -1;
        }

        public final void setTarget(boolean z, ActivityRecord activityRecord, int i) {
            if (z) {
                this.mOpenActivity = activityRecord;
                this.mOpenIndex = i;
            } else {
                this.mCloseActivity = activityRecord;
                this.mCloseIndex = i;
            }
        }

        public void analysisOrder(ArraySet arraySet, ArraySet arraySet2, Task task, AnalysisResult analysisResult) {
            int size = arraySet.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                ActivityRecord activityRecord = (ActivityRecord) arraySet.valueAt(size);
                if (activityRecord.getTask() == task) {
                    setTarget(false, activityRecord, task.mChildren.indexOf(activityRecord));
                    break;
                }
                size--;
            }
            int size2 = arraySet2.size() - 1;
            while (true) {
                if (size2 < 0) {
                    break;
                }
                ActivityRecord activityRecord2 = (ActivityRecord) arraySet2.valueAt(size2);
                if (activityRecord2.getTask() == task) {
                    setTarget(true, activityRecord2, task.mChildren.indexOf(activityRecord2));
                    break;
                }
                size2--;
            }
            int i = this.mOpenIndex;
            int i2 = this.mCloseIndex;
            if (i > i2 && i2 != -1) {
                analysisResult.onCheckResult(1, this.mCloseActivity, this.mOpenActivity);
            } else if (i < i2 && i != -1) {
                analysisResult.onCheckResult(2, this.mCloseActivity, this.mOpenActivity);
            }
            reset();
        }
    }

    public final void addTransitionRecord(int i, boolean z, WindowContainer windowContainer) {
        TransitionState transitionState = (TransitionState) this.mTmpOpenCloseRecord.get(i);
        if (transitionState == null) {
            transitionState = new TransitionState();
            this.mTmpOpenCloseRecord.set(i, transitionState);
        }
        transitionState.addParticipant(windowContainer, z);
        this.mTmpAnalysisRecord.add(Integer.valueOf(i));
    }

    public final void clearRecord() {
        this.mTmpOpenCloseRecord.clear();
        this.mTmpAnalysisRecord.clear();
    }

    public class TransitionState {
        public final ArraySet mOpenParticipant = new ArraySet();
        public final ArraySet mCloseParticipant = new ArraySet();

        public void addParticipant(WindowContainer windowContainer, boolean z) {
            (z ? this.mOpenParticipant : this.mCloseParticipant).add(windowContainer);
        }

        public ArraySet getParticipant(boolean z) {
            return z ? this.mOpenParticipant : this.mCloseParticipant;
        }
    }

    public SnapshotController(WindowManagerService windowManagerService) {
        SnapshotPersistQueue snapshotPersistQueue = new SnapshotPersistQueue();
        this.mSnapshotPersistQueue = snapshotPersistQueue;
        this.mTaskSnapshotController = new TaskSnapshotController(windowManagerService, snapshotPersistQueue);
        this.mActivitySnapshotController = new ActivitySnapshotController(windowManagerService, snapshotPersistQueue);
    }

    public void registerTransitionStateConsumer(int i, Consumer consumer) {
        ArrayList arrayList = (ArrayList) this.mTransitionStateConsumer.get(i);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.mTransitionStateConsumer.set(i, arrayList);
        }
        if (!arrayList.contains(consumer)) {
            arrayList.add(consumer);
        }
        this.mActivatedType = i | this.mActivatedType;
    }

    public final boolean hasTransitionStateConsumer(int i) {
        return (this.mActivatedType & i) != 0;
    }

    public void systemReady() {
        this.mSnapshotPersistQueue.systemReady();
        this.mTaskSnapshotController.systemReady();
        this.mActivitySnapshotController.systemReady();
    }

    public void setPause(boolean z) {
        this.mSnapshotPersistQueue.setPaused(z);
    }

    public void onAppRemoved(ActivityRecord activityRecord) {
        this.mTaskSnapshotController.onAppRemoved(activityRecord);
        this.mActivitySnapshotController.onAppRemoved(activityRecord);
    }

    public void onAppDied(ActivityRecord activityRecord) {
        this.mTaskSnapshotController.onAppDied(activityRecord);
        this.mActivitySnapshotController.onAppDied(activityRecord);
    }

    public void notifyAppVisibilityChanged(ActivityRecord activityRecord, boolean z) {
        Task task;
        if (z || !hasTransitionStateConsumer(8) || (task = activityRecord.getTask()) == null || task.isVisibleRequested()) {
            return;
        }
        addTransitionRecord(8, false, task);
        this.mActivitySnapshotController.preTransitionStart();
        notifyTransition(8);
        this.mActivitySnapshotController.postTransitionStart();
        clearRecord();
    }

    public void onTransitionStarting(DisplayContent displayContent) {
        handleAppTransition(displayContent.mClosingApps, displayContent.mOpeningApps);
    }

    public void handleAppTransition(ArraySet arraySet, ArraySet arraySet2) {
        if (this.mActivatedType == 0) {
            return;
        }
        analysisTransition(arraySet, arraySet2);
        this.mActivitySnapshotController.preTransitionStart();
        Iterator it = this.mTmpAnalysisRecord.iterator();
        while (it.hasNext()) {
            notifyTransition(((Integer) it.next()).intValue());
        }
        this.mActivitySnapshotController.postTransitionStart();
        clearRecord();
    }

    public final void notifyTransition(int i) {
        TransitionState transitionState = (TransitionState) this.mTmpOpenCloseRecord.get(i);
        Iterator it = ((ArrayList) this.mTransitionStateConsumer.get(i)).iterator();
        while (it.hasNext()) {
            ((Consumer) it.next()).accept(transitionState);
        }
    }

    public final void analysisTransition(ArraySet arraySet, ArraySet arraySet2) {
        getParticipantTasks(arraySet, this.mTmpCloseTasks, false);
        getParticipantTasks(arraySet2, this.mTmpOpenTasks, true);
        for (int size = this.mTmpCloseTasks.size() - 1; size >= 0; size--) {
            Task task = (Task) this.mTmpCloseTasks.valueAt(size);
            if (this.mTmpOpenTasks.contains(task)) {
                if (hasTransitionStateConsumer(1) || hasTransitionStateConsumer(2)) {
                    this.mActivityOrderCheck.analysisOrder(arraySet, arraySet2, task, this.mResultHandler);
                }
            } else if (hasTransitionStateConsumer(8)) {
                addTransitionRecord(8, false, task);
            }
        }
        if (hasTransitionStateConsumer(4)) {
            for (int size2 = this.mTmpOpenTasks.size() - 1; size2 >= 0; size2--) {
                WindowContainer windowContainer = (Task) this.mTmpOpenTasks.valueAt(size2);
                if (!this.mTmpCloseTasks.contains(windowContainer)) {
                    addTransitionRecord(4, true, windowContainer);
                }
            }
        }
        this.mTmpCloseTasks.clear();
        this.mTmpOpenTasks.clear();
    }

    public final void getParticipantTasks(ArraySet arraySet, ArraySet arraySet2, boolean z) {
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            ActivityRecord activityRecord = (ActivityRecord) arraySet.valueAt(size);
            Task task = activityRecord.getTask();
            if (task != null && z == activityRecord.isVisibleRequested()) {
                arraySet2.add(task);
            }
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        this.mTaskSnapshotController.dump(printWriter, str);
        this.mActivitySnapshotController.dump(printWriter, str);
    }
}
