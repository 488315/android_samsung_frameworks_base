package android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

/* loaded from: classes5.dex */
public class ImageSwitcher extends ViewSwitcher {
    public ImageSwitcher(Context context) {
        super(context);
    }

    public ImageSwitcher(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setImageResource(int i) {
        ((ImageView) getNextView()).setImageResource(i);
        showNext();
    }

    public void setImageURI(Uri uri) {
        ((ImageView) getNextView()).setImageURI(uri);
        showNext();
    }

    public void setImageDrawable(Drawable drawable) {
        ((ImageView) getNextView()).lambda$setImageURIAsync$2(drawable);
        showNext();
    }

    @Override // android.widget.ViewSwitcher, android.widget.ViewAnimator, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return ImageSwitcher.class.getName();
    }
}
