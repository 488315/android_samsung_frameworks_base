package com.android.app.viewcapture;

import android.view.View;
import com.android.app.viewcapture.ViewCapture;

/* loaded from: classes.dex */
public final /* synthetic */ class ViewCapture$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ViewCapture.WindowListener f$0;

    public /* synthetic */ ViewCapture$$ExternalSyntheticLambda5(ViewCapture.WindowListener windowListener, int i) {
        this.$r8$classId = i;
        this.f$0 = windowListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final ViewCapture.WindowListener windowListener = this.f$0;
        switch (i) {
            case 0:
                LooperExecutor looperExecutor = ViewCapture.MAIN_EXECUTOR;
                View view = windowListener.mRoot;
                if (view != null) {
                    view.getViewTreeObserver().removeOnDrawListener(windowListener);
                    windowListener.mRoot = null;
                    break;
                }
                break;
            case 1:
                if (!windowListener.mRoot.isAttachedToWindow()) {
                    windowListener.mRoot.addOnAttachStateChangeListener(
                    /*  JADX ERROR: Method code generation error
                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0037: INVOKE 
                          (wrap:android.view.View:0x0030: IGET (r2v1 'windowListener' com.android.app.viewcapture.ViewCapture$WindowListener) A[WRAPPED] (LINE:49) com.android.app.viewcapture.ViewCapture.WindowListener.mRoot android.view.View)
                          (wrap:android.view.View$OnAttachStateChangeListener:0x0034: CONSTRUCTOR (r2v1 'windowListener' com.android.app.viewcapture.ViewCapture$WindowListener A[DONT_INLINE]) A[MD:(com.android.app.viewcapture.ViewCapture$WindowListener):void (m), WRAPPED] (LINE:53) call: com.android.app.viewcapture.ViewCapture.WindowListener.1.<init>(com.android.app.viewcapture.ViewCapture$WindowListener):void type: CONSTRUCTOR)
                         VIRTUAL call: android.view.View.addOnAttachStateChangeListener(android.view.View$OnAttachStateChangeListener):void A[MD:(android.view.View$OnAttachStateChangeListener):void (c)] (LINE:56) in method: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda5.run():void, file: classes.dex
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
                        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.app.viewcapture.ViewCapture, state: NOT_LOADED
                        	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
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
                        	... 27 more
                        */
                    /*
                        this = this;
                        int r0 = r2.$r8$classId
                        com.android.app.viewcapture.ViewCapture$WindowListener r2 = r2.f$0
                        switch(r0) {
                            case 0: goto L3b;
                            case 1: goto L13;
                            default: goto L7;
                        }
                    L7:
                        android.view.View r0 = r2.mRoot
                        if (r0 == 0) goto L12
                        android.view.ViewTreeObserver r0 = r0.getViewTreeObserver()
                        r0.removeOnDrawListener(r2)
                    L12:
                        return
                    L13:
                        android.view.View r0 = r2.mRoot
                        boolean r0 = r0.isAttachedToWindow()
                        if (r0 == 0) goto L30
                        android.view.View r0 = r2.mRoot
                        if (r0 == 0) goto L3a
                        android.view.ViewTreeObserver r0 = r0.getViewTreeObserver()
                        r0.removeOnDrawListener(r2)
                        android.view.View r0 = r2.mRoot
                        android.view.ViewTreeObserver r0 = r0.getViewTreeObserver()
                        r0.addOnDrawListener(r2)
                        goto L3a
                    L30:
                        android.view.View r0 = r2.mRoot
                        com.android.app.viewcapture.ViewCapture$WindowListener$1 r1 = new com.android.app.viewcapture.ViewCapture$WindowListener$1
                        r1.<init>(r2)
                        r0.addOnAttachStateChangeListener(r1)
                    L3a:
                        return
                    L3b:
                        com.android.app.viewcapture.LooperExecutor r0 = com.android.app.viewcapture.ViewCapture.MAIN_EXECUTOR
                        android.view.View r0 = r2.mRoot
                        if (r0 == 0) goto L4b
                        android.view.ViewTreeObserver r0 = r0.getViewTreeObserver()
                        r0.removeOnDrawListener(r2)
                        r0 = 0
                        r2.mRoot = r0
                    L4b:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda5.run():void");
                }
            }
