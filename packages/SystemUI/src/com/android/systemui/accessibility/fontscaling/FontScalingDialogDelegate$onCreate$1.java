package com.android.systemui.accessibility.fontscaling;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.common.ui.view.SeekBarWithIconButtonsView;

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
            FontScalingDialogDelegate.access$changeFontSize(this.this$0, seekBar.getProgress(), this.this$0.CHANGE_BY_BUTTON_DELAY_MS);
        } else {
            FontScalingDialogDelegate.access$changeFontSize(this.this$0, seekBar.getProgress(), this.this$0.CHANGE_BY_SEEKBAR_DELAY_MS);
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeekBar seekBar) {
    }
}
