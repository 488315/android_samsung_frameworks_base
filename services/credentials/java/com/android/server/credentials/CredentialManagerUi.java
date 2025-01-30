package com.android.server.credentials;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.credentials.CredentialProviderInfo;
import android.credentials.ui.DisabledProviderData;
import android.credentials.ui.IntentFactory;
import android.credentials.ui.RequestInfo;
import android.credentials.ui.UserSelectionDialogResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.service.credentials.CredentialProviderInfoFactory;
import android.util.Slog;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CredentialManagerUi {
    public final CredentialManagerUiCallback mCallbacks;
    public final Context mContext;
    public final Set mEnabledProviders;
    public final ResultReceiver mResultReceiver = new ResultReceiver(new Handler(Looper.getMainLooper())) { // from class: com.android.server.credentials.CredentialManagerUi.1
        @Override // android.os.ResultReceiver
        public void onReceiveResult(int i, Bundle bundle) {
            CredentialManagerUi.this.handleUiResult(i, bundle);
        }
    };
    public UiStatus mStatus = UiStatus.IN_PROGRESS;
    public final int mUserId;

    public interface CredentialManagerUiCallback {
        void onUiCancellation(boolean z);

        void onUiSelection(UserSelectionDialogResult userSelectionDialogResult);

        void onUiSelectorInvocationFailure();
    }

    enum UiStatus {
        IN_PROGRESS,
        USER_INTERACTION,
        NOT_STARTED,
        TERMINATED
    }

    public final void handleUiResult(int i, Bundle bundle) {
        if (i == 0) {
            this.mStatus = UiStatus.TERMINATED;
            this.mCallbacks.onUiCancellation(true);
            return;
        }
        if (i == 1) {
            this.mStatus = UiStatus.TERMINATED;
            this.mCallbacks.onUiCancellation(false);
            return;
        }
        if (i != 2) {
            if (i == 3) {
                this.mStatus = UiStatus.TERMINATED;
                this.mCallbacks.onUiSelectorInvocationFailure();
                return;
            } else {
                this.mStatus = UiStatus.IN_PROGRESS;
                this.mCallbacks.onUiSelectorInvocationFailure();
                return;
            }
        }
        this.mStatus = UiStatus.IN_PROGRESS;
        UserSelectionDialogResult fromResultData = UserSelectionDialogResult.fromResultData(bundle);
        if (fromResultData != null) {
            this.mCallbacks.onUiSelection(fromResultData);
        } else {
            Slog.i("CredentialManagerUi", "No selection found in UI result");
        }
    }

    public Intent createCancelIntent(IBinder iBinder, String str) {
        return IntentFactory.createCancelUiIntent(iBinder, true, str);
    }

    public CredentialManagerUi(Context context, int i, CredentialManagerUiCallback credentialManagerUiCallback, Set set) {
        this.mContext = context;
        this.mUserId = i;
        this.mCallbacks = credentialManagerUiCallback;
        this.mEnabledProviders = set;
    }

    public void setStatus(UiStatus uiStatus) {
        this.mStatus = uiStatus;
    }

    public UiStatus getStatus() {
        return this.mStatus;
    }

    public PendingIntent createPendingIntent(RequestInfo requestInfo, ArrayList arrayList) {
        return PendingIntent.getActivityAsUser(this.mContext, 0, IntentFactory.createCredentialSelectorIntent(requestInfo, arrayList, new ArrayList(CredentialProviderInfoFactory.getCredentialProviderServices(this.mContext, this.mUserId, 2, this.mEnabledProviders, new HashSet()).stream().filter(new Predicate() { // from class: com.android.server.credentials.CredentialManagerUi$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$createPendingIntent$0;
                lambda$createPendingIntent$0 = CredentialManagerUi.lambda$createPendingIntent$0((CredentialProviderInfo) obj);
                return lambda$createPendingIntent$0;
            }
        }).map(new Function() { // from class: com.android.server.credentials.CredentialManagerUi$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                DisabledProviderData lambda$createPendingIntent$1;
                lambda$createPendingIntent$1 = CredentialManagerUi.lambda$createPendingIntent$1((CredentialProviderInfo) obj);
                return lambda$createPendingIntent$1;
            }
        }).toList()), this.mResultReceiver).setAction(UUID.randomUUID().toString()), 67108864, null, UserHandle.of(this.mUserId));
    }

    public static /* synthetic */ boolean lambda$createPendingIntent$0(CredentialProviderInfo credentialProviderInfo) {
        return !credentialProviderInfo.isEnabled();
    }

    public static /* synthetic */ DisabledProviderData lambda$createPendingIntent$1(CredentialProviderInfo credentialProviderInfo) {
        return new DisabledProviderData(credentialProviderInfo.getComponentName().flattenToString());
    }
}
