package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageHelper;
import androidx.collection.SimpleArrayMap;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.TransformationCallback;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.expandable.ExpandableWidget;
import com.google.android.material.expandable.ExpandableWidgetHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButtonImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButtonImplLollipop;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.internal.VisibilityAwareImageButton;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.stateful.ExtendableSavedState;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FloatingActionButton extends VisibilityAwareImageButton implements ExpandableWidget, Shapeable, CoordinatorLayout.AttachedBehavior {
    public ColorStateList backgroundTint;
    public PorterDuff.Mode backgroundTintMode;
    public final boolean compatPadding;
    public final int customSize;
    public final ExpandableWidgetHelper expandableWidgetHelper;
    public final AppCompatImageHelper imageHelper;
    public int imagePadding;
    public FloatingActionButtonImplLollipop impl;
    public final int maxImageSize;
    public final Rect shadowPadding;
    public final int size;
    public final Rect touchArea;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.android.material.floatingactionbutton.FloatingActionButton$1, reason: invalid class name */
    public class AnonymousClass1 implements FloatingActionButtonImpl.InternalVisibilityChangedListener {
        public final /* synthetic */ OnVisibilityChangedListener val$listener;

        public AnonymousClass1(FloatingActionButton floatingActionButton, OnVisibilityChangedListener onVisibilityChangedListener) {
            this.val$listener = onVisibilityChangedListener;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Behavior extends BaseBehavior<FloatingActionButton> {
        public Behavior() {
        }

        @Override // com.google.android.material.floatingactionbutton.FloatingActionButton.BaseBehavior
        public /* bridge */ /* synthetic */ void setInternalAutoHideListener(OnVisibilityChangedListener onVisibilityChangedListener) {
            super.setInternalAutoHideListener(onVisibilityChangedListener);
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class OnVisibilityChangedListener {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ShadowDelegateImpl implements ShadowViewDelegate {
        public ShadowDelegateImpl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TransformationCallbackWrapper {
        public final TransformationCallback listener;

        public TransformationCallbackWrapper(TransformationCallback transformationCallback) {
            this.listener = transformationCallback;
        }

        public final boolean equals(Object obj) {
            return (obj instanceof TransformationCallbackWrapper) && ((TransformationCallbackWrapper) obj).listener.equals(this.listener);
        }

        public final int hashCode() {
            return this.listener.hashCode();
        }
    }

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    @Override // android.widget.ImageView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        FloatingActionButtonImpl impl = getImpl();
        getDrawableState();
        impl.getClass();
    }

    @Override // android.view.View
    public final ColorStateList getBackgroundTintList() {
        return this.backgroundTint;
    }

    @Override // android.view.View
    public final PorterDuff.Mode getBackgroundTintMode() {
        return this.backgroundTintMode;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior
    public final CoordinatorLayout.Behavior getBehavior() {
        return new Behavior();
    }

    public final FloatingActionButtonImpl getImpl() {
        if (this.impl == null) {
            this.impl = new FloatingActionButtonImplLollipop(this, new ShadowDelegateImpl());
        }
        return this.impl;
    }

    public final int getSizeDimension(int i) {
        int i2 = this.customSize;
        if (i2 != 0) {
            return i2;
        }
        Resources resources = getResources();
        return i != -1 ? i != 1 ? resources.getDimensionPixelSize(R.dimen.design_fab_size_normal) : resources.getDimensionPixelSize(R.dimen.design_fab_size_mini) : Math.max(resources.getConfiguration().screenWidthDp, resources.getConfiguration().screenHeightDp) < 470 ? getSizeDimension(1) : getSizeDimension(0);
    }

    public final void hide(OnVisibilityChangedListener onVisibilityChangedListener) {
        final FloatingActionButtonImpl impl = getImpl();
        final AnonymousClass1 anonymousClass1 = onVisibilityChangedListener == null ? null : new AnonymousClass1(this, onVisibilityChangedListener);
        FloatingActionButton floatingActionButton = impl.view;
        if (floatingActionButton.getVisibility() == 0) {
            if (impl.animState == 1) {
                return;
            }
        } else if (impl.animState != 2) {
            return;
        }
        Animator animator = impl.currentAnimator;
        if (animator != null) {
            animator.cancel();
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        FloatingActionButton floatingActionButton2 = impl.view;
        int i = 0;
        byte b = 0;
        if (!floatingActionButton2.isLaidOut() || floatingActionButton2.isInEditMode()) {
            floatingActionButton.internalSetVisibility(4, false);
            if (anonymousClass1 != null) {
                anonymousClass1.val$listener.getClass();
                return;
            }
            return;
        }
        MotionSpec motionSpec = impl.hideMotionSpec;
        AnimatorSet createAnimator = motionSpec != null ? impl.createAnimator(motionSpec, 0.0f, 0.0f, 0.0f) : impl.createDefaultAnimator(0.0f, 0.4f, 0.4f, FloatingActionButtonImpl.HIDE_ANIM_DURATION_ATTR, FloatingActionButtonImpl.HIDE_ANIM_EASING_ATTR);
        final byte b2 = b == true ? 1 : 0;
        createAnimator.addListener(
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0058: INVOKE 
              (r9v4 'createAnimator' android.animation.AnimatorSet)
              (wrap:android.animation.AnimatorListenerAdapter:0x0055: CONSTRUCTOR 
              (r1v0 'impl' com.google.android.material.floatingactionbutton.FloatingActionButtonImpl A[DONT_INLINE])
              (r7v4 'b2' byte A[DONT_INLINE])
              (r8v2 'anonymousClass1' com.google.android.material.floatingactionbutton.FloatingActionButton$1 A[DONT_INLINE])
             A[MD:(com.google.android.material.floatingactionbutton.FloatingActionButtonImpl, boolean, com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$InternalVisibilityChangedListener):void (m), WRAPPED] (LINE:86) call: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.1.<init>(com.google.android.material.floatingactionbutton.FloatingActionButtonImpl, boolean, com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$InternalVisibilityChangedListener):void type: CONSTRUCTOR)
             VIRTUAL call: android.animation.AnimatorSet.addListener(android.animation.Animator$AnimatorListener):void A[MD:(android.animation.Animator$AnimatorListener):void (c)] (LINE:89) in method: com.google.android.material.floatingactionbutton.FloatingActionButton.hide(com.google.android.material.floatingactionbutton.FloatingActionButton$OnVisibilityChangedListener):void, file: classes4.dex
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:186)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
            	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(ReferencePipeline.java:285)
            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:571)
            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:560)
            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:153)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:176)
            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:265)
            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:636)
            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:297)
            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:286)
            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:270)
            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:161)
            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:103)
            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:45)
            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:34)
            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:22)
            	at jadx.core.ProcessClass.process(ProcessClass.java:79)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:402)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:390)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:340)
            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl, state: NOT_LOADED
            	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:305)
            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:910)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
            	... 39 more
            */
        /*
            this = this;
            r0 = 1
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl r1 = r8.getImpl()
            if (r9 != 0) goto L9
            r8 = 0
            goto Lf
        L9:
            com.google.android.material.floatingactionbutton.FloatingActionButton$1 r2 = new com.google.android.material.floatingactionbutton.FloatingActionButton$1
            r2.<init>(r8, r9)
            r8 = r2
        Lf:
            com.google.android.material.floatingactionbutton.FloatingActionButton r9 = r1.view
            int r2 = r9.getVisibility()
            if (r2 != 0) goto L1c
            int r2 = r1.animState
            if (r2 != r0) goto L22
            goto L7f
        L1c:
            int r2 = r1.animState
            r3 = 2
            if (r2 == r3) goto L22
            goto L7f
        L22:
            android.animation.Animator r2 = r1.currentAnimator
            if (r2 == 0) goto L29
            r2.cancel()
        L29:
            java.util.WeakHashMap r2 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            com.google.android.material.floatingactionbutton.FloatingActionButton r2 = r1.view
            boolean r3 = r2.isLaidOut()
            r7 = 0
            if (r3 == 0) goto L74
            boolean r2 = r2.isInEditMode()
            if (r2 != 0) goto L74
            com.google.android.material.animation.MotionSpec r9 = r1.hideMotionSpec
            if (r9 == 0) goto L44
            r2 = 0
            android.animation.AnimatorSet r9 = r1.createAnimator(r9, r2, r2, r2)
            goto L53
        L44:
            int r5 = com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.HIDE_ANIM_DURATION_ATTR
            int r6 = com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.HIDE_ANIM_EASING_ATTR
            r4 = 1053609165(0x3ecccccd, float:0.4)
            r2 = 0
            r3 = 1053609165(0x3ecccccd, float:0.4)
            android.animation.AnimatorSet r9 = r1.createDefaultAnimator(r2, r3, r4, r5, r6)
        L53:
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$1 r2 = new com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$1
            r2.<init>(r1, r7, r8)
            r9.addListener(r2)
            java.util.ArrayList r8 = r1.hideListeners
            if (r8 == 0) goto L70
            int r1 = r8.size()
        L63:
            if (r7 >= r1) goto L70
            java.lang.Object r2 = r8.get(r7)
            int r7 = r7 + r0
            android.animation.Animator$AnimatorListener r2 = (android.animation.Animator.AnimatorListener) r2
            r9.addListener(r2)
            goto L63
        L70:
            r9.start()
            return
        L74:
            r0 = 4
            r9.internalSetVisibility(r0, r7)
            if (r8 == 0) goto L7f
            com.google.android.material.floatingactionbutton.FloatingActionButton$OnVisibilityChangedListener r8 = r8.val$listener
            r8.getClass()
        L7f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.floatingactionbutton.FloatingActionButton.hide(com.google.android.material.floatingactionbutton.FloatingActionButton$OnVisibilityChangedListener):void");
    }

    @Override // android.widget.ImageView, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        getImpl().getClass();
    }

    public final void offsetRectWithShadow(Rect rect) {
        int i = rect.left;
        Rect rect2 = this.shadowPadding;
        rect.left = i + rect2.left;
        rect.top += rect2.top;
        rect.right -= rect2.right;
        rect.bottom -= rect2.bottom;
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        FloatingActionButtonImpl impl = getImpl();
        MaterialShapeDrawable materialShapeDrawable = impl.shapeDrawable;
        if (materialShapeDrawable != null) {
            MaterialShapeUtils.setParentAbsoluteElevation(impl.view, materialShapeDrawable);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        FloatingActionButtonImpl impl = getImpl();
        ViewTreeObserver viewTreeObserver = impl.view.getViewTreeObserver();
        FloatingActionButtonImpl.AnonymousClass6 anonymousClass6 = impl.preDrawListener;
        if (anonymousClass6 != null) {
            viewTreeObserver.removeOnPreDrawListener(anonymousClass6);
            impl.preDrawListener = null;
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onMeasure(int i, int i2) {
        int sizeDimension = getSizeDimension(this.size);
        this.imagePadding = (sizeDimension - this.maxImageSize) / 2;
        getImpl().updatePadding();
        int min = Math.min(View.resolveSize(sizeDimension, i), View.resolveSize(sizeDimension, i2));
        Rect rect = this.shadowPadding;
        setMeasuredDimension(rect.left + min + rect.right, min + rect.top + rect.bottom);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof ExtendableSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        ExtendableSavedState extendableSavedState = (ExtendableSavedState) parcelable;
        super.onRestoreInstanceState(extendableSavedState.mSuperState);
        ExpandableWidgetHelper expandableWidgetHelper = this.expandableWidgetHelper;
        Bundle bundle = (Bundle) extendableSavedState.extendableStates.get("expandableWidgetHelper");
        bundle.getClass();
        expandableWidgetHelper.getClass();
        expandableWidgetHelper.expanded = bundle.getBoolean("expanded", false);
        expandableWidgetHelper.expandedComponentIdHint = bundle.getInt("expandedComponentIdHint", 0);
        if (expandableWidgetHelper.expanded) {
            ViewParent parent = expandableWidgetHelper.widget.getParent();
            if (parent instanceof CoordinatorLayout) {
                ((CoordinatorLayout) parent).dispatchDependentViewsChanged(expandableWidgetHelper.widget);
            }
        }
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (onSaveInstanceState == null) {
            onSaveInstanceState = new Bundle();
        }
        ExtendableSavedState extendableSavedState = new ExtendableSavedState(onSaveInstanceState);
        SimpleArrayMap simpleArrayMap = extendableSavedState.extendableStates;
        ExpandableWidgetHelper expandableWidgetHelper = this.expandableWidgetHelper;
        expandableWidgetHelper.getClass();
        Bundle bundle = new Bundle();
        bundle.putBoolean("expanded", expandableWidgetHelper.expanded);
        bundle.putInt("expandedComponentIdHint", expandableWidgetHelper.expandedComponentIdHint);
        simpleArrayMap.put("expandableWidgetHelper", bundle);
        return extendableSavedState;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        if (motionEvent.getAction() == 0) {
            Rect rect = this.touchArea;
            rect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            offsetRectWithShadow(rect);
            FloatingActionButtonImplLollipop floatingActionButtonImplLollipop = this.impl;
            if (floatingActionButtonImplLollipop.ensureMinTouchTargetSize) {
                int i2 = floatingActionButtonImplLollipop.minTouchTargetSize;
                FloatingActionButton floatingActionButton = floatingActionButtonImplLollipop.view;
                i = Math.max((i2 - floatingActionButton.getSizeDimension(floatingActionButton.size)) / 2, 0);
            } else {
                i = 0;
            }
            int i3 = -i;
            rect.inset(i3, i3);
            if (!this.touchArea.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                return false;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void setBackgroundColor(int i) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    @Override // android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    @Override // android.view.View
    public final void setBackgroundResource(int i) {
        Log.i("FloatingActionButton", "Setting a custom background is not supported.");
    }

    @Override // android.view.View
    public final void setBackgroundTintList(ColorStateList colorStateList) {
        if (this.backgroundTint != colorStateList) {
            this.backgroundTint = colorStateList;
            FloatingActionButtonImpl impl = getImpl();
            MaterialShapeDrawable materialShapeDrawable = impl.shapeDrawable;
            if (materialShapeDrawable != null) {
                materialShapeDrawable.setTintList(colorStateList);
            }
            BorderDrawable borderDrawable = impl.borderDrawable;
            if (borderDrawable != null) {
                if (colorStateList != null) {
                    borderDrawable.currentBorderTintColor = colorStateList.getColorForState(borderDrawable.getState(), borderDrawable.currentBorderTintColor);
                }
                borderDrawable.borderTint = colorStateList;
                borderDrawable.invalidateShader = true;
                borderDrawable.invalidateSelf();
            }
        }
    }

    @Override // android.view.View
    public final void setBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.backgroundTintMode != mode) {
            this.backgroundTintMode = mode;
            MaterialShapeDrawable materialShapeDrawable = getImpl().shapeDrawable;
            if (materialShapeDrawable != null) {
                materialShapeDrawable.setTintMode(mode);
            }
        }
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        MaterialShapeDrawable materialShapeDrawable = getImpl().shapeDrawable;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setElevation(f);
        }
    }

    @Override // android.widget.ImageView
    public final void setImageDrawable(Drawable drawable) {
        if (getDrawable() != drawable) {
            super.setImageDrawable(drawable);
            FloatingActionButtonImpl impl = getImpl();
            float f = impl.imageMatrixScale;
            impl.imageMatrixScale = f;
            Matrix matrix = impl.tmpMatrix;
            impl.calculateImageMatrixFromScale(f, matrix);
            impl.view.setImageMatrix(matrix);
        }
    }

    @Override // android.widget.ImageView
    public final void setImageResource(int i) {
        this.imageHelper.setImageResource(i);
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        drawable.clearColorFilter();
    }

    @Override // android.view.View
    public final void setScaleX(float f) {
        super.setScaleX(f);
        getImpl().onScaleChanged();
    }

    @Override // android.view.View
    public final void setScaleY(float f) {
        super.setScaleY(f);
        getImpl().onScaleChanged();
    }

    public void setShadowPaddingEnabled(boolean z) {
        FloatingActionButtonImpl impl = getImpl();
        impl.shadowPaddingEnabled = z;
        impl.updatePadding();
    }

    @Override // com.google.android.material.shape.Shapeable
    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        getImpl().setShapeAppearance(shapeAppearanceModel);
    }

    @Override // android.view.View
    public final void setTranslationX(float f) {
        super.setTranslationX(f);
        getImpl().onTranslationChanged();
    }

    @Override // android.view.View
    public final void setTranslationY(float f) {
        super.setTranslationY(f);
        getImpl().onTranslationChanged();
    }

    @Override // android.view.View
    public final void setTranslationZ(float f) {
        super.setTranslationZ(f);
        getImpl().onTranslationChanged();
    }

    @Override // com.google.android.material.internal.VisibilityAwareImageButton, android.widget.ImageView, android.view.View
    public final void setVisibility(int i) {
        internalSetVisibility(i, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void show(OnVisibilityChangedListener onVisibilityChangedListener) {
        final FloatingActionButtonImpl impl = getImpl();
        final AnonymousClass1 anonymousClass1 = onVisibilityChangedListener == null ? null : new AnonymousClass1(this, onVisibilityChangedListener);
        int i = 0;
        byte b = 0;
        if ((impl.view.getVisibility() == 0 ? impl.animState != 1 : impl.animState == 2) == true) {
            return;
        }
        Animator animator = impl.currentAnimator;
        if (animator != null) {
            animator.cancel();
        }
        byte b2 = impl.showMotionSpec == null;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        FloatingActionButton floatingActionButton = impl.view;
        if ((floatingActionButton.isLaidOut() && !floatingActionButton.isInEditMode()) != true) {
            floatingActionButton.internalSetVisibility(0, false);
            floatingActionButton.setAlpha(1.0f);
            floatingActionButton.setScaleY(1.0f);
            floatingActionButton.setScaleX(1.0f);
            impl.imageMatrixScale = 1.0f;
            Matrix matrix = impl.tmpMatrix;
            impl.calculateImageMatrixFromScale(1.0f, matrix);
            floatingActionButton.setImageMatrix(matrix);
            if (anonymousClass1 != null) {
                anonymousClass1.val$listener.getClass();
                return;
            }
            return;
        }
        if (floatingActionButton.getVisibility() != 0) {
            floatingActionButton.setAlpha(0.0f);
            floatingActionButton.setScaleY(b2 != false ? 0.4f : 0.0f);
            floatingActionButton.setScaleX(b2 != false ? 0.4f : 0.0f);
            float f = b2 == true ? 0.4f : 0.0f;
            impl.imageMatrixScale = f;
            Matrix matrix2 = impl.tmpMatrix;
            impl.calculateImageMatrixFromScale(f, matrix2);
            floatingActionButton.setImageMatrix(matrix2);
        }
        MotionSpec motionSpec = impl.showMotionSpec;
        AnimatorSet createAnimator = motionSpec != null ? impl.createAnimator(motionSpec, 1.0f, 1.0f, 1.0f) : impl.createDefaultAnimator(1.0f, 1.0f, 1.0f, FloatingActionButtonImpl.SHOW_ANIM_DURATION_ATTR, FloatingActionButtonImpl.SHOW_ANIM_EASING_ATTR);
        final byte b3 = b == true ? 1 : 0;
        createAnimator.addListener(
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0094: INVOKE 
              (r9v13 'createAnimator' android.animation.AnimatorSet)
              (wrap:android.animation.AnimatorListenerAdapter:0x0091: CONSTRUCTOR 
              (r1v0 'impl' com.google.android.material.floatingactionbutton.FloatingActionButtonImpl A[DONT_INLINE])
              (r7v4 'b3' byte A[DONT_INLINE])
              (r8v2 'anonymousClass1' com.google.android.material.floatingactionbutton.FloatingActionButton$1 A[DONT_INLINE])
             A[MD:(com.google.android.material.floatingactionbutton.FloatingActionButtonImpl, boolean, com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$InternalVisibilityChangedListener):void (m), WRAPPED] (LINE:146) call: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.2.<init>(com.google.android.material.floatingactionbutton.FloatingActionButtonImpl, boolean, com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$InternalVisibilityChangedListener):void type: CONSTRUCTOR)
             VIRTUAL call: android.animation.AnimatorSet.addListener(android.animation.Animator$AnimatorListener):void A[MD:(android.animation.Animator$AnimatorListener):void (c)] (LINE:149) in method: com.google.android.material.floatingactionbutton.FloatingActionButton.show(com.google.android.material.floatingactionbutton.FloatingActionButton$OnVisibilityChangedListener):void, file: classes4.dex
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:186)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
            	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(ReferencePipeline.java:285)
            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:571)
            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:560)
            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:153)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:176)
            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:265)
            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:636)
            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:297)
            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:286)
            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:270)
            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:161)
            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:103)
            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:45)
            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:34)
            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:22)
            	at jadx.core.ProcessClass.process(ProcessClass.java:79)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:402)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:390)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:340)
            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl, state: NOT_LOADED
            	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:305)
            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:910)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
            	... 43 more
            */
        /*
            this = this;
            r0 = 1
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl r1 = r8.getImpl()
            if (r9 != 0) goto L9
            r8 = 0
            goto Lf
        L9:
            com.google.android.material.floatingactionbutton.FloatingActionButton$1 r2 = new com.google.android.material.floatingactionbutton.FloatingActionButton$1
            r2.<init>(r8, r9)
            r8 = r2
        Lf:
            com.google.android.material.floatingactionbutton.FloatingActionButton r9 = r1.view
            int r9 = r9.getVisibility()
            r7 = 0
            if (r9 == 0) goto L1e
            int r9 = r1.animState
            r2 = 2
            if (r9 != r2) goto L24
            goto L22
        L1e:
            int r9 = r1.animState
            if (r9 == r0) goto L24
        L22:
            r9 = r0
            goto L25
        L24:
            r9 = r7
        L25:
            if (r9 == 0) goto L29
            goto Lcd
        L29:
            android.animation.Animator r9 = r1.currentAnimator
            if (r9 == 0) goto L30
            r9.cancel()
        L30:
            com.google.android.material.animation.MotionSpec r9 = r1.showMotionSpec
            if (r9 != 0) goto L36
            r9 = r0
            goto L37
        L36:
            r9 = r7
        L37:
            java.util.WeakHashMap r2 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            com.google.android.material.floatingactionbutton.FloatingActionButton r2 = r1.view
            boolean r3 = r2.isLaidOut()
            if (r3 == 0) goto L49
            boolean r3 = r2.isInEditMode()
            if (r3 != 0) goto L49
            r3 = r0
            goto L4a
        L49:
            r3 = r7
        L4a:
            r4 = 1065353216(0x3f800000, float:1.0)
            if (r3 == 0) goto Lb0
            int r3 = r2.getVisibility()
            if (r3 == 0) goto L78
            r3 = 0
            r2.setAlpha(r3)
            r5 = 1053609165(0x3ecccccd, float:0.4)
            if (r9 == 0) goto L5f
            r6 = r5
            goto L60
        L5f:
            r6 = r3
        L60:
            r2.setScaleY(r6)
            if (r9 == 0) goto L67
            r6 = r5
            goto L68
        L67:
            r6 = r3
        L68:
            r2.setScaleX(r6)
            if (r9 == 0) goto L6e
            r3 = r5
        L6e:
            r1.imageMatrixScale = r3
            android.graphics.Matrix r9 = r1.tmpMatrix
            r1.calculateImageMatrixFromScale(r3, r9)
            r2.setImageMatrix(r9)
        L78:
            com.google.android.material.animation.MotionSpec r9 = r1.showMotionSpec
            if (r9 == 0) goto L81
            android.animation.AnimatorSet r9 = r1.createAnimator(r9, r4, r4, r4)
            goto L8f
        L81:
            int r5 = com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.SHOW_ANIM_DURATION_ATTR
            int r6 = com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.SHOW_ANIM_EASING_ATTR
            r4 = 1065353216(0x3f800000, float:1.0)
            r2 = 1065353216(0x3f800000, float:1.0)
            r3 = 1065353216(0x3f800000, float:1.0)
            android.animation.AnimatorSet r9 = r1.createDefaultAnimator(r2, r3, r4, r5, r6)
        L8f:
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$2 r2 = new com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$2
            r2.<init>(r1, r7, r8)
            r9.addListener(r2)
            java.util.ArrayList r8 = r1.showListeners
            if (r8 == 0) goto Lac
            int r1 = r8.size()
        L9f:
            if (r7 >= r1) goto Lac
            java.lang.Object r2 = r8.get(r7)
            int r7 = r7 + r0
            android.animation.Animator$AnimatorListener r2 = (android.animation.Animator.AnimatorListener) r2
            r9.addListener(r2)
            goto L9f
        Lac:
            r9.start()
            return
        Lb0:
            r2.internalSetVisibility(r7, r7)
            r2.setAlpha(r4)
            r2.setScaleY(r4)
            r2.setScaleX(r4)
            r1.imageMatrixScale = r4
            android.graphics.Matrix r9 = r1.tmpMatrix
            r1.calculateImageMatrixFromScale(r4, r9)
            r2.setImageMatrix(r9)
            if (r8 == 0) goto Lcd
            com.google.android.material.floatingactionbutton.FloatingActionButton$OnVisibilityChangedListener r8 = r8.val$listener
            r8.getClass()
        Lcd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.floatingactionbutton.FloatingActionButton.show(com.google.android.material.floatingactionbutton.FloatingActionButton$OnVisibilityChangedListener):void");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BaseBehavior<T extends FloatingActionButton> extends CoordinatorLayout.Behavior {
        public final boolean autoHideEnabled;
        public OnVisibilityChangedListener internalAutoHideListener;
        public Rect tmpRect;

        public BaseBehavior() {
            this.autoHideEnabled = true;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean getInsetDodgeRect(Rect rect, View view) {
            FloatingActionButton floatingActionButton = (FloatingActionButton) view;
            Rect rect2 = floatingActionButton.shadowPadding;
            rect.set(floatingActionButton.getLeft() + rect2.left, floatingActionButton.getTop() + rect2.top, floatingActionButton.getRight() - rect2.right, floatingActionButton.getBottom() - rect2.bottom);
            return true;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
            if (layoutParams.dodgeInsetEdges == 0) {
                layoutParams.dodgeInsetEdges = 80;
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            FloatingActionButton floatingActionButton = (FloatingActionButton) view;
            if (view2 instanceof AppBarLayout) {
                updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout) view2, floatingActionButton);
            } else {
                ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                if (layoutParams instanceof CoordinatorLayout.LayoutParams ? ((CoordinatorLayout.LayoutParams) layoutParams).mBehavior instanceof BottomSheetBehavior : false) {
                    updateFabVisibilityForBottomSheet(view2, floatingActionButton);
                }
            }
            return false;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            FloatingActionButton floatingActionButton = (FloatingActionButton) view;
            List dependencies = coordinatorLayout.getDependencies(floatingActionButton);
            int size = dependencies.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                View view2 = (View) dependencies.get(i3);
                if (!(view2 instanceof AppBarLayout)) {
                    ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                    if ((layoutParams instanceof CoordinatorLayout.LayoutParams ? ((CoordinatorLayout.LayoutParams) layoutParams).mBehavior instanceof BottomSheetBehavior : false) && updateFabVisibilityForBottomSheet(view2, floatingActionButton)) {
                        break;
                    }
                } else {
                    if (updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout) view2, floatingActionButton)) {
                        break;
                    }
                }
            }
            coordinatorLayout.onLayoutChild(floatingActionButton, i);
            Rect rect = floatingActionButton.shadowPadding;
            if (rect != null && rect.centerX() > 0 && rect.centerY() > 0) {
                CoordinatorLayout.LayoutParams layoutParams2 = (CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams();
                int i4 = floatingActionButton.getRight() >= coordinatorLayout.getWidth() - ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin ? rect.right : floatingActionButton.getLeft() <= ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin ? -rect.left : 0;
                if (floatingActionButton.getBottom() >= coordinatorLayout.getHeight() - ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin) {
                    i2 = rect.bottom;
                } else if (floatingActionButton.getTop() <= ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin) {
                    i2 = -rect.top;
                }
                if (i2 != 0) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    floatingActionButton.offsetTopAndBottom(i2);
                }
                if (i4 != 0) {
                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    floatingActionButton.offsetLeftAndRight(i4);
                }
            }
            return true;
        }

        public void setInternalAutoHideListener(OnVisibilityChangedListener onVisibilityChangedListener) {
            this.internalAutoHideListener = onVisibilityChangedListener;
        }

        public final boolean updateFabVisibilityForAppBarLayout(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, FloatingActionButton floatingActionButton) {
            if (!(this.autoHideEnabled && ((CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams()).mAnchorId == appBarLayout.getId() && floatingActionButton.userSetVisibility == 0)) {
                return false;
            }
            if (this.tmpRect == null) {
                this.tmpRect = new Rect();
            }
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(coordinatorLayout, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                floatingActionButton.hide(this.internalAutoHideListener);
            } else {
                floatingActionButton.show(this.internalAutoHideListener);
            }
            return true;
        }

        public final boolean updateFabVisibilityForBottomSheet(View view, FloatingActionButton floatingActionButton) {
            if (!(this.autoHideEnabled && ((CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams()).mAnchorId == view.getId() && floatingActionButton.userSetVisibility == 0)) {
                return false;
            }
            if (view.getTop() < (floatingActionButton.getHeight() / 2) + ((ViewGroup.MarginLayoutParams) ((CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams())).topMargin) {
                floatingActionButton.hide(this.internalAutoHideListener);
            } else {
                floatingActionButton.show(this.internalAutoHideListener);
            }
            return true;
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.FloatingActionButton_Behavior_Layout);
            this.autoHideEnabled = obtainStyledAttributes.getBoolean(0, true);
            obtainStyledAttributes.recycle();
        }
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.floatingActionButtonStyle);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, R.style.Widget_Design_FloatingActionButton), attributeSet, i);
        Drawable drawable;
        Drawable drawable2;
        this.shadowPadding = new Rect();
        this.touchArea = new Rect();
        Context context2 = getContext();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.FloatingActionButton, i, R.style.Widget_Design_FloatingActionButton, new int[0]);
        this.backgroundTint = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 1);
        this.backgroundTintMode = ViewUtils.parseTintMode(obtainStyledAttributes.getInt(2, -1), null);
        ColorStateList colorStateList = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 12);
        this.size = obtainStyledAttributes.getInt(7, -1);
        this.customSize = obtainStyledAttributes.getDimensionPixelSize(6, 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(3, 0);
        float dimension = obtainStyledAttributes.getDimension(4, 0.0f);
        float dimension2 = obtainStyledAttributes.getDimension(9, 0.0f);
        float dimension3 = obtainStyledAttributes.getDimension(11, 0.0f);
        this.compatPadding = obtainStyledAttributes.getBoolean(16, false);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.mtrl_fab_min_touch_target);
        int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(10, 0);
        this.maxImageSize = dimensionPixelSize3;
        FloatingActionButtonImpl impl = getImpl();
        if (impl.maxImageSize != dimensionPixelSize3) {
            impl.maxImageSize = dimensionPixelSize3;
            float f = impl.imageMatrixScale;
            impl.imageMatrixScale = f;
            Matrix matrix = impl.tmpMatrix;
            impl.calculateImageMatrixFromScale(f, matrix);
            impl.view.setImageMatrix(matrix);
        }
        MotionSpec createFromAttribute = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, 15);
        MotionSpec createFromAttribute2 = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, 8);
        ShapeAppearanceModel build = ShapeAppearanceModel.builder(context2, attributeSet, i, R.style.Widget_Design_FloatingActionButton, ShapeAppearanceModel.PILL).build();
        boolean z = obtainStyledAttributes.getBoolean(5, false);
        setEnabled(obtainStyledAttributes.getBoolean(0, true));
        obtainStyledAttributes.recycle();
        AppCompatImageHelper appCompatImageHelper = new AppCompatImageHelper(this);
        this.imageHelper = appCompatImageHelper;
        appCompatImageHelper.loadFromAttributes(attributeSet, i);
        this.expandableWidgetHelper = new ExpandableWidgetHelper(this);
        getImpl().setShapeAppearance(build);
        FloatingActionButtonImpl impl2 = getImpl();
        ColorStateList colorStateList2 = this.backgroundTint;
        PorterDuff.Mode mode = this.backgroundTintMode;
        FloatingActionButtonImplLollipop floatingActionButtonImplLollipop = (FloatingActionButtonImplLollipop) impl2;
        ShapeAppearanceModel shapeAppearanceModel = floatingActionButtonImplLollipop.shapeAppearance;
        shapeAppearanceModel.getClass();
        FloatingActionButtonImplLollipop.AlwaysStatefulMaterialShapeDrawable alwaysStatefulMaterialShapeDrawable = new FloatingActionButtonImplLollipop.AlwaysStatefulMaterialShapeDrawable(shapeAppearanceModel);
        floatingActionButtonImplLollipop.shapeDrawable = alwaysStatefulMaterialShapeDrawable;
        alwaysStatefulMaterialShapeDrawable.setTintList(colorStateList2);
        if (mode != null) {
            floatingActionButtonImplLollipop.shapeDrawable.setTintMode(mode);
        }
        MaterialShapeDrawable materialShapeDrawable = floatingActionButtonImplLollipop.shapeDrawable;
        FloatingActionButton floatingActionButton = floatingActionButtonImplLollipop.view;
        materialShapeDrawable.initializeElevationOverlay(floatingActionButton.getContext());
        if (dimensionPixelSize > 0) {
            Context context3 = floatingActionButton.getContext();
            ShapeAppearanceModel shapeAppearanceModel2 = floatingActionButtonImplLollipop.shapeAppearance;
            shapeAppearanceModel2.getClass();
            BorderDrawable borderDrawable = new BorderDrawable(shapeAppearanceModel2);
            int color = context3.getColor(R.color.design_fab_stroke_top_outer_color);
            int color2 = context3.getColor(R.color.design_fab_stroke_top_inner_color);
            int color3 = context3.getColor(R.color.design_fab_stroke_end_inner_color);
            int color4 = context3.getColor(R.color.design_fab_stroke_end_outer_color);
            borderDrawable.topOuterStrokeColor = color;
            borderDrawable.topInnerStrokeColor = color2;
            borderDrawable.bottomOuterStrokeColor = color3;
            borderDrawable.bottomInnerStrokeColor = color4;
            float f2 = dimensionPixelSize;
            if (borderDrawable.borderWidth != f2) {
                borderDrawable.borderWidth = f2;
                borderDrawable.paint.setStrokeWidth(f2 * 1.3333f);
                borderDrawable.invalidateShader = true;
                borderDrawable.invalidateSelf();
            }
            if (colorStateList2 != null) {
                borderDrawable.currentBorderTintColor = colorStateList2.getColorForState(borderDrawable.getState(), borderDrawable.currentBorderTintColor);
            }
            borderDrawable.borderTint = colorStateList2;
            borderDrawable.invalidateShader = true;
            borderDrawable.invalidateSelf();
            floatingActionButtonImplLollipop.borderDrawable = borderDrawable;
            BorderDrawable borderDrawable2 = floatingActionButtonImplLollipop.borderDrawable;
            borderDrawable2.getClass();
            MaterialShapeDrawable materialShapeDrawable2 = floatingActionButtonImplLollipop.shapeDrawable;
            materialShapeDrawable2.getClass();
            drawable2 = new LayerDrawable(new Drawable[]{borderDrawable2, materialShapeDrawable2});
            drawable = null;
        } else {
            drawable = null;
            floatingActionButtonImplLollipop.borderDrawable = null;
            drawable2 = floatingActionButtonImplLollipop.shapeDrawable;
        }
        RippleDrawable rippleDrawable = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(colorStateList), drawable2, drawable);
        floatingActionButtonImplLollipop.rippleDrawable = rippleDrawable;
        floatingActionButtonImplLollipop.contentBackground = rippleDrawable;
        getImpl().minTouchTargetSize = dimensionPixelSize2;
        FloatingActionButtonImpl impl3 = getImpl();
        if (impl3.elevation != dimension) {
            impl3.elevation = dimension;
            impl3.onElevationsChanged(dimension, impl3.hoveredFocusedTranslationZ, impl3.pressedTranslationZ);
        }
        FloatingActionButtonImpl impl4 = getImpl();
        if (impl4.hoveredFocusedTranslationZ != dimension2) {
            impl4.hoveredFocusedTranslationZ = dimension2;
            impl4.onElevationsChanged(impl4.elevation, dimension2, impl4.pressedTranslationZ);
        }
        FloatingActionButtonImpl impl5 = getImpl();
        if (impl5.pressedTranslationZ != dimension3) {
            impl5.pressedTranslationZ = dimension3;
            impl5.onElevationsChanged(impl5.elevation, impl5.hoveredFocusedTranslationZ, dimension3);
        }
        getImpl().showMotionSpec = createFromAttribute;
        getImpl().hideMotionSpec = createFromAttribute2;
        getImpl().ensureMinTouchTargetSize = z;
        setScaleType(ImageView.ScaleType.MATRIX);
    }
}
