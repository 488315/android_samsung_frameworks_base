package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class RefreshCallbackItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<RefreshCallbackItem> CREATOR = new Parcelable.Creator<RefreshCallbackItem>() { // from class: android.app.servertransaction.RefreshCallbackItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RefreshCallbackItem createFromParcel(Parcel parcel) {
            return new RefreshCallbackItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RefreshCallbackItem[] newArray(int i) {
            return new RefreshCallbackItem[i];
        }
    };
    private final int mPostExecutionState;

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    boolean shouldHaveDefinedPreExecutionState() {
        return false;
    }

    public RefreshCallbackItem(IBinder iBinder, int i) {
        super(iBinder);
        this.mPostExecutionState = i;
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.reportRefresh(getActivityClientRecord(clientTransactionHandler));
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public int getPostExecutionState() {
        return this.mPostExecutionState;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mPostExecutionState);
    }

    private RefreshCallbackItem(Parcel parcel) {
        super(parcel);
        this.mPostExecutionState = parcel.readInt();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return super.equals(obj) && this.mPostExecutionState == ((RefreshCallbackItem) obj).mPostExecutionState;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((527 + super.hashCode()) * 31) + this.mPostExecutionState;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "RefreshCallbackItem{" + super.toString() + ",mPostExecutionState=" + this.mPostExecutionState + "}";
    }
}
