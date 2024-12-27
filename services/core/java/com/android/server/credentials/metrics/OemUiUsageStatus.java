package com.android.server.credentials.metrics;

import android.credentials.selection.IntentCreationResult;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public enum OemUiUsageStatus {
    UNKNOWN("UNKNOWN"),
    SUCCESS("SUCCESS"),
    FAILURE_NOT_SPECIFIED("FAILURE_NOT_SPECIFIED"),
    FAILURE_SPECIFIED_BUT_NOT_FOUND("FAILURE_SPECIFIED_BUT_NOT_FOUND"),
    FAILURE_SPECIFIED_BUT_NOT_ENABLED("FAILURE_SPECIFIED_BUT_NOT_ENABLED");

    private final int mLoggingInt;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.credentials.metrics.OemUiUsageStatus$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$android$credentials$selection$IntentCreationResult$OemUiUsageStatus;

        static {
            int[] iArr = new int[IntentCreationResult.OemUiUsageStatus.values().length];
            $SwitchMap$android$credentials$selection$IntentCreationResult$OemUiUsageStatus = iArr;
            try {
                iArr[IntentCreationResult.OemUiUsageStatus.UNKNOWN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$credentials$selection$IntentCreationResult$OemUiUsageStatus[IntentCreationResult.OemUiUsageStatus.SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$credentials$selection$IntentCreationResult$OemUiUsageStatus[IntentCreationResult.OemUiUsageStatus.OEM_UI_CONFIG_NOT_SPECIFIED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$credentials$selection$IntentCreationResult$OemUiUsageStatus[IntentCreationResult.OemUiUsageStatus.OEM_UI_CONFIG_SPECIFIED_BUT_NOT_FOUND.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$credentials$selection$IntentCreationResult$OemUiUsageStatus[IntentCreationResult.OemUiUsageStatus.OEM_UI_CONFIG_SPECIFIED_FOUND_BUT_NOT_ENABLED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    OemUiUsageStatus(String str) {
        this.mLoggingInt = r2;
    }

    public final int getLoggingInt() {
        return this.mLoggingInt;
    }
}
