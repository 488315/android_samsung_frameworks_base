package com.android.systemui.media.mediaoutput.controller.device;

import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.media.AudioManager;
import android.util.Log;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.AudioDeviceExt;
import com.android.systemui.media.mediaoutput.entity.BluetoothDevice;
import com.android.systemui.media.mediaoutput.entity.BuiltInDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.android.systemui.media.mediaoutput.ext.CachedBluetoothDeviceExtKt;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.badge.AuracastKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class AuracastDeviceController extends DeviceController {
    public final AudioManager audioManager;
    public final LocalBluetoothManager localBluetoothManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public AuracastDeviceController(AudioManager audioManager, LocalBluetoothManager localBluetoothManager) {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast;
        this.audioManager = audioManager;
        this.localBluetoothManager = localBluetoothManager;
        Log.d("AuracastDeviceController", "init() - " + ((localBluetoothManager == null || (localBluetoothProfileManager = localBluetoothManager.mProfileManager) == null || (localBluetoothLeBroadcast = localBluetoothProfileManager.mLeAudioBroadcast) == null) ? null : Integer.valueOf(localBluetoothLeBroadcast.mBroadcastId)));
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("AuracastDeviceController", "close()");
    }

    public final void updateAuracast(List list, List list2) {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant;
        boolean z;
        AuracastDeviceController auracastDeviceController = this;
        LocalBluetoothManager localBluetoothManager = auracastDeviceController.localBluetoothManager;
        if (localBluetoothManager == null || (localBluetoothProfileManager = localBluetoothManager.mProfileManager) == null || (localBluetoothLeBroadcastAssistant = localBluetoothProfileManager.mLeAudioBroadcastAssistant) == null) {
            return;
        }
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = localBluetoothLeBroadcastAssistant.mService;
        int i = 0;
        Collection arrayList = bluetoothLeBroadcastAssistant == null ? new ArrayList(0) : bluetoothLeBroadcastAssistant.getDevicesMatchingConnectionStates(new int[]{2, 1, 3});
        arrayList.getClass();
        if (arrayList.isEmpty()) {
            arrayList = null;
        }
        if (arrayList == null) {
            if (!AudioManagerExtKt.isBroadcasting(auracastDeviceController.audioManager)) {
                auracastDeviceController = null;
            }
            if (auracastDeviceController != null) {
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : list) {
                    if (obj instanceof BuiltInDevice) {
                        arrayList2.add(obj);
                    }
                }
                ArrayList arrayList3 = new ArrayList();
                int size = arrayList2.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj2 = arrayList2.get(i2);
                    i2++;
                    AudioDeviceExt.INSTANCE.getClass();
                    if (AudioDeviceExt.isActive((BuiltInDevice) obj2)) {
                        arrayList3.add(obj2);
                    }
                }
                ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
                int size2 = arrayList3.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj3 = arrayList3.get(i3);
                    i3++;
                    BuiltInDevice builtInDevice = (BuiltInDevice) obj3;
                    Integer numValueOf = Integer.valueOf(list.indexOf(builtInDevice));
                    ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
                    Icons.Badge badge = Icons.Badge.INSTANCE;
                    ImageVector imageVector = (ImageVector) AuracastKt.Auracast$delegate.getValue();
                    companion.getClass();
                    BuiltInDevice builtInDeviceCopy$default = BuiltInDevice.copy$default(builtInDevice, null, ImageVectorConverterPainter.Companion.toConverter(imageVector), 0, State.CONNECTED, 367);
                    builtInDeviceCopy$default.deepCopy(builtInDevice);
                    arrayList4.add(new Pair(numValueOf, builtInDeviceCopy$default));
                }
                int size3 = arrayList4.size();
                int i4 = 0;
                while (i4 < size3) {
                    Object obj4 = arrayList4.get(i4);
                    i4++;
                    Pair pair = (Pair) obj4;
                    list.set(((Number) pair.component1()).intValue(), (BuiltInDevice) pair.component2());
                }
                ArrayList arrayList5 = new ArrayList();
                for (Object obj5 : list2) {
                    if (obj5 instanceof BluetoothDevice) {
                        arrayList5.add(obj5);
                    }
                }
                ArrayList arrayList6 = new ArrayList();
                int size4 = arrayList5.size();
                int i5 = 0;
                while (i5 < size4) {
                    Object obj6 = arrayList5.get(i5);
                    i5++;
                    AudioDeviceExt.INSTANCE.getClass();
                    if (AudioDeviceExt.isActive((BluetoothDevice) obj6)) {
                        arrayList6.add(obj6);
                    }
                }
                ArrayList arrayList7 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList6, 10));
                int size5 = arrayList6.size();
                int i6 = 0;
                while (i6 < size5) {
                    Object obj7 = arrayList6.get(i6);
                    i6++;
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) obj7;
                    Integer numValueOf2 = Integer.valueOf(((ArrayList) list2).indexOf(bluetoothDevice));
                    BluetoothDevice bluetoothDeviceCopy$default = BluetoothDevice.copy$default(bluetoothDevice, null, null, 0, State.CONNECTED, false, 1919);
                    bluetoothDeviceCopy$default.deepCopy(bluetoothDevice);
                    arrayList7.add(new Pair(numValueOf2, bluetoothDeviceCopy$default));
                }
                int size6 = arrayList7.size();
                while (i < size6) {
                    Object obj8 = arrayList7.get(i);
                    i++;
                    Pair pair2 = (Pair) obj8;
                    ((ArrayList) list2).set(((Number) pair2.component1()).intValue(), (BluetoothDevice) pair2.component2());
                }
                return;
            }
            return;
        }
        Collection collection = arrayList;
        ArrayList arrayList8 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(collection, 10));
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            arrayList8.add(((android.bluetooth.BluetoothDevice) it.next()).getAddress());
        }
        if (AudioManagerExtKt.isBroadcasting(auracastDeviceController.audioManager)) {
            ArrayList arrayList9 = new ArrayList();
            for (Object obj9 : list) {
                if (obj9 instanceof BuiltInDevice) {
                    arrayList9.add(obj9);
                }
            }
            ArrayList arrayList10 = new ArrayList();
            int size7 = arrayList9.size();
            int i7 = 0;
            while (i7 < size7) {
                Object obj10 = arrayList9.get(i7);
                i7++;
                AudioDeviceExt.INSTANCE.getClass();
                if (AudioDeviceExt.isActive((BuiltInDevice) obj10)) {
                    arrayList10.add(obj10);
                }
            }
            ArrayList arrayList11 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList10, 10));
            int size8 = arrayList10.size();
            int i8 = 0;
            while (i8 < size8) {
                Object obj11 = arrayList10.get(i8);
                i8++;
                BuiltInDevice builtInDevice2 = (BuiltInDevice) obj11;
                Integer numValueOf3 = Integer.valueOf(list.indexOf(builtInDevice2));
                BuiltInDevice builtInDeviceCopy$default2 = BuiltInDevice.copy$default(builtInDevice2, null, null, 0, State.CONNECTED, 383);
                builtInDeviceCopy$default2.deepCopy(builtInDevice2);
                arrayList11.add(new Pair(numValueOf3, builtInDeviceCopy$default2));
            }
            int size9 = arrayList11.size();
            int i9 = 0;
            while (i9 < size9) {
                Object obj12 = arrayList11.get(i9);
                i9++;
                Pair pair3 = (Pair) obj12;
                list.set(((Number) pair3.component1()).intValue(), (BuiltInDevice) pair3.component2());
            }
            ArrayList arrayList12 = new ArrayList();
            for (Object obj13 : list2) {
                if (arrayList8.contains(((AudioDevice) obj13).getId())) {
                    arrayList12.add(obj13);
                }
            }
            ArrayList arrayList13 = new ArrayList();
            int size10 = arrayList12.size();
            int i10 = 0;
            while (i10 < size10) {
                Object obj14 = arrayList12.get(i10);
                i10++;
                if (obj14 instanceof BluetoothDevice) {
                    arrayList13.add(obj14);
                }
            }
            ArrayList arrayList14 = new ArrayList();
            int size11 = arrayList13.size();
            int i11 = 0;
            while (i11 < size11) {
                Object obj15 = arrayList13.get(i11);
                i11++;
                CachedBluetoothDevice cachedBluetoothDevice = ((BluetoothDevice) obj15).cachedBluetoothDevice;
                if (cachedBluetoothDevice == null) {
                    cachedBluetoothDevice = null;
                }
                if (CachedBluetoothDeviceExtKt.isActiveDeviceWithMembers(cachedBluetoothDevice, 26)) {
                    arrayList14.add(obj15);
                }
            }
            ArrayList arrayList15 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList14, 10));
            int size12 = arrayList14.size();
            int i12 = 0;
            while (i12 < size12) {
                Object obj16 = arrayList14.get(i12);
                i12++;
                BluetoothDevice bluetoothDevice2 = (BluetoothDevice) obj16;
                Integer numValueOf4 = Integer.valueOf(((ArrayList) list2).indexOf(bluetoothDevice2));
                ImageVectorConverterPainter.Companion companion2 = ImageVectorConverterPainter.Companion;
                Icons.Badge badge2 = Icons.Badge.INSTANCE;
                ImageVector imageVector2 = (ImageVector) AuracastKt.Auracast$delegate.getValue();
                companion2.getClass();
                BluetoothDevice bluetoothDeviceCopy$default2 = BluetoothDevice.copy$default(bluetoothDevice2, null, ImageVectorConverterPainter.Companion.toConverter(imageVector2), 0, State.SELECTED, false, 1903);
                bluetoothDeviceCopy$default2.deepCopy(bluetoothDevice2);
                arrayList15.add(new Pair(numValueOf4, bluetoothDeviceCopy$default2));
            }
            int size13 = arrayList15.size();
            z = false;
            int i13 = 0;
            while (i13 < size13) {
                Object obj17 = arrayList15.get(i13);
                i13++;
                Pair pair4 = (Pair) obj17;
                ((ArrayList) list2).set(((Number) pair4.component1()).intValue(), (BluetoothDevice) pair4.component2());
                z = true;
            }
        } else {
            ArrayList arrayList16 = new ArrayList();
            for (Object obj18 : list2) {
                if (obj18 instanceof BluetoothDevice) {
                    arrayList16.add(obj18);
                }
            }
            ArrayList arrayList17 = new ArrayList();
            int size14 = arrayList16.size();
            int i14 = 0;
            while (i14 < size14) {
                Object obj19 = arrayList16.get(i14);
                i14++;
                AudioDeviceExt.INSTANCE.getClass();
                if (AudioDeviceExt.isActive((BluetoothDevice) obj19)) {
                    arrayList17.add(obj19);
                }
            }
            ArrayList arrayList18 = new ArrayList();
            int size15 = arrayList17.size();
            int i15 = 0;
            while (i15 < size15) {
                Object obj20 = arrayList17.get(i15);
                i15++;
                CachedBluetoothDevice cachedBluetoothDevice2 = ((BluetoothDevice) obj20).cachedBluetoothDevice;
                if (cachedBluetoothDevice2 == null) {
                    cachedBluetoothDevice2 = null;
                }
                List<BluetoothLeBroadcastReceiveState> allSources = localBluetoothLeBroadcastAssistant.getAllSources(cachedBluetoothDevice2.mDevice);
                if (!(allSources instanceof Collection) || !allSources.isEmpty()) {
                    for (BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState : allSources) {
                        bluetoothLeBroadcastReceiveState.getClass();
                        if (bluetoothLeBroadcastReceiveState.getPaSyncState() != 2) {
                            List<Long> bisSyncState = bluetoothLeBroadcastReceiveState.getBisSyncState();
                            if (!(bisSyncState instanceof Collection) || !bisSyncState.isEmpty()) {
                                for (Long l : bisSyncState) {
                                    if (l != null && l.longValue() == 0) {
                                    }
                                }
                            }
                        }
                        arrayList18.add(obj20);
                    }
                }
            }
            ArrayList arrayList19 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList18, 10));
            int size16 = arrayList18.size();
            int i16 = 0;
            while (i16 < size16) {
                Object obj21 = arrayList18.get(i16);
                i16++;
                BluetoothDevice bluetoothDevice3 = (BluetoothDevice) obj21;
                Integer numValueOf5 = Integer.valueOf(((ArrayList) list2).indexOf(bluetoothDevice3));
                ImageVectorConverterPainter.Companion companion3 = ImageVectorConverterPainter.Companion;
                Icons.Badge badge3 = Icons.Badge.INSTANCE;
                ImageVector imageVector3 = (ImageVector) AuracastKt.Auracast$delegate.getValue();
                companion3.getClass();
                BluetoothDevice bluetoothDeviceCopy$default3 = BluetoothDevice.copy$default(bluetoothDevice3, null, ImageVectorConverterPainter.Companion.toConverter(imageVector3), 0, State.SELECTED, false, 1903);
                bluetoothDeviceCopy$default3.deepCopy(bluetoothDevice3);
                arrayList19.add(new Pair(numValueOf5, bluetoothDeviceCopy$default3));
            }
            int size17 = arrayList19.size();
            z = false;
            int i17 = 0;
            while (i17 < size17) {
                Object obj22 = arrayList19.get(i17);
                i17++;
                Pair pair5 = (Pair) obj22;
                ((ArrayList) list2).set(((Number) pair5.component1()).intValue(), (BluetoothDevice) pair5.component2());
                z = true;
            }
        }
        List list3 = z ? list2 : null;
        if (list3 != null) {
            ArrayList arrayList20 = new ArrayList();
            for (Object obj23 : list3) {
                if (obj23 instanceof BluetoothDevice) {
                    arrayList20.add(obj23);
                }
            }
            ArrayList arrayList21 = new ArrayList();
            int size18 = arrayList20.size();
            int i18 = 0;
            while (i18 < size18) {
                Object obj24 = arrayList20.get(i18);
                i18++;
                if (((BluetoothDevice) obj24).selectable) {
                    arrayList21.add(obj24);
                }
            }
            ArrayList arrayList22 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList21, 10));
            int size19 = arrayList21.size();
            int i19 = 0;
            while (i19 < size19) {
                Object obj25 = arrayList21.get(i19);
                i19++;
                BluetoothDevice bluetoothDevice4 = (BluetoothDevice) obj25;
                Integer numValueOf6 = Integer.valueOf(((ArrayList) list2).indexOf(bluetoothDevice4));
                BluetoothDevice bluetoothDeviceCopy$default4 = BluetoothDevice.copy$default(bluetoothDevice4, null, null, 0, null, false, 1791);
                bluetoothDeviceCopy$default4.deepCopy(bluetoothDevice4);
                arrayList22.add(new Pair(numValueOf6, bluetoothDeviceCopy$default4));
            }
            int size20 = arrayList22.size();
            while (i < size20) {
                Object obj26 = arrayList22.get(i);
                i++;
                Pair pair6 = (Pair) obj26;
                ((ArrayList) list2).set(((Number) pair6.component1()).intValue(), (BluetoothDevice) pair6.component2());
            }
            Unit unit = Unit.INSTANCE;
        }
    }
}
