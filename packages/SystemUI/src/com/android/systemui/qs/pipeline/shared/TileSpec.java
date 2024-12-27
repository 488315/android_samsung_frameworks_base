package com.android.systemui.qs.pipeline.shared;

import android.content.ComponentName;
import android.text.TextUtils;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public abstract class TileSpec {
    public static final Companion Companion = new Companion(null);
    public final String spec;

    public final class Companion {
        private Companion() {
        }

        public static TileSpec create(String str) {
            TileSpec customTileSpec;
            if (TextUtils.isEmpty(str)) {
                return Invalid.INSTANCE;
            }
            if (str.startsWith("custom(")) {
                ComponentName componentName = null;
                if (str.startsWith("custom(") && str.endsWith(")")) {
                    componentName = ComponentName.unflattenFromString(str.substring(7, str.length() - 1));
                }
                if (componentName == null) {
                    return Invalid.INSTANCE;
                }
                customTileSpec = new CustomTileSpec(str, componentName);
            } else {
                customTileSpec = new PlatformTileSpec(str);
            }
            return customTileSpec;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class CustomTileSpec extends TileSpec {
        public final ComponentName componentName;
        public final String spec;

        public CustomTileSpec(String str, ComponentName componentName) {
            super(str, null);
            this.spec = str;
            this.componentName = componentName;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CustomTileSpec)) {
                return false;
            }
            CustomTileSpec customTileSpec = (CustomTileSpec) obj;
            return Intrinsics.areEqual(this.spec, customTileSpec.spec) && Intrinsics.areEqual(this.componentName, customTileSpec.componentName);
        }

        @Override // com.android.systemui.qs.pipeline.shared.TileSpec
        public final String getSpec() {
            return this.spec;
        }

        public final int hashCode() {
            return this.componentName.hashCode() + (this.spec.hashCode() * 31);
        }

        public final String toString() {
            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("C(", this.componentName.flattenToShortString(), ")");
        }
    }

    public final class Invalid extends TileSpec {
        public static final Invalid INSTANCE = new Invalid();

        private Invalid() {
            super("", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Invalid);
        }

        public final int hashCode() {
            return 1236272636;
        }

        public final String toString() {
            return "Invalid";
        }
    }

    public final class PlatformTileSpec extends TileSpec {
        public final String spec;

        public PlatformTileSpec(String str) {
            super(str, null);
            this.spec = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof PlatformTileSpec) && Intrinsics.areEqual(this.spec, ((PlatformTileSpec) obj).spec);
        }

        @Override // com.android.systemui.qs.pipeline.shared.TileSpec
        public final String getSpec() {
            return this.spec;
        }

        public final int hashCode() {
            return this.spec.hashCode();
        }

        public final String toString() {
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("P("), this.spec, ")");
        }
    }

    public /* synthetic */ TileSpec(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    public String getSpec() {
        return this.spec;
    }

    private TileSpec(String str) {
        this.spec = str;
    }
}
