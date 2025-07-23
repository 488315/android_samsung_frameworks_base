package com.android.systemui.common.shared.model;

import android.content.Context;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ContentDescription {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static String loadContentDescription(ContentDescription contentDescription, Context context) {
            if (contentDescription == null) {
                return null;
            }
            if (contentDescription instanceof Loaded) {
                return ((Loaded) contentDescription).description;
            }
            if (contentDescription instanceof Resource) {
                return context.getString(((Resource) contentDescription).res);
            }
            throw new NoWhenBranchMatchedException();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Loaded extends ContentDescription {
        public final String description;

        public Loaded(String str) {
            super(null);
            this.description = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Loaded) && Intrinsics.areEqual(this.description, ((Loaded) obj).description);
        }

        public final int hashCode() {
            String str = this.description;
            if (str == null) {
                return 0;
            }
            return str.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("Loaded(description="), this.description, ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Resource extends ContentDescription {
        public final int res;

        public Resource(int i) {
            super(null);
            this.res = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Resource) && this.res == ((Resource) obj).res;
        }

        public final int hashCode() {
            return Integer.hashCode(this.res);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.res, ")", new StringBuilder("Resource(res="));
        }
    }

    public /* synthetic */ ContentDescription(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ContentDescription() {
    }
}
