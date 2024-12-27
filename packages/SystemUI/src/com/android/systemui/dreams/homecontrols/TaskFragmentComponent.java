package com.android.systemui.dreams.homecontrols;

import android.app.Activity;
import android.os.Binder;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.window.TaskFragmentInfo;
import android.window.TaskFragmentOperation;
import android.window.TaskFragmentOrganizer;
import android.window.TaskFragmentTransaction;
import android.window.WindowContainerTransaction;
import com.android.systemui.util.concurrency.DelayableExecutor;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TaskFragmentComponent {
    public final Activity activity;
    public final DelayableExecutor executor;
    public final Binder fragmentToken = new Binder();
    public final Function0 hide;
    public final Function1 onCreateCallback;
    public final Function1 onInfoChangedCallback;
    public final TaskFragmentComponent$organizer$1 organizer;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        TaskFragmentComponent create(Activity activity, Function1 function1, Function1 function12, Function0 function0);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [android.window.TaskFragmentOrganizer, com.android.systemui.dreams.homecontrols.TaskFragmentComponent$organizer$1] */
    public TaskFragmentComponent(Activity activity, Function1 function1, Function1 function12, Function0 function0, final DelayableExecutor delayableExecutor) {
        this.activity = activity;
        this.onCreateCallback = function1;
        this.onInfoChangedCallback = function12;
        this.hide = function0;
        this.executor = delayableExecutor;
        ?? r1 = new TaskFragmentOrganizer(delayableExecutor) { // from class: com.android.systemui.dreams.homecontrols.TaskFragmentComponent$organizer$1
            public final void onTransactionReady(TaskFragmentTransaction taskFragmentTransaction) {
                TaskFragmentComponent taskFragmentComponent = TaskFragmentComponent.this;
                taskFragmentComponent.getClass();
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                for (TaskFragmentTransaction.Change change : taskFragmentTransaction.getChanges()) {
                    TaskFragmentInfo taskFragmentInfo = change.getTaskFragmentInfo();
                    if (taskFragmentInfo != null && Intrinsics.areEqual(taskFragmentInfo.getFragmentToken(), taskFragmentComponent.fragmentToken)) {
                        int type = change.getType();
                        Function0 function02 = taskFragmentComponent.hide;
                        switch (type) {
                            case 1:
                                windowContainerTransaction.addTaskFragmentOperation(taskFragmentComponent.fragmentToken, new TaskFragmentOperation.Builder(13).build());
                                taskFragmentComponent.onCreateCallback.invoke(taskFragmentInfo);
                                break;
                            case 2:
                                taskFragmentComponent.onInfoChangedCallback.invoke(taskFragmentInfo);
                                break;
                            case 3:
                                function02.invoke();
                                break;
                            case 4:
                            case 6:
                                break;
                            case 5:
                                function02.invoke();
                                break;
                            default:
                                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(change.getType(), "Unknown TaskFragmentEvent="));
                        }
                    }
                }
                taskFragmentComponent.organizer.onTransactionHandled(taskFragmentTransaction.getTransactionToken(), windowContainerTransaction, 6, false);
            }
        };
        r1.registerOrganizer(true);
        this.organizer = r1;
    }
}
