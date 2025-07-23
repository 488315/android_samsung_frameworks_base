package android.app.contextualsearch;

import android.annotation.SystemApi;
import android.app.contextualsearch.IContextualSearchManager;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public final class ContextualSearchManager {

    @SystemApi
    public static final String ACTION_LAUNCH_CONTEXTUAL_SEARCH = "android.app.contextualsearch.action.LAUNCH_CONTEXTUAL_SEARCH";
    private static final boolean DEBUG = false;

    @SystemApi
    public static final int ENTRYPOINT_LONG_PRESS_HOME = 2;

    @SystemApi
    public static final int ENTRYPOINT_LONG_PRESS_META = 10;

    @SystemApi
    public static final int ENTRYPOINT_LONG_PRESS_NAV_HANDLE = 1;

    @SystemApi
    public static final int ENTRYPOINT_LONG_PRESS_OVERVIEW = 3;

    @SystemApi
    public static final int ENTRYPOINT_OVERVIEW_ACTION = 4;

    @SystemApi
    public static final int ENTRYPOINT_OVERVIEW_MENU = 5;

    @SystemApi
    public static final int ENTRYPOINT_SYSTEM_ACTION = 9;

    @SystemApi
    public static final String EXTRA_ENTRYPOINT = "android.app.contextualsearch.extra.ENTRYPOINT";

    @SystemApi
    public static final String EXTRA_FLAG_SECURE_FOUND = "android.app.contextualsearch.extra.FLAG_SECURE_FOUND";
    public static final String EXTRA_INVOCATION_TIME_MS = "android.app.contextualsearch.extra.INVOCATION_TIME_MS";
    public static final String EXTRA_IS_AUDIO_PLAYING = "android.app.contextualsearch.extra.IS_AUDIO_PLAYING";

    @SystemApi
    public static final String EXTRA_IS_MANAGED_PROFILE_VISIBLE = "android.app.contextualsearch.extra.IS_MANAGED_PROFILE_VISIBLE";

    @SystemApi
    public static final String EXTRA_SCREENSHOT = "android.app.contextualsearch.extra.SCREENSHOT";

    @SystemApi
    public static final String EXTRA_TOKEN = "android.app.contextualsearch.extra.TOKEN";

    @SystemApi
    public static final String EXTRA_VISIBLE_PACKAGE_NAMES = "android.app.contextualsearch.extra.VISIBLE_PACKAGE_NAMES";
    public static final String FEATURE_CONTEXTUAL_SEARCH = "com.google.android.feature.CONTEXTUAL_SEARCH";
    private final IContextualSearchManager mService = IContextualSearchManager.Stub.asInterface(ServiceManager.getService(Context.CONTEXTUAL_SEARCH_SERVICE));
    private static final Set<Integer> VALID_ENTRYPOINT_VALUES = new HashSet(Arrays.asList(1, 2, 3, 4, 5, 9, 10));
    private static final String TAG = "ContextualSearchManager";

    @Retention(RetentionPolicy.SOURCE)
    public @interface Entrypoint {
    }

    @SystemApi
    public void startContextualSearch(int i) {
        if (!VALID_ENTRYPOINT_VALUES.contains(Integer.valueOf(i))) {
            throw new IllegalArgumentException("Invalid entrypoint: " + i);
        }
        try {
            this.mService.startContextualSearch(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void startContextualSearch() {
        try {
            this.mService.startContextualSearchForForegroundApp();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
