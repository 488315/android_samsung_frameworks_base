package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepository;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepository;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

public final class CustomTileInteractor {
    public final CoroutineContext backgroundContext;
    public UserHandle currentUser;
    public final CustomTileRepository customTileRepository;
    public final CustomTileDefaultsRepository defaultsRepository;
    public final CoroutineScope tileScope;
    public final TileSpec.CustomTileSpec tileSpec;
    public Job updatesJob;
    public final MutexImpl userMutex = MutexKt.Mutex$default();
    public final SharedFlowImpl tileUpdates = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);

    public CustomTileInteractor(TileSpec.CustomTileSpec customTileSpec, CustomTileDefaultsRepository customTileDefaultsRepository, CustomTileRepository customTileRepository, CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        this.tileSpec = customTileSpec;
        this.defaultsRepository = customTileDefaultsRepository;
        this.customTileRepository = customTileRepository;
        this.tileScope = coroutineScope;
        this.backgroundContext = coroutineContext;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0131 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x010f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a9 A[Catch: all -> 0x00af, TRY_LEAVE, TryCatch #5 {all -> 0x00af, blocks: (B:32:0x00f5, B:48:0x00a1, B:50:0x00a9, B:53:0x00b3, B:55:0x00b7, B:56:0x00ba, B:61:0x00da), top: B:47:0x00a1 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00b3 A[Catch: all -> 0x00af, TRY_ENTER, TryCatch #5 {all -> 0x00af, blocks: (B:32:0x00f5, B:48:0x00a1, B:50:0x00a9, B:53:0x00b3, B:55:0x00b7, B:56:0x00ba, B:61:0x00da), top: B:47:0x00a1 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0025  */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v16, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r11v19, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r11v2, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r11v20 */
    /* JADX WARN: Type inference failed for: r11v23 */
    /* JADX WARN: Type inference failed for: r11v29 */
    /* JADX WARN: Type inference failed for: r11v3, types: [com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v30 */
    /* JADX WARN: Type inference failed for: r11v32 */
    /* JADX WARN: Type inference failed for: r11v8 */
    /* JADX WARN: Type inference failed for: r2v18, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object initForUser(android.os.UserHandle r12, kotlin.coroutines.Continuation r13) {
        /*
            Method dump skipped, instructions count: 329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor.initForUser(android.os.UserHandle, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
