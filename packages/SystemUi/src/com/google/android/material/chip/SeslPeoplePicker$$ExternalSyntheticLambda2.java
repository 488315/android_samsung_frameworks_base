package com.google.android.material.chip;

import android.util.Log;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SeslPeoplePicker$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SeslPeoplePicker f$0;

    public /* synthetic */ SeslPeoplePicker$$ExternalSyntheticLambda2(SeslPeoplePicker seslPeoplePicker, int i) {
        this.$r8$classId = i;
        this.f$0 = seslPeoplePicker;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SeslPeoplePicker seslPeoplePicker = this.f$0;
                int i = SeslPeoplePicker.$r8$clinit;
                seslPeoplePicker.updateFloatWhenExpanded();
                break;
            case 1:
                final SeslPeoplePicker seslPeoplePicker2 = this.f$0;
                if (seslPeoplePicker2.mChipGroup.getTotalWidth() > seslPeoplePicker2.getWidth()) {
                    final int integer = seslPeoplePicker2.getContext().getResources().getInteger(R.integer.sesl_chip_default_anim_duration);
                    SeslExpandableContainer seslExpandableContainer = seslPeoplePicker2.mContainer;
                    seslExpandableContainer.mFloatChangeAllowed = false;
                    seslExpandableContainer.mExpansionButton.setFloated(true);
                    if (seslPeoplePicker2.mIsRtl) {
                        seslPeoplePicker2.mContainer.mExpansionButton.setVisibility(0);
                        seslPeoplePicker2.mContainer.mExpansionButton.setFloated(true);
                        SeslExpandableContainer seslExpandableContainer2 = seslPeoplePicker2.mContainer;
                        boolean z = seslExpandableContainer2.mExpanded;
                        if (z) {
                            Log.w("SeslExpandableContainer", "cannot scroll if container is expanded");
                        } else if (z) {
                            Log.w("SeslExpandableContainer", "cannot scroll if container is expanded");
                        } else {
                            seslExpandableContainer2.mScrollView.post(new SeslExpandableContainer$$ExternalSyntheticLambda3(seslExpandableContainer2, integer, 0, 0));
                        }
                    } else {
                        final SeslExpandableContainer seslExpandableContainer3 = seslPeoplePicker2.mContainer;
                        if (seslExpandableContainer3.mExpanded) {
                            Log.w("SeslExpandableContainer", "cannot scroll if container is expanded");
                        } else {
                            seslExpandableContainer3.post(new Runnable() { // from class: com.google.android.material.chip.SeslExpandableContainer$$ExternalSyntheticLambda4
                                public final /* synthetic */ int f$2 = 0;

                                @Override // java.lang.Runnable
                                public final void run() {
                                    SeslExpandableContainer seslExpandableContainer4 = SeslExpandableContainer.this;
                                    int i2 = integer;
                                    int i3 = this.f$2;
                                    int i4 = SeslExpandableContainer.$r8$clinit;
                                    int scrollContentsWidth = seslExpandableContainer4.getScrollContentsWidth();
                                    if (seslExpandableContainer4.mExpanded) {
                                        Log.w("SeslExpandableContainer", "cannot scroll if container is expanded");
                                    } else {
                                        seslExpandableContainer4.mScrollView.post(new SeslExpandableContainer$$ExternalSyntheticLambda3(seslExpandableContainer4, i2, scrollContentsWidth, i3));
                                    }
                                }
                            });
                        }
                    }
                    final long j = integer;
                    
                    /*  JADX ERROR: Method code generation error
                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0082: INVOKE 
                          (wrap:android.os.CountDownTimer:0x007f: CONSTRUCTOR 
                          (r2v0 'seslPeoplePicker2' com.google.android.material.chip.SeslPeoplePicker A[DONT_INLINE])
                          (r5v1 'j' long A[DONT_INLINE])
                          (r5v1 'j' long A[DONT_INLINE])
                         A[MD:(com.google.android.material.chip.SeslPeoplePicker, long, long):void (m), WRAPPED] (LINE:128) call: com.google.android.material.chip.SeslPeoplePicker.1.<init>(com.google.android.material.chip.SeslPeoplePicker, long, long):void type: CONSTRUCTOR)
                         VIRTUAL call: android.os.CountDownTimer.start():android.os.CountDownTimer A[MD:():android.os.CountDownTimer (c)] (LINE:131) in method: com.google.android.material.chip.SeslPeoplePicker$$ExternalSyntheticLambda2.run():void, file: classes2.dex
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
                        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.google.android.material.chip.SeslPeoplePicker, state: NOT_LOADED
                        	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:305)
                        	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                        	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:97)
                        	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:878)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                        	... 47 more
                        */
                    /*
                        this = this;
                        int r0 = r7.$r8$classId
                        switch(r0) {
                            case 0: goto L86;
                            case 1: goto Lf;
                            case 2: goto L7;
                            default: goto L5;
                        }
                    L5:
                        goto L8e
                    L7:
                        com.google.android.material.chip.SeslPeoplePicker r7 = r7.f$0
                        int r0 = com.google.android.material.chip.SeslPeoplePicker.$r8$clinit
                        r7.updateFloatWhenExpanded()
                        return
                    Lf:
                        com.google.android.material.chip.SeslPeoplePicker r2 = r7.f$0
                        com.google.android.material.chip.SeslChipGroup r7 = r2.mChipGroup
                        int r7 = r7.getTotalWidth()
                        int r0 = r2.getWidth()
                        if (r7 <= r0) goto L85
                        android.content.Context r7 = r2.getContext()
                        android.content.res.Resources r7 = r7.getResources()
                        r0 = 2131427571(0x7f0b00f3, float:1.8476762E38)
                        int r7 = r7.getInteger(r0)
                        com.google.android.material.chip.SeslExpandableContainer r0 = r2.mContainer
                        r1 = 0
                        r0.mFloatChangeAllowed = r1
                        com.google.android.material.chip.SeslExpansionButton r0 = r0.mExpansionButton
                        r3 = 1
                        r0.setFloated(r3)
                        boolean r0 = r2.mIsRtl
                        java.lang.String r4 = "SeslExpandableContainer"
                        java.lang.String r5 = "cannot scroll if container is expanded"
                        if (r0 == 0) goto L68
                        com.google.android.material.chip.SeslExpandableContainer r0 = r2.mContainer
                        com.google.android.material.chip.SeslExpansionButton r0 = r0.mExpansionButton
                        r0.setVisibility(r1)
                        com.google.android.material.chip.SeslExpandableContainer r0 = r2.mContainer
                        com.google.android.material.chip.SeslExpansionButton r0 = r0.mExpansionButton
                        r0.setFloated(r3)
                        com.google.android.material.chip.SeslExpandableContainer r0 = r2.mContainer
                        boolean r3 = r0.mExpanded
                        if (r3 != 0) goto L64
                        if (r3 != 0) goto L60
                        android.widget.HorizontalScrollView r3 = r0.mScrollView
                        com.google.android.material.chip.SeslExpandableContainer$$ExternalSyntheticLambda3 r4 = new com.google.android.material.chip.SeslExpandableContainer$$ExternalSyntheticLambda3
                        r4.<init>(r0, r7, r1, r1)
                        r3.post(r4)
                        goto L7a
                    L60:
                        android.util.Log.w(r4, r5)
                        goto L7a
                    L64:
                        android.util.Log.w(r4, r5)
                        goto L7a
                    L68:
                        com.google.android.material.chip.SeslExpandableContainer r0 = r2.mContainer
                        boolean r1 = r0.mExpanded
                        if (r1 != 0) goto L77
                        com.google.android.material.chip.SeslExpandableContainer$$ExternalSyntheticLambda4 r1 = new com.google.android.material.chip.SeslExpandableContainer$$ExternalSyntheticLambda4
                        r1.<init>()
                        r0.post(r1)
                        goto L7a
                    L77:
                        android.util.Log.w(r4, r5)
                    L7a:
                        com.google.android.material.chip.SeslPeoplePicker$1 r0 = new com.google.android.material.chip.SeslPeoplePicker$1
                        long r5 = (long) r7
                        r1 = r0
                        r3 = r5
                        r1.<init>(r2, r3, r5)
                        r0.start()
                    L85:
                        return
                    L86:
                        com.google.android.material.chip.SeslPeoplePicker r7 = r7.f$0
                        int r0 = com.google.android.material.chip.SeslPeoplePicker.$r8$clinit
                        r7.updateFloatWhenExpanded()
                        return
                    L8e:
                        com.google.android.material.chip.SeslPeoplePicker r7 = r7.f$0
                        int r0 = com.google.android.material.chip.SeslPeoplePicker.C42574.$r8$clinit
                        int r0 = com.google.android.material.chip.SeslPeoplePicker.$r8$clinit
                        r7.updateFloatWhenExpanded()
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.SeslPeoplePicker$$ExternalSyntheticLambda2.run():void");
                }
            }
