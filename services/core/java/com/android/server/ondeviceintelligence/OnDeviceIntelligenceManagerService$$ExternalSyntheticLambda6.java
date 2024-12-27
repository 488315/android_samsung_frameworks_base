package com.android.server.ondeviceintelligence;

import android.os.IBinder;
import com.android.internal.infra.AndroidFuture;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda6 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OnDeviceIntelligenceManagerService f$0;
    public final /* synthetic */ AndroidFuture f$1;

    public /* synthetic */ OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda6(OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService, AndroidFuture androidFuture, int i) {
        this.$r8$classId = i;
        this.f$0 = onDeviceIntelligenceManagerService;
        this.f$1 = androidFuture;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService = this.f$0;
                AndroidFuture androidFuture = this.f$1;
                final IBinder iBinder = (IBinder) obj;
                Throwable th = (Throwable) obj2;
                onDeviceIntelligenceManagerService.getClass();
                if (th == null) {
                    androidFuture.complete(
                    /*  JADX ERROR: Method code generation error
                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x003c: INVOKE 
                          (r1v1 'androidFuture' com.android.internal.infra.AndroidFuture)
                          (wrap:android.app.ondeviceintelligence.IProcessingSignal$Stub:0x0039: CONSTRUCTOR (r2v1 'iBinder' android.os.IBinder A[DONT_INLINE]) A[MD:(android.os.IBinder):void (m), WRAPPED] call: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.9.<init>(android.os.IBinder):void type: CONSTRUCTOR)
                         VIRTUAL call: com.android.internal.infra.AndroidFuture.complete(java.lang.Object):boolean in method: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda6.accept(java.lang.Object, java.lang.Object):void, file: classes2.dex
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
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService, state: NOT_LOADED
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
                        	... 27 more
                        */
                    /*
                        this = this;
                        int r0 = r1.$r8$classId
                        switch(r0) {
                            case 0: goto L26;
                            default: goto L5;
                        }
                    L5:
                        com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService r0 = r1.f$0
                        com.android.internal.infra.AndroidFuture r1 = r1.f$1
                        android.os.IBinder r2 = (android.os.IBinder) r2
                        java.lang.Throwable r3 = (java.lang.Throwable) r3
                        r0.getClass()
                        if (r3 == 0) goto L1d
                        java.lang.String r2 = "OnDeviceIntelligenceManagerService"
                        java.lang.String r0 = "Error forwarding ICancellationSignal to manager layer"
                        android.util.Log.e(r2, r0, r3)
                        r1.completeExceptionally(r3)
                        goto L25
                    L1d:
                        com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$8 r3 = new com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$8
                        r3.<init>(r2)
                        r1.complete(r3)
                    L25:
                        return
                    L26:
                        com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService r0 = r1.f$0
                        com.android.internal.infra.AndroidFuture r1 = r1.f$1
                        android.os.IBinder r2 = (android.os.IBinder) r2
                        java.lang.Throwable r3 = (java.lang.Throwable) r3
                        r0.getClass()
                        if (r3 == 0) goto L37
                        r1.completeExceptionally(r3)
                        goto L3f
                    L37:
                        com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$9 r3 = new com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$9
                        r3.<init>(r2)
                        r1.complete(r3)
                    L3f:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda6.accept(java.lang.Object, java.lang.Object):void");
                }
            }
