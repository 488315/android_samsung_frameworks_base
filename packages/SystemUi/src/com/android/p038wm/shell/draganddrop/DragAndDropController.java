package com.android.p038wm.shell.draganddrop;

import android.app.ActivityTaskManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentCallbacks2;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.HardwareRenderer;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import com.android.launcher3.icons.IconProvider;
import com.android.p038wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda18;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.common.DnDSnackBarController;
import com.android.p038wm.shell.common.DnDSnackBarWindow;
import com.android.p038wm.shell.common.ExternalInterfaceBinder;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.MultiWindowOverheatUI;
import com.android.p038wm.shell.common.RemoteCallable;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.draganddrop.AppResultFactory;
import com.android.p038wm.shell.draganddrop.DragAndDropController;
import com.android.p038wm.shell.draganddrop.DragAndDropEventLogger;
import com.android.p038wm.shell.draganddrop.DragAndDropPolicy;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.p038wm.shell.sysui.ShellCommandHandler;
import com.android.p038wm.shell.sysui.ShellController;
import com.android.p038wm.shell.sysui.ShellInit;
import com.android.systemui.R;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.multiwindow.IDragAndDropControllerProxy;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BooleanSupplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DragAndDropController implements RemoteCallable, DisplayController.OnDisplaysChangedListener, View.OnDragListener, ComponentCallbacks2 {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final DragAndDropEventLogger mLogger;
    public final ShellExecutor mMainExecutor;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public SplitScreenController mSplitScreen;
    public final ArrayList mListeners = new ArrayList();
    public final SparseArray mDisplayDropTargets = new SparseArray();
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
    public final C39611 mDismissReceiver = new BroadcastReceiver() { // from class: com.android.wm.shell.draganddrop.DragAndDropController.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -517253288, 0, "Receive %s", String.valueOf(intent.getAction()));
            }
            DragAndDropController dragAndDropController = DragAndDropController.this;
            int size = dragAndDropController.mDisplayDropTargets.size();
            while (true) {
                size--;
                if (size < 0) {
                    return;
                }
                PerDisplay perDisplay = (PerDisplay) dragAndDropController.mDisplayDropTargets.valueAt(size);
                Handler handler = perDisplay.rootView.getHandler();
                if (handler == null) {
                    Slog.w("DragAndDropController", "Couldn't make dropTarget invisible since handler isn't existed.");
                } else {
                    handler.post(new DragAndDropController$$ExternalSyntheticLambda1(dragAndDropController, perDisplay, 2));
                }
            }
        }
    };
    public final Rect mTmpRect = new Rect();
    public final C39622 mProxy = new C39622();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.draganddrop.DragAndDropController$2 */
    public final class C39622 extends IDragAndDropControllerProxy.Stub {
        public C39622() {
        }

        public final void show(final int i) {
            PerDisplay perDisplay = (PerDisplay) DragAndDropController.this.mDisplayDropTargets.get(i);
            if (perDisplay == null) {
                int i2 = DragAndDropController.$r8$clinit;
                Slog.w("DragAndDropController", "Couldn't show dropTarget since wrong displayId #" + i);
                return;
            }
            Handler handler = perDisplay.rootView.getHandler();
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.android.wm.shell.draganddrop.DragAndDropController$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DragAndDropController.C39622 c39622 = DragAndDropController.C39622.this;
                        int i3 = i;
                        DragAndDropController.PerDisplay perDisplay2 = (DragAndDropController.PerDisplay) DragAndDropController.this.mDisplayDropTargets.get(i3);
                        if (perDisplay2 == null) {
                            int i4 = DragAndDropController.$r8$clinit;
                            Slog.w("DragAndDropController", "Couldn't show dropTarget since display #" + i3 + " was removed");
                            return;
                        }
                        if (!perDisplay2.isHandlingDrag) {
                            int i5 = DragAndDropController.$r8$clinit;
                            Slog.w("DragAndDropController", "DropTarget not handling for display Id #" + i3);
                            return;
                        }
                        perDisplay2.hideRequested = false;
                        DragAndDropController.this.getClass();
                        DragAndDropController.setDropTargetWindowVisibility(perDisplay2, 0);
                        DropTargetLayout dropTargetLayout = (DropTargetLayout) perDisplay2.dragLayout;
                        dropTargetLayout.getClass();
                        dropTargetLayout.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                    }
                });
            } else {
                int i3 = DragAndDropController.$r8$clinit;
                Slog.w("DragAndDropController", "Couldn't show dropTarget since handler isn't existed.");
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IDragAndDropImpl extends IDragAndDrop$Stub implements ExternalInterfaceBinder {
        public DragAndDropController mController;

        public IDragAndDropImpl(DragAndDropController dragAndDropController) {
            this.mController = dragAndDropController;
        }

        @Override // com.android.p038wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PerDisplay implements HardwareRenderer.FrameDrawingCallback {
        public int activeDragCount;
        public final Context context;
        public final int displayId;
        public final DnDSnackBarController dndSnackBarController;
        public DragAndDropClientRecord dragAndDropClientRecord;
        public final IDragLayout dragLayout;
        public IDropTargetUiController dropTargetUiController;
        public final ExecutableAppHolder executableAppHolder;
        public boolean hideRequested;
        public boolean isHandlingDrag;
        public boolean mHasDrawn;
        public final Rect mHiddenDropTargetArea;
        public final FrameLayout rootView;
        public final SmartTipController smartTipController;
        public final VisibleTasks visibleTasks;
        public int windowVisibility;

        /* renamed from: wm */
        public final WindowManager f444wm;

        public PerDisplay(int i, Context context, WindowManager windowManager, FrameLayout frameLayout, IDragLayout iDragLayout) {
            this(i, context, windowManager, frameLayout, iDragLayout, new SmartTipController(context), new DnDSnackBarController(context), new ExecutableAppHolder(context));
        }

        public final void onFrameDraw(long j) {
            this.mHasDrawn = true;
        }

        public PerDisplay(int i, Context context, WindowManager windowManager, FrameLayout frameLayout, IDragLayout iDragLayout, SmartTipController smartTipController, DnDSnackBarController dnDSnackBarController, ExecutableAppHolder executableAppHolder) {
            this.mHiddenDropTargetArea = new Rect();
            this.displayId = i;
            this.context = context;
            this.f444wm = windowManager;
            this.rootView = frameLayout;
            this.dragLayout = iDragLayout;
            this.smartTipController = smartTipController;
            this.dndSnackBarController = dnDSnackBarController;
            this.executableAppHolder = executableAppHolder;
            if (executableAppHolder != null) {
                synchronized (executableAppHolder.mCallbacks) {
                    if (!((ArrayList) executableAppHolder.mCallbacks).contains(iDragLayout)) {
                        ((ArrayList) executableAppHolder.mCallbacks).add(iDragLayout);
                    }
                }
                this.visibleTasks = new VisibleTasks(i);
            } else {
                this.visibleTasks = null;
            }
            this.windowVisibility = frameLayout.getVisibility();
        }
    }

    /* JADX WARN: Type inference failed for: r7v4, types: [com.android.wm.shell.draganddrop.DragAndDropController$1] */
    public DragAndDropController(Context context, ShellInit shellInit, ShellController shellController, ShellCommandHandler shellCommandHandler, DisplayController displayController, UiEventLogger uiEventLogger, IconProvider iconProvider, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mShellController = shellController;
        this.mShellCommandHandler = shellCommandHandler;
        this.mDisplayController = displayController;
        this.mLogger = new DragAndDropEventLogger(uiEventLogger);
        this.mMainExecutor = shellExecutor;
        shellInit.addInitCallback(new DragAndDropController$$ExternalSyntheticLambda0(this, 0), this);
    }

    public static void clearState(PerDisplay perDisplay) {
        Slog.w("DragAndDropController", "clearState d=" + perDisplay.displayId);
        perDisplay.dropTargetUiController = null;
        perDisplay.isHandlingDrag = false;
        perDisplay.activeDragCount = 0;
        ExecutableAppHolder executableAppHolder = perDisplay.executableAppHolder;
        if (executableAppHolder != null) {
            executableAppHolder.mExecutableApp = null;
            ((HashMap) executableAppHolder.mExecutableAppMap).clear();
            executableAppHolder.mResult = null;
            executableAppHolder.mIsMimeType = false;
        }
        DragAndDropClientRecord dragAndDropClientRecord = perDisplay.dragAndDropClientRecord;
        if (dragAndDropClientRecord != null) {
            try {
                dragAndDropClientRecord.mClient.onDisconnected();
            } catch (RemoteException unused) {
                Slog.d("DragAndDropClient", "Failed to disconnect.");
            }
            perDisplay.dragAndDropClientRecord = null;
            perDisplay.hideRequested = false;
        }
    }

    public static void setDropTargetWindowVisibility(PerDisplay perDisplay, int i) {
        if (perDisplay.hideRequested && i == 0) {
            if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -1432766569, 5, "Do not update drop target window visibility: displayId=%d visibility=%d", Long.valueOf(perDisplay.displayId), Long.valueOf(i));
                return;
            }
            return;
        }
        if (perDisplay.windowVisibility == i) {
            if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -47008999, 0, "Do not update drop target window visibility: window is already set to %s.", String.valueOf(i));
                return;
            }
            return;
        }
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 1184615936, 5, "Set drop target window visibility: displayId=%d visibility=%d", Long.valueOf(perDisplay.displayId), Long.valueOf(i));
        }
        perDisplay.rootView.setVisibility(i);
        if (i == 0) {
            perDisplay.rootView.requestApplyInsets();
            if (!perDisplay.mHasDrawn && perDisplay.rootView.getViewRootImpl() != null) {
                perDisplay.rootView.getViewRootImpl().registerRtFrameCallback(perDisplay);
            }
        } else {
            perDisplay.mHasDrawn = false;
        }
        perDisplay.windowVisibility = i;
    }

    public void addDisplayDropTarget(int i, Context context, WindowManager windowManager, FrameLayout frameLayout, IDragLayout iDragLayout) {
        this.mDisplayDropTargets.put(i, new PerDisplay(i, context, windowManager, frameLayout, iDragLayout));
    }

    public boolean deviceSupportsSplitScreenMultiWindow() {
        return ActivityTaskManager.deviceSupportsMultiWindow(this.mContext);
    }

    @Override // com.android.p038wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.p038wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0080, code lost:
    
        r7 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0124, code lost:
    
        if ((r0.type == 0) != false) goto L50;
     */
    /* JADX WARN: Removed duplicated region for block: B:45:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean handleDrop(DragEvent dragEvent, PerDisplay perDisplay) {
        boolean z;
        int i;
        DragAndDropPolicy.LaunchOptions launchOptions;
        DragAndDropPolicy.LaunchOptions launchOptions2;
        dragEvent.getDragSurface();
        perDisplay.activeDragCount--;
        DragAndDropPolicy.Target target = ((DropTargetLayout) perDisplay.dragLayout).mCurrentTarget;
        if (target != null && !target.isResizable) {
            this.mDisplayController.getDisplayLayout(perDisplay.displayId).getStableBounds(this.mTmpRect, false);
            DnDSnackBarController dnDSnackBarController = perDisplay.dndSnackBarController;
            Rect rect = this.mTmpRect;
            DnDSnackBarWindow dnDSnackBarWindow = dnDSnackBarController.mView;
            if (dnDSnackBarWindow != null && dnDSnackBarWindow.isAttachedToWindow()) {
                dnDSnackBarController.mView.hide();
            }
            if (!dnDSnackBarController.mWasShownSnackBar) {
                Context context = dnDSnackBarController.mContext;
                if (ActivityTaskManager.supportsSplitScreenMultiWindow(context)) {
                    boolean z2 = CoreRune.FW_SUPPORT_DOWNLOADABLE_RESERVE_BATTERY_MODE ? false : false;
                    if (!z2) {
                        final DnDSnackBarWindow dnDSnackBarWindow2 = (DnDSnackBarWindow) LayoutInflater.from(context).inflate(R.layout.dnd_snack_bar, (ViewGroup) null);
                        dnDSnackBarController.mView = dnDSnackBarWindow2;
                        dnDSnackBarWindow2.getClass();
                        dnDSnackBarWindow2.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.common.DnDSnackBarWindow$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnTouchListener
                            public final boolean onTouch(View view, MotionEvent motionEvent) {
                                DnDSnackBarWindow dnDSnackBarWindow3 = DnDSnackBarWindow.this;
                                int i2 = DnDSnackBarWindow.$r8$clinit;
                                dnDSnackBarWindow3.getClass();
                                int action = motionEvent.getAction();
                                if (action != 0 && action != 4) {
                                    return true;
                                }
                                dnDSnackBarWindow3.hide();
                                return true;
                            }
                        });
                        dnDSnackBarWindow2.findViewById(R.id.snack_bar_button).setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.common.DnDSnackBarWindow$$ExternalSyntheticLambda1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                DnDSnackBarWindow dnDSnackBarWindow3 = DnDSnackBarWindow.this;
                                DnDSnackBarController dnDSnackBarController2 = (DnDSnackBarController) dnDSnackBarWindow3.mCallbacks;
                                dnDSnackBarController2.getClass();
                                Bundle bundle = new Bundle();
                                bundle.putString(":settings:fragment_args_key", "multi_window_for_all_apps");
                                Intent intent = new Intent();
                                intent.setAction("com.samsung.settings.LABS_SETTINGS");
                                intent.putExtra(":settings:show_fragment_args", bundle);
                                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                dnDSnackBarController2.mContext.startActivity(intent);
                                dnDSnackBarWindow3.hide();
                            }
                        });
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2008, android.R.string.accessibility_system_action_back_label, -3);
                        dnDSnackBarWindow2.mLp = layoutParams;
                        layoutParams.privateFlags |= 80;
                        layoutParams.setFitInsetsTypes(0);
                        dnDSnackBarWindow2.mLp.setTitle("DnDSnackBar");
                        WindowManager.LayoutParams layoutParams2 = dnDSnackBarWindow2.mLp;
                        layoutParams2.windowAnimations = android.R.style.Animation.Toast;
                        dnDSnackBarWindow2.mWindowManager.addView(dnDSnackBarWindow2, layoutParams2);
                        dnDSnackBarWindow2.mLp.gravity = 49;
                        dnDSnackBarWindow2.measure(0, 0);
                        dnDSnackBarWindow2.mLp.y = (rect.bottom - dnDSnackBarWindow2.getMeasuredHeight()) - dnDSnackBarWindow2.mMarginBottom;
                        dnDSnackBarWindow2.mWindowManager.updateViewLayout(dnDSnackBarWindow2, dnDSnackBarWindow2.mLp);
                        dnDSnackBarController.mView.mCallbacks = dnDSnackBarController;
                    }
                }
            }
        }
        IDragLayout iDragLayout = perDisplay.dragLayout;
        DragAndDropController$$ExternalSyntheticLambda1 dragAndDropController$$ExternalSyntheticLambda1 = new DragAndDropController$$ExternalSyntheticLambda1(this, perDisplay, 1);
        DropTargetLayout dropTargetLayout = (DropTargetLayout) iDragLayout;
        DragAndDropPolicy.Target target2 = dropTargetLayout.mCurrentTarget;
        boolean z3 = target2 != null || dropTargetLayout.mDismissButtonView.mIsEnterDismissButton;
        dropTargetLayout.mHasDropped = true;
        if (dropTargetLayout.mHasDragSourceTask) {
            if (target2 != null) {
            }
            z = false;
            z3 = false;
            if (z) {
                int displayId = dropTargetLayout.getDisplay() == null ? 0 : dropTargetLayout.getDisplay().getDisplayId();
                DragAndDropPolicy dragAndDropPolicy = dropTargetLayout.mPolicy;
                DragAndDropPolicy.Target target3 = dropTargetLayout.mCurrentTarget;
                ClipData clipData = dragEvent.getClipData();
                DragAndDropPermissions obtain = DragAndDropPermissions.obtain(dragEvent);
                if (target3 == null) {
                    dragAndDropPolicy.getClass();
                } else if (dragAndDropPolicy.mTargets.contains(target3)) {
                    boolean z4 = CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET;
                    int i2 = target3.type;
                    SplitScreenController splitScreenController = dragAndDropPolicy.mSplitScreen;
                    if (z4 && dragAndDropPolicy.supportMultiSplitDropTarget()) {
                        if ((i2 == 1 || i2 == 3 || i2 == 2 || i2 == 4) || target3.isMultiSplit()) {
                            splitScreenController.onDroppedToSplit(-1, dragAndDropPolicy.mLoggerSessionId);
                        }
                        if (target3.isMultiSplit()) {
                            launchOptions = new DragAndDropPolicy.LaunchOptions(-1, target3.convertTypeToCellStagePosition(), -1);
                        } else {
                            if (i2 == 1 || i2 == 3 || i2 == 2 || i2 == 4) {
                                launchOptions2 = new DragAndDropPolicy.LaunchOptions((i2 == 2 || i2 == 1) ? 0 : 1, 0, (i2 == 2 || i2 == 4) ? 1 : 0);
                            } else {
                                launchOptions2 = new DragAndDropPolicy.LaunchOptions(-1, 0, -1);
                            }
                            if ((i2 == 5) || (r3 = dragAndDropPolicy.mFreeformStarter) == null) {
                                DragAndDropPolicy.Starter starter = dragAndDropPolicy.mStarter;
                            }
                            dragAndDropPolicy.startClipDescription(clipData.getDescription(), dragAndDropPolicy.mSession.dragData, launchOptions2.splitPosition, starter, obtain, launchOptions2, displayId);
                            if (target3.alreadyRun) {
                                Context context2 = dragAndDropPolicy.mContext;
                                Toast.makeText(context2, context2.getResources().getString(R.string.multiwindow_app_already_in_this_window), 0).show();
                            }
                        }
                    } else {
                        int i3 = (i2 == 2 || i2 == 1) ? 1 : 0;
                        if (i2 == 0 || splitScreenController == null) {
                            i = -1;
                        } else {
                            i = i3 ^ 1;
                            splitScreenController.onDroppedToSplit(i, dragAndDropPolicy.mLoggerSessionId);
                        }
                        launchOptions = new DragAndDropPolicy.LaunchOptions(i, 0, -1);
                    }
                    launchOptions2 = launchOptions;
                    if (i2 == 5) {
                    }
                    DragAndDropPolicy.Starter starter2 = dragAndDropPolicy.mStarter;
                    dragAndDropPolicy.startClipDescription(clipData.getDescription(), dragAndDropPolicy.mSession.dragData, launchOptions2.splitPosition, starter2, obtain, launchOptions2, displayId);
                    if (target3.alreadyRun) {
                    }
                }
            }
            dropTargetLayout.hide(dragEvent, dragAndDropController$$ExternalSyntheticLambda1);
            return z3;
        }
        z = true;
        if (z) {
        }
        dropTargetLayout.hide(dragEvent, dragAndDropController$$ExternalSyntheticLambda1);
        return z3;
    }

    public boolean isUserSetup() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        return (Settings.Global.getInt(contentResolver, "device_provisioned", 0) != 0) && (Settings.Secure.getIntForUser(contentResolver, "user_setup_complete", 0, -2) != 0);
    }

    public final void notifyDragStarted() {
        for (int i = 0; i < this.mListeners.size(); i++) {
            ((BubbleController$$ExternalSyntheticLambda18) this.mListeners.get(i)).f$0.collapseStack();
        }
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        ((HandlerExecutor) this.mMainExecutor).execute(new DragAndDropController$$ExternalSyntheticLambda1(this, configuration, 3));
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayAdded(int i) {
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -1006733970, 1, "Display added: %d", Long.valueOf(i));
        }
        if (i != 0) {
            return;
        }
        Context createWindowContext = this.mDisplayController.getDisplayContext(i).createWindowContext(2016, null);
        WindowManager windowManager = (WindowManager) createWindowContext.getSystemService(WindowManager.class);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2016, 16777224, -3);
        layoutParams.privateFlags |= -2147483568;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("ShellDropTarget");
        layoutParams.flags |= 512;
        layoutParams.multiwindowFlags |= 16;
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(createWindowContext).inflate(R.layout.global_drop_target, (ViewGroup) null);
        frameLayout.setOnDragListener(this);
        frameLayout.setVisibility(4);
        DropTargetLayout dropTargetLayout = new DropTargetLayout(createWindowContext, this.mSplitScreen, this.mTransaction);
        frameLayout.addView(dropTargetLayout, new FrameLayout.LayoutParams(-1, -1));
        try {
            windowManager.addView(frameLayout, layoutParams);
            addDisplayDropTarget(i, createWindowContext, windowManager, frameLayout, dropTargetLayout);
            createWindowContext.registerComponentCallbacks(this);
        } catch (WindowManager.InvalidDisplayException unused) {
            Slog.w("DragAndDropController", "Unable to add view for display id: " + i);
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 2057038970, 1, "Display changed: %d", Long.valueOf(i));
        }
        PerDisplay perDisplay = (PerDisplay) this.mDisplayDropTargets.get(i);
        if (perDisplay == null) {
            return;
        }
        perDisplay.rootView.requestApplyInsets();
        DnDSnackBarController dnDSnackBarController = perDisplay.dndSnackBarController;
        if (dnDSnackBarController != null) {
            DnDSnackBarWindow dnDSnackBarWindow = dnDSnackBarController.mView;
            if (dnDSnackBarWindow != null && dnDSnackBarWindow.isAttachedToWindow()) {
                dnDSnackBarController.mView.hide();
            }
            dnDSnackBarController.mView = null;
        }
        DropTargetLayout dropTargetLayout = (DropTargetLayout) perDisplay.dragLayout;
        if (dropTargetLayout.mIsShowing) {
            DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(i);
            DragAndDropPolicy.DragSession dragSession = dropTargetLayout.mPolicy.mSession;
            if (dragSession != null) {
                dragSession.displayLayout = displayLayout;
            }
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayRemoved(int i) {
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -1382704050, 1, "Display removed: %d", Long.valueOf(i));
        }
        PerDisplay perDisplay = (PerDisplay) this.mDisplayDropTargets.get(i);
        if (perDisplay == null) {
            return;
        }
        perDisplay.context.unregisterComponentCallbacks(this);
        perDisplay.f444wm.removeViewImmediate(perDisplay.rootView);
        this.mDisplayDropTargets.remove(i);
        DnDSnackBarController dnDSnackBarController = perDisplay.dndSnackBarController;
        if (dnDSnackBarController != null) {
            DnDSnackBarWindow dnDSnackBarWindow = dnDSnackBarController.mView;
            if (dnDSnackBarWindow != null && dnDSnackBarWindow.isAttachedToWindow()) {
                dnDSnackBarController.mView.hide();
            }
            dnDSnackBarController.mView = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0228  */
    @Override // android.view.View.OnDragListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onDrag(View view, DragEvent dragEvent) {
        boolean z;
        AppResult nonResizeableAppsResult;
        boolean z2;
        Rect rect;
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 1862198614, 680, "Drag event: action=%s x=%f y=%f xOffset=%f yOffset=%f", String.valueOf(DragEvent.actionToString(dragEvent.getAction())), Double.valueOf(dragEvent.getX()), Double.valueOf(dragEvent.getY()), Double.valueOf(dragEvent.getOffsetX()), Double.valueOf(dragEvent.getOffsetY()));
        }
        int displayId = view.getDisplay().getDisplayId();
        PerDisplay perDisplay = (PerDisplay) this.mDisplayDropTargets.get(displayId);
        ClipDescription clipDescription = dragEvent.getClipDescription();
        if (perDisplay == null) {
            return false;
        }
        boolean z3 = true;
        if (!(!perDisplay.context.getResources().getConfiguration().isDexMode())) {
            return false;
        }
        BooleanSupplier booleanSupplier = this.mSplitScreen.mIsKeyguardOccludedAndShowingSupplier;
        if (booleanSupplier != null ? booleanSupplier.getAsBoolean() : false) {
            Slog.w("DragAndDropController", "isKeyguardOccludedAndShowing=true");
            return false;
        }
        if (dragEvent.getAction() == 1) {
            Slog.w("DragAndDropController", "ACTION_DRAG_STARTED");
            if (!isUserSetup()) {
                Slog.w("DragAndDropController", "User setup is not yet completed.");
                return false;
            }
            if (!deviceSupportsSplitScreenMultiWindow()) {
                Slog.w("DragAndDropController", "This device does not support multi-windows.");
                return false;
            }
            this.mSplitScreen.dismissAddToAppPairDialog();
            if (dragEvent.getClipData().getItemCount() <= 0 || (!clipDescription.hasMimeType("application/vnd.android.activity") && !clipDescription.hasMimeType("application/vnd.android.shortcut") && !clipDescription.hasMimeType("application/vnd.android.task"))) {
                z3 = false;
            }
            perDisplay.isHandlingDrag = z3;
            if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
                long itemCount = dragEvent.getClipData().getItemCount();
                String str = "";
                for (int i = 0; i < clipDescription.getMimeTypeCount(); i++) {
                    if (i > 0) {
                        str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, ", ");
                    }
                    StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
                    m18m.append(clipDescription.getMimeType(i));
                    str = m18m.toString();
                }
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 375908576, 7, "Clip description: handlingDrag=%b itemCount=%d mimeTypes=%s", Boolean.valueOf(z3), Long.valueOf(itemCount), String.valueOf(str));
            }
            if (perDisplay.isHandlingDrag && !MultiWindowCoreState.MW_ENABLED && MultiWindowOverheatUI.showIfNeeded(this.mContext)) {
                return false;
            }
            boolean z4 = perDisplay.isHandlingDrag;
            if (z4) {
                perDisplay.visibleTasks.update();
                ExecutableAppHolder executableAppHolder = perDisplay.executableAppHolder;
                executableAppHolder.mResult = new MimeTypeAppResult(executableAppHolder.mMultiInstanceBlockList, executableAppHolder.mMultiInstanceAllowList, dragEvent.getClipData().getItemAt(0).getActivityInfo(), null);
                executableAppHolder.mIsMimeType = true;
                perDisplay.dropTargetUiController = new MimeTypeDropTargetController(this, this.mDisplayController, this.mSplitScreen, this.mLogger);
            } else if (!z4) {
                perDisplay.visibleTasks.update();
                ExecutableAppHolder executableAppHolder2 = perDisplay.executableAppHolder;
                ClipData clipData = dragEvent.getClipData();
                DragAndDropPermissions obtain = DragAndDropPermissions.obtain(dragEvent);
                executableAppHolder2.getClass();
                if (clipData != null) {
                    String callingPackageName = clipData.getCallingPackageName();
                    executableAppHolder2.mCallingPackageName = callingPackageName;
                    boolean contains = executableAppHolder2.mCallingPackageBlockList.mBlockList.contains(callingPackageName);
                    boolean z5 = ExecutableAppHolder.DEBUG;
                    if (!contains) {
                        int flags = obtain != null ? obtain.getFlags() : 0;
                        executableAppHolder2.mCallingUserId = clipData.getCallingUserId();
                        if (z5) {
                            StringBuilder sb = new StringBuilder("extractFrom: clipData=");
                            StringBuilder sb2 = new StringBuilder(128);
                            sb2.append("ClipData { ");
                            clipData.toShortString(sb2, !z5);
                            sb2.append(" }");
                            sb.append(sb2.toString());
                            sb.append(", from : ");
                            sb.append(executableAppHolder2.mCallingPackageName);
                            Slog.d("ExecutableAppHolder", sb.toString());
                        }
                        AppResultFactory appResultFactory = executableAppHolder2.mAppResultFactory;
                        appResultFactory.getClass();
                        AppResultFactory.ResultExtra resultExtra = new AppResultFactory.ResultExtra();
                        Iterator it = appResultFactory.mResolvers.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                Optional makeFrom = ((Resolver) it.next()).makeFrom(clipData, flags, resultExtra);
                                if (makeFrom.isPresent()) {
                                    nonResizeableAppsResult = (AppResult) makeFrom.get();
                                    break;
                                }
                            } else {
                                nonResizeableAppsResult = resultExtra.mNonResizeableAppOnly ? new NonResizeableAppsResult() : null;
                            }
                        }
                        executableAppHolder2.mResult = nonResizeableAppsResult;
                        if (nonResizeableAppsResult != null) {
                            z = true;
                            perDisplay.isHandlingDrag |= z;
                            if (z) {
                                perDisplay.dropTargetUiController = new LaunchableDataDropTargetController(this.mContext, this, this.mDisplayController);
                            }
                        }
                    } else if (z5) {
                        Slog.d("ExecutableAppHolder", "Failed to update from clipData due to callingPackage is in block list.");
                    }
                }
                z = false;
                perDisplay.isHandlingDrag |= z;
                if (z) {
                }
            }
            DragAndDropClientRecord from = DragAndDropClientRecord.from(dragEvent.getClipData(), displayId);
            perDisplay.dragAndDropClientRecord = from;
            if (from != null) {
                try {
                    from.mClient.onConnected(this.mProxy.asBinder(), from.mDisplayId);
                } catch (RemoteException unused) {
                    Slog.d("DragAndDropClient", "Failed to connect.");
                }
                DragAndDropClientRecord dragAndDropClientRecord = perDisplay.dragAndDropClientRecord;
                dragAndDropClientRecord.getClass();
                try {
                    z2 = dragAndDropClientRecord.mClient.getInitialDropTargetVisible();
                } catch (RemoteException unused2) {
                    Slog.d("DragAndDropClient", "Failed to disconnect.");
                    z2 = true;
                }
                perDisplay.hideRequested = !z2;
                Rect rect2 = perDisplay.mHiddenDropTargetArea;
                DragAndDropClientRecord dragAndDropClientRecord2 = perDisplay.dragAndDropClientRecord;
                dragAndDropClientRecord2.getClass();
                try {
                    rect = dragAndDropClientRecord2.mClient.getHiddenDropTargetArea();
                } catch (RemoteException unused3) {
                    Slog.d("DragAndDropClient", "Failed to disconnect.");
                    rect = new Rect();
                }
                rect2.set(rect);
            }
        }
        IDropTargetUiController iDropTargetUiController = perDisplay.dropTargetUiController;
        if (iDropTargetUiController != null) {
            boolean onDrag = iDropTargetUiController.onDrag(dragEvent, displayId, perDisplay);
            if (dragEvent.getAction() == 4) {
                clearState(perDisplay);
            } else if (!onDrag && dragEvent.getAction() == 1) {
                setDropTargetWindowVisibility(perDisplay, 4);
                clearState(perDisplay);
            }
            return onDrag;
        }
        if (!perDisplay.isHandlingDrag) {
            return false;
        }
        switch (dragEvent.getAction()) {
            case 1:
                if (perDisplay.activeDragCount != 0) {
                    Slog.w("DragAndDropController", "Unexpected drag start during an active drag");
                    break;
                } else {
                    InstanceId logStart = this.mLogger.logStart(dragEvent);
                    perDisplay.activeDragCount++;
                    ((DropTargetLayout) perDisplay.dragLayout).prepare(this.mDisplayController.getDisplayLayout(displayId), dragEvent.getClipData(), logStart, null, null, null);
                    setDropTargetWindowVisibility(perDisplay, 0);
                    notifyDragStarted();
                    break;
                }
            case 2:
                ((DropTargetLayout) perDisplay.dragLayout).update(dragEvent);
                break;
            case 3:
                ((DropTargetLayout) perDisplay.dragLayout).update(dragEvent);
                break;
            case 4:
                IDragLayout iDragLayout = perDisplay.dragLayout;
                if (((DropTargetLayout) iDragLayout).mHasDropped) {
                    DragAndDropEventLogger dragAndDropEventLogger = this.mLogger;
                    dragAndDropEventLogger.getClass();
                    dragAndDropEventLogger.log(DragAndDropEventLogger.DragAndDropUiEventEnum.GLOBAL_APP_DRAG_DROPPED, dragAndDropEventLogger.mActivityInfo);
                } else {
                    perDisplay.activeDragCount--;
                    iDragLayout.hide(dragEvent, new DragAndDropController$$ExternalSyntheticLambda1(this, perDisplay, 0));
                }
                DragAndDropEventLogger dragAndDropEventLogger2 = this.mLogger;
                dragAndDropEventLogger2.getClass();
                dragAndDropEventLogger2.log(DragAndDropEventLogger.DragAndDropUiEventEnum.GLOBAL_APP_DRAG_END, dragAndDropEventLogger2.mActivityInfo);
                break;
            case 5:
                ((DropTargetLayout) perDisplay.dragLayout).show();
                break;
            case 6:
                perDisplay.dragLayout.hide(dragEvent, null);
                break;
        }
        return false;
    }

    public boolean supportsMultiWindow() {
        return ActivityTaskManager.supportsMultiWindow(this.mContext);
    }

    @Override // android.content.ComponentCallbacks2
    public final void onTrimMemory(int i) {
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {
    }
}
