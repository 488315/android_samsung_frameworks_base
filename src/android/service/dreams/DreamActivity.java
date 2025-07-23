package android.service.dreams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.dreams.DreamService;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public class DreamActivity extends Activity {
    static final String EXTRA_CALLBACK = "binder";
    static final String EXTRA_DREAM_TITLE = "title";
    private DreamService.DreamActivityCallbacks mCallback;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String title = getTitle(getIntent());
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        DreamService.DreamActivityCallbacks callback = getCallback(getIntent());
        this.mCallback = callback;
        if (callback == null) {
            finishAndRemoveTask();
        } else {
            callback.onActivityCreated(this);
        }
    }

    public static void setTitle(Intent intent, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        intent.putExtra("title", charSequence);
    }

    public static String getTitle(Intent intent) {
        return intent.getStringExtra("title");
    }

    public static void setCallback(Intent intent, DreamService.DreamActivityCallbacks dreamActivityCallbacks) {
        intent.putExtra("binder", dreamActivityCallbacks);
    }

    public static DreamService.DreamActivityCallbacks getCallback(Intent intent) {
        IBinder binder = intent.getExtras().getBinder("binder");
        if (binder instanceof DreamService.DreamActivityCallbacks) {
            return (DreamService.DreamActivityCallbacks) binder;
        }
        return null;
    }

    @Override // android.app.Activity
    public void onDestroy() {
        DreamService.DreamActivityCallbacks dreamActivityCallbacks = this.mCallback;
        if (dreamActivityCallbacks != null) {
            dreamActivityCallbacks.onActivityDestroyed();
        }
        super.onDestroy();
    }
}
