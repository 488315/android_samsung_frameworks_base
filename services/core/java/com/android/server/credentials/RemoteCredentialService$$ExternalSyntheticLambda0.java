package com.android.server.credentials;

import android.os.Binder;
import android.service.credentials.BeginCreateCredentialRequest;
import android.service.credentials.BeginGetCredentialRequest;
import android.service.credentials.ClearCredentialStateRequest;
import android.service.credentials.ICredentialProviderService;
import com.android.internal.infra.ServiceConnector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public final /* synthetic */ class RemoteCredentialService$$ExternalSyntheticLambda0 implements ServiceConnector.Job {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ RemoteCredentialService f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ AtomicReference f$2;
    public final /* synthetic */ AtomicReference f$3;

    public /* synthetic */ RemoteCredentialService$$ExternalSyntheticLambda0(RemoteCredentialService remoteCredentialService, BeginCreateCredentialRequest beginCreateCredentialRequest, AtomicReference atomicReference, AtomicReference atomicReference2) {
        this.f$0 = remoteCredentialService;
        this.f$1 = beginCreateCredentialRequest;
        this.f$2 = atomicReference;
        this.f$3 = atomicReference2;
    }

    public /* synthetic */ RemoteCredentialService$$ExternalSyntheticLambda0(RemoteCredentialService remoteCredentialService, BeginGetCredentialRequest beginGetCredentialRequest, AtomicReference atomicReference, AtomicReference atomicReference2) {
        this.f$0 = remoteCredentialService;
        this.f$1 = beginGetCredentialRequest;
        this.f$2 = atomicReference;
        this.f$3 = atomicReference2;
    }

    public /* synthetic */ RemoteCredentialService$$ExternalSyntheticLambda0(RemoteCredentialService remoteCredentialService, ClearCredentialStateRequest clearCredentialStateRequest, AtomicReference atomicReference, AtomicReference atomicReference2) {
        this.f$0 = remoteCredentialService;
        this.f$1 = clearCredentialStateRequest;
        this.f$2 = atomicReference;
        this.f$3 = atomicReference2;
    }

    public final Object run(Object obj) {
        long clearCallingIdentity;
        switch (this.$r8$classId) {
            case 0:
                final RemoteCredentialService remoteCredentialService = this.f$0;
                BeginCreateCredentialRequest beginCreateCredentialRequest = (BeginCreateCredentialRequest) this.f$1;
                final AtomicReference atomicReference = this.f$2;
                final AtomicReference atomicReference2 = this.f$3;
                ICredentialProviderService iCredentialProviderService = (ICredentialProviderService) obj;
                int i = RemoteCredentialService.$r8$clinit;
                remoteCredentialService.getClass();
                final CompletableFuture completableFuture = new CompletableFuture();
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    iCredentialProviderService.onBeginCreateCredential(beginCreateCredentialRequest, 
                    /*  JADX ERROR: Method code generation error
                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x007a: INVOKE 
                          (r8v1 'iCredentialProviderService' android.service.credentials.ICredentialProviderService)
                          (r1v1 'beginCreateCredentialRequest' android.service.credentials.BeginCreateCredentialRequest)
                          (wrap:android.service.credentials.IBeginCreateCredentialCallback$Stub:0x0077: CONSTRUCTOR 
                          (r0v1 'remoteCredentialService' com.android.server.credentials.RemoteCredentialService A[DONT_INLINE])
                          (r3v1 'completableFuture' java.util.concurrent.CompletableFuture A[DONT_INLINE])
                          (r2v0 'atomicReference' java.util.concurrent.atomic.AtomicReference A[DONT_INLINE])
                          (r7v1 'atomicReference2' java.util.concurrent.atomic.AtomicReference A[DONT_INLINE])
                         A[Catch: all -> 0x0081, MD:(com.android.server.credentials.RemoteCredentialService, java.util.concurrent.CompletableFuture, java.util.concurrent.atomic.AtomicReference, java.util.concurrent.atomic.AtomicReference):void (m), WRAPPED] call: com.android.server.credentials.RemoteCredentialService.2.<init>(com.android.server.credentials.RemoteCredentialService, java.util.concurrent.CompletableFuture, java.util.concurrent.atomic.AtomicReference, java.util.concurrent.atomic.AtomicReference):void type: CONSTRUCTOR)
                         INTERFACE call: android.service.credentials.ICredentialProviderService.onBeginCreateCredential(android.service.credentials.BeginCreateCredentialRequest, android.service.credentials.IBeginCreateCredentialCallback):void A[Catch: all -> 0x0081, TRY_LEAVE] in method: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda0.run(java.lang.Object):java.lang.Object, file: classes.dex
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                        	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                        	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:317)
                        	at jadx.core.dex.regions.TryCatchRegion.generate(TryCatchRegion.java:85)
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
                        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.server.credentials.RemoteCredentialService, state: NOT_LOADED
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
                        int r0 = r7.$r8$classId
                        switch(r0) {
                            case 0: goto L5b;
                            case 1: goto L30;
                            default: goto L5;
                        }
                    L5:
                        com.android.server.credentials.RemoteCredentialService r0 = r7.f$0
                        java.lang.Object r1 = r7.f$1
                        android.service.credentials.ClearCredentialStateRequest r1 = (android.service.credentials.ClearCredentialStateRequest) r1
                        java.util.concurrent.atomic.AtomicReference r2 = r7.f$2
                        java.util.concurrent.atomic.AtomicReference r7 = r7.f$3
                        android.service.credentials.ICredentialProviderService r8 = (android.service.credentials.ICredentialProviderService) r8
                        int r3 = com.android.server.credentials.RemoteCredentialService.$r8$clinit
                        r0.getClass()
                        java.util.concurrent.CompletableFuture r3 = new java.util.concurrent.CompletableFuture
                        r3.<init>()
                        long r4 = android.os.Binder.clearCallingIdentity()
                        com.android.server.credentials.RemoteCredentialService$3 r6 = new com.android.server.credentials.RemoteCredentialService$3     // Catch: java.lang.Throwable -> L2b
                        r6.<init>(r0, r3, r2, r7)     // Catch: java.lang.Throwable -> L2b
                        r8.onClearCredentialState(r1, r6)     // Catch: java.lang.Throwable -> L2b
                        android.os.Binder.restoreCallingIdentity(r4)
                        return r3
                    L2b:
                        r7 = move-exception
                        android.os.Binder.restoreCallingIdentity(r4)
                        throw r7
                    L30:
                        com.android.server.credentials.RemoteCredentialService r0 = r7.f$0
                        java.lang.Object r1 = r7.f$1
                        android.service.credentials.BeginGetCredentialRequest r1 = (android.service.credentials.BeginGetCredentialRequest) r1
                        java.util.concurrent.atomic.AtomicReference r2 = r7.f$2
                        java.util.concurrent.atomic.AtomicReference r7 = r7.f$3
                        android.service.credentials.ICredentialProviderService r8 = (android.service.credentials.ICredentialProviderService) r8
                        int r3 = com.android.server.credentials.RemoteCredentialService.$r8$clinit
                        r0.getClass()
                        java.util.concurrent.CompletableFuture r3 = new java.util.concurrent.CompletableFuture
                        r3.<init>()
                        long r4 = android.os.Binder.clearCallingIdentity()
                        com.android.server.credentials.RemoteCredentialService$1 r6 = new com.android.server.credentials.RemoteCredentialService$1     // Catch: java.lang.Throwable -> L56
                        r6.<init>(r0, r3, r2, r7)     // Catch: java.lang.Throwable -> L56
                        r8.onBeginGetCredential(r1, r6)     // Catch: java.lang.Throwable -> L56
                        android.os.Binder.restoreCallingIdentity(r4)
                        return r3
                    L56:
                        r7 = move-exception
                        android.os.Binder.restoreCallingIdentity(r4)
                        throw r7
                    L5b:
                        com.android.server.credentials.RemoteCredentialService r0 = r7.f$0
                        java.lang.Object r1 = r7.f$1
                        android.service.credentials.BeginCreateCredentialRequest r1 = (android.service.credentials.BeginCreateCredentialRequest) r1
                        java.util.concurrent.atomic.AtomicReference r2 = r7.f$2
                        java.util.concurrent.atomic.AtomicReference r7 = r7.f$3
                        android.service.credentials.ICredentialProviderService r8 = (android.service.credentials.ICredentialProviderService) r8
                        int r3 = com.android.server.credentials.RemoteCredentialService.$r8$clinit
                        r0.getClass()
                        java.util.concurrent.CompletableFuture r3 = new java.util.concurrent.CompletableFuture
                        r3.<init>()
                        long r4 = android.os.Binder.clearCallingIdentity()
                        com.android.server.credentials.RemoteCredentialService$2 r6 = new com.android.server.credentials.RemoteCredentialService$2     // Catch: java.lang.Throwable -> L81
                        r6.<init>(r0, r3, r2, r7)     // Catch: java.lang.Throwable -> L81
                        r8.onBeginCreateCredential(r1, r6)     // Catch: java.lang.Throwable -> L81
                        android.os.Binder.restoreCallingIdentity(r4)
                        return r3
                    L81:
                        r7 = move-exception
                        android.os.Binder.restoreCallingIdentity(r4)
                        throw r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda0.run(java.lang.Object):java.lang.Object");
                }
            }
