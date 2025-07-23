package android.window;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.RemoteAnimationDefinition;
import android.window.ITaskFragmentOrganizer;
import android.window.TaskFragmentOrganizer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes5.dex */
public class TaskFragmentOrganizer extends WindowOrganizer {
    public static final String KEY_ERROR_CALLBACK_OP_TYPE = "operation_type";
    public static final String KEY_ERROR_CALLBACK_TASK_FRAGMENT_INFO = "task_fragment_info";
    public static final String KEY_ERROR_CALLBACK_THROWABLE = "fragment_throwable";
    public static final String KEY_RESTORE_TASK_FRAGMENTS_INFO = "key_restore_task_fragments_info";
    public static final String KEY_RESTORE_TASK_FRAGMENT_PARENT_INFO = "key_restore_task_fragment_parent_info";
    public static final int TASK_FRAGMENT_TRANSIT_CHANGE = 6;
    public static final int TASK_FRAGMENT_TRANSIT_CLOSE = 2;
    public static final int TASK_FRAGMENT_TRANSIT_DRAG_RESIZE = 1017;
    public static final int TASK_FRAGMENT_TRANSIT_NONE = 0;
    public static final int TASK_FRAGMENT_TRANSIT_OPEN = 1;
    private final Executor mExecutor;
    private final ITaskFragmentOrganizer mInterface;
    private final TaskFragmentOrganizerToken mToken;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TaskFragmentTransitionType {
    }

    public static Bundle putErrorInfoInBundle(Throwable th, TaskFragmentInfo taskFragmentInfo, int i) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_ERROR_CALLBACK_THROWABLE, th);
        if (taskFragmentInfo != null) {
            bundle.putParcelable(KEY_ERROR_CALLBACK_TASK_FRAGMENT_INFO, taskFragmentInfo);
        }
        bundle.putInt(KEY_ERROR_CALLBACK_OP_TYPE, i);
        return bundle;
    }

    public TaskFragmentOrganizer(Executor executor) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mInterface = anonymousClass1;
        this.mToken = new TaskFragmentOrganizerToken(anonymousClass1);
        this.mExecutor = executor;
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }

    public void registerOrganizer() {
        registerOrganizer(false, null);
    }

    public void registerOrganizer(boolean z) {
        registerOrganizer(z, null);
    }

    public void registerOrganizer(boolean z, Bundle bundle) {
        try {
            ITaskFragmentOrganizerController controller = getController();
            ITaskFragmentOrganizer iTaskFragmentOrganizer = this.mInterface;
            if (bundle == null) {
                bundle = new Bundle();
            }
            controller.registerOrganizer(iTaskFragmentOrganizer, z, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterOrganizer() {
        try {
            getController().unregisterOrganizer(this.mInterface);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerRemoteAnimations(RemoteAnimationDefinition remoteAnimationDefinition) {
        try {
            getController().registerRemoteAnimations(this.mInterface, remoteAnimationDefinition);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterRemoteAnimations() {
        try {
            getController().unregisterRemoteAnimations(this.mInterface);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSavedState(Bundle bundle) {
        if (bundle.getSize() > 200000) {
            throw new IllegalArgumentException("Saved state too large, " + bundle.getSize());
        }
        try {
            getController().setSavedState(this.mInterface, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onTransactionHandled(IBinder iBinder, WindowContainerTransaction windowContainerTransaction, int i, boolean z) {
        windowContainerTransaction.setTaskFragmentOrganizer(this.mInterface);
        try {
            getController().onTransactionHandled(iBinder, windowContainerTransaction, i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.window.WindowOrganizer
    public void applyTransaction(WindowContainerTransaction windowContainerTransaction) {
        throw new RuntimeException("Not allowed!");
    }

    public void applyTransaction(WindowContainerTransaction windowContainerTransaction, int i, boolean z) {
        if (windowContainerTransaction.isEmpty()) {
            return;
        }
        windowContainerTransaction.setTaskFragmentOrganizer(this.mInterface);
        try {
            getController().applyTransaction(windowContainerTransaction, i, z, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void applySystemTransaction(WindowContainerTransaction windowContainerTransaction, int i, RemoteTransition remoteTransition) {
        if (windowContainerTransaction.isEmpty()) {
            return;
        }
        windowContainerTransaction.setTaskFragmentOrganizer(this.mInterface);
        try {
            getController().applyTransaction(windowContainerTransaction, i, remoteTransition != null, remoteTransition);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onTransactionReady(TaskFragmentTransaction taskFragmentTransaction) {
        onTransactionHandled(taskFragmentTransaction.getTransactionToken(), new WindowContainerTransaction(), 0, false);
    }

    /* renamed from: android.window.TaskFragmentOrganizer$1, reason: invalid class name */
    class AnonymousClass1 extends ITaskFragmentOrganizer.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTransactionReady$0(TaskFragmentTransaction taskFragmentTransaction) {
            TaskFragmentOrganizer.this.onTransactionReady(taskFragmentTransaction);
        }

        @Override // android.window.ITaskFragmentOrganizer
        public void onTransactionReady(final TaskFragmentTransaction taskFragmentTransaction) {
            TaskFragmentOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskFragmentOrganizer$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TaskFragmentOrganizer.AnonymousClass1.this.lambda$onTransactionReady$0(taskFragmentTransaction);
                }
            });
        }
    }

    public TaskFragmentOrganizerToken getOrganizerToken() {
        return this.mToken;
    }

    private ITaskFragmentOrganizerController getController() {
        try {
            return getWindowOrganizerController().getTaskFragmentOrganizerController();
        } catch (RemoteException unused) {
            return null;
        }
    }

    public static boolean isActivityEmbedded(Activity activity) {
        Objects.requireNonNull(activity);
        ActivityWindowInfo activityWindowInfo = ActivityWindowInfo.getActivityWindowInfo(activity);
        return activityWindowInfo != null && activityWindowInfo.isEmbedded();
    }

    public boolean isSupportActivityEmbedded(String str) {
        try {
            return getController().isSupportActivityEmbedded(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
