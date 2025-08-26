package com.android.systemui.qs.panels.data.repository;

import android.content.SharedPreferences;
import android.content.pm.UserInfo;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.SharedPreferencesExt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

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
            final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), SharedPreferencesExt.INSTANCE.observe(sharedPreferences$1));
            final QSPreferencesRepository qSPreferencesRepository2 = this.this$0;
            Flow flow = new Flow() { // from class: com.android.systemui.qs.panels.data.repository.QSPreferencesRepository$largeTilesSpecs$lambda$5$$inlined$map$1

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

                    /* JADX WARN: Removed duplicated region for block: B:27:0x00a2  */
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
                            SharedPreferences sharedPreferences = this.$prefs$inlined;
                            QSPreferencesRepository qSPreferencesRepository = this.this$0;
                            Set set = ((DefaultLargeTilesRepositoryImpl) qSPreferencesRepository.defaultLargeTilesRepository).defaultLargeTiles;
                            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
                            Iterator it = set.iterator();
                            while (it.hasNext()) {
                                arrayList.add(((TileSpec) it.next()).getSpec());
                            }
                            Set<String> stringSet = sharedPreferences.getStringSet("large_tiles_specs", CollectionsKt___CollectionsKt.toSet(arrayList));
                            if (stringSet != null) {
                                Set<String> set2 = stringSet;
                                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
                                for (String str : set2) {
                                    TileSpec.Companion companion = TileSpec.Companion;
                                    str.getClass();
                                    companion.getClass();
                                    arrayList2.add(TileSpec.Companion.create(str));
                                }
                                Set set3 = CollectionsKt___CollectionsKt.toSet(arrayList2);
                                if (set3 == null) {
                                    set3 = ((DefaultLargeTilesRepositoryImpl) qSPreferencesRepository.defaultLargeTilesRepository).defaultLargeTiles;
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(set3, anonymousClass1) == coroutineSingletons) {
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
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector2, sharedPreferences$1, qSPreferencesRepository2), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (kotlinx.coroutines.flow.FlowKt.emitAll(flowCollector, flow, this) == coroutineSingletons) {
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
