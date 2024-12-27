package com.android.server.wm;

import android.app.IApplicationThread;
import android.app.servertransaction.ActivityLifecycleItem;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.ClientTransactionItem;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.window.flags.Flags;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ClientLifecycleManager {
    final ArrayMap mPendingTransactions = new ArrayMap();
    public WindowManagerService mWms;

    public static void scheduleTransaction(ClientTransaction clientTransaction) {
        IApplicationThread client = clientTransaction.getClient();
        try {
            try {
                clientTransaction.schedule();
            } catch (RemoteException e) {
                Slog.w(
                        "ClientLifecycleManager",
                        "Failed to deliver transaction for "
                                + client
                                + "\ntransaction="
                                + clientTransaction);
                throw e;
            }
        } finally {
            if (!(client instanceof Binder)) {
                clientTransaction.recycle();
            }
        }
    }

    public final void dispatchPendingTransaction(IApplicationThread iApplicationThread) {
        ClientTransaction clientTransaction;
        if (Flags.bundleClientTransactionFlag()
                && (clientTransaction =
                                (ClientTransaction)
                                        this.mPendingTransactions.remove(
                                                iApplicationThread.asBinder()))
                        != null) {
            try {
                scheduleTransaction(clientTransaction);
            } catch (RemoteException e) {
                Slog.e("ClientLifecycleManager", "Failed to deliver pending transaction", e);
            }
        }
    }

    public final void dispatchPendingTransactions() {
        if (!Flags.bundleClientTransactionFlag() || this.mPendingTransactions.isEmpty()) {
            return;
        }
        Trace.traceBegin(32L, "clientTransactionsDispatched");
        int size = this.mPendingTransactions.size();
        for (int i = 0; i < size; i++) {
            try {
                scheduleTransaction((ClientTransaction) this.mPendingTransactions.valueAt(i));
            } catch (RemoteException e) {
                Slog.e("ClientLifecycleManager", "Failed to deliver pending transaction", e);
            }
        }
        this.mPendingTransactions.clear();
        Trace.traceEnd(32L);
    }

    public final void scheduleTransactionAndLifecycleItems(
            IApplicationThread iApplicationThread,
            ClientTransactionItem clientTransactionItem,
            ActivityLifecycleItem activityLifecycleItem,
            boolean z) {
        if (!Flags.bundleClientTransactionFlag()) {
            ClientTransaction obtain = ClientTransaction.obtain(iApplicationThread);
            obtain.addTransactionItem(clientTransactionItem);
            obtain.addTransactionItem(activityLifecycleItem);
            scheduleTransaction(obtain);
            return;
        }
        IBinder asBinder = iApplicationThread.asBinder();
        ClientTransaction clientTransaction =
                (ClientTransaction) this.mPendingTransactions.get(asBinder);
        if (clientTransaction == null) {
            clientTransaction = ClientTransaction.obtain(iApplicationThread);
            this.mPendingTransactions.put(asBinder, clientTransaction);
        }
        clientTransaction.addTransactionItem(clientTransactionItem);
        clientTransaction.addTransactionItem(activityLifecycleItem);
        if (z || shouldDispatchPendingTransactionsImmediately()) {
            this.mPendingTransactions.remove(clientTransaction.getClient().asBinder());
            scheduleTransaction(clientTransaction);
        }
    }

    public final void scheduleTransactionItem(
            IApplicationThread iApplicationThread, ClientTransactionItem clientTransactionItem) {
        if (!Flags.bundleClientTransactionFlag()) {
            ClientTransaction obtain = ClientTransaction.obtain(iApplicationThread);
            obtain.addTransactionItem(clientTransactionItem);
            scheduleTransaction(obtain);
            return;
        }
        IBinder asBinder = iApplicationThread.asBinder();
        ClientTransaction clientTransaction =
                (ClientTransaction) this.mPendingTransactions.get(asBinder);
        if (clientTransaction == null) {
            clientTransaction = ClientTransaction.obtain(iApplicationThread);
            this.mPendingTransactions.put(asBinder, clientTransaction);
        }
        clientTransaction.addTransactionItem(clientTransactionItem);
        if (shouldDispatchPendingTransactionsImmediately()) {
            this.mPendingTransactions.remove(clientTransaction.getClient().asBinder());
            scheduleTransaction(clientTransaction);
        }
    }

    public final boolean shouldDispatchPendingTransactionsImmediately() {
        WindowManagerService windowManagerService = this.mWms;
        if (windowManagerService == null) {
            return true;
        }
        WindowSurfacePlacer windowSurfacePlacer = windowManagerService.mWindowPlacerLocked;
        return (windowSurfacePlacer.mDeferDepth > 0
                        || windowSurfacePlacer.mTraversalScheduled
                        || windowSurfacePlacer.mInLayout)
                ? false
                : true;
    }
}
