package android.inputmethodservice;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.p009os.Process;
import android.p009os.UserHandle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import com.samsung.android.feature.SemFloatingFeature;

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
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    static boolean isHoneyboard(String packageName) {
        return "com.samsung.android.honeyboard".equals(packageName);
    }

    static boolean isMockIme(String packageName) {
        return "com.android.cts.mockime".equals(packageName);
    }

    static boolean isBixbyDictationId(String id) {
        return "com.samsung.android.bixby.service/.dictation.DictationInputMethodService".equals(id);
    }

    static Context createDisplayContextAndSetTheme(Context context, int theme, InputMethodManager mImm) {
        int curTokenDisplayId = mImm.getCurTokenDisplayId();
        Log.m94d(TAG, "onCreate: FocusDisplayId=" + mImm.getCurrentFocusDisplayID() + ", CurTokenDisplayId " + curTokenDisplayId);
        if (isHoneyboard(context.getPackageName())) {
            DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
            Display[] displays = dm.getDisplays(null);
            for (Display display : displays) {
                if (display.getDisplayId() == curTokenDisplayId) {
                    Context displayContext = context.createDisplayContext(display);
                    displayContext.setTheme(theme);
                    return displayContext;
                }
            }
        }
        Log.m94d(TAG, "createDisplayContextAndSetTheme: displayContext can't be created");
        return context;
    }

    static void sendBroadcastShownState(Context context, EditorInfo editorInfo, boolean isInputViewShown, int candidatesVisibility) {
        Log.m94d(TAG, "sendBroadcastImeShownState: isInputViewShown=" + isInputViewShown + " candidatesVisibility=" + candidatesVisibility);
        Intent respInt = new Intent();
        respInt.setAction(RESPONSE_AXT9INFO);
        respInt.putExtra(IS_VISIBLE_WINDOW, isInputViewShown);
        respInt.putExtra(IS_VISIBLE_CANDIDATE, candidatesVisibility);
        respInt.putExtra("PID", Process.myPid());
        if (editorInfo != null) {
            respInt.putExtra("inputType", editorInfo.inputType);
            respInt.putExtra("imeOptions", editorInfo.imeOptions);
        }
        if (context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL) == 0 || context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS) == 0) {
            context.sendBroadcastAsUser(respInt, UserHandle.ALL);
        } else {
            context.sendBroadcast(respInt);
        }
    }

    static void sendBroadcastForSSRM(Context context, boolean isVisible) {
        Log.m94d(TAG, "sendInputViewShownStateSSRM(): " + isVisible);
        Intent respInt = new Intent();
        respInt.setAction(ACTION_INPUTMETHOD_STARTING_SSRM);
        respInt.addFlags(1073741824);
        respInt.putExtra("visible", isVisible);
        context.sendBroadcast(respInt);
    }

    static int getExtractSideMargin() {
        float secFloatingFeatureSystemuiConfigCornerRound = Float.parseFloat(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_CORNER_ROUND"));
        int extractSideMargin = (int) (((10.0f - secFloatingFeatureSystemuiConfigCornerRound) * secFloatingFeatureSystemuiConfigCornerRound) - 1.0f);
        Log.m94d(TAG, "getExtractSideMargin: margin=" + (extractSideMargin * 2));
        return extractSideMargin * 2;
    }

    static FrameLayout.LayoutParams getLayoutParamsExtractSideMargin(int extractSideMargin) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1, 48);
        layoutParams.setMarginStart(extractSideMargin);
        layoutParams.setMarginEnd(extractSideMargin);
        return layoutParams;
    }

    static int getPixel(Resources resources, int dp) {
        return (int) TypedValue.applyDimension(1, dp, resources.getDisplayMetrics());
    }
}
