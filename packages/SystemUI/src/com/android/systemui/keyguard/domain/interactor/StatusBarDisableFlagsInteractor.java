package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.CoreStartable;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.deviceconfig.domain.interactor.DeviceConfigInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.navigation.domain.interactor.NavigationInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.process.ProcessWrapper;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.Pair;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes2.dex */
public final class StatusBarDisableFlagsInteractor implements CoreStartable {
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor;
    public final IStatusBarService statusBarService;
    public final IBinder disableToken = new Binder();
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 disableFlagsForUserId = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new Pair(0, 0));

    public StatusBarDisableFlagsInteractor(CoroutineScope coroutineScope, Context context, CoroutineDispatcher coroutineDispatcher, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, IStatusBarService iStatusBarService, ProcessWrapper processWrapper, KeyguardTransitionInteractor keyguardTransitionInteractor, SelectedUserInteractor selectedUserInteractor, DeviceConfigInteractor deviceConfigInteractor, NavigationInteractor navigationInteractor, AuthenticationInteractor authenticationInteractor, PowerInteractor powerInteractor) {
        this.context = context;
        this.backgroundDispatcher = coroutineDispatcher;
        this.deviceEntryFaceAuthInteractor = deviceEntryFaceAuthInteractor;
        this.statusBarService = iStatusBarService;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
