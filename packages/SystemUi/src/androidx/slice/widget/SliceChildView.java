package androidx.slice.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda4;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class SliceChildView extends FrameLayout {
    public int mInsetBottom;
    public int mInsetEnd;
    public int mInsetStart;
    public int mInsetTop;
    public long mLastUpdated;
    public SliceAdapter mLoadingListener;
    public VolumePanelDialog$$ExternalSyntheticLambda4 mObserver;
    public RowStyle mRowStyle;
    public boolean mShowLastUpdated;
    public SliceStyle mSliceStyle;
    public int mTintColor;
    public SliceViewPolicy mViewPolicy;

    public SliceChildView(Context context) {
        super(context);
        this.mTintColor = -1;
        this.mLastUpdated = -1L;
    }

    public Set getLoadingActions() {
        return null;
    }

    public final int getMode() {
        SliceViewPolicy sliceViewPolicy = this.mViewPolicy;
        if (sliceViewPolicy != null) {
            return sliceViewPolicy.mMode;
        }
        return 2;
    }

    public abstract void resetView();

    public void setInsets(int i, int i2, int i3, int i4) {
        this.mInsetStart = i;
        this.mInsetTop = i2;
        this.mInsetEnd = i3;
        this.mInsetBottom = i4;
    }

    public void setLastUpdated(long j) {
        this.mLastUpdated = j;
    }

    public void setPolicy(SliceViewPolicy sliceViewPolicy) {
        this.mViewPolicy = sliceViewPolicy;
    }

    public void setShowLastUpdated(boolean z) {
        this.mShowLastUpdated = z;
    }

    public void setSliceActionListener(VolumePanelDialog$$ExternalSyntheticLambda4 volumePanelDialog$$ExternalSyntheticLambda4) {
        this.mObserver = volumePanelDialog$$ExternalSyntheticLambda4;
    }

    public void setStyle(SliceStyle sliceStyle, RowStyle rowStyle) {
        this.mSliceStyle = sliceStyle;
        this.mRowStyle = rowStyle;
    }

    public void setTint(int i) {
        this.mTintColor = i;
    }

    public SliceChildView(Context context, AttributeSet attributeSet) {
        this(context);
    }

    public void setAllowTwoLines(boolean z) {
    }

    public void setLoadingActions(Set set) {
    }

    public void setSliceActions(List list) {
    }

    public void setSliceContent(ListContent listContent) {
    }

    public void setSliceItem(SliceContent sliceContent, boolean z, int i, int i2, VolumePanelDialog$$ExternalSyntheticLambda4 volumePanelDialog$$ExternalSyntheticLambda4) {
    }
}
