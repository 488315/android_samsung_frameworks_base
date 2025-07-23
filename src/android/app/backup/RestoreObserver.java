package android.app.backup;

import android.annotation.SystemApi;

/* loaded from: classes.dex */
public abstract class RestoreObserver {
    public void onUpdate(int i, String str) {
    }

    public void restoreFinished(int i) {
    }

    @SystemApi
    public void restoreSetsAvailable(RestoreSet[] restoreSetArr) {
    }

    public void restoreStarting(int i) {
    }
}
