package com.android.systemui.accessibility.hearingaid;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Log;
import androidx.collection.ArraySet;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HapClientProfile;
import com.android.settingslib.bluetooth.HearingAidAudioRoutingHelper;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.internal.ContextScope;

/* loaded from: classes.dex */
public class HearingDevicesInputRoutingController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final HearingAidAudioRoutingHelper audioRoutingHelper;
    public final CoroutineDispatcher backgroundDispatcher;
    public final ContextScope bgCoroutineScope;
    public CachedBluetoothDevice cachedDevice;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        HearingDevicesInputRoutingController create(Context context);
    }

    public interface InputRoutingControlAvailableCallback {
        void onResult(boolean z);
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class InputRoutingValue {
        public static final /* synthetic */ InputRoutingValue[] $VALUES;
        public static final InputRoutingValue BUILTIN_MIC;
        public static final InputRoutingValue HEARING_DEVICE;

        static {
            InputRoutingValue inputRoutingValue = new InputRoutingValue("HEARING_DEVICE", 0);
            HEARING_DEVICE = inputRoutingValue;
            InputRoutingValue inputRoutingValue2 = new InputRoutingValue("BUILTIN_MIC", 1);
            BUILTIN_MIC = inputRoutingValue2;
            InputRoutingValue[] inputRoutingValueArr = {inputRoutingValue, inputRoutingValue2};
            $VALUES = inputRoutingValueArr;
            EnumEntriesKt.enumEntries(inputRoutingValueArr);
        }

        private InputRoutingValue(String str, int i) {
        }

        public static InputRoutingValue valueOf(String str) {
            return (InputRoutingValue) Enum.valueOf(InputRoutingValue.class, str);
        }

        public static InputRoutingValue[] values() {
            return (InputRoutingValue[]) $VALUES.clone();
        }
    }

    public HearingDevicesInputRoutingController(Context context, AudioManager audioManager, CoroutineDispatcher coroutineDispatcher) {
        this.audioManager = audioManager;
        this.backgroundDispatcher = coroutineDispatcher;
        this.audioRoutingHelper = new HearingAidAudioRoutingHelper(context);
        this.bgCoroutineScope = CoroutineScopeKt.CoroutineScope(coroutineDispatcher);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$isInputRoutingControlAvailableInternal(HearingDevicesInputRoutingController hearingDevicesInputRoutingController, ContinuationImpl continuationImpl) throws Throwable {
        HearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1 hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1;
        CachedBluetoothDevice cachedBluetoothDevice;
        Set set;
        boolean z;
        boolean z2;
        hearingDevicesInputRoutingController.getClass();
        if (continuationImpl instanceof HearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1) {
            hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1 = (HearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1) continuationImpl;
            int i = hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1.label = i - Integer.MIN_VALUE;
            } else {
                hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1 = new HearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1(hearingDevicesInputRoutingController, continuationImpl);
            }
        }
        Object obj = hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            CachedBluetoothDevice cachedBluetoothDevice2 = hearingDevicesInputRoutingController.cachedDevice;
            if (cachedBluetoothDevice2 == null) {
                return Boolean.FALSE;
            }
            Set set2 = cachedBluetoothDevice2.mMemberDevices;
            HearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$inputInfos$1 hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$inputInfos$1 = new HearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$inputInfos$1(hearingDevicesInputRoutingController, null);
            hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1.L$0 = cachedBluetoothDevice2;
            hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1.L$1 = set2;
            hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1.label = 1;
            Object objWithContext = BuildersKt.withContext(hearingDevicesInputRoutingController.backgroundDispatcher, hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$inputInfos$1, hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
            cachedBluetoothDevice = cachedBluetoothDevice2;
            obj = objWithContext;
            set = set2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            set = (Set) hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1.L$1;
            cachedBluetoothDevice = (CachedBluetoothDevice) hearingDevicesInputRoutingController$isInputRoutingControlAvailableInternal$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        AudioDeviceInfo[] audioDeviceInfoArr = (AudioDeviceInfo[]) obj;
        ArraySet arraySet = new ArraySet(0, 1, null);
        arraySet.add(cachedBluetoothDevice.mDevice.getAddress());
        set.getClass();
        if (!set.isEmpty()) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                arraySet.add(((CachedBluetoothDevice) it.next()).mDevice.getAddress());
            }
        }
        audioDeviceInfoArr.getClass();
        int length = audioDeviceInfoArr.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                z = false;
                break;
            }
            if (arraySet.contains(audioDeviceInfoArr[i3].getAddress())) {
                z = true;
                break;
            }
            i3++;
        }
        List profiles = cachedBluetoothDevice.getProfiles();
        if ((profiles instanceof Collection) && profiles.isEmpty()) {
            z2 = false;
        } else {
            Iterator it2 = profiles.iterator();
            while (it2.hasNext()) {
                if (((LocalBluetoothProfile) it2.next()) instanceof HapClientProfile) {
                    z2 = true;
                    break;
                }
            }
            z2 = false;
        }
        if (z2 && !z) {
            Log.d("HearingDevicesInputRoutingController", "Not supported input type hearing device.");
        }
        return Boolean.valueOf(z2 && z);
    }
}
