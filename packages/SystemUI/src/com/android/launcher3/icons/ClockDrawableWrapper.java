package com.android.launcher3.icons;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.launcher3.icons.mono.ThemedIconDrawable;
import com.android.systemui.R;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class ClockDrawableWrapper extends AdaptiveIconDrawable implements BitmapInfo.Extender {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long TICK_MS = TimeUnit.MINUTES.toMillis(1);
    public final AnimationInfo mAnimationInfo;
    public AnimationInfo mThemeInfo;

    public class AnimationInfo {
        public Drawable.ConstantState baseDrawableState;
        public int defaultHour;
        public int defaultMinute;
        public int defaultSecond;
        public int hourLayerIndex;
        public int minuteLayerIndex;
        public int secondLayerIndex;

        public /* synthetic */ AnimationInfo(int i) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0046  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean applyTime(Calendar calendar, LayerDrawable layerDrawable) {
            boolean z;
            calendar.setTimeInMillis(System.currentTimeMillis());
            int i = ((12 - this.defaultHour) + calendar.get(10)) % 12;
            int i2 = ((60 - this.defaultMinute) + calendar.get(12)) % 60;
            int i3 = ((60 - this.defaultSecond) + calendar.get(13)) % 60;
            int i4 = this.hourLayerIndex;
            if (i4 != -1) {
                z = layerDrawable.getDrawable(i4).setLevel(calendar.get(12) + (i * 60));
            }
            int i5 = this.minuteLayerIndex;
            if (i5 != -1 && layerDrawable.getDrawable(i5).setLevel((calendar.get(10) * 60) + i2)) {
                z = true;
            }
            int i6 = this.secondLayerIndex;
            if (i6 == -1 || !layerDrawable.getDrawable(i6).setLevel(i3 * 10)) {
                return z;
            }
            return true;
        }

        public final AnimationInfo copyForIcon(Drawable drawable) {
            AnimationInfo animationInfo = new AnimationInfo();
            animationInfo.baseDrawableState = drawable.getConstantState();
            animationInfo.defaultHour = this.defaultHour;
            animationInfo.defaultMinute = this.defaultMinute;
            animationInfo.defaultSecond = this.defaultSecond;
            animationInfo.hourLayerIndex = this.hourLayerIndex;
            animationInfo.minuteLayerIndex = this.minuteLayerIndex;
            animationInfo.secondLayerIndex = this.secondLayerIndex;
            return animationInfo;
        }

        private AnimationInfo() {
        }
    }

    public class ClockBitmapInfo extends BitmapInfo {
        public final AnimationInfo animInfo;
        public final float boundsOffset;
        public final Bitmap mFlattenedBackground;
        public final Bitmap themeBackground;
        public final AnimationInfo themeData;

        public ClockBitmapInfo(Bitmap bitmap, int i, float f, AnimationInfo animationInfo, Bitmap bitmap2, AnimationInfo animationInfo2, Bitmap bitmap3) {
            super(bitmap, i);
            this.boundsOffset = Math.max(0.035f, (1.0f - f) / 2.0f);
            this.animInfo = animationInfo;
            this.mFlattenedBackground = bitmap2;
            this.themeData = animationInfo2;
            this.themeBackground = bitmap3;
        }

        @Override // com.android.launcher3.icons.BitmapInfo
        public final FastBitmapDrawable newIcon$1(int i, Context context) {
            Bitmap bitmap;
            AnimationInfo animationInfoCopyForIcon;
            int i2;
            BlendModeColorFilter blendModeColorFilter;
            AnimationInfo animationInfo;
            if ((i & 1) == 0 || (animationInfo = this.themeData) == null) {
                bitmap = this.mFlattenedBackground;
                animationInfoCopyForIcon = this.animInfo;
                i2 = -1;
                blendModeColorFilter = null;
            } else {
                ThemedIconDrawable.Companion.getClass();
                Resources resources = context.getResources();
                int[] iArr = {resources.getColor(R.color.themed_icon_background_color), resources.getColor(R.color.themed_icon_color)};
                Drawable drawableMutate = animationInfo.baseDrawableState.newDrawable().mutate();
                i2 = iArr[1];
                drawableMutate.setTint(i2);
                animationInfoCopyForIcon = animationInfo.copyForIcon(drawableMutate);
                bitmap = this.themeBackground;
                blendModeColorFilter = new BlendModeColorFilter(iArr[0], BlendMode.SRC_IN);
            }
            int i3 = i2;
            AnimationInfo animationInfo2 = animationInfoCopyForIcon;
            Bitmap bitmap2 = bitmap;
            if (animationInfo2 == null) {
                return newIcon$1(i, context);
            }
            ClockIconDrawable.ClockConstantState clockConstantState = new ClockIconDrawable.ClockConstantState(this, i3, this.boundsOffset, animationInfo2, bitmap2, blendModeColorFilter);
            FastBitmapDrawable fastBitmapDrawableCreateDrawable = clockConstantState.createDrawable();
            Drawable.ConstantState constantState = clockConstantState.mBadgeConstantState;
            if (constantState != null) {
                Drawable drawableNewDrawable = constantState.newDrawable();
                Drawable drawable = fastBitmapDrawableCreateDrawable.mBadge;
                if (drawable != null) {
                    drawable.setCallback(null);
                }
                fastBitmapDrawableCreateDrawable.mBadge = drawableNewDrawable;
                if (drawableNewDrawable != null) {
                    drawableNewDrawable.setCallback(fastBitmapDrawableCreateDrawable);
                }
                Rect bounds = fastBitmapDrawableCreateDrawable.getBounds();
                Drawable drawable2 = fastBitmapDrawableCreateDrawable.mBadge;
                if (drawable2 != null) {
                    int iWidth = bounds.width();
                    float f = BaseIconFactory.LEGACY_ICON_SCALE;
                    int i4 = (int) (iWidth * 0.444f);
                    int i5 = bounds.right;
                    int i6 = bounds.bottom;
                    drawable2.setBounds(i5 - i4, i6 - i4, i5, i6);
                }
                fastBitmapDrawableCreateDrawable.updateFilter();
            }
            fastBitmapDrawableCreateDrawable.mCreationFlags = clockConstantState.mCreationFlags;
            applyFlags(context, fastBitmapDrawableCreateDrawable, i);
            return fastBitmapDrawableCreateDrawable;
        }

        @Override // com.android.launcher3.icons.BitmapInfo
        /* renamed from: clone */
        public final BitmapInfo mo974clone() {
            ClockBitmapInfo clockBitmapInfo = new ClockBitmapInfo(this.icon, this.color, 1.0f - (this.boundsOffset * 2.0f), this.animInfo, this.mFlattenedBackground, this.themeData, this.themeBackground);
            clockBitmapInfo.flags = this.flags;
            clockBitmapInfo.badgeInfo = this.badgeInfo;
            return clockBitmapInfo;
        }
    }

    public class ClockIconDrawable extends FastBitmapDrawable implements Runnable {
        public final AnimationInfo mAnimInfo;
        public final Bitmap mBG;
        public final ColorFilter mBgFilter;
        public final Paint mBgPaint;
        public final float mBoundsOffset;
        public final float mCanvasScale;
        public final LayerDrawable mFG;
        public final AdaptiveIconDrawable mFullDrawable;
        public final int mThemedFgColor;
        public final Calendar mTime;

        public class ClockConstantState extends FastBitmapDrawable.FastBitmapConstantState {
            public final AnimationInfo mAnimInfo;
            public final Bitmap mBG;
            public final ColorFilter mBgFilter;
            public final float mBoundsOffset;
            public final int mThemedFgColor;

            public ClockConstantState(BitmapInfo bitmapInfo, int i, float f, AnimationInfo animationInfo, Bitmap bitmap, ColorFilter colorFilter) {
                super(bitmapInfo);
                this.mBoundsOffset = f;
                this.mAnimInfo = animationInfo;
                this.mBG = bitmap;
                this.mBgFilter = colorFilter;
                this.mThemedFgColor = i;
            }

            @Override // com.android.launcher3.icons.FastBitmapDrawable.FastBitmapConstantState
            public final FastBitmapDrawable createDrawable() {
                return new ClockIconDrawable(this);
            }
        }

        public ClockIconDrawable(ClockConstantState clockConstantState) {
            super(clockConstantState.mBitmapInfo);
            Calendar calendar = Calendar.getInstance();
            this.mTime = calendar;
            Paint paint = new Paint(3);
            this.mBgPaint = paint;
            float f = clockConstantState.mBoundsOffset;
            this.mBoundsOffset = f;
            AnimationInfo animationInfo = clockConstantState.mAnimInfo;
            this.mAnimInfo = animationInfo;
            this.mBG = clockConstantState.mBG;
            ColorFilter colorFilter = clockConstantState.mBgFilter;
            this.mBgFilter = colorFilter;
            paint.setColorFilter(colorFilter);
            this.mThemedFgColor = clockConstantState.mThemedFgColor;
            AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) animationInfo.baseDrawableState.newDrawable().mutate();
            this.mFullDrawable = adaptiveIconDrawable;
            LayerDrawable layerDrawable = (LayerDrawable) adaptiveIconDrawable.getForeground();
            this.mFG = layerDrawable;
            animationInfo.applyTime(calendar, layerDrawable);
            this.mCanvasScale = 1.0f - (f * 2.0f);
        }

        @Override // com.android.launcher3.icons.FastBitmapDrawable
        public final void drawInternal(Canvas canvas, Rect rect) {
            if (this.mAnimInfo == null) {
                super.drawInternal(canvas, rect);
                return;
            }
            canvas.drawBitmap(this.mBG, (Rect) null, rect, this.mBgPaint);
            this.mAnimInfo.applyTime(this.mTime, this.mFG);
            int iSave = canvas.save();
            canvas.translate(rect.left, rect.top);
            float f = this.mCanvasScale;
            canvas.scale(f, f, rect.width() / 2, rect.height() / 2);
            canvas.clipPath(this.mFullDrawable.getIconMask());
            this.mFG.draw(canvas);
            canvas.restoreToCount(iSave);
            reschedule();
        }

        @Override // com.android.launcher3.icons.FastBitmapDrawable
        public final FastBitmapDrawable.FastBitmapConstantState newConstantState() {
            return new ClockConstantState(this.mBitmapInfo, this.mThemedFgColor, this.mBoundsOffset, this.mAnimInfo, this.mBG, this.mBgPaint.getColorFilter());
        }

        @Override // com.android.launcher3.icons.FastBitmapDrawable, android.graphics.drawable.Drawable
        public final void onBoundsChange(Rect rect) {
            super.onBoundsChange(rect);
            this.mFullDrawable.setBounds(0, 0, rect.width(), rect.height());
        }

        public final void reschedule() {
            if (isVisible()) {
                unscheduleSelf(this);
                long jUptimeMillis = SystemClock.uptimeMillis();
                long j = ClockDrawableWrapper.TICK_MS;
                scheduleSelf(this, (jUptimeMillis - (jUptimeMillis % j)) + j);
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (this.mAnimInfo.applyTime(this.mTime, this.mFG)) {
                invalidateSelf();
            } else {
                reschedule();
            }
        }

        @Override // com.android.launcher3.icons.FastBitmapDrawable, android.graphics.drawable.Drawable
        public final void setAlpha(int i) {
            super.setAlpha(i);
            this.mBgPaint.setAlpha(i);
            this.mFG.setAlpha(i);
        }

        @Override // android.graphics.drawable.Drawable
        public final boolean setVisible(boolean z, boolean z2) {
            boolean visible = super.setVisible(z, z2);
            if (z) {
                reschedule();
                return visible;
            }
            unscheduleSelf(this);
            return visible;
        }

        @Override // com.android.launcher3.icons.FastBitmapDrawable
        public final void updateFilter() {
            super.updateFilter();
            setAlpha(255);
            this.mBgPaint.setColorFilter(this.mBgFilter);
            this.mFG.setColorFilter(null);
        }
    }

    private ClockDrawableWrapper(AdaptiveIconDrawable adaptiveIconDrawable) {
        super(adaptiveIconDrawable.getBackground(), adaptiveIconDrawable.getForeground());
        this.mAnimationInfo = new AnimationInfo(0);
        this.mThemeInfo = null;
    }

    public static ClockDrawableWrapper forExtras(Bundle bundle, ClockDrawableWrapper$$ExternalSyntheticLambda0 clockDrawableWrapper$$ExternalSyntheticLambda0) {
        int i;
        if (bundle != null && (i = bundle.getInt("com.android.launcher3.LEVEL_PER_TICK_ICON_ROUND", 0)) != 0) {
            Drawable drawableMutate = ((Drawable) clockDrawableWrapper$$ExternalSyntheticLambda0.apply(i)).mutate();
            if (drawableMutate instanceof AdaptiveIconDrawable) {
                AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawableMutate;
                ClockDrawableWrapper clockDrawableWrapper = new ClockDrawableWrapper(adaptiveIconDrawable);
                AnimationInfo animationInfo = clockDrawableWrapper.mAnimationInfo;
                animationInfo.baseDrawableState = drawableMutate.getConstantState();
                animationInfo.hourLayerIndex = bundle.getInt("com.android.launcher3.HOUR_LAYER_INDEX", -1);
                animationInfo.minuteLayerIndex = bundle.getInt("com.android.launcher3.MINUTE_LAYER_INDEX", -1);
                animationInfo.secondLayerIndex = bundle.getInt("com.android.launcher3.SECOND_LAYER_INDEX", -1);
                animationInfo.defaultHour = bundle.getInt("com.android.launcher3.DEFAULT_HOUR", 0);
                animationInfo.defaultMinute = bundle.getInt("com.android.launcher3.DEFAULT_MINUTE", 0);
                animationInfo.defaultSecond = bundle.getInt("com.android.launcher3.DEFAULT_SECOND", 0);
                LayerDrawable layerDrawable = (LayerDrawable) clockDrawableWrapper.getForeground();
                int numberOfLayers = layerDrawable.getNumberOfLayers();
                int i2 = animationInfo.hourLayerIndex;
                if (i2 < 0 || i2 >= numberOfLayers) {
                    animationInfo.hourLayerIndex = -1;
                }
                int i3 = animationInfo.minuteLayerIndex;
                if (i3 < 0 || i3 >= numberOfLayers) {
                    animationInfo.minuteLayerIndex = -1;
                }
                int i4 = animationInfo.secondLayerIndex;
                if (i4 < 0 || i4 >= numberOfLayers) {
                    animationInfo.secondLayerIndex = -1;
                } else {
                    layerDrawable.setDrawable(i4, null);
                    animationInfo.secondLayerIndex = -1;
                }
                if (IconProvider.ATLEAST_T && (adaptiveIconDrawable.getMonochrome() instanceof LayerDrawable)) {
                    clockDrawableWrapper.mThemeInfo = animationInfo.copyForIcon(new AdaptiveIconDrawable(new ColorDrawable(-1), adaptiveIconDrawable.getMonochrome().mutate()));
                }
                animationInfo.applyTime(Calendar.getInstance(), layerDrawable);
                return clockDrawableWrapper;
            }
        }
        return null;
    }

    @Override // android.graphics.drawable.AdaptiveIconDrawable
    public final Drawable getMonochrome() {
        AnimationInfo animationInfo = this.mThemeInfo;
        if (animationInfo == null) {
            return null;
        }
        Drawable drawableMutate = animationInfo.baseDrawableState.newDrawable().mutate();
        if (!(drawableMutate instanceof AdaptiveIconDrawable)) {
            return null;
        }
        Drawable foreground = ((AdaptiveIconDrawable) drawableMutate).getForeground();
        this.mThemeInfo.applyTime(Calendar.getInstance(), (LayerDrawable) foreground);
        return foreground;
    }
}
