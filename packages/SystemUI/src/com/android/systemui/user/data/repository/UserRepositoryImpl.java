package com.android.systemui.user.data.repository;

import android.R;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.Prefs;
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
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
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
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getSETTING_SIMPLE_USER_SWITCHER$annotations() {
        }
    }

    /* renamed from: com.android.systemui.user.data.repository.UserRepositoryImpl$isUserUnlocked$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserHandle $userHandle;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(UserHandle userHandle, Continuation continuation) {
            super(2, continuation);
            this.$userHandle = userHandle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = UserRepositoryImpl.this.new AnonymousClass2(this.$userHandle, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x004c, code lost:
        
            if (r1.emit(r7, r6) == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            FlowCollector flowCollector;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                UserRepositoryImpl userRepositoryImpl = UserRepositoryImpl.this;
                UserHandle userHandle = this.$userHandle;
                this.L$0 = flowCollector;
                this.label = 1;
                int i2 = UserRepositoryImpl.$r8$clinit;
                userRepositoryImpl.getClass();
                obj = BuildersKt.withContext(userRepositoryImpl.backgroundDispatcher, new UserRepositoryImpl$getUnlockedState$2(userHandle, userRepositoryImpl, null), this);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            this.L$0 = null;
            this.label = 2;
        }
    }

    /* renamed from: com.android.systemui.user.data.repository.UserRepositoryImpl$logOutSecondaryUser$2, reason: invalid class name and case insensitive filesystem */
    final class C11242 extends SuspendLambda implements Function2 {
        int label;

        public C11242(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserRepositoryImpl.this.new C11242(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11242) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return new Integer(UserRepositoryImpl.this.devicePolicyManager.logoutUser());
        }
    }

    /* renamed from: com.android.systemui.user.data.repository.UserRepositoryImpl$logOutToSystemUser$2, reason: invalid class name and case insensitive filesystem */
    final class C11252 extends SuspendLambda implements Function2 {
        int label;

        public C11252(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserRepositoryImpl.this.new C11252(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11252) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            UserRepositoryImpl.this.statusBarService.reboot(false);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        Object L$0;
        int label;

        /* renamed from: com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1$1, reason: invalid class name and collision with other inner class name */
        final class C06101 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ UserRepositoryImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C06101(UserRepositoryImpl userRepositoryImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = userRepositoryImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C06101(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C06101) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return this.this$0.manager.getAliveUsers();
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserRepositoryImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x0095, code lost:
        
            if (r7 == r0) goto L22;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            MutableStateFlow mutableStateFlow;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                UserRepositoryImpl userRepositoryImpl = UserRepositoryImpl.this;
                mutableStateFlow = userRepositoryImpl._userInfos;
                C06101 c06101 = new C06101(userRepositoryImpl, null);
                this.L$0 = mutableStateFlow;
                this.label = 1;
                obj = BuildersKt.withContext(userRepositoryImpl.backgroundDispatcher, c06101, this);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                UserHandle userHandle = (UserHandle) obj;
                if (userHandle != null) {
                    UserRepositoryImpl.this.mainUserId = userHandle.getIdentifier();
                }
                return Unit.INSTANCE;
            }
            mutableStateFlow = (MutableStateFlow) this.L$0;
            ResultKt.throwOnFailure(obj);
            mutableStateFlow.setValue(CollectionsKt___CollectionsKt.sortedWith(CollectionsKt___CollectionsKt.sortedWith((Iterable) obj, new Comparator() { // from class: com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1$invokeSuspend$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((UserInfo) obj2).creationTime), Long.valueOf(((UserInfo) obj3).creationTime));
                }
            }), new Comparator() { // from class: com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1$invokeSuspend$$inlined$sortedBy$2
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((UserInfo) obj2).isGuest()), Boolean.valueOf(((UserInfo) obj3).isGuest()));
                }
            }));
            List list = (List) UserRepositoryImpl.this._userInfos.getValue();
            if (list != null) {
                UserRepositoryImpl userRepositoryImpl2 = UserRepositoryImpl.this;
                if (list.size() > (!UserManager.supportsMultipleUsers() ? 1 : 0)) {
                    Prefs.putBoolean(userRepositoryImpl2.appContext, "HasSeenMultiUser", true);
                    Unit unit = Unit.INSTANCE;
                    Log.d("UserRepository", "refreshUsers: put HasSeenMultiUser as true");
                }
            }
            UserRepositoryImpl userRepositoryImpl3 = UserRepositoryImpl.this;
            if (userRepositoryImpl3.mainUserId == -10000) {
                UserRepositoryImpl$refreshUsers$1$mainUser$1 userRepositoryImpl$refreshUsers$1$mainUser$1 = new UserRepositoryImpl$refreshUsers$1$mainUser$1(userRepositoryImpl3, null);
                this.L$0 = null;
                this.label = 2;
                obj = BuildersKt.withContext(userRepositoryImpl3.backgroundDispatcher, userRepositoryImpl$refreshUsers$1$mainUser$1, this);
            }
            return Unit.INSTANCE;
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

                /* JADX WARN: Code restructure failed: missing block: B:21:0x0064, code lost:
                
                    if (r6.emit(r8, r0) == r1) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object objWithContext = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(objWithContext);
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector2;
                        anonymousClass1.label = 1;
                        int i3 = UserRepositoryImpl.$r8$clinit;
                        UserRepositoryImpl userRepositoryImpl = this.this$0;
                        userRepositoryImpl.getClass();
                        objWithContext = BuildersKt.withContext(userRepositoryImpl.backgroundDispatcher, new UserRepositoryImpl$getSettings$2(userRepositoryImpl, null), anonymousClass1);
                        if (objWithContext != coroutineSingletons) {
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(objWithContext);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(objWithContext);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flow, coroutineScope, startedEagerly, BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new UserRepositoryImpl$_userSwitcherSettings$3(this, null)));
        this._userSwitcherSettings = readonlyStateFlowStateIn;
        this.userSwitcherSettings = readonlyStateFlowStateIn;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._userInfos = stateFlowImplMutableStateFlow;
        this.userInfos = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(stateFlowImplMutableStateFlow);
        this.mainUserId = -10000;
        this.lastSelectedNonGuestUserId = -10000;
        this.isGuestUserAutoCreated = context.getResources().getBoolean(R.bool.config_letterboxIsSplitScreenAspectRatioForUnresizableAppsEnabled);
        this.isGuestUserResetting = false;
        this.isGuestUserCreationScheduled = new AtomicBoolean();
        this.isStatusBarUserChipEnabled = context.getResources().getBoolean(com.android.systemui.R.bool.flag_user_switcher_chip);
        this.secondaryUserId = -10000;
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = SelectionStatus.SELECTION_COMPLETE;
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowConflatedKt.conflatedCallbackFlow(new UserRepositoryImpl$selectedUser$1$1(this, ref$ObjectRef, null)), new UserRepositoryImpl$selectedUser$1$2(this, null)), coroutineScope, startedEagerly, new SelectedUserModel(((UserTrackerImpl) userTracker).getUserInfo(), (SelectionStatus) ref$ObjectRef.element));
        this.selectedUser = readonlyStateFlowStateIn2;
        this.selectedUserInfo = new UserRepositoryImpl$special$$inlined$map$2(readonlyStateFlowStateIn2);
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new UserRepositoryImpl$isSecondaryUserLogoutSupported$2(null), new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED"), null, new UserRepositoryImpl$$ExternalSyntheticLambda0(), 14)));
        this.isSecondaryUserLogoutSupported = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$3

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(this.this$0.devicePolicyManager.isLogoutEnabled());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher2);
        Flow flowFlatMapLatestConflated = LatestConflatedKt.flatMapLatestConflated(readonlyStateFlowStateIn2, new UserRepositoryImpl$isSecondaryUserLogoutEnabled$1(this, null));
        Boolean bool = Boolean.FALSE;
        this.isSecondaryUserLogoutEnabled = FlowKt.stateIn(flowFlatMapLatestConflated, coroutineScope, startedEagerly, bool);
        this.isLogoutToSystemUserEnabled = FlowKt.stateIn(LatestConflatedKt.flatMapLatestConflated(readonlyStateFlowStateIn2, new UserRepositoryImpl$isLogoutToSystemUserEnabled$1(this, null)), coroutineScope, startedEagerly, bool);
    }

    public final UserInfo getSelectedUserInfo() {
        return ((SelectedUserModel) this.selectedUser.$$delegate_0.getValue()).userInfo;
    }

    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 isUserUnlocked(final UserHandle userHandle) {
        final Flow flowBroadcastFlow$default = BroadcastDispatcher.broadcastFlow$default(this.broadcastDispatcher, new IntentFilter("android.intent.action.USER_UNLOCKED"), null, 14);
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass2(userHandle, null), new Flow() { // from class: com.android.systemui.user.data.repository.UserRepositoryImpl$isUserUnlocked$$inlined$map$1

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

                /* JADX WARN: Code restructure failed: missing block: B:21:0x0068, code lost:
                
                    if (r7.emit(r9, r0) == r1) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        UserHandle userHandle = this.$userHandle$inlined;
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector2;
                        anonymousClass1.label = 1;
                        int i3 = UserRepositoryImpl.$r8$clinit;
                        UserRepositoryImpl userRepositoryImpl = this.this$0;
                        userRepositoryImpl.getClass();
                        Object objWithContext = BuildersKt.withContext(userRepositoryImpl.backgroundDispatcher, new UserRepositoryImpl$getUnlockedState$2(userHandle, userRepositoryImpl, null), anonymousClass1);
                        if (objWithContext != coroutineSingletons) {
                            obj2 = objWithContext;
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowBroadcastFlow$default.collect(new AnonymousClass2(flowCollector, this, userHandle), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
    }

    public final Object logOutSecondaryUser(Continuation continuation) throws Throwable {
        if (!((Boolean) this.isSecondaryUserLogoutEnabled.$$delegate_0.getValue()).booleanValue()) {
            return Unit.INSTANCE;
        }
        Object objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new C11242(null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    public final Object logOutToSystemUser(Continuation continuation) throws Throwable {
        if (!((Boolean) this.isLogoutToSystemUserEnabled.$$delegate_0.getValue()).booleanValue()) {
            return Unit.INSTANCE;
        }
        Object objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new C11252(null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    public final void refreshUsers() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new AnonymousClass1(null), 7);
    }
}
