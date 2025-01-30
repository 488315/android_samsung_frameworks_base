package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.util.settings.SecureSettingsImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1", m277f = "TileSpecRepository.kt", m278l = {190}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ int $userId$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ TileSpecSettingsRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1(Continuation continuation, TileSpecSettingsRepository tileSpecSettingsRepository, int i) {
        super(3, continuation);
        this.this$0 = tileSpecSettingsRepository;
        this.$userId$inlined = i;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1 tileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1 = new TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$userId$inlined);
        tileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        tileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1.L$1 = obj2;
        return tileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowOn;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                this.this$0.logger.logUsingRetailTiles();
                flowOn = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2((List) this.this$0.retailModeTiles$delegate.getValue());
            } else {
                final TileSpecSettingsRepository tileSpecSettingsRepository = this.this$0;
                final int i2 = this.$userId$inlined;
                int i3 = TileSpecSettingsRepository.$r8$clinit;
                tileSpecSettingsRepository.getClass();
                ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
                TileSpecSettingsRepository$settingsTiles$1 tileSpecSettingsRepository$settingsTiles$1 = new TileSpecSettingsRepository$settingsTiles$1(tileSpecSettingsRepository, i2, null);
                conflatedCallbackFlow.getClass();
                final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new TileSpecSettingsRepository$settingsTiles$2(null), ConflatedCallbackFlow.conflatedCallbackFlow(tileSpecSettingsRepository$settingsTiles$1));
                final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$settingsTiles$$inlined$map$1

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    /* renamed from: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$settingsTiles$$inlined$map$1$2 */
                    public final class C21902 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ int $userId$inlined;
                        public final /* synthetic */ TileSpecSettingsRepository this$0;

                        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                        @DebugMetadata(m276c = "com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$settingsTiles$$inlined$map$1$2", m277f = "TileSpecRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                        /* renamed from: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$settingsTiles$$inlined$map$1$2$1, reason: invalid class name */
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
                                return C21902.this.emit(null, this);
                            }
                        }

                        public C21902(FlowCollector flowCollector, TileSpecSettingsRepository tileSpecSettingsRepository, int i) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = tileSpecSettingsRepository;
                            this.$userId$inlined = i;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
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
                                        String stringForUser = ((SecureSettingsImpl) this.this$0.secureSettings).getStringForUser(this.$userId$inlined, "sysui_qs_tiles");
                                        if (stringForUser == null) {
                                            stringForUser = "";
                                        }
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(stringForUser, anonymousClass1) == coroutineSingletons) {
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
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new C21902(flowCollector2, tileSpecSettingsRepository, i2), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                }), new TileSpecSettingsRepository$settingsTiles$4(tileSpecSettingsRepository, i2, null));
                flowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$settingsTiles$$inlined$map$2

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    /* renamed from: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$settingsTiles$$inlined$map$2$2 */
                    public final class C21912 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ int $userId$inlined;
                        public final /* synthetic */ TileSpecSettingsRepository this$0;

                        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                        @DebugMetadata(m276c = "com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$settingsTiles$$inlined$map$2$2", m277f = "TileSpecRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                        /* renamed from: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$settingsTiles$$inlined$map$2$2$1, reason: invalid class name */
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
                                return C21912.this.emit(null, this);
                            }
                        }

                        public C21912(FlowCollector flowCollector, TileSpecSettingsRepository tileSpecSettingsRepository, int i) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = tileSpecSettingsRepository;
                            this.$userId$inlined = i;
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
                                        int i3 = TileSpecSettingsRepository.$r8$clinit;
                                        TileSpecSettingsRepository tileSpecSettingsRepository = this.this$0;
                                        tileSpecSettingsRepository.getClass();
                                        List<String> split$default = StringsKt__StringsKt.split$default((String) obj, new String[]{","}, 0, 6);
                                        TileSpec.Companion companion = TileSpec.Companion;
                                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split$default, 10));
                                        for (String str : split$default) {
                                            companion.getClass();
                                            arrayList.add(TileSpec.Companion.create(str));
                                        }
                                        ArrayList arrayList2 = new ArrayList();
                                        Iterator it = arrayList.iterator();
                                        while (it.hasNext()) {
                                            Object next = it.next();
                                            if (!Intrinsics.areEqual((TileSpec) next, TileSpec.Invalid.INSTANCE)) {
                                                arrayList2.add(next);
                                            }
                                        }
                                        boolean z = !arrayList2.isEmpty();
                                        int i4 = this.$userId$inlined;
                                        QSPipelineLogger qSPipelineLogger = tileSpecSettingsRepository.logger;
                                        if (z) {
                                            qSPipelineLogger.logParsedTiles(arrayList2, i4, false);
                                        } else {
                                            List defaultSpecs = QSHost.getDefaultSpecs(tileSpecSettingsRepository.resources);
                                            TileSpec.Companion companion2 = TileSpec.Companion;
                                            ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(defaultSpecs, 10));
                                            Iterator it2 = ((ArrayList) defaultSpecs).iterator();
                                            while (it2.hasNext()) {
                                                String str2 = (String) it2.next();
                                                companion2.getClass();
                                                arrayList3.add(TileSpec.Companion.create(str2));
                                            }
                                            arrayList2 = new ArrayList();
                                            Iterator it3 = arrayList3.iterator();
                                            while (it3.hasNext()) {
                                                Object next2 = it3.next();
                                                if (!Intrinsics.areEqual((TileSpec) next2, TileSpec.Invalid.INSTANCE)) {
                                                    arrayList2.add(next2);
                                                }
                                            }
                                            qSPipelineLogger.logParsedTiles(arrayList2, i4, true);
                                        }
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(arrayList2, anonymousClass1) == coroutineSingletons) {
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
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new C21912(flowCollector2, tileSpecSettingsRepository, i2), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                }, tileSpecSettingsRepository.backgroundDispatcher);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flowOn, flowCollector) == coroutineSingletons) {
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
