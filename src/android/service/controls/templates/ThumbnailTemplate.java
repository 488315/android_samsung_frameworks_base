package android.service.controls.templates;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import com.android.internal.R;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public final class ThumbnailTemplate extends ControlTemplate {
    private static final String KEY_ACTIVE = "key_active";
    private static final String KEY_CONTENT_DESCRIPTION = "key_content_description";
    private static final String KEY_ICON = "key_icon";
    private static final int TYPE = 3;
    private final boolean mActive;
    private final CharSequence mContentDescription;
    private final Icon mThumbnail;

    @Override // android.service.controls.templates.ControlTemplate
    public int getTemplateType() {
        return 3;
    }

    public ThumbnailTemplate(String str, boolean z, Icon icon, CharSequence charSequence) {
        super(str);
        Preconditions.checkNotNull(icon);
        Preconditions.checkNotNull(charSequence);
        this.mActive = z;
        this.mThumbnail = icon;
        this.mContentDescription = charSequence;
    }

    ThumbnailTemplate(Bundle bundle) {
        super(bundle);
        this.mActive = bundle.getBoolean(KEY_ACTIVE);
        this.mThumbnail = (Icon) bundle.getParcelable(KEY_ICON, Icon.class);
        this.mContentDescription = bundle.getCharSequence(KEY_CONTENT_DESCRIPTION, "");
    }

    public boolean isActive() {
        return this.mActive;
    }

    public Icon getThumbnail() {
        return this.mThumbnail;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    @Override // android.service.controls.templates.ControlTemplate
    public void prepareTemplateForBinder(Context context) {
        rescaleThumbnail(context.getResources().getDimensionPixelSize(R.dimen.controls_thumbnail_image_max_width), context.getResources().getDimensionPixelSize(R.dimen.controls_thumbnail_image_max_height));
    }

    private void rescaleThumbnail(int i, int i2) {
        this.mThumbnail.scaleDownIfNecessary(i, i2);
    }

    @Override // android.service.controls.templates.ControlTemplate
    Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putBoolean(KEY_ACTIVE, this.mActive);
        dataBundle.putObject(KEY_ICON, this.mThumbnail);
        dataBundle.putObject(KEY_CONTENT_DESCRIPTION, this.mContentDescription);
        return dataBundle;
    }
}
