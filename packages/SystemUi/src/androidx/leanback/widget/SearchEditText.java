package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import androidx.leanback.widget.SearchBar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SearchEditText extends StreamingTextView {
    public SearchBar.C02874 mKeyboardDismissListener;

    public SearchEditText(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && this.mKeyboardDismissListener != null) {
            post(new Runnable() { // from class: androidx.leanback.widget.SearchEditText.1
                @Override // java.lang.Runnable
                public final void run() {
                    SearchBar.C02874 c02874 = SearchEditText.this.mKeyboardDismissListener;
                    if (c02874 != null) {
                        SearchBar.this.getClass();
                    }
                }
            });
        }
        return super.onKeyPreIme(i, keyEvent);
    }

    public SearchEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 2132018194);
    }

    public SearchEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
