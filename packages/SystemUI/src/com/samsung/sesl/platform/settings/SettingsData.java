package com.samsung.sesl.platform.settings;

import android.content.ContentResolver;
import android.net.Uri;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SettingsData {
    public /* synthetic */ SettingsData(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract SettingDB getDb();

    public abstract String getKey();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SettingsBooleanData extends SettingsData {
        public final SettingDB db;
        public final boolean defaultValue;
        public final String key;

        public /* synthetic */ SettingsBooleanData(String str, boolean z, int i, SettingDB settingDB, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, z, i, (i2 & 8) != 0 ? SettingDB.Global : settingDB);
        }

        @Override // com.samsung.sesl.platform.settings.SettingsData
        public final SettingDB getDb() {
            return this.db;
        }

        @Override // com.samsung.sesl.platform.settings.SettingsData
        public final String getKey() {
            return this.key;
        }

        public final Boolean getSettingValue$phone_release(ContentResolver contentResolver) {
            return Boolean.valueOf(((Number) this.db.getGetInt().invoke(contentResolver, this.key, Integer.valueOf(this.defaultValue ? 1 : 0))).intValue() == 1);
        }

        public SettingsBooleanData(String str, boolean z, int i, SettingDB settingDB) {
            super(null);
            this.key = str;
            this.defaultValue = z;
            this.db = settingDB;
        }
    }

    private SettingsData() {
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.platform.settings.SettingsData$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SettingsData settingsData = SettingsData.this;
                return (Uri) settingsData.getDb().getGetUriFor().mo779invoke(settingsData.getKey());
            }
        });
    }
}
