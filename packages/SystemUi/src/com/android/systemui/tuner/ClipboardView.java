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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
