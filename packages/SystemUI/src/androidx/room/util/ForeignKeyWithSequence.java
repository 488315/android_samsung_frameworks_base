package androidx.room.util;

/* loaded from: classes.dex */
public final class ForeignKeyWithSequence implements Comparable {
    public final String from;
    public final int id;
    public final int sequence;
    public final String to;

    public ForeignKeyWithSequence(int i, int i2, String str, String str2) {
        this.id = i;
        this.sequence = i2;
        this.from = str;
        this.to = str2;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        ForeignKeyWithSequence foreignKeyWithSequence = (ForeignKeyWithSequence) obj;
        int i = this.id - foreignKeyWithSequence.id;
        return i == 0 ? this.sequence - foreignKeyWithSequence.sequence : i;
    }
}
