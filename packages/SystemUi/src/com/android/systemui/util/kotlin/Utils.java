package com.android.systemui.util.kotlin;

import kotlin.Pair;
import kotlin.Triple;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Utils {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static Quad toQuad(Object obj, Triple triple) {
            return new Quad(obj, triple.getFirst(), triple.getSecond(), triple.getThird());
        }

        public static Triple toTriple(Object obj, Pair pair) {
            return new Triple(obj, pair.getFirst(), pair.getSecond());
        }
    }
}
