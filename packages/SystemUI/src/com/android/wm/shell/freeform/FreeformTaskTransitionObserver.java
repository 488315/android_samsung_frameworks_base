package com.android.wm.shell.freeform;

import android.app.ActivityManager;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;
import android.view.SurfaceControl;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import android.window.TransitionInfo;
import com.android.wm.shell.desktopmode.DesktopDisplayEventHandler;
import com.android.wm.shell.desktopmode.DesktopImmersiveController;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.DesktopUserRepositories$desktopRepoByUserId$1;
import com.android.wm.shell.desktopmode.multidesks.DeskTransition;
import com.android.wm.shell.desktopmode.multidesks.DesksOrganizer;
import com.android.wm.shell.desktopmode.multidesks.DesksTransitionObserver;
import com.android.wm.shell.desktopmode.multidesks.OnDeskRemovedListener;
import com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer;
import com.android.wm.shell.shared.FocusTransitionListener;
import com.android.wm.shell.shared.IFocusTransitionListener$Stub$Proxy;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.FocusTransitionObserver;
import com.android.wm.shell.transition.FocusTransitionObserver$$ExternalSyntheticLambda1;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public class FreeformTaskTransitionObserver implements Transitions.TransitionObserver {
    public final Optional mDesksTransitionObserver;
    public final Optional mDesktopImmersiveController;
    public final FocusTransitionObserver mFocusTransitionObserver;
    public final Optional mTaskChangeListener;
    public final Map mTransitionToTaskInfo = new HashMap();
    public final Transitions mTransitions;
    public final WindowDecorViewModel mWindowDecorViewModel;

    public FreeformTaskTransitionObserver(Context context, ShellInit shellInit, Transitions transitions, Optional<DesktopImmersiveController> optional, WindowDecorViewModel windowDecorViewModel, Optional<TaskChangeListener> optional2, FocusTransitionObserver focusTransitionObserver, Optional<DesksTransitionObserver> optional3, DesktopState desktopState) {
        this.mTransitions = transitions;
        this.mDesktopImmersiveController = optional;
        this.mWindowDecorViewModel = windowDecorViewModel;
        this.mTaskChangeListener = optional2;
        this.mFocusTransitionObserver = focusTransitionObserver;
        this.mDesksTransitionObserver = optional3;
        if (((DesktopStateImpl) desktopState).isFreeformEnabled) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformTaskTransitionObserver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.onInit();
                }
            }, this);
        }
    }

    public void onInit() {
        this.mTransitions.registerObserver(this);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionFinished(final IBinder iBinder, final boolean z) {
        this.mDesksTransitionObserver.ifPresent(new FreeformTaskTransitionObserver$$ExternalSyntheticLambda4(iBinder, 5));
        if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue()) {
            this.mDesktopImmersiveController.ifPresent(new Consumer() { // from class: com.android.wm.shell.freeform.FreeformTaskTransitionObserver$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((DesktopImmersiveController) obj).onTransitionFinished(iBinder, z);
                }
            });
        }
        List list = (List) ((HashMap) this.mTransitionToTaskInfo).getOrDefault(iBinder, Collections.EMPTY_LIST);
        ((HashMap) this.mTransitionToTaskInfo).remove(iBinder);
        boolean z2 = CoreRune.MW_CAPTION;
        WindowDecorViewModel windowDecorViewModel = this.mWindowDecorViewModel;
        if (z2) {
            windowDecorViewModel.onTransitionFinished(iBinder);
        }
        for (int i = 0; i < list.size(); i++) {
            windowDecorViewModel.destroyWindowDecoration((ActivityManager.RunningTaskInfo) list.get(i));
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionMerged(final IBinder iBinder, final IBinder iBinder2) {
        final int i = 0;
        this.mDesksTransitionObserver.ifPresent(new Consumer() { // from class: com.android.wm.shell.freeform.FreeformTaskTransitionObserver$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Set set;
                switch (i) {
                    case 0:
                        IBinder iBinder3 = iBinder;
                        IBinder iBinder4 = iBinder2;
                        DesksTransitionObserver desksTransitionObserver = (DesksTransitionObserver) obj;
                        desksTransitionObserver.getClass();
                        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue() && (set = (Set) desksTransitionObserver.deskTransitions.remove(iBinder3)) != null) {
                            Map map = desksTransitionObserver.deskTransitions;
                            Set set2 = set;
                            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
                            Iterator it = set2.iterator();
                            while (it.hasNext()) {
                                arrayList.add(((DeskTransition) it.next()).copyWithToken(iBinder4));
                            }
                            map.put(iBinder4, CollectionsKt___CollectionsKt.toMutableSet(arrayList));
                            break;
                        }
                        break;
                    default:
                        ((DesktopImmersiveController) obj).onTransitionMerged(iBinder, iBinder2);
                        break;
                }
            }
        });
        if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue()) {
            final int i2 = 1;
            this.mDesktopImmersiveController.ifPresent(new Consumer() { // from class: com.android.wm.shell.freeform.FreeformTaskTransitionObserver$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Set set;
                    switch (i2) {
                        case 0:
                            IBinder iBinder3 = iBinder;
                            IBinder iBinder4 = iBinder2;
                            DesksTransitionObserver desksTransitionObserver = (DesksTransitionObserver) obj;
                            desksTransitionObserver.getClass();
                            if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue() && (set = (Set) desksTransitionObserver.deskTransitions.remove(iBinder3)) != null) {
                                Map map = desksTransitionObserver.deskTransitions;
                                Set set2 = set;
                                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
                                Iterator it = set2.iterator();
                                while (it.hasNext()) {
                                    arrayList.add(((DeskTransition) it.next()).copyWithToken(iBinder4));
                                }
                                map.put(iBinder4, CollectionsKt___CollectionsKt.toMutableSet(arrayList));
                                break;
                            }
                            break;
                        default:
                            ((DesktopImmersiveController) obj).onTransitionMerged(iBinder, iBinder2);
                            break;
                    }
                }
            });
        }
        List list = (List) ((HashMap) this.mTransitionToTaskInfo).get(iBinder);
        if (list == null) {
            return;
        }
        ((HashMap) this.mTransitionToTaskInfo).remove(iBinder);
        List list2 = (List) ((HashMap) this.mTransitionToTaskInfo).get(iBinder2);
        if (list2 != null) {
            list2.addAll(list);
        } else {
            ((HashMap) this.mTransitionToTaskInfo).put(iBinder2, list);
        }
        if (CoreRune.MW_CAPTION) {
            this.mWindowDecorViewModel.onTransitionMerged(iBinder, iBinder2);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionReady(final IBinder iBinder, final TransitionInfo transitionInfo, final SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2) {
        ActivityManager.RunningTaskInfo taskInfo;
        final int i = 0;
        this.mDesksTransitionObserver.ifPresent(new Consumer() { // from class: com.android.wm.shell.freeform.FreeformTaskTransitionObserver$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityManager.RunningTaskInfo taskInfo2;
                Integer deskAtEnd;
                int i2;
                Object next;
                ActivityManager.RunningTaskInfo taskInfo3;
                switch (i) {
                    case 0:
                        IBinder iBinder2 = iBinder;
                        TransitionInfo transitionInfo2 = transitionInfo;
                        DesksTransitionObserver desksTransitionObserver = (DesksTransitionObserver) obj;
                        desksTransitionObserver.getClass();
                        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                            Set<DeskTransition> set = (Set) desksTransitionObserver.deskTransitions.remove(iBinder2);
                            Collection linkedHashSet = (Set) ((LinkedHashMap) desksTransitionObserver.activeDeskTransitions).get(iBinder2);
                            if (linkedHashSet == null) {
                                linkedHashSet = new LinkedHashSet();
                            }
                            if (set != null) {
                                for (DeskTransition deskTransition : set) {
                                    DesksTransitionObserver.logD("Desk transition ready: %s", deskTransition);
                                    DesktopUserRepositories desktopUserRepositories = desksTransitionObserver.desktopUserRepositories;
                                    DesktopRepository current = desktopUserRepositories.getCurrent();
                                    Object obj2 = null;
                                    if (!(deskTransition instanceof DeskTransition.RemoveDesk)) {
                                        boolean z = deskTransition instanceof DeskTransition.ActivateDesk;
                                        DesksOrganizer desksOrganizer = desksTransitionObserver.desksOrganizer;
                                        if (z) {
                                            DeskTransition.ActivateDesk activateDesk = (DeskTransition.ActivateDesk) deskTransition;
                                            int i3 = activateDesk.userId;
                                            if (i3 == -1 || i3 == current.userId) {
                                                Iterator it = transitionInfo2.getChanges().iterator();
                                                while (true) {
                                                    boolean zHasNext = it.hasNext();
                                                    i2 = activateDesk.deskId;
                                                    if (zHasNext) {
                                                        next = it.next();
                                                        TransitionInfo.Change change = (TransitionInfo.Change) next;
                                                        change.getClass();
                                                        ((RootTaskDesksOrganizer) desksOrganizer).getClass();
                                                        ActivityManager.RunningTaskInfo taskInfo4 = change.getTaskInfo();
                                                        if (taskInfo4 == null || taskInfo4.taskId != i2 || (taskInfo3 = change.getTaskInfo()) == null || !taskInfo3.isVisibleRequested || change.getMode() != 3) {
                                                        }
                                                    } else {
                                                        next = null;
                                                    }
                                                }
                                                if (((TransitionInfo.Change) next) == null) {
                                                    DesksTransitionObserver.logD("Activating desk without transition change", new Object[0]);
                                                }
                                                int displayForDesk = current.desktopData.getDisplayForDesk(i2);
                                                int i4 = activateDesk.displayId;
                                                if (displayForDesk != i4) {
                                                    current.onDeskDisplayChanged(i2, i4);
                                                    OnDeskRemovedListener onDeskRemovedListener = activateDesk.onDeskRemovedListener;
                                                    if (onDeskRemovedListener != null) {
                                                        ((DesktopDisplayEventHandler) onDeskRemovedListener).createDefaultDesksIfNeeded(Collections.singleton(Integer.valueOf(displayForDesk)), null);
                                                    }
                                                }
                                                current.setActiveDesk(i4, i2);
                                            }
                                        } else if (deskTransition instanceof DeskTransition.ActiveDeskWithTask) {
                                            Iterator it2 = transitionInfo2.getChanges().iterator();
                                            while (true) {
                                                if (it2.hasNext()) {
                                                    Object next2 = it2.next();
                                                    TransitionInfo.Change change2 = (TransitionInfo.Change) next2;
                                                    ActivityManager.RunningTaskInfo taskInfo5 = change2.getTaskInfo();
                                                    if (taskInfo5 != null) {
                                                        DeskTransition.ActiveDeskWithTask activeDeskWithTask = (DeskTransition.ActiveDeskWithTask) deskTransition;
                                                        if (taskInfo5.taskId == activeDeskWithTask.enterTaskId && (taskInfo2 = change2.getTaskInfo()) != null && taskInfo2.isVisibleRequested && (deskAtEnd = ((RootTaskDesksOrganizer) desksOrganizer).getDeskAtEnd(change2)) != null && deskAtEnd.intValue() == activeDeskWithTask.deskId) {
                                                            obj2 = next2;
                                                        }
                                                    }
                                                }
                                            }
                                            if (((TransitionInfo.Change) obj2) != null) {
                                                DeskTransition.ActiveDeskWithTask activeDeskWithTask2 = (DeskTransition.ActiveDeskWithTask) deskTransition;
                                                int i5 = activeDeskWithTask2.displayId;
                                                int i6 = activeDeskWithTask2.deskId;
                                                current.setActiveDesk(i5, i6);
                                                current.addTaskToDesk(activeDeskWithTask2.displayId, i6, activeDeskWithTask2.enterTaskId, true);
                                            }
                                        } else if (deskTransition instanceof DeskTransition.DeactivateDesk) {
                                            desksTransitionObserver.handleDeactivateDeskTransition(transitionInfo2, (DeskTransition.DeactivateDesk) deskTransition);
                                        } else if (deskTransition instanceof DeskTransition.ChangeDeskDisplay) {
                                            DeskTransition.ChangeDeskDisplay changeDeskDisplay = (DeskTransition.ChangeDeskDisplay) deskTransition;
                                            DesksTransitionObserver.logD("handleChangeDeskDisplay: %s", changeDeskDisplay);
                                            int i7 = changeDeskDisplay.deskId;
                                            Iterator it3 = desktopUserRepositories.getRepositoriesWithDeskId(i7).iterator();
                                            while (it3.hasNext()) {
                                                ((DesktopRepository) it3.next()).onDeskDisplayChanged(i7, changeDeskDisplay.displayId);
                                            }
                                        } else {
                                            if (!(deskTransition instanceof DeskTransition.RemoveDisplay)) {
                                                throw new NoWhenBranchMatchedException();
                                            }
                                            final DeskTransition.RemoveDisplay removeDisplay = (DeskTransition.RemoveDisplay) deskTransition;
                                            DesksTransitionObserver.logD("handleRemoveDisplay: %s", removeDisplay);
                                            Function1 function1 = new Function1() { // from class: com.android.wm.shell.desktopmode.multidesks.DesksTransitionObserver$$ExternalSyntheticLambda0
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj3) {
                                                    DesktopRepository.DesktopData desktopData;
                                                    DesktopRepository desktopRepository = (DesktopRepository) obj3;
                                                    int i8 = DesksTransitionObserver.$r8$clinit;
                                                    int i9 = removeDisplay.displayId;
                                                    Set allDeskIds = desktopRepository.getAllDeskIds();
                                                    ArrayList arrayList = new ArrayList();
                                                    Iterator it4 = allDeskIds.iterator();
                                                    while (true) {
                                                        boolean zHasNext2 = it4.hasNext();
                                                        desktopData = desktopRepository.desktopData;
                                                        if (!zHasNext2) {
                                                            break;
                                                        }
                                                        Object next3 = it4.next();
                                                        if (desktopData.getDisplayForDesk(((Number) next3).intValue()) == i9) {
                                                            arrayList.add(next3);
                                                        }
                                                    }
                                                    int size = arrayList.size();
                                                    int i10 = 0;
                                                    while (i10 < size) {
                                                        Object obj4 = arrayList.get(i10);
                                                        i10++;
                                                        desktopRepository.removeDesk(((Number) obj4).intValue(), true);
                                                    }
                                                    desktopData.removeDisplay(i9);
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            DesktopUserRepositories$desktopRepoByUserId$1 desktopUserRepositories$desktopRepoByUserId$1 = desktopUserRepositories.desktopRepoByUserId;
                                            int size = desktopUserRepositories$desktopRepoByUserId$1.size();
                                            for (int i8 = 0; i8 < size; i8++) {
                                                desktopUserRepositories$desktopRepoByUserId$1.keyAt(i8);
                                                function1.mo781invoke((DesktopRepository) desktopUserRepositories$desktopRepoByUserId$1.valueAt(i8));
                                            }
                                        }
                                    } else {
                                        if (transitionInfo2.getType() != 2) {
                                            throw new IllegalStateException("Expected close transition for desk removal");
                                        }
                                        DeskTransition.RemoveDesk removeDesk = (DeskTransition.RemoveDesk) deskTransition;
                                        current.removeDesk(removeDesk.deskId, true);
                                        OnDeskRemovedListener onDeskRemovedListener2 = removeDesk.onDeskRemovedListener;
                                        if (onDeskRemovedListener2 != null) {
                                            ((DesktopDisplayEventHandler) onDeskRemovedListener2).createDefaultDesksIfNeeded(Collections.singleton(Integer.valueOf(removeDesk.displayId)), null);
                                        }
                                    }
                                    if ((deskTransition instanceof DeskTransition.ActivateDesk) || (deskTransition instanceof DeskTransition.ActiveDeskWithTask)) {
                                        linkedHashSet.add(deskTransition);
                                    }
                                }
                            }
                            desksTransitionObserver.activeDeskTransitions.put(iBinder2, linkedHashSet);
                            return;
                        }
                        return;
                    default:
                        IBinder iBinder3 = iBinder;
                        TransitionInfo transitionInfo3 = transitionInfo;
                        DesksTransitionObserver desksTransitionObserver2 = (DesksTransitionObserver) obj;
                        Set<DeskTransition> set2 = (Set) desksTransitionObserver2.activeDeskTransitions.remove(iBinder3);
                        DesktopRepository current2 = desksTransitionObserver2.desktopUserRepositories.getCurrent();
                        if (set2 != null) {
                            for (DeskTransition deskTransition2 : set2) {
                                if (deskTransition2 instanceof DeskTransition.ActivateDesk) {
                                    DeskTransition.ActivateDesk activateDesk2 = (DeskTransition.ActivateDesk) deskTransition2;
                                    current2.verifyFreeformTasksInZOrder(activateDesk2.deskId, DesksTransitionObserver.getToFrontTaskIdsInDesk(transitionInfo3, activateDesk2.deskId));
                                } else if (deskTransition2 instanceof DeskTransition.ActiveDeskWithTask) {
                                    DeskTransition.ActiveDeskWithTask activeDeskWithTask3 = (DeskTransition.ActiveDeskWithTask) deskTransition2;
                                    current2.verifyFreeformTasksInZOrder(activeDeskWithTask3.deskId, DesksTransitionObserver.getToFrontTaskIdsInDesk(transitionInfo3, activeDeskWithTask3.deskId));
                                }
                            }
                            return;
                        }
                        return;
                }
            }
        });
        if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue()) {
            this.mDesktopImmersiveController.ifPresent(new Consumer() { // from class: com.android.wm.shell.freeform.FreeformTaskTransitionObserver$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((DesktopImmersiveController) obj).onTransitionReady(iBinder, transitionInfo, transaction, transaction2);
                }
            });
        }
        final FocusTransitionObserver focusTransitionObserver = this.mFocusTransitionObserver;
        SparseArray sparseArrayClone = focusTransitionObserver.mFocusedTaskOnDisplay.clone();
        List changes = transitionInfo.getChanges();
        int i2 = 1;
        int size = changes.size() - 1;
        while (size >= 0) {
            TransitionInfo.Change change = (TransitionInfo.Change) changes.get(size);
            ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
            if (taskInfo2 != null) {
                if (change.hasFlags(1048576) || change.getMode() == i2) {
                    int i3 = taskInfo2.displayId;
                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) focusTransitionObserver.mFocusedTaskOnDisplay.get(i3);
                    if (runningTaskInfo != null) {
                        focusTransitionObserver.mTmpTasksToBeNotified.add(runningTaskInfo);
                    }
                    focusTransitionObserver.mTmpTasksToBeNotified.add(taskInfo2);
                    focusTransitionObserver.mFocusedTaskOnDisplay.put(i3, taskInfo2);
                } else {
                    boolean z = false;
                    int i4 = (change.getStartDisplayId() == -1 || change.getEndDisplayId() == -1 || change.getStartDisplayId() == change.getEndDisplayId()) ? 0 : i2;
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) sparseArrayClone.get(change.getStartDisplayId());
                    if (runningTaskInfo2 != null && taskInfo2.taskId == runningTaskInfo2.taskId) {
                        z = true;
                    }
                    if (change.getMode() == 6 && i4 != 0 && z) {
                        int endDisplayId = change.getEndDisplayId();
                        ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) focusTransitionObserver.mFocusedTaskOnDisplay.get(endDisplayId);
                        if (runningTaskInfo3 != null) {
                            focusTransitionObserver.mTmpTasksToBeNotified.add(runningTaskInfo3);
                        }
                        focusTransitionObserver.mTmpTasksToBeNotified.add(taskInfo2);
                        focusTransitionObserver.mFocusedTaskOnDisplay.put(endDisplayId, taskInfo2);
                    }
                }
            }
            if (change.hasFlags(32) && change.hasFlags(1048576) && focusTransitionObserver.mFocusedDisplayId != change.getEndDisplayId()) {
                int endDisplayId2 = change.getEndDisplayId();
                ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) focusTransitionObserver.mFocusedTaskOnDisplay.get(focusTransitionObserver.mFocusedDisplayId);
                if (runningTaskInfo4 != null) {
                    focusTransitionObserver.mTmpTasksToBeNotified.add(runningTaskInfo4);
                }
                focusTransitionObserver.mFocusedDisplayId = endDisplayId2;
                IFocusTransitionListener$Stub$Proxy iFocusTransitionListener$Stub$Proxy = focusTransitionObserver.mRemoteListener;
                if (iFocusTransitionListener$Stub$Proxy != null) {
                    try {
                        iFocusTransitionListener$Stub$Proxy.onFocusedDisplayChanged(endDisplayId2);
                    } catch (RemoteException e) {
                        Slog.w("FocusTransitionObserver", "Failed call notifyFocusedDisplayChangedToRemote", e);
                    }
                }
                ((HashMap) focusTransitionObserver.mLocalListeners).forEach(new BiConsumer() { // from class: com.android.wm.shell.transition.FocusTransitionObserver$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        FocusTransitionObserver focusTransitionObserver2 = focusTransitionObserver;
                        focusTransitionObserver2.getClass();
                        ((Executor) obj2).execute(new FocusTransitionObserver$$ExternalSyntheticLambda0(focusTransitionObserver2, (FocusTransitionListener) obj, 1));
                    }
                });
                ActivityManager.RunningTaskInfo runningTaskInfo5 = (ActivityManager.RunningTaskInfo) focusTransitionObserver.mFocusedTaskOnDisplay.get(focusTransitionObserver.mFocusedDisplayId);
                if (runningTaskInfo5 != null) {
                    focusTransitionObserver.mTmpTasksToBeNotified.add(runningTaskInfo5);
                }
            }
            size--;
            i2 = 1;
        }
        focusTransitionObserver.mTmpTasksToBeNotified.forEach(new FocusTransitionObserver$$ExternalSyntheticLambda1(focusTransitionObserver));
        focusTransitionObserver.mTmpTasksToBeNotified.clear();
        boolean z2 = CoreRune.MW_CAPTION;
        WindowDecorViewModel windowDecorViewModel = this.mWindowDecorViewModel;
        if (z2) {
            windowDecorViewModel.getClass();
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (TransitionInfo.Change change2 : transitionInfo.getChanges()) {
            if ((change2.getFlags() & 2) == 0 && (taskInfo = change2.getTaskInfo()) != null && taskInfo.taskId != -1) {
                if (change2.getParent() != null && transitionInfo.getChange(change2.getParent()).getTaskInfo() != null) {
                    arrayList2.add(change2.getParent());
                }
                if (!arrayList2.contains(change2.getContainer())) {
                    int mode = change2.getMode();
                    if (mode == 1) {
                        this.mTaskChangeListener.ifPresent(new FreeformTaskTransitionObserver$$ExternalSyntheticLambda4(change2, 1));
                        windowDecorViewModel.onTaskOpening(change2.getTaskInfo(), change2.getLeash(), transaction, transaction2);
                    } else if (mode == 2) {
                        arrayList.add(change2.getTaskInfo());
                        this.mTaskChangeListener.ifPresent(new FreeformTaskTransitionObserver$$ExternalSyntheticLambda4(change2, 0));
                        windowDecorViewModel.onTaskClosing(change2.getTaskInfo(), transaction, transaction2);
                    } else if (mode == 3) {
                        this.mTaskChangeListener.ifPresent(new FreeformTaskTransitionObserver$$ExternalSyntheticLambda4(change2, 2));
                        windowDecorViewModel.onTaskChanging(change2.getTaskInfo(), change2.getLeash(), transaction, transaction2);
                        if (CoreRune.MW_CAPTION) {
                            windowDecorViewModel.onTaskToFront(change2.getTaskInfo());
                        }
                    } else if (mode == 4) {
                        this.mTaskChangeListener.ifPresent(new FreeformTaskTransitionObserver$$ExternalSyntheticLambda4(change2, 4));
                        windowDecorViewModel.onTaskChanging(change2.getTaskInfo(), change2.getLeash(), transaction, transaction2);
                        if (CoreRune.MW_CAPTION) {
                            windowDecorViewModel.onTaskToBack(change2.getTaskInfo());
                        }
                    } else if (mode == 6) {
                        this.mTaskChangeListener.ifPresent(new FreeformTaskTransitionObserver$$ExternalSyntheticLambda4(change2, 3));
                        windowDecorViewModel.onTaskChanging(change2.getTaskInfo(), change2.getLeash(), transaction, transaction2);
                    }
                    if (CoreRune.MW_CAPTION) {
                        windowDecorViewModel.onDecorationTaskTransitionReady(iBinder, change2);
                    }
                }
            }
        }
        final int i5 = 1;
        this.mDesksTransitionObserver.ifPresent(new Consumer() { // from class: com.android.wm.shell.freeform.FreeformTaskTransitionObserver$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityManager.RunningTaskInfo taskInfo22;
                Integer deskAtEnd;
                int i22;
                Object next;
                ActivityManager.RunningTaskInfo taskInfo3;
                switch (i5) {
                    case 0:
                        IBinder iBinder2 = iBinder;
                        TransitionInfo transitionInfo2 = transitionInfo;
                        DesksTransitionObserver desksTransitionObserver = (DesksTransitionObserver) obj;
                        desksTransitionObserver.getClass();
                        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                            Set<DeskTransition> set = (Set) desksTransitionObserver.deskTransitions.remove(iBinder2);
                            Collection linkedHashSet = (Set) ((LinkedHashMap) desksTransitionObserver.activeDeskTransitions).get(iBinder2);
                            if (linkedHashSet == null) {
                                linkedHashSet = new LinkedHashSet();
                            }
                            if (set != null) {
                                for (DeskTransition deskTransition : set) {
                                    DesksTransitionObserver.logD("Desk transition ready: %s", deskTransition);
                                    DesktopUserRepositories desktopUserRepositories = desksTransitionObserver.desktopUserRepositories;
                                    DesktopRepository current = desktopUserRepositories.getCurrent();
                                    Object obj2 = null;
                                    if (!(deskTransition instanceof DeskTransition.RemoveDesk)) {
                                        boolean z3 = deskTransition instanceof DeskTransition.ActivateDesk;
                                        DesksOrganizer desksOrganizer = desksTransitionObserver.desksOrganizer;
                                        if (z3) {
                                            DeskTransition.ActivateDesk activateDesk = (DeskTransition.ActivateDesk) deskTransition;
                                            int i32 = activateDesk.userId;
                                            if (i32 == -1 || i32 == current.userId) {
                                                Iterator it = transitionInfo2.getChanges().iterator();
                                                while (true) {
                                                    boolean zHasNext = it.hasNext();
                                                    i22 = activateDesk.deskId;
                                                    if (zHasNext) {
                                                        next = it.next();
                                                        TransitionInfo.Change change3 = (TransitionInfo.Change) next;
                                                        change3.getClass();
                                                        ((RootTaskDesksOrganizer) desksOrganizer).getClass();
                                                        ActivityManager.RunningTaskInfo taskInfo4 = change3.getTaskInfo();
                                                        if (taskInfo4 == null || taskInfo4.taskId != i22 || (taskInfo3 = change3.getTaskInfo()) == null || !taskInfo3.isVisibleRequested || change3.getMode() != 3) {
                                                        }
                                                    } else {
                                                        next = null;
                                                    }
                                                }
                                                if (((TransitionInfo.Change) next) == null) {
                                                    DesksTransitionObserver.logD("Activating desk without transition change", new Object[0]);
                                                }
                                                int displayForDesk = current.desktopData.getDisplayForDesk(i22);
                                                int i42 = activateDesk.displayId;
                                                if (displayForDesk != i42) {
                                                    current.onDeskDisplayChanged(i22, i42);
                                                    OnDeskRemovedListener onDeskRemovedListener = activateDesk.onDeskRemovedListener;
                                                    if (onDeskRemovedListener != null) {
                                                        ((DesktopDisplayEventHandler) onDeskRemovedListener).createDefaultDesksIfNeeded(Collections.singleton(Integer.valueOf(displayForDesk)), null);
                                                    }
                                                }
                                                current.setActiveDesk(i42, i22);
                                            }
                                        } else if (deskTransition instanceof DeskTransition.ActiveDeskWithTask) {
                                            Iterator it2 = transitionInfo2.getChanges().iterator();
                                            while (true) {
                                                if (it2.hasNext()) {
                                                    Object next2 = it2.next();
                                                    TransitionInfo.Change change22 = (TransitionInfo.Change) next2;
                                                    ActivityManager.RunningTaskInfo taskInfo5 = change22.getTaskInfo();
                                                    if (taskInfo5 != null) {
                                                        DeskTransition.ActiveDeskWithTask activeDeskWithTask = (DeskTransition.ActiveDeskWithTask) deskTransition;
                                                        if (taskInfo5.taskId == activeDeskWithTask.enterTaskId && (taskInfo22 = change22.getTaskInfo()) != null && taskInfo22.isVisibleRequested && (deskAtEnd = ((RootTaskDesksOrganizer) desksOrganizer).getDeskAtEnd(change22)) != null && deskAtEnd.intValue() == activeDeskWithTask.deskId) {
                                                            obj2 = next2;
                                                        }
                                                    }
                                                }
                                            }
                                            if (((TransitionInfo.Change) obj2) != null) {
                                                DeskTransition.ActiveDeskWithTask activeDeskWithTask2 = (DeskTransition.ActiveDeskWithTask) deskTransition;
                                                int i52 = activeDeskWithTask2.displayId;
                                                int i6 = activeDeskWithTask2.deskId;
                                                current.setActiveDesk(i52, i6);
                                                current.addTaskToDesk(activeDeskWithTask2.displayId, i6, activeDeskWithTask2.enterTaskId, true);
                                            }
                                        } else if (deskTransition instanceof DeskTransition.DeactivateDesk) {
                                            desksTransitionObserver.handleDeactivateDeskTransition(transitionInfo2, (DeskTransition.DeactivateDesk) deskTransition);
                                        } else if (deskTransition instanceof DeskTransition.ChangeDeskDisplay) {
                                            DeskTransition.ChangeDeskDisplay changeDeskDisplay = (DeskTransition.ChangeDeskDisplay) deskTransition;
                                            DesksTransitionObserver.logD("handleChangeDeskDisplay: %s", changeDeskDisplay);
                                            int i7 = changeDeskDisplay.deskId;
                                            Iterator it3 = desktopUserRepositories.getRepositoriesWithDeskId(i7).iterator();
                                            while (it3.hasNext()) {
                                                ((DesktopRepository) it3.next()).onDeskDisplayChanged(i7, changeDeskDisplay.displayId);
                                            }
                                        } else {
                                            if (!(deskTransition instanceof DeskTransition.RemoveDisplay)) {
                                                throw new NoWhenBranchMatchedException();
                                            }
                                            final DeskTransition.RemoveDisplay removeDisplay = (DeskTransition.RemoveDisplay) deskTransition;
                                            DesksTransitionObserver.logD("handleRemoveDisplay: %s", removeDisplay);
                                            Function1 function1 = new Function1() { // from class: com.android.wm.shell.desktopmode.multidesks.DesksTransitionObserver$$ExternalSyntheticLambda0
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj3) {
                                                    DesktopRepository.DesktopData desktopData;
                                                    DesktopRepository desktopRepository = (DesktopRepository) obj3;
                                                    int i8 = DesksTransitionObserver.$r8$clinit;
                                                    int i9 = removeDisplay.displayId;
                                                    Set allDeskIds = desktopRepository.getAllDeskIds();
                                                    ArrayList arrayList3 = new ArrayList();
                                                    Iterator it4 = allDeskIds.iterator();
                                                    while (true) {
                                                        boolean zHasNext2 = it4.hasNext();
                                                        desktopData = desktopRepository.desktopData;
                                                        if (!zHasNext2) {
                                                            break;
                                                        }
                                                        Object next3 = it4.next();
                                                        if (desktopData.getDisplayForDesk(((Number) next3).intValue()) == i9) {
                                                            arrayList3.add(next3);
                                                        }
                                                    }
                                                    int size2 = arrayList3.size();
                                                    int i10 = 0;
                                                    while (i10 < size2) {
                                                        Object obj4 = arrayList3.get(i10);
                                                        i10++;
                                                        desktopRepository.removeDesk(((Number) obj4).intValue(), true);
                                                    }
                                                    desktopData.removeDisplay(i9);
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            DesktopUserRepositories$desktopRepoByUserId$1 desktopUserRepositories$desktopRepoByUserId$1 = desktopUserRepositories.desktopRepoByUserId;
                                            int size2 = desktopUserRepositories$desktopRepoByUserId$1.size();
                                            for (int i8 = 0; i8 < size2; i8++) {
                                                desktopUserRepositories$desktopRepoByUserId$1.keyAt(i8);
                                                function1.mo781invoke((DesktopRepository) desktopUserRepositories$desktopRepoByUserId$1.valueAt(i8));
                                            }
                                        }
                                    } else {
                                        if (transitionInfo2.getType() != 2) {
                                            throw new IllegalStateException("Expected close transition for desk removal");
                                        }
                                        DeskTransition.RemoveDesk removeDesk = (DeskTransition.RemoveDesk) deskTransition;
                                        current.removeDesk(removeDesk.deskId, true);
                                        OnDeskRemovedListener onDeskRemovedListener2 = removeDesk.onDeskRemovedListener;
                                        if (onDeskRemovedListener2 != null) {
                                            ((DesktopDisplayEventHandler) onDeskRemovedListener2).createDefaultDesksIfNeeded(Collections.singleton(Integer.valueOf(removeDesk.displayId)), null);
                                        }
                                    }
                                    if ((deskTransition instanceof DeskTransition.ActivateDesk) || (deskTransition instanceof DeskTransition.ActiveDeskWithTask)) {
                                        linkedHashSet.add(deskTransition);
                                    }
                                }
                            }
                            desksTransitionObserver.activeDeskTransitions.put(iBinder2, linkedHashSet);
                            return;
                        }
                        return;
                    default:
                        IBinder iBinder3 = iBinder;
                        TransitionInfo transitionInfo3 = transitionInfo;
                        DesksTransitionObserver desksTransitionObserver2 = (DesksTransitionObserver) obj;
                        Set<DeskTransition> set2 = (Set) desksTransitionObserver2.activeDeskTransitions.remove(iBinder3);
                        DesktopRepository current2 = desksTransitionObserver2.desktopUserRepositories.getCurrent();
                        if (set2 != null) {
                            for (DeskTransition deskTransition2 : set2) {
                                if (deskTransition2 instanceof DeskTransition.ActivateDesk) {
                                    DeskTransition.ActivateDesk activateDesk2 = (DeskTransition.ActivateDesk) deskTransition2;
                                    current2.verifyFreeformTasksInZOrder(activateDesk2.deskId, DesksTransitionObserver.getToFrontTaskIdsInDesk(transitionInfo3, activateDesk2.deskId));
                                } else if (deskTransition2 instanceof DeskTransition.ActiveDeskWithTask) {
                                    DeskTransition.ActiveDeskWithTask activeDeskWithTask3 = (DeskTransition.ActiveDeskWithTask) deskTransition2;
                                    current2.verifyFreeformTasksInZOrder(activeDeskWithTask3.deskId, DesksTransitionObserver.getToFrontTaskIdsInDesk(transitionInfo3, activeDeskWithTask3.deskId));
                                }
                            }
                            return;
                        }
                        return;
                }
            }
        });
        ((HashMap) this.mTransitionToTaskInfo).put(iBinder, arrayList);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionStarting(IBinder iBinder) {
        if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue()) {
            this.mDesktopImmersiveController.ifPresent(new FreeformTaskTransitionObserver$$ExternalSyntheticLambda13());
        }
        if (CoreRune.MW_CAPTION) {
            this.mWindowDecorViewModel.onTransitionStarting(iBinder);
        }
    }
}
