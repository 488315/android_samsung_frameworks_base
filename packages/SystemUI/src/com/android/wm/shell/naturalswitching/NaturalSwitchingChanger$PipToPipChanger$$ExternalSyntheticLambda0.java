package com.android.wm.shell.naturalswitching;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.naturalswitching.NaturalSwitchingChanger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NaturalSwitchingChanger$PipToPipChanger$$ExternalSyntheticLambda0 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NaturalSwitchingChanger f$0;

    public /* synthetic */ NaturalSwitchingChanger$PipToPipChanger$$ExternalSyntheticLambda0(NaturalSwitchingChanger naturalSwitchingChanger, int i) {
        this.$r8$classId = i;
        this.f$0 = naturalSwitchingChanger;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        NaturalSwitchingChanger naturalSwitchingChanger = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                ((NaturalSwitchingChanger.PipToPipChanger) naturalSwitchingChanger).mHideLayoutCallback.accept(Boolean.FALSE);
                break;
            case 1:
                ((NaturalSwitchingChanger.FreeformToFreeformChanger) naturalSwitchingChanger).mHideLayoutCallback.accept(Boolean.TRUE);
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
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger$$ExternalSyntheticLambda1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        SurfaceControl surfaceControl = targetLeash;
                        Rect rect4 = rect;
                        float f3 = f;
                        float f4 = f2;
                        float f5 = width;
                        float f6 = height;
                        SurfaceControl.Transaction transaction3 = transaction2;
                        if (surfaceControl == null) {
                            return;
                        }
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        float f7 = (f3 * floatValue) + rect4.left;
                        float f8 = (f4 * floatValue) + rect4.top;
                        transaction3.setPosition(surfaceControl, f7, f8);
                        transaction3.setWindowCrop(surfaceControl, (int) ((f5 * floatValue) + rect4.width()), (int) ((floatValue * f6) + rect4.height()));
                        transaction3.apply();
                    }
                });
                ofFloat.addListener(
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x006c: INVOKE 
                      (r11v14 'ofFloat' android.animation.ValueAnimator)
                      (wrap:android.animation.AnimatorListenerAdapter:0x0069: CONSTRUCTOR 
                      (r0v4 'splitToSplitChanger' com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger A[DONT_INLINE])
                     A[MD:(com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger):void (m), WRAPPED] (LINE:106) call: com.android.wm.shell.naturalswitching.NaturalSwitchingChanger.SplitToSplitChanger.1.<init>(com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger):void type: CONSTRUCTOR)
                     VIRTUAL call: android.animation.ValueAnimator.addListener(android.animation.Animator$AnimatorListener):void A[MD:(android.animation.Animator$AnimatorListener):void (c)] (LINE:109) in method: com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$PipToPipChanger$$ExternalSyntheticLambda0.runWithTransaction(android.view.SurfaceControl$Transaction):void, file: classes3.dex
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
                    com.android.wm.shell.naturalswitching.NaturalSwitchingChanger r0 = r11.f$0
                    int r11 = r11.$r8$classId
                    switch(r11) {
                        case 0: goto L87;
                        case 1: goto L7d;
                        case 2: goto L73;
                        default: goto L7;
                    }
                L7:
                    com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger r0 = (com.android.wm.shell.naturalswitching.NaturalSwitchingChanger.SplitToSplitChanger) r0
                    com.android.wm.shell.splitscreen.SplitScreenController r11 = r0.mSplitController
                    r11.updateSurfaceBoundsForNS(r12)
                    com.android.wm.shell.splitscreen.SplitScreenController r11 = r0.mSplitController
                    int r12 = r0.mToPosition
                    int r11 = r11.getStageTypeAtPosition(r12)
                    com.android.wm.shell.splitscreen.SplitScreenController r12 = r0.mSplitController
                    android.graphics.Rect r12 = r12.getStageBounds(r11)
                    com.android.wm.shell.splitscreen.SplitScreenController r1 = r0.mSplitController
                    android.view.SurfaceControl r3 = r1.getTargetLeash(r11)
                    android.view.SurfaceControl$Transaction r9 = r0.mTransaction
                    android.graphics.Rect r11 = r0.mDropBounds
                    android.graphics.Rect r4 = new android.graphics.Rect
                    r4.<init>(r11)
                    android.graphics.Rect r11 = new android.graphics.Rect
                    r11.<init>(r12)
                    android.graphics.Rect r10 = new android.graphics.Rect
                    r10.<init>()
                    int r12 = r11.left
                    int r1 = r4.left
                    int r12 = r12 - r1
                    float r5 = (float) r12
                    int r12 = r11.top
                    int r1 = r4.top
                    int r12 = r12 - r1
                    float r6 = (float) r12
                    int r12 = r11.width()
                    int r1 = r4.width()
                    int r12 = r12 - r1
                    float r7 = (float) r12
                    int r11 = r11.height()
                    int r12 = r4.height()
                    int r11 = r11 - r12
                    float r8 = (float) r11
                    r11 = 2
                    float[] r11 = new float[r11]
                    r11 = {x009c: FILL_ARRAY_DATA , data: [0, 1065353216} // fill-array
                    android.animation.ValueAnimator r11 = android.animation.ValueAnimator.ofFloat(r11)
                    com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger$$ExternalSyntheticLambda1 r2 = new com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger$$ExternalSyntheticLambda1
                    r2.<init>()
                    r11.addUpdateListener(r2)
                    com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger$1 r12 = new com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$SplitToSplitChanger$1
                    r12.<init>(r0)
                    r11.addListener(r12)
                    r11.start()
                    return
                L73:
                    com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$FullToFreeformChanger r0 = (com.android.wm.shell.naturalswitching.NaturalSwitchingChanger.FullToFreeformChanger) r0
                    com.android.wm.shell.naturalswitching.NaturalSwitchingLayout$$ExternalSyntheticLambda2 r11 = r0.mHideLayoutCallback
                    java.lang.Boolean r12 = java.lang.Boolean.FALSE
                    r11.accept(r12)
                    return
                L7d:
                    com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$FreeformToFreeformChanger r0 = (com.android.wm.shell.naturalswitching.NaturalSwitchingChanger.FreeformToFreeformChanger) r0
                    com.android.wm.shell.naturalswitching.NaturalSwitchingLayout$$ExternalSyntheticLambda2 r11 = r0.mHideLayoutCallback
                    java.lang.Boolean r12 = java.lang.Boolean.TRUE
                    r11.accept(r12)
                    return
                L87:
                    com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$PipToPipChanger r0 = (com.android.wm.shell.naturalswitching.NaturalSwitchingChanger.PipToPipChanger) r0
                    com.android.wm.shell.naturalswitching.NaturalSwitchingLayout$$ExternalSyntheticLambda2 r11 = r0.mHideLayoutCallback
                    java.lang.Boolean r12 = java.lang.Boolean.FALSE
                    r11.accept(r12)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$PipToPipChanger$$ExternalSyntheticLambda0.runWithTransaction(android.view.SurfaceControl$Transaction):void");
            }
        }
