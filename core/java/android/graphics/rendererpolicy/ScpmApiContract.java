package android.graphics.rendererpolicy;

import android.net.Uri;

/* loaded from: classes.dex */
interface ScpmApiContract {
    public static final String ACTION_CLEAR_DATA = "com.samsung.android.scpm.policy.CLEAR_DATA";
    public static final String ACTION_PREFIX = "com.samsung.android.scpm.policy.UPDATE.";
    public static final String AUTHORITY = "com.samsung.android.scpm.policy";
    public static final String URI_STRING = "content://com.samsung.android.scpm.policy/";
    public static final Uri URI = Uri.parse(URI_STRING);

    public interface Key {
        public static final String APP_ID = "appId";
        public static final String APP_SIGNATURE = "appSignature";
        public static final String PACKAGE_NAME = "packageName";
        public static final String RCODE = "rcode";
        public static final String RECEIVER_PACKAGE_NAME = "receiverPackageName";
        public static final String RESULT = "result";
        public static final String RMSG = "rmsg";
        public static final String TOKEN = "token";
        public static final String VERSION = "version";
    }

    public interface Method {
        public static final String GET_LAST_ERROR = "getLastError";
        public static final String GET_STATUS = "getStatus";
        public static final String INITIALIZE = "initialize";
        public static final String REGISTER = "register";
        public static final String UNREGISTER = "unregister";
    }

    public interface Result {
        public static final int FAIL = 2;
        public static final int SUCCESS = 1;
    }
}
