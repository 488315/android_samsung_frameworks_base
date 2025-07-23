package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import androidx.leanback.widget.SearchBar;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SearchEditText extends StreamingTextView {
    public SearchBar.AnonymousClass4 mKeyboardDismissListener;

    public SearchEditText(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && this.mKeyboardDismissListener != null) {
            post(new Runnable() { // from class: androidx.leanback.widget.SearchEditText.1
                @Override // java.lang.Runnable
                public final void run() {
                    SearchBar.AnonymousClass4 anonymousClass4 = SearchEditText.this.mKeyboardDismissListener;
                    if (anonymousClass4 != null) {
                        SearchBar.this.getClass();
                    }
                }
            });
        }
        return super.onKeyPreIme(i, keyEvent);
    }

    public SearchEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.style.TextAppearance_Leanback_SearchTextEdit);
    }

    public SearchEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
