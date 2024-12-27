package com.android.systemui.media.mediaoutput.controller.media;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.entity.MediaAction;
import com.android.systemui.media.mediaoutput.entity.MediaInfo;
import com.android.systemui.media.mediaoutput.entity.NoMediaSessionInfo;
import com.android.systemui.media.mediaoutput.ext.ImageString;
import com.android.systemui.media.mediaoutput.ext.PackageManagerExtKt;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import com.android.systemui.media.mediaoutput.icons.Icons$Action;
import com.android.systemui.media.mediaoutput.icons.action.PlayKt;
import com.android.systemui.monet.ColorScheme;
import com.samsung.android.media.SemSoundAssistantManager;
import java.util.Collections;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.BuildersKt;

public final class NoSessionController extends SessionController {
    public final AudioManager audioManager;
    public final Lazy colorSchemeLoader$delegate;
    public NoMediaSessionInfo mediaInfoCache;
    public final Lazy packageName$delegate;
    public final Lazy semSoundAssistantManager$delegate;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public NoSessionController(final Context context, AudioManager audioManager) {
        super(context, null, 2, null);
        this.audioManager = audioManager;
        this.semSoundAssistantManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.media.NoSessionController$semSoundAssistantManager$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new SemSoundAssistantManager(context);
            }
        });
        this.colorSchemeLoader$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.media.NoSessionController$colorSchemeLoader$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new ColorSchemeLoader();
            }
        });
        this.packageName$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.media.NoSessionController$packageName$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return "no_session_media_id";
            }
        });
        Log.d("NoSessionController", "init()");
        NoMediaSessionInfo noMediaSessionInfo = new NoMediaSessionInfo(getPackageName(), null, null, null, null, null, null, 126, null);
        this.mediaInfoCache = noMediaSessionInfo;
        this._mediaInfo.setValue(noMediaSessionInfo);
        update$1();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final void execute(long j, long j2) {
        BuildersKt.launch$default(this.coroutineScope, null, null, new NoSessionController$execute$1(j, this, null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final String getAppName() {
        String recentPlayPackageName = getRecentPlayPackageName();
        if (recentPlayPackageName.length() <= 0) {
            recentPlayPackageName = null;
        }
        return recentPlayPackageName != null ? PackageManagerExtKt.getAppLabel(this.context.getPackageManager(), recentPlayPackageName) : this.context.getString(R.string.no_media);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    public final String getPackageName() {
        return (String) this.packageName$delegate.getValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0042, code lost:
    
        if (r0.length() > 0) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x001b, code lost:
    
        if (r0 != null) goto L20;
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
            goto L46
        L1e:
            android.content.Context r5 = r5.context
            android.content.ContentResolver r5 = r5.getContentResolver()
            java.lang.String r0 = "media_button_receiver"
            java.lang.String r5 = android.provider.Settings.Secure.getString(r5, r0)
            if (r5 == 0) goto L45
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
            int r5 = r0.length()
            if (r5 <= 0) goto L45
            goto L46
        L45:
            r0 = r2
        L46:
            java.lang.String r5 = "recent packageName = "
            java.lang.String r3 = "NoSessionController"
            com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m(r5, r0, r3)
            if (r0 == 0) goto L78
            com.android.systemui.media.mediaoutput.controller.media.SessionController$Companion r5 = com.android.systemui.media.mediaoutput.controller.media.SessionController.Companion
            r5.getClass()
            java.util.List r5 = com.android.systemui.media.mediaoutput.controller.media.SessionController.BLUETOOTH_MEDIA_SESSION_PACKAGE
            boolean r5 = r5.contains(r0)
            if (r5 != 0) goto L5d
            goto L5e
        L5d:
            r0 = r2
        L5e:
            if (r0 == 0) goto L78
            java.util.List r5 = com.android.systemui.media.mediaoutput.controller.media.SessionController.MEDIA_SESSION_BLOCKED_LIST
            boolean r5 = r5.contains(r0)
            if (r5 != 0) goto L69
            goto L6a
        L69:
            r0 = r2
        L6a:
            if (r0 == 0) goto L78
            java.util.List r5 = com.android.systemui.media.mediaoutput.controller.media.SessionController.RECENT_BLOCKED_LIST
            boolean r5 = r5.contains(r0)
            if (r5 != 0) goto L75
            r2 = r0
        L75:
            if (r2 == 0) goto L78
            r1 = r2
        L78:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.NoSessionController.getRecentPlayPackageName():java.lang.String");
    }

    public final synchronized void update(NoMediaSessionInfo noMediaSessionInfo) {
        this.mediaInfoCache = noMediaSessionInfo;
        update((MediaInfo) noMediaSessionInfo);
    }

    public final void update$1() {
        CharSequence resourceString;
        Log.d("NoSessionController", "update()");
        String recentPlayPackageName = getRecentPlayPackageName();
        NoMediaSessionInfo noMediaSessionInfo = this.mediaInfoCache;
        if (!(!Intrinsics.areEqual(noMediaSessionInfo.packageName, recentPlayPackageName))) {
            noMediaSessionInfo = null;
        }
        if (noMediaSessionInfo != null) {
            if ((recentPlayPackageName.length() > 0 ? recentPlayPackageName : null) != null) {
                Icons$Action icons$Action = Icons$Action.INSTANCE;
                resourceString = new ImageString(R.string.tap_to_resume_playback, "%s", (ImageVector) PlayKt.Play$delegate.getValue());
            } else {
                resourceString = new ResourceString(R.string.no_media, null, 2, null);
            }
            CharSequence charSequence = resourceString;
            MediaAction.Companion.getClass();
            NoMediaSessionInfo copy$default = NoMediaSessionInfo.copy$default(noMediaSessionInfo, recentPlayPackageName, charSequence, Collections.singletonList(MediaAction.copy$default(MediaAction.play, 0L, !StringsKt__StringsJVMKt.isBlank(recentPlayPackageName), 7)), null, null, 65);
            Log.d("NoSessionController", "update() - " + copy$default);
            update(copy$default);
            if ((recentPlayPackageName.length() > 0 ? recentPlayPackageName : null) == null) {
                return;
            }
            ColorSchemeLoader colorSchemeLoader = (ColorSchemeLoader) this.colorSchemeLoader$delegate.getValue();
            Context context = this.context;
            Function2 function2 = new Function2() { // from class: com.android.systemui.media.mediaoutput.controller.media.NoSessionController$update$4$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    NoSessionController.this.update(NoMediaSessionInfo.copy$default(NoSessionController.this.mediaInfoCache, null, null, null, (Painter) obj, (ColorScheme) obj2, 79));
                    return Unit.INSTANCE;
                }
            };
            colorSchemeLoader.getClass();
            BuildersKt.launch$default(colorSchemeLoader.coroutineScope, null, null, new ColorSchemeLoader$process$3(context, colorSchemeLoader, function2, recentPlayPackageName, null), 3);
        }
    }
}
