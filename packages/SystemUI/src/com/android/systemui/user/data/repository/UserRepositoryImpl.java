package com.android.systemui.user.data.repository;

import android.R;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.os.UserHandle;
import android.os.UserManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.user.data.model.SelectedUserModel;
import com.android.systemui.user.data.model.SelectionStatus;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;
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
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UserRepositoryImpl implements UserRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _userInfos;
    public final ReadonlyStateFlow _userSwitcherSettings;
    public final Context appContext;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final BroadcastDispatcher broadcastDispatcher;
    public final DevicePolicyManager devicePolicyManager;
    public final GlobalSettings globalSettings;
    public final boolean isGuestUserAutoCreated;
    public final AtomicBoolean isGuestUserCreationScheduled;
    public boolean isGuestUserResetting;
    public final ReadonlyStateFlow isLogoutToSystemUserEnabled;
    public final ReadonlyStateFlow isSecondaryUserLogoutEnabled;
    public final Flow isSecondaryUserLogoutSupported;
    public final boolean isStatusBarUserChipEnabled;
    public int lastSelectedNonGuestUserId;
    public final CoroutineDispatcher mainDispatcher;
    public int mainUserId;
    public final UserManager manager;
    public final Resources resources;
    public int secondaryUserId;
    public final ReadonlyStateFlow selectedUser;
    public final UserRepositoryImpl$special$$inlined$map$2 selectedUserInfo;
    public final IStatusBarService statusBarService;
    public final UserTracker tracker;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 userInfos;
    public final ReadonlyStateFlow userSwitcherSettings;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getSETTING_SIMPLE_USER_SWITCHER$annotations() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v7, types: [T, com.android.systemui.user.data.model.SelectionStatus] */
    public UserRepositoryImpl(Context context, Resources resources, UserManager userManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, GlobalSettings globalSettings, UserTracker userTracker, DevicePolicyManager devicePolicyManager, BroadcastDispatcher broadcastDispatcher, IStatusBarService iStatusBarService) {
        this.appContext = context;
        this.resources = resources;
        this.manager = userManager;
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.globalSettings = globalSettings;
        this.tracker = userTracker;
        this.devicePolicyManager = devicePolicyManager;
        this.broadcastDispatcher = broadcastDispatcher;
        this.statusBarService = iStatusBarService;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new UserRepositoryImpl$_userSwitcherSettings$1(null), SettingsProxyExt.INSTANCE.observerFlow(globalSettings, "lockscreenSimpleUserSwitcher", "add_users_when_locked", SettingsHelper.INDEX_USER_SWITCHER_ENABLED));
        Flow flow = new Flow() { // from class: com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserRepositoryImpl this$0;

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
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, UserRepositoryImpl userRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = userRepositoryImpl;
                }

                /* JADX WARN: Code restructure failed: missing block: B:18:0x0064, code lost:
                
                    if (r6.emit(r8, r0) != r1) goto L23;
                 */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 0
                        r4 = 2
                        r5 = 1
                        if (r2 == 0) goto L3b
                        if (r2 == r5) goto L33
                        if (r2 != r4) goto L2b
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L67
                    L2b:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L33:
                        java.lang.Object r6 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L5c
                    L3b:
                        kotlin.ResultKt.throwOnFailure(r8)
                        kotlin.Unit r7 = (kotlin.Unit) r7
                        kotlinx.coroutines.flow.FlowCollector r7 = r6.$this_unsafeFlow
                        r0.L$0 = r7
                        r0.label = r5
                        int r8 = com.android.systemui.user.data.repository.UserRepositoryImpl.$r8$clinit
                        com.android.systemui.user.data.repository.UserRepositoryImpl r6 = r6.this$0
                        r6.getClass()
                        com.android.systemui.user.data.repository.UserRepositoryImpl$getSettings$2 r8 = new com.android.systemui.user.data.repository.UserRepositoryImpl$getSettings$2
                        r8.<init>(r6, r3)
                        kotlinx.coroutines.CoroutineDispatcher r6 = r6.backgroundDispatcher
                        java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r6, r8, r0)
                        if (r8 != r1) goto L5b
                        goto L66
                    L5b:
                        r6 = r7
                    L5c:
                        r0.L$0 = r3
                        r0.label = r4
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L67
                    L66:
                        return r1
                    L67:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope, startedEagerly, BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new UserRepositoryImpl$_userSwitcherSettings$3(this, null)));
        this._userSwitcherSettings = stateIn;
        this.userSwitcherSettings = stateIn;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._userInfos = MutableStateFlow;
        this.userInfos = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(MutableStateFlow);
        this.mainUserId = -10000;
        this.lastSelectedNonGuestUserId = -10000;
        this.isGuestUserAutoCreated = context.getResources().getBoolean(R.bool.config_letterboxIsSplitScreenAspectRatioForUnresizableAppsEnabled);
        this.isGuestUserResetting = false;
        this.isGuestUserCreationScheduled = new AtomicBoolean();
        this.isStatusBarUserChipEnabled = context.getResources().getBoolean(com.android.systemui.R.bool.flag_user_switcher_chip);
        this.secondaryUserId = -10000;
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = SelectionStatus.SELECTION_COMPLETE;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowConflatedKt.conflatedCallbackFlow(new UserRepositoryImpl$selectedUser$1$1(this, ref$ObjectRef, null)), new UserRepositoryImpl$selectedUser$1$2(this, null)), coroutineScope, startedEagerly, new SelectedUserModel(((UserTrackerImpl) userTracker).getUserInfo(), (SelectionStatus) ref$ObjectRef.element));
        this.selectedUser = stateIn2;
        this.selectedUserInfo = new UserRepositoryImpl$special$$inlined$map$2(stateIn2);
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new UserRepositoryImpl$isSecondaryUserLogoutSupported$2(null), new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED"), null, new UserRepositoryImpl$$ExternalSyntheticLambda0(), 14)));
        this.isSecondaryUserLogoutSupported = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserRepositoryImpl this$0;

                /* renamed from: com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, UserRepositoryImpl userRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = userRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$3$2$1 r0 = (com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$3$2$1 r0 = new com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4b
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        com.android.systemui.user.data.repository.UserRepositoryImpl r5 = r4.this$0
                        android.app.admin.DevicePolicyManager r5 = r5.devicePolicyManager
                        boolean r5 = r5.isLogoutEnabled()
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4b
                        return r1
                    L4b:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher2);
        Flow flatMapLatestConflated = LatestConflatedKt.flatMapLatestConflated(stateIn2, new UserRepositoryImpl$isSecondaryUserLogoutEnabled$1(this, null));
        Boolean bool = Boolean.FALSE;
        this.isSecondaryUserLogoutEnabled = FlowKt.stateIn(flatMapLatestConflated, coroutineScope, startedEagerly, bool);
        this.isLogoutToSystemUserEnabled = FlowKt.stateIn(LatestConflatedKt.flatMapLatestConflated(stateIn2, new UserRepositoryImpl$isLogoutToSystemUserEnabled$1(this, null)), coroutineScope, startedEagerly, bool);
    }

    public final UserInfo getSelectedUserInfo() {
        return ((SelectedUserModel) this.selectedUser.$$delegate_0.getValue()).userInfo;
    }

    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 isUserUnlocked(final UserHandle userHandle) {
        final Flow broadcastFlow$default = BroadcastDispatcher.broadcastFlow$default(this.broadcastDispatcher, new IntentFilter("android.intent.action.USER_UNLOCKED"), null, 14);
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new UserRepositoryImpl$isUserUnlocked$2(this, userHandle, null), new Flow() { // from class: com.android.systemui.user.data.repository.UserRepositoryImpl$isUserUnlocked$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.user.data.repository.UserRepositoryImpl$isUserUnlocked$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserHandle $userHandle$inlined;
                public final /* synthetic */ UserRepositoryImpl this$0;

                /* renamed from: com.android.systemui.user.data.repository.UserRepositoryImpl$isUserUnlocked$$inlined$map$1$2$1, reason: invalid class name */
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
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, UserRepositoryImpl userRepositoryImpl, UserHandle userHandle) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = userRepositoryImpl;
                    this.$userHandle$inlined = userHandle;
                }

                /* JADX WARN: Code restructure failed: missing block: B:18:0x0068, code lost:
                
                    if (r7.emit(r9, r0) != r1) goto L23;
                 */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.user.data.repository.UserRepositoryImpl$isUserUnlocked$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.user.data.repository.UserRepositoryImpl$isUserUnlocked$$inlined$map$1$2$1 r0 = (com.android.systemui.user.data.repository.UserRepositoryImpl$isUserUnlocked$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.user.data.repository.UserRepositoryImpl$isUserUnlocked$$inlined$map$1$2$1 r0 = new com.android.systemui.user.data.repository.UserRepositoryImpl$isUserUnlocked$$inlined$map$1$2$1
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 0
                        r4 = 2
                        r5 = 1
                        if (r2 == 0) goto L3b
                        if (r2 == r5) goto L33
                        if (r2 != r4) goto L2b
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L6b
                    L2b:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L33:
                        java.lang.Object r7 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L60
                    L3b:
                        kotlin.ResultKt.throwOnFailure(r9)
                        kotlin.Unit r8 = (kotlin.Unit) r8
                        android.os.UserHandle r8 = r7.$userHandle$inlined
                        kotlinx.coroutines.flow.FlowCollector r9 = r7.$this_unsafeFlow
                        r0.L$0 = r9
                        r0.label = r5
                        int r2 = com.android.systemui.user.data.repository.UserRepositoryImpl.$r8$clinit
                        com.android.systemui.user.data.repository.UserRepositoryImpl r7 = r7.this$0
                        r7.getClass()
                        com.android.systemui.user.data.repository.UserRepositoryImpl$getUnlockedState$2 r2 = new com.android.systemui.user.data.repository.UserRepositoryImpl$getUnlockedState$2
                        r2.<init>(r8, r7, r3)
                        kotlinx.coroutines.CoroutineDispatcher r7 = r7.backgroundDispatcher
                        java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r2, r0)
                        if (r7 != r1) goto L5d
                        goto L6a
                    L5d:
                        r6 = r9
                        r9 = r7
                        r7 = r6
                    L60:
                        r0.L$0 = r3
                        r0.label = r4
                        java.lang.Object r7 = r7.emit(r9, r0)
                        if (r7 != r1) goto L6b
                    L6a:
                        return r1
                    L6b:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.data.repository.UserRepositoryImpl$isUserUnlocked$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this, userHandle), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
    }

    public final Object logOutSecondaryUser(Continuation continuation) {
        if (!((Boolean) this.isSecondaryUserLogoutEnabled.$$delegate_0.getValue()).booleanValue()) {
            return Unit.INSTANCE;
        }
        Object withContext = BuildersKt.withContext(this.backgroundDispatcher, new UserRepositoryImpl$logOutSecondaryUser$2(this, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    public final Object logOutToSystemUser(Continuation continuation) {
        if (!((Boolean) this.isLogoutToSystemUserEnabled.$$delegate_0.getValue()).booleanValue()) {
            return Unit.INSTANCE;
        }
        Object withContext = BuildersKt.withContext(this.backgroundDispatcher, new UserRepositoryImpl$logOutToSystemUser$2(this, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    public final void refreshUsers() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new UserRepositoryImpl$refreshUsers$1(this, null), 7);
    }
}
