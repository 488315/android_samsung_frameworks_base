package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

public class DestroyActivityItem extends ActivityLifecycleItem {
    public static final Parcelable.Creator<DestroyActivityItem> CREATOR =
            new Parcelable.Creator<DestroyActivityItem>() { // from class:
                // android.app.servertransaction.DestroyActivityItem.1
                @Override // android.os.Parcelable.Creator
                public DestroyActivityItem createFromParcel(Parcel in) {
                    return new DestroyActivityItem(in);
                }

                @Override // android.os.Parcelable.Creator
                public DestroyActivityItem[] newArray(int size) {
                    return new DestroyActivityItem[size];
                }
            };
    private boolean mFinished;

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(ClientTransactionHandler client) {
        client.getActivitiesToBeDestroyed().put(getActivityToken(), this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(
            ClientTransactionHandler client,
            ActivityThread.ActivityClientRecord r,
            PendingTransactionActions pendingActions) {
        Trace.traceBegin(64L, "activityDestroy");
        client.handleDestroyActivity(r, this.mFinished, false, "DestroyActivityItem");
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(
            ClientTransactionHandler client, PendingTransactionActions pendingActions) {
        client.getActivitiesToBeDestroyed().remove(getActivityToken());
    }

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 6;
    }

    private DestroyActivityItem() {}

    public static DestroyActivityItem obtain(IBinder activityToken, boolean finished) {
        DestroyActivityItem instance =
                (DestroyActivityItem) ObjectPool.obtain(DestroyActivityItem.class);
        if (instance == null) {
            instance = new DestroyActivityItem();
        }
        instance.setActivityToken(activityToken);
        instance.mFinished = finished;
        return instance;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem,
    // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mFinished = false;
        ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeBoolean(this.mFinished);
    }

    private DestroyActivityItem(Parcel in) {
        super(in);
        this.mFinished = in.readBoolean();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        DestroyActivityItem other = (DestroyActivityItem) o;
        return this.mFinished == other.mFinished;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return (((17 * 31) + super.hashCode()) * 31) + (this.mFinished ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "DestroyActivityItem{" + super.toString() + ",finished=" + this.mFinished + "}";
    }
}
