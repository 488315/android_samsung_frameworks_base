package com.samsung.android.multiwindow;

import android.graphics.Rect;
import android.p009os.IBinder;
import android.p009os.RemoteException;
import android.util.Slog;
import com.samsung.android.multiwindow.DragAndDropHelper;
import com.samsung.android.multiwindow.IDragAndDropClient;
import com.samsung.android.multiwindow.IDragAndDropControllerProxy;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes5.dex */
public class DragAndDropHelper {
    private static final boolean DEBUG = CoreRune.SAFE_DEBUG;
    private static final String TAG = "DragAndDropHelper";
    private int mDisplayId;
    private final Rect mHiddenDropTargetArea;
    private final boolean mInitialDropTargetVisible;
    private IDragAndDropControllerProxy mServerProxy;
    private IDragAndDropClient mStub;

    /* renamed from: com.samsung.android.multiwindow.DragAndDropHelper$1 */
    class BinderC51991 extends IDragAndDropClient.Stub {
        BinderC51991() {
        }

        @Override // com.samsung.android.multiwindow.IDragAndDropClient
        public void onConnected(IBinder serverProxy, int displayId) throws RemoteException {
            if (DragAndDropHelper.DEBUG) {
                Slog.m117i(DragAndDropHelper.TAG, "onConnected");
            }
            serverProxy.linkToDeath(new IBinder.DeathRecipient() { // from class: com.samsung.android.multiwindow.DragAndDropHelper$1$$ExternalSyntheticLambda0
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    DragAndDropHelper.BinderC51991.this.lambda$onConnected$0();
                }
            }, 0);
            DragAndDropHelper.this.mServerProxy = IDragAndDropControllerProxy.Stub.asInterface(serverProxy);
            DragAndDropHelper.this.mDisplayId = displayId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConnected$0() {
            if (DragAndDropHelper.DEBUG) {
                Slog.m113d(DragAndDropHelper.TAG, "binderDied");
            }
            DragAndDropHelper.this.dismiss();
        }

        @Override // com.samsung.android.multiwindow.IDragAndDropClient
        public void onDisconnected() {
            if (DragAndDropHelper.DEBUG) {
                Slog.m117i(DragAndDropHelper.TAG, "onDisconnected");
            }
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
    }

    private DragAndDropHelper(boolean shellDropTargetVisibility, Rect hiddenDropTargetArea) {
        Rect rect = new Rect();
        this.mHiddenDropTargetArea = rect;
        this.mStub = new BinderC51991();
        this.mInitialDropTargetVisible = shellDropTargetVisibility;
        rect.set(hiddenDropTargetArea);
    }

    public void show() {
        if (this.mServerProxy == null) {
            Slog.m121w(TAG, "Abort to show.");
            return;
        }
        if (DEBUG) {
            Slog.m113d(TAG, "Requested to show");
        }
        try {
            this.mServerProxy.show(this.mDisplayId);
        } catch (RemoteException e) {
            Slog.m121w(TAG, "Failed to show. " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismiss() {
        this.mServerProxy = null;
    }

    public IBinder getBinder() {
        return this.mStub.asBinder();
    }

    public static class Builder {
        private boolean mInitialDropTargetVisible = true;
        private Rect mHiddenDropTargetArea = new Rect();

        public Builder setInitialDropTargetVisible(boolean visible) {
            this.mInitialDropTargetVisible = visible;
            return this;
        }

        public Builder setHiddenDropTargetArea(Rect hiddenDropTargetArea) {
            this.mHiddenDropTargetArea.set(hiddenDropTargetArea);
            return this;
        }

        public DragAndDropHelper build() {
            return new DragAndDropHelper(this.mInitialDropTargetVisible, this.mHiddenDropTargetArea);
        }
    }
}
