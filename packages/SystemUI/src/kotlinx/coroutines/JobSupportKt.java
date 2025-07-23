package kotlinx.coroutines;

import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class JobSupportKt {
    public static final Symbol COMPLETING_ALREADY = new Symbol("COMPLETING_ALREADY");
    public static final Symbol COMPLETING_WAITING_CHILDREN = new Symbol("COMPLETING_WAITING_CHILDREN");
    public static final Symbol COMPLETING_RETRY = new Symbol("COMPLETING_RETRY");
    public static final Symbol TOO_LATE_TO_CANCEL = new Symbol("TOO_LATE_TO_CANCEL");
    public static final Symbol SEALED = new Symbol("SEALED");
    public static final Empty EMPTY_NEW = new Empty(false);
    public static final Empty EMPTY_ACTIVE = new Empty(true);

    public static final Object unboxState(Object obj) {
        Incomplete incomplete;
        IncompleteStateBox incompleteStateBox = obj instanceof IncompleteStateBox ? (IncompleteStateBox) obj : null;
        return (incompleteStateBox == null || (incomplete = incompleteStateBox.state) == null) ? obj : incomplete;
    }
}
