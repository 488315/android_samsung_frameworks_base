package androidx.compose.ui.platform;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import androidx.compose.runtime.CompositionContext;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.SupervisorJobImpl;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class WindowRecomposer_androidKt {
    public static final Map animationScale = new LinkedHashMap();

    /* JADX WARN: Type inference failed for: r6v1, types: [androidx.compose.ui.platform.WindowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$contentObserver$1] */
    public static final StateFlow access$getAnimationScaleFlowFor(Context context) {
        StateFlow stateFlow;
        Map map = animationScale;
        synchronized (map) {
            try {
                LinkedHashMap linkedHashMap = (LinkedHashMap) map;
                Object obj = linkedHashMap.get(context);
                if (obj == null) {
                    ContentResolver contentResolver = context.getContentResolver();
                    Uri uriFor = Settings.Global.getUriFor(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE);
                    final BufferedChannel Channel$default = ChannelKt.Channel$default(-1, null, null, 6);
                    final Handler createAsync = Handler.createAsync(Looper.getMainLooper());
                    SafeFlow safeFlow = new SafeFlow(new WindowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$1(contentResolver, uriFor, new ContentObserver(createAsync) { // from class: androidx.compose.ui.platform.WindowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$contentObserver$1
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z, Uri uri) {
                            Channel.this.mo3456trySendJP2dKIU(Unit.INSTANCE);
                        }
                    }, Channel$default, context, null));
                    SupervisorJobImpl SupervisorJob$default = SupervisorKt.SupervisorJob$default();
                    DefaultScheduler defaultScheduler = Dispatchers.Default;
                    obj = FlowKt.stateIn(safeFlow, new ContextScope(CoroutineContext.DefaultImpls.plus(SupervisorJob$default, MainDispatcherLoader.dispatcher)), SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Float.valueOf(Settings.Global.getFloat(context.getContentResolver(), SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f)));
                    linkedHashMap.put(context, obj);
                }
                stateFlow = (StateFlow) obj;
            } catch (Throwable th) {
                throw th;
            }
        }
        return stateFlow;
    }

    public static final CompositionContext getCompositionContext(View view) {
        Object tag = view.getTag(R.id.androidx_compose_ui_view_composition_context);
        if (tag instanceof CompositionContext) {
            return (CompositionContext) tag;
        }
        return null;
    }
}
