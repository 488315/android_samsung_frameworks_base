package android.widget;

import android.app.AppGlobals;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RemoteViews;
import android.widget.TextClock;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Formatter;
import java.util.Locale;

@RemoteViews.RemoteView
@Deprecated
/* loaded from: classes5.dex */
public class AnalogClock extends View {
    private static final String LOG_TAG = "AnalogClock";
    private boolean mChanged;
    private Clock mClock;
    private TextClock.ClockEventDelegate mClockEventDelegate;
    private Drawable mDial;
    private int mDialHeight;
    private final TintInfo mDialTintInfo;
    private int mDialWidth;
    private float mHour;
    private Drawable mHourHand;
    private final TintInfo mHourHandTintInfo;
    private final BroadcastReceiver mIntentReceiver;
    private Drawable mMinuteHand;
    private final TintInfo mMinuteHandTintInfo;
    private float mMinutes;
    private boolean mReceiverAttached;
    private Drawable mSecondHand;
    private final TintInfo mSecondHandTintInfo;
    private float mSeconds;
    private final int mSecondsHandFps;
    private final Runnable mTick;
    private ZoneId mTimeZone;
    private boolean mVisible;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<AnalogClock> {
        private int mDialTintBlendModeId;
        private int mDialTintListId;
        private int mHourHandTintBlendModeId;
        private int mHourHandTintListId;
        private int mMinuteHandTintBlendModeId;
        private int mMinuteHandTintListId;
        private boolean mPropertiesMapped = false;
        private int mSecondHandTintBlendModeId;
        private int mSecondHandTintListId;
        private int mTimeZoneId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mDialTintBlendModeId = propertyMapper.mapObject("dialTintBlendMode", 6);
            this.mDialTintListId = propertyMapper.mapObject("dialTintList", 5);
            this.mHourHandTintBlendModeId = propertyMapper.mapObject("hourHandTintBlendMode", 8);
            this.mHourHandTintListId = propertyMapper.mapObject("hourHandTintList", 7);
            this.mMinuteHandTintBlendModeId = propertyMapper.mapObject("minuteHandTintBlendMode", 10);
            this.mMinuteHandTintListId = propertyMapper.mapObject("minuteHandTintList", 9);
            this.mSecondHandTintBlendModeId = propertyMapper.mapObject("secondHandTintBlendMode", 12);
            this.mSecondHandTintListId = propertyMapper.mapObject("secondHandTintList", 11);
            this.mTimeZoneId = propertyMapper.mapObject("timeZone", 16843724);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(AnalogClock analogClock, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mDialTintBlendModeId, analogClock.getDialTintBlendMode());
            propertyReader.readObject(this.mDialTintListId, analogClock.getDialTintList());
            propertyReader.readObject(this.mHourHandTintBlendModeId, analogClock.getHourHandTintBlendMode());
            propertyReader.readObject(this.mHourHandTintListId, analogClock.getHourHandTintList());
            propertyReader.readObject(this.mMinuteHandTintBlendModeId, analogClock.getMinuteHandTintBlendMode());
            propertyReader.readObject(this.mMinuteHandTintListId, analogClock.getMinuteHandTintList());
            propertyReader.readObject(this.mSecondHandTintBlendModeId, analogClock.getSecondHandTintBlendMode());
            propertyReader.readObject(this.mSecondHandTintListId, analogClock.getSecondHandTintList());
            propertyReader.readObject(this.mTimeZoneId, analogClock.getTimeZone());
        }
    }

    public AnalogClock(Context context) {
        this(context, null);
    }

    public AnalogClock(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AnalogClock(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AnalogClock(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TintInfo tintInfo = new TintInfo();
        this.mHourHandTintInfo = tintInfo;
        TintInfo tintInfo2 = new TintInfo();
        this.mMinuteHandTintInfo = tintInfo2;
        TintInfo tintInfo3 = new TintInfo();
        this.mSecondHandTintInfo = tintInfo3;
        TintInfo tintInfo4 = new TintInfo();
        this.mDialTintInfo = tintInfo4;
        this.mIntentReceiver = new BroadcastReceiver() { // from class: android.widget.AnalogClock.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
                    AnalogClock.this.createClock();
                }
                AnalogClock.this.mTick.run();
            }
        };
        this.mTick = new Runnable() { // from class: android.widget.AnalogClock.2
            @Override // java.lang.Runnable
            public void run() {
                long millis;
                AnalogClock.this.removeCallbacks(this);
                if (AnalogClock.this.mVisible) {
                    Instant instantNow = AnalogClock.this.now();
                    ZonedDateTime zonedDateTimeAtZone = instantNow.atZone(AnalogClock.this.mClock.getZone());
                    LocalTime localTime = zonedDateTimeAtZone.toLocalTime();
                    if (AnalogClock.this.mSecondHand == null || AnalogClock.this.mSecondsHandFps <= 0) {
                        long millis2 = Duration.between(instantNow, zonedDateTimeAtZone.plusMinutes(1L).withSecond(0).toInstant()).toMillis();
                        millis = millis2 <= 0 ? Duration.ofMinutes(1L).toMillis() : millis2;
                    } else {
                        long millis3 = Duration.ofNanos(localTime.getNano()).toMillis();
                        double d = 1000.0d / AnalogClock.this.mSecondsHandFps;
                        millis = Math.round(d - Math.round(millis3 % d));
                        if (millis <= 0) {
                            millis = Math.round(d);
                        }
                    }
                    AnalogClock.this.postDelayed(this, millis);
                    AnalogClock.this.onTimeChanged(localTime, instantNow.toEpochMilli());
                    AnalogClock.this.invalidate();
                }
            }
        };
        this.mClockEventDelegate = new TextClock.ClockEventDelegate(context);
        this.mSecondsHandFps = AppGlobals.getIntCoreSetting(WidgetFlags.KEY_ANALOG_CLOCK_SECONDS_HAND_FPS, context.getResources().getInteger(R.integer.config_defaultAnalogClockSecondsHandFps));
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AnalogClock, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.AnalogClock, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        this.mDial = drawable;
        if (drawable == null) {
            this.mDial = context.getDrawable(R.drawable.clock_dial);
        }
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(5);
        if (colorStateList != null) {
            tintInfo4.mTintList = colorStateList;
            tintInfo4.mHasTintList = true;
        }
        BlendMode blendMode = Drawable.parseBlendMode(typedArrayObtainStyledAttributes.getInt(6, -1), null);
        if (blendMode != null) {
            tintInfo4.mTintBlendMode = blendMode;
            tintInfo4.mHasTintBlendMode = true;
        }
        if (tintInfo4.mHasTintList || tintInfo4.mHasTintBlendMode) {
            this.mDial = tintInfo4.apply(this.mDial);
        }
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(1);
        this.mHourHand = drawable2;
        if (drawable2 == null) {
            this.mHourHand = context.getDrawable(R.drawable.clock_hand_hour);
        }
        ColorStateList colorStateList2 = typedArrayObtainStyledAttributes.getColorStateList(7);
        if (colorStateList2 != null) {
            tintInfo.mTintList = colorStateList2;
            tintInfo.mHasTintList = true;
        }
        BlendMode blendMode2 = Drawable.parseBlendMode(typedArrayObtainStyledAttributes.getInt(8, -1), null);
        if (blendMode2 != null) {
            tintInfo.mTintBlendMode = blendMode2;
            tintInfo.mHasTintBlendMode = true;
        }
        if (tintInfo.mHasTintList || tintInfo.mHasTintBlendMode) {
            this.mHourHand = tintInfo.apply(this.mHourHand);
        }
        Drawable drawable3 = typedArrayObtainStyledAttributes.getDrawable(2);
        this.mMinuteHand = drawable3;
        if (drawable3 == null) {
            this.mMinuteHand = context.getDrawable(R.drawable.clock_hand_minute);
        }
        ColorStateList colorStateList3 = typedArrayObtainStyledAttributes.getColorStateList(9);
        if (colorStateList3 != null) {
            tintInfo2.mTintList = colorStateList3;
            tintInfo2.mHasTintList = true;
        }
        BlendMode blendMode3 = Drawable.parseBlendMode(typedArrayObtainStyledAttributes.getInt(10, -1), null);
        if (blendMode3 != null) {
            tintInfo2.mTintBlendMode = blendMode3;
            tintInfo2.mHasTintBlendMode = true;
        }
        if (tintInfo2.mHasTintList || tintInfo2.mHasTintBlendMode) {
            this.mMinuteHand = tintInfo2.apply(this.mMinuteHand);
        }
        this.mSecondHand = typedArrayObtainStyledAttributes.getDrawable(4);
        ColorStateList colorStateList4 = typedArrayObtainStyledAttributes.getColorStateList(11);
        if (colorStateList4 != null) {
            tintInfo3.mTintList = colorStateList4;
            tintInfo3.mHasTintList = true;
        }
        BlendMode blendMode4 = Drawable.parseBlendMode(typedArrayObtainStyledAttributes.getInt(12, -1), null);
        if (blendMode4 != null) {
            tintInfo3.mTintBlendMode = blendMode4;
            tintInfo3.mHasTintBlendMode = true;
        }
        if (tintInfo3.mHasTintList || tintInfo3.mHasTintBlendMode) {
            this.mSecondHand = tintInfo3.apply(this.mSecondHand);
        }
        this.mTimeZone = toZoneId(typedArrayObtainStyledAttributes.getString(3));
        createClock();
        typedArrayObtainStyledAttributes.recycle();
        this.mDialWidth = this.mDial.getIntrinsicWidth();
        this.mDialHeight = this.mDial.getIntrinsicHeight();
    }

    @RemotableViewMethod
    public void setDial(Icon icon) throws IOException {
        Drawable drawableLoadDrawable = icon.loadDrawable(getContext());
        this.mDial = drawableLoadDrawable;
        this.mDialWidth = drawableLoadDrawable.getIntrinsicWidth();
        this.mDialHeight = this.mDial.getIntrinsicHeight();
        if (this.mDialTintInfo.mHasTintList || this.mDialTintInfo.mHasTintBlendMode) {
            this.mDial = this.mDialTintInfo.apply(this.mDial);
        }
        this.mChanged = true;
        invalidate();
    }

    @RemotableViewMethod
    public void setDialTintList(ColorStateList colorStateList) {
        this.mDialTintInfo.mTintList = colorStateList;
        this.mDialTintInfo.mHasTintList = true;
        this.mDial = this.mDialTintInfo.apply(this.mDial);
    }

    public ColorStateList getDialTintList() {
        return this.mDialTintInfo.mTintList;
    }

    @RemotableViewMethod
    public void setDialTintBlendMode(BlendMode blendMode) {
        this.mDialTintInfo.mTintBlendMode = blendMode;
        this.mDialTintInfo.mHasTintBlendMode = true;
        this.mDial = this.mDialTintInfo.apply(this.mDial);
    }

    public BlendMode getDialTintBlendMode() {
        return this.mDialTintInfo.mTintBlendMode;
    }

    @RemotableViewMethod
    public void setHourHand(Icon icon) {
        this.mHourHand = icon.loadDrawable(getContext());
        if (this.mHourHandTintInfo.mHasTintList || this.mHourHandTintInfo.mHasTintBlendMode) {
            this.mHourHand = this.mHourHandTintInfo.apply(this.mHourHand);
        }
        this.mChanged = true;
        invalidate();
    }

    @RemotableViewMethod
    public void setHourHandTintList(ColorStateList colorStateList) {
        this.mHourHandTintInfo.mTintList = colorStateList;
        this.mHourHandTintInfo.mHasTintList = true;
        this.mHourHand = this.mHourHandTintInfo.apply(this.mHourHand);
    }

    public ColorStateList getHourHandTintList() {
        return this.mHourHandTintInfo.mTintList;
    }

    @RemotableViewMethod
    public void setHourHandTintBlendMode(BlendMode blendMode) {
        this.mHourHandTintInfo.mTintBlendMode = blendMode;
        this.mHourHandTintInfo.mHasTintBlendMode = true;
        this.mHourHand = this.mHourHandTintInfo.apply(this.mHourHand);
    }

    public BlendMode getHourHandTintBlendMode() {
        return this.mHourHandTintInfo.mTintBlendMode;
    }

    @RemotableViewMethod
    public void setMinuteHand(Icon icon) {
        this.mMinuteHand = icon.loadDrawable(getContext());
        if (this.mMinuteHandTintInfo.mHasTintList || this.mMinuteHandTintInfo.mHasTintBlendMode) {
            this.mMinuteHand = this.mMinuteHandTintInfo.apply(this.mMinuteHand);
        }
        this.mChanged = true;
        invalidate();
    }

    @RemotableViewMethod
    public void setMinuteHandTintList(ColorStateList colorStateList) {
        this.mMinuteHandTintInfo.mTintList = colorStateList;
        this.mMinuteHandTintInfo.mHasTintList = true;
        this.mMinuteHand = this.mMinuteHandTintInfo.apply(this.mMinuteHand);
    }

    public ColorStateList getMinuteHandTintList() {
        return this.mMinuteHandTintInfo.mTintList;
    }

    @RemotableViewMethod
    public void setMinuteHandTintBlendMode(BlendMode blendMode) {
        this.mMinuteHandTintInfo.mTintBlendMode = blendMode;
        this.mMinuteHandTintInfo.mHasTintBlendMode = true;
        this.mMinuteHand = this.mMinuteHandTintInfo.apply(this.mMinuteHand);
    }

    public BlendMode getMinuteHandTintBlendMode() {
        return this.mMinuteHandTintInfo.mTintBlendMode;
    }

    @RemotableViewMethod
    public void setSecondHand(Icon icon) {
        this.mSecondHand = icon == null ? null : icon.loadDrawable(getContext());
        if (this.mSecondHandTintInfo.mHasTintList || this.mSecondHandTintInfo.mHasTintBlendMode) {
            this.mSecondHand = this.mSecondHandTintInfo.apply(this.mSecondHand);
        }
        this.mTick.run();
        this.mChanged = true;
        invalidate();
    }

    @RemotableViewMethod
    public void setSecondHandTintList(ColorStateList colorStateList) {
        this.mSecondHandTintInfo.mTintList = colorStateList;
        this.mSecondHandTintInfo.mHasTintList = true;
        this.mSecondHand = this.mSecondHandTintInfo.apply(this.mSecondHand);
    }

    public ColorStateList getSecondHandTintList() {
        return this.mSecondHandTintInfo.mTintList;
    }

    @RemotableViewMethod
    public void setSecondHandTintBlendMode(BlendMode blendMode) {
        this.mSecondHandTintInfo.mTintBlendMode = blendMode;
        this.mSecondHandTintInfo.mHasTintBlendMode = true;
        this.mSecondHand = this.mSecondHandTintInfo.apply(this.mSecondHand);
    }

    public BlendMode getSecondHandTintBlendMode() {
        return this.mSecondHandTintInfo.mTintBlendMode;
    }

    public String getTimeZone() {
        ZoneId zoneId = this.mTimeZone;
        if (zoneId == null) {
            return null;
        }
        return zoneId.getId();
    }

    @RemotableViewMethod
    public void setTimeZone(String str) {
        this.mTimeZone = toZoneId(str);
        createClock();
        onTimeChanged();
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        if (z) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mReceiverAttached) {
            this.mClockEventDelegate.registerTimeChangeReceiver(this.mIntentReceiver, getHandler());
            this.mReceiverAttached = true;
        }
        createClock();
        onTimeChanged();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        if (this.mReceiverAttached) {
            this.mClockEventDelegate.unregisterTimeChangeReceiver(this.mIntentReceiver);
            this.mReceiverAttached = false;
        }
        super.onDetachedFromWindow();
    }

    public void setClockEventDelegate(TextClock.ClockEventDelegate clockEventDelegate) {
        Preconditions.checkState(!this.mReceiverAttached, "Clock events already registered");
        this.mClockEventDelegate = clockEventDelegate;
    }

    private void onVisible() {
        if (this.mVisible) {
            return;
        }
        this.mVisible = true;
        this.mTick.run();
    }

    private void onInvisible() {
        if (this.mVisible) {
            removeCallbacks(this.mTick);
            this.mVisible = false;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        float f = 1.0f;
        float f2 = (mode == 0 || size >= (i4 = this.mDialWidth)) ? 1.0f : size / i4;
        if (mode2 != 0 && size2 < (i3 = this.mDialHeight)) {
            f = size2 / i3;
        }
        float fMin = Math.min(f2, f);
        setMeasuredDimension(resolveSizeAndState((int) (this.mDialWidth * fMin), i, 0), resolveSizeAndState((int) (this.mDialHeight * fMin), i2, 0));
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mChanged = true;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boolean z = this.mChanged;
        boolean z2 = false;
        if (z) {
            this.mChanged = false;
        }
        int i = this.mRight - this.mLeft;
        int i2 = this.mBottom - this.mTop;
        int i3 = i / 2;
        int i4 = i2 / 2;
        Drawable drawable = this.mDial;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (i < intrinsicWidth || i2 < intrinsicHeight) {
            float fMin = Math.min(i / intrinsicWidth, i2 / intrinsicHeight);
            canvas.save();
            canvas.scale(fMin, fMin, i3, i4);
            z2 = true;
        }
        if (z) {
            int i5 = intrinsicWidth / 2;
            int i6 = intrinsicHeight / 2;
            drawable.setBounds(i3 - i5, i4 - i6, i5 + i3, i6 + i4);
        }
        drawable.draw(canvas);
        canvas.save();
        float f = i3;
        float f2 = i4;
        canvas.rotate((this.mHour / 12.0f) * 360.0f, f, f2);
        Drawable drawable2 = this.mHourHand;
        if (z) {
            int intrinsicWidth2 = drawable2.getIntrinsicWidth() / 2;
            int intrinsicHeight2 = drawable2.getIntrinsicHeight() / 2;
            drawable2.setBounds(i3 - intrinsicWidth2, i4 - intrinsicHeight2, intrinsicWidth2 + i3, intrinsicHeight2 + i4);
        }
        drawable2.draw(canvas);
        canvas.restore();
        canvas.save();
        canvas.rotate((this.mMinutes / 60.0f) * 360.0f, f, f2);
        Drawable drawable3 = this.mMinuteHand;
        if (z) {
            int intrinsicWidth3 = drawable3.getIntrinsicWidth() / 2;
            int intrinsicHeight3 = drawable3.getIntrinsicHeight() / 2;
            drawable3.setBounds(i3 - intrinsicWidth3, i4 - intrinsicHeight3, intrinsicWidth3 + i3, intrinsicHeight3 + i4);
        }
        drawable3.draw(canvas);
        canvas.restore();
        Drawable drawable4 = this.mSecondHand;
        if (drawable4 != null && this.mSecondsHandFps > 0) {
            canvas.save();
            canvas.rotate((this.mSeconds / 60.0f) * 360.0f, f, f2);
            if (z) {
                int intrinsicWidth4 = drawable4.getIntrinsicWidth() / 2;
                int intrinsicHeight4 = drawable4.getIntrinsicHeight() / 2;
                drawable4.setBounds(i3 - intrinsicWidth4, i4 - intrinsicHeight4, i3 + intrinsicWidth4, i4 + intrinsicHeight4);
            }
            drawable4.draw(canvas);
            canvas.restore();
        }
        if (z2) {
            canvas.restore();
        }
    }

    protected Instant now() {
        return this.mClock.instant();
    }

    protected void onTimeChanged() {
        Instant instantNow = now();
        onTimeChanged(instantNow.atZone(this.mClock.getZone()).toLocalTime(), instantNow.toEpochMilli());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTimeChanged(LocalTime localTime, long j) {
        float f = this.mHour;
        float f2 = this.mMinutes;
        float second = localTime.getSecond() + (localTime.getNano() / 1.0E9f);
        if (this.mSecondsHandFps > 0) {
            second = Math.round(second * r3) / this.mSecondsHandFps;
        }
        this.mSeconds = second;
        this.mMinutes = localTime.getMinute() + (this.mSeconds / 60.0f);
        float hour = localTime.getHour();
        float f3 = this.mMinutes;
        float f4 = hour + (f3 / 60.0f);
        this.mHour = f4;
        this.mChanged = true;
        if (((int) f) == ((int) f4) && ((int) f2) == ((int) f3)) {
            return;
        }
        updateContentDescription(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createClock() {
        ZoneId zoneId = this.mTimeZone;
        if (zoneId == null) {
            this.mClock = Clock.systemDefaultZone();
        } else {
            this.mClock = Clock.system(zoneId);
        }
    }

    private void updateContentDescription(long j) {
        setContentDescription(DateUtils.formatDateRange(this.mContext, new Formatter(new StringBuilder(50), Locale.getDefault()), j, j, 129, getTimeZone()).toString());
    }

    private static ZoneId toZoneId(String str) {
        if (str == null) {
            return null;
        }
        try {
            return ZoneId.of(str);
        } catch (DateTimeException e) {
            Log.w(LOG_TAG, "Failed to parse time zone from " + str, e);
            return null;
        }
    }

    private final class TintInfo {
        boolean mHasTintBlendMode;
        boolean mHasTintList;
        BlendMode mTintBlendMode;
        ColorStateList mTintList;

        private TintInfo() {
        }

        Drawable apply(Drawable drawable) {
            if (drawable == null) {
                return null;
            }
            Drawable drawableMutate = drawable.mutate();
            if (this.mHasTintList) {
                drawableMutate.setTintList(this.mTintList);
            }
            if (this.mHasTintBlendMode) {
                drawableMutate.setTintBlendMode(this.mTintBlendMode);
            }
            if (drawable.isStateful()) {
                drawableMutate.setState(AnalogClock.this.getDrawableState());
            }
            return drawableMutate;
        }
    }
}
