package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public class WindowContextWindowRemovalItem extends ClientTransactionItem {
    public static final Parcelable.Creator<WindowContextWindowRemovalItem> CREATOR = new Parcelable.Creator<WindowContextWindowRemovalItem>() { // from class: android.app.servertransaction.WindowContextWindowRemovalItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContextWindowRemovalItem createFromParcel(Parcel parcel) {
            return new WindowContextWindowRemovalItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContextWindowRemovalItem[] newArray(int i) {
            return new WindowContextWindowRemovalItem[i];
        }
    };
    private final IBinder mClientToken;

    public WindowContextWindowRemovalItem(IBinder iBinder) {
        this.mClientToken = (IBinder) Objects.requireNonNull(iBinder);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handleWindowContextWindowRemoval(this.mClientToken);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mClientToken);
    }

    private WindowContextWindowRemovalItem(Parcel parcel) {
        this.mClientToken = parcel.readStrongBinder();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mClientToken, ((WindowContextWindowRemovalItem) obj).mClientToken);
    }

    public int hashCode() {
        return 527 + Objects.hashCode(this.mClientToken);
    }

    public String toString() {
        return "WindowContextWindowRemovalItem{clientToken=" + this.mClientToken + "}";
    }
}
