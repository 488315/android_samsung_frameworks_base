package com.android.systemui.qs.pipeline.shared;

import android.content.ComponentName;
import android.text.TextUtils;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class TileSpec {
    public static final Companion Companion = new Companion(null);
    public final String spec;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static TileSpec create(String str) {
            if (TextUtils.isEmpty(str)) {
                return Invalid.INSTANCE;
            }
            if (!str.startsWith("custom(")) {
                return new PlatformTileSpec(str);
            }
            ComponentName unflattenFromString = (str.startsWith("custom(") && str.endsWith(")")) ? ComponentName.unflattenFromString(str.substring(7, str.length() - 1)) : null;
            return unflattenFromString != null ? new CustomTileSpec(str, unflattenFromString) : Invalid.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            return "CustomTileSpec(spec=" + this.spec + ", componentName=" + this.componentName + ")";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Invalid extends TileSpec {
        public static final Invalid INSTANCE = new Invalid();

        private Invalid() {
            super("", null);
        }

        public final String toString() {
            return "TileSpec.INVALID";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            if (obj instanceof PlatformTileSpec) {
                return Intrinsics.areEqual(this.spec, ((PlatformTileSpec) obj).spec);
            }
            return false;
        }

        @Override // com.android.systemui.qs.pipeline.shared.TileSpec
        public final String getSpec() {
            return this.spec;
        }

        public final int hashCode() {
            return this.spec.hashCode();
        }

        public final String toString() {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("PlatformTileSpec(spec="), this.spec, ")");
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
