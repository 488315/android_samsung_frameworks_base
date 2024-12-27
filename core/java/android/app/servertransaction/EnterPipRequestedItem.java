package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class EnterPipRequestedItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<EnterPipRequestedItem> CREATOR = new Parcelable.Creator<EnterPipRequestedItem>() { // from class: android.app.servertransaction.EnterPipRequestedItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterPipRequestedItem createFromParcel(Parcel in) {
            return new EnterPipRequestedItem(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterPipRequestedItem[] newArray(int size) {
            return new EnterPipRequestedItem[size];
        }
    };

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler client, ActivityThread.ActivityClientRecord r, PendingTransactionActions pendingActions) {
        client.handlePictureInPictureRequested(r);
    }

    private EnterPipRequestedItem() {
    }

    public static EnterPipRequestedItem obtain(IBinder activityToken) {
        EnterPipRequestedItem instance = (EnterPipRequestedItem) ObjectPool.obtain(EnterPipRequestedItem.class);
        if (instance == null) {
            instance = new EnterPipRequestedItem();
        }
        instance.setActivityToken(activityToken);
        return instance;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        ObjectPool.recycle(this);
    }

    private EnterPipRequestedItem(Parcel in) {
        super(in);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "EnterPipRequestedItem{" + super.toString() + "}";
    }
}
