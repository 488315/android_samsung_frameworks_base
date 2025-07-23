package android.inputmethodservice;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.R;

/* loaded from: classes2.dex */
public class CompactExtractEditLayout extends LinearLayout {
    private View mInputExtractAccessories;
    private View mInputExtractAction;
    private View mInputExtractEditText;
    private boolean mPerformLayoutChanges;

    public CompactExtractEditLayout(Context context) {
        super(context);
    }

    public CompactExtractEditLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CompactExtractEditLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mInputExtractEditText = findViewById(16908325);
        this.mInputExtractAccessories = findViewById(16908378);
        View findViewById = findViewById(16908377);
        this.mInputExtractAction = findViewById;
        if (this.mInputExtractEditText == null || this.mInputExtractAccessories == null || findViewById == null) {
            return;
        }
        this.mPerformLayoutChanges = true;
    }

    private int applyFractionInt(int i, int i2) {
        return Math.round(getResources().getFraction(i, i2, i2));
    }

    private static void setLayoutHeight(View view, int i) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = i;
        view.setLayoutParams(layoutParams);
    }

    private static void setLayoutMarginBottom(View view, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.bottomMargin = i;
        view.setLayoutParams(marginLayoutParams);
    }

    private void applyProportionalLayout(int i, int i2) {
        if (getResources().getConfiguration().isScreenRound()) {
            setGravity(80);
        }
        setLayoutHeight(this, applyFractionInt(R.fraction.input_extract_layout_height, i2));
        setPadding(applyFractionInt(R.fraction.input_extract_layout_padding_left, i), 0, applyFractionInt(R.fraction.input_extract_layout_padding_right, i), 0);
        setLayoutMarginBottom(this.mInputExtractEditText, applyFractionInt(R.fraction.input_extract_text_margin_bottom, i2));
        setLayoutMarginBottom(this.mInputExtractAccessories, applyFractionInt(R.fraction.input_extract_action_margin_bottom, i2));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mPerformLayoutChanges) {
            Resources resources = getResources();
            Configuration configuration = resources.getConfiguration();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            if (configuration.isScreenRound() && i2 < i) {
                i2 = i;
            }
            applyProportionalLayout(i, i2);
        }
    }
}
