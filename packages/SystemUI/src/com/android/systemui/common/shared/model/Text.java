package com.android.systemui.common.shared.model;

import android.content.Context;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class Text {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static String loadText(Text text, Context context) {
            if (text == null) {
                return null;
            }
            if (text instanceof Loaded) {
                return ((Loaded) text).text;
            }
            if (text instanceof Resource) {
                return context.getString(((Resource) text).res);
            }
            throw new NoWhenBranchMatchedException();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Loaded extends Text {
        public final String text;

        public Loaded(String str) {
            super(null);
            this.text = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Loaded) && Intrinsics.areEqual(this.text, ((Loaded) obj).text);
        }

        public final int hashCode() {
            String str = this.text;
            if (str == null) {
                return 0;
            }
            return str.hashCode();
        }

        public final String toString() {
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("Loaded(text="), this.text, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Resource extends Text {
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
            return Anchor$$ExternalSyntheticOutline0.m(this.res, ")", new StringBuilder("Resource(res="));
        }
    }

    private Text() {
    }

    public /* synthetic */ Text(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
