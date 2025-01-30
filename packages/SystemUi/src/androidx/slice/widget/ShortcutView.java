package androidx.slice.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceActionImpl;
import com.android.systemui.R;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ShortcutView extends SliceChildView {
    public SliceItem mActionItem;
    public IconCompat mIcon;
    public final int mLargeIconSize;
    public ListContent mListContent;
    public Set mLoadingActions;
    public final int mSmallIconSize;

    public ShortcutView(Context context) {
        super(context);
        Resources resources = getResources();
        this.mSmallIconSize = resources.getDimensionPixelSize(R.dimen.abc_slice_icon_size);
        this.mLargeIconSize = resources.getDimensionPixelSize(R.dimen.abc_slice_shortcut_size);
    }

    @Override // androidx.slice.widget.SliceChildView
    public final Set getLoadingActions() {
        return this.mLoadingActions;
    }

    @Override // android.view.View
    public final boolean performClick() {
        if (this.mListContent == null) {
            return false;
        }
        if (!callOnClick()) {
            try {
                SliceItem sliceItem = this.mActionItem;
                if (sliceItem != null) {
                    sliceItem.fireActionInternal(null, null);
                    if (this.mObserver != null) {
                        EventInfo eventInfo = new EventInfo(3, 1, -1, 0);
                        if (this.mActionItem == null) {
                            SliceItem sliceItem2 = this.mListContent.mSliceItem;
                        }
                        this.mObserver.onSliceAction(eventInfo);
                    }
                }
            } catch (PendingIntent.CanceledException e) {
                Log.e("ShortcutView", "PendingIntent for slice cannot be sent", e);
            }
        }
        return true;
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void resetView() {
        this.mListContent = null;
        this.mActionItem = null;
        this.mIcon = null;
        setBackground(null);
        removeAllViews();
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setLoadingActions(Set set) {
        this.mLoadingActions = set;
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setSliceContent(ListContent listContent) {
        resetView();
        this.mListContent = listContent;
        if (listContent == null) {
            return;
        }
        SliceActionImpl sliceActionImpl = (SliceActionImpl) listContent.getShortcut(getContext());
        this.mActionItem = sliceActionImpl.mActionItem;
        this.mIcon = sliceActionImpl.mIcon;
        boolean z = sliceActionImpl.mImageMode == 0;
        SliceItem sliceItem = this.mListContent.mColorItem;
        int i = sliceItem != null ? sliceItem.getInt() : -1;
        if (i == -1) {
            i = SliceViewUtil.getColorAttr(android.R.attr.colorAccent, getContext());
        }
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.setTint(i);
        ImageView imageView = new ImageView(getContext());
        if (this.mIcon != null && z) {
            imageView.setBackground(shapeDrawable);
        }
        addView(imageView);
        if (this.mIcon != null) {
            int i2 = z ? this.mSmallIconSize : this.mLargeIconSize;
            Context context = getContext();
            IconCompat iconCompat = this.mIcon;
            boolean z2 = !z;
            ImageView imageView2 = new ImageView(context);
            imageView2.setImageDrawable(iconCompat.loadDrawable(context));
            imageView2.setScaleType(z2 ? ImageView.ScaleType.CENTER_CROP : ImageView.ScaleType.CENTER_INSIDE);
            addView(imageView2);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView2.getLayoutParams();
            if (z2) {
                Bitmap createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                imageView2.layout(0, 0, i2, i2);
                imageView2.draw(canvas);
                imageView2.setImageBitmap(SliceViewUtil.getCircularBitmap(createBitmap));
            } else {
                imageView2.setColorFilter(-1);
            }
            layoutParams.width = i2;
            layoutParams.height = i2;
            layoutParams.gravity = 17;
            setClickable(true);
        } else {
            setClickable(false);
        }
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams2.gravity = 17;
        setLayoutParams(layoutParams2);
    }
}
