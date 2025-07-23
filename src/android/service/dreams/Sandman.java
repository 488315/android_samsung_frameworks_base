package android.service.dreams;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.util.Slog;
import com.android.internal.R;

/* loaded from: classes3.dex */
public final class Sandman {
    private static final String TAG = "Sandman";

    private Sandman() {
    }

    public static boolean shouldStartDockApp(Context context, Intent intent) {
        ComponentName unflattenFromString = ComponentName.unflattenFromString(context.getResources().getString(R.string.config_somnambulatorComponent));
        ComponentName resolveActivity = intent.resolveActivity(context.getPackageManager());
        return (resolveActivity == null || resolveActivity.equals(unflattenFromString)) ? false : true;
    }

    public static void startDreamByUserRequest(Context context) {
        startDream(context, false);
    }

    public static void startDreamWhenDockedIfAppropriate(Context context) {
        if (!isScreenSaverEnabled(context) || !isScreenSaverActivatedOnDock(context)) {
            Slog.i(TAG, "Dreams currently disabled for docks.");
        } else {
            startDream(context, true);
        }
    }

    private static void startDream(Context context, boolean z) {
        try {
            IDreamManager asInterface = IDreamManager.Stub.asInterface(ServiceManager.getService(DreamService.DREAM_SERVICE));
            if (asInterface == null || asInterface.isDreaming()) {
                return;
            }
            if (z) {
                Slog.i(TAG, "Activating dream while docked.");
                ((PowerManager) context.getSystemService(PowerManager.class)).wakeUp(SystemClock.uptimeMillis(), 3, "android.service.dreams:DREAM");
            } else {
                Slog.i(TAG, "Activating dream by user request.");
            }
            asInterface.dream();
        } catch (RemoteException e) {
            Slog.e(TAG, "Could not start dream when docked.", e);
        }
    }

    private static boolean isScreenSaverEnabled(Context context) {
        return Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.SCREENSAVER_ENABLED, context.getResources().getBoolean(R.bool.config_dreamsEnabledByDefault) ? 1 : 0, -2) != 0;
    }

    private static boolean isScreenSaverActivatedOnDock(Context context) {
        return Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.SCREENSAVER_ACTIVATE_ON_DOCK, context.getResources().getBoolean(R.bool.config_dreamsActivatedOnDockByDefault) ? 1 : 0, -2) != 0;
    }
}
