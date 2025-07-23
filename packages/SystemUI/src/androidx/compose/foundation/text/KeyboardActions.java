package androidx.compose.foundation.text;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class KeyboardActions {
    public static final Companion Companion = new Companion(null);
    public static final KeyboardActions Default = new KeyboardActions(null, null, null, null, null, null, 63, null);
    public final Function1 onDone;
    public final Function1 onGo;
    public final Function1 onNext;
    public final Function1 onPrevious;
    public final Function1 onSearch;
    public final Function1 onSend;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public KeyboardActions() {
        this(null, null, null, null, null, null, 63, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyboardActions)) {
            return false;
        }
        KeyboardActions keyboardActions = (KeyboardActions) obj;
        return this.onDone == keyboardActions.onDone && this.onGo == keyboardActions.onGo && this.onNext == keyboardActions.onNext && this.onPrevious == keyboardActions.onPrevious && this.onSearch == keyboardActions.onSearch && this.onSend == keyboardActions.onSend;
    }

    public final int hashCode() {
        Function1 function1 = this.onDone;
        int hashCode = (function1 != null ? function1.hashCode() : 0) * 31;
        Function1 function12 = this.onGo;
        int hashCode2 = (hashCode + (function12 != null ? function12.hashCode() : 0)) * 31;
        Function1 function13 = this.onNext;
        int hashCode3 = (hashCode2 + (function13 != null ? function13.hashCode() : 0)) * 31;
        Function1 function14 = this.onPrevious;
        int hashCode4 = (hashCode3 + (function14 != null ? function14.hashCode() : 0)) * 31;
        Function1 function15 = this.onSearch;
        int hashCode5 = (hashCode4 + (function15 != null ? function15.hashCode() : 0)) * 31;
        Function1 function16 = this.onSend;
        return hashCode5 + (function16 != null ? function16.hashCode() : 0);
    }

    public KeyboardActions(Function1 function1, Function1 function12, Function1 function13, Function1 function14, Function1 function15, Function1 function16) {
        this.onDone = function1;
        this.onGo = function12;
        this.onNext = function13;
        this.onPrevious = function14;
        this.onSearch = function15;
        this.onSend = function16;
    }

    public /* synthetic */ KeyboardActions(Function1 function1, Function1 function12, Function1 function13, Function1 function14, Function1 function15, Function1 function16, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : function1, (i & 2) != 0 ? null : function12, (i & 4) != 0 ? null : function13, (i & 8) != 0 ? null : function14, (i & 16) != 0 ? null : function15, (i & 32) != 0 ? null : function16);
    }
}
