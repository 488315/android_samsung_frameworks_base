package com.android.wm.shell.desktopmode.compatui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.os.IBinder;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.app.animation.Interpolators;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.desktopmode.DesktopDisplayEventHandler$$ExternalSyntheticOutline0;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.DesktopWallpaperActivity;
import com.android.wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.shared.desktopmode.DesktopModeCompatPolicy;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.RemoteTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;

/* loaded from: classes3.dex */
public final class SystemModalsTransitionHandler implements Transitions.TransitionHandler {
    public final ShellExecutor animExecutor;
    public final DesktopModeCompatPolicy desktopModeCompatPolicy;
    public final DesktopUserRepositories desktopUserRepositories;
    public final ShellExecutor mainExecutor;
    public final Set showingSystemModalsIds = new LinkedHashSet();
    public final Transitions transitions;

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

    public SystemModalsTransitionHandler(Context context, ShellExecutor shellExecutor, ShellExecutor shellExecutor2, ShellInit shellInit, Transitions transitions, DesktopUserRepositories desktopUserRepositories, DesktopModeCompatPolicy desktopModeCompatPolicy) {
        this.mainExecutor = shellExecutor;
        this.animExecutor = shellExecutor2;
        this.transitions = transitions;
        this.desktopUserRepositories = desktopUserRepositories;
        this.desktopModeCompatPolicy = desktopModeCompatPolicy;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.compatui.SystemModalsTransitionHandler.1
            @Override // java.lang.Runnable
            public final void run() {
                SystemModalsTransitionHandler systemModalsTransitionHandler = SystemModalsTransitionHandler.this;
                systemModalsTransitionHandler.transitions.addHandler(systemModalsTransitionHandler);
            }
        }, this);
    }

    public static void logV$5(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strConcat = "%s: ".concat(str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "SystemModalsTransitionHandler", objArr);
        ProtoLog.v(shellProtoLogGroup, strConcat, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public final void animateSystemModal(final SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback, boolean z) {
        float f = z ? 0.0f : 1.0f;
        float f2 = z ? 1.0f : 0.0f;
        final SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
        valueAnimatorOfFloat.setDuration(150L);
        valueAnimatorOfFloat.setInterpolator(Interpolators.LINEAR);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.compatui.SystemModalsTransitionHandler$createAlphaAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                transaction3.setAlpha(surfaceControl, ((Float) valueAnimator.getAnimatedValue()).floatValue()).apply();
            }
        });
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.desktopmode.compatui.SystemModalsTransitionHandler$animateSystemModal$$inlined$addListener$default$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                ShellExecutor shellExecutor = this.this$0.mainExecutor;
                final Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
                shellExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.compatui.SystemModalsTransitionHandler$animateSystemModal$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        transitionFinishCallback2.onTransitionFinished(null);
                    }
                });
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
        if (z) {
            transaction2.show(surfaceControl);
        } else {
            transaction2.hide(surfaceControl);
        }
        transaction.setAlpha(surfaceControl, f);
        transaction.apply();
        this.animExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.compatui.SystemModalsTransitionHandler.animateSystemModal.2
            @Override // java.lang.Runnable
            public final void run() {
                valueAnimatorOfFloat.start();
            }
        });
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        ActivityManager.RunningTaskInfo taskInfo;
        Object next;
        IBinder iBinder2;
        TransitionInfo transitionInfo2;
        ActivityManager.RunningTaskInfo taskInfo2;
        int i = 0;
        if (this.desktopUserRepositories.getCurrent().isAnyDeskActive(0) && !KeyguardTransitionHandler.handles(transitionInfo)) {
            boolean zIsOpeningType = TransitionUtil.isOpeningType(transitionInfo.getType());
            DesktopModeCompatPolicy desktopModeCompatPolicy = this.desktopModeCompatPolicy;
            Object obj = null;
            if (zIsOpeningType) {
                Iterator it = transitionInfo.getChanges().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    TransitionInfo.Change change = (TransitionInfo.Change) next;
                    if (TransitionUtil.isOpeningMode(change.getMode()) && !TransitionUtil.isHomeOrRecents(change) && (taskInfo2 = change.getTaskInfo()) != null) {
                        DesktopWallpaperActivity.Companion.getClass();
                        if (!DesktopWallpaperActivity.Companion.isWallpaperTask(taskInfo2) && desktopModeCompatPolicy.isTopActivityExemptFromDesktopWindowing(taskInfo2)) {
                            break;
                        }
                    }
                }
                TransitionInfo.Change change2 = (TransitionInfo.Change) next;
                if (change2 != null) {
                    SystemModalsTransitionHandler$handoverIfNeeded$1 systemModalsTransitionHandler$handoverIfNeeded$1 = SystemModalsTransitionHandler$handoverIfNeeded$1.INSTANCE;
                    ArrayList arrayList = this.transitions.mHandlers;
                    int size = arrayList.size();
                    while (true) {
                        if (i >= size) {
                            iBinder2 = iBinder;
                            transitionInfo2 = transitionInfo;
                            break;
                        }
                        Object obj2 = arrayList.get(i);
                        i++;
                        iBinder2 = iBinder;
                        transitionInfo2 = transitionInfo;
                        Object handlerForHandover = ((Transitions.TransitionHandler) obj2).getHandlerForHandover(iBinder2, transitionInfo2, systemModalsTransitionHandler$handoverIfNeeded$1);
                        if (handlerForHandover != null) {
                            obj = handlerForHandover;
                            break;
                        }
                    }
                    if (obj != null && ((RemoteTransitionHandler) obj).startAnimation(iBinder2, transitionInfo2, transaction, transaction2, transitionFinishCallback)) {
                        Slog.d("SystemModalsTransitionHandler", "startAnimation: opening transition handed over.");
                        return true;
                    }
                    ActivityManager.RunningTaskInfo taskInfo3 = change2.getTaskInfo();
                    if (taskInfo3 == null) {
                        throw new IllegalArgumentException("Required value was null.");
                    }
                    logV$5("Animating system modal launch: taskId=%d", Integer.valueOf(taskInfo3.taskId));
                    this.showingSystemModalsIds.add(Integer.valueOf(taskInfo3.taskId));
                    animateSystemModal(change2.getLeash(), transaction, transaction2, transitionFinishCallback, true);
                    return true;
                }
            } else if (TransitionUtil.isClosingType(transitionInfo.getType())) {
                for (Object obj3 : transitionInfo.getChanges()) {
                    TransitionInfo.Change change3 = (TransitionInfo.Change) obj3;
                    if (TransitionUtil.isClosingMode(change3.getMode()) && (taskInfo = change3.getTaskInfo()) != null) {
                        DesktopWallpaperActivity.Companion.getClass();
                        if ((!DesktopWallpaperActivity.Companion.isWallpaperTask(taskInfo) && desktopModeCompatPolicy.isTopActivityExemptFromDesktopWindowing(taskInfo)) || this.showingSystemModalsIds.contains(Integer.valueOf(taskInfo.taskId))) {
                            obj = obj3;
                            break;
                        }
                    }
                }
                TransitionInfo.Change change4 = (TransitionInfo.Change) obj;
                if (change4 != null) {
                    ActivityManager.RunningTaskInfo taskInfo4 = change4.getTaskInfo();
                    if (taskInfo4 == null) {
                        throw new IllegalArgumentException("Required value was null.");
                    }
                    logV$5("Animating system modal close: taskId=%d", Integer.valueOf(taskInfo4.taskId));
                    this.showingSystemModalsIds.remove(Integer.valueOf(taskInfo4.taskId));
                    animateSystemModal(change4.getLeash(), transaction, transaction2, transitionFinishCallback, false);
                    return true;
                }
            }
        }
        return false;
    }
}
