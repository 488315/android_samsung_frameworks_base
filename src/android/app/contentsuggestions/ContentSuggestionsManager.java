package android.app.contentsuggestions;

import android.annotation.SystemApi;
import android.app.contentsuggestions.IClassificationsCallback;
import android.app.contentsuggestions.ISelectionsCallback;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.SyncResultReceiver;
import java.util.List;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes.dex */
public final class ContentSuggestionsManager {
    public static final String EXTRA_BITMAP = "android.contentsuggestions.extra.BITMAP";
    private static final int SYNC_CALLS_TIMEOUT_MS = 5000;
    private static final String TAG = "ContentSuggestionsManager";
    private final IContentSuggestionsManager mService;
    private final int mUser;

    public interface ClassificationsCallback {
        void onContentClassificationsAvailable(int i, List<ContentClassification> list);
    }

    public interface SelectionsCallback {
        void onContentSelectionsAvailable(int i, List<ContentSelection> list);
    }

    public ContentSuggestionsManager(int i, IContentSuggestionsManager iContentSuggestionsManager) {
        this.mService = iContentSuggestionsManager;
        this.mUser = i;
    }

    public void provideContextImage(Bitmap bitmap, Bundle bundle) {
        IContentSuggestionsManager iContentSuggestionsManager = this.mService;
        if (iContentSuggestionsManager == null) {
            Log.e(TAG, "provideContextImage called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            iContentSuggestionsManager.provideContextBitmap(this.mUser, bitmap, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void provideContextImage(int i, Bundle bundle) {
        IContentSuggestionsManager iContentSuggestionsManager = this.mService;
        if (iContentSuggestionsManager == null) {
            Log.e(TAG, "provideContextImage called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            iContentSuggestionsManager.provideContextImage(this.mUser, i, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void suggestContentSelections(SelectionsRequest selectionsRequest, Executor executor, SelectionsCallback selectionsCallback) {
        IContentSuggestionsManager iContentSuggestionsManager = this.mService;
        if (iContentSuggestionsManager == null) {
            Log.e(TAG, "suggestContentSelections called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            iContentSuggestionsManager.suggestContentSelections(this.mUser, selectionsRequest, new SelectionsCallbackWrapper(selectionsCallback, executor));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void classifyContentSelections(ClassificationsRequest classificationsRequest, Executor executor, ClassificationsCallback classificationsCallback) {
        IContentSuggestionsManager iContentSuggestionsManager = this.mService;
        if (iContentSuggestionsManager == null) {
            Log.e(TAG, "classifyContentSelections called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            iContentSuggestionsManager.classifyContentSelections(this.mUser, classificationsRequest, new ClassificationsCallbackWrapper(classificationsCallback, executor));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyInteraction(String str, Bundle bundle) {
        IContentSuggestionsManager iContentSuggestionsManager = this.mService;
        if (iContentSuggestionsManager == null) {
            Log.e(TAG, "notifyInteraction called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            iContentSuggestionsManager.notifyInteraction(this.mUser, str, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isEnabled() {
        if (this.mService == null) {
            return false;
        }
        SyncResultReceiver syncResultReceiver = new SyncResultReceiver(5000);
        try {
            this.mService.isEnabled(this.mUser, syncResultReceiver);
            return syncResultReceiver.getIntResult() != 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SyncResultReceiver.TimeoutException unused) {
            throw new RuntimeException("Fail to get the enable status.");
        }
    }

    public void resetTemporaryService(int i) {
        IContentSuggestionsManager iContentSuggestionsManager = this.mService;
        if (iContentSuggestionsManager == null) {
            Log.e(TAG, "resetTemporaryService called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            iContentSuggestionsManager.resetTemporaryService(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTemporaryService(int i, String str, int i2) {
        IContentSuggestionsManager iContentSuggestionsManager = this.mService;
        if (iContentSuggestionsManager == null) {
            Log.e(TAG, "setTemporaryService called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            iContentSuggestionsManager.setTemporaryService(i, str, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDefaultServiceEnabled(int i, boolean z) {
        IContentSuggestionsManager iContentSuggestionsManager = this.mService;
        if (iContentSuggestionsManager == null) {
            Log.e(TAG, "setDefaultServiceEnabled called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            iContentSuggestionsManager.setDefaultServiceEnabled(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SelectionsCallbackWrapper extends ISelectionsCallback.Stub {
        private final SelectionsCallback mCallback;
        private final Executor mExecutor;

        SelectionsCallbackWrapper(SelectionsCallback selectionsCallback, Executor executor) {
            this.mCallback = selectionsCallback;
            this.mExecutor = executor;
        }

        @Override // android.app.contentsuggestions.ISelectionsCallback
        public void onContentSelectionsAvailable(final int i, final List<ContentSelection> list) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.app.contentsuggestions.ContentSuggestionsManager$SelectionsCallbackWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onContentSelectionsAvailable$0(i, list);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onContentSelectionsAvailable$0(int i, List list) {
            this.mCallback.onContentSelectionsAvailable(i, list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ClassificationsCallbackWrapper extends IClassificationsCallback.Stub {
        private final ClassificationsCallback mCallback;
        private final Executor mExecutor;

        ClassificationsCallbackWrapper(ClassificationsCallback classificationsCallback, Executor executor) {
            this.mCallback = classificationsCallback;
            this.mExecutor = executor;
        }

        @Override // android.app.contentsuggestions.IClassificationsCallback
        public void onContentClassificationsAvailable(final int i, final List<ContentClassification> list) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.app.contentsuggestions.ContentSuggestionsManager$ClassificationsCallbackWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onContentClassificationsAvailable$0(i, list);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onContentClassificationsAvailable$0(int i, List list) {
            this.mCallback.onContentClassificationsAvailable(i, list);
        }
    }
}
