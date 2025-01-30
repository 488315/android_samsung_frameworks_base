package com.android.systemui.keyguard.p009ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
