package androidx.picker.features.composable.widget;

import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.CompoundButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.util.Supplier;
import androidx.picker.features.composable.ActionableComposableViewHolder;
import androidx.picker.loader.select.SelectableItem;
import androidx.picker.model.viewdata.AllAppsViewData;
import androidx.picker.model.viewdata.ViewData;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComposableAllAppSwitchViewHolder extends ActionableComposableViewHolder {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private DisposableHandle disposableHandle;
    private final View divider;
    private boolean fromUser;

    /* renamed from: switch, reason: not valid java name */
    private final SwitchCompat f826switch;

    static {
        MutablePropertyReference0Impl mutablePropertyReference0Impl = new MutablePropertyReference0Impl(ComposableAllAppSwitchViewHolder.class, "selected", "<v#0>", 0);
        Reflection.factory.getClass();
        $$delegatedProperties = new KProperty[]{mutablePropertyReference0Impl};
    }

    public ComposableAllAppSwitchViewHolder(View view) {
        super(view);
        View findViewById = view.findViewById(R.id.switch_widget);
        Intrinsics.checkNotNull(findViewById);
        this.f826switch = (SwitchCompat) findViewById;
        View findViewById2 = view.findViewById(R.id.switch_divider_widget);
        Intrinsics.checkNotNull(findViewById2);
        this.divider = findViewById2;
    }

    /* renamed from: bindData$lambda-0, reason: not valid java name */
    private static final boolean m323bindData$lambda0(SelectableItem selectableItem) {
        return selectableItem.getValue(null, $$delegatedProperties[0]).booleanValue();
    }

    /* renamed from: bindData$lambda-1, reason: not valid java name */
    private static final void m324bindData$lambda1(SelectableItem selectableItem, boolean z) {
        selectableItem.setValue(null, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: bindData$lambda-2, reason: not valid java name */
    public static final void m325bindData$lambda2(ComposableAllAppSwitchViewHolder composableAllAppSwitchViewHolder, SelectableItem selectableItem, View view) {
        m324bindData$lambda1(selectableItem, composableAllAppSwitchViewHolder.f826switch.isChecked());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: bindData$lambda-3, reason: not valid java name */
    public static final boolean m326bindData$lambda3(ComposableAllAppSwitchViewHolder composableAllAppSwitchViewHolder, View view, MotionEvent motionEvent) {
        composableAllAppSwitchViewHolder.fromUser = true;
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: bindData$lambda-4, reason: not valid java name */
    public static final void m327bindData$lambda4(ComposableAllAppSwitchViewHolder composableAllAppSwitchViewHolder, SelectableItem selectableItem, CompoundButton compoundButton, boolean z) {
        if (composableAllAppSwitchViewHolder.fromUser) {
            m324bindData$lambda1(selectableItem, z);
        }
        composableAllAppSwitchViewHolder.fromUser = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: bindData$lambda-5, reason: not valid java name */
    public static final Boolean m328bindData$lambda5(ComposableAllAppSwitchViewHolder composableAllAppSwitchViewHolder, SelectableItem selectableItem) {
        m324bindData$lambda1(selectableItem, !composableAllAppSwitchViewHolder.f826switch.isChecked());
        return Boolean.TRUE;
    }

    @Override // androidx.picker.features.composable.ActionableComposableViewHolder, androidx.picker.features.composable.ComposableViewHolder
    public void bindData(ViewData viewData) {
        AllAppsViewData allAppsViewData = (AllAppsViewData) viewData;
        DisposableHandle disposableHandle = this.disposableHandle;
        if (disposableHandle != null) {
            disposableHandle.dispose();
        }
        Function1 function1 = new Function1() { // from class: androidx.picker.features.composable.widget.ComposableAllAppSwitchViewHolder$bindData$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                SwitchCompat switchCompat;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                ComposableAllAppSwitchViewHolder.this.fromUser = false;
                switchCompat = ComposableAllAppSwitchViewHolder.this.f826switch;
                switchCompat.setChecked(booleanValue);
                return Unit.INSTANCE;
            }
        };
        final SelectableItem selectableItem = allAppsViewData.selectableItem;
        this.disposableHandle = selectableItem.bind(function1);
        this.f826switch.setChecked(m323bindData$lambda0(selectableItem));
        this.f826switch.setOnClickListener(new View.OnClickListener() { // from class: androidx.picker.features.composable.widget.ComposableAllAppSwitchViewHolder$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ComposableAllAppSwitchViewHolder.m325bindData$lambda2(ComposableAllAppSwitchViewHolder.this, selectableItem, view);
            }
        });
        this.f826switch.setOnTouchListener(new View.OnTouchListener() { // from class: androidx.picker.features.composable.widget.ComposableAllAppSwitchViewHolder$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean m326bindData$lambda3;
                m326bindData$lambda3 = ComposableAllAppSwitchViewHolder.m326bindData$lambda3(ComposableAllAppSwitchViewHolder.this, view, motionEvent);
                return m326bindData$lambda3;
            }
        });
        this.f826switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: androidx.picker.features.composable.widget.ComposableAllAppSwitchViewHolder$$ExternalSyntheticLambda2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ComposableAllAppSwitchViewHolder.m327bindData$lambda4(ComposableAllAppSwitchViewHolder.this, selectableItem, compoundButton, z);
            }
        });
        this.divider.setVisibility(8);
        setDoAction(new Supplier() { // from class: androidx.picker.features.composable.widget.ComposableAllAppSwitchViewHolder$$ExternalSyntheticLambda3
            @Override // androidx.core.util.Supplier
            public final Object get() {
                Boolean m328bindData$lambda5;
                m328bindData$lambda5 = ComposableAllAppSwitchViewHolder.m328bindData$lambda5(ComposableAllAppSwitchViewHolder.this, selectableItem);
                return m328bindData$lambda5;
            }
        });
    }

    @Override // androidx.picker.features.composable.ActionableComposableViewHolder, androidx.picker.features.composable.ComposableViewHolder
    public void onBind(View view) {
        super.onBind(view);
        Object systemService = view.getContext().getSystemService("accessibility");
        AccessibilityManager accessibilityManager = systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null;
        if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
            return;
        }
        this.f826switch.setFocusable(false);
        this.f826switch.setClickable(false);
        view.setContentDescription(null);
    }

    @Override // androidx.picker.features.composable.ActionableComposableViewHolder, androidx.picker.features.composable.ComposableViewHolder
    public void onViewRecycled(View view) {
        super.onViewRecycled(view);
        this.f826switch.setOnCheckedChangeListener(null);
        this.f826switch.setOnTouchListener(null);
        this.fromUser = false;
        DisposableHandle disposableHandle = this.disposableHandle;
        if (disposableHandle != null) {
            disposableHandle.dispose();
        }
    }
}
