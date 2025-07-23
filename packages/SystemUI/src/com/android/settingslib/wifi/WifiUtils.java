package com.android.settingslib.wifi;

import android.R;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.SystemClock;
import android.util.ArraySet;
import com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda2;
import com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda3;
import com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda4;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class WifiUtils {
    public static final Companion Companion = new Companion(null);
    public static final String ACTION_WIFI_DIALOG = "com.android.settings.WIFI_DIALOG";
    public static final String EXTRA_CHOSEN_WIFI_ENTRY_KEY = "key_chosen_wifientry_key";
    public static final String EXTRA_CONNECT_FOR_CALLER = "connect_for_caller";
    public static final int[] WIFI_PIE = {R.drawable.list_selector_background_disabled, R.drawable.list_selector_background_disabled_light, R.drawable.list_selector_background_focus, R.drawable.list_selector_background_focused, R.drawable.list_selector_background_focused_light};
    public static final int[] NO_INTERNET_WIFI_PIE = {com.android.systemui.R.drawable.ic_no_internet_wifi_signal_0, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_1, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_2, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_3, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_4};

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String getVisibilityStatus(AccessPoint accessPoint) {
            String str;
            int i;
            StringBuilder sb;
            int i2;
            int i3;
            boolean z = true;
            WifiInfo wifiInfo = accessPoint.mInfo;
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            StringBuilder sb4 = new StringBuilder();
            StringBuilder sb5 = new StringBuilder();
            if (!accessPoint.isActive() || wifiInfo == null) {
                str = null;
            } else {
                str = wifiInfo.getBSSID();
                if (str != null) {
                    sb2.append(" ");
                    sb2.append(str);
                }
                sb2.append(" standard = ");
                sb2.append(wifiInfo.getWifiStandard());
                sb2.append(" rssi=");
                sb2.append(wifiInfo.getRssi());
                sb2.append("  score=");
                sb2.append(wifiInfo.getScore());
                if (accessPoint.mSpeed != 0) {
                    sb2.append(" speed=");
                    sb2.append(AccessPoint.getSpeedLabel(accessPoint.mSpeed, accessPoint.mContext));
                }
                int i4 = StringCompanionObject.$r8$clinit;
                sb2.append(String.format(" tx=%.1f,", Arrays.copyOf(new Object[]{Double.valueOf(wifiInfo.getSuccessfulTxPacketsPerSecond())}, 1)));
                sb2.append(String.format("%.1f,", Arrays.copyOf(new Object[]{Double.valueOf(wifiInfo.getRetriedTxPacketsPerSecond())}, 1)));
                sb2.append(String.format("%.1f ", Arrays.copyOf(new Object[]{Double.valueOf(wifiInfo.getLostTxPacketsPerSecond())}, 1)));
                sb2.append(String.format("rx=%.1f", Arrays.copyOf(new Object[]{Double.valueOf(wifiInfo.getSuccessfulRxPacketsPerSecond())}, 1)));
            }
            String str2 = str;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            ArraySet arraySet = new ArraySet();
            synchronized (accessPoint.mLock) {
                arraySet.addAll((Collection) accessPoint.mScanResults);
                arraySet.addAll((Collection) accessPoint.mExtraScanResults);
            }
            Iterator it = arraySet.iterator();
            int i5 = -127;
            int i6 = 0;
            int i7 = 0;
            int i8 = 0;
            int i9 = -127;
            int i10 = -127;
            while (true) {
                boolean z2 = z;
                if (!it.hasNext()) {
                    break;
                }
                ScanResult scanResult = (ScanResult) it.next();
                if (scanResult == null) {
                    i = i5;
                    sb = sb4;
                    i2 = i9;
                } else {
                    int i11 = scanResult.frequency;
                    int i12 = i5;
                    if (i11 < 4900 || i11 > 5900) {
                        i = i12;
                        int i13 = i9;
                        if (i11 < 2400 || i11 > 2500) {
                            sb = sb4;
                            i2 = i13;
                            if (i11 >= 58320 && i11 <= 70200) {
                                i8++;
                                int i14 = scanResult.level;
                                if (i14 > i) {
                                    i = i14;
                                }
                                if (i8 <= 4) {
                                    sb5.append(verboseScanResultSummary(accessPoint, scanResult, str2, elapsedRealtime));
                                }
                            }
                        } else {
                            i6++;
                            int i15 = scanResult.level;
                            if (i15 > i10) {
                                i10 = i15;
                            }
                            if (i6 <= 4) {
                                sb = sb4;
                                i2 = i13;
                                sb3.append(verboseScanResultSummary(accessPoint, scanResult, str2, elapsedRealtime));
                            } else {
                                sb = sb4;
                                i2 = i13;
                            }
                        }
                    } else {
                        i7++;
                        int i16 = scanResult.level;
                        int i17 = i16 > i9 ? i16 : i9;
                        if (i7 <= 4) {
                            i3 = i12;
                            sb4.append(verboseScanResultSummary(accessPoint, scanResult, str2, elapsedRealtime));
                        } else {
                            i3 = i12;
                        }
                        i5 = i3;
                        z = z2;
                        i9 = i17;
                    }
                }
                i5 = i;
                i9 = i2;
                z = z2;
                sb4 = sb;
            }
            int i18 = i5;
            StringBuilder sb6 = sb4;
            int i19 = i9;
            sb2.append(" [");
            if (i6 > 0) {
                sb2.append("(");
                sb2.append(i6);
                sb2.append(")");
                if (i6 > 4) {
                    sb2.append("max=");
                    sb2.append(i10);
                    sb2.append(",");
                }
                sb2.append(sb3.toString());
            }
            sb2.append(";");
            if (i7 > 0) {
                sb2.append("(");
                sb2.append(i7);
                sb2.append(")");
                if (i7 > 4) {
                    sb2.append("max=");
                    sb2.append(i19);
                    sb2.append(",");
                }
                sb2.append(sb6.toString());
            }
            sb2.append(";");
            if (i8 > 0) {
                sb2.append("(");
                sb2.append(i8);
                sb2.append(")");
                if (i8 > 4) {
                    sb2.append("max=");
                    sb2.append(i18);
                    sb2.append(",");
                }
                sb2.append(sb5.toString());
            }
            sb2.append("]");
            return sb2.toString();
        }

        public final String verboseScanResultSummary(AccessPoint accessPoint, ScanResult scanResult, String str, long j) {
            StringBuilder sb = new StringBuilder(" \n{");
            sb.append(scanResult.BSSID);
            if (Intrinsics.areEqual(scanResult.BSSID, str)) {
                sb.append("*");
            }
            sb.append("=");
            sb.append(scanResult.frequency);
            sb.append(",");
            sb.append(scanResult.level);
            TimestampedScoredNetwork timestampedScoredNetwork = (TimestampedScoredNetwork) accessPoint.mScoredNetworkCache.get(scanResult.BSSID);
            int calculateBadge = timestampedScoredNetwork == null ? 0 : timestampedScoredNetwork.mScore.calculateBadge(scanResult.level);
            if (calculateBadge != 0) {
                sb.append(",");
                sb.append(AccessPoint.getSpeedLabel(calculateBadge, accessPoint.mContext));
            }
            int i = ((int) (j - (scanResult.timestamp / 1000))) / 1000;
            sb.append(",");
            sb.append(i);
            sb.append("s}");
            return sb.toString();
        }

        private Companion() {
        }

        public static /* synthetic */ void getACTION_WIFI_DIALOG$annotations() {
        }

        public static /* synthetic */ void getEXTRA_CHOSEN_WIFI_ENTRY_KEY$annotations() {
        }

        public static /* synthetic */ void getEXTRA_CONNECT_FOR_CALLER$annotations() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class InternetIconInjector {
        public final Context context;

        public InternetIconInjector(Context context) {
            this.context = context;
        }
    }

    public static final StandaloneCoroutine checkWepAllowed(Context context, CoroutineScope coroutineScope, String str, InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda2 internetAdapter$InternetViewHolder$$ExternalSyntheticLambda2, InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda3 internetAdapter$InternetViewHolder$$ExternalSyntheticLambda3, InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda4 internetAdapter$InternetViewHolder$$ExternalSyntheticLambda4) {
        Companion.getClass();
        return BuildersKt.launch$default(coroutineScope, null, null, new WifiUtils$Companion$checkWepAllowed$2(context, 2009, internetAdapter$InternetViewHolder$$ExternalSyntheticLambda4, internetAdapter$InternetViewHolder$$ExternalSyntheticLambda3, str, internetAdapter$InternetViewHolder$$ExternalSyntheticLambda2, null), 3);
    }

    public static final String getVisibilityStatus(AccessPoint accessPoint) {
        return Companion.getVisibilityStatus(accessPoint);
    }

    public static final String verboseScanResultSummary(AccessPoint accessPoint, ScanResult scanResult, String str, long j) {
        return Companion.verboseScanResultSummary(accessPoint, scanResult, str, j);
    }
}
