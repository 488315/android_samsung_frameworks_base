package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Debug;
import android.os.IBinder;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.window.DesktopModeFlags;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.protolog.ProtoLog;
import com.android.internal.util.LatencyTracker;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleTransitions;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.desktopmode.DesktopModeVisualIndicator;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.MoveToDesktopAnimator;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$$ExternalSyntheticLambda0;
import kotlin.collections.EmptyList;
import kotlin.collections.IndexedValue;
import kotlin.collections.IndexingIterable;
import kotlin.collections.IndexingIterator;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;

/* loaded from: classes3.dex */
public abstract class DragToDesktopTransitionHandler implements Transitions.TransitionHandler {
    public final Optional bubbleController;
    public final Context context;
    public final DesktopUserRepositories desktopUserRepositories;
    public DesktopTasksController$dragToDesktopStateListener$1 dragToDesktopStateListener;
    public final InteractionJankMonitor interactionJankMonitor;
    public final Intent launchHomeIntent;
    public final LinkedList logHistory;
    public DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener onTaskResizeAnimationListener;
    public final RectEvaluator rectEvaluator;
    public final SimpleDateFormat simpleDateFormat;
    public SplitScreenController splitScreenController;
    public final RootTaskDisplayAreaOrganizer taskDisplayAreaOrganizer;
    public final Supplier transactionSupplier;
    public TransitionState transitionState;
    public final Transitions transitions;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class CancelState {
        public static final /* synthetic */ CancelState[] $VALUES;
        public static final CancelState CANCEL_BUBBLE_LEFT;
        public static final CancelState CANCEL_BUBBLE_RIGHT;
        public static final CancelState CANCEL_SPLIT_LEFT;
        public static final CancelState CANCEL_SPLIT_RIGHT;
        public static final CancelState NO_CANCEL;
        public static final CancelState STANDARD_CANCEL;

        static {
            CancelState cancelState = new CancelState("NO_CANCEL", 0);
            NO_CANCEL = cancelState;
            CancelState cancelState2 = new CancelState("STANDARD_CANCEL", 1);
            STANDARD_CANCEL = cancelState2;
            CancelState cancelState3 = new CancelState("CANCEL_SPLIT_LEFT", 2);
            CANCEL_SPLIT_LEFT = cancelState3;
            CancelState cancelState4 = new CancelState("CANCEL_SPLIT_RIGHT", 3);
            CANCEL_SPLIT_RIGHT = cancelState4;
            CancelState cancelState5 = new CancelState("CANCEL_BUBBLE_LEFT", 4);
            CANCEL_BUBBLE_LEFT = cancelState5;
            CancelState cancelState6 = new CancelState("CANCEL_BUBBLE_RIGHT", 5);
            CANCEL_BUBBLE_RIGHT = cancelState6;
            CancelState[] cancelStateArr = {cancelState, cancelState2, cancelState3, cancelState4, cancelState5, cancelState6};
            $VALUES = cancelStateArr;
            EnumEntriesKt.enumEntries(cancelStateArr);
        }

        private CancelState(String str, int i) {
        }

        public static CancelState valueOf(String str) {
            return (CancelState) Enum.valueOf(CancelState.class, str);
        }

        public static CancelState[] values() {
            return (CancelState[]) $VALUES.clone();
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getDRAG_TO_DESKTOP_FINISH_ANIM_DURATION_MS$annotations() {
        }
    }

    public final class DragToDesktopLayers {
        public final int dragLayer;
        public final int topAppLayer;
        public final int topHomeLayer;
        public final int topWallpaperLayer;

        public DragToDesktopLayers(int i, int i2, int i3, int i4) {
            this.topAppLayer = i;
            this.topHomeLayer = i2;
            this.topWallpaperLayer = i3;
            this.dragLayer = i4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DragToDesktopLayers)) {
                return false;
            }
            DragToDesktopLayers dragToDesktopLayers = (DragToDesktopLayers) obj;
            return this.topAppLayer == dragToDesktopLayers.topAppLayer && this.topHomeLayer == dragToDesktopLayers.topHomeLayer && this.topWallpaperLayer == dragToDesktopLayers.topWallpaperLayer && this.dragLayer == dragToDesktopLayers.dragLayer;
        }

