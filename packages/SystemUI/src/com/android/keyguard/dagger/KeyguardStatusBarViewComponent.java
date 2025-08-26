package com.android.keyguard.dagger;

import com.android.systemui.shade.ShadeViewStateProvider;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;

/* loaded from: classes.dex */
public interface KeyguardStatusBarViewComponent {

    public interface Factory {
        KeyguardStatusBarViewComponent build(KeyguardStatusBarView keyguardStatusBarView, ShadeViewStateProvider shadeViewStateProvider);
    }
}
