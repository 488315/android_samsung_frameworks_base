package com.android.wm.shell.freeform;

import android.view.animation.Animation;

/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformContainerView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ FreeformContainerView f$0;

    @Override // java.lang.Runnable
    public final void run() {
        final FreeformContainerView freeformContainerView = this.f$0;
        float[] fArr = FreeformContainerView.TAIL_ICON_ALPHA_ARRAY;
        freeformContainerView.updateIconsPosition();
        Animation.AnimationListener animationListener = 
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0009: CONSTRUCTOR (r0v1 'animationListener' android.view.animation.Animation$AnimationListener) = (r12v1 'freeformContainerView' com.android.wm.shell.freeform.FreeformContainerView A[DONT_INLINE]) A[DECLARE_VAR, MD:(com.android.wm.shell.freeform.FreeformContainerView):void (m)] (LINE:10) call: com.android.wm.shell.freeform.FreeformContainerView.5.<init>(com.android.wm.shell.freeform.FreeformContainerView):void type: CONSTRUCTOR in method: com.android.wm.shell.freeform.FreeformContainerView$$ExternalSyntheticLambda0.run():void, file: classes3.dex
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.wm.shell.freeform.FreeformContainerView, state: NOT_LOADED
            	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
            	... 15 more
            */
        /*
            this = this;
            com.android.wm.shell.freeform.FreeformContainerView r12 = r12.f$0
            float[] r0 = com.android.wm.shell.freeform.FreeformContainerView.TAIL_ICON_ALPHA_ARRAY
            r12.updateIconsPosition()
            com.android.wm.shell.freeform.FreeformContainerView$5 r0 = new com.android.wm.shell.freeform.FreeformContainerView$5
            r0.<init>(r12)
            android.graphics.Rect r1 = r12.mTmpBounds
            r12.getPointerViewBounds(r1)
            android.view.ViewGroup r1 = r12.mPointerGroupView
            android.graphics.Rect r2 = r12.mTmpBounds
            float r9 = r2.exactCenterX()
            android.graphics.Rect r2 = r12.mTmpBounds
            float r11 = r2.exactCenterY()
            android.view.animation.AnimationSet r2 = new android.view.animation.AnimationSet
            r3 = 0
            r2.<init>(r3)
            android.view.animation.ScaleAnimation r3 = new android.view.animation.ScaleAnimation
            r6 = 1056964608(0x3f000000, float:0.5)
            r7 = 1065353216(0x3f800000, float:1.0)
            r4 = 1056964608(0x3f000000, float:0.5)
            r5 = 1065353216(0x3f800000, float:1.0)
            r8 = 0
            r10 = 0
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11)
            r4 = 350(0x15e, double:1.73E-321)
            r3.setDuration(r4)
            android.view.animation.OvershootInterpolator r6 = new android.view.animation.OvershootInterpolator
            r7 = 1077936128(0x40400000, float:3.0)
            r6.<init>(r7)
            r3.setInterpolator(r6)
            android.view.animation.AlphaAnimation r6 = new android.view.animation.AlphaAnimation
            r7 = 1036831949(0x3dcccccd, float:0.1)
            r8 = 1065353216(0x3f800000, float:1.0)
            r6.<init>(r7, r8)
            r6.setDuration(r4)
            android.view.animation.PathInterpolator r4 = com.samsung.android.util.InterpolatorUtils.SINE_IN_OUT_70
            r6.setInterpolator(r4)
            r2.addAnimation(r3)
            r2.addAnimation(r6)
            r3 = 100
            r2.setStartOffset(r3)
            r2.setAnimationListener(r0)
            r1.startAnimation(r2)
            r0 = 1
            r12.mPointerSettleDownEffectRequested = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.freeform.FreeformContainerView$$ExternalSyntheticLambda0.run():void");
    }
}
