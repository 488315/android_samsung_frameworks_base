package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcelable;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public abstract class ClientTransactionItem implements BaseClientRequest, Parcelable {
    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public IBinder getActivityToken() {
        return null;
    }

    public int getPostExecutionState() {
        return -1;
    }

    public boolean isActivityLifecycleItem() {
        return false;
    }

    boolean shouldHaveDefinedPreExecutionState() {
        return true;
    }

    void dump(String str, PrintWriter printWriter, ClientTransactionHandler clientTransactionHandler) {
        printWriter.append((CharSequence) str).println(this);
    }
}
