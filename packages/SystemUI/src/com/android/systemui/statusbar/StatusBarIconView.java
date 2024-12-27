package com.android.systemui.statusbar;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.FloatProperty;
import android.util.Log;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import androidx.core.graphics.ColorUtils;
import com.android.app.animation.Interpolators;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.Flags;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.notification.NotificationContentDescription;
import com.android.systemui.statusbar.notification.NotificationDozeHelper;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import com.android.systemui.statusbar.phone.NotificationIconContainer$$ExternalSyntheticLambda0;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatusBarIconView extends AnimatedImageView implements StatusIconDisplayable {
    public int mAnimationStartColor;
    public boolean mApplyShadowEffect;
    public boolean mBlockDotAnim;
    public final boolean mBlocked;
    public ValueAnimator mColorAnimator;
    public final StatusBarIconView$$ExternalSyntheticLambda0 mColorUpdater;
    public final Configuration mConfiguration;
    public int mCurrentSetColor;
    public int mDecorColor;
    public ObjectAnimator mDotAnimator;
    public float mDotAppearAmount;
    public final Paint mDotPaint;
    public float mDotRadius;
    public DoubleShadowStatusBarIconDrawable mDoubleShadowIconDrawable;
    public float mDozeAmount;
    public final NotificationDozeHelper mDozer;
    public int mDrawableColor;
    public StatusBarIcon mIcon;
    public float mIconAppearAmount;
    public ObjectAnimator mIconAppearAnimator;
    public int mIconColor;
    public Rect mIconRect;
    public float mIconScale;
    public float mIconScaleFactor;
    public Runnable mLayoutRunnable;
    public float[] mMatrix;
    public ColorMatrixColorFilter mMatrixColorFilter;
    int mNewStatusBarIconSize;
    public StatusBarNotification mNotification;
    public Drawable mNumberBackground;
    public final Paint mNumberPain;
    public String mNumberText;
    public int mNumberX;
    public int mNumberY;
    public Runnable mOnDismissListener;
    int mOriginalStatusBarIconSize;
    float mScaleToFitNewIconSize;
    public boolean mShowsConversation;
    public final String mSlot;
    public int mStaticDotRadius;
    int mStatusBarIconDrawingSize;
    public int mStatusBarIconDrawingSizeIncreased;
    public int mVisibleState;
    public static final AnonymousClass1 ICON_APPEAR_AMOUNT = new FloatProperty("iconAppearAmount") { // from class: com.android.systemui.statusbar.StatusBarIconView.1
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((StatusBarIconView) obj).mIconAppearAmount);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            StatusBarIconView statusBarIconView = (StatusBarIconView) obj;
            if (statusBarIconView.mIconAppearAmount != f) {
                statusBarIconView.mIconAppearAmount = f;
                statusBarIconView.invalidate();
            }
        }
    };
    public static final AnonymousClass2 DOT_APPEAR_AMOUNT = new FloatProperty("dot_appear_amount") { // from class: com.android.systemui.statusbar.StatusBarIconView.2
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((StatusBarIconView) obj).mDotAppearAmount);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            StatusBarIconView statusBarIconView = (StatusBarIconView) obj;
            if (statusBarIconView.mDotAppearAmount != f) {
                statusBarIconView.mDotAppearAmount = f;
                statusBarIconView.invalidate();
            }
        }
    };

    public StatusBarIconView(Context context, String str, StatusBarNotification statusBarNotification) {
        this(context, str, statusBarNotification, false);
    }

    public static String getVisibleStateString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "UNKNOWN" : "HIDDEN" : "DOT" : "ICON";
    }

    public final void debug(int i) {
        super.debug(i);
        Log.d("View", ImageView.debugIndent(i) + "slot=" + this.mSlot);
        Log.d("View", ImageView.debugIndent(i) + "icon=" + this.mIcon);
    }

    @Override // android.view.View
    public final void getDrawingRect(Rect rect) {
        super.getDrawingRect(rect);
        float translationX = getTranslationX();
        float translationY = getTranslationY();
        rect.left = (int) (rect.left + translationX);
        rect.right = (int) (rect.right + translationX);
        rect.top = (int) (rect.top + translationY);
        rect.bottom = (int) (rect.bottom + translationY);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.drawable.Drawable getIcon(com.android.internal.statusbar.StatusBarIcon r8) {
        /*
            r7 = this;
            android.content.Context r0 = r7.getContext()
            android.service.notification.StatusBarNotification r1 = r7.mNotification
            if (r1 == 0) goto L10
            android.content.Context r0 = r7.getContext()
            android.content.Context r0 = r1.getPackageContext(r0)
        L10:
            android.content.Context r1 = r7.getContext()
            if (r0 == 0) goto L17
            goto L1b
        L17:
            android.content.Context r0 = r7.getContext()
        L1b:
            android.os.UserHandle r2 = r8.user
            int r2 = r2.getIdentifier()
            r3 = -1
            if (r2 != r3) goto L25
            r2 = 0
        L25:
            boolean r3 = android.app.Flags.notificationsUseMonochromeAppIcon()
            r4 = 1
            if (r3 == 0) goto L5a
            com.android.internal.statusbar.StatusBarIcon$Type r3 = r8.type
            com.android.internal.statusbar.StatusBarIcon$Type r5 = com.android.internal.statusbar.StatusBarIcon.Type.MaybeMonochromeAppIcon
            if (r3 != r5) goto L5a
            android.content.pm.PackageManager r3 = r0.getPackageManager()
            android.content.pm.ApplicationInfo r5 = r0.getApplicationInfo()
            android.graphics.drawable.Drawable r3 = r5.loadIcon(r3)
            boolean r5 = r3 instanceof android.graphics.drawable.AdaptiveIconDrawable
            if (r5 == 0) goto L5a
            android.graphics.drawable.AdaptiveIconDrawable r3 = (android.graphics.drawable.AdaptiveIconDrawable) r3
            android.graphics.drawable.Drawable r3 = r3.getMonochrome()
            if (r3 == 0) goto L5a
            r7.setCropToPadding(r4)
            android.widget.ImageView$ScaleType r5 = android.widget.ImageView.ScaleType.CENTER
            r7.setScaleType(r5)
            com.android.systemui.statusbar.ScalingDrawableWrapper r5 = new com.android.systemui.statusbar.ScalingDrawableWrapper
            r6 = 1061158912(0x3f400000, float:0.75)
            r5.<init>(r3, r6)
            goto L5b
        L5a:
            r5 = 0
        L5b:
            if (r5 != 0) goto L63
            android.graphics.drawable.Icon r8 = r8.icon
            android.graphics.drawable.Drawable r5 = r8.loadDrawableAsUser(r0, r2)
        L63:
            android.util.TypedValue r8 = new android.util.TypedValue
            r8.<init>()
            android.content.res.Resources r0 = r1.getResources()
            r2 = 2131170361(0x7f071439, float:1.7955078E38)
            r0.getValue(r2, r8, r4)
            float r8 = r8.getFloat()
            float r7 = r7.mIconScaleFactor
            r0 = 0
            int r0 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r0 == 0) goto L7e
            r8 = r7
        L7e:
            if (r5 == 0) goto L99
            boolean r7 = android.app.ActivityManager.isLowRamDeviceStatic()
            android.content.res.Resources r0 = r1.getResources()
            if (r7 == 0) goto L8e
            r7 = 17105719(0x1050337, float:2.4430549E-38)
            goto L91
        L8e:
            r7 = 17105718(0x1050336, float:2.4430546E-38)
        L91:
            int r7 = r0.getDimensionPixelSize(r7)
            android.graphics.drawable.Drawable r5 = com.android.systemui.util.drawable.DrawableSize.downscaleToSize(r0, r5, r7, r7)
        L99:
            r7 = 1065353216(0x3f800000, float:1.0)
            int r7 = (r8 > r7 ? 1 : (r8 == r7 ? 0 : -1))
            if (r7 != 0) goto La0
            goto La6
        La0:
            com.android.systemui.statusbar.ScalingDrawableWrapper r7 = new com.android.systemui.statusbar.ScalingDrawableWrapper
            r7.<init>(r5, r8)
            r5 = r7
        La6:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.StatusBarIconView.getIcon(com.android.internal.statusbar.StatusBarIcon):android.graphics.drawable.Drawable");
    }

    public final float getIconScaleIncreased() {
        return this.mStatusBarIconDrawingSizeIncreased / this.mStatusBarIconDrawingSize;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final String getSlot() {
        return this.mSlot;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final int getVisibleState() {
        return this.mVisibleState;
    }

    @Override // com.android.systemui.statusbar.AnimatedImageView, android.widget.ImageView, android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final boolean isIconBlocked() {
        return this.mBlocked;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final boolean isIconVisible() {
        StatusBarIcon statusBarIcon = this.mIcon;
        return statusBarIcon != null && statusBarIcon.visible;
    }

    public void maybeUpdateIconScaleDimens() {
        int i;
        int i2;
        if (!(this.mNotification != null)) {
            this.mIconScale = 1.0f;
            return;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (getDrawable() != null && layoutParams != null && (i = layoutParams.width) > 0 && (i2 = layoutParams.height) > 0) {
            float intrinsicWidth = getDrawable().getIntrinsicWidth();
            float intrinsicHeight = getDrawable().getIntrinsicHeight();
            float min = Math.min(i / intrinsicWidth, i2 / intrinsicHeight);
            if (min > 1.0f) {
                min = 1.0f;
            }
            float f = this.mOriginalStatusBarIconSize;
            float min2 = Math.min(f / (intrinsicWidth * min), f / (intrinsicHeight * min));
            if (min2 > 1.0f) {
                Math.min(min2, 1.0f / min);
            }
        }
        this.mIconScale = (this.mStatusBarIconDrawingSize / this.mOriginalStatusBarIconSize) * 1.0f;
        updatePivot();
    }

    @Override // com.android.systemui.statusbar.AnimatedImageView, android.widget.ImageView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setRequestedFrameRate(-2.0f);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int diff = configuration.diff(this.mConfiguration);
        this.mConfiguration.setTo(configuration);
        if ((1073745920 & diff) != 0) {
            updateIconDimens();
        }
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        int tint = DarkIconDispatcher.getTint(arrayList, this, i);
        setImageTintList(ColorStateList.valueOf(tint));
        setDecorColor(tint);
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onDraw(Canvas canvas) {
        float interpolate;
        if (this.mIconAppearAmount > 0.0f) {
            canvas.save();
            int width = getWidth() / 2;
            int height = getHeight() / 2;
            float f = this.mIconScale;
            float f2 = this.mIconAppearAmount;
            canvas.scale(f * f2, f * f2, width, height);
            DoubleShadowStatusBarIconDrawable doubleShadowStatusBarIconDrawable = this.mDoubleShadowIconDrawable;
            if (doubleShadowStatusBarIconDrawable != null) {
                doubleShadowStatusBarIconDrawable.draw(canvas);
            }
            super.onDraw(canvas);
            canvas.restore();
        }
        Drawable drawable = this.mNumberBackground;
        if (drawable != null) {
            drawable.draw(canvas);
            canvas.drawText(this.mNumberText, this.mNumberX, this.mNumberY, this.mNumberPain);
        }
        if (this.mDotAppearAmount != 0.0f) {
            float alpha = Color.alpha(this.mDecorColor) / 255.0f;
            float f3 = this.mDotAppearAmount;
            if (f3 <= 1.0f) {
                interpolate = this.mDotRadius * f3;
            } else {
                float f4 = f3 - 1.0f;
                alpha *= 1.0f - f4;
                interpolate = NotificationUtils.interpolate(this.mDotRadius, getWidth() / 4, f4);
            }
            this.mDotPaint.setAlpha((int) (alpha * 255.0f));
            canvas.drawCircle(this.mNewStatusBarIconSize / 2, getHeight() / 2, interpolate, this.mDotPaint);
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        StatusBarNotification statusBarNotification = this.mNotification;
        if (statusBarNotification != null) {
            accessibilityEvent.setParcelableData(statusBarNotification.getNotification());
        }
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        Runnable runnable = this.mLayoutRunnable;
        if (runnable != null) {
            runnable.run();
            this.mLayoutRunnable = null;
        }
        updatePivot();
        if (!this.mApplyShadowEffect || getDrawable() == null) {
            return;
        }
        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        if (rect.equals(this.mIconRect)) {
            return;
        }
        this.mIconRect = rect;
        DoubleShadowStatusBarIconDrawable doubleShadowStatusBarIconDrawable = new DoubleShadowStatusBarIconDrawable(getDrawable(), getContext(), getWidth(), getHeight());
        this.mDoubleShadowIconDrawable = doubleShadowStatusBarIconDrawable;
        doubleShadowStatusBarIconDrawable.drawShadowOnly = true;
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mNotification != null) {
            return;
        }
        setMeasuredDimension((int) (getMeasuredWidth() * this.mScaleToFitNewIconSize), getMeasuredHeight());
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        updateDrawable(true);
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mNumberBackground != null) {
            placeNumber();
        }
    }

    public final void placeNumber() {
        String string = this.mIcon.number > getContext().getResources().getInteger(R.integer.status_bar_notification_info_maxnum) ? getContext().getResources().getString(R.string.status_bar_notification_info_overflow) : NumberFormat.getIntegerInstance().format(this.mIcon.number);
        this.mNumberText = string;
        int width = getWidth();
        int height = getHeight();
        Rect rect = new Rect();
        this.mNumberPain.getTextBounds(string, 0, string.length(), rect);
        int i = rect.right - rect.left;
        int i2 = rect.bottom - rect.top;
        this.mNumberBackground.getPadding(rect);
        int i3 = rect.left + i + rect.right;
        if (i3 < this.mNumberBackground.getMinimumWidth()) {
            i3 = this.mNumberBackground.getMinimumWidth();
        }
        int i4 = rect.right;
        this.mNumberX = (width - i4) - (((i3 - i4) - rect.left) / 2);
        int i5 = rect.top + i2 + rect.bottom;
        if (i5 < this.mNumberBackground.getMinimumWidth()) {
            i5 = this.mNumberBackground.getMinimumWidth();
        }
        int i6 = rect.bottom;
        this.mNumberY = (height - i6) - ((((i5 - rect.top) - i2) - i6) / 2);
        this.mNumberBackground.setBounds(width - i3, height - i5, width, height);
    }

    public final void reloadDimens$1() {
        boolean z = this.mDotRadius == ((float) this.mStaticDotRadius);
        Resources resources = getResources();
        this.mStaticDotRadius = resources.getDimensionPixelSize(com.android.systemui.R.dimen.overflow_dot_radius);
        this.mOriginalStatusBarIconSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_icon_view_width);
        int dimensionPixelSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_icon_view_width);
        this.mNewStatusBarIconSize = dimensionPixelSize;
        this.mScaleToFitNewIconSize = dimensionPixelSize / this.mOriginalStatusBarIconSize;
        this.mStatusBarIconDrawingSizeIncreased = resources.getDimensionPixelSize(com.android.systemui.R.dimen.status_bar_icon_drawing_size_dark);
        this.mStatusBarIconDrawingSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.status_bar_icon_drawing_size);
        if (z) {
            this.mDotRadius = this.mStaticDotRadius;
        }
        resources.getDimension(17106294);
        resources.getDimension(17106293);
    }

    /* JADX WARN: Code restructure failed: missing block: B:65:0x004b, code lost:
    
        if (r0.getResId() == r3.getResId()) goto L6;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean set(com.android.internal.statusbar.StatusBarIcon r8) {
        /*
            Method dump skipped, instructions count: 229
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.StatusBarIconView.set(com.android.internal.statusbar.StatusBarIcon):boolean");
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setDecorColor(int i) {
        this.mDecorColor = i;
        updateDecorColor();
    }

    public final void setIconColor(int i, boolean z) {
        if (this.mIconColor != i) {
            this.mIconColor = i;
            ValueAnimator valueAnimator = this.mColorAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            int i2 = this.mCurrentSetColor;
            if (i2 == i) {
                return;
            }
            if (!z || i2 == 0) {
                this.mCurrentSetColor = i;
                updateIconColor();
                return;
            }
            this.mAnimationStartColor = i2;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mColorAnimator = ofFloat;
            ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            this.mColorAnimator.setDuration(100L);
            this.mColorAnimator.addUpdateListener(this.mColorUpdater);
            this.mColorAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.StatusBarIconView.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    StatusBarIconView statusBarIconView = StatusBarIconView.this;
                    statusBarIconView.mColorAnimator = null;
                    statusBarIconView.mAnimationStartColor = 0;
                }
            });
            this.mColorAnimator.start();
        }
    }

    public final void setNotification(StatusBarNotification statusBarNotification, CharSequence charSequence) {
        this.mNotification = statusBarNotification;
        if (!TextUtils.isEmpty(charSequence)) {
            setContentDescription(charSequence);
            setImportantForAccessibility(1);
        }
        maybeUpdateIconScaleDimens();
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setStaticDrawableColor(int i) {
        this.mDrawableColor = i;
        this.mCurrentSetColor = i;
        updateIconColor();
        if (Color.alpha(0) == 255) {
            int i2 = this.mDrawableColor;
            if (!ContrastColorUtil.satisfiesTextContrast(0, i2)) {
                float[] fArr = new float[3];
                int i3 = this.mDrawableColor;
                ThreadLocal threadLocal = ColorUtils.TEMP_ARRAY;
                ColorUtils.RGBToHSL(Color.red(i3), Color.green(i3), Color.blue(i3), fArr);
                if (fArr[1] < 0.2f) {
                    i2 = 0;
                }
                ContrastColorUtil.resolveContrastColor(((ImageView) this).mContext, i2, 0, !ContrastColorUtil.isColorLight(0));
            }
        }
        this.mIconColor = i;
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setVisibleState(int i) {
        setVisibleState(i, true, null, 0L);
    }

    @Override // android.view.View
    public final String toString() {
        return "StatusBarIconView(slot='" + this.mSlot + "' alpha=" + getAlpha() + " icon=" + this.mIcon + " visibleState=" + getVisibleStateString(this.mVisibleState) + " iconColor=#" + Integer.toHexString(this.mIconColor) + " staticDrawableColor=#" + Integer.toHexString(this.mDrawableColor) + " decorColor=#" + Integer.toHexString(this.mDecorColor) + " animationStartColor=#" + Integer.toHexString(this.mAnimationStartColor) + " currentSetColor=#" + Integer.toHexString(this.mCurrentSetColor) + " notification=" + this.mNotification + ')';
    }

    public final void updateDecorColor() {
        int interpolateColors = NotificationUtils.interpolateColors(this.mDozeAmount, this.mDecorColor, -1);
        if (this.mDotPaint.getColor() != interpolateColors) {
            this.mDotPaint.setColor(interpolateColors);
            if (this.mDotAppearAmount != 0.0f) {
                invalidate();
            }
        }
    }

    public final boolean updateDrawable(boolean z) {
        if (this.mIcon == null) {
            return false;
        }
        try {
            Trace.beginSection("StatusBarIconView#updateDrawable()");
            Drawable icon = getIcon(this.mIcon);
            if (icon == null) {
                Log.w("StatusBarIconView", "No icon for slot " + this.mSlot + "; " + this.mIcon.icon);
                return false;
            }
            if (z) {
                setImageDrawable(null);
            }
            setImageDrawable(icon);
            if (this.mApplyShadowEffect) {
                DoubleShadowStatusBarIconDrawable doubleShadowStatusBarIconDrawable = new DoubleShadowStatusBarIconDrawable(getDrawable(), getContext(), getWidth(), getHeight());
                this.mDoubleShadowIconDrawable = doubleShadowStatusBarIconDrawable;
                doubleShadowStatusBarIconDrawable.drawShadowOnly = true;
            }
            return true;
        } catch (OutOfMemoryError unused) {
            Log.w("StatusBarIconView", "OOM while inflating " + this.mIcon.icon + " for slot " + this.mSlot);
            return false;
        } finally {
            Trace.endSection();
        }
    }

    public final void updateIconColor() {
        if (this.mShowsConversation || Boolean.TRUE.equals(getTag(com.android.systemui.R.id.conversation_notification))) {
            setColorFilter((ColorFilter) null);
            return;
        }
        if (this.mCurrentSetColor == 0) {
            NotificationDozeHelper notificationDozeHelper = this.mDozer;
            float f = this.mDozeAmount;
            if (f > 0.0f) {
                notificationDozeHelper.mGrayscaleColorMatrix.setSaturation(1.0f - f);
                setColorFilter(new ColorMatrixColorFilter(notificationDozeHelper.mGrayscaleColorMatrix));
                return;
            } else {
                notificationDozeHelper.getClass();
                setColorFilter((ColorFilter) null);
                return;
            }
        }
        this.mMatrix = new float[20];
        this.mMatrixColorFilter = new ColorMatrixColorFilter(this.mMatrix);
        int interpolateColors = NotificationUtils.interpolateColors(this.mDozeAmount, this.mCurrentSetColor, -1);
        float[] fArr = this.mMatrix;
        float f2 = this.mDozeAmount * 0.67f;
        Arrays.fill(fArr, 0.0f);
        fArr[4] = Color.red(interpolateColors);
        fArr[9] = Color.green(interpolateColors);
        fArr[14] = Color.blue(interpolateColors);
        fArr[18] = (Color.alpha(interpolateColors) / 255.0f) + f2;
        this.mMatrixColorFilter.setColorMatrixArray(this.mMatrix);
        setColorFilter((ColorFilter) null);
        setColorFilter(this.mMatrixColorFilter);
    }

    public final void updateIconDimens() {
        Trace.beginSection("StatusBarIconView#updateIconDimens");
        try {
            reloadDimens$1();
            updateDrawable(true);
            maybeUpdateIconScaleDimens();
        } finally {
            Trace.endSection();
        }
    }

    public final void updatePivot() {
        if (isLayoutRtl()) {
            setPivotX(((this.mIconScale + 1.0f) / 2.0f) * getWidth());
        } else {
            setPivotX(((1.0f - this.mIconScale) / 2.0f) * getWidth());
        }
        setPivotY((getHeight() - (this.mIconScale * getWidth())) / 2.0f);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.StatusBarIconView$$ExternalSyntheticLambda0] */
    public StatusBarIconView(Context context, String str, StatusBarNotification statusBarNotification, boolean z) {
        super(context);
        this.mStatusBarIconDrawingSizeIncreased = 1;
        this.mStatusBarIconDrawingSize = 1;
        this.mOriginalStatusBarIconSize = 1;
        this.mNewStatusBarIconSize = 1;
        this.mScaleToFitNewIconSize = 1.0f;
        this.mIconScale = 1.0f;
        this.mDotPaint = new Paint(1);
        this.mVisibleState = 0;
        this.mIconAppearAmount = 1.0f;
        this.mCurrentSetColor = 0;
        this.mAnimationStartColor = 0;
        this.mColorUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.StatusBarIconView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                StatusBarIconView statusBarIconView = StatusBarIconView.this;
                statusBarIconView.mCurrentSetColor = NotificationUtils.interpolateColors(valueAnimator.getAnimatedFraction(), statusBarIconView.mAnimationStartColor, statusBarIconView.mIconColor);
                statusBarIconView.updateIconColor();
            }
        };
        this.mDoubleShadowIconDrawable = null;
        this.mIconRect = new Rect(0, 0, 0, 0);
        this.mApplyShadowEffect = false;
        this.mDozer = new NotificationDozeHelper();
        this.mBlocked = z;
        this.mSlot = str;
        Paint paint = new Paint();
        this.mNumberPain = paint;
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(context.getColor(com.android.systemui.R.drawable.notification_number_text_color));
        paint.setAntiAlias(true);
        setNotification(statusBarNotification, statusBarNotification != null ? NotificationContentDescription.contentDescForNotification(((ImageView) this).mContext, statusBarNotification.getNotification()) : null);
        setScaleType(ImageView.ScaleType.CENTER);
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        this.mConfiguration = configuration;
        boolean z2 = (configuration.uiMode & 48) == 32;
        if (this.mNotification != null) {
            setDecorColor(getContext().getColor(z2 ? R.color.red : R.color.resolver_accent_ripple));
        }
        reloadDimens$1();
        maybeUpdateIconScaleDimens();
        Flags.FEATURE_FLAGS.getClass();
        setCropToPadding(true);
    }

    @Override // com.android.systemui.statusbar.StatusIconDisplayable
    public final void setVisibleState(int i, boolean z) {
        setVisibleState(i, z, null, 0L);
    }

    public final void setVisibleState(int i, boolean z, final NotificationIconContainer$$ExternalSyntheticLambda0 notificationIconContainer$$ExternalSyntheticLambda0, long j) {
        float f;
        Interpolator interpolator;
        boolean z2;
        boolean z3 = true;
        boolean z4 = false;
        if (i != this.mVisibleState) {
            this.mVisibleState = i;
            ObjectAnimator objectAnimator = this.mIconAppearAnimator;
            if (objectAnimator != null) {
                objectAnimator.cancel();
            }
            ObjectAnimator objectAnimator2 = this.mDotAnimator;
            if (objectAnimator2 != null) {
                objectAnimator2.cancel();
            }
            float f2 = 1.0f;
            if (z) {
                Interpolator interpolator2 = Interpolators.FAST_OUT_LINEAR_IN;
                if (i == 0) {
                    interpolator = Interpolators.LINEAR_OUT_SLOW_IN;
                    f = 1.0f;
                } else {
                    f = 0.0f;
                    interpolator = interpolator2;
                }
                float f3 = this.mIconAppearAmount;
                if (f != f3) {
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, ICON_APPEAR_AMOUNT, f3, f);
                    this.mIconAppearAnimator = ofFloat;
                    ofFloat.setInterpolator(interpolator);
                    this.mIconAppearAnimator.setDuration(j == 0 ? 100L : j);
                    this.mIconAppearAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.StatusBarIconView.4
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            StatusBarIconView.this.mIconAppearAnimator = null;
                            Runnable runnable = notificationIconContainer$$ExternalSyntheticLambda0;
                            if (runnable != null) {
                                runnable.run();
                            }
                        }
                    });
                    this.mIconAppearAnimator.start();
                    z2 = true;
                } else {
                    z2 = false;
                }
                float f4 = i == 0 ? 2.0f : 0.0f;
                if (i == 1) {
                    interpolator2 = Interpolators.LINEAR_OUT_SLOW_IN;
                } else {
                    f2 = f4;
                }
                float f5 = this.mDotAppearAmount;
                if (f2 == f5 || this.mBlockDotAnim) {
                    z3 = z2;
                } else {
                    ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, DOT_APPEAR_AMOUNT, f5, f2);
                    this.mDotAnimator = ofFloat2;
                    ofFloat2.setInterpolator(interpolator2);
                    this.mDotAnimator.setDuration(j != 0 ? j : 100L);
                    final boolean z5 = !z2;
                    this.mDotAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.StatusBarIconView.5
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            Runnable runnable;
                            StatusBarIconView.this.mDotAnimator = null;
                            if (!z5 || (runnable = notificationIconContainer$$ExternalSyntheticLambda0) == null) {
                                return;
                            }
                            runnable.run();
                        }
                    });
                    this.mDotAnimator.start();
                }
                z4 = z3;
            } else {
                float f6 = i == 0 ? 1.0f : 0.0f;
                if (this.mIconAppearAmount != f6) {
                    this.mIconAppearAmount = f6;
                    invalidate();
                }
                float f7 = i == 1 ? 1.0f : i == 0 ? 2.0f : 0.0f;
                if (this.mDotAppearAmount != f7) {
                    this.mDotAppearAmount = f7;
                    invalidate();
                }
            }
        }
        if (z4 || notificationIconContainer$$ExternalSyntheticLambda0 == null) {
            return;
        }
        notificationIconContainer$$ExternalSyntheticLambda0.run();
    }
}
