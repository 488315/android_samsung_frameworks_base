package com.android.systemui.statusbar.chips.ui.viewmodel;

import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel;
import com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel;
import com.android.systemui.statusbar.chips.notification.ui.viewmodel.NotifChipsViewModel;
import com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel;
import com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel;
import com.android.systemui.statusbar.chips.ui.model.MultipleOngoingActivityChipsModel;
import com.android.systemui.statusbar.chips.ui.model.MultipleOngoingActivityChipsModelLegacy;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedLazily;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OngoingActivityChipsViewModel {
    public static final InternalChipModel.Inactive DEFAULT_INTERNAL_INACTIVE_MODEL;
    public static final String TAG;
    public final OngoingActivityChipsViewModel$special$$inlined$map$8 activeChips;
    public final ReadonlyStateFlow chips;
    public final ReadonlyStateFlow chipsLegacy;
    public final ReadonlyStateFlow incomingChipBundle;
    public final OngoingActivityChipsViewModel$special$$inlined$map$2 internalChip;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 internalChips;
    public final ReadonlyStateFlow isLandscape;
    public final ReadonlyStateFlow isScreenReasonablyLarge;
    public final LogBuffer logger;
    public final ReadonlyStateFlow primaryChip;
    public final OngoingActivityChipsViewModel$special$$inlined$map$9 visibleChipKeys;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ChipBundle {
        public final OngoingActivityChipModel call;
        public final OngoingActivityChipModel castToOtherDevice;
        public final List notifs;
        public final OngoingActivityChipModel screenRecord;
        public final OngoingActivityChipModel shareToApp;

        public ChipBundle() {
            this(null, null, null, null, null, 31, null);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r10v2, types: [com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel] */
        /* JADX WARN: Type inference failed for: r7v6, types: [com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel] */
        /* JADX WARN: Type inference failed for: r8v2, types: [com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel] */
        /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel] */
        public static ChipBundle copy$default(ChipBundle chipBundle, OngoingActivityChipModel.Inactive inactive, OngoingActivityChipModel.Inactive inactive2, OngoingActivityChipModel.Inactive inactive3, OngoingActivityChipModel.Inactive inactive4, List list, int i) {
            OngoingActivityChipModel.Inactive inactive5 = inactive;
            if ((i & 1) != 0) {
                inactive5 = chipBundle.screenRecord;
            }
            OngoingActivityChipModel.Inactive inactive6 = inactive5;
            OngoingActivityChipModel.Inactive inactive7 = inactive2;
            if ((i & 2) != 0) {
                inactive7 = chipBundle.shareToApp;
            }
            OngoingActivityChipModel.Inactive inactive8 = inactive7;
            OngoingActivityChipModel.Inactive inactive9 = inactive3;
            if ((i & 4) != 0) {
                inactive9 = chipBundle.castToOtherDevice;
            }
            OngoingActivityChipModel.Inactive inactive10 = inactive9;
            OngoingActivityChipModel.Inactive inactive11 = inactive4;
            if ((i & 8) != 0) {
                inactive11 = chipBundle.call;
            }
            OngoingActivityChipModel.Inactive inactive12 = inactive11;
            if ((i & 16) != 0) {
                list = chipBundle.notifs;
            }
            chipBundle.getClass();
            return new ChipBundle(inactive6, inactive8, inactive10, inactive12, list);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ChipBundle)) {
                return false;
            }
            ChipBundle chipBundle = (ChipBundle) obj;
            return Intrinsics.areEqual(this.screenRecord, chipBundle.screenRecord) && Intrinsics.areEqual(this.shareToApp, chipBundle.shareToApp) && Intrinsics.areEqual(this.castToOtherDevice, chipBundle.castToOtherDevice) && Intrinsics.areEqual(this.call, chipBundle.call) && Intrinsics.areEqual(this.notifs, chipBundle.notifs);
        }

        public final int hashCode() {
            return this.notifs.hashCode() + ((this.call.hashCode() + ((this.castToOtherDevice.hashCode() + ((this.shareToApp.hashCode() + (this.screenRecord.hashCode() * 31)) * 31)) * 31)) * 31);
        }

        public final String toString() {
            return "ChipBundle(screenRecord=" + this.screenRecord + ", shareToApp=" + this.shareToApp + ", castToOtherDevice=" + this.castToOtherDevice + ", call=" + this.call + ", notifs=" + this.notifs + ")";
        }

        public ChipBundle(OngoingActivityChipModel ongoingActivityChipModel, OngoingActivityChipModel ongoingActivityChipModel2, OngoingActivityChipModel ongoingActivityChipModel3, OngoingActivityChipModel ongoingActivityChipModel4, List<? extends OngoingActivityChipModel.Active> list) {
            this.screenRecord = ongoingActivityChipModel;
            this.shareToApp = ongoingActivityChipModel2;
            this.castToOtherDevice = ongoingActivityChipModel3;
            this.call = ongoingActivityChipModel4;
            this.notifs = list;
        }

        public ChipBundle(OngoingActivityChipModel ongoingActivityChipModel, OngoingActivityChipModel ongoingActivityChipModel2, OngoingActivityChipModel ongoingActivityChipModel3, OngoingActivityChipModel ongoingActivityChipModel4, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? new OngoingActivityChipModel.Inactive(false, null, 3, null) : ongoingActivityChipModel, (i & 2) != 0 ? new OngoingActivityChipModel.Inactive(false, null, 3, null) : ongoingActivityChipModel2, (i & 4) != 0 ? new OngoingActivityChipModel.Inactive(false, null, 3, null) : ongoingActivityChipModel3, (i & 8) != 0 ? new OngoingActivityChipModel.Inactive(false, null, 3, null) : ongoingActivityChipModel4, (i & 16) != 0 ? EmptyList.INSTANCE : list);
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class ChipType {
        public static final /* synthetic */ ChipType[] $VALUES;
        public static final ChipType Call;
        public static final ChipType CastToOtherDevice;
        public static final ChipType Notification;
        public static final ChipType ScreenRecord;
        public static final ChipType ShareToApp;

        static {
            ChipType chipType = new ChipType("ScreenRecord", 0);
            ScreenRecord = chipType;
            ChipType chipType2 = new ChipType("ShareToApp", 1);
            ShareToApp = chipType2;
            ChipType chipType3 = new ChipType("CastToOtherDevice", 2);
            CastToOtherDevice = chipType3;
            ChipType chipType4 = new ChipType("Call", 3);
            Call = chipType4;
            ChipType chipType5 = new ChipType(PluginLockStar.NOTIFICATION_TYPE, 4);
            Notification = chipType5;
            ChipType[] chipTypeArr = {chipType, chipType2, chipType3, chipType4, chipType5};
            $VALUES = chipTypeArr;
            EnumEntriesKt.enumEntries(chipTypeArr);
        }

        private ChipType(String str, int i) {
        }

        public static ChipType valueOf(String str) {
            return (ChipType) Enum.valueOf(ChipType.class, str);
        }

        public static ChipType[] values() {
            return (ChipType[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface InternalChipModel {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Active implements InternalChipModel {
            public final OngoingActivityChipModel.Active model;
            public final ChipType type;

            public Active(ChipType chipType, OngoingActivityChipModel.Active active) {
                this.type = chipType;
                this.model = active;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Active)) {
                    return false;
                }
                Active active = (Active) obj;
                return this.type == active.type && Intrinsics.areEqual(this.model, active.model);
            }

            public final int hashCode() {
                return this.model.hashCode() + (this.type.hashCode() * 31);
            }

            public final String toString() {
                return "Active(type=" + this.type + ", model=" + this.model + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Inactive implements InternalChipModel {
            public final OngoingActivityChipModel.Inactive call;
            public final OngoingActivityChipModel.Inactive castToOtherDevice;
            public final OngoingActivityChipModel.Inactive notifs;
            public final OngoingActivityChipModel.Inactive screenRecord;
            public final OngoingActivityChipModel.Inactive shareToApp;

            public Inactive(OngoingActivityChipModel.Inactive inactive, OngoingActivityChipModel.Inactive inactive2, OngoingActivityChipModel.Inactive inactive3, OngoingActivityChipModel.Inactive inactive4, OngoingActivityChipModel.Inactive inactive5) {
                this.screenRecord = inactive;
                this.shareToApp = inactive2;
                this.castToOtherDevice = inactive3;
                this.call = inactive4;
                this.notifs = inactive5;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Inactive)) {
                    return false;
                }
                Inactive inactive = (Inactive) obj;
                return Intrinsics.areEqual(this.screenRecord, inactive.screenRecord) && Intrinsics.areEqual(this.shareToApp, inactive.shareToApp) && Intrinsics.areEqual(this.castToOtherDevice, inactive.castToOtherDevice) && Intrinsics.areEqual(this.call, inactive.call) && Intrinsics.areEqual(this.notifs, inactive.notifs);
            }

            public final int hashCode() {
                return this.notifs.hashCode() + ((this.call.hashCode() + ((this.castToOtherDevice.hashCode() + ((this.shareToApp.hashCode() + (this.screenRecord.hashCode() * 31)) * 31)) * 31)) * 31);
            }

            public final String toString() {
                return "Inactive(screenRecord=" + this.screenRecord + ", shareToApp=" + this.shareToApp + ", castToOtherDevice=" + this.castToOtherDevice + ", call=" + this.call + ", notifs=" + this.notifs + ")";
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InternalMultipleOngoingActivityChipsModel {
        public final InternalChipModel primary;
        public final InternalChipModel secondary;

        public InternalMultipleOngoingActivityChipsModel(InternalChipModel internalChipModel, InternalChipModel internalChipModel2) {
            this.primary = internalChipModel;
            this.secondary = internalChipModel2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof InternalMultipleOngoingActivityChipsModel)) {
                return false;
            }
            InternalMultipleOngoingActivityChipsModel internalMultipleOngoingActivityChipsModel = (InternalMultipleOngoingActivityChipsModel) obj;
            return Intrinsics.areEqual(this.primary, internalMultipleOngoingActivityChipsModel.primary) && Intrinsics.areEqual(this.secondary, internalMultipleOngoingActivityChipsModel.secondary);
        }

        public final int hashCode() {
            return this.secondary.hashCode() + (this.primary.hashCode() * 31);
        }

        public final String toString() {
            return "InternalMultipleOngoingActivityChipsModel(primary=" + this.primary + ", secondary=" + this.secondary + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MostImportantChipResult {
        public final InternalChipModel mostImportantChip;
        public final ChipBundle remainingChips;

        public MostImportantChipResult(InternalChipModel internalChipModel, ChipBundle chipBundle) {
            this.mostImportantChip = internalChipModel;
            this.remainingChips = chipBundle;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MostImportantChipResult)) {
                return false;
            }
            MostImportantChipResult mostImportantChipResult = (MostImportantChipResult) obj;
            return Intrinsics.areEqual(this.mostImportantChip, mostImportantChipResult.mostImportantChip) && Intrinsics.areEqual(this.remainingChips, mostImportantChipResult.remainingChips);
        }

        public final int hashCode() {
            return this.remainingChips.hashCode() + (this.mostImportantChip.hashCode() * 31);
        }

        public final String toString() {
            return "MostImportantChipResult(mostImportantChip=" + this.mostImportantChip + ", remainingChips=" + this.remainingChips + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ChipType.values().length];
            try {
                iArr[ChipType.ScreenRecord.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ChipType.ShareToApp.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ChipType.CastToOtherDevice.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ChipType.Call.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ChipType.Notification.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        StatusBarChipLogTags.INSTANCE.getClass();
        TAG = StringsKt__StringsKt.padEnd(20, "ChipsViewModel");
        InternalChipModel.Inactive inactive = new InternalChipModel.Inactive(new OngoingActivityChipModel.Inactive(false, null, 3, null), new OngoingActivityChipModel.Inactive(false, null, 3, null), new OngoingActivityChipModel.Inactive(false, null, 3, null), new OngoingActivityChipModel.Inactive(false, null, 3, null), new OngoingActivityChipModel.Inactive(false, null, 3, null));
        DEFAULT_INTERNAL_INACTIVE_MODEL = inactive;
        new InternalMultipleOngoingActivityChipsModel(inactive, inactive);
    }

    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$8, kotlinx.coroutines.flow.Flow] */
    /* JADX WARN: Type inference failed for: r14v2, types: [com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$9] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$2, kotlinx.coroutines.flow.Flow] */
    public OngoingActivityChipsViewModel(CoroutineScope coroutineScope, ScreenRecordChipViewModel screenRecordChipViewModel, ShareToAppChipViewModel shareToAppChipViewModel, CastToOtherDeviceChipViewModel castToOtherDeviceChipViewModel, CallChipViewModel callChipViewModel, NotifChipsViewModel notifChipsViewModel, DisplayStateInteractor displayStateInteractor, ConfigurationInteractor configurationInteractor, LogBuffer logBuffer) {
        this.logger = logBuffer;
        final Flow flow = ((ConfigurationInteractorImpl) configurationInteractor).configurationValues;
        Flow flow2 = new Flow() { // from class: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ OngoingActivityChipsViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, OngoingActivityChipsViewModel ongoingActivityChipsViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = ongoingActivityChipsViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L52
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.content.res.Configuration r5 = (android.content.res.Configuration) r5
                        java.lang.String r6 = com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel.TAG
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel r6 = r4.this$0
                        r6.getClass()
                        int r5 = r5.orientation
                        r6 = 2
                        if (r5 != r6) goto L42
                        r5 = r3
                        goto L43
                    L42:
                        r5 = 0
                    L43:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L52
                        return r1
                    L52:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow2, coroutineScope, WhileSubscribed$default, bool);
        this.isLandscape = stateIn;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, ((DisplayStateInteractorImpl) displayStateInteractor).isLargeScreen, new OngoingActivityChipsViewModel$isScreenReasonablyLarge$1(null))), new OngoingActivityChipsViewModel$isScreenReasonablyLarge$2(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isScreenReasonablyLarge = stateIn2;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 combine = FlowKt.combine(screenRecordChipViewModel.chip, shareToAppChipViewModel.chip, castToOtherDeviceChipViewModel.chip, callChipViewModel.chip, notifChipsViewModel.chips, new OngoingActivityChipsViewModel$incomingChipBundle$1(this, null));
        StartedLazily startedLazily = SharingStarted.Companion.Lazily;
        final ReadonlyStateFlow stateIn3 = FlowKt.stateIn(combine, coroutineScope, startedLazily, new ChipBundle(null, null, null, null, null, 31, null));
        this.incomingChipBundle = stateIn3;
        ?? r4 = new Flow() { // from class: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ OngoingActivityChipsViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, OngoingActivityChipsViewModel ongoingActivityChipsViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = ongoingActivityChipsViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$ChipBundle r5 = (com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel.ChipBundle) r5
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel r6 = r4.this$0
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$MostImportantChipResult r5 = com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel.access$pickMostImportantChip(r6, r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$InternalChipModel r5 = r5.mostImportantChip
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.internalChip = r4;
        final Flow pairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(r4, DEFAULT_INTERNAL_INACTIVE_MODEL);
        final ReadonlyStateFlow stateIn4 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ OngoingActivityChipsViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, OngoingActivityChipsViewModel ongoingActivityChipsViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = ongoingActivityChipsViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L51
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.util.kotlin.WithPrev r5 = (com.android.systemui.util.kotlin.WithPrev) r5
                        java.lang.Object r6 = r5.component1()
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$InternalChipModel r6 = (com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel.InternalChipModel) r6
                        java.lang.Object r5 = r5.component2()
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$InternalChipModel r5 = (com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel.InternalChipModel) r5
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel r2 = r4.this$0
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel r5 = com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel.access$createOutputModel(r2, r6, r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L51
                        return r1
                    L51:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedLazily, new OngoingActivityChipModel.Inactive(false, null, 3, null));
        this.primaryChip = stateIn4;
        this.internalChips = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn3, stateIn2, new OngoingActivityChipsViewModel$internalChips$1(this, null));
        this.chips = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(new MultipleOngoingActivityChipsModel(null, null, null, 7, null)));
        final ReadonlyStateFlow stateIn5 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$5

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$5$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$5$2$1 r0 = (com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$5$2$1 r0 = new com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$5$2$1
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r9)
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel r8 = (com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel) r8
                        com.android.systemui.statusbar.chips.ui.model.MultipleOngoingActivityChipsModelLegacy r9 = new com.android.systemui.statusbar.chips.ui.model.MultipleOngoingActivityChipsModelLegacy
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Inactive r2 = new com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Inactive
                        r4 = 0
                        r5 = 3
                        r6 = 0
                        r2.<init>(r4, r6, r5, r6)
                        r9.<init>(r8, r2)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                        java.lang.Object r7 = r7.emit(r9, r0)
                        if (r7 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedLazily, new MultipleOngoingActivityChipsModelLegacy(null, null, 3, null));
        this.chipsLegacy = stateIn5;
        final ?? r0 = new Flow() { // from class: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$8

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$8$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$8$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                        boolean r0 = r7 instanceof com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$8.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$8$2$1 r0 = (com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$8.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$8$2$1 r0 = new com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$8$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L56
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        com.android.systemui.statusbar.chips.ui.model.MultipleOngoingActivityChipsModelLegacy r6 = (com.android.systemui.statusbar.chips.ui.model.MultipleOngoingActivityChipsModelLegacy) r6
                        java.util.ArrayList r7 = new java.util.ArrayList
                        r7.<init>()
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel r2 = r6.primary
                        boolean r4 = r2 instanceof com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active
                        if (r4 == 0) goto L42
                        r7.add(r2)
                    L42:
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel r6 = r6.secondary
                        boolean r2 = r6 instanceof com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active
                        if (r2 == 0) goto L4b
                        r7.add(r6)
                    L4b:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L56
                        return r1
                    L56:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$8.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.activeChips = r0;
        this.visibleChipKeys = new Flow() { // from class: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$9

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$9$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$9$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$9.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$9$2$1 r0 = (com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$9.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$9$2$1 r0 = new com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$9$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L83
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.util.List r7 = (java.util.List) r7
                        java.lang.Iterable r7 = (java.lang.Iterable) r7
                        java.util.ArrayList r8 = new java.util.ArrayList
                        r8.<init>()
                        java.util.Iterator r7 = r7.iterator()
                    L3f:
                        boolean r2 = r7.hasNext()
                        if (r2 == 0) goto L56
                        java.lang.Object r2 = r7.next()
                        r4 = r2
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Active r4 = (com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active) r4
                        boolean r4 = r4.isHidden()
                        if (r4 != 0) goto L3f
                        r8.add(r2)
                        goto L3f
                    L56:
                        java.util.ArrayList r7 = new java.util.ArrayList
                        r2 = 10
                        int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r8, r2)
                        r7.<init>(r2)
                        int r2 = r8.size()
                        r4 = 0
                    L66:
                        if (r4 >= r2) goto L78
                        java.lang.Object r5 = r8.get(r4)
                        int r4 = r4 + 1
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Active r5 = (com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active) r5
                        java.lang.String r5 = r5.getKey()
                        r7.add(r5)
                        goto L66
                    L78:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r7, r0)
                        if (r6 != r1) goto L83
                        return r1
                    L83:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$9.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    public static final OngoingActivityChipModel access$createOutputModel(OngoingActivityChipsViewModel ongoingActivityChipsViewModel, InternalChipModel internalChipModel, InternalChipModel internalChipModel2) {
        ongoingActivityChipsViewModel.getClass();
        if (!(internalChipModel instanceof InternalChipModel.Active) || !(internalChipModel2 instanceof InternalChipModel.Inactive)) {
            return internalChipModel2 instanceof InternalChipModel.Active ? ((InternalChipModel.Active) internalChipModel2).model : new OngoingActivityChipModel.Inactive(false, null, 3, null);
        }
        int i = WhenMappings.$EnumSwitchMapping$0[((InternalChipModel.Active) internalChipModel).type.ordinal()];
        if (i == 1) {
            return ((InternalChipModel.Inactive) internalChipModel2).screenRecord;
        }
        if (i == 2) {
            return ((InternalChipModel.Inactive) internalChipModel2).shareToApp;
        }
        if (i == 3) {
            return ((InternalChipModel.Inactive) internalChipModel2).castToOtherDevice;
        }
        if (i == 4) {
            return ((InternalChipModel.Inactive) internalChipModel2).call;
        }
        if (i == 5) {
            return ((InternalChipModel.Inactive) internalChipModel2).notifs;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final MostImportantChipResult access$pickMostImportantChip(OngoingActivityChipsViewModel ongoingActivityChipsViewModel, ChipBundle chipBundle) {
        ongoingActivityChipsViewModel.getClass();
        OngoingActivityChipModel ongoingActivityChipModel = chipBundle.screenRecord;
        if (ongoingActivityChipModel instanceof OngoingActivityChipModel.Active) {
            return new MostImportantChipResult(new InternalChipModel.Active(ChipType.ScreenRecord, (OngoingActivityChipModel.Active) ongoingActivityChipModel), ChipBundle.copy$default(chipBundle, new OngoingActivityChipModel.Inactive(false, null, 3, null), new OngoingActivityChipModel.Inactive(false, null, 3, null), null, null, null, 28));
        }
        OngoingActivityChipModel ongoingActivityChipModel2 = chipBundle.shareToApp;
        if (ongoingActivityChipModel2 instanceof OngoingActivityChipModel.Active) {
            return new MostImportantChipResult(new InternalChipModel.Active(ChipType.ShareToApp, (OngoingActivityChipModel.Active) ongoingActivityChipModel2), ChipBundle.copy$default(chipBundle, null, new OngoingActivityChipModel.Inactive(false, null, 3, null), null, null, null, 29));
        }
        OngoingActivityChipModel ongoingActivityChipModel3 = chipBundle.castToOtherDevice;
        if (ongoingActivityChipModel3 instanceof OngoingActivityChipModel.Active) {
            return new MostImportantChipResult(new InternalChipModel.Active(ChipType.CastToOtherDevice, (OngoingActivityChipModel.Active) ongoingActivityChipModel3), ChipBundle.copy$default(chipBundle, null, null, new OngoingActivityChipModel.Inactive(false, null, 3, null), null, null, 27));
        }
        OngoingActivityChipModel ongoingActivityChipModel4 = chipBundle.call;
        if (ongoingActivityChipModel4 instanceof OngoingActivityChipModel.Active) {
            return new MostImportantChipResult(new InternalChipModel.Active(ChipType.Call, (OngoingActivityChipModel.Active) ongoingActivityChipModel4), ChipBundle.copy$default(chipBundle, null, null, null, new OngoingActivityChipModel.Inactive(false, null, 3, null), null, 23));
        }
        if (!chipBundle.notifs.isEmpty()) {
            InternalChipModel.Active active = new InternalChipModel.Active(ChipType.Notification, (OngoingActivityChipModel.Active) CollectionsKt___CollectionsKt.first(chipBundle.notifs));
            List list = chipBundle.notifs;
            return new MostImportantChipResult(active, ChipBundle.copy$default(chipBundle, null, null, null, null, list.subList(1, list.size()), 15));
        }
        OngoingActivityChipModel ongoingActivityChipModel5 = chipBundle.screenRecord;
        if (!(ongoingActivityChipModel5 instanceof OngoingActivityChipModel.Inactive)) {
            throw new IllegalStateException("Check failed.");
        }
        if (!(ongoingActivityChipModel2 instanceof OngoingActivityChipModel.Inactive)) {
            throw new IllegalStateException("Check failed.");
        }
        if (!(ongoingActivityChipModel3 instanceof OngoingActivityChipModel.Inactive)) {
            throw new IllegalStateException("Check failed.");
        }
        if (!(ongoingActivityChipModel4 instanceof OngoingActivityChipModel.Inactive)) {
            throw new IllegalStateException("Check failed.");
        }
        if (chipBundle.notifs.isEmpty()) {
            return new MostImportantChipResult(new InternalChipModel.Inactive((OngoingActivityChipModel.Inactive) ongoingActivityChipModel5, (OngoingActivityChipModel.Inactive) ongoingActivityChipModel2, (OngoingActivityChipModel.Inactive) ongoingActivityChipModel3, (OngoingActivityChipModel.Inactive) ongoingActivityChipModel4, new OngoingActivityChipModel.Inactive(false, null, 3, null)), chipBundle);
        }
        throw new IllegalStateException("Check failed.");
    }
}
