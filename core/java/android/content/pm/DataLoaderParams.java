package android.content.pm;

import android.annotation.SystemApi;
import android.content.ComponentName;

@SystemApi
/* loaded from: classes.dex */
public class DataLoaderParams {
    private final DataLoaderParamsParcel mData;

    public static final DataLoaderParams forStreaming(
            ComponentName componentName, String arguments) {
        return new DataLoaderParams(1, componentName, arguments);
    }

    @SystemApi
    public static final DataLoaderParams forIncremental(
            ComponentName componentName, String arguments) {
        return new DataLoaderParams(2, componentName, arguments);
    }

    public DataLoaderParams(int type, ComponentName componentName, String arguments) {
        DataLoaderParamsParcel data = new DataLoaderParamsParcel();
        data.type = type;
        data.packageName = componentName.getPackageName();
        data.className = componentName.getClassName();
        data.arguments = arguments;
        this.mData = data;
    }

    DataLoaderParams(DataLoaderParamsParcel data) {
        this.mData = data;
    }

    public final DataLoaderParamsParcel getData() {
        return this.mData;
    }

    public final int getType() {
        return this.mData.type;
    }

    public final ComponentName getComponentName() {
        return new ComponentName(this.mData.packageName, this.mData.className);
    }

    public final String getArguments() {
        return this.mData.arguments;
    }
}
