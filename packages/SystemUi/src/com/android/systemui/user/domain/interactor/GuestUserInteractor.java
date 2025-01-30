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
import android.view.WindowManagerGlobal;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.GuestResetOrExitSessionReceiver;
import com.android.systemui.GuestResumeSessionReceiver;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.isGuestUserAutoCreated = ((UserRepositoryImpl) userRepository).isGuestUserAutoCreated;
        this.isGuestUserResetting = ((UserRepositoryImpl) userRepository).isGuestUserResetting;
        ((UserTrackerImpl) guestResumeSessionReceiver.mUserTracker).addCallback(guestResumeSessionReceiver.mUserChangedCallback, guestResumeSessionReceiver.mMainExecutor);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.GUEST_RESET");
        intentFilter.addAction("android.intent.action.GUEST_EXIT");
        guestResetOrExitSessionReceiver.mBroadcastDispatcher.registerReceiver(guestResetOrExitSessionReceiver, intentFilter, null, UserHandle.SYSTEM);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object guaranteePresent(Continuation continuation) {
        GuestUserInteractor$guaranteePresent$1 guestUserInteractor$guaranteePresent$1;
        Object obj;
        int i;
        Object withContext;
        if (continuation instanceof GuestUserInteractor$guaranteePresent$1) {
            guestUserInteractor$guaranteePresent$1 = (GuestUserInteractor$guaranteePresent$1) continuation;
            int i2 = guestUserInteractor$guaranteePresent$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                guestUserInteractor$guaranteePresent$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj = guestUserInteractor$guaranteePresent$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = guestUserInteractor$guaranteePresent$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    if (!isDeviceAllowedToAddGuest()) {
                        return Unit.INSTANCE;
                    }
                    GuestUserInteractor$guaranteePresent$guestUser$1 guestUserInteractor$guaranteePresent$guestUser$1 = new GuestUserInteractor$guaranteePresent$guestUser$1(this, null);
                    guestUserInteractor$guaranteePresent$1.L$0 = this;
                    guestUserInteractor$guaranteePresent$1.label = 1;
                    obj = BuildersKt.withContext(this.backgroundDispatcher, guestUserInteractor$guaranteePresent$guestUser$1, guestUserInteractor$guaranteePresent$1);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    this = (GuestUserInteractor) guestUserInteractor$guaranteePresent$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                if (((UserInfo) obj) == null) {
                    return Unit.INSTANCE;
                }
                guestUserInteractor$guaranteePresent$1.L$0 = null;
                guestUserInteractor$guaranteePresent$1.label = 2;
                if (((UserRepositoryImpl) this.repository).isGuestUserCreationScheduled.compareAndSet(false, true)) {
                    withContext = BuildersKt.withContext(this.backgroundDispatcher, new GuestUserInteractor$scheduleCreation$2(this, null), guestUserInteractor$guaranteePresent$1);
                    if (withContext != coroutineSingletons) {
                        withContext = Unit.INSTANCE;
                    }
                } else {
                    withContext = Unit.INSTANCE;
                }
                if (withContext == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
        }
        guestUserInteractor$guaranteePresent$1 = new GuestUserInteractor$guaranteePresent$1(this, continuation);
        obj = guestUserInteractor$guaranteePresent$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = guestUserInteractor$guaranteePresent$1.label;
        if (i != 0) {
        }
        if (((UserInfo) obj) == null) {
        }
    }

    public final boolean isDeviceAllowedToAddGuest() {
        return ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).isDeviceProvisioned() && !this.devicePolicyManager.isDeviceManaged();
    }

    public final void onDeviceBootCompleted() {
        BuildersKt.launch$default(this.applicationScope, null, null, new GuestUserInteractor$onDeviceBootCompleted$1(this, null), 3);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0181 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object remove(int i, int i2, Function1 function1, Function0 function0, Function1 function12, Continuation continuation) {
        GuestUserInteractor$remove$1 guestUserInteractor$remove$1;
        Object obj;
        int i3;
        UserInfo selectedUserInfo;
        int i4;
        Function1 function13;
        GuestUserInteractor guestUserInteractor;
        Function1 function14;
        Function0 function02;
        UserInfo userInfo;
        Function1 function15;
        GuestUserInteractor guestUserInteractor2;
        int intValue;
        Integer num;
        CoroutineDispatcher coroutineDispatcher;
        GuestUserInteractor$remove$3 guestUserInteractor$remove$3;
        int i5 = i;
        if (continuation instanceof GuestUserInteractor$remove$1) {
            guestUserInteractor$remove$1 = (GuestUserInteractor$remove$1) continuation;
            int i6 = guestUserInteractor$remove$1.label;
            if ((i6 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                guestUserInteractor$remove$1.label = i6 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj = guestUserInteractor$remove$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i3 = guestUserInteractor$remove$1.label;
                if (i3 != 0) {
                    ResultKt.throwOnFailure(obj);
                    selectedUserInfo = ((UserRepositoryImpl) this.repository).getSelectedUserInfo();
                    if (selectedUserInfo.id != i5) {
                        Log.w("GuestUserInteractor", "User requesting to start a new session (" + i5 + ") is not current user (" + selectedUserInfo + ".id)");
                        return Unit.INSTANCE;
                    }
                    if (!selectedUserInfo.isGuest()) {
                        Log.w("GuestUserInteractor", "User requesting to start a new session (" + i5 + ") is not a guest");
                        return Unit.INSTANCE;
                    }
                    GuestUserInteractor$remove$marked$1 guestUserInteractor$remove$marked$1 = new GuestUserInteractor$remove$marked$1(this, selectedUserInfo, null);
                    guestUserInteractor$remove$1.L$0 = this;
                    guestUserInteractor$remove$1.L$1 = function1;
                    guestUserInteractor$remove$1.L$2 = function0;
                    guestUserInteractor$remove$1.L$3 = function12;
                    guestUserInteractor$remove$1.L$4 = selectedUserInfo;
                    guestUserInteractor$remove$1.I$0 = i5;
                    i4 = i2;
                    guestUserInteractor$remove$1.I$1 = i4;
                    guestUserInteractor$remove$1.label = 1;
                    obj = BuildersKt.withContext(this.backgroundDispatcher, guestUserInteractor$remove$marked$1, guestUserInteractor$remove$1);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    function13 = function12;
                    guestUserInteractor = this;
                    function14 = function1;
                    function02 = function0;
                } else {
                    if (i3 != 1) {
                        if (i3 != 2) {
                            if (i3 != 3) {
                                if (i3 != 4) {
                                    if (i3 != 5) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj);
                                    return Unit.INSTANCE;
                                }
                                ResultKt.throwOnFailure(obj);
                                try {
                                    WindowManagerGlobal.getWindowManagerService().lockNow((Bundle) null);
                                } catch (RemoteException unused) {
                                    Log.e("GuestUserInteractor", "Couldn't remove guest because ActivityManager or WindowManager is dead");
                                }
                                return Unit.INSTANCE;
                            }
                            userInfo = (UserInfo) guestUserInteractor$remove$1.L$2;
                            function15 = (Function1) guestUserInteractor$remove$1.L$1;
                            guestUserInteractor2 = (GuestUserInteractor) guestUserInteractor$remove$1.L$0;
                            ResultKt.throwOnFailure(obj);
                            num = (Integer) obj;
                            if (num != null) {
                                function15.invoke(new Integer(num.intValue()));
                            }
                            coroutineDispatcher = guestUserInteractor2.backgroundDispatcher;
                            guestUserInteractor$remove$3 = new GuestUserInteractor$remove$3(guestUserInteractor2, userInfo, null);
                            guestUserInteractor$remove$1.L$0 = null;
                            guestUserInteractor$remove$1.L$1 = null;
                            guestUserInteractor$remove$1.L$2 = null;
                            guestUserInteractor$remove$1.label = 4;
                            if (BuildersKt.withContext(coroutineDispatcher, guestUserInteractor$remove$3, guestUserInteractor$remove$1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            WindowManagerGlobal.getWindowManagerService().lockNow((Bundle) null);
                            return Unit.INSTANCE;
                        }
                        userInfo = (UserInfo) guestUserInteractor$remove$1.L$2;
                        function15 = (Function1) guestUserInteractor$remove$1.L$1;
                        guestUserInteractor2 = (GuestUserInteractor) guestUserInteractor$remove$1.L$0;
                        ResultKt.throwOnFailure(obj);
                        intValue = ((Number) obj).intValue();
                        if (intValue == -10000) {
                            function15.invoke(new Integer(intValue));
                            CoroutineDispatcher coroutineDispatcher2 = guestUserInteractor2.backgroundDispatcher;
                            GuestUserInteractor$remove$4 guestUserInteractor$remove$4 = new GuestUserInteractor$remove$4(guestUserInteractor2, userInfo, null);
                            guestUserInteractor$remove$1.L$0 = null;
                            guestUserInteractor$remove$1.L$1 = null;
                            guestUserInteractor$remove$1.L$2 = null;
                            guestUserInteractor$remove$1.label = 5;
                            if (BuildersKt.withContext(coroutineDispatcher2, guestUserInteractor$remove$4, guestUserInteractor$remove$1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            return Unit.INSTANCE;
                        }
                        Log.e("GuestUserInteractor", "Could not create new guest, switching back to main user");
                        CoroutineDispatcher coroutineDispatcher3 = guestUserInteractor2.backgroundDispatcher;
                        GuestUserInteractor$remove$mainUser$1 guestUserInteractor$remove$mainUser$1 = new GuestUserInteractor$remove$mainUser$1(guestUserInteractor2, null);
                        guestUserInteractor$remove$1.L$0 = guestUserInteractor2;
                        guestUserInteractor$remove$1.L$1 = function15;
                        guestUserInteractor$remove$1.L$2 = userInfo;
                        guestUserInteractor$remove$1.label = 3;
                        obj = BuildersKt.withContext(coroutineDispatcher3, guestUserInteractor$remove$mainUser$1, guestUserInteractor$remove$1);
                        if (obj == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        num = (Integer) obj;
                        if (num != null) {
                        }
                        coroutineDispatcher = guestUserInteractor2.backgroundDispatcher;
                        guestUserInteractor$remove$3 = new GuestUserInteractor$remove$3(guestUserInteractor2, userInfo, null);
                        guestUserInteractor$remove$1.L$0 = null;
                        guestUserInteractor$remove$1.L$1 = null;
                        guestUserInteractor$remove$1.L$2 = null;
                        guestUserInteractor$remove$1.label = 4;
                        if (BuildersKt.withContext(coroutineDispatcher, guestUserInteractor$remove$3, guestUserInteractor$remove$1) == coroutineSingletons) {
                        }
                        WindowManagerGlobal.getWindowManagerService().lockNow((Bundle) null);
                        return Unit.INSTANCE;
                    }
                    int i7 = guestUserInteractor$remove$1.I$1;
                    i5 = guestUserInteractor$remove$1.I$0;
                    selectedUserInfo = (UserInfo) guestUserInteractor$remove$1.L$4;
                    function13 = (Function1) guestUserInteractor$remove$1.L$3;
                    function02 = (Function0) guestUserInteractor$remove$1.L$2;
                    function14 = (Function1) guestUserInteractor$remove$1.L$1;
                    guestUserInteractor = (GuestUserInteractor) guestUserInteractor$remove$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    i4 = i7;
                }
                if (((Boolean) obj).booleanValue()) {
                    IconCompat$$ExternalSyntheticOutline0.m30m("Couldn't mark the guest for deletion for user ", i5, "GuestUserInteractor");
                    return Unit.INSTANCE;
                }
                if (i4 != -10000) {
                    UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) guestUserInteractor.repository;
                    if (userRepositoryImpl.isGuestUserAutoCreated) {
                        userRepositoryImpl.isGuestUserResetting = true;
                    }
                    function13.invoke(new Integer(i4));
                    guestUserInteractor.manager.removeUserWhenPossible(UserHandle.of(selectedUserInfo.id), false);
                    return Unit.INSTANCE;
                }
                guestUserInteractor$remove$1.L$0 = guestUserInteractor;
                guestUserInteractor$remove$1.L$1 = function13;
                guestUserInteractor$remove$1.L$2 = selectedUserInfo;
                guestUserInteractor$remove$1.L$3 = null;
                guestUserInteractor$remove$1.L$4 = null;
                guestUserInteractor$remove$1.label = 2;
                guestUserInteractor.getClass();
                obj = BuildersKt.withContext(guestUserInteractor.mainDispatcher, new GuestUserInteractor$create$2(function14, guestUserInteractor, function02, null), guestUserInteractor$remove$1);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
                userInfo = selectedUserInfo;
                function15 = function13;
                guestUserInteractor2 = guestUserInteractor;
                intValue = ((Number) obj).intValue();
                if (intValue == -10000) {
                }
            }
        }
        guestUserInteractor$remove$1 = new GuestUserInteractor$remove$1(this, continuation);
        obj = guestUserInteractor$remove$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i3 = guestUserInteractor$remove$1.label;
        if (i3 != 0) {
        }
        if (((Boolean) obj).booleanValue()) {
        }
    }
}
