package com.android.wm.shell.pip.phone;

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
import android.view.accessibility.IWindowSurfaceInfoCallback;
import android.window.ScreenCapture;
import com.android.systemui.R;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipAccessibilityInteractionConnection {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface AccessibilityCallbacks {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PipAccessibilityInteractionConnectionImpl extends IAccessibilityInteractionConnection.Stub {
        public /* synthetic */ PipAccessibilityInteractionConnectionImpl(PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection, int i) {
            this();
        }

        public final void findAccessibilityNodeInfoByAccessibilityId(final long j, final Region region, final int i, final IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, final int i2, final int i3, final long j2, final MagnificationSpec magnificationSpec, float[] fArr, final Bundle bundle) {
            PipAccessibilityInteractionConnection.this.mMainExcutor.execute(new Runnable(j, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec, bundle) { // from class: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda0
                public final /* synthetic */ long f$1;
                public final /* synthetic */ int f$3;
                public final /* synthetic */ IAccessibilityInteractionConnectionCallback f$4;

                {
                    this.f$3 = i;
                    this.f$4 = iAccessibilityInteractionConnectionCallback;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl = PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl.this;
                    long j3 = this.f$1;
                    int i4 = this.f$3;
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback2 = this.f$4;
                    PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection = PipAccessibilityInteractionConnection.this;
                    pipAccessibilityInteractionConnection.getClass();
                    try {
                        iAccessibilityInteractionConnectionCallback2.setFindAccessibilityNodeInfosResult(j3 == AccessibilityNodeInfo.ROOT_NODE_ID ? pipAccessibilityInteractionConnection.getNodeList() : null, i4);
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        public final void findAccessibilityNodeInfosByText(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
            PipAccessibilityInteractionConnection.this.mMainExcutor.execute(new PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda1(this, j, str, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec, 1));
        }

        public final void findAccessibilityNodeInfosByViewId(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
            PipAccessibilityInteractionConnection.this.mMainExcutor.execute(new PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda1(this, j, str, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec, 3));
        }

        public final void findFocus(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
            PipAccessibilityInteractionConnection.this.mMainExcutor.execute(new PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda1(this, j, i, region, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2, magnificationSpec, 2));
        }

        public final void focusSearch(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
            PipAccessibilityInteractionConnection.this.mMainExcutor.execute(new PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda1(this, j, i, region, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2, magnificationSpec, 0));
        }

        public final void performAccessibilityAction(final long j, final int i, final Bundle bundle, final int i2, final IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, final int i3, final int i4, final long j2) {
            PipAccessibilityInteractionConnection.this.mMainExcutor.execute(new Runnable(j, i, bundle, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2) { // from class: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda4
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
                    if (j3 == AccessibilityNodeInfo.ROOT_NODE_ID) {
                        PipBoundsState pipBoundsState = pipAccessibilityInteractionConnection.mPipBoundsState;
                        if (i5 == R.id.action_pip_resize) {
                            int width = pipBoundsState.getBounds().width();
                            int width2 = pipAccessibilityInteractionConnection.mNormalBounds.width();
                            PipTaskOrganizer pipTaskOrganizer = pipAccessibilityInteractionConnection.mTaskOrganizer;
                            PipSnapAlgorithm pipSnapAlgorithm = pipAccessibilityInteractionConnection.mSnapAlgorithm;
                            if (width == width2 && pipBoundsState.getBounds().height() == pipAccessibilityInteractionConnection.mNormalBounds.height()) {
                                PipSnapAlgorithm.applySnapFraction(pipAccessibilityInteractionConnection.mExpandedBounds, pipAccessibilityInteractionConnection.mExpandedMovementBounds, pipSnapAlgorithm.getSnapFraction(0, pipBoundsState.getBounds(), pipAccessibilityInteractionConnection.mNormalMovementBounds));
                                final int i7 = 0;
                                pipTaskOrganizer.scheduleFinishResizePip(pipAccessibilityInteractionConnection.mExpandedBounds, 0, new Consumer() { // from class: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i8 = i7;
                                        PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection2 = pipAccessibilityInteractionConnection;
                                        switch (i8) {
                                            case 0:
                                                pipAccessibilityInteractionConnection2.mMotionHelper.synchronizePinnedStackBounds();
                                                pipAccessibilityInteractionConnection2.mUpdateMovementBoundCallback.run();
                                                break;
                                            default:
                                                pipAccessibilityInteractionConnection2.mMotionHelper.synchronizePinnedStackBounds();
                                                pipAccessibilityInteractionConnection2.mUpdateMovementBoundCallback.run();
                                                break;
                                        }
                                    }
                                });
                            } else {
                                PipSnapAlgorithm.applySnapFraction(pipAccessibilityInteractionConnection.mNormalBounds, pipAccessibilityInteractionConnection.mNormalMovementBounds, pipSnapAlgorithm.getSnapFraction(0, pipBoundsState.getBounds(), pipAccessibilityInteractionConnection.mExpandedMovementBounds));
                                final int i8 = 1;
                                pipTaskOrganizer.scheduleFinishResizePip(pipAccessibilityInteractionConnection.mNormalBounds, 0, new Consumer() { // from class: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i82 = i8;
                                        PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection2 = pipAccessibilityInteractionConnection;
                                        switch (i82) {
                                            case 0:
                                                pipAccessibilityInteractionConnection2.mMotionHelper.synchronizePinnedStackBounds();
                                                pipAccessibilityInteractionConnection2.mUpdateMovementBoundCallback.run();
                                                break;
                                            default:
                                                pipAccessibilityInteractionConnection2.mMotionHelper.synchronizePinnedStackBounds();
                                                pipAccessibilityInteractionConnection2.mUpdateMovementBoundCallback.run();
                                                break;
                                        }
                                    }
                                });
                            }
                        } else {
                            PipMotionHelper pipMotionHelper = pipAccessibilityInteractionConnection.mMotionHelper;
                            if (i5 == R.id.action_pip_stash) {
                                pipMotionHelper.getClass();
                                Rect rect = new Rect();
                                PipBoundsState pipBoundsState2 = pipMotionHelper.mPipBoundsState;
                                pipBoundsState2.mPipDisplayLayoutState.getDisplayLayout();
                                int i9 = pipBoundsState2.getBounds().left == pipBoundsState2.mMovementBounds.left ? 1 : 2;
                                float width3 = i9 == 1 ? (pipBoundsState2.mStashOffset - pipBoundsState2.getBounds().width()) + pipBoundsState2.getStashInsets().left : (pipBoundsState2.mPipDisplayLayoutState.getDisplayBounds().right - pipBoundsState2.mStashOffset) - pipBoundsState2.getStashInsets().right;
                                rect.set((int) width3, pipBoundsState2.getBounds().top, (int) (width3 + pipBoundsState2.getBounds().width()), pipBoundsState2.getBounds().bottom);
                                pipMotionHelper.resizeAndAnimatePipUnchecked$1(rect);
                                pipBoundsState2.setStashed(i9, false);
                            } else if (i5 == R.id.action_pip_unstash) {
                                pipAccessibilityInteractionConnection.mUnstashCallback.run();
                                pipBoundsState.setStashed(0, false);
                            } else if (i5 == 16) {
                                PipTouchHandler pipTouchHandler = ((PipTouchHandler$$ExternalSyntheticLambda4) pipAccessibilityInteractionConnection.mCallbacks).f$0;
                                pipTouchHandler.mMenuController.showMenuInternal(pipTouchHandler.mPipBoundsState.getBounds(), pipTouchHandler.willResizeMenu(), false, pipTouchHandler.mPipTaskOrganizer.shouldShowSplitMenu());
                            } else if (i5 == 262144) {
                                pipMotionHelper.expandLeavePip$1(false, false);
                            } else if (i5 == 1048576) {
                                pipMotionHelper.dismissPip();
                            } else if (i5 == 16908354) {
                                int i10 = bundle2.getInt("ACTION_ARGUMENT_MOVE_WINDOW_X");
                                int i11 = bundle2.getInt("ACTION_ARGUMENT_MOVE_WINDOW_Y");
                                new Rect().set(pipBoundsState.getBounds());
                                pipAccessibilityInteractionConnection.mTmpBounds.offsetTo(i10, i11);
                                pipMotionHelper.movePip(pipAccessibilityInteractionConnection.mTmpBounds, false);
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

        public final void getWindowSurfaceInfo(IWindowSurfaceInfoCallback iWindowSurfaceInfoCallback) {
        }

        public final void clearAccessibilityFocus() {
        }

        public final void notifyOutsideTouch() {
        }

        public final void attachAccessibilityOverlayToWindow(SurfaceControl surfaceControl, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
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
        Context context = this.mContext;
        AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain();
        obtain.setSourceNodeId(AccessibilityNodeInfo.ROOT_NODE_ID, -3);
        obtain.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
        obtain.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS);
        obtain.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW);
        obtain.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
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
