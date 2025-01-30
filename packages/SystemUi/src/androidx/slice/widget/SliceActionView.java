package androidx.slice.widget;

import android.R;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import androidx.core.content.ContextCompat;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceActionImpl;
import com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda4;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SliceActionView extends FrameLayout implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    public View mActionView;
    public EventInfo mEventInfo;
    public final int mIconSize;
    public final int mImageSize;
    public VolumePanelDialog$$ExternalSyntheticLambda4 mObserver;
    public ProgressBar mProgressView;
    public SliceActionImpl mSliceAction;
    public final int mTextActionPadding;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ImageToggle extends ImageView implements Checkable, View.OnClickListener {
        public boolean mIsChecked;
        public View.OnClickListener mListener;

        public ImageToggle(Context context) {
            super(context);
            super.setOnClickListener(this);
        }

        @Override // android.widget.Checkable
        public final boolean isChecked() {
            return this.mIsChecked;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            toggle();
        }

        @Override // android.widget.ImageView, android.view.View
        public final int[] onCreateDrawableState(int i) {
            int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
            if (this.mIsChecked) {
                ImageView.mergeDrawableStates(onCreateDrawableState, SliceActionView.CHECKED_STATE_SET);
            }
            return onCreateDrawableState;
        }

        @Override // android.widget.Checkable
        public final void setChecked(boolean z) {
            if (this.mIsChecked != z) {
                this.mIsChecked = z;
                refreshDrawableState();
                View.OnClickListener onClickListener = this.mListener;
                if (onClickListener != null) {
                    onClickListener.onClick(this);
                }
            }
        }

        @Override // android.view.View
        public final void setOnClickListener(View.OnClickListener onClickListener) {
            this.mListener = onClickListener;
        }

        @Override // android.widget.Checkable
        public final void toggle() {
            setChecked(!this.mIsChecked);
        }
    }

    public SliceActionView(Context context, SliceStyle sliceStyle, RowStyle rowStyle) {
        super(context);
        Resources resources = getContext().getResources();
        this.mIconSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.abc_slice_icon_size);
        this.mImageSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.abc_slice_small_image_size);
        this.mTextActionPadding = 0;
        if (rowStyle != null) {
            this.mIconSize = rowStyle.mIconSize;
            this.mImageSize = rowStyle.mImageSize;
            this.mTextActionPadding = rowStyle.mTextActionPadding;
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this.mSliceAction == null || this.mActionView == null) {
            return;
        }
        sendActionInternal();
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (this.mSliceAction == null || this.mActionView == null) {
            return;
        }
        sendActionInternal();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void sendActionInternal() {
        Intent intent;
        EventInfo eventInfo;
        SliceActionImpl sliceActionImpl = this.mSliceAction;
        if (sliceActionImpl == null || sliceActionImpl.mActionItem == null) {
            return;
        }
        try {
            if (sliceActionImpl.isToggle()) {
                boolean isChecked = ((Checkable) this.mActionView).isChecked();
                intent = new Intent().addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE).putExtra("android.app.slice.extra.TOGGLE_STATE", isChecked);
                EventInfo eventInfo2 = this.mEventInfo;
                if (eventInfo2 != null) {
                    eventInfo2.state = isChecked ? 1 : 0;
                }
            } else {
                intent = null;
            }
            this.mSliceAction.mActionItem.fireActionInternal(getContext(), intent);
            VolumePanelDialog$$ExternalSyntheticLambda4 volumePanelDialog$$ExternalSyntheticLambda4 = this.mObserver;
            if (volumePanelDialog$$ExternalSyntheticLambda4 == null || (eventInfo = this.mEventInfo) == null) {
                return;
            }
            SliceItem sliceItem = this.mSliceAction.mSliceItem;
            volumePanelDialog$$ExternalSyntheticLambda4.onSliceAction(eventInfo);
        } catch (PendingIntent.CanceledException e) {
            View view = this.mActionView;
            if (view instanceof Checkable) {
                view.setSelected(true ^ ((Checkable) view).isChecked());
            }
            Log.e("SliceActionView", "PendingIntent for slice cannot be sent", e);
        }
    }

    public final void setAction(SliceActionImpl sliceActionImpl, EventInfo eventInfo, VolumePanelDialog$$ExternalSyntheticLambda4 volumePanelDialog$$ExternalSyntheticLambda4, int i, SliceAdapter sliceAdapter) {
        View view = this.mActionView;
        if (view != null) {
            removeView(view);
            this.mActionView = null;
        }
        View view2 = this.mProgressView;
        if (view2 != null) {
            removeView(view2);
            this.mProgressView = null;
        }
        this.mSliceAction = sliceActionImpl;
        this.mEventInfo = eventInfo;
        this.mObserver = volumePanelDialog$$ExternalSyntheticLambda4;
        this.mActionView = null;
        boolean isDefaultToggle = sliceActionImpl.isDefaultToggle();
        int i2 = 0;
        CharSequence charSequence = sliceActionImpl.mTitle;
        boolean z = sliceActionImpl.mIsChecked;
        if (isDefaultToggle) {
            Switch r6 = (Switch) LayoutInflater.from(getContext()).inflate(com.android.systemui.R.layout.abc_slice_switch, (ViewGroup) this, false);
            r6.setChecked(z);
            r6.setOnCheckedChangeListener(this);
            r6.setMinimumHeight(this.mImageSize);
            r6.setMinimumWidth(this.mImageSize);
            addView(r6);
            if (i != -1) {
                int colorAttr = SliceViewUtil.getColorAttr(R.attr.colorForeground, getContext());
                int[] iArr = CHECKED_STATE_SET;
                int[] iArr2 = FrameLayout.EMPTY_STATE_SET;
                ColorStateList colorStateList = new ColorStateList(new int[][]{iArr, iArr2}, new int[]{i, colorAttr});
                Drawable trackDrawable = r6.getTrackDrawable();
                trackDrawable.setTintList(colorStateList);
                r6.setTrackDrawable(trackDrawable);
                int colorAttr2 = SliceViewUtil.getColorAttr(com.android.systemui.R.attr.colorSwitchThumbNormal, getContext());
                if (colorAttr2 == 0) {
                    Context context = getContext();
                    Object obj = ContextCompat.sLock;
                    colorAttr2 = context.getColor(com.android.systemui.R.color.switch_thumb_normal_material_light);
                }
                ColorStateList colorStateList2 = new ColorStateList(new int[][]{iArr, iArr2}, new int[]{i, colorAttr2});
                Drawable thumbDrawable = r6.getThumbDrawable();
                thumbDrawable.setTintList(colorStateList2);
                r6.setThumbDrawable(thumbDrawable);
            }
            this.mActionView = r6;
        } else {
            int i3 = sliceActionImpl.mImageMode;
            if (i3 == 6) {
                Button button = new Button(getContext());
                this.mActionView = button;
                button.setText(charSequence);
                addView(this.mActionView);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mActionView.getLayoutParams();
                layoutParams.width = -2;
                layoutParams.height = -2;
                this.mActionView.setLayoutParams(layoutParams);
                int i4 = this.mTextActionPadding;
                this.mActionView.setPadding(i4, i4, i4, i4);
                this.mActionView.setOnClickListener(this);
            } else if (sliceActionImpl.mIcon != null) {
                if (sliceActionImpl.isToggle()) {
                    ImageToggle imageToggle = new ImageToggle(getContext());
                    imageToggle.setChecked(z);
                    this.mActionView = imageToggle;
                } else {
                    this.mActionView = new ImageView(getContext());
                }
                addView(this.mActionView);
                Drawable loadDrawable = this.mSliceAction.mIcon.loadDrawable(getContext());
                ((ImageView) this.mActionView).setImageDrawable(loadDrawable);
                if (i != -1 && this.mSliceAction.mImageMode == 0 && loadDrawable != null) {
                    loadDrawable.setTint(i);
                }
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mActionView.getLayoutParams();
                int i5 = this.mImageSize;
                layoutParams2.width = i5;
                layoutParams2.height = i5;
                this.mActionView.setLayoutParams(layoutParams2);
                if (i3 == 0) {
                    int i6 = this.mImageSize;
                    i2 = (i6 == -1 ? this.mIconSize : i6 - this.mIconSize) / 2;
                }
                this.mActionView.setPadding(i2, i2, i2, i2);
                this.mActionView.setBackground(SliceViewUtil.getDrawable(R.attr.selectableItemBackgroundBorderless, getContext()));
                this.mActionView.setOnClickListener(this);
            }
        }
        View view3 = this.mActionView;
        if (view3 != null) {
            CharSequence charSequence2 = sliceActionImpl.mContentDescription;
            if (charSequence2 != null) {
                charSequence = charSequence2;
            }
            view3.setContentDescription(charSequence);
        }
    }
}
