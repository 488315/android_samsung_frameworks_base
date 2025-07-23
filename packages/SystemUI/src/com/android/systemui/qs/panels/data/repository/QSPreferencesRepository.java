package com.android.systemui.qs.panels.data.repository;

import android.content.IntentFilter;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSPreferencesRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 backupRestorationEvents;
    public final DefaultLargeTilesRepository defaultLargeTilesRepository;
    public final Flow largeTilesSpecs;
    public final LogBuffer logBuffer;
    public final Lazy logger$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.panels.data.repository.QSPreferencesRepository$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new Logger(QSPreferencesRepository.this.logBuffer, "QSPreferencesRepository");
        }
    });
    public final UserFileManager userFileManager;
    public final UserRepository userRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public QSPreferencesRepository(UserFileManager userFileManager, UserRepository userRepository, DefaultLargeTilesRepository defaultLargeTilesRepository, CoroutineDispatcher coroutineDispatcher, LogBuffer logBuffer, BroadcastDispatcher broadcastDispatcher) {
        this.userFileManager = userFileManager;
        this.userRepository = userRepository;
        this.defaultLargeTilesRepository = defaultLargeTilesRepository;
        this.logBuffer = logBuffer;
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.android.systemui.backup.RESTORE_FINISHED"), null, 2), new QSPreferencesRepository$backupRestorationEvents$1(this, null)));
        this.backupRestorationEvents = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        this.largeTilesSpecs = FlowKt.flowOn(FlowKt.transformLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, ((UserRepositoryImpl) userRepository).selectedUserInfo, QSPreferencesRepository$largeTilesSpecs$3.INSTANCE), new QSPreferencesRepository$special$$inlined$flatMapLatest$1(null, this)), coroutineDispatcher);
    }
}
