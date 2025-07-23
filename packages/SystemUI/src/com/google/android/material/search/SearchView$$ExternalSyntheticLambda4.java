package com.google.android.material.search;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.google.android.material.internal.ViewUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class SearchView$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SearchView f$0;

    public /* synthetic */ SearchView$$ExternalSyntheticLambda4(SearchView searchView, int i) {
        this.$r8$classId = i;
        this.f$0 = searchView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SearchView searchView = this.f$0;
        switch (i) {
            case 0:
                searchView.show();
                break;
            case 1:
                if (searchView.editText.requestFocus()) {
                    searchView.editText.sendAccessibilityEvent(8);
                }
                ViewUtils.showKeyboard(searchView.editText);
                break;
            default:
                searchView.editText.clearFocus();
                SearchBar searchBar = searchView.searchBar;
                if (searchBar != null) {
                    searchBar.requestFocus();
                }
                EditText editText = searchView.editText;
                InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext().getSystemService(InputMethodManager.class);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    break;
                }
                break;
        }
    }
}
