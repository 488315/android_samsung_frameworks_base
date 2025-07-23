package android.app;

import android.content.ComponentName;
import android.os.Bundle;
import android.util.AndroidRuntimeException;

/* loaded from: classes.dex */
public class RemoteServiceException extends AndroidRuntimeException {
    public RemoteServiceException(String str) {
        super(str);
    }

    public RemoteServiceException(String str, Throwable th) {
        super(str, th);
    }

    public static class ForegroundServiceDidNotStartInTimeException extends RemoteServiceException {
        private static final String KEY_SERVICE_CLASS_NAME = "serviceclassname";
        public static final int TYPE_ID = 1;

        public ForegroundServiceDidNotStartInTimeException(String str, Throwable th) {
            super(str, th);
        }

        public static Bundle createExtrasForService(ComponentName componentName) {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_SERVICE_CLASS_NAME, componentName.getClassName());
            return bundle;
        }

        public static String getServiceClassNameFromExtras(Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            return bundle.getString(KEY_SERVICE_CLASS_NAME);
        }
    }

    public static class ForegroundServiceDidNotStopInTimeException extends RemoteServiceException {
        private static final String KEY_SERVICE_CLASS_NAME = "serviceclassname";
        public static final int TYPE_ID = 7;

        public ForegroundServiceDidNotStopInTimeException(String str, Throwable th) {
            super(str, th);
        }

        public static Bundle createExtrasForService(ComponentName componentName) {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_SERVICE_CLASS_NAME, componentName.getClassName());
            return bundle;
        }

        public static String getServiceClassNameFromExtras(Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            return bundle.getString(KEY_SERVICE_CLASS_NAME);
        }
    }

    public static class CannotPostForegroundServiceNotificationException extends RemoteServiceException {
        public static final int TYPE_ID = 2;

        public CannotPostForegroundServiceNotificationException(String str) {
            super(str);
        }
    }

    public static class BadForegroundServiceNotificationException extends RemoteServiceException {
        public static final int TYPE_ID = 3;

        public BadForegroundServiceNotificationException(String str) {
            super(str);
        }
    }

    public static class BadUserInitiatedJobNotificationException extends RemoteServiceException {
        public static final int TYPE_ID = 6;

        public BadUserInitiatedJobNotificationException(String str) {
            super(str);
        }
    }

    public static class MissingRequestPasswordComplexityPermissionException extends RemoteServiceException {
        public static final int TYPE_ID = 4;

        public MissingRequestPasswordComplexityPermissionException(String str) {
            super(str);
        }
    }

    public static class CrashedByAdbException extends RemoteServiceException {
        public static final int TYPE_ID = 5;

        public CrashedByAdbException(String str) {
            super(str);
        }
    }
}
