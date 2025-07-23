package android.app.servertransaction;

import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

/* loaded from: classes.dex */
public class StartActivityItem extends ActivityLifecycleItem {
    public static final Parcelable.Creator<StartActivityItem> CREATOR = new Parcelable.Creator<StartActivityItem>() { // from class: android.app.servertransaction.StartActivityItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StartActivityItem createFromParcel(Parcel parcel) {
            return new StartActivityItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StartActivityItem[] newArray(int i) {
            return new StartActivityItem[i];
        }
    };
    private final ActivityOptions.SceneTransitionInfo mSceneTransitionInfo;

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 2;
    }

    public StartActivityItem(IBinder iBinder, ActivityOptions.SceneTransitionInfo sceneTransitionInfo) {
        super(iBinder);
        this.mSceneTransitionInfo = sceneTransitionInfo;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "startActivityItem");
        clientTransactionHandler.handleStartActivity(activityClientRecord, pendingTransactionActions, this.mSceneTransitionInfo);
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mSceneTransitionInfo, i);
    }

    private StartActivityItem(Parcel parcel) {
        super(parcel);
        this.mSceneTransitionInfo = (ActivityOptions.SceneTransitionInfo) parcel.readTypedObject(ActivityOptions.SceneTransitionInfo.CREATOR);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return (this.mSceneTransitionInfo == null) == (((StartActivityItem) obj).mSceneTransitionInfo == null);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((527 + super.hashCode()) * 31) + (this.mSceneTransitionInfo != null ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "StartActivityItem{" + super.toString() + ",sceneTransitionInfo=" + this.mSceneTransitionInfo + "}";
    }
}
