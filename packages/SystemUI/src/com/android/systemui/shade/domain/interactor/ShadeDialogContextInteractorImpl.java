package com.android.systemui.shade.domain.interactor;

import android.content.Context;
import android.os.Trace;
import android.util.Log;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepositoryImpl;
import com.android.systemui.display.shared.model.DisplayWindowProperties;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepository;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import javax.inject.Provider;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class ShadeDialogContextInteractorImpl implements CoreStartable, ShadeDialogContextInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope bgScope;
    public final Context defaultContext;
    public final Provider displayWindowPropertyRepository;
    public final Provider shadeDisplaysRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl$start$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            /* synthetic */ int I$0;
            int label;
            final /* synthetic */ ShadeDialogContextInteractorImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(ShadeDialogContextInteractorImpl shadeDialogContextInteractorImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = shadeDialogContextInteractorImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, continuation);
                anonymousClass2.I$0 = ((Number) obj).intValue();
                return anonymousClass2;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                int i = this.I$0;
                ShadeDialogContextInteractorImpl shadeDialogContextInteractorImpl = this.this$0;
                int i2 = ShadeDialogContextInteractorImpl.$r8$clinit;
                shadeDialogContextInteractorImpl.getContextOrDefault(i);
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShadeDialogContextInteractorImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final StateFlowImpl stateFlowImpl = ((ShadeDisplaysRepositoryImpl) ((ShadeDisplaysRepository) ShadeDialogContextInteractorImpl.this.shadeDisplaysRepository.get())).displayId;
                Flow flow = new Flow() { // from class: com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl$start$1$invokeSuspend$$inlined$filter$1

                    /* renamed from: com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl$start$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl$start$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            Object L$1;
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
                                if (((Number) obj).intValue() != 0) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
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
                        Object objCollect = stateFlowImpl.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(ShadeDialogContextInteractorImpl.this, null);
                this.label = 1;
                if (FlowKt.collectLatest(flow, anonymousClass2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public ShadeDialogContextInteractorImpl(Context context, Provider provider, Provider provider2, CoroutineScope coroutineScope) {
        this.defaultContext = context;
        this.displayWindowPropertyRepository = provider;
        this.shadeDisplaysRepository = provider2;
        this.bgScope = coroutineScope;
    }

    public final Context getContext() {
        ShadeWindowGoesAround.INSTANCE.getClass();
        return !ShadeWindowGoesAround.FLAG.isTrue() ? this.defaultContext : getContextOrDefault(((Number) ((ShadeDisplaysRepositoryImpl) ((ShadeDisplaysRepository) this.shadeDisplaysRepository.get())).displayId.getValue()).intValue());
    }

    public final Context getContextOrDefault(int i) {
        Context context;
        try {
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice("Getting dialog context for displayId=" + i);
            }
            try {
                DisplayWindowProperties displayWindowProperties = ((DisplayWindowPropertiesRepositoryImpl) ((DisplayWindowPropertiesRepository) this.displayWindowPropertyRepository.get())).get(i, 2017);
                if (displayWindowProperties == null) {
                    Log.e("ShadeDialogContextRepo", "DisplayWindowPropertiesRepository returned null for display " + i + ". Returning default one");
                    context = this.defaultContext;
                } else {
                    context = displayWindowProperties.context;
                }
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                return context;
            } catch (Throwable th) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e("ShadeDialogContextRepo", "Couldn't get dialog context for displayId=" + i + ". Returning default one", e);
            return this.defaultContext;
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ShadeWindowGoesAround.INSTANCE.getClass();
        boolean zIsTrue = ShadeWindowGoesAround.FLAG.isTrue();
        if (!zIsTrue) {
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.shade_window_goes_around to be enabled.");
        }
        if (zIsTrue) {
            CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new AnonymousClass1(null), 6);
        }
    }
}
