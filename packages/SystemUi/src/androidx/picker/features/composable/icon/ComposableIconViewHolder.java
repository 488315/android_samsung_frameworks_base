package androidx.picker.features.composable.icon;

import android.view.View;
import android.widget.ImageView;
import androidx.picker.features.composable.ComposableViewHolder;
import androidx.picker.helper.ImageViewHelperKt;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.CategoryViewData;
import androidx.picker.model.viewdata.ViewData;
import com.android.systemui.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComposableIconViewHolder extends ComposableViewHolder {
    private DisposableHandle disposableHandle;
    private final ImageView iconView;
    private final ShimmerFrameLayout shimmerLayout;
    private final ImageView subIconView;

    public ComposableIconViewHolder(View view) {
        super(view);
        this.shimmerLayout = view.findViewById(R.id.shimmerFrameLayout);
        View findViewById = view.findViewById(R.id.icon);
        Intrinsics.checkNotNull(findViewById);
        this.iconView = (ImageView) findViewById;
        View findViewById2 = view.findViewById(R.id.sub_icon);
        Intrinsics.checkNotNull(findViewById2);
        this.subIconView = (ImageView) findViewById2;
    }

    @Override // androidx.picker.features.composable.ComposableViewHolder
    public void bindData(ViewData viewData) {
        if (!(viewData instanceof AppInfoViewData)) {
            if (viewData instanceof CategoryViewData) {
                this.iconView.setImageDrawable(((CategoryViewData) viewData).appData.icon);
                return;
            }
            return;
        }
        AppInfoViewData appInfoViewData = (AppInfoViewData) viewData;
        if (appInfoViewData.getIcon() != null) {
            this.iconView.setImageDrawable(appInfoViewData.getIcon());
        } else {
            this.disposableHandle = ImageViewHelperKt.loadIcon(this.iconView, Dispatchers.Default, appInfoViewData.iconFlow, this.shimmerLayout);
        }
        this.subIconView.setImageDrawable(appInfoViewData.getSubIcon());
    }

    @Override // androidx.picker.features.composable.ComposableViewHolder
    public void onViewRecycled(View view) {
        super.onViewRecycled(view);
        this.iconView.setImageDrawable(null);
        this.subIconView.setImageDrawable(null);
        DisposableHandle disposableHandle = this.disposableHandle;
        if (disposableHandle != null) {
            disposableHandle.dispose();
        }
    }
}
