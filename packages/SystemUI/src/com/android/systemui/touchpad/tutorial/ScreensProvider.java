package com.android.systemui.touchpad.tutorial;

import com.android.systemui.inputdevice.tutorial.TouchpadTutorialScreensProvider;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.BackGestureScreenViewModel;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureScreenViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ScreensProvider implements TouchpadTutorialScreensProvider {
    public final BackGestureScreenViewModel backGestureScreenViewModel;
    public final EasterEggGestureViewModel easterEggGestureViewModel;
    public final HomeGestureScreenViewModel homeGestureScreenViewModel;

    public ScreensProvider(BackGestureScreenViewModel backGestureScreenViewModel, HomeGestureScreenViewModel homeGestureScreenViewModel, EasterEggGestureViewModel easterEggGestureViewModel) {
        this.backGestureScreenViewModel = backGestureScreenViewModel;
        this.homeGestureScreenViewModel = homeGestureScreenViewModel;
        this.easterEggGestureViewModel = easterEggGestureViewModel;
    }
}
