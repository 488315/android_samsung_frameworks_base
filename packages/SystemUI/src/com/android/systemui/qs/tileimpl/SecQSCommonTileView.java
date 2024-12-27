package com.android.systemui.qs.tileimpl;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Handler;
import android.os.Looper;
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
import com.android.systemui.qs.SecQSPanelResourcePicker;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SecQSCommonTileView(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker, QSIconView qSIconView, View view, ColorStateList colorStateList, ColorStateList colorStateList2, boolean z) {
        this.context = context;
        this.resourcePicker = secQSPanelResourcePicker;
        this.iconView = qSIconView;
        this.parentView = view;
        this.secLabelColor = colorStateList;
        this.secSubLabelColor = colorStateList2;
        this.isNoBgLargeTile = z;
        this.activeStrokeWidth = context.getResources().getDimension(R.dimen.control_corner_material);
        this.inactiveStrokeWidth = context.getResources().getDimension(R.dimen.control_inset_material);
        this.activeColor = context.getColor(com.android.systemui.R.color.qs_tile_round_background_on);
        this.disabledColor = context.getColor(com.android.systemui.R.color.qs_tile_round_background_off);
        this.inactiveColor = context.getColor(com.android.systemui.R.color.qs_tile_round_background_dim);
        this.lastState = -1;
        this.uiHandler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.tileimpl.SecQSCommonTileView$uiHandler$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new Handler(Looper.getMainLooper());
            }
        });
        View.AccessibilityDelegate accessibilityDelegate = new View.AccessibilityDelegate() { // from class: com.android.systemui.qs.tileimpl.SecQSCommonTileView$accessibilityDelegate$1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                accessibilityNodeInfo.setSelected(false);
                accessibilityNodeInfo.setClassName("android.widget.Button");
            }
        };
        int tileIconSize = secQSPanelResourcePicker.getTileIconSize(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(tileIconSize, tileIconSize, 17);
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.setClipChildren(false);
        frameLayout.setClipToPadding(false);
        frameLayout.setFocusable(false);
        this.iconFrame = frameLayout;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.setTintList(ColorStateList.valueOf(0));
        shapeDrawable.setIntrinsicHeight(tileIconSize);
        shapeDrawable.setIntrinsicWidth(tileIconSize);
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
            qSIconView.setAccessibilityDelegate(accessibilityDelegate);
        }
        view.setAccessibilityDelegate(accessibilityDelegate);
    }

    public final LinearLayout createLabel(int i, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(this.context).inflate(i, viewGroup, false);
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
            Log.w(viewGroup.toString(), "createLabel(): label inflates failed");
        }
        return this.labelContainer;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0184  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleStateChanged(com.android.systemui.plugins.qs.QSTile.State r11, final boolean r12) {
        /*
            Method dump skipped, instructions count: 527
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tileimpl.SecQSCommonTileView.handleStateChanged(com.android.systemui.plugins.qs.QSTile$State, boolean):void");
    }

    public final void init(final QSTile qSTile, View view) {
        Expandable.Companion.getClass();
        final Expandable$Companion$fromView$1 expandable$Companion$fromView$1 = new Expandable$Companion$fromView$1(view);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.SecQSCommonTileView$init$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                QSTile.this.click(expandable$Companion$fromView$1);
            }
        };
        this.iconFrame.setOnClickListener(onClickListener);
        QSIconView qSIconView = this.iconView;
        qSIconView.setOnClickListener(onClickListener);
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() { // from class: com.android.systemui.qs.tileimpl.SecQSCommonTileView$init$3
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                QSTile.this.longClick(expandable$Companion$fromView$1);
                return true;
            }
        };
        this.iconFrame.setOnLongClickListener(onLongClickListener);
        qSIconView.setOnLongClickListener(onLongClickListener);
        this.tileSpec = qSTile.getTileSpec();
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

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SecQSCommonTileView(android.content.Context r9, com.android.systemui.qs.SecQSPanelResourcePicker r10, com.android.systemui.plugins.qs.QSIconView r11, android.view.View r12, android.content.res.ColorStateList r13, android.content.res.ColorStateList r14, boolean r15, int r16, kotlin.jvm.internal.DefaultConstructorMarker r17) {
        /*
            r8 = this;
            r1 = r9
            r0 = r16 & 16
            com.android.systemui.qs.tileimpl.SecQSCommonTileView$Companion r2 = com.android.systemui.qs.tileimpl.SecQSCommonTileView.Companion
            if (r0 == 0) goto L17
            r2.getClass()
            r0 = 2131101137(0x7f0605d1, float:1.7814675E38)
            int r0 = r9.getColor(r0)
            android.content.res.ColorStateList r0 = android.content.res.ColorStateList.valueOf(r0)
            r5 = r0
            goto L18
        L17:
            r5 = r13
        L18:
            r0 = r16 & 32
            if (r0 == 0) goto L2c
            r2.getClass()
            r0 = 2131101144(0x7f0605d8, float:1.781469E38)
            int r0 = r9.getColor(r0)
            android.content.res.ColorStateList r0 = android.content.res.ColorStateList.valueOf(r0)
            r6 = r0
            goto L2d
        L2c:
            r6 = r14
        L2d:
            r0 = r16 & 64
            if (r0 == 0) goto L34
            r0 = 0
            r7 = r0
            goto L35
        L34:
            r7 = r15
        L35:
            r0 = r8
            r1 = r9
            r2 = r10
            r3 = r11
            r4 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tileimpl.SecQSCommonTileView.<init>(android.content.Context, com.android.systemui.qs.SecQSPanelResourcePicker, com.android.systemui.plugins.qs.QSIconView, android.view.View, android.content.res.ColorStateList, android.content.res.ColorStateList, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
