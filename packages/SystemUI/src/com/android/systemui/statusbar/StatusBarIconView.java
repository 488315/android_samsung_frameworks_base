package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
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
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import androidx.core.graphics.ColorUtils;
import com.android.app.animation.Interpolators;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.R;
import com.android.systemui.modes.shared.ModesUiIcons;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.notification.NotificationContentDescription;
import com.android.systemui.statusbar.notification.NotificationDozeHelper;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import com.android.systemui.statusbar.phone.LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.phone.NotificationIconContainer$$ExternalSyntheticLambda0;
import com.android.systemui.util.drawable.DrawableSize;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StatusBarIconView extends AnimatedImageView implements StatusIconDisplayable {
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
    public LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda4 mOnDismissListener;
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

    public final Drawable getIcon(StatusBarIcon statusBarIcon) {
        Context context = getContext();
        StatusBarNotification statusBarNotification = this.mNotification;
        if (statusBarNotification != null) {
            context = statusBarNotification.getPackageContext(getContext());
        }
        Context context2 = getContext();
        if (context == null) {
            context = getContext();
        }
        int i = ModesUiIcons.$r8$clinit;
        int identifier = statusBarIcon.user.getIdentifier();
        if (identifier == -1) {
            identifier = 0;
        }
        Drawable loadDrawableAsUser = statusBarIcon.icon.loadDrawableAsUser(context, identifier);
        TypedValue typedValue = new TypedValue();
        context2.getResources().getValue(R.dimen.status_bar_icon_scale_factor, typedValue, true);
        float f = typedValue.getFloat();
        float f2 = this.mIconScaleFactor;
        if (f2 != 0.0f) {
            f = f2;
        }
        if (loadDrawableAsUser != null) {
            boolean isLowRamDeviceStatic = ActivityManager.isLowRamDeviceStatic();
            Resources resources = context2.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(isLowRamDeviceStatic ? 17105816 : 17105815);
            loadDrawableAsUser = DrawableSize.downscaleToSize(resources, loadDrawableAsUser, dimensionPixelSize, dimensionPixelSize);
        }
        return f == 1.0f ? loadDrawableAsUser : new ScalingDrawableWrapper(loadDrawableAsUser, f);
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
            int i3 = ModesUiIcons.$r8$clinit;
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

    public final void reloadDimens$1() {
        boolean z = this.mDotRadius == ((float) this.mStaticDotRadius);
        Resources resources = getResources();
        this.mStaticDotRadius = resources.getDimensionPixelSize(R.dimen.overflow_dot_radius);
        this.mOriginalStatusBarIconSize = resources.getDimensionPixelSize(R.dimen.notification_icon_view_width);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.notification_icon_view_width);
        this.mNewStatusBarIconSize = dimensionPixelSize;
        this.mScaleToFitNewIconSize = dimensionPixelSize / this.mOriginalStatusBarIconSize;
        this.mStatusBarIconDrawingSizeIncreased = resources.getDimensionPixelSize(R.dimen.status_bar_icon_drawing_size_dark);
        this.mStatusBarIconDrawingSize = resources.getDimensionPixelSize(R.dimen.status_bar_icon_drawing_size);
        if (z) {
            this.mDotRadius = this.mStaticDotRadius;
        }
        resources.getDimension(17106386);
        resources.getDimension(17106385);
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x004b, code lost:
    
        if (r0.getResId() == r3.getResId()) goto L6;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean set(com.android.internal.statusbar.StatusBarIcon r7) {
        /*
            r6 = this;
            com.android.internal.statusbar.StatusBarIcon r0 = r6.mIcon
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L52
            android.graphics.drawable.Icon r0 = r0.icon
            android.graphics.drawable.Icon r3 = r7.icon
            if (r0 != r3) goto Le
        Lc:
            r0 = r1
            goto L4e
        Le:
            int r4 = r0.getType()
            int r5 = r3.getType()
            if (r4 == r5) goto L1a
        L18:
            r0 = r2
            goto L4e
        L1a:
            int r4 = r0.getType()
            r5 = 2
            if (r4 == r5) goto L35
            r5 = 4
            if (r4 == r5) goto L28
            r5 = 6
            if (r4 == r5) goto L28
            goto L18
        L28:
            java.lang.String r0 = r0.getUriString()
            java.lang.String r3 = r3.getUriString()
            boolean r0 = r0.equals(r3)
            goto L4e
        L35:
            java.lang.String r4 = r0.getResPackage()
            java.lang.String r5 = r3.getResPackage()
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L18
            int r0 = r0.getResId()
            int r3 = r3.getResId()
            if (r0 != r3) goto L18
            goto Lc
        L4e:
            if (r0 == 0) goto L52
            r0 = r1
            goto L53
        L52:
            r0 = r2
        L53:
            if (r0 == 0) goto L5f
            com.android.internal.statusbar.StatusBarIcon r3 = r6.mIcon
            int r3 = r3.iconLevel
            int r4 = r7.iconLevel
            if (r3 != r4) goto L5f
            r3 = r1
            goto L60
        L5f:
            r3 = r2
        L60:
            com.android.internal.statusbar.StatusBarIcon r4 = r6.mIcon
            if (r4 == 0) goto L6c
            boolean r4 = r4.visible
            boolean r5 = r7.visible
            if (r4 != r5) goto L6c
            r4 = r1
            goto L6d
        L6c:
            r4 = r2
        L6d:
            com.android.internal.statusbar.StatusBarIcon r5 = r7.clone()
            r6.mIcon = r5
            java.lang.CharSequence r5 = r7.contentDescription
            r6.setContentDescription(r5)
            if (r0 != 0) goto L8b
            boolean r0 = r6.updateDrawable(r2)
            if (r0 != 0) goto L81
            return r2
        L81:
            r0 = 2131363213(0x7f0a058d, float:1.8346228E38)
            r5 = 0
            r6.setTag(r0, r5)
            r6.maybeUpdateIconScaleDimens()
        L8b:
            if (r3 != 0) goto L92
            int r0 = r7.iconLevel
            r6.setImageLevel(r0)
        L92:
            int r0 = com.android.systemui.modes.shared.ModesUiIcons.$r8$clinit
            if (r4 != 0) goto La4
            boolean r7 = r7.visible
            if (r7 == 0) goto L9f
            boolean r7 = r6.mBlocked
            if (r7 != 0) goto L9f
            goto La1
        L9f:
            r2 = 8
        La1:
            r6.setVisibility(r2)
        La4:
            return r1
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
        float f;
        float abs;
        this.mDrawableColor = i;
        this.mCurrentSetColor = i;
        updateIconColor();
        if (Color.alpha(0) == 255) {
            int i2 = this.mDrawableColor;
            if (!ContrastColorUtil.satisfiesTextContrast(0, i2)) {
                int i3 = this.mDrawableColor;
                ThreadLocal threadLocal = ColorUtils.TEMP_ARRAY;
                float red = Color.red(i3) / 255.0f;
                float green = Color.green(i3) / 255.0f;
                float blue = Color.blue(i3) / 255.0f;
                float max = Math.max(red, Math.max(green, blue));
                float min = Math.min(red, Math.min(green, blue));
                float f2 = max - min;
                float f3 = (max + min) / 2.0f;
                if (max == min) {
                    f = 0.0f;
                    abs = 0.0f;
                } else {
                    f = max == red ? ((green - blue) / f2) % 6.0f : max == green ? ((blue - red) / f2) + 2.0f : ((red - green) / f2) + 4.0f;
                    abs = f2 / (1.0f - Math.abs((2.0f * f3) - 1.0f));
                }
                float f4 = (f * 60.0f) % 360.0f;
                if (f4 < 0.0f) {
                    f4 += 360.0f;
                }
                if (new float[]{f4 < 0.0f ? 0.0f : Math.min(f4, 360.0f), abs < 0.0f ? 0.0f : Math.min(abs, 1.0f), f3 >= 0.0f ? Math.min(f3, 1.0f) : 0.0f}[1] < 0.2f) {
                    i2 = 0;
                }
                ContrastColorUtil.resolveContrastColor(((ImageView) this).mContext, i2, 0, true ^ ContrastColorUtil.isColorLight(0));
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
        try {
            if (this.mIcon == null) {
                return false;
            }
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
        if (this.mShowsConversation || Boolean.TRUE.equals(getTag(R.id.conversation_notification))) {
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
        setNotification(statusBarNotification, statusBarNotification != null ? NotificationContentDescription.contentDescForNotification(((ImageView) this).mContext, statusBarNotification.getNotification()) : null);
        setScaleType(ImageView.ScaleType.CENTER);
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        this.mConfiguration = configuration;
        boolean z2 = (configuration.uiMode & 48) == 32;
        if (this.mNotification != null) {
            setDecorColor(getContext().getColor(z2 ? android.R.color.system_shade_disabled_dark : android.R.color.system_shade_disabled_light));
        }
        reloadDimens$1();
        maybeUpdateIconScaleDimens();
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
