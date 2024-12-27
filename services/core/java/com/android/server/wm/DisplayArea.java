package com.android.server.wm;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import android.window.DisplayAreaInfo;
import android.window.IDisplayAreaOrganizer;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.policy.PhoneWindowManager;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class DisplayArea extends WindowContainer {
    boolean mDisplayAreaAppearedSent;
    final int mFeatureId;
    private final String mName;
    IDisplayAreaOrganizer mOrganizer;
    private final DisplayAreaOrganizerController mOrganizerController;
    protected boolean mSetIgnoreOrientationRequest;
    protected boolean mSetIgnoreOrientationRequestOverride;
    private final Configuration mTmpConfiguration;
    protected final Type mType;

    public class Dimmable extends DisplayArea {
        private final Dimmer mDimmer;

        public Dimmable(WindowManagerService windowManagerService, Type type, String str, int i) {
            super(windowManagerService, type, str, i);
            this.mDimmer = new Dimmer(this);
        }

        @Override // com.android.server.wm.WindowContainer
        public Dimmer getDimmer() {
            return this.mDimmer;
        }

        @Override // com.android.server.wm.WindowContainer
        public void prepareSurfaces() {
            Dimmer.DimState dimState;
            Dimmer.DimState dimState2 = this.mDimmer.mDimState;
            if (dimState2 != null) {
                dimState2.mLastDimmingWindow = null;
            }
            super.prepareSurfaces();
            Dimmer.DimState dimState3 = this.mDimmer.mDimState;
            Rect rect = dimState3 != null ? dimState3.mDimBounds : null;
            if (rect != null) {
                getBounds(rect);
                rect.offsetTo(0, 0);
            }
            if (!this.mTransitionController.isShellTransitionsEnabled()
                    && forAllTasks(new DisplayArea$$ExternalSyntheticLambda0(1))
                    && (dimState = this.mDimmer.mDimState) != null) {
                dimState.mLastDimmingWindow = null;
            }
            if (rect == null || !this.mDimmer.updateDims(getSyncTransaction())) {
                return;
            }
            scheduleAnimation();
        }
    }

    public class Tokens extends DisplayArea {
        public final DisplayArea$Tokens$$ExternalSyntheticLambda1 mGetOrientingWindow;
        public int mLastKeyguardForcedOrientation;
        public final Comparator mWindowComparator;

        public Tokens(WindowManagerService windowManagerService, Type type, String str, int i) {
            super(windowManagerService, type, str, i);
            this.mLastKeyguardForcedOrientation = -1;
            this.mWindowComparator =
                    Comparator.comparingInt(new DisplayArea$Tokens$$ExternalSyntheticLambda0());
            this.mGetOrientingWindow =
                    new Predicate() { // from class:
                        // com.android.server.wm.DisplayArea$Tokens$$ExternalSyntheticLambda1
                        /* JADX WARN: Code restructure failed: missing block: B:22:0x0060, code lost:

                           if (r5 == false) goto L26;
                        */
                        @Override // java.util.function.Predicate
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final boolean test(java.lang.Object r6) {
                            /*
                                r5 = this;
                                com.android.server.wm.DisplayArea$Tokens r5 = com.android.server.wm.DisplayArea.Tokens.this
                                com.android.server.wm.WindowState r6 = (com.android.server.wm.WindowState) r6
                                r5.getClass()
                                boolean r0 = r6.isVisible()
                                r1 = 0
                                if (r0 == 0) goto L71
                                boolean r0 = r6.mLegacyPolicyVisibilityAfterAnim
                                if (r0 != 0) goto L13
                                goto L71
                            L13:
                                com.android.server.wm.WindowManagerService r0 = r5.mWmService
                                com.android.server.policy.WindowManagerPolicy r0 = r0.mPolicy
                                android.view.WindowManager$LayoutParams r2 = r6.mAttrs
                                com.android.server.policy.PhoneWindowManager r0 = (com.android.server.policy.PhoneWindowManager) r0
                                boolean r2 = r0.isKeyguardHostWindow(r2)
                                r3 = 1
                                if (r2 == 0) goto L64
                                com.android.server.wm.DisplayContent r2 = r5.mDisplayContent
                                boolean r2 = r2.isKeyguardLocked()
                                if (r2 != 0) goto L39
                                com.android.server.wm.DisplayContent r2 = r5.mDisplayContent
                                com.android.server.wm.DisplayPolicy r2 = r2.mDisplayPolicy
                                boolean r2 = r2.mAwake
                                if (r2 == 0) goto L39
                                boolean r2 = r0.okToAnimate(r3)
                                if (r2 == 0) goto L39
                                goto L71
                            L39:
                                com.android.server.wm.DisplayContent r2 = r5.mDisplayContent
                                com.android.server.wm.AppTransition r2 = r2.mAppTransition
                                java.util.ArrayList r2 = r2.mNextAppTransitionRequests
                                r4 = 9
                                java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
                                boolean r2 = r2.contains(r4)
                                if (r2 == 0) goto L59
                                com.android.server.wm.DisplayContent r5 = r5.mDisplayContent
                                com.android.server.wm.UnknownAppVisibilityController r5 = r5.mUnknownAppVisibilityController
                                android.util.ArrayMap r5 = r5.mUnknownApps
                                boolean r5 = r5.isEmpty()
                                if (r5 == 0) goto L59
                                r5 = r3
                                goto L5a
                            L59:
                                r5 = r1
                            L5a:
                                boolean r0 = r0.isKeyguardShowingAndNotOccluded()
                                if (r0 != 0) goto L62
                                if (r5 == 0) goto L64
                            L62:
                                r1 = r3
                                goto L71
                            L64:
                                android.view.WindowManager$LayoutParams r5 = r6.mAttrs
                                int r5 = r5.screenOrientation
                                r6 = -1
                                if (r5 == r6) goto L71
                                r6 = 3
                                if (r5 == r6) goto L71
                                r6 = -2
                                if (r5 != r6) goto L62
                            L71:
                                return r1
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.server.wm.DisplayArea$Tokens$$ExternalSyntheticLambda1.test(java.lang.Object):boolean");
                        }
                    };
        }

        @Override // com.android.server.wm.DisplayArea
        public final Tokens asTokens() {
            return this;
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
        public int getOrientation(int i) {
            this.mLastOrientationSource = null;
            WindowState window = getWindow(this.mGetOrientingWindow);
            if (window == null) {
                return i;
            }
            int i2 = window.mAttrs.screenOrientation;
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_ORIENTATION,
                        2230151187668089583L,
                        20,
                        null,
                        String.valueOf(window),
                        Long.valueOf(i2),
                        Long.valueOf(this.mDisplayContent.mDisplayId));
            }
            if (((PhoneWindowManager) this.mWmService.mPolicy)
                    .isKeyguardHostWindow(window.mAttrs)) {
                if (i2 == -2 || i2 == -1) {
                    i2 = this.mLastKeyguardForcedOrientation;
                } else {
                    this.mLastKeyguardForcedOrientation = i2;
                }
            }
            this.mLastOrientationSource = window;
            return i2;
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer,
                  // com.android.server.wm.ConfigurationContainer
        public final void resolveOverrideConfiguration(Configuration configuration) {
            super.resolveOverrideConfiguration(configuration);
            if (configuration.windowConfiguration.getWindowingMode() == 5) {
                getResolvedOverrideConfiguration().windowConfiguration.setWindowingMode(1);
            }
        }
    }

    final class Type {
        public static final /* synthetic */ Type[] $VALUES;
        public static final Type ABOVE_TASKS;
        public static final Type ANY;
        public static final Type BELOW_TASKS;

        static {
            Type type = new Type("ABOVE_TASKS", 0);
            ABOVE_TASKS = type;
            Type type2 = new Type("BELOW_TASKS", 1);
            BELOW_TASKS = type2;
            Type type3 = new Type("ANY", 2);
            ANY = type3;
            $VALUES = new Type[] {type, type2, type3};
        }

        public static Type typeOf(WindowContainer windowContainer) {
            if (windowContainer.asDisplayArea() != null) {
                return ((DisplayArea) windowContainer).mType;
            }
            if ((windowContainer instanceof WindowToken)
                    && !(windowContainer instanceof ActivityRecord)) {
                return ((WindowToken) windowContainer).getWindowLayerFromType() < 2
                        ? BELOW_TASKS
                        : ABOVE_TASKS;
            }
            if (windowContainer instanceof Task) {
                return ANY;
            }
            throw new IllegalArgumentException("Unknown container: " + windowContainer);
        }

        public static Type valueOf(String str) {
            return (Type) Enum.valueOf(Type.class, str);
        }

        public static Type[] values() {
            return (Type[]) $VALUES.clone();
        }
    }

    public DisplayArea(WindowManagerService windowManagerService, Type type, String str, int i) {
        super(windowManagerService);
        this.mTmpConfiguration = new Configuration();
        setOverrideOrientation(-2);
        this.mType = type;
        this.mName = str;
        this.mFeatureId = i;
        this.mRemoteToken = new WindowContainer.RemoteToken(this);
        this.mOrganizerController =
                windowManagerService
                        .mAtmService
                        .mWindowOrganizerController
                        .mDisplayAreaOrganizerController;
    }

    @Override // com.android.server.wm.WindowContainer
    public final DisplayArea asDisplayArea() {
        return this;
    }

    public Tokens asTokens() {
        return null;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean canCreateRemoteAnimationTarget() {
        if (CoreRune.FW_REMOTE_WALLPAPER_ANIM && this.mFeatureId == 10002) {
            return true;
        }
        return this instanceof ActivityRecord;
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public void commitPendingTransaction() {
        scheduleAnimation();
    }

    @Override // com.android.server.wm.WindowContainer
    public /* bridge */ /* synthetic */ int compareTo(WindowContainer windowContainer) {
        return super.compareTo(windowContainer);
    }

    @Override // com.android.server.wm.WindowContainer
    public void dump(PrintWriter printWriter, String str, boolean z) {
        super.dump(printWriter, str, z);
        if (this.mSetIgnoreOrientationRequest) {
            printWriter.println(str + "mSetIgnoreOrientationRequest=true");
        }
        if (hasRequestedOverrideConfiguration()) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "overrideConfig=");
            m.append(getRequestedOverrideConfiguration());
            printWriter.println(m.toString());
        }
    }

    public void dumpChildDisplayArea(PrintWriter printWriter, String str, boolean z) {
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "  ");
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayArea asDisplayArea = getChildAt(childCount).asDisplayArea();
            if (asDisplayArea != null) {
                StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "* ");
                m.append(asDisplayArea.getName());
                printWriter.print(m.toString());
                if (asDisplayArea.isOrganized()) {
                    printWriter.print(" (organized)");
                }
                printWriter.println();
                if (!asDisplayArea.isTaskDisplayArea()) {
                    asDisplayArea.dump(printWriter, m$1, z);
                    asDisplayArea.dumpChildDisplayArea(printWriter, m$1, z);
                }
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        if (i != 2 || isVisible()) {
            long start = protoOutputStream.start(j);
            super.dumpDebug(protoOutputStream, 1146756268033L, i);
            protoOutputStream.write(1138166333442L, this.mName);
            protoOutputStream.write(1133871366148L, isTaskDisplayArea());
            protoOutputStream.write(1133871366149L, asRootDisplayArea() != null);
            protoOutputStream.write(1120986464262L, this.mFeatureId);
            protoOutputStream.write(1133871366151L, isOrganized());
            protoOutputStream.write(1133871366152L, getIgnoreOrientationRequest());
            protoOutputStream.end(start);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean fillsParent() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public void forAllActivities(Consumer consumer, boolean z) {
        if (this.mType == Type.ABOVE_TASKS) {
            return;
        }
        super.forAllActivities(consumer, z);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean forAllActivities(Predicate predicate, boolean z) {
        if (this.mType == Type.ABOVE_TASKS) {
            return false;
        }
        return super.forAllActivities(predicate, z);
    }

    @Override // com.android.server.wm.WindowContainer
    public void forAllDisplayAreas(Consumer consumer) {
        super.forAllDisplayAreas(consumer);
        consumer.accept(this);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean forAllLeafTaskFragments(Predicate predicate) {
        if (this.mType == Type.ABOVE_TASKS) {
            return false;
        }
        return super.forAllLeafTaskFragments(predicate);
    }

    @Override // com.android.server.wm.WindowContainer
    public void forAllLeafTasks(Consumer consumer, boolean z) {
        if (this.mType == Type.ABOVE_TASKS) {
            return;
        }
        super.forAllLeafTasks(consumer, z);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean forAllLeafTasks(Predicate predicate) {
        if (this.mType == Type.ABOVE_TASKS) {
            return false;
        }
        return super.forAllLeafTasks(predicate);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean forAllRootTasks(Predicate predicate, boolean z) {
        if (this.mType == Type.ABOVE_TASKS) {
            return false;
        }
        return super.forAllRootTasks(predicate, z);
    }

    @Override // com.android.server.wm.WindowContainer
    public void forAllTaskDisplayAreas(Consumer consumer, boolean z) {
        if (this.mType != Type.ANY) {
            return;
        }
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(i);
            if (windowContainer.asDisplayArea() != null) {
                windowContainer.asDisplayArea().forAllTaskDisplayAreas(consumer, z);
            }
            i += z ? -1 : 1;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean forAllTaskDisplayAreas(Predicate predicate, boolean z) {
        if (this.mType != Type.ANY) {
            return false;
        }
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(i);
            int i2 = 1;
            if (windowContainer.asDisplayArea() != null
                    && windowContainer.asDisplayArea().forAllTaskDisplayAreas(predicate, z)) {
                return true;
            }
            if (z) {
                i2 = -1;
            }
            i += i2;
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean forAllTasks(Predicate predicate) {
        if (this.mType == Type.ABOVE_TASKS) {
            return false;
        }
        return super.forAllTasks(predicate);
    }

    @Override // com.android.server.wm.WindowContainer
    public ActivityRecord getActivity(
            Predicate predicate, boolean z, ActivityRecord activityRecord) {
        if (this.mType == Type.ABOVE_TASKS) {
            return null;
        }
        return super.getActivity(predicate, z, activityRecord);
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ SurfaceControl getAnimationLeash() {
        return super.getAnimationLeash();
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public SurfaceControl getAnimationLeashParent() {
        return getParentSurfaceControl();
    }

    @Override // com.android.server.wm.WindowContainer
    public DisplayArea getDisplayArea() {
        return this;
    }

    public DisplayAreaInfo getDisplayAreaInfo() {
        DisplayAreaInfo displayAreaInfo =
                new DisplayAreaInfo(
                        this.mRemoteToken.toWindowContainerToken(),
                        getDisplayContent().mDisplayId,
                        this.mFeatureId);
        RootDisplayArea rootDisplayArea = getRootDisplayArea();
        if (rootDisplayArea == null) {
            rootDisplayArea = getDisplayContent();
        }
        displayAreaInfo.rootDisplayAreaId = rootDisplayArea.mFeatureId;
        displayAreaInfo.configuration.setTo(getConfiguration());
        return displayAreaInfo;
    }

    @Override // com.android.server.wm.WindowContainer
    public /* bridge */ /* synthetic */ SurfaceControl getFreezeSnapshotTarget() {
        return super.getFreezeSnapshotTarget();
    }

    public boolean getIgnoreOrientationRequest() {
        if (this.mSetIgnoreOrientationRequest) {
            WindowManagerService windowManagerService = this.mWmService;
            if (!windowManagerService.mIsIgnoreOrientationRequestDisabled
                    && windowManagerService.mAppCompatConfiguration.mDeviceConfig.getFlagValue(
                            "allow_ignore_orientation_request")) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public /* bridge */ /* synthetic */ SparseArray getInsetsSourceProviders() {
        return super.getInsetsSourceProviders();
    }

    @Override // com.android.server.wm.WindowContainer
    public Object getItemFromDisplayAreas(Function function) {
        Object itemFromDisplayAreas = super.getItemFromDisplayAreas(function);
        return itemFromDisplayAreas != null ? itemFromDisplayAreas : function.apply(this);
    }

    @Override // com.android.server.wm.WindowContainer
    public Object getItemFromTaskDisplayAreas(Function function, boolean z) {
        Object itemFromTaskDisplayAreas;
        if (this.mType != Type.ANY) {
            return null;
        }
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(i);
            if (windowContainer.asDisplayArea() != null
                    && (itemFromTaskDisplayAreas =
                                    windowContainer
                                            .asDisplayArea()
                                            .getItemFromTaskDisplayAreas(function, z))
                            != null) {
                return itemFromTaskDisplayAreas;
            }
            i += z ? -1 : 1;
        }
        return null;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public String getName() {
        return this.mName;
    }

    @Override // com.android.server.wm.WindowContainer
    public int getOrientation(int i) {
        int orientation = super.getOrientation(i);
        if (!shouldIgnoreOrientationRequest(orientation)) {
            return orientation;
        }
        this.mLastOrientationSource = null;
        return -2;
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ SurfaceControl getParentSurfaceControl() {
        return super.getParentSurfaceControl();
    }

    @Override // com.android.server.wm.WindowContainer
    public /* bridge */ /* synthetic */ SurfaceControl.Transaction getPendingTransaction() {
        return super.getPendingTransaction();
    }

    @Override // com.android.server.wm.WindowContainer
    public long getProtoFieldId() {
        return 1146756268036L;
    }

    @Override // com.android.server.wm.WindowContainer
    public Task getRootTask(Predicate predicate, boolean z) {
        if (this.mType == Type.ABOVE_TASKS) {
            return null;
        }
        return super.getRootTask(predicate, z);
    }

    public void getStableRect(Rect rect) {
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent == null) {
            getBounds(rect);
        } else {
            displayContent.getStableRect(rect);
            rect.intersect(getBounds());
        }
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public int getSurfaceHeight() {
        return this.mSurfaceControl.getHeight();
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public int getSurfaceWidth() {
        return this.mSurfaceControl.getWidth();
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ SurfaceControl.Transaction getSyncTransaction() {
        return super.getSyncTransaction();
    }

    @Override // com.android.server.wm.WindowContainer
    public Task getTask(Predicate predicate, boolean z) {
        if (this.mType == Type.ABOVE_TASKS) {
            return null;
        }
        return super.getTask(predicate, z);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean handlesOrientationChangeFromDescendant(int i) {
        return !shouldIgnoreOrientationRequest(i)
                && super.handlesOrientationChangeFromDescendant(i);
    }

    @Override // com.android.server.wm.WindowContainer
    public /* bridge */ /* synthetic */ boolean hasInsetsSourceProvider() {
        return super.hasInsetsSourceProvider();
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isOrganized() {
        return this.mOrganizer != null;
    }

    public boolean isTaskDisplayArea() {
        return false;
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ SurfaceControl.Builder makeAnimationLeash() {
        return super.makeAnimationLeash();
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean needRemoteWallpaperAnim() {
        if (CoreRune.FW_REMOTE_WALLPAPER_ANIM && this.mFeatureId == 10002) {
            return !this.mTransitionController.isCollecting()
                    || (this.mTransitionController.mCollectingTransition.mFlags & 276736) == 0;
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean needsZBoost() {
        return false;
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ void onAnimationLeashCreated(
            SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        super.onAnimationLeashCreated(transaction, surfaceControl);
    }

    @Override // com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ void onAnimationLeashLost(
            SurfaceControl.Transaction transaction) {
        super.onAnimationLeashLost(transaction);
    }

    @Override // com.android.server.wm.WindowContainer
    public void onChildPositionChanged(WindowContainer windowContainer) {
        Type type = this.mType;
        Type typeOf = Type.typeOf(windowContainer);
        int ordinal = type.ordinal();
        Type type2 = Type.ABOVE_TASKS;
        Type type3 = Type.BELOW_TASKS;
        if (ordinal == 0) {
            Preconditions.checkState(typeOf == type2, "ABOVE_TASKS can only contain ABOVE_TASKS");
        } else if (ordinal == 1) {
            Preconditions.checkState(typeOf == type3, "BELOW_TASKS can only contain BELOW_TASKS");
        }
        if (windowContainer instanceof Task) {
            return;
        }
        for (int i = 1; i < getChildCount(); i++) {
            WindowContainer childAt = getChildAt(i - 1);
            WindowContainer childAt2 = getChildAt(i);
            if (windowContainer == childAt || windowContainer == childAt2) {
                Type typeOf2 = Type.typeOf(childAt);
                Type typeOf3 = Type.typeOf(childAt2);
                Preconditions.checkState(
                        typeOf2 == type3 || typeOf3 != type3,
                        typeOf2 + " must be above BELOW_TASKS");
                Preconditions.checkState(
                        typeOf2 != type2 || typeOf3 == type2,
                        typeOf3 + " must be below ABOVE_TASKS");
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(Configuration configuration) {
        this.mTransitionController.collectForDisplayAreaChange(this);
        this.mTmpConfiguration.setTo(getConfiguration());
        super.onConfigurationChanged(configuration);
        if (this.mOrganizer == null || getConfiguration().diff(this.mTmpConfiguration) == 0) {
            return;
        }
        sendDisplayAreaInfoChanged();
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean onDescendantOrientationChanged(WindowContainer windowContainer) {
        return !shouldIgnoreOrientationRequest(
                        windowContainer != null ? windowContainer.getOverrideOrientation() : -2)
                && super.onDescendantOrientationChanged(windowContainer);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public /* bridge */ /* synthetic */ void onRequestedOverrideConfigurationChanged(
            Configuration configuration) {
        super.onRequestedOverrideConfigurationChanged(configuration);
    }

    @Override // com.android.server.wm.WindowContainer
    public /* bridge */ /* synthetic */ void onUnfrozen() {
        super.onUnfrozen();
    }

    @Override // com.android.server.wm.WindowContainer
    public void positionChildAt(int i, WindowContainer windowContainer, boolean z) {
        if (windowContainer.asDisplayArea() == null) {
            super.positionChildAt(i, windowContainer, z);
            return;
        }
        DisplayArea asDisplayArea = windowContainer.asDisplayArea();
        if (asDisplayArea.getParent() != this) {
            throw new IllegalArgumentException(
                    "positionChildAt: container="
                            + asDisplayArea.getName()
                            + " is not a child of container="
                            + getName()
                            + " current parent="
                            + asDisplayArea.getParent());
        }
        Type typeOf = Type.typeOf(asDisplayArea);
        int size = this.mChildren.size() - 1;
        while (true) {
            if (size <= 0) {
                size = 0;
                break;
            } else if (Type.typeOf(getChildAt(size)) == typeOf) {
                break;
            } else {
                size--;
            }
        }
        Type typeOf2 = Type.typeOf(asDisplayArea);
        int i2 = 0;
        while (true) {
            if (i2 >= this.mChildren.size()) {
                i2 = this.mChildren.size() - 1;
                break;
            } else if (Type.typeOf(getChildAt(i2)) == typeOf2) {
                break;
            } else {
                i2++;
            }
        }
        int i3 = 0;
        for (int i4 = i2; i4 <= size; i4++) {
            if (((WindowContainer) this.mChildren.get(i4)).isAlwaysOnTop()) {
                i3++;
            }
        }
        if (asDisplayArea.isAlwaysOnTop()) {
            i2 = (size - i3) + 1;
        } else {
            size -= i3;
        }
        super.positionChildAt(Math.max(Math.min(i, size), i2), windowContainer, false);
        WindowContainer parent = getParent();
        if (!z || parent == null) {
            return;
        }
        if (i == Integer.MAX_VALUE || i == Integer.MIN_VALUE) {
            parent.positionChildAt(i, this, true);
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public boolean providesMaxBounds() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public Object reduceOnAllTaskDisplayAreas(BiFunction biFunction, Object obj, boolean z) {
        if (this.mType != Type.ANY) {
            return obj;
        }
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(i);
            if (windowContainer.asDisplayArea() != null) {
                obj =
                        windowContainer
                                .asDisplayArea()
                                .reduceOnAllTaskDisplayAreas(biFunction, obj, z);
            }
            i += z ? -1 : 1;
        }
        return obj;
    }

    @Override // com.android.server.wm.WindowContainer
    public void removeImmediately() {
        setOrganizer(null);
        super.removeImmediately();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void resolveOverrideConfiguration(Configuration configuration) {
        super.resolveOverrideConfiguration(configuration);
        Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
        Rect bounds = resolvedOverrideConfiguration.windowConfiguration.getBounds();
        Rect appBounds = resolvedOverrideConfiguration.windowConfiguration.getAppBounds();
        Rect appBounds2 = configuration.windowConfiguration.getAppBounds();
        if (bounds.isEmpty()) {
            return;
        }
        if ((appBounds != null && !appBounds.isEmpty())
                || appBounds2 == null
                || appBounds2.isEmpty()) {
            return;
        }
        Rect rect = new Rect(bounds);
        rect.intersect(appBounds2);
        resolvedOverrideConfiguration.windowConfiguration.setAppBounds(rect);
    }

    public void sendDisplayAreaAppeared() {
        IDisplayAreaOrganizer iDisplayAreaOrganizer = this.mOrganizer;
        if (iDisplayAreaOrganizer == null || this.mDisplayAreaAppearedSent) {
            return;
        }
        this.mOrganizerController.getClass();
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                    -4514772405648277945L,
                    0,
                    null,
                    String.valueOf(getName()));
        }
        try {
            iDisplayAreaOrganizer.onDisplayAreaAppeared(
                    getDisplayAreaInfo(),
                    new SurfaceControl(
                            getSurfaceControl(),
                            "DisplayAreaOrganizerController.onDisplayAreaAppeared"));
        } catch (RemoteException unused) {
        }
        this.mDisplayAreaAppearedSent = true;
    }

    public void sendDisplayAreaInfoChanged() {
        IDisplayAreaOrganizer iDisplayAreaOrganizer = this.mOrganizer;
        if (iDisplayAreaOrganizer == null || !this.mDisplayAreaAppearedSent) {
            return;
        }
        this.mOrganizerController.getClass();
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                    -1007032390526684388L,
                    0,
                    null,
                    String.valueOf(getName()));
        }
        try {
            iDisplayAreaOrganizer.onDisplayAreaInfoChanged(getDisplayAreaInfo());
        } catch (RemoteException unused) {
        }
    }

    public void sendDisplayAreaVanished(IDisplayAreaOrganizer iDisplayAreaOrganizer) {
        if (iDisplayAreaOrganizer == null || !this.mDisplayAreaAppearedSent) {
            return;
        }
        migrateToNewSurfaceControl(getSyncTransaction());
        this.mOrganizerController.getClass();
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                    995846188225477231L,
                    0,
                    null,
                    String.valueOf(getName()));
        }
        if (iDisplayAreaOrganizer.asBinder().isBinderAlive()) {
            try {
                iDisplayAreaOrganizer.onDisplayAreaVanished(getDisplayAreaInfo());
            } catch (RemoteException unused) {
            }
        } else {
            Slog.d(
                    "DisplayAreaOrganizerController",
                    "Organizer died before sending onDisplayAreaVanished");
        }
        this.mDisplayAreaAppearedSent = false;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void setAlwaysOnTop(boolean z) {
        if (isAlwaysOnTop() == z) {
            return;
        }
        super.setAlwaysOnTop(z);
        if (getParent().asDisplayArea() != null) {
            getParent().asDisplayArea().positionChildAt(Integer.MAX_VALUE, this, false);
        }
    }

    public boolean setIgnoreOrientationRequest(boolean z) {
        if (this.mSetIgnoreOrientationRequest == z) {
            return false;
        }
        this.mSetIgnoreOrientationRequest = z;
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent == null) {
            return false;
        }
        ActivityRecord activityRecord = displayContent.mFocusedApp;
        if (activityRecord != null) {
            displayContent.mOrientationRequestingTaskDisplayArea = activityRecord.getDisplayArea();
        }
        if (!z) {
            return this.mDisplayContent.updateOrientation(false);
        }
        DisplayContent displayContent2 = this.mDisplayContent;
        int i = displayContent2.mDisplayRotation.mLastOrientation;
        WindowContainer lastOrientationSource = displayContent2.getLastOrientationSource();
        if (i == -2 || i == -1) {
            return false;
        }
        if (lastOrientationSource == null || lastOrientationSource.isDescendantOf(this)) {
            return this.mDisplayContent.updateOrientation(false);
        }
        return false;
    }

    public void setOrganizer(IDisplayAreaOrganizer iDisplayAreaOrganizer) {
        setOrganizer(iDisplayAreaOrganizer, false);
    }

    public void setOrganizer(IDisplayAreaOrganizer iDisplayAreaOrganizer, boolean z) {
        if (this.mOrganizer == iDisplayAreaOrganizer) {
            return;
        }
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent == null || !displayContent.mDisplay.isTrusted()) {
            throw new IllegalStateException(
                    "Don't organize or trigger events for unavailable or untrusted display.");
        }
        IDisplayAreaOrganizer iDisplayAreaOrganizer2 = this.mOrganizer;
        this.mOrganizer = iDisplayAreaOrganizer;
        sendDisplayAreaVanished(iDisplayAreaOrganizer2);
        if (!z) {
            sendDisplayAreaAppeared();
        } else if (iDisplayAreaOrganizer != null) {
            this.mDisplayAreaAppearedSent = true;
        }
    }

    public boolean shouldIgnoreOrientationRequest(int i) {
        if ((CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY && this.mSetIgnoreOrientationRequestOverride)
                || i == 14
                || i == 5
                || !getIgnoreOrientationRequest()) {
            return false;
        }
        DisplayContent displayContent = this.mDisplayContent;
        return displayContent == null
                || displayContent.getActivity(new DisplayArea$$ExternalSyntheticLambda0(0)) == null;
    }

    public String toString() {
        return this.mName + "@" + System.identityHashCode(this);
    }
}
