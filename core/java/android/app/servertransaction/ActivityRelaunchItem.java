package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.app.ResultInfo;
import android.content.res.CompatibilityInfo;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;
import android.util.MergedConfiguration;
import android.window.ActivityWindowInfo;

import com.android.internal.content.ReferrerIntent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActivityRelaunchItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<ActivityRelaunchItem> CREATOR =
            new Parcelable.Creator<ActivityRelaunchItem>() { // from class:
                // android.app.servertransaction.ActivityRelaunchItem.1
                @Override // android.os.Parcelable.Creator
                public ActivityRelaunchItem createFromParcel(Parcel in) {
                    return new ActivityRelaunchItem(in);
                }

                @Override // android.os.Parcelable.Creator
                public ActivityRelaunchItem[] newArray(int size) {
                    return new ActivityRelaunchItem[size];
                }
            };
    private static final String TAG = "ActivityRelaunchItem";
    private ActivityThread.ActivityClientRecord mActivityClientRecord;
    private ActivityWindowInfo mActivityWindowInfo;
    private MergedConfiguration mConfig;
    private int mConfigChanges;
    private List<ReferrerIntent> mPendingNewIntents;
    private List<ResultInfo> mPendingResults;
    private boolean mPreserveWindow;

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(ClientTransactionHandler client) {
        if (!client.isExecutingLocalTransaction()) {
            CompatibilityInfo.applyOverrideScaleIfNeeded(this.mConfig);
        }
        this.mActivityClientRecord =
                client.prepareRelaunchActivity(
                        getActivityToken(),
                        this.mPendingResults,
                        this.mPendingNewIntents,
                        this.mConfigChanges,
                        this.mConfig,
                        this.mPreserveWindow,
                        this.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(
            ClientTransactionHandler client,
            ActivityThread.ActivityClientRecord r,
            PendingTransactionActions pendingActions) {
        if (this.mActivityClientRecord == null) {
            return;
        }
        Trace.traceBegin(64L, "activityRestart");
        client.handleRelaunchActivity(this.mActivityClientRecord, pendingActions);
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(
            ClientTransactionHandler client, PendingTransactionActions pendingActions) {
        ActivityThread.ActivityClientRecord r = getActivityClientRecord(client);
        client.reportRelaunch(r);
    }

    private ActivityRelaunchItem() {}

    public static ActivityRelaunchItem obtain(
            IBinder activityToken,
            List<ResultInfo> pendingResults,
            List<ReferrerIntent> pendingNewIntents,
            int configChanges,
            MergedConfiguration config,
            boolean preserveWindow,
            ActivityWindowInfo activityWindowInfo) {
        ActivityRelaunchItem instance =
                (ActivityRelaunchItem) ObjectPool.obtain(ActivityRelaunchItem.class);
        if (instance == null) {
            instance = new ActivityRelaunchItem();
        }
        instance.setActivityToken(activityToken);
        instance.mPendingResults = pendingResults != null ? new ArrayList(pendingResults) : null;
        instance.mPendingNewIntents =
                pendingNewIntents != null ? new ArrayList(pendingNewIntents) : null;
        instance.mConfigChanges = configChanges;
        instance.mConfig = new MergedConfiguration(config);
        instance.mPreserveWindow = preserveWindow;
        instance.mActivityWindowInfo = new ActivityWindowInfo(activityWindowInfo);
        return instance;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem,
    // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mPendingResults = null;
        this.mPendingNewIntents = null;
        this.mConfigChanges = 0;
        this.mConfig = null;
        this.mPreserveWindow = false;
        this.mActivityClientRecord = null;
        this.mActivityWindowInfo = null;
        ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.mPendingResults, flags);
        dest.writeTypedList(this.mPendingNewIntents, flags);
        dest.writeInt(this.mConfigChanges);
        dest.writeTypedObject(this.mConfig, flags);
        dest.writeBoolean(this.mPreserveWindow);
        dest.writeTypedObject(this.mActivityWindowInfo, flags);
    }

    private ActivityRelaunchItem(Parcel in) {
        super(in);
        this.mPendingResults = in.createTypedArrayList(ResultInfo.CREATOR);
        this.mPendingNewIntents = in.createTypedArrayList(ReferrerIntent.CREATOR);
        this.mConfigChanges = in.readInt();
        this.mConfig = (MergedConfiguration) in.readTypedObject(MergedConfiguration.CREATOR);
        this.mPreserveWindow = in.readBoolean();
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
        ActivityRelaunchItem other = (ActivityRelaunchItem) o;
        return Objects.equals(this.mPendingResults, other.mPendingResults)
                && Objects.equals(this.mPendingNewIntents, other.mPendingNewIntents)
                && this.mConfigChanges == other.mConfigChanges
                && Objects.equals(this.mConfig, other.mConfig)
                && this.mPreserveWindow == other.mPreserveWindow
                && Objects.equals(this.mActivityWindowInfo, other.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return (((((((((((((17 * 31) + super.hashCode()) * 31)
                                                                                                + Objects
                                                                                                        .hashCode(
                                                                                                                this
                                                                                                                        .mPendingResults))
                                                                                        * 31)
                                                                                + Objects.hashCode(
                                                                                        this
                                                                                                .mPendingNewIntents))
                                                                        * 31)
                                                                + this.mConfigChanges)
                                                        * 31)
                                                + Objects.hashCode(this.mConfig))
                                        * 31)
                                + (this.mPreserveWindow ? 1 : 0))
                        * 31)
                + Objects.hashCode(this.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "ActivityRelaunchItem{"
                + super.toString()
                + ",pendingResults="
                + this.mPendingResults
                + ",pendingNewIntents="
                + this.mPendingNewIntents
                + ",configChanges="
                + this.mConfigChanges
                + ",config="
                + this.mConfig
                + ",preserveWindow="
                + this.mPreserveWindow
                + ",activityWindowInfo="
                + this.mActivityWindowInfo
                + "}";
    }
}
