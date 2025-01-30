package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.media.AudioManager;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.RingerModeLiveData;
import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.RingerModeTrackerImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MuteQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AudioManager audioManager;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final CoroutineScope coroutineScope;
    public final Flow lockScreenState;
    public final RingerModeTracker ringerModeTracker;
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;
    public int previousNonSilentMode = 2;
    public final String key = "mute";
    public final int pickerIconResourceId = R.drawable.ic_notifications_silence;

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

    public MuteQuickAffordanceConfig(Context context, UserTracker userTracker, UserFileManager userFileManager, RingerModeTracker ringerModeTracker, AudioManager audioManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2) {
        this.context = context;
        this.userTracker = userTracker;
        this.userFileManager = userFileManager;
        this.ringerModeTracker = ringerModeTracker;
        this.audioManager = audioManager;
        this.coroutineScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher2;
        RingerModeLiveData ringerModeLiveData = ((RingerModeTrackerImpl) ringerModeTracker).ringerModeInternal;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        MuteQuickAffordanceConfig$asFlow$1 muteQuickAffordanceConfig$asFlow$1 = new MuteQuickAffordanceConfig$asFlow$1(ringerModeLiveData, null);
        conflatedCallbackFlow.getClass();
        final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new MuteQuickAffordanceConfig$lockScreenState$1(this, null), FlowKt.flowOn(ConflatedCallbackFlow.conflatedCallbackFlow(muteQuickAffordanceConfig$asFlow$1), coroutineDispatcher))), new MuteQuickAffordanceConfig$lockScreenState$2(this, null));
        this.lockScreenState = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceConfig$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceConfig$special$$inlined$map$1$2 */
            public final class C15772 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MuteQuickAffordanceConfig this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceConfig$special$$inlined$map$1$2", m277f = "MuteQuickAffordanceConfig.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceConfig$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        return C15772.this.emit(null, this);
                    }
                }

                public C15772(FlowCollector flowCollector, MuteQuickAffordanceConfig muteQuickAffordanceConfig) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = muteQuickAffordanceConfig;
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
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Integer num = (Integer) obj;
                                Pair pair = this.this$0.audioManager.isVolumeFixed() ? new Pair(ActivationState.NotSupported.INSTANCE, new Integer(R.string.volume_ringer_hint_mute)) : (num != null && num.intValue() == 0) ? new Pair(ActivationState.Active.INSTANCE, new Integer(R.string.volume_ringer_hint_mute)) : new Pair(ActivationState.Inactive.INSTANCE, new Integer(R.string.volume_ringer_hint_unmute));
                                KeyguardQuickAffordanceConfig.LockScreenState.Visible visible = new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(R.drawable.ic_notifications_silence, new ContentDescription.Resource(((Number) pair.component2()).intValue())), (ActivationState) pair.component1());
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(visible, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C15772(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher2);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String getKey() {
        return this.key;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Flow getLockScreenState() {
        return this.lockScreenState;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final int getPickerIconResourceId() {
        return this.pickerIconResourceId;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Object getPickerScreenState(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new MuteQuickAffordanceConfig$getPickerScreenState$2(this, null), continuation);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        BuildersKt.launch$default(this.coroutineScope, this.backgroundDispatcher, null, new MuteQuickAffordanceConfig$onTriggered$1(this, null), 2);
        return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(R.string.volume_ringer_status_silent);
    }
}
