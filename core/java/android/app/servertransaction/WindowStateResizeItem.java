package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Log;
import android.util.MergedConfiguration;
import android.view.IWindow;
import android.view.InsetsState;
import android.window.ActivityWindowInfo;
import android.window.ClientWindowFrames;
import java.util.Objects;

/* loaded from: classes.dex */
public class WindowStateResizeItem extends WindowStateTransactionItem {
    public static final Parcelable.Creator<WindowStateResizeItem> CREATOR = new Parcelable.Creator<WindowStateResizeItem>() { // from class: android.app.servertransaction.WindowStateResizeItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowStateResizeItem createFromParcel(Parcel in) {
            return new WindowStateResizeItem(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowStateResizeItem[] newArray(int size) {
            return new WindowStateResizeItem[size];
        }
    };
    private static final String TAG = "WindowStateResizeItem";
    private ActivityWindowInfo mActivityWindowInfo;
    private boolean mAlwaysConsumeSystemBars;
    private MergedConfiguration mConfiguration;
    private int mDisplayId;
    private boolean mDragResizing;
    private boolean mForceLayout;
    private ClientWindowFrames mFrames;
    private InsetsState mInsetsState;
    private boolean mReportDraw;
    private int mSyncSeqId;

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public void execute(ClientTransactionHandler client, IWindow window, PendingTransactionActions pendingActions) {
        Trace.traceBegin(32L, this.mReportDraw ? "windowResizedReport" : "windowResized");
        try {
            window.resized(this.mFrames, this.mReportDraw, this.mConfiguration, this.mInsetsState, this.mForceLayout, this.mAlwaysConsumeSystemBars, this.mDisplayId, this.mSyncSeqId, this.mDragResizing, this.mActivityWindowInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "The original window no longer exists in the new process", e);
        }
        Trace.traceEnd(32L);
    }

    private WindowStateResizeItem() {
    }

    public static WindowStateResizeItem obtain(IWindow window, ClientWindowFrames frames, boolean reportDraw, MergedConfiguration configuration, InsetsState insetsState, boolean forceLayout, boolean alwaysConsumeSystemBars, int displayId, int syncSeqId, boolean dragResizing, ActivityWindowInfo activityWindowInfo) {
        ActivityWindowInfo activityWindowInfo2;
        WindowStateResizeItem instance = (WindowStateResizeItem) ObjectPool.obtain(WindowStateResizeItem.class);
        if (instance == null) {
            instance = new WindowStateResizeItem();
        }
        instance.setWindow(window);
        instance.mFrames = new ClientWindowFrames(frames);
        instance.mReportDraw = reportDraw;
        instance.mConfiguration = new MergedConfiguration(configuration);
        instance.mInsetsState = new InsetsState(insetsState, true);
        instance.mForceLayout = forceLayout;
        instance.mAlwaysConsumeSystemBars = alwaysConsumeSystemBars;
        instance.mDisplayId = displayId;
        instance.mSyncSeqId = syncSeqId;
        instance.mDragResizing = dragResizing;
        if (activityWindowInfo != null) {
            activityWindowInfo2 = new ActivityWindowInfo(activityWindowInfo);
        } else {
            activityWindowInfo2 = null;
        }
        instance.mActivityWindowInfo = activityWindowInfo2;
        return instance;
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mFrames = null;
        this.mReportDraw = false;
        this.mConfiguration = null;
        this.mInsetsState = null;
        this.mForceLayout = false;
        this.mAlwaysConsumeSystemBars = false;
        this.mDisplayId = -1;
        this.mSyncSeqId = -1;
        this.mDragResizing = false;
        this.mActivityWindowInfo = null;
        ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedObject(this.mFrames, flags);
        dest.writeBoolean(this.mReportDraw);
        dest.writeTypedObject(this.mConfiguration, flags);
        dest.writeTypedObject(this.mInsetsState, flags);
        dest.writeBoolean(this.mForceLayout);
        dest.writeBoolean(this.mAlwaysConsumeSystemBars);
        dest.writeInt(this.mDisplayId);
        dest.writeInt(this.mSyncSeqId);
        dest.writeBoolean(this.mDragResizing);
        dest.writeTypedObject(this.mActivityWindowInfo, flags);
    }

    private WindowStateResizeItem(Parcel in) {
        super(in);
        this.mFrames = (ClientWindowFrames) in.readTypedObject(ClientWindowFrames.CREATOR);
        this.mReportDraw = in.readBoolean();
        this.mConfiguration = (MergedConfiguration) in.readTypedObject(MergedConfiguration.CREATOR);
        this.mInsetsState = (InsetsState) in.readTypedObject(InsetsState.CREATOR);
        this.mForceLayout = in.readBoolean();
        this.mAlwaysConsumeSystemBars = in.readBoolean();
        this.mDisplayId = in.readInt();
        this.mSyncSeqId = in.readInt();
        this.mDragResizing = in.readBoolean();
        this.mActivityWindowInfo = (ActivityWindowInfo) in.readTypedObject(ActivityWindowInfo.CREATOR);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        WindowStateResizeItem other = (WindowStateResizeItem) o;
        return Objects.equals(this.mFrames, other.mFrames) && this.mReportDraw == other.mReportDraw && Objects.equals(this.mConfiguration, other.mConfiguration) && Objects.equals(this.mInsetsState, other.mInsetsState) && this.mForceLayout == other.mForceLayout && this.mAlwaysConsumeSystemBars == other.mAlwaysConsumeSystemBars && this.mDisplayId == other.mDisplayId && this.mSyncSeqId == other.mSyncSeqId && this.mDragResizing == other.mDragResizing && Objects.equals(this.mActivityWindowInfo, other.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public int hashCode() {
        return (((((((((((((((((((((17 * 31) + super.hashCode()) * 31) + Objects.hashCode(this.mFrames)) * 31) + (this.mReportDraw ? 1 : 0)) * 31) + Objects.hashCode(this.mConfiguration)) * 31) + Objects.hashCode(this.mInsetsState)) * 31) + (this.mForceLayout ? 1 : 0)) * 31) + (this.mAlwaysConsumeSystemBars ? 1 : 0)) * 31) + this.mDisplayId) * 31) + this.mSyncSeqId) * 31) + (this.mDragResizing ? 1 : 0)) * 31) + Objects.hashCode(this.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public String toString() {
        return "WindowStateResizeItem{" + super.toString() + ", reportDrawn=" + this.mReportDraw + ", configuration=" + this.mConfiguration + ", activityWindowInfo=" + this.mActivityWindowInfo + "}";
    }
}
