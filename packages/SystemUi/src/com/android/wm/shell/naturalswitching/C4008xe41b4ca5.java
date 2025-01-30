package com.android.wm.shell.naturalswitching;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.naturalswitching.NaturalSwitchingChanger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$PipToPipChanger$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C4008xe41b4ca5 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NaturalSwitchingChanger f$0;

    public /* synthetic */ C4008xe41b4ca5(NaturalSwitchingChanger naturalSwitchingChanger, int i) {
        this.$r8$classId = i;
        this.f$0 = naturalSwitchingChanger;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        int i = this.$r8$classId;
        NaturalSwitchingChanger naturalSwitchingChanger = this.f$0;
        switch (i) {
            case 0:
                ((NaturalSwitchingChanger.PipToPipChanger) naturalSwitchingChanger).mHideLayoutCallback.accept(Boolean.FALSE);
                break;
            case 1:
                ((NaturalSwitchingChanger.FreeformToFreeformChanger) naturalSwitchingChanger).mHideLayoutCallback.accept(Boolean.FALSE);
                break;
            case 2:
                ((NaturalSwitchingChanger.FullToFreeformChanger) naturalSwitchingChanger).mHideLayoutCallback.accept(Boolean.FALSE);
                break;
            default:
                final NaturalSwitchingChanger.SplitToSplitChanger splitToSplitChanger = (NaturalSwitchingChanger.SplitToSplitChanger) naturalSwitchingChanger;
                splitToSplitChanger.mSplitController.updateSurfaceBoundsForNS(transaction);
                int stageTypeAtPosition = splitToSplitChanger.mSplitController.getStageTypeAtPosition(splitToSplitChanger.mToPosition);
                Rect stageBounds = splitToSplitChanger.mSplitController.getStageBounds(stageTypeAtPosition);
                final SurfaceControl targetLeash = splitToSplitChanger.mSplitController.getTargetLeash(stageTypeAtPosition);
                final SurfaceControl.Transaction transaction2 = splitToSplitChanger.mTransaction;
                final Rect rect = new Rect(splitToSplitChanger.mDropBounds);
                Rect rect2 = new Rect(stageBounds);
                final Rect rect3 = new Rect();
                final float f = rect2.left - rect.left;
                final float f2 = rect2.top - rect.top;
                final float width = rect2.width() - rect.width();
                final float height = rect2.height() - rect.height();
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger$$ExternalSyntheticLambda0
                    public final /* synthetic */ float f$6 = 0.0f;
                    public final /* synthetic */ float f$7 = 0.0f;

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        SurfaceControl surfaceControl = targetLeash;
                        Rect rect4 = rect;
                        float f3 = f;
                        float f4 = f2;
                        float f5 = width;
                        float f6 = height;
                        float f7 = this.f$6;
                        float f8 = this.f$7;
                        SurfaceControl.Transaction transaction3 = transaction2;
                        Rect rect5 = rect3;
                        if (surfaceControl == null) {
                            return;
                        }
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        float f9 = (f3 * floatValue) + rect4.left;
                        float f10 = (f4 * floatValue) + rect4.top;
                        int width2 = (int) ((f5 * floatValue) + rect4.width());
                        int height2 = (int) ((f6 * floatValue) + rect4.height());
                        if (f7 == 0.0f && f8 == 0.0f) {
                            transaction3.setPosition(surfaceControl, f9, f10);
                            transaction3.setWindowCrop(surfaceControl, width2, height2);
                        } else {
                            int i2 = (int) (f7 * floatValue);
                            int i3 = (int) (floatValue * f8);
                            transaction3.setPosition(surfaceControl, f9 + i2, f10 + i3);
                            rect5.set(0, 0, width2, height2);
                            rect5.offsetTo(-i2, -i3);
                            transaction3.setCrop(surfaceControl, rect5);
                        }
                        transaction3.apply();
                    }
                });
                ofFloat.addListener(
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x008c: INVOKE 
                      (r12v9 'ofFloat' android.animation.ValueAnimator)
                      (wrap:android.animation.AnimatorListenerAdapter:0x0089: CONSTRUCTOR 
                      (r11v2 'splitToSplitChanger' com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger A[DONT_INLINE])
                     A[MD:(com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger):void (m), WRAPPED] (LINE:138) call: com.android.wm.shell.naturalswitching.NaturalSwitchingChanger.SplitToSplitChanger.1.<init>(com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger):void type: CONSTRUCTOR)
                     VIRTUAL call: android.animation.ValueAnimator.addListener(android.animation.Animator$AnimatorListener):void A[MD:(android.animation.Animator$AnimatorListener):void (c)] (LINE:141) in method: com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$PipToPipChanger$$ExternalSyntheticLambda0.runWithTransaction(android.view.SurfaceControl$Transaction):void, file: classes2.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                    	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:267)
                    	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
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
                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.wm.shell.naturalswitching.NaturalSwitchingChanger, state: NOT_LOADED
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
                    	... 41 more
                    */
                /*
                    this = this;
                    int r0 = r11.$r8$classId
                    com.android.wm.shell.naturalswitching.NaturalSwitchingChanger r11 = r11.f$0
                    switch(r0) {
                        case 0: goto L1c;
                        case 1: goto L12;
                        case 2: goto L8;
                        default: goto L7;
                    }
                L7:
                    goto L26
                L8:
                    com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$FullToFreeformChanger r11 = (com.android.wm.shell.naturalswitching.NaturalSwitchingChanger.FullToFreeformChanger) r11
                    java.util.function.Consumer r11 = r11.mHideLayoutCallback
                    java.lang.Boolean r12 = java.lang.Boolean.FALSE
                    r11.accept(r12)
                    return
                L12:
                    com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$FreeformToFreeformChanger r11 = (com.android.wm.shell.naturalswitching.NaturalSwitchingChanger.FreeformToFreeformChanger) r11
                    java.util.function.Consumer r11 = r11.mHideLayoutCallback
                    java.lang.Boolean r12 = java.lang.Boolean.FALSE
                    r11.accept(r12)
                    return
                L1c:
                    com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$PipToPipChanger r11 = (com.android.wm.shell.naturalswitching.NaturalSwitchingChanger.PipToPipChanger) r11
                    java.util.function.Consumer r11 = r11.mHideLayoutCallback
                    java.lang.Boolean r12 = java.lang.Boolean.FALSE
                    r11.accept(r12)
                    return
                L26:
                    com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger r11 = (com.android.wm.shell.naturalswitching.NaturalSwitchingChanger.SplitToSplitChanger) r11
                    com.android.wm.shell.splitscreen.SplitScreenController r0 = r11.mSplitController
                    r0.updateSurfaceBoundsForNS(r12)
                    com.android.wm.shell.splitscreen.SplitScreenController r12 = r11.mSplitController
                    int r0 = r11.mToPosition
                    int r12 = r12.getStageTypeAtPosition(r0)
                    com.android.wm.shell.splitscreen.SplitScreenController r0 = r11.mSplitController
                    android.graphics.Rect r0 = r0.getStageBounds(r12)
                    com.android.wm.shell.splitscreen.SplitScreenController r1 = r11.mSplitController
                    android.view.SurfaceControl r3 = r1.getTargetLeash(r12)
                    android.view.SurfaceControl$Transaction r9 = r11.mTransaction
                    android.graphics.Rect r4 = new android.graphics.Rect
                    android.graphics.Rect r12 = r11.mDropBounds
                    r4.<init>(r12)
                    android.graphics.Rect r12 = new android.graphics.Rect
                    r12.<init>(r0)
                    android.graphics.Rect r10 = new android.graphics.Rect
                    r10.<init>()
                    int r0 = r12.left
                    int r1 = r4.left
                    int r0 = r0 - r1
                    float r5 = (float) r0
                    int r0 = r12.top
                    int r1 = r4.top
                    int r0 = r0 - r1
                    float r6 = (float) r0
                    int r0 = r12.width()
                    int r1 = r4.width()
                    int r0 = r0 - r1
                    float r7 = (float) r0
                    int r12 = r12.height()
                    int r0 = r4.height()
                    int r12 = r12 - r0
                    float r8 = (float) r12
                    r12 = 2
                    float[] r12 = new float[r12]
                    r12 = {x009e: FILL_ARRAY_DATA , data: [0, 1065353216} // fill-array
                    android.animation.ValueAnimator r12 = android.animation.ValueAnimator.ofFloat(r12)
                    com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger$$ExternalSyntheticLambda0 r0 = new com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger$$ExternalSyntheticLambda0
                    r2 = r0
                    r2.<init>()
                    r12.addUpdateListener(r0)
                    com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger$1 r0 = new com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger$1
                    r0.<init>(r11)
                    r12.addListener(r0)
                    r12.start()
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.naturalswitching.C4008xe41b4ca5.runWithTransaction(android.view.SurfaceControl$Transaction):void");
            }
        }
