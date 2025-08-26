package com.android.systemui.user.domain.interactor;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.UserIcons;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.SystemUISecondaryUserService;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.process.ProcessWrapper;
import com.android.systemui.telephony.domain.interactor.TelephonyInteractor;
import com.android.systemui.user.CreateUserActivity;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import com.android.systemui.user.data.source.UserRecord;
import com.android.systemui.user.domain.model.ShowDialogRequestModel;
import com.android.systemui.user.legacyhelper.data.LegacyUserDataHelper;
import com.android.systemui.user.shared.model.UserActionModel;
import com.android.systemui.user.shared.model.UserModel;
import com.android.systemui.user.ui.dialog.DialogShowerImpl;
import com.android.systemui.user.utils.MultiUserActionsEvent;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.kotlin.WithPrev;
import com.android.systemui.utils.UserRestrictionChecker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes3.dex */
public final class UserSwitcherInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _dialogDismissRequests;
    public final StateFlowImpl _dialogShowRequests;
    public final ActivityManager activityManager;
    public final ActivityStarter activityStarter;
    public final Context applicationContext;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final MutexImpl callbackMutex = MutexKt.Mutex$default();
    public final Set callbacks = new LinkedHashSet();
    public final ReadonlyStateFlow dialogDismissRequests;
    public final ReadonlyStateFlow dialogShowRequests;
    public final FeatureFlags featureFlags;
    public final GuestUserInteractor guestUserInteractor;
    public final HeadlessSystemUserMode headlessSystemUserMode;
    public final boolean isGuestUserAutoCreated;
    public final boolean isGuestUserResetting;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback;
    public final CoroutineDispatcher mainDispatcher;
    public final UserManager manager;
    public final ProcessWrapper processWrapper;
    public final RefreshUsersScheduler refreshUsersScheduler;
    public final UserRepository repository;
    public final ReadonlyStateFlow selectedUserRecord;
    public final UiEventLogger uiEventLogger;
    public final UserSwitcherInteractor$special$$inlined$map$1 userInfos;
    public final UserLogoutInteractor userLogoutInteractor;
    public final ReadonlyStateFlow userRecords;
    public final UserRestrictionChecker userRestrictionChecker;

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSwitcherInteractor.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            UserSwitcherInteractor.this.refreshUsersScheduler.refreshIfNotPaused();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public AnonymousClass4(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass4 anonymousClass4 = new AnonymousClass4((Continuation) obj3);
            anonymousClass4.L$0 = (Intent) obj;
            anonymousClass4.L$1 = (WithPrev) obj2;
            return anonymousClass4.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return new Pair((Intent) this.L$0, ((WithPrev) this.L$1).getPreviousValue());
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$5, reason: invalid class name */
    final class AnonymousClass5 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass5(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass5 anonymousClass5 = UserSwitcherInteractor.this.new AnonymousClass5(continuation);
            anonymousClass5.L$0 = obj;
            return anonymousClass5;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass5) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                UserSwitcherInteractor userSwitcherInteractor = UserSwitcherInteractor.this;
                this.label = 1;
                if (UserSwitcherInteractor.access$onBroadcastReceived(userSwitcherInteractor, intent, userInfo, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$6, reason: invalid class name */
    final class AnonymousClass6 extends SuspendLambda implements Function2 {
        final /* synthetic */ KeyguardUpdateMonitor $keyguardUpdateMonitor;
        int label;

        /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$6$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardUpdateMonitor $keyguardUpdateMonitor;
            int label;
            final /* synthetic */ UserSwitcherInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(KeyguardUpdateMonitor keyguardUpdateMonitor, UserSwitcherInteractor userSwitcherInteractor, Continuation continuation) {
                super(2, continuation);
                this.$keyguardUpdateMonitor = keyguardUpdateMonitor;
                this.this$0 = userSwitcherInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$keyguardUpdateMonitor, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$keyguardUpdateMonitor.registerCallback(this.this$0.keyguardUpdateMonitorCallback);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass6(KeyguardUpdateMonitor keyguardUpdateMonitor, Continuation continuation) {
            super(2, continuation);
            this.$keyguardUpdateMonitor = keyguardUpdateMonitor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSwitcherInteractor.this.new AnonymousClass6(this.$keyguardUpdateMonitor, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                UserSwitcherInteractor userSwitcherInteractor = UserSwitcherInteractor.this;
                CoroutineDispatcher coroutineDispatcher = userSwitcherInteractor.mainDispatcher;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$keyguardUpdateMonitor, userSwitcherInteractor, null);
                this.label = 1;
                if (BuildersKt.withContext(coroutineDispatcher, anonymousClass1, this) == coroutineSingletons) {
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface UserCallback {
        default boolean isEvictable() {
            return false;
        }

        void onUserStateChanged();
    }

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
            try {
                iArr[UserActionModel.SIGN_OUT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$addCallback$1, reason: invalid class name and case insensitive filesystem */
    final class C11281 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserCallback $callback;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11281(UserCallback userCallback, Continuation continuation) {
            super(2, continuation);
            this.$callback = userCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSwitcherInteractor.this.new C11281(this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11281) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            UserSwitcherInteractor userSwitcherInteractor;
            Mutex mutex;
            UserCallback userCallback;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                userSwitcherInteractor = UserSwitcherInteractor.this;
                MutexImpl mutexImpl = userSwitcherInteractor.callbackMutex;
                UserCallback userCallback2 = this.$callback;
                this.L$0 = mutexImpl;
                this.L$1 = userSwitcherInteractor;
                this.L$2 = userCallback2;
                this.label = 1;
                if (mutexImpl.lock(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                mutex = mutexImpl;
                userCallback = userCallback2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                userCallback = (UserCallback) this.L$2;
                userSwitcherInteractor = (UserSwitcherInteractor) this.L$1;
                mutex = (Mutex) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            try {
                userSwitcherInteractor.callbacks.add(userCallback);
                Unit unit = Unit.INSTANCE;
                mutex.unlock(null);
                return Unit.INSTANCE;
            } catch (Throwable th) {
                mutex.unlock(null);
                throw th;
            }
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$canSwitchUsers$1, reason: invalid class name and case insensitive filesystem */
    final class C11291 extends ContinuationImpl {
        int I$0;
        Object L$0;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public C11291(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            UserSwitcherInteractor userSwitcherInteractor = UserSwitcherInteractor.this;
            int i = UserSwitcherInteractor.$r8$clinit;
            return userSwitcherInteractor.canSwitchUsers(0, this, false);
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$canSwitchUsers$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $selectedUserId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int i, Continuation continuation) {
            super(2, continuation);
            this.$selectedUserId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSwitcherInteractor.this.new AnonymousClass2(this.$selectedUserId, continuation);
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
            return new Integer(UserSwitcherInteractor.this.manager.getUserSwitchability(UserHandle.of(this.$selectedUserId)));
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$executeAction$1, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C11301 extends FunctionReferenceImpl implements Function1 {
        public C11301(Object obj) {
            super(1, obj, UserSwitcherInteractor.class, "showDialog", "showDialog(Lcom/android/systemui/user/domain/model/ShowDialogRequestModel;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            UserSwitcherInteractor userSwitcherInteractor = (UserSwitcherInteractor) this.receiver;
            int i = UserSwitcherInteractor.$r8$clinit;
            userSwitcherInteractor.showDialog((ShowDialogRequestModel) obj);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$executeAction$2, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C11312 extends FunctionReferenceImpl implements Function0 {
        public C11312(Object obj) {
            super(0, obj, UserSwitcherInteractor.class, "dismissDialog", "dismissDialog()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            UserSwitcherInteractor userSwitcherInteractor = (UserSwitcherInteractor) this.receiver;
            int i = UserSwitcherInteractor.$r8$clinit;
            userSwitcherInteractor.dismissDialog();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$executeAction$4, reason: invalid class name and case insensitive filesystem */
    final class C11324 extends SuspendLambda implements Function2 {
        int label;

        public C11324(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSwitcherInteractor.this.new C11324(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11324) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            UserSwitcherInteractor.this.userLogoutInteractor.logOut();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$exitGuestUser$1, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C11331 extends FunctionReferenceImpl implements Function1 {
        public C11331(Object obj) {
            super(1, obj, UserSwitcherInteractor.class, "showDialog", "showDialog(Lcom/android/systemui/user/domain/model/ShowDialogRequestModel;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            UserSwitcherInteractor userSwitcherInteractor = (UserSwitcherInteractor) this.receiver;
            int i = UserSwitcherInteractor.$r8$clinit;
            userSwitcherInteractor.showDialog((ShowDialogRequestModel) obj);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$exitGuestUser$2, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C11342 extends FunctionReferenceImpl implements Function0 {
        public C11342(Object obj) {
            super(0, obj, UserSwitcherInteractor.class, "dismissDialog", "dismissDialog()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            UserSwitcherInteractor userSwitcherInteractor = (UserSwitcherInteractor) this.receiver;
            int i = UserSwitcherInteractor.$r8$clinit;
            userSwitcherInteractor.dismissDialog();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$exitGuestUser$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass3(Object obj) {
            super(1, obj, UserSwitcherInteractor.class, "switchUser", "switchUser(I)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            int iIntValue = ((Number) obj).intValue();
            UserSwitcherInteractor userSwitcherInteractor = (UserSwitcherInteractor) this.receiver;
            int i = UserSwitcherInteractor.$r8$clinit;
            userSwitcherInteractor.switchUser(iIntValue);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$getUserImage$1, reason: invalid class name and case insensitive filesystem */
    final class C11351 extends ContinuationImpl {
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C11351(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            UserSwitcherInteractor userSwitcherInteractor = UserSwitcherInteractor.this;
            int i = UserSwitcherInteractor.$r8$clinit;
            return userSwitcherInteractor.getUserImage(0, this, false);
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$isAnyUserUnlocked$1, reason: invalid class name and case insensitive filesystem */
    final class C11361 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C11361(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            UserSwitcherInteractor userSwitcherInteractor = UserSwitcherInteractor.this;
            int i = UserSwitcherInteractor.$r8$clinit;
            return userSwitcherInteractor.isAnyUserUnlocked(this);
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$removeCallback$1, reason: invalid class name and case insensitive filesystem */
    final class C11371 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserCallback $callback;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11371(UserCallback userCallback, Continuation continuation) {
            super(2, continuation);
            this.$callback = userCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSwitcherInteractor.this.new C11371(this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11371) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            UserSwitcherInteractor userSwitcherInteractor;
            Mutex mutex;
            UserCallback userCallback;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                userSwitcherInteractor = UserSwitcherInteractor.this;
                MutexImpl mutexImpl = userSwitcherInteractor.callbackMutex;
                UserCallback userCallback2 = this.$callback;
                this.L$0 = mutexImpl;
                this.L$1 = userSwitcherInteractor;
                this.L$2 = userCallback2;
                this.label = 1;
                if (mutexImpl.lock(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                mutex = mutexImpl;
                userCallback = userCallback2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                userCallback = (UserCallback) this.L$2;
                userSwitcherInteractor = (UserSwitcherInteractor) this.L$1;
                mutex = (Mutex) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            try {
                userSwitcherInteractor.callbacks.remove(userCallback);
                Unit unit = Unit.INSTANCE;
                mutex.unlock(null);
                return Unit.INSTANCE;
            } catch (Throwable th) {
                mutex.unlock(null);
                throw th;
            }
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$removeGuestUser$1, reason: invalid class name and case insensitive filesystem */
    final class C11381 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $guestUserId;
        final /* synthetic */ int $targetUserId;
        int label;

        /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$removeGuestUser$1$1, reason: invalid class name and collision with other inner class name */
        final /* synthetic */ class C06111 extends FunctionReferenceImpl implements Function1 {
            public C06111(Object obj) {
                super(1, obj, UserSwitcherInteractor.class, "showDialog", "showDialog(Lcom/android/systemui/user/domain/model/ShowDialogRequestModel;)V", 0);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                UserSwitcherInteractor userSwitcherInteractor = (UserSwitcherInteractor) this.receiver;
                int i = UserSwitcherInteractor.$r8$clinit;
                userSwitcherInteractor.showDialog((ShowDialogRequestModel) obj);
                return Unit.INSTANCE;
            }
        }

        /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$removeGuestUser$1$2, reason: invalid class name */
        final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function0 {
            public AnonymousClass2(Object obj) {
                super(0, obj, UserSwitcherInteractor.class, "dismissDialog", "dismissDialog()V", 0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                UserSwitcherInteractor userSwitcherInteractor = (UserSwitcherInteractor) this.receiver;
                int i = UserSwitcherInteractor.$r8$clinit;
                userSwitcherInteractor.dismissDialog();
                return Unit.INSTANCE;
            }
        }

        /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$removeGuestUser$1$3, reason: invalid class name */
        final /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1 {
            public AnonymousClass3(Object obj) {
                super(1, obj, UserSwitcherInteractor.class, "switchUser", "switchUser(I)V", 0);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                int iIntValue = ((Number) obj).intValue();
                UserSwitcherInteractor userSwitcherInteractor = (UserSwitcherInteractor) this.receiver;
                int i = UserSwitcherInteractor.$r8$clinit;
                userSwitcherInteractor.switchUser(iIntValue);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11381(int i, int i2, Continuation continuation) {
            super(2, continuation);
            this.$guestUserId = i;
            this.$targetUserId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSwitcherInteractor.this.new C11381(this.$guestUserId, this.$targetUserId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11381) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                GuestUserInteractor guestUserInteractor = UserSwitcherInteractor.this.guestUserInteractor;
                int i2 = this.$guestUserId;
                int i3 = this.$targetUserId;
                C06111 c06111 = new C06111(UserSwitcherInteractor.this);
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(UserSwitcherInteractor.this);
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(UserSwitcherInteractor.this);
                this.label = 1;
                if (guestUserInteractor.remove(i2, i3, c06111, anonymousClass2, anonymousClass3, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$selectUser$1, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C11391 extends FunctionReferenceImpl implements Function3 {
        public C11391(Object obj) {
            super(3, obj, UserSwitcherInteractor.class, "exitGuestUser", "exitGuestUser(IIZ)V", 0);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            ((UserSwitcherInteractor) this.receiver).exitGuestUser(((Number) obj).intValue(), ((Number) obj2).intValue(), ((Boolean) obj3).booleanValue());
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$selectUser$2, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C11402 extends FunctionReferenceImpl implements Function3 {
        public C11402(Object obj) {
            super(3, obj, UserSwitcherInteractor.class, "exitGuestUser", "exitGuestUser(IIZ)V", 0);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            ((UserSwitcherInteractor) this.receiver).exitGuestUser(((Number) obj).intValue(), ((Number) obj2).intValue(), ((Boolean) obj3).booleanValue());
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$switchUser$1, reason: invalid class name and case insensitive filesystem */
    final class C11411 extends SuspendLambda implements Function2 {
        final /* synthetic */ Runnable $runnable;
        int label;

        /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$switchUser$1$1, reason: invalid class name and collision with other inner class name */
        final class C06121 extends SuspendLambda implements Function2 {
            final /* synthetic */ Runnable $runnable;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C06121(Runnable runnable, Continuation continuation) {
                super(2, continuation);
                this.$runnable = runnable;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C06121(this.$runnable, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C06121) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$runnable.run();
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11411(Runnable runnable, Continuation continuation) {
            super(2, continuation);
            this.$runnable = runnable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSwitcherInteractor.this.new C11411(this.$runnable, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11411) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher coroutineDispatcher = UserSwitcherInteractor.this.backgroundDispatcher;
                C06121 c06121 = new C06121(this.$runnable, null);
                this.label = 1;
                if (BuildersKt.withContext(coroutineDispatcher, c06121, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$toUserModel$1, reason: invalid class name and case insensitive filesystem */
    final class C11421 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public C11421(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            UserSwitcherInteractor userSwitcherInteractor = UserSwitcherInteractor.this;
            int i = UserSwitcherInteractor.$r8$clinit;
            return userSwitcherInteractor.toUserModel(null, 0, false, this);
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r9v1, types: [com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public UserSwitcherInteractor(Context context, UserRepository userRepository, ActivityStarter activityStarter, KeyguardInteractor keyguardInteractor, FeatureFlags featureFlags, UserManager userManager, HeadlessSystemUserMode headlessSystemUserMode, CoroutineScope coroutineScope, TelephonyInteractor telephonyInteractor, BroadcastDispatcher broadcastDispatcher, KeyguardUpdateMonitor keyguardUpdateMonitor, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, ActivityManager activityManager, RefreshUsersScheduler refreshUsersScheduler, GuestUserInteractor guestUserInteractor, UiEventLogger uiEventLogger, UserRestrictionChecker userRestrictionChecker, ProcessWrapper processWrapper, UserLogoutInteractor userLogoutInteractor) {
        this.applicationContext = context;
        this.repository = userRepository;
        this.activityStarter = activityStarter;
        this.keyguardInteractor = keyguardInteractor;
        this.featureFlags = featureFlags;
        this.manager = userManager;
        this.headlessSystemUserMode = headlessSystemUserMode;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.mainDispatcher = coroutineDispatcher2;
        this.activityManager = activityManager;
        this.refreshUsersScheduler = refreshUsersScheduler;
        this.guestUserInteractor = guestUserInteractor;
        this.uiEventLogger = uiEventLogger;
        this.userRestrictionChecker = userRestrictionChecker;
        this.processWrapper = processWrapper;
        this.userLogoutInteractor = userLogoutInteractor;
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) userRepository;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = userRepositoryImpl.userInfos;
        ?? r9 = new Flow() { // from class: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.userInfos = r9;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.combine(r9, userRepositoryImpl.selectedUserInfo, getActions(), userRepositoryImpl.userSwitcherSettings, new UserSwitcherInteractor$userRecords$1(this, null)), new UserSwitcherInteractor$userRecords$2(this, null));
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.userRecords = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, startedEagerly, new ArrayList());
        final UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = userRepositoryImpl.selectedUserInfo;
        this.selectedUserRecord = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserSwitcherInteractor this$0;

                /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, UserSwitcherInteractor userSwitcherInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = userSwitcherInteractor;
                }

                /* JADX WARN: Code restructure failed: missing block: B:21:0x005a, code lost:
                
                    if (r5.emit(r7, r0) == r1) goto L22;
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
                    Object objAccess$toRecord = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(objAccess$toRecord);
                        UserInfo userInfo = (UserInfo) obj;
                        int i3 = userInfo.id;
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector2;
                        anonymousClass1.label = 1;
                        objAccess$toRecord = UserSwitcherInteractor.access$toRecord(this.this$0, userInfo, i3, anonymousClass1);
                        if (objAccess$toRecord != coroutineSingletons) {
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(objAccess$toRecord);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(objAccess$toRecord);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = userRepositoryImpl$special$$inlined$map$2.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, null);
        this.isGuestUserAutoCreated = guestUserInteractor.isGuestUserAutoCreated;
        this.isGuestUserResetting = guestUserInteractor.isGuestUserResetting;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._dialogShowRequests = stateFlowImplMutableStateFlow;
        this.dialogShowRequests = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._dialogDismissRequests = stateFlowImplMutableStateFlow2;
        this.dialogDismissRequests = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        this.keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardGoingAway() {
                int i = UserSwitcherInteractor.$r8$clinit;
                this.this$0.dismissDialog();
            }
        };
        refreshUsersScheduler.refreshIfNotPaused();
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(telephonyInteractor.callState), new AnonymousClass1(null)), coroutineScope);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.USER_INFO_CHANGED", "android.intent.action.USER_SWITCHED", "android.intent.action.USER_STOPPED", "android.intent.action.USER_UNLOCKED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, intentFilter, UserHandle.SYSTEM, new UserSwitcherInteractor$$ExternalSyntheticLambda0(), 12), com.android.systemui.util.kotlin.FlowKt.pairwise(userRepositoryImpl.selectedUserInfo, null), new AnonymousClass4(null)), new AnonymousClass5(null)), coroutineScope);
        restartSecondaryService(userRepositoryImpl.getSelectedUserInfo().id);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(keyguardUpdateMonitor, null), 7);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$onBroadcastReceived(UserSwitcherInteractor userSwitcherInteractor, Intent intent, UserInfo userInfo, ContinuationImpl continuationImpl) {
        UserSwitcherInteractor$onBroadcastReceived$1 userSwitcherInteractor$onBroadcastReceived$1;
        String str;
        userSwitcherInteractor.getClass();
        if (continuationImpl instanceof UserSwitcherInteractor$onBroadcastReceived$1) {
            userSwitcherInteractor$onBroadcastReceived$1 = (UserSwitcherInteractor$onBroadcastReceived$1) continuationImpl;
            int i = userSwitcherInteractor$onBroadcastReceived$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                userSwitcherInteractor$onBroadcastReceived$1.label = i - Integer.MIN_VALUE;
            } else {
                userSwitcherInteractor$onBroadcastReceived$1 = new UserSwitcherInteractor$onBroadcastReceived$1(userSwitcherInteractor, continuationImpl);
            }
        }
        Object obj = userSwitcherInteractor$onBroadcastReceived$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = userSwitcherInteractor$onBroadcastReceived$1.label;
        boolean z = true;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            String action = intent.getAction();
            if (action != null) {
                switch (action.hashCode()) {
                    case -201513518:
                        str = "android.intent.action.USER_INFO_CHANGED";
                        action.equals(str);
                        break;
                    case -19011148:
                        str = "android.intent.action.LOCALE_CHANGED";
                        action.equals(str);
                        break;
                    case 833559602:
                        if (action.equals("android.intent.action.USER_UNLOCKED") && intent.getIntExtra("android.intent.extra.user_handle", -10000) != 0) {
                            z = false;
                            break;
                        }
                        break;
                    case 959232034:
                        if (action.equals("android.intent.action.USER_SWITCHED")) {
                            userSwitcherInteractor.dismissDialog();
                            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                            if (userInfo == null || userInfo.id != intExtra) {
                                CoroutineTracingKt.launchTraced$default(userSwitcherInteractor.applicationScope, null, null, new UserSwitcherInteractor$notifyCallbacks$1(userSwitcherInteractor, null), 7);
                                userSwitcherInteractor.restartSecondaryService(intExtra);
                            }
                            GuestUserInteractor guestUserInteractor = userSwitcherInteractor.guestUserInteractor;
                            if (guestUserInteractor.isGuestUserAutoCreated) {
                                userSwitcherInteractor$onBroadcastReceived$1.L$0 = userSwitcherInteractor;
                                userSwitcherInteractor$onBroadcastReceived$1.label = 1;
                                if (guestUserInteractor.guaranteePresent(userSwitcherInteractor$onBroadcastReceived$1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            }
                        }
                        break;
                }
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            userSwitcherInteractor = (UserSwitcherInteractor) userSwitcherInteractor$onBroadcastReceived$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        if (z) {
            RefreshUsersScheduler refreshUsersScheduler = userSwitcherInteractor.refreshUsersScheduler;
            refreshUsersScheduler.getClass();
            RefreshUsersScheduler$unpauseAndRefresh$1 refreshUsersScheduler$unpauseAndRefresh$1 = new RefreshUsersScheduler$unpauseAndRefresh$1(refreshUsersScheduler, null);
            CoroutineTracingKt.launchTraced$default(refreshUsersScheduler.applicationScope, refreshUsersScheduler.mainDispatcher, null, refreshUsersScheduler$unpauseAndRefresh$1, 5);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$toRecord(UserSwitcherInteractor userSwitcherInteractor, UserInfo userInfo, int i, ContinuationImpl continuationImpl) throws Throwable {
        UserSwitcherInteractor$toRecord$1 userSwitcherInteractor$toRecord$1;
        UserInfo userInfo2;
        Context context;
        UserManager userManager;
        int i2;
        Bitmap userIcon;
        userSwitcherInteractor.getClass();
        if (continuationImpl instanceof UserSwitcherInteractor$toRecord$1) {
            userSwitcherInteractor$toRecord$1 = (UserSwitcherInteractor$toRecord$1) continuationImpl;
            int i3 = userSwitcherInteractor$toRecord$1.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                userSwitcherInteractor$toRecord$1.label = i3 - Integer.MIN_VALUE;
            } else {
                userSwitcherInteractor$toRecord$1 = new UserSwitcherInteractor$toRecord$1(userSwitcherInteractor, continuationImpl);
            }
        }
        Object obj = userSwitcherInteractor$toRecord$1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = userSwitcherInteractor$toRecord$1.label;
        if (i4 == 0) {
            ResultKt.throwOnFailure(obj);
            LegacyUserDataHelper legacyUserDataHelper = LegacyUserDataHelper.INSTANCE;
            Context context2 = userSwitcherInteractor.applicationContext;
            UserManager userManager2 = userSwitcherInteractor.manager;
            int i5 = userInfo.id == i ? 1 : 0;
            userSwitcherInteractor$toRecord$1.L$0 = userInfo;
            userSwitcherInteractor$toRecord$1.L$1 = context2;
            userSwitcherInteractor$toRecord$1.L$2 = userManager2;
            userSwitcherInteractor$toRecord$1.I$0 = i5;
            userSwitcherInteractor$toRecord$1.label = 1;
            Object objCanSwitchUsers = userSwitcherInteractor.canSwitchUsers(i, userSwitcherInteractor$toRecord$1, false);
            if (objCanSwitchUsers == obj2) {
                return obj2;
            }
            userInfo2 = userInfo;
            context = context2;
            userManager = userManager2;
            obj = objCanSwitchUsers;
            i2 = i5;
        } else {
            if (i4 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i2 = userSwitcherInteractor$toRecord$1.I$0;
            userManager = (UserManager) userSwitcherInteractor$toRecord$1.L$2;
            context = (Context) userSwitcherInteractor$toRecord$1.L$1;
            UserInfo userInfo3 = (UserInfo) userSwitcherInteractor$toRecord$1.L$0;
            ResultKt.throwOnFailure(obj);
            userInfo2 = userInfo3;
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean z = i2 != 0;
        LegacyUserDataHelper legacyUserDataHelper2 = LegacyUserDataHelper.INSTANCE;
        boolean zIsGuest = userInfo2.isGuest();
        LegacyUserDataHelper.INSTANCE.getClass();
        Bitmap bitmapCreateScaledBitmap = null;
        if (!userInfo2.isGuest() && UserManager.supportsMultipleUsers() && (userIcon = userManager.getUserIcon(userInfo2.id)) != null) {
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.max_avatar_size);
            bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(userIcon, dimensionPixelSize, dimensionPixelSize, true);
        }
        return new UserRecord(userInfo2, bitmapCreateScaledBitmap, zIsGuest, z, false, false, zBooleanValue || (z && !zIsGuest), false, false, null, false, 1968, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x007b, code lost:
    
        if (r9 == r4) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0170, code lost:
    
        if (r2 == r4) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0172, code lost:
    
        return r4;
     */
    /* JADX WARN: Path cross not found for [B:28:0x00ca, B:45:0x0144], limit reached: 62 */
    /* JADX WARN: Path cross not found for [B:50:0x0152, B:26:0x00c6], limit reached: 62 */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001c  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:61:0x0170 -> B:63:0x0173). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$toUserModels(UserSwitcherInteractor userSwitcherInteractor, List list, int i, boolean z, ContinuationImpl continuationImpl) throws Throwable {
        UserSwitcherInteractor$toUserModels$1 userSwitcherInteractor$toUserModels$1;
        List list2;
        boolean z2;
        Object objCanSwitchUsers;
        Collection arrayList;
        int i2;
        boolean z3;
        UserSwitcherInteractor userSwitcherInteractor2;
        boolean z4;
        Iterator it;
        UserSwitcherInteractor userSwitcherInteractor3 = userSwitcherInteractor;
        int i3 = i;
        userSwitcherInteractor3.getClass();
        if (continuationImpl instanceof UserSwitcherInteractor$toUserModels$1) {
            userSwitcherInteractor$toUserModels$1 = (UserSwitcherInteractor$toUserModels$1) continuationImpl;
            int i4 = userSwitcherInteractor$toUserModels$1.label;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                userSwitcherInteractor$toUserModels$1.label = i4 - Integer.MIN_VALUE;
            } else {
                userSwitcherInteractor$toUserModels$1 = new UserSwitcherInteractor$toUserModels$1(userSwitcherInteractor3, continuationImpl);
            }
        }
        Object userModel = userSwitcherInteractor$toUserModels$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i5 = userSwitcherInteractor$toUserModels$1.label;
        int i6 = 2;
        boolean z5 = true;
        if (i5 == 0) {
            ResultKt.throwOnFailure(userModel);
            userSwitcherInteractor$toUserModels$1.L$0 = userSwitcherInteractor3;
            list2 = list;
            userSwitcherInteractor$toUserModels$1.L$1 = list2;
            userSwitcherInteractor$toUserModels$1.I$0 = i3;
            z2 = z;
            userSwitcherInteractor$toUserModels$1.Z$0 = z2;
            userSwitcherInteractor$toUserModels$1.label = 1;
            objCanSwitchUsers = userSwitcherInteractor3.canSwitchUsers(i3, userSwitcherInteractor$toUserModels$1, false);
        } else if (i5 == 1) {
            boolean z6 = userSwitcherInteractor$toUserModels$1.Z$0;
            i3 = userSwitcherInteractor$toUserModels$1.I$0;
            List list3 = (List) userSwitcherInteractor$toUserModels$1.L$1;
            UserSwitcherInteractor userSwitcherInteractor4 = (UserSwitcherInteractor) userSwitcherInteractor$toUserModels$1.L$0;
            ResultKt.throwOnFailure(userModel);
            z2 = z6;
            userSwitcherInteractor3 = userSwitcherInteractor4;
            objCanSwitchUsers = userModel;
            list2 = list3;
        } else {
            if (i5 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            z4 = userSwitcherInteractor$toUserModels$1.Z$1;
            z3 = userSwitcherInteractor$toUserModels$1.Z$0;
            i2 = userSwitcherInteractor$toUserModels$1.I$0;
            it = (Iterator) userSwitcherInteractor$toUserModels$1.L$2;
            arrayList = (Collection) userSwitcherInteractor$toUserModels$1.L$1;
            userSwitcherInteractor2 = (UserSwitcherInteractor) userSwitcherInteractor$toUserModels$1.L$0;
            ResultKt.throwOnFailure(userModel);
            UserModel userModel2 = (UserModel) userModel;
            if (userModel2 != null) {
                arrayList.add(userModel2);
            }
            i6 = 2;
            z5 = true;
            if (it.hasNext()) {
                return (List) arrayList;
            }
            UserInfo userInfo = (UserInfo) it.next();
            userSwitcherInteractor$toUserModels$1.L$0 = userSwitcherInteractor2;
            userSwitcherInteractor$toUserModels$1.L$1 = arrayList;
            userSwitcherInteractor$toUserModels$1.L$2 = it;
            userSwitcherInteractor$toUserModels$1.I$0 = i2;
            userSwitcherInteractor$toUserModels$1.Z$0 = z3;
            userSwitcherInteractor$toUserModels$1.Z$1 = z4;
            userSwitcherInteractor$toUserModels$1.label = i6;
            userSwitcherInteractor2.getClass();
            if (!z3 && !userInfo.isPrimary()) {
                if (QpRune.QUICK_MUM_TWO_PHONE) {
                    boolean zSupportsMultipleUsers = UserManager.supportsMultipleUsers();
                    SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                    Pair pair = new Pair(Boolean.valueOf(settingsHelper.isTwoPhoneRegistered()), Boolean.valueOf(settingsHelper.hasTwoPhoneAccount()));
                    boolean zBooleanValue = ((Boolean) pair.component1()).booleanValue();
                    boolean zBooleanValue2 = ((Boolean) pair.component2()).booleanValue();
                    boolean z7 = (zSupportsMultipleUsers && zBooleanValue && zBooleanValue2) ? z5 : false;
                    StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("isTwoPhoneSettingOff: ", " [supportMultipleUsers:", " isTwoPhoneRegistered:", z7, zSupportsMultipleUsers);
                    sbM.append(zBooleanValue);
                    sbM.append(" hasTwoPhoneAccount:");
                    sbM.append(zBooleanValue2);
                    sbM.append("]");
                    Log.d("UserSwitcherInteractor", sbM.toString());
                    if (z7 && userInfo.isEnabled() && (userInfo.isGuest() || userInfo.supportsSwitchToByUser())) {
                        userModel = userSwitcherInteractor2.toUserModel(userInfo, i2, z4, userSwitcherInteractor$toUserModels$1);
                        if (userModel != CoroutineSingletons.COROUTINE_SUSPENDED) {
                            userModel = (UserModel) userModel;
                        }
                    }
                }
                userModel = null;
            } else if (userInfo.isEnabled() && (userInfo.isGuest() || userInfo.supportsSwitchToByUser())) {
                userModel = userSwitcherInteractor2.toUserModel(userInfo, i2, z4, userSwitcherInteractor$toUserModels$1);
                if (userModel != CoroutineSingletons.COROUTINE_SUSPENDED) {
                    userModel = (UserModel) userModel;
                }
            } else {
                userModel = null;
            }
        }
        boolean zBooleanValue3 = ((Boolean) objCanSwitchUsers).booleanValue();
        List listSortedWith = CollectionsKt___CollectionsKt.sortedWith(list2, new Comparator() { // from class: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$toUserModels$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((UserInfo) obj).isGuest()), Boolean.valueOf(((UserInfo) obj2).isGuest()));
            }
        });
        arrayList = new ArrayList();
        boolean z8 = z2;
        i2 = i3;
        z3 = z8;
        userSwitcherInteractor2 = userSwitcherInteractor3;
        z4 = zBooleanValue3;
        it = listSortedWith.iterator();
        if (it.hasNext()) {
        }
    }

    public final void addCallback(UserCallback userCallback) {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C11281(userCallback, null), 7);
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a4, code lost:
    
        if (r11 == r1) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object canSwitchUsers(int i, ContinuationImpl continuationImpl, boolean z) throws Throwable {
        C11291 c11291;
        UserSwitcherInteractor userSwitcherInteractor;
        int i2;
        boolean z2;
        if (continuationImpl instanceof C11291) {
            c11291 = (C11291) continuationImpl;
            int i3 = c11291.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                c11291.label = i3 - Integer.MIN_VALUE;
            } else {
                c11291 = new C11291(continuationImpl);
            }
        }
        Object objWithContext = c11291.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = c11291.label;
        boolean z3 = false;
        if (i4 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            UserSwitcherInteractor$canSwitchUsers$isHeadlessSystemUserMode$1 userSwitcherInteractor$canSwitchUsers$isHeadlessSystemUserMode$1 = new UserSwitcherInteractor$canSwitchUsers$isHeadlessSystemUserMode$1(this, null);
            c11291.L$0 = this;
            c11291.I$0 = i;
            c11291.Z$0 = z;
            c11291.label = 1;
            objWithContext = BuildersKt.withContext(this.backgroundDispatcher, userSwitcherInteractor$canSwitchUsers$isHeadlessSystemUserMode$1, c11291);
            if (objWithContext != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i4 != 1) {
            if (i4 != 2) {
                if (i4 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(objWithContext);
                if (((Number) objWithContext).intValue() == 0) {
                    z3 = true;
                }
                return Boolean.valueOf(z3);
            }
            i2 = c11291.I$0;
            userSwitcherInteractor = (UserSwitcherInteractor) c11291.L$0;
            ResultKt.throwOnFailure(objWithContext);
            if (((Boolean) objWithContext).booleanValue()) {
                z2 = false;
                if (z2) {
                }
                return Boolean.valueOf(z3);
            }
            UserSwitcherInteractor userSwitcherInteractor2 = userSwitcherInteractor;
            i = i2;
            this = userSwitcherInteractor2;
            int i5 = i;
            userSwitcherInteractor = this;
            i2 = i5;
            z2 = true;
            if (z2) {
                CoroutineDispatcher coroutineDispatcher = userSwitcherInteractor.backgroundDispatcher;
                AnonymousClass2 anonymousClass2 = userSwitcherInteractor.new AnonymousClass2(i2, null);
                c11291.L$0 = null;
                c11291.label = 3;
                objWithContext = BuildersKt.withContext(coroutineDispatcher, anonymousClass2, c11291);
            }
            return Boolean.valueOf(z3);
        }
        z = c11291.Z$0;
        i = c11291.I$0;
        this = (UserSwitcherInteractor) c11291.L$0;
        ResultKt.throwOnFailure(objWithContext);
        boolean zBooleanValue = ((Boolean) objWithContext).booleanValue();
        if (z && zBooleanValue) {
            c11291.L$0 = this;
            c11291.I$0 = i;
            c11291.label = 2;
            objWithContext = this.isAnyUserUnlocked(c11291);
            if (objWithContext != coroutineSingletons) {
                int i6 = i;
                userSwitcherInteractor = this;
                i2 = i6;
                if (((Boolean) objWithContext).booleanValue()) {
                }
            }
            return coroutineSingletons;
        }
        int i52 = i;
        userSwitcherInteractor = this;
        i2 = i52;
        z2 = true;
        if (z2) {
        }
        return Boolean.valueOf(z3);
    }

    public final void dismissDialog() {
        this._dialogDismissRequests.setValue(Unit.INSTANCE);
    }

    public final void executeAction(UserActionModel userActionModel, final DialogShowerImpl dialogShowerImpl) {
        int i = WhenMappings.$EnumSwitchMapping$0[userActionModel.ordinal()];
        if (i == 1) {
            this.uiEventLogger.log(MultiUserActionsEvent.CREATE_GUEST_FROM_USER_SWITCHER);
            C11301 c11301 = new C11301(this);
            C11312 c11312 = new C11312(this);
            Function1 function1 = new Function1() { // from class: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    int iIntValue = ((Integer) obj).intValue();
                    int i2 = UserSwitcherInteractor.$r8$clinit;
                    this.f$0.selectUser(iIntValue, dialogShowerImpl);
                    return Unit.INSTANCE;
                }
            };
            GuestUserInteractor guestUserInteractor = this.guestUserInteractor;
            guestUserInteractor.getClass();
            CoroutineTracingKt.launchTraced$default(guestUserInteractor.applicationScope, null, null, new GuestUserInteractor$createAndSwitchTo$1(guestUserInteractor, c11301, c11312, function1, null), 7);
            return;
        }
        if (i == 2) {
            this.uiEventLogger.log(MultiUserActionsEvent.CREATE_USER_FROM_USER_SWITCHER);
            UserInfo selectedUserInfo = ((UserRepositoryImpl) this.repository).getSelectedUserInfo();
            dismissDialog();
            Context context = this.applicationContext;
            boolean zIsKeyguardShowing = this.keyguardInteractor.isKeyguardShowing();
            int i2 = CreateUserActivity.$r8$clinit;
            Intent intent = new Intent(context, (Class<?>) CreateUserActivity.class);
            intent.addFlags(335544320);
            intent.putExtra("extra_is_keyguard_showing", zIsKeyguardShowing);
            this.activityStarter.startActivity(intent, true, null, true, selectedUserInfo.getUserHandle());
            return;
        }
        ActivityStarter activityStarter = this.activityStarter;
        if (i == 3) {
            this.uiEventLogger.log(MultiUserActionsEvent.CREATE_RESTRICTED_USER_FROM_USER_SWITCHER);
            dismissDialog();
            activityStarter.startActivity(new Intent().setAction("android.os.action.CREATE_SUPERVISED_USER").setPackage(this.applicationContext.getString(android.R.string.face_recalibrate_notification_content)).addFlags(268435456), true);
        } else if (i == 4) {
            activityStarter.startActivity(new Intent("android.settings.USER_SETTINGS"), true);
        } else {
            if (i != 5) {
                throw new NoWhenBranchMatchedException();
            }
            dismissDialog();
            CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C11324(null), 7);
        }
    }

    public final void exitGuestUser(int i, int i2, boolean z) {
        C11331 c11331 = new C11331(this);
        C11342 c11342 = new C11342(this);
        AnonymousClass3 anonymousClass3 = new AnonymousClass3(this);
        GuestUserInteractor guestUserInteractor = this.guestUserInteractor;
        UserInfo selectedUserInfo = ((UserRepositoryImpl) guestUserInteractor.repository).getSelectedUserInfo();
        int i3 = selectedUserInfo.id;
        if (i3 != i) {
            Log.w("GuestUserInteractor", MutableVectorKt$$ExternalSyntheticOutline0.m(i, i3, "User requesting to start a new session (", ") is not current user (", ")"));
            return;
        }
        if (selectedUserInfo.isGuest()) {
            CoroutineTracingKt.launchTraced$default(guestUserInteractor.applicationScope, null, null, new GuestUserInteractor$exit$1(guestUserInteractor, i2, selectedUserInfo, z, c11331, c11342, anonymousClass3, null), 7);
        } else {
            Log.w("GuestUserInteractor", "User requesting to start a new session (" + i + ") is not a guest");
        }
    }

    public final Flow getActions() {
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) this.repository;
        return FlowKt.flowOn(FlowKt.combine(userRepositoryImpl.selectedUserInfo, this.userInfos, userRepositoryImpl.userSwitcherSettings, this.keyguardInteractor.isKeyguardShowing, new UserSwitcherInteractor$actions$1(this, null)), this.backgroundDispatcher);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getUserImage(int i, ContinuationImpl continuationImpl, boolean z) throws Throwable {
        C11351 c11351;
        if (continuationImpl instanceof C11351) {
            c11351 = (C11351) continuationImpl;
            int i2 = c11351.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c11351.label = i2 - Integer.MIN_VALUE;
            } else {
                c11351 = new C11351(continuationImpl);
            }
        }
        Object objWithContext = c11351.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = c11351.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            if (z || !UserManager.supportsMultipleUsers()) {
                Drawable drawable = this.applicationContext.getDrawable(R.drawable.ic_account_circle);
                if (drawable != null) {
                    return drawable;
                }
                throw new IllegalStateException("Required value was null.");
            }
            UserSwitcherInteractor$getUserImage$userIcon$1 userSwitcherInteractor$getUserImage$userIcon$1 = new UserSwitcherInteractor$getUserImage$userIcon$1(this, i, null);
            c11351.L$0 = this;
            c11351.I$0 = i;
            c11351.label = 1;
            objWithContext = BuildersKt.withContext(this.backgroundDispatcher, userSwitcherInteractor$getUserImage$userIcon$1, c11351);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = c11351.I$0;
            this = (UserSwitcherInteractor) c11351.L$0;
            ResultKt.throwOnFailure(objWithContext);
        }
        Bitmap bitmap = (Bitmap) objWithContext;
        return bitmap != null ? new BitmapDrawable(bitmap) : UserIcons.getDefaultUserIcon(this.applicationContext.getResources(), i, false);
    }

    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 getUsers() {
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) this.repository;
        return FlowKt.combine(this.userInfos, userRepositoryImpl.selectedUserInfo, userRepositoryImpl.userSwitcherSettings, new UserSwitcherInteractor$users$1(this, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0058 A[PHI: r9 r10
      0x0058: PHI (r9v3 java.util.Iterator) = (r9v1 java.util.Iterator), (r9v4 java.util.Iterator) binds: [B:19:0x0051, B:33:0x008d] A[DONT_GENERATE, DONT_INLINE]
      0x0058: PHI (r10v7 com.android.systemui.user.domain.interactor.UserSwitcherInteractor) = 
      (r10v6 com.android.systemui.user.domain.interactor.UserSwitcherInteractor)
      (r10v9 com.android.systemui.user.domain.interactor.UserSwitcherInteractor)
     binds: [B:19:0x0051, B:33:0x008d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x007d -> B:28:0x0080). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object isAnyUserUnlocked(ContinuationImpl continuationImpl) throws Throwable {
        C11361 c11361;
        UserSwitcherInteractor userSwitcherInteractor;
        Iterator it;
        boolean z;
        if (continuationImpl instanceof C11361) {
            c11361 = (C11361) continuationImpl;
            int i = c11361.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c11361.label = i - Integer.MIN_VALUE;
            } else {
                c11361 = new C11361(continuationImpl);
            }
        }
        Object obj = c11361.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c11361.label;
        boolean z2 = false;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            List users = this.manager.getUsers(true, true, true);
            if (!(users instanceof Collection) || !users.isEmpty()) {
                Iterator it2 = users.iterator();
                userSwitcherInteractor = this;
                it = it2;
                if (it.hasNext()) {
                }
            }
            return Boolean.valueOf(z2);
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        it = (Iterator) c11361.L$1;
        UserSwitcherInteractor userSwitcherInteractor2 = (UserSwitcherInteractor) c11361.L$0;
        ResultKt.throwOnFailure(obj);
        if (((Boolean) obj).booleanValue()) {
            userSwitcherInteractor = userSwitcherInteractor2;
            z = false;
        } else {
            userSwitcherInteractor = userSwitcherInteractor2;
            z = true;
        }
        if (z) {
            z2 = true;
        } else if (it.hasNext()) {
            UserInfo userInfo = (UserInfo) it.next();
            if (userInfo.id != 0) {
                CoroutineDispatcher coroutineDispatcher = userSwitcherInteractor.backgroundDispatcher;
                UserSwitcherInteractor$isAnyUserUnlocked$2$1 userSwitcherInteractor$isAnyUserUnlocked$2$1 = new UserSwitcherInteractor$isAnyUserUnlocked$2$1(userSwitcherInteractor, userInfo, null);
                c11361.L$0 = userSwitcherInteractor;
                c11361.L$1 = it;
                c11361.label = 1;
                Object objWithContext = BuildersKt.withContext(coroutineDispatcher, userSwitcherInteractor$isAnyUserUnlocked$2$1, c11361);
                if (objWithContext == coroutineSingletons) {
                    return coroutineSingletons;
                }
                userSwitcherInteractor2 = userSwitcherInteractor;
                obj = objWithContext;
                if (((Boolean) obj).booleanValue()) {
                }
                if (z) {
                }
            } else {
                z = false;
                if (z) {
                }
            }
        }
        return Boolean.valueOf(z2);
    }

    public final void removeCallback(UserCallback userCallback) {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C11371(userCallback, null), 7);
    }

    public final void removeGuestUser(int i) {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C11381(i, -10000, null), 7);
    }

    public final void restartSecondaryService(int i) {
        List aliveUsers = this.manager.getAliveUsers();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(aliveUsers, 10));
        Iterator it = aliveUsers.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
        }
        if (arrayList.contains(Integer.valueOf(i))) {
            Intent intent = new Intent(this.applicationContext, (Class<?>) SystemUISecondaryUserService.class);
            UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) this.repository;
            int i2 = userRepositoryImpl.secondaryUserId;
            if (i2 != -10000) {
                this.applicationContext.stopServiceAsUser(intent, UserHandle.of(i2));
                userRepositoryImpl.secondaryUserId = -10000;
            }
            this.processWrapper.getClass();
            if (i == Process.myUserHandle().getIdentifier() || ProcessWrapper.isSystemUser()) {
                return;
            }
            this.applicationContext.startServiceAsUser(intent, UserHandle.of(i));
            userRepositoryImpl.secondaryUserId = i;
        }
    }

    public final void selectUser(int i, DialogShowerImpl dialogShowerImpl) {
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) this.repository;
        UserInfo selectedUserInfo = userRepositoryImpl.getSelectedUserInfo();
        int i2 = selectedUserInfo.id;
        KeyguardInteractor keyguardInteractor = this.keyguardInteractor;
        if (i == i2 && selectedUserInfo.isGuest()) {
            showDialog(new ShowDialogRequestModel.ShowExitGuestDialog(selectedUserInfo.id, userRepositoryImpl.lastSelectedNonGuestUserId, selectedUserInfo.isEphemeral(), keyguardInteractor.isKeyguardShowing(), new C11391(this), dialogShowerImpl));
        } else {
            if (selectedUserInfo.isGuest()) {
                showDialog(new ShowDialogRequestModel.ShowExitGuestDialog(selectedUserInfo.id, i, selectedUserInfo.isEphemeral(), keyguardInteractor.isKeyguardShowing(), new C11402(this), dialogShowerImpl));
                return;
            }
            if (dialogShowerImpl != null) {
                dialogShowerImpl.dismiss();
            }
            switchUser(i);
        }
    }

    public final void showDialog(ShowDialogRequestModel showDialogRequestModel) {
        this._dialogShowRequests.setValue(showDialogRequestModel);
    }

    public final void showUserSwitcher(Expandable expandable) {
        if (((FeatureFlagsClassicRelease) this.featureFlags).isEnabled(Flags.FULL_SCREEN_USER_SWITCHER)) {
            showDialog(new ShowDialogRequestModel.ShowUserSwitcherFullscreenDialog(expandable));
        } else {
            showDialog(new ShowDialogRequestModel.ShowUserSwitcherDialog(expandable));
        }
    }

    public final void switchUser(final int i) {
        RefreshUsersScheduler refreshUsersScheduler = this.refreshUsersScheduler;
        refreshUsersScheduler.getClass();
        CoroutineTracingKt.launchTraced$default(refreshUsersScheduler.applicationScope, refreshUsersScheduler.mainDispatcher, null, new RefreshUsersScheduler$pause$1(refreshUsersScheduler, null), 5);
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C11411(new Runnable() { // from class: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$switchUser$runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    this.this$0.activityManager.switchUser(i);
                } catch (RemoteException e) {
                    Log.e("UserSwitcherInteractor", "Couldn't switch user.", e);
                }
            }
        }, null), 7);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* JADX WARN: Type inference failed for: r17v0, types: [com.android.systemui.user.domain.interactor.UserSwitcherInteractor] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.common.shared.model.Text] */
    /* JADX WARN: Type inference failed for: r4v6, types: [com.android.systemui.common.shared.model.Text] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object toUserModel(UserInfo userInfo, int i, boolean z, ContinuationImpl continuationImpl) throws Throwable {
        C11421 c11421;
        int i2;
        int i3;
        Text.Loaded loaded;
        int i4;
        int i5;
        Text.Loaded loaded2;
        boolean z2 = z;
        if (continuationImpl instanceof C11421) {
            c11421 = (C11421) continuationImpl;
            int i6 = c11421.label;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                c11421.label = i6 - Integer.MIN_VALUE;
            } else {
                c11421 = new C11421(continuationImpl);
            }
        }
        Object obj = c11421.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i7 = c11421.label;
        if (i7 != 0) {
            if (i7 == 1) {
                int i8 = c11421.I$1;
                i5 = c11421.I$0;
                z2 = c11421.Z$0;
                ?? r4 = (Text) c11421.L$0;
                ResultKt.throwOnFailure(obj);
                i4 = i8;
                loaded2 = r4;
                return new UserModel(i4, loaded2, (Drawable) obj, i5 == 0, z2, true);
            }
            if (i7 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i9 = c11421.I$1;
            i3 = c11421.I$0;
            z2 = c11421.Z$0;
            ?? r42 = (Text) c11421.L$0;
            ResultKt.throwOnFailure(obj);
            i2 = i9;
            loaded = r42;
            return new UserModel(i2, loaded, (Drawable) obj, i3 == 0, (z2 && i3 == 0) ? false : true, false);
        }
        ResultKt.throwOnFailure(obj);
        int i10 = userInfo.id;
        int i11 = i10 == i ? 1 : 0;
        if (userInfo.isGuest()) {
            Text.Loaded loaded3 = new Text.Loaded(userInfo.name);
            c11421.L$0 = loaded3;
            c11421.Z$0 = z2;
            c11421.I$0 = i11;
            c11421.I$1 = i10;
            c11421.label = 1;
            Object userImage = getUserImage(i10, c11421, true);
            if (userImage != coroutineSingletons) {
                i4 = i10;
                i5 = i11;
                loaded2 = loaded3;
                obj = userImage;
                return new UserModel(i4, loaded2, (Drawable) obj, i5 == 0, z2, true);
            }
        } else {
            Text.Loaded loaded4 = new Text.Loaded(userInfo.name);
            c11421.L$0 = loaded4;
            c11421.Z$0 = z2;
            c11421.I$0 = i11;
            c11421.I$1 = i10;
            c11421.label = 2;
            Object userImage2 = getUserImage(i10, c11421, false);
            if (userImage2 != coroutineSingletons) {
                i2 = i10;
                i3 = i11;
                loaded = loaded4;
                obj = userImage2;
                if (z2) {
                }
                return new UserModel(i2, loaded, (Drawable) obj, i3 == 0, (z2 && i3 == 0) ? false : true, false);
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001e  */
    /* JADX WARN: Type inference failed for: r0v9, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$toRecord(UserSwitcherInteractor userSwitcherInteractor, UserActionModel userActionModel, int i, boolean z, ContinuationImpl continuationImpl) throws Throwable {
        UserSwitcherInteractor$toRecord$2 userSwitcherInteractor$toRecord$2;
        Context context;
        boolean z2;
        boolean z3;
        Context context2;
        boolean z4;
        UserSwitcherInteractor userSwitcherInteractor2 = userSwitcherInteractor;
        UserActionModel userActionModel2 = userActionModel;
        int i2 = i;
        userSwitcherInteractor2.getClass();
        if (continuationImpl instanceof UserSwitcherInteractor$toRecord$2) {
            userSwitcherInteractor$toRecord$2 = (UserSwitcherInteractor$toRecord$2) continuationImpl;
            int i3 = userSwitcherInteractor$toRecord$2.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                userSwitcherInteractor$toRecord$2.label = i3 - Integer.MIN_VALUE;
            } else {
                userSwitcherInteractor$toRecord$2 = new UserSwitcherInteractor$toRecord$2(userSwitcherInteractor2, continuationImpl);
            }
        }
        Object obj = userSwitcherInteractor$toRecord$2.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = userSwitcherInteractor$toRecord$2.label;
        if (i4 == 0) {
            ResultKt.throwOnFailure(obj);
            context = userSwitcherInteractor2.applicationContext;
            if (userActionModel2 != UserActionModel.SIGN_OUT) {
                userSwitcherInteractor$toRecord$2.L$0 = userSwitcherInteractor2;
                userSwitcherInteractor$toRecord$2.L$1 = context;
                userSwitcherInteractor$toRecord$2.L$2 = userActionModel2;
                userSwitcherInteractor$toRecord$2.I$0 = i2;
                z3 = z;
                userSwitcherInteractor$toRecord$2.I$1 = z3 ? 1 : 0;
                userSwitcherInteractor$toRecord$2.label = 1;
                Object objCanSwitchUsers = userSwitcherInteractor2.canSwitchUsers(i2, userSwitcherInteractor$toRecord$2, true);
                if (objCanSwitchUsers == coroutineSingletons) {
                    return coroutineSingletons;
                }
                context2 = context;
                obj = objCanSwitchUsers;
            } else {
                z2 = z;
                context2 = context;
                z4 = true;
                boolean z5 = z2;
                UserRestrictionChecker userRestrictionChecker = userSwitcherInteractor2.userRestrictionChecker;
                LegacyUserDataHelper legacyUserDataHelper = LegacyUserDataHelper.INSTANCE;
                boolean z6 = userActionModel2 == UserActionModel.ENTER_GUEST_MODE;
                boolean z7 = userActionModel2 == UserActionModel.ADD_USER;
                boolean z8 = userActionModel2 == UserActionModel.ADD_SUPERVISED_USER;
                boolean z9 = userActionModel2 == UserActionModel.SIGN_OUT;
                LegacyUserDataHelper.INSTANCE.getClass();
                userRestrictionChecker.getClass();
                RestrictedLockUtils.EnforcedAdmin enforcedAdminCheckIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(context2, "no_add_user", i2);
                return new UserRecord(null, null, z6, false, z7, z5, z4, z8, z9, (enforcedAdminCheckIfRestrictionEnforced == null || RestrictedLockUtilsInternal.hasBaseUserRestriction(context2, "no_add_user", i2)) ? null : enforcedAdminCheckIfRestrictionEnforced, userActionModel2 == UserActionModel.NAVIGATE_TO_USER_MANAGEMENT, 11, null);
            }
        } else {
            if (i4 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ?? r0 = userSwitcherInteractor$toRecord$2.I$1;
            int i5 = userSwitcherInteractor$toRecord$2.I$0;
            UserActionModel userActionModel3 = (UserActionModel) userSwitcherInteractor$toRecord$2.L$2;
            context2 = (Context) userSwitcherInteractor$toRecord$2.L$1;
            UserSwitcherInteractor userSwitcherInteractor3 = (UserSwitcherInteractor) userSwitcherInteractor$toRecord$2.L$0;
            ResultKt.throwOnFailure(obj);
            i2 = i5;
            userActionModel2 = userActionModel3;
            z3 = r0;
            userSwitcherInteractor2 = userSwitcherInteractor3;
        }
        z2 = z3;
        if (!((Boolean) obj).booleanValue() || (userSwitcherInteractor2.isGuestUserAutoCreated && userSwitcherInteractor2.isGuestUserResetting)) {
            z4 = false;
            boolean z52 = z2;
            UserRestrictionChecker userRestrictionChecker2 = userSwitcherInteractor2.userRestrictionChecker;
            LegacyUserDataHelper legacyUserDataHelper2 = LegacyUserDataHelper.INSTANCE;
            if (userActionModel2 == UserActionModel.ENTER_GUEST_MODE) {
            }
            if (userActionModel2 == UserActionModel.ADD_USER) {
            }
            if (userActionModel2 == UserActionModel.ADD_SUPERVISED_USER) {
            }
            if (userActionModel2 == UserActionModel.SIGN_OUT) {
            }
            LegacyUserDataHelper.INSTANCE.getClass();
            userRestrictionChecker2.getClass();
            RestrictedLockUtils.EnforcedAdmin enforcedAdminCheckIfRestrictionEnforced2 = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(context2, "no_add_user", i2);
            return new UserRecord(null, null, z6, false, z7, z52, z4, z8, z9, (enforcedAdminCheckIfRestrictionEnforced2 == null || RestrictedLockUtilsInternal.hasBaseUserRestriction(context2, "no_add_user", i2)) ? null : enforcedAdminCheckIfRestrictionEnforced2, userActionModel2 == UserActionModel.NAVIGATE_TO_USER_MANAGEMENT, 11, null);
        }
        context = context2;
        context2 = context;
        z4 = true;
        boolean z522 = z2;
        UserRestrictionChecker userRestrictionChecker22 = userSwitcherInteractor2.userRestrictionChecker;
        LegacyUserDataHelper legacyUserDataHelper22 = LegacyUserDataHelper.INSTANCE;
        if (userActionModel2 == UserActionModel.ENTER_GUEST_MODE) {
        }
        if (userActionModel2 == UserActionModel.ADD_USER) {
        }
        if (userActionModel2 == UserActionModel.ADD_SUPERVISED_USER) {
        }
        if (userActionModel2 == UserActionModel.SIGN_OUT) {
        }
        LegacyUserDataHelper.INSTANCE.getClass();
        userRestrictionChecker22.getClass();
        RestrictedLockUtils.EnforcedAdmin enforcedAdminCheckIfRestrictionEnforced22 = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(context2, "no_add_user", i2);
        return new UserRecord(null, null, z6, false, z7, z522, z4, z8, z9, (enforcedAdminCheckIfRestrictionEnforced22 == null || RestrictedLockUtilsInternal.hasBaseUserRestriction(context2, "no_add_user", i2)) ? null : enforcedAdminCheckIfRestrictionEnforced22, userActionModel2 == UserActionModel.NAVIGATE_TO_USER_MANAGEMENT, 11, null);
    }
}
