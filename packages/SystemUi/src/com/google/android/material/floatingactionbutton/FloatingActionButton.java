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
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.internal.VisibilityAwareImageButton;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.stateful.ExtendableSavedState;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class FloatingActionButton extends VisibilityAwareImageButton implements ExpandableWidget, Shapeable, CoordinatorLayout.AttachedBehavior {
    public ColorStateList backgroundTint;
    public PorterDuff.Mode backgroundTintMode;
    public final boolean compatPadding;
    public final int customSize;
    public final ExpandableWidgetHelper expandableWidgetHelper;
    public final AppCompatImageHelper imageHelper;
    public int imagePadding;
    public FloatingActionButtonImplLollipop impl;
    public int maxImageSize;
    public final Rect shadowPadding;
    public final int size;
    public final Rect touchArea;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.android.material.floatingactionbutton.FloatingActionButton$1 */
    public final class C42881 implements FloatingActionButtonImpl.InternalVisibilityChangedListener {
        public final /* synthetic */ OnVisibilityChangedListener val$listener;

        public C42881(FloatingActionButton floatingActionButton, OnVisibilityChangedListener onVisibilityChangedListener) {
            this.val$listener = onVisibilityChangedListener;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Behavior extends BaseBehavior<FloatingActionButton> {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class OnVisibilityChangedListener {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShadowDelegateImpl implements ShadowViewDelegate {
        public ShadowDelegateImpl() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TransformationCallbackWrapper {
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

    public static int resolveAdjustedSize(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode == Integer.MIN_VALUE) {
            return Math.min(i, size);
        }
        if (mode == 0) {
            return i;
        }
        if (mode == 1073741824) {
            return size;
        }
        throw new IllegalArgumentException();
    }

    @Override // android.widget.ImageView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        getImpl().onDrawableStateChanged(getDrawableState());
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
        final C42881 c42881 = onVisibilityChangedListener == null ? null : new C42881(this, onVisibilityChangedListener);
        FloatingActionButton floatingActionButton = impl.view;
        final boolean z = false;
        if (floatingActionButton.getVisibility() != 0 ? impl.animState != 2 : impl.animState == 1) {
            return;
        }
        Animator animator = impl.currentAnimator;
        if (animator != null) {
            animator.cancel();
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        FloatingActionButton floatingActionButton2 = impl.view;
        if (!(ViewCompat.Api19Impl.isLaidOut(floatingActionButton2) && !floatingActionButton2.isInEditMode())) {
            floatingActionButton.internalSetVisibility(4, false);
            if (c42881 != null) {
                c42881.val$listener.getClass();
                return;
            }
            return;
        }
        MotionSpec motionSpec = impl.hideMotionSpec;
        AnimatorSet createAnimator = motionSpec != null ? impl.createAnimator(motionSpec, 0.0f, 0.0f, 0.0f) : impl.createDefaultAnimator(0.0f, 0.4f, 0.4f, FloatingActionButtonImpl.HIDE_ANIM_DURATION_ATTR, FloatingActionButtonImpl.HIDE_ANIM_EASING_ATTR);
        createAnimator.addListener(
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0062: INVOKE 
              (r9v4 'createAnimator' android.animation.AnimatorSet)
              (wrap:android.animation.AnimatorListenerAdapter:0x005f: CONSTRUCTOR 
              (r6v0 'impl' com.google.android.material.floatingactionbutton.FloatingActionButtonImpl A[DONT_INLINE])
              (r7v0 'z' boolean A[DONT_INLINE])
              (r8v2 'c42881' com.google.android.material.floatingactionbutton.FloatingActionButton$1 A[DONT_INLINE])
             A[MD:(com.google.android.material.floatingactionbutton.FloatingActionButtonImpl, boolean, com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$InternalVisibilityChangedListener):void (m), WRAPPED] (LINE:96) call: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.1.<init>(com.google.android.material.floatingactionbutton.FloatingActionButtonImpl, boolean, com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$InternalVisibilityChangedListener):void type: CONSTRUCTOR)
             VIRTUAL call: android.animation.AnimatorSet.addListener(android.animation.Animator$AnimatorListener):void A[MD:(android.animation.Animator$AnimatorListener):void (c)] (LINE:99) in method: com.google.android.material.floatingactionbutton.FloatingActionButton.hide(com.google.android.material.floatingactionbutton.FloatingActionButton$OnVisibilityChangedListener):void, file: classes2.dex
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
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
            	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(ReferencePipeline.java:285)
            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:571)
            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:560)
            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
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
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl r6 = r8.getImpl()
            if (r9 != 0) goto L8
            r8 = 0
            goto Le
        L8:
            com.google.android.material.floatingactionbutton.FloatingActionButton$1 r0 = new com.google.android.material.floatingactionbutton.FloatingActionButton$1
            r0.<init>(r8, r9)
            r8 = r0
        Le:
            com.google.android.material.floatingactionbutton.FloatingActionButton r9 = r6.view
            int r0 = r9.getVisibility()
            r1 = 1
            r7 = 0
            if (r0 != 0) goto L1d
            int r0 = r6.animState
            if (r0 != r1) goto L24
            goto L22
        L1d:
            int r0 = r6.animState
            r2 = 2
            if (r0 == r2) goto L24
        L22:
            r0 = r1
            goto L25
        L24:
            r0 = r7
        L25:
            if (r0 == 0) goto L28
            goto L8c
        L28:
            android.animation.Animator r0 = r6.currentAnimator
            if (r0 == 0) goto L2f
            r0.cancel()
        L2f:
            java.util.WeakHashMap r0 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            com.google.android.material.floatingactionbutton.FloatingActionButton r0 = r6.view
            boolean r2 = androidx.core.view.ViewCompat.Api19Impl.isLaidOut(r0)
            if (r2 == 0) goto L40
            boolean r0 = r0.isInEditMode()
            if (r0 != 0) goto L40
            goto L41
        L40:
            r1 = r7
        L41:
            if (r1 == 0) goto L81
            com.google.android.material.animation.MotionSpec r9 = r6.hideMotionSpec
            if (r9 == 0) goto L4d
            r0 = 0
            android.animation.AnimatorSet r9 = r6.createAnimator(r9, r0, r0, r0)
            goto L5d
        L4d:
            r1 = 0
            r2 = 1053609165(0x3ecccccd, float:0.4)
            r3 = 1053609165(0x3ecccccd, float:0.4)
            int r4 = com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.HIDE_ANIM_DURATION_ATTR
            int r5 = com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.HIDE_ANIM_EASING_ATTR
            r0 = r6
            android.animation.AnimatorSet r9 = r0.createDefaultAnimator(r1, r2, r3, r4, r5)
        L5d:
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$1 r0 = new com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$1
            r0.<init>(r6, r7, r8)
            r9.addListener(r0)
            java.util.ArrayList r8 = r6.hideListeners
            if (r8 == 0) goto L7d
            java.util.Iterator r8 = r8.iterator()
        L6d:
            boolean r0 = r8.hasNext()
            if (r0 == 0) goto L7d
            java.lang.Object r0 = r8.next()
            android.animation.Animator$AnimatorListener r0 = (android.animation.Animator.AnimatorListener) r0
            r9.addListener(r0)
            goto L6d
        L7d:
            r9.start()
            goto L8c
        L81:
            r0 = 4
            r9.internalSetVisibility(r0, r7)
            if (r8 == 0) goto L8c
            com.google.android.material.floatingactionbutton.FloatingActionButton$OnVisibilityChangedListener r8 = r8.val$listener
            r8.getClass()
        L8c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.floatingactionbutton.FloatingActionButton.hide(com.google.android.material.floatingactionbutton.FloatingActionButton$OnVisibilityChangedListener):void");
    }

    @Override // android.widget.ImageView, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        getImpl().jumpDrawableToCurrentState();
    }

    public final void offsetRectWithShadow(Rect rect) {
        int i = rect.left;
        Rect rect2 = this.shadowPadding;
        rect.left = i + rect2.left;
        rect.top += rect2.top;
        rect.right -= rect2.right;
        rect.bottom -= rect2.bottom;
    }

    public final void onApplySupportImageTint() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        drawable.clearColorFilter();
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$6] */
    @Override // android.widget.ImageView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        final FloatingActionButtonImpl impl = getImpl();
        MaterialShapeDrawable materialShapeDrawable = impl.shapeDrawable;
        FloatingActionButton floatingActionButton = impl.view;
        if (materialShapeDrawable != null) {
            MaterialShapeUtils.setParentAbsoluteElevation(floatingActionButton, materialShapeDrawable);
        }
        if (!(impl instanceof FloatingActionButtonImplLollipop)) {
            ViewTreeObserver viewTreeObserver = floatingActionButton.getViewTreeObserver();
            if (impl.preDrawListener == null) {
                impl.preDrawListener = 
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0023: IPUT 
                      (wrap:??:0x0020: CONSTRUCTOR (r2v1 'impl' com.google.android.material.floatingactionbutton.FloatingActionButtonImpl A[DONT_INLINE]) A[MD:(com.google.android.material.floatingactionbutton.FloatingActionButtonImpl):void (m), WRAPPED] (LINE:33) call: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.6.<init>(com.google.android.material.floatingactionbutton.FloatingActionButtonImpl):void type: CONSTRUCTOR)
                      (r2v1 'impl' com.google.android.material.floatingactionbutton.FloatingActionButtonImpl)
                     (LINE:36) com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.preDrawListener com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$6 in method: com.google.android.material.floatingactionbutton.FloatingActionButton.onAttachedToWindow():void, file: classes2.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
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
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                    	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(ReferencePipeline.java:285)
                    	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:571)
                    	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:560)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
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
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:487)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                    	... 49 more
                    */
                /*
                    this = this;
                    super.onAttachedToWindow()
                    com.google.android.material.floatingactionbutton.FloatingActionButtonImpl r2 = r2.getImpl()
                    com.google.android.material.shape.MaterialShapeDrawable r0 = r2.shapeDrawable
                    com.google.android.material.floatingactionbutton.FloatingActionButton r1 = r2.view
                    if (r0 == 0) goto L10
                    com.google.android.material.shape.MaterialShapeUtils.setParentAbsoluteElevation(r1, r0)
                L10:
                    boolean r0 = r2 instanceof com.google.android.material.floatingactionbutton.FloatingActionButtonImplLollipop
                    r0 = r0 ^ 1
                    if (r0 == 0) goto L2a
                    android.view.ViewTreeObserver r0 = r1.getViewTreeObserver()
                    com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$6 r1 = r2.preDrawListener
                    if (r1 != 0) goto L25
                    com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$6 r1 = new com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$6
                    r1.<init>(r2)
                    r2.preDrawListener = r1
                L25:
                    com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$6 r2 = r2.preDrawListener
                    r0.addOnPreDrawListener(r2)
                L2a:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.floatingactionbutton.FloatingActionButton.onAttachedToWindow():void");
            }

            @Override // android.widget.ImageView, android.view.View
            public final void onDetachedFromWindow() {
                super.onDetachedFromWindow();
                FloatingActionButtonImpl impl = getImpl();
                ViewTreeObserver viewTreeObserver = impl.view.getViewTreeObserver();
                FloatingActionButtonImpl.ViewTreeObserverOnPreDrawListenerC42936 viewTreeObserverOnPreDrawListenerC42936 = impl.preDrawListener;
                if (viewTreeObserverOnPreDrawListenerC42936 != null) {
                    viewTreeObserver.removeOnPreDrawListener(viewTreeObserverOnPreDrawListenerC42936);
                    impl.preDrawListener = null;
                }
            }

            @Override // android.widget.ImageView, android.view.View
            public final void onMeasure(int i, int i2) {
                int sizeDimension = getSizeDimension(this.size);
                this.imagePadding = (sizeDimension - this.maxImageSize) / 2;
                getImpl().updatePadding();
                int min = Math.min(resolveAdjustedSize(sizeDimension, i), resolveAdjustedSize(sizeDimension, i2));
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
                    View view = expandableWidgetHelper.widget;
                    ViewParent parent = view.getParent();
                    if (parent instanceof CoordinatorLayout) {
                        ((CoordinatorLayout) parent).dispatchDependentViewsChanged(view);
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
                boolean z;
                if (motionEvent.getAction() == 0) {
                    Rect rect = this.touchArea;
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (ViewCompat.Api19Impl.isLaidOut(this)) {
                        rect.set(0, 0, getWidth(), getHeight());
                        offsetRectWithShadow(rect);
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z && !this.touchArea.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
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
                onApplySupportImageTint();
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

            public final void show(OnVisibilityChangedListener onVisibilityChangedListener) {
                final FloatingActionButtonImpl impl = getImpl();
                final C42881 c42881 = onVisibilityChangedListener == null ? null : new C42881(this, onVisibilityChangedListener);
                final boolean z = false;
                if (impl.view.getVisibility() == 0 ? impl.animState != 1 : impl.animState == 2) {
                    return;
                }
                Animator animator = impl.currentAnimator;
                if (animator != null) {
                    animator.cancel();
                }
                boolean z2 = impl.showMotionSpec == null;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                FloatingActionButton floatingActionButton = impl.view;
                boolean z3 = ViewCompat.Api19Impl.isLaidOut(floatingActionButton) && !floatingActionButton.isInEditMode();
                Matrix matrix = impl.tmpMatrix;
                if (!z3) {
                    floatingActionButton.internalSetVisibility(0, false);
                    floatingActionButton.setAlpha(1.0f);
                    floatingActionButton.setScaleY(1.0f);
                    floatingActionButton.setScaleX(1.0f);
                    impl.imageMatrixScale = 1.0f;
                    impl.calculateImageMatrixFromScale(1.0f, matrix);
                    floatingActionButton.setImageMatrix(matrix);
                    if (c42881 != null) {
                        c42881.val$listener.getClass();
                        return;
                    }
                    return;
                }
                if (floatingActionButton.getVisibility() != 0) {
                    floatingActionButton.setAlpha(0.0f);
                    floatingActionButton.setScaleY(z2 ? 0.4f : 0.0f);
                    floatingActionButton.setScaleX(z2 ? 0.4f : 0.0f);
                    float f = z2 ? 0.4f : 0.0f;
                    impl.imageMatrixScale = f;
                    impl.calculateImageMatrixFromScale(f, matrix);
                    floatingActionButton.setImageMatrix(matrix);
                }
                MotionSpec motionSpec = impl.showMotionSpec;
                AnimatorSet createAnimator = motionSpec != null ? impl.createAnimator(motionSpec, 1.0f, 1.0f, 1.0f) : impl.createDefaultAnimator(1.0f, 1.0f, 1.0f, FloatingActionButtonImpl.SHOW_ANIM_DURATION_ATTR, FloatingActionButtonImpl.SHOW_ANIM_EASING_ATTR);
                createAnimator.addListener(
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0095: INVOKE 
                      (r9v12 'createAnimator' android.animation.AnimatorSet)
                      (wrap:android.animation.AnimatorListenerAdapter:0x0092: CONSTRUCTOR 
                      (r6v0 'impl' com.google.android.material.floatingactionbutton.FloatingActionButtonImpl A[DONT_INLINE])
                      (r7v0 'z' boolean A[DONT_INLINE])
                      (r8v2 'c42881' com.google.android.material.floatingactionbutton.FloatingActionButton$1 A[DONT_INLINE])
                     A[MD:(com.google.android.material.floatingactionbutton.FloatingActionButtonImpl, boolean, com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$InternalVisibilityChangedListener):void (m), WRAPPED] (LINE:147) call: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.2.<init>(com.google.android.material.floatingactionbutton.FloatingActionButtonImpl, boolean, com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$InternalVisibilityChangedListener):void type: CONSTRUCTOR)
                     VIRTUAL call: android.animation.AnimatorSet.addListener(android.animation.Animator$AnimatorListener):void A[MD:(android.animation.Animator$AnimatorListener):void (c)] (LINE:150) in method: com.google.android.material.floatingactionbutton.FloatingActionButton.show(com.google.android.material.floatingactionbutton.FloatingActionButton$OnVisibilityChangedListener):void, file: classes2.dex
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
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                    	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(ReferencePipeline.java:285)
                    	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:571)
                    	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:560)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
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
                    com.google.android.material.floatingactionbutton.FloatingActionButtonImpl r6 = r8.getImpl()
                    if (r9 != 0) goto L8
                    r8 = 0
                    goto Le
                L8:
                    com.google.android.material.floatingactionbutton.FloatingActionButton$1 r0 = new com.google.android.material.floatingactionbutton.FloatingActionButton$1
                    r0.<init>(r8, r9)
                    r8 = r0
                Le:
                    com.google.android.material.floatingactionbutton.FloatingActionButton r9 = r6.view
                    int r9 = r9.getVisibility()
                    r0 = 1
                    r7 = 0
                    if (r9 == 0) goto L21
                    int r9 = r6.animState
                    r1 = 2
                    if (r9 != r1) goto L1f
                L1d:
                    r9 = r0
                    goto L26
                L1f:
                    r9 = r7
                    goto L26
                L21:
                    int r9 = r6.animState
                    if (r9 == r0) goto L1f
                    goto L1d
                L26:
                    if (r9 == 0) goto L2a
                    goto Lcf
                L2a:
                    android.animation.Animator r9 = r6.currentAnimator
                    if (r9 == 0) goto L31
                    r9.cancel()
                L31:
                    com.google.android.material.animation.MotionSpec r9 = r6.showMotionSpec
                    if (r9 != 0) goto L37
                    r9 = r0
                    goto L38
                L37:
                    r9 = r7
                L38:
                    java.util.WeakHashMap r1 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                    com.google.android.material.floatingactionbutton.FloatingActionButton r1 = r6.view
                    boolean r2 = androidx.core.view.ViewCompat.Api19Impl.isLaidOut(r1)
                    if (r2 == 0) goto L49
                    boolean r2 = r1.isInEditMode()
                    if (r2 != 0) goto L49
                    goto L4a
                L49:
                    r0 = r7
                L4a:
                    android.graphics.Matrix r2 = r6.tmpMatrix
                    r3 = 1065353216(0x3f800000, float:1.0)
                    if (r0 == 0) goto Lb4
                    int r0 = r1.getVisibility()
                    if (r0 == 0) goto L78
                    r0 = 0
                    r1.setAlpha(r0)
                    r4 = 1053609165(0x3ecccccd, float:0.4)
                    if (r9 == 0) goto L61
                    r5 = r4
                    goto L62
                L61:
                    r5 = r0
                L62:
                    r1.setScaleY(r5)
                    if (r9 == 0) goto L69
                    r5 = r4
                    goto L6a
                L69:
                    r5 = r0
                L6a:
                    r1.setScaleX(r5)
                    if (r9 == 0) goto L70
                    r0 = r4
                L70:
                    r6.imageMatrixScale = r0
                    r6.calculateImageMatrixFromScale(r0, r2)
                    r1.setImageMatrix(r2)
                L78:
                    com.google.android.material.animation.MotionSpec r9 = r6.showMotionSpec
                    if (r9 == 0) goto L81
                    android.animation.AnimatorSet r9 = r6.createAnimator(r9, r3, r3, r3)
                    goto L90
                L81:
                    r1 = 1065353216(0x3f800000, float:1.0)
                    r2 = 1065353216(0x3f800000, float:1.0)
                    r3 = 1065353216(0x3f800000, float:1.0)
                    int r4 = com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.SHOW_ANIM_DURATION_ATTR
                    int r5 = com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.SHOW_ANIM_EASING_ATTR
                    r0 = r6
                    android.animation.AnimatorSet r9 = r0.createDefaultAnimator(r1, r2, r3, r4, r5)
                L90:
                    com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$2 r0 = new com.google.android.material.floatingactionbutton.FloatingActionButtonImpl$2
                    r0.<init>(r6, r7, r8)
                    r9.addListener(r0)
                    java.util.ArrayList r8 = r6.showListeners
                    if (r8 == 0) goto Lb0
                    java.util.Iterator r8 = r8.iterator()
                La0:
                    boolean r0 = r8.hasNext()
                    if (r0 == 0) goto Lb0
                    java.lang.Object r0 = r8.next()
                    android.animation.Animator$AnimatorListener r0 = (android.animation.Animator.AnimatorListener) r0
                    r9.addListener(r0)
                    goto La0
                Lb0:
                    r9.start()
                    goto Lcf
                Lb4:
                    r1.internalSetVisibility(r7, r7)
                    r1.setAlpha(r3)
                    r1.setScaleY(r3)
                    r1.setScaleX(r3)
                    r6.imageMatrixScale = r3
                    r6.calculateImageMatrixFromScale(r3, r2)
                    r1.setImageMatrix(r2)
                    if (r8 == 0) goto Lcf
                    com.google.android.material.floatingactionbutton.FloatingActionButton$OnVisibilityChangedListener r8 = r8.val$listener
                    r8.getClass()
                Lcf:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.floatingactionbutton.FloatingActionButton.show(com.google.android.material.floatingactionbutton.FloatingActionButton$OnVisibilityChangedListener):void");
            }

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            public static class BaseBehavior<T extends FloatingActionButton> extends CoordinatorLayout.Behavior<T> {
                public final boolean autoHideEnabled;
                public OnVisibilityChangedListener internalAutoHideListener;
                public Rect tmpRect;

                public BaseBehavior() {
                    this.autoHideEnabled = true;
                }

                @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
                public final boolean getInsetDodgeRect(View view, Rect rect) {
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
                    if (rect == null || rect.centerX() <= 0 || rect.centerY() <= 0) {
                        return true;
                    }
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
                    if (i4 == 0) {
                        return true;
                    }
                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    floatingActionButton.offsetLeftAndRight(i4);
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
                super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132018817), attributeSet, i);
                this.shadowPadding = new Rect();
                this.touchArea = new Rect();
                Context context2 = getContext();
                TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.FloatingActionButton, i, 2132018817, new int[0]);
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
                ShapeAppearanceModel build = ShapeAppearanceModel.builder(context2, attributeSet, i, 2132018817, ShapeAppearanceModel.PILL).build();
                boolean z = obtainStyledAttributes.getBoolean(5, false);
                setEnabled(obtainStyledAttributes.getBoolean(0, true));
                obtainStyledAttributes.recycle();
                AppCompatImageHelper appCompatImageHelper = new AppCompatImageHelper(this);
                this.imageHelper = appCompatImageHelper;
                appCompatImageHelper.loadFromAttributes(attributeSet, i);
                this.expandableWidgetHelper = new ExpandableWidgetHelper(this);
                getImpl().setShapeAppearance(build);
                getImpl().initializeBackgroundDrawable(this.backgroundTint, this.backgroundTintMode, colorStateList, dimensionPixelSize);
                getImpl().minTouchTargetSize = dimensionPixelSize2;
                FloatingActionButtonImpl impl2 = getImpl();
                if (impl2.elevation != dimension) {
                    impl2.elevation = dimension;
                    impl2.onElevationsChanged(dimension, impl2.hoveredFocusedTranslationZ, impl2.pressedTranslationZ);
                }
                FloatingActionButtonImpl impl3 = getImpl();
                if (impl3.hoveredFocusedTranslationZ != dimension2) {
                    impl3.hoveredFocusedTranslationZ = dimension2;
                    impl3.onElevationsChanged(impl3.elevation, dimension2, impl3.pressedTranslationZ);
                }
                FloatingActionButtonImpl impl4 = getImpl();
                if (impl4.pressedTranslationZ != dimension3) {
                    impl4.pressedTranslationZ = dimension3;
                    impl4.onElevationsChanged(impl4.elevation, impl4.hoveredFocusedTranslationZ, dimension3);
                }
                getImpl().showMotionSpec = createFromAttribute;
                getImpl().hideMotionSpec = createFromAttribute2;
                getImpl().ensureMinTouchTargetSize = z;
                setScaleType(ImageView.ScaleType.MATRIX);
            }
        }
