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
        public WindowStateResizeItem createFromParcel(Parcel parcel) {
            return new WindowStateResizeItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowStateResizeItem[] newArray(int i) {
            return new WindowStateResizeItem[i];
        }
    };
    private static final String TAG = "WindowStateResizeItem";
    private final ActivityWindowInfo mActivityWindowInfo;
    private final boolean mAlwaysConsumeSystemBars;
    private final MergedConfiguration mConfiguration;
    private final int mDisplayId;
    private final boolean mDragResizing;
    private final boolean mForceLayout;
    private final ClientWindowFrames mFrames;
    private final InsetsState mInsetsState;
    private final boolean mReportDraw;
    private final int mSyncSeqId;

    public WindowStateResizeItem(IWindow iWindow, ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4, ActivityWindowInfo activityWindowInfo) {
        super(iWindow);
        this.mFrames = new ClientWindowFrames(clientWindowFrames);
        this.mConfiguration = new MergedConfiguration(mergedConfiguration);
        this.mInsetsState = new InsetsState(insetsState, true);
        if (activityWindowInfo != null) {
            this.mActivityWindowInfo = new ActivityWindowInfo(activityWindowInfo);
        } else {
            this.mActivityWindowInfo = null;
        }
        this.mReportDraw = z;
        this.mForceLayout = z2;
        this.mAlwaysConsumeSystemBars = z3;
        this.mDisplayId = i;
        this.mSyncSeqId = i2;
        this.mDragResizing = z4;
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, IWindow iWindow, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(32L, this.mReportDraw ? "windowResizedReport" : "windowResized");
        try {
            iWindow.resized(this.mFrames, this.mReportDraw, this.mConfiguration, this.mInsetsState, this.mForceLayout, this.mAlwaysConsumeSystemBars, this.mDisplayId, this.mSyncSeqId, this.mDragResizing, this.mActivityWindowInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "The original window no longer exists in the new process", e);
        }
        Trace.traceEnd(32L);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mFrames, i);
        parcel.writeBoolean(this.mReportDraw);
        parcel.writeTypedObject(this.mConfiguration, i);
        parcel.writeTypedObject(this.mInsetsState, i);
        parcel.writeBoolean(this.mForceLayout);
        parcel.writeBoolean(this.mAlwaysConsumeSystemBars);
        parcel.writeInt(this.mDisplayId);
        parcel.writeInt(this.mSyncSeqId);
        parcel.writeBoolean(this.mDragResizing);
        parcel.writeTypedObject(this.mActivityWindowInfo, i);
    }

    private WindowStateResizeItem(Parcel parcel) {
        super(parcel);
        this.mFrames = (ClientWindowFrames) Objects.requireNonNull((ClientWindowFrames) parcel.readTypedObject(ClientWindowFrames.CREATOR));
        this.mReportDraw = parcel.readBoolean();
        this.mConfiguration = (MergedConfiguration) Objects.requireNonNull((MergedConfiguration) parcel.readTypedObject(MergedConfiguration.CREATOR));
        this.mInsetsState = (InsetsState) Objects.requireNonNull((InsetsState) parcel.readTypedObject(InsetsState.CREATOR));
        this.mForceLayout = parcel.readBoolean();
        this.mAlwaysConsumeSystemBars = parcel.readBoolean();
        this.mDisplayId = parcel.readInt();
        this.mSyncSeqId = parcel.readInt();
        this.mDragResizing = parcel.readBoolean();
        this.mActivityWindowInfo = (ActivityWindowInfo) parcel.readTypedObject(ActivityWindowInfo.CREATOR);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        WindowStateResizeItem windowStateResizeItem = (WindowStateResizeItem) obj;
        return Objects.equals(this.mFrames, windowStateResizeItem.mFrames) && this.mReportDraw == windowStateResizeItem.mReportDraw && Objects.equals(this.mConfiguration, windowStateResizeItem.mConfiguration) && Objects.equals(this.mInsetsState, windowStateResizeItem.mInsetsState) && this.mForceLayout == windowStateResizeItem.mForceLayout && this.mAlwaysConsumeSystemBars == windowStateResizeItem.mAlwaysConsumeSystemBars && this.mDisplayId == windowStateResizeItem.mDisplayId && this.mSyncSeqId == windowStateResizeItem.mSyncSeqId && this.mDragResizing == windowStateResizeItem.mDragResizing && Objects.equals(this.mActivityWindowInfo, windowStateResizeItem.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public int hashCode() {
        return ((((((((((((((((((((527 + super.hashCode()) * 31) + Objects.hashCode(this.mFrames)) * 31) + (this.mReportDraw ? 1 : 0)) * 31) + Objects.hashCode(this.mConfiguration)) * 31) + Objects.hashCode(this.mInsetsState)) * 31) + (this.mForceLayout ? 1 : 0)) * 31) + (this.mAlwaysConsumeSystemBars ? 1 : 0)) * 31) + this.mDisplayId) * 31) + this.mSyncSeqId) * 31) + (this.mDragResizing ? 1 : 0)) * 31) + Objects.hashCode(this.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.WindowStateTransactionItem
    public String toString() {
        return "WindowStateResizeItem{" + super.toString() + ", reportDrawn=" + this.mReportDraw + ", configuration=" + this.mConfiguration + ", activityWindowInfo=" + this.mActivityWindowInfo + "}";
    }
}
