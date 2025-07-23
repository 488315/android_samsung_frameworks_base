package com.android.internal.os;

import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.samsung.android.share.SemShareConstants;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes5.dex */
public class DebugStore {
    private static final boolean DEBUG_EVENTS = false;
    private static final String TAG = "DebugStore";
    private static DebugStoreNative sDebugStoreNative = new DebugStoreNativeImpl();

    public interface DebugStoreNative {
        long beginEvent(String str, List<String> list);

        void endEvent(long j, List<String> list);

        void recordEvent(String str, List<String> list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native long beginEventNative(String str, List<String> list);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void endEventNative(long j, List<String> list);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void recordEventNative(String str, List<String> list);

    public static void setDebugStoreNative(DebugStoreNative debugStoreNative) {
        sDebugStoreNative = debugStoreNative;
    }

    public static long recordServiceOnStart(int i, int i2, Intent intent) {
        return sDebugStoreNative.beginEvent("SvcStart", List.of("stId", String.valueOf(i), "flg", Integer.toHexString(i2), SemShareConstants.DMA_SURVEY_KEY_RESOLVER_ACTION, Objects.toString(intent != null ? intent.getAction() : null), "comp", Objects.toString(intent != null ? intent.getComponent() : null), "pkg", Objects.toString(intent != null ? intent.getPackage() : null)));
    }

    public static long recordServiceCreate(ServiceInfo serviceInfo) {
        return sDebugStoreNative.beginEvent("SvcCreate", List.of("name", Objects.toString(serviceInfo != null ? serviceInfo.name : null), "pkg", Objects.toString(serviceInfo != null ? serviceInfo.packageName : null)));
    }

    public static long recordServiceBind(boolean z, Intent intent) {
        return sDebugStoreNative.beginEvent("SvcBind", List.of("rebind", String.valueOf(z), SemShareConstants.DMA_SURVEY_KEY_RESOLVER_ACTION, Objects.toString(intent != null ? intent.getAction() : null), "cmp", Objects.toString(intent != null ? intent.getComponent() : null), "pkg", Objects.toString(intent != null ? intent.getPackage() : null)));
    }

    public static void recordGoAsync(int i) {
        sDebugStoreNative.recordEvent("GoAsync", List.of("tname", Thread.currentThread().getName(), "tid", String.valueOf(Thread.currentThread().getId()), "prid", Integer.toHexString(i)));
    }

    public static void recordFinish(int i) {
        sDebugStoreNative.recordEvent("Finish", List.of("tname", Thread.currentThread().getName(), "tid", String.valueOf(Thread.currentThread().getId()), "prid", Integer.toHexString(i)));
    }

    public static void recordLongLooperMessage(int i, String str, long j) {
        sDebugStoreNative.recordEvent("LooperMsg", List.of("code", String.valueOf(i), "trgt", Objects.toString(str), "elapsed", String.valueOf(j)));
    }

    public static long recordBroadcastReceive(Intent intent, int i) {
        return sDebugStoreNative.beginEvent("BcRcv", List.of((Object[]) new String[]{"tname", Thread.currentThread().getName(), "tid", String.valueOf(Thread.currentThread().getId()), SemShareConstants.DMA_SURVEY_KEY_RESOLVER_ACTION, Objects.toString(intent != null ? intent.getAction() : null), "cmp", Objects.toString(intent != null ? intent.getComponent() : null), "pkg", Objects.toString(intent != null ? intent.getPackage() : null), "prid", Integer.toHexString(i)}));
    }

    public static long recordBroadcastReceiveReg(Intent intent, int i) {
        return sDebugStoreNative.beginEvent("BcRcvReg", List.of((Object[]) new String[]{"tname", Thread.currentThread().getName(), "tid", String.valueOf(Thread.currentThread().getId()), SemShareConstants.DMA_SURVEY_KEY_RESOLVER_ACTION, Objects.toString(intent != null ? intent.getAction() : null), "cmp", Objects.toString(intent != null ? intent.getComponent() : null), "pkg", Objects.toString(intent != null ? intent.getPackage() : null), "prid", Integer.toHexString(i)}));
    }

    public static long recordHandleBindApplication() {
        return sDebugStoreNative.beginEvent("BindApp", Collections.EMPTY_LIST);
    }

    public static long recordScheduleReceiver() {
        return sDebugStoreNative.beginEvent("SchRcv", List.of("tname", Thread.currentThread().getName(), "tid", String.valueOf(Thread.currentThread().getId())));
    }

    public static long recordScheduleRegisteredReceiver() {
        return sDebugStoreNative.beginEvent("SchRcvReg", List.of("tname", Thread.currentThread().getName(), "tid", String.valueOf(Thread.currentThread().getId())));
    }

    public static void recordEventEnd(long j) {
        sDebugStoreNative.endEvent(j, Collections.EMPTY_LIST);
    }

    private static class DebugStoreNativeImpl implements DebugStoreNative {
        private DebugStoreNativeImpl() {
        }

        @Override // com.android.internal.os.DebugStore.DebugStoreNative
        public long beginEvent(String str, List<String> list) {
            return DebugStore.beginEventNative(str, list);
        }

        @Override // com.android.internal.os.DebugStore.DebugStoreNative
        public void endEvent(long j, List<String> list) {
            DebugStore.endEventNative(j, list);
        }

        @Override // com.android.internal.os.DebugStore.DebugStoreNative
        public void recordEvent(String str, List<String> list) {
            DebugStore.recordEventNative(str, list);
        }

        private String attributeString(List<String> list) {
            StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i));
                if (i % 2 == 0) {
                    sb.append("=");
                } else if (i < list.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            return sb.toString();
        }
    }
}
