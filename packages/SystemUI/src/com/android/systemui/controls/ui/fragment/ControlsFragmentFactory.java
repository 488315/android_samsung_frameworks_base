package com.android.systemui.controls.ui.fragment;

import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import com.android.systemui.controls.controller.util.BadgeSubject;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.ui.SecControlsUiController;
import com.android.systemui.controls.ui.util.ControlsActivityStarter;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.util.SALogger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ControlsFragmentFactory extends FragmentFactory {
    public final BadgeSubject badgeSubject;
    public final ControlsActivityStarter controlsActivityStarter;
    public final SecControlsUiController controlsUiController;
    public final LayoutUtil layoutUtil;
    public final ControlsListingController listingController;
    public final SALogger saLogger;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ControlsFragmentFactory(ControlsActivityStarter controlsActivityStarter, LayoutUtil layoutUtil, SALogger sALogger, BadgeSubject badgeSubject, ControlsListingController controlsListingController, SecControlsUiController secControlsUiController) {
        this.controlsActivityStarter = controlsActivityStarter;
        this.layoutUtil = layoutUtil;
        this.saLogger = sALogger;
        this.badgeSubject = badgeSubject;
        this.listingController = controlsListingController;
        this.controlsUiController = secControlsUiController;
    }

    @Override // androidx.fragment.app.FragmentFactory
    public final Fragment instantiate(ClassLoader classLoader, String str) {
        Fragment instantiate;
        Fragment settingFragment;
        if (Intrinsics.areEqual(str, MainFragment.class.getName())) {
            instantiate = new MainFragment(this.controlsActivityStarter, this.layoutUtil, this.saLogger, this.badgeSubject, this.listingController, this.controlsUiController);
        } else {
            boolean areEqual = Intrinsics.areEqual(str, NoAppFragment.class.getName());
            SALogger sALogger = this.saLogger;
            if (areEqual) {
                settingFragment = new NoAppFragment(sALogger);
            } else if (Intrinsics.areEqual(str, NoFavoriteFragment.class.getName())) {
                instantiate = new NoFavoriteFragment(this.controlsActivityStarter, sALogger, this.badgeSubject);
            } else if (Intrinsics.areEqual(str, SettingFragment.class.getName())) {
                settingFragment = new SettingFragment(sALogger);
            } else {
                instantiate = super.instantiate(classLoader, str);
            }
            instantiate = settingFragment;
        }
        instantiate.getClass();
        Log.d("ControlsFragmentFactory", str);
        return instantiate;
    }
}
