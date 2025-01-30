package com.android.server.credentials.metrics;

import android.util.Slog;
import java.util.AbstractMap;
import java.util.Map;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v1 com.android.server.credentials.metrics.EntryEnum, still in use, count: 1, list:
  (r0v1 com.android.server.credentials.metrics.EntryEnum) from 0x003a: IGET (r0v1 com.android.server.credentials.metrics.EntryEnum) A[WRAPPED] com.android.server.credentials.metrics.EntryEnum.mInnerMetricCode int
	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:252)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:180)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes.dex */
public final class EntryEnum {
    UNKNOWN(0),
    ACTION_ENTRY(1),
    CREDENTIAL_ENTRY(2),
    REMOTE_ENTRY(3),
    AUTHENTICATION_ENTRY(4);

    public static final Map sKeyToEntryCode;
    private final int mInnerMetricCode;

    public static EntryEnum valueOf(String str) {
        return (EntryEnum) Enum.valueOf(EntryEnum.class, str);
    }

    public static EntryEnum[] values() {
        return (EntryEnum[]) $VALUES.clone();
    }

    static {
        EntryEnum entryEnum = CREDENTIAL_ENTRY;
        sKeyToEntryCode = Map.ofEntries(new AbstractMap.SimpleEntry("action_key", Integer.valueOf(new EntryEnum(1).mInnerMetricCode)), new AbstractMap.SimpleEntry("authentication_action_key", Integer.valueOf(r3.mInnerMetricCode)), new AbstractMap.SimpleEntry("remote_entry_key", Integer.valueOf(new EntryEnum(3).mInnerMetricCode)), new AbstractMap.SimpleEntry("credential_key", Integer.valueOf(entryEnum.mInnerMetricCode)), new AbstractMap.SimpleEntry("save_entry_key", Integer.valueOf(entryEnum.mInnerMetricCode)));
    }

    public EntryEnum(int i) {
        this.mInnerMetricCode = i;
    }

    public int getMetricCode() {
        return this.mInnerMetricCode;
    }

    public static int getMetricCodeFromString(String str) {
        Map map = sKeyToEntryCode;
        if (!map.containsKey(str)) {
            Slog.i("EntryEnum", "Attempted to use an unsupported string key entry type");
            return UNKNOWN.mInnerMetricCode;
        }
        return ((Integer) map.get(str)).intValue();
    }
}
