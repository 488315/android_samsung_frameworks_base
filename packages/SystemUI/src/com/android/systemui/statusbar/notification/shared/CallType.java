package com.android.systemui.statusbar.notification.shared;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public final class CallType {
    public static final /* synthetic */ CallType[] $VALUES;
    public static final CallType Incoming;
    public static final CallType None;
    public static final CallType Ongoing;
    public static final CallType Screening;
    public static final CallType Unknown;

    static {
        CallType callType = new CallType("None", 0);
        None = callType;
        CallType callType2 = new CallType("Incoming", 1);
        Incoming = callType2;
        CallType callType3 = new CallType("Ongoing", 2);
        Ongoing = callType3;
        CallType callType4 = new CallType("Screening", 3);
        Screening = callType4;
        CallType callType5 = new CallType(C2paManifestList.UNKNOWN_VALUE, 4);
        Unknown = callType5;
        CallType[] callTypeArr = {callType, callType2, callType3, callType4, callType5};
        $VALUES = callTypeArr;
        EnumEntriesKt.enumEntries(callTypeArr);
    }

    private CallType(String str, int i) {
    }

    public static CallType valueOf(String str) {
        return (CallType) Enum.valueOf(CallType.class, str);
    }

    public static CallType[] values() {
        return (CallType[]) $VALUES.clone();
    }
}
