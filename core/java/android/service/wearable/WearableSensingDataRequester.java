package android.service.wearable;

import android.annotation.SystemApi;
import android.app.wearable.WearableSensingDataRequest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.Consumer;

@SystemApi
public interface WearableSensingDataRequester {
    public static final int STATUS_OBSERVER_CANCELLED = 2;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_TOO_FREQUENT = 4;
    public static final int STATUS_TOO_LARGE = 3;
    public static final int STATUS_UNKNOWN = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StatusCode {}

    void requestData(
            WearableSensingDataRequest wearableSensingDataRequest, Consumer<Integer> consumer);
}
