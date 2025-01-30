package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class Placeholder extends View {
    public View mContent;
    public int mContentId;
    public int mEmptyVisibility;

    public Placeholder(Context context) {
        super(context);
        this.mContentId = -1;
        this.mContent = null;
        this.mEmptyVisibility = 4;
        init(null);
    }

    public final void init(AttributeSet attributeSet) {
        super.setVisibility(this.mEmptyVisibility);
        this.mContentId = -1;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_placeholder);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 0) {
                    this.mContentId = obtainStyledAttributes.getResourceId(index, this.mContentId);
                } else if (index == 1) {
                    this.mEmptyVisibility = obtainStyledAttributes.getInt(index, this.mEmptyVisibility);
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (isInEditMode()) {
            canvas.drawRGB(IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut, IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut, IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut);
            Paint paint = new Paint();
            paint.setARGB(255, 210, 210, 210);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, 0));
            Rect rect = new Rect();
            canvas.getClipBounds(rect);
            paint.setTextSize(rect.height());
            int height = rect.height();
            int width = rect.width();
            paint.setTextAlign(Paint.Align.LEFT);
            paint.getTextBounds("?", 0, 1, rect);
            canvas.drawText("?", ((width / 2.0f) - (rect.width() / 2.0f)) - rect.left, ((rect.height() / 2.0f) + (height / 2.0f)) - rect.bottom, paint);
        }
    }

    public Placeholder(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContentId = -1;
        this.mContent = null;
        this.mEmptyVisibility = 4;
        init(attributeSet);
    }

    public Placeholder(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContentId = -1;
        this.mContent = null;
        this.mEmptyVisibility = 4;
        init(attributeSet);
    }

    public Placeholder(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        this.mContentId = -1;
        this.mContent = null;
        this.mEmptyVisibility = 4;
        init(attributeSet);
    }
}
