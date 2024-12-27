package com.android.server.wm;

import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.util.proto.ProtoOutputStream;
import android.view.DisplayInfo;
import android.view.SurfaceControl;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.policy.WindowManagerPolicy;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

public class WindowToken extends WindowContainer {
    public boolean mClientVisible;
    public SurfaceControl mFixedRotationTransformLeash;
    public FixedRotationTransformState mFixedRotationTransformState;
    public final boolean mFromClientToken;
    public boolean mIsPortraitWindowToken;
    public final Bundle mOptions;
    public final boolean mOwnerCanManageAppTokens;
    public boolean mPersistOnEmpty;
    public final boolean mRoundedCornerOverlay;
    public final WindowToken$$ExternalSyntheticLambda0 mWindowComparator;
    public boolean paused;
    public String stringName;
    public final IBinder token;
    public final int windowType;

    public class FixedRotationTransformState {
        public final DisplayFrames mDisplayFrames;
        public final DisplayInfo mDisplayInfo;
        public final Configuration mRotatedOverrideConfiguration;
        public final ArrayList mAssociatedTokens = new ArrayList(3);
        public boolean mIsTransforming = true;

        public FixedRotationTransformState(
                DisplayInfo displayInfo, DisplayFrames displayFrames, Configuration configuration) {
            this.mDisplayInfo = displayInfo;
            this.mDisplayFrames = displayFrames;
            this.mRotatedOverrideConfiguration = configuration;
        }

        public void disassociate(WindowToken windowToken) {
            this.mAssociatedTokens.remove(windowToken);
        }

        public void resetTransform() {
            for (int size = this.mAssociatedTokens.size() - 1; size >= 0; size--) {
                WindowToken windowToken = (WindowToken) this.mAssociatedTokens.get(size);
                if (windowToken.mFixedRotationTransformLeash != null) {
                    SurfaceControl.Transaction syncTransaction = windowToken.getSyncTransaction();
                    SurfaceControl surfaceControl = windowToken.mSurfaceControl;
                    if (surfaceControl != null) {
                        syncTransaction.reparent(
                                surfaceControl, windowToken.getParentSurfaceControl());
                    }
                    syncTransaction.remove(windowToken.mFixedRotationTransformLeash);
                    windowToken.mFixedRotationTransformLeash = null;
                }
            }
        }

        public void transform(WindowContainer windowContainer) {}
    }

    public final class FixedRotationTransformStateLegacy extends FixedRotationTransformState {
        public final ArrayList mRotatedContainers;
        public final SeamlessRotator mRotator;

        public FixedRotationTransformStateLegacy(
                DisplayInfo displayInfo,
                DisplayFrames displayFrames,
                Configuration configuration,
                int i) {
            super(displayInfo, displayFrames, configuration);
            this.mRotatedContainers = new ArrayList(3);
            this.mRotator = new SeamlessRotator(displayInfo.rotation, i, displayInfo, true);
        }

        @Override // com.android.server.wm.WindowToken.FixedRotationTransformState
        public final void disassociate(WindowToken windowToken) {
            super.disassociate(windowToken);
            this.mRotatedContainers.remove(windowToken);
        }

        @Override // com.android.server.wm.WindowToken.FixedRotationTransformState
        public final void resetTransform() {
            for (int size = this.mRotatedContainers.size() - 1; size >= 0; size--) {
                WindowContainer windowContainer =
                        (WindowContainer) this.mRotatedContainers.get(size);
                if (windowContainer.getParent() != null) {
                    this.mRotator.finish(windowContainer.getPendingTransaction(), windowContainer);
                }
            }
        }

        @Override // com.android.server.wm.WindowToken.FixedRotationTransformState
        public final void transform(WindowContainer windowContainer) {
            this.mRotator.unrotate(windowContainer.getPendingTransaction(), windowContainer);
            if (this.mRotatedContainers.contains(windowContainer)) {
                return;
            }
            this.mRotatedContainers.add(windowContainer);
        }
    }

