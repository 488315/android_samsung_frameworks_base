package android.app.servertransaction;

import android.app.ActivityClient;
import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

public class TopResumedActivityChangeItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<TopResumedActivityChangeItem> CREATOR =
            new Parcelable.Creator<TopResumedActivityChangeItem>() { // from class:
                // android.app.servertransaction.TopResumedActivityChangeItem.1
                @Override // android.os.Parcelable.Creator
                public TopResumedActivityChangeItem createFromParcel(Parcel in) {
                    return new TopResumedActivityChangeItem(in);
                }

                @Override // android.os.Parcelable.Creator
                public TopResumedActivityChangeItem[] newArray(int size) {
                    return new TopResumedActivityChangeItem[size];
                }
            };
    private boolean mOnTop;

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(
            ClientTransactionHandler client,
            ActivityThread.ActivityClientRecord r,
            PendingTransactionActions pendingActions) {
        Trace.traceBegin(64L, "topResumedActivityChangeItem");
        client.handleTopResumedActivityChanged(r, this.mOnTop, "topResumedActivityChangeItem");
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(
            ClientTransactionHandler client, PendingTransactionActions pendingActions) {
        if (this.mOnTop) {
            return;
        }
        ActivityClient.getInstance().activityTopResumedStateLost();
    }

    private TopResumedActivityChangeItem() {}

    public static TopResumedActivityChangeItem obtain(IBinder activityToken, boolean onTop) {
        TopResumedActivityChangeItem instance =
                (TopResumedActivityChangeItem)
                        ObjectPool.obtain(TopResumedActivityChangeItem.class);
        if (instance == null) {
            instance = new TopResumedActivityChangeItem();
        }
        instance.setActivityToken(activityToken);
        instance.mOnTop = onTop;
        return instance;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem,
    // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mOnTop = false;
        ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeBoolean(this.mOnTop);
    }

    private TopResumedActivityChangeItem(Parcel in) {
        super(in);
        this.mOnTop = in.readBoolean();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        TopResumedActivityChangeItem other = (TopResumedActivityChangeItem) o;
        return this.mOnTop == other.mOnTop;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return (((17 * 31) + super.hashCode()) * 31) + (this.mOnTop ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "TopResumedActivityChangeItem{" + super.toString() + ",onTop=" + this.mOnTop + "}";
    }
}
