package com.android.systemui.accessibility.hearingaid;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHapClient;
import android.bluetooth.BluetoothHapPresetInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.bluetooth.AmbientVolumeUiController;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HapClientProfile;
import com.android.systemui.R;
import com.android.systemui.accessibility.hearingaid.HearingDevicesInputRoutingController;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes.dex */
public final /* synthetic */ class HearingDevicesDialogDelegate$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HearingDevicesDialogDelegate f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ HearingDevicesDialogDelegate$$ExternalSyntheticLambda3(HearingDevicesDialogDelegate hearingDevicesDialogDelegate, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = hearingDevicesDialogDelegate;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 0;
        switch (this.$r8$classId) {
            case 0:
                HearingDevicesDialogDelegate hearingDevicesDialogDelegate = this.f$0;
                List list = (List) this.f$1;
                String str = HearingDevicesDialogDelegate.ACTION_BLUETOOTH_DEVICE_DETAILS;
                hearingDevicesDialogDelegate.getClass();
                CachedBluetoothDevice activeHearingDevice = HearingDevicesDialogDelegate.getActiveHearingDevice(list);
                HearingDevicesPresetsController hearingDevicesPresetsController = hearingDevicesDialogDelegate.mPresetController;
                if (hearingDevicesPresetsController != null) {
                    if (activeHearingDevice == null || !activeHearingDevice.getProfiles().stream().anyMatch(new HearingDevicesPresetsController$$ExternalSyntheticLambda0(1))) {
                        hearingDevicesPresetsController.mDevice = null;
                    } else {
                        hearingDevicesPresetsController.mDevice = activeHearingDevice;
                    }
                    hearingDevicesPresetsController.refreshPresetInfo();
                    hearingDevicesDialogDelegate.mPresetLayout.setVisibility(hearingDevicesDialogDelegate.mPresetController.isPresetControlAvailable() ? 0 : 8);
                }
                HearingDevicesInputRoutingController hearingDevicesInputRoutingController = hearingDevicesDialogDelegate.mInputRoutingController;
                if (hearingDevicesInputRoutingController != null) {
                    hearingDevicesInputRoutingController.cachedDevice = activeHearingDevice;
                    BuildersKt.launch$default(hearingDevicesInputRoutingController.bgCoroutineScope, null, null, new HearingDevicesInputRoutingController$isInputRoutingControlAvailable$1(hearingDevicesInputRoutingController, new HearingDevicesDialogDelegate$$ExternalSyntheticLambda9(hearingDevicesDialogDelegate, i), null), 3);
                }
                AmbientVolumeUiController ambientVolumeUiController = hearingDevicesDialogDelegate.mAmbientController;
                if (ambientVolumeUiController != null) {
                    ambientVolumeUiController.loadDevice(activeHearingDevice);
                    break;
                }
                break;
            case 1:
                HearingDevicesDialogDelegate hearingDevicesDialogDelegate2 = this.f$0;
                List list2 = (List) this.f$1;
                HearingDevicesListAdapter hearingDevicesListAdapter = hearingDevicesDialogDelegate2.mDeviceListAdapter;
                if (hearingDevicesListAdapter != null) {
                    hearingDevicesListAdapter.mItemList.clear();
                    hearingDevicesListAdapter.mItemList.addAll(list2);
                    hearingDevicesListAdapter.notifyDataSetChanged();
                    break;
                }
                break;
            default:
                final HearingDevicesDialogDelegate hearingDevicesDialogDelegate3 = this.f$0;
                final SystemUIDialog systemUIDialog = (SystemUIDialog) this.f$1;
                String str2 = HearingDevicesDialogDelegate.ACTION_BLUETOOTH_DEVICE_DETAILS;
                final List hearingDeviceItemList = hearingDevicesDialogDelegate3.getHearingDeviceItemList();
                final CachedBluetoothDevice activeHearingDevice2 = HearingDevicesDialogDelegate.getActiveHearingDevice(hearingDeviceItemList);
                hearingDevicesDialogDelegate3.mLocalBluetoothManager.mEventManager.registerCallback(hearingDevicesDialogDelegate3);
                hearingDevicesDialogDelegate3.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() throws Resources.NotFoundException {
                        final HearingDevicesDialogDelegate hearingDevicesDialogDelegate4 = hearingDevicesDialogDelegate3;
                        final SystemUIDialog systemUIDialog2 = systemUIDialog;
                        List list3 = hearingDeviceItemList;
                        CachedBluetoothDevice cachedBluetoothDevice = activeHearingDevice2;
                        String str3 = HearingDevicesDialogDelegate.ACTION_BLUETOOTH_DEVICE_DETAILS;
                        hearingDevicesDialogDelegate4.getClass();
                        RecyclerView recyclerView = (RecyclerView) systemUIDialog2.requireViewById(R.id.device_list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(systemUIDialog2.getContext()));
                        HearingDevicesListAdapter hearingDevicesListAdapter2 = new HearingDevicesListAdapter(list3, hearingDevicesDialogDelegate4);
                        hearingDevicesDialogDelegate4.mDeviceListAdapter = hearingDevicesListAdapter2;
                        recyclerView.setAdapter(hearingDevicesListAdapter2);
                        Button button = (Button) systemUIDialog2.requireViewById(R.id.pair_new_device_button);
                        boolean z = hearingDevicesDialogDelegate4.mShowPairNewDevice;
                        button.setVisibility(z ? 0 : 8);
                        if (z) {
                            button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$$ExternalSyntheticLambda5
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    HearingDevicesDialogDelegate hearingDevicesDialogDelegate5 = hearingDevicesDialogDelegate4;
                                    SystemUIDialog systemUIDialog3 = systemUIDialog2;
                                    String str4 = HearingDevicesDialogDelegate.ACTION_BLUETOOTH_DEVICE_DETAILS;
                                    hearingDevicesDialogDelegate5.getClass();
                                    hearingDevicesDialogDelegate5.mUiEventLogger.log(HearingDevicesUiEvent.HEARING_DEVICES_PAIR, hearingDevicesDialogDelegate5.mLaunchSourceId, null);
                                    SystemUIDialog systemUIDialog4 = hearingDevicesDialogDelegate5.mDialog;
                                    if (systemUIDialog4 != null) {
                                        systemUIDialog4.dismiss();
                                    }
                                    Intent intent = new Intent("android.settings.HEARING_DEVICES_PAIRING_SETTINGS").setPackage(hearingDevicesDialogDelegate5.mQSSettingsPackageRepository.getSettingsPackageName());
                                    DialogTransitionAnimator dialogTransitionAnimator = hearingDevicesDialogDelegate5.mDialogTransitionAnimator;
                                    dialogTransitionAnimator.getClass();
                                    hearingDevicesDialogDelegate5.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0, DialogTransitionAnimator.createActivityTransitionController$default(systemUIDialog3, dialogTransitionAnimator));
                                }
                            });
                        }
                        HearingDevicesPresetsController hearingDevicesPresetsController2 = new HearingDevicesPresetsController(hearingDevicesDialogDelegate4.mProfileManager, hearingDevicesDialogDelegate4.mPresetCallback);
                        hearingDevicesDialogDelegate4.mPresetController = hearingDevicesPresetsController2;
                        if (cachedBluetoothDevice == null || !cachedBluetoothDevice.getProfiles().stream().anyMatch(new HearingDevicesPresetsController$$ExternalSyntheticLambda0(1))) {
                            hearingDevicesPresetsController2.mDevice = null;
                        } else {
                            hearingDevicesPresetsController2.mDevice = cachedBluetoothDevice;
                        }
                        hearingDevicesPresetsController2.refreshPresetInfo();
                        hearingDevicesDialogDelegate4.mPresetSpinner = (Spinner) systemUIDialog2.requireViewById(R.id.preset_spinner);
                        HearingDevicesSpinnerAdapter hearingDevicesSpinnerAdapter = new HearingDevicesSpinnerAdapter(systemUIDialog2.getContext());
                        hearingDevicesDialogDelegate4.mPresetInfoAdapter = hearingDevicesSpinnerAdapter;
                        hearingDevicesDialogDelegate4.mPresetSpinner.setAdapter((SpinnerAdapter) hearingDevicesSpinnerAdapter);
                        hearingDevicesDialogDelegate4.mPresetSpinner.setAccessibilityDelegate(new View.AccessibilityDelegate(hearingDevicesDialogDelegate4) { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate.2
                            public AnonymousClass2(final HearingDevicesDialogDelegate hearingDevicesDialogDelegate42) {
                            }

                            @Override // android.view.View.AccessibilityDelegate
                            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                                accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
                                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                            }
                        });
                        HearingDevicesPresetsController hearingDevicesPresetsController3 = hearingDevicesDialogDelegate42.mPresetController;
                        CachedBluetoothDevice cachedBluetoothDevice2 = hearingDevicesPresetsController3.mDevice;
                        hearingDevicesDialogDelegate42.refreshPresetUi((cachedBluetoothDevice2 == null || hearingDevicesPresetsController3.mHapClientProfile == null) ? 0 : hearingDevicesPresetsController3.mActivePresetIndex, (cachedBluetoothDevice2 == null || hearingDevicesPresetsController3.mHapClientProfile == null) ? Collections.EMPTY_LIST : hearingDevicesPresetsController3.mPresetInfos);
                        hearingDevicesDialogDelegate42.mPresetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate.3
                            public AnonymousClass3() {
                            }

                            @Override // android.widget.AdapterView.OnItemSelectedListener
                            public final void onItemSelected(AdapterView adapterView, View view, int i2, long j) {
                                HapClientProfile hapClientProfile;
                                int hapGroup;
                                HearingDevicesSpinnerAdapter hearingDevicesSpinnerAdapter2 = HearingDevicesDialogDelegate.this.mPresetInfoAdapter;
                                hearingDevicesSpinnerAdapter2.mSelectedPosition = i2;
                                hearingDevicesSpinnerAdapter2.notifyDataSetChanged();
                                HearingDevicesDialogDelegate hearingDevicesDialogDelegate5 = HearingDevicesDialogDelegate.this;
                                hearingDevicesDialogDelegate5.mUiEventLogger.log(HearingDevicesUiEvent.HEARING_DEVICES_PRESET_SELECT, hearingDevicesDialogDelegate5.mLaunchSourceId, null);
                                HearingDevicesPresetsController hearingDevicesPresetsController4 = HearingDevicesDialogDelegate.this.mPresetController;
                                int index = ((BluetoothHapPresetInfo) ((hearingDevicesPresetsController4.mDevice == null || hearingDevicesPresetsController4.mHapClientProfile == null) ? Collections.EMPTY_LIST : hearingDevicesPresetsController4.mPresetInfos).get(i2)).getIndex();
                                CachedBluetoothDevice cachedBluetoothDevice3 = hearingDevicesPresetsController4.mDevice;
                                if (cachedBluetoothDevice3 == null || (hapClientProfile = hearingDevicesPresetsController4.mHapClientProfile) == null) {
                                    return;
                                }
                                hearingDevicesPresetsController4.mSelectedPresetIndex = index;
                                BluetoothDevice bluetoothDevice = cachedBluetoothDevice3.mDevice;
                                BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
                                boolean zSupportsSynchronizedPresets = bluetoothHapClient == null ? false : bluetoothHapClient.supportsSynchronizedPresets(bluetoothDevice);
                                HapClientProfile hapClientProfile2 = hearingDevicesPresetsController4.mHapClientProfile;
                                BluetoothDevice bluetoothDevice2 = hearingDevicesPresetsController4.mDevice.mDevice;
                                BluetoothHapClient bluetoothHapClient2 = hapClientProfile2.mService;
                                if (bluetoothHapClient2 == null) {
                                    Log.w("HapClientProfile", "Proxy not attached to service. Cannot get hap group.");
                                    hapGroup = -1;
                                } else {
                                    hapGroup = bluetoothHapClient2.getHapGroup(bluetoothDevice2);
                                }
                                if (!zSupportsSynchronizedPresets) {
                                    hearingDevicesPresetsController4.selectPresetIndependently(index);
                                    return;
                                }
                                if (hapGroup == -1) {
                                    Log.w("HearingDevicesPresetsController", "supportSynchronizedPresets but hapGroupId is invalid.");
                                    hearingDevicesPresetsController4.selectPresetIndependently(index);
                                    return;
                                }
                                if (hearingDevicesPresetsController4.mDevice == null || hearingDevicesPresetsController4.mHapClientProfile == null) {
                                    return;
                                }
                                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(index, hapGroup, "selectPresetSynchronously, presetIndex: ", ", groupId: ", ", device: ");
                                sbM.append(hearingDevicesPresetsController4.mDevice.mDevice.getAddress());
                                Log.d("HearingDevicesPresetsController", sbM.toString());
                                BluetoothHapClient bluetoothHapClient3 = hearingDevicesPresetsController4.mHapClientProfile.mService;
                                if (bluetoothHapClient3 == null) {
                                    Log.w("HapClientProfile", "Proxy not attached to service. Cannot select preset for group.");
                                } else {
                                    bluetoothHapClient3.selectPresetForGroup(hapGroup, index);
                                }
                            }

                            @Override // android.widget.AdapterView.OnItemSelectedListener
                            public final void onNothingSelected(AdapterView adapterView) {
                            }
                        });
                        View viewRequireViewById = systemUIDialog2.requireViewById(R.id.preset_layout);
                        hearingDevicesDialogDelegate42.mPresetLayout = viewRequireViewById;
                        viewRequireViewById.setVisibility(hearingDevicesDialogDelegate42.mPresetController.isPresetControlAvailable() ? 0 : 8);
                        hearingDevicesDialogDelegate42.mBgExecutor.execute(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda2(hearingDevicesDialogDelegate42, 2));
                        HearingDevicesInputRoutingController hearingDevicesInputRoutingControllerCreate = hearingDevicesDialogDelegate42.mInputRoutingControllerFactory.create(systemUIDialog2.getContext());
                        hearingDevicesDialogDelegate42.mInputRoutingController = hearingDevicesInputRoutingControllerCreate;
                        hearingDevicesInputRoutingControllerCreate.cachedDevice = cachedBluetoothDevice;
                        hearingDevicesDialogDelegate42.mInputRoutingSpinner = (Spinner) systemUIDialog2.requireViewById(R.id.input_routing_spinner);
                        HearingDevicesSpinnerAdapter hearingDevicesSpinnerAdapter2 = new HearingDevicesSpinnerAdapter(systemUIDialog2.getContext());
                        hearingDevicesDialogDelegate42.mInputRoutingAdapter = hearingDevicesSpinnerAdapter2;
                        Context context = systemUIDialog2.getContext();
                        HearingDevicesInputRoutingController.Companion.getClass();
                        hearingDevicesSpinnerAdapter2.addAll(context.getResources().getStringArray(R.array.hearing_device_input_routing_options));
                        hearingDevicesDialogDelegate42.mInputRoutingSpinner.setAdapter((SpinnerAdapter) hearingDevicesDialogDelegate42.mInputRoutingAdapter);
                        hearingDevicesDialogDelegate42.mInputRoutingSpinner.setAccessibilityDelegate(new View.AccessibilityDelegate(hearingDevicesDialogDelegate42) { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate.4
                            public AnonymousClass4(final HearingDevicesDialogDelegate hearingDevicesDialogDelegate42) {
                            }

                            @Override // android.view.View.AccessibilityDelegate
                            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                                accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
                                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                            }
                        });
                        CachedBluetoothDevice cachedBluetoothDevice3 = hearingDevicesDialogDelegate42.mInputRoutingController.cachedDevice;
                        int iOrdinal = (cachedBluetoothDevice3 == null || cachedBluetoothDevice3.mDevice.isMicrophonePreferredForCalls()) ? HearingDevicesInputRoutingController.InputRoutingValue.HEARING_DEVICE.ordinal() : HearingDevicesInputRoutingController.InputRoutingValue.BUILTIN_MIC.ordinal();
                        hearingDevicesDialogDelegate42.mInputRoutingSpinner.setSelection(iOrdinal, false);
                        HearingDevicesSpinnerAdapter hearingDevicesSpinnerAdapter3 = hearingDevicesDialogDelegate42.mInputRoutingAdapter;
                        hearingDevicesSpinnerAdapter3.mSelectedPosition = iOrdinal;
                        hearingDevicesSpinnerAdapter3.notifyDataSetChanged();
                        hearingDevicesDialogDelegate42.mInputRoutingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate.5
                            public AnonymousClass5() {
                            }

                            @Override // android.widget.AdapterView.OnItemSelectedListener
                            public final void onItemSelected(AdapterView adapterView, View view, int i2, long j) {
                                HearingDevicesSpinnerAdapter hearingDevicesSpinnerAdapter4 = HearingDevicesDialogDelegate.this.mInputRoutingAdapter;
                                hearingDevicesSpinnerAdapter4.mSelectedPosition = i2;
                                hearingDevicesSpinnerAdapter4.notifyDataSetChanged();
                                HearingDevicesDialogDelegate hearingDevicesDialogDelegate5 = HearingDevicesDialogDelegate.this;
                                hearingDevicesDialogDelegate5.mUiEventLogger.log(HearingDevicesUiEvent.HEARING_DEVICES_INPUT_ROUTING_SELECT, hearingDevicesDialogDelegate5.mLaunchSourceId, null);
                                HearingDevicesInputRoutingController hearingDevicesInputRoutingController2 = HearingDevicesDialogDelegate.this.mInputRoutingController;
                                CachedBluetoothDevice cachedBluetoothDevice4 = hearingDevicesInputRoutingController2.cachedDevice;
                                if (cachedBluetoothDevice4 == null) {
                                    return;
                                }
                                boolean z2 = i2 == HearingDevicesInputRoutingController.InputRoutingValue.BUILTIN_MIC.ordinal();
                                if (!hearingDevicesInputRoutingController2.audioRoutingHelper.setPreferredInputDeviceForCalls(cachedBluetoothDevice4, z2 ? 2 : 0)) {
                                    Log.d("HearingDevicesInputRoutingController", "Fail to configure setPreferredInputDeviceForCalls");
                                }
                                boolean z3 = !z2;
                                cachedBluetoothDevice4.mDevice.setMicrophonePreferredForCalls(z3);
                                Set set = cachedBluetoothDevice4.mMemberDevices;
                                set.getClass();
                                if (set.isEmpty()) {
                                    return;
                                }
                                Iterator it = set.iterator();
                                while (it.hasNext()) {
                                    ((CachedBluetoothDevice) it.next()).mDevice.setMicrophonePreferredForCalls(z3);
                                }
                            }

                            @Override // android.widget.AdapterView.OnItemSelectedListener
                            public final void onNothingSelected(AdapterView adapterView) {
                            }
                        });
                        hearingDevicesDialogDelegate42.mInputRoutingLayout = systemUIDialog2.requireViewById(R.id.input_routing_layout);
                        HearingDevicesInputRoutingController hearingDevicesInputRoutingController2 = hearingDevicesDialogDelegate42.mInputRoutingController;
                        HearingDevicesDialogDelegate$$ExternalSyntheticLambda9 hearingDevicesDialogDelegate$$ExternalSyntheticLambda9 = new HearingDevicesDialogDelegate$$ExternalSyntheticLambda9(hearingDevicesDialogDelegate42, 1);
                        hearingDevicesInputRoutingController2.getClass();
                        BuildersKt.launch$default(hearingDevicesInputRoutingController2.bgCoroutineScope, null, null, new HearingDevicesInputRoutingController$isInputRoutingControlAvailable$1(hearingDevicesInputRoutingController2, hearingDevicesDialogDelegate$$ExternalSyntheticLambda9, null), 3);
                        AmbientVolumeLayout ambientVolumeLayout = (AmbientVolumeLayout) hearingDevicesDialogDelegate42.mDialog.requireViewById(R.id.ambient_layout);
                        ambientVolumeLayout.mUiEventLogger = hearingDevicesDialogDelegate42.mUiEventLogger;
                        ambientVolumeLayout.mLaunchSourceId = hearingDevicesDialogDelegate42.mLaunchSourceId;
                        AmbientVolumeUiController ambientVolumeUiController2 = new AmbientVolumeUiController(hearingDevicesDialogDelegate42.mDialog.getContext(), hearingDevicesDialogDelegate42.mLocalBluetoothManager, ambientVolumeLayout);
                        hearingDevicesDialogDelegate42.mAmbientController = ambientVolumeUiController2;
                        ambientVolumeUiController2.mShowUiWhenLocalDataExist = false;
                        ambientVolumeUiController2.loadDevice(cachedBluetoothDevice);
                        hearingDevicesDialogDelegate42.mBgExecutor.execute(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda2(hearingDevicesDialogDelegate42, 1));
                        Context context2 = systemUIDialog2.getContext();
                        ArrayList arrayList = new ArrayList();
                        PackageManager packageManager = context2.getPackageManager();
                        Intent intent = HearingDevicesDialogDelegate.LIVE_CAPTION_INTENT;
                        intent.setPackage(packageManager.getSystemCaptionsServicePackageName());
                        ToolItem toolItem = packageManager.queryIntentActivities(intent, 0).isEmpty() ? null : new ToolItem(context2.getString(R.string.quick_settings_hearing_devices_live_caption_title), context2.getDrawable(R.drawable.ic_volume_odi_captions), intent, true);
                        if (toolItem != null) {
                            arrayList.add(toolItem);
                        }
                        try {
                            arrayList.addAll(HearingDevicesToolItemParser.parseStringArray(context2, context2.getResources().getStringArray(R.array.config_quickSettingsHearingDevicesRelatedToolName), context2.getResources().getStringArray(R.array.config_quickSettingsHearingDevicesRelatedToolIcon)));
                        } catch (Resources.NotFoundException unused) {
                            Log.i("HearingDevicesDialogDelegate", "No hearing devices related tool config resource");
                        }
                        systemUIDialog2.requireViewById(R.id.tools_layout).setVisibility(arrayList.isEmpty() ? 8 : 0);
                        LinearLayout linearLayout = (LinearLayout) systemUIDialog2.requireViewById(R.id.tools_container);
                        for (int i2 = 0; i2 < arrayList.size(); i2++) {
                            ToolItem toolItem2 = (ToolItem) arrayList.get(i2);
                            final View viewInflate = LayoutInflater.from(context2).inflate(R.layout.hearing_tool_item, (ViewGroup) linearLayout, false);
                            ImageView imageView = (ImageView) viewInflate.requireViewById(R.id.tool_icon);
                            TextView textView = (TextView) viewInflate.requireViewById(R.id.tool_name);
                            viewInflate.setContentDescription(toolItem2.toolName);
                            imageView.setImageDrawable(toolItem2.toolIcon);
                            if (toolItem2.isCustomIcon) {
                                imageView.getDrawable().mutate().setTint(context2.getColor(android.R.color.resolver_text_color_secondary_dark));
                            }
                            textView.setText(toolItem2.toolName);
                            final Intent intent2 = toolItem2.toolIntent;
                            viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$$ExternalSyntheticLambda6
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    String strFlattenToString;
                                    HearingDevicesDialogDelegate hearingDevicesDialogDelegate5 = hearingDevicesDialogDelegate42;
                                    Intent intent3 = intent2;
                                    View view2 = viewInflate;
                                    String str4 = HearingDevicesDialogDelegate.ACTION_BLUETOOTH_DEVICE_DETAILS;
                                    hearingDevicesDialogDelegate5.getClass();
                                    if (intent3.getComponent() != null) {
                                        strFlattenToString = intent3.getComponent().flattenToString();
                                    } else {
                                        strFlattenToString = intent3.getPackage() + "/" + intent3.getAction();
                                    }
                                    hearingDevicesDialogDelegate5.mUiEventLogger.log(HearingDevicesUiEvent.HEARING_DEVICES_RELATED_TOOL_CLICK, hearingDevicesDialogDelegate5.mLaunchSourceId, strFlattenToString);
                                    SystemUIDialog systemUIDialog3 = hearingDevicesDialogDelegate5.mDialog;
                                    if (systemUIDialog3 != null) {
                                        systemUIDialog3.dismiss();
                                    }
                                    DialogTransitionAnimator dialogTransitionAnimator = hearingDevicesDialogDelegate5.mDialogTransitionAnimator;
                                    dialogTransitionAnimator.getClass();
                                    hearingDevicesDialogDelegate5.mActivityStarter.postStartActivityDismissingKeyguard(intent3, 0, DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view2));
                                }
                            });
                            linearLayout.addView(viewInflate);
                            if (i2 != arrayList.size() - 1) {
                                int dimensionPixelSize = context2.getResources().getDimensionPixelSize(R.dimen.hearing_devices_layout_margin);
                                Space space = new Space(context2);
                                space.setLayoutParams(new LinearLayout.LayoutParams(dimensionPixelSize, 0));
                                linearLayout.addView(space);
                            }
                        }
                    }
                });
                break;
        }
    }
}
