package com.android.p038wm.shell.pip.phone;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.MagnificationSpec;
import android.view.SurfaceControl;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IAccessibilityInteractionConnection;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import android.window.ScreenCapture;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.pip.PipBoundsState;
import com.android.p038wm.shell.pip.PipSnapAlgorithm;
import com.android.p038wm.shell.pip.PipTaskOrganizer;
import com.android.p038wm.shell.pip.phone.PipAccessibilityInteractionConnection;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipAccessibilityInteractionConnection {
    public List mAccessibilityNodeInfoList;
    public final AccessibilityCallbacks mCallbacks;
    public final Context mContext;
    public final ShellExecutor mMainExcutor;
    public final PipMotionHelper mMotionHelper;
    public final PipBoundsState mPipBoundsState;
    public final PipSnapAlgorithm mSnapAlgorithm;
    public final PipTaskOrganizer mTaskOrganizer;
    public final Runnable mUnstashCallback;
    public final Runnable mUpdateMovementBoundCallback;
    public final Rect mNormalBounds = new Rect();
    public final Rect mExpandedBounds = new Rect();
    public final Rect mNormalMovementBounds = new Rect();
    public final Rect mExpandedMovementBounds = new Rect();
    public final Rect mTmpBounds = new Rect();
    public final PipAccessibilityInteractionConnectionImpl mConnectionImpl = new PipAccessibilityInteractionConnectionImpl(this, 0);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface AccessibilityCallbacks {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PipAccessibilityInteractionConnectionImpl extends IAccessibilityInteractionConnection.Stub {
        public /* synthetic */ PipAccessibilityInteractionConnectionImpl(PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection, int i) {
            this();
        }

        public final void findAccessibilityNodeInfoByAccessibilityId(long j, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr, Bundle bundle) {
            ((HandlerExecutor) PipAccessibilityInteractionConnection.this.mMainExcutor).execute(new RunnableC4052xdda2519f(this, j, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec, bundle));
        }

        public final void findAccessibilityNodeInfosByText(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
            ((HandlerExecutor) PipAccessibilityInteractionConnection.this.mMainExcutor).execute(new RunnableC4052xdda2519f(this, j, str, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec, 0));
        }

        public final void findAccessibilityNodeInfosByViewId(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
            ((HandlerExecutor) PipAccessibilityInteractionConnection.this.mMainExcutor).execute(new RunnableC4052xdda2519f(this, j, str, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec, 1));
        }

        public final void findFocus(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
            ((HandlerExecutor) PipAccessibilityInteractionConnection.this.mMainExcutor).execute(new RunnableC4050xdda2519d(this, j, i, region, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2, magnificationSpec, 1));
        }

        public final void focusSearch(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
            ((HandlerExecutor) PipAccessibilityInteractionConnection.this.mMainExcutor).execute(new RunnableC4050xdda2519d(this, j, i, region, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2, magnificationSpec, 0));
        }

        public final void performAccessibilityAction(final long j, final int i, final Bundle bundle, final int i2, final IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, final int i3, final int i4, final long j2) {
            ((HandlerExecutor) PipAccessibilityInteractionConnection.this.mMainExcutor).execute(new Runnable(j, i, bundle, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2) { // from class: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda1
                public final /* synthetic */ long f$1;
                public final /* synthetic */ int f$2;
                public final /* synthetic */ Bundle f$3;
                public final /* synthetic */ int f$4;
                public final /* synthetic */ IAccessibilityInteractionConnectionCallback f$5;

                @Override // java.lang.Runnable
                public final void run() {
                    PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl = PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl.this;
                    long j3 = this.f$1;
                    int i5 = this.f$2;
                    Bundle bundle2 = this.f$3;
                    int i6 = this.f$4;
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback2 = this.f$5;
                    final PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection = PipAccessibilityInteractionConnection.this;
                    pipAccessibilityInteractionConnection.getClass();
                    boolean z = false;
                    z = false;
                    if (j3 == AccessibilityNodeInfo.ROOT_NODE_ID) {
                        PipBoundsState pipBoundsState = pipAccessibilityInteractionConnection.mPipBoundsState;
                        final int i7 = 1;
                        if (i5 == R.id.action_pip_resize) {
                            int width = pipBoundsState.getBounds().width();
                            Rect rect = pipAccessibilityInteractionConnection.mNormalBounds;
                            int width2 = rect.width();
                            PipTaskOrganizer pipTaskOrganizer = pipAccessibilityInteractionConnection.mTaskOrganizer;
                            Rect rect2 = pipAccessibilityInteractionConnection.mNormalMovementBounds;
                            Rect rect3 = pipAccessibilityInteractionConnection.mExpandedMovementBounds;
                            PipSnapAlgorithm pipSnapAlgorithm = pipAccessibilityInteractionConnection.mSnapAlgorithm;
                            if (width == width2 && pipBoundsState.getBounds().height() == rect.height()) {
                                float snapFraction = pipSnapAlgorithm.getSnapFraction(0, pipBoundsState.getBounds(), rect2);
                                Rect rect4 = pipAccessibilityInteractionConnection.mExpandedBounds;
                                PipSnapAlgorithm.applySnapFraction(snapFraction, rect4, rect3);
                                pipTaskOrganizer.scheduleFinishResizePip(rect4, new Consumer() { // from class: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        switch (i7) {
                                            case 0:
                                                PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection2 = pipAccessibilityInteractionConnection;
                                                pipAccessibilityInteractionConnection2.mMotionHelper.synchronizePinnedStackBounds();
                                                pipAccessibilityInteractionConnection2.mUpdateMovementBoundCallback.run();
                                                break;
                                            default:
                                                PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection3 = pipAccessibilityInteractionConnection;
                                                pipAccessibilityInteractionConnection3.mMotionHelper.synchronizePinnedStackBounds();
                                                pipAccessibilityInteractionConnection3.mUpdateMovementBoundCallback.run();
                                                break;
                                        }
                                    }
                                });
                            } else {
                                PipSnapAlgorithm.applySnapFraction(pipSnapAlgorithm.getSnapFraction(0, pipBoundsState.getBounds(), rect3), rect, rect2);
                                final int i8 = z ? 1 : 0;
                                pipTaskOrganizer.scheduleFinishResizePip(rect, new Consumer() { // from class: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        switch (i8) {
                                            case 0:
                                                PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection2 = pipAccessibilityInteractionConnection;
                                                pipAccessibilityInteractionConnection2.mMotionHelper.synchronizePinnedStackBounds();
                                                pipAccessibilityInteractionConnection2.mUpdateMovementBoundCallback.run();
                                                break;
                                            default:
                                                PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection3 = pipAccessibilityInteractionConnection;
                                                pipAccessibilityInteractionConnection3.mMotionHelper.synchronizePinnedStackBounds();
                                                pipAccessibilityInteractionConnection3.mUpdateMovementBoundCallback.run();
                                                break;
                                        }
                                    }
                                });
                            }
                        } else {
                            PipMotionHelper pipMotionHelper = pipAccessibilityInteractionConnection.mMotionHelper;
                            if (i5 == R.id.action_pip_stash) {
                                pipMotionHelper.getClass();
                                Rect rect5 = new Rect();
                                PipBoundsState pipBoundsState2 = pipMotionHelper.mPipBoundsState;
                                pipBoundsState2.getDisplayLayout().stableInsets(false);
                                int i9 = pipBoundsState2.getBounds().left == pipBoundsState2.mMovementBounds.left ? 1 : 2;
                                float width3 = i9 == 1 ? (pipBoundsState2.mStashOffset - pipBoundsState2.getBounds().width()) + pipBoundsState2.getStashInsets().left : (pipBoundsState2.getDisplayBounds().right - pipBoundsState2.mStashOffset) - pipBoundsState2.getStashInsets().right;
                                rect5.set((int) width3, pipBoundsState2.getBounds().top, (int) (width3 + pipBoundsState2.getBounds().width()), pipBoundsState2.getBounds().bottom);
                                pipMotionHelper.resizeAndAnimatePipUnchecked(rect5);
                                pipBoundsState2.setStashed(i9, false);
                            } else if (i5 == R.id.action_pip_unstash) {
                                pipAccessibilityInteractionConnection.mUnstashCallback.run();
                                pipBoundsState.setStashed(0, false);
                            } else if (i5 == 16) {
                                PipTouchHandler pipTouchHandler = ((PipTouchHandler$$ExternalSyntheticLambda3) pipAccessibilityInteractionConnection.mCallbacks).f$0;
                                pipTouchHandler.mMenuController.showMenu(1, pipTouchHandler.mPipBoundsState.getBounds(), true, pipTouchHandler.willResizeMenu(), pipTouchHandler.mPipTaskOrganizer.shouldShowSplitMenu());
                            } else if (i5 == 262144) {
                                pipMotionHelper.expandLeavePip(false, false);
                            } else if (i5 == 1048576) {
                                pipMotionHelper.dismissPip();
                            } else if (i5 == 16908354) {
                                int i10 = bundle2.getInt("ACTION_ARGUMENT_MOVE_WINDOW_X");
                                int i11 = bundle2.getInt("ACTION_ARGUMENT_MOVE_WINDOW_Y");
                                new Rect().set(pipBoundsState.getBounds());
                                Rect rect6 = pipAccessibilityInteractionConnection.mTmpBounds;
                                rect6.offsetTo(i10, i11);
                                pipMotionHelper.movePip(rect6, false);
                            }
                        }
                        z = true;
                    }
                    try {
                        iAccessibilityInteractionConnectionCallback2.setPerformAccessibilityActionResult(z, i6);
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        private PipAccessibilityInteractionConnectionImpl() {
        }

        public final void attachAccessibilityOverlayToWindow(SurfaceControl surfaceControl) {
        }

        public final void clearAccessibilityFocus() {
        }

        public final void notifyOutsideTouch() {
        }

        public final void takeScreenshotOfWindow(int i, ScreenCapture.ScreenCaptureListener screenCaptureListener, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        }
    }

    public PipAccessibilityInteractionConnection(Context context, PipBoundsState pipBoundsState, PipMotionHelper pipMotionHelper, PipTaskOrganizer pipTaskOrganizer, PipSnapAlgorithm pipSnapAlgorithm, AccessibilityCallbacks accessibilityCallbacks, Runnable runnable, Runnable runnable2, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mMainExcutor = shellExecutor;
        this.mPipBoundsState = pipBoundsState;
        this.mMotionHelper = pipMotionHelper;
        this.mTaskOrganizer = pipTaskOrganizer;
        this.mSnapAlgorithm = pipSnapAlgorithm;
        this.mUpdateMovementBoundCallback = runnable;
        this.mUnstashCallback = runnable2;
        this.mCallbacks = accessibilityCallbacks;
    }

    public final List getNodeList() {
        if (this.mAccessibilityNodeInfoList == null) {
            this.mAccessibilityNodeInfoList = new ArrayList(1);
        }
        AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain();
        obtain.setSourceNodeId(AccessibilityNodeInfo.ROOT_NODE_ID, -3);
        obtain.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
        obtain.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS);
        obtain.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW);
        obtain.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
        Context context = this.mContext;
        obtain.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_pip_resize, context.getString(R.string.accessibility_action_pip_resize)));
        obtain.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_pip_stash, context.getString(R.string.accessibility_action_pip_stash)));
        obtain.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_pip_unstash, context.getString(R.string.accessibility_action_pip_unstash)));
        obtain.setImportantForAccessibility(true);
        obtain.setClickable(true);
        obtain.setVisibleToUser(true);
        this.mAccessibilityNodeInfoList.clear();
        this.mAccessibilityNodeInfoList.add(obtain);
        return this.mAccessibilityNodeInfoList;
    }
}
