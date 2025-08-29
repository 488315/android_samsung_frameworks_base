package com.android.systemui.media.mediaoutput.controller.device;

import android.bluetooth.BluetoothA2dp;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.common.DeviceUtils;
import com.android.systemui.media.mediaoutput.common.MediaOutputConst;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.BuiltInDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.device.DockKt;
import com.android.systemui.media.mediaoutput.icons.device.HdmiKt;
import com.android.systemui.media.mediaoutput.icons.device.HearingAidsKt;
import com.android.systemui.media.mediaoutput.icons.device.LevelUKt;
import com.android.systemui.media.mediaoutput.icons.device.LineKt;
import com.android.systemui.media.mediaoutput.icons.device.MobileDeviceKt;
import com.android.systemui.media.mediaoutput.icons.device.SoundAccessoryKt;
import com.android.systemui.media.mediaoutput.icons.device.TabletKt;
import com.android.systemui.media.mediaoutput.icons.device.UsbKt;
import com.android.systemui.util.DeviceType;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.media.SemSoundAssistantManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.Lazy;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes2.dex */
public final class BuiltInDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final Context context;
    public final LocalBluetoothManager localBluetoothManager;
    public StandaloneCoroutine updateJob;

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BuiltInDeviceController.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Companion companion = BuiltInDeviceController.Companion;
                BuiltInDeviceController builtInDeviceController = BuiltInDeviceController.this;
                Pair pair = new Pair(builtInDeviceController.context, builtInDeviceController.audioManager);
                companion.getClass();
                Flow flowBuffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new BuiltInDeviceController$Companion$deviceStateChanges$1(pair, null)), -1, 2);
                MediaOutputConst.INSTANCE.getClass();
                Flow flowM3481debounceHG0u8IE = FlowKt.m3481debounceHG0u8IE(flowBuffer$default, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                final BuiltInDeviceController builtInDeviceController2 = BuiltInDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) throws IOException {
                        Companion companion2 = BuiltInDeviceController.Companion;
                        Object objUpdateDevices$1 = builtInDeviceController2.updateDevices$1((List) obj2, false, continuation);
                        return objUpdateDevices$1 == CoroutineSingletons.COROUTINE_SUSPENDED ? objUpdateDevices$1 : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowM3481debounceHG0u8IE.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public BuiltInDeviceController(Context context, AudioManager audioManager, LocalBluetoothManager localBluetoothManager) {
        this.context = context;
        this.audioManager = audioManager;
        this.localBluetoothManager = localBluetoothManager;
        Lazy lazy = AudioManagerExtKt.mediaStrategy$delegate;
        Log.d("BuiltInDeviceController", "init() - currentDeviceType = " + audioManager.semGetCurrentDeviceType());
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit adjustVolume(AudioDevice audioDevice, int i) {
        Log.d("BuiltInDeviceController", "adjustVolume() - " + audioDevice + " - " + i);
        if (audioDevice instanceof BuiltInDevice) {
            this.audioManager.semSetFineVolume(3, i, 0);
            DeviceUtils deviceUtils = DeviceUtils.INSTANCE;
            ContentResolver contentResolver = this.context.getContentResolver();
            boolean needEarProtect = ((BuiltInDevice) audioDevice).getNeedEarProtect();
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    BuiltInDeviceController builtInDeviceController = this.f$0;
                    StandaloneCoroutine standaloneCoroutine = builtInDeviceController.updateJob;
                    if (standaloneCoroutine != null) {
                        standaloneCoroutine.cancel(null);
                    }
                    builtInDeviceController.updateJob = BuildersKt.launch$default(builtInDeviceController.getControllerScope(), null, null, new BuiltInDeviceController$adjustVolume$2$1(builtInDeviceController, null), 3);
                    return Unit.INSTANCE;
                }
            };
            deviceUtils.getClass();
            DeviceUtils.checkVolumeLimiter(contentResolver, needEarProtect, i, function0);
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("BuiltInDeviceController", "close()");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Object transfer(AudioDevice audioDevice, Continuation continuation) {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        A2dpProfile a2dpProfile;
        BluetoothA2dp bluetoothA2dp;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "BuiltInDeviceController");
        if (audioDevice instanceof BuiltInDevice) {
            AudioManager audioManager = this.audioManager;
            AudioDeviceInfo audioDeviceInfo = ((BuiltInDevice) audioDevice).audioDeviceInfo;
            if (audioDeviceInfo == null) {
                audioDeviceInfo = null;
            }
            AudioManagerExtKt.setDeviceForced(audioManager, audioDeviceInfo);
            LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
            if (localBluetoothManager != null && (localBluetoothProfileManager = localBluetoothManager.mProfileManager) != null && (a2dpProfile = localBluetoothProfileManager.mA2dpProfile) != null && (bluetoothA2dp = a2dpProfile.mService) != null) {
                bluetoothA2dp.setDualPlayMode(false);
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:126:0x045f  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x04af  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x04b4  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x04cd  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x04fa  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x050f  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0512  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x052d  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0464 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0437 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object updateDevices$1(List list, boolean z, Continuation continuation) throws IOException {
        BuiltInDeviceController$updateDevices$1 builtInDeviceController$updateDevices$1;
        ArrayList arrayList;
        AudioDeviceInfo audioDeviceInfo;
        ResourceString resourceString;
        AudioDeviceInfo audioDeviceInfo2;
        AudioManager audioManager;
        int type;
        BuiltInDevice builtInDeviceCopy$default;
        String strJoinToString$default;
        ResourceString resourceString2;
        Object failure;
        ApplicationInfo applicationInfo;
        BuiltInDevice builtInDevice;
        BuiltInDevice builtInDevice2;
        BuiltInDevice builtInDevice3;
        ImageVector imageVector;
        int i = 1;
        if (continuation instanceof BuiltInDeviceController$updateDevices$1) {
            builtInDeviceController$updateDevices$1 = (BuiltInDeviceController$updateDevices$1) continuation;
            int i2 = builtInDeviceController$updateDevices$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                builtInDeviceController$updateDevices$1.label = i2 - Integer.MIN_VALUE;
            } else {
                builtInDeviceController$updateDevices$1 = new BuiltInDeviceController$updateDevices$1(this, continuation);
            }
        }
        Object obj = builtInDeviceController$updateDevices$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = builtInDeviceController$updateDevices$1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            boolean zIsWiredHeadsetOn = this.audioManager.isWiredHeadsetOn();
            List<AudioDeviceInfo> list2 = list;
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : list2) {
                if (((AudioDeviceInfo) obj2).getType() == 8) {
                    arrayList2.add(obj2);
                }
            }
            ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
            int size = arrayList2.size();
            int i4 = 0;
            while (i4 < size) {
                Object obj3 = arrayList2.get(i4);
                i4++;
                arrayList3.add(((AudioDeviceInfo) obj3).getAddress());
            }
            for (AudioDeviceInfo audioDeviceInfo3 : list2) {
                AudioDeviceInfoExt.INSTANCE.getClass();
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("\t", AudioDeviceInfoExt.toLogText(audioDeviceInfo3), "BuiltInDeviceController");
            }
            ArrayList arrayList4 = new ArrayList();
            for (Object obj4 : list2) {
                AudioDeviceInfoExt.INSTANCE.getClass();
                if (AudioDeviceInfoExt.isValidDeviceTypeForMedia((AudioDeviceInfo) obj4, zIsWiredHeadsetOn)) {
                    arrayList4.add(obj4);
                }
            }
            ArrayList arrayList5 = new ArrayList();
            int size2 = arrayList4.size();
            int i5 = 0;
            while (i5 < size2) {
                Object obj5 = arrayList4.get(i5);
                i5++;
                AudioDeviceInfo audioDeviceInfo4 = (AudioDeviceInfo) obj5;
                if (arrayList2.contains(audioDeviceInfo4) || !arrayList3.contains(audioDeviceInfo4.getAddress())) {
                    arrayList5.add(obj5);
                }
            }
            ArrayList arrayList6 = new ArrayList();
            int size3 = arrayList5.size();
            int i6 = 0;
            while (true) {
                AudioDeviceInfo audioDeviceInfo5 = null;
                if (i6 < size3) {
                    Object obj6 = arrayList5.get(i6);
                    i6 += i;
                    AudioDeviceInfo audioDeviceInfo6 = (AudioDeviceInfo) obj6;
                    BuiltInDevice.Companion.getClass();
                    int type2 = audioDeviceInfo6.getType();
                    int i7 = i;
                    if (type2 != 19) {
                        int i8 = R.string.headset_media;
                        if (type2 == 22) {
                            String strM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(audioDeviceInfo6.getId(), "BuiltIn_");
                            ResourceString resourceString3 = new ResourceString(R.string.headset_media, null, 2, null);
                            ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
                            Icons.Device device = Icons.Device.INSTANCE;
                            ImageVector imageVector2 = (ImageVector) LevelUKt.LevelU$delegate.getValue();
                            companion.getClass();
                            builtInDevice3 = new BuiltInDevice(strM, resourceString3, null, ImageVectorConverterPainter.Companion.toConverter(imageVector2), null, 0, 0, null, z, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode, null);
                            builtInDevice2 = builtInDevice3;
                        } else if (type2 != 23) {
                            if (type2 != 26 && type2 != 27) {
                                switch (type2) {
                                    case 1:
                                    case 2:
                                        String strM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(audioDeviceInfo6.getId(), "BuiltIn_");
                                        ResourceString resourceString4 = new ResourceString(R.string.phone_speaker, null, 2, null);
                                        ImageVectorConverterPainter.Companion companion2 = ImageVectorConverterPainter.Companion;
                                        if (DeviceType.isTablet()) {
                                            Icons.Device device2 = Icons.Device.INSTANCE;
                                            imageVector = (ImageVector) TabletKt.Tablet$delegate.getValue();
                                        } else {
                                            Icons.Device device3 = Icons.Device.INSTANCE;
                                            imageVector = (ImageVector) MobileDeviceKt.MobileDevice$delegate.getValue();
                                        }
                                        companion2.getClass();
                                        builtInDevice3 = new BuiltInDevice(strM2, resourceString4, null, ImageVectorConverterPainter.Companion.toConverter(imageVector), null, 0, 0, null, false, 500, null);
                                        break;
                                    case 3:
                                    case 4:
                                        String strM3 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(audioDeviceInfo6.getId(), "BuiltIn_");
                                        if (audioDeviceInfo6.getType() != 3) {
                                            i8 = R.string.headphones;
                                        }
                                        ResourceString resourceString5 = new ResourceString(i8, null, 2, null);
                                        ImageVectorConverterPainter.Companion companion3 = ImageVectorConverterPainter.Companion;
                                        Icons.Device device4 = Icons.Device.INSTANCE;
                                        ImageVector imageVector3 = (ImageVector) LevelUKt.LevelU$delegate.getValue();
                                        companion3.getClass();
                                        builtInDevice2 = new BuiltInDevice(strM3, resourceString5, null, ImageVectorConverterPainter.Companion.toConverter(imageVector3), null, 0, 0, null, z, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode, null);
                                        break;
                                    case 5:
                                    case 6:
                                        String strM4 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(audioDeviceInfo6.getId(), "BuiltIn_");
                                        String string = audioDeviceInfo6.getProductName().toString();
                                        ImageVectorConverterPainter.Companion companion4 = ImageVectorConverterPainter.Companion;
                                        Icons.Device device5 = Icons.Device.INSTANCE;
                                        ImageVector imageVector4 = (ImageVector) LineKt.Line$delegate.getValue();
                                        companion4.getClass();
                                        builtInDevice = new BuiltInDevice(strM4, string, null, ImageVectorConverterPainter.Companion.toConverter(imageVector4), null, 0, 0, null, false, 500, null);
                                        builtInDevice2 = builtInDevice;
                                        break;
                                    default:
                                        switch (type2) {
                                            case 8:
                                                String address = audioDeviceInfo6.getAddress();
                                                String string2 = audioDeviceInfo6.getProductName().toString();
                                                ImageVectorConverterPainter.Companion companion5 = ImageVectorConverterPainter.Companion;
                                                Icons.Device device6 = Icons.Device.INSTANCE;
                                                ImageVector imageVector5 = (ImageVector) SoundAccessoryKt.SoundAccessory$delegate.getValue();
                                                companion5.getClass();
                                                builtInDevice2 = new BuiltInDevice(address, string2, null, ImageVectorConverterPainter.Companion.toConverter(imageVector5), null, 0, 0, null, false, 500, null);
                                                break;
                                            case 9:
                                            case 10:
                                                String strM5 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(audioDeviceInfo6.getId(), "BuiltIn_");
                                                ResourceString resourceString6 = new ResourceString(R.string.hdmi_mhl_device, null, 2, null);
                                                ImageVectorConverterPainter.Companion companion6 = ImageVectorConverterPainter.Companion;
                                                Icons.Device device7 = Icons.Device.INSTANCE;
                                                ImageVector imageVector6 = (ImageVector) HdmiKt.Hdmi$delegate.getValue();
                                                companion6.getClass();
                                                builtInDevice2 = new BuiltInDevice(strM5, resourceString6, null, ImageVectorConverterPainter.Companion.toConverter(imageVector6), null, 0, 0, null, false, 500, null);
                                                break;
                                            case 11:
                                                break;
                                            case 12:
                                                String strM6 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(audioDeviceInfo6.getId(), "BuiltIn_");
                                                String string3 = audioDeviceInfo6.getProductName().toString();
                                                ImageVectorConverterPainter.Companion companion7 = ImageVectorConverterPainter.Companion;
                                                Icons.Device device8 = Icons.Device.INSTANCE;
                                                ImageVector imageVector7 = (ImageVector) UsbKt.Usb$delegate.getValue();
                                                companion7.getClass();
                                                builtInDevice2 = new BuiltInDevice(strM6, string3, null, ImageVectorConverterPainter.Companion.toConverter(imageVector7), null, 0, 0, null, false, 500, null);
                                                break;
                                            case 13:
                                                String strM7 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(audioDeviceInfo6.getId(), "BuiltIn_");
                                                ResourceString resourceString7 = new ResourceString(R.string.hdmi_mhl_device, null, 2, null);
                                                ImageVectorConverterPainter.Companion companion8 = ImageVectorConverterPainter.Companion;
                                                Icons.Device device9 = Icons.Device.INSTANCE;
                                                ImageVector imageVector8 = (ImageVector) DockKt.Dock$delegate.getValue();
                                                companion8.getClass();
                                                builtInDevice2 = new BuiltInDevice(strM7, resourceString7, null, ImageVectorConverterPainter.Companion.toConverter(imageVector8), null, 0, 0, null, false, 500, null);
                                                break;
                                            default:
                                                builtInDevice2 = 0;
                                                break;
                                        }
                                }
                            } else {
                                String address2 = audioDeviceInfo6.getAddress();
                                ResourceString resourceString8 = new ResourceString(R.string.headset_media, null, 2, null);
                                ImageVectorConverterPainter.Companion companion9 = ImageVectorConverterPainter.Companion;
                                Icons.Device device10 = Icons.Device.INSTANCE;
                                ImageVector imageVector9 = (ImageVector) LevelUKt.LevelU$delegate.getValue();
                                companion9.getClass();
                                builtInDevice3 = new BuiltInDevice(address2, resourceString8, null, ImageVectorConverterPainter.Companion.toConverter(imageVector9), null, 0, 0, null, z, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode, null);
                            }
                            builtInDevice2 = builtInDevice3;
                        } else {
                            String strM8 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(audioDeviceInfo6.getId(), "BuiltIn_");
                            String string4 = audioDeviceInfo6.getProductName().toString();
                            ImageVectorConverterPainter.Companion companion10 = ImageVectorConverterPainter.Companion;
                            Icons.Device device11 = Icons.Device.INSTANCE;
                            ImageVector imageVector10 = (ImageVector) HearingAidsKt.HearingAids$delegate.getValue();
                            companion10.getClass();
                            builtInDevice = new BuiltInDevice(strM8, string4, null, ImageVectorConverterPainter.Companion.toConverter(imageVector10), null, 0, 0, null, false, 500, null);
                            builtInDevice2 = builtInDevice;
                        }
                    }
                    if (builtInDevice2 != 0) {
                        builtInDevice2.audioDeviceInfo = audioDeviceInfo6;
                        audioDeviceInfo5 = builtInDevice2;
                    }
                    if (audioDeviceInfo5 != null) {
                        arrayList6.add(audioDeviceInfo5);
                    }
                    i = i7;
                } else {
                    int i9 = i;
                    ArrayList arrayList7 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList6, 10));
                    int size4 = arrayList6.size();
                    int i10 = 0;
                    while (i10 < size4) {
                        int i11 = i10 + 1;
                        BuiltInDevice builtInDevice4 = (BuiltInDevice) arrayList6.get(i10);
                        AudioDeviceInfoExt audioDeviceInfoExt = AudioDeviceInfoExt.INSTANCE;
                        AudioDeviceInfo audioDeviceInfo7 = builtInDevice4.audioDeviceInfo;
                        if (audioDeviceInfo7 == null) {
                            audioDeviceInfo7 = audioDeviceInfo5;
                        }
                        ContentResolver contentResolver = this.context.getContentResolver();
                        Context context = this.context;
                        AudioManager audioManager2 = this.audioManager;
                        audioDeviceInfoExt.getClass();
                        DeviceUtils.INSTANCE.getClass();
                        if ((new SemSoundAssistantManager(context).isMultiSoundOn() ? audioDeviceInfo7 : audioDeviceInfo5) != null) {
                            int i12 = Settings.System.getInt(contentResolver, "multisound_devicetype", -1);
                            if ((i12 != 0 ? i12 != i9 ? -1 : 8 : 2) == audioDeviceInfo7.getType()) {
                                String string5 = Settings.System.getString(contentResolver, "multisound_app");
                                if (string5 == null) {
                                    arrayList = arrayList6;
                                    strJoinToString$default = null;
                                    if (strJoinToString$default == null) {
                                        resourceString2 = null;
                                        audioDeviceInfo = null;
                                        resourceString = resourceString2;
                                    } else {
                                        if (strJoinToString$default.length() <= 0) {
                                            strJoinToString$default = null;
                                        }
                                        if (strJoinToString$default != null) {
                                            resourceString2 = new ResourceString(R.string.ps_selected, Collections.singletonList(strJoinToString$default));
                                        }
                                        audioDeviceInfo = null;
                                        resourceString = resourceString2;
                                    }
                                } else {
                                    if (string5.length() <= 0) {
                                        string5 = null;
                                    }
                                    if (string5 != null) {
                                        List<String> listSplit$default = StringsKt__StringsKt.split$default(string5, new String[]{":"}, 0, 6);
                                        ArrayList arrayList8 = new ArrayList();
                                        for (String str : listSplit$default) {
                                            try {
                                                int i13 = Result.$r8$clinit;
                                            } catch (Throwable th) {
                                                th = th;
                                            }
                                            try {
                                                failure = context.getPackageManager().getApplicationInfo(str, 0);
                                            } catch (Throwable th2) {
                                                th = th2;
                                                int i14 = Result.$r8$clinit;
                                                failure = new Result.Failure(th);
                                                if (failure instanceof Result.Failure) {
                                                }
                                                applicationInfo = (ApplicationInfo) failure;
                                                if (applicationInfo == null) {
                                                }
                                            }
                                            if (failure instanceof Result.Failure) {
                                                failure = null;
                                            }
                                            applicationInfo = (ApplicationInfo) failure;
                                            if (applicationInfo == null) {
                                                arrayList8.add(applicationInfo);
                                            }
                                        }
                                        ArrayList arrayList9 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList8, 10));
                                        int size5 = arrayList8.size();
                                        int i15 = 0;
                                        while (i15 < size5) {
                                            Object obj7 = arrayList8.get(i15);
                                            i15++;
                                            arrayList9.add(((ApplicationInfo) obj7).loadLabel(context.getPackageManager()).toString());
                                            arrayList6 = arrayList6;
                                        }
                                        arrayList = arrayList6;
                                        strJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(arrayList9, null, null, null, null, 63);
                                    }
                                    if (strJoinToString$default == null) {
                                    }
                                }
                            } else {
                                arrayList = arrayList6;
                                int type3 = audioDeviceInfo7.getType();
                                Lazy lazy = AudioManagerExtKt.mediaStrategy$delegate;
                                if (audioManager2.semGetCurrentDeviceType() != type3) {
                                    audioDeviceInfo7 = null;
                                }
                                if (audioDeviceInfo7 != null) {
                                    audioDeviceInfo = null;
                                    resourceString = new ResourceString(R.string.main_audio_output, null, 2, null);
                                } else {
                                    audioDeviceInfo = null;
                                }
                            }
                            builtInDevice4.multiSoundInfo = resourceString;
                            audioDeviceInfo2 = builtInDevice4.audioDeviceInfo;
                            if (audioDeviceInfo2 == null) {
                                audioDeviceInfo2 = audioDeviceInfo;
                            }
                            AudioDeviceInfoExt audioDeviceInfoExt2 = AudioDeviceInfoExt.INSTANCE;
                            audioManager = this.audioManager;
                            audioDeviceInfoExt2.getClass();
                            type = audioDeviceInfo2.getType();
                            Lazy lazy2 = AudioManagerExtKt.mediaStrategy$delegate;
                            if (audioManager.semGetCurrentDeviceType() != type) {
                                audioDeviceInfo2 = audioDeviceInfo;
                            }
                            if (audioDeviceInfo2 == null) {
                                builtInDeviceCopy$default = BuiltInDevice.copy$default(builtInDevice4, builtInDevice4.multiSoundInfo, null, this.audioManager.semGetFineVolume(3), State.SELECTED, 347);
                                builtInDeviceCopy$default.deepCopy(builtInDevice4);
                            } else {
                                builtInDeviceCopy$default = BuiltInDevice.copy$default(builtInDevice4, builtInDevice4.multiSoundInfo, null, 0, null, 507);
                                builtInDeviceCopy$default.deepCopy(builtInDevice4);
                            }
                            arrayList7.add(builtInDeviceCopy$default);
                            i10 = i11;
                            audioDeviceInfo5 = audioDeviceInfo;
                            arrayList6 = arrayList;
                            i9 = 1;
                        } else {
                            arrayList = arrayList6;
                            audioDeviceInfo = audioDeviceInfo5;
                        }
                        resourceString = audioDeviceInfo;
                        builtInDevice4.multiSoundInfo = resourceString;
                        audioDeviceInfo2 = builtInDevice4.audioDeviceInfo;
                        if (audioDeviceInfo2 == null) {
                        }
                        AudioDeviceInfoExt audioDeviceInfoExt22 = AudioDeviceInfoExt.INSTANCE;
                        audioManager = this.audioManager;
                        audioDeviceInfoExt22.getClass();
                        type = audioDeviceInfo2.getType();
                        Lazy lazy22 = AudioManagerExtKt.mediaStrategy$delegate;
                        if (audioManager.semGetCurrentDeviceType() != type) {
                        }
                        if (audioDeviceInfo2 == null) {
                        }
                        arrayList7.add(builtInDeviceCopy$default);
                        i10 = i11;
                        audioDeviceInfo5 = audioDeviceInfo;
                        arrayList6 = arrayList;
                        i9 = 1;
                    }
                    int size6 = arrayList7.size();
                    int i16 = 0;
                    while (i16 < size6) {
                        Object obj8 = arrayList7.get(i16);
                        i16++;
                        Log.d("BuiltInDeviceController", "\t" + ((BuiltInDevice) obj8));
                    }
                    SharedFlowImpl sharedFlowImpl = this.devicesFlow;
                    builtInDeviceController$updateDevices$1.L$0 = arrayList7;
                    builtInDeviceController$updateDevices$1.label = 1;
                    if (sharedFlowImpl.emit(arrayList7, builtInDeviceController$updateDevices$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
