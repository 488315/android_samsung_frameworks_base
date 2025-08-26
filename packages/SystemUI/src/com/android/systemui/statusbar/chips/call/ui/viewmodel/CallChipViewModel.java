package com.android.systemui.statusbar.chips.call.ui.viewmodel;

import android.R;
import android.app.PendingIntent;
import android.content.Context;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.call.domain.interactor.CallChipInteractor;
import com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel;
import com.android.systemui.statusbar.chips.ui.model.ColorsModel;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel;
import com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel;
import com.android.systemui.util.time.SystemClock;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class CallChipViewModel implements OngoingActivityChipViewModel {
    public static final Companion Companion = null;
    public static final Icon.Resource phoneIcon;
    public final ActivityStarter activityStarter;
    public final ReadonlyStateFlow chip;
    public final ReadonlyStateFlow chipLegacy;
    public final ReadonlyStateFlow chipWithReturnAnimation;
    public final Context context;
    public final OngoingCallModel latestState;
    public final TransitionState latestTransitionState;
    public final Logger logger;
    public final StateFlowImpl transitionState;
    public final StatusBarChipsUiEventLogger uiEventLogger;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface TransitionState {

        public final class NoTransition implements TransitionState {
            public static final NoTransition INSTANCE = new NoTransition();

            private NoTransition() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof NoTransition);
            }

            public final int hashCode() {
                return 28185635;
            }

            public final String toString() {
                return "NoTransition";
            }
        }
    }

    static {
        new Companion(null);
        phoneIcon = new Icon.Resource(R.drawable.input_method_fullscreen_background, new ContentDescription.Resource(com.android.systemui.R.string.ongoing_call_content_description));
    }

    public CallChipViewModel(Context context, CoroutineScope coroutineScope, CallChipInteractor callChipInteractor, final SystemClock systemClock, ActivityStarter activityStarter, LogBuffer logBuffer, StatusBarChipsUiEventLogger statusBarChipsUiEventLogger) {
        this.context = context;
        this.activityStarter = activityStarter;
        this.uiEventLogger = statusBarChipsUiEventLogger;
        StatusBarChipLogTags.INSTANCE.getClass();
        this.logger = new Logger(logBuffer, StringsKt__StringsKt.padEnd(20, "OngoingCallVM"));
        new ActivityTransitionAnimator.TransitionCookie(String.valueOf(CallChipViewModel.class));
        TransitionState.NoTransition noTransition = TransitionState.NoTransition.INSTANCE;
        this.transitionState = StateFlowKt.MutableStateFlow(noTransition);
        this.latestState = OngoingCallModel.NoCall.INSTANCE;
        this.latestTransitionState = noTransition;
        this.chipWithReturnAnimation = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(new OngoingActivityChipModel.Inactive(false, null, 3, null)));
        final ReadonlyStateFlow readonlyStateFlow = callChipInteractor.ongoingCallState;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ SystemClock $systemClock$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CallChipViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CallChipViewModel callChipViewModel, SystemClock systemClock) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = callChipViewModel;
                    this.$systemClock$inlined = systemClock;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    OngoingActivityChipModel.ChipIcon singleColorIcon;
                    OngoingActivityChipModel timer;
                    OngoingActivityChipModel inactive;
                    int i = 0;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i2 - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i3 = anonymousClass1.label;
                    if (i3 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        OngoingCallModel ongoingCallModel = (OngoingCallModel) obj;
                        final CallChipViewModel callChipViewModel = this.this$0;
                        Logger logger = callChipViewModel.logger;
                        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, CallChipViewModel$chipLegacy$1$1.INSTANCE, null);
                        logMessageObtain.setStr1(ongoingCallModel.logString());
                        logger.getBuffer().commit(logMessageObtain);
                        if (ongoingCallModel instanceof OngoingCallModel.NoCall) {
                            inactive = new OngoingActivityChipModel.Inactive(false, null, 3, null);
                        } else {
                            if (!(ongoingCallModel instanceof OngoingCallModel.InCall)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            OngoingCallModel.InCall inCall = (OngoingCallModel.InCall) ongoingCallModel;
                            if (inCall.isAppVisible) {
                                inactive = new OngoingActivityChipModel.Inactive(false, null, 3, null);
                            } else {
                                CallChipViewModel.TransitionState.NoTransition noTransition = CallChipViewModel.TransitionState.NoTransition.INSTANCE;
                                SystemClock systemClock = this.$systemClock$inlined;
                                callChipViewModel.getClass();
                                String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("callChip-", inCall.notificationKey);
                                ContentDescription.Loaded loaded = new ContentDescription.Loaded(callChipViewModel.context.getString(com.android.systemui.R.string.accessibility_desc_notification_icon, inCall.appName, callChipViewModel.context.getString(com.android.systemui.R.string.ongoing_call_content_description)));
                                StatusBarIconView statusBarIconView = inCall.notificationIconView;
                                if (statusBarIconView != null) {
                                    RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                                    int i4 = StatusBarConnectedDisplays.$r8$clinit;
                                    singleColorIcon = new OngoingActivityChipModel.ChipIcon.StatusBarView(statusBarIconView, loaded);
                                } else {
                                    singleColorIcon = new OngoingActivityChipModel.ChipIcon.SingleColorIcon(CallChipViewModel.phoneIcon);
                                }
                                ColorsModel.AccentThemed accentThemed = ColorsModel.AccentThemed.INSTANCE;
                                final PendingIntent pendingIntent = inCall.intent;
                                final InstanceId instanceId = inCall.notificationInstanceId;
                                long j = inCall.startTimeMs;
                                if (j <= 0) {
                                    timer = new OngoingActivityChipModel.Active.IconOnly(strM, false, singleColorIcon, accentThemed, pendingIntent != null ? 
                                    /*  JADX ERROR: Method code generation error
                                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x00fb: CONSTRUCTOR (r10v6 'timer' com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel) = 
                                          (r11v2 'strM' java.lang.String)
                                          false
                                          (r13v2 'singleColorIcon' com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$ChipIcon)
                                          (r14v2 'accentThemed' com.android.systemui.statusbar.chips.ui.model.ColorsModel$AccentThemed)
                                          (wrap:android.view.View$OnClickListener:0x00d0: TERNARY null = ((r9v10 'pendingIntent' android.app.PendingIntent) != (null android.app.PendingIntent)) ? (wrap:android.view.View$OnClickListener:0x00d4: CONSTRUCTOR 
                                          (r6v1 'callChipViewModel' com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel A[DONT_INLINE])
                                          (r12v3 'instanceId' com.android.internal.logging.InstanceId A[DONT_INLINE])
                                          (r9v10 'pendingIntent' android.app.PendingIntent A[DONT_INLINE])
                                         A[MD:(com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel, com.android.internal.logging.InstanceId, android.app.PendingIntent):void (m), WRAPPED] (LINE:213) call: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$getOnClickListener$1.<init>(com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel, com.android.internal.logging.InstanceId, android.app.PendingIntent):void type: CONSTRUCTOR) : (null android.view.View$OnClickListener))
                                          (wrap:com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$ClickBehavior:?: TERNARY null = ((r9v10 'pendingIntent' android.app.PendingIntent) == (null android.app.PendingIntent)) ? (wrap:??:0x00da: SGET  A[WRAPPED] (LINE:219) com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.ClickBehavior.None.INSTANCE com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$ClickBehavior$None) : (wrap:com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$ClickBehavior:0x00e6: CONSTRUCTOR 
                                          (wrap:com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$$ExternalSyntheticLambda0:0x00e3: CONSTRUCTOR (r2v0 'i' int) A[MD:(int):void (m), WRAPPED] (LINE:228) call: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$$ExternalSyntheticLambda0.<init>(int):void type: CONSTRUCTOR)
                                         A[MD:(kotlin.jvm.functions.Function1):void (m), WRAPPED] (LINE:231) call: com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.ClickBehavior.ExpandAction.<init>(kotlin.jvm.functions.Function1):void type: CONSTRUCTOR))
                                          (null com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$TransitionManager)
                                          false
                                          false
                                          (r12v3 'instanceId' com.android.internal.logging.InstanceId)
                                          (258 int)
                                          (null kotlin.jvm.internal.DefaultConstructorMarker)
                                         A[MD:(java.lang.String, boolean, com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$ChipIcon, com.android.systemui.statusbar.chips.ui.model.ColorsModel, android.view.View$OnClickListener, com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$ClickBehavior, com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$TransitionManager, boolean, boolean, com.android.internal.logging.InstanceId, int, kotlin.jvm.internal.DefaultConstructorMarker):void (m)] (LINE:252) call: com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active.IconOnly.<init>(java.lang.String, boolean, com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$ChipIcon, com.android.systemui.statusbar.chips.ui.model.ColorsModel, android.view.View$OnClickListener, com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$ClickBehavior, com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$TransitionManager, boolean, boolean, com.android.internal.logging.InstanceId, int, kotlin.jvm.internal.DefaultConstructorMarker):void type: CONSTRUCTOR in method: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes3.dex
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
                                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:140)
                                        	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
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
                                        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                                        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                                        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$getOnClickListener$1, state: NOT_LOADED
                                        	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
                                        	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                                        	at jadx.core.codegen.InsnGen.makeTernary(InsnGen.java:1187)
                                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:536)
                                        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                                        	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
                                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:782)
                                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                        	... 43 more
                                        */
                                    /*
                                        Method dump skipped, instructions count: 347
                                        To view this dump add '--comments-level debug' option
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this, systemClock), continuation);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                            }
                        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), new OngoingActivityChipModel.Inactive(false, null, 3, null));
                        this.chipLegacy = readonlyStateFlowStateIn;
                        this.chip = readonlyStateFlowStateIn;
                    }
                }
