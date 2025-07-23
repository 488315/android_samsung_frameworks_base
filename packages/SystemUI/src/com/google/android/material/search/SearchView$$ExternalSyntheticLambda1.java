package com.google.android.material.search;

import android.view.View;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.internal.ViewUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class SearchView$$ExternalSyntheticLambda1 implements ViewUtils.OnApplyWindowInsetsListener, OnApplyWindowInsetsListener {
    public final /* synthetic */ SearchView f$0;

    @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
        SearchView searchView = this.f$0;
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(searchView.toolbar);
        searchView.toolbar.setPadding(windowInsetsCompat.getSystemWindowInsetLeft() + (isLayoutRtl ? relativePadding.end : relativePadding.start), relativePadding.top, windowInsetsCompat.getSystemWindowInsetRight() + (isLayoutRtl ? relativePadding.start : relativePadding.end), relativePadding.bottom);
        return windowInsetsCompat;
    }

    @Override // androidx.core.view.OnApplyWindowInsetsListener
    public WindowInsetsCompat onApplyWindowInsets(WindowInsetsCompat windowInsetsCompat, View view) {
        int i = SearchView.$r8$clinit;
        SearchView searchView = this.f$0;
        int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
        if (searchView.statusBarSpacer.getLayoutParams().height != systemWindowInsetTop) {
            searchView.statusBarSpacer.getLayoutParams().height = systemWindowInsetTop;
            searchView.statusBarSpacer.requestLayout();
        }
        searchView.statusBarSpacer.setVisibility(systemWindowInsetTop > 0 ? 0 : 8);
        return windowInsetsCompat;
    }
}
