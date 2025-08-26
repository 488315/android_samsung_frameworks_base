package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
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
            updateLayout();
        }
        updateTouchTargetArea();
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b4, code lost:
    
        if (r10.length() <= 0) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b6, code lost:
    
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b8, code lost:
    
        if (r7 <= 2) goto L52;
     */
    @Override // android.widget.LinearLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onMeasure(int i, int i2) {
        String str;
        int i3 = 0;
        this.mLabel.setSingleLine(false);
        super.onMeasure(i, i2);
        if (!this.mCollapsedView) {
            String string = this.mLabel.getText().toString();
            if (!TextUtils.isEmpty(string)) {
                TextPaint paint = this.mLabel.getPaint();
                paint.setTextSize(this.mLabel.getTextSize());
                String[] strArrSplit = string.split("\n");
                int measuredWidth = (this.mLabel.getMeasuredWidth() - this.mLabel.getPaddingLeft()) - this.mLabel.getPaddingRight();
                int length = strArrSplit.length;
                int i4 = 0;
                int i5 = 0;
                loop0: while (true) {
                    if (i4 >= length) {
                        break;
                    }
                    String[] strArrSplit2 = strArrSplit[i4].split(" ");
                    StringBuilder sb = new StringBuilder();
                    int length2 = strArrSplit2.length;
                    int i6 = i3;
                    while (true) {
                        if (i6 >= length2) {
                            break;
                        }
                        String str2 = strArrSplit2[i6];
                        float f = measuredWidth;
                        if (paint.measureText(str2) > f) {
                            i5 = this.mMaxLabelLines + 1;
                            break loop0;
                        }
                        if (sb.length() > 0) {
                            str = ((Object) sb) + " " + str2;
                        } else {
                            str = str2;
                        }
                        if (paint.measureText(str) <= f) {
                            if (sb.length() > 0) {
                                sb.append(" ");
                            }
                            sb.append(str2);
                            i3 = 0;
                        } else {
                            i5++;
                            if (i5 > 2) {
                                break loop0;
                            }
                            i3 = 0;
                            sb.setLength(0);
                            sb.append(str2);
                        }
                        i6++;
                    }
                    i4++;
                }
                if (i5 <= this.mMaxLabelLines && (TextUtils.isEmpty(this.mSecondLine.getText()) || i5 <= this.mMaxLabelLines - 1)) {
                    return;
                }
            }
        }
        this.mLabel.setSingleLine();
        super.onMeasure(i, i2);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void setShowLabels(final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.SecQSTileView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mLabelContainer.setVisibility(z ? 0 : 8);
            }
        });
    }

    public final void updateLayout() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mIconFrame.getLayoutParams();
        layoutParams.height = -1;
        if (this.mIsLargeView && this.mIsNonBGTile) {
            layoutParams.width = this.mResourcePicker.getNoBGTileLabelStartMargin(((LinearLayout) this).mContext) + this.mResourcePicker.getNoBGTileIconStartMargin(((LinearLayout) this).mContext) + this.mResourcePicker.getNoBGTileIconSize(((LinearLayout) this).mContext);
            this.mIconFrame.setPaddingRelative(this.mResourcePicker.getNoBGTileIconStartMargin(((LinearLayout) this).mContext), 0, this.mResourcePicker.getNoBGTileLabelStartMargin(((LinearLayout) this).mContext), 0);
        } else {
            int tileIconStartMargin = this.mResourcePicker.getTileIconStartMargin(((LinearLayout) this).mContext) + this.mResourcePicker.getTileIconSize(((LinearLayout) this).mContext);
            SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
            layoutParams.width = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTileLabelStartMargin(((LinearLayout) this).mContext) + tileIconStartMargin;
            FrameLayout frameLayout = this.mIconFrame;
            int tileIconStartMargin2 = this.mResourcePicker.getTileIconStartMargin(((LinearLayout) this).mContext);
            SecQSPanelResourcePicker secQSPanelResourcePicker2 = this.mResourcePicker;
            frameLayout.setPaddingRelative(tileIconStartMargin2, 0, secQSPanelResourcePicker2.resourcePickHelper.getTargetPicker().getTileLabelStartMargin(((LinearLayout) this).mContext), 0);
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

    public SecQSTileView(Context context, boolean z, boolean z2, boolean z3, boolean z4, QSIconViewImpl qSIconViewImpl) throws Resources.NotFoundException {
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
        TextView textView3 = this.mLabel;
        boolean z5 = this.mIsLargeView;
        int i = R.dimen.sec_qs_tile_label_text_size;
        FontSizeUtils.updateFontSize(textView3, z5 ? R.dimen.sec_style_qs_tile_text_size : R.dimen.sec_qs_tile_label_text_size, 1.0f, 1.3f);
        FontSizeUtils.updateFontSize(this.mSecondLine, this.mIsLargeView ? R.dimen.sec_style_qs_tile_second_text_size : i, 1.0f, 1.3f);
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
            updateLayout();
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
