package com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.mediarouter.data.repository.MediaRouterRepository;
import com.android.systemui.mediarouter.data.repository.MediaRouterRepositoryImpl;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.casttootherdevice.domain.model.MediaRouterCastModel;
import com.android.systemui.statusbar.policy.CastDevice;
import java.util.Iterator;
import java.util.List;
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

/* loaded from: classes3.dex */
public final class MediaRouterChipInteractor {
    public static final String TAG;
    public final ReadonlyStateFlow activeCastDevice;
    public final LogBuffer logger;
    public final ReadonlyStateFlow mediaRouterCastingState;
    public final MediaRouterRepository mediaRouterRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        StatusBarChipLogTags.INSTANCE.getClass();
        TAG = StringsKt__StringsKt.padEnd(20, "MediaRouter");
    }

    public MediaRouterChipInteractor(CoroutineScope coroutineScope, MediaRouterRepository mediaRouterRepository, LogBuffer logBuffer) {
        this.mediaRouterRepository = mediaRouterRepository;
        this.logger = logBuffer;
        final ReadonlyStateFlow readonlyStateFlow = ((MediaRouterRepositoryImpl) mediaRouterRepository).castDevices;
        Flow flow = new Flow() { // from class: com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object next;
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
                        Iterator it = ((List) obj).iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it.next();
                            if (((CastDevice) next).isCasting) {
                                break;
                            }
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(next, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.activeCastDevice = readonlyStateFlowStateIn;
        this.mediaRouterCastingState = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaRouterChipInteractor this$0;

                /* renamed from: com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaRouterChipInteractor mediaRouterChipInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaRouterChipInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object casting;
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
                        CastDevice castDevice = (CastDevice) obj;
                        MediaRouterChipInteractor mediaRouterChipInteractor = this.this$0;
                        if (castDevice != null) {
                            LogBuffer logBuffer = mediaRouterChipInteractor.logger;
                            LogMessage logMessageObtain = logBuffer.obtain(MediaRouterChipInteractor.TAG, LogLevel.INFO, MediaRouterChipInteractor$mediaRouterCastingState$1$2.INSTANCE, null);
                            String str = castDevice.name;
                            ((LogMessageImpl) logMessageObtain).str1 = str;
                            logBuffer.commit(logMessageObtain);
                            casting = new MediaRouterCastModel.Casting(str);
                        } else {
                            LogBuffer logBuffer2 = mediaRouterChipInteractor.logger;
                            logBuffer2.commit(logBuffer2.obtain(MediaRouterChipInteractor.TAG, LogLevel.INFO, MediaRouterChipInteractor$mediaRouterCastingState$1$4.INSTANCE, null));
                            casting = MediaRouterCastModel.DoingNothing.INSTANCE;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(casting, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MediaRouterCastModel.DoingNothing.INSTANCE);
    }
}
