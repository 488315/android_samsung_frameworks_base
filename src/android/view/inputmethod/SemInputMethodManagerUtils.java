package android.view.inputmethod;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.sec.enterprise.ApplicationRestrictionsManager;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.text.InputFilter;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class SemInputMethodManagerUtils {
    public static final String ACTION_DEACTIVATE = "actionDeactivate";
    public static final String ACTION_REQUEST_SELF_SHOW = "actionRequestShowSelf";
    public static final String ACTION_SHOW_TOOLKIT_HBD = "actionShowToolKitHbd";
    public static final String ACTION_UPDATE_RESULT_TOOLKIT_HBD = "actionUpdateResultToolKitHbd";
    public static final String ACTION_UPDATE_TOOLKIT_HBD = "actionUpdateToolKitHbd";
    public static final String CLASS_NAME_TOOLKIT_HONEYBOARD = "com.samsung.android.writingtoolkit.service.FakeHoneyBoardService";
    public static final String KEY_APP_SHOW_REQUESTED = "appShowRequested";
    public static final String KEY_SELECTED_TEXT = "selectedText";
    public static final String METHOD_ID_BIXBY = "com.samsung.android.bixby.voiceinput/com.samsung.android.svoiceime.BixbyDictVoiceReco";
    public static final String METHOD_ID_BIXBY_DICTATION = "com.samsung.android.bixby.service/.dictation.DictationInputMethodService";
    public static final String METHOD_ID_BIXBY_OLD = "com.samsung.android.svoiceime/.BixbyDictVoiceReco";
    public static final String METHOD_ID_CUSTOMIZED_SOGOU = "com.sohu.inputmethod.sogou.samsung/.SogouIME";
    public static final String METHOD_ID_GOOGLE_VOICE_TTS = "com.google.android.tts/com.google.android.apps.speech.tts.googletts.settings.asr.voiceime.VoiceInputMethodService";
    public static final String METHOD_ID_HONEYBOARD = "com.samsung.android.honeyboard/.service.HoneyBoardService";
    public static final String METHOD_ID_MOCK_IME = "com.android.cts.mockime/.MockIme";
    public static final String METHOD_ID_SWIFTKEY = "com.touchtype.swiftkey/com.touchtype.KeyboardService";
    public static final String METHOD_ID_TOOLKIT_HONEYBOARD = "com.samsung.android.honeyboard/com.samsung.android.writingtoolkit.service.FakeHoneyBoardService";
    public static final String PACKAGE_GOOGLE_VOICE = "com.google.android.googlequicksearchbox";
    public static final String PACKAGE_GOOGLE_VOICE_TTS = "com.google.android.tts";
    public static final String PACKAGE_HONEYBOARD = "com.samsung.android.honeyboard";
    private static final String TAG = "InputMethodManagerUtils";
    private static final String PROP_ENABLE_DEBUG_CALL_STACK = "persist.sys.ime.enable_debug_call_stack";
    static final boolean DEBUG_CALL_STACK = SystemProperties.getBoolean(PROP_ENABLE_DEBUG_CALL_STACK, false);

    static boolean isFlipLargeCoverScreenFolded() {
        return false;
    }

    static void putInfoInExtra(View view, EditorInfo editorInfo, String str) {
        int maxLengthForEditText;
        if (InputMethodManager.DEBUG_SEP) {
            Log.v(TAG, "Starting input: editorInfo=" + editorInfo);
        }
        if (view == null) {
            Log.d(TAG, "putInfoInExtra: view is null");
            return;
        }
        if ((view instanceof EditText) && (maxLengthForEditText = getMaxLengthForEditText((EditText) view)) >= 0) {
            editorInfo.extras.putInt("maxLength", maxLengthForEditText);
        }
        if (view.getDisplay() != null) {
            int displayId = view.getDisplay().getDisplayId();
            Log.d(TAG, str + " - Id : " + displayId);
            editorInfo.extras.putInt("displayId", displayId);
            return;
        }
        if (view.getContext().getDisplay() != null) {
            int displayId2 = view.getContext().getDisplay().getDisplayId();
            Log.d(TAG, str + " - Id from getContext : " + displayId2);
            editorInfo.extras.putInt("displayId", displayId2);
            return;
        }
        Log.d(TAG, "getDisplay is null");
    }

    static int getMaxLengthForEditText(EditText editText) {
        if (editText.onCheckIsTextEditor() && editText.isEnabled()) {
            InputFilter[] filters = editText.getFilters();
            int length = filters.length;
            for (int i = 0; i < length; i++) {
                InputFilter inputFilter = filters[i];
                if (inputFilter instanceof InputFilter.LengthFilter) {
                    try {
                        return ((InputFilter.LengthFilter) inputFilter).getMax();
                    } catch (Exception e) {
                        Log.v(TAG, "getMaxLengthForEditText LengthFilter = " + e);
                    }
                } else if (inputFilter != null) {
                    try {
                        Class[] clsArr = new Class[0];
                        return ((Integer) inputFilter.getClass().getMethod("getMaxLength", null).invoke(inputFilter, null)).intValue();
                    } catch (Exception e2) {
                        Log.v(TAG, "getMaxLengthForEditText InputFilter = " + e2);
                    }
                } else {
                    continue;
                }
            }
        }
        return -1;
    }

    static boolean isWritingToolkitDisallowedByKnox() {
        Bundle applicationRestrictions = getApplicationRestrictionsManager().getApplicationRestrictions("com.samsung.android.knox.galaxyai", UserHandle.myUserId());
        return (applicationRestrictions == null || applicationRestrictions.isEmpty() || !applicationRestrictions.containsKey("key_writing_toolkit") || applicationRestrictions.getBundle("key_writing_toolkit") == null || !applicationRestrictions.getBundle("key_writing_toolkit").getBoolean("grayout")) ? false : true;
    }

    static ApplicationRestrictionsManager getApplicationRestrictionsManager() {
        return EnterpriseDeviceManager.getInstance().getApplicationRestrictionsManager();
    }

    static boolean isDexDesktopDisplay(Context context, int i) {
        if (i == 0) {
            return false;
        }
        for (Display display : ((DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE)).getDisplays()) {
            if (display.getDisplayId() == i && (display.getFlags() & 131072) != 0) {
                return true;
            }
        }
        return false;
    }

    public static void showDexToast(Context context) {
        Toast.makeText(context, R.string.input_method_dex_cover_screen_toast, 0).show();
    }

    static boolean isCurrentMockIme(String str) {
        return str != null && METHOD_ID_MOCK_IME.equals(str);
    }
}
