package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import java.io.PrintWriter;
import java.util.Objects;

/* loaded from: classes.dex */
public abstract class ActivityTransactionItem extends ClientTransactionItem {
    private final IBinder mActivityToken;

    public abstract void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions);

    public ActivityTransactionItem(IBinder iBinder) {
        this.mActivityToken = (IBinder) Objects.requireNonNull(iBinder);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public final void execute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        execute(clientTransactionHandler, getActivityClientRecord(clientTransactionHandler), pendingTransactionActions);
    }

    final ActivityThread.ActivityClientRecord getActivityClientRecord(ClientTransactionHandler clientTransactionHandler) {
        ActivityThread.ActivityClientRecord activityClient = clientTransactionHandler.getActivityClient(getActivityToken());
        if (activityClient == null) {
            throw new IllegalArgumentException("Activity client record must not be null to execute transaction item: " + this);
        }
        if (clientTransactionHandler.getActivity(getActivityToken()) != null) {
            return activityClient;
        }
        throw new IllegalArgumentException("Activity must not be null to execute transaction item: " + this);
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public IBinder getActivityToken() {
        return this.mActivityToken;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mActivityToken);
    }

    ActivityTransactionItem(Parcel parcel) {
        this(parcel.readStrongBinder());
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    void dump(String str, PrintWriter printWriter, ClientTransactionHandler clientTransactionHandler) {
        super.dump(str, printWriter, clientTransactionHandler);
        printWriter.append((CharSequence) str).append("Target activity: ").println(TransactionExecutorHelper.getActivityName(this.mActivityToken, clientTransactionHandler));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mActivityToken, ((ActivityTransactionItem) obj).mActivityToken);
    }

    public int hashCode() {
        return Objects.hashCode(this.mActivityToken);
    }

    public String toString() {
        return "mActivityToken=" + this.mActivityToken;
    }
}
