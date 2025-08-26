package com.samsung.sesl.platform.settings;

import android.content.ContentResolver;
import android.provider.Settings;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class SettingDB {
    public static final /* synthetic */ SettingDB[] $VALUES;
    public static final SettingDB Global;
    private final Function3 getInt;
    private final Function1 getUriFor;

    /* renamed from: com.samsung.sesl.platform.settings.SettingDB$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(1, Settings.Global.class, "getUriFor", "getUriFor(Ljava/lang/String;)Landroid/net/Uri;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return Settings.Global.getUriFor((String) obj);
        }
    }

    /* renamed from: com.samsung.sesl.platform.settings.SettingDB$2, reason: invalid class name */
    final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function3 {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(3, Settings.Global.class, "getInt", "getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I", 0);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return Integer.valueOf(Settings.Global.getInt((ContentResolver) obj, (String) obj2, ((Number) obj3).intValue()));
        }
    }

    /* renamed from: com.samsung.sesl.platform.settings.SettingDB$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1 {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        public AnonymousClass3() {
            super(1, Settings.System.class, "getUriFor", "getUriFor(Ljava/lang/String;)Landroid/net/Uri;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return Settings.System.getUriFor((String) obj);
        }
    }

    /* renamed from: com.samsung.sesl.platform.settings.SettingDB$4, reason: invalid class name */
    final /* synthetic */ class AnonymousClass4 extends FunctionReferenceImpl implements Function3 {
        public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

        public AnonymousClass4() {
            super(3, Settings.System.class, "getInt", "getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I", 0);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return Integer.valueOf(Settings.System.getInt((ContentResolver) obj, (String) obj2, ((Number) obj3).intValue()));
        }
    }

    /* renamed from: com.samsung.sesl.platform.settings.SettingDB$5, reason: invalid class name */
    final /* synthetic */ class AnonymousClass5 extends FunctionReferenceImpl implements Function1 {
        public static final AnonymousClass5 INSTANCE = new AnonymousClass5();

        public AnonymousClass5() {
            super(1, Settings.Secure.class, "getUriFor", "getUriFor(Ljava/lang/String;)Landroid/net/Uri;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return Settings.Secure.getUriFor((String) obj);
        }
    }

    /* renamed from: com.samsung.sesl.platform.settings.SettingDB$6, reason: invalid class name */
    final /* synthetic */ class AnonymousClass6 extends FunctionReferenceImpl implements Function3 {
        public static final AnonymousClass6 INSTANCE = new AnonymousClass6();

        public AnonymousClass6() {
            super(3, Settings.Secure.class, "getInt", "getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I", 0);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return Integer.valueOf(Settings.Secure.getInt((ContentResolver) obj, (String) obj2, ((Number) obj3).intValue()));
        }
    }

    static {
        SettingDB settingDB = new SettingDB("Global", 0, AnonymousClass1.INSTANCE, AnonymousClass2.INSTANCE);
        Global = settingDB;
        SettingDB[] settingDBArr = {settingDB, new SettingDB("System", 1, AnonymousClass3.INSTANCE, AnonymousClass4.INSTANCE), new SettingDB("Secure", 2, AnonymousClass5.INSTANCE, AnonymousClass6.INSTANCE)};
        $VALUES = settingDBArr;
        EnumEntriesKt.enumEntries(settingDBArr);
    }

    private SettingDB(String str, int i, Function1 function1, Function3 function3) {
        this.getUriFor = function1;
        this.getInt = function3;
    }

    public static SettingDB valueOf(String str) {
        return (SettingDB) Enum.valueOf(SettingDB.class, str);
    }

    public static SettingDB[] values() {
        return (SettingDB[]) $VALUES.clone();
    }

    public final Function3 getGetInt() {
        return this.getInt;
    }

    public final Function1 getGetUriFor() {
        return this.getUriFor;
    }
}
