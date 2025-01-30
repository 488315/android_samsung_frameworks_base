package androidx.picker.features.composable.widget;

import android.view.View;
import android.widget.ImageButton;
import androidx.core.util.Supplier;
import androidx.picker.features.composable.ActionableComposableViewHolder;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.ViewData;
import com.android.systemui.R;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComposableActionViewHolder extends ActionableComposableViewHolder {
    private final View divider;
    private Boolean hasCustomClickListener;
    private final ImageButton imageButton;

    public ComposableActionViewHolder(View view) {
        super(view);
        View findViewById = view.findViewById(R.id.image_button);
        Intrinsics.checkNotNull(findViewById);
        this.imageButton = (ImageButton) findViewById;
        View findViewById2 = view.findViewById(R.id.switch_divider_widget);
        Intrinsics.checkNotNull(findViewById2);
        this.divider = findViewById2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: bindData$lambda-2, reason: not valid java name */
    public static final Boolean m321bindData$lambda2(AppInfoViewData appInfoViewData) {
        return Boolean.TRUE;
    }

    private final void setHasCustomClickListener(Boolean bool) {
        this.hasCustomClickListener = bool;
        this.divider.setVisibility(Intrinsics.areEqual(bool, Boolean.TRUE) ? 0 : 8);
    }

    @Override // androidx.picker.features.composable.ActionableComposableViewHolder, androidx.picker.features.composable.ComposableViewHolder
    public void bindData(final ViewData viewData) {
        final AppInfoViewData appInfoViewData = (AppInfoViewData) viewData;
        this.imageButton.setImageDrawable(appInfoViewData.getActionIcon());
        final Function1 function1 = appInfoViewData.onActionClick;
        if (function1 != null) {
            this.imageButton.setOnClickListener(new View.OnClickListener() { // from class: androidx.picker.features.composable.widget.ComposableActionViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Function1.this.invoke(viewData);
                }
            });
        }
        setDoAction(new Supplier() { // from class: androidx.picker.features.composable.widget.ComposableActionViewHolder$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Supplier
            public final Object get() {
                Boolean m321bindData$lambda2;
                m321bindData$lambda2 = ComposableActionViewHolder.m321bindData$lambda2(AppInfoViewData.this);
                return m321bindData$lambda2;
            }
        });
    }

    @Override // androidx.picker.features.composable.ActionableComposableViewHolder, androidx.picker.features.composable.ComposableViewHolder
    public void onBind(View view) {
        if (this.hasCustomClickListener == null) {
            setHasCustomClickListener(Boolean.valueOf(view.hasOnClickListeners()));
        }
        super.onBind(view);
    }

    @Override // androidx.picker.features.composable.ActionableComposableViewHolder, androidx.picker.features.composable.ComposableViewHolder
    public void onViewRecycled(View view) {
        super.onViewRecycled(view);
        this.imageButton.setOnClickListener(null);
    }
}
