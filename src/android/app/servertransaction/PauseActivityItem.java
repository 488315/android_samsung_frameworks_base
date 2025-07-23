package android.app.servertransaction;

import android.app.ActivityClient;
import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

/* loaded from: classes.dex */
public class PauseActivityItem extends ActivityLifecycleItem {
    public static final Parcelable.Creator<PauseActivityItem> CREATOR = new Parcelable.Creator<PauseActivityItem>() { // from class: android.app.servertransaction.PauseActivityItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PauseActivityItem createFromParcel(Parcel parcel) {
            return new PauseActivityItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PauseActivityItem[] newArray(int i) {
            return new PauseActivityItem[i];
        }
    };
    private final boolean mAutoEnteringPip;
    private final boolean mDontReport;
    private final boolean mFinished;
    private final boolean mUserLeaving;

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 4;
    }

    public PauseActivityItem(IBinder iBinder) {
        this(iBinder, false, false, true, false);
    }

    public PauseActivityItem(IBinder iBinder, boolean z, boolean z2, boolean z3, boolean z4) {
        super(iBinder);
        this.mFinished = z;
        this.mUserLeaving = z2;
        this.mDontReport = z3;
        this.mAutoEnteringPip = z4;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityPause");
        clientTransactionHandler.handlePauseActivity(activityClientRecord, this.mFinished, this.mUserLeaving, this.mAutoEnteringPip, pendingTransactionActions, "PAUSE_ACTIVITY_ITEM");
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        if (this.mDontReport) {
            return;
        }
        ActivityClient.getInstance().activityPaused(getActivityToken());
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mFinished);
        parcel.writeBoolean(this.mUserLeaving);
        parcel.writeBoolean(this.mDontReport);
        parcel.writeBoolean(this.mAutoEnteringPip);
    }

    private PauseActivityItem(Parcel parcel) {
        super(parcel);
        this.mFinished = parcel.readBoolean();
        this.mUserLeaving = parcel.readBoolean();
        this.mDontReport = parcel.readBoolean();
        this.mAutoEnteringPip = parcel.readBoolean();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        PauseActivityItem pauseActivityItem = (PauseActivityItem) obj;
        return this.mFinished == pauseActivityItem.mFinished && this.mUserLeaving == pauseActivityItem.mUserLeaving && this.mDontReport == pauseActivityItem.mDontReport && this.mAutoEnteringPip == pauseActivityItem.mAutoEnteringPip;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((((((527 + super.hashCode()) * 31) + (this.mFinished ? 1 : 0)) * 31) + (this.mUserLeaving ? 1 : 0)) * 31) + (this.mDontReport ? 1 : 0)) * 31) + (this.mAutoEnteringPip ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "PauseActivityItem{" + super.toString() + ",finished=" + this.mFinished + ",userLeaving=" + this.mUserLeaving + ",dontReport=" + this.mDontReport + ",autoEnteringPip=" + this.mAutoEnteringPip + "}";
    }
}
