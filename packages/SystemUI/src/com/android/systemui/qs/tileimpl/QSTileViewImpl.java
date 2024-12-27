package com.android.systemui.qs.tileimpl;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Trace;
import android.os.VibrationEffect;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.Flags;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import com.android.systemui.haptics.qs.LongPressHapticBuilder;
import com.android.systemui.haptics.qs.QSLongPressEffect;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.statusbar.VibratorHelper;
import java.util.List;
import java.util.Objects;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class QSTileViewImpl extends QSTileView implements HeightOverrideable, LaunchableView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public String accessibilityClass;
    public Drawable backgroundBaseDrawable;
    public int backgroundColor;
    public LayerDrawable backgroundDrawable;
    public int backgroundOverlayColor;
    public Drawable backgroundOverlayDrawable;
    public final ImageView chevronView;
    public final int colorActive;
    public final ArgbEvaluator colorEvaluator;
    public final int colorInactive;
    public final int colorLabelActive;
    public final int colorLabelInactive;
    public final int colorLabelUnavailable;
    public final int colorSecondaryLabelActive;
    public final int colorSecondaryLabelInactive;
    public final int colorSecondaryLabelUnavailable;
    public final int colorUnavailable;
    public final ImageView customDrawableView;
    public QSLongPressProperties finalLongPressProperties;
    public boolean haveLongPressPropertiesBeenReset;
    public final int heightOverride;
    public final QSIconViewImpl icon;
    public QSLongPressProperties initialLongPressProperties;
    public final TextView label;
    public final IgnorableChildLinearLayout labelContainer;
    public boolean lastDisabledByPolicy;
    public int lastIconTint;
    public int lastState;
    public CharSequence lastStateDescription;
    public final LaunchableViewDelegate launchableViewDelegate;
    public final int[] locInScreen;
    public final QSLongPressEffect longPressEffect;
    public ValueAnimator longPressEffectAnimator;
    public final int overlayColorActive;
    public final int overlayColorInactive;
    public final Rect paddingForLaunch;
    public int position;
    public RippleDrawable qsTileBackground;
    public final TextView secondaryLabel;
    public boolean showRippleEffect;
    public final ViewGroup sideView;
    public final ValueAnimator singleAnimator;
    public float squishinessFraction;
    public CharSequence stateDescriptionDeltas;
    public boolean tileState;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getLONG_PRESS_EFFECT_HEIGHT_SCALE$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }

        public static /* synthetic */ void getLONG_PRESS_EFFECT_WIDTH_SCALE$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }

        public static /* synthetic */ void getTILE_STATE_RES_PREFIX$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class StateChangeRunnable implements Runnable {
        public final QSTile.State state;

        public StateChangeRunnable(QSTile.State state) {
            this.state = state;
        }

        public final boolean equals(Object obj) {
            return obj instanceof StateChangeRunnable;
        }

        public final int hashCode() {
            return Reflection.getOrCreateKotlinClass(StateChangeRunnable.class).hashCode();
        }

        @Override // java.lang.Runnable
        public final void run() {
            QSTileViewImpl qSTileViewImpl = QSTileViewImpl.this;
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                TraceUtilsKt.beginSlice("QSTileViewImpl#handleStateChanged");
            }
            try {
                qSTileViewImpl.handleStateChanged(this.state);
                Unit unit = Unit.INSTANCE;
            } finally {
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        }
    }

    static {
        new Companion(null);
    }

    public QSTileViewImpl(Context context) {
        this(context, false, null, 6, null);
    }

    public boolean animationsEnabled() {
        if (!isShown() || getAlpha() != 1.0f) {
            return false;
        }
        getLocationOnScreen(this.locInScreen);
        return this.locInScreen[1] >= (-getHeight());
    }

    public final void changeCornerRadius(float f) {
        LayerDrawable layerDrawable = this.backgroundDrawable;
        if (layerDrawable == null) {
            layerDrawable = null;
        }
        int numberOfLayers = layerDrawable.getNumberOfLayers();
        for (int i = 0; i < numberOfLayers; i++) {
            LayerDrawable layerDrawable2 = this.backgroundDrawable;
            if (layerDrawable2 == null) {
                layerDrawable2 = null;
            }
            Drawable drawable = layerDrawable2.getDrawable(i);
            if (drawable instanceof GradientDrawable) {
                ((GradientDrawable) drawable).setCornerRadius(f);
            }
        }
    }

    public final Drawable createTileBackground() {
        Flags.FEATURE_FLAGS.getClass();
        this.qsTileBackground = (RippleDrawable) ((LinearLayout) this).mContext.getDrawable(R.drawable.qs_tile_background);
        Intrinsics.checkNotNull(((LinearLayout) this).mContext.getDrawable(R.drawable.qs_tile_focused_background));
        RippleDrawable rippleDrawable = this.qsTileBackground;
        if (rippleDrawable == null) {
            rippleDrawable = null;
        }
        LayerDrawable layerDrawable = (LayerDrawable) rippleDrawable.findDrawableByLayerId(R.id.background);
        this.backgroundDrawable = layerDrawable;
        this.backgroundBaseDrawable = layerDrawable.findDrawableByLayerId(R.id.qs_tile_background_base);
        LayerDrawable layerDrawable2 = this.backgroundDrawable;
        if (layerDrawable2 == null) {
            layerDrawable2 = null;
        }
        Drawable findDrawableByLayerId = layerDrawable2.findDrawableByLayerId(R.id.qs_tile_background_overlay);
        this.backgroundOverlayDrawable = findDrawableByLayerId;
        findDrawableByLayerId.mutate().setTintMode(PorterDuff.Mode.SRC);
        RippleDrawable rippleDrawable2 = this.qsTileBackground;
        if (rippleDrawable2 == null) {
            return null;
        }
        return rippleDrawable2;
    }

    public final int getBackgroundColorForState(int i, boolean z) {
        if (i == 0 || z) {
            return this.colorUnavailable;
        }
        if (i == 2) {
            return this.colorActive;
        }
        if (i == 1) {
            return this.colorInactive;
        }
        ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "Invalid state ", "QSTileViewImpl");
        return 0;
    }

    public final List<Integer> getCurrentColors$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        Integer valueOf = Integer.valueOf(this.backgroundColor);
        TextView textView = this.label;
        if (textView == null) {
            textView = null;
        }
        Integer valueOf2 = Integer.valueOf(textView.getCurrentTextColor());
        TextView textView2 = this.secondaryLabel;
        if (textView2 == null) {
            textView2 = null;
        }
        Integer valueOf3 = Integer.valueOf(textView2.getCurrentTextColor());
        ImageView imageView = this.chevronView;
        ColorStateList imageTintList = (imageView != null ? imageView : null).getImageTintList();
        return CollectionsKt__CollectionsKt.listOf(valueOf, valueOf2, valueOf3, Integer.valueOf(imageTintList != null ? imageTintList.getDefaultColor() : 0));
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final int getDetailY() {
        return (getHeight() / 2) + getTop();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final QSIconView getIcon() {
        return this.icon;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getIconWithBackground() {
        return this.icon;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getLabel() {
        TextView textView = this.label;
        if (textView == null) {
            return null;
        }
        return textView;
    }

    public final int getLabelColorForState(int i, boolean z) {
        if (i == 0 || z) {
            return this.colorLabelUnavailable;
        }
        if (i == 2) {
            return this.colorLabelActive;
        }
        if (i == 1) {
            return this.colorLabelInactive;
        }
        ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "Invalid state ", "QSTileViewImpl");
        return 0;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getLabelContainer() {
        IgnorableChildLinearLayout ignorableChildLinearLayout = this.labelContainer;
        if (ignorableChildLinearLayout == null) {
            return null;
        }
        return ignorableChildLinearLayout;
    }

    public final int getOverlayColorForState(int i) {
        if (i == 1) {
            return this.overlayColorInactive;
        }
        if (i != 2) {
            return 0;
        }
        return this.overlayColorActive;
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final Rect getPaddingForLaunchAnimation() {
        return this.paddingForLaunch;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getSecondaryIcon() {
        ViewGroup viewGroup = this.sideView;
        if (viewGroup != null) {
            return viewGroup;
        }
        return null;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getSecondaryLabel() {
        TextView textView = this.secondaryLabel;
        if (textView != null) {
            return textView;
        }
        return null;
    }

    public final int getSecondaryLabelColorForState(int i, boolean z) {
        if (i == 0 || z) {
            return this.colorSecondaryLabelUnavailable;
        }
        if (i == 2) {
            return this.colorSecondaryLabelActive;
        }
        if (i == 1) {
            return this.colorSecondaryLabelInactive;
        }
        ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "Invalid state ", "QSTileViewImpl");
        return 0;
    }

    public void handleStateChanged(QSTile.State state) {
        int intValue;
        QSLongPressEffect qSLongPressEffect;
        int longPressEffectDuration;
        boolean z;
        boolean animationsEnabled = animationsEnabled();
        setClickable(state.state != 0);
        setLongClickable(state.handlesLongClick);
        this.icon.setIcon(state, animationsEnabled);
        setContentDescription(state.contentDescription);
        StringBuilder sb = new StringBuilder();
        SubtitleArrayMapping subtitleArrayMapping = SubtitleArrayMapping.INSTANCE;
        String str = state.spec;
        subtitleArrayMapping.getClass();
        int i = R.array.tile_states_default;
        if (str == null) {
            intValue = R.array.tile_states_default;
        } else {
            Integer num = (Integer) SubtitleArrayMapping.subtitleIdsMap.get(str);
            if (num == null) {
                num = Integer.valueOf(R.array.tile_states_default);
            }
            intValue = num.intValue();
        }
        CharSequence stateText = state.getStateText(intValue, getResources());
        state.secondaryLabel = state.getSecondaryLabel(stateText);
        if (!TextUtils.isEmpty(stateText)) {
            sb.append(stateText);
        }
        if (state.disabledByPolicy && state.state != 0) {
            sb.append(", ");
            String str2 = state.spec;
            if (str2 != null) {
                Integer num2 = (Integer) SubtitleArrayMapping.subtitleIdsMap.get(str2);
                if (num2 == null) {
                    num2 = Integer.valueOf(R.array.tile_states_default);
                }
                i = num2.intValue();
            }
            sb.append(getResources().getStringArray(i)[0]);
        }
        if (!TextUtils.isEmpty(state.stateDescription)) {
            sb.append(", ");
            sb.append(state.stateDescription);
            int i2 = this.lastState;
            if (i2 != -1 && state.state == i2 && !Intrinsics.areEqual(state.stateDescription, this.lastStateDescription)) {
                this.stateDescriptionDeltas = state.stateDescription;
            }
        }
        setStateDescription(sb.toString());
        this.lastStateDescription = state.stateDescription;
        VibrationEffect vibrationEffect = null;
        this.accessibilityClass = state.state == 0 ? null : state.expandedAccessibilityClassName;
        boolean z2 = state instanceof QSTile.AdapterState;
        if (z2 && this.tileState != (z = ((QSTile.AdapterState) state).value)) {
            this.tileState = z;
        }
        TextView textView = this.label;
        if (textView == null) {
            textView = null;
        }
        if (!Objects.equals(textView.getText(), state.label)) {
            TextView textView2 = this.label;
            if (textView2 == null) {
                textView2 = null;
            }
            textView2.setText(state.label);
        }
        TextView textView3 = this.secondaryLabel;
        if (textView3 == null) {
            textView3 = null;
        }
        if (!Objects.equals(textView3.getText(), state.secondaryLabel)) {
            TextView textView4 = this.secondaryLabel;
            if (textView4 == null) {
                textView4 = null;
            }
            textView4.setText(state.secondaryLabel);
            TextView textView5 = this.secondaryLabel;
            if (textView5 == null) {
                textView5 = null;
            }
            textView5.setVisibility(TextUtils.isEmpty(state.secondaryLabel) ? 8 : 0);
        }
        if (state.state != this.lastState || state.disabledByPolicy != this.lastDisabledByPolicy) {
            this.singleAnimator.cancel();
            if (animationsEnabled) {
                ValueAnimator valueAnimator = this.singleAnimator;
                PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[5];
                propertyValuesHolderArr[0] = QSTileViewImplKt.access$colorValuesHolder("background", this.backgroundColor, getBackgroundColorForState(state.state, state.disabledByPolicy));
                TextView textView6 = this.label;
                if (textView6 == null) {
                    textView6 = null;
                }
                propertyValuesHolderArr[1] = QSTileViewImplKt.access$colorValuesHolder("label", textView6.getCurrentTextColor(), getLabelColorForState(state.state, state.disabledByPolicy));
                TextView textView7 = this.secondaryLabel;
                if (textView7 == null) {
                    textView7 = null;
                }
                propertyValuesHolderArr[2] = QSTileViewImplKt.access$colorValuesHolder("secondaryLabel", textView7.getCurrentTextColor(), getSecondaryLabelColorForState(state.state, state.disabledByPolicy));
                ImageView imageView = this.chevronView;
                if (imageView == null) {
                    imageView = null;
                }
                ColorStateList imageTintList = imageView.getImageTintList();
                propertyValuesHolderArr[3] = QSTileViewImplKt.access$colorValuesHolder("chevron", imageTintList != null ? imageTintList.getDefaultColor() : 0, getSecondaryLabelColorForState(state.state, state.disabledByPolicy));
                propertyValuesHolderArr[4] = QSTileViewImplKt.access$colorValuesHolder("overlay", this.backgroundOverlayColor, getOverlayColorForState(state.state));
                valueAnimator.setValues(propertyValuesHolderArr);
                this.singleAnimator.start();
            } else {
                setAllColors(getBackgroundColorForState(state.state, state.disabledByPolicy), getLabelColorForState(state.state, state.disabledByPolicy), getSecondaryLabelColorForState(state.state, state.disabledByPolicy), getSecondaryLabelColorForState(state.state, state.disabledByPolicy), getOverlayColorForState(state.state));
            }
        }
        Drawable drawable = state.sideViewCustomDrawable;
        if (drawable != null) {
            ImageView imageView2 = this.customDrawableView;
            if (imageView2 == null) {
                imageView2 = null;
            }
            imageView2.setImageDrawable(drawable);
            ImageView imageView3 = this.customDrawableView;
            if (imageView3 == null) {
                imageView3 = null;
            }
            imageView3.setVisibility(0);
            ImageView imageView4 = this.chevronView;
            if (imageView4 == null) {
                imageView4 = null;
            }
            imageView4.setVisibility(8);
        } else if (!z2 || ((QSTile.AdapterState) state).forceExpandIcon) {
            ImageView imageView5 = this.customDrawableView;
            if (imageView5 == null) {
                imageView5 = null;
            }
            imageView5.setImageDrawable(null);
            ImageView imageView6 = this.customDrawableView;
            if (imageView6 == null) {
                imageView6 = null;
            }
            imageView6.setVisibility(8);
            ImageView imageView7 = this.chevronView;
            if (imageView7 == null) {
                imageView7 = null;
            }
            imageView7.setVisibility(0);
        } else {
            ImageView imageView8 = this.customDrawableView;
            if (imageView8 == null) {
                imageView8 = null;
            }
            imageView8.setImageDrawable(null);
            ImageView imageView9 = this.customDrawableView;
            if (imageView9 == null) {
                imageView9 = null;
            }
            imageView9.setVisibility(8);
            ImageView imageView10 = this.chevronView;
            if (imageView10 == null) {
                imageView10 = null;
            }
            imageView10.setVisibility(8);
        }
        TextView textView8 = this.label;
        if (textView8 == null) {
            textView8 = null;
        }
        textView8.setEnabled(!state.disabledByPolicy);
        this.lastState = state.state;
        this.lastDisabledByPolicy = state.disabledByPolicy;
        this.lastIconTint = this.icon.getColor(state);
        if (!state.handlesLongClick || (qSLongPressEffect = this.longPressEffect) == null || (longPressEffectDuration = getLongPressEffectDuration()) <= 0) {
            this.showRippleEffect = isClickable();
            this.initialLongPressProperties = null;
            this.finalLongPressProperties = null;
            return;
        }
        if (longPressEffectDuration != qSLongPressEffect.effectDuration) {
            qSLongPressEffect.effectDuration = longPressEffectDuration;
            LongPressHapticBuilder longPressHapticBuilder = LongPressHapticBuilder.INSTANCE;
            int[] iArr = qSLongPressEffect.durations;
            int i3 = iArr != null ? iArr[0] : 0;
            int i4 = iArr != null ? iArr[1] : 0;
            longPressHapticBuilder.getClass();
            if (i3 == 0 || i4 == 0) {
                Log.d("LongPressHapticBuilder", "The LOW_TICK and/or SPIN primitives are not supported. No signal created.");
            } else if (longPressEffectDuration < i4 + 99) {
                Log.d("LongPressHapticBuilder", "Cannot fit long-press hint signal in the effect duration. No signal created");
            } else {
                int i5 = 75 / i3;
                int i6 = 24 / i3;
                VibrationEffect.Composition startComposition = VibrationEffect.startComposition();
                for (int i7 = 0; i7 < i5; i7++) {
                    startComposition.addPrimitive(8, 0.08f, 0);
                }
                startComposition.addPrimitive(3, 0.2f, 0);
                int i8 = 0;
                while (i8 < i6) {
                    i8++;
                    startComposition.addPrimitive(8, 0.08f / i8, 0);
                }
                vibrationEffect = startComposition.compose();
            }
            qSLongPressEffect.longPressHint = vibrationEffect;
            qSLongPressEffect.setState(QSLongPressEffect.State.IDLE);
        }
        this.showRippleEffect = false;
        initializeLongPressProperties(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void init(final QSTile qSTile) {
        Expandable.Companion.getClass();
        final Expandable$Companion$fromView$1 expandable$Companion$fromView$1 = new Expandable$Companion$fromView$1(this);
        if (this.longPressEffect == null) {
            View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$init$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    QSTile.this.click(expandable$Companion$fromView$1);
                }
            };
            View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$init$3
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    QSTile.this.longClick(expandable$Companion$fromView$1);
                    return true;
                }
            };
            setOnClickListener(onClickListener);
            setOnLongClickListener(onLongClickListener);
            return;
        }
        setHapticFeedbackEnabled(false);
        QSLongPressEffect qSLongPressEffect = this.longPressEffect;
        qSLongPressEffect.qsTile = qSTile;
        qSLongPressEffect.expandable = expandable$Companion$fromView$1;
        qSLongPressEffect.callback = new QSTileViewImpl$initLongPressEffectCallback$1(this);
        setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$init$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSLongPressEffect qSLongPressEffect2 = QSTileViewImpl.this.longPressEffect;
                if (qSLongPressEffect2.state == QSLongPressEffect.State.TIMEOUT_WAIT) {
                    qSLongPressEffect2.setState(QSLongPressEffect.State.IDLE);
                    QSTile qSTile2 = qSLongPressEffect2.qsTile;
                    if (qSTile2 != null) {
                        qSTile2.click(qSLongPressEffect2.expandable);
                    }
                }
            }
        });
        setOnLongClickListener(null);
    }

    public final void initializeLongPressProperties(int i, int i2) {
        float f = i;
        float f2 = i2;
        this.initialLongPressProperties = new QSLongPressProperties(f, f2, getResources().getDimensionPixelSize(R.dimen.qs_corner_radius), getBackgroundColorForState(this.lastState, false), getLabelColorForState(this.lastState, false), getSecondaryLabelColorForState(this.lastState, false), getSecondaryLabelColorForState(this.lastState, false), getOverlayColorForState(this.lastState), this.lastIconTint);
        this.finalLongPressProperties = new QSLongPressProperties(f * 1.2f, f2 * 1.1f, getResources().getDimensionPixelSize(R.dimen.qs_corner_radius) - 20, getBackgroundColorForState(2, false), getLabelColorForState(2, false), getSecondaryLabelColorForState(2, false), getSecondaryLabelColorForState(2, false), getOverlayColorForState(2), Utils.getColorAttrDefaultColor(getContext(), R.attr.onShadeActive, 0));
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void onActivityLaunchAnimationEnd() {
        if (this.longPressEffect == null || this.haveLongPressPropertiesBeenReset) {
            return;
        }
        resetLongPressEffectProperties();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TextView textView = this.label;
        if (textView == null) {
            textView = null;
        }
        FontSizeUtils.updateFontSize(textView, R.dimen.qs_tile_text_size);
        TextView textView2 = this.secondaryLabel;
        if (textView2 == null) {
            textView2 = null;
        }
        FontSizeUtils.updateFontSize(textView2, R.dimen.qs_tile_text_size);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.qs_icon_size);
        ViewGroup.LayoutParams layoutParams = this.icon.getLayoutParams();
        layoutParams.height = dimensionPixelSize;
        layoutParams.width = dimensionPixelSize;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.qs_tile_padding);
        setPaddingRelative(getResources().getDimensionPixelSize(R.dimen.qs_tile_start_padding), dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.qs_label_container_margin);
        IgnorableChildLinearLayout ignorableChildLinearLayout = this.labelContainer;
        if (ignorableChildLinearLayout == null) {
            ignorableChildLinearLayout = null;
        }
        ((ViewGroup.MarginLayoutParams) ignorableChildLinearLayout.getLayoutParams()).setMarginStart(dimensionPixelSize3);
        ViewGroup viewGroup = this.sideView;
        if (viewGroup == null) {
            viewGroup = null;
        }
        ((ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams()).setMarginStart(dimensionPixelSize3);
        ImageView imageView = this.chevronView;
        if (imageView == null) {
            imageView = null;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
        marginLayoutParams.height = dimensionPixelSize;
        marginLayoutParams.width = dimensionPixelSize;
        int dimensionPixelSize4 = getResources().getDimensionPixelSize(R.dimen.qs_drawable_end_margin);
        ImageView imageView2 = this.customDrawableView;
        if (imageView2 == null) {
            imageView2 = null;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) imageView2.getLayoutParams();
        marginLayoutParams2.height = dimensionPixelSize;
        marginLayoutParams2.setMarginEnd(dimensionPixelSize4);
        setBackground(createTileBackground());
        int i = this.backgroundColor;
        Drawable drawable = this.backgroundBaseDrawable;
        if (drawable == null) {
            drawable = null;
        }
        drawable.mutate().setTint(i);
        this.backgroundColor = i;
        int i2 = this.backgroundOverlayColor;
        Drawable drawable2 = this.backgroundOverlayDrawable;
        (drawable2 != null ? drawable2 : null).setTint(i2);
        this.backgroundOverlayColor = i2;
    }

    @Override // android.view.View
    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        Flags.FEATURE_FLAGS.getClass();
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (!TextUtils.isEmpty(this.accessibilityClass)) {
            accessibilityEvent.setClassName(this.accessibilityClass);
        }
        if (accessibilityEvent.getContentChangeTypes() != 64 || this.stateDescriptionDeltas == null) {
            return;
        }
        accessibilityEvent.getText().add(this.stateDescriptionDeltas);
        this.stateDescriptionDeltas = null;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        String str;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setSelected(false);
        TextView textView = this.secondaryLabel;
        if (textView == null) {
            textView = null;
        }
        if (TextUtils.isEmpty(textView.getText())) {
            TextView textView2 = this.label;
            str = String.valueOf((textView2 != null ? textView2 : null).getText());
        } else {
            TextView textView3 = this.label;
            if (textView3 == null) {
                textView3 = null;
            }
            CharSequence text = textView3.getText();
            TextView textView4 = this.secondaryLabel;
            str = ((Object) text) + ", " + ((Object) (textView4 != null ? textView4 : null).getText());
        }
        accessibilityNodeInfo.setText(str);
        if (this.lastDisabledByPolicy) {
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId(), getResources().getString(R.string.accessibility_tile_disabled_by_policy_action_description)));
        }
        if (!TextUtils.isEmpty(this.accessibilityClass)) {
            accessibilityNodeInfo.setClassName(this.lastDisabledByPolicy ? "android.widget.Button" : this.accessibilityClass);
            if (Switch.class.getName().equals(this.accessibilityClass)) {
                accessibilityNodeInfo.setChecked(this.tileState);
                accessibilityNodeInfo.setCheckable(true);
                if (isLongClickable()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK.getId(), getResources().getString(R.string.accessibility_long_click_tile)));
                }
            }
        }
        if (this.position != -1) {
            accessibilityNodeInfo.setCollectionItemInfo(new AccessibilityNodeInfo.CollectionItemInfo(this.position, 1, 0, 1, false));
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateHeight();
        float measuredWidth = getMeasuredWidth();
        if (!isLongClickable() || this.longPressEffect == null) {
            return;
        }
        QSLongPressProperties qSLongPressProperties = this.initialLongPressProperties;
        if (qSLongPressProperties != null) {
            qSLongPressProperties.width = measuredWidth;
        }
        QSLongPressProperties qSLongPressProperties2 = this.finalLongPressProperties;
        if (qSLongPressProperties2 == null) {
            return;
        }
        qSLongPressProperties2.width = measuredWidth * 1.1f;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        Trace.traceBegin(4096L, "QSTileViewImpl#onMeasure");
        super.onMeasure(i, i2);
        Trace.endSection();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void onStateChanged(QSTile.State state) {
        StateChangeRunnable stateChangeRunnable = new StateChangeRunnable(state.copy());
        removeCallbacks(stateChangeRunnable);
        post(stateChangeRunnable);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        QSTileViewImpl$initLongPressEffectCallback$1 qSTileViewImpl$initLongPressEffectCallback$1;
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        if (this.longPressEffect != null) {
            Integer valueOf = motionEvent != null ? Integer.valueOf(motionEvent.getActionMasked()) : null;
            if (valueOf != null && valueOf.intValue() == 0) {
                QSLongPressEffect qSLongPressEffect = this.longPressEffect;
                int i = QSLongPressEffect.WhenMappings.$EnumSwitchMapping$0[qSLongPressEffect.state.ordinal()];
                if (i == 1) {
                    qSLongPressEffect.setState(QSLongPressEffect.State.TIMEOUT_WAIT);
                } else if (i == 2 && (qSTileViewImpl$initLongPressEffectCallback$1 = qSLongPressEffect.callback) != null) {
                    QSTileViewImpl qSTileViewImpl = qSTileViewImpl$initLongPressEffectCallback$1.this$0;
                    qSTileViewImpl.resetLongPressEffectProperties();
                    ValueAnimator valueAnimator = qSTileViewImpl.longPressEffectAnimator;
                    if (valueAnimator != null) {
                        valueAnimator.cancel();
                    }
                }
                if (isLongClickable()) {
                    postDelayed(new Runnable() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$onTouchEvent$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            QSTileViewImpl$initLongPressEffectCallback$1 qSTileViewImpl$initLongPressEffectCallback$12;
                            QSLongPressEffect qSLongPressEffect2 = QSTileViewImpl.this.longPressEffect;
                            if (qSLongPressEffect2.state != QSLongPressEffect.State.TIMEOUT_WAIT || (qSTileViewImpl$initLongPressEffectCallback$12 = qSLongPressEffect2.callback) == null) {
                                return;
                            }
                            final QSTileViewImpl qSTileViewImpl2 = qSTileViewImpl$initLongPressEffectCallback$12.this$0;
                            ValueAnimator valueAnimator2 = qSTileViewImpl2.longPressEffectAnimator;
                            if (valueAnimator2 == null || !valueAnimator2.isRunning()) {
                                final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                                ofFloat.setDuration(qSTileViewImpl2.longPressEffect != null ? r1.effectDuration : 0L);
                                ofFloat.setInterpolator(new AccelerateDecelerateInterpolator());
                                ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$initLongPressEffectCallback$1$onStartAnimator$lambda$3$$inlined$doOnStart$1
                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationStart(Animator animator) {
                                        QSLongPressEffect qSLongPressEffect3 = QSTileViewImpl.this.longPressEffect;
                                        if (qSLongPressEffect3 != null) {
                                            VibrationEffect vibrationEffect = qSLongPressEffect3.longPressHint;
                                            VibratorHelper vibratorHelper = qSLongPressEffect3.vibratorHelper;
                                            if (vibratorHelper != null && vibrationEffect != null) {
                                                vibratorHelper.vibrate(vibrationEffect);
                                            }
                                            qSLongPressEffect3.setState(QSLongPressEffect.State.RUNNING_FORWARD);
                                        }
                                    }

                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationCancel(Animator animator) {
                                    }

                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator) {
                                    }

                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationRepeat(Animator animator) {
                                    }
                                });
                                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$initLongPressEffectCallback$1$onStartAnimator$1$2
                                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                    public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                                        float floatValue = ((Float) ofFloat.getAnimatedValue()).floatValue();
                                        if (floatValue == 0.0f) {
                                            qSTileViewImpl2.bringToFront();
                                            return;
                                        }
                                        QSTileViewImpl qSTileViewImpl3 = qSTileViewImpl2;
                                        if (!qSTileViewImpl3.isLongClickable() || qSTileViewImpl3.longPressEffect == null) {
                                            return;
                                        }
                                        if (qSTileViewImpl3.haveLongPressPropertiesBeenReset) {
                                            qSTileViewImpl3.haveLongPressPropertiesBeenReset = false;
                                        }
                                        QSLongPressProperties qSLongPressProperties = qSTileViewImpl3.initialLongPressProperties;
                                        float f = qSLongPressProperties != null ? qSLongPressProperties.height : 0.0f;
                                        QSLongPressProperties qSLongPressProperties2 = qSTileViewImpl3.finalLongPressProperties;
                                        int m$1 = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(qSLongPressProperties2 != null ? qSLongPressProperties2.height : 0.0f, f, floatValue, f);
                                        float f2 = qSLongPressProperties != null ? qSLongPressProperties.width : 0.0f;
                                        int m$12 = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(qSLongPressProperties2 != null ? qSLongPressProperties2.width : 0.0f, f2, floatValue, f2);
                                        int i2 = (m$1 - (qSLongPressProperties != null ? (int) qSLongPressProperties.height : 0)) / 2;
                                        int i3 = (m$12 - (qSLongPressProperties != null ? (int) qSLongPressProperties.width : 0)) / 2;
                                        qSTileViewImpl3.getBackground().setBounds(-i3, -i2, m$12 - i3, m$1 - i2);
                                        QSLongPressProperties qSLongPressProperties3 = qSTileViewImpl3.initialLongPressProperties;
                                        float f3 = qSLongPressProperties3 != null ? qSLongPressProperties3.cornerRadius : 0.0f;
                                        QSLongPressProperties qSLongPressProperties4 = qSTileViewImpl3.finalLongPressProperties;
                                        qSTileViewImpl3.changeCornerRadius((((qSLongPressProperties4 != null ? qSLongPressProperties4.cornerRadius : 0.0f) - f3) * floatValue) + f3);
                                        ArgbEvaluator argbEvaluator = qSTileViewImpl3.colorEvaluator;
                                        QSLongPressProperties qSLongPressProperties5 = qSTileViewImpl3.initialLongPressProperties;
                                        Integer valueOf2 = Integer.valueOf(qSLongPressProperties5 != null ? qSLongPressProperties5.backgroundColor : 0);
                                        QSLongPressProperties qSLongPressProperties6 = qSTileViewImpl3.finalLongPressProperties;
                                        int intValue = ((Integer) argbEvaluator.evaluate(floatValue, valueOf2, Integer.valueOf(qSLongPressProperties6 != null ? qSLongPressProperties6.backgroundColor : 0))).intValue();
                                        ArgbEvaluator argbEvaluator2 = qSTileViewImpl3.colorEvaluator;
                                        QSLongPressProperties qSLongPressProperties7 = qSTileViewImpl3.initialLongPressProperties;
                                        Integer valueOf3 = Integer.valueOf(qSLongPressProperties7 != null ? qSLongPressProperties7.labelColor : 0);
                                        QSLongPressProperties qSLongPressProperties8 = qSTileViewImpl3.finalLongPressProperties;
                                        int intValue2 = ((Integer) argbEvaluator2.evaluate(floatValue, valueOf3, Integer.valueOf(qSLongPressProperties8 != null ? qSLongPressProperties8.labelColor : 0))).intValue();
                                        ArgbEvaluator argbEvaluator3 = qSTileViewImpl3.colorEvaluator;
                                        QSLongPressProperties qSLongPressProperties9 = qSTileViewImpl3.initialLongPressProperties;
                                        Integer valueOf4 = Integer.valueOf(qSLongPressProperties9 != null ? qSLongPressProperties9.secondaryLabelColor : 0);
                                        QSLongPressProperties qSLongPressProperties10 = qSTileViewImpl3.finalLongPressProperties;
                                        int intValue3 = ((Integer) argbEvaluator3.evaluate(floatValue, valueOf4, Integer.valueOf(qSLongPressProperties10 != null ? qSLongPressProperties10.secondaryLabelColor : 0))).intValue();
                                        ArgbEvaluator argbEvaluator4 = qSTileViewImpl3.colorEvaluator;
                                        QSLongPressProperties qSLongPressProperties11 = qSTileViewImpl3.initialLongPressProperties;
                                        Integer valueOf5 = Integer.valueOf(qSLongPressProperties11 != null ? qSLongPressProperties11.chevronColor : 0);
                                        QSLongPressProperties qSLongPressProperties12 = qSTileViewImpl3.finalLongPressProperties;
                                        int intValue4 = ((Integer) argbEvaluator4.evaluate(floatValue, valueOf5, Integer.valueOf(qSLongPressProperties12 != null ? qSLongPressProperties12.chevronColor : 0))).intValue();
                                        ArgbEvaluator argbEvaluator5 = qSTileViewImpl3.colorEvaluator;
                                        QSLongPressProperties qSLongPressProperties13 = qSTileViewImpl3.initialLongPressProperties;
                                        Integer valueOf6 = Integer.valueOf(qSLongPressProperties13 != null ? qSLongPressProperties13.overlayColor : 0);
                                        QSLongPressProperties qSLongPressProperties14 = qSTileViewImpl3.finalLongPressProperties;
                                        qSTileViewImpl3.setAllColors(intValue, intValue2, intValue3, intValue4, ((Integer) argbEvaluator5.evaluate(floatValue, valueOf6, Integer.valueOf(qSLongPressProperties14 != null ? qSLongPressProperties14.overlayColor : 0))).intValue());
                                        QSIconViewImpl qSIconViewImpl = qSTileViewImpl3.icon;
                                        ImageView imageView = (ImageView) qSIconViewImpl.mIcon;
                                        ArgbEvaluator argbEvaluator6 = qSTileViewImpl3.colorEvaluator;
                                        QSLongPressProperties qSLongPressProperties15 = qSTileViewImpl3.initialLongPressProperties;
                                        Integer valueOf7 = Integer.valueOf(qSLongPressProperties15 != null ? qSLongPressProperties15.iconColor : 0);
                                        QSLongPressProperties qSLongPressProperties16 = qSTileViewImpl3.finalLongPressProperties;
                                        qSIconViewImpl.setTint(imageView, ((Integer) argbEvaluator6.evaluate(floatValue, valueOf7, Integer.valueOf(qSLongPressProperties16 != null ? qSLongPressProperties16.iconColor : 0))).intValue());
                                    }
                                });
                                ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$initLongPressEffectCallback$1$onStartAnimator$lambda$3$$inlined$doOnEnd$1
                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator) {
                                        QSLongPressEffect qSLongPressEffect3 = QSTileViewImpl.this.longPressEffect;
                                        if (qSLongPressEffect3 != null) {
                                            if (qSLongPressEffect3.state == QSLongPressEffect.State.RUNNING_FORWARD) {
                                                qSLongPressEffect3.setState(QSLongPressEffect.State.IDLE);
                                                VibrationEffect vibrationEffect = qSLongPressEffect3.snapEffect;
                                                VibratorHelper vibratorHelper = qSLongPressEffect3.vibratorHelper;
                                                if (vibratorHelper != null && vibrationEffect != null) {
                                                    vibratorHelper.vibrate(vibrationEffect);
                                                }
                                                if (qSLongPressEffect3.keyguardStateController.isUnlocked()) {
                                                    QSTileViewImpl$initLongPressEffectCallback$1 qSTileViewImpl$initLongPressEffectCallback$13 = qSLongPressEffect3.callback;
                                                    if (qSTileViewImpl$initLongPressEffectCallback$13 != null) {
                                                        QSTileViewImpl qSTileViewImpl3 = qSTileViewImpl$initLongPressEffectCallback$13.this$0;
                                                        QSLongPressProperties qSLongPressProperties = qSTileViewImpl3.initialLongPressProperties;
                                                        int i2 = qSLongPressProperties != null ? (int) qSLongPressProperties.height : 0;
                                                        int i3 = qSLongPressProperties != null ? (int) qSLongPressProperties.width : 0;
                                                        QSLongPressProperties qSLongPressProperties2 = qSTileViewImpl3.finalLongPressProperties;
                                                        int i4 = qSLongPressProperties2 != null ? (int) (qSLongPressProperties2.height - i2) : 0;
                                                        int i5 = qSLongPressProperties2 != null ? (int) (qSLongPressProperties2.width - i3) : 0;
                                                        Rect rect = qSTileViewImpl3.paddingForLaunch;
                                                        rect.left = (-i5) / 2;
                                                        rect.top = (-i4) / 2;
                                                        rect.right = i5 / 2;
                                                        rect.bottom = i4 / 2;
                                                    }
                                                    QSTile qSTile = qSLongPressEffect3.qsTile;
                                                    if (qSTile != null) {
                                                        qSTile.longClick(qSLongPressEffect3.expandable);
                                                    }
                                                } else {
                                                    QSTileViewImpl$initLongPressEffectCallback$1 qSTileViewImpl$initLongPressEffectCallback$14 = qSLongPressEffect3.callback;
                                                    if (qSTileViewImpl$initLongPressEffectCallback$14 != null) {
                                                        qSTileViewImpl$initLongPressEffectCallback$14.this$0.resetLongPressEffectProperties();
                                                    }
                                                    QSTile qSTile2 = qSLongPressEffect3.qsTile;
                                                    if (qSTile2 != null) {
                                                        qSTile2.longClick(qSLongPressEffect3.expandable);
                                                    }
                                                }
                                            }
                                            if (qSLongPressEffect3.state != QSLongPressEffect.State.TIMEOUT_WAIT) {
                                                qSLongPressEffect3.setState(QSLongPressEffect.State.IDLE);
                                            }
                                        }
                                    }

                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationCancel(Animator animator) {
                                    }

                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationRepeat(Animator animator) {
                                    }

                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationStart(Animator animator) {
                                    }
                                });
                                ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$initLongPressEffectCallback$1$onStartAnimator$lambda$3$$inlined$doOnCancel$1
                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationCancel(Animator animator) {
                                        QSLongPressEffect qSLongPressEffect3 = QSTileViewImpl.this.longPressEffect;
                                        if (qSLongPressEffect3 != null) {
                                            qSLongPressEffect3.setState(QSLongPressEffect.State.TIMEOUT_WAIT);
                                        }
                                    }

                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator) {
                                    }

                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationRepeat(Animator animator) {
                                    }

                                    @Override // android.animation.Animator.AnimatorListener
                                    public final void onAnimationStart(Animator animator) {
                                    }
                                });
                                ofFloat.start();
                                qSTileViewImpl2.longPressEffectAnimator = ofFloat;
                            }
                        }
                    }, ViewConfiguration.getTapTimeout());
                }
            } else if (valueOf != null && valueOf.intValue() == 1) {
                QSLongPressEffect qSLongPressEffect2 = this.longPressEffect;
                if (qSLongPressEffect2.state == QSLongPressEffect.State.RUNNING_FORWARD) {
                    qSLongPressEffect2.setState(QSLongPressEffect.State.RUNNING_BACKWARDS);
                    QSTileViewImpl$initLongPressEffectCallback$1 qSTileViewImpl$initLongPressEffectCallback$12 = qSLongPressEffect2.callback;
                    if (qSTileViewImpl$initLongPressEffectCallback$12 != null) {
                        qSTileViewImpl$initLongPressEffectCallback$12.onReverseAnimator();
                    }
                }
            } else if (valueOf != null && valueOf.intValue() == 3) {
                QSLongPressEffect qSLongPressEffect3 = this.longPressEffect;
                int i2 = QSLongPressEffect.WhenMappings.$EnumSwitchMapping$0[qSLongPressEffect3.state.ordinal()];
                if (i2 == 3) {
                    qSLongPressEffect3.setState(QSLongPressEffect.State.IDLE);
                } else if (i2 == 4) {
                    qSLongPressEffect3.setState(QSLongPressEffect.State.RUNNING_BACKWARDS);
                    QSTileViewImpl$initLongPressEffectCallback$1 qSTileViewImpl$initLongPressEffectCallback$13 = qSLongPressEffect3.callback;
                    if (qSTileViewImpl$initLongPressEffectCallback$13 != null) {
                        qSTileViewImpl$initLongPressEffectCallback$13.onReverseAnimator();
                    }
                }
            }
        }
        return onTouchEvent;
    }

    public final void resetLongPressEffectProperties() {
        Drawable background = getBackground();
        QSLongPressProperties qSLongPressProperties = this.initialLongPressProperties;
        int measuredWidth = qSLongPressProperties != null ? (int) qSLongPressProperties.width : getMeasuredWidth();
        QSLongPressProperties qSLongPressProperties2 = this.initialLongPressProperties;
        background.setBounds(0, 0, measuredWidth, qSLongPressProperties2 != null ? (int) qSLongPressProperties2.height : getMeasuredHeight());
        changeCornerRadius(getResources().getDimensionPixelSize(R.dimen.qs_corner_radius));
        setAllColors(getBackgroundColorForState(this.lastState, this.lastDisabledByPolicy), getLabelColorForState(this.lastState, this.lastDisabledByPolicy), getSecondaryLabelColorForState(this.lastState, this.lastDisabledByPolicy), getSecondaryLabelColorForState(this.lastState, this.lastDisabledByPolicy), getOverlayColorForState(this.lastState));
        QSIconViewImpl qSIconViewImpl = this.icon;
        qSIconViewImpl.setTint((ImageView) qSIconViewImpl.mIcon, this.lastIconTint);
        this.haveLongPressPropertiesBeenReset = true;
    }

    public final void setAllColors(int i, int i2, int i3, int i4, int i5) {
        Drawable drawable = this.backgroundBaseDrawable;
        if (drawable == null) {
            drawable = null;
        }
        drawable.mutate().setTint(i);
        this.backgroundColor = i;
        TextView textView = this.label;
        if (textView == null) {
            textView = null;
        }
        textView.setTextColor(i2);
        TextView textView2 = this.secondaryLabel;
        if (textView2 == null) {
            textView2 = null;
        }
        textView2.setTextColor(i3);
        ImageView imageView = this.chevronView;
        if (imageView == null) {
            imageView = null;
        }
        imageView.setImageTintList(ColorStateList.valueOf(i4));
        Drawable drawable2 = this.backgroundOverlayDrawable;
        (drawable2 != null ? drawable2 : null).setTint(i5);
        this.backgroundOverlayColor = i5;
    }

    @Override // android.view.View
    public final void setClickable(boolean z) {
        LayerDrawable layerDrawable;
        super.setClickable(z);
        Flags.FEATURE_FLAGS.getClass();
        if (z && this.showRippleEffect) {
            layerDrawable = this.qsTileBackground;
            if (layerDrawable == null) {
                layerDrawable = null;
            }
            LayerDrawable layerDrawable2 = this.backgroundDrawable;
            (layerDrawable2 != null ? layerDrawable2 : null).setCallback(layerDrawable);
        } else {
            LayerDrawable layerDrawable3 = this.backgroundDrawable;
            layerDrawable = layerDrawable3 != null ? layerDrawable3 : null;
        }
        setBackground(layerDrawable);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void setPosition(int i) {
        this.position = i;
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void setShouldBlockVisibilityChanges(boolean z) {
        this.launchableViewDelegate.setShouldBlockVisibilityChanges(z);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        this.launchableViewDelegate.setVisibility(i);
    }

    @Override // android.view.View
    public final String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        int[] iArr = this.locInScreen;
        sb.append(HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(iArr[0], iArr[1], "locInScreen=(", ", ", ")"));
        sb.append(", iconView=" + this.icon);
        sb.append(", tileState=" + this.tileState);
        sb.append("]");
        return sb.toString();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View updateAccessibilityOrder(View view) {
        setAccessibilityTraversalAfter(view != null ? view.getId() : 0);
        return this;
    }

    public final void updateHeight() {
        if (!this.haveLongPressPropertiesBeenReset && this.longPressEffect != null) {
            resetLongPressEffectProperties();
        }
        int i = this.heightOverride;
        if (i == -1) {
            i = getMeasuredHeight();
        }
        float f = i;
        setBottom(getTop() + ((int) (((this.squishinessFraction * 0.9f) + 0.1f) * f)));
        setScrollY((i - getHeight()) / 2);
        if (!isLongClickable() || this.longPressEffect == null) {
            return;
        }
        QSLongPressProperties qSLongPressProperties = this.initialLongPressProperties;
        if (qSLongPressProperties != null) {
            qSLongPressProperties.height = f;
        }
        QSLongPressProperties qSLongPressProperties2 = this.finalLongPressProperties;
        if (qSLongPressProperties2 == null) {
            return;
        }
        qSLongPressProperties2.height = f * 1.2f;
    }

    public QSTileViewImpl(Context context, boolean z) {
        this(context, z, null, 4, null);
    }

    public /* synthetic */ QSTileViewImpl(Context context, boolean z, QSLongPressEffect qSLongPressEffect, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? false : z, (i & 4) != 0 ? null : qSLongPressEffect);
    }

    public QSTileViewImpl(Context context, boolean z, QSLongPressEffect qSLongPressEffect) {
        super(context);
        this.longPressEffect = qSLongPressEffect;
        QSIconViewImpl qSIconViewImpl = new QSIconViewImpl(context);
        this.icon = qSIconViewImpl;
        this.position = -1;
        this.heightOverride = -1;
        this.squishinessFraction = 1.0f;
        this.colorActive = Utils.getColorAttrDefaultColor(context, R.attr.shadeActive, 0);
        this.colorInactive = Utils.getColorAttrDefaultColor(context, R.attr.shadeInactive, 0);
        this.colorUnavailable = Utils.getColorAttrDefaultColor(context, R.attr.shadeDisabled, 0);
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(context, R.attr.onShadeActive, 0);
        this.overlayColorActive = Color.argb((int) (0.11f * Color.alpha(colorAttrDefaultColor)), Color.red(colorAttrDefaultColor), Color.green(colorAttrDefaultColor), Color.blue(colorAttrDefaultColor));
        int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(context, R.attr.onShadeInactive, 0);
        this.overlayColorInactive = Color.argb((int) (0.08f * Color.alpha(colorAttrDefaultColor2)), Color.red(colorAttrDefaultColor2), Color.green(colorAttrDefaultColor2), Color.blue(colorAttrDefaultColor2));
        this.colorLabelActive = Utils.getColorAttrDefaultColor(context, R.attr.onShadeActive, 0);
        this.colorLabelInactive = Utils.getColorAttrDefaultColor(context, R.attr.onShadeInactive, 0);
        this.colorLabelUnavailable = Utils.getColorAttrDefaultColor(context, R.attr.outline, 0);
        this.colorSecondaryLabelActive = Utils.getColorAttrDefaultColor(context, R.attr.onShadeActiveVariant, 0);
        this.colorSecondaryLabelInactive = Utils.getColorAttrDefaultColor(context, R.attr.onShadeInactiveVariant, 0);
        this.colorSecondaryLabelUnavailable = Utils.getColorAttrDefaultColor(context, R.attr.outline, 0);
        this.showRippleEffect = true;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(350L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$singleAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                QSTileViewImpl qSTileViewImpl = QSTileViewImpl.this;
                int intValue = ((Integer) valueAnimator2.getAnimatedValue("background")).intValue();
                int intValue2 = ((Integer) valueAnimator2.getAnimatedValue("label")).intValue();
                int intValue3 = ((Integer) valueAnimator2.getAnimatedValue("secondaryLabel")).intValue();
                int intValue4 = ((Integer) valueAnimator2.getAnimatedValue("chevron")).intValue();
                int intValue5 = ((Integer) valueAnimator2.getAnimatedValue("overlay")).intValue();
                int i = QSTileViewImpl.$r8$clinit;
                qSTileViewImpl.setAllColors(intValue, intValue2, intValue3, intValue4, intValue5);
            }
        });
        this.singleAnimator = valueAnimator;
        this.lastState = -1;
        this.launchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$launchableViewDelegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                super/*android.widget.LinearLayout*/.setVisibility(((Number) obj).intValue());
                return Unit.INSTANCE;
            }
        });
        this.locInScreen = new int[2];
        this.haveLongPressPropertiesBeenReset = true;
        this.paddingForLaunch = new Rect();
        this.colorEvaluator = ArgbEvaluator.getInstance();
        if (getContext().getTheme().resolveAttribute(R.attr.isQsTheme, new TypedValue(), true)) {
            setId(LinearLayout.generateViewId());
            setOrientation(0);
            setGravity(8388627);
            setImportantForAccessibility(1);
            setClipChildren(false);
            setClipToPadding(false);
            setFocusable(true);
            setBackground(createTileBackground());
            int backgroundColorForState = getBackgroundColorForState(2, false);
            Drawable drawable = this.backgroundBaseDrawable;
            (drawable == null ? null : drawable).mutate().setTint(backgroundColorForState);
            this.backgroundColor = backgroundColorForState;
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_tile_padding);
            setPaddingRelative(getResources().getDimensionPixelSize(R.dimen.qs_tile_start_padding), dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
            int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.qs_icon_size);
            addView(qSIconViewImpl, new LinearLayout.LayoutParams(dimensionPixelSize2, dimensionPixelSize2));
            IgnorableChildLinearLayout ignorableChildLinearLayout = (IgnorableChildLinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.qs_tile_label, (ViewGroup) this, false);
            this.labelContainer = ignorableChildLinearLayout;
            this.label = (TextView) ignorableChildLinearLayout.requireViewById(R.id.tile_label);
            IgnorableChildLinearLayout ignorableChildLinearLayout2 = this.labelContainer;
            TextView textView = (TextView) (ignorableChildLinearLayout2 == null ? null : ignorableChildLinearLayout2).requireViewById(R.id.app_label);
            this.secondaryLabel = textView;
            if (z) {
                IgnorableChildLinearLayout ignorableChildLinearLayout3 = this.labelContainer;
                (ignorableChildLinearLayout3 == null ? null : ignorableChildLinearLayout3).ignoreLastView = true;
                (ignorableChildLinearLayout3 == null ? null : ignorableChildLinearLayout3).forceUnspecifiedMeasure = true;
                textView.setAlpha(0.0f);
            }
            int labelColorForState = getLabelColorForState(2, false);
            TextView textView2 = this.label;
            (textView2 == null ? null : textView2).setTextColor(labelColorForState);
            int secondaryLabelColorForState = getSecondaryLabelColorForState(2, false);
            TextView textView3 = this.secondaryLabel;
            (textView3 == null ? null : textView3).setTextColor(secondaryLabelColorForState);
            View view = this.labelContainer;
            addView(view == null ? null : view);
            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.qs_tile_side_icon, (ViewGroup) this, false);
            this.sideView = viewGroup;
            this.customDrawableView = (ImageView) viewGroup.requireViewById(R.id.customDrawable);
            ViewGroup viewGroup2 = this.sideView;
            this.chevronView = (ImageView) (viewGroup2 == null ? null : viewGroup2).requireViewById(R.id.chevron);
            int secondaryLabelColorForState2 = getSecondaryLabelColorForState(2, false);
            ImageView imageView = this.chevronView;
            (imageView == null ? null : imageView).setImageTintList(ColorStateList.valueOf(secondaryLabelColorForState2));
            View view2 = this.sideView;
            addView(view2 != null ? view2 : null);
            return;
        }
        throw new IllegalStateException("QSViewImpl must be inflated with a theme that contains Theme.SystemUI.QuickSettings");
    }
}
