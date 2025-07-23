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
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
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
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.FlatteningSequence;
import kotlin.sequences.FlatteningSequence$iterator$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;
import kotlin.sequences.TransformingSequence;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final SparseArray boundsBeforeMinimizeByTaskId = new SparseArray();
    public final SparseArray boundsBeforeFullImmersiveByTaskId = new SparseArray();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ActiveTasksListener {
        void onActiveTasksChanged(int i);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getINVALID_DESK_ID$annotations() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DeskChangeListener {
        void onActiveDeskChanged(int i, int i2, int i3);

        void onCanCreateDesksChanged(boolean z);

        void onDeskAdded(int i, int i2);

        void onDeskRemoved(int i, int i2);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DesktopData {
        void addDesk(int i, Desk desk);

        void createDesk(int i, int i2, int i3);

        Sequence desksSequence();

        Sequence desksSequence(int i);

        void forAllDesks(int i, Function1 function1);

        void forAllDesks(DesktopRepository$$ExternalSyntheticLambda4 desktopRepository$$ExternalSyntheticLambda4);

        void forAllDesks(Function2 function2);

        Desk getActiveDesk(int i);

        Set getAllActiveDesks();

        Desk getDefaultDesk(int i);

        Desk getDesk(int i);

        Desk getDeskForCreateByHome();

        Desk getDeskForNewDisplay();

        int getDisplayForDesk(int i);

        int getNumberOfDesks();

        int getNumberOfDesks(int i);

        void remove(int i);

        void removeDisplay(int i);

        void setActiveDesk(int i, int i2);

        void setDeskInactive(int i);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SingleDesktopData implements DesktopData {
        public final DesktopRepository$SingleDesktopData$deskByDisplayId$1 deskByDisplayId = new DesktopRepository$SingleDesktopData$deskByDisplayId$1();

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final Sequence desksSequence() {
            return SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.deskByDisplayId));
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void forAllDesks(int i, Function1 function1) {
            function1.mo779invoke(this.deskByDisplayId.getOrCreate(i));
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
            Sequence asSequence;
            Desk desk = (Desk) this.deskByDisplayId.get(i);
            return (desk == null || (asSequence = ArraysKt___ArraysKt.asSequence(new Desk[]{desk})) == null) ? EmptySequence.INSTANCE : asSequence;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void forAllDesks(DesktopRepository$$ExternalSyntheticLambda4 desktopRepository$$ExternalSyntheticLambda4) {
            DesktopRepository$SingleDesktopData$deskByDisplayId$1 desktopRepository$SingleDesktopData$deskByDisplayId$1 = this.deskByDisplayId;
            int size = desktopRepository$SingleDesktopData$deskByDisplayId$1.size();
            for (int i = 0; i < size; i++) {
                desktopRepository$SingleDesktopData$deskByDisplayId$1.keyAt(i);
                desktopRepository$$ExternalSyntheticLambda4.mo779invoke((Desk) desktopRepository$SingleDesktopData$deskByDisplayId$1.valueAt(i));
            }
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void forAllDesks(Function2 function2) {
            DesktopRepository$SingleDesktopData$deskByDisplayId$1 desktopRepository$SingleDesktopData$deskByDisplayId$1 = this.deskByDisplayId;
            int size = desktopRepository$SingleDesktopData$deskByDisplayId$1.size();
            for (int i = 0; i < size; i++) {
                int keyAt = desktopRepository$SingleDesktopData$deskByDisplayId$1.keyAt(i);
                function2.invoke(Integer.valueOf(keyAt), (Desk) desktopRepository$SingleDesktopData$deskByDisplayId$1.valueAt(i));
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface VisibleTasksListener {
        void onTasksVisibilityChanged(int i, int i2);
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

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|1|(2:3|(8:5|6|7|8|(1:(2:11|12)(2:18|19))(3:20|21|(1:23))|13|14|15))|27|6|7|8|(0)(0)|13|14|15) */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0030, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x005e, code lost:
    
        r13 = new java.lang.Object[]{r0.getStackTrace()};
        r12.getClass();
        logE("An exception occurred while updating the persistent repository \n%s", r13);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$updatePersistentRepositoryForDesk(com.android.wm.shell.desktopmode.DesktopRepository r12, com.android.wm.shell.desktopmode.DesktopRepository.Desk r13, kotlin.coroutines.jvm.internal.ContinuationImpl r14) {
        /*
            r12.getClass()
            boolean r0 = r14 instanceof com.android.wm.shell.desktopmode.DesktopRepository$updatePersistentRepositoryForDesk$2
            if (r0 == 0) goto L17
            r0 = r14
            com.android.wm.shell.desktopmode.DesktopRepository$updatePersistentRepositoryForDesk$2 r0 = (com.android.wm.shell.desktopmode.DesktopRepository$updatePersistentRepositoryForDesk$2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L17
            int r1 = r1 - r2
            r0.label = r1
        L15:
            r11 = r0
            goto L1d
        L17:
            com.android.wm.shell.desktopmode.DesktopRepository$updatePersistentRepositoryForDesk$2 r0 = new com.android.wm.shell.desktopmode.DesktopRepository$updatePersistentRepositoryForDesk$2
            r0.<init>(r12, r14)
            goto L15
        L1d:
            java.lang.Object r14 = r11.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 1
            if (r1 == 0) goto L3b
            if (r1 != r2) goto L33
            java.lang.Object r12 = r11.L$0
            com.android.wm.shell.desktopmode.DesktopRepository r12 = (com.android.wm.shell.desktopmode.DesktopRepository) r12
            kotlin.ResultKt.throwOnFailure(r14)     // Catch: java.lang.Exception -> L30
            goto L6e
        L30:
            r0 = move-exception
            r13 = r0
            goto L5e
        L33:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L3b:
            kotlin.ResultKt.throwOnFailure(r14)
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository r1 = r12.persistentRepository     // Catch: java.lang.Exception -> L30
            r14 = r2
            int r2 = r12.userId     // Catch: java.lang.Exception -> L30
            int r3 = r13.deskId     // Catch: java.lang.Exception -> L30
            android.util.ArraySet r4 = r13.visibleTasks     // Catch: java.lang.Exception -> L30
            android.util.ArraySet r5 = r13.minimizedTasks     // Catch: java.lang.Exception -> L30
            java.util.ArrayList r6 = r13.freeformTasksInZOrder     // Catch: java.lang.Exception -> L30
            java.lang.Integer r7 = r13.leftTiledTaskId     // Catch: java.lang.Exception -> L30
            java.lang.Integer r8 = r13.rightTiledTaskId     // Catch: java.lang.Exception -> L30
            int r9 = r13.displayId     // Catch: java.lang.Exception -> L30
            int r10 = r13.usedDesk     // Catch: java.lang.Exception -> L30
            r11.L$0 = r12     // Catch: java.lang.Exception -> L30
            r11.label = r14     // Catch: java.lang.Exception -> L30
            java.lang.Object r12 = r1.addOrUpdateDesktop(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Exception -> L30
            if (r12 != r0) goto L6e
            return r0
        L5e:
            java.lang.StackTraceElement[] r13 = r13.getStackTrace()
            java.lang.Object[] r13 = new java.lang.Object[]{r13}
            r12.getClass()
            java.lang.String r12 = "An exception occurred while updating the persistent repository \n%s"
            logE(r12, r13)
        L6e:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.DesktopRepository.access$updatePersistentRepositoryForDesk(com.android.wm.shell.desktopmode.DesktopRepository, com.android.wm.shell.desktopmode.DesktopRepository$Desk, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static void logD(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", str);
        SpreadBuilder m2 = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopRepository", objArr);
        ProtoLog.d(shellProtoLogGroup, m, m2.list.toArray(new Object[m2.list.size()]));
    }

    public static void logE(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", str);
        SpreadBuilder m2 = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopRepository", objArr);
        ProtoLog.e(shellProtoLogGroup, m, m2.list.toArray(new Object[m2.list.size()]));
    }

    public static void logW(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String concat = "%s: ".concat(str);
        SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopRepository", objArr);
        ProtoLog.w(shellProtoLogGroup, concat, m.list.toArray(new Object[m.list.size()]));
    }

    public final void addClosingTask(int i, Integer num, int i2) {
        Desk activeDesk;
        DesktopData desktopData = this.desktopData;
        if ((num == null || (activeDesk = desktopData.getDesk(num.intValue())) == null) && (activeDesk = desktopData.getActiveDesk(i)) == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Expected active desk in display: ").toString());
        }
        boolean add = activeDesk.closingTasks.add(Integer.valueOf(i2));
        int i3 = activeDesk.deskId;
        if (add) {
            logD("Added closing task=%d displayId=%d deskId=%d", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3));
        } else {
            logW("Task with taskId=%d displayId=%d deskId=%d is already closing", Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3));
        }
    }

    public final void addDesk(final int i, final int i2, final int i3) {
        Desk desk;
        logD("addDesk for displayId=%d and deskId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        final boolean canCreateDesks = canCreateDesks();
        DesktopData desktopData = this.desktopData;
        desktopData.createDesk(i, i2, i3);
        final boolean canCreateDesks2 = canCreateDesks();
        for (Map.Entry entry : this.deskChangeListeners.entrySet()) {
            final DeskChangeListener deskChangeListener = (DeskChangeListener) entry.getKey();
            ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$addDesk$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (i3 == -1) {
                        return;
                    }
                    deskChangeListener.onDeskAdded(i, i2);
                    boolean z = canCreateDesks;
                    boolean z2 = canCreateDesks2;
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
        desk.deskLabel = SequencesKt___SequencesKt.count(SequencesKt___SequencesKt.filter(desktopData.desksSequence(), new DesktopRepository$$ExternalSyntheticLambda2(0))) + 1;
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
        logD("addTaskToDesk for displayId=%d, deskId=%d, taskId=%d, isVisible=%b", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Boolean.valueOf(z));
        logD("addOrMoveTaskToTopOfDesk displayId=%d, deskId=%d, taskId=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        DesktopData desktopData = this.desktopData;
        Desk desk = desktopData.getDesk(i2);
        if (desk == null) {
            throw new IllegalStateException(("Could not find desk: " + i2).toString());
        }
        logD("addOrMoveTaskToTopOfDesk: display=%d deskId=%d taskId=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        desktopData.forAllDesks(new Function2() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$$ExternalSyntheticLambda6
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
        desktopData.forAllDesks(i, new DesktopRepository$$ExternalSyntheticLambda4(i3, 1, this));
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
            updatePersistentRepository(i);
        }
        logD("addActiveTaskToDesk for displayId=%d, deskId=%d, taskId=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        Desk desk2 = desktopData.getDesk(i2);
        if (desk2 == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "Did not find desk: ").toString());
        }
        removeActiveTask(i3, Integer.valueOf(i2));
        if (desk2.activeTasks.add(Integer.valueOf(i3))) {
            logD("Adds active task=%d displayId=%d deskId=%d", Integer.valueOf(i3), Integer.valueOf(i), Integer.valueOf(i2));
            if (i == 0 && desk2.usedDesk != 1) {
                Desk activeDesk = desktopData.getActiveDesk(i);
                if (activeDesk == null || activeDesk.deskId != i2) {
                    desk2.usedDesk = 0;
                } else {
                    desk2.usedDesk = 2;
                }
                updatePersistentRepositoryForDesk(i2);
            }
            updateActiveTasksListeners(i);
        }
        updateTaskInDesk(i, i2, i3, z);
    }

    public final void addVisibleTasksListener(final VisibleTasksListener visibleTasksListener, Executor executor) {
        this.visibleTasksListeners.put(visibleTasksListener, executor);
        Sequence desksSequence = this.desktopData.desksSequence();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : desksSequence) {
            Integer valueOf = Integer.valueOf(((Desk) obj).displayId);
            Object obj2 = linkedHashMap.get(valueOf);
            if (obj2 == null) {
                obj2 = new ArrayList();
                linkedHashMap.put(valueOf, obj2);
            }
            ((List) obj2).add(obj);
        }
        Iterator it = linkedHashMap.keySet().iterator();
        while (it.hasNext()) {
            final int intValue = ((Number) it.next()).intValue();
            final int visibleTaskCount = getVisibleTaskCount(intValue);
            executor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$addVisibleTasksListener$2$1
                @Override // java.lang.Runnable
                public final void run() {
                    DesktopRepository.VisibleTasksListener.this.onTasksVisibilityChanged(intValue, visibleTaskCount);
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
        return SequencesKt___SequencesKt.toSet(new TransformingSequence(this.desktopData.desksSequence(), new DesktopRepository$$ExternalSyntheticLambda2(5)));
    }

    public final Integer getDefaultDeskId(int i) {
        Desk defaultDesk = this.desktopData.getDefaultDesk(i);
        if (defaultDesk != null) {
            return Integer.valueOf(defaultDesk.deskId);
        }
        return null;
    }

    public final Integer getDeskIdForTask(int i) {
        Object obj;
        Iterator it = this.desktopData.desksSequence().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (((Desk) obj).activeTasks.contains(Integer.valueOf(i))) {
                break;
            }
        }
        Desk desk = (Desk) obj;
        if (desk != null) {
            return Integer.valueOf(desk.deskId);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Integer getDisplayIdForTask(final int i) {
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        this.desktopData.forAllDesks(new Function2() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$$ExternalSyntheticLambda1
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
        boolean isTrue = DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue();
        DesktopData desktopData = this.desktopData;
        if (isTrue) {
            return desktopData.getActiveDesk(i) != null;
        }
        if (desktopData.getDefaultDesk(i) != null) {
            return !r3.visibleTasks.isEmpty();
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
            Set subtract = CollectionsKt___CollectionsKt.subtract(CollectionsKt___CollectionsKt.subtract(desk.visibleTasks, desk.closingTasks), desk.minimizedTasks);
            Object obj = null;
            if (subtract instanceof List) {
                List list = (List) subtract;
                if (list.size() == 1) {
                    obj = list.get(0);
                }
            } else {
                Iterator it = subtract.iterator();
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
        Integer valueOf = activeDesk != null ? Integer.valueOf(activeDesk.deskId) : null;
        if (valueOf == null) {
            logD("Minimize task: No active desk found for task: taskId=%d", Integer.valueOf(i2));
        } else {
            minimizeTaskInDesk(i, valueOf.intValue(), i2);
        }
    }

    public final void minimizeTaskInDesk(int i, int i2, int i3) {
        ArraySet arraySet;
        logD("MinimizeTaskInDesk: displayId=%d deskId=%d, task=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        Desk desk = this.desktopData.getDesk(i2);
        if (desk == null || (arraySet = desk.minimizedTasks) == null) {
            logD("Minimize task: No active desk found for task: taskId=%d", Integer.valueOf(i3));
        } else {
            arraySet.add(Integer.valueOf(i3));
        }
        updateTaskInDesk(i, i2, i3, false);
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
            updatePersistentRepositoryForDesk(i2);
        }
    }

    public final void notifyVisibleTaskListeners(final int i, final int i2) {
        for (Map.Entry entry : this.visibleTasksListeners.entrySet()) {
            final VisibleTasksListener visibleTasksListener = (VisibleTasksListener) entry.getKey();
            ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$notifyVisibleTaskListeners$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    DesktopRepository.VisibleTasksListener.this.onTasksVisibilityChanged(i, i2);
                }
            });
        }
    }

    public final void onDeskDisplayChanged(final int i, final int i2) {
        boolean canCreateDesks = canCreateDesks();
        DesktopData desktopData = this.desktopData;
        Desk desk = desktopData.getDesk(i);
        if (desk == null) {
            throw new IllegalStateException(("Expected to find desk with id: " + i).toString());
        }
        Desk deepCopy = desk.deepCopy();
        deepCopy.displayId = i2;
        if (CoreRune.DW_MULTIPLE_DESKS) {
            removeDesk(i, false);
        } else {
            removeDesk(i, true);
        }
        desktopData.addDesk(i2, deepCopy);
        boolean canCreateDesks2 = canCreateDesks();
        for (Map.Entry entry : this.deskChangeListeners.entrySet()) {
            final DeskChangeListener deskChangeListener = (DeskChangeListener) entry.getKey();
            ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$onDeskDisplayChanged$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    DesktopRepository.DeskChangeListener.this.onDeskAdded(i2, i);
                }
            });
            if (canCreateDesks != canCreateDesks2) {
                deskChangeListener.onCanCreateDesksChanged(canCreateDesks2);
            }
        }
        logD("onDeskDisplayChanged deskId=%d newDisplayId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        updatePersistentRepository(i2);
    }

    public final void removeActiveTask(int i, Integer num) {
        logD("removeActiveTask for taskId=%d, excludedDeskId=%d", Integer.valueOf(i), num);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(this.desktopData.desksSequence(), new DesktopRepository$$ExternalSyntheticLambda0(num, 0)));
        while (filteringSequence$iterator$1.hasNext()) {
            Desk desk = (Desk) filteringSequence$iterator$1.next();
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
        final boolean canCreateDesks = canCreateDesks();
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
        final boolean canCreateDesks2 = canCreateDesks();
        if (desktopData.getNumberOfDesks(desk.displayId) == 0) {
            DesktopStateImpl.Companion.getClass();
            if (DesktopStateImpl.desktopExternalDisplayId == desk.displayId) {
                DesktopStateImpl.desktopExternalDisplayId = -1;
            }
        }
        if (CoreRune.DW_DESK_LABEL && z) {
            Iterator it = new SequencesKt___SequencesKt$sortedWith$1(desktopData.desksSequence(), new Comparator() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$reassignDeskLabel$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((DesktopRepository.Desk) obj).deskLabel), Integer.valueOf(((DesktopRepository.Desk) obj2).deskLabel));
                }
            }).iterator();
            while (it.hasNext()) {
                Object next = it.next();
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
                        boolean z3 = canCreateDesks;
                        boolean z4 = canCreateDesks2;
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
            desktopData.forAllDesks(i, new DesktopRepository$$ExternalSyntheticLambda4(i2, 0, this));
            return;
        }
        Integer displayIdForTask = getDisplayIdForTask(i2);
        if (displayIdForTask != null) {
            int intValue = displayIdForTask.intValue();
            logD("Removes freeform task: taskId=%d, displayId=%d", Integer.valueOf(i2), Integer.valueOf(intValue));
            desktopData.forAllDesks(intValue, new DesktopRepository$$ExternalSyntheticLambda4(i2, 0, this));
        }
    }

    public final void removeTaskFromDesk(int i, int i2) {
        String joinToString$default;
        logD("removeTaskFromDesk: deskId=%d, taskId=%d", Integer.valueOf(i), Integer.valueOf(i2));
        this.boundsBeforeMaximizeByTaskId.remove(i2);
        this.boundsBeforeFullImmersiveByTaskId.remove(i2);
        Desk desk = this.desktopData.getDesk(i);
        if (desk == null) {
            return;
        }
        boolean remove = desk.freeformTasksInZOrder.remove(Integer.valueOf(i2));
        int i3 = desk.deskId;
        if (remove) {
            Integer valueOf = Integer.valueOf(i3);
            joinToString$default = CollectionsKt___CollectionsKt.joinToString$default(desk.freeformTasksInZOrder, ", ", "[", "]", null, 56);
            logD("Remaining freeform tasks in desk: %d, tasks: %s", valueOf, joinToString$default);
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
        Object obj;
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
                        obj = it.next();
                        if (((Desk) obj).usedDesk == 2) {
                            break;
                        }
                    } else {
                        obj = null;
                        break;
                    }
                }
                Desk desk3 = (Desk) obj;
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
            desk.deskLabel = SequencesKt___SequencesKt.count(SequencesKt___SequencesKt.filter(desktopData.desksSequence(), new DesktopRepository$$ExternalSyntheticLambda2(0))) + 1;
        }
        desktopData.setActiveDesk(i, i2);
        for (Map.Entry entry : this.deskChangeListeners.entrySet()) {
            final DeskChangeListener deskChangeListener = (DeskChangeListener) entry.getKey();
            ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$setActiveDesk$4$1
                @Override // java.lang.Runnable
                public final void run() {
                    DesktopRepository.DeskChangeListener.this.onActiveDeskChanged(i, i2, i3);
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
                    DesktopRepository.DeskChangeListener.this.onActiveDeskChanged(displayForDesk, -1, i);
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
        BuildersKt.launch$default(this.mainCoroutineScope, null, null, new DesktopRepository$updatePersistentRepository$1(SequencesKt___SequencesKt.toList(new TransformingSequence(this.desktopData.desksSequence(i), new DesktopRepository$$ExternalSyntheticLambda2(3))), this, null), 3);
    }

    public final void updatePersistentRepositoryForDesk(int i) {
        Desk desk = this.desktopData.getDesk(i);
        if (desk != null) {
            BuildersKt.launch$default(this.mainCoroutineScope, null, null, new DesktopRepository$updatePersistentRepositoryForDesk$1(this, desk.deepCopy(), null), 3);
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
            final Integer valueOf = Integer.valueOf(i2);
            desktopData.forAllDesks(new Function2() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj).intValue();
                    DesktopRepository.Desk desk = (DesktopRepository.Desk) obj2;
                    int i4 = DesktopRepository.$r8$clinit;
                    int i5 = desk.deskId;
                    Integer num = valueOf;
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
        int i4 = 0;
        int size = (desk2 == null || (arraySet2 = desk2.visibleTasks) == null) ? 0 : arraySet2.size();
        if (z) {
            desk.visibleTasks.add(Integer.valueOf(i3));
            logD("UnminimizeTask: display=%d, task=%d", Integer.valueOf(i), Integer.valueOf(i3));
            desktopData.forAllDesks(i, new DesktopRepository$$ExternalSyntheticLambda4(i3, 1, this));
        } else {
            desk.visibleTasks.remove(Integer.valueOf(i3));
        }
        Desk desk3 = desktopData.getDesk(i2);
        if (desk3 != null && (arraySet = desk3.visibleTasks) != null) {
            i4 = arraySet.size();
        }
        if (size != i4) {
            logD("Update task visibility taskId=%d visible=%b deskId=%d displayId=%d", Integer.valueOf(i3), Boolean.valueOf(z), Integer.valueOf(i2), Integer.valueOf(i));
            logD("VisibleTaskCount has changed from %d to %d", Integer.valueOf(size), Integer.valueOf(i4));
            notifyVisibleTaskListeners(i, i4);
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
            int intValue = ((Number) it.next()).intValue();
            desk.freeformTasksInZOrder.remove(Integer.valueOf(intValue));
            desk.freeformTasksInZOrder.add(0, Integer.valueOf(intValue));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            return SequencesKt___SequencesKt.flatMap(SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.desktopDisplays)), new DesktopRepository$$ExternalSyntheticLambda2(6));
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final void forAllDesks(DesktopRepository$$ExternalSyntheticLambda4 desktopRepository$$ExternalSyntheticLambda4) {
            SparseArray sparseArray = this.desktopDisplays;
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                sparseArray.keyAt(i);
                Iterator it = ((DesktopDisplay) sparseArray.valueAt(i)).orderedDesks.iterator();
                while (it.hasNext()) {
                    desktopRepository$$ExternalSyntheticLambda4.mo779invoke((Desk) it.next());
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
            return SequencesKt___SequencesKt.toSet(new TransformingSequence(SequencesKt___SequencesKt.filter(SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.desktopDisplays)), new DesktopRepository$$ExternalSyntheticLambda2(7)), new DesktopRepository$$ExternalSyntheticLambda2(8)));
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
        public final Desk getDeskForNewDisplay() {
            Object obj;
            DesktopDisplay desktopDisplay = (DesktopDisplay) this.desktopDisplays.get(0);
            if (desktopDisplay != null) {
                Iterator it = desktopDisplay.orderedDesks.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        obj = null;
                        break;
                    }
                    obj = it.next();
                    if (((Desk) obj).usedDesk == 1) {
                        break;
                    }
                }
                Desk desk = (Desk) obj;
                if (desk != null) {
                    return desk;
                }
                FilteringSequence filter = SequencesKt___SequencesKt.filter(desksSequence(0), new DesktopRepository$$ExternalSyntheticLambda0(desktopDisplay, 1));
                if (desktopDisplay.activeDeskId != null || SequencesKt___SequencesKt.count(filter) > 1) {
                    return (Desk) SequencesKt___SequencesKt.firstOrNull(new SequencesKt___SequencesKt$sortedWith$1(filter, new Comparator() { // from class: com.android.wm.shell.desktopmode.DesktopRepository$MultiDesktopData$getDeskForNewDisplay$$inlined$sortedBy$1
                        @Override // java.util.Comparator
                        public final int compare(Object obj2, Object obj3) {
                            return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((DesktopRepository.Desk) obj2).deskId), Integer.valueOf(((DesktopRepository.Desk) obj3).deskId));
                        }
                    }));
                }
            }
            return null;
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final int getDisplayForDesk(int i) {
            Object obj;
            FlatteningSequence$iterator$1 flatteningSequence$iterator$1 = new FlatteningSequence$iterator$1((FlatteningSequence) desksSequence());
            while (true) {
                if (!flatteningSequence$iterator$1.hasNext()) {
                    obj = null;
                    break;
                }
                obj = flatteningSequence$iterator$1.next();
                if (((Desk) obj).deskId == i) {
                    break;
                }
            }
            Desk desk = (Desk) obj;
            if (desk != null) {
                return desk.displayId;
            }
            throw new IllegalStateException(("Display for desk=" + i + " not found").toString());
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.DesktopData
        public final int getNumberOfDesks() {
            Iterator it = SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.desktopDisplays)).iterator();
            int i = 0;
            while (it.hasNext()) {
                Set set = ((DesktopDisplay) it.next()).orderedDesks;
                ArrayList arrayList = new ArrayList();
                for (Object obj : set) {
                    if (((Desk) obj).usedDesk != -1) {
                        arrayList.add(obj);
                    }
                }
                i += arrayList.size();
            }
            return i;
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
                        return ((Boolean) Function1.this.mo779invoke(obj)).booleanValue();
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
            FlatteningSequence$iterator$1 flatteningSequence$iterator$1 = new FlatteningSequence$iterator$1(SequencesKt___SequencesKt.flatMap(SequencesKt___SequencesKt.filter(SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.desktopDisplays)), new DesktopRepository$MultiDesktopData$$ExternalSyntheticLambda1(i, 1)), new DesktopRepository$$ExternalSyntheticLambda2(9)));
            while (flatteningSequence$iterator$1.hasNext()) {
                function1.mo779invoke((Desk) flatteningSequence$iterator$1.next());
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int hashCode = (this.orderedDesks.hashCode() + (Integer.hashCode(this.displayId) * 31)) * 31;
            Integer num = this.activeDeskId;
            return hashCode + (num == null ? 0 : num.hashCode());
        }

        public final String toString() {
            return "DesktopDisplay(displayId=" + this.displayId + ", orderedDesks=" + this.orderedDesks + ", activeDeskId=" + this.activeDeskId + ")";
        }

        public /* synthetic */ DesktopDisplay(int i, Set set, Integer num, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, (i2 & 2) != 0 ? new LinkedHashSet() : set, (i2 & 4) != 0 ? null : num);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int hashCode = (this.freeformTasksInZOrder.hashCode() + ((this.closingTasks.hashCode() + ((this.minimizedTasks.hashCode() + ((this.visibleTasks.hashCode() + ((this.activeTasks.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, Integer.hashCode(this.deskId) * 31, 31)) * 31)) * 31)) * 31)) * 31)) * 31;
            Integer num = this.fullImmersiveTaskId;
            int hashCode2 = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
            Integer num2 = this.topTransparentFullscreenTaskId;
            int hashCode3 = (hashCode2 + (num2 == null ? 0 : num2.hashCode())) * 31;
            Integer num3 = this.leftTiledTaskId;
            int hashCode4 = (hashCode3 + (num3 == null ? 0 : num3.hashCode())) * 31;
            Integer num4 = this.rightTiledTaskId;
            return Integer.hashCode(this.deskLabel) + ReorderTile$$ExternalSyntheticOutline0.m(this.usedDesk, (hashCode4 + (num4 != null ? num4.hashCode() : 0)) * 31, 31);
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
