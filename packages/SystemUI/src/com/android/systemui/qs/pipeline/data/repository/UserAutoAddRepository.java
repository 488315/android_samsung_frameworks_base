package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.qs.pipeline.data.model.RestoreData;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class UserAutoAddRepository {
    public static final Companion Companion = new Companion(null);
    public StateFlow _autoAdded;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher bgDispatcher;
    public final SharedFlowImpl changeEvents = SharedFlowKt.MutableSharedFlow$default(0, 10, null, 5);
    public final QSPipelineLogger logger;
    public final SecureSettings secureSettings;
    public final int userId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ChangeAction {
        Set apply(Set set);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        UserAutoAddRepository create(int i);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MarkTile implements ChangeAction {
        public final TileSpec tileSpec;

        public MarkTile(TileSpec tileSpec) {
            this.tileSpec = tileSpec;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository.ChangeAction
        public final Set apply(Set set) {
            Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(set);
            mutableSet.add(this.tileSpec);
            return mutableSet;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof MarkTile) && Intrinsics.areEqual(this.tileSpec, ((MarkTile) obj).tileSpec);
        }

        public final int hashCode() {
            return this.tileSpec.hashCode();
        }

        public final String toString() {
            return "MarkTile(tileSpec=" + this.tileSpec + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RestoreTiles implements ChangeAction {
        public final RestoreData restoredData;

        public RestoreTiles(RestoreData restoreData) {
            this.restoredData = restoreData;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository.ChangeAction
        public final Set apply(Set set) {
            return SetsKt___SetsKt.plus(set, (Iterable) this.restoredData.restoredAutoAddedTiles);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof RestoreTiles) && Intrinsics.areEqual(this.restoredData, ((RestoreTiles) obj).restoredData);
        }

        public final int hashCode() {
            return this.restoredData.hashCode();
        }

        public final String toString() {
            return "RestoreTiles(restoredData=" + this.restoredData + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UnmarkTile implements ChangeAction {
        public final TileSpec tileSpec;

        public UnmarkTile(TileSpec tileSpec) {
            this.tileSpec = tileSpec;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository.ChangeAction
        public final Set apply(Set set) {
            Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(set);
            mutableSet.remove(this.tileSpec);
            return mutableSet;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof UnmarkTile) && Intrinsics.areEqual(this.tileSpec, ((UnmarkTile) obj).tileSpec);
        }

        public final int hashCode() {
            return this.tileSpec.hashCode();
        }

        public final String toString() {
            return "UnmarkTile(tileSpec=" + this.tileSpec + ")";
        }
    }

    public UserAutoAddRepository(int i, SecureSettings secureSettings, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.userId = i;
        this.secureSettings = secureSettings;
        this.logger = qSPipelineLogger;
        this.applicationScope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
    }

    public static final Object access$store(UserAutoAddRepository userAutoAddRepository, Set set, Continuation continuation) {
        userAutoAddRepository.getClass();
        ArrayList arrayList = new ArrayList();
        for (Object obj : set) {
            if (!(((TileSpec) obj) instanceof TileSpec.Invalid)) {
                arrayList.add(obj);
            }
        }
        Object withContext = BuildersKt.withContext(userAutoAddRepository.bgDispatcher, new UserAutoAddRepository$store$2(userAutoAddRepository, CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, new PropertyReference1Impl() { // from class: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository$store$toStore$2
            @Override // kotlin.jvm.internal.PropertyReference1Impl, kotlin.reflect.KProperty1
            public final Object get(Object obj2) {
                return ((TileSpec) obj2).getSpec();
            }
        }, 30), null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00d4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00d5 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object autoAdded(kotlin.coroutines.jvm.internal.ContinuationImpl r12) {
        /*
            Method dump skipped, instructions count: 214
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository.autoAdded(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
