package android.hardware.location;

import android.hardware.location.ContextHubTransaction;
import android.hardware.location.IContextHubTransactionCallback;
import android.util.Log;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public class ContextHubTransactionHelper {
    private static final String TAG = "ContextHubTransactionHelper";

    public static IContextHubTransactionCallback createNanoAppQueryCallback(final ContextHubTransaction<List<NanoAppState>> transaction) {
        Objects.requireNonNull(transaction, "transaction cannot be null");
        return new IContextHubTransactionCallback.Stub() { // from class: android.hardware.location.ContextHubTransactionHelper.1
            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onQueryResponse(int result, List<NanoAppState> nanoappList) {
                ContextHubTransaction.this.setResponse(new ContextHubTransaction.Response(result, nanoappList));
            }

            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onTransactionComplete(int result) {
                Log.e(ContextHubTransactionHelper.TAG, "Received a non-query callback on a query request");
                ContextHubTransaction.this.setResponse(new ContextHubTransaction.Response(7, null));
            }
        };
    }

    public static IContextHubTransactionCallback createTransactionCallback(final ContextHubTransaction<Void> transaction) {
        Objects.requireNonNull(transaction, "transaction cannot be null");
        return new IContextHubTransactionCallback.Stub() { // from class: android.hardware.location.ContextHubTransactionHelper.2
            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onQueryResponse(int result, List<NanoAppState> nanoappList) {
                Log.e(ContextHubTransactionHelper.TAG, "Received a query callback on a non-query request");
                ContextHubTransaction.this.setResponse(new ContextHubTransaction.Response(7, null));
            }

            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onTransactionComplete(int result) {
                ContextHubTransaction.this.setResponse(new ContextHubTransaction.Response(result, null));
            }
        };
    }
}
