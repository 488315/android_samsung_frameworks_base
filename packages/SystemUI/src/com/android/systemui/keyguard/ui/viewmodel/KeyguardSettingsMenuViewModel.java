package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardSettingsMenuViewModel {
    public final KeyguardLongPressInteractor interactor;
    public final ReadonlyStateFlow isVisible;
    public final ReadonlyStateFlow shouldOpenSettings;
    public final Icon.Resource icon = new Icon.Resource(R.drawable.ic_palette, null);
    public final Text.Resource text = new Text.Resource(R.string.lock_screen_settings);

    public KeyguardSettingsMenuViewModel(KeyguardLongPressInteractor keyguardLongPressInteractor) {
        this.interactor = keyguardLongPressInteractor;
        this.isVisible = keyguardLongPressInteractor.isMenuVisible;
        this.shouldOpenSettings = keyguardLongPressInteractor.shouldOpenSettings;
    }
}
