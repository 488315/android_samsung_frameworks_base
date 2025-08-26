package android.credentials.selection;

import android.app.AppGlobals;
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
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.credentials.flags.Flags;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class IntentFactory {
    private static final String TAG = "CredManIntentHelper";

    public static IntentCreationResult createCredentialSelectorIntentForAutofill(Context context, RequestInfo requestInfo, ArrayList<DisabledProviderData> arrayList, ResultReceiver resultReceiver, int i) {
        return createCredentialSelectorIntentInternal(context, requestInfo, arrayList, resultReceiver, i);
    }

    public static IntentCreationResult createCredentialSelectorIntentForCredMan(Context context, RequestInfo requestInfo, ArrayList<ProviderData> arrayList, ArrayList<DisabledProviderData> arrayList2, ResultReceiver resultReceiver, int i) throws Resources.NotFoundException {
        IntentCreationResult intentCreationResultCreateCredentialSelectorIntentInternal = createCredentialSelectorIntentInternal(context, requestInfo, arrayList2, resultReceiver, i);
        intentCreationResultCreateCredentialSelectorIntentInternal.getIntent().putParcelableArrayListExtra(ProviderData.EXTRA_ENABLED_PROVIDER_DATA_LIST, arrayList);
        return intentCreationResultCreateCredentialSelectorIntentInternal;
    }

    public static Intent createCredentialSelectorIntent(Context context, RequestInfo requestInfo, ArrayList<ProviderData> arrayList, ArrayList<DisabledProviderData> arrayList2, ResultReceiver resultReceiver, int i) {
        return createCredentialSelectorIntentForCredMan(context, requestInfo, arrayList, arrayList2, resultReceiver, i).getIntent();
    }

    public static Intent createCancelUiIntent(Context context, IBinder iBinder, boolean z, String str, int i) throws Resources.NotFoundException {
        Intent intent = new Intent();
        setCredentialSelectorUiComponentName(context, intent, new IntentCreationResult.Builder(intent), i);
        intent.putExtra(CancelSelectionRequest.EXTRA_CANCEL_UI_REQUEST, new CancelSelectionRequest(new RequestToken(iBinder), z, str));
        return intent;
    }

    private static IntentCreationResult createCredentialSelectorIntentInternal(Context context, RequestInfo requestInfo, ArrayList<DisabledProviderData> arrayList, ResultReceiver resultReceiver, int i) throws Resources.NotFoundException {
        Intent intent = new Intent();
        IntentCreationResult.Builder builder = new IntentCreationResult.Builder(intent);
        setCredentialSelectorUiComponentName(context, intent, builder, i);
        intent.putParcelableArrayListExtra(ProviderData.EXTRA_DISABLED_PROVIDER_DATA_LIST, arrayList);
        intent.putExtra(RequestInfo.EXTRA_REQUEST_INFO, requestInfo);
        intent.putExtra(Constants.EXTRA_RESULT_RECEIVER, toIpcFriendlyResultReceiver(resultReceiver));
        return builder.build();
    }

    private static void setCredentialSelectorUiComponentName(Context context, Intent intent, IntentCreationResult.Builder builder, int i) throws Resources.NotFoundException {
        if (Flags.configurableSelectorUiEnabled()) {
            ComponentName oemOverrideComponentName = getOemOverrideComponentName(context, builder, i);
            ComponentName componentNameUnflattenFromString = null;
            try {
                componentNameUnflattenFromString = ComponentName.unflattenFromString(Resources.getSystem().getString(R.string.config_fallbackCredentialManagerDialogComponent));
                builder.setFallbackUiPackageName(componentNameUnflattenFromString.getPackageName());
            } catch (Exception e) {
                Slog.w(TAG, "Fallback CredMan IU not found: " + e);
            }
            if (oemOverrideComponentName == null) {
                oemOverrideComponentName = componentNameUnflattenFromString;
            }
            intent.setComponent(oemOverrideComponentName);
            return;
        }
        intent.setComponent(ComponentName.unflattenFromString(Resources.getSystem().getString(R.string.config_fallbackCredentialManagerDialogComponent)));
    }

    private static ComponentName getOemOverrideComponentName(Context context, IntentCreationResult.Builder builder, int i) throws Resources.NotFoundException {
        ComponentName componentNameUnflattenFromString;
        ActivityInfo activityInfo;
        String string = Resources.getSystem().getString(R.string.config_oemCredentialManagerDialogComponent);
        if (!TextUtils.isEmpty(string)) {
            try {
                componentNameUnflattenFromString = ComponentName.unflattenFromString(string);
            } catch (Exception e) {
                Slog.i(TAG, "Failed to parse OEM component name " + string + ": " + e);
                componentNameUnflattenFromString = null;
            }
            if (componentNameUnflattenFromString != null) {
                try {
                    builder.setOemUiPackageName(componentNameUnflattenFromString.getPackageName());
                    if (Flags.propagateUserContextForIntentCreation()) {
                        activityInfo = context.getPackageManager().getActivityInfo(componentNameUnflattenFromString, PackageManager.ComponentInfoFlags.of(1048576L));
                    } else {
                        activityInfo = AppGlobals.getPackageManager().getActivityInfo(componentNameUnflattenFromString, 0L, i);
                    }
                    if (activityInfo == null) {
                        return null;
                    }
                    boolean z = activityInfo.enabled;
                    int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(componentNameUnflattenFromString);
                    if (componentEnabledSetting == 1) {
                        z = true;
                    } else if (componentEnabledSetting == 2) {
                        z = false;
                    }
                    if (z && activityInfo.exported) {
                        builder.setOemUiUsageStatus(IntentCreationResult.OemUiUsageStatus.SUCCESS);
                        Slog.i(TAG, "Found enabled oem CredMan UI component." + string);
                        return componentNameUnflattenFromString;
                    }
                    builder.setOemUiUsageStatus(IntentCreationResult.OemUiUsageStatus.OEM_UI_CONFIG_SPECIFIED_FOUND_BUT_NOT_ENABLED);
                    Slog.i(TAG, "Found enabled oem CredMan UI component but it was not enabled.");
                    return null;
                } catch (PackageManager.NameNotFoundException | RemoteException unused) {
                    builder.setOemUiUsageStatus(IntentCreationResult.OemUiUsageStatus.OEM_UI_CONFIG_SPECIFIED_BUT_NOT_FOUND);
                    Slog.i(TAG, "Unable to find oem CredMan UI component: " + string + MediaMetrics.SEPARATOR);
                    return null;
                }
            }
            builder.setOemUiUsageStatus(IntentCreationResult.OemUiUsageStatus.OEM_UI_CONFIG_SPECIFIED_BUT_NOT_FOUND);
            Slog.i(TAG, "Invalid OEM ComponentName format.");
            return null;
        }
        builder.setOemUiUsageStatus(IntentCreationResult.OemUiUsageStatus.OEM_UI_CONFIG_NOT_SPECIFIED);
        Slog.i(TAG, "Invalid empty OEM component name.");
        return null;
    }

    private static <T extends ResultReceiver> ResultReceiver toIpcFriendlyResultReceiver(T t) {
        Parcel parcelObtain = Parcel.obtain();
        t.writeToParcel(parcelObtain, 0);
        parcelObtain.setDataPosition(0);
        ResultReceiver resultReceiverCreateFromParcel = ResultReceiver.CREATOR.createFromParcel(parcelObtain);
        parcelObtain.recycle();
        return resultReceiverCreateFromParcel;
    }

    private IntentFactory() {
    }
}
