package com.android.systemui.bixby2.controller.mediacontrol;

import android.content.pm.PackageManager;
import android.provider.Settings;
import android.view.KeyEvent;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.bixby2.util.AudioManagerWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class PlayLastSongController extends MediaCommandType {
    public static final int $stable = 0;
    public static final Companion Companion = new Companion(null);
    private static final String YOUTUBE_PACKAGE = "com.google.android.youtube";
    private final int mode;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public PlayLastSongController(int i) {
        this.mode = i;
    }

    private final void dispatchMediaKeyEvent() {
        AudioManagerWrapper audioManagerWrapper = new AudioManagerWrapper(MediaCommandType.Companion.getContext());
        audioManagerWrapper.dispatchMediaKeyEvent(new KeyEvent(0, 126));
        audioManagerWrapper.dispatchMediaKeyEvent(new KeyEvent(1, 126));
    }

    private final boolean isInstalledApp(String str) {
        PackageManager packageManager = MediaCommandType.Companion.getContext().getPackageManager();
        String[] strArr = (String[]) StringsKt__StringsKt.split$default(str, new String[]{"/"}, 0, 6).toArray(new String[0]);
        if (strArr.length != 2) {
            return false;
        }
        try {
            packageManager.getPackageInfo(strArr[0], 4);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType
    public CommandActionResponse action() {
        String string;
        if (this.mode != 0 || (string = Settings.Secure.getString(MediaCommandType.Companion.getContext().getContentResolver(), "media_button_receiver")) == null) {
            return new CommandActionResponse(2, ActionResults.RESULT_MEDIA_NOT_AVAILABLE);
        }
        String[] strArr = (String[]) StringsKt__StringsKt.split$default(string, new String[]{","}, 0, 6).toArray(new String[0]);
        if ((strArr.length != 2 && strArr.length != 3) || !isInstalledApp(strArr[0])) {
            return new CommandActionResponse(2, ActionResults.RESULT_MEDIA_NOT_AVAILABLE);
        }
        if (StringsKt__StringsKt.contains(strArr[0], YOUTUBE_PACKAGE, false)) {
            return new CommandActionResponse(2, ActionResults.RESULT_MEDIA_NOT_AVAILABLE);
        }
        String str = strArr[0];
        dispatchMediaKeyEvent();
        return new CommandActionResponse(1, "success");
    }

    public final int getMode() {
        return this.mode;
    }
}
