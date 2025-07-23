package com.android.systemui.media.mediaoutput.controller.media;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.ext.PackageManagerExtKt;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.media.SemSoundAssistantManager;
import java.util.Arrays;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NoSessionController implements NoSession {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _appColorSchemeFlow;
    public final StateFlowImpl _appIconFlow;
    public final StateFlowImpl _mediaActionsFlow;
    public final StateFlowImpl _titleFlow;
    public final ReadonlyStateFlow appColorSchemeFlow;
    public final ReadonlyStateFlow appIconFlow;
    public final AudioManager audioManager;
    public final Lazy colorSchemeLoader$delegate;
    public final Context context;
    public final ContextScope coroutineScope;
    public final Lazy id$delegate;
    public final ReadonlyStateFlow mediaActionsFlow;
    public String packageName;
    public final Lazy semSoundAssistantManager$delegate;
    public final ReadonlyStateFlow titleFlow;

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

    public NoSessionController(Context context, AudioManager audioManager) {
        this.context = context;
        this.audioManager = audioManager;
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        this.coroutineScope = CoroutineScopeKt.CoroutineScope(DefaultIoScheduler.INSTANCE);
        this.semSoundAssistantManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.media.NoSessionController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = NoSessionController.$r8$clinit;
                return new SemSoundAssistantManager(NoSessionController.this.context);
            }
        });
        final int i = 0;
        this.colorSchemeLoader$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.media.NoSessionController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = NoSessionController.$r8$clinit;
                        return new ColorSchemeLoader();
                    default:
                        int i3 = NoSessionController.$r8$clinit;
                        return "no_session_media_id";
                }
            }
        });
        final int i2 = 1;
        this.id$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.media.NoSessionController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = NoSessionController.$r8$clinit;
                        return new ColorSchemeLoader();
                    default:
                        int i3 = NoSessionController.$r8$clinit;
                        return "no_session_media_id";
                }
            }
        });
        this.packageName = "no_session_media_id";
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow("");
        this._titleFlow = MutableStateFlow;
        this.titleFlow = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._mediaActionsFlow = MutableStateFlow2;
        this.mediaActionsFlow = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(null);
        this._appIconFlow = MutableStateFlow3;
        this.appIconFlow = FlowKt.asStateFlow(MutableStateFlow3);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(null);
        this._appColorSchemeFlow = MutableStateFlow4;
        this.appColorSchemeFlow = FlowKt.asStateFlow(MutableStateFlow4);
        Log.d("NoSessionController", "init()");
        run();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final void close() {
        CoroutineScopeKt.cancel(this.coroutineScope, null);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final void execute(long j, long j2) {
        Log.d("NoSessionController", "execute() - " + j);
        BuildersKt.launch$default(this.coroutineScope, null, null, new NoSessionController$execute$1(j, this, null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getAppColorSchemeFlow() {
        return this.appColorSchemeFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getAppIconFlow() {
        return this.appIconFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final String getAppName() {
        String appLabel;
        String recentPlayPackageName = getRecentPlayPackageName();
        if (recentPlayPackageName.length() <= 0) {
            recentPlayPackageName = null;
        }
        return (recentPlayPackageName == null || (appLabel = PackageManagerExtKt.getAppLabel(this.context.getPackageManager(), recentPlayPackageName)) == null) ? this.context.getString(R.string.no_media) : appLabel;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.EntityString
    public final List getAttributes() {
        return Arrays.asList(new Pair(UniversalCredentialUtil.AGENT_TITLE, this._titleFlow.getValue()), new Pair("mediaActions", this._mediaActionsFlow.getValue()));
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final String getId() {
        return (String) this.id$delegate.getValue();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final Flow getMediaActionsFlow() {
        return this.mediaActionsFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final String getPackageName() {
        return this.packageName;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0044, code lost:
    
        if (r0.length() > 0) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x001b, code lost:
    
        if (r0 != null) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getRecentPlayPackageName() {
        /*
            r5 = this;
            kotlin.Lazy r0 = r5.semSoundAssistantManager$delegate
            java.lang.Object r0 = r0.getValue()
            com.samsung.android.media.SemSoundAssistantManager r0 = (com.samsung.android.media.SemSoundAssistantManager) r0
            java.lang.String r1 = "media_button_package"
            java.lang.String r0 = r0.getSoundAssistantProperty(r1)
            java.lang.String r1 = ""
            r2 = 0
            if (r0 == 0) goto L1e
            int r3 = r0.length()
            if (r3 <= 0) goto L1a
            goto L1b
        L1a:
            r0 = r2
        L1b:
            if (r0 == 0) goto L1e
            goto L48
        L1e:
            android.content.Context r5 = r5.context
            android.content.ContentResolver r5 = r5.getContentResolver()
            java.lang.String r0 = "media_button_receiver"
            java.lang.String r5 = android.provider.Settings.Secure.getString(r5, r0)
            if (r5 == 0) goto L47
            r0 = 6
            java.lang.String r3 = "/"
            r4 = 0
            int r0 = kotlin.text.StringsKt__StringsKt.indexOf$default(r5, r3, r4, r4, r0)
            r3 = -1
            if (r0 != r3) goto L39
            r0 = r1
            goto L3e
        L39:
            java.lang.String r5 = r5.substring(r4, r0)
            r0 = r5
        L3e:
            if (r0 == 0) goto L47
            int r5 = r0.length()
            if (r5 <= 0) goto L47
            goto L48
        L47:
            r0 = r2
        L48:
            java.lang.String r5 = "recent packageName = "
            java.lang.String r3 = "NoSessionController"
            com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m(r5, r0, r3)
            if (r0 == 0) goto L7a
            com.android.systemui.media.mediaoutput.controller.media.SessionController$Companion r5 = com.android.systemui.media.mediaoutput.controller.media.SessionController.Companion
            r5.getClass()
            java.util.List r5 = com.android.systemui.media.mediaoutput.controller.media.SessionController.Companion.BLUETOOTH_MEDIA_SESSION_PACKAGE
            boolean r5 = r5.contains(r0)
            if (r5 != 0) goto L5f
            goto L60
        L5f:
            r0 = r2
        L60:
            if (r0 == 0) goto L7a
            java.util.List r5 = com.android.systemui.media.mediaoutput.controller.media.SessionController.Companion.MEDIA_SESSION_BLOCKED_LIST
            boolean r5 = r5.contains(r0)
            if (r5 != 0) goto L6b
            goto L6c
        L6b:
            r0 = r2
        L6c:
            if (r0 == 0) goto L7a
            java.util.List r5 = com.android.systemui.media.mediaoutput.controller.media.SessionController.Companion.RECENT_BLOCKED_LIST
            boolean r5 = r5.contains(r0)
            if (r5 != 0) goto L77
            r2 = r0
        L77:
            if (r2 == 0) goto L7a
            return r2
        L7a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.NoSessionController.getRecentPlayPackageName():java.lang.String");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final ReadonlyStateFlow getTitleFlow() {
        return this.titleFlow;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final void run() {
        Log.d("NoSessionController", "run()");
        String recentPlayPackageName = getRecentPlayPackageName();
        if (recentPlayPackageName.equals(this.packageName)) {
            recentPlayPackageName = null;
        }
        if (recentPlayPackageName == null) {
            return;
        }
        this.packageName = getRecentPlayPackageName();
        BuildersKt.launch$default(this.coroutineScope, null, null, new NoSessionController$run$2(this, !StringsKt__StringsKt.isBlank(r0), null), 3);
    }

    public final String toString() {
        return toLogText();
    }
}
