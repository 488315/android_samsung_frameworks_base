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
        public EnterPipRequestedItem createFromParcel(Parcel parcel) {
            return new EnterPipRequestedItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterPipRequestedItem[] newArray(int i) {
            return new EnterPipRequestedItem[i];
        }
    };

    public EnterPipRequestedItem(IBinder iBinder) {
        super(iBinder);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handlePictureInPictureRequested(activityClientRecord);
    }

    private EnterPipRequestedItem(Parcel parcel) {
        super(parcel);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "EnterPipRequestedItem{" + super.toString() + "}";
    }
}
