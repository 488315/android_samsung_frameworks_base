package android.hardware.location;

import android.hardware.location.ContextHubTransaction;
import android.hardware.location.IContextHubTransactionCallback;
import android.util.Log;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public class ContextHubTransactionHelper {
    private static final String TAG = "ContextHubTransactionHelper";

    public static IContextHubTransactionCallback createNanoAppQueryCallback(final ContextHubTransaction<List<NanoAppState>> contextHubTransaction) {
        Objects.requireNonNull(contextHubTransaction, "transaction cannot be null");
        return new IContextHubTransactionCallback.Stub() { // from class: android.hardware.location.ContextHubTransactionHelper.1
            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onQueryResponse(int i, List<NanoAppState> list) {
                contextHubTransaction.setResponse(new ContextHubTransaction.Response(i, list));
            }

            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onTransactionComplete(int i) {
                Log.e(ContextHubTransactionHelper.TAG, "Received a non-query callback on a query request");
                contextHubTransaction.setResponse(new ContextHubTransaction.Response(7, null));
            }
        };
    }

    public static IContextHubTransactionCallback createTransactionCallback(final ContextHubTransaction<Void> contextHubTransaction) {
        Objects.requireNonNull(contextHubTransaction, "transaction cannot be null");
        return new IContextHubTransactionCallback.Stub() { // from class: android.hardware.location.ContextHubTransactionHelper.2
            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onQueryResponse(int i, List<NanoAppState> list) {
                Log.e(ContextHubTransactionHelper.TAG, "Received a query callback on a non-query request");
                contextHubTransaction.setResponse(new ContextHubTransaction.Response(7, null));
            }

            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onTransactionComplete(int i) {
                contextHubTransaction.setResponse(new ContextHubTransaction.Response(i, null));
            }
        };
    }
}
