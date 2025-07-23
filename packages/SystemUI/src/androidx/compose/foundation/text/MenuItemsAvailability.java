package androidx.compose.foundation.text;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MenuItemsAvailability {
    public static final Companion Companion = new Companion(null);
    public final int value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ MenuItemsAvailability(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ MenuItemsAvailability m199boximpl(int i) {
        return new MenuItemsAvailability(i);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof MenuItemsAvailability) {
            return this.value == ((MenuItemsAvailability) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("MenuItemsAvailability(value="), this.value, ')');
    }
}
