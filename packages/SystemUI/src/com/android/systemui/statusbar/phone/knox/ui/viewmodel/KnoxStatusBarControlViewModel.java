package com.android.systemui.statusbar.phone.knox.ui.viewmodel;

import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.knox.domain.interactor.KnoxStatusBarControlInteractor;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KnoxStatusBarControlViewModel {
    public final DarkIconDispatcher darkIconDispatcher;
    public final ReadonlyStateFlow knoxStatusBarCustomText;
    public Function1 setHidden;
    public final ReadonlyStateFlow statusBarHidden;
    public final ReadonlyStateFlow statusBarIconsEnabled;

    public KnoxStatusBarControlViewModel(KnoxStatusBarControlInteractor knoxStatusBarControlInteractor, KeyguardStateController keyguardStateController, DarkIconDispatcher darkIconDispatcher) {
        this.darkIconDispatcher = darkIconDispatcher;
        this.statusBarHidden = knoxStatusBarControlInteractor.statusBarHidden;
        this.statusBarIconsEnabled = knoxStatusBarControlInteractor.statusBarIconsEnabled;
        this.knoxStatusBarCustomText = knoxStatusBarControlInteractor.knoxStatusBarCustomText;
    }
}
