package com.android.systemui.accessibility.fontscaling;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.common.ui.view.SeekBarWithIconButtonsView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FontScalingDialogDelegate$onCreate$1 implements SeekBarWithIconButtonsView.OnSeekBarWithIconButtonsChangeListener {
    public final /* synthetic */ FontScalingDialogDelegate this$0;

    public FontScalingDialogDelegate$onCreate$1(FontScalingDialogDelegate fontScalingDialogDelegate) {
        this.this$0 = fontScalingDialogDelegate;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        FontScalingDialogDelegate fontScalingDialogDelegate = this.this$0;
        fontScalingDialogDelegate.getClass();
        Configuration configuration = new Configuration(fontScalingDialogDelegate.configuration);
        configuration.fontScale = Float.parseFloat(fontScalingDialogDelegate.strEntryValues[i]);
        Context createConfigurationContext = fontScalingDialogDelegate.context.createConfigurationContext(configuration);
        createConfigurationContext.getTheme().setTo(fontScalingDialogDelegate.context.getTheme());
        TextView textView = fontScalingDialogDelegate.title;
        if (textView == null) {
            textView = null;
        }
        textView.setTextSize(0, createConfigurationContext.getResources().getDimension(R.dimen.dialog_title_text_size));
    }

    public final void onUserInteractionFinalized(SeekBar seekBar, int i) {
        if (i == 1) {
            FontScalingDialogDelegate fontScalingDialogDelegate = this.this$0;
            int progress = seekBar.getProgress();
            this.this$0.getClass();
            FontScalingDialogDelegate.access$changeFontSize(fontScalingDialogDelegate, progress, 300L);
            return;
        }
        FontScalingDialogDelegate fontScalingDialogDelegate2 = this.this$0;
        int progress2 = seekBar.getProgress();
        this.this$0.getClass();
        FontScalingDialogDelegate.access$changeFontSize(fontScalingDialogDelegate2, progress2, 100L);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeekBar seekBar) {
    }
}
