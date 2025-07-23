package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.app.PictureInPictureUiState;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class PipStateTransactionItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<PipStateTransactionItem> CREATOR = new Parcelable.Creator<PipStateTransactionItem>() { // from class: android.app.servertransaction.PipStateTransactionItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PipStateTransactionItem createFromParcel(Parcel parcel) {
            return new PipStateTransactionItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PipStateTransactionItem[] newArray(int i) {
            return new PipStateTransactionItem[i];
        }
    };
    private final PictureInPictureUiState mPipState;

    public PipStateTransactionItem(IBinder iBinder, PictureInPictureUiState pictureInPictureUiState) {
        super(iBinder);
        this.mPipState = pictureInPictureUiState;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handlePictureInPictureStateChanged(activityClientRecord, this.mPipState);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        this.mPipState.writeToParcel(parcel, i);
    }

    private PipStateTransactionItem(Parcel parcel) {
        super(parcel);
        this.mPipState = PictureInPictureUiState.CREATOR.createFromParcel(parcel);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (super.equals(obj)) {
            return Objects.equals(this.mPipState, ((PipStateTransactionItem) obj).mPipState);
        }
        return false;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((527 + super.hashCode()) * 31) + Objects.hashCode(this.mPipState);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "PipStateTransactionItem{" + super.toString() + "}";
    }
}
