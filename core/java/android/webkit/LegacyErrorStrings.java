package android.webkit;

import android.content.Context;
import android.util.Log;

import com.android.internal.R;

/* loaded from: classes4.dex */
class LegacyErrorStrings {
    private static final String LOGTAG = "Http";

    private LegacyErrorStrings() {}

    static String getString(int errorCode, Context context) {
        return context.getText(getResource(errorCode)).toString();
    }

    private static int getResource(int errorCode) {
        switch (errorCode) {
            case -15:
                break;
            case -14:
                break;
            case -13:
                break;
            case -12:
                break;
            case -11:
                break;
            case -10:
                break;
            case -9:
                break;
            case -8:
                break;
            case -7:
                break;
            case -6:
                break;
            case -5:
                break;
            case -4:
                break;
            case -3:
                break;
            case -2:
                break;
            case -1:
                break;
            case 0:
                break;
            default:
                Log.w(LOGTAG, "Using generic message for unknown error code: " + errorCode);
                break;
        }
        return R.string.httpError;
    }
}
