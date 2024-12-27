package com.android.server.desktopmode;

import android.content.Context;
import android.view.Display;
import android.widget.Toast;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ToastManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ Context f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ ToastManager$$ExternalSyntheticLambda0(int i, Context context, String str, boolean z) {
        this.f$0 = context;
        this.f$1 = str;
        this.f$2 = i;
        this.f$3 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        Display display;
        Context context = this.f$0;
        final String str = this.f$1;
        int i = this.f$2;
        boolean z2 = this.f$3;
        final Toast makeText = Toast.makeText(context, str, i);
        if (z2) {
            makeText.semSetPreferredDisplayType(0);
        }
        makeText.addCallback(
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x001a: INVOKE 
              (r2v1 'makeText' android.widget.Toast)
              (wrap:android.widget.Toast$Callback:0x0017: CONSTRUCTOR (r2v1 'makeText' android.widget.Toast A[DONT_INLINE]), (r1v0 'str' java.lang.String A[DONT_INLINE]) A[MD:(android.widget.Toast, java.lang.String):void (m), WRAPPED] call: com.android.server.desktopmode.ToastManager.1.<init>(android.widget.Toast, java.lang.String):void type: CONSTRUCTOR)
             VIRTUAL call: android.widget.Toast.addCallback(android.widget.Toast$Callback):void A[MD:(android.widget.Toast$Callback):void (c)] in method: com.android.server.desktopmode.ToastManager$$ExternalSyntheticLambda0.run():void, file: classes.dex
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.server.desktopmode.ToastManager, state: NOT_LOADED
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
            	... 15 more
            */
        /*
            this = this;
            android.content.Context r0 = r8.f$0
            java.lang.String r1 = r8.f$1
            int r2 = r8.f$2
            boolean r8 = r8.f$3
            java.lang.String r3 = "sToasts added, sToasts="
            android.widget.Toast r2 = android.widget.Toast.makeText(r0, r1, r2)
            r4 = 0
            if (r8 == 0) goto L15
            r2.semSetPreferredDisplayType(r4)
        L15:
            com.android.server.desktopmode.ToastManager$1 r8 = new com.android.server.desktopmode.ToastManager$1
            r8.<init>(r2, r1)
            r2.addCallback(r8)
            java.util.List r8 = com.android.server.desktopmode.ToastManager.sToasts
            monitor-enter(r8)
            r5 = r8
            java.util.ArrayList r5 = (java.util.ArrayList) r5     // Catch: java.lang.Throwable -> L3c
            r5.add(r2)     // Catch: java.lang.Throwable -> L3c
            boolean r5 = com.samsung.android.desktopmode.DesktopModeFeature.DEBUG     // Catch: java.lang.Throwable -> L3c
            if (r5 == 0) goto L3e
            java.lang.String r6 = "[DMS]ToastManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3c
            r7.<init>(r3)     // Catch: java.lang.Throwable -> L3c
            r7.append(r8)     // Catch: java.lang.Throwable -> L3c
            java.lang.String r3 = r7.toString()     // Catch: java.lang.Throwable -> L3c
            com.android.server.desktopmode.Log.d(r6, r3)     // Catch: java.lang.Throwable -> L3c
            goto L3e
        L3c:
            r0 = move-exception
            goto La3
        L3e:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L3c
            boolean r8 = com.samsung.android.desktopmode.DesktopModeFeature.IS_TABLET
            if (r8 == 0) goto L44
            goto L85
        L44:
            android.view.Display r8 = r0.getDisplay()
            if (r8 == 0) goto L51
            int r8 = r8.getDisplayId()
            if (r8 == 0) goto L51
            goto L85
        L51:
            com.samsung.android.cover.CoverManager r8 = com.android.server.desktopmode.ToastManager.sCoverManager
            if (r8 != 0) goto L5c
            com.samsung.android.cover.CoverManager r8 = new com.samsung.android.cover.CoverManager
            r8.<init>(r0)
            com.android.server.desktopmode.ToastManager.sCoverManager = r8
        L5c:
            com.samsung.android.cover.CoverManager r8 = com.android.server.desktopmode.ToastManager.sCoverManager
            com.samsung.android.cover.CoverState r8 = r8.getCoverState()
            if (r8 == 0) goto L85
            boolean r3 = r8.attached
            r6 = 1
            if (r3 != r6) goto L85
            boolean r3 = r8.switchState
            if (r3 != 0) goto L85
            int r8 = r8.getType()
            r3 = 8
            if (r8 != r3) goto L85
            android.content.res.Resources r8 = r0.getResources()
            r0 = 17105223(0x1050147, float:2.4429158E-38)
            int r8 = r8.getDimensionPixelOffset(r0)
            r0 = 80
            r2.setGravity(r0, r4, r8)
        L85:
            r2.show()
            if (r5 == 0) goto La2
            java.lang.String r8 = "[DMS]ToastManager"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "Toast.show("
            r0.<init>(r2)
            r0.append(r1)
            java.lang.String r1 = ")"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.android.server.desktopmode.Log.d(r8, r0)
        La2:
            return
        La3:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L3c
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.desktopmode.ToastManager$$ExternalSyntheticLambda0.run():void");
    }
}
