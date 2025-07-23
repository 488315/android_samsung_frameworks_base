package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.navigation.NamedNavArgument;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class Screen {
    public static final Companion Companion = new Companion(null);
    public final List navArgument;
    public final String route;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CastSetting extends Screen {
        public static final CastSetting INSTANCE = new CastSetting();

        private CastSetting() {
            super("CastSetting", null, 2, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof CastSetting);
        }

        public final int hashCode() {
            return 930328957;
        }

        public final String toString() {
            return "CastSetting";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LabsHome extends Screen {
        public static final LabsHome INSTANCE = new LabsHome();

        private LabsHome() {
            super("Labs", null, 2, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof LabsHome);
        }

        public final int hashCode() {
            return -438566247;
        }

        public final String toString() {
            return "LabsHome";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Phone extends Screen {
        public static final Phone INSTANCE = new Phone();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private Phone() {
            /*
                r8 = this;
                androidx.navigation.NamedNavArgument r0 = new androidx.navigation.NamedNavArgument
                androidx.navigation.NavArgumentBuilder r1 = new androidx.navigation.NavArgumentBuilder
                r1.<init>()
                androidx.navigation.NavType$Companion$StringType$1 r3 = androidx.navigation.NavType.StringType
                androidx.navigation.NavArgument$Builder r1 = r1.builder
                r1.type = r3
                kotlin.Unit r2 = kotlin.Unit.INSTANCE
                if (r3 != 0) goto L16
                androidx.navigation.NavType$Companion r2 = androidx.navigation.NavType.Companion
                r2.getClass()
            L16:
                androidx.navigation.NavArgument r2 = new androidx.navigation.NavArgument
                boolean r4 = r1.isNullable
                boolean r7 = r1.unknownDefaultValuePresent
                r5 = 0
                r6 = 0
                r2.<init>(r3, r4, r5, r6, r7)
                java.lang.String r1 = "packageName"
                r0.<init>(r1, r2)
                java.util.List r0 = java.util.Collections.singletonList(r0)
                r1 = 0
                java.lang.String r2 = "Phone/{packageName}"
                r8.<init>(r2, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.Screen.Phone.<init>():void");
        }

        @Override // com.android.systemui.media.mediaoutput.compose.Screen
        public final String createRoute(String str) {
            return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Phone/", str);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Phone);
        }

        public final int hashCode() {
            return 1243617754;
        }

        public final String toString() {
            return "Phone";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Selector extends Screen {
        public static final Selector INSTANCE = new Selector();

        private Selector() {
            super("Selector", null, 2, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Selector);
        }

        public final int hashCode() {
            return -1594576909;
        }

        public final String toString() {
            return "Selector";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SettingHome extends Screen {
        public static final SettingHome INSTANCE = new SettingHome();

        private SettingHome() {
            super("SettingHome", null, 2, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof SettingHome);
        }

        public final int hashCode() {
            return -2055034181;
        }

        public final String toString() {
            return "SettingHome";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SpotifyCastSetting extends Screen {
        public static final SpotifyCastSetting INSTANCE = new SpotifyCastSetting();

        private SpotifyCastSetting() {
            super("SpotifyCastSetting", null, 2, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof SpotifyCastSetting);
        }

        public final int hashCode() {
            return 1481028107;
        }

        public final String toString() {
            return "SpotifyCastSetting";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TV extends Screen {
        public static final TV INSTANCE = new TV();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private TV() {
            /*
                r8 = this;
                androidx.navigation.NamedNavArgument r0 = new androidx.navigation.NamedNavArgument
                androidx.navigation.NavArgumentBuilder r1 = new androidx.navigation.NavArgumentBuilder
                r1.<init>()
                androidx.navigation.NavType$Companion$StringType$1 r3 = androidx.navigation.NavType.StringType
                androidx.navigation.NavArgument$Builder r1 = r1.builder
                r1.type = r3
                kotlin.Unit r2 = kotlin.Unit.INSTANCE
                if (r3 != 0) goto L16
                androidx.navigation.NavType$Companion r2 = androidx.navigation.NavType.Companion
                r2.getClass()
            L16:
                androidx.navigation.NavArgument r2 = new androidx.navigation.NavArgument
                boolean r4 = r1.isNullable
                boolean r7 = r1.unknownDefaultValuePresent
                r5 = 0
                r6 = 0
                r2.<init>(r3, r4, r5, r6, r7)
                java.lang.String r1 = "deviceId"
                r0.<init>(r1, r2)
                java.util.List r0 = java.util.Collections.singletonList(r0)
                r1 = 0
                java.lang.String r2 = "TV/{deviceId}"
                r8.<init>(r2, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.Screen.TV.<init>():void");
        }

        @Override // com.android.systemui.media.mediaoutput.compose.Screen
        public final String createRoute(String str) {
            return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("TV/", str);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof TV);
        }

        public final int hashCode() {
            return 845310326;
        }

        public final String toString() {
            return "TV";
        }
    }

    public /* synthetic */ Screen(String str, List list, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, list);
    }

    public String createRoute(String str) {
        return this.route;
    }

    private Screen(String str, List<NamedNavArgument> list) {
        this.route = str;
        this.navArgument = list;
    }

    public Screen(String str, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? EmptyList.INSTANCE : list, null);
    }
}
