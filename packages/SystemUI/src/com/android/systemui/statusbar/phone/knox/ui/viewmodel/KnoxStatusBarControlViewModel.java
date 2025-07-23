package com.android.systemui.statusbar.phone.knox.ui.viewmodel;

import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda12;
import com.android.systemui.statusbar.phone.knox.domain.interactor.KnoxStatusBarControlInteractor;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class KnoxStatusBarControlViewModel {
    public final DarkIconDispatcher darkIconDispatcher;
    public final ReadonlyStateFlow knoxStatusBarCustomText;
    public KeyguardStatusBarViewController$$ExternalSyntheticLambda12 setHidden;
    public final ReadonlyStateFlow statusBarHidden;
    public final ReadonlyStateFlow statusBarIconsEnabled;

    public KnoxStatusBarControlViewModel(KnoxStatusBarControlInteractor knoxStatusBarControlInteractor, KeyguardStateController keyguardStateController, DarkIconDispatcher darkIconDispatcher) {
        this.darkIconDispatcher = darkIconDispatcher;
        this.statusBarHidden = knoxStatusBarControlInteractor.statusBarHidden;
        this.statusBarIconsEnabled = knoxStatusBarControlInteractor.statusBarIconsEnabled;
        this.knoxStatusBarCustomText = knoxStatusBarControlInteractor.knoxStatusBarCustomText;
    }
}
