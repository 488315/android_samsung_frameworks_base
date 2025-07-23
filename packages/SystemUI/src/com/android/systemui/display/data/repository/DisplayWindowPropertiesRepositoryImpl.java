package com.android.systemui.display.data.repository;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.WindowManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.display.shared.model.DisplayWindowProperties;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import java.io.PrintWriter;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DisplayWindowPropertiesRepositoryImpl implements DisplayWindowPropertiesRepository, CoreStartable {
    public final CoroutineScope backgroundApplicationScope;
    public final DisplayRepository displayRepository;
    public final Context globalContext;
    public final LayoutInflater globalLayoutInflater;
    public final WindowManager globalWindowManager;
    public final HashBasedTable properties;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DisplayWindowPropertiesRepositoryImpl(CoroutineScope coroutineScope, Context context, WindowManager windowManager, LayoutInflater layoutInflater, DisplayRepository displayRepository) {
        this.backgroundApplicationScope = coroutineScope;
        this.globalContext = context;
        this.globalWindowManager = windowManager;
        this.globalLayoutInflater = layoutInflater;
        this.displayRepository = displayRepository;
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (!ShadeWindowGoesAround.FLAG.isTrue()) {
            throw new IllegalStateException("This should be instantiated only when wither StatusBarConnectedDisplays or ShadeWindowGoesAround are enabled.");
        }
        this.properties = HashBasedTable.create();
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.write("perDisplayContexts: " + this.properties);
    }

    public final DisplayWindowProperties get(int i, int i2) {
        int i3;
        DisplayWindowProperties displayWindowProperties;
        Display display = ((DisplayRepositoryImpl) this.displayRepository).displayRepositoryFromLib.getDisplay(i);
        if (display != null) {
            HashBasedTable hashBasedTable = this.properties;
            Integer valueOf = Integer.valueOf(i);
            Integer valueOf2 = Integer.valueOf(i2);
            Map map = (Map) Maps.safeGet(valueOf, hashBasedTable.rowMap());
            DisplayWindowProperties displayWindowProperties2 = (DisplayWindowProperties) (map == null ? null : Maps.safeGet(valueOf2, map));
            if (displayWindowProperties2 != null) {
                return displayWindowProperties2;
            }
            int displayId = display.getDisplayId();
            if (displayId == 0) {
                i3 = i2;
                displayWindowProperties = new DisplayWindowProperties(displayId, i3, this.globalContext, this.globalWindowManager, this.globalLayoutInflater);
            } else {
                i3 = i2;
                Context createWindowContext = this.globalContext.createWindowContext(display, i3, Bundle.EMPTY);
                createWindowContext.setTheme(R.style.Theme_SystemUI);
                if (createWindowContext.getDisplayId() != display.getDisplayId()) {
                    Log.e("DisplayWindowPropsRepo", "Returning null because the new context doesn't have the desired display id " + display.getDisplayId() + ". Display was already removed.");
                    displayWindowProperties = null;
                } else {
                    WindowManager windowManager = WindowManagerUtils.getWindowManager(createWindowContext);
                    LayoutInflater from = LayoutInflater.from(createWindowContext);
                    from.getClass();
                    displayWindowProperties = new DisplayWindowProperties(displayId, i3, createWindowContext, windowManager, from);
                }
            }
            if (displayWindowProperties != null) {
                hashBasedTable.put(Integer.valueOf(i), Integer.valueOf(i3), displayWindowProperties);
                return displayWindowProperties;
            }
        }
        return null;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.backgroundApplicationScope, null, null, new DisplayWindowPropertiesRepositoryImpl$start$1(this, null), 6);
    }
}
