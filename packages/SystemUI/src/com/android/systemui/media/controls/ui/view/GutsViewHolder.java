package com.android.systemui.media.controls.ui.view;

import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.monet.ColorScheme;
import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GutsViewHolder {
    public static final Companion Companion = new Companion(null);
    public static final Set ids = SetsKt__SetsKt.setOf(Integer.valueOf(R.id.remove_text), Integer.valueOf(R.id.cancel), Integer.valueOf(R.id.dismiss), Integer.valueOf(R.id.settings));
    public final View cancel;
    public final TextView cancelText;
    public ColorScheme colorScheme;
    public final ViewGroup dismiss;
    public final TextView dismissText;
    public final TextView gutsText;
    public boolean isDismissible = true;
    public final ImageButton settings;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public GutsViewHolder(View view) {
        this.gutsText = (TextView) view.requireViewById(R.id.remove_text);
        this.cancel = view.requireViewById(R.id.cancel);
        this.cancelText = (TextView) view.requireViewById(R.id.cancel_text);
        this.dismiss = (ViewGroup) view.requireViewById(R.id.dismiss);
        this.dismissText = (TextView) view.requireViewById(R.id.dismiss_text);
        this.settings = (ImageButton) view.requireViewById(R.id.settings);
    }

    public final void setAccentPrimaryColor(int i) {
        ColorStateList valueOf = ColorStateList.valueOf(i);
        this.settings.setImageTintList(valueOf);
        this.cancelText.setBackgroundTintList(valueOf);
        this.dismissText.setBackgroundTintList(valueOf);
    }

    public final void setSurfaceColor(int i) {
        this.dismissText.setTextColor(i);
        if (this.isDismissible) {
            return;
        }
        this.cancelText.setTextColor(i);
    }

    public final void setTextPrimaryColor(int i) {
        ColorStateList valueOf = ColorStateList.valueOf(i);
        this.gutsText.setTextColor(valueOf);
        if (this.isDismissible) {
            this.cancelText.setTextColor(valueOf);
        }
    }
}
