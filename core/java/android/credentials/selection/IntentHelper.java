package android.credentials.selection;

import android.annotation.SystemApi;
import android.content.Intent;
import android.os.ResultReceiver;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@SystemApi
/* loaded from: classes.dex */
public final class IntentHelper {
    public static CancelSelectionRequest extractCancelUiRequest(Intent intent) {
        return (CancelSelectionRequest)
                intent.getParcelableExtra(
                        CancelSelectionRequest.EXTRA_CANCEL_UI_REQUEST,
                        CancelSelectionRequest.class);
    }

    public static RequestInfo extractRequestInfo(Intent intent) {
        return (RequestInfo)
                intent.getParcelableExtra(RequestInfo.EXTRA_REQUEST_INFO, RequestInfo.class);
    }

    public static List<GetCredentialProviderInfo> extractGetCredentialProviderInfoList(
            Intent intent) {
        List<GetCredentialProviderData> providerList =
                intent.getParcelableArrayListExtra(
                        ProviderData.EXTRA_ENABLED_PROVIDER_DATA_LIST,
                        GetCredentialProviderData.class);
        return providerList == null
                ? Collections.emptyList()
                : providerList.stream()
                        .map(
                                new Function() { // from class:
                                    // android.credentials.selection.IntentHelper$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return ((GetCredentialProviderData) obj)
                                                .toGetCredentialProviderInfo();
                                    }
                                })
                        .toList();
    }

    public static List<CreateCredentialProviderInfo> extractCreateCredentialProviderInfoList(
            Intent intent) {
        List<CreateCredentialProviderData> providerList =
                intent.getParcelableArrayListExtra(
                        ProviderData.EXTRA_ENABLED_PROVIDER_DATA_LIST,
                        CreateCredentialProviderData.class);
        return providerList == null
                ? Collections.emptyList()
                : providerList.stream()
                        .map(
                                new Function() { // from class:
                                    // android.credentials.selection.IntentHelper$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return ((CreateCredentialProviderData) obj)
                                                .toCreateCredentialProviderInfo();
                                    }
                                })
                        .toList();
    }

    public static List<DisabledProviderInfo> extractDisabledProviderInfoList(Intent intent) {
        List<DisabledProviderData> providerList =
                intent.getParcelableArrayListExtra(
                        ProviderData.EXTRA_DISABLED_PROVIDER_DATA_LIST, DisabledProviderData.class);
        return providerList == null
                ? Collections.emptyList()
                : providerList.stream()
                        .map(
                                new Function() { // from class:
                                    // android.credentials.selection.IntentHelper$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return ((DisabledProviderData) obj)
                                                .toDisabledProviderInfo();
                                    }
                                })
                        .toList();
    }

    public static ResultReceiver extractResultReceiver(Intent intent) {
        return (ResultReceiver)
                intent.getParcelableExtra(Constants.EXTRA_RESULT_RECEIVER, ResultReceiver.class);
    }

    private IntentHelper() {}
}
