package android.app.servertransaction;

import android.app.ActivityClient;
import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

public class ResumeActivityItem extends ActivityLifecycleItem {
    public static final Parcelable.Creator<ResumeActivityItem> CREATOR =
            new Parcelable.Creator<ResumeActivityItem>() { // from class:
                // android.app.servertransaction.ResumeActivityItem.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ResumeActivityItem createFromParcel(Parcel in) {
                    return new ResumeActivityItem(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ResumeActivityItem[] newArray(int size) {
                    return new ResumeActivityItem[size];
                }
            };
    private static final String TAG = "ResumeActivityItem";
    private boolean mIsForward;
    private int mProcState;
    private boolean mShouldSendCompatFakeFocus;
    private boolean mUpdateProcState;

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(ClientTransactionHandler client) {
        if (this.mUpdateProcState) {
            client.updateProcessState(this.mProcState, false);
        }
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(
            ClientTransactionHandler client,
            ActivityThread.ActivityClientRecord r,
            PendingTransactionActions pendingActions) {
        Trace.traceBegin(64L, "activityResume");
        client.handleResumeActivity(
                r, true, this.mIsForward, this.mShouldSendCompatFakeFocus, "RESUME_ACTIVITY");
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(
            ClientTransactionHandler client, PendingTransactionActions pendingActions) {
        ActivityClient.getInstance()
                .activityResumed(
                        getActivityToken(), client.isHandleSplashScreenExit(getActivityToken()));
    }

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 3;
    }

    private ResumeActivityItem() {}

    public static ResumeActivityItem obtain(
            IBinder activityToken,
            int procState,
            boolean isForward,
            boolean shouldSendCompatFakeFocus) {
        ResumeActivityItem instance =
                (ResumeActivityItem) ObjectPool.obtain(ResumeActivityItem.class);
        if (instance == null) {
            instance = new ResumeActivityItem();
        }
        instance.setActivityToken(activityToken);
        instance.mProcState = procState;
        instance.mUpdateProcState = true;
        instance.mIsForward = isForward;
        instance.mShouldSendCompatFakeFocus = shouldSendCompatFakeFocus;
        return instance;
    }

    public static ResumeActivityItem obtain(
            IBinder activityToken, boolean isForward, boolean shouldSendCompatFakeFocus) {
        ResumeActivityItem instance =
                (ResumeActivityItem) ObjectPool.obtain(ResumeActivityItem.class);
        if (instance == null) {
            instance = new ResumeActivityItem();
        }
        instance.setActivityToken(activityToken);
        instance.mProcState = -1;
        instance.mUpdateProcState = false;
        instance.mIsForward = isForward;
        instance.mShouldSendCompatFakeFocus = shouldSendCompatFakeFocus;
        return instance;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem,
    // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mProcState = -1;
        this.mUpdateProcState = false;
        this.mIsForward = false;
        this.mShouldSendCompatFakeFocus = false;
        ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mProcState);
        dest.writeBoolean(this.mUpdateProcState);
        dest.writeBoolean(this.mIsForward);
        dest.writeBoolean(this.mShouldSendCompatFakeFocus);
    }

    private ResumeActivityItem(Parcel in) {
        super(in);
        this.mProcState = in.readInt();
        this.mUpdateProcState = in.readBoolean();
        this.mIsForward = in.readBoolean();
        this.mShouldSendCompatFakeFocus = in.readBoolean();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        ResumeActivityItem other = (ResumeActivityItem) o;
        return this.mProcState == other.mProcState
                && this.mUpdateProcState == other.mUpdateProcState
                && this.mIsForward == other.mIsForward
                && this.mShouldSendCompatFakeFocus == other.mShouldSendCompatFakeFocus;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return (((((((((17 * 31) + super.hashCode()) * 31) + this.mProcState) * 31)
                                                + (this.mUpdateProcState ? 1 : 0))
                                        * 31)
                                + (this.mIsForward ? 1 : 0))
                        * 31)
                + (this.mShouldSendCompatFakeFocus ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "ResumeActivityItem{"
                + super.toString()
                + ",procState="
                + this.mProcState
                + ",updateProcState="
                + this.mUpdateProcState
                + ",isForward="
                + this.mIsForward
                + ",shouldSendCompatFakeFocus="
                + this.mShouldSendCompatFakeFocus
                + "}";
    }
}
