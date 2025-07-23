package androidx.profileinstaller;

import android.content.pm.PackageInfo;
import android.util.Log;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ProfileInstaller {
    public static final String PROFILE_BASE_DIR = "/data/misc/profiles/cur/" + UserInfo.getCurrentUserId();
    public static final AnonymousClass1 EMPTY_DIAGNOSTICS = new DiagnosticsCallback() { // from class: androidx.profileinstaller.ProfileInstaller.1
        @Override // androidx.profileinstaller.ProfileInstaller.DiagnosticsCallback
        public final void onDiagnosticReceived() {
        }

        @Override // androidx.profileinstaller.ProfileInstaller.DiagnosticsCallback
        public final void onResultReceived(int i, Object obj) {
        }
    };
    public static final AnonymousClass2 LOG_DIAGNOSTICS = new AnonymousClass2();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.profileinstaller.ProfileInstaller$2, reason: invalid class name */
    public class AnonymousClass2 implements DiagnosticsCallback {
        @Override // androidx.profileinstaller.ProfileInstaller.DiagnosticsCallback
        public final void onDiagnosticReceived() {
            Log.d("ProfileInstaller", "DIAGNOSTIC_PROFILE_IS_COMPRESSED");
        }

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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DiagnosticsCallback {
        void onDiagnosticReceived();

        void onResultReceived(int i, Object obj);
    }

    private ProfileInstaller() {
    }

    public static void noteProfileWrittenFor(PackageInfo packageInfo, File file) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(file, "profileinstaller_profileWrittenFor_lastUpdateTime.dat")));
            try {
                dataOutputStream.writeLong(packageInfo.lastUpdateTime);
                dataOutputStream.close();
            } finally {
            }
        } catch (IOException unused) {
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:2|3|4|(3:6|(1:8)(8:13|14|15|16|17|18|(1:20)(1:23)|(1:22))|(2:10|11))|35|(15:(2:37|(5:39|40|(1:46)(1:43)|44|45))(3:256|257|(6:259|40|(0)|46|44|45))|52|(3:203|204|(4:206|207|208|209)(2:213|214))|54|(3:171|172|(3:179|180|(4:182|183|184|(1:178))(2:185|186))(3:(1:175)|176|(0)))|56|(2:58|(5:62|63|64|65|(2:67|68)(3:69|70|71))(2:60|61))|86|(3:91|92|(11:96|97|98|99|100|101|102|103|(3:107|108|(13:110|(2:111|(1:113)(1:114))|115|116|117|118|119|120|(1:90)|(0)|46|44|45))|105|106)(2:94|95))|88|(0)|(0)|46|44|45)|47|48|49|50|51) */
    /* JADX WARN: Code restructure failed: missing block: B:245:0x00f8, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:246:0x00fe, code lost:
    
        r8 = r0.getMessage();
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x0102, code lost:
    
        if (r8 == null) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:250:0x010c, code lost:
    
        r5.onDiagnosticReceived();
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x0113, code lost:
    
        r8 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:252:0x0110, code lost:
    
        r5.onResultReceived(6, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:253:0x00f5, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x00fa, code lost:
    
        r5.onResultReceived(7, r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0171 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x011a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x02d3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0227  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void writeProfile(android.content.Context r16, java.util.concurrent.Executor r17, androidx.profileinstaller.ProfileInstaller.DiagnosticsCallback r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 742
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.profileinstaller.ProfileInstaller.writeProfile(android.content.Context, java.util.concurrent.Executor, androidx.profileinstaller.ProfileInstaller$DiagnosticsCallback, boolean):void");
    }
}
