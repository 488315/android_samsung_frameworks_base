package com.android.systemui.user.data.repository;

import android.R;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserManager;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UserRepositoryImpl implements UserRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _isUserSwitchingInProgress;
    public final StateFlowImpl _selectedUserInfo;
    public final StateFlowImpl _userInfos;
    public final ReadonlyStateFlow _userSwitcherSettings;
    public final Context appContext;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final GlobalSettings globalSettings;
    public final boolean isGuestUserAutoCreated;
    public final AtomicBoolean isGuestUserCreationScheduled;
    public boolean isGuestUserResetting;
    public final boolean isStatusBarUserChipEnabled;
    public int lastSelectedNonGuestUserId;
    public final CoroutineDispatcher mainDispatcher;
    public int mainUserId;
    public final UserManager manager;
    public int secondaryUserId;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 selectedUserInfo;
    public final UserTracker tracker;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 userInfos;
    public final ReadonlyStateFlow userSwitcherSettings;

    static {
        new Companion(null);
    }

    public UserRepositoryImpl(Context context, UserManager userManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, GlobalSettings globalSettings, UserTracker userTracker, FeatureFlags featureFlags) {
        this.appContext = context;
        this.manager = userManager;
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.globalSettings = globalSettings;
        this.tracker = userTracker;
        SettingsProxyExt.INSTANCE.getClass();
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new UserRepositoryImpl$_userSwitcherSettings$1(null), SettingsProxyExt.observerFlow(globalSettings, 0, "lockscreenSimpleUserSwitcher", "add_users_when_locked", "user_switcher_enabled"));
        Flow flow = new Flow() { // from class: com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$1$2 */
            public final class C35462 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$1$2", m277f = "UserRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C35462.this.emit(null, this);
                    }
                }

                public C35462(FlowCollector flowCollector, UserRepositoryImpl userRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = userRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:19:0x0066 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object obj2;
                    CoroutineSingletons coroutineSingletons;
                    int i;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            obj2 = anonymousClass1.result;
                            coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                FlowCollector flowCollector2 = this.$this_unsafeFlow;
                                anonymousClass1.L$0 = flowCollector2;
                                anonymousClass1.label = 1;
                                int i3 = UserRepositoryImpl.$r8$clinit;
                                UserRepositoryImpl userRepositoryImpl = this.this$0;
                                userRepositoryImpl.getClass();
                                obj2 = BuildersKt.withContext(userRepositoryImpl.backgroundDispatcher, new UserRepositoryImpl$getSettings$2(userRepositoryImpl, null), anonymousClass1);
                                if (obj2 == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                                flowCollector = flowCollector2;
                            } else {
                                if (i != 1) {
                                    if (i != 2) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                    return Unit.INSTANCE;
                                }
                                flowCollector = (FlowCollector) anonymousClass1.L$0;
                                ResultKt.throwOnFailure(obj2);
                            }
                            anonymousClass1.L$0 = null;
                            anonymousClass1.label = 2;
                            if (flowCollector.emit(obj2, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    obj2 = anonymousClass1.result;
                    coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                    if (flowCollector.emit(obj2, anonymousClass1) == coroutineSingletons) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C35462(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new UserRepositoryImpl$_userSwitcherSettings$3(this, null)));
        this._userSwitcherSettings = stateIn;
        this.userSwitcherSettings = stateIn;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._userInfos = MutableStateFlow;
        this.userInfos = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._selectedUserInfo = MutableStateFlow2;
        this.selectedUserInfo = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(MutableStateFlow2);
        this.mainUserId = -10000;
        this.lastSelectedNonGuestUserId = -10000;
        this.isGuestUserAutoCreated = context.getResources().getBoolean(R.bool.config_enableWcgMode);
        this.isGuestUserResetting = false;
        this._isUserSwitchingInProgress = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.isGuestUserCreationScheduled = new AtomicBoolean();
        this.isStatusBarUserChipEnabled = context.getResources().getBoolean(com.android.systemui.R.bool.flag_user_switcher_chip);
        this.secondaryUserId = -10000;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        UserRepositoryImpl$observeSelectedUser$1 userRepositoryImpl$observeSelectedUser$1 = new UserRepositoryImpl$observeSelectedUser$1(this, null);
        conflatedCallbackFlow.getClass();
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(ConflatedCallbackFlow.conflatedCallbackFlow(userRepositoryImpl$observeSelectedUser$1), new UserRepositoryImpl$observeSelectedUser$2(this, null)), coroutineScope);
        Flags flags = Flags.INSTANCE;
    }

    public final UserInfo getSelectedUserInfo() {
        Object value = this._selectedUserInfo.getValue();
        if (value != null) {
            return (UserInfo) value;
        }
        throw new IllegalStateException("Required value was null.".toString());
    }

    public final void refreshUsers() {
        BuildersKt.launch$default(this.applicationScope, null, null, new UserRepositoryImpl$refreshUsers$1(this, null), 3);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getSETTING_SIMPLE_USER_SWITCHER$annotations() {
        }
    }
}
