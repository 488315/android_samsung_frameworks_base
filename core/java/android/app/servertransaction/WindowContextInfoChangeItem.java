package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.window.WindowContextInfo;

import java.util.Objects;

public class WindowContextInfoChangeItem extends ClientTransactionItem {
    public static final Parcelable.Creator<WindowContextInfoChangeItem> CREATOR =
            new Parcelable.Creator<WindowContextInfoChangeItem>() { // from class:
                // android.app.servertransaction.WindowContextInfoChangeItem.1
                @Override // android.os.Parcelable.Creator
                public WindowContextInfoChangeItem createFromParcel(Parcel in) {
                    return new WindowContextInfoChangeItem(in);
                }

                @Override // android.os.Parcelable.Creator
                public WindowContextInfoChangeItem[] newArray(int size) {
                    return new WindowContextInfoChangeItem[size];
                }
            };
    private IBinder mClientToken;
    private WindowContextInfo mInfo;

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(ClientTransactionHandler client, PendingTransactionActions pendingActions) {
        client.handleWindowContextInfoChanged(this.mClientToken, this.mInfo);
    }

    private WindowContextInfoChangeItem() {}

    public static WindowContextInfoChangeItem obtain(
            IBinder clientToken, Configuration config, int displayId) {
        WindowContextInfoChangeItem instance =
                (WindowContextInfoChangeItem) ObjectPool.obtain(WindowContextInfoChangeItem.class);
        if (instance == null) {
            instance = new WindowContextInfoChangeItem();
        }
        instance.mClientToken = (IBinder) Objects.requireNonNull(clientToken);
        instance.mInfo = new WindowContextInfo(new Configuration(config), displayId);
        return instance;
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        this.mClientToken = null;
        this.mInfo = null;
        ObjectPool.recycle(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.mClientToken);
        dest.writeTypedObject(this.mInfo, flags);
    }

    private WindowContextInfoChangeItem(Parcel in) {
        this.mClientToken = in.readStrongBinder();
        this.mInfo = (WindowContextInfo) in.readTypedObject(WindowContextInfo.CREATOR);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WindowContextInfoChangeItem other = (WindowContextInfoChangeItem) o;
        if (Objects.equals(this.mClientToken, other.mClientToken)
                && Objects.equals(this.mInfo, other.mInfo)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = (17 * 31) + Objects.hashCode(this.mClientToken);
        return (result * 31) + Objects.hashCode(this.mInfo);
    }

    public String toString() {
        return "WindowContextInfoChangeItem{clientToken="
                + this.mClientToken
                + ", info="
                + this.mInfo
                + "}";
    }
}
