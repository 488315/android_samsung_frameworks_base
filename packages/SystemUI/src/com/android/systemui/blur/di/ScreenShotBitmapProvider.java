package com.android.systemui.blur.di;

import android.graphics.Bitmap;
import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface ScreenShotBitmapProvider {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int RESIZE_SCALE = 5;

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Type {
        public static final /* synthetic */ Type[] $VALUES;
        public static final Type WALLPAPER;
        public static final Type WINDOW_MANAGER;

        static {
            Type type = new Type("WINDOW_MANAGER", 0);
            WINDOW_MANAGER = type;
            Type type2 = new Type("WALLPAPER", 1);
            WALLPAPER = type2;
            Type[] typeArr = {type, type2};
            $VALUES = typeArr;
            EnumEntriesKt.enumEntries(typeArr);
        }

        private Type(String str, int i) {
        }

        public static Type valueOf(String str) {
            return (Type) Enum.valueOf(Type.class, str);
        }

        public static Type[] values() {
            return (Type[]) $VALUES.clone();
        }
    }

    Bitmap getScreenShot();
}
