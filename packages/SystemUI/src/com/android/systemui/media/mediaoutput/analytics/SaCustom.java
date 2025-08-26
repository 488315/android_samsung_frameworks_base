package com.android.systemui.media.mediaoutput.analytics;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.scab.CABContract;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class SaCustom {
    public final String key;
    public final String value;

    public final class Action extends SaCustom {
        public final String arg;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
        }

        public Action(String str) {
            super("action", str, null);
            this.arg = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Action) && Intrinsics.areEqual(this.arg, ((Action) obj).arg);
        }

        public final int hashCode() {
            return this.arg.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("Action(arg="), this.arg, ")");
        }
    }

    public final class App extends SaCustom {
        public final String arg;

        public App(String str) {
            super(SystemUIAnalytics.QPNE_KEY_APP, str, null);
            this.arg = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof App) && Intrinsics.areEqual(this.arg, ((App) obj).arg);
        }

        public final int hashCode() {
            return this.arg.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("App(arg="), this.arg, ")");
        }
    }

    public final class From extends SaCustom {
        public final String arg;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
        }

        public From(String str) {
            super("from", str, null);
            this.arg = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof From) && Intrinsics.areEqual(this.arg, ((From) obj).arg);
        }

        public final int hashCode() {
            return this.arg.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("From(arg="), this.arg, ")");
        }
    }

    public final class Lock extends SaCustom {
        public final String arg;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
        }

        public Lock(String str) {
            super("lock", str, null);
            this.arg = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Lock) && Intrinsics.areEqual(this.arg, ((Lock) obj).arg);
        }

        public final int hashCode() {
            return this.arg.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("Lock(arg="), this.arg, ")");
        }
    }

    public final class Name extends SaCustom {
        public final String arg;

        public Name(String str) {
            super("name", str, null);
            this.arg = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Name) && Intrinsics.areEqual(this.arg, ((Name) obj).arg);
        }

        public final int hashCode() {
            return this.arg.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("Name(arg="), this.arg, ")");
        }
    }

    public final class Number extends SaCustom {
        public final int arg;

        public Number(int i) {
            super(CABContract.CABBusinessContactPhone.NUMBER, String.valueOf(i), null);
            this.arg = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Number) && this.arg == ((Number) obj).arg;
        }

        public final int hashCode() {
            return Integer.hashCode(this.arg);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.arg, ")", new StringBuilder("Number(arg="));
        }
    }

    public final class Type extends SaCustom {
        public final String arg;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
        }

        public Type(String str) {
            super("type", str, null);
            this.arg = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Type) && Intrinsics.areEqual(this.arg, ((Type) obj).arg);
        }

        public final int hashCode() {
            return this.arg.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("Type(arg="), this.arg, ")");
        }
    }

    public final class Value extends SaCustom {
        public final String arg;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
        }

        public Value(String str) {
            super("value", str, null);
            this.arg = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Value) && Intrinsics.areEqual(this.arg, ((Value) obj).arg);
        }

        public final int hashCode() {
            return this.arg.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("Value(arg="), this.arg, ")");
        }
    }

    public /* synthetic */ SaCustom(String str, String str2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2);
    }

    private SaCustom(String str, String str2) {
        this.key = str;
        this.value = str2;
    }
}