    public WindowToken(
            WindowManagerService windowManagerService,
            IBinder iBinder,
            int i,
            boolean z,
            DisplayContent displayContent,
            boolean z2,
            boolean z3,
            boolean z4,
            Bundle bundle) {
        super(windowManagerService);
        this.paused = false;
        this.mWindowComparator =
                new Comparator() { // from class:
                                   // com.android.server.wm.WindowToken$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        WindowToken windowToken = WindowToken.this;
                        WindowState windowState = (WindowState) obj;
                        WindowState windowState2 = (WindowState) obj2;
                        windowToken.getClass();
                        if (windowState.mToken != windowToken) {
                            throw new IllegalArgumentException(
                                    "newWindow="
                                            + windowState
                                            + " is not a child of token="
                                            + windowToken);
                        }
                        if (windowState2.mToken == windowToken) {
                            return windowToken.isFirstChildWindowGreaterThanSecond(
                                            windowState, windowState2)
                                    ? 1
                                    : -1;
                        }
                        throw new IllegalArgumentException(
                                "existingWindow="
                                        + windowState2
                                        + " is not a child of token="
                                        + windowToken);
                    }
                };
        this.token = iBinder;
        this.windowType = i;
        this.mOptions = bundle;
        this.mPersistOnEmpty = z;
        this.mOwnerCanManageAppTokens = z2;
        this.mRoundedCornerOverlay = z3;
        this.mFromClientToken = z4;
        if (displayContent != null) {
            displayContent.addWindowToken(iBinder, this);
        }
    }

    public void addWindow(WindowState windowState) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_FOCUS,
                    2740931087734487464L,
                    0,
                    null,
                    String.valueOf(windowState),
                    String.valueOf(Debug.getCallers(5)));
        }
        if (windowState.mIsChildWindow) {
            return;
        }
        if (this.mSurfaceControl == null) {
            createSurfaceControl(true);
            reassignLayer(getSyncTransaction());
        }
        if (this.mChildren.contains(windowState)) {
            return;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_ADD_REMOVE,
                    2382798629637143561L,
                    0,
                    null,
                    String.valueOf(windowState),
                    String.valueOf(this));
        }
        addChild(windowState, this.mWindowComparator);
        this.mWmService.mWindowsChanged = true;
    }

    public void applyFixedRotationTransform(
            DisplayInfo displayInfo, DisplayFrames displayFrames, Configuration configuration) {
        FixedRotationTransformState fixedRotationTransformState = this.mFixedRotationTransformState;
        if (fixedRotationTransformState != null) {
            fixedRotationTransformState.disassociate(this);
        }
        Configuration configuration2 = new Configuration(configuration);
        FixedRotationTransformState fixedRotationTransformState2 =
                this.mTransitionController.isShellTransitionsEnabled()
                        ? new FixedRotationTransformState(
                                displayInfo, displayFrames, configuration2)
                        : new FixedRotationTransformStateLegacy(
                                displayInfo,
                                displayFrames,
                                configuration2,
                                this.mDisplayContent.mDisplayRotation.mRotation);
        this.mFixedRotationTransformState = fixedRotationTransformState2;
        fixedRotationTransformState2.mAssociatedTokens.add(this);
        this.mDisplayContent.mDisplayPolicy.simulateLayoutDisplay(displayFrames);
        onFixedRotationStatePrepared();
    }

    @Override // com.android.server.wm.WindowContainer
    public final WindowToken asWindowToken() {
        return this;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void assignLayer(SurfaceControl.Transaction transaction, int i) {
        if (this.mRoundedCornerOverlay) {
            super.assignLayer(transaction, 1073741826);
        } else {
            super.assignLayer(transaction, i);
        }
        DexController dexController = this.mWmService.mAtmService.mDexController;
        dexController.getClass();
        DisplayContent displayContent = getDisplayContent();
        if (displayContent != null
                && displayContent.isDefaultDisplay
                && dexController.shouldShowDexImeInDefaultDisplayLocked()) {
            WindowState windowState =
                    displayContent.mDisplayPolicy.mExt.mTaskbarController.mTaskbarWin;
            if ((windowState == null || windowState.mToken != this) && this.windowType != 2019) {
                return;
            }
            DisplayContent displayContent2 = this.mDisplayContent;
            SurfaceControl surfaceControl = this.mSurfaceControl;
            if (displayContent2.mImeWindowsContainer.getSurfaceControl() != null) {
                DisplayContent.ImeContainer imeContainer = displayContent2.mImeWindowsContainer;
                imeContainer.mNeedsLayer = true;
                imeContainer.assignRelativeLayer(transaction, surfaceControl, -1);
            }
        }
    }

    public boolean canShowInCurrentDevice() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void createSurfaceControl(boolean z) {
        if (!this.mFromClientToken || z) {
            super.createSurfaceControl(z);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void dump(PrintWriter printWriter, String str, boolean z) {
        super.dump(printWriter, str, z);
        printWriter.print(str);
        printWriter.print("windows=");
        printWriter.println(this.mChildren);
        printWriter.print(str);
        printWriter.print("windowType=");
        printWriter.print(this.windowType);
        printWriter.println();
        if (hasFixedRotationTransform()) {
            printWriter.print(str);
            printWriter.print("fixedRotationConfig=");
            printWriter.println(this.mFixedRotationTransformState.mRotatedOverrideConfiguration);
        }
        if (this.mIsPortraitWindowToken) {
            printWriter.print(str);
            printWriter.println("mIsPortraitWindowToken=true");
        }
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        if (i != 2 || isVisible()) {
            long start = protoOutputStream.start(j);
            super.dumpDebug(protoOutputStream, 1146756268033L, i);
            protoOutputStream.write(1120986464258L, System.identityHashCode(this));
            protoOutputStream.write(1133871366150L, this.paused);
            protoOutputStream.end(start);
        }
    }

    public final void finishFixedRotationTransform(
            DisplayContent$$ExternalSyntheticLambda6 displayContent$$ExternalSyntheticLambda6) {
        WindowContainer parent;
        FixedRotationTransformState fixedRotationTransformState = this.mFixedRotationTransformState;
        if (fixedRotationTransformState == null) {
            return;
        }
        fixedRotationTransformState.resetTransform();
        fixedRotationTransformState.mIsTransforming = false;
        if (displayContent$$ExternalSyntheticLambda6 != null) {
            displayContent$$ExternalSyntheticLambda6.run();
        }
        for (int size = fixedRotationTransformState.mAssociatedTokens.size() - 1;
                size >= 0;
                size--) {
            WindowToken windowToken =
                    (WindowToken) fixedRotationTransformState.mAssociatedTokens.get(size);
            windowToken.mFixedRotationTransformState = null;
            if (displayContent$$ExternalSyntheticLambda6 == null
                    && (parent = windowToken.getParent()) != null) {
                if (windowToken.mTransitionController.isShellTransitionsEnabled()
                        && windowToken.asActivityRecord() != null
                        && windowToken.isVisible()) {
                    TransitionController transitionController = windowToken.mTransitionController;
                    Transition transition = transitionController.mCollectingTransition;
                    if (transition == null) {
                        transition =
                                transitionController.requestStartTransition(
                                        transitionController.createTransition(6, 0),
                                        null,
                                        null,
                                        null);
                    }
                    transition.collect(windowToken, false);
                    transition.collectVisibleChange(windowToken);
                    transition.setReady(windowToken.mDisplayContent, true);
                }
                int rotation = windowToken.getWindowConfiguration().getRotation();
                windowToken.onConfigurationChanged(parent.getConfiguration());
                windowToken.onCancelFixedRotationTransform(rotation);
            }
        }
    }

    public float getCompatScale() {
        return this.mDisplayContent.mCompatibleScreenScale;
    }

    public final Rect getFixedRotationTransformDisplayBounds() {
        if (isFixedRotationTransforming()) {
            return this.mFixedRotationTransformState.mRotatedOverrideConfiguration
                    .windowConfiguration.getBounds();
        }
        return null;
    }

    public final DisplayFrames getFixedRotationTransformDisplayFrames() {
        if (isFixedRotationTransforming()) {
            return this.mFixedRotationTransformState.mDisplayFrames;
        }
        return null;
    }

    public final DisplayInfo getFixedRotationTransformDisplayInfo() {
        if (isFixedRotationTransforming()) {
            return this.mFixedRotationTransformState.mDisplayInfo;
        }
        return null;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public String getName() {
        return toString();
    }

    public final SurfaceControl getOrCreateFixedRotationLeash(
            SurfaceControl.Transaction transaction) {
        if (!this.mTransitionController.isShellTransitionsEnabled()) {
            return null;
        }
        int relativeDisplayRotation = getRelativeDisplayRotation();
        if (relativeDisplayRotation == 0) {
            return this.mFixedRotationTransformLeash;
        }
        SurfaceControl surfaceControl = this.mFixedRotationTransformLeash;
        if (surfaceControl != null) {
            return surfaceControl;
        }
        SurfaceControl build =
                makeSurface()
                        .setContainerLayer()
                        .setParent(getParentSurfaceControl())
                        .setName(this.mSurfaceControl + " - rotation-leash")
                        .setHidden(false)
                        .setCallsite("WindowToken.getOrCreateFixedRotationLeash")
                        .build();
        if (this.mIsPortraitWindowToken) {
            transaction.reparent(this.mSurfaceControl, build);
            this.mFixedRotationTransformLeash = build;
            return build;
        }
        Point point = this.mLastSurfacePosition;
        transaction.setPosition(build, point.x, point.y);
        transaction.reparent(this.mSurfaceControl, build);
        getPendingTransaction()
                .setFixedTransformHint(build, getWindowConfiguration().getDisplayRotation());
        this.mFixedRotationTransformLeash = build;
        updateSurfaceRotation(transaction, relativeDisplayRotation, build);
        return this.mFixedRotationTransformLeash;
    }

    @Override // com.android.server.wm.WindowContainer
    public long getProtoFieldId() {
        return 1146756268039L;
    }

    public final int getWindowLayerFromType() {
        WindowManagerPolicy windowManagerPolicy = this.mWmService.mPolicy;
        int i = this.windowType;
        boolean z = this.mOwnerCanManageAppTokens;
        boolean z2 = this.mRoundedCornerOverlay;
        windowManagerPolicy.getClass();
        return WindowManagerPolicy.getWindowLayerFromTypeLw(i, z, z2);
    }

    @Override // com.android.server.wm.WindowContainer
    public final int getWindowType() {
        return this.windowType;
    }

    public final boolean hasFixedRotationTransform() {
        return this.mFixedRotationTransformState != null;
    }

    public boolean hasSizeCompatBounds() {
        return false;
    }

    public boolean isFirstChildWindowGreaterThanSecond(
            WindowState windowState, WindowState windowState2) {
        return windowState.mBaseLayer >= windowState2.mBaseLayer;
    }

    public final boolean isFixedRotationTransforming() {
        FixedRotationTransformState fixedRotationTransformState = this.mFixedRotationTransformState;
        return fixedRotationTransformState != null && fixedRotationTransformState.mIsTransforming;
    }

    public void linkFixedRotationTransform(WindowToken windowToken) {
        FixedRotationTransformState fixedRotationTransformState;
        FixedRotationTransformState fixedRotationTransformState2 =
                windowToken.mFixedRotationTransformState;
        if (fixedRotationTransformState2 == null
                || (fixedRotationTransformState = this.mFixedRotationTransformState)
                        == fixedRotationTransformState2) {
            return;
        }
        if (fixedRotationTransformState != null) {
            fixedRotationTransformState.disassociate(this);
        }
        this.mFixedRotationTransformState = fixedRotationTransformState2;
        fixedRotationTransformState2.mAssociatedTokens.add(this);
        onFixedRotationStatePrepared();
    }

    @Override // com.android.server.wm.WindowContainer
    public final SurfaceControl.Builder makeSurface() {
        SurfaceControl.Builder makeSurface = super.makeSurface();
        if (this.mRoundedCornerOverlay) {
            makeSurface.setParent(null);
        }
        return makeSurface;
    }

    public void onCancelFixedRotationTransform(int i) {}

    @Override // com.android.server.wm.WindowContainer
    public void onDisplayChanged(DisplayContent displayContent) {
        FixedRotationTransformState fixedRotationTransformState;
        displayContent.reParentWindowToken(this);
        if (this.mIsPortraitWindowToken
                && (fixedRotationTransformState = this.mFixedRotationTransformState) != null) {
            DisplayInfo displayInfo = displayContent.mDisplayInfo;
            DisplayInfo displayInfo2 = fixedRotationTransformState.mDisplayInfo;
            if (displayInfo.getNaturalWidth() != displayInfo2.getNaturalWidth()
                    || displayInfo.getNaturalHeight() != displayInfo2.getNaturalHeight()) {
                finishFixedRotationTransform(null);
                displayContent.startFixedRotationTransform(this, 0);
            }
        }
        super.onDisplayChanged(displayContent);
    }

    public final void onFixedRotationStatePrepared() {
        onConfigurationChanged(getParent().getConfiguration());
        ActivityRecord asActivityRecord = asActivityRecord();
        if (asActivityRecord == null || !asActivityRecord.hasProcess()) {
            return;
        }
        asActivityRecord.app.registerActivityConfigurationListener(asActivityRecord);
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean prepareSync() {
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent != null
                && displayContent.isRotationChanging()
                && AsyncRotationController.canBeAsync(this)) {
            return false;
        }
        return super.prepareSync();
    }

    public final void removeAllWindowsIfPossible() {
        int size = this.mChildren.size() - 1;
        while (size >= 0) {
            WindowState windowState = (WindowState) this.mChildren.get(size);
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_MOVEMENT_enabled[3]) {
                ProtoLogImpl_54989576.w(
                        ProtoLogGroup.WM_DEBUG_WINDOW_MOVEMENT,
                        8174298531248485625L,
                        0,
                        null,
                        String.valueOf(windowState));
            }
            windowState.removeIfPossible();
            if (size > this.mChildren.size()) {
                size = this.mChildren.size();
            }
            size--;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void removeImmediately() {
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent != null) {
            displayContent.removeWindowToken(this.token, true);
        }
        super.removeImmediately();
    }

    @Override // com.android.server.wm.WindowContainer
    public void resetSurfacePositionForAnimationLeash(SurfaceControl.Transaction transaction) {
        if (isFixedRotationTransforming()) {
            return;
        }
        super.resetSurfacePositionForAnimationLeash(transaction);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void resolveOverrideConfiguration(Configuration configuration) {
        super.resolveOverrideConfiguration(configuration);
        if (isFixedRotationTransforming()) {
            getResolvedOverrideConfiguration()
                    .updateFrom(this.mFixedRotationTransformState.mRotatedOverrideConfiguration);
        }
    }

    public void setClientVisible(boolean z) {
        if (this.mClientVisible == z) {
            return;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
                    -7314975896738778749L,
                    12,
                    null,
                    String.valueOf(this),
                    Boolean.valueOf(z),
                    String.valueOf(Debug.getCallers(5)));
        }
        this.mClientVisible = z;
        sendAppVisibilityToClients();
    }

    public void setExiting(boolean z) {
        if (this.mChildren.isEmpty()) {
            super.removeImmediately();
            return;
        }
        this.mPersistOnEmpty = false;
        if (isVisible()) {
            int size = this.mChildren.size();
            boolean z2 = false;
            for (int i = 0; i < size; i++) {
                z2 |= ((WindowState) this.mChildren.get(i)).onSetAppExiting(z);
            }
            if (z2) {
                this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
                this.mWmService.updateFocusedWindowLocked(0, false);
            }
        }
    }

    public String toString() {
        if (this.stringName == null) {
            this.stringName =
                    "WindowToken{"
                            + Integer.toHexString(System.identityHashCode(this))
                            + " type="
                            + this.windowType
                            + " "
                            + this.token
                            + "}";
        }
        return this.stringName;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void updateSurfacePosition(SurfaceControl.Transaction transaction) {
        ActivityRecord asActivityRecord = asActivityRecord();
        if (asActivityRecord == null || !asActivityRecord.isConfigurationDispatchPaused()) {
            super.updateSurfacePosition(transaction);
            if (this.mTransitionController.isShellTransitionsEnabled()
                    || !isFixedRotationTransforming()) {
                return;
            }
            Task rootTask = asActivityRecord != null ? asActivityRecord.getRootTask() : null;
            if (rootTask == null || !rootTask.inPinnedWindowingMode()) {
                this.mFixedRotationTransformState.transform(this);
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void updateSurfaceRotation(
            SurfaceControl.Transaction transaction, int i, SurfaceControl surfaceControl) {
        Task rootTask;
        int windowingMode;
        ActivityRecord asActivityRecord = asActivityRecord();
        if (asActivityRecord != null && (rootTask = asActivityRecord.getRootTask()) != null) {
            Transition transition = this.mTransitionController.mCollectingTransition;
            if (transition == null) {
                windowingMode = rootTask.getWindowingMode();
            } else {
                Transition.ChangeInfo changeInfo =
                        (Transition.ChangeInfo) transition.mChanges.get(rootTask);
                windowingMode =
                        changeInfo == null
                                ? rootTask.getWindowingMode()
                                : changeInfo.mWindowingMode;
            }
            if (windowingMode == 2) {
                return;
            }
        }
        super.updateSurfaceRotation(transaction, i, surfaceControl);
    }

    public final boolean windowsCanBeWallpaperTarget() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowState) this.mChildren.get(size)).hasWallpaper()) {
                return true;
            }
        }
        return false;
    }
}
