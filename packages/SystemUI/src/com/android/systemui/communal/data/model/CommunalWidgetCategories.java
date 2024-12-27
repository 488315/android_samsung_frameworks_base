package com.android.systemui.communal.data.model;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class CommunalWidgetCategories {
    public static final Companion Companion = new Companion(null);
    public final int categories;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private /* synthetic */ CommunalWidgetCategories(int i) {
        this.categories = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ CommunalWidgetCategories m940boximpl(int i) {
        return new CommunalWidgetCategories(i);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof CommunalWidgetCategories) {
            return this.categories == ((CommunalWidgetCategories) obj).categories;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.categories);
    }

    public final String toString() {
        return Anchor$$ExternalSyntheticOutline0.m(this.categories, ")", new StringBuilder("CommunalWidgetCategories(categories="));
    }
}
