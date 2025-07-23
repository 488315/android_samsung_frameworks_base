package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepository;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepository;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CustomTileInteractor {
    public final CoroutineContext backgroundContext;
    public UserHandle currentUser;
    public final CustomTileRepository customTileRepository;
    public final CustomTileDefaultsRepository defaultsRepository;
    public final CoroutineScope tileScope;
    public final TileSpec.CustomTileSpec tileSpec;
    public StandaloneCoroutine updatesJob;
    public final MutexImpl userMutex = MutexKt.Mutex$default();
    public final SharedFlowImpl tileUpdates = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);

    public CustomTileInteractor(TileSpec.CustomTileSpec customTileSpec, CustomTileDefaultsRepository customTileDefaultsRepository, CustomTileRepository customTileRepository, CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        this.tileSpec = customTileSpec;
        this.defaultsRepository = customTileDefaultsRepository;
        this.customTileRepository = customTileRepository;
        this.tileScope = coroutineScope;
        this.backgroundContext = coroutineContext;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:0|1|(4:(2:3|(7:5|6|7|(1:(1:(1:(1:(5:13|14|15|16|17)(2:20|21))(5:22|23|24|(4:27|15|16|17)|26))(9:28|29|30|31|32|33|34|(3:36|24|(0))|26))(1:46))(1:73)|47|48|(3:50|51|52)(12:53|(1:55)|56|57|58|59|60|61|62|63|(5:65|32|33|34|(0))|26)))|47|48|(0)(0))|76|6|7|(0)(0)|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x009e, code lost:
    
        if (r13.lock(r0) == r1) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x003e, code lost:
    
        r12 = th;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00aa A[Catch: all -> 0x00b0, TRY_LEAVE, TryCatch #0 {all -> 0x00b0, blocks: (B:32:0x00f7, B:48:0x00a2, B:50:0x00aa, B:53:0x00b4, B:55:0x00b8, B:56:0x00bb, B:61:0x00dc), top: B:47:0x00a2 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00b4 A[Catch: all -> 0x00b0, TRY_ENTER, TryCatch #0 {all -> 0x00b0, blocks: (B:32:0x00f7, B:48:0x00a2, B:50:0x00aa, B:53:0x00b4, B:55:0x00b8, B:56:0x00bb, B:61:0x00dc), top: B:47:0x00a2 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0025  */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v17, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r11v2, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r11v20, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r11v21 */
    /* JADX WARN: Type inference failed for: r11v24 */
    /* JADX WARN: Type inference failed for: r11v3, types: [com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v30 */
    /* JADX WARN: Type inference failed for: r11v31 */
    /* JADX WARN: Type inference failed for: r11v33 */
    /* JADX WARN: Type inference failed for: r11v9 */
    /* JADX WARN: Type inference failed for: r2v18, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object initForUser(android.os.UserHandle r12, kotlin.coroutines.jvm.internal.ContinuationImpl r13) {
        /*
            Method dump skipped, instructions count: 333
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor.initForUser(android.os.UserHandle, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
