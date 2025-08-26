package com.android.systemui.statusbar.core;

import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.phone.fragment.dagger.HomeStatusBarComponent;

/* loaded from: classes3.dex */
public interface StatusBarInitializer extends CoreStartable {

    public interface Factory {
    }

    public interface OnStatusBarViewInitializedListener {
        void onStatusBarViewInitialized(HomeStatusBarComponent homeStatusBarComponent);
    }
}
