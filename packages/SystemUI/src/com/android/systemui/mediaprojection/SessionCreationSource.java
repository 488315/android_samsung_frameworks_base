package com.android.systemui.mediaprojection;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SessionCreationSource {
    public static final /* synthetic */ SessionCreationSource[] $VALUES;
    public static final SessionCreationSource APP;
    public static final SessionCreationSource CAST;
    public static final SessionCreationSource SYSTEM_UI_SCREEN_RECORDER;
    public static final SessionCreationSource UNKNOWN;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SessionCreationSource.values().length];
            try {
                iArr[SessionCreationSource.APP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SessionCreationSource.CAST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SessionCreationSource.SYSTEM_UI_SCREEN_RECORDER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SessionCreationSource.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        SessionCreationSource sessionCreationSource = new SessionCreationSource("APP", 0);
        APP = sessionCreationSource;
        SessionCreationSource sessionCreationSource2 = new SessionCreationSource("CAST", 1);
        CAST = sessionCreationSource2;
        SessionCreationSource sessionCreationSource3 = new SessionCreationSource("SYSTEM_UI_SCREEN_RECORDER", 2);
        SYSTEM_UI_SCREEN_RECORDER = sessionCreationSource3;
        SessionCreationSource sessionCreationSource4 = new SessionCreationSource("UNKNOWN", 3);
        UNKNOWN = sessionCreationSource4;
        SessionCreationSource[] sessionCreationSourceArr = {sessionCreationSource, sessionCreationSource2, sessionCreationSource3, sessionCreationSource4};
        $VALUES = sessionCreationSourceArr;
        EnumEntriesKt.enumEntries(sessionCreationSourceArr);
    }

    private SessionCreationSource(String str, int i) {
    }

    public static SessionCreationSource valueOf(String str) {
        return (SessionCreationSource) Enum.valueOf(SessionCreationSource.class, str);
    }

    public static SessionCreationSource[] values() {
        return (SessionCreationSource[]) $VALUES.clone();
    }
}
