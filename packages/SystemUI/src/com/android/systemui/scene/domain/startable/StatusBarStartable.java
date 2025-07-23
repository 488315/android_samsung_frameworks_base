package com.android.systemui.scene.domain.startable;

import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.CoreStartable;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.deviceconfig.domain.interactor.DeviceConfigInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.navigation.domain.interactor.NavigationInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class StatusBarStartable implements CoreStartable {
    public final Context applicationContext;
    public final CoroutineScope applicationScope;
    public final AuthenticationInteractor authenticationInteractor;
    public final CoroutineDispatcher backgroundDispatcher;
    public final DeviceConfigInteractor deviceConfigInteractor;
    public final DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor;
    public final DeviceEntryInteractor deviceEntryInteractor;
    public final IBinder disableToken = new Binder();
    public final NavigationInteractor navigationInteractor;
    public final PowerInteractor powerInteractor;
    public final SceneContainerOcclusionInteractor sceneContainerOcclusionInteractor;
    public final SceneInteractor sceneInteractor;
    public final SelectedUserInteractor selectedUserInteractor;
    public final IStatusBarService statusBarService;

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

    public StatusBarStartable(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Context context, SelectedUserInteractor selectedUserInteractor, SceneInteractor sceneInteractor, DeviceEntryInteractor deviceEntryInteractor, SceneContainerOcclusionInteractor sceneContainerOcclusionInteractor, DeviceConfigInteractor deviceConfigInteractor, NavigationInteractor navigationInteractor, AuthenticationInteractor authenticationInteractor, PowerInteractor powerInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, IStatusBarService iStatusBarService) {
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.applicationContext = context;
        this.selectedUserInteractor = selectedUserInteractor;
        this.sceneInteractor = sceneInteractor;
        this.deviceEntryInteractor = deviceEntryInteractor;
        this.sceneContainerOcclusionInteractor = sceneContainerOcclusionInteractor;
        this.deviceConfigInteractor = deviceConfigInteractor;
        this.navigationInteractor = navigationInteractor;
        this.authenticationInteractor = authenticationInteractor;
        this.powerInteractor = powerInteractor;
        this.deviceEntryFaceAuthInteractor = deviceEntryFaceAuthInteractor;
        this.statusBarService = iStatusBarService;
    }

    @Override // com.android.systemui.CoreStartable
    public final void onBootCompleted() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.backgroundDispatcher, null, new StatusBarStartable$onBootCompleted$1(this, null), 5);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
