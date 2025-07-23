package com.samsung.systemui.splugins;

import android.text.TextUtils;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface SPluginManager {
    public static final String NOTIFICATION_CHANNEL_ID = "ALR";
    public static final String PLUGIN_CHANGED = "com.android.systemui.action.PLUGIN_CHANGED";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Helper {
        public static <P> String getAction(Class<P> cls) {
            ProvidesInterface providesInterface = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
            if (providesInterface == null) {
                throw new RuntimeException(cls + " doesn't provide an interface");
            }
            if (!TextUtils.isEmpty(providesInterface.action())) {
                return providesInterface.action();
            }
            throw new RuntimeException(cls + " doesn't provide an action");
        }
    }

    <T extends SPlugin> void addPluginListener(SPluginListener<T> sPluginListener, Class<?> cls);

    <T extends SPlugin> void addPluginListener(SPluginListener<T> sPluginListener, Class<?> cls, boolean z);

    <T extends SPlugin> void addPluginListener(SPluginListener<T> sPluginListener, Class<?> cls, boolean z, boolean z2);

    <T extends SPlugin> void addPluginListener(String str, SPluginListener<T> sPluginListener, Class<?> cls);

    <T extends SPlugin> void addPluginListener(String str, SPluginListener<T> sPluginListener, Class cls, boolean z, boolean z2);

    <T> boolean dependsOn(SPlugin sPlugin, Class<T> cls);

    String[] getAllowedPlugins();

    <T extends SPlugin> T getOneShotPlugin(Class<T> cls);

    <T extends SPlugin> T getOneShotPlugin(String str, Class<?> cls);

    void removePluginListener(SPluginListener<?> sPluginListener);
}
