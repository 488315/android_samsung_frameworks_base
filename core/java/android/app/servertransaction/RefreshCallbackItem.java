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
        public RefreshCallbackItem createFromParcel(Parcel in) {
            return new RefreshCallbackItem(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RefreshCallbackItem[] newArray(int size) {
            return new RefreshCallbackItem[size];
        }
    };
    private int mPostExecutionState;

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler client, ActivityThread.ActivityClientRecord r, PendingTransactionActions pendingActions) {
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(ClientTransactionHandler client, PendingTransactionActions pendingActions) {
        ActivityThread.ActivityClientRecord r = getActivityClientRecord(client);
        client.reportRefresh(r);
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public int getPostExecutionState() {
        return this.mPostExecutionState;
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    boolean shouldHaveDefinedPreExecutionState() {
        return false;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        ObjectPool.recycle(this);
    }

    public static RefreshCallbackItem obtain(IBinder activityToken, int postExecutionState) {
        if (postExecutionState != 5 && postExecutionState != 4) {
            throw new IllegalArgumentException("Only ON_STOP or ON_PAUSE are allowed as a post execution state for RefreshCallbackItem but got " + postExecutionState);
        }
        RefreshCallbackItem instance = (RefreshCallbackItem) ObjectPool.obtain(RefreshCallbackItem.class);
        if (instance == null) {
            instance = new RefreshCallbackItem();
        }
        instance.setActivityToken(activityToken);
        instance.mPostExecutionState = postExecutionState;
        return instance;
    }

    private RefreshCallbackItem() {
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mPostExecutionState);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        RefreshCallbackItem other = (RefreshCallbackItem) o;
        return this.mPostExecutionState == other.mPostExecutionState;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        int result = (17 * 31) + super.hashCode();
        return (result * 31) + this.mPostExecutionState;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "RefreshCallbackItem{" + super.toString() + ",mPostExecutionState=" + this.mPostExecutionState + "}";
    }

    private RefreshCallbackItem(Parcel in) {
        super(in);
        this.mPostExecutionState = in.readInt();
    }
}
