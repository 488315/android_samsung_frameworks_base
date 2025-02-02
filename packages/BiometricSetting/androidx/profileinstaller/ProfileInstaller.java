package androidx.profileinstaller;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.util.Log;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class ProfileInstaller {
    private static final DiagnosticsCallback EMPTY_DIAGNOSTICS = new C01491();
    static final DiagnosticsCallback LOG_DIAGNOSTICS = new C01502();

    /* renamed from: androidx.profileinstaller.ProfileInstaller$2 */
    final class C01502 implements DiagnosticsCallback {
        @Override // androidx.profileinstaller.ProfileInstaller.DiagnosticsCallback
        public final void onResultReceived(int i, Object obj) {
            String str;
            switch (i) {
                case 1:
                    str = "RESULT_INSTALL_SUCCESS";
                    break;
                case 2:
                    str = "RESULT_ALREADY_INSTALLED";
                    break;
                case 3:
                    str = "RESULT_UNSUPPORTED_ART_VERSION";
                    break;
                case 4:
                    str = "RESULT_NOT_WRITABLE";
                    break;
                case 5:
                    str = "RESULT_DESIRED_FORMAT_UNSUPPORTED";
                    break;
                case 6:
                    str = "RESULT_BASELINE_PROFILE_NOT_FOUND";
                    break;
                case 7:
                    str = "RESULT_IO_EXCEPTION";
                    break;
                case 8:
                    str = "RESULT_PARSE_EXCEPTION";
                    break;
                case 9:
                default:
                    str = "";
                    break;
                case 10:
                    str = "RESULT_INSTALL_SKIP_FILE_SUCCESS";
                    break;
                case 11:
                    str = "RESULT_DELETE_SKIP_FILE_SUCCESS";
                    break;
            }
            if (i == 6 || i == 7 || i == 8) {
                Log.e("ProfileInstaller", str, (Throwable) obj);
            } else {
                Log.d("ProfileInstaller", str);
            }
        }
    }

    public interface DiagnosticsCallback {
        void onResultReceived(int i, Object obj);
    }

    public static void writeProfile(Context context) {
        writeProfile(context, new ProfileInstaller$$ExternalSyntheticLambda0(), EMPTY_DIAGNOSTICS, false);
    }

    static void writeProfile(Context context, Executor executor, DiagnosticsCallback diagnosticsCallback, boolean z) {
        Context applicationContext = context.getApplicationContext();
        String packageName = applicationContext.getPackageName();
        ApplicationInfo applicationInfo = applicationContext.getApplicationInfo();
        AssetManager assets = applicationContext.getAssets();
        String name = new File(applicationInfo.sourceDir).getName();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            File filesDir = context.getFilesDir();
            if (!z) {
                File file = new File(filesDir, "profileinstaller_profileWrittenFor_lastUpdateTime.dat");
                if (file.exists()) {
                    try {
                        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
                        try {
                            long readLong = dataInputStream.readLong();
                            dataInputStream.close();
                            r2 = readLong == packageInfo.lastUpdateTime;
                            if (r2) {
                                diagnosticsCallback.onResultReceived(2, null);
                            }
                        } finally {
                        }
                    } catch (IOException unused) {
                    }
                }
                if (r2) {
                    Log.d("ProfileInstaller", "Skipping profile installation for " + context.getPackageName());
                    ProfileVerifier.writeProfileVerification(context);
                    return;
                }
            }
            Log.d("ProfileInstaller", "Installing profile for " + context.getPackageName());
            new DeviceProfileWriter(assets, executor, diagnosticsCallback, name, new File(new File("/data/misc/profiles/cur/0", packageName), "primary.prof")).deviceAllowsProfileInstallerAotWrites();
            ProfileVerifier.writeProfileVerification(context);
        } catch (PackageManager.NameNotFoundException e) {
            diagnosticsCallback.onResultReceived(7, e);
            ProfileVerifier.writeProfileVerification(context);
        }
    }

    /* renamed from: androidx.profileinstaller.ProfileInstaller$1 */
    final class C01491 implements DiagnosticsCallback {
        @Override // androidx.profileinstaller.ProfileInstaller.DiagnosticsCallback
        public final void onResultReceived(int i, Object obj) {
        }
    }
}
