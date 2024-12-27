package com.android.systemui.tuner;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class ClipboardView extends ImageView implements ClipboardManager.OnPrimaryClipChangedListener {
    public final ClipboardManager mClipboardManager;
    public ClipData mCurrentClip;

    public ClipboardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mClipboardManager = (ClipboardManager) context.getSystemService(ClipboardManager.class);
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mClipboardManager.addPrimaryClipChangedListener(this);
        onPrimaryClipChanged();
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mClipboardManager.removePrimaryClipChangedListener(this);
    }

    @Override // android.view.View
    public final boolean onDragEvent(DragEvent dragEvent) {
        int action = dragEvent.getAction();
        if (action == 3) {
            this.mClipboardManager.setPrimaryClip(dragEvent.getClipData());
        } else if (action != 4) {
            if (action == 5) {
                setBackgroundColor(1308622847);
                return true;
            }
            if (action != 6) {
                return true;
            }
        }
        setBackgroundColor(0);
        return true;
    }

    @Override // android.content.ClipboardManager.OnPrimaryClipChangedListener
    public final void onPrimaryClipChanged() {
        ClipData primaryClip = this.mClipboardManager.getPrimaryClip();
        this.mCurrentClip = primaryClip;
        setImageResource(primaryClip != null ? R.drawable.clipboard_full : R.drawable.clipboard_empty);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        ClipData clipData;
        if (motionEvent.getActionMasked() == 0 && (clipData = this.mCurrentClip) != null) {
            startDragAndDrop(clipData, new View.DragShadowBuilder(this), null, 256);
        }
        return super.onTouchEvent(motionEvent);
    }
}
