package com.android.systemui.communal.data.repository;

import android.content.IntentFilter;
import android.content.pm.UserInfo;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalPrefsRepositoryImpl implements CommunalPrefsRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 backupRestorationEvents;
    public final CoroutineDispatcher bgDispatcher;
    public final Lazy logger$delegate;
    public final UserFileManager userFileManager;

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

    public CommunalPrefsRepositoryImpl(CoroutineDispatcher coroutineDispatcher, UserFileManager userFileManager, BroadcastDispatcher broadcastDispatcher, final LogBuffer logBuffer) {
        this.bgDispatcher = coroutineDispatcher;
        this.userFileManager = userFileManager;
        this.logger$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = CommunalPrefsRepositoryImpl.$r8$clinit;
                return new Logger(LogBuffer.this, "CommunalPrefsRepository");
            }
        });
        this.backupRestorationEvents = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.android.systemui.backup.RESTORE_FINISHED"), null, 2), new CommunalPrefsRepositoryImpl$backupRestorationEvents$1(this, null)));
    }

    public final Flow readKeyForUser(UserInfo userInfo, String str) {
        return FlowKt.flowOn(FlowKt.transformLatest(this.backupRestorationEvents, new CommunalPrefsRepositoryImpl$readKeyForUser$$inlined$flatMapLatest$1(null, this, userInfo, str)), this.bgDispatcher);
    }

    public final Object setBooleanKeyValueForUser(UserInfo userInfo, String str, String str2, SuspendLambda suspendLambda) {
        Object withContext = BuildersKt.withContext(this.bgDispatcher, new CommunalPrefsRepositoryImpl$setBooleanKeyValueForUser$2(this, userInfo, str, str2, null), suspendLambda);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
