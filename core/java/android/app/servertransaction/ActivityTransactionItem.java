package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;

import java.io.PrintWriter;
import java.util.Objects;

/* loaded from: classes.dex */
public abstract class ActivityTransactionItem extends ClientTransactionItem {
    private IBinder mActivityToken;

    public abstract void execute(
            ClientTransactionHandler clientTransactionHandler,
            ActivityThread.ActivityClientRecord activityClientRecord,
            PendingTransactionActions pendingTransactionActions);

    ActivityTransactionItem() {}

    @Override // android.app.servertransaction.BaseClientRequest
    public final void execute(
            ClientTransactionHandler client, PendingTransactionActions pendingActions) {
        ActivityThread.ActivityClientRecord r = getActivityClientRecord(client);
        execute(client, r, pendingActions);
    }

    final ActivityThread.ActivityClientRecord getActivityClientRecord(
            ClientTransactionHandler client) {
        ActivityThread.ActivityClientRecord r = client.getActivityClient(getActivityToken());
        if (r == null) {
            throw new IllegalArgumentException(
                    "Activity client record must not be null to execute transaction item: " + this);
        }
        if (client.getActivity(getActivityToken()) == null) {
            throw new IllegalArgumentException(
                    "Activity must not be null to execute transaction item: " + this);
        }
        return r;
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public IBinder getActivityToken() {
        return this.mActivityToken;
    }

    void setActivityToken(IBinder activityToken) {
        this.mActivityToken = (IBinder) Objects.requireNonNull(activityToken);
    }

    ActivityTransactionItem(Parcel in) {
        this.mActivityToken = in.readStrongBinder();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.mActivityToken);
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        this.mActivityToken = null;
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    void dump(String prefix, PrintWriter pw, ClientTransactionHandler transactionHandler) {
        super.dump(prefix, pw, transactionHandler);
        pw.append((CharSequence) prefix)
                .append("Target activity: ")
                .println(
                        TransactionExecutorHelper.getActivityName(
                                this.mActivityToken, transactionHandler));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActivityTransactionItem other = (ActivityTransactionItem) o;
        return Objects.equals(this.mActivityToken, other.mActivityToken);
    }

    public int hashCode() {
        return Objects.hashCode(this.mActivityToken);
    }

    public String toString() {
        return "mActivityToken=" + this.mActivityToken;
    }
}
