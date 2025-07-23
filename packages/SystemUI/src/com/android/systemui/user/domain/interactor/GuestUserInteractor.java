package com.android.systemui.user.domain.interactor;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.os.UserManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.GuestResetOrExitSessionReceiver;
import com.android.systemui.GuestResumeSessionReceiver;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0081, code lost:
    
        if (r6 == r1) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0083, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0056, code lost:
    
        if (r7 == r1) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object guaranteePresent(kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.android.systemui.user.domain.interactor.GuestUserInteractor$guaranteePresent$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.user.domain.interactor.GuestUserInteractor$guaranteePresent$1 r0 = (com.android.systemui.user.domain.interactor.GuestUserInteractor$guaranteePresent$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.user.domain.interactor.GuestUserInteractor$guaranteePresent$1 r0 = new com.android.systemui.user.domain.interactor.GuestUserInteractor$guaranteePresent$1
            r0.<init>(r6, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 0
            r5 = 1
            if (r2 == 0) goto L3b
            if (r2 == r5) goto L33
            if (r2 != r3) goto L2b
            kotlin.ResultKt.throwOnFailure(r7)
            goto L84
        L2b:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L33:
            java.lang.Object r6 = r0.L$0
            com.android.systemui.user.domain.interactor.GuestUserInteractor r6 = (com.android.systemui.user.domain.interactor.GuestUserInteractor) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L59
        L3b:
            kotlin.ResultKt.throwOnFailure(r7)
            boolean r7 = r6.isDeviceAllowedToAddGuest()
            if (r7 != 0) goto L47
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L47:
            com.android.systemui.user.domain.interactor.GuestUserInteractor$guaranteePresent$guestUser$1 r7 = new com.android.systemui.user.domain.interactor.GuestUserInteractor$guaranteePresent$guestUser$1
            r7.<init>(r6, r4)
            r0.L$0 = r6
            r0.label = r5
            kotlinx.coroutines.CoroutineDispatcher r2 = r6.backgroundDispatcher
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r2, r7, r0)
            if (r7 != r1) goto L59
            goto L83
        L59:
            android.content.pm.UserInfo r7 = (android.content.pm.UserInfo) r7
            if (r7 != 0) goto L87
            r0.L$0 = r4
            r0.label = r3
            com.android.systemui.user.data.repository.UserRepository r7 = r6.repository
            com.android.systemui.user.data.repository.UserRepositoryImpl r7 = (com.android.systemui.user.data.repository.UserRepositoryImpl) r7
            java.util.concurrent.atomic.AtomicBoolean r7 = r7.isGuestUserCreationScheduled
            r2 = 0
            boolean r7 = r7.compareAndSet(r2, r5)
            if (r7 != 0) goto L71
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            goto L81
        L71:
            com.android.systemui.user.domain.interactor.GuestUserInteractor$scheduleCreation$2 r7 = new com.android.systemui.user.domain.interactor.GuestUserInteractor$scheduleCreation$2
            r7.<init>(r6, r4)
            kotlinx.coroutines.CoroutineDispatcher r6 = r6.backgroundDispatcher
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r6, r7, r0)
            if (r6 != r1) goto L7f
            goto L81
        L7f:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
        L81:
            if (r6 != r1) goto L84
        L83:
            return r1
        L84:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L87:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.domain.interactor.GuestUserInteractor.guaranteePresent(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final boolean isDeviceAllowedToAddGuest() {
        return ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).deviceProvisioned.get() && !this.devicePolicyManager.isDeviceManaged();
    }

    public final void onDeviceBootCompleted() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new GuestUserInteractor$onDeviceBootCompleted$1(this, null), 7);
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0182, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r1, r2, r3) == r4) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x015b, code lost:
    
        if (r2 == r4) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x01bb, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r1, r2, r3) == r4) goto L65;
     */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object remove(int r18, int r19, kotlin.jvm.functions.Function1 r20, kotlin.jvm.functions.Function0 r21, kotlin.jvm.functions.Function1 r22, kotlin.coroutines.jvm.internal.ContinuationImpl r23) {
        /*
            Method dump skipped, instructions count: 483
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.domain.interactor.GuestUserInteractor.remove(int, int, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
