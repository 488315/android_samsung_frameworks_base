package com.google.android.material.search;

import android.view.View;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.internal.ViewUtils;

/* loaded from: classes4.dex */
public final /* synthetic */ class SearchView$$ExternalSyntheticLambda1 implements ViewUtils.OnApplyWindowInsetsListener, OnApplyWindowInsetsListener {
    public final /* synthetic */ SearchView f$0;

    @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
        SearchView searchView = this.f$0;
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(searchView.toolbar);
        searchView.toolbar.setPadding(windowInsetsCompat.getSystemWindowInsetLeft() + (zIsLayoutRtl ? relativePadding.end : relativePadding.start), relativePadding.top, windowInsetsCompat.getSystemWindowInsetRight() + (zIsLayoutRtl ? relativePadding.start : relativePadding.end), relativePadding.bottom);
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
