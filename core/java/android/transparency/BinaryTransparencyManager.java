package android.transparency;

import android.content.Context;
import android.p009os.Bundle;
import android.p009os.RemoteException;
import android.util.Slog;
import com.android.internal.p029os.IBinaryTransparencyService;
import java.util.List;

/* loaded from: classes4.dex */
public class BinaryTransparencyManager {
    private static final String TAG = "TransparencyManager";
    private final Context mContext;
    private final IBinaryTransparencyService mService;

    public BinaryTransparencyManager(Context context, IBinaryTransparencyService service) {
        this.mContext = context;
        this.mService = service;
    }

    public String getSignedImageInfo() {
        try {
            return this.mService.getSignedImageInfo();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<IBinaryTransparencyService.ApexInfo> collectAllApexInfo(boolean includeTestOnly) {
        try {
            return this.mService.collectAllApexInfo(includeTestOnly);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<IBinaryTransparencyService.AppInfo> collectAllUpdatedPreloadInfo(Bundle packagesToSkip) {
        try {
            Slog.m113d(TAG, "Calling backend's collectAllUpdatedPreloadInfo()");
            return this.mService.collectAllUpdatedPreloadInfo(packagesToSkip);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<IBinaryTransparencyService.AppInfo> collectAllSilentInstalledMbaInfo(Bundle packagesToSkip) {
        try {
            Slog.m113d(TAG, "Calling backend's collectAllSilentInstalledMbaInfo()");
            return this.mService.collectAllSilentInstalledMbaInfo(packagesToSkip);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
