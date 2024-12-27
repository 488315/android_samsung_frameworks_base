package com.android.server.wm;

import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.DisplayAreaAppearedInfo;
import android.window.IDisplayAreaOrganizer;
import android.window.IDisplayAreaOrganizerController;
import android.window.WindowContainerToken;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DisplayAreaOrganizerController extends IDisplayAreaOrganizerController.Stub {
    public final WindowManagerGlobalLock mGlobalLock;
    public int mNextTaskDisplayAreaFeatureId = 20002;
    public final HashMap mOrganizersByFeatureIds = new HashMap();
    public final ActivityTaskManagerService mService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeathRecipient implements IBinder.DeathRecipient {
        public final int mFeature;
        public final IDisplayAreaOrganizer mOrganizer;

        public DeathRecipient(IDisplayAreaOrganizer iDisplayAreaOrganizer, int i) {
            this.mOrganizer = iDisplayAreaOrganizer;
            this.mFeature = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            WindowManagerGlobalLock windowManagerGlobalLock =
                    DisplayAreaOrganizerController.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayAreaOrganizerState displayAreaOrganizerState =
                            (DisplayAreaOrganizerState)
                                    DisplayAreaOrganizerController.this.mOrganizersByFeatureIds.get(
                                            Integer.valueOf(this.mFeature));
                    IDisplayAreaOrganizer iDisplayAreaOrganizer =
                            displayAreaOrganizerState != null
                                    ? displayAreaOrganizerState.mOrganizer
                                    : null;
                    if (iDisplayAreaOrganizer != null) {
                        IBinder asBinder = iDisplayAreaOrganizer.asBinder();
                        if (!asBinder.equals(this.mOrganizer.asBinder())
                                && asBinder.isBinderAlive()) {
                            Slog.d(
                                    "DisplayAreaOrganizerController",
                                    "Dead organizer replaced for feature=" + this.mFeature);
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        ((DisplayAreaOrganizerState)
                                        DisplayAreaOrganizerController.this.mOrganizersByFeatureIds
                                                .remove(Integer.valueOf(this.mFeature)))
                                .destroy();
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayAreaOrganizerState {
        public final DeathRecipient mDeathRecipient;
        public final IDisplayAreaOrganizer mOrganizer;

        public DisplayAreaOrganizerState(IDisplayAreaOrganizer iDisplayAreaOrganizer, int i) {
            this.mOrganizer = iDisplayAreaOrganizer;
            DeathRecipient deathRecipient =
                    DisplayAreaOrganizerController.this
                    .new DeathRecipient(iDisplayAreaOrganizer, i);
            this.mDeathRecipient = deathRecipient;
            try {
                iDisplayAreaOrganizer.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException unused) {
            }
        }

        public final void destroy() {
            final IBinder asBinder = this.mOrganizer.asBinder();
            DisplayAreaOrganizerController.this.mService.mRootWindowContainer.forAllDisplayAreas(
                    new Consumer() { // from class:
                                     // com.android.server.wm.DisplayAreaOrganizerController$DisplayAreaOrganizerState$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            DisplayAreaOrganizerController.DisplayAreaOrganizerState
                                    displayAreaOrganizerState =
                                            DisplayAreaOrganizerController.DisplayAreaOrganizerState
                                                    .this;
                            IBinder iBinder = asBinder;
                            DisplayArea displayArea = (DisplayArea) obj;
                            displayAreaOrganizerState.getClass();
                            IDisplayAreaOrganizer iDisplayAreaOrganizer = displayArea.mOrganizer;
                            if (iDisplayAreaOrganizer == null
                                    || !iDisplayAreaOrganizer.asBinder().equals(iBinder)) {
                                return;
                            }
                            if (!displayArea.isTaskDisplayArea()
                                    || !displayArea.asTaskDisplayArea().mCreatedByOrganizer) {
                                displayArea.setOrganizer(null);
                                return;
                            }
                            TaskDisplayArea asTaskDisplayArea = displayArea.asTaskDisplayArea();
                            DisplayAreaOrganizerController displayAreaOrganizerController =
                                    DisplayAreaOrganizerController.this;
                            displayAreaOrganizerController.getClass();
                            asTaskDisplayArea.setOrganizer(null);
                            displayAreaOrganizerController.mService.mRootWindowContainer
                                    .mTaskSupervisor.beginDeferResume();
                            try {
                                Task remove = asTaskDisplayArea.remove();
                                displayAreaOrganizerController.mService.mRootWindowContainer
                                        .mTaskSupervisor.endDeferResume();
                                asTaskDisplayArea.removeImmediately();
                                if (remove != null) {
                                    remove.resumeNextFocusAfterReparent();
                                }
                            } catch (Throwable th) {
                                displayAreaOrganizerController.mService.mRootWindowContainer
                                        .mTaskSupervisor.endDeferResume();
                                throw th;
                            }
                        }
                    });
            asBinder.unlinkToDeath(this.mDeathRecipient, 0);
        }
    }

    public DisplayAreaOrganizerController(ActivityTaskManagerService activityTaskManagerService) {
        this.mService = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
    }

    public static TaskDisplayArea createTaskDisplayArea(
            final RootDisplayArea rootDisplayArea, String str, int i) {
        TaskDisplayArea taskDisplayArea =
                new TaskDisplayArea(
                        rootDisplayArea.mDisplayContent, rootDisplayArea.mWmService, str, i, true);
        DisplayArea displayArea =
                (DisplayArea)
                        rootDisplayArea.getItemFromDisplayAreas(
                                new Function() { // from class:
                                                 // com.android.server.wm.DisplayAreaOrganizerController$$ExternalSyntheticLambda4
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        RootDisplayArea rootDisplayArea2;
                                        RootDisplayArea rootDisplayArea3 = RootDisplayArea.this;
                                        DisplayArea displayArea2 = (DisplayArea) obj;
                                        if (displayArea2.mType == DisplayArea.Type.ANY
                                                && ((rootDisplayArea2 =
                                                                        displayArea2
                                                                                .getRootDisplayArea())
                                                                == rootDisplayArea3
                                                        || rootDisplayArea2 == displayArea2)) {
                                            return displayArea2;
                                        }
                                        return null;
                                    }
                                });
        if (displayArea != null) {
            WindowContainer parent = displayArea.getParent();
            parent.addChild(taskDisplayArea, parent.mChildren.indexOf(displayArea) + 1);
            return taskDisplayArea;
        }
        throw new IllegalStateException(
                "Root must either contain TDA or DAG root=" + rootDisplayArea);
    }

    public final DisplayAreaAppearedInfo createTaskDisplayArea(
            IDisplayAreaOrganizer iDisplayAreaOrganizer, int i, final int i2, String str) {
        TaskDisplayArea taskDisplayArea;
        TaskDisplayArea taskDisplayArea2;
        DisplayAreaAppearedInfo displayAreaAppearedInfo;
        ActivityTaskManagerService.enforceTaskPermission("createTaskDisplayArea()");
        long callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                5147103403966149923L,
                                1,
                                null,
                                Long.valueOf(callingUid));
                    }
                    DisplayContent displayContent =
                            this.mService.mRootWindowContainer.getDisplayContent(i);
                    if (displayContent == null) {
                        throw new IllegalArgumentException(
                                "createTaskDisplayArea unknown displayId=" + i);
                    }
                    if (!displayContent.mDisplay.isTrusted()) {
                        throw new IllegalArgumentException(
                                "createTaskDisplayArea untrusted displayId=" + i);
                    }
                    final int i3 = 0;
                    RootDisplayArea rootDisplayArea =
                            (RootDisplayArea)
                                    displayContent.getItemFromDisplayAreas(
                                            new Function() { // from class:
                                                             // com.android.server.wm.DisplayAreaOrganizerController$$ExternalSyntheticLambda1
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    int i4 = i3;
                                                    int i5 = i2;
                                                    switch (i4) {
                                                        case 0:
                                                            DisplayArea displayArea =
                                                                    (DisplayArea) obj;
                                                            if (displayArea.asRootDisplayArea()
                                                                            == null
                                                                    || displayArea.mFeatureId
                                                                            != i5) {
                                                                return null;
                                                            }
                                                            return displayArea.asRootDisplayArea();
                                                        default:
                                                            TaskDisplayArea taskDisplayArea3 =
                                                                    (TaskDisplayArea) obj;
                                                            if (taskDisplayArea3.mFeatureId == i5) {
                                                                return taskDisplayArea3;
                                                            }
                                                            return null;
                                                    }
                                                }
                                            });
                    if (rootDisplayArea == null) {
                        final int i4 = 1;
                        taskDisplayArea =
                                (TaskDisplayArea)
                                        displayContent.getItemFromTaskDisplayAreas(
                                                new Function() { // from class:
                                                                 // com.android.server.wm.DisplayAreaOrganizerController$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        int i42 = i4;
                                                        int i5 = i2;
                                                        switch (i42) {
                                                            case 0:
                                                                DisplayArea displayArea =
                                                                        (DisplayArea) obj;
                                                                if (displayArea.asRootDisplayArea()
                                                                                == null
                                                                        || displayArea.mFeatureId
                                                                                != i5) {
                                                                    return null;
                                                                }
                                                                return displayArea
                                                                        .asRootDisplayArea();
                                                            default:
                                                                TaskDisplayArea taskDisplayArea3 =
                                                                        (TaskDisplayArea) obj;
                                                                if (taskDisplayArea3.mFeatureId
                                                                        == i5) {
                                                                    return taskDisplayArea3;
                                                                }
                                                                return null;
                                                        }
                                                    }
                                                });
                    } else {
                        taskDisplayArea = null;
                    }
                    if (rootDisplayArea == null && taskDisplayArea == null) {
                        throw new IllegalArgumentException(
                                "Can't find a parent DisplayArea with featureId=" + i2);
                    }
                    int i5 = this.mNextTaskDisplayAreaFeatureId;
                    this.mNextTaskDisplayAreaFeatureId = i5 + 1;
                    DisplayAreaOrganizerState displayAreaOrganizerState =
                            new DisplayAreaOrganizerState(iDisplayAreaOrganizer, i5);
                    if (rootDisplayArea != null) {
                        taskDisplayArea2 = createTaskDisplayArea(rootDisplayArea, str, i5);
                    } else {
                        taskDisplayArea2 =
                                new TaskDisplayArea(
                                        taskDisplayArea.mDisplayContent,
                                        taskDisplayArea.mWmService,
                                        str,
                                        i5,
                                        true);
                        taskDisplayArea.addChild(taskDisplayArea2, Integer.MAX_VALUE);
                    }
                    taskDisplayArea2.setOrganizer(iDisplayAreaOrganizer, true);
                    displayAreaAppearedInfo =
                            new DisplayAreaAppearedInfo(
                                    taskDisplayArea2.getDisplayAreaInfo(),
                                    new SurfaceControl(
                                            taskDisplayArea2.getSurfaceControl(),
                                            "DisplayAreaOrganizerController.createTaskDisplayArea"));
                    this.mOrganizersByFeatureIds.put(
                            Integer.valueOf(i5), displayAreaOrganizerState);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return displayAreaAppearedInfo;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void deleteTaskDisplayArea(WindowContainerToken windowContainerToken) {
        ActivityTaskManagerService.enforceTaskPermission("deleteTaskDisplayArea()");
        long callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                -1659480097203667175L,
                                1,
                                null,
                                Long.valueOf(callingUid));
                    }
                    WindowContainer fromBinder =
                            WindowContainer.fromBinder(windowContainerToken.asBinder());
                    if (fromBinder == null || fromBinder.asTaskDisplayArea() == null) {
                        throw new IllegalArgumentException(
                                "Can't resolve TaskDisplayArea from token");
                    }
                    TaskDisplayArea asTaskDisplayArea = fromBinder.asTaskDisplayArea();
                    if (!asTaskDisplayArea.mCreatedByOrganizer) {
                        throw new IllegalArgumentException(
                                "Attempt to delete TaskDisplayArea not created by organizer"
                                    + " TaskDisplayArea="
                                        + asTaskDisplayArea);
                    }
                    ((DisplayAreaOrganizerState)
                                    this.mOrganizersByFeatureIds.remove(
                                            Integer.valueOf(asTaskDisplayArea.mFeatureId)))
                            .destroy();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ParceledListSlice registerOrganizer(
            IDisplayAreaOrganizer iDisplayAreaOrganizer, int i) {
        ParceledListSlice parceledListSlice;
        ActivityTaskManagerService.enforceTaskPermission("registerOrganizer()");
        long callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                3968604152682328317L,
                                4,
                                null,
                                String.valueOf(iDisplayAreaOrganizer.asBinder()),
                                Long.valueOf(callingUid));
                    }
                    if (this.mOrganizersByFeatureIds.get(Integer.valueOf(i)) != null) {
                        ((DisplayAreaOrganizerState)
                                        this.mOrganizersByFeatureIds.remove(Integer.valueOf(i)))
                                .destroy();
                        Slog.d(
                                "DisplayAreaOrganizerController",
                                "Replacing dead organizer for feature=" + i);
                    }
                    DisplayAreaOrganizerState displayAreaOrganizerState =
                            new DisplayAreaOrganizerState(iDisplayAreaOrganizer, i);
                    ArrayList arrayList = new ArrayList();
                    this.mService.mRootWindowContainer.forAllDisplays(
                            new DisplayAreaOrganizerController$$ExternalSyntheticLambda5(
                                    this, i, arrayList, iDisplayAreaOrganizer, 1));
                    this.mOrganizersByFeatureIds.put(Integer.valueOf(i), displayAreaOrganizerState);
                    parceledListSlice = new ParceledListSlice(arrayList);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return parceledListSlice;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void unregisterOrganizer(final IDisplayAreaOrganizer iDisplayAreaOrganizer) {
        ActivityTaskManagerService.enforceTaskPermission("unregisterTaskOrganizer()");
        long callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                -943497726140336963L,
                                4,
                                null,
                                String.valueOf(iDisplayAreaOrganizer.asBinder()),
                                Long.valueOf(callingUid));
                    }
                    this.mOrganizersByFeatureIds
                            .entrySet()
                            .removeIf(
                                    new Predicate() { // from class:
                                                      // com.android.server.wm.DisplayAreaOrganizerController$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            Map.Entry entry = (Map.Entry) obj;
                                            boolean equals =
                                                    ((DisplayAreaOrganizerController
                                                                            .DisplayAreaOrganizerState)
                                                                    entry.getValue())
                                                            .mOrganizer
                                                            .asBinder()
                                                            .equals(
                                                                    iDisplayAreaOrganizer
                                                                            .asBinder());
                                            if (equals) {
                                                ((DisplayAreaOrganizerController
                                                                        .DisplayAreaOrganizerState)
                                                                entry.getValue())
                                                        .destroy();
                                            }
                                            return equals;
                                        }
                                    });
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
