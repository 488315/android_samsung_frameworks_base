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

    public static void sendUserSelectionResult(ResultReceiver resultReceiver, UserSelectionResult userSelectionResult) {
        UserSelectionDialogResult userSelectionDialogResult = userSelectionResult.toUserSelectionDialogResult();
        Bundle bundle = new Bundle();
        UserSelectionDialogResult.addToBundle(userSelectionDialogResult, bundle);
        resultReceiver.send(2, bundle);
    }

    public UserSelectionResult(String str, String str2, String str3, ProviderPendingIntentResponse providerPendingIntentResponse) {
        this.mProviderId = (String) Preconditions.checkStringNotEmpty(str);
        this.mEntryKey = (String) Preconditions.checkStringNotEmpty(str2);
        this.mEntrySubkey = (String) Preconditions.checkStringNotEmpty(str3);
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
        return new UserSelectionDialogResult(null, this.mProviderId, this.mEntryKey, this.mEntrySubkey, this.mProviderPendingIntentResponse);
    }
}
