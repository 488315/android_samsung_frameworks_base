package com.android.systemui.shared.customization.data.content;

import android.content.Context;
import com.android.systemui.shared.customization.data.content.CustomizationProviderContract;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CustomizationProviderClientImpl implements CustomizationProviderClient {
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;

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

    public CustomizationProviderClientImpl(Context context, CoroutineDispatcher coroutineDispatcher) {
        this.context = context;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public final Object deleteAllSelections(String str, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundDispatcher, new CustomizationProviderClientImpl$deleteAllSelections$2(this, str, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    public final Object insertSelection(String str, String str2, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundDispatcher, new CustomizationProviderClientImpl$insertSelection$2(this, str, str2, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1] */
    public final CustomizationProviderClientImpl$observeSelections$$inlined$map$1 observeSelections() {
        CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.INSTANCE.getClass();
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CustomizationProviderClientImpl$observeUri$2(null), FlowKt.callbackFlow(new CustomizationProviderClientImpl$observeUri$1(this, CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.URI, null)));
        return new Flow() { // from class: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1$2 */
            public final class C24792 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CustomizationProviderClientImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1$2", m277f = "CustomizationProviderClient.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1$2$1, reason: invalid class name */
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
                        return C24792.this.emit(null, this);
                    }
                }

                public C24792(FlowCollector flowCollector, CustomizationProviderClientImpl customizationProviderClientImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = customizationProviderClientImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:19:0x005a A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object obj2;
                    CoroutineSingletons coroutineSingletons;
                    int i;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            obj2 = anonymousClass1.result;
                            coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                FlowCollector flowCollector2 = this.$this_unsafeFlow;
                                anonymousClass1.L$0 = flowCollector2;
                                anonymousClass1.label = 1;
                                obj2 = this.this$0.querySelections(anonymousClass1);
                                if (obj2 == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                                flowCollector = flowCollector2;
                            } else {
                                if (i != 1) {
                                    if (i != 2) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                    return Unit.INSTANCE;
                                }
                                flowCollector = (FlowCollector) anonymousClass1.L$0;
                                ResultKt.throwOnFailure(obj2);
                            }
                            anonymousClass1.L$0 = null;
                            anonymousClass1.label = 2;
                            if (flowCollector.emit(obj2, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    obj2 = anonymousClass1.result;
                    coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                    if (flowCollector.emit(obj2, anonymousClass1) == coroutineSingletons) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C24792(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object querySelections(Continuation continuation) {
        CustomizationProviderClientImpl$querySelections$1 customizationProviderClientImpl$querySelections$1;
        int i;
        if (continuation instanceof CustomizationProviderClientImpl$querySelections$1) {
            customizationProviderClientImpl$querySelections$1 = (CustomizationProviderClientImpl$querySelections$1) continuation;
            int i2 = customizationProviderClientImpl$querySelections$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                customizationProviderClientImpl$querySelections$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = customizationProviderClientImpl$querySelections$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = customizationProviderClientImpl$querySelections$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    CustomizationProviderClientImpl$querySelections$2 customizationProviderClientImpl$querySelections$2 = new CustomizationProviderClientImpl$querySelections$2(this, null);
                    customizationProviderClientImpl$querySelections$1.label = 1;
                    obj = BuildersKt.withContext(this.backgroundDispatcher, customizationProviderClientImpl$querySelections$2, customizationProviderClientImpl$querySelections$1);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                List list = (List) obj;
                return list != null ? EmptyList.INSTANCE : list;
            }
        }
        customizationProviderClientImpl$querySelections$1 = new CustomizationProviderClientImpl$querySelections$1(this, continuation);
        Object obj2 = customizationProviderClientImpl$querySelections$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = customizationProviderClientImpl$querySelections$1.label;
        if (i != 0) {
        }
        List list2 = (List) obj2;
        if (list2 != null) {
        }
    }
}
