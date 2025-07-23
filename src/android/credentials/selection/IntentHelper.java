package android.credentials.selection;

import android.annotation.SystemApi;
import android.content.Intent;
import android.os.ResultReceiver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@SystemApi
/* loaded from: classes.dex */
public final class IntentHelper {
    public static CancelSelectionRequest extractCancelUiRequest(Intent intent) {
        return (CancelSelectionRequest) intent.getParcelableExtra(CancelSelectionRequest.EXTRA_CANCEL_UI_REQUEST, CancelSelectionRequest.class);
    }

    public static RequestInfo extractRequestInfo(Intent intent) {
        return (RequestInfo) intent.getParcelableExtra(RequestInfo.EXTRA_REQUEST_INFO, RequestInfo.class);
    }

    public static List<GetCredentialProviderInfo> extractGetCredentialProviderInfoList(Intent intent) {
        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(ProviderData.EXTRA_ENABLED_PROVIDER_DATA_LIST, GetCredentialProviderData.class);
        if (parcelableArrayListExtra == null) {
            return Collections.EMPTY_LIST;
        }
        return parcelableArrayListExtra.stream().map(new Function() { // from class: android.credentials.selection.IntentHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((GetCredentialProviderData) obj).toGetCredentialProviderInfo();
            }
        }).toList();
    }

    public static List<CreateCredentialProviderInfo> extractCreateCredentialProviderInfoList(Intent intent) {
        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(ProviderData.EXTRA_ENABLED_PROVIDER_DATA_LIST, CreateCredentialProviderData.class);
        if (parcelableArrayListExtra == null) {
            return Collections.EMPTY_LIST;
        }
        return parcelableArrayListExtra.stream().map(new Function() { // from class: android.credentials.selection.IntentHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((CreateCredentialProviderData) obj).toCreateCredentialProviderInfo();
            }
        }).toList();
    }

    public static List<DisabledProviderInfo> extractDisabledProviderInfoList(Intent intent) {
        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(ProviderData.EXTRA_DISABLED_PROVIDER_DATA_LIST, DisabledProviderData.class);
        if (parcelableArrayListExtra == null) {
            return Collections.EMPTY_LIST;
        }
        return parcelableArrayListExtra.stream().map(new Function() { // from class: android.credentials.selection.IntentHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DisabledProviderData) obj).toDisabledProviderInfo();
            }
        }).toList();
    }

    public static ResultReceiver extractResultReceiver(Intent intent) {
        return (ResultReceiver) intent.getParcelableExtra(Constants.EXTRA_RESULT_RECEIVER, ResultReceiver.class);
    }

    private IntentHelper() {
    }
}
