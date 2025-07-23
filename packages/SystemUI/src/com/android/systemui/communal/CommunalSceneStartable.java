package com.android.systemui.communal;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import com.android.systemui.util.settings.SettingsProxyExt;
import com.android.systemui.util.settings.SystemSettings;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalSceneStartable implements CoreStartable {
    public static final Companion Companion = new Companion(null);
    public static final int DEFAULT_SCREEN_TIMEOUT = 15000;
    public final CoroutineScope bgScope;
    public final CommunalInteractor communalInteractor;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public boolean isDreaming;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final CoroutineDispatcher mainDispatcher;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public int screenTimeout = DEFAULT_SCREEN_TIMEOUT;
    public final SystemSettings systemSettings;
    public StandaloneCoroutine timeoutJob;
    public final UiEventLogger uiEventLogger;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public CommunalSceneStartable(CommunalInteractor communalInteractor, CommunalSettingsInteractor communalSettingsInteractor, CommunalSceneInteractor communalSceneInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, SystemSettings systemSettings, NotificationShadeWindowController notificationShadeWindowController, CoroutineScope coroutineScope, CoroutineScope coroutineScope2, CoroutineDispatcher coroutineDispatcher, UiEventLogger uiEventLogger) {
        this.communalInteractor = communalInteractor;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.communalSceneInteractor = communalSceneInteractor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.keyguardInteractor = keyguardInteractor;
        this.systemSettings = systemSettings;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.bgScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.uiEventLogger = uiEventLogger;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CommunalSettingsInteractor communalSettingsInteractor = this.communalSettingsInteractor;
        if (communalSettingsInteractor.isCommunalFlagEnabled()) {
            CommunalSceneStartable$start$1 communalSceneStartable$start$1 = new CommunalSceneStartable$start$1(this, null);
            CoroutineScope coroutineScope = this.bgScope;
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, communalSceneStartable$start$1, 7);
            communalSettingsInteractor.isV2FlagEnabled();
            FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), SettingsProxyExt.INSTANCE.observerFlow(this.systemSettings, "screen_off_timeout")), new CommunalSceneStartable$start$2(this, null)), coroutineScope);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new CommunalSceneStartable$start$3(this, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new CommunalSceneStartable$start$4(this, null), 7);
            communalSettingsInteractor.isV2FlagEnabled();
        }
    }
}
