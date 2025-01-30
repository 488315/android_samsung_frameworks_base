package android.view.inputmethod;

import android.p009os.SystemProperties;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class SemInputMethodManagerUtils {
    public static final String METHOD_ID_BIXBY = "com.samsung.android.bixby.voiceinput/com.samsung.android.svoiceime.BixbyDictVoiceReco";
    public static final String METHOD_ID_BIXBY_DICTATION = "com.samsung.android.bixby.service/.dictation.DictationInputMethodService";
    public static final String METHOD_ID_BIXBY_OLD = "com.samsung.android.svoiceime/.BixbyDictVoiceReco";
    public static final String METHOD_ID_CUSTOMIZED_SOGOU = "com.sohu.inputmethod.sogou.samsung/.SogouIME";
    public static final String METHOD_ID_GOOGLE_VOICE_TTS = "com.google.android.tts/com.google.android.apps.speech.tts.googletts.settings.asr.voiceime.VoiceInputMethodService";
    public static final String METHOD_ID_HONEYBOARD = "com.samsung.android.honeyboard/.service.HoneyBoardService";
    public static final String METHOD_ID_SWIFTKEY = "com.touchtype.swiftkey/com.touchtype.KeyboardService";
    public static final String PACKAGE_GOOGLE_VOICE = "com.google.android.googlequicksearchbox";
    public static final String PACKAGE_GOOGLE_VOICE_TTS = "com.google.android.tts";
    public static final String PACKAGE_HONEYBOARD = "com.samsung.android.honeyboard";
    private static final String TAG = "InputMethodManagerUtils";
    private static final String PROP_ENABLE_DEBUG_CALL_STACK = "persist.sys.ime.enable_debug_call_stack";
    static final boolean DEBUG_CALL_STACK = SystemProperties.getBoolean(PROP_ENABLE_DEBUG_CALL_STACK, false);

    static void putInfoInExtra(View view, EditorInfo editorInfo) {
        int maxLength;
        if (InputMethodManager.DEBUG_SEP) {
            Log.m100v(TAG, "Starting input: editorInfo=" + editorInfo);
        }
        if ((view instanceof EditText) && (maxLength = getMaxLengthForEditText((EditText) view)) >= 0) {
            editorInfo.extras.putInt("maxLength", maxLength);
        }
        if (view.getDisplay() != null) {
            int displayId = view.getDisplay().getDisplayId();
            Log.m94d(TAG, "startInputInner - Id : " + displayId);
            editorInfo.extras.putInt("displayId", displayId);
        } else {
            if (view.getContext().getDisplay() != null) {
                int displayId2 = view.getContext().getDisplay().getDisplayId();
                Log.m94d(TAG, "startInputInner - Id from getContext : " + displayId2);
                editorInfo.extras.putInt("displayId", displayId2);
                return;
            }
            Log.m94d(TAG, "getDisplay is null");
        }
    }

    static int getMaxLengthForEditText(EditText editText) {
        if (!editText.onCheckIsTextEditor() || !editText.isEnabled()) {
            return -1;
        }
        InputFilter[] filters = editText.getFilters();
        int length = filters.length;
        for (int i = 0; i < length; i++) {
            InputFilter filter = filters[i];
            if (filter instanceof InputFilter.LengthFilter) {
                try {
                    int maxLength = ((InputFilter.LengthFilter) filter).getMax();
                    return maxLength;
                } catch (Exception e) {
                    Log.m100v(TAG, "getMaxLengthForEditText LengthFilter = " + e);
                }
            } else if (filter != null) {
                try {
                    Method m = filter.getClass().getMethod("getMaxLength", new Class[0]);
                    Object value = m.invoke(filter, new Object[0]);
                    int maxLength2 = ((Integer) value).intValue();
                    return maxLength2;
                } catch (Exception e2) {
                    Log.m100v(TAG, "getMaxLengthForEditText InputFilter = " + e2);
                }
            } else {
                continue;
            }
        }
        return -1;
    }
}
