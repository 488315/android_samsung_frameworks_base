package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.os.UserHandle;
import android.service.quicksettings.Tile;
import com.android.systemui.qs.external.CustomTileStatePersister;
import com.android.systemui.qs.external.PackageManagerAdapter;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CustomTileRepositoryImpl implements CustomTileRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineContext backgroundContext;
    public final CustomTileStatePersister customTileStatePersister;
    public final PackageManagerAdapter packageManagerAdapter;
    public final TileSpec.CustomTileSpec tileSpec;
    public final MutexImpl tileUpdateMutex = MutexKt.Mutex$default();
    public final SharedFlowImpl tileWithUserState = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public final Object isTileActive(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundContext, new CustomTileRepositoryImpl$isTileActive$2(this, null), continuation);
    }

    public final Object isTileToggleable(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundContext, new CustomTileRepositoryImpl$isTileToggleable$2(this, null), continuation);
    }

    public final Object restoreForTheUserIfNeeded(UserHandle userHandle, Continuation continuation, boolean z) {
        if (z) {
            TileWithUser tileWithUser = (TileWithUser) CollectionsKt___CollectionsKt.lastOrNull(this.tileWithUserState.getReplayCache());
            if (!Intrinsics.areEqual(tileWithUser != null ? tileWithUser.user : null, userHandle)) {
                return BuildersKt.withContext(this.backgroundContext, new CustomTileRepositoryImpl$restoreForTheUserIfNeeded$2(this, userHandle, null), continuation);
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0086 A[Catch: all -> 0x0099, TryCatch #0 {all -> 0x0099, blocks: (B:14:0x00c5, B:30:0x0078, B:32:0x0086, B:33:0x008c, B:35:0x0092, B:36:0x00a0, B:38:0x00a5, B:42:0x009b), top: B:29:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0092 A[Catch: all -> 0x0099, TryCatch #0 {all -> 0x0099, blocks: (B:14:0x00c5, B:30:0x0078, B:32:0x0086, B:33:0x008c, B:35:0x0092, B:36:0x00a0, B:38:0x00a5, B:42:0x009b), top: B:29:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00a5 A[Catch: all -> 0x0099, TryCatch #0 {all -> 0x0099, blocks: (B:14:0x00c5, B:30:0x0078, B:32:0x0086, B:33:0x008c, B:35:0x0092, B:36:0x00a0, B:38:0x00a5, B:42:0x009b), top: B:29:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x009b A[Catch: all -> 0x0099, TryCatch #0 {all -> 0x0099, blocks: (B:14:0x00c5, B:30:0x0078, B:32:0x0086, B:33:0x008c, B:35:0x0092, B:36:0x00a0, B:38:0x00a5, B:42:0x009b), top: B:29:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x008b  */
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
    public final java.lang.Object updateTile(android.os.UserHandle r7, boolean r8, kotlin.jvm.functions.Function1 r9, kotlin.coroutines.Continuation r10) {
        /*
            Method dump skipped, instructions count: 217
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl.updateTile(android.os.UserHandle, boolean, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object updateWithDefaults(UserHandle userHandle, final CustomTileDefaults customTileDefaults, boolean z, Continuation continuation) {
        if (!(customTileDefaults instanceof CustomTileDefaults.Result)) {
            return Unit.INSTANCE;
        }
        Object updateTile = updateTile(userHandle, z, new Function1() { // from class: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$updateWithDefaults$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Removed duplicated region for block: B:7:0x004b  */
            @Override // kotlin.jvm.functions.Function1
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke(java.lang.Object r7) {
                /*
                    r6 = this;
                    android.service.quicksettings.Tile r7 = (android.service.quicksettings.Tile) r7
                    android.graphics.drawable.Icon r0 = r7.getIcon()
                    r1 = 1
                    if (r0 == 0) goto L4c
                    com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl r0 = com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl.this
                    android.graphics.drawable.Icon r2 = r7.getIcon()
                    com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults r3 = r2
                    com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults$Result r3 = (com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults.Result) r3
                    android.graphics.drawable.Icon r3 = r3.icon
                    int r4 = com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl.$r8$clinit
                    r0.getClass()
                    r0 = 0
                    if (r3 != 0) goto L1f
                L1d:
                    r2 = r0
                    goto L48
                L1f:
                    if (r2 != r3) goto L23
                    r2 = r1
                    goto L48
                L23:
                    int r4 = r2.getType()
                    r5 = 2
                    if (r4 != r5) goto L1d
                    int r4 = r3.getType()
                    if (r4 == r5) goto L31
                    goto L1d
                L31:
                    int r4 = r2.getResId()
                    int r5 = r3.getResId()
                    if (r4 == r5) goto L3c
                    goto L1d
                L3c:
                    java.lang.String r2 = r2.getResPackage()
                    java.lang.String r3 = r3.getResPackage()
                    boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r3)
                L48:
                    if (r2 == 0) goto L4b
                    goto L4c
                L4b:
                    r1 = r0
                L4c:
                    if (r1 == 0) goto L57
                    com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults r0 = r2
                    com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults$Result r0 = (com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults.Result) r0
                    android.graphics.drawable.Icon r0 = r0.icon
                    r7.setIcon(r0)
                L57:
                    com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults r6 = r2
                    com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults$Result r6 = (com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults.Result) r6
                    java.lang.CharSequence r6 = r6.label
                    r7.setDefaultLabel(r6)
                    kotlin.Unit r6 = kotlin.Unit.INSTANCE
                    return r6
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$updateWithDefaults$2.invoke(java.lang.Object):java.lang.Object");
            }
        }, continuation);
        return updateTile == CoroutineSingletons.COROUTINE_SUSPENDED ? updateTile : Unit.INSTANCE;
    }

    public final Object updateWithTile(UserHandle userHandle, final Tile tile, boolean z, Continuation continuation) {
        Object updateTile = updateTile(userHandle, z, new Function1() { // from class: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$updateWithTile$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Tile tile2 = (Tile) obj;
                Tile tile3 = tile;
                if (tile3.getIcon() != null) {
                    tile2.setIcon(tile3.getIcon());
                }
                if (tile3.getCustomLabel() != null) {
                    tile2.setLabel(tile3.getCustomLabel());
                }
                if (tile3.getSubtitle() != null) {
                    tile2.setSubtitle(tile3.getSubtitle());
                }
                if (tile3.getContentDescription() != null) {
                    tile2.setContentDescription(tile3.getContentDescription());
                }
                if (tile3.getStateDescription() != null) {
                    tile2.setStateDescription(tile3.getStateDescription());
                }
                tile2.setActivityLaunchForClick(tile3.getActivityLaunchForClick());
                tile2.setState(tile3.getState());
                return Unit.INSTANCE;
            }
        }, continuation);
        return updateTile == CoroutineSingletons.COROUTINE_SUSPENDED ? updateTile : Unit.INSTANCE;
    }
}
