package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.shared.animation.MinimizeAnimator;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;

/* loaded from: classes3.dex */
public final class DesktopMinimizationTransitionHandler implements Transitions.TransitionHandler {
    public static final Companion Companion = new Companion(null);
    public final ShellExecutor animExecutor;
    public final Handler animHandler;
    public final DisplayController displayController;
    public final ShellExecutor mainExecutor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public DesktopMinimizationTransitionHandler(ShellExecutor shellExecutor, ShellExecutor shellExecutor2, DisplayController displayController, Handler handler) {
        this.mainExecutor = shellExecutor;
        this.animExecutor = shellExecutor2;
        this.displayController = displayController;
        this.animHandler = handler;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        ActivityManager.RunningTaskInfo taskInfo;
        if (TransitionUtil.isClosingType(transitionInfo.getType()) || transitionInfo.getType() == 1020) {
            final ArrayList arrayList = new ArrayList();
            Function1 function1 = new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopMinimizationTransitionHandler$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    final List list = arrayList;
                    final Animator animator = (Animator) obj;
                    ShellExecutor shellExecutor = this.f$0.mainExecutor;
                    final Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
                    shellExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopMinimizationTransitionHandler$startAnimation$onAnimFinish$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            list.remove(animator);
                            if (list.isEmpty()) {
                                transitionFinishCallback2.onTransitionFinished(null);
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
                change.getClass();
                if (change.getMode() == transitionInfo.getType() || (transitionInfo.getType() == 1020 && change.getMode() == 4)) {
                    ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
                    if ((taskInfo2 != null && taskInfo2.getWindowingMode() == 5) || ((taskInfo = change.getTaskInfo()) != null && taskInfo.getWindowingMode() == 1)) {
                        arrayList2.add(obj);
                    }
                }
            }
            ArrayList arrayList3 = new ArrayList();
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                int i2 = i + 1;
                TransitionInfo.Change change2 = (TransitionInfo.Change) arrayList2.get(i);
                change2.getClass();
                SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
                transaction2.hide(change2.getLeash());
                ActivityManager.RunningTaskInfo taskInfo3 = change2.getTaskInfo();
                Animator animatorCreate = null;
                Context displayContext = taskInfo3 != null ? this.displayController.getDisplayContext(taskInfo3.displayId) : null;
                if (displayContext == null) {
                    ActivityManager.RunningTaskInfo taskInfo4 = change2.getTaskInfo();
                    Integer numValueOf = taskInfo4 != null ? Integer.valueOf(taskInfo4.taskId) : null;
                    ActivityManager.RunningTaskInfo taskInfo5 = change2.getTaskInfo();
                    String str = "displayContext is null for taskId=" + numValueOf + ", displayId=" + (taskInfo5 != null ? Integer.valueOf(taskInfo5.displayId) : null);
                    Companion.getClass();
                    ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                    String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", str);
                    SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopMinimizationTransitionHandler", new Object[0]);
                    ProtoLog.w(shellProtoLogGroup, strM, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
                } else {
                    animatorCreate = MinimizeAnimator.create(displayContext, change2, transaction3, function1, InteractionJankMonitor.getInstance(), this.animHandler);
                }
                if (animatorCreate != null) {
                    arrayList3.add(animatorCreate);
                }
                i = i2;
            }
            CollectionsKt__MutableCollectionsKt.addAll(arrayList3, arrayList);
            if (!arrayList.isEmpty()) {
                transaction.apply();
                this.animExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopMinimizationTransitionHandler.startAnimation.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            ((Animator) it.next()).start();
                        }
                    }
                });
                return true;
            }
        }
        return false;
    }
}
