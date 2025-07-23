package com.android.systemui.dreams.homecontrols.system;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.dreams.homecontrols.HomeControlsDreamService;
import com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor;
import com.android.systemui.settings.UserContextProvider;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class HomeControlsDreamStartable implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope bgScope;
    public final ComponentName componentName;
    public final HomeControlsComponentInteractor homeControlsComponentInteractor;
    public final UserContextProvider userContextProvider;

    public HomeControlsDreamStartable(Context context, PackageManager packageManager, UserContextProvider userContextProvider, HomeControlsComponentInteractor homeControlsComponentInteractor, CoroutineScope coroutineScope) {
        this.userContextProvider = userContextProvider;
        this.homeControlsComponentInteractor = homeControlsComponentInteractor;
        this.bgScope = coroutineScope;
        this.componentName = new ComponentName(context, (Class<?>) HomeControlsDreamService.class);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new HomeControlsDreamStartable$start$1(this, null), 7);
    }
}
