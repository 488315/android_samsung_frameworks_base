package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.RefactorFlag;
import com.android.systemui.flags.UnreleasedFlag;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.tileimpl.HeightOverrideable;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;
import com.android.systemui.util.Utils;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class TileLayout extends ViewGroup implements SecQSPanel.QSTileLayout {
    public int mCellHeight;
    public int mCellMarginHorizontal;
    public int mCellMarginVertical;
    public int mCellWidth;
    public int mColumns;
    public int mEstimatedCellHeight;
    public final Boolean mIsSmallLandscapeLockscreenEnabled;
    public final boolean mLessRows;
    public boolean mListening;
    public int mMaxAllowedRows;
    public int mMaxColumns;
    public int mMinRows;
    public final ArrayList mRecords;
    public int mResourceCellHeight;
    public final int mResourceCellHeightResId;
    public int mResourceColumns;
    public int mRows;
    public int mSidePadding;
    public float mSquishinessFraction;
    public final TextView mTempTextView;

    public TileLayout(Context context) {
        this(context, null);
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void addTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        this.mRecords.add(tileRecord);
        boolean z = this.mListening;
        QSTile qSTile = tileRecord.tile;
        qSTile.setListening(this, z);
        addTileView$1(tileRecord);
        ((QSTileImpl) qSTile).saveTileIconAsImage();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void addTileView$1(SecQSPanelControllerBase.TileRecord tileRecord) {
        QSTileView qSTileView = tileRecord.tileView;
        if (qSTileView instanceof HeightOverrideable) {
            float f = this.mSquishinessFraction;
            QSTileViewImpl qSTileViewImpl = (QSTileViewImpl) ((HeightOverrideable) qSTileView);
            if (qSTileViewImpl.squishinessFraction != f) {
                qSTileViewImpl.squishinessFraction = f;
                qSTileViewImpl.updateHeight();
            }
        }
        addView(qSTileView);
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final void layoutTileRecords(int i, boolean z) {
        boolean z2 = getLayoutDirection() == 1;
        int min = Math.min(i, this.mRows * this.mColumns);
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < min) {
            if (i3 == this.mColumns) {
                i4++;
                i3 = 0;
            }
            SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) this.mRecords.get(i2);
            int i5 = (int) (((this.mCellHeight * ((this.mSquishinessFraction * 0.9f) + 0.1f)) + this.mCellMarginVertical) * i4);
            int i6 = z2 ? (this.mColumns - i3) - 1 : i3;
            int paddingStart = getPaddingStart() + this.mSidePadding;
            int i7 = this.mCellWidth;
            int i8 = ((this.mCellMarginHorizontal + i7) * i6) + paddingStart;
            int i9 = i7 + i8;
            int measuredHeight = tileRecord.tileView.getMeasuredHeight() + i5;
            QSTileView qSTileView = tileRecord.tileView;
            if (z) {
                qSTileView.layout(i8, i5, i9, measuredHeight);
            } else {
                qSTileView.setLeftTopRightBottom(i8, i5, i9, measuredHeight);
            }
            qSTileView.setPosition(i2);
            qSTileView.getMeasuredHeight();
            i2++;
            i3++;
        }
    }

    public final void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setCollectionInfo(new AccessibilityNodeInfo.CollectionInfo(this.mRecords.size(), 1, false));
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        layoutTileRecords(this.mRecords.size(), true);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int size = this.mRecords.size();
        int size2 = View.MeasureSpec.getSize(i);
        int paddingStart = (size2 - getPaddingStart()) - getPaddingEnd();
        if (View.MeasureSpec.getMode(i2) == 0) {
            this.mRows = ((size + r8) - 1) / this.mColumns;
        }
        int i3 = this.mColumns;
        this.mCellWidth = ((paddingStart - (this.mCellMarginHorizontal * (i3 - 1))) - (this.mSidePadding * 2)) / i3;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(this.mResourceCellHeight, this.mEstimatedCellHeight), 1073741824);
        Iterator it = this.mRecords.iterator();
        View view = this;
        while (it.hasNext()) {
            SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) it.next();
            if (tileRecord.tileView.getVisibility() != 8) {
                int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mCellWidth, 1073741824);
                QSTileView qSTileView = tileRecord.tileView;
                qSTileView.measure(makeMeasureSpec2, makeMeasureSpec);
                view = qSTileView.updateAccessibilityOrder(view);
                this.mCellHeight = qSTileView.getMeasuredHeight();
            }
        }
        int i4 = this.mCellHeight;
        int i5 = this.mCellMarginVertical;
        int i6 = ((i4 + i5) * this.mRows) - i5;
        if (i6 < 0) {
            i6 = 0;
        }
        setMeasuredDimension(size2, i6);
    }

    @Override // android.view.ViewGroup
    public final void removeAllViews() {
        Iterator it = this.mRecords.iterator();
        while (it.hasNext()) {
            ((SecQSPanelControllerBase.TileRecord) it.next()).tile.setListening(this, false);
        }
        this.mRecords.clear();
        super.removeAllViews();
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void removeTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        this.mRecords.remove(tileRecord);
        tileRecord.tile.setListening(this, false);
        removeView(tileRecord.tileView);
    }

    public void setListening(boolean z, UiEventLogger uiEventLogger) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        Iterator it = this.mRecords.iterator();
        while (it.hasNext()) {
            ((SecQSPanelControllerBase.TileRecord) it.next()).tile.setListening(this, this.mListening);
        }
    }

    public boolean updateMaxRows(int i, int i2) {
        int i3 = i + this.mCellMarginVertical;
        int i4 = this.mRows;
        int max = i3 / (Math.max(this.mResourceCellHeight, this.mEstimatedCellHeight) + this.mCellMarginVertical);
        this.mRows = max;
        int i5 = this.mMinRows;
        if (max < i5) {
            this.mRows = i5;
        } else {
            int i6 = this.mMaxAllowedRows;
            if (max >= i6) {
                this.mRows = i6;
            }
        }
        int i7 = this.mRows;
        int i8 = this.mColumns;
        int i9 = ((i2 + i8) - 1) / i8;
        if (i7 > i9) {
            this.mRows = i9;
        }
        return i4 != this.mRows;
    }

    public boolean updateResources() {
        Resources resources = getResources();
        this.mResourceColumns = Math.max(1, (this.mIsSmallLandscapeLockscreenEnabled.booleanValue() && ((ViewGroup) this).mContext.getResources().getBoolean(R.bool.is_small_screen_landscape)) ? resources.getInteger(R.integer.small_land_lockscreen_quick_settings_num_columns) : resources.getInteger(R.integer.quick_settings_num_columns));
        this.mResourceCellHeight = resources.getDimensionPixelSize(this.mResourceCellHeightResId);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.qs_tile_margin_horizontal);
        this.mCellMarginHorizontal = dimensionPixelSize;
        this.mSidePadding = (this instanceof SideLabelTileLayout) ^ true ? dimensionPixelSize / 2 : 0;
        this.mCellMarginVertical = resources.getDimensionPixelSize(R.dimen.qs_tile_margin_vertical);
        int max = Math.max(1, (this.mIsSmallLandscapeLockscreenEnabled.booleanValue() && ((ViewGroup) this).mContext.getResources().getBoolean(R.bool.is_small_screen_landscape)) ? resources.getInteger(R.integer.small_land_lockscreen_quick_settings_max_rows) : resources.getInteger(R.integer.quick_settings_max_rows));
        this.mMaxAllowedRows = max;
        if (this.mLessRows) {
            this.mMaxAllowedRows = Math.max(this.mMinRows, max - 1);
        }
        this.mTempTextView.dispatchConfigurationChanged(((ViewGroup) this).mContext.getResources().getConfiguration());
        FontSizeUtils.updateFontSize(this.mTempTextView, R.dimen.qs_tile_text_size);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mTempTextView.measure(makeMeasureSpec, makeMeasureSpec);
        this.mEstimatedCellHeight = (((ViewGroup) this).mContext.getResources().getDimensionPixelSize(R.dimen.qs_tile_padding) * 2) + (this.mTempTextView.getMeasuredHeight() * 2);
        int i = this.mColumns;
        int min = Math.min(this.mResourceColumns, this.mMaxColumns);
        this.mColumns = min;
        if (i == min) {
            return false;
        }
        requestLayout();
        return true;
    }

    public TileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mResourceCellHeightResId = R.dimen.qs_tile_height;
        boolean z = true;
        this.mRows = 1;
        this.mRecords = new ArrayList();
        this.mMaxAllowedRows = 3;
        this.mMinRows = 1;
        this.mMaxColumns = 100;
        this.mSquishinessFraction = 1.0f;
        UnreleasedFlag unreleasedFlag = Flags.LOCKSCREEN_ENABLE_LANDSCAPE;
        RefactorFlag.Companion companion = RefactorFlag.Companion;
        companion.getClass();
        Boolean bool = (Boolean) RefactorFlag.Companion.forView$default(companion, unreleasedFlag).isEnabled$delegate.getValue();
        bool.booleanValue();
        this.mIsSmallLandscapeLockscreenEnabled = bool;
        if (Settings.System.getInt(context.getContentResolver(), "qs_less_rows", 0) == 0 && !Utils.useQsMediaPlayer(context)) {
            z = false;
        }
        this.mLessRows = z;
        this.mTempTextView = new TextView(context);
        updateResources();
    }
}
