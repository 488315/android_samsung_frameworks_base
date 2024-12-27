package android.app.servertransaction;

import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

public class StartActivityItem extends ActivityLifecycleItem {
    public static final Parcelable.Creator<StartActivityItem> CREATOR =
            new Parcelable.Creator<StartActivityItem>() { // from class:
                // android.app.servertransaction.StartActivityItem.1
                @Override // android.os.Parcelable.Creator
                public StartActivityItem createFromParcel(Parcel in) {
                    return new StartActivityItem(in);
                }

                @Override // android.os.Parcelable.Creator
                public StartActivityItem[] newArray(int size) {
                    return new StartActivityItem[size];
                }
            };
    private static final String TAG = "StartActivityItem";
    private ActivityOptions.SceneTransitionInfo mSceneTransitionInfo;

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(
            ClientTransactionHandler client,
            ActivityThread.ActivityClientRecord r,
            PendingTransactionActions pendingActions) {
        Trace.traceBegin(64L, "startActivityItem");
        client.handleStartActivity(r, pendingActions, this.mSceneTransitionInfo);
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 2;
    }

    private StartActivityItem() {}

    public static StartActivityItem obtain(
            IBinder activityToken, ActivityOptions.SceneTransitionInfo sceneTransitionInfo) {
        StartActivityItem instance = (StartActivityItem) ObjectPool.obtain(StartActivityItem.class);
        if (instance == null) {
            instance = new StartActivityItem();
        }
        instance.setActivityToken(activityToken);
        instance.mSceneTransitionInfo = sceneTransitionInfo;
        return instance;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem,
    // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mSceneTransitionInfo = null;
        ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedObject(this.mSceneTransitionInfo, flags);
    }

    private StartActivityItem(Parcel in) {
        super(in);
        this.mSceneTransitionInfo =
                (ActivityOptions.SceneTransitionInfo)
                        in.readTypedObject(ActivityOptions.SceneTransitionInfo.CREATOR);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        StartActivityItem other = (StartActivityItem) o;
        return (this.mSceneTransitionInfo == null) == (other.mSceneTransitionInfo == null);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        int result = (17 * 31) + super.hashCode();
        return (result * 31) + (this.mSceneTransitionInfo != null ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "StartActivityItem{"
                + super.toString()
                + ",sceneTransitionInfo="
                + this.mSceneTransitionInfo
                + "}";
    }
}
