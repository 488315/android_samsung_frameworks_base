package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Icon;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.management.ControlsModel;
import com.android.systemui.controls.p005ui.CanUseIconPredicate;
import com.android.systemui.controls.p005ui.RenderInfo;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlHolder extends Holder {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ControlHolderAccessibilityDelegate accessibilityDelegate;
    public final CanUseIconPredicate canUseIconPredicate;
    public final CheckBox favorite;
    public final Function2 favoriteCallback;
    public final String favoriteStateDescription;
    public final ImageView icon;
    public final ControlsModel.MoveHelper moveHelper;
    public final String notFavoriteStateDescription;
    public final TextView removed;
    public final TextView subtitle;
    public final TextView title;

    public ControlHolder(View view, int i, ControlsModel.MoveHelper moveHelper, Function2 function2) {
        super(view, null);
        this.moveHelper = moveHelper;
        this.favoriteCallback = function2;
        this.favoriteStateDescription = this.itemView.getContext().getString(R.string.accessibility_control_favorite);
        this.notFavoriteStateDescription = this.itemView.getContext().getString(R.string.accessibility_control_not_favorite);
        this.icon = (ImageView) this.itemView.requireViewById(R.id.icon);
        this.title = (TextView) this.itemView.requireViewById(R.id.title);
        this.subtitle = (TextView) this.itemView.requireViewById(R.id.subtitle);
        this.removed = (TextView) this.itemView.requireViewById(R.id.status);
        View requireViewById = this.itemView.requireViewById(R.id.favorite);
        ((CheckBox) requireViewById).setVisibility(0);
        this.favorite = (CheckBox) requireViewById;
        this.canUseIconPredicate = new CanUseIconPredicate(i);
        ControlHolderAccessibilityDelegate controlHolderAccessibilityDelegate = new ControlHolderAccessibilityDelegate(new ControlHolder$accessibilityDelegate$1(this), new ControlHolder$accessibilityDelegate$2(this), moveHelper);
        this.accessibilityDelegate = controlHolderAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate(this.itemView, controlHolderAccessibilityDelegate);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.controls.management.Holder
    public final void bindData(final ElementWrapper elementWrapper) {
        ControlInterface controlInterface = (ControlInterface) elementWrapper;
        ComponentName component = controlInterface.getComponent();
        int deviceType = controlInterface.getDeviceType();
        RenderInfo.Companion companion = RenderInfo.Companion;
        View view = this.itemView;
        Context context = view.getContext();
        companion.getClass();
        RenderInfo lookup = RenderInfo.Companion.lookup(context, component, deviceType, 0);
        this.title.setText(controlInterface.getTitle());
        this.subtitle.setText(controlInterface.getSubtitle());
        updateFavorite(controlInterface.getFavorite());
        this.removed.setText(controlInterface.getRemoved() ? view.getContext().getText(R.string.controls_removed) : "");
        view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.ControlHolder$bindData$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ControlHolder.this.updateFavorite(!r2.favorite.isChecked());
                ControlHolder.this.favoriteCallback.invoke(((ControlInterface) elementWrapper).getControlId(), Boolean.valueOf(ControlHolder.this.favorite.isChecked()));
            }
        });
        Context context2 = view.getContext();
        ColorStateList colorStateList = context2.getResources().getColorStateList(lookup.foreground, context2.getTheme());
        ImageView imageView = this.icon;
        Unit unit = null;
        imageView.setImageTintList(null);
        Icon customIcon = controlInterface.getCustomIcon();
        if (customIcon != null) {
            if (!((Boolean) this.canUseIconPredicate.invoke(customIcon)).booleanValue()) {
                customIcon = null;
            }
            if (customIcon != null) {
                imageView.setImageIcon(customIcon);
                unit = Unit.INSTANCE;
            }
        }
        if (unit == null) {
            imageView.setImageDrawable(lookup.icon);
            if (controlInterface.getDeviceType() != 52) {
                imageView.setImageTintList(colorStateList);
            }
        }
    }

    public final CharSequence stateDescription(boolean z) {
        if (!z) {
            return this.notFavoriteStateDescription;
        }
        if (this.moveHelper == null) {
            return this.favoriteStateDescription;
        }
        return this.itemView.getContext().getString(R.string.accessibility_control_favorite_position, Integer.valueOf(getLayoutPosition() + 1));
    }

    @Override // com.android.systemui.controls.management.Holder
    public final void updateFavorite(boolean z) {
        this.favorite.setChecked(z);
        this.accessibilityDelegate.isFavorite = z;
        this.itemView.setStateDescription(stateDescription(z));
    }
}
