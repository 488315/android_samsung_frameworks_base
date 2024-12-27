package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import java.util.Arrays;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class SecQSTileView extends SecQSTileBaseView {
    public final boolean mIsLargeView;
    public final boolean mIsNonBGTile;
    public final TextView mLabel;
    public final ViewGroup mLabelContainer;
    public final int mMaxLabelLines;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final ColorStateList mSecLabelColor;
    public final ColorStateList mSecSubLabelColor;
    public final TextView mSecondLine;
    public int mState;

    public SecQSTileView(Context context) {
        this(context, false);
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileBaseView, com.android.systemui.plugins.qs.QSTileView
    public final int getDetailY() {
        return (this.mLabelContainer.getHeight() / 2) + this.mLabelContainer.getTop() + getTop();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getLabel() {
        return this.mLabel;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getLabelContainer() {
        return this.mLabelContainer;
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileBaseView
    public void handleStateChanged(QSTile.State state) {
        super.handleStateChanged(state);
        if (!Objects.equals(this.mLabel.getText(), state.label) || this.mState != state.state) {
            this.mLabel.setTextColor(this.mSecLabelColor);
            this.mState = state.state;
            this.mLabel.setText(state.label);
        }
        if (!Objects.equals(this.mSecondLine.getText(), state.secondaryLabel)) {
            this.mSecondLine.setText(state.secondaryLabel);
            this.mSecondLine.setTextColor(this.mSecSubLabelColor);
            this.mSecondLine.setVisibility((TextUtils.isEmpty(state.secondaryLabel) || this.mCollapsedView) ? 8 : 0);
        }
        this.mLabel.setEnabled(!state.disabledByPolicy);
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileBaseView
    public final void init(SecQSTileBaseView$$ExternalSyntheticLambda1 secQSTileBaseView$$ExternalSyntheticLambda1, SecQSTileBaseView$$ExternalSyntheticLambda1 secQSTileBaseView$$ExternalSyntheticLambda12, SecQSTileBaseView$$ExternalSyntheticLambda3 secQSTileBaseView$$ExternalSyntheticLambda3) {
        if (this.mIsLargeView) {
            super.init(secQSTileBaseView$$ExternalSyntheticLambda1, secQSTileBaseView$$ExternalSyntheticLambda12, secQSTileBaseView$$ExternalSyntheticLambda3);
            setOnClickListener(secQSTileBaseView$$ExternalSyntheticLambda12);
        } else {
            setOnClickListener(secQSTileBaseView$$ExternalSyntheticLambda1);
        }
        this.mIconFrame.setBackground(null);
        this.mIcon.setBackground(null);
        setOnLongClickListener(secQSTileBaseView$$ExternalSyntheticLambda3);
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileBaseView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mIsLargeView) {
            updateLayout$3$1();
        }
        updateTouchTargetArea();
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00b4, code lost:
    
        if (r10.length() <= 0) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b6, code lost:
    
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b8, code lost:
    
        if (r7 <= 2) goto L50;
     */
    @Override // android.widget.LinearLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onMeasure(int r17, int r18) {
        /*
            r16 = this;
            r0 = r16
            android.widget.TextView r1 = r0.mLabel
            r2 = 0
            r1.setSingleLine(r2)
            super.onMeasure(r17, r18)
            boolean r1 = r0.mCollapsedView
            if (r1 != 0) goto Ld5
            android.widget.TextView r1 = r0.mLabel
            java.lang.CharSequence r1 = r1.getText()
            java.lang.String r1 = r1.toString()
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 == 0) goto L21
            goto Ld5
        L21:
            android.widget.TextView r3 = r0.mLabel
            android.text.TextPaint r3 = r3.getPaint()
            android.widget.TextView r4 = r0.mLabel
            float r4 = r4.getTextSize()
            r3.setTextSize(r4)
            java.lang.String r4 = "\n"
            java.lang.String[] r1 = r1.split(r4)
            android.widget.TextView r4 = r0.mLabel
            int r4 = r4.getMeasuredWidth()
            android.widget.TextView r5 = r0.mLabel
            int r5 = r5.getPaddingLeft()
            int r4 = r4 - r5
            android.widget.TextView r5 = r0.mLabel
            int r5 = r5.getPaddingRight()
            int r4 = r4 - r5
            int r5 = r1.length
            r6 = r2
            r7 = r6
        L4d:
            if (r6 >= r5) goto Lbe
            r8 = r1[r6]
            java.lang.String r9 = " "
            java.lang.String[] r8 = r8.split(r9)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            int r11 = r8.length
            r12 = r2
        L5e:
            r13 = 2
            if (r12 >= r11) goto Lb0
            r14 = r8[r12]
            float r15 = r3.measureText(r14)
            float r2 = (float) r4
            int r15 = (r15 > r2 ? 1 : (r15 == r2 ? 0 : -1))
            if (r15 <= 0) goto L71
            int r1 = r0.mMaxLabelLines
            int r7 = r1 + 1
            goto Lbe
        L71:
            int r15 = r10.length()
            if (r15 <= 0) goto L8a
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r10)
            r15.append(r9)
            r15.append(r14)
            java.lang.String r15 = r15.toString()
            goto L8b
        L8a:
            r15 = r14
        L8b:
            float r15 = r3.measureText(r15)
            int r2 = (r15 > r2 ? 1 : (r15 == r2 ? 0 : -1))
            if (r2 > 0) goto La1
            int r2 = r10.length()
            if (r2 <= 0) goto L9c
            r10.append(r9)
        L9c:
            r10.append(r14)
            r2 = 0
            goto Lad
        La1:
            int r7 = r7 + 1
            if (r7 <= r13) goto La6
            goto Lbe
        La6:
            r2 = 0
            r10.setLength(r2)
            r10.append(r14)
        Lad:
            int r12 = r12 + 1
            goto L5e
        Lb0:
            int r8 = r10.length()
            if (r8 <= 0) goto Lbb
            int r7 = r7 + 1
            if (r7 <= r13) goto Lbb
            goto Lbe
        Lbb:
            int r6 = r6 + 1
            goto L4d
        Lbe:
            int r1 = r0.mMaxLabelLines
            if (r7 <= r1) goto Lc3
            goto Ld5
        Lc3:
            android.widget.TextView r1 = r0.mSecondLine
            java.lang.CharSequence r1 = r1.getText()
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto Ldd
            int r1 = r0.mMaxLabelLines
            int r1 = r1 + (-1)
            if (r7 <= r1) goto Ldd
        Ld5:
            android.widget.TextView r1 = r0.mLabel
            r1.setSingleLine()
            super.onMeasure(r17, r18)
        Ldd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tileimpl.SecQSTileView.onMeasure(int, int):void");
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void setShowLabels(final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.SecQSTileView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SecQSTileView.this.mLabelContainer.setVisibility(z ? 0 : 8);
            }
        });
    }

    public final void updateLayout$3$1() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mIconFrame.getLayoutParams();
        layoutParams.height = -1;
        if (this.mIsLargeView && this.mIsNonBGTile) {
            layoutParams.width = this.mResourcePicker.getNoBGTileLabelStartMargin(((LinearLayout) this).mContext) + this.mResourcePicker.getNoBGTileIconStartMargin(((LinearLayout) this).mContext) + this.mResourcePicker.getNoBGTileIconSize(((LinearLayout) this).mContext);
            this.mIconFrame.setPaddingRelative(this.mResourcePicker.getNoBGTileIconStartMargin(((LinearLayout) this).mContext), 0, this.mResourcePicker.getNoBGTileLabelStartMargin(((LinearLayout) this).mContext), 0);
        } else {
            int tileIconSize = this.mResourcePicker.getTileIconSize(((LinearLayout) this).mContext);
            SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
            int tileIconStartMargin = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTileIconStartMargin(((LinearLayout) this).mContext) + tileIconSize;
            SecQSPanelResourcePicker secQSPanelResourcePicker2 = this.mResourcePicker;
            layoutParams.width = secQSPanelResourcePicker2.resourcePickHelper.getTargetPicker().getTileLabelStartMargin(((LinearLayout) this).mContext) + tileIconStartMargin;
            FrameLayout frameLayout = this.mIconFrame;
            SecQSPanelResourcePicker secQSPanelResourcePicker3 = this.mResourcePicker;
            int tileIconStartMargin2 = secQSPanelResourcePicker3.resourcePickHelper.getTargetPicker().getTileIconStartMargin(((LinearLayout) this).mContext);
            SecQSPanelResourcePicker secQSPanelResourcePicker4 = this.mResourcePicker;
            frameLayout.setPaddingRelative(tileIconStartMargin2, 0, secQSPanelResourcePicker4.resourcePickHelper.getTargetPicker().getTileLabelStartMargin(((LinearLayout) this).mContext), 0);
        }
        this.mIconFrame.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mLabelContainer.getLayoutParams();
        layoutParams2.height = -1;
        layoutParams2.weight = 1.0f;
        this.mLabelContainer.setLayoutParams(layoutParams2);
        ((LinearLayout) this.mLabelContainer).setGravity(17);
        this.mLabel.setGravity(8388611);
        this.mSecondLine.setGravity(8388611);
    }

    public void updateTouchTargetArea() {
        if (this.mIsLargeView) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mIconFrame.getLayoutParams();
        layoutParams.width = -1;
        this.mIconFrame.setLayoutParams(layoutParams);
        setLayoutParams(new LinearLayout.LayoutParams(this.mResourcePicker.getTouchIconSize(((LinearLayout) this).mContext), this.mResourcePicker.getTouchIconSize(((LinearLayout) this).mContext)));
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mLabelContainer.getLayoutParams();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        layoutParams2.height = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getLabelHeight(((LinearLayout) this).mContext);
        this.mLabelContainer.setLayoutParams(layoutParams2);
    }

    public SecQSTileView(Context context, QSIconViewImpl qSIconViewImpl) {
        this(context, false, false, false, false, qSIconViewImpl);
    }

    public SecQSTileView(Context context, boolean z) {
        this(context, z, false, false, false);
    }

    public SecQSTileView(Context context, boolean z, boolean z2, boolean z3, boolean z4) {
        this(context, z, z2, z3, z4, null);
    }

    public SecQSTileView(Context context, boolean z, boolean z2, boolean z3, boolean z4, QSIconViewImpl qSIconViewImpl) {
        super(context, z, qSIconViewImpl);
        this.mMaxLabelLines = 2;
        ((LinearLayout) this).mContext = context;
        this.mIsLargeView = z2;
        this.mIsNonBGTile = z4;
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        setClipChildren(false);
        setClipToPadding(false);
        setClickable(true);
        setId(View.generateViewId());
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.sec_qs_tile_label, (ViewGroup) this, false);
        this.mLabelContainer = viewGroup;
        viewGroup.setClipChildren(false);
        this.mLabelContainer.setClipToPadding(false);
        if (!z2) {
            this.mLabelContainer.setVisibility(8);
        }
        TextView textView = (TextView) this.mLabelContainer.findViewById(R.id.tile_label);
        this.mLabel = textView;
        textView.setSelected(true);
        this.mLabel.setBreakStrategy(1);
        TextView textView2 = (TextView) this.mLabelContainer.findViewById(R.id.app_label);
        this.mSecondLine = textView2;
        textView2.setSelected(true);
        addView(this.mLabelContainer);
        setOrientation(!z2 ? 1 : 0);
        if (z2) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(context.getResources().getDimensionPixelSize(R.dimen.large_tile_width), context.getResources().getDimensionPixelSize(R.dimen.large_tile_height));
            layoutParams.weight = 1.0f;
            setLayoutParams(layoutParams);
            setGravity(16);
            setBackground(context.getDrawable(R.drawable.sec_large_button_ripple_background));
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.large_tile_label_margin_end);
            ViewGroup viewGroup2 = this.mLabelContainer;
            viewGroup2.setPaddingRelative(viewGroup2.getPaddingStart(), this.mLabelContainer.getPaddingTop(), dimensionPixelSize, this.mLabelContainer.getPaddingBottom());
            this.mLabel.setTextAlignment(5);
            this.mLabel.setLayoutDirection(3);
            this.mSecondLine.setTextAlignment(5);
            this.mSecondLine.setLayoutDirection(3);
            updateLayout$3$1();
        } else {
            setBackground(context.getDrawable(R.drawable.sec_tile_view_ripple_background));
        }
        float[] fArr = new float[8];
        Arrays.fill(fArr, ((LinearLayout) this).mContext.getResources().getInteger(R.integer.sec_style_qs_tile_label_background_radius));
        new ShapeDrawable(new RoundRectShape(fArr, null, null)).getPaint().setColor(-1);
        this.mSecLabelColor = ColorStateList.valueOf(((LinearLayout) this).mContext.getColor(R.color.qs_tile_label));
        this.mSecSubLabelColor = ColorStateList.valueOf(((LinearLayout) this).mContext.getColor(R.color.qs_tile_sub_label));
        if (!z2) {
            setPadding(0, 0, 0, 0);
            updateTouchTargetArea();
            setGravity(49);
        }
        if (z2) {
            this.mIcon.setFocusable(true);
            this.mIcon.setImportantForAccessibility(1);
        } else {
            this.mIcon.setFocusable(false);
            this.mIcon.setImportantForAccessibility(2);
        }
        setFocusable(true);
        setImportantForAccessibility(1);
    }
}
