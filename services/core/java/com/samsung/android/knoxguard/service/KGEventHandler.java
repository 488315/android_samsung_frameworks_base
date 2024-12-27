package com.samsung.android.knoxguard.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class KGEventHandler extends Handler {
    public static final String TAG = "KG.KGEventHandler";

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum SystemEvent {
        ON_BOOT_COMPLETED,
        ON_SETUP_WIZARD_COMPLETED,
        ON_USER_PRESENT,
        ON_PACKAGE_REPLACED_OR_REMOVED,
        ON_PACKAGE_DATA_CLEARED
    }

    public KGEventHandler(Looper looper) {
        super(looper);
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        super.handleMessage(message);
        SystemEvent systemEvent = SystemEvent.values()[message.what];
        Context context = (Context) message.obj;
        int ordinal = systemEvent.ordinal();
        if (ordinal == 0) {
            SystemIntentProcessor.handleBootCompleted(context);
            return;
        }
        if (ordinal == 1) {
            SystemIntentProcessor.handleSetupWizardCompleted(context);
            return;
        }
        if (ordinal == 2) {
            SystemIntentProcessor.handleUserPresent(context);
            return;
        }
        if (ordinal == 3) {
            SystemIntentProcessor.handlePackageReplacedOrRemoved(context, message.getData());
        } else {
            if (ordinal == 4) {
                SystemIntentProcessor.handlePackageDataCleared(context, message.getData());
                return;
            }
            Slog.w(TAG, "Unknown system event: " + systemEvent.name());
        }
    }
}
