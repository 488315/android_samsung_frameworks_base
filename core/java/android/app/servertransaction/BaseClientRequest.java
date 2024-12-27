package android.app.servertransaction;

import android.app.ClientTransactionHandler;

public interface BaseClientRequest extends ObjectPoolItem {
    void execute(
            ClientTransactionHandler clientTransactionHandler,
            PendingTransactionActions pendingTransactionActions);

    default void preExecute(ClientTransactionHandler client) {}

    default void postExecute(
            ClientTransactionHandler client, PendingTransactionActions pendingActions) {}
}
