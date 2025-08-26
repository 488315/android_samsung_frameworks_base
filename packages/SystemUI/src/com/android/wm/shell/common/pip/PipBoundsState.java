package com.android.wm.shell.common.pip;

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
import android.util.SparseArray;
import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda11;
import com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda3;
import com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda8;
import com.android.wm.shell.pip.phone.PipEdgePanelSupport;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class PipBoundsState {
    public float mAspectRatio;
    public float mBoundsScale;
    public final Context mContext;
    public boolean mHasUserMovedPip;
    public boolean mHasUserResizedPip;
    public int mImeHeight;
    public boolean mIsImeShowing;
    public ComponentName mLastPipComponentName;
    public PipController$$ExternalSyntheticLambda3 mOnMinimalSizeChangeCallback;
    public PipController$$ExternalSyntheticLambda8 mOnPipStashCallback;
    public PipController$$ExternalSyntheticLambda3 mOnPipTaskAppearedCallback;
    public PipController$$ExternalSyntheticLambda11 mOnShelfVisibilityChangeCallback;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public int mPipEdgeMargin;
    public PipReentryState mPipReentryState;
    public PipTransitionState mPipTransitionState;
    public final SizeSpecSource mSizeSpecSource;
    public int mStashOffset;
    public final Rect mBounds = new Rect();
    public final Rect mMovementBounds = new Rect();
    public final Rect mNormalBounds = new Rect();
    public final Rect mExpandedBounds = new Rect();
    public final Rect mNormalMovementBounds = new Rect();
    public final Rect mExpandedMovementBounds = new Rect();
    public final Rect mRestoreBounds = new Rect();
    public final Point mMaxSize = new Point();
    public final Point mMinSize = new Point();
    public int mStashedState = 0;
    public final LauncherState mLauncherState = new LauncherState();
    public final MotionBoundsState mMotionBoundsState = new MotionBoundsState();
    public final Set mRestrictedKeepClearAreas = new ArraySet();
    public final Set mUnrestrictedKeepClearAreas = new ArraySet();
    public final SparseArray mNamedUnrestrictedKeepClearAreas = new SparseArray();
    public final List mOnPipExclusionBoundsChangeCallbacks = new ArrayList();
    public final List mOnAspectRatioChangedCallbacks = new ArrayList();
    public final Rect mCachedLauncherShelfHeightKeepClearArea = new Rect();
    public final List mOnPipComponentChangedListeners = new ArrayList();
    public final Rect mStashInsetBounds = new Rect();

    public final class LauncherState {
        public int mAppIconSizePx;
    }

    public class MotionBoundsState {
        public final Rect mBoundsInMotion = new Rect();
        public final Rect mAnimatingToBounds = new Rect();

        public final boolean isInMotion() {
            return !this.mBoundsInMotion.isEmpty();
        }

        public final void setBoundsInMotion(Rect rect) {
            this.mBoundsInMotion.set(rect);
        }
    }

    public interface OnPipComponentChangedListener {
        void onPipComponentChanged();
    }

    final class PipReentryState {
        public final float mBoundsScale;
        public final float mSnapFraction;

        public PipReentryState(float f, float f2) {
            this.mBoundsScale = f;
            this.mSnapFraction = f2;
        }
    }

    public PipBoundsState(Context context, SizeSpecSource sizeSpecSource, PipDisplayLayoutState pipDisplayLayoutState) {
        this.mContext = context;
        this.mStashOffset = context.getResources().getDimensionPixelSize(R.dimen.pip_stash_offset);
        this.mPipEdgeMargin = context.getResources().getDimensionPixelSize(R.dimen.pip_stash_handle_margin_to_edge_handle);
        this.mSizeSpecSource = sizeSpecSource;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        addPipExclusionBoundsChangeCallback(new Consumer() { // from class: com.android.wm.shell.common.pip.PipBoundsState$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.mBoundsScale = Math.min(r1.mBounds.width() / r1.mMaxSize.x, 1.0f);
            }
        });
    }

    public final void addPipExclusionBoundsChangeCallback(Consumer consumer) {
        this.mOnPipExclusionBoundsChangeCallbacks.add(consumer);
        Iterator it = this.mOnPipExclusionBoundsChangeCallbacks.iterator();
        while (it.hasNext()) {
            ((Consumer) it.next()).accept(getBounds());
        }
    }

    public void clearReentryState() {
        this.mPipReentryState = null;
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder sbM = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "  PipBoundsState", "    mBounds=");
        sbM.append(this.mBounds);
        printWriter.println(sbM.toString());
        printWriter.println("    mNormalBounds=" + this.mNormalBounds);
        printWriter.println("    mExpandedBounds=" + this.mExpandedBounds);
        printWriter.println("    mMovementBounds=" + this.mMovementBounds);
        printWriter.println("    mNormalMovementBounds=" + this.mNormalMovementBounds);
        printWriter.println("    mExpandedMovementBounds=" + this.mExpandedMovementBounds);
        printWriter.println("    mLastPipComponentName=" + this.mLastPipComponentName);
        StringBuilder sbM2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    mAspectRatio="), this.mAspectRatio, printWriter, "    mStashedState="), this.mStashedState, printWriter, "    mStashOffset="), this.mStashOffset, printWriter, "    mIsImeShowing="), this.mIsImeShowing, printWriter, "    mImeHeight=");
        sbM2.append(this.mImeHeight);
        printWriter.println(sbM2.toString());
        printWriter.println("    mIsShelfShowing=false");
        printWriter.println("    mShelfHeight=0");
        StringBuilder sbM3 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    mHasUserMovedPip="), this.mHasUserMovedPip, printWriter, "    mHasUserResizedPip="), this.mHasUserResizedPip, printWriter, "    mMinSize=");
        sbM3.append(this.mMinSize);
        printWriter.println(sbM3.toString());
        printWriter.println("    mMaxSize=" + this.mMaxSize);
        StringBuilder sbM4 = MagnificationImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    mBoundsScale"), this.mBoundsScale, printWriter, "    mRestrictedKeepClearAreas=");
        sbM4.append(this.mRestrictedKeepClearAreas);
        printWriter.println(sbM4.toString());
        printWriter.println("    mUnrestrictedKeepClearAreas=" + this.mUnrestrictedKeepClearAreas);
        PipReentryState pipReentryState = this.mPipReentryState;
        if (pipReentryState == null) {
            printWriter.println("    mPipReentryState=null");
        } else {
            StringBuilder sbM5 = MagnificationImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "    PipBoundsState$PipReentryState", "      mBoundsScale="), pipReentryState.mBoundsScale, printWriter, "      mSnapFraction=");
            sbM5.append(pipReentryState.mSnapFraction);
            printWriter.println(sbM5.toString());
        }
        LauncherState launcherState = this.mLauncherState;
        launcherState.getClass();
        printWriter.println("    ".concat(LauncherState.class.getSimpleName()));
        MagnificationImpl$$ExternalSyntheticOutline0.m(new StringBuilder("        getAppIconSizePx="), launcherState.mAppIconSizePx, printWriter);
        MotionBoundsState motionBoundsState = this.mMotionBoundsState;
        motionBoundsState.getClass();
        printWriter.println("    ".concat(MotionBoundsState.class.getSimpleName()));
        printWriter.println("      mBoundsInMotion=" + motionBoundsState.mBoundsInMotion);
        printWriter.println("      mAnimatingToBounds=" + motionBoundsState.mAnimatingToBounds);
        PhoneSizeSpecSource phoneSizeSpecSource = (PhoneSizeSpecSource) this.mSizeSpecSource;
        phoneSizeSpecSource.getClass();
        printWriter.println("      mOverrideMinSize=" + phoneSizeSpecSource.mOverrideMinSize);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("      mOverridableMinSize=", phoneSizeSpecSource.mOverridableMinSize, printWriter);
        printWriter.println("      mDefaultMinSize=" + phoneSizeSpecSource.mDefaultMinSize);
        printWriter.println("      mDefaultSizePercent=" + (phoneSizeSpecSource.getMIsSquareDisplay() ? phoneSizeSpecSource.mSystemPreferredDefaultSizePercentForSquareDisplay : phoneSizeSpecSource.mSystemPreferredDefaultSizePercent));
        printWriter.println("      mMinimumSizePercent=" + (phoneSizeSpecSource.getMIsSquareDisplay() ? phoneSizeSpecSource.mSystemPreferredMinimumSizePercentForSquareDisplay : phoneSizeSpecSource.mSystemPreferredMinimumSizePercent));
        printWriter.println("      mOptimizedAspectRatio=" + phoneSizeSpecSource.mOptimizedAspectRatio);
    }

    public final Rect getBounds() {
        return new Rect(this.mBounds);
    }

    public final Rect getStashInsets() {
        PipEdgePanelSupport pipEdgePanelSupport = new PipEdgePanelSupport(this.mContext);
        PipDisplayLayoutState pipDisplayLayoutState = this.mPipDisplayLayoutState;
        Rect rect = pipDisplayLayoutState.getDisplayLayout().mStableInsets;
        DisplayLayout displayLayout = pipDisplayLayoutState.getDisplayLayout();
        int iNavigationBarPosition = DisplayLayout.navigationBarPosition(this.mContext.getResources(), displayLayout.mWidth, displayLayout.mHeight, displayLayout.mRotation);
        int i = Settings.System.getInt(pipEdgePanelSupport.mContext.getContentResolver(), "active_edge_area", 1);
        this.mStashInsetBounds.setEmpty();
        if (iNavigationBarPosition == 1 || i == 0) {
            this.mStashInsetBounds.left = rect.left;
        } else if (iNavigationBarPosition == 2 || i == 1) {
            this.mStashInsetBounds.right = rect.right;
        }
        return this.mStashInsetBounds;
    }

    public final Set getUnrestrictedKeepClearAreas() {
        if (this.mNamedUnrestrictedKeepClearAreas.size() == 0) {
            return this.mUnrestrictedKeepClearAreas;
        }
        ArraySet arraySet = new ArraySet(this.mUnrestrictedKeepClearAreas);
        for (int i = 0; i < this.mNamedUnrestrictedKeepClearAreas.size(); i++) {
            arraySet.add((Rect) this.mNamedUnrestrictedKeepClearAreas.get(this.mNamedUnrestrictedKeepClearAreas.keyAt(i)));
        }
        return arraySet;
    }

    public final boolean isStashed() {
        return this.mStashedState != 0;
    }

    public final void saveReentryState(float f) {
        this.mPipReentryState = new PipReentryState(this.mBoundsScale, f);
    }

    public final void setAspectRatio(float f) {
        if (Float.compare(this.mAspectRatio, f) != 0) {
            this.mAspectRatio = f;
            ArrayList arrayList = (ArrayList) this.mOnAspectRatioChangedCallbacks;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((Consumer) obj).accept(Float.valueOf(this.mAspectRatio));
            }
        }
    }

    public final void setBounds(Rect rect) {
        PipTransitionState pipTransitionState;
        if (!this.mBounds.equals(rect)) {
            int i = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
            StringBuilder sb = new StringBuilder("[PipBoundsState] setBounds: ");
            sb.append(this.mBounds);
            sb.append(" -> ");
            sb.append(rect);
            sb.append(", Callers=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(6, "PipTaskOrganizer", sb);
        }
        int i2 = 0;
        if (!rect.isEmpty()) {
            PipDisplayLayoutState pipDisplayLayoutState = this.mPipDisplayLayoutState;
            if (!pipDisplayLayoutState.getDisplayBounds().equals(rect) && pipDisplayLayoutState.getDisplayBounds().contains(rect) && isStashed() && (pipTransitionState = this.mPipTransitionState) != null && pipTransitionState.mState > 3) {
                setStashed(0, false);
            }
        }
        this.mBounds.set(rect);
        ArrayList arrayList = (ArrayList) this.mOnPipExclusionBoundsChangeCallbacks;
        int size = arrayList.size();
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((Consumer) obj).accept(rect);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setBoundsStateForEntry(ComponentName componentName, ActivityInfo activityInfo, PictureInPictureParams pictureInPictureParams, PipBoundsAlgorithm pipBoundsAlgorithm) {
        float aspectRatioFloat;
        setLastPipComponentName(componentName);
        if (pictureInPictureParams != null) {
            pipBoundsAlgorithm.getClass();
            aspectRatioFloat = pictureInPictureParams.hasSetAspectRatio() ? pictureInPictureParams.getAspectRatioFloat() : pipBoundsAlgorithm.mDefaultAspectRatio;
        }
        setAspectRatio(aspectRatioFloat);
        setOverrideMinSize(pipBoundsAlgorithm.getMinimalSize(activityInfo));
        PipController$$ExternalSyntheticLambda3 pipController$$ExternalSyntheticLambda3 = this.mOnPipTaskAppearedCallback;
        if (pipController$$ExternalSyntheticLambda3 != null) {
            pipController$$ExternalSyntheticLambda3.run();
        }
    }

    public final void setHasUserResizedPip() {
        this.mHasUserResizedPip = true;
        if (this.mIsImeShowing) {
            this.mRestoreBounds.setEmpty();
        }
    }

    public final void setLastPipComponentName(ComponentName componentName) {
        if (Objects.equals(this.mLastPipComponentName, componentName)) {
            return;
        }
        clearReentryState();
        int i = 0;
        this.mHasUserResizedPip = false;
        this.mHasUserMovedPip = false;
        this.mLastPipComponentName = componentName;
        ArrayList arrayList = (ArrayList) this.mOnPipComponentChangedListeners;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((OnPipComponentChangedListener) obj).onPipComponentChanged();
        }
    }

    public final void setNamedUnrestrictedKeepClearArea(int i, Rect rect) {
        if (rect == null) {
            this.mNamedUnrestrictedKeepClearAreas.remove(i);
            return;
        }
        this.mNamedUnrestrictedKeepClearAreas.put(i, rect);
        if (i == 0) {
            this.mCachedLauncherShelfHeightKeepClearArea.set(rect);
        }
    }

    public final void setOverrideMinSize(Size size) {
        PipController$$ExternalSyntheticLambda3 pipController$$ExternalSyntheticLambda3;
        SizeSpecSource sizeSpecSource = this.mSizeSpecSource;
        if (size != null) {
            Size defaultSize = ((PhoneSizeSpecSource) sizeSpecSource).getDefaultSize(this.mAspectRatio);
            if (size.getWidth() > defaultSize.getWidth() || size.getHeight() > defaultSize.getHeight()) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                    ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -2952349473808633476L, 0, String.valueOf(size), String.valueOf(defaultSize));
                    return;
                }
                return;
            }
        }
        PhoneSizeSpecSource phoneSizeSpecSource = (PhoneSizeSpecSource) sizeSpecSource;
        boolean zEquals = Objects.equals(size, phoneSizeSpecSource.getOverrideMinSize());
        phoneSizeSpecSource.mOverrideMinSize = size;
        if (zEquals || (pipController$$ExternalSyntheticLambda3 = this.mOnMinimalSizeChangeCallback) == null) {
            return;
        }
        pipController$$ExternalSyntheticLambda3.run();
    }

    public final void setStashed(int i, boolean z) {
        if (this.mStashedState == i) {
            return;
        }
        int i2 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
        KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(new StringBuilder("setStashed old="), this.mStashedState, " new=", i, "PipTaskOrganizer");
        PipController$$ExternalSyntheticLambda8 pipController$$ExternalSyntheticLambda8 = this.mOnPipStashCallback;
        if (pipController$$ExternalSyntheticLambda8 != null) {
            pipController$$ExternalSyntheticLambda8.accept(i);
        }
        this.mStashedState = i;
        if (z) {
            Log.d("PipTaskOrganizer", "setStashed skipWMCoreUpdate=true");
            return;
        }
        try {
            ActivityTaskManager.getService().onPictureInPictureUiStateChanged(new PictureInPictureUiState(i != 0));
        } catch (RemoteException | IllegalStateException unused) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 6744113849991380724L, 0, "PipBoundsState");
            }
            int i3 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
            Log.e("PipTaskOrganizer", "[PipBoundsState] setStashed: Activity is not in PIP mode, caller=" + Debug.getCallers(10));
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PipBoundsState{mBounds=");
        sb.append(this.mBounds);
        sb.append(", mMovementBounds=");
        sb.append(this.mMovementBounds);
        sb.append(", mNormalBounds=");
        sb.append(this.mNormalBounds);
        sb.append(", mExpandedBounds=");
        sb.append(this.mExpandedBounds);
        sb.append(", mAspectRatio=");
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.mAspectRatio, '}');
    }

    public final void updateMinMaxSize(float f) {
        SizeSpecSource sizeSpecSource = this.mSizeSpecSource;
        Size minSize = ((PhoneSizeSpecSource) sizeSpecSource).getMinSize(f);
        this.mMinSize.set(minSize.getWidth(), minSize.getHeight());
        Size maxSize = ((PhoneSizeSpecSource) sizeSpecSource).getMaxSize(f);
        this.mMaxSize.set(maxSize.getWidth(), maxSize.getHeight());
    }
}
