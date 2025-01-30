package androidx.apppickerview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Deprecated
/* loaded from: classes.dex */
public class AppPickerView extends RecyclerView implements RecyclerView.RecyclerListener {
    public final AppPickerIconLoader mAppPickerIconLoader;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageButton mActionButton;
        public final ImageView mAppIcon;
        public final ViewGroup mAppIconContainer;
        public final CheckBox mCheckBox;
        public final View mDividerView;
        public final ViewGroup mLeftContainer;
        public final SwitchCompat mSwitch;
        public final ViewGroup mTitleContainer;
        public final ViewGroup mWidgetContainer;

        public ViewHolder(View view) {
            super(view);
            this.mAppIcon = (ImageView) view.findViewById(R.id.icon);
            this.mAppIconContainer = (ViewGroup) view.findViewById(R.id.icon_frame);
            this.mTitleContainer = (ViewGroup) view.findViewById(R.id.title_frame);
            this.mLeftContainer = (ViewGroup) view.findViewById(R.id.left_frame);
            this.mCheckBox = (CheckBox) view.findViewById(R.id.check_widget);
            this.mWidgetContainer = (ViewGroup) view.findViewById(R.id.widget_frame);
            this.mSwitch = (SwitchCompat) view.findViewById(R.id.switch_widget);
            this.mDividerView = view.findViewById(R.id.switch_divider_widget);
            this.mActionButton = (ImageButton) view.findViewById(R.id.image_button);
        }
    }

    public AppPickerView(Context context) {
        this(context, null);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAppPickerIconLoader.getClass();
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        this.mAppPickerIconLoader.getClass();
        super.onDetachedFromWindow();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.RecyclerListener
    public final void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        ImageButton imageButton = viewHolder2.mActionButton;
        if (imageButton != null && imageButton.hasOnClickListeners()) {
            imageButton.setOnClickListener(null);
        }
        ImageView imageView = viewHolder2.mAppIcon;
        if (imageView != null && imageView.hasOnClickListeners()) {
            imageView.setOnClickListener(null);
        }
        CheckBox checkBox = viewHolder2.mCheckBox;
        if (checkBox != null) {
            checkBox.setOnCheckedChangeListener(null);
        }
        View view = viewHolder2.itemView;
        if (view != null && view.hasOnClickListeners()) {
            view.setOnClickListener(null);
        }
        SwitchCompat switchCompat = viewHolder2.mSwitch;
        if (switchCompat != null) {
            switchCompat.setOnCheckedChangeListener(null);
        }
    }

    public AppPickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AppPickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRecyclerListener = this;
        this.mAppPickerIconLoader = new AppPickerIconLoader(context);
    }
}
