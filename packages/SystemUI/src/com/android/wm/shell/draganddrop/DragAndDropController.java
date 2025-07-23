package com.android.wm.shell.draganddrop;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
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
import android.window.WindowContainerToken;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.launcher3.icons.IconProvider;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.DnDSnackBarController;
import com.android.wm.shell.common.DnDSnackBarWindow;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.draganddrop.GlobalDragListener;
import com.android.wm.shell.draganddrop.SplitDragPolicy;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.multiwindow.IDragAndDropControllerProxy;
import com.samsung.android.rune.CoreRune;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DragAndDropController implements RemoteCallable, GlobalDragListener.GlobalDragListenerCallback, DisplayController.OnDisplaysChangedListener, ShellTaskOrganizer.TaskVanishedListener, View.OnDragListener, ComponentCallbacks2 {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final DesktopState mDesktopState;
    public final DisplayController mDisplayController;
    public final GlobalDragListener mGlobalDragListener;
    public boolean mIsUserSetup;
    public final DragAndDropEventLogger mLogger;
    public final ShellExecutor mMainExecutor;
    public final MultiInstanceHelper mMultiInstanceHelper;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public final ShellTaskOrganizer mShellTaskOrganizer;
    public SplitScreenController mSplitScreen;
    public final Transitions mTransitions;
    public final ArrayList mListeners = new ArrayList();
    public final SparseArray mDisplayDropTargets = new SparseArray();
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
    public final AnonymousClass1 mDismissReceiver = new BroadcastReceiver() { // from class: com.android.wm.shell.draganddrop.DragAndDropController.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -2395132550410191693L, 0, String.valueOf(intent.getAction()));
            }
            DragAndDropController dragAndDropController = DragAndDropController.this;
            for (int size = dragAndDropController.mDisplayDropTargets.size() - 1; size >= 0; size--) {
                PerDisplay perDisplay = (PerDisplay) dragAndDropController.mDisplayDropTargets.valueAt(size);
                Handler handler = perDisplay.rootView.getHandler();
                if (handler == null) {
                    Slog.w("DragAndDropController", "Couldn't make dropTarget invisible since handler isn't existed.");
                } else {
                    handler.post(new DragAndDropController$$ExternalSyntheticLambda3(dragAndDropController, perDisplay, 1));
                }
            }
        }
    };
    public final Rect mTmpRect = new Rect();
    public final AnonymousClass2 mProxy = new AnonymousClass2();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.draganddrop.DragAndDropController$2, reason: invalid class name */
    public class AnonymousClass2 extends IDragAndDropControllerProxy.Stub {
        public AnonymousClass2() {
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
                        DragAndDropController.AnonymousClass2 anonymousClass2 = DragAndDropController.AnonymousClass2.this;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class IDragAndDropImpl extends IDragAndDrop$Stub implements ExternalInterfaceBinder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public DragAndDropController mController;

        public IDragAndDropImpl(DragAndDropController dragAndDropController) {
            this.mController = dragAndDropController;
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PerDisplay implements HardwareRenderer.FrameDrawingCallback {
        public int activeDragCount;
        public final Context context;
        public final int displayId;
        public final DnDSnackBarController dndSnackBarController;
        public DragAndDropClientRecord dragAndDropClientRecord;
        public final DragLayoutProvider dragLayout;
        public DragSession dragSession;
        public IDropTargetUiController dropTargetUiController;
        public final ExecutableAppHolder executableAppHolder;
        public boolean hasDrawn;
        public boolean hideRequested;
        public boolean isHandlingDrag;
        public final Rect mHiddenDropTargetArea;
        public final FrameLayout rootView;
        public final SmartTipController smartTipController;
        public final VisibleTasks visibleTasks;
        public int windowVisibility;
        public final WindowManager wm;

        public PerDisplay(int i, Context context, WindowManager windowManager, FrameLayout frameLayout, DragLayoutProvider dragLayoutProvider) {
            this(i, context, windowManager, frameLayout, dragLayoutProvider, new SmartTipController(context), new DnDSnackBarController(context), new ExecutableAppHolder(context));
        }

        public final void onFrameDraw(long j) {
            this.hasDrawn = true;
        }

        public PerDisplay(int i, Context context, WindowManager windowManager, FrameLayout frameLayout, DragLayoutProvider dragLayoutProvider, SmartTipController smartTipController, DnDSnackBarController dnDSnackBarController, ExecutableAppHolder executableAppHolder) {
            this.mHiddenDropTargetArea = new Rect();
            this.displayId = i;
            this.context = context;
            this.wm = windowManager;
            this.rootView = frameLayout;
            this.dragLayout = dragLayoutProvider;
            this.smartTipController = smartTipController;
            this.dndSnackBarController = dnDSnackBarController;
            this.executableAppHolder = executableAppHolder;
            if (executableAppHolder != null) {
                synchronized (executableAppHolder.mCallbacks) {
                    try {
                        if (!((ArrayList) executableAppHolder.mCallbacks).contains(dragLayoutProvider)) {
                            ((ArrayList) executableAppHolder.mCallbacks).add(dragLayoutProvider);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                this.visibleTasks = new VisibleTasks(i);
            } else {
                this.visibleTasks = null;
            }
            this.windowVisibility = frameLayout.getVisibility();
        }
    }

    /* JADX WARN: Type inference failed for: r8v4, types: [com.android.wm.shell.draganddrop.DragAndDropController$1] */
    public DragAndDropController(Context context, ShellInit shellInit, ShellController shellController, ShellCommandHandler shellCommandHandler, ShellTaskOrganizer shellTaskOrganizer, DisplayController displayController, UiEventLogger uiEventLogger, IconProvider iconProvider, GlobalDragListener globalDragListener, Transitions transitions, Lazy lazy, MultiInstanceHelper multiInstanceHelper, DesktopState desktopState, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mShellController = shellController;
        this.mShellCommandHandler = shellCommandHandler;
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mDisplayController = displayController;
        this.mLogger = new DragAndDropEventLogger(uiEventLogger);
        this.mGlobalDragListener = globalDragListener;
        this.mTransitions = transitions;
        this.mMultiInstanceHelper = multiInstanceHelper;
        this.mDesktopState = desktopState;
        this.mMainExecutor = shellExecutor;
        shellInit.addInitCallback(new DragAndDropController$$ExternalSyntheticLambda1(this, 0), this);
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
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -7433667676133965876L, 5, Long.valueOf(perDisplay.displayId), Long.valueOf(i));
                return;
            }
            return;
        }
        if (perDisplay.windowVisibility == i) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 5303986652599694531L, 0, String.valueOf(i));
                return;
            }
            return;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 2483469667165484778L, 5, Long.valueOf(perDisplay.displayId), Long.valueOf(i));
        }
        perDisplay.rootView.setVisibility(i);
        if (i == 0) {
            perDisplay.rootView.requestApplyInsets();
            if (!perDisplay.hasDrawn && perDisplay.rootView.getViewRootImpl() != null) {
                perDisplay.rootView.getViewRootImpl().registerRtFrameCallback(perDisplay);
            }
        } else {
            perDisplay.hasDrawn = false;
        }
        perDisplay.windowVisibility = i;
    }

    public void addDisplayDropTarget(int i, Context context, WindowManager windowManager, FrameLayout frameLayout, DragLayoutProvider dragLayoutProvider) {
        this.mDisplayDropTargets.put(i, new PerDisplay(i, context, windowManager, frameLayout, dragLayoutProvider));
    }

    public boolean deviceSupportsSplitScreenMultiWindow() {
        return ActivityTaskManager.deviceSupportsMultiWindow(this.mContext);
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    public final boolean handleDrop(DragEvent dragEvent, PerDisplay perDisplay) {
        boolean z;
        boolean z2;
        int i;
        SplitDragPolicy.LaunchOptions launchOptions;
        SplitDragPolicy.LaunchOptions launchOptions2;
        SplitDragPolicy.Starter starter;
        SplitDragPolicy.Target target;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        dragEvent.getDragSurface();
        perDisplay.activeDragCount--;
        int i2 = perDisplay.dragSession.hideDragSourceTaskId;
        WindowContainerToken windowContainerToken = (i2 == -1 || (runningTaskInfo = this.mShellTaskOrganizer.getRunningTaskInfo(i2)) == null) ? null : runningTaskInfo.token;
        SplitDragPolicy.Target target2 = ((DropTargetLayout) perDisplay.dragLayout).mCurrentTarget;
        if (target2 != null && !target2.isResizable) {
            this.mDisplayController.getDisplayLayout(perDisplay.displayId).getStableBounds(this.mTmpRect, false);
            DnDSnackBarController dnDSnackBarController = perDisplay.dndSnackBarController;
            Rect rect = this.mTmpRect;
            DnDSnackBarWindow dnDSnackBarWindow = dnDSnackBarController.mView;
            if (dnDSnackBarWindow != null && dnDSnackBarWindow.isAttachedToWindow()) {
                dnDSnackBarController.mView.hide();
            }
            if (!dnDSnackBarController.mWasShownSnackBar && ActivityTaskManager.supportsSplitScreenMultiWindow(dnDSnackBarController.mContext)) {
                final DnDSnackBarWindow dnDSnackBarWindow2 = (DnDSnackBarWindow) LayoutInflater.from(dnDSnackBarController.mContext).inflate(R.layout.dnd_snack_bar, (ViewGroup) null);
                dnDSnackBarController.mView = dnDSnackBarWindow2;
                dnDSnackBarWindow2.getClass();
                dnDSnackBarWindow2.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.common.DnDSnackBarWindow$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                        DnDSnackBarWindow dnDSnackBarWindow3 = DnDSnackBarWindow.this;
                        int i3 = DnDSnackBarWindow.$r8$clinit;
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
                        DnDSnackBarController dnDSnackBarController2 = dnDSnackBarWindow3.mCallbacks;
                        dnDSnackBarController2.getClass();
                        Bundle bundle = new Bundle();
                        bundle.putString(":settings:fragment_args_key", "multi_window_for_all_apps");
                        Intent intent = new Intent();
                        intent.setAction("com.samsung.settings.LABS_SETTINGS");
                        intent.putExtra(":settings:show_fragment_args", bundle);
                        intent.addFlags(268435456);
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
        DragLayoutProvider dragLayoutProvider = perDisplay.dragLayout;
        DragAndDropController$$ExternalSyntheticLambda3 dragAndDropController$$ExternalSyntheticLambda3 = new DragAndDropController$$ExternalSyntheticLambda3(this, perDisplay, 2);
        DropTargetLayout dropTargetLayout = (DropTargetLayout) dragLayoutProvider;
        dropTargetLayout.mHasDropped = true;
        if (dropTargetLayout.mIsHideDragSourceTask && ((target = dropTargetLayout.mCurrentTarget) == null || target.type == 0)) {
            z = false;
            z2 = false;
        } else {
            if (dropTargetLayout.mCurrentTarget == null) {
                if (!(dropTargetLayout.mIsIntentSenderDropTarget ? false : dropTargetLayout.mDismissView.mIsEnterDismissButton)) {
                    z = false;
                    z2 = true;
                }
            }
            z = true;
            z2 = true;
        }
        if (z2) {
            int displayId = dropTargetLayout.getDisplay() == null ? 0 : dropTargetLayout.getDisplay().getDisplayId();
            SplitDragPolicy splitDragPolicy = dropTargetLayout.mPolicy;
            SplitDragPolicy.Target target3 = dropTargetLayout.mCurrentTarget;
            DragAndDropPermissions obtain = DragAndDropPermissions.obtain(dragEvent);
            if (target3 == null) {
                splitDragPolicy.getClass();
            } else if (splitDragPolicy.mTargets.contains(target3)) {
                DesktopStateImpl.Companion.getClass();
                boolean inDesktopWindowing = DesktopStateImpl.Companion.inDesktopWindowing(displayId);
                int i3 = target3.type;
                if (inDesktopWindowing) {
                    launchOptions2 = new SplitDragPolicy.LaunchOptions(-1, 0, -1, false, (int) dragEvent.getX(), (int) dragEvent.getY());
                } else {
                    boolean z3 = CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET;
                    SplitScreenController splitScreenController = splitDragPolicy.mSplitScreen;
                    if (z3 && splitDragPolicy.supportMultiSplitDropTarget()) {
                        if ((i3 == 1 || i3 == 3 || i3 == 2 || i3 == 4) || target3.isMultiSplit()) {
                            splitScreenController.onDroppedToSplit(-1, splitDragPolicy.mLoggerSessionId);
                        }
                        if (target3.isMultiSplit()) {
                            int i4 = 24;
                            switch (i3) {
                                case 6:
                                case 10:
                                case 11:
                                    break;
                                case 7:
                                    i4 = 72;
                                    break;
                                case 8:
                                    i4 = 48;
                                    break;
                                case 9:
                                case 12:
                                case 13:
                                    i4 = 96;
                                    break;
                                default:
                                    i4 = 0;
                                    break;
                            }
                            launchOptions2 = new SplitDragPolicy.LaunchOptions(-1, i4, -1, i3 == 10 || i3 == 11 || i3 == 12 || i3 == 13);
                        } else {
                            if (i3 == 1 || i3 == 3 || i3 == 2 || i3 == 4) {
                                launchOptions = new SplitDragPolicy.LaunchOptions((i3 == 2 || i3 == 1) ? 0 : 1, 0, (i3 == 2 || i3 == 4) ? 1 : 0, false);
                            } else {
                                launchOptions2 = new SplitDragPolicy.LaunchOptions(-1, 0, -1, false);
                            }
                        }
                    } else {
                        int i5 = (i3 == 2 || i3 == 1) ? 1 : 0;
                        if (i3 == 0 || splitScreenController == null) {
                            i = -1;
                        } else {
                            i = i5 ^ 1;
                            splitScreenController.onDroppedToSplit(i, splitDragPolicy.mLoggerSessionId);
                        }
                        launchOptions = new SplitDragPolicy.LaunchOptions(i, 0, -1, false);
                    }
                    launchOptions2 = launchOptions;
                }
                if (i3 != 5 || (starter = splitDragPolicy.mFreeformStarter) == null) {
                    starter = splitDragPolicy.mSplitscreenStarter;
                }
                SplitDragPolicy.Starter starter2 = starter;
                DragSession dragSession = splitDragPolicy.mSession;
                if (dragSession.appData != null) {
                    splitDragPolicy.launchApp(dragSession, starter2, launchOptions2.splitPosition, windowContainerToken, -1, obtain, launchOptions2, displayId);
                } else {
                    splitDragPolicy.launchIntent(dragSession, starter2, launchOptions2.splitPosition, -1, obtain, launchOptions2, displayId);
                }
                if (target3.alreadyRun) {
                    Toast.makeText(splitDragPolicy.mContext, splitDragPolicy.mContext.getResources().getString(R.string.multiwindow_app_already_in_this_window), 0).show();
                }
            }
        }
        dropTargetLayout.hide(dragAndDropController$$ExternalSyntheticLambda3, true);
        android.util.secutil.Slog.d("DropTargetLayout", "Drop result is " + z);
        return z;
    }

    public boolean isUserSetup() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        return (Settings.Global.getInt(contentResolver, "device_provisioned", 0) != 0) && (Settings.Secure.getIntForUser(contentResolver, SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, -2) != 0);
    }

    public final boolean notifyListeners(Function function) {
        for (int i = 0; i < this.mListeners.size(); i++) {
            if (((Boolean) function.apply((DragAndDropListener) this.mListeners.get(i))).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        this.mMainExecutor.execute(new DragAndDropController$$ExternalSyntheticLambda3(this, configuration, 3));
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayAdded(int i) {
        int i2;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 2150044987932031977L, 1, Long.valueOf(i));
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
        layoutParams.multiWindowFlags |= 16;
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(createWindowContext).inflate(R.layout.global_drop_target, (ViewGroup) null);
        frameLayout.setOnDragListener(this);
        frameLayout.setVisibility(4);
        DropTargetLayout dropTargetLayout = new DropTargetLayout(createWindowContext, this.mSplitScreen, this.mTransaction, this.mTransitions);
        frameLayout.addView(dropTargetLayout, new ViewGroup.LayoutParams(-1, -1));
        try {
            windowManager.addView(frameLayout, layoutParams);
            i2 = i;
            try {
                addDisplayDropTarget(i2, createWindowContext, windowManager, frameLayout, dropTargetLayout);
                createWindowContext.registerComponentCallbacks(this);
            } catch (WindowManager.InvalidDisplayException unused) {
                Slog.w("DragAndDropController", "Unable to add view for display id: " + i2);
            }
        } catch (WindowManager.InvalidDisplayException unused2) {
            i2 = i;
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -1937012838822497354L, 1, Long.valueOf(i));
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
            DragSession dragSession = dropTargetLayout.mPolicy.mSession;
            if (dragSession != null) {
                dragSession.displayLayout = displayLayout;
            }
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayRemoved(int i) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 5861505718780965768L, 1, Long.valueOf(i));
        }
        PerDisplay perDisplay = (PerDisplay) this.mDisplayDropTargets.get(i);
        if (perDisplay == null) {
            return;
        }
        perDisplay.context.unregisterComponentCallbacks(this);
        perDisplay.wm.removeViewImmediate(perDisplay.rootView);
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:114:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x028a  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0308  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x014c  */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r7v4 */
    @Override // android.view.View.OnDragListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onDrag(android.view.View r27, android.view.DragEvent r28) {
        /*
            Method dump skipped, instructions count: 1194
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.draganddrop.DragAndDropController.onDrag(android.view.View, android.view.DragEvent):boolean");
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskVanishedListener
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        PerDisplay perDisplay;
        if (runningTaskInfo.baseIntent == null) {
            return;
        }
        int i = 0;
        while (true) {
            if (i >= this.mDisplayDropTargets.size()) {
                perDisplay = null;
                break;
            }
            perDisplay = (PerDisplay) this.mDisplayDropTargets.valueAt(i);
            if (perDisplay.isHandlingDrag) {
                break;
            } else {
                i++;
            }
        }
        if (perDisplay == null || perDisplay.activeDragCount <= 0 || !perDisplay.isHandlingDrag) {
            return;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -3119176285175531349L, 1, Long.valueOf(runningTaskInfo.taskId), String.valueOf(runningTaskInfo.baseIntent.getComponent()));
        }
        perDisplay.dragSession.updateRunningTask();
        perDisplay.dragLayout.getClass();
    }

    public boolean supportsMultiWindow() {
        return ActivityTaskManager.supportsMultiWindow(this.mContext);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DragAndDropListener {
        default boolean onUnhandledDrag(PendingIntent pendingIntent, int i, DragEvent dragEvent, GlobalDragListener$onUnhandledDrop$1 globalDragListener$onUnhandledDrop$1) {
            return false;
        }

        default void onDragStarted() {
        }
    }

    @Override // android.content.ComponentCallbacks2
    public final void onTrimMemory(int i) {
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {
    }
}
