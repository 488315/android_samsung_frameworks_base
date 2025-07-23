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
    public static final Parcelable.Creator<MoveToDisplayItem> CREATOR = new Parcelable.Creator<MoveToDisplayItem>() { // from class: android.app.servertransaction.MoveToDisplayItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MoveToDisplayItem createFromParcel(Parcel parcel) {
            return new MoveToDisplayItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MoveToDisplayItem[] newArray(int i) {
            return new MoveToDisplayItem[i];
        }
    };
    private final ActivityWindowInfo mActivityWindowInfo;
    private final Configuration mConfiguration;
    private final int mTargetDisplayId;

    public MoveToDisplayItem(IBinder iBinder, int i, Configuration configuration, ActivityWindowInfo activityWindowInfo) {
        super(iBinder);
        this.mTargetDisplayId = i;
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
        Trace.traceBegin(64L, "activityMovedToDisplay");
        clientTransactionHandler.handleActivityConfigurationChanged(activityClientRecord, this.mConfiguration, this.mTargetDisplayId, this.mActivityWindowInfo);
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mTargetDisplayId);
        parcel.writeTypedObject(this.mConfiguration, i);
        parcel.writeTypedObject(this.mActivityWindowInfo, i);
    }

    private MoveToDisplayItem(Parcel parcel) {
        super(parcel);
        this.mTargetDisplayId = parcel.readInt();
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
        MoveToDisplayItem moveToDisplayItem = (MoveToDisplayItem) obj;
        return this.mTargetDisplayId == moveToDisplayItem.mTargetDisplayId && Objects.equals(this.mConfiguration, moveToDisplayItem.mConfiguration) && Objects.equals(this.mActivityWindowInfo, moveToDisplayItem.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((((527 + super.hashCode()) * 31) + this.mTargetDisplayId) * 31) + this.mConfiguration.hashCode()) * 31) + Objects.hashCode(this.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "MoveToDisplayItem{" + super.toString() + ",targetDisplayId=" + this.mTargetDisplayId + ",configuration=" + this.mConfiguration + ",activityWindowInfo=" + this.mActivityWindowInfo + "}";
    }
}
