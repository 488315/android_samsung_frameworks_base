package com.android.server.credentials.metrics;

import android.util.Slog;

import java.util.AbstractMap;
import java.util.Map;

public enum EntryEnum {
    UNKNOWN("UNKNOWN"),
    ACTION_ENTRY("ACTION_ENTRY"),
    CREDENTIAL_ENTRY("CREDENTIAL_ENTRY"),
    REMOTE_ENTRY("REMOTE_ENTRY"),
    AUTHENTICATION_ENTRY("AUTHENTICATION_ENTRY");

    public static final Map sKeyToEntryCode;
    private final int mInnerMetricCode;

    static {
        EntryEnum entryEnum = ACTION_ENTRY;
        EntryEnum entryEnum2 = CREDENTIAL_ENTRY;
        EntryEnum entryEnum3 = REMOTE_ENTRY;
        sKeyToEntryCode =
                Map.ofEntries(
                        new AbstractMap.SimpleEntry(
                                "action_key", Integer.valueOf(entryEnum.mInnerMetricCode)),
                        new AbstractMap.SimpleEntry(
                                "authentication_action_key",
                                Integer.valueOf(AUTHENTICATION_ENTRY.mInnerMetricCode)),
                        new AbstractMap.SimpleEntry(
                                "remote_entry_key", Integer.valueOf(entryEnum3.mInnerMetricCode)),
                        new AbstractMap.SimpleEntry(
                                "credential_key", Integer.valueOf(entryEnum2.mInnerMetricCode)),
                        new AbstractMap.SimpleEntry(
                                "save_entry_key", Integer.valueOf(entryEnum2.mInnerMetricCode)));
    }

    EntryEnum(String str) {
        this.mInnerMetricCode = r2;
    }

    public static int getMetricCodeFromString(String str) {
        Map map = sKeyToEntryCode;
        if (map.containsKey(str)) {
            return ((Integer) map.get(str)).intValue();
        }
        Slog.i("EntryEnum", "Attempted to use an unsupported string key entry type");
        return UNKNOWN.mInnerMetricCode;
    }

    public final int getMetricCode() {
        return this.mInnerMetricCode;
    }
}
