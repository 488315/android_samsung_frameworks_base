package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

/* loaded from: classes.dex */
public class StopActivityItem extends ActivityLifecycleItem {
    public static final Parcelable.Creator<StopActivityItem> CREATOR =
            new Parcelable.Creator<StopActivityItem>() { // from class:
                // android.app.servertransaction.StopActivityItem.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public StopActivityItem createFromParcel(Parcel in) {
                    return new StopActivityItem(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public StopActivityItem[] newArray(int size) {
                    return new StopActivityItem[size];
                }
            };
    private static final String TAG = "StopActivityItem";

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(
            ClientTransactionHandler client,
            ActivityThread.ActivityClientRecord r,
            PendingTransactionActions pendingActions) {
        Trace.traceBegin(64L, "activityStop");
        client.handleStopActivity(r, pendingActions, true, "STOP_ACTIVITY_ITEM");
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(
            ClientTransactionHandler client, PendingTransactionActions pendingActions) {
        client.reportStop(pendingActions);
    }

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 5;
    }

    private StopActivityItem() {}

    public static StopActivityItem obtain(IBinder activityToken) {
        StopActivityItem instance = (StopActivityItem) ObjectPool.obtain(StopActivityItem.class);
        if (instance == null) {
            instance = new StopActivityItem();
        }
        instance.setActivityToken(activityToken);
        return instance;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem,
    // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        ObjectPool.recycle(this);
    }

    private StopActivityItem(Parcel in) {
        super(in);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "StopActivityItem{" + super.toString() + "}";
    }
}
