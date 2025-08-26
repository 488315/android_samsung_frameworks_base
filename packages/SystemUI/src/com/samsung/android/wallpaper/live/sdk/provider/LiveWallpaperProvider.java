package com.samsung.android.wallpaper.live.sdk.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.SemSystemProperties;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.provider.ProviderCallDispatcher;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.wallpaper.live.sdk.data.RunningStateOptions;
import com.samsung.android.wallpaper.live.sdk.data.RunningStateResults;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetBackgroundRegion$Params;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetEngineRunningState$Params;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Params;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetThumbnail;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperEngineManager;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService;
import com.samsung.android.wallpaper.live.sdk.utils.SdkCommonUtils;
import com.samsung.android.wallpaper.live.sdk.utils.SdkLog;
import java.io.IOException;

/* loaded from: classes4.dex */
public abstract class LiveWallpaperProvider extends ContentProvider {
    public static final boolean DEBUG = !SemSystemProperties.getBoolean("ro.product_ship", true);
    public LiveWallpaperProviderCallDispatcher mCallDispatcher;

    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, final Bundle bundle) throws IOException {
        ProviderCallResult providerCallResultOnGetScreenshot;
        int callingUid = Binder.getCallingUid();
        boolean z = DEBUG;
        if (z) {
            StringBuilder sbM = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(callingUid, "dispatchCall : caller=", ", ", str, ", extras=[");
            sbM.append(SdkCommonUtils.dumpBundleToString(bundle));
            sbM.append("]");
            SdkLog.d("LiveWallpaperProvider", sbM.toString());
        } else if (callingUid != 1000) {
            SdkLog.d("LiveWallpaperProvider", "dispatchCall : caller=" + callingUid + ", " + str);
        } else {
            SdkLog.d("LiveWallpaperProvider", "dispatchCall : " + str);
        }
        Bundle bundle2 = null;
        if (this.mCallDispatcher == null) {
            SdkLog.e("LiveWallpaperProvider", "call : dispatcher is null. method=" + str);
            return null;
        }
        if (getContext().getPackageManager().checkSignatures(1000, callingUid) != 0) {
            SdkLog.e("LiveWallpaperProvider", "dispatchCall : caller should be signed with platform signature. caller=" + callingUid);
            return null;
        }
        LiveWallpaperProviderCallDispatcher liveWallpaperProviderCallDispatcher = this.mCallDispatcher;
        Context context = getContext();
        liveWallpaperProviderCallDispatcher.getClass();
        if (str == null) {
            SdkLog.d("LiveWallpaperProviderCallDispatcher", "dispatchCall: provider call method is null");
        } else {
            switch (str) {
                case "prepare_wallpaper":
                    new ProviderCallParams(bundle) { // from class: com.samsung.android.wallpaper.live.sdk.provider.call.PrepareWallpaper$Params
                        {
                            super(bundle);
                            bundle.getInt("which", 5);
                            bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, 0);
                            bundle.getString("wallpaper_service_class_name");
                            bundle.getBundle("external_params");
                        }
                    };
                    providerCallResultOnGetScreenshot = null;
                    break;
                case "capture_surface":
                case "get_screenshot":
                    providerCallResultOnGetScreenshot = liveWallpaperProviderCallDispatcher.onGetScreenshot(context, new GetScreenshot$Params(bundle));
                    break;
                case "get_background_region":
                    providerCallResultOnGetScreenshot = liveWallpaperProviderCallDispatcher.onGetBackgroundRegion(context, new GetBackgroundRegion$Params(bundle));
                    break;
                case "get_thumbnail":
                    providerCallResultOnGetScreenshot = liveWallpaperProviderCallDispatcher.onGetThumbnail(context, new GetThumbnail.Params(bundle));
                    break;
                case "get_thumbnail_by_backup_id":
                    new ProviderCallParams(bundle) { // from class: com.samsung.android.wallpaper.live.sdk.provider.call.GetThumbnailByBackupId$Params
                        {
                            super(bundle);
                            bundle.getString("wallpaper_service_class_name");
                            bundle.getString("backup_id");
                            bundle.getLong("timeout", 0L);
                            bundle.getBundle("service_settings");
                        }
                    };
                    providerCallResultOnGetScreenshot = null;
                    break;
                case "get_engine_running_state":
                    int i = new GetEngineRunningState$Params(bundle).which;
                    LiveWallpaperService.BaseEngine engine = LiveWallpaperEngineManager.getInstance(context).getEngine(i);
                    if (engine != null) {
                        RunningStateResults runningStateResultsOnGetRunningState = engine.onGetRunningState(new RunningStateOptions());
                        final Bundle bundle3 = runningStateResultsOnGetRunningState != null ? runningStateResultsOnGetRunningState.mExtras : null;
                        StringBuilder sb = new StringBuilder("onGetEngineRunningState : exist=");
                        sb.append((bundle3 == null || bundle3.isEmpty()) ? false : true);
                        SdkLog.i("LiveWallpaperProviderCallDispatcher", sb.toString());
                        if (bundle3 != null) {
                            providerCallResultOnGetScreenshot = new ProviderCallResult(bundle3) { // from class: com.samsung.android.wallpaper.live.sdk.provider.call.GetEngineRunningState$Result
                                public final Bundle mRunningState;

                                {
                                    this.mRunningState = bundle3;
                                }

                                @Override // com.samsung.android.wallpaper.live.sdk.provider.ProviderCallResult
                                public final Bundle toBundle() {
                                    Bundle bundle4 = this.mRunningState;
                                    if (bundle4 == null || bundle4.isEmpty()) {
                                        return null;
                                    }
                                    return this.mRunningState;
                                }
                            };
                            break;
                        }
                    } else {
                        SdkLog.i("LiveWallpaperProviderCallDispatcher", "onGetEngineRunningState : engine is null. which=" + i);
                    }
                    providerCallResultOnGetScreenshot = null;
                    break;
                default:
                    SdkLog.d("LiveWallpaperProviderCallDispatcher", "provider call method is not available = ".concat(str));
                    providerCallResultOnGetScreenshot = null;
                    break;
            }
            if (providerCallResultOnGetScreenshot != null) {
                bundle2 = providerCallResultOnGetScreenshot.toBundle();
            }
        }
        if (z) {
            StringBuilder sbM2 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("dispatchCall : ", str, ", result=[");
            sbM2.append(SdkCommonUtils.dumpBundleToString(bundle2));
            sbM2.append("]");
            SdkLog.d("LiveWallpaperProvider", sbM2.toString());
        }
        return bundle2;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public abstract ProviderCallDispatcher getCallDispatcher();

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        this.mCallDispatcher = getCallDispatcher();
        return false;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
