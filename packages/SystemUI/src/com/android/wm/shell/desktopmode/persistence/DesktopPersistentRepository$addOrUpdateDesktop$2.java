package com.android.wm.shell.desktopmode.persistence;

import android.util.ArraySet;
import com.android.wm.shell.desktopmode.persistence.Desktop;
import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepositories;
import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryState;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DesktopPersistentRepository$addOrUpdateDesktop$2 extends SuspendLambda implements Function2 {
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
    public DesktopPersistentRepository$addOrUpdateDesktop$2(int i, DesktopPersistentRepository desktopPersistentRepository, int i2, ArraySet<Integer> arraySet, ArraySet<Integer> arraySet2, ArrayList<Integer> arrayList, Integer num, Integer num2, int i3, int i4, Continuation continuation) {
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
        DesktopPersistentRepository$addOrUpdateDesktop$2 desktopPersistentRepository$addOrUpdateDesktop$2 = new DesktopPersistentRepository$addOrUpdateDesktop$2(this.$userId, this.this$0, this.$desktopId, this.$visibleTasks, this.$minimizedTasks, this.$freeformTasksInZOrder, this.$leftTiledTask, this.$rightTiledTask, this.$displayId, this.$usedDesk, continuation);
        desktopPersistentRepository$addOrUpdateDesktop$2.L$0 = obj;
        return desktopPersistentRepository$addOrUpdateDesktop$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DesktopPersistentRepository$addOrUpdateDesktop$2) create((DesktopPersistentRepositories) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        DesktopPersistentRepository.Companion companion = DesktopPersistentRepository.Companion;
        DesktopPersistentRepository desktopPersistentRepository = this.this$0;
        desktopRepoByUserOrDefault.getClass();
        int i = this.$desktopId;
        desktopPersistentRepository.getClass();
        Desktop.Builder newBuilder = Desktop.newBuilder();
        newBuilder.copyOnWrite();
        Desktop.m3236$$Nest$msetDesktopId(i, (Desktop) newBuilder.instance);
        newBuilder.copyOnWrite();
        int i2 = 0;
        Desktop.m3237$$Nest$msetDisplayId(0, (Desktop) newBuilder.instance);
        Desktop.Builder builder = (Desktop.Builder) desktopRepoByUserOrDefault.getDesktopOrDefault(i, (Desktop) newBuilder.build()).toBuilder();
        ArraySet<Integer> arraySet = this.$visibleTasks;
        ArraySet<Integer> arraySet2 = this.$minimizedTasks;
        ArrayList<Integer> arrayList = this.$freeformTasksInZOrder;
        Integer num = this.$leftTiledTask;
        Integer num2 = this.$rightTiledTask;
        companion.getClass();
        builder.copyOnWrite();
        Desktop.m3235$$Nest$mgetMutableTasksByTaskIdMap((Desktop) builder.instance).clear();
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
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(arraySet, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        Iterator<Integer> it = arraySet.iterator();
        while (it.hasNext()) {
            Integer next = it.next();
            Integer num4 = next;
            DesktopPersistentRepository.Companion companion2 = DesktopPersistentRepository.Companion;
            num4.getClass();
            int intValue = num4.intValue();
            DesktopTaskState desktopTaskState = DesktopTaskState.VISIBLE;
            int intValue2 = num4.intValue();
            companion2.getClass();
            linkedHashMap.put(next, DesktopPersistentRepository.Companion.createDesktopTask(intValue, desktopTaskState, (num != null && intValue2 == num.intValue()) ? DesktopTaskTilingState.LEFT : (num2 != null && intValue2 == num2.intValue()) ? DesktopTaskTilingState.RIGHT : DesktopTaskTilingState.NONE));
        }
        builder.copyOnWrite();
        Desktop.m3235$$Nest$mgetMutableTasksByTaskIdMap((Desktop) builder.instance).putAll(linkedHashMap);
        int mapCapacity2 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(arraySet2, 10));
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(mapCapacity2 >= 16 ? mapCapacity2 : 16);
        Iterator<Integer> it2 = arraySet2.iterator();
        while (it2.hasNext()) {
            Integer next2 = it2.next();
            Integer num5 = next2;
            DesktopPersistentRepository.Companion companion3 = DesktopPersistentRepository.Companion;
            num5.getClass();
            int intValue3 = num5.intValue();
            DesktopTaskState desktopTaskState2 = DesktopTaskState.MINIMIZED;
            DesktopTaskTilingState desktopTaskTilingState = DesktopTaskTilingState.NONE;
            companion3.getClass();
            linkedHashMap2.put(next2, DesktopPersistentRepository.Companion.createDesktopTask(intValue3, desktopTaskState2, desktopTaskTilingState));
        }
        builder.copyOnWrite();
        Desktop.m3235$$Nest$mgetMutableTasksByTaskIdMap((Desktop) builder.instance).putAll(linkedHashMap2);
        ArrayList<Integer> arrayList3 = this.$freeformTasksInZOrder;
        builder.copyOnWrite();
        Desktop.m3234$$Nest$mclearZOrderedTasks((Desktop) builder.instance);
        builder.copyOnWrite();
        Desktop.m3233$$Nest$maddAllZOrderedTasks((Desktop) builder.instance, arrayList3);
        int i3 = this.$displayId;
        builder.copyOnWrite();
        Desktop.m3237$$Nest$msetDisplayId(i3, (Desktop) builder.instance);
        int i4 = this.$usedDesk;
        builder.copyOnWrite();
        Desktop.m3238$$Nest$msetUsed(i4, (Desktop) builder.instance);
        DesktopPersistentRepositories.Builder builder2 = (DesktopPersistentRepositories.Builder) desktopPersistentRepositories.toBuilder();
        int i5 = this.$userId;
        DesktopRepositoryState.Builder builder3 = (DesktopRepositoryState.Builder) desktopRepoByUserOrDefault.toBuilder();
        int i6 = this.$desktopId;
        Desktop desktop = (Desktop) builder.build();
        builder3.copyOnWrite();
        DesktopRepositoryState.m3242$$Nest$mgetMutableDesktopMap((DesktopRepositoryState) builder3.instance).put(Integer.valueOf(i6), desktop);
        DesktopRepositoryState desktopRepositoryState = (DesktopRepositoryState) builder3.build();
        builder2.copyOnWrite();
        DesktopPersistentRepositories.m3240$$Nest$mgetMutableDesktopRepoByUserMap((DesktopPersistentRepositories) builder2.instance).put(Integer.valueOf(i5), desktopRepositoryState);
        return builder2.build();
    }
}
