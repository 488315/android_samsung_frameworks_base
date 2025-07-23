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
import com.samsung.android.wallpaper.live.sdk.provider.call.GetEngineRunningState$Params;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Params;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetThumbnail;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperEngineManager;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService;
import com.samsung.android.wallpaper.live.sdk.utils.SdkCommonUtils;
import com.samsung.android.wallpaper.live.sdk.utils.SdkLog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class LiveWallpaperProvider extends ContentProvider {
    public static final boolean DEBUG = !SemSystemProperties.getBoolean("ro.product_ship", true);
    public LiveWallpaperProviderCallDispatcher mCallDispatcher;

    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, final Bundle bundle) {
        ProviderCallResult onGetScreenshot;
        int callingUid = Binder.getCallingUid();
        boolean z = DEBUG;
        if (z) {
            StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(callingUid, "dispatchCall : caller=", ", ", str, ", extras=[");
            m.append(SdkCommonUtils.dumpBundleToString(bundle));
            m.append("]");
            SdkLog.d("LiveWallpaperProvider", m.toString());
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
                    onGetScreenshot = null;
                    break;
                case "capture_surface":
                case "get_screenshot":
                    onGetScreenshot = liveWallpaperProviderCallDispatcher.onGetScreenshot(context, new GetScreenshot$Params(bundle));
                    break;
                case "get_background_region":
                    new ProviderCallParams(bundle) { // from class: com.samsung.android.wallpaper.live.sdk.provider.call.GetBackgroundRegion$Params
                        {
                            super(bundle);
                            bundle.getInt("which");
                            bundle.getInt("source_which");
                            bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
                            bundle.getString("wallpaper_service_class_name");
                            bundle.getInt("rotation", 0);
                        }
                    };
                    onGetScreenshot = null;
                    break;
                case "get_thumbnail":
                    onGetScreenshot = liveWallpaperProviderCallDispatcher.onGetThumbnail(context, new GetThumbnail.Params(bundle));
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
                    onGetScreenshot = null;
                    break;
                case "get_engine_running_state":
                    int i = new GetEngineRunningState$Params(bundle).which;
                    LiveWallpaperService.BaseEngine engine = LiveWallpaperEngineManager.getInstance(context).getEngine(i);
                    if (engine != null) {
                        RunningStateResults onGetRunningState = engine.onGetRunningState(new RunningStateOptions());
                        final Bundle bundle3 = onGetRunningState != null ? onGetRunningState.mExtras : null;
                        StringBuilder sb = new StringBuilder("onGetEngineRunningState : exist=");
                        sb.append((bundle3 == null || bundle3.isEmpty()) ? false : true);
                        SdkLog.i("LiveWallpaperProviderCallDispatcher", sb.toString());
                        if (bundle3 != null) {
                            onGetScreenshot = new ProviderCallResult(bundle3) { // from class: com.samsung.android.wallpaper.live.sdk.provider.call.GetEngineRunningState$Result
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
                    onGetScreenshot = null;
                    break;
                default:
                    SdkLog.d("LiveWallpaperProviderCallDispatcher", "provider call method is not available = ".concat(str));
                    onGetScreenshot = null;
                    break;
            }
            if (onGetScreenshot != null) {
                bundle2 = onGetScreenshot.toBundle();
            }
        }
        if (z) {
            StringBuilder m2 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("dispatchCall : ", str, ", result=[");
            m2.append(SdkCommonUtils.dumpBundleToString(bundle2));
            m2.append("]");
            SdkLog.d("LiveWallpaperProvider", m2.toString());
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
