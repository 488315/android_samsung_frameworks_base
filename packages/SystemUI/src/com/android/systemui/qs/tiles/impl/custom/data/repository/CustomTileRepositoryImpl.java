package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.os.UserHandle;
import android.service.quicksettings.Tile;
import com.android.systemui.qs.external.CustomTileStatePersister;
import com.android.systemui.qs.external.PackageManagerAdapter;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CustomTileRepositoryImpl implements CustomTileRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineContext backgroundContext;
    public final CustomTileStatePersister customTileStatePersister;
    public final PackageManagerAdapter packageManagerAdapter;
    public final TileSpec.CustomTileSpec tileSpec;
    public final MutexImpl tileUpdateMutex = MutexKt.Mutex$default();
    public final SharedFlowImpl tileWithUserState = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TileWithUser {
        public final Tile tile;
        public final UserHandle user;

        public TileWithUser(UserHandle userHandle, Tile tile) {
            this.user = userHandle;
            this.tile = tile;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TileWithUser)) {
                return false;
            }
            TileWithUser tileWithUser = (TileWithUser) obj;
            return Intrinsics.areEqual(this.user, tileWithUser.user) && Intrinsics.areEqual(this.tile, tileWithUser.tile);
        }

        public final int hashCode() {
            return this.tile.hashCode() + (this.user.hashCode() * 31);
        }

        public final String toString() {
            return "TileWithUser(user=" + this.user + ", tile=" + this.tile + ")";
        }
    }

    static {
        new Companion(null);
    }

    public CustomTileRepositoryImpl(TileSpec.CustomTileSpec customTileSpec, CustomTileStatePersister customTileStatePersister, PackageManagerAdapter packageManagerAdapter, CoroutineContext coroutineContext) {
        this.tileSpec = customTileSpec;
        this.customTileStatePersister = customTileStatePersister;
        this.packageManagerAdapter = packageManagerAdapter;
        this.backgroundContext = coroutineContext;
    }

    public final TileWithUser getCurrentTileWithUser() {
        return (TileWithUser) CollectionsKt___CollectionsKt.lastOrNull(this.tileWithUserState.getReplayCache());
    }

    public final Object isTileActive(ContinuationImpl continuationImpl) {
        return BuildersKt.withContext(this.backgroundContext, new CustomTileRepositoryImpl$isTileActive$2(this, null), continuationImpl);
    }

    public final Object isTileToggleable(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundContext, new CustomTileRepositoryImpl$isTileToggleable$2(this, null), continuation);
    }

    public final Object restoreForTheUserIfNeeded(UserHandle userHandle, Continuation continuation, boolean z) {
        if (z) {
            TileWithUser currentTileWithUser = getCurrentTileWithUser();
            if (!Intrinsics.areEqual(currentTileWithUser != null ? currentTileWithUser.user : null, userHandle)) {
                return BuildersKt.withContext(this.backgroundContext, new CustomTileRepositoryImpl$restoreForTheUserIfNeeded$2(this, userHandle, null), continuation);
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x0075, code lost:
    
        if (r10.lock(r0) == r1) goto L38;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007e A[Catch: all -> 0x0091, TryCatch #0 {all -> 0x0091, blocks: (B:14:0x00bd, B:30:0x0078, B:32:0x007e, B:33:0x0084, B:35:0x008a, B:36:0x0098, B:38:0x009d, B:42:0x0093), top: B:29:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x008a A[Catch: all -> 0x0091, TryCatch #0 {all -> 0x0091, blocks: (B:14:0x00bd, B:30:0x0078, B:32:0x007e, B:33:0x0084, B:35:0x008a, B:36:0x0098, B:38:0x009d, B:42:0x0093), top: B:29:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009d A[Catch: all -> 0x0091, TryCatch #0 {all -> 0x0091, blocks: (B:14:0x00bd, B:30:0x0078, B:32:0x007e, B:33:0x0084, B:35:0x008a, B:36:0x0098, B:38:0x009d, B:42:0x0093), top: B:29:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0093 A[Catch: all -> 0x0091, TryCatch #0 {all -> 0x0091, blocks: (B:14:0x00bd, B:30:0x0078, B:32:0x007e, B:33:0x0084, B:35:0x008a, B:36:0x0098, B:38:0x009d, B:42:0x0093), top: B:29:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /* JADX WARN: Type inference failed for: r6v11, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r7v13, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r7v3, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r7v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateTile(android.os.UserHandle r7, boolean r8, kotlin.jvm.functions.Function1 r9, kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            Method dump skipped, instructions count: 209
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl.updateTile(android.os.UserHandle, boolean, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
