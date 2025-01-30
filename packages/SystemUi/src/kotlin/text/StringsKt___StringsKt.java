package kotlin.text;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class StringsKt___StringsKt extends StringsKt___StringsJvmKt {
    public static final String take(String str) {
        int length = str.length();
        if (500 <= length) {
            length = 500;
        }
        return str.substring(0, length);
    }
}
