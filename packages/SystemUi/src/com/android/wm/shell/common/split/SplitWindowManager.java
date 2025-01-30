package com.android.wm.shell.common.split;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.IWindow;
import android.view.InsetsState;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceSession;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import com.android.wm.shell.common.split.DividerPanel;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.splitscreen.AppPairShortcutController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda0;
import com.android.systemui.R;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplitWindowManager extends WindowlessWindowManager implements DividerPanel.DividerPanelCallbacks {
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
    public SurfaceControl mLeash;
    public final ParentContainerCallbacks mParentContainerCallbacks;
    public SharedPreferences mPref;
    public boolean mShowingFirstAutoOpenDividerPanel;
    public SurfaceControl.Transaction mSyncTransaction;
    public SurfaceControlViewHost mViewHost;
    public final String mWindowName;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ParentContainerCallbacks {
    }

    public SplitWindowManager(String str, Context context, Configuration configuration, ParentContainerCallbacks parentContainerCallbacks) {
        this(str, context, configuration, parentContainerCallbacks, false);
    }

    public final SurfaceControl getParentSurface(IWindow iWindow, WindowManager.LayoutParams layoutParams) {
        SurfaceControl.Builder callsite = new SurfaceControl.Builder(new SurfaceSession()).setContainerLayer().setName("SplitWindowManager".concat(this.mIsCellDivider ? "Cell" : "")).setHidden(true).setCallsite("SplitWindowManager#attachToParentSurface");
        callsite.setParent(StageCoordinator.this.mRootTaskLeash);
        this.mLeash = callsite.build();
        StageCoordinator.C41151 c41151 = (StageCoordinator.C41151) this.mParentContainerCallbacks;
        StageCoordinator stageCoordinator = StageCoordinator.this;
        if (stageCoordinator.mDividerVisible) {
            stageCoordinator.mSyncQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda0(c41151, 7));
        }
        return this.mLeash;
    }

    public final SurfaceControl getSurfaceControl(IWindow iWindow) {
        return super.getSurfaceControl(iWindow);
    }

    public final void init(SplitLayout splitLayout, InsetsState insetsState) {
        Rect rect;
        if (this.mDividerView != null || this.mViewHost != null) {
            throw new UnsupportedOperationException("Try to inflate divider view again without release first");
        }
        Context context = this.mContext;
        this.mViewHost = new SurfaceControlViewHost(context, context.getDisplay(), this, "SplitWindowManager");
        if (CoreRune.MW_MULTI_SPLIT_DIVIDER) {
            boolean isVerticalDivision = splitLayout.isVerticalDivision();
            if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) {
                if (isVerticalDivision) {
                    this.mDividerView = (DividerView) LayoutInflater.from(this.mContext).inflate(R.layout.multi_split_cell_divider_horizontal, (ViewGroup) null);
                } else {
                    this.mDividerView = (DividerView) LayoutInflater.from(this.mContext).inflate(R.layout.multi_split_cell_divider, (ViewGroup) null);
                }
            } else if (isVerticalDivision) {
                this.mDividerView = (DividerView) LayoutInflater.from(this.mContext).inflate(R.layout.multi_split_divider, (ViewGroup) null);
            } else {
                this.mDividerView = (DividerView) LayoutInflater.from(this.mContext).inflate(R.layout.multi_split_divider_horizontal, (ViewGroup) null);
            }
        } else {
            this.mDividerView = (DividerView) LayoutInflater.from(this.mContext).inflate(R.layout.split_divider, (ViewGroup) null);
        }
        if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS && splitLayout.mStageCoordinator.mIsVideoControls) {
            this.mDividerView.mSetTouchRegion = false;
        }
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) {
            splitLayout.getClass();
            rect = new Rect(splitLayout.mCellDividerBounds);
        } else {
            splitLayout.getClass();
            rect = new Rect(splitLayout.mDividerBounds);
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(rect.width(), rect.height(), 2034, 545521704, -3);
        layoutParams.token = new Binder();
        layoutParams.setTitle(this.mWindowName);
        layoutParams.privateFlags |= 536870976;
        layoutParams.accessibilityTitle = this.mContext.getResources().getString(R.string.accessibility_divider);
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) {
            layoutParams.type = 2614;
        }
        this.mViewHost.setView(this.mDividerView, layoutParams);
        DividerView dividerView = this.mDividerView;
        SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
        dividerView.mSplitLayout = splitLayout;
        dividerView.mSplitWindowManager = this;
        dividerView.mViewHost = surfaceControlViewHost;
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && dividerView.mIsCellDivider) {
            Rect rect2 = dividerView.mDividerBounds;
            splitLayout.getClass();
            rect2.set(new Rect(splitLayout.mCellDividerBounds));
        } else {
            dividerView.mDividerBounds.set(splitLayout.mDividerBounds);
        }
        dividerView.onInsetsChanged(insetsState, false);
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
            public C38926() {
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
        dividerPanel2.mSplitLayout = splitLayout;
        dividerPanel2.mCallbacks = this;
        dividerPanel2.mAppPairShortcutController = appPairShortcutController;
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("DividerPref", 0);
        this.mPref = sharedPreferences;
        this.mIsFirstAutoOpenDividerPanel = sharedPreferences.getBoolean("divider_panel_first_auto_open", true);
    }

    public final void release(SurfaceControl.Transaction transaction) {
        if (this.mDividerView != null) {
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
                appPairShortcutController.createAppPairShortcut(3);
                return;
            }
            appPairShortcutController.getClass();
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.multiwindow.SEND_SPLIT_STATE_CHANGED");
            intent.addFlags(285212672);
            intent.setPackage("com.samsung.android.smartsuggestions");
            for (int i = 0; i < 3; i++) {
                intent.putExtra(AppPairShortcutController.sPairComponentNameList[i], "");
            }
            AppPairShortcutController.HandlerC4091H handlerC4091H = appPairShortcutController.f456mH;
            handlerC4091H.sendMessage(handlerC4091H.obtainMessage(7, intent));
        }
    }

    public final void setConfiguration(Configuration configuration) {
        super.setConfiguration(configuration);
        Context createConfigurationContext = this.mContext.createConfigurationContext(configuration);
        this.mContext = createConfigurationContext;
        DividerPanel dividerPanel = this.mDividerPanel;
        dividerPanel.getClass();
        dividerPanel.mContext = new ContextThemeWrapper(createConfigurationContext, android.R.style.Theme.DeviceDefault.DayNight);
        AlertDialog alertDialog = dividerPanel.mAddToAppPairDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        dividerPanel.removeDividerPanel();
    }

    public final void setInteractive(String str, boolean z, boolean z2) {
        final DividerView dividerView = this.mDividerView;
        if (dividerView == null || z == dividerView.mInteractive) {
            return;
        }
        int i = 0;
        if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 970879579, 0, "Set divider bar %s from %s", z ? "interactive" : "non-interactive", str);
        }
        dividerView.mInteractive = z;
        if (!z && dividerView.mMoving) {
            SplitLayout splitLayout = dividerView.mSplitLayout;
            final int i2 = splitLayout.mDividePosition;
            splitLayout.flingDividePosition(0, i2, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, new Runnable() { // from class: com.android.wm.shell.common.split.DividerView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DividerView dividerView2 = DividerView.this;
                    dividerView2.mSplitLayout.setDividePosition(i2, null, true);
                }
            });
            dividerView.mMoving = false;
        }
        dividerView.releaseTouching();
        DividerHandleView dividerHandleView = dividerView.mHandle;
        if (!dividerView.mInteractive && z2) {
            i = 4;
        }
        dividerHandleView.setVisibility(i);
    }

    public final void setTouchRegion(Rect rect) {
        SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
        if (surfaceControlViewHost != null) {
            setTouchRegion(surfaceControlViewHost.getWindowToken().asBinder(), new Region(rect));
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.common.split.SplitWindowManager$$ExternalSyntheticLambda0] */
    public SplitWindowManager(String str, Context context, Configuration configuration, ParentContainerCallbacks parentContainerCallbacks, boolean z) {
        super(configuration, (SurfaceControl) null, (IBinder) null);
        this.mIsFirstAutoOpenDividerPanel = false;
        this.mIsPendingFirstAutoOpenDividerPanel = false;
        this.mShowingFirstAutoOpenDividerPanel = false;
        this.mSyncTransaction = null;
        this.mDividerPanelAutoOpen = new Runnable() { // from class: com.android.wm.shell.common.split.SplitWindowManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SplitWindowManager splitWindowManager = SplitWindowManager.this;
                if (!splitWindowManager.mDividerVisible) {
                    Slog.d("SplitWindowManager", "Faild to run DividerPanel first auto open");
                    return;
                }
                splitWindowManager.mDividerPanel.updateDividerPanel();
                splitWindowManager.mIsFirstAutoOpenDividerPanel = false;
                SharedPreferences.Editor edit = splitWindowManager.mPref.edit();
                edit.putBoolean("divider_panel_first_auto_open", false);
                edit.apply();
                splitWindowManager.mShowingFirstAutoOpenDividerPanel = true;
                Slog.d("SplitWindowManager", "Run DividerPanel first auto open");
            }
        };
        Context createConfigurationContext = context.createConfigurationContext(configuration);
        this.mContext = createConfigurationContext;
        this.mParentContainerCallbacks = parentContainerCallbacks;
        this.mWindowName = str;
        this.mDividerPanel = new DividerPanel(createConfigurationContext);
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER) {
            this.mIsCellDivider = z;
        }
    }
}
