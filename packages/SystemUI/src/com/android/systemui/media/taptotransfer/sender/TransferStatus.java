package com.android.systemui.media.taptotransfer.sender;

import android.os.VibrationEffect;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TransferStatus {
    public static final /* synthetic */ TransferStatus[] $VALUES;
    public static final TransferStatus FAILED;
    public static final TransferStatus IN_PROGRESS;
    public static final TransferStatus NOT_STARTED;
    public static final TransferStatus SUCCEEDED;
    public static final TransferStatus TOO_FAR;
    private final VibrationEffect vibrationEffect;

    static {
        TransferStatus transferStatus = new TransferStatus("NOT_STARTED", 0, VibrationEffect.startComposition().addPrimitive(1, 1.0f, 0).compose());
        NOT_STARTED = transferStatus;
        TransferStatus transferStatus2 = new TransferStatus("IN_PROGRESS", 1, VibrationEffect.startComposition().addPrimitive(4, 1.0f, 0).addPrimitive(1, 0.7f, 70).compose());
        IN_PROGRESS = transferStatus2;
        TransferStatus transferStatus3 = new TransferStatus("SUCCEEDED", 2, null, 1, null);
        SUCCEEDED = transferStatus3;
        TransferStatus transferStatus4 = new TransferStatus("FAILED", 3, VibrationEffect.get(1));
        FAILED = transferStatus4;
        TransferStatus transferStatus5 = new TransferStatus("TOO_FAR", 4, 0 == true ? 1 : 0, 1, null);
        TOO_FAR = transferStatus5;
        TransferStatus[] transferStatusArr = {transferStatus, transferStatus2, transferStatus3, transferStatus4, transferStatus5};
        $VALUES = transferStatusArr;
        EnumEntriesKt.enumEntries(transferStatusArr);
    }

    private TransferStatus(String str, int i, VibrationEffect vibrationEffect) {
        this.vibrationEffect = vibrationEffect;
    }

    public static TransferStatus valueOf(String str) {
        return (TransferStatus) Enum.valueOf(TransferStatus.class, str);
    }

    public static TransferStatus[] values() {
        return (TransferStatus[]) $VALUES.clone();
    }

    public final VibrationEffect getVibrationEffect() {
        return this.vibrationEffect;
    }

    public /* synthetic */ TransferStatus(String str, int i, VibrationEffect vibrationEffect, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, (i2 & 1) != 0 ? null : vibrationEffect);
    }
}
