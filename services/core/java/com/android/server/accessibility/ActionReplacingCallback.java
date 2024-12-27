package com.android.server.accessibility;

import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Slog;
import android.view.MagnificationSpec;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IAccessibilityInteractionConnection;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ActionReplacingCallback
        extends IAccessibilityInteractionConnectionCallback.Stub {
    public final int mInteractionId;
    public AccessibilityNodeInfo mNodeFromOriginalWindow;
    public AccessibilityNodeInfo mNodeWithReplacementActions;
    public final int mNodeWithReplacementActionsInteractionId;
    public List mNodesFromOriginalWindow;
    public List mPrefetchedNodesFromOriginalWindow;
    public boolean mReplacementNodeIsReadyOrFailed;
    public final IAccessibilityInteractionConnectionCallback mServiceCallback;
    public final Object mLock = new Object();
    public boolean mSetFindNodeFromOriginalWindowCalled = false;
    public boolean mSetFindNodesFromOriginalWindowCalled = false;
    public boolean mSetPrefetchFromOriginalWindowCalled = false;

    public ActionReplacingCallback(
            IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback,
            IAccessibilityInteractionConnection iAccessibilityInteractionConnection,
            int i,
            int i2,
            long j) {
        this.mServiceCallback = iAccessibilityInteractionConnectionCallback;
        this.mInteractionId = i;
        int i3 = i + 1;
        this.mNodeWithReplacementActionsInteractionId = i3;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                iAccessibilityInteractionConnection.findAccessibilityNodeInfoByAccessibilityId(
                        AccessibilityNodeInfo.ROOT_NODE_ID,
                        (Region) null,
                        i3,
                        this,
                        0,
                        i2,
                        j,
                        (MagnificationSpec) null,
                        (float[]) null,
                        (Bundle) null);
            } catch (RemoteException unused) {
                this.mReplacementNodeIsReadyOrFailed = true;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List replaceActionsLocked(List list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                replaceActionsOnInfoLocked((AccessibilityNodeInfo) list.get(i));
            }
        }
        if (list == null) {
            return null;
        }
        return new ArrayList(list);
    }

    public final void replaceActionsOnInfoLocked(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfo2;
        accessibilityNodeInfo.removeAllActions();
        accessibilityNodeInfo.setClickable(false);
        accessibilityNodeInfo.setFocusable(false);
        accessibilityNodeInfo.setContextClickable(false);
        accessibilityNodeInfo.setScrollable(false);
        accessibilityNodeInfo.setLongClickable(false);
        accessibilityNodeInfo.setDismissable(false);
        if (accessibilityNodeInfo.getSourceNodeId() != AccessibilityNodeInfo.ROOT_NODE_ID
                || (accessibilityNodeInfo2 = this.mNodeWithReplacementActions) == null) {
            return;
        }
        List<AccessibilityNodeInfo.AccessibilityAction> actionList =
                accessibilityNodeInfo2.getActionList();
        if (actionList != null) {
            for (int i = 0; i < actionList.size(); i++) {
                accessibilityNodeInfo.addAction(actionList.get(i));
            }
            accessibilityNodeInfo.addAction(
                    AccessibilityNodeInfo.AccessibilityAction.ACTION_ACCESSIBILITY_FOCUS);
            accessibilityNodeInfo.addAction(
                    AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
        }
        accessibilityNodeInfo.setClickable(this.mNodeWithReplacementActions.isClickable());
        accessibilityNodeInfo.setFocusable(this.mNodeWithReplacementActions.isFocusable());
        accessibilityNodeInfo.setContextClickable(
                this.mNodeWithReplacementActions.isContextClickable());
        accessibilityNodeInfo.setScrollable(this.mNodeWithReplacementActions.isScrollable());
        accessibilityNodeInfo.setLongClickable(this.mNodeWithReplacementActions.isLongClickable());
        accessibilityNodeInfo.setDismissable(this.mNodeWithReplacementActions.isDismissable());
    }

    public final void replaceInfoActionsAndCallServiceIfReady() {
        boolean z;
        boolean z2;
        AccessibilityNodeInfo accessibilityNodeInfo;
        boolean z3;
        List list;
        List list2;
        AccessibilityNodeInfo accessibilityNodeInfo2;
        synchronized (this.mLock) {
            try {
                z = true;
                z2 =
                        this.mReplacementNodeIsReadyOrFailed
                                && this.mSetFindNodeFromOriginalWindowCalled;
                if (z2 && (accessibilityNodeInfo2 = this.mNodeFromOriginalWindow) != null) {
                    replaceActionsOnInfoLocked(accessibilityNodeInfo2);
                    this.mSetFindNodeFromOriginalWindowCalled = false;
                }
                accessibilityNodeInfo = this.mNodeFromOriginalWindow;
            } finally {
            }
        }
        if (z2) {
            try {
                this.mServiceCallback.setFindAccessibilityNodeInfoResult(
                        accessibilityNodeInfo, this.mInteractionId);
            } catch (RemoteException unused) {
            }
        }
        synchronized (this.mLock) {
            try {
                z3 =
                        this.mReplacementNodeIsReadyOrFailed
                                && this.mSetFindNodesFromOriginalWindowCalled;
                list = null;
                if (z3) {
                    list2 = replaceActionsLocked(this.mNodesFromOriginalWindow);
                    this.mSetFindNodesFromOriginalWindowCalled = false;
                } else {
                    list2 = null;
                }
            } finally {
            }
        }
        if (z3) {
            try {
                this.mServiceCallback.setFindAccessibilityNodeInfosResult(
                        list2, this.mInteractionId);
            } catch (RemoteException unused2) {
            }
        }
        synchronized (this.mLock) {
            try {
                if (!this.mReplacementNodeIsReadyOrFailed
                        || !this.mSetPrefetchFromOriginalWindowCalled) {
                    z = false;
                }
                if (z) {
                    list = replaceActionsLocked(this.mPrefetchedNodesFromOriginalWindow);
                    this.mSetPrefetchFromOriginalWindowCalled = false;
                }
            } finally {
            }
        }
        if (z) {
            try {
                this.mServiceCallback.setPrefetchAccessibilityNodeInfoResult(
                        list, this.mInteractionId);
            } catch (RemoteException unused3) {
            }
        }
    }

    public final void sendAttachOverlayResult(int i, int i2) {
        this.mServiceCallback.sendAttachOverlayResult(i, i2);
    }

    public final void sendTakeScreenshotOfWindowError(int i, int i2) {
        this.mServiceCallback.sendTakeScreenshotOfWindowError(i, i2);
    }

    public final void setFindAccessibilityNodeInfoResult(
            AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        synchronized (this.mLock) {
            try {
                if (i == this.mInteractionId) {
                    this.mNodeFromOriginalWindow = accessibilityNodeInfo;
                    this.mSetFindNodeFromOriginalWindowCalled = true;
                } else if (i != this.mNodeWithReplacementActionsInteractionId) {
                    Slog.e("ActionReplacingCallback", "Callback with unexpected interactionId");
                    return;
                } else {
                    this.mNodeWithReplacementActions = accessibilityNodeInfo;
                    this.mReplacementNodeIsReadyOrFailed = true;
                }
                replaceInfoActionsAndCallServiceIfReady();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setFindAccessibilityNodeInfosResult(List list, int i) {
        synchronized (this.mLock) {
            try {
                if (i == this.mInteractionId) {
                    this.mNodesFromOriginalWindow = list;
                    this.mSetFindNodesFromOriginalWindowCalled = true;
                } else {
                    if (i != this.mNodeWithReplacementActionsInteractionId) {
                        Slog.e("ActionReplacingCallback", "Callback with unexpected interactionId");
                        return;
                    }
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        AccessibilityNodeInfo accessibilityNodeInfo =
                                (AccessibilityNodeInfo) list.get(i2);
                        if (accessibilityNodeInfo.getSourceNodeId()
                                == AccessibilityNodeInfo.ROOT_NODE_ID) {
                            this.mNodeWithReplacementActions = accessibilityNodeInfo;
                        }
                    }
                    this.mReplacementNodeIsReadyOrFailed = true;
                }
                replaceInfoActionsAndCallServiceIfReady();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setPerformAccessibilityActionResult(boolean z, int i) {
        this.mServiceCallback.setPerformAccessibilityActionResult(z, i);
    }

    public final void setPrefetchAccessibilityNodeInfoResult(List list, int i) {
        synchronized (this.mLock) {
            if (i != this.mInteractionId) {
                Slog.e("ActionReplacingCallback", "Callback with unexpected interactionId");
                return;
            }
            this.mPrefetchedNodesFromOriginalWindow = list;
            this.mSetPrefetchFromOriginalWindowCalled = true;
            replaceInfoActionsAndCallServiceIfReady();
        }
    }
}
