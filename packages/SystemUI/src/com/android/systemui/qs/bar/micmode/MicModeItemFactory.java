package com.android.systemui.qs.bar.micmode;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.qs.bar.micmode.MicModeDetailItems;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MicModeItemFactory {
    static {
        new MicModeItemFactory();
    }

    private MicModeItemFactory() {
    }

    public static final MicModeDetailItems.Item create(int i, Context context) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? new StandardVoipItem(context.getString(R.string.sec_qs_mic_mode_standard), 0, null, null, 14, null) : new VoiceFocusCallItem(context.getString(R.string.sec_qs_mic_mode_voice_focus), 0, null, null, 14, null) : new StandardCallItem(context.getString(R.string.sec_qs_mic_mode_standard), 0, null, null, 14, null) : new FullSpectrumVoipItem(context.getString(R.string.sec_qs_mic_mode_full_spectrum), 0, null, null, 14, null) : new VoiceFocusVoipItem(context.getString(R.string.sec_qs_mic_mode_voice_focus), 0, null, null, 14, null);
    }
}
