package androidx.slidingpanelayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.R$styleable;
import androidx.appcompat.widget.ViewStubCompat;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SPLToolbarContainer extends FrameLayout {
    public final ViewStubCompat mViewStubCompat;

    public SPLToolbarContainer(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        ViewStubCompat viewStubCompat = this.mViewStubCompat;
        if (viewStubCompat != null) {
            viewStubCompat.bringToFront();
            this.mViewStubCompat.invalidate();
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        ViewStubCompat viewStubCompat = this.mViewStubCompat;
        if (viewStubCompat != null) {
            viewStubCompat.bringToFront();
            this.mViewStubCompat.invalidate();
        }
        super.onMeasure(i, i2);
    }

    public SPLToolbarContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SPLToolbarContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(R$styleable.AppCompatTheme);
        if (!obtainStyledAttributes.getBoolean(147, false)) {
            LayoutInflater.from(context).inflate(R.layout.sesl_spl_action_mode_view_stub, (ViewGroup) this, true);
            this.mViewStubCompat = (ViewStubCompat) findViewById(R.id.action_mode_bar_stub);
        }
        obtainStyledAttributes.recycle();
        setWillNotDraw(false);
    }
}
