package androidx.compose.foundation.text;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public final class Handle {
    public static final /* synthetic */ Handle[] $VALUES;
    public static final Handle Cursor;
    public static final Handle SelectionEnd;
    public static final Handle SelectionStart;

    static {
        Handle handle = new Handle("Cursor", 0);
        Cursor = handle;
        Handle handle2 = new Handle("SelectionStart", 1);
        SelectionStart = handle2;
        Handle handle3 = new Handle("SelectionEnd", 2);
        SelectionEnd = handle3;
        Handle[] handleArr = {handle, handle2, handle3};
        $VALUES = handleArr;
        EnumEntriesKt.enumEntries(handleArr);
    }

    private Handle(String str, int i) {
    }

    public static Handle valueOf(String str) {
        return (Handle) Enum.valueOf(Handle.class, str);
    }

    public static Handle[] values() {
        return (Handle[]) $VALUES.clone();
    }
}
