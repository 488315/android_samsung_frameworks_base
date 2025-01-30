package com.android.systemui.volume;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import com.android.keyguard.AlphaOptimizedImageButton;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class CaptionsToggleImageButton extends AlphaOptimizedImageButton {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mCaptionsEnabled;
    public VolumeDialogImpl$$ExternalSyntheticLambda9 mConfirmedTapListener;
    public GestureDetector mGestureDetector;
    public final C35951 mGestureListener;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.volume.CaptionsToggleImageButton$1] */
    public CaptionsToggleImageButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCaptionsEnabled = false;
        this.mGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.volume.CaptionsToggleImageButton.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public final boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                CaptionsToggleImageButton captionsToggleImageButton = CaptionsToggleImageButton.this;
                int i = CaptionsToggleImageButton.$r8$clinit;
                return captionsToggleImageButton.tryToSendTapConfirmedEvent();
            }
        };
        setContentDescription(getContext().getString(R.string.volume_odi_captions_content_description));
    }

    @Override // android.widget.ImageView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        return super.onCreateDrawableState(i + 1);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        GestureDetector gestureDetector = this.mGestureDetector;
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    public final boolean tryToSendTapConfirmedEvent() {
        VolumeDialogImpl$$ExternalSyntheticLambda9 volumeDialogImpl$$ExternalSyntheticLambda9 = this.mConfirmedTapListener;
        if (volumeDialogImpl$$ExternalSyntheticLambda9 == null) {
            return false;
        }
        VolumeDialogImpl volumeDialogImpl = volumeDialogImpl$$ExternalSyntheticLambda9.f$0;
        volumeDialogImpl.mController.setCaptionsEnabled(!volumeDialogImpl.mController.areCaptionsEnabled());
        volumeDialogImpl.updateCaptionsIcon();
        Events.writeEvent(21, new Object[0]);
        return true;
    }
}
