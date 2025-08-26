package com.android.wm.shell.transition;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Debug;
import android.os.IBinder;
import android.os.Trace;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import android.util.Slog;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.display.data.repository.FocusedDisplayRepositoryImpl$focusedTask$1$listener$1;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final /* synthetic */ class Transitions$ShellTransitionImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ Transitions$ShellTransitionImpl$$ExternalSyntheticLambda0(Object obj, Object obj2, int i, Object obj3) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01d4 A[Catch: all -> 0x015a, TryCatch #0 {all -> 0x015a, blocks: (B:39:0x0142, B:41:0x014c, B:42:0x0153, B:46:0x015d, B:55:0x016c, B:57:0x0193, B:59:0x01d4, B:61:0x01e4, B:62:0x01ec), top: B:98:0x0142 }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        WindowContainerTransaction windowContainerTransaction;
        TransitionRequestInfo transitionRequestInfo;
        WindowContainerTransaction windowContainerTransaction2;
        WindowContainerTransaction windowContainerTransaction3;
        switch (this.$r8$classId) {
            case 0:
                Transitions.ShellTransitionImpl shellTransitionImpl = (Transitions.ShellTransitionImpl) this.f$0;
                TransitionFilter transitionFilter = (TransitionFilter) this.f$1;
                RemoteTransition remoteTransition = (RemoteTransition) this.f$2;
                RemoteTransitionHandler remoteTransitionHandler = Transitions.this.mRemoteTransitionHandler;
                remoteTransitionHandler.getClass();
                remoteTransitionHandler.handleDeath(remoteTransition.asBinder(), null);
                remoteTransitionHandler.mFilters.add(new Pair(transitionFilter, remoteTransition));
                return;
            case 1:
                Transitions.this.mFocusTransitionObserver.setLocalFocusTransitionListener((FocusedDisplayRepositoryImpl$focusedTask$1$listener$1) this.f$1, (Executor) this.f$2);
                return;
            default:
                Transitions.TransitionPlayerImpl transitionPlayerImpl = (Transitions.TransitionPlayerImpl) this.f$0;
                IBinder iBinder = (IBinder) this.f$1;
                TransitionRequestInfo transitionRequestInfo2 = (TransitionRequestInfo) this.f$2;
                Transitions transitions = Transitions.this;
                transitions.getClass();
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 319134872409225255L, 1, Long.valueOf(transitionRequestInfo2.getDebugId()), String.valueOf(iBinder), String.valueOf(transitionRequestInfo2));
                }
                if (transitions.mKnownTransitions.containsKey(iBinder)) {
                    throw new RuntimeException("Transition already started " + iBinder);
                }
                Transitions.ActiveTransition activeTransition = new Transitions.ActiveTransition(iBinder);
                transitions.mKnownTransitions.put(iBinder, activeTransition);
                if (transitionRequestInfo2.getType() == 12) {
                    transitions.mSleepHandler.handleRequest(iBinder, transitionRequestInfo2);
                    activeTransition.mHandler = transitions.mSleepHandler;
                    transitionRequestInfo = transitionRequestInfo2;
                    windowContainerTransaction = null;
                } else {
                    boolean zIsTagEnabled = Trace.isTagEnabled(32L);
                    if (zIsTagEnabled) {
                        Trace.traceBegin(32L, "dispatchRequest: " + Transitions.transitTypeToString(transitionRequestInfo2.getType()));
                    }
                    Pair pairDispatchRequest = transitions.dispatchRequest(iBinder, transitionRequestInfo2, null);
                    if (zIsTagEnabled) {
                        if (pairDispatchRequest != null) {
                            Trace.instant(32L, ((Transitions.TransitionHandler) pairDispatchRequest.first).getClass().getSimpleName() + "#handleRequest handled " + Transitions.transitTypeToString(transitionRequestInfo2.getType()));
                        }
                        Trace.traceEnd(32L);
                    }
                    if (pairDispatchRequest != null) {
                        activeTransition.mHandler = (Transitions.TransitionHandler) pairDispatchRequest.first;
                        windowContainerTransaction = (WindowContainerTransaction) pairDispatchRequest.second;
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 8651619095650909722L, 1, Long.valueOf(transitionRequestInfo2.getDebugId()), activeTransition.mHandler.getClass().getSimpleName());
                        }
                    } else {
                        windowContainerTransaction = null;
                    }
                    if (transitionRequestInfo2.getDisplayChange() != null) {
                        TransitionRequestInfo.DisplayChange displayChange = transitionRequestInfo2.getDisplayChange();
                        if (displayChange.getStartRotation() == displayChange.getEndRotation() && (displayChange.getStartAbsBounds() == null || displayChange.getStartAbsBounds().equals(displayChange.getEndAbsBounds()))) {
                            transitionRequestInfo = transitionRequestInfo2;
                        } else {
                            if (windowContainerTransaction == null) {
                                windowContainerTransaction = new WindowContainerTransaction();
                            }
                            WindowContainerTransaction windowContainerTransaction4 = windowContainerTransaction;
                            DisplayController displayController = transitions.mDisplayController;
                            int displayId = displayChange.getDisplayId();
                            displayChange.getStartAbsBounds();
                            Rect endAbsBounds = displayChange.getEndAbsBounds();
                            int startRotation = displayChange.getStartRotation();
                            int endRotation = displayChange.getEndRotation();
                            synchronized (displayController.mDisplays) {
                                try {
                                    DisplayController.DisplayRecord displayRecord = (DisplayController.DisplayRecord) displayController.mDisplays.get(displayId);
                                    if (displayRecord == null) {
                                        Slog.w("DisplayController", "Skipping Display rotate on non-added display.");
                                        transitionRequestInfo = transitionRequestInfo2;
                                        windowContainerTransaction2 = windowContainerTransaction4;
                                    } else {
                                        DisplayLayout displayLayout = displayRecord.mDisplayLayout;
                                        if (displayLayout == null) {
                                            transitionRequestInfo = transitionRequestInfo2;
                                            windowContainerTransaction2 = windowContainerTransaction4;
                                        } else if (endAbsBounds == null) {
                                            transitionRequestInfo = transitionRequestInfo2;
                                            windowContainerTransaction2 = windowContainerTransaction4;
                                            if (startRotation != endRotation) {
                                                displayRecord.mDisplayLayout.rotateTo(displayRecord.mContext.getResources(), endRotation);
                                            }
                                        } else {
                                            if (startRotation == endRotation) {
                                                if (endRotation != 1) {
                                                    if (endRotation == 3) {
                                                    }
                                                }
                                                Resources resources = displayRecord.mContext.getResources();
                                                transitionRequestInfo = transitionRequestInfo2;
                                                windowContainerTransaction2 = windowContainerTransaction4;
                                                Size size = new Size(endAbsBounds.height(), endAbsBounds.width());
                                                displayLayout.mWidth = size.getWidth();
                                                displayLayout.mHeight = size.getHeight();
                                                displayLayout.recalcInsets(resources);
                                                Slog.d("DisplayController", "onDisplayChangeRequested: rotate size(d #" + displayId + ")" + endAbsBounds);
                                                if (startRotation != endRotation) {
                                                }
                                            }
                                            Resources resources2 = displayRecord.mContext.getResources();
                                            Size size2 = new Size(endAbsBounds.width(), endAbsBounds.height());
                                            displayLayout.mWidth = size2.getWidth();
                                            displayLayout.mHeight = size2.getHeight();
                                            displayLayout.recalcInsets(resources2);
                                            transitionRequestInfo = transitionRequestInfo2;
                                            windowContainerTransaction2 = windowContainerTransaction4;
                                            if (startRotation != endRotation) {
                                            }
                                        }
                                        displayController.mChangeController.dispatchOnDisplayChange(displayId, startRotation, endRotation, null, windowContainerTransaction2);
                                    }
                                } finally {
                                }
                            }
                            windowContainerTransaction = windowContainerTransaction2;
                        }
                    }
                }
                if ((transitionRequestInfo.getType() == 8 || (transitionRequestInfo.getFlags() & 4096) != 0) && transitionRequestInfo.getTriggerTask() != null && transitionRequestInfo.getTriggerTask().getWindowingMode() == 5) {
                    int i = transitionRequestInfo.getTriggerTask().displayId;
                    DesktopStateImpl.Companion.getClass();
                    if (DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                        Log.d("ShellTransitions", "skip change dw freeform to fullscreen when occluding the keyguard");
                        windowContainerTransaction3 = null;
                    } else {
                        if (windowContainerTransaction == null) {
                            windowContainerTransaction = new WindowContainerTransaction();
                        }
                        windowContainerTransaction.setWindowingMode(transitionRequestInfo.getTriggerTask().token, 1);
                        windowContainerTransaction3 = null;
                        windowContainerTransaction.setBounds(transitionRequestInfo.getTriggerTask().token, (Rect) null);
                    }
                } else {
                    windowContainerTransaction3 = null;
                }
                transitions.mOrganizer.startTransition(iBinder, (windowContainerTransaction == null || !windowContainerTransaction.isEmpty()) ? windowContainerTransaction : windowContainerTransaction3);
                transitions.mPendingTransitions.add(0, activeTransition);
                if (CoreRune.MW_SHELL_TRANSITION_TIMEOUT) {
                    activeTransition.mPendingTime = System.currentTimeMillis();
                    activeTransition.mPendingCallStack = Debug.getCallers(1, 4);
                    return;
                }
                return;
        }
    }
}
