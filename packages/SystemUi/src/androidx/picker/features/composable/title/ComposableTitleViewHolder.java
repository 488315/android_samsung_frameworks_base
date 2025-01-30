package androidx.picker.features.composable.title;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.picker.features.composable.ComposableViewHolder;
import androidx.picker.features.observable.ObservableProperty;
import androidx.picker.helper.ContextHelperKt;
import androidx.picker.helper.TextViewHelperKt;
import androidx.picker.loader.select.SelectableItem;
import androidx.picker.model.viewdata.AllAppsViewData;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.CategoryViewData;
import androidx.picker.model.viewdata.ViewData;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComposableTitleViewHolder extends ComposableViewHolder {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private int currentLayoutId;
    private DisposableHandle disposableHandle;
    private final TextView extraTitleView;
    private final Lazy highlightColor$delegate;
    private final Lazy subLabelDescriptionColor$delegate;
    private final Lazy subLabelValueColor$delegate;
    private final TextView summaryView;
    private final TextView titleView;

    static {
        PropertyReference0Impl propertyReference0Impl = new PropertyReference0Impl(ComposableTitleViewHolder.class, "highlightText", "<v#0>", 0);
        Reflection.factory.getClass();
        $$delegatedProperties = new KProperty[]{propertyReference0Impl};
    }

    public ComposableTitleViewHolder(final View view) {
        super(view);
        View findViewById = view.findViewById(R.id.title);
        Intrinsics.checkNotNull(findViewById);
        TextView textView = (TextView) findViewById;
        TextViewHelperKt.limitFontLarge(textView);
        this.titleView = textView;
        View findViewById2 = view.findViewById(R.id.summary);
        Intrinsics.checkNotNull(findViewById2);
        TextView textView2 = (TextView) findViewById2;
        TextViewHelperKt.limitFontLarge(textView2);
        this.summaryView = textView2;
        View findViewById3 = view.findViewById(R.id.extra_label);
        Intrinsics.checkNotNull(findViewById3);
        TextView textView3 = (TextView) findViewById3;
        TextViewHelperKt.limitFontLarge(textView3);
        this.extraTitleView = textView3;
        this.highlightColor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.picker.features.composable.title.ComposableTitleViewHolder$highlightColor$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Integer.valueOf(ContextHelperKt.getPrimaryColor(view.getContext()));
            }
        });
        this.subLabelValueColor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.picker.features.composable.title.ComposableTitleViewHolder$subLabelValueColor$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i;
                Context context = view.getContext();
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
                int i2 = typedValue.resourceId;
                if (i2 != 0) {
                    Object obj = ContextCompat.sLock;
                    i = context.getColor(i2);
                } else {
                    i = typedValue.data;
                }
                return Integer.valueOf(i);
            }
        });
        this.subLabelDescriptionColor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.picker.features.composable.title.ComposableTitleViewHolder$subLabelDescriptionColor$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i;
                Context context = view.getContext();
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(android.R.attr.textColorSecondary, typedValue, true);
                int i2 = typedValue.resourceId;
                if (i2 != 0) {
                    Object obj = ContextCompat.sLock;
                    i = context.getColor(i2);
                } else {
                    i = typedValue.data;
                }
                return Integer.valueOf(i);
            }
        });
        this.currentLayoutId = R.layout.picker_app_composable_frame_title_single;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void adjustLayout(boolean z) {
        if (getFrameView() instanceof ConstraintLayout) {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this.currentLayoutId, ((ConstraintLayout) getFrameView()).getContext());
            constraintSet.applyTo((ConstraintLayout) getFrameView());
            getFrameView().getLayoutParams().height = getLayoutHeight(z);
        }
    }

    /* renamed from: bindData$lambda-4, reason: not valid java name */
    private static final String m318bindData$lambda4(ObservableProperty<String> observableProperty) {
        return observableProperty.getValue(null, $$delegatedProperties[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: bindData$lambda-6, reason: not valid java name */
    public static final void m319bindData$lambda6(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((DisposableHandle) it.next()).dispose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getHighlightColor() {
        return ((Number) this.highlightColor$delegate.getValue()).intValue();
    }

    private final int getLayoutHeight(boolean z) {
        return z ? getFrameView().getResources().getDimensionPixelOffset(R.dimen.picker_app_list_sub_label_height) : getFrameView().getResources().getDimensionPixelOffset(R.dimen.picker_app_list_single_line_height);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getLayoutId(boolean z) {
        return z ? R.layout.picker_app_composable_frame_title_sublabel : R.layout.picker_app_composable_frame_title_single;
    }

    private final int getSubLabelDescriptionColor() {
        return ((Number) this.subLabelDescriptionColor$delegate.getValue()).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getSubLabelShowState(ViewData viewData) {
        if (!(viewData instanceof AppInfoViewData)) {
            return false;
        }
        AppInfoViewData appInfoViewData = (AppInfoViewData) viewData;
        return (appInfoViewData.getItemType() == 5 && appInfoViewData.isValueInSubLabel() && !appInfoViewData.getSelected()) ? false : true;
    }

    private final int getSubLabelValueColor() {
        return ((Number) this.subLabelValueColor$delegate.getValue()).intValue();
    }

    @Override // androidx.picker.features.composable.ComposableViewHolder
    public void bindData(final ViewData viewData) {
        DisposableHandle registerAfterChangeUpdateListener;
        DisposableHandle disposableHandle = this.disposableHandle;
        if (disposableHandle != null) {
            disposableHandle.dispose();
        }
        final ArrayList arrayList = new ArrayList();
        boolean z = viewData instanceof AppInfoViewData;
        if (z) {
            AppInfoViewData appInfoViewData = (AppInfoViewData) viewData;
            boolean z2 = !TextUtils.isEmpty(appInfoViewData.getSubLabel()) && getSubLabelShowState(viewData);
            int layoutId = getLayoutId(z2);
            if (this.currentLayoutId != layoutId) {
                this.currentLayoutId = layoutId;
                adjustLayout(z2);
            }
            this.titleView.setText(appInfoViewData.getLabel());
            this.summaryView.setText(appInfoViewData.getSubLabel());
            this.extraTitleView.setText(appInfoViewData.getExtraLabel());
            this.summaryView.setTextColor(appInfoViewData.isValueInSubLabel() ? getSubLabelValueColor() : getSubLabelDescriptionColor());
            SelectableItem selectableItem = appInfoViewData.selectableItem;
            if (selectableItem != null && (registerAfterChangeUpdateListener = selectableItem.registerAfterChangeUpdateListener(new Function1() { // from class: androidx.picker.features.composable.title.ComposableTitleViewHolder$bindData$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Removed duplicated region for block: B:8:0x002e  */
                @Override // kotlin.jvm.functions.Function1
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj) {
                    boolean z3;
                    int layoutId2;
                    int i;
                    boolean subLabelShowState;
                    ((Boolean) obj).booleanValue();
                    if (!TextUtils.isEmpty(((AppInfoViewData) ViewData.this).getSubLabel())) {
                        subLabelShowState = this.getSubLabelShowState(ViewData.this);
                        if (subLabelShowState) {
                            z3 = true;
                            layoutId2 = this.getLayoutId(z3);
                            i = this.currentLayoutId;
                            if (i != layoutId2) {
                                this.currentLayoutId = layoutId2;
                                this.adjustLayout(z3);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    z3 = false;
                    layoutId2 = this.getLayoutId(z3);
                    i = this.currentLayoutId;
                    if (i != layoutId2) {
                    }
                    return Unit.INSTANCE;
                }
            })) != null) {
                arrayList.add(registerAfterChangeUpdateListener);
            }
        } else if (viewData instanceof CategoryViewData) {
            this.titleView.setText(((CategoryViewData) viewData).appData.label);
        } else if (viewData instanceof AllAppsViewData) {
            TextView textView = this.titleView;
            textView.setText(textView.getContext().getResources().getText(R.string.title_all_apps));
        }
        if (z) {
            ObservableProperty observableProperty = ((AppInfoViewData) viewData).highlightText;
            TextViewHelperKt.setHighLightText(this.titleView, m318bindData$lambda4(observableProperty), getHighlightColor());
            DisposableHandle bind = observableProperty.bind(new Function1() { // from class: androidx.picker.features.composable.title.ComposableTitleViewHolder$bindData$3
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    TextView textView2;
                    int highlightColor;
                    textView2 = ComposableTitleViewHolder.this.titleView;
                    highlightColor = ComposableTitleViewHolder.this.getHighlightColor();
                    TextViewHelperKt.setHighLightText(textView2, (String) obj, highlightColor);
                    return Unit.INSTANCE;
                }
            });
            if (bind != null) {
                arrayList.add(bind);
            }
        }
        this.disposableHandle = new DisposableHandle() { // from class: androidx.picker.features.composable.title.ComposableTitleViewHolder$$ExternalSyntheticLambda0
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                ComposableTitleViewHolder.m319bindData$lambda6(arrayList);
            }
        };
    }

    @Override // androidx.picker.features.composable.ComposableViewHolder
    public void onViewRecycled(View view) {
        super.onViewRecycled(view);
        DisposableHandle disposableHandle = this.disposableHandle;
        if (disposableHandle != null) {
            disposableHandle.dispose();
        }
    }
}
