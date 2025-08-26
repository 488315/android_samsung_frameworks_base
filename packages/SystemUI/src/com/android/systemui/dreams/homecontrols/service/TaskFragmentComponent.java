package com.android.systemui.dreams.homecontrols.service;

import android.app.Activity;
import android.os.Binder;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.window.TaskFragmentInfo;
import android.window.TaskFragmentOperation;
import android.window.TaskFragmentOrganizer;
import android.window.TaskFragmentTransaction;
import android.window.WindowContainerTransaction;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class TaskFragmentComponent {
    public final Activity activity;
    public final DelayableExecutor executor;
    public final Binder fragmentToken = new Binder();
    public final Function0 hide;
    public final Function1 onCreateCallback;
    public final Function1 onInfoChangedCallback;
    public final Organizer organizer;

    public interface Factory {
        TaskFragmentComponent create(Activity activity, Function1 function1, Function1 function12, Function0 function0);
    }

    public final class Organizer extends TaskFragmentOrganizer {
        public final WeakReference component;

        public Organizer(WeakReference<TaskFragmentComponent> weakReference, Executor executor) {
            super(executor);
            this.component = weakReference;
        }

        public final void onTransactionReady(TaskFragmentTransaction taskFragmentTransaction) {
            TaskFragmentComponent taskFragmentComponent = (TaskFragmentComponent) this.component.get();
            if (taskFragmentComponent != null) {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                for (TaskFragmentTransaction.Change change : taskFragmentTransaction.getChanges()) {
                    TaskFragmentInfo taskFragmentInfo = change.getTaskFragmentInfo();
                    if (taskFragmentInfo != null && Intrinsics.areEqual(taskFragmentInfo.getFragmentToken(), taskFragmentComponent.fragmentToken)) {
                        int type = change.getType();
                        Function0 function0 = taskFragmentComponent.hide;
                        switch (type) {
                            case 1:
                                windowContainerTransaction.addTaskFragmentOperation(taskFragmentComponent.fragmentToken, new TaskFragmentOperation.Builder(1002).build());
                                taskFragmentComponent.onCreateCallback.mo781invoke(taskFragmentInfo);
                                break;
                            case 2:
                                taskFragmentComponent.onInfoChangedCallback.mo781invoke(taskFragmentInfo);
                                break;
                            case 3:
                                function0.invoke();
                                break;
                            case 4:
                            case 6:
                                break;
                            case 5:
                                function0.invoke();
                                break;
                            default:
                                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(change.getType(), "Unknown TaskFragmentEvent="));
                        }
                    }
                }
                taskFragmentComponent.organizer.onTransactionHandled(taskFragmentTransaction.getTransactionToken(), windowContainerTransaction, 6, false);
            }
        }
    }

    public TaskFragmentComponent(Activity activity, Function1 function1, Function1 function12, Function0 function0, DelayableExecutor delayableExecutor) {
        this.activity = activity;
        this.onCreateCallback = function1;
        this.onInfoChangedCallback = function12;
        this.hide = function0;
        this.executor = delayableExecutor;
        Organizer organizer = new Organizer(new WeakReference(this), delayableExecutor);
        organizer.registerOrganizer(true);
        this.organizer = organizer;
    }
}
