package com.android.server.input;

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
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.AccelerateInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.display.DisplayPowerController2;
import com.android.server.input.FocusEventDebugView;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class FocusEventDebugView extends LinearLayout {
    public static final String TAG = FocusEventDebugView.class.getSimpleName();
    public final int mOuterPadding;
    public final PressedKeyContainer mPressedKeyContainer;
    public final Map mPressedKeys;
    public final PressedKeyContainer mPressedModifierContainer;

    public FocusEventDebugView(Context context) {
        super(context);
        this.mPressedKeys = new HashMap();
        setFocusableInTouchMode(true);
        this.mOuterPadding = (int) TypedValue.applyDimension(1, 16.0f, ((LinearLayout) this).mContext.getResources().getDisplayMetrics());
        setOrientation(0);
        setLayoutDirection(1);
        setGravity(8388691);
        PressedKeyContainer pressedKeyContainer = new PressedKeyContainer(((LinearLayout) this).mContext);
        this.mPressedKeyContainer = pressedKeyContainer;
        pressedKeyContainer.setOrientation(0);
        pressedKeyContainer.setGravity(85);
        pressedKeyContainer.setLayoutDirection(0);
        final HorizontalScrollView horizontalScrollView = new HorizontalScrollView(((LinearLayout) this).mContext);
        horizontalScrollView.addView(pressedKeyContainer);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        horizontalScrollView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.server.input.FocusEventDebugView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                horizontalScrollView.fullScroll(66);
            }
        });
        horizontalScrollView.setHorizontalFadingEdgeEnabled(true);
        addView(horizontalScrollView, new LinearLayout.LayoutParams(0, -2, 1.0f));
        PressedKeyContainer pressedKeyContainer2 = new PressedKeyContainer(((LinearLayout) this).mContext);
        this.mPressedModifierContainer = pressedKeyContainer2;
        pressedKeyContainer2.setOrientation(1);
        pressedKeyContainer2.setGravity(83);
        addView(pressedKeyContainer2, new LinearLayout.LayoutParams(-2, -2));
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        RoundedCorner roundedCorner = windowInsets.getRoundedCorner(3);
        int radius = roundedCorner != null ? roundedCorner.getRadius() : 0;
        RoundedCorner roundedCorner2 = windowInsets.getRoundedCorner(2);
        if (roundedCorner2 != null) {
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

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        handleKeyEvent(keyEvent);
        return super.dispatchKeyEvent(keyEvent);
    }

    public void reportEvent(final InputEvent inputEvent) {
        if (inputEvent instanceof KeyEvent) {
            post(new Runnable() { // from class: com.android.server.input.FocusEventDebugView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FocusEventDebugView.this.lambda$reportEvent$1(inputEvent);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportEvent$1(InputEvent inputEvent) {
        handleKeyEvent(KeyEvent.obtain((KeyEvent) inputEvent));
    }

    public final void handleKeyEvent(KeyEvent keyEvent) {
        PressedKeyContainer pressedKeyContainer;
        Pair pair = new Pair(Integer.valueOf(keyEvent.getDeviceId()), Integer.valueOf(keyEvent.getScanCode()));
        if (KeyEvent.isModifierKey(keyEvent.getKeyCode())) {
            pressedKeyContainer = this.mPressedModifierContainer;
        } else {
            pressedKeyContainer = this.mPressedKeyContainer;
        }
        PressedKeyView pressedKeyView = (PressedKeyView) this.mPressedKeys.get(pair);
        int action = keyEvent.getAction();
        if (action != 0) {
            if (action == 1) {
                if (pressedKeyView == null) {
                    Slog.w(TAG, "Got key up for " + KeyEvent.keyCodeToString(keyEvent.getKeyCode()) + " that was not tracked as being down.");
                } else {
                    this.mPressedKeys.remove(pair);
                    pressedKeyContainer.handleKeyRelease(pressedKeyView);
                }
            }
        } else if (pressedKeyView != null) {
            if (keyEvent.getRepeatCount() == 0) {
                Slog.w(TAG, "Got key down for " + KeyEvent.keyCodeToString(keyEvent.getKeyCode()) + " that was already tracked as being down.");
            } else {
                pressedKeyContainer.handleKeyRepeat(pressedKeyView);
            }
        } else {
            PressedKeyView pressedKeyView2 = new PressedKeyView(((LinearLayout) this).mContext, getLabel(keyEvent));
            this.mPressedKeys.put(pair, pressedKeyView2);
            pressedKeyContainer.handleKeyPressed(pressedKeyView2);
        }
        keyEvent.recycle();
    }

    public static String getLabel(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 61) {
            return "⇥";
        }
        if (keyCode == 62) {
            return "␣";
        }
        if (keyCode == 66) {
            return "⏎";
        }
        if (keyCode == 67) {
            return "⌫";
        }
        if (keyCode == 111) {
            return "ESC";
        }
        if (keyCode == 112) {
            return "⌦";
        }
        if (keyCode == 160) {
            return "⏎";
        }
        switch (keyCode) {
            case 19:
                return "↑";
            case 20:
                return "↓";
            case 21:
                return "←";
            case 22:
                return "→";
            default:
                switch (keyCode) {
                    case 268:
                        return "↖";
                    case 269:
                        return "↙";
                    case FrameworkStatsLog.f392x1636cc72 /* 270 */:
                        return "↗";
                    case FrameworkStatsLog.f391x9ea7cdac /* 271 */:
                        return "↘";
                    default:
                        int unicodeChar = keyEvent.getUnicodeChar();
                        if (unicodeChar != 0) {
                            return new String(Character.toChars(unicodeChar));
                        }
                        String keyCodeToString = KeyEvent.keyCodeToString(keyEvent.getKeyCode());
                        return keyCodeToString.startsWith("KEYCODE_") ? keyCodeToString.substring(8) : keyCodeToString;
                }
        }
    }

    public class PressedKeyView extends TextView {
        public static final ColorFilter sInvertColors = new ColorMatrixColorFilter(new float[]{-1.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 255.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, -1.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 255.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, -1.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 255.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON});

        public PressedKeyView(Context context, String str) {
            super(context);
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int applyDimension = (int) TypedValue.applyDimension(1, 16.0f, displayMetrics);
            int applyDimension2 = (int) TypedValue.applyDimension(1, 8.0f, displayMetrics);
            int applyDimension3 = (int) TypedValue.applyDimension(1, 32.0f, displayMetrics);
            int applyDimension4 = (int) TypedValue.applyDimension(2, 12.0f, displayMetrics);
            setText(str);
            setGravity(17);
            setMinimumWidth(applyDimension3);
            setTextSize(applyDimension4);
            setTypeface(Typeface.SANS_SERIF);
            setBackgroundResource(R.drawable.expander_close_holo_dark);
            setPaddingRelative(applyDimension, applyDimension2, applyDimension, applyDimension2);
            setHighlighted(true);
        }

        public void setHighlighted(boolean z) {
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

    public class PressedKeyContainer extends LinearLayout {
        public final ViewGroup.MarginLayoutParams mPressedKeyLayoutParams;

        public void handleKeyRepeat(PressedKeyView pressedKeyView) {
        }

        public PressedKeyContainer(Context context) {
            super(context);
            int applyDimension = (int) TypedValue.applyDimension(1, 16.0f, context.getResources().getDisplayMetrics());
            LayoutTransition layoutTransition = new LayoutTransition();
            layoutTransition.disableTransitionType(2);
            layoutTransition.disableTransitionType(3);
            layoutTransition.disableTransitionType(1);
            layoutTransition.setDuration(100L);
            setLayoutTransition(layoutTransition);
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
            this.mPressedKeyLayoutParams = marginLayoutParams;
            if (getOrientation() == 1) {
                marginLayoutParams.setMargins(0, applyDimension, 0, 0);
            } else {
                marginLayoutParams.setMargins(applyDimension, 0, 0, 0);
            }
        }

        public void handleKeyPressed(PressedKeyView pressedKeyView) {
            addView(pressedKeyView, getChildCount(), this.mPressedKeyLayoutParams);
            invalidate();
        }

        public void handleKeyRelease(PressedKeyView pressedKeyView) {
            pressedKeyView.setHighlighted(false);
            pressedKeyView.clearAnimation();
            pressedKeyView.animate().alpha(DisplayPowerController2.RATE_FROM_DOZE_TO_ON).setDuration(1000L).setInterpolator(new AccelerateInterpolator()).withEndAction(new Runnable() { // from class: com.android.server.input.FocusEventDebugView$PressedKeyContainer$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FocusEventDebugView.PressedKeyContainer.this.cleanUpPressedKeyViews();
                }
            }).start();
        }

        public final void cleanUpPressedKeyViews() {
            int i = 0;
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                View childAt = getChildAt(i2);
                if (childAt.getAlpha() != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                    break;
                }
                childAt.setVisibility(8);
                childAt.clearAnimation();
                i++;
            }
            removeViews(0, i);
            invalidate();
        }
    }
}
