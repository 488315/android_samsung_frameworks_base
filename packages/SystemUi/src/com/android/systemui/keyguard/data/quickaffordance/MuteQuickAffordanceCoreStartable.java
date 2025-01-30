package com.android.systemui.keyguard.data.quickaffordance;

import androidx.lifecycle.Observer;
import com.android.systemui.CoreStartable;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.RingerModeTrackerImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MuteQuickAffordanceCoreStartable implements CoreStartable {
    public final CoroutineDispatcher backgroundDispatcher;
    public final CoroutineScope coroutineScope;
    public final FeatureFlags featureFlags;
    public final KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository;
    public final MuteQuickAffordanceCoreStartable$observer$1 observer = new Observer() { // from class: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$observer$1
        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            int intValue = ((Number) obj).intValue();
            MuteQuickAffordanceCoreStartable muteQuickAffordanceCoreStartable = MuteQuickAffordanceCoreStartable.this;
            BuildersKt.launch$default(muteQuickAffordanceCoreStartable.coroutineScope, muteQuickAffordanceCoreStartable.backgroundDispatcher, null, new MuteQuickAffordanceCoreStartable$updateLastNonSilentRingerMode$1(intValue, muteQuickAffordanceCoreStartable, null), 2);
        }
    };
    public final RingerModeTracker ringerModeTracker;
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$observer$1] */
    public MuteQuickAffordanceCoreStartable(FeatureFlags featureFlags, UserTracker userTracker, RingerModeTracker ringerModeTracker, UserFileManager userFileManager, KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.featureFlags = featureFlags;
        this.userTracker = userTracker;
        this.ringerModeTracker = ringerModeTracker;
        this.userFileManager = userFileManager;
        this.keyguardQuickAffordanceRepository = keyguardQuickAffordanceRepository;
        this.coroutineScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (((FeatureFlagsRelease) this.featureFlags).isEnabled(Flags.CUSTOMIZABLE_LOCK_SCREEN_QUICK_AFFORDANCES)) {
            final ReadonlyStateFlow readonlyStateFlow = this.keyguardQuickAffordanceRepository.selections;
            FlowKt.launchIn(new Flow() { // from class: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                /* renamed from: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1$2 */
                public final class C15792 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ MuteQuickAffordanceCoreStartable this$0;

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    @DebugMetadata(m276c = "com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1$2", m277f = "MuteQuickAffordanceCoreStartable.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1$2$1, reason: invalid class name */
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
                            return C15792.this.emit(null, this);
                        }
                    }

                    public C15792(FlowCollector flowCollector, MuteQuickAffordanceCoreStartable muteQuickAffordanceCoreStartable) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = muteQuickAffordanceCoreStartable;
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
                        boolean z;
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
                                    Collection values = ((Map) obj).values();
                                    boolean z2 = false;
                                    if (!(values instanceof Collection) || !values.isEmpty()) {
                                        Iterator it = values.iterator();
                                        while (true) {
                                            if (!it.hasNext()) {
                                                break;
                                            }
                                            List list = (List) it.next();
                                            if (!(list instanceof Collection) || !list.isEmpty()) {
                                                Iterator it2 = list.iterator();
                                                while (it2.hasNext()) {
                                                    if (Intrinsics.areEqual(((KeyguardQuickAffordanceConfig) it2.next()).getKey(), "mute")) {
                                                        z = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            z = false;
                                            if (z) {
                                                z2 = true;
                                                break;
                                            }
                                        }
                                    }
                                    MuteQuickAffordanceCoreStartable muteQuickAffordanceCoreStartable = this.this$0;
                                    if (z2) {
                                        ((RingerModeTrackerImpl) muteQuickAffordanceCoreStartable.ringerModeTracker).ringerModeInternal.observeForever(muteQuickAffordanceCoreStartable.observer);
                                    } else {
                                        ((RingerModeTrackerImpl) muteQuickAffordanceCoreStartable.ringerModeTracker).ringerModeInternal.removeObserver(muteQuickAffordanceCoreStartable.observer);
                                    }
                                    Unit unit = Unit.INSTANCE;
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
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
                    Object collect = Flow.this.collect(new C15792(flowCollector, this), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, this.coroutineScope);
        }
    }
}
