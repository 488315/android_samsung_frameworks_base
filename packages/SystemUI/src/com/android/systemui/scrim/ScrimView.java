package com.android.systemui.scrim;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import androidx.profileinstaller.ProfileInstallReceiver$$ExternalSyntheticLambda0;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.systemui.Dependency;
import com.android.systemui.scrim.ScrimDrawable;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.statusbar.phone.QSScrimViewSwitch;
import com.android.systemui.util.LargeScreenUtils;
import java.util.function.IntConsumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class ScrimView extends View implements ScrimViewBase {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mBlendWithMainColor;
    public PorterDuffColorFilter mColorFilter;
    public final Object mColorLock;
    public final ColorExtractor.GradientColors mColors;
    public Drawable mDrawable;
    public Rect mDrawableBounds;
    public final ProfileInstallReceiver$$ExternalSyntheticLambda0 mExecutor;
    public final Looper mExecutorLooper;
    public QSScrimViewSwitch mQSScrimViewSwitch;
    public String mScrimName;
    public int mTintColor;
    public float mViewAlpha;
    public IntConsumer mVisibilityChangedListener;

    public ScrimView(Context context) {
        this(context, null);
    }

    public final boolean canReceivePointerEvents() {
        return false;
    }

    @Override // android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        String str = this.mScrimName;
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, str, dispatchTouchEvent);
        return dispatchTouchEvent;
    }

    public final void executeOnExecutor(Runnable runnable) {
        if (this.mExecutor == null || Looper.myLooper() == this.mExecutorLooper) {
            runnable.run();
        } else {
            this.mExecutor.getClass();
            runnable.run();
        }
    }

    public Drawable getDrawable() {
        return this.mDrawable;
    }

    public final int getMainColor() {
        Drawable drawable = this.mDrawable;
        if (drawable instanceof ScrimDrawable) {
            return ((ScrimDrawable) drawable).getMainColor();
        }
        return -1;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        super.invalidateDrawable(drawable);
        if (drawable == this.mDrawable) {
            invalidate();
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.mDrawable.getAlpha() > 0) {
            Resources resources = getResources();
            Drawable drawable = this.mDrawable;
            if (drawable instanceof ScrimDrawable) {
                ((ScrimDrawable) drawable).mShouldUseLargeScreenSize = LargeScreenUtils.shouldUseLargeScreenShadeHeader(resources);
            }
            this.mDrawable.draw(canvas);
        }
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        Rect rect = this.mDrawableBounds;
        if (rect != null) {
            this.mDrawable.setBounds(rect);
        } else if (z) {
            this.mDrawable.setBounds(i, i2, i3, i4);
            invalidate();
        }
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        IntConsumer intConsumer = this.mVisibilityChangedListener;
        if (intConsumer == null || this != view) {
            return;
        }
        intConsumer.accept(i);
    }

    @Override // android.view.View
    public final void setClickable(final boolean z) {
        executeOnExecutor(new Runnable() { // from class: com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                super/*android.view.View*/.setClickable(z);
            }
        });
    }

    public final void setColors(final ColorExtractor.GradientColors gradientColors, final boolean z) {
        if (gradientColors == null) {
            throw new IllegalArgumentException("Colors cannot be null");
        }
        executeOnExecutor(new Runnable() { // from class: com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ScrimView scrimView = ScrimView.this;
                ColorExtractor.GradientColors gradientColors2 = gradientColors;
                boolean z2 = z;
                synchronized (scrimView.mColorLock) {
                    try {
                        if (scrimView.mColors.equals(gradientColors2)) {
                            return;
                        }
                        scrimView.mColors.set(gradientColors2);
                        scrimView.updateColorWithTint(z2);
                    } finally {
                    }
                }
            }
        });
    }

    public final void setCornerRadius(int i) {
        Drawable drawable = this.mDrawable;
        if (drawable instanceof ScrimDrawable) {
            ScrimDrawable scrimDrawable = (ScrimDrawable) drawable;
            float f = i;
            if (f == scrimDrawable.mCornerRadius) {
                return;
            }
            scrimDrawable.mCornerRadius = f;
            ScrimDrawable.ConcaveInfo concaveInfo = scrimDrawable.mConcaveInfo;
            if (concaveInfo != null) {
                concaveInfo.mPathOverlap = f;
                float[] fArr = concaveInfo.mCornerRadii;
                fArr[0] = f;
                fArr[1] = f;
                fArr[2] = f;
                fArr[3] = f;
                scrimDrawable.updatePath();
            }
            scrimDrawable.invalidateSelf();
        }
    }

    public void setDrawable(final Drawable drawable) {
        executeOnExecutor(new Runnable() { // from class: com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ScrimView scrimView = ScrimView.this;
                Drawable drawable2 = drawable;
                scrimView.mDrawable = drawable2;
                drawable2.setCallback(scrimView);
                scrimView.mDrawable.setBounds(scrimView.getLeft(), scrimView.getTop(), scrimView.getRight(), scrimView.getBottom());
                scrimView.mDrawable.setAlpha((int) (scrimView.mViewAlpha * 255.0f));
                scrimView.invalidate();
            }
        });
    }

    public final void setScrimName(String str) {
        this.mScrimName = str;
        if (str.equals("behind_scrim")) {
            this.mQSScrimViewSwitch = (QSScrimViewSwitch) Dependency.sDependency.getDependencyInner(QSScrimViewSwitch.class);
        }
    }

    public final void setTint(final int i) {
        executeOnExecutor(new Runnable() { // from class: com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda4
            public final /* synthetic */ boolean f$2 = false;

            @Override // java.lang.Runnable
            public final void run() {
                ScrimView scrimView = ScrimView.this;
                int i2 = i;
                boolean z = this.f$2;
                if (scrimView.mTintColor == i2) {
                    return;
                }
                scrimView.mTintColor = i2;
                scrimView.updateColorWithTint(z);
            }
        });
    }

    public final void setViewAlpha(final float f) {
        if (!Float.isNaN(f)) {
            executeOnExecutor(new Runnable() { // from class: com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ScrimView scrimView = ScrimView.this;
                    float f2 = f;
                    if (f2 != scrimView.mViewAlpha) {
                        scrimView.mViewAlpha = f2;
                        scrimView.mDrawable.setAlpha((int) (255.0f * f2));
                        QSScrimViewSwitch qSScrimViewSwitch = scrimView.mQSScrimViewSwitch;
                        if (qSScrimViewSwitch != null) {
                            qSScrimViewSwitch._scrimBehindAlpha.updateState(null, Float.valueOf(f2));
                        }
                    }
                }
            });
        } else {
            throw new IllegalArgumentException("alpha cannot be NaN: " + f);
        }
    }

    public final void updateColorWithTint(boolean z) {
        Drawable drawable = this.mDrawable;
        if (drawable instanceof ScrimDrawable) {
            ScrimDrawable scrimDrawable = (ScrimDrawable) drawable;
            float alpha = Color.alpha(this.mTintColor) / 255.0f;
            int i = this.mTintColor;
            if (this.mBlendWithMainColor) {
                i = ColorUtils.blendARGB(alpha, this.mColors.getMainColor(), this.mTintColor);
            }
            scrimDrawable.setColor(i, z);
            return;
        }
        if (Color.alpha(this.mTintColor) != 0) {
            PorterDuffColorFilter porterDuffColorFilter = this.mColorFilter;
            PorterDuff.Mode mode = porterDuffColorFilter == null ? PorterDuff.Mode.SRC_OVER : porterDuffColorFilter.getMode();
            PorterDuffColorFilter porterDuffColorFilter2 = this.mColorFilter;
            if (porterDuffColorFilter2 == null || porterDuffColorFilter2.getColor() != this.mTintColor) {
                this.mColorFilter = new PorterDuffColorFilter(this.mTintColor, mode);
            }
        } else {
            this.mColorFilter = null;
        }
        this.mDrawable.setColorFilter(this.mColorFilter);
        this.mDrawable.invalidateSelf();
    }

    public ScrimView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScrimView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ScrimView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mColorLock = new Object();
        new ColorExtractor.GradientColors();
        this.mViewAlpha = 1.0f;
        this.mBlendWithMainColor = true;
        this.mQSScrimViewSwitch = null;
        setFocusable(false);
        setImportantForAccessibility(2);
        ScrimDrawable scrimDrawable = new ScrimDrawable();
        this.mDrawable = scrimDrawable;
        scrimDrawable.setCallback(this);
        this.mColors = new ColorExtractor.GradientColors();
        this.mExecutorLooper = Looper.myLooper();
        this.mExecutor = new ProfileInstallReceiver$$ExternalSyntheticLambda0();
        executeOnExecutor(new Runnable() { // from class: com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ScrimView scrimView = ScrimView.this;
                int i3 = ScrimView.$r8$clinit;
                scrimView.updateColorWithTint(false);
            }
        });
    }
}
