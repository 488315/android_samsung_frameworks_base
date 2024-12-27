package android.app.servertransaction;

import android.app.ActivityClient;
import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

public class PauseActivityItem extends ActivityLifecycleItem {
    public static final Parcelable.Creator<PauseActivityItem> CREATOR =
            new Parcelable.Creator<PauseActivityItem>() { // from class:
                // android.app.servertransaction.PauseActivityItem.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PauseActivityItem createFromParcel(Parcel in) {
                    return new PauseActivityItem(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PauseActivityItem[] newArray(int size) {
                    return new PauseActivityItem[size];
                }
            };
    private static final String TAG = "PauseActivityItem";
    private boolean mAutoEnteringPip;
    private boolean mDontReport;
    private boolean mFinished;
    private boolean mUserLeaving;

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(
            ClientTransactionHandler client,
            ActivityThread.ActivityClientRecord r,
            PendingTransactionActions pendingActions) {
        Trace.traceBegin(64L, "activityPause");
        client.handlePauseActivity(
                r,
                this.mFinished,
                this.mUserLeaving,
                this.mAutoEnteringPip,
                pendingActions,
                "PAUSE_ACTIVITY_ITEM");
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 4;
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(
            ClientTransactionHandler client, PendingTransactionActions pendingActions) {
        if (this.mDontReport) {
            return;
        }
        ActivityClient.getInstance().activityPaused(getActivityToken());
    }

    private PauseActivityItem() {}

    public static PauseActivityItem obtain(
            IBinder activityToken,
            boolean finished,
            boolean userLeaving,
            boolean dontReport,
            boolean autoEnteringPip) {
        PauseActivityItem instance = (PauseActivityItem) ObjectPool.obtain(PauseActivityItem.class);
        if (instance == null) {
            instance = new PauseActivityItem();
        }
        instance.setActivityToken(activityToken);
        instance.mFinished = finished;
        instance.mUserLeaving = userLeaving;
        instance.mDontReport = dontReport;
        instance.mAutoEnteringPip = autoEnteringPip;
        return instance;
    }

    public static PauseActivityItem obtain(IBinder activityToken) {
        return obtain(activityToken, false, false, true, false);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem,
    // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mFinished = false;
        this.mUserLeaving = false;
        this.mDontReport = false;
        this.mAutoEnteringPip = false;
        ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeBoolean(this.mFinished);
        dest.writeBoolean(this.mUserLeaving);
        dest.writeBoolean(this.mDontReport);
        dest.writeBoolean(this.mAutoEnteringPip);
    }

    private PauseActivityItem(Parcel in) {
        super(in);
        this.mFinished = in.readBoolean();
        this.mUserLeaving = in.readBoolean();
        this.mDontReport = in.readBoolean();
        this.mAutoEnteringPip = in.readBoolean();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        PauseActivityItem other = (PauseActivityItem) o;
        return this.mFinished == other.mFinished
                && this.mUserLeaving == other.mUserLeaving
                && this.mDontReport == other.mDontReport
                && this.mAutoEnteringPip == other.mAutoEnteringPip;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return (((((((((17 * 31) + super.hashCode()) * 31) + (this.mFinished ? 1 : 0)) * 31)
                                                + (this.mUserLeaving ? 1 : 0))
                                        * 31)
                                + (this.mDontReport ? 1 : 0))
                        * 31)
                + (this.mAutoEnteringPip ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "PauseActivityItem{"
                + super.toString()
                + ",finished="
                + this.mFinished
                + ",userLeaving="
                + this.mUserLeaving
                + ",dontReport="
                + this.mDontReport
                + ",autoEnteringPip="
                + this.mAutoEnteringPip
                + "}";
    }
}
