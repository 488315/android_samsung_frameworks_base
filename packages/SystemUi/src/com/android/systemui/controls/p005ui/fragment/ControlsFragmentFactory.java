package com.android.systemui.controls.p005ui.fragment;

import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import com.android.systemui.controls.controller.util.BadgeSubject;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.p005ui.CustomControlsUiController;
import com.android.systemui.controls.p005ui.util.ControlsActivityStarter;
import com.android.systemui.controls.p005ui.util.LayoutUtil;
import com.android.systemui.controls.p005ui.util.SALogger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsFragmentFactory extends FragmentFactory {
    public final BadgeSubject badgeSubject;
    public final ControlsActivityStarter controlsActivityStarter;
    public final CustomControlsUiController controlsUiController;
    public final LayoutUtil layoutUtil;
    public final ControlsListingController listingController;
    public final SALogger saLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ControlsFragmentFactory(ControlsActivityStarter controlsActivityStarter, LayoutUtil layoutUtil, SALogger sALogger, BadgeSubject badgeSubject, ControlsListingController controlsListingController, CustomControlsUiController customControlsUiController) {
        this.controlsActivityStarter = controlsActivityStarter;
        this.layoutUtil = layoutUtil;
        this.saLogger = sALogger;
        this.badgeSubject = badgeSubject;
        this.listingController = controlsListingController;
        this.controlsUiController = customControlsUiController;
    }

    @Override // androidx.fragment.app.FragmentFactory
    public final Fragment instantiate(ClassLoader classLoader, String str) {
        Fragment noAppFragment;
        if (Intrinsics.areEqual(str, MainFragment.class.getName())) {
            noAppFragment = new MainFragment(this.controlsActivityStarter, this.layoutUtil, this.saLogger, this.badgeSubject, this.listingController, this.controlsUiController);
        } else {
            boolean areEqual = Intrinsics.areEqual(str, NoAppFragment.class.getName());
            SALogger sALogger = this.saLogger;
            noAppFragment = areEqual ? new NoAppFragment(sALogger) : Intrinsics.areEqual(str, NoFavoriteFragment.class.getName()) ? new NoFavoriteFragment(this.controlsActivityStarter, sALogger, this.badgeSubject) : Intrinsics.areEqual(str, SettingFragment.class.getName()) ? new SettingFragment(sALogger) : super.instantiate(classLoader, str);
        }
        Log.d("ControlsFragmentFactory", str);
        return noAppFragment;
    }
}
