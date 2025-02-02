package androidx.profileinstaller;

import android.content.res.AssetManager;
import android.os.Build;
import androidx.profileinstaller.ProfileInstaller;
import java.io.File;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class DeviceProfileWriter {
    private final ProfileInstaller.DiagnosticsCallback mDiagnostics;
    private final Executor mExecutor;

    public DeviceProfileWriter(AssetManager assetManager, Executor executor, ProfileInstaller.DiagnosticsCallback diagnosticsCallback, String str, File file) {
        this.mExecutor = executor;
        this.mDiagnostics = diagnosticsCallback;
    }

    public final void deviceAllowsProfileInstallerAotWrites() {
        final Integer valueOf = Integer.valueOf(Build.VERSION.SDK_INT);
        final int i = 3;
        this.mExecutor.execute(new Runnable() { // from class: androidx.profileinstaller.DeviceProfileWriter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DeviceProfileWriter.this.mDiagnostics.onResultReceived(i, valueOf);
            }
        });
    }
}
