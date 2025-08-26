package com.android.wm.shell.draganddrop;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
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
import android.window.WindowContainerToken;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.launcher3.icons.IconProvider;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda4;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.DnDSnackBarController;
import com.android.wm.shell.common.DnDSnackBarWindow;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.common.MultiWindowOverheatUI;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.draganddrop.AppResultFactory;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.draganddrop.DragAndDropEventLogger;
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
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.multiwindow.IDragAndDropControllerProxy;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

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
                        DragAndDropController.AnonymousClass2 anonymousClass2 = this.f$0;
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

    /* JADX WARN: Removed duplicated region for block: B:40:0x010a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
                        DnDSnackBarWindow dnDSnackBarWindow3 = dnDSnackBarWindow2;
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
                        DnDSnackBarWindow dnDSnackBarWindow3 = dnDSnackBarWindow2;
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
        } else if (dropTargetLayout.mCurrentTarget != null) {
            z = true;
            z2 = true;
        } else {
            if (!(dropTargetLayout.mIsIntentSenderDropTarget ? false : dropTargetLayout.mDismissView.mIsEnterDismissButton)) {
                z = false;
            }
            z2 = true;
        }
        if (z2) {
            int displayId = dropTargetLayout.getDisplay() == null ? 0 : dropTargetLayout.getDisplay().getDisplayId();
            SplitDragPolicy splitDragPolicy = dropTargetLayout.mPolicy;
            SplitDragPolicy.Target target3 = dropTargetLayout.mCurrentTarget;
            DragAndDropPermissions dragAndDropPermissionsObtain = DragAndDropPermissions.obtain(dragEvent);
            if (target3 == null) {
                splitDragPolicy.getClass();
            } else if (splitDragPolicy.mTargets.contains(target3)) {
                DesktopStateImpl.Companion.getClass();
                boolean zInDesktopWindowing = DesktopStateImpl.Companion.inDesktopWindowing(displayId);
                int i3 = target3.type;
                if (zInDesktopWindowing) {
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
                    splitDragPolicy.launchApp(dragSession, starter2, launchOptions2.splitPosition, windowContainerToken, -1, dragAndDropPermissionsObtain, launchOptions2, displayId);
                } else {
                    splitDragPolicy.launchIntent(dragSession, starter2, launchOptions2.splitPosition, -1, dragAndDropPermissionsObtain, launchOptions2, displayId);
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
        Context contextCreateWindowContext = this.mDisplayController.getDisplayContext(i).createWindowContext(2016, null);
        WindowManager windowManager = (WindowManager) contextCreateWindowContext.getSystemService(WindowManager.class);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2016, 16777224, -3);
        layoutParams.privateFlags |= -2147483568;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("ShellDropTarget");
        layoutParams.flags |= 512;
        layoutParams.multiWindowFlags |= 16;
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(contextCreateWindowContext).inflate(R.layout.global_drop_target, (ViewGroup) null);
        frameLayout.setOnDragListener(this);
        frameLayout.setVisibility(4);
        DropTargetLayout dropTargetLayout = new DropTargetLayout(contextCreateWindowContext, this.mSplitScreen, this.mTransaction, this.mTransitions, this.mMultiInstanceHelper);
        frameLayout.addView(dropTargetLayout, new ViewGroup.LayoutParams(-1, -1));
        try {
            windowManager.addView(frameLayout, layoutParams);
            i2 = i;
            try {
                addDisplayDropTarget(i2, contextCreateWindowContext, windowManager, frameLayout, dropTargetLayout);
                contextCreateWindowContext.registerComponentCallbacks(this);
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
    /* JADX WARN: Removed duplicated region for block: B:121:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x028a  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02a0  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0308  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x014c  */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r7v4 */
    @Override // android.view.View.OnDragListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onDrag(View view, DragEvent dragEvent) {
        DragSession dragSession;
        int i;
        ?? r7;
        boolean z;
        boolean z2;
        AppResult nonResizeableAppsResult;
        boolean z3;
        DragAndDropClientRecord dragAndDropClientRecordFrom;
        boolean initialDropTargetVisible;
        Rect rect;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 6161155429892961383L, 680, String.valueOf(DragEvent.actionToString(dragEvent.getAction())), Double.valueOf(dragEvent.getX()), Double.valueOf(dragEvent.getY()), Double.valueOf(dragEvent.getOffsetX()), Double.valueOf(dragEvent.getOffsetY()));
        }
        int displayId = view.getDisplay().getDisplayId();
        PerDisplay perDisplay = (PerDisplay) this.mDisplayDropTargets.get(displayId);
        ClipDescription clipDescription = dragEvent.getClipDescription();
        if (perDisplay == null || perDisplay.context.getResources().getConfiguration().isDexMode()) {
            return false;
        }
        WMShell$$ExternalSyntheticLambda4 wMShell$$ExternalSyntheticLambda4 = this.mSplitScreen.mIsKeyguardOccludedAndShowingSupplier;
        if (wMShell$$ExternalSyntheticLambda4 != null ? wMShell$$ExternalSyntheticLambda4.getAsBoolean() : false) {
            Slog.w("DragAndDropController", "isKeyguardOccludedAndShowing=true");
            return false;
        }
        if (dragEvent.getAction() == 1) {
            Slog.d("DragAndDropController", "ACTION_DRAG_STARTED");
            if (dragEvent.getClipData() == null) {
                Slog.w("DragAndDropController", "clipdata is null");
                return false;
            }
            if (!this.mIsUserSetup) {
                boolean zIsUserSetup = isUserSetup();
                this.mIsUserSetup = zIsUserSetup;
                if (!zIsUserSetup) {
                    Slog.w("DragAndDropController", "User setup is not yet completed.");
                    return false;
                }
            } else if (!deviceSupportsSplitScreenMultiWindow()) {
                Slog.w("DragAndDropController", "This device does not support multi-windows.");
                return false;
            }
            dragSession = new DragSession(ActivityTaskManager.getInstance(), this.mDisplayController.getDisplayLayout(displayId), dragEvent.getClipData(), dragEvent.getDragFlags(), perDisplay.executableAppHolder, perDisplay.visibleTasks);
            perDisplay.dragSession = dragSession;
            dragSession.updateRunningTask();
            ActivityManager.RunningTaskInfo runningTaskInfo = dragSession.runningTaskInfo;
            boolean z4 = runningTaskInfo != null && runningTaskInfo.isFreeform() && ((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode;
            if (dragEvent.getClipData() == null || dragEvent.getClipData().getItemCount() <= 0) {
                z = false;
                perDisplay.isHandlingDrag = z;
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
                    long itemCount = dragEvent.getClipData() != null ? dragEvent.getClipData().getItemCount() : -1L;
                    String string = "";
                    if (clipDescription != null) {
                        for (int i2 = 0; i2 < clipDescription.getMimeTypeCount(); i2++) {
                            if (i2 > 0) {
                                string = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string, ", ");
                            }
                            StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(string);
                            sbM.append(clipDescription.getMimeType(i2));
                            string = sbM.toString();
                        }
                    }
                    String strValueOf = String.valueOf(string);
                    int dragFlags = dragEvent.getDragFlags();
                    StringJoiner stringJoiner = new StringJoiner("|");
                    if ((dragFlags & 256) != 0) {
                        stringJoiner.add("GLOBAL");
                    }
                    if ((dragFlags & 1) != 0) {
                        stringJoiner.add("GLOBAL_URI_READ");
                    }
                    if ((dragFlags & 2) != 0) {
                        stringJoiner.add("GLOBAL_URI_WRITE");
                    }
                    if ((dragFlags & 64) != 0) {
                        stringJoiner.add("GLOBAL_PERSISTABLE_URI_PERMISSION");
                    }
                    if ((dragFlags & 128) != 0) {
                        stringJoiner.add("GLOBAL_PREFIX_URI_PERMISSION");
                    }
                    if ((dragFlags & 512) != 0) {
                        stringJoiner.add("OPAQUE");
                    }
                    if ((dragFlags & 1024) != 0) {
                        stringJoiner.add("ACCESSIBILITY_ACTION");
                    }
                    if ((dragFlags & 2048) != 0) {
                        stringJoiner.add("REQUEST_SURFACE_FOR_RETURN_ANIMATION");
                    }
                    if ((dragFlags & 4096) != 0) {
                        stringJoiner.add("GLOBAL_SAME_APPLICATION");
                    }
                    if ((dragFlags & 8192) != 0) {
                        stringJoiner.add("START_INTENT_SENDER_ON_UNHANDLED_DRAG");
                    }
                    if ((dragFlags & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
                        stringJoiner.add("HIDE_CALLING_TASK_ON_DRAG_START");
                    }
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 7890563632905928192L, 7, Boolean.valueOf(z), Long.valueOf(itemCount), strValueOf, String.valueOf(stringJoiner.toString()));
                }
                if (perDisplay.isHandlingDrag || MultiWindowCoreState.MW_ENABLED || !MultiWindowOverheatUI.showIfNeeded(this.mContext)) {
                    if (perDisplay.isHandlingDrag || (dragEvent.getDragFlags() & 8192) == 0) {
                        z2 = perDisplay.isHandlingDrag;
                        if (!z2) {
                            perDisplay.visibleTasks.update();
                            ExecutableAppHolder executableAppHolder = perDisplay.executableAppHolder;
                            executableAppHolder.mResult = new MimeTypeAppResult(executableAppHolder.mMultiInstanceBlockList, executableAppHolder.mMultiInstanceAllowList, dragEvent.getClipData().getItemAt(0).getActivityInfo(), null);
                            executableAppHolder.mIsMimeType = true;
                            perDisplay.dropTargetUiController = new MimeTypeDropTargetController(this, this.mDisplayController, this.mShellTaskOrganizer, this.mLogger);
                        } else if (!z2) {
                            perDisplay.visibleTasks.update();
                            ExecutableAppHolder executableAppHolder2 = perDisplay.executableAppHolder;
                            ClipData clipData = dragEvent.getClipData();
                            DragAndDropPermissions dragAndDropPermissionsObtain = DragAndDropPermissions.obtain(dragEvent);
                            executableAppHolder2.getClass();
                            if (clipData == null) {
                                z3 = false;
                                perDisplay.isHandlingDrag |= z3;
                                if (z3) {
                                    perDisplay.dropTargetUiController = new LaunchableDataDropTargetController(this.mContext, this, this.mDisplayController);
                                }
                            } else {
                                String callingPackageName = clipData.getCallingPackageName();
                                executableAppHolder2.mCallingPackageName = callingPackageName;
                                if (!executableAppHolder2.mCallingPackageBlockList.mBlockList.contains(callingPackageName)) {
                                    int flags = dragAndDropPermissionsObtain != null ? dragAndDropPermissionsObtain.getFlags() : 0;
                                    executableAppHolder2.mCallingUserId = clipData.getCallingUserId();
                                    AppResultFactory appResultFactory = executableAppHolder2.mAppResultFactory;
                                    appResultFactory.getClass();
                                    AppResultFactory.ResultExtra resultExtra = new AppResultFactory.ResultExtra();
                                    ArrayList arrayList = appResultFactory.mResolvers;
                                    int size = arrayList.size();
                                    int i3 = 0;
                                    while (true) {
                                        if (i3 < size) {
                                            Object obj = arrayList.get(i3);
                                            i3++;
                                            Optional optionalMakeFrom = ((BaseResolver) obj).makeFrom(clipData, flags, resultExtra);
                                            if (optionalMakeFrom.isPresent()) {
                                                nonResizeableAppsResult = (AppResult) optionalMakeFrom.get();
                                                break;
                                            }
                                        } else {
                                            nonResizeableAppsResult = resultExtra.mNonResizeableAppOnly ? new NonResizeableAppsResult() : null;
                                        }
                                    }
                                    executableAppHolder2.mResult = nonResizeableAppsResult;
                                    if (nonResizeableAppsResult != null) {
                                        z3 = true;
                                    }
                                    perDisplay.isHandlingDrag |= z3;
                                    if (z3) {
                                    }
                                }
                            }
                        }
                    } else {
                        perDisplay.visibleTasks.update();
                        ExecutableAppHolder executableAppHolder3 = perDisplay.executableAppHolder;
                        executableAppHolder3.mResult = new DefaultAppResult(executableAppHolder3.mMultiInstanceBlockList, executableAppHolder3.mMultiInstanceAllowList, null);
                        perDisplay.dropTargetUiController = new IntentSenderDropTargetController(this.mContext, this, this.mDisplayController);
                    }
                    dragAndDropClientRecordFrom = DragAndDropClientRecord.from(dragEvent.getClipData(), displayId);
                    perDisplay.dragAndDropClientRecord = dragAndDropClientRecordFrom;
                    if (dragAndDropClientRecordFrom != null) {
                        try {
                            dragAndDropClientRecordFrom.mClient.onConnected(this.mProxy.asBinder(), dragAndDropClientRecordFrom.mDisplayId);
                        } catch (RemoteException unused) {
                            Slog.d("DragAndDropClient", "Failed to connect.");
                        }
                        DragAndDropClientRecord dragAndDropClientRecord = perDisplay.dragAndDropClientRecord;
                        dragAndDropClientRecord.getClass();
                        try {
                            initialDropTargetVisible = dragAndDropClientRecord.mClient.getInitialDropTargetVisible();
                        } catch (RemoteException unused2) {
                            Slog.d("DragAndDropClient", "Failed to disconnect.");
                            initialDropTargetVisible = true;
                        }
                        perDisplay.hideRequested = !initialDropTargetVisible;
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
            } else {
                ClipDescription clipDescription2 = dragEvent.getClipDescription();
                if ((clipDescription2.hasMimeType("application/vnd.android.activity") || clipDescription2.hasMimeType("application/vnd.android.shortcut") || clipDescription2.hasMimeType("application/vnd.android.task") || DragUtils.getLaunchIntent(dragEvent.getClipData(), dragEvent.getDragFlags()) != null) && !z4) {
                    z = true;
                }
                perDisplay.isHandlingDrag = z;
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
                }
                if (perDisplay.isHandlingDrag) {
                }
                if (perDisplay.isHandlingDrag) {
                    z2 = perDisplay.isHandlingDrag;
                    if (!z2) {
                    }
                    dragAndDropClientRecordFrom = DragAndDropClientRecord.from(dragEvent.getClipData(), displayId);
                    perDisplay.dragAndDropClientRecord = dragAndDropClientRecordFrom;
                    if (dragAndDropClientRecordFrom != null) {
                    }
                }
            }
            return false;
        }
        dragSession = null;
        this.mSplitScreen.dismissAddToAppPairDialog();
        IDropTargetUiController iDropTargetUiController = perDisplay.dropTargetUiController;
        if (iDropTargetUiController != null) {
            boolean zOnDrag = iDropTargetUiController.onDrag(dragEvent, displayId, perDisplay);
            if (dragEvent.getAction() == 4) {
                clearState(perDisplay);
            } else if (!zOnDrag && dragEvent.getAction() == 1) {
                setDropTargetWindowVisibility(perDisplay, 4);
                clearState(perDisplay);
            }
            return zOnDrag;
        }
        if (perDisplay.isHandlingDrag) {
            switch (dragEvent.getAction()) {
                case 1:
                    if (perDisplay.activeDragCount != 0) {
                        Slog.w("DragAndDropController", "Unexpected drag start during an active drag");
                        break;
                    } else {
                        dragSession.initialize();
                        perDisplay.dragSession = dragSession;
                        perDisplay.activeDragCount++;
                        ((DropTargetLayout) perDisplay.dragLayout).prepare(dragSession, this.mLogger.logStart(dragSession), null, null, false);
                        int i4 = perDisplay.dragSession.hideDragSourceTaskId;
                        if (i4 != -1) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -2922974451172233144L, 1, Long.valueOf(i4));
                            }
                            i = 0;
                            this.mShellTaskOrganizer.setTaskSurfaceVisibility(perDisplay.dragSession.hideDragSourceTaskId, false);
                        } else {
                            i = 0;
                        }
                        setDropTargetWindowVisibility(perDisplay, i);
                        final int i5 = 0;
                        notifyListeners(new Function() { // from class: com.android.wm.shell.draganddrop.DragAndDropController$$ExternalSyntheticLambda2
                            @Override // java.util.function.Function
                            public final Object apply(Object obj2) {
                                DragAndDropController.DragAndDropListener dragAndDropListener = (DragAndDropController.DragAndDropListener) obj2;
                                switch (i5) {
                                    case 0:
                                        int i6 = DragAndDropController.$r8$clinit;
                                        dragAndDropListener.onDragStarted();
                                        break;
                                    default:
                                        int i7 = DragAndDropController.$r8$clinit;
                                        dragAndDropListener.getClass();
                                        break;
                                }
                                return Boolean.FALSE;
                            }
                        });
                        break;
                    }
                case 2:
                    ((DropTargetLayout) perDisplay.dragLayout).update(dragEvent);
                    break;
                case 3:
                    ((DropTargetLayout) perDisplay.dragLayout).update(dragEvent);
                    break;
                case 4:
                    if (((DropTargetLayout) perDisplay.dragLayout).mHasDropped) {
                        DragAndDropEventLogger dragAndDropEventLogger = this.mLogger;
                        dragAndDropEventLogger.getClass();
                        dragAndDropEventLogger.log(DragAndDropEventLogger.DragAndDropUiEventEnum.GLOBAL_APP_DRAG_DROPPED, dragAndDropEventLogger.mActivityInfo);
                    } else {
                        int i6 = perDisplay.dragSession.hideDragSourceTaskId;
                        if (i6 != -1) {
                            r7 = 1;
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -1075144303163786144L, 1, Long.valueOf(i6));
                            }
                            this.mShellTaskOrganizer.setTaskSurfaceVisibility(perDisplay.dragSession.hideDragSourceTaskId, true);
                        } else {
                            r7 = 1;
                        }
                        perDisplay.activeDragCount -= r7;
                        ((DropTargetLayout) perDisplay.dragLayout).hide(new DragAndDropController$$ExternalSyntheticLambda3(this, perDisplay, 0), r7);
                    }
                    DragAndDropEventLogger dragAndDropEventLogger2 = this.mLogger;
                    dragAndDropEventLogger2.getClass();
                    dragAndDropEventLogger2.log(DragAndDropEventLogger.DragAndDropUiEventEnum.GLOBAL_APP_DRAG_END, dragAndDropEventLogger2.mActivityInfo);
                    final int i7 = 1;
                    notifyListeners(new Function() { // from class: com.android.wm.shell.draganddrop.DragAndDropController$$ExternalSyntheticLambda2
                        @Override // java.util.function.Function
                        public final Object apply(Object obj2) {
                            DragAndDropController.DragAndDropListener dragAndDropListener = (DragAndDropController.DragAndDropListener) obj2;
                            switch (i7) {
                                case 0:
                                    int i62 = DragAndDropController.$r8$clinit;
                                    dragAndDropListener.onDragStarted();
                                    break;
                                default:
                                    int i72 = DragAndDropController.$r8$clinit;
                                    dragAndDropListener.getClass();
                                    break;
                            }
                            return Boolean.FALSE;
                        }
                    });
                    break;
                case 5:
                    ((DropTargetLayout) perDisplay.dragLayout).show();
                    break;
                case 6:
                    ((DropTargetLayout) perDisplay.dragLayout).hide(null, true);
                    break;
            }
            return false;
        }
        return false;
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

    public interface DragAndDropListener {
        default boolean onUnhandledDrag(PendingIntent pendingIntent, int i, DragEvent dragEvent, GlobalDragListener.AnonymousClass1 anonymousClass1) {
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
