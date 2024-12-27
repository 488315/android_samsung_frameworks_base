package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.app.PictureInPictureUiState;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public final class PipStateTransactionItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<PipStateTransactionItem> CREATOR =
            new Parcelable.Creator<PipStateTransactionItem>() { // from class:
                // android.app.servertransaction.PipStateTransactionItem.1
                @Override // android.os.Parcelable.Creator
                public PipStateTransactionItem createFromParcel(Parcel in) {
                    return new PipStateTransactionItem(in);
                }

                @Override // android.os.Parcelable.Creator
                public PipStateTransactionItem[] newArray(int size) {
                    return new PipStateTransactionItem[size];
                }
            };
    private PictureInPictureUiState mPipState;

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(
            ClientTransactionHandler client,
            ActivityThread.ActivityClientRecord r,
            PendingTransactionActions pendingActions) {
        client.handlePictureInPictureStateChanged(r, this.mPipState);
    }

    private PipStateTransactionItem() {}

    public static PipStateTransactionItem obtain(
            IBinder activityToken, PictureInPictureUiState pipState) {
        PipStateTransactionItem instance =
                (PipStateTransactionItem) ObjectPool.obtain(PipStateTransactionItem.class);
        if (instance == null) {
            instance = new PipStateTransactionItem();
        }
        instance.setActivityToken(activityToken);
        instance.mPipState = pipState;
        return instance;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem,
    // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mPipState = null;
        ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        this.mPipState.writeToParcel(dest, flags);
    }

    private PipStateTransactionItem(Parcel in) {
        super(in);
        this.mPipState = PictureInPictureUiState.CREATOR.createFromParcel(in);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        PipStateTransactionItem other = (PipStateTransactionItem) o;
        return Objects.equals(this.mPipState, other.mPipState);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        int result = (17 * 31) + super.hashCode();
        return (result * 31) + Objects.hashCode(this.mPipState);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "PipStateTransactionItem{" + super.toString() + "}";
    }
}
