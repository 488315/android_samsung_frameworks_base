package com.android.p038wm.shell.transition;

import android.R;
import android.app.ActivityTaskManager;
import android.app.IApplicationThread;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseIntArray;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.window.ITransitionPlayer;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import android.window.WindowContainerTransactionCallback;
import android.window.WindowOrganizer;
import com.android.p038wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.common.ExternalInterfaceBinder;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.RemoteCallable;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.TransactionPool;
import com.android.p038wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.p038wm.shell.nano.Transition;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.splitscreen.StageCoordinator;
import com.android.p038wm.shell.sysui.ShellCommandHandler;
import com.android.p038wm.shell.sysui.ShellController;
import com.android.p038wm.shell.sysui.ShellInit;
import com.android.p038wm.shell.transition.DefaultTransitionHandler;
import com.android.p038wm.shell.transition.Transitions;
import com.android.p038wm.shell.transition.change.ChangeTransitionProvider;
import com.android.p038wm.shell.util.TransitionUtil;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardService$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Transitions implements RemoteCallable, ShellCommandHandler.ShellCommandActionHandler {
    public static final boolean ENABLE_SHELL_TRANSITIONS;
    public static final boolean SHELL_TRANSITIONS_ROTATION;
    public final ShellExecutor mAnimExecutor;
    public final ChangeTransitionProvider mChangeTransitProvider;
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final ArrayList mHandlers;
    public final ShellTransitionImpl mImpl;
    public boolean mIsRegistered;
    public final ShellExecutor mMainExecutor;
    public final MultiTaskingTransitionProvider mMultiTaskingTransitProvider;
    public final ArrayList mObservers;
    public final WindowOrganizer mOrganizer;
    public final ArrayList mPendingTransitions;
    public final TransitionPlayerImpl mPlayerImpl;
    public final ArrayList mReadyDuringSync;
    public StageCoordinator.RecentsTransitionCallback mRecentTransitionCallback;
    public final RemoteTransitionHandler mRemoteTransitionHandler;
    public final ArrayList mRunWhenIdleQueue;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public final SleepHandler mSleepHandler;
    public final Tracer mTracer;
    public final ArrayList mTracks;
    public float mTransitionAnimationScaleSetting;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ActiveTransition {
        public boolean mAborted;
        public SurfaceControl.Transaction mFinishT;
        public TransitionHandler mHandler;
        public TransitionInfo mInfo;
        public ArrayList mMerged;
        public long mPendingTime;
        public SurfaceControl.Transaction mStartT;
        public IBinder mToken;
        public ArrayList mTransfer;

        private ActiveTransition() {
        }

        public /* synthetic */ ActiveTransition(int i) {
            this();
        }

        public final int getTrack() {
            TransitionInfo transitionInfo = this.mInfo;
            if (transitionInfo != null) {
                return transitionInfo.getTrack();
            }
            return -1;
        }

        public final String toString() {
            TransitionInfo transitionInfo = this.mInfo;
            if (transitionInfo == null || transitionInfo.getDebugId() < 0) {
                return this.mToken.toString() + "@" + getTrack();
            }
            return "(#" + this.mInfo.getDebugId() + ")" + this.mToken + "@" + getTrack();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IShellTransitionsImpl extends IShellTransitions$Stub implements ExternalInterfaceBinder {
        public Transitions mTransitions;

        public IShellTransitionsImpl(Transitions transitions) {
            this.mTransitions = transitions;
        }

        @Override // com.android.p038wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mTransitions = null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver() {
            super(null);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            Transitions transitions = Transitions.this;
            boolean z2 = Transitions.ENABLE_SHELL_TRANSITIONS;
            Context context = transitions.mContext;
            transitions.mTransitionAnimationScaleSetting = WindowManager.fixScale(Settings.Global.getFloat(context.getContentResolver(), "transition_animation_scale", context.getResources().getFloat(R.dimen.chooser_preview_width)));
            ((HandlerExecutor) Transitions.this.mMainExecutor).execute(new Transitions$$ExternalSyntheticLambda1(this, 1));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShellTransitionImpl implements ShellTransitions {
        public /* synthetic */ ShellTransitionImpl(Transitions transitions, int i) {
            this(transitions);
        }

        private ShellTransitionImpl(Transitions transitions) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Track {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TransitionFinishCallback {
        void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TransitionObserver {
        void onTransitionFinished(IBinder iBinder);

        void onTransitionMerged(IBinder iBinder, IBinder iBinder2);

        void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2);

        void onTransitionStarting();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TransitionPlayerImpl extends ITransitionPlayer.Stub {
        public /* synthetic */ TransitionPlayerImpl(Transitions transitions, int i) {
            this();
        }

        public final void onTransitionReady(final IBinder iBinder, final TransitionInfo transitionInfo, final SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2) {
            ((HandlerExecutor) Transitions.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.transition.Transitions$TransitionPlayerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Transitions.TransitionPlayerImpl transitionPlayerImpl = Transitions.TransitionPlayerImpl.this;
                    Transitions.this.onTransitionReady(iBinder, transitionInfo, transaction, transaction2);
                }
            });
        }

        public final void requestStartTransition(final IBinder iBinder, final TransitionRequestInfo transitionRequestInfo) {
            ((HandlerExecutor) Transitions.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.transition.Transitions$TransitionPlayerImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    WindowContainerTransaction windowContainerTransaction;
                    Transitions.TransitionPlayerImpl transitionPlayerImpl = Transitions.TransitionPlayerImpl.this;
                    IBinder iBinder2 = iBinder;
                    TransitionRequestInfo transitionRequestInfo2 = transitionRequestInfo;
                    Transitions transitions = Transitions.this;
                    transitions.getClass();
                    int i = 0;
                    if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -2076257741, 0, "Transition requested: %s %s", String.valueOf(iBinder2), String.valueOf(transitionRequestInfo2));
                    }
                    if (transitions.isTransitionKnown(iBinder2)) {
                        throw new RuntimeException("Transition already started " + iBinder2);
                    }
                    Transitions.ActiveTransition activeTransition = new Transitions.ActiveTransition(i);
                    if (transitionRequestInfo2.getType() == 12) {
                        transitions.mSleepHandler.handleRequest(iBinder2, transitionRequestInfo2);
                        activeTransition.mHandler = transitions.mSleepHandler;
                        windowContainerTransaction = null;
                    } else {
                        int size = transitions.mHandlers.size() - 1;
                        windowContainerTransaction = null;
                        while (true) {
                            if (size < 0) {
                                break;
                            }
                            windowContainerTransaction = ((Transitions.TransitionHandler) transitions.mHandlers.get(size)).handleRequest(iBinder2, transitionRequestInfo2);
                            if (windowContainerTransaction != null) {
                                activeTransition.mHandler = (Transitions.TransitionHandler) transitions.mHandlers.get(size);
                                break;
                            }
                            size--;
                        }
                        if (transitionRequestInfo2.getDisplayChange() != null) {
                            TransitionRequestInfo.DisplayChange displayChange = transitionRequestInfo2.getDisplayChange();
                            if (displayChange.getEndRotation() != displayChange.getStartRotation()) {
                                if (windowContainerTransaction == null) {
                                    windowContainerTransaction = new WindowContainerTransaction();
                                }
                                DisplayController displayController = transitions.mDisplayController;
                                int displayId = displayChange.getDisplayId();
                                int startRotation = displayChange.getStartRotation();
                                int endRotation = displayChange.getEndRotation();
                                synchronized (displayController.mDisplays) {
                                    DisplayController.DisplayRecord displayRecord = (DisplayController.DisplayRecord) displayController.mDisplays.get(displayId);
                                    if (displayRecord == null) {
                                        Slog.w("DisplayController", "Skipping Display rotate on non-added display.");
                                    } else {
                                        DisplayLayout displayLayout = displayRecord.mDisplayLayout;
                                        if (displayLayout != null) {
                                            displayLayout.rotateTo(endRotation, displayRecord.mContext.getResources());
                                        }
                                        displayController.mChangeController.dispatchOnDisplayChange(displayId, startRotation, endRotation, null, windowContainerTransaction);
                                    }
                                }
                            }
                        }
                    }
                    if (transitionRequestInfo2.getType() == 8 && transitionRequestInfo2.getTriggerTask() != null && transitionRequestInfo2.getTriggerTask().getWindowingMode() == 5) {
                        if (windowContainerTransaction == null) {
                            windowContainerTransaction = new WindowContainerTransaction();
                        }
                        windowContainerTransaction.setWindowingMode(transitionRequestInfo2.getTriggerTask().token, 1);
                        windowContainerTransaction.setBounds(transitionRequestInfo2.getTriggerTask().token, (Rect) null);
                    }
                    transitions.mOrganizer.startTransition(iBinder2, (windowContainerTransaction == null || !windowContainerTransaction.isEmpty()) ? windowContainerTransaction : null);
                    activeTransition.mToken = iBinder2;
                    transitions.mPendingTransitions.add(0, activeTransition);
                    if (CoreRune.MW_SHELL_TRANSITION) {
                        activeTransition.mPendingTime = System.currentTimeMillis();
                    }
                }
            });
        }

        public final void transitionAborted(final IBinder iBinder) {
            ((HandlerExecutor) Transitions.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.transition.Transitions$TransitionPlayerImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Transitions.TransitionPlayerImpl transitionPlayerImpl = Transitions.TransitionPlayerImpl.this;
                    IBinder iBinder2 = iBinder;
                    Transitions transitions = Transitions.this;
                    boolean z = Transitions.ENABLE_SHELL_TRANSITIONS;
                    transitions.getClass();
                    if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                        ArrayList arrayList = transitions.mPendingTransitions;
                        int i = -1;
                        int size = arrayList.size() - 1;
                        while (true) {
                            if (size < 0) {
                                break;
                            }
                            if (((Transitions.ActiveTransition) arrayList.get(size)).mToken == iBinder2) {
                                i = size;
                                break;
                            }
                            size--;
                        }
                        if (i >= 0) {
                            arrayList.remove(i);
                            return;
                        }
                        Log.e("ShellTransitions", "Got transitionAborted for non-pending transition " + iBinder2 + ". expecting one of " + Arrays.toString(arrayList.stream().map(new Transitions$$ExternalSyntheticLambda0(1)).toArray()));
                    }
                }
            });
        }

        private TransitionPlayerImpl() {
        }
    }

    static {
        boolean z = SystemProperties.getBoolean("persist.wm.debug.shell_transit", true);
        ENABLE_SHELL_TRANSITIONS = z;
        SHELL_TRANSITIONS_ROTATION = z && SystemProperties.getBoolean("persist.wm.debug.shell_transit_rotate", false);
    }

    public Transitions(Context context, ShellInit shellInit, ShellController shellController, WindowOrganizer windowOrganizer, TransactionPool transactionPool, DisplayController displayController, ShellExecutor shellExecutor, Handler handler, ShellExecutor shellExecutor2) {
        this(context, shellInit, shellController, windowOrganizer, transactionPool, displayController, shellExecutor, handler, shellExecutor2, null, new RootTaskDisplayAreaOrganizer(shellExecutor, context));
    }

    public static boolean hasDuplicatedOpenTypeChanges(TransitionInfo transitionInfo) {
        if (TransitionUtil.isClosingType(transitionInfo.getType())) {
            return false;
        }
        SparseIntArray sparseIntArray = new SparseIntArray();
        for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m >= 0; m136m--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
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

    public static boolean isEmptyExceptZombie(ArrayList arrayList) {
        boolean z;
        Iterator it = arrayList.iterator();
        do {
            z = true;
            if (!it.hasNext()) {
                return true;
            }
            ActiveTransition activeTransition = (ActiveTransition) it.next();
            activeTransition.getClass();
            if (System.currentTimeMillis() - activeTransition.mPendingTime <= 10000) {
                z = false;
            }
        } while (z);
        return false;
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

    public final void addHandler(TransitionHandler transitionHandler) {
        ArrayList arrayList = this.mHandlers;
        if (arrayList.isEmpty()) {
            throw new RuntimeException("Unexpected handler added prior to initialization, please use ShellInit callbacks to ensure proper ordering");
        }
        arrayList.add(transitionHandler);
        transitionHandler.setAnimScaleSetting(this.mTransitionAnimationScaleSetting);
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1826225975, 0, "addHandler: %s", transitionHandler.getClass().getSimpleName());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0020, code lost:
    
        if (((r18.mInfo.getFlags() & com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_DEVICE_DOZING) != 0) != false) goto L9;
     */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02b0  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchReady(ActiveTransition activeTransition) {
        int i;
        boolean z;
        SurfaceControl surfaceControl;
        TransitionHandler transitionHandler;
        TransitionInfo transitionInfo = activeTransition.mInfo;
        int type = transitionInfo.getType();
        ArrayList arrayList = this.mTracks;
        int i2 = 1;
        int i3 = 0;
        if (type != 12) {
        }
        ArrayList arrayList2 = this.mReadyDuringSync;
        arrayList2.add(0, activeTransition);
        boolean z2 = false;
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            Track track = (Track) arrayList.get(i4);
            if (!(track.mActiveTransition == null && track.mReadyTransitions.isEmpty())) {
                if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -957941843, 1, "Start finish-for-sync track %d", Long.valueOf(i4));
                }
                finishForSync(activeTransition, i4, null);
                z2 = true;
            }
        }
        if (z2) {
            return false;
        }
        arrayList2.remove(activeTransition);
        int track2 = transitionInfo.getTrack();
        while (track2 >= arrayList.size()) {
            arrayList.add(new Track(i3));
        }
        Track track3 = (Track) arrayList.get(track2);
        track3.mReadyTransitions.add(activeTransition);
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER && (transitionHandler = activeTransition.mHandler) != null) {
            transitionHandler.transitionReady(activeTransition.mToken, transitionInfo);
        }
        int i5 = 0;
        while (true) {
            ArrayList arrayList3 = this.mObservers;
            if (i5 >= arrayList3.size()) {
                break;
            }
            ((TransitionObserver) arrayList3.get(i5)).onTransitionReady(activeTransition.mToken, transitionInfo, activeTransition.mStartT, activeTransition.mFinishT);
            i5++;
        }
        if (transitionInfo.getRootCount() == 0 && !KeyguardTransitionHandler.handles(transitionInfo)) {
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 444814824, 0, "No transition roots in %s so abort", String.valueOf(activeTransition));
            }
            onAbort(activeTransition);
            return true;
        }
        int size = transitionInfo.getChanges().size();
        boolean z3 = size > 0;
        boolean z4 = false;
        boolean z5 = false;
        for (int i6 = size - 1; i6 >= 0; i6--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i6);
            z4 |= change.getTaskInfo() != null;
            z5 |= change.hasFlags(8);
            if (!change.hasFlags(32768)) {
                z3 = false;
            }
        }
        if (z4 || !z5 || size != 2) {
            int i7 = 3;
            if ((transitionInfo.getType() != 4 && transitionInfo.getType() != 3) || !z3) {
                TransitionInfo transitionInfo2 = activeTransition.mInfo;
                SurfaceControl.Transaction transaction = activeTransition.mStartT;
                SurfaceControl.Transaction transaction2 = activeTransition.mFinishT;
                boolean isOpeningType = TransitionUtil.isOpeningType(transitionInfo2.getType());
                int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo2, 1);
                while (m136m >= 0) {
                    TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo2.getChanges().get(m136m);
                    if (!change2.hasFlags(65794)) {
                        SurfaceControl leash = change2.getLeash();
                        int mode = ((TransitionInfo.Change) transitionInfo2.getChanges().get(m136m)).getMode();
                        if (mode == i7 && (change2.getStartAbsBounds().height() != change2.getEndAbsBounds().height() || change2.getStartAbsBounds().width() != change2.getEndAbsBounds().width())) {
                            transaction.setWindowCrop(leash, change2.getEndAbsBounds().width(), change2.getEndAbsBounds().height());
                        }
                        if (((change2.getForceHidingTransit() != 0 || (change2.getTaskInfo() != null && change2.getTaskInfo().isForceHidden)) ? i2 : 0) != 0) {
                            SurfaceControl leash2 = change2.getLeash();
                            int forceHidingTransit = change2.getForceHidingTransit();
                            float f = (change2.getForceHidingTransit() == i2 && change2.getFreeformStashScale() == 1.0f) ? 1.0f : 0.0f;
                            transaction.setAlpha(leash2, f);
                            Log.d("MultiTaskingTransitionProvider", "applyForceHideAlpha: leash=" + leash2 + ", startAlpha=" + f + ", transit=" + MultiWindowManager.forceHidingTransitToString(forceHidingTransit));
                            i = i2;
                        } else {
                            i = 0;
                        }
                        if (TransitionInfo.isIndependent(change2, transitionInfo2)) {
                            if (mode != i2) {
                                i7 = 3;
                                if (mode != 3) {
                                    if (mode == 2 || mode == 4) {
                                        if (change2.hasFlags(16777216)) {
                                            if (!CoreRune.MW_SPLIT_SHELL_TRANSITION || transitionInfo2.getType() != 1007) {
                                                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && (transitionInfo2.getFlags() & 65536) != 0) {
                                                    transaction2.show(change2.getLeash());
                                                }
                                            }
                                            z = true;
                                            if (z) {
                                                transaction2.hide(leash);
                                            } else {
                                                Log.d("ShellTransitions", "setupStartState: skip to hide, " + change2);
                                            }
                                        }
                                        z = false;
                                        if (z) {
                                        }
                                    } else if (isOpeningType && mode == 6) {
                                        transaction.show(leash);
                                    }
                                }
                            } else {
                                i7 = 3;
                            }
                            transaction.show(leash);
                            transaction.setMatrix(leash, 1.0f, 0.0f, 0.0f, 1.0f);
                            if ((CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION && transitionInfo2.hasCustomDisplayChangeTransition()) || (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && TransitionUtil.hasDisplayRotationChange(transitionInfo2))) {
                                transaction.setAlpha(leash, 1.0f);
                                transaction2.show(leash);
                            } else {
                                if (isOpeningType && (change2.getFlags() & 8) == 0) {
                                    transaction.setAlpha(leash, 0.0f);
                                }
                                transaction2.show(leash);
                            }
                        } else {
                            if (mode == i2 || mode == 3 || mode == 6) {
                                transaction.show(leash);
                                if (change2.getFreeformStashScale() <= 0.0f || change2.getFreeformStashScale() >= 1.0f) {
                                    surfaceControl = leash;
                                    transaction.setMatrix(leash, 1.0f, 0.0f, 0.0f, 1.0f);
                                } else {
                                    surfaceControl = leash;
                                }
                                if (i == 0) {
                                    transaction.setAlpha(surfaceControl, 1.0f);
                                }
                                transaction.setPosition(surfaceControl, change2.getEndRelOffset().x, change2.getEndRelOffset().y);
                            }
                            i7 = 3;
                        }
                    }
                    m136m--;
                    i2 = 1;
                }
                if (track3.mReadyTransitions.size() > 1) {
                    return true;
                }
                processReadyQueue(track3);
                return true;
            }
        }
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 53183017, 0, "Non-visible anim so abort: %s", String.valueOf(activeTransition));
        }
        onAbort(activeTransition);
        return true;
    }

    public final Pair dispatchRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo, TransitionHandler transitionHandler) {
        WindowContainerTransaction handleRequest;
        ArrayList arrayList = this.mHandlers;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return null;
            }
            if (arrayList.get(size) != transitionHandler && (handleRequest = ((TransitionHandler) arrayList.get(size)).handleRequest(iBinder, transitionRequestInfo)) != null) {
                return new Pair((TransitionHandler) arrayList.get(size), handleRequest);
            }
        }
    }

    public final TransitionHandler dispatchTransition(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, TransitionFinishCallback transitionFinishCallback, TransitionHandler transitionHandler, TransitionHandler transitionHandler2) {
        ArrayList arrayList = this.mHandlers;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                throw new IllegalStateException("This shouldn't happen, maybe the default handler is broken.");
            }
            if (arrayList.get(size) != transitionHandler) {
                if (CoreRune.MW_PIP_SHELL_TRANSITION && arrayList.get(size) == transitionHandler2) {
                }
                if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1308483871, 0, " try handler %s", String.valueOf(arrayList.get(size)));
                }
                if (((TransitionHandler) arrayList.get(size)).startAnimation(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback)) {
                    if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1297259344, 0, " animated by %s", String.valueOf(arrayList.get(size)));
                    }
                    this.mTracer.logDispatched(transitionInfo.getDebugId(), (TransitionHandler) arrayList.get(size));
                    return (TransitionHandler) arrayList.get(size);
                }
            }
        }
    }

    public final void finishForSync(final ActiveTransition activeTransition, final int i, ActiveTransition activeTransition2) {
        if (!isTransitionKnown(activeTransition.mToken)) {
            Log.d("ShellTransitions", "finishForSleep: already played sync transition " + activeTransition);
            return;
        }
        ArrayList arrayList = this.mTracks;
        Track track = (Track) arrayList.get(i);
        if (activeTransition2 != null) {
            Track track2 = (Track) arrayList.get(activeTransition2.getTrack());
            if (track2 != track) {
                Log.e("ShellTransitions", "finishForSleep: mismatched Tracks between forceFinish and logic " + activeTransition2.getTrack() + " vs " + i);
            }
            if (track2.mActiveTransition == activeTransition2) {
                Log.e("ShellTransitions", "Forcing transition to finish due to sync timeout: " + activeTransition2);
                activeTransition2.mAborted = true;
                TransitionHandler transitionHandler = activeTransition2.mHandler;
                if (transitionHandler != null) {
                    transitionHandler.onTransitionConsumed(activeTransition2.mToken, true, null);
                }
                onFinish(activeTransition2, null, null);
            }
        }
        int i2 = 0;
        if (track.mActiveTransition == null && track.mReadyTransitions.isEmpty()) {
            return;
        }
        ArrayList arrayList2 = this.mReadyDuringSync;
        if (arrayList2.isEmpty()) {
            return;
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        TransitionInfo transitionInfo = new TransitionInfo(12, 0);
        while (track.mActiveTransition != null && !arrayList2.isEmpty()) {
            final ActiveTransition activeTransition3 = track.mActiveTransition;
            ActiveTransition activeTransition4 = (ActiveTransition) arrayList2.get(i2);
            if (((activeTransition4.mInfo.getFlags() & QuickStepContract.SYSUI_STATE_DEVICE_DOZING) != 0 ? 1 : i2) == 0) {
                Log.e("ShellTransitions", "Somehow blocked on a non-sync transition? " + activeTransition4);
            }
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 615101231, i2, " Attempt to merge sync %s into %s via a SLEEP proxy", String.valueOf(activeTransition4), String.valueOf(activeTransition3));
            }
            activeTransition3.mHandler.mergeAnimation(activeTransition4.mToken, transitionInfo, transaction, activeTransition3.mToken, new Transitions$$ExternalSyntheticLambda3());
            if (track.mActiveTransition == activeTransition3) {
                ((HandlerExecutor) this.mMainExecutor).executeDelayed(120L, new Runnable() { // from class: com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        Transitions.this.finishForSync(activeTransition, i, activeTransition3);
                    }
                });
                return;
            }
            i2 = 0;
        }
    }

    @Override // com.android.p038wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.p038wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    public final boolean isAnimating() {
        boolean z;
        if (!this.mReadyDuringSync.isEmpty()) {
            return true;
        }
        int i = 0;
        while (true) {
            ArrayList arrayList = this.mTracks;
            if (i >= arrayList.size()) {
                z = true;
                break;
            }
            Track track = (Track) arrayList.get(i);
            if (!(track.mActiveTransition == null && track.mReadyTransitions.isEmpty())) {
                z = false;
                break;
            }
            i++;
        }
        return !z;
    }

    public final boolean isTransitionKnown(IBinder iBinder) {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.mPendingTransitions;
            if (i >= arrayList.size()) {
                int i2 = 0;
                while (true) {
                    ArrayList arrayList2 = this.mReadyDuringSync;
                    if (i2 >= arrayList2.size()) {
                        int i3 = 0;
                        while (true) {
                            ArrayList arrayList3 = this.mTracks;
                            if (i3 >= arrayList3.size()) {
                                return false;
                            }
                            Track track = (Track) arrayList3.get(i3);
                            for (int i4 = 0; i4 < track.mReadyTransitions.size(); i4++) {
                                if (((ActiveTransition) track.mReadyTransitions.get(i4)).mToken == iBinder) {
                                    return true;
                                }
                            }
                            ActiveTransition activeTransition = track.mActiveTransition;
                            if (activeTransition != null) {
                                if (activeTransition.mToken == iBinder) {
                                    return true;
                                }
                                if (activeTransition.mMerged == null) {
                                    continue;
                                } else {
                                    for (int i5 = 0; i5 < activeTransition.mMerged.size(); i5++) {
                                        if (((ActiveTransition) activeTransition.mMerged.get(i5)).mToken == iBinder) {
                                            return true;
                                        }
                                    }
                                }
                            }
                            i3++;
                        }
                    } else {
                        if (((ActiveTransition) arrayList2.get(i2)).mToken == iBinder) {
                            return true;
                        }
                        i2++;
                    }
                }
            } else {
                if (((ActiveTransition) arrayList.get(i)).mToken == iBinder) {
                    return true;
                }
                i++;
            }
        }
    }

    public final void onAbort(ActiveTransition activeTransition) {
        Track track = (Track) this.mTracks.get(activeTransition.getTrack());
        activeTransition.mAborted = true;
        int debugId = activeTransition.mInfo.getDebugId();
        Tracer tracer = this.mTracer;
        tracer.getClass();
        Transition transition = new Transition();
        transition.f455id = debugId;
        transition.abortTimeNs = SystemClock.elapsedRealtimeNanos();
        tracer.mTraceBuffer.add(transition);
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

    public final void onFinish(ActiveTransition activeTransition, WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback) {
        SurfaceControl.Transaction transaction;
        ArrayList arrayList;
        Track track = (Track) this.mTracks.get(activeTransition.getTrack());
        ActiveTransition activeTransition2 = track.mActiveTransition;
        if (activeTransition2 != activeTransition) {
            if (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER || activeTransition2 == null || (arrayList = activeTransition2.mTransfer) == null || !arrayList.contains(activeTransition)) {
                Log.e("ShellTransitions", "Trying to finish a non-running transition. Either remote crashed or  a handler didn't properly deal with a merge. " + activeTransition, new RuntimeException());
                return;
            } else {
                Log.d("ShellTransitions", "Finishing is skipped due to transferred transit=" + activeTransition);
                return;
            }
        }
        track.mActiveTransition = null;
        int i = 0;
        while (true) {
            ArrayList arrayList2 = this.mObservers;
            if (i >= arrayList2.size()) {
                break;
            }
            ((TransitionObserver) arrayList2.get(i)).onTransitionFinished(activeTransition.mToken);
            i++;
        }
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 823755091, 3, "Transition animation finished (aborted=%b), notifying core %s", Boolean.valueOf(activeTransition.mAborted), String.valueOf(activeTransition));
        }
        SurfaceControl.Transaction transaction2 = activeTransition.mStartT;
        if (transaction2 != null) {
            transaction2.clear();
        }
        if (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER || activeTransition.mTransfer == null) {
            transaction = null;
        } else {
            transaction = null;
            for (int i2 = 0; i2 < activeTransition.mTransfer.size(); i2++) {
                ActiveTransition activeTransition3 = (ActiveTransition) activeTransition.mTransfer.get(i2);
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
                transaction.merge(activeTransition.mFinishT);
            }
        }
        if (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER || transaction == null) {
            transaction = activeTransition.mFinishT;
        }
        if (activeTransition.mMerged != null) {
            for (int i4 = 0; i4 < activeTransition.mMerged.size(); i4++) {
                ActiveTransition activeTransition5 = (ActiveTransition) activeTransition.mMerged.get(i4);
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
        boolean z = CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER;
        WindowOrganizer windowOrganizer = this.mOrganizer;
        if (z && activeTransition.mTransfer != null) {
            for (int i5 = 0; i5 < activeTransition.mTransfer.size(); i5++) {
                ActiveTransition activeTransition6 = (ActiveTransition) activeTransition.mTransfer.get(i5);
                windowOrganizer.finishTransition(activeTransition6.mToken, (WindowContainerTransaction) null, (WindowContainerTransactionCallback) null);
                TransitionInfo transitionInfo = activeTransition6.mInfo;
                if (transitionInfo != null) {
                    transitionInfo.releaseAnimSurfaces();
                }
                if (activeTransition6.mMerged != null) {
                    for (int i6 = 0; i6 < activeTransition6.mMerged.size(); i6++) {
                        ActiveTransition activeTransition7 = (ActiveTransition) activeTransition6.mMerged.get(i6);
                        windowOrganizer.finishTransition(activeTransition7.mToken, (WindowContainerTransaction) null, (WindowContainerTransactionCallback) null);
                        TransitionInfo transitionInfo2 = activeTransition7.mInfo;
                        if (transitionInfo2 != null) {
                            transitionInfo2.releaseAnimSurfaces();
                        }
                    }
                    activeTransition6.mMerged.clear();
                }
            }
            activeTransition.mTransfer.clear();
        }
        TransitionInfo transitionInfo3 = activeTransition.mInfo;
        if (transitionInfo3 != null) {
            transitionInfo3.releaseAnimSurfaces();
        }
        windowOrganizer.finishTransition(activeTransition.mToken, windowContainerTransaction, windowContainerTransactionCallback);
        if (activeTransition.mMerged != null) {
            for (int i7 = 0; i7 < activeTransition.mMerged.size(); i7++) {
                ActiveTransition activeTransition8 = (ActiveTransition) activeTransition.mMerged.get(i7);
                windowOrganizer.finishTransition(activeTransition8.mToken, (WindowContainerTransaction) null, (WindowContainerTransactionCallback) null);
                TransitionInfo transitionInfo4 = activeTransition8.mInfo;
                if (transitionInfo4 != null) {
                    transitionInfo4.releaseAnimSurfaces();
                }
            }
            activeTransition.mMerged.clear();
        }
        processReadyQueue(track);
    }

    public final void onMerged(ActiveTransition activeTransition, ActiveTransition activeTransition2) {
        int indexOf;
        if (activeTransition.getTrack() != activeTransition2.getTrack()) {
            throw new IllegalStateException("Can't merge across tracks: " + activeTransition2 + " into " + activeTransition);
        }
        Track track = (Track) this.mTracks.get(activeTransition.getTrack());
        int i = 0;
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -344473798, 0, "Transition was merged: %s into %s", String.valueOf(activeTransition2), String.valueOf(activeTransition));
        }
        boolean isEmpty = track.mReadyTransitions.isEmpty();
        ArrayList arrayList = track.mReadyTransitions;
        if (isEmpty || arrayList.get(0) != activeTransition2) {
            Log.e("ShellTransitions", "Merged transition out-of-order? " + activeTransition2);
            indexOf = arrayList.indexOf(activeTransition2);
            if (indexOf < 0) {
                Log.e("ShellTransitions", "Merged a transition that is no-longer queued? " + activeTransition2);
                return;
            }
        } else {
            indexOf = 0;
        }
        arrayList.remove(indexOf);
        if (activeTransition.mMerged == null) {
            activeTransition.mMerged = new ArrayList();
        }
        activeTransition.mMerged.add(activeTransition2);
        TransitionHandler transitionHandler = activeTransition2.mHandler;
        if (transitionHandler != null && !activeTransition2.mAborted) {
            transitionHandler.onTransitionConsumed(activeTransition2.mToken, false, activeTransition2.mFinishT);
        }
        while (true) {
            ArrayList arrayList2 = this.mObservers;
            if (i >= arrayList2.size()) {
                int debugId = activeTransition2.mInfo.getDebugId();
                int debugId2 = activeTransition.mInfo.getDebugId();
                Tracer tracer = this.mTracer;
                tracer.getClass();
                Transition transition = new Transition();
                transition.f455id = debugId;
                transition.mergeTimeNs = SystemClock.elapsedRealtimeNanos();
                transition.mergedInto = debugId2;
                tracer.mTraceBuffer.add(transition);
                processReadyQueue(track);
                return;
            }
            ((TransitionObserver) arrayList2.get(i)).onTransitionMerged(activeTransition2.mToken, activeTransition.mToken);
            i++;
        }
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final boolean onShellCommand(PrintWriter printWriter, String[] strArr) {
        String str = strArr[0];
        str.getClass();
        if (str.equals("tracing")) {
            this.mTracer.onShellCommand(printWriter, (String[]) Arrays.copyOfRange(strArr, 1, strArr.length));
            return true;
        }
        KeyboardUI$$ExternalSyntheticOutline0.m134m(new StringBuilder("Invalid command: "), strArr[0], printWriter);
        printShellCommandHelp(printWriter, "");
        return false;
    }

    public void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        transitionInfo.setUnreleasedWarningCallSiteForAllSurfaces("Transitions.onTransitionReady");
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1070270131, 0, "onTransitionReady %s: %s", String.valueOf(iBinder), String.valueOf(transitionInfo));
        }
        ArrayList arrayList = this.mPendingTransitions;
        int i = -1;
        int size = arrayList.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            if (((ActiveTransition) arrayList.get(size)).mToken == iBinder) {
                i = size;
                break;
            }
            size--;
        }
        if (i < 0) {
            throw new IllegalStateException("Got transitionReady for non-pending transition " + iBinder + ". expecting one of " + Arrays.toString(arrayList.stream().map(new Transitions$$ExternalSyntheticLambda0(0)).toArray()));
        }
        ActiveTransition activeTransition = (ActiveTransition) arrayList.remove(i);
        activeTransition.mInfo = transitionInfo;
        activeTransition.mStartT = transaction;
        activeTransition.mFinishT = transaction2;
        if (i > 0) {
            Log.i("ShellTransitions", "Transition might be ready out-of-order " + i + " for " + activeTransition + ". This is ok if it's on a different track.");
        }
        ArrayList arrayList2 = this.mReadyDuringSync;
        if (arrayList2.isEmpty()) {
            dispatchReady(activeTransition);
        } else {
            arrayList2.add(activeTransition);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:146:0x029f, code lost:
    
        if (r15.hasFlags(com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) == false) goto L156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x02a9, code lost:
    
        if (com.android.p038wm.shell.util.TransitionUtil.isHomeOrRecents(r15) != false) goto L156;
     */
    /* JADX WARN: Removed duplicated region for block: B:115:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0265  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0273  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void playTransition(ActiveTransition activeTransition) {
        final Transitions transitions;
        final int i;
        int i2;
        boolean z;
        int i3;
        char c;
        int i4;
        boolean z2;
        final ActiveTransition activeTransition2 = activeTransition;
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 599868787, 0, "Playing animation for %s", String.valueOf(activeTransition));
        }
        int i5 = 0;
        while (true) {
            ArrayList arrayList = this.mObservers;
            if (i5 >= arrayList.size()) {
                break;
            }
            ((TransitionObserver) arrayList.get(i5)).onTransitionStarting();
            i5++;
        }
        TransitionInfo transitionInfo = activeTransition2.mInfo;
        SurfaceControl.Transaction transaction = activeTransition2.mStartT;
        int type = transitionInfo.getType();
        boolean isOpeningType = TransitionUtil.isOpeningType(type);
        boolean isClosingType = TransitionUtil.isClosingType(type);
        for (int i6 = 0; i6 < transitionInfo.getRootCount(); i6++) {
            transaction.show(transitionInfo.getRoot(i6).getLeash());
        }
        int i7 = -1;
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            int i8 = ChangeTransitionProvider.$r8$clinit;
            int size = transitionInfo.getChanges().size();
            int i9 = size + 1;
            int size2 = transitionInfo.getChanges().size() - 1;
            while (true) {
                if (size2 < 0) {
                    break;
                }
                TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(size2);
                if (change.getChangeLeash() != null && change.getChangeTransitMode() == 2) {
                    i7 = (size + i9) - size2;
                    break;
                }
                size2--;
            }
        }
        int size3 = transitionInfo.getChanges().size();
        int i10 = size3 + 1;
        int i11 = size3 - 1;
        boolean z3 = false;
        while (i11 >= 0) {
            TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(i11);
            SurfaceControl leash = change2.getLeash();
            int mode = change2.getMode();
            if (!CoreRune.MW_SHELL_CHANGE_TRANSITION || change2.getChangeLeash() == null) {
                i2 = i7;
                if ((!CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION || change2.getParent() == null || change2.getMode() != 6 || !transitionInfo.hasCustomDisplayChangeTransition()) && ((!CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION || change2.getForceHidingTransit() == 0) && TransitionInfo.isIndependent(change2, transitionInfo))) {
                    boolean z4 = change2.getParent() != null;
                    int rootIndexFor = TransitionUtil.rootIndexFor(change2, transitionInfo);
                    TransitionInfo.Root root = transitionInfo.getRoot(rootIndexFor);
                    int windowingMode = change2.getConfiguration().windowConfiguration.getWindowingMode();
                    if (!CoreRune.MW_SPLIT_SHELL_TRANSITION || (change2.getFlags() & 2) == 0) {
                        z = isOpeningType;
                    } else {
                        z = isOpeningType;
                        if (root.getConfiguration().windowConfiguration.isSplitScreen()) {
                            Log.d("ShellTransitions", "setupAnimHierarchy: skip to reparent wallpaper, rootLeash is split");
                            c = 2;
                            i11--;
                            activeTransition2 = activeTransition;
                            i7 = i2;
                            isOpeningType = z;
                        }
                    }
                    if (!CoreRune.MW_FREEFORM_SHELL_TRANSITION || windowingMode != 5 || (change2.getFlags() & QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED) == 0 || root.isActivityRootLeash()) {
                        if (CoreRune.MW_EMBED_ACTIVITY && (type == 6 || type == 1007)) {
                            if ((mode != 3 && mode != 4 && !z3) || !change2.hasFlags(512)) {
                                if (change2.hasFlags(32) && mode == 6) {
                                    i3 = 1;
                                    z3 = true;
                                    if (type == i3 || windowingMode != 2 || change2.isEnteringPinnedMode()) {
                                        if (!z4) {
                                            transaction.reparent(leash, transitionInfo.getRoot(rootIndexFor).getLeash());
                                            transaction.setPosition(leash, change2.getStartAbsBounds().left - transitionInfo.getRoot(rootIndexFor).getOffset().x, change2.getStartAbsBounds().top - transitionInfo.getRoot(rootIndexFor).getOffset().y);
                                        }
                                        if ((change2.getFlags() & 2) == 0) {
                                            i4 = ((mode == 1 || mode == 3) ? (-i10) + size3 : -i10) - i11;
                                            c = 2;
                                        } else if (mode == 1 || mode == 3) {
                                            c = 2;
                                            if (!z) {
                                            }
                                            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                                            }
                                            i4 = (i10 + size3) - i11;
                                        } else {
                                            c = 2;
                                            if (mode != 2) {
                                            }
                                            i4 = i10 - i11;
                                        }
                                        transaction.setLayer(leash, i4);
                                        i11--;
                                        activeTransition2 = activeTransition;
                                        i7 = i2;
                                        isOpeningType = z;
                                    }
                                }
                            }
                        }
                        i3 = 1;
                        if (type == i3) {
                        }
                        if (!z4) {
                        }
                        if ((change2.getFlags() & 2) == 0) {
                        }
                        transaction.setLayer(leash, i4);
                        i11--;
                        activeTransition2 = activeTransition;
                        i7 = i2;
                        isOpeningType = z;
                    } else {
                        Log.d("ShellTransitions", "setupAnimHierarchy: skip to reparent " + change2 + ", reason=non_activity_root_leash");
                    }
                    c = 2;
                    i11--;
                    activeTransition2 = activeTransition;
                    i7 = i2;
                    isOpeningType = z;
                }
            } else {
                int i12 = change2.getChangeTransitMode() == 6 ? i10 + size3 : (i10 + size3) - i11;
                if (i12 < i7 && change2.getChangeTransitMode() == 1) {
                    i12 += i7;
                }
                int i13 = ChangeTransitionProvider.$r8$clinit;
                SurfaceControl changeLeash = change2.getChangeLeash();
                int rootIndexFor2 = TransitionUtil.rootIndexFor(change2, transitionInfo);
                if (!transitionInfo.hasCustomDisplayChangeTransition() && (!ChangeTransitionProvider.isDisplayRotating(transitionInfo) || (CoreRune.MW_SPLIT_SHELL_TRANSITION && change2.getTaskInfo() != null && change2.getTaskInfo().isSplitScreen()))) {
                    i2 = i7;
                    z2 = true;
                } else {
                    i2 = i7;
                    z2 = false;
                }
                if (z2) {
                    transaction.reparent(changeLeash, transitionInfo.getRoot(rootIndexFor2).getLeash());
                }
                transaction.setLayer(change2.getChangeLeash(), i12);
                Log.d("ChangeTransitionProvider", "assignChangeLeashLayer: z=" + i12 + ", leash=" + change2.getChangeLeash() + ", reparent=" + z2 + ", " + MultiWindowManager.changeTransitModeToString(change2.getChangeTransitMode()));
            }
            z = isOpeningType;
            c = 2;
            i11--;
            activeTransition2 = activeTransition;
            i7 = i2;
            isOpeningType = z;
        }
        TransitionHandler transitionHandler = activeTransition2.mHandler;
        if (transitionHandler != null) {
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                i = 0;
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 138343607, 0, " try firstHandler %s", String.valueOf(transitionHandler));
            } else {
                i = 0;
            }
            transitions = this;
            if (activeTransition2.mHandler.startAnimation(activeTransition2.mToken, activeTransition2.mInfo, activeTransition2.mStartT, activeTransition2.mFinishT, new TransitionFinishCallback(transitions) { // from class: com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda6
                public final /* synthetic */ Transitions f$0;

                {
                    this.f$0 = transitions;
                }

                @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback) {
                    int i14 = i;
                    Transitions.ActiveTransition activeTransition3 = activeTransition2;
                    Transitions transitions2 = this.f$0;
                    switch (i14) {
                        case 0:
                            transitions2.onFinish(activeTransition3, windowContainerTransaction, windowContainerTransactionCallback);
                            break;
                        default:
                            transitions2.onFinish(activeTransition3, windowContainerTransaction, windowContainerTransactionCallback);
                            break;
                    }
                }
            })) {
                if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 707170340, 0, " animated by firstHandler", null);
                }
                transitions.mTracer.logDispatched(activeTransition2.mInfo.getDebugId(), activeTransition2.mHandler);
                return;
            }
        } else {
            transitions = this;
        }
        final int i14 = 1;
        activeTransition2.mHandler = dispatchTransition(activeTransition2.mToken, activeTransition2.mInfo, activeTransition2.mStartT, activeTransition2.mFinishT, new TransitionFinishCallback(transitions) { // from class: com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda6
            public final /* synthetic */ Transitions f$0;

            {
                this.f$0 = transitions;
            }

            @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
            public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback) {
                int i142 = i14;
                Transitions.ActiveTransition activeTransition3 = activeTransition2;
                Transitions transitions2 = this.f$0;
                switch (i142) {
                    case 0:
                        transitions2.onFinish(activeTransition3, windowContainerTransaction, windowContainerTransactionCallback);
                        break;
                    default:
                        transitions2.onFinish(activeTransition3, windowContainerTransaction, windowContainerTransactionCallback);
                        break;
                }
            }
        }, activeTransition2.mHandler, null);
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final void printShellCommandHelp(PrintWriter printWriter, String str) {
        printWriter.println(str.concat("tracing"));
        this.mTracer.printShellCommandHelp(printWriter, str.concat("  "));
    }

    public final void processReadyQueue(Track track) {
        ArrayList arrayList = track.mReadyTransitions;
        int i = 0;
        if (!arrayList.isEmpty()) {
            final ActiveTransition activeTransition = (ActiveTransition) arrayList.get(0);
            final ActiveTransition activeTransition2 = track.mActiveTransition;
            if (activeTransition2 == null) {
                arrayList.remove(0);
                track.mActiveTransition = activeTransition;
                if (!activeTransition.mAborted) {
                    playTransition(activeTransition);
                    processReadyQueue(track);
                    return;
                } else {
                    SurfaceControl.Transaction transaction = activeTransition.mStartT;
                    if (transaction != null) {
                        transaction.apply();
                    }
                    onFinish(activeTransition, null, null);
                    return;
                }
            }
            if (activeTransition.mAborted) {
                onMerged(activeTransition2, activeTransition);
                return;
            }
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1145580304, 0, "Transition %s ready while %s is still animating. Notify the animating transition in case they can be merged", String.valueOf(activeTransition), String.valueOf(activeTransition2));
            }
            int debugId = activeTransition.mInfo.getDebugId();
            int debugId2 = activeTransition2.mInfo.getDebugId();
            Tracer tracer = this.mTracer;
            tracer.getClass();
            Transition transition = new Transition();
            transition.f455id = debugId;
            transition.mergeRequestTimeNs = SystemClock.elapsedRealtimeNanos();
            transition.mergedInto = debugId2;
            tracer.mTraceBuffer.add(transition);
            if (CoreRune.MW_SHELL_TRANSITION) {
                activeTransition2.mHandler.beforeMergeAnimation(activeTransition.mToken, activeTransition.mHandler);
            }
            activeTransition2.mHandler.mergeAnimation(activeTransition.mToken, activeTransition.mInfo, activeTransition.mStartT, activeTransition2.mToken, new TransitionFinishCallback() { // from class: com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda2
                @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback) {
                    int indexOf;
                    Transitions transitions = Transitions.this;
                    transitions.getClass();
                    boolean z = CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER;
                    Transitions.ActiveTransition activeTransition3 = activeTransition;
                    Transitions.ActiveTransition activeTransition4 = activeTransition2;
                    if (!z || !activeTransition3.mInfo.canTransferAnimation()) {
                        transitions.onMerged(activeTransition4, activeTransition3);
                        return;
                    }
                    if (activeTransition4.getTrack() != activeTransition3.getTrack()) {
                        throw new IllegalStateException("Can't merge across tracks: " + activeTransition3 + " into " + activeTransition4);
                    }
                    ArrayList arrayList2 = activeTransition4.mTransfer;
                    if (arrayList2 != null && !arrayList2.isEmpty()) {
                        throw new IllegalStateException("Can't transfer " + activeTransition4 + " into " + activeTransition3);
                    }
                    Transitions.Track track2 = (Transitions.Track) transitions.mTracks.get(activeTransition4.getTrack());
                    if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1793244704, 0, "Transition was transferred: %s into %s", String.valueOf(activeTransition4), String.valueOf(activeTransition3));
                    }
                    boolean isEmpty = track2.mReadyTransitions.isEmpty();
                    ArrayList arrayList3 = track2.mReadyTransitions;
                    if (isEmpty || arrayList3.get(0) != activeTransition3) {
                        Log.e("ShellTransitions", "Transfer transition out-of-order? " + activeTransition3);
                        indexOf = arrayList3.indexOf(activeTransition3);
                        if (indexOf < 0) {
                            Log.e("ShellTransitions", "Transfer a transition that is no-longer queued? " + activeTransition3);
                            return;
                        }
                    } else {
                        indexOf = 0;
                    }
                    arrayList3.remove(indexOf);
                    if (activeTransition3.mTransfer == null) {
                        activeTransition3.mTransfer = new ArrayList();
                    }
                    activeTransition3.mTransfer.add(activeTransition4);
                    activeTransition3.mHandler.transferAnimation(activeTransition3.mToken, activeTransition3.mInfo, activeTransition3.mStartT, windowContainerTransaction);
                    Transitions.TransitionHandler transitionHandler = activeTransition4.mHandler;
                    if (transitionHandler != null) {
                        transitionHandler.onTransitionConsumed(activeTransition4.mToken, false, null);
                    }
                    track2.mActiveTransition = activeTransition3;
                    transitions.playTransition(activeTransition3);
                    transitions.processReadyQueue(track2);
                }
            });
            return;
        }
        if (track.mActiveTransition != null) {
            return;
        }
        boolean z = ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled;
        ArrayList arrayList2 = this.mTracks;
        boolean z2 = true;
        if (z) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 359760983, 1, "Track %d became idle", Long.valueOf(arrayList2.indexOf(track)));
        }
        int i2 = 0;
        while (true) {
            if (i2 >= arrayList2.size()) {
                break;
            }
            Track track2 = (Track) arrayList2.get(i2);
            if (!(track2.mActiveTransition == null && track2.mReadyTransitions.isEmpty())) {
                z2 = false;
                break;
            }
            i2++;
        }
        if (!z2) {
            return;
        }
        ArrayList arrayList3 = this.mReadyDuringSync;
        if (!arrayList3.isEmpty()) {
            while (!arrayList3.isEmpty() && dispatchReady((ActiveTransition) arrayList3.remove(0))) {
            }
            return;
        }
        ArrayList arrayList4 = this.mPendingTransitions;
        if (!arrayList4.isEmpty() && (!CoreRune.MW_SHELL_TRANSITION || !isEmptyExceptZombie(arrayList4))) {
            return;
        }
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 490784151, 0, "All active transition animations finished", null);
        }
        while (true) {
            ArrayList arrayList5 = this.mRunWhenIdleQueue;
            if (i >= arrayList5.size()) {
                arrayList5.clear();
                return;
            } else {
                ((Runnable) arrayList5.get(i)).run();
                i++;
            }
        }
    }

    public void replaceDefaultHandlerForTest(TransitionHandler transitionHandler) {
        this.mHandlers.set(0, transitionHandler);
    }

    public final IBinder startTransition(int i, WindowContainerTransaction windowContainerTransaction, TransitionHandler transitionHandler) {
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 590143644, 1, "Directly starting a new transition type=%d wct=%s handler=%s", Long.valueOf(i), String.valueOf(windowContainerTransaction), String.valueOf(transitionHandler));
        }
        ActiveTransition activeTransition = new ActiveTransition(0);
        activeTransition.mHandler = transitionHandler;
        activeTransition.mToken = this.mOrganizer.startNewTransition(i, windowContainerTransaction);
        this.mPendingTransitions.add(activeTransition);
        if (CoreRune.MW_SHELL_TRANSITION) {
            activeTransition.mPendingTime = System.currentTimeMillis();
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
            Log.i("ShellTransitions", "startTransition done, active=" + activeTransition);
        }
        return activeTransition.mToken;
    }

    public Transitions(Context context, ShellInit shellInit, ShellController shellController, WindowOrganizer windowOrganizer, TransactionPool transactionPool, DisplayController displayController, ShellExecutor shellExecutor, Handler handler, ShellExecutor shellExecutor2, ShellCommandHandler shellCommandHandler, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer) {
        DefaultTransitionHandler defaultTransitionHandler;
        int i;
        int i2 = 0;
        this.mImpl = new ShellTransitionImpl(this, i2);
        this.mSleepHandler = new SleepHandler();
        this.mTracer = new Tracer();
        this.mIsRegistered = false;
        ArrayList arrayList = new ArrayList();
        this.mHandlers = arrayList;
        this.mObservers = new ArrayList();
        this.mRunWhenIdleQueue = new ArrayList();
        this.mTransitionAnimationScaleSetting = 1.0f;
        this.mPendingTransitions = new ArrayList();
        this.mReadyDuringSync = new ArrayList();
        this.mTracks = new ArrayList();
        this.mOrganizer = windowOrganizer;
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        this.mAnimExecutor = shellExecutor2;
        this.mDisplayController = displayController;
        this.mPlayerImpl = new TransitionPlayerImpl(this, i2);
        DefaultTransitionHandler defaultTransitionHandler2 = new DefaultTransitionHandler(context, shellInit, displayController, transactionPool, shellExecutor, handler, shellExecutor2, rootTaskDisplayAreaOrganizer);
        RemoteTransitionHandler remoteTransitionHandler = new RemoteTransitionHandler(shellExecutor);
        this.mRemoteTransitionHandler = remoteTransitionHandler;
        this.mShellController = shellController;
        arrayList.add(defaultTransitionHandler2);
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1438128520, 0, "addHandler: Default", null);
        }
        arrayList.add(remoteTransitionHandler);
        this.mShellCommandHandler = shellCommandHandler;
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 2096035537, 0, "addHandler: Remote", null);
        }
        if (CoreRune.MW_SHELL_TRANSITION) {
            defaultTransitionHandler = defaultTransitionHandler2;
            i = 0;
            MultiTaskingTransitionProvider multiTaskingTransitionProvider = new MultiTaskingTransitionProvider(defaultTransitionHandler2.mTransitionAnimation, displayController, transactionPool, shellExecutor, shellExecutor2);
            this.mMultiTaskingTransitProvider = multiTaskingTransitionProvider;
            defaultTransitionHandler.mMultiTaskingTransitProvider = multiTaskingTransitionProvider;
            remoteTransitionHandler.mMultiTaskingTransitions = multiTaskingTransitionProvider;
            remoteTransitionHandler.mAnimExecutor = shellExecutor2;
        } else {
            defaultTransitionHandler = defaultTransitionHandler2;
            i = 0;
        }
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            ChangeTransitionProvider changeTransitionProvider = new ChangeTransitionProvider(this, displayController, transactionPool, shellExecutor, shellExecutor2);
            this.mChangeTransitProvider = changeTransitionProvider;
            defaultTransitionHandler.mChangeTransitProvider = changeTransitionProvider;
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_WITH_DIM) {
            defaultTransitionHandler.mDimTransitionProvider = new DimTransitionProvider();
        }
        if (CoreRune.FW_INFORM_SCREEN_ROTATION_ANIMATION_STARTED_FOR_CAPTURED_BLUR) {
            defaultTransitionHandler.mCapturedBlurHelper = new DefaultTransitionHandler.CapturedBlurHelper(defaultTransitionHandler, i);
            defaultTransitionHandler.mMaxRotationAnimationDuration = 0L;
        }
        shellInit.addInitCallback(new Transitions$$ExternalSyntheticLambda1(this, i), this);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TransitionHandler {
        WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo);

        boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, TransitionFinishCallback transitionFinishCallback);

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

        default void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, TransitionFinishCallback transitionFinishCallback) {
        }
    }
}
