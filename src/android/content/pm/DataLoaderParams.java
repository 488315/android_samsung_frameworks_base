package android.content.pm;

import android.annotation.SystemApi;
import android.content.ComponentName;

@SystemApi
/* loaded from: classes.dex */
public class DataLoaderParams {
    private final DataLoaderParamsParcel mData;

    public static final DataLoaderParams forStreaming(ComponentName componentName, String str) {
        return new DataLoaderParams(1, componentName, str);
    }

    @SystemApi
    public static final DataLoaderParams forIncremental(ComponentName componentName, String str) {
        return new DataLoaderParams(2, componentName, str);
    }

    public DataLoaderParams(int i, ComponentName componentName, String str) {
        DataLoaderParamsParcel dataLoaderParamsParcel = new DataLoaderParamsParcel();
        dataLoaderParamsParcel.type = i;
        dataLoaderParamsParcel.packageName = componentName.getPackageName();
        dataLoaderParamsParcel.className = componentName.getClassName();
        dataLoaderParamsParcel.arguments = str;
        this.mData = dataLoaderParamsParcel;
    }

    DataLoaderParams(DataLoaderParamsParcel dataLoaderParamsParcel) {
        this.mData = dataLoaderParamsParcel;
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
