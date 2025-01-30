package androidx.leanback.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ActionMode;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import com.android.systemui.R;
import java.util.Random;
import java.util.regex.Pattern;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
class StreamingTextView extends EditText {
    public static final Pattern SPLIT_PATTERN = Pattern.compile("\\S+");
    public static final C02941 STREAM_POSITION_PROPERTY = new Property(Integer.class, "streamPosition") { // from class: androidx.leanback.widget.StreamingTextView.1
        @Override // android.util.Property
        public final Object get(Object obj) {
            return Integer.valueOf(((StreamingTextView) obj).mStreamPosition);
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            StreamingTextView streamingTextView = (StreamingTextView) obj;
            streamingTextView.mStreamPosition = ((Integer) obj2).intValue();
            streamingTextView.invalidate();
        }
    };
    public Bitmap mOneDot;
    public final Random mRandom;
    public int mStreamPosition;
    public ObjectAnimator mStreamingAnimation;
    public Bitmap mTwoDot;

    public StreamingTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRandom = new Random();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mOneDot = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lb_text_dot_one), (int) (r0.getWidth() * 1.3f), (int) (r0.getHeight() * 1.3f), false);
        this.mTwoDot = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lb_text_dot_two), (int) (r0.getWidth() * 1.3f), (int) (r0.getHeight() * 1.3f), false);
        this.mStreamPosition = -1;
        ObjectAnimator objectAnimator = this.mStreamingAnimation;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        setText("");
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("androidx.leanback.widget.StreamingTextView");
    }

    @Override // android.widget.TextView
    public final void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(callback);
    }

    public StreamingTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRandom = new Random();
    }
}
