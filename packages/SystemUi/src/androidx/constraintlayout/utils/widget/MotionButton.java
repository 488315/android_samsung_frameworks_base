package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MotionButton extends AppCompatButton {
    public Path mPath;
    public RectF mRect;
    public float mRound;
    public float mRoundPercent;
    public ViewOutlineProvider mViewOutlineProvider;

    public MotionButton(Context context) {
        super(context);
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        init(context, null);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public final void init(Context context, AttributeSet attributeSet) {
        setPadding(0, 0, 0, 0);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.ImageFilterView);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 10) {
                    float dimension = obtainStyledAttributes.getDimension(index, 0.0f);
                    if (Float.isNaN(dimension)) {
                        this.mRound = dimension;
                        float f = this.mRoundPercent;
                        this.mRoundPercent = -1.0f;
                        setRoundPercent(f);
                    } else {
                        boolean z = this.mRound != dimension;
                        this.mRound = dimension;
                        if (dimension != 0.0f) {
                            if (this.mPath == null) {
                                this.mPath = new Path();
                            }
                            if (this.mRect == null) {
                                this.mRect = new RectF();
                            }
                            if (this.mViewOutlineProvider == null) {
                                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.MotionButton.2
                                    @Override // android.view.ViewOutlineProvider
                                    public final void getOutline(View view, Outline outline) {
                                        outline.setRoundRect(0, 0, MotionButton.this.getWidth(), MotionButton.this.getHeight(), MotionButton.this.mRound);
                                    }
                                };
                                this.mViewOutlineProvider = viewOutlineProvider;
                                setOutlineProvider(viewOutlineProvider);
                            }
                            setClipToOutline(true);
                            this.mRect.set(0.0f, 0.0f, getWidth(), getHeight());
                            this.mPath.reset();
                            Path path = this.mPath;
                            RectF rectF = this.mRect;
                            float f2 = this.mRound;
                            path.addRoundRect(rectF, f2, f2, Path.Direction.CW);
                        } else {
                            setClipToOutline(false);
                        }
                        if (z) {
                            invalidateOutline();
                        }
                    }
                } else if (index == 11) {
                    setRoundPercent(obtainStyledAttributes.getFloat(index, 0.0f));
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    public final void setRoundPercent(float f) {
        boolean z = this.mRoundPercent != f;
        this.mRoundPercent = f;
        if (f != 0.0f) {
            if (this.mPath == null) {
                this.mPath = new Path();
            }
            if (this.mRect == null) {
                this.mRect = new RectF();
            }
            if (this.mViewOutlineProvider == null) {
                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.MotionButton.1
                    @Override // android.view.ViewOutlineProvider
                    public final void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, MotionButton.this.getWidth(), MotionButton.this.getHeight(), (Math.min(r3, r4) * MotionButton.this.mRoundPercent) / 2.0f);
                    }
                };
                this.mViewOutlineProvider = viewOutlineProvider;
                setOutlineProvider(viewOutlineProvider);
            }
            setClipToOutline(true);
            int width = getWidth();
            int height = getHeight();
            float min = (Math.min(width, height) * this.mRoundPercent) / 2.0f;
            this.mRect.set(0.0f, 0.0f, width, height);
            this.mPath.reset();
            this.mPath.addRoundRect(this.mRect, min, min, Path.Direction.CW);
        } else {
            setClipToOutline(false);
        }
        if (z) {
            invalidateOutline();
        }
    }

    public MotionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        init(context, attributeSet);
    }

    public MotionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        init(context, attributeSet);
    }
}
