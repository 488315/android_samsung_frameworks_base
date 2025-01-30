package android.inputmethodservice;

import android.content.Context;
import android.net.Uri;
import android.p009os.UserManager;
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
        return Uri.withAppendedPath(DEX_CONTENT_URI, SETTINGS_KEY_KEYBOARD_DEX);
    }

    static Uri getDexKeyboardSettingsChangedUri() {
        return Uri.withAppendedPath(DEX_CONTENT_URI_FOR_ON_CHANGE, SETTINGS_KEY_KEYBOARD_DEX);
    }

    boolean shouldRegisterContentObserver(Context context) {
        return isUiServiceExist(context) && isSystemUser(context);
    }

    boolean shouldUseDexKeyboardSettings() {
        return (isNotDefaultDisplay() && isDeskTopMode()) || isDEXStandAloneMode();
    }

    void updateClientDisplayId(EditorInfo editorInfo) {
        if (editorInfo != null && editorInfo.extras != null) {
            int displayId = editorInfo.extras.getInt("displayId");
            Log.m94d(TAG, "updateClientDisplayId: displayId=" + displayId + ", mClientDisplayId=" + this.mClientDisplayId);
            this.mClientDisplayId = displayId;
        }
    }

    boolean getOnscreenKeyboardForDEXValue() {
        boolean showImeWithHardKeyboardForDEX = this.mImm.getDexSettingsValue(SETTINGS_KEY_KEYBOARD_DEX, "0");
        Log.m94d(TAG, "getOnscreenKeyboardForDEXValue: showImeWithHardKeyboardForDEX() : " + showImeWithHardKeyboardForDEX);
        return showImeWithHardKeyboardForDEX;
    }

    private boolean isNotDefaultDisplay() {
        return this.mClientDisplayId != 0;
    }

    private boolean isDeskTopMode() {
        SemDesktopModeState state = getDesktopModeState();
        if (state == null) {
            Log.m94d(TAG, "isDeskTopMode: DesktopModeState null!");
            return false;
        }
        if (state.enabled == 4 || state.enabled == 3) {
            Log.m94d(TAG, "isDeskTopMode: desktop mode, state.enabled=" + state.enabled);
            return true;
        }
        Log.m94d(TAG, "isDeskTopMode: no desktop mode, state.enabled=" + state.enabled);
        return false;
    }

    private boolean isDEXStandAloneMode() {
        SemDesktopModeState state = getDesktopModeState();
        if (state == null) {
            Log.m94d(TAG, "isDEXStandAloneMode: DesktopModeState null!");
            return false;
        }
        int type = state.getDisplayType();
        if (type == 101) {
            Log.m94d(TAG, "isDEXStandAloneMode: stand alone mode, displayType=" + type);
            return true;
        }
        Log.m94d(TAG, "isDEXStandAloneMode: no stand alone mode, displayType=" + type);
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
        boolean rVal = this.mImm.getDexSettingsValue(SETTINGS_KEY_TOUCH_KEYBOARD, "false");
        Log.m94d(TAG, "isDualViewEnabled() : " + rVal);
        return rVal;
    }

    private boolean isUiServiceExist(Context context) {
        return false;
    }

    private boolean isSystemUser(Context context) {
        UserManager usrMgr = (UserManager) context.getSystemService("user");
        return usrMgr.isSystemUser();
    }

    void dumpDexMode(Printer p) {
        p.println("Input method service Dex state");
        p.println("  DexDesktopMode=" + isDeskTopMode());
        p.println("  DexStandAloneMode=" + isDEXStandAloneMode());
        p.println("  DexShowOnScreenKeyboardInSamsungDex=" + getOnscreenKeyboardForDEXValue());
        p.println("  DexDualViewEnabled=" + isDualViewEnabled());
    }
}
