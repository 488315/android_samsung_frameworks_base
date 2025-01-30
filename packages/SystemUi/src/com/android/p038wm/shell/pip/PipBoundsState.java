package com.android.p038wm.shell.pip;

import android.app.ActivityTaskManager;
import android.app.PictureInPictureParams;
import android.app.PictureInPictureUiState;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;
import android.util.Size;
import com.android.internal.util.function.TriConsumer;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.pip.phone.PipEdgePanelSupport;
import com.android.p038wm.shell.pip.phone.PipSizeSpecHandler;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PipBoundsState {
    public float mAspectRatio;
    public final Context mContext;
    public boolean mHasUserMovedPip;
    public boolean mHasUserResizedPip;
    public int mImeHeight;
    public boolean mIsImeShowing;
    public boolean mIsShelfShowing;
    public ComponentName mLastPipComponentName;
    public Runnable mOnMinimalSizeChangeCallback;
    public IntConsumer mOnPipStashCallback;
    public Runnable mOnPipTaskAppearedCallback;
    public TriConsumer mOnShelfVisibilityChangeCallback;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public int mPipEdgeMargin;
    public PipReentryState mPipReentryState;
    public final PipSizeSpecHandler mPipSizeSpecHandler;
    public PipTransitionState mPipTransitionState;
    public int mShelfHeight;
    public int mStashOffset;
    public final Rect mBounds = new Rect();
    public final Rect mMovementBounds = new Rect();
    public final Rect mNormalBounds = new Rect();
    public final Rect mExpandedBounds = new Rect();
    public final Rect mNormalMovementBounds = new Rect();
    public final Rect mExpandedMovementBounds = new Rect();
    public final Point mMaxSize = new Point();
    public final Point mMinSize = new Point();
    public int mStashedState = 0;
    public final LauncherState mLauncherState = new LauncherState();
    public final MotionBoundsState mMotionBoundsState = new MotionBoundsState();
    public final Set mRestrictedKeepClearAreas = new ArraySet();
    public final Set mUnrestrictedKeepClearAreas = new ArraySet();
    public final Map mNamedUnrestrictedKeepClearAreas = new HashMap();
    public final List mOnPipExclusionBoundsChangeCallbacks = new ArrayList();
    public final Rect mStashInsetBounds = new Rect();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LauncherState {
        public int mAppIconSizePx;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MotionBoundsState {
        public final Rect mBoundsInMotion = new Rect();
        public final Rect mAnimatingToBounds = new Rect();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PipReentryState {
        public final Size mSize;
        public final float mSnapFraction;

        public PipReentryState(Size size, float f) {
            this.mSize = size;
            this.mSnapFraction = f;
        }
    }

    public PipBoundsState(Context context, PipSizeSpecHandler pipSizeSpecHandler, PipDisplayLayoutState pipDisplayLayoutState) {
        this.mContext = context;
        reloadResources();
        this.mPipSizeSpecHandler = pipSizeSpecHandler;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
    }

    public void clearReentryState() {
        this.mPipReentryState = null;
    }

    public final Rect getBounds() {
        return new Rect(this.mBounds);
    }

    public final Rect getDisplayBounds() {
        return this.mPipDisplayLayoutState.getDisplayBounds();
    }

    public final DisplayLayout getDisplayLayout() {
        return this.mPipDisplayLayoutState.getDisplayLayout();
    }

    public final Rect getStashInsets() {
        Context context = this.mContext;
        PipEdgePanelSupport pipEdgePanelSupport = new PipEdgePanelSupport(context);
        Rect stableInsets = getDisplayLayout().stableInsets(false);
        DisplayLayout displayLayout = getDisplayLayout();
        int navigationBarPosition = DisplayLayout.navigationBarPosition(context.getResources(), displayLayout.mWidth, displayLayout.mHeight, displayLayout.mRotation);
        int i = Settings.System.getInt(pipEdgePanelSupport.mContext.getContentResolver(), "active_edge_area", 1);
        Rect rect = this.mStashInsetBounds;
        rect.setEmpty();
        if (navigationBarPosition == 1 || i == 0) {
            rect.left = stableInsets.left;
        } else if (navigationBarPosition == 2 || i == 1) {
            rect.right = stableInsets.right;
        }
        return rect;
    }

    public final Set getUnrestrictedKeepClearAreas() {
        Map map = this.mNamedUnrestrictedKeepClearAreas;
        boolean isEmpty = ((HashMap) map).isEmpty();
        Set set = this.mUnrestrictedKeepClearAreas;
        if (isEmpty) {
            return set;
        }
        ArraySet arraySet = new ArraySet(set);
        arraySet.addAll(((HashMap) map).values());
        return arraySet;
    }

    public final boolean isStashed() {
        return this.mStashedState != 0;
    }

    public void onConfigurationChanged() {
        reloadResources();
    }

    public final void reloadResources() {
        Context context = this.mContext;
        this.mStashOffset = context.getResources().getDimensionPixelSize(R.dimen.pip_stash_offset);
        this.mPipEdgeMargin = context.getResources().getDimensionPixelSize(R.dimen.pip_stash_handle_margin_to_edge_handle);
    }

    public final void setBounds(Rect rect) {
        PipTransitionState pipTransitionState;
        Rect rect2 = this.mBounds;
        if (!rect2.equals(rect)) {
            StringBuilder sb = new StringBuilder("[PipBoundsState] setBounds: ");
            sb.append(rect2);
            sb.append(" -> ");
            sb.append(rect);
            sb.append(", Callers=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m78m(6, sb, "PipTaskOrganizer");
        }
        if (!rect.isEmpty() && !getDisplayBounds().equals(rect) && getDisplayBounds().contains(rect) && isStashed() && (pipTransitionState = this.mPipTransitionState) != null && pipTransitionState.mState > 3) {
            setStashed(0, false);
        }
        rect2.set(rect);
        Iterator it = ((ArrayList) this.mOnPipExclusionBoundsChangeCallbacks).iterator();
        while (it.hasNext()) {
            ((Consumer) it.next()).accept(rect);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setBoundsStateForEntry(ComponentName componentName, ActivityInfo activityInfo, PictureInPictureParams pictureInPictureParams, PipBoundsAlgorithm pipBoundsAlgorithm) {
        float f;
        boolean z;
        Runnable runnable;
        Runnable runnable2;
        setLastPipComponentName(componentName);
        if (pictureInPictureParams != null) {
            pipBoundsAlgorithm.getClass();
            if (pictureInPictureParams.hasSetAspectRatio()) {
                f = pictureInPictureParams.getAspectRatioFloat();
                this.mAspectRatio = f;
                Size minimalSize = pipBoundsAlgorithm.getMinimalSize(activityInfo);
                PipSizeSpecHandler pipSizeSpecHandler = this.mPipSizeSpecHandler;
                z = !Objects.equals(minimalSize, pipSizeSpecHandler.getOverrideMinSize());
                pipSizeSpecHandler.mOverrideMinSize = minimalSize;
                if (z && (runnable2 = this.mOnMinimalSizeChangeCallback) != null) {
                    runnable2.run();
                }
                runnable = this.mOnPipTaskAppearedCallback;
                if (runnable == null) {
                    runnable.run();
                    return;
                }
                return;
            }
        }
        f = pipBoundsAlgorithm.mDefaultAspectRatio;
        this.mAspectRatio = f;
        Size minimalSize2 = pipBoundsAlgorithm.getMinimalSize(activityInfo);
        PipSizeSpecHandler pipSizeSpecHandler2 = this.mPipSizeSpecHandler;
        z = !Objects.equals(minimalSize2, pipSizeSpecHandler2.getOverrideMinSize());
        pipSizeSpecHandler2.mOverrideMinSize = minimalSize2;
        if (z) {
            runnable2.run();
        }
        runnable = this.mOnPipTaskAppearedCallback;
        if (runnable == null) {
        }
    }

    public final void setLastPipComponentName(ComponentName componentName) {
        boolean z = !Objects.equals(this.mLastPipComponentName, componentName);
        this.mLastPipComponentName = componentName;
        if (z) {
            clearReentryState();
            this.mHasUserResizedPip = false;
            this.mHasUserMovedPip = false;
        }
    }

    public final void setShelfVisibility(int i, boolean z, boolean z2) {
        if ((z && i > 0) == this.mIsShelfShowing && i == this.mShelfHeight) {
            return;
        }
        this.mIsShelfShowing = z;
        this.mShelfHeight = i;
        TriConsumer triConsumer = this.mOnShelfVisibilityChangeCallback;
        if (triConsumer != null) {
            triConsumer.accept(Boolean.valueOf(z), Integer.valueOf(this.mShelfHeight), Boolean.valueOf(z2));
        }
    }

    public final void setStashed(int i, boolean z) {
        if (this.mStashedState == i) {
            return;
        }
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(new StringBuilder("setStashed old="), this.mStashedState, " new=", i, "PipTaskOrganizer");
        IntConsumer intConsumer = this.mOnPipStashCallback;
        if (intConsumer != null) {
            intConsumer.accept(i);
        }
        this.mStashedState = i;
        if (z) {
            Log.d("PipTaskOrganizer", "setStashed skipWMCoreUpdate=true");
            return;
        }
        try {
            ActivityTaskManager.getService().onPictureInPictureStateChanged(new PictureInPictureUiState(i != 0));
        } catch (RemoteException unused) {
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -83501820, 0, "%s: Unable to set alert PiP state change.", "PipBoundsState");
            }
        } catch (IllegalStateException unused2) {
            Log.e("PipTaskOrganizer", "[PipBoundsState] setStashed: Activity is not in PIP mode, caller=" + Debug.getCallers(10));
        }
    }

    public final String toString() {
        return "PipBoundsState{mBounds=" + this.mBounds + ", mMovementBounds=" + this.mMovementBounds + ", mNormalBounds=" + this.mNormalBounds + ", mExpandedBounds=" + this.mExpandedBounds + ", mAspectRatio=" + this.mAspectRatio + '}';
    }
}
