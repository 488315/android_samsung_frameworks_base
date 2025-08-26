package com.android.systemui.deviceconfig.data.repository;

import com.android.systemui.util.DeviceConfigProxy;
import java.util.concurrent.Executor;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes2.dex */
public final class DeviceConfigRepository {
    public final CoroutineDispatcher backgroundDispatcher;
    public final Executor backgroundExecutor;
    public final DeviceConfigProxy dataSource;

    public DeviceConfigRepository(Executor executor, CoroutineDispatcher coroutineDispatcher, DeviceConfigProxy deviceConfigProxy) {
        this.backgroundExecutor = executor;
        this.backgroundDispatcher = coroutineDispatcher;
        this.dataSource = deviceConfigProxy;
    }
}
