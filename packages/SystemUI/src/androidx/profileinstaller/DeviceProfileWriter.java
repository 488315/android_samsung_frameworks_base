package androidx.profileinstaller;

import android.content.res.AssetManager;
import androidx.profileinstaller.ProfileInstaller;
import java.io.File;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class DeviceProfileWriter {
    public final String mApkName;
    public final AssetManager mAssetManager;
    public final File mCurProfile;
    public final ProfileInstaller.DiagnosticsCallback mDiagnostics;
    public final Executor mExecutor;
    public DexProfileData[] mProfile;
    public final String mProfileMetaSourceLocation;
    public final String mProfileSourceLocation;
    public byte[] mTranscodedProfile;
    public boolean mDeviceSupportsAotProfile = false;
    public final byte[] mDesiredVersion = ProfileVersion.V015_S;

    public DeviceProfileWriter(AssetManager assetManager, Executor executor, ProfileInstaller.DiagnosticsCallback diagnosticsCallback, String str, String str2, String str3, File file) {
        this.mAssetManager = assetManager;
        this.mExecutor = executor;
        this.mDiagnostics = diagnosticsCallback;
        this.mApkName = str;
        this.mProfileSourceLocation = str2;
        this.mProfileMetaSourceLocation = str3;
        this.mCurProfile = file;
    }

    public final void result(final int i, final Object obj) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.profileinstaller.DeviceProfileWriter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DeviceProfileWriter deviceProfileWriter = this.f$0;
                deviceProfileWriter.mDiagnostics.onResultReceived(i, obj);
            }
        });
    }
}
