package androidx.profileinstaller;

import android.content.res.AssetManager;
import androidx.profileinstaller.ProfileInstaller;
import java.io.File;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DeviceProfileWriter {
    public final ProfileInstaller.DiagnosticsCallback mDiagnostics;
    public final Executor mExecutor;

    public DeviceProfileWriter(AssetManager assetManager, Executor executor, ProfileInstaller.DiagnosticsCallback diagnosticsCallback, String str, String str2, String str3, File file) {
        this.mExecutor = executor;
        this.mDiagnostics = diagnosticsCallback;
    }
}
