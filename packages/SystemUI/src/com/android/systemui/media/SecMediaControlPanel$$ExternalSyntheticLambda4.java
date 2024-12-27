package com.android.systemui.media;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecMediaControlPanel$$ExternalSyntheticLambda4 implements View.OnTouchListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecMediaControlPanel$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                return ((SecMediaControlPanel) obj).mBudsButtonExpanded.onTouchEvent(motionEvent);
            case 1:
                TextView textView = ((SecMediaControlPanel) obj).mViewHolder.mediaOutputText;
                if (textView == null) {
                    textView = null;
                }
                return textView.onTouchEvent(motionEvent);
            default:
                return ((ImageButton) obj).onTouchEvent(motionEvent);
        }
    }
}
