package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TaskInfo;
import android.app.TaskStackListener;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.LauncherApps;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.Vibrator;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.view.GestureDetector;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowManagerGlobal;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.logging.InstanceId;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.launcher3.icons.IconProvider;
import com.android.server.LocalServices;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$$ExternalSyntheticOutline0;
import com.android.systemui.wmshell.WMShell;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda4;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.ComponentUtils;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.LaunchAdjacentController;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.split.CellUtil;
import com.android.wm.shell.common.split.DividerResizeController;
import com.android.wm.shell.common.split.DividerSnapAlgorithm;
import com.android.wm.shell.common.split.MultiSplitLayoutInfo;
import com.android.wm.shell.common.split.SplitLayout;
import com.android.wm.shell.common.split.SplitScreenUtils;
import com.android.wm.shell.common.split.SplitState;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.draganddrop.SplitDragPolicy;
import com.android.wm.shell.flexpanel.FlexPanelStartController;
import com.android.wm.shell.fullscreen.FullscreenTaskListener;
import com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.splitscreen.StageCoordinator.AnonymousClass2;
import com.android.wm.shell.sysui.KeyguardChangeListener;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class SplitScreenController implements SplitDragPolicy.Starter, RemoteCallable, KeyguardChangeListener {
    public final AnonymousClass1 mConnection;
    public final Context mContext;
    public final DesktopState mDesktopState;
    public final Optional mDesktopTasksController;
    public final DisplayController mDisplayController;
    public final DisplayImeController mDisplayImeController;
    public final DisplayInsetsController mDisplayInsetsController;
    public DividerResizeController mDividerResizeController;
    public final DragAndDropController mDragAndDropController;
    public FullscreenTaskListener mFullscreenTaskListener;
    public final Optional mGestureStarter;
    public final IconProvider mIconProvider;
    public WMShell$$ExternalSyntheticLambda4 mIsKeyguardOccludedAndShowingSupplier;
    public final LaunchAdjacentController mLaunchAdjacentController;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public final MultiInstanceHelper mMultiInstanceHelpher;
    public final NaturalSwitchingDropTargetController mNsController;
    public final Optional mRecentTasksOptional;
    public final RootTaskDisplayAreaOrganizer mRootTDAOrganizer;
    public SplitScreenProxyService mService;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public final SplitScreenShellCommandHandler mSplitScreenShellCommandHandler;
    public final SplitState mSplitState;
    StageCoordinator mStageCoordinator;
    public final SyncTransactionQueue mSyncQueue;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final TransactionPool mTransactionPool;
    public final Transitions mTransitions;
    public final Optional mWindowDecorViewModel;
    public final SplitScreenImpl mImpl = new SplitScreenImpl(this, 0);
    public int mFocusedTaskPosition = 0;

    public class CallerInfo {
        public final int mUid;

        public CallerInfo() {
            Binder.getCallingPid();
            this.mUid = Binder.getCallingUid();
        }
    }

    public class ISplitScreenImpl extends ISplitScreen$Stub implements ExternalInterfaceBinder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public SplitScreenController mController;
        public final SingleInstanceRemoteListener mListener;
        public final SingleInstanceRemoteListener mSelectListener;
        public final AnonymousClass1 mSplitScreenListener = new SplitScreen.SplitScreenListener() { // from class: com.android.wm.shell.splitscreen.SplitScreenController.ISplitScreenImpl.1
            @Override // com.android.wm.shell.splitscreen.SplitScreen.SplitScreenListener
            public final void onStagePositionChanged(int i, int i2) {
                IInterface iInterface = ISplitScreenImpl.this.mListener.mListener;
                if (iInterface == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    ISplitScreenListener$Stub$Proxy iSplitScreenListener$Stub$Proxy = (ISplitScreenListener$Stub$Proxy) iInterface;
                    Parcel parcelObtain = Parcel.obtain(iSplitScreenListener$Stub$Proxy.mRemote);
                    try {
                        parcelObtain.writeInterfaceToken("com.android.wm.shell.splitscreen.ISplitScreenListener");
                        parcelObtain.writeInt(i);
                        parcelObtain.writeInt(i2);
                        iSplitScreenListener$Stub$Proxy.mRemote.transact(1, parcelObtain, null, 1);
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain.recycle();
                        throw th;
                    }
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }

            @Override // com.android.wm.shell.splitscreen.SplitScreen.SplitScreenListener
            public final void onTaskStageChanged(int i, int i2, boolean z) {
                IInterface iInterface = ISplitScreenImpl.this.mListener.mListener;
                if (iInterface == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    ISplitScreenListener$Stub$Proxy iSplitScreenListener$Stub$Proxy = (ISplitScreenListener$Stub$Proxy) iInterface;
                    Parcel parcelObtain = Parcel.obtain(iSplitScreenListener$Stub$Proxy.mRemote);
                    try {
                        parcelObtain.writeInterfaceToken("com.android.wm.shell.splitscreen.ISplitScreenListener");
                        parcelObtain.writeInt(i);
                        parcelObtain.writeInt(i2);
                        parcelObtain.writeBoolean(z);
                        iSplitScreenListener$Stub$Proxy.mRemote.transact(2, parcelObtain, null, 1);
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain.recycle();
                        throw th;
                    }
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }
        };
        public final AnonymousClass2 mSplitSelectListener = new AnonymousClass2();

        /* renamed from: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$2, reason: invalid class name */
        public class AnonymousClass2 {
            public AnonymousClass2() {
            }
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$1] */
        public ISplitScreenImpl(SplitScreenController splitScreenController) {
            this.mController = splitScreenController;
            this.mListener = new SingleInstanceRemoteListener(splitScreenController, new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3(this, 0), new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3(this, 1));
            this.mSelectListener = new SingleInstanceRemoteListener(splitScreenController, new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3(this, 2), new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3(this, 3));
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
            this.mListener.unregister();
            this.mSelectListener.unregister();
        }
    }

    public class Impl extends TaskStackListener {
        public static final /* synthetic */ int $r8$clinit = 0;

        public Impl() {
        }

        public final void onTaskFocusChanged(int i, boolean z) {
            SplitScreenController.this.mMainExecutor.execute(new SplitScreenController$Impl$$ExternalSyntheticLambda0(this, z, i));
        }
    }

    public class SplitScreenImpl implements SplitScreen {
        public final ArrayMap mExecutors;
        public final AnonymousClass1 mListener;

        /* renamed from: com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$1, reason: invalid class name */
        public class AnonymousClass1 implements SplitScreen.SplitScreenListener {
            public AnonymousClass1() {
            }

            @Override // com.android.wm.shell.splitscreen.SplitScreen.SplitScreenListener
            public final void onSplitBoundsChanged(final Rect rect, final Rect rect2, final Rect rect3) {
                final int i = 0;
                while (true) {
                    SplitScreenImpl splitScreenImpl = SplitScreenImpl.this;
                    if (i >= splitScreenImpl.mExecutors.size()) {
                        return;
                    }
                    ((Executor) splitScreenImpl.mExecutors.valueAt(i)).execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SplitScreenController.SplitScreenImpl.AnonymousClass1 anonymousClass1 = this.f$0;
                            int i2 = i;
                            ((SplitScreen.SplitScreenListener) SplitScreenController.SplitScreenImpl.this.mExecutors.keyAt(i2)).onSplitBoundsChanged(rect, rect2, rect3);
                        }
                    });
                    i++;
                }
            }

            @Override // com.android.wm.shell.splitscreen.SplitScreen.SplitScreenListener
            public final void onSplitVisibilityChanged(boolean z) {
                int i = 0;
                while (true) {
                    SplitScreenImpl splitScreenImpl = SplitScreenImpl.this;
                    if (i >= splitScreenImpl.mExecutors.size()) {
                        return;
                    }
                    ((Executor) splitScreenImpl.mExecutors.valueAt(i)).execute(new SplitScreenController$Impl$$ExternalSyntheticLambda0(this, i, z));
                    i++;
                }
            }

            @Override // com.android.wm.shell.splitscreen.SplitScreen.SplitScreenListener
            public final void onStagePositionChanged(final int i, final int i2) {
                final int i3 = 0;
                while (true) {
                    SplitScreenImpl splitScreenImpl = SplitScreenImpl.this;
                    if (i3 >= splitScreenImpl.mExecutors.size()) {
                        return;
                    }
                    ((Executor) splitScreenImpl.mExecutors.valueAt(i3)).execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            SplitScreenController.SplitScreenImpl.AnonymousClass1 anonymousClass1 = this.f$0;
                            int i4 = i3;
                            ((SplitScreen.SplitScreenListener) SplitScreenController.SplitScreenImpl.this.mExecutors.keyAt(i4)).onStagePositionChanged(i, i2);
                        }
                    });
                    i3++;
                }
            }

            @Override // com.android.wm.shell.splitscreen.SplitScreen.SplitScreenListener
            public final void onTaskStageChanged(final int i, final int i2, final boolean z) {
                final int i3 = 0;
                while (true) {
                    SplitScreenImpl splitScreenImpl = SplitScreenImpl.this;
                    if (i3 >= splitScreenImpl.mExecutors.size()) {
                        return;
                    }
                    ((Executor) splitScreenImpl.mExecutors.valueAt(i3)).execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$1$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            SplitScreenController.SplitScreenImpl.AnonymousClass1 anonymousClass1 = this.f$0;
                            int i4 = i3;
                            ((SplitScreen.SplitScreenListener) SplitScreenController.SplitScreenImpl.this.mExecutors.keyAt(i4)).onTaskStageChanged(i, i2, z);
                        }
                    });
                    i3++;
                }
            }
        }

        public /* synthetic */ SplitScreenImpl(SplitScreenController splitScreenController, int i) {
            this();
        }

        public final void registerSplitAnimationListener(WMShell.AnonymousClass10 anonymousClass10, Executor executor) {
            StageCoordinator stageCoordinator = SplitScreenController.this.mStageCoordinator;
            stageCoordinator.mSplitInvocationListener = anonymousClass10;
            stageCoordinator.mSplitInvocationListenerExecutor = executor;
            SplitScreenTransitions splitScreenTransitions = stageCoordinator.mSplitTransitions;
            splitScreenTransitions.mSplitInvocationListener = anonymousClass10;
            splitScreenTransitions.mSplitInvocationListenerExecutor = executor;
        }

        private SplitScreenImpl() {
            this.mExecutors = new ArrayMap();
            this.mListener = new AnonymousClass1();
        }
    }

    public class SplitTwoFingerGestureStarter {
        public final ComponentName allAppsComponentName;
        public boolean mEnabled;
        public Vibrator mVibrator;
        public final String TAG = SplitTwoFingerGestureStarter.class.getSimpleName();
        public final boolean DEBUG = CoreRune.IS_DEBUG_LEVEL_MID;

        public SplitTwoFingerGestureStarter() {
            WindowManagerGlobal.getWindowSession();
            this.allAppsComponentName = MultiWindowUtils.getEdgeAllAppsComponent();
        }

        public static boolean intValueIn(int i, int... iArr) {
            for (int i2 : iArr) {
                if (i == i2) {
                    return true;
                }
            }
            return false;
        }

        public final void startSplitByTwoTouchSwipeIfPossible(int i, String str) {
            int i2;
            String str2 = this.TAG;
            boolean z = this.DEBUG;
            if (z) {
                Slog.d(str2, "startSplitByTwoTouchSwipeIfPossible: gestureFrom=" + i + " caller=" + str);
            }
            SplitScreenController splitScreenController = SplitScreenController.this;
            if (splitScreenController.mStageCoordinator == null) {
                Slog.w(str2, "  mStageCoordinator is null.");
                return;
            }
            if (!MultiWindowCoreState.MW_ENABLED) {
                Slog.w(str2, "  dynamic enabled=false");
                return;
            }
            if (!this.mEnabled) {
                if (z) {
                    Slog.w(str2, "  enabled=false");
                    return;
                }
                return;
            }
            if (!intValueIn(i, 1, 3, 4)) {
                if (z) {
                    Slog.w(str2, "  not allowed gestureFrom=" + i + " callSite=" + str);
                    return;
                }
                return;
            }
            boolean zIntValueIn = intValueIn(i, 1, 2);
            int i3 = !zIntValueIn ? 1 : 0;
            if (!CoreRune.MW_MULTI_SPLIT_FULL_TO_SPLIT_BY_GESTURE || MultiWindowUtils.isInSubDisplay(splitScreenController.mContext) || (!splitScreenController.mStageCoordinator.isSplitScreenVisible() && CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && !MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize())) {
                DisplayLayout displayLayout = splitScreenController.mDisplayController.getDisplayLayout(splitScreenController.mContext.getDisplayId());
                int i4 = displayLayout.mWidth > displayLayout.mHeight ? 2 : 1;
                if (!intValueIn(i4, 1, 2)) {
                    if (z) {
                        Slog.w(str2, "  dl has no orientation.");
                        return;
                    }
                    return;
                } else {
                    if (!(intValueIn(i, 2, 4) ^ (i4 == 2))) {
                        if (z) {
                            Slog.d(str2, "  gestureFrom is not fit.");
                            return;
                        }
                        return;
                    }
                    i2 = -1;
                }
            } else {
                if (splitScreenController.mStageCoordinator.isMultiSplitScreenVisible()) {
                    if (z) {
                        Slog.d(str2, "  inMultiSplit. skip.");
                        return;
                    }
                    return;
                }
                int splitDivision = splitScreenController.mStageCoordinator.getSplitDivision();
                if (splitScreenController.mStageCoordinator.isSplitScreenVisible()) {
                    if (!intValueIn(splitDivision, 0, 1)) {
                        if (z) {
                            Slog.w(str2, "  inSplit. but division is not set.");
                            return;
                        }
                        return;
                    } else {
                        if (!(intValueIn(i, 2, 4) ^ (splitDivision == 0))) {
                            if (z) {
                                Slog.d(str2, "  inSplit. gestureFrom is not fit in split.");
                                return;
                            }
                            return;
                        }
                    }
                }
                i2 = !intValueIn(i, 1, 3) ? 1 : 0;
            }
            if (z) {
                StringBuilder sb = new StringBuilder("enterSplitIfPossible: all apps position=");
                sb.append(i3 != -1 ? !zIntValueIn ? i3 != 1 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "unknown=") : "bottomOrRight" : "topOrLeft" : "undefined");
                Slog.d(str2, sb.toString());
            }
            List visibleTasks = MultiWindowManager.getInstance().getVisibleTasks();
            final int i5 = i3 != splitScreenController.mStageCoordinator.mSideStagePosition ? 2 : 1;
            if (!visibleTasks.stream().anyMatch(new Predicate() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$SplitTwoFingerGestureStarter$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
                    return this.f$0.allAppsComponentName.equals(runningTaskInfo.topActivity) && runningTaskInfo.isTopTaskInStage;
                }
            })) {
                visibleTasks.stream().filter(new Predicate() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$SplitTwoFingerGestureStarter$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        int i6 = i5;
                        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
                        int stageType = runningTaskInfo.configuration.windowConfiguration.getStageType();
                        if (stageType != 0) {
                            if (stageType != i6) {
                                return false;
                            }
                        } else if (runningTaskInfo.getWindowingMode() != 1) {
                            return false;
                        }
                        return true;
                    }
                }).findFirst().ifPresent(new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda20(i, this, i3, i2, 1));
            } else if (z) {
                Slog.d(str2, "startSplitByTwoTouchSwipeIfPossible: failed, reason=apps_edge_on_top");
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.content.ServiceConnection, com.android.wm.shell.splitscreen.SplitScreenController$1] */
    public SplitScreenController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DisplayController displayController, DisplayImeController displayImeController, DisplayInsetsController displayInsetsController, DragAndDropController dragAndDropController, Transitions transitions, TransactionPool transactionPool, IconProvider iconProvider, Optional<RecentTasksController> optional, LaunchAdjacentController launchAdjacentController, Optional<WindowDecorViewModel> optional2, Optional<DesktopTasksController> optional3, StageCoordinator stageCoordinator, MultiInstanceHelper multiInstanceHelper, SplitState splitState, ShellExecutor shellExecutor, Handler handler, DesktopState desktopState, NaturalSwitchingDropTargetController naturalSwitchingDropTargetController, FlexPanelStartController flexPanelStartController) {
        Impl impl = new Impl();
        ?? r2 = new ServiceConnection() { // from class: com.android.wm.shell.splitscreen.SplitScreenController.1
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Slog.d("SplitScreenProxyService", "onServiceConnected. inject splitController obj");
                SplitScreenProxyService splitScreenProxyService = (SplitScreenProxyService) LocalServices.getService(SplitScreenProxyService.class);
                if (splitScreenProxyService != null) {
                    SplitScreenController splitScreenController = SplitScreenController.this;
                    splitScreenProxyService.mSplitScreenController = splitScreenController;
                    splitScreenController.mService = splitScreenProxyService;
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
            }
        };
        this.mConnection = r2;
        new CopyOnWriteArrayList();
        this.mShellCommandHandler = shellCommandHandler;
        this.mShellController = shellController;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mSyncQueue = syncTransactionQueue;
        this.mContext = context;
        this.mRootTDAOrganizer = rootTaskDisplayAreaOrganizer;
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mDisplayController = displayController;
        this.mDisplayImeController = displayImeController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mDragAndDropController = dragAndDropController;
        this.mTransitions = transitions;
        this.mTransactionPool = transactionPool;
        this.mIconProvider = iconProvider;
        this.mRecentTasksOptional = optional;
        this.mLaunchAdjacentController = launchAdjacentController;
        this.mWindowDecorViewModel = optional2;
        this.mDesktopTasksController = optional3;
        this.mStageCoordinator = stageCoordinator;
        this.mMultiInstanceHelpher = multiInstanceHelper;
        this.mSplitState = splitState;
        this.mSplitScreenShellCommandHandler = new SplitScreenShellCommandHandler(this);
        this.mDesktopState = desktopState;
        if (ActivityTaskManager.deviceSupportsMultiWindow(context)) {
            shellInit.addInitCallback(new SplitScreenController$$ExternalSyntheticLambda7(this, 0), this);
        }
        context.bindService(new Intent(context, (Class<?>) SplitScreenProxyService.class), (ServiceConnection) r2, 1);
        shellTaskOrganizer.registerMultiWindowCoreStateListener(new ShellTaskOrganizer.MultiWindowCoreStateChangeListener() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$$ExternalSyntheticLambda8
            @Override // com.android.wm.shell.ShellTaskOrganizer.MultiWindowCoreStateChangeListener
            public final boolean onMultiWindowCoreStateChanged(int i) {
                SplitScreenController splitScreenController = this.f$0;
                StageCoordinator stageCoordinator2 = splitScreenController.mStageCoordinator;
                if (stageCoordinator2.mMainStage.mIsActive) {
                    if ((i & 1024) != 0 || (i & 512) != 0) {
                        boolean z = MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED || MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED;
                        Runnable runnable = stageCoordinator2.mDelayedHandleLayoutSizeChange;
                        HandlerExecutor handlerExecutor = (HandlerExecutor) stageCoordinator2.mMainExecutor;
                        if (handlerExecutor.mHandler.hasCallbacks(runnable)) {
                            handlerExecutor.removeCallbacks(stageCoordinator2.mDelayedHandleLayoutSizeChange);
                        }
                        handlerExecutor.executeDelayed(stageCoordinator2.mDelayedHandleLayoutSizeChange, z ? 0 : 300);
                    }
                    if ((i & 1) != 0 && !MultiWindowCoreState.MW_ENABLED) {
                        boolean zIsSplitScreenVisible = splitScreenController.mStageCoordinator.isSplitScreenVisible();
                        StageCoordinator stageCoordinator3 = splitScreenController.mStageCoordinator;
                        StageTaskListener stageTaskListener = stageCoordinator3.mSideStage;
                        if (!stageTaskListener.isFocused()) {
                            stageTaskListener = stageCoordinator3.mMainStage;
                        }
                        ActivityManager.RunningTaskInfo runningTaskInfo = stageTaskListener.mRootTaskInfo;
                        if (runningTaskInfo == null) {
                            Slog.e("SplitScreenController", "onMultiWindowCoreStateChanged: cannot find focusedInfo");
                            return zIsSplitScreenVisible;
                        }
                        if (splitScreenController.mFocusedTaskPosition == 0) {
                            Slog.d("SplitScreenController", "onMultiWindowCoreStateChanged: dismiss split");
                            splitScreenController.exitSplitScreen(-1, 1);
                            return zIsSplitScreenVisible;
                        }
                        Slog.d("SplitScreenController", "onMultiWindowCoreStateChanged: maximize split #" + runningTaskInfo.taskId);
                        splitScreenController.maximizeSplitTask(runningTaskInfo.token);
                        return zIsSplitScreenVisible;
                    }
                }
                return false;
            }
        });
        this.mGestureStarter = Optional.of(new SplitTwoFingerGestureStarter());
        transitions.registerObserver(new Transitions.TransitionObserver() { // from class: com.android.wm.shell.splitscreen.SplitScreenController.2
            @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
            public final void onTransitionFinished(IBinder iBinder, boolean z) {
                SplitScreenController splitScreenController;
                SplitScreenProxyService splitScreenProxyService = SplitScreenController.this.mService;
                if (splitScreenProxyService == null || (splitScreenController = splitScreenProxyService.mSplitScreenController) == null) {
                    return;
                }
                ((HandlerExecutor) splitScreenController.mMainExecutor).executeDelayed(new SplitScreenProxyService$$ExternalSyntheticLambda1(splitScreenProxyService, 0), 0L);
            }

            @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
            public final void onTransitionStarting(IBinder iBinder) {
            }

            @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
            public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
            }

            @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
            public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
            }
        });
        try {
            ActivityTaskManager.getService().registerTaskStackListener(impl);
        } catch (RemoteException unused) {
        }
        this.mNsController = naturalSwitchingDropTargetController;
    }

    public static String exitReasonToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN_EXIT";
            case 1:
                return "APP_DOES_NOT_SUPPORT_MULTIWINDOW";
            case 2:
                return "APP_FINISHED";
            case 3:
                return "DEVICE_FOLDED";
            case 4:
                return "DRAG_DIVIDER";
            case 5:
                return "RETURN_HOME";
            case 6:
                return "ROOT_TASK_VANISHED";
            case 7:
                return "SCREEN_LOCKED";
            case 8:
                return "SCREEN_LOCKED_SHOW_ON_TOP";
            case 9:
                return "CHILD_TASK_ENTER_PIP";
            case 10:
                return "RECREATE_SPLIT";
            case 11:
            case 14:
            default:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "unknown reason, reason int = ");
            case 12:
                return "DESKTOP_MODE";
            case 13:
                return "FULLSCREEN_REQUEST";
            case 15:
                return "FREEFORM_REQUEST";
        }
    }

    public static ActivityManager.RunningTaskInfo getToggleSplitScreenTarget() {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        Iterator it = ActivityTaskManager.getInstance().getTasks(99).iterator();
        while (true) {
            if (!it.hasNext()) {
                runningTaskInfo = null;
                break;
            }
            runningTaskInfo = (ActivityManager.RunningTaskInfo) it.next();
            if (runningTaskInfo.getWindowingMode() == 1) {
                break;
            }
        }
        if (runningTaskInfo != null && runningTaskInfo.originallySupportedMultiWindow && runningTaskInfo.getActivityType() == 1) {
            return runningTaskInfo;
        }
        return null;
    }

    public final int determineNewInstancePosition(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo.getWindowingMode() != 1) {
            if (this.mStageCoordinator.getSplitPosition(runningTaskInfo.taskId) != 0) {
                return 0;
            }
        }
        return 1;
    }

    public final void dismissAddToAppPairDialog() {
        AlertDialog alertDialog = this.mStageCoordinator.mSplitLayout.mSplitWindowManager.mDividerPanel.mAddToAppPairDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public final void dismissSplitTask(WindowContainerToken windowContainerToken) {
        this.mStageCoordinator.dismissSplitTask(windowContainerToken, null, false);
    }

    public final void enterSplitScreen$1() {
        ActivityManager.RunningTaskInfo toggleSplitScreenTarget;
        if (this.mStageCoordinator.isSplitScreenVisible() || (toggleSplitScreenTarget = getToggleSplitScreenTarget()) == null) {
            return;
        }
        startIntent(MultiWindowUtils.getEdgeAllAppsActivityIntent(toggleSplitScreenTarget.baseIntent.getComponent(), toggleSplitScreenTarget.userId, toggleSplitScreenTarget.taskId), null, 1, (!CoreRune.MW_MULTI_SPLIT_CREATE_MODE || MultiWindowUtils.isInSubDisplay(this.mContext)) ? -1 : 0, null);
    }

    public final void exitSplitScreen(int i, int i2) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        if (stageCoordinator.mMainStage.mIsActive) {
            int stageOfTask = stageCoordinator.getStageOfTask(i);
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            stageCoordinator.prepareExitSplitScreen(stageOfTask, i2, windowContainerTransaction, true);
            stageCoordinator.mSplitTransitions.startDismissTransition(windowContainerTransaction, stageCoordinator, stageOfTask, i2, false);
            stageCoordinator.setSideStagePosition$1(null, 1);
            stageCoordinator.logExit(i2);
        }
    }

    public final void finishEnterSplitScreen(SurfaceControl.Transaction transaction) {
        this.mStageCoordinator.finishEnterSplitScreen(transaction, false);
    }

    public final int getActivateSplitPosition(TaskInfo taskInfo) {
        return this.mStageCoordinator.getActivateSplitPosition(taskInfo);
    }

    public final void getAllStageBounds(Rect rect, Rect rect2, Rect rect3) {
        SplitLayout splitLayout = this.mStageCoordinator.mSplitLayout;
        if (CellUtil.isCellInLeftOrTopBounds(splitLayout.mCellStageWindowConfigPosition, splitLayout.isVerticalDivision())) {
            rect.set(splitLayout.getRefHostBounds());
            rect2.set(splitLayout.getBottomRightRefBounds());
        } else {
            rect2.set(splitLayout.getTopLeftRefBounds());
            rect.set(splitLayout.getRefHostBounds());
        }
        Rect bounds3 = splitLayout.getBounds3();
        Rect rect4 = splitLayout.mRootBounds;
        bounds3.offset(-rect4.left, -rect4.top);
        rect3.set(bounds3);
    }

    public final Rect getCellDividerBounds() {
        SplitLayout splitLayout = this.mStageCoordinator.mSplitLayout;
        splitLayout.getClass();
        return new Rect(splitLayout.mCellDividerBounds);
    }

    public final int getCellHostStageType() {
        return this.mStageCoordinator.getCellHostStageType();
    }

    public final int getCellStageWindowConfigPosition() {
        return this.mStageCoordinator.mCellStageWindowConfigPosition;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    public final Rect getDividerBounds() {
        SplitLayout splitLayout = this.mStageCoordinator.mSplitLayout;
        splitLayout.getClass();
        return new Rect(splitLayout.mDividerBounds);
    }

    public final int getFocusedStageTaskIdWithoutAppsEdgeActivity() {
        ComponentName componentName;
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        StageTaskListener stageTaskListener = stageCoordinator.mSideStage;
        if (!stageTaskListener.isFocused()) {
            stageTaskListener = stageCoordinator.mMainStage;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = stageTaskListener.mRootTaskInfo;
        if (runningTaskInfo == null || (componentName = runningTaskInfo.topActivity) == null) {
            Slog.d("SplitScreenController", "focusStageRootTaskInfo is null, " + runningTaskInfo);
            return -1;
        }
        if (MultiWindowUtils.isAppsEdgeActivity(componentName)) {
            return this.mStageCoordinator.getTaskIdByStageType(stageTaskListener.mStageType == 0 ? 1 : 0);
        }
        StageCoordinator stageCoordinator2 = this.mStageCoordinator;
        return stageCoordinator2.getTaskIdByStageType(stageCoordinator2.getFocusedStageType());
    }

    public final int getMainStagePositionExt() {
        return this.mStageCoordinator.getMainStageWinConfigPosition();
    }

    public final ActivityManager.RunningTaskInfo getMainStageRootTaskInfo() {
        return this.mStageCoordinator.mMainStage.mRootTaskInfo;
    }

    public final String getPackageName(int i, WindowContainerToken windowContainerToken) {
        ActivityManager.RunningTaskInfo taskInfo;
        if (!this.mStageCoordinator.isSplitScreenVisible()) {
            taskInfo = (ActivityManager.RunningTaskInfo) this.mRecentTasksOptional.map(new SplitScreenController$$ExternalSyntheticLambda5(0, windowContainerToken)).orElse(null);
            if (SplitScreenUtils.isValidToSplit(taskInfo)) {
            }
            return null;
        }
        taskInfo = getTaskInfo(i);
        if (taskInfo != null) {
            return ComponentUtils.getPackageName(taskInfo.baseIntent);
        }
        return null;
    }

    public final void getRefStageBounds(Rect rect, Rect rect2) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        rect.set(stageCoordinator.mSplitLayout.getTopLeftRefBounds());
        rect2.set(stageCoordinator.mSplitLayout.getBottomRightRefBounds());
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    public final int getSideStagePosition() {
        return this.mStageCoordinator.mSideStagePosition;
    }

    public final int getSplitCreateMode() {
        return this.mStageCoordinator.getSplitCreateMode();
    }

    public final int getSplitDivision() {
        return this.mStageCoordinator.getSplitDivision();
    }

    public final int getSplitPosition(int i) {
        return this.mStageCoordinator.getSplitPosition(i);
    }

    public final void getStageBounds(Rect rect, Rect rect2) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        if (!stageCoordinator.isSplitScreenVisible()) {
            stageCoordinator.mSplitLayout.getInitBounds(rect, rect2);
        } else {
            rect.set(stageCoordinator.mSplitLayout.getTopLeftBounds());
            rect2.set(stageCoordinator.mSplitLayout.getBottomRightBounds());
        }
    }

    public final int getStageOfTask(int i) {
        return this.mStageCoordinator.getStageOfTask(i);
    }

    public final int getStageTypeAtPosition(int i) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        StageTaskListener stageAtPosition = stageCoordinator.getStageAtPosition(i);
        if (stageAtPosition == stageCoordinator.mMainStage) {
            return 0;
        }
        if (stageAtPosition == stageCoordinator.mSideStage) {
            return 1;
        }
        return (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageAtPosition == stageCoordinator.mCellStage) ? 5 : 0;
    }

    public final SurfaceControl getTargetLeash(int i) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        stageCoordinator.getClass();
        return (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && i == 5) ? stageCoordinator.mCellStage.mRootLeash : i == 0 ? stageCoordinator.mMainStage.mRootLeash : stageCoordinator.mSideStage.mRootLeash;
    }

    public final ActivityManager.RunningTaskInfo getTaskInfo(int i) {
        if (!this.mStageCoordinator.isSplitScreenVisible()) {
            return null;
        }
        int topVisibleChildTaskId = -1;
        if (i == -1) {
            return null;
        }
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        if (i == -1) {
            stageCoordinator.getClass();
        } else {
            topVisibleChildTaskId = stageCoordinator.mSideStagePosition == i ? stageCoordinator.mSideStage.getTopVisibleChildTaskId() : stageCoordinator.mMainStage.getTopVisibleChildTaskId();
        }
        return this.mTaskOrganizer.getRunningTaskInfo(topVisibleChildTaskId);
    }

    public final ActivityManager.RunningTaskInfo getTopRunningTaskInfoByPosition(int i) {
        StageTaskListener stageAtPosition = this.mStageCoordinator.getStageAtPosition(i);
        if (stageAtPosition != null) {
            return stageAtPosition.getTopRunningTaskInfo();
        }
        return null;
    }

    public final StageCoordinator getTransitionHandler() {
        return this.mStageCoordinator;
    }

    public final int getUserId(int i, WindowContainerToken windowContainerToken) {
        ActivityManager.RunningTaskInfo taskInfo;
        if (this.mStageCoordinator.isSplitScreenVisible()) {
            taskInfo = getTaskInfo(i);
        } else {
            taskInfo = (ActivityManager.RunningTaskInfo) this.mRecentTasksOptional.map(new SplitScreenController$$ExternalSyntheticLambda5(1, windowContainerToken)).orElse(null);
            if (!SplitScreenUtils.isValidToSplit(taskInfo)) {
                return -1;
            }
        }
        if (taskInfo != null) {
            return taskInfo.userId;
        }
        return -1;
    }

    public final void invertDividerPosition(WindowContainerTransaction windowContainerTransaction) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        SplitLayout splitLayout = stageCoordinator.mSplitLayout;
        int i = splitLayout.mDividerPosition;
        DividerSnapAlgorithm dividerSnapAlgorithm = splitLayout.getDividerSnapAlgorithm();
        int i2 = dividerSnapAlgorithm.mFirstSplitTarget.position;
        int i3 = dividerSnapAlgorithm.mLastSplitTarget.position;
        if (i2 <= i && i3 >= i) {
            i = (i2 + i3) - i;
        }
        stageCoordinator.mSplitLayout.updateSnapAlgorithm(i);
        stageCoordinator.mSplitLayout.setDividePosition(stageCoordinator.mSplitLayout.getDividerSnapAlgorithm().calculateSnapTarget(i, true).position, windowContainerTransaction, true);
    }

    public final boolean isLaunchToSplit(TaskInfo taskInfo) {
        return this.mStageCoordinator.getActivateSplitPosition(taskInfo) != -1;
    }

    public final boolean isLeftRightSplit() {
        SplitLayout splitLayout = this.mStageCoordinator.mSplitLayout;
        return splitLayout != null && splitLayout.mIsLeftRightSplit;
    }

    public final boolean isMultiSplitScreenVisible() {
        return this.mStageCoordinator.isMultiSplitScreenVisible();
    }

    public final boolean isParallelMultiSplit() {
        return this.mStageCoordinator.mSplitLayout.mParallelMultiSplit;
    }

    public final boolean isSplitScreenActive() {
        return this.mStageCoordinator.mMainStage.mIsActive;
    }

    public final boolean isSplitScreenFeasible(boolean z) {
        SplitLayout splitLayout = this.mStageCoordinator.mSplitLayout;
        return splitLayout != null && splitLayout.isSplitScreenFeasible(z);
    }

    public final boolean isSplitScreenVisible() {
        return this.mStageCoordinator.isSplitScreenVisible();
    }

    public final boolean isTaskInSplitScreen$1(int i) {
        return this.mStageCoordinator.getStageOfTask(i) != -1;
    }

    public final boolean isTaskRoot(int i) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        return (stageCoordinator == null || (runningTaskInfo = stageCoordinator.mRootTaskInfo) == null || runningTaskInfo.taskId != i) ? false : true;
    }

    public final boolean isTaskRootOrStageRoot(int i) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = stageCoordinator.mRootTaskInfo;
        if (runningTaskInfo2 != null && runningTaskInfo2.taskId == i) {
            return true;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo3 = stageCoordinator.mMainStage.mRootTaskInfo;
        if (runningTaskInfo3 != null && runningTaskInfo3.taskId == i) {
            return true;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo4 = stageCoordinator.mSideStage.mRootTaskInfo;
        if (runningTaskInfo4 == null || runningTaskInfo4.taskId != i) {
            return CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && (runningTaskInfo = stageCoordinator.mCellStage.mRootTaskInfo) != null && runningTaskInfo.taskId == i;
        }
        return true;
    }

    public final boolean isVerticalDivision() {
        return this.mStageCoordinator.isVerticalDivision();
    }

    public final void maximizeSplitTask(WindowContainerToken windowContainerToken) {
        this.mStageCoordinator.maximizeSplitTask(windowContainerToken, null);
    }

    public final void moveSplitToFreeform(WindowContainerToken windowContainerToken, Rect rect, boolean z) {
        this.mStageCoordinator.moveSplitToFreeform(windowContainerToken, rect, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void moveTaskToFullscreen(int i) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        stageCoordinator.getClass();
        boolean z = false;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -4495304678011028405L, 0, null);
        }
        if (stageCoordinator.mMainStage.mChildrenTaskInfo.contains(i)) {
            if (stageCoordinator.mSideStagePosition == 0) {
                z = true;
            }
        } else if (!stageCoordinator.mSideStage.mChildrenTaskInfo.contains(i)) {
            return;
        } else {
            if (stageCoordinator.mSideStagePosition == 1) {
            }
        }
        stageCoordinator.mSplitLayout.flingDividerToDismiss(12, !z);
    }

    public final void moveToStage(int i, int i2, WindowContainerTransaction windowContainerTransaction) {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskOrganizer.getRunningTaskInfo(i);
        if (runningTaskInfo == null) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown taskId"));
        }
        if (isTaskInSplitScreen$1(i)) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "taskId is in split"));
        }
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        stageCoordinator.getClass();
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1563569262687696354L, 5, Long.valueOf(runningTaskInfo.taskId), Long.valueOf(i2));
        }
        stageCoordinator.prepareEnterSplitScreen(windowContainerTransaction, runningTaskInfo, i2, false);
        stageCoordinator.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, stageCoordinator, stageCoordinator.isSplitScreenVisible() ? 1005 : VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI, !stageCoordinator.mIsDropEntering, 1);
        stageCoordinator.mIsDropEntering = false;
        stageCoordinator.mSkipEvictingMainStageChildren = false;
    }

    public final void onDroppedToSplit(int i, InstanceId instanceId) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        stageCoordinator.getClass();
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 3250015241683482964L, 1, Long.valueOf(i));
        }
        if (!stageCoordinator.isSplitScreenVisible()) {
            stageCoordinator.mIsDropEntering = true;
            stageCoordinator.mSkipEvictingMainStageChildren = true;
        }
        SplitscreenEventLogger splitscreenEventLogger = stageCoordinator.mLogger;
        splitscreenEventLogger.mDragEnterPosition = i;
        splitscreenEventLogger.mEnterSessionId = instanceId;
        splitscreenEventLogger.mEnterReason = 2;
    }

    public final void onFreeformToSplitRequested(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, int i, boolean z2, Rect rect, boolean z3) {
        this.mStageCoordinator.onFreeformToSplitRequested(runningTaskInfo, z, i, z2, rect, z3, "freeform_to_split");
    }

    public void onInit() {
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SplitScreenController splitScreenController = this.f$0;
                PrintWriter printWriter = (PrintWriter) obj;
                String str = (String) obj2;
                QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "SplitScreenController");
                StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                if (stageCoordinator != null) {
                    stageCoordinator.dump$2(printWriter, str);
                }
            }
        };
        ShellCommandHandler shellCommandHandler = this.mShellCommandHandler;
        shellCommandHandler.addDumpCallback(biConsumer, this);
        shellCommandHandler.addCommandCallback("splitscreen", this.mSplitScreenShellCommandHandler, this);
        ShellController shellController = this.mShellController;
        shellController.addKeyguardChangeListener(this);
        shellController.addExternalInterface("com.android.wm.shell.splitscreen.ISplitScreen", new Supplier() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                SplitScreenController splitScreenController = this.f$0;
                splitScreenController.getClass();
                return new SplitScreenController.ISplitScreenImpl(splitScreenController);
            }
        }, this);
        if (this.mStageCoordinator == null) {
            this.mStageCoordinator = new StageCoordinator(this.mContext, 0, this.mSyncQueue, this.mTaskOrganizer, this.mDisplayController, this.mDisplayImeController, this.mDisplayInsetsController, this.mTransitions, this.mTransactionPool, this.mIconProvider, this.mMainExecutor, this.mMainHandler, this.mRecentTasksOptional, this.mLaunchAdjacentController, this.mWindowDecorViewModel, this.mSplitState, this.mDesktopTasksController, this.mRootTDAOrganizer, this.mDesktopState);
            DividerResizeController dividerResizeController = new DividerResizeController(this.mContext, this.mMainExecutor);
            this.mDividerResizeController = dividerResizeController;
            StageCoordinator stageCoordinator = this.mStageCoordinator;
            stageCoordinator.mDividerResizeController = dividerResizeController;
            dividerResizeController.mStageCoordinator = stageCoordinator;
        }
        DragAndDropController dragAndDropController = this.mDragAndDropController;
        if (dragAndDropController != null) {
            dragAndDropController.mSplitScreen = this;
        }
        final NaturalSwitchingDropTargetController naturalSwitchingDropTargetController = this.mNsController;
        if (naturalSwitchingDropTargetController != null) {
            naturalSwitchingDropTargetController.mMainHandler.post(new Runnable() { // from class: com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NaturalSwitchingDropTargetController naturalSwitchingDropTargetController2 = naturalSwitchingDropTargetController;
                    SplitScreenController splitScreenController = this;
                    naturalSwitchingDropTargetController2.getClass();
                    naturalSwitchingDropTargetController2.mGestureDetector = new GestureDetector(naturalSwitchingDropTargetController2.mContext, naturalSwitchingDropTargetController2);
                    naturalSwitchingDropTargetController2.mNaturalSwitchingLayout = new NaturalSwitchingLayout(naturalSwitchingDropTargetController2.mContext, splitScreenController, naturalSwitchingDropTargetController2.mTransitions, naturalSwitchingDropTargetController2.mTaskOrganizer, naturalSwitchingDropTargetController2.mSyncQueue, naturalSwitchingDropTargetController2.mBackgroundExecutor);
                }
            });
        }
        final int i = 0;
        this.mWindowDecorViewModel.ifPresent(new Consumer(this) { // from class: com.android.wm.shell.splitscreen.SplitScreenController$$ExternalSyntheticLambda3
            public final /* synthetic */ SplitScreenController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                SplitScreenController splitScreenController = this.f$0;
                switch (i2) {
                    case 0:
                        ((WindowDecorViewModel) obj).setSplitScreenController(splitScreenController);
                        break;
                    default:
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        splitScreenController.getClass();
                        desktopTasksController.splitScreenController = splitScreenController;
                        desktopTasksController.dragToDesktopTransitionHandler.splitScreenController = splitScreenController;
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mDesktopTasksController.ifPresent(new Consumer(this) { // from class: com.android.wm.shell.splitscreen.SplitScreenController$$ExternalSyntheticLambda3
            public final /* synthetic */ SplitScreenController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i2;
                SplitScreenController splitScreenController = this.f$0;
                switch (i22) {
                    case 0:
                        ((WindowDecorViewModel) obj).setSplitScreenController(splitScreenController);
                        break;
                    default:
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        splitScreenController.getClass();
                        desktopTasksController.splitScreenController = splitScreenController;
                        desktopTasksController.dragToDesktopTransitionHandler.splitScreenController = splitScreenController;
                        break;
                }
            }
        });
    }

    @Override // com.android.wm.shell.sysui.KeyguardChangeListener
    public final void onKeyguardVisibilityChanged(boolean z, boolean z2, boolean z3) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        boolean z4 = stageCoordinator.mKeyguardActive;
        stageCoordinator.mKeyguardActive = z;
        if (!stageCoordinator.mMainStage.mIsActive) {
            if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
                stageCoordinator.updateSplitDivisionIfNeeded();
            }
            if (z4 != z) {
                int i = stageCoordinator.mContext.getResources().getConfiguration().orientation;
                if (!z && i != stageCoordinator.mOrientation) {
                    stageCoordinator.handleLayoutSizeChange(stageCoordinator.mSplitLayout, false);
                }
                stageCoordinator.mOrientation = i;
                return;
            }
            return;
        }
        boolean z5 = CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY;
        if (z5 && !z && stageCoordinator.isApplyFoldingPolicy(false)) {
            stageCoordinator.mTopStageAfterFold = -1;
            if (CoreRune.MW_SPLIT_CONTINUITY_MODE && stageCoordinator.mFoldLockSettingsObserver.isSelectiveStayAwake()) {
                if (z4 && stageCoordinator.isMultiSplitScreenVisible()) {
                    stageCoordinator.updateCoverDisplaySplitLayoutIfNeeded();
                    stageCoordinator.setSplitsVisible(false);
                }
                Log.d("StageCoordinator", "cover display already updated in selectiveAwake");
            } else {
                if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
                    stageCoordinator.updateSplitDivisionIfNeeded();
                }
                if (stageCoordinator.updateCoverDisplaySplitLayoutIfNeeded()) {
                    if (z4) {
                        if (stageCoordinator.isMultiSplitScreenVisible()) {
                            Log.d("StageCoordinator", "in fold state, cannot visible multi split state");
                            stageCoordinator.setSplitsVisible(false);
                        } else if (stageCoordinator.isSplitScreenVisible() && stageCoordinator.mFoldLockSettingsObserver.isSleepOnFold()) {
                            Log.d("StageCoordinator", "if is sleep on fold state, split visible should be false when keyguard is released.");
                            stageCoordinator.setSplitsVisible(false);
                        }
                    }
                    stageCoordinator.mSplitLayout.update(null, true);
                    stageCoordinator.onLayoutSizeChanged(stageCoordinator.mSplitLayout, null);
                }
            }
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -468740076483298004L, 15, Boolean.valueOf(z), Boolean.valueOf(z2));
        }
        if (z5 && stageCoordinator.isSplitScreenVisible()) {
            stageCoordinator.mSplitBackgroundController.setSplitsVisible(!stageCoordinator.mKeyguardActive, false);
        }
        if (!z5 || stageCoordinator.isSplitScreenVisible()) {
            stageCoordinator.setDividerVisibility(null, !stageCoordinator.mKeyguardActive);
        } else {
            stageCoordinator.setDividerVisibility(null, false);
        }
    }

    public final void onPipExpandToSplit(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        stageCoordinator.getClass();
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 241889366381180208L, 0, String.valueOf(runningTaskInfo));
        }
        stageCoordinator.prepareEnterSplitScreen(windowContainerTransaction, runningTaskInfo, stageCoordinator.getActivateSplitPosition(runningTaskInfo), false);
        if (!stageCoordinator.isSplitScreenVisible() || stageCoordinator.mSplitRequest == null) {
            return;
        }
        (SplitScreenUtils.reverseSplitPosition(stageCoordinator.mSideStagePosition) == stageCoordinator.mSplitRequest.mActivatePosition ? stageCoordinator.mMainStage : stageCoordinator.mSideStage).evictOtherChildren(windowContainerTransaction, runningTaskInfo.taskId);
    }

    public final void onPipToSplitRequested(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, int i, boolean z2, Rect rect, boolean z3) {
        this.mStageCoordinator.onFreeformToSplitRequested(runningTaskInfo, z, i, z2, rect, z3, "pip_to_split");
    }

    public final void openInSplitWithAllApps(int i, Intent intent, UserHandle userHandle) {
        int i2 = (!CoreRune.MW_MULTI_SPLIT_FREE_POSITION || MultiWindowUtils.isInSubDisplay(this.mContext)) ? -1 : 0;
        if (i != -1) {
            this.mStageCoordinator.startTaskWithAllApps(i, new CallerInfo(), i2);
            return;
        }
        if (userHandle == null) {
            userHandle = UserHandle.CURRENT;
        }
        UserHandle userHandle2 = userHandle;
        startPendingIntentAndIntent(PendingIntent.getActivityAsUser(this.mContext, 0, intent, 1107296256, null, userHandle2), MultiWindowUtils.getEdgeAllAppsActivityIntent(intent.getComponent(), userHandle2.getIdentifier(), -1), 1, i2);
    }

    public final void prepareEnterSplitScreen(WindowContainerTransaction windowContainerTransaction) {
        this.mStageCoordinator.prepareEnterSplitScreen(windowContainerTransaction, null, -1, false);
    }

    public final void prepareExitSplitScreen(int i, int i2, WindowContainerTransaction windowContainerTransaction) {
        this.mStageCoordinator.prepareExitSplitScreen(i, i2, windowContainerTransaction, true);
        this.mStageCoordinator.clearSplitPairedInRecents(i2);
    }

    public final IBinder requestEnterSplitSelect(int i, ActivityManager.RunningTaskInfo runningTaskInfo, Rect rect, WindowContainerTransaction windowContainerTransaction) {
        return this.mStageCoordinator.requestEnterSplitSelect(i, runningTaskInfo, rect, windowContainerTransaction);
    }

    public final boolean rotateMultiSplitWithTransition() {
        return this.mStageCoordinator.rotateMultiSplitWithTransition();
    }

    public final void setDividerVisibilityFromNS(boolean z) {
        this.mStageCoordinator.setDividerVisibility(null, z);
    }

    public final void setReparentLeafTaskIfRelaunch() {
        if (this.mStageCoordinator.mRootTaskInfo == null) {
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setReparentLeafTaskIfRelaunch(this.mStageCoordinator.mRootTaskInfo.token, true);
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        Slog.d("SplitScreenController", "setReparentLeafTaskIfRelaunch: true, reason=exit_pip");
    }

    public final void setSideStagePosition(WindowContainerTransaction windowContainerTransaction, int i) {
        this.mStageCoordinator.setSideStagePosition(i, -1, windowContainerTransaction, true);
    }

    public final void setSplitInvisible() {
        this.mStageCoordinator.setSplitsVisible$1(false, true);
    }

    public final void setSplitVisible() {
        this.mStageCoordinator.setSplitsVisible(true);
    }

    public final void shiftStageTasks(WindowContainerTransaction windowContainerTransaction, int i, int i2, int i3) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        StageTaskListener stageTaskListenerByStageType = stageCoordinator.getStageTaskListenerByStageType(i);
        StageTaskListener stageTaskListenerByStageType2 = stageCoordinator.getStageTaskListenerByStageType(i2);
        StageTaskListener stageTaskListenerByStageType3 = stageCoordinator.getStageTaskListenerByStageType(i3);
        if (stageTaskListenerByStageType == null || stageTaskListenerByStageType2 == null || stageTaskListenerByStageType3 == null) {
            Slog.w("StageCoordinator", "Cannot swapStageTasks, stage1=" + stageTaskListenerByStageType + ",stage2=" + stageTaskListenerByStageType2 + ",stage3=" + stageTaskListenerByStageType3 + ",callers=" + Debug.getCallers(3));
        } else {
            stageTaskListenerByStageType.reparentAllChildren(stageTaskListenerByStageType2.mRootTaskInfo.token, windowContainerTransaction);
            stageTaskListenerByStageType2.reparentAllChildren(stageTaskListenerByStageType3.mRootTaskInfo.token, windowContainerTransaction);
            stageTaskListenerByStageType3.reparentAllChildren(stageTaskListenerByStageType.mRootTaskInfo.token, windowContainerTransaction);
        }
        this.mStageCoordinator.mIsStageTasksChanged = true;
    }

    @Override // com.android.wm.shell.draganddrop.SplitDragPolicy.Starter
    public final void startDragAndSplit(Intent intent, int i, int i2, Bundle bundle, int i3, int i4, int i5, boolean z) {
        startIntent(PendingIntent.getActivityAsUser(this.mContext, 0, intent, 1107296256, null, new UserHandle(i3)), i3, null, i, bundle, null, true, i2, i4, i5, z);
    }

    public final void startIntent(Intent intent, UserHandle userHandle, int i, int i2, Bundle bundle) {
        UserHandle userHandle2 = userHandle == null ? UserHandle.CURRENT : userHandle;
        Slog.d("SplitScreenController", "startIntent: position=" + i);
        PendingIntent activityAsUser = PendingIntent.getActivityAsUser(this.mContext, 0, intent, 1107296256, null, userHandle2);
        if ((this.mStageCoordinator.mMainStage.mIsActive || getToggleSplitScreenTarget() != null) && !this.mSplitState.isSplitStashed()) {
            startIntent(activityAsUser, userHandle2.getIdentifier(), intent, i, bundle, null, false, -1, i2, 0, false);
            return;
        }
        try {
            activityAsUser.send(this.mContext, 0, null, null, null, null, bundle);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public final void startIntentToCell(PendingIntent pendingIntent, Intent intent, UserHandle userHandle, int i, boolean z) {
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && this.mStageCoordinator.isSplitScreenVisible()) {
            Intent intent2 = new Intent();
            intent2.addFlags(262144);
            ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
            if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && this.mStageCoordinator.isMultiSplitActive()) {
                activityOptionsMakeBasic.setResumedAffordanceAnimation();
            }
            this.mStageCoordinator.startIntentToCell(pendingIntent, intent, intent2, userHandle, i, z, activityOptionsMakeBasic.toBundle());
        }
    }

    public final void startIntents(Intent intent, Intent intent2, UserHandle userHandle, UserHandle userHandle2, int i, float f, int i2, RemoteTransition remoteTransition) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        stageCoordinator.getClass();
        stageCoordinator.startSplitScreen(-1, null, intent, intent2, null, userHandle == null ? UserHandle.CURRENT : userHandle, userHandle2 == null ? UserHandle.CURRENT : userHandle2, null, i, 0, f, 0.0f, 1, i2, false, null, remoteTransition);
    }

    public final void startPendingIntentAndIntent(PendingIntent pendingIntent, Intent intent, int i, int i2) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        stageCoordinator.getClass();
        stageCoordinator.startSplitScreen(-1, pendingIntent, null, intent, null, null, UserHandle.CURRENT, null, i, 0, 0.5f, 0.0f, 2, i2, false, null, null);
    }

    @Override // com.android.wm.shell.draganddrop.SplitDragPolicy.Starter
    public final void startShortcut(String str, String str2, int i, Bundle bundle, UserHandle userHandle) {
        if (CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET) {
            this.mStageCoordinator.isSplitScreenVisible();
        }
        Bundle bundleResolveStartStage = this.mStageCoordinator.resolveStartStage(-1, i, bundle, null, -1);
        if (bundleResolveStartStage == null) {
            bundleResolveStartStage = new Bundle();
        }
        ActivityOptions activityOptionsFromBundle = ActivityOptions.fromBundle(bundleResolveStartStage);
        MultiInstanceHelper.samePackage(userHandle.getIdentifier(), getUserId(SplitScreenUtils.reverseSplitPosition(i), null), str, getPackageName(SplitScreenUtils.reverseSplitPosition(i), null));
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        Bundle bundle2 = activityOptionsFromBundle.toBundle();
        stageCoordinator.getClass();
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -5167067216468757684L, 80, String.valueOf(str), String.valueOf(str2), Long.valueOf(i), Long.valueOf(userHandle.getIdentifier()));
        }
        StageCoordinator.AnonymousClass2 anonymousClass2 = stageCoordinator.new AnonymousClass2(!stageCoordinator.mMainStage.mIsActive, i);
        Bundle bundleResolveStartStage2 = stageCoordinator.resolveStartStage(-1, i, bundle2, null, -1);
        RemoteAnimationAdapter remoteAnimationAdapter = new RemoteAnimationAdapter(anonymousClass2, 0L, 0L);
        ActivityOptions activityOptionsFromBundle2 = ActivityOptions.fromBundle(bundleResolveStartStage2);
        activityOptionsFromBundle2.setApplyNoUserActionFlagForShortcut(true);
        activityOptionsFromBundle2.update(ActivityOptions.makeRemoteAnimation(remoteAnimationAdapter));
        try {
            ((LauncherApps) stageCoordinator.mContext.getSystemService(LauncherApps.class)).startShortcut(str, str2, null, activityOptionsFromBundle2.toBundle(), userHandle);
        } catch (ActivityNotFoundException e) {
            Slog.e("StageCoordinator", "Failed to launch shortcut", e);
        }
    }

    public final void startSplitTasks(int i, int i2, int i3, boolean z, int i4, float f, float f2, boolean z2) {
        try {
            this.mStageCoordinator.startSplitTasks(i, i2, i3, z, i4, f, f2, z2);
        } catch (Exception e) {
            Slog.e("SplitScreenController", "Failed to launch tasks", e);
        }
    }

    @Override // com.android.wm.shell.draganddrop.SplitDragPolicy.Starter
    public final void startTask(int i, int i2, Bundle bundle, WindowContainerToken windowContainerToken) {
        startTask(i, i2, bundle, windowContainerToken, -1, 0, false, false);
    }

    public final void startTaskAndIntent(int i, Intent intent, int i2, int i3) {
        this.mStageCoordinator.startTaskAndIntent(i, intent, i2, i3, null);
    }

    public final void startTasks(int i, Bundle bundle, int i2, Bundle bundle2, int i3, float f, RemoteTransition remoteTransition) {
        try {
            this.mStageCoordinator.startTasks(i, bundle, i2, bundle2, -1, null, i3, 8, f, 0, 0.5f, remoteTransition, null, -1, false, null);
        } catch (Exception e) {
            Slog.e("SplitScreenController", "Failed to launch tasks", e);
        }
    }

    public final void swapStageTasks(int i, int i2, WindowContainerTransaction windowContainerTransaction) {
        this.mStageCoordinator.swapStageTasks(i, i2, windowContainerTransaction);
        this.mStageCoordinator.mIsStageTasksChanged = true;
    }

    public final void swapTasksInSplitScreenMode() {
        this.mStageCoordinator.swapTasksInSplitScreenMode$1();
    }

    public final void toggleSplitScreen(int i) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        if (stageCoordinator.mMainStage.mIsActive && stageCoordinator.isSplitScreenVisible()) {
            exitSplitScreen(getFocusedStageTaskIdWithoutAppsEdgeActivity(), 14);
            return;
        }
        ActivityManager.RunningTaskInfo toggleSplitScreenTarget = getToggleSplitScreenTarget();
        if (toggleSplitScreenTarget == null) {
            return;
        }
        Intent edgeAllAppsActivityIntent = MultiWindowUtils.getEdgeAllAppsActivityIntent(toggleSplitScreenTarget.baseIntent.getComponent(), toggleSplitScreenTarget.userId, toggleSplitScreenTarget.taskId);
        int i2 = (!CoreRune.MW_MULTI_SPLIT_CREATE_MODE || MultiWindowUtils.isInSubDisplay(this.mContext)) ? -1 : 0;
        StageCoordinator stageCoordinator2 = this.mStageCoordinator;
        startIntent(edgeAllAppsActivityIntent, null, (stageCoordinator2.isLandscape() && stageCoordinator2.mSplitLayout.mRotation == 3 && !CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) ? 0 : i, i2, null);
    }

    public final void updateMultiSplitLayout(MultiSplitLayoutInfo multiSplitLayoutInfo, boolean z, WindowContainerTransaction windowContainerTransaction) {
        this.mStageCoordinator.updateMultiSplitLayout(multiSplitLayoutInfo, true, windowContainerTransaction);
    }

    public final void updateSplitScreenSurfaces(SurfaceControl.Transaction transaction) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        stageCoordinator.updateSurfaceBounds(stageCoordinator.mSplitLayout, transaction, false);
        stageCoordinator.mSplitLayout.update(transaction, true);
    }

    public final void updateSurfaceBoundsForNS(SurfaceControl.Transaction transaction) {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        stageCoordinator.updateSurfaceBounds(stageCoordinator.mSplitLayout, transaction, false);
    }

    public final void startTask(int i, int i2, Bundle bundle) {
        this.mStageCoordinator.startTask(i, i2, bundle, null, -1, -1);
    }

    @Override // com.android.wm.shell.draganddrop.SplitDragPolicy.Starter
    public final void startTask(int i, final int i2, Bundle bundle, WindowContainerToken windowContainerToken, int i3, int i4, boolean z, boolean z2) {
        if (z2 && i2 != -1) {
            this.mStageCoordinator.startTaskWithMultiSplit(i, i2, bundle, i3, i4, z);
            return;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 3393513473091973382L, 0, null);
        }
        if (isTaskInSplitScreen$1(i) && this.mStageCoordinator.isSplitScreenVisible()) {
            return;
        }
        final int[] iArr = new int[1];
        IRemoteAnimationRunner.Stub stub = new IRemoteAnimationRunner.Stub() { // from class: com.android.wm.shell.splitscreen.SplitScreenController.3
            public final void onAnimationCancelled() {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                StageCoordinator stageCoordinator = SplitScreenController.this.mStageCoordinator;
                stageCoordinator.mMainStage.evictInvisibleChildren(windowContainerTransaction);
                stageCoordinator.mSideStage.evictInvisibleChildren(windowContainerTransaction);
                SplitScreenController.this.mSyncQueue.queue(windowContainerTransaction);
            }

            public final void onAnimationStart(int i5, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                try {
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                } catch (RemoteException e) {
                    Slog.e("SplitScreenController", "Failed to invoke onAnimationFinished", e);
                }
                int i6 = iArr[0];
                if (i6 == 0 || i6 == 2) {
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    StageCoordinator stageCoordinator = SplitScreenController.this.mStageCoordinator;
                    if (i2 == stageCoordinator.mSideStagePosition) {
                        stageCoordinator.mSideStage.evictNonOpeningChildren(remoteAnimationTargetArr, windowContainerTransaction);
                    } else {
                        stageCoordinator.mMainStage.evictNonOpeningChildren(remoteAnimationTargetArr, windowContainerTransaction);
                    }
                    SplitScreenController.this.mSyncQueue.queue(windowContainerTransaction);
                }
            }
        };
        Bundle bundleResolveStartStage = this.mStageCoordinator.resolveStartStage(-1, i2, bundle, null, -1);
        RemoteAnimationAdapter remoteAnimationAdapter = new RemoteAnimationAdapter(stub, 0L, 0L);
        ActivityOptions activityOptionsFromBundle = ActivityOptions.fromBundle(bundleResolveStartStage);
        activityOptionsFromBundle.update(ActivityOptions.makeRemoteAnimation(remoteAnimationAdapter));
        try {
            iArr[0] = ActivityTaskManager.getService().startActivityFromRecents(i, activityOptionsFromBundle.toBundle());
        } catch (RemoteException e) {
            Slog.e("SplitScreenController", "Failed to launch task", e);
        }
    }

    public final Rect getStageBounds(int i) {
        return this.mStageCoordinator.getStageBounds(i);
    }

    @Override // com.android.wm.shell.draganddrop.SplitDragPolicy.Starter
    public final void startIntent(PendingIntent pendingIntent, int i, int i2, Bundle bundle, WindowContainerToken windowContainerToken, int i3) {
        startIntent(pendingIntent, i, null, i2, bundle, windowContainerToken, false, i3, -1, 0, false);
    }

    @Override // com.android.wm.shell.draganddrop.SplitDragPolicy.Starter
    public final void startIntent(PendingIntent pendingIntent, final int i, Intent intent, int i2, Bundle bundle, final WindowContainerToken windowContainerToken, boolean z, int i3, int i4, int i5, boolean z2) {
        int i6;
        int i7;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
            i6 = i2;
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 7397366169021201346L, 68, String.valueOf(pendingIntent), Long.valueOf(i), String.valueOf(intent), Long.valueOf(i6));
        } else {
            i6 = i2;
        }
        Intent intent2 = intent == null ? new Intent() : intent;
        intent2.addFlags(262144);
        String packageName = ComponentUtils.getPackageName(pendingIntent);
        String packageName2 = getPackageName(SplitScreenUtils.reverseSplitPosition(i6), windowContainerToken);
        int userId = getUserId(SplitScreenUtils.reverseSplitPosition(i6), windowContainerToken);
        final ComponentName component = pendingIntent.getIntent().getComponent();
        ActivityManager.RecentTaskInfo recentTaskInfo = z ? null : (ActivityManager.RecentTaskInfo) this.mRecentTasksOptional.map(new Function() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ComponentName componentName = component;
                int i8 = i;
                WindowContainerToken windowContainerToken2 = windowContainerToken;
                RecentTasksController recentTasksController = (RecentTasksController) obj;
                if (componentName == null) {
                    recentTasksController.getClass();
                    return null;
                }
                List recentTasks = recentTasksController.mActivityTaskManager.getRecentTasks(Integer.MAX_VALUE, 2, ActivityManager.getCurrentUser());
                for (int i9 = 0; i9 < recentTasks.size(); i9++) {
                    ActivityManager.RecentTaskInfo recentTaskInfo2 = (ActivityManager.RecentTaskInfo) recentTasks.get(i9);
                    if (!recentTaskInfo2.isVisible && !recentTaskInfo2.token.equals(windowContainerToken2) && componentName.equals(recentTaskInfo2.baseIntent.getComponent()) && i8 == recentTaskInfo2.userId) {
                        return recentTaskInfo2;
                    }
                }
                return null;
            }
        }).orElse(null);
        if (recentTaskInfo != null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 499723348482210341L, 0, String.valueOf(recentTaskInfo));
            }
            if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION && i5 != 0) {
                this.mStageCoordinator.startTaskWithMultiSplit(recentTaskInfo.taskId, i6, bundle, i4, i5, z2);
                i7 = 0;
            } else {
                StageCoordinator stageCoordinator = this.mStageCoordinator;
                int i8 = recentTaskInfo.taskId;
                i7 = 0;
                stageCoordinator.startTask(i8, i2, bundle, windowContainerToken, i3, i4);
            }
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 4518717401423163906L, i7, null);
                return;
            }
            return;
        }
        MultiInstanceHelper.samePackage(i, userId, packageName, packageName2);
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
            this.mStageCoordinator.startIntent(pendingIntent, intent2, i2, bundle, windowContainerToken, i4, i5, z2);
        } else {
            this.mStageCoordinator.startIntent(pendingIntent, intent2, i2, bundle, windowContainerToken, -1, 0, false);
        }
    }
}
