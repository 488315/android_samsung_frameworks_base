package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.RingerModeTracker;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class MuteQuickAffordanceCoreStartable implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineDispatcher backgroundDispatcher;
    public final CoroutineScope coroutineScope;
    public final KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository;
    public final MuteQuickAffordanceCoreStartable$observer$1 observer = new MuteQuickAffordanceCoreStartable$observer$1(this);
    public final RingerModeTracker ringerModeTracker;
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;

    public MuteQuickAffordanceCoreStartable(UserTracker userTracker, RingerModeTracker ringerModeTracker, UserFileManager userFileManager, KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.userTracker = userTracker;
        this.ringerModeTracker = ringerModeTracker;
        this.userFileManager = userFileManager;
        this.keyguardQuickAffordanceRepository = keyguardQuickAffordanceRepository;
        this.coroutineScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        final ReadonlyStateFlow readonlyStateFlow = this.keyguardQuickAffordanceRepository.selections;
        FlowKt.launchIn(new Flow() { // from class: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MuteQuickAffordanceCoreStartable this$0;

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
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, MuteQuickAffordanceCoreStartable muteQuickAffordanceCoreStartable) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = muteQuickAffordanceCoreStartable;
                }

                /* JADX WARN: Removed duplicated region for block: B:36:0x00aa A[RETURN] */
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
                        Collection<List> collectionValues = ((Map) obj).values();
                        boolean z = collectionValues instanceof Collection;
                        MuteQuickAffordanceCoreStartable muteQuickAffordanceCoreStartable = this.this$0;
                        if (z && collectionValues.isEmpty()) {
                            muteQuickAffordanceCoreStartable.ringerModeTracker.getRingerModeInternal().removeObserver(muteQuickAffordanceCoreStartable.observer);
                            unit = Unit.INSTANCE;
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
                            }
                        } else {
                            loop0: for (List list : collectionValues) {
                                if (!(list instanceof Collection) || !list.isEmpty()) {
                                    Iterator it = list.iterator();
                                    while (it.hasNext()) {
                                        if (Intrinsics.areEqual(((KeyguardQuickAffordanceConfig) it.next()).getKey(), "mute")) {
                                            muteQuickAffordanceCoreStartable.ringerModeTracker.getRingerModeInternal().observeForever(muteQuickAffordanceCoreStartable.observer);
                                            break loop0;
                                        }
                                    }
                                }
                            }
                            muteQuickAffordanceCoreStartable.ringerModeTracker.getRingerModeInternal().removeObserver(muteQuickAffordanceCoreStartable.observer);
                            unit = Unit.INSTANCE;
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, this.coroutineScope);
    }
}
