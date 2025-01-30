package com.android.wm.shell.freeform;

import android.app.ActivityManager;
import android.content.Context;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DexWindowDecorViewModel;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FreeformTaskTransitionObserver implements Transitions.TransitionObserver {
    public final DexWindowDecorViewModel mDexWindowDecorViewModel;
    public final Map mTransitionToTaskInfo = new HashMap();
    public final Transitions mTransitions;
    public final WindowDecorViewModel mWindowDecorViewModel;

    public FreeformTaskTransitionObserver(Context context, ShellInit shellInit, Transitions transitions, WindowDecorViewModel windowDecorViewModel, DexWindowDecorViewModel dexWindowDecorViewModel) {
        this.mTransitions = transitions;
        this.mWindowDecorViewModel = windowDecorViewModel;
        this.mDexWindowDecorViewModel = dexWindowDecorViewModel;
        if (Transitions.ENABLE_SHELL_TRANSITIONS && FreeformComponents.isFreeformEnabled(context)) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformTaskTransitionObserver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FreeformTaskTransitionObserver.this.onInit();
                }
            }, this);
        }
    }

    public void onInit() {
        this.mTransitions.mObservers.add(this);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionFinished(IBinder iBinder) {
        HashMap hashMap = (HashMap) this.mTransitionToTaskInfo;
        List list = (List) hashMap.getOrDefault(iBinder, Collections.emptyList());
        hashMap.remove(iBinder);
        WindowDecorViewModel windowDecorViewModel = this.mWindowDecorViewModel;
        windowDecorViewModel.onTransitionFinished(iBinder);
        this.mDexWindowDecorViewModel.onTransitionFinished(iBinder);
        for (int i = 0; i < list.size(); i++) {
            windowDecorViewModel.destroyWindowDecoration((ActivityManager.RunningTaskInfo) list.get(i));
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
        this.mWindowDecorViewModel.onTransitionMerged(iBinder, iBinder2);
        Map map = this.mTransitionToTaskInfo;
        List list = (List) ((HashMap) map).get(iBinder);
        if (list == null) {
            return;
        }
        ((HashMap) map).remove(iBinder);
        List list2 = (List) ((HashMap) map).get(iBinder2);
        if (list2 != null) {
            list2.addAll(list);
        } else {
            ((HashMap) map).put(iBinder2, list);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        ActivityManager.RunningTaskInfo taskInfo;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (TransitionInfo.Change change : transitionInfo.getChanges()) {
            if ((change.getFlags() & 2) == 0 && (taskInfo = change.getTaskInfo()) != null && taskInfo.taskId != -1) {
                if (change.getParent() != null && transitionInfo.getChange(change.getParent()).getTaskInfo() != null) {
                    arrayList2.add(change.getParent());
                }
                if (!arrayList2.contains(change.getContainer())) {
                    int mode = change.getMode();
                    WindowDecorViewModel windowDecorViewModel = this.mWindowDecorViewModel;
                    if (mode == 1) {
                        windowDecorViewModel.onTaskOpening(change.getTaskInfo(), change.getLeash(), transaction, transaction2);
                    } else if (mode == 2) {
                        arrayList.add(change.getTaskInfo());
                        windowDecorViewModel.onTaskClosing(change.getTaskInfo(), transaction, transaction2);
                    } else if (mode == 3) {
                        windowDecorViewModel.onTaskChanging(change.getTaskInfo(), change.getLeash(), transaction, transaction2);
                        if (CoreRune.MW_CAPTION_SHELL) {
                            windowDecorViewModel.onTaskToFront(change.getTaskInfo());
                        }
                    } else if (mode != 4) {
                        if (mode == 6) {
                            windowDecorViewModel.onTaskChanging(change.getTaskInfo(), change.getLeash(), transaction, transaction2);
                        }
                    } else if (CoreRune.MW_CAPTION_SHELL) {
                        windowDecorViewModel.onTaskToBack(change.getTaskInfo());
                    }
                    windowDecorViewModel.onTransitionReady(iBinder, transitionInfo, change);
                    this.mDexWindowDecorViewModel.onTransitionReady(iBinder, transitionInfo, change);
                }
            }
        }
        ((HashMap) this.mTransitionToTaskInfo).put(iBinder, arrayList);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionStarting() {
    }
}
