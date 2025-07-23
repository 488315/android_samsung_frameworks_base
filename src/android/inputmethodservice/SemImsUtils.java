package android.inputmethodservice;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

/* loaded from: classes2.dex */
final class SemImsUtils {
    private static final String ACTION_INPUTMETHOD_STARTING_SSRM = "com.samsung.android.intent.action.INPUTMETHOD_STARTING";
    private static final String IS_VISIBLE_CANDIDATE = "AxT9IME.isVisibleCandidate";
    private static final String IS_VISIBLE_WINDOW = "AxT9IME.isVisibleWindow";
    private static final String IS_VISIBLE_WINDOW_SSRM = "visible";
    private static final String METHOD_ID_BIXBY_DICTATION = "com.samsung.android.bixby.service/.dictation.DictationInputMethodService";
    private static final String RESPONSE_AXT9INFO = "ResponseAxT9Info";
    static final String TAG = "InputMethodService";

    SemImsUtils() {
    }

    static int getNavigationBarHeight(Resources resources) {
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    static boolean isHoneyboard(String str) {
        return "com.samsung.android.honeyboard".equals(str);
    }

    static boolean isMockIme(String str) {
        return "com.android.cts.mockime".equals(str);
    }

    static boolean isBixbyDictationId(String str) {
        return "com.samsung.android.bixby.service/.dictation.DictationInputMethodService".equals(str);
    }

    static Context createDisplayContextAndSetTheme(Context context, int i, InputMethodManager inputMethodManager) {
        int curTokenDisplayId = inputMethodManager.getCurTokenDisplayId();
        Log.d(TAG, "onCreate: FocusDisplayId=" + inputMethodManager.getCurrentFocusDisplayID() + ", CurTokenDisplayId " + curTokenDisplayId);
        if (isHoneyboard(context.getPackageName())) {
            for (Display display : ((DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE)).getDisplays(null)) {
                if (display.getDisplayId() == curTokenDisplayId) {
                    Context createDisplayContext = context.createDisplayContext(display);
                    createDisplayContext.setTheme(i);
                    return createDisplayContext;
                }
            }
        }
        Log.d(TAG, "createDisplayContextAndSetTheme: displayContext can't be created");
        return context;
    }

    static void sendBroadcastShownState(Context context, EditorInfo editorInfo, boolean z, int i) {
        Log.d(TAG, "sendBroadcastImeShownState: isInputViewShown=" + z + " candidatesVisibility=" + i);
        Intent intent = new Intent();
        intent.setAction(RESPONSE_AXT9INFO);
        intent.putExtra(IS_VISIBLE_WINDOW, z);
        intent.putExtra(IS_VISIBLE_CANDIDATE, i);
        intent.putExtra("PID", Process.myPid());
        if (editorInfo != null) {
            intent.putExtra("inputType", editorInfo.inputType);
            intent.putExtra("imeOptions", editorInfo.imeOptions);
        }
        if (context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL) == 0 || context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS) == 0) {
            context.sendBroadcastAsUser(intent, UserHandle.ALL);
        } else {
            context.sendBroadcast(intent);
        }
    }

    static void sendBroadcastForSSRM(Context context, boolean z) {
        Log.d(TAG, "sendInputViewShownStateSSRM(): " + z);
        Intent intent = new Intent();
        intent.setAction(ACTION_INPUTMETHOD_STARTING_SSRM);
        intent.addFlags(1073741824);
        intent.putExtra("visible", z);
        context.sendBroadcast(intent);
    }

    static int getPixel(Resources resources, int i) {
        return (int) TypedValue.applyDimension(1, i, resources.getDisplayMetrics());
    }
}
