package com.android.settingslib.widget;

import android.content.Context;
import android.os.flagging.AconfigPackage;
import android.util.Log;
import com.android.settingslib.widget.theme.flags.FeatureFlagsImpl;
import com.android.settingslib.widget.theme.flags.Flags;
import java.util.Arrays;
import kotlin.enums.EnumEntriesKt;

/* loaded from: classes.dex */
public final class SettingsThemeHelper {
    public static final SettingsThemeHelper INSTANCE = new SettingsThemeHelper();
    public static ExpressiveThemeState expressiveThemeState = ExpressiveThemeState.UNKNOWN;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class ExpressiveThemeState {
        public static final /* synthetic */ ExpressiveThemeState[] $VALUES;
        public static final ExpressiveThemeState DISABLED;
        public static final ExpressiveThemeState ENABLED;
        public static final ExpressiveThemeState UNKNOWN;

        static {
            ExpressiveThemeState expressiveThemeState = new ExpressiveThemeState("UNKNOWN", 0);
            UNKNOWN = expressiveThemeState;
            ExpressiveThemeState expressiveThemeState2 = new ExpressiveThemeState("ENABLED", 1);
            ENABLED = expressiveThemeState2;
            ExpressiveThemeState expressiveThemeState3 = new ExpressiveThemeState("DISABLED", 2);
            DISABLED = expressiveThemeState3;
            ExpressiveThemeState[] expressiveThemeStateArr = {expressiveThemeState, expressiveThemeState2, expressiveThemeState3};
            $VALUES = expressiveThemeStateArr;
            EnumEntriesKt.enumEntries(expressiveThemeStateArr);
        }

        private ExpressiveThemeState(String str, int i) {
        }

        public static ExpressiveThemeState valueOf(String str) {
            return (ExpressiveThemeState) Enum.valueOf(ExpressiveThemeState.class, str);
        }

        public static ExpressiveThemeState[] values() {
            return (ExpressiveThemeState[]) $VALUES.clone();
        }
    }

    private SettingsThemeHelper() {
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0077  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean isExpressiveTheme(Context context) throws Exception {
        boolean zBooleanValue;
        ExpressiveThemeState expressiveThemeState2;
        INSTANCE.getClass();
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            zBooleanValue = ((Boolean) clsLoadClass.getMethod("getBoolean", (Class[]) Arrays.copyOf(new Class[]{String.class, Boolean.TYPE}, 2)).invoke(clsLoadClass, Arrays.copyOf(new Object[]{"is_expressive_design_enabled", Boolean.FALSE}, 2))).booleanValue();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception unused) {
            zBooleanValue = false;
        }
        if (!zBooleanValue) {
            Flags.FEATURE_FLAGS.getClass();
            if (!FeatureFlagsImpl.isCached) {
                try {
                    FeatureFlagsImpl.isExpressiveDesignEnabled = AconfigPackage.load("com.android.settingslib.widget.theme.flags").getBooleanFlagValue("is_expressive_design_enabled", false);
                } catch (Exception e2) {
                    Log.e("FeatureFlagsImplExport", e2.toString());
                } catch (LinkageError e3) {
                    Log.w("FeatureFlagsImplExport", e3.toString());
                }
                FeatureFlagsImpl.isCached = true;
            }
            expressiveThemeState2 = FeatureFlagsImpl.isExpressiveDesignEnabled ? ExpressiveThemeState.ENABLED : ExpressiveThemeState.DISABLED;
        }
        expressiveThemeState = expressiveThemeState2;
        if (expressiveThemeState2 != ExpressiveThemeState.UNKNOWN) {
            return expressiveThemeState == ExpressiveThemeState.ENABLED;
        }
        throw new Exception("need to call com.android.settingslib.widget.SettingsThemeHelper.init(Context) first.");
    }
}
