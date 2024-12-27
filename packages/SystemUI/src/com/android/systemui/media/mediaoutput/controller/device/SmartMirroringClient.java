package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Messenger;
import android.util.Log;
import com.samsung.android.knox.foresight.KnoxForesight;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractList.IteratorImpl;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public final class SmartMirroringClient {
    public final AudioManager audioManager;
    public final Flow connectionFlow = FlowKt.buffer$default(FlowKt.callbackFlow(new SmartMirroringClient$connectionFlow$1(this, null)), -1);
    public final Context context;
    public Messenger service;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class REASON {
        public static final /* synthetic */ EnumEntries $ENTRIES;
        public static final /* synthetic */ REASON[] $VALUES;
        public static final REASON UNKNOWN;
        private final int value;

        static {
            REASON reason = new REASON("UNKNOWN", 0, -1);
            UNKNOWN = reason;
            REASON[] reasonArr = {reason, new REASON(KnoxForesight.SUCCESS, 1, 0), new REASON("SCAN_P2P_BUSY", 2, 1), new REASON("SCAN_HDMI_CONNECTED", 3, 2), new REASON("SCAN_HOTSPOT_ON", 4, 3), new REASON("SCAN_WIFI_RESTRICTED", 5, 4), new REASON("SCAN_DEX_ENABLED", 6, 5), new REASON("SCAN_TV2MOBILE_POPUP_PLAYER", 7, 6), new REASON("SCAN_SETTING_RESTRICTED", 8, 8), new REASON("SCAN_P2P_CONNECTED", 9, 9), new REASON("SCAN_WIFIDISPLAY_SINK_CONNECTED", 10, 10), new REASON("SCAN_SECOND_SCREEN_CONNECTED", 11, 11), new REASON("SCAN_HIGH_TEMPERATURE", 12, 12), new REASON("SCAN_SECOND_SCREEN_ALREADY_RUNNING", 13, 13), new REASON("SCAN_AUDIO_MIRRORING_CONNECTED", 14, 14), new REASON("SCAN_SMART_VIEW_NONE_INITIATION", 15, 15)};
            $VALUES = reasonArr;
            $ENTRIES = EnumEntriesKt.enumEntries(reasonArr);
        }

        private REASON(String str, int i, int i2) {
            this.value = i2;
        }

        public static REASON valueOf(String str) {
            return (REASON) Enum.valueOf(REASON.class, str);
        }

        public static REASON[] values() {
            return (REASON[]) $VALUES.clone();
        }

        public final int getValue() {
            return this.value;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class RESULT {
        public static final /* synthetic */ EnumEntries $ENTRIES;
        public static final /* synthetic */ RESULT[] $VALUES;
        public static final RESULT UNKNOWN;
        private final int value;

        static {
            RESULT result = new RESULT("UNKNOWN", 0, -1);
            UNKNOWN = result;
            RESULT[] resultArr = {result, new RESULT(KnoxForesight.SUCCESS, 1, 0), new RESULT("FAIL", 2, 1)};
            $VALUES = resultArr;
            $ENTRIES = EnumEntriesKt.enumEntries(resultArr);
        }

        private RESULT(String str, int i, int i2) {
            this.value = i2;
        }

        public static RESULT valueOf(String str) {
            return (RESULT) Enum.valueOf(RESULT.class, str);
        }

        public static RESULT[] values() {
            return (RESULT[]) $VALUES.clone();
        }

        public final int getValue() {
            return this.value;
        }
    }

    public final class Response {
        public static final Companion Companion = new Companion(null);
        public final REASON reason;
        public final RESULT result;

        public final class Companion {
            private Companion() {
            }

            public static Response getParseResponse(Bundle bundle) {
                Object obj;
                Object obj2;
                AbstractList abstractList = (AbstractList) RESULT.$ENTRIES;
                abstractList.getClass();
                AbstractList.IteratorImpl iteratorImpl = abstractList.new IteratorImpl();
                while (true) {
                    obj = null;
                    if (!iteratorImpl.hasNext()) {
                        obj2 = null;
                        break;
                    }
                    obj2 = iteratorImpl.next();
                    if (((RESULT) obj2).getValue() == bundle.getInt("result", -1)) {
                        break;
                    }
                }
                RESULT result = (RESULT) obj2;
                if (result == null) {
                    result = RESULT.UNKNOWN;
                }
                AbstractList abstractList2 = (AbstractList) REASON.$ENTRIES;
                abstractList2.getClass();
                AbstractList.IteratorImpl iteratorImpl2 = abstractList2.new IteratorImpl();
                while (true) {
                    if (!iteratorImpl2.hasNext()) {
                        break;
                    }
                    Object next = iteratorImpl2.next();
                    if (((REASON) next).getValue() == bundle.getInt("reason", -1)) {
                        obj = next;
                        break;
                    }
                }
                REASON reason = (REASON) obj;
                if (reason == null) {
                    reason = REASON.UNKNOWN;
                }
                return new Response(result, reason);
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public Response(RESULT result, REASON reason) {
            this.result = result;
            this.reason = reason;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Response)) {
                return false;
            }
            Response response = (Response) obj;
            return this.result == response.result && this.reason == response.reason;
        }

        public final int hashCode() {
            return this.reason.hashCode() + (this.result.hashCode() * 31);
        }

        public final String toString() {
            return "Response(result=" + this.result + ", reason=" + this.reason + ")";
        }
    }

    static {
        new Companion(null);
    }

    public SmartMirroringClient(Context context, AudioManager audioManager) {
        this.context = context;
        this.audioManager = audioManager;
        Log.d("SmartMirroringClient", "init()");
    }
}
