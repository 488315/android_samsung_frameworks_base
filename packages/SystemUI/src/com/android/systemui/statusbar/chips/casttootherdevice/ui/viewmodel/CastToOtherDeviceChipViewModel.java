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
import com.android.systemui.statusbar.chips.casttootherdevice.domain.model.MediaRouterCastModel;
import com.android.systemui.statusbar.chips.casttootherdevice.ui.view.EndCastScreenToOtherDeviceDialogDelegate;
import com.android.systemui.statusbar.chips.casttootherdevice.ui.view.EndGenericCastToOtherDeviceDialogDelegate;
import com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel;
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
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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

                /* JADX WARN: Removed duplicated region for block: B:36:0x0115 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object timer;
                    int i;
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
                        ProjectionChipModel projectionChipModel = (ProjectionChipModel) obj;
                        if (projectionChipModel instanceof ProjectionChipModel.NotProjecting) {
                            timer = new OngoingActivityChipModel.Inactive(false, null, 3, null);
                        } else {
                            if (!(projectionChipModel instanceof ProjectionChipModel.Projecting)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            ProjectionChipModel.Projecting projecting = (ProjectionChipModel.Projecting) projectionChipModel;
                            int i4 = CastToOtherDeviceChipViewModel.WhenMappings.$EnumSwitchMapping$1[projecting.receiver.ordinal()];
                            if (i4 == 1) {
                                int i5 = CastToOtherDeviceChipViewModel.WhenMappings.$EnumSwitchMapping$0[projecting.contentType.ordinal()];
                                CastToOtherDeviceChipViewModel castToOtherDeviceChipViewModel = this.this$0;
                                if (i5 == 1) {
                                    CastToOtherDeviceChipViewModel.Companion companion = CastToOtherDeviceChipViewModel.Companion;
                                    castToOtherDeviceChipViewModel.getClass();
                                    OngoingActivityChipModel.ChipIcon.SingleColorIcon singleColorIcon = new OngoingActivityChipModel.ChipIcon.SingleColorIcon(new Icon.Resource(CastToOtherDeviceChipViewModel.CAST_TO_OTHER_DEVICE_ICON, new ContentDescription.Resource(R.string.cast_screen_to_other_device_chip_accessibility_label)));
                                    ColorsModel.Red red = ColorsModel.Red.INSTANCE;
                                    long jElapsedRealtime = castToOtherDeviceChipViewModel.systemClock.elapsedRealtime();
                                    Context context = castToOtherDeviceChipViewModel.context;
                                    CastToOtherDeviceChipViewModel$createCastScreenToOtherDeviceDialogDelegate$1 castToOtherDeviceChipViewModel$createCastScreenToOtherDeviceDialogDelegate$1 = new CastToOtherDeviceChipViewModel$createCastScreenToOtherDeviceDialogDelegate$1(castToOtherDeviceChipViewModel);
                                    EndMediaProjectionDialogHelper endMediaProjectionDialogHelper = castToOtherDeviceChipViewModel.endMediaProjectionDialogHelper;
                                    EndCastScreenToOtherDeviceDialogDelegate endCastScreenToOtherDeviceDialogDelegate = new EndCastScreenToOtherDeviceDialogDelegate(endMediaProjectionDialogHelper, context, castToOtherDeviceChipViewModel$createCastScreenToOtherDeviceDialogDelegate$1, projecting);
                                    InstanceId instanceId = castToOtherDeviceChipViewModel.instanceId;
                                    OngoingActivityChipViewModel.Companion.getClass();
                                    OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1 ongoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1 = new OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1(castToOtherDeviceChipViewModel.logger, CastToOtherDeviceChipViewModel.TAG, castToOtherDeviceChipViewModel.uiEventLogger, instanceId, endCastScreenToOtherDeviceDialogDelegate, castToOtherDeviceChipViewModel.dialogTransitionAnimator, CastToOtherDeviceChipViewModel.DIALOG_CUJ);
                                    new EndCastScreenToOtherDeviceDialogDelegate(endMediaProjectionDialogHelper, castToOtherDeviceChipViewModel.context, new CastToOtherDeviceChipViewModel$createCastScreenToOtherDeviceDialogDelegate$1(castToOtherDeviceChipViewModel), projecting);
                                    timer = new OngoingActivityChipModel.Active.Timer("CastToOtherDevice", true, singleColorIcon, red, jElapsedRealtime, null, false, ongoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1, new OngoingActivityChipModel.ClickBehavior.ExpandAction(new OngoingActivityChipViewModel$Companion$$ExternalSyntheticLambda0()), null, false, false, castToOtherDeviceChipViewModel.instanceId, 3680, null);
                                } else {
                                    if (i5 != 2) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                    timer = CastToOtherDeviceChipViewModel.access$createIconOnlyCastChip(castToOtherDeviceChipViewModel, null);
                                }
                                i = 1;
                                anonymousClass1.label = i;
                                if (this.$this_unsafeFlow.emit(timer, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i4 != 2) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                timer = new OngoingActivityChipModel.Inactive(false, null, 3, null);
                            }
                        }
                        i = 1;
                        anonymousClass1.label = i;
                        if (this.$this_unsafeFlow.emit(timer, anonymousClass1) == coroutineSingletons) {
                        }
                    } else {
                        if (i3 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Lazily, new OngoingActivityChipModel.Inactive(false, null, 3, null));
        this.projectionChip = readonlyStateFlowStateIn;
        final ReadonlyStateFlow readonlyStateFlow2 = mediaRouterChipInteractor.mediaRouterCastingState;
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel$special$$inlined$map$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object objAccess$createIconOnlyCastChip;
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
                        MediaRouterCastModel mediaRouterCastModel = (MediaRouterCastModel) obj;
                        if (mediaRouterCastModel instanceof MediaRouterCastModel.DoingNothing) {
                            objAccess$createIconOnlyCastChip = new OngoingActivityChipModel.Inactive(false, null, 3, null);
                        } else {
                            if (!(mediaRouterCastModel instanceof MediaRouterCastModel.Casting)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            objAccess$createIconOnlyCastChip = CastToOtherDeviceChipViewModel.access$createIconOnlyCastChip(this.this$0, ((MediaRouterCastModel.Casting) mediaRouterCastModel).deviceName);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(objAccess$createIconOnlyCastChip, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow2.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new OngoingActivityChipModel.Inactive(false, null, 3, null));
        this.routerChip = readonlyStateFlowStateIn2;
        ReadonlyStateFlow readonlyStateFlowStateIn3 = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlowStateIn, readonlyStateFlowStateIn2, new CastToOtherDeviceChipViewModel$internalChip$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new OngoingActivityChipModel.Inactive(false, null, 3, null));
        this.internalChip = readonlyStateFlowStateIn3;
        ChipTransitionHelper chipTransitionHelper = new ChipTransitionHelper(coroutineScope);
        this.hideChipDuringDialogTransitionHelper = chipTransitionHelper;
        this.chip = chipTransitionHelper.createChipFlow(readonlyStateFlowStateIn3);
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
