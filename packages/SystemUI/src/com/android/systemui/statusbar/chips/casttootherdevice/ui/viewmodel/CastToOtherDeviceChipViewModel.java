package com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel;

import android.content.Context;
import com.android.internal.logging.InstanceId;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor;
import com.android.systemui.statusbar.chips.casttootherdevice.ui.view.EndGenericCastToOtherDeviceDialogDelegate;
import com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor;
import com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper;
import com.android.systemui.statusbar.chips.ui.model.ColorsModel;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.ChipTransitionHelper;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel$Companion$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1;
import com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger;
import com.android.systemui.util.time.SystemClock;
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
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CastToOtherDeviceChipViewModel implements OngoingActivityChipViewModel {
    public static final String TAG;
    public final ReadonlyStateFlow chip;
    public final Context context;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final EndMediaProjectionDialogHelper endMediaProjectionDialogHelper;
    public final ChipTransitionHelper hideChipDuringDialogTransitionHelper;
    public final InstanceId instanceId;
    public final ReadonlyStateFlow internalChip;
    public final LogBuffer logger;
    public final MediaProjectionChipInteractor mediaProjectionChipInteractor;
    public final MediaRouterChipInteractor mediaRouterChipInteractor;
    public final ReadonlyStateFlow projectionChip;
    public final ReadonlyStateFlow routerChip;
    public final SystemClock systemClock;
    public final StatusBarChipsUiEventLogger uiEventLogger;
    public static final Companion Companion = new Companion(null);
    public static final int CAST_TO_OTHER_DEVICE_ICON = R.drawable.ic_cast_connected;
    public static final DialogCuj DIALOG_CUJ = new DialogCuj(111, "Cast to other device");
    public static final DialogCuj DIALOG_CUJ_AUDIO_ONLY = new DialogCuj(111, "Cast to other device audio only");

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[ProjectionChipModel.ContentType.values().length];
            try {
                iArr[ProjectionChipModel.ContentType.Screen.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProjectionChipModel.ContentType.Audio.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ProjectionChipModel.Receiver.values().length];
            try {
                iArr2[ProjectionChipModel.Receiver.CastToOtherDevice.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[ProjectionChipModel.Receiver.ShareToApp.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        StatusBarChipLogTags.INSTANCE.getClass();
        TAG = StringsKt__StringsKt.padEnd(20, "CastToOtherVM");
    }

    public CastToOtherDeviceChipViewModel(CoroutineScope coroutineScope, Context context, MediaProjectionChipInteractor mediaProjectionChipInteractor, MediaRouterChipInteractor mediaRouterChipInteractor, SystemClock systemClock, DialogTransitionAnimator dialogTransitionAnimator, EndMediaProjectionDialogHelper endMediaProjectionDialogHelper, LogBuffer logBuffer, StatusBarChipsUiEventLogger statusBarChipsUiEventLogger) {
        this.context = context;
        this.mediaProjectionChipInteractor = mediaProjectionChipInteractor;
        this.mediaRouterChipInteractor = mediaRouterChipInteractor;
        this.systemClock = systemClock;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.endMediaProjectionDialogHelper = endMediaProjectionDialogHelper;
        this.logger = logBuffer;
        this.uiEventLogger = statusBarChipsUiEventLogger;
        this.instanceId = statusBarChipsUiEventLogger.instanceIdSequence.newInstanceId();
        final ReadonlyStateFlow readonlyStateFlow = mediaProjectionChipInteractor.projection;
        Flow flow = new Flow() { // from class: com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CastToOtherDeviceChipViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CastToOtherDeviceChipViewModel castToOtherDeviceChipViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = castToOtherDeviceChipViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                /* JADX WARN: Removed duplicated region for block: B:21:0x0115 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r25, kotlin.coroutines.Continuation r26) {
                    /*
                        Method dump skipped, instructions count: 287
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Lazily, new OngoingActivityChipModel.Inactive(false, null, 3, null));
        this.projectionChip = stateIn;
        final ReadonlyStateFlow readonlyStateFlow2 = mediaRouterChipInteractor.mediaRouterCastingState;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CastToOtherDeviceChipViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CastToOtherDeviceChipViewModel castToOtherDeviceChipViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = castToOtherDeviceChipViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L5a
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        com.android.systemui.statusbar.chips.casttootherdevice.domain.model.MediaRouterCastModel r6 = (com.android.systemui.statusbar.chips.casttootherdevice.domain.model.MediaRouterCastModel) r6
                        boolean r7 = r6 instanceof com.android.systemui.statusbar.chips.casttootherdevice.domain.model.MediaRouterCastModel.DoingNothing
                        if (r7 == 0) goto L41
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Inactive r6 = new com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Inactive
                        r7 = 3
                        r2 = 0
                        r4 = 0
                        r6.<init>(r4, r2, r7, r2)
                        goto L4f
                    L41:
                        boolean r7 = r6 instanceof com.android.systemui.statusbar.chips.casttootherdevice.domain.model.MediaRouterCastModel.Casting
                        if (r7 == 0) goto L5d
                        com.android.systemui.statusbar.chips.casttootherdevice.domain.model.MediaRouterCastModel$Casting r6 = (com.android.systemui.statusbar.chips.casttootherdevice.domain.model.MediaRouterCastModel.Casting) r6
                        java.lang.String r6 = r6.deviceName
                        com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel r7 = r5.this$0
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Active$IconOnly r6 = com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel.access$createIconOnlyCastChip(r7, r6)
                    L4f:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    L5d:
                        kotlin.NoWhenBranchMatchedException r5 = new kotlin.NoWhenBranchMatchedException
                        r5.<init>()
                        throw r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new OngoingActivityChipModel.Inactive(false, null, 3, null));
        this.routerChip = stateIn2;
        ReadonlyStateFlow stateIn3 = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, stateIn2, new CastToOtherDeviceChipViewModel$internalChip$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new OngoingActivityChipModel.Inactive(false, null, 3, null));
        this.internalChip = stateIn3;
        ChipTransitionHelper chipTransitionHelper = new ChipTransitionHelper(coroutineScope);
        this.hideChipDuringDialogTransitionHelper = chipTransitionHelper;
        this.chip = chipTransitionHelper.createChipFlow(stateIn3);
    }

    public static final OngoingActivityChipModel.Active.IconOnly access$createIconOnlyCastChip(CastToOtherDeviceChipViewModel castToOtherDeviceChipViewModel, String str) {
        castToOtherDeviceChipViewModel.getClass();
        OngoingActivityChipModel.ChipIcon.SingleColorIcon singleColorIcon = new OngoingActivityChipModel.ChipIcon.SingleColorIcon(new Icon.Resource(CAST_TO_OTHER_DEVICE_ICON, new ContentDescription.Resource(R.string.accessibility_casting)));
        ColorsModel.Red red = ColorsModel.Red.INSTANCE;
        Context context = castToOtherDeviceChipViewModel.context;
        CastToOtherDeviceChipViewModel$createGenericCastToOtherDeviceDialogDelegate$1 castToOtherDeviceChipViewModel$createGenericCastToOtherDeviceDialogDelegate$1 = new CastToOtherDeviceChipViewModel$createGenericCastToOtherDeviceDialogDelegate$1(castToOtherDeviceChipViewModel);
        EndMediaProjectionDialogHelper endMediaProjectionDialogHelper = castToOtherDeviceChipViewModel.endMediaProjectionDialogHelper;
        EndGenericCastToOtherDeviceDialogDelegate endGenericCastToOtherDeviceDialogDelegate = new EndGenericCastToOtherDeviceDialogDelegate(endMediaProjectionDialogHelper, context, str, castToOtherDeviceChipViewModel$createGenericCastToOtherDeviceDialogDelegate$1);
        InstanceId instanceId = castToOtherDeviceChipViewModel.instanceId;
        OngoingActivityChipViewModel.Companion.getClass();
        OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1 ongoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1 = new OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1(castToOtherDeviceChipViewModel.logger, TAG, castToOtherDeviceChipViewModel.uiEventLogger, instanceId, endGenericCastToOtherDeviceDialogDelegate, castToOtherDeviceChipViewModel.dialogTransitionAnimator, DIALOG_CUJ_AUDIO_ONLY);
        new EndGenericCastToOtherDeviceDialogDelegate(endMediaProjectionDialogHelper, castToOtherDeviceChipViewModel.context, str, new CastToOtherDeviceChipViewModel$createGenericCastToOtherDeviceDialogDelegate$1(castToOtherDeviceChipViewModel));
        return new OngoingActivityChipModel.Active.IconOnly("CastToOtherDevice", true, singleColorIcon, red, ongoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1, new OngoingActivityChipModel.ClickBehavior.ExpandAction(new OngoingActivityChipViewModel$Companion$$ExternalSyntheticLambda0()), null, false, false, castToOtherDeviceChipViewModel.instanceId, 448, null);
    }
}
