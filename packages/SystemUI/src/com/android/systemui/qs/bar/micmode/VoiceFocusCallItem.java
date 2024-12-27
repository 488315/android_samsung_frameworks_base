package com.android.systemui.qs.bar.micmode;

import com.android.systemui.qs.bar.micmode.MicModeDetailItems;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class VoiceFocusCallItem extends MicModeDetailItems.Item {
    public final String loggingId;
    public final String loggingValue;
    public final int micMode;
    public final String text;

    public /* synthetic */ VoiceFocusCallItem(String str, int i, String str2, String str3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? 4 : i, (i2 & 4) != 0 ? SystemUIAnalytics.STID_MIC_MODE_EFFECT_CP_CALL : str2, (i2 & 8) != 0 ? SystemUIAnalytics.ST_VALUE_MIC_MODE_VOICE_FOCUS : str3);
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

    public VoiceFocusCallItem(String str, int i, String str2, String str3) {
        this.text = str;
        this.micMode = i;
        this.loggingId = str2;
        this.loggingValue = str3;
    }
}
