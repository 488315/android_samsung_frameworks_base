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
        public WindowContextWindowRemovalItem createFromParcel(Parcel in) {
            return new WindowContextWindowRemovalItem(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContextWindowRemovalItem[] newArray(int size) {
            return new WindowContextWindowRemovalItem[size];
        }
    };
    private IBinder mClientToken;

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(ClientTransactionHandler client, PendingTransactionActions pendingActions) {
        client.handleWindowContextWindowRemoval(this.mClientToken);
    }

    private WindowContextWindowRemovalItem() {
    }

    public static WindowContextWindowRemovalItem obtain(IBinder clientToken) {
        WindowContextWindowRemovalItem instance = (WindowContextWindowRemovalItem) ObjectPool.obtain(WindowContextWindowRemovalItem.class);
        if (instance == null) {
            instance = new WindowContextWindowRemovalItem();
        }
        instance.mClientToken = (IBinder) Objects.requireNonNull(clientToken);
        return instance;
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        this.mClientToken = null;
        ObjectPool.recycle(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.mClientToken);
    }

    private WindowContextWindowRemovalItem(Parcel in) {
        this.mClientToken = in.readStrongBinder();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WindowContextWindowRemovalItem other = (WindowContextWindowRemovalItem) o;
        return Objects.equals(this.mClientToken, other.mClientToken);
    }

    public int hashCode() {
        int result = (17 * 31) + Objects.hashCode(this.mClientToken);
        return result;
    }

    public String toString() {
        return "WindowContextWindowRemovalItem{clientToken=" + this.mClientToken + "}";
    }
}
