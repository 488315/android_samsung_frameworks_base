package com.android.systemui.qs.bar.micmode;

import com.android.systemui.qs.bar.micmode.MicModeDetailItems;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StandardVoipItem extends MicModeDetailItems.Item {
    public final String loggingId;
    public final String loggingValue;
    public final int micMode;
    public final String text;

    public /* synthetic */ StandardVoipItem(String str, int i, String str2, String str3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? 0 : i, (i2 & 4) != 0 ? "ASMM1030" : str2, (i2 & 8) != 0 ? "STANDARD" : str3);
    }

    @Override // com.android.systemui.qs.bar.micmode.MicModeDetailItems.Item
    public final String getLoggingId() {
        return this.loggingId;
    }

    @Override // com.android.systemui.qs.bar.micmode.MicModeDetailItems.Item
    public final String getLoggingValue() {
        return this.loggingValue;
    }

    @Override // com.android.systemui.qs.bar.micmode.MicModeDetailItems.Item
    public final int getMicMode() {
        return this.micMode;
    }

    @Override // com.android.systemui.qs.bar.micmode.MicModeDetailItems.Item
    public final String getText() {
        return this.text;
    }

    public StandardVoipItem(String str, int i, String str2, String str3) {
        this.text = str;
        this.micMode = i;
        this.loggingId = str2;
        this.loggingValue = str3;
    }
}
