package com.android.systemui.qp.flashlight;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SubroomFlashLightTurnOffView extends LinearLayout {
    public ClickListener mListener;
    public Button mTurnOffView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ClickListener {
    }

    public SubroomFlashLightTurnOffView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        Button button = (Button) findViewById(R.id.turn_off_view);
        this.mTurnOffView = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qp.flashlight.SubroomFlashLightTurnOffView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubroomFlashLightTurnOffView subroomFlashLightTurnOffView = SubroomFlashLightTurnOffView.this;
                SubroomFlashLightSettingsActivity subroomFlashLightSettingsActivity = (SubroomFlashLightSettingsActivity) subroomFlashLightTurnOffView.mListener;
                ((FlashlightControllerImpl) subroomFlashLightSettingsActivity.mFlashlightController).setFlashlight(false);
                Log.d("SubroomFlashLightSettingsActivity", "onTurnOffClick: ");
                subroomFlashLightSettingsActivity.finishFlashLightActivity();
                subroomFlashLightTurnOffView.mTurnOffView.announceForAccessibility(subroomFlashLightTurnOffView.getResources().getString(R.string.accessibility_quick_settings_flashlight_off));
            }
        });
    }
}
