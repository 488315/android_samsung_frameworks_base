package android.credentials.selection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.credentials.selection.IntentCreationResult;
import android.media.MediaMetrics;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.credentials.flags.Flags;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class IntentFactory {
    private static final String TAG = "CredManIntentHelper";

    public static IntentCreationResult createCredentialSelectorIntentForAutofill(Context context, RequestInfo requestInfo, ArrayList<DisabledProviderData> disabledProviderDataList, ResultReceiver resultReceiver) {
        return createCredentialSelectorIntentInternal(context, requestInfo, disabledProviderDataList, resultReceiver);
    }

    public static IntentCreationResult createCredentialSelectorIntentForCredMan(Context context, RequestInfo requestInfo, ArrayList<ProviderData> enabledProviderDataList, ArrayList<DisabledProviderData> disabledProviderDataList, ResultReceiver resultReceiver) {
        IntentCreationResult result = createCredentialSelectorIntentInternal(context, requestInfo, disabledProviderDataList, resultReceiver);
        result.getIntent().putParcelableArrayListExtra(ProviderData.EXTRA_ENABLED_PROVIDER_DATA_LIST, enabledProviderDataList);
        return result;
    }

    public static Intent createCredentialSelectorIntent(Context context, RequestInfo requestInfo, ArrayList<ProviderData> enabledProviderDataList, ArrayList<DisabledProviderData> disabledProviderDataList, ResultReceiver resultReceiver) {
        return createCredentialSelectorIntentForCredMan(context, requestInfo, enabledProviderDataList, disabledProviderDataList, resultReceiver).getIntent();
    }

    public static Intent createCancelUiIntent(Context context, IBinder requestToken, boolean shouldShowCancellationUi, String appPackageName) {
        Intent intent = new Intent();
        IntentCreationResult.Builder intentResultBuilder = new IntentCreationResult.Builder(intent);
        setCredentialSelectorUiComponentName(context, intent, intentResultBuilder);
        intent.putExtra(CancelSelectionRequest.EXTRA_CANCEL_UI_REQUEST, new CancelSelectionRequest(new RequestToken(requestToken), shouldShowCancellationUi, appPackageName));
        return intent;
    }

    private static IntentCreationResult createCredentialSelectorIntentInternal(Context context, RequestInfo requestInfo, ArrayList<DisabledProviderData> disabledProviderDataList, ResultReceiver resultReceiver) {
        Intent intent = new Intent();
        IntentCreationResult.Builder intentResultBuilder = new IntentCreationResult.Builder(intent);
        setCredentialSelectorUiComponentName(context, intent, intentResultBuilder);
        intent.putParcelableArrayListExtra(ProviderData.EXTRA_DISABLED_PROVIDER_DATA_LIST, disabledProviderDataList);
        intent.putExtra(RequestInfo.EXTRA_REQUEST_INFO, requestInfo);
        intent.putExtra(Constants.EXTRA_RESULT_RECEIVER, toIpcFriendlyResultReceiver(resultReceiver));
        return intentResultBuilder.build();
    }

    private static void setCredentialSelectorUiComponentName(Context context, Intent intent, IntentCreationResult.Builder intentResultBuilder) {
        if (Flags.configurableSelectorUiEnabled()) {
            ComponentName componentName = getOemOverrideComponentName(context, intentResultBuilder);
            ComponentName fallbackUiComponentName = null;
            try {
                fallbackUiComponentName = ComponentName.unflattenFromString(Resources.getSystem().getString(R.string.config_fallbackCredentialManagerDialogComponent));
                intentResultBuilder.setFallbackUiPackageName(fallbackUiComponentName.getPackageName());
            } catch (Exception e) {
                Slog.w(TAG, "Fallback CredMan IU not found: " + e);
            }
            if (componentName == null) {
                componentName = fallbackUiComponentName;
            }
            intent.setComponent(componentName);
            return;
        }
        intent.setComponent(ComponentName.unflattenFromString(Resources.getSystem().getString(R.string.config_fallbackCredentialManagerDialogComponent)));
    }

    private static ComponentName getOemOverrideComponentName(Context context, IntentCreationResult.Builder intentResultBuilder) {
        ComponentName result = null;
        String oemComponentString = Resources.getSystem().getString(R.string.config_oemCredentialManagerDialogComponent);
        if (!TextUtils.isEmpty(oemComponentString)) {
            ComponentName oemComponentName = null;
            try {
                oemComponentName = ComponentName.unflattenFromString(oemComponentString);
            } catch (Exception e) {
                Slog.i(TAG, "Failed to parse OEM component name " + oemComponentString + ": " + e);
            }
            if (oemComponentName != null) {
                try {
                    intentResultBuilder.setOemUiPackageName(oemComponentName.getPackageName());
                    ActivityInfo info = context.getPackageManager().getActivityInfo(oemComponentName, PackageManager.ComponentInfoFlags.of(1048576L));
                    boolean oemComponentEnabled = info.enabled;
                    int runtimeComponentEnabledState = context.getPackageManager().getComponentEnabledSetting(oemComponentName);
                    if (runtimeComponentEnabledState == 1) {
                        oemComponentEnabled = true;
                    } else if (runtimeComponentEnabledState == 2) {
                        oemComponentEnabled = false;
                    }
                    if (oemComponentEnabled && info.exported) {
                        intentResultBuilder.setOemUiUsageStatus(IntentCreationResult.OemUiUsageStatus.SUCCESS);
                        Slog.i(TAG, "Found enabled oem CredMan UI component." + oemComponentString);
                        result = oemComponentName;
                    } else {
                        intentResultBuilder.setOemUiUsageStatus(IntentCreationResult.OemUiUsageStatus.OEM_UI_CONFIG_SPECIFIED_FOUND_BUT_NOT_ENABLED);
                        Slog.i(TAG, "Found enabled oem CredMan UI component but it was not enabled.");
                    }
                } catch (PackageManager.NameNotFoundException e2) {
                    intentResultBuilder.setOemUiUsageStatus(IntentCreationResult.OemUiUsageStatus.OEM_UI_CONFIG_SPECIFIED_BUT_NOT_FOUND);
                    Slog.i(TAG, "Unable to find oem CredMan UI component: " + oemComponentString + MediaMetrics.SEPARATOR);
                }
            } else {
                intentResultBuilder.setOemUiUsageStatus(IntentCreationResult.OemUiUsageStatus.OEM_UI_CONFIG_SPECIFIED_BUT_NOT_FOUND);
                Slog.i(TAG, "Invalid OEM ComponentName format.");
            }
        } else {
            intentResultBuilder.setOemUiUsageStatus(IntentCreationResult.OemUiUsageStatus.OEM_UI_CONFIG_NOT_SPECIFIED);
            Slog.i(TAG, "Invalid empty OEM component name.");
        }
        return result;
    }

    private static <T extends ResultReceiver> ResultReceiver toIpcFriendlyResultReceiver(T resultReceiver) {
        Parcel parcel = Parcel.obtain();
        resultReceiver.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        ResultReceiver ipcFriendly = ResultReceiver.CREATOR.createFromParcel(parcel);
        parcel.recycle();
        return ipcFriendly;
    }

    private IntentFactory() {
    }
}
