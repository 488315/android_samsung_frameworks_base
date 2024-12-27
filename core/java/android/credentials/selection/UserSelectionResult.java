package android.credentials.selection;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.android.internal.util.Preconditions;

@SystemApi
/* loaded from: classes.dex */
public final class UserSelectionResult {
    private final String mEntryKey;
    private final String mEntrySubkey;
    private final String mProviderId;
    private ProviderPendingIntentResponse mProviderPendingIntentResponse;

    public static void sendUserSelectionResult(
            ResultReceiver resultReceiver, UserSelectionResult userSelectionResult) {
        UserSelectionDialogResult result = userSelectionResult.toUserSelectionDialogResult();
        Bundle resultData = new Bundle();
        UserSelectionDialogResult.addToBundle(result, resultData);
        resultReceiver.send(2, resultData);
    }

    public UserSelectionResult(
            String providerId,
            String entryKey,
            String entrySubkey,
            ProviderPendingIntentResponse providerPendingIntentResponse) {
        this.mProviderId = (String) Preconditions.checkStringNotEmpty(providerId);
        this.mEntryKey = (String) Preconditions.checkStringNotEmpty(entryKey);
        this.mEntrySubkey = (String) Preconditions.checkStringNotEmpty(entrySubkey);
        this.mProviderPendingIntentResponse = providerPendingIntentResponse;
    }

    public String getProviderId() {
        return this.mProviderId;
    }

    public String getEntryKey() {
        return this.mEntryKey;
    }

    public String getEntrySubkey() {
        return this.mEntrySubkey;
    }

    public ProviderPendingIntentResponse getPendingIntentProviderResponse() {
        return this.mProviderPendingIntentResponse;
    }

    UserSelectionDialogResult toUserSelectionDialogResult() {
        return new UserSelectionDialogResult(
                null,
                this.mProviderId,
                this.mEntryKey,
                this.mEntrySubkey,
                this.mProviderPendingIntentResponse);
    }
}
