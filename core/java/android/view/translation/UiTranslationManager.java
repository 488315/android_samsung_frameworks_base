package android.view.translation;

import android.annotation.SystemApi;
import android.app.assist.ActivityId;
import android.content.ComponentName;
import android.content.Context;
import android.icu.util.ULocale;
import android.os.Binder;
import android.os.Bundle;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.view.autofill.AutofillId;

import com.android.internal.util.FunctionalUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public final class UiTranslationManager {
    public static final String EXTRA_PACKAGE_NAME = "package_name";
    public static final String EXTRA_SOURCE_LOCALE = "source_locale";
    public static final String EXTRA_STATE = "state";
    public static final String EXTRA_TARGET_LOCALE = "target_locale";
    public static final String LOG_TAG = "UiTranslation";
    public static final int STATE_UI_TRANSLATION_FINISHED = 3;
    public static final int STATE_UI_TRANSLATION_PAUSED = 1;
    public static final int STATE_UI_TRANSLATION_RESUMED = 2;
    public static final int STATE_UI_TRANSLATION_STARTED = 0;
    private static final String TAG = "UiTranslationManager";
    private final Map<UiTranslationStateCallback, IRemoteCallback> mCallbacks = new ArrayMap();
    private final Context mContext;
    private final ITranslationManager mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface UiTranslationState {}

    public UiTranslationManager(Context context, ITranslationManager service) {
        this.mContext = (Context) Objects.requireNonNull(context);
        this.mService = service;
    }

    @SystemApi
    @Deprecated
    public void startTranslation(
            TranslationSpec sourceSpec,
            TranslationSpec targetSpec,
            List<AutofillId> viewIds,
            ActivityId activityId) {
        startTranslation(
                sourceSpec,
                targetSpec,
                viewIds,
                activityId,
                new UiTranslationSpec.Builder().setShouldPadContentForCompat(true).build());
    }

    @SystemApi
    public void startTranslation(
            TranslationSpec sourceSpec,
            TranslationSpec targetSpec,
            List<AutofillId> viewIds,
            ActivityId activityId,
            UiTranslationSpec uiTranslationSpec) {
        Objects.requireNonNull(sourceSpec);
        Objects.requireNonNull(targetSpec);
        Objects.requireNonNull(viewIds);
        Objects.requireNonNull(activityId);
        Objects.requireNonNull(activityId.getToken());
        Objects.requireNonNull(uiTranslationSpec);
        if (viewIds.size() == 0) {
            throw new IllegalArgumentException("Invalid empty views: " + viewIds);
        }
        try {
            this.mService.updateUiTranslationState(
                    0,
                    sourceSpec,
                    targetSpec,
                    viewIds,
                    activityId.getToken(),
                    activityId.getTaskId(),
                    uiTranslationSpec,
                    this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void finishTranslation(ActivityId activityId) {
        try {
            Objects.requireNonNull(activityId);
            Objects.requireNonNull(activityId.getToken());
            this.mService.updateUiTranslationState(
                    3,
                    null,
                    null,
                    null,
                    activityId.getToken(),
                    activityId.getTaskId(),
                    null,
                    this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void pauseTranslation(ActivityId activityId) {
        try {
            Objects.requireNonNull(activityId);
            Objects.requireNonNull(activityId.getToken());
            this.mService.updateUiTranslationState(
                    1,
                    null,
                    null,
                    null,
                    activityId.getToken(),
                    activityId.getTaskId(),
                    null,
                    this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void resumeTranslation(ActivityId activityId) {
        try {
            Objects.requireNonNull(activityId);
            Objects.requireNonNull(activityId.getToken());
            this.mService.updateUiTranslationState(
                    2,
                    null,
                    null,
                    null,
                    activityId.getToken(),
                    activityId.getTaskId(),
                    null,
                    this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerUiTranslationStateCallback(
            Executor executor, UiTranslationStateCallback callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        synchronized (this.mCallbacks) {
            if (this.mCallbacks.containsKey(callback)) {
                Log.w(
                        TAG,
                        "registerUiTranslationStateCallback: callback already registered;"
                            + " ignoring.");
                return;
            }
            IRemoteCallback remoteCallback =
                    new UiTranslationStateRemoteCallback(executor, callback);
            try {
                this.mService.registerUiTranslationStateCallback(
                        remoteCallback, this.mContext.getUserId());
                this.mCallbacks.put(callback, remoteCallback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterUiTranslationStateCallback(UiTranslationStateCallback callback) {
        Objects.requireNonNull(callback);
        synchronized (this.mCallbacks) {
            IRemoteCallback remoteCallback = this.mCallbacks.get(callback);
            if (remoteCallback == null) {
                Log.w(TAG, "unregisterUiTranslationStateCallback: callback not found; ignoring.");
                return;
            }
            try {
                this.mService.unregisterUiTranslationStateCallback(
                        remoteCallback, this.mContext.getUserId());
                this.mCallbacks.remove(callback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void onTranslationFinished(
            boolean activityDestroyed, ActivityId activityId, ComponentName componentName) {
        try {
            this.mService.onTranslationFinished(
                    activityDestroyed,
                    activityId.getToken(),
                    componentName,
                    this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class UiTranslationStateRemoteCallback extends IRemoteCallback.Stub {
        private final UiTranslationStateCallback mCallback;
        private final Executor mExecutor;
        private ULocale mSourceLocale;
        private ULocale mTargetLocale;

        UiTranslationStateRemoteCallback(Executor executor, UiTranslationStateCallback callback) {
            this.mExecutor = executor;
            this.mCallback = callback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendResult$1(final Bundle bundle) throws Exception {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.view.translation.UiTranslationManager$UiTranslationStateRemoteCallback$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            UiTranslationManager.UiTranslationStateRemoteCallback.this
                                    .lambda$sendResult$0(bundle);
                        }
                    });
        }

        @Override // android.os.IRemoteCallback
        public void sendResult(final Bundle bundle) {
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                            .ThrowingRunnable() { // from class:
                                                  // android.view.translation.UiTranslationManager$UiTranslationStateRemoteCallback$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            UiTranslationManager.UiTranslationStateRemoteCallback.this
                                    .lambda$sendResult$1(bundle);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: onStateChange, reason: merged with bridge method [inline-methods] */
        public void lambda$sendResult$0(Bundle bundle) {
            int state = bundle.getInt("state");
            String packageName = bundle.getString("package_name");
            switch (state) {
                case 0:
                    this.mSourceLocale =
                            (ULocale)
                                    bundle.getSerializable(
                                            UiTranslationManager.EXTRA_SOURCE_LOCALE,
                                            ULocale.class);
                    this.mTargetLocale =
                            (ULocale)
                                    bundle.getSerializable(
                                            UiTranslationManager.EXTRA_TARGET_LOCALE,
                                            ULocale.class);
                    this.mCallback.onStarted(this.mSourceLocale, this.mTargetLocale, packageName);
                    break;
                case 1:
                    this.mCallback.onPaused(packageName);
                    break;
                case 2:
                    this.mCallback.onResumed(this.mSourceLocale, this.mTargetLocale, packageName);
                    break;
                case 3:
                    this.mCallback.onFinished(packageName);
                    break;
                default:
                    Log.wtf(UiTranslationManager.TAG, "Unexpected translation state:" + state);
                    break;
            }
        }
    }
}
