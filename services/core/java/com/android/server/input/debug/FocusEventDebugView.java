package com.android.server.input.debug;

import android.R;
import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.Slog;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.AccelerateInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.input.InputManagerService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FocusEventDebugView extends RelativeLayout {
    public final DisplayMetrics mDm;
    public FocusEventDebugGlobalMonitor mFocusEventDebugGlobalMonitor;
    public final int mOuterPadding;
    public PressedKeyContainer mPressedKeyContainer;
    public final Map mPressedKeys;
    public PressedKeyContainer mPressedModifierContainer;
    public RotaryInputGraphView mRotaryInputGraphView;
    public final Supplier mRotaryInputGraphViewFactory;
    public RotaryInputValueView mRotaryInputValueView;
    public final Supplier mRotaryInputValueViewFactory;
    public final InputManagerService mService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PressedKeyContainer extends LinearLayout {
        public final ViewGroup.MarginLayoutParams mPressedKeyLayoutParams;

        public PressedKeyContainer(Context context) {
            super(context);
            int applyDimension =
                    (int)
                            TypedValue.applyDimension(
                                    1, 16.0f, context.getResources().getDisplayMetrics());
            LayoutTransition layoutTransition = new LayoutTransition();
            layoutTransition.disableTransitionType(2);
            layoutTransition.disableTransitionType(3);
            layoutTransition.disableTransitionType(1);
            layoutTransition.setDuration(100L);
            setLayoutTransition(layoutTransition);
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    new ViewGroup.MarginLayoutParams(-2, -2);
            this.mPressedKeyLayoutParams = marginLayoutParams;
            if (getOrientation() == 1) {
                marginLayoutParams.setMargins(0, applyDimension, 0, 0);
            } else {
                marginLayoutParams.setMargins(applyDimension, 0, 0, 0);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PressedKeyView extends TextView {
        public static final ColorFilter sInvertColors =
                new ColorMatrixColorFilter(
                        new float[] {
                            -1.0f,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            255.0f,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            -1.0f,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            255.0f,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            -1.0f,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            255.0f,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            1.0f,
                            FullScreenMagnificationGestureHandler.MAX_SCALE
                        });

        public final void setHighlighted(boolean z) {
            if (z) {
                setTextColor(-16777216);
                getBackground().setColorFilter(sInvertColors);
            } else {
                setTextColor(-1);
                getBackground().clearColorFilter();
            }
            invalidate();
        }
    }

    public static void $r8$lambda$NOfikC3SqQwvKrlgqWnqfisz50A(
            FocusEventDebugView focusEventDebugView, boolean z) {
        View view = focusEventDebugView.mPressedKeyContainer;
        if (z == (view != null)) {
            return;
        }
        if (!z) {
            focusEventDebugView.removeView(view);
            focusEventDebugView.mPressedKeyContainer = null;
            focusEventDebugView.removeView(focusEventDebugView.mPressedModifierContainer);
            focusEventDebugView.mPressedModifierContainer = null;
            return;
        }
        PressedKeyContainer pressedKeyContainer =
                new PressedKeyContainer(((RelativeLayout) focusEventDebugView).mContext);
        focusEventDebugView.mPressedKeyContainer = pressedKeyContainer;
        pressedKeyContainer.setOrientation(0);
        focusEventDebugView.mPressedKeyContainer.setGravity(85);
        focusEventDebugView.mPressedKeyContainer.setLayoutDirection(0);
        final HorizontalScrollView horizontalScrollView =
                new HorizontalScrollView(((RelativeLayout) focusEventDebugView).mContext);
        horizontalScrollView.addView(focusEventDebugView.mPressedKeyContainer);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        horizontalScrollView.addOnLayoutChangeListener(
                new View
                        .OnLayoutChangeListener() { // from class:
                                                    // com.android.server.input.debug.FocusEventDebugView$$ExternalSyntheticLambda5
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(
                            View view2,
                            int i,
                            int i2,
                            int i3,
                            int i4,
                            int i5,
                            int i6,
                            int i7,
                            int i8) {
                        horizontalScrollView.fullScroll(66);
                    }
                });
        horizontalScrollView.setHorizontalFadingEdgeEnabled(true);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(12);
        layoutParams.addRule(11);
        focusEventDebugView.addView(horizontalScrollView, layoutParams);
        PressedKeyContainer pressedKeyContainer2 =
                new PressedKeyContainer(((RelativeLayout) focusEventDebugView).mContext);
        focusEventDebugView.mPressedModifierContainer = pressedKeyContainer2;
        pressedKeyContainer2.setOrientation(1);
        focusEventDebugView.mPressedModifierContainer.setGravity(83);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(12);
        layoutParams2.addRule(9);
        layoutParams2.addRule(0, horizontalScrollView.getId());
        focusEventDebugView.addView(focusEventDebugView.mPressedModifierContainer, layoutParams2);
    }

    public FocusEventDebugView(
            Context context,
            InputManagerService inputManagerService,
            Supplier supplier,
            Supplier supplier2) {
        super(context);
        this.mPressedKeys = new HashMap();
        setFocusableInTouchMode(true);
        this.mService = inputManagerService;
        this.mRotaryInputValueViewFactory = supplier;
        this.mRotaryInputGraphViewFactory = supplier2;
        DisplayMetrics displayMetrics =
                ((RelativeLayout) this).mContext.getResources().getDisplayMetrics();
        this.mDm = displayMetrics;
        this.mOuterPadding = (int) TypedValue.applyDimension(1, 16.0f, displayMetrics);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        handleKeyEvent(keyEvent);
        return super.dispatchKeyEvent(keyEvent);
    }

    public final void handleKeyEvent(KeyEvent keyEvent) {
        String str;
        if (this.mPressedKeyContainer != null) {
            Pair pair =
                    new Pair(
                            Integer.valueOf(keyEvent.getDeviceId()),
                            Integer.valueOf(keyEvent.getScanCode()));
            final PressedKeyContainer pressedKeyContainer =
                    KeyEvent.isModifierKey(keyEvent.getKeyCode())
                            ? this.mPressedModifierContainer
                            : this.mPressedKeyContainer;
            PressedKeyView pressedKeyView =
                    (PressedKeyView) ((HashMap) this.mPressedKeys).get(pair);
            int action = keyEvent.getAction();
            if (action != 0) {
                if (action == 1) {
                    if (pressedKeyView == null) {
                        Slog.w(
                                "FocusEventDebugView",
                                "Got key up for "
                                        + KeyEvent.keyCodeToString(keyEvent.getKeyCode())
                                        + " that was not tracked as being down.");
                    } else {
                        ((HashMap) this.mPressedKeys).remove(pair);
                        pressedKeyContainer.getClass();
                        pressedKeyView.setHighlighted(false);
                        pressedKeyView.clearAnimation();
                        pressedKeyView
                                .animate()
                                .alpha(FullScreenMagnificationGestureHandler.MAX_SCALE)
                                .setDuration(1000L)
                                .setInterpolator(new AccelerateInterpolator())
                                .withEndAction(
                                        new Runnable() { // from class:
                                                         // com.android.server.input.debug.FocusEventDebugView$PressedKeyContainer$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                FocusEventDebugView.PressedKeyContainer
                                                        pressedKeyContainer2 =
                                                                FocusEventDebugView
                                                                        .PressedKeyContainer.this;
                                                int i = 0;
                                                for (int i2 = 0;
                                                        i2 < pressedKeyContainer2.getChildCount();
                                                        i2++) {
                                                    View childAt =
                                                            pressedKeyContainer2.getChildAt(i2);
                                                    if (childAt.getAlpha()
                                                            != FullScreenMagnificationGestureHandler
                                                                    .MAX_SCALE) {
                                                        break;
                                                    }
                                                    childAt.setVisibility(8);
                                                    childAt.clearAnimation();
                                                    i++;
                                                }
                                                pressedKeyContainer2.removeViews(0, i);
                                                pressedKeyContainer2.invalidate();
                                            }
                                        })
                                .start();
                    }
                }
            } else if (pressedKeyView == null) {
                Context context = ((RelativeLayout) this).mContext;
                int keyCode = keyEvent.getKeyCode();
                if (keyCode == 3) {
                    str = "◯";
                } else if (keyCode == 4) {
                    str = "◁";
                } else if (keyCode == 61) {
                    str = "⇥";
                } else if (keyCode != 62) {
                    if (keyCode != 66) {
                        if (keyCode == 67) {
                            str = "⌫";
                        } else if (keyCode == 85) {
                            str = "⏯";
                        } else if (keyCode != 160) {
                            if (keyCode == 312) {
                                str = "□";
                            } else if (keyCode == 111) {
                                str = "esc";
                            } else if (keyCode != 112) {
                                switch (keyCode) {
                                    case 19:
                                        str = "↑";
                                        break;
                                    case 20:
                                        str = "↓";
                                        break;
                                    case 21:
                                        str = "←";
                                        break;
                                    case 22:
                                        str = "→";
                                        break;
                                    default:
                                        switch (keyCode) {
                                            case 268:
                                                str = "↖";
                                                break;
                                            case 269:
                                                str = "↙";
                                                break;
                                            case FrameworkStatsLog
                                                    .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_AI_CLEAR_ZOOM_MERGE_ZSL_ANCHOR_6 /* 270 */:
                                                str = "↗";
                                                break;
                                            case FrameworkStatsLog
                                                    .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_AI_CLEAR_ZOOM_MERGE_OIS_ANCHOR_6 /* 271 */:
                                                str = "↘";
                                                break;
                                            default:
                                                int unicodeChar = keyEvent.getUnicodeChar();
                                                if (unicodeChar == 0) {
                                                    str =
                                                            KeyEvent.keyCodeToString(
                                                                    keyEvent.getKeyCode());
                                                    if (str.startsWith("KEYCODE_")) {
                                                        str = str.substring(8);
                                                        break;
                                                    }
                                                } else if ((Integer.MIN_VALUE & unicodeChar) == 0) {
                                                    str = String.valueOf((char) unicodeChar);
                                                    break;
                                                } else {
                                                    str =
                                                            "◌"
                                                                    + String.valueOf(
                                                                            (char)
                                                                                    KeyCharacterMap
                                                                                            .getCombiningChar(
                                                                                                    unicodeChar
                                                                                                            & Integer
                                                                                                                    .MAX_VALUE));
                                                    break;
                                                }
                                                break;
                                        }
                                }
                            } else {
                                str = "⌦";
                            }
                        }
                    }
                    str = "⏎";
                } else {
                    str = "␣";
                }
                PressedKeyView pressedKeyView2 = new PressedKeyView(context);
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                int applyDimension = (int) TypedValue.applyDimension(1, 16.0f, displayMetrics);
                int applyDimension2 = (int) TypedValue.applyDimension(1, 8.0f, displayMetrics);
                int applyDimension3 = (int) TypedValue.applyDimension(1, 32.0f, displayMetrics);
                int applyDimension4 = (int) TypedValue.applyDimension(2, 12.0f, displayMetrics);
                pressedKeyView2.setText(str);
                pressedKeyView2.setGravity(17);
                pressedKeyView2.setMinimumWidth(applyDimension3);
                pressedKeyView2.setTextSize(applyDimension4);
                pressedKeyView2.setTypeface(Typeface.SANS_SERIF);
                pressedKeyView2.setBackgroundResource(R.drawable.ic_ab_back_holo_light_am);
                pressedKeyView2.setPaddingRelative(
                        applyDimension, applyDimension2, applyDimension, applyDimension2);
                pressedKeyView2.setHighlighted(true);
                ((HashMap) this.mPressedKeys).put(pair, pressedKeyView2);
                pressedKeyContainer.addView(
                        pressedKeyView2,
                        pressedKeyContainer.getChildCount(),
                        pressedKeyContainer.mPressedKeyLayoutParams);
                pressedKeyContainer.invalidate();
            } else if (keyEvent.getRepeatCount() == 0) {
                Slog.w(
                        "FocusEventDebugView",
                        "Got key down for "
                                + KeyEvent.keyCodeToString(keyEvent.getKeyCode())
                                + " that was already tracked as being down.");
            } else {
                pressedKeyContainer.getClass();
            }
            keyEvent.recycle();
        }
    }

    public void handleRotaryInput(MotionEvent motionEvent) {
        RotaryInputGraphView.CyclicBuffer cyclicBuffer;
        int i;
        RotaryInputGraphView.GraphValue[] graphValueArr;
        if (this.mRotaryInputValueView != null) {
            float axisValue = motionEvent.getAxisValue(26);
            RotaryInputValueView rotaryInputValueView = this.mRotaryInputValueView;
            rotaryInputValueView.removeCallbacks(
                    rotaryInputValueView.mUpdateActivityStatusCallback);
            rotaryInputValueView.setText(
                    rotaryInputValueView.getFormattedValue(
                            rotaryInputValueView.mScaledVerticalScrollFactor * axisValue));
            rotaryInputValueView.setTextColor(-12447960);
            rotaryInputValueView
                    .getBackground()
                    .setColorFilter(RotaryInputValueView.ACTIVE_BACKGROUND_FILTER);
            rotaryInputValueView.postDelayed(
                    rotaryInputValueView.mUpdateActivityStatusCallback, 250L);
            RotaryInputGraphView rotaryInputGraphView = this.mRotaryInputGraphView;
            long eventTime = motionEvent.getEventTime();
            while (true) {
                cyclicBuffer = rotaryInputGraphView.mGraphValues;
                i = cyclicBuffer.mSize;
                graphValueArr = cyclicBuffer.mValues;
                if (i <= 0) {
                    break;
                }
                if (eventTime - graphValueArr[(cyclicBuffer.mLastIndex + (401 - i)) % 400].mTime
                        <= RotaryInputGraphView.MAX_SHOWN_TIME_INTERVAL) {
                    break;
                } else {
                    cyclicBuffer.mSize = i - 1;
                }
            }
            if (i == 0) {
                rotaryInputGraphView.mFrameCenterPosition =
                        FullScreenMagnificationGestureHandler.MAX_SCALE;
            }
            float f =
                    (i == 0 ? 0.0f : graphValueArr[cyclicBuffer.mLastIndex].mPos)
                            + (axisValue * rotaryInputGraphView.mScaledVerticalScrollFactor);
            int i2 = (cyclicBuffer.mLastIndex + 1) % 400;
            cyclicBuffer.mLastIndex = i2;
            RotaryInputGraphView.GraphValue[] graphValueArr2 = cyclicBuffer.mValues;
            RotaryInputGraphView.GraphValue graphValue = graphValueArr2[i2];
            if (graphValue == null) {
                RotaryInputGraphView.GraphValue graphValue2 = new RotaryInputGraphView.GraphValue();
                graphValue2.mPos = f;
                graphValue2.mTime = eventTime;
                graphValueArr2[i2] = graphValue2;
            } else {
                graphValue.mPos = f;
                graphValue.mTime = eventTime;
            }
            if (i != 400) {
                cyclicBuffer.mSize = i + 1;
            }
            float abs =
                    Math.abs(f - rotaryInputGraphView.mFrameCenterPosition)
                            - rotaryInputGraphView.mFrameCenterToBorderDistance;
            if (abs > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                float f2 = rotaryInputGraphView.mFrameCenterPosition;
                rotaryInputGraphView.mFrameCenterPosition =
                        ((f - f2 < FullScreenMagnificationGestureHandler.MAX_SCALE ? -1 : 1) * abs)
                                + f2;
            }
            rotaryInputGraphView.invalidate();
            motionEvent.recycle();
        }
    }

    public void handleUpdateShowRotaryInput(boolean z) {
        if (z == (this.mRotaryInputValueView != null)) {
            return;
        }
        if (!z) {
            this.mFocusEventDebugGlobalMonitor.dispose();
            this.mFocusEventDebugGlobalMonitor = null;
            removeView(this.mRotaryInputValueView);
            this.mRotaryInputValueView = null;
            removeView(this.mRotaryInputGraphView);
            this.mRotaryInputGraphView = null;
            return;
        }
        this.mFocusEventDebugGlobalMonitor = new FocusEventDebugGlobalMonitor(this, this.mService);
        this.mRotaryInputValueView = (RotaryInputValueView) this.mRotaryInputValueViewFactory.get();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14);
        layoutParams.addRule(12);
        addView(this.mRotaryInputValueView, layoutParams);
        this.mRotaryInputGraphView = (RotaryInputGraphView) this.mRotaryInputGraphViewFactory.get();
        RelativeLayout.LayoutParams layoutParams2 =
                new RelativeLayout.LayoutParams(-1, (int) (this.mDm.heightPixels * 0.5d));
        layoutParams2.addRule(13);
        addView(this.mRotaryInputGraphView, layoutParams2);
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        RoundedCorner roundedCorner = windowInsets.getRoundedCorner(3);
        int radius =
                (roundedCorner == null || windowInsets.isRound()) ? 0 : roundedCorner.getRadius();
        RoundedCorner roundedCorner2 = windowInsets.getRoundedCorner(2);
        if (roundedCorner2 != null && !windowInsets.isRound()) {
            radius = Math.max(radius, roundedCorner2.getRadius());
        }
        if (windowInsets.getDisplayCutout() != null) {
            radius = Math.max(radius, windowInsets.getDisplayCutout().getSafeInsetBottom());
        }
        int i = this.mOuterPadding;
        setPadding(i, i, i, radius + i);
        setClipToPadding(false);
        invalidate();
        return super.onApplyWindowInsets(windowInsets);
    }
}
