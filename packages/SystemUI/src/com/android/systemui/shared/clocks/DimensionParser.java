package com.android.systemui.shared.clocks;

import android.content.Context;
import java.util.regex.Pattern;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class DimensionParser {

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        Pattern.compile("(\\d+(\\.\\d+)?)([a-z]+)");
        MapsKt__MapsKt.mapOf(new Pair("dp", 1), new Pair("dip", 1), new Pair("sp", 2), new Pair("px", 0), new Pair("pt", 3), new Pair("mm", 5), new Pair("in", 4));
    }

    public DimensionParser(Context context) {
    }
}
