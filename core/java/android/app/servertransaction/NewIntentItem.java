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
    public static final Parcelable.Creator<NewIntentItem> CREATOR =
            new Parcelable.Creator<
                    NewIntentItem>() { // from class: android.app.servertransaction.NewIntentItem.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NewIntentItem createFromParcel(Parcel in) {
                    return new NewIntentItem(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NewIntentItem[] newArray(int size) {
                    return new NewIntentItem[size];
                }
            };
    private List<ReferrerIntent> mIntents;
    private boolean mResume;

    @Override // android.app.servertransaction.ClientTransactionItem
    public int getPostExecutionState() {
        return this.mResume ? 3 : -1;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(
            ClientTransactionHandler client,
            ActivityThread.ActivityClientRecord r,
            PendingTransactionActions pendingActions) {
        Trace.traceBegin(64L, "activityNewIntent");
        client.handleNewIntent(r, this.mIntents);
        Trace.traceEnd(64L);
    }

    private NewIntentItem() {}

    public static NewIntentItem obtain(
            IBinder activityToken, List<ReferrerIntent> intents, boolean resume) {
        NewIntentItem instance = (NewIntentItem) ObjectPool.obtain(NewIntentItem.class);
        if (instance == null) {
            instance = new NewIntentItem();
        }
        instance.setActivityToken(activityToken);
        instance.mIntents = new ArrayList(intents);
        instance.mResume = resume;
        return instance;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem,
    // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mIntents = null;
        this.mResume = false;
        ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeBoolean(this.mResume);
        dest.writeTypedList(this.mIntents, flags);
    }

    private NewIntentItem(Parcel in) {
        super(in);
        this.mResume = in.readBoolean();
        this.mIntents = in.createTypedArrayList(ReferrerIntent.CREATOR);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        NewIntentItem other = (NewIntentItem) o;
        return this.mResume == other.mResume && Objects.equals(this.mIntents, other.mIntents);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return (((((17 * 31) + super.hashCode()) * 31) + (this.mResume ? 1 : 0)) * 31)
                + this.mIntents.hashCode();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "NewIntentItem{"
                + super.toString()
                + ",intents="
                + this.mIntents
                + ",resume="
                + this.mResume
                + "}";
    }
}
