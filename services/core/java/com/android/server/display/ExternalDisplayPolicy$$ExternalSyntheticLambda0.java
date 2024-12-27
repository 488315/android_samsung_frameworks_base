package com.android.server.display;

import android.R;
import android.util.Slog;

import com.android.server.display.notifications.DisplayNotificationManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
