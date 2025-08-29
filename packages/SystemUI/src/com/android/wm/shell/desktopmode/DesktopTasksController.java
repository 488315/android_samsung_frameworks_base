package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.TaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.view.Display;
import android.view.DragEvent;
import android.view.IWindowManager;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import android.window.DisplayAreaInfo;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.util.SparseArrayKt$valueIterator$1;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.DesktopModeCompatUtils;
import com.android.internal.policy.SystemBarUtils;
import com.android.internal.protolog.ProtoLog;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
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
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.HomeIntentProvider;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.UserProfileContexts;
import com.android.wm.shell.common.pip.PipUtils;
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
import com.android.wm.shell.draganddrop.GlobalDragListener;
import com.android.wm.shell.freeform.FreeformTaskTransitionStarter;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.recents.RecentsTransitionStateListener;
import com.android.wm.shell.shared.GroupedTaskInfo;
import com.android.wm.shell.shared.TransitionUtil;
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
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence.AnonymousClass1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;
import kotlin.text.CharsKt__CharJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class DesktopTasksController implements RemoteCallable, Transitions.TransitionHandler, DragAndDropController.DragAndDropListener, UserChangeListener {
    public final Optional bubbleController;
    public final Context context;
    public DesktopModeWindowDecorViewModel.AnonymousClass1 decorViewModelDesktopDisabledChangeListener;
    public final List defaultDisplayDesktopModeChangeListenerExecutors;
    public final List defaultDisplayDesktopModeChangeListeners;
    public DesktopTasksController$IDesktopModeImpl$deskChangeListener$1 deskChangeListener;
    public final DesksOrganizer desksOrganizer;
    public final DesksTransitionObserver desksTransitionObserver;
    public final DesktopConfig desktopConfig;
    public DesktopTasksController$IDesktopModeImpl$desktopDisabledFlagsListener$1 desktopDisabledFlagsListener;
    public int desktopDisabledFlagsOnDefaultDisplay;
    public final ShellExecutor desktopExecutor;
    public final DesktopImmersiveController desktopImmersiveController;
    public final DesktopMixedTransitionHandler desktopMixedTransitionHandler;
    public final DesktopModeCompatPolicy desktopModeCompatPolicy;
    public final DesktopModeDragAndDropTransitionHandler desktopModeDragAndDropTransitionHandler;
    public DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopModeEnterExitTransitionListener;
    public final DesktopModeEventLogger desktopModeEventLogger;
    public final DesktopModeShellCommandHandler desktopModeShellCommandHandler;
    public final DesktopModeUiEventLogger desktopModeUiEventLogger;
    public Long desktopStartMillis;
    public Long desktopStartMillisOnExternal;
    public final DesktopState desktopState;
    public final Optional desktopTasksLimiter;
    public final DesktopWallpaperActivityTokenProvider desktopWallpaperActivityTokenProvider;
    public final DisplayController displayController;
    public final DisplayManager displayManager;
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
    public static final long SA_DURATION_UNIT_MS = TimeUnit.MINUTES.toMillis(1);
    public final DesktopTasksController$$ExternalSyntheticLambda1 mOnAnimationFinishedCallback = new DesktopTasksController$$ExternalSyntheticLambda1(this, 0);
    public final DesktopTasksController$dragToDesktopStateListener$1 dragToDesktopStateListener = new DesktopTasksController$dragToDesktopStateListener$1(this);
    public int recentsTransitionState = 1;
    public final ArrayList taskIdsOfTabletMode = new ArrayList();
    public int disableConnectDisplayIdBeforeUserSwitch = -1;
    public final DesktopModeImpl desktopMode = new DesktopModeImpl();

    public final class Companion {

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

    public interface DefaultDisplayDesktopModeChangeListener {
        void onDefaultDisplayDesktopModeChanged(boolean z);
    }

    public final class DesktopModeImpl implements DesktopMode {
        public DesktopModeImpl() {
        }
    }

    public final class IDesktopModeImpl extends IDesktopMode$Stub implements ExternalInterfaceBinder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public DesktopTasksController controller;
        public final SingleInstanceRemoteListener remoteListener;
        public final DesktopTasksController$IDesktopModeImpl$deskChangeListener$1 deskChangeListener = new DesktopTasksController$IDesktopModeImpl$deskChangeListener$1(this);
        public final DesktopTasksController$IDesktopModeImpl$visibleTasksListener$1 visibleTasksListener = new DesktopRepository.VisibleTasksListener() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$visibleTasksListener$1
            @Override // com.android.wm.shell.desktopmode.DesktopRepository.VisibleTasksListener
            public final void onTasksVisibilityChanged(final int i, final int i2) {
                ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onVisibilityChanged display=%d visible=%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
                SingleInstanceRemoteListener singleInstanceRemoteListener = this.this$0.remoteListener;
                if (singleInstanceRemoteListener == null) {
                    singleInstanceRemoteListener = null;
                }
                singleInstanceRemoteListener.call(new SingleInstanceRemoteListener.RemoteCall() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$visibleTasksListener$1$onTasksVisibilityChanged$1
                    @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
                    public final void accept(Object obj) {
                        IDesktopTaskListener$Stub$Proxy iDesktopTaskListener$Stub$Proxy = (IDesktopTaskListener$Stub$Proxy) ((IDesktopTaskListener) obj);
                        int i3 = i;
                        int i4 = i2;
                        Parcel parcelObtain = Parcel.obtain(iDesktopTaskListener$Stub$Proxy.mRemote);
                        try {
                            parcelObtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                            parcelObtain.writeInt(i3);
                            parcelObtain.writeInt(i4);
                            iDesktopTaskListener$Stub$Proxy.mRemote.transact(2, parcelObtain, null, 1);
                        } finally {
                            parcelObtain.recycle();
                        }
                    }
                });
            }
        };
        public final DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 taskbarDesktopTaskListener = new DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1(this);
        public final DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopModeEntryExitTransitionListener = new DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1(this);
        public final DesktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 exitDesktopModeListener = new DesktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1(this);
        public final DesktopTasksController$IDesktopModeImpl$desktopDisabledFlagsListener$1 desktopDisabledFlagsListener = new DesktopTasksController$IDesktopModeImpl$desktopDisabledFlagsListener$1(this);

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
                            DesktopModeWindowDecorViewModel.AnonymousClass1 anonymousClass1;
                            IDesktopTaskListener iDesktopTaskListener = (IDesktopTaskListener) obj2;
                            DesktopTasksController desktopTasksController3 = desktopTasksController2;
                            DesktopRepository.DesktopData desktopData = desktopTasksController3.taskRepository.desktopData;
                            FilteringSequence filteringSequenceFilter = SequencesKt___SequencesKt.filter(desktopData.desksSequence(), new DesktopRepository$$ExternalSyntheticLambda0(6));
                            LinkedHashMap linkedHashMap = new LinkedHashMap();
                            FilteringSequence.AnonymousClass1 anonymousClass12 = filteringSequenceFilter.new AnonymousClass1();
                            while (anonymousClass12.hasNext()) {
                                Object next = anonymousClass12.next();
                                Integer numValueOf = Integer.valueOf(((DesktopRepository.Desk) next).displayId);
                                Object arrayList = linkedHashMap.get(numValueOf);
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                    linkedHashMap.put(numValueOf, arrayList);
                                }
                                ((List) arrayList).add(next);
                            }
                            ArrayList arrayList2 = new ArrayList(linkedHashMap.size());
                            for (Map.Entry entry : linkedHashMap.entrySet()) {
                                int iIntValue = ((Number) entry.getKey()).intValue();
                                List list = (List) entry.getValue();
                                DesktopRepository.Desk activeDesk = desktopData.getActiveDesk(iIntValue);
                                Integer numValueOf2 = activeDesk != null ? Integer.valueOf(activeDesk.deskId) : null;
                                DisplayDeskState displayDeskState = new DisplayDeskState();
                                displayDeskState.displayId = iIntValue;
                                displayDeskState.activeDeskId = numValueOf2 != null ? numValueOf2.intValue() : -1;
                                List list2 = list;
                                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                                Iterator it = list2.iterator();
                                while (it.hasNext()) {
                                    arrayList3.add(Integer.valueOf(((DesktopRepository.Desk) it.next()).deskId));
                                }
                                displayDeskState.deskIds = CollectionsKt___CollectionsKt.toIntArray(arrayList3);
                                arrayList2.add(displayDeskState);
                            }
                            DisplayDeskState[] displayDeskStateArr = (DisplayDeskState[]) arrayList2.toArray(new DisplayDeskState[0]);
                            boolean zCanCreateDesks$default = CoreRune.DW_MULTIPLE_DESKS ? DesktopTasksController.canCreateDesks$default(desktopTasksController3) : false;
                            IDesktopTaskListener$Stub$Proxy iDesktopTaskListener$Stub$Proxy = (IDesktopTaskListener$Stub$Proxy) iDesktopTaskListener;
                            Parcel parcelObtain = Parcel.obtain(iDesktopTaskListener$Stub$Proxy.mRemote);
                            try {
                                parcelObtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                                parcelObtain.writeTypedArray(displayDeskStateArr, 0);
                                parcelObtain.writeBoolean(zCanCreateDesks$default);
                                iDesktopTaskListener$Stub$Proxy.mRemote.transact(1, parcelObtain, null, 1);
                                parcelObtain.recycle();
                                iDesktopTaskListener$Stub$Proxy.onDesktopDisabledFlagsChangedOnDefaultDisplay(desktopTasksController3.desktopDisabledFlagsOnDefaultDisplay);
                                if (!CoreRune.MW_CAPTION_DESKTOP_DISABLED || (anonymousClass1 = desktopTasksController3.decorViewModelDesktopDisabledChangeListener) == null) {
                                    return;
                                }
                                anonymousClass1.onDesktopDisabledFlagsChangedOnDefaultDisplay(desktopTasksController3.desktopDisabledFlagsOnDefaultDisplay);
                            } catch (Throwable th) {
                                parcelObtain.recycle();
                                throw th;
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
                    desktopTasksController2.desktopDisabledFlagsListener = iDesktopModeImpl.desktopDisabledFlagsListener;
                    desktopTasksController2.deskChangeListener = iDesktopModeImpl.deskChangeListener;
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
                    desktopTasksController2.desktopDisabledFlagsListener = null;
                    desktopTasksController2.deskChangeListener = null;
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

    /* renamed from: com.android.wm.shell.desktopmode.DesktopTasksController$createDeskRoot$1, reason: invalid class name and case insensitive filesystem */
    public final class C12011 implements DesksOrganizer.OnCreateCallback {
        public final /* synthetic */ int $displayId;
        public final /* synthetic */ Function1 $onResult;
        public final /* synthetic */ int $userId;

        public C12011(int i, int i2, Function1 function1) {
            this.$displayId = i;
            this.$userId = i2;
            this.$onResult = function1;
        }

        public final void onCreated(int i) {
            Object[] objArr = {Integer.valueOf(i), Integer.valueOf(this.$displayId), Integer.valueOf(this.$userId)};
            Companion companion = DesktopTasksController.Companion;
            DesktopTasksController.this.getClass();
            DesktopTasksController.logD$1("createDesk obtained deskId=%d for displayId=%d and userId=%d", objArr);
            this.$onResult.mo781invoke(Integer.valueOf(i));
        }
    }

    /* renamed from: com.android.wm.shell.desktopmode.DesktopTasksController$moveTaskToDefaultDeskAndActivate$1, reason: invalid class name and case insensitive filesystem */
    final class C12021 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $displayId;
        final /* synthetic */ RemoteTransition $remoteTransition;
        final /* synthetic */ int $taskId;
        final /* synthetic */ DesktopModeTransitionSource $transitionSource;
        final /* synthetic */ WindowContainerTransaction $wct;
        int I$0;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C12021(int i, int i2, WindowContainerTransaction windowContainerTransaction, DesktopModeTransitionSource desktopModeTransitionSource, RemoteTransition remoteTransition, Continuation continuation) {
            super(2, continuation);
            this.$taskId = i;
            this.$displayId = i2;
            this.$wct = windowContainerTransaction;
            this.$transitionSource = desktopModeTransitionSource;
            this.$remoteTransition = remoteTransition;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DesktopTasksController.this.new C12021(this.$taskId, this.$displayId, this.$wct, this.$transitionSource, this.$remoteTransition, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C12021) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Type inference failed for: r5v0, types: [com.android.wm.shell.desktopmode.DesktopTasksController$createDeskSuspending$2$1] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object orThrow;
            DesktopTasksController desktopTasksController;
            int i;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                    int i3 = this.$taskId;
                    int i4 = this.$displayId;
                    this.L$0 = desktopTasksController2;
                    this.I$0 = i3;
                    this.label = 1;
                    Integer defaultDeskId = desktopTasksController2.taskRepository.getDefaultDeskId(i4);
                    if (defaultDeskId != null) {
                        orThrow = new Integer(defaultDeskId.intValue());
                    } else {
                        int i5 = desktopTasksController2.userId;
                        final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this));
                        DesktopTasksController.createDesk$default(desktopTasksController2, i4, i5, false, new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$createDeskSuspending$2$1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                int iIntValue = ((Number) obj2).intValue();
                                int i6 = Result.$r8$clinit;
                                safeContinuation.resumeWith(Integer.valueOf(iIntValue));
                                return Unit.INSTANCE;
                            }
                        }, 24);
                        orThrow = safeContinuation.getOrThrow();
                    }
                    if (orThrow == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    desktopTasksController = desktopTasksController2;
                    i = i3;
                    obj = orThrow;
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    int i6 = this.I$0;
                    DesktopTasksController desktopTasksController3 = (DesktopTasksController) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    i = i6;
                    desktopTasksController = desktopTasksController3;
                }
                DesktopTasksController.moveTaskToDesk$default(desktopTasksController, i, ((Number) obj).intValue(), this.$wct, this.$transitionSource, this.$remoteTransition, 32);
            } catch (Throwable th) {
                DesktopTasksController desktopTasksController4 = DesktopTasksController.this;
                Object[] objArr = {th.getMessage()};
                Companion companion = DesktopTasksController.Companion;
                desktopTasksController4.getClass();
                DesktopTasksController.logE("Failed to move task to default desk: %s", objArr);
            }
            return Unit.INSTANCE;
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
        this.displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        if (((DesktopStateImpl) desktopState).canEnterDesktopMode) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController.1
                @Override // java.lang.Runnable
                public final void run() {
                    RecentTasksController recentTasksController2;
                    DeviceStateManager deviceStateManager;
                    final DesktopTasksController desktopTasksController = DesktopTasksController.this;
                    Companion companion = DesktopTasksController.Companion;
                    desktopTasksController.getClass();
                    DesktopTasksController.logD$1("onInit", new Object[0]);
                    BiConsumer biConsumer = new BiConsumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$onInit$1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) throws Resources.NotFoundException {
                            desktopTasksController.dump$2((PrintWriter) obj, (String) obj2);
                        }
                    };
                    ShellCommandHandler shellCommandHandler2 = desktopTasksController.shellCommandHandler;
                    shellCommandHandler2.addDumpCallback(biConsumer, desktopTasksController);
                    shellCommandHandler2.addCommandCallback("desktopmode", desktopTasksController.desktopModeShellCommandHandler, desktopTasksController);
                    Supplier supplier = new Supplier() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$onInit$2
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            DesktopTasksController desktopTasksController2 = desktopTasksController;
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
                    RecentsTransitionStateListener recentsTransitionStateListener = new RecentsTransitionStateListener() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$onInit$3
                        @Override // com.android.wm.shell.recents.RecentsTransitionStateListener
                        public final void onTransitionStateChanged(int i) {
                            DesktopTilingDividerWindowManager desktopTilingDividerWindowManager;
                            String str = i != 1 ? i != 2 ? i != 3 ? "UNKNOWN" : "TRANSITION_STATE_ANIMATING" : "TRANSITION_STATE_REQUESTED" : "TRANSITION_STATE_NOT_RUNNING";
                            DesktopTasksController.Companion companion2 = DesktopTasksController.Companion;
                            DesktopTasksController desktopTasksController2 = desktopTasksController;
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
                    };
                    RecentsTransitionHandler recentsTransitionHandler2 = desktopTasksController.recentsTransitionHandler;
                    recentsTransitionHandler2.mStateListeners.add(recentsTransitionStateListener);
                    desktopTasksController.dragAndDropController.mListeners.add(desktopTasksController);
                    if (CoreRune.DW_MULTI_FOLD_POLICY && (deviceStateManager = (DeviceStateManager) desktopTasksController.context.getSystemService(DeviceStateManager.class)) != null) {
                        deviceStateManager.registerCallback(desktopTasksController.mainExecutor, new DeviceStateManager.FoldStateListener(desktopTasksController.context, new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$onInit$4
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) throws Resources.NotFoundException {
                                Boolean bool = (Boolean) obj;
                                if (bool != null) {
                                    DesktopTasksController desktopTasksController2 = desktopTasksController;
                                    boolean zBooleanValue = bool.booleanValue();
                                    DesktopTasksController.Companion companion2 = DesktopTasksController.Companion;
                                    desktopTasksController2.getClass();
                                    if (zBooleanValue) {
                                        DesktopStateImpl.Companion.getClass();
                                        if (DesktopStateImpl.Companion.inDesktopWindowing(0)) {
                                            DesktopTasksController.exitDefaultDisplayDesktopWindowing$default(desktopTasksController2, null, 3);
                                        }
                                    }
                                }
                            }
                        }));
                    }
                    final Uri uri = Uri.parse("content://com.samsung.android.smartmirroring/smart_view_connected");
                    final Uri uri2 = Uri.parse("content://com.samsung.android.secondscreen/second_screen_connected");
                    final Handler handler2 = desktopTasksController.handler;
                    ContentObserver contentObserver = new ContentObserver(handler2) { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$registerDesktopDisabledObservers$settingsObserver$1
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z, Uri uri3) throws Resources.NotFoundException {
                            if (uri3 == null) {
                                return;
                            }
                            if (!uri3.equals(uri)) {
                                if (uri3.equals(uri2)) {
                                    DesktopTasksController desktopTasksController2 = desktopTasksController;
                                    String type = desktopTasksController2.context.getContentResolver().getType(Uri.parse("content://com.samsung.android.secondscreen/second_screen_connected"));
                                    if (type != null) {
                                        if (Boolean.parseBoolean(type)) {
                                            desktopTasksController2.addDesktopDisabledFlagsOnDefaultDisplay(4);
                                            return;
                                        } else {
                                            desktopTasksController2.removeDesktopDisabledFlagsOnDefaultDisplay(4);
                                            return;
                                        }
                                    }
                                    return;
                                }
                                return;
                            }
                            DesktopTasksController desktopTasksController3 = desktopTasksController;
                            String type2 = desktopTasksController3.context.getContentResolver().getType(Uri.parse("content://com.samsung.android.smartmirroring/smart_view_connected"));
                            if (type2 != null) {
                                if (!Boolean.parseBoolean(type2)) {
                                    desktopTasksController3.removeDesktopDisabledFlagsOnDefaultDisplay(2);
                                    return;
                                }
                                if (desktopTasksController3.taskRepository.getActiveDeskId(0) != null) {
                                    desktopTasksController3.showDesktopDisabledToast(Integer.valueOf(R.string.dw_desktop_disabled_while_smart_view_is_in_use));
                                    DesktopTasksController.exitDefaultDisplayDesktopWindowing$default(desktopTasksController3, null, 3);
                                }
                                desktopTasksController3.addDesktopDisabledFlagsOnDefaultDisplay(2);
                            }
                        }
                    };
                    uri.getClass();
                    try {
                        desktopTasksController.context.getContentResolver().registerContentObserver(uri, false, contentObserver);
                    } catch (SecurityException unused) {
                        DesktopTasksController.logD$1("registerObserver can not register observer", new Object[0]);
                    }
                    uri2.getClass();
                    try {
                        desktopTasksController.context.getContentResolver().registerContentObserver(uri2, false, contentObserver);
                    } catch (SecurityException unused2) {
                        DesktopTasksController.logD$1("registerObserver can not register observer", new Object[0]);
                    }
                    int i = desktopTasksController.desktopDisabledFlagsOnDefaultDisplay;
                    if (!MultiWindowCoreState.MW_ENABLED) {
                        i |= 1;
                    }
                    if (Boolean.parseBoolean(desktopTasksController.context.getContentResolver().getType(Uri.parse("content://com.samsung.android.smartmirroring/smart_view_connected")))) {
                        i |= 2;
                    }
                    if (Boolean.parseBoolean(desktopTasksController.context.getContentResolver().getType(Uri.parse("content://com.samsung.android.secondscreen/second_screen_connected")))) {
                        i |= 4;
                    }
                    IWindowManager iWindowManagerAsInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
                    if (iWindowManagerAsInterface != null && iWindowManagerAsInterface.isSafeModeEnabled()) {
                        i |= 8;
                    }
                    desktopTasksController.desktopDisabledFlagsOnDefaultDisplay = i;
                    recentsTransitionHandler2.mDesktopTasksController = desktopTasksController;
                    if (!CoreRune.DW_DESK_LABEL || (recentTasksController2 = desktopTasksController.recentTasksController) == null) {
                        return;
                    }
                    recentTasksController2.mDesktopTasksController = desktopTasksController;
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
                        public final Object mo781invoke(Object obj) {
                            int i4 = Result.$r8$clinit;
                            safeContinuation.resumeWith((Integer) obj);
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
                public final Object activeDesk(int i, Continuation continuation) throws Resources.NotFoundException {
                    Companion companion = DesktopTasksController.Companion;
                    DesktopTasksController desktopTasksController = DesktopTasksController.this;
                    desktopTasksController.getClass();
                    final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
                    Function1 function1 = new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$activeDeskSuspending$2$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            int i2 = Result.$r8$clinit;
                            safeContinuation.resumeWith((Integer) obj);
                            return Unit.INSTANCE;
                        }
                    };
                    DesktopTasksController.activateDesk$default(desktopTasksController, i, null, 0, 0, 14);
                    function1.mo781invoke(Integer.valueOf(i));
                    Object orThrow = safeContinuation.getOrThrow();
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (orThrow != coroutineSingletons) {
                        orThrow = Unit.INSTANCE;
                    }
                    return orThrow == coroutineSingletons ? orThrow : Unit.INSTANCE;
                }
            };
            optional.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new DesktopTasksController$$ExternalSyntheticLambda2(this, 0)));
        }
        shellTaskOrganizer.registerMultiWindowCoreStateListener(new ShellTaskOrganizer.MultiWindowCoreStateChangeListener() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController.5
            @Override // com.android.wm.shell.ShellTaskOrganizer.MultiWindowCoreStateChangeListener
            public final boolean onMultiWindowCoreStateChanged(int i) throws Resources.NotFoundException {
                Companion companion = DesktopTasksController.Companion;
                DesktopTasksController desktopTasksController = DesktopTasksController.this;
                desktopTasksController.getClass();
                if ((i & 1) == 0) {
                    return false;
                }
                if (MultiWindowCoreState.MW_ENABLED) {
                    desktopTasksController.removeDesktopDisabledFlagsOnDefaultDisplay(1);
                    return true;
                }
                DesktopStateImpl.Companion.getClass();
                if (DesktopStateImpl.Companion.inDesktopWindowing(0)) {
                    DesktopTasksController.exitDefaultDisplayDesktopWindowing$default(desktopTasksController, null, 3);
                }
                desktopTasksController.addDesktopDisabledFlagsOnDefaultDisplay(1);
                return true;
            }
        });
        this.defaultDisplayDesktopModeChangeListeners = new ArrayList();
        this.defaultDisplayDesktopModeChangeListenerExecutors = new ArrayList();
    }

    public static /* synthetic */ void activateDesk$default(DesktopTasksController desktopTasksController, int i, RemoteTransition remoteTransition, int i2, int i3, int i4) throws Resources.NotFoundException {
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
    /* JADX WARN: Removed duplicated region for block: B:134:0x02c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Function1 addDeskActivationChanges$default(final DesktopTasksController desktopTasksController, final int i, WindowContainerTransaction windowContainerTransaction, TaskInfo taskInfo, int i2, int i3, int i4) throws Resources.NotFoundException {
        ShellTaskOrganizer shellTaskOrganizer;
        DesktopModeVisualIndicator desktopModeVisualIndicator;
        final Integer taskIdToMinimize;
        int i5 = 1;
        final TaskInfo taskInfo2 = (i4 & 4) != 0 ? null : taskInfo;
        int i6 = 0;
        final boolean z = (i4 & 8) == 0;
        int i7 = (i4 & 16) != 0 ? -1 : i2;
        final int displayForDesk = (i4 & 32) != 0 ? i7 != -1 ? i7 : desktopTasksController.taskRepository.desktopData.getDisplayForDesk(i) : i3;
        desktopTasksController.getClass();
        Integer numValueOf = taskInfo2 != null ? Integer.valueOf(taskInfo2.taskId) : null;
        logV$1("addDeskActivationChanges newTaskId=%d deskId=%d displayId=%d", taskInfo2 != null ? Integer.valueOf(taskInfo2.taskId) : null, Integer.valueOf(i), Integer.valueOf(displayForDesk));
        boolean zIsTrue = DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue();
        ShellTaskOrganizer shellTaskOrganizer2 = desktopTasksController.shellTaskOrganizer;
        if (!zIsTrue) {
            Integer numValueOf2 = taskInfo2 != null ? Integer.valueOf(taskInfo2.taskId) : null;
            logV$1("bringDesktopAppsToFront, newTaskId=%d", numValueOf2);
            desktopTasksController.prepareForDeskActivation(windowContainerTransaction, displayForDesk);
            List expandedTasksOrdered = desktopTasksController.taskRepository.getExpandedTasksOrdered(displayForDesk);
            if (numValueOf2 == null || !desktopTasksController.desktopTasksLimiter.isPresent()) {
                taskIdToMinimize = null;
            } else {
                DesktopTasksLimiter desktopTasksLimiter = (DesktopTasksLimiter) desktopTasksController.desktopTasksLimiter.get();
                int i8 = DesktopTasksLimiter.$r8$clinit;
                taskIdToMinimize = desktopTasksLimiter.getTaskIdToMinimize(expandedTasksOrdered, numValueOf2, false);
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) expandedTasksOrdered;
            int size = arrayList2.size();
            while (i6 < size) {
                Object obj = arrayList2.get(i6);
                i6++;
                int iIntValue = ((Number) obj).intValue();
                if (taskIdToMinimize == null || iIntValue != taskIdToMinimize.intValue()) {
                    arrayList.add(obj);
                }
            }
            Iterator it = CollectionsKt___CollectionsKt.reversed(arrayList).iterator();
            while (it.hasNext()) {
                int iIntValue2 = ((Number) it.next()).intValue();
                ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer2.getRunningTaskInfo(iIntValue2);
                if (runningTaskInfo != null) {
                    windowContainerTransaction.reorder(runningTaskInfo.token, true);
                } else if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
                    ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                    activityOptionsMakeBasic.setLaunchWindowingMode(5);
                    activityOptionsMakeBasic.setSplashScreenStyle(1);
                    windowContainerTransaction.startTask(iIntValue2, activityOptionsMakeBasic.toBundle());
                }
            }
            DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 = desktopTasksController.taskbarDesktopTaskListener;
            if (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 != null) {
                desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1.onTaskbarCornerRoundingUpdate(desktopTasksController.doesAnyTaskRequireTaskbarRounding(displayForDesk, null));
            }
            return new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    TaskInfo taskInfo3 = taskInfo2;
                    IBinder iBinder = (IBinder) obj2;
                    DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                    Integer num = taskIdToMinimize;
                    DesktopTasksController desktopTasksController2 = desktopTasksController;
                    if (num != null) {
                        desktopTasksController2.addPendingMinimizeTransition(iBinder, num.intValue(), DesktopModeEventLogger.Companion.MinimizeReason.TASK_LIMIT);
                    }
                    if (taskInfo3 != null && z) {
                        desktopTasksController2.addPendingAppLaunchTransition(iBinder, taskInfo3.taskId, num);
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
                        deskChangeListener.onDeskAdded(displayForDesk, i);
                        DesktopRepository desktopRepository2 = desktopRepository;
                        int i9 = DesktopRepository.$r8$clinit;
                        if (desktopRepository2.canCreateDesks()) {
                            return;
                        }
                        deskChangeListener.onCanCreateDesksChanged(false);
                    }
                });
            }
        }
        if (CoreRune.DW_DESK_LABEL && (desktopModeVisualIndicator = desktopTasksController.visualIndicator) == null) {
            if (desktopModeVisualIndicator == null) {
                Context context = desktopTasksController.context;
                BubbleController bubbleController = (BubbleController) desktopTasksController.bubbleController.orElse(null);
                BubblePositioner bubblePositioner = bubbleController != null ? bubbleController.mBubblePositioner : null;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = desktopTasksController.snapEventHandler;
                shellTaskOrganizer = shellTaskOrganizer2;
                desktopModeVisualIndicator = new DesktopModeVisualIndicator(desktopTasksController.desktopExecutor, desktopTasksController.mainExecutor, desktopTasksController.syncQueue, displayForDesk, desktopTasksController.displayController, desktopTasksController.rootTaskDisplayAreaOrganizer, context, bubblePositioner, desktopModeWindowDecorViewModel == null ? null : desktopModeWindowDecorViewModel, desktopTasksController.recentTasksController);
            } else {
                shellTaskOrganizer = shellTaskOrganizer2;
            }
            if (desktopTasksController.visualIndicator == null) {
                desktopTasksController.setVisualIndicator(desktopModeVisualIndicator);
            }
            DesktopModeVisualIndicator desktopModeVisualIndicator2 = desktopTasksController.visualIndicator;
            if (desktopModeVisualIndicator2 != null) {
                final DesktopTasksController$$ExternalSyntheticLambda1 desktopTasksController$$ExternalSyntheticLambda1 = new DesktopTasksController$$ExternalSyntheticLambda1(desktopTasksController, 1);
                final Integer numValueOf3 = Integer.valueOf(i);
                final VisualIndicatorViewContainer visualIndicatorViewContainer = desktopModeVisualIndicator2.mVisualIndicatorViewContainer;
                visualIndicatorViewContainer.getClass();
                visualIndicatorViewContainer.desktopExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$startDeskLabelAnimator$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.lang.Runnable
                    public final void run() {
                        View view = visualIndicatorViewContainer.indicatorView;
                        final View viewFindViewById = view != null ? view.findViewById(R.id.labelContainer) : null;
                        if (viewFindViewById != null) {
                            viewFindViewById.setAlpha(1.0f);
                        }
                        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                        final VisualIndicatorViewContainer visualIndicatorViewContainer2 = visualIndicatorViewContainer;
                        ShellExecutor shellExecutor = visualIndicatorViewContainer2.mainExecutor;
                        final Integer num = numValueOf3;
                        shellExecutor.executeBlocking(new Runnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$startDeskLabelAnimator$1.1
                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX WARN: Removed duplicated region for block: B:11:0x001c  */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void run() {
                                GroupedTaskInfo deskForSnapshot;
                                RecentTasksController recentTasksController;
                                Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                                Integer num2 = num;
                                T deskLabel = 0;
                                deskLabel = 0;
                                deskLabel = 0;
                                if (num2 != null) {
                                    VisualIndicatorViewContainer visualIndicatorViewContainer3 = visualIndicatorViewContainer2;
                                    int iIntValue3 = num2.intValue();
                                    RecentTasksController recentTasksController2 = visualIndicatorViewContainer3.recentsTasksController;
                                    Integer deskLabel2 = recentTasksController2 != null ? recentTasksController2.getDeskLabel(iIntValue3) : null;
                                    if (deskLabel2 == null) {
                                        RecentTasksController recentTasksController3 = visualIndicatorViewContainer2.recentsTasksController;
                                        if (recentTasksController3 != null && (deskForSnapshot = recentTasksController3.getDeskForSnapshot()) != null && (recentTasksController = visualIndicatorViewContainer2.recentsTasksController) != null) {
                                            if (deskForSnapshot.mType == 4) {
                                                throw new IllegalStateException("No desk ID for a mixed task");
                                            }
                                            deskLabel = recentTasksController.getDeskLabel(deskForSnapshot.mDeskId);
                                        }
                                    } else {
                                        deskLabel = deskLabel2;
                                    }
                                }
                                ref$ObjectRef2.element = deskLabel;
                            }
                        });
                        visualIndicatorViewContainer.addDeskLabel((Integer) ref$ObjectRef.element);
                        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
                        final VisualIndicatorViewContainer visualIndicatorViewContainer3 = visualIndicatorViewContainer;
                        final Function0 function0 = desktopTasksController$$ExternalSyntheticLambda1;
                        valueAnimatorOfFloat.setStartDelay(1000L);
                        valueAnimatorOfFloat.setDuration(300L);
                        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$startDeskLabelAnimator$1$animator$1$1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                View view2 = viewFindViewById;
                                if (view2 != null) {
                                    view2.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                }
                            }
                        });
                        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$startDeskLabelAnimator$1$animator$1$2
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                visualIndicatorViewContainer3.releaseVisualIndicator();
                                Function0 function02 = function0;
                                if (function02 != null) {
                                    function02.invoke();
                                }
                            }
                        });
                        valueAnimatorOfFloat.start();
                    }
                });
            }
        } else {
            shellTaskOrganizer = shellTaskOrganizer2;
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
        T taskIdsToMinimize$default = desktopTasksLimiter2 != null ? DesktopTasksLimiter.getTaskIdsToMinimize$default(desktopTasksLimiter2, expandedTasksIdsInDeskOrdered, numValueOf) : 0;
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
        boolean zIsTrue2 = DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue();
        DisplayController displayController = desktopTasksController.displayController;
        if (zIsTrue2) {
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = (ArrayList) expandedTasksIdsInDeskOrdered;
            int size2 = arrayList4.size();
            int i9 = 0;
            while (i9 < size2) {
                int i10 = i5;
                Object obj4 = arrayList4.get(i9);
                i9++;
                int iIntValue3 = ((Number) obj4).intValue();
                DesksOrganizer desksOrganizer2 = desksOrganizer;
                T t = ref$ObjectRef2.element;
                if (t == 0) {
                    Integer num = (Integer) ref$ObjectRef.element;
                    if (num != null && iIntValue3 == num.intValue()) {
                    }
                } else if (!((List) t).contains(Integer.valueOf(iIntValue3))) {
                    arrayList3.add(obj4);
                }
                i5 = i10;
                desksOrganizer = desksOrganizer2;
            }
            DesksOrganizer desksOrganizer3 = desksOrganizer;
            int i11 = i5;
            Iterator it3 = CollectionsKt___CollectionsKt.reversed(arrayList3).iterator();
            while (it3.hasNext()) {
                int iIntValue4 = ((Number) it3.next()).intValue();
                ActivityManager.RunningTaskInfo runningTaskInfo3 = shellTaskOrganizer.getRunningTaskInfo(iIntValue4);
                if (runningTaskInfo3 == null) {
                    ActivityOptions activityOptionsMakeBasic2 = ActivityOptions.makeBasic();
                    activityOptionsMakeBasic2.setLaunchWindowingMode(5);
                    activityOptionsMakeBasic2.setSplashScreenStyle(i11);
                    DisplayLayout displayLayout = displayController.getDisplayLayout(displayForDesk);
                    activityOptionsMakeBasic2.setLaunchBounds(displayLayout != null ? DesktopModeUtils.calculateDefaultDesktopTaskBounds(displayLayout) : null);
                    Unit unit = Unit.INSTANCE;
                    windowContainerTransaction.startTask(iIntValue4, activityOptionsMakeBasic2.toBundle());
                } else if (runningTaskInfo3.parentTaskId != i) {
                    ((RootTaskDesksOrganizer) desksOrganizer3).moveTaskToDesk(windowContainerTransaction, i, runningTaskInfo3);
                } else {
                    ((RootTaskDesksOrganizer) desksOrganizer3).reorderTaskToFront(windowContainerTransaction, i, runningTaskInfo3);
                }
                i11 = 1;
            }
        }
        Integer activeDeskId = desktopTasksController.taskRepository.getActiveDeskId(displayForDesk);
        Integer num2 = (activeDeskId == null || activeDeskId.intValue() == i) ? null : activeDeskId;
        boolean z2 = CoreRune.MW_SA_LOGGING;
        final DesktopTasksController$$ExternalSyntheticLambda3 desktopTasksController$$ExternalSyntheticLambda3PrepareDeskDeactivationIfNeeded$default = prepareDeskDeactivationIfNeeded$default(desktopTasksController, windowContainerTransaction, num2, 0, 0, 12);
        if (displayForDesk == 0) {
            DesktopStateImpl.Companion.getClass();
            if (!DesktopStateImpl.Companion.inDesktopWindowing(0)) {
                desktopTasksController.onDefaultDisplayDesktopModeChanged(true);
                DesktopStateImpl.Companion.setInDesktopWindowing(true);
            }
        } else {
            DesktopStateImpl.Companion.getClass();
            DesktopStateImpl.Companion.setDesktopExternalDisplayId(displayForDesk);
        }
        if (z2) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (displayForDesk != 0 && desktopTasksController.desktopStartMillisOnExternal == null) {
                desktopTasksController.desktopStartMillisOnExternal = Long.valueOf(jElapsedRealtime);
                Display display = displayController.mDisplayManager.getDisplay(displayForDesk);
                if (display != null) {
                    String str = display.getType() == 2 ? "By wired display connected" : display.getType() == 3 ? "By wireless display connected" : null;
                    if (str != null) {
                        CoreSaLogger.logForDexWithScreenId("300", "3004", str);
                    }
                }
            }
            if (desktopTasksController.desktopStartMillis == null) {
                desktopTasksController.desktopStartMillis = Long.valueOf(jElapsedRealtime);
            }
        }
        final Integer num3 = numValueOf;
        final int i12 = displayForDesk;
        return new Function1(num3, i12, i, desktopTasksController, ref$ObjectRef2, ref$ObjectRef, desktopTasksController$$ExternalSyntheticLambda3PrepareDeskDeactivationIfNeeded$default) { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda6
            public final /* synthetic */ Integer f$0;
            public final /* synthetic */ int f$1;
            public final /* synthetic */ int f$2;
            public final /* synthetic */ DesktopTasksController f$3;
            public final /* synthetic */ Ref$ObjectRef f$4;
            public final /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda3 f$6;

            {
                this.f$6 = desktopTasksController$$ExternalSyntheticLambda3PrepareDeskDeactivationIfNeeded$default;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj5) {
                DeskTransition activateDesk;
                final IBinder iBinder = (IBinder) obj5;
                Integer num4 = this.f$0;
                int i13 = this.f$1;
                int i14 = this.f$2;
                DesktopTasksController desktopTasksController2 = this.f$3;
                if (num4 != null) {
                    DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                    activateDesk = new DeskTransition.ActiveDeskWithTask(iBinder, i13, i14, num4.intValue());
                } else {
                    activateDesk = desktopTasksController2.taskRepository.desktopData.getDisplayForDesk(i14) != i13 ? new DeskTransition.ActivateDesk(iBinder, i13, i14, desktopTasksController2.onDeskRemovedListener, desktopTasksController2.userId) : new DeskTransition.ActivateDesk(iBinder, i13, i14, null, 0, 24, null);
                }
                desktopTasksController2.desksTransitionObserver.addPendingTransition(activateDesk);
                final List list2 = (List) this.f$4.element;
                if (list2 != null) {
                    final DesktopModeEventLogger.Companion.MinimizeReason minimizeReason = DesktopModeEventLogger.Companion.MinimizeReason.TASK_LIMIT;
                    Integer num5 = (Integer) CollectionsKt___CollectionsKt.firstOrNull(list2);
                    final ActivityManager.RunningTaskInfo runningTaskInfo4 = num5 != null ? desktopTasksController2.shellTaskOrganizer.getRunningTaskInfo(num5.intValue()) : null;
                    desktopTasksController2.desktopTasksLimiter.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda9
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj6) {
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
                DesktopTasksController$$ExternalSyntheticLambda3 desktopTasksController$$ExternalSyntheticLambda3 = this.f$6;
                if (desktopTasksController$$ExternalSyntheticLambda3 != null) {
                    desktopTasksController$$ExternalSyntheticLambda3.mo781invoke(iBinder);
                }
                return Unit.INSTANCE;
            }
        };
    }

    public static boolean canCreateDesks$default(DesktopTasksController desktopTasksController) {
        DesktopRepository desktopRepository = desktopTasksController.taskRepository;
        if (desktopTasksController.desktopDisabledFlagsOnDefaultDisplay != 0) {
            return false;
        }
        int i = ((DesktopConfigImpl) desktopTasksController.desktopConfig).maxDeskLimit;
        return i == 0 || desktopRepository.desktopData.getNumberOfDesks() < i;
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
        Function1 desktopTasksController$$ExternalSyntheticLambda12 = desktopTasksController$createDeskSuspending$2$1;
        if ((i3 & 32) != 0) {
            desktopTasksController$$ExternalSyntheticLambda12 = new DesktopTasksController$$ExternalSyntheticLambda12(0);
        }
        final Function1 function1 = desktopTasksController$$ExternalSyntheticLambda12;
        Object[] objArr = {Integer.valueOf(i), Integer.valueOf(i5), Boolean.valueOf(z2)};
        desktopTasksController.getClass();
        logV$1("createDesk displayId=%d, userId=%d enforceDeskLimit=%b", objArr);
        final DesktopRepository profile = desktopTasksController.userRepositories.getProfile(i5);
        DesktopRepository.DesktopData desktopData = profile.desktopData;
        if (i == 0 && z4) {
            DesktopRepository.Desk deskForCreateByHome = desktopData.getDeskForCreateByHome();
            Integer numValueOf = deskForCreateByHome != null ? Integer.valueOf(deskForCreateByHome.deskId) : null;
            if (numValueOf != null) {
                final int iIntValue = numValueOf.intValue();
                DesktopRepository.Desk desk = desktopData.getDesk(iIntValue);
                if (desk != null) {
                    desk.usedDesk = 0;
                }
                profile.updatePersistentRepository(i);
                for (Map.Entry entry : profile.deskChangeListeners.entrySet()) {
                    final DesktopRepository.DeskChangeListener deskChangeListener = (DesktopRepository.DeskChangeListener) entry.getKey();
                    ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$postCreateDeskFromHomeAndReuse$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            deskChangeListener.onDeskAdded(i, iIntValue);
                            DesktopRepository desktopRepository = profile;
                            int i6 = DesktopRepository.$r8$clinit;
                            if (desktopRepository.canCreateDesks()) {
                                return;
                            }
                            deskChangeListener.onCanCreateDesksChanged(false);
                        }
                    });
                }
                return;
            }
        }
        if (!z2 || (desktopTasksController.desktopDisabledFlagsOnDefaultDisplay == 0 && ((i4 = ((DesktopConfigImpl) desktopTasksController.desktopConfig).maxDeskLimit) == 0 || desktopData.getNumberOfDesks() < i4))) {
            desktopTasksController.createDeskRoot(i, i5, new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda17
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                    Integer num = (Integer) obj;
                    DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                    DesktopTasksController desktopTasksController2 = this.f$0;
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
                        function1.mo781invoke(num);
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

    public static void exitDefaultDisplayDesktopWindowing$default(DesktopTasksController desktopTasksController, ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws Resources.NotFoundException {
        if ((i & 1) != 0) {
            runningTaskInfo = null;
        }
        boolean z = (i & 2) == 0;
        Integer activeDeskId = desktopTasksController.taskRepository.getActiveDeskId(0);
        if (activeDeskId != null) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            if (runningTaskInfo != null) {
                DisplayAreaInfo displayAreaInfo = desktopTasksController.rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(0);
                if (displayAreaInfo != null) {
                    windowContainerTransaction.reparent(runningTaskInfo.token, displayAreaInfo.token, true);
                }
            } else {
                desktopTasksController.moveHomeTaskToTop(windowContainerTransaction, 0);
            }
            ShellTaskOrganizer shellTaskOrganizer = desktopTasksController.shellTaskOrganizer;
            if (z) {
                for (Pair pair : CollectionsKt___CollectionsKt.reversed(desktopTasksController.taskIdsOfTabletMode)) {
                    Integer num = (Integer) pair.first;
                    Integer num2 = (Integer) pair.second;
                    DesktopRepository desktopRepository = desktopTasksController.taskRepository;
                    num.getClass();
                    if (desktopRepository.getDeskIdForTask(num.intValue()) == null) {
                        if (num2 != null && num2.intValue() == 6) {
                            SplitScreenController splitScreenController = desktopTasksController.splitScreenController;
                            if (splitScreenController == null) {
                                splitScreenController = null;
                            }
                            if (splitScreenController.isTaskRoot(num.intValue())) {
                                SplitScreenController splitScreenController2 = desktopTasksController.splitScreenController;
                                if (splitScreenController2 == null) {
                                    splitScreenController2 = null;
                                }
                                if (!splitScreenController2.isSplitScreenActive()) {
                                }
                            }
                        }
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = shellTaskOrganizer.getRunningTaskInfo(num.intValue());
                        if (runningTaskInfo2 != null) {
                            windowContainerTransaction.reorder(runningTaskInfo2.token, true);
                            if (num2 != null && num2.intValue() == 5) {
                                windowContainerTransaction.setAlwaysOnTop(runningTaskInfo2.token, true);
                            }
                        }
                    }
                }
                desktopTasksController.taskIdsOfTabletMode.clear();
            }
            windowContainerTransaction.setDisplayIdForChangeTransition(0, "desktop_exit");
            prepareDeskDeactivationIfNeeded$default(desktopTasksController, windowContainerTransaction, activeDeskId, 0, 0, 28);
            shellTaskOrganizer.applyTransaction(windowContainerTransaction);
            desktopTasksController.taskRepository.setDeskInactive(activeDeskId.intValue());
            DesktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 = desktopTasksController.exitDesktopModeListener;
            if (desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 != null) {
                desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1.onExitDesktopModeStarted();
            }
        }
    }

    public static void logD$1(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strConcat = "%s: ".concat(str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopTasksController", objArr);
        ProtoLog.d(shellProtoLogGroup, strConcat, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public static void logE(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strConcat = "%s: ".concat(str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopTasksController", objArr);
        ProtoLog.e(shellProtoLogGroup, strConcat, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public static void logV$1(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopTasksController", objArr);
        ProtoLog.v(shellProtoLogGroup, strM, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public static void logW(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopTasksController", objArr);
        ProtoLog.w(shellProtoLogGroup, strM, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public static /* synthetic */ boolean moveTaskToDefaultDeskAndActivate$default(DesktopTasksController desktopTasksController, int i, DesktopModeTransitionSource desktopModeTransitionSource, RemoteTransition remoteTransition, IMoveToDesktopCallback iMoveToDesktopCallback, int i2) {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if ((i2 & 8) != 0) {
            remoteTransition = null;
        }
        return desktopTasksController.moveTaskToDefaultDeskAndActivate(i, windowContainerTransaction, desktopModeTransitionSource, remoteTransition);
    }

    public static boolean moveTaskToDesk$default(DesktopTasksController desktopTasksController, int i, int i2, WindowContainerTransaction windowContainerTransaction, DesktopModeTransitionSource desktopModeTransitionSource, RemoteTransition remoteTransition, int i3) throws Resources.NotFoundException {
        IBinder iBinderMoveToDesktop;
        Function1 function1;
        IBinder iBinderMoveToDesktop2;
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
            logV$1("moveRunningTaskToDesk taskId=%d deskId=%d displayId=%d", Integer.valueOf(runningTaskInfo.taskId), Integer.valueOf(i2), Integer.valueOf(displayForDesk));
            desktopTasksController.exitSplitIfApplicable(windowContainerTransaction2, runningTaskInfo);
            DesktopImmersiveController.ExitResult exitResultExitImmersiveIfApplicable = desktopImmersiveController.exitImmersiveIfApplicable(windowContainerTransaction2, displayForDesk, Integer.valueOf(runningTaskInfo.taskId), DesktopImmersiveController.ExitReason.TASK_LAUNCH);
            Function1 function1AddDeskActivationChanges$default = addDeskActivationChanges$default(desktopTasksController, i2, windowContainerTransaction2, runningTaskInfo, 0, 0, 56);
            WindowContainerTransaction windowContainerTransaction3 = windowContainerTransaction2;
            desktopTasksController.addMoveToDeskTaskChanges(windowContainerTransaction3, runningTaskInfo, i2, null, false);
            if (remoteTransition2 != null) {
                OneShotRemoteHandler oneShotRemoteHandler = new OneShotRemoteHandler(shellExecutor, remoteTransition2);
                iBinderMoveToDesktop2 = transitions.startTransition(3, windowContainerTransaction3, oneShotRemoteHandler);
                oneShotRemoteHandler.mTransition = iBinderMoveToDesktop2;
            } else {
                iBinderMoveToDesktop2 = enterDesktopTaskTransitionHandler.moveToDesktop(windowContainerTransaction3, desktopModeTransitionSource);
            }
            DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 = desktopTasksController.desktopModeEnterExitTransitionListener;
            if (desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 != null) {
                desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1.onEnterDesktopModeTransitionStarted();
            }
            function1AddDeskActivationChanges$default.mo781invoke(iBinderMoveToDesktop2);
            DesktopImmersiveController.ExitResult.Exit exitAsExit = exitResultExitImmersiveIfApplicable.asExit();
            if (exitAsExit != null && (function12 = exitAsExit.runOnTransitionStart) != null) {
                function12.mo781invoke(iBinderMoveToDesktop2);
            }
            if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                return true;
            }
            desktopTasksController.taskRepository.setActiveDesk(displayForDesk, i2);
            return true;
        }
        RecentTasksController recentTasksController = desktopTasksController.recentTasksController;
        if ((recentTasksController != null ? recentTasksController.findTaskInBackground(i) : null) == null) {
            logW("moveTaskToDesk taskId=%d not found", Integer.valueOf(i));
            return false;
        }
        ActivityManager.RecentTaskInfo recentTaskInfoFindTaskInBackground = recentTasksController != null ? recentTasksController.findTaskInBackground(i) : null;
        if (recentTaskInfoFindTaskInBackground == null) {
            logW("moveBackgroundTaskToDesktop taskId=%d not found", Integer.valueOf(i));
            return false;
        }
        logV$1("moveBackgroundTaskToDesktop with taskId=%d", Integer.valueOf(i));
        Integer orCreateDefaultDeskId = desktopTasksController.getOrCreateDefaultDeskId(recentTaskInfoFindTaskInBackground.displayId, false);
        if (orCreateDefaultDeskId == null) {
            return false;
        }
        Function1 function1AddDeskActivationChanges$default2 = addDeskActivationChanges$default(desktopTasksController, orCreateDefaultDeskId.intValue(), windowContainerTransaction2, recentTaskInfoFindTaskInBackground, 0, 0, 56);
        DesktopImmersiveController.ExitResult exitResultExitImmersiveIfApplicable2 = desktopImmersiveController.exitImmersiveIfApplicable(windowContainerTransaction2, 0, Integer.valueOf(i), DesktopImmersiveController.ExitReason.TASK_LAUNCH);
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchWindowingMode(5);
        Unit unit = Unit.INSTANCE;
        windowContainerTransaction2.startTask(i, activityOptionsMakeBasic.toBundle());
        if (remoteTransition2 != null) {
            OneShotRemoteHandler oneShotRemoteHandler2 = new OneShotRemoteHandler(shellExecutor, remoteTransition2);
            iBinderMoveToDesktop = transitions.startTransition(3, windowContainerTransaction2, oneShotRemoteHandler2);
            oneShotRemoteHandler2.mTransition = iBinderMoveToDesktop;
        } else {
            iBinderMoveToDesktop = enterDesktopTaskTransitionHandler.moveToDesktop(windowContainerTransaction2, desktopModeTransitionSource);
        }
        DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$12 = desktopTasksController.desktopModeEnterExitTransitionListener;
        if (desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$12 != null) {
            desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$12.onEnterDesktopModeTransitionStarted();
        }
        function1AddDeskActivationChanges$default2.mo781invoke(iBinderMoveToDesktop);
        DesktopImmersiveController.ExitResult.Exit exitAsExit2 = exitResultExitImmersiveIfApplicable2.asExit();
        if (exitAsExit2 == null || (function1 = exitAsExit2.runOnTransitionStart) == null) {
            return true;
        }
        function1.mo781invoke(iBinderMoveToDesktop);
        return true;
    }

    public static /* synthetic */ DesktopTasksController$$ExternalSyntheticLambda3 performDesktopExitCleanUp$default(DesktopTasksController desktopTasksController, WindowContainerTransaction windowContainerTransaction, Integer num, int i, boolean z, boolean z2, int i2) throws Resources.NotFoundException {
        if ((i2 & 16) != 0) {
            z2 = true;
        }
        return desktopTasksController.performDesktopExitCleanUp(windowContainerTransaction, num, i, z, z2, false);
    }

    public static DesktopTasksController$$ExternalSyntheticLambda3 prepareDeskDeactivationIfNeeded$default(DesktopTasksController desktopTasksController, WindowContainerTransaction windowContainerTransaction, Integer num, int i, int i2, int i3) {
        boolean z = CoreRune.MW_SA_LOGGING;
        if ((i3 & 4) != 0) {
            i = -1;
        }
        if ((i3 & 8) != 0) {
            i2 = -1;
        }
        if ((i3 & 16) != 0) {
            z = false;
        }
        desktopTasksController.getClass();
        if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue() || num == null) {
            return null;
        }
        int iIntValue = num.intValue();
        RootTaskDesksOrganizer rootTaskDesksOrganizer = (RootTaskDesksOrganizer) desktopTasksController.desksOrganizer;
        rootTaskDesksOrganizer.getClass();
        RootTaskDesksOrganizer.logV$2("deactivateDesk %d", num);
        rootTaskDesksOrganizer.updateLaunchRoot(iIntValue, windowContainerTransaction, false);
        for (DesktopRepository desktopRepository : desktopTasksController.userRepositories.getRepositoriesWithDeskId(num.intValue())) {
            if (desktopRepository.desktopData.getDisplayForDesk(num.intValue()) == 0) {
                DesktopStateImpl.Companion.getClass();
                if (DesktopStateImpl.Companion.inDesktopWindowing(0)) {
                    Integer activeDeskId = desktopRepository.getActiveDeskId(0);
                    if (activeDeskId == null || !activeDeskId.equals(num)) {
                        int i4 = desktopTasksController.userId;
                        StringBuilder sb = new StringBuilder("prepareDeskDeactivationIfNeeded: skip, desk=");
                        sb.append(num);
                        sb.append(", active=");
                        sb.append(activeDeskId);
                        sb.append(", repo_user=");
                        KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(sb, desktopRepository.userId, ", current_user=", i4, "ShellDesktopMode");
                    } else if (!z) {
                        DesktopStateImpl.Companion.setInDesktopWindowing(false);
                        desktopTasksController.onDefaultDisplayDesktopModeChanged(false);
                    }
                }
            } else if (i == -1 || i == DesktopStateImpl.desktopExternalDisplayId) {
                DesktopStateImpl.Companion.getClass();
                DesktopStateImpl.Companion.setDesktopExternalDisplayId(-1);
            }
            if (CoreRune.MW_SA_LOGGING && !z) {
                desktopTasksController.desktopExitLogging(desktopRepository.desktopData.getDisplayForDesk(num.intValue()));
            }
        }
        return new DesktopTasksController$$ExternalSyntheticLambda3(desktopTasksController, num, i2);
    }

    public static void removeDesk$default(DesktopTasksController desktopTasksController, int i) {
        DesktopRepository desktopRepository = desktopTasksController.taskRepository;
        desktopTasksController.getClass();
        desktopTasksController.removeDesk(desktopRepository.desktopData.getDisplayForDesk(i), i, desktopRepository);
    }

    public static void setBoundsToDropPosition(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, DisplayLayout displayLayout, PointF pointF, Rect rect) {
        Rect rect2 = new Rect(rect);
        rect2.offsetTo(((int) pointF.x) - (rect2.width() / 2), (int) pointF.y);
        int iWidth = rect2.left;
        int iHeight = rect2.top;
        Rect rect3 = new Rect();
        displayLayout.getStableBounds(rect3, false);
        int i = rect2.right;
        int i2 = rect3.right;
        if (i > i2) {
            iWidth = i2 - rect2.width();
        } else {
            int i3 = rect2.left;
            int i4 = rect3.left;
            if (i3 < i4) {
                iWidth = i4;
            }
        }
        int i5 = rect2.bottom;
        int i6 = rect3.bottom;
        if (i5 > i6) {
            iHeight = i6 - rect2.height();
        } else {
            int i7 = rect2.top;
            int i8 = rect3.top;
            if (i7 < i8) {
                iHeight = i8;
            }
        }
        rect2.offsetTo(iWidth, iHeight);
        windowContainerTransaction.setBounds(runningTaskInfo.token, rect2);
    }

    public static /* synthetic */ void startLaunchTransition$default(DesktopTasksController desktopTasksController, WindowContainerTransaction windowContainerTransaction, int i, int i2) throws Resources.NotFoundException {
        desktopTasksController.startLaunchTransition(1, windowContainerTransaction, null, null, i, i2, DesktopModeEventLogger.Companion.UnminimizeReason.UNKNOWN);
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x015d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void activateDesk(int i, RemoteTransition remoteTransition, int i2, int i3) throws Resources.NotFoundException {
        int i4;
        Integer num;
        int displayForDesk;
        Integer activeDeskId;
        if (this.taskRepository.getAllDeskIds().contains(Integer.valueOf(i))) {
            if (i2 == -1 || i2 == (displayForDesk = this.taskRepository.desktopData.getDisplayForDesk(i)) || (activeDeskId = this.taskRepository.getActiveDeskId(displayForDesk)) == null || i != activeDeskId.intValue() || !(displayForDesk == 0 || i2 == 0)) {
                if ((i2 == 0 || (i2 == -1 && this.taskRepository.desktopData.getDisplayForDesk(i) == 0)) && (i4 = this.desktopDisabledFlagsOnDefaultDisplay) != 0) {
                    logD$1("activateDesk do not activate desk on default display flags=0x%x", Integer.valueOf(i4));
                    showDesktopDisabledToast(null);
                    return;
                }
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                Function1 function1AddDeskActivationChanges$default = addDeskActivationChanges$default(this, i, windowContainerTransaction, null, i2, 0, 44);
                int i5 = 0;
                if (i3 != -1) {
                    List expandedTasksIdsInDeskOrdered = this.taskRepository.getExpandedTasksIdsInDeskOrdered(i);
                    DesktopTasksLimiter desktopTasksLimiter = (DesktopTasksLimiter) this.desktopTasksLimiter.orElse(null);
                    List taskIdsToMinimize$default = desktopTasksLimiter != null ? DesktopTasksLimiter.getTaskIdsToMinimize$default(desktopTasksLimiter, expandedTasksIdsInDeskOrdered, Integer.valueOf(i3)) : null;
                    ShellTaskOrganizer shellTaskOrganizer = this.shellTaskOrganizer;
                    DesksOrganizer desksOrganizer = this.desksOrganizer;
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
                            int iIntValue = num.intValue();
                            desktopTasksLimiter2.desktopUserRepositories.getCurrent();
                            Toast.makeText(desktopTasksLimiter2.displayController.getDisplayContext(displayForDesk2), desktopTasksLimiter2.context.getResources().getQuantityString(android.R.plurals.duration_days_shortest_future, iIntValue, Integer.valueOf(iIntValue)), 0).show();
                        }
                    }
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = shellTaskOrganizer.getRunningTaskInfo(i3);
                    if (runningTaskInfo2 != null) {
                        RootTaskDesksOrganizer rootTaskDesksOrganizer = (RootTaskDesksOrganizer) desksOrganizer;
                        RootTaskDesksOrganizer.DeskRoot deskRoot = (RootTaskDesksOrganizer.DeskRoot) rootTaskDesksOrganizer.deskRootsByDeskId.get(i);
                        if (deskRoot == null) {
                            throw new IllegalStateException(("Root not found for desk: " + i).toString());
                        }
                        Object obj = ((LinkedHashMap) rootTaskDesksOrganizer.deskMinimizationRootsByDeskId).get(Integer.valueOf(i));
                        if (obj == null) {
                            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Minimization root not found for desk: ").toString());
                        }
                        RootTaskDesksOrganizer.DeskMinimizationRoot deskMinimizationRoot = (RootTaskDesksOrganizer.DeskMinimizationRoot) obj;
                        if (deskRoot.children.contains(Integer.valueOf(runningTaskInfo2.taskId)) || deskMinimizationRoot.children.contains(Integer.valueOf(runningTaskInfo2.taskId))) {
                            rootTaskDesksOrganizer.reorderTaskToFront(windowContainerTransaction, i, runningTaskInfo2);
                        } else {
                            logV$1("moveBackgroundTaskToFront taskId=%s", Integer.valueOf(i3));
                            DisplayLayout displayLayout = this.displayController.getDisplayLayout(this.taskRepository.desktopData.getDisplayForDesk(i));
                            if (displayLayout != null) {
                                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                                activityOptionsMakeBasic.setLaunchWindowingMode(5);
                                activityOptionsMakeBasic.setLaunchBounds(DesktopModeUtils.calculateDefaultDesktopTaskBounds(displayLayout));
                                Unit unit = Unit.INSTANCE;
                                windowContainerTransaction.startTask(i3, activityOptionsMakeBasic.toBundle());
                            }
                        }
                    }
                }
                windowContainerTransaction.setTransactionType(4);
                if (remoteTransition == null) {
                    logV$1("RemoteTransition is null", new Object[0]);
                } else {
                    i5 = 3;
                }
                Transitions transitions = this.transitions;
                OneShotRemoteHandler oneShotRemoteHandler = remoteTransition != null ? new OneShotRemoteHandler(transitions.mMainExecutor, remoteTransition) : null;
                IBinder iBinderStartTransition = transitions.startTransition(i5, windowContainerTransaction, oneShotRemoteHandler);
                if (oneShotRemoteHandler != null) {
                    oneShotRemoteHandler.mTransition = iBinderStartTransition;
                }
                iBinderStartTransition.getClass();
                function1AddDeskActivationChanges$default.mo781invoke(iBinderStartTransition);
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
        boolean zIsDeskActive = current.isDeskActive(i);
        DesktopRepository.DesktopData desktopData = current.desktopData;
        DesksOrganizer desksOrganizer = desktopTasksLimiter.desksOrganizer;
        DisplayController displayController = desktopTasksLimiter.displayController;
        ShellTaskOrganizer shellTaskOrganizer = desktopTasksLimiter.shellTaskOrganizer;
        if (!zIsDeskActive) {
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
                    int iIntValue = num2.intValue();
                    Toast.makeText(displayController.getDisplayContext(desktopData.getDisplayForDesk(i)), desktopTasksLimiter.context.getResources().getQuantityString(android.R.plurals.duration_days_shortest_future, iIntValue, Integer.valueOf(iIntValue)), 0).show();
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
                    int iIntValue2 = num3.intValue();
                    Toast.makeText(displayController.getDisplayContext(desktopData.getDisplayForDesk(i)), desktopTasksLimiter.context.getResources().getQuantityString(android.R.plurals.duration_days_shortest_future, iIntValue2, Integer.valueOf(iIntValue2)), 0).show();
                }
                ((RootTaskDesksOrganizer) desksOrganizer).minimizeTask(windowContainerTransaction, i, runningTaskInfo2);
                return taskIdToMinimize2;
            }
            current.removeTaskFromDesk(i, taskIdToMinimize2.intValue());
        }
    }

    public final void addDesktopDisabledFlagsOnDefaultDisplay(int i) {
        DesktopModeWindowDecorViewModel.AnonymousClass1 anonymousClass1;
        DesktopTasksController$IDesktopModeImpl$deskChangeListener$1 desktopTasksController$IDesktopModeImpl$deskChangeListener$1;
        if ((this.desktopDisabledFlagsOnDefaultDisplay & i) != 0) {
            return;
        }
        boolean zCanCreateDesks$default = canCreateDesks$default(this);
        int i2 = i | this.desktopDisabledFlagsOnDefaultDisplay;
        this.desktopDisabledFlagsOnDefaultDisplay = i2;
        DesktopTasksController$IDesktopModeImpl$desktopDisabledFlagsListener$1 desktopTasksController$IDesktopModeImpl$desktopDisabledFlagsListener$1 = this.desktopDisabledFlagsListener;
        if (desktopTasksController$IDesktopModeImpl$desktopDisabledFlagsListener$1 != null) {
            desktopTasksController$IDesktopModeImpl$desktopDisabledFlagsListener$1.onDesktopDisabledFlagsChangedOnDefaultDisplay(i2);
        }
        boolean zCanCreateDesks$default2 = canCreateDesks$default(this);
        if (zCanCreateDesks$default != zCanCreateDesks$default2 && (desktopTasksController$IDesktopModeImpl$deskChangeListener$1 = this.deskChangeListener) != null) {
            desktopTasksController$IDesktopModeImpl$deskChangeListener$1.onCanCreateDesksChanged(zCanCreateDesks$default2);
        }
        if (!CoreRune.MW_CAPTION_DESKTOP_DISABLED || (anonymousClass1 = this.decorViewModelDesktopDisabledChangeListener) == null) {
            return;
        }
        anonymousClass1.onDesktopDisabledFlagsChangedOnDefaultDisplay(this.desktopDisabledFlagsOnDefaultDisplay);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addMoveToDeskTaskChanges(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, int i, PointF pointF, boolean z) throws Resources.NotFoundException {
        ActivityInfo.WindowLayout windowLayout;
        int displayForDesk = this.taskRepository.desktopData.getDisplayForDesk(i);
        DisplayLayout displayLayout = this.displayController.getDisplayLayout(displayForDesk);
        if (displayLayout == null) {
            return;
        }
        Rect inheritedExistingTaskBounds = DesktopModeUtils.getInheritedExistingTaskBounds(this.taskRepository, this.shellTaskOrganizer, runningTaskInfo, i);
        if (z) {
            Slog.e("DesktopTasksController", "addMoveToDeskTaskChanges: keep current systemModalTask");
            return;
        }
        if (inheritedExistingTaskBounds == null) {
            Rect initialBounds = getInitialBounds(displayLayout, runningTaskInfo, displayForDesk);
            if (pointF != null) {
                setBoundsToDropPosition(windowContainerTransaction, runningTaskInfo, displayLayout, pointF, initialBounds);
            } else {
                ActivityInfo activityInfo = ((TaskInfo) runningTaskInfo).topActivityInfo;
                if (activityInfo == null || (windowLayout = activityInfo.windowLayout) == null) {
                    windowContainerTransaction.setBounds(runningTaskInfo.token, initialBounds);
                } else {
                    int i2 = windowLayout.gravity;
                    int i3 = i2 & 7;
                    int i4 = i2 & 112;
                    if (i3 == 0 && i4 == 0) {
                    }
                }
            }
        } else if (pointF != null) {
            setBoundsToDropPosition(windowContainerTransaction, runningTaskInfo, displayLayout, pointF, inheritedExistingTaskBounds);
        } else {
            windowContainerTransaction.setBounds(runningTaskInfo.token, inheritedExistingTaskBounds).getClass();
        }
        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            ((RootTaskDesksOrganizer) this.desksOrganizer).moveTaskToDesk(windowContainerTransaction, i, runningTaskInfo);
        } else {
            DisplayAreaInfo displayAreaInfo = this.rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(displayForDesk);
            displayAreaInfo.getClass();
            windowContainerTransaction.setWindowingMode(runningTaskInfo.token, displayAreaInfo.configuration.windowConfiguration.getWindowingMode() == 5 ? 0 : 5);
            windowContainerTransaction.reorder(runningTaskInfo.token, true).getClass();
        }
        DesktopConfigImpl desktopConfigImpl = (DesktopConfigImpl) this.desktopConfig;
        if (desktopConfigImpl.useDesktopOverrideDensity) {
            windowContainerTransaction.setDensityDpi(runningTaskInfo.token, desktopConfigImpl.desktopDensityOverride);
        }
        windowContainerTransaction.setSkipLayoutTask(runningTaskInfo.token, true);
    }

    public final DesktopTasksController$$ExternalSyntheticLambda3 addMoveToFullscreenChanges(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        DisplayAreaInfo displayAreaInfo = this.rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(runningTaskInfo.displayId);
        displayAreaInfo.getClass();
        int windowingMode = displayAreaInfo.configuration.windowConfiguration.getWindowingMode();
        boolean zIsTopActivityExemptFromDesktopWindowing = this.desktopModeCompatPolicy.isTopActivityExemptFromDesktopWindowing(runningTaskInfo);
        boolean z2 = false;
        windowContainerTransaction.setWindowingMode(runningTaskInfo.token, (windowingMode != 1 || zIsTopActivityExemptFromDesktopWindowing) ? 1 : 0);
        windowContainerTransaction.setBounds(runningTaskInfo.token, new Rect());
        if (((DesktopConfigImpl) this.desktopConfig).useDesktopOverrideDensity) {
            windowContainerTransaction.setDensityDpi(runningTaskInfo.token, this.context.getResources().getDisplayMetrics().densityDpi);
        }
        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue() && !zIsTopActivityExemptFromDesktopWindowing) {
            windowContainerTransaction.reparent(runningTaskInfo.token, displayAreaInfo.token, true);
        }
        if (((RootTaskDesksOrganizer) this.desksOrganizer).deskRootsByDeskId.contains(runningTaskInfo.parentTaskId) && !zIsTopActivityExemptFromDesktopWindowing && (runningTaskInfo2 = this.shellTaskOrganizer.getRunningTaskInfo(runningTaskInfo.parentTaskId)) != null) {
            windowContainerTransaction.reorder(runningTaskInfo2.token, false);
        }
        if (DesktopModeCompatPolicy.isTransparentTask(((TaskInfo) runningTaskInfo).numActivities, ((TaskInfo) runningTaskInfo).isActivityStackTransparent) && zIsTopActivityExemptFromDesktopWindowing) {
            z2 = true;
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
        this.desktopTasksLimiter.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new DesktopTasksController$$ExternalSyntheticLambda8(iBinder, this.shellTaskOrganizer.getRunningTaskInfo(i), i, minimizeReason)));
    }

    public final void cascadeWindow(Rect rect, DisplayLayout displayLayout, int i) throws Resources.NotFoundException {
        Object obj;
        Rect rect2 = new Rect();
        displayLayout.getStableBounds(rect2, false);
        int i2 = displayLayout.mNavBarFrameHeight;
        int i3 = displayLayout.mTaskbarFrameHeight;
        if (i2 != i3) {
            rect2.bottom = displayLayout.mHeight - i3;
        }
        ArrayList arrayList = (ArrayList) this.taskRepository.getExpandedTasksOrdered(i);
        int size = arrayList.size();
        int i4 = 0;
        while (true) {
            if (i4 >= size) {
                obj = null;
                break;
            }
            obj = arrayList.get(i4);
            i4++;
            if (!this.taskRepository.isClosingTask(((Number) obj).intValue())) {
                break;
            }
        }
        Integer num = (Integer) obj;
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
                int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.freeform_required_visible_empty_space_in_header);
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
            function1.mo781invoke(null);
            return;
        }
        if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            logD$1("createDesk reusing displayId=%d for single-desk", Integer.valueOf(i));
            function1.mo781invoke(Integer.valueOf(i));
            return;
        }
        int i3 = 0;
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_HSUM.isTrue() && UserManager.isHeadlessSystemUserMode() && i2 == 0) {
            logW("createDesk ignoring attempt for system user", new Object[0]);
            function1.mo781invoke(null);
            return;
        }
        C12011 c12011 = new C12011(i, i2, function1);
        RootTaskDesksOrganizer rootTaskDesksOrganizer = (RootTaskDesksOrganizer) this.desksOrganizer;
        Object[] objArr = {Integer.valueOf(i), Integer.valueOf(i2)};
        rootTaskDesksOrganizer.getClass();
        RootTaskDesksOrganizer.logV$2("createDesk in displayId=%d userId=%s", objArr);
        RootTaskDesksOrganizer.DeskRoot deskRootFirstUnassignedDesk = rootTaskDesksOrganizer.firstUnassignedDesk(i, i2);
        if (deskRootFirstUnassignedDesk != null) {
            deskRootFirstUnassignedDesk.users.add(Integer.valueOf(i2));
            c12011.onCreated(deskRootFirstUnassignedDesk.deskId);
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
        Integer numValueOf = Integer.valueOf(i2);
        RootTaskDesksOrganizer.logV$2("createDeskRoot in display: %d for user: %d", Integer.valueOf(i), numValueOf);
        ((ArrayList) rootTaskDesksOrganizer.createDeskRootRequests).add(new RootTaskDesksOrganizer.CreateDeskRequest(i, numValueOf, c12011));
        rootTaskDesksOrganizer.shellTaskOrganizer.createDeskRootTask(i, 1, -1, rootTaskDesksOrganizer);
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
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchWindowingMode(i);
        activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(3);
        activityOptionsMakeBasic.setLaunchBounds(initialBounds);
        return activityOptionsMakeBasic;
    }

    public final void desktopExitLogging(int i) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long j = SA_DURATION_UNIT_MS;
        if (i != 0) {
            DesktopStateImpl.Companion.getClass();
            if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                Long l = this.desktopStartMillisOnExternal;
                if (l != null) {
                    CoreSaLogger.logForDexWithScreenId("300", "3901", (jElapsedRealtime - l.longValue()) / j);
                }
                this.desktopStartMillisOnExternal = null;
            }
        }
        DesktopStateImpl.Companion.getClass();
        if (DesktopStateImpl.inDesktopWindowing || DesktopStateImpl.desktopExternalDisplayId != -1) {
            return;
        }
        Long l2 = this.desktopStartMillis;
        if (l2 != null) {
            CoreSaLogger.logForDexWithScreenId("300", "3900", String.valueOf((jElapsedRealtime - l2.longValue()) / j), i == 0 ? 1 : 2);
        }
        this.desktopStartMillis = null;
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
            int iIntValue = ((Number) obj).intValue();
            if (num == null || iIntValue != num.intValue()) {
                arrayList.add(obj);
            }
        }
        if (arrayList.isEmpty()) {
            z = false;
        } else {
            int size2 = arrayList.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList.get(i3);
                i3++;
                ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(((Number) obj2).intValue());
                if (runningTaskInfo == null) {
                    return false;
                }
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
                int i4 = DesktopModeUtils.DESKTOP_MODE_LANDSCAPE_APP_PADDING;
                logD$1("isMaximizedToStableBoundsEdges(taskInfo, stableBounds) = %s", Boolean.valueOf(Intrinsics.areEqual(bounds2, rect)));
                Rect bounds3 = runningTaskInfo.configuration.windowConfiguration.getBounds();
                if (getSnapBounds(runningTaskInfo, snapPosition).equals(bounds3) || getSnapBounds(runningTaskInfo, SnapPosition.RIGHT).equals(bounds3) || Intrinsics.areEqual(runningTaskInfo.configuration.windowConfiguration.getBounds(), rect)) {
                    break;
                }
            }
            z = false;
        }
        logD$1("doesAnyTaskRequireTaskbarRounding = %s", Boolean.valueOf(z));
        return z;
    }

    public final void dump$2(PrintWriter printWriter, String str) throws Resources.NotFoundException {
        String str2;
        String str3 = "  ";
        String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "DesktopTasksController");
        int i = this.desktopDisabledFlagsOnDefaultDisplay;
        if (i != 0) {
            CharsKt__CharJVMKt.checkRadix(16);
            printWriter.println(strM + "desktopDisabledOnDefaultDisplay=0x" + Integer.toString(i, 16));
        }
        DesktopModeVisualIndicator desktopModeVisualIndicator = this.visualIndicator;
        if (desktopModeVisualIndicator != null) {
            printWriter.println(str + "visualIndicator=" + desktopModeVisualIndicator);
        }
        DragToDesktopTransitionHandler dragToDesktopTransitionHandler = this.dragToDesktopTransitionHandler;
        dragToDesktopTransitionHandler.getClass();
        String str4 = strM + "    ";
        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, strM, "[DragToDesktopTransitionHandler]");
        printWriter.println(str4 + "transitionState=" + dragToDesktopTransitionHandler.transitionState);
        StringBuilder sb = new StringBuilder();
        sb.append(str4);
        CarrierTextController$$ExternalSyntheticOutline0.m(sb, "(LogHistory)", printWriter);
        Iterator it = dragToDesktopTransitionHandler.logHistory.iterator();
        while (it.hasNext()) {
            QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str4, (String) it.next());
        }
        DesktopConfigImpl desktopConfigImpl = (DesktopConfigImpl) this.desktopConfig;
        desktopConfigImpl.getClass();
        new IndentingPrintWriter(printWriter, "  ", strM).increaseIndent();
        printWriter.println("DesktopConfig");
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("maxTaskLimit=", desktopConfigImpl.maxTaskLimit, printWriter);
        printWriter.print("maxTaskLimit config override=" + desktopConfigImpl.context.getResources().getInteger(android.R.integer.config_previousVibrationsDumpSizeLimit));
        SystemProperties.Handle handleFind = SystemProperties.find("persist.wm.debug.desktop_max_task_limit");
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("maxTaskLimit sysprop=", handleFind != null ? Integer.valueOf(handleFind.getInt(-1)) : "null", printWriter);
        desktopConfigImpl.desktopState.getClass();
        printWriter.println("showAppHandle config override=false");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "inDesktopWindowing=", DesktopStateImpl.inDesktopWindowing);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("desktopExternalDisplayId=", DesktopStateImpl.desktopExternalDisplayId, printWriter);
        DesktopUserRepositories desktopUserRepositories = this.userRepositories;
        desktopUserRepositories.getClass();
        String str5 = strM + "    ";
        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, strM, "DesktopUserRepositories:");
        printWriter.println(str5 + "currentUserId=" + desktopUserRepositories.userId);
        DesktopUserRepositories$desktopRepoByUserId$1 desktopUserRepositories$desktopRepoByUserId$1 = desktopUserRepositories.desktopRepoByUserId;
        int size = desktopUserRepositories$desktopRepoByUserId$1.size();
        int i2 = 0;
        while (i2 < size) {
            desktopUserRepositories$desktopRepoByUserId$1.keyAt(i2);
            DesktopRepository desktopRepository = (DesktopRepository) desktopUserRepositories$desktopRepoByUserId$1.valueAt(i2);
            desktopRepository.getClass();
            String str6 = str5 + str3;
            printWriter.println(str5 + "DesktopRepository");
            printWriter.println(str6 + "userId=" + desktopRepository.userId);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str6);
            sb2.append(str3);
            String string = sb2.toString();
            DesktopRepository.DesktopData desktopData = desktopRepository.desktopData;
            Sequence sequenceDesksSequence = desktopData.desksSequence();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : sequenceDesksSequence) {
                DesktopUserRepositories$desktopRepoByUserId$1 desktopUserRepositories$desktopRepoByUserId$12 = desktopUserRepositories$desktopRepoByUserId$1;
                Integer numValueOf = Integer.valueOf(((DesktopRepository.Desk) obj).displayId);
                Object obj2 = linkedHashMap.get(numValueOf);
                if (obj2 == null) {
                    str2 = str5;
                    ArrayList arrayList = new ArrayList();
                    linkedHashMap.put(numValueOf, arrayList);
                    obj2 = arrayList;
                } else {
                    str2 = str5;
                }
                ((List) obj2).add(obj);
                desktopUserRepositories$desktopRepoByUserId$1 = desktopUserRepositories$desktopRepoByUserId$12;
                str5 = str2;
            }
            DesktopUserRepositories$desktopRepoByUserId$1 desktopUserRepositories$desktopRepoByUserId$13 = desktopUserRepositories$desktopRepoByUserId$1;
            String str7 = str5;
            ArrayList arrayList2 = new ArrayList(linkedHashMap.size());
            Iterator it2 = linkedHashMap.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry entry = (Map.Entry) it2.next();
                int iIntValue = ((Number) entry.getKey()).intValue();
                List list = (List) entry.getValue();
                Iterator it3 = it2;
                Integer numValueOf2 = Integer.valueOf(iIntValue);
                DesktopRepository.Desk activeDesk = desktopData.getActiveDesk(iIntValue);
                arrayList2.add(new Triple(numValueOf2, activeDesk != null ? Integer.valueOf(activeDesk.deskId) : null, list));
                it2 = it3;
            }
            int size2 = arrayList2.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj3 = arrayList2.get(i3);
                i3++;
                Triple triple = (Triple) obj3;
                int iIntValue2 = ((Number) triple.component1()).intValue();
                Integer num = (Integer) triple.component2();
                List<DesktopRepository.Desk> list2 = (List) triple.component3();
                ArrayList arrayList3 = arrayList2;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str6);
                int i4 = size2;
                sb3.append("Display #");
                sb3.append(iIntValue2);
                sb3.append(":");
                printWriter.println(sb3.toString());
                int size3 = list2.size();
                StringBuilder sb4 = new StringBuilder();
                sb4.append(string);
                int i5 = size;
                sb4.append("numOfDesks=");
                sb4.append(size3);
                printWriter.println(sb4.toString());
                printWriter.println(string + "activeDesk=" + num);
                printWriter.println(string + "desks:");
                String str8 = string + str3;
                for (DesktopRepository.Desk desk : list2) {
                    int i6 = desk.deskId;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(str8);
                    sb5.append("Desk #");
                    sb5.append(i6);
                    sb5.append(":");
                    printWriter.println(sb5.toString());
                    printWriter.print(str8 + "  activeTasks=");
                    printWriter.println(CollectionsKt___CollectionsKt.joinToString$default(desk.activeTasks, ", ", "[", "]", null, 56));
                    printWriter.print(str8 + "  visibleTasks=");
                    printWriter.println(CollectionsKt___CollectionsKt.joinToString$default(desk.visibleTasks, ", ", "[", "]", null, 56));
                    printWriter.print(str8 + "  freeformTasksInZOrder=");
                    printWriter.println(CollectionsKt___CollectionsKt.joinToString$default(desk.freeformTasksInZOrder, ", ", "[", "]", null, 56));
                    printWriter.print(str8 + "  minimizedTasks=");
                    printWriter.println(CollectionsKt___CollectionsKt.joinToString$default(desk.minimizedTasks, ", ", "[", "]", null, 56));
                    printWriter.print(str8 + "  fullImmersiveTaskId=");
                    printWriter.println(desk.fullImmersiveTaskId);
                    printWriter.print(str8 + "  topTransparentFullscreenTaskId=");
                    printWriter.println(desk.topTransparentFullscreenTaskId);
                    printWriter.print(str8 + "  usedDesk=");
                    printWriter.println(desk.usedDesk);
                    printWriter.print(str8 + "  deskLabel=");
                    printWriter.println(desk.deskLabel);
                    str3 = str3;
                }
                arrayList2 = arrayList3;
                size2 = i4;
                size = i5;
            }
            printWriter.println(str6 + "activeTasksListeners=" + desktopRepository.activeTasksListeners.size());
            printWriter.println(str6 + "visibleTasksListeners=" + desktopRepository.visibleTasksListeners.size());
            i2++;
            desktopUserRepositories$desktopRepoByUserId$1 = desktopUserRepositories$desktopRepoByUserId$13;
            str5 = str7;
            size = size;
            str3 = str3;
        }
        FocusTransitionObserver focusTransitionObserver = this.focusTransitionObserver;
        focusTransitionObserver.getClass();
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "    ", strM);
        indentingPrintWriter.println("FocusTransitionObserver:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf("currentFocusedDisplayId=%d\n", new Object[]{Integer.valueOf(focusTransitionObserver.mFocusedDisplayId)});
        indentingPrintWriter.println("currentFocusedTaskOnDisplay:");
        indentingPrintWriter.increaseIndent();
        for (int i7 = 0; i7 < focusTransitionObserver.mFocusedTaskOnDisplay.size(); i7++) {
            indentingPrintWriter.printf("Display #%d: taskId=%d topActivity=%s\n", new Object[]{Integer.valueOf(focusTransitionObserver.mFocusedTaskOnDisplay.keyAt(i7)), Integer.valueOf(((ActivityManager.RunningTaskInfo) focusTransitionObserver.mFocusedTaskOnDisplay.valueAt(i7)).taskId), ((ActivityManager.RunningTaskInfo) focusTransitionObserver.mFocusedTaskOnDisplay.valueAt(i7)).topActivity});
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

    public final Rect getInitialBounds(DisplayLayout displayLayout, ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws Resources.NotFoundException {
        Rect rectCalculateDefaultDesktopTaskBounds;
        if (DesktopModeFlags.ENABLE_WINDOWING_DYNAMIC_INITIAL_BOUNDS.isTrue()) {
            this.desktopModeCompatPolicy.getClass();
            ActivityInfo activityInfo = ((TaskInfo) runningTaskInfo).topActivityInfo;
            rectCalculateDefaultDesktopTaskBounds = DesktopModeUtils.calculateInitialBounds$default(displayLayout, runningTaskInfo, activityInfo != null ? DesktopModeCompatUtils.shouldExcludeCaptionFromAppBounds(activityInfo, ((TaskInfo) runningTaskInfo).isResizeable, ((TaskInfo) runningTaskInfo).appCompatTaskInfo.hasOptOutEdgeToEdge()) : false ? SystemBarUtils.getDesktopViewAppHeaderHeightPx(this.context) : 0, null, 20);
        } else {
            rectCalculateDefaultDesktopTaskBounds = DesktopModeUtils.calculateDefaultDesktopTaskBounds(displayLayout);
        }
        if (DesktopModeFlags.ENABLE_CASCADING_WINDOWS.isTrue()) {
            cascadeWindow(rectCalculateDefaultDesktopTaskBounds, displayLayout, i);
        }
        return rectCalculateDefaultDesktopTaskBounds;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00c0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Integer getOrCreateDefaultDeskId(int i, boolean z) {
        Integer numValueOf;
        Integer lastUsedDeskIdInDefaultDisplay;
        if (z && i == 0 && (lastUsedDeskIdInDefaultDisplay = this.taskRepository.getLastUsedDeskIdInDefaultDisplay()) != null) {
            return lastUsedDeskIdInDefaultDisplay;
        }
        Integer defaultDeskId = this.taskRepository.getDefaultDeskId(i);
        if (defaultDeskId != null) {
            return defaultDeskId;
        }
        int i2 = this.userId;
        logV$1("createDeskImmediate displayId=%d, userId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        DesktopRepository profile = this.userRepositories.getProfile(i2);
        Integer num = null;
        if (i != -1) {
            if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                logD$1("createDeskRootImmediate reusing displayId=%d for single-desk", Integer.valueOf(i));
                numValueOf = Integer.valueOf(i);
            } else if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_HSUM.isTrue() && UserManager.isHeadlessSystemUserMode() && i2 == 0) {
                logW("createDeskRootImmediate ignoring attempt for system user", new Object[0]);
            } else {
                RootTaskDesksOrganizer rootTaskDesksOrganizer = (RootTaskDesksOrganizer) this.desksOrganizer;
                Object[] objArr = {Integer.valueOf(i), Integer.valueOf(i2)};
                rootTaskDesksOrganizer.getClass();
                RootTaskDesksOrganizer.logV$2("createDeskImmediate in displayId=%d userId=%s", objArr);
                RootTaskDesksOrganizer.DeskRoot deskRootFirstUnassignedDesk = rootTaskDesksOrganizer.firstUnassignedDesk(i, i2);
                if (deskRootFirstUnassignedDesk != null) {
                    deskRootFirstUnassignedDesk.users.add(Integer.valueOf(i2));
                    numValueOf = Integer.valueOf(deskRootFirstUnassignedDesk.deskId);
                }
            }
            if (numValueOf != null) {
                logW("Failed to add desk in displayId=%d for userId=%d", Integer.valueOf(i), Integer.valueOf(i2));
            } else {
                profile.addDesk(i, numValueOf.intValue(), -1);
                num = numValueOf;
            }
            if (num == null) {
                logE("Failed to create immediate desk in displayId=%s for userId=%s:\n%s", Integer.valueOf(i), Integer.valueOf(this.userId), ExceptionsKt__ExceptionsKt.stackTraceToString(new Throwable()));
            }
            return num;
        }
        logW("createDeskRootImmediate attempt with invalid displayId", Integer.valueOf(i));
        numValueOf = null;
        if (numValueOf != null) {
        }
        if (num == null) {
        }
        return num;
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
        displayLayout.getStableBoundsByInsetsVisibility(rect);
        int iWidth = rect.width() / 2;
        int i = WhenMappings.$EnumSwitchMapping$0[snapPosition.ordinal()];
        if (i == 1) {
            int i2 = rect.left;
            return new Rect(i2, rect.top, iWidth + i2, rect.bottom);
        }
        if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        int i3 = rect.right;
        return new Rect(i3 - iWidth, rect.top, i3, rect.bottom);
    }

    public final DesktopModeVisualIndicator getVisualIndicator() {
        return this.visualIndicator;
    }

    /* JADX WARN: Removed duplicated region for block: B:129:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0373  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0456  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x05d1  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x0655  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x0666  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x0680  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0691  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x0693  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x06b7  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x06c2  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x06d5  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x06df A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:330:0x06f5  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x06fe  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x070c A[ADDED_TO_REGION] */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final WindowContainerTransaction handleRequest(final IBinder iBinder, TransitionRequestInfo transitionRequestInfo) throws Resources.NotFoundException {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        WindowContainerTransaction windowContainerTransaction;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        DesktopTasksController desktopTasksController;
        WindowContainerTransaction windowContainerTransaction2;
        boolean z5;
        WindowContainerTransaction windowContainerTransaction3;
        Integer activeDeskId;
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        Rect inheritedExistingTaskBounds;
        DesktopConfigImpl desktopConfigImpl;
        WindowContainerTransaction windowContainerTransaction4;
        IBinder iBinder2;
        Function1 function1;
        Integer numAddAndGetMinimizeChanges;
        DisplayLayout displayLayout;
        Function1 function1AddDeskActivationChanges$default;
        DesktopImmersiveController.ExitResult.Exit exitAsExit;
        Function1 function12;
        Integer deskIdForTask;
        ActivityManager.RunningTaskInfo triggerTask;
        ArrayList arrayList;
        int type = transitionRequestInfo.getType();
        ActivityManager.RunningTaskInfo triggerTask2 = transitionRequestInfo.getTriggerTask();
        Integer numValueOf = triggerTask2 != null ? Integer.valueOf(triggerTask2.taskId) : null;
        String strM = "";
        String strM2 = transitionRequestInfo.getFlags() == 0 ? "" : ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(transitionRequestInfo.getFlags(), "f=", ", ");
        String str = transitionRequestInfo.getPipChange() == null ? "" : "pip=" + transitionRequestInfo.getPipChange() + ", ";
        String str2 = transitionRequestInfo.getDisplayChange() == null ? "" : "d=" + transitionRequestInfo.getDisplayChange() + ", ";
        String str3 = transitionRequestInfo.getRemoteTransition() == null ? "" : "remote=true, ";
        int debugId = transitionRequestInfo.getDebugId();
        StringBuilder sb = new StringBuilder("TransitionRequestInfo {type=");
        sb.append(type);
        sb.append(", tid=");
        sb.append(numValueOf);
        sb.append(", ");
        MoveResult$$ExternalSyntheticOutline0.m(sb, strM2, str, str2, str3);
        sb.append("debugId=");
        sb.append(debugId);
        sb.append("}");
        Log.d("ShellDesktopMode", "handleRequest: " + sb.toString());
        TransitionRequestInfo.DisplayChange displayChange = transitionRequestInfo.getDisplayChange();
        if (DesktopExperienceFlags.ENABLE_DISPLAY_DISCONNECT_INTERACTION.isTrue() && displayChange != null && displayChange.getDisconnectReparentDisplay() != -1) {
            final int displayId = displayChange.getDisplayId();
            final int disconnectReparentDisplay = displayChange.getDisconnectReparentDisplay();
            final WindowContainerTransaction windowContainerTransaction5 = new WindowContainerTransaction();
            WindowContainerToken token = this.desktopWallpaperActivityTokenProvider.getToken(displayId);
            if (token != null) {
                logV$1("removeWallpaperTask", new Object[0]);
                windowContainerTransaction5.removeTask(token);
            }
            logV$1("removeHomeTask in displayId=%d", Integer.valueOf(displayId));
            ActivityManager.RunningTaskInfo homeTask = getHomeTask(displayId);
            if (homeTask != null) {
                windowContainerTransaction5.removeRootTask(homeTask.getToken());
            }
            Function1 function13 = new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    WindowContainerTransaction windowContainerTransaction6 = windowContainerTransaction5;
                    IBinder iBinder3 = iBinder;
                    DesktopRepository desktopRepository = (DesktopRepository) obj;
                    DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                    DesktopRepository.DesktopData desktopData = desktopRepository.desktopData;
                    int i = displayId;
                    List list = CollectionsKt___CollectionsKt.toList(SequencesKt___SequencesKt.toSet(new TransformingSequence(desktopData.desksSequence(i), new DesktopRepository$$ExternalSyntheticLambda0(2))));
                    DesktopTasksController desktopTasksController2 = this;
                    DesktopStateImpl desktopStateImpl = (DesktopStateImpl) desktopTasksController2.desktopState;
                    int i2 = disconnectReparentDisplay;
                    boolean zIsDesktopModeSupportedOnDisplay = desktopStateImpl.isDesktopModeSupportedOnDisplay(i2);
                    ShellTaskOrganizer shellTaskOrganizer = desktopTasksController2.shellTaskOrganizer;
                    DesksTransitionObserver desksTransitionObserver = desktopTasksController2.desksTransitionObserver;
                    if (zIsDesktopModeSupportedOnDisplay) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            int iIntValue = ((Number) it.next()).intValue();
                            RootTaskDesksOrganizer rootTaskDesksOrganizer = (RootTaskDesksOrganizer) desktopTasksController2.desksOrganizer;
                            Object[] objArr = {Integer.valueOf(iIntValue), Integer.valueOf(i2), Boolean.FALSE};
                            rootTaskDesksOrganizer.getClass();
                            RootTaskDesksOrganizer.logV$2("moveDeskToDisplay deskId=%d, displayId=%d, toTop=%b", objArr);
                            DisplayAreaInfo displayAreaInfo = rootTaskDesksOrganizer.rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(i2);
                            if (displayAreaInfo == null) {
                                throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "DisplayAreaInfo not found for displayId=").toString());
                            }
                            Object obj2 = rootTaskDesksOrganizer.deskRootsByDeskId.get(iIntValue);
                            if (obj2 == null) {
                                throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iIntValue, "Root not found for desk: ").toString());
                            }
                            RootTaskDesksOrganizer.DeskRoot deskRoot = (RootTaskDesksOrganizer.DeskRoot) obj2;
                            windowContainerTransaction6.reparent(deskRoot.token, displayAreaInfo.token, false);
                            RootTaskDesksOrganizer.DeskMinimizationRoot deskMinimizationRoot = (RootTaskDesksOrganizer.DeskMinimizationRoot) ((LinkedHashMap) rootTaskDesksOrganizer.deskMinimizationRootsByDeskId).get(Integer.valueOf(iIntValue));
                            if (deskMinimizationRoot == null) {
                                throw new IllegalStateException(("Minimization root not found for desk: " + iIntValue).toString());
                            }
                            windowContainerTransaction6.reparent(deskMinimizationRoot.token, displayAreaInfo.token, false);
                            windowContainerTransaction6.setWindowingMode(deskRoot.token, 5);
                            windowContainerTransaction6.setWindowingMode(deskMinimizationRoot.token, 5);
                            ActivityManager.RunningTaskInfo runningTaskInfo3 = shellTaskOrganizer.getRunningTaskInfo(iIntValue);
                            if (runningTaskInfo3 != null) {
                                windowContainerTransaction6.setReparentLeafTaskIfRelaunch(runningTaskInfo3.token, false);
                            }
                            desksTransitionObserver.addPendingTransition(new DeskTransition.ChangeDeskDisplay(iBinder3, iIntValue, i2));
                            int i3 = i2;
                            DesktopTasksController desktopTasksController3 = desktopTasksController2;
                            int i4 = i;
                            Function1 function1UpdateDesksActivationOnDisconnection = desktopTasksController3.updateDesksActivationOnDisconnection(i4, desktopRepository.userId, iIntValue, i3, windowContainerTransaction6, false);
                            desktopTasksController2 = desktopTasksController3;
                            if (function1UpdateDesksActivationOnDisconnection != null) {
                                function1UpdateDesksActivationOnDisconnection.mo781invoke(iBinder3);
                            }
                            i = i4;
                            i2 = i3;
                        }
                    } else {
                        if (desktopTasksController2.rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(i2) == null) {
                            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "Expected to find displayAreaInfo for displayId=").toString());
                        }
                        Iterator it2 = list.iterator();
                        while (it2.hasNext()) {
                            int iIntValue2 = ((Number) it2.next()).intValue();
                            ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                            StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(iIntValue2, i, "DesktopTasksController: onDisplayDisconnect deskId=", ", disconnect=", ", destination=");
                            sbM.append(i2);
                            ProtoLog.v(shellProtoLogGroup, sbM.toString(), new Object[0]);
                            desksTransitionObserver.addPendingTransition(new DeskTransition.ChangeDeskDisplay(iBinder3, iIntValue2, i2));
                            ActivityManager.RunningTaskInfo runningTaskInfo4 = shellTaskOrganizer.getRunningTaskInfo(iIntValue2);
                            if (runningTaskInfo4 != null) {
                                windowContainerTransaction6.setReparentLeafTaskIfRelaunch(runningTaskInfo4.token, true);
                            }
                        }
                        desksTransitionObserver.addPendingTransition(new DeskTransition.RemoveDisplay(iBinder3, i));
                        DesktopStateImpl.Companion.getClass();
                        if (DesktopStateImpl.desktopExternalDisplayId == i) {
                            DesktopStateImpl.Companion.setDesktopExternalDisplayId(-1);
                        }
                        if (CoreRune.MW_SA_LOGGING) {
                            desktopTasksController2.desktopExitLogging(i);
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            DesktopUserRepositories$desktopRepoByUserId$1 desktopUserRepositories$desktopRepoByUserId$1 = this.userRepositories.desktopRepoByUserId;
            int size = desktopUserRepositories$desktopRepoByUserId$1.size();
            for (int i = 0; i < size; i++) {
                desktopUserRepositories$desktopRepoByUserId$1.keyAt(i);
                function13.mo781invoke((DesktopRepository) desktopUserRepositories$desktopRepoByUserId$1.valueAt(i));
            }
            return windowContainerTransaction5;
        }
        ActivityManager.RunningTaskInfo triggerTask3 = transitionRequestInfo.getTriggerTask();
        boolean z6 = this.recentsTransitionState >= 3;
        boolean z7 = z6 && triggerTask3 != null && triggerTask3.getWindowingMode() == 5 && TransitionUtil.isOpeningType(transitionRequestInfo.getType()) && this.taskRepository.isActiveTask(triggerTask3.taskId);
        if (triggerTask3 == null || (arrayList = triggerTask3.launchCookies) == null || arrayList.isEmpty()) {
            z = false;
        } else {
            int size2 = arrayList.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj = arrayList.get(i2);
                i2++;
                if (Intrinsics.areEqual((IBinder) obj, this.dragAndDropFullscreenCookie)) {
                    z = true;
                    break;
                }
            }
            z = false;
        }
        if ((transitionRequestInfo.getFlags() & 2097152) != 0) {
            z2 = z;
            z3 = true;
        } else {
            z2 = z;
            z3 = false;
        }
        if (z7) {
            z4 = true;
        } else {
            if (z6) {
                strM = "recents animation is running";
            } else if (z2) {
                this.dragAndDropFullscreenCookie = null;
            } else {
                if (!DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_WALLPAPER_ACTIVITY.isTrue() || !TransitionUtil.isClosingType(transitionRequestInfo.getType()) || transitionRequestInfo.getTriggerTask() == null) {
                    if (transitionRequestInfo.getType() != 1 && transitionRequestInfo.getType() != 3) {
                        strM = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(transitionRequestInfo.getType(), "transition type not handled (", ")");
                    } else if (triggerTask3 == null) {
                        strM = "triggerTask is null";
                    } else if (triggerTask3.getActivityType() != 1) {
                        strM = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(triggerTask3.getActivityType(), "activityType not handled (", ")");
                    } else if (triggerTask3.getWindowingMode() != 1 && !triggerTask3.isFreeform()) {
                        strM = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(triggerTask3.getWindowingMode(), "windowingMode not handled (", ")");
                    } else if (CoreRune.SYSFW_APP_SPEG && this.context.getPackageManager().isSpeg(triggerTask3.effectiveUid)) {
                        strM = "app launched by SPEG";
                    }
                }
                z4 = true;
            }
            z4 = false;
        }
        if (!z4) {
            logV$1("skipping handleRequest reason=%s", strM);
            if (transitionRequestInfo.getType() == 4 && (triggerTask = transitionRequestInfo.getTriggerTask()) != null && this.taskRepository.isActiveTask(triggerTask.taskId)) {
                logV$1(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(triggerTask.taskId, "handleMoveTaskToBackIfNeeded: #"), new Object[0]);
                minimizeTask(triggerTask, DesktopModeEventLogger.Companion.MinimizeReason.TASK_TO_BACK);
            }
            if (triggerTask3 != null) {
                DesktopStateImpl.Companion companion = DesktopStateImpl.Companion;
                int i3 = triggerTask3.displayId;
                companion.getClass();
                if (DesktopStateImpl.Companion.inDesktopWindowing(i3) && triggerTask3.getActivityType() == 2 && TransitionUtil.isOpeningType(transitionRequestInfo.getType())) {
                    minimizeAllTasks(triggerTask3.displayId);
                }
            }
            return null;
        }
        if (triggerTask3 != null) {
            if (!z7) {
                if (TransitionUtil.isClosingType(transitionRequestInfo.getType())) {
                    transitionRequestInfo.getType();
                    logV$1("handleTaskClosing", new Object[0]);
                    if (isDesktopModeShowing(triggerTask3.displayId) && ((deskIdForTask = this.taskRepository.getDeskIdForTask(triggerTask3.taskId)) != null || !DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue())) {
                        windowContainerTransaction2 = new WindowContainerTransaction();
                        DesktopTasksController$$ExternalSyntheticLambda3 desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default = !willExitDesktop(triggerTask3.taskId, false) ? null : performDesktopExitCleanUp$default(this, windowContainerTransaction2, deskIdForTask, triggerTask3.displayId, true, true, 32);
                        if (desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default != null) {
                            desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default.mo781invoke(iBinder);
                        }
                        if (!DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_BACK_NAVIGATION.isTrue()) {
                            this.taskRepository.addClosingTask(triggerTask3.displayId, deskIdForTask, triggerTask3.taskId);
                            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.snapEventHandler;
                            if (desktopModeWindowDecorViewModel == null) {
                                desktopModeWindowDecorViewModel = null;
                            }
                            desktopModeWindowDecorViewModel.removeTaskIfTiled(triggerTask3.displayId, triggerTask3.taskId);
                        }
                        DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 = this.taskbarDesktopTaskListener;
                        if (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 != null) {
                            desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1.onTaskbarCornerRoundingUpdate(doesAnyTaskRequireTaskbarRounding(triggerTask3.displayId, Integer.valueOf(triggerTask3.taskId)));
                        }
                        if (windowContainerTransaction2.isEmpty()) {
                            windowContainerTransaction2 = null;
                        }
                    }
                } else {
                    boolean zIsTopActivityExemptFromDesktopWindowing = this.desktopModeCompatPolicy.isTopActivityExemptFromDesktopWindowing(triggerTask3);
                    DesksOrganizer desksOrganizer = this.desksOrganizer;
                    ShellTaskOrganizer shellTaskOrganizer = this.shellTaskOrganizer;
                    if (zIsTopActivityExemptFromDesktopWindowing) {
                        logV$1("handleIncompatibleTaskLaunch", new Object[0]);
                        if (triggerTask3.getWindowingMode() == 1) {
                            if (((RootTaskDesksOrganizer) desksOrganizer).deskRootsByDeskId.contains(triggerTask3.parentTaskId) && this.taskRepository.getActiveDeskId(triggerTask3.displayId) == null) {
                                WindowContainerTransaction windowContainerTransaction6 = new WindowContainerTransaction();
                                DisplayAreaInfo displayAreaInfo = this.rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(triggerTask3.displayId);
                                displayAreaInfo.getClass();
                                windowContainerTransaction6.reparent(triggerTask3.token, displayAreaInfo.token, true);
                                windowContainerTransaction6.setWindowingMode(triggerTask3.token, 0);
                                shellTaskOrganizer.applyTransaction(windowContainerTransaction6);
                            } else if (isDesktopModeShowing(triggerTask3.displayId) || forceEnterDesktop(triggerTask3.displayId)) {
                                if (DesktopModeFlags.INCLUDE_TOP_TRANSPARENT_FULLSCREEN_TASK_IN_DESKTOP_HEURISTIC.isTrue()) {
                                    if (DesktopModeCompatPolicy.isTransparentTask(((TaskInfo) triggerTask3).numActivities, ((TaskInfo) triggerTask3).isActivityStackTransparent)) {
                                        DesktopRepository desktopRepository = this.taskRepository;
                                        int i4 = triggerTask3.displayId;
                                        int i5 = triggerTask3.taskId;
                                        desktopRepository.logD("Top transparent fullscreen task set for display: taskId=%d, displayId=%d", Integer.valueOf(i5), Integer.valueOf(i4));
                                        DesktopRepository.Desk activeDesk = desktopRepository.desktopData.getActiveDesk(i4);
                                        if (activeDesk != null) {
                                            activeDesk.topTransparentFullscreenTaskId = Integer.valueOf(i5);
                                        }
                                    }
                                }
                                if (triggerTask3.getWindowingMode() != 1) {
                                    windowContainerTransaction2 = new WindowContainerTransaction();
                                    ArrayList runningTasks = shellTaskOrganizer.getRunningTasks(triggerTask3.displayId);
                                    ArrayList arrayList2 = new ArrayList();
                                    int size3 = runningTasks.size();
                                    int i6 = 0;
                                    while (i6 < size3) {
                                        Object obj2 = runningTasks.get(i6);
                                        i6++;
                                        ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) obj2;
                                        if (runningTaskInfo3.getActivityType() == 3 && runningTaskInfo3.isVisible) {
                                            arrayList2.add(obj2);
                                        }
                                    }
                                    ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList2);
                                    if (runningTaskInfo4 != null) {
                                        windowContainerTransaction2.reorder(runningTaskInfo4.token, false);
                                    }
                                    DesktopTasksController$$ExternalSyntheticLambda3 desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges = addMoveToFullscreenChanges(windowContainerTransaction2, triggerTask3, willExitDesktop(triggerTask3.taskId, false));
                                    if (desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges != null) {
                                        desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges.mo781invoke(iBinder);
                                    }
                                }
                            }
                            windowContainerTransaction2 = null;
                        }
                    } else {
                        int windowingMode = triggerTask3.getWindowingMode();
                        DesktopImmersiveController desktopImmersiveController = this.desktopImmersiveController;
                        if (windowingMode == 1) {
                            logV$1("handleFullscreenTaskLaunch", new Object[0]);
                            if (!shouldFullscreenTaskLaunchSwitchToDesktop(triggerTask3)) {
                                DesktopStateImpl.Companion companion2 = DesktopStateImpl.Companion;
                                int i7 = triggerTask3.displayId;
                                companion2.getClass();
                                if (DesktopStateImpl.Companion.inDesktopWindowing(i7)) {
                                    logD$1("Switch fullscreen task to freeform on transition: taskId=%d, keepFull=%s", Integer.valueOf(triggerTask3.taskId), Boolean.valueOf(z3));
                                    windowContainerTransaction2 = new WindowContainerTransaction();
                                    Integer orCreateDefaultDeskId = getOrCreateDefaultDeskId(triggerTask3.displayId, false);
                                    if (orCreateDefaultDeskId != null) {
                                        int iIntValue = orCreateDefaultDeskId.intValue();
                                        addMoveToDeskTaskChanges(windowContainerTransaction2, triggerTask3, iIntValue, null, z3);
                                        if ((triggerTask3.baseIntent.getFlags() & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0 && isDesktopModeShowing(triggerTask3.displayId)) {
                                            function1AddDeskActivationChanges$default = new DesktopTasksController$$ExternalSyntheticLambda8(this, iIntValue, windowContainerTransaction2, triggerTask3);
                                            desktopTasksController = this;
                                            runningTaskInfo = triggerTask3;
                                        } else {
                                            function1AddDeskActivationChanges$default = addDeskActivationChanges$default(this, iIntValue, windowContainerTransaction2, triggerTask3, 0, 0, 48);
                                            desktopTasksController = this;
                                            windowContainerTransaction2 = windowContainerTransaction2;
                                            runningTaskInfo = triggerTask3;
                                            windowContainerTransaction2.reorder(runningTaskInfo.token, true);
                                        }
                                        function1AddDeskActivationChanges$default.mo781invoke(iBinder);
                                        int i8 = runningTaskInfo.displayId;
                                        DesktopImmersiveController.ExitReason exitReason = DesktopImmersiveController.ExitReason.TASK_LAUNCH;
                                        desktopImmersiveController.getClass();
                                        if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue() && (exitAsExit = desktopImmersiveController.exitImmersiveIfApplicable(windowContainerTransaction2, i8, null, exitReason).asExit()) != null && (function12 = exitAsExit.runOnTransitionStart) != null) {
                                            function12.mo781invoke(iBinder);
                                        }
                                    }
                                } else if (this.taskRepository.isActiveTask(triggerTask3.taskId)) {
                                    windowContainerTransaction2 = new WindowContainerTransaction();
                                    DesktopTasksController$$ExternalSyntheticLambda3 desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges2 = addMoveToFullscreenChanges(windowContainerTransaction2, triggerTask3, willExitDesktop(triggerTask3.taskId, true));
                                    if (desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges2 != null) {
                                        desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges2.mo781invoke(iBinder);
                                    }
                                } else {
                                    windowContainerTransaction2 = null;
                                }
                                runningTaskInfo = triggerTask3;
                                desktopTasksController = this;
                            }
                        } else {
                            runningTaskInfo = triggerTask3;
                            desktopTasksController = this;
                            if (runningTaskInfo.isFreeform()) {
                                logV$1("handleFreeformTaskLaunch", new Object[0]);
                                if (desktopTasksController.keyguardManager.isKeyguardLocked()) {
                                    logV$1("skip keyguard is locked", new Object[0]);
                                } else {
                                    int i9 = runningTaskInfo.displayId;
                                    if (i9 <= 0 || ((DesktopStateImpl) desktopTasksController.desktopState).isDesktopModeSupportedOnDisplay(i9)) {
                                        Integer orCreateDefaultDeskId2 = desktopTasksController.getOrCreateDefaultDeskId(runningTaskInfo.displayId, false);
                                        if (orCreateDefaultDeskId2 != null) {
                                            int iIntValue2 = orCreateDefaultDeskId2.intValue();
                                            WindowContainerTransaction windowContainerTransaction7 = new WindowContainerTransaction();
                                            if (desktopTasksController.isDesktopModeShowing(runningTaskInfo.displayId)) {
                                                ArrayList runningTasks2 = shellTaskOrganizer.getRunningTasks(runningTaskInfo.displayId);
                                                ArrayList arrayList3 = new ArrayList();
                                                int size4 = runningTasks2.size();
                                                int i10 = 0;
                                                while (i10 < size4) {
                                                    Object obj3 = runningTasks2.get(i10);
                                                    i10++;
                                                    ActivityManager.RunningTaskInfo runningTaskInfo5 = (ActivityManager.RunningTaskInfo) obj3;
                                                    ArrayList arrayList4 = runningTasks2;
                                                    if (runningTaskInfo5.getActivityType() == 3 && runningTaskInfo5.isVisible) {
                                                        arrayList3.add(obj3);
                                                    }
                                                    runningTasks2 = arrayList4;
                                                }
                                                ActivityManager.RunningTaskInfo runningTaskInfo6 = (ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList3);
                                                if (runningTaskInfo6 != null) {
                                                    windowContainerTransaction7.reorder(runningTaskInfo6.token, false);
                                                }
                                                Integer deskIdForTask2 = desktopTasksController.taskRepository.getDeskIdForTask(runningTaskInfo.taskId);
                                                if (deskIdForTask2 == null || iIntValue2 == deskIdForTask2.intValue() || desktopTasksController.taskRepository.desktopData.getDisplayForDesk(iIntValue2) != 0) {
                                                    if (runningTaskInfo.parentTaskId == -1 && deskIdForTask2 == null && (activeDeskId = desktopTasksController.taskRepository.getActiveDeskId(runningTaskInfo.displayId)) != null && activeDeskId.intValue() == iIntValue2 && (runningTaskInfo2 = shellTaskOrganizer.getRunningTaskInfo(iIntValue2)) != null) {
                                                        windowContainerTransaction7.reparent(runningTaskInfo.token, runningTaskInfo2.token, true);
                                                    }
                                                    inheritedExistingTaskBounds = DesktopModeUtils.getInheritedExistingTaskBounds(desktopTasksController.taskRepository, shellTaskOrganizer, runningTaskInfo, iIntValue2);
                                                    if (!desktopTasksController.taskRepository.isActiveTask(runningTaskInfo.taskId) && inheritedExistingTaskBounds != null) {
                                                        windowContainerTransaction7.setBounds(runningTaskInfo.token, inheritedExistingTaskBounds);
                                                    }
                                                    if (inheritedExistingTaskBounds == null && DesktopModeFlags.ENABLE_CASCADING_WINDOWS.isTrue() && !desktopTasksController.taskRepository.isVisibleTask(runningTaskInfo.taskId) && !desktopTasksController.taskRepository.isMinimizedTask(runningTaskInfo.taskId) && (displayLayout = desktopTasksController.displayController.getDisplayLayout(runningTaskInfo.displayId)) != null) {
                                                        Rect rect = new Rect(runningTaskInfo.configuration.windowConfiguration.getBounds());
                                                        desktopTasksController.cascadeWindow(rect, displayLayout, runningTaskInfo.displayId);
                                                        windowContainerTransaction7.setBounds(runningTaskInfo.token, rect);
                                                    }
                                                    desktopConfigImpl = (DesktopConfigImpl) desktopTasksController.desktopConfig;
                                                    if (desktopConfigImpl.useDesktopOverrideDensity) {
                                                        windowContainerTransaction7.setDensityDpi(runningTaskInfo.token, desktopConfigImpl.desktopDensityOverride);
                                                    }
                                                    if (desktopTasksController.taskRepository.isMinimizedTask(runningTaskInfo.taskId)) {
                                                        windowContainerTransaction4 = windowContainerTransaction7;
                                                        iBinder2 = iBinder;
                                                    } else {
                                                        windowContainerTransaction4 = windowContainerTransaction7;
                                                        iBinder2 = iBinder;
                                                        desktopTasksController.desktopTasksLimiter.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new DesktopTasksController$$ExternalSyntheticLambda7(iBinder2, runningTaskInfo.displayId, runningTaskInfo.taskId, DesktopModeEventLogger.Companion.UnminimizeReason.TASK_LAUNCH, 0)));
                                                    }
                                                    int i11 = runningTaskInfo.displayId;
                                                    DesktopImmersiveController.ExitReason exitReason2 = DesktopImmersiveController.ExitReason.TASK_LAUNCH;
                                                    desktopImmersiveController.getClass();
                                                    if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue()) {
                                                        windowContainerTransaction = null;
                                                    } else {
                                                        windowContainerTransaction = null;
                                                        DesktopImmersiveController.ExitResult.Exit exitAsExit2 = desktopImmersiveController.exitImmersiveIfApplicable(windowContainerTransaction4, i11, null, exitReason2).asExit();
                                                        if (exitAsExit2 != null && (function1 = exitAsExit2.runOnTransitionStart) != null) {
                                                            function1.mo781invoke(iBinder2);
                                                        }
                                                    }
                                                    numAddAndGetMinimizeChanges = desktopTasksController.addAndGetMinimizeChanges(iIntValue2, windowContainerTransaction4, Integer.valueOf(runningTaskInfo.taskId), false);
                                                    desktopTasksController.addPendingAppLaunchTransition(iBinder2, runningTaskInfo.taskId, numAddAndGetMinimizeChanges);
                                                    if (numAddAndGetMinimizeChanges == null) {
                                                        desktopTasksController.addPendingMinimizeTransition(iBinder2, numAddAndGetMinimizeChanges.intValue(), DesktopModeEventLogger.Companion.MinimizeReason.TASK_LIMIT);
                                                    } else if (!windowContainerTransaction4.isEmpty()) {
                                                        WindowContainerTransaction windowContainerTransaction8 = desktopTasksController.snapEventHandler;
                                                        if (windowContainerTransaction8 == null) {
                                                            windowContainerTransaction8 = windowContainerTransaction;
                                                        }
                                                        windowContainerTransaction8.removeTaskIfTiled(runningTaskInfo.displayId, runningTaskInfo.taskId);
                                                    }
                                                    windowContainerTransaction2 = windowContainerTransaction4;
                                                } else {
                                                    if (desktopTasksController.taskRepository.desktopData.getDisplayForDesk(deskIdForTask2.intValue()) == 0) {
                                                        ((RootTaskDesksOrganizer) desksOrganizer).moveTaskToDesk(windowContainerTransaction7, iIntValue2, runningTaskInfo);
                                                        ActivityManager.RunningTaskInfo runningTaskInfo7 = shellTaskOrganizer.getRunningTaskInfo(deskIdForTask2.intValue());
                                                        if (runningTaskInfo7 != null) {
                                                            windowContainerTransaction7.reorder(runningTaskInfo7.token, false);
                                                        }
                                                    }
                                                    inheritedExistingTaskBounds = DesktopModeUtils.getInheritedExistingTaskBounds(desktopTasksController.taskRepository, shellTaskOrganizer, runningTaskInfo, iIntValue2);
                                                    if (!desktopTasksController.taskRepository.isActiveTask(runningTaskInfo.taskId)) {
                                                        windowContainerTransaction7.setBounds(runningTaskInfo.token, inheritedExistingTaskBounds);
                                                    }
                                                    if (inheritedExistingTaskBounds == null) {
                                                        Rect rect2 = new Rect(runningTaskInfo.configuration.windowConfiguration.getBounds());
                                                        desktopTasksController.cascadeWindow(rect2, displayLayout, runningTaskInfo.displayId);
                                                        windowContainerTransaction7.setBounds(runningTaskInfo.token, rect2);
                                                    }
                                                    desktopConfigImpl = (DesktopConfigImpl) desktopTasksController.desktopConfig;
                                                    if (desktopConfigImpl.useDesktopOverrideDensity) {
                                                    }
                                                    if (desktopTasksController.taskRepository.isMinimizedTask(runningTaskInfo.taskId)) {
                                                    }
                                                    int i112 = runningTaskInfo.displayId;
                                                    DesktopImmersiveController.ExitReason exitReason22 = DesktopImmersiveController.ExitReason.TASK_LAUNCH;
                                                    desktopImmersiveController.getClass();
                                                    if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue()) {
                                                    }
                                                    numAddAndGetMinimizeChanges = desktopTasksController.addAndGetMinimizeChanges(iIntValue2, windowContainerTransaction4, Integer.valueOf(runningTaskInfo.taskId), false);
                                                    desktopTasksController.addPendingAppLaunchTransition(iBinder2, runningTaskInfo.taskId, numAddAndGetMinimizeChanges);
                                                    if (numAddAndGetMinimizeChanges == null) {
                                                    }
                                                    windowContainerTransaction2 = windowContainerTransaction4;
                                                }
                                            } else {
                                                logD$1("Bring desktop tasks to front on transition=taskId=%d", Integer.valueOf(runningTaskInfo.taskId));
                                                if (desktopTasksController.taskRepository.isActiveTask(runningTaskInfo.taskId) && !desktopTasksController.forceEnterDesktop(runningTaskInfo.displayId)) {
                                                    DesktopTasksController$$ExternalSyntheticLambda3 desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges3 = desktopTasksController.addMoveToFullscreenChanges(windowContainerTransaction7, runningTaskInfo, desktopTasksController.willExitDesktop(runningTaskInfo.taskId, true));
                                                    if (desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges3 != null) {
                                                        desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges3.mo781invoke(iBinder);
                                                    }
                                                    windowContainerTransaction2 = windowContainerTransaction7;
                                                }
                                            }
                                        } else {
                                            windowContainerTransaction = null;
                                        }
                                    }
                                }
                                windowContainerTransaction2 = null;
                                windowContainerTransaction = null;
                            }
                        }
                        windowContainerTransaction = null;
                    }
                }
                z5 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
                if (z5 && runningTaskInfo != null && !z7 && TransitionUtil.isOpeningType(transitionRequestInfo.getType()) && runningTaskInfo.displayId != 0) {
                    windowContainerTransaction3 = desktopTasksController.splitScreenController;
                    if (windowContainerTransaction3 == null) {
                        windowContainerTransaction3 = windowContainerTransaction;
                    }
                    if (windowContainerTransaction3.isTaskInSplitScreen$1(runningTaskInfo.taskId)) {
                        WindowContainerTransaction windowContainerTransaction9 = desktopTasksController.splitScreenController;
                        (windowContainerTransaction9 == null ? windowContainerTransaction : windowContainerTransaction9).dismissSplitTask(runningTaskInfo.token);
                    }
                }
                if (z5 && runningTaskInfo != null && windowContainerTransaction2 != null) {
                    desktopTasksController.exitSplitIfApplicable(windowContainerTransaction2, runningTaskInfo);
                }
                logV$1("handleRequest result=%s", windowContainerTransaction2);
                return windowContainerTransaction2;
            }
            logV$1("DesktopTasksController: handleMidRecentsFreeformTaskLaunch", new Object[0]);
            windowContainerTransaction2 = new WindowContainerTransaction();
            DesktopTasksController$$ExternalSyntheticLambda3 desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges4 = addMoveToFullscreenChanges(windowContainerTransaction2, triggerTask3, willExitDesktop(triggerTask3.taskId, true));
            if (desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges4 != null) {
                desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges4.mo781invoke(iBinder);
            }
            windowContainerTransaction2.reorder(triggerTask3.token, true);
            windowContainerTransaction = null;
            runningTaskInfo = triggerTask3;
            desktopTasksController = this;
            z5 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
            if (z5) {
                windowContainerTransaction3 = desktopTasksController.splitScreenController;
                if (windowContainerTransaction3 == null) {
                }
                if (windowContainerTransaction3.isTaskInSplitScreen$1(runningTaskInfo.taskId)) {
                }
            }
            if (z5) {
                desktopTasksController.exitSplitIfApplicable(windowContainerTransaction2, runningTaskInfo);
            }
            logV$1("handleRequest result=%s", windowContainerTransaction2);
            return windowContainerTransaction2;
        }
        windowContainerTransaction = null;
        runningTaskInfo = triggerTask3;
        desktopTasksController = this;
        windowContainerTransaction2 = windowContainerTransaction;
        z5 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
        if (z5) {
        }
        if (z5) {
        }
        logV$1("handleRequest result=%s", windowContainerTransaction2);
        return windowContainerTransaction2;
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
                    Context context2 = (Context) ref$ObjectRef.element;
                    if (context2 == null) {
                        context2 = this.context;
                    }
                    Toast.makeText(context2, R.string.multiwindow_desktop_mode_non_resizable_snap_text, 0).show();
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
        boolean zIsAnyDeskActive = this.taskRepository.isAnyDeskActive(i);
        boolean z = ((Integer) SequencesKt___SequencesKt.firstOrNull(SequencesKt___SequencesKt.mapNotNull(this.taskRepository.desktopData.desksSequence(i), new DesktopRepository$$ExternalSyntheticLambda0(4)))) != null;
        if (DesktopModeFlags.INCLUDE_TOP_TRANSPARENT_FULLSCREEN_TASK_IN_DESKTOP_HEURISTIC.isTrue() && DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_MODALS_POLICY.isTrue()) {
            logV$1("isDesktopModeShowing: hasVisibleTasks=%s hasTopTransparentFullscreenTask=%s", Boolean.valueOf(zIsAnyDeskActive), Boolean.valueOf(z));
            return zIsAnyDeskActive || z;
        }
        logV$1("isDesktopModeShowing: hasVisibleTasks=%s", Boolean.valueOf(zIsAnyDeskActive));
        return zIsAnyDeskActive;
    }

    public final void minimizeAllTasks(int i) throws Resources.NotFoundException {
        Set set;
        Integer activeDeskId = this.taskRepository.getActiveDeskId(i);
        Set activeTaskIdsInDesk = activeDeskId != null ? this.taskRepository.getActiveTaskIdsInDesk(activeDeskId.intValue()) : null;
        if ((activeDeskId != null && activeDeskId.intValue() == -1) || i == -1 || (set = activeTaskIdsInDesk) == null || set.isEmpty()) {
            return;
        }
        minimizeTasks(CollectionsKt___CollectionsKt.toList(activeTaskIdsInDesk), activeDeskId.intValue(), i);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0073  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void minimizeTask(ActivityManager.RunningTaskInfo runningTaskInfo, DesktopModeEventLogger.Companion.MinimizeReason minimizeReason) throws Resources.NotFoundException {
        boolean zIsOnlyVisibleNonClosingTask;
        boolean z;
        DesktopTasksController desktopTasksController;
        int i;
        int i2;
        Function1 function1;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        int i3 = runningTaskInfo.taskId;
        int i4 = runningTaskInfo.displayId;
        Integer deskIdForTask = this.taskRepository.getDeskIdForTask(i3);
        if (deskIdForTask == null) {
            if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                logW(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(runningTaskInfo.taskId, "minimizeTask: desk not found for task: "), new Object[0]);
                return;
            }
            deskIdForTask = getOrCreateDefaultDeskId(runningTaskInfo.displayId, false);
        }
        Integer num = deskIdForTask;
        DesktopExperienceFlags desktopExperienceFlags = DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND;
        if (desktopExperienceFlags.isTrue()) {
            DesktopRepository desktopRepository = this.taskRepository;
            if (num == null) {
                throw new IllegalStateException("Expected non-null deskId");
            }
            zIsOnlyVisibleNonClosingTask = desktopRepository.isOnlyVisibleNonClosingTaskInDesk(i3, num.intValue());
        } else {
            zIsOnlyVisibleNonClosingTask = this.taskRepository.isOnlyVisibleNonClosingTask(i3, i4);
        }
        boolean z2 = zIsOnlyVisibleNonClosingTask;
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PIP.isTrue()) {
            PictureInPictureParams pictureInPictureParams = runningTaskInfo.pictureInPictureParams;
            if ((pictureInPictureParams != null ? pictureInPictureParams.isAutoEnterEnabled() : false) && i4 == 0) {
                z = true;
            }
        } else {
            z = false;
        }
        if (z) {
            windowContainerTransaction.merge((WindowContainerTransaction) this.transitions.dispatchRequest(SYNTHETIC_TRANSITION, new TransitionRequestInfo(10, (ActivityManager.RunningTaskInfo) null, runningTaskInfo, (RemoteTransition) null, (TransitionRequestInfo.DisplayChange) null, 0), null).second, true);
            FreeformTaskTransitionStarter freeformTaskTransitionStarter = this.freeformTaskTransitionStarter;
            if (freeformTaskTransitionStarter == null) {
                freeformTaskTransitionStarter = null;
            }
            IBinder iBinderStartPipTransition = freeformTaskTransitionStarter.startPipTransition(windowContainerTransaction);
            if (!PipUtils.isPip2ExperimentEnabled()) {
                this.desktopTasksLimiter.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new DesktopTasksController$$ExternalSyntheticLambda8(iBinderStartPipTransition, i4, Collections.singletonList(Integer.valueOf(i3)), minimizeReason)));
            }
            desktopTasksController = this;
            i2 = i3;
            i = i4;
        } else {
            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.snapEventHandler;
            if (desktopModeWindowDecorViewModel == null) {
                desktopModeWindowDecorViewModel = null;
            }
            desktopModeWindowDecorViewModel.removeTaskIfTiled(i4, i3);
            DesktopTasksController$$ExternalSyntheticLambda3 desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default = performDesktopExitCleanUp$default(this, windowContainerTransaction, num, i4, willExitDesktop(i3, false), false, 48);
            desktopTasksController = this;
            i = i4;
            DesktopImmersiveController.ExitResult exitResultExitImmersiveIfApplicable = minimizeReason == DesktopModeEventLogger.Companion.MinimizeReason.MINIMIZE_BUTTON ? DesktopImmersiveController.ExitResult.NoExit.INSTANCE : desktopTasksController.desktopImmersiveController.exitImmersiveIfApplicable(windowContainerTransaction, runningTaskInfo, DesktopImmersiveController.ExitReason.MINIMIZED);
            if (!desktopExperienceFlags.isTrue()) {
                windowContainerTransaction.reorder(runningTaskInfo.token, false).getClass();
            } else {
                if (num == null) {
                    throw new IllegalStateException("Expected non-null deskId");
                }
                ((RootTaskDesksOrganizer) desktopTasksController.desksOrganizer).minimizeTask(windowContainerTransaction, num.intValue(), runningTaskInfo);
            }
            FreeformTaskTransitionStarter freeformTaskTransitionStarter2 = desktopTasksController.freeformTaskTransitionStarter;
            if (freeformTaskTransitionStarter2 == null) {
                freeformTaskTransitionStarter2 = null;
            }
            IBinder iBinderStartMinimizedModeTransition = freeformTaskTransitionStarter2.startMinimizedModeTransition(i3, windowContainerTransaction, z2);
            i2 = i3;
            desktopTasksController.desktopTasksLimiter.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new DesktopTasksController$$ExternalSyntheticLambda7(iBinderStartMinimizedModeTransition, i, i2, minimizeReason, 1)));
            DesktopImmersiveController.ExitResult.Exit exitAsExit = exitResultExitImmersiveIfApplicable.asExit();
            if (exitAsExit != null && (function1 = exitAsExit.runOnTransitionStart) != null) {
                iBinderStartMinimizedModeTransition.getClass();
                function1.mo781invoke(iBinderStartMinimizedModeTransition);
            }
            if (desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default != null) {
                iBinderStartMinimizedModeTransition.getClass();
                desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default.mo781invoke(iBinderStartMinimizedModeTransition);
            }
        }
        if (CoreRune.MW_SA_LOGGING) {
            CoreSaLogger.logForDexMW("3002", String.valueOf(desktopTasksController.taskRepository.getMinimizedTaskIdsInDesk(i).size()));
        }
        DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 = desktopTasksController.taskbarDesktopTaskListener;
        if (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 != null) {
            desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1.onTaskbarCornerRoundingUpdate(desktopTasksController.doesAnyTaskRequireTaskbarRounding(i, Integer.valueOf(i2)));
        }
    }

    public final void minimizeTasks(List list, int i, int i2) throws Resources.NotFoundException {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        Object next;
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PIP.isTrue() && !PipUtils.isPip2ExperimentEnabled() && i2 == 0) {
            DesktopRepository.Desk desk = this.taskRepository.desktopData.getDesk(i);
            if (desk == null) {
                throw new IllegalStateException(("Could not find desk for pip: " + i).toString());
            }
            FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(desk.freeformTasksInZOrder), new DesktopTasksController$$ExternalSyntheticLambda2(this, 1)).new AnonymousClass1();
            while (true) {
                if (!anonymousClass1.hasNext()) {
                    next = null;
                    break;
                }
                next = anonymousClass1.next();
                PictureInPictureParams pictureInPictureParams = ((ActivityManager.RunningTaskInfo) next).pictureInPictureParams;
                if (pictureInPictureParams != null && pictureInPictureParams.isAutoEnterEnabled()) {
                    break;
                }
            }
            runningTaskInfo = (ActivityManager.RunningTaskInfo) next;
            if (runningTaskInfo != null) {
                logV$1("findPipCandidate taskId=%d", Integer.valueOf(runningTaskInfo.taskId));
            }
        } else {
            runningTaskInfo = null;
        }
        int i3 = runningTaskInfo != null ? runningTaskInfo.taskId : -1;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            if (iIntValue != i3 && (runningTaskInfo2 = this.shellTaskOrganizer.getRunningTaskInfo(iIntValue)) != null) {
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.snapEventHandler;
                if (desktopModeWindowDecorViewModel == null) {
                    desktopModeWindowDecorViewModel = null;
                }
                desktopModeWindowDecorViewModel.removeTaskIfTiled(i2, iIntValue);
                ((RootTaskDesksOrganizer) this.desksOrganizer).minimizeTask(windowContainerTransaction, i, runningTaskInfo2);
            }
        }
        ArrayList arrayList = new ArrayList(list);
        if (i3 != -1) {
            arrayList.remove(Integer.valueOf(i3));
        }
        if (!arrayList.isEmpty()) {
            FreeformTaskTransitionStarter freeformTaskTransitionStarter = this.freeformTaskTransitionStarter;
            if (freeformTaskTransitionStarter == null) {
                freeformTaskTransitionStarter = null;
            }
            this.desktopTasksLimiter.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new DesktopTasksController$$ExternalSyntheticLambda3(freeformTaskTransitionStarter.startMinimizeAllTransition(windowContainerTransaction, i2), i2, arrayList)));
        }
        if (runningTaskInfo != null) {
            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = this.snapEventHandler;
            (desktopModeWindowDecorViewModel2 != null ? desktopModeWindowDecorViewModel2 : null).removeTaskIfTiled(i2, runningTaskInfo.taskId);
            minimizeTask(runningTaskInfo, DesktopModeEventLogger.Companion.MinimizeReason.TASK_LIMIT);
        }
    }

    public final void moveHomeTaskToTop(WindowContainerTransaction windowContainerTransaction, int i) throws Resources.NotFoundException {
        logV$1("moveHomeTaskToTop in displayId=%d", Integer.valueOf(i));
        ActivityManager.RunningTaskInfo homeTask = getHomeTask(i);
        if (homeTask == null || windowContainerTransaction.reorder(homeTask.getToken(), true) == null) {
            this.homeIntentProvider.addLaunchHomePendingIntent(windowContainerTransaction, i, Integer.valueOf(this.userId));
            Unit unit = Unit.INSTANCE;
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
            BuildersKt.launch$default(this.mainScope, null, null, new C12021(i, i2, windowContainerTransaction, desktopModeTransitionSource, remoteTransition, null), 3);
            return true;
        }
        Integer orCreateDefaultDeskId = getOrCreateDefaultDeskId(i2, false);
        if (orCreateDefaultDeskId != null) {
            return moveTaskToDesk$default(this, i, orCreateDefaultDeskId.intValue(), windowContainerTransaction, desktopModeTransitionSource, remoteTransition, 32);
        }
        return false;
    }

    public final void moveTaskToFront(int i, RemoteTransition remoteTransition, DesktopModeEventLogger.Companion.UnminimizeReason unminimizeReason) throws Resources.NotFoundException {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(i);
        if (runningTaskInfo != null) {
            moveTaskToFront(runningTaskInfo, remoteTransition, unminimizeReason);
            return;
        }
        logV$1("moveBackgroundTaskToFront taskId=%s", Integer.valueOf(i));
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchWindowingMode(5);
        Unit unit = Unit.INSTANCE;
        windowContainerTransaction.startTask(i, activityOptionsMakeBasic.toBundle());
        Integer deskIdForTask = this.taskRepository.getDeskIdForTask(i);
        if (deskIdForTask == null && (deskIdForTask = getOrCreateDefaultDeskId(0, false)) == null) {
            return;
        }
        startLaunchTransition(1, windowContainerTransaction, Integer.valueOf(i), remoteTransition, deskIdForTask.intValue(), 0, unminimizeReason);
    }

    public final void moveToDisplay(ActivityManager.RunningTaskInfo runningTaskInfo, int i, Rect rect, Transitions.TransitionHandler transitionHandler) throws Resources.NotFoundException {
        DisplayLayout displayLayout;
        Rect rectCalculateMaximizeBounds;
        boolean z;
        DesktopTasksController$$ExternalSyntheticLambda3 desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default;
        logV$1("moveToDisplay: taskId=%d displayId=%d", Integer.valueOf(runningTaskInfo.taskId), Integer.valueOf(i));
        if (runningTaskInfo.displayId == i) {
            logD$1("moveToDisplay: task already on display %d", Integer.valueOf(i));
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
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
            windowContainerTransaction.reparent(runningTaskInfo.token, displayAreaInfo.token, true);
            windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 0);
            windowContainerTransaction.setBounds(runningTaskInfo.token, new Rect());
            transitions.startTransition(1, windowContainerTransaction, null);
            return;
        }
        if (runningTaskInfo.isFreeform()) {
            if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                ((RootTaskDesksOrganizer) this.desksOrganizer).moveTaskToDesk(windowContainerTransaction, defaultDeskId.intValue(), runningTaskInfo);
            }
            if (runningTaskInfo.getDisplayId() == 0) {
                logD$1("moveToDisplay: when it go to external display, unset alwaysOnTop", new Object[0]);
                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                windowContainerTransaction2.setAlwaysOnTop(runningTaskInfo.token, false);
                this.shellTaskOrganizer.applyTransaction(windowContainerTransaction2);
            }
            if (rect != null) {
                windowContainerTransaction.setBounds(runningTaskInfo.token, rect);
            } else {
                int i2 = runningTaskInfo.displayId;
                DisplayController displayController = this.displayController;
                DisplayLayout displayLayout2 = displayController.getDisplayLayout(i2);
                if (displayLayout2 != null && (displayLayout = displayController.getDisplayLayout(i)) != null) {
                    Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
                    int iWidth = (bounds.width() * displayLayout.mDensityDpi) / displayLayout2.mDensityDpi;
                    int iHeight = (bounds.height() * displayLayout.mDensityDpi) / displayLayout2.mDensityDpi;
                    int iWidth2 = displayLayout2.mWidth - bounds.width();
                    int iHeight2 = displayLayout2.mHeight - bounds.height();
                    int i3 = displayLayout.mWidth - iWidth;
                    int i4 = displayLayout.mHeight - iHeight;
                    int i5 = iWidth2 != 0 ? (bounds.left * i3) / iWidth2 : i3 / 2;
                    int i6 = iHeight2 != 0 ? (bounds.top * i4) / iHeight2 : i4 / 2;
                    if (i3 < 0 || i4 < 0) {
                        DesktopStateImpl.Companion.getClass();
                        rectCalculateMaximizeBounds = DesktopStateImpl.Companion.inNonResizableDesktopWindowing(runningTaskInfo) ? DesktopModeUtils.calculateMaximizeBounds(displayLayout, runningTaskInfo) : getInitialBounds(displayLayout, runningTaskInfo, i);
                    } else {
                        rectCalculateMaximizeBounds = new Rect(0, 0, iWidth, iHeight);
                        rectCalculateMaximizeBounds.offsetTo(RangesKt___RangesKt.coerceIn(i5, 0, i3), RangesKt___RangesKt.coerceIn(i6, 0, i4));
                    }
                    windowContainerTransaction.setBounds(runningTaskInfo.token, rectCalculateMaximizeBounds);
                }
            }
        } else {
            addMoveToDeskTaskChanges(windowContainerTransaction, runningTaskInfo, defaultDeskId.intValue(), null, false);
            windowContainerTransaction = windowContainerTransaction;
        }
        DesktopExperienceFlags desktopExperienceFlags = DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND;
        if (desktopExperienceFlags.isTrue()) {
            z = true;
        } else {
            z = true;
            windowContainerTransaction.reparent(runningTaskInfo.token, displayAreaInfo.token, true);
        }
        WindowContainerTransaction windowContainerTransaction3 = windowContainerTransaction;
        Function1 function1AddDeskActivationChanges$default = addDeskActivationChanges$default(this, defaultDeskId.intValue(), windowContainerTransaction3, runningTaskInfo, 0, 0, 56);
        windowContainerTransaction3.reorder(runningTaskInfo.token, z, z);
        int i7 = runningTaskInfo.displayId;
        Integer deskIdForTask = this.taskRepository.getDeskIdForTask(runningTaskInfo.taskId);
        if (desktopExperienceFlags.isTrue()) {
            desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default = !willExitDesktop(runningTaskInfo.taskId, false) ? null : performDesktopExitCleanUp$default(this, windowContainerTransaction3, deskIdForTask, i7, true, false, 32);
            if (desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default == null) {
                Integer deskIdForTask2 = this.taskRepository.getDeskIdForTask(runningTaskInfo.taskId);
                int iIntValue = deskIdForTask2 != null ? deskIdForTask2.intValue() : -1;
                Integer displayIdForTask = this.taskRepository.getDisplayIdForTask(runningTaskInfo.taskId);
                int iIntValue2 = displayIdForTask != null ? displayIdForTask.intValue() : -1;
                int i8 = runningTaskInfo.taskId;
                if (iIntValue != -1 && iIntValue2 != -1) {
                    this.taskRepository.setTaskInFullImmersiveStateInDesk(iIntValue, i8, false);
                }
                desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default = null;
            }
        } else {
            desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default = null;
        }
        IBinder iBinderStartTransition = transitions.startTransition(6, windowContainerTransaction3, transitionHandler == null ? this.moveToDisplayTransitionHandler : transitionHandler);
        if (desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default != null) {
            iBinderStartTransition.getClass();
            desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default.mo781invoke(iBinderStartTransition);
        }
        iBinderStartTransition.getClass();
        function1AddDeskActivationChanges$default.mo781invoke(iBinderStartTransition);
        SplitScreenController splitScreenController = this.splitScreenController;
        if (splitScreenController == null) {
            splitScreenController = null;
        }
        if (splitScreenController.isTaskInSplitScreen$1(runningTaskInfo.taskId)) {
            SplitScreenController splitScreenController2 = this.splitScreenController;
            (splitScreenController2 == null ? null : splitScreenController2).dismissSplitTask(runningTaskInfo.token);
        }
    }

    public final void moveToFullscreen(int i, DesktopModeTransitionSource desktopModeTransitionSource) throws Resources.NotFoundException {
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

    public final void moveToFullscreenWithAnimation(ActivityManager.RunningTaskInfo runningTaskInfo, Point point, DesktopModeTransitionSource desktopModeTransitionSource, Rect rect) throws Resources.NotFoundException {
        DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1;
        DesktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 = this.exitDesktopModeListener;
        if (desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 != null) {
            desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1.onExitDesktopModeStarted();
        }
        logV$1("moveToFullscreenWithAnimation taskId=%d", Integer.valueOf(runningTaskInfo.taskId));
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        DesktopTasksController$$ExternalSyntheticLambda3 desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges = addMoveToFullscreenChanges(windowContainerTransaction, runningTaskInfo, willExitDesktop(runningTaskInfo.taskId, true));
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
        IBinder iBinderStartTransition = exitDesktopTaskTransitionHandler.mTransitions.startTransition(i2 != 1 ? i2 != 3 ? i2 != 4 ? VolteConstants.ErrorCode.CALL_REJECT_REASON_USR_BUSY_CS_CALL : VolteConstants.ErrorCode.CALL_STATUS_CONF_ADD_USER_TO_SESSION_FAILURE : VolteConstants.ErrorCode.CALL_END_CALL_NW_HANDOVER : VolteConstants.ErrorCode.CALL_STATUS_CONF_REMOVE_USER_FROM_SESSION_FAILURE, windowContainerTransaction, exitDesktopTaskTransitionHandler);
        ((ArrayList) exitDesktopTaskTransitionHandler.mPendingTransitionTokens).add(iBinderStartTransition);
        if (desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges != null) {
            iBinderStartTransition.getClass();
            desktopTasksController$$ExternalSyntheticLambda3AddMoveToFullscreenChanges.mo781invoke(iBinderStartTransition);
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
    public final void moveToNextDisplay(int i) throws Resources.NotFoundException {
        Object next;
        Integer next2;
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
        List listAsList = Arrays.asList(typedArray);
        Iterator it = listAsList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (((Number) next).intValue() > runningTaskInfo.displayId) {
                    break;
                }
            }
        }
        Integer num = (Integer) next;
        if (num == null) {
            Iterator it2 = listAsList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    next2 = 0;
                    break;
                } else {
                    next2 = it2.next();
                    if (((Number) next2).intValue() < runningTaskInfo.displayId) {
                        break;
                    }
                }
            }
            num = next2;
        }
        if (num == null) {
            logW("moveToNextDisplay: next display not found", new Object[0]);
        } else {
            moveToDisplay(runningTaskInfo, num.intValue(), null, null);
        }
    }

    @Override // com.android.wm.shell.sysui.UserChangeListener
    public final void onBeforeUserSwitching(int i) {
        DesktopTasksController desktopTasksController;
        logV$1("onBeforeUserSwitching newUserId=%d", Integer.valueOf(i));
        Integer activeDeskId = this.taskRepository.getActiveDeskId(0);
        if (activeDeskId != null) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            desktopTasksController = this;
            prepareDeskDeactivationIfNeeded$default(desktopTasksController, windowContainerTransaction, activeDeskId, 0, 0, 28);
            desktopTasksController.shellTaskOrganizer.applyTransaction(windowContainerTransaction);
            desktopTasksController.taskRepository.setDeskInactive(activeDeskId.intValue());
            DesktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 = desktopTasksController.exitDesktopModeListener;
            if (desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 != null) {
                desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1.onExitDesktopModeStarted();
            }
        } else {
            desktopTasksController = this;
        }
        Display display = desktopTasksController.displayController.mDisplayManager.getDisplay(DesktopStateImpl.desktopExternalDisplayId);
        if (display == null || display.getType() != 2) {
            return;
        }
        desktopTasksController.displayManager.setEnableConnectedDisplay(display.getDisplayId(), false);
        desktopTasksController.disableConnectDisplayIdBeforeUserSwitch = display.getDisplayId();
    }

    public final void onDefaultDisplayDesktopModeChanged(final boolean z) {
        Iterator it = ((ArrayList) this.defaultDisplayDesktopModeChangeListeners).iterator();
        int i = 0;
        while (it.hasNext()) {
            final DefaultDisplayDesktopModeChangeListener defaultDisplayDesktopModeChangeListener = (DefaultDisplayDesktopModeChangeListener) it.next();
            ((Executor) ((ArrayList) this.defaultDisplayDesktopModeChangeListenerExecutors).get(i)).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController.onDefaultDisplayDesktopModeChanged.1
                @Override // java.lang.Runnable
                public final void run() {
                    defaultDisplayDesktopModeChangeListener.onDefaultDisplayDesktopModeChanged(z);
                }
            });
            i++;
        }
        if (z) {
            return;
        }
        this.taskIdsOfTabletMode.clear();
    }

    @Override // com.android.wm.shell.draganddrop.DragAndDropController.DragAndDropListener
    public final boolean onUnhandledDrag(PendingIntent pendingIntent, int i, DragEvent dragEvent, GlobalDragListener.AnonymousClass1 anonymousClass1) throws Resources.NotFoundException {
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
                DesktopModeVisualIndicator.IndicatorType indicatorTypeUpdateVisualIndicator = updateVisualIndicator(focusedFreeformTask, dragEvent.getDragSurface(), dragEvent.getX(), dragEvent.getY(), DesktopModeVisualIndicator.DragStartState.DRAGGED_INTENT, false, true, true);
                releaseVisualIndicator();
                int[] iArr = WhenMappings.$EnumSwitchMapping$1;
                int i3 = iArr[indicatorTypeUpdateVisualIndicator.ordinal()];
                if (i3 == 1) {
                    i2 = 1;
                } else {
                    if (i3 != 2 && i3 != 3 && i3 != 7 && i3 != 8) {
                        throw new IllegalStateException(("Invalid indicator type: " + indicatorTypeUpdateVisualIndicator).toString());
                    }
                    i2 = 5;
                }
                DisplayLayout displayLayout = this.displayController.getDisplayLayout(0);
                if (displayLayout != null) {
                    Rect rect = new Rect();
                    int i4 = iArr[indicatorTypeUpdateVisualIndicator.ordinal()];
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
                    ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                    activityOptionsMakeBasic.setLaunchWindowingMode(i2);
                    activityOptionsMakeBasic.setLaunchBounds(rect);
                    activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(3);
                    activityOptionsMakeBasic.setPendingIntentLaunchFlags(402653184);
                    activityOptionsMakeBasic.setSplashScreenStyle(1);
                    if (i2 == 1) {
                        Binder binder = new Binder();
                        this.dragAndDropFullscreenCookie = binder;
                        activityOptionsMakeBasic.setLaunchCookie(binder);
                    }
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    windowContainerTransaction.sendPendingIntent(pendingIntent, (Intent) null, activityOptionsMakeBasic.toBundle());
                    if (i2 != 5) {
                        this.transitions.startTransition(1, windowContainerTransaction, null);
                    } else if (DesktopModeFlags.ENABLE_DESKTOP_TAB_TEARING_MINIMIZE_ANIMATION_BUGFIX.isTrue()) {
                        Integer orCreateDefaultDeskId = getOrCreateDefaultDeskId(0, false);
                        if (orCreateDefaultDeskId != null) {
                            startLaunchTransition$default(this, windowContainerTransaction, orCreateDefaultDeskId.intValue(), 0);
                        }
                    } else {
                        DesktopModeDragAndDropTransitionHandler desktopModeDragAndDropTransitionHandler = this.desktopModeDragAndDropTransitionHandler;
                        IBinder iBinderStartTransition = desktopModeDragAndDropTransitionHandler.transitions.startTransition(1, windowContainerTransaction, desktopModeDragAndDropTransitionHandler);
                        List list = desktopModeDragAndDropTransitionHandler.pendingTransitionTokens;
                        iBinderStartTransition.getClass();
                        ((ArrayList) list).add(iBinderStartTransition);
                    }
                    anonymousClass1.accept(Boolean.TRUE);
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
        final int i2 = this.disableConnectDisplayIdBeforeUserSwitch;
        if (i2 != -1) {
            ((HandlerExecutor) this.mainExecutor).executeDelayed(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController.onUserChanged.1
                @Override // java.lang.Runnable
                public final void run() {
                    DesktopTasksController.this.displayManager.setEnableConnectedDisplay(i2, true);
                }
            }, 7000L);
            this.disableConnectDisplayIdBeforeUserSwitch = -1;
        }
    }

    public final DesktopTasksController$$ExternalSyntheticLambda3 performDesktopExitCleanUp(WindowContainerTransaction windowContainerTransaction, Integer num, int i, boolean z, boolean z2, boolean z3) throws Resources.NotFoundException {
        if (!z) {
            return null;
        }
        DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 = this.desktopModeEnterExitTransitionListener;
        if (desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 != null) {
            desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1.onExitDesktopModeTransitionStarted();
        }
        if (!z3 || !DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            boolean zIsTrue = DesktopModeFlags.ENABLE_DESKTOP_WALLPAPER_ACTIVITY_FOR_SYSTEM_USER.isTrue();
            DesktopWallpaperActivityTokenProvider desktopWallpaperActivityTokenProvider = this.desktopWallpaperActivityTokenProvider;
            if (zIsTrue) {
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
        return prepareDeskDeactivationIfNeeded$default(this, windowContainerTransaction, num, 0, 0, 28);
    }

    public final void prepareForDeskActivation(WindowContainerTransaction windowContainerTransaction, int i) throws Resources.NotFoundException {
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
            boolean zIsTrue = DesktopModeFlags.ENABLE_DESKTOP_WALLPAPER_ACTIVITY_FOR_SYSTEM_USER.isTrue();
            DesktopWallpaperActivityTokenProvider desktopWallpaperActivityTokenProvider = this.desktopWallpaperActivityTokenProvider;
            if (zIsTrue) {
                WindowContainerToken token = desktopWallpaperActivityTokenProvider.getToken(i);
                if (token != null) {
                    windowContainerTransaction.reorder(token, true);
                    return;
                }
                Intent intent = new Intent(this.context, (Class<?>) DesktopWallpaperActivity.class);
                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                activityOptionsMakeBasic.setLaunchWindowingMode(1);
                activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(3);
                windowContainerTransaction.sendPendingIntent(PendingIntent.getActivity(this.context, 0, intent, 67108864), intent, activityOptionsMakeBasic.toBundle());
                return;
            }
            UserHandle userHandleOf = UserHandle.of(this.userId);
            Context contextCreateContextAsUser = this.context.createContextAsUser(userHandleOf, 0);
            Intent intent2 = new Intent(contextCreateContextAsUser, (Class<?>) DesktopWallpaperActivity.class);
            desktopWallpaperActivityTokenProvider.getToken(i);
            intent2.putExtra("android.intent.extra.user_handle", this.userId);
            ActivityOptions activityOptionsMakeBasic2 = ActivityOptions.makeBasic();
            activityOptionsMakeBasic2.setLaunchWindowingMode(1);
            activityOptionsMakeBasic2.setPendingIntentBackgroundActivityStartMode(3);
            windowContainerTransaction.sendPendingIntent(PendingIntent.getActivityAsUser(contextCreateContextAsUser, 0, intent2, 67108864, null, userHandleOf), intent2, activityOptionsMakeBasic2.toBundle());
        }
    }

    public final void releaseVisualIndicator() {
        DesktopModeVisualIndicator desktopModeVisualIndicator = this.visualIndicator;
        if (desktopModeVisualIndicator != null) {
            logV$1("releaseVisualIndicator: " + desktopModeVisualIndicator + ", c=" + Debug.getCallers(3), new Object[0]);
        }
        DesktopModeVisualIndicator desktopModeVisualIndicator2 = this.visualIndicator;
        if (desktopModeVisualIndicator2 != null) {
            desktopModeVisualIndicator2.mVisualIndicatorViewContainer.releaseVisualIndicator();
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
        if (((RootTaskDesksOrganizer) this.desksOrganizer).deskRootsByDeskId.contains(i)) {
            Set activeTaskIdsInDesk = this.taskRepository.getActiveTaskIdsInDesk(i);
            if (this.recentTasksController != null) {
                ActivityTaskManager.getService().removeAllVisibleRecentTasksExt(true, CollectionsKt___CollectionsKt.toIntArray(activeTaskIdsInDesk));
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void removeAllVisibleRecentTasks() {
        List<ActivityManager.RecentTaskInfo> recentTasks;
        RecentTasksController recentTasksController = this.recentTasksController;
        if (recentTasksController != null) {
            try {
                recentTasks = recentTasksController.mActivityTaskManager.getRecentTasks(Integer.MAX_VALUE, 3, ActivityManager.getCurrentUser());
            } catch (BadParcelableException unused) {
                recentTasks = Collections.EMPTY_LIST;
            }
            if (recentTasks == null) {
                recentTasks = EmptyList.INSTANCE;
            }
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (ActivityManager.RecentTaskInfo recentTaskInfo : recentTasks) {
            if (!this.taskRepository.isActiveTask(recentTaskInfo.taskId)) {
                linkedHashSet.add(Integer.valueOf(recentTaskInfo.taskId));
            }
        }
        if (recentTasksController != null) {
            ActivityTaskManager.getService().removeAllVisibleRecentTasksExt(true, CollectionsKt___CollectionsKt.toIntArray(linkedHashSet));
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
                int iIntValue = ((Number) it.next()).intValue();
                ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(iIntValue);
                if (runningTaskInfo != null) {
                    windowContainerTransaction.removeTask(runningTaskInfo.token);
                } else {
                    RecentTasksController recentTasksController = this.recentTasksController;
                    if (recentTasksController != null) {
                        recentTasksController.mActivityTaskManager.removeTask(iIntValue);
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
                    SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "RootTaskDesksOrganizer", objArr2);
                    ProtoLog.w(shellProtoLogGroup, "%s: removeDesk attempted to remove non-existent desk=%d", spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
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
                IBinder iBinderStartTransition = this.transitions.startTransition(2, windowContainerTransaction, null);
                if (desktopExperienceFlags.isTrue()) {
                    iBinderStartTransition.getClass();
                    i3 = i;
                    i4 = i2;
                    this.desksTransitionObserver.addPendingTransition(new DeskTransition.RemoveDesk(iBinderStartTransition, i3, i4, activeTaskIdsInDesk, this.onDeskRemovedListener));
                } else {
                    i3 = i;
                    i4 = i2;
                }
                if (i3 != 0 || (activeDeskId = this.taskRepository.getActiveDeskId(i3)) == null) {
                    return;
                }
                if ((activeDeskId.intValue() == i4 ? activeDeskId : null) != null) {
                    DesktopStateImpl.Companion.getClass();
                    if (DesktopStateImpl.Companion.inDesktopWindowing(0)) {
                        DesktopStateImpl.Companion.setInDesktopWindowing(false);
                        onDefaultDisplayDesktopModeChanged(false);
                        if (CoreRune.MW_SA_LOGGING && !DesktopStateImpl.inDesktopWindowing && DesktopStateImpl.desktopExternalDisplayId == -1) {
                            desktopExitLogging(0);
                        }
                    }
                }
            }
        }
    }

    public final void removeDesktopDisabledFlagsOnDefaultDisplay(int i) {
        DesktopModeWindowDecorViewModel.AnonymousClass1 anonymousClass1;
        DesktopTasksController$IDesktopModeImpl$deskChangeListener$1 desktopTasksController$IDesktopModeImpl$deskChangeListener$1;
        if ((this.desktopDisabledFlagsOnDefaultDisplay & i) == 0) {
            return;
        }
        boolean zCanCreateDesks$default = canCreateDesks$default(this);
        int i2 = (~i) & this.desktopDisabledFlagsOnDefaultDisplay;
        this.desktopDisabledFlagsOnDefaultDisplay = i2;
        DesktopTasksController$IDesktopModeImpl$desktopDisabledFlagsListener$1 desktopTasksController$IDesktopModeImpl$desktopDisabledFlagsListener$1 = this.desktopDisabledFlagsListener;
        if (desktopTasksController$IDesktopModeImpl$desktopDisabledFlagsListener$1 != null) {
            desktopTasksController$IDesktopModeImpl$desktopDisabledFlagsListener$1.onDesktopDisabledFlagsChangedOnDefaultDisplay(i2);
        }
        boolean zCanCreateDesks$default2 = canCreateDesks$default(this);
        if (zCanCreateDesks$default != zCanCreateDesks$default2 && (desktopTasksController$IDesktopModeImpl$deskChangeListener$1 = this.deskChangeListener) != null) {
            desktopTasksController$IDesktopModeImpl$deskChangeListener$1.onCanCreateDesksChanged(zCanCreateDesks$default2);
        }
        if (!CoreRune.MW_CAPTION_DESKTOP_DISABLED || (anonymousClass1 = this.decorViewModelDesktopDisabledChangeListener) == null) {
            return;
        }
        anonymousClass1.onDesktopDisabledFlagsChangedOnDefaultDisplay(this.desktopDisabledFlagsOnDefaultDisplay);
    }

    public final void requestFloat(ActivityManager.RunningTaskInfo runningTaskInfo, Boolean bool) {
        DragToDesktopTransitionHandler dragToDesktopTransitionHandler = this.dragToDesktopTransitionHandler;
        boolean inProgress$1 = dragToDesktopTransitionHandler.getInProgress$1();
        if (TaskInfoKt.isFullscreen(runningTaskInfo) || runningTaskInfo.isFreeform() || inProgress$1 || TaskInfoKt.isMultiWindow(runningTaskInfo)) {
            if (!inProgress$1) {
                this.bubbleController.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new DesktopTasksController$$ExternalSyntheticLambda12(1)));
            } else {
                releaseVisualIndicator();
                dragToDesktopTransitionHandler.cancelDragToDesktopTransition(Intrinsics.areEqual(bool, Boolean.TRUE) ? DragToDesktopTransitionHandler.CancelState.CANCEL_BUBBLE_LEFT : DragToDesktopTransitionHandler.CancelState.CANCEL_BUBBLE_RIGHT);
            }
        }
    }

    public final void requestSplit(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) throws Resources.NotFoundException {
        DesktopTasksController desktopTasksController;
        DesktopTasksController$$ExternalSyntheticLambda3 desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default;
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
                desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default = performDesktopExitCleanUp$default(desktopTasksController, windowContainerTransaction, deskIdForTask, i2, true, false, 32);
            } else {
                desktopTasksController = this;
                desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default = null;
            }
            SplitScreenController splitScreenController = desktopTasksController.splitScreenController;
            IBinder iBinderRequestEnterSplitSelect = (splitScreenController != null ? splitScreenController : null).requestEnterSplitSelect(!z ? 1 : 0, runningTaskInfo, runningTaskInfo.configuration.windowConfiguration.getBounds(), windowContainerTransaction);
            if (iBinderRequestEnterSplitSelect == null || desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default == null) {
                return;
            }
            desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default.mo781invoke(iBinderRequestEnterSplitSelect);
        }
    }

    public final void setVisualIndicator(DesktopModeVisualIndicator desktopModeVisualIndicator) {
        if (this.visualIndicator == null) {
            this.visualIndicator = desktopModeVisualIndicator;
            logV$1("setVisualIndicator: " + desktopModeVisualIndicator + ", c=" + Debug.getCallers(3), new Object[0]);
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

    public final void showDesktopDisabledToast(Integer num) {
        if (num == null) {
            num = (this.desktopDisabledFlagsOnDefaultDisplay & 2) != 0 ? Integer.valueOf(R.string.dw_desktop_disabled_while_smart_view_is_in_use) : null;
        }
        if (num != null) {
            Toast.makeText(this.context, num.intValue(), 0).show();
        }
    }

    public final void snapToHalfScreen(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Rect rect, SnapPosition snapPosition, DesktopModeEventLogger.Companion.ResizeTrigger resizeTrigger, DesktopModeEventLogger.Companion.InputMethod inputMethod) {
        DesktopTilingWindowDecoration desktopTilingWindowDecoration;
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        int i;
        DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1;
        ActivityManager.RunningTaskInfo runningTaskInfo3;
        Integer numValueOf = Integer.valueOf(rect.width());
        Integer numValueOf2 = Integer.valueOf(rect.height());
        DesktopModeEventLogger.Companion companion = DesktopModeEventLogger.Companion;
        this.desktopModeEventLogger.logTaskResizingStarted(resizeTrigger, inputMethod, runningTaskInfo, numValueOf, numValueOf2, this.displayController, null);
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
        boolean zEquals = snapBounds2.equals(runningTaskInfo.configuration.windowConfiguration.getBounds());
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
        if (!zEquals) {
            WindowContainerTransaction bounds2 = new WindowContainerTransaction().setBounds(runningTaskInfo.token, snapBounds2);
            if (CoreRune.DW_SHELL_CHANGE_TRANSITION) {
                bounds2.setChangeTransitStartBounds(runningTaskInfo.token, rect);
                bounds2.setChangeTransitMode(runningTaskInfo.token, 1, "onAppTiled");
            }
            desktopTilingWindowDecoration.lastFocusedTiledTaskId = runningTaskInfo.taskId;
            desktopTilingWindowDecoration.toggleResizeDesktopTaskTransitionHandler.startTransition(bounds2, rect, desktopTilingWindowDecoration$$ExternalSyntheticLambda2);
        } else if (snapBounds2.equals(rect)) {
            desktopTilingWindowDecoration$$ExternalSyntheticLambda2.invoke();
        } else {
            desktopTilingWindowDecoration.returnToDragStartAnimator.start(runningTaskInfo.taskId, appResizingHelper.desktopModeWindowDecoration.mTaskSurface, rect, snapBounds2, desktopTilingWindowDecoration$$ExternalSyntheticLambda2);
        }
        if (zEquals || (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 = this.taskbarDesktopTaskListener) == null) {
            return;
        }
        desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1.onTaskbarCornerRoundingUpdate(true);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        return false;
    }

    public final IBinder startLaunchTransition(int i, WindowContainerTransaction windowContainerTransaction, Integer num, RemoteTransition remoteTransition, int i2, int i3, DesktopModeEventLogger.Companion.UnminimizeReason unminimizeReason) throws Resources.NotFoundException {
        Function1 function1AddDeskActivationChanges$default;
        IBinder iBinderStartTransition;
        Function1 function1;
        WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction;
        logV$1("startLaunchTransition type=%s launchingTaskId=%d deskId=%d displayId=%d", WindowManager.transitTypeToString(i), num, Integer.valueOf(i2), Integer.valueOf(i3));
        Integer numAddAndGetMinimizeChanges = addAndGetMinimizeChanges(i2, windowContainerTransaction2, num, num == null);
        DesktopImmersiveController.ExitResult exitResultExitImmersiveIfApplicable = this.desktopImmersiveController.exitImmersiveIfApplicable(windowContainerTransaction2, i3, num, DesktopImmersiveController.ExitReason.TASK_LAUNCH);
        if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue() ? !(!DesktopExperienceFlags.ENABLE_DISPLAY_WINDOWING_MODE_SWITCHING.isTrue() || isDesktopModeShowing(i3)) : !this.taskRepository.isDeskActive(i2)) {
            function1AddDeskActivationChanges$default = null;
        } else {
            WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
            function1AddDeskActivationChanges$default = addDeskActivationChanges$default(this, i2, windowContainerTransaction3, null, 0, 0, 60);
            windowContainerTransaction3.merge(windowContainerTransaction2, true);
            DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 = this.desktopModeEnterExitTransitionListener;
            if (desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 != null) {
                desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1.onEnterDesktopModeTransitionStarted();
            }
            windowContainerTransaction2 = windowContainerTransaction3;
        }
        if (remoteTransition == null) {
            logV$1("startLaunchTransition -- no remoteTransition -- wct = " + windowContainerTransaction2, new Object[0]);
            DesktopImmersiveController.ExitResult.Exit exitAsExit = exitResultExitImmersiveIfApplicable.asExit();
            Integer numValueOf = exitAsExit != null ? Integer.valueOf(exitAsExit.exitingTask) : null;
            DesktopMixedTransitionHandler desktopMixedTransitionHandler = this.desktopMixedTransitionHandler;
            desktopMixedTransitionHandler.getClass();
            boolean zIsTrue = DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue();
            Transitions transitions = desktopMixedTransitionHandler.transitions;
            if (zIsTrue || DesktopModeFlags.ENABLE_DESKTOP_APP_LAUNCH_TRANSITIONS_BUGFIX.isTrue()) {
                if (numValueOf == null) {
                    DesktopMixedTransitionHandler.logV$6("Starting mixed launch transition for task#%d", num);
                } else {
                    DesktopMixedTransitionHandler.logV$6("Starting mixed launch transition for task#%d with immersive exit of task#%d", num, numValueOf);
                }
                IBinder iBinderStartTransition2 = transitions.startTransition(i, windowContainerTransaction2, desktopMixedTransitionHandler);
                List list = desktopMixedTransitionHandler.pendingMixedTransitions;
                iBinderStartTransition2.getClass();
                ((ArrayList) list).add(new DesktopMixedTransitionHandler.PendingMixedTransition.Launch(iBinderStartTransition2, num, numAddAndGetMinimizeChanges, numValueOf));
                iBinderStartTransition = iBinderStartTransition2;
            } else {
                iBinderStartTransition = transitions.startTransition(i, windowContainerTransaction2, null);
            }
        } else {
            Transitions transitions2 = this.transitions;
            ShellExecutor shellExecutor = this.mainExecutor;
            if (numAddAndGetMinimizeChanges == null) {
                OneShotRemoteHandler oneShotRemoteHandler = new OneShotRemoteHandler(shellExecutor, remoteTransition);
                iBinderStartTransition = transitions2.startTransition(i, windowContainerTransaction2, oneShotRemoteHandler);
                oneShotRemoteHandler.mTransition = iBinderStartTransition;
            } else {
                DesktopWindowLimitRemoteHandler desktopWindowLimitRemoteHandler = new DesktopWindowLimitRemoteHandler(shellExecutor, this.rootTaskDisplayAreaOrganizer, remoteTransition, numAddAndGetMinimizeChanges.intValue());
                iBinderStartTransition = transitions2.startTransition(i, windowContainerTransaction2, desktopWindowLimitRemoteHandler);
                iBinderStartTransition.getClass();
                desktopWindowLimitRemoteHandler.transition = iBinderStartTransition;
                desktopWindowLimitRemoteHandler.oneShotRemoteHandler.mTransition = iBinderStartTransition;
            }
        }
        if (numAddAndGetMinimizeChanges != null) {
            iBinderStartTransition.getClass();
            addPendingMinimizeTransition(iBinderStartTransition, numAddAndGetMinimizeChanges.intValue(), DesktopModeEventLogger.Companion.MinimizeReason.TASK_LIMIT);
        }
        if (num != null && this.taskRepository.isMinimizedTask(num.intValue())) {
            iBinderStartTransition.getClass();
            this.desktopTasksLimiter.ifPresent(new DesktopTasksControllerKt$sam$java_util_function_Consumer$0(new DesktopTasksController$$ExternalSyntheticLambda7(iBinderStartTransition, i3, num.intValue(), unminimizeReason, 0)));
        }
        if (function1AddDeskActivationChanges$default != null) {
            iBinderStartTransition.getClass();
            function1AddDeskActivationChanges$default.mo781invoke(iBinderStartTransition);
        }
        DesktopImmersiveController.ExitResult.Exit exitAsExit2 = exitResultExitImmersiveIfApplicable.asExit();
        if (exitAsExit2 != null && (function1 = exitAsExit2.runOnTransitionStart) != null) {
            iBinderStartTransition.getClass();
            function1.mo781invoke(iBinderStartTransition);
        }
        iBinderStartTransition.getClass();
        return iBinderStartTransition;
    }

    public final void toggleDesktopTask(ActivityManager.RunningTaskInfo runningTaskInfo, ToggleTaskSizeInteraction.Direction direction) {
        boolean zIsTaskMaximized = DesktopModeUtils.isTaskMaximized(runningTaskInfo, this.displayController);
        if (!zIsTaskMaximized && direction == ToggleTaskSizeInteraction.Direction.RESTORE) {
            logD$1("toggleDesktopTask taskId##%d is not maximized state", Integer.valueOf(runningTaskInfo.taskId));
            return;
        }
        if (zIsTaskMaximized && direction == ToggleTaskSizeInteraction.Direction.MAXIMIZE) {
            logD$1("toggleDesktopTask taskId##%d is maximized state", Integer.valueOf(runningTaskInfo.taskId));
            return;
        }
        logV$1("restoreDesktopTask taskId=%d displayId=%d bounds=%d", Integer.valueOf(runningTaskInfo.taskId), Integer.valueOf(runningTaskInfo.displayId), runningTaskInfo.configuration.windowConfiguration.getBounds());
        toggleDesktopTaskSize(runningTaskInfo, new ToggleTaskSizeInteraction(direction, ToggleTaskSizeInteraction.Source.KEYBOARD_SHORTCUT, DesktopModeEventLogger.Companion.InputMethod.KEYBOARD, null, 8, null));
    }

    public final void toggleDesktopTaskSize(ActivityManager.RunningTaskInfo runningTaskInfo, ToggleTaskSizeInteraction toggleTaskSizeInteraction) {
        Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
        Integer numValueOf = Integer.valueOf(bounds.width());
        Integer numValueOf2 = Integer.valueOf(bounds.height());
        DesktopModeEventLogger.Companion companion = DesktopModeEventLogger.Companion;
        this.desktopModeEventLogger.logTaskResizingStarted(toggleTaskSizeInteraction.resizeTrigger, toggleTaskSizeInteraction.inputMethod, runningTaskInfo, numValueOf, numValueOf2, this.displayController, null);
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
                DesktopStateImpl.Companion.getClass();
                if (DesktopStateImpl.Companion.inNonResizableDesktopWindowing(runningTaskInfo)) {
                    Rect rect3 = new Rect();
                    displayLayout.getStableBoundsByInsetsVisibility(rect3);
                    if (MultiWindowUtils.isTaskWidthOrHeightGreaterOrEqual(rect2, rect3)) {
                        rect2.set(DesktopModeUtils.calculateInitialBounds$default(displayLayout, runningTaskInfo, 0, null, 28));
                    }
                } else {
                    DisplayLayout displayLayout2 = (DisplayLayout) this.taskRepository.displayLayoutBeforeMaximizeByTaskId.removeReturnOld(runningTaskInfo.taskId);
                    int i = displayLayout.mRotation;
                    if (displayLayout2 != null) {
                        int i2 = displayLayout2.mRotation;
                        if (i2 == -1 || i == -1) {
                            return;
                        }
                        if (i2 % 2 != i % 2) {
                            Rect rect4 = new Rect();
                            Rect rect5 = new Rect();
                            rect4.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                            rect5.set(0, 0, displayLayout2.mWidth, displayLayout2.mHeight);
                            MultiWindowUtils.adjustBoundsForScreenRatio(rect5, rect4, rect2, rect2);
                        }
                    }
                }
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
            this.taskRepository.displayLayoutBeforeMaximizeByTaskId.set(runningTaskInfo.taskId, new DisplayLayout(displayLayout));
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

    public final void toggleExternalDisplay(int i) throws Resources.NotFoundException {
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

    public final Function1 updateDesksActivationOnDisconnection(int i, int i2, int i3, int i4, WindowContainerTransaction windowContainerTransaction, boolean z) {
        return z ? addDeskActivationChanges$default(this, i3, windowContainerTransaction, null, 0, i4, 28) : prepareDeskDeactivationIfNeeded$default(this, windowContainerTransaction, Integer.valueOf(i3), i, i2, 16);
    }

    public final DesktopModeVisualIndicator.IndicatorType updateVisualIndicator(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, float f, float f2, DesktopModeVisualIndicator.DragStartState dragStartState, boolean z, boolean z2, boolean z3) {
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
            desktopModeVisualIndicator = new DesktopModeVisualIndicator(this.desktopExecutor, this.mainExecutor, this.syncQueue, runningTaskInfo, displayController, displayContext, surfaceControl, this.rootTaskDisplayAreaOrganizer, dragStartState2, bubblePositioner, desktopModeWindowDecorViewModel == null ? null : desktopModeWindowDecorViewModel, this.recentTasksController);
        } else {
            desktopModeVisualIndicator = desktopModeVisualIndicator3;
        }
        if (this.visualIndicator == null) {
            setVisualIndicator(desktopModeVisualIndicator);
        }
        return desktopModeVisualIndicator.updateIndicatorType(new PointF(f, f2), runningTaskInfo, z, false, z2, z3);
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

    public final void moveTaskToFront(ActivityManager.RunningTaskInfo runningTaskInfo, RemoteTransition remoteTransition, DesktopModeEventLogger.Companion.UnminimizeReason unminimizeReason) throws Resources.NotFoundException {
        int i = runningTaskInfo.displayId;
        Integer activeDeskId = this.taskRepository.getActiveDeskId(i);
        if (activeDeskId == null) {
            logV$1("moveTaskToFront no active desk in displayId=%s", Integer.valueOf(i));
        }
        if (activeDeskId == null && (activeDeskId = this.taskRepository.getDeskIdForTask(runningTaskInfo.taskId)) == null && (activeDeskId = getOrCreateDefaultDeskId(runningTaskInfo.displayId, false)) == null) {
            return;
        }
        int iIntValue = activeDeskId.intValue();
        logV$1("moveTaskToFront taskId=%s deskId=%s", Integer.valueOf(runningTaskInfo.taskId), Integer.valueOf(iIntValue));
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
            Integer deskIdForTask = this.taskRepository.getDeskIdForTask(runningTaskInfo.taskId);
            DesksOrganizer desksOrganizer = this.desksOrganizer;
            if (deskIdForTask == null || deskIdForTask.intValue() != iIntValue) {
                ((RootTaskDesksOrganizer) desksOrganizer).moveTaskToDesk(windowContainerTransaction, iIntValue, runningTaskInfo);
            } else {
                ((RootTaskDesksOrganizer) desksOrganizer).reorderTaskToFront(windowContainerTransaction, iIntValue, runningTaskInfo);
            }
        } else {
            windowContainerTransaction.reorder(runningTaskInfo.token, true, true).getClass();
        }
        ArrayList runningTasks = this.shellTaskOrganizer.getRunningTasks(runningTaskInfo.displayId);
        ArrayList arrayList = new ArrayList();
        int size = runningTasks.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = runningTasks.get(i2);
            i2++;
            ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) obj;
            if (runningTaskInfo2.getActivityType() == 3 && runningTaskInfo2.isVisible) {
                arrayList.add(obj);
            }
        }
        ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList);
        if (runningTaskInfo3 != null) {
            windowContainerTransaction.reorder(runningTaskInfo3.token, false);
        }
        startLaunchTransition(3, windowContainerTransaction, Integer.valueOf(runningTaskInfo.taskId), remoteTransition, iIntValue, runningTaskInfo.displayId, unminimizeReason);
    }

    public static /* synthetic */ void getDesktopModeEnterExitTransitionListener$annotations() {
    }

    public static /* synthetic */ void getExitDesktopModeListener$annotations() {
    }

    public static /* synthetic */ void getTaskbarDesktopTaskListener$annotations() {
    }
}
