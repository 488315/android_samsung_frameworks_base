package android.window;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.RemoteAnimationDefinition;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

public class TaskFragmentOrganizer extends WindowOrganizer {
    public static final String KEY_ERROR_CALLBACK_OP_TYPE = "operation_type";
    public static final String KEY_ERROR_CALLBACK_TASK_FRAGMENT_INFO = "task_fragment_info";
    public static final String KEY_ERROR_CALLBACK_THROWABLE = "fragment_throwable";
    public static final int TASK_FRAGMENT_TRANSIT_CHANGE = 6;
    public static final int TASK_FRAGMENT_TRANSIT_CLOSE = 2;
    public static final int TASK_FRAGMENT_TRANSIT_DRAG_RESIZE = 1017;
    public static final int TASK_FRAGMENT_TRANSIT_NONE = 0;
    public static final int TASK_FRAGMENT_TRANSIT_OPEN = 1;
    private final Executor mExecutor;
    private final ITaskFragmentOrganizer mInterface = new AnonymousClass1();
    private final TaskFragmentOrganizerToken mToken =
            new TaskFragmentOrganizerToken(this.mInterface);

    @Retention(RetentionPolicy.SOURCE)
    public @interface TaskFragmentTransitionType {}

    public static Bundle putErrorInfoInBundle(
            Throwable exception, TaskFragmentInfo info, int opType) {
        Bundle errorBundle = new Bundle();
        errorBundle.putSerializable(KEY_ERROR_CALLBACK_THROWABLE, exception);
        if (info != null) {
            errorBundle.putParcelable(KEY_ERROR_CALLBACK_TASK_FRAGMENT_INFO, info);
        }
        errorBundle.putInt(KEY_ERROR_CALLBACK_OP_TYPE, opType);
        return errorBundle;
    }

    public TaskFragmentOrganizer(Executor executor) {
        this.mExecutor = executor;
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }

    public void registerOrganizer() {
        try {
            getController().registerOrganizer(this.mInterface, false);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerOrganizer(boolean isSystemOrganizer) {
        try {
            getController().registerOrganizer(this.mInterface, isSystemOrganizer);
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

    public void registerRemoteAnimations(RemoteAnimationDefinition definition) {
        try {
            getController().registerRemoteAnimations(this.mInterface, definition);
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

    public void onTransactionHandled(
            IBinder transactionToken,
            WindowContainerTransaction wct,
            int transitionType,
            boolean shouldApplyIndependently) {
        wct.setTaskFragmentOrganizer(this.mInterface);
        try {
            getController()
                    .onTransactionHandled(
                            transactionToken, wct, transitionType, shouldApplyIndependently);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.window.WindowOrganizer
    public void applyTransaction(WindowContainerTransaction wct) {
        throw new RuntimeException("Not allowed!");
    }

    public void applyTransaction(
            WindowContainerTransaction wct, int transitionType, boolean shouldApplyIndependently) {
        if (wct.isEmpty()) {
            return;
        }
        wct.setTaskFragmentOrganizer(this.mInterface);
        try {
            getController().applyTransaction(wct, transitionType, shouldApplyIndependently, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void applySystemTransaction(
            WindowContainerTransaction wct, int transitionType, RemoteTransition remoteTransition) {
        if (wct.isEmpty()) {
            return;
        }
        wct.setTaskFragmentOrganizer(this.mInterface);
        try {
            getController()
                    .applyTransaction(
                            wct, transitionType, remoteTransition != null, remoteTransition);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onTransactionReady(TaskFragmentTransaction transaction) {
        onTransactionHandled(
                transaction.getTransactionToken(), new WindowContainerTransaction(), 0, false);
    }

    /* renamed from: android.window.TaskFragmentOrganizer$1, reason: invalid class name */
    class AnonymousClass1 extends ITaskFragmentOrganizer.Stub {
        AnonymousClass1() {}

        public /* synthetic */ void lambda$onTransactionReady$0(
                TaskFragmentTransaction transaction) {
            TaskFragmentOrganizer.this.onTransactionReady(transaction);
        }

        @Override // android.window.ITaskFragmentOrganizer
        public void onTransactionReady(final TaskFragmentTransaction transaction) {
            TaskFragmentOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskFragmentOrganizer$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskFragmentOrganizer.AnonymousClass1.this.lambda$onTransactionReady$0(
                                    transaction);
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
        } catch (RemoteException e) {
            return null;
        }
    }

    public boolean isActivityEmbedded(IBinder activityToken) {
        try {
            return getController().isActivityEmbedded(activityToken);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSupportActivityEmbedded(String packageName) {
        try {
            return getController().isSupportActivityEmbedded(packageName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
