package com.android.wm.shell.common.split;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.Debug;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.IWindow;
import android.view.InsetsState;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.view.accessibility.AccessibilityManager;
import android.window.InputTransferToken;
import com.android.systemui.R;
import com.android.systemui.aibrief.control.BriefNowBarController;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.splitscreen.AppPairShortcutController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda2;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public final class SplitWindowManager extends WindowlessWindowManager {
    public AlertDialog mAddToAppPairDialogForRecent;
    public AppPairShortcutController mAppPairShortcutController;
    public Context mContext;
    public final DividerPanel mDividerPanel;
    public final SplitWindowManager$$ExternalSyntheticLambda0 mDividerPanelAutoOpen;
    public DividerResizeController mDividerResizeController;
    public DividerView mDividerView;
    public boolean mDividerVisible;
    public final boolean mIsCellDivider;
    public boolean mIsFirstAutoOpenDividerPanel;
    public boolean mIsPendingFirstAutoOpenDividerPanel;
    public boolean mLastDividerHandleHidden;
    public boolean mLastDividerInteractive;
    public SurfaceControl mLeash;
    public final ParentContainerCallbacks mParentContainerCallbacks;
    public SharedPreferences mPref;
    public boolean mShowingFirstAutoOpenDividerPanel;
    public SurfaceControl.Transaction mSyncTransaction;
    public SurfaceControlViewHost mViewHost;
    public final String mWindowName;

    public interface ParentContainerCallbacks {
    }

    public SplitWindowManager(String str, Context context, Configuration configuration, ParentContainerCallbacks parentContainerCallbacks) {
        this(str, context, configuration, parentContainerCallbacks, false);
    }

    public final SurfaceControl getParentSurface(IWindow iWindow, WindowManager.LayoutParams layoutParams) {
        SurfaceControl.Builder callsite = new SurfaceControl.Builder().setContainerLayer().setName("SplitWindowManager").setHidden(true).setCallsite("SplitWindowManager#attachToParentSurface");
        callsite.setParent(StageCoordinator.this.mRootTaskLeash);
        this.mLeash = callsite.build();
        StageCoordinator.AnonymousClass1 anonymousClass1 = (StageCoordinator.AnonymousClass1) this.mParentContainerCallbacks;
        StageCoordinator stageCoordinator = StageCoordinator.this;
        if (stageCoordinator.mDividerVisible) {
            stageCoordinator.mSyncQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda2(anonymousClass1, 3));
        }
        return this.mLeash;
    }

    public final SurfaceControl getSurfaceControl(IWindow iWindow) {
        return super.getSurfaceControl(iWindow);
    }

    public final void init(SplitLayout splitLayout, InsetsState insetsState, boolean z, DesktopState desktopState) {
        Rect rect;
        if (this.mDividerView != null || this.mViewHost != null) {
            throw new UnsupportedOperationException("Try to inflate divider view again without release first");
        }
        Context context = this.mContext;
        this.mViewHost = new SurfaceControlViewHost(context, context.getDisplay(), this, "SplitWindowManager");
        if (CoreRune.MW_MULTI_SPLIT_DIVIDER) {
            boolean zIsVerticalDivision = splitLayout.isVerticalDivision();
            if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) {
                if (CoreRune.MW_PARALLEL_MULTI_SPLIT && splitLayout.mParallelMultiSplit) {
                    if (zIsVerticalDivision) {
                        this.mDividerView = (DividerView) LayoutInflater.from(this.mContext).inflate(R.layout.parallel_multi_split_cell_divider, (ViewGroup) null);
                    } else {
                        this.mDividerView = (DividerView) LayoutInflater.from(this.mContext).inflate(R.layout.parallel_multi_split_cell_divider_horizontal, (ViewGroup) null);
                    }
                } else if (zIsVerticalDivision) {
                    this.mDividerView = (DividerView) LayoutInflater.from(this.mContext).inflate(R.layout.multi_split_cell_divider_horizontal, (ViewGroup) null);
                } else {
                    this.mDividerView = (DividerView) LayoutInflater.from(this.mContext).inflate(R.layout.multi_split_cell_divider, (ViewGroup) null);
                }
            } else if (zIsVerticalDivision) {
                this.mDividerView = (DividerView) LayoutInflater.from(this.mContext).inflate(R.layout.multi_split_divider, (ViewGroup) null);
            } else {
                this.mDividerView = (DividerView) LayoutInflater.from(this.mContext).inflate(R.layout.multi_split_divider_horizontal, (ViewGroup) null);
            }
        } else {
            this.mDividerView = (DividerView) LayoutInflater.from(this.mContext).inflate(R.layout.split_divider, (ViewGroup) null);
        }
        boolean z2 = CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER;
        if (z2 && this.mIsCellDivider) {
            splitLayout.getClass();
            rect = new Rect(splitLayout.mCellDividerBounds);
        } else {
            splitLayout.getClass();
            rect = new Rect(splitLayout.mDividerBounds);
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(rect.width(), rect.height(), 2034, 537133096, -3);
        layoutParams.token = new Binder();
        layoutParams.setTitle(this.mWindowName);
        layoutParams.privateFlags |= 536870976;
        layoutParams.accessibilityTitle = this.mContext.getResources().getString(R.string.accessibility_divider);
        if (z2 && this.mIsCellDivider) {
            layoutParams.type = 2614;
        }
        this.mViewHost.setView(this.mDividerView, layoutParams);
        DividerView dividerView = this.mDividerView;
        SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
        dividerView.mSplitLayout = splitLayout;
        dividerView.mSplitWindowManager = this;
        dividerView.mViewHost = surfaceControlViewHost;
        if (z2 && dividerView.mIsCellDivider) {
            Rect rect2 = dividerView.mDividerBounds;
            splitLayout.getClass();
            rect2.set(new Rect(splitLayout.mCellDividerBounds));
        } else {
            dividerView.mDividerBounds.set(splitLayout.mDividerBounds);
        }
        dividerView.onInsetsChanged(insetsState, false);
        SplitLayout splitLayout2 = dividerView.mSplitLayout;
        boolean z3 = splitLayout2.mIsLeftRightSplit;
        if (z2 && dividerView.mIsCellDivider && (!CoreRune.MW_PARALLEL_MULTI_SPLIT || !splitLayout2.mParallelMultiSplit)) {
            z3 = !z3;
        }
        dividerView.mHandle.setIsLeftRightSplit(z3);
        dividerView.mCorners.mIsLeftRightSplit = z3;
        Resources resources = dividerView.getResources();
        int i = R.dimen.split_divider_handle_region_width;
        dividerView.mHandleRegionWidth = resources.getDimensionPixelSize(z3 ? R.dimen.split_divider_handle_region_height : R.dimen.split_divider_handle_region_width);
        Resources resources2 = dividerView.getResources();
        if (!z3) {
            i = ((DesktopStateImpl) desktopState).canEnterDesktopMode ? R.dimen.desktop_mode_portrait_split_divider_handle_region_height : R.dimen.split_divider_handle_region_height;
        }
        dividerView.mHandleRegionHeight = resources2.getDimensionPixelSize(i);
        DividerResizeController dividerResizeController = this.mDividerResizeController;
        if (dividerResizeController != null) {
            dividerResizeController.mSplitLayout = splitLayout;
        }
        this.mAppPairShortcutController = new AppPairShortcutController(this.mContext, splitLayout);
        final DividerView dividerView2 = this.mDividerView;
        DividerPanel dividerPanel = this.mDividerPanel;
        DividerResizeController dividerResizeController2 = this.mDividerResizeController;
        dividerView2.mDividerPanel = dividerPanel;
        dividerView2.mGestureDetector = new GestureDetector(dividerView2.getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.android.wm.shell.common.split.DividerView.6
            public AnonymousClass6() {
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onSingleTapUp(MotionEvent motionEvent) {
                DividerView.this.openDividerPanelIfNeeded();
                return true;
            }
        });
        dividerView2.mDividerResizeController = dividerResizeController2;
        DividerPanel dividerPanel2 = this.mDividerPanel;
        DividerView dividerView3 = this.mDividerView;
        AppPairShortcutController appPairShortcutController = this.mAppPairShortcutController;
        dividerPanel2.mWindowManager.mDividerView = dividerView3;
        dividerPanel2.mDividerView = dividerView3;
        dividerPanel2.mSplitLayout = splitLayout;
        dividerPanel2.mCallbacks = this;
        dividerPanel2.mAppPairShortcutController = appPairShortcutController;
        dividerPanel2.mAccessibilityManager = (AccessibilityManager) dividerPanel2.mContext.getSystemService("accessibility");
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("DividerPref", 0);
        this.mPref = sharedPreferences;
        this.mIsFirstAutoOpenDividerPanel = sharedPreferences.getBoolean("divider_panel_first_auto_open", true);
        if (z) {
            this.mDividerView.setInteractive("restore_setup", this.mLastDividerInteractive, this.mLastDividerHandleHidden);
        }
    }

    public final void release(SurfaceControl.Transaction transaction) {
        DividerView dividerView = this.mDividerView;
        if (dividerView != null) {
            this.mLastDividerInteractive = dividerView.mInteractive;
            this.mLastDividerHandleHidden = dividerView.mHideHandle;
            this.mDividerView = null;
        }
        SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
        if (surfaceControlViewHost != null) {
            this.mSyncTransaction = transaction;
            surfaceControlViewHost.release();
            this.mSyncTransaction = null;
            this.mViewHost = null;
        }
        if (this.mLeash != null) {
            if (CoreRune.MW_SHELL_TRANSITION_LOG) {
                Slog.d("SplitWindowManager", "release:[MST] mLeash=" + this.mLeash + ", t=" + transaction + ", Callers=" + Debug.getCallers(7));
            }
            if (transaction == null) {
                new SurfaceControl.Transaction().remove(this.mLeash).apply();
            } else {
                transaction.remove(this.mLeash);
            }
            this.mLeash = null;
        }
    }

    public final void removeSurface(SurfaceControl surfaceControl) {
        SurfaceControl.Transaction transaction = this.mSyncTransaction;
        if (transaction != null) {
            transaction.remove(surfaceControl);
        } else {
            super.removeSurface(surfaceControl);
        }
    }

    public final void sendSplitStateChangedInfo(boolean z) {
        AppPairShortcutController appPairShortcutController = this.mAppPairShortcutController;
        if (appPairShortcutController != null) {
            if (!z) {
                SplitLayout splitLayout = appPairShortcutController.mSplitLayout;
                if (splitLayout == null || splitLayout.mWinToken1 == null || splitLayout.mWinToken2 == null) {
                    return;
                }
                if (appPairShortcutController.mStageCoordinator.isMultiSplitActive() && splitLayout.mWinToken3 == null) {
                    return;
                }
                appPairShortcutController.createAppPairShortcut(3);
                return;
            }
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.multiwindow.SEND_SPLIT_STATE_CHANGED");
            intent.addFlags(285212672);
            intent.setPackage(BriefNowBarController.SUGGESTION_PACKAGE);
            for (int i = 0; i < 3; i++) {
                intent.putExtra(AppPairShortcutController.sPairComponentNameList[i], "");
            }
            AppPairShortcutController.H h = appPairShortcutController.mH;
            h.sendMessage(h.obtainMessage(7, intent));
        }
    }

    public final void setConfiguration(Configuration configuration) {
        super.setConfiguration(configuration);
        Context contextCreateConfigurationContext = this.mContext.createConfigurationContext(configuration);
        this.mContext = contextCreateConfigurationContext;
        DividerView dividerView = this.mDividerView;
        if (dividerView != null) {
            int color = contextCreateConfigurationContext.getResources().getColor(17171593, null);
            dividerView.mBackground.setBackgroundColor(color);
            DividerRoundedCorner dividerRoundedCorner = dividerView.mCorners;
            if (dividerRoundedCorner != null) {
                dividerRoundedCorner.mDividerBarBackground.setColor(color);
                dividerView.mCorners.invalidate();
            }
        }
        DividerPanel dividerPanel = this.mDividerPanel;
        Context context = this.mContext;
        dividerPanel.getClass();
        dividerPanel.mContext = new ContextThemeWrapper(context, android.R.style.Theme.DeviceDefault.DayNight);
        AlertDialog alertDialog = dividerPanel.mAddToAppPairDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        dividerPanel.removeDividerPanel();
    }

    public final void setTouchRegion(Rect rect) {
        SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
        if (surfaceControlViewHost != null) {
            setTouchRegion(surfaceControlViewHost.getWindowToken().asBinder(), new Region(rect));
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.wm.shell.common.split.SplitWindowManager$$ExternalSyntheticLambda0] */
    public SplitWindowManager(String str, Context context, Configuration configuration, ParentContainerCallbacks parentContainerCallbacks, boolean z) {
        super(configuration, (SurfaceControl) null, (InputTransferToken) null);
        this.mIsFirstAutoOpenDividerPanel = false;
        this.mIsPendingFirstAutoOpenDividerPanel = false;
        this.mShowingFirstAutoOpenDividerPanel = false;
        this.mAddToAppPairDialogForRecent = null;
        this.mSyncTransaction = null;
        this.mLastDividerInteractive = true;
        this.mDividerPanelAutoOpen = new Runnable() { // from class: com.android.wm.shell.common.split.SplitWindowManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SplitWindowManager splitWindowManager = this.f$0;
                if (!splitWindowManager.mDividerVisible) {
                    Slog.d("SplitWindowManager", "Faild to run DividerPanel first auto open");
                    return;
                }
                splitWindowManager.mDividerPanel.updateDividerPanel();
                splitWindowManager.mIsFirstAutoOpenDividerPanel = false;
                SharedPreferences.Editor editorEdit = splitWindowManager.mPref.edit();
                editorEdit.putBoolean("divider_panel_first_auto_open", false);
                editorEdit.apply();
                splitWindowManager.mShowingFirstAutoOpenDividerPanel = true;
                Slog.d("SplitWindowManager", "Run DividerPanel first auto open");
            }
        };
        Context contextCreateConfigurationContext = context.createConfigurationContext(configuration);
        this.mContext = contextCreateConfigurationContext;
        this.mParentContainerCallbacks = parentContainerCallbacks;
        this.mWindowName = str;
        this.mDividerPanel = new DividerPanel(contextCreateConfigurationContext);
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER) {
            this.mIsCellDivider = z;
        }
    }
}
