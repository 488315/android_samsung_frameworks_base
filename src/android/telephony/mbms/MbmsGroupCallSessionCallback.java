package android.telephony.mbms;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* loaded from: classes4.dex */
public interface MbmsGroupCallSessionCallback {

    @Retention(RetentionPolicy.SOURCE)
    public @interface GroupCallError {
    }

    default void onAvailableSaisUpdated(List<Integer> list, List<List<Integer>> list2) {
    }

    default void onError(int i, String str) {
    }

    default void onMiddlewareReady() {
    }

    default void onServiceInterfaceAvailable(String str, int i) {
    }
}
