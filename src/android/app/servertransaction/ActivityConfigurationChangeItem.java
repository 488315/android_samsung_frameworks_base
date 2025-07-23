package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;
import android.window.ActivityWindowInfo;
import java.util.Objects;

/* loaded from: classes.dex */
public class ActivityConfigurationChangeItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<ActivityConfigurationChangeItem> CREATOR = new Parcelable.Creator<ActivityConfigurationChangeItem>() { // from class: android.app.servertransaction.ActivityConfigurationChangeItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityConfigurationChangeItem createFromParcel(Parcel parcel) {
            return new ActivityConfigurationChangeItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityConfigurationChangeItem[] newArray(int i) {
            return new ActivityConfigurationChangeItem[i];
        }
    };
    private final ActivityWindowInfo mActivityWindowInfo;
    private final Configuration mConfiguration;

    public ActivityConfigurationChangeItem(IBinder iBinder, Configuration configuration, ActivityWindowInfo activityWindowInfo) {
        super(iBinder);
        this.mConfiguration = new Configuration(configuration);
        this.mActivityWindowInfo = new ActivityWindowInfo(activityWindowInfo);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(ClientTransactionHandler clientTransactionHandler) {
        CompatibilityInfo.applyOverrideIfNeeded(this.mConfiguration);
        clientTransactionHandler.updatePendingActivityConfiguration(getActivityToken(), this.mConfiguration);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityConfigChanged");
        clientTransactionHandler.handleActivityConfigurationChanged(activityClientRecord, this.mConfiguration, -1, this.mActivityWindowInfo);
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mConfiguration, i);
        parcel.writeTypedObject(this.mActivityWindowInfo, i);
    }

    private ActivityConfigurationChangeItem(Parcel parcel) {
        super(parcel);
        this.mConfiguration = (Configuration) Objects.requireNonNull((Configuration) parcel.readTypedObject(Configuration.CREATOR));
        this.mActivityWindowInfo = (ActivityWindowInfo) Objects.requireNonNull((ActivityWindowInfo) parcel.readTypedObject(ActivityWindowInfo.CREATOR));
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        ActivityConfigurationChangeItem activityConfigurationChangeItem = (ActivityConfigurationChangeItem) obj;
        return Objects.equals(this.mConfiguration, activityConfigurationChangeItem.mConfiguration) && Objects.equals(this.mActivityWindowInfo, activityConfigurationChangeItem.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((527 + super.hashCode()) * 31) + Objects.hashCode(this.mConfiguration)) * 31) + Objects.hashCode(this.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "ActivityConfigurationChange{" + super.toString() + ",config=" + this.mConfiguration + ",activityWindowInfo=" + this.mActivityWindowInfo + "}";
    }
}
