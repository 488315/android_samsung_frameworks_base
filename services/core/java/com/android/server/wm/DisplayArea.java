package com.android.server.wm;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import android.window.DisplayAreaInfo;
import android.window.IDisplayAreaOrganizer;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.Preconditions;
import com.android.server.wm.DisplayArea;
import com.android.server.wm.WindowContainer;
import com.android.server.policy.WindowManagerPolicy;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/* loaded from: classes3.dex */
public class DisplayArea extends WindowContainer {
    final int mFeatureId;
    private final String mName;
    IDisplayAreaOrganizer mOrganizer;
    private final DisplayAreaOrganizerController mOrganizerController;
    protected boolean mSetIgnoreOrientationRequest;
    private final Configuration mTmpConfiguration;
    protected final Type mType;

    @Override // com.android.server.wm.WindowContainer
    public final DisplayArea asDisplayArea() {
        return this;
    }

    public Tokens asTokens() {
        return null;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean fillsParent() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public DisplayArea getDisplayArea() {
        return this;
    }

    @Override // com.android.server.wm.WindowContainer
    public long getProtoFieldId() {
        return 1146756268036L;
    }

    public boolean isTaskDisplayArea() {
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean needsZBoost() {
        return false;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public boolean providesMaxBounds() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ void commitPendingTransaction() {
        super.commitPendingTransaction();
    }

    @Override // com.android.server.wm.WindowContainer
    public /* bridge */ /* synthetic */ int compareTo(WindowContainer windowContainer) {
        return super.compareTo(windowContainer);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ SurfaceControl getAnimationLeash() {
        return super.getAnimationLeash();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ SurfaceControl getAnimationLeashParent() {
        return super.getAnimationLeashParent();
    }

    @Override // com.android.server.wm.WindowContainer
    public /* bridge */ /* synthetic */ DisplayContent getDisplayContent() {
        return super.getDisplayContent();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceFreezer.Freezable
    public /* bridge */ /* synthetic */ SurfaceControl getFreezeSnapshotTarget() {
        return super.getFreezeSnapshotTarget();
    }

    @Override // com.android.server.wm.WindowContainer
    public /* bridge */ /* synthetic */ SparseArray getInsetsSourceProviders() {
        return super.getInsetsSourceProviders();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ SurfaceControl getParentSurfaceControl() {
        return super.getParentSurfaceControl();
    }

    @Override // com.android.server.wm.WindowContainer
    public /* bridge */ /* synthetic */ SurfaceControl.Transaction getPendingTransaction() {
        return super.getPendingTransaction();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ SurfaceControl getSurfaceControl() {
        return super.getSurfaceControl();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ int getSurfaceHeight() {
        return super.getSurfaceHeight();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ int getSurfaceWidth() {
        return super.getSurfaceWidth();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ SurfaceControl.Transaction getSyncTransaction() {
        return super.getSyncTransaction();
    }

    @Override // com.android.server.wm.WindowContainer
    public /* bridge */ /* synthetic */ boolean hasInsetsSourceProvider() {
        return super.hasInsetsSourceProvider();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ SurfaceControl.Builder makeAnimationLeash() {
        return super.makeAnimationLeash();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ void onAnimationLeashCreated(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        super.onAnimationLeashCreated(transaction, surfaceControl);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public /* bridge */ /* synthetic */ void onAnimationLeashLost(SurfaceControl.Transaction transaction) {
        super.onAnimationLeashLost(transaction);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public /* bridge */ /* synthetic */ void onRequestedOverrideConfigurationChanged(Configuration configuration) {
        super.onRequestedOverrideConfigurationChanged(configuration);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceFreezer.Freezable
    public /* bridge */ /* synthetic */ void onUnfrozen() {
        super.onUnfrozen();
    }

    public DisplayArea(WindowManagerService windowManagerService, Type type, String str, int i) {
        super(windowManagerService);
        this.mTmpConfiguration = new Configuration();
        setOverrideOrientation(-2);
        this.mType = type;
        this.mName = str;
        this.mFeatureId = i;
        this.mRemoteToken = new WindowContainer.RemoteToken(this);
        this.mOrganizerController = windowManagerService.mAtmService.mWindowOrganizerController.mDisplayAreaOrganizerController;
    }

    @Override // com.android.server.wm.WindowContainer
    public void onChildPositionChanged(WindowContainer windowContainer) {
        super.onChildPositionChanged(windowContainer);
        Type.checkChild(this.mType, Type.typeOf(windowContainer));
        if (windowContainer instanceof Task) {
            return;
        }
        for (int i = 1; i < getChildCount(); i++) {
            WindowContainer childAt = getChildAt(i - 1);
            WindowContainer childAt2 = getChildAt(i);
            if (windowContainer == childAt || windowContainer == childAt2) {
                Type.checkSiblings(Type.typeOf(childAt), Type.typeOf(childAt2));
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void positionChildAt(int i, WindowContainer windowContainer, boolean z) {
        if (windowContainer.asDisplayArea() == null) {
            super.positionChildAt(i, windowContainer, z);
            return;
        }
        super.positionChildAt(findPositionForChildDisplayArea(i, windowContainer.asDisplayArea()), windowContainer, false);
        WindowContainer parent = getParent();
        if (!z || parent == null) {
            return;
        }
        if (i == Integer.MAX_VALUE || i == Integer.MIN_VALUE) {
            parent.positionChildAt(i, this, true);
        }
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

    @Override // com.android.server.wm.WindowContainer
    public boolean handlesOrientationChangeFromDescendant(int i) {
        return !shouldIgnoreOrientationRequest(i) && super.handlesOrientationChangeFromDescendant(i);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean onDescendantOrientationChanged(WindowContainer windowContainer) {
        return !shouldIgnoreOrientationRequest(windowContainer != null ? windowContainer.getOverrideOrientation() : -2) && super.onDescendantOrientationChanged(windowContainer);
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
            displayContent.onLastFocusedTaskDisplayAreaChanged(activityRecord.getDisplayArea());
        }
        if (!z) {
            return this.mDisplayContent.updateOrientation();
        }
        int lastOrientation = this.mDisplayContent.getLastOrientation();
        WindowContainer lastOrientationSource = this.mDisplayContent.getLastOrientationSource();
        if (lastOrientation == -2 || lastOrientation == -1) {
            return false;
        }
        if (lastOrientationSource == null || lastOrientationSource.isDescendantOf(this)) {
            return this.mDisplayContent.updateOrientation();
        }
        return false;
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

    public boolean shouldIgnoreOrientationRequest(int i) {
        return (i == 14 || i == 5 || !getIgnoreOrientationRequest() || shouldRespectOrientationRequestDueToPerAppOverride()) ? false : true;
    }

    public final boolean shouldRespectOrientationRequestDueToPerAppOverride() {
        ActivityRecord activityRecord;
        DisplayContent displayContent = this.mDisplayContent;
        return (displayContent == null || (activityRecord = displayContent.topRunningActivity(true)) == null || activityRecord.getTaskFragment() == null || activityRecord.getTaskFragment().getWindowingMode() != 1 || !activityRecord.mLetterboxUiController.isOverrideRespectRequestedOrientationEnabled()) ? false : true;
    }

    public boolean getIgnoreOrientationRequest() {
        return this.mSetIgnoreOrientationRequest && !this.mWmService.isIgnoreOrientationRequestDisabled();
    }

    public final int findPositionForChildDisplayArea(int i, DisplayArea displayArea) {
        if (displayArea.getParent() != this) {
            throw new IllegalArgumentException("positionChildAt: container=" + displayArea.getName() + " is not a child of container=" + getName() + " current parent=" + displayArea.getParent());
        }
        int findMaxPositionForChildDisplayArea = findMaxPositionForChildDisplayArea(displayArea);
        int findMinPositionForChildDisplayArea = findMinPositionForChildDisplayArea(displayArea);
        int i2 = 0;
        for (int i3 = findMinPositionForChildDisplayArea; i3 <= findMaxPositionForChildDisplayArea; i3++) {
            if (((WindowContainer) this.mChildren.get(i3)).isAlwaysOnTop()) {
                i2++;
            }
        }
        if (displayArea.isAlwaysOnTop()) {
            findMinPositionForChildDisplayArea = (findMaxPositionForChildDisplayArea - i2) + 1;
        } else {
            findMaxPositionForChildDisplayArea -= i2;
        }
        return Math.max(Math.min(i, findMaxPositionForChildDisplayArea), findMinPositionForChildDisplayArea);
    }

    public final int findMaxPositionForChildDisplayArea(DisplayArea displayArea) {
        Type typeOf = Type.typeOf(displayArea);
        for (int size = this.mChildren.size() - 1; size > 0; size--) {
            if (Type.typeOf(getChildAt(size)) == typeOf) {
                return size;
            }
        }
        return 0;
    }

    public final int findMinPositionForChildDisplayArea(DisplayArea displayArea) {
        Type typeOf = Type.typeOf(displayArea);
        for (int i = 0; i < this.mChildren.size(); i++) {
            if (Type.typeOf(getChildAt(i)) == typeOf) {
                return i;
            }
        }
        return this.mChildren.size() - 1;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public String getName() {
        return this.mName;
    }

    public String toString() {
        return this.mName + "@" + System.identityHashCode(this);
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
    public void dump(PrintWriter printWriter, String str, boolean z) {
        super.dump(printWriter, str, z);
        if (this.mSetIgnoreOrientationRequest) {
            printWriter.println(str + "mSetIgnoreOrientationRequest=true");
        }
        if (hasRequestedOverrideConfiguration()) {
            printWriter.println(str + "overrideConfig=" + getRequestedOverrideConfiguration());
        }
    }

    public void dumpChildDisplayArea(PrintWriter printWriter, String str, boolean z) {
        String str2 = str + "  ";
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayArea asDisplayArea = getChildAt(childCount).asDisplayArea();
            if (asDisplayArea != null) {
                printWriter.print(str + "* " + asDisplayArea.getName());
                if (asDisplayArea.isOrganized()) {
                    printWriter.print(" (organized)");
                }
                printWriter.println();
                if (!asDisplayArea.isTaskDisplayArea()) {
                    asDisplayArea.dump(printWriter, str2, z);
                    asDisplayArea.dumpChildDisplayArea(printWriter, str2, z);
                }
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public ActivityRecord getActivity(Predicate predicate, boolean z, ActivityRecord activityRecord) {
        Type type = this.mType;
        if (type == Type.ABOVE_TASKS || type == Type.BELOW_TASKS) {
            return null;
        }
        return super.getActivity(predicate, z, activityRecord);
    }

    @Override // com.android.server.wm.WindowContainer
    public Task getTask(Predicate predicate, boolean z) {
        Type type = this.mType;
        if (type == Type.ABOVE_TASKS || type == Type.BELOW_TASKS) {
            return null;
        }
        return super.getTask(predicate, z);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean forAllActivities(Predicate predicate, boolean z) {
        Type type = this.mType;
        if (type == Type.ABOVE_TASKS || type == Type.BELOW_TASKS) {
            return false;
        }
        return super.forAllActivities(predicate, z);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean forAllRootTasks(Predicate predicate, boolean z) {
        Type type = this.mType;
        if (type == Type.ABOVE_TASKS || type == Type.BELOW_TASKS) {
            return false;
        }
        return super.forAllRootTasks(predicate, z);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean forAllTasks(Predicate predicate) {
        Type type = this.mType;
        if (type == Type.ABOVE_TASKS || type == Type.BELOW_TASKS) {
            return false;
        }
        return super.forAllTasks(predicate);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean forAllLeafTasks(Predicate predicate) {
        Type type = this.mType;
        if (type == Type.ABOVE_TASKS || type == Type.BELOW_TASKS) {
            return false;
        }
        return super.forAllLeafTasks(predicate);
    }

    @Override // com.android.server.wm.WindowContainer
    public void forAllDisplayAreas(Consumer consumer) {
        super.forAllDisplayAreas(consumer);
        consumer.accept(this);
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
            if (windowContainer.asDisplayArea() != null && windowContainer.asDisplayArea().forAllTaskDisplayAreas(predicate, z)) {
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
    public Object reduceOnAllTaskDisplayAreas(BiFunction biFunction, Object obj, boolean z) {
        if (this.mType != Type.ANY) {
            return obj;
        }
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(i);
            if (windowContainer.asDisplayArea() != null) {
                obj = windowContainer.asDisplayArea().reduceOnAllTaskDisplayAreas(biFunction, obj, z);
            }
            i += z ? -1 : 1;
        }
        return obj;
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
            if (windowContainer.asDisplayArea() != null && (itemFromTaskDisplayAreas = windowContainer.asDisplayArea().getItemFromTaskDisplayAreas(function, z)) != null) {
                return itemFromTaskDisplayAreas;
            }
            i += z ? -1 : 1;
        }
        return null;
    }

    public void setOrganizer(IDisplayAreaOrganizer iDisplayAreaOrganizer) {
        setOrganizer(iDisplayAreaOrganizer, false);
    }

    public void setOrganizer(IDisplayAreaOrganizer iDisplayAreaOrganizer, boolean z) {
        if (this.mOrganizer == iDisplayAreaOrganizer) {
            return;
        }
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent == null || !displayContent.isTrusted()) {
            throw new IllegalStateException("Don't organize or trigger events for unavailable or untrusted display.");
        }
        IDisplayAreaOrganizer iDisplayAreaOrganizer2 = this.mOrganizer;
        this.mOrganizer = iDisplayAreaOrganizer;
        sendDisplayAreaVanished(iDisplayAreaOrganizer2);
        if (z) {
            return;
        }
        sendDisplayAreaAppeared();
    }

    public void sendDisplayAreaAppeared() {
        IDisplayAreaOrganizer iDisplayAreaOrganizer = this.mOrganizer;
        if (iDisplayAreaOrganizer == null) {
            return;
        }
        this.mOrganizerController.onDisplayAreaAppeared(iDisplayAreaOrganizer, this);
    }

    public void sendDisplayAreaVanished(IDisplayAreaOrganizer iDisplayAreaOrganizer) {
        if (iDisplayAreaOrganizer == null) {
            return;
        }
        if (this.mOrganizer == null) {
            Slog.w(StartingSurfaceController.TAG, "sendDisplayAreaVanished: Use pending=" + getPendingTransaction() + ", sync=" + getSyncTransaction());
            migrateToNewSurfaceControl(getPendingTransaction());
        } else {
            migrateToNewSurfaceControl(getSyncTransaction());
        }
        this.mOrganizerController.onDisplayAreaVanished(iDisplayAreaOrganizer, this);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(Configuration configuration) {
        this.mTransitionController.collectForDisplayAreaChange(this);
        this.mTmpConfiguration.setTo(getConfiguration());
        super.onConfigurationChanged(configuration);
        if (this.mOrganizer == null || getConfiguration().diff(this.mTmpConfiguration) == 0) {
            return;
        }
        this.mOrganizerController.onDisplayAreaInfoChanged(this.mOrganizer, this);
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
        if ((appBounds != null && !appBounds.isEmpty()) || appBounds2 == null || appBounds2.isEmpty()) {
            return;
        }
        Rect rect = new Rect(bounds);
        rect.intersect(appBounds2);
        resolvedOverrideConfiguration.windowConfiguration.setAppBounds(rect);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isOrganized() {
        return this.mOrganizer != null;
    }

    public DisplayAreaInfo getDisplayAreaInfo() {
        DisplayAreaInfo displayAreaInfo = new DisplayAreaInfo(this.mRemoteToken.toWindowContainerToken(), getDisplayContent().getDisplayId(), this.mFeatureId);
        RootDisplayArea rootDisplayArea = getRootDisplayArea();
        if (rootDisplayArea == null) {
            rootDisplayArea = getDisplayContent();
        }
        displayAreaInfo.rootDisplayAreaId = rootDisplayArea.mFeatureId;
        displayAreaInfo.configuration.setTo(getConfiguration());
        return displayAreaInfo;
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

    @Override // com.android.server.wm.WindowContainer
    public void removeImmediately() {
        setOrganizer(null);
        super.removeImmediately();
    }

    public class Tokens extends DisplayArea {
        public final Predicate mGetOrientingWindow;
        public int mLastKeyguardForcedOrientation;
        public final Comparator mWindowComparator;

        @Override // com.android.server.wm.DisplayArea
        public final Tokens asTokens() {
            return this;
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ void commitPendingTransaction() {
            super.commitPendingTransaction();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
        public /* bridge */ /* synthetic */ int compareTo(WindowContainer windowContainer) {
            return super.compareTo(windowContainer);
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ SurfaceControl getAnimationLeash() {
            return super.getAnimationLeash();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ SurfaceControl getAnimationLeashParent() {
            return super.getAnimationLeashParent();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
        public /* bridge */ /* synthetic */ DisplayContent getDisplayContent() {
            return super.getDisplayContent();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceFreezer.Freezable
        public /* bridge */ /* synthetic */ SurfaceControl getFreezeSnapshotTarget() {
            return super.getFreezeSnapshotTarget();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
        public /* bridge */ /* synthetic */ SparseArray getInsetsSourceProviders() {
            return super.getInsetsSourceProviders();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ SurfaceControl getParentSurfaceControl() {
            return super.getParentSurfaceControl();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
        public /* bridge */ /* synthetic */ SurfaceControl.Transaction getPendingTransaction() {
            return super.getPendingTransaction();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ SurfaceControl getSurfaceControl() {
            return super.getSurfaceControl();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ int getSurfaceHeight() {
            return super.getSurfaceHeight();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ int getSurfaceWidth() {
            return super.getSurfaceWidth();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ SurfaceControl.Transaction getSyncTransaction() {
            return super.getSyncTransaction();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
        public /* bridge */ /* synthetic */ boolean hasInsetsSourceProvider() {
            return super.hasInsetsSourceProvider();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ SurfaceControl.Builder makeAnimationLeash() {
            return super.makeAnimationLeash();
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ void onAnimationLeashCreated(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
            super.onAnimationLeashCreated(transaction, surfaceControl);
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
        public /* bridge */ /* synthetic */ void onAnimationLeashLost(SurfaceControl.Transaction transaction) {
            super.onAnimationLeashLost(transaction);
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
        public /* bridge */ /* synthetic */ void onRequestedOverrideConfigurationChanged(Configuration configuration) {
            super.onRequestedOverrideConfigurationChanged(configuration);
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceFreezer.Freezable
        public /* bridge */ /* synthetic */ void onUnfrozen() {
            super.onUnfrozen();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$new$0(WindowState windowState) {
            if (!windowState.isVisible() || !windowState.mLegacyPolicyVisibilityAfterAnim) {
                return false;
            }
            WindowManagerPolicy windowManagerPolicy = this.mWmService.mPolicy;
            if (windowManagerPolicy.isKeyguardHostWindow(windowState.mAttrs)) {
                if (!this.mDisplayContent.isKeyguardLocked() && ((this.mDisplayContent.getDisplayPolicy().isAwake() || this.mWmService.mPowerManager.isInteractive()) && windowManagerPolicy.okToAnimate(true))) {
                    return false;
                }
                boolean z = this.mDisplayContent.mAppTransition.isUnoccluding() && this.mDisplayContent.mUnknownAppVisibilityController.allResolved();
                if (windowManagerPolicy.isKeyguardShowingAndNotOccluded() || z) {
                    return true;
                }
            }
            int i = windowState.mAttrs.screenOrientation;
            return (i == -1 || i == 3 || i == -2) ? false : true;
        }

        public Tokens(WindowManagerService windowManagerService, Type type, String str) {
            this(windowManagerService, type, str, 2);
        }

        public Tokens(WindowManagerService windowManagerService, Type type, String str, int i) {
            super(windowManagerService, type, str, i);
            this.mLastKeyguardForcedOrientation = -1;
            this.mWindowComparator = Comparator.comparingInt(new ToIntFunction() { // from class: com.android.server.wm.DisplayArea$Tokens$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((WindowToken) obj).getWindowLayerFromType();
                }
            });
            this.mGetOrientingWindow = new Predicate() { // from class: com.android.server.wm.DisplayArea$Tokens$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$new$0;
                    lambda$new$0 = DisplayArea.Tokens.this.lambda$new$0((WindowState) obj);
                    return lambda$new$0;
                }
            };
        }

        public void addChild(WindowToken windowToken) {
            addChild(windowToken, this.mWindowComparator);
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
        public int getOrientation(int i) {
            this.mLastOrientationSource = null;
            WindowState window = getWindow(this.mGetOrientingWindow);
            if (window == null) {
                return i;
            }
            int i2 = window.mAttrs.screenOrientation;
            if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 1674747211, 20, (String) null, new Object[]{String.valueOf(window), Long.valueOf(i2), Long.valueOf(this.mDisplayContent.getDisplayId())});
            }
            if (this.mWmService.mPolicy.isKeyguardHostWindow(window.mAttrs)) {
                if (i2 != -2 && i2 != -1) {
                    this.mLastKeyguardForcedOrientation = i2;
                } else {
                    i2 = this.mLastKeyguardForcedOrientation;
                }
            }
            this.mLastOrientationSource = window;
            return i2;
        }

        @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
        public void resolveOverrideConfiguration(Configuration configuration) {
            super.resolveOverrideConfiguration(configuration);
            if (configuration.windowConfiguration.getWindowingMode() == 5) {
                getResolvedOverrideConfiguration().windowConfiguration.setWindowingMode(1);
            }
        }
    }

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
            this.mDimmer.resetDimStates();
            super.prepareSurfaces();
            Rect dimBounds = this.mDimmer.getDimBounds();
            if (dimBounds != null) {
                getBounds(dimBounds);
                dimBounds.offsetTo(0, 0);
            }
            if (!this.mTransitionController.isShellTransitionsEnabled() && forAllTasks(new Predicate() { // from class: com.android.server.wm.DisplayArea$Dimmable$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$prepareSurfaces$0;
                    lambda$prepareSurfaces$0 = DisplayArea.Dimmable.lambda$prepareSurfaces$0((Task) obj);
                    return lambda$prepareSurfaces$0;
                }
            })) {
                this.mDimmer.resetDimStates();
            }
            if (dimBounds == null || !this.mDimmer.updateDims(getSyncTransaction())) {
                return;
            }
            scheduleAnimation();
        }

        public static /* synthetic */ boolean lambda$prepareSurfaces$0(Task task) {
            return !task.canAffectSystemUiFlags();
        }
    }

    enum Type {
        ABOVE_TASKS,
        BELOW_TASKS,
        ANY;

        public static void checkSiblings(Type type, Type type2) {
            Type type3 = BELOW_TASKS;
            Preconditions.checkState(type == type3 || type2 != type3, type + " must be above BELOW_TASKS");
            Type type4 = ABOVE_TASKS;
            Preconditions.checkState(type != type4 || type2 == type4, type2 + " must be below ABOVE_TASKS");
        }

        public static void checkChild(Type type, Type type2) {
            int i = AbstractC28961.$SwitchMap$com$android$server$wm$DisplayArea$Type[type.ordinal()];
            if (i == 1) {
                Preconditions.checkState(type2 == ABOVE_TASKS, "ABOVE_TASKS can only contain ABOVE_TASKS");
            } else {
                if (i != 2) {
                    return;
                }
                Preconditions.checkState(type2 == BELOW_TASKS, "BELOW_TASKS can only contain BELOW_TASKS");
            }
        }

        public static Type typeOf(WindowContainer windowContainer) {
            if (windowContainer.asDisplayArea() != null) {
                return ((DisplayArea) windowContainer).mType;
            }
            if ((windowContainer instanceof WindowToken) && !(windowContainer instanceof ActivityRecord)) {
                return typeOf((WindowToken) windowContainer);
            }
            if (windowContainer instanceof Task) {
                return ANY;
            }
            throw new IllegalArgumentException("Unknown container: " + windowContainer);
        }

        public static Type typeOf(WindowToken windowToken) {
            if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_AS_CAPTURED_BLUR && CustomLetterboxConfiguration.isEnhancedControllerToken(windowToken.token)) {
                return BELOW_TASKS;
            }
            return windowToken.getWindowLayerFromType() < 2 ? BELOW_TASKS : ABOVE_TASKS;
        }
    }

    /* renamed from: com.android.server.wm.DisplayArea$1 */
    public abstract /* synthetic */ class AbstractC28961 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$server$wm$DisplayArea$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$com$android$server$wm$DisplayArea$Type = iArr;
            try {
                iArr[Type.ABOVE_TASKS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$server$wm$DisplayArea$Type[Type.BELOW_TASKS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
