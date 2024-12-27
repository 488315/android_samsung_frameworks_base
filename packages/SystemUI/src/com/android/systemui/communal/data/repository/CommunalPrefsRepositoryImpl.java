package com.android.systemui.communal.data.repository;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.UserInfo;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommunalPrefsRepositoryImpl implements CommunalPrefsRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineDispatcher bgDispatcher;
    public final ReadonlyStateFlow isCtaDismissed;
    public final Logger logger;
    public final UserFileManager userFileManager;
    public final UserRepository userRepository;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public CommunalPrefsRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, UserRepository userRepository, UserFileManager userFileManager, BroadcastDispatcher broadcastDispatcher, LogBuffer logBuffer, TableLogBuffer tableLogBuffer) {
        this.bgDispatcher = coroutineDispatcher;
        this.userRepository = userRepository;
        this.userFileManager = userFileManager;
        this.logger = new Logger(logBuffer, "CommunalPrefsRepositoryImpl");
        this.isCtaDismissed = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) FlowKt.transformLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((UserRepositoryImpl) userRepository).selectedUserInfo, new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.android.systemui.backup.RESTORE_FINISHED"), null, 4, "com.android.systemui.permission.SELF", 2)), new CommunalPrefsRepositoryImpl$isCtaDismissed$1(this, null)), new CommunalPrefsRepositoryImpl$isCtaDismissed$2(null)), new CommunalPrefsRepositoryImpl$special$$inlined$flatMapLatest$1(null, this)), tableLogBuffer, "", "isCtaDismissed", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
    }

    public final SharedPreferences getSharedPrefsForUser(UserInfo userInfo) {
        return ((UserFileManagerImpl) this.userFileManager).getSharedPreferences$1(userInfo.id, "communal_hub_prefs");
    }

    public final Object setCtaDismissedForCurrentUser(Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.bgDispatcher, new CommunalPrefsRepositoryImpl$setCtaDismissedForCurrentUser$2(this, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
