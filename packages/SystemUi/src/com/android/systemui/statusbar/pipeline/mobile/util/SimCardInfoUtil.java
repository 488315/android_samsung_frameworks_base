package com.android.systemui.statusbar.pipeline.mobile.util;

import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.systemui.BasicRune;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SimType;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.GlobalSettingsImpl;
import com.android.systemui.util.settings.SystemSettings;
import com.android.systemui.util.settings.SystemSettingsImpl;
import java.util.Locale;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SimCardInfoUtil {
    public final GlobalSettings globalSettings;
    public final SystemSettings systemSettings;
    public final TelephonyManager telephonyManager;

    public SimCardInfoUtil(TelephonyManager telephonyManager, GlobalSettings globalSettings, SystemSettings systemSettings) {
        this.telephonyManager = telephonyManager;
        this.globalSettings = globalSettings;
        this.systemSettings = systemSettings;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005c, code lost:
    
        if (r1.equals("311270") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0066, code lost:
    
        if (r1.equals("310004") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0070, code lost:
    
        if (r1.equals("46007") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x007a, code lost:
    
        if (r1.equals("46002") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0084, code lost:
    
        if (r1.equals("46000") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x008e, code lost:
    
        if (r1.equals("45412") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00c2, code lost:
    
        if (r1.equals("20802") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00cb, code lost:
    
        if (r1.equals("20801") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00d7, code lost:
    
        if (r1.equals("20404") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0021, code lost:
    
        if (r1.equals("312770") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x00de, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual("BAE0000000000000", r5) == false) goto L68;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final SimType getSimCardInfo(int i) {
        int slotIndex = SubscriptionManager.getSlotIndex(i);
        TelephonyManager telephonyManager = this.telephonyManager;
        String simOperatorNumericForPhone = telephonyManager.getSimOperatorNumericForPhone(slotIndex);
        String groupIdLevel1 = telephonyManager.getGroupIdLevel1(i);
        if (simOperatorNumericForPhone != null) {
            switch (simOperatorNumericForPhone.hashCode()) {
                case 47657530:
                    break;
                case 47661371:
                    break;
                case 47661372:
                    break;
                case 49649684:
                    if (simOperatorNumericForPhone.equals("45005")) {
                        break;
                    }
                    break;
                case 49649685:
                    if (simOperatorNumericForPhone.equals("45006")) {
                        break;
                    }
                    break;
                case 49649687:
                    if (simOperatorNumericForPhone.equals("45008")) {
                        break;
                    }
                    break;
                case 49653556:
                    break;
                case 49679470:
                    break;
                case 49679472:
                    break;
                case 49679477:
                    break;
                case 1506816866:
                    break;
                case 1506848792:
                    break;
                case 1506850745:
                    if (simOperatorNumericForPhone.equals("311480")) {
                        if (!Intrinsics.areEqual("BAE0000000000000", groupIdLevel1) && !Intrinsics.areEqual("BAE1000000000000", groupIdLevel1) && !Intrinsics.areEqual("BAE2000000000000", groupIdLevel1) && !Intrinsics.areEqual("BA01270000000000", groupIdLevel1)) {
                            break;
                        } else {
                            break;
                        }
                    }
                    break;
                case 1506883388:
                    break;
            }
        }
        String simOperatorNameForPhone = telephonyManager.getSimOperatorNameForPhone(slotIndex);
        if (!TextUtils.isEmpty(simOperatorNameForPhone) ? StringsKt__StringsKt.contains(simOperatorNameForPhone.toLowerCase(Locale.ROOT), "airtel", false) : false) {
            return SimType.AIRTEL;
        }
        String simOperatorNameForPhone2 = telephonyManager.getSimOperatorNameForPhone(slotIndex);
        return TextUtils.isEmpty(simOperatorNameForPhone2) ? false : StringsKt__StringsKt.contains(simOperatorNameForPhone2.toLowerCase(Locale.ROOT), "jio", false) ? SimType.RELIANCE : SimType.ETC;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0054 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isSimSettingOn(int i) {
        String str;
        GlobalSettings globalSettings = this.globalSettings;
        try {
        } catch (Settings.SettingNotFoundException unused) {
            boolean z = BasicRune.STATUS_NETWORK_MULTI_SIM;
            String str2 = (!z || i == 0) ? "phone1_on" : "phone2_on";
            SystemSettings systemSettings = this.systemSettings;
            try {
                int parseInt = Integer.parseInt(((SystemSettingsImpl) systemSettings).getStringForUser(systemSettings.getUserId(), str2));
                globalSettings.putIntForUser(parseInt, globalSettings.getUserId(), (!z || i == 0) ? "phone1_on" : "phone2_on");
                if (parseInt == 1) {
                    return true;
                }
            } catch (NumberFormatException unused2) {
                throw new Settings.SettingNotFoundException(str2);
            }
        }
        try {
            if (BasicRune.STATUS_NETWORK_MULTI_SIM && i != 0) {
                str = "phone2_on";
                return Integer.parseInt(((GlobalSettingsImpl) globalSettings).getStringForUser(globalSettings.getUserId(), str)) != 1;
            }
            if (Integer.parseInt(((GlobalSettingsImpl) globalSettings).getStringForUser(globalSettings.getUserId(), str)) != 1) {
            }
        } catch (NumberFormatException unused3) {
            throw new Settings.SettingNotFoundException(str);
        }
        str = "phone1_on";
    }
}
