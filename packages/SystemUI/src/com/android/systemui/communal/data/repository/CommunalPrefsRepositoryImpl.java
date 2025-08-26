package com.android.systemui.communal.data.repository;

import android.content.IntentFilter;
import android.content.pm.UserInfo;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* loaded from: classes2.dex */
public final class CommunalPrefsRepositoryImpl implements CommunalPrefsRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 backupRestorationEvents;
    public final CoroutineDispatcher bgDispatcher;
    public final Lazy logger$delegate;
    public final UserFileManager userFileManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl$setBooleanKeyValueForUser$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $key;
        final /* synthetic */ String $logMsg;
        final /* synthetic */ UserInfo $user;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(UserInfo userInfo, String str, String str2, Continuation continuation) {
            super(2, continuation);
            this.$user = userInfo;
            this.$key = str;
            this.$logMsg = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalPrefsRepositoryImpl.this.new AnonymousClass2(this.$user, this.$key, this.$logMsg, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CommunalPrefsRepositoryImpl communalPrefsRepositoryImpl = CommunalPrefsRepositoryImpl.this;
            UserInfo userInfo = this.$user;
            int i = CommunalPrefsRepositoryImpl.$r8$clinit;
            communalPrefsRepositoryImpl.getClass();
            ((UserFileManagerImpl) communalPrefsRepositoryImpl.userFileManager).getSharedPreferences$1(userInfo.id, "communal_hub_prefs").edit().putBoolean(this.$key, true).apply();
            Logger.i$default((Logger) CommunalPrefsRepositoryImpl.this.logger$delegate.getValue(), this.$logMsg, null, 2, null);
            return Unit.INSTANCE;
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
                return new Logger(logBuffer, "CommunalPrefsRepository");
            }
        });
        this.backupRestorationEvents = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.android.systemui.backup.RESTORE_FINISHED"), null, 2), new CommunalPrefsRepositoryImpl$backupRestorationEvents$1(this, null)));
    }

    public final Flow readKeyForUser(UserInfo userInfo, String str) {
        return kotlinx.coroutines.flow.FlowKt.flowOn(kotlinx.coroutines.flow.FlowKt.transformLatest(this.backupRestorationEvents, new CommunalPrefsRepositoryImpl$readKeyForUser$$inlined$flatMapLatest$1(null, this, userInfo, str)), this.bgDispatcher);
    }

    public final Object setBooleanKeyValueForUser(UserInfo userInfo, String str, String str2, SuspendLambda suspendLambda) {
        Object objWithContext = BuildersKt.withContext(this.bgDispatcher, new AnonymousClass2(userInfo, str, str2, null), suspendLambda);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }
}
