package com.android.server.locksettings;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;
import com.android.server.knox.ContainerDependencyWrapper;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.knox.dar.KeyProtector;
import com.android.server.knox.dar.SecureUtil;
import com.android.server.pm.PersonaManagerService;

import java.io.File;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class LockSettingsService$$ExternalSyntheticLambda11
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ LockSettingsService$$ExternalSyntheticLambda11(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        switch (i) {
            case 0:
                PersonaManagerService personaManagerService = (PersonaManagerService) obj;
                Context context = personaManagerService.mContext;
                LockPatternUtils lockPatternUtils = personaManagerService.mLockPatternUtils;
                ContainerDependencyWrapper containerDependencyWrapper =
                        ContainerDependencyWrapper.sInstance;
                if (Settings.Secure.getIntForUser(
                                        context.getContentResolver(),
                                        "knox_finger_print_plus",
                                        0,
                                        i2)
                                == 1
                        && lockPatternUtils.getBiometricType(i2) == 0) {
                    Settings.Secure.putIntForUser(
                            context.getContentResolver(), "knox_finger_print_plus", 0, i2);
                    Log.d("ContainerDependencyWrapper", "unset two-factor setting value.");
                    break;
                }
                break;
            default:
                DarManagerService darManagerService = (DarManagerService) obj;
                darManagerService.getClass();
                if (i2 == 0) {
                    Log.i("DarManagerService", "Save secured escrow data for user " + i2);
                    File file = new File(Environment.getDataSystemDeDirectory(i2), "spblob");
                    File file2 = new File(file, String.format("%016x.%s", 0L, "e0"));
                    File file3 = new File(file, String.format("%016x.%s", 0L, "p1"));
                    if (!file2.exists() || !file3.exists()) {
                        Log.d(
                                "DarManagerService",
                                "Escrow data doesn't exist [ "
                                        + file2.exists()
                                        + "/"
                                        + file3.exists()
                                        + " ]");
                        break;
                    } else {
                        byte[] fileRead = DarManagerService.fileRead(file2.getAbsolutePath());
                        byte[] fileRead2 = DarManagerService.fileRead(file3.getAbsolutePath());
                        if (!SecureUtil.isAnyoneEmptyHere(fileRead, fileRead2)) {
                            byte[] specificKeyViaProtector =
                                    darManagerService.getSpecificKeyViaProtector(
                                            "SdpSecureDataKey", i2);
                            if (!SecureUtil.isEmpty(specificKeyViaProtector)) {
                                darManagerService.mKeyProtector.getClass();
                                byte[] encryptFast =
                                        KeyProtector.encryptFast(specificKeyViaProtector, fileRead);
                                darManagerService.mKeyProtector.getClass();
                                byte[] encryptFast2 =
                                        KeyProtector.encryptFast(
                                                specificKeyViaProtector, fileRead2);
                                Log.i(
                                        "DarManagerService",
                                        String.format(
                                                "Escrow data for SYSTEM user %d got secured [ Res :"
                                                    + " %b/%b ]",
                                                Integer.valueOf(i2),
                                                Boolean.valueOf(
                                                        DarManagerService.fileWrite(
                                                                file2.getAbsolutePath() + ".bku",
                                                                encryptFast)),
                                                Boolean.valueOf(
                                                        DarManagerService.fileWrite(
                                                                file3.getAbsolutePath() + ".bku",
                                                                encryptFast2))));
                                break;
                            } else {
                                Log.e(
                                        "DarManagerService",
                                        new Exception(
                                                        "Unexpected failure while get secure data"
                                                            + " key")
                                                .getMessage());
                                break;
                            }
                        }
                    }
                }
                break;
        }
    }
}
