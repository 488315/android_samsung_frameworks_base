package android.transparency;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.os.IBinaryTransparencyService;
import java.util.List;

/* loaded from: classes4.dex */
public class BinaryTransparencyManager {
    private static final String TAG = "TransparencyManager";
    private final Context mContext;
    private final IBinaryTransparencyService mService;

    public BinaryTransparencyManager(Context context, IBinaryTransparencyService iBinaryTransparencyService) {
        this.mContext = context;
        this.mService = iBinaryTransparencyService;
    }

    public String getSignedImageInfo() {
        try {
            return this.mService.getSignedImageInfo();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<IBinaryTransparencyService.ApexInfo> collectAllApexInfo(boolean z) {
        try {
            return this.mService.collectAllApexInfo(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<IBinaryTransparencyService.AppInfo> collectAllUpdatedPreloadInfo(Bundle bundle) {
        try {
            Slog.d(TAG, "Calling backend's collectAllUpdatedPreloadInfo()");
            return this.mService.collectAllUpdatedPreloadInfo(bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<IBinaryTransparencyService.AppInfo> collectAllSilentInstalledMbaInfo(Bundle bundle) {
        try {
            Slog.d(TAG, "Calling backend's collectAllSilentInstalledMbaInfo()");
            return this.mService.collectAllSilentInstalledMbaInfo(bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
