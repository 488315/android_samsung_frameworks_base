package android.credentials.p003ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.p009os.IBinder;
import android.p009os.Parcel;
import android.p009os.ResultReceiver;
import com.android.internal.C4337R;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class IntentFactory {
  public static Intent createCredentialSelectorIntent(
      RequestInfo requestInfo,
      ArrayList<ProviderData> enabledProviderDataList,
      ArrayList<DisabledProviderData> disabledProviderDataList,
      ResultReceiver resultReceiver) {
    Intent intent = new Intent();
    ComponentName componentName =
        ComponentName.unflattenFromString(
            Resources.getSystem().getString(C4337R.string.config_credentialManagerDialogComponent));
    intent.setComponent(componentName);
    intent.putParcelableArrayListExtra(
        ProviderData.EXTRA_ENABLED_PROVIDER_DATA_LIST, enabledProviderDataList);
    intent.putParcelableArrayListExtra(
        ProviderData.EXTRA_DISABLED_PROVIDER_DATA_LIST, disabledProviderDataList);
    intent.putExtra(RequestInfo.EXTRA_REQUEST_INFO, requestInfo);
    intent.putExtra(Constants.EXTRA_RESULT_RECEIVER, toIpcFriendlyResultReceiver(resultReceiver));
    return intent;
  }

  public static Intent createCancelUiIntent(
      IBinder requestToken, boolean shouldShowCancellationUi, String appPackageName) {
    Intent intent = new Intent();
    ComponentName componentName =
        ComponentName.unflattenFromString(
            Resources.getSystem().getString(C4337R.string.config_credentialManagerDialogComponent));
    intent.setComponent(componentName);
    intent.putExtra(
        CancelUiRequest.EXTRA_CANCEL_UI_REQUEST,
        new CancelUiRequest(requestToken, shouldShowCancellationUi, appPackageName));
    return intent;
  }

  public static Intent createProviderUpdateIntent() {
    Intent intent = new Intent();
    ComponentName componentName =
        ComponentName.unflattenFromString(
            Resources.getSystem()
                .getString(C4337R.string.config_credentialManagerReceiverComponent));
    intent.setComponent(componentName);
    intent.setAction(Constants.CREDMAN_ENABLED_PROVIDERS_UPDATED);
    return intent;
  }

  private static <T extends ResultReceiver> ResultReceiver toIpcFriendlyResultReceiver(
      T resultReceiver) {
    Parcel parcel = Parcel.obtain();
    resultReceiver.writeToParcel(parcel, 0);
    parcel.setDataPosition(0);
    ResultReceiver ipcFriendly = ResultReceiver.CREATOR.createFromParcel(parcel);
    parcel.recycle();
    return ipcFriendly;
  }

  private IntentFactory() {}
}
