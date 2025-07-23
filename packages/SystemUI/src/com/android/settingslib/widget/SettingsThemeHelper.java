package com.android.settingslib.widget;

import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SettingsThemeHelper {
    public static final SettingsThemeHelper INSTANCE = new SettingsThemeHelper();
    public static ExpressiveThemeState expressiveThemeState = ExpressiveThemeState.UNKNOWN;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:21:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final boolean isExpressiveTheme(android.content.Context r6) {
        /*
            com.android.settingslib.widget.SettingsThemeHelper r0 = com.android.settingslib.widget.SettingsThemeHelper.INSTANCE
            r0.getClass()
            java.lang.String r0 = "is_expressive_design_enabled"
            r1 = 0
            r2 = 1
            java.lang.ClassLoader r6 = r6.getClassLoader()     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            java.lang.String r3 = "android.os.SystemProperties"
            java.lang.Class r6 = r6.loadClass(r3)     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            r3 = 2
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r4[r1] = r5     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            java.lang.Class r5 = java.lang.Boolean.TYPE     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            r4[r2] = r5     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            java.lang.String r5 = "getBoolean"
            java.lang.Object[] r4 = java.util.Arrays.copyOf(r4, r3)     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            java.lang.Class[] r4 = (java.lang.Class[]) r4     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            java.lang.reflect.Method r4 = r6.getMethod(r5, r4)     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            java.lang.Boolean r5 = java.lang.Boolean.FALSE     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            java.lang.Object[] r5 = new java.lang.Object[]{r0, r5}     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            java.lang.Object[] r3 = java.util.Arrays.copyOf(r5, r3)     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            java.lang.Object r6 = r4.invoke(r6, r3)     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            boolean r6 = r6.booleanValue()     // Catch: java.lang.Exception -> L3f java.lang.IllegalArgumentException -> L8f
            goto L40
        L3f:
            r6 = r1
        L40:
            if (r6 != 0) goto L77
            com.android.settingslib.widget.theme.flags.FeatureFlagsImpl r6 = com.android.settingslib.widget.theme.flags.Flags.FEATURE_FLAGS
            r6.getClass()
            boolean r6 = com.android.settingslib.widget.theme.flags.FeatureFlagsImpl.isCached
            if (r6 != 0) goto L6f
            java.lang.String r6 = "FeatureFlagsImplExport"
            java.lang.String r3 = "com.android.settingslib.widget.theme.flags"
            android.os.flagging.AconfigPackage r3 = android.os.flagging.AconfigPackage.load(r3)     // Catch: java.lang.LinkageError -> L5a java.lang.Exception -> L5c
            boolean r0 = r3.getBooleanFlagValue(r0, r1)     // Catch: java.lang.LinkageError -> L5a java.lang.Exception -> L5c
            com.android.settingslib.widget.theme.flags.FeatureFlagsImpl.isExpressiveDesignEnabled = r0     // Catch: java.lang.LinkageError -> L5a java.lang.Exception -> L5c
            goto L6d
        L5a:
            r0 = move-exception
            goto L5e
        L5c:
            r0 = move-exception
            goto L66
        L5e:
            java.lang.String r0 = r0.toString()
            android.util.Log.w(r6, r0)
            goto L6d
        L66:
            java.lang.String r0 = r0.toString()
            android.util.Log.e(r6, r0)
        L6d:
            com.android.settingslib.widget.theme.flags.FeatureFlagsImpl.isCached = r2
        L6f:
            boolean r6 = com.android.settingslib.widget.theme.flags.FeatureFlagsImpl.isExpressiveDesignEnabled
            if (r6 == 0) goto L74
            goto L77
        L74:
            com.android.settingslib.widget.SettingsThemeHelper$ExpressiveThemeState r6 = com.android.settingslib.widget.SettingsThemeHelper.ExpressiveThemeState.DISABLED
            goto L79
        L77:
            com.android.settingslib.widget.SettingsThemeHelper$ExpressiveThemeState r6 = com.android.settingslib.widget.SettingsThemeHelper.ExpressiveThemeState.ENABLED
        L79:
            com.android.settingslib.widget.SettingsThemeHelper.expressiveThemeState = r6
            com.android.settingslib.widget.SettingsThemeHelper$ExpressiveThemeState r0 = com.android.settingslib.widget.SettingsThemeHelper.ExpressiveThemeState.UNKNOWN
            if (r6 == r0) goto L87
            com.android.settingslib.widget.SettingsThemeHelper$ExpressiveThemeState r6 = com.android.settingslib.widget.SettingsThemeHelper.expressiveThemeState
            com.android.settingslib.widget.SettingsThemeHelper$ExpressiveThemeState r0 = com.android.settingslib.widget.SettingsThemeHelper.ExpressiveThemeState.ENABLED
            if (r6 != r0) goto L86
            r1 = r2
        L86:
            return r1
        L87:
            java.lang.Exception r6 = new java.lang.Exception
            java.lang.String r0 = "need to call com.android.settingslib.widget.SettingsThemeHelper.init(Context) first."
            r6.<init>(r0)
            throw r6
        L8f:
            r6 = move-exception
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.widget.SettingsThemeHelper.isExpressiveTheme(android.content.Context):boolean");
    }
}
