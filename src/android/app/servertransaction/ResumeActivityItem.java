package android.app.servertransaction;

import android.app.ActivityClient;
import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

/* loaded from: classes.dex */
public class ResumeActivityItem extends ActivityLifecycleItem {
    public static final Parcelable.Creator<ResumeActivityItem> CREATOR = new Parcelable.Creator<ResumeActivityItem>() { // from class: android.app.servertransaction.ResumeActivityItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResumeActivityItem createFromParcel(Parcel parcel) {
            return new ResumeActivityItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResumeActivityItem[] newArray(int i) {
            return new ResumeActivityItem[i];
        }
    };
    private final boolean mIsForward;
    private final int mProcState;
    private final boolean mShouldSendCompatFakeFocus;

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 3;
    }

    public ResumeActivityItem(IBinder iBinder, boolean z, boolean z2) {
        this(iBinder, -1, z, z2);
    }

    public ResumeActivityItem(IBinder iBinder, int i, boolean z, boolean z2) {
        super(iBinder);
        this.mProcState = i;
        this.mIsForward = z;
        this.mShouldSendCompatFakeFocus = z2;
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(ClientTransactionHandler clientTransactionHandler) {
        int i = this.mProcState;
        if (i != -1) {
            clientTransactionHandler.updateProcessState(i, false);
        }
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityResume");
        clientTransactionHandler.handleResumeActivity(activityClientRecord, true, this.mIsForward, this.mShouldSendCompatFakeFocus, "RESUME_ACTIVITY");
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        ActivityClient.getInstance().activityResumed(getActivityToken(), clientTransactionHandler.isHandleSplashScreenExit(getActivityToken()));
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mProcState);
        parcel.writeBoolean(this.mIsForward);
        parcel.writeBoolean(this.mShouldSendCompatFakeFocus);
    }

    private ResumeActivityItem(Parcel parcel) {
        super(parcel);
        this.mProcState = parcel.readInt();
        this.mIsForward = parcel.readBoolean();
        this.mShouldSendCompatFakeFocus = parcel.readBoolean();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        ResumeActivityItem resumeActivityItem = (ResumeActivityItem) obj;
        return this.mProcState == resumeActivityItem.mProcState && this.mIsForward == resumeActivityItem.mIsForward && this.mShouldSendCompatFakeFocus == resumeActivityItem.mShouldSendCompatFakeFocus;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((((527 + super.hashCode()) * 31) + this.mProcState) * 31) + (this.mIsForward ? 1 : 0)) * 31) + (this.mShouldSendCompatFakeFocus ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "ResumeActivityItem{" + super.toString() + ",procState=" + this.mProcState + ",isForward=" + this.mIsForward + ",shouldSendCompatFakeFocus=" + this.mShouldSendCompatFakeFocus + "}";
    }
}
