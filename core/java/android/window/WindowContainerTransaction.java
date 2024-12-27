package android.window;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.view.InsetsFrameProvider;
import android.view.SurfaceControl;
import android.window.ITaskFragmentOrganizer;
import android.window.TaskFragmentOperation;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class WindowContainerTransaction implements Parcelable {
    public static final int ADDITIONAL_FLAG_KEEP_ROTATION_DURING_TRANSITION = 1;
    public static final int CHANGE_TRANSIT_REQUEST_FULLSCREEN_TO_SPLIT = 1;
    public static final int CHANGE_TRANSIT_REQUEST_FULLSCREEN_TO_SPLIT_ROTATION = 3;
    public static final int CHANGE_TRANSIT_REQUEST_SPLIT_TO_FULLSCREEN = 2;
    public static final int CHANGE_TRANSIT_REQUEST_UNDEFINED = 0;
    public static final Parcelable.Creator<WindowContainerTransaction> CREATOR = new Parcelable.Creator<WindowContainerTransaction>() { // from class: android.window.WindowContainerTransaction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContainerTransaction createFromParcel(Parcel in) {
            return new WindowContainerTransaction(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContainerTransaction[] newArray(int size) {
            return new WindowContainerTransaction[size];
        }
    };
    public static final int TRANSACTION_TYPE_DISMISS_SPLIT_WITH_ALL_APPS = 7;
    public static final int TRANSACTION_TYPE_DISMISS_SPLIT_WITH_FREEFORM = 6;
    public static final int TRANSACTION_TYPE_START_INTENTS = 1;
    public static final int TRANSACTION_TYPE_START_TASKS = 3;
    public static final int TRANSACTION_TYPE_START_TASKS_FROM_RECENT = 5;
    public static final int TRANSACTION_TYPE_START_TASK_AND_INTENT = 2;
    public static final int TRANSACTION_TYPE_UNDEFINED = 0;
    public static final int TRANSACTION_TYPE_UPDATE_DESKTOP_MODE_ACTIVE = 4;
    private int mAdditionalFlag;
    private boolean mAvoidReady;
    private final ArrayList<ContainerChange> mChangeList;
    private int mChangeTransitionRequest;
    private final ArrayMap<IBinder, Change> mChanges;
    private boolean mDismissSplit;
    private String mDisplayChangeTransitionReason;
    private int mDisplayIdForChangeTransition;
    private IBinder mErrorCallbackToken;
    private final ArrayList<HierarchyOp> mHierarchyOps;
    private ArrayList<IBinder> mMergedTransitionTokens;
    private boolean mPositionChange;
    private ITaskFragmentOrganizer mTaskFragmentOrganizer;
    private ArrayMap<SurfaceControl, SurfaceControl> mTransferLeashMap;
    private ArrayList<IBinder> mTransferTransitionTokens;
    private int mType;

    public @interface AdditionalFlags {
    }

    public @interface ChangeTransitRequest {
    }

    public static String changeTransitRequestToString(int changeTransitRequest) {
        switch (changeTransitRequest) {
            case 0:
                return "CHANGE_TRANSIT_REQUEST_UNDEFINED";
            case 1:
                return "CHANGE_TRANSIT_REQUEST_FULLSCREEN_TO_SPLIT";
            case 2:
                return "CHANGE_TRANSIT_REQUEST_SPLIT_TO_FULLSCREEN";
            default:
                return Integer.toString(changeTransitRequest);
        }
    }

    public boolean avoidReady() {
        return this.mAvoidReady;
    }

    public void setAvoidReady() {
        this.mAvoidReady = true;
    }

    public void addAdditionalInfo(int flag) {
        this.mAdditionalFlag |= flag;
    }

    public int getAdditionalFlags() {
        return this.mAdditionalFlag;
    }

    public WindowContainerTransaction() {
        this.mChanges = new ArrayMap<>();
        this.mHierarchyOps = new ArrayList<>();
        this.mType = 0;
        this.mPositionChange = false;
        this.mDismissSplit = false;
        this.mTransferLeashMap = new ArrayMap<>();
        this.mMergedTransitionTokens = new ArrayList<>();
        this.mTransferTransitionTokens = new ArrayList<>();
        this.mChangeTransitionRequest = 0;
        this.mDisplayIdForChangeTransition = -1;
        this.mChangeList = new ArrayList<>();
    }

    private WindowContainerTransaction(Parcel in) {
        this.mChanges = new ArrayMap<>();
        this.mHierarchyOps = new ArrayList<>();
        this.mType = 0;
        this.mPositionChange = false;
        this.mDismissSplit = false;
        this.mTransferLeashMap = new ArrayMap<>();
        this.mMergedTransitionTokens = new ArrayList<>();
        this.mTransferTransitionTokens = new ArrayList<>();
        this.mChangeTransitionRequest = 0;
        this.mDisplayIdForChangeTransition = -1;
        this.mChangeList = new ArrayList<>();
        in.readMap(this.mChanges, null);
        in.readTypedList(this.mHierarchyOps, HierarchyOp.CREATOR);
        this.mErrorCallbackToken = in.readStrongBinder();
        this.mTaskFragmentOrganizer = ITaskFragmentOrganizer.Stub.asInterface(in.readStrongBinder());
        this.mType = in.readInt();
        this.mPositionChange = in.readBoolean();
        this.mDismissSplit = in.readBoolean();
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            this.mChangeTransitionRequest = in.readInt();
        }
        if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
            this.mDisplayIdForChangeTransition = in.readInt();
            this.mDisplayChangeTransitionReason = in.readString();
        }
        in.readTypedList(this.mChangeList, ContainerChange.CREATOR);
        if (CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER) {
            in.readMap(this.mTransferLeashMap, null);
        }
        if (CoreRune.MW_SHELL_TRANSITION) {
            this.mAvoidReady = in.readBoolean();
            this.mAdditionalFlag = in.readInt();
        }
        if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX) {
            this.mTransferTransitionTokens = in.readArrayList(null, IBinder.class);
            this.mMergedTransitionTokens = in.readArrayList(null, IBinder.class);
        }
    }

    public WindowContainerTransaction orderedSetWindowingMode(WindowContainerToken container, int windowingMode) {
        ContainerChange cc = new ContainerChange();
        cc.mToken = container.asBinder();
        cc.mChange = new Change();
        cc.mChange.mWindowingMode = windowingMode;
        this.mChangeList.add(cc);
        return this;
    }

    public WindowContainerTransaction orderedSetChangeTransitMode(WindowContainerToken container, int changeTransitMode, String reason) {
        ContainerChange cc = new ContainerChange();
        cc.mToken = container.asBinder();
        cc.mChange = new Change();
        cc.mChange.mChangeTransitMode = changeTransitMode;
        cc.mChange.mChangeTransitReason = reason;
        this.mChangeList.add(cc);
        return this;
    }

    public List<ContainerChange> getChangeList() {
        return this.mChangeList;
    }

    private Change getOrCreateChange(IBinder token) {
        Change out = this.mChanges.get(token);
        if (out == null) {
            Change out2 = new Change();
            this.mChanges.put(token, out2);
            return out2;
        }
        return out;
    }

    public void clear() {
        this.mChanges.clear();
        this.mHierarchyOps.clear();
        this.mErrorCallbackToken = null;
        this.mTaskFragmentOrganizer = null;
    }

    public WindowContainerTransaction setBounds(WindowContainerToken container, Rect bounds) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mConfiguration.windowConfiguration.setBounds(bounds);
        chg.mConfigSetMask |= 536870912;
        chg.mWindowSetMask |= 1;
        return this;
    }

    public WindowContainerTransaction setAppBounds(WindowContainerToken container, Rect appBounds) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mConfiguration.windowConfiguration.setAppBounds(appBounds);
        chg.mConfigSetMask |= 536870912;
        chg.mWindowSetMask |= 2;
        return this;
    }

    public WindowContainerTransaction setScreenSizeDp(WindowContainerToken container, int w, int h) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mConfiguration.screenWidthDp = w;
        chg.mConfiguration.screenHeightDp = h;
        chg.mConfigSetMask |= 1024;
        return this;
    }

    public WindowContainerTransaction setDensityDpi(WindowContainerToken container, int densityDpi) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mConfiguration.densityDpi = densityDpi;
        chg.mConfigSetMask |= 4096;
        return this;
    }

    public WindowContainerTransaction scheduleFinishEnterPip(WindowContainerToken container, Rect bounds) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mPinnedBounds = new Rect(bounds);
        chg.mChangeMask |= 4;
        return this;
    }

    public WindowContainerTransaction setStagePosition(WindowContainerToken container, int stagePosition) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mConfiguration.windowConfiguration.setStagePosition(stagePosition);
        chg.mConfigSetMask |= 536870912;
        chg.mWindowSetMask |= 2097152;
        return this;
    }

    public WindowContainerTransaction setEmbedActivityMode(WindowContainerToken container, int embedActivityMode) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mConfiguration.windowConfiguration.setEmbedActivityMode(embedActivityMode);
        chg.mConfigSetMask |= 536870912;
        chg.mWindowSetMask |= 8388608;
        return this;
    }

    public WindowContainerTransaction setBoundsChangeTransaction(WindowContainerToken container, SurfaceControl.Transaction t) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mBoundsChangeTransaction = t;
        chg.mChangeMask |= 2;
        return this;
    }

    public WindowContainerTransaction setBoundsChangeTransaction(WindowContainerToken task, Rect surfaceBounds) {
        Change chg = getOrCreateChange(task.asBinder());
        if (chg.mBoundsChangeSurfaceBounds == null) {
            chg.mBoundsChangeSurfaceBounds = new Rect();
        }
        chg.mBoundsChangeSurfaceBounds.set(surfaceBounds);
        chg.mChangeMask |= 16;
        return this;
    }

    public WindowContainerTransaction setActivityWindowingMode(WindowContainerToken container, int windowingMode) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mActivityWindowingMode = windowingMode;
        return this;
    }

    public WindowContainerTransaction setWindowingMode(WindowContainerToken container, int windowingMode) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mWindowingMode = windowingMode;
        return this;
    }

    public WindowContainerTransaction setFocusable(WindowContainerToken container, boolean focusable) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mFocusable = focusable;
        chg.mChangeMask |= 1;
        return this;
    }

    public WindowContainerTransaction setHidden(WindowContainerToken container, boolean hidden) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mHidden = hidden;
        chg.mChangeMask |= 8;
        return this;
    }

    public WindowContainerTransaction setSmallestScreenWidthDp(WindowContainerToken container, int widthDp) {
        Change cfg = getOrCreateChange(container.asBinder());
        cfg.mConfiguration.smallestScreenWidthDp = widthDp;
        cfg.mConfigSetMask |= 2048;
        return this;
    }

    public WindowContainerTransaction setIgnoreOrientationRequest(WindowContainerToken container, boolean ignoreOrientationRequest) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mIgnoreOrientationRequest = ignoreOrientationRequest;
        chg.mChangeMask |= 32;
        return this;
    }

    public WindowContainerTransaction setForceTranslucent(WindowContainerToken container, boolean forceTranslucent) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mForceTranslucent = forceTranslucent;
        chg.mChangeMask |= 128;
        return this;
    }

    public WindowContainerTransaction setFreeformTranslucent(WindowContainerToken container, int translucent) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mConfiguration.windowConfiguration.setFreeformTranslucent(translucent);
        chg.mConfigSetMask |= 536870912;
        chg.mWindowSetMask |= 67108864;
        return this;
    }

    public WindowContainerTransaction setDoNotPip(WindowContainerToken container) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mChangeMask |= 64;
        return this;
    }

    public WindowContainerTransaction setRelativeBounds(WindowContainerToken container, Rect relBounds) {
        Change chg = getOrCreateChange(container.asBinder());
        if (chg.mRelativeBounds == null) {
            chg.mRelativeBounds = new Rect();
        }
        chg.mRelativeBounds.set(relBounds);
        chg.mChangeMask |= 512;
        chg.mConfigSetMask |= 536870912;
        chg.mWindowSetMask |= 1;
        return this;
    }

    public WindowContainerTransaction reparent(WindowContainerToken child, WindowContainerToken parent, boolean onTop) {
        this.mHierarchyOps.add(HierarchyOp.createForReparent(child.asBinder(), parent == null ? null : parent.asBinder(), onTop));
        return this;
    }

    public WindowContainerTransaction reorder(WindowContainerToken child, boolean onTop) {
        return reorder(child, onTop, false);
    }

    public WindowContainerTransaction reorder(WindowContainerToken child, boolean onTop, boolean includingParents) {
        this.mHierarchyOps.add(HierarchyOp.createForReorder(child.asBinder(), onTop, includingParents));
        return this;
    }

    public WindowContainerTransaction reparentTasks(WindowContainerToken currentParent, WindowContainerToken newParent, int[] windowingModes, int[] activityTypes, boolean onTop, boolean reparentTopOnly) {
        this.mHierarchyOps.add(HierarchyOp.createForChildrenTasksReparent(currentParent != null ? currentParent.asBinder() : null, newParent != null ? newParent.asBinder() : null, windowingModes, activityTypes, onTop, reparentTopOnly));
        return this;
    }

    public WindowContainerTransaction reparentTasks(WindowContainerToken currentParent, WindowContainerToken newParent, int[] windowingModes, int[] activityTypes, boolean onTop) {
        return reparentTasks(currentParent, newParent, windowingModes, activityTypes, onTop, false);
    }

    public WindowContainerTransaction setLaunchRoot(WindowContainerToken container, int[] windowingModes, int[] activityTypes) {
        this.mHierarchyOps.add(HierarchyOp.createForSetLaunchRoot(container.asBinder(), windowingModes, activityTypes));
        return this;
    }

    public WindowContainerTransaction setAdjacentRoots(WindowContainerToken root1, WindowContainerToken root2) {
        this.mHierarchyOps.add(HierarchyOp.createForAdjacentRoots(root1.asBinder(), root2.asBinder()));
        return this;
    }

    public WindowContainerTransaction setLaunchAdjacentFlagRoot(WindowContainerToken container) {
        this.mHierarchyOps.add(HierarchyOp.createForSetLaunchAdjacentFlagRoot(container.asBinder(), false));
        return this;
    }

    public WindowContainerTransaction clearLaunchAdjacentFlagRoot(WindowContainerToken container) {
        this.mHierarchyOps.add(HierarchyOp.createForSetLaunchAdjacentFlagRoot(container.asBinder(), true));
        return this;
    }

    public WindowContainerTransaction startTask(int taskId, Bundle options) {
        this.mHierarchyOps.add(HierarchyOp.createForTaskLaunch(taskId, options));
        return this;
    }

    public WindowContainerTransaction removeTask(WindowContainerToken containerToken) {
        this.mHierarchyOps.add(HierarchyOp.createForRemoveTask(containerToken.asBinder()));
        return this;
    }

    public WindowContainerTransaction closeTask(WindowContainerToken containerToken) {
        this.mHierarchyOps.add(HierarchyOp.createForCloseTask(containerToken.asBinder()));
        return this;
    }

    public WindowContainerTransaction setDragResizing(WindowContainerToken container, boolean dragResizing) {
        Change change = getOrCreateChange(container.asBinder());
        change.mChangeMask |= 256;
        change.mDragResizing = dragResizing;
        return this;
    }

    public WindowContainerTransaction sendPendingIntent(PendingIntent sender, Intent intent, Bundle options) {
        this.mHierarchyOps.add(new HierarchyOp.Builder(7).setLaunchOptions(options).setPendingIntent(sender).setActivityIntent(intent).build());
        return this;
    }

    public WindowContainerTransaction startShortcut(String callingPackage, ShortcutInfo shortcutInfo, Bundle options) {
        this.mHierarchyOps.add(HierarchyOp.createForStartShortcut(callingPackage, shortcutInfo, options));
        return this;
    }

    public WindowContainerTransaction createTaskFragment(TaskFragmentCreationParams taskFragmentCreationParams) {
        TaskFragmentOperation operation = new TaskFragmentOperation.Builder(0).setTaskFragmentCreationParams(taskFragmentCreationParams).build();
        return addTaskFragmentOperation(taskFragmentCreationParams.getFragmentToken(), operation);
    }

    public WindowContainerTransaction deleteTaskFragment(IBinder fragmentToken) {
        TaskFragmentOperation operation = new TaskFragmentOperation.Builder(1).build();
        return addTaskFragmentOperation(fragmentToken, operation);
    }

    public WindowContainerTransaction startActivityInTaskFragment(IBinder fragmentToken, IBinder callerToken, Intent activityIntent, Bundle activityOptions) {
        TaskFragmentOperation operation = new TaskFragmentOperation.Builder(2).setActivityToken(callerToken).setActivityIntent(activityIntent).setBundle(activityOptions).build();
        return addTaskFragmentOperation(fragmentToken, operation);
    }

    public WindowContainerTransaction reparentActivityToTaskFragment(IBinder fragmentToken, IBinder activityToken) {
        TaskFragmentOperation operation = new TaskFragmentOperation.Builder(3).setActivityToken(activityToken).build();
        return addTaskFragmentOperation(fragmentToken, operation);
    }

    public WindowContainerTransaction setAdjacentTaskFragments(IBinder fragmentToken1, IBinder fragmentToken2, TaskFragmentAdjacentParams params) {
        TaskFragmentOperation operation = new TaskFragmentOperation.Builder(4).setSecondaryFragmentToken(fragmentToken2).setBundle(params != null ? params.toBundle() : null).build();
        return addTaskFragmentOperation(fragmentToken1, operation);
    }

    public WindowContainerTransaction clearAdjacentTaskFragments(IBinder fragmentToken) {
        TaskFragmentOperation operation = new TaskFragmentOperation.Builder(5).build();
        return addTaskFragmentOperation(fragmentToken, operation);
    }

    public WindowContainerTransaction restoreTransientOrder(WindowContainerToken container) {
        HierarchyOp hierarchyOp = new HierarchyOp.Builder(9).setContainer(container.asBinder()).build();
        this.mHierarchyOps.add(hierarchyOp);
        return this;
    }

    public WindowContainerTransaction addInsetsSource(WindowContainerToken receiver, IBinder owner, int index, int type, Rect frame, Rect[] boundingRects, int flags) {
        HierarchyOp hierarchyOp = new HierarchyOp.Builder(10).setContainer(receiver.asBinder()).setInsetsFrameProvider(new InsetsFrameProvider(owner, index, type).setSource(3).setArbitraryRectangle(frame).setBoundingRects(boundingRects).setFlags(flags)).setInsetsFrameOwner(owner).build();
        this.mHierarchyOps.add(hierarchyOp);
        return this;
    }

    public WindowContainerTransaction removeInsetsSource(WindowContainerToken receiver, IBinder owner, int index, int type) {
        HierarchyOp hierarchyOp = new HierarchyOp.Builder(11).setContainer(receiver.asBinder()).setInsetsFrameProvider(new InsetsFrameProvider(owner, index, type)).setInsetsFrameOwner(owner).build();
        this.mHierarchyOps.add(hierarchyOp);
        return this;
    }

    public WindowContainerTransaction requestFocusOnTaskFragment(IBinder fragmentToken) {
        TaskFragmentOperation operation = new TaskFragmentOperation.Builder(6).build();
        return addTaskFragmentOperation(fragmentToken, operation);
    }

    public WindowContainerTransaction finishActivity(IBinder activityToken) {
        HierarchyOp hierarchyOp = new HierarchyOp.Builder(14).setContainer(activityToken).build();
        this.mHierarchyOps.add(hierarchyOp);
        return this;
    }

    public WindowContainerTransaction setCompanionTaskFragment(IBinder fragmentToken, IBinder companionFragmentToken) {
        TaskFragmentOperation operation = new TaskFragmentOperation.Builder(7).setSecondaryFragmentToken(companionFragmentToken).build();
        return addTaskFragmentOperation(fragmentToken, operation);
    }

    public WindowContainerTransaction addTaskFragmentOperation(IBinder fragmentToken, TaskFragmentOperation taskFragmentOperation) {
        Objects.requireNonNull(fragmentToken);
        Objects.requireNonNull(taskFragmentOperation);
        HierarchyOp hierarchyOp = new HierarchyOp.Builder(17).setContainer(fragmentToken).setTaskFragmentOperation(taskFragmentOperation).build();
        this.mHierarchyOps.add(hierarchyOp);
        return this;
    }

    public WindowContainerTransaction setAlwaysOnTop(WindowContainerToken windowContainer, boolean alwaysOnTop) {
        HierarchyOp hierarchyOp = new HierarchyOp.Builder(12).setContainer(windowContainer.asBinder()).setAlwaysOnTop(alwaysOnTop).build();
        this.mHierarchyOps.add(hierarchyOp);
        return this;
    }

    public WindowContainerTransaction setErrorCallbackToken(IBinder errorCallbackToken) {
        if (this.mErrorCallbackToken != null) {
            throw new IllegalStateException("Can't set multiple error token for one transaction.");
        }
        this.mErrorCallbackToken = errorCallbackToken;
        return this;
    }

    public WindowContainerTransaction setTaskFragmentOrganizer(ITaskFragmentOrganizer organizer) {
        this.mTaskFragmentOrganizer = organizer;
        return this;
    }

    public WindowContainerTransaction clearAdjacentRoots(WindowContainerToken root) {
        this.mHierarchyOps.add(HierarchyOp.createForClearAdjacentRoots(root.asBinder()));
        return this;
    }

    public WindowContainerTransaction setReparentLeafTaskIfRelaunch(WindowContainerToken windowContainer, boolean reparentLeafTaskIfRelaunch) {
        HierarchyOp hierarchyOp = new HierarchyOp.Builder(16).setContainer(windowContainer.asBinder()).setReparentLeafTaskIfRelaunch(reparentLeafTaskIfRelaunch).build();
        this.mHierarchyOps.add(hierarchyOp);
        return this;
    }

    public WindowContainerTransaction movePipActivityToPinnedRootTask(WindowContainerToken parentToken, Rect bounds) {
        this.mHierarchyOps.add(new HierarchyOp.Builder(18).setContainer(parentToken.asBinder()).setBounds(bounds).build());
        return this;
    }

    public WindowContainerTransaction deferConfigToTransitionEnd(WindowContainerToken container) {
        Change change = getOrCreateChange(container.asBinder());
        change.mConfigAtTransitionEnd = true;
        return this;
    }

    public void merge(WindowContainerTransaction other, boolean transfer) {
        IBinder taskFragmentOrganizerAsBinder;
        IBinder iBinder;
        int n = other.mChanges.size();
        for (int i = 0; i < n; i++) {
            IBinder key = other.mChanges.keyAt(i);
            Change existing = this.mChanges.get(key);
            if (existing == null) {
                existing = new Change();
                this.mChanges.put(key, existing);
            }
            existing.merge(other.mChanges.valueAt(i), transfer);
        }
        int n2 = other.mHierarchyOps.size();
        for (int i2 = 0; i2 < n2; i2++) {
            this.mHierarchyOps.add(transfer ? other.mHierarchyOps.get(i2) : new HierarchyOp(other.mHierarchyOps.get(i2)));
        }
        if (this.mErrorCallbackToken != null && other.mErrorCallbackToken != null && this.mErrorCallbackToken != other.mErrorCallbackToken) {
            throw new IllegalArgumentException("Can't merge two WCTs with different error token");
        }
        if (this.mTaskFragmentOrganizer != null) {
            taskFragmentOrganizerAsBinder = this.mTaskFragmentOrganizer.asBinder();
        } else {
            taskFragmentOrganizerAsBinder = null;
        }
        IBinder otherTaskFragmentOrganizerAsBinder = other.mTaskFragmentOrganizer != null ? other.mTaskFragmentOrganizer.asBinder() : null;
        if (!Objects.equals(taskFragmentOrganizerAsBinder, otherTaskFragmentOrganizerAsBinder)) {
            throw new IllegalArgumentException("Can't merge two WCTs from different TaskFragmentOrganizers");
        }
        if (this.mErrorCallbackToken != null) {
            iBinder = this.mErrorCallbackToken;
        } else {
            iBinder = other.mErrorCallbackToken;
        }
        this.mErrorCallbackToken = iBinder;
    }

    public boolean isEmpty() {
        return this.mChanges.isEmpty() && this.mHierarchyOps.isEmpty();
    }

    public Map<IBinder, Change> getChanges() {
        return this.mChanges;
    }

    public List<HierarchyOp> getHierarchyOps() {
        return this.mHierarchyOps;
    }

    public IBinder getErrorCallbackToken() {
        return this.mErrorCallbackToken;
    }

    public ITaskFragmentOrganizer getTaskFragmentOrganizer() {
        return this.mTaskFragmentOrganizer;
    }

    public void setTransactionType(int type) {
        this.mType = type;
    }

    public boolean isStartIntentsType() {
        return this.mType == 1;
    }

    public boolean isStartTaskAndIntentType() {
        return this.mType == 2;
    }

    public boolean isStartTasksType() {
        return this.mType == 3;
    }

    public boolean isUpdateDesktopModeActive() {
        return this.mType == 4;
    }

    public boolean isStartTasksFromRecentType() {
        return this.mType == 5;
    }

    public void setDismissSplit(boolean dismiss) {
        this.mDismissSplit = dismiss;
    }

    public boolean isDismissSplit() {
        return this.mDismissSplit;
    }

    public boolean isDismissSplitWithFreeform() {
        return this.mType == 6;
    }

    public boolean isDismissSplitWithAllApps() {
        return this.mType == 7;
    }

    public void setChangeStagePosition(boolean change) {
        this.mPositionChange = change;
    }

    public boolean isStagePositionChanged() {
        return this.mPositionChange;
    }

    public void addTransferLeash(SurfaceControl leash, SurfaceControl transferLeash) {
        this.mTransferLeashMap.put(leash, transferLeash);
    }

    public ArrayMap<SurfaceControl, SurfaceControl> getTransferLeashMap() {
        return this.mTransferLeashMap;
    }

    public void addTransferTransitionToken(IBinder transferTransitionToken) {
        this.mTransferTransitionTokens.add(transferTransitionToken);
    }

    public void addMergedTransitionToken(IBinder transferTransitionToken) {
        this.mMergedTransitionTokens.add(transferTransitionToken);
    }

    public ArrayList<IBinder> getTransferTransitionTokens() {
        return this.mTransferTransitionTokens;
    }

    public ArrayList<IBinder> getMergedTransitionTokens() {
        return this.mMergedTransitionTokens;
    }

    public void setTaskViewTaskOrganizerTaskId(WindowContainerToken container, int organizerTaskId) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mTaskViewTaskOrganizerTaskId = organizerTaskId;
    }

    public String toString() {
        String extraStr = "";
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION && changeTransitionRequested()) {
            extraStr = " changeTransitRequest=" + changeTransitRequestToString(this.mChangeTransitionRequest);
        }
        return "WindowContainerTransaction { changes = " + this.mChanges + " hops = " + this.mHierarchyOps + " errorCallbackToken=" + this.mErrorCallbackToken + " taskFragmentOrganizer=" + this.mTaskFragmentOrganizer + extraStr + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(this.mChanges);
        dest.writeTypedList(this.mHierarchyOps);
        dest.writeStrongBinder(this.mErrorCallbackToken);
        dest.writeStrongInterface(this.mTaskFragmentOrganizer);
        dest.writeInt(this.mType);
        dest.writeBoolean(this.mPositionChange);
        dest.writeBoolean(this.mDismissSplit);
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            dest.writeInt(this.mChangeTransitionRequest);
        }
        if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
            dest.writeInt(this.mDisplayIdForChangeTransition);
            dest.writeString(this.mDisplayChangeTransitionReason);
        }
        dest.writeTypedList(this.mChangeList);
        if (CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER) {
            dest.writeMap(this.mTransferLeashMap);
        }
        if (CoreRune.MW_SHELL_TRANSITION) {
            dest.writeBoolean(this.mAvoidReady);
            dest.writeInt(this.mAdditionalFlag);
        }
        if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX) {
            dest.writeList(this.mTransferTransitionTokens);
            dest.writeList(this.mMergedTransitionTokens);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setChangeTransitionRequest(int changeTransitionRequest) {
        this.mChangeTransitionRequest = changeTransitionRequest;
    }

    public boolean changeTransitionRequested() {
        return this.mChangeTransitionRequest != 0;
    }

    public int getChangeTransitionRequest() {
        return this.mChangeTransitionRequest;
    }

    public WindowContainerTransaction setChangeTransitMode(WindowContainerToken container, int changeTransitMode, String reason) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mChangeTransitMode = changeTransitMode;
        chg.mChangeTransitReason = reason;
        return this;
    }

    public WindowContainerTransaction addChangeTransitFlags(WindowContainerToken container, int changeTransitFlags) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mChangeTransitFlags |= changeTransitFlags;
        return this;
    }

    public WindowContainerTransaction setChangeTransitStartBounds(WindowContainerToken container, Rect startBounds) {
        Change chg = getOrCreateChange(container.asBinder());
        if (chg.mChangeTransitStartBounds == null) {
            chg.mChangeTransitStartBounds = new Rect();
        }
        chg.mChangeTransitStartBounds.set(startBounds);
        return this;
    }

    public void setDisplayIdForChangeTransition(int displayId, String reason) {
        this.mDisplayIdForChangeTransition = displayId;
        this.mDisplayChangeTransitionReason = reason;
    }

    public boolean displayChangeTransitionRequested() {
        return this.mDisplayIdForChangeTransition != -1;
    }

    public int getDisplayIdForChangeTransition() {
        return this.mDisplayIdForChangeTransition;
    }

    public String getDisplayChangeTransitionReason() {
        return this.mDisplayChangeTransitionReason;
    }

    public WindowContainerTransaction setChangeFreeformStashMode(WindowContainerToken container, int changeFreeformStashMode) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mChangeFreeformStashMode = changeFreeformStashMode;
        return this;
    }

    public WindowContainerTransaction setChangeFreeformStashScale(WindowContainerToken container, float changeFreeformStashScale) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mChangeFreeformStashScale = changeFreeformStashScale;
        return this;
    }

    public WindowContainerTransaction requestForceTaskInfoChange(WindowContainerToken container) {
        Change chg = getOrCreateChange(container.asBinder());
        chg.mForceTaskInfoChangeRequested = true;
        return this;
    }

    public static class Change implements Parcelable {
        public static final int CHANGE_BOUNDS_TRANSACTION = 2;
        public static final int CHANGE_BOUNDS_TRANSACTION_RECT = 16;
        public static final int CHANGE_DRAG_RESIZING = 256;
        public static final int CHANGE_FOCUSABLE = 1;
        public static final int CHANGE_FORCE_NO_PIP = 64;
        public static final int CHANGE_FORCE_TRANSLUCENT = 128;
        public static final int CHANGE_HIDDEN = 8;
        public static final int CHANGE_IGNORE_ORIENTATION_REQUEST = 32;
        public static final int CHANGE_PIP_CALLBACK = 4;
        public static final int CHANGE_RELATIVE_BOUNDS = 512;
        public static final Parcelable.Creator<Change> CREATOR = new Parcelable.Creator<Change>() { // from class: android.window.WindowContainerTransaction.Change.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Change createFromParcel(Parcel in) {
                return new Change(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Change[] newArray(int size) {
                return new Change[size];
            }
        };
        private int mActivityWindowingMode;
        private Rect mBoundsChangeSurfaceBounds;
        private SurfaceControl.Transaction mBoundsChangeTransaction;
        private int mChangeFreeformStashMode;
        private float mChangeFreeformStashScale;
        private int mChangeMask;
        private int mChangeTransitFlags;
        private int mChangeTransitMode;
        private String mChangeTransitReason;
        private Rect mChangeTransitStartBounds;
        private boolean mConfigAtTransitionEnd;
        private int mConfigSetMask;
        private final Configuration mConfiguration;
        private boolean mDragResizing;
        private boolean mFocusable;
        private boolean mForceTaskInfoChangeRequested;
        private boolean mForceTranslucent;
        private boolean mHidden;
        private boolean mIgnoreOrientationRequest;
        private Rect mPinnedBounds;
        private Rect mRelativeBounds;
        private int mTaskViewTaskOrganizerTaskId;
        private int mWindowSetMask;
        private int mWindowingMode;

        public Change() {
            this.mConfiguration = new Configuration();
            this.mFocusable = true;
            this.mHidden = false;
            this.mIgnoreOrientationRequest = false;
            this.mForceTranslucent = false;
            this.mDragResizing = false;
            this.mChangeMask = 0;
            this.mConfigSetMask = 0;
            this.mWindowSetMask = 0;
            this.mPinnedBounds = null;
            this.mBoundsChangeTransaction = null;
            this.mBoundsChangeSurfaceBounds = null;
            this.mRelativeBounds = null;
            this.mConfigAtTransitionEnd = false;
            this.mActivityWindowingMode = -1;
            this.mWindowingMode = -1;
            this.mChangeTransitMode = 0;
            this.mChangeFreeformStashMode = 0;
            this.mTaskViewTaskOrganizerTaskId = -1;
        }

        protected Change(Parcel in) {
            this.mConfiguration = new Configuration();
            this.mFocusable = true;
            this.mHidden = false;
            this.mIgnoreOrientationRequest = false;
            this.mForceTranslucent = false;
            this.mDragResizing = false;
            this.mChangeMask = 0;
            this.mConfigSetMask = 0;
            this.mWindowSetMask = 0;
            this.mPinnedBounds = null;
            this.mBoundsChangeTransaction = null;
            this.mBoundsChangeSurfaceBounds = null;
            this.mRelativeBounds = null;
            this.mConfigAtTransitionEnd = false;
            this.mActivityWindowingMode = -1;
            this.mWindowingMode = -1;
            this.mChangeTransitMode = 0;
            this.mChangeFreeformStashMode = 0;
            this.mTaskViewTaskOrganizerTaskId = -1;
            this.mConfiguration.readFromParcel(in);
            this.mFocusable = in.readBoolean();
            this.mHidden = in.readBoolean();
            this.mIgnoreOrientationRequest = in.readBoolean();
            this.mForceTranslucent = in.readBoolean();
            this.mDragResizing = in.readBoolean();
            this.mChangeMask = in.readInt();
            this.mConfigSetMask = in.readInt();
            this.mWindowSetMask = in.readInt();
            if ((this.mChangeMask & 4) != 0) {
                this.mPinnedBounds = new Rect();
                this.mPinnedBounds.readFromParcel(in);
            }
            if ((this.mChangeMask & 2) != 0) {
                this.mBoundsChangeTransaction = SurfaceControl.Transaction.CREATOR.createFromParcel(in);
            }
            if ((this.mChangeMask & 16) != 0) {
                this.mBoundsChangeSurfaceBounds = new Rect();
                this.mBoundsChangeSurfaceBounds.readFromParcel(in);
            }
            if ((this.mChangeMask & 512) != 0) {
                this.mRelativeBounds = new Rect();
                this.mRelativeBounds.readFromParcel(in);
            }
            this.mConfigAtTransitionEnd = in.readBoolean();
            this.mWindowingMode = in.readInt();
            this.mActivityWindowingMode = in.readInt();
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                this.mChangeTransitMode = in.readInt();
                this.mChangeTransitFlags = in.readInt();
                this.mChangeTransitReason = in.readString();
                this.mChangeTransitStartBounds = (Rect) in.readTypedObject(Rect.CREATOR);
            }
            this.mChangeFreeformStashMode = in.readInt();
            this.mChangeFreeformStashScale = in.readFloat();
            this.mForceTaskInfoChangeRequested = in.readBoolean();
            this.mTaskViewTaskOrganizerTaskId = in.readInt();
        }

        public void merge(Change other, boolean transfer) {
            Rect rect;
            Rect rect2;
            this.mConfiguration.setTo(other.mConfiguration, other.mConfigSetMask, other.mWindowSetMask);
            this.mConfigSetMask |= other.mConfigSetMask;
            this.mWindowSetMask |= other.mWindowSetMask;
            if ((other.mChangeMask & 1) != 0) {
                this.mFocusable = other.mFocusable;
            }
            if (transfer && (other.mChangeMask & 2) != 0) {
                this.mBoundsChangeTransaction = other.mBoundsChangeTransaction;
                other.mBoundsChangeTransaction = null;
            }
            if ((other.mChangeMask & 4) != 0) {
                this.mPinnedBounds = transfer ? other.mPinnedBounds : new Rect(other.mPinnedBounds);
            }
            if ((other.mChangeMask & 8) != 0) {
                this.mHidden = other.mHidden;
            }
            if ((other.mChangeMask & 32) != 0) {
                this.mIgnoreOrientationRequest = other.mIgnoreOrientationRequest;
            }
            if ((other.mChangeMask & 128) != 0) {
                this.mForceTranslucent = other.mForceTranslucent;
            }
            if ((other.mChangeMask & 256) != 0) {
                this.mDragResizing = other.mDragResizing;
            }
            this.mChangeMask |= other.mChangeMask;
            if (other.mActivityWindowingMode >= 0) {
                this.mActivityWindowingMode = other.mActivityWindowingMode;
            }
            if (other.mWindowingMode >= 0) {
                this.mWindowingMode = other.mWindowingMode;
            }
            if (other.mBoundsChangeSurfaceBounds != null) {
                this.mBoundsChangeSurfaceBounds = transfer ? other.mBoundsChangeSurfaceBounds : new Rect(other.mBoundsChangeSurfaceBounds);
            }
            if (other.mRelativeBounds != null) {
                if (transfer) {
                    rect2 = other.mRelativeBounds;
                } else {
                    rect2 = new Rect(other.mRelativeBounds);
                }
                this.mRelativeBounds = rect2;
            }
            this.mConfigAtTransitionEnd = this.mConfigAtTransitionEnd || other.mConfigAtTransitionEnd;
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION && other.hasChangeTransitMode()) {
                this.mChangeTransitMode = other.mChangeTransitMode;
                this.mChangeTransitReason = other.mChangeTransitReason;
                if (other.mChangeTransitFlags != 0) {
                    this.mChangeTransitFlags |= other.mChangeTransitFlags;
                }
                if (other.mChangeTransitStartBounds != null) {
                    if (transfer) {
                        rect = other.mChangeTransitStartBounds;
                    } else {
                        rect = new Rect(other.mChangeTransitStartBounds);
                    }
                    this.mChangeTransitStartBounds = rect;
                }
            }
            if (other.hasChangeFreeformStashMode()) {
                this.mChangeFreeformStashMode = other.getChangeFreeformStashMode();
            }
            if (other.hasChangeFreeformStashScale()) {
                this.mChangeFreeformStashScale = other.getChangeFreeformStashScale();
            }
            if (other.isForceTaskInfoChangeRequested()) {
                this.mForceTaskInfoChangeRequested = true;
            }
            if (other.isOrganizedTaskViewTask()) {
                this.mTaskViewTaskOrganizerTaskId = other.mTaskViewTaskOrganizerTaskId;
            }
        }

        public int getWindowingMode() {
            return this.mWindowingMode;
        }

        public int getActivityWindowingMode() {
            return this.mActivityWindowingMode;
        }

        public Configuration getConfiguration() {
            return this.mConfiguration;
        }

        public boolean getFocusable() {
            if ((this.mChangeMask & 1) == 0) {
                throw new RuntimeException("Focusable not set. check CHANGE_FOCUSABLE first");
            }
            return this.mFocusable;
        }

        public boolean getHidden() {
            if ((this.mChangeMask & 8) == 0) {
                throw new RuntimeException("Hidden not set. check CHANGE_HIDDEN first");
            }
            return this.mHidden;
        }

        public boolean getIgnoreOrientationRequest() {
            if ((this.mChangeMask & 32) == 0) {
                throw new RuntimeException("IgnoreOrientationRequest not set. Check CHANGE_IGNORE_ORIENTATION_REQUEST first");
            }
            return this.mIgnoreOrientationRequest;
        }

        public boolean getForceTranslucent() {
            if ((this.mChangeMask & 128) == 0) {
                throw new RuntimeException("Force translucent not set. Check CHANGE_FORCE_TRANSLUCENT first");
            }
            return this.mForceTranslucent;
        }

        public boolean getDragResizing() {
            if ((this.mChangeMask & 256) == 0) {
                throw new RuntimeException("Drag resizing not set. Check CHANGE_DRAG_RESIZING first");
            }
            return this.mDragResizing;
        }

        public boolean getConfigAtTransitionEnd() {
            return this.mConfigAtTransitionEnd;
        }

        public int getChangeMask() {
            return this.mChangeMask;
        }

        public int getConfigSetMask() {
            return this.mConfigSetMask;
        }

        public int getWindowSetMask() {
            return this.mWindowSetMask;
        }

        public Rect getEnterPipBounds() {
            return this.mPinnedBounds;
        }

        public SurfaceControl.Transaction getBoundsChangeTransaction() {
            return this.mBoundsChangeTransaction;
        }

        public Rect getBoundsChangeSurfaceBounds() {
            return this.mBoundsChangeSurfaceBounds;
        }

        public Rect getRelativeBounds() {
            return this.mRelativeBounds;
        }

        public boolean hasChangeTransitMode() {
            return this.mChangeTransitMode != 0;
        }

        public int getChangeTransitMode() {
            return this.mChangeTransitMode;
        }

        public int getChangeTransitFlags() {
            return this.mChangeTransitFlags;
        }

        public String getChangeTransitReason() {
            return this.mChangeTransitReason;
        }

        public Rect getChangeTransitStartBounds() {
            return this.mChangeTransitStartBounds;
        }

        public boolean hasChangeFreeformStashScale() {
            return this.mChangeFreeformStashScale != 0.0f;
        }

        public float getChangeFreeformStashScale() {
            return this.mChangeFreeformStashScale;
        }

        public boolean hasChangeFreeformStashMode() {
            return this.mChangeFreeformStashMode != 0;
        }

        public int getChangeFreeformStashMode() {
            return this.mChangeFreeformStashMode;
        }

        public boolean isForceTaskInfoChangeRequested() {
            return this.mForceTaskInfoChangeRequested;
        }

        public boolean isOrganizedTaskViewTask() {
            return this.mTaskViewTaskOrganizerTaskId > -1;
        }

        public int getTaskViewTaskOrganizerTaskId() {
            return this.mTaskViewTaskOrganizerTaskId;
        }

        public String toString() {
            boolean changesBounds = ((this.mConfigSetMask & 536870912) == 0 || (this.mWindowSetMask & 1) == 0) ? false : true;
            boolean changesAppBounds = ((536870912 & this.mConfigSetMask) == 0 || (this.mWindowSetMask & 2) == 0) ? false : true;
            boolean changesSs = (this.mConfigSetMask & 1024) != 0;
            boolean changesSss = (this.mConfigSetMask & 2048) != 0;
            boolean changesStagePosition = (this.mWindowSetMask & 2097152) != 0;
            StringBuilder sb = new StringBuilder();
            sb.append('{');
            if (changesBounds) {
                sb.append("bounds:" + this.mConfiguration.windowConfiguration.getBounds() + ",");
            }
            if (changesAppBounds) {
                sb.append("appbounds:" + this.mConfiguration.windowConfiguration.getAppBounds() + ",");
            }
            if (changesSss) {
                sb.append("ssw:" + this.mConfiguration.smallestScreenWidthDp + ",");
            }
            if (changesSs) {
                sb.append("sw/h:" + this.mConfiguration.screenWidthDp + "x" + this.mConfiguration.screenHeightDp + ",");
            }
            if ((1 & this.mChangeMask) != 0) {
                sb.append("focusable:" + this.mFocusable + ",");
            }
            if ((this.mChangeMask & 128) != 0) {
                sb.append("forceTranslucent:" + this.mForceTranslucent + ",");
            }
            if ((this.mChangeMask & 8) != 0) {
                sb.append("hidden:" + this.mHidden + ",");
            }
            if ((this.mChangeMask & 256) != 0) {
                sb.append("dragResizing:" + this.mDragResizing + ",");
            }
            if (this.mBoundsChangeTransaction != null) {
                sb.append("hasBoundsTransaction,");
            }
            if ((this.mChangeMask & 32) != 0) {
                sb.append("ignoreOrientationRequest:" + this.mIgnoreOrientationRequest + ",");
            }
            if ((this.mChangeMask & 512) != 0) {
                sb.append("relativeBounds:").append(this.mRelativeBounds).append(",");
            }
            if (this.mConfigAtTransitionEnd) {
                sb.append("configAtTransitionEnd").append(",");
            }
            if (changesStagePosition) {
                sb.append("stagePosition" + this.mConfiguration.windowConfiguration.getStagePositionToString() + ",");
            }
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                if (hasChangeTransitMode()) {
                    sb.append("changeTransit:" + this.mChangeTransitMode + ",");
                }
                if (this.mChangeTransitStartBounds != null) {
                    sb.append("changeStartBounds:" + this.mChangeTransitStartBounds + ",");
                }
                if (this.mChangeTransitFlags != 0) {
                    sb.append("changeTransitFlags:" + this.mChangeTransitFlags + ",");
                }
            }
            if (hasChangeFreeformStashMode()) {
                sb.append("changeFreeformStashMode:" + this.mChangeFreeformStashMode + ",");
            }
            if (hasChangeFreeformStashScale()) {
                sb.append("changeFreeformStashScale:" + this.mChangeFreeformStashScale + ",");
            }
            sb.append("}");
            return sb.toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            this.mConfiguration.writeToParcel(dest, flags);
            dest.writeBoolean(this.mFocusable);
            dest.writeBoolean(this.mHidden);
            dest.writeBoolean(this.mIgnoreOrientationRequest);
            dest.writeBoolean(this.mForceTranslucent);
            dest.writeBoolean(this.mDragResizing);
            dest.writeInt(this.mChangeMask);
            dest.writeInt(this.mConfigSetMask);
            dest.writeInt(this.mWindowSetMask);
            if (this.mPinnedBounds != null) {
                this.mPinnedBounds.writeToParcel(dest, flags);
            }
            if (this.mBoundsChangeTransaction != null) {
                this.mBoundsChangeTransaction.writeToParcel(dest, flags);
            }
            if (this.mBoundsChangeSurfaceBounds != null) {
                this.mBoundsChangeSurfaceBounds.writeToParcel(dest, flags);
            }
            if (this.mRelativeBounds != null) {
                this.mRelativeBounds.writeToParcel(dest, flags);
            }
            dest.writeBoolean(this.mConfigAtTransitionEnd);
            dest.writeInt(this.mWindowingMode);
            dest.writeInt(this.mActivityWindowingMode);
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                dest.writeInt(this.mChangeTransitMode);
                dest.writeInt(this.mChangeTransitFlags);
                dest.writeString(this.mChangeTransitReason);
                dest.writeTypedObject(this.mChangeTransitStartBounds, flags);
            }
            dest.writeInt(this.mChangeFreeformStashMode);
            dest.writeFloat(this.mChangeFreeformStashScale);
            dest.writeBoolean(this.mForceTaskInfoChangeRequested);
            dest.writeInt(this.mTaskViewTaskOrganizerTaskId);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static final class HierarchyOp implements Parcelable {
        public static final Parcelable.Creator<HierarchyOp> CREATOR = new Parcelable.Creator<HierarchyOp>() { // from class: android.window.WindowContainerTransaction.HierarchyOp.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HierarchyOp createFromParcel(Parcel in) {
                return new HierarchyOp(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HierarchyOp[] newArray(int size) {
                return new HierarchyOp[size];
            }
        };
        public static final int HIERARCHY_OP_TYPE_ADD_INSETS_FRAME_PROVIDER = 10;
        public static final int HIERARCHY_OP_TYPE_ADD_TASK_FRAGMENT_OPERATION = 17;
        public static final int HIERARCHY_OP_TYPE_CHILDREN_TASKS_REPARENT = 2;
        public static final int HIERARCHY_OP_TYPE_CLEAR_ADJACENT_ROOTS = 15;
        public static final int HIERARCHY_OP_TYPE_CLOSE_TASK = 100;
        public static final int HIERARCHY_OP_TYPE_FINISH_ACTIVITY = 14;
        public static final int HIERARCHY_OP_TYPE_LAUNCH_TASK = 5;
        public static final int HIERARCHY_OP_TYPE_MOVE_PIP_ACTIVITY_TO_PINNED_TASK = 18;
        public static final int HIERARCHY_OP_TYPE_PENDING_INTENT = 7;
        public static final int HIERARCHY_OP_TYPE_REMOVE_INSETS_FRAME_PROVIDER = 11;
        public static final int HIERARCHY_OP_TYPE_REMOVE_TASK = 13;
        public static final int HIERARCHY_OP_TYPE_REORDER = 1;
        public static final int HIERARCHY_OP_TYPE_REPARENT = 0;
        public static final int HIERARCHY_OP_TYPE_RESTORE_TRANSIENT_ORDER = 9;
        public static final int HIERARCHY_OP_TYPE_SET_ADJACENT_ROOTS = 4;
        public static final int HIERARCHY_OP_TYPE_SET_ALWAYS_ON_TOP = 12;
        public static final int HIERARCHY_OP_TYPE_SET_LAUNCH_ADJACENT_FLAG_ROOT = 6;
        public static final int HIERARCHY_OP_TYPE_SET_LAUNCH_ROOT = 3;
        public static final int HIERARCHY_OP_TYPE_SET_REPARENT_LEAF_TASK_IF_RELAUNCH = 16;
        public static final int HIERARCHY_OP_TYPE_START_SHORTCUT = 8;
        public static final String LAUNCH_KEY_SHORTCUT_CALLING_PACKAGE = "android:transaction.hop.shortcut_calling_package";
        public static final String LAUNCH_KEY_TASK_ID = "android:transaction.hop.taskId";
        private Intent mActivityIntent;
        private int[] mActivityTypes;
        private boolean mAlwaysOnTop;
        private Rect mBounds;
        private IBinder mContainer;
        private boolean mIncludingParents;
        private IBinder mInsetsFrameOwner;
        private InsetsFrameProvider mInsetsFrameProvider;
        private Bundle mLaunchOptions;
        private PendingIntent mPendingIntent;
        private IBinder mReparent;
        private boolean mReparentLeafTaskIfRelaunch;
        private boolean mReparentTopOnly;
        private ShortcutInfo mShortcutInfo;
        private TaskFragmentOperation mTaskFragmentOperation;
        private boolean mToTop;
        private final int mType;
        private int[] mWindowingModes;

        public static HierarchyOp createForReparent(IBinder container, IBinder reparent, boolean toTop) {
            return new Builder(0).setContainer(container).setReparentContainer(reparent).setToTop(toTop).build();
        }

        public static HierarchyOp createForReorder(IBinder container, boolean toTop, boolean includingParents) {
            return new Builder(1).setContainer(container).setReparentContainer(container).setToTop(toTop).setIncludingParents(includingParents).build();
        }

        public static HierarchyOp createForChildrenTasksReparent(IBinder currentParent, IBinder newParent, int[] windowingModes, int[] activityTypes, boolean onTop, boolean reparentTopOnly) {
            return new Builder(2).setContainer(currentParent).setReparentContainer(newParent).setWindowingModes(windowingModes).setActivityTypes(activityTypes).setToTop(onTop).setReparentTopOnly(reparentTopOnly).build();
        }

        public static HierarchyOp createForSetLaunchRoot(IBinder container, int[] windowingModes, int[] activityTypes) {
            return new Builder(3).setContainer(container).setWindowingModes(windowingModes).setActivityTypes(activityTypes).build();
        }

        public static HierarchyOp createForAdjacentRoots(IBinder root1, IBinder root2) {
            return new Builder(4).setContainer(root1).setReparentContainer(root2).build();
        }

        public static HierarchyOp createForTaskLaunch(int taskId, Bundle options) {
            Bundle fullOptions = options == null ? new Bundle() : options;
            fullOptions.putInt(LAUNCH_KEY_TASK_ID, taskId);
            return new Builder(5).setToTop(true).setLaunchOptions(fullOptions).build();
        }

        public static HierarchyOp createForStartShortcut(String callingPackage, ShortcutInfo shortcutInfo, Bundle options) {
            Bundle fullOptions = options == null ? new Bundle() : options;
            fullOptions.putString(LAUNCH_KEY_SHORTCUT_CALLING_PACKAGE, callingPackage);
            return new Builder(8).setShortcutInfo(shortcutInfo).setLaunchOptions(fullOptions).build();
        }

        public static HierarchyOp createForSetLaunchAdjacentFlagRoot(IBinder container, boolean clearRoot) {
            return new Builder(6).setContainer(container).setToTop(clearRoot).build();
        }

        public static HierarchyOp createForRemoveTask(IBinder container) {
            return new Builder(13).setContainer(container).build();
        }

        public static HierarchyOp createForCloseTask(IBinder container) {
            return new Builder(100).setContainer(container).build();
        }

        public static HierarchyOp createForClearAdjacentRoots(IBinder root) {
            return new Builder(15).setContainer(root).build();
        }

        private HierarchyOp(int type) {
            this.mType = type;
        }

        public HierarchyOp(HierarchyOp copy) {
            this.mType = copy.mType;
            this.mContainer = copy.mContainer;
            this.mBounds = copy.mBounds;
            this.mIncludingParents = copy.mIncludingParents;
            this.mReparent = copy.mReparent;
            this.mInsetsFrameProvider = copy.mInsetsFrameProvider;
            this.mInsetsFrameOwner = copy.mInsetsFrameOwner;
            this.mToTop = copy.mToTop;
            this.mReparentTopOnly = copy.mReparentTopOnly;
            this.mWindowingModes = copy.mWindowingModes;
            this.mActivityTypes = copy.mActivityTypes;
            this.mLaunchOptions = copy.mLaunchOptions;
            this.mActivityIntent = copy.mActivityIntent;
            this.mTaskFragmentOperation = copy.mTaskFragmentOperation;
            this.mPendingIntent = copy.mPendingIntent;
            this.mShortcutInfo = copy.mShortcutInfo;
            this.mAlwaysOnTop = copy.mAlwaysOnTop;
            this.mReparentLeafTaskIfRelaunch = copy.mReparentLeafTaskIfRelaunch;
        }

        protected HierarchyOp(Parcel in) {
            this.mType = in.readInt();
            this.mContainer = in.readStrongBinder();
            this.mBounds = (Rect) in.readTypedObject(Rect.CREATOR);
            this.mIncludingParents = in.readBoolean();
            this.mReparent = in.readStrongBinder();
            this.mInsetsFrameProvider = (InsetsFrameProvider) in.readTypedObject(InsetsFrameProvider.CREATOR);
            this.mInsetsFrameOwner = in.readStrongBinder();
            this.mToTop = in.readBoolean();
            this.mReparentTopOnly = in.readBoolean();
            this.mWindowingModes = in.createIntArray();
            this.mActivityTypes = in.createIntArray();
            this.mLaunchOptions = in.readBundle();
            this.mActivityIntent = (Intent) in.readTypedObject(Intent.CREATOR);
            this.mTaskFragmentOperation = (TaskFragmentOperation) in.readTypedObject(TaskFragmentOperation.CREATOR);
            this.mPendingIntent = (PendingIntent) in.readTypedObject(PendingIntent.CREATOR);
            this.mShortcutInfo = (ShortcutInfo) in.readTypedObject(ShortcutInfo.CREATOR);
            this.mAlwaysOnTop = in.readBoolean();
            this.mReparentLeafTaskIfRelaunch = in.readBoolean();
        }

        public int getType() {
            return this.mType;
        }

        public boolean isReparent() {
            return this.mType == 0;
        }

        public IBinder getNewParent() {
            return this.mReparent;
        }

        public InsetsFrameProvider getInsetsFrameProvider() {
            return this.mInsetsFrameProvider;
        }

        public IBinder getInsetsFrameOwner() {
            return this.mInsetsFrameOwner;
        }

        public IBinder getContainer() {
            return this.mContainer;
        }

        public IBinder getAdjacentRoot() {
            return this.mReparent;
        }

        public boolean getToTop() {
            return this.mToTop;
        }

        public boolean getReparentTopOnly() {
            return this.mReparentTopOnly;
        }

        public int[] getWindowingModes() {
            return this.mWindowingModes;
        }

        public int[] getActivityTypes() {
            return this.mActivityTypes;
        }

        public Bundle getLaunchOptions() {
            return this.mLaunchOptions;
        }

        public Intent getActivityIntent() {
            return this.mActivityIntent;
        }

        public boolean isAlwaysOnTop() {
            return this.mAlwaysOnTop;
        }

        public boolean isReparentLeafTaskIfRelaunch() {
            return this.mReparentLeafTaskIfRelaunch;
        }

        public TaskFragmentOperation getTaskFragmentOperation() {
            return this.mTaskFragmentOperation;
        }

        public PendingIntent getPendingIntent() {
            return this.mPendingIntent;
        }

        public ShortcutInfo getShortcutInfo() {
            return this.mShortcutInfo;
        }

        public Rect getBounds() {
            return this.mBounds;
        }

        public boolean includingParents() {
            return this.mIncludingParents;
        }

        public static String hopToString(int type) {
            switch (type) {
                case 0:
                    return "reparent";
                case 1:
                    return "reorder";
                case 2:
                    return "ChildrenTasksReparent";
                case 3:
                    return "SetLaunchRoot";
                case 4:
                    return "SetAdjacentRoot";
                case 5:
                    return "LaunchTask";
                case 6:
                    return "SetAdjacentFlagRoot";
                case 7:
                    return "PendingIntent";
                case 8:
                    return "StartShortcut";
                case 10:
                    return "addInsetsFrameProvider";
                case 11:
                    return "removeInsetsFrameProvider";
                case 12:
                    return "setAlwaysOnTop";
                case 13:
                    return "RemoveTask";
                case 14:
                    return "finishActivity";
                case 15:
                    return "ClearAdjacentRoot";
                case 16:
                    return "setReparentLeafTaskIfRelaunch";
                case 17:
                    return "addTaskFragmentOperation";
                case 100:
                    return "CloseTask";
                default:
                    return "HOP(" + type + NavigationBarInflaterView.KEY_CODE_END;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{").append(hopToString(this.mType)).append(": ");
            switch (this.mType) {
                case 0:
                    sb.append(this.mContainer).append(" to ").append(this.mToTop ? "top of " : "bottom of ").append(this.mReparent);
                    break;
                case 1:
                    sb.append(this.mContainer).append(" to ").append(this.mToTop ? GenerateXML.TOP : GenerateXML.BOTTOM);
                    break;
                case 2:
                    sb.append("from=").append(this.mContainer).append(" to=").append(this.mReparent).append(" mToTop=").append(this.mToTop).append(" mReparentTopOnly=").append(this.mReparentTopOnly).append(" mWindowingMode=").append(Arrays.toString(this.mWindowingModes)).append(" mActivityType=").append(Arrays.toString(this.mActivityTypes));
                    break;
                case 3:
                    sb.append("container=").append(this.mContainer).append(" mWindowingMode=").append(Arrays.toString(this.mWindowingModes)).append(" mActivityType=").append(Arrays.toString(this.mActivityTypes));
                    break;
                case 4:
                    sb.append("container=").append(this.mContainer).append(" adjacentRoot=").append(this.mReparent);
                    break;
                case 5:
                    sb.append(this.mLaunchOptions);
                    break;
                case 6:
                    sb.append("container=").append(this.mContainer).append(" clearRoot=").append(this.mToTop);
                    break;
                case 7:
                    sb.append("options=").append(this.mLaunchOptions);
                    break;
                case 8:
                    sb.append("options=").append(this.mLaunchOptions).append(" info=").append(this.mShortcutInfo);
                    break;
                case 10:
                case 11:
                    sb.append("container=").append(this.mContainer).append(" provider=").append(this.mInsetsFrameProvider).append(" owner=").append(this.mInsetsFrameOwner);
                    break;
                case 12:
                    sb.append("container=").append(this.mContainer).append(" alwaysOnTop=").append(this.mAlwaysOnTop);
                    break;
                case 13:
                case 100:
                    sb.append("task=").append(this.mContainer);
                    break;
                case 14:
                    sb.append("activity=").append(this.mContainer);
                    break;
                case 15:
                    sb.append("container=").append(this.mContainer);
                    break;
                case 16:
                    sb.append("container= ").append(this.mContainer).append(" reparentLeafTaskIfRelaunch= ").append(this.mReparentLeafTaskIfRelaunch);
                    break;
                case 17:
                    sb.append("fragmentToken= ").append(this.mContainer).append(" operation= ").append(this.mTaskFragmentOperation);
                    break;
                default:
                    sb.append("container=").append(this.mContainer).append(" reparent=").append(this.mReparent).append(" mToTop=").append(this.mToTop).append(" mWindowingMode=").append(Arrays.toString(this.mWindowingModes)).append(" mActivityType=").append(Arrays.toString(this.mActivityTypes));
                    break;
            }
            return sb.append("}").toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mType);
            dest.writeStrongBinder(this.mContainer);
            dest.writeTypedObject(this.mBounds, flags);
            dest.writeBoolean(this.mIncludingParents);
            dest.writeStrongBinder(this.mReparent);
            dest.writeTypedObject(this.mInsetsFrameProvider, flags);
            dest.writeStrongBinder(this.mInsetsFrameOwner);
            dest.writeBoolean(this.mToTop);
            dest.writeBoolean(this.mReparentTopOnly);
            dest.writeIntArray(this.mWindowingModes);
            dest.writeIntArray(this.mActivityTypes);
            dest.writeBundle(this.mLaunchOptions);
            dest.writeTypedObject(this.mActivityIntent, flags);
            dest.writeTypedObject(this.mTaskFragmentOperation, flags);
            dest.writeTypedObject(this.mPendingIntent, flags);
            dest.writeTypedObject(this.mShortcutInfo, flags);
            dest.writeBoolean(this.mAlwaysOnTop);
            dest.writeBoolean(this.mReparentLeafTaskIfRelaunch);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private static class Builder {
            private Intent mActivityIntent;
            private int[] mActivityTypes;
            private boolean mAlwaysOnTop;
            private Rect mBounds;
            private IBinder mContainer;
            private boolean mIncludingParents;
            private IBinder mInsetsFrameOwner;
            private InsetsFrameProvider mInsetsFrameProvider;
            private Bundle mLaunchOptions;
            private PendingIntent mPendingIntent;
            private IBinder mReparent;
            private boolean mReparentLeafTaskIfRelaunch;
            private boolean mReparentTopOnly;
            private ShortcutInfo mShortcutInfo;
            private TaskFragmentOperation mTaskFragmentOperation;
            private boolean mToTop;
            private final int mType;
            private int[] mWindowingModes;

            Builder(int type) {
                this.mType = type;
            }

            Builder setContainer(IBinder container) {
                this.mContainer = container;
                return this;
            }

            Builder setReparentContainer(IBinder reparentContainer) {
                this.mReparent = reparentContainer;
                return this;
            }

            Builder setInsetsFrameProvider(InsetsFrameProvider provider) {
                this.mInsetsFrameProvider = provider;
                return this;
            }

            Builder setInsetsFrameOwner(IBinder owner) {
                this.mInsetsFrameOwner = owner;
                return this;
            }

            Builder setToTop(boolean toTop) {
                this.mToTop = toTop;
                return this;
            }

            Builder setReparentTopOnly(boolean reparentTopOnly) {
                this.mReparentTopOnly = reparentTopOnly;
                return this;
            }

            Builder setWindowingModes(int[] windowingModes) {
                this.mWindowingModes = windowingModes;
                return this;
            }

            Builder setActivityTypes(int[] activityTypes) {
                this.mActivityTypes = activityTypes;
                return this;
            }

            Builder setLaunchOptions(Bundle launchOptions) {
                this.mLaunchOptions = launchOptions;
                return this;
            }

            Builder setActivityIntent(Intent activityIntent) {
                this.mActivityIntent = activityIntent;
                return this;
            }

            Builder setPendingIntent(PendingIntent sender) {
                this.mPendingIntent = sender;
                return this;
            }

            Builder setAlwaysOnTop(boolean alwaysOnTop) {
                this.mAlwaysOnTop = alwaysOnTop;
                return this;
            }

            Builder setTaskFragmentOperation(TaskFragmentOperation taskFragmentOperation) {
                this.mTaskFragmentOperation = taskFragmentOperation;
                return this;
            }

            Builder setReparentLeafTaskIfRelaunch(boolean reparentLeafTaskIfRelaunch) {
                this.mReparentLeafTaskIfRelaunch = reparentLeafTaskIfRelaunch;
                return this;
            }

            Builder setShortcutInfo(ShortcutInfo shortcutInfo) {
                this.mShortcutInfo = shortcutInfo;
                return this;
            }

            Builder setBounds(Rect bounds) {
                this.mBounds = bounds;
                return this;
            }

            Builder setIncludingParents(boolean value) {
                this.mIncludingParents = value;
                return this;
            }

            HierarchyOp build() {
                int[] iArr;
                HierarchyOp hierarchyOp = new HierarchyOp(this.mType);
                hierarchyOp.mContainer = this.mContainer;
                hierarchyOp.mReparent = this.mReparent;
                if (this.mWindowingModes != null) {
                    iArr = Arrays.copyOf(this.mWindowingModes, this.mWindowingModes.length);
                } else {
                    iArr = null;
                }
                hierarchyOp.mWindowingModes = iArr;
                hierarchyOp.mActivityTypes = this.mActivityTypes != null ? Arrays.copyOf(this.mActivityTypes, this.mActivityTypes.length) : null;
                hierarchyOp.mInsetsFrameProvider = this.mInsetsFrameProvider;
                hierarchyOp.mInsetsFrameOwner = this.mInsetsFrameOwner;
                hierarchyOp.mToTop = this.mToTop;
                hierarchyOp.mReparentTopOnly = this.mReparentTopOnly;
                hierarchyOp.mLaunchOptions = this.mLaunchOptions;
                hierarchyOp.mActivityIntent = this.mActivityIntent;
                hierarchyOp.mPendingIntent = this.mPendingIntent;
                hierarchyOp.mAlwaysOnTop = this.mAlwaysOnTop;
                hierarchyOp.mTaskFragmentOperation = this.mTaskFragmentOperation;
                hierarchyOp.mShortcutInfo = this.mShortcutInfo;
                hierarchyOp.mBounds = this.mBounds;
                hierarchyOp.mIncludingParents = this.mIncludingParents;
                hierarchyOp.mReparentLeafTaskIfRelaunch = this.mReparentLeafTaskIfRelaunch;
                return hierarchyOp;
            }
        }
    }

    public static class TaskFragmentAdjacentParams {
        private static final String DELAY_PRIMARY_LAST_ACTIVITY_REMOVAL = "android:transaction.adjacent.option.delay_primary_removal";
        private static final String DELAY_SECONDARY_LAST_ACTIVITY_REMOVAL = "android:transaction.adjacent.option.delay_secondary_removal";
        private boolean mDelayPrimaryLastActivityRemoval;
        private boolean mDelaySecondaryLastActivityRemoval;

        public TaskFragmentAdjacentParams() {
        }

        public TaskFragmentAdjacentParams(Bundle bundle) {
            this.mDelayPrimaryLastActivityRemoval = bundle.getBoolean(DELAY_PRIMARY_LAST_ACTIVITY_REMOVAL);
            this.mDelaySecondaryLastActivityRemoval = bundle.getBoolean(DELAY_SECONDARY_LAST_ACTIVITY_REMOVAL);
        }

        public void setShouldDelayPrimaryLastActivityRemoval(boolean delay) {
            this.mDelayPrimaryLastActivityRemoval = delay;
        }

        public void setShouldDelaySecondaryLastActivityRemoval(boolean delay) {
            this.mDelaySecondaryLastActivityRemoval = delay;
        }

        public boolean shouldDelayPrimaryLastActivityRemoval() {
            return this.mDelayPrimaryLastActivityRemoval;
        }

        public boolean shouldDelaySecondaryLastActivityRemoval() {
            return this.mDelaySecondaryLastActivityRemoval;
        }

        Bundle toBundle() {
            Bundle b = new Bundle();
            b.putBoolean(DELAY_PRIMARY_LAST_ACTIVITY_REMOVAL, this.mDelayPrimaryLastActivityRemoval);
            b.putBoolean(DELAY_SECONDARY_LAST_ACTIVITY_REMOVAL, this.mDelaySecondaryLastActivityRemoval);
            return b;
        }
    }

    public static final class ContainerChange implements Parcelable {
        public static final Parcelable.Creator<ContainerChange> CREATOR = new Parcelable.Creator<ContainerChange>() { // from class: android.window.WindowContainerTransaction.ContainerChange.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ContainerChange createFromParcel(Parcel in) {
                return new ContainerChange(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ContainerChange[] newArray(int size) {
                return new ContainerChange[size];
            }
        };
        Change mChange;
        IBinder mToken;

        public ContainerChange() {
        }

        protected ContainerChange(Parcel in) {
            this.mToken = in.readStrongBinder();
            this.mChange = Change.CREATOR.createFromParcel(in);
        }

        public Change getChange() {
            return this.mChange;
        }

        public IBinder getToken() {
            return this.mToken;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeStrongBinder(this.mToken);
            this.mChange.writeToParcel(dest, flags);
        }
    }
}
