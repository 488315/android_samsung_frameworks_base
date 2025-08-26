package com.google.android.setupdesign.view;

import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

/* loaded from: classes4.dex */
public interface TouchableMovementMethod {

    public class TouchableLinkMovementMethod extends LinkMovementMethod implements TouchableMovementMethod {
        public MotionEvent lastEvent;
        public boolean lastEventResult = false;

        @Override // android.text.method.LinkMovementMethod, android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod, android.text.method.MovementMethod
        public final boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            this.lastEvent = motionEvent;
            boolean zOnTouchEvent = super.onTouchEvent(textView, spannable, motionEvent);
            if (motionEvent.getAction() == 0) {
                this.lastEventResult = Selection.getSelectionStart(spannable) != -1;
                return zOnTouchEvent;
            }
            this.lastEventResult = zOnTouchEvent;
            return zOnTouchEvent;
        }
    }
}
