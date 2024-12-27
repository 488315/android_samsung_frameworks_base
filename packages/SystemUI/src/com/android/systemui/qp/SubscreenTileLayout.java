package com.android.systemui.qp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qp.SubroomQuickSettingsQSPanelBaseView;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelControllerBase$TileRecord;
import com.android.systemui.qs.tileimpl.QSIconViewImpl;
import com.android.systemui.qs.tileimpl.SecQSTileBaseView;
import java.util.ArrayList;
import java.util.Iterator;

public class SubscreenTileLayout extends ViewGroup implements QSPanel.QSTileLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayList mBoundaryBox;
    public int mCellHeight;
    public int mCellMarginHorizontal;
    public int mCellMarginVertical;
    public int mCellWidth;
    public int mColumns;
    public int mLastTileBottom;
    public boolean mListening;
    public int mMaxAllowedRows;
    public int mMaxCellHeight;
    public final ArrayList mRecords;
    public int mRows;
    public float mSquishinessFraction;
    public int mTileLayoutHeight;
    public int mTileVerticalMargin;

    public SubscreenTileLayout(Context context) {
        this(context, null);
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public final void addTile(QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord) {
        this.mRecords.add(qSPanelControllerBase$TileRecord);
        qSPanelControllerBase$TileRecord.tile.setListening(this, this.mListening);
        qSPanelControllerBase$TileRecord.tileView.getIcon().setOnLongClickListener(new View.OnLongClickListener(this) { // from class: com.android.systemui.qp.SubscreenTileLayout.1
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return true;
            }
        });
        QSTileView qSTileView = qSPanelControllerBase$TileRecord.tileView;
        if (qSTileView != null) {
            qSTileView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            addView(qSTileView);
        }
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public final int getTilesHeight() {
        return getPaddingBottom() + this.mLastTileBottom;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final int indexOf(SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord) {
        for (int i = 0; i < this.mRecords.size(); i++) {
            QSTile qSTile = ((QSPanelControllerBase$TileRecord) this.mRecords.get(i)).tile;
            if (subscreenTileRecord.mTilespec.equals(qSTile.getTileSpec())) {
                Log.d("SubscreenTileLayout", "diffInfo.spec = " + qSTile.getTileSpec() + " i = " + i);
                return i;
            }
        }
        Log.d("SubscreenTileLayout", "diffInfo.spec is null");
        return -1;
    }

    public final void layoutTileRecords$1(int i, boolean z) {
        boolean z2 = getLayoutDirection() == 1;
        this.mLastTileBottom = 0;
        int min = Math.min(i, this.mRows * this.mColumns);
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < min) {
            if (i3 == this.mColumns) {
                i4++;
                i3 = 0;
            }
            QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = (QSPanelControllerBase$TileRecord) this.mRecords.get(i2);
            int i5 = this.mCellHeight;
            int i6 = i4 == 0 ? i5 * i4 : (i5 + this.mTileVerticalMargin) * i4;
            int i7 = z2 ? (this.mColumns - i3) - 1 : i3;
            int i8 = this.mCellWidth;
            int i9 = (this.mCellMarginHorizontal + i8) * i7;
            int i10 = i8 + i9;
            int i11 = this.mCellHeight + i6;
            if (z) {
                qSPanelControllerBase$TileRecord.tileView.layout(i9, i6, i10, i11);
            } else {
                qSPanelControllerBase$TileRecord.tileView.setLeftTopRightBottom(i9, i6, i10, i11);
            }
            qSPanelControllerBase$TileRecord.tileView.setPosition(i2);
            this.mLastTileBottom = i6 + ((int) (qSPanelControllerBase$TileRecord.tileView.getMeasuredHeight() * ((this.mSquishinessFraction * 0.9f) + 0.1f)));
            i2++;
            i3++;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        layoutTileRecords$1(this.mRecords.size(), true);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int color;
        int size = this.mRecords.size();
        int size2 = View.MeasureSpec.getSize(i);
        int paddingStart = (size2 - getPaddingStart()) - getPaddingEnd();
        if (View.MeasureSpec.getMode(i2) == 0) {
            int i3 = this.mColumns;
            this.mRows = ((size + i3) - 1) / i3;
        }
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(size2, paddingStart, "onMeasure width: ", " availableWidth: ", " mRows: ");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, this.mRows, " numTiles: ", size, " mColumns: ");
        RecyclerView$$ExternalSyntheticOutline0.m(this.mColumns, "SubscreenTileLayout", m);
        int i4 = this.mTileLayoutHeight;
        if (i4 < this.mMaxCellHeight) {
            this.mCellHeight = ((ViewGroup) this).mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_tile_height_no_label);
        } else {
            this.mCellHeight = ((ViewGroup) this).mContext.getResources().getDimensionPixelSize(R.dimen.qs_tile_subscreen_cell_height_no_label);
        }
        try {
            if (QpRune.QUICK_SUBSCREEN_PANEL) {
                this.mTileVerticalMargin = ((ViewGroup) this).mContext.getResources().getDimensionPixelSize(R.dimen.qs_tile_subscreen_vertical_margin);
                this.mCellMarginHorizontal = ((ViewGroup) this).mContext.getResources().getDimensionPixelSize(R.dimen.qs_tile_subscreen_horizontal_margin);
            } else {
                int i5 = this.mCellHeight;
                int i6 = this.mRows;
                this.mTileVerticalMargin = (i4 - (i5 * i6)) / (i6 - 1);
                int i7 = this.mCellWidth;
                int i8 = this.mColumns;
                this.mCellMarginHorizontal = (size2 - (i7 * i8)) / (i8 - 1);
            }
            Log.d("SubscreenTileLayout", "onMeasure, pageHeight: " + i4 + " mMaxCellHeight: " + this.mMaxCellHeight + " pageWidth: " + size2 + " mCellMarginHorizontal :" + this.mCellMarginHorizontal + " mCellHeight: " + this.mCellHeight + ",mCellWidth: " + this.mCellWidth + ",mTileVerticalMargin: " + this.mTileVerticalMargin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mCellHeight, 1073741824);
        int dimensionPixelSize = ((ViewGroup) this).mContext.getResources().getDimensionPixelSize(R.dimen.subscreen_qs_tile_image_icon_size);
        int dimensionPixelSize2 = ((ViewGroup) this).mContext.getResources().getDimensionPixelSize(R.dimen.subscreen_qs_tile_icon_size);
        Iterator it = this.mRecords.iterator();
        View view = this;
        while (it.hasNext()) {
            final QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = (QSPanelControllerBase$TileRecord) it.next();
            if (qSPanelControllerBase$TileRecord.tileView.getVisibility() != 8) {
                QSTileView qSTileView = qSPanelControllerBase$TileRecord.tileView;
                qSTileView.setShowLabels(false);
                SecQSTileBaseView secQSTileBaseView = (SecQSTileBaseView) qSTileView;
                QSIconViewImpl qSIconViewImpl = secQSTileBaseView.mIcon;
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) qSIconViewImpl.getLayoutParams();
                layoutParams.width = dimensionPixelSize;
                layoutParams.height = dimensionPixelSize;
                QSTile qSTile = qSPanelControllerBase$TileRecord.tile;
                ((ImageView) qSTileView.getIcon().getIconView()).setColorFilter(((ViewGroup) this).mContext.getColor(qSTile.getState().state == 2 ? R.color.subscreen_qs_tile_icon_on_color : R.color.subscreen_qs_tile_icon_off_color), PorterDuff.Mode.SRC_IN);
                qSIconViewImpl.setLayoutParams(layoutParams);
                ImageView imageView = secQSTileBaseView.mBg;
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams2.width = dimensionPixelSize2;
                layoutParams2.height = dimensionPixelSize2;
                imageView.setLayoutParams(layoutParams2);
                int i9 = qSTile.getState().state;
                if (i9 == 0) {
                    color = ((ViewGroup) this).mContext.getColor(R.color.subscreen_qs_tile_round_background_dim);
                } else if (i9 == 1) {
                    color = ((ViewGroup) this).mContext.getColor(R.color.subscreen_qs_tile_round_background_off);
                } else if (i9 != 2) {
                    Log.e("SubscreenTileLayout", "getCircleColor: invalid state[" + i9 + "]");
                    color = ((ViewGroup) this).mContext.getColor(R.color.subscreen_qs_tile_round_background_off);
                } else {
                    color = ((ViewGroup) this).mContext.getColor(R.color.subscreen_qs_tile_round_background_on);
                }
                imageView.setImageTintList(ColorStateList.valueOf(color));
                FrameLayout frameLayout = secQSTileBaseView.mIconFrame;
                frameLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                qSIconViewImpl.setImportantForAccessibility(2);
                frameLayout.setImportantForAccessibility(1);
                ViewCompat.setAccessibilityDelegate(frameLayout, new AccessibilityDelegateCompat() { // from class: com.android.systemui.qp.SubscreenTileLayout.4
                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                        this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat.mInfo);
                        QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord2 = qSPanelControllerBase$TileRecord;
                        boolean z = qSPanelControllerBase$TileRecord2.tile.getState().state == 2;
                        accessibilityNodeInfoCompat.setClassName(Button.class.getName());
                        accessibilityNodeInfoCompat.setText(((Object) qSPanelControllerBase$TileRecord2.tile.getTileLabel()) + "," + SubscreenTileLayout.this.getResources().getString(z ? R.string.switch_bar_on : R.string.switch_bar_off));
                    }
                });
                Drawable tileBackground = secQSTileBaseView.getTileBackground();
                if (tileBackground instanceof RippleDrawable) {
                    ((RippleDrawable) tileBackground).setColor(ColorStateList.valueOf(((ViewGroup) this).mContext.getColor(R.color.subscreen_qs_ripple_background)));
                }
                qSTileView.measure(View.MeasureSpec.makeMeasureSpec(this.mCellWidth, 1073741824), makeMeasureSpec);
                view = qSTileView.updateAccessibilityOrder(view);
            }
        }
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            super.onMeasure(i, i2);
        }
    }

    @Override // android.view.ViewGroup
    public final void removeAllViews() {
        Iterator it = this.mRecords.iterator();
        while (it.hasNext()) {
            ((QSPanelControllerBase$TileRecord) it.next()).tile.setListening(this, false);
        }
        this.mRecords.clear();
        this.mBoundaryBox.clear();
        super.removeAllViews();
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public final void removeTile(QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord) {
        this.mRecords.remove(qSPanelControllerBase$TileRecord);
        qSPanelControllerBase$TileRecord.tile.setListening(this, false);
        removeView(qSPanelControllerBase$TileRecord.tileView);
    }

    public final void selectTile(SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord, boolean z) {
        int indexOf = indexOf(subscreenTileRecord);
        if (indexOf < 0) {
            return;
        }
        if (indexOf >= this.mRecords.size()) {
            indexOf = this.mRecords.size() - 1;
        }
        QSTileView qSTileView = ((QSPanelControllerBase$TileRecord) this.mRecords.get(indexOf)).tileView;
        if (z) {
            qSTileView.setAlpha(0.0f);
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(indexOf, "selectTile position = ", "SubscreenTileLayout");
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public final void setListening(boolean z, UiEventLogger uiEventLogger) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        Iterator it = this.mRecords.iterator();
        while (it.hasNext()) {
            ((QSPanelControllerBase$TileRecord) it.next()).tile.setListening(this, this.mListening);
        }
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public final boolean updateResources() {
        Resources resources = ((ViewGroup) this).mContext.getResources();
        this.mCellMarginHorizontal = resources.getDimensionPixelSize(R.dimen.qs_tile_margin_horizontal);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.qs_tile_margin_vertical);
        this.mCellMarginVertical = dimensionPixelSize;
        if (dimensionPixelSize == 0) {
            this.mCellMarginVertical = this.mCellMarginHorizontal;
        }
        this.mMaxCellHeight = 100;
        this.mMaxAllowedRows = 2;
        this.mCellWidth = resources.getDimensionPixelSize(R.dimen.subscreen_qs_tile_icon_size);
        Log.d("SubscreenTileLayout", "updateResources columns: 4 mMaxCellHeight: " + this.mMaxCellHeight + " mMaxAllowedRows: " + this.mMaxAllowedRows + " mCellWidth: " + this.mCellWidth);
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder("mColumns = ");
        sb.append(this.mColumns);
        arrayList.add(sb.toString());
        arrayList.add("mRows = " + this.mRows);
        arrayList.add("mMaxAllowedRows = " + this.mMaxAllowedRows);
        arrayList.add("mCellHeight = " + this.mCellHeight);
        arrayList.add("mMaxCellHeight = " + this.mMaxCellHeight);
        arrayList.add("mCellMarginHorizontal = " + this.mCellMarginHorizontal);
        arrayList.add("mCellMarginVertical = " + this.mCellMarginVertical);
        arrayList.add("mTileVerticalMargin = " + this.mTileVerticalMargin);
        arrayList.add("mTileLayoutHeight = " + this.mTileLayoutHeight);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
        }
        if (this.mColumns == 4) {
            return false;
        }
        this.mColumns = 4;
        requestLayout();
        return true;
    }

    public SubscreenTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRecords = new ArrayList();
        this.mMaxAllowedRows = 1;
        this.mSquishinessFraction = 1.0f;
        this.mBoundaryBox = new ArrayList();
        this.mTileLayoutHeight = 1;
        setFocusableInTouchMode(true);
        updateResources();
    }

    public final void removeTile(SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord) {
        int indexOf = indexOf(subscreenTileRecord);
        ExifInterface$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(indexOf, "removeTile index = ", "tile = "), subscreenTileRecord.mTilespec, "SubscreenTileLayout");
        QSPanelControllerBase$TileRecord qSPanelControllerBase$TileRecord = (QSPanelControllerBase$TileRecord) this.mRecords.get(indexOf);
        this.mRecords.remove(indexOf);
        removeView(qSPanelControllerBase$TileRecord.tileView);
    }

    public final void addTile(SubroomQuickSettingsQSPanelBaseView.SubscreenTileRecord subscreenTileRecord, int i) {
        int min = Math.min(this.mColumns * this.mRows, this.mRecords.size());
        if (i > min) {
            i = min;
        }
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, min, "addTile position = ", "total = ", "idx = ");
        m.append(i);
        m.append(" spec = ");
        ExifInterface$$ExternalSyntheticOutline0.m(m, subscreenTileRecord.mTilespec, "withAnimationfalse", "SubscreenTileLayout");
        this.mRecords.add(i, subscreenTileRecord);
        QSTileView qSTileView = subscreenTileRecord.tileView;
        if (qSTileView != null) {
            qSTileView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            addView(qSTileView);
        }
    }
}
