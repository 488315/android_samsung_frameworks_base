package android.preference;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import com.android.internal.R;

@Deprecated
/* loaded from: classes3.dex */
public class SeekBarDialogPreference extends DialogPreference {
    private final Drawable mMyIcon;

    public SeekBarDialogPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        createActionButtons();
        this.mMyIcon = getDialogIcon();
        setDialogIcon((Drawable) null);
    }

    public SeekBarDialogPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeekBarDialogPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.seekBarDialogPreferenceStyle);
    }

    public SeekBarDialogPreference(Context context) {
        this(context, null);
    }

    public void createActionButtons() {
        setPositiveButtonText(17039370);
        setNegativeButtonText(17039360);
    }

    @Override // android.preference.DialogPreference
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        ImageView imageView = (ImageView) view.findViewById(16908294);
        Drawable drawable = this.mMyIcon;
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        } else {
            imageView.setVisibility(8);
        }
    }

    protected static SeekBar getSeekBar(View view) {
        return (SeekBar) view.findViewById(R.id.seekbar);
    }
}
