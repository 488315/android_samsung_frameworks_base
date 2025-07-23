package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.window.WindowContextInfo;
import java.util.Objects;

/* loaded from: classes.dex */
public class WindowContextInfoChangeItem extends ClientTransactionItem {
    public static final Parcelable.Creator<WindowContextInfoChangeItem> CREATOR = new Parcelable.Creator<WindowContextInfoChangeItem>() { // from class: android.app.servertransaction.WindowContextInfoChangeItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContextInfoChangeItem createFromParcel(Parcel parcel) {
            return new WindowContextInfoChangeItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContextInfoChangeItem[] newArray(int i) {
            return new WindowContextInfoChangeItem[i];
        }
    };
    private final IBinder mClientToken;
    private final WindowContextInfo mInfo;

    public WindowContextInfoChangeItem(IBinder iBinder, Configuration configuration, int i) {
        this.mClientToken = (IBinder) Objects.requireNonNull(iBinder);
        this.mInfo = new WindowContextInfo(new Configuration(configuration), i);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handleWindowContextInfoChanged(this.mClientToken, this.mInfo);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mClientToken);
        parcel.writeTypedObject(this.mInfo, i);
    }

    private WindowContextInfoChangeItem(Parcel parcel) {
        this.mClientToken = parcel.readStrongBinder();
        this.mInfo = (WindowContextInfo) Objects.requireNonNull((WindowContextInfo) parcel.readTypedObject(WindowContextInfo.CREATOR));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            WindowContextInfoChangeItem windowContextInfoChangeItem = (WindowContextInfoChangeItem) obj;
            if (Objects.equals(this.mClientToken, windowContextInfoChangeItem.mClientToken) && Objects.equals(this.mInfo, windowContextInfoChangeItem.mInfo)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((527 + Objects.hashCode(this.mClientToken)) * 31) + Objects.hashCode(this.mInfo);
    }

    public String toString() {
        return "WindowContextInfoChangeItem{clientToken=" + this.mClientToken + ", info=" + this.mInfo + "}";
    }
}
