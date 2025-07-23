package com.android.systemui.statusbar.policy;

import android.content.Context;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.leak.LeakDetector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ExtensionControllerImpl implements ExtensionController {
    public final Context mDefaultContext;
    public final LeakDetector mLeakDetector;
    public final PluginManager mPluginManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ExtensionBuilder {
        public final ExtensionImpl mExtension;

        public /* synthetic */ ExtensionBuilder(ExtensionControllerImpl extensionControllerImpl, int i) {
            this(extensionControllerImpl);
        }

        public final ExtensionImpl build() {
            ExtensionImpl extensionImpl = this.mExtension;
            Collections.sort(extensionImpl.mProducers, Comparator.comparingInt(new ExtensionControllerImpl$ExtensionBuilder$$ExternalSyntheticLambda0()));
            ExtensionImpl.m3085$$Nest$mnotifyChanged(extensionImpl);
            return extensionImpl;
        }

        public final void withPlugin(Class cls) {
            String action = PluginManager.Helper.getAction(cls);
            ExtensionImpl extensionImpl = this.mExtension;
            extensionImpl.mProducers.add(extensionImpl.new PluginItem(action, cls, null));
        }

        private ExtensionBuilder(ExtensionControllerImpl extensionControllerImpl) {
            this.mExtension = new ExtensionImpl(extensionControllerImpl, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ExtensionImpl implements ExtensionController.Extension {
        public final ArrayList mCallbacks;
        public Object mItem;
        public Context mPluginContext;
        public final ArrayList mProducers;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Default implements Item {
            public final Supplier mSupplier;

            public Default(ExtensionImpl extensionImpl, Supplier<Object> supplier) {
                this.mSupplier = supplier;
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Item
            public final Object get() {
                return this.mSupplier.get();
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Item
            public final int sortOrder() {
                return 4;
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class PluginItem implements Item, PluginListener {
            public Plugin mItem;

            public PluginItem(String str, Class<Plugin> cls, ExtensionController.PluginConverter pluginConverter) {
                ExtensionControllerImpl.this.mPluginManager.addPluginListener(str, this, cls);
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Item
            public final Object get() {
                return this.mItem;
            }

            @Override // com.android.systemui.plugins.PluginListener
            public final void onPluginConnected(Plugin plugin, Context context) {
                ExtensionImpl extensionImpl = ExtensionImpl.this;
                extensionImpl.mPluginContext = context;
                this.mItem = plugin;
                ExtensionImpl.m3085$$Nest$mnotifyChanged(extensionImpl);
            }

            @Override // com.android.systemui.plugins.PluginListener
            public final void onPluginDisconnected(Plugin plugin) {
                ExtensionImpl extensionImpl = ExtensionImpl.this;
                extensionImpl.mPluginContext = null;
                this.mItem = null;
                ExtensionImpl.m3085$$Nest$mnotifyChanged(extensionImpl);
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Item
            public final int sortOrder() {
                return 0;
            }
        }

        /* renamed from: -$$Nest$mnotifyChanged, reason: not valid java name */
        public static void m3085$$Nest$mnotifyChanged(ExtensionImpl extensionImpl) {
            Object obj = extensionImpl.mItem;
            if (obj != null) {
                ExtensionControllerImpl.this.mLeakDetector.trackGarbage(obj);
            }
            extensionImpl.mItem = null;
            int i = 0;
            while (true) {
                if (i >= extensionImpl.mProducers.size()) {
                    break;
                }
                Object obj2 = ((Item) extensionImpl.mProducers.get(i)).get();
                if (obj2 != null) {
                    extensionImpl.mItem = obj2;
                    break;
                }
                i++;
            }
            for (int i2 = 0; i2 < extensionImpl.mCallbacks.size(); i2++) {
                ((Consumer) extensionImpl.mCallbacks.get(i2)).accept(extensionImpl.mItem);
            }
        }

        public /* synthetic */ ExtensionImpl(ExtensionControllerImpl extensionControllerImpl, int i) {
            this();
        }

        private ExtensionImpl() {
            this.mProducers = new ArrayList();
            this.mCallbacks = new ArrayList();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Item {
        Object get();

        int sortOrder();
    }

    public ExtensionControllerImpl(Context context, LeakDetector leakDetector, PluginManager pluginManager, TunerService tunerService, ConfigurationController configurationController) {
        this.mDefaultContext = context;
        this.mLeakDetector = leakDetector;
        this.mPluginManager = pluginManager;
    }
}
