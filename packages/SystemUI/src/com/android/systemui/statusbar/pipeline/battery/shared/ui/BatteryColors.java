package com.android.systemui.statusbar.pipeline.battery.shared.ui;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface BatteryColors {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DarkThemeChargingColors implements BatteryColors {
        public static final DarkThemeChargingColors INSTANCE = new DarkThemeChargingColors();

        static {
            ColorKt.Color(4282672640L);
            ColorKt.Color(4290051870L);
            ColorKt.Color(4292280195L);
        }

        private DarkThemeChargingColors() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof DarkThemeChargingColors);
        }

        public final int hashCode() {
            return 457957745;
        }

        public final String toString() {
            return "DarkThemeChargingColors";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DarkThemeDefaultColors implements BatteryColors {
        public static final DarkThemeDefaultColors INSTANCE = new DarkThemeDefaultColors();

        static {
            Color.Companion.getClass();
            long j = Color.Black;
            long j2 = Color.White;
            ColorKt.Color(4291151301L);
        }

        private DarkThemeDefaultColors() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof DarkThemeDefaultColors);
        }

        public final int hashCode() {
            return -1407363359;
        }

        public final String toString() {
            return "DarkThemeDefaultColors";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DarkThemeErrorColors implements BatteryColors {
        public static final DarkThemeErrorColors INSTANCE = new DarkThemeErrorColors();

        static {
            ColorKt.Color(4286121530L);
            ColorKt.Color(4294902118L);
            ColorKt.Color(4294937786L);
        }

        private DarkThemeErrorColors() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof DarkThemeErrorColors);
        }

        public final int hashCode() {
            return -1889610392;
        }

        public final String toString() {
            return "DarkThemeErrorColors";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DarkThemePowerSaveColors implements BatteryColors {
        public static final DarkThemePowerSaveColors INSTANCE = new DarkThemePowerSaveColors();

        static {
            ColorKt.Color(4284108288L);
            ColorKt.Color(4294957591L);
            ColorKt.Color(4294962047L);
        }

        private DarkThemePowerSaveColors() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof DarkThemePowerSaveColors);
        }

        public final int hashCode() {
            return -662318078;
        }

        public final String toString() {
            return "DarkThemePowerSaveColors";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LightThemeChargingColors implements BatteryColors {
        public static final LightThemeChargingColors INSTANCE = new LightThemeChargingColors();

        static {
            ColorKt.Color(4282672640L);
            ColorKt.Color(4290051870L);
            ColorKt.Color(4292280195L);
        }

        private LightThemeChargingColors() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof LightThemeChargingColors);
        }

        public final int hashCode() {
            return 730231991;
        }

        public final String toString() {
            return "LightThemeChargingColors";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LightThemeDefaultColors implements BatteryColors {
        public static final LightThemeDefaultColors INSTANCE = new LightThemeDefaultColors();

        static {
            Color.Companion.getClass();
            long j = Color.White;
            long j2 = Color.Black;
            ColorKt.Color(4287401100L);
        }

        private LightThemeDefaultColors() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof LightThemeDefaultColors);
        }

        public final int hashCode() {
            return 541082331;
        }

        public final String toString() {
            return "LightThemeDefaultColors";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LightThemeErrorColors implements BatteryColors {
        public static final LightThemeErrorColors INSTANCE = new LightThemeErrorColors();

        static {
            ColorKt.Color(4286121530L);
            ColorKt.Color(4294902118L);
            ColorKt.Color(4294937786L);
        }

        private LightThemeErrorColors() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof LightThemeErrorColors);
        }

        public final int hashCode() {
            return 2103474146;
        }

        public final String toString() {
            return "LightThemeErrorColors";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LightThemePowerSaveColors implements BatteryColors {
        public static final LightThemePowerSaveColors INSTANCE = new LightThemePowerSaveColors();

        static {
            ColorKt.Color(4284108288L);
            ColorKt.Color(4294957591L);
            ColorKt.Color(4294962047L);
        }

        private LightThemePowerSaveColors() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof LightThemePowerSaveColors);
        }

        public final int hashCode() {
            return -811751044;
        }

        public final String toString() {
            return "LightThemePowerSaveColors";
        }
    }
}
