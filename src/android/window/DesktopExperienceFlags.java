package android.window;

import android.os.SystemProperties;
import android.util.Log;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import java.util.function.BooleanSupplier;

/* loaded from: classes5.dex */
public enum DesktopExperienceFlags {
    ACTIVITY_EMBEDDING_SUPPORT_FOR_CONNECTED_DISPLAYS(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda0
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.activityEmbeddingSupportForConnectedDisplays();
        }
    }, false),
    BASE_DENSITY_FOR_EXTERNAL_DISPLAYS(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda11
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return com.android.server.display.feature.flags.Flags.baseDensityForExternalDisplays();
        }
    }, true),
    CONNECTED_DISPLAYS_CURSOR(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda22
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return com.android.input.flags.Flags.connectedDisplaysCursor();
        }
    }, true),
    DISPLAY_TOPOLOGY(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda23
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return com.android.server.display.feature.flags.Flags.displayTopology();
        }
    }, true),
    ENABLE_BUG_FIXES_FOR_SECONDARY_DISPLAY(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda24
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableBugFixesForSecondaryDisplay();
        }
    }, true),
    ENABLE_CONNECTED_DISPLAYS_DND(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda25
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableConnectedDisplaysDnd();
        }
    }, false),
    ENABLE_CONNECTED_DISPLAYS_PIP(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda26
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableConnectedDisplaysPip();
        }
    }, false),
    ENABLE_CONNECTED_DISPLAYS_WALLPAPER(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda27
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return android.app.Flags.enableConnectedDisplaysWallpaper();
        }
    }, false),
    ENABLE_CONNECTED_DISPLAYS_WINDOW_DRAG(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda28
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableConnectedDisplaysWindowDrag();
        }
    }, true),
    ENABLE_DISPLAY_CONTENT_MODE_MANAGEMENT(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda29
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return com.android.server.display.feature.flags.Flags.enableDisplayContentModeManagement();
        }
    }, true),
    ENABLE_DISPLAY_DISCONNECT_INTERACTION(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda1
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDisplayDisconnectInteraction();
        }
    }, false),
    ENABLE_DISPLAY_FOCUS_IN_SHELL_TRANSITIONS(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda2
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDisplayFocusInShellTransitions();
        }
    }, true),
    ENABLE_DISPLAY_RECONNECT_INTERACTION(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda3
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDisplayReconnectInteraction();
        }
    }, false),
    ENABLE_DISPLAY_WINDOWING_MODE_SWITCHING(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda4
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDisplayWindowingModeSwitching();
        }
    }, true),
    ENABLE_DRAG_TO_MAXIMIZE(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda5
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDragToMaximize();
        }
    }, true),
    ENABLE_DYNAMIC_RADIUS_COMPUTATION_BUGFIX(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda6
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableDynamicRadiusComputationBugfix();
        }
    }, false),
    ENABLE_INDEPENDENT_BACK_IN_PROJECTED(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda7
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableIndependentBackInProjected();
        }
    }, false),
    ENABLE_KEYBOARD_SHORTCUTS_TO_SWITCH_DESKS(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda8
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.keyboardShortcutsToSwitchDesks();
        }
    }, false),
    ENABLE_MOVE_TO_NEXT_DISPLAY_SHORTCUT(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda9
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableMoveToNextDisplayShortcut();
        }
    }, true),
    ENABLE_MULTIDISPLAY_TRACKPAD_BACK_GESTURE(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda10
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableMultidisplayTrackpadBackGesture();
        }
    }, false),
    ENABLE_MULTIPLE_DESKTOPS_BACKEND(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda12
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableMultipleDesktopsBackend();
        }
    }, false),
    ENABLE_MULTIPLE_DESKTOPS_FRONTEND(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda13
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableMultipleDesktopsFrontend();
        }
    }, false),
    ENABLE_PERSISTING_DISPLAY_SIZE_FOR_CONNECTED_DISPLAYS(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda14
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enablePersistingDisplaySizeForConnectedDisplays();
        }
    }, false),
    ENABLE_PER_DISPLAY_DESKTOP_WALLPAPER_ACTIVITY(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda15
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enablePerDisplayDesktopWallpaperActivity();
        }
    }, false),
    ENABLE_PER_DISPLAY_PACKAGE_CONTEXT_CACHE_IN_STATUSBAR_NOTIF(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda16
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enablePerDisplayPackageContextCacheInStatusbarNotif();
        }
    }, false),
    ENABLE_PROJECTED_DISPLAY_DESKTOP_MODE(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda17
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableProjectedDisplayDesktopMode();
        }
    }, false),
    ENABLE_TASKBAR_CONNECTED_DISPLAYS(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda18
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enableTaskbarConnectedDisplays();
        }
    }, true),
    ENTER_DESKTOP_BY_DEFAULT_ON_FREEFORM_DISPLAYS(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda19
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.enterDesktopByDefaultOnFreeformDisplays();
        }
    }, true),
    FORM_FACTOR_BASED_DESKTOP_FIRST_SWITCH(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda20
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.formFactorBasedDesktopFirstSwitch();
        }
    }, false),
    REPARENT_WINDOW_TOKEN_API(new BooleanSupplier() { // from class: android.window.DesktopExperienceFlags$$ExternalSyntheticLambda21
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flags.reparentWindowTokenApi();
        }
    }, true);

    public static final String SYSTEM_PROPERTY_NAME = "persist.wm.debug.desktop_experience_devopts";
    private static final String TAG = "DesktopExperienceFlags";
    private static Boolean sCachedToggleOverride;
    private final BooleanSupplier mFlagFunction;
    private final boolean mShouldOverrideByDevOption;

    public static class DesktopExperienceFlag {
        private final BooleanSupplier mFlagFunction;
        private final boolean mShouldOverrideByDevOption;

        public DesktopExperienceFlag(BooleanSupplier booleanSupplier, boolean z) {
            this.mFlagFunction = booleanSupplier;
            this.mShouldOverrideByDevOption = z;
        }

        public boolean isTrue() {
            return DesktopExperienceFlags.isFlagTrue(this.mFlagFunction, this.mShouldOverrideByDevOption);
        }
    }

    DesktopExperienceFlags(BooleanSupplier booleanSupplier, boolean z) {
        this.mFlagFunction = booleanSupplier;
        this.mShouldOverrideByDevOption = z;
    }

    public boolean isTrue() {
        return isFlagTrue(this.mFlagFunction, this.mShouldOverrideByDevOption);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isFlagTrue(BooleanSupplier booleanSupplier, boolean z) {
        if (z && Flags.showDesktopExperienceDevOption() && getToggleOverride()) {
            return true;
        }
        return booleanSupplier.getAsBoolean();
    }

    private static boolean getToggleOverride() {
        Boolean bool = sCachedToggleOverride;
        if (bool != null) {
            return bool.booleanValue();
        }
        boolean toggleOverrideFromSystem = getToggleOverrideFromSystem();
        sCachedToggleOverride = Boolean.valueOf(toggleOverrideFromSystem);
        Log.d(TAG, "Toggle override initialized to: " + toggleOverrideFromSystem);
        return toggleOverrideFromSystem;
    }

    private static boolean getToggleOverrideFromSystem() {
        return SystemProperties.getBoolean("persist.wm.debug.desktop_experience_devopts", false);
    }
}
