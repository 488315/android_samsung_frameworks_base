package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class CloseDesktopTaskTransitionHandler implements Transitions.TransitionHandler {
    public final ShellExecutor animExecutor;
    public final Handler animHandler;
    public final Context context;
    public final InteractionJankMonitor interactionJankMonitor;
    public final ShellExecutor mainExecutor;
    public final Map runningAnimations;
    public final Supplier transactionSupplier;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public CloseDesktopTaskTransitionHandler(Context context, ShellExecutor shellExecutor, ShellExecutor shellExecutor2, Handler handler) {
        this(context, shellExecutor, shellExecutor2, null, handler, 8, null);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(final IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        SurfaceControl leash;
        ActivityManager.RunningTaskInfo taskInfo;
        int i = 1;
        int i2 = 2;
        if (transitionInfo.getType() != 2) {
            return false;
        }
        final ArrayList arrayList = new ArrayList();
        final Function1 function1 = new Function1() { // from class: com.android.wm.shell.desktopmode.CloseDesktopTaskTransitionHandler$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                final List list = arrayList;
                final IBinder iBinder2 = iBinder;
                final Animator animator = (Animator) obj;
                final CloseDesktopTaskTransitionHandler closeDesktopTaskTransitionHandler = this.f$0;
                ShellExecutor shellExecutor = closeDesktopTaskTransitionHandler.mainExecutor;
                final Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
                shellExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.CloseDesktopTaskTransitionHandler$startAnimation$onAnimFinish$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        list.remove(animator);
                        if (list.isEmpty()) {
                            closeDesktopTaskTransitionHandler.runningAnimations.remove(iBinder2);
                            transitionFinishCallback2.onTransitionFinished(null);
                            closeDesktopTaskTransitionHandler.interactionJankMonitor.end(122);
                        }
                    }
                });
                return Unit.INSTANCE;
            }
        };
        List changes = transitionInfo.getChanges();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : changes) {
            TransitionInfo.Change change = (TransitionInfo.Change) obj;
            if (change.getMode() == 2 && (taskInfo = change.getTaskInfo()) != null && taskInfo.getWindowingMode() == 5) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
        int size = arrayList2.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj2 = arrayList2.get(i3);
            i3 += i;
            final TransitionInfo.Change change2 = (TransitionInfo.Change) obj2;
            change2.getClass();
            transaction2.hide(change2.getLeash());
            AnimatorSet animatorSet = new AnimatorSet();
            Animator[] animatorArr = new Animator[i2];
            Rect startAbsBounds = change2.getStartAbsBounds();
            Rect rect = new Rect(startAbsBounds);
            float f = i2;
            rect.inset((int) ((startAbsBounds.width() * 0.050000012f) / f), (int) ((startAbsBounds.height() * 0.050000012f) / f));
            rect.offset(0, (int) ActionRow$$ExternalSyntheticOutline0.m(this.context, 1, 36.0f));
            ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(new RectEvaluator(), startAbsBounds, rect);
            valueAnimatorOfObject.setDuration(200L);
            valueAnimatorOfObject.setInterpolator(Interpolators.STANDARD_ACCELERATE);
            valueAnimatorOfObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.CloseDesktopTaskTransitionHandler$createBoundsCloseAnimation$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Rect rect2 = (Rect) valueAnimator.getAnimatedValue();
                    float animatedFraction = 1 - (valueAnimator.getAnimatedFraction() * 0.050000012f);
                    ((SurfaceControl.Transaction) this.this$0.transactionSupplier.get()).setPosition(change2.getLeash(), rect2.left, rect2.top).setScale(change2.getLeash(), animatedFraction, animatedFraction).setFrameTimeline(Choreographer.getInstance().getVsyncId()).apply();
                }
            });
            animatorArr[0] = valueAnimatorOfObject;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
            valueAnimatorOfFloat.setDuration(100L);
            valueAnimatorOfFloat.setInterpolator(Interpolators.LINEAR);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.CloseDesktopTaskTransitionHandler$createAlphaCloseAnimation$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ((SurfaceControl.Transaction) this.this$0.transactionSupplier.get()).setAlpha(change2.getLeash(), ((Float) valueAnimator.getAnimatedValue()).floatValue()).apply();
                }
            });
            animatorArr[1] = valueAnimatorOfFloat;
            animatorSet.playTogether(animatorArr);
            animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.desktopmode.CloseDesktopTaskTransitionHandler$createCloseAnimation$lambda$4$$inlined$addListener$default$1
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    function1.mo781invoke(animator);
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                }
            });
            arrayList3.add(animatorSet);
            arrayList2 = arrayList2;
            arrayList = arrayList;
            i = 1;
            i2 = 2;
        }
        final ArrayList arrayList4 = arrayList;
        ArrayList arrayList5 = arrayList2;
        CollectionsKt__MutableCollectionsKt.addAll(arrayList3, arrayList4);
        if (arrayList4.isEmpty()) {
            return false;
        }
        this.runningAnimations.put(iBinder, arrayList4);
        TransitionInfo.Change change3 = (TransitionInfo.Change) CollectionsKt___CollectionsKt.lastOrNull(arrayList5);
        if (change3 != null && (leash = change3.getLeash()) != null) {
            this.interactionJankMonitor.begin(leash, this.context, this.animHandler, 122);
        }
        transaction.apply();
        this.animExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.CloseDesktopTaskTransitionHandler.startAnimation.3
            @Override // java.lang.Runnable
            public final void run() {
                Iterator it = arrayList4.iterator();
                while (it.hasNext()) {
                    ((Animator) it.next()).start();
                }
            }
        });
        return true;
    }

    public CloseDesktopTaskTransitionHandler(Context context, ShellExecutor shellExecutor, ShellExecutor shellExecutor2, Supplier<SurfaceControl.Transaction> supplier, Handler handler) {
        this.context = context;
        this.mainExecutor = shellExecutor;
        this.animExecutor = shellExecutor2;
        this.transactionSupplier = supplier;
        this.animHandler = handler;
        this.runningAnimations = new LinkedHashMap();
        this.interactionJankMonitor = InteractionJankMonitor.getInstance();
    }

    public /* synthetic */ CloseDesktopTaskTransitionHandler(Context context, ShellExecutor shellExecutor, ShellExecutor shellExecutor2, Supplier supplier, Handler handler, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, shellExecutor, shellExecutor2, (i & 8) != 0 ? new Supplier() { // from class: com.android.wm.shell.desktopmode.CloseDesktopTaskTransitionHandler.1
            @Override // java.util.function.Supplier
            public final Object get() {
                return new SurfaceControl.Transaction();
            }
        } : supplier, handler);
    }
}
