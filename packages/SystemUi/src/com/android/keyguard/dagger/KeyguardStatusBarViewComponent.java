package com.android.keyguard.dagger;

import com.android.systemui.shade.ShadeViewStateProvider;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;
import com.android.systemui.statusbar.phone.KeyguardStatusBarViewController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface KeyguardStatusBarViewComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        KeyguardStatusBarViewComponent build(KeyguardStatusBarView keyguardStatusBarView, ShadeViewStateProvider shadeViewStateProvider);
    }

    KeyguardStatusBarViewController getKeyguardStatusBarViewController();
}
