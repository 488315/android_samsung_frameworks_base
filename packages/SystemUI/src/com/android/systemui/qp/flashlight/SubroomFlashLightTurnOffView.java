package com.android.systemui.qp.flashlight;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;

/* loaded from: classes2.dex */
public class SubroomFlashLightTurnOffView extends LinearLayout {
    public SubroomFlashLightSettingsActivity mListener;

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
        ((Button) findViewById(R.id.turn_off_view)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qp.flashlight.SubroomFlashLightTurnOffView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubroomFlashLightSettingsActivity subroomFlashLightSettingsActivity = this.f$0.mListener;
                ((FlashlightControllerImpl) subroomFlashLightSettingsActivity.mFlashlightController).setFlashlight(false);
                Log.d("SubroomFlashLightSettingsActivity", "onTurnOffClick: ");
                subroomFlashLightSettingsActivity.finishFlashLightActivity();
            }
        });
    }
}
