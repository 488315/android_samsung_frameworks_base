package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

/* loaded from: classes.dex */
public class StopActivityItem extends ActivityLifecycleItem {
    public static final Parcelable.Creator<StopActivityItem> CREATOR = new Parcelable.Creator<StopActivityItem>() { // from class: android.app.servertransaction.StopActivityItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StopActivityItem createFromParcel(Parcel parcel) {
            return new StopActivityItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StopActivityItem[] newArray(int i) {
            return new StopActivityItem[i];
        }
    };

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 5;
    }

    public StopActivityItem(IBinder iBinder) {
        super(iBinder);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityStop");
        clientTransactionHandler.handleStopActivity(activityClientRecord, pendingTransactionActions, true, "STOP_ACTIVITY_ITEM");
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.reportStop(pendingTransactionActions);
    }

    private StopActivityItem(Parcel parcel) {
        super(parcel);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "StopActivityItem{" + super.toString() + "}";
    }
}
