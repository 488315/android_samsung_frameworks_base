package com.android.wm.shell.desktopmode.persistence;

import android.content.Context;
import android.util.ArraySet;
import android.util.Log;
import androidx.datastore.DataStoreFile;
import androidx.datastore.core.CorruptionException;
import androidx.datastore.core.DataStore;
import androidx.datastore.core.DataStoreFactory;
import androidx.datastore.core.Serializer;
import androidx.datastore.core.UncloseableOutputStream;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import com.android.framework.protobuf.InvalidProtocolBufferException;
import com.android.wm.shell.desktopmode.persistence.Desktop;
import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepositories;
import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryState;
import com.android.wm.shell.desktopmode.persistence.DesktopTask;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class DesktopPersistentRepository {
    public static final Companion Companion = new Companion(null);
    public final DataStore dataStore;
    public final FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 dataStoreFlow;

    public final class Companion {

        public final class DesktopPersistentRepositoriesSerializer implements Serializer {
            public static final DesktopPersistentRepositoriesSerializer INSTANCE = new DesktopPersistentRepositoriesSerializer();
            public static final DesktopPersistentRepositories defaultValue = DesktopPersistentRepositories.getDefaultInstance();

            private DesktopPersistentRepositoriesSerializer() {
            }

            @Override // androidx.datastore.core.Serializer
            public final Object getDefaultValue() {
                return defaultValue;
            }

            @Override // androidx.datastore.core.Serializer
            public final Object readFrom(InputStream inputStream) throws CorruptionException {
                try {
                    return DesktopPersistentRepositories.parseFrom(inputStream);
                } catch (InvalidProtocolBufferException e) {
                    throw new CorruptionException("Cannot read proto.", e);
                }
            }

            @Override // androidx.datastore.core.Serializer
            public final Unit writeTo(Object obj, UncloseableOutputStream uncloseableOutputStream) throws IOException {
                ((DesktopPersistentRepositories) obj).writeTo(uncloseableOutputStream);
                return Unit.INSTANCE;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static DesktopTask createDesktopTask(int i, DesktopTaskState desktopTaskState, DesktopTaskTilingState desktopTaskTilingState) {
            DesktopTask.Builder builderNewBuilder = DesktopTask.newBuilder();
            builderNewBuilder.copyOnWrite();
            DesktopTask.m3263$$Nest$msetTaskId((DesktopTask) builderNewBuilder.instance, i);
            builderNewBuilder.copyOnWrite();
            DesktopTask.m3261$$Nest$msetDesktopTaskState((DesktopTask) builderNewBuilder.instance, desktopTaskState);
            builderNewBuilder.copyOnWrite();
            DesktopTask.m3262$$Nest$msetDesktopTaskTilingState((DesktopTask) builderNewBuilder.instance, desktopTaskTilingState);
            return (DesktopTask) builderNewBuilder.build();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$addOrUpdateDesktop$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DesktopPersistentRepository.this.addOrUpdateDesktop(0, 0, null, null, null, null, null, 0, 0, this);
        }
    }

    /* renamed from: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$addOrUpdateDesktop$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $desktopId;
        final /* synthetic */ int $displayId;
        final /* synthetic */ ArrayList<Integer> $freeformTasksInZOrder;
        final /* synthetic */ Integer $leftTiledTask;
        final /* synthetic */ ArraySet<Integer> $minimizedTasks;
        final /* synthetic */ Integer $rightTiledTask;
        final /* synthetic */ int $usedDesk;
        final /* synthetic */ int $userId;
        final /* synthetic */ ArraySet<Integer> $visibleTasks;
        /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ DesktopPersistentRepository this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int i, DesktopPersistentRepository desktopPersistentRepository, int i2, ArraySet<Integer> arraySet, ArraySet<Integer> arraySet2, ArrayList<Integer> arrayList, Integer num, Integer num2, int i3, int i4, Continuation continuation) {
            super(2, continuation);
            this.$userId = i;
            this.this$0 = desktopPersistentRepository;
            this.$desktopId = i2;
            this.$visibleTasks = arraySet;
            this.$minimizedTasks = arraySet2;
            this.$freeformTasksInZOrder = arrayList;
            this.$leftTiledTask = num;
            this.$rightTiledTask = num2;
            this.$displayId = i3;
            this.$usedDesk = i4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$userId, this.this$0, this.$desktopId, this.$visibleTasks, this.$minimizedTasks, this.$freeformTasksInZOrder, this.$leftTiledTask, this.$rightTiledTask, this.$displayId, this.$usedDesk, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((DesktopPersistentRepositories) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            DesktopPersistentRepositories desktopPersistentRepositories = (DesktopPersistentRepositories) this.L$0;
            DesktopRepositoryState desktopRepoByUserOrDefault = desktopPersistentRepositories.getDesktopRepoByUserOrDefault(this.$userId, DesktopRepositoryState.getDefaultInstance());
            Companion companion = DesktopPersistentRepository.Companion;
            DesktopPersistentRepository desktopPersistentRepository = this.this$0;
            desktopRepoByUserOrDefault.getClass();
            int i = this.$desktopId;
            desktopPersistentRepository.getClass();
            Desktop.Builder builderNewBuilder = Desktop.newBuilder();
            builderNewBuilder.copyOnWrite();
            Desktop.m3253$$Nest$msetDesktopId(i, (Desktop) builderNewBuilder.instance);
            builderNewBuilder.copyOnWrite();
            int i2 = 0;
            Desktop.m3254$$Nest$msetDisplayId(0, (Desktop) builderNewBuilder.instance);
            Desktop.Builder builder = (Desktop.Builder) desktopRepoByUserOrDefault.getDesktopOrDefault(i, (Desktop) builderNewBuilder.build()).toBuilder();
            ArraySet<Integer> arraySet = this.$visibleTasks;
            ArraySet<Integer> arraySet2 = this.$minimizedTasks;
            ArrayList<Integer> arrayList = this.$freeformTasksInZOrder;
            Integer num = this.$leftTiledTask;
            Integer num2 = this.$rightTiledTask;
            companion.getClass();
            builder.copyOnWrite();
            Desktop.m3252$$Nest$mgetMutableTasksByTaskIdMap((Desktop) builder.instance).clear();
            if (arrayList.size() > arraySet2.size() + arraySet.size() && arraySet.isEmpty()) {
                ArrayList arrayList2 = new ArrayList();
                int size = arrayList.size();
                while (i2 < size) {
                    Integer num3 = arrayList.get(i2);
                    i2++;
                    if (!arraySet2.contains(Integer.valueOf(num3.intValue()))) {
                        arrayList2.add(num3);
                    }
                }
                arraySet.addAll(arrayList2);
            }
            int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(arraySet, 10));
            if (iMapCapacity < 16) {
                iMapCapacity = 16;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
            Iterator<Integer> it = arraySet.iterator();
            while (it.hasNext()) {
                Integer next = it.next();
                Integer num4 = next;
                Companion companion2 = DesktopPersistentRepository.Companion;
                num4.getClass();
                int iIntValue = num4.intValue();
                DesktopTaskState desktopTaskState = DesktopTaskState.VISIBLE;
                int iIntValue2 = num4.intValue();
                companion2.getClass();
                linkedHashMap.put(next, Companion.createDesktopTask(iIntValue, desktopTaskState, (num != null && iIntValue2 == num.intValue()) ? DesktopTaskTilingState.LEFT : (num2 != null && iIntValue2 == num2.intValue()) ? DesktopTaskTilingState.RIGHT : DesktopTaskTilingState.NONE));
            }
            builder.copyOnWrite();
            Desktop.m3252$$Nest$mgetMutableTasksByTaskIdMap((Desktop) builder.instance).putAll(linkedHashMap);
            int iMapCapacity2 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(arraySet2, 10));
            LinkedHashMap linkedHashMap2 = new LinkedHashMap(iMapCapacity2 >= 16 ? iMapCapacity2 : 16);
            Iterator<Integer> it2 = arraySet2.iterator();
            while (it2.hasNext()) {
                Integer next2 = it2.next();
                Integer num5 = next2;
                Companion companion3 = DesktopPersistentRepository.Companion;
                num5.getClass();
                int iIntValue3 = num5.intValue();
                DesktopTaskState desktopTaskState2 = DesktopTaskState.MINIMIZED;
                DesktopTaskTilingState desktopTaskTilingState = DesktopTaskTilingState.NONE;
                companion3.getClass();
                linkedHashMap2.put(next2, Companion.createDesktopTask(iIntValue3, desktopTaskState2, desktopTaskTilingState));
            }
            builder.copyOnWrite();
            Desktop.m3252$$Nest$mgetMutableTasksByTaskIdMap((Desktop) builder.instance).putAll(linkedHashMap2);
            ArrayList<Integer> arrayList3 = this.$freeformTasksInZOrder;
            builder.copyOnWrite();
            Desktop.m3251$$Nest$mclearZOrderedTasks((Desktop) builder.instance);
            builder.copyOnWrite();
            Desktop.m3250$$Nest$maddAllZOrderedTasks((Desktop) builder.instance, arrayList3);
            int i3 = this.$displayId;
            builder.copyOnWrite();
            Desktop.m3254$$Nest$msetDisplayId(i3, (Desktop) builder.instance);
            int i4 = this.$usedDesk;
            builder.copyOnWrite();
            Desktop.m3255$$Nest$msetUsed(i4, (Desktop) builder.instance);
            DesktopPersistentRepositories.Builder builder2 = (DesktopPersistentRepositories.Builder) desktopPersistentRepositories.toBuilder();
            int i5 = this.$userId;
            DesktopRepositoryState.Builder builder3 = (DesktopRepositoryState.Builder) desktopRepoByUserOrDefault.toBuilder();
            int i6 = this.$desktopId;
            Desktop desktop = (Desktop) builder.build();
            builder3.copyOnWrite();
            DesktopRepositoryState.m3259$$Nest$mgetMutableDesktopMap((DesktopRepositoryState) builder3.instance).put(Integer.valueOf(i6), desktop);
            DesktopRepositoryState desktopRepositoryState = (DesktopRepositoryState) builder3.build();
            builder2.copyOnWrite();
            DesktopPersistentRepositories.m3257$$Nest$mgetMutableDesktopRepoByUserMap((DesktopPersistentRepositories) builder2.instance).put(Integer.valueOf(i5), desktopRepositoryState);
            return builder2.build();
        }
    }

    /* renamed from: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$getDesktopRepositoryState$1, reason: invalid class name and case insensitive filesystem */
    final class C12081 extends ContinuationImpl {
        int I$0;
        int label;
        /* synthetic */ Object result;

        public C12081(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DesktopPersistentRepository.this.getDesktopRepositoryState(0, this);
        }
    }

    /* renamed from: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$getUserDesktopRepositoryMap$1, reason: invalid class name and case insensitive filesystem */
    final class C12091 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C12091(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DesktopPersistentRepository.this.getUserDesktopRepositoryMap(this);
        }
    }

    /* renamed from: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$readDesktop$1, reason: invalid class name and case insensitive filesystem */
    final class C12101 extends ContinuationImpl {
        int I$0;
        int label;
        /* synthetic */ Object result;

        public C12101(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DesktopPersistentRepository.this.readDesktop(0, 0, this);
        }
    }

    /* renamed from: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeDesktop$1, reason: invalid class name and case insensitive filesystem */
    final class C12111 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C12111(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DesktopPersistentRepository.this.removeDesktop(0, 0, this);
        }
    }

    /* renamed from: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeDesktop$2, reason: invalid class name and case insensitive filesystem */
    final class C12122 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $desktopId;
        final /* synthetic */ int $userId;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C12122(int i, int i2, Continuation continuation) {
            super(2, continuation);
            this.$userId = i;
            this.$desktopId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C12122 c12122 = new C12122(this.$userId, this.$desktopId, continuation);
            c12122.L$0 = obj;
            return c12122;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C12122) create((DesktopPersistentRepositories) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            DesktopPersistentRepositories desktopPersistentRepositories = (DesktopPersistentRepositories) this.L$0;
            DesktopRepositoryState desktopRepoByUserOrDefault = desktopPersistentRepositories.getDesktopRepoByUserOrDefault(this.$userId, DesktopRepositoryState.getDefaultInstance());
            DesktopPersistentRepositories.Builder builder = (DesktopPersistentRepositories.Builder) desktopPersistentRepositories.toBuilder();
            int i = this.$userId;
            DesktopRepositoryState.Builder builder2 = (DesktopRepositoryState.Builder) desktopRepoByUserOrDefault.toBuilder();
            int i2 = this.$desktopId;
            builder2.copyOnWrite();
            DesktopRepositoryState.m3259$$Nest$mgetMutableDesktopMap((DesktopRepositoryState) builder2.instance).remove(Integer.valueOf(i2));
            DesktopRepositoryState desktopRepositoryState = (DesktopRepositoryState) builder2.build();
            builder.copyOnWrite();
            DesktopPersistentRepositories.m3257$$Nest$mgetMutableDesktopRepoByUserMap((DesktopPersistentRepositories) builder.instance).put(Integer.valueOf(i), desktopRepositoryState);
            return builder.build();
        }
    }

    /* renamed from: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeUsers$1, reason: invalid class name and case insensitive filesystem */
    final class C12131 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C12131(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DesktopPersistentRepository.this.removeUsers(null, this);
        }
    }

    /* renamed from: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeUsers$2, reason: invalid class name and case insensitive filesystem */
    final class C12142 extends SuspendLambda implements Function2 {
        final /* synthetic */ List<Integer> $uids;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C12142(List<Integer> list, Continuation continuation) {
            super(2, continuation);
            this.$uids = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C12142 c12142 = new C12142(this.$uids, continuation);
            c12142.L$0 = obj;
            return c12142;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C12142) create((DesktopPersistentRepositories) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            DesktopPersistentRepositories.Builder builder = (DesktopPersistentRepositories.Builder) ((DesktopPersistentRepositories) this.L$0).toBuilder();
            Iterator<T> it = this.$uids.iterator();
            while (it.hasNext()) {
                int iIntValue = ((Number) it.next()).intValue();
                builder.copyOnWrite();
                DesktopPersistentRepositories.m3257$$Nest$mgetMutableDesktopRepoByUserMap((DesktopPersistentRepositories) builder.instance).remove(Integer.valueOf(iIntValue));
            }
            return builder.build();
        }
    }

    public DesktopPersistentRepository(DataStore dataStore) {
        this.dataStore = dataStore;
        this.dataStoreFlow = new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(dataStore.getData(), new DesktopPersistentRepository$dataStoreFlow$1(null));
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object addOrUpdateDesktop(int i, int i2, ArraySet arraySet, ArraySet arraySet2, ArrayList arrayList, Integer num, Integer num2, int i3, int i4, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i5 = anonymousClass1.label;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i5 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        AnonymousClass1 anonymousClass12 = anonymousClass1;
        Object obj = anonymousClass12.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i6 = anonymousClass12.label;
        try {
            if (i6 == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = this.dataStore;
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(i, this, i2, arraySet, arraySet2, arrayList, num, num2, i3, i4, null);
                anonymousClass12.label = 1;
                if (dataStore.updateData(anonymousClass2, anonymousClass12) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i6 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
        } catch (Exception e) {
            Log.e("DesktopPersistenceRepo", "Error in updating desktop mode related data, data is stored in a file named desktop_persistent_repositories.pb", e);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getDesktopRepositoryState(int i, ContinuationImpl continuationImpl) {
        C12081 c12081;
        if (continuationImpl instanceof C12081) {
            c12081 = (C12081) continuationImpl;
            int i2 = c12081.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c12081.label = i2 - Integer.MIN_VALUE;
            } else {
                c12081 = new C12081(continuationImpl);
            }
        }
        Object objFirst = c12081.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = c12081.label;
        try {
            if (i3 == 0) {
                ResultKt.throwOnFailure(objFirst);
                FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 flowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 = this.dataStoreFlow;
                c12081.I$0 = i;
                c12081.label = 1;
                objFirst = FlowKt.first(flowKt__ErrorsKt$catch$$inlined$unsafeFlow$1, c12081);
                if (objFirst == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i3 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i = c12081.I$0;
                ResultKt.throwOnFailure(objFirst);
            }
            return (DesktopRepositoryState) ((DesktopPersistentRepositories) objFirst).getDesktopRepoByUserMap().get(new Integer(i));
        } catch (Exception e) {
            Log.e("DesktopPersistenceRepo", "Unable to read from datastore", e);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getUserDesktopRepositoryMap(ContinuationImpl continuationImpl) {
        C12091 c12091;
        if (continuationImpl instanceof C12091) {
            c12091 = (C12091) continuationImpl;
            int i = c12091.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c12091.label = i - Integer.MIN_VALUE;
            } else {
                c12091 = new C12091(continuationImpl);
            }
        }
        Object objFirst = c12091.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c12091.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(objFirst);
                FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 flowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 = this.dataStoreFlow;
                c12091.label = 1;
                objFirst = FlowKt.first(flowKt__ErrorsKt$catch$$inlined$unsafeFlow$1, c12091);
                if (objFirst == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(objFirst);
            }
            return ((DesktopPersistentRepositories) objFirst).getDesktopRepoByUserMap();
        } catch (Exception e) {
            Log.e("DesktopPersistenceRepo", "Unable to read from datastore", e);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object readDesktop(int i, int i2, ContinuationImpl continuationImpl) {
        C12101 c12101;
        if (continuationImpl instanceof C12101) {
            c12101 = (C12101) continuationImpl;
            int i3 = c12101.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                c12101.label = i3 - Integer.MIN_VALUE;
            } else {
                c12101 = new C12101(continuationImpl);
            }
        }
        Object desktopRepositoryState = c12101.result;
        Object obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = c12101.label;
        try {
            if (i4 == 0) {
                ResultKt.throwOnFailure(desktopRepositoryState);
                c12101.I$0 = i2;
                c12101.label = 1;
                desktopRepositoryState = getDesktopRepositoryState(i, c12101);
                if (desktopRepositoryState == obj) {
                    return obj;
                }
            } else {
                if (i4 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i2 = c12101.I$0;
                ResultKt.throwOnFailure(desktopRepositoryState);
            }
            DesktopRepositoryState desktopRepositoryState2 = (DesktopRepositoryState) desktopRepositoryState;
            if (desktopRepositoryState2 != null) {
                return desktopRepositoryState2.getDesktopOrThrow(i2);
            }
            return null;
        } catch (Exception e) {
            Log.e("DesktopPersistenceRepo", "Unable to get desktop info from persistent repository", e);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object removeDesktop(int i, int i2, ContinuationImpl continuationImpl) {
        C12111 c12111;
        if (continuationImpl instanceof C12111) {
            c12111 = (C12111) continuationImpl;
            int i3 = c12111.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                c12111.label = i3 - Integer.MIN_VALUE;
            } else {
                c12111 = new C12111(continuationImpl);
            }
        }
        Object obj = c12111.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = c12111.label;
        try {
            if (i4 == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = this.dataStore;
                C12122 c12122 = new C12122(i, i2, null);
                c12111.label = 1;
                if (dataStore.updateData(c12122, c12111) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i4 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
        } catch (Throwable th) {
            Log.e("DesktopPersistenceRepo", "Error in removing desktop related data, data is stored in a file named desktop_persistent_repositories.pb", th);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object removeUsers(List list, ContinuationImpl continuationImpl) {
        C12131 c12131;
        if (continuationImpl instanceof C12131) {
            c12131 = (C12131) continuationImpl;
            int i = c12131.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c12131.label = i - Integer.MIN_VALUE;
            } else {
                c12131 = new C12131(continuationImpl);
            }
        }
        Object obj = c12131.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c12131.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = this.dataStore;
                C12142 c12142 = new C12142(list, null);
                c12131.label = 1;
                if (dataStore.updateData(c12142, c12131) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
        } catch (Exception e) {
            Log.e("DesktopPersistenceRepo", "Error in removing user related data, data is stored in a file named desktop_persistent_repositories.pb", e);
        }
        return Unit.INSTANCE;
    }

    public DesktopPersistentRepository(final Context context, CoroutineScope coroutineScope) {
        this(DataStoreFactory.create$default(DataStoreFactory.INSTANCE, Companion.DesktopPersistentRepositoriesSerializer.INSTANCE, new ReplaceFileCorruptionHandler(new DesktopPersistentRepository$$ExternalSyntheticLambda0()), coroutineScope, new Function0() { // from class: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Context context2 = context;
                DesktopPersistentRepository.Companion companion = DesktopPersistentRepository.Companion;
                return DataStoreFile.dataStoreFile(context2, "desktop_persistent_repositories.pb");
            }
        }, 4));
    }
}
