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
import com.google.common.collect.StandardTable;
import java.io.PrintWriter;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class DisplayWindowPropertiesRepositoryImpl implements DisplayWindowPropertiesRepository, CoreStartable {
    public final CoroutineScope backgroundApplicationScope;
    public final DisplayRepository displayRepository;
    public final Context globalContext;
    public final LayoutInflater globalLayoutInflater;
    public final WindowManager globalWindowManager;
    public final HashBasedTable properties;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.display.data.repository.DisplayWindowPropertiesRepositoryImpl$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DisplayWindowPropertiesRepositoryImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow displayRemovalEvent = ((DisplayRepositoryImpl) DisplayWindowPropertiesRepositoryImpl.this.displayRepository).displayRepositoryFromLib.getDisplayRemovalEvent();
                final DisplayWindowPropertiesRepositoryImpl displayWindowPropertiesRepositoryImpl = DisplayWindowPropertiesRepositoryImpl.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.display.data.repository.DisplayWindowPropertiesRepositoryImpl.start.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        int iIntValue = ((Number) obj2).intValue();
                        HashBasedTable hashBasedTable = displayWindowPropertiesRepositoryImpl.properties;
                        Integer num = new Integer(iIntValue);
                        hashBasedTable.getClass();
                        new StandardTable.Row(num).clear();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (displayRemovalEvent.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
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
            Integer numValueOf = Integer.valueOf(i);
            Integer numValueOf2 = Integer.valueOf(i2);
            Map map = (Map) Maps.safeGet(numValueOf, hashBasedTable.rowMap());
            DisplayWindowProperties displayWindowProperties2 = (DisplayWindowProperties) (map == null ? null : Maps.safeGet(numValueOf2, map));
            if (displayWindowProperties2 != null) {
                return displayWindowProperties2;
            }
            int displayId = display.getDisplayId();
            if (displayId == 0) {
                i3 = i2;
                displayWindowProperties = new DisplayWindowProperties(displayId, i3, this.globalContext, this.globalWindowManager, this.globalLayoutInflater);
            } else {
                i3 = i2;
                Context contextCreateWindowContext = this.globalContext.createWindowContext(display, i3, Bundle.EMPTY);
                contextCreateWindowContext.setTheme(R.style.Theme_SystemUI);
                if (contextCreateWindowContext.getDisplayId() != display.getDisplayId()) {
                    Log.e("DisplayWindowPropsRepo", "Returning null because the new context doesn't have the desired display id " + display.getDisplayId() + ". Display was already removed.");
                    displayWindowProperties = null;
                } else {
                    WindowManager windowManager = WindowManagerUtils.getWindowManager(contextCreateWindowContext);
                    LayoutInflater layoutInflaterFrom = LayoutInflater.from(contextCreateWindowContext);
                    layoutInflaterFrom.getClass();
                    displayWindowProperties = new DisplayWindowProperties(displayId, i3, contextCreateWindowContext, windowManager, layoutInflaterFrom);
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
        CoroutineTracingKt.launchTraced$default(this.backgroundApplicationScope, null, null, new AnonymousClass1(null), 6);
    }
}
