package com.android.systemui.qs.tileimpl;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.graphics.drawable.SeslRecoilDrawable;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecQSCommonTileView {
    public static final Companion Companion = new Companion(null);
    public final SecQSCommonTileView$accessibilityDelegate$1 accessibilityDelegate;
    public final int activeColor;
    public final float activeStrokeWidth;
    public int circleColor;
    public final Context context;
    public final int disabledColor;
    public final FrameLayout iconFrame;
    public final QSIconView iconView;
    public final int inactiveColor;
    public final float inactiveStrokeWidth;
    public final boolean isNoBgLargeTile;
    public TextView label;
    public LinearLayout labelContainer;
    public int lastState;
    public CharSequence lastStateDescription;
    public final View parentView;
    public final SecQSPanelResourcePicker resourcePicker;
    public final ColorStateList secLabelColor;
    public final ColorStateList secSubLabelColor;
    public TextView secondLine;
    public final Drawable tileBackground;
    public final ImageView tileBg;
    public String tileSpec;
    public final Lazy uiHandler$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v14, types: [android.view.View$AccessibilityDelegate, com.android.systemui.qs.tileimpl.SecQSCommonTileView$accessibilityDelegate$1] */
    public SecQSCommonTileView(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker, QSIconView qSIconView, View view, ColorStateList colorStateList, ColorStateList colorStateList2, boolean z, boolean z2) {
        this.context = context;
        this.resourcePicker = secQSPanelResourcePicker;
        this.iconView = qSIconView;
        this.parentView = view;
        this.secLabelColor = colorStateList;
        this.secSubLabelColor = colorStateList2;
        this.isNoBgLargeTile = z;
        this.activeStrokeWidth = context.getResources().getDimension(R.dimen.date_picker_date_label_size);
        this.inactiveStrokeWidth = context.getResources().getDimension(R.dimen.date_picker_day_height);
        this.activeColor = context.getColor(com.android.systemui.R.color.qs_tile_round_background_on);
        this.disabledColor = context.getColor(com.android.systemui.R.color.qs_tile_round_background_off);
        this.inactiveColor = context.getColor(com.android.systemui.R.color.qs_tile_round_background_dim);
        this.lastState = -1;
        this.uiHandler$delegate = LazyKt__LazyJVMKt.lazy(new SecQSCommonTileView$$ExternalSyntheticLambda0());
        ?? r7 = new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.tileimpl.SecQSCommonTileView$accessibilityDelegate$1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                accessibilityNodeInfo.setSelected(false);
                accessibilityNodeInfo.setClassName("android.widget.Button");
                if (SecQSCommonTileView.this.lastState == 0 || accessibilityNodeInfo.getStateDescription() == null) {
                    accessibilityNodeInfo.setCheckable(false);
                } else {
                    accessibilityNodeInfo.setChecked(SecQSCommonTileView.this.lastState == 2);
                    accessibilityNodeInfo.setCheckable(true);
                }
            }
        };
        this.accessibilityDelegate = r7;
        int tileImageSize = z2 ? secQSPanelResourcePicker.getTileImageSize(context) : secQSPanelResourcePicker.getTileIconSize(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(tileImageSize, tileImageSize, 17);
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.setClipChildren(false);
        frameLayout.setClipToPadding(false);
        frameLayout.setFocusable(false);
        this.iconFrame = frameLayout;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.setTintList(ColorStateList.valueOf(0));
        shapeDrawable.setIntrinsicHeight(tileImageSize);
        shapeDrawable.setIntrinsicWidth(tileImageSize);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageDrawable(shapeDrawable);
        this.tileBg = imageView;
        Drawable drawable = qSIconView.getContext().getDrawable(com.android.systemui.R.drawable.sec_tile_view_transparent_ripple_background);
        if ((drawable instanceof SeslRecoilDrawable ? (SeslRecoilDrawable) drawable : null) != null) {
            updateRippleSize();
        }
        this.tileBackground = drawable;
        qSIconView.setBackground(drawable);
        qSIconView.setLayoutParams(layoutParams);
        frameLayout.addView(imageView);
        frameLayout.addView(qSIconView);
        if (view instanceof LargeTileView) {
            qSIconView.setAccessibilityDelegate(r7);
        }
        view.setAccessibilityDelegate(r7);
    }

    public final LinearLayout createLabel(int i, QSTileView qSTileView) {
        View inflate = LayoutInflater.from(this.context).inflate(i, (ViewGroup) qSTileView, false);
        LinearLayout linearLayout = inflate instanceof LinearLayout ? (LinearLayout) inflate : null;
        if (linearLayout != null) {
            this.labelContainer = linearLayout;
            TextView textView = (TextView) linearLayout.requireViewById(com.android.systemui.R.id.tile_label);
            textView.setSelected(true);
            textView.setPaintFlags(textView.getPaintFlags() | 192);
            this.label = textView;
            TextView textView2 = (TextView) linearLayout.requireViewById(com.android.systemui.R.id.app_label);
            textView2.setSelected(true);
            textView2.setPaintFlags(textView2.getPaintFlags() | 192);
            this.secondLine = textView2;
        } else {
            Log.w(qSTileView.toString(), "createLabel(): label inflates failed");
        }
        return this.labelContainer;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleStateChanged(com.android.systemui.plugins.qs.QSTile.State r12, final boolean r13) {
        /*
            Method dump skipped, instructions count: 553
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tileimpl.SecQSCommonTileView.handleStateChanged(com.android.systemui.plugins.qs.QSTile$State, boolean):void");
    }

    public final void init(final QSTile qSTile, QSTileView qSTileView) {
        Expandable.Companion.getClass();
        final Expandable$Companion$fromView$1 expandable$Companion$fromView$1 = new Expandable$Companion$fromView$1(qSTileView);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.SecQSCommonTileView$init$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSTile.this.click(expandable$Companion$fromView$1);
            }
        };
        this.iconFrame.setOnClickListener(onClickListener);
        QSIconView qSIconView = this.iconView;
        qSIconView.setOnClickListener(onClickListener);
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() { // from class: com.android.systemui.qs.tileimpl.SecQSCommonTileView$init$3
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                QSTile.this.longClick(expandable$Companion$fromView$1);
                return true;
            }
        };
        this.iconFrame.setOnLongClickListener(onLongClickListener);
        qSIconView.setOnLongClickListener(onLongClickListener);
        this.tileSpec = qSTile.getTileSpec();
    }

    /* JADX WARN: Code restructure failed: missing block: B:81:0x012e, code lost:
    
        if (r12.length() <= 0) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0130, code lost:
    
        r9 = r9 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0133, code lost:
    
        if (r9 <= 2) goto L94;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setLabelSingleLine(boolean r20) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tileimpl.SecQSCommonTileView.setLabelSingleLine(boolean):boolean");
    }

    public final void updateRippleSize() {
        QSIconView qSIconView = this.iconView;
        Pair pair = new Pair(Integer.valueOf(qSIconView.getMeasuredWidth() / 2), Integer.valueOf(qSIconView.getMeasuredHeight() / 2));
        int intValue = ((Number) pair.component1()).intValue();
        int intValue2 = ((Number) pair.component2()).intValue();
        int roundToInt = MathKt__MathJVMKt.roundToInt(qSIconView.getHeight() * 0.43f);
        Drawable drawable = this.tileBackground;
        if (drawable != null) {
            drawable.setHotspotBounds(intValue - roundToInt, intValue2 - roundToInt, intValue + roundToInt, intValue2 + roundToInt);
        }
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException
        */
    public SecQSCommonTileView(android.content.Context r10, com.android.systemui.qs.SecQSPanelResourcePicker r11, com.android.systemui.plugins.qs.QSIconView r12, android.view.View r13, android.content.res.ColorStateList r14, android.content.res.ColorStateList r15, boolean r16, boolean r17, int r18, kotlin.jvm.internal.DefaultConstructorMarker r19) {
        /*
            r9 = this;
            r0 = r18
            r2 = r0 & 16
            com.android.systemui.qs.tileimpl.SecQSCommonTileView$Companion r3 = com.android.systemui.qs.tileimpl.SecQSCommonTileView.Companion
            if (r2 == 0) goto L18
            r3.getClass()
            r2 = 2131101223(0x7f060627, float:1.781485E38)
            int r2 = r10.getColor(r2)
            android.content.res.ColorStateList r2 = android.content.res.ColorStateList.valueOf(r2)
            r5 = r2
            goto L19
        L18:
            r5 = r14
        L19:
            r2 = r0 & 32
            if (r2 == 0) goto L2d
            r3.getClass()
            r2 = 2131101230(0x7f06062e, float:1.7814864E38)
            int r2 = r10.getColor(r2)
            android.content.res.ColorStateList r2 = android.content.res.ColorStateList.valueOf(r2)
            r6 = r2
            goto L2e
        L2d:
            r6 = r15
        L2e:
            r2 = r0 & 64
            r3 = 0
            if (r2 == 0) goto L35
            r7 = r3
            goto L37
        L35:
            r7 = r16
        L37:
            r0 = r0 & 128(0x80, float:1.8E-43)
            if (r0 == 0) goto L42
            r8 = r3
            r0 = r9
            r1 = r10
            r2 = r11
            r4 = r13
            r3 = r12
            goto L49
        L42:
            r8 = r17
            r0 = r9
            r1 = r10
            r2 = r11
            r3 = r12
            r4 = r13
        L49:
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tileimpl.SecQSCommonTileView.<init>(android.content.Context, com.android.systemui.qs.SecQSPanelResourcePicker, com.android.systemui.plugins.qs.QSIconView, android.view.View, android.content.res.ColorStateList, android.content.res.ColorStateList, boolean, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
