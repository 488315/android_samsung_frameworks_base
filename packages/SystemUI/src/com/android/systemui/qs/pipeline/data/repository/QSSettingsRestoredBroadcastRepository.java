package com.android.systemui.qs.pipeline.data.repository;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.pipeline.data.model.RestoreData;
import com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__MergeKt;
import kotlinx.coroutines.flow.FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes2.dex */
public final class QSSettingsRestoredBroadcastRepository implements QSSettingsRestoredRepository {
    public static final Companion Companion = new Companion(null);
    public static final IntentFilter INTENT_FILTER = new IntentFilter("android.os.action.SETTING_RESTORED");
    public static final List requiredExtras = Arrays.asList("setting_name", "previous_value", "new_value");
    public final DeviceProvisionedController deviceProvisionedController;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 onUserSetupChangedForSomeUser;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 restoreData;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final void access$validateIntent(Companion companion, Intent intent) {
            companion.getClass();
            for (String str : QSSettingsRestoredBroadcastRepository.requiredExtras) {
                if (!intent.hasExtra(str)) {
                    throw new IllegalStateException(intent + " doesn't have " + str);
                }
            }
        }

        private Companion() {
        }
    }

    public QSSettingsRestoredBroadcastRepository(BroadcastDispatcher broadcastDispatcher, DeviceProvisionedController deviceProvisionedController, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.deviceProvisionedController = deviceProvisionedController;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), FlowConflatedKt.conflatedCallbackFlow(new QSSettingsRestoredBroadcastRepository$onUserSetupChangedForSomeUser$1(this, null)));
        this.onUserSetupChangedForSomeUser = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        final MutexImpl mutexImplMutex$default = MutexKt.Mutex$default();
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        final Flow flowBroadcastFlow$default = BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, INTENT_FILTER, UserHandle.ALL, new QSSettingsRestoredBroadcastRepository$$ExternalSyntheticLambda0(), 12);
        final Flow flow = new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$filter$1

            /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$filter$1$2$1, reason: invalid class name */
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
                        QSSettingsRestoredBroadcastRepository.Companion companion = QSSettingsRestoredBroadcastRepository.Companion;
                        Intent intent = (Intent) ((Pair) obj).getFirst();
                        companion.getClass();
                        String stringExtra = intent.getStringExtra("setting_name");
                        if (Intrinsics.areEqual(stringExtra, "sysui_qs_tiles") || Intrinsics.areEqual(stringExtra, "qs_auto_tiles")) {
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
                Object objCollect = flowBroadcastFlow$default.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 flowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 = new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$mapNotNull$1

            /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Map $firstIntent$inlined;
                public final /* synthetic */ Mutex $mutex$inlined;
                public final /* synthetic */ QSSettingsRestoredBroadcastRepository $this_run$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$mapNotNull$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    int I$0;
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    Object L$3;
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

                public AnonymousClass2(FlowCollector flowCollector, Mutex mutex, Map map, QSSettingsRestoredBroadcastRepository qSSettingsRestoredBroadcastRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$mutex$inlined = mutex;
                    this.$firstIntent$inlined = map;
                    this.$this_run$inlined = qSSettingsRestoredBroadcastRepository;
                }

                /* JADX WARN: Code restructure failed: missing block: B:30:0x00c0, code lost:
                
                    if (r4.emit(r8, r0) == r1) goto L31;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Intent intent;
                    AnonymousClass2 anonymousClass2;
                    int i;
                    Mutex mutex;
                    FlowCollector flowCollector;
                    RestoreData restoreDataAccess$processIntents;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i2 - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i3 = anonymousClass1.label;
                    try {
                        if (i3 == 0) {
                            ResultKt.throwOnFailure(obj2);
                            Pair pair = (Pair) obj;
                            intent = (Intent) pair.component1();
                            int iIntValue = ((Number) pair.component2()).intValue();
                            anonymousClass1.L$0 = this;
                            FlowCollector flowCollector2 = this.$this_unsafeFlow;
                            anonymousClass1.L$1 = flowCollector2;
                            anonymousClass1.L$2 = intent;
                            Mutex mutex2 = this.$mutex$inlined;
                            anonymousClass1.L$3 = mutex2;
                            anonymousClass1.I$0 = iIntValue;
                            anonymousClass1.label = 1;
                            if (mutex2.lock(anonymousClass1) != coroutineSingletons) {
                                anonymousClass2 = this;
                                i = iIntValue;
                                mutex = mutex2;
                                flowCollector = flowCollector2;
                            }
                            return coroutineSingletons;
                        }
                        if (i3 != 1) {
                            if (i3 != 2) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                            return Unit.INSTANCE;
                        }
                        i = anonymousClass1.I$0;
                        mutex = (Mutex) anonymousClass1.L$3;
                        intent = (Intent) anonymousClass1.L$2;
                        flowCollector = (FlowCollector) anonymousClass1.L$1;
                        anonymousClass2 = (AnonymousClass2) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(obj2);
                        if (anonymousClass2.$firstIntent$inlined.containsKey(new Integer(i))) {
                            Object objRemove = anonymousClass2.$firstIntent$inlined.remove(new Integer(i));
                            objRemove.getClass();
                            restoreDataAccess$processIntents = QSSettingsRestoredBroadcastRepository.access$processIntents(anonymousClass2.$this_run$inlined, i, (Intent) objRemove, intent);
                        } else {
                            anonymousClass2.$firstIntent$inlined.put(new Integer(i), intent);
                            restoreDataAccess$processIntents = null;
                        }
                        mutex.unlock(null);
                        if (restoreDataAccess$processIntents != null) {
                            anonymousClass1.L$0 = null;
                            anonymousClass1.L$1 = null;
                            anonymousClass1.L$2 = null;
                            anonymousClass1.L$3 = null;
                            anonymousClass1.label = 2;
                        }
                        return Unit.INSTANCE;
                    } catch (Throwable th) {
                        mutex.unlock(null);
                        throw th;
                    }
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector, mutexImplMutex$default, linkedHashMap, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new QSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromTwoBroadcasts$4(null));
        Flow flow2 = new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$map$1

            /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Map $firstIntent$inlined;
                public final /* synthetic */ Mutex $mutex$inlined;
                public final /* synthetic */ QSSettingsRestoredBroadcastRepository $this_run$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository$restoreData$lambda$9$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    Object L$2;
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

                public AnonymousClass2(FlowCollector flowCollector, Mutex mutex, Map map, QSSettingsRestoredBroadcastRepository qSSettingsRestoredBroadcastRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$mutex$inlined = mutex;
                    this.$firstIntent$inlined = map;
                    this.$this_run$inlined = qSSettingsRestoredBroadcastRepository;
                }

                /* JADX WARN: Code restructure failed: missing block: B:39:0x0105, code lost:
                
                    if (r10.emit(r4, r0) == r1) goto L40;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    Mutex mutex;
                    QSSettingsRestoredBroadcastRepository qSSettingsRestoredBroadcastRepository;
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
                    try {
                        if (i2 == 0) {
                            ResultKt.throwOnFailure(obj2);
                            anonymousClass1.L$0 = this;
                            flowCollector = this.$this_unsafeFlow;
                            anonymousClass1.L$1 = flowCollector;
                            mutex = this.$mutex$inlined;
                            anonymousClass1.L$2 = mutex;
                            anonymousClass1.label = 1;
                            if (mutex.lock(anonymousClass1) != coroutineSingletons) {
                            }
                            return coroutineSingletons;
                        }
                        if (i2 != 1) {
                            if (i2 != 2) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                            return Unit.INSTANCE;
                        }
                        Mutex mutex2 = (Mutex) anonymousClass1.L$2;
                        flowCollector = (FlowCollector) anonymousClass1.L$1;
                        AnonymousClass2 anonymousClass2 = (AnonymousClass2) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(obj2);
                        mutex = mutex2;
                        this = anonymousClass2;
                        Map map = this.$firstIntent$inlined;
                        LinkedHashMap linkedHashMap = new LinkedHashMap();
                        Iterator it = map.entrySet().iterator();
                        while (true) {
                            boolean zHasNext = it.hasNext();
                            qSSettingsRestoredBroadcastRepository = this.$this_run$inlined;
                            if (!zHasNext) {
                                break;
                            }
                            Map.Entry entry = (Map.Entry) it.next();
                            if (((DeviceProvisionedControllerImpl) qSSettingsRestoredBroadcastRepository.deviceProvisionedController).isUserSetup(((Number) entry.getKey()).intValue())) {
                                linkedHashMap.put(entry.getKey(), entry.getValue());
                            }
                        }
                        Iterator it2 = linkedHashMap.entrySet().iterator();
                        while (it2.hasNext()) {
                            this.$firstIntent$inlined.remove(((Map.Entry) it2.next()).getKey());
                        }
                        ArrayList arrayList = new ArrayList(linkedHashMap.size());
                        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
                            arrayList.add(QSSettingsRestoredBroadcastRepository.access$processSingleIntent(qSSettingsRestoredBroadcastRepository, ((Number) entry2.getKey()).intValue(), (Intent) entry2.getValue()));
                        }
                        FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3 flowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3 = new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3(arrayList);
                        mutex.unlock(null);
                        anonymousClass1.L$0 = null;
                        anonymousClass1.L$1 = null;
                        anonymousClass1.L$2 = null;
                        anonymousClass1.label = 2;
                    } catch (Throwable th) {
                        mutex.unlock(null);
                        throw th;
                    }
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, mutexImplMutex$default, linkedHashMap, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        int i = FlowKt__MergeKt.$r8$clinit;
        Flow flowBuffer$default = kotlinx.coroutines.flow.FlowKt.buffer$default(kotlinx.coroutines.flow.FlowKt.flowOn(kotlinx.coroutines.flow.FlowKt.merge(flowKt__ErrorsKt$catch$$inlined$unsafeFlow$1, new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(new FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1(flow2), new QSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromUserSetup$2(null)), new QSSettingsRestoredBroadcastRepository$restoreData$1$restoresFromUserSetup$3(qSPipelineLogger, null))), coroutineDispatcher), 10, 2);
        SharingStarted.Companion.getClass();
        this.restoreData = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(kotlinx.coroutines.flow.FlowKt.shareIn(flowBuffer$default, coroutineScope, SharingStarted.Companion.Eagerly, 0), new QSSettingsRestoredBroadcastRepository$restoreData$2(qSPipelineLogger));
    }

    public static final RestoreData access$processIntents(QSSettingsRestoredBroadcastRepository qSSettingsRestoredBroadcastRepository, int i, Intent intent, Intent intent2) {
        Pair pair;
        qSSettingsRestoredBroadcastRepository.getClass();
        Companion companion = Companion;
        Companion.access$validateIntent(companion, intent);
        Companion.access$validateIntent(companion, intent2);
        String stringExtra = intent.getStringExtra("setting_name");
        String stringExtra2 = intent2.getStringExtra("setting_name");
        if (Intrinsics.areEqual(stringExtra, "sysui_qs_tiles") && Intrinsics.areEqual(stringExtra2, "qs_auto_tiles")) {
            pair = new Pair(intent, intent2);
        } else {
            if (!Intrinsics.areEqual(stringExtra, "qs_auto_tiles") || !Intrinsics.areEqual(stringExtra2, "sysui_qs_tiles")) {
                throw new IllegalStateException("Wrong intents (" + intent + ", " + intent2 + ")");
            }
            pair = new Pair(intent2, intent);
        }
        Intent intent3 = (Intent) pair.component1();
        Intent intent4 = (Intent) pair.component2();
        String stringExtra3 = intent3.getStringExtra("new_value");
        if (stringExtra3 == null) {
            stringExtra3 = "";
        }
        TilesSettingConverter tilesSettingConverter = TilesSettingConverter.INSTANCE;
        tilesSettingConverter.getClass();
        List tilesList = TilesSettingConverter.toTilesList(stringExtra3);
        String stringExtra4 = intent4.getStringExtra("new_value");
        String str = stringExtra4 != null ? stringExtra4 : "";
        tilesSettingConverter.getClass();
        return new RestoreData(tilesList, TilesSettingConverter.toTilesSet(str), i);
    }

    public static final RestoreData access$processSingleIntent(QSSettingsRestoredBroadcastRepository qSSettingsRestoredBroadcastRepository, int i, Intent intent) {
        qSSettingsRestoredBroadcastRepository.getClass();
        Companion.access$validateIntent(Companion, intent);
        if (Intrinsics.areEqual(intent.getStringExtra("setting_name"), "sysui_qs_tiles")) {
            String stringExtra = intent.getStringExtra("new_value");
            if (stringExtra == null) {
                stringExtra = "";
            }
            TilesSettingConverter.INSTANCE.getClass();
            return new RestoreData(TilesSettingConverter.toTilesList(stringExtra), EmptySet.INSTANCE, i);
        }
        throw new IllegalStateException("Single intent restored for user " + i + " is not tiles: " + intent);
    }
}
