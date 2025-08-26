package com.android.systemui.qs.tileimpl;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils;
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
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.util.ViewUtil;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;

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
                if (this.this$0.lastState == 0 || accessibilityNodeInfo.getStateDescription() == null) {
                    accessibilityNodeInfo.setCheckable(false);
                } else {
                    accessibilityNodeInfo.setChecked(this.this$0.lastState == 2);
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
        View viewInflate = LayoutInflater.from(this.context).inflate(i, (ViewGroup) qSTileView, false);
        LinearLayout linearLayout = viewInflate instanceof LinearLayout ? (LinearLayout) viewInflate : null;
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

    /* JADX WARN: Removed duplicated region for block: B:100:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0122  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleStateChanged(QSTile.State state, final boolean z) {
        int i;
        TextView textView;
        CharSequence charSequence;
        String string;
        CharSequence charSequence2;
        CharSequence charSequence3;
        TextView textView2;
        String strReplace;
        String string2;
        Paint paint;
        Drawable drawable = this.tileBg.getDrawable();
        ShapeDrawable shapeDrawable = drawable instanceof ShapeDrawable ? (ShapeDrawable) drawable : null;
        if (shapeDrawable != null && (paint = shapeDrawable.getPaint()) != null) {
            float f = this.inactiveStrokeWidth;
            if (f >= 0.0f) {
                paint.setStyle(Paint.Style.STROKE);
                int i2 = state.state;
                if (i2 == 1) {
                    paint.setStrokeWidth(f);
                } else if (i2 == 2) {
                    paint.setStrokeWidth(this.activeStrokeWidth);
                }
            } else {
                paint.setStyle(Paint.Style.FILL);
            }
        }
        int i3 = this.circleColor;
        if (!this.isNoBgLargeTile) {
            int i4 = state.state;
            if (i4 == 0) {
                i = this.inactiveColor;
            } else if (i4 == 1) {
                i = this.disabledColor;
            } else if (i4 != 2) {
                ClockEventController$$ExternalSyntheticOutline0.m(i4, "toCircleColor: invalid state ", "SecQSCommonTileView");
            } else {
                i = this.activeColor;
            }
            this.circleColor = i;
            if (i != i3) {
                this.tileBg.setImageTintList(ColorStateList.valueOf(i));
            }
            QSIconView qSIconView = this.iconView;
            qSIconView.setIcon(state, true);
            textView = this.label;
            if (textView != null) {
                if (Intrinsics.areEqual(textView.getText(), state.label)) {
                    textView = null;
                }
                int i5 = com.android.systemui.R.dimen.sec_qs_tile_label_text_size;
                if (textView != null) {
                    textView.setText(state.label);
                    textView.setTextColor(this.secLabelColor);
                    textView.setEnabled(!state.disabledByPolicy);
                    textView.setSingleLine(false);
                    textView.setBreakStrategy(1);
                    FontSizeUtils.updateFontSize(textView, this.parentView instanceof LargeTileView ? com.android.systemui.R.dimen.sec_style_qs_tile_text_size : com.android.systemui.R.dimen.sec_qs_tile_label_text_size, 1.0f, 1.15f);
                }
                TextView textView3 = this.secondLine;
                if (textView3 != null) {
                    if (Intrinsics.areEqual(textView3.getText(), state.secondaryLabel)) {
                        textView3 = null;
                    }
                    if (textView3 != null) {
                        CharSequence charSequence4 = state.secondaryLabel;
                        textView3.setVisibility(TextUtils.isEmpty(charSequence4) ? 8 : 0);
                        textView3.setText(charSequence4);
                        textView3.setTextColor(this.secSubLabelColor);
                        if (this.parentView instanceof LargeTileView) {
                            i5 = com.android.systemui.R.dimen.sec_style_qs_tile_second_text_size;
                        }
                        FontSizeUtils.updateFontSize(textView3, i5, 1.0f, 1.15f);
                    }
                }
                Handler handler = (Handler) this.uiHandler$delegate.getValue();
                Runnable runnable = new Runnable() { // from class: com.android.systemui.qs.tileimpl.SecQSCommonTileView$handleLabel$1$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.this$0.setLabelSingleLine(z);
                    }
                };
                TextView textView4 = this.label;
                handler.postDelayed(runnable, (textView4 == null || textView4.getLineCount() != 0) ? 0L : 100L);
            }
            String str = "";
            if (!z) {
                StringBuilder sb = new StringBuilder();
                String str2 = this.tileSpec;
                if (str2 == null || !StringsKt__StringsKt.contains(str2, "SoundMode", false)) {
                    int i6 = state.state;
                    sb.append(i6 != 1 ? i6 != 2 ? "" : this.context.getString(com.android.systemui.R.string.switch_bar_on) : this.context.getString(com.android.systemui.R.string.switch_bar_off));
                    CharSequence charSequence5 = state.stateDescription;
                    if (TextUtils.isEmpty(charSequence5)) {
                        charSequence5 = null;
                    }
                    if (charSequence5 != null) {
                        sb.append(", ");
                        sb.append(charSequence5);
                        int i7 = this.lastState;
                        if (i7 != -1 && state.state == i7) {
                            Intrinsics.areEqual(state.stateDescription, this.lastStateDescription);
                        }
                    }
                } else {
                    sb.append(state.label);
                    sb.append(" ");
                    sb.append(this.context.getString(com.android.systemui.R.string.switch_bar_on));
                }
                if (this.parentView instanceof LargeTileView) {
                    qSIconView.setStateDescription(sb);
                }
                this.parentView.setStateDescription(sb);
                this.lastState = state.state;
                this.lastStateDescription = state.stateDescription;
            }
            charSequence = state.label;
            if (charSequence == null) {
                StringBuilder sb2 = new StringBuilder(charSequence.length());
                sb2.append(charSequence);
                string = sb2.toString();
            } else {
                string = null;
            }
            charSequence2 = state.contentDescription;
            if (charSequence2 != null && (string2 = charSequence2.toString()) != null) {
                string = string2;
            }
            String strReplace2 = (string != null || (strReplace = new Regex("\n").replace(string, " ")) == null) ? null : new Regex("-").replace(strReplace, "");
            charSequence3 = state.secondaryLabel;
            if (charSequence3 != null) {
                strReplace2 = strReplace2 + ", " + ((Object) charSequence3);
            }
            if (this.parentView instanceof LargeTileView) {
                qSIconView.setContentDescription(strReplace2);
            }
            this.parentView.setContentDescription(strReplace2);
            textView2 = this.label;
            if (textView2 != null) {
                LinearLayout linearLayout = this.labelContainer;
                String strM = AnimatorInflaterCompat$$ExternalSyntheticOutline0.m(", labelContainer = ", linearLayout != null ? ViewUtil.INSTANCE.toIdSting(linearLayout) : null, ", label = ", ViewUtil.INSTANCE.toIdSting(textView2));
                if (strM != null) {
                    str = strM;
                }
            }
            String str3 = state.spec;
            View view = this.parentView;
            ViewUtil viewUtil = ViewUtil.INSTANCE;
            String idSting = viewUtil.toIdSting(this.iconFrame);
            String idSting2 = viewUtil.toIdSting(qSIconView);
            String shortIdSting = viewUtil.toShortIdSting(this);
            StringBuilder sb3 = new StringBuilder("handleStateChanged state.spec = ");
            sb3.append(str3);
            sb3.append(", parent = ");
            sb3.append(view);
            sb3.append(str);
            MoveResult$$ExternalSyntheticOutline0.m(sb3, ", iconFrame = ", idSting, ", icon = ", idSting2);
            ExifInterface$$ExternalSyntheticOutline0.m(sb3, ", common = ", shortIdSting, "SecQSCommonTileView");
        }
        int noBGTileIconSize = this.resourcePicker.getNoBGTileIconSize(this.context);
        ViewGroup.LayoutParams layoutParams = this.tileBg.getLayoutParams();
        layoutParams.width = noBGTileIconSize;
        layoutParams.height = noBGTileIconSize;
        i = 0;
        this.circleColor = i;
        if (i != i3) {
        }
        QSIconView qSIconView2 = this.iconView;
        qSIconView2.setIcon(state, true);
        textView = this.label;
        if (textView != null) {
        }
        String str4 = "";
        if (!z) {
        }
        charSequence = state.label;
        if (charSequence == null) {
        }
        charSequence2 = state.contentDescription;
        if (charSequence2 != null) {
            string = string2;
        }
        if (string != null) {
        }
        charSequence3 = state.secondaryLabel;
        if (charSequence3 != null) {
        }
        if (this.parentView instanceof LargeTileView) {
        }
        this.parentView.setContentDescription(strReplace2);
        textView2 = this.label;
        if (textView2 != null) {
        }
        String str32 = state.spec;
        View view2 = this.parentView;
        ViewUtil viewUtil2 = ViewUtil.INSTANCE;
        String idSting3 = viewUtil2.toIdSting(this.iconFrame);
        String idSting22 = viewUtil2.toIdSting(qSIconView2);
        String shortIdSting2 = viewUtil2.toShortIdSting(this);
        StringBuilder sb32 = new StringBuilder("handleStateChanged state.spec = ");
        sb32.append(str32);
        sb32.append(", parent = ");
        sb32.append(view2);
        sb32.append(str4);
        MoveResult$$ExternalSyntheticOutline0.m(sb32, ", iconFrame = ", idSting3, ", icon = ", idSting22);
        ExifInterface$$ExternalSyntheticOutline0.m(sb32, ", common = ", shortIdSting2, "SecQSCommonTileView");
    }

    public final void init(final QSTile qSTile, QSTileView qSTileView) {
        Expandable.Companion.getClass();
        final Expandable$Companion$fromView$1 expandable$Companion$fromView$1 = new Expandable$Companion$fromView$1(qSTileView);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.SecQSCommonTileView.init.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                qSTile.click(expandable$Companion$fromView$1);
            }
        };
        this.iconFrame.setOnClickListener(onClickListener);
        QSIconView qSIconView = this.iconView;
        qSIconView.setOnClickListener(onClickListener);
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() { // from class: com.android.systemui.qs.tileimpl.SecQSCommonTileView.init.3
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                qSTile.longClick(expandable$Companion$fromView$1);
                return true;
            }
        };
        this.iconFrame.setOnLongClickListener(onLongClickListener);
        qSIconView.setOnLongClickListener(onLongClickListener);
        this.tileSpec = qSTile.getTileSpec();
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x012e, code lost:
    
        if (r12.length() <= 0) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0130, code lost:
    
        r9 = r9 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0133, code lost:
    
        if (r9 <= 2) goto L94;
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x012a A[EDGE_INSN: B:98:0x012a->B:62:0x012a BREAK  A[LOOP:3: B:37:0x00ce->B:61:0x0125], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean setLabelSingleLine(boolean z) {
        TextView textView;
        Collection collectionTake;
        CharSequence text;
        Collection collectionTake2;
        int length;
        int i;
        String str;
        int i2 = 0;
        if (z || (textView = this.label) == null || textView.isSingleLine()) {
            return false;
        }
        int measuredWidth = (textView.getMeasuredWidth() - textView.getPaddingLeft()) - textView.getPaddingRight();
        String string = textView.getText().toString();
        TextView textView2 = this.label;
        TextPaint paint = textView2 != null ? textView2.getPaint() : null;
        List listSplit = new Regex("\n").split(string);
        int i3 = 1;
        if (listSplit.isEmpty()) {
            collectionTake = EmptyList.INSTANCE;
        } else {
            ListIterator listIterator = listSplit.listIterator(listSplit.size());
            while (listIterator.hasPrevious()) {
                if (((String) listIterator.previous()).length() != 0) {
                    collectionTake = CollectionsKt___CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                    break;
                }
            }
            collectionTake = EmptyList.INSTANCE;
        }
        String[] strArr = (String[]) collectionTake.toArray(new String[0]);
        int length2 = strArr.length;
        int i4 = 0;
        int i5 = 0;
        loop1: while (true) {
            if (i4 >= length2) {
                break;
            }
            String str2 = strArr[i4];
            str2.getClass();
            List listSplit2 = new Regex(" ").split(str2);
            if (!listSplit2.isEmpty()) {
                ListIterator listIterator2 = listSplit2.listIterator(listSplit2.size());
                while (listIterator2.hasPrevious()) {
                    if (((String) listIterator2.previous()).length() != 0) {
                        collectionTake2 = CollectionsKt___CollectionsKt.take(listSplit2, listIterator2.nextIndex() + i3);
                        break;
                    }
                }
                collectionTake2 = EmptyList.INSTANCE;
                String[] strArr2 = (String[]) collectionTake2.toArray(new String[i2]);
                StringBuilder sb = new StringBuilder();
                length = strArr2.length;
                i = i2;
                while (true) {
                    if (i < length) {
                        break;
                    }
                    String str3 = strArr2[i];
                    str3.getClass();
                    float f = measuredWidth;
                    if ((paint != null ? paint.measureText(str3) : 0.0f) > f) {
                        i5 = 3;
                        break loop1;
                    }
                    if (sb.length() == 0) {
                        str = str3;
                    } else {
                        str = ((Object) sb) + " " + str3;
                    }
                    if ((paint != null ? paint.measureText(str) : 0.0f) <= f) {
                        if (sb.length() > 0) {
                            sb.append(" ");
                        }
                        sb.append(str3);
                    } else {
                        i5++;
                        if (i5 > 2) {
                            break loop1;
                        }
                        sb.setLength(0);
                        sb.append(str3);
                    }
                    i++;
                }
            } else {
                collectionTake2 = EmptyList.INSTANCE;
                String[] strArr22 = (String[]) collectionTake2.toArray(new String[i2]);
                StringBuilder sb2 = new StringBuilder();
                length = strArr22.length;
                i = i2;
                while (true) {
                    if (i < length) {
                    }
                    i++;
                }
            }
            i4++;
            i2 = 0;
            i3 = 1;
        }
        TextView textView3 = this.secondLine;
        boolean z2 = textView3 != null && (text = textView3.getText()) != null && text.length() > 0 && i5 > 1;
        if (i5 <= 2 && !z2) {
            return false;
        }
        textView.setSingleLine(true);
        return true;
    }

    public final void updateRippleSize() {
        QSIconView qSIconView = this.iconView;
        Pair pair = new Pair(Integer.valueOf(qSIconView.getMeasuredWidth() / 2), Integer.valueOf(qSIconView.getMeasuredHeight() / 2));
        int iIntValue = ((Number) pair.component1()).intValue();
        int iIntValue2 = ((Number) pair.component2()).intValue();
        int iRoundToInt = MathKt__MathJVMKt.roundToInt(qSIconView.getHeight() * 0.43f);
        Drawable drawable = this.tileBackground;
        if (drawable != null) {
            drawable.setHotspotBounds(iIntValue - iRoundToInt, iIntValue2 - iRoundToInt, iIntValue + iRoundToInt, iIntValue2 + iRoundToInt);
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
            r2 = 2131101226(0x7f06062a, float:1.7814856E38)
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
            r2 = 2131101233(0x7f060631, float:1.781487E38)
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
