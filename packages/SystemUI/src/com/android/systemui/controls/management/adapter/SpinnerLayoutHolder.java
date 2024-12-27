package com.android.systemui.controls.management.adapter;

import android.content.ComponentName;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ComponentInfo;
import com.android.systemui.controls.controller.util.BadgeProvider;
import com.android.systemui.controls.controller.util.BadgeProviderImpl;
import com.android.systemui.controls.management.model.MainComponentModel;
import com.android.systemui.controls.management.model.MainModel;
import com.android.systemui.controls.panels.SecSelectedComponentRepositoryImpl;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl$spinnerItemSelectionChangedCallback$1;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl$spinnerTouchCallback$1;
import com.android.systemui.controls.ui.SecSelectionItem;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.controls.ui.view.ControlsAppCompatSpinner;
import com.android.systemui.controls.ui.view.ControlsSpinner;
import com.android.systemui.controls.util.SALogger;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SpinnerLayoutHolder extends Holder {
    public final ControlsSpinner spinner;

    public SpinnerLayoutHolder(View view, ControlsSpinner.SpinnerTouchCallback spinnerTouchCallback, ControlsSpinner.SpinnerItemSelectionChangedCallback spinnerItemSelectionChangedCallback, View.OnClickListener onClickListener, BadgeProvider badgeProvider) {
        super(view, null);
        ControlsSpinner controlsSpinner = (ControlsSpinner) view.requireViewById(R.id.controls_spinner);
        this.spinner = controlsSpinner;
        controlsSpinner.spinnerTouchCallback = spinnerTouchCallback;
        controlsSpinner.spinnerItemSelectedChangedCallback = spinnerItemSelectionChangedCallback;
        controlsSpinner.buttonClickCallback = onClickListener;
        controlsSpinner.badgeProvider = badgeProvider;
    }

    @Override // com.android.systemui.controls.management.adapter.Holder
    public final void bindData(MainModel mainModel) {
        if (mainModel instanceof MainComponentModel) {
            MainComponentModel mainComponentModel = (MainComponentModel) mainModel;
            List list = mainComponentModel.controlsSpinnerInfo;
            ComponentName componentName = mainComponentModel.selected;
            boolean z = mainComponentModel.showButton;
            final ControlsSpinner controlsSpinner = this.spinner;
            Object obj = null;
            if (z) {
                LinearLayout linearLayout = controlsSpinner.launchButtonLayout;
                if (linearLayout == null) {
                    linearLayout = null;
                }
                linearLayout.setVisibility(0);
                Button button = controlsSpinner.launchButton;
                if (button == null) {
                    button = null;
                }
                button.setOnClickListener(controlsSpinner.buttonClickCallback);
            } else {
                LinearLayout linearLayout2 = controlsSpinner.launchButtonLayout;
                if (linearLayout2 == null) {
                    linearLayout2 = null;
                }
                linearLayout2.setVisibility(8);
                Button button2 = controlsSpinner.launchButton;
                if (button2 == null) {
                    button2 = null;
                }
                button2.setOnClickListener(null);
            }
            if (list.size() <= 1) {
                TextView textView = controlsSpinner.noSpinner;
                if (textView == null) {
                    textView = null;
                }
                ControlsSpinner.SelectionItem selectionItem = (ControlsSpinner.SelectionItem) CollectionsKt___CollectionsKt.firstOrNull(list);
                textView.setText(selectionItem != null ? selectionItem.getAppName() : null);
                TextView textView2 = controlsSpinner.noSpinner;
                if (textView2 == null) {
                    textView2 = null;
                }
                textView2.setVisibility(0);
                ControlsAppCompatSpinner controlsAppCompatSpinner = controlsSpinner.spinner;
                (controlsAppCompatSpinner != null ? controlsAppCompatSpinner : null).setVisibility(8);
                return;
            }
            TextView textView3 = controlsSpinner.noSpinner;
            if (textView3 == null) {
                textView3 = null;
            }
            textView3.setVisibility(8);
            ControlsAppCompatSpinner controlsAppCompatSpinner2 = controlsSpinner.spinner;
            if (controlsAppCompatSpinner2 == null) {
                controlsAppCompatSpinner2 = null;
            }
            controlsAppCompatSpinner2.setVisibility(0);
            final ControlsSpinner.ItemAdapter itemAdapter = new ControlsSpinner.ItemAdapter(controlsSpinner.getContext(), R.layout.controls_spinner_base_item, R.layout.controls_spinner_dropdown, controlsSpinner.badgeProvider);
            itemAdapter.addAll(list);
            ControlsAppCompatSpinner controlsAppCompatSpinner3 = controlsSpinner.spinner;
            if (controlsAppCompatSpinner3 == null) {
                controlsAppCompatSpinner3 = null;
            }
            controlsAppCompatSpinner3.setAdapter((SpinnerAdapter) itemAdapter);
            controlsAppCompatSpinner3.setDropDownHorizontalOffset((int) controlsAppCompatSpinner3.getResources().getDimension(R.dimen.control_spinner_popup_side_offset));
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (Intrinsics.areEqual(((ControlsSpinner.SelectionItem) next).getComponentName(), componentName)) {
                    obj = next;
                    break;
                }
            }
            ControlsSpinner.SelectionItem selectionItem2 = (ControlsSpinner.SelectionItem) obj;
            controlsAppCompatSpinner3.setSelection(list.indexOf(selectionItem2));
            controlsSpinner.previous = selectionItem2;
            controlsAppCompatSpinner3.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.controls.ui.view.ControlsSpinner$showSpinner$1$1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    ControlsSpinner.SpinnerTouchCallback spinnerTouchCallback = controlsSpinner.spinnerTouchCallback;
                    if (spinnerTouchCallback == null) {
                        return false;
                    }
                    SecControlsUiControllerImpl$spinnerTouchCallback$1 secControlsUiControllerImpl$spinnerTouchCallback$1 = (SecControlsUiControllerImpl$spinnerTouchCallback$1) spinnerTouchCallback;
                    if (motionEvent == null || motionEvent.getAction() != 1) {
                        return false;
                    }
                    secControlsUiControllerImpl$spinnerTouchCallback$1.this$0.saLogger.sendEvent(SALogger.Event.OpenSpinner.INSTANCE);
                    return false;
                }
            });
            controlsAppCompatSpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.android.systemui.controls.ui.view.ControlsSpinner$showSpinner$1$2
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
                    SelectedComponentRepository.SelectedComponent selectedComponent;
                    if (adapterView != null) {
                        ControlsSpinner.ItemAdapter itemAdapter2 = ControlsSpinner.ItemAdapter.this;
                        ControlsSpinner controlsSpinner2 = controlsSpinner;
                        ControlsSpinner.SelectionItem selectionItem3 = (ControlsSpinner.SelectionItem) adapterView.getItemAtPosition(i);
                        itemAdapter2.mSelectedIndex = i;
                        itemAdapter2.notifyDataSetChanged();
                        if (selectionItem3.equals(controlsSpinner2.previous)) {
                            return;
                        }
                        ControlsSpinner.SpinnerItemSelectionChangedCallback spinnerItemSelectionChangedCallback = controlsSpinner2.spinnerItemSelectedChangedCallback;
                        if (spinnerItemSelectionChangedCallback != null) {
                            SecSelectionItem secSelectionItem = (SecSelectionItem) selectionItem3;
                            SecControlsUiControllerImpl secControlsUiControllerImpl = ((SecControlsUiControllerImpl$spinnerItemSelectionChangedCallback$1) spinnerItemSelectionChangedCallback).this$0;
                            List<ComponentInfo> list2 = secControlsUiControllerImpl.allComponentInfo;
                            Object obj2 = null;
                            if (list2 == null) {
                                list2 = null;
                            }
                            for (ComponentInfo componentInfo : list2) {
                                ComponentName componentName2 = componentInfo.componentName;
                                List list3 = componentInfo.structureInfos;
                                Objects.toString(componentName2);
                                Objects.toString(list3);
                            }
                            List list4 = secControlsUiControllerImpl.allComponentInfo;
                            if (list4 == null) {
                                list4 = null;
                            }
                            Iterator it2 = list4.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    break;
                                }
                                Object next2 = it2.next();
                                if (Intrinsics.areEqual(((ComponentInfo) next2).componentName, secSelectionItem.componentName)) {
                                    obj2 = next2;
                                    break;
                                }
                            }
                            ComponentInfo componentInfo2 = (ComponentInfo) obj2;
                            if (componentInfo2 != null && !Intrinsics.areEqual(componentInfo2.componentName, secControlsUiControllerImpl.selectedItem.getComponentName())) {
                                secControlsUiControllerImpl.saveFavorites(componentInfo2.componentName);
                                ComponentInfo.Companion.getClass();
                                if (!componentInfo2.equals(ComponentInfo.EMPTY_COMPONENT_INFO)) {
                                    int i2 = SecControlsUiControllerImpl.$r8$clinit;
                                    ControlsServiceInfo isPanelComponent = secControlsUiControllerImpl.isPanelComponent(componentInfo2);
                                    if (isPanelComponent != null) {
                                        selectedComponent = new SelectedComponentRepository.SelectedComponent(new SelectedItem.PanelItem(isPanelComponent.loadLabel(), componentInfo2.componentName));
                                    } else {
                                        ControlsServiceInfo access$getComponent = SecControlsUiControllerImpl.access$getComponent(secControlsUiControllerImpl, componentInfo2);
                                        selectedComponent = new SelectedComponentRepository.SelectedComponent(new SelectedItem.ComponentItem(access$getComponent != null ? access$getComponent.loadLabel() : "", componentInfo2));
                                    }
                                    ((SecSelectedComponentRepositoryImpl) secControlsUiControllerImpl.secSelectedComponentRepository).setSelectedComponent(selectedComponent);
                                }
                                secControlsUiControllerImpl.isChanged = false;
                                secControlsUiControllerImpl.saLogger.sendEvent(new SALogger.Event.TapSpinnerApp(secControlsUiControllerImpl.selectedItem.getComponentName().getPackageName()));
                                secControlsUiControllerImpl.adapterNeedToUpdateDataSet = true;
                                ControlsServiceInfo isPanelComponent2 = secControlsUiControllerImpl.isPanelComponent(componentInfo2);
                                ControlsServiceInfo access$getComponent2 = SecControlsUiControllerImpl.access$getComponent(secControlsUiControllerImpl, componentInfo2);
                                SecControlsUiControllerImpl.access$reload(secControlsUiControllerImpl, isPanelComponent2 != null ? new SelectedItem.PanelItem(isPanelComponent2.loadLabel(), componentInfo2.componentName) : new SelectedItem.ComponentItem(access$getComponent2 != null ? access$getComponent2.loadLabel() : "", componentInfo2));
                            }
                        }
                        controlsSpinner2.previous = selectionItem3;
                        BadgeProvider badgeProvider = controlsSpinner2.badgeProvider;
                        if (badgeProvider != null) {
                            ((BadgeProviderImpl) badgeProvider).dismiss();
                        }
                    }
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public final void onNothingSelected(AdapterView adapterView) {
                }
            });
        }
    }
}
