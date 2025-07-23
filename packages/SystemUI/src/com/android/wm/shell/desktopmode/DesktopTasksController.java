package com.android.wm.shell.desktopmode;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.TaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.WindowManager;
import android.widget.Toast;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import android.window.DisplayAreaInfo;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.util.SparseArrayKt$valueIterator$1;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.DesktopModeCompatUtils;
import com.android.internal.policy.SystemBarUtils;
import com.android.internal.protolog.ProtoLog;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$$ExternalSyntheticOutline0;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.DragHintToFullscreenManager;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.HomeIntentProvider;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.UserProfileContexts;
import com.android.wm.shell.desktopmode.DesktopImmersiveController;
import com.android.wm.shell.desktopmode.DesktopMixedTransitionHandler;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeTransitionTypes;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeVisualIndicator;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopTaskPosition;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler;
import com.android.wm.shell.desktopmode.common.ToggleTaskSizeInteraction;
import com.android.wm.shell.desktopmode.desktopwallpaperactivity.DesktopWallpaperActivityTokenProvider;
import com.android.wm.shell.desktopmode.minimize.DesktopWindowLimitRemoteHandler;
import com.android.wm.shell.desktopmode.multidesks.DeskTransition;
import com.android.wm.shell.desktopmode.multidesks.DesksOrganizer;
import com.android.wm.shell.desktopmode.multidesks.DesksTransitionObserver;
import com.android.wm.shell.desktopmode.multidesks.OnDeskRemovedListener;
import com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializer;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.draganddrop.GlobalDragListener$onUnhandledDrop$1;
import com.android.wm.shell.freeform.FreeformTaskTransitionStarter;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.recents.RecentsTransitionStateListener;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopConfigImpl;
import com.android.wm.shell.shared.desktopmode.DesktopModeCompatPolicy;
import com.android.wm.shell.shared.desktopmode.DesktopModeTransitionSource;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.shared.desktopmode.DesktopTaskToFrontReason;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.sysui.UserChangeListener;
import com.android.wm.shell.transition.FocusTransitionObserver;
import com.android.wm.shell.transition.OneShotRemoteHandler;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration;
import com.android.wm.shell.windowdecor.extension.TaskInfoKt;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingDecorViewModel;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingDividerWindowManager;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration$$ExternalSyntheticLambda2;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopTasksController implements RemoteCallable, Transitions.TransitionHandler, DragAndDropController.DragAndDropListener, UserChangeListener {
    public final Optional bubbleController;
    public final Context context;
    public DefaultDisplayDesktopModeChangeListener defaultDisplayDesktopModeChangeListener;
    public Executor defaultDisplayDesktopModeChangeListenerExecutor;
    public final DesksOrganizer desksOrganizer;
    public final DesksTransitionObserver desksTransitionObserver;
    public final DesktopConfig desktopConfig;
    public final ShellExecutor desktopExecutor;
    public final DesktopImmersiveController desktopImmersiveController;
    public final DesktopMixedTransitionHandler desktopMixedTransitionHandler;
    public final DesktopModeCompatPolicy desktopModeCompatPolicy;
    public final DesktopModeDragAndDropTransitionHandler desktopModeDragAndDropTransitionHandler;
    public DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopModeEnterExitTransitionListener;
    public final DesktopModeEventLogger desktopModeEventLogger;
    public final DesktopModeShellCommandHandler desktopModeShellCommandHandler;
    public final DesktopModeUiEventLogger desktopModeUiEventLogger;
    public final DesktopState desktopState;
    public final Optional desktopTasksLimiter;
    public final DesktopWallpaperActivityTokenProvider desktopWallpaperActivityTokenProvider;
    public final DisplayController displayController;
    public final DragAndDropController dragAndDropController;
    public Binder dragAndDropFullscreenCookie;
    public DragHintToFullscreenManager dragHintToFullscreenManager;
    public final DragToDesktopTransitionHandler dragToDesktopTransitionHandler;
    public final DragToDisplayTransitionHandler dragToDisplayTransitionHandler;
    public final EnterDesktopTaskTransitionHandler enterDesktopTaskTransitionHandler;
    public DesktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 exitDesktopModeListener;
    public final ExitDesktopTaskTransitionHandler exitDesktopTaskTransitionHandler;
    public final FocusTransitionObserver focusTransitionObserver;
    public FreeformTaskTransitionStarter freeformTaskTransitionStarter;
    public final Handler handler;
    public final HomeIntentProvider homeIntentProvider;
    public final InteractionJankMonitor interactionJankMonitor;
    public final KeyguardManager keyguardManager;
    public final ShellExecutor mainExecutor;
    public final CoroutineScope mainScope;
    public final DesktopModeMoveToDisplayTransitionHandler moveToDisplayTransitionHandler;
    public final MultiInstanceHelper multiInstanceHelper;
    public OnDeskRemovedListener onDeskRemovedListener;
    public final OverviewToDesktopTransitionObserver overviewToDesktopTransitionObserver;
    public final RecentTasksController recentTasksController;
    public final RecentsTransitionHandler recentsTransitionHandler;
    public final ReturnToDragStartAnimator returnToDragStartAnimator;
    public final RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer;
    public final ShellCommandHandler shellCommandHandler;
    public final ShellController shellController;
    public final ShellTaskOrganizer shellTaskOrganizer;
    public DesktopModeWindowDecorViewModel snapEventHandler;
    public SplitScreenController splitScreenController;
    public final SyncTransactionQueue syncQueue;
    public DesktopRepository taskRepository;
    public DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 taskbarDesktopTaskListener;
    public final ToggleResizeDesktopTaskTransitionHandler toggleResizeDesktopTaskTransitionHandler;
    public final Transitions transitions;
    public int userId;
    public final UserProfileContexts userProfileContexts;
    public final DesktopUserRepositories userRepositories;
    public DesktopModeVisualIndicator visualIndicator;
    public static final Companion Companion = new Companion(null);
    public static final float DESKTOP_MODE_INITIAL_BOUNDS_SCALE = SystemProperties.getInt("persist.wm.debug.desktop_mode_initial_bounds_scale", 75) / 100.0f;
    public static final long APP_HANDLE_DRAG_HOLD_CUJ_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(10);
    public static final IBinder SYNTHETIC_TRANSITION = new Binder();
    public final DesktopTasksController$$ExternalSyntheticLambda1 mOnAnimationFinishedCallback = new DesktopTasksController$$ExternalSyntheticLambda1(this);
    public final DesktopTasksController$dragToDesktopStateListener$1 dragToDesktopStateListener = new DesktopTasksController$dragToDesktopStateListener$1(this);
    public int recentsTransitionState = 1;
    public final DesktopModeImpl desktopMode = new DesktopModeImpl();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[DesktopTaskToFrontReason.values().length];
                try {
                    iArr[DesktopTaskToFrontReason.UNKNOWN.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[DesktopTaskToFrontReason.TASKBAR_TAP.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[DesktopTaskToFrontReason.ALT_TAB.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[DesktopTaskToFrontReason.TASKBAR_MANAGE_WINDOW.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DefaultDisplayDesktopModeChangeListener {
        void onDefaultDisplayDesktopModeChanged(boolean z);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DesktopModeImpl implements DesktopMode {
        public DesktopModeImpl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class IDesktopModeImpl extends IDesktopMode$Stub implements ExternalInterfaceBinder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public DesktopTasksController controller;
        public final SingleInstanceRemoteListener remoteListener;
        public final DesktopTasksController$IDesktopModeImpl$deskChangeListener$1 deskChangeListener = new DesktopRepository.DeskChangeListener() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$deskChangeListener$1
            @Override // com.android.wm.shell.desktopmode.DesktopRepository.DeskChangeListener
            public final void onActiveDeskChanged(final int i, final int i2, final int i3) {
                ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onActiveDeskChanged display=%d new=%d old=%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)});
                SingleInstanceRemoteListener singleInstanceRemoteListener = DesktopTasksController.IDesktopModeImpl.this.remoteListener;
                if (singleInstanceRemoteListener == null) {
                    singleInstanceRemoteListener = null;
                }
                singleInstanceRemoteListener.call(new SingleInstanceRemoteListener.RemoteCall() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$deskChangeListener$1$onActiveDeskChanged$1
                    @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
                    public final void accept(Object obj) {
                        IDesktopTaskListener$Stub$Proxy iDesktopTaskListener$Stub$Proxy = (IDesktopTaskListener$Stub$Proxy) ((IDesktopTaskListener) obj);
                        int i4 = i;
                        int i5 = i2;
                        int i6 = i3;
                        Parcel obtain = Parcel.obtain(iDesktopTaskListener$Stub$Proxy.mRemote);
                        try {
                            obtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                            obtain.writeInt(i4);
                            obtain.writeInt(i5);
                            obtain.writeInt(i6);
                            iDesktopTaskListener$Stub$Proxy.mRemote.transact(10, obtain, null, 1);
                        } finally {
                            obtain.recycle();
                        }
                    }
                });
            }

            @Override // com.android.wm.shell.desktopmode.DesktopRepository.DeskChangeListener
            public final void onCanCreateDesksChanged(final boolean z) {
                if (!CoreRune.DW_MULTIPLE_DESKS) {
                    ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onCanCreateDesksChanged do not support multiple desks", new Object[0]);
                    return;
                }
                ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onCanCreateDesksChanged canCreateDesks=%b", new Object[]{Boolean.valueOf(z)});
                SingleInstanceRemoteListener singleInstanceRemoteListener = DesktopTasksController.IDesktopModeImpl.this.remoteListener;
                if (singleInstanceRemoteListener == null) {
                    singleInstanceRemoteListener = null;
                }
                singleInstanceRemoteListener.call(new SingleInstanceRemoteListener.RemoteCall() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$deskChangeListener$1$onCanCreateDesksChanged$1
                    @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
                    public final void accept(Object obj) {
                        boolean z2 = z;
                        IDesktopTaskListener$Stub$Proxy iDesktopTaskListener$Stub$Proxy = (IDesktopTaskListener$Stub$Proxy) ((IDesktopTaskListener) obj);
                        Parcel obtain = Parcel.obtain(iDesktopTaskListener$Stub$Proxy.mRemote);
                        try {
                            obtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                            obtain.writeBoolean(z2);
                            iDesktopTaskListener$Stub$Proxy.mRemote.transact(7, obtain, null, 1);
                        } finally {
                            obtain.recycle();
                        }
                    }
                });
            }

            /* JADX WARN: Code restructure failed: missing block: B:58:0x0078, code lost:
            
                if (r0 == null) goto L24;
             */
            @Override // com.android.wm.shell.desktopmode.DesktopRepository.DeskChangeListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onDeskAdded(final int r8, final int r9) {
                /*
                    Method dump skipped, instructions count: 304
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$deskChangeListener$1.onDeskAdded(int, int):void");
            }

            @Override // com.android.wm.shell.desktopmode.DesktopRepository.DeskChangeListener
            public final void onDeskRemoved(final int i, final int i2) {
                ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onDeskRemoved display=%d deskId=%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
                SingleInstanceRemoteListener singleInstanceRemoteListener = DesktopTasksController.IDesktopModeImpl.this.remoteListener;
                if (singleInstanceRemoteListener == null) {
                    singleInstanceRemoteListener = null;
                }
                singleInstanceRemoteListener.call(new SingleInstanceRemoteListener.RemoteCall() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$deskChangeListener$1$onDeskRemoved$1
                    @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
                    public final void accept(Object obj) {
                        IDesktopTaskListener$Stub$Proxy iDesktopTaskListener$Stub$Proxy = (IDesktopTaskListener$Stub$Proxy) ((IDesktopTaskListener) obj);
                        int i3 = i;
                        int i4 = i2;
                        Parcel obtain = Parcel.obtain(iDesktopTaskListener$Stub$Proxy.mRemote);
                        try {
                            obtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                            obtain.writeInt(i3);
                            obtain.writeInt(i4);
                            iDesktopTaskListener$Stub$Proxy.mRemote.transact(9, obtain, null, 1);
                        } finally {
                            obtain.recycle();
                        }
                    }
                });
            }
        };
        public final DesktopTasksController$IDesktopModeImpl$visibleTasksListener$1 visibleTasksListener = new DesktopRepository.VisibleTasksListener() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$visibleTasksListener$1
            @Override // com.android.wm.shell.desktopmode.DesktopRepository.VisibleTasksListener
            public final void onTasksVisibilityChanged(final int i, final int i2) {
                ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onVisibilityChanged display=%d visible=%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
                SingleInstanceRemoteListener singleInstanceRemoteListener = DesktopTasksController.IDesktopModeImpl.this.remoteListener;
                if (singleInstanceRemoteListener == null) {
                    singleInstanceRemoteListener = null;
                }
                singleInstanceRemoteListener.call(new SingleInstanceRemoteListener.RemoteCall() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$visibleTasksListener$1$onTasksVisibilityChanged$1
                    @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
                    public final void accept(Object obj) {
                        IDesktopTaskListener$Stub$Proxy iDesktopTaskListener$Stub$Proxy = (IDesktopTaskListener$Stub$Proxy) ((IDesktopTaskListener) obj);
                        int i3 = i;
                        int i4 = i2;
                        Parcel obtain = Parcel.obtain(iDesktopTaskListener$Stub$Proxy.mRemote);
                        try {
                            obtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                            obtain.writeInt(i3);
                            obtain.writeInt(i4);
                            iDesktopTaskListener$Stub$Proxy.mRemote.transact(2, obtain, null, 1);
                        } finally {
                            obtain.recycle();
                        }
                    }
                });
            }
        };
        public final DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 taskbarDesktopTaskListener = new DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1(this);
        public final DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopModeEntryExitTransitionListener = new DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1(this);
        public final DesktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 exitDesktopModeListener = new DesktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1(this);

        /* JADX WARN: Type inference failed for: r4v1, types: [com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$deskChangeListener$1] */
        /* JADX WARN: Type inference failed for: r4v2, types: [com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$visibleTasksListener$1] */
        public IDesktopModeImpl(DesktopTasksController desktopTasksController) {
            this.controller = desktopTasksController;
            this.remoteListener = new SingleInstanceRemoteListener(this.controller, new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController.IDesktopModeImpl.1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    final DesktopTasksController desktopTasksController2 = (DesktopTasksController) obj;
                    IDesktopModeImpl iDesktopModeImpl = IDesktopModeImpl.this;
                    desktopTasksController2.getClass();
                    SingleInstanceRemoteListener singleInstanceRemoteListener = iDesktopModeImpl.remoteListener;
                    if (singleInstanceRemoteListener == null) {
                        singleInstanceRemoteListener = null;
                    }
                    singleInstanceRemoteListener.call(new SingleInstanceRemoteListener.RemoteCall() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$syncInitialState$1
                        @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
                        public final void accept(Object obj2) {
                            IDesktopTaskListener iDesktopTaskListener = (IDesktopTaskListener) obj2;
                            DesktopTasksController desktopTasksController3 = DesktopTasksController.this;
                            DesktopRepository.DesktopData desktopData = desktopTasksController3.taskRepository.desktopData;
                            FilteringSequence filter = SequencesKt___SequencesKt.filter(desktopData.desksSequence(), new DesktopRepository$$ExternalSyntheticLambda2(4));
                            LinkedHashMap linkedHashMap = new LinkedHashMap();
                            FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(filter);
                            while (filteringSequence$iterator$1.hasNext()) {
                                Object next = filteringSequence$iterator$1.next();
                                Integer valueOf = Integer.valueOf(((DesktopRepository.Desk) next).displayId);
                                Object obj3 = linkedHashMap.get(valueOf);
                                if (obj3 == null) {
                                    obj3 = new ArrayList();
                                    linkedHashMap.put(valueOf, obj3);
                                }
                                ((List) obj3).add(next);
                            }
                            ArrayList arrayList = new ArrayList(linkedHashMap.size());
                            for (Map.Entry entry : linkedHashMap.entrySet()) {
                                int intValue = ((Number) entry.getKey()).intValue();
                                List list = (List) entry.getValue();
                                DesktopRepository.Desk activeDesk = desktopData.getActiveDesk(intValue);
                                Integer valueOf2 = activeDesk != null ? Integer.valueOf(activeDesk.deskId) : null;
                                DisplayDeskState displayDeskState = new DisplayDeskState();
                                displayDeskState.displayId = intValue;
                                displayDeskState.activeDeskId = valueOf2 != null ? valueOf2.intValue() : -1;
                                List list2 = list;
                                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                                Iterator it = list2.iterator();
                                while (it.hasNext()) {
                                    arrayList2.add(Integer.valueOf(((DesktopRepository.Desk) it.next()).deskId));
                                }
                                displayDeskState.deskIds = CollectionsKt___CollectionsKt.toIntArray(arrayList2);
                                arrayList.add(displayDeskState);
                            }
                            boolean z = false;
                            DisplayDeskState[] displayDeskStateArr = (DisplayDeskState[]) arrayList.toArray(new DisplayDeskState[0]);
                            if (CoreRune.DW_MULTIPLE_DESKS) {
                                DesktopRepository desktopRepository = desktopTasksController3.taskRepository;
                                int i = ((DesktopConfigImpl) desktopTasksController3.desktopConfig).maxDeskLimit;
                                if (i == 0 || desktopRepository.desktopData.getNumberOfDesks() < i) {
                                    z = true;
                                }
                            }
                            IDesktopTaskListener$Stub$Proxy iDesktopTaskListener$Stub$Proxy = (IDesktopTaskListener$Stub$Proxy) iDesktopTaskListener;
                            Parcel obtain = Parcel.obtain(iDesktopTaskListener$Stub$Proxy.mRemote);
                            try {
                                obtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                                obtain.writeTypedArray(displayDeskStateArr, 0);
                                obtain.writeBoolean(z);
                                iDesktopTaskListener$Stub$Proxy.mRemote.transact(1, obtain, null, 1);
                            } finally {
                                obtain.recycle();
                            }
                        }
                    });
                    DesktopRepository desktopRepository = desktopTasksController2.taskRepository;
                    DesktopTasksController$IDesktopModeImpl$deskChangeListener$1 desktopTasksController$IDesktopModeImpl$deskChangeListener$1 = iDesktopModeImpl.deskChangeListener;
                    ArrayMap arrayMap = desktopRepository.deskChangeListeners;
                    ShellExecutor shellExecutor = desktopTasksController2.mainExecutor;
                    arrayMap.put(desktopTasksController$IDesktopModeImpl$deskChangeListener$1, shellExecutor);
                    desktopTasksController2.taskRepository.addVisibleTasksListener(iDesktopModeImpl.visibleTasksListener, shellExecutor);
                    desktopTasksController2.taskbarDesktopTaskListener = iDesktopModeImpl.taskbarDesktopTaskListener;
                    desktopTasksController2.desktopModeEnterExitTransitionListener = iDesktopModeImpl.desktopModeEntryExitTransitionListener;
                    desktopTasksController2.exitDesktopModeListener = iDesktopModeImpl.exitDesktopModeListener;
                }
            }, new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController.IDesktopModeImpl.2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DesktopTasksController desktopTasksController2 = (DesktopTasksController) obj;
                    IDesktopModeImpl iDesktopModeImpl = IDesktopModeImpl.this;
                    desktopTasksController2.getClass();
                    int i = IDesktopModeImpl.$r8$clinit;
                    iDesktopModeImpl.getClass();
                    DesktopRepository desktopRepository = desktopTasksController2.taskRepository;
                    desktopRepository.deskChangeListeners.remove(iDesktopModeImpl.deskChangeListener);
                    DesktopRepository desktopRepository2 = desktopTasksController2.taskRepository;
                    desktopRepository2.visibleTasksListeners.remove(iDesktopModeImpl.visibleTasksListener);
                    desktopTasksController2.taskbarDesktopTaskListener = null;
                    desktopTasksController2.desktopModeEnterExitTransitionListener = null;
                    desktopTasksController2.exitDesktopModeListener = null;
                }
            });
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            SingleInstanceRemoteListener singleInstanceRemoteListener = this.remoteListener;
            if (singleInstanceRemoteListener == null) {
                singleInstanceRemoteListener = null;
            }
            singleInstanceRemoteListener.unregister();
            this.controller = null;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SnapPosition {
        public static final /* synthetic */ SnapPosition[] $VALUES;
        public static final SnapPosition LEFT;
        public static final SnapPosition RIGHT;

        static {
            SnapPosition snapPosition = new SnapPosition("RIGHT", 0);
            RIGHT = snapPosition;
            SnapPosition snapPosition2 = new SnapPosition("LEFT", 1);
            LEFT = snapPosition2;
            SnapPosition[] snapPositionArr = {snapPosition, snapPosition2};
            $VALUES = snapPositionArr;
            EnumEntriesKt.enumEntries(snapPositionArr);
        }

        private SnapPosition(String str, int i) {
        }

        public static SnapPosition valueOf(String str) {
            return (SnapPosition) Enum.valueOf(SnapPosition.class, str);
        }

        public static SnapPosition[] values() {
            return (SnapPosition[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[SnapPosition.values().length];
            try {
                iArr[SnapPosition.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SnapPosition.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[DesktopModeVisualIndicator.IndicatorType.values().length];
            try {
                iArr2[DesktopModeVisualIndicator.IndicatorType.TO_FULLSCREEN_INDICATOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[DesktopModeVisualIndicator.IndicatorType.TO_SPLIT_LEFT_INDICATOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[DesktopModeVisualIndicator.IndicatorType.TO_SPLIT_RIGHT_INDICATOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[DesktopModeVisualIndicator.IndicatorType.NO_INDICATOR.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[DesktopModeVisualIndicator.IndicatorType.TO_BUBBLE_LEFT_INDICATOR.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[DesktopModeVisualIndicator.IndicatorType.TO_BUBBLE_RIGHT_INDICATOR.ordinal()] = 6;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_INDICATOR.ordinal()] = 7;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_MAXIMIZED_WINDOW.ordinal()] = 8;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public DesktopTasksController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DragAndDropController dragAndDropController, Transitions transitions, KeyguardManager keyguardManager, ReturnToDragStartAnimator returnToDragStartAnimator, DesktopMixedTransitionHandler desktopMixedTransitionHandler, EnterDesktopTaskTransitionHandler enterDesktopTaskTransitionHandler, ExitDesktopTaskTransitionHandler exitDesktopTaskTransitionHandler, DesktopModeDragAndDropTransitionHandler desktopModeDragAndDropTransitionHandler, ToggleResizeDesktopTaskTransitionHandler toggleResizeDesktopTaskTransitionHandler, DragToDesktopTransitionHandler dragToDesktopTransitionHandler, DesktopImmersiveController desktopImmersiveController, DesktopUserRepositories desktopUserRepositories, DesktopRepositoryInitializer desktopRepositoryInitializer, RecentsTransitionHandler recentsTransitionHandler, MultiInstanceHelper multiInstanceHelper, ShellExecutor shellExecutor, CoroutineScope coroutineScope, ShellExecutor shellExecutor2, Optional<DesktopTasksLimiter> optional, RecentTasksController recentTasksController, InteractionJankMonitor interactionJankMonitor, Handler handler, FocusTransitionObserver focusTransitionObserver, DesktopModeEventLogger desktopModeEventLogger, DesktopModeUiEventLogger desktopModeUiEventLogger, DesktopWallpaperActivityTokenProvider desktopWallpaperActivityTokenProvider, Optional<BubbleController> optional2, OverviewToDesktopTransitionObserver overviewToDesktopTransitionObserver, DesksOrganizer desksOrganizer, DesksTransitionObserver desksTransitionObserver, UserProfileContexts userProfileContexts, DesktopModeCompatPolicy desktopModeCompatPolicy, DragToDisplayTransitionHandler dragToDisplayTransitionHandler, DesktopModeMoveToDisplayTransitionHandler desktopModeMoveToDisplayTransitionHandler, HomeIntentProvider homeIntentProvider, DesktopState desktopState, DesktopConfig desktopConfig) {
        this.context = context;
        this.shellCommandHandler = shellCommandHandler;
        this.shellController = shellController;
        this.displayController = displayController;
        this.shellTaskOrganizer = shellTaskOrganizer;
        this.syncQueue = syncTransactionQueue;
        this.rootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        this.dragAndDropController = dragAndDropController;
        this.transitions = transitions;
        this.keyguardManager = keyguardManager;
        this.returnToDragStartAnimator = returnToDragStartAnimator;
        this.desktopMixedTransitionHandler = desktopMixedTransitionHandler;
        this.enterDesktopTaskTransitionHandler = enterDesktopTaskTransitionHandler;
        this.exitDesktopTaskTransitionHandler = exitDesktopTaskTransitionHandler;
        this.desktopModeDragAndDropTransitionHandler = desktopModeDragAndDropTransitionHandler;
        this.toggleResizeDesktopTaskTransitionHandler = toggleResizeDesktopTaskTransitionHandler;
        this.dragToDesktopTransitionHandler = dragToDesktopTransitionHandler;
        this.desktopImmersiveController = desktopImmersiveController;
        this.userRepositories = desktopUserRepositories;
        this.recentsTransitionHandler = recentsTransitionHandler;
        this.multiInstanceHelper = multiInstanceHelper;
        this.mainExecutor = shellExecutor;
        this.mainScope = coroutineScope;
        this.desktopExecutor = shellExecutor2;
        this.desktopTasksLimiter = optional;
        this.recentTasksController = recentTasksController;
        this.interactionJankMonitor = interactionJankMonitor;
        this.handler = handler;
        this.focusTransitionObserver = focusTransitionObserver;
        this.desktopModeEventLogger = desktopModeEventLogger;
        this.desktopModeUiEventLogger = desktopModeUiEventLogger;
        this.desktopWallpaperActivityTokenProvider = desktopWallpaperActivityTokenProvider;
        this.bubbleController = optional2;
        this.overviewToDesktopTransitionObserver = overviewToDesktopTransitionObserver;
        this.desksOrganizer = desksOrganizer;
        this.desksTransitionObserver = desksTransitionObserver;
        this.userProfileContexts = userProfileContexts;
        this.desktopModeCompatPolicy = desktopModeCompatPolicy;
        this.dragToDisplayTransitionHandler = dragToDisplayTransitionHandler;
        this.moveToDisplayTransitionHandler = desktopModeMoveToDisplayTransitionHandler;
        this.homeIntentProvider = homeIntentProvider;
        this.desktopState = desktopState;
        this.desktopConfig = desktopConfig;
        this.desktopModeShellCommandHandler = new DesktopModeShellCommandHandler(this, focusTransitionObserver);
        if (((DesktopStateImpl) desktopState).canEnterDesktopMode) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController.1
                @Override // java.lang.Runnable
                public final void run() {
                    DeviceStateManager deviceStateManager;
                    final DesktopTasksController desktopTasksController = DesktopTasksController.this;
                    Companion companion = DesktopTasksController.Companion;
                    desktopTasksController.getClass();
                    DesktopTasksController.logD$1("onInit", new Object[0]);
                    BiConsumer biConsumer = new BiConsumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$onInit$1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            DesktopTasksController.this.dump$2((PrintWriter) obj, (String) obj2);
                        }
                    };
                    ShellCommandHandler shellCommandHandler2 = desktopTasksController.shellCommandHandler;
                    shellCommandHandler2.addDumpCallback(biConsumer, desktopTasksController);
                    shellCommandHandler2.addCommandCallback("desktopmode", desktopTasksController.desktopModeShellCommandHandler, desktopTasksController);
                    Supplier supplier = new Supplier() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$onInit$2
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                            DesktopTasksController.Companion companion2 = DesktopTasksController.Companion;
                            desktopTasksController2.getClass();
                            return new DesktopTasksController.IDesktopModeImpl(desktopTasksController2);
                        }
                    };
                    ShellController shellController2 = desktopTasksController.shellController;
                    shellController2.addExternalInterface("com.android.wm.shell.desktopmode.IDesktopMode", supplier, desktopTasksController);
                    shellController2.addUserChangeListener(desktopTasksController);
                    desktopTasksController.updateCurrentUser(ActivityManager.getCurrentUser());
                    desktopTasksController.transitions.addHandler(desktopTasksController);
                    desktopTasksController.dragToDesktopTransitionHandler.dragToDesktopStateListener = desktopTasksController.dragToDesktopStateListener;
                    desktopTasksController.recentsTransitionHandler.mStateListeners.add(new RecentsTransitionStateListener() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$onInit$3
                        @Override // com.android.wm.shell.recents.RecentsTransitionStateListener
                        public final void onTransitionStateChanged(int i) {
                            DesktopTilingDividerWindowManager desktopTilingDividerWindowManager;
                            String str = i != 1 ? i != 2 ? i != 3 ? "UNKNOWN" : "TRANSITION_STATE_ANIMATING" : "TRANSITION_STATE_REQUESTED" : "TRANSITION_STATE_NOT_RUNNING";
                            DesktopTasksController.Companion companion2 = DesktopTasksController.Companion;
                            DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                            desktopTasksController2.getClass();
                            DesktopTasksController.logV$1("Recents transition state changed: %s", str);
                            desktopTasksController2.recentsTransitionState = i;
                            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = desktopTasksController2.snapEventHandler;
                            if (desktopModeWindowDecorViewModel == null) {
                                desktopModeWindowDecorViewModel = null;
                            }
                            boolean z = i >= 3;
                            SparseArrayKt$valueIterator$1 sparseArrayKt$valueIterator$1 = new SparseArrayKt$valueIterator$1(desktopModeWindowDecorViewModel.mDesktopTilingDecorViewModel.tilingTransitionHandlerByDisplayId);
                            while (sparseArrayKt$valueIterator$1.hasNext()) {
                                DesktopTilingWindowDecoration desktopTilingWindowDecoration = (DesktopTilingWindowDecoration) sparseArrayKt$valueIterator$1.next();
                                if (desktopTilingWindowDecoration.isTilingManagerInitialised) {
                                    if (z) {
                                        DesktopTilingDividerWindowManager desktopTilingDividerWindowManager2 = desktopTilingWindowDecoration.desktopTilingDividerWindowManager;
                                        if (desktopTilingDividerWindowManager2 != null && desktopTilingDividerWindowManager2.dividerShown) {
                                            SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) desktopTilingDividerWindowManager2.transactionSupplier.get();
                                            transaction.hide(desktopTilingDividerWindowManager2.leash);
                                            transaction.apply();
                                            desktopTilingDividerWindowManager2.dividerShown = false;
                                        }
                                    } else if (desktopTilingWindowDecoration.allTiledTasksVisible() && (desktopTilingDividerWindowManager = desktopTilingWindowDecoration.desktopTilingDividerWindowManager) != null && !desktopTilingDividerWindowManager.dividerShown) {
                                        SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) desktopTilingDividerWindowManager.transactionSupplier.get();
                                        transaction2.show(desktopTilingDividerWindowManager.leash);
                                        transaction2.apply();
                                        desktopTilingDividerWindowManager.dividerShown = true;
                                    }
                                }
                            }
                        }
                    });
                    desktopTasksController.dragAndDropController.mListeners.add(desktopTasksController);
                    if (!CoreRune.DW_MULTI_FOLD_POLICY || (deviceStateManager = (DeviceStateManager) desktopTasksController.context.getSystemService(DeviceStateManager.class)) == null) {
                        return;
                    }
                    deviceStateManager.registerCallback(desktopTasksController.mainExecutor, new DeviceStateManager.FoldStateListener(desktopTasksController.context, new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$onInit$4
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            Integer activeDeskId;
                            Boolean bool = (Boolean) obj;
                            if (bool != null) {
                                DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                                boolean booleanValue = bool.booleanValue();
                                DesktopTasksController.Companion companion2 = DesktopTasksController.Companion;
                                desktopTasksController2.getClass();
                                if (booleanValue) {
                                    DesktopStateImpl.Companion.getClass();
                                    if (!DesktopStateImpl.Companion.inDesktopWindowing(0) || (activeDeskId = desktopTasksController2.taskRepository.getActiveDeskId(0)) == null) {
                                        return;
                                    }
                                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                    desktopTasksController2.moveHomeTaskToTop(windowContainerTransaction, 0);
                                    desktopTasksController2.prepareDeskDeactivationIfNeeded(windowContainerTransaction, activeDeskId);
                                    desktopTasksController2.shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                                    desktopTasksController2.taskRepository.setDeskInactive(activeDeskId.intValue());
                                    DesktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 = desktopTasksController2.exitDesktopModeListener;
                                    if (desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 != null) {
                                        desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1.onExitDesktopModeStarted();
                                    }
                                }
                            }
                        }
                    }));
                }
            }, this);
        }
        int currentUser = ActivityManager.getCurrentUser();
        this.userId = currentUser;
        this.taskRepository = desktopUserRepositories.getProfile(currentUser);
        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            DesktopRepositoryInitializerImpl desktopRepositoryInitializerImpl = (DesktopRepositoryInitializerImpl) desktopRepositoryInitializer;
            desktopRepositoryInitializerImpl.deskRecreationFactory = new DesktopRepositoryInitializer.DeskRecreationFactory() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController.2
                @Override // com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializer.DeskRecreationFactory
                public final Object recreateDesk(int i, int i2, int i3, Continuation continuation) {
                    Companion companion = DesktopTasksController.Companion;
                    DesktopTasksController desktopTasksController = DesktopTasksController.this;
                    desktopTasksController.getClass();
                    final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
                    desktopTasksController.createDeskRoot(i2, i, new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$createDeskRootSuspending$2$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            int i4 = Result.$r8$clinit;
                            Continuation.this.resumeWith((Integer) obj);
                            return Unit.INSTANCE;
                        }
                    });
                    Object orThrow = safeContinuation.getOrThrow();
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    return orThrow;
                }
            };
            desktopRepositoryInitializerImpl.deskActivationFactory = new DesktopRepositoryInitializer.DeskActivationFactory() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController.3
                @Override // com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializer.DeskActivationFactory
                public final Object activeDesk(int i, Continuation continuation) {
                    Companion companion = DesktopTasksController.Companion;
                    DesktopTasksController desktopTasksController = DesktopTasksController.this;
                    desktopTasksController.getClass();
                    final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
                    Function1 function1 = new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$activeDeskSuspending$2$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            int i2 = Result.$r8$clinit;
                            Continuation.this.resumeWith((Integer) obj);
                            return Unit.INSTANCE;
                        }
                    };
                    DesktopTasksController.activateDesk$default(desktopTasksController, i, null, 0, 0, 14);
                    function1.mo779invoke(Integer.valueOf(i));
                    Object orThrow = safeContinuation.getOrThrow();
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (orThrow != coroutineSingletons) {
                        orThrow = Unit.INSTANCE;
                    }
                    return orThrow == coroutineSingletons ? orThrow : Unit.INSTANCE;
                }
            };
            optional.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                    ((DesktopTasksLimiter) obj).desktopTasksController = DesktopTasksController.this;
                    return Unit.INSTANCE;
                }
            }));
        }
    }

    public static /* synthetic */ void activateDesk$default(DesktopTasksController desktopTasksController, int i, RemoteTransition remoteTransition, int i2, int i3, int i4) {
        if ((i4 & 2) != 0) {
            remoteTransition = null;
        }
        if ((i4 & 4) != 0) {
            i2 = -1;
        }
        if ((i4 & 8) != 0) {
            i3 = -1;
        }
        desktopTasksController.activateDesk(i, remoteTransition, i2, i3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Function1 addDeskActivationChanges$default(final DesktopTasksController desktopTasksController, final int i, WindowContainerTransaction windowContainerTransaction, TaskInfo taskInfo, int i2, int i3, int i4) {
        int i5;
        final Integer num;
        final TaskInfo taskInfo2 = (i4 & 4) != 0 ? null : taskInfo;
        int i6 = 0;
        final boolean z = (i4 & 8) == 0;
        int i7 = (i4 & 16) != 0 ? -1 : i2;
        final int displayForDesk = (i4 & 32) != 0 ? i7 != -1 ? i7 : desktopTasksController.taskRepository.desktopData.getDisplayForDesk(i) : i3;
        desktopTasksController.getClass();
        Integer valueOf = taskInfo2 != null ? Integer.valueOf(taskInfo2.taskId) : null;
        logV$1("addDeskActivationChanges newTaskId=%d deskId=%d displayId=%d", taskInfo2 != null ? Integer.valueOf(taskInfo2.taskId) : null, Integer.valueOf(i), Integer.valueOf(displayForDesk));
        boolean isTrue = DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue();
        ShellTaskOrganizer shellTaskOrganizer = desktopTasksController.shellTaskOrganizer;
        if (!isTrue) {
            Integer valueOf2 = taskInfo2 != null ? Integer.valueOf(taskInfo2.taskId) : null;
            logV$1("bringDesktopAppsToFront, newTaskId=%d", valueOf2);
            desktopTasksController.prepareForDeskActivation(windowContainerTransaction, displayForDesk);
            List expandedTasksOrdered = desktopTasksController.taskRepository.getExpandedTasksOrdered(displayForDesk);
            if (valueOf2 == null || !desktopTasksController.desktopTasksLimiter.isPresent()) {
                num = null;
            } else {
                DesktopTasksLimiter desktopTasksLimiter = (DesktopTasksLimiter) desktopTasksController.desktopTasksLimiter.get();
                int i8 = DesktopTasksLimiter.$r8$clinit;
                num = desktopTasksLimiter.getTaskIdToMinimize(expandedTasksOrdered, valueOf2, false);
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) expandedTasksOrdered;
            int size = arrayList2.size();
            while (i6 < size) {
                Object obj = arrayList2.get(i6);
                i6++;
                int intValue = ((Number) obj).intValue();
                if (num == null || intValue != num.intValue()) {
                    arrayList.add(obj);
                }
            }
            Iterator it = CollectionsKt___CollectionsKt.reversed(arrayList).iterator();
            while (it.hasNext()) {
                int intValue2 = ((Number) it.next()).intValue();
                ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer.getRunningTaskInfo(intValue2);
                if (runningTaskInfo != null) {
                    windowContainerTransaction.reorder(runningTaskInfo.token, true);
                } else if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setLaunchWindowingMode(5);
                    makeBasic.setSplashScreenStyle(1);
                    windowContainerTransaction.startTask(intValue2, makeBasic.toBundle());
                }
            }
            DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 = desktopTasksController.taskbarDesktopTaskListener;
            if (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 != null) {
                desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1.onTaskbarCornerRoundingUpdate(desktopTasksController.doesAnyTaskRequireTaskbarRounding(displayForDesk, null));
            }
            return new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    TaskInfo taskInfo3 = taskInfo2;
                    IBinder iBinder = (IBinder) obj2;
                    DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                    Integer num2 = num;
                    DesktopTasksController desktopTasksController2 = desktopTasksController;
                    if (num2 != null) {
                        desktopTasksController2.addPendingMinimizeTransition(iBinder, num2.intValue(), DesktopModeEventLogger.Companion.MinimizeReason.TASK_LIMIT);
                    }
                    if (taskInfo3 != null && z) {
                        desktopTasksController2.addPendingAppLaunchTransition(iBinder, taskInfo3.taskId, num2);
                    }
                    return Unit.INSTANCE;
                }
            };
        }
        final DesktopRepository desktopRepository = desktopTasksController.taskRepository;
        DesktopRepository.Desk desk = desktopRepository.desktopData.getDesk(i);
        if (desk != null && desk.usedDesk == -1) {
            for (Map.Entry entry : desktopRepository.deskChangeListeners.entrySet()) {
                final DesktopRepository.DeskChangeListener deskChangeListener = (DesktopRepository.DeskChangeListener) entry.getKey();
                ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$postPendingOnDeskAddedIfNeeded$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DesktopRepository.DeskChangeListener.this.onDeskAdded(displayForDesk, i);
                        DesktopRepository desktopRepository2 = desktopRepository;
                        int i9 = DesktopRepository.$r8$clinit;
                        if (desktopRepository2.canCreateDesks()) {
                            return;
                        }
                        DesktopRepository.DeskChangeListener.this.onCanCreateDesksChanged(false);
                    }
                });
            }
        }
        desktopTasksController.prepareForDeskActivation(windowContainerTransaction, displayForDesk);
        DesksOrganizer desksOrganizer = desktopTasksController.desksOrganizer;
        if (i7 != -1) {
            DisplayAreaInfo displayAreaInfo = desktopTasksController.rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(displayForDesk);
            if (displayAreaInfo != null) {
                WindowContainerToken windowContainerToken = displayAreaInfo.token;
                RootTaskDesksOrganizer rootTaskDesksOrganizer = (RootTaskDesksOrganizer) desksOrganizer;
                Object obj2 = rootTaskDesksOrganizer.deskRootsByDeskId.get(i);
                if (obj2 == null) {
                    throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Root not found for desk: ").toString());
                }
                RootTaskDesksOrganizer.DeskRoot deskRoot = (RootTaskDesksOrganizer.DeskRoot) obj2;
                Object obj3 = ((LinkedHashMap) rootTaskDesksOrganizer.deskMinimizationRootsByDeskId).get(Integer.valueOf(i));
                if (obj3 == null) {
                    throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Minimization root not found for desk: ").toString());
                }
                windowContainerTransaction.reparent(deskRoot.token, windowContainerToken, false);
                windowContainerTransaction.reparent(((RootTaskDesksOrganizer.DeskMinimizationRoot) obj3).token, windowContainerToken, false);
                rootTaskDesksOrganizer.activateDesk(windowContainerTransaction, i);
            }
        } else {
            ((RootTaskDesksOrganizer) desksOrganizer).activateDesk(windowContainerTransaction, i);
        }
        DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$12 = desktopTasksController.taskbarDesktopTaskListener;
        if (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$12 != null) {
            desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$12.onTaskbarCornerRoundingUpdate(desktopTasksController.doesAnyTaskRequireTaskbarRounding(displayForDesk, null));
        }
        List expandedTasksIdsInDeskOrdered = desktopTasksController.taskRepository.getExpandedTasksIdsInDeskOrdered(i);
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
        DesktopTasksLimiter desktopTasksLimiter2 = (DesktopTasksLimiter) desktopTasksController.desktopTasksLimiter.orElse(null);
        T taskIdsToMinimize$default = desktopTasksLimiter2 != null ? DesktopTasksLimiter.getTaskIdsToMinimize$default(desktopTasksLimiter2, expandedTasksIdsInDeskOrdered, valueOf) : 0;
        ref$ObjectRef2.element = taskIdsToMinimize$default;
        List list = (List) taskIdsToMinimize$default;
        if (list != null) {
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                ActivityManager.RunningTaskInfo runningTaskInfo2 = shellTaskOrganizer.getRunningTaskInfo(((Number) it2.next()).intValue());
                if (runningTaskInfo2 != null) {
                    ((RootTaskDesksOrganizer) desksOrganizer).minimizeTask(windowContainerTransaction, i, runningTaskInfo2);
                }
            }
        }
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = (ArrayList) expandedTasksIdsInDeskOrdered;
            int size2 = arrayList4.size();
            int i9 = 0;
            while (i9 < size2) {
                Object obj4 = arrayList4.get(i9);
                i9 += i5;
                int intValue3 = ((Number) obj4).intValue();
                T t = ref$ObjectRef2.element;
                if (t != 0) {
                    i5 = ((List) t).contains(Integer.valueOf(intValue3)) ? 1 : 1;
                    arrayList3.add(obj4);
                } else {
                    Integer num2 = (Integer) ref$ObjectRef.element;
                    if (num2 != null && intValue3 == num2.intValue()) {
                    }
                    arrayList3.add(obj4);
                }
            }
            Iterator it3 = CollectionsKt___CollectionsKt.reversed(arrayList3).iterator();
            while (it3.hasNext()) {
                int intValue4 = ((Number) it3.next()).intValue();
                ActivityManager.RunningTaskInfo runningTaskInfo3 = shellTaskOrganizer.getRunningTaskInfo(intValue4);
                if (runningTaskInfo3 == null) {
                    ActivityOptions makeBasic2 = ActivityOptions.makeBasic();
                    makeBasic2.setLaunchWindowingMode(5);
                    makeBasic2.setSplashScreenStyle(1);
                    windowContainerTransaction.startTask(intValue4, makeBasic2.toBundle());
                } else if (runningTaskInfo3.parentTaskId != i) {
                    ((RootTaskDesksOrganizer) desksOrganizer).moveTaskToDesk(windowContainerTransaction, i, runningTaskInfo3);
                } else {
                    ((RootTaskDesksOrganizer) desksOrganizer).reorderTaskToFront(windowContainerTransaction, i, runningTaskInfo3);
                }
            }
        }
        Integer activeDeskId = desktopTasksController.taskRepository.getActiveDeskId(displayForDesk);
        final DesktopTasksController$$ExternalSyntheticLambda5 prepareDeskDeactivationIfNeeded = desktopTasksController.prepareDeskDeactivationIfNeeded(windowContainerTransaction, (activeDeskId == null || activeDeskId.intValue() == i) ? null : activeDeskId);
        if (displayForDesk == 0) {
            DesktopStateImpl.Companion.getClass();
            DesktopStateImpl.Companion.setInDesktopWindowing(true);
            desktopTasksController.onDefaultDisplayDesktopModeChanged(true);
        } else {
            DesktopStateImpl.Companion.getClass();
            DesktopStateImpl.desktopExternalDisplayId = displayForDesk;
        }
        final int i10 = displayForDesk;
        final Integer num3 = valueOf;
        return new Function1(num3, i10, i, desktopTasksController, ref$ObjectRef2, ref$ObjectRef, prepareDeskDeactivationIfNeeded) { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda4
            public final /* synthetic */ Integer f$0;
            public final /* synthetic */ int f$1;
            public final /* synthetic */ int f$2;
            public final /* synthetic */ DesktopTasksController f$3;
            public final /* synthetic */ Ref$ObjectRef f$4;
            public final /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda5 f$6;

            {
                this.f$6 = prepareDeskDeactivationIfNeeded;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj5) {
                DeskTransition activateDesk;
                final IBinder iBinder = (IBinder) obj5;
                Integer num4 = this.f$0;
                int i11 = this.f$1;
                int i12 = this.f$2;
                DesktopTasksController desktopTasksController2 = this.f$3;
                if (num4 != null) {
                    DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                    activateDesk = new DeskTransition.ActiveDeskWithTask(iBinder, i11, i12, num4.intValue());
                } else {
                    activateDesk = desktopTasksController2.taskRepository.desktopData.getDisplayForDesk(i12) != i11 ? new DeskTransition.ActivateDesk(iBinder, i11, i12, desktopTasksController2.onDeskRemovedListener) : new DeskTransition.ActivateDesk(iBinder, i11, i12, null, 8, null);
                }
                desktopTasksController2.desksTransitionObserver.addPendingTransition(activateDesk);
                final List list2 = (List) this.f$4.element;
                if (list2 != null) {
                    final DesktopModeEventLogger.Companion.MinimizeReason minimizeReason = DesktopModeEventLogger.Companion.MinimizeReason.TASK_LIMIT;
                    Integer num5 = (Integer) CollectionsKt___CollectionsKt.firstOrNull(list2);
                    final ActivityManager.RunningTaskInfo runningTaskInfo4 = num5 != null ? desktopTasksController2.shellTaskOrganizer.getRunningTaskInfo(num5.intValue()) : null;
                    desktopTasksController2.desktopTasksLimiter.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda8
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj6) {
                            IBinder iBinder2 = iBinder;
                            ActivityManager.RunningTaskInfo runningTaskInfo5 = runningTaskInfo4;
                            List list3 = list2;
                            DesktopTasksLimiter desktopTasksLimiter3 = (DesktopTasksLimiter) obj6;
                            DesktopTasksController.Companion companion2 = DesktopTasksController.Companion;
                            desktopTasksLimiter3.addPendingMinimizeChanges(iBinder2, runningTaskInfo5 != null ? runningTaskInfo5.displayId : 0, list3, minimizeReason);
                            return Unit.INSTANCE;
                        }
                    }));
                }
                DesktopTasksController$$ExternalSyntheticLambda5 desktopTasksController$$ExternalSyntheticLambda5 = this.f$6;
                if (desktopTasksController$$ExternalSyntheticLambda5 != null) {
                    desktopTasksController$$ExternalSyntheticLambda5.mo779invoke(iBinder);
                }
                return Unit.INSTANCE;
            }
        };
    }

    public static void createDesk$default(final DesktopTasksController desktopTasksController, final int i, int i2, boolean z, DesktopTasksController$createDeskSuspending$2$1 desktopTasksController$createDeskSuspending$2$1, int i3) {
        int i4;
        if ((i3 & 2) != 0) {
            i2 = desktopTasksController.userId;
        }
        final int i5 = i2;
        boolean z2 = (i3 & 4) != 0;
        final boolean z3 = (i3 & 8) != 0 ? false : z;
        final boolean z4 = (i3 & 16) == 0;
        Function1 function1 = desktopTasksController$createDeskSuspending$2$1;
        if ((i3 & 32) != 0) {
            function1 = new DesktopTasksController$$ExternalSyntheticLambda10(0);
        }
        final Function1 function12 = function1;
        Object[] objArr = {Integer.valueOf(i), Integer.valueOf(i5), Boolean.valueOf(z2)};
        desktopTasksController.getClass();
        logV$1("createDesk displayId=%d, userId=%d enforceDeskLimit=%b", objArr);
        final DesktopRepository profile = desktopTasksController.userRepositories.getProfile(i5);
        DesktopRepository.DesktopData desktopData = profile.desktopData;
        if (i == 0 && z4) {
            DesktopRepository.Desk deskForCreateByHome = desktopData.getDeskForCreateByHome();
            Integer valueOf = deskForCreateByHome != null ? Integer.valueOf(deskForCreateByHome.deskId) : null;
            if (valueOf != null) {
                final int intValue = valueOf.intValue();
                DesktopRepository.Desk desk = desktopData.getDesk(intValue);
                if (desk != null) {
                    desk.usedDesk = 0;
                }
                profile.updatePersistentRepository(i);
                for (Map.Entry entry : profile.deskChangeListeners.entrySet()) {
                    final DesktopRepository.DeskChangeListener deskChangeListener = (DesktopRepository.DeskChangeListener) entry.getKey();
                    ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$postCreateDeskFromHomeAndReuse$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            DesktopRepository.DeskChangeListener.this.onDeskAdded(i, intValue);
                            DesktopRepository desktopRepository = profile;
                            int i6 = DesktopRepository.$r8$clinit;
                            if (desktopRepository.canCreateDesks()) {
                                return;
                            }
                            DesktopRepository.DeskChangeListener.this.onCanCreateDesksChanged(false);
                        }
                    });
                }
                return;
            }
        }
        if (!z2 || (i4 = ((DesktopConfigImpl) desktopTasksController.desktopConfig).maxDeskLimit) == 0 || desktopData.getNumberOfDesks() < i4) {
            desktopTasksController.createDeskRoot(i, i5, new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    Integer num = (Integer) obj;
                    DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                    DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                    int i6 = i;
                    if (num == null) {
                        Object[] objArr2 = {Integer.valueOf(i6), Integer.valueOf(i5)};
                        desktopTasksController2.getClass();
                        DesktopTasksController.logW("Failed to add desk in displayId=%d for userId=%d", objArr2);
                    } else {
                        DesktopRepository desktopRepository = profile;
                        if (z4) {
                            desktopRepository.addDesk(i6, num.intValue(), 0);
                        } else {
                            desktopRepository.addDesk(i6, num.intValue(), -1);
                        }
                        function12.mo779invoke(num);
                        if (z3) {
                            DesktopTasksController.activateDesk$default(desktopTasksController2, num.intValue(), null, 0, 0, 14);
                        }
                    }
                    return Unit.INSTANCE;
                }
            });
        } else {
            logW("createDesk already at desk-limit, ignoring request", new Object[0]);
        }
    }

    public static void logD$1(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String concat = "%s: ".concat(str);
        SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopTasksController", objArr);
        ProtoLog.d(shellProtoLogGroup, concat, m.list.toArray(new Object[m.list.size()]));
    }

    public static void logE(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String concat = "%s: ".concat(str);
        SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopTasksController", objArr);
        ProtoLog.e(shellProtoLogGroup, concat, m.list.toArray(new Object[m.list.size()]));
    }

    public static void logV$1(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", str);
        SpreadBuilder m2 = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopTasksController", objArr);
        ProtoLog.v(shellProtoLogGroup, m, m2.list.toArray(new Object[m2.list.size()]));
    }

    public static void logW(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", str);
        SpreadBuilder m2 = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopTasksController", objArr);
        ProtoLog.w(shellProtoLogGroup, m, m2.list.toArray(new Object[m2.list.size()]));
    }

    public static /* synthetic */ boolean moveTaskToDefaultDeskAndActivate$default(DesktopTasksController desktopTasksController, int i, DesktopModeTransitionSource desktopModeTransitionSource, RemoteTransition remoteTransition, IMoveToDesktopCallback iMoveToDesktopCallback, int i2) {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if ((i2 & 8) != 0) {
            remoteTransition = null;
        }
        return desktopTasksController.moveTaskToDefaultDeskAndActivate(i, windowContainerTransaction, desktopModeTransitionSource, remoteTransition);
    }

    public static boolean moveTaskToDesk$default(DesktopTasksController desktopTasksController, int i, int i2, WindowContainerTransaction windowContainerTransaction, DesktopModeTransitionSource desktopModeTransitionSource, RemoteTransition remoteTransition, int i3) {
        boolean z;
        IBinder moveToDesktop;
        Function1 function1;
        IBinder moveToDesktop2;
        Function1 function12;
        WindowContainerTransaction windowContainerTransaction2 = (i3 & 4) != 0 ? new WindowContainerTransaction() : windowContainerTransaction;
        RemoteTransition remoteTransition2 = (i3 & 16) != 0 ? null : remoteTransition;
        ActivityManager.RunningTaskInfo runningTaskInfo = desktopTasksController.shellTaskOrganizer.getRunningTaskInfo(i);
        EnterDesktopTaskTransitionHandler enterDesktopTaskTransitionHandler = desktopTasksController.enterDesktopTaskTransitionHandler;
        Transitions transitions = desktopTasksController.transitions;
        ShellExecutor shellExecutor = desktopTasksController.mainExecutor;
        DesktopImmersiveController desktopImmersiveController = desktopTasksController.desktopImmersiveController;
        if (runningTaskInfo != null) {
            int displayForDesk = desktopTasksController.taskRepository.desktopData.getDisplayForDesk(i2);
            z = true;
            logV$1("moveRunningTaskToDesk taskId=%d deskId=%d displayId=%d", Integer.valueOf(runningTaskInfo.taskId), Integer.valueOf(i2), Integer.valueOf(displayForDesk));
            desktopTasksController.exitSplitIfApplicable(windowContainerTransaction2, runningTaskInfo);
            DesktopImmersiveController.ExitResult exitImmersiveIfApplicable = desktopImmersiveController.exitImmersiveIfApplicable(windowContainerTransaction2, displayForDesk, Integer.valueOf(runningTaskInfo.taskId), DesktopImmersiveController.ExitReason.TASK_LAUNCH);
            Function1 addDeskActivationChanges$default = addDeskActivationChanges$default(desktopTasksController, i2, windowContainerTransaction2, runningTaskInfo, 0, 0, 56);
            desktopTasksController.addMoveToDeskTaskChanges(windowContainerTransaction2, runningTaskInfo, i2, null);
            if (remoteTransition2 != null) {
                OneShotRemoteHandler oneShotRemoteHandler = new OneShotRemoteHandler(shellExecutor, remoteTransition2);
                moveToDesktop2 = transitions.startTransition(3, windowContainerTransaction2, oneShotRemoteHandler);
                oneShotRemoteHandler.mTransition = moveToDesktop2;
            } else {
                moveToDesktop2 = enterDesktopTaskTransitionHandler.moveToDesktop(windowContainerTransaction2, desktopModeTransitionSource);
            }
            DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 = desktopTasksController.desktopModeEnterExitTransitionListener;
            if (desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 != null) {
                desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1.onEnterDesktopModeTransitionStarted();
            }
            addDeskActivationChanges$default.mo779invoke(moveToDesktop2);
            DesktopImmersiveController.ExitResult.Exit asExit = exitImmersiveIfApplicable.asExit();
            if (asExit != null && (function12 = asExit.runOnTransitionStart) != null) {
                function12.mo779invoke(moveToDesktop2);
            }
            if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                desktopTasksController.taskRepository.setActiveDesk(displayForDesk, i2);
                return true;
            }
        } else {
            z = true;
            RecentTasksController recentTasksController = desktopTasksController.recentTasksController;
            if ((recentTasksController != null ? recentTasksController.findTaskInBackground(i) : null) == null) {
                logW("moveTaskToDesk taskId=%d not found", Integer.valueOf(i));
                return false;
            }
            ActivityManager.RecentTaskInfo findTaskInBackground = recentTasksController != null ? recentTasksController.findTaskInBackground(i) : null;
            if (findTaskInBackground == null) {
                logW("moveBackgroundTaskToDesktop taskId=%d not found", Integer.valueOf(i));
                return false;
            }
            logV$1("moveBackgroundTaskToDesktop with taskId=%d", Integer.valueOf(i));
            Integer orCreateDefaultDeskId = desktopTasksController.getOrCreateDefaultDeskId(findTaskInBackground.displayId, false);
            if (orCreateDefaultDeskId == null) {
                return false;
            }
            Function1 addDeskActivationChanges$default2 = addDeskActivationChanges$default(desktopTasksController, orCreateDefaultDeskId.intValue(), windowContainerTransaction2, findTaskInBackground, 0, 0, 56);
            DesktopImmersiveController.ExitResult exitImmersiveIfApplicable2 = desktopImmersiveController.exitImmersiveIfApplicable(windowContainerTransaction2, 0, Integer.valueOf(i), DesktopImmersiveController.ExitReason.TASK_LAUNCH);
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setLaunchWindowingMode(5);
            Unit unit = Unit.INSTANCE;
            windowContainerTransaction2.startTask(i, makeBasic.toBundle());
            if (remoteTransition2 != null) {
                OneShotRemoteHandler oneShotRemoteHandler2 = new OneShotRemoteHandler(shellExecutor, remoteTransition2);
                moveToDesktop = transitions.startTransition(3, windowContainerTransaction2, oneShotRemoteHandler2);
                oneShotRemoteHandler2.mTransition = moveToDesktop;
            } else {
                moveToDesktop = enterDesktopTaskTransitionHandler.moveToDesktop(windowContainerTransaction2, desktopModeTransitionSource);
            }
            DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$12 = desktopTasksController.desktopModeEnterExitTransitionListener;
            if (desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$12 != null) {
                desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$12.onEnterDesktopModeTransitionStarted();
            }
            addDeskActivationChanges$default2.mo779invoke(moveToDesktop);
            DesktopImmersiveController.ExitResult.Exit asExit2 = exitImmersiveIfApplicable2.asExit();
            if (asExit2 != null && (function1 = asExit2.runOnTransitionStart) != null) {
                function1.mo779invoke(moveToDesktop);
            }
        }
        return z;
    }

    public static /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda5 performDesktopExitCleanUp$default(DesktopTasksController desktopTasksController, WindowContainerTransaction windowContainerTransaction, Integer num, int i, boolean z, boolean z2, int i2) {
        if ((i2 & 16) != 0) {
            z2 = true;
        }
        return desktopTasksController.performDesktopExitCleanUp(windowContainerTransaction, num, i, z, z2, false);
    }

    public static void removeDesk$default(DesktopTasksController desktopTasksController, int i) {
        DesktopRepository desktopRepository = desktopTasksController.taskRepository;
        desktopTasksController.getClass();
        desktopTasksController.removeDesk(desktopRepository.desktopData.getDisplayForDesk(i), i, desktopRepository);
    }

    public static void setBoundsToDropPosition(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, DisplayLayout displayLayout, PointF pointF, Rect rect) {
        Rect rect2 = new Rect(rect);
        rect2.offsetTo(((int) pointF.x) - (rect2.width() / 2), (int) pointF.y);
        int i = rect2.left;
        int i2 = rect2.top;
        Rect rect3 = new Rect();
        displayLayout.getStableBounds(rect3, false);
        int i3 = rect2.right;
        int i4 = rect3.right;
        if (i3 > i4) {
            i = i4 - rect2.width();
        } else {
            int i5 = rect2.left;
            int i6 = rect3.left;
            if (i5 < i6) {
                i = i6;
            }
        }
        int i7 = rect2.bottom;
        int i8 = rect3.bottom;
        if (i7 > i8) {
            i2 = i8 - rect2.height();
        } else {
            int i9 = rect2.top;
            int i10 = rect3.top;
            if (i9 < i10) {
                i2 = i10;
            }
        }
        rect2.offsetTo(i, i2);
        windowContainerTransaction.setBounds(runningTaskInfo.token, rect2);
    }

    public static /* synthetic */ void startLaunchTransition$default(DesktopTasksController desktopTasksController, WindowContainerTransaction windowContainerTransaction, int i, int i2) {
        desktopTasksController.startLaunchTransition(1, windowContainerTransaction, null, null, i, i2, DesktopModeEventLogger.Companion.UnminimizeReason.UNKNOWN);
    }

    public final void activateDesk(int i, RemoteTransition remoteTransition, int i2, int i3) {
        Integer num;
        int displayForDesk;
        Integer activeDeskId;
        if (this.taskRepository.getAllDeskIds().contains(Integer.valueOf(i))) {
            if (i2 == -1 || i2 == (displayForDesk = this.taskRepository.desktopData.getDisplayForDesk(i)) || (activeDeskId = this.taskRepository.getActiveDeskId(displayForDesk)) == null || i != activeDeskId.intValue()) {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                Function1 addDeskActivationChanges$default = addDeskActivationChanges$default(this, i, windowContainerTransaction, null, i2, 0, 44);
                int i4 = 0;
                if (i3 != -1) {
                    List expandedTasksIdsInDeskOrdered = this.taskRepository.getExpandedTasksIdsInDeskOrdered(i);
                    DesktopTasksLimiter desktopTasksLimiter = (DesktopTasksLimiter) this.desktopTasksLimiter.orElse(null);
                    List taskIdsToMinimize$default = desktopTasksLimiter != null ? DesktopTasksLimiter.getTaskIdsToMinimize$default(desktopTasksLimiter, expandedTasksIdsInDeskOrdered, Integer.valueOf(i3)) : null;
                    DesksOrganizer desksOrganizer = this.desksOrganizer;
                    ShellTaskOrganizer shellTaskOrganizer = this.shellTaskOrganizer;
                    if (taskIdsToMinimize$default != null) {
                        int displayForDesk2 = i2 != -1 ? i2 : this.taskRepository.desktopData.getDisplayForDesk(i);
                        Iterator it = taskIdsToMinimize$default.iterator();
                        while (it.hasNext()) {
                            ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer.getRunningTaskInfo(((Number) it.next()).intValue());
                            if (runningTaskInfo != null) {
                                ((RootTaskDesksOrganizer) desksOrganizer).minimizeTask(windowContainerTransaction, i, runningTaskInfo);
                            }
                        }
                        DesktopTasksLimiter desktopTasksLimiter2 = (DesktopTasksLimiter) this.desktopTasksLimiter.orElse(null);
                        if (desktopTasksLimiter2 != null && (num = desktopTasksLimiter2.maxTasksLimit) != null) {
                            int intValue = num.intValue();
                            desktopTasksLimiter2.desktopUserRepositories.getCurrent();
                            Toast.makeText(desktopTasksLimiter2.displayController.getDisplayContext(displayForDesk2), desktopTasksLimiter2.context.getResources().getQuantityString(R.plurals.duration_days_shortest_future, intValue, Integer.valueOf(intValue)), 0).show();
                        }
                    }
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = shellTaskOrganizer.getRunningTaskInfo(i3);
                    if (runningTaskInfo2 != null) {
                        ((RootTaskDesksOrganizer) desksOrganizer).reorderTaskToFront(windowContainerTransaction, i, runningTaskInfo2);
                    } else {
                        logV$1("moveBackgroundTaskToFront taskId=%s", Integer.valueOf(i3));
                        DisplayLayout displayLayout = this.displayController.getDisplayLayout(this.taskRepository.desktopData.getDisplayForDesk(i));
                        if (displayLayout != null) {
                            ActivityOptions makeBasic = ActivityOptions.makeBasic();
                            makeBasic.setLaunchWindowingMode(5);
                            makeBasic.setLaunchBounds(DesktopModeUtils.calculateDefaultDesktopTaskBounds(displayLayout));
                            Unit unit = Unit.INSTANCE;
                            windowContainerTransaction.startTask(i3, makeBasic.toBundle());
                        }
                    }
                }
                if (remoteTransition == null) {
                    logV$1("RemoteTransition is null", new Object[0]);
                } else {
                    i4 = 3;
                }
                Transitions transitions = this.transitions;
                OneShotRemoteHandler oneShotRemoteHandler = remoteTransition != null ? new OneShotRemoteHandler(transitions.mMainExecutor, remoteTransition) : null;
                IBinder startTransition = transitions.startTransition(i4, windowContainerTransaction, oneShotRemoteHandler);
                if (oneShotRemoteHandler != null) {
                    oneShotRemoteHandler.mTransition = startTransition;
                }
                startTransition.getClass();
                addDeskActivationChanges$default.mo779invoke(startTransition);
                DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 = this.desktopModeEnterExitTransitionListener;
                if (desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 != null) {
                    desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1.onEnterDesktopModeTransitionStarted();
                }
            }
        }
    }

    public final Integer addAndGetMinimizeChanges(int i, WindowContainerTransaction windowContainerTransaction, Integer num, boolean z) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        Collection collection;
        if (!this.desktopTasksLimiter.isPresent()) {
            return null;
        }
        if (num != null && z) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        DesktopTasksLimiter desktopTasksLimiter = (DesktopTasksLimiter) this.desktopTasksLimiter.get();
        desktopTasksLimiter.getClass();
        DesktopTasksLimiter.logV("addAndGetMinimizeTaskChanges, newFrontTask=%d", num);
        DesktopRepository current = desktopTasksLimiter.desktopUserRepositories.getCurrent();
        boolean isDeskActive = current.isDeskActive(i);
        DesktopRepository.DesktopData desktopData = current.desktopData;
        DesksOrganizer desksOrganizer = desktopTasksLimiter.desksOrganizer;
        DisplayController displayController = desktopTasksLimiter.displayController;
        ShellTaskOrganizer shellTaskOrganizer = desktopTasksLimiter.shellTaskOrganizer;
        if (!isDeskActive) {
            Integer taskIdToMinimize = desktopTasksLimiter.getTaskIdToMinimize(current.getExpandedTasksIdsInDeskOrdered(i), num, z);
            if (taskIdToMinimize != null && (runningTaskInfo = shellTaskOrganizer.getRunningTaskInfo(taskIdToMinimize.intValue())) != null) {
                if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                    ((RootTaskDesksOrganizer) desksOrganizer).minimizeTask(windowContainerTransaction, i, runningTaskInfo);
                    Unit unit = Unit.INSTANCE;
                } else {
                    windowContainerTransaction.reorder(runningTaskInfo.token, false).getClass();
                }
                Integer num2 = desktopTasksLimiter.maxTasksLimit;
                if (num2 != null) {
                    int intValue = num2.intValue();
                    Toast.makeText(displayController.getDisplayContext(desktopData.getDisplayForDesk(i)), desktopTasksLimiter.context.getResources().getQuantityString(R.plurals.duration_days_shortest_future, intValue, Integer.valueOf(intValue)), 0).show();
                }
            }
            return taskIdToMinimize;
        }
        while (true) {
            DesktopRepository.Desk desk = desktopData.getDesk(i);
            if (desk == null || (collection = desk.visibleTasks) == null) {
                collection = EmptyList.INSTANCE;
            }
            Integer taskIdToMinimize2 = desktopTasksLimiter.getTaskIdToMinimize(CollectionsKt___CollectionsKt.reversed(new ArrayList(collection)), num, z);
            if (taskIdToMinimize2 == null) {
                return null;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo2 = shellTaskOrganizer.getRunningTaskInfo(taskIdToMinimize2.intValue());
            if (runningTaskInfo2 != null) {
                Integer num3 = desktopTasksLimiter.maxTasksLimit;
                if (num3 != null) {
                    int intValue2 = num3.intValue();
                    Toast.makeText(displayController.getDisplayContext(desktopData.getDisplayForDesk(i)), desktopTasksLimiter.context.getResources().getQuantityString(R.plurals.duration_days_shortest_future, intValue2, Integer.valueOf(intValue2)), 0).show();
                }
                ((RootTaskDesksOrganizer) desksOrganizer).minimizeTask(windowContainerTransaction, i, runningTaskInfo2);
                return taskIdToMinimize2;
            }
            current.removeTaskFromDesk(i, taskIdToMinimize2.intValue());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0045, code lost:
    
        if (r8 == 0) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addMoveToDeskTaskChanges(android.window.WindowContainerTransaction r5, android.app.ActivityManager.RunningTaskInfo r6, int r7, android.graphics.PointF r8) {
        /*
            r4 = this;
            com.android.wm.shell.desktopmode.DesktopRepository r0 = r4.taskRepository
            com.android.wm.shell.desktopmode.DesktopRepository$DesktopData r0 = r0.desktopData
            int r0 = r0.getDisplayForDesk(r7)
            com.android.wm.shell.common.DisplayController r1 = r4.displayController
            com.android.wm.shell.common.DisplayLayout r1 = r1.getDisplayLayout(r0)
            if (r1 != 0) goto L11
            return
        L11:
            com.android.wm.shell.desktopmode.DesktopRepository r2 = r4.taskRepository
            com.android.wm.shell.ShellTaskOrganizer r3 = r4.shellTaskOrganizer
            android.graphics.Rect r2 = com.android.wm.shell.desktopmode.DesktopModeUtils.getInheritedExistingTaskBounds(r2, r3, r6, r7)
            if (r2 == 0) goto L2b
            if (r8 == 0) goto L21
            setBoundsToDropPosition(r5, r6, r1, r8, r2)
            goto L4c
        L21:
            android.window.WindowContainerToken r8 = r6.token
            android.window.WindowContainerTransaction r8 = r5.setBounds(r8, r2)
            r8.getClass()
            goto L4c
        L2b:
            android.graphics.Rect r2 = r4.getInitialBounds(r1, r6, r0)
            if (r8 == 0) goto L35
            setBoundsToDropPosition(r5, r6, r1, r8, r2)
            goto L4c
        L35:
            android.content.pm.ActivityInfo r8 = r6.topActivityInfo
            if (r8 == 0) goto L47
            android.content.pm.ActivityInfo$WindowLayout r8 = r8.windowLayout
            if (r8 == 0) goto L47
            int r8 = r8.gravity
            r1 = r8 & 7
            r8 = r8 & 112(0x70, float:1.57E-43)
            if (r1 != 0) goto L4c
            if (r8 != 0) goto L4c
        L47:
            android.window.WindowContainerToken r8 = r6.token
            r5.setBounds(r8, r2)
        L4c:
            android.window.DesktopExperienceFlags r8 = android.window.DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND
            boolean r8 = r8.isTrue()
            r1 = 1
            if (r8 == 0) goto L5d
            com.android.wm.shell.desktopmode.multidesks.DesksOrganizer r8 = r4.desksOrganizer
            com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer r8 = (com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer) r8
            r8.moveTaskToDesk(r5, r7, r6)
            goto L80
        L5d:
            com.android.wm.shell.RootTaskDisplayAreaOrganizer r7 = r4.rootTaskDisplayAreaOrganizer
            android.window.DisplayAreaInfo r7 = r7.getDisplayAreaInfo(r0)
            r7.getClass()
            android.content.res.Configuration r7 = r7.configuration
            android.app.WindowConfiguration r7 = r7.windowConfiguration
            int r7 = r7.getWindowingMode()
            r8 = 5
            if (r7 != r8) goto L72
            r8 = 0
        L72:
            android.window.WindowContainerToken r7 = r6.token
            r5.setWindowingMode(r7, r8)
            android.window.WindowContainerToken r7 = r6.token
            android.window.WindowContainerTransaction r7 = r5.reorder(r7, r1)
            r7.getClass()
        L80:
            com.android.wm.shell.shared.desktopmode.DesktopConfig r4 = r4.desktopConfig
            com.android.wm.shell.shared.desktopmode.DesktopConfigImpl r4 = (com.android.wm.shell.shared.desktopmode.DesktopConfigImpl) r4
            boolean r7 = r4.useDesktopOverrideDensity
            if (r7 == 0) goto L8f
            android.window.WindowContainerToken r7 = r6.token
            int r4 = r4.desktopDensityOverride
            r5.setDensityDpi(r7, r4)
        L8f:
            android.window.WindowContainerToken r4 = r6.token
            r5.setSkipLayoutTask(r4, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.DesktopTasksController.addMoveToDeskTaskChanges(android.window.WindowContainerTransaction, android.app.ActivityManager$RunningTaskInfo, int, android.graphics.PointF):void");
    }

    public final DesktopTasksController$$ExternalSyntheticLambda5 addMoveToFullscreenChanges(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        DisplayAreaInfo displayAreaInfo = this.rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(runningTaskInfo.displayId);
        displayAreaInfo.getClass();
        int windowingMode = displayAreaInfo.configuration.windowConfiguration.getWindowingMode();
        boolean isTopActivityExemptFromDesktopWindowing = this.desktopModeCompatPolicy.isTopActivityExemptFromDesktopWindowing(runningTaskInfo);
        windowContainerTransaction.setWindowingMode(runningTaskInfo.token, (windowingMode != 1 || isTopActivityExemptFromDesktopWindowing) ? 1 : 0);
        windowContainerTransaction.setBounds(runningTaskInfo.token, new Rect());
        if (((DesktopConfigImpl) this.desktopConfig).useDesktopOverrideDensity) {
            windowContainerTransaction.setDensityDpi(runningTaskInfo.token, this.context.getResources().getDisplayMetrics().densityDpi);
        }
        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue() && !isTopActivityExemptFromDesktopWindowing) {
            windowContainerTransaction.reparent(runningTaskInfo.token, displayAreaInfo.token, true);
        }
        boolean z2 = DesktopModeCompatPolicy.isTransparentTask(((TaskInfo) runningTaskInfo).numActivities, ((TaskInfo) runningTaskInfo).isActivityStackTransparent) && isTopActivityExemptFromDesktopWindowing;
        if (((RootTaskDesksOrganizer) this.desksOrganizer).deskRootsByDeskId.contains(runningTaskInfo.parentTaskId) && !z2 && (runningTaskInfo2 = this.shellTaskOrganizer.getRunningTaskInfo(runningTaskInfo.parentTaskId)) != null) {
            windowContainerTransaction.reorder(runningTaskInfo2.token, false);
        }
        windowContainerTransaction.setFullscreenTransparentInDesktop(runningTaskInfo.token, z2);
        return performDesktopExitCleanUp$default(this, windowContainerTransaction, this.taskRepository.getDeskIdForTask(runningTaskInfo.taskId), runningTaskInfo.displayId, z, false, 32);
    }

    public final void addPendingAppLaunchTransition(IBinder iBinder, int i, Integer num) {
        if (DesktopModeFlags.ENABLE_DESKTOP_APP_LAUNCH_TRANSITIONS_BUGFIX.isTrue()) {
            ((ArrayList) this.desktopMixedTransitionHandler.pendingMixedTransitions).add(new DesktopMixedTransitionHandler.PendingMixedTransition.Launch(iBinder, Integer.valueOf(i), num, null));
        }
    }

    public final void addPendingMinimizeTransition(IBinder iBinder, int i, DesktopModeEventLogger.Companion.MinimizeReason minimizeReason) {
        this.desktopTasksLimiter.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new DesktopTasksController$$ExternalSyntheticLambda7(iBinder, this.shellTaskOrganizer.getRunningTaskInfo(i), i, minimizeReason)));
    }

    public final void cascadeWindow(Rect rect, DisplayLayout displayLayout, int i) {
        Rect rect2 = new Rect();
        displayLayout.getStableBounds(rect2, false);
        int i2 = displayLayout.mNavBarFrameHeight;
        int i3 = displayLayout.mTaskbarFrameHeight;
        if (i2 != i3) {
            rect2.bottom = displayLayout.mHeight - i3;
        }
        Integer num = (Integer) CollectionsKt___CollectionsKt.firstOrNull(this.taskRepository.getExpandedTasksOrdered(i));
        if (num != null) {
            ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(num.intValue());
            if (runningTaskInfo != null) {
                Resources resources = this.context.getResources();
                Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
                Rect rect3 = new Rect(rect);
                DesktopTaskPosition desktopTaskPosition = DesktopTaskPositionKt.getDesktopTaskPosition(rect2, bounds);
                DesktopTaskPosition.Center center = DesktopTaskPosition.Center.INSTANCE;
                Point topLeftCoordinates = center.getTopLeftCoordinates(rect2, rect3);
                rect3.offsetTo(topLeftCoordinates.x, topLeftCoordinates.y);
                int dimensionPixelSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.freeform_required_visible_empty_space_in_header);
                boolean z = rect3.left - bounds.left > dimensionPixelSize;
                boolean z2 = rect3.top - bounds.top > dimensionPixelSize;
                boolean z3 = bounds.right - rect3.right > dimensionPixelSize;
                boolean z4 = bounds.bottom - rect3.bottom > dimensionPixelSize;
                if ((!z && !z2 && !z3 && !z4) || !center.equals(desktopTaskPosition)) {
                    topLeftCoordinates = desktopTaskPosition.next().getTopLeftCoordinates(rect2, rect);
                }
                rect.offsetTo(topLeftCoordinates.x, topLeftCoordinates.y);
            }
        }
    }

    public final void createDeskRoot(int i, int i2, Function1 function1) {
        Object obj = null;
        if (i == -1) {
            logW("createDesk attempt with invalid displayId", Integer.valueOf(i));
            function1.mo779invoke(null);
            return;
        }
        if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            logD$1("createDesk reusing displayId=%d for single-desk", Integer.valueOf(i));
            function1.mo779invoke(Integer.valueOf(i));
            return;
        }
        int i3 = 0;
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_HSUM.isTrue() && UserManager.isHeadlessSystemUserMode() && i2 == 0) {
            logW("createDesk ignoring attempt for system user", new Object[0]);
            function1.mo779invoke(null);
            return;
        }
        DesktopTasksController$createDeskRoot$1 desktopTasksController$createDeskRoot$1 = new DesktopTasksController$createDeskRoot$1(this, i, i2, function1);
        RootTaskDesksOrganizer rootTaskDesksOrganizer = (RootTaskDesksOrganizer) this.desksOrganizer;
        Object[] objArr = {Integer.valueOf(i), Integer.valueOf(i2)};
        rootTaskDesksOrganizer.getClass();
        RootTaskDesksOrganizer.logV$2("createDesk in displayId=%d userId=%s", objArr);
        RootTaskDesksOrganizer.DeskRoot firstUnassignedDesk = rootTaskDesksOrganizer.firstUnassignedDesk(i, i2);
        if (firstUnassignedDesk != null) {
            firstUnassignedDesk.users.add(Integer.valueOf(i2));
            desktopTasksController$createDeskRoot$1.onCreated(firstUnassignedDesk.deskId);
            return;
        }
        ArrayList arrayList = (ArrayList) rootTaskDesksOrganizer.createDeskRootRequests;
        int size = arrayList.size();
        while (true) {
            if (i3 >= size) {
                break;
            }
            Object obj2 = arrayList.get(i3);
            i3++;
            if (((RootTaskDesksOrganizer.CreateDeskRequest) obj2).userId == null) {
                obj = obj2;
                break;
            }
        }
        RootTaskDesksOrganizer.CreateDeskRequest createDeskRequest = (RootTaskDesksOrganizer.CreateDeskRequest) obj;
        if (createDeskRequest != null) {
            ((ArrayList) rootTaskDesksOrganizer.createDeskRootRequests).remove(createDeskRequest);
            List list = rootTaskDesksOrganizer.createDeskRootRequests;
            ArrayList arrayList2 = (ArrayList) list;
            arrayList2.add(new RootTaskDesksOrganizer.CreateDeskRequest(createDeskRequest.displayId, Integer.valueOf(i2), createDeskRequest.onCreateCallback));
            return;
        }
        Integer valueOf = Integer.valueOf(i2);
        RootTaskDesksOrganizer.logV$2("createDeskRoot in display: %d for user: %d", Integer.valueOf(i), valueOf);
        ((ArrayList) rootTaskDesksOrganizer.createDeskRootRequests).add(new RootTaskDesksOrganizer.CreateDeskRequest(i, valueOf, desktopTasksController$createDeskRoot$1));
        rootTaskDesksOrganizer.shellTaskOrganizer.createDeskRootTask(i, 1, rootTaskDesksOrganizer);
    }

    public final ActivityOptions createNewWindowOptions(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int i;
        Rect initialBounds;
        if (runningTaskInfo.isFreeform()) {
            i = 5;
        } else {
            if (runningTaskInfo.getWindowingMode() != 1 && !TaskInfoKt.isMultiWindow(runningTaskInfo)) {
                throw new IllegalStateException(("Invalid windowing mode: " + runningTaskInfo.getWindowingMode()).toString());
            }
            i = 6;
        }
        if (i == 5) {
            DisplayLayout displayLayout = this.displayController.getDisplayLayout(runningTaskInfo.displayId);
            initialBounds = displayLayout != null ? getInitialBounds(displayLayout, runningTaskInfo, runningTaskInfo.displayId) : null;
        } else {
            if (i != 6) {
                throw new IllegalStateException(("Invalid windowing mode: " + i).toString());
            }
            initialBounds = new Rect();
        }
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchWindowingMode(i);
        makeBasic.setPendingIntentBackgroundActivityStartMode(3);
        makeBasic.setLaunchBounds(initialBounds);
        return makeBasic;
    }

    public final boolean doesAnyTaskRequireTaskbarRounding(int i, Integer num) {
        boolean z = true;
        List expandedTasksOrdered = this.taskRepository.getExpandedTasksOrdered(i);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) expandedTasksOrdered;
        int size = arrayList2.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList2.get(i2);
            i2++;
            int intValue = ((Number) obj).intValue();
            if (num == null || intValue != num.intValue()) {
                arrayList.add(obj);
            }
        }
        if (!arrayList.isEmpty()) {
            int size2 = arrayList.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList.get(i3);
                i3++;
                ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(((Number) obj2).intValue());
                if (runningTaskInfo != null) {
                    DisplayLayout displayLayout = this.displayController.getDisplayLayout(runningTaskInfo.displayId);
                    Rect rect = new Rect();
                    if (displayLayout != null) {
                        displayLayout.getStableBounds(rect, false);
                    }
                    logD$1("taskInfo = %s", runningTaskInfo);
                    Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
                    SnapPosition snapPosition = SnapPosition.LEFT;
                    logD$1("isTaskSnappedToHalfScreen(taskInfo) = %s", Boolean.valueOf(getSnapBounds(runningTaskInfo, snapPosition).equals(bounds) || getSnapBounds(runningTaskInfo, SnapPosition.RIGHT).equals(bounds)));
                    Rect bounds2 = runningTaskInfo.configuration.windowConfiguration.getBounds();
                    float f = DesktopModeUtils.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                    logD$1("isMaximizedToStableBoundsEdges(taskInfo, stableBounds) = %s", Boolean.valueOf(Intrinsics.areEqual(bounds2, rect)));
                    Rect bounds3 = runningTaskInfo.configuration.windowConfiguration.getBounds();
                    if (getSnapBounds(runningTaskInfo, snapPosition).equals(bounds3) || getSnapBounds(runningTaskInfo, SnapPosition.RIGHT).equals(bounds3) || Intrinsics.areEqual(runningTaskInfo.configuration.windowConfiguration.getBounds(), rect)) {
                        break;
                    }
                } else {
                    return false;
                }
            }
        }
        z = false;
        logD$1("doesAnyTaskRequireTaskbarRounding = %s", Boolean.valueOf(z));
        return z;
    }

    public final void dump$2(PrintWriter printWriter, String str) {
        String joinToString$default;
        String joinToString$default2;
        String joinToString$default3;
        String joinToString$default4;
        String str2;
        String str3 = "  ";
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "DesktopTasksController");
        DesktopConfigImpl desktopConfigImpl = (DesktopConfigImpl) this.desktopConfig;
        desktopConfigImpl.getClass();
        new IndentingPrintWriter(printWriter, "  ", m).increaseIndent();
        printWriter.println("DesktopConfig");
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("maxTaskLimit=", desktopConfigImpl.maxTaskLimit, printWriter);
        printWriter.print("maxTaskLimit config override=" + desktopConfigImpl.context.getResources().getInteger(R.integer.config_previousVibrationsDumpSizeLimit));
        SystemProperties.Handle find = SystemProperties.find("persist.wm.debug.desktop_max_task_limit");
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("maxTaskLimit sysprop=", find != null ? Integer.valueOf(find.getInt(-1)) : "null", printWriter);
        desktopConfigImpl.desktopState.getClass();
        printWriter.println("showAppHandle config override=false");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "inDesktopWindowing=", DesktopStateImpl.inDesktopWindowing);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("desktopExternalDisplayId=", DesktopStateImpl.desktopExternalDisplayId, printWriter);
        DesktopUserRepositories desktopUserRepositories = this.userRepositories;
        desktopUserRepositories.getClass();
        String str4 = m + "    ";
        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, m, "DesktopUserRepositories:");
        printWriter.println(str4 + "currentUserId=" + desktopUserRepositories.userId);
        DesktopUserRepositories$desktopRepoByUserId$1 desktopUserRepositories$desktopRepoByUserId$1 = desktopUserRepositories.desktopRepoByUserId;
        int size = desktopUserRepositories$desktopRepoByUserId$1.size();
        int i = 0;
        while (i < size) {
            desktopUserRepositories$desktopRepoByUserId$1.keyAt(i);
            DesktopRepository desktopRepository = (DesktopRepository) desktopUserRepositories$desktopRepoByUserId$1.valueAt(i);
            desktopRepository.getClass();
            String str5 = str4 + str3;
            printWriter.println(str4 + "DesktopRepository");
            printWriter.println(str5 + "userId=" + desktopRepository.userId);
            StringBuilder sb = new StringBuilder();
            sb.append(str5);
            sb.append(str3);
            String sb2 = sb.toString();
            DesktopRepository.DesktopData desktopData = desktopRepository.desktopData;
            Sequence desksSequence = desktopData.desksSequence();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : desksSequence) {
                DesktopUserRepositories$desktopRepoByUserId$1 desktopUserRepositories$desktopRepoByUserId$12 = desktopUserRepositories$desktopRepoByUserId$1;
                Integer valueOf = Integer.valueOf(((DesktopRepository.Desk) obj).displayId);
                Object obj2 = linkedHashMap.get(valueOf);
                if (obj2 == null) {
                    str2 = str4;
                    ArrayList arrayList = new ArrayList();
                    linkedHashMap.put(valueOf, arrayList);
                    obj2 = arrayList;
                } else {
                    str2 = str4;
                }
                ((List) obj2).add(obj);
                desktopUserRepositories$desktopRepoByUserId$1 = desktopUserRepositories$desktopRepoByUserId$12;
                str4 = str2;
            }
            DesktopUserRepositories$desktopRepoByUserId$1 desktopUserRepositories$desktopRepoByUserId$13 = desktopUserRepositories$desktopRepoByUserId$1;
            String str6 = str4;
            ArrayList arrayList2 = new ArrayList(linkedHashMap.size());
            Iterator it = linkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                int intValue = ((Number) entry.getKey()).intValue();
                List list = (List) entry.getValue();
                Iterator it2 = it;
                Integer valueOf2 = Integer.valueOf(intValue);
                DesktopRepository.Desk activeDesk = desktopData.getActiveDesk(intValue);
                arrayList2.add(new Triple(valueOf2, activeDesk != null ? Integer.valueOf(activeDesk.deskId) : null, list));
                it = it2;
            }
            int size2 = arrayList2.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj3 = arrayList2.get(i2);
                i2++;
                Triple triple = (Triple) obj3;
                int intValue2 = ((Number) triple.component1()).intValue();
                Integer num = (Integer) triple.component2();
                List<DesktopRepository.Desk> list2 = (List) triple.component3();
                ArrayList arrayList3 = arrayList2;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str5);
                int i3 = size2;
                sb3.append("Display #");
                sb3.append(intValue2);
                sb3.append(":");
                printWriter.println(sb3.toString());
                int size3 = list2.size();
                StringBuilder sb4 = new StringBuilder();
                sb4.append(sb2);
                int i4 = size;
                sb4.append("numOfDesks=");
                sb4.append(size3);
                printWriter.println(sb4.toString());
                printWriter.println(sb2 + "activeDesk=" + num);
                printWriter.println(sb2 + "desks:");
                String str7 = sb2 + str3;
                for (DesktopRepository.Desk desk : list2) {
                    int i5 = desk.deskId;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(str7);
                    String str8 = str3;
                    sb5.append("Desk #");
                    sb5.append(i5);
                    sb5.append(":");
                    printWriter.println(sb5.toString());
                    printWriter.print(str7 + "  activeTasks=");
                    joinToString$default = CollectionsKt___CollectionsKt.joinToString$default(desk.activeTasks, ", ", "[", "]", null, 56);
                    printWriter.println(joinToString$default);
                    printWriter.print(str7 + "  visibleTasks=");
                    joinToString$default2 = CollectionsKt___CollectionsKt.joinToString$default(desk.visibleTasks, ", ", "[", "]", null, 56);
                    printWriter.println(joinToString$default2);
                    printWriter.print(str7 + "  freeformTasksInZOrder=");
                    joinToString$default3 = CollectionsKt___CollectionsKt.joinToString$default(desk.freeformTasksInZOrder, ", ", "[", "]", null, 56);
                    printWriter.println(joinToString$default3);
                    printWriter.print(str7 + "  minimizedTasks=");
                    joinToString$default4 = CollectionsKt___CollectionsKt.joinToString$default(desk.minimizedTasks, ", ", "[", "]", null, 56);
                    printWriter.println(joinToString$default4);
                    printWriter.print(str7 + "  fullImmersiveTaskId=");
                    printWriter.println(desk.fullImmersiveTaskId);
                    printWriter.print(str7 + "  topTransparentFullscreenTaskId=");
                    printWriter.println(desk.topTransparentFullscreenTaskId);
                    printWriter.print(str7 + "  usedDesk=");
                    printWriter.println(desk.usedDesk);
                    printWriter.print(str7 + "  deskLabel=");
                    printWriter.println(desk.deskLabel);
                    str3 = str8;
                }
                arrayList2 = arrayList3;
                size2 = i3;
                size = i4;
            }
            printWriter.println(str5 + "activeTasksListeners=" + desktopRepository.activeTasksListeners.size());
            printWriter.println(str5 + "visibleTasksListeners=" + desktopRepository.visibleTasksListeners.size());
            i++;
            desktopUserRepositories$desktopRepoByUserId$1 = desktopUserRepositories$desktopRepoByUserId$13;
            str4 = str6;
            size = size;
            str3 = str3;
        }
        FocusTransitionObserver focusTransitionObserver = this.focusTransitionObserver;
        focusTransitionObserver.getClass();
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "    ", m);
        indentingPrintWriter.println("FocusTransitionObserver:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf("currentFocusedDisplayId=%d\n", new Object[]{Integer.valueOf(focusTransitionObserver.mFocusedDisplayId)});
        indentingPrintWriter.println("currentFocusedTaskOnDisplay:");
        indentingPrintWriter.increaseIndent();
        for (int i6 = 0; i6 < focusTransitionObserver.mFocusedTaskOnDisplay.size(); i6++) {
            indentingPrintWriter.printf("Display #%d: taskId=%d topActivity=%s\n", new Object[]{Integer.valueOf(focusTransitionObserver.mFocusedTaskOnDisplay.keyAt(i6)), Integer.valueOf(((ActivityManager.RunningTaskInfo) focusTransitionObserver.mFocusedTaskOnDisplay.valueAt(i6)).taskId), ((ActivityManager.RunningTaskInfo) focusTransitionObserver.mFocusedTaskOnDisplay.valueAt(i6)).topActivity});
        }
    }

    public final void exitSplitIfApplicable(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo) {
        SplitScreenController splitScreenController = this.splitScreenController;
        if (splitScreenController == null) {
            splitScreenController = null;
        }
        if (splitScreenController.isTaskInSplitScreen$1(runningTaskInfo.taskId)) {
            SplitScreenController splitScreenController2 = this.splitScreenController;
            SplitScreenController splitScreenController3 = splitScreenController2 == null ? null : splitScreenController2;
            if (splitScreenController2 == null) {
                splitScreenController2 = null;
            }
            splitScreenController3.prepareExitSplitScreen(splitScreenController2.getStageOfTask(runningTaskInfo.taskId), 12, windowContainerTransaction);
            SplitScreenController splitScreenController4 = this.splitScreenController;
            StageCoordinator transitionHandler = (splitScreenController4 != null ? splitScreenController4 : null).getTransitionHandler();
            if (transitionHandler != null) {
                transitionHandler.setSplitsVisible(false);
            }
        }
    }

    public final boolean forceEnterDesktop(int i) {
        Display display;
        DesktopStateImpl desktopStateImpl = (DesktopStateImpl) this.desktopState;
        if (desktopStateImpl.enterDesktopByDefaultOnFreeformDisplay && (((display = desktopStateImpl.displayManager.getDisplay(i)) == null || display.getType() != 5) && desktopStateImpl.isDesktopModeSupportedOnDisplay(i))) {
            if (i != 0) {
                return true;
            }
            DisplayAreaInfo displayAreaInfo = this.rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(i);
            if (displayAreaInfo == null) {
                logW("forceEnterDesktop cannot find DisplayAreaInfo for displayId=%d. This could happen when the display is a non-trusted virtual display.", Integer.valueOf(i));
                return false;
            }
            if (displayAreaInfo.configuration.windowConfiguration.getWindowingMode() == 5) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.context;
    }

    public final ActivityManager.RunningTaskInfo getFocusedFreeformTask(int i) {
        Object obj;
        ArrayList runningTasks = this.shellTaskOrganizer.getRunningTasks(i);
        int size = runningTasks.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                obj = null;
                break;
            }
            obj = runningTasks.get(i2);
            i2++;
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
            if (runningTaskInfo.isFocused && runningTaskInfo.getWindowingMode() == 5 && !runningTaskInfo.activatableDeskRoot) {
                break;
            }
        }
        return (ActivityManager.RunningTaskInfo) obj;
    }

    public final ActivityManager.RunningTaskInfo getHomeTask(int i) {
        Object obj;
        ArrayList runningTasks = this.shellTaskOrganizer.getRunningTasks(i);
        int size = runningTasks.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                obj = null;
                break;
            }
            obj = runningTasks.get(i2);
            i2++;
            if (((ActivityManager.RunningTaskInfo) obj).getActivityType() == 2) {
                break;
            }
        }
        return (ActivityManager.RunningTaskInfo) obj;
    }

    public final Rect getInitialBounds(DisplayLayout displayLayout, ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        Rect calculateDefaultDesktopTaskBounds;
        if (DesktopModeFlags.ENABLE_WINDOWING_DYNAMIC_INITIAL_BOUNDS.isTrue()) {
            this.desktopModeCompatPolicy.getClass();
            ActivityInfo activityInfo = ((TaskInfo) runningTaskInfo).topActivityInfo;
            calculateDefaultDesktopTaskBounds = DesktopModeUtils.calculateInitialBounds$default(displayLayout, runningTaskInfo, activityInfo != null ? DesktopModeCompatUtils.shouldExcludeCaptionFromAppBounds(activityInfo, ((TaskInfo) runningTaskInfo).isResizeable, ((TaskInfo) runningTaskInfo).appCompatTaskInfo.hasOptOutEdgeToEdge()) : false ? SystemBarUtils.getDesktopViewAppHeaderHeightPx(this.context) : 0, null, 20);
        } else {
            calculateDefaultDesktopTaskBounds = DesktopModeUtils.calculateDefaultDesktopTaskBounds(displayLayout);
        }
        if (DesktopModeFlags.ENABLE_CASCADING_WINDOWS.isTrue()) {
            cascadeWindow(calculateDefaultDesktopTaskBounds, displayLayout, i);
        }
        return calculateDefaultDesktopTaskBounds;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00dc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Integer getOrCreateDefaultDeskId(int r7, boolean r8) {
        /*
            Method dump skipped, instructions count: 259
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.DesktopTasksController.getOrCreateDefaultDeskId(int, boolean):java.lang.Integer");
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mainExecutor;
    }

    public final Rect getSnapBounds(ActivityManager.RunningTaskInfo runningTaskInfo, SnapPosition snapPosition) {
        DisplayLayout displayLayout = this.displayController.getDisplayLayout(runningTaskInfo.displayId);
        if (displayLayout == null) {
            return new Rect();
        }
        Rect rect = new Rect();
        displayLayout.getStableBounds(rect, false);
        int width = rect.width() / 2;
        int i = WhenMappings.$EnumSwitchMapping$0[snapPosition.ordinal()];
        if (i == 1) {
            int i2 = rect.left;
            return new Rect(i2, rect.top, width + i2, rect.bottom);
        }
        if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        int i3 = rect.right;
        return new Rect(i3 - width, rect.top, i3, rect.bottom);
    }

    public final DesktopModeVisualIndicator getVisualIndicator() {
        return this.visualIndicator;
    }

    /* JADX WARN: Code restructure failed: missing block: B:126:0x0312, code lost:
    
        if (r1.isEmpty() != false) goto L125;
     */
    /* JADX WARN: Removed duplicated region for block: B:59:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0265  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0624 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x063a  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0643  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.window.WindowContainerTransaction handleRequest(final android.os.IBinder r17, android.window.TransitionRequestInfo r18) {
        /*
            Method dump skipped, instructions count: 1625
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.DesktopTasksController.handleRequest(android.os.IBinder, android.window.TransitionRequestInfo):android.window.WindowContainerTransaction");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [T, android.content.Context] */
    public final void handleSnapResizingTaskOnDrag(ActivityManager.RunningTaskInfo runningTaskInfo, SnapPosition snapPosition, SurfaceControl surfaceControl, Rect rect, Rect rect2, MotionEvent motionEvent) {
        releaseVisualIndicator();
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ?? displayContext = this.displayController.getDisplayContext(runningTaskInfo.getDisplayId());
        ref$ObjectRef.element = displayContext;
        if (displayContext == 0) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(runningTaskInfo.getDisplayId(), "displayContext is null for ", "DesktopTasksController");
        }
        if (!runningTaskInfo.isResizeable && DesktopModeFlags.DISABLE_NON_RESIZABLE_APP_SNAP_RESIZE.isTrue()) {
            InteractionJankMonitor interactionJankMonitor = this.interactionJankMonitor;
            Context context = (Context) ref$ObjectRef.element;
            if (context == null) {
                context = this.context;
            }
            interactionJankMonitor.begin(surfaceControl, context, this.handler, 118, "drag_non_resizable");
            this.returnToDragStartAnimator.start(runningTaskInfo.taskId, surfaceControl, rect, rect2, new Function0() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda0
                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                    Context context2 = (Context) Ref$ObjectRef.this.element;
                    if (context2 == null) {
                        context2 = this.context;
                    }
                    Toast.makeText(context2, com.android.systemui.R.string.multiwindow_desktop_mode_non_resizable_snap_text, 0).show();
                    return Unit.INSTANCE;
                }
            });
            return;
        }
        DesktopModeEventLogger.Companion.ResizeTrigger resizeTrigger = snapPosition == SnapPosition.LEFT ? DesktopModeEventLogger.Companion.ResizeTrigger.DRAG_LEFT : DesktopModeEventLogger.Companion.ResizeTrigger.DRAG_RIGHT;
        InteractionJankMonitor interactionJankMonitor2 = this.interactionJankMonitor;
        Context context2 = (Context) ref$ObjectRef.element;
        if (context2 == null) {
            context2 = this.context;
        }
        interactionJankMonitor2.begin(surfaceControl, context2, this.handler, 118, "drag_resizable");
        DesktopModeEventLogger.Companion.getClass();
        snapToHalfScreen(runningTaskInfo, surfaceControl, rect, snapPosition, resizeTrigger, DesktopModeEventLogger.Companion.getInputMethodFromMotionEvent(motionEvent));
    }

    public final boolean isDesktopModeShowing(int i) {
        boolean isAnyDeskActive = this.taskRepository.isAnyDeskActive(i);
        boolean z = ((Integer) SequencesKt___SequencesKt.firstOrNull(SequencesKt___SequencesKt.mapNotNull(this.taskRepository.desktopData.desksSequence(i), new DesktopRepository$$ExternalSyntheticLambda2(2)))) != null;
        if (DesktopModeFlags.INCLUDE_TOP_TRANSPARENT_FULLSCREEN_TASK_IN_DESKTOP_HEURISTIC.isTrue() && DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_MODALS_POLICY.isTrue()) {
            logV$1("isDesktopModeShowing: hasVisibleTasks=%s hasTopTransparentFullscreenTask=%s", Boolean.valueOf(isAnyDeskActive), Boolean.valueOf(z));
            return isAnyDeskActive || z;
        }
        logV$1("isDesktopModeShowing: hasVisibleTasks=%s", Boolean.valueOf(isAnyDeskActive));
        return isAnyDeskActive;
    }

    public final void minimizeAllTasks(int i) {
        Set set;
        Integer activeDeskId = this.taskRepository.getActiveDeskId(i);
        Set activeTaskIdsInDesk = activeDeskId != null ? this.taskRepository.getActiveTaskIdsInDesk(activeDeskId.intValue()) : null;
        if ((activeDeskId != null && activeDeskId.intValue() == -1) || i == -1 || (set = activeTaskIdsInDesk) == null || set.isEmpty()) {
            return;
        }
        minimizeTasks(CollectionsKt___CollectionsKt.toList(activeTaskIdsInDesk), activeDeskId.intValue(), i);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void minimizeTask(android.app.ActivityManager.RunningTaskInfo r17, com.android.wm.shell.desktopmode.DesktopModeEventLogger.Companion.MinimizeReason r18) {
        /*
            Method dump skipped, instructions count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.DesktopTasksController.minimizeTask(android.app.ActivityManager$RunningTaskInfo, com.android.wm.shell.desktopmode.DesktopModeEventLogger$Companion$MinimizeReason):void");
    }

    public final void minimizeTasks(final List list, int i, final int i2) {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            int intValue = ((Number) it.next()).intValue();
            ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(intValue);
            if (runningTaskInfo != null) {
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.snapEventHandler;
                (desktopModeWindowDecorViewModel != null ? desktopModeWindowDecorViewModel : null).removeTaskIfTiled(i2, intValue);
                ((RootTaskDesksOrganizer) this.desksOrganizer).minimizeTask(windowContainerTransaction, i, runningTaskInfo);
            }
        }
        FreeformTaskTransitionStarter freeformTaskTransitionStarter = this.freeformTaskTransitionStarter;
        final IBinder startMinimizeAllTransition = (freeformTaskTransitionStarter != null ? freeformTaskTransitionStarter : null).startMinimizeAllTransition(windowContainerTransaction, i2);
        this.desktopTasksLimiter.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                IBinder iBinder = startMinimizeAllTransition;
                List list2 = list;
                DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                iBinder.getClass();
                ((DesktopTasksLimiter) obj).addPendingMinimizeChanges(iBinder, i2, list2, DesktopModeEventLogger.Companion.MinimizeReason.TASK_LIMIT);
                return Unit.INSTANCE;
            }
        }));
    }

    public final void moveHomeTaskToTop(WindowContainerTransaction windowContainerTransaction, int i) {
        logV$1("moveHomeTaskToTop in displayId=%d", Integer.valueOf(i));
        ActivityManager.RunningTaskInfo homeTask = getHomeTask(i);
        if (homeTask != null) {
            windowContainerTransaction.reorder(homeTask.getToken(), true);
        }
    }

    public final boolean moveTaskToDefaultDeskAndActivate(int i, WindowContainerTransaction windowContainerTransaction, DesktopModeTransitionSource desktopModeTransitionSource, RemoteTransition remoteTransition) {
        TaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(i);
        if (runningTaskInfo == null) {
            RecentTasksController recentTasksController = this.recentTasksController;
            runningTaskInfo = recentTasksController != null ? recentTasksController.findTaskInBackground(i) : null;
        }
        if (runningTaskInfo == null) {
            logW("moveTaskToDefaultDeskAndActivate taskId=%d not found", Integer.valueOf(i));
            return false;
        }
        int i2 = runningTaskInfo.displayId;
        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            BuildersKt.launch$default(this.mainScope, null, null, new DesktopTasksController$moveTaskToDefaultDeskAndActivate$1(this, i, i2, windowContainerTransaction, desktopModeTransitionSource, remoteTransition, null), 3);
            return true;
        }
        Integer orCreateDefaultDeskId = getOrCreateDefaultDeskId(i2, false);
        if (orCreateDefaultDeskId != null) {
            return moveTaskToDesk$default(this, i, orCreateDefaultDeskId.intValue(), windowContainerTransaction, desktopModeTransitionSource, remoteTransition, 32);
        }
        return false;
    }

    public final void moveTaskToFront(int i, RemoteTransition remoteTransition, DesktopModeEventLogger.Companion.UnminimizeReason unminimizeReason) {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(i);
        if (runningTaskInfo != null) {
            moveTaskToFront(runningTaskInfo, remoteTransition, unminimizeReason);
            return;
        }
        logV$1("moveBackgroundTaskToFront taskId=%s", Integer.valueOf(i));
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchWindowingMode(5);
        Unit unit = Unit.INSTANCE;
        windowContainerTransaction.startTask(i, makeBasic.toBundle());
        Integer deskIdForTask = this.taskRepository.getDeskIdForTask(i);
        if (deskIdForTask == null && (deskIdForTask = getOrCreateDefaultDeskId(0, false)) == null) {
            return;
        }
        startLaunchTransition(1, windowContainerTransaction, Integer.valueOf(i), remoteTransition, deskIdForTask.intValue(), 0, unminimizeReason);
    }

    public final void moveToDisplay(ActivityManager.RunningTaskInfo runningTaskInfo, int i, Rect rect, Transitions.TransitionHandler transitionHandler) {
        DisplayLayout displayLayout;
        Rect initialBounds;
        boolean z;
        WindowContainerTransaction windowContainerTransaction;
        DesktopTasksController$$ExternalSyntheticLambda5 desktopTasksController$$ExternalSyntheticLambda5;
        logV$1("moveToDisplay: taskId=%d displayId=%d", Integer.valueOf(runningTaskInfo.taskId), Integer.valueOf(i));
        if (runningTaskInfo.displayId == i) {
            logD$1("moveToDisplay: task already on display %d", Integer.valueOf(i));
            return;
        }
        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
        DisplayAreaInfo displayAreaInfo = this.rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(i);
        if (displayAreaInfo == null) {
            logW("moveToDisplay: display not found", new Object[0]);
            return;
        }
        Integer defaultDeskId = this.taskRepository.getDefaultDeskId(i);
        if (defaultDeskId == null) {
            logW(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "moveToDisplay: desk not found for display: "), new Object[0]);
            return;
        }
        DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.snapEventHandler;
        if (desktopModeWindowDecorViewModel == null) {
            desktopModeWindowDecorViewModel = null;
        }
        desktopModeWindowDecorViewModel.removeTaskIfTiled(runningTaskInfo.displayId, runningTaskInfo.taskId);
        Integer activeDeskId = this.taskRepository.getActiveDeskId(i);
        Transitions transitions = this.transitions;
        if (activeDeskId == null && i == 0) {
            windowContainerTransaction2.reparent(runningTaskInfo.token, displayAreaInfo.token, true);
            windowContainerTransaction2.setWindowingMode(runningTaskInfo.token, 0);
            windowContainerTransaction2.setBounds(runningTaskInfo.token, new Rect());
            transitions.startTransition(1, windowContainerTransaction2, null);
            return;
        }
        if (runningTaskInfo.isFreeform()) {
            if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                ((RootTaskDesksOrganizer) this.desksOrganizer).moveTaskToDesk(windowContainerTransaction2, defaultDeskId.intValue(), runningTaskInfo);
            }
            if (runningTaskInfo.getDisplayId() == 0) {
                logD$1("moveToDisplay: when it go to external display, unset alwaysOnTop", new Object[0]);
                WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                windowContainerTransaction3.setAlwaysOnTop(runningTaskInfo.token, false);
                this.shellTaskOrganizer.applyTransaction(windowContainerTransaction3);
            }
            if (rect != null) {
                windowContainerTransaction2.setBounds(runningTaskInfo.token, rect);
            } else {
                int i2 = runningTaskInfo.displayId;
                DisplayController displayController = this.displayController;
                DisplayLayout displayLayout2 = displayController.getDisplayLayout(i2);
                if (displayLayout2 != null && (displayLayout = displayController.getDisplayLayout(i)) != null) {
                    Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
                    int width = (bounds.width() * displayLayout.mDensityDpi) / displayLayout2.mDensityDpi;
                    int height = (bounds.height() * displayLayout.mDensityDpi) / displayLayout2.mDensityDpi;
                    int width2 = displayLayout2.mWidth - bounds.width();
                    int height2 = displayLayout2.mHeight - bounds.height();
                    int i3 = displayLayout.mWidth - width;
                    int i4 = displayLayout.mHeight - height;
                    int i5 = width2 != 0 ? (bounds.left * i3) / width2 : i3 / 2;
                    int i6 = height2 != 0 ? (bounds.top * i4) / height2 : i4 / 2;
                    if (i3 < 0 || i4 < 0) {
                        initialBounds = getInitialBounds(displayLayout, runningTaskInfo, i);
                    } else {
                        initialBounds = new Rect(0, 0, width, height);
                        initialBounds.offsetTo(RangesKt___RangesKt.coerceIn(i5, 0, i3), RangesKt___RangesKt.coerceIn(i6, 0, i4));
                    }
                    windowContainerTransaction2.setBounds(runningTaskInfo.token, initialBounds);
                }
            }
        } else {
            addMoveToDeskTaskChanges(windowContainerTransaction2, runningTaskInfo, defaultDeskId.intValue(), null);
        }
        DesktopExperienceFlags desktopExperienceFlags = DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND;
        if (desktopExperienceFlags.isTrue()) {
            z = true;
        } else {
            z = true;
            windowContainerTransaction2.reparent(runningTaskInfo.token, displayAreaInfo.token, true);
        }
        Function1 addDeskActivationChanges$default = addDeskActivationChanges$default(this, defaultDeskId.intValue(), windowContainerTransaction2, runningTaskInfo, 0, 0, 56);
        windowContainerTransaction2.reorder(runningTaskInfo.token, z, z);
        int i7 = runningTaskInfo.displayId;
        Integer deskIdForTask = this.taskRepository.getDeskIdForTask(runningTaskInfo.taskId);
        if (desktopExperienceFlags.isTrue() && willExitDesktop(runningTaskInfo.taskId, false)) {
            windowContainerTransaction = windowContainerTransaction2;
            desktopTasksController$$ExternalSyntheticLambda5 = performDesktopExitCleanUp$default(this, windowContainerTransaction, deskIdForTask, i7, true, false, 32);
        } else {
            windowContainerTransaction = windowContainerTransaction2;
            desktopTasksController$$ExternalSyntheticLambda5 = null;
        }
        IBinder startTransition = transitions.startTransition(6, windowContainerTransaction, transitionHandler == null ? this.moveToDisplayTransitionHandler : transitionHandler);
        if (desktopTasksController$$ExternalSyntheticLambda5 != null) {
            startTransition.getClass();
            desktopTasksController$$ExternalSyntheticLambda5.mo779invoke(startTransition);
        }
        startTransition.getClass();
        addDeskActivationChanges$default.mo779invoke(startTransition);
        SplitScreenController splitScreenController = this.splitScreenController;
        if (splitScreenController == null) {
            splitScreenController = null;
        }
        if (splitScreenController.isTaskInSplitScreen$1(runningTaskInfo.taskId)) {
            SplitScreenController splitScreenController2 = this.splitScreenController;
            (splitScreenController2 == null ? null : splitScreenController2).dismissSplitTask(runningTaskInfo.token);
        }
    }

    public final void moveToFullscreen(int i, DesktopModeTransitionSource desktopModeTransitionSource) {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(i);
        if (runningTaskInfo != null) {
            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.snapEventHandler;
            if (desktopModeWindowDecorViewModel == null) {
                desktopModeWindowDecorViewModel = null;
            }
            desktopModeWindowDecorViewModel.removeTaskIfTiled(runningTaskInfo.displayId, i);
            moveToFullscreenWithAnimation(runningTaskInfo, runningTaskInfo.positionInParent, desktopModeTransitionSource, null);
        }
    }

    public final void moveToFullscreenWithAnimation(ActivityManager.RunningTaskInfo runningTaskInfo, Point point, DesktopModeTransitionSource desktopModeTransitionSource, Rect rect) {
        DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1;
        DesktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 = this.exitDesktopModeListener;
        if (desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 != null) {
            desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1.onExitDesktopModeStarted();
        }
        logV$1("moveToFullscreenWithAnimation taskId=%d", Integer.valueOf(runningTaskInfo.taskId));
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        DesktopTasksController$$ExternalSyntheticLambda5 addMoveToFullscreenChanges = addMoveToFullscreenChanges(windowContainerTransaction, runningTaskInfo, willExitDesktop(runningTaskInfo.taskId, true));
        if (CoreRune.DW_SHELL_CHANGE_TRANSITION) {
            windowContainerTransaction.setChangeTransitMode(runningTaskInfo.token, 1, "moveToFullscreenWithAnimation(" + desktopModeTransitionSource + ")");
            if (rect != null) {
                windowContainerTransaction.setChangeTransitStartBounds(runningTaskInfo.token, rect);
            }
        }
        if (!forceEnterDesktop(runningTaskInfo.displayId)) {
            moveHomeTaskToTop(windowContainerTransaction, runningTaskInfo.displayId);
            windowContainerTransaction.reorder(runningTaskInfo.token, true);
        }
        ExitDesktopTaskTransitionHandler exitDesktopTaskTransitionHandler = this.exitDesktopTaskTransitionHandler;
        exitDesktopTaskTransitionHandler.mLatencyTracker.onActionStart(32);
        exitDesktopTaskTransitionHandler.mPosition = point;
        exitDesktopTaskTransitionHandler.mOnAnimationFinishedCallback = this.mOnAnimationFinishedCallback;
        int i = DesktopModeTransitionTypes.$r8$clinit;
        int i2 = DesktopModeTransitionTypes.WhenMappings.$EnumSwitchMapping$0[desktopModeTransitionSource.ordinal()];
        IBinder startTransition = exitDesktopTaskTransitionHandler.mTransitions.startTransition(i2 != 1 ? i2 != 3 ? i2 != 4 ? VolteConstants.ErrorCode.CALL_REJECT_REASON_USR_BUSY_CS_CALL : VolteConstants.ErrorCode.CALL_STATUS_CONF_ADD_USER_TO_SESSION_FAILURE : VolteConstants.ErrorCode.CALL_END_CALL_NW_HANDOVER : VolteConstants.ErrorCode.CALL_STATUS_CONF_REMOVE_USER_FROM_SESSION_FAILURE, windowContainerTransaction, exitDesktopTaskTransitionHandler);
        ((ArrayList) exitDesktopTaskTransitionHandler.mPendingTransitionTokens).add(startTransition);
        if (addMoveToFullscreenChanges != null) {
            startTransition.getClass();
            addMoveToFullscreenChanges.mo779invoke(startTransition);
        }
        if (this.taskRepository.isOnlyVisibleNonClosingTask(runningTaskInfo.taskId, -1) || DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue() || (desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 = this.desktopModeEnterExitTransitionListener) == null) {
            return;
        }
        desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1.onExitDesktopModeTransitionStarted();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.lang.Object] */
    public final void moveToNextDisplay(int i) {
        Object obj;
        Integer num;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(i);
        if (runningTaskInfo == null) {
            logW("moveToNextDisplay: taskId=%d not found", Integer.valueOf(i));
            return;
        }
        logV$1("moveToNextDisplay: taskId=%d displayId=%d", Integer.valueOf(i), Integer.valueOf(runningTaskInfo.displayId));
        Integer[] typedArray = ArraysKt___ArraysJvmKt.toTypedArray(this.rootTaskDisplayAreaOrganizer.getDisplayIds());
        Integer[] numArr = typedArray;
        if (numArr.length > 1) {
            Arrays.sort(numArr);
        }
        List asList = Arrays.asList(typedArray);
        Iterator it = asList.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (((Number) obj).intValue() > runningTaskInfo.displayId) {
                    break;
                }
            }
        }
        Integer num2 = (Integer) obj;
        if (num2 == null) {
            Iterator it2 = asList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    num = 0;
                    break;
                } else {
                    num = it2.next();
                    if (((Number) num).intValue() < runningTaskInfo.displayId) {
                        break;
                    }
                }
            }
            num2 = num;
        }
        if (num2 == null) {
            logW("moveToNextDisplay: next display not found", new Object[0]);
        } else {
            moveToDisplay(runningTaskInfo, num2.intValue(), null, null);
        }
    }

    public final void onDefaultDisplayDesktopModeChanged(final boolean z) {
        Executor executor;
        final DefaultDisplayDesktopModeChangeListener defaultDisplayDesktopModeChangeListener = this.defaultDisplayDesktopModeChangeListener;
        if (defaultDisplayDesktopModeChangeListener == null || (executor = this.defaultDisplayDesktopModeChangeListenerExecutor) == null) {
            return;
        }
        executor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$onDefaultDisplayDesktopModeChanged$1$1
            @Override // java.lang.Runnable
            public final void run() {
                DesktopTasksController.DefaultDisplayDesktopModeChangeListener.this.onDefaultDisplayDesktopModeChanged(z);
            }
        });
    }

    @Override // com.android.wm.shell.draganddrop.DragAndDropController.DragAndDropListener
    public final boolean onUnhandledDrag(PendingIntent pendingIntent, int i, DragEvent dragEvent, GlobalDragListener$onUnhandledDrop$1 globalDragListener$onUnhandledDrop$1) {
        int i2;
        if (isDesktopModeShowing(0)) {
            MultiInstanceHelper.Companion.getClass();
            Intent intent = pendingIntent.getIntent();
            if (!this.multiInstanceHelper.supportsMultiInstanceSplit(i, intent != null ? intent.getComponent() : null)) {
                logV$1("Dropped intent does not support multi-instance", new Object[0]);
                return false;
            }
            ActivityManager.RunningTaskInfo focusedFreeformTask = getFocusedFreeformTask(0);
            if (focusedFreeformTask != null) {
                DesktopModeVisualIndicator.IndicatorType updateVisualIndicator = updateVisualIndicator(focusedFreeformTask, dragEvent.getDragSurface(), dragEvent.getX(), dragEvent.getY(), DesktopModeVisualIndicator.DragStartState.DRAGGED_INTENT, false, true);
                releaseVisualIndicator();
                int[] iArr = WhenMappings.$EnumSwitchMapping$1;
                int i3 = iArr[updateVisualIndicator.ordinal()];
                if (i3 == 1) {
                    i2 = 1;
                } else {
                    if (i3 != 2 && i3 != 3 && i3 != 7 && i3 != 8) {
                        throw new IllegalStateException(("Invalid indicator type: " + updateVisualIndicator).toString());
                    }
                    i2 = 5;
                }
                DisplayLayout displayLayout = this.displayController.getDisplayLayout(0);
                if (displayLayout != null) {
                    Rect rect = new Rect();
                    int i4 = iArr[updateVisualIndicator.ordinal()];
                    if (i4 == 2) {
                        rect.set(getSnapBounds(focusedFreeformTask, SnapPosition.LEFT));
                    } else if (i4 == 3) {
                        rect.set(getSnapBounds(focusedFreeformTask, SnapPosition.RIGHT));
                    } else if (i4 == 7) {
                        rect.set(DesktopModeUtils.calculateDefaultDesktopTaskBounds(displayLayout));
                        rect.offsetTo(((int) dragEvent.getX()) - (rect.width() / 2), (int) dragEvent.getY());
                    } else if (i4 == 8) {
                        rect.set(DesktopModeUtils.calculateMaximizeBounds(displayLayout, focusedFreeformTask));
                    }
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setLaunchWindowingMode(i2);
                    makeBasic.setLaunchBounds(rect);
                    makeBasic.setPendingIntentBackgroundActivityStartMode(3);
                    makeBasic.setPendingIntentLaunchFlags(402653184);
                    makeBasic.setSplashScreenStyle(1);
                    if (i2 == 1) {
                        Binder binder = new Binder();
                        this.dragAndDropFullscreenCookie = binder;
                        makeBasic.setLaunchCookie(binder);
                    }
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    windowContainerTransaction.sendPendingIntent(pendingIntent, (Intent) null, makeBasic.toBundle());
                    if (i2 != 5) {
                        this.transitions.startTransition(1, windowContainerTransaction, null);
                    } else if (DesktopModeFlags.ENABLE_DESKTOP_TAB_TEARING_MINIMIZE_ANIMATION_BUGFIX.isTrue()) {
                        Integer orCreateDefaultDeskId = getOrCreateDefaultDeskId(0, false);
                        if (orCreateDefaultDeskId != null) {
                            startLaunchTransition$default(this, windowContainerTransaction, orCreateDefaultDeskId.intValue(), 0);
                        }
                    } else {
                        DesktopModeDragAndDropTransitionHandler desktopModeDragAndDropTransitionHandler = this.desktopModeDragAndDropTransitionHandler;
                        IBinder startTransition = desktopModeDragAndDropTransitionHandler.transitions.startTransition(1, windowContainerTransaction, desktopModeDragAndDropTransitionHandler);
                        List list = desktopModeDragAndDropTransitionHandler.pendingTransitionTokens;
                        startTransition.getClass();
                        ((ArrayList) list).add(startTransition);
                    }
                    globalDragListener$onUnhandledDrop$1.accept(Boolean.TRUE);
                    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    transaction.remove(dragEvent.getDragSurface());
                    transaction.apply();
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.android.wm.shell.sysui.UserChangeListener
    public final void onUserChanged(int i, Context context) {
        logV$1("onUserChanged previousUserId=%d, newUserId=%d", Integer.valueOf(this.userId), Integer.valueOf(i));
        updateCurrentUser(i);
    }

    public final DesktopTasksController$$ExternalSyntheticLambda5 performDesktopExitCleanUp(WindowContainerTransaction windowContainerTransaction, Integer num, int i, boolean z, boolean z2, boolean z3) {
        if (!z) {
            return null;
        }
        DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 = this.desktopModeEnterExitTransitionListener;
        if (desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 != null) {
            desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1.onExitDesktopModeTransitionStarted();
        }
        if (!z3 || !DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            boolean isTrue = DesktopModeFlags.ENABLE_DESKTOP_WALLPAPER_ACTIVITY_FOR_SYSTEM_USER.isTrue();
            DesktopWallpaperActivityTokenProvider desktopWallpaperActivityTokenProvider = this.desktopWallpaperActivityTokenProvider;
            if (isTrue) {
                WindowContainerToken token = desktopWallpaperActivityTokenProvider.getToken(i);
                if (token != null) {
                    logV$1("moveWallpaperActivityToBack", new Object[0]);
                    windowContainerTransaction.reorder(token, false);
                }
            } else {
                WindowContainerToken token2 = desktopWallpaperActivityTokenProvider.getToken(i);
                if (token2 != null) {
                    logV$1("removeWallpaperTask", new Object[0]);
                    windowContainerTransaction.removeTask(token2);
                }
            }
            if (z2) {
                this.homeIntentProvider.addLaunchHomePendingIntent(windowContainerTransaction, i, Integer.valueOf(this.userId));
            }
        }
        return prepareDeskDeactivationIfNeeded(windowContainerTransaction, num);
    }

    public final DesktopTasksController$$ExternalSyntheticLambda5 prepareDeskDeactivationIfNeeded(WindowContainerTransaction windowContainerTransaction, Integer num) {
        if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue() || num == null) {
            return null;
        }
        int intValue = num.intValue();
        RootTaskDesksOrganizer rootTaskDesksOrganizer = (RootTaskDesksOrganizer) this.desksOrganizer;
        rootTaskDesksOrganizer.getClass();
        RootTaskDesksOrganizer.logV$2("deactivateDesk %d", num);
        rootTaskDesksOrganizer.updateLaunchRoot(intValue, windowContainerTransaction, false);
        DesktopRepository desktopRepository = this.taskRepository;
        if (desktopRepository.desktopData.getDisplayForDesk(num.intValue()) == 0) {
            DesktopStateImpl.Companion.getClass();
            DesktopStateImpl.Companion.setInDesktopWindowing(false);
            onDefaultDisplayDesktopModeChanged(false);
        } else {
            DesktopStateImpl.Companion.getClass();
            DesktopStateImpl.desktopExternalDisplayId = -1;
        }
        return new DesktopTasksController$$ExternalSyntheticLambda5(this, num);
    }

    public final void prepareForDeskActivation(WindowContainerTransaction windowContainerTransaction, int i) {
        if (i == 0) {
            ArrayList runningTasks = this.shellTaskOrganizer.getRunningTasks(i);
            ArrayList arrayList = new ArrayList();
            int size = runningTasks.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = runningTasks.get(i2);
                i2++;
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
                if (runningTaskInfo.getWindowingMode() == 5 && runningTaskInfo.configuration.windowConfiguration.isAlwaysOnTop()) {
                    arrayList.add(obj);
                }
            }
            int size2 = arrayList.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList.get(i3);
                i3++;
                windowContainerTransaction.reorder(((ActivityManager.RunningTaskInfo) obj2).token, false);
            }
        }
        moveHomeTaskToTop(windowContainerTransaction, !DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue() ? this.context.getDisplayId() : i);
        if (i == 0 && DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_WALLPAPER_ACTIVITY.isTrue()) {
            logV$1("addWallpaperActivity", new Object[0]);
            boolean isTrue = DesktopModeFlags.ENABLE_DESKTOP_WALLPAPER_ACTIVITY_FOR_SYSTEM_USER.isTrue();
            DesktopWallpaperActivityTokenProvider desktopWallpaperActivityTokenProvider = this.desktopWallpaperActivityTokenProvider;
            if (isTrue) {
                WindowContainerToken token = desktopWallpaperActivityTokenProvider.getToken(i);
                if (token != null) {
                    windowContainerTransaction.reorder(token, true);
                    return;
                }
                Intent intent = new Intent(this.context, (Class<?>) DesktopWallpaperActivity.class);
                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                makeBasic.setLaunchWindowingMode(1);
                makeBasic.setPendingIntentBackgroundActivityStartMode(3);
                windowContainerTransaction.sendPendingIntent(PendingIntent.getActivity(this.context, 0, intent, 67108864), intent, makeBasic.toBundle());
                return;
            }
            UserHandle of = UserHandle.of(this.userId);
            Context createContextAsUser = this.context.createContextAsUser(of, 0);
            Intent intent2 = new Intent(createContextAsUser, (Class<?>) DesktopWallpaperActivity.class);
            desktopWallpaperActivityTokenProvider.getToken(i);
            intent2.putExtra("android.intent.extra.user_handle", this.userId);
            ActivityOptions makeBasic2 = ActivityOptions.makeBasic();
            makeBasic2.setLaunchWindowingMode(1);
            makeBasic2.setPendingIntentBackgroundActivityStartMode(3);
            windowContainerTransaction.sendPendingIntent(PendingIntent.getActivityAsUser(createContextAsUser, 0, intent2, 67108864, null, of), intent2, makeBasic2.toBundle());
        }
    }

    public final void releaseVisualIndicator() {
        DesktopModeVisualIndicator desktopModeVisualIndicator = this.visualIndicator;
        if (desktopModeVisualIndicator != null) {
            final VisualIndicatorViewContainer visualIndicatorViewContainer = desktopModeVisualIndicator.mVisualIndicatorViewContainer;
            if (!visualIndicatorViewContainer.isReleased) {
                visualIndicatorViewContainer.desktopExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$releaseVisualIndicator$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceControlViewHost surfaceControlViewHost = VisualIndicatorViewContainer.this.indicatorViewHost;
                        if (surfaceControlViewHost != null) {
                            surfaceControlViewHost.release();
                        }
                        VisualIndicatorViewContainer.this.indicatorViewHost = null;
                    }
                });
                SurfaceControl surfaceControl = visualIndicatorViewContainer.indicatorLeash;
                if (surfaceControl != null) {
                    final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    transaction.remove(surfaceControl);
                    visualIndicatorViewContainer.indicatorLeash = null;
                    visualIndicatorViewContainer.syncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$releaseVisualIndicator$2$1
                        @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                        public final void runWithTransaction(SurfaceControl.Transaction transaction2) {
                            transaction2.merge(transaction);
                            transaction.close();
                        }
                    });
                }
                visualIndicatorViewContainer.isReleased = true;
            }
        }
        this.visualIndicator = null;
    }

    public final void removeAllTasksInDesk(int i) {
        logV$1("removeAllTasksInDesk from deskId=%d", Integer.valueOf(i));
        DesksTransitionObserver desksTransitionObserver = this.desksTransitionObserver;
        if (!desksTransitionObserver.deskTransitions.isEmpty()) {
            Map map = desksTransitionObserver.deskTransitions;
            if (!map.isEmpty()) {
                Iterator it = ((LinkedHashMap) map).entrySet().iterator();
                while (it.hasNext()) {
                    Iterable<DeskTransition> iterable = (Iterable) ((Map.Entry) it.next()).getValue();
                    if (!(iterable instanceof Collection) || !((Collection) iterable).isEmpty()) {
                        for (DeskTransition deskTransition : iterable) {
                            if ((deskTransition instanceof DeskTransition.ActivateDesk) || (deskTransition instanceof DeskTransition.ActiveDeskWithTask)) {
                                logW("abort removeAllTasksInDesk during activate transition", new Object[0]);
                                return;
                            }
                        }
                    }
                }
            }
        }
        RootTaskDesksOrganizer rootTaskDesksOrganizer = (RootTaskDesksOrganizer) this.desksOrganizer;
        if (rootTaskDesksOrganizer.deskRootsByDeskId.contains(i)) {
            RecentTasksController recentTasksController = this.recentTasksController;
            if (recentTasksController != null) {
                ActivityTaskManager.getService().removeAllTasksInRootTask(i);
            }
            RootTaskDesksOrganizer.DeskMinimizationRoot deskMinimizationRoot = (RootTaskDesksOrganizer.DeskMinimizationRoot) ((LinkedHashMap) rootTaskDesksOrganizer.deskMinimizationRootsByDeskId).get(Integer.valueOf(i));
            Integer valueOf = deskMinimizationRoot != null ? Integer.valueOf(deskMinimizationRoot.taskInfo.taskId) : null;
            if (valueOf != null) {
                int intValue = valueOf.intValue();
                if (recentTasksController != null) {
                    ActivityTaskManager.getService().removeAllTasksInRootTask(intValue);
                }
            }
        }
    }

    public final void removeDesk(int i, int i2, DesktopRepository desktopRepository) {
        int i3;
        int i4;
        Integer activeDeskId;
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_BACK_NAVIGATION.isTrue()) {
            logV$1("removeDesk deskId=%d from displayId=%d", Integer.valueOf(i2), Integer.valueOf(i));
            Set activeTaskIdsInDesk = DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue() ? desktopRepository.getActiveTaskIdsInDesk(i2) : desktopRepository.removeDesk(i2, true);
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            Iterator it = activeTaskIdsInDesk.iterator();
            while (it.hasNext()) {
                int intValue = ((Number) it.next()).intValue();
                ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(intValue);
                if (runningTaskInfo != null) {
                    windowContainerTransaction.removeTask(runningTaskInfo.token);
                } else {
                    RecentTasksController recentTasksController = this.recentTasksController;
                    if (recentTasksController != null) {
                        recentTasksController.mActivityTaskManager.removeTask(intValue);
                    }
                }
            }
            DesktopExperienceFlags desktopExperienceFlags = DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND;
            if (desktopExperienceFlags.isTrue()) {
                int i5 = this.userId;
                RootTaskDesksOrganizer rootTaskDesksOrganizer = (RootTaskDesksOrganizer) this.desksOrganizer;
                Object[] objArr = {Integer.valueOf(i2), Integer.valueOf(i5)};
                rootTaskDesksOrganizer.getClass();
                RootTaskDesksOrganizer.logV$2("removeDesk %d for userId=%d", objArr);
                RootTaskDesksOrganizer.DeskRoot deskRoot = (RootTaskDesksOrganizer.DeskRoot) rootTaskDesksOrganizer.deskRootsByDeskId.get(i2);
                if (deskRoot == null) {
                    Object[] objArr2 = {Integer.valueOf(i2)};
                    ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                    SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "RootTaskDesksOrganizer", objArr2);
                    ProtoLog.w(shellProtoLogGroup, "%s: removeDesk attempted to remove non-existent desk=%d", m.list.toArray(new Object[m.list.size()]));
                } else {
                    rootTaskDesksOrganizer.updateLaunchRoot(i2, windowContainerTransaction, false);
                    deskRoot.users.remove(Integer.valueOf(i5));
                    if (deskRoot.users.isEmpty()) {
                        RootTaskDesksOrganizer.logD$2("removeDesk %d is no longer used by any users, removing it completely", Integer.valueOf(i2));
                        rootTaskDesksOrganizer.removeDeskRootRequests.add(Integer.valueOf(i2));
                        windowContainerTransaction.removeRootTask(deskRoot.token);
                        RootTaskDesksOrganizer.DeskMinimizationRoot deskMinimizationRoot = (RootTaskDesksOrganizer.DeskMinimizationRoot) ((LinkedHashMap) rootTaskDesksOrganizer.deskMinimizationRootsByDeskId).get(Integer.valueOf(i2));
                        if (deskMinimizationRoot != null) {
                            windowContainerTransaction.removeRootTask(deskMinimizationRoot.token);
                        }
                    }
                }
            }
            if (desktopExperienceFlags.isTrue() || !windowContainerTransaction.isEmpty()) {
                IBinder startTransition = this.transitions.startTransition(2, windowContainerTransaction, null);
                if (desktopExperienceFlags.isTrue()) {
                    startTransition.getClass();
                    i3 = i;
                    i4 = i2;
                    this.desksTransitionObserver.addPendingTransition(new DeskTransition.RemoveDesk(startTransition, i3, i4, activeTaskIdsInDesk, this.onDeskRemovedListener));
                } else {
                    i3 = i;
                    i4 = i2;
                }
                if (i3 != 0 || (activeDeskId = this.taskRepository.getActiveDeskId(i3)) == null) {
                    return;
                }
                if ((activeDeskId.intValue() == i4 ? activeDeskId : null) != null) {
                    DesktopStateImpl.Companion.getClass();
                    DesktopStateImpl.Companion.setInDesktopWindowing(false);
                    onDefaultDisplayDesktopModeChanged(false);
                }
            }
        }
    }

    public final void requestFloat(ActivityManager.RunningTaskInfo runningTaskInfo, Boolean bool) {
        DragToDesktopTransitionHandler dragToDesktopTransitionHandler = this.dragToDesktopTransitionHandler;
        boolean inProgress$1 = dragToDesktopTransitionHandler.getInProgress$1();
        if (TaskInfoKt.isFullscreen(runningTaskInfo) || runningTaskInfo.isFreeform() || inProgress$1 || TaskInfoKt.isMultiWindow(runningTaskInfo)) {
            if (!inProgress$1) {
                this.bubbleController.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new DesktopTasksController$$ExternalSyntheticLambda10(1)));
            } else {
                releaseVisualIndicator();
                dragToDesktopTransitionHandler.cancelDragToDesktopTransition(Intrinsics.areEqual(bool, Boolean.TRUE) ? DragToDesktopTransitionHandler.CancelState.CANCEL_BUBBLE_LEFT : DragToDesktopTransitionHandler.CancelState.CANCEL_BUBBLE_RIGHT);
            }
        }
    }

    public final void requestSplit(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        DesktopTasksController desktopTasksController;
        DesktopTasksController$$ExternalSyntheticLambda5 performDesktopExitCleanUp$default;
        DragToDesktopTransitionHandler dragToDesktopTransitionHandler = this.dragToDesktopTransitionHandler;
        boolean inProgress$1 = dragToDesktopTransitionHandler.getInProgress$1();
        if (TaskInfoKt.isFullscreen(runningTaskInfo) || runningTaskInfo.isFreeform() || inProgress$1) {
            if (inProgress$1) {
                releaseVisualIndicator();
                dragToDesktopTransitionHandler.cancelDragToDesktopTransition(z ? DragToDesktopTransitionHandler.CancelState.CANCEL_SPLIT_LEFT : DragToDesktopTransitionHandler.CancelState.CANCEL_SPLIT_RIGHT);
                return;
            }
            Integer deskIdForTask = this.taskRepository.getDeskIdForTask(runningTaskInfo.taskId);
            logV$1("Split requested for task=%d in desk=%d", Integer.valueOf(runningTaskInfo.taskId), deskIdForTask);
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            if (!DesktopModeFlags.ENABLE_INPUT_LAYER_TRANSITION_FIX.isTrue()) {
                windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 6);
            }
            windowContainerTransaction.setDensityDpi(runningTaskInfo.token, this.context.getResources().getDisplayMetrics().densityDpi);
            int i = runningTaskInfo.taskId;
            int i2 = runningTaskInfo.displayId;
            if (willExitDesktop(i, true)) {
                desktopTasksController = this;
                performDesktopExitCleanUp$default = performDesktopExitCleanUp$default(desktopTasksController, windowContainerTransaction, deskIdForTask, i2, true, false, 32);
            } else {
                desktopTasksController = this;
                performDesktopExitCleanUp$default = null;
            }
            SplitScreenController splitScreenController = desktopTasksController.splitScreenController;
            IBinder requestEnterSplitSelect = (splitScreenController != null ? splitScreenController : null).requestEnterSplitSelect(!z ? 1 : 0, runningTaskInfo, runningTaskInfo.configuration.windowConfiguration.getBounds(), windowContainerTransaction);
            if (requestEnterSplitSelect == null || performDesktopExitCleanUp$default == null) {
                return;
            }
            performDesktopExitCleanUp$default.mo779invoke(requestEnterSplitSelect);
        }
    }

    public final boolean shouldFullscreenTaskLaunchSwitchToDesktop(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (isDesktopModeShowing(runningTaskInfo.displayId)) {
            DesktopStateImpl.Companion companion = DesktopStateImpl.Companion;
            int i = runningTaskInfo.displayId;
            companion.getClass();
            if (DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                return true;
            }
        }
        return forceEnterDesktop(runningTaskInfo.displayId);
    }

    public final void snapToHalfScreen(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Rect rect, SnapPosition snapPosition, DesktopModeEventLogger.Companion.ResizeTrigger resizeTrigger, DesktopModeEventLogger.Companion.InputMethod inputMethod) {
        DesktopTilingWindowDecoration desktopTilingWindowDecoration;
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        int i;
        DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1;
        ActivityManager.RunningTaskInfo runningTaskInfo3;
        Integer valueOf = Integer.valueOf(rect.width());
        Integer valueOf2 = Integer.valueOf(rect.height());
        DesktopModeEventLogger.Companion companion = DesktopModeEventLogger.Companion;
        this.desktopModeEventLogger.logTaskResizingStarted(resizeTrigger, inputMethod, runningTaskInfo, valueOf, valueOf2, this.displayController, null);
        Rect snapBounds = getSnapBounds(runningTaskInfo, snapPosition);
        this.desktopModeEventLogger.logTaskResizingEnded(resizeTrigger, inputMethod, runningTaskInfo, Integer.valueOf(snapBounds.width()), Integer.valueOf(snapBounds.height()), this.displayController, null);
        if (!DesktopModeFlags.ENABLE_TILE_RESIZING.isTrue()) {
            if (snapBounds.equals(runningTaskInfo.configuration.windowConfiguration.getBounds())) {
                if (snapBounds.equals(rect) || surfaceControl == null) {
                    return;
                }
                int i2 = runningTaskInfo.taskId;
                int i3 = ReturnToDragStartAnimator.$r8$clinit;
                this.returnToDragStartAnimator.start(i2, surfaceControl, rect, snapBounds, null);
                return;
            }
            DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$12 = this.taskbarDesktopTaskListener;
            if (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$12 != null) {
                desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$12.onTaskbarCornerRoundingUpdate(true);
            }
            WindowContainerTransaction bounds = new WindowContainerTransaction().setBounds(runningTaskInfo.token, snapBounds);
            if (CoreRune.DW_SHELL_CHANGE_TRANSITION) {
                bounds.setChangeTransitStartBounds(runningTaskInfo.token, rect);
                bounds.setChangeTransitMode(runningTaskInfo.token, 1, "snapToHalfScreen");
            }
            ToggleResizeDesktopTaskTransitionHandler.startTransition$default(this.toggleResizeDesktopTaskTransitionHandler, bounds, rect, 4);
            return;
        }
        DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.snapEventHandler;
        if (desktopModeWindowDecorViewModel == null) {
            desktopModeWindowDecorViewModel = null;
        }
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        DesktopTilingDecorViewModel desktopTilingDecorViewModel = desktopModeWindowDecorViewModel.mDesktopTilingDecorViewModel;
        desktopTilingDecorViewModel.getClass();
        int i4 = runningTaskInfo.displayId;
        DesktopTilingWindowDecoration desktopTilingWindowDecoration2 = (DesktopTilingWindowDecoration) desktopTilingDecorViewModel.tilingTransitionHandlerByDisplayId.get(i4);
        if (desktopTilingWindowDecoration2 == null) {
            desktopTilingWindowDecoration = new DesktopTilingWindowDecoration(desktopTilingDecorViewModel.context, desktopTilingDecorViewModel.mainDispatcher, desktopTilingDecorViewModel.bgScope, desktopTilingDecorViewModel.syncQueue, desktopTilingDecorViewModel.displayController, desktopTilingDecorViewModel.taskResourceLoader, i4, desktopTilingDecorViewModel.rootTdaOrganizer, desktopTilingDecorViewModel.transitions, desktopTilingDecorViewModel.shellTaskOrganizer, desktopTilingDecorViewModel.toggleResizeDesktopTaskTransitionHandler, desktopTilingDecorViewModel.returnToDragStartAnimator, desktopTilingDecorViewModel.desktopUserRepositories, desktopTilingDecorViewModel.desktopModeEventLogger, desktopTilingDecorViewModel.focusTransitionObserver, desktopTilingDecorViewModel.mainExecutor, desktopTilingDecorViewModel.desktopState, null, 131072, null);
            desktopTilingDecorViewModel.tilingTransitionHandlerByDisplayId.put(i4, desktopTilingWindowDecoration);
        } else {
            desktopTilingWindowDecoration = desktopTilingWindowDecoration2;
        }
        desktopTilingDecorViewModel.transitions.registerObserver(desktopTilingWindowDecoration);
        Rect snapBounds2 = desktopTilingWindowDecoration.getSnapBounds(snapPosition);
        DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper = new DesktopTilingWindowDecoration.AppResizingHelper(runningTaskInfo, desktopModeWindowDecoration, desktopTilingWindowDecoration.context, snapBounds2, desktopTilingWindowDecoration.displayController, desktopTilingWindowDecoration.taskResourceLoader, desktopTilingWindowDecoration.mainDispatcher, desktopTilingWindowDecoration.bgScope, desktopTilingWindowDecoration.transactionSupplier);
        boolean z = desktopTilingWindowDecoration.leftTaskResizingHelper == null && desktopTilingWindowDecoration.rightTaskResizingHelper == null;
        boolean equals = snapBounds2.equals(runningTaskInfo.configuration.windowConfiguration.getBounds());
        int[] iArr = DesktopTilingWindowDecoration.WhenMappings.$EnumSwitchMapping$0;
        int i5 = iArr[snapPosition.ordinal()];
        if (i5 == 1) {
            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper2 = desktopTilingWindowDecoration.leftTaskResizingHelper;
            if (appResizingHelper2 != null) {
                desktopTilingWindowDecoration.removeTaskIfTiled(appResizingHelper2.taskInfo.taskId, false, false);
            }
            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper3 = desktopTilingWindowDecoration.rightTaskResizingHelper;
            if (appResizingHelper3 != null && (runningTaskInfo2 = appResizingHelper3.taskInfo) != null && (i = runningTaskInfo.taskId) == runningTaskInfo2.taskId) {
                desktopTilingWindowDecoration.removeTaskIfTiled(i, false, false);
            }
            desktopTilingWindowDecoration.leftTaskResizingHelper = appResizingHelper;
        } else {
            if (i5 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper4 = desktopTilingWindowDecoration.rightTaskResizingHelper;
            if (appResizingHelper4 != null) {
                desktopTilingWindowDecoration.removeTaskIfTiled(appResizingHelper4.taskInfo.taskId, false, false);
            }
            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper5 = desktopTilingWindowDecoration.leftTaskResizingHelper;
            if (appResizingHelper5 != null && (runningTaskInfo3 = appResizingHelper5.taskInfo) != null) {
                int i6 = runningTaskInfo3.taskId;
                int i7 = runningTaskInfo.taskId;
                if (i6 == i7) {
                    desktopTilingWindowDecoration.removeTaskIfTiled(i7, false, false);
                }
            }
            desktopTilingWindowDecoration.rightTaskResizingHelper = appResizingHelper;
        }
        desktopTilingWindowDecoration.isDarkMode = (runningTaskInfo.configuration.uiMode & 48) == 32;
        desktopModeWindowDecoration.mTaskDragResizer.addDragEventListener(desktopTilingWindowDecoration);
        DesktopTilingWindowDecoration$$ExternalSyntheticLambda2 desktopTilingWindowDecoration$$ExternalSyntheticLambda2 = new DesktopTilingWindowDecoration$$ExternalSyntheticLambda2(desktopTilingWindowDecoration, runningTaskInfo, z);
        int i8 = runningTaskInfo.taskId;
        int i9 = iArr[snapPosition.ordinal()];
        int i10 = desktopTilingWindowDecoration.displayId;
        DesktopUserRepositories desktopUserRepositories = desktopTilingWindowDecoration.desktopUserRepositories;
        if (i9 == 1) {
            desktopUserRepositories.getCurrent().addLeftTiledTask(i10, i8);
        } else {
            if (i9 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            desktopUserRepositories.getCurrent().addRightTiledTask(i10, i8);
        }
        if (!equals) {
            WindowContainerTransaction bounds2 = new WindowContainerTransaction().setBounds(runningTaskInfo.token, snapBounds2);
            if (CoreRune.DW_SHELL_CHANGE_TRANSITION) {
                bounds2.setChangeTransitStartBounds(runningTaskInfo.token, rect);
                bounds2.setChangeTransitMode(runningTaskInfo.token, 1, "onAppTiled");
            }
            desktopTilingWindowDecoration.toggleResizeDesktopTaskTransitionHandler.startTransition(bounds2, rect, desktopTilingWindowDecoration$$ExternalSyntheticLambda2);
        } else if (snapBounds2.equals(rect)) {
            desktopTilingWindowDecoration$$ExternalSyntheticLambda2.invoke();
        } else {
            desktopTilingWindowDecoration.returnToDragStartAnimator.start(runningTaskInfo.taskId, appResizingHelper.desktopModeWindowDecoration.mTaskSurface, rect, snapBounds2, desktopTilingWindowDecoration$$ExternalSyntheticLambda2);
        }
        if (equals || (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 = this.taskbarDesktopTaskListener) == null) {
            return;
        }
        desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1.onTaskbarCornerRoundingUpdate(true);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        return false;
    }

    public final IBinder startLaunchTransition(int i, WindowContainerTransaction windowContainerTransaction, Integer num, RemoteTransition remoteTransition, int i2, int i3, DesktopModeEventLogger.Companion.UnminimizeReason unminimizeReason) {
        Function1 addDeskActivationChanges$default;
        IBinder startTransition;
        Function1 function1;
        WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction;
        logV$1("startLaunchTransition type=%s launchingTaskId=%d deskId=%d displayId=%d", WindowManager.transitTypeToString(i), num, Integer.valueOf(i2), Integer.valueOf(i3));
        Integer addAndGetMinimizeChanges = addAndGetMinimizeChanges(i2, windowContainerTransaction2, num, num == null);
        DesktopImmersiveController.ExitResult exitImmersiveIfApplicable = this.desktopImmersiveController.exitImmersiveIfApplicable(windowContainerTransaction2, i3, num, DesktopImmersiveController.ExitReason.TASK_LAUNCH);
        if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue() ? !(!DesktopExperienceFlags.ENABLE_DISPLAY_WINDOWING_MODE_SWITCHING.isTrue() || isDesktopModeShowing(i3)) : !this.taskRepository.isDeskActive(i2)) {
            addDeskActivationChanges$default = null;
        } else {
            WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
            addDeskActivationChanges$default = addDeskActivationChanges$default(this, i2, windowContainerTransaction3, null, 0, 0, 60);
            windowContainerTransaction3.merge(windowContainerTransaction2, true);
            DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 = this.desktopModeEnterExitTransitionListener;
            if (desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 != null) {
                desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1.onEnterDesktopModeTransitionStarted();
            }
            windowContainerTransaction2 = windowContainerTransaction3;
        }
        if (remoteTransition == null) {
            logV$1("startLaunchTransition -- no remoteTransition -- wct = " + windowContainerTransaction2, new Object[0]);
            DesktopImmersiveController.ExitResult.Exit asExit = exitImmersiveIfApplicable.asExit();
            Integer valueOf = asExit != null ? Integer.valueOf(asExit.exitingTask) : null;
            DesktopMixedTransitionHandler desktopMixedTransitionHandler = this.desktopMixedTransitionHandler;
            desktopMixedTransitionHandler.getClass();
            boolean isTrue = DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue();
            Transitions transitions = desktopMixedTransitionHandler.transitions;
            if (isTrue || DesktopModeFlags.ENABLE_DESKTOP_APP_LAUNCH_TRANSITIONS_BUGFIX.isTrue()) {
                if (valueOf == null) {
                    DesktopMixedTransitionHandler.logV$6("Starting mixed launch transition for task#%d", num);
                } else {
                    DesktopMixedTransitionHandler.logV$6("Starting mixed launch transition for task#%d with immersive exit of task#%d", num, valueOf);
                }
                IBinder startTransition2 = transitions.startTransition(i, windowContainerTransaction2, desktopMixedTransitionHandler);
                List list = desktopMixedTransitionHandler.pendingMixedTransitions;
                startTransition2.getClass();
                ((ArrayList) list).add(new DesktopMixedTransitionHandler.PendingMixedTransition.Launch(startTransition2, num, addAndGetMinimizeChanges, valueOf));
                startTransition = startTransition2;
            } else {
                startTransition = transitions.startTransition(i, windowContainerTransaction2, null);
            }
        } else {
            Transitions transitions2 = this.transitions;
            ShellExecutor shellExecutor = this.mainExecutor;
            if (addAndGetMinimizeChanges == null) {
                OneShotRemoteHandler oneShotRemoteHandler = new OneShotRemoteHandler(shellExecutor, remoteTransition);
                startTransition = transitions2.startTransition(i, windowContainerTransaction2, oneShotRemoteHandler);
                oneShotRemoteHandler.mTransition = startTransition;
            } else {
                DesktopWindowLimitRemoteHandler desktopWindowLimitRemoteHandler = new DesktopWindowLimitRemoteHandler(shellExecutor, this.rootTaskDisplayAreaOrganizer, remoteTransition, addAndGetMinimizeChanges.intValue());
                startTransition = transitions2.startTransition(i, windowContainerTransaction2, desktopWindowLimitRemoteHandler);
                startTransition.getClass();
                desktopWindowLimitRemoteHandler.transition = startTransition;
                desktopWindowLimitRemoteHandler.oneShotRemoteHandler.mTransition = startTransition;
            }
        }
        if (addAndGetMinimizeChanges != null) {
            startTransition.getClass();
            addPendingMinimizeTransition(startTransition, addAndGetMinimizeChanges.intValue(), DesktopModeEventLogger.Companion.MinimizeReason.TASK_LIMIT);
        }
        if (num != null && this.taskRepository.isMinimizedTask(num.intValue())) {
            startTransition.getClass();
            this.desktopTasksLimiter.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new DesktopTasksController$$ExternalSyntheticLambda6(startTransition, i3, num.intValue(), unminimizeReason, 0)));
        }
        if (addDeskActivationChanges$default != null) {
            startTransition.getClass();
            addDeskActivationChanges$default.mo779invoke(startTransition);
        }
        DesktopImmersiveController.ExitResult.Exit asExit2 = exitImmersiveIfApplicable.asExit();
        if (asExit2 != null && (function1 = asExit2.runOnTransitionStart) != null) {
            startTransition.getClass();
            function1.mo779invoke(startTransition);
        }
        startTransition.getClass();
        return startTransition;
    }

    public final void toggleDesktopTask(ActivityManager.RunningTaskInfo runningTaskInfo, ToggleTaskSizeInteraction.Direction direction) {
        boolean isTaskMaximized = DesktopModeUtils.isTaskMaximized(runningTaskInfo, this.displayController);
        if (!isTaskMaximized && direction == ToggleTaskSizeInteraction.Direction.RESTORE) {
            logD$1("toggleDesktopTask taskId##%d is not maximized state", Integer.valueOf(runningTaskInfo.taskId));
            return;
        }
        if (isTaskMaximized && direction == ToggleTaskSizeInteraction.Direction.MAXIMIZE) {
            logD$1("toggleDesktopTask taskId##%d is maximized state", Integer.valueOf(runningTaskInfo.taskId));
            return;
        }
        logV$1("restoreDesktopTask taskId=%d displayId=%d bounds=%d", Integer.valueOf(runningTaskInfo.taskId), Integer.valueOf(runningTaskInfo.displayId), runningTaskInfo.configuration.windowConfiguration.getBounds());
        toggleDesktopTaskSize(runningTaskInfo, new ToggleTaskSizeInteraction(direction, ToggleTaskSizeInteraction.Source.KEYBOARD_SHORTCUT, DesktopModeEventLogger.Companion.InputMethod.KEYBOARD, null, 8, null));
    }

    public final void toggleDesktopTaskSize(ActivityManager.RunningTaskInfo runningTaskInfo, ToggleTaskSizeInteraction toggleTaskSizeInteraction) {
        Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
        Integer valueOf = Integer.valueOf(bounds.width());
        Integer valueOf2 = Integer.valueOf(bounds.height());
        DesktopModeEventLogger.Companion companion = DesktopModeEventLogger.Companion;
        this.desktopModeEventLogger.logTaskResizingStarted(toggleTaskSizeInteraction.resizeTrigger, toggleTaskSizeInteraction.inputMethod, runningTaskInfo, valueOf, valueOf2, this.displayController, null);
        DisplayLayout displayLayout = this.displayController.getDisplayLayout(runningTaskInfo.displayId);
        if (displayLayout == null) {
            return;
        }
        Rect rect = new Rect();
        ToggleTaskSizeInteraction.Direction direction = ToggleTaskSizeInteraction.Direction.RESTORE;
        ToggleTaskSizeInteraction.Direction direction2 = toggleTaskSizeInteraction.direction;
        boolean z = direction2 == direction;
        boolean z2 = direction2 == ToggleTaskSizeInteraction.Direction.MAXIMIZE;
        if (z) {
            Rect rect2 = (Rect) this.taskRepository.boundsBeforeMaximizeByTaskId.removeReturnOld(runningTaskInfo.taskId);
            if (rect2 != null) {
                rect.set(rect2);
            } else if (DesktopModeFlags.ENABLE_WINDOWING_DYNAMIC_INITIAL_BOUNDS.isTrue()) {
                rect.set(DesktopModeUtils.calculateInitialBounds$default(displayLayout, runningTaskInfo, 0, null, 28));
            } else {
                rect.set(DesktopModeUtils.calculateDefaultDesktopTaskBounds(displayLayout));
            }
        } else {
            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.snapEventHandler;
            (desktopModeWindowDecorViewModel != null ? desktopModeWindowDecorViewModel : null).removeTaskIfTiled(runningTaskInfo.displayId, runningTaskInfo.taskId);
            this.taskRepository.boundsBeforeMaximizeByTaskId.set(runningTaskInfo.taskId, new Rect(bounds));
            rect.set(DesktopModeUtils.calculateMaximizeBounds(displayLayout, runningTaskInfo));
        }
        boolean z3 = z && (getSnapBounds(runningTaskInfo, SnapPosition.LEFT).equals(rect) || getSnapBounds(runningTaskInfo, SnapPosition.RIGHT).equals(rect));
        logD$1("willMaximize = %s", Boolean.valueOf(z2));
        logD$1("shouldRestoreToSnap = %s", Boolean.valueOf(z3));
        boolean z4 = z2 || z3 || doesAnyTaskRequireTaskbarRounding(runningTaskInfo.displayId, Integer.valueOf(runningTaskInfo.taskId));
        DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 = this.taskbarDesktopTaskListener;
        if (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 != null) {
            desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1.onTaskbarCornerRoundingUpdate(z4);
        }
        WindowContainerTransaction bounds2 = new WindowContainerTransaction().setBounds(runningTaskInfo.token, rect);
        DesktopModeUiEventLogger.DesktopUiEventEnum desktopUiEventEnum = toggleTaskSizeInteraction.uiEvent;
        if (desktopUiEventEnum != null) {
            this.desktopModeUiEventLogger.log(runningTaskInfo, desktopUiEventEnum);
        }
        if (CoreRune.DW_SHELL_CHANGE_TRANSITION) {
            bounds2.setChangeTransitMode(runningTaskInfo.token, 1, "toggle_desktop_task_size(maximize=" + z2 + ")");
        }
        this.desktopModeEventLogger.logTaskResizingEnded(toggleTaskSizeInteraction.resizeTrigger, toggleTaskSizeInteraction.inputMethod, runningTaskInfo, Integer.valueOf(rect.width()), Integer.valueOf(rect.height()), this.displayController, null);
        ToggleResizeDesktopTaskTransitionHandler.startTransition$default(this.toggleResizeDesktopTaskTransitionHandler, bounds2, toggleTaskSizeInteraction.animationStartBounds, 4);
    }

    public final void toggleExternalDisplay(int i) {
        int i2;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(i);
        if (runningTaskInfo == null) {
            logW("moveToConnectedDisplay: taskId=%d not found", Integer.valueOf(i));
            return;
        }
        logV$1("moveToConnectedDisplay: taskId=%d displayId=%d", Integer.valueOf(i), Integer.valueOf(runningTaskInfo.displayId));
        if (runningTaskInfo.displayId == 0) {
            DesktopStateImpl.Companion.getClass();
            i2 = DesktopStateImpl.desktopExternalDisplayId;
        } else {
            i2 = 0;
        }
        moveToDisplay(runningTaskInfo, i2, null, null);
    }

    public final void updateCurrentUser(int i) {
        this.userId = i;
        this.taskRepository = this.userRepositories.getProfile(i);
        DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.snapEventHandler;
        if (desktopModeWindowDecorViewModel != null) {
            SparseArrayKt$valueIterator$1 sparseArrayKt$valueIterator$1 = new SparseArrayKt$valueIterator$1(desktopModeWindowDecorViewModel.mDesktopTilingDecorViewModel.tilingTransitionHandlerByDisplayId);
            while (sparseArrayKt$valueIterator$1.hasNext()) {
                ((DesktopTilingWindowDecoration) sparseArrayKt$valueIterator$1.next()).resetTilingSession();
            }
        }
    }

    public final Function1 updateDesksActivationOnDisconnection(int i, int i2, WindowContainerTransaction windowContainerTransaction, boolean z) {
        return z ? addDeskActivationChanges$default(this, i, windowContainerTransaction, null, 0, i2, 28) : prepareDeskDeactivationIfNeeded(windowContainerTransaction, Integer.valueOf(i));
    }

    public final DesktopModeVisualIndicator.IndicatorType updateVisualIndicator(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, float f, float f2, DesktopModeVisualIndicator.DragStartState dragStartState, boolean z, boolean z2) {
        DesktopModeVisualIndicator.DragStartState dragStartState2;
        DesktopModeVisualIndicator desktopModeVisualIndicator;
        DesktopModeVisualIndicator desktopModeVisualIndicator2 = this.visualIndicator;
        if (desktopModeVisualIndicator2 != null) {
            dragStartState2 = dragStartState;
            if (desktopModeVisualIndicator2.mDragStartState != dragStartState2) {
                Slog.e("DesktopTasksController", "Visual indicator from previous motion event was never released");
                releaseVisualIndicator();
            }
        } else {
            dragStartState2 = dragStartState;
        }
        DesktopModeVisualIndicator desktopModeVisualIndicator3 = this.visualIndicator;
        if (desktopModeVisualIndicator3 == null) {
            int i = runningTaskInfo.displayId;
            DisplayController displayController = this.displayController;
            Context displayContext = displayController.getDisplayContext(i);
            BubbleController bubbleController = (BubbleController) this.bubbleController.orElse(null);
            BubblePositioner bubblePositioner = bubbleController != null ? bubbleController.mBubblePositioner : null;
            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.snapEventHandler;
            desktopModeVisualIndicator = new DesktopModeVisualIndicator(this.desktopExecutor, this.mainExecutor, this.syncQueue, runningTaskInfo, displayController, displayContext, surfaceControl, this.rootTaskDisplayAreaOrganizer, dragStartState2, bubblePositioner, desktopModeWindowDecorViewModel == null ? null : desktopModeWindowDecorViewModel);
        } else {
            desktopModeVisualIndicator = desktopModeVisualIndicator3;
        }
        if (this.visualIndicator == null) {
            this.visualIndicator = desktopModeVisualIndicator;
        }
        return desktopModeVisualIndicator.updateIndicatorType(new PointF(f, f2), runningTaskInfo, z, false, z2);
    }

    public final boolean willExitDesktop(int i, boolean z) {
        Integer deskIdForTask = this.taskRepository.getDeskIdForTask(i);
        if (deskIdForTask != null) {
            if (this.taskRepository.desktopData.getDisplayForDesk(deskIdForTask.intValue()) != 0) {
                return false;
            }
        }
        return z;
    }

    public final void moveTaskToFront(ActivityManager.RunningTaskInfo runningTaskInfo, RemoteTransition remoteTransition, DesktopModeEventLogger.Companion.UnminimizeReason unminimizeReason) {
        Integer deskIdForTask = this.taskRepository.getDeskIdForTask(runningTaskInfo.taskId);
        if (deskIdForTask == null && (deskIdForTask = getOrCreateDefaultDeskId(runningTaskInfo.displayId, false)) == null) {
            return;
        }
        int intValue = deskIdForTask.intValue();
        logV$1("moveTaskToFront taskId=%s deskId=%s", Integer.valueOf(runningTaskInfo.taskId), Integer.valueOf(intValue));
        DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.snapEventHandler;
        if (desktopModeWindowDecorViewModel == null) {
            desktopModeWindowDecorViewModel = null;
        }
        DesktopTilingWindowDecoration desktopTilingWindowDecoration = (DesktopTilingWindowDecoration) desktopModeWindowDecorViewModel.mDesktopTilingDecorViewModel.tilingTransitionHandlerByDisplayId.get(runningTaskInfo.displayId);
        if (desktopTilingWindowDecoration != null ? desktopTilingWindowDecoration.moveTiledPairToFront(runningTaskInfo.taskId, true) : false) {
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            ((RootTaskDesksOrganizer) this.desksOrganizer).reorderTaskToFront(windowContainerTransaction, intValue, runningTaskInfo);
        } else {
            windowContainerTransaction.reorder(runningTaskInfo.token, true, true).getClass();
        }
        startLaunchTransition(3, windowContainerTransaction, Integer.valueOf(runningTaskInfo.taskId), remoteTransition, intValue, runningTaskInfo.displayId, unminimizeReason);
    }

    public static /* synthetic */ void getDesktopModeEnterExitTransitionListener$annotations() {
    }

    public static /* synthetic */ void getExitDesktopModeListener$annotations() {
    }

    public static /* synthetic */ void getTaskbarDesktopTaskListener$annotations() {
    }
}
