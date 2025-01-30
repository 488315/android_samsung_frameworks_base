package com.android.systemui.user.domain.interactor;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.UserIcons;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.SystemUISecondaryUserService;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.qs.user.UserSwitchDialogController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.telephony.domain.interactor.TelephonyInteractor;
import com.android.systemui.user.CreateUserActivity;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.source.UserRecord;
import com.android.systemui.user.domain.model.ShowDialogRequestModel;
import com.android.systemui.user.legacyhelper.data.LegacyUserDataHelper;
import com.android.systemui.user.ui.dialog.DialogShowerImpl;
import com.android.systemui.user.shared.model.UserActionModel;
import com.android.systemui.user.shared.model.UserModel;
import com.android.systemui.user.utils.MultiUserActionsEvent;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.kotlin.WithPrev;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UserInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _dialogDismissRequests;
    public final StateFlowImpl _dialogShowRequests;
    public final ActivityManager activityManager;
    public final ActivityStarter activityStarter;
    public final Context applicationContext;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final MutexImpl callbackMutex;
    public final Set callbacks;
    public final ReadonlyStateFlow dialogDismissRequests;
    public final ReadonlyStateFlow dialogShowRequests;
    public final FeatureFlags featureFlags;
    public final GuestUserInteractor guestUserInteractor;
    public final HeadlessSystemUserMode headlessSystemUserMode;
    public final boolean isGuestUserAutoCreated;
    public final boolean isGuestUserResetting;
    public final boolean isStatusBarUserChipEnabled;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback;
    public final UserManager manager;
    public final RefreshUsersScheduler refreshUsersScheduler;
    public final UserRepository repository;
    public final ReadonlyStateFlow selectedUserRecord;
    public final SettingsHelper settingsHelper;
    public final UiEventLogger uiEventLogger;
    public final UserInteractor$special$$inlined$map$1 userInfos;
    public final ReadonlyStateFlow userRecords;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor$1", m277f = "UserInteractor.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.user.domain.interactor.UserInteractor$1 */
    public static final class C35521 extends SuspendLambda implements Function2 {
        int label;

        public C35521(Continuation<? super C35521> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserInteractor.this.new C35521(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C35521) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            UserInteractor.this.refreshUsersScheduler.refreshIfNotPaused();
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor$4", m277f = "UserInteractor.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.user.domain.interactor.UserInteractor$4 */
    public static final class C35544 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public C35544(Continuation<? super C35544> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            C35544 c35544 = new C35544((Continuation) obj3);
            c35544.L$0 = (Intent) obj;
            c35544.L$1 = (WithPrev) obj2;
            return c35544.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return new Pair((Intent) this.L$0, ((WithPrev) this.L$1).previousValue);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor$5", m277f = "UserInteractor.kt", m278l = {340}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.user.domain.interactor.UserInteractor$5 */
    public static final class C35555 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public C35555(Continuation<? super C35555> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C35555 c35555 = UserInteractor.this.new C35555(continuation);
            c35555.L$0 = obj;
            return c35555;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C35555) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Pair pair = (Pair) this.L$0;
                Intent intent = (Intent) pair.component1();
                UserInfo userInfo = (UserInfo) pair.component2();
                UserInteractor userInteractor = UserInteractor.this;
                this.label = 1;
                if (UserInteractor.access$onBroadcastReceived(userInteractor, intent, userInfo, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface UserCallback {
        boolean isEvictable();

        void onUserStateChanged();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[UserActionModel.values().length];
            try {
                iArr[UserActionModel.ENTER_GUEST_MODE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[UserActionModel.ADD_USER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[UserActionModel.ADD_SUPERVISED_USER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[UserActionModel.NAVIGATE_TO_USER_MANAGEMENT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public UserInteractor(Context context, UserRepository userRepository, ActivityStarter activityStarter, KeyguardInteractor keyguardInteractor, FeatureFlags featureFlags, UserManager userManager, HeadlessSystemUserMode headlessSystemUserMode, CoroutineScope coroutineScope, TelephonyInteractor telephonyInteractor, BroadcastDispatcher broadcastDispatcher, KeyguardUpdateMonitor keyguardUpdateMonitor, CoroutineDispatcher coroutineDispatcher, ActivityManager activityManager, RefreshUsersScheduler refreshUsersScheduler, GuestUserInteractor guestUserInteractor, UiEventLogger uiEventLogger, SettingsHelper settingsHelper) {
        this.applicationContext = context;
        this.repository = userRepository;
        this.activityStarter = activityStarter;
        this.keyguardInteractor = keyguardInteractor;
        this.featureFlags = featureFlags;
        this.manager = userManager;
        this.headlessSystemUserMode = headlessSystemUserMode;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.activityManager = activityManager;
        this.refreshUsersScheduler = refreshUsersScheduler;
        this.guestUserInteractor = guestUserInteractor;
        this.uiEventLogger = uiEventLogger;
        this.settingsHelper = settingsHelper;
        Symbol symbol = MutexKt.UNLOCK_FAIL;
        this.callbackMutex = new MutexImpl(false);
        this.callbacks = new LinkedHashSet();
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) userRepository;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = userRepositoryImpl.userInfos;
        ?? r6 = new Flow() { // from class: com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$1$2 */
            public final class C35492 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$1$2", m277f = "UserInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        return C35492.this.emit(null, this);
                    }
                }

                public C35492(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                ArrayList arrayList = new ArrayList();
                                for (Object obj3 : (List) obj) {
                                    if (((UserInfo) obj3).isFull()) {
                                        arrayList.add(obj3);
                                    }
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(arrayList, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C35492(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.userInfos = r6;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.combine(r6, userRepositoryImpl.selectedUserInfo, getActions(), userRepositoryImpl.userSwitcherSettings, new UserInteractor$userRecords$1(this, null)), new UserInteractor$userRecords$2(this, null));
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.userRecords = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, startedEagerly, new ArrayList());
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$12 = userRepositoryImpl.selectedUserInfo;
        this.selectedUserRecord = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$2$2 */
            public final class C35502 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserInteractor this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$2$2", m277f = "UserInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType, IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        return C35502.this.emit(null, this);
                    }
                }

                public C35502(FlowCollector flowCollector, UserInteractor userInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = userInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:19:0x005c A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
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
                                UserInfo userInfo = (UserInfo) obj;
                                int i3 = userInfo.id;
                                FlowCollector flowCollector2 = this.$this_unsafeFlow;
                                anonymousClass1.L$0 = flowCollector2;
                                anonymousClass1.label = 1;
                                obj2 = UserInteractor.access$toRecord(this.this$0, userInfo, i3, anonymousClass1);
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
                Object collect = Flow.this.collect(new C35502(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, null);
        this.isGuestUserAutoCreated = guestUserInteractor.isGuestUserAutoCreated;
        this.isGuestUserResetting = guestUserInteractor.isGuestUserResetting;
        this.isStatusBarUserChipEnabled = userRepositoryImpl.isStatusBarUserChipEnabled;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._dialogShowRequests = MutableStateFlow;
        this.dialogShowRequests = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._dialogDismissRequests = MutableStateFlow2;
        this.dialogDismissRequests = FlowKt.asStateFlow(MutableStateFlow2);
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.user.domain.interactor.UserInteractor$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardGoingAway() {
                UserInteractor.this.dismissDialog();
            }
        };
        this.keyguardUpdateMonitorCallback = keyguardUpdateMonitorCallback;
        refreshUsersScheduler.refreshIfNotPaused();
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(telephonyInteractor.callState), new C35521(null)), coroutineScope);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_INFO_CHANGED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_STOPPED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, intentFilter, UserHandle.SYSTEM, new Function2() { // from class: com.android.systemui.user.domain.interactor.UserInteractor.3
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return (Intent) obj;
            }
        }, 12), com.android.systemui.util.kotlin.FlowKt.pairwise(userRepositoryImpl.selectedUserInfo, null), new C35544(null)), new C35555(null)), coroutineScope);
        keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$onBroadcastReceived(UserInteractor userInteractor, Intent intent, UserInfo userInfo, Continuation continuation) {
        UserInteractor$onBroadcastReceived$1 userInteractor$onBroadcastReceived$1;
        int i;
        boolean z;
        userInteractor.getClass();
        if (continuation instanceof UserInteractor$onBroadcastReceived$1) {
            userInteractor$onBroadcastReceived$1 = (UserInteractor$onBroadcastReceived$1) continuation;
            int i2 = userInteractor$onBroadcastReceived$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                userInteractor$onBroadcastReceived$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = userInteractor$onBroadcastReceived$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = userInteractor$onBroadcastReceived$1.label;
                z = true;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    String action = intent.getAction();
                    if (action != null) {
                        int hashCode = action.hashCode();
                        if (hashCode != -201513518) {
                            boolean z2 = false;
                            if (hashCode != 833559602) {
                                if (hashCode == 959232034 && action.equals("android.intent.action.USER_SWITCHED")) {
                                    userInteractor.dismissDialog();
                                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                                    if (userInfo != null && userInfo.id == intExtra) {
                                        z2 = true;
                                    }
                                    if (!z2) {
                                        BuildersKt.launch$default(userInteractor.applicationScope, null, null, new UserInteractor$notifyCallbacks$1(userInteractor, null), 3);
                                        Context context = userInteractor.applicationContext;
                                        Intent intent2 = new Intent(context, (Class<?>) SystemUISecondaryUserService.class);
                                        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) userInteractor.repository;
                                        int i3 = userRepositoryImpl.secondaryUserId;
                                        if (i3 != -10000) {
                                            context.stopServiceAsUser(intent2, UserHandle.of(i3));
                                            userRepositoryImpl.secondaryUserId = -10000;
                                        }
                                        if (intExtra != 0) {
                                            context.startServiceAsUser(intent2, UserHandle.of(intExtra));
                                            userRepositoryImpl.secondaryUserId = intExtra;
                                        }
                                    }
                                    GuestUserInteractor guestUserInteractor = userInteractor.guestUserInteractor;
                                    if (guestUserInteractor.isGuestUserAutoCreated) {
                                        userInteractor$onBroadcastReceived$1.L$0 = userInteractor;
                                        userInteractor$onBroadcastReceived$1.label = 1;
                                        if (guestUserInteractor.guaranteePresent(userInteractor$onBroadcastReceived$1) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    }
                                }
                            } else if (action.equals("android.intent.action.USER_UNLOCKED") && intent.getIntExtra("android.intent.extra.user_handle", -10000) != 0) {
                                z = false;
                            }
                        } else {
                            action.equals("android.intent.action.USER_INFO_CHANGED");
                        }
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    userInteractor = (UserInteractor) userInteractor$onBroadcastReceived$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                if (z) {
                    RefreshUsersScheduler refreshUsersScheduler = userInteractor.refreshUsersScheduler;
                    refreshUsersScheduler.getClass();
                    BuildersKt.launch$default(refreshUsersScheduler.applicationScope, refreshUsersScheduler.mainDispatcher, null, new RefreshUsersScheduler$unpauseAndRefresh$1(refreshUsersScheduler, null), 2);
                }
                return Unit.INSTANCE;
            }
        }
        userInteractor$onBroadcastReceived$1 = new UserInteractor$onBroadcastReceived$1(userInteractor, continuation);
        Object obj2 = userInteractor$onBroadcastReceived$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = userInteractor$onBroadcastReceived$1.label;
        z = true;
        if (i != 0) {
        }
        if (z) {
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00b3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$toRecord(UserInteractor userInteractor, UserInfo userInfo, int i, Continuation continuation) {
        UserInteractor$toRecord$1 userInteractor$toRecord$1;
        int i2;
        UserInfo userInfo2;
        Context context;
        UserManager userManager;
        int i3;
        Bitmap bitmap;
        Bitmap userIcon;
        userInteractor.getClass();
        if (continuation instanceof UserInteractor$toRecord$1) {
            userInteractor$toRecord$1 = (UserInteractor$toRecord$1) continuation;
            int i4 = userInteractor$toRecord$1.label;
            if ((i4 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                userInteractor$toRecord$1.label = i4 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = userInteractor$toRecord$1.result;
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                i2 = userInteractor$toRecord$1.label;
                if (i2 != 0) {
                    ResultKt.throwOnFailure(obj);
                    LegacyUserDataHelper legacyUserDataHelper = LegacyUserDataHelper.INSTANCE;
                    int i5 = userInfo.id == i ? 1 : 0;
                    userInteractor$toRecord$1.L$0 = userInfo;
                    Context context2 = userInteractor.applicationContext;
                    userInteractor$toRecord$1.L$1 = context2;
                    UserManager userManager2 = userInteractor.manager;
                    userInteractor$toRecord$1.L$2 = userManager2;
                    userInteractor$toRecord$1.I$0 = i5;
                    userInteractor$toRecord$1.label = 1;
                    Object canSwitchUsers = userInteractor.canSwitchUsers(i, userInteractor$toRecord$1, false);
                    if (canSwitchUsers != obj2) {
                        userInfo2 = userInfo;
                        context = context2;
                        userManager = userManager2;
                        int i6 = i5;
                        obj = canSwitchUsers;
                        i3 = i6;
                    }
                    return obj2;
                }
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i3 = userInteractor$toRecord$1.I$0;
                userManager = (UserManager) userInteractor$toRecord$1.L$2;
                context = (Context) userInteractor$toRecord$1.L$1;
                UserInfo userInfo3 = (UserInfo) userInteractor$toRecord$1.L$0;
                ResultKt.throwOnFailure(obj);
                userInfo2 = userInfo3;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                boolean z = i3 == 0;
                LegacyUserDataHelper legacyUserDataHelper2 = LegacyUserDataHelper.INSTANCE;
                boolean isGuest = userInfo2.isGuest();
                LegacyUserDataHelper.INSTANCE.getClass();
                if (userInfo2.isGuest() || (userIcon = userManager.getUserIcon(userInfo2.id)) == null) {
                    bitmap = null;
                } else {
                    int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.max_avatar_size);
                    bitmap = Bitmap.createScaledBitmap(userIcon, dimensionPixelSize, dimensionPixelSize, true);
                }
                obj2 = new UserRecord(userInfo2, bitmap, isGuest, z, false, false, !booleanValue || (z && !isGuest), false, null, false, 944, null);
                return obj2;
            }
        }
        userInteractor$toRecord$1 = new UserInteractor$toRecord$1(userInteractor, continuation);
        Object obj3 = userInteractor$toRecord$1.result;
        Object obj22 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i2 = userInteractor$toRecord$1.label;
        if (i2 != 0) {
        }
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        if (i3 == 0) {
        }
        LegacyUserDataHelper legacyUserDataHelper22 = LegacyUserDataHelper.INSTANCE;
        boolean isGuest2 = userInfo2.isGuest();
        LegacyUserDataHelper.INSTANCE.getClass();
        if (userInfo2.isGuest()) {
            int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.max_avatar_size);
            bitmap = Bitmap.createScaledBitmap(userIcon, dimensionPixelSize2, dimensionPixelSize2, true);
            obj22 = new UserRecord(userInfo2, bitmap, isGuest2, z, false, false, !booleanValue2 || (z && !isGuest2), false, null, false, 944, null);
            return obj22;
        }
        bitmap = null;
        obj22 = new UserRecord(userInfo2, bitmap, isGuest2, z, false, false, !booleanValue2 || (z && !isGuest2), false, null, false, 944, null);
        return obj22;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002c  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:43:0x015a -> B:11:0x015c). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$toUserModels(UserInteractor userInteractor, List list, int i, boolean z, Continuation continuation) {
        UserInteractor$toUserModels$1 userInteractor$toUserModels$1;
        int i2;
        List list2;
        boolean z2;
        Object canSwitchUsers;
        UserInteractor userInteractor2;
        boolean z3;
        boolean z4;
        boolean z5;
        Collection arrayList;
        int i3;
        boolean z6;
        Iterator it;
        int i4;
        boolean z7;
        Object userModel;
        UserInteractor userInteractor3 = userInteractor;
        int i5 = i;
        userInteractor.getClass();
        if (continuation instanceof UserInteractor$toUserModels$1) {
            userInteractor$toUserModels$1 = (UserInteractor$toUserModels$1) continuation;
            int i6 = userInteractor$toUserModels$1.label;
            if ((i6 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                userInteractor$toUserModels$1.label = i6 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = userInteractor$toUserModels$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i2 = userInteractor$toUserModels$1.label;
                if (i2 != 0) {
                    ResultKt.throwOnFailure(obj);
                    userInteractor$toUserModels$1.L$0 = userInteractor3;
                    list2 = list;
                    userInteractor$toUserModels$1.L$1 = list2;
                    userInteractor$toUserModels$1.I$0 = i5;
                    z2 = z;
                    userInteractor$toUserModels$1.Z$0 = z2;
                    userInteractor$toUserModels$1.label = 1;
                    canSwitchUsers = userInteractor3.canSwitchUsers(i5, userInteractor$toUserModels$1, false);
                    if (canSwitchUsers == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else if (i2 == 1) {
                    boolean z8 = userInteractor$toUserModels$1.Z$0;
                    i5 = userInteractor$toUserModels$1.I$0;
                    List list3 = (List) userInteractor$toUserModels$1.L$1;
                    UserInteractor userInteractor4 = (UserInteractor) userInteractor$toUserModels$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    z2 = z8;
                    userInteractor3 = userInteractor4;
                    canSwitchUsers = obj;
                    list2 = list3;
                } else {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    z3 = userInteractor$toUserModels$1.Z$1;
                    z6 = userInteractor$toUserModels$1.Z$0;
                    i3 = userInteractor$toUserModels$1.I$0;
                    it = (Iterator) userInteractor$toUserModels$1.L$2;
                    arrayList = (Collection) userInteractor$toUserModels$1.L$1;
                    UserInteractor userInteractor5 = (UserInteractor) userInteractor$toUserModels$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    userInteractor2 = userInteractor5;
                    z4 = true;
                    UserModel userModel2 = (UserModel) obj;
                    if (userModel2 != null) {
                        arrayList.add(userModel2);
                    }
                    i4 = 2;
                    z5 = false;
                    if (!it.hasNext()) {
                        UserInfo userInfo = (UserInfo) it.next();
                        userInteractor$toUserModels$1.L$0 = userInteractor2;
                        userInteractor$toUserModels$1.L$1 = arrayList;
                        userInteractor$toUserModels$1.L$2 = it;
                        userInteractor$toUserModels$1.I$0 = i3;
                        userInteractor$toUserModels$1.Z$0 = z6;
                        userInteractor$toUserModels$1.Z$1 = z3;
                        userInteractor$toUserModels$1.label = i4;
                        userInteractor2.getClass();
                        if (z6 || userInfo.isPrimary()) {
                            z7 = z6;
                            if (userInfo.isEnabled() && (userInfo.isGuest() || userInfo.supportsSwitchToByUser())) {
                                userModel = userInteractor2.toUserModel(userInfo, i3, z3, userInteractor$toUserModels$1);
                                if (userModel != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    userModel = (UserModel) userModel;
                                }
                                obj = userModel;
                                if (obj != coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                                z6 = z7;
                            }
                            userModel = null;
                            obj = userModel;
                            if (obj != coroutineSingletons) {
                            }
                        } else {
                            if (QpRune.QUICK_MANAGE_TWO_PHONE) {
                                boolean supportsMultipleUsers = DeviceState.supportsMultipleUsers();
                                SettingsHelper settingsHelper = userInteractor2.settingsHelper;
                                boolean isTwoPhoneRegistered = settingsHelper.isTwoPhoneRegistered();
                                boolean hasTwoPhoneAccount = settingsHelper.hasTwoPhoneAccount();
                                if (supportsMultipleUsers && isTwoPhoneRegistered && hasTwoPhoneAccount) {
                                    z5 = z4;
                                }
                                if (z5) {
                                    z7 = z6;
                                    StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("isOffTwoPhoneSetting: true [supportMultipleUsers:", supportsMultipleUsers, " isTwoPhoneRegistered:", isTwoPhoneRegistered, " hasTwoPhoneAccount:");
                                    m69m.append(hasTwoPhoneAccount);
                                    m69m.append("]");
                                    Log.d("UserInteractor", m69m.toString());
                                } else {
                                    z7 = z6;
                                }
                                if (!(!z5) && userInfo.isEnabled() && (userInfo.isGuest() || userInfo.supportsSwitchToByUser())) {
                                    userModel = userInteractor2.toUserModel(userInfo, i3, z3, userInteractor$toUserModels$1);
                                    if (userModel != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                        userModel = (UserModel) userModel;
                                    }
                                    obj = userModel;
                                    if (obj != coroutineSingletons) {
                                    }
                                }
                            } else {
                                z7 = z6;
                            }
                            userModel = null;
                            obj = userModel;
                            if (obj != coroutineSingletons) {
                            }
                        }
                        UserModel userModel22 = (UserModel) obj;
                        if (userModel22 != null) {
                        }
                        i4 = 2;
                        z5 = false;
                        if (!it.hasNext()) {
                            return (List) arrayList;
                        }
                    }
                }
                boolean booleanValue = ((Boolean) canSwitchUsers).booleanValue();
                List sortedWith = CollectionsKt___CollectionsKt.sortedWith(list2, new Comparator() { // from class: com.android.systemui.user.domain.interactor.UserInteractor$toUserModels$$inlined$sortedBy$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj2, Object obj3) {
                        return ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((UserInfo) obj2).isGuest()), Boolean.valueOf(((UserInfo) obj3).isGuest()));
                    }
                });
                userInteractor2 = userInteractor3;
                z3 = booleanValue;
                z4 = true;
                z5 = false;
                arrayList = new ArrayList();
                boolean z9 = z2;
                i3 = i5;
                z6 = z9;
                it = sortedWith.iterator();
                i4 = 2;
                if (!it.hasNext()) {
                }
            }
        }
        userInteractor$toUserModels$1 = new UserInteractor$toUserModels$1(userInteractor3, continuation);
        Object obj2 = userInteractor$toUserModels$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i2 = userInteractor$toUserModels$1.label;
        if (i2 != 0) {
        }
        boolean booleanValue2 = ((Boolean) canSwitchUsers).booleanValue();
        List sortedWith2 = CollectionsKt___CollectionsKt.sortedWith(list2, new Comparator() { // from class: com.android.systemui.user.domain.interactor.UserInteractor$toUserModels$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj22, Object obj3) {
                return ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((UserInfo) obj22).isGuest()), Boolean.valueOf(((UserInfo) obj3).isGuest()));
            }
        });
        userInteractor2 = userInteractor3;
        z3 = booleanValue2;
        z4 = true;
        z5 = false;
        arrayList = new ArrayList();
        boolean z92 = z2;
        i3 = i5;
        z6 = z92;
        it = sortedWith2.iterator();
        i4 = 2;
        if (!it.hasNext()) {
        }
    }

    public final void addCallback(UserCallback userCallback) {
        BuildersKt.launch$default(this.applicationScope, null, null, new UserInteractor$addCallback$1(this, userCallback, null), 3);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x007d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object canSwitchUsers(int i, Continuation continuation, boolean z) {
        UserInteractor$canSwitchUsers$1 userInteractor$canSwitchUsers$1;
        Object obj;
        CoroutineSingletons coroutineSingletons;
        int i2;
        UserInteractor userInteractor;
        int i3;
        boolean z2;
        if (continuation instanceof UserInteractor$canSwitchUsers$1) {
            userInteractor$canSwitchUsers$1 = (UserInteractor$canSwitchUsers$1) continuation;
            int i4 = userInteractor$canSwitchUsers$1.label;
            if ((i4 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                userInteractor$canSwitchUsers$1.label = i4 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj = userInteractor$canSwitchUsers$1.result;
                coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i2 = userInteractor$canSwitchUsers$1.label;
                boolean z3 = false;
                if (i2 != 0) {
                    ResultKt.throwOnFailure(obj);
                    UserInteractor$canSwitchUsers$isHeadlessSystemUserMode$1 userInteractor$canSwitchUsers$isHeadlessSystemUserMode$1 = new UserInteractor$canSwitchUsers$isHeadlessSystemUserMode$1(this, null);
                    userInteractor$canSwitchUsers$1.L$0 = this;
                    userInteractor$canSwitchUsers$1.I$0 = i;
                    userInteractor$canSwitchUsers$1.Z$0 = z;
                    userInteractor$canSwitchUsers$1.label = 1;
                    obj = BuildersKt.withContext(this.backgroundDispatcher, userInteractor$canSwitchUsers$isHeadlessSystemUserMode$1, userInteractor$canSwitchUsers$1);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i2 != 1) {
                        if (i2 != 2) {
                            if (i2 != 3) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            if (((Number) obj).intValue() == 0) {
                                z3 = true;
                            }
                            return Boolean.valueOf(z3);
                        }
                        i3 = userInteractor$canSwitchUsers$1.I$0;
                        userInteractor = (UserInteractor) userInteractor$canSwitchUsers$1.L$0;
                        ResultKt.throwOnFailure(obj);
                        if (((Boolean) obj).booleanValue()) {
                            z2 = false;
                            if (z2) {
                            }
                            return Boolean.valueOf(z3);
                        }
                        UserInteractor userInteractor2 = userInteractor;
                        i = i3;
                        this = userInteractor2;
                        z2 = true;
                        int i5 = i;
                        userInteractor = this;
                        i3 = i5;
                        if (z2) {
                            CoroutineDispatcher coroutineDispatcher = userInteractor.backgroundDispatcher;
                            UserInteractor$canSwitchUsers$2 userInteractor$canSwitchUsers$2 = new UserInteractor$canSwitchUsers$2(userInteractor, i3, null);
                            userInteractor$canSwitchUsers$1.L$0 = null;
                            userInteractor$canSwitchUsers$1.label = 3;
                            obj = BuildersKt.withContext(coroutineDispatcher, userInteractor$canSwitchUsers$2, userInteractor$canSwitchUsers$1);
                            if (obj == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            if (((Number) obj).intValue() == 0) {
                            }
                        }
                        return Boolean.valueOf(z3);
                    }
                    z = userInteractor$canSwitchUsers$1.Z$0;
                    i = userInteractor$canSwitchUsers$1.I$0;
                    this = (UserInteractor) userInteractor$canSwitchUsers$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                boolean booleanValue = ((Boolean) obj).booleanValue();
                if (z && booleanValue) {
                    userInteractor$canSwitchUsers$1.L$0 = this;
                    userInteractor$canSwitchUsers$1.I$0 = i;
                    userInteractor$canSwitchUsers$1.label = 2;
                    obj = this.isAnyUserUnlocked(userInteractor$canSwitchUsers$1);
                    if (obj != coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    int i6 = i;
                    userInteractor = this;
                    i3 = i6;
                    if (((Boolean) obj).booleanValue()) {
                    }
                }
                z2 = true;
                int i52 = i;
                userInteractor = this;
                i3 = i52;
                if (z2) {
                }
                return Boolean.valueOf(z3);
            }
        }
        userInteractor$canSwitchUsers$1 = new UserInteractor$canSwitchUsers$1(this, continuation);
        obj = userInteractor$canSwitchUsers$1.result;
        coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        i2 = userInteractor$canSwitchUsers$1.label;
        boolean z32 = false;
        if (i2 != 0) {
        }
        boolean booleanValue2 = ((Boolean) obj).booleanValue();
        if (z) {
            userInteractor$canSwitchUsers$1.L$0 = this;
            userInteractor$canSwitchUsers$1.I$0 = i;
            userInteractor$canSwitchUsers$1.label = 2;
            obj = this.isAnyUserUnlocked(userInteractor$canSwitchUsers$1);
            if (obj != coroutineSingletons) {
            }
        }
        z2 = true;
        int i522 = i;
        userInteractor = this;
        i3 = i522;
        if (z2) {
        }
        return Boolean.valueOf(z32);
    }

    public final void dismissDialog() {
        this._dialogDismissRequests.setValue(Unit.INSTANCE);
    }

    public final void executeAction(UserActionModel userActionModel, final UserSwitchDialogController.DialogShower dialogShower) {
        int i = WhenMappings.$EnumSwitchMapping$0[userActionModel.ordinal()];
        UiEventLogger uiEventLogger = this.uiEventLogger;
        if (i == 1) {
            uiEventLogger.log(MultiUserActionsEvent.CREATE_GUEST_FROM_USER_SWITCHER);
            UserInteractor$executeAction$1 userInteractor$executeAction$1 = new UserInteractor$executeAction$1(this);
            UserInteractor$executeAction$2 userInteractor$executeAction$2 = new UserInteractor$executeAction$2(this);
            Function1 function1 = new Function1() { // from class: com.android.systemui.user.domain.interactor.UserInteractor$executeAction$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    UserInteractor.this.selectUser(((Number) obj).intValue(), dialogShower);
                    return Unit.INSTANCE;
                }
            };
            GuestUserInteractor guestUserInteractor = this.guestUserInteractor;
            BuildersKt.launch$default(guestUserInteractor.applicationScope, null, null, new GuestUserInteractor$createAndSwitchTo$1(guestUserInteractor, userInteractor$executeAction$1, userInteractor$executeAction$2, function1, null), 3);
            return;
        }
        Context context = this.applicationContext;
        if (i != 2) {
            ActivityStarter activityStarter = this.activityStarter;
            if (i != 3) {
                if (i != 4) {
                    return;
                }
                activityStarter.startActivity(new Intent("android.settings.USER_SETTINGS"), true);
                return;
            } else {
                uiEventLogger.log(MultiUserActionsEvent.CREATE_RESTRICTED_USER_FROM_USER_SWITCHER);
                dismissDialog();
                activityStarter.startActivity(new Intent().setAction("android.os.action.CREATE_SUPERVISED_USER").setPackage(context.getString(android.R.string.ext_media_status_bad_removal)).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE), true);
                return;
            }
        }
        uiEventLogger.log(MultiUserActionsEvent.CREATE_USER_FROM_USER_SWITCHER);
        UserInfo selectedUserInfo = ((UserRepositoryImpl) this.repository).getSelectedUserInfo();
        dismissDialog();
        ActivityStarter activityStarter2 = this.activityStarter;
        boolean z = ((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) this.keyguardInteractor.repository).keyguardStateController).mShowing;
        int i2 = CreateUserActivity.$r8$clinit;
        Intent intent = new Intent(context, (Class<?>) CreateUserActivity.class);
        intent.addFlags(335544320);
        intent.putExtra("extra_is_keyguard_showing", z);
        activityStarter2.startActivity(intent, true, null, true, selectedUserInfo.getUserHandle());
    }

    public final void exitGuestUser(int i, int i2, boolean z) {
        UserInteractor$exitGuestUser$1 userInteractor$exitGuestUser$1 = new UserInteractor$exitGuestUser$1(this);
        UserInteractor$exitGuestUser$2 userInteractor$exitGuestUser$2 = new UserInteractor$exitGuestUser$2(this);
        UserInteractor$exitGuestUser$3 userInteractor$exitGuestUser$3 = new UserInteractor$exitGuestUser$3(this);
        GuestUserInteractor guestUserInteractor = this.guestUserInteractor;
        UserInfo selectedUserInfo = ((UserRepositoryImpl) guestUserInteractor.repository).getSelectedUserInfo();
        int i3 = selectedUserInfo.id;
        if (i3 != i) {
            Log.w("GuestUserInteractor", SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("User requesting to start a new session (", i, ") is not current user (", i3, ")"));
            return;
        }
        if (selectedUserInfo.isGuest()) {
            BuildersKt.launch$default(guestUserInteractor.applicationScope, null, null, new GuestUserInteractor$exit$1(guestUserInteractor, i2, selectedUserInfo, z, userInteractor$exitGuestUser$1, userInteractor$exitGuestUser$2, userInteractor$exitGuestUser$3, null), 3);
        } else {
            Log.w("GuestUserInteractor", "User requesting to start a new session (" + i + ") is not a guest");
        }
    }

    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 getActions() {
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) this.repository;
        return FlowKt.combine(userRepositoryImpl.selectedUserInfo, this.userInfos, userRepositoryImpl.userSwitcherSettings, this.keyguardInteractor.isKeyguardShowing, new UserInteractor$actions$1(this, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getUserImage(int i, Continuation continuation, boolean z) {
        UserInteractor$getUserImage$1 userInteractor$getUserImage$1;
        int i2;
        if (continuation instanceof UserInteractor$getUserImage$1) {
            userInteractor$getUserImage$1 = (UserInteractor$getUserImage$1) continuation;
            int i3 = userInteractor$getUserImage$1.label;
            if ((i3 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                userInteractor$getUserImage$1.label = i3 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = userInteractor$getUserImage$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i2 = userInteractor$getUserImage$1.label;
                if (i2 != 0) {
                    ResultKt.throwOnFailure(obj);
                    if (z) {
                        Drawable drawable = this.applicationContext.getDrawable(R.drawable.ic_account_circle);
                        if (drawable != null) {
                            return drawable;
                        }
                        throw new IllegalStateException("Required value was null.".toString());
                    }
                    UserInteractor$getUserImage$userIcon$1 userInteractor$getUserImage$userIcon$1 = new UserInteractor$getUserImage$userIcon$1(this, i, null);
                    userInteractor$getUserImage$1.L$0 = this;
                    userInteractor$getUserImage$1.I$0 = i;
                    userInteractor$getUserImage$1.label = 1;
                    obj = BuildersKt.withContext(this.backgroundDispatcher, userInteractor$getUserImage$userIcon$1, userInteractor$getUserImage$1);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    i = userInteractor$getUserImage$1.I$0;
                    this = (UserInteractor) userInteractor$getUserImage$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                Bitmap bitmap = (Bitmap) obj;
                return bitmap == null ? new BitmapDrawable(bitmap) : UserIcons.getDefaultUserIcon(this.applicationContext.getResources(), i, false);
            }
        }
        userInteractor$getUserImage$1 = new UserInteractor$getUserImage$1(this, continuation);
        Object obj2 = userInteractor$getUserImage$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i2 = userInteractor$getUserImage$1.label;
        if (i2 != 0) {
        }
        Bitmap bitmap2 = (Bitmap) obj2;
        if (bitmap2 == null) {
        }
    }

    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 getUsers() {
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) this.repository;
        return FlowKt.combine(this.userInfos, userRepositoryImpl.selectedUserInfo, userRepositoryImpl.userSwitcherSettings, new UserInteractor$users$1(this, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0074 -> B:10:0x0077). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object isAnyUserUnlocked(Continuation continuation) {
        UserInteractor$isAnyUserUnlocked$1 userInteractor$isAnyUserUnlocked$1;
        int i;
        UserInteractor userInteractor;
        Iterator it;
        boolean z;
        if (continuation instanceof UserInteractor$isAnyUserUnlocked$1) {
            userInteractor$isAnyUserUnlocked$1 = (UserInteractor$isAnyUserUnlocked$1) continuation;
            int i2 = userInteractor$isAnyUserUnlocked$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                userInteractor$isAnyUserUnlocked$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = userInteractor$isAnyUserUnlocked$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = userInteractor$isAnyUserUnlocked$1.label;
                boolean z2 = false;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    List users = this.manager.getUsers(true, true, true);
                    if (!users.isEmpty()) {
                        Iterator it2 = users.iterator();
                        userInteractor = this;
                        it = it2;
                        if (it.hasNext()) {
                        }
                    }
                    return Boolean.valueOf(z2);
                }
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                it = (Iterator) userInteractor$isAnyUserUnlocked$1.L$1;
                UserInteractor userInteractor2 = (UserInteractor) userInteractor$isAnyUserUnlocked$1.L$0;
                ResultKt.throwOnFailure(obj);
                if (((Boolean) obj).booleanValue()) {
                    userInteractor = userInteractor2;
                    z = false;
                } else {
                    userInteractor = userInteractor2;
                    z = true;
                }
                if (z) {
                    z2 = true;
                    return Boolean.valueOf(z2);
                }
                if (it.hasNext()) {
                    UserInfo userInfo = (UserInfo) it.next();
                    if (userInfo.id != 0) {
                        CoroutineDispatcher coroutineDispatcher = userInteractor.backgroundDispatcher;
                        UserInteractor$isAnyUserUnlocked$2$1 userInteractor$isAnyUserUnlocked$2$1 = new UserInteractor$isAnyUserUnlocked$2$1(userInteractor, userInfo, null);
                        userInteractor$isAnyUserUnlocked$1.L$0 = userInteractor;
                        userInteractor$isAnyUserUnlocked$1.L$1 = it;
                        userInteractor$isAnyUserUnlocked$1.label = 1;
                        Object withContext = BuildersKt.withContext(coroutineDispatcher, userInteractor$isAnyUserUnlocked$2$1, userInteractor$isAnyUserUnlocked$1);
                        if (withContext == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        userInteractor2 = userInteractor;
                        obj = withContext;
                        if (((Boolean) obj).booleanValue()) {
                        }
                        if (z) {
                        }
                        if (it.hasNext()) {
                        }
                    } else {
                        z = false;
                        if (z) {
                        }
                        if (it.hasNext()) {
                        }
                    }
                }
                return Boolean.valueOf(z2);
            }
        }
        userInteractor$isAnyUserUnlocked$1 = new UserInteractor$isAnyUserUnlocked$1(this, continuation);
        Object obj2 = userInteractor$isAnyUserUnlocked$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = userInteractor$isAnyUserUnlocked$1.label;
        boolean z22 = false;
        if (i != 0) {
        }
    }

    public final void removeCallback(UserCallback userCallback) {
        BuildersKt.launch$default(this.applicationScope, null, null, new UserInteractor$removeCallback$1(this, userCallback, null), 3);
    }

    public final void removeGuestUser(int i) {
        BuildersKt.launch$default(this.applicationScope, null, null, new UserInteractor$removeGuestUser$1(this, i, -10000, null), 3);
    }

    public final void selectUser(int i, UserSwitchDialogController.DialogShower dialogShower) {
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) this.repository;
        UserInfo selectedUserInfo = userRepositoryImpl.getSelectedUserInfo();
        int i2 = selectedUserInfo.id;
        KeyguardInteractor keyguardInteractor = this.keyguardInteractor;
        if (i == i2 && selectedUserInfo.isGuest()) {
            showDialog(new ShowDialogRequestModel.ShowExitGuestDialog(selectedUserInfo.id, userRepositoryImpl.lastSelectedNonGuestUserId, selectedUserInfo.isEphemeral(), ((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) keyguardInteractor.repository).keyguardStateController).mShowing, new UserInteractor$selectUser$1(this), dialogShower));
        } else {
            if (selectedUserInfo.isGuest()) {
                showDialog(new ShowDialogRequestModel.ShowExitGuestDialog(selectedUserInfo.id, i, selectedUserInfo.isEphemeral(), ((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) keyguardInteractor.repository).keyguardStateController).mShowing, new UserInteractor$selectUser$2(this), dialogShower));
                return;
            }
            if (dialogShower != null) {
                ((DialogShowerImpl) dialogShower).dismiss();
            }
            switchUser(i);
        }
    }

    public final void showDialog(ShowDialogRequestModel showDialogRequestModel) {
        this._dialogShowRequests.setValue(showDialogRequestModel);
    }

    public final void showUserSwitcher(Expandable expandable) {
        if (((FeatureFlagsRelease) this.featureFlags).isEnabled(Flags.FULL_SCREEN_USER_SWITCHER)) {
            showDialog(new ShowDialogRequestModel.ShowUserSwitcherFullscreenDialog(expandable));
        } else {
            showDialog(new ShowDialogRequestModel.ShowUserSwitcherDialog(expandable));
        }
    }

    public final void switchUser(int i) {
        RefreshUsersScheduler refreshUsersScheduler = this.refreshUsersScheduler;
        BuildersKt.launch$default(refreshUsersScheduler.applicationScope, refreshUsersScheduler.mainDispatcher, null, new RefreshUsersScheduler$pause$1(refreshUsersScheduler, null), 2);
        try {
            this.activityManager.switchUser(i);
        } catch (RemoteException e) {
            Log.e("UserInteractor", "Couldn't switch user.", e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002b  */
    /* JADX WARN: Type inference failed for: r17v0, types: [com.android.systemui.user.domain.interactor.UserInteractor] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.common.shared.model.Text] */
    /* JADX WARN: Type inference failed for: r4v6, types: [com.android.systemui.common.shared.model.Text] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object toUserModel(UserInfo userInfo, int i, boolean z, Continuation continuation) {
        UserInteractor$toUserModel$1 userInteractor$toUserModel$1;
        int i2;
        int i3;
        int i4;
        Text.Loaded loaded;
        boolean z2;
        int i5;
        int i6;
        Text.Loaded loaded2;
        UserModel userModel;
        boolean z3 = z;
        if (continuation instanceof UserInteractor$toUserModel$1) {
            userInteractor$toUserModel$1 = (UserInteractor$toUserModel$1) continuation;
            int i7 = userInteractor$toUserModel$1.label;
            if ((i7 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                userInteractor$toUserModel$1.label = i7 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = userInteractor$toUserModel$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i2 = userInteractor$toUserModel$1.label;
                if (i2 != 0) {
                    ResultKt.throwOnFailure(obj);
                    int i8 = userInfo.id;
                    int i9 = i8 == i ? 1 : 0;
                    if (userInfo.isGuest()) {
                        Text.Loaded loaded3 = new Text.Loaded(userInfo.name);
                        userInteractor$toUserModel$1.L$0 = loaded3;
                        userInteractor$toUserModel$1.Z$0 = z3;
                        userInteractor$toUserModel$1.I$0 = i9;
                        userInteractor$toUserModel$1.I$1 = i8;
                        userInteractor$toUserModel$1.label = 1;
                        Object userImage = getUserImage(i8, userInteractor$toUserModel$1, true);
                        if (userImage == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        z2 = z3;
                        i5 = i8;
                        i6 = i9;
                        loaded2 = loaded3;
                        obj = userImage;
                        userModel = new UserModel(i5, loaded2, (Drawable) obj, i6 == 0, z2, true);
                    } else {
                        Text.Loaded loaded4 = new Text.Loaded(userInfo.name);
                        userInteractor$toUserModel$1.L$0 = loaded4;
                        userInteractor$toUserModel$1.Z$0 = z3;
                        userInteractor$toUserModel$1.I$0 = i9;
                        userInteractor$toUserModel$1.I$1 = i8;
                        userInteractor$toUserModel$1.label = 2;
                        Object userImage2 = getUserImage(i8, userInteractor$toUserModel$1, false);
                        if (userImage2 == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        i3 = i8;
                        i4 = i9;
                        loaded = loaded4;
                        obj = userImage2;
                        if (z3) {
                        }
                        userModel = new UserModel(i3, loaded, (Drawable) obj, i4 == 0, (z3 && i4 == 0) ? false : true, false);
                    }
                } else if (i2 == 1) {
                    int i10 = userInteractor$toUserModel$1.I$1;
                    i6 = userInteractor$toUserModel$1.I$0;
                    boolean z4 = userInteractor$toUserModel$1.Z$0;
                    ?? r4 = (Text) userInteractor$toUserModel$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    i5 = i10;
                    z2 = z4;
                    loaded2 = r4;
                    userModel = new UserModel(i5, loaded2, (Drawable) obj, i6 == 0, z2, true);
                } else {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    int i11 = userInteractor$toUserModel$1.I$1;
                    i4 = userInteractor$toUserModel$1.I$0;
                    z3 = userInteractor$toUserModel$1.Z$0;
                    ?? r42 = (Text) userInteractor$toUserModel$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    i3 = i11;
                    loaded = r42;
                    userModel = new UserModel(i3, loaded, (Drawable) obj, i4 == 0, (z3 && i4 == 0) ? false : true, false);
                }
                return userModel;
            }
        }
        userInteractor$toUserModel$1 = new UserInteractor$toUserModel$1(this, continuation);
        Object obj2 = userInteractor$toUserModel$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i2 = userInteractor$toUserModel$1.label;
        if (i2 != 0) {
        }
        return userModel;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$toRecord(UserInteractor userInteractor, UserActionModel userActionModel, int i, boolean z, Continuation continuation) {
        UserInteractor$toRecord$2 userInteractor$toRecord$2;
        int i2;
        Context context;
        UserActionModel userActionModel2;
        Object canSwitchUsers;
        boolean z2;
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced;
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin;
        UserInteractor userInteractor2 = userInteractor;
        int i3 = i;
        userInteractor.getClass();
        if (continuation instanceof UserInteractor$toRecord$2) {
            userInteractor$toRecord$2 = (UserInteractor$toRecord$2) continuation;
            int i4 = userInteractor$toRecord$2.label;
            if ((i4 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                userInteractor$toRecord$2.label = i4 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = userInteractor$toRecord$2.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i2 = userInteractor$toRecord$2.label;
                if (i2 != 0) {
                    ResultKt.throwOnFailure(obj);
                    userInteractor$toRecord$2.L$0 = userInteractor2;
                    context = userInteractor2.applicationContext;
                    userInteractor$toRecord$2.L$1 = context;
                    userActionModel2 = userActionModel;
                    userInteractor$toRecord$2.L$2 = userActionModel2;
                    userInteractor$toRecord$2.I$0 = i3;
                    userInteractor$toRecord$2.Z$0 = z;
                    userInteractor$toRecord$2.label = 1;
                    canSwitchUsers = userInteractor2.canSwitchUsers(i3, userInteractor$toRecord$2, true);
                    if (canSwitchUsers == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    z2 = z;
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    boolean z3 = userInteractor$toRecord$2.Z$0;
                    i3 = userInteractor$toRecord$2.I$0;
                    UserActionModel userActionModel3 = (UserActionModel) userInteractor$toRecord$2.L$2;
                    context = (Context) userInteractor$toRecord$2.L$1;
                    UserInteractor userInteractor3 = (UserInteractor) userInteractor$toRecord$2.L$0;
                    ResultKt.throwOnFailure(obj);
                    z2 = z3;
                    userInteractor2 = userInteractor3;
                    canSwitchUsers = obj;
                    userActionModel2 = userActionModel3;
                }
                boolean z4 = (((Boolean) canSwitchUsers).booleanValue() || (userInteractor2.isGuestUserAutoCreated && userInteractor2.isGuestUserResetting)) ? false : true;
                LegacyUserDataHelper legacyUserDataHelper = LegacyUserDataHelper.INSTANCE;
                boolean z5 = userActionModel2 != UserActionModel.ENTER_GUEST_MODE;
                boolean z6 = userActionModel2 != UserActionModel.ADD_USER;
                boolean z7 = userActionModel2 != UserActionModel.ADD_SUPERVISED_USER;
                LegacyUserDataHelper.INSTANCE.getClass();
                checkIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(context, "no_add_user", i3);
                if (checkIfRestrictionEnforced != null) {
                    enforcedAdmin = null;
                } else {
                    if (RestrictedLockUtilsInternal.hasBaseUserRestriction(context, "no_add_user", i3)) {
                        checkIfRestrictionEnforced = null;
                    }
                    enforcedAdmin = checkIfRestrictionEnforced;
                }
                return new UserRecord(null, null, z5, false, z6, z2, z4, z7, enforcedAdmin, userActionModel2 != UserActionModel.NAVIGATE_TO_USER_MANAGEMENT, 11, null);
            }
        }
        userInteractor$toRecord$2 = new UserInteractor$toRecord$2(userInteractor2, continuation);
        Object obj2 = userInteractor$toRecord$2.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i2 = userInteractor$toRecord$2.label;
        if (i2 != 0) {
        }
        if (((Boolean) canSwitchUsers).booleanValue()) {
        }
        LegacyUserDataHelper legacyUserDataHelper2 = LegacyUserDataHelper.INSTANCE;
        if (userActionModel2 != UserActionModel.ENTER_GUEST_MODE) {
        }
        if (userActionModel2 != UserActionModel.ADD_USER) {
        }
        if (userActionModel2 != UserActionModel.ADD_SUPERVISED_USER) {
        }
        LegacyUserDataHelper.INSTANCE.getClass();
        checkIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(context, "no_add_user", i3);
        if (checkIfRestrictionEnforced != null) {
        }
        return new UserRecord(null, null, z5, false, z6, z2, z4, z7, enforcedAdmin, userActionModel2 != UserActionModel.NAVIGATE_TO_USER_MANAGEMENT, 11, null);
    }
}
