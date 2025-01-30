package com.android.systemui.statusbar.phone.knox.p024ui.viewmodel;

import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.knox.domain.interactor.KnoxStatusBarControlInteractor;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
