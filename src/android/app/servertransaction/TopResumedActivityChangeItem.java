package android.app.servertransaction;

import android.app.ActivityClient;
import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

/* loaded from: classes.dex */
public class TopResumedActivityChangeItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<TopResumedActivityChangeItem> CREATOR = new Parcelable.Creator<TopResumedActivityChangeItem>() { // from class: android.app.servertransaction.TopResumedActivityChangeItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TopResumedActivityChangeItem createFromParcel(Parcel parcel) {
            return new TopResumedActivityChangeItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TopResumedActivityChangeItem[] newArray(int i) {
            return new TopResumedActivityChangeItem[i];
        }
    };
    private final boolean mOnTop;

    public TopResumedActivityChangeItem(IBinder iBinder, boolean z) {
        super(iBinder);
        this.mOnTop = z;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "topResumedActivityChangeItem");
        clientTransactionHandler.handleTopResumedActivityChanged(activityClientRecord, this.mOnTop, "topResumedActivityChangeItem");
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        if (this.mOnTop) {
            return;
        }
        ActivityClient.getInstance().activityTopResumedStateLost();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mOnTop);
    }

    private TopResumedActivityChangeItem(Parcel parcel) {
        super(parcel);
        this.mOnTop = parcel.readBoolean();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return super.equals(obj) && this.mOnTop == ((TopResumedActivityChangeItem) obj).mOnTop;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((527 + super.hashCode()) * 31) + (this.mOnTop ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "TopResumedActivityChangeItem{" + super.toString() + ",onTop=" + this.mOnTop + "}";
    }
}
