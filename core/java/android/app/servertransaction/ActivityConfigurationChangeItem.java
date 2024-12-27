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

public class ActivityConfigurationChangeItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<ActivityConfigurationChangeItem> CREATOR =
            new Parcelable.Creator<ActivityConfigurationChangeItem>() { // from class:
                // android.app.servertransaction.ActivityConfigurationChangeItem.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ActivityConfigurationChangeItem createFromParcel(Parcel in) {
                    return new ActivityConfigurationChangeItem(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ActivityConfigurationChangeItem[] newArray(int size) {
                    return new ActivityConfigurationChangeItem[size];
                }
            };
    private ActivityWindowInfo mActivityWindowInfo;
    private Configuration mConfiguration;

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(ClientTransactionHandler client) {
        CompatibilityInfo.applyOverrideScaleIfNeeded(this.mConfiguration);
        client.updatePendingActivityConfiguration(getActivityToken(), this.mConfiguration);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(
            ClientTransactionHandler client,
            ActivityThread.ActivityClientRecord r,
            PendingTransactionActions pendingActions) {
        Trace.traceBegin(64L, "activityConfigChanged");
        client.handleActivityConfigurationChanged(
                r, this.mConfiguration, -1, this.mActivityWindowInfo);
        Trace.traceEnd(64L);
    }

    private ActivityConfigurationChangeItem() {}

    public static ActivityConfigurationChangeItem obtain(
            IBinder activityToken, Configuration config, ActivityWindowInfo activityWindowInfo) {
        ActivityConfigurationChangeItem instance =
                (ActivityConfigurationChangeItem)
                        ObjectPool.obtain(ActivityConfigurationChangeItem.class);
        if (instance == null) {
            instance = new ActivityConfigurationChangeItem();
        }
        instance.setActivityToken(activityToken);
        instance.mConfiguration = new Configuration(config);
        instance.mActivityWindowInfo = new ActivityWindowInfo(activityWindowInfo);
        return instance;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem,
    // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mConfiguration = null;
        this.mActivityWindowInfo = null;
        ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedObject(this.mConfiguration, flags);
        dest.writeTypedObject(this.mActivityWindowInfo, flags);
    }

    private ActivityConfigurationChangeItem(Parcel in) {
        super(in);
        this.mConfiguration = (Configuration) in.readTypedObject(Configuration.CREATOR);
        this.mActivityWindowInfo =
                (ActivityWindowInfo) in.readTypedObject(ActivityWindowInfo.CREATOR);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        ActivityConfigurationChangeItem other = (ActivityConfigurationChangeItem) o;
        return Objects.equals(this.mConfiguration, other.mConfiguration)
                && Objects.equals(this.mActivityWindowInfo, other.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        int result = (17 * 31) + super.hashCode();
        return (((result * 31) + Objects.hashCode(this.mConfiguration)) * 31)
                + Objects.hashCode(this.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "ActivityConfigurationChange{"
                + super.toString()
                + ",config="
                + this.mConfiguration
                + ",activityWindowInfo="
                + this.mActivityWindowInfo
                + "}";
    }
}
