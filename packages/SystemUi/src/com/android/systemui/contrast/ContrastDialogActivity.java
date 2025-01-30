package com.android.systemui.contrast;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.os.Bundle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.SecureSettings;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ContrastDialogActivity extends Activity {
    public final Context context;
    public final Executor mainExecutor;
    public final SecureSettings secureSettings;
    public final UiModeManager uiModeManager;
    public final UserTracker userTracker;

    public ContrastDialogActivity(Context context, Executor executor, UiModeManager uiModeManager, UserTracker userTracker, SecureSettings secureSettings) {
        this.context = context;
        this.mainExecutor = executor;
        this.uiModeManager = uiModeManager;
        this.userTracker = userTracker;
        this.secureSettings = secureSettings;
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new ContrastDialog(this.context, this.mainExecutor, this.uiModeManager, this.userTracker, this.secureSettings).show();
        finish();
    }
}
