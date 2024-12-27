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
public class MoveToDisplayItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<MoveToDisplayItem> CREATOR =
            new Parcelable.Creator<MoveToDisplayItem>() { // from class:
                // android.app.servertransaction.MoveToDisplayItem.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public MoveToDisplayItem createFromParcel(Parcel in) {
                    return new MoveToDisplayItem(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public MoveToDisplayItem[] newArray(int size) {
                    return new MoveToDisplayItem[size];
                }
            };
    private ActivityWindowInfo mActivityWindowInfo;
    private Configuration mConfiguration;
    private int mTargetDisplayId;

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
        Trace.traceBegin(64L, "activityMovedToDisplay");
        client.handleActivityConfigurationChanged(
                r, this.mConfiguration, this.mTargetDisplayId, this.mActivityWindowInfo);
        Trace.traceEnd(64L);
    }

    private MoveToDisplayItem() {}

    public static MoveToDisplayItem obtain(
            IBinder activityToken,
            int targetDisplayId,
            Configuration configuration,
            ActivityWindowInfo activityWindowInfo) {
        MoveToDisplayItem instance = (MoveToDisplayItem) ObjectPool.obtain(MoveToDisplayItem.class);
        if (instance == null) {
            instance = new MoveToDisplayItem();
        }
        instance.setActivityToken(activityToken);
        instance.mTargetDisplayId = targetDisplayId;
        instance.mConfiguration = new Configuration(configuration);
        instance.mActivityWindowInfo = new ActivityWindowInfo(activityWindowInfo);
        return instance;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem,
    // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mTargetDisplayId = 0;
        this.mConfiguration = null;
        this.mActivityWindowInfo = null;
        ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mTargetDisplayId);
        dest.writeTypedObject(this.mConfiguration, flags);
        dest.writeTypedObject(this.mActivityWindowInfo, flags);
    }

    private MoveToDisplayItem(Parcel in) {
        super(in);
        this.mTargetDisplayId = in.readInt();
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
        MoveToDisplayItem other = (MoveToDisplayItem) o;
        return this.mTargetDisplayId == other.mTargetDisplayId
                && Objects.equals(this.mConfiguration, other.mConfiguration)
                && Objects.equals(this.mActivityWindowInfo, other.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        int result = (17 * 31) + super.hashCode();
        return (((((result * 31) + this.mTargetDisplayId) * 31) + this.mConfiguration.hashCode())
                        * 31)
                + Objects.hashCode(this.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "MoveToDisplayItem{"
                + super.toString()
                + ",targetDisplayId="
                + this.mTargetDisplayId
                + ",configuration="
                + this.mConfiguration
                + ",activityWindowInfo="
                + this.mActivityWindowInfo
                + "}";
    }
}
