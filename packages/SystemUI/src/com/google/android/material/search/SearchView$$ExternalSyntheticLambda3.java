package com.google.android.material.search;

import android.view.View;
import com.google.android.material.search.SearchView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class SearchView$$ExternalSyntheticLambda3 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SearchView f$0;

    public /* synthetic */ SearchView$$ExternalSyntheticLambda3(SearchView searchView, int i) {
        this.$r8$classId = i;
        this.f$0 = searchView;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        SearchView searchView = this.f$0;
        switch (i) {
            case 0:
                int i2 = SearchView.$r8$clinit;
                searchView.show();
                break;
            case 1:
                int i3 = SearchView.$r8$clinit;
                if (!searchView.currentTransitionState.equals(SearchView.TransitionState.HIDDEN) && !searchView.currentTransitionState.equals(SearchView.TransitionState.HIDING)) {
                    searchView.searchViewAnimationHelper.hide();
                    break;
                }
                break;
            default:
                searchView.editText.setText("");
                searchView.requestFocusAndShowKeyboardIfNeeded();
                break;
        }
    }
}
