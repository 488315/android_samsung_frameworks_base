package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

/* loaded from: classes.dex */
public class DestroyActivityItem extends ActivityLifecycleItem {
    public static final Parcelable.Creator<DestroyActivityItem> CREATOR = new Parcelable.Creator<DestroyActivityItem>() { // from class: android.app.servertransaction.DestroyActivityItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DestroyActivityItem createFromParcel(Parcel parcel) {
            return new DestroyActivityItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DestroyActivityItem[] newArray(int i) {
            return new DestroyActivityItem[i];
        }
    };
    private final boolean mFinished;

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 6;
    }

    public DestroyActivityItem(IBinder iBinder, boolean z) {
        super(iBinder);
        this.mFinished = z;
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(ClientTransactionHandler clientTransactionHandler) {
        clientTransactionHandler.getActivitiesToBeDestroyed().put(getActivityToken(), this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityDestroy");
        clientTransactionHandler.handleDestroyActivity(activityClientRecord, this.mFinished, false, "DestroyActivityItem");
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.getActivitiesToBeDestroyed().remove(getActivityToken());
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mFinished);
    }

    private DestroyActivityItem(Parcel parcel) {
        super(parcel);
        this.mFinished = parcel.readBoolean();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return super.equals(obj) && this.mFinished == ((DestroyActivityItem) obj).mFinished;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((527 + super.hashCode()) * 31) + (this.mFinished ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "DestroyActivityItem{" + super.toString() + ",finished=" + this.mFinished + "}";
    }
}
