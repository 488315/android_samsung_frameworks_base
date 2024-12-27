package com.android.server.stats.pull;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Slog;
import android.util.StatsEvent;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.service.nano.StringListParamProto;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class SettingsStatsUtil {
    public static final FlagsData[] GLOBAL_SETTINGS = {
        new FlagsData("GlobalFeature__boolean_whitelist", 1),
        new FlagsData("GlobalFeature__integer_whitelist", 2),
        new FlagsData("GlobalFeature__float_whitelist", 3),
        new FlagsData("GlobalFeature__string_whitelist", 4)
    };
    public static final FlagsData[] SECURE_SETTINGS = {
        new FlagsData("SecureFeature__boolean_whitelist", 1),
        new FlagsData("SecureFeature__integer_whitelist", 2),
        new FlagsData("SecureFeature__float_whitelist", 3),
        new FlagsData("SecureFeature__string_whitelist", 4)
    };
    public static final FlagsData[] SYSTEM_SETTINGS = {
        new FlagsData("SystemFeature__boolean_whitelist", 1),
        new FlagsData("SystemFeature__integer_whitelist", 2),
        new FlagsData("SystemFeature__float_whitelist", 3),
        new FlagsData("SystemFeature__string_whitelist", 4)
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FlagsData {
        public final int mDataType;
        public final String mFlagName;

        public FlagsData(String str, int i) {
            this.mFlagName = str;
            this.mDataType = i;
        }
    }

    public static StatsEvent createStatsEvent(int i, int i2, int i3, String str, String str2) {
        int i4;
        StatsEvent.Builder writeString = StatsEvent.newBuilder().setAtomId(i).writeString(str);
        boolean isEmpty = TextUtils.isEmpty(str2);
        boolean z = false;
        float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
        if (isEmpty) {
            writeString
                    .writeInt(0)
                    .writeBoolean(false)
                    .writeInt(0)
                    .writeFloat(FullScreenMagnificationGestureHandler.MAX_SCALE)
                    .writeString("")
                    .writeInt(i2);
        } else {
            if (i3 != 1) {
                if (i3 == 2) {
                    try {
                        i4 = Integer.parseInt(str2);
                    } catch (NumberFormatException unused) {
                        HeimdAllFsService$$ExternalSyntheticOutline0.m(
                                "Can not parse value to float: ", str2, "SettingsStatsUtil");
                    }
                    str2 = "";
                } else if (i3 == 3) {
                    try {
                        f = Float.parseFloat(str2);
                    } catch (NumberFormatException unused2) {
                        HeimdAllFsService$$ExternalSyntheticOutline0.m(
                                "Can not parse value to float: ", str2, "SettingsStatsUtil");
                    }
                } else if (i3 != 4) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            i3, "Unexpected value type ", "SettingsStatsUtil");
                } else {
                    i4 = 0;
                }
                i4 = 0;
                str2 = "";
            } else {
                boolean equals = "1".equals(str2);
                str2 = "";
                z = equals;
                i4 = 0;
            }
            writeString
                    .writeInt(i3)
                    .writeBoolean(z)
                    .writeInt(i4)
                    .writeFloat(f)
                    .writeString(str2)
                    .writeInt(i2);
        }
        return writeString.build();
    }

    public static StringListParamProto getList(String str) {
        String property = DeviceConfig.getProperty("settings_stats", str);
        if (TextUtils.isEmpty(property)) {
            return null;
        }
        try {
            return StringListParamProto.parseFrom(Base64.decode(property, 3));
        } catch (Exception e) {
            Slog.e("SettingsStatsUtil", "Error parsing string list proto", e);
            return null;
        }
    }

    public static List logGlobalSettings(Context context, int i, int i2) {
        ArrayList arrayList = new ArrayList();
        ContentResolver contentResolver = context.getContentResolver();
        FlagsData[] flagsDataArr = GLOBAL_SETTINGS;
        for (int i3 = 0; i3 < 4; i3++) {
            FlagsData flagsData = flagsDataArr[i3];
            StringListParamProto list = getList(flagsData.mFlagName);
            if (list != null) {
                for (String str : list.element) {
                    arrayList.add(
                            createStatsEvent(
                                    i,
                                    i2,
                                    flagsData.mDataType,
                                    str,
                                    Settings.Global.getStringForUser(contentResolver, str, i2)));
                }
            }
        }
        return arrayList;
    }

    public static List logSecureSettings(Context context, int i, int i2) {
        ArrayList arrayList = new ArrayList();
        ContentResolver contentResolver = context.getContentResolver();
        FlagsData[] flagsDataArr = SECURE_SETTINGS;
        for (int i3 = 0; i3 < 4; i3++) {
            FlagsData flagsData = flagsDataArr[i3];
            StringListParamProto list = getList(flagsData.mFlagName);
            if (list != null) {
                for (String str : list.element) {
                    arrayList.add(
                            createStatsEvent(
                                    i,
                                    i2,
                                    flagsData.mDataType,
                                    str,
                                    Settings.Secure.getStringForUser(contentResolver, str, i2)));
                }
            }
        }
        return arrayList;
    }

    public static List logSystemSettings(Context context, int i, int i2) {
        ArrayList arrayList = new ArrayList();
        ContentResolver contentResolver = context.getContentResolver();
        FlagsData[] flagsDataArr = SYSTEM_SETTINGS;
        for (int i3 = 0; i3 < 4; i3++) {
            FlagsData flagsData = flagsDataArr[i3];
            StringListParamProto list = getList(flagsData.mFlagName);
            if (list != null) {
                for (String str : list.element) {
                    arrayList.add(
                            createStatsEvent(
                                    i,
                                    i2,
                                    flagsData.mDataType,
                                    str,
                                    Settings.System.getStringForUser(contentResolver, str, i2)));
                }
            }
        }
        return arrayList;
    }
}
