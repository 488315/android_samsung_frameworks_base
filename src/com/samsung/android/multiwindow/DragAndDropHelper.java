package com.samsung.android.multiwindow;

import android.graphics.Rect;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.samsung.android.multiwindow.IDragAndDropClient;
import com.samsung.android.multiwindow.IDragAndDropControllerProxy;

/* loaded from: classes6.dex */
public class DragAndDropHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "DragAndDropHelper";
    private IBinder.DeathRecipient mDeathRecipient;
    private int mDisplayId;
    private final Rect mHiddenDropTargetArea;
    private final boolean mInitialDropTargetVisible;
    private IDragAndDropControllerProxy mServerProxy;
    private IDragAndDropClient mStub;

    private DragAndDropHelper(boolean z, Rect rect) {
        Rect rect2 = new Rect();
        this.mHiddenDropTargetArea = rect2;
        this.mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.samsung.android.multiwindow.DragAndDropHelper.1
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                DragAndDropHelper.this.dismiss();
            }
        };
        this.mStub = new IDragAndDropClient.Stub() { // from class: com.samsung.android.multiwindow.DragAndDropHelper.2
            @Override // com.samsung.android.multiwindow.IDragAndDropClient
            public void onConnected(IBinder iBinder, int i) throws RemoteException {
                iBinder.linkToDeath(DragAndDropHelper.this.mDeathRecipient, 0);
                DragAndDropHelper.this.mServerProxy = IDragAndDropControllerProxy.Stub.asInterface(iBinder);
                DragAndDropHelper.this.mDisplayId = i;
            }

            @Override // com.samsung.android.multiwindow.IDragAndDropClient
            public void onDisconnected() {
                DragAndDropHelper.this.dismiss();
            }

            @Override // com.samsung.android.multiwindow.IDragAndDropClient
            public boolean getInitialDropTargetVisible() {
                return DragAndDropHelper.this.mInitialDropTargetVisible;
            }

            @Override // com.samsung.android.multiwindow.IDragAndDropClient
            public Rect getHiddenDropTargetArea() {
                return DragAndDropHelper.this.mHiddenDropTargetArea;
            }
        };
        this.mInitialDropTargetVisible = z;
        rect2.set(rect);
    }

    public void show() {
        IDragAndDropControllerProxy iDragAndDropControllerProxy = this.mServerProxy;
        if (iDragAndDropControllerProxy == null) {
            Slog.w(TAG, "Abort to show.");
            return;
        }
        try {
            iDragAndDropControllerProxy.show(this.mDisplayId);
        } catch (RemoteException e) {
            Slog.w(TAG, "Failed to show. " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismiss() {
        IDragAndDropControllerProxy iDragAndDropControllerProxy = this.mServerProxy;
        if (iDragAndDropControllerProxy != null) {
            iDragAndDropControllerProxy.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
            this.mServerProxy = null;
        }
        this.mStub = null;
    }

    public IBinder getBinder() {
        return this.mStub.asBinder();
    }

    public static class Builder {
        private boolean mInitialDropTargetVisible = true;
        private Rect mHiddenDropTargetArea = new Rect();

        public Builder setInitialDropTargetVisible(boolean z) {
            this.mInitialDropTargetVisible = z;
            return this;
        }

        public Builder setHiddenDropTargetArea(Rect rect) {
            this.mHiddenDropTargetArea.set(rect);
            return this;
        }

        public DragAndDropHelper build() {
            return new DragAndDropHelper(this.mInitialDropTargetVisible, this.mHiddenDropTargetArea);
        }
    }
}
