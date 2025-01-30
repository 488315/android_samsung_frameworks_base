package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import android.os.Bundle;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel$Mobile;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DemoModeMobileConnectionDataSource {
    public final DemoModeMobileConnectionDataSource$special$$inlined$map$1 _mobileCommands;
    public final ReadonlySharedFlow mobileEvents;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSource$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public DemoModeMobileConnectionDataSource(DemoModeController demoModeController, CoroutineScope coroutineScope) {
        final Flow demoFlowForCommand = demoModeController.demoFlowForCommand();
        ?? r0 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSource$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSource$special$$inlined$map$1$2 */
            public final class C32252 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DemoModeMobileConnectionDataSource this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSource$special$$inlined$map$1$2", m277f = "DemoModeMobileConnectionDataSource.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSource$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        return C32252.this.emit(null, this);
                    }
                }

                public C32252(FlowCollector flowCollector, DemoModeMobileConnectionDataSource demoModeMobileConnectionDataSource) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = demoModeMobileConnectionDataSource;
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup;
                    int i2;
                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i3 = anonymousClass1.label;
                        if ((i3 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i3 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Bundle bundle = (Bundle) obj;
                                this.this$0.getClass();
                                String string = bundle.getString("mobile");
                                Object obj3 = null;
                                Integer num = null;
                                if (string != null) {
                                    if (Intrinsics.areEqual(string, "show")) {
                                        String string2 = bundle.getString(ActionResults.RESULT_SET_VOLUME_SUCCESS);
                                        Integer valueOf = string2 != null ? Integer.valueOf(Integer.parseInt(string2)) : null;
                                        String string3 = bundle.getString("datatype");
                                        if (string3 != null) {
                                            switch (string3.hashCode()) {
                                                case 101:
                                                    if (string3.equals("e")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.f220E;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                case 103:
                                                    if (string3.equals("g")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.f221G;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                case 104:
                                                    if (string3.equals("h")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.f222H;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                case 1639:
                                                    if (string3.equals("1x")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.ONE_X;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                case 1684:
                                                    if (string3.equals("3g")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.THREE_G;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                case 1715:
                                                    if (string3.equals("4g")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.FOUR_G;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                case 1746:
                                                    if (string3.equals("5g")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.NR_5G;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                case 3267:
                                                    if (string3.equals("h+")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.H_PLUS;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                case 53208:
                                                    if (string3.equals("4g+")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.FOUR_G_PLUS;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                case 54169:
                                                    if (string3.equals("5g+")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.NR_5G_PLUS;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                case 54227:
                                                    if (string3.equals("5ge")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.LTE_CA_5G_E;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                case 99470:
                                                    if (string3.equals("dis")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.DATA_DISABLED;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                case 107485:
                                                    if (string3.equals("lte")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.LTE;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                case 109267:
                                                    if (string3.equals("not")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.NOT_DEFAULT_DATA;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                case 3332078:
                                                    if (string3.equals("lte+")) {
                                                        signalIcon$MobileIconGroup2 = TelephonyIcons.LTE_PLUS;
                                                        break;
                                                    }
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                                default:
                                                    signalIcon$MobileIconGroup2 = TelephonyIcons.UNKNOWN;
                                                    break;
                                            }
                                            signalIcon$MobileIconGroup = signalIcon$MobileIconGroup2;
                                        } else {
                                            signalIcon$MobileIconGroup = null;
                                        }
                                        String string4 = bundle.getString("slot");
                                        Integer valueOf2 = string4 != null ? Integer.valueOf(Integer.parseInt(string4)) : null;
                                        String string5 = bundle.getString("carrierid");
                                        Integer valueOf3 = string5 != null ? Integer.valueOf(Integer.parseInt(string5)) : null;
                                        String string6 = bundle.getString("inflate");
                                        Boolean valueOf4 = string6 != null ? Boolean.valueOf(Boolean.parseBoolean(string6)) : null;
                                        String string7 = bundle.getString("activity");
                                        if (string7 != null) {
                                            int hashCode = string7.hashCode();
                                            if (hashCode == 3365) {
                                                if (string7.equals("in")) {
                                                    i2 = 1;
                                                    num = Integer.valueOf(i2);
                                                }
                                                i2 = 0;
                                                num = Integer.valueOf(i2);
                                            } else if (hashCode != 110414) {
                                                if (hashCode == 100357129 && string7.equals("inout")) {
                                                    i2 = 3;
                                                    num = Integer.valueOf(i2);
                                                }
                                                i2 = 0;
                                                num = Integer.valueOf(i2);
                                            } else {
                                                if (string7.equals("out")) {
                                                    i2 = 2;
                                                    num = Integer.valueOf(i2);
                                                }
                                                i2 = 0;
                                                num = Integer.valueOf(i2);
                                            }
                                        }
                                        Integer num2 = num;
                                        boolean areEqual = Intrinsics.areEqual(bundle.getString("carriernetworkchange"), "show");
                                        boolean areEqual2 = Intrinsics.areEqual(bundle.getString("roam"), "show");
                                        String string8 = bundle.getString("networkname");
                                        if (string8 == null) {
                                            string8 = "demo mode";
                                        }
                                        obj3 = new FakeNetworkEventModel$Mobile(valueOf, signalIcon$MobileIconGroup, valueOf2, valueOf3, valueOf4, num2, areEqual, areEqual2, string8, false, 512, null);
                                    } else {
                                        String string9 = bundle.getString("slot");
                                        final Integer valueOf5 = string9 != null ? Integer.valueOf(Integer.parseInt(string9)) : null;
                                        obj3 = 
                                        /*  JADX ERROR: Method code generation error
                                            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x01fc: CONSTRUCTOR (r6v3 'obj3' java.lang.Object) = (r6v2 'valueOf5' java.lang.Integer A[DONT_INLINE]) A[MD:(java.lang.Integer):void (m)] (LINE:506) call: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel$MobileDisabled.<init>(java.lang.Integer):void type: CONSTRUCTOR in method: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSource$special$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes2.dex
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                                            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:140)
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
                                            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel$MobileDisabled, state: NOT_LOADED
                                            	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:305)
                                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                            	... 110 more
                                            */
                                        /*
                                            Method dump skipped, instructions count: 586
                                            To view this dump add '--comments-level debug' option
                                        */
                                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSource$special$$inlined$map$1.C32252.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                    }
                                }

                                @Override // kotlinx.coroutines.flow.Flow
                                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                    Object collect = Flow.this.collect(new C32252(flowCollector, this), continuation);
                                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                                }
                            };
                            this._mobileCommands = r0;
                            this.mobileEvents = FlowKt.shareIn(r0, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion), 0);
                        }
                    }
