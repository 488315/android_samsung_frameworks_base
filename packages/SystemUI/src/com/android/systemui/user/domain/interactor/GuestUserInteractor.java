package com.android.systemui.user.domain.interactor;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.GuestResetOrExitSessionReceiver;
import com.android.systemui.GuestResumeSessionReceiver;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class GuestUserInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context applicationContext;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final DevicePolicyManager devicePolicyManager;
    public final DeviceProvisionedController deviceProvisionedController;
    public final boolean isGuestUserAutoCreated;
    public final boolean isGuestUserResetting;
    public final CoroutineDispatcher mainDispatcher;
    public final UserManager manager;
    public final RefreshUsersScheduler refreshUsersScheduler;
    public final UserRepository repository;
    public final UiEventLogger uiEventLogger;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.GuestUserInteractor$guaranteePresent$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            return GuestUserInteractor.this.guaranteePresent(this);
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.GuestUserInteractor$onDeviceBootCompleted$1, reason: invalid class name and case insensitive filesystem */
    final class C11261 extends SuspendLambda implements Function2 {
        Object L$0;
        int label;

        public C11261(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return GuestUserInteractor.this.new C11261(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11261) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x003c, code lost:
        
            if (r6.guaranteePresent(r5) == r0) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x007c, code lost:
        
            if (r6.guaranteePresent(r5) == r0) goto L26;
         */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0071  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            GuestUserInteractor guestUserInteractor;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(obj);
                guestUserInteractor = GuestUserInteractor.this;
                int i2 = GuestUserInteractor.$r8$clinit;
                if (guestUserInteractor.isDeviceAllowedToAddGuest()) {
                    GuestUserInteractor guestUserInteractor2 = GuestUserInteractor.this;
                    this.L$0 = null;
                    this.label = 3;
                }
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            GuestUserInteractor guestUserInteractor3 = GuestUserInteractor.this;
            int i3 = GuestUserInteractor.$r8$clinit;
            if (guestUserInteractor3.isDeviceAllowedToAddGuest()) {
                GuestUserInteractor guestUserInteractor4 = GuestUserInteractor.this;
                this.label = 1;
            } else {
                final GuestUserInteractor guestUserInteractor5 = GuestUserInteractor.this;
                this.L$0 = guestUserInteractor5;
                this.label = 2;
                final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                ((DeviceProvisionedControllerImpl) guestUserInteractor5.deviceProvisionedController).addCallback(new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.user.domain.interactor.GuestUserInteractor$onDeviceBootCompleted$1$1$callback$1
                    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
                    public final void onDeviceProvisionedChanged() {
                        int i4 = Result.$r8$clinit;
                        cancellableContinuationImpl.resumeWith(Unit.INSTANCE);
                        ((DeviceProvisionedControllerImpl) guestUserInteractor5.deviceProvisionedController).removeCallback(this);
                    }
                });
                if (cancellableContinuationImpl.getResult() != coroutineSingletons) {
                    guestUserInteractor = GuestUserInteractor.this;
                    int i22 = GuestUserInteractor.$r8$clinit;
                    if (guestUserInteractor.isDeviceAllowedToAddGuest()) {
                    }
                    return Unit.INSTANCE;
                }
            }
            return coroutineSingletons;
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.GuestUserInteractor$remove$1, reason: invalid class name and case insensitive filesystem */
    final class C11271 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        public C11271(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return GuestUserInteractor.this.remove(0, 0, null, null, null, this);
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.GuestUserInteractor$remove$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserInfo $currentUser;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(UserInfo userInfo, Continuation continuation) {
            super(2, continuation);
            this.$currentUser = userInfo;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return GuestUserInteractor.this.new AnonymousClass3(this.$currentUser, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return new Integer(GuestUserInteractor.this.manager.removeUserWhenPossible(UserHandle.of(this.$currentUser.id), false));
        }
    }

    /* renamed from: com.android.systemui.user.domain.interactor.GuestUserInteractor$remove$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserInfo $currentUser;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(UserInfo userInfo, Continuation continuation) {
            super(2, continuation);
            this.$currentUser = userInfo;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return GuestUserInteractor.this.new AnonymousClass4(this.$currentUser, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return new Integer(GuestUserInteractor.this.manager.removeUserWhenPossible(UserHandle.of(this.$currentUser.id), false));
        }
    }

    static {
        new Companion(null);
    }

    public GuestUserInteractor(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, UserManager userManager, UserRepository userRepository, DeviceProvisionedController deviceProvisionedController, DevicePolicyManager devicePolicyManager, RefreshUsersScheduler refreshUsersScheduler, UiEventLogger uiEventLogger, GuestResumeSessionReceiver guestResumeSessionReceiver, GuestResetOrExitSessionReceiver guestResetOrExitSessionReceiver) {
        this.applicationContext = context;
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.manager = userManager;
        this.repository = userRepository;
        this.deviceProvisionedController = deviceProvisionedController;
        this.devicePolicyManager = devicePolicyManager;
        this.refreshUsersScheduler = refreshUsersScheduler;
        this.uiEventLogger = uiEventLogger;
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) userRepository;
        this.isGuestUserAutoCreated = userRepositoryImpl.isGuestUserAutoCreated;
        this.isGuestUserResetting = userRepositoryImpl.isGuestUserResetting;
        if (context.getUserId() == 0) {
            ((UserTrackerImpl) guestResumeSessionReceiver.mUserTracker).addCallback(guestResumeSessionReceiver.mUserChangedCallback, guestResumeSessionReceiver.mMainExecutor);
            guestResetOrExitSessionReceiver.getClass();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.GUEST_RESET");
            intentFilter.addAction("android.intent.action.GUEST_EXIT");
            guestResetOrExitSessionReceiver.mBroadcastDispatcher.registerReceiver(guestResetOrExitSessionReceiver, intentFilter, null, UserHandle.SYSTEM);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0081, code lost:
    
        if (r6 == r1) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object guaranteePresent(ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        Object objWithContext;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objWithContext2 = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext2);
            if (!isDeviceAllowedToAddGuest()) {
                return Unit.INSTANCE;
            }
            GuestUserInteractor$guaranteePresent$guestUser$1 guestUserInteractor$guaranteePresent$guestUser$1 = new GuestUserInteractor$guaranteePresent$guestUser$1(this, null);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            objWithContext2 = BuildersKt.withContext(this.backgroundDispatcher, guestUserInteractor$guaranteePresent$guestUser$1, anonymousClass1);
            if (objWithContext2 != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objWithContext2);
            return Unit.INSTANCE;
        }
        this = (GuestUserInteractor) anonymousClass1.L$0;
        ResultKt.throwOnFailure(objWithContext2);
        if (((UserInfo) objWithContext2) != null) {
            return Unit.INSTANCE;
        }
        anonymousClass1.L$0 = null;
        anonymousClass1.label = 2;
        if (((UserRepositoryImpl) this.repository).isGuestUserCreationScheduled.compareAndSet(false, true)) {
            objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new GuestUserInteractor$scheduleCreation$2(this, null), anonymousClass1);
            if (objWithContext != coroutineSingletons) {
                objWithContext = Unit.INSTANCE;
            }
        } else {
            objWithContext = Unit.INSTANCE;
        }
    }

    public final boolean isDeviceAllowedToAddGuest() {
        return ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).deviceProvisioned.get() && !this.devicePolicyManager.isDeviceManaged();
    }

    public final void onDeviceBootCompleted() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C11261(null), 7);
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x0182, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r1, r2, r3) != r4) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x01bb, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r1, r2, r3) == r4) goto L65;
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object remove(int i, int i2, Function1 function1, Function0 function0, Function1 function12, ContinuationImpl continuationImpl) throws Throwable {
        C11271 c11271;
        UserInfo selectedUserInfo;
        Function1 function13;
        Function1 function14;
        int i3;
        Function0 function02;
        GuestUserInteractor guestUserInteractor;
        UserInfo userInfo;
        GuestUserInteractor guestUserInteractor2;
        Function1 function15;
        int iIntValue;
        Integer num;
        IWindowManager windowManagerService;
        int i4 = i;
        if (continuationImpl instanceof C11271) {
            c11271 = (C11271) continuationImpl;
            int i5 = c11271.label;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                c11271.label = i5 - Integer.MIN_VALUE;
            } else {
                c11271 = new C11271(continuationImpl);
            }
        }
        Object objWithContext = c11271.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i6 = c11271.label;
        if (i6 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            selectedUserInfo = ((UserRepositoryImpl) this.repository).getSelectedUserInfo();
            if (selectedUserInfo.id != i4) {
                Log.w("GuestUserInteractor", "User requesting to start a new session (" + i4 + ") is not current user (" + selectedUserInfo + ".id)");
                return Unit.INSTANCE;
            }
            if (!selectedUserInfo.isGuest()) {
                Log.w("GuestUserInteractor", "User requesting to start a new session (" + i4 + ") is not a guest");
                return Unit.INSTANCE;
            }
            GuestUserInteractor$remove$marked$1 guestUserInteractor$remove$marked$1 = new GuestUserInteractor$remove$marked$1(this, selectedUserInfo, null);
            c11271.L$0 = this;
            function13 = function1;
            c11271.L$1 = function13;
            c11271.L$2 = function0;
            function14 = function12;
            c11271.L$3 = function14;
            c11271.L$4 = selectedUserInfo;
            c11271.I$0 = i4;
            i3 = i2;
            c11271.I$1 = i3;
            c11271.label = 1;
            objWithContext = BuildersKt.withContext(this.backgroundDispatcher, guestUserInteractor$remove$marked$1, c11271);
            if (objWithContext != coroutineSingletons) {
                function02 = function0;
                guestUserInteractor = this;
            }
            return coroutineSingletons;
        }
        if (i6 == 1) {
            int i7 = c11271.I$1;
            i4 = c11271.I$0;
            selectedUserInfo = (UserInfo) c11271.L$4;
            function14 = (Function1) c11271.L$3;
            Function0 function03 = (Function0) c11271.L$2;
            function13 = (Function1) c11271.L$1;
            guestUserInteractor = (GuestUserInteractor) c11271.L$0;
            ResultKt.throwOnFailure(objWithContext);
            function02 = function03;
            i3 = i7;
        } else {
            if (i6 == 2) {
                userInfo = (UserInfo) c11271.L$2;
                function15 = (Function1) c11271.L$1;
                guestUserInteractor2 = (GuestUserInteractor) c11271.L$0;
                ResultKt.throwOnFailure(objWithContext);
                iIntValue = ((Number) objWithContext).intValue();
                if (iIntValue != -10000) {
                    Log.e("GuestUserInteractor", "Could not create new guest, switching back to main user");
                    CoroutineDispatcher coroutineDispatcher = guestUserInteractor2.backgroundDispatcher;
                    GuestUserInteractor$remove$mainUser$1 guestUserInteractor$remove$mainUser$1 = new GuestUserInteractor$remove$mainUser$1(guestUserInteractor2, null);
                    c11271.L$0 = guestUserInteractor2;
                    c11271.L$1 = function15;
                    c11271.L$2 = userInfo;
                    c11271.label = 3;
                    objWithContext = BuildersKt.withContext(coroutineDispatcher, guestUserInteractor$remove$mainUser$1, c11271);
                    if (objWithContext != coroutineSingletons) {
                        num = (Integer) objWithContext;
                        if (num != null) {
                        }
                        CoroutineDispatcher coroutineDispatcher2 = guestUserInteractor2.backgroundDispatcher;
                        AnonymousClass3 anonymousClass3 = guestUserInteractor2.new AnonymousClass3(userInfo, null);
                        c11271.L$0 = null;
                        c11271.L$1 = null;
                        c11271.L$2 = null;
                        c11271.label = 4;
                    }
                } else {
                    function15.mo781invoke(new Integer(iIntValue));
                    CoroutineDispatcher coroutineDispatcher3 = guestUserInteractor2.backgroundDispatcher;
                    AnonymousClass4 anonymousClass4 = guestUserInteractor2.new AnonymousClass4(userInfo, null);
                    c11271.L$0 = null;
                    c11271.L$1 = null;
                    c11271.L$2 = null;
                    c11271.label = 5;
                }
                return coroutineSingletons;
            }
            if (i6 != 3) {
                if (i6 != 4) {
                    if (i6 != 5) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(objWithContext);
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(objWithContext);
                try {
                    windowManagerService = WindowManagerGlobal.getWindowManagerService();
                } catch (RemoteException unused) {
                    Log.e("GuestUserInteractor", "Couldn't remove guest because ActivityManager or WindowManager is dead");
                }
                if (windowManagerService == null) {
                    throw new IllegalStateException("Required value was null.");
                }
                windowManagerService.lockNow((Bundle) null);
                return Unit.INSTANCE;
            }
            userInfo = (UserInfo) c11271.L$2;
            function15 = (Function1) c11271.L$1;
            guestUserInteractor2 = (GuestUserInteractor) c11271.L$0;
            ResultKt.throwOnFailure(objWithContext);
            num = (Integer) objWithContext;
            if (num != null) {
                function15.mo781invoke(new Integer(num.intValue()));
            }
            CoroutineDispatcher coroutineDispatcher22 = guestUserInteractor2.backgroundDispatcher;
            AnonymousClass3 anonymousClass32 = guestUserInteractor2.new AnonymousClass3(userInfo, null);
            c11271.L$0 = null;
            c11271.L$1 = null;
            c11271.L$2 = null;
            c11271.label = 4;
        }
        Function0 function04 = function02;
        if (!((Boolean) objWithContext).booleanValue()) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(i4, "Couldn't mark the guest for deletion for user ", "GuestUserInteractor");
            return Unit.INSTANCE;
        }
        if (i3 != -10000) {
            UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) guestUserInteractor.repository;
            if (userRepositoryImpl.isGuestUserAutoCreated) {
                userRepositoryImpl.isGuestUserResetting = true;
            }
            function14.mo781invoke(new Integer(i3));
            guestUserInteractor.manager.removeUserWhenPossible(UserHandle.of(selectedUserInfo.id), false);
            return Unit.INSTANCE;
        }
        c11271.L$0 = guestUserInteractor;
        c11271.L$1 = function14;
        c11271.L$2 = selectedUserInfo;
        c11271.L$3 = null;
        c11271.L$4 = null;
        c11271.label = 2;
        guestUserInteractor.getClass();
        objWithContext = BuildersKt.withContext(guestUserInteractor.mainDispatcher, new GuestUserInteractor$create$2(function13, guestUserInteractor, function04, null), c11271);
        if (objWithContext != coroutineSingletons) {
            userInfo = selectedUserInfo;
            guestUserInteractor2 = guestUserInteractor;
            function15 = function14;
            iIntValue = ((Number) objWithContext).intValue();
            if (iIntValue != -10000) {
            }
        }
        return coroutineSingletons;
    }
}
