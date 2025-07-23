package android.inputmethodservice;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.UserManager;
import android.util.Log;
import android.util.Printer;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;

/* loaded from: classes2.dex */
final class SemDesktopModeManagerWrapper {
    static final Uri DEX_CONTENT_URI = Uri.parse("content://0@com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
    static final Uri DEX_CONTENT_URI_FOR_ON_CHANGE = Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
    static final String SETTINGS_KEY_KEYBOARD_DEX = "keyboard_dex";
    static final String SETTINGS_KEY_TOUCH_KEYBOARD = "touch_keyboard";
    static final String TAG = "InputMethodService";
    int mClientDisplayId = -1;
    final InputMethodManager mImm;
    final SemDesktopModeManager mSemDesktopModeManager;

    SemDesktopModeManagerWrapper(Context context) {
        this.mSemDesktopModeManager = (SemDesktopModeManager) context.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
        this.mImm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    static Uri getDexKeyboardSettingsUri() {
        return Uri.withAppendedPath(DEX_CONTENT_URI, "keyboard_dex");
    }

    static Uri getDexKeyboardSettingsChangedUri() {
        return Uri.withAppendedPath(DEX_CONTENT_URI_FOR_ON_CHANGE, "keyboard_dex");
    }

    boolean shouldRegisterContentObserver(Context context) {
        return isUiServiceExist(context) && isSystemUser(context);
    }

    boolean shouldUseDexKeyboardSettings() {
        return (isNotDefaultDisplay() && isDeskTopMode()) || isDEXStandAloneMode();
    }

    void updateClientDisplayId(EditorInfo editorInfo) {
        if (editorInfo == null || editorInfo.extras == null) {
            return;
        }
        int i = editorInfo.extras.getInt("displayId");
        Log.d(TAG, "updateClientDisplayId: displayId=" + i + ", mClientDisplayId=" + this.mClientDisplayId);
        this.mClientDisplayId = i;
    }

    boolean getOnscreenKeyboardForDEXValue() {
        boolean dexSettingsValue = this.mImm.getDexSettingsValue("keyboard_dex", "0");
        Log.d(TAG, "getOnscreenKeyboardForDEXValue: showImeWithHardKeyboardForDEX() : " + dexSettingsValue);
        return dexSettingsValue;
    }

    private boolean isNotDefaultDisplay() {
        return this.mClientDisplayId != 0;
    }

    private boolean isDeskTopMode() {
        SemDesktopModeState desktopModeState = getDesktopModeState();
        if (desktopModeState == null) {
            Log.d(TAG, "isDeskTopMode: DesktopModeState null!");
            return false;
        }
        if (desktopModeState.enabled == 4 || desktopModeState.enabled == 3) {
            Log.d(TAG, "isDeskTopMode: desktop mode, state.enabled=" + desktopModeState.enabled);
            return true;
        }
        Log.d(TAG, "isDeskTopMode: no desktop mode, state.enabled=" + desktopModeState.enabled);
        return false;
    }

    private boolean isDEXStandAloneMode() {
        SemDesktopModeState desktopModeState = getDesktopModeState();
        if (desktopModeState == null) {
            Log.d(TAG, "isDEXStandAloneMode: DesktopModeState null!");
            return false;
        }
        int displayType = desktopModeState.getDisplayType();
        if (displayType == 101) {
            Log.d(TAG, "isDEXStandAloneMode: stand alone mode, displayType=" + displayType);
            return true;
        }
        Log.d(TAG, "isDEXStandAloneMode: no stand alone mode, displayType=" + displayType);
        return false;
    }

    private SemDesktopModeState getDesktopModeState() {
        SemDesktopModeManager semDesktopModeManager = this.mSemDesktopModeManager;
        if (semDesktopModeManager == null) {
            return null;
        }
        return semDesktopModeManager.getDesktopModeState();
    }

    private boolean isDualViewEnabled() {
        boolean dexSettingsValue = this.mImm.getDexSettingsValue("touch_keyboard", "false");
        Log.d(TAG, "isDualViewEnabled() : " + dexSettingsValue);
        return dexSettingsValue;
    }

    private boolean isUiServiceExist(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(SemDesktopModeManager.UI_SERVICE_PACKAGE, 0);
            if (applicationInfo != null) {
                return applicationInfo.enabled;
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w(TAG, "isPackageExists failed: unknown package com.sec.android.desktopmode.uiservice");
        }
        return false;
    }

    private boolean isSystemUser(Context context) {
        return ((UserManager) context.getSystemService("user")).isSystemUser();
    }

    void dumpDexMode(Printer printer) {
        printer.println("Input method service Dex state");
        printer.println("  DexDesktopMode=" + isDeskTopMode());
        printer.println("  DexStandAloneMode=" + isDEXStandAloneMode());
        printer.println("  DexShowOnScreenKeyboardInSamsungDex=" + getOnscreenKeyboardForDEXValue());
        printer.println("  DexDualViewEnabled=" + isDualViewEnabled());
    }
}
