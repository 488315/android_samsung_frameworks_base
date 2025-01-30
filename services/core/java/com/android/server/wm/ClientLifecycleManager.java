package com.android.server.wm;

import android.app.IApplicationThread;
import android.app.servertransaction.ActivityLifecycleItem;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.ClientTransactionItem;
import android.os.Binder;
import android.os.IBinder;

/* loaded from: classes3.dex */
public class ClientLifecycleManager {
    public void scheduleTransaction(ClientTransaction clientTransaction) {
        IApplicationThread client = clientTransaction.getClient();
        clientTransaction.schedule();
        if (client instanceof Binder) {
            return;
        }
        clientTransaction.recycle();
    }

    public void scheduleTransaction(IApplicationThread iApplicationThread, IBinder iBinder, ActivityLifecycleItem activityLifecycleItem) {
        scheduleTransaction(transactionWithState(iApplicationThread, iBinder, activityLifecycleItem));
    }

    public void scheduleTransaction(IApplicationThread iApplicationThread, IBinder iBinder, ClientTransactionItem clientTransactionItem) {
        scheduleTransaction(transactionWithCallback(iApplicationThread, iBinder, clientTransactionItem));
    }

    public void scheduleTransaction(IApplicationThread iApplicationThread, ClientTransactionItem clientTransactionItem) {
        scheduleTransaction(transactionWithCallback(iApplicationThread, null, clientTransactionItem));
    }

    public static ClientTransaction transactionWithState(IApplicationThread iApplicationThread, IBinder iBinder, ActivityLifecycleItem activityLifecycleItem) {
        ClientTransaction obtain = ClientTransaction.obtain(iApplicationThread, iBinder);
        obtain.setLifecycleStateRequest(activityLifecycleItem);
        return obtain;
    }

    public static ClientTransaction transactionWithCallback(IApplicationThread iApplicationThread, IBinder iBinder, ClientTransactionItem clientTransactionItem) {
        ClientTransaction obtain = ClientTransaction.obtain(iApplicationThread, iBinder);
        obtain.addCallback(clientTransactionItem);
        return obtain;
    }
}
