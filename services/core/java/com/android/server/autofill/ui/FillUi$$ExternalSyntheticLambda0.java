package com.android.server.autofill.ui;

import android.graphics.Point;
import android.view.View;
import android.widget.Filter;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.autofill.Helper;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class FillUi$$ExternalSyntheticLambda0
        implements Filter.FilterListener {
    public final /* synthetic */ FillUi f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ FillUi$$ExternalSyntheticLambda0(FillUi fillUi, int i) {
        this.f$0 = fillUi;
        this.f$1 = i;
    }

    @Override // android.widget.Filter.FilterListener
    public final void onFilterComplete(int i) {
        boolean z;
        boolean z2;
        FillUi fillUi = this.f$0;
        int i2 = this.f$1;
        if (fillUi.mDestroyed) {
            return;
        }
        String str = fillUi.mFilterText;
        int length = str == null ? 0 : str.length();
        AutoFillUI.AnonymousClass1 anonymousClass1 = fillUi.mCallback;
        if (i <= 0) {
            if (Helper.sDebug) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        length, "No dataset matches filter with ", " chars", "FillUi");
            }
            anonymousClass1.requestHideFillUi();
            return;
        }
        int i3 = fillUi.mMaxInputLengthForAutofill;
        if (length > i3) {
            if (Helper.sDebug) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i3,
                        "Not showing fill UI because user entered more than ",
                        " characters",
                        "FillUi");
            }
            anonymousClass1.requestHideFillUi();
            return;
        }
        int i4 = fillUi.mVisibleDatasetsMaxCount;
        FillUi.ItemsAdapter itemsAdapter = fillUi.mAdapter;
        if (itemsAdapter == null) {
            z2 = false;
        } else {
            if (!fillUi.mFullScreen) {
                if (itemsAdapter.getCount() <= 0) {
                    if (fillUi.mContentWidth != 0) {
                        fillUi.mContentWidth = 0;
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (fillUi.mContentHeight != 0) {
                        fillUi.mContentHeight = 0;
                    }
                } else {
                    Point point = fillUi.mTempPoint;
                    FillUi.resolveMaxWindowSize(fillUi.mContext, point);
                    fillUi.mContentWidth = 0;
                    fillUi.mContentHeight = 0;
                    int makeMeasureSpec =
                            View.MeasureSpec.makeMeasureSpec(point.x, Integer.MIN_VALUE);
                    int makeMeasureSpec2 =
                            View.MeasureSpec.makeMeasureSpec(point.y, Integer.MIN_VALUE);
                    int count = itemsAdapter.getCount();
                    View view = fillUi.mHeader;
                    if (view != null) {
                        view.measure(makeMeasureSpec, makeMeasureSpec2);
                        z =
                                fillUi.updateWidth(fillUi.mHeader, point)
                                        | fillUi.updateHeight(fillUi.mHeader, point);
                    } else {
                        z = false;
                    }
                    for (int i5 = 0; i5 < count; i5++) {
                        View view2 =
                                ((FillUi.ViewItem)
                                                ((ArrayList) itemsAdapter.mFilteredItems).get(i5))
                                        .view;
                        view2.measure(makeMeasureSpec, makeMeasureSpec2);
                        z |= fillUi.updateWidth(view2, point);
                        if (i5 < i4) {
                            z |= fillUi.updateHeight(view2, point);
                        }
                    }
                    View view3 = fillUi.mFooter;
                    if (view3 != null) {
                        view3.measure(makeMeasureSpec, makeMeasureSpec2);
                        z2 =
                                fillUi.updateHeight(fillUi.mFooter, point)
                                        | fillUi.updateWidth(fillUi.mFooter, point)
                                        | z;
                    } else {
                        z2 = z;
                    }
                }
            }
            z2 = true;
        }
        if (z2) {
            fillUi.requestShowFillUi();
        }
        if (itemsAdapter.getCount() > i4) {
            fillUi.mListView.setVerticalScrollBarEnabled(true);
            fillUi.mListView.onVisibilityAggregated(true);
        } else {
            fillUi.mListView.setVerticalScrollBarEnabled(false);
        }
        if (itemsAdapter.getCount() != i2) {
            fillUi.mListView.requestLayout();
        }
    }
}
