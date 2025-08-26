package com.android.internal.globalactions;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;

/* loaded from: classes5.dex */
public abstract class SinglePressAction implements Action {
    private final Drawable mIcon;
    private final int mIconResId;
    private final CharSequence mMessage;
    private final int mMessageResId;

    public String getStatus() {
        return null;
    }

    @Override // com.android.internal.globalactions.Action
    public boolean isEnabled() {
        return true;
    }

    @Override // com.android.internal.globalactions.Action
    public abstract void onPress();

    protected SinglePressAction(int i, int i2) {
        this.mIconResId = i;
        this.mMessageResId = i2;
        this.mMessage = null;
        this.mIcon = null;
    }

    protected SinglePressAction(int i, Drawable drawable, CharSequence charSequence) {
        this.mIconResId = i;
        this.mMessageResId = 0;
        this.mMessage = charSequence;
        this.mIcon = drawable;
    }

    @Override // com.android.internal.globalactions.Action
    public CharSequence getLabelForAccessibility(Context context) {
        CharSequence charSequence = this.mMessage;
        return charSequence != null ? charSequence : context.getString(this.mMessageResId);
    }

    @Override // com.android.internal.globalactions.Action
    public View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) throws Resources.NotFoundException {
        View viewInflate = layoutInflater.inflate(R.layout.global_actions_item, viewGroup, false);
        ImageView imageView = (ImageView) viewInflate.findViewById(16908294);
        TextView textView = (TextView) viewInflate.findViewById(16908299);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.status);
        String status = getStatus();
        if (textView2 != null) {
            if (!TextUtils.isEmpty(status)) {
                textView2.lambda$setTextAsync$0(status);
            } else {
                textView2.setVisibility(8);
            }
        }
        if (imageView != null) {
            Drawable drawable = this.mIcon;
            if (drawable != null) {
                imageView.lambda$setImageURIAsync$2(drawable);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                int i = this.mIconResId;
                if (i != 0) {
                    imageView.lambda$setImageURIAsync$2(context.getDrawable(i));
                }
            }
        }
        if (textView != null) {
            CharSequence charSequence = this.mMessage;
            if (charSequence != null) {
                textView.lambda$setTextAsync$0(charSequence);
                return viewInflate;
            }
            textView.setText(this.mMessageResId);
        }
        return viewInflate;
    }
}
