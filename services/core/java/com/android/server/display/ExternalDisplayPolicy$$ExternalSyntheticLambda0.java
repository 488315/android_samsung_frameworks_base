package com.android.server.display;

import android.R;
import android.util.Slog;

import com.android.server.display.notifications.DisplayNotificationManager;

public final /* synthetic */ class ExternalDisplayPolicy$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ DisplayNotificationManager f$0;

    @Override // java.lang.Runnable
    public final void run() {
        DisplayNotificationManager displayNotificationManager = this.f$0;
        if (displayNotificationManager.mConnectedDisplayErrorHandlingEnabled) {
            displayNotificationManager.sendErrorNotification(
                    displayNotificationManager.createErrorNotification(
                            R.string.fast_scroll_numeric_alphabet, R.drawable.jog_dial_dimple));
        } else {
            Slog.d(
                    "DisplayNotificationManager",
                    "onHighTemperatureExternalDisplayNotAllowed:"
                        + " mConnectedDisplayErrorHandlingEnabled is false");
        }
    }
}
