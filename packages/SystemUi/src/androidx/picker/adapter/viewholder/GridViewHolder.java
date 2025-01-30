package androidx.picker.adapter.viewholder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.picker.helper.ContextHelperKt;
import androidx.picker.helper.ImageViewHelperKt;
import androidx.picker.helper.TextViewHelperKt;
import androidx.picker.model.AppInfo;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.ViewData;
import com.android.systemui.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Dispatchers;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class GridViewHolder extends PickerViewHolder {
    public final TextView appName;
    public GridViewHolder$$ExternalSyntheticLambda0 disposableHandle;
    public final Lazy highlightColor$delegate;
    public final ImageView icon;
    public final ShimmerFrameLayout shimmerLayout;
    public final ImageView subIcon;

    public GridViewHolder(final View view) {
        super(view);
        this.shimmerLayout = view.findViewById(R.id.shimmerFrameLayout);
        View findViewById = view.findViewById(R.id.icon);
        Intrinsics.checkNotNull(findViewById);
        this.icon = (ImageView) findViewById;
        View findViewById2 = view.findViewById(R.id.sub_icon);
        Intrinsics.checkNotNull(findViewById2);
        this.subIcon = (ImageView) findViewById2;
        View findViewById3 = view.findViewById(R.id.title);
        Intrinsics.checkNotNull(findViewById3);
        TextView textView = (TextView) findViewById3;
        TextViewHelperKt.limitFontLarge(textView);
        this.appName = textView;
        this.highlightColor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.picker.adapter.viewholder.GridViewHolder$highlightColor$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Integer.valueOf(ContextHelperKt.getPrimaryColor(view.getContext()));
            }
        });
    }

    @Override // androidx.picker.adapter.viewholder.PickerViewHolder
    public void bindData(ViewData viewData) {
        ArrayList arrayList = new ArrayList();
        boolean z = viewData instanceof AppInfoViewData;
        TextView textView = this.appName;
        if (z) {
            AppInfoViewData appInfoViewData = (AppInfoViewData) viewData;
            AppInfo appInfo = appInfoViewData.getAppInfo();
            ImageView imageView = this.icon;
            imageView.setTag(appInfo);
            Drawable icon = appInfoViewData.getIcon();
            if (icon != null) {
                imageView.setImageDrawable(icon);
            } else {
                arrayList.add(ImageViewHelperKt.loadIcon(imageView, Dispatchers.Default, appInfoViewData.iconFlow, this.shimmerLayout));
            }
            this.subIcon.setImageDrawable(appInfoViewData.getSubIcon());
            textView.setText(appInfoViewData.getLabel());
        }
        Object systemService = this.itemView.getContext().getSystemService("accessibility");
        AccessibilityManager accessibilityManager = systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null;
        if (accessibilityManager != null && accessibilityManager.isEnabled()) {
            this.item.setContentDescription(textView.getText());
        }
        if (z) {
            arrayList.add(((AppInfoViewData) viewData).highlightText.bind(new Function1() { // from class: androidx.picker.adapter.viewholder.GridViewHolder$bindData$3
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    GridViewHolder gridViewHolder = GridViewHolder.this;
                    TextViewHelperKt.setHighLightText(gridViewHolder.appName, (String) obj, ((Number) gridViewHolder.highlightColor$delegate.getValue()).intValue());
                    return Unit.INSTANCE;
                }
            }));
        }
        this.disposableHandle = new GridViewHolder$$ExternalSyntheticLambda0(arrayList);
        super.bindData(viewData);
    }

    @Override // androidx.picker.adapter.viewholder.PickerViewHolder
    public void onViewRecycled() {
        super.onViewRecycled();
        GridViewHolder$$ExternalSyntheticLambda0 gridViewHolder$$ExternalSyntheticLambda0 = this.disposableHandle;
        if (gridViewHolder$$ExternalSyntheticLambda0 != null) {
            gridViewHolder$$ExternalSyntheticLambda0.dispose();
        }
        this.icon.setImageDrawable(null);
        this.subIcon.setImageDrawable(null);
    }
}
