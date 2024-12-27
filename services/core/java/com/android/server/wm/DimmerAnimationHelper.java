package com.android.server.wm;

import android.os.Debug;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DimmerAnimationHelper {
    public AnimationSpec mAlphaAnimationSpec;
    public final AnimationAdapterFactory mAnimationAdapterFactory;
    public LocalAnimationAdapter mLocalAnimationAdapter;
    public final Change mCurrentProperties = new Change();
    public final Change mRequestedProperties = new Change();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AnimationAdapterFactory {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AnimationSpec implements LocalAnimationAdapter.AnimationSpec {
        public final AnimationExtremes mAlpha;
        public final AnimationExtremes mBlur;
        public final long mDuration;
        public float mCurrentAlpha = FullScreenMagnificationGestureHandler.MAX_SCALE;
        public int mCurrentBlur = 0;
        public boolean mStarted = false;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class AnimationExtremes {
            public final Object mFinishValue;
            public final Object mStartValue;

            public AnimationExtremes(Object obj, Object obj2) {
                this.mStartValue = obj;
                this.mFinishValue = obj2;
            }

            public final String toString() {
                return "[" + this.mStartValue + "->" + this.mFinishValue + "]";
            }
        }

        public AnimationSpec(
                AnimationExtremes animationExtremes, AnimationExtremes animationExtremes2, long j) {
            this.mAlpha = animationExtremes;
            this.mBlur = animationExtremes2;
            this.mDuration = j;
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public final void apply(
                SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, long j) {
            if (!this.mStarted) {
                this.mStarted = true;
                return;
            }
            float fraction = getFraction(j);
            AnimationExtremes animationExtremes = this.mAlpha;
            float floatValue = ((Float) animationExtremes.mFinishValue).floatValue();
            Object obj = animationExtremes.mStartValue;
            this.mCurrentAlpha =
                    ((Float) obj).floatValue()
                            + ((floatValue - ((Float) obj).floatValue()) * fraction);
            AnimationExtremes animationExtremes2 = this.mBlur;
            int intValue = ((Integer) animationExtremes2.mFinishValue).intValue();
            Object obj2 = animationExtremes2.mStartValue;
            this.mCurrentBlur =
                    ((Integer) obj2).intValue()
                            + ((intValue - ((Integer) obj2).intValue()) * ((int) fraction));
            if (!surfaceControl.isValid()) {
                Log.w(
                        "WindowManager",
                        "Dimmer#AnimationSpec tried to access "
                                + surfaceControl
                                + " after release");
                return;
            }
            try {
                transaction.setAlpha(surfaceControl, this.mCurrentAlpha);
                transaction.setBackgroundBlurRadius(surfaceControl, this.mCurrentBlur);
            } catch (IllegalStateException | NullPointerException e) {
                Log.e(
                        "WindowManager",
                        "Dimmer#AnimationSpec tried to access " + surfaceControl + " after release",
                        e);
            }
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public final void dump(PrintWriter printWriter, String str) {
            printWriter.print(str);
            printWriter.print("from_alpha=");
            AnimationExtremes animationExtremes = this.mAlpha;
            printWriter.print(animationExtremes.mStartValue);
            printWriter.print(" to_alpha=");
            printWriter.print(animationExtremes.mFinishValue);
            printWriter.print(str);
            printWriter.print("from_blur=");
            AnimationExtremes animationExtremes2 = this.mBlur;
            printWriter.print(animationExtremes2.mStartValue);
            printWriter.print(" to_blur=");
            printWriter.print(animationExtremes2.mFinishValue);
            printWriter.print(" duration=");
            printWriter.println(this.mDuration);
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public final void dumpDebugInner(ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(1146756268035L);
            AnimationExtremes animationExtremes = this.mAlpha;
            protoOutputStream.write(
                    1108101562369L, ((Float) animationExtremes.mStartValue).floatValue());
            protoOutputStream.write(
                    1108101562370L, ((Float) animationExtremes.mFinishValue).floatValue());
            protoOutputStream.write(1112396529667L, this.mDuration);
            protoOutputStream.end(start);
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public final long getDuration() {
            return this.mDuration;
        }

        public final String toString() {
            return "Animation spec: alpha=" + this.mAlpha + ", blur=" + this.mBlur;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Change {
        public float mAlpha = -1.0f;
        public int mBlurRadius = -1;
        public WindowState mDimmingContainer = null;
        public WindowContainer mGeometryParent = null;

        public final String toString() {
            return "Dim state: alpha="
                    + this.mAlpha
                    + ", blur="
                    + this.mBlurRadius
                    + ", container="
                    + this.mDimmingContainer
                    + ", geometryParent "
                    + this.mGeometryParent;
        }
    }

    public DimmerAnimationHelper(AnimationAdapterFactory animationAdapterFactory) {
        this.mAnimationAdapterFactory = animationAdapterFactory;
    }

    public final void setCurrentAlphaBlur(
            SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        Change change = this.mCurrentProperties;
        try {
            if (surfaceControl.isValid()) {
                transaction.setAlpha(surfaceControl, change.mAlpha);
                transaction.setBackgroundBlurRadius(surfaceControl, change.mBlurRadius);
                return;
            }
            Log.w(
                    "WindowManager",
                    "Tried to change look of dim "
                            + surfaceControl
                            + " after remove, caller="
                            + Debug.getCallers(3));
        } catch (NullPointerException e) {
            Log.w(
                    "WindowManager",
                    "Tried to change look of dim " + surfaceControl + " after remove",
                    e);
        }
    }

    public final void stopCurrentAnimation(SurfaceControl surfaceControl) {
        AnimationSpec animationSpec;
        if (this.mLocalAnimationAdapter == null
                || (animationSpec = this.mAlphaAnimationSpec) == null) {
            return;
        }
        Change change = this.mCurrentProperties;
        change.getClass();
        change.mAlpha = animationSpec.mCurrentAlpha;
        change.mBlurRadius = animationSpec.mCurrentBlur;
        this.mLocalAnimationAdapter.onAnimationCancelled(surfaceControl);
        this.mLocalAnimationAdapter = null;
        this.mAlphaAnimationSpec = null;
    }
}
