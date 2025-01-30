package kotlinx.coroutines.selects;

import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class SelectKt {
    public static final Symbol NOT_SELECTED = new Symbol("NOT_SELECTED");
    public static final Symbol ALREADY_SELECTED = new Symbol("ALREADY_SELECTED");
    public static final Symbol UNDECIDED = new Symbol("UNDECIDED");
    public static final Symbol RESUMED = new Symbol("RESUMED");
    public static final SeqNumber selectOpSequenceNumber = new SeqNumber();
}