        public final int hashCode() {
            return Integer.hashCode(this.dragLayer) + ReorderTile$$ExternalSyntheticOutline0.m(this.topWallpaperLayer, ReorderTile$$ExternalSyntheticOutline0.m(this.topHomeLayer, Integer.hashCode(this.topAppLayer) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("DragToDesktopLayers(topAppLayer=");
            sb.append(this.topAppLayer);
            sb.append(", topHomeLayer=");
            sb.append(this.topHomeLayer);
            sb.append(", topWallpaperLayer=");
            sb.append(this.topWallpaperLayer);
            sb.append(", dragLayer=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.dragLayer, ")", sb);
        }
    }

    public abstract class TransitionState {
        public Boolean isTransitionReversed;

        public /* synthetic */ TransitionState(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract Animator getActiveCancelAnimation();

        public abstract CancelState getCancelState();

        public abstract IBinder getCancelTransitionToken();

        public abstract MoveToDesktopAnimator getDragAnimator();

        public abstract Runnable getDragCancelCallback();

        public abstract TransitionInfo.Change getDraggedTaskChange();

        public abstract int getDraggedTaskId();

        public abstract IBinder getEndTransitionToken();

        public abstract List getFreeformTaskChanges();

        public abstract TransitionInfo.Change getHomeChange();

        public abstract boolean getMergedEndTransition();

        public abstract boolean getStartAborted();

        public abstract boolean getStartInterrupted();

        public abstract Transitions.TransitionFinishCallback getStartTransitionFinishCb();

        public abstract SurfaceControl.Transaction getStartTransitionFinishTransaction();

        public abstract IBinder getStartTransitionToken();

        public abstract DragToDesktopLayers getSurfaceLayers();

        public abstract DesktopModeVisualIndicator getVisualIndicator();

        public abstract void setActiveCancelAnimation(Animator animator);

        public abstract void setCancelState(CancelState cancelState);

        public abstract void setCancelTransitionToken(IBinder iBinder);

        public abstract void setDraggedTaskChange(TransitionInfo.Change change);

        public abstract void setEndTransitionToken(IBinder iBinder);

        public abstract void setFreeformTaskChanges(List list);

        public abstract void setHomeChange(TransitionInfo.Change change);

        public abstract void setMergedEndTransition();

        public abstract void setStartAborted();

        public abstract void setStartInterrupted();

        public abstract void setStartTransitionFinishCb(Transitions.TransitionFinishCallback transitionFinishCallback);

        public abstract void setStartTransitionFinishTransaction(SurfaceControl.Transaction transaction);

        public abstract void setSurfaceLayers(DragToDesktopLayers dragToDesktopLayers);

        private TransitionState() {
        }

        public final class FromSplit extends TransitionState {
            public Animator activeCancelAnimation;
            public CancelState cancelState;
            public IBinder cancelTransitionToken;
            public final MoveToDesktopAnimator dragAnimator;
            public final Runnable dragCancelCallback;
            public TransitionInfo.Change draggedTaskChange;
            public final int draggedTaskId;
            public IBinder endTransitionToken;
            public List freeformTaskChanges;
            public TransitionInfo.Change homeChange;
            public boolean mergedEndTransition;
            public final int otherSplitTask;
            public TransitionInfo.Change splitRootChange;
            public boolean startAborted;
            public boolean startInterrupted;
            public Transitions.TransitionFinishCallback startTransitionFinishCb;
            public SurfaceControl.Transaction startTransitionFinishTransaction;
            public final IBinder startTransitionToken;
            public DragToDesktopLayers surfaceLayers;
            public final DesktopModeVisualIndicator visualIndicator;

            public FromSplit(int i, MoveToDesktopAnimator moveToDesktopAnimator, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback, SurfaceControl.Transaction transaction, IBinder iBinder2, TransitionInfo.Change change, TransitionInfo.Change change2, List list, DragToDesktopLayers dragToDesktopLayers, CancelState cancelState, boolean z, DesktopModeVisualIndicator desktopModeVisualIndicator, boolean z2, IBinder iBinder3, boolean z3, Animator animator, Runnable runnable, TransitionInfo.Change change3, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
                this(i, moveToDesktopAnimator, iBinder, (i3 & 8) != 0 ? null : transitionFinishCallback, (i3 & 16) != 0 ? null : transaction, (i3 & 32) != 0 ? null : iBinder2, (i3 & 64) != 0 ? null : change, (i3 & 128) != 0 ? null : change2, (i3 & 256) != 0 ? EmptyList.INSTANCE : list, (i3 & 512) != 0 ? null : dragToDesktopLayers, (i3 & 1024) != 0 ? CancelState.NO_CANCEL : cancelState, (i3 & 2048) != 0 ? false : z, desktopModeVisualIndicator, (i3 & 8192) != 0 ? false : z2, (i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? null : iBinder3, (32768 & i3) != 0 ? false : z3, (65536 & i3) != 0 ? null : animator, (131072 & i3) != 0 ? null : runnable, (i3 & 262144) != 0 ? null : change3, i2);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof FromSplit)) {
                    return false;
                }
                FromSplit fromSplit = (FromSplit) obj;
                return this.draggedTaskId == fromSplit.draggedTaskId && Intrinsics.areEqual(this.dragAnimator, fromSplit.dragAnimator) && Intrinsics.areEqual(this.startTransitionToken, fromSplit.startTransitionToken) && Intrinsics.areEqual(this.startTransitionFinishCb, fromSplit.startTransitionFinishCb) && Intrinsics.areEqual(this.startTransitionFinishTransaction, fromSplit.startTransitionFinishTransaction) && Intrinsics.areEqual(this.cancelTransitionToken, fromSplit.cancelTransitionToken) && Intrinsics.areEqual(this.homeChange, fromSplit.homeChange) && Intrinsics.areEqual(this.draggedTaskChange, fromSplit.draggedTaskChange) && Intrinsics.areEqual(this.freeformTaskChanges, fromSplit.freeformTaskChanges) && Intrinsics.areEqual(this.surfaceLayers, fromSplit.surfaceLayers) && this.cancelState == fromSplit.cancelState && this.startAborted == fromSplit.startAborted && Intrinsics.areEqual(this.visualIndicator, fromSplit.visualIndicator) && this.startInterrupted == fromSplit.startInterrupted && Intrinsics.areEqual(this.endTransitionToken, fromSplit.endTransitionToken) && this.mergedEndTransition == fromSplit.mergedEndTransition && Intrinsics.areEqual(this.activeCancelAnimation, fromSplit.activeCancelAnimation) && Intrinsics.areEqual(this.dragCancelCallback, fromSplit.dragCancelCallback) && Intrinsics.areEqual(this.splitRootChange, fromSplit.splitRootChange) && this.otherSplitTask == fromSplit.otherSplitTask;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final Animator getActiveCancelAnimation() {
                return this.activeCancelAnimation;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final CancelState getCancelState() {
                return this.cancelState;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final IBinder getCancelTransitionToken() {
                return this.cancelTransitionToken;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final MoveToDesktopAnimator getDragAnimator() {
                return this.dragAnimator;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final Runnable getDragCancelCallback() {
                return this.dragCancelCallback;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final TransitionInfo.Change getDraggedTaskChange() {
                return this.draggedTaskChange;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final int getDraggedTaskId() {
                return this.draggedTaskId;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final IBinder getEndTransitionToken() {
                return this.endTransitionToken;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final List getFreeformTaskChanges() {
                return this.freeformTaskChanges;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final TransitionInfo.Change getHomeChange() {
                return this.homeChange;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final boolean getMergedEndTransition() {
                return this.mergedEndTransition;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final boolean getStartAborted() {
                return this.startAborted;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final boolean getStartInterrupted() {
                return this.startInterrupted;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final Transitions.TransitionFinishCallback getStartTransitionFinishCb() {
                return this.startTransitionFinishCb;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final SurfaceControl.Transaction getStartTransitionFinishTransaction() {
                return this.startTransitionFinishTransaction;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final IBinder getStartTransitionToken() {
                return this.startTransitionToken;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final DragToDesktopLayers getSurfaceLayers() {
                return this.surfaceLayers;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final DesktopModeVisualIndicator getVisualIndicator() {
                return this.visualIndicator;
            }

            public final int hashCode() {
                int iHashCode = (this.startTransitionToken.hashCode() + ((this.dragAnimator.hashCode() + (Integer.hashCode(this.draggedTaskId) * 31)) * 31)) * 31;
                Transitions.TransitionFinishCallback transitionFinishCallback = this.startTransitionFinishCb;
                int iHashCode2 = (iHashCode + (transitionFinishCallback == null ? 0 : transitionFinishCallback.hashCode())) * 31;
                SurfaceControl.Transaction transaction = this.startTransitionFinishTransaction;
                int iHashCode3 = (iHashCode2 + (transaction == null ? 0 : transaction.hashCode())) * 31;
                IBinder iBinder = this.cancelTransitionToken;
                int iHashCode4 = (iHashCode3 + (iBinder == null ? 0 : iBinder.hashCode())) * 31;
                TransitionInfo.Change change = this.homeChange;
                int iHashCode5 = (iHashCode4 + (change == null ? 0 : change.hashCode())) * 31;
                TransitionInfo.Change change2 = this.draggedTaskChange;
                int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.freeformTaskChanges, (iHashCode5 + (change2 == null ? 0 : change2.hashCode())) * 31, 31);
                DragToDesktopLayers dragToDesktopLayers = this.surfaceLayers;
                int iM2 = TransitionData$$ExternalSyntheticOutline0.m((this.cancelState.hashCode() + ((iM + (dragToDesktopLayers == null ? 0 : dragToDesktopLayers.hashCode())) * 31)) * 31, 31, this.startAborted);
                DesktopModeVisualIndicator desktopModeVisualIndicator = this.visualIndicator;
                int iM3 = TransitionData$$ExternalSyntheticOutline0.m((iM2 + (desktopModeVisualIndicator == null ? 0 : desktopModeVisualIndicator.hashCode())) * 31, 31, this.startInterrupted);
                IBinder iBinder2 = this.endTransitionToken;
                int iM4 = TransitionData$$ExternalSyntheticOutline0.m((iM3 + (iBinder2 == null ? 0 : iBinder2.hashCode())) * 31, 31, this.mergedEndTransition);
                Animator animator = this.activeCancelAnimation;
                int iHashCode6 = (iM4 + (animator == null ? 0 : animator.hashCode())) * 31;
                Runnable runnable = this.dragCancelCallback;
                int iHashCode7 = (iHashCode6 + (runnable == null ? 0 : runnable.hashCode())) * 31;
                TransitionInfo.Change change3 = this.splitRootChange;
                return Integer.hashCode(this.otherSplitTask) + ((iHashCode7 + (change3 != null ? change3.hashCode() : 0)) * 31);
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setActiveCancelAnimation(Animator animator) {
                this.activeCancelAnimation = animator;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setCancelState(CancelState cancelState) {
                this.cancelState = cancelState;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setCancelTransitionToken(IBinder iBinder) {
                this.cancelTransitionToken = iBinder;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setDraggedTaskChange(TransitionInfo.Change change) {
                this.draggedTaskChange = change;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setEndTransitionToken(IBinder iBinder) {
                this.endTransitionToken = iBinder;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setFreeformTaskChanges(List list) {
                this.freeformTaskChanges = list;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setHomeChange(TransitionInfo.Change change) {
                this.homeChange = change;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setMergedEndTransition() {
                this.mergedEndTransition = true;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setStartAborted() {
                this.startAborted = true;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setStartInterrupted() {
                this.startInterrupted = true;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setStartTransitionFinishCb(Transitions.TransitionFinishCallback transitionFinishCallback) {
                this.startTransitionFinishCb = transitionFinishCallback;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setStartTransitionFinishTransaction(SurfaceControl.Transaction transaction) {
                this.startTransitionFinishTransaction = transaction;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setSurfaceLayers(DragToDesktopLayers dragToDesktopLayers) {
                this.surfaceLayers = dragToDesktopLayers;
            }

            public final String toString() {
                return "FromSplit(draggedTaskId=" + this.draggedTaskId + ", dragAnimator=" + this.dragAnimator + ", startTransitionToken=" + this.startTransitionToken + ", startTransitionFinishCb=" + this.startTransitionFinishCb + ", startTransitionFinishTransaction=" + this.startTransitionFinishTransaction + ", cancelTransitionToken=" + this.cancelTransitionToken + ", homeChange=" + this.homeChange + ", draggedTaskChange=" + this.draggedTaskChange + ", freeformTaskChanges=" + this.freeformTaskChanges + ", surfaceLayers=" + this.surfaceLayers + ", cancelState=" + this.cancelState + ", startAborted=" + this.startAborted + ", visualIndicator=" + this.visualIndicator + ", startInterrupted=" + this.startInterrupted + ", endTransitionToken=" + this.endTransitionToken + ", mergedEndTransition=" + this.mergedEndTransition + ", activeCancelAnimation=" + this.activeCancelAnimation + ", dragCancelCallback=" + this.dragCancelCallback + ", splitRootChange=" + this.splitRootChange + ", otherSplitTask=" + this.otherSplitTask + ")";
            }

            public FromSplit(int i, MoveToDesktopAnimator moveToDesktopAnimator, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback, SurfaceControl.Transaction transaction, IBinder iBinder2, TransitionInfo.Change change, TransitionInfo.Change change2, List<TransitionInfo.Change> list, DragToDesktopLayers dragToDesktopLayers, CancelState cancelState, boolean z, DesktopModeVisualIndicator desktopModeVisualIndicator, boolean z2, IBinder iBinder3, boolean z3, Animator animator, Runnable runnable, TransitionInfo.Change change3, int i2) {
                super(null);
                this.draggedTaskId = i;
                this.dragAnimator = moveToDesktopAnimator;
                this.startTransitionToken = iBinder;
                this.startTransitionFinishCb = transitionFinishCallback;
                this.startTransitionFinishTransaction = transaction;
                this.cancelTransitionToken = iBinder2;
                this.homeChange = change;
                this.draggedTaskChange = change2;
                this.freeformTaskChanges = list;
                this.surfaceLayers = dragToDesktopLayers;
                this.cancelState = cancelState;
                this.startAborted = z;
                this.visualIndicator = desktopModeVisualIndicator;
                this.startInterrupted = z2;
                this.endTransitionToken = iBinder3;
                this.mergedEndTransition = z3;
                this.activeCancelAnimation = animator;
                this.dragCancelCallback = runnable;
                this.splitRootChange = change3;
                this.otherSplitTask = i2;
            }
        }

        public final class FromFullscreen extends TransitionState {
            public Animator activeCancelAnimation;
            public CancelState cancelState;
            public IBinder cancelTransitionToken;
            public final MoveToDesktopAnimator dragAnimator;
            public final Runnable dragCancelCallback;
            public TransitionInfo.Change draggedTaskChange;
            public final int draggedTaskId;
            public IBinder endTransitionToken;
            public List freeformTaskChanges;
            public TransitionInfo.Change homeChange;
            public boolean mergedEndTransition;
            public final List otherRootChanges;
            public boolean startAborted;
            public boolean startInterrupted;
            public Transitions.TransitionFinishCallback startTransitionFinishCb;
            public SurfaceControl.Transaction startTransitionFinishTransaction;
            public final IBinder startTransitionToken;
            public DragToDesktopLayers surfaceLayers;
            public SurfaceControl transitionRootLeash;
            public final DesktopModeVisualIndicator visualIndicator;

            public FromFullscreen(int i, MoveToDesktopAnimator moveToDesktopAnimator, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback, SurfaceControl.Transaction transaction, IBinder iBinder2, TransitionInfo.Change change, TransitionInfo.Change change2, List list, DragToDesktopLayers dragToDesktopLayers, CancelState cancelState, boolean z, DesktopModeVisualIndicator desktopModeVisualIndicator, boolean z2, IBinder iBinder3, boolean z3, Animator animator, Runnable runnable, SurfaceControl surfaceControl, List list2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
                this(i, moveToDesktopAnimator, iBinder, (i2 & 8) != 0 ? null : transitionFinishCallback, (i2 & 16) != 0 ? null : transaction, (i2 & 32) != 0 ? null : iBinder2, (i2 & 64) != 0 ? null : change, (i2 & 128) != 0 ? null : change2, (i2 & 256) != 0 ? EmptyList.INSTANCE : list, (i2 & 512) != 0 ? null : dragToDesktopLayers, (i2 & 1024) != 0 ? CancelState.NO_CANCEL : cancelState, (i2 & 2048) != 0 ? false : z, desktopModeVisualIndicator, (i2 & 8192) != 0 ? false : z2, (i2 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? null : iBinder3, (32768 & i2) != 0 ? false : z3, (65536 & i2) != 0 ? null : animator, (131072 & i2) != 0 ? null : runnable, (262144 & i2) != 0 ? null : surfaceControl, (i2 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? new ArrayList() : list2);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof FromFullscreen)) {
                    return false;
                }
                FromFullscreen fromFullscreen = (FromFullscreen) obj;
                return this.draggedTaskId == fromFullscreen.draggedTaskId && Intrinsics.areEqual(this.dragAnimator, fromFullscreen.dragAnimator) && Intrinsics.areEqual(this.startTransitionToken, fromFullscreen.startTransitionToken) && Intrinsics.areEqual(this.startTransitionFinishCb, fromFullscreen.startTransitionFinishCb) && Intrinsics.areEqual(this.startTransitionFinishTransaction, fromFullscreen.startTransitionFinishTransaction) && Intrinsics.areEqual(this.cancelTransitionToken, fromFullscreen.cancelTransitionToken) && Intrinsics.areEqual(this.homeChange, fromFullscreen.homeChange) && Intrinsics.areEqual(this.draggedTaskChange, fromFullscreen.draggedTaskChange) && Intrinsics.areEqual(this.freeformTaskChanges, fromFullscreen.freeformTaskChanges) && Intrinsics.areEqual(this.surfaceLayers, fromFullscreen.surfaceLayers) && this.cancelState == fromFullscreen.cancelState && this.startAborted == fromFullscreen.startAborted && Intrinsics.areEqual(this.visualIndicator, fromFullscreen.visualIndicator) && this.startInterrupted == fromFullscreen.startInterrupted && Intrinsics.areEqual(this.endTransitionToken, fromFullscreen.endTransitionToken) && this.mergedEndTransition == fromFullscreen.mergedEndTransition && Intrinsics.areEqual(this.activeCancelAnimation, fromFullscreen.activeCancelAnimation) && Intrinsics.areEqual(this.dragCancelCallback, fromFullscreen.dragCancelCallback) && Intrinsics.areEqual(this.transitionRootLeash, fromFullscreen.transitionRootLeash) && Intrinsics.areEqual(this.otherRootChanges, fromFullscreen.otherRootChanges);
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final Animator getActiveCancelAnimation() {
                return this.activeCancelAnimation;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final CancelState getCancelState() {
                return this.cancelState;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final IBinder getCancelTransitionToken() {
                return this.cancelTransitionToken;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final MoveToDesktopAnimator getDragAnimator() {
                return this.dragAnimator;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final Runnable getDragCancelCallback() {
                return this.dragCancelCallback;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final TransitionInfo.Change getDraggedTaskChange() {
                return this.draggedTaskChange;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final int getDraggedTaskId() {
                return this.draggedTaskId;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final IBinder getEndTransitionToken() {
                return this.endTransitionToken;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final List getFreeformTaskChanges() {
                return this.freeformTaskChanges;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final TransitionInfo.Change getHomeChange() {
                return this.homeChange;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final boolean getMergedEndTransition() {
                return this.mergedEndTransition;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final boolean getStartAborted() {
                return this.startAborted;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final boolean getStartInterrupted() {
                return this.startInterrupted;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final Transitions.TransitionFinishCallback getStartTransitionFinishCb() {
                return this.startTransitionFinishCb;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final SurfaceControl.Transaction getStartTransitionFinishTransaction() {
                return this.startTransitionFinishTransaction;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final IBinder getStartTransitionToken() {
                return this.startTransitionToken;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final DragToDesktopLayers getSurfaceLayers() {
                return this.surfaceLayers;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final DesktopModeVisualIndicator getVisualIndicator() {
                return this.visualIndicator;
            }

            public final int hashCode() {
                int iHashCode = (this.startTransitionToken.hashCode() + ((this.dragAnimator.hashCode() + (Integer.hashCode(this.draggedTaskId) * 31)) * 31)) * 31;
                Transitions.TransitionFinishCallback transitionFinishCallback = this.startTransitionFinishCb;
                int iHashCode2 = (iHashCode + (transitionFinishCallback == null ? 0 : transitionFinishCallback.hashCode())) * 31;
                SurfaceControl.Transaction transaction = this.startTransitionFinishTransaction;
                int iHashCode3 = (iHashCode2 + (transaction == null ? 0 : transaction.hashCode())) * 31;
                IBinder iBinder = this.cancelTransitionToken;
                int iHashCode4 = (iHashCode3 + (iBinder == null ? 0 : iBinder.hashCode())) * 31;
                TransitionInfo.Change change = this.homeChange;
                int iHashCode5 = (iHashCode4 + (change == null ? 0 : change.hashCode())) * 31;
                TransitionInfo.Change change2 = this.draggedTaskChange;
                int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.freeformTaskChanges, (iHashCode5 + (change2 == null ? 0 : change2.hashCode())) * 31, 31);
                DragToDesktopLayers dragToDesktopLayers = this.surfaceLayers;
                int iM2 = TransitionData$$ExternalSyntheticOutline0.m((this.cancelState.hashCode() + ((iM + (dragToDesktopLayers == null ? 0 : dragToDesktopLayers.hashCode())) * 31)) * 31, 31, this.startAborted);
                DesktopModeVisualIndicator desktopModeVisualIndicator = this.visualIndicator;
                int iM3 = TransitionData$$ExternalSyntheticOutline0.m((iM2 + (desktopModeVisualIndicator == null ? 0 : desktopModeVisualIndicator.hashCode())) * 31, 31, this.startInterrupted);
                IBinder iBinder2 = this.endTransitionToken;
                int iM4 = TransitionData$$ExternalSyntheticOutline0.m((iM3 + (iBinder2 == null ? 0 : iBinder2.hashCode())) * 31, 31, this.mergedEndTransition);
                Animator animator = this.activeCancelAnimation;
                int iHashCode6 = (iM4 + (animator == null ? 0 : animator.hashCode())) * 31;
                Runnable runnable = this.dragCancelCallback;
                int iHashCode7 = (iHashCode6 + (runnable == null ? 0 : runnable.hashCode())) * 31;
                SurfaceControl surfaceControl = this.transitionRootLeash;
                return this.otherRootChanges.hashCode() + ((iHashCode7 + (surfaceControl != null ? surfaceControl.hashCode() : 0)) * 31);
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setActiveCancelAnimation(Animator animator) {
                this.activeCancelAnimation = animator;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setCancelState(CancelState cancelState) {
                this.cancelState = cancelState;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setCancelTransitionToken(IBinder iBinder) {
                this.cancelTransitionToken = iBinder;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setDraggedTaskChange(TransitionInfo.Change change) {
                this.draggedTaskChange = change;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setEndTransitionToken(IBinder iBinder) {
                this.endTransitionToken = iBinder;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setFreeformTaskChanges(List list) {
                this.freeformTaskChanges = list;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setHomeChange(TransitionInfo.Change change) {
                this.homeChange = change;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setMergedEndTransition() {
                this.mergedEndTransition = true;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setStartAborted() {
                this.startAborted = true;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setStartInterrupted() {
                this.startInterrupted = true;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setStartTransitionFinishCb(Transitions.TransitionFinishCallback transitionFinishCallback) {
                this.startTransitionFinishCb = transitionFinishCallback;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setStartTransitionFinishTransaction(SurfaceControl.Transaction transaction) {
                this.startTransitionFinishTransaction = transaction;
            }

            @Override // com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler.TransitionState
            public final void setSurfaceLayers(DragToDesktopLayers dragToDesktopLayers) {
                this.surfaceLayers = dragToDesktopLayers;
            }

            public final String toString() {
                return "FromFullscreen(draggedTaskId=" + this.draggedTaskId + ", dragAnimator=" + this.dragAnimator + ", startTransitionToken=" + this.startTransitionToken + ", startTransitionFinishCb=" + this.startTransitionFinishCb + ", startTransitionFinishTransaction=" + this.startTransitionFinishTransaction + ", cancelTransitionToken=" + this.cancelTransitionToken + ", homeChange=" + this.homeChange + ", draggedTaskChange=" + this.draggedTaskChange + ", freeformTaskChanges=" + this.freeformTaskChanges + ", surfaceLayers=" + this.surfaceLayers + ", cancelState=" + this.cancelState + ", startAborted=" + this.startAborted + ", visualIndicator=" + this.visualIndicator + ", startInterrupted=" + this.startInterrupted + ", endTransitionToken=" + this.endTransitionToken + ", mergedEndTransition=" + this.mergedEndTransition + ", activeCancelAnimation=" + this.activeCancelAnimation + ", dragCancelCallback=" + this.dragCancelCallback + ", transitionRootLeash=" + this.transitionRootLeash + ", otherRootChanges=" + this.otherRootChanges + ")";
            }

            public FromFullscreen(int i, MoveToDesktopAnimator moveToDesktopAnimator, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback, SurfaceControl.Transaction transaction, IBinder iBinder2, TransitionInfo.Change change, TransitionInfo.Change change2, List<TransitionInfo.Change> list, DragToDesktopLayers dragToDesktopLayers, CancelState cancelState, boolean z, DesktopModeVisualIndicator desktopModeVisualIndicator, boolean z2, IBinder iBinder3, boolean z3, Animator animator, Runnable runnable, SurfaceControl surfaceControl, List<TransitionInfo.Change> list2) {
                super(null);
                this.draggedTaskId = i;
                this.dragAnimator = moveToDesktopAnimator;
                this.startTransitionToken = iBinder;
                this.startTransitionFinishCb = transitionFinishCallback;
                this.startTransitionFinishTransaction = transaction;
                this.cancelTransitionToken = iBinder2;
                this.homeChange = change;
                this.draggedTaskChange = change2;
                this.freeformTaskChanges = list;
                this.surfaceLayers = dragToDesktopLayers;
                this.cancelState = cancelState;
                this.startAborted = z;
                this.visualIndicator = desktopModeVisualIndicator;
                this.startInterrupted = z2;
                this.endTransitionToken = iBinder3;
                this.mergedEndTransition = z3;
                this.activeCancelAnimation = animator;
                this.dragCancelCallback = runnable;
                this.transitionRootLeash = surfaceControl;
                this.otherRootChanges = list2;
            }
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ DragToDesktopTransitionHandler(Context context, Transitions transitions, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DesktopUserRepositories desktopUserRepositories, InteractionJankMonitor interactionJankMonitor, Optional optional, Supplier supplier, DesktopState desktopState, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, transitions, rootTaskDisplayAreaOrganizer, desktopUserRepositories, interactionJankMonitor, optional, supplier, desktopState);
    }

    public static ValueAnimator createInterruptAlphaAnimator(final SurfaceControl.Transaction transaction, final SurfaceControl surfaceControl, boolean z) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        transaction.show(surfaceControl);
        valueAnimatorOfFloat.setDuration(336L);
        valueAnimatorOfFloat.setInterpolator(Interpolators.LINEAR);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler$createInterruptAlphaAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                transaction.setAlpha(surfaceControl, ((Float) valueAnimator.getAnimatedValue()).floatValue()).setFrameTimeline(Choreographer.getInstance().getVsyncId()).apply();
            }
        });
        return valueAnimatorOfFloat;
    }

    public static void logV$4(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DragToDesktopTransitionHandler", objArr);
        ProtoLog.v(shellProtoLogGroup, strM, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public static void logW$2(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DragToDesktopTransitionHandler", objArr);
        ProtoLog.w(shellProtoLogGroup, strM, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public static void restoreWindowOrder(WindowContainerTransaction windowContainerTransaction, TransitionState transitionState) {
        WindowContainerToken container;
        WindowContainerToken container2;
        WindowContainerToken container3;
        if (transitionState instanceof TransitionState.FromFullscreen) {
            TransitionState.FromFullscreen fromFullscreen = (TransitionState.FromFullscreen) transitionState;
            List list = fromFullscreen.otherRootChanges;
            ArrayList arrayList = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                WindowContainerToken container4 = ((TransitionInfo.Change) it.next()).getContainer();
                if (container4 != null) {
                    arrayList.add(container4);
                }
            }
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                windowContainerTransaction.reorder((WindowContainerToken) obj, true);
            }
            TransitionInfo.Change change = fromFullscreen.draggedTaskChange;
            if (change == null || (container3 = change.getContainer()) == null) {
                throw new IllegalStateException("Dragged task should be non-null before cancelling");
            }
            windowContainerTransaction.reorder(container3, true);
        } else {
            if (!(transitionState instanceof TransitionState.FromSplit)) {
                throw new NoWhenBranchMatchedException();
            }
            TransitionInfo.Change change2 = ((TransitionState.FromSplit) transitionState).splitRootChange;
            if (change2 == null || (container = change2.getContainer()) == null) {
                throw new IllegalStateException("Split root should be non-null before cancelling");
            }
            windowContainerTransaction.reorder(container, true);
        }
        TransitionInfo.Change homeChange = transitionState.getHomeChange();
        if (homeChange == null || (container2 = homeChange.getContainer()) == null) {
            throw new IllegalStateException("Home task should be non-null before cancelling");
        }
        windowContainerTransaction.restoreTransientOrder(container2);
    }

    public void animateEndDragToDesktop(SurfaceControl.Transaction transaction, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        final TransitionState transitionStateRequireTransitionState = requireTransitionState();
        TransitionInfo.Change draggedTaskChange = transitionStateRequireTransitionState.getDraggedTaskChange();
        if (draggedTaskChange == null) {
            throw new IllegalStateException("Expected non-null change of dragged task");
        }
        final SurfaceControl leash = draggedTaskChange.getLeash();
        Rect startAbsBounds = draggedTaskChange.getStartAbsBounds();
        Rect endAbsBounds = draggedTaskChange.getEndAbsBounds();
        transitionStateRequireTransitionState.getDragAnimator().cancelAnimator();
        final float scale = transitionStateRequireTransitionState.getDragAnimator().getScale();
        PointF pointF = transitionStateRequireTransitionState.getDragAnimator().position;
        int iWidth = startAbsBounds.width();
        int iHeight = startAbsBounds.height();
        int i = (int) pointF.x;
        int i2 = (int) pointF.y;
        Rect rect = new Rect(i, i2, iWidth + i, iHeight + i2);
        DesktopTasksController$dragToDesktopStateListener$1 desktopTasksController$dragToDesktopStateListener$1 = this.dragToDesktopStateListener;
        if (desktopTasksController$dragToDesktopStateListener$1 != null) {
            desktopTasksController$dragToDesktopStateListener$1.removeVisualIndicator();
        }
        DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener desktopModeOnTaskResizeAnimationListener = this.onTaskResizeAnimationListener;
        if (desktopModeOnTaskResizeAnimationListener == null) {
            desktopModeOnTaskResizeAnimationListener = null;
        }
        desktopModeOnTaskResizeAnimationListener.onAnimationStart(transitionStateRequireTransitionState.getDraggedTaskId(), transaction, rect);
        final SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) this.transactionSupplier.get();
        ValueAnimator duration = ValueAnimator.ofObject(this.rectEvaluator, rect, endAbsBounds).setDuration(336L);
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler$animateEndDragToDesktop$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Rect rect2 = (Rect) valueAnimator.getAnimatedValue();
                float animatedFraction = valueAnimator.getAnimatedFraction();
                float f = scale;
                float fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(1, f, animatedFraction, f);
                SurfaceControl.Transaction transaction3 = transaction2;
                SurfaceControl surfaceControl = leash;
                transaction3.setScale(surfaceControl, fM$1, fM$1);
                transaction3.setPosition(surfaceControl, rect2.left, rect2.top);
                transaction3.setWindowCrop(surfaceControl, rect2.width(), rect2.height());
                DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener desktopModeOnTaskResizeAnimationListener2 = this.onTaskResizeAnimationListener;
                if (desktopModeOnTaskResizeAnimationListener2 == null) {
                    desktopModeOnTaskResizeAnimationListener2 = null;
                }
                desktopModeOnTaskResizeAnimationListener2.onBoundsChange(transitionStateRequireTransitionState.getDraggedTaskId(), transaction2, rect2);
            }
        });
        duration.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler$animateEndDragToDesktop$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener desktopModeOnTaskResizeAnimationListener2 = this.this$0.onTaskResizeAnimationListener;
                if (desktopModeOnTaskResizeAnimationListener2 == null) {
                    desktopModeOnTaskResizeAnimationListener2 = null;
                }
                desktopModeOnTaskResizeAnimationListener2.onAnimationEnd(transitionStateRequireTransitionState.getDraggedTaskId());
                transitionFinishCallback.onTransitionFinished(null);
                this.this$0.clearState();
                this.this$0.interactionJankMonitor.end(116);
            }
        });
        duration.start();
    }

    public abstract DragToDesktopLayers calculateStartDragToDesktopLayers(TransitionInfo transitionInfo);

    public final void cancelDragToDesktopTransition(CancelState cancelState) {
        CancelState cancelState2;
        ActivityManager.RunningTaskInfo taskInfo;
        ActivityManager.RunningTaskInfo taskInfo2;
        if (!getInProgress$1()) {
            logV$4("cancelDragToDesktop: not in progress, returning", new Object[0]);
            return;
        }
        TransitionState transitionStateRequireTransitionState = requireTransitionState();
        if (transitionStateRequireTransitionState.getStartAborted()) {
            logV$4("cancelDragToDesktop: start was aborted, clearing state", new Object[0]);
            clearState();
            return;
        }
        if (transitionStateRequireTransitionState.getStartInterrupted()) {
            logV$4("cancelDragToDesktop: start was interrupted, returning", new Object[0]);
            return;
        }
        transitionStateRequireTransitionState.setCancelState(cancelState);
        if (transitionStateRequireTransitionState.getDraggedTaskChange() != null && cancelState == CancelState.STANDARD_CANCEL) {
            transitionStateRequireTransitionState.setActiveCancelAnimation(startCancelAnimation());
            return;
        }
        if (transitionStateRequireTransitionState.getDraggedTaskChange() == null || !(cancelState == (cancelState2 = CancelState.CANCEL_SPLIT_LEFT) || cancelState == CancelState.CANCEL_SPLIT_RIGHT)) {
            if (transitionStateRequireTransitionState.getDraggedTaskChange() != null) {
                CancelState cancelState3 = CancelState.CANCEL_BUBBLE_LEFT;
                if (cancelState == cancelState3 || cancelState == CancelState.CANCEL_BUBBLE_RIGHT) {
                    if (this.bubbleController.isEmpty() || !(transitionStateRequireTransitionState instanceof TransitionState.FromFullscreen)) {
                        transitionStateRequireTransitionState.setActiveCancelAnimation(startCancelAnimation());
                        return;
                    }
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    restoreWindowOrder(windowContainerTransaction, transitionStateRequireTransitionState);
                    boolean z = cancelState == cancelState3;
                    TransitionState transitionStateRequireTransitionState2 = requireTransitionState();
                    TransitionInfo.Change draggedTaskChange = transitionStateRequireTransitionState2.getDraggedTaskChange();
                    if (draggedTaskChange == null || draggedTaskChange.getTaskInfo() == null) {
                        throw new IllegalStateException("Expected non-null taskInfo");
                    }
                    PointF pointF = new PointF(transitionStateRequireTransitionState2.getDragAnimator().position);
                    float scale = transitionStateRequireTransitionState2.getDragAnimator().getScale();
                    float f = transitionStateRequireTransitionState2.getDragAnimator().cornerRadius;
                    transitionStateRequireTransitionState2.getDragAnimator().cancelAnimator();
                    BubbleController bubbleController = (BubbleController) this.bubbleController.orElseThrow(DragToDesktopTransitionHandler$requestBubble$controller$1.INSTANCE);
                    new BubbleTransitions.DragData(z, scale, f, pointF, windowContainerTransaction);
                    bubbleController.getClass();
                    return;
                }
                return;
            }
            return;
        }
        int i = cancelState != cancelState2 ? 1 : 0;
        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
        restoreWindowOrder(windowContainerTransaction2, transitionStateRequireTransitionState);
        SurfaceControl.Transaction startTransitionFinishTransaction = transitionStateRequireTransitionState.getStartTransitionFinishTransaction();
        if (startTransitionFinishTransaction != null) {
            startTransitionFinishTransaction.apply();
        }
        Transitions.TransitionFinishCallback startTransitionFinishCb = transitionStateRequireTransitionState.getStartTransitionFinishCb();
        if (startTransitionFinishCb != null) {
            startTransitionFinishCb.onTransitionFinished(null);
        }
        TransitionState transitionStateRequireTransitionState3 = requireTransitionState();
        TransitionInfo.Change draggedTaskChange2 = transitionStateRequireTransitionState3.getDraggedTaskChange();
        if (draggedTaskChange2 == null || (taskInfo = draggedTaskChange2.getTaskInfo()) == null) {
            throw new IllegalStateException("Expected non-null taskInfo");
        }
        TransitionState transitionStateRequireTransitionState4 = requireTransitionState();
        TransitionInfo.Change draggedTaskChange3 = transitionStateRequireTransitionState4.getDraggedTaskChange();
        if (draggedTaskChange3 == null || (taskInfo2 = draggedTaskChange3.getTaskInfo()) == null) {
            throw new IllegalStateException("Expected non-null taskInfo");
        }
        Rect rect = new Rect(taskInfo2.configuration.windowConfiguration.getBounds());
        float scale2 = transitionStateRequireTransitionState4.getDragAnimator().getScale();
        float fWidth = rect.width() * scale2;
        float fHeight = rect.height() * scale2;
        PointF pointF2 = new PointF(transitionStateRequireTransitionState4.getDragAnimator().position);
        float f2 = pointF2.x;
        float f3 = pointF2.y;
        Rect rect2 = new Rect((int) f2, (int) f3, (int) (f2 + fWidth), (int) (f3 + fHeight));
        transitionStateRequireTransitionState3.getDragAnimator().cancelAnimator();
        requestSplitSelect(i, taskInfo, rect2, windowContainerTransaction2);
        clearState();
    }

    public final void clearState() {
        TransitionState transitionState = this.transitionState;
        if (transitionState != null) {
            recordLogHistory("[C] " + transitionState);
            logV$4("clearState: " + this.transitionState + ", c=" + Debug.getCallers(2), new Object[0]);
        }
        this.transitionState = null;
    }

    public final boolean getInProgress$1() {
        return this.transitionState != null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    public final boolean isHomeChange(TransitionInfo.Change change) {
        ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
        return (taskInfo == null || taskInfo.getActivityType() != 2 || (taskInfo.isTopActivityTransparent && taskInfo.numActivities == 1)) ? false : true;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        SurfaceControl leash;
        SurfaceControl leash2;
        final TransitionState transitionStateRequireTransitionState = requireTransitionState();
        if (transitionStateRequireTransitionState.getCancelState() == CancelState.CANCEL_SPLIT_LEFT || transitionStateRequireTransitionState.getCancelState() == CancelState.CANCEL_SPLIT_RIGHT) {
            logV$4("mergeAnimation: cancel through split", new Object[0]);
            clearState();
            return;
        }
        if (transitionInfo.getType() == 1024) {
            logV$4("mergeAnimation: convert-to-bubble", new Object[0]);
            Transitions.TransitionFinishCallback startTransitionFinishCb = transitionStateRequireTransitionState.getStartTransitionFinishCb();
            if (startTransitionFinishCb != null) {
                startTransitionFinishCb.onTransitionFinished(null);
            }
            clearState();
            return;
        }
        boolean z = transitionInfo.getType() == 1111 && Intrinsics.areEqual(iBinder, transitionStateRequireTransitionState.getCancelTransitionToken()) && Intrinsics.areEqual(iBinder2, transitionStateRequireTransitionState.getStartTransitionToken());
        boolean z2 = transitionInfo.getType() == 1110 && Intrinsics.areEqual(iBinder2, transitionStateRequireTransitionState.getStartTransitionToken());
        if (transitionInfo.getType() == 1109 && Intrinsics.areEqual(iBinder, transitionStateRequireTransitionState.getStartTransitionToken())) {
            logW$2("mergeAnimation: clear state, reason=reversed_order, " + transitionStateRequireTransitionState, new Object[0]);
            clearState();
            DesktopTasksController$dragToDesktopStateListener$1 desktopTasksController$dragToDesktopStateListener$1 = this.dragToDesktopStateListener;
            if (desktopTasksController$dragToDesktopStateListener$1 != null) {
                desktopTasksController$dragToDesktopStateListener$1.removeVisualIndicator();
                return;
            }
            return;
        }
        SurfaceControl.Transaction startTransitionFinishTransaction = transitionStateRequireTransitionState.getStartTransitionFinishTransaction();
        if (startTransitionFinishTransaction == null) {
            throw new IllegalStateException("Start transition expected to be waiting for merge but wasn't");
        }
        Transitions.TransitionFinishCallback startTransitionFinishCb2 = transitionStateRequireTransitionState.getStartTransitionFinishCb();
        if (startTransitionFinishCb2 == null) {
            throw new IllegalStateException("Start transition expected to be waiting for merge but wasn't");
        }
        if (z2) {
            logV$4("mergeAnimation: end-transition, target=" + iBinder2, new Object[0]);
            transitionStateRequireTransitionState.setMergedEndTransition();
            setupEndDragToDesktop(transitionInfo, transaction, startTransitionFinishTransaction);
            transitionFinishCallback.onTransitionFinished(null);
            LatencyTracker.getInstance(this.context).onActionEnd(30);
            animateEndDragToDesktop(transaction, startTransitionFinishCb2);
            return;
        }
        if (z) {
            logV$4("mergeAnimation: cancel-transition, target=" + iBinder2, new Object[0]);
            LatencyTracker.getInstance(this.context).onActionCancel(30);
            for (TransitionInfo.Change change : transitionInfo.getChanges()) {
                transaction.show(change.getLeash());
                startTransitionFinishTransaction.show(change.getLeash());
            }
            transaction.apply();
            transitionFinishCallback.onTransitionFinished(null);
            startTransitionFinishCb2.onTransitionFinished(null);
            clearState();
            return;
        }
        logW$2("unhandled merge transition: transitionInfo=" + transitionInfo, new Object[0]);
        if (Intrinsics.areEqual(iBinder2, transitionStateRequireTransitionState.getStartTransitionToken()) && !transitionStateRequireTransitionState.getMergedEndTransition() && DesktopModeFlags.ENABLE_DRAG_TO_DESKTOP_INCOMING_TRANSITIONS_BUGFIX.isTrue()) {
            if (transitionStateRequireTransitionState.getCancelTransitionToken() == null && transitionStateRequireTransitionState.getEndTransitionToken() == null) {
                logV$4("interruptStartTransition, bookend not requested -> animate to Home", new Object[0]);
                transitionStateRequireTransitionState.getDragAnimator().cancelAnimator();
                Runnable dragCancelCallback = transitionStateRequireTransitionState.getDragCancelCallback();
                if (dragCancelCallback != null) {
                    dragCancelCallback.run();
                }
                SurfaceControl.Transaction transaction3 = (SurfaceControl.Transaction) this.transactionSupplier.get();
                final Runnable runnable = new Runnable() { // from class: com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler$interruptStartTransition$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Transitions.TransitionFinishCallback startTransitionFinishCb3 = transitionStateRequireTransitionState.getStartTransitionFinishCb();
                        if (startTransitionFinishCb3 != null) {
                            startTransitionFinishCb3.onTransitionFinished(null);
                        }
                        this.clearState();
                    }
                };
                TransitionInfo.Change homeChange = transitionStateRequireTransitionState.getHomeChange();
                if (homeChange == null || (leash = homeChange.getLeash()) == null) {
                    throw new IllegalStateException("Expected home leash to be non-null");
                }
                TransitionInfo.Change draggedTaskChange = transitionStateRequireTransitionState.getDraggedTaskChange();
                if (draggedTaskChange == null || (leash2 = draggedTaskChange.getLeash()) == null) {
                    throw new IllegalStateException("Expected dragged leash to be non-null");
                }
                ValueAnimator valueAnimatorCreateInterruptAlphaAnimator = createInterruptAlphaAnimator(transaction3, leash, true);
                ValueAnimator valueAnimatorCreateInterruptAlphaAnimator2 = createInterruptAlphaAnimator(transaction3, leash2, false);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(valueAnimatorCreateInterruptAlphaAnimator, valueAnimatorCreateInterruptAlphaAnimator2);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler$createInterruptToHomeAnimator$1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        runnable.run();
                    }
                });
                animatorSet.start();
            } else {
                logV$4("interruptStartTransition, bookend requested -> finish start transition", new Object[0]);
                Transitions.TransitionFinishCallback startTransitionFinishCb3 = transitionStateRequireTransitionState.getStartTransitionFinishCb();
                if (startTransitionFinishCb3 != null) {
                    startTransitionFinishCb3.onTransitionFinished(null);
                }
                transitionStateRequireTransitionState.getDragAnimator().cancelAnimator();
            }
            Animator activeCancelAnimation = transitionStateRequireTransitionState.getActiveCancelAnimation();
            if (activeCancelAnimation != null) {
                activeCancelAnimation.removeAllListeners();
            }
            Animator activeCancelAnimation2 = transitionStateRequireTransitionState.getActiveCancelAnimation();
            if (activeCancelAnimation2 != null) {
                activeCancelAnimation2.cancel();
            }
            transitionStateRequireTransitionState.setActiveCancelAnimation(null);
            transitionStateRequireTransitionState.setStartInterrupted();
            DesktopTasksController$dragToDesktopStateListener$1 desktopTasksController$dragToDesktopStateListener$12 = this.dragToDesktopStateListener;
            if (desktopTasksController$dragToDesktopStateListener$12 != null) {
                desktopTasksController$dragToDesktopStateListener$12.removeVisualIndicator();
            }
            this.interactionJankMonitor.cancel(107);
            this.interactionJankMonitor.cancel(116);
            LatencyTracker.getInstance(this.context).onActionCancel(30);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        SurfaceControl leash;
        SurfaceControl.Transaction startTransitionFinishTransaction;
        TransitionState transitionState = this.transitionState;
        if (transitionState != null && z) {
            if (Intrinsics.areEqual(transitionState.getStartTransitionToken(), iBinder)) {
                logV$4("onTransitionConsumed() start transition aborted", new Object[0]);
                transitionState.setStartAborted();
                this.interactionJankMonitor.cancel(107);
            } else {
                if (!Intrinsics.areEqual(transitionState.getCancelTransitionToken(), iBinder)) {
                    this.interactionJankMonitor.cancel(116);
                    return;
                }
                TransitionInfo.Change draggedTaskChange = transitionState.getDraggedTaskChange();
                if (draggedTaskChange != null && (leash = draggedTaskChange.getLeash()) != null && (startTransitionFinishTransaction = transitionState.getStartTransitionFinishTransaction()) != null) {
                    startTransitionFinishTransaction.show(leash);
                }
                Transitions.TransitionFinishCallback startTransitionFinishCb = transitionState.getStartTransitionFinishCb();
                if (startTransitionFinishCb != null) {
                    startTransitionFinishCb.onTransitionFinished(null);
                }
                clearState();
            }
        }
    }

    public final void recordLogHistory(String str) {
        if (this.logHistory.size() == 6) {
            this.logHistory.removeFirst();
        }
        this.logHistory.add("(" + this.simpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + ") " + str);
    }

    public final void requestSplitSelect(int i, ActivityManager.RunningTaskInfo runningTaskInfo, Rect rect, WindowContainerTransaction windowContainerTransaction) {
        if (runningTaskInfo.getWindowingMode() == 6) {
            SplitScreenController splitScreenController = this.splitScreenController;
            SplitScreenController splitScreenController2 = splitScreenController == null ? null : splitScreenController;
            if (splitScreenController == null) {
                splitScreenController = null;
            }
            splitScreenController2.prepareExitSplitScreen(splitScreenController.getStageOfTask(runningTaskInfo.taskId), 12, windowContainerTransaction);
            SplitScreenController splitScreenController3 = this.splitScreenController;
            if (splitScreenController3 == null) {
                splitScreenController3 = null;
            }
            splitScreenController3.getTransitionHandler().setSplitsVisible(false);
        }
        windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 6);
        windowContainerTransaction.setDensityDpi(runningTaskInfo.token, this.context.getResources().getDisplayMetrics().densityDpi);
        SplitScreenController splitScreenController4 = this.splitScreenController;
        (splitScreenController4 != null ? splitScreenController4 : null).requestEnterSplitSelect(i, runningTaskInfo, rect, windowContainerTransaction);
    }

    public final TransitionState requireTransitionState() {
        TransitionState transitionState = this.transitionState;
        if (transitionState != null) {
            return transitionState;
        }
        throw new IllegalStateException("Expected non-null transition state");
    }

    public void setupEndDragToDesktop(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        SurfaceControl leash;
        SurfaceControl surfaceControl;
        ActivityManager.RunningTaskInfo taskInfo;
        TransitionState transitionStateRequireTransitionState = requireTransitionState();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Object obj : transitionInfo.getChanges()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            TransitionInfo.Change change = (TransitionInfo.Change) obj;
            if ((transitionStateRequireTransitionState instanceof TransitionState.FromSplit) && (taskInfo = change.getTaskInfo()) != null && taskInfo.taskId == ((TransitionState.FromSplit) transitionStateRequireTransitionState).otherSplitTask) {
                transaction.hide(change.getLeash());
                transaction2.hide(change.getLeash());
            } else if (change.getMode() == 2) {
                transaction.hide(change.getLeash());
                transaction2.hide(change.getLeash());
            } else {
                ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
                if (taskInfo2 == null || taskInfo2.taskId != transitionStateRequireTransitionState.getDraggedTaskId()) {
                    ActivityManager.RunningTaskInfo taskInfo3 = change.getTaskInfo();
                    if (taskInfo3 != null && taskInfo3.getWindowingMode() == 5) {
                        TransitionInfo.Change draggedTaskChange = transitionStateRequireTransitionState.getDraggedTaskChange();
                        if (draggedTaskChange == null || (leash = draggedTaskChange.getLeash()) == null) {
                            throw new IllegalStateException("Expected dragged leash to be non-null");
                        }
                        int i3 = -i;
                        transaction.setRelativeLayer(change.getLeash(), leash, i3);
                        transaction2.setRelativeLayer(change.getLeash(), leash, i3);
                        arrayList.add(change);
                    }
                } else {
                    transaction.show(change.getLeash());
                    transaction2.show(change.getLeash());
                    transitionStateRequireTransitionState.setDraggedTaskChange(change);
                    if ((transitionStateRequireTransitionState instanceof TransitionState.FromFullscreen) && (surfaceControl = ((TransitionState.FromFullscreen) transitionStateRequireTransitionState).transitionRootLeash) != null && surfaceControl.isValid()) {
                        logV$4("reparent " + change.getLeash() + " to " + surfaceControl, new Object[0]);
                        transaction.reparent(change.getLeash(), surfaceControl);
                    }
                    DragToDesktopLayers surfaceLayers = transitionStateRequireTransitionState.getSurfaceLayers();
                    if (surfaceLayers != null) {
                        transaction.setLayer(change.getLeash(), surfaceLayers.dragLayer);
                    }
                }
            }
            i = i2;
        }
        transitionStateRequireTransitionState.setFreeformTaskChanges(arrayList);
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x01c1  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        SurfaceControl surfaceControl;
        TransitionState transitionStateRequireTransitionState = requireTransitionState();
        if (DesktopModeFlags.ENABLE_DRAG_TO_DESKTOP_INCOMING_TRANSITIONS_BUGFIX.isTrue()) {
            if (transitionInfo.getType() == 1109 && Intrinsics.areEqual(iBinder, transitionStateRequireTransitionState.getStartTransitionToken()) && Intrinsics.areEqual(transitionStateRequireTransitionState.isTransitionReversed, Boolean.TRUE)) {
                logW$2("handleCancelOrExitAfterInterrupt: clear state, reason=reversed_order, " + transitionStateRequireTransitionState, new Object[0]);
                transaction.apply();
                transitionFinishCallback.onTransitionFinished(null);
                clearState();
                DesktopTasksController$dragToDesktopStateListener$1 desktopTasksController$dragToDesktopStateListener$1 = this.dragToDesktopStateListener;
                if (desktopTasksController$dragToDesktopStateListener$1 == null) {
                    return true;
                }
                desktopTasksController$dragToDesktopStateListener$1.removeVisualIndicator();
                return true;
            }
            boolean z = transitionInfo.getType() == 1111 && Intrinsics.areEqual(iBinder, transitionStateRequireTransitionState.getCancelTransitionToken());
            boolean z2 = transitionInfo.getType() == 1110 && Intrinsics.areEqual(iBinder, transitionStateRequireTransitionState.getEndTransitionToken());
            if (z || z2) {
                if (transitionStateRequireTransitionState.getStartInterrupted()) {
                    logV$4("startAnimation: interrupted -> isCancel=" + z + ", isEnd=" + z2, new Object[0]);
                    if (z2) {
                        setupEndDragToDesktop(transitionInfo, transaction, transaction2);
                        animateEndDragToDesktop(transaction, transitionFinishCallback);
                    } else {
                        for (TransitionInfo.Change change : transitionInfo.getChanges()) {
                            transaction.show(change.getLeash());
                            transaction2.show(change.getLeash());
                        }
                    }
                    transaction.apply();
                    transitionFinishCallback.onTransitionFinished(null);
                    clearState();
                    return true;
                }
                logW$2("Not interrupted, but received startAnimation for cancel/end drag.isCancel=" + z + ", isEnd=" + z2, new Object[0]);
                if (transitionStateRequireTransitionState.getStartTransitionFinishCb() == null) {
                    transitionStateRequireTransitionState.isTransitionReversed = Boolean.TRUE;
                }
            }
        }
        if (transitionInfo.getType() != 1109 || !Intrinsics.areEqual(iBinder, transitionStateRequireTransitionState.getStartTransitionToken())) {
            return false;
        }
        DragToDesktopLayers dragToDesktopLayersCalculateStartDragToDesktopLayers = calculateStartDragToDesktopLayers(transitionInfo);
        TransitionUtil.LeafTaskFilter leafTaskFilter = new TransitionUtil.LeafTaskFilter();
        Iterator it = new IndexingIterable(new CollectionsKt___CollectionsKt$$ExternalSyntheticLambda0(transitionInfo.getChanges())).iterator();
        while (true) {
            IndexingIterator indexingIterator = (IndexingIterator) it;
            if (!indexingIterator.iterator.hasNext()) {
                transitionStateRequireTransitionState.setSurfaceLayers(dragToDesktopLayersCalculateStartDragToDesktopLayers);
                transitionStateRequireTransitionState.setStartTransitionFinishCb(transitionFinishCallback);
                transitionStateRequireTransitionState.setStartTransitionFinishTransaction(transaction2);
                TransitionInfo.Change draggedTaskChange = transitionStateRequireTransitionState.getDraggedTaskChange();
                if (draggedTaskChange == null) {
                    throw new IllegalStateException("Expected non-null task change.");
                }
                ActivityManager.RunningTaskInfo taskInfo = draggedTaskChange.getTaskInfo();
                if (taskInfo == null) {
                    throw new IllegalStateException("Expected non-null task info.");
                }
                if (DesktopModeFlags.ENABLE_VISUAL_INDICATOR_IN_TRANSITION_BUGFIX.isTrue()) {
                    TransitionInfo.Root root = transitionInfo.getRoot(transitionInfo.findRootIndex(taskInfo.displayId));
                    DesktopModeVisualIndicator visualIndicator = transitionStateRequireTransitionState.getVisualIndicator();
                    if (visualIndicator != null) {
                        SurfaceControl leash = root.getLeash();
                        final VisualIndicatorViewContainer visualIndicatorViewContainer = visualIndicator.mVisualIndicatorViewContainer;
                        SurfaceControl surfaceControl2 = visualIndicatorViewContainer.indicatorLeash;
                        if (surfaceControl2 != null) {
                            transaction.reparent(surfaceControl2, leash);
                            if (CoreRune.DW_TASK_SNAPSHOT_BLUR && (surfaceControl = visualIndicatorViewContainer.taskSnapshotLeash) != null) {
                                transaction.reparent(surfaceControl, leash);
                            }
                        }
                        DesktopModeVisualIndicator.IndicatorType indicatorType = visualIndicator.mCurrentType;
                        if (indicatorType != DesktopModeVisualIndicator.IndicatorType.NO_INDICATOR && indicatorType != DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_MAXIMIZED_WINDOW) {
                            final DisplayLayout displayLayout = visualIndicator.mDisplayController.getDisplayLayout(visualIndicator.mTaskInfo.displayId);
                            final DesktopModeVisualIndicator.IndicatorType indicatorType2 = visualIndicator.mCurrentType;
                            final int i = visualIndicator.mTaskInfo.displayId;
                            if (!visualIndicatorViewContainer.isReleased) {
                                visualIndicatorViewContainer.desktopExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$fadeInIndicator$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        VisualIndicatorViewContainer visualIndicatorViewContainer2 = visualIndicatorViewContainer;
                                        visualIndicatorViewContainer2.fadeInIndicatorInternal(displayLayout, indicatorType2, i, visualIndicatorViewContainer2.snapEventHandler);
                                    }
                                });
                            }
                        }
                    }
                }
                transaction.apply();
                if (transitionStateRequireTransitionState.getCancelState() == CancelState.NO_CANCEL) {
                    DesktopModeVisualIndicator visualIndicator2 = transitionStateRequireTransitionState.getVisualIndicator();
                    if (visualIndicator2 != null && visualIndicator2.mVisualIndicatorViewContainer.indicatorLeash != null) {
                        transitionStateRequireTransitionState.getDragAnimator().getClass();
                    }
                    MoveToDesktopAnimator dragAnimator = transitionStateRequireTransitionState.getDragAnimator();
                    dragAnimator.allowSurfaceChangesOnMove = true;
                    dragAnimator.dragToDesktopAnimator.start();
                    return true;
                }
                if (transitionStateRequireTransitionState.getCancelState() == CancelState.STANDARD_CANCEL) {
                    startCancelDragToDesktopTransition();
                    return true;
                }
                CancelState cancelState = transitionStateRequireTransitionState.getCancelState();
                CancelState cancelState2 = CancelState.CANCEL_SPLIT_LEFT;
                if (cancelState == cancelState2 || transitionStateRequireTransitionState.getCancelState() == CancelState.CANCEL_SPLIT_RIGHT) {
                    int i2 = transitionStateRequireTransitionState.getCancelState() == cancelState2 ? 0 : 1;
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    restoreWindowOrder(windowContainerTransaction, requireTransitionState());
                    SurfaceControl.Transaction startTransitionFinishTransaction = transitionStateRequireTransitionState.getStartTransitionFinishTransaction();
                    if (startTransitionFinishTransaction != null) {
                        startTransitionFinishTransaction.apply();
                    }
                    Transitions.TransitionFinishCallback startTransitionFinishCb = transitionStateRequireTransitionState.getStartTransitionFinishCb();
                    if (startTransitionFinishCb != null) {
                        startTransitionFinishCb.onTransitionFinished(null);
                    }
                    requestSplitSelect(i2, taskInfo, new Rect(taskInfo.configuration.windowConfiguration.getBounds()), windowContainerTransaction);
                    return true;
                }
                CancelState cancelState3 = transitionStateRequireTransitionState.getCancelState();
                CancelState cancelState4 = CancelState.CANCEL_BUBBLE_LEFT;
                if (cancelState3 != cancelState4 && transitionStateRequireTransitionState.getCancelState() != CancelState.CANCEL_BUBBLE_RIGHT) {
                    return true;
                }
                if (this.bubbleController.isEmpty() || !(transitionStateRequireTransitionState instanceof TransitionState.FromFullscreen)) {
                    startCancelDragToDesktopTransition();
                    return true;
                }
                TransitionState.FromFullscreen fromFullscreen = (TransitionState.FromFullscreen) transitionStateRequireTransitionState;
                TransitionInfo.Change change2 = fromFullscreen.draggedTaskChange;
                if (change2 == null || change2.getTaskInfo() == null) {
                    throw new IllegalStateException("Expected non-null task info.");
                }
                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                restoreWindowOrder(windowContainerTransaction2, requireTransitionState());
                boolean z3 = fromFullscreen.cancelState == cancelState4;
                PointF pointF = new PointF(0.0f, 0.0f);
                BubbleController bubbleController = (BubbleController) this.bubbleController.orElseThrow(DragToDesktopTransitionHandler$requestBubble$controller$1.INSTANCE);
                new BubbleTransitions.DragData(z3, 1.0f, 0.0f, pointF, windowContainerTransaction2);
                bubbleController.getClass();
                return true;
            }
            IndexedValue indexedValue = (IndexedValue) indexingIterator.next();
            int i3 = indexedValue.index;
            TransitionInfo.Change change3 = (TransitionInfo.Change) indexedValue.value;
            if (TransitionUtil.isWallpaper(change3)) {
                transaction.setLayer(change3.getLeash(), dragToDesktopLayersCalculateStartDragToDesktopLayers.topWallpaperLayer - i3);
                transaction.show(change3.getLeash());
            } else if (isHomeChange(change3)) {
                transitionStateRequireTransitionState.setHomeChange(change3);
                transaction.setLayer(change3.getLeash(), dragToDesktopLayersCalculateStartDragToDesktopLayers.topHomeLayer - i3);
                transaction.show(change3.getLeash());
            } else {
                boolean zIsIndependent = TransitionInfo.isIndependent(change3, transitionInfo);
                int i4 = dragToDesktopLayersCalculateStartDragToDesktopLayers.dragLayer;
                if (zIsIndependent) {
                    boolean z4 = transitionStateRequireTransitionState instanceof TransitionState.FromSplit;
                    int i5 = dragToDesktopLayersCalculateStartDragToDesktopLayers.topAppLayer;
                    if (z4) {
                        TransitionState.FromSplit fromSplit = (TransitionState.FromSplit) transitionStateRequireTransitionState;
                        fromSplit.splitRootChange = change3;
                        if (fromSplit.cancelState == CancelState.NO_CANCEL) {
                            i4 = i5 - i3;
                        }
                        transaction.setLayer(change3.getLeash(), i4);
                        transaction.show(change3.getLeash());
                    } else {
                        if (!(transitionStateRequireTransitionState instanceof TransitionState.FromFullscreen)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        ActivityManager.RunningTaskInfo taskInfo2 = change3.getTaskInfo();
                        if (taskInfo2 != null) {
                            TransitionState.FromFullscreen fromFullscreen2 = (TransitionState.FromFullscreen) transitionStateRequireTransitionState;
                            if (taskInfo2.taskId == fromFullscreen2.draggedTaskId) {
                                fromFullscreen2.draggedTaskChange = change3;
                                ActivityManager.RunningTaskInfo taskInfo3 = change3.getTaskInfo();
                                if (taskInfo3 != null) {
                                    SurfaceControl leash2 = transitionInfo.getRoot(transitionInfo.findRootIndex(taskInfo3.displayId)).getLeash();
                                    fromFullscreen2.transitionRootLeash = leash2;
                                    logV$4("Set rootLeash=" + leash2, new Object[0]);
                                }
                                Rect endAbsBounds = change3.getEndAbsBounds();
                                transaction.setLayer(change3.getLeash(), i4);
                                transaction.setWindowCrop(change3.getLeash(), endAbsBounds.width(), endAbsBounds.height());
                                transaction.show(change3.getLeash());
                            } else {
                                ((TransitionState.FromFullscreen) transitionStateRequireTransitionState).otherRootChanges.add(change3);
                                Rect endAbsBounds2 = change3.getEndAbsBounds();
                                transaction.setLayer(change3.getLeash(), i5 - i3);
                                transaction.setWindowCrop(change3.getLeash(), endAbsBounds2.width(), endAbsBounds2.height());
                                transaction.show(change3.getLeash());
                            }
                        }
                    }
                } else if (leafTaskFilter.test(change3)) {
                    ActivityManager.RunningTaskInfo taskInfo4 = change3.getTaskInfo();
                    if (taskInfo4 != null && taskInfo4.taskId == transitionStateRequireTransitionState.getDraggedTaskId() && transitionStateRequireTransitionState.getCancelState() != CancelState.STANDARD_CANCEL) {
                        transitionStateRequireTransitionState.setDraggedTaskChange(change3);
                    }
                    ActivityManager.RunningTaskInfo taskInfo5 = change3.getTaskInfo();
                    if (taskInfo5 != null && taskInfo5.taskId == transitionStateRequireTransitionState.getDraggedTaskId() && transitionStateRequireTransitionState.getCancelState() == CancelState.NO_CANCEL) {
                        this.taskDisplayAreaOrganizer.reparentToDisplayArea(change3.getEndDisplayId(), transaction, change3.getLeash());
                        Rect endAbsBounds3 = change3.getEndAbsBounds();
                        transaction.setLayer(change3.getLeash(), i4);
                        transaction.setWindowCrop(change3.getLeash(), endAbsBounds3.width(), endAbsBounds3.height());
                        transaction.show(change3.getLeash());
                    }
                }
            }
        }
    }

    public final Animator startCancelAnimation() {
        final TransitionState transitionStateRequireTransitionState = requireTransitionState();
        MoveToDesktopAnimator dragAnimator = transitionStateRequireTransitionState.getDragAnimator();
        TransitionInfo.Change draggedTaskChange = transitionStateRequireTransitionState.getDraggedTaskChange();
        if (draggedTaskChange == null) {
            throw new IllegalStateException("Expected non-null task change");
        }
        final SurfaceControl leash = draggedTaskChange.getLeash();
        dragAnimator.cancelAnimator();
        PointF pointF = dragAnimator.position;
        final float f = pointF.x;
        final float f2 = pointF.y;
        final float f3 = draggedTaskChange.getEndAbsBounds().left - f;
        final float f4 = draggedTaskChange.getEndAbsBounds().top - f2;
        final SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.transactionSupplier.get();
        ValueAnimator duration = ValueAnimator.ofFloat(0.4f, 1.0f).setDuration(336L);
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler$startCancelAnimation$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                float animatedFraction = valueAnimator.getAnimatedFraction();
                float f5 = (f3 * animatedFraction) + f;
                float f6 = (f4 * animatedFraction) + f2;
                SurfaceControl.Transaction transaction2 = transaction;
                SurfaceControl surfaceControl = leash;
                transaction2.setPosition(surfaceControl, f5, f6);
                transaction2.setScale(surfaceControl, fFloatValue, fFloatValue);
                transaction2.show(surfaceControl);
                transaction2.apply();
            }
        });
        duration.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler$startCancelAnimation$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                transitionStateRequireTransitionState.setActiveCancelAnimation(null);
                DesktopTasksController$dragToDesktopStateListener$1 desktopTasksController$dragToDesktopStateListener$1 = this.dragToDesktopStateListener;
                if (desktopTasksController$dragToDesktopStateListener$1 != null) {
                    desktopTasksController$dragToDesktopStateListener$1.removeVisualIndicator();
                }
                this.startCancelDragToDesktopTransition();
            }
        });
        duration.start();
        return duration;
    }

    public final void startCancelDragToDesktopTransition() {
        TransitionState transitionStateRequireTransitionState = requireTransitionState();
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        restoreWindowOrder(windowContainerTransaction, transitionStateRequireTransitionState);
        transitionStateRequireTransitionState.setCancelTransitionToken(this.transitions.startTransition(VolteConstants.ErrorCode.CALL_HOLD_FAILED, windowContainerTransaction, this));
    }

    private DragToDesktopTransitionHandler(Context context, Transitions transitions, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DesktopUserRepositories desktopUserRepositories, InteractionJankMonitor interactionJankMonitor, Optional<BubbleController> optional, Supplier<SurfaceControl.Transaction> supplier, DesktopState desktopState) {
        this.context = context;
        this.transitions = transitions;
        this.taskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        this.desktopUserRepositories = desktopUserRepositories;
        this.interactionJankMonitor = interactionJankMonitor;
        this.bubbleController = optional;
        this.transactionSupplier = supplier;
        this.logHistory = new LinkedList();
        this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.rectEvaluator = new RectEvaluator(new Rect());
        this.launchHomeIntent = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME");
    }
}
