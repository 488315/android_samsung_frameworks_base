package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SecQSTileBaseView extends QSTileView implements LaunchableView {
    public String mAccessibilityClass;
    public final ImageView mBg;
    public int mCircleColor;
    public final boolean mCollapsedView;
    public final int mColorActive;
    public final int mColorDisabled;
    public final int mColorInactive;
    public final LaunchableViewDelegate mDelegate;
    public final H mHandler;
    public final QSIconViewImpl mIcon;
    public final FrameLayout mIconFrame;
    public int mLastState;
    public CharSequence mLastStateDescription;
    public final int[] mLocInScreen;
    public int mOrientation;
    public final RippleDrawable mRipple;
    public int mScreenLayout;
    public int mSemDisplayDeviceType;
    public CharSequence mStateDescriptionDeltas;
    public final float mStrokeWidthActive;
    public final float mStrokeWidthInactive;
    public final Drawable mTileBackground;
    public String mTileSpec;
    public boolean mTileState;

    public final class H extends Handler {
        public H() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what == 1) {
                SecQSTileBaseView.this.handleStateChanged((QSTile.State) message.obj);
            }
        }
    }

    /* renamed from: $r8$lambda$8PEmPle3qwT-ZBfqDAlQpjAmlGM, reason: not valid java name */
    public static /* synthetic */ Unit m2078$r8$lambda$8PEmPle3qwTZBfqDAlQpjAmlGM(SecQSTileBaseView secQSTileBaseView, Integer num) {
        secQSTileBaseView.getClass();
        super.setVisibility(num.intValue());
        return Unit.INSTANCE;
    }

    public SecQSTileBaseView(Context context) {
        this(context, false, null);
    }

    public int getCircleColor(int i) {
        if (i == 0) {
            return this.mColorInactive;
        }
        if (i == 1) {
            return this.mColorDisabled;
        }
        if (i == 2) {
            return this.mColorActive;
        }
        ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "Invalid state ", "QSTileBaseView");
        return 0;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public int getDetailY() {
        return (getHeight() / 2) + getTop();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final QSIconView getIcon() {
        return this.mIcon;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getIconWithBackground() {
        return this.mIconFrame;
    }

    public Drawable getTileBackground() {
        return this.mTileBackground;
    }

    public void handleStateChanged(QSTile.State state) {
        boolean z;
        if (this.mBg.getDrawable() instanceof ShapeDrawable) {
            ShapeDrawable shapeDrawable = (ShapeDrawable) this.mBg.getDrawable();
            shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
            int i = state.state;
            if (i != 1) {
                if (i == 2 && this.mStrokeWidthActive >= 0.0f) {
                    shapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
                    shapeDrawable.getPaint().setStrokeWidth(this.mStrokeWidthActive);
                }
            } else if (this.mStrokeWidthInactive >= 0.0f) {
                shapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
                shapeDrawable.getPaint().setStrokeWidth(this.mStrokeWidthInactive);
            }
        }
        int circleColor = getCircleColor(state.state);
        if (circleColor != this.mCircleColor) {
            this.mBg.setImageTintList(ColorStateList.valueOf(circleColor));
            this.mCircleColor = circleColor;
        }
        super.setClickable(state.state != 0);
        setLongClickable(state.handlesLongClick);
        this.mIcon.setIcon(state, true);
        StringBuilder sb = new StringBuilder();
        String str = this.mTileSpec;
        if (str == null || str.contains("SoundMode")) {
            sb.append(((LinearLayout) this).mContext.getString(R.string.switch_bar_on));
        } else {
            int i2 = state.state;
            if (i2 == 1) {
                sb.append(((LinearLayout) this).mContext.getString(R.string.switch_bar_off));
            } else if (i2 == 2) {
                sb.append(((LinearLayout) this).mContext.getString(R.string.switch_bar_on));
            }
            if (!TextUtils.isEmpty(state.stateDescription)) {
                sb.append(", ");
                sb.append(state.stateDescription);
                int i3 = this.mLastState;
                if (i3 != -1 && state.state == i3 && !state.stateDescription.equals(this.mLastStateDescription)) {
                    this.mStateDescriptionDeltas = state.stateDescription;
                }
            }
        }
        setStateDescription(sb.toString());
        int i4 = state.state;
        this.mLastState = i4;
        this.mLastStateDescription = state.stateDescription;
        this.mAccessibilityClass = i4 == 0 ? null : state.expandedAccessibilityClassName;
        if (!(state instanceof QSTile.BooleanState) || this.mTileState == (z = ((QSTile.BooleanState) state).value)) {
            return;
        }
        this.mTileState = z;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.qs.tileimpl.SecQSTileBaseView$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.qs.tileimpl.SecQSTileBaseView$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.qs.tileimpl.SecQSTileBaseView$$ExternalSyntheticLambda3] */
    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void init(final QSTile qSTile) {
        Expandable.Companion.getClass();
        final Expandable$Companion$fromView$1 expandable$Companion$fromView$1 = new Expandable$Companion$fromView$1(this);
        final int i = 0;
        final int i2 = 1;
        init(new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.SecQSTileBaseView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        qSTile.click(expandable$Companion$fromView$1);
                        break;
                    default:
                        qSTile.secondaryClick(expandable$Companion$fromView$1);
                        break;
                }
            }
        }, new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.SecQSTileBaseView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        qSTile.click(expandable$Companion$fromView$1);
                        break;
                    default:
                        qSTile.secondaryClick(expandable$Companion$fromView$1);
                        break;
                }
            }
        }, new View.OnLongClickListener() { // from class: com.android.systemui.qs.tileimpl.SecQSTileBaseView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                QSTile.this.longClick(expandable$Companion$fromView$1);
                return true;
            }
        });
        this.mTileSpec = qSTile.getTileSpec();
    }

    public final Drawable newTileBackground() {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        return drawable;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = this.mOrientation;
        int i2 = configuration.orientation;
        if (i == i2 && this.mScreenLayout == configuration.screenLayout && this.mSemDisplayDeviceType == configuration.semDisplayDeviceType) {
            return;
        }
        this.mOrientation = i2;
        this.mScreenLayout = configuration.screenLayout;
        this.mSemDisplayDeviceType = configuration.semDisplayDeviceType;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (!TextUtils.isEmpty(this.mAccessibilityClass)) {
            accessibilityEvent.setClassName(this.mAccessibilityClass);
        }
        if (accessibilityEvent.getContentChangeTypes() != 64 || this.mStateDescriptionDeltas == null) {
            return;
        }
        accessibilityEvent.getText().add(this.mStateDescriptionDeltas);
        this.mStateDescriptionDeltas = null;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setSelected(false);
        if (TextUtils.isEmpty(this.mAccessibilityClass)) {
            return;
        }
        accessibilityNodeInfo.setClassName(this.mAccessibilityClass);
        if (Switch.class.getName().equals(this.mAccessibilityClass)) {
            accessibilityNodeInfo.setText(getResources().getString(this.mTileState ? R.string.switch_bar_on : R.string.switch_bar_off));
            accessibilityNodeInfo.setChecked(this.mTileState);
            accessibilityNodeInfo.setCheckable(true);
            if (isLongClickable()) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK.getId(), getResources().getString(R.string.accessibility_long_click_tile)));
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mRipple != null) {
            updateRippleSize();
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void onStateChanged(QSTile.State state) {
        this.mHandler.obtainMessage(1, state).sendToTarget();
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void setShouldBlockVisibilityChanges(boolean z) {
        this.mDelegate.setShouldBlockVisibilityChanges(z);
    }

    @Override // android.view.View
    public final String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        sb.append("locInScreen=(" + this.mLocInScreen[0] + ", " + this.mLocInScreen[1] + ")");
        StringBuilder sb2 = new StringBuilder(", iconView=");
        sb2.append(this.mIcon.toString());
        sb.append(sb2.toString());
        sb.append(", tileState=" + this.mTileState);
        sb.append("]");
        return sb.toString();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View updateAccessibilityOrder(View view) {
        setAccessibilityTraversalAfter(view.getId());
        return this;
    }

    public final void updateRippleSize() {
        int measuredWidth = this.mIcon.getMeasuredWidth() / 2;
        int measuredHeight = this.mIcon.getMeasuredHeight() / 2;
        int height = (int) (this.mIcon.getHeight() * 0.43f);
        this.mRipple.setHotspotBounds(measuredWidth - height, measuredHeight - height, measuredWidth + height, measuredHeight + height);
    }

    public SecQSTileBaseView(Context context, boolean z) {
        this(context, z, null);
    }

    public SecQSTileBaseView(Context context, boolean z, QSIconViewImpl qSIconViewImpl) {
        super(context);
        this.mHandler = new H();
        this.mLocInScreen = new int[2];
        this.mStateDescriptionDeltas = null;
        this.mLastState = -1;
        this.mDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.qs.tileimpl.SecQSTileBaseView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return SecQSTileBaseView.m2078$r8$lambda$8PEmPle3qwTZBfqDAlQpjAmlGM(SecQSTileBaseView.this, (Integer) obj);
            }
        });
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        FrameLayout frameLayout = new FrameLayout(context);
        this.mIconFrame = frameLayout;
        this.mStrokeWidthActive = context.getResources().getDimension(android.R.dimen.control_corner_material);
        this.mStrokeWidthInactive = context.getResources().getDimension(android.R.dimen.control_inset_material);
        Context context2 = ((LinearLayout) this).mContext;
        int tileIconSize = z ? secQSPanelResourcePicker.getTileIconSize(context2) : secQSPanelResourcePicker.getTouchIconSize(context2);
        Context context3 = ((LinearLayout) this).mContext;
        int tileIconSize2 = z ? secQSPanelResourcePicker.getTileIconSize(context3) : secQSPanelResourcePicker.getTouchIconSize(context3);
        setLayoutParams(new LinearLayout.LayoutParams(tileIconSize, tileIconSize2));
        addView(frameLayout, new LinearLayout.LayoutParams(tileIconSize, tileIconSize2));
        ImageView imageView = new ImageView(getContext());
        this.mBg = imageView;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.setTintList(ColorStateList.valueOf(0));
        int tileIconSize3 = secQSPanelResourcePicker.getTileIconSize(context);
        shapeDrawable.setIntrinsicHeight(tileIconSize3);
        shapeDrawable.setIntrinsicWidth(tileIconSize3);
        imageView.setImageDrawable(shapeDrawable);
        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(tileIconSize3, tileIconSize3, 17);
        frameLayout.addView(imageView, layoutParams);
        imageView.setLayoutParams(layoutParams);
        this.mIcon = qSIconViewImpl;
        if (qSIconViewImpl == null) {
            this.mIcon = new QSIconViewImpl(context);
        }
        frameLayout.addView(this.mIcon, new FrameLayout.LayoutParams(tileIconSize3, tileIconSize3, 17));
        frameLayout.setClipChildren(false);
        frameLayout.setClipToPadding(false);
        frameLayout.setFocusable(false);
        Drawable newTileBackground = newTileBackground();
        this.mTileBackground = newTileBackground;
        if (newTileBackground instanceof RippleDrawable) {
            RippleDrawable rippleDrawable = (RippleDrawable) newTileBackground;
            rippleDrawable.setColor(ColorStateList.valueOf(((LinearLayout) this).mContext.getColor(R.color.sec_qs_ripple_background)));
            this.mRipple = rippleDrawable;
            if (getWidth() != 0) {
                updateRippleSize();
            }
        }
        setImportantForAccessibility(2);
        this.mIcon.setBackground(newTileBackground);
        this.mIcon.setFocusable(true);
        setFocusable(false);
        this.mColorActive = ((LinearLayout) this).mContext.getColor(R.color.qs_tile_round_background_on);
        this.mColorDisabled = ((LinearLayout) this).mContext.getColor(R.color.qs_tile_round_background_off);
        this.mColorInactive = ((LinearLayout) this).mContext.getColor(R.color.qs_tile_round_background_dim);
        setPadding(0, 0, 0, 0);
        setClipChildren(false);
        setClipToPadding(false);
        this.mCollapsedView = z;
    }

    public void init(SecQSTileBaseView$$ExternalSyntheticLambda1 secQSTileBaseView$$ExternalSyntheticLambda1, SecQSTileBaseView$$ExternalSyntheticLambda1 secQSTileBaseView$$ExternalSyntheticLambda12, SecQSTileBaseView$$ExternalSyntheticLambda3 secQSTileBaseView$$ExternalSyntheticLambda3) {
        this.mIconFrame.setOnClickListener(secQSTileBaseView$$ExternalSyntheticLambda1);
        this.mIconFrame.setOnLongClickListener(secQSTileBaseView$$ExternalSyntheticLambda3);
        this.mIcon.setOnClickListener(secQSTileBaseView$$ExternalSyntheticLambda1);
        this.mIcon.setOnLongClickListener(secQSTileBaseView$$ExternalSyntheticLambda3);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void setPosition(int i) {
    }
}
