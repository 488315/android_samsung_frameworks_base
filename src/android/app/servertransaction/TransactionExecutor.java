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

    public void execute(ClientTransaction clientTransaction) {
        Trace.traceBegin(32L, "clientTransactionExecuted");
        try {
            try {
                executeTransactionItems(clientTransaction);
                Trace.traceEnd(32L);
                this.mPendingActions.clear();
            } catch (Exception e) {
                Slog.e(TAG, "Failed to execute the transaction: " + TransactionExecutorHelper.transactionToString(clientTransaction, this.mTransactionHandler));
                throw e;
            }
        } catch (Throwable th) {
            Trace.traceEnd(32L);
            throw th;
        }
    }

    public void executeTransactionItems(ClientTransaction clientTransaction) {
        List<ClientTransactionItem> transactionItems = clientTransaction.getTransactionItems();
        int size = transactionItems.size();
        for (int i = 0; i < size; i++) {
            ClientTransactionItem clientTransactionItem = transactionItems.get(i);
            if (clientTransactionItem.isActivityLifecycleItem()) {
                executeLifecycleItem(clientTransaction, (ActivityLifecycleItem) clientTransactionItem);
            } else {
                executeNonLifecycleItem(clientTransaction, clientTransactionItem, TransactionExecutorHelper.shouldExcludeLastLifecycleState(transactionItems, i));
            }
        }
    }

    private void executeNonLifecycleItem(ClientTransaction clientTransaction, ClientTransactionItem clientTransactionItem, boolean z) {
        int closestPreExecutionState;
        IBinder activityToken = clientTransactionItem.getActivityToken();
        ActivityThread.ActivityClientRecord activityClient = this.mTransactionHandler.getActivityClient(activityToken);
        if (activityToken != null && activityClient == null && this.mTransactionHandler.getActivitiesToBeDestroyed().containsKey(activityToken)) {
            Slog.w(TAG, "Skip pre-destroyed transaction item:\n" + clientTransactionItem);
            return;
        }
        int postExecutionState = clientTransactionItem.getPostExecutionState();
        if (clientTransactionItem.shouldHaveDefinedPreExecutionState() && (closestPreExecutionState = this.mHelper.getClosestPreExecutionState(activityClient, postExecutionState)) != -1) {
            cycleToPath(activityClient, closestPreExecutionState, clientTransaction);
        }
        clientTransactionItem.execute(this.mTransactionHandler, this.mPendingActions);
        clientTransactionItem.postExecute(this.mTransactionHandler, this.mPendingActions);
        if (activityClient == null) {
            activityClient = this.mTransactionHandler.getActivityClient(activityToken);
        }
        if (postExecutionState == -1 || activityClient == null) {
            return;
        }
        cycleToPath(activityClient, postExecutionState, z, clientTransaction);
    }

    private void executeLifecycleItem(ClientTransaction clientTransaction, ActivityLifecycleItem activityLifecycleItem) {
        IBinder activityToken = activityLifecycleItem.getActivityToken();
        ActivityThread.ActivityClientRecord activityClient = this.mTransactionHandler.getActivityClient(activityToken);
        if (activityClient == null) {
            if (this.mTransactionHandler.getActivitiesToBeDestroyed().get(activityToken) == activityLifecycleItem) {
                activityLifecycleItem.postExecute(this.mTransactionHandler, this.mPendingActions);
            }
        } else {
            cycleToPath(activityClient, activityLifecycleItem.getTargetState(), true, clientTransaction);
            activityLifecycleItem.execute(this.mTransactionHandler, this.mPendingActions);
            activityLifecycleItem.postExecute(this.mTransactionHandler, this.mPendingActions);
        }
    }

    public void cycleToPath(ActivityThread.ActivityClientRecord activityClientRecord, int i, ClientTransaction clientTransaction) {
        cycleToPath(activityClientRecord, i, false, clientTransaction);
    }

    private void cycleToPath(ActivityThread.ActivityClientRecord activityClientRecord, int i, boolean z, ClientTransaction clientTransaction) {
        performLifecycleSequence(activityClientRecord, this.mHelper.getLifecyclePath(activityClientRecord.getLifecycleState(), i, z), clientTransaction);
    }

    private void performLifecycleSequence(ActivityThread.ActivityClientRecord activityClientRecord, IntArray intArray, ClientTransaction clientTransaction) {
        int size = intArray.size();
        for (int i = 0; i < size; i++) {
            int i2 = intArray.get(i);
            switch (i2) {
                case 1:
                    this.mTransactionHandler.handleLaunchActivity(activityClientRecord, this.mPendingActions, -1, null);
                    break;
                case 2:
                    this.mTransactionHandler.handleStartActivity(activityClientRecord, this.mPendingActions, null);
                    break;
                case 3:
                    this.mTransactionHandler.handleResumeActivity(activityClientRecord, false, activityClientRecord.isForward, false, "LIFECYCLER_RESUME_ACTIVITY");
                    break;
                case 4:
                    this.mTransactionHandler.handlePauseActivity(activityClientRecord, false, false, false, this.mPendingActions, "LIFECYCLER_PAUSE_ACTIVITY");
                    break;
                case 5:
                    this.mTransactionHandler.handleStopActivity(activityClientRecord, this.mPendingActions, false, "LIFECYCLER_STOP_ACTIVITY");
                    break;
                case 6:
                    this.mTransactionHandler.handleDestroyActivity(activityClientRecord, false, false, "performLifecycleSequence. cycling to:" + intArray.get(size - 1));
                    break;
                case 7:
                    this.mTransactionHandler.performRestartActivity(activityClientRecord, false);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected lifecycle state: " + i2);
            }
        }
    }
}
