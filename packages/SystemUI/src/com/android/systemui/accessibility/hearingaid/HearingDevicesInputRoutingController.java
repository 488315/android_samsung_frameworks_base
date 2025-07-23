package com.android.systemui.accessibility.hearingaid;

import android.content.Context;
import android.media.AudioManager;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HearingAidAudioRoutingHelper;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class HearingDevicesInputRoutingController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final HearingAidAudioRoutingHelper audioRoutingHelper;
    public final CoroutineDispatcher backgroundDispatcher;
    public final ContextScope bgCoroutineScope;
    public CachedBluetoothDevice cachedDevice;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        HearingDevicesInputRoutingController create(Context context);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface InputRoutingControlAvailableCallback {
        void onResult(boolean z);
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00af A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$isInputRoutingControlAvailableInternal(com.android.systemui.accessibility.hearingaid.HearingDevicesInputRoutingController r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            Method dump skipped, instructions count: 240
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.hearingaid.HearingDevicesInputRoutingController.access$isInputRoutingControlAvailableInternal(com.android.systemui.accessibility.hearingaid.HearingDevicesInputRoutingController, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
