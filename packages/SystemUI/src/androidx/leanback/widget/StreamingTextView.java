package androidx.leanback.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Property;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import com.android.systemui.R;
import java.util.Random;
import java.util.regex.Pattern;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class StreamingTextView extends EditText {
    public static final Pattern SPLIT_PATTERN = null;
    public static final AnonymousClass1 STREAM_POSITION_PROPERTY = null;
    public Bitmap mOneDot;
    public final Random mRandom;
    public int mStreamPosition;
    public final ObjectAnimator mStreamingAnimation;
    public Bitmap mTwoDot;

    static {
        Pattern.compile("\\S+");
        new Property(Integer.class, "streamPosition") { // from class: androidx.leanback.widget.StreamingTextView.1
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
    }

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

    public StreamingTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRandom = new Random();
    }
}
