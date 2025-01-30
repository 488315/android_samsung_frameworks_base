package androidx.profileinstaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;
import androidx.profileinstaller.ProfileInstaller;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ProfileInstallReceiver extends BroadcastReceiver {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ResultDiagnostics implements ProfileInstaller.DiagnosticsCallback {
        public ResultDiagnostics() {
        }

        @Override // androidx.profileinstaller.ProfileInstaller.DiagnosticsCallback
        public final void onResultReceived(int i, Object obj) {
            ProfileInstaller.LOG_DIAGNOSTICS.onResultReceived(i, obj);
            ProfileInstallReceiver.this.setResultCode(i);
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Bundle extras;
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        final int i = 0;
        if ("androidx.profileinstaller.action.INSTALL_PROFILE".equals(action)) {
            ProfileInstaller.writeProfile(context, new Executor() { // from class: androidx.profileinstaller.ProfileInstallReceiver$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    switch (i) {
                        case 0:
                            runnable.run();
                            break;
                        case 1:
                            runnable.run();
                            break;
                        default:
                            runnable.run();
                            break;
                    }
                }
            }, new ResultDiagnostics(), true);
            return;
        }
        if (!"androidx.profileinstaller.action.SKIP_FILE".equals(action)) {
            if ("androidx.profileinstaller.action.SAVE_PROFILE".equals(action)) {
                ResultDiagnostics resultDiagnostics = new ResultDiagnostics();
                Process.sendSignal(Process.myPid(), 10);
                resultDiagnostics.onResultReceived(12, null);
                return;
            } else {
                if (!"androidx.profileinstaller.action.BENCHMARK_OPERATION".equals(action) || (extras = intent.getExtras()) == null) {
                    return;
                }
                String string = extras.getString("EXTRA_BENCHMARK_OPERATION");
                ResultDiagnostics resultDiagnostics2 = new ResultDiagnostics();
                if (!"DROP_SHADER_CACHE".equals(string)) {
                    resultDiagnostics2.onResultReceived(16, null);
                    return;
                } else if (BenchmarkOperation.deleteFilesRecursively(context.createDeviceProtectedStorageContext().getCodeCacheDir())) {
                    resultDiagnostics2.onResultReceived(14, null);
                    return;
                } else {
                    resultDiagnostics2.onResultReceived(15, null);
                    return;
                }
            }
        }
        Bundle extras2 = intent.getExtras();
        if (extras2 != null) {
            String string2 = extras2.getString("EXTRA_SKIP_FILE_OPERATION");
            if (!"WRITE_SKIP_FILE".equals(string2)) {
                if ("DELETE_SKIP_FILE".equals(string2)) {
                    ResultDiagnostics resultDiagnostics3 = new ResultDiagnostics();
                    ProfileInstaller.C04271 c04271 = ProfileInstaller.EMPTY_DIAGNOSTICS;
                    new File(context.getFilesDir(), "profileinstaller_profileWrittenFor_lastUpdateTime.dat").delete();
                    resultDiagnostics3.onResultReceived(11, null);
                    return;
                }
                return;
            }
            ResultDiagnostics resultDiagnostics4 = new ResultDiagnostics();
            ProfileInstaller.C04271 c042712 = ProfileInstaller.EMPTY_DIAGNOSTICS;
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0);
                try {
                    DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(context.getFilesDir(), "profileinstaller_profileWrittenFor_lastUpdateTime.dat")));
                    try {
                        dataOutputStream.writeLong(packageInfo.lastUpdateTime);
                        dataOutputStream.close();
                    } finally {
                    }
                } catch (IOException unused) {
                }
                resultDiagnostics4.onResultReceived(10, null);
            } catch (PackageManager.NameNotFoundException e) {
                resultDiagnostics4.onResultReceived(7, e);
            }
        }
    }
}
