package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Trace;
import android.util.IntArray;
import android.util.Slog;
import java.util.List;

/* loaded from: classes.dex */
public class TransactionExecutor {
    private static final boolean DEBUG_RESOLVER = false;
    private static final String TAG = "TransactionExecutor";
    private final ClientTransactionHandler mTransactionHandler;
    private final PendingTransactionActions mPendingActions = new PendingTransactionActions();
    private final TransactionExecutorHelper mHelper = new TransactionExecutorHelper();

    public TransactionExecutor(ClientTransactionHandler clientTransactionHandler) {
        this.mTransactionHandler = clientTransactionHandler;
    }

    public void execute(ClientTransaction transaction) {
        Trace.traceBegin(32L, "clientTransactionExecuted");
        try {
            try {
                if (transaction.getTransactionItems() != null) {
                    executeTransactionItems(transaction);
                } else {
                    executeCallbacks(transaction);
                    executeLifecycleState(transaction);
                }
                Trace.traceEnd(32L);
                this.mPendingActions.clear();
            } catch (Exception e) {
                Slog.e(TAG, "Failed to execute the transaction: " + TransactionExecutorHelper.transactionToString(transaction, this.mTransactionHandler));
                throw e;
            }
        } catch (Throwable th) {
            Trace.traceEnd(32L);
            throw th;
        }
    }

    public void executeTransactionItems(ClientTransaction transaction) {
        List<ClientTransactionItem> items = transaction.getTransactionItems();
        int size = items.size();
        for (int i = 0; i < size; i++) {
            ClientTransactionItem item = items.get(i);
            if (item.isActivityLifecycleItem()) {
                executeLifecycleItem(transaction, (ActivityLifecycleItem) item);
            } else {
                executeNonLifecycleItem(transaction, item, TransactionExecutorHelper.shouldExcludeLastLifecycleState(items, i));
            }
        }
    }

    @Deprecated
    public void executeCallbacks(ClientTransaction transaction) {
        List<ClientTransactionItem> callbacks = transaction.getCallbacks();
        if (callbacks == null || callbacks.isEmpty()) {
            return;
        }
        ActivityLifecycleItem finalStateRequest = transaction.getLifecycleStateRequest();
        int finalState = finalStateRequest != null ? finalStateRequest.getTargetState() : -1;
        int lastCallbackRequestingState = TransactionExecutorHelper.lastCallbackRequestingState(transaction);
        int size = callbacks.size();
        int i = 0;
        while (i < size) {
            ClientTransactionItem item = callbacks.get(i);
            int postExecutionState = item.getPostExecutionState();
            boolean shouldExcludeLastLifecycleState = postExecutionState != -1 && i == lastCallbackRequestingState && finalState == postExecutionState;
            executeNonLifecycleItem(transaction, item, shouldExcludeLastLifecycleState);
            i++;
        }
    }

    private void executeNonLifecycleItem(ClientTransaction transaction, ClientTransactionItem item, boolean shouldExcludeLastLifecycleState) {
        int closestPreExecutionState;
        IBinder token = item.getActivityToken();
        ActivityThread.ActivityClientRecord r = this.mTransactionHandler.getActivityClient(token);
        if (token != null && r == null && this.mTransactionHandler.getActivitiesToBeDestroyed().containsKey(token)) {
            Slog.w(TAG, "Skip pre-destroyed transaction item:\n" + item);
            return;
        }
        int postExecutionState = item.getPostExecutionState();
        if (item.shouldHaveDefinedPreExecutionState() && (closestPreExecutionState = this.mHelper.getClosestPreExecutionState(r, postExecutionState)) != -1) {
            cycleToPath(r, closestPreExecutionState, transaction);
        }
        item.execute(this.mTransactionHandler, this.mPendingActions);
        item.postExecute(this.mTransactionHandler, this.mPendingActions);
        if (r == null) {
            r = this.mTransactionHandler.getActivityClient(token);
        }
        if (postExecutionState != -1 && r != null) {
            cycleToPath(r, postExecutionState, shouldExcludeLastLifecycleState, transaction);
        }
    }

    @Deprecated
    private void executeLifecycleState(ClientTransaction transaction) {
        ActivityLifecycleItem lifecycleItem = transaction.getLifecycleStateRequest();
        if (lifecycleItem == null) {
            return;
        }
        executeLifecycleItem(transaction, lifecycleItem);
    }

    private void executeLifecycleItem(ClientTransaction transaction, ActivityLifecycleItem lifecycleItem) {
        IBinder token = lifecycleItem.getActivityToken();
        ActivityThread.ActivityClientRecord r = this.mTransactionHandler.getActivityClient(token);
        if (r == null) {
            if (this.mTransactionHandler.getActivitiesToBeDestroyed().get(token) == lifecycleItem) {
                lifecycleItem.postExecute(this.mTransactionHandler, this.mPendingActions);
            }
        } else {
            cycleToPath(r, lifecycleItem.getTargetState(), true, transaction);
            lifecycleItem.execute(this.mTransactionHandler, this.mPendingActions);
            lifecycleItem.postExecute(this.mTransactionHandler, this.mPendingActions);
        }
    }

    public void cycleToPath(ActivityThread.ActivityClientRecord r, int finish, ClientTransaction transaction) {
        cycleToPath(r, finish, false, transaction);
    }

    private void cycleToPath(ActivityThread.ActivityClientRecord r, int finish, boolean excludeLastState, ClientTransaction transaction) {
        int start = r.getLifecycleState();
        IntArray path = this.mHelper.getLifecyclePath(start, finish, excludeLastState);
        performLifecycleSequence(r, path, transaction);
    }

    private void performLifecycleSequence(ActivityThread.ActivityClientRecord r, IntArray path, ClientTransaction transaction) {
        int size = path.size();
        for (int i = 0; i < size; i++) {
            int state = path.get(i);
            switch (state) {
                case 1:
                    this.mTransactionHandler.handleLaunchActivity(r, this.mPendingActions, -1, null);
                    break;
                case 2:
                    this.mTransactionHandler.handleStartActivity(r, this.mPendingActions, null);
                    break;
                case 3:
                    this.mTransactionHandler.handleResumeActivity(r, false, r.isForward, false, "LIFECYCLER_RESUME_ACTIVITY");
                    break;
                case 4:
                    this.mTransactionHandler.handlePauseActivity(r, false, false, false, this.mPendingActions, "LIFECYCLER_PAUSE_ACTIVITY");
                    break;
                case 5:
                    this.mTransactionHandler.handleStopActivity(r, this.mPendingActions, false, "LIFECYCLER_STOP_ACTIVITY");
                    break;
                case 6:
                    this.mTransactionHandler.handleDestroyActivity(r, false, false, "performLifecycleSequence. cycling to:" + path.get(size - 1));
                    break;
                case 7:
                    this.mTransactionHandler.performRestartActivity(r, false);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected lifecycle state: " + state);
            }
        }
    }
}
