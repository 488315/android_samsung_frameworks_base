package androidx.picker.features.composable.left;

import android.view.View;
import android.widget.RadioButton;
import androidx.core.util.Supplier;
import androidx.picker.features.composable.ActionableComposableViewHolder;
import androidx.picker.helper.CompountButtonHelperKt;
import androidx.picker.loader.select.SelectableItem;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.ViewData;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComposableRadioButtonViewHolder extends ActionableComposableViewHolder {
    private DisposableHandle disposableHandle;
    private final RadioButton radioButton;

    public ComposableRadioButtonViewHolder(View view) {
        super(view);
        this.radioButton = (RadioButton) view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: bindData$lambda-0, reason: not valid java name */
    public static final void m316bindData$lambda0(SelectableItem selectableItem, ComposableRadioButtonViewHolder composableRadioButtonViewHolder, View view) {
        selectableItem.setValue(Boolean.valueOf(composableRadioButtonViewHolder.radioButton.isChecked()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: bindData$lambda-1, reason: not valid java name */
    public static final Boolean m317bindData$lambda1(SelectableItem selectableItem, ComposableRadioButtonViewHolder composableRadioButtonViewHolder) {
        selectableItem.setValue(Boolean.valueOf(!composableRadioButtonViewHolder.radioButton.isChecked()));
        return Boolean.TRUE;
    }

    @Override // androidx.picker.features.composable.ActionableComposableViewHolder, androidx.picker.features.composable.ComposableViewHolder
    public void bindData(ViewData viewData) {
        final SelectableItem selectableItem;
        AppInfoViewData appInfoViewData = viewData instanceof AppInfoViewData ? (AppInfoViewData) viewData : null;
        if (appInfoViewData == null || (selectableItem = appInfoViewData.selectableItem) == null) {
            return;
        }
        DisposableHandle disposableHandle = this.disposableHandle;
        if (disposableHandle != null) {
            disposableHandle.dispose();
        }
        this.disposableHandle = selectableItem.bind(new Function1() { // from class: androidx.picker.features.composable.left.ComposableRadioButtonViewHolder$bindData$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                RadioButton radioButton;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                radioButton = ComposableRadioButtonViewHolder.this.radioButton;
                radioButton.setChecked(booleanValue);
                return Unit.INSTANCE;
            }
        });
        this.radioButton.setOnClickListener(new View.OnClickListener() { // from class: androidx.picker.features.composable.left.ComposableRadioButtonViewHolder$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ComposableRadioButtonViewHolder.m316bindData$lambda0(SelectableItem.this, this, view);
            }
        });
        setDoAction(new Supplier() { // from class: androidx.picker.features.composable.left.ComposableRadioButtonViewHolder$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Supplier
            public final Object get() {
                Boolean m317bindData$lambda1;
                m317bindData$lambda1 = ComposableRadioButtonViewHolder.m317bindData$lambda1(SelectableItem.this, this);
                return m317bindData$lambda1;
            }
        });
    }

    @Override // androidx.picker.features.composable.ActionableComposableViewHolder, androidx.picker.features.composable.ComposableViewHolder
    public void onBind(View view) {
        CompountButtonHelperKt.setAccessibilityFocusable(this.radioButton, view.hasOnClickListeners());
        super.onBind(view);
    }

    @Override // androidx.picker.features.composable.ActionableComposableViewHolder, androidx.picker.features.composable.ComposableViewHolder
    public void onViewRecycled(View view) {
        super.onViewRecycled(view);
        this.radioButton.setOnClickListener(null);
        DisposableHandle disposableHandle = this.disposableHandle;
        if (disposableHandle != null) {
            disposableHandle.dispose();
        }
    }
}
