package android.graphics;

import android.animation.Animator;
import android.graphics.animation.RenderNodeAnimator;
import android.view.NativeVectorDrawableAnimator;
import com.android.internal.util.ArrayUtils;
import dalvik.annotation.optimization.CriticalNative;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public final class RenderNode {
    public static final int USAGE_BACKGROUND = 1;
    public static final int USAGE_UNKNOWN = 0;
    private final AnimationHost mAnimationHost;
    private CompositePositionUpdateListener mCompositePositionUpdateListener;
    private RecordingCanvas mCurrentRecordingCanvas;
    public final long mNativeRenderNode;

    public interface AnimationHost {
        boolean isAttached();

        void registerAnimatingRenderNode(RenderNode renderNode, Animator animator);

        void registerVectorDrawableAnimator(NativeVectorDrawableAnimator nativeVectorDrawableAnimator);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UsageHint {
    }

    private static native void nAddAnimator(long j, long j2);

    @CriticalNative
    private static native boolean nClearStretch(long j);

    private static native long nCreate(String str);

    @CriticalNative
    private static native void nDiscardDisplayList(long j);

    private static native void nEndAllAnimators(long j);

    private static native void nForceEndAnimators(long j);

    private static native int nGetAllocatedSize(long j);

    @CriticalNative
    private static native boolean nGetAllowForceDark(long j);

    @CriticalNative
    private static native float nGetAlpha(long j);

    @CriticalNative
    private static native int nGetAmbientShadowColor(long j);

    @CriticalNative
    private static native boolean nGetAnimationMatrix(long j, long j2);

    @CriticalNative
    private static native int nGetBottom(long j);

    @CriticalNative
    private static native float nGetCameraDistance(long j);

    @CriticalNative
    private static native boolean nGetClipToBounds(long j);

    @CriticalNative
    private static native boolean nGetClipToOutline(long j);

    @CriticalNative
    private static native float nGetElevation(long j);

    @CriticalNative
    private static native int nGetHeight(long j);

    @CriticalNative
    private static native void nGetInverseTransformMatrix(long j, long j2);

    @CriticalNative
    private static native int nGetLayerType(long j);

    @CriticalNative
    private static native int nGetLeft(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetNativeFinalizer();

    @CriticalNative
    private static native float nGetPivotX(long j);

    @CriticalNative
    private static native float nGetPivotY(long j);

    @CriticalNative
    private static native int nGetRight(long j);

    @CriticalNative
    private static native float nGetRotation(long j);

    @CriticalNative
    private static native float nGetRotationX(long j);

    @CriticalNative
    private static native float nGetRotationY(long j);

    @CriticalNative
    private static native float nGetScaleX(long j);

    @CriticalNative
    private static native float nGetScaleY(long j);

    @CriticalNative
    private static native int nGetSpotShadowColor(long j);

    @CriticalNative
    private static native int nGetTop(long j);

    @CriticalNative
    private static native void nGetTransformMatrix(long j, long j2);

    @CriticalNative
    private static native float nGetTranslationX(long j);

    @CriticalNative
    private static native float nGetTranslationY(long j);

    @CriticalNative
    private static native float nGetTranslationZ(long j);

    @CriticalNative
    private static native long nGetUniqueId(long j);

    private static native int nGetUsageSize(long j);

    @CriticalNative
    private static native int nGetWidth(long j);

    @CriticalNative
    private static native boolean nHasIdentityMatrix(long j);

    @CriticalNative
    private static native boolean nHasOverlappingRendering(long j);

    @CriticalNative
    private static native boolean nHasShadow(long j);

    @CriticalNative
    private static native boolean nIsPivotExplicitlySet(long j);

    @CriticalNative
    private static native boolean nIsValid(long j);

    @CriticalNative
    private static native boolean nOffsetLeftAndRight(long j, int i);

    @CriticalNative
    private static native boolean nOffsetTopAndBottom(long j, int i);

    private static native void nOutput(long j);

    private static native void nRequestPositionUpdates(long j, WeakReference<PositionUpdateListener> weakReference);

    @CriticalNative
    private static native boolean nResetPivot(long j);

    @CriticalNative
    private static native boolean nSetAllowForceDark(long j, boolean z);

    @CriticalNative
    private static native boolean nSetAlpha(long j, float f);

    @CriticalNative
    private static native boolean nSetAmbientShadowColor(long j, int i);

    @CriticalNative
    private static native boolean nSetAnimationMatrix(long j, long j2);

    @CriticalNative
    private static native boolean nSetBackdropRenderEffect(long j, long j2);

    @CriticalNative
    private static native boolean nSetBottom(long j, int i);

    @CriticalNative
    private static native boolean nSetCameraDistance(long j, float f);

    @CriticalNative
    private static native boolean nSetClipBounds(long j, int i, int i2, int i3, int i4);

    @CriticalNative
    private static native boolean nSetClipBoundsEmpty(long j);

    @CriticalNative
    private static native boolean nSetClipToBounds(long j, boolean z);

    @CriticalNative
    private static native boolean nSetClipToOutline(long j, boolean z);

    @CriticalNative
    private static native boolean nSetElevation(long j, float f);

    @CriticalNative
    private static native boolean nSetHasOverlappingRendering(long j, boolean z);

    @CriticalNative
    private static native void nSetIsTextureView(long j);

    @CriticalNative
    private static native boolean nSetLayerPaint(long j, long j2);

    @CriticalNative
    private static native boolean nSetLayerType(long j, int i);

    @CriticalNative
    private static native boolean nSetLeft(long j, int i);

    @CriticalNative
    private static native boolean nSetLeftTopRightBottom(long j, int i, int i2, int i3, int i4);

    @CriticalNative
    private static native boolean nSetOutlineEmpty(long j);

    @CriticalNative
    private static native boolean nSetOutlineNone(long j);

    @CriticalNative
    private static native boolean nSetOutlinePath(long j, long j2, float f);

    @CriticalNative
    private static native boolean nSetOutlineRoundRect(long j, int i, int i2, int i3, int i4, float f, float f2);

    @CriticalNative
    private static native boolean nSetPivotX(long j, float f);

    @CriticalNative
    private static native boolean nSetPivotY(long j, float f);

    @CriticalNative
    private static native boolean nSetProjectBackwards(long j, boolean z);

    @CriticalNative
    private static native boolean nSetProjectionReceiver(long j, boolean z);

    @CriticalNative
    private static native boolean nSetRenderEffect(long j, long j2);

    @CriticalNative
    private static native boolean nSetRevealClip(long j, boolean z, float f, float f2, float f3);

    @CriticalNative
    private static native boolean nSetRight(long j, int i);

    @CriticalNative
    private static native boolean nSetRotation(long j, float f);

    @CriticalNative
    private static native boolean nSetRotationX(long j, float f);

    @CriticalNative
    private static native boolean nSetRotationY(long j, float f);

    @CriticalNative
    private static native boolean nSetScaleX(long j, float f);

    @CriticalNative
    private static native boolean nSetScaleY(long j, float f);

    @CriticalNative
    private static native boolean nSetSpotShadowColor(long j, int i);

    @CriticalNative
    private static native boolean nSetStaticMatrix(long j, long j2);

    @CriticalNative
    private static native boolean nSetTop(long j, int i);

    @CriticalNative
    private static native boolean nSetTranslationX(long j, float f);

    @CriticalNative
    private static native boolean nSetTranslationY(long j, float f);

    @CriticalNative
    private static native boolean nSetTranslationZ(long j, float f);

    @CriticalNative
    private static native void nSetUsageHint(long j, int i);

    @CriticalNative
    private static native boolean nStretch(long j, float f, float f2, float f3, float f4);

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(RenderNode.class.getClassLoader(), RenderNode.nGetNativeFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    public RenderNode(String str) {
        this(str, null);
    }

    private RenderNode(String str, AnimationHost animationHost) {
        long jNCreate = nCreate(str);
        this.mNativeRenderNode = jNCreate;
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, jNCreate);
        this.mAnimationHost = animationHost;
    }

    private RenderNode(long j) {
        this.mNativeRenderNode = j;
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, j);
        this.mAnimationHost = null;
    }

    public static RenderNode create(String str, AnimationHost animationHost) {
        return new RenderNode(str, animationHost);
    }

    public static RenderNode adopt(long j) {
        return new RenderNode(j);
    }

    public interface PositionUpdateListener {
        default void applyStretch(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        }

        void positionChanged(long j, int i, int i2, int i3, int i4);

        void positionLost(long j);

        default void positionChanged(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
            positionChanged(j, i, i2, i3, i4);
        }

        static boolean callPositionChanged(WeakReference<PositionUpdateListener> weakReference, long j, int i, int i2, int i3, int i4) {
            PositionUpdateListener positionUpdateListener = weakReference.get();
            if (positionUpdateListener == null) {
                return false;
            }
            positionUpdateListener.positionChanged(j, i, i2, i3, i4);
            return true;
        }

        static boolean callPositionChanged2(WeakReference<PositionUpdateListener> weakReference, long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
            PositionUpdateListener positionUpdateListener = weakReference.get();
            if (positionUpdateListener == null) {
                return false;
            }
            positionUpdateListener.positionChanged(j, i, i2, i3, i4, i5, i6, i7, i8, i9, i10);
            return true;
        }

        static boolean callApplyStretch(WeakReference<PositionUpdateListener> weakReference, long j, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            PositionUpdateListener positionUpdateListener = weakReference.get();
            if (positionUpdateListener == null) {
                return false;
            }
            positionUpdateListener.applyStretch(j, f, f2, f3, f4, f5, f6, f7, f8, f9, f10);
            return true;
        }

        static boolean callPositionLost(WeakReference<PositionUpdateListener> weakReference, long j) {
            PositionUpdateListener positionUpdateListener = weakReference.get();
            if (positionUpdateListener == null) {
                return false;
            }
            positionUpdateListener.positionLost(j);
            return true;
        }
    }

    private static final class CompositePositionUpdateListener implements PositionUpdateListener {
        private static final PositionUpdateListener[] sEmpty = new PositionUpdateListener[0];
        private final PositionUpdateListener[] mListeners;

        CompositePositionUpdateListener(PositionUpdateListener... positionUpdateListenerArr) {
            this.mListeners = positionUpdateListenerArr == null ? sEmpty : positionUpdateListenerArr;
        }

        public CompositePositionUpdateListener with(PositionUpdateListener positionUpdateListener) {
            return new CompositePositionUpdateListener((PositionUpdateListener[]) ArrayUtils.appendElement(PositionUpdateListener.class, this.mListeners, positionUpdateListener));
        }

        public CompositePositionUpdateListener without(PositionUpdateListener positionUpdateListener) {
            return new CompositePositionUpdateListener((PositionUpdateListener[]) ArrayUtils.removeElement(PositionUpdateListener.class, this.mListeners, positionUpdateListener));
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionChanged(long j, int i, int i2, int i3, int i4) {
            for (PositionUpdateListener positionUpdateListener : this.mListeners) {
                positionUpdateListener.positionChanged(j, i, i2, i3, i4);
            }
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionChanged(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
            for (PositionUpdateListener positionUpdateListener : this.mListeners) {
                positionUpdateListener.positionChanged(j, i, i2, i3, i4, i5, i6, i7, i8, i9, i10);
            }
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionLost(long j) {
            for (PositionUpdateListener positionUpdateListener : this.mListeners) {
                positionUpdateListener.positionLost(j);
            }
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void applyStretch(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            for (PositionUpdateListener positionUpdateListener : this.mListeners) {
                positionUpdateListener.applyStretch(j, f, f2, f3, f4, f5, f6, f7, f8, f9, f10);
            }
        }
    }

    public void addPositionUpdateListener(PositionUpdateListener positionUpdateListener) {
        CompositePositionUpdateListener compositePositionUpdateListenerWith;
        CompositePositionUpdateListener compositePositionUpdateListener = this.mCompositePositionUpdateListener;
        if (compositePositionUpdateListener == null) {
            compositePositionUpdateListenerWith = new CompositePositionUpdateListener(positionUpdateListener);
        } else {
            compositePositionUpdateListenerWith = compositePositionUpdateListener.with(positionUpdateListener);
        }
        this.mCompositePositionUpdateListener = compositePositionUpdateListenerWith;
        nRequestPositionUpdates(this.mNativeRenderNode, new WeakReference(compositePositionUpdateListenerWith));
    }

    public void removePositionUpdateListener(PositionUpdateListener positionUpdateListener) {
        CompositePositionUpdateListener compositePositionUpdateListener = this.mCompositePositionUpdateListener;
        if (compositePositionUpdateListener != null) {
            CompositePositionUpdateListener compositePositionUpdateListenerWithout = compositePositionUpdateListener.without(positionUpdateListener);
            this.mCompositePositionUpdateListener = compositePositionUpdateListenerWithout;
            nRequestPositionUpdates(this.mNativeRenderNode, new WeakReference(compositePositionUpdateListenerWithout));
        }
    }

    public RecordingCanvas beginRecording(int i, int i2) {
        if (this.mCurrentRecordingCanvas != null) {
            throw new IllegalStateException("Recording currently in progress - missing #endRecording() call?");
        }
        RecordingCanvas recordingCanvasObtain = RecordingCanvas.obtain(this, i, i2);
        this.mCurrentRecordingCanvas = recordingCanvasObtain;
        return recordingCanvasObtain;
    }

    public RecordingCanvas beginRecording() {
        return beginRecording(nGetWidth(this.mNativeRenderNode), nGetHeight(this.mNativeRenderNode));
    }

    public void endRecording() {
        RecordingCanvas recordingCanvas = this.mCurrentRecordingCanvas;
        if (recordingCanvas == null) {
            throw new IllegalStateException("No recording in progress, forgot to call #beginRecording()?");
        }
        this.mCurrentRecordingCanvas = null;
        recordingCanvas.finishRecording(this);
        recordingCanvas.recycle();
    }

    @Deprecated
    public RecordingCanvas start(int i, int i2) {
        return beginRecording(i, i2);
    }

    @Deprecated
    public void end(RecordingCanvas recordingCanvas) {
        if (recordingCanvas != this.mCurrentRecordingCanvas) {
            throw new IllegalArgumentException("Wrong canvas");
        }
        endRecording();
    }

    public void discardDisplayList() {
        nDiscardDisplayList(this.mNativeRenderNode);
    }

    public boolean hasDisplayList() {
        return nIsValid(this.mNativeRenderNode);
    }

    public boolean hasIdentityMatrix() {
        return nHasIdentityMatrix(this.mNativeRenderNode);
    }

    public void getMatrix(Matrix matrix) {
        nGetTransformMatrix(this.mNativeRenderNode, matrix.ni());
    }

    public void getInverseMatrix(Matrix matrix) {
        nGetInverseTransformMatrix(this.mNativeRenderNode, matrix.ni());
    }

    @Deprecated
    public boolean setLayerType(int i) {
        return nSetLayerType(this.mNativeRenderNode, i);
    }

    @Deprecated
    public boolean setLayerPaint(Paint paint) {
        return nSetLayerPaint(this.mNativeRenderNode, paint != null ? paint.getNativeInstance() : 0L);
    }

    public boolean setUseCompositingLayer(boolean z, Paint paint) {
        return nSetLayerPaint(this.mNativeRenderNode, paint != null ? paint.getNativeInstance() : 0L) | nSetLayerType(this.mNativeRenderNode, z ? 2 : 0);
    }

    public boolean getUseCompositingLayer() {
        return nGetLayerType(this.mNativeRenderNode) != 0;
    }

    public boolean setClipRect(Rect rect) {
        if (rect == null) {
            return nSetClipBoundsEmpty(this.mNativeRenderNode);
        }
        return nSetClipBounds(this.mNativeRenderNode, rect.left, rect.top, rect.right, rect.bottom);
    }

    public boolean setClipToBounds(boolean z) {
        return nSetClipToBounds(this.mNativeRenderNode, z);
    }

    public boolean getClipToBounds() {
        return nGetClipToBounds(this.mNativeRenderNode);
    }

    public boolean setProjectBackwards(boolean z) {
        return nSetProjectBackwards(this.mNativeRenderNode, z);
    }

    public boolean setProjectionReceiver(boolean z) {
        return nSetProjectionReceiver(this.mNativeRenderNode, z);
    }

    public boolean setOutline(Outline outline) {
        if (outline == null) {
            return nSetOutlineNone(this.mNativeRenderNode);
        }
        int i = outline.mMode;
        if (i == 0) {
            return nSetOutlineEmpty(this.mNativeRenderNode);
        }
        if (i == 1) {
            return nSetOutlineRoundRect(this.mNativeRenderNode, outline.mRect.left, outline.mRect.top, outline.mRect.right, outline.mRect.bottom, outline.mRadius, outline.mAlpha);
        }
        if (i == 2) {
            return nSetOutlinePath(this.mNativeRenderNode, outline.mPath.mNativePath, outline.mAlpha);
        }
        throw new IllegalArgumentException("Unrecognized outline?");
    }

    public boolean clearStretch() {
        return nClearStretch(this.mNativeRenderNode);
    }

    public boolean stretch(float f, float f2, float f3, float f4) {
        if (Float.isInfinite(f) || Float.isNaN(f)) {
            throw new IllegalArgumentException("vecX must be a finite, non-NaN value " + f);
        }
        if (Float.isInfinite(f2) || Float.isNaN(f2)) {
            throw new IllegalArgumentException("vecY must be a finite, non-NaN value " + f2);
        }
        if (f3 <= 0.0f) {
            throw new IllegalArgumentException("The max horizontal stretch amount must be >0, got " + f3);
        }
        if (f4 <= 0.0f) {
            throw new IllegalArgumentException("The max vertical stretch amount must be >0, got " + f4);
        }
        return nStretch(this.mNativeRenderNode, f, f2, f3, f4);
    }

    public boolean hasShadow() {
        return nHasShadow(this.mNativeRenderNode);
    }

    public boolean setSpotShadowColor(int i) {
        return nSetSpotShadowColor(this.mNativeRenderNode, i);
    }

    public int getSpotShadowColor() {
        return nGetSpotShadowColor(this.mNativeRenderNode);
    }

    public boolean setAmbientShadowColor(int i) {
        return nSetAmbientShadowColor(this.mNativeRenderNode, i);
    }

    public int getAmbientShadowColor() {
        return nGetAmbientShadowColor(this.mNativeRenderNode);
    }

    public boolean setClipToOutline(boolean z) {
        return nSetClipToOutline(this.mNativeRenderNode, z);
    }

    public boolean getClipToOutline() {
        return nGetClipToOutline(this.mNativeRenderNode);
    }

    public boolean setRevealClip(boolean z, float f, float f2, float f3) {
        return nSetRevealClip(this.mNativeRenderNode, z, f, f2, f3);
    }

    public boolean setStaticMatrix(Matrix matrix) {
        return nSetStaticMatrix(this.mNativeRenderNode, matrix.ni());
    }

    public boolean setAnimationMatrix(Matrix matrix) {
        return nSetAnimationMatrix(this.mNativeRenderNode, matrix != null ? matrix.ni() : 0L);
    }

    public Matrix getAnimationMatrix() {
        Matrix matrix = new Matrix();
        if (nGetAnimationMatrix(this.mNativeRenderNode, matrix.ni())) {
            return matrix;
        }
        return null;
    }

    public boolean setAlpha(float f) {
        return nSetAlpha(this.mNativeRenderNode, f);
    }

    public boolean setRenderEffect(RenderEffect renderEffect) {
        return nSetRenderEffect(this.mNativeRenderNode, renderEffect != null ? renderEffect.getNativeInstance() : 0L);
    }

    public boolean setBackdropRenderEffect(RenderEffect renderEffect) {
        return nSetBackdropRenderEffect(this.mNativeRenderNode, renderEffect != null ? renderEffect.getNativeInstance() : 0L);
    }

    public float getAlpha() {
        return nGetAlpha(this.mNativeRenderNode);
    }

    public boolean setHasOverlappingRendering(boolean z) {
        return nSetHasOverlappingRendering(this.mNativeRenderNode, z);
    }

    public void setUsageHint(int i) {
        nSetUsageHint(this.mNativeRenderNode, i);
    }

    public boolean hasOverlappingRendering() {
        return nHasOverlappingRendering(this.mNativeRenderNode);
    }

    public boolean setElevation(float f) {
        return nSetElevation(this.mNativeRenderNode, f);
    }

    public float getElevation() {
        return nGetElevation(this.mNativeRenderNode);
    }

    public boolean setTranslationX(float f) {
        return nSetTranslationX(this.mNativeRenderNode, f);
    }

    public float getTranslationX() {
        return nGetTranslationX(this.mNativeRenderNode);
    }

    public boolean setTranslationY(float f) {
        return nSetTranslationY(this.mNativeRenderNode, f);
    }

    public float getTranslationY() {
        return nGetTranslationY(this.mNativeRenderNode);
    }

    public boolean setTranslationZ(float f) {
        return nSetTranslationZ(this.mNativeRenderNode, f);
    }

    public float getTranslationZ() {
        return nGetTranslationZ(this.mNativeRenderNode);
    }

    public boolean setRotationZ(float f) {
        return nSetRotation(this.mNativeRenderNode, f);
    }

    public float getRotationZ() {
        return nGetRotation(this.mNativeRenderNode);
    }

    public boolean setRotationX(float f) {
        return nSetRotationX(this.mNativeRenderNode, f);
    }

    public float getRotationX() {
        return nGetRotationX(this.mNativeRenderNode);
    }

    public boolean setRotationY(float f) {
        return nSetRotationY(this.mNativeRenderNode, f);
    }

    public float getRotationY() {
        return nGetRotationY(this.mNativeRenderNode);
    }

    public boolean setScaleX(float f) {
        return nSetScaleX(this.mNativeRenderNode, f);
    }

    public float getScaleX() {
        return nGetScaleX(this.mNativeRenderNode);
    }

    public boolean setScaleY(float f) {
        return nSetScaleY(this.mNativeRenderNode, f);
    }

    public float getScaleY() {
        return nGetScaleY(this.mNativeRenderNode);
    }

    public boolean setPivotX(float f) {
        return nSetPivotX(this.mNativeRenderNode, f);
    }

    public float getPivotX() {
        return nGetPivotX(this.mNativeRenderNode);
    }

    public boolean setPivotY(float f) {
        return nSetPivotY(this.mNativeRenderNode, f);
    }

    public float getPivotY() {
        return nGetPivotY(this.mNativeRenderNode);
    }

    public boolean isPivotExplicitlySet() {
        return nIsPivotExplicitlySet(this.mNativeRenderNode);
    }

    public boolean resetPivot() {
        return nResetPivot(this.mNativeRenderNode);
    }

    public boolean setCameraDistance(float f) {
        if (!Float.isFinite(f) || f < 0.0f) {
            throw new IllegalArgumentException("distance must be finite & positive, given=" + f);
        }
        return nSetCameraDistance(this.mNativeRenderNode, -f);
    }

    public float getCameraDistance() {
        return -nGetCameraDistance(this.mNativeRenderNode);
    }

    public boolean setLeft(int i) {
        return nSetLeft(this.mNativeRenderNode, i);
    }

    public boolean setTop(int i) {
        return nSetTop(this.mNativeRenderNode, i);
    }

    public boolean setRight(int i) {
        return nSetRight(this.mNativeRenderNode, i);
    }

    public boolean setBottom(int i) {
        return nSetBottom(this.mNativeRenderNode, i);
    }

    public int getLeft() {
        return nGetLeft(this.mNativeRenderNode);
    }

    public int getTop() {
        return nGetTop(this.mNativeRenderNode);
    }

    public int getRight() {
        return nGetRight(this.mNativeRenderNode);
    }

    public int getBottom() {
        return nGetBottom(this.mNativeRenderNode);
    }

    public int getWidth() {
        return nGetWidth(this.mNativeRenderNode);
    }

    public int getHeight() {
        return nGetHeight(this.mNativeRenderNode);
    }

    public boolean setLeftTopRightBottom(int i, int i2, int i3, int i4) {
        return nSetLeftTopRightBottom(this.mNativeRenderNode, i, i2, i3, i4);
    }

    public boolean setPosition(int i, int i2, int i3, int i4) {
        return nSetLeftTopRightBottom(this.mNativeRenderNode, i, i2, i3, i4);
    }

    public boolean setPosition(Rect rect) {
        return nSetLeftTopRightBottom(this.mNativeRenderNode, rect.left, rect.top, rect.right, rect.bottom);
    }

    public boolean offsetLeftAndRight(int i) {
        return nOffsetLeftAndRight(this.mNativeRenderNode, i);
    }

    public boolean offsetTopAndBottom(int i) {
        return nOffsetTopAndBottom(this.mNativeRenderNode, i);
    }

    public void output() {
        nOutput(this.mNativeRenderNode);
    }

    public long computeApproximateMemoryUsage() {
        return nGetUsageSize(this.mNativeRenderNode);
    }

    public long computeApproximateMemoryAllocated() {
        return nGetAllocatedSize(this.mNativeRenderNode);
    }

    public boolean setForceDarkAllowed(boolean z) {
        return nSetAllowForceDark(this.mNativeRenderNode, z);
    }

    public boolean isForceDarkAllowed() {
        return nGetAllowForceDark(this.mNativeRenderNode);
    }

    public long getUniqueId() {
        return nGetUniqueId(this.mNativeRenderNode);
    }

    public void setIsTextureView() {
        nSetIsTextureView(this.mNativeRenderNode);
    }

    public void addAnimator(RenderNodeAnimator renderNodeAnimator) {
        if (!isAttached()) {
            throw new IllegalStateException("Cannot start this animator on a detached view!");
        }
        nAddAnimator(this.mNativeRenderNode, renderNodeAnimator.getNativeAnimator());
        this.mAnimationHost.registerAnimatingRenderNode(this, renderNodeAnimator);
    }

    public boolean isAttached() {
        AnimationHost animationHost = this.mAnimationHost;
        return animationHost != null && animationHost.isAttached();
    }

    public void registerVectorDrawableAnimator(NativeVectorDrawableAnimator nativeVectorDrawableAnimator) {
        if (!isAttached()) {
            throw new IllegalStateException("Cannot start this animator on a detached view!");
        }
        this.mAnimationHost.registerVectorDrawableAnimator(nativeVectorDrawableAnimator);
    }

    public void endAllAnimators() {
        nEndAllAnimators(this.mNativeRenderNode);
    }

    public void forceEndAnimators() {
        nForceEndAnimators(this.mNativeRenderNode);
    }
}
