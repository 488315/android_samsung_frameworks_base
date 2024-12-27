package com.android.systemui.media.mediaoutput.compose;

import androidx.navigation.NamedNavArgument;
import androidx.navigation.NamedNavArgumentKt;
import androidx.navigation.NavArgumentBuilder;
import androidx.navigation.NavType;
import java.util.Collections;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class Screen {
    public final List navArgument;
    public final String route;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Phone extends Screen {
        public static final Phone INSTANCE = new Phone();

        private Phone() {
            super("Phone/{packageName}", Collections.singletonList(NamedNavArgumentKt.navArgument(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.Screen.Phone.1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    ((NavArgumentBuilder) obj).builder.type = NavType.StringType;
                    return Unit.INSTANCE;
                }
            }, "packageName")), null);
        }

        @Override // com.android.systemui.media.mediaoutput.compose.Screen
        public final String createRoute(String str) {
            return "Phone/".concat(str);
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TV extends Screen {
        public static final TV INSTANCE = new TV();

        private TV() {
            super("TV/{deviceId}", Collections.singletonList(NamedNavArgumentKt.navArgument(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.Screen.TV.1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    ((NavArgumentBuilder) obj).builder.type = NavType.StringType;
                    return Unit.INSTANCE;
                }
            }, "deviceId")), null);
        }

        @Override // com.android.systemui.media.mediaoutput.compose.Screen
        public final String createRoute(String str) {
            return "TV/".concat(str);
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

    static {
        new Companion(null);
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
