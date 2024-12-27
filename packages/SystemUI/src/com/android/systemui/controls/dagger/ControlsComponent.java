package com.android.systemui.controls.dagger;

import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.controls.controller.ControlsTileResourceConfigurationImpl;
import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;
import java.util.Optional;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlow;

public final class ControlsComponent {
    public final StateFlow canShowWhileLockedSetting;
    public final Optional controlsController;
    public final Optional controlsListingController;
    public final ControlsTileResourceConfigurationImpl controlsTileResourceConfiguration;
    public final Optional controlsUiController;
    public final boolean featureEnabled;
    public final KeyguardStateController keyguardStateController;
    public final LockPatternUtils lockPatternUtils;
    public final Optional secControlsController;
    public final Optional secControlsUiController;
    public final UserTracker userTracker;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Visibility {
        public static final /* synthetic */ Visibility[] $VALUES;
        public static final Visibility AVAILABLE;
        public static final Visibility AVAILABLE_AFTER_UNLOCK;
        public static final Visibility UNAVAILABLE;

        static {
            Visibility visibility = new Visibility("AVAILABLE", 0);
            AVAILABLE = visibility;
            Visibility visibility2 = new Visibility("AVAILABLE_AFTER_UNLOCK", 1);
            AVAILABLE_AFTER_UNLOCK = visibility2;
            Visibility visibility3 = new Visibility("UNAVAILABLE", 2);
            UNAVAILABLE = visibility3;
            Visibility[] visibilityArr = {visibility, visibility2, visibility3};
            $VALUES = visibilityArr;
            EnumEntriesKt.enumEntries(visibilityArr);
        }

        private Visibility(String str, int i) {
        }

        public static Visibility valueOf(String str) {
            return (Visibility) Enum.valueOf(Visibility.class, str);
        }

        public static Visibility[] values() {
            return (Visibility[]) $VALUES.clone();
        }
    }

    public ControlsComponent(boolean z, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, LockPatternUtils lockPatternUtils, KeyguardStateController keyguardStateController, UserTracker userTracker, ControlsSettingsRepository controlsSettingsRepository, Optional<ControlsTileResourceConfigurationImpl> optional) {
        this.featureEnabled = z;
        this.lockPatternUtils = lockPatternUtils;
        this.keyguardStateController = keyguardStateController;
        this.userTracker = userTracker;
        this.controlsController = optionalIf(z, lazy);
        this.secControlsController = optionalIf(z, lazy2);
        this.controlsUiController = optionalIf(z, lazy3);
        this.secControlsUiController = optionalIf(z, lazy4);
        this.controlsListingController = optionalIf(z, lazy5);
        this.canShowWhileLockedSetting = ((ControlsSettingsRepositoryImpl) controlsSettingsRepository).canShowControlsInLockscreen;
        this.controlsTileResourceConfiguration = optional.orElse(new ControlsTileResourceConfigurationImpl());
    }

    public static Optional optionalIf(boolean z, Lazy lazy) {
        if (z) {
            Optional of = Optional.of(lazy.get());
            Intrinsics.checkNotNull(of);
            return of;
        }
        Optional empty = Optional.empty();
        Intrinsics.checkNotNull(empty);
        return empty;
    }
}
