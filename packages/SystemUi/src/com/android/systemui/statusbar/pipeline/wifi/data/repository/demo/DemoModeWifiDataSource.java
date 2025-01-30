package com.android.systemui.statusbar.pipeline.wifi.data.repository.demo;

import android.os.Bundle;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.demomode.DemoModeController;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import com.sec.ims.settings.ImsProfile;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DemoModeWifiDataSource {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DemoModeWifiDataSource$special$$inlined$map$1 _wifiCommands;
    public final ReadonlySharedFlow wifiEvents;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public DemoModeWifiDataSource(DemoModeController demoModeController, CoroutineScope coroutineScope) {
        final Flow demoFlowForCommand = demoModeController.demoFlowForCommand();
        ?? r0 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1$2 */
            public final class C33552 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DemoModeWifiDataSource this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1$2", m277f = "DemoModeWifiDataSource.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C33552.this.emit(null, this);
                    }
                }

                public C33552(FlowCollector flowCollector, DemoModeWifiDataSource demoModeWifiDataSource) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = demoModeWifiDataSource;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    Object obj2;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj3 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj3);
                                Bundle bundle = (Bundle) obj;
                                int i3 = DemoModeWifiDataSource.$r8$clinit;
                                this.this$0.getClass();
                                String string = bundle.getString(ImsProfile.PDN_WIFI);
                                Object obj4 = null;
                                if (string != null) {
                                    if (Intrinsics.areEqual(string, "show")) {
                                        String string2 = bundle.getString(ActionResults.RESULT_SET_VOLUME_SUCCESS);
                                        final Integer valueOf = string2 != null ? Integer.valueOf(Integer.parseInt(string2)) : null;
                                        final int activity = DemoModeWifiDataSource.toActivity(bundle.getString("activity"));
                                        final String string3 = bundle.getString("ssid");
                                        final Boolean valueOf2 = Boolean.valueOf(Boolean.parseBoolean(bundle.getString("fully")));
                                        obj2 = 
                                        /*  JADX ERROR: Method code generation error
                                            jadx.core.utils.exceptions.CodegenException: Error generate insn: CONSTRUCTOR (r5v6 'obj2' java.lang.Object) = 
                                              (r2v10 'valueOf' java.lang.Integer A[DONT_INLINE])
                                              (r9v14 'activity' int A[DONT_INLINE])
                                              (r4v8 'string3' java.lang.String A[DONT_INLINE])
                                              (r8v6 'valueOf2' java.lang.Boolean A[DONT_INLINE])
                                             A[MD:(java.lang.Integer, int, java.lang.String, java.lang.Boolean):void (m)] (LINE:131) call: com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model.FakeWifiEventModel$Wifi.<init>(java.lang.Integer, int, java.lang.String, java.lang.Boolean):void type: CONSTRUCTOR in method: com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes2.dex
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
                                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                            	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
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
                                            	at jadx.core.codegen.ClassGen.addInnerClass(ClassGen.java:310)
                                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:299)
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
                                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:845)
                                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
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
                                            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model.FakeWifiEventModel$Wifi, state: NOT_LOADED
                                            	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:305)
                                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                            	... 110 more
                                            */
                                        /*
                                            this = this;
                                            boolean r0 = r9 instanceof com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1.C33552.AnonymousClass1
                                            if (r0 == 0) goto L13
                                            r0 = r9
                                            com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1.C33552.AnonymousClass1) r0
                                            int r1 = r0.label
                                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                            r3 = r1 & r2
                                            if (r3 == 0) goto L13
                                            int r1 = r1 - r2
                                            r0.label = r1
                                            goto L18
                                        L13:
                                            com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1$2$1
                                            r0.<init>(r9)
                                        L18:
                                            java.lang.Object r9 = r0.result
                                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                            int r2 = r0.label
                                            r3 = 1
                                            if (r2 == 0) goto L30
                                            if (r2 != r3) goto L28
                                            kotlin.ResultKt.throwOnFailure(r9)
                                            goto Ld4
                                        L28:
                                            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                                            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                                            r7.<init>(r8)
                                            throw r7
                                        L30:
                                            kotlin.ResultKt.throwOnFailure(r9)
                                            android.os.Bundle r8 = (android.os.Bundle) r8
                                            int r9 = com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource.$r8$clinit
                                            com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource r9 = r7.this$0
                                            r9.getClass()
                                            java.lang.String r9 = "wifi"
                                            java.lang.String r9 = r8.getString(r9)
                                            r2 = 0
                                            if (r9 != 0) goto L48
                                            goto Lc9
                                        L48:
                                            java.lang.String r4 = "show"
                                            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r4)
                                            java.lang.String r5 = "activity"
                                            java.lang.String r6 = "level"
                                            if (r4 == 0) goto L87
                                            java.lang.String r9 = r8.getString(r6)
                                            if (r9 == 0) goto L63
                                            int r9 = java.lang.Integer.parseInt(r9)
                                            java.lang.Integer r2 = java.lang.Integer.valueOf(r9)
                                        L63:
                                            java.lang.String r9 = r8.getString(r5)
                                            int r9 = com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource.toActivity(r9)
                                            java.lang.String r4 = "ssid"
                                            java.lang.String r4 = r8.getString(r4)
                                            java.lang.String r5 = "fully"
                                            java.lang.String r8 = r8.getString(r5)
                                            boolean r8 = java.lang.Boolean.parseBoolean(r8)
                                            com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model.FakeWifiEventModel$Wifi r5 = new com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model.FakeWifiEventModel$Wifi
                                            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r8)
                                            r5.<init>(r2, r9, r4, r8)
                                        L85:
                                            r2 = r5
                                            goto Lc9
                                        L87:
                                            java.lang.String r2 = "carriermerged"
                                            boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r2)
                                            if (r9 == 0) goto Lc7
                                            java.lang.String r9 = "slot"
                                            java.lang.String r9 = r8.getString(r9)
                                            if (r9 == 0) goto L9d
                                            int r9 = java.lang.Integer.parseInt(r9)
                                            goto L9f
                                        L9d:
                                            r9 = 10
                                        L9f:
                                            java.lang.String r2 = r8.getString(r6)
                                            if (r2 == 0) goto Laa
                                            int r2 = java.lang.Integer.parseInt(r2)
                                            goto Lab
                                        Laa:
                                            r2 = 0
                                        Lab:
                                            java.lang.String r4 = "numlevels"
                                            java.lang.String r4 = r8.getString(r4)
                                            if (r4 == 0) goto Lb8
                                            int r4 = java.lang.Integer.parseInt(r4)
                                            goto Lb9
                                        Lb8:
                                            r4 = 4
                                        Lb9:
                                            java.lang.String r8 = r8.getString(r5)
                                            int r8 = com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource.toActivity(r8)
                                            com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model.FakeWifiEventModel$CarrierMerged r5 = new com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model.FakeWifiEventModel$CarrierMerged
                                            r5.<init>(r9, r2, r4, r8)
                                            goto L85
                                        Lc7:
                                            com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model.FakeWifiEventModel$WifiDisabled r2 = com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model.FakeWifiEventModel$WifiDisabled.INSTANCE
                                        Lc9:
                                            r0.label = r3
                                            kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                                            java.lang.Object r7 = r7.emit(r2, r0)
                                            if (r7 != r1) goto Ld4
                                            return r1
                                        Ld4:
                                            kotlin.Unit r7 = kotlin.Unit.INSTANCE
                                            return r7
                                        */
                                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource$special$$inlined$map$1.C33552.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                    }
                                }

                                @Override // kotlinx.coroutines.flow.Flow
                                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                    Object collect = Flow.this.collect(new C33552(flowCollector, this), continuation);
                                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                                }
                            };
                            this._wifiCommands = r0;
                            this.wifiEvents = FlowKt.shareIn(r0, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion), 0);
                        }

                        public static int toActivity(String str) {
                            if (str != null) {
                                int hashCode = str.hashCode();
                                if (hashCode != 3365) {
                                    if (hashCode != 110414) {
                                        if (hashCode == 100357129 && str.equals("inout")) {
                                            return 3;
                                        }
                                    } else if (str.equals("out")) {
                                        return 2;
                                    }
                                } else if (str.equals("in")) {
                                    return 1;
                                }
                            }
                            return 0;
                        }
                    }
