package com.android.systemui.qs.bar.micmode;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.qs.bar.micmode.MicModeDetailItems;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
