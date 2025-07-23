package com.android.internal.app;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Slog;
import java.util.Objects;

/* loaded from: classes5.dex */
public class LaunchAfterAuthenticationActivity extends Activity {
    private static final String EXTRA_ON_SUCCESS_INTENT = "com.android.internal.app.extra.ON_SUCCESS_INTENT";
    private static final String TAG = "LaunchAfterAuthenticationActivity";

    public static Intent createLaunchAfterAuthenticationIntent(IntentSender intentSender) {
        return new Intent().setClassName("android", LaunchAfterAuthenticationActivity.class.getName()).putExtra(EXTRA_ON_SUCCESS_INTENT, intentSender).setFlags(276824064);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestDismissKeyguardIfNeeded((IntentSender) getIntent().getParcelableExtra(EXTRA_ON_SUCCESS_INTENT, IntentSender.class));
    }

    private void requestDismissKeyguardIfNeeded(final IntentSender intentSender) {
        KeyguardManager keyguardManager = (KeyguardManager) Objects.requireNonNull((KeyguardManager) getSystemService(KeyguardManager.class));
        if (keyguardManager.isKeyguardLocked()) {
            keyguardManager.requestDismissKeyguard(this, new KeyguardManager.KeyguardDismissCallback() { // from class: com.android.internal.app.LaunchAfterAuthenticationActivity.1
                @Override // android.app.KeyguardManager.KeyguardDismissCallback
                public void onDismissCancelled() {
                    LaunchAfterAuthenticationActivity.this.finish();
                }

                @Override // android.app.KeyguardManager.KeyguardDismissCallback
                public void onDismissSucceeded() {
                    IntentSender intentSender2 = intentSender;
                    if (intentSender2 != null) {
                        LaunchAfterAuthenticationActivity.this.onUnlocked(intentSender2);
                    }
                    LaunchAfterAuthenticationActivity.this.finish();
                }

                @Override // android.app.KeyguardManager.KeyguardDismissCallback
                public void onDismissError() {
                    Slog.e(LaunchAfterAuthenticationActivity.TAG, "Error while dismissing keyguard.");
                    LaunchAfterAuthenticationActivity.this.finish();
                }
            });
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUnlocked(IntentSender intentSender) {
        try {
            intentSender.sendIntent(this, 0, null, null, null);
        } catch (IntentSender.SendIntentException e) {
            Slog.e(TAG, "Error while sending original intent", e);
        }
    }
}
