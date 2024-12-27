package android.service.dreams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
        this.mCallback = getCallback(getIntent());
        if (this.mCallback == null) {
            finishAndRemoveTask();
        } else {
            this.mCallback.onActivityCreated(this);
        }
    }

    public static void setTitle(Intent intent, CharSequence title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        intent.putExtra("title", title);
    }

    public static String getTitle(Intent intent) {
        return intent.getStringExtra("title");
    }

    public static void setCallback(Intent intent, DreamService.DreamActivityCallbacks callback) {
        intent.putExtra("binder", callback);
    }

    public static DreamService.DreamActivityCallbacks getCallback(Intent intent) {
        Object binder = intent.getExtras().getBinder("binder");
        if (binder instanceof DreamService.DreamActivityCallbacks) {
            return (DreamService.DreamActivityCallbacks) binder;
        }
        return null;
    }

    @Override // android.app.Activity
    public void onDestroy() {
        if (this.mCallback != null) {
            this.mCallback.onActivityDestroyed();
        }
        super.onDestroy();
    }
}
