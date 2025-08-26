package com.android.systemui.statusbar.policy.ui.dialog.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.service.notification.SystemZenRules;
import android.service.notification.ZenModeConfig;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.settingslib.notification.modes.ZenIcon;
import com.android.settingslib.notification.modes.ZenMode;
import com.android.settingslib.notification.modes.ZenModeDescriptions;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.QSModesEvent;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate;
import com.android.systemui.statusbar.policy.ui.dialog.ModesDialogEventLogger;
import com.google.common.base.Platform;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class ModesDialogViewModel {
    public final Context context;
    public final ModesDialogDelegate dialogDelegate;
    public final ModesDialogEventLogger dialogEventLogger;
    public final Flow tiles;
    public final FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 visibleModes;
    public final ZenModeDescriptions zenModeDescriptions;

    public ModesDialogViewModel(Context context, final ZenModeInteractor zenModeInteractor, CoroutineDispatcher coroutineDispatcher, ModesDialogDelegate modesDialogDelegate, ModesDialogEventLogger modesDialogEventLogger) {
        this.context = context;
        this.dialogDelegate = modesDialogDelegate;
        this.dialogEventLogger = modesDialogEventLogger;
        this.zenModeDescriptions = new ZenModeDescriptions(context);
        final FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 flowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 = new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(EmptyList.INSTANCE, zenModeInteractor.modes, new ModesDialogViewModel$visibleModes$1(null));
        this.visibleModes = flowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
        this.tiles = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ZenModeInteractor $zenModeInteractor$inlined;
                public final /* synthetic */ ModesDialogViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    Object L$3;
                    Object L$4;
                    Object L$5;
                    Object L$6;
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

                public AnonymousClass2(FlowCollector flowCollector, ZenModeInteractor zenModeInteractor, ModesDialogViewModel modesDialogViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$zenModeInteractor$inlined = zenModeInteractor;
                    this.this$0 = modesDialogViewModel;
                }

                /* JADX WARN: Code restructure failed: missing block: B:45:0x015d, code lost:
                
                    if (r11.emit((java.util.List) r4, r2) == r3) goto L46;
                 */
                /* JADX WARN: Removed duplicated region for block: B:19:0x007c  */
                /* JADX WARN: Removed duplicated region for block: B:25:0x00b7  */
                /* JADX WARN: Removed duplicated region for block: B:26:0x00b9  */
                /* JADX WARN: Removed duplicated region for block: B:29:0x00ca  */
                /* JADX WARN: Removed duplicated region for block: B:33:0x00ec  */
                /* JADX WARN: Removed duplicated region for block: B:39:0x0107  */
                /* JADX WARN: Removed duplicated region for block: B:42:0x0115  */
                /* JADX WARN: Removed duplicated region for block: B:44:0x0145  */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                /* JADX WARN: Type inference failed for: r7v13 */
                /* JADX WARN: Type inference failed for: r7v14 */
                /* JADX WARN: Type inference failed for: r7v2, types: [com.android.systemui.common.shared.model.ContentDescription, java.lang.Integer] */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x009f -> B:23:0x00a2). Please report as a decompilation issue!!! */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Collection arrayList;
                    Iterator it;
                    FlowCollector flowCollector;
                    int i;
                    String str;
                    String string;
                    AnonymousClass2 anonymousClass2 = this;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i2 - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = anonymousClass2.new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i3 = anonymousClass1.label;
                    int i4 = 1;
                    boolean z = false;
                    ?? r7 = 0;
                    if (i3 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        List list = (List) obj;
                        arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                        it = list.iterator();
                        flowCollector = anonymousClass2.$this_unsafeFlow;
                        if (it.hasNext()) {
                        }
                        return coroutineSingletons;
                    }
                    if (i3 != 1) {
                        if (i3 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                        return Unit.INSTANCE;
                    }
                    String str2 = (String) anonymousClass1.L$6;
                    arrayList = (Collection) anonymousClass1.L$5;
                    final ZenMode zenMode = (ZenMode) anonymousClass1.L$4;
                    it = (Iterator) anonymousClass1.L$3;
                    Collection collection = (Collection) anonymousClass1.L$2;
                    flowCollector = (FlowCollector) anonymousClass1.L$1;
                    AnonymousClass2 anonymousClass22 = (AnonymousClass2) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    String str3 = str2;
                    anonymousClass2 = anonymousClass22;
                    Icon.Loaded loaded = new Icon.Loaded(((ZenIcon) obj2).drawable, r7, r7);
                    String name = zenMode.mRule.getName();
                    int i5 = Platform.$r8$clinit;
                    String str4 = name != null ? "" : name;
                    ModesDialogViewModel modesDialogViewModel = anonymousClass2.this$0;
                    String modeDescription = modesDialogViewModel.getModeDescription(zenMode, false);
                    if (zenMode.isActive()) {
                        if (modeDescription != null) {
                            string = modesDialogViewModel.context.getString(R.string.zen_mode_on_with_details, modeDescription);
                            string.getClass();
                        } else {
                            string = modesDialogViewModel.context.getString(R.string.zen_mode_on);
                            string.getClass();
                        }
                        str = string;
                        i = R.string.zen_mode_off;
                    } else {
                        if (modeDescription == null) {
                            Context context = modesDialogViewModel.context;
                            i = R.string.zen_mode_off;
                            modeDescription = context.getString(R.string.zen_mode_off);
                        } else {
                            i = R.string.zen_mode_off;
                        }
                        str = modeDescription;
                    }
                    final ModesDialogViewModel modesDialogViewModel2 = anonymousClass2.this$0;
                    String modeDescription2 = modesDialogViewModel2.getModeDescription(zenMode, true);
                    if (modeDescription2 == null) {
                        modeDescription2 = "";
                    }
                    boolean zIsActive = zenMode.isActive();
                    Context context2 = modesDialogViewModel2.context;
                    if (zenMode.isActive()) {
                        i = R.string.zen_mode_on;
                    }
                    String string2 = context2.getString(i);
                    final ZenModeInteractor zenModeInteractor = anonymousClass2.$zenModeInteractor$inlined;
                    arrayList.add(new ModeTileViewModel(str3, loaded, str4, str, modeDescription2, zIsActive, string2, 
                    /*  JADX ERROR: Method code generation error
                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x013c: INVOKE 
                          (r4v4 'arrayList' java.util.Collection)
                          (wrap:com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModeTileViewModel:0x0139: CONSTRUCTOR 
                          (r13v1 'str3' java.lang.String)
                          (r14v0 'loaded' com.android.systemui.common.shared.model.Icon$Loaded)
                          (r15v1 'str4' java.lang.String)
                          (r16v2 'str' java.lang.String)
                          (r17v1 'modeDescription2' java.lang.String)
                          (r18v0 'zIsActive' boolean)
                          (r19v1 'string2' java.lang.String)
                          (wrap:kotlin.jvm.functions.Function0:0x011e: CONSTRUCTOR 
                          (r8v5 'zenMode' com.android.settingslib.notification.modes.ZenMode A[DONT_INLINE])
                          (r1v17 'modesDialogViewModel2' com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel A[DONT_INLINE])
                          (r7v8 'zenModeInteractor' com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor A[DONT_INLINE])
                         A[MD:(com.android.settingslib.notification.modes.ZenMode, com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel, com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor):void (m), WRAPPED] (LINE:287) call: com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel$tiles$1$1$1.<init>(com.android.settingslib.notification.modes.ZenMode, com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel, com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor):void type: CONSTRUCTOR)
                          (wrap:kotlin.jvm.functions.Function0:0x0123: CONSTRUCTOR 
                          (r1v17 'modesDialogViewModel2' com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel A[DONT_INLINE])
                          (r8v5 'zenMode' com.android.settingslib.notification.modes.ZenMode A[DONT_INLINE])
                         A[MD:(com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel, com.android.settingslib.notification.modes.ZenMode):void (m), WRAPPED] (LINE:292) call: com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel$tiles$1$1$2.<init>(com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel, com.android.settingslib.notification.modes.ZenMode):void type: CONSTRUCTOR)
                          (wrap:java.lang.String:0x012f: INVOKE 
                          (wrap:android.content.res.Resources:0x0128: INVOKE 
                          (wrap:android.content.Context:0x0126: IGET (r1v17 'modesDialogViewModel2' com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel) A[WRAPPED] (LINE:295) com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel.context android.content.Context)
                         VIRTUAL call: android.content.Context.getResources():android.content.res.Resources A[MD:():android.content.res.Resources (c), WRAPPED] (LINE:297))
                          (wrap:int:SGET  A[WRAPPED] com.android.systemui.R.string.accessibility_long_click_tile int)
                         VIRTUAL call: android.content.res.Resources.getString(int):java.lang.String A[MD:(int):java.lang.String throws android.content.res.Resources$NotFoundException (c), WRAPPED] (LINE:304))
                         A[MD:(java.lang.String, com.android.systemui.common.shared.model.Icon, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, java.lang.String):void (m), WRAPPED] (LINE:314) call: com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModeTileViewModel.<init>(java.lang.String, com.android.systemui.common.shared.model.Icon, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, java.lang.String):void type: CONSTRUCTOR)
                         INTERFACE call: java.util.Collection.add(java.lang.Object):boolean A[MD:(E):boolean (c)] (LINE:317) in method: com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel$special$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes3.dex
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                        	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
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
                        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel$tiles$1$1$1, state: NOT_LOADED
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
                        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                        	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
                        	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:910)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                        	... 23 more
                        */
                    /*
                        Method dump skipped, instructions count: 355
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__TransformKt$runningFold$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, zenModeInteractor, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
    }

    public static final void access$openSettings(ModesDialogViewModel modesDialogViewModel, ZenMode zenMode) {
        ModesDialogEventLogger modesDialogEventLogger = modesDialogViewModel.dialogEventLogger;
        modesDialogEventLogger.getClass();
        modesDialogEventLogger.uiEventLogger.log(zenMode.isManualDnd() ? QSModesEvent.QS_MODES_DND_SETTINGS : QSModesEvent.QS_MODES_MODE_SETTINGS, 0, zenMode.getOwnerPackage());
        modesDialogViewModel.dialogDelegate.launchFromDialog(new Intent("android.settings.AUTOMATIC_ZEN_RULE_SETTINGS").putExtra("android.provider.extra.AUTOMATIC_ZEN_RULE_ID", zenMode.mId));
    }

    public final String getModeDescription(ZenMode zenMode, boolean z) {
        String string;
        ZenModeConfig.ScheduleInfo scheduleInfoTryParseScheduleConditionId;
        String daysOfWeekFull;
        if (!zenMode.mRule.isEnabled()) {
            return this.context.getResources().getString(R.string.zen_mode_set_up);
        }
        if (!zenMode.mRule.isManualInvocationAllowed() && !zenMode.isActive()) {
            return this.context.getResources().getString(R.string.zen_mode_no_manual_invocation);
        }
        ZenModeDescriptions zenModeDescriptions = this.zenModeDescriptions;
        if (!z) {
            return zenModeDescriptions.getTriggerDescription(zenMode);
        }
        zenModeDescriptions.getClass();
        if (!"android".equals(zenMode.mRule.getPackageName()) || zenMode.mRule.getType() != 1 || (scheduleInfoTryParseScheduleConditionId = ZenModeConfig.tryParseScheduleConditionId(zenMode.mRule.getConditionId())) == null || (daysOfWeekFull = SystemZenRules.getDaysOfWeekFull(zenModeDescriptions.mContext, scheduleInfoTryParseScheduleConditionId)) == null) {
            string = null;
        } else {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(daysOfWeekFull, ", ");
            sbM.append(SystemZenRules.getTimeSummary(zenModeDescriptions.mContext, scheduleInfoTryParseScheduleConditionId));
            string = sbM.toString();
        }
        return string == null ? zenModeDescriptions.getTriggerDescription(zenMode) : string;
    }
}
