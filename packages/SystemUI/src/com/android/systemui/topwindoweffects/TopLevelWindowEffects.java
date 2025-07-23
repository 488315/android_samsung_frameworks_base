package com.android.systemui.topwindoweffects;

import android.content.Context;
import android.view.WindowManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.keyevent.domain.interactor.KeyEventInteractor;
import com.android.systemui.topwindoweffects.domain.interactor.SqueezeEffectInteractor;
import com.android.systemui.topwindoweffects.ui.viewmodel.SqueezeEffectViewModel;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TopLevelWindowEffects implements CoreStartable {
    public final CoroutineScope applicationScope;
    public final Context context;
    public final KeyEventInteractor keyEventInteractor;
    public final SqueezeEffectInteractor squeezeEffectInteractor;
    public final SqueezeEffectViewModel.Factory viewModelFactory;
    public final WindowManager windowManager;

    public TopLevelWindowEffects(Context context, CoroutineScope coroutineScope, WindowManager windowManager, SqueezeEffectInteractor squeezeEffectInteractor, KeyEventInteractor keyEventInteractor, SqueezeEffectViewModel.Factory factory) {
        this.context = context;
        this.applicationScope = coroutineScope;
        this.windowManager = windowManager;
        this.squeezeEffectInteractor = squeezeEffectInteractor;
        this.keyEventInteractor = keyEventInteractor;
        this.viewModelFactory = factory;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BuildersKt.launch$default(this.applicationScope, null, null, new TopLevelWindowEffects$start$1(this, null), 3);
    }
}
