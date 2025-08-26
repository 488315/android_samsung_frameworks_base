package com.android.wm.shell.desktopmode;

import android.graphics.Region;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.util.SparseArrayKt$valueIterator$1;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopConfigImpl;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence.AnonymousClass1;
import kotlin.sequences.FlatteningSequence;
import kotlin.sequences.FlatteningSequence.AnonymousClass1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;
import kotlin.sequences.TransformingSequence;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class DesktopRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DesktopConfig desktopConfig;
    public final DesktopData desktopData;
    public Executor desktopGestureExclusionExecutor;
    public Consumer desktopGestureExclusionListener;
    public final CoroutineScope mainCoroutineScope;
    public final DesktopPersistentRepository persistentRepository;
    public final int userId;
    public final ArrayMap deskChangeListeners = new ArrayMap();
    public final ArraySet activeTasksListeners = new ArraySet();
    public final ArrayMap visibleTasksListeners = new ArrayMap();
    public final SparseArray desktopExclusionRegions = new SparseArray();
    public final SparseArray boundsBeforeMaximizeByTaskId = new SparseArray();
    public final SparseArray displayLayoutBeforeMaximizeByTaskId = new SparseArray();
    public final SparseArray boundsBeforeMinimizeByTaskId = new SparseArray();
    public final SparseArray boundsBeforeFullImmersiveByTaskId = new SparseArray();

    public interface ActiveTasksListener {
        void onActiveTasksChanged(int i);
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getINVALID_DESK_ID$annotations() {
        }
    }

    public interface DeskChangeListener {
        void onActiveDeskChanged(int i, int i2, int i3);

        void onCanCreateDesksChanged(boolean z);

        void onDeskAdded(int i, int i2);

        void onDeskRemoved(int i, int i2);
    }

    public interface DesktopData {
        void addDesk(int i, Desk desk);

        void createDesk(int i, int i2, int i3);

        Sequence desksSequence();

        Sequence desksSequence(int i);

        void forAllDesks(int i, Function1 function1);

        void forAllDesks(DesktopRepository$$ExternalSyntheticLambda5 desktopRepository$$ExternalSyntheticLambda5);

        void forAllDesks(Function2 function2);

        Desk getActiveDesk(int i);

        Set getAllActiveDesks();

        Desk getDefaultDesk(int i);

        Desk getDesk(int i);

        Desk getDeskForCreateByHome();

        Desk getDeskForDefaultDisplay(Integer num);

        Desk getDeskForNewDisplay();

        int getDisplayForDesk(int i);

        int getNumberOfDesks();

        int getNumberOfDesks(int i);

        void remove(int i);

        void removeDisplay(int i);

        void setActiveDesk(int i, int i2);

        void setDeskInactive(int i);
    }

    public final class SingleDesktopData implements DesktopData {
        public final DesktopRepository$SingleDesktopData$deskByDisplayId$1 deskByDisplayId = new DesktopRepository$SingleDesktopData$deskByDisplayId$1();

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Sequence desksSequence() {
            return SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.deskByDisplayId));
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void forAllDesks(int i, Function1 function1) {
            function1.mo781invoke(this.deskByDisplayId.getOrCreate(i));
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Desk getActiveDesk(int i) {
            return this.deskByDisplayId.getOrCreate(i);
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Set getAllActiveDesks() {
            return SequencesKt___SequencesKt.toSet(SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.deskByDisplayId)));
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Desk getDefaultDesk(int i) {
            return this.deskByDisplayId.getOrCreate(i);
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Desk getDesk(int i) {
            return this.deskByDisplayId.getOrCreate(i);
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Desk getDeskForCreateByHome() {
            return null;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Desk getDeskForDefaultDisplay(Integer num) {
            return null;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Desk getDeskForNewDisplay() {
            return null;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final int getNumberOfDesks() {
            return 1;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void remove(int i) {
            Desk desk = (Desk) this.deskByDisplayId.get(i);
            if (desk != null) {
                desk.activeTasks.clear();
                desk.visibleTasks.clear();
                desk.minimizedTasks.clear();
                desk.closingTasks.clear();
                desk.freeformTasksInZOrder.clear();
                desk.fullImmersiveTaskId = null;
                desk.topTransparentFullscreenTaskId = null;
                desk.leftTiledTaskId = null;
                desk.rightTiledTaskId = null;
                desk.usedDesk = -1;
            }
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void removeDisplay(int i) {
            this.deskByDisplayId.remove(i);
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final int getNumberOfDesks(int i) {
            return 1;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Sequence desksSequence(int i) {
            Sequence sequenceAsSequence;
            Desk desk = (Desk) this.deskByDisplayId.get(i);
            return (desk == null || (sequenceAsSequence = ArraysKt___ArraysKt.asSequence(new Desk[]{desk})) == null) ? EmptySequence.INSTANCE : sequenceAsSequence;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void forAllDesks(DesktopRepository$$ExternalSyntheticLambda5 desktopRepository$$ExternalSyntheticLambda5) {
            DesktopRepository$SingleDesktopData$deskByDisplayId$1 desktopRepository$SingleDesktopData$deskByDisplayId$1 = this.deskByDisplayId;
            int size = desktopRepository$SingleDesktopData$deskByDisplayId$1.size();
            for (int i = 0; i < size; i++) {
                desktopRepository$SingleDesktopData$deskByDisplayId$1.keyAt(i);
                desktopRepository$$ExternalSyntheticLambda5.mo781invoke((Desk) desktopRepository$SingleDesktopData$deskByDisplayId$1.valueAt(i));
            }
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void forAllDesks(Function2 function2) {
            DesktopRepository$SingleDesktopData$deskByDisplayId$1 desktopRepository$SingleDesktopData$deskByDisplayId$1 = this.deskByDisplayId;
            int size = desktopRepository$SingleDesktopData$deskByDisplayId$1.size();
            for (int i = 0; i < size; i++) {
                int iKeyAt = desktopRepository$SingleDesktopData$deskByDisplayId$1.keyAt(i);
                function2.invoke(Integer.valueOf(iKeyAt), (Desk) desktopRepository$SingleDesktopData$deskByDisplayId$1.valueAt(i));
            }
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final int getDisplayForDesk(int i) {
            return i;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void setDeskInactive(int i) {
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void addDesk(int i, Desk desk) {
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void setActiveDesk(int i, int i2) {
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void createDesk(int i, int i2, int i3) {
        }
    }

    public interface VisibleTasksListener {
        void onTasksVisibilityChanged(int i, int i2);
    }

    /* renamed from: com.android.wm.shell.desktopmode.DesktopRepository$updatePersistentRepository$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ List<Desk> $desks;
        Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ DesktopRepository this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(List<Desk> list, DesktopRepository desktopRepository, Continuation continuation) {
            super(2, continuation);
            this.$desks = list;
            this.this$0 = desktopRepository;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$desks, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            DesktopRepository desktopRepository;
            Iterator it;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                List<Desk> list = this.$desks;
                desktopRepository = this.this$0;
                it = list.iterator();
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                it = (Iterator) this.L$1;
                desktopRepository = (DesktopRepository) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            while (it.hasNext()) {
                Desk desk = (Desk) it.next();
                if (desktopRepository.desktopData.getDesk(desk.deskId) != null) {
                    this.L$0 = desktopRepository;
                    this.L$1 = it;
                    this.label = 1;
                    if (DesktopRepository.access$updatePersistentRepositoryForDesk(desktopRepository, desk, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.wm.shell.desktopmode.DesktopRepository$updatePersistentRepositoryForDesk$1, reason: invalid class name and case insensitive filesystem */
    final class C12001 extends SuspendLambda implements Function2 {
        final /* synthetic */ Desk $desk;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C12001(Desk desk, Continuation continuation) {
            super(2, continuation);
            this.$desk = desk;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DesktopRepository.this.new C12001(this.$desk, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C12001) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DesktopRepository desktopRepository = DesktopRepository.this;
                Desk desk = this.$desk;
                this.label = 1;
                if (DesktopRepository.access$updatePersistentRepositoryForDesk(desktopRepository, desk, this) == coroutineSingletons) {
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

    /* renamed from: com.android.wm.shell.desktopmode.DesktopRepository$updatePersistentRepositoryForDesk$2, reason: invalid class name */
    final class AnonymousClass2 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass2(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DesktopRepository.access$updatePersistentRepositoryForDesk(DesktopRepository.this, null, this);
        }
    }

    static {
        new Companion(null);
    }

    public DesktopRepository(DesktopPersistentRepository desktopPersistentRepository, CoroutineScope coroutineScope, int i, DesktopConfig desktopConfig) {
        this.persistentRepository = desktopPersistentRepository;
        this.mainCoroutineScope = coroutineScope;
        this.userId = i;
        this.desktopConfig = desktopConfig;
        this.desktopData = DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue() ? new MultiDesktopData() : new SingleDesktopData();
    }

    public static final Region access$calculateDesktopExclusionRegion(DesktopRepository desktopRepository) {
        desktopRepository.getClass();
        Region region = new Region();
        SparseArrayKt$valueIterator$1 sparseArrayKt$valueIterator$1 = new SparseArrayKt$valueIterator$1(desktopRepository.desktopExclusionRegions);
        while (sparseArrayKt$valueIterator$1.hasNext()) {
            region.op((Region) sparseArrayKt$valueIterator$1.next(), Region.Op.UNION);
        }
        return region;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$updatePersistentRepositoryForDesk(DesktopRepository desktopRepository, Desk desk, ContinuationImpl continuationImpl) {
        AnonymousClass2 anonymousClass2;
        desktopRepository.getClass();
        if (continuationImpl instanceof AnonymousClass2) {
            anonymousClass2 = (AnonymousClass2) continuationImpl;
            int i = anonymousClass2.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass2.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass2 = desktopRepository.new AnonymousClass2(continuationImpl);
            }
        }
        AnonymousClass2 anonymousClass22 = anonymousClass2;
        Object obj = anonymousClass22.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass22.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DesktopPersistentRepository desktopPersistentRepository = desktopRepository.persistentRepository;
                int i3 = desktopRepository.userId;
                int i4 = desk.deskId;
                ArraySet arraySet = desk.visibleTasks;
                ArraySet arraySet2 = desk.minimizedTasks;
                ArrayList arrayList = desk.freeformTasksInZOrder;
                Integer num = desk.leftTiledTaskId;
                Integer num2 = desk.rightTiledTaskId;
                int i5 = desk.displayId;
                int i6 = desk.usedDesk;
                anonymousClass22.L$0 = desktopRepository;
                anonymousClass22.label = 1;
                Object objAddOrUpdateDesktop = desktopPersistentRepository.addOrUpdateDesktop(i3, i4, arraySet, arraySet2, arrayList, num, num2, i5, i6, anonymousClass22);
                desktopRepository = objAddOrUpdateDesktop;
                if (objAddOrUpdateDesktop == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                DesktopRepository desktopRepository2 = (DesktopRepository) anonymousClass22.L$0;
                ResultKt.throwOnFailure(obj);
                desktopRepository = desktopRepository2;
            }
        } catch (Exception e) {
            desktopRepository.logE("An exception occurred while updating the persistent repository \n%s", e.getStackTrace());
        }
        return Unit.INSTANCE;
    }

    public final void addClosingTask(int i, Integer num, int i2) {
        Desk activeDesk;
        DesktopData desktopData = this.desktopData;
        if ((num == null || (activeDesk = desktopData.getDesk(num.intValue())) == null) && (activeDesk = desktopData.getActiveDesk(i)) == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Expected active desk in display: ").toString());
        }
        boolean zAdd = activeDesk.closingTasks.add(Integer.valueOf(i2));
        int i3 = activeDesk.deskId;
        if (zAdd) {
            logD("Added closing task=%d displayId=%d deskId=%d", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3));
        } else {
            logW("Task with taskId=%d displayId=%d deskId=%d is already closing", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3));
        }
    }

    public final void addDesk(final int i, final int i2, final int i3) {
        Desk desk;
        logD("addDesk for displayId=%d and deskId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        final boolean zCanCreateDesks = canCreateDesks();
        DesktopData desktopData = this.desktopData;
        desktopData.createDesk(i, i2, i3);
        final boolean zCanCreateDesks2 = canCreateDesks();
        for (Map.Entry entry : this.deskChangeListeners.entrySet()) {
            final DeskChangeListener deskChangeListener = (DeskChangeListener) entry.getKey();
            ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$addDesk$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (i3 == -1) {
                        return;
                    }
                    deskChangeListener.onDeskAdded(i, i2);
                    boolean z = zCanCreateDesks;
                    boolean z2 = zCanCreateDesks2;
                    if (z != z2) {
                        deskChangeListener.onCanCreateDesksChanged(z2);
                    }
                }
            });
        }
        if (i == 0 && desktopData.getNumberOfDesks(i) == 1) {
            updatePersistentRepository(i);
        }
        if (!CoreRune.DW_DESK_LABEL || i3 == -1 || (desk = desktopData.getDesk(i2)) == null) {
            return;
        }
        desk.deskLabel = nextDeskLabel();
    }

    public final void addLeftTiledTask(int i, int i2) {
        logD("addLeftTiledTask for displayId=%d, taskId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        DesktopData desktopData = this.desktopData;
        Desk defaultDesk = desktopData.getDefaultDesk(i);
        if (defaultDesk == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Expected desk in display: ").toString());
        }
        logD("addLeftTiledTaskToDesk for displayId=%d, taskId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        int i3 = defaultDesk.deskId;
        Desk desk = desktopData.getDesk(i3);
        if (desk == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "Did not find desk: ").toString());
        }
        desk.leftTiledTaskId = Integer.valueOf(i2);
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
            updatePersistentRepository(i);
        }
    }

    public final void addRightTiledTask(int i, int i2) {
        logD("addRightTiledTask for displayId=%d, taskId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        DesktopData desktopData = this.desktopData;
        Desk defaultDesk = desktopData.getDefaultDesk(i);
        if (defaultDesk == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Expected desk in display: ").toString());
        }
        logD("addRightTiledTaskToDesk for displayId=%d, taskId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        int i3 = defaultDesk.deskId;
        Desk desk = desktopData.getDesk(i3);
        if (desk == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "Did not find desk: ").toString());
        }
        desk.rightTiledTaskId = Integer.valueOf(i2);
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
            updatePersistentRepository(i);
        }
    }

    public final void addTask(int i, int i2, boolean z) {
        logD("addTask for displayId=%d, taskId=%d, isVisible=%b", Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z));
        Desk defaultDesk = this.desktopData.getDefaultDesk(i);
        if (defaultDesk == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Expected desk in display: ").toString());
        }
        addTaskToDesk(i, defaultDesk.deskId, i2, z);
    }

    public final void addTaskToDesk(int i, int i2, final int i3, boolean z) {
        Integer num;
        logD("addTaskToDesk for displayId=%d, deskId=%d, taskId=%d, isVisible=%b", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Boolean.valueOf(z));
        logD("addOrMoveTaskToTopOfDesk displayId=%d, deskId=%d, taskId=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        DesktopData desktopData = this.desktopData;
        Desk desk = desktopData.getDesk(i2);
        if (desk == null) {
            throw new IllegalStateException(("Could not find desk: " + i2).toString());
        }
        logD("addOrMoveTaskToTopOfDesk: display=%d deskId=%d taskId=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        desktopData.forAllDesks(new Function2() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                ((Integer) obj).intValue();
                int i4 = DesktopRepository.$r8$clinit;
                ((DesktopRepository.Desk) obj2).freeformTasksInZOrder.remove(Integer.valueOf(i3));
                return Unit.INSTANCE;
            }
        });
        desk.freeformTasksInZOrder.add(0, Integer.valueOf(i3));
        logD("UnminimizeTask: display=%d, task=%d", Integer.valueOf(i), Integer.valueOf(i3));
        desktopData.forAllDesks(i, new DesktopRepository$$ExternalSyntheticLambda5(i3, 1, this));
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
            updatePersistentRepository(i);
        }
        logD("addActiveTaskToDesk for displayId=%d, deskId=%d, taskId=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        Desk desk2 = desktopData.getDesk(i2);
        if (desk2 == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "Did not find desk: ").toString());
        }
        removeActiveTask(i3, Integer.valueOf(i2));
        for (Desk desk3 : desktopData.desksSequence()) {
            if (desk3.deskId != i2 && (num = desk3.fullImmersiveTaskId) != null && num.intValue() == i3) {
                logD("Remove fullImmersiveTaskId=%d", Integer.valueOf(i3));
                desk3.fullImmersiveTaskId = null;
            }
        }
        if (desk2.activeTasks.add(Integer.valueOf(i3))) {
            logD("Adds active task=%d displayId=%d deskId=%d", Integer.valueOf(i3), Integer.valueOf(i), Integer.valueOf(i2));
            if (i == 0 && desk2.usedDesk == -1) {
                desk2.usedDesk = 0;
                updatePersistentRepositoryForDesk(i2);
            }
            updateActiveTasksListeners(i);
        }
        updateTaskInDesk(i, i2, i3, z);
    }

    public final void addVisibleTasksListener(final VisibleTasksListener visibleTasksListener, Executor executor) {
        this.visibleTasksListeners.put(visibleTasksListener, executor);
        Sequence sequenceDesksSequence = this.desktopData.desksSequence();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : sequenceDesksSequence) {
            Integer numValueOf = Integer.valueOf(((Desk) obj).displayId);
            Object arrayList = linkedHashMap.get(numValueOf);
            if (arrayList == null) {
                arrayList = new ArrayList();
                linkedHashMap.put(numValueOf, arrayList);
            }
            ((List) arrayList).add(obj);
        }
        Iterator it = linkedHashMap.keySet().iterator();
        while (it.hasNext()) {
            final int iIntValue = ((Number) it.next()).intValue();
            final int visibleTaskCount = getVisibleTaskCount(iIntValue);
            executor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$addVisibleTasksListener$2$1
                @Override // java.lang.Runnable
                public final void run() {
                    visibleTasksListener.onTasksVisibilityChanged(iIntValue, visibleTaskCount);
                }
            });
        }
    }

    public final boolean canCreateDesks() {
        if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            return false;
        }
        int i = ((DesktopConfigImpl) this.desktopConfig).maxDeskLimit;
        return i == 0 || i > this.desktopData.getNumberOfDesks();
    }

    public final Integer getActiveDeskId(int i) {
        Desk activeDesk = this.desktopData.getActiveDesk(i);
        if (activeDesk != null) {
            return Integer.valueOf(activeDesk.deskId);
        }
        return null;
    }

    public final Set getActiveTaskIdsInDesk(int i) {
        ArraySet arraySet;
        Set set;
        Desk desk = this.desktopData.getDesk(i);
        if (desk != null && (arraySet = desk.activeTasks) != null && (set = CollectionsKt___CollectionsKt.toSet(arraySet)) != null) {
            return set;
        }
        logW("getTasksInDesk: could not find desk: deskId=%d", Integer.valueOf(i));
        return EmptySet.INSTANCE;
    }

    public final Set getAllDeskIds() {
        return SequencesKt___SequencesKt.toSet(new TransformingSequence(this.desktopData.desksSequence(), new DesktopRepository$$ExternalSyntheticLambda0(1)));
    }

    public final Integer getDefaultDeskId(int i) {
        Desk defaultDesk = this.desktopData.getDefaultDesk(i);
        if (defaultDesk != null) {
            return Integer.valueOf(defaultDesk.deskId);
        }
        return null;
    }

    public final Integer getDeskIdForTask(int i) {
        Object next;
        Iterator it = this.desktopData.desksSequence().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (((Desk) next).activeTasks.contains(Integer.valueOf(i))) {
                break;
            }
        }
        Desk desk = (Desk) next;
        if (desk != null) {
            return Integer.valueOf(desk.deskId);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Integer getDisplayIdForTask(final int i) {
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        this.desktopData.forAllDesks(new Function2() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$$ExternalSyntheticLambda3
            /* JADX WARN: Type inference failed for: r2v1, types: [T, java.lang.Integer] */
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                ?? r2 = (Integer) obj;
                r2.intValue();
                int i2 = DesktopRepository.$r8$clinit;
                if (((DesktopRepository.Desk) obj2).activeTasks.contains(Integer.valueOf(i))) {
                    ref$ObjectRef.element = r2;
                }
                return Unit.INSTANCE;
            }
        });
        if (ref$ObjectRef.element == 0) {
            logW("No display id found for task: taskId=%d", Integer.valueOf(i));
        }
        return (Integer) ref$ObjectRef.element;
    }

    public final List getExpandedTasksIdsInDeskOrdered(int i) {
        ArrayList<Integer> freeformTasksIdsInDeskInZOrder = getFreeformTasksIdsInDeskInZOrder(i);
        ArrayList arrayList = new ArrayList();
        int size = freeformTasksIdsInDeskInZOrder.size();
        int i2 = 0;
        while (i2 < size) {
            Integer num = freeformTasksIdsInDeskInZOrder.get(i2);
            i2++;
            if (!isMinimizedTask(num.intValue())) {
                arrayList.add(num);
            }
        }
        return arrayList;
    }

    public final List getExpandedTasksOrdered(int i) {
        ArrayList<Integer> freeformTasksInZOrder = getFreeformTasksInZOrder(i);
        ArrayList arrayList = new ArrayList();
        int size = freeformTasksInZOrder.size();
        int i2 = 0;
        while (i2 < size) {
            Integer num = freeformTasksInZOrder.get(i2);
            i2++;
            if (!isMinimizedTask(num.intValue())) {
                arrayList.add(num);
            }
        }
        return arrayList;
    }

    public final ArrayList<Integer> getFreeformTasksIdsInDeskInZOrder(int i) {
        Collection collection;
        Desk desk = this.desktopData.getDesk(i);
        if (desk == null || (collection = desk.freeformTasksInZOrder) == null) {
            collection = EmptyList.INSTANCE;
        }
        return new ArrayList<>(collection);
    }

    public final ArrayList<Integer> getFreeformTasksInZOrder(int i) {
        Collection collection;
        Desk defaultDesk = this.desktopData.getDefaultDesk(i);
        if (defaultDesk == null || (collection = defaultDesk.freeformTasksInZOrder) == null) {
            collection = EmptyList.INSTANCE;
        }
        return new ArrayList<>(collection);
    }

    public final Integer getLastUsedDeskIdInDefaultDisplay() {
        Object next;
        Iterator it = this.desktopData.desksSequence(0).iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (((Desk) next).usedDesk == 2) {
                break;
            }
        }
        Desk desk = (Desk) next;
        if (desk != null) {
            return Integer.valueOf(desk.deskId);
        }
        return null;
    }

    public final ArraySet<Integer> getMinimizedTaskIdsInDesk(int i) {
        Desk desk = this.desktopData.getDesk(i);
        return new ArraySet<>(desk != null ? desk.minimizedTasks : null);
    }

    public final int getVisibleTaskCount(int i) {
        ArraySet arraySet;
        Desk activeDesk = this.desktopData.getActiveDesk(i);
        int size = (activeDesk == null || (arraySet = activeDesk.visibleTasks) == null) ? 0 : arraySet.size();
        logD(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(size, "getVisibleTaskCount="), new Object[0]);
        return size;
    }

    public final boolean isActiveTask(int i) {
        Iterator it = this.desktopData.desksSequence().iterator();
        while (it.hasNext()) {
            if (((Desk) it.next()).activeTasks.contains(Integer.valueOf(i))) {
                return true;
            }
        }
        return false;
    }

    public final boolean isActiveTaskInDesk(int i, int i2) {
        Desk desk = this.desktopData.getDesk(i2);
        if (desk == null) {
            return false;
        }
        return desk.activeTasks.contains(Integer.valueOf(i));
    }

    public final boolean isAnyDeskActive(int i) {
        boolean zIsTrue = DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue();
        DesktopData desktopData = this.desktopData;
        if (zIsTrue) {
            return desktopData.getActiveDesk(i) != null;
        }
        if (desktopData.getDefaultDesk(i) != null) {
            return !r0.visibleTasks.isEmpty();
        }
        logE(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Could not find default desk for display: "), new Object[0]);
        return false;
    }

    public final boolean isClosingTask(int i) {
        Iterator it = this.desktopData.desksSequence().iterator();
        while (it.hasNext()) {
            if (((Desk) it.next()).closingTasks.contains(Integer.valueOf(i))) {
                return true;
            }
        }
        return false;
    }

    public final boolean isDeskActive(int i) {
        Set allActiveDesks = this.desktopData.getAllActiveDesks();
        if ((allActiveDesks instanceof Collection) && allActiveDesks.isEmpty()) {
            return false;
        }
        Iterator it = allActiveDesks.iterator();
        while (it.hasNext()) {
            if (((Desk) it.next()).deskId == i) {
                return true;
            }
        }
        return false;
    }

    public final boolean isMinimizedTask(int i) {
        Iterator it = this.desktopData.desksSequence().iterator();
        while (it.hasNext()) {
            if (((Desk) it.next()).minimizedTasks.contains(Integer.valueOf(i))) {
                return true;
            }
        }
        return false;
    }

    public final boolean isOnlyVisibleNonClosingTask(int i, int i2) {
        Set allActiveDesks;
        DesktopData desktopData = this.desktopData;
        if (i2 != -1) {
            Desk activeDesk = desktopData.getActiveDesk(i2);
            allActiveDesks = activeDesk != null ? Collections.singleton(activeDesk) : EmptySet.INSTANCE;
        } else {
            allActiveDesks = desktopData.getAllActiveDesks();
        }
        Set set = allActiveDesks;
        if ((set instanceof Collection) && set.isEmpty()) {
            return false;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            if (isOnlyVisibleNonClosingTaskInDesk(i, ((Desk) it.next()).deskId)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isOnlyVisibleNonClosingTaskInDesk(int i, int i2) {
        Desk desk = this.desktopData.getDesk(i2);
        if (desk != null) {
            Set setSubtract = CollectionsKt___CollectionsKt.subtract(CollectionsKt___CollectionsKt.subtract(desk.visibleTasks, desk.closingTasks), desk.minimizedTasks);
            Object obj = null;
            if (setSubtract instanceof List) {
                List list = (List) setSubtract;
                if (list.size() == 1) {
                    obj = list.get(0);
                }
            } else {
                Iterator it = setSubtract.iterator();
                if (it.hasNext()) {
                    Object next = it.next();
                    if (!it.hasNext()) {
                        obj = next;
                    }
                }
            }
            Integer num = (Integer) obj;
            if (num != null && num.intValue() == i) {
                return true;
            }
        }
        return false;
    }

    public final boolean isTaskInFullImmersiveState(int i) {
        Iterator it = this.desktopData.desksSequence().iterator();
        while (it.hasNext()) {
            Integer num = ((Desk) it.next()).fullImmersiveTaskId;
            if (num != null && i == num.intValue()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isVisibleTask(int i) {
        Iterator it = this.desktopData.desksSequence().iterator();
        while (it.hasNext()) {
            if (((Desk) it.next()).visibleTasks.contains(Integer.valueOf(i))) {
                return true;
            }
        }
        return false;
    }

    public final boolean isVisibleTaskInDesk(int i, int i2) {
        Desk desk = this.desktopData.getDesk(i2);
        if (desk == null) {
            return false;
        }
        return desk.visibleTasks.contains(Integer.valueOf(i));
    }

    public final void logD(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("[u%d] %s: ", str);
        SpreadBuilder spreadBuilder = new SpreadBuilder(3);
        spreadBuilder.add(Integer.valueOf(this.userId));
        spreadBuilder.add("DesktopRepository");
        spreadBuilder.addSpread(objArr);
        ProtoLog.d(shellProtoLogGroup, strM, spreadBuilder.list.toArray(new Object[spreadBuilder.list.size()]));
    }

    public final void logE(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("[u%d] %s: ", str);
        SpreadBuilder spreadBuilder = new SpreadBuilder(3);
        spreadBuilder.add(Integer.valueOf(this.userId));
        spreadBuilder.add("DesktopRepository");
        spreadBuilder.addSpread(objArr);
        ProtoLog.e(shellProtoLogGroup, strM, spreadBuilder.list.toArray(new Object[spreadBuilder.list.size()]));
    }

    public final void logW(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strConcat = "[u%d] %s: ".concat(str);
        SpreadBuilder spreadBuilder = new SpreadBuilder(3);
        spreadBuilder.add(Integer.valueOf(this.userId));
        spreadBuilder.add("DesktopRepository");
        spreadBuilder.addSpread(objArr);
        ProtoLog.w(shellProtoLogGroup, strConcat, spreadBuilder.list.toArray(new Object[spreadBuilder.list.size()]));
    }

    public final void minimizeTask(int i, int i2) {
        logD("minimizeTask displayId=%d, taskId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        if (i == -1) {
            Integer displayIdForTask = getDisplayIdForTask(i2);
            if (displayIdForTask != null) {
                minimizeTask(displayIdForTask.intValue(), i2);
                return;
            } else {
                logW("Minimize task: No display id found for task: taskId=%d", Integer.valueOf(i2));
                return;
            }
        }
        Desk activeDesk = this.desktopData.getActiveDesk(i);
        Integer numValueOf = activeDesk != null ? Integer.valueOf(activeDesk.deskId) : null;
        if (numValueOf == null) {
            logD("Minimize task: No active desk found for task: taskId=%d", Integer.valueOf(i2));
        } else {
            minimizeTaskInDesk(i, numValueOf.intValue(), i2);
        }
    }

    public final void minimizeTaskInDesk(int i, int i2, int i3) {
        ArraySet arraySet;
        ArraySet arraySet2;
        logD("MinimizeTaskInDesk: displayId=%d deskId=%d, task=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        DesktopData desktopData = this.desktopData;
        Desk desk = desktopData.getDesk(i2);
        if (desk == null || (arraySet2 = desk.minimizedTasks) == null) {
            logD("Minimize task: No active desk found for task: taskId=%d", Integer.valueOf(i3));
        } else {
            arraySet2.add(Integer.valueOf(i3));
        }
        int size = 0;
        updateTaskInDesk(i, i2, i3, false);
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
            updatePersistentRepositoryForDesk(i2);
        }
        if (CoreRune.MW_SA_LOGGING) {
            int size2 = getMinimizedTaskIdsInDesk(i2).size();
            Desk desk2 = desktopData.getDesk(i2);
            if (desk2 != null && (arraySet = desk2.visibleTasks) != null) {
                size = arraySet.size();
            }
            CoreSaLogger.logForDexMW("3002", "[minimizedWindow] : " + size2 + " [visibleWindow] : " + size);
        }
    }

    public final int nextDeskLabel() {
        return SequencesKt___SequencesKt.count(SequencesKt___SequencesKt.filter(this.desktopData.desksSequence(), new DesktopRepository$$ExternalSyntheticLambda0(0))) + 1;
    }

    public final void notifyVisibleTaskListeners(final int i, final int i2) {
        for (Map.Entry entry : this.visibleTasksListeners.entrySet()) {
            final VisibleTasksListener visibleTasksListener = (VisibleTasksListener) entry.getKey();
            ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$notifyVisibleTaskListeners$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    visibleTasksListener.onTasksVisibilityChanged(i, i2);
                }
            });
        }
    }

    public final void onDeskDisplayChanged(final int i, final int i2) {
        boolean zCanCreateDesks = canCreateDesks();
        DesktopData desktopData = this.desktopData;
        Desk desk = desktopData.getDesk(i);
        if (desk == null) {
            throw new IllegalStateException(("Expected to find desk with id: " + i).toString());
        }
        Desk deskDeepCopy = desk.deepCopy();
        deskDeepCopy.displayId = i2;
        if (CoreRune.DW_MULTIPLE_DESKS) {
            removeDesk(i, false);
        } else {
            removeDesk(i, true);
        }
        desktopData.addDesk(i2, deskDeepCopy);
        boolean zCanCreateDesks2 = canCreateDesks();
        for (Map.Entry entry : this.deskChangeListeners.entrySet()) {
            final DeskChangeListener deskChangeListener = (DeskChangeListener) entry.getKey();
            ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$onDeskDisplayChanged$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    deskChangeListener.onDeskAdded(i2, i);
                }
            });
            if (zCanCreateDesks != zCanCreateDesks2) {
                deskChangeListener.onCanCreateDesksChanged(zCanCreateDesks2);
            }
        }
        logD("onDeskDisplayChanged deskId=%d newDisplayId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        updatePersistentRepository(i2);
    }

    public final void removeActiveTask(int i, final Integer num) {
        logD("removeActiveTask for taskId=%d, excludedDeskId=%d", Integer.valueOf(i), num);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.filter(this.desktopData.desksSequence(), new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                int i2 = DesktopRepository.$r8$clinit;
                int i3 = ((DesktopRepository.Desk) obj).deskId;
                Integer num2 = num;
                return Boolean.valueOf(num2 == null || i3 != num2.intValue());
            }
        }).new AnonymousClass1();
        while (anonymousClass1.hasNext()) {
            Desk desk = (Desk) anonymousClass1.next();
            if (removeActiveTaskFromDesk(desk.deskId, i, false)) {
                logD("Removed active task=%d displayId=%d deskId=%d", Integer.valueOf(i), Integer.valueOf(desk.displayId), Integer.valueOf(desk.deskId));
                linkedHashSet.add(Integer.valueOf(desk.displayId));
            }
        }
        Iterator it = linkedHashSet.iterator();
        while (it.hasNext()) {
            updateActiveTasksListeners(((Number) it.next()).intValue());
        }
    }

    public final boolean removeActiveTaskFromDesk(int i, int i2, boolean z) {
        logD("removeActiveTaskFromDesk for deskId=%d, taskId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        Desk desk = this.desktopData.getDesk(i);
        if (desk == null || !desk.activeTasks.remove(Integer.valueOf(i2))) {
            return false;
        }
        logD("Removed active task=%d from deskId=%d", Integer.valueOf(i2), Integer.valueOf(desk.deskId));
        if (!z) {
            return true;
        }
        updateActiveTasksListeners(desk.displayId);
        return true;
    }

    public final Set removeDesk(int i, final boolean z) {
        logD("removeDesk %d", Integer.valueOf(i));
        final boolean zCanCreateDesks = canCreateDesks();
        DesktopData desktopData = this.desktopData;
        final Desk desk = desktopData.getDesk(i);
        if (desk == null) {
            EmptySet emptySet = EmptySet.INSTANCE;
            logW("Could not find desk to remove: deskId=%d", Integer.valueOf(i));
            return emptySet;
        }
        Desk activeDesk = desktopData.getActiveDesk(desk.displayId);
        int i2 = 0;
        int i3 = desk.deskId;
        final boolean z2 = activeDesk != null && activeDesk.deskId == i3;
        ArraySet arraySet = new ArraySet(desk.activeTasks);
        desktopData.remove(i3);
        final boolean zCanCreateDesks2 = canCreateDesks();
        if (CoreRune.DW_DESK_LABEL && z) {
            FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.filter(new SequencesKt___SequencesKt$sortedWith$1(desktopData.desksSequence(), new Comparator() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$reassignDeskLabel$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((DesktopRepository.Desk) obj).deskLabel), Integer.valueOf(((DesktopRepository.Desk) obj2).deskLabel));
                }
            }), new DesktopRepository$$ExternalSyntheticLambda0(5)).new AnonymousClass1();
            while (anonymousClass1.hasNext()) {
                Object next = anonymousClass1.next();
                int i4 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                ((Desk) next).deskLabel = i4;
                i2 = i4;
            }
        }
        int i5 = desk.displayId;
        notifyVisibleTaskListeners(i5, getVisibleTaskCount(i5));
        for (Map.Entry entry : this.deskChangeListeners.entrySet()) {
            final DeskChangeListener deskChangeListener = (DeskChangeListener) entry.getKey();
            ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$removeDesk$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (z2) {
                        DesktopRepository.DeskChangeListener deskChangeListener2 = deskChangeListener;
                        DesktopRepository.Desk desk2 = desk;
                        deskChangeListener2.onActiveDeskChanged(desk2.displayId, -1, desk2.deskId);
                    }
                    DesktopRepository.DeskChangeListener deskChangeListener3 = deskChangeListener;
                    DesktopRepository.Desk desk3 = desk;
                    deskChangeListener3.onDeskRemoved(desk3.displayId, desk3.deskId);
                    if (!CoreRune.DW_MULTIPLE_DESKS || z) {
                        boolean z3 = zCanCreateDesks;
                        boolean z4 = zCanCreateDesks2;
                        if (z3 != z4) {
                            deskChangeListener.onCanCreateDesksChanged(z4);
                        }
                    }
                }
            });
        }
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue() && DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            BuildersKt.launch$default(this.mainCoroutineScope, null, null, new DesktopRepository$removeDeskFromPersistentRepository$1(this, desk, null), 3);
        }
        return arraySet;
    }

    public final void removeTask(int i, int i2) {
        logD("Removes freeform task: taskId=%d", Integer.valueOf(i2));
        DesktopData desktopData = this.desktopData;
        if (i != -1) {
            logD("Removes freeform task: taskId=%d, displayId=%d", Integer.valueOf(i2), Integer.valueOf(i));
            desktopData.forAllDesks(i, new DesktopRepository$$ExternalSyntheticLambda5(i2, 0, this));
            return;
        }
        Integer displayIdForTask = getDisplayIdForTask(i2);
        if (displayIdForTask != null) {
            int iIntValue = displayIdForTask.intValue();
            logD("Removes freeform task: taskId=%d, displayId=%d", Integer.valueOf(i2), Integer.valueOf(iIntValue));
            desktopData.forAllDesks(iIntValue, new DesktopRepository$$ExternalSyntheticLambda5(i2, 0, this));
        }
    }

    public final void removeTaskFromDesk(int i, int i2) {
        logD("removeTaskFromDesk: deskId=%d, taskId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        this.boundsBeforeMaximizeByTaskId.remove(i2);
        this.displayLayoutBeforeMaximizeByTaskId.remove(i2);
        this.boundsBeforeFullImmersiveByTaskId.remove(i2);
        Desk desk = this.desktopData.getDesk(i);
        if (desk == null) {
            return;
        }
        boolean zRemove = desk.freeformTasksInZOrder.remove(Integer.valueOf(i2));
        int i3 = desk.deskId;
        if (zRemove) {
            logD("Remaining freeform tasks in desk: %d, tasks: %s", Integer.valueOf(i3), CollectionsKt___CollectionsKt.joinToString$default(desk.freeformTasksInZOrder, ", ", "[", "]", null, 56));
        }
        unminimizeTaskFromDesk(i, i2);
        setTaskInFullImmersiveStateInDesk(i, i2, false);
        removeActiveTaskFromDesk(i, i2, true);
        removeVisibleTaskFromDesk(i, i2);
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
            updatePersistentRepositoryForDesk(i3);
        }
    }

    public final void removeVisibleTaskFromDesk(int i, int i2) {
        Desk desk = this.desktopData.getDesk(i);
        if (desk != null && desk.visibleTasks.remove(Integer.valueOf(i2))) {
            notifyVisibleTaskListeners(desk.displayId, desk.visibleTasks.size());
        }
    }

    public final void setActiveDesk(final int i, final int i2) {
        Object next;
        Desk desk;
        logD("setActiveDesk for displayId=%d and deskId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        DesktopData desktopData = this.desktopData;
        Desk activeDesk = desktopData.getActiveDesk(i);
        final int i3 = activeDesk != null ? activeDesk.deskId : -1;
        if (i != -1 && i != 0) {
            Desk activeDesk2 = desktopData.getActiveDesk(i);
            if (activeDesk2 != null) {
                activeDesk2.usedDesk = 0;
            }
            Desk desk2 = desktopData.getDesk(i2);
            if (desk2 != null) {
                desk2.usedDesk = 1;
            }
        } else if (i == 0) {
            Desk activeDesk3 = desktopData.getActiveDesk(i);
            if (activeDesk3 != null) {
                activeDesk3.usedDesk = 0;
            } else {
                Iterator it = desktopData.desksSequence(i).iterator();
                while (true) {
                    if (it.hasNext()) {
                        next = it.next();
                        if (((Desk) next).usedDesk == 2) {
                            break;
                        }
                    } else {
                        next = null;
                        break;
                    }
                }
                Desk desk3 = (Desk) next;
                if (desk3 != null) {
                    desk3.usedDesk = 0;
                }
            }
            Desk desk4 = desktopData.getDesk(i2);
            if (desk4 != null) {
                desk4.usedDesk = 2;
            }
        }
        if (CoreRune.DW_DESK_LABEL && (desk = desktopData.getDesk(i2)) != null && desk.deskLabel == 0) {
            desk.deskLabel = nextDeskLabel();
        }
        desktopData.setActiveDesk(i, i2);
        for (Map.Entry entry : this.deskChangeListeners.entrySet()) {
            final DeskChangeListener deskChangeListener = (DeskChangeListener) entry.getKey();
            ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$setActiveDesk$4$1
                @Override // java.lang.Runnable
                public final void run() {
                    deskChangeListener.onActiveDeskChanged(i, i2, i3);
                }
            });
        }
    }

    public final void setDeskInactive(final int i) {
        DesktopData desktopData = this.desktopData;
        final int displayForDesk = desktopData.getDisplayForDesk(i);
        Desk activeDesk = desktopData.getActiveDesk(displayForDesk);
        int i2 = activeDesk != null ? activeDesk.deskId : -1;
        if (i2 == -1 || i2 != i) {
            return;
        }
        desktopData.setDeskInactive(i);
        for (Map.Entry entry : this.deskChangeListeners.entrySet()) {
            final DeskChangeListener deskChangeListener = (DeskChangeListener) entry.getKey();
            ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$setDeskInactive$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    deskChangeListener.onActiveDeskChanged(displayForDesk, -1, i);
                }
            });
        }
    }

    public final void setTaskInFullImmersiveState(int i, int i2, boolean z) {
        Desk activeDesk = this.desktopData.getActiveDesk(i);
        if (activeDesk == null) {
            return;
        }
        setTaskInFullImmersiveStateInDesk(activeDesk.deskId, i2, z);
    }

    public final void setTaskInFullImmersiveStateInDesk(int i, int i2, boolean z) {
        Desk desk = this.desktopData.getDesk(i);
        if (desk == null) {
            return;
        }
        if (z) {
            desk.fullImmersiveTaskId = Integer.valueOf(i2);
            return;
        }
        Integer num = desk.fullImmersiveTaskId;
        if (num != null && num.intValue() == i2) {
            desk.fullImmersiveTaskId = null;
        }
    }

    public final void unminimizeTaskFromDesk(int i, int i2) {
        ArraySet arraySet;
        logD("Unminimize Task from desk: deskId=%d, taskId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        Desk desk = this.desktopData.getDesk(i);
        if (desk == null || (arraySet = desk.minimizedTasks) == null || !arraySet.remove(Integer.valueOf(i2))) {
            logW("Unminimize Task: deskId=%d, taskId=%d, no task data", Integer.valueOf(i), Integer.valueOf(i2));
        }
    }

    public final void updateActiveTasksListeners(int i) {
        Iterator it = this.activeTasksListeners.iterator();
        while (it.hasNext()) {
            ((ActiveTasksListener) it.next()).onActiveTasksChanged(i);
        }
    }

    public final void updatePersistentRepository(int i) {
        BuildersKt.launch$default(this.mainCoroutineScope, null, null, new AnonymousClass1(SequencesKt___SequencesKt.toList(new TransformingSequence(this.desktopData.desksSequence(i), new DesktopRepository$$ExternalSyntheticLambda0(3))), this, null), 3);
    }

    public final void updatePersistentRepositoryForDesk(int i) {
        Desk desk = this.desktopData.getDesk(i);
        if (desk != null) {
            BuildersKt.launch$default(this.mainCoroutineScope, null, null, new C12001(desk.deepCopy(), null), 3);
        }
    }

    public final void updateTask(int i, int i2, boolean z) {
        Integer displayIdForTask = i == -1 ? getDisplayIdForTask(i2) : Integer.valueOf(i);
        if (displayIdForTask == null) {
            logW("No display id found for task: taskId=%d", Integer.valueOf(i2));
            return;
        }
        Desk defaultDesk = this.desktopData.getDefaultDesk(displayIdForTask.intValue());
        if (defaultDesk != null) {
            updateTaskInDesk(displayIdForTask.intValue(), defaultDesk.deskId, i2, z);
        } else {
            throw new IllegalStateException(("Expected a desk in display: " + displayIdForTask).toString());
        }
    }

    public final void updateTaskInDesk(int i, int i2, final int i3, boolean z) {
        ArraySet arraySet;
        ArraySet arraySet2;
        if (i == -1) {
            throw new IllegalStateException("Display must be valid");
        }
        logD("updateTaskInDesk taskId=%d, deskId=%d, displayId=%d, isVisible=%b", Integer.valueOf(i3), Integer.valueOf(i2), Integer.valueOf(i), Boolean.valueOf(z));
        DesktopData desktopData = this.desktopData;
        if (z) {
            final Integer numValueOf = Integer.valueOf(i2);
            desktopData.forAllDesks(new Function2() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$$ExternalSyntheticLambda12
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj).intValue();
                    DesktopRepository.Desk desk = (DesktopRepository.Desk) obj2;
                    int i4 = DesktopRepository.$r8$clinit;
                    int i5 = desk.deskId;
                    Integer num = numValueOf;
                    if (num == null || i5 != num.intValue()) {
                        this.removeVisibleTaskFromDesk(desk.deskId, i3);
                    }
                    return Unit.INSTANCE;
                }
            });
        }
        Desk desk = desktopData.getDesk(i2);
        if (desk == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "Did not find desk: ").toString());
        }
        Desk desk2 = desktopData.getDesk(i2);
        int size = 0;
        int size2 = (desk2 == null || (arraySet2 = desk2.visibleTasks) == null) ? 0 : arraySet2.size();
        if (z) {
            desk.visibleTasks.add(Integer.valueOf(i3));
            logD("UnminimizeTask: display=%d, task=%d", Integer.valueOf(i), Integer.valueOf(i3));
            desktopData.forAllDesks(i, new DesktopRepository$$ExternalSyntheticLambda5(i3, 1, this));
        } else {
            desk.visibleTasks.remove(Integer.valueOf(i3));
        }
        Desk desk3 = desktopData.getDesk(i2);
        if (desk3 != null && (arraySet = desk3.visibleTasks) != null) {
            size = arraySet.size();
        }
        if (size2 != size) {
            logD("Update task visibility taskId=%d visible=%b deskId=%d displayId=%d", Integer.valueOf(i3), Boolean.valueOf(z), Integer.valueOf(i2), Integer.valueOf(i));
            logD("VisibleTaskCount has changed from %d to %d", Integer.valueOf(size2), Integer.valueOf(size));
            notifyVisibleTaskListeners(i, size);
            if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
                updatePersistentRepository(i);
            }
        }
    }

    public final void verifyFreeformTasksInZOrder(int i, List list) {
        Desk desk = this.desktopData.getDesk(i);
        if (desk == null) {
            throw new IllegalStateException(("Could not find desk: " + i).toString());
        }
        Iterator it = CollectionsKt___CollectionsKt.reversed(list).iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            desk.freeformTasksInZOrder.remove(Integer.valueOf(iIntValue));
            desk.freeformTasksInZOrder.add(0, Integer.valueOf(iIntValue));
        }
    }

    public final class MultiDesktopData implements DesktopData {
        public final SparseArray desktopDisplays = new SparseArray();

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void addDesk(int i, Desk desk) {
            int i2;
            DesktopDisplay desktopDisplay = (DesktopDisplay) this.desktopDisplays.get(i);
            if (desktopDisplay == null) {
                i2 = i;
                DesktopDisplay desktopDisplay2 = new DesktopDisplay(i2, null, null, 6, null);
                this.desktopDisplays.set(i2, desktopDisplay2);
                desktopDisplay = desktopDisplay2;
            } else {
                i2 = i;
            }
            Set set = desktopDisplay.orderedDesks;
            if (!(set instanceof Collection) || !set.isEmpty()) {
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    int i3 = ((Desk) it.next()).deskId;
                    int i4 = desk.deskId;
                    if (i4 == i3) {
                        throw new IllegalStateException(ListImplementation$$ExternalSyntheticOutline0.m(i4, i2, "Attempting to add desk#", " that already exists in display#").toString());
                    }
                }
            }
            desktopDisplay.orderedDesks.add(desk);
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void createDesk(int i, int i2, int i3) {
            DesktopDisplay desktopDisplay = (DesktopDisplay) this.desktopDisplays.get(i);
            if (desktopDisplay == null) {
                desktopDisplay = new DesktopDisplay(i, null, null, 6, null);
                this.desktopDisplays.set(i, desktopDisplay);
            }
            Set set = desktopDisplay.orderedDesks;
            if (!(set instanceof Collection) || !set.isEmpty()) {
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    if (((Desk) it.next()).deskId == i2) {
                        throw new IllegalStateException(ListImplementation$$ExternalSyntheticOutline0.m(i2, i, "Attempting to create desk#", " that already exists in display#").toString());
                    }
                }
            }
            desktopDisplay.orderedDesks.add(new Desk(i2, i, null, null, null, null, null, null, null, null, null, i3, 0, 6140, null));
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Sequence desksSequence() {
            return SequencesKt___SequencesKt.flatMap(SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.desktopDisplays)), new DesktopRepository$$ExternalSyntheticLambda0(7));
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void forAllDesks(DesktopRepository$$ExternalSyntheticLambda5 desktopRepository$$ExternalSyntheticLambda5) {
            SparseArray sparseArray = this.desktopDisplays;
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                sparseArray.keyAt(i);
                Iterator it = ((DesktopDisplay) sparseArray.valueAt(i)).orderedDesks.iterator();
                while (it.hasNext()) {
                    desktopRepository$$ExternalSyntheticLambda5.mo781invoke((Desk) it.next());
                }
            }
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Desk getActiveDesk(int i) {
            DesktopDisplay desktopDisplay = (DesktopDisplay) this.desktopDisplays.get(i);
            Object obj = null;
            if (desktopDisplay == null || desktopDisplay.activeDeskId == null) {
                return null;
            }
            Iterator it = desktopDisplay.orderedDesks.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                int i2 = ((Desk) next).deskId;
                Integer num = desktopDisplay.activeDeskId;
                if (num != null && i2 == num.intValue()) {
                    obj = next;
                    break;
                }
            }
            return (Desk) obj;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Set getAllActiveDesks() {
            return SequencesKt___SequencesKt.toSet(new TransformingSequence(SequencesKt___SequencesKt.filter(SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.desktopDisplays)), new DesktopRepository$$ExternalSyntheticLambda0(8)), new DesktopRepository$$ExternalSyntheticLambda0(9)));
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Desk getDefaultDesk(int i) {
            DesktopDisplay desktopDisplay = (DesktopDisplay) this.desktopDisplays.get(i);
            Object obj = null;
            if (desktopDisplay == null) {
                return null;
            }
            Iterator it = desktopDisplay.orderedDesks.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                int i2 = ((Desk) next).deskId;
                Integer num = desktopDisplay.activeDeskId;
                if (num != null && i2 == num.intValue()) {
                    obj = next;
                    break;
                }
            }
            Desk desk = (Desk) obj;
            return desk == null ? (Desk) CollectionsKt___CollectionsKt.firstOrNull(desktopDisplay.orderedDesks) : desk;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Desk getDesk(int i) {
            SparseArray sparseArray = this.desktopDisplays;
            int size = sparseArray.size();
            int i2 = 0;
            while (true) {
                Object obj = null;
                if (i2 >= size) {
                    return null;
                }
                sparseArray.keyAt(i2);
                Iterator it = ((DesktopDisplay) sparseArray.valueAt(i2)).orderedDesks.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Object next = it.next();
                    if (((Desk) next).deskId == i) {
                        obj = next;
                        break;
                    }
                }
                Desk desk = (Desk) obj;
                if (desk != null) {
                    return desk;
                }
                i2++;
            }
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Desk getDeskForCreateByHome() {
            DesktopDisplay desktopDisplay = (DesktopDisplay) this.desktopDisplays.get(0);
            Object obj = null;
            if (desktopDisplay == null) {
                return null;
            }
            Iterator it = desktopDisplay.orderedDesks.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (((Desk) next).usedDesk == -1) {
                    obj = next;
                    break;
                }
            }
            return (Desk) obj;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Desk getDeskForDefaultDisplay(Integer num) {
            Object obj;
            int i = 0;
            DesktopDisplay desktopDisplay = (DesktopDisplay) this.desktopDisplays.get(0);
            Object obj2 = null;
            if (desktopDisplay == null) {
                return null;
            }
            Set set = desktopDisplay.orderedDesks;
            ArrayList arrayList = new ArrayList();
            for (Object obj3 : set) {
                int i2 = ((Desk) obj3).deskId;
                if (num == null || i2 != num.intValue()) {
                    arrayList.add(obj3);
                }
            }
            int size = arrayList.size();
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    obj = null;
                    break;
                }
                obj = arrayList.get(i3);
                i3++;
                if (((Desk) obj).usedDesk == 2) {
                    break;
                }
            }
            Desk desk = (Desk) obj;
            if (desk != null) {
                return desk;
            }
            int size2 = arrayList.size();
            while (true) {
                if (i >= size2) {
                    break;
                }
                Object obj4 = arrayList.get(i);
                i++;
                if (((Desk) obj4).usedDesk == 0) {
                    obj2 = obj4;
                    break;
                }
            }
            Desk desk2 = (Desk) obj2;
            return desk2 == null ? (Desk) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList) : desk2;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Desk getDeskForNewDisplay() {
            DesktopDisplay desktopDisplay = (DesktopDisplay) this.desktopDisplays.get(0);
            Object obj = null;
            if (desktopDisplay == null) {
                return null;
            }
            Iterator it = desktopDisplay.orderedDesks.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (((Desk) next).usedDesk == 1) {
                    obj = next;
                    break;
                }
            }
            Desk desk = (Desk) obj;
            return desk != null ? desk : (Desk) SequencesKt___SequencesKt.firstOrNull(new SequencesKt___SequencesKt$sortedWith$1(SequencesKt___SequencesKt.filter(desksSequence(0), new DesktopRepository$$ExternalSyntheticLambda0(11)), new Comparator() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$MultiDesktopData$getDeskForNewDisplay$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((DesktopRepository.Desk) obj2).deskId), Integer.valueOf(((DesktopRepository.Desk) obj3).deskId));
                }
            }));
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final int getDisplayForDesk(int i) {
            Object next;
            FlatteningSequence.AnonymousClass1 anonymousClass1 = ((FlatteningSequence) desksSequence()).new AnonymousClass1();
            while (true) {
                if (!anonymousClass1.hasNext()) {
                    next = null;
                    break;
                }
                next = anonymousClass1.next();
                if (((Desk) next).deskId == i) {
                    break;
                }
            }
            Desk desk = (Desk) next;
            if (desk != null) {
                return desk.displayId;
            }
            throw new IllegalStateException(("Display for desk=" + i + " not found").toString());
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final int getNumberOfDesks() {
            Iterator it = SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.desktopDisplays)).iterator();
            int size = 0;
            while (it.hasNext()) {
                Set set = ((DesktopDisplay) it.next()).orderedDesks;
                ArrayList arrayList = new ArrayList();
                for (Object obj : set) {
                    if (((Desk) obj).usedDesk != -1) {
                        arrayList.add(obj);
                    }
                }
                size += arrayList.size();
            }
            return size;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void remove(int i) {
            setDeskInactive(i);
            SparseArray sparseArray = this.desktopDisplays;
            int size = sparseArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                sparseArray.keyAt(i2);
                Set set = ((DesktopDisplay) sparseArray.valueAt(i2)).orderedDesks;
                final DesktopRepository$MultiDesktopData$$ExternalSyntheticLambda1 desktopRepository$MultiDesktopData$$ExternalSyntheticLambda1 = new DesktopRepository$MultiDesktopData$$ExternalSyntheticLambda1(i, 0);
                set.removeIf(new Predicate() { // from class: com.android.wm.shell.desktopmode.DesktopRepositoryKt$sam$java_util_function_Predicate$0
                    @Override // java.util.function.Predicate
                    public final /* synthetic */ boolean test(Object obj) {
                        return ((Boolean) desktopRepository$MultiDesktopData$$ExternalSyntheticLambda1.mo781invoke(obj)).booleanValue();
                    }
                });
            }
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void removeDisplay(int i) {
            this.desktopDisplays.remove(i);
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void setActiveDesk(int i, int i2) {
            DesktopDisplay desktopDisplay = (DesktopDisplay) this.desktopDisplays.get(i);
            if (desktopDisplay == null) {
                throw new IllegalStateException(("Expected display#" + i + " to exist").toString());
            }
            Object obj = null;
            boolean z = false;
            for (Object obj2 : desktopDisplay.orderedDesks) {
                if (((Desk) obj2).deskId == i2) {
                    if (z) {
                        throw new IllegalArgumentException("Collection contains more than one matching element.");
                    }
                    z = true;
                    obj = obj2;
                }
            }
            if (!z) {
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
            desktopDisplay.activeDeskId = Integer.valueOf(((Desk) obj).deskId);
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void setDeskInactive(int i) {
            SparseArray sparseArray = this.desktopDisplays;
            int size = sparseArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                sparseArray.keyAt(i2);
                DesktopDisplay desktopDisplay = (DesktopDisplay) sparseArray.valueAt(i2);
                Integer num = desktopDisplay.activeDeskId;
                if (num != null && num.intValue() == i) {
                    desktopDisplay.activeDeskId = null;
                }
            }
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Sequence desksSequence(int i) {
            Set set;
            DesktopDisplay desktopDisplay = (DesktopDisplay) this.desktopDisplays.get(i);
            if (desktopDisplay != null && (set = desktopDisplay.orderedDesks) != null) {
                return new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(set);
            }
            return EmptySequence.INSTANCE;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void forAllDesks(Function2 function2) {
            SparseArray sparseArray = this.desktopDisplays;
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                sparseArray.keyAt(i);
                DesktopDisplay desktopDisplay = (DesktopDisplay) sparseArray.valueAt(i);
                Iterator it = desktopDisplay.orderedDesks.iterator();
                while (it.hasNext()) {
                    function2.invoke(Integer.valueOf(desktopDisplay.displayId), (Desk) it.next());
                }
            }
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final int getNumberOfDesks(int i) {
            Set set;
            DesktopDisplay desktopDisplay = (DesktopDisplay) this.desktopDisplays.get(i);
            if (desktopDisplay == null || (set = desktopDisplay.orderedDesks) == null) {
                return 0;
            }
            return set.size();
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void forAllDesks(int i, Function1 function1) {
            FlatteningSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.flatMap(SequencesKt___SequencesKt.filter(SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.desktopDisplays)), new DesktopRepository$MultiDesktopData$$ExternalSyntheticLambda1(i, 1)), new DesktopRepository$$ExternalSyntheticLambda0(10)).new AnonymousClass1();
            while (anonymousClass1.hasNext()) {
                function1.mo781invoke((Desk) anonymousClass1.next());
            }
        }
    }

    public final class DesktopDisplay {
        public Integer activeDeskId;
        public final int displayId;
        public final Set orderedDesks;

        public DesktopDisplay(int i, Set<Desk> set, Integer num) {
            this.displayId = i;
            this.orderedDesks = set;
            this.activeDeskId = num;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DesktopDisplay)) {
                return false;
            }
            DesktopDisplay desktopDisplay = (DesktopDisplay) obj;
            return this.displayId == desktopDisplay.displayId && Intrinsics.areEqual(this.orderedDesks, desktopDisplay.orderedDesks) && Intrinsics.areEqual(this.activeDeskId, desktopDisplay.activeDeskId);
        }

        public final int hashCode() {
            int iHashCode = (this.orderedDesks.hashCode() + (Integer.hashCode(this.displayId) * 31)) * 31;
            Integer num = this.activeDeskId;
            return iHashCode + (num == null ? 0 : num.hashCode());
        }

        public final String toString() {
            return "DesktopDisplay(displayId=" + this.displayId + ", orderedDesks=" + this.orderedDesks + ", activeDeskId=" + this.activeDeskId + ")";
        }

        public /* synthetic */ DesktopDisplay(int i, Set set, Integer num, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, (i2 & 2) != 0 ? new LinkedHashSet() : set, (i2 & 4) != 0 ? null : num);
        }
    }

    public final class Desk {
        public final ArraySet activeTasks;
        public final ArraySet closingTasks;
        public final int deskId;
        public int deskLabel;
        public int displayId;
        public final ArrayList freeformTasksInZOrder;
        public Integer fullImmersiveTaskId;
        public Integer leftTiledTaskId;
        public final ArraySet minimizedTasks;
        public Integer rightTiledTaskId;
        public Integer topTransparentFullscreenTaskId;
        public int usedDesk;
        public final ArraySet visibleTasks;

        public Desk(int i, int i2, ArraySet<Integer> arraySet, ArraySet<Integer> arraySet2, ArraySet<Integer> arraySet3, ArraySet<Integer> arraySet4, ArrayList<Integer> arrayList, Integer num, Integer num2, Integer num3, Integer num4, int i3, int i4) {
            this.deskId = i;
            this.displayId = i2;
            this.activeTasks = arraySet;
            this.visibleTasks = arraySet2;
            this.minimizedTasks = arraySet3;
            this.closingTasks = arraySet4;
            this.freeformTasksInZOrder = arrayList;
            this.fullImmersiveTaskId = num;
            this.topTransparentFullscreenTaskId = num2;
            this.leftTiledTaskId = num3;
            this.rightTiledTaskId = num4;
            this.usedDesk = i3;
            this.deskLabel = i4;
        }

        public final Desk deepCopy() {
            return new Desk(this.deskId, this.displayId, new ArraySet(this.activeTasks), new ArraySet(this.visibleTasks), new ArraySet(this.minimizedTasks), new ArraySet(this.closingTasks), new ArrayList(this.freeformTasksInZOrder), this.fullImmersiveTaskId, this.topTransparentFullscreenTaskId, this.leftTiledTaskId, this.rightTiledTaskId, this.usedDesk, this.deskLabel);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Desk)) {
                return false;
            }
            Desk desk = (Desk) obj;
            return this.deskId == desk.deskId && this.displayId == desk.displayId && Intrinsics.areEqual(this.activeTasks, desk.activeTasks) && Intrinsics.areEqual(this.visibleTasks, desk.visibleTasks) && Intrinsics.areEqual(this.minimizedTasks, desk.minimizedTasks) && Intrinsics.areEqual(this.closingTasks, desk.closingTasks) && Intrinsics.areEqual(this.freeformTasksInZOrder, desk.freeformTasksInZOrder) && Intrinsics.areEqual(this.fullImmersiveTaskId, desk.fullImmersiveTaskId) && Intrinsics.areEqual(this.topTransparentFullscreenTaskId, desk.topTransparentFullscreenTaskId) && Intrinsics.areEqual(this.leftTiledTaskId, desk.leftTiledTaskId) && Intrinsics.areEqual(this.rightTiledTaskId, desk.rightTiledTaskId) && this.usedDesk == desk.usedDesk && this.deskLabel == desk.deskLabel;
        }

        public final int hashCode() {
            int iHashCode = (this.freeformTasksInZOrder.hashCode() + ((this.closingTasks.hashCode() + ((this.minimizedTasks.hashCode() + ((this.visibleTasks.hashCode() + ((this.activeTasks.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, Integer.hashCode(this.deskId) * 31, 31)) * 31)) * 31)) * 31)) * 31)) * 31;
            Integer num = this.fullImmersiveTaskId;
            int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
            Integer num2 = this.topTransparentFullscreenTaskId;
            int iHashCode3 = (iHashCode2 + (num2 == null ? 0 : num2.hashCode())) * 31;
            Integer num3 = this.leftTiledTaskId;
            int iHashCode4 = (iHashCode3 + (num3 == null ? 0 : num3.hashCode())) * 31;
            Integer num4 = this.rightTiledTaskId;
            return Integer.hashCode(this.deskLabel) + ReorderTile$$ExternalSyntheticOutline0.m(this.usedDesk, (iHashCode4 + (num4 != null ? num4.hashCode() : 0)) * 31, 31);
        }

        public final String toString() {
            int i = this.displayId;
            ArraySet arraySet = this.activeTasks;
            ArraySet arraySet2 = this.visibleTasks;
            ArraySet arraySet3 = this.minimizedTasks;
            ArraySet arraySet4 = this.closingTasks;
            ArrayList arrayList = this.freeformTasksInZOrder;
            Integer num = this.fullImmersiveTaskId;
            Integer num2 = this.topTransparentFullscreenTaskId;
            Integer num3 = this.leftTiledTaskId;
            Integer num4 = this.rightTiledTaskId;
            int i2 = this.usedDesk;
            int i3 = this.deskLabel;
            StringBuilder sb = new StringBuilder("Desk(deskId=");
            ViewPager$$ExternalSyntheticOutline0.m(sb, this.deskId, ", displayId=", i, ", activeTasks=");
            sb.append(arraySet);
            sb.append(", visibleTasks=");
            sb.append(arraySet2);
            sb.append(", minimizedTasks=");
            sb.append(arraySet3);
            sb.append(", closingTasks=");
            sb.append(arraySet4);
            sb.append(", freeformTasksInZOrder=");
            sb.append(arrayList);
            sb.append(", fullImmersiveTaskId=");
            sb.append(num);
            sb.append(", topTransparentFullscreenTaskId=");
            sb.append(num2);
            sb.append(", leftTiledTaskId=");
            sb.append(num3);
            sb.append(", rightTiledTaskId=");
            sb.append(num4);
            sb.append(", usedDesk=");
            sb.append(i2);
            sb.append(", deskLabel=");
            return ReorderTile$$ExternalSyntheticOutline0.m(i3, ")", sb);
        }

        public /* synthetic */ Desk(int i, int i2, ArraySet arraySet, ArraySet arraySet2, ArraySet arraySet3, ArraySet arraySet4, ArrayList arrayList, Integer num, Integer num2, Integer num3, Integer num4, int i3, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, (i5 & 4) != 0 ? new ArraySet() : arraySet, (i5 & 8) != 0 ? new ArraySet() : arraySet2, (i5 & 16) != 0 ? new ArraySet() : arraySet3, (i5 & 32) != 0 ? new ArraySet() : arraySet4, (i5 & 64) != 0 ? new ArrayList() : arrayList, (i5 & 128) != 0 ? null : num, (i5 & 256) != 0 ? null : num2, (i5 & 512) != 0 ? null : num3, (i5 & 1024) != 0 ? null : num4, (i5 & 2048) != 0 ? -1 : i3, (i5 & 4096) != 0 ? 0 : i4);
        }
    }
}
