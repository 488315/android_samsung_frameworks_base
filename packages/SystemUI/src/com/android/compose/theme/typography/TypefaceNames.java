package com.android.compose.theme.typography;

import android.content.Context;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class TypefaceNames {
    public static final Companion Companion = new Companion(null);
    public final String brand;
    public final String plain;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static TypefaceNames get(Context context) {
            return new TypefaceNames(getTypefaceName(context, Config.Brand), getTypefaceName(context, Config.Plain), null);
        }

        public static String getTypefaceName(Context context, Config config) {
            String string = context.getString(context.getResources().getIdentifier(config.getConfigName(), "string", "android"));
            string.getClass();
            if (string.length() <= 0) {
                string = null;
            }
            return string == null ? config.getDefault() : string;
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class Config {
        public static final /* synthetic */ Config[] $VALUES;
        public static final Config Brand;
        public static final Config Plain;
        private final String configName;

        /* renamed from: default, reason: not valid java name */
        private final String f25default;

        static {
            Config config = new Config("Brand", 0, "config_headlineFontFamily", "sans-serif");
            Brand = config;
            Config config2 = new Config("Plain", 1, "config_bodyFontFamily", "sans-serif");
            Plain = config2;
            Config[] configArr = {config, config2};
            $VALUES = configArr;
            EnumEntriesKt.enumEntries(configArr);
        }

        private Config(String str, int i, String str2, String str3) {
            this.configName = str2;
            this.f25default = str3;
        }

        public static Config valueOf(String str) {
            return (Config) Enum.valueOf(Config.class, str);
        }

        public static Config[] values() {
            return (Config[]) $VALUES.clone();
        }

        public final String getConfigName() {
            return this.configName;
        }

        public final String getDefault() {
            return this.f25default;
        }
    }

    public /* synthetic */ TypefaceNames(String str, String str2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TypefaceNames)) {
            return false;
        }
        TypefaceNames typefaceNames = (TypefaceNames) obj;
        return Intrinsics.areEqual(this.brand, typefaceNames.brand) && Intrinsics.areEqual(this.plain, typefaceNames.plain);
    }

    public final int hashCode() {
        return this.plain.hashCode() + (this.brand.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TypefaceNames(brand=");
        sb.append(this.brand);
        sb.append(", plain=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.plain, ")");
    }

    private TypefaceNames(String str, String str2) {
        this.brand = str;
        this.plain = str2;
    }
}
