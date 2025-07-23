package com.android.systemui.statusbar.core;

import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.phone.fragment.dagger.HomeStatusBarComponent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface StatusBarInitializer extends CoreStartable {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnStatusBarViewInitializedListener {
        void onStatusBarViewInitialized(HomeStatusBarComponent homeStatusBarComponent);
    }
}
