package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeConverter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ChangeTransform extends Transition {
    public final boolean mReparent;
    public final Matrix mTempMatrix;
    public final boolean mUseOverlay;
    public static final String[] sTransitionProperties = {"android:changeTransform:matrix", "android:changeTransform:transforms", "android:changeTransform:parentMatrix"};
    public static final C05301 NON_TRANSLATIONS_PROPERTY = new Property(float[].class, "nonTranslations") { // from class: androidx.transition.ChangeTransform.1
        @Override // android.util.Property
        public final /* bridge */ /* synthetic */ Object get(Object obj) {
            return null;
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            PathAnimatorMatrix pathAnimatorMatrix = (PathAnimatorMatrix) obj;
            float[] fArr = (float[]) obj2;
            pathAnimatorMatrix.getClass();
            System.arraycopy(fArr, 0, pathAnimatorMatrix.mValues, 0, fArr.length);
            pathAnimatorMatrix.setAnimationMatrix();
        }
    };
    public static final C05312 TRANSLATIONS_PROPERTY = new Property(PointF.class, "translations") { // from class: androidx.transition.ChangeTransform.2
        @Override // android.util.Property
        public final /* bridge */ /* synthetic */ Object get(Object obj) {
            return null;
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            PathAnimatorMatrix pathAnimatorMatrix = (PathAnimatorMatrix) obj;
            PointF pointF = (PointF) obj2;
            pathAnimatorMatrix.getClass();
            pathAnimatorMatrix.mTranslationX = pointF.x;
            pathAnimatorMatrix.mTranslationY = pointF.y;
            pathAnimatorMatrix.setAnimationMatrix();
        }
    };
    public static final boolean SUPPORTS_VIEW_REMOVAL_SUPPRESSION = true;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GhostListener extends TransitionListenerAdapter {
        public final GhostView mGhostView;
        public final View mView;

        public GhostListener(View view, GhostView ghostView) {
            this.mView = view;
            this.mGhostView = ghostView;
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public final void onTransitionEnd(Transition transition) {
            transition.removeListener(this);
            int i = GhostViewPort.$r8$clinit;
            View view = this.mView;
            GhostViewPort ghostViewPort = (GhostViewPort) view.getTag(R.id.ghost_view);
            if (ghostViewPort != null) {
                int i2 = ghostViewPort.mReferences - 1;
                ghostViewPort.mReferences = i2;
                if (i2 <= 0) {
                    ((GhostViewHolder) ghostViewPort.getParent()).removeView(ghostViewPort);
                }
            }
            view.setTag(R.id.transition_transform, null);
            view.setTag(R.id.parent_matrix, null);
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public final void onTransitionPause() {
            this.mGhostView.setVisibility(4);
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public final void onTransitionResume() {
            this.mGhostView.setVisibility(0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PathAnimatorMatrix {
        public final Matrix mMatrix = new Matrix();
        public float mTranslationX;
        public float mTranslationY;
        public final float[] mValues;
        public final View mView;

        public PathAnimatorMatrix(View view, float[] fArr) {
            this.mView = view;
            float[] fArr2 = (float[]) fArr.clone();
            this.mValues = fArr2;
            this.mTranslationX = fArr2[2];
            this.mTranslationY = fArr2[5];
            setAnimationMatrix();
        }

        public final void setAnimationMatrix() {
            float f = this.mTranslationX;
            float[] fArr = this.mValues;
            fArr[2] = f;
            fArr[5] = this.mTranslationY;
            Matrix matrix = this.mMatrix;
            matrix.setValues(fArr);
            ViewUtils.IMPL.getClass();
            this.mView.setAnimationMatrix(matrix);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Transforms {
        public final float mRotationX;
        public final float mRotationY;
        public final float mRotationZ;
        public final float mScaleX;
        public final float mScaleY;
        public final float mTranslationX;
        public final float mTranslationY;
        public final float mTranslationZ;

        public Transforms(View view) {
            this.mTranslationX = view.getTranslationX();
            this.mTranslationY = view.getTranslationY();
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            this.mTranslationZ = ViewCompat.Api21Impl.getTranslationZ(view);
            this.mScaleX = view.getScaleX();
            this.mScaleY = view.getScaleY();
            this.mRotationX = view.getRotationX();
            this.mRotationY = view.getRotationY();
            this.mRotationZ = view.getRotation();
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof Transforms)) {
                return false;
            }
            Transforms transforms = (Transforms) obj;
            return transforms.mTranslationX == this.mTranslationX && transforms.mTranslationY == this.mTranslationY && transforms.mTranslationZ == this.mTranslationZ && transforms.mScaleX == this.mScaleX && transforms.mScaleY == this.mScaleY && transforms.mRotationX == this.mRotationX && transforms.mRotationY == this.mRotationY && transforms.mRotationZ == this.mRotationZ;
        }

        public final int hashCode() {
            float f = this.mTranslationX;
            int floatToIntBits = (f != 0.0f ? Float.floatToIntBits(f) : 0) * 31;
            float f2 = this.mTranslationY;
            int floatToIntBits2 = (floatToIntBits + (f2 != 0.0f ? Float.floatToIntBits(f2) : 0)) * 31;
            float f3 = this.mTranslationZ;
            int floatToIntBits3 = (floatToIntBits2 + (f3 != 0.0f ? Float.floatToIntBits(f3) : 0)) * 31;
            float f4 = this.mScaleX;
            int floatToIntBits4 = (floatToIntBits3 + (f4 != 0.0f ? Float.floatToIntBits(f4) : 0)) * 31;
            float f5 = this.mScaleY;
            int floatToIntBits5 = (floatToIntBits4 + (f5 != 0.0f ? Float.floatToIntBits(f5) : 0)) * 31;
            float f6 = this.mRotationX;
            int floatToIntBits6 = (floatToIntBits5 + (f6 != 0.0f ? Float.floatToIntBits(f6) : 0)) * 31;
            float f7 = this.mRotationY;
            int floatToIntBits7 = (floatToIntBits6 + (f7 != 0.0f ? Float.floatToIntBits(f7) : 0)) * 31;
            float f8 = this.mRotationZ;
            return floatToIntBits7 + (f8 != 0.0f ? Float.floatToIntBits(f8) : 0);
        }
    }

    public ChangeTransform() {
        this.mUseOverlay = true;
        this.mReparent = true;
        this.mTempMatrix = new Matrix();
    }

    @Override // androidx.transition.Transition
    public final void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // androidx.transition.Transition
    public final void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
        if (SUPPORTS_VIEW_REMOVAL_SUPPRESSION) {
            return;
        }
        View view = transitionValues.view;
        ((ViewGroup) view.getParent()).startViewTransition(view);
    }

    public final void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view.getVisibility() == 8) {
            return;
        }
        HashMap hashMap = (HashMap) transitionValues.values;
        hashMap.put("android:changeTransform:parent", view.getParent());
        hashMap.put("android:changeTransform:transforms", new Transforms(view));
        Matrix matrix = view.getMatrix();
        hashMap.put("android:changeTransform:matrix", (matrix == null || matrix.isIdentity()) ? null : new Matrix(matrix));
        if (this.mReparent) {
            Matrix matrix2 = new Matrix();
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            ViewUtils.IMPL.getClass();
            viewGroup.transformMatrixToGlobal(matrix2);
            matrix2.preTranslate(-viewGroup.getScrollX(), -viewGroup.getScrollY());
            hashMap.put("android:changeTransform:parentMatrix", matrix2);
            hashMap.put("android:changeTransform:intermediateMatrix", view.getTag(R.id.transition_transform));
            hashMap.put("android:changeTransform:intermediateParentMatrix", view.getTag(R.id.parent_matrix));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0052, code lost:
    
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x029c, code lost:
    
        r2 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0299, code lost:
    
        if (r3.size() == r14) goto L102;
     */
    /* JADX WARN: Removed duplicated region for block: B:128:0x030f  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x02a1  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x02a7  */
    @Override // androidx.transition.Transition
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        boolean z;
        Matrix matrix;
        Matrix matrix2;
        Matrix matrix3;
        Matrix matrix4;
        final Matrix matrix5;
        final View view;
        ViewGroup viewGroup2;
        Object obj;
        HashMap hashMap;
        int i;
        ObjectAnimator objectAnimator;
        GhostViewPort ghostViewPort;
        int i2;
        ArrayList arrayList;
        int i3;
        boolean z2;
        GhostViewHolder ghostViewHolder;
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        HashMap hashMap2 = (HashMap) transitionValues.values;
        if (!hashMap2.containsKey("android:changeTransform:parent")) {
            return null;
        }
        HashMap hashMap3 = (HashMap) transitionValues2.values;
        if (!hashMap3.containsKey("android:changeTransform:parent")) {
            return null;
        }
        ViewGroup viewGroup3 = (ViewGroup) hashMap2.get("android:changeTransform:parent");
        ViewGroup viewGroup4 = (ViewGroup) hashMap3.get("android:changeTransform:parent");
        if (this.mReparent) {
            if (isValidTarget(viewGroup3)) {
            }
            boolean z3 = false;
            if (!z3) {
                z = true;
                matrix = (Matrix) hashMap2.get("android:changeTransform:intermediateMatrix");
                if (matrix != null) {
                    hashMap2.put("android:changeTransform:matrix", matrix);
                }
                matrix2 = (Matrix) hashMap2.get("android:changeTransform:intermediateParentMatrix");
                if (matrix2 != null) {
                    hashMap2.put("android:changeTransform:parentMatrix", matrix2);
                }
                View view2 = transitionValues2.view;
                if (z) {
                    Matrix matrix6 = (Matrix) hashMap3.get("android:changeTransform:parentMatrix");
                    view2.setTag(R.id.parent_matrix, matrix6);
                    Matrix matrix7 = this.mTempMatrix;
                    matrix7.reset();
                    matrix6.invert(matrix7);
                    Matrix matrix8 = (Matrix) hashMap2.get("android:changeTransform:matrix");
                    if (matrix8 == null) {
                        matrix8 = new Matrix();
                        hashMap2.put("android:changeTransform:matrix", matrix8);
                    }
                    matrix8.postConcat((Matrix) hashMap2.get("android:changeTransform:parentMatrix"));
                    matrix8.postConcat(matrix7);
                }
                matrix3 = (Matrix) hashMap2.get("android:changeTransform:matrix");
                matrix4 = (Matrix) hashMap3.get("android:changeTransform:matrix");
                if (matrix3 == null) {
                    matrix3 = MatrixUtils.IDENTITY_MATRIX;
                }
                if (matrix4 == null) {
                    matrix4 = MatrixUtils.IDENTITY_MATRIX;
                }
                matrix5 = matrix4;
                if (matrix3.equals(matrix5)) {
                    final Transforms transforms = (Transforms) hashMap3.get("android:changeTransform:transforms");
                    view2.setTranslationX(0.0f);
                    view2.setTranslationY(0.0f);
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api21Impl.setTranslationZ(view2, 0.0f);
                    view2.setScaleX(1.0f);
                    view2.setScaleY(1.0f);
                    view2.setRotationX(0.0f);
                    view2.setRotationY(0.0f);
                    view2.setRotation(0.0f);
                    float[] fArr = new float[9];
                    matrix3.getValues(fArr);
                    float[] fArr2 = new float[9];
                    matrix5.getValues(fArr2);
                    final PathAnimatorMatrix pathAnimatorMatrix = new PathAnimatorMatrix(view2, fArr);
                    view = view2;
                    ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(pathAnimatorMatrix, PropertyValuesHolder.ofObject(NON_TRANSLATIONS_PROPERTY, new FloatArrayEvaluator(new float[9]), fArr, fArr2), PropertyValuesHolder.ofObject(TRANSLATIONS_PROPERTY, (TypeConverter) null, this.mPathMotion.getPath(fArr[2], fArr[5], fArr2[2], fArr2[5])));
                    final boolean z4 = z;
                    viewGroup2 = viewGroup3;
                    obj = "android:changeTransform:parentMatrix";
                    hashMap = hashMap2;
                    i = 0;
                    AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: androidx.transition.ChangeTransform.3
                        public boolean mIsCanceled;
                        public final Matrix mTempMatrix = new Matrix();

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator) {
                            this.mIsCanceled = true;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            if (!this.mIsCanceled) {
                                if (z4 && ChangeTransform.this.mUseOverlay) {
                                    this.mTempMatrix.set(matrix5);
                                    view.setTag(R.id.transition_transform, this.mTempMatrix);
                                    Transforms transforms2 = transforms;
                                    View view3 = view;
                                    transforms2.getClass();
                                    String[] strArr = ChangeTransform.sTransitionProperties;
                                    view3.setTranslationX(transforms2.mTranslationX);
                                    view3.setTranslationY(transforms2.mTranslationY);
                                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                                    ViewCompat.Api21Impl.setTranslationZ(view3, transforms2.mTranslationZ);
                                    view3.setScaleX(transforms2.mScaleX);
                                    view3.setScaleY(transforms2.mScaleY);
                                    view3.setRotationX(transforms2.mRotationX);
                                    view3.setRotationY(transforms2.mRotationY);
                                    view3.setRotation(transforms2.mRotationZ);
                                } else {
                                    view.setTag(R.id.transition_transform, null);
                                    view.setTag(R.id.parent_matrix, null);
                                }
                            }
                            View view4 = view;
                            ViewUtils.IMPL.getClass();
                            view4.setAnimationMatrix(null);
                            Transforms transforms3 = transforms;
                            View view5 = view;
                            transforms3.getClass();
                            String[] strArr2 = ChangeTransform.sTransitionProperties;
                            view5.setTranslationX(transforms3.mTranslationX);
                            view5.setTranslationY(transforms3.mTranslationY);
                            WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                            ViewCompat.Api21Impl.setTranslationZ(view5, transforms3.mTranslationZ);
                            view5.setScaleX(transforms3.mScaleX);
                            view5.setScaleY(transforms3.mScaleY);
                            view5.setRotationX(transforms3.mRotationX);
                            view5.setRotationY(transforms3.mRotationY);
                            view5.setRotation(transforms3.mRotationZ);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
                        public final void onAnimationPause(Animator animator) {
                            this.mTempMatrix.set(pathAnimatorMatrix.mMatrix);
                            view.setTag(R.id.transition_transform, this.mTempMatrix);
                            Transforms transforms2 = transforms;
                            View view3 = view;
                            transforms2.getClass();
                            String[] strArr = ChangeTransform.sTransitionProperties;
                            view3.setTranslationX(transforms2.mTranslationX);
                            view3.setTranslationY(transforms2.mTranslationY);
                            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                            ViewCompat.Api21Impl.setTranslationZ(view3, transforms2.mTranslationZ);
                            view3.setScaleX(transforms2.mScaleX);
                            view3.setScaleY(transforms2.mScaleY);
                            view3.setRotationX(transforms2.mRotationX);
                            view3.setRotationY(transforms2.mRotationY);
                            view3.setRotation(transforms2.mRotationZ);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
                        public final void onAnimationResume(Animator animator) {
                            View view3 = view;
                            String[] strArr = ChangeTransform.sTransitionProperties;
                            view3.setTranslationX(0.0f);
                            view3.setTranslationY(0.0f);
                            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                            ViewCompat.Api21Impl.setTranslationZ(view3, 0.0f);
                            view3.setScaleX(1.0f);
                            view3.setScaleY(1.0f);
                            view3.setRotationX(0.0f);
                            view3.setRotationY(0.0f);
                            view3.setRotation(0.0f);
                        }
                    };
                    ofPropertyValuesHolder.addListener(animatorListenerAdapter);
                    ofPropertyValuesHolder.addPauseListener(animatorListenerAdapter);
                    objectAnimator = ofPropertyValuesHolder;
                } else {
                    view = view2;
                    hashMap = hashMap2;
                    viewGroup2 = viewGroup3;
                    objectAnimator = null;
                    i = 0;
                    obj = "android:changeTransform:parentMatrix";
                }
                View view3 = transitionValues.view;
                if (!z && objectAnimator != null && this.mUseOverlay) {
                    Matrix matrix9 = new Matrix((Matrix) hashMap3.get(obj));
                    ViewUtils.IMPL.getClass();
                    viewGroup.transformMatrixToLocal(matrix9);
                    int i4 = GhostViewPort.$r8$clinit;
                    if (!(view.getParent() instanceof ViewGroup)) {
                        throw new IllegalArgumentException("Ghosted views must be parented by a ViewGroup");
                    }
                    int i5 = GhostViewHolder.$r8$clinit;
                    GhostViewHolder ghostViewHolder2 = (GhostViewHolder) viewGroup.getTag(R.id.ghost_view_holder);
                    View view4 = view;
                    GhostViewPort ghostViewPort2 = (GhostViewPort) view4.getTag(R.id.ghost_view);
                    if (ghostViewPort2 == null || (ghostViewHolder = (GhostViewHolder) ghostViewPort2.getParent()) == ghostViewHolder2) {
                        ghostViewPort = ghostViewPort2;
                        i2 = i;
                    } else {
                        int i6 = ghostViewPort2.mReferences;
                        ghostViewHolder.removeView(ghostViewPort2);
                        i2 = i6;
                        ghostViewPort = null;
                    }
                    if (ghostViewPort == null) {
                        ghostViewPort = new GhostViewPort(view4);
                        ghostViewPort.mMatrix = matrix9;
                        if (ghostViewHolder2 == null) {
                            ghostViewHolder2 = new GhostViewHolder(viewGroup);
                        } else {
                            if (!ghostViewHolder2.mAttached) {
                                throw new IllegalStateException("This GhostViewHolder is detached!");
                            }
                            new ViewGroupOverlayApi18(ghostViewHolder2.mParent).mViewGroupOverlay.remove(ghostViewHolder2);
                            new ViewGroupOverlayApi18(ghostViewHolder2.mParent).mViewGroupOverlay.add(ghostViewHolder2);
                        }
                        GhostViewPort.copySize(viewGroup, ghostViewHolder2);
                        GhostViewPort.copySize(viewGroup, ghostViewPort);
                        ArrayList arrayList2 = new ArrayList();
                        GhostViewHolder.getParents(arrayList2, ghostViewPort.mView);
                        ArrayList arrayList3 = new ArrayList();
                        int childCount = ghostViewHolder2.getChildCount() - 1;
                        int i7 = i;
                        while (i7 <= childCount) {
                            int i8 = (i7 + childCount) / 2;
                            GhostViewHolder.getParents(arrayList3, ((GhostViewPort) ghostViewHolder2.getChildAt(i8)).mView);
                            if (arrayList2.isEmpty() || arrayList3.isEmpty() || arrayList2.get(i) != arrayList3.get(i)) {
                                arrayList = arrayList2;
                            } else {
                                int min = Math.min(arrayList2.size(), arrayList3.size());
                                int i9 = 1;
                                while (true) {
                                    if (i9 < min) {
                                        View view5 = (View) arrayList2.get(i9);
                                        arrayList = arrayList2;
                                        View view6 = (View) arrayList3.get(i9);
                                        if (view5 != view6) {
                                            ViewGroup viewGroup5 = (ViewGroup) view5.getParent();
                                            int childCount2 = viewGroup5.getChildCount();
                                            if (view5.getZ() == view6.getZ()) {
                                                int i10 = 0;
                                                while (i10 < childCount2) {
                                                    i3 = childCount;
                                                    View childAt = viewGroup5.getChildAt(viewGroup5.getChildDrawingOrder(i10));
                                                    if (childAt != view5) {
                                                        if (childAt == view6) {
                                                            break;
                                                        }
                                                        i10++;
                                                        childCount = i3;
                                                    }
                                                }
                                            } else if (view5.getZ() <= view6.getZ()) {
                                                i3 = childCount;
                                            }
                                        } else {
                                            i9++;
                                            arrayList2 = arrayList;
                                        }
                                    } else {
                                        arrayList = arrayList2;
                                        i3 = childCount;
                                    }
                                    if (z2) {
                                        i7 = i8 + 1;
                                        childCount = i3;
                                    } else {
                                        childCount = i8 - 1;
                                    }
                                    arrayList3.clear();
                                    i = 0;
                                    arrayList2 = arrayList;
                                }
                                z2 = true;
                                if (z2) {
                                }
                                arrayList3.clear();
                                i = 0;
                                arrayList2 = arrayList;
                            }
                            i3 = childCount;
                            z2 = true;
                            if (z2) {
                            }
                            arrayList3.clear();
                            i = 0;
                            arrayList2 = arrayList;
                        }
                        if (i7 < 0 || i7 >= ghostViewHolder2.getChildCount()) {
                            ghostViewHolder2.addView(ghostViewPort);
                        } else {
                            ghostViewHolder2.addView(ghostViewPort, i7);
                        }
                        ghostViewPort.mReferences = i2;
                    } else {
                        ghostViewPort.mMatrix = matrix9;
                    }
                    ghostViewPort.mReferences++;
                    ghostViewPort.mStartParent = (ViewGroup) hashMap.get("android:changeTransform:parent");
                    ghostViewPort.mStartView = view3;
                    Transition transition = this;
                    while (true) {
                        Transition transition2 = transition.mParent;
                        if (transition2 == null) {
                            break;
                        }
                        transition = transition2;
                    }
                    transition.addListener(new GhostListener(view4, ghostViewPort));
                    if (SUPPORTS_VIEW_REMOVAL_SUPPRESSION) {
                        if (view3 != view4) {
                            ViewUtils.setTransitionAlpha(view3, 0.0f);
                        }
                        ViewUtils.setTransitionAlpha(view4, 1.0f);
                    }
                } else if (!SUPPORTS_VIEW_REMOVAL_SUPPRESSION) {
                    viewGroup2.endViewTransition(view3);
                }
                return objectAnimator;
            }
        }
        z = false;
        matrix = (Matrix) hashMap2.get("android:changeTransform:intermediateMatrix");
        if (matrix != null) {
        }
        matrix2 = (Matrix) hashMap2.get("android:changeTransform:intermediateParentMatrix");
        if (matrix2 != null) {
        }
        View view22 = transitionValues2.view;
        if (z) {
        }
        matrix3 = (Matrix) hashMap2.get("android:changeTransform:matrix");
        matrix4 = (Matrix) hashMap3.get("android:changeTransform:matrix");
        if (matrix3 == null) {
        }
        if (matrix4 == null) {
        }
        matrix5 = matrix4;
        if (matrix3.equals(matrix5)) {
        }
        View view32 = transitionValues.view;
        if (!z) {
        }
        if (!SUPPORTS_VIEW_REMOVAL_SUPPRESSION) {
        }
        return objectAnimator;
    }

    @Override // androidx.transition.Transition
    public final String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public ChangeTransform(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mUseOverlay = true;
        this.mReparent = true;
        this.mTempMatrix = new Matrix();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.CHANGE_TRANSFORM);
        XmlPullParser xmlPullParser = (XmlPullParser) attributeSet;
        this.mUseOverlay = !TypedArrayUtils.hasAttribute(xmlPullParser, "reparentWithOverlay") ? true : obtainStyledAttributes.getBoolean(1, true);
        this.mReparent = TypedArrayUtils.hasAttribute(xmlPullParser, "reparent") ? obtainStyledAttributes.getBoolean(0, true) : true;
        obtainStyledAttributes.recycle();
    }
}
