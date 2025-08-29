package com.android.systemui.keyboard.shortcut.ui;

import androidx.compose.runtime.internal.ComposableLambdaImpl;
import com.android.systemui.CoreStartable;
import com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter;
import com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class ShortcutHelperDialogStarter implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final CoroutineScope applicationScope;
    public ComponentSystemUIDialog dialog;
    public final SystemUIDialogFactory dialogFactory;
    public final ShortcutCustomizationDialogStarter.Factory shortcutCustomizationDialogStarterFactory;
    public final ShortcutHelperViewModel shortcutHelperViewModel;

    public ShortcutHelperDialogStarter(CoroutineScope coroutineScope, ShortcutHelperViewModel shortcutHelperViewModel, ShortcutCustomizationDialogStarter.Factory factory, SystemUIDialogFactory systemUIDialogFactory, ActivityStarter activityStarter) {
        this.applicationScope = coroutineScope;
        this.shortcutHelperViewModel = shortcutHelperViewModel;
        this.shortcutCustomizationDialogStarterFactory = factory;
        this.dialogFactory = systemUIDialogFactory;
        this.activityStarter = activityStarter;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        final Flow flow = this.shortcutHelperViewModel.shouldShow;
        FlowKt.launchIn(new Flow() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter$start$$inlined$map$1

            /* renamed from: com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter$start$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ShortcutHelperDialogStarter this$0;

                /* renamed from: com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter$start$$inlined$map$1$2$1, reason: invalid class name */
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
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, ShortcutHelperDialogStarter shortcutHelperDialogStarter) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = shortcutHelperDialogStarter;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Unit unit;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                        final ShortcutHelperDialogStarter shortcutHelperDialogStarter = this.this$0;
                        if (zBooleanValue) {
                            int i3 = ShortcutHelperDialogStarter.$r8$clinit;
                            shortcutHelperDialogStarter.getClass();
                            ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(-1030814093, true, 
                            /*  JADX ERROR: Method code generation error
                                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x004b: CONSTRUCTOR (r2v1 'composableLambdaImpl' androidx.compose.runtime.internal.ComposableLambdaImpl) = 
                                  (-1030814093 int)
                                  true
                                  (wrap:kotlin.jvm.functions.Function3:0x0043: CONSTRUCTOR 
                                  (r9v2 'shortcutHelperDialogStarter' com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter A[DONT_INLINE])
                                 A[MD:(com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter):void (m), WRAPPED] (LINE:68) call: com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter$createShortcutHelperDialog$1.<init>(com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter):void type: CONSTRUCTOR)
                                 A[DECLARE_VAR, MD:(int, boolean, java.lang.Object):void (m)] (LINE:76) call: androidx.compose.runtime.internal.ComposableLambdaImpl.<init>(int, boolean, java.lang.Object):void type: CONSTRUCTOR in method: com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter$start$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes2.dex
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
                                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                                Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter$createShortcutHelperDialog$1, state: NOT_LOADED
                                	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
                                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                                	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
                                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:782)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                	... 27 more
                                */
                            /*
                                this = this;
                                boolean r0 = r9 instanceof com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter$start$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r9
                                com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter$start$$inlined$map$1$2$1 r0 = (com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter$start$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter$start$$inlined$map$1$2$1 r0 = new com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter$start$$inlined$map$1$2$1
                                r0.<init>(r9)
                            L18:
                                java.lang.Object r9 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r9)
                                goto L7c
                            L27:
                                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                                java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                                r7.<init>(r8)
                                throw r7
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r9)
                                java.lang.Boolean r8 = (java.lang.Boolean) r8
                                boolean r8 = r8.booleanValue()
                                com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter r9 = r7.this$0
                                if (r8 == 0) goto L66
                                int r8 = com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter.$r8$clinit
                                r9.getClass()
                                com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter$createShortcutHelperDialog$1 r8 = new com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter$createShortcutHelperDialog$1
                                r8.<init>(r9)
                                androidx.compose.runtime.internal.ComposableLambdaImpl r2 = new androidx.compose.runtime.internal.ComposableLambdaImpl
                                r4 = -1030814093(0xffffffffc28f0673, float:-71.512596)
                                r2.<init>(r4, r3, r8)
                                com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperBottomSheet r8 = com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperBottomSheet.INSTANCE
                                r8.getClass()
                                float r8 = com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperBottomSheet.LargeScreenWidthLandscape
                                com.android.systemui.statusbar.phone.SystemUIDialogFactory r4 = r9.dialogFactory
                                r5 = 0
                                r6 = 23
                                com.android.systemui.statusbar.phone.ComponentSystemUIDialog r8 = com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt.m3093createBottomSheet6ZxE2Lo$default(r4, r2, r5, r8, r6)
                                r8.show()
                                r9.dialog = r8
                                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                goto L71
                            L66:
                                com.android.systemui.statusbar.phone.ComponentSystemUIDialog r8 = r9.dialog
                                if (r8 == 0) goto L70
                                r8.dismiss()
                                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                goto L71
                            L70:
                                r8 = 0
                            L71:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                                java.lang.Object r7 = r7.emit(r8, r0)
                                if (r7 != r1) goto L7c
                                return r1
                            L7c:
                                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                                return r7
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.ShortcutHelperDialogStarter$start$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = flow.collect(new AnonymousClass2(flowCollector, this), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                }, this.applicationScope);
            }

            public static /* synthetic */ void getDialog$annotations() {
            }
        }
