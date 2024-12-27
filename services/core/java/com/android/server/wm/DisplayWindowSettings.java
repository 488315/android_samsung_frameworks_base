package com.android.server.wm;

import android.provider.Settings;
import android.util.ArrayMap;
import android.view.DisplayInfo;

import com.samsung.android.rune.CoreRune;

public final class DisplayWindowSettings {
    public final WindowManagerService mService;
    public final DisplayWindowSettingsProvider mSettingsProvider;

    public DisplayWindowSettings(
            WindowManagerService windowManagerService,
            DisplayWindowSettingsProvider displayWindowSettingsProvider) {
        this.mService = windowManagerService;
        this.mSettingsProvider = displayWindowSettingsProvider;
    }

    public final void applyRotationSettingsToDisplayLocked(DisplayContent displayContent) {
        DisplayWindowSettings$SettingsProvider$SettingsEntry settings =
                this.mSettingsProvider.getSettings(displayContent.mDisplayInfo);
        if (CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY
                && settings.mIgnoreOrientationRequest == null) {
            displayContent.setIgnoreOrientationRequestOverrideIfNeeded();
            return;
        }
        Boolean bool = settings.mIgnoreOrientationRequest;
        displayContent.setIgnoreOrientationRequest(bool != null ? bool.booleanValue() : false);
        displayContent.mDisplayRotation.mAllowAllRotations = -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00af, code lost:

       if (r8.equals(r7.getOwnerPackageName()) != false) goto L44;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void applySettingsToDisplayLocked(
            com.android.server.wm.DisplayContent r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 309
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DisplayWindowSettings.applySettingsToDisplayLocked(com.android.server.wm.DisplayContent,"
                    + " boolean):void");
    }

    public final int getWindowingModeLocked(
            DisplayWindowSettings$SettingsProvider$SettingsEntry
                    displayWindowSettings$SettingsProvider$SettingsEntry,
            DisplayContent displayContent) {
        int i = displayWindowSettings$SettingsProvider$SettingsEntry.mWindowingMode;
        WindowManagerService windowManagerService = this.mService;
        if (i == 5 && !windowManagerService.mAtmService.mSupportsFreeformWindowManagement) {
            return 1;
        }
        if (i == 0) {
            return (windowManagerService.mAtmService.mSupportsFreeformWindowManagement
                            && (windowManagerService.mIsPc
                                    || displayContent.forceDesktopMode()
                                    || ((displayContent.isDefaultDisplay
                                                    && displayContent.isDexMode())
                                            || displayContent.mDisplayId == 2)))
                    ? 5
                    : 1;
        }
        return i;
    }

    public final void onDisplayRemoved(DisplayContent displayContent) {
        DisplayInfo displayInfo = displayContent.mDisplayInfo;
        DisplayWindowSettingsProvider.WritableSettings writableSettings =
                this.mSettingsProvider.mOverrideSettings;
        String identifier = writableSettings.getIdentifier(displayInfo);
        if (((ArrayMap) writableSettings.mSettings).containsKey(identifier)) {
            if (writableSettings.mVirtualDisplayIdentifiers.remove(identifier)
                    || ((DisplayWindowSettings$SettingsProvider$SettingsEntry)
                                    ((ArrayMap) writableSettings.mSettings).get(identifier))
                            .isEmpty()) {
                ((ArrayMap) writableSettings.mSettings).remove(identifier);
            }
        }
    }

    public final void setForcedDensity(DisplayInfo displayInfo, int i, int i2) {
        if (displayInfo.displayId == 0) {
            Settings.Secure.putStringForUser(
                    this.mService.mContext.getContentResolver(),
                    "display_density_forced",
                    i == 0 ? "" : Integer.toString(i),
                    i2);
        }
        DisplayWindowSettingsProvider displayWindowSettingsProvider = this.mSettingsProvider;
        DisplayWindowSettings$SettingsProvider$SettingsEntry overrideSettings =
                displayWindowSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mForcedDensity = i;
        displayWindowSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    public final void setForcedSize(DisplayContent displayContent, int i, int i2) {
        String str;
        if (displayContent.isDefaultDisplay) {
            if (i == 0 || i2 == 0) {
                str = "";
            } else {
                str = i + "," + i2;
            }
            Settings.Global.putString(
                    this.mService.mContext.getContentResolver(), "display_size_forced", str);
        }
        DisplayInfo displayInfo = displayContent.mDisplayInfo;
        DisplayWindowSettingsProvider displayWindowSettingsProvider = this.mSettingsProvider;
        DisplayWindowSettings$SettingsProvider$SettingsEntry overrideSettings =
                displayWindowSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mForcedWidth = i;
        overrideSettings.mForcedHeight = i2;
        displayWindowSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    public final void setNullableIgnoreOrientationRequest(
            DisplayContent displayContent, Boolean bool) {
        DisplayInfo displayInfo = displayContent.mDisplayInfo;
        DisplayWindowSettingsProvider displayWindowSettingsProvider = this.mSettingsProvider;
        DisplayWindowSettings$SettingsProvider$SettingsEntry overrideSettings =
                displayWindowSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mIgnoreOrientationRequest = bool;
        displayWindowSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    public final boolean shouldShowSystemDecorsLocked(DisplayContent displayContent) {
        int i = displayContent.mDisplayId;
        if (i == 0 || i == 2) {
            return true;
        }
        if (displayContent.isRemoteAppDisplay() || displayContent.isAppCastingDisplay()) {
            return false;
        }
        Boolean bool =
                this.mSettingsProvider.getSettings(displayContent.mDisplayInfo)
                        .mShouldShowSystemDecors;
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }
}
