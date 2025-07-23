package android.app.servertransaction;

import android.app.ClientTransactionHandler;

/* loaded from: classes.dex */
public interface BaseClientRequest {
    void execute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions);

    default void postExecute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
    }

    default void preExecute(ClientTransactionHandler clientTransactionHandler) {
    }
}
