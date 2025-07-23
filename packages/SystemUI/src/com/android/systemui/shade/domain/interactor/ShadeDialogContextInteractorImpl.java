package com.android.systemui.shade.domain.interactor;

import android.content.Context;
import android.os.Trace;
import android.util.Log;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepositoryImpl;
import com.android.systemui.display.shared.model.DisplayWindowProperties;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepository;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import javax.inject.Provider;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeDialogContextInteractorImpl implements CoreStartable, ShadeDialogContextInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope bgScope;
    public final Context defaultContext;
    public final Provider displayWindowPropertyRepository;
    public final Provider shadeDisplaysRepository;

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

    public ShadeDialogContextInteractorImpl(Context context, Provider provider, Provider provider2, CoroutineScope coroutineScope) {
        this.defaultContext = context;
        this.displayWindowPropertyRepository = provider;
        this.shadeDisplaysRepository = provider2;
        this.bgScope = coroutineScope;
    }

    public final Context getContext() {
        ShadeWindowGoesAround.INSTANCE.getClass();
        return !ShadeWindowGoesAround.FLAG.isTrue() ? this.defaultContext : getContextOrDefault(((Number) ((ShadeDisplaysRepositoryImpl) ((ShadeDisplaysRepository) this.shadeDisplaysRepository.get())).displayId.getValue()).intValue());
    }

    public final Context getContextOrDefault(int i) {
        Context context;
        try {
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                TraceUtilsKt.beginSlice("Getting dialog context for displayId=" + i);
            }
            try {
                DisplayWindowProperties displayWindowProperties = ((DisplayWindowPropertiesRepositoryImpl) ((DisplayWindowPropertiesRepository) this.displayWindowPropertyRepository.get())).get(i, 2017);
                if (displayWindowProperties == null) {
                    Log.e("ShadeDialogContextRepo", "DisplayWindowPropertiesRepository returned null for display " + i + ". Returning default one");
                    context = this.defaultContext;
                } else {
                    context = displayWindowProperties.context;
                }
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
                return context;
            } catch (Throwable th) {
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e("ShadeDialogContextRepo", "Couldn't get dialog context for displayId=" + i + ". Returning default one", e);
            return this.defaultContext;
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ShadeWindowGoesAround.INSTANCE.getClass();
        boolean isTrue = ShadeWindowGoesAround.FLAG.isTrue();
        if (!isTrue) {
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.shade_window_goes_around to be enabled.");
        }
        if (isTrue) {
            CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new ShadeDialogContextInteractorImpl$start$1(this, null), 6);
        }
    }
}
