package com.android.systemui.media.mediaoutput.controller.media;

import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.entity.MediaAction;
import com.android.systemui.media.mediaoutput.ext.ImageString;
import com.android.systemui.media.mediaoutput.ext.PackageManagerExtKt;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.action.PlayKt;
import com.android.systemui.monet.ColorScheme;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.media.SemSoundAssistantManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.media.mediaoutput.controller.media.NoSessionController$execute$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ long $action;
        int label;
        final /* synthetic */ NoSessionController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(long j, NoSessionController noSessionController, Continuation continuation) {
            super(2, continuation);
            this.$action = j;
            this.this$0 = noSessionController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$action, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:23:0x008b, code lost:
        
            if (kotlin.Unit.INSTANCE != r0) goto L30;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x00aa, code lost:
        
            if (kotlin.Unit.INSTANCE == r0) goto L29;
         */
        /* JADX WARN: Removed duplicated region for block: B:22:0x006e  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long j = this.$action;
                if (j != 4) {
                    if (j == -4) {
                        StateFlowImpl stateFlowImpl = this.this$0._mediaActionsFlow;
                        MediaAction.Companion.getClass();
                        List listSingletonList = Collections.singletonList(MediaAction.play);
                        this.label = 4;
                        stateFlowImpl.setValue(listSingletonList);
                    }
                    return Unit.INSTANCE;
                }
                StateFlowImpl stateFlowImpl2 = this.this$0._mediaActionsFlow;
                MediaAction.Companion.getClass();
                List listSingletonList2 = Collections.singletonList(MediaAction.buffering);
                this.label = 1;
                stateFlowImpl2.setValue(listSingletonList2);
                if (Unit.INSTANCE != coroutineSingletons) {
                    AudioManager audioManager = this.this$0.audioManager;
                    audioManager.dispatchMediaKeyEvent(new KeyEvent(0, 126));
                    audioManager.dispatchMediaKeyEvent(new KeyEvent(1, 126));
                    this.label = 2;
                    if (DelayKt.delay(5000L, this) != coroutineSingletons) {
                    }
                }
                return coroutineSingletons;
            }
            if (i == 1) {
                ResultKt.throwOnFailure(obj);
                AudioManager audioManager2 = this.this$0.audioManager;
                audioManager2.dispatchMediaKeyEvent(new KeyEvent(0, 126));
                audioManager2.dispatchMediaKeyEvent(new KeyEvent(1, 126));
                this.label = 2;
                if (DelayKt.delay(5000L, this) != coroutineSingletons) {
                    StateFlowImpl stateFlowImpl3 = this.this$0._mediaActionsFlow;
                    MediaAction.Companion.getClass();
                    List listSingletonList3 = Collections.singletonList(MediaAction.copy$default(MediaAction.play, null, false, 14));
                    this.label = 3;
                    stateFlowImpl3.setValue(listSingletonList3);
                }
                return coroutineSingletons;
            }
            if (i != 2) {
                if (i != 3 && i != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            StateFlowImpl stateFlowImpl32 = this.this$0._mediaActionsFlow;
            MediaAction.Companion.getClass();
            List listSingletonList32 = Collections.singletonList(MediaAction.copy$default(MediaAction.play, null, false, 14));
            this.label = 3;
            stateFlowImpl32.setValue(listSingletonList32);
        }
    }

    /* renamed from: com.android.systemui.media.mediaoutput.controller.media.NoSessionController$run$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $isRecentMedia;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.controller.media.NoSessionController$run$2$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            /* synthetic */ Object L$1;
            int label;
            final /* synthetic */ NoSessionController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(NoSessionController noSessionController, Continuation continuation) {
                super(3, continuation);
                this.this$0 = noSessionController;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.this$0, (Continuation) obj3);
                anonymousClass4.L$0 = (Painter) obj;
                anonymousClass4.L$1 = (ColorScheme) obj2;
                return anonymousClass4.invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code restructure failed: missing block: B:14:0x0049, code lost:
            
                if (kotlin.Unit.INSTANCE == r0) goto L15;
             */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invokeSuspend(Object obj) {
                ColorScheme colorScheme;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Painter painter = (Painter) this.L$0;
                    colorScheme = (ColorScheme) this.L$1;
                    StateFlowImpl stateFlowImpl = this.this$0._appIconFlow;
                    this.L$0 = colorScheme;
                    this.label = 1;
                    stateFlowImpl.setValue(painter);
                    if (Unit.INSTANCE != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                if (i != 1) {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                colorScheme = (ColorScheme) this.L$0;
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl2 = this.this$0._appColorSchemeFlow;
                this.L$0 = null;
                this.label = 2;
                stateFlowImpl2.setValue(colorScheme);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$isRecentMedia = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = NoSessionController.this.new AnonymousClass2(this.$isRecentMedia, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:35:0x00a5, code lost:
        
            if (kotlin.Unit.INSTANCE != r0) goto L37;
         */
        /* JADX WARN: Removed duplicated region for block: B:34:0x009a  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            Object resourceString;
            MediaAction mediaAction;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                StateFlowImpl stateFlowImpl = NoSessionController.this._titleFlow;
                if (!this.$isRecentMedia) {
                    coroutineScope = null;
                }
                if (coroutineScope != null) {
                    Icons.Action action = Icons.Action.INSTANCE;
                    resourceString = new ImageString(R.string.tap_to_resume_playback, "%s", (ImageVector) PlayKt.Play$delegate.getValue());
                } else {
                    resourceString = new ResourceString(R.string.no_media, null, 2, null);
                }
                this.label = 1;
                stateFlowImpl.updateState(null, resourceString);
                if (Unit.INSTANCE != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i == 1) {
                ResultKt.throwOnFailure(obj);
            } else {
                if (i == 2) {
                    ResultKt.throwOnFailure(obj);
                    StateFlowImpl stateFlowImpl2 = NoSessionController.this._appIconFlow;
                    this.label = 3;
                    stateFlowImpl2.setValue(null);
                    if (Unit.INSTANCE != coroutineSingletons) {
                        StateFlowImpl stateFlowImpl3 = NoSessionController.this._appColorSchemeFlow;
                        this.label = 4;
                        stateFlowImpl3.setValue(null);
                    }
                    return coroutineSingletons;
                }
                if (i != 3) {
                    if (i != 4) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    String str = NoSessionController.this.packageName;
                    if (str.length() <= 0) {
                        str = null;
                    }
                    if (str == null) {
                        return Unit.INSTANCE;
                    }
                    ColorSchemeLoader colorSchemeLoader = (ColorSchemeLoader) NoSessionController.this.colorSchemeLoader$delegate.getValue();
                    NoSessionController noSessionController = NoSessionController.this;
                    Context context = noSessionController.context;
                    String str2 = noSessionController.packageName;
                    AnonymousClass4 anonymousClass4 = new AnonymousClass4(noSessionController, null);
                    colorSchemeLoader.getClass();
                    BuildersKt.launch$default(colorSchemeLoader.coroutineScope, null, null, new ColorSchemeLoader$process$2(context, anonymousClass4, str2, null), 3);
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl32 = NoSessionController.this._appColorSchemeFlow;
                this.label = 4;
                stateFlowImpl32.setValue(null);
            }
            StateFlowImpl stateFlowImpl4 = NoSessionController.this._mediaActionsFlow;
            if (this.$isRecentMedia) {
                MediaAction.Companion.getClass();
                mediaAction = MediaAction.play;
            } else {
                MediaAction.Companion.getClass();
                mediaAction = MediaAction.noPlay;
            }
            List listSingletonList = Collections.singletonList(mediaAction);
            this.label = 2;
            stateFlowImpl4.setValue(listSingletonList);
            if (Unit.INSTANCE != coroutineSingletons) {
                StateFlowImpl stateFlowImpl22 = NoSessionController.this._appIconFlow;
                this.label = 3;
                stateFlowImpl22.setValue(null);
                if (Unit.INSTANCE != coroutineSingletons) {
                }
            }
            return coroutineSingletons;
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
                return new SemSoundAssistantManager(this.f$0.context);
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
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow("");
        this._titleFlow = stateFlowImplMutableStateFlow;
        this.titleFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._mediaActionsFlow = stateFlowImplMutableStateFlow2;
        this.mediaActionsFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(null);
        this._appIconFlow = stateFlowImplMutableStateFlow3;
        this.appIconFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(null);
        this._appColorSchemeFlow = stateFlowImplMutableStateFlow4;
        this.appColorSchemeFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
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
        BuildersKt.launch$default(this.coroutineScope, null, null, new AnonymousClass1(j, this, null), 3);
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x001e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getRecentPlayPackageName() {
        String soundAssistantProperty = ((SemSoundAssistantManager) this.semSoundAssistantManager$delegate.getValue()).getSoundAssistantProperty("media_button_package");
        if (soundAssistantProperty == null) {
            String string = Settings.Secure.getString(this.context.getContentResolver(), "media_button_receiver");
            if (string == null) {
                soundAssistantProperty = null;
            } else {
                int iIndexOf$default = StringsKt__StringsKt.indexOf$default(string, "/", 0, false, 6);
                soundAssistantProperty = iIndexOf$default == -1 ? "" : string.substring(0, iIndexOf$default);
                if (soundAssistantProperty == null || soundAssistantProperty.length() <= 0) {
                }
            }
        } else {
            if (soundAssistantProperty.length() <= 0) {
                soundAssistantProperty = null;
            }
            if (soundAssistantProperty == null) {
            }
        }
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("recent packageName = ", soundAssistantProperty, "NoSessionController");
        if (soundAssistantProperty != null) {
            SessionController.Companion.getClass();
            if (SessionController.Companion.BLUETOOTH_MEDIA_SESSION_PACKAGE.contains(soundAssistantProperty)) {
                soundAssistantProperty = null;
            }
            if (soundAssistantProperty != null) {
                if (SessionController.Companion.MEDIA_SESSION_BLOCKED_LIST.contains(soundAssistantProperty)) {
                    soundAssistantProperty = null;
                }
                if (soundAssistantProperty != null) {
                    String str = SessionController.Companion.RECENT_BLOCKED_LIST.contains(soundAssistantProperty) ? null : soundAssistantProperty;
                    if (str != null) {
                        return str;
                    }
                }
            }
        }
        return "";
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
        BuildersKt.launch$default(this.coroutineScope, null, null, new AnonymousClass2(!StringsKt__StringsKt.isBlank(r0), null), 3);
    }

    public final String toString() {
        return toLogText();
    }
}
