package com.android.server.wm;

import android.os.Bundle;
import android.os.IBinder;
import android.util.SparseArray;
import android.view.DisplayAddress;
import android.view.SurfaceControl;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.wm.BLASTSyncEngine;
import com.android.window.flags.Flags;
import com.samsung.android.rune.CoreRune;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WallpaperWindowToken extends WindowToken {
    public SparseArray mCropHints;
    public DisplayAddress mDisplayAddress;
    private boolean mIsForPrimaryDevice;
    public boolean mShowWhenLocked;
    public int mWallpaperDisplayOffsetX;
    public int mWallpaperDisplayOffsetY;
    public float mWallpaperX;
    public float mWallpaperXStep;
    public float mWallpaperY;
    public float mWallpaperYStep;
    public String stringInfo;

    public WallpaperWindowToken(WindowManagerService windowManagerService, IBinder iBinder, DisplayContent displayContent, Bundle bundle) {
        super(windowManagerService, iBinder, 2013, true, displayContent, true, false, false, bundle);
        this.mShowWhenLocked = false;
        this.mWallpaperX = -1.0f;
        this.mWallpaperY = -1.0f;
        this.mWallpaperXStep = -1.0f;
        this.mWallpaperYStep = -1.0f;
        this.mWallpaperDisplayOffsetX = Integer.MIN_VALUE;
        this.mWallpaperDisplayOffsetY = Integer.MIN_VALUE;
        this.mDisplayAddress = null;
        this.mIsForPrimaryDevice = false;
        this.mCropHints = new SparseArray();
        this.stringInfo = null;
        displayContent.mWallpaperController.mWallpaperTokens.add(this);
        setWindowingMode(1);
        if (CoreRune.FW_FOLD_WALLPAPER_POLICY) {
            this.mDisplayAddress = displayContent.mDisplayInfo.address;
            this.mWmService.mExt.getClass();
            throw null;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final WallpaperWindowToken asWallpaperToken() {
        return this;
    }

    @Override // com.android.server.wm.WindowToken
    public final boolean canShowInCurrentDevice() {
        DisplayAddress displayAddress;
        DisplayAddress displayAddress2 = this.mDisplayContent.mDisplayInfo.address;
        if (displayAddress2 == null || (displayAddress = this.mDisplayAddress) == null) {
            return false;
        }
        return displayAddress2.equals(displayAddress);
    }

    public final void commitVisibility(boolean z) {
        if (z == this.mClientVisible) {
            return;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 7214407534407465113L, 60, null, String.valueOf(this), Boolean.valueOf(this.mClientVisible), Boolean.valueOf(this.mVisibleRequested));
        }
        setVisibleRequested(z);
        setVisible$1(z);
    }

    @Override // com.android.server.wm.WindowContainer
    public final void forAllWallpaperWindows(Consumer consumer) {
        consumer.accept(this);
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.ConfigurationContainer
    public final String getName() {
        if (this.stringName == null) {
            toString();
        }
        return this.stringName;
    }

    public final boolean hasVisibleNotDrawnWallpaper() {
        if (!this.mClientVisible) {
            return false;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowState windowState = (WindowState) this.mChildren.get(size);
            if (!windowState.isDrawn() && windowState.isVisible()) {
                return true;
            }
        }
        return false;
    }

    public boolean isForPrimaryDevice() {
        return this.mIsForPrimaryDevice;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isSyncFinished(BLASTSyncEngine.SyncGroup syncGroup) {
        return (this.mVisibleRequested && hasVisibleNotDrawnWallpaper()) ? false : true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isVisible() {
        return this.mClientVisible;
    }

    @Override // com.android.server.wm.WindowToken
    public final void linkFixedRotationTransform(WindowToken windowToken) {
        if (!this.mIsPortraitWindowToken && windowToken.hasFixedRotationTransform()) {
            if (this.mTransitionController.isCollecting(windowToken)) {
                this.mTransitionController.collect(this);
                if (this.mClientVisible && isVisibleRequested()) {
                    this.mTransitionController.collectVisibleChange(this);
                }
            }
            super.linkFixedRotationTransform(windowToken);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.server.wm.WallpaperWindowToken, com.android.server.wm.WindowContainer] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.server.wm.WindowContainer] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.server.wm.WindowContainer] */
    @Override // com.android.server.wm.WindowContainer
    public final boolean needRemoteWallpaperAnim() {
        if (this.mTransitionController.isCollecting() && (this.mTransitionController.mCollectingTransition.mFlags & 276736) != 0) {
            return false;
        }
        while (this != 0) {
            DisplayArea asDisplayArea = this.asDisplayArea();
            if (asDisplayArea != null && asDisplayArea.mFeatureId == 10002) {
                return true;
            }
            this = this.getParent();
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean onChildVisibleRequestedChanged(WindowContainer windowContainer) {
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void prepareSurfaces() {
        SurfaceControl surfaceControl;
        super.prepareSurfaces();
        if (!Flags.ensureWallpaperInTransitions() || this.mTransitionController.inTransition(this)) {
            return;
        }
        getSyncTransaction().setVisibility(this.mSurfaceControl, this.mClientVisible);
        if (!this.mIsPortraitWindowToken || (surfaceControl = this.mFixedRotationTransformLeash) == null || surfaceControl == null) {
            return;
        }
        SurfaceControl.Transaction syncTransaction = getSyncTransaction();
        SurfaceControl surfaceControl2 = this.mSurfaceControl;
        if (surfaceControl2 != null) {
            syncTransaction.reparent(surfaceControl2, getParentSurfaceControl());
        }
        syncTransaction.remove(this.mFixedRotationTransformLeash);
        this.mFixedRotationTransformLeash = null;
    }

    @Override // com.android.server.wm.WindowToken
    public final void setExiting(boolean z) {
        super.setExiting(z);
        this.mDisplayContent.mWallpaperController.mWallpaperTokens.remove(this);
    }

    public final void setVisibility(boolean z) {
        ActivityRecord activityRecord;
        if (this.mVisibleRequested != z) {
            WindowState windowState = this.mDisplayContent.mWallpaperController.mWallpaperTarget;
            if ((windowState != null && ((activityRecord = windowState.mActivityRecord) == null || this.mTransitionController.isCollecting(activityRecord))) || z || (CoreRune.FW_SHELL_TRANSITION_AOD_APPEAR && this.mTransitionController.isCollecting() && (this.mTransitionController.mCollectingTransition.mFlags & 262144) != 0)) {
                this.mTransitionController.collect(this);
            }
            setVisibleRequested(z);
        }
        if (z || !(this.mTransitionController.inTransition() || getDisplayContent().mAppTransition.mAppTransitionState == 2)) {
            commitVisibility(z);
        }
    }

    public final void setVisible$1(boolean z) {
        boolean z2 = this.mClientVisible;
        setClientVisible(z);
        if (!z || z2) {
            return;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowState) this.mChildren.get(size)).requestUpdateWallpaperIfNeeded();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean setVisibleRequested(boolean z) {
        if (!super.setVisibleRequested(z)) {
            return false;
        }
        forAllWindows((Consumer) new WindowToken$$ExternalSyntheticLambda1(this, !z), true);
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean showWallpaper() {
        return false;
    }

    @Override // com.android.server.wm.WindowToken
    public final String toString() {
        if (this.stringName == null) {
            this.stringName = "WallpaperWindowToken{" + Integer.toHexString(System.identityHashCode(this)) + " token=" + this.token + '}';
        }
        return this.stringName + toStringInfo(false);
    }

    public final String toStringInfo(boolean z) {
        if (this.stringInfo == null || z) {
            StringBuilder sb = new StringBuilder("_<");
            sb.append(this.mShowWhenLocked ? "lock" : "system");
            if (CoreRune.FW_FOLD_WALLPAPER_POLICY) {
                sb.append("|");
                sb.append(this.mIsForPrimaryDevice ? "primary" : "non-primary");
            }
            sb.append(">");
            this.stringInfo = sb.toString();
        }
        return this.stringInfo;
    }

    public final void updateWallpaperOffset(boolean z) {
        WallpaperController wallpaperController = this.mDisplayContent.mWallpaperController;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (wallpaperController.updateWallpaperOffset((WindowState) this.mChildren.get(size), z && !this.mWmService.mFlags.mWallpaperOffsetAsync)) {
                z = false;
            }
        }
    }
}
