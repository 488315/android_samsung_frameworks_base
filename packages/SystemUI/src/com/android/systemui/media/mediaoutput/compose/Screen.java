package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.navigation.NamedNavArgument;
import androidx.navigation.NavArgument;
import androidx.navigation.NavArgumentBuilder;
import androidx.navigation.NavType;
import androidx.navigation.NavType$Companion$StringType$1;
import java.util.Collections;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public abstract class Screen {
    public static final Companion Companion = new Companion(null);
    public final List navArgument;
    public final String route;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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

    public final class Phone extends Screen {
        public static final Phone INSTANCE = new Phone();

        private Phone() {
            NavArgumentBuilder navArgumentBuilder = new NavArgumentBuilder();
            NavType$Companion$StringType$1 navType$Companion$StringType$1 = NavType.StringType;
            NavArgument.Builder builder = navArgumentBuilder.builder;
            builder.type = navType$Companion$StringType$1;
            Unit unit = Unit.INSTANCE;
            if (navType$Companion$StringType$1 == null) {
                NavType.Companion.getClass();
            }
            super("Phone/{packageName}", Collections.singletonList(new NamedNavArgument("packageName", new NavArgument(navType$Companion$StringType$1, builder.isNullable, null, false, builder.unknownDefaultValuePresent))), null);
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

    public final class TV extends Screen {
        public static final TV INSTANCE = new TV();

        private TV() {
            NavArgumentBuilder navArgumentBuilder = new NavArgumentBuilder();
            NavType$Companion$StringType$1 navType$Companion$StringType$1 = NavType.StringType;
            NavArgument.Builder builder = navArgumentBuilder.builder;
            builder.type = navType$Companion$StringType$1;
            Unit unit = Unit.INSTANCE;
            if (navType$Companion$StringType$1 == null) {
                NavType.Companion.getClass();
            }
            super("TV/{deviceId}", Collections.singletonList(new NamedNavArgument("deviceId", new NavArgument(navType$Companion$StringType$1, builder.isNullable, null, false, builder.unknownDefaultValuePresent))), null);
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
