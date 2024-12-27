package android.app.contextualsearch;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes.dex */
public final class ContextualSearchManager {
    public static final String ACTION_LAUNCH_CONTEXTUAL_SEARCH =
            "android.app.contextualsearch.action.LAUNCH_CONTEXTUAL_SEARCH";
    private static final boolean DEBUG = false;
    public static final int ENTRYPOINT_LONG_PRESS_HOME = 2;
    public static final int ENTRYPOINT_LONG_PRESS_META = 10;
    public static final int ENTRYPOINT_LONG_PRESS_NAV_HANDLE = 1;
    public static final int ENTRYPOINT_LONG_PRESS_OVERVIEW = 3;
    public static final int ENTRYPOINT_OVERVIEW_ACTION = 4;
    public static final int ENTRYPOINT_OVERVIEW_MENU = 5;
    public static final int ENTRYPOINT_SYSTEM_ACTION = 9;
    public static final String EXTRA_ENTRYPOINT = "android.app.contextualsearch.extra.ENTRYPOINT";
    public static final String EXTRA_FLAG_SECURE_FOUND =
            "android.app.contextualsearch.extra.FLAG_SECURE_FOUND";
    public static final String EXTRA_INVOCATION_TIME_MS =
            "android.app.contextualsearch.extra.INVOCATION_TIME_MS";
    public static final String EXTRA_IS_MANAGED_PROFILE_VISIBLE =
            "android.app.contextualsearch.extra.IS_MANAGED_PROFILE_VISIBLE";
    public static final String EXTRA_SCREENSHOT = "android.app.contextualsearch.extra.SCREENSHOT";
    public static final String EXTRA_TOKEN = "android.app.contextualsearch.extra.TOKEN";
    public static final String EXTRA_VISIBLE_PACKAGE_NAMES =
            "android.app.contextualsearch.extra.VISIBLE_PACKAGE_NAMES";
    private static final String TAG = ContextualSearchManager.class.getSimpleName();
    private final IContextualSearchManager mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Entrypoint {}

    public ContextualSearchManager() {
        IBinder b = ServiceManager.getService(Context.CONTEXTUAL_SEARCH_SERVICE);
        this.mService = IContextualSearchManager.Stub.asInterface(b);
    }

    public void startContextualSearch(int entrypoint) {
        try {
            this.mService.startContextualSearch(entrypoint);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
