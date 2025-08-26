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
import android.view.translation.UiTranslationSpec;
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
    public @interface UiTranslationState {
    }

    public UiTranslationManager(Context context, ITranslationManager iTranslationManager) {
        this.mContext = (Context) Objects.requireNonNull(context);
        this.mService = iTranslationManager;
    }

    @SystemApi
    @Deprecated
    public void startTranslation(TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, ActivityId activityId) {
        startTranslation(translationSpec, translationSpec2, list, activityId, new UiTranslationSpec.Builder().setShouldPadContentForCompat(true).build());
    }

    @SystemApi
    public void startTranslation(TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, ActivityId activityId, UiTranslationSpec uiTranslationSpec) {
        Objects.requireNonNull(translationSpec);
        Objects.requireNonNull(translationSpec2);
        Objects.requireNonNull(list);
        Objects.requireNonNull(activityId);
        Objects.requireNonNull(activityId.getToken());
        Objects.requireNonNull(uiTranslationSpec);
        if (list.size() == 0) {
            throw new IllegalArgumentException("Invalid empty views: " + list);
        }
        try {
            this.mService.updateUiTranslationState(0, translationSpec, translationSpec2, list, activityId.getToken(), activityId.getTaskId(), uiTranslationSpec, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void finishTranslation(ActivityId activityId) {
        try {
            Objects.requireNonNull(activityId);
            Objects.requireNonNull(activityId.getToken());
            this.mService.updateUiTranslationState(3, null, null, null, activityId.getToken(), activityId.getTaskId(), null, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void pauseTranslation(ActivityId activityId) {
        try {
            Objects.requireNonNull(activityId);
            Objects.requireNonNull(activityId.getToken());
            this.mService.updateUiTranslationState(1, null, null, null, activityId.getToken(), activityId.getTaskId(), null, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void resumeTranslation(ActivityId activityId) {
        try {
            Objects.requireNonNull(activityId);
            Objects.requireNonNull(activityId.getToken());
            this.mService.updateUiTranslationState(2, null, null, null, activityId.getToken(), activityId.getTaskId(), null, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerUiTranslationStateCallback(Executor executor, UiTranslationStateCallback uiTranslationStateCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(uiTranslationStateCallback);
        synchronized (this.mCallbacks) {
            if (this.mCallbacks.containsKey(uiTranslationStateCallback)) {
                Log.w(TAG, "registerUiTranslationStateCallback: callback already registered; ignoring.");
                return;
            }
            UiTranslationStateRemoteCallback uiTranslationStateRemoteCallback = new UiTranslationStateRemoteCallback(executor, uiTranslationStateCallback);
            try {
                this.mService.registerUiTranslationStateCallback(uiTranslationStateRemoteCallback, this.mContext.getUserId());
                this.mCallbacks.put(uiTranslationStateCallback, uiTranslationStateRemoteCallback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterUiTranslationStateCallback(UiTranslationStateCallback uiTranslationStateCallback) {
        Objects.requireNonNull(uiTranslationStateCallback);
        synchronized (this.mCallbacks) {
            IRemoteCallback iRemoteCallback = this.mCallbacks.get(uiTranslationStateCallback);
            if (iRemoteCallback == null) {
                Log.w(TAG, "unregisterUiTranslationStateCallback: callback not found; ignoring.");
                return;
            }
            try {
                this.mService.unregisterUiTranslationStateCallback(iRemoteCallback, this.mContext.getUserId());
                this.mCallbacks.remove(uiTranslationStateCallback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void onTranslationFinished(boolean z, ActivityId activityId, ComponentName componentName) {
        try {
            this.mService.onTranslationFinished(z, activityId.getToken(), componentName, this.mContext.getUserId());
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

        UiTranslationStateRemoteCallback(Executor executor, UiTranslationStateCallback uiTranslationStateCallback) {
            this.mExecutor = executor;
            this.mCallback = uiTranslationStateCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendResult$1(final Bundle bundle) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.view.translation.UiTranslationManager$UiTranslationStateRemoteCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendResult$0(bundle);
                }
            });
        }

        @Override // android.os.IRemoteCallback
        public void sendResult(final Bundle bundle) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.view.translation.UiTranslationManager$UiTranslationStateRemoteCallback$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$sendResult$1(bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: onStateChange, reason: merged with bridge method [inline-methods] */
        public void lambda$sendResult$0(Bundle bundle) {
            int i = bundle.getInt("state");
            String string = bundle.getString("package_name");
            if (i == 0) {
                this.mSourceLocale = (ULocale) bundle.getSerializable(UiTranslationManager.EXTRA_SOURCE_LOCALE, ULocale.class);
                ULocale uLocale = (ULocale) bundle.getSerializable(UiTranslationManager.EXTRA_TARGET_LOCALE, ULocale.class);
                this.mTargetLocale = uLocale;
                this.mCallback.onStarted(this.mSourceLocale, uLocale, string);
                return;
            }
            if (i == 1) {
                this.mCallback.onPaused(string);
                return;
            }
            if (i == 2) {
                this.mCallback.onResumed(this.mSourceLocale, this.mTargetLocale, string);
            } else {
                if (i == 3) {
                    this.mCallback.onFinished(string);
                    return;
                }
                Log.wtf(UiTranslationManager.TAG, "Unexpected translation state:" + i);
            }
        }
    }
}
