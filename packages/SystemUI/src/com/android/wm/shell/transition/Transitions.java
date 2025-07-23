package com.android.wm.shell.transition;

import android.R;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.app.IApplicationThread;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.tracing.perfetto.TraceFunction;
import android.tracing.perfetto.TracingContext;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.SparseIntArray;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.window.ITransitionPlayer;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowAnimationState;
import android.window.WindowContainerTransaction;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.systemui.display.data.repository.FocusedDisplayRepositoryImpl$focusedTask$1$listener$1;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.desktopmode.DesktopModeTransitionTypes;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.FocusTransitionListener;
import com.android.wm.shell.shared.IShellTransitions;
import com.android.wm.shell.shared.ShellTransitions;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.transition.change.ChangeTransitionProvider;
import com.android.wm.shell.transition.tracing.PerfettoTransitionTracer;
import com.android.wm.shell.transition.tracing.PerfettoTransitionTracer$$ExternalSyntheticLambda4;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class Transitions implements RemoteCallable, ShellCommandHandler.ShellCommandActionHandler {
    public static final boolean DEBUG_START_TRANSITION;
    public static final boolean ENABLE_SHELL_TRANSITIONS;
    public static final boolean SHELL_TRANSITIONS_ROTATION;
    public final ShellExecutor mAnimExecutor;
    public final ChangeTransitionProvider mChangeTransitProvider;
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final FocusTransitionObserver mFocusTransitionObserver;
    public final ArrayList mHandlers;
    public final HomeTransitionObserver mHomeTransitionObserver;
    public final ShellTransitionImpl mImpl;
    public boolean mIsRegistered;
    public final ArrayMap mKnownTransitions;
    public final ShellExecutor mMainExecutor;
    public final MultiTaskingTransitionProvider mMultiTaskingTransitProvider;
    public final ArrayList mObservers;
    public final ShellTaskOrganizer mOrganizer;
    public final ArrayList mPendingTransitions;
    public final TransitionPlayerImpl mPlayerImpl;
    public final ArrayList mReadyDuringSync;
    public StageCoordinator.RecentsTransitionCallback mRecentTransitionCallback;
    public final RemoteTransitionHandler mRemoteTransitionHandler;
    public final ArrayList mRunWhenIdleQueue;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public final SleepHandler mSleepHandler;
    public final ArrayList mTracks;
    public float mTransitionAnimationScaleSetting;
    public final PerfettoTransitionTracer mTransitionTracer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ActiveTransition {
        public boolean mAborted;
        public boolean mApplyStartTransactionOnMerged;
        public String mApplyStartTransactionReason;
        public SurfaceControl.Transaction mFinishT;
        public TransitionHandler mHandler;
        public TransitionInfo mInfo;
        public ArrayList mMerged;
        public String mPendingCallStack;
        public long mPendingTime;
        public SurfaceControl.Transaction mStartT;
        public final IBinder mToken;
        public ArrayList mTransfer;

        public ActiveTransition(IBinder iBinder) {
            this.mToken = iBinder;
        }

        public final String getTimeoutInfo() {
            StringBuilder sb = new StringBuilder(" Timeout{duration=");
            sb.append(System.currentTimeMillis() - this.mPendingTime);
            sb.append(", callers=");
            return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.mPendingCallStack, "}");
        }

        public final int getTrack() {
            TransitionInfo transitionInfo = this.mInfo;
            if (transitionInfo != null) {
                return transitionInfo.getTrack();
            }
            return -1;
        }

        public final boolean isTimeout() {
            return System.currentTimeMillis() - this.mPendingTime > 60000;
        }

        public final String toString() {
            TransitionInfo transitionInfo = this.mInfo;
            String str = "";
            if (transitionInfo == null || transitionInfo.getDebugId() < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.mToken.toString());
                sb.append("@");
                sb.append(getTrack());
                if (CoreRune.MW_SHELL_TRANSITION_TIMEOUT && isTimeout()) {
                    str = getTimeoutInfo();
                }
                sb.append(str);
                return sb.toString();
            }
            StringBuilder sb2 = new StringBuilder("(#");
            sb2.append(this.mInfo.getDebugId());
            sb2.append(") ");
            sb2.append(this.mToken);
            sb2.append("@");
            sb2.append(getTrack());
            if (CoreRune.MW_SHELL_TRANSITION_TIMEOUT && isTimeout()) {
                str = getTimeoutInfo();
            }
            sb2.append(str);
            return sb2.toString();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class IShellTransitionsImpl extends IShellTransitions.Stub implements ExternalInterfaceBinder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public Transitions mTransitions;

        public IShellTransitionsImpl(Transitions transitions) {
            this.mTransitions = transitions;
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            Transitions transitions = this.mTransitions;
            HomeTransitionObserver homeTransitionObserver = transitions.mHomeTransitionObserver;
            homeTransitionObserver.getClass();
            transitions.mObservers.remove(homeTransitionObserver);
            SingleInstanceRemoteListener singleInstanceRemoteListener = homeTransitionObserver.mListener;
            if (singleInstanceRemoteListener != null) {
                singleInstanceRemoteListener.unregister();
            }
            this.mTransitions = null;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SettingsObserver extends ContentObserver {
        public SettingsObserver() {
            super(null);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            Transitions transitions = Transitions.this;
            boolean z2 = Transitions.DEBUG_START_TRANSITION;
            transitions.mTransitionAnimationScaleSetting = WindowManager.fixScale(Settings.Global.getFloat(transitions.mContext.getContentResolver(), SettingsHelper.INDEX_TRANSITION_ANIMATION_SCALE, transitions.mContext.getResources().getFloat(R.dimen.config_resActivitySnapshotScale)));
            Transitions.this.mMainExecutor.execute(new Transitions$$ExternalSyntheticLambda1(this, 1));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ShellTransitionImpl implements ShellTransitions {
        public /* synthetic */ ShellTransitionImpl(Transitions transitions, int i) {
            this();
        }

        @Override // com.android.wm.shell.shared.ShellTransitions
        public final void registerRemote(TransitionFilter transitionFilter, RemoteTransition remoteTransition) {
            Transitions.this.mMainExecutor.execute(new Transitions$ShellTransitionImpl$$ExternalSyntheticLambda0(this, transitionFilter, 0, remoteTransition));
        }

        @Override // com.android.wm.shell.shared.ShellTransitions
        public final void setFocusTransitionListener(FocusedDisplayRepositoryImpl$focusedTask$1$listener$1 focusedDisplayRepositoryImpl$focusedTask$1$listener$1, Executor executor) {
            Transitions.this.mMainExecutor.execute(new Transitions$ShellTransitionImpl$$ExternalSyntheticLambda0(this, focusedDisplayRepositoryImpl$focusedTask$1$listener$1, 1, executor));
        }

        @Override // com.android.wm.shell.shared.ShellTransitions
        public final void unregisterRemote(RemoteTransition remoteTransition) {
            Transitions.this.mMainExecutor.execute(new Transitions$ShellTransitionImpl$$ExternalSyntheticLambda1(0, this, remoteTransition));
        }

        @Override // com.android.wm.shell.shared.ShellTransitions
        public final void unsetFocusTransitionListener(FocusTransitionListener focusTransitionListener) {
            Transitions.this.mMainExecutor.execute(new Transitions$ShellTransitionImpl$$ExternalSyntheticLambda1(1, this, focusTransitionListener));
        }

        private ShellTransitionImpl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Track {
        public ActiveTransition mActiveTransition;
        public final ArrayList mReadyTransitions;

        public /* synthetic */ Track(int i) {
            this();
        }

        private Track() {
            this.mReadyTransitions = new ArrayList();
            this.mActiveTransition = null;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TransitionFinishCallback {
        void onTransitionFinished(WindowContainerTransaction windowContainerTransaction);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TransitionHandler {
        default boolean canMergeAbortedTransition(TransitionInfo transitionInfo) {
            return false;
        }

        default TransitionHandler getHandlerForTakeover(IBinder iBinder, TransitionInfo transitionInfo) {
            return null;
        }

        WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo);

        default void mergeAnimation() {
        }

        boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, TransitionFinishCallback transitionFinishCallback);

        default boolean takeOverAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, TransitionFinishCallback transitionFinishCallback, WindowAnimationState[] windowAnimationStateArr) {
            return false;
        }

        default void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, TransitionFinishCallback transitionFinishCallback) {
            mergeAnimation();
        }

        default void setAnimScaleSetting(float f) {
        }

        default void beforeMergeAnimation(IBinder iBinder, TransitionHandler transitionHandler) {
        }

        default void transitionReady(IBinder iBinder, TransitionInfo transitionInfo) {
        }

        default void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        }

        default void transferAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, WindowContainerTransaction windowContainerTransaction) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TransitionPlayerImpl extends ITransitionPlayer.Stub {
        public /* synthetic */ TransitionPlayerImpl(Transitions transitions, int i) {
            this();
        }

        public final void onTransitionReady(final IBinder iBinder, final TransitionInfo transitionInfo, final SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 6541979577719206723L, 1, Long.valueOf(transaction.getId()));
            }
            Transitions.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.transition.Transitions$TransitionPlayerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Transitions.TransitionPlayerImpl transitionPlayerImpl = Transitions.TransitionPlayerImpl.this;
                    Transitions.this.onTransitionReady(iBinder, transitionInfo, transaction, transaction2);
                }
            });
        }

        public final void requestStartTransition(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
            Transitions.this.mMainExecutor.execute(new Transitions$ShellTransitionImpl$$ExternalSyntheticLambda0(this, iBinder, 2, transitionRequestInfo));
        }

        public final void transitionAborted(IBinder iBinder) {
            Transitions.this.mMainExecutor.execute(new Transitions$ShellTransitionImpl$$ExternalSyntheticLambda1(2, this, iBinder));
        }

        private TransitionPlayerImpl() {
        }
    }

    static {
        boolean z;
        DEBUG_START_TRANSITION = Build.IS_DEBUGGABLE && SystemProperties.getBoolean("persist.wm.debug.start_shell_transition", false);
        try {
        } catch (RemoteException unused) {
            Log.w("ShellTransitions", "Error getting system features");
        }
        if (AppGlobals.getPackageManager().hasSystemFeature("android.hardware.type.automotive", 0)) {
            z = SystemProperties.getBoolean("persist.wm.debug.shell_transit", true);
            ENABLE_SHELL_TRANSITIONS = z;
            SHELL_TRANSITIONS_ROTATION = !z && SystemProperties.getBoolean("persist.wm.debug.shell_transit_rotate", false);
        }
        z = true;
        ENABLE_SHELL_TRANSITIONS = z;
        SHELL_TRANSITIONS_ROTATION = !z && SystemProperties.getBoolean("persist.wm.debug.shell_transit_rotate", false);
    }

    public Transitions(Context context, ShellInit shellInit, ShellController shellController, ShellTaskOrganizer shellTaskOrganizer, TransactionPool transactionPool, DisplayController displayController, DisplayInsetsController displayInsetsController, ShellExecutor shellExecutor, Handler handler, ShellExecutor shellExecutor2, Handler handler2, HomeTransitionObserver homeTransitionObserver, FocusTransitionObserver focusTransitionObserver) {
        this(context, shellInit, new ShellCommandHandler(), shellController, shellTaskOrganizer, transactionPool, displayController, displayInsetsController, shellExecutor, handler, shellExecutor2, handler2, new RootTaskDisplayAreaOrganizer(shellExecutor, context, shellInit), homeTransitionObserver, focusTransitionObserver);
    }

    public static int calculateAnimLayer(TransitionInfo.Change change, int i, int i2, int i3) {
        int i4 = i2 + 1;
        boolean isOpeningType = TransitionUtil.isOpeningType(i3);
        boolean isClosingType = TransitionUtil.isClosingType(i3);
        int mode = change.getMode();
        if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX && (change.getFlags() & 2) != 0) {
            return ((mode == 1 || mode == 3) ? (-i4) + i2 : -i4) - i;
        }
        if (mode == 1 || mode == 3) {
            if (!isOpeningType && (isClosingType || (CoreRune.MW_SPLIT_SHELL_TRANSITION && i3 == 1104 && TransitionUtil.isHomeOrRecents(change)))) {
                return i4 - i;
            }
        } else if (mode == 2 || mode == 4) {
            if (isOpeningType) {
                return i4 - i;
            }
        } else if (isClosingType || TransitionUtil.isOrderOnly(change)) {
            return i4 - i;
        }
        return (i4 + i2) - i;
    }

    public static boolean hasDuplicatedOpenTypeChanges(TransitionInfo transitionInfo) {
        if (TransitionUtil.isClosingType(transitionInfo.getType())) {
            return false;
        }
        SparseIntArray sparseIntArray = new SparseIntArray();
        for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
            if (TransitionUtil.isOpeningType(change.getMode()) && change.getConfiguration().windowConfiguration.isSplitScreen()) {
                sparseIntArray.put(change.getConfiguration().windowConfiguration.getStageType(), 1);
            }
        }
        if (sparseIntArray.size() <= 1) {
            return false;
        }
        Log.d("ShellTransitions", "duplicated split open changes in default transition");
        return true;
    }

    public static void setRunningRemoteTransitionDelegate(IApplicationThread iApplicationThread) {
        if (iApplicationThread == null) {
            return;
        }
        try {
            ActivityTaskManager.getService().setRunningRemoteTransitionDelegate(iApplicationThread);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        } catch (SecurityException unused) {
            Log.e("ShellTransitions", "Unable to boost animation process. This should only happen during unit tests");
        }
    }

    public static String transitTypeToString(int i) {
        String str;
        if (i < 1000) {
            return WindowManager.transitTypeToString(i);
        }
        String str2 = "";
        if (i != 1024) {
            switch (i) {
                case 1001:
                    str = "EXIT_PIP";
                    break;
                case 1002:
                    str = "EXIT_PIP_TO_SPLIT";
                    break;
                case 1003:
                    str = "REMOVE_PIP";
                    break;
                case VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI /* 1004 */:
                    str = "SPLIT_SCREEN_PAIR_OPEN";
                    break;
                case 1005:
                    str = "SPLIT_SCREEN_OPEN_TO_SIDE";
                    break;
                case 1006:
                    str = "SPLIT_DISMISS_SNAP";
                    break;
                case 1007:
                    str = "SPLIT_DISMISS";
                    break;
                case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS /* 1008 */:
                    str = "MAXIMIZE";
                    break;
                case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE /* 1009 */:
                    str = "RESTORE_FROM_MAXIMIZE";
                    break;
                default:
                    switch (i) {
                        case EnterpriseContainerCallback.CONTAINER_CANCELLED /* 1016 */:
                            str = "RESIZE_PIP";
                            break;
                        case 1017:
                            str = "TASK_FRAGMENT_DRAG_RESIZE";
                            break;
                        case 1018:
                            str = "SPLIT_PASSTHROUGH";
                            break;
                        case 1019:
                            str = "CLEANUP_PIP_EXIT";
                            break;
                        case 1020:
                            str = "MINIMIZE";
                            break;
                        case 1021:
                            str = "START_RECENTS_TRANSITION";
                            break;
                        case 1022:
                            str = "END_RECENTS_TRANSITION";
                            break;
                        default:
                            str = "";
                            break;
                    }
            }
        } else {
            str = "CONVERT_TO_BUBBLE";
        }
        if (str.isEmpty()) {
            int i2 = DesktopModeTransitionTypes.$r8$clinit;
            switch (i) {
                case VolteConstants.ErrorCode.CALL_SWITCH_FAILURE /* 1109 */:
                    str2 = "DESKTOP_MODE_START_DRAG_TO_DESKTOP";
                    break;
                case VolteConstants.ErrorCode.CALL_SWITCH_REJECTED /* 1110 */:
                    str2 = "DESKTOP_MODE_END_DRAG_TO_DESKTOP";
                    break;
                case VolteConstants.ErrorCode.CALL_HOLD_FAILED /* 1111 */:
                    str2 = "DESKTOP_MODE_CANCEL_DRAG_TO_DESKTOP";
                    break;
                case VolteConstants.ErrorCode.CALL_RESUME_FAILED /* 1112 */:
                    str2 = "DESKTOP_MODE_TOGGLE_RESIZE";
                    break;
            }
            str = str2;
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "(FIRST_CUSTOM+");
        m.append(i - 1000);
        m.append(")");
        return m.toString();
    }

    public final void addHandler(TransitionHandler transitionHandler) {
        if (this.mHandlers.isEmpty()) {
            throw new RuntimeException("Unexpected handler added prior to initialization, please use ShellInit callbacks to ensure proper ordering");
        }
        this.mHandlers.add(transitionHandler);
        transitionHandler.setAnimScaleSetting(this.mTransitionAnimationScaleSetting);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 6809365603763764620L, 0, transitionHandler.getClass().getSimpleName());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:136:0x031c, code lost:
    
        if (r9.getTaskInfo().isForceHidden != false) goto L172;
     */
    /* JADX WARN: Removed duplicated region for block: B:140:0x037b  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0479  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean dispatchReady(com.android.wm.shell.transition.Transitions.ActiveTransition r25) {
        /*
            Method dump skipped, instructions count: 1267
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.transition.Transitions.dispatchReady(com.android.wm.shell.transition.Transitions$ActiveTransition):boolean");
    }

    public final Pair dispatchRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo, DefaultMixedHandler defaultMixedHandler) {
        WindowContainerTransaction handleRequest;
        for (int size = this.mHandlers.size() - 1; size >= 0; size--) {
            if (this.mHandlers.get(size) != defaultMixedHandler && (handleRequest = ((TransitionHandler) this.mHandlers.get(size)).handleRequest(iBinder, transitionRequestInfo)) != null) {
                return new Pair((TransitionHandler) this.mHandlers.get(size), handleRequest);
            }
        }
        return null;
    }

    public final TransitionHandler dispatchTransition(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, TransitionFinishCallback transitionFinishCallback, TransitionHandler transitionHandler, TransitionHandler transitionHandler2) {
        for (int size = this.mHandlers.size() - 1; size >= 0; size--) {
            if (this.mHandlers.get(size) != transitionHandler) {
                if (CoreRune.MW_PIP_SHELL_TRANSITION && this.mHandlers.get(size) == transitionHandler2) {
                }
                if (((TransitionHandler) this.mHandlers.get(size)).startAnimation(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback)) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 5292399587644158186L, 0, String.valueOf(this.mHandlers.get(size)));
                    }
                    this.mTransitionTracer.logDispatched(transitionInfo.getDebugId(), (TransitionHandler) this.mHandlers.get(size));
                    if (Trace.isTagEnabled(32L)) {
                        Trace.instant(32L, ((TransitionHandler) this.mHandlers.get(size)).getClass().getSimpleName() + "#startAnimation animated " + transitTypeToString(transitionInfo.getType()));
                    }
                    return (TransitionHandler) this.mHandlers.get(size);
                }
            } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 8716556112614799549L, 0, String.valueOf(this.mHandlers.get(size)));
            }
        }
        throw new IllegalStateException("This shouldn't happen, maybe the default handler is broken.");
    }

    public final void finishForSync(final IBinder iBinder, final int i, ActiveTransition activeTransition) {
        ActiveTransition activeTransition2;
        if (!this.mKnownTransitions.containsKey(iBinder)) {
            Log.d("ShellTransitions", "finishForSleep: already played sync transition " + iBinder);
            return;
        }
        Track track = (Track) this.mTracks.get(i);
        char c = 1;
        if (activeTransition != null) {
            Track track2 = (Track) this.mTracks.get(activeTransition.getTrack());
            if (track2 != track) {
                Log.e("ShellTransitions", "finishForSleep: mismatched Tracks between forceFinish and logic " + activeTransition.getTrack() + " vs " + i);
            }
            if (track2.mActiveTransition == activeTransition) {
                Log.e("ShellTransitions", "Forcing transition to finish due to sync timeout: " + activeTransition);
                activeTransition.mAborted = true;
                TransitionHandler transitionHandler = activeTransition.mHandler;
                if (transitionHandler != null) {
                    transitionHandler.onTransitionConsumed(activeTransition.mToken, true, null);
                }
                onFinish(activeTransition.mToken, null);
            }
        }
        if ((track.mActiveTransition == null && track.mReadyTransitions.isEmpty()) || this.mReadyDuringSync.isEmpty()) {
            return;
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        TransitionInfo transitionInfo = new TransitionInfo(12, 0);
        while (track.mActiveTransition != null && !this.mReadyDuringSync.isEmpty()) {
            final ActiveTransition activeTransition3 = track.mActiveTransition;
            ActiveTransition activeTransition4 = (ActiveTransition) this.mReadyDuringSync.get(0);
            if ((activeTransition4.mInfo.getFlags() & 2097152) == 0) {
                Log.e("ShellTransitions", "Somehow blocked on a non-sync transition? " + activeTransition4);
            }
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[c]) {
                activeTransition2 = activeTransition4;
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -8650312174245760311L, 0, String.valueOf(activeTransition4), String.valueOf(activeTransition3));
            } else {
                activeTransition2 = activeTransition4;
            }
            activeTransition3.mHandler.mergeAnimation(activeTransition2.mToken, transitionInfo, transaction, transaction, activeTransition3.mToken, new Transitions$$ExternalSyntheticLambda3());
            if (track.mActiveTransition == activeTransition3) {
                ((HandlerExecutor) this.mMainExecutor).executeDelayed(new Runnable() { // from class: com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        Transitions transitions = Transitions.this;
                        IBinder iBinder2 = iBinder;
                        int i2 = i;
                        Transitions.ActiveTransition activeTransition5 = activeTransition3;
                        boolean z = Transitions.DEBUG_START_TRANSITION;
                        transitions.finishForSync(iBinder2, i2, activeTransition5);
                    }
                }, 120L);
                return;
            }
            c = 1;
        }
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    public final void onAbort(ActiveTransition activeTransition) {
        Track track = (Track) this.mTracks.get(activeTransition.getTrack());
        activeTransition.mAborted = true;
        PerfettoTransitionTracer perfettoTransitionTracer = this.mTransitionTracer;
        final int debugId = activeTransition.mInfo.getDebugId();
        if (perfettoTransitionTracer.mActiveTraces.get() > 0) {
            Trace.traceBegin(32L, "logAborted");
            try {
                perfettoTransitionTracer.mDataSource.trace(new TraceFunction() { // from class: com.android.wm.shell.transition.tracing.PerfettoTransitionTracer$$ExternalSyntheticLambda0
                    public final void trace(TracingContext tracingContext) {
                        int i = debugId;
                        ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
                        long start = newTracePacket.start(1146756268128L);
                        newTracePacket.write(1120986464257L, i);
                        newTracePacket.write(1112396529671L, SystemClock.elapsedRealtimeNanos());
                        newTracePacket.end(start);
                    }
                });
            } finally {
                Trace.traceEnd(32L);
            }
        }
        TransitionHandler transitionHandler = activeTransition.mHandler;
        if (transitionHandler != null) {
            transitionHandler.onTransitionConsumed(activeTransition.mToken, true, null);
        }
        TransitionInfo transitionInfo = activeTransition.mInfo;
        if (transitionInfo != null) {
            transitionInfo.releaseAnimSurfaces();
        }
        if (track.mReadyTransitions.size() > 1) {
            return;
        }
        processReadyQueue(track);
    }

    public final void onFinish(IBinder iBinder, WindowContainerTransaction windowContainerTransaction) {
        ActiveTransition activeTransition;
        ArrayList arrayList;
        SurfaceControl.Transaction transaction;
        IBinder iBinder2;
        ((HandlerExecutor) this.mMainExecutor).assertCurrentThread();
        ActiveTransition activeTransition2 = (ActiveTransition) this.mKnownTransitions.get(iBinder);
        if (activeTransition2 == null) {
            Log.e("ShellTransitions", "Trying to finish a non-existent transition: " + iBinder);
            return;
        }
        if (activeTransition2.getTrack() < 0) {
            Log.e("ShellTransitions", "Trying to finish a invalid track transition: " + iBinder);
            return;
        }
        Track track = (Track) this.mTracks.get(activeTransition2.getTrack());
        if (track == null || track.mActiveTransition != activeTransition2) {
            if (!CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER || (activeTransition = track.mActiveTransition) == null || (arrayList = activeTransition.mTransfer) == null || !arrayList.contains(activeTransition2)) {
                Log.e("ShellTransitions", "Trying to finish a non-running transition. Either remote crashed or  a handler didn't properly deal with a merge. " + activeTransition2, new RuntimeException());
                return;
            } else {
                Log.d("ShellTransitions", "Finishing is skipped due to transferred transit=" + activeTransition2);
                return;
            }
        }
        track.mActiveTransition = null;
        for (int i = 0; i < this.mObservers.size(); i++) {
            ((TransitionObserver) this.mObservers.get(i)).onTransitionFinished(activeTransition2.mToken, activeTransition2.mAborted);
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 5216177164274512366L, 3, Boolean.valueOf(activeTransition2.mAborted), String.valueOf(activeTransition2));
        }
        SurfaceControl.Transaction transaction2 = activeTransition2.mStartT;
        if (transaction2 != null) {
            transaction2.clear();
        }
        if (!CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER || activeTransition2.mTransfer == null) {
            transaction = null;
        } else {
            transaction = null;
            for (int i2 = 0; i2 < activeTransition2.mTransfer.size(); i2++) {
                ActiveTransition activeTransition3 = (ActiveTransition) activeTransition2.mTransfer.get(i2);
                SurfaceControl.Transaction transaction3 = activeTransition3.mStartT;
                if (transaction3 != null) {
                    if (transaction == null) {
                        transaction = transaction3;
                    } else {
                        transaction.merge(transaction3);
                    }
                }
                SurfaceControl.Transaction transaction4 = activeTransition3.mFinishT;
                if (transaction4 != null) {
                    if (transaction == null) {
                        transaction = transaction4;
                    } else {
                        transaction.merge(transaction4);
                    }
                }
                if (activeTransition3.mMerged != null) {
                    for (int i3 = 0; i3 < activeTransition3.mMerged.size(); i3++) {
                        ActiveTransition activeTransition4 = (ActiveTransition) activeTransition3.mMerged.get(i3);
                        SurfaceControl.Transaction transaction5 = activeTransition4.mStartT;
                        if (transaction5 != null) {
                            if (transaction == null) {
                                transaction = transaction5;
                            } else {
                                transaction.merge(transaction5);
                            }
                        }
                        SurfaceControl.Transaction transaction6 = activeTransition4.mFinishT;
                        if (transaction6 != null) {
                            if (transaction == null) {
                                transaction = transaction6;
                            } else {
                                transaction.merge(transaction6);
                            }
                        }
                    }
                }
            }
            if (transaction != null) {
                transaction.merge(activeTransition2.mFinishT);
            }
        }
        if (!CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER || transaction == null) {
            transaction = activeTransition2.mFinishT;
        }
        if (activeTransition2.mMerged != null) {
            for (int i4 = 0; i4 < activeTransition2.mMerged.size(); i4++) {
                ActiveTransition activeTransition5 = (ActiveTransition) activeTransition2.mMerged.get(i4);
                SurfaceControl.Transaction transaction7 = activeTransition5.mStartT;
                if (transaction7 != null) {
                    if (transaction == null) {
                        transaction = transaction7;
                    } else {
                        transaction.merge(transaction7);
                    }
                }
                SurfaceControl.Transaction transaction8 = activeTransition5.mFinishT;
                if (transaction8 != null) {
                    if (transaction == null) {
                        transaction = transaction8;
                    } else {
                        transaction.merge(transaction8);
                    }
                }
            }
        }
        if (transaction != null) {
            transaction.apply();
        }
        WindowContainerTransaction windowContainerTransaction2 = CoreRune.FW_SHELL_TRANSITION_BUG_FIX ? new WindowContainerTransaction() : null;
        boolean z = CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER;
        ShellTaskOrganizer shellTaskOrganizer = this.mOrganizer;
        if (z && activeTransition2.mTransfer != null) {
            for (int i5 = 0; i5 < activeTransition2.mTransfer.size(); i5++) {
                ActiveTransition activeTransition6 = (ActiveTransition) activeTransition2.mTransfer.get(i5);
                if (windowContainerTransaction2 != null) {
                    windowContainerTransaction2.addTransferTransitionToken(activeTransition6.mToken);
                } else {
                    shellTaskOrganizer.finishTransition(activeTransition6.mToken, (WindowContainerTransaction) null);
                }
                TransitionInfo transitionInfo = activeTransition6.mInfo;
                if (transitionInfo != null) {
                    transitionInfo.releaseAnimSurfaces();
                }
                if (activeTransition6.mMerged != null) {
                    for (int i6 = 0; i6 < activeTransition6.mMerged.size(); i6++) {
                        ActiveTransition activeTransition7 = (ActiveTransition) activeTransition6.mMerged.get(i6);
                        if (windowContainerTransaction2 != null) {
                            windowContainerTransaction2.addTransferTransitionToken(activeTransition7.mToken);
                        } else {
                            shellTaskOrganizer.finishTransition(activeTransition7.mToken, (WindowContainerTransaction) null);
                        }
                        TransitionInfo transitionInfo2 = activeTransition7.mInfo;
                        if (transitionInfo2 != null) {
                            transitionInfo2.releaseAnimSurfaces();
                        }
                    }
                    activeTransition6.mMerged.clear();
                }
            }
            activeTransition2.mTransfer.clear();
        }
        TransitionInfo transitionInfo3 = activeTransition2.mInfo;
        if (transitionInfo3 != null) {
            transitionInfo3.releaseAnimSurfaces();
        }
        if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX) {
            iBinder2 = activeTransition2.mToken;
        } else {
            shellTaskOrganizer.finishTransition(activeTransition2.mToken, windowContainerTransaction);
            iBinder2 = null;
        }
        if (activeTransition2.mMerged != null) {
            for (int i7 = 0; i7 < activeTransition2.mMerged.size(); i7++) {
                ActiveTransition activeTransition8 = (ActiveTransition) activeTransition2.mMerged.get(i7);
                if (!CoreRune.FW_SHELL_TRANSITION_BUG_FIX || windowContainerTransaction2 == null) {
                    shellTaskOrganizer.finishTransition(activeTransition8.mToken, (WindowContainerTransaction) null);
                } else {
                    windowContainerTransaction2.addMergedTransitionToken(activeTransition8.mToken);
                }
                TransitionInfo transitionInfo4 = activeTransition8.mInfo;
                if (transitionInfo4 != null) {
                    transitionInfo4.releaseAnimSurfaces();
                }
                this.mKnownTransitions.remove(activeTransition8.mToken);
            }
            activeTransition2.mMerged.clear();
        }
        this.mKnownTransitions.remove(iBinder);
        if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX) {
            shellTaskOrganizer.finishAllTransitions(iBinder2, windowContainerTransaction, windowContainerTransaction2);
        }
        processReadyQueue(track);
    }

    public final void onMerged(IBinder iBinder, IBinder iBinder2) {
        int indexOf;
        ((HandlerExecutor) this.mMainExecutor).assertCurrentThread();
        ActiveTransition activeTransition = (ActiveTransition) this.mKnownTransitions.get(iBinder);
        if (activeTransition == null) {
            Log.e("ShellTransitions", "Merging into a non-existent transition: " + iBinder);
            return;
        }
        ActiveTransition activeTransition2 = (ActiveTransition) this.mKnownTransitions.get(iBinder2);
        if (activeTransition2 == null) {
            Log.e("ShellTransitions", "Merging a non-existent transition: " + iBinder2);
            return;
        }
        if (activeTransition.getTrack() != activeTransition2.getTrack()) {
            throw new IllegalStateException("Can't merge across tracks: " + activeTransition2 + " into " + activeTransition);
        }
        Track track = (Track) this.mTracks.get(activeTransition.getTrack());
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 7347095270847821413L, 0, String.valueOf(activeTransition2), String.valueOf(activeTransition));
        }
        if (track.mReadyTransitions.isEmpty() || track.mReadyTransitions.get(0) != activeTransition2) {
            Log.e("ShellTransitions", "Merged transition out-of-order? " + activeTransition2);
            indexOf = track.mReadyTransitions.indexOf(activeTransition2);
            if (indexOf < 0) {
                Log.e("ShellTransitions", "Merged a transition that is no-longer queued? " + activeTransition2);
                return;
            }
        } else {
            indexOf = 0;
        }
        track.mReadyTransitions.remove(indexOf);
        if (activeTransition.mMerged == null) {
            activeTransition.mMerged = new ArrayList();
        }
        activeTransition.mMerged.add(activeTransition2);
        TransitionHandler transitionHandler = activeTransition2.mHandler;
        if (transitionHandler != null && !activeTransition2.mAborted) {
            transitionHandler.onTransitionConsumed(activeTransition2.mToken, false, activeTransition2.mFinishT);
        }
        for (int i = 0; i < this.mObservers.size(); i++) {
            ((TransitionObserver) this.mObservers.get(i)).onTransitionMerged(activeTransition2.mToken, activeTransition.mToken);
        }
        if (CoreRune.MW_EMBED_ACTIVITY_PERFORMANCE && activeTransition2.mStartT != null && activeTransition2.mApplyStartTransactionOnMerged) {
            Log.d("ShellTransitions", "onMerged: apply startT, reason=" + activeTransition2.mApplyStartTransactionReason + ", merged=" + activeTransition2);
            activeTransition2.mStartT.apply();
        }
        PerfettoTransitionTracer perfettoTransitionTracer = this.mTransitionTracer;
        int debugId = activeTransition2.mInfo.getDebugId();
        int debugId2 = activeTransition.mInfo.getDebugId();
        if (perfettoTransitionTracer.mActiveTraces.get() > 0) {
            Trace.traceBegin(32L, "logMerged");
            try {
                perfettoTransitionTracer.mDataSource.trace(new PerfettoTransitionTracer$$ExternalSyntheticLambda4(debugId, debugId2, 1));
            } finally {
                Trace.traceEnd(32L);
            }
        }
        processReadyQueue(track);
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final boolean onShellCommand(PrintWriter printWriter, String[] strArr) {
        if (strArr.length == 0) {
            return false;
        }
        String str = strArr[0];
        str.getClass();
        if (str.equals("tracing")) {
            printWriter.println("Command not supported. Use the Perfetto command instead to start and stop this trace instead.");
            return false;
        }
        CarrierTextController$$ExternalSyntheticOutline0.m(new StringBuilder("Invalid command: "), strArr[0], printWriter);
        return false;
    }

    public void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        transitionInfo.setUnreleasedWarningCallSiteForAllSurfaces("Transitions.onTransitionReady");
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1354476626241269833L, 1, Long.valueOf(transitionInfo.getDebugId()), String.valueOf(iBinder), String.valueOf(transitionInfo.toString("    ")));
        }
        ArrayList arrayList = this.mPendingTransitions;
        int size = arrayList.size() - 1;
        while (true) {
            if (size < 0) {
                size = -1;
                break;
            } else if (((ActiveTransition) arrayList.get(size)).mToken == iBinder) {
                break;
            } else {
                size--;
            }
        }
        if (size < 0) {
            ActiveTransition activeTransition = (ActiveTransition) this.mKnownTransitions.get(iBinder);
            if (activeTransition != null) {
                Log.e("ShellTransitions", "Got duplicate transitionReady for " + iBinder);
                transaction.apply();
                SurfaceControl.Transaction transaction3 = activeTransition.mFinishT;
                if (transaction3 != null) {
                    transaction3.merge(transaction2);
                    return;
                } else {
                    activeTransition.mFinishT = transaction2;
                    return;
                }
            }
            Log.wtf("ShellTransitions", "Got transitionReady for non-pending transition " + iBinder + ". expecting one of " + Arrays.toString(this.mPendingTransitions.stream().map(new Transitions$$ExternalSyntheticLambda0(0)).toArray()));
            ActiveTransition activeTransition2 = new ActiveTransition(iBinder);
            this.mKnownTransitions.put(iBinder, activeTransition2);
            this.mPendingTransitions.add(activeTransition2);
            size = this.mPendingTransitions.size() + (-1);
        }
        ActiveTransition activeTransition3 = (ActiveTransition) this.mPendingTransitions.remove(size);
        activeTransition3.mInfo = transitionInfo;
        activeTransition3.mStartT = transaction;
        activeTransition3.mFinishT = transaction2;
        if (size > 0) {
            Log.i("ShellTransitions", "Transition might be ready out-of-order " + size + " for " + activeTransition3 + ". This is ok if it's on a different track.");
        }
        if (this.mReadyDuringSync.isEmpty()) {
            dispatchReady(activeTransition3);
        } else {
            this.mReadyDuringSync.add(activeTransition3);
        }
    }

    public final void playTransition(ActiveTransition activeTransition) {
        final IBinder iBinder;
        boolean z;
        TransitionInfo transitionInfo;
        int i;
        boolean z2 = true;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 7933815964243868580L, 0, String.valueOf(activeTransition));
        }
        final IBinder iBinder2 = activeTransition.mToken;
        for (int i2 = 0; i2 < this.mObservers.size(); i2++) {
            ((TransitionObserver) this.mObservers.get(i2)).onTransitionStarting(iBinder2);
        }
        TransitionInfo transitionInfo2 = activeTransition.mInfo;
        SurfaceControl.Transaction transaction = activeTransition.mStartT;
        int type = transitionInfo2.getType();
        for (int i3 = 0; i3 < transitionInfo2.getRootCount(); i3++) {
            transaction.show(transitionInfo2.getRoot(i3).getLeash());
        }
        int i4 = -1;
        int i5 = 2;
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            int size = transitionInfo2.getChanges().size();
            int i6 = size + 1;
            int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo2, 1);
            while (true) {
                if (m < 0) {
                    break;
                }
                TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo2.getChanges().get(m);
                if (change.getChangeLeash() != null && change.getChangeTransitMode() == 2) {
                    i4 = (size + i6) - m;
                    break;
                }
                m--;
            }
        }
        int size2 = transitionInfo2.getChanges().size();
        int i7 = size2 - 1;
        boolean z3 = false;
        while (i7 >= 0) {
            TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo2.getChanges().get(i7);
            SurfaceControl leash = change2.getLeash();
            if (!CoreRune.MW_SHELL_CHANGE_TRANSITION || change2.getChangeLeash() == null) {
                int i8 = i5;
                if ((!CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION || change2.getParent() == null || ((change2.getMode() != 6 && !TransitionUtil.isOpeningType(change2.getMode())) || !transitionInfo2.hasCustomDisplayChangeTransition())) && (!CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION || change2.getForceHidingTransit() == 0)) {
                    if (CoreRune.MW_EMBED_ACTIVITY && (type == 6 || type == 1007)) {
                        int mode = change2.getMode();
                        if ((mode != 3 && mode != 4 && !z3) || !change2.hasFlags(512)) {
                            if (change2.hasFlags(32) && mode == 6) {
                                z3 = z2;
                            }
                        }
                    }
                    if (!change2.shouldSkipSetupAnimHierarchy() && TransitionInfo.isIndependent(change2, transitionInfo2)) {
                        boolean z4 = change2.getParent() != null ? z2 : false;
                        TransitionInfo.Root root = transitionInfo2.getRoot(TransitionUtil.rootIndexFor(change2, transitionInfo2));
                        int windowingMode = change2.getConfiguration().windowConfiguration.getWindowingMode();
                        boolean z5 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
                        if (!z5 || (change2.getFlags() & 2) == 0) {
                            z = z4;
                        } else {
                            z = z4;
                            if (root.getConfiguration().windowConfiguration.isSplitScreen()) {
                                Log.d("ShellTransitions", "setupAnimHierarchy: skip to reparent wallpaper, rootLeash is split");
                            }
                        }
                        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
                            transitionInfo = transitionInfo2;
                            if (windowingMode == 5 && (change2.getFlags() & 33554432) != 0 && !root.isActivityRootLeash()) {
                                Log.d("ShellTransitions", "setupAnimHierarchy: skip to reparent " + change2 + ", reason=non_activity_root_leash");
                                i7--;
                                transitionInfo2 = transitionInfo;
                                z2 = true;
                                i5 = 2;
                            }
                        } else {
                            transitionInfo = transitionInfo2;
                        }
                        if (type != 1 || windowingMode != i8 || change2.isEnteringPinnedMode()) {
                            if (type == 1019) {
                                i = 1;
                                if (windowingMode == 1) {
                                }
                            } else {
                                i = 1;
                            }
                            if (z5 && type == i && (transitionInfo.getFlags() & 262144) != 0 && TransitionUtil.isHomeTask(change2)) {
                                Log.d("ShellTransitions", "setupAnimHierarchy: skip to reparent " + change2 + ", reason=finger_lock");
                            } else {
                                if (!z) {
                                    transaction.reparent(leash, root.getLeash());
                                    transaction.setPosition(leash, change2.getStartAbsBounds().left - root.getOffset().x, change2.getStartAbsBounds().top - root.getOffset().y);
                                }
                                transaction.setLayer(leash, calculateAnimLayer(change2, i7, size2, type));
                            }
                        }
                        i7--;
                        transitionInfo2 = transitionInfo;
                        z2 = true;
                        i5 = 2;
                    }
                }
            } else {
                int i9 = size2 + 1;
                int i10 = change2.getChangeTransitMode() == 6 ? i9 + size2 : (i9 + size2) - i7;
                if (i10 < i4 && change2.getChangeTransitMode() == z2) {
                    i10 += i4;
                }
                SurfaceControl changeLeash = change2.getChangeLeash();
                int rootIndexFor = TransitionUtil.rootIndexFor(change2, transitionInfo2);
                boolean z6 = (!ChangeTransitionProvider.isDisplayRotating(transitionInfo2) || (CoreRune.MW_SPLIT_SHELL_TRANSITION && change2.getTaskInfo() != null && change2.getTaskInfo().isSplitScreen())) ? z2 : false;
                if (z6) {
                    transaction.reparent(changeLeash, transitionInfo2.getRoot(rootIndexFor).getLeash());
                }
                transaction.setLayer(change2.getChangeLeash(), i10);
                Log.d("ChangeTransitionProvider", "assignChangeLeashLayer: z=" + i10 + ", leash=" + change2.getChangeLeash() + ", reparent=" + z6 + ", " + MultiWindowManager.changeTransitModeToString(change2.getChangeTransitMode()));
            }
            transitionInfo = transitionInfo2;
            i7--;
            transitionInfo2 = transitionInfo;
            z2 = true;
            i5 = 2;
        }
        TransitionHandler transitionHandler = activeTransition.mHandler;
        if (transitionHandler != null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 5057565356781286066L, 0, String.valueOf(transitionHandler));
            }
            final int i11 = 0;
            iBinder = iBinder2;
            if (activeTransition.mHandler.startAnimation(iBinder, activeTransition.mInfo, activeTransition.mStartT, activeTransition.mFinishT, new TransitionFinishCallback(this) { // from class: com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda7
                public final /* synthetic */ Transitions f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                    Transitions transitions = this.f$0;
                    int i12 = i11;
                    IBinder iBinder3 = iBinder2;
                    switch (i12) {
                        case 0:
                            boolean z7 = Transitions.DEBUG_START_TRANSITION;
                            transitions.onFinish(iBinder3, windowContainerTransaction);
                            break;
                        default:
                            boolean z8 = Transitions.DEBUG_START_TRANSITION;
                            transitions.onFinish(iBinder3, windowContainerTransaction);
                            break;
                    }
                }
            })) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 2542068187878801251L, 0, null);
                }
                this.mTransitionTracer.logDispatched(activeTransition.mInfo.getDebugId(), activeTransition.mHandler);
                if (Trace.isTagEnabled(32L)) {
                    Trace.instant(32L, activeTransition.mHandler.getClass().getSimpleName() + "#startAnimation animated " + transitTypeToString(activeTransition.mInfo.getType()));
                    return;
                }
                return;
            }
        } else {
            iBinder = iBinder2;
        }
        final int i12 = 1;
        activeTransition.mHandler = dispatchTransition(iBinder, activeTransition.mInfo, activeTransition.mStartT, activeTransition.mFinishT, new TransitionFinishCallback(this) { // from class: com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda7
            public final /* synthetic */ Transitions f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
            public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                Transitions transitions = this.f$0;
                int i122 = i12;
                IBinder iBinder3 = iBinder;
                switch (i122) {
                    case 0:
                        boolean z7 = Transitions.DEBUG_START_TRANSITION;
                        transitions.onFinish(iBinder3, windowContainerTransaction);
                        break;
                    default:
                        boolean z8 = Transitions.DEBUG_START_TRANSITION;
                        transitions.onFinish(iBinder3, windowContainerTransaction);
                        break;
                }
            }
        }, activeTransition.mHandler, null);
    }

    public final void processReadyQueue(Track track) {
        if (track.mReadyTransitions.isEmpty()) {
            if (track.mActiveTransition == null) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1071245984949193362L, 1, Long.valueOf(this.mTracks.indexOf(track)));
                }
                for (int i = 0; i < this.mTracks.size(); i++) {
                    Track track2 = (Track) this.mTracks.get(i);
                    if (track2.mActiveTransition != null || !track2.mReadyTransitions.isEmpty()) {
                        return;
                    }
                }
                if (!this.mReadyDuringSync.isEmpty()) {
                    while (!this.mReadyDuringSync.isEmpty() && dispatchReady((ActiveTransition) this.mReadyDuringSync.remove(0))) {
                    }
                    return;
                } else {
                    if (this.mPendingTransitions.isEmpty()) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 5665710760068723116L, 0, null);
                        }
                        this.mKnownTransitions.clear();
                        for (int i2 = 0; i2 < this.mRunWhenIdleQueue.size(); i2++) {
                            ((Runnable) this.mRunWhenIdleQueue.get(i2)).run();
                        }
                        this.mRunWhenIdleQueue.clear();
                        return;
                    }
                    return;
                }
            }
            return;
        }
        final ActiveTransition activeTransition = (ActiveTransition) track.mReadyTransitions.get(0);
        ActiveTransition activeTransition2 = track.mActiveTransition;
        if (activeTransition2 == null) {
            track.mReadyTransitions.remove(0);
            track.mActiveTransition = activeTransition;
            if (activeTransition.mAborted) {
                SurfaceControl.Transaction transaction = activeTransition.mStartT;
                if (transaction != null) {
                    transaction.apply();
                }
                onFinish(activeTransition.mToken, null);
                return;
            }
            boolean isTagEnabled = Trace.isTagEnabled(32L);
            if (isTagEnabled) {
                Trace.traceBegin(32L, "playTransition: " + transitTypeToString(activeTransition.mInfo.getType()));
            }
            playTransition(activeTransition);
            if (isTagEnabled) {
            }
            processReadyQueue(track);
            return;
        }
        final IBinder iBinder = activeTransition2.mToken;
        final IBinder iBinder2 = activeTransition.mToken;
        if (activeTransition.mAborted) {
            onMerged(iBinder, iBinder2);
            return;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 8857561493841984889L, 0, String.valueOf(activeTransition), String.valueOf(activeTransition2));
        }
        PerfettoTransitionTracer perfettoTransitionTracer = this.mTransitionTracer;
        int debugId = activeTransition.mInfo.getDebugId();
        int debugId2 = activeTransition2.mInfo.getDebugId();
        if (perfettoTransitionTracer.mActiveTraces.get() > 0) {
            Trace.traceBegin(32L, "logMergeRequested");
            try {
                perfettoTransitionTracer.mDataSource.trace(new PerfettoTransitionTracer$$ExternalSyntheticLambda4(debugId, debugId2, 0));
            } finally {
                Trace.traceEnd(32L);
            }
        }
        if (CoreRune.MW_SHELL_TRANSITION) {
            activeTransition2.mHandler.beforeMergeAnimation(activeTransition.mToken, activeTransition.mHandler);
        }
        activeTransition2.mHandler.mergeAnimation(activeTransition.mToken, activeTransition.mInfo, activeTransition.mStartT, activeTransition.mFinishT, activeTransition2.mToken, new TransitionFinishCallback() { // from class: com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda2
            @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
            public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                int indexOf;
                IBinder iBinder3 = iBinder;
                IBinder iBinder4 = iBinder2;
                boolean z = Transitions.DEBUG_START_TRANSITION;
                Transitions transitions = Transitions.this;
                if (!CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER || !activeTransition.mInfo.canTransferAnimation()) {
                    transitions.onMerged(iBinder3, iBinder4);
                    return;
                }
                Transitions.ActiveTransition activeTransition3 = (Transitions.ActiveTransition) transitions.mKnownTransitions.get(iBinder3);
                Transitions.ActiveTransition activeTransition4 = (Transitions.ActiveTransition) transitions.mKnownTransitions.get(iBinder4);
                if (activeTransition3 == null) {
                    Log.e("ShellTransitions", "onTransfer: non-existent playing transition: " + iBinder3);
                    return;
                }
                if (activeTransition4 == null) {
                    Log.e("ShellTransitions", "onTransfer: non-existent ready transition: " + iBinder4);
                    return;
                }
                if (activeTransition3.getTrack() != activeTransition4.getTrack()) {
                    throw new IllegalStateException("Can't merge across tracks: " + activeTransition4 + " into " + activeTransition3);
                }
                ArrayList arrayList = activeTransition3.mTransfer;
                if (arrayList != null && !arrayList.isEmpty()) {
                    throw new IllegalStateException("Can't transfer " + activeTransition3 + " into " + activeTransition4);
                }
                Transitions.Track track3 = (Transitions.Track) transitions.mTracks.get(activeTransition3.getTrack());
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -3110546006985067719L, 0, String.valueOf(activeTransition3), String.valueOf(activeTransition4));
                }
                if (track3.mReadyTransitions.isEmpty() || track3.mReadyTransitions.get(0) != activeTransition4) {
                    Log.e("ShellTransitions", "Transfer transition out-of-order? " + activeTransition4);
                    indexOf = track3.mReadyTransitions.indexOf(activeTransition4);
                    if (indexOf < 0) {
                        Log.e("ShellTransitions", "Transfer a transition that is no-longer queued? " + activeTransition4);
                        return;
                    }
                } else {
                    indexOf = 0;
                }
                track3.mReadyTransitions.remove(indexOf);
                if (activeTransition4.mTransfer == null) {
                    activeTransition4.mTransfer = new ArrayList();
                }
                activeTransition4.mTransfer.add(activeTransition3);
                activeTransition4.mHandler.transferAnimation(activeTransition4.mToken, activeTransition4.mInfo, activeTransition4.mStartT, windowContainerTransaction);
                Transitions.TransitionHandler transitionHandler = activeTransition3.mHandler;
                if (transitionHandler != null) {
                    transitionHandler.onTransitionConsumed(activeTransition3.mToken, false, null);
                }
                track3.mActiveTransition = activeTransition4;
                transitions.playTransition(activeTransition4);
                transitions.processReadyQueue(track3);
            }
        });
    }

    public final void registerObserver(TransitionObserver transitionObserver) {
        this.mObservers.add(transitionObserver);
    }

    public void replaceDefaultHandlerForTest(TransitionHandler transitionHandler) {
        this.mHandlers.set(0, transitionHandler);
    }

    public final void runOnIdle(Runnable runnable) {
        int i;
        if (this.mPendingTransitions.isEmpty() && this.mReadyDuringSync.isEmpty()) {
            while (i < this.mTracks.size()) {
                Track track = (Track) this.mTracks.get(i);
                i = (track.mActiveTransition == null && track.mReadyTransitions.isEmpty()) ? i + 1 : 0;
            }
            runnable.run();
            return;
        }
        this.mRunWhenIdleQueue.add(runnable);
    }

    public final IBinder startTransition(int i, WindowContainerTransaction windowContainerTransaction, TransitionHandler transitionHandler) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 4018922785617165231L, 0, String.valueOf(transitTypeToString(i)), String.valueOf(windowContainerTransaction), String.valueOf(transitionHandler));
        }
        if (DEBUG_START_TRANSITION) {
            Log.d("ShellTransitions", "startTransition: type=" + transitTypeToString(i) + " wct=" + windowContainerTransaction + " handler=" + transitionHandler.getClass().getName(), new Throwable());
        }
        ActiveTransition activeTransition = new ActiveTransition(this.mOrganizer.startNewTransition(i, windowContainerTransaction));
        activeTransition.mHandler = transitionHandler;
        this.mKnownTransitions.put(activeTransition.mToken, activeTransition);
        this.mPendingTransitions.add(activeTransition);
        if (CoreRune.MW_SHELL_TRANSITION_TIMEOUT) {
            activeTransition.mPendingTime = System.currentTimeMillis();
            activeTransition.mPendingCallStack = Debug.getCallers(1, 4);
        }
        if (CoreRune.FW_SHELL_TRANSITION_LOG) {
            Log.i("ShellTransitions", "startTransition done, active=" + activeTransition);
        }
        return activeTransition.mToken;
    }

    public Transitions(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, ShellTaskOrganizer shellTaskOrganizer, TransactionPool transactionPool, DisplayController displayController, DisplayInsetsController displayInsetsController, ShellExecutor shellExecutor, Handler handler, ShellExecutor shellExecutor2, Handler handler2, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, HomeTransitionObserver homeTransitionObserver, FocusTransitionObserver focusTransitionObserver) {
        ShellExecutor shellExecutor3;
        int i = 0;
        this.mImpl = new ShellTransitionImpl(this, i);
        this.mSleepHandler = new SleepHandler();
        this.mIsRegistered = false;
        ArrayList arrayList = new ArrayList();
        this.mHandlers = arrayList;
        this.mObservers = new ArrayList();
        this.mRunWhenIdleQueue = new ArrayList();
        this.mTransitionAnimationScaleSetting = 1.0f;
        this.mKnownTransitions = new ArrayMap();
        this.mPendingTransitions = new ArrayList();
        this.mReadyDuringSync = new ArrayList();
        this.mTracks = new ArrayList();
        this.mOrganizer = shellTaskOrganizer;
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        this.mAnimExecutor = shellExecutor2;
        this.mDisplayController = displayController;
        this.mPlayerImpl = new TransitionPlayerImpl(this, i);
        DefaultTransitionHandler defaultTransitionHandler = new DefaultTransitionHandler(context, shellInit, displayController, displayInsetsController, transactionPool, shellExecutor, handler, shellExecutor2, handler2, rootTaskDisplayAreaOrganizer, InteractionJankMonitor.getInstance());
        RemoteTransitionHandler remoteTransitionHandler = new RemoteTransitionHandler(shellExecutor);
        this.mRemoteTransitionHandler = remoteTransitionHandler;
        this.mShellCommandHandler = shellCommandHandler;
        this.mShellController = shellController;
        arrayList.add(defaultTransitionHandler);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -6160073112438359978L, 0, null);
        }
        arrayList.add(remoteTransitionHandler);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -2436748845961170270L, 0, null);
        }
        if (CoreRune.MW_SHELL_TRANSITION) {
            shellExecutor3 = shellExecutor2;
            MultiTaskingTransitionProvider multiTaskingTransitionProvider = new MultiTaskingTransitionProvider(defaultTransitionHandler.mTransitionAnimation, displayController, transactionPool, shellExecutor, shellExecutor3);
            this.mMultiTaskingTransitProvider = multiTaskingTransitionProvider;
            defaultTransitionHandler.mMultiTaskingTransitProvider = multiTaskingTransitionProvider;
            remoteTransitionHandler.mMultiTaskingTransitions = multiTaskingTransitionProvider;
            remoteTransitionHandler.mAnimExecutor = shellExecutor3;
        } else {
            shellExecutor3 = shellExecutor2;
        }
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            ChangeTransitionProvider changeTransitionProvider = new ChangeTransitionProvider(this, displayController, transactionPool, shellExecutor, shellExecutor3);
            this.mChangeTransitProvider = changeTransitionProvider;
            defaultTransitionHandler.mChangeTransitProvider = changeTransitionProvider;
        }
        if (CoreRune.FW_SHELL_TRANSITION_WITH_DIM) {
            defaultTransitionHandler.mDimTransitionProvider = new DimTransitionProvider();
        }
        shellInit.addInitCallback(new Transitions$$ExternalSyntheticLambda1(this, 0), this);
        this.mHomeTransitionObserver = homeTransitionObserver;
        this.mFocusTransitionObserver = focusTransitionObserver;
        this.mTransitionTracer = new PerfettoTransitionTracer();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TransitionObserver {
        default void onTransitionStarting(IBinder iBinder) {
        }

        default void onTransitionFinished(IBinder iBinder, boolean z) {
        }

        default void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
        }

        default void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        }
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final void printShellCommandHelp(PrintWriter printWriter, String str) {
    }
}
