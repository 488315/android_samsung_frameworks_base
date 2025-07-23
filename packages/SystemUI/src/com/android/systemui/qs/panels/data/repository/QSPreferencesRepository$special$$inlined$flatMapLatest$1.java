package com.android.systemui.qs.panels.data.repository;

import android.content.SharedPreferences;
import android.content.pm.UserInfo;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import com.android.systemui.util.kotlin.SharedPreferencesExt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSPreferencesRepository$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ QSPreferencesRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSPreferencesRepository$special$$inlined$flatMapLatest$1(Continuation continuation, QSPreferencesRepository qSPreferencesRepository) {
        super(3, continuation);
        this.this$0 = qSPreferencesRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        QSPreferencesRepository$special$$inlined$flatMapLatest$1 qSPreferencesRepository$special$$inlined$flatMapLatest$1 = new QSPreferencesRepository$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        qSPreferencesRepository$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        qSPreferencesRepository$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return qSPreferencesRepository$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            UserInfo userInfo = (UserInfo) ((Pair) this.L$1).component2();
            QSPreferencesRepository qSPreferencesRepository = this.this$0;
            int i2 = userInfo.id;
            int i3 = QSPreferencesRepository.$r8$clinit;
            final SharedPreferences sharedPreferences$1 = ((UserFileManagerImpl) qSPreferencesRepository.userFileManager).getSharedPreferences$1(i2, "quick_settings_prefs");
            final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), SharedPreferencesExt.INSTANCE.observe(sharedPreferences$1));
            final QSPreferencesRepository qSPreferencesRepository2 = this.this$0;
            Flow flow = new Flow() { // from class: com.android.systemui.qs.panels.data.repository.QSPreferencesRepository$largeTilesSpecs$lambda$5$$inlined$map$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.qs.panels.data.repository.QSPreferencesRepository$largeTilesSpecs$lambda$5$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ SharedPreferences $prefs$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ QSPreferencesRepository this$0;

                    /* renamed from: com.android.systemui.qs.panels.data.repository.QSPreferencesRepository$largeTilesSpecs$lambda$5$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, SharedPreferences sharedPreferences, QSPreferencesRepository qSPreferencesRepository) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$prefs$inlined = sharedPreferences;
                        this.this$0 = qSPreferencesRepository;
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:28:0x009f, code lost:
                    
                        if (r8 != null) goto L28;
                     */
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                        /*
                            r7 = this;
                            boolean r0 = r9 instanceof com.android.systemui.qs.panels.data.repository.QSPreferencesRepository$largeTilesSpecs$lambda$5$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r9
                            com.android.systemui.qs.panels.data.repository.QSPreferencesRepository$largeTilesSpecs$lambda$5$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.panels.data.repository.QSPreferencesRepository$largeTilesSpecs$lambda$5$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.qs.panels.data.repository.QSPreferencesRepository$largeTilesSpecs$lambda$5$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.panels.data.repository.QSPreferencesRepository$largeTilesSpecs$lambda$5$$inlined$map$1$2$1
                            r0.<init>(r9)
                        L18:
                            java.lang.Object r9 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L30
                            if (r2 != r3) goto L28
                            kotlin.ResultKt.throwOnFailure(r9)
                            goto Lb3
                        L28:
                            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                            r7.<init>(r8)
                            throw r7
                        L30:
                            kotlin.ResultKt.throwOnFailure(r9)
                            kotlin.Unit r8 = (kotlin.Unit) r8
                            android.content.SharedPreferences r8 = r7.$prefs$inlined
                            com.android.systemui.qs.panels.data.repository.QSPreferencesRepository r9 = r7.this$0
                            com.android.systemui.qs.panels.data.repository.DefaultLargeTilesRepository r2 = r9.defaultLargeTilesRepository
                            com.android.systemui.qs.panels.data.repository.DefaultLargeTilesRepositoryImpl r2 = (com.android.systemui.qs.panels.data.repository.DefaultLargeTilesRepositoryImpl) r2
                            java.util.Set r2 = r2.defaultLargeTiles
                            java.lang.Iterable r2 = (java.lang.Iterable) r2
                            java.util.ArrayList r4 = new java.util.ArrayList
                            r5 = 10
                            int r6 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r2, r5)
                            r4.<init>(r6)
                            java.util.Iterator r2 = r2.iterator()
                        L50:
                            boolean r6 = r2.hasNext()
                            if (r6 == 0) goto L64
                            java.lang.Object r6 = r2.next()
                            com.android.systemui.qs.pipeline.shared.TileSpec r6 = (com.android.systemui.qs.pipeline.shared.TileSpec) r6
                            java.lang.String r6 = r6.getSpec()
                            r4.add(r6)
                            goto L50
                        L64:
                            java.util.Set r2 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r4)
                            java.lang.String r4 = "large_tiles_specs"
                            java.util.Set r8 = r8.getStringSet(r4, r2)
                            if (r8 == 0) goto La2
                            java.lang.Iterable r8 = (java.lang.Iterable) r8
                            java.util.ArrayList r2 = new java.util.ArrayList
                            int r4 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r8, r5)
                            r2.<init>(r4)
                            java.util.Iterator r8 = r8.iterator()
                        L7f:
                            boolean r4 = r8.hasNext()
                            if (r4 == 0) goto L9b
                            java.lang.Object r4 = r8.next()
                            java.lang.String r4 = (java.lang.String) r4
                            com.android.systemui.qs.pipeline.shared.TileSpec$Companion r5 = com.android.systemui.qs.pipeline.shared.TileSpec.Companion
                            r4.getClass()
                            r5.getClass()
                            com.android.systemui.qs.pipeline.shared.TileSpec r4 = com.android.systemui.qs.pipeline.shared.TileSpec.Companion.create(r4)
                            r2.add(r4)
                            goto L7f
                        L9b:
                            java.util.Set r8 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r2)
                            if (r8 == 0) goto La2
                            goto La8
                        La2:
                            com.android.systemui.qs.panels.data.repository.DefaultLargeTilesRepository r8 = r9.defaultLargeTilesRepository
                            com.android.systemui.qs.panels.data.repository.DefaultLargeTilesRepositoryImpl r8 = (com.android.systemui.qs.panels.data.repository.DefaultLargeTilesRepositoryImpl) r8
                            java.util.Set r8 = r8.defaultLargeTiles
                        La8:
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                            java.lang.Object r7 = r7.emit(r8, r0)
                            if (r7 != r1) goto Lb3
                            return r1
                        Lb3:
                            kotlin.Unit r7 = kotlin.Unit.INSTANCE
                            return r7
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.data.repository.QSPreferencesRepository$largeTilesSpecs$lambda$5$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, sharedPreferences$1, qSPreferencesRepository2), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flow, this) == coroutineSingletons) {
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
