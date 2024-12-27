package com.android.server.battery.batteryInfo;

import android.util.Slog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.battery.BattFeatures;
import com.android.server.battery.BattLogBuffer;
import com.android.server.battery.BattUtils;
import com.android.server.battery.BatteryLogger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BattInfoManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ BattInfoManager f$0;

    public /* synthetic */ BattInfoManager$$ExternalSyntheticLambda0(BattInfoManager battInfoManager) {
        this.f$0 = battInfoManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        final BattInfoManager battInfoManager = this.f$0;
        battInfoManager.getClass();
        Slog.i("[SS][BattInfo]BattInfoManager", "[init]init");
        int i6 = battInfoManager.mBatteryCount;
        boolean[] zArr = new boolean[i6];
        boolean z = false;
        int i7 = battInfoManager.mBatteryType;
        if (i7 == 0) {
            zArr[0] = false;
        } else if (i7 == 3) {
            zArr[0] = BattUtils.readNodeAsLong("/sys/class/power_supply/sec_auth/presence") == 1 && BattUtils.readNodeAsLong("/sys/class/power_supply/sec_auth/batt_auth") == 1;
            zArr[1] = BattUtils.readNodeAsLong("/sys/class/power_supply/sec_auth_2nd/presence") == 1 && BattUtils.readNodeAsLong("/sys/class/power_supply/sec_auth_2nd/batt_auth") == 1;
        } else if (i7 == 1) {
            zArr[0] = BattUtils.readNodeAsLong("/sys/class/power_supply/sec_auth/presence") == 1 && BattUtils.readNodeAsLong("/sys/class/power_supply/sec_auth/batt_auth") == 1;
        } else if (i7 == 2) {
            zArr[0] = !BattUtils.readNode("/sys/class/power_supply/sbp-fg/qr_code", true).isEmpty();
        } else if (i7 == 4) {
            zArr[0] = !BattUtils.readNode("/sys/class/power_supply/sbp-fg/qr_code", true).isEmpty();
            zArr[1] = !BattUtils.readNode("/sys/class/power_supply/sbp-fg-2/qr_code", true).isEmpty();
        }
        Slog.d("[SS][BattInfo]BattInfoManager", "[checkIcAuthenticationResults]result:" + Arrays.toString(zArr));
        battInfoManager.mAuthentificationResults = zArr;
        if ((i7 == 3 || i7 == 1) && !IntStream.range(0, i6).allMatch(new IntPredicate() { // from class: com.android.server.battery.batteryInfo.BattInfoManager$$ExternalSyntheticLambda1
            @Override // java.util.function.IntPredicate
            public final boolean test(int i8) {
                return BattInfoManager.this.mAuthentificationResults[i8];
            }
        }) && (i = battInfoManager.mInitCheckStatusCount) < 60) {
            battInfoManager.mInitCheckStatusCount = i + 1;
            SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("[handleMessage]DualAuth IcAuthentication fails. retry count:"), battInfoManager.mInitCheckStatusCount, "[SS][BattInfo]BattInfoManager");
            battInfoManager.mHandler.postDelayed(new BattInfoManager$$ExternalSyntheticLambda0(battInfoManager), 1000L);
            return;
        }
        BatteryLogger.writeToFile("/data/log/battery_service/battery_service_main_history", "Final IcAuthenticationResults", "Final IcAuthenticationResults:" + Arrays.toString(battInfoManager.mAuthentificationResults) + " ,Retry Count:" + battInfoManager.mInitCheckStatusCount);
        StringBuilder sb = new StringBuilder("Final IcAuthenticationResults:");
        sb.append(Arrays.toString(battInfoManager.mAuthentificationResults));
        BattLogBuffer.addLog(3, sb.toString());
        battInfoManager.mQrData = new QrData(i7, i6, battInfoManager.mAuthentificationResults);
        battInfoManager.mFirstUseDateData = new FirstUseDateData(battInfoManager.mBatteryType, battInfoManager.mBatteryCount, battInfoManager.mAuthentificationResults, battInfoManager.mContext, battInfoManager.mWorkerThread.getLooper());
        if (battInfoManager.mSupportsAsoc) {
            boolean[] zArr2 = battInfoManager.mAuthentificationResults;
            boolean[] zArr3 = battInfoManager.mQrData.mIsQrEquals;
            AsocData asocData = new AsocData();
            asocData.mBatteryType = i7;
            asocData.mBatteryCount = i6;
            asocData.mAuthentificationResults = zArr2;
            asocData.mCurrentValues = new Long[i6];
            if (i7 == 0) {
                asocData.efsPaths.add("/efs/FactoryApp/asoc");
            } else if (i7 == 1) {
                asocData.efsPaths.add("/efs/FactoryApp/asoc");
                asocData.authPaths.add("/sys/class/power_supply/sec_auth/asoc");
            } else if (i7 == 3) {
                asocData.efsPaths.add("/efs/FactoryApp/asoc");
                asocData.efsPaths.add("/efs/FactoryApp/asoc_2nd");
                asocData.authPaths.add("/sys/class/power_supply/sec_auth/asoc");
                asocData.authPaths.add("/sys/class/power_supply/sec_auth_2nd/asoc");
            }
            asocData.saveInfoHistory();
            int i8 = asocData.mBatteryType;
            if (i8 == 1 || i8 == 3) {
                int i9 = 0;
                while (i9 < asocData.mBatteryCount) {
                    boolean z2 = asocData.mAuthentificationResults[i9];
                    String str = asocData.TAG;
                    if (z2) {
                        long j = 100;
                        if (zArr3[i9]) {
                            i5 = i7;
                            long readNodeAsLong$1 = BattUtils.readNodeAsLong$1((String) asocData.efsPaths.get(i9));
                            i4 = i6;
                            long readNodeAsLong$12 = BattUtils.readNodeAsLong$1((String) asocData.authPaths.get(i9));
                            long j2 = (readNodeAsLong$12 < 0 || readNodeAsLong$12 > 100 || readNodeAsLong$12 >= readNodeAsLong$1) ? readNodeAsLong$1 : readNodeAsLong$12;
                            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("[syncAuthAndEfsForAuth]efsAsoc:", readNodeAsLong$1, " ,authAsoc:");
                            m.append(readNodeAsLong$12);
                            m.append(" =>worseAsoc:");
                            m.append(j2);
                            Slog.d(str, m.toString());
                            BattUtils.writeNode(j2, (String) asocData.efsPaths.get(i9));
                            BattUtils.writeNode(j2, (String) asocData.authPaths.get(i9));
                        } else {
                            i4 = i6;
                            i5 = i7;
                            long readNodeAsLong = BattUtils.readNodeAsLong((String) asocData.authPaths.get(i9));
                            if (readNodeAsLong == 65535 || readNodeAsLong < 0) {
                                Slog.i(str, "[syncAuthAndEfsForAuth]init authAsoc:100");
                                BattUtils.writeNode(100L, (String) asocData.authPaths.get(i9));
                            } else {
                                j = readNodeAsLong;
                            }
                            Slog.d(str, "[syncAuthAndEfsForAuth]sync efs asoc with auth");
                            BattUtils.writeNode(j, (String) asocData.efsPaths.get(i9));
                        }
                    } else {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(i9, "[syncAuthAndEfsForAuth]Authentification false => skip_", str);
                        i4 = i6;
                        i5 = i7;
                    }
                    i9++;
                    i7 = i5;
                    i6 = i4;
                }
            }
            i2 = i6;
            i3 = i7;
            asocData.setPermissionsEfs(1007);
            asocData.updateAndSet();
            asocData.saveInfoHistory();
            if (BattUtils.isExist("/efs/FactoryApp/batt_hist")) {
                try {
                    Files.delete(Paths.get("/efs/FactoryApp/batt_hist", new String[0]));
                    z = true;
                } catch (IOException e) {
                    Slog.e("[SS]BattUtils", "[deleteNode]Exception", e);
                }
                DeviceIdleController$$ExternalSyntheticOutline0.m("[deleteNode]path:/efs/FactoryApp/batt_hist ,result:", "[SS]BattUtils", z);
            }
            battInfoManager.mAsocData = asocData;
        } else {
            i2 = i6;
            i3 = i7;
        }
        boolean[] zArr4 = battInfoManager.mAuthentificationResults;
        boolean[] zArr5 = battInfoManager.mQrData.mIsQrEquals;
        DischargeLevelData dischargeLevelData = new DischargeLevelData();
        int i10 = i3;
        dischargeLevelData.mBatteryType = i10;
        int i11 = i2;
        dischargeLevelData.mBatteryCount = i11;
        dischargeLevelData.mAuthentificationResults = zArr4;
        dischargeLevelData.mCurrentValues = new Long[i11];
        if (i10 == 0) {
            dischargeLevelData.efsPaths.add("/efs/FactoryApp/batt_discharge_level");
        } else if (i10 == 1) {
            dischargeLevelData.efsPaths.add("/efs/FactoryApp/batt_discharge_level");
            dischargeLevelData.authPaths.add("/sys/class/power_supply/sec_auth/batt_discharge_level");
        } else if (i10 == 3) {
            dischargeLevelData.efsPaths.add("/efs/FactoryApp/batt_discharge_level");
            dischargeLevelData.efsPaths.add("/efs/FactoryApp/batt_discharge_level_2nd");
            dischargeLevelData.authPaths.add("/sys/class/power_supply/sec_auth/batt_discharge_level");
            dischargeLevelData.authPaths.add("/sys/class/power_supply/sec_auth_2nd/batt_discharge_level");
        } else if (i10 == 2) {
            dischargeLevelData.efsPaths.add("/efs/FactoryApp/batt_discharge_level");
            dischargeLevelData.authPaths.add("/sys/class/power_supply/sbp-fg/cycle");
        } else if (i10 == 4) {
            dischargeLevelData.efsPaths.add("/efs/FactoryApp/batt_discharge_level");
            dischargeLevelData.efsPaths.add("/efs/FactoryApp/batt_discharge_level_2nd");
            dischargeLevelData.authPaths.add("/sys/class/power_supply/sbp-fg/cycle");
            dischargeLevelData.authPaths.add("/sys/class/power_supply/sbp-fg-2/cycle");
        }
        int i12 = dischargeLevelData.mBatteryType;
        if (i12 == 1 || i12 == 3) {
            dischargeLevelData.syncAuthAndEfsForAuth$1(zArr5);
        } else if (i12 == 2 || i12 == 4) {
            dischargeLevelData.updateEfsFromSBP(0L);
        } else if (i12 == 0) {
            dischargeLevelData.initializeIfNotExist();
        }
        dischargeLevelData.setPermissionsEfs(1007);
        dischargeLevelData.mCurrentValues = dischargeLevelData.readEfsValues();
        dischargeLevelData.saveInfoHistory();
        battInfoManager.mDischargeLevelData = dischargeLevelData;
        if (battInfoManager.mSupportsFullStatusUsage) {
            boolean[] zArr6 = battInfoManager.mAuthentificationResults;
            boolean[] zArr7 = battInfoManager.mQrData.mIsQrEquals;
            FullStatusUsageData fullStatusUsageData = new FullStatusUsageData();
            fullStatusUsageData.mBatteryType = i10;
            fullStatusUsageData.mBatteryCount = i11;
            fullStatusUsageData.mAuthentificationResults = zArr6;
            fullStatusUsageData.mCurrentValues = new Long[i11];
            if (i10 == 0) {
                fullStatusUsageData.efsPaths.add("/efs/FactoryApp/batt_full_status_usage");
            } else if (i10 == 1) {
                fullStatusUsageData.efsPaths.add("/efs/FactoryApp/batt_full_status_usage");
                fullStatusUsageData.authPaths.add("/sys/class/power_supply/sec_auth/batt_full_status_usage");
            } else if (i10 == 3) {
                fullStatusUsageData.efsPaths.add("/efs/FactoryApp/batt_full_status_usage");
                fullStatusUsageData.efsPaths.add("/efs/FactoryApp/batt_full_status_usage_2nd");
                fullStatusUsageData.authPaths.add("/sys/class/power_supply/sec_auth/batt_full_status_usage");
                fullStatusUsageData.authPaths.add("/sys/class/power_supply/sec_auth_2nd/batt_full_status_usage");
            }
            int i13 = fullStatusUsageData.mBatteryType;
            if (i13 == 1 || i13 == 3) {
                fullStatusUsageData.syncAuthAndEfsForAuth$1(zArr7);
            } else if (i13 == 0) {
                fullStatusUsageData.initializeIfNotExist();
            }
            fullStatusUsageData.setPermissionsEfs(1007);
            fullStatusUsageData.mCurrentValues = fullStatusUsageData.readEfsValues();
            fullStatusUsageData.saveInfoHistory();
            battInfoManager.mFullStatusUsageData = fullStatusUsageData;
        }
        if (BattFeatures.FEATURE_SAVE_BATTERY_CYCLE) {
            battInfoManager.processCycle();
        }
        battInfoManager.mInitFinished = true;
    }
}
