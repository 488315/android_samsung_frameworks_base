package android.window;

import android.app.Instrumentation;
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
import android.util.Log;
import android.view.InsetsFrameProvider;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.window.ITaskFragmentOrganizer;
import android.window.TaskFragmentOperation;
import android.window.WindowContainerTransaction;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public final class WindowContainerTransaction implements Parcelable {
    public static final int ADDITIONAL_FLAG_KEEP_ROTATION_DURING_TRANSITION = 1;
    public static final int ADDITIONAL_FLAG_TRANSITION_REASON_DIVIDER_DRAG_END = 2;
    public static final int ADDITIONAL_FLAG_TRANSITION_REASON_SPLIT_STACKING = 4;
    public static final int CHANGE_TRANSIT_REQUEST_FULLSCREEN_TO_SPLIT = 1;
    public static final int CHANGE_TRANSIT_REQUEST_FULLSCREEN_TO_SPLIT_ROTATION = 3;
    public static final int CHANGE_TRANSIT_REQUEST_SPLIT_TO_FULLSCREEN = 2;
    public static final int CHANGE_TRANSIT_REQUEST_UNDEFINED = 0;
    public static final Parcelable.Creator<WindowContainerTransaction> CREATOR = new Parcelable.Creator<WindowContainerTransaction>() { // from class: android.window.WindowContainerTransaction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContainerTransaction createFromParcel(Parcel parcel) {
            return new WindowContainerTransaction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContainerTransaction[] newArray(int i) {
            return new WindowContainerTransaction[i];
        }
    };
    public static final int TRANSACTION_TYPE_ACTIVATE_DESK = 4;
    public static final int TRANSACTION_TYPE_DISMISS_SPLIT_WITH_ALL_APPS = 7;
    public static final int TRANSACTION_TYPE_DISMISS_SPLIT_WITH_FREEFORM = 6;
    public static final int TRANSACTION_TYPE_START_INTENTS = 1;
    public static final int TRANSACTION_TYPE_START_TASKS = 3;
    public static final int TRANSACTION_TYPE_START_TASKS_FROM_RECENT = 5;
    public static final int TRANSACTION_TYPE_START_TASK_AND_INTENT = 2;
    public static final int TRANSACTION_TYPE_UNDEFINED = 0;
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String changeTransitRequestToString(int i) {
        if (i == 0) {
            return "CHANGE_TRANSIT_REQUEST_UNDEFINED";
        }
        if (i == 1) {
            return "CHANGE_TRANSIT_REQUEST_FULLSCREEN_TO_SPLIT";
        }
        if (i == 2) {
            return "CHANGE_TRANSIT_REQUEST_SPLIT_TO_FULLSCREEN";
        }
        return Integer.toString(i);
    }

    public WindowContainerTransaction setChangeFreeformStashMode(WindowContainerToken windowContainerToken, int i) {
        getOrCreateChange(windowContainerToken.asBinder()).mChangeFreeformStashMode = i;
        return this;
    }

    public WindowContainerTransaction setChangeFreeformStashScale(WindowContainerToken windowContainerToken, float f) {
        getOrCreateChange(windowContainerToken.asBinder()).mChangeFreeformStashScale = f;
        return this;
    }

    public WindowContainerTransaction requestForceTaskInfoChange(WindowContainerToken windowContainerToken) {
        getOrCreateChange(windowContainerToken.asBinder()).mForceTaskInfoChangeRequested = true;
        return this;
    }

    public boolean avoidReady() {
        return this.mAvoidReady;
    }

    public void setAvoidReady() {
        this.mAvoidReady = true;
    }

    public void addAdditionalInfo(int i) {
        this.mAdditionalFlag = i | this.mAdditionalFlag;
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

    private WindowContainerTransaction(Parcel parcel) throws ClassNotFoundException, IOException {
        ArrayMap<IBinder, Change> arrayMap = new ArrayMap<>();
        this.mChanges = arrayMap;
        ArrayList<HierarchyOp> arrayList = new ArrayList<>();
        this.mHierarchyOps = arrayList;
        this.mType = 0;
        this.mPositionChange = false;
        this.mDismissSplit = false;
        this.mTransferLeashMap = new ArrayMap<>();
        this.mMergedTransitionTokens = new ArrayList<>();
        this.mTransferTransitionTokens = new ArrayList<>();
        this.mChangeTransitionRequest = 0;
        this.mDisplayIdForChangeTransition = -1;
        ArrayList<ContainerChange> arrayList2 = new ArrayList<>();
        this.mChangeList = arrayList2;
        parcel.readMap(arrayMap, null);
        parcel.readTypedList(arrayList, HierarchyOp.CREATOR);
        this.mErrorCallbackToken = parcel.readStrongBinder();
        this.mTaskFragmentOrganizer = ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
        this.mType = parcel.readInt();
        this.mPositionChange = parcel.readBoolean();
        this.mDismissSplit = parcel.readBoolean();
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            this.mChangeTransitionRequest = parcel.readInt();
        }
        if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
            this.mDisplayIdForChangeTransition = parcel.readInt();
            this.mDisplayChangeTransitionReason = parcel.readString();
        }
        parcel.readTypedList(arrayList2, ContainerChange.CREATOR);
        if (CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER) {
            parcel.readMap(this.mTransferLeashMap, null);
        }
        if (CoreRune.MW_SHELL_TRANSITION) {
            this.mAvoidReady = parcel.readBoolean();
            this.mAdditionalFlag = parcel.readInt();
        }
        if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX) {
            this.mTransferTransitionTokens = parcel.readArrayList(null, IBinder.class);
            this.mMergedTransitionTokens = parcel.readArrayList(null, IBinder.class);
        }
    }

    public WindowContainerTransaction orderedSetWindowingMode(WindowContainerToken windowContainerToken, int i) {
        ContainerChange containerChange = new ContainerChange();
        containerChange.mToken = windowContainerToken.asBinder();
        containerChange.mChange = new Change();
        containerChange.mChange.mWindowingMode = i;
        this.mChangeList.add(containerChange);
        return this;
    }

    public WindowContainerTransaction orderedSetChangeTransitMode(WindowContainerToken windowContainerToken, int i, String str) {
        ContainerChange containerChange = new ContainerChange();
        containerChange.mToken = windowContainerToken.asBinder();
        containerChange.mChange = new Change();
        containerChange.mChange.mChangeTransitMode = i;
        containerChange.mChange.mChangeTransitReason = str;
        this.mChangeList.add(containerChange);
        return this;
    }

    public List<ContainerChange> getChangeList() {
        return this.mChangeList;
    }

    private Change getOrCreateChange(IBinder iBinder) {
        Change change = this.mChanges.get(iBinder);
        if (change != null) {
            return change;
        }
        Change change2 = new Change();
        this.mChanges.put(iBinder, change2);
        return change2;
    }

    public void clear() {
        this.mChanges.clear();
        this.mHierarchyOps.clear();
        this.mErrorCallbackToken = null;
        this.mTaskFragmentOrganizer = null;
    }

    public WindowContainerTransaction setBounds(WindowContainerToken windowContainerToken, Rect rect) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mConfiguration.windowConfiguration.setBounds(rect);
        orCreateChange.mConfigSetMask |= 536870912;
        orCreateChange.mWindowSetMask |= 1;
        return this;
    }

    public WindowContainerTransaction setAppBounds(WindowContainerToken windowContainerToken, Rect rect) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mConfiguration.windowConfiguration.setAppBounds(rect);
        orCreateChange.mConfigSetMask |= 536870912;
        orCreateChange.mWindowSetMask |= 2;
        return this;
    }

    public WindowContainerTransaction setScreenSizeDp(WindowContainerToken windowContainerToken, int i, int i2) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mConfiguration.screenWidthDp = i;
        orCreateChange.mConfiguration.screenHeightDp = i2;
        orCreateChange.mConfigSetMask |= 1024;
        return this;
    }

    public WindowContainerTransaction setDensityDpi(WindowContainerToken windowContainerToken, int i) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mConfiguration.densityDpi = i;
        orCreateChange.mConfigSetMask |= 4096;
        return this;
    }

    public WindowContainerTransaction setStagePosition(WindowContainerToken windowContainerToken, int i) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mConfiguration.windowConfiguration.setStagePosition(i);
        orCreateChange.mConfigSetMask |= 536870912;
        orCreateChange.mWindowSetMask |= 2097152;
        return this;
    }

    public WindowContainerTransaction setEmbedActivityMode(WindowContainerToken windowContainerToken, int i) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mConfiguration.windowConfiguration.setEmbedActivityMode(i);
        orCreateChange.mConfigSetMask |= 536870912;
        orCreateChange.mWindowSetMask |= 8388608;
        return this;
    }

    public WindowContainerTransaction setBoundsChangeTransaction(WindowContainerToken windowContainerToken, SurfaceControl.Transaction transaction) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mBoundsChangeTransaction = transaction;
        orCreateChange.mChangeMask |= 2;
        return this;
    }

    public WindowContainerTransaction setActivityWindowingMode(WindowContainerToken windowContainerToken, int i) {
        getOrCreateChange(windowContainerToken.asBinder()).mActivityWindowingMode = i;
        return this;
    }

    public WindowContainerTransaction setWindowingMode(WindowContainerToken windowContainerToken, int i) {
        getOrCreateChange(windowContainerToken.asBinder()).mWindowingMode = i;
        return this;
    }

    public WindowContainerTransaction setFocusable(WindowContainerToken windowContainerToken, boolean z) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mFocusable = z;
        orCreateChange.mChangeMask |= 1;
        return this;
    }

    public WindowContainerTransaction setExcludeImeInsets(WindowContainerToken windowContainerToken, boolean z) {
        this.mHierarchyOps.add(new HierarchyOp.Builder(21).setContainer(windowContainerToken.asBinder()).setExcludeInsetsTypes(z ? WindowInsets.Type.ime() : 0).build());
        return this;
    }

    public WindowContainerTransaction setHidden(WindowContainerToken windowContainerToken, boolean z) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mHidden = z;
        orCreateChange.mChangeMask |= 8;
        return this;
    }

    public WindowContainerTransaction setSmallestScreenWidthDp(WindowContainerToken windowContainerToken, int i) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mConfiguration.smallestScreenWidthDp = i;
        orCreateChange.mConfigSetMask |= 2048;
        return this;
    }

    public WindowContainerTransaction setIgnoreOrientationRequest(WindowContainerToken windowContainerToken, boolean z) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mIgnoreOrientationRequest = z;
        orCreateChange.mChangeMask |= 16;
        return this;
    }

    public WindowContainerTransaction setForceTranslucent(WindowContainerToken windowContainerToken, boolean z) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mForceTranslucent = z;
        orCreateChange.mChangeMask |= 64;
        return this;
    }

    public WindowContainerTransaction setDoNotPip(WindowContainerToken windowContainerToken) {
        getOrCreateChange(windowContainerToken.asBinder()).mChangeMask |= 32;
        return this;
    }

    public WindowContainerTransaction setRelativeBounds(WindowContainerToken windowContainerToken, Rect rect) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        if (orCreateChange.mRelativeBounds == null) {
            orCreateChange.mRelativeBounds = new Rect();
        }
        orCreateChange.mRelativeBounds.set(rect);
        orCreateChange.mChangeMask |= 256;
        orCreateChange.mConfigSetMask |= 536870912;
        orCreateChange.mWindowSetMask |= 1;
        return this;
    }

    public WindowContainerTransaction setDragResizing(WindowContainerToken windowContainerToken, boolean z) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mChangeMask |= 128;
        orCreateChange.mDragResizing = z;
        return this;
    }

    public WindowContainerTransaction setAlwaysOnTop(WindowContainerToken windowContainerToken, boolean z) {
        this.mHierarchyOps.add(new HierarchyOp.Builder(12).setContainer(windowContainerToken.asBinder()).setAlwaysOnTop(z).build());
        return this;
    }

    public WindowContainerTransaction setReparentLeafTaskIfRelaunch(WindowContainerToken windowContainerToken, boolean z) {
        this.mHierarchyOps.add(new HierarchyOp.Builder(16).setContainer(windowContainerToken.asBinder()).setReparentLeafTaskIfRelaunch(z).build());
        return this;
    }

    public WindowContainerTransaction deferConfigToTransitionEnd(WindowContainerToken windowContainerToken) {
        getOrCreateChange(windowContainerToken.asBinder()).mConfigAtTransitionEnd = true;
        return this;
    }

    public WindowContainerTransaction setTaskTrimmableFromRecents(WindowContainerToken windowContainerToken, boolean z) {
        this.mHierarchyOps.add(HierarchyOp.createForSetTaskTrimmableFromRecents(windowContainerToken.asBinder(), z));
        return this;
    }

    public WindowContainerTransaction setSafeRegionBounds(WindowContainerToken windowContainerToken, Rect rect) {
        this.mHierarchyOps.add(HierarchyOp.createForSetSafeRegionBounds(windowContainerToken.asBinder(), rect));
        return this;
    }

    public WindowContainerTransaction reorder(WindowContainerToken windowContainerToken, boolean z) {
        return reorder(windowContainerToken, z, false);
    }

    public WindowContainerTransaction reorder(WindowContainerToken windowContainerToken, boolean z, boolean z2) {
        this.mHierarchyOps.add(HierarchyOp.createForReorder(windowContainerToken.asBinder(), z, z2));
        return this;
    }

    public WindowContainerTransaction reparent(WindowContainerToken windowContainerToken, WindowContainerToken windowContainerToken2, boolean z) {
        this.mHierarchyOps.add(HierarchyOp.createForReparent(windowContainerToken.asBinder(), windowContainerToken2 == null ? null : windowContainerToken2.asBinder(), z));
        return this;
    }

    public WindowContainerTransaction reparentTasks(WindowContainerToken windowContainerToken, WindowContainerToken windowContainerToken2, int[] iArr, int[] iArr2, boolean z, boolean z2) {
        this.mHierarchyOps.add(HierarchyOp.createForChildrenTasksReparent(windowContainerToken != null ? windowContainerToken.asBinder() : null, windowContainerToken2 != null ? windowContainerToken2.asBinder() : null, iArr, iArr2, z, z2));
        return this;
    }

    public WindowContainerTransaction reparentTasks(WindowContainerToken windowContainerToken, WindowContainerToken windowContainerToken2, int[] iArr, int[] iArr2, boolean z) {
        return reparentTasks(windowContainerToken, windowContainerToken2, iArr, iArr2, z, false);
    }

    public WindowContainerTransaction removeTask(WindowContainerToken windowContainerToken) {
        this.mHierarchyOps.add(HierarchyOp.createForRemoveTask(windowContainerToken.asBinder()));
        return this;
    }

    public WindowContainerTransaction removeRootTask(WindowContainerToken windowContainerToken) {
        this.mHierarchyOps.add(HierarchyOp.createForRemoveRootTask(windowContainerToken.asBinder()));
        return this;
    }

    public WindowContainerTransaction restoreTransientOrder(WindowContainerToken windowContainerToken) {
        this.mHierarchyOps.add(new HierarchyOp.Builder(9).setContainer(windowContainerToken.asBinder()).build());
        return this;
    }

    public WindowContainerTransaction restoreBackNavi() {
        this.mHierarchyOps.add(new HierarchyOp.Builder(20).build());
        return this;
    }

    public WindowContainerTransaction startTask(int i, Bundle bundle) {
        if (Instrumentation.DEBUG_START_ACTIVITY) {
            Log.d(Instrumentation.TAG, "WCT.startTask: taskId=" + i + " options=" + bundle, new Throwable());
        }
        this.mHierarchyOps.add(HierarchyOp.createForTaskLaunch(i, bundle));
        return this;
    }

    public WindowContainerTransaction sendPendingIntent(PendingIntent pendingIntent, Intent intent, Bundle bundle) {
        if (Instrumentation.DEBUG_START_ACTIVITY) {
            Log.d(Instrumentation.TAG, "WCT.sendPendingIntent: sender=" + pendingIntent.getIntent() + " fillInIntent=" + intent + " options=" + bundle, new Throwable());
        }
        this.mHierarchyOps.add(new HierarchyOp.Builder(7).setLaunchOptions(bundle).setPendingIntent(pendingIntent).setActivityIntent(intent).build());
        return this;
    }

    public WindowContainerTransaction startShortcut(String str, ShortcutInfo shortcutInfo, Bundle bundle) {
        if (Instrumentation.DEBUG_START_ACTIVITY) {
            Log.d(Instrumentation.TAG, "WCT.startShortcut: shortcutInfo=" + shortcutInfo + " options=" + bundle, new Throwable());
        }
        this.mHierarchyOps.add(HierarchyOp.createForStartShortcut(str, shortcutInfo, bundle));
        return this;
    }

    public WindowContainerTransaction setLaunchRoot(WindowContainerToken windowContainerToken, int[] iArr, int[] iArr2) {
        this.mHierarchyOps.add(HierarchyOp.createForSetLaunchRoot(windowContainerToken.asBinder(), iArr, iArr2));
        return this;
    }

    @Deprecated
    public WindowContainerTransaction setAdjacentRoots(WindowContainerToken windowContainerToken, WindowContainerToken windowContainerToken2) {
        return setAdjacentRootSet(windowContainerToken, windowContainerToken2);
    }

    public WindowContainerTransaction setAdjacentRootSet(WindowContainerToken... windowContainerTokenArr) {
        if (windowContainerTokenArr.length < 2) {
            throw new IllegalArgumentException("setAdjacentRootSet must have size >= 2");
        }
        IBinder[] iBinderArr = new IBinder[windowContainerTokenArr.length];
        for (int i = 0; i < windowContainerTokenArr.length; i++) {
            iBinderArr[i] = windowContainerTokenArr[i].asBinder();
        }
        this.mHierarchyOps.add(new HierarchyOp.Builder(4).setContainers(iBinderArr).build());
        return this;
    }

    public WindowContainerTransaction clearAdjacentRoots(WindowContainerToken windowContainerToken) {
        this.mHierarchyOps.add(HierarchyOp.createForClearAdjacentRoots(windowContainerToken.asBinder()));
        return this;
    }

    public WindowContainerTransaction setLaunchAdjacentFlagRoot(WindowContainerToken windowContainerToken) {
        this.mHierarchyOps.add(HierarchyOp.createForSetLaunchAdjacentFlagRoot(windowContainerToken.asBinder(), false));
        return this;
    }

    public WindowContainerTransaction clearLaunchAdjacentFlagRoot(WindowContainerToken windowContainerToken) {
        this.mHierarchyOps.add(HierarchyOp.createForSetLaunchAdjacentFlagRoot(windowContainerToken.asBinder(), true));
        return this;
    }

    public WindowContainerTransaction setDisableLaunchAdjacent(WindowContainerToken windowContainerToken, boolean z) {
        this.mHierarchyOps.add(HierarchyOp.createForSetDisableLaunchAdjacent(windowContainerToken.asBinder(), z));
        return this;
    }

    public WindowContainerTransaction movePipActivityToPinnedRootTask(WindowContainerToken windowContainerToken, Rect rect) {
        this.mHierarchyOps.add(new HierarchyOp.Builder(18).setContainer(windowContainerToken.asBinder()).setBounds(rect).build());
        return this;
    }

    public WindowContainerTransaction scheduleFinishEnterPip(WindowContainerToken windowContainerToken, Rect rect) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mPinnedBounds = new Rect(rect);
        orCreateChange.mChangeMask |= 4;
        return this;
    }

    public WindowContainerTransaction addInsetsSource(WindowContainerToken windowContainerToken, IBinder iBinder, int i, int i2, Rect rect, Rect[] rectArr, int i3) {
        this.mHierarchyOps.add(new HierarchyOp.Builder(10).setContainer(windowContainerToken.asBinder()).setInsetsFrameProvider(new InsetsFrameProvider(iBinder, i, i2).setSource(3).setArbitraryRectangle(rect).setBoundingRects(rectArr).setFlags(i3)).setInsetsFrameOwner(iBinder).build());
        return this;
    }

    public WindowContainerTransaction removeInsetsSource(WindowContainerToken windowContainerToken, IBinder iBinder, int i, int i2) {
        this.mHierarchyOps.add(new HierarchyOp.Builder(11).setContainer(windowContainerToken.asBinder()).setInsetsFrameProvider(new InsetsFrameProvider(iBinder, i, i2)).setInsetsFrameOwner(iBinder).build());
        return this;
    }

    public WindowContainerTransaction closeTask(WindowContainerToken windowContainerToken) {
        this.mHierarchyOps.add(HierarchyOp.createForCloseTask(windowContainerToken.asBinder()));
        return this;
    }

    public WindowContainerTransaction addKeyguardState(KeyguardState keyguardState) {
        Objects.requireNonNull(keyguardState);
        this.mHierarchyOps.add(new HierarchyOp.Builder(22).setKeyguardState(keyguardState).build());
        return this;
    }

    public WindowContainerTransaction setTaskFragmentOrganizer(ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        this.mTaskFragmentOrganizer = iTaskFragmentOrganizer;
        return this;
    }

    public WindowContainerTransaction setErrorCallbackToken(IBinder iBinder) {
        if (this.mErrorCallbackToken != null) {
            throw new IllegalStateException("Can't set multiple error token for one transaction.");
        }
        this.mErrorCallbackToken = iBinder;
        return this;
    }

    public WindowContainerTransaction createTaskFragment(TaskFragmentCreationParams taskFragmentCreationParams) {
        return addTaskFragmentOperation(taskFragmentCreationParams.getFragmentToken(), new TaskFragmentOperation.Builder(0).setTaskFragmentCreationParams(taskFragmentCreationParams).build());
    }

    public WindowContainerTransaction deleteTaskFragment(IBinder iBinder) {
        return addTaskFragmentOperation(iBinder, new TaskFragmentOperation.Builder(1).build());
    }

    public WindowContainerTransaction startActivityInTaskFragment(IBinder iBinder, IBinder iBinder2, Intent intent, Bundle bundle) {
        return addTaskFragmentOperation(iBinder, new TaskFragmentOperation.Builder(2).setActivityToken(iBinder2).setActivityIntent(intent).setBundle(bundle).build());
    }

    public WindowContainerTransaction reparentActivityToTaskFragment(IBinder iBinder, IBinder iBinder2) {
        return addTaskFragmentOperation(iBinder, new TaskFragmentOperation.Builder(3).setActivityToken(iBinder2).build());
    }

    public WindowContainerTransaction setAdjacentTaskFragments(IBinder iBinder, IBinder iBinder2, TaskFragmentAdjacentParams taskFragmentAdjacentParams) {
        return addTaskFragmentOperation(iBinder, new TaskFragmentOperation.Builder(4).setSecondaryFragmentToken(iBinder2).setBundle(taskFragmentAdjacentParams != null ? taskFragmentAdjacentParams.toBundle() : null).build());
    }

    public WindowContainerTransaction clearAdjacentTaskFragments(IBinder iBinder) {
        return addTaskFragmentOperation(iBinder, new TaskFragmentOperation.Builder(5).build());
    }

    public WindowContainerTransaction requestFocusOnTaskFragment(IBinder iBinder) {
        return addTaskFragmentOperation(iBinder, new TaskFragmentOperation.Builder(6).build());
    }

    public WindowContainerTransaction finishActivity(IBinder iBinder) {
        this.mHierarchyOps.add(new HierarchyOp.Builder(14).setContainer(iBinder).build());
        return this;
    }

    public WindowContainerTransaction setCompanionTaskFragment(IBinder iBinder, IBinder iBinder2) {
        return addTaskFragmentOperation(iBinder, new TaskFragmentOperation.Builder(7).setSecondaryFragmentToken(iBinder2).build());
    }

    public WindowContainerTransaction addTaskFragmentOperation(IBinder iBinder, TaskFragmentOperation taskFragmentOperation) {
        Objects.requireNonNull(iBinder);
        Objects.requireNonNull(taskFragmentOperation);
        this.mHierarchyOps.add(new HierarchyOp.Builder(17).setContainer(iBinder).setTaskFragmentOperation(taskFragmentOperation).build());
        return this;
    }

    public WindowContainerTransaction setReachabilityOffset(WindowContainerToken windowContainerToken, int i, int i2, int i3) {
        this.mHierarchyOps.add(HierarchyOp.createForReachability(windowContainerToken.asBinder(), i, i2, i3));
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void merge(WindowContainerTransaction windowContainerTransaction, boolean z) {
        IBinder iBinder;
        int size = windowContainerTransaction.mChanges.size();
        int i = 0;
        while (true) {
            Object[] objArr = 0;
            if (i >= size) {
                break;
            }
            IBinder iBinderKeyAt = windowContainerTransaction.mChanges.keyAt(i);
            Change change = this.mChanges.get(iBinderKeyAt);
            if (change == null) {
                change = new Change();
                this.mChanges.put(iBinderKeyAt, change);
            }
            change.merge(windowContainerTransaction.mChanges.valueAt(i), z);
            i++;
        }
        int size2 = windowContainerTransaction.mHierarchyOps.size();
        for (int i2 = 0; i2 < size2; i2++) {
            HierarchyOp hierarchyOp = windowContainerTransaction.mHierarchyOps.get(i2);
            ArrayList<HierarchyOp> arrayList = this.mHierarchyOps;
            if (!z) {
                hierarchyOp = new HierarchyOp(hierarchyOp);
            }
            arrayList.add(hierarchyOp);
        }
        IBinder iBinder2 = this.mErrorCallbackToken;
        if (iBinder2 != null && (iBinder = windowContainerTransaction.mErrorCallbackToken) != null && iBinder2 != iBinder) {
            throw new IllegalArgumentException("Can't merge two WCTs with different error token");
        }
        ITaskFragmentOrganizer iTaskFragmentOrganizer = this.mTaskFragmentOrganizer;
        IBinder iBinderAsBinder = iTaskFragmentOrganizer != null ? iTaskFragmentOrganizer.asBinder() : null;
        ITaskFragmentOrganizer iTaskFragmentOrganizer2 = windowContainerTransaction.mTaskFragmentOrganizer;
        if (!Objects.equals(iBinderAsBinder, iTaskFragmentOrganizer2 != null ? iTaskFragmentOrganizer2.asBinder() : null)) {
            throw new IllegalArgumentException("Can't merge two WCTs from different TaskFragmentOrganizers");
        }
        IBinder iBinder3 = this.mErrorCallbackToken;
        if (iBinder3 == null) {
            iBinder3 = windowContainerTransaction.mErrorCallbackToken;
        }
        this.mErrorCallbackToken = iBinder3;
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

    public void setTransactionType(int i) {
        this.mType = i;
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

    public boolean isActivateDeskType() {
        return this.mType == 4;
    }

    public boolean isStartTasksFromRecentType() {
        return this.mType == 5;
    }

    public void setDismissSplit(boolean z) {
        this.mDismissSplit = z;
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

    public void setChangeStagePosition(boolean z) {
        this.mPositionChange = z;
    }

    public boolean isStagePositionChanged() {
        return this.mPositionChange;
    }

    public void addTransferLeash(SurfaceControl surfaceControl, SurfaceControl surfaceControl2) {
        this.mTransferLeashMap.put(surfaceControl, surfaceControl2);
    }

    public ArrayMap<SurfaceControl, SurfaceControl> getTransferLeashMap() {
        return this.mTransferLeashMap;
    }

    public void addTransferTransitionToken(IBinder iBinder) {
        this.mTransferTransitionTokens.add(iBinder);
    }

    public void addMergedTransitionToken(IBinder iBinder) {
        this.mMergedTransitionTokens.add(iBinder);
    }

    public ArrayList<IBinder> getTransferTransitionTokens() {
        return this.mTransferTransitionTokens;
    }

    public ArrayList<IBinder> getMergedTransitionTokens() {
        return this.mMergedTransitionTokens;
    }

    public void setTaskViewTaskOrganizerTaskId(WindowContainerToken windowContainerToken, int i) {
        getOrCreateChange(windowContainerToken.asBinder()).mTaskViewTaskOrganizerTaskId = i;
    }

    public void setSkipLayoutTask(WindowContainerToken windowContainerToken, boolean z) {
        getOrCreateChange(windowContainerToken.asBinder()).mSkipLayoutTask = z;
    }

    public void setFullscreenTransparentInDesktop(WindowContainerToken windowContainerToken, boolean z) {
        getOrCreateChange(windowContainerToken.asBinder()).mIsFullscreenTransparentInDesktop = z;
    }

    public void setCaptionShowingState(WindowContainerToken windowContainerToken, boolean z) {
        getOrCreateChange(windowContainerToken.asBinder()).mCaptionShowingState = z ? 1 : 2;
    }

    public String toString() {
        String str;
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION && changeTransitionRequested()) {
            str = " changeTransitRequest=" + changeTransitRequestToString(this.mChangeTransitionRequest);
        } else {
            str = "";
        }
        return "WindowContainerTransaction { changes= " + this.mChanges + " hops= " + this.mHierarchyOps + " errorCallbackToken=" + this.mErrorCallbackToken + " taskFragmentOrganizer=" + this.mTaskFragmentOrganizer + str + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeMap(this.mChanges);
        parcel.writeTypedList(this.mHierarchyOps);
        parcel.writeStrongBinder(this.mErrorCallbackToken);
        parcel.writeStrongInterface(this.mTaskFragmentOrganizer);
        parcel.writeInt(this.mType);
        parcel.writeBoolean(this.mPositionChange);
        parcel.writeBoolean(this.mDismissSplit);
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            parcel.writeInt(this.mChangeTransitionRequest);
        }
        if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
            parcel.writeInt(this.mDisplayIdForChangeTransition);
            parcel.writeString(this.mDisplayChangeTransitionReason);
        }
        parcel.writeTypedList(this.mChangeList);
        if (CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER) {
            parcel.writeMap(this.mTransferLeashMap);
        }
        if (CoreRune.MW_SHELL_TRANSITION) {
            parcel.writeBoolean(this.mAvoidReady);
            parcel.writeInt(this.mAdditionalFlag);
        }
        if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX) {
            parcel.writeList(this.mTransferTransitionTokens);
            parcel.writeList(this.mMergedTransitionTokens);
        }
    }

    public void setChangeTransitionRequest(int i) {
        this.mChangeTransitionRequest = i;
    }

    public boolean changeTransitionRequested() {
        return this.mChangeTransitionRequest != 0;
    }

    public int getChangeTransitionRequest() {
        return this.mChangeTransitionRequest;
    }

    public WindowContainerTransaction setChangeTransitMode(WindowContainerToken windowContainerToken, int i, String str) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mChangeTransitMode = i;
        orCreateChange.mChangeTransitReason = str;
        return this;
    }

    public boolean hasChangeTransitMode() {
        return this.mChanges.values().stream().anyMatch(new Predicate() { // from class: android.window.WindowContainerTransaction$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((WindowContainerTransaction.Change) obj).hasChangeTransitMode();
            }
        });
    }

    public WindowContainerTransaction addChangeTransitFlags(WindowContainerToken windowContainerToken, int i) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        orCreateChange.mChangeTransitFlags = i | orCreateChange.mChangeTransitFlags;
        return this;
    }

    public WindowContainerTransaction setChangeTransitStartBounds(WindowContainerToken windowContainerToken, Rect rect) {
        Change orCreateChange = getOrCreateChange(windowContainerToken.asBinder());
        if (orCreateChange.mChangeTransitStartBounds == null) {
            orCreateChange.mChangeTransitStartBounds = new Rect();
        }
        orCreateChange.mChangeTransitStartBounds.set(rect);
        return this;
    }

    public void setDisplayIdForChangeTransition(int i, String str) {
        this.mDisplayIdForChangeTransition = i;
        this.mDisplayChangeTransitionReason = str;
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

    public static class Change implements Parcelable {
        public static final int CHANGE_BOUNDS_TRANSACTION = 2;
        public static final int CHANGE_DRAG_RESIZING = 128;
        public static final int CHANGE_FOCUSABLE = 1;
        public static final int CHANGE_FORCE_NO_PIP = 32;
        public static final int CHANGE_FORCE_TRANSLUCENT = 64;
        public static final int CHANGE_HIDDEN = 8;
        public static final int CHANGE_IGNORE_ORIENTATION_REQUEST = 16;
        public static final int CHANGE_PIP_CALLBACK = 4;
        public static final int CHANGE_RELATIVE_BOUNDS = 256;
        public static final Parcelable.Creator<Change> CREATOR = new Parcelable.Creator<Change>() { // from class: android.window.WindowContainerTransaction.Change.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Change createFromParcel(Parcel parcel) {
                return new Change(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Change[] newArray(int i) {
                return new Change[i];
            }
        };
        private int mActivityWindowingMode;
        private SurfaceControl.Transaction mBoundsChangeTransaction;
        private int mCaptionShowingState;
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
        private boolean mIsFullscreenTransparentInDesktop;
        private Rect mPinnedBounds;
        private Rect mRelativeBounds;
        private boolean mSkipLayoutTask;
        private int mTaskViewTaskOrganizerTaskId;
        private int mWindowSetMask;
        private int mWindowingMode;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ChangeMask {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private Change() {
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
            this.mRelativeBounds = null;
            this.mConfigAtTransitionEnd = false;
            this.mActivityWindowingMode = -1;
            this.mWindowingMode = -1;
            this.mChangeTransitMode = 0;
            this.mChangeFreeformStashMode = 0;
            this.mTaskViewTaskOrganizerTaskId = -1;
            this.mSkipLayoutTask = false;
            this.mIsFullscreenTransparentInDesktop = false;
            this.mCaptionShowingState = 0;
        }

        private Change(Parcel parcel) {
            Configuration configuration = new Configuration();
            this.mConfiguration = configuration;
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
            this.mRelativeBounds = null;
            this.mConfigAtTransitionEnd = false;
            this.mActivityWindowingMode = -1;
            this.mWindowingMode = -1;
            this.mChangeTransitMode = 0;
            this.mChangeFreeformStashMode = 0;
            this.mTaskViewTaskOrganizerTaskId = -1;
            this.mSkipLayoutTask = false;
            this.mIsFullscreenTransparentInDesktop = false;
            this.mCaptionShowingState = 0;
            configuration.readFromParcel(parcel);
            this.mFocusable = parcel.readBoolean();
            this.mHidden = parcel.readBoolean();
            this.mIgnoreOrientationRequest = parcel.readBoolean();
            this.mForceTranslucent = parcel.readBoolean();
            this.mDragResizing = parcel.readBoolean();
            this.mChangeMask = parcel.readInt();
            this.mConfigSetMask = parcel.readInt();
            this.mWindowSetMask = parcel.readInt();
            if ((this.mChangeMask & 4) != 0) {
                Rect rect = new Rect();
                this.mPinnedBounds = rect;
                rect.readFromParcel(parcel);
            }
            if ((this.mChangeMask & 2) != 0) {
                this.mBoundsChangeTransaction = SurfaceControl.Transaction.CREATOR.createFromParcel(parcel);
            }
            if ((this.mChangeMask & 256) != 0) {
                Rect rect2 = new Rect();
                this.mRelativeBounds = rect2;
                rect2.readFromParcel(parcel);
            }
            this.mConfigAtTransitionEnd = parcel.readBoolean();
            this.mWindowingMode = parcel.readInt();
            this.mActivityWindowingMode = parcel.readInt();
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                this.mChangeTransitMode = parcel.readInt();
                this.mChangeTransitFlags = parcel.readInt();
                this.mChangeTransitReason = parcel.readString();
                this.mChangeTransitStartBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
            }
            if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
                this.mChangeFreeformStashMode = parcel.readInt();
                this.mChangeFreeformStashScale = parcel.readFloat();
                this.mForceTaskInfoChangeRequested = parcel.readBoolean();
            }
            this.mTaskViewTaskOrganizerTaskId = parcel.readInt();
            this.mSkipLayoutTask = parcel.readBoolean();
            this.mIsFullscreenTransparentInDesktop = parcel.readBoolean();
            if (CoreRune.MW_CAPTION_DESKTOP_DIMMING) {
                this.mCaptionShowingState = parcel.readInt();
            }
        }

        public void merge(Change change, boolean z) {
            this.mConfiguration.setTo(change.mConfiguration, change.mConfigSetMask, change.mWindowSetMask);
            this.mConfigSetMask |= change.mConfigSetMask;
            this.mWindowSetMask |= change.mWindowSetMask;
            int i = change.mChangeMask;
            if ((i & 1) != 0) {
                this.mFocusable = change.mFocusable;
            }
            if (z && (i & 2) != 0) {
                this.mBoundsChangeTransaction = change.mBoundsChangeTransaction;
                change.mBoundsChangeTransaction = null;
            }
            if ((i & 4) != 0) {
                this.mPinnedBounds = z ? change.mPinnedBounds : new Rect(change.mPinnedBounds);
            }
            int i2 = change.mChangeMask;
            if ((i2 & 8) != 0) {
                this.mHidden = change.mHidden;
            }
            if ((i2 & 16) != 0) {
                this.mIgnoreOrientationRequest = change.mIgnoreOrientationRequest;
            }
            if ((i2 & 64) != 0) {
                this.mForceTranslucent = change.mForceTranslucent;
            }
            if ((i2 & 128) != 0) {
                this.mDragResizing = change.mDragResizing;
            }
            this.mChangeMask = i2 | this.mChangeMask;
            int i3 = change.mActivityWindowingMode;
            if (i3 >= 0) {
                this.mActivityWindowingMode = i3;
            }
            int i4 = change.mWindowingMode;
            if (i4 >= 0) {
                this.mWindowingMode = i4;
            }
            Rect rect = change.mRelativeBounds;
            if (rect != null) {
                if (!z) {
                    rect = new Rect(change.mRelativeBounds);
                }
                this.mRelativeBounds = rect;
            }
            this.mConfigAtTransitionEnd = this.mConfigAtTransitionEnd || change.mConfigAtTransitionEnd;
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION && change.hasChangeTransitMode()) {
                this.mChangeTransitMode = change.mChangeTransitMode;
                this.mChangeTransitReason = change.mChangeTransitReason;
                int i5 = change.mChangeTransitFlags;
                if (i5 != 0) {
                    this.mChangeTransitFlags = i5 | this.mChangeTransitFlags;
                }
                Rect rect2 = change.mChangeTransitStartBounds;
                if (rect2 != null) {
                    if (!z) {
                        rect2 = new Rect(change.mChangeTransitStartBounds);
                    }
                    this.mChangeTransitStartBounds = rect2;
                }
            }
            if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
                if (change.hasChangeFreeformStashMode()) {
                    this.mChangeFreeformStashMode = change.getChangeFreeformStashMode();
                }
                if (change.hasChangeFreeformStashScale()) {
                    this.mChangeFreeformStashScale = change.getChangeFreeformStashScale();
                }
                if (change.isForceTaskInfoChangeRequested()) {
                    this.mForceTaskInfoChangeRequested = true;
                }
            }
            if (change.isOrganizedTaskViewTask()) {
                this.mTaskViewTaskOrganizerTaskId = change.mTaskViewTaskOrganizerTaskId;
            }
            this.mSkipLayoutTask = change.mSkipLayoutTask;
            this.mIsFullscreenTransparentInDesktop = change.mIsFullscreenTransparentInDesktop;
            if (CoreRune.MW_CAPTION_DESKTOP_DIMMING) {
                this.mCaptionShowingState = change.mCaptionShowingState;
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
            if ((this.mChangeMask & 16) == 0) {
                throw new RuntimeException("IgnoreOrientationRequest not set. Check CHANGE_IGNORE_ORIENTATION_REQUEST first");
            }
            return this.mIgnoreOrientationRequest;
        }

        public boolean getForceTranslucent() {
            if ((this.mChangeMask & 64) == 0) {
                throw new RuntimeException("Force translucent not set. Check CHANGE_FORCE_TRANSLUCENT first");
            }
            return this.mForceTranslucent;
        }

        public boolean getDragResizing() {
            if ((this.mChangeMask & 128) == 0) {
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

        public boolean hasChangeTransitionFlags(int i) {
            return (this.mChangeTransitFlags & i) != 0;
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

        public void setSkipLayoutTask(boolean z) {
            this.mSkipLayoutTask = z;
        }

        public boolean skipLayoutTask() {
            return this.mSkipLayoutTask;
        }

        public boolean isFullscreenTransparentInDesktop() {
            return this.mIsFullscreenTransparentInDesktop;
        }

        public boolean hasChangeCaptionShowingState() {
            return this.mCaptionShowingState != 0;
        }

        public int getChangeCaptionShowingState() {
            return this.mCaptionShowingState;
        }

        public String toString() {
            int i = this.mConfigSetMask;
            boolean z = ((i & 536870912) == 0 || (this.mWindowSetMask & 1) == 0) ? false : true;
            boolean z2 = ((536870912 & i) == 0 || (this.mWindowSetMask & 2) == 0) ? false : true;
            boolean z3 = (i & 1024) != 0;
            boolean z4 = (i & 2048) != 0;
            boolean z5 = (this.mWindowSetMask & 2097152) != 0;
            StringBuilder sb = new StringBuilder("{");
            if (z) {
                sb.append("bounds:" + this.mConfiguration.windowConfiguration.getBounds() + ",");
            }
            if (z2) {
                sb.append("appbounds:" + this.mConfiguration.windowConfiguration.getAppBounds() + ",");
            }
            if (z4) {
                sb.append("ssw:" + this.mConfiguration.smallestScreenWidthDp + ",");
            }
            if (z3) {
                sb.append("sw/h:" + this.mConfiguration.screenWidthDp + "x" + this.mConfiguration.screenHeightDp + ",");
            }
            if ((this.mChangeMask & 1) != 0) {
                sb.append("focusable:" + this.mFocusable + ",");
            }
            if ((this.mChangeMask & 64) != 0) {
                sb.append("forceTranslucent:" + this.mForceTranslucent + ",");
            }
            if ((this.mChangeMask & 8) != 0) {
                sb.append("hidden:" + this.mHidden + ",");
            }
            if ((this.mChangeMask & 128) != 0) {
                sb.append("dragResizing:" + this.mDragResizing + ",");
            }
            if (this.mBoundsChangeTransaction != null) {
                sb.append("hasBoundsTransaction,");
            }
            if ((this.mChangeMask & 16) != 0) {
                sb.append("ignoreOrientationRequest:" + this.mIgnoreOrientationRequest + ",");
            }
            if ((this.mChangeMask & 256) != 0) {
                sb.append("relativeBounds:");
                sb.append(this.mRelativeBounds);
                sb.append(",");
            }
            if (this.mConfigAtTransitionEnd) {
                sb.append("configAtTransitionEnd,");
            }
            if (z5) {
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
            if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
                if (hasChangeFreeformStashMode()) {
                    sb.append("changeFreeformStashMode:" + this.mChangeFreeformStashMode + ",");
                }
                if (hasChangeFreeformStashScale()) {
                    sb.append("changeFreeformStashScale:" + this.mChangeFreeformStashScale + ",");
                }
            }
            sb.append("}");
            return sb.toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            this.mConfiguration.writeToParcel(parcel, i);
            parcel.writeBoolean(this.mFocusable);
            parcel.writeBoolean(this.mHidden);
            parcel.writeBoolean(this.mIgnoreOrientationRequest);
            parcel.writeBoolean(this.mForceTranslucent);
            parcel.writeBoolean(this.mDragResizing);
            parcel.writeInt(this.mChangeMask);
            parcel.writeInt(this.mConfigSetMask);
            parcel.writeInt(this.mWindowSetMask);
            Rect rect = this.mPinnedBounds;
            if (rect != null) {
                rect.writeToParcel(parcel, i);
            }
            SurfaceControl.Transaction transaction = this.mBoundsChangeTransaction;
            if (transaction != null) {
                transaction.writeToParcel(parcel, i);
            }
            Rect rect2 = this.mRelativeBounds;
            if (rect2 != null) {
                rect2.writeToParcel(parcel, i);
            }
            parcel.writeBoolean(this.mConfigAtTransitionEnd);
            parcel.writeInt(this.mWindowingMode);
            parcel.writeInt(this.mActivityWindowingMode);
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                parcel.writeInt(this.mChangeTransitMode);
                parcel.writeInt(this.mChangeTransitFlags);
                parcel.writeString(this.mChangeTransitReason);
                parcel.writeTypedObject(this.mChangeTransitStartBounds, i);
            }
            if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
                parcel.writeInt(this.mChangeFreeformStashMode);
                parcel.writeFloat(this.mChangeFreeformStashScale);
                parcel.writeBoolean(this.mForceTaskInfoChangeRequested);
            }
            parcel.writeInt(this.mTaskViewTaskOrganizerTaskId);
            parcel.writeBoolean(this.mSkipLayoutTask);
            parcel.writeBoolean(this.mIsFullscreenTransparentInDesktop);
            if (CoreRune.MW_CAPTION_DESKTOP_DIMMING) {
                parcel.writeInt(this.mCaptionShowingState);
            }
        }
    }

    public static final class HierarchyOp implements Parcelable {
        public static final Parcelable.Creator<HierarchyOp> CREATOR = new Parcelable.Creator<HierarchyOp>() { // from class: android.window.WindowContainerTransaction.HierarchyOp.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HierarchyOp createFromParcel(Parcel parcel) {
                return new HierarchyOp(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HierarchyOp[] newArray(int i) {
                return new HierarchyOp[i];
            }
        };
        public static final int HIERARCHY_OP_TYPE_ADD_INSETS_FRAME_PROVIDER = 10;
        public static final int HIERARCHY_OP_TYPE_ADD_TASK_FRAGMENT_OPERATION = 17;
        public static final int HIERARCHY_OP_TYPE_APP_COMPAT_REACHABILITY = 25;
        public static final int HIERARCHY_OP_TYPE_CHILDREN_TASKS_REPARENT = 2;
        public static final int HIERARCHY_OP_TYPE_CLEAR_ADJACENT_ROOTS = 15;
        public static final int HIERARCHY_OP_TYPE_CLOSE_TASK = 100;
        public static final int HIERARCHY_OP_TYPE_FINISH_ACTIVITY = 14;
        public static final int HIERARCHY_OP_TYPE_LAUNCH_TASK = 5;
        public static final int HIERARCHY_OP_TYPE_MOVE_PIP_ACTIVITY_TO_PINNED_TASK = 18;
        public static final int HIERARCHY_OP_TYPE_PENDING_INTENT = 7;
        public static final int HIERARCHY_OP_TYPE_REMOVE_INSETS_FRAME_PROVIDER = 11;
        public static final int HIERARCHY_OP_TYPE_REMOVE_ROOT_TASK = 24;
        public static final int HIERARCHY_OP_TYPE_REMOVE_TASK = 13;
        public static final int HIERARCHY_OP_TYPE_REORDER = 1;
        public static final int HIERARCHY_OP_TYPE_REPARENT = 0;
        public static final int HIERARCHY_OP_TYPE_RESTORE_BACK_NAVIGATION = 20;
        public static final int HIERARCHY_OP_TYPE_RESTORE_TRANSIENT_ORDER = 9;
        public static final int HIERARCHY_OP_TYPE_SET_ADJACENT_ROOTS = 4;
        public static final int HIERARCHY_OP_TYPE_SET_ALWAYS_ON_TOP = 12;
        public static final int HIERARCHY_OP_TYPE_SET_DISABLE_LAUNCH_ADJACENT = 23;
        public static final int HIERARCHY_OP_TYPE_SET_EXCLUDE_INSETS_TYPES = 21;
        public static final int HIERARCHY_OP_TYPE_SET_IS_TRIMMABLE = 19;
        public static final int HIERARCHY_OP_TYPE_SET_KEYGUARD_STATE = 22;
        public static final int HIERARCHY_OP_TYPE_SET_LAUNCH_ADJACENT_FLAG_ROOT = 6;
        public static final int HIERARCHY_OP_TYPE_SET_LAUNCH_ROOT = 3;
        public static final int HIERARCHY_OP_TYPE_SET_REPARENT_LEAF_TASK_IF_RELAUNCH = 16;
        public static final int HIERARCHY_OP_TYPE_SET_SAFE_REGION_BOUNDS = 26;
        public static final int HIERARCHY_OP_TYPE_START_SHORTCUT = 8;
        public static final String LAUNCH_KEY_SHORTCUT_CALLING_PACKAGE = "android:transaction.hop.shortcut_calling_package";
        public static final String LAUNCH_KEY_TASK_ID = "android:transaction.hop.taskId";
        public static final String REACHABILITY_EVENT_X = "android:transaction.reachability_x";
        public static final String REACHABILITY_EVENT_Y = "android:transaction.reachability_y";
        private Intent mActivityIntent;
        private int[] mActivityTypes;
        private boolean mAlwaysOnTop;
        private Bundle mAppCompatOptions;
        private Rect mBounds;
        private IBinder mContainer;
        private IBinder[] mContainers;
        private int mExcludeInsetsTypes;
        private boolean mIncludingParents;
        private IBinder mInsetsFrameOwner;
        private InsetsFrameProvider mInsetsFrameProvider;
        private boolean mIsTrimmableFromRecents;
        private KeyguardState mKeyguardState;
        private boolean mLaunchAdjacentDisabled;
        private Bundle mLaunchOptions;
        private PendingIntent mPendingIntent;
        private IBinder mReparent;
        private boolean mReparentLeafTaskIfRelaunch;
        private boolean mReparentTopOnly;
        private Rect mSafeRegionBounds;
        private ShortcutInfo mShortcutInfo;
        private TaskFragmentOperation mTaskFragmentOperation;
        private boolean mToTop;
        private final int mType;
        private int[] mWindowingModes;

        @Retention(RetentionPolicy.SOURCE)
        public @interface HierarchyOpType {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public static HierarchyOp createForReparent(IBinder iBinder, IBinder iBinder2, boolean z) {
            return new Builder(0).setContainer(iBinder).setReparentContainer(iBinder2).setToTop(z).build();
        }

        public static HierarchyOp createForReorder(IBinder iBinder, boolean z, boolean z2) {
            return new Builder(1).setContainer(iBinder).setReparentContainer(iBinder).setToTop(z).setIncludingParents(z2).build();
        }

        public static HierarchyOp createForChildrenTasksReparent(IBinder iBinder, IBinder iBinder2, int[] iArr, int[] iArr2, boolean z, boolean z2) {
            return new Builder(2).setContainer(iBinder).setReparentContainer(iBinder2).setWindowingModes(iArr).setActivityTypes(iArr2).setToTop(z).setReparentTopOnly(z2).build();
        }

        public static HierarchyOp createForSetLaunchRoot(IBinder iBinder, int[] iArr, int[] iArr2) {
            return new Builder(3).setContainer(iBinder).setWindowingModes(iArr).setActivityTypes(iArr2).build();
        }

        public static HierarchyOp createForAdjacentRoots(IBinder iBinder, IBinder iBinder2) {
            return new Builder(4).setContainer(iBinder).setReparentContainer(iBinder2).build();
        }

        public static HierarchyOp createForTaskLaunch(int i, Bundle bundle) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putInt(LAUNCH_KEY_TASK_ID, i);
            return new Builder(5).setToTop(true).setLaunchOptions(bundle).build();
        }

        public static HierarchyOp createForStartShortcut(String str, ShortcutInfo shortcutInfo, Bundle bundle) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString(LAUNCH_KEY_SHORTCUT_CALLING_PACKAGE, str);
            return new Builder(8).setShortcutInfo(shortcutInfo).setLaunchOptions(bundle).build();
        }

        public static HierarchyOp createForSetLaunchAdjacentFlagRoot(IBinder iBinder, boolean z) {
            return new Builder(6).setContainer(iBinder).setToTop(z).build();
        }

        public static HierarchyOp createForSetDisableLaunchAdjacent(IBinder iBinder, boolean z) {
            return new Builder(23).setContainer(iBinder).setLaunchAdjacentDisabled(z).build();
        }

        public static HierarchyOp createForRemoveTask(IBinder iBinder) {
            return new Builder(13).setContainer(iBinder).build();
        }

        public static HierarchyOp createForRemoveRootTask(IBinder iBinder) {
            return new Builder(24).setContainer(iBinder).build();
        }

        public static HierarchyOp createForCloseTask(IBinder iBinder) {
            return new Builder(100).setContainer(iBinder).build();
        }

        public static HierarchyOp createForClearAdjacentRoots(IBinder iBinder) {
            return new Builder(15).setContainer(iBinder).build();
        }

        public static HierarchyOp createForReachability(IBinder iBinder, int i, int i2, int i3) {
            Bundle bundle = new Bundle();
            bundle.putInt(LAUNCH_KEY_TASK_ID, i);
            bundle.putInt(REACHABILITY_EVENT_X, i2);
            bundle.putInt(REACHABILITY_EVENT_Y, i3);
            return new Builder(25).setAppCompatOptions(bundle).setContainer(iBinder).build();
        }

        public static HierarchyOp createForSetTaskTrimmableFromRecents(IBinder iBinder, boolean z) {
            return new Builder(19).setContainer(iBinder).setIsTrimmableFromRecents(z).build();
        }

        public static HierarchyOp createForSetSafeRegionBounds(IBinder iBinder, Rect rect) {
            return new Builder(26).setContainer(iBinder).setSafeRegionBounds(rect).build();
        }

        private HierarchyOp(int i) {
            this.mType = i;
        }

        public HierarchyOp(HierarchyOp hierarchyOp) {
            this.mType = hierarchyOp.mType;
            this.mContainer = hierarchyOp.mContainer;
            this.mContainers = hierarchyOp.mContainers;
            this.mBounds = hierarchyOp.mBounds;
            this.mIncludingParents = hierarchyOp.mIncludingParents;
            this.mReparent = hierarchyOp.mReparent;
            this.mInsetsFrameProvider = hierarchyOp.mInsetsFrameProvider;
            this.mInsetsFrameOwner = hierarchyOp.mInsetsFrameOwner;
            this.mToTop = hierarchyOp.mToTop;
            this.mReparentTopOnly = hierarchyOp.mReparentTopOnly;
            this.mWindowingModes = hierarchyOp.mWindowingModes;
            this.mActivityTypes = hierarchyOp.mActivityTypes;
            this.mLaunchOptions = hierarchyOp.mLaunchOptions;
            this.mAppCompatOptions = hierarchyOp.mAppCompatOptions;
            this.mActivityIntent = hierarchyOp.mActivityIntent;
            this.mTaskFragmentOperation = hierarchyOp.mTaskFragmentOperation;
            this.mKeyguardState = hierarchyOp.mKeyguardState;
            this.mPendingIntent = hierarchyOp.mPendingIntent;
            this.mShortcutInfo = hierarchyOp.mShortcutInfo;
            this.mAlwaysOnTop = hierarchyOp.mAlwaysOnTop;
            this.mReparentLeafTaskIfRelaunch = hierarchyOp.mReparentLeafTaskIfRelaunch;
            this.mIsTrimmableFromRecents = hierarchyOp.mIsTrimmableFromRecents;
            this.mExcludeInsetsTypes = hierarchyOp.mExcludeInsetsTypes;
            this.mLaunchAdjacentDisabled = hierarchyOp.mLaunchAdjacentDisabled;
            this.mSafeRegionBounds = hierarchyOp.mSafeRegionBounds;
        }

        private HierarchyOp(Parcel parcel) {
            this.mType = parcel.readInt();
            this.mContainer = parcel.readStrongBinder();
            this.mContainers = parcel.createBinderArray();
            this.mBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
            this.mIncludingParents = parcel.readBoolean();
            this.mReparent = parcel.readStrongBinder();
            this.mInsetsFrameProvider = (InsetsFrameProvider) parcel.readTypedObject(InsetsFrameProvider.CREATOR);
            this.mInsetsFrameOwner = parcel.readStrongBinder();
            this.mToTop = parcel.readBoolean();
            this.mReparentTopOnly = parcel.readBoolean();
            this.mWindowingModes = parcel.createIntArray();
            this.mActivityTypes = parcel.createIntArray();
            this.mLaunchOptions = parcel.readBundle();
            this.mAppCompatOptions = parcel.readBundle(getClass().getClassLoader());
            this.mActivityIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            this.mTaskFragmentOperation = (TaskFragmentOperation) parcel.readTypedObject(TaskFragmentOperation.CREATOR);
            this.mKeyguardState = (KeyguardState) parcel.readTypedObject(KeyguardState.CREATOR);
            this.mPendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
            this.mShortcutInfo = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
            this.mAlwaysOnTop = parcel.readBoolean();
            this.mReparentLeafTaskIfRelaunch = parcel.readBoolean();
            this.mIsTrimmableFromRecents = parcel.readBoolean();
            this.mExcludeInsetsTypes = parcel.readInt();
            this.mLaunchAdjacentDisabled = parcel.readBoolean();
            this.mSafeRegionBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
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

        public IBinder[] getContainers() {
            return this.mContainers;
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

        public Bundle getAppCompatOptions() {
            return this.mAppCompatOptions;
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

        public KeyguardState getKeyguardState() {
            return this.mKeyguardState;
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

        public boolean isTrimmableFromRecents() {
            return this.mIsTrimmableFromRecents;
        }

        public int getExcludeInsetsTypes() {
            return this.mExcludeInsetsTypes;
        }

        public boolean isLaunchAdjacentDisabled() {
            return this.mLaunchAdjacentDisabled;
        }

        public Rect getSafeRegionBounds() {
            return this.mSafeRegionBounds;
        }

        public static String hopToString(int i) {
            if (i == 26) {
                return "setSafeRegionBounds";
            }
            if (i == 100) {
                return "CloseTask";
            }
            switch (i) {
                case 0:
                    return "reparent";
                case 1:
                    return "reorder";
                case 2:
                    return "childrenTasksReparent";
                case 3:
                    return "setLaunchRoot";
                case 4:
                    return "setAdjacentRoots";
                case 5:
                    return "launchTask";
                case 6:
                    return "setAdjacentFlagRoot";
                case 7:
                    return "pendingIntent";
                case 8:
                    return "startShortcut";
                case 9:
                    return "restoreTransientOrder";
                case 10:
                    return "addInsetsFrameProvider";
                case 11:
                    return "removeInsetsFrameProvider";
                case 12:
                    return "setAlwaysOnTop";
                case 13:
                    return "removeTask";
                case 14:
                    return "finishActivity";
                case 15:
                    return "clearAdjacentRoots";
                case 16:
                    return "setReparentLeafTaskIfRelaunch";
                case 17:
                    return "addTaskFragmentOperation";
                case 18:
                    return "movePipActivityToPinnedTask";
                case 19:
                    return "setIsTrimmable";
                case 20:
                    return "restoreBackNav";
                case 21:
                    return "setExcludeInsetsTypes";
                case 22:
                    return "setKeyguardState";
                case 23:
                    return "setDisableLaunchAdjacent";
                case 24:
                    return "removeRootTask";
                default:
                    return "HOP(" + i + NavigationBarInflaterView.KEY_CODE_END;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:46:0x022b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            sb.append(hopToString(this.mType));
            sb.append(": ");
            int i = this.mType;
            if (i == 19) {
                sb.append("container= ");
                sb.append(this.mContainer);
                sb.append(" isTrimmable= ");
                sb.append(this.mIsTrimmableFromRecents);
            } else if (i != 100) {
                switch (i) {
                    case 0:
                        sb.append(this.mContainer);
                        sb.append(" to ");
                        sb.append(this.mToTop ? "top of " : "bottom of ");
                        sb.append(this.mReparent);
                        break;
                    case 1:
                        sb.append(this.mContainer);
                        sb.append(" to ");
                        sb.append(this.mToTop ? GenerateXML.TOP : GenerateXML.BOTTOM);
                        break;
                    case 2:
                        sb.append("from=");
                        sb.append(this.mContainer);
                        sb.append(" to=");
                        sb.append(this.mReparent);
                        sb.append(" mToTop=");
                        sb.append(this.mToTop);
                        sb.append(" mReparentTopOnly=");
                        sb.append(this.mReparentTopOnly);
                        sb.append(" mWindowingMode=");
                        sb.append(Arrays.toString(this.mWindowingModes));
                        sb.append(" mActivityType=");
                        sb.append(Arrays.toString(this.mActivityTypes));
                        break;
                    case 3:
                        sb.append("container=");
                        sb.append(this.mContainer);
                        sb.append(" mWindowingMode=");
                        sb.append(Arrays.toString(this.mWindowingModes));
                        sb.append(" mActivityType=");
                        sb.append(Arrays.toString(this.mActivityTypes));
                        break;
                    case 4:
                        for (IBinder iBinder : this.mContainers) {
                            if (iBinder == this.mContainers[0]) {
                                sb.append("adjacentRoots=");
                                sb.append(iBinder);
                            } else {
                                sb.append(", ");
                                sb.append(iBinder);
                            }
                        }
                        break;
                    case 5:
                        sb.append(this.mLaunchOptions);
                        break;
                    case 6:
                        sb.append("container=");
                        sb.append(this.mContainer);
                        sb.append(" clearRoot=");
                        sb.append(this.mToTop);
                        break;
                    case 7:
                        sb.append("options=");
                        sb.append(this.mLaunchOptions);
                        break;
                    case 8:
                        sb.append("options=");
                        sb.append(this.mLaunchOptions);
                        sb.append(" info=");
                        sb.append(this.mShortcutInfo);
                        break;
                    default:
                        switch (i) {
                            case 10:
                            case 11:
                                sb.append("container=");
                                sb.append(this.mContainer);
                                sb.append(" provider=");
                                sb.append(this.mInsetsFrameProvider);
                                sb.append(" owner=");
                                sb.append(this.mInsetsFrameOwner);
                                break;
                            case 12:
                                sb.append("container=");
                                sb.append(this.mContainer);
                                sb.append(" alwaysOnTop=");
                                sb.append(this.mAlwaysOnTop);
                                break;
                            case 13:
                                sb.append("task=");
                                sb.append(this.mContainer);
                                break;
                            case 14:
                                sb.append("activity=");
                                sb.append(this.mContainer);
                                break;
                            case 15:
                                sb.append("container=");
                                sb.append(this.mContainer);
                                break;
                            case 16:
                                sb.append("container= ");
                                sb.append(this.mContainer);
                                sb.append(" reparentLeafTaskIfRelaunch= ");
                                sb.append(this.mReparentLeafTaskIfRelaunch);
                                break;
                            case 17:
                                sb.append("fragmentToken= ");
                                sb.append(this.mContainer);
                                sb.append(" operation= ");
                                sb.append(this.mTaskFragmentOperation);
                                break;
                            default:
                                switch (i) {
                                    case 21:
                                        sb.append("container= ");
                                        sb.append(this.mContainer);
                                        sb.append(" mExcludeInsetsTypes= ");
                                        sb.append(WindowInsets.Type.toString(this.mExcludeInsetsTypes));
                                        break;
                                    case 22:
                                        sb.append("KeyguardState= ");
                                        sb.append(this.mKeyguardState);
                                        break;
                                    case 23:
                                        sb.append("container=");
                                        sb.append(this.mContainer);
                                        sb.append(" disabled=");
                                        sb.append(this.mLaunchAdjacentDisabled);
                                        break;
                                    case 24:
                                        sb.append("rootTask=");
                                        sb.append(this.mContainer);
                                        break;
                                    case 25:
                                        sb.append(this.mAppCompatOptions);
                                        break;
                                    case 26:
                                        sb.append("container= ");
                                        sb.append(this.mContainer);
                                        sb.append(" safeRegionBounds= ");
                                        sb.append(this.mSafeRegionBounds);
                                        break;
                                    default:
                                        sb.append("container=");
                                        sb.append(this.mContainer);
                                        sb.append(" reparent=");
                                        sb.append(this.mReparent);
                                        sb.append(" mToTop=");
                                        sb.append(this.mToTop);
                                        sb.append(" mWindowingMode=");
                                        sb.append(Arrays.toString(this.mWindowingModes));
                                        sb.append(" mActivityType=");
                                        sb.append(Arrays.toString(this.mActivityTypes));
                                        break;
                                }
                        }
                }
            }
            sb.append("}");
            return sb.toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mType);
            parcel.writeStrongBinder(this.mContainer);
            parcel.writeBinderArray(this.mContainers);
            parcel.writeTypedObject(this.mBounds, i);
            parcel.writeBoolean(this.mIncludingParents);
            parcel.writeStrongBinder(this.mReparent);
            parcel.writeTypedObject(this.mInsetsFrameProvider, i);
            parcel.writeStrongBinder(this.mInsetsFrameOwner);
            parcel.writeBoolean(this.mToTop);
            parcel.writeBoolean(this.mReparentTopOnly);
            parcel.writeIntArray(this.mWindowingModes);
            parcel.writeIntArray(this.mActivityTypes);
            parcel.writeBundle(this.mLaunchOptions);
            parcel.writeBundle(this.mAppCompatOptions);
            parcel.writeTypedObject(this.mActivityIntent, i);
            parcel.writeTypedObject(this.mTaskFragmentOperation, i);
            parcel.writeTypedObject(this.mKeyguardState, i);
            parcel.writeTypedObject(this.mPendingIntent, i);
            parcel.writeTypedObject(this.mShortcutInfo, i);
            parcel.writeBoolean(this.mAlwaysOnTop);
            parcel.writeBoolean(this.mReparentLeafTaskIfRelaunch);
            parcel.writeBoolean(this.mIsTrimmableFromRecents);
            parcel.writeInt(this.mExcludeInsetsTypes);
            parcel.writeBoolean(this.mLaunchAdjacentDisabled);
            parcel.writeTypedObject(this.mSafeRegionBounds, i);
        }

        private static class Builder {
            private Intent mActivityIntent;
            private int[] mActivityTypes;
            private boolean mAlwaysOnTop;
            private Bundle mAppCompatOptions;
            private Rect mBounds;
            private IBinder mContainer;
            private IBinder[] mContainers;
            private int mExcludeInsetsTypes;
            private boolean mIncludingParents;
            private IBinder mInsetsFrameOwner;
            private InsetsFrameProvider mInsetsFrameProvider;
            private boolean mIsTrimmableFromRecents;
            private KeyguardState mKeyguardState;
            private boolean mLaunchAdjacentDisabled;
            private Bundle mLaunchOptions;
            private PendingIntent mPendingIntent;
            private IBinder mReparent;
            private boolean mReparentLeafTaskIfRelaunch;
            private boolean mReparentTopOnly;
            private Rect mSafeRegionBounds;
            private ShortcutInfo mShortcutInfo;
            private TaskFragmentOperation mTaskFragmentOperation;
            private boolean mToTop;
            private final int mType;
            private int[] mWindowingModes;

            Builder(int i) {
                this.mType = i;
            }

            Builder setContainer(IBinder iBinder) {
                this.mContainer = iBinder;
                return this;
            }

            Builder setContainers(IBinder[] iBinderArr) {
                this.mContainers = iBinderArr;
                return this;
            }

            Builder setReparentContainer(IBinder iBinder) {
                this.mReparent = iBinder;
                return this;
            }

            Builder setInsetsFrameProvider(InsetsFrameProvider insetsFrameProvider) {
                this.mInsetsFrameProvider = insetsFrameProvider;
                return this;
            }

            Builder setInsetsFrameOwner(IBinder iBinder) {
                this.mInsetsFrameOwner = iBinder;
                return this;
            }

            Builder setToTop(boolean z) {
                this.mToTop = z;
                return this;
            }

            Builder setReparentTopOnly(boolean z) {
                this.mReparentTopOnly = z;
                return this;
            }

            Builder setWindowingModes(int[] iArr) {
                this.mWindowingModes = iArr;
                return this;
            }

            Builder setActivityTypes(int[] iArr) {
                this.mActivityTypes = iArr;
                return this;
            }

            Builder setLaunchOptions(Bundle bundle) {
                this.mLaunchOptions = bundle;
                return this;
            }

            Builder setAppCompatOptions(Bundle bundle) {
                this.mAppCompatOptions = bundle;
                return this;
            }

            Builder setActivityIntent(Intent intent) {
                this.mActivityIntent = intent;
                return this;
            }

            Builder setPendingIntent(PendingIntent pendingIntent) {
                this.mPendingIntent = pendingIntent;
                return this;
            }

            Builder setAlwaysOnTop(boolean z) {
                this.mAlwaysOnTop = z;
                return this;
            }

            Builder setTaskFragmentOperation(TaskFragmentOperation taskFragmentOperation) {
                this.mTaskFragmentOperation = taskFragmentOperation;
                return this;
            }

            Builder setKeyguardState(KeyguardState keyguardState) {
                this.mKeyguardState = keyguardState;
                return this;
            }

            Builder setReparentLeafTaskIfRelaunch(boolean z) {
                this.mReparentLeafTaskIfRelaunch = z;
                return this;
            }

            Builder setShortcutInfo(ShortcutInfo shortcutInfo) {
                this.mShortcutInfo = shortcutInfo;
                return this;
            }

            Builder setBounds(Rect rect) {
                this.mBounds = rect;
                return this;
            }

            Builder setIncludingParents(boolean z) {
                this.mIncludingParents = z;
                return this;
            }

            Builder setIsTrimmableFromRecents(boolean z) {
                this.mIsTrimmableFromRecents = z;
                return this;
            }

            Builder setExcludeInsetsTypes(int i) {
                this.mExcludeInsetsTypes = i;
                return this;
            }

            Builder setLaunchAdjacentDisabled(boolean z) {
                this.mLaunchAdjacentDisabled = z;
                return this;
            }

            Builder setSafeRegionBounds(Rect rect) {
                this.mSafeRegionBounds = rect;
                return this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            HierarchyOp build() {
                HierarchyOp hierarchyOp = new HierarchyOp(this.mType);
                hierarchyOp.mContainer = this.mContainer;
                hierarchyOp.mContainers = this.mContainers;
                hierarchyOp.mReparent = this.mReparent;
                int[] iArr = this.mWindowingModes;
                hierarchyOp.mWindowingModes = iArr != null ? Arrays.copyOf(iArr, iArr.length) : null;
                int[] iArr2 = this.mActivityTypes;
                hierarchyOp.mActivityTypes = iArr2 != null ? Arrays.copyOf(iArr2, iArr2.length) : null;
                hierarchyOp.mInsetsFrameProvider = this.mInsetsFrameProvider;
                hierarchyOp.mInsetsFrameOwner = this.mInsetsFrameOwner;
                hierarchyOp.mToTop = this.mToTop;
                hierarchyOp.mReparentTopOnly = this.mReparentTopOnly;
                hierarchyOp.mLaunchOptions = this.mLaunchOptions;
                hierarchyOp.mAppCompatOptions = this.mAppCompatOptions;
                hierarchyOp.mActivityIntent = this.mActivityIntent;
                hierarchyOp.mPendingIntent = this.mPendingIntent;
                hierarchyOp.mAlwaysOnTop = this.mAlwaysOnTop;
                hierarchyOp.mTaskFragmentOperation = this.mTaskFragmentOperation;
                hierarchyOp.mKeyguardState = this.mKeyguardState;
                hierarchyOp.mShortcutInfo = this.mShortcutInfo;
                hierarchyOp.mBounds = this.mBounds;
                hierarchyOp.mIncludingParents = this.mIncludingParents;
                hierarchyOp.mReparentLeafTaskIfRelaunch = this.mReparentLeafTaskIfRelaunch;
                hierarchyOp.mIsTrimmableFromRecents = this.mIsTrimmableFromRecents;
                hierarchyOp.mExcludeInsetsTypes = this.mExcludeInsetsTypes;
                hierarchyOp.mLaunchAdjacentDisabled = this.mLaunchAdjacentDisabled;
                hierarchyOp.mSafeRegionBounds = this.mSafeRegionBounds;
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

        public void setShouldDelayPrimaryLastActivityRemoval(boolean z) {
            this.mDelayPrimaryLastActivityRemoval = z;
        }

        public void setShouldDelaySecondaryLastActivityRemoval(boolean z) {
            this.mDelaySecondaryLastActivityRemoval = z;
        }

        public boolean shouldDelayPrimaryLastActivityRemoval() {
            return this.mDelayPrimaryLastActivityRemoval;
        }

        public boolean shouldDelaySecondaryLastActivityRemoval() {
            return this.mDelaySecondaryLastActivityRemoval;
        }

        Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putBoolean(DELAY_PRIMARY_LAST_ACTIVITY_REMOVAL, this.mDelayPrimaryLastActivityRemoval);
            bundle.putBoolean(DELAY_SECONDARY_LAST_ACTIVITY_REMOVAL, this.mDelaySecondaryLastActivityRemoval);
            return bundle;
        }
    }

    public static final class ContainerChange implements Parcelable {
        public static final Parcelable.Creator<ContainerChange> CREATOR = new Parcelable.Creator<ContainerChange>() { // from class: android.window.WindowContainerTransaction.ContainerChange.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ContainerChange createFromParcel(Parcel parcel) {
                return new ContainerChange(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ContainerChange[] newArray(int i) {
                return new ContainerChange[i];
            }
        };
        Change mChange;
        IBinder mToken;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public ContainerChange() {
        }

        protected ContainerChange(Parcel parcel) {
            this.mToken = parcel.readStrongBinder();
            this.mChange = Change.CREATOR.createFromParcel(parcel);
        }

        public Change getChange() {
            return this.mChange;
        }

        public IBinder getToken() {
            return this.mToken;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeStrongBinder(this.mToken);
            this.mChange.writeToParcel(parcel, i);
        }
    }
}
