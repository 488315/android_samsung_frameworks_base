package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardTouchHandlingInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardSettingsMenuViewModel {
    public final KeyguardTouchHandlingInteractor interactor;
    public final ReadonlyStateFlow isVisible;
    public final ReadonlyStateFlow shouldOpenSettings;
    public final ChannelFlowTransformLatest textSize;
    public final Icon.Resource icon = new Icon.Resource(R.drawable.ic_palette, null);
    public final Text.Resource text = new Text.Resource(R.string.lock_screen_settings);

    public KeyguardSettingsMenuViewModel(KeyguardTouchHandlingInteractor keyguardTouchHandlingInteractor, ConfigurationInteractor configurationInteractor) {
        this.interactor = keyguardTouchHandlingInteractor;
        this.isVisible = keyguardTouchHandlingInteractor.isMenuVisible;
        this.shouldOpenSettings = keyguardTouchHandlingInteractor.shouldOpenSettings;
        this.textSize = ((ConfigurationInteractorImpl) configurationInteractor).dimensionPixelSize(17106416);
    }
}
