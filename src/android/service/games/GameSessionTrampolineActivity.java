package android.service.games;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Slog;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes3.dex */
public final class GameSessionTrampolineActivity extends Activity {
    static final String FUTURE_KEY = "GameSessionTrampolineActivity.future";
    private static final String HAS_LAUNCHED_INTENT_KEY = "GameSessionTrampolineActivity.hasLaunchedIntent";
    static final String INTENT_KEY = "GameSessionTrampolineActivity.intent";
    static final String OPTIONS_KEY = "GameSessionTrampolineActivity.options";
    private static final int REQUEST_CODE = 1;
    private static final String TAG = "GameSessionTrampoline";
    private boolean mHasLaunchedIntent = false;

    public static Intent createIntent(Intent intent, Bundle bundle, AndroidFuture<GameSessionActivityResult> androidFuture) {
        Intent intent2 = new Intent();
        intent2.setComponent(new ComponentName("android", "android.service.games.GameSessionTrampolineActivity"));
        intent2.putExtra(INTENT_KEY, intent);
        intent2.putExtra(OPTIONS_KEY, bundle);
        intent2.putExtra(FUTURE_KEY, androidFuture);
        return intent2;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        GameSessionTrampolineActivity gameSessionTrampolineActivity;
        super.onCreate(bundle);
        if (bundle != null) {
            this.mHasLaunchedIntent = bundle.getBoolean(HAS_LAUNCHED_INTENT_KEY);
        }
        if (this.mHasLaunchedIntent) {
            return;
        }
        this.mHasLaunchedIntent = true;
        try {
            gameSessionTrampolineActivity = this;
        } catch (Exception e) {
            e = e;
            gameSessionTrampolineActivity = this;
        }
        try {
            gameSessionTrampolineActivity.startActivityAsCaller((Intent) getIntent().getParcelableExtra(INTENT_KEY, Intent.class), getIntent().getBundleExtra(OPTIONS_KEY), false, getUserId(), 1);
        } catch (Exception e2) {
            e = e2;
            Slog.w(TAG, "Unable to launch activity from game session");
            ((AndroidFuture) gameSessionTrampolineActivity.getIntent().getParcelableExtra(FUTURE_KEY, AndroidFuture.class)).completeExceptionally(e);
            gameSessionTrampolineActivity.finish();
            gameSessionTrampolineActivity.overridePendingTransition(0, 0);
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(HAS_LAUNCHED_INTENT_KEY, this.mHasLaunchedIntent);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i != 1) {
            throw new IllegalStateException("Unexpected request code: " + i);
        }
        ((AndroidFuture) getIntent().getParcelableExtra(FUTURE_KEY, AndroidFuture.class)).complete(new GameSessionActivityResult(i2, intent));
        finish();
        overridePendingTransition(0, 0);
    }
}
