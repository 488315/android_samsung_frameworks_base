package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import androidx.leanback.widget.RoundedRectHelperApi21;
import androidx.leanback.widget.ShadowHelperApi21;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ShadowOverlayContainer extends FrameLayout {
    public boolean mInitialized;
    public Paint mOverlayPaint;

    static {
        new Rect();
    }

    public ShadowOverlayContainer(Context context) {
        this(context, null, 0);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    public ShadowOverlayContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShadowOverlayContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (!this.mInitialized) {
            getResources().getDimension(R.dimen.lb_material_shadow_normal_z);
            getResources().getDimension(R.dimen.lb_material_shadow_focused_z);
            if (this.mInitialized) {
                throw new IllegalStateException("Already initialized");
            }
            return;
        }
        throw new IllegalStateException("Already initialized");
    }

    public ShadowOverlayContainer(Context context, int i, boolean z, float f, float f2, int i2) {
        super(context);
        if (!this.mInitialized) {
            this.mInitialized = true;
            if (i == 2) {
                setLayoutMode(1);
                LayoutInflater.from(getContext()).inflate(R.layout.lb_shadow, (ViewGroup) this, true);
                StaticShadowHelper$ShadowImpl staticShadowHelper$ShadowImpl = new StaticShadowHelper$ShadowImpl();
                staticShadowHelper$ShadowImpl.mNormalShadow = findViewById(R.id.lb_shadow_normal);
                staticShadowHelper$ShadowImpl.mFocusShadow = findViewById(R.id.lb_shadow_focused);
            } else if (i == 3) {
                if (i2 > 0) {
                    ShadowHelperApi21.C02931 c02931 = ShadowHelperApi21.sOutlineProvider;
                    if (RoundedRectHelperApi21.sRoundedRectProvider == null) {
                        RoundedRectHelperApi21.sRoundedRectProvider = new SparseArray();
                    }
                    ViewOutlineProvider viewOutlineProvider = (ViewOutlineProvider) RoundedRectHelperApi21.sRoundedRectProvider.get(i2);
                    if (viewOutlineProvider == null) {
                        viewOutlineProvider = new RoundedRectHelperApi21.RoundedRectOutlineProvider(i2);
                        if (RoundedRectHelperApi21.sRoundedRectProvider.size() < 32) {
                            RoundedRectHelperApi21.sRoundedRectProvider.put(i2, viewOutlineProvider);
                        }
                    }
                    setOutlineProvider(viewOutlineProvider);
                    setClipToOutline(true);
                } else {
                    setOutlineProvider(ShadowHelperApi21.sOutlineProvider);
                }
                new ShadowHelperApi21.ShadowImpl();
                setZ(f);
            }
            if (z) {
                setWillNotDraw(false);
                Paint paint = new Paint();
                this.mOverlayPaint = paint;
                paint.setColor(0);
                this.mOverlayPaint.setStyle(Paint.Style.FILL);
                return;
            }
            setWillNotDraw(true);
            this.mOverlayPaint = null;
            return;
        }
        throw new IllegalStateException();
    }
}
