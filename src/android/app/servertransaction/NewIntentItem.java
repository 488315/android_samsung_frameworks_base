package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;
import com.android.internal.content.ReferrerIntent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class NewIntentItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<NewIntentItem> CREATOR = new Parcelable.Creator<NewIntentItem>() { // from class: android.app.servertransaction.NewIntentItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NewIntentItem createFromParcel(Parcel parcel) {
            return new NewIntentItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NewIntentItem[] newArray(int i) {
            return new NewIntentItem[i];
        }
    };
    private List<ReferrerIntent> mIntents;
    private final boolean mResume;

    public NewIntentItem(IBinder iBinder, List<ReferrerIntent> list, boolean z) {
        super(iBinder);
        this.mIntents = new ArrayList(list);
        this.mResume = z;
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public int getPostExecutionState() {
        return this.mResume ? 3 : -1;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityNewIntent");
        clientTransactionHandler.handleNewIntent(activityClientRecord, this.mIntents);
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mResume);
        parcel.writeTypedList(this.mIntents, i);
    }

    private NewIntentItem(Parcel parcel) {
        super(parcel);
        this.mResume = parcel.readBoolean();
        this.mIntents = parcel.createTypedArrayList(ReferrerIntent.CREATOR);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        NewIntentItem newIntentItem = (NewIntentItem) obj;
        return this.mResume == newIntentItem.mResume && Objects.equals(this.mIntents, newIntentItem.mIntents);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((527 + super.hashCode()) * 31) + (this.mResume ? 1 : 0)) * 31) + this.mIntents.hashCode();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "NewIntentItem{" + super.toString() + ",intents=" + this.mIntents + ",resume=" + this.mResume + "}";
    }
}
