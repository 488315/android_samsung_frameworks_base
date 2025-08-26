package com.android.systemui.util;

import android.hardware.devicestate.DeviceStateManager;
import android.util.Log;
import com.android.systemui.QpRune;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.ArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class SecQsUiDisplayModeInteractor implements PanelScreenShotLogger.LogProvider {
    private static final String TAG = "SecQsUiDisplayModeInteractor";
    private final MutableStateFlow _uiDisplayMode;
    private final DeviceStateManager deviceStateManager;
    private final StateFlow foldState;
    private final DelayableExecutor mainExecutor;
    private final StateFlow uiDisplayMode;
    private Boolean wasFolded;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* renamed from: com.android.systemui.util.SecQsUiDisplayModeInteractor$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SecQsUiDisplayModeInteractor.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FoldState foldState, Continuation continuation) {
            return ((AnonymousClass2) create(foldState, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SecQsUiDisplayModeInteractor.this.updateUiDisplayModeAtFoldStateChanged();
            return Unit.INSTANCE;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class DeviceState {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ DeviceState[] $VALUES;
        public static final DeviceState FLIP = new DeviceState("FLIP", 0);
        public static final DeviceState FOLD = new DeviceState("FOLD", 1);
        public static final DeviceState MULTI_FOLD = new DeviceState("MULTI_FOLD", 2);
        public static final DeviceState PHONE = new DeviceState("PHONE", 3);
        public static final DeviceState TABLET = new DeviceState("TABLET", 4);

        private static final /* synthetic */ DeviceState[] $values() {
            return new DeviceState[]{FLIP, FOLD, MULTI_FOLD, PHONE, TABLET};
        }

        static {
            DeviceState[] deviceStateArr$values = $values();
            $VALUES = deviceStateArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(deviceStateArr$values);
        }

        private DeviceState(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static DeviceState valueOf(String str) {
            return (DeviceState) Enum.valueOf(DeviceState.class, str);
        }

        public static DeviceState[] values() {
            return (DeviceState[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class FoldState {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ FoldState[] $VALUES;
        public static final FoldState FOLD = new FoldState("FOLD", 0);
        public static final FoldState HALF_FOLD = new FoldState("HALF_FOLD", 1);
        public static final FoldState UNFOLD = new FoldState("UNFOLD", 2);
        public static final FoldState UNSET = new FoldState("UNSET", 3);

        private static final /* synthetic */ FoldState[] $values() {
            return new FoldState[]{FOLD, HALF_FOLD, UNFOLD, UNSET};
        }

        static {
            FoldState[] foldStateArr$values = $values();
            $VALUES = foldStateArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(foldStateArr$values);
        }

        private FoldState(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static FoldState valueOf(String str) {
            return (FoldState) Enum.valueOf(FoldState.class, str);
        }

        public static FoldState[] values() {
            return (FoldState[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class UiDisplayMode {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ UiDisplayMode[] $VALUES;
        public static final UiDisplayMode COVER = new UiDisplayMode("COVER", 0);
        public static final UiDisplayMode LARGE = new UiDisplayMode("LARGE", 1);
        public static final UiDisplayMode NARROW = new UiDisplayMode("NARROW", 2);
        public static final UiDisplayMode NORMAL = new UiDisplayMode("NORMAL", 3);
        public static final UiDisplayMode WIDE = new UiDisplayMode("WIDE", 4);

        private static final /* synthetic */ UiDisplayMode[] $values() {
            return new UiDisplayMode[]{COVER, LARGE, NARROW, NORMAL, WIDE};
        }

        static {
            UiDisplayMode[] uiDisplayModeArr$values = $values();
            $VALUES = uiDisplayModeArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(uiDisplayModeArr$values);
        }

        private UiDisplayMode(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static UiDisplayMode valueOf(String str) {
            return (UiDisplayMode) Enum.valueOf(UiDisplayMode.class, str);
        }

        public static UiDisplayMode[] values() {
            return (UiDisplayMode[]) $VALUES.clone();
        }
    }

    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DeviceState.values().length];
            try {
                iArr[DeviceState.MULTI_FOLD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeviceState.FOLD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeviceState.FLIP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DeviceState.TABLET.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public SecQsUiDisplayModeInteractor(DeviceStateManager deviceStateManager, DelayableExecutor delayableExecutor, CoroutineScope coroutineScope) {
        this.deviceStateManager = deviceStateManager;
        this.mainExecutor = delayableExecutor;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(UiDisplayMode.NORMAL);
        this._uiDisplayMode = stateFlowImplMutableStateFlow;
        this.uiDisplayMode = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new SecQsUiDisplayModeInteractor$foldState$1(this, null));
        SharingStarted.Companion.getClass();
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowConflatedCallbackFlow, coroutineScope, SharingStarted.Companion.Eagerly, FoldState.UNSET);
        this.foldState = readonlyStateFlowStateIn;
        UiDisplayMode uiDisplayModeUpdateUiDisplayMode = updateUiDisplayMode();
        Log.d(TAG, " init uiMode = " + uiDisplayModeUpdateUiDisplayMode + ", foldState = " + readonlyStateFlowStateIn.$$delegate_0.getValue());
        stateFlowImplMutableStateFlow.setValue(uiDisplayModeUpdateUiDisplayMode);
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(readonlyStateFlowStateIn, new AnonymousClass2(null)), coroutineScope);
        initLogProvider();
    }

    private final DeviceState getDeviceState() {
        return QpRune.QUICK_TABLET ? DeviceState.TABLET : DeviceState.PHONE;
    }

    private final void initLogProvider() {
        PanelScreenShotLogger.INSTANCE.addLogProvider(TAG, this);
    }

    private final boolean isFolded() {
        return this.foldState.getValue() == FoldState.FOLD;
    }

    private final boolean shouldCheckUiDisplayModeAtFoldStateChanged() {
        int i = WhenMappings.$EnumSwitchMapping$0[getDeviceState().ordinal()];
        return i == 1 || i == 2 || i == 3;
    }

    private final UiDisplayMode updateUiDisplayMode() {
        boolean zIsFolded = isFolded();
        int i = WhenMappings.$EnumSwitchMapping$0[getDeviceState().ordinal()];
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? UiDisplayMode.NORMAL : UiDisplayMode.LARGE : zIsFolded ? UiDisplayMode.COVER : UiDisplayMode.NORMAL : zIsFolded ? UiDisplayMode.NARROW : UiDisplayMode.WIDE : zIsFolded ? UiDisplayMode.NORMAL : UiDisplayMode.LARGE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateUiDisplayModeAtFoldStateChanged() {
        if (shouldCheckUiDisplayModeAtFoldStateChanged()) {
            MutableStateFlow mutableStateFlow = this._uiDisplayMode;
            UiDisplayMode uiDisplayModeUpdateUiDisplayMode = updateUiDisplayMode();
            Log.d(TAG, "updateUiDisplayModeAtFoldStateChanged " + uiDisplayModeUpdateUiDisplayMode);
            mutableStateFlow.setValue(uiDisplayModeUpdateUiDisplayMode);
        }
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public ArrayList<String> gatherState() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SecQsUiDisplayModeInteractor ============================================= ");
        arrayList.add("  uiDisplayMode = " + this._uiDisplayMode.getValue() + "  foldState =  " + this.foldState.getValue());
        arrayList.add("============================================================== ");
        return arrayList;
    }

    public final StateFlow getFoldState() {
        return this.foldState;
    }

    public final StateFlow getUiDisplayMode() {
        return this.uiDisplayMode;
    }

    public final boolean isFoldNarrow() {
        return this.uiDisplayMode.getValue() == UiDisplayMode.NARROW;
    }

    public final boolean isFoldWide() {
        return this.uiDisplayMode.getValue() == UiDisplayMode.WIDE;
    }

    public final boolean isTablet() {
        return this.uiDisplayMode.getValue() == UiDisplayMode.LARGE;
    }
}
