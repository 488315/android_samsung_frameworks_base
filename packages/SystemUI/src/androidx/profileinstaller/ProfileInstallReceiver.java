package androidx.profileinstaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;
import androidx.profileinstaller.ProfileInstaller;
import java.io.File;

/* loaded from: classes.dex */
public class ProfileInstallReceiver extends BroadcastReceiver {

    public class ResultDiagnostics implements ProfileInstaller.DiagnosticsCallback {
        public ResultDiagnostics() {
        }

        @Override // androidx.profileinstaller.ProfileInstaller.DiagnosticsCallback
        public final void onDiagnosticReceived() {
            ProfileInstaller.LOG_DIAGNOSTICS.onDiagnosticReceived();
        }

        @Override // androidx.profileinstaller.ProfileInstaller.DiagnosticsCallback
        public final void onResultReceived(int i, Object obj) {
            ProfileInstaller.LOG_DIAGNOSTICS.onResultReceived(i, obj);
            ProfileInstallReceiver.this.setResultCode(i);
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) throws Throwable {
        Bundle extras;
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if ("androidx.profileinstaller.action.INSTALL_PROFILE".equals(action)) {
            ProfileInstaller.writeProfile(context, new ProfileInstallReceiver$$ExternalSyntheticLambda0(), new ResultDiagnostics(), true);
            return;
        }
        if (!"androidx.profileinstaller.action.SKIP_FILE".equals(action)) {
            if ("androidx.profileinstaller.action.SAVE_PROFILE".equals(action)) {
                ResultDiagnostics resultDiagnostics = new ResultDiagnostics();
                Process.sendSignal(Process.myPid(), 10);
                resultDiagnostics.onResultReceived(12, null);
                return;
            }
            if (!"androidx.profileinstaller.action.BENCHMARK_OPERATION".equals(action) || (extras = intent.getExtras()) == null) {
                return;
            }
            String string = extras.getString("EXTRA_BENCHMARK_OPERATION");
            ResultDiagnostics resultDiagnostics2 = new ResultDiagnostics();
            if ("DROP_SHADER_CACHE".equals(string)) {
                if (BenchmarkOperation.deleteFilesRecursively(context.createDeviceProtectedStorageContext().getCacheDir())) {
                    resultDiagnostics2.onResultReceived(14, null);
                    return;
                } else {
                    resultDiagnostics2.onResultReceived(15, null);
                    return;
                }
            }
            if (!"SAVE_PROFILE".equals(string)) {
                resultDiagnostics2.onResultReceived(16, null);
                return;
            } else {
                Process.sendSignal(extras.getInt("EXTRA_PID", Process.myPid()), 10);
                resultDiagnostics2.onResultReceived(12, null);
                return;
            }
        }
        Bundle extras2 = intent.getExtras();
        if (extras2 != null) {
            String string2 = extras2.getString("EXTRA_SKIP_FILE_OPERATION");
            if (!"WRITE_SKIP_FILE".equals(string2)) {
                if ("DELETE_SKIP_FILE".equals(string2)) {
                    ResultDiagnostics resultDiagnostics3 = new ResultDiagnostics();
                    String str = ProfileInstaller.PROFILE_BASE_DIR;
                    new File(context.getFilesDir(), "profileinstaller_profileWrittenFor_lastUpdateTime.dat").delete();
                    String str2 = ProfileInstaller.PROFILE_BASE_DIR;
                    resultDiagnostics3.onResultReceived(11, null);
                    return;
                }
                return;
            }
            ResultDiagnostics resultDiagnostics4 = new ResultDiagnostics();
            String str3 = ProfileInstaller.PROFILE_BASE_DIR;
            try {
                ProfileInstaller.noteProfileWrittenFor(context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0), context.getFilesDir());
                String str4 = ProfileInstaller.PROFILE_BASE_DIR;
                resultDiagnostics4.onResultReceived(10, null);
            } catch (PackageManager.NameNotFoundException e) {
                String str5 = ProfileInstaller.PROFILE_BASE_DIR;
                resultDiagnostics4.onResultReceived(7, e);
            }
        }
    }
}
