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

/* loaded from: classes.dex */
public class ActivityRelaunchItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<ActivityRelaunchItem> CREATOR = new Parcelable.Creator<ActivityRelaunchItem>() { // from class: android.app.servertransaction.ActivityRelaunchItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityRelaunchItem createFromParcel(Parcel parcel) {
            return new ActivityRelaunchItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityRelaunchItem[] newArray(int i) {
            return new ActivityRelaunchItem[i];
        }
    };
    private static final String TAG = "ActivityRelaunchItem";
    private ActivityThread.ActivityClientRecord mActivityClientRecord;
    private final ActivityWindowInfo mActivityWindowInfo;
    private final MergedConfiguration mConfig;
    private final int mConfigChanges;
    private final List<ReferrerIntent> mPendingNewIntents;
    private final List<ResultInfo> mPendingResults;
    private final boolean mPreserveWindow;

    public ActivityRelaunchItem(IBinder iBinder, List<ResultInfo> list, List<ReferrerIntent> list2, int i, MergedConfiguration mergedConfiguration, boolean z, ActivityWindowInfo activityWindowInfo) {
        super(iBinder);
        this.mPendingResults = list != null ? new ArrayList(list) : null;
        this.mPendingNewIntents = list2 != null ? new ArrayList(list2) : null;
        this.mConfig = new MergedConfiguration(mergedConfiguration);
        this.mActivityWindowInfo = new ActivityWindowInfo(activityWindowInfo);
        this.mConfigChanges = i;
        this.mPreserveWindow = z;
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(ClientTransactionHandler clientTransactionHandler) {
        if (!clientTransactionHandler.isExecutingLocalTransaction()) {
            CompatibilityInfo.applyOverrideIfNeeded(this.mConfig);
        }
        this.mActivityClientRecord = clientTransactionHandler.prepareRelaunchActivity(getActivityToken(), this.mPendingResults, this.mPendingNewIntents, this.mConfigChanges, this.mConfig, this.mPreserveWindow, this.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
        if (this.mActivityClientRecord == null) {
            return;
        }
        Trace.traceBegin(64L, "activityRestart");
        clientTransactionHandler.handleRelaunchActivity(this.mActivityClientRecord, pendingTransactionActions);
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.reportRelaunch(getActivityClientRecord(clientTransactionHandler));
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mPendingResults, i);
        parcel.writeTypedList(this.mPendingNewIntents, i);
        parcel.writeTypedObject(this.mConfig, i);
        parcel.writeTypedObject(this.mActivityWindowInfo, i);
        parcel.writeInt(this.mConfigChanges);
        parcel.writeBoolean(this.mPreserveWindow);
    }

    private ActivityRelaunchItem(Parcel parcel) {
        super(parcel);
        this.mPendingResults = parcel.createTypedArrayList(ResultInfo.CREATOR);
        this.mPendingNewIntents = parcel.createTypedArrayList(ReferrerIntent.CREATOR);
        this.mConfig = (MergedConfiguration) Objects.requireNonNull((MergedConfiguration) parcel.readTypedObject(MergedConfiguration.CREATOR));
        this.mActivityWindowInfo = (ActivityWindowInfo) Objects.requireNonNull((ActivityWindowInfo) parcel.readTypedObject(ActivityWindowInfo.CREATOR));
        this.mConfigChanges = parcel.readInt();
        this.mPreserveWindow = parcel.readBoolean();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        ActivityRelaunchItem activityRelaunchItem = (ActivityRelaunchItem) obj;
        return Objects.equals(this.mPendingResults, activityRelaunchItem.mPendingResults) && Objects.equals(this.mPendingNewIntents, activityRelaunchItem.mPendingNewIntents) && Objects.equals(this.mConfig, activityRelaunchItem.mConfig) && Objects.equals(this.mActivityWindowInfo, activityRelaunchItem.mActivityWindowInfo) && this.mConfigChanges == activityRelaunchItem.mConfigChanges && this.mPreserveWindow == activityRelaunchItem.mPreserveWindow;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((((((((((527 + super.hashCode()) * 31) + Objects.hashCode(this.mPendingResults)) * 31) + Objects.hashCode(this.mPendingNewIntents)) * 31) + Objects.hashCode(this.mConfig)) * 31) + Objects.hashCode(this.mActivityWindowInfo)) * 31) + this.mConfigChanges) * 31) + (this.mPreserveWindow ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "ActivityRelaunchItem{" + super.toString() + ",pendingResults=" + this.mPendingResults + ",pendingNewIntents=" + this.mPendingNewIntents + ",config=" + this.mConfig + ",activityWindowInfo=" + this.mActivityWindowInfo + ",configChanges=" + this.mConfigChanges + ",preserveWindow=" + this.mPreserveWindow + "}";
    }
}
