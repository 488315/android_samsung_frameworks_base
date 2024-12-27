package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcelable;

import java.io.PrintWriter;

/* loaded from: classes.dex */
public abstract class ClientTransactionItem implements BaseClientRequest, Parcelable {
    public int getPostExecutionState() {
        return -1;
    }

    boolean shouldHaveDefinedPreExecutionState() {
        return true;
    }

    public IBinder getActivityToken() {
        return null;
    }

    public boolean isActivityLifecycleItem() {
        return false;
    }

    void dump(String prefix, PrintWriter pw, ClientTransactionHandler transactionHandler) {
        pw.append((CharSequence) prefix).println(this);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
