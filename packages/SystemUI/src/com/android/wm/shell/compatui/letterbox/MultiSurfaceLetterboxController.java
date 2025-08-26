package com.android.wm.shell.compatui.letterbox;

import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.internal.protolog.ProtoLog;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class MultiSurfaceLetterboxController implements LetterboxController {
    public static final String TAG;
    public final LetterboxSurfaceBuilder letterboxBuilder;
    public final Map letterboxMap = new LinkedHashMap();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TAG = "MultiSurfaceLetterboxController";
    }

    public MultiSurfaceLetterboxController(LetterboxSurfaceBuilder letterboxSurfaceBuilder) {
        this.letterboxBuilder = letterboxSurfaceBuilder;
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void createLetterboxSurface(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        final MultiSurfaceLetterboxController$$ExternalSyntheticLambda2 multiSurfaceLetterboxController$$ExternalSyntheticLambda2 = new MultiSurfaceLetterboxController$$ExternalSyntheticLambda2(this, transaction, surfaceControl, letterboxKey);
        LetterboxUtils$Maps.runOnItem$default(LetterboxUtils$Maps.INSTANCE, this.letterboxMap, letterboxKey, null, new Function2() { // from class: com.android.wm.shell.compatui.letterbox.MultiSurfaceLetterboxController$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                String str = MultiSurfaceLetterboxController.TAG;
                MultiSurfaceLetterboxController$$ExternalSyntheticLambda2 multiSurfaceLetterboxController$$ExternalSyntheticLambda22 = multiSurfaceLetterboxController$$ExternalSyntheticLambda2;
                ((Map) obj2).put((LetterboxKey) obj, new LetterboxSurfaces((SurfaceControl) multiSurfaceLetterboxController$$ExternalSyntheticLambda22.mo781invoke(SystemUIAnalytics.DT_BOUNCER_POSITION_LEFT), (SurfaceControl) multiSurfaceLetterboxController$$ExternalSyntheticLambda22.mo781invoke("Top"), (SurfaceControl) multiSurfaceLetterboxController$$ExternalSyntheticLambda22.mo781invoke(SystemUIAnalytics.DT_BOUNCER_POSITION_RIGHT), (SurfaceControl) multiSurfaceLetterboxController$$ExternalSyntheticLambda22.mo781invoke("Bottom")));
                return Unit.INSTANCE;
            }
        }, 2);
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void destroyLetterboxSurface(LetterboxKey letterboxKey, final SurfaceControl.Transaction transaction) {
        LetterboxUtils$Maps.runOnItem$default(LetterboxUtils$Maps.INSTANCE, this.letterboxMap, letterboxKey, new Function1() { // from class: com.android.wm.shell.compatui.letterbox.MultiSurfaceLetterboxController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                SurfaceControl.Transaction transaction2 = transaction;
                String str = MultiSurfaceLetterboxController.TAG;
                Iterator it = ((LetterboxSurfaces) obj).iterator();
                while (it.hasNext()) {
                    SurfaceControl surfaceControl = (SurfaceControl) it.next();
                    this.f$0.getClass();
                    if (surfaceControl != null) {
                        transaction2.remove(surfaceControl);
                    }
                }
                return Unit.INSTANCE;
            }
        }, null, 4);
        this.letterboxMap.remove(letterboxKey);
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void dump() {
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_APP_COMPAT, "%s: %s", new Object[]{TAG, String.valueOf(((LinkedHashMap) this.letterboxMap).keySet())});
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void updateLetterboxSurfaceBounds(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction, Rect rect, Rect rect2) {
        LetterboxUtils$Maps.runOnItem$default(LetterboxUtils$Maps.INSTANCE, this.letterboxMap, letterboxKey, new MultiSurfaceLetterboxController$$ExternalSyntheticLambda2(this, transaction, rect, rect2), null, 4);
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void updateLetterboxSurfaceVisibility(LetterboxKey letterboxKey, final SurfaceControl.Transaction transaction, final boolean z) {
        LetterboxUtils$Maps.runOnItem$default(LetterboxUtils$Maps.INSTANCE, this.letterboxMap, letterboxKey, new Function1() { // from class: com.android.wm.shell.compatui.letterbox.MultiSurfaceLetterboxController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                SurfaceControl.Transaction transaction2 = transaction;
                String str = MultiSurfaceLetterboxController.TAG;
                Iterator it = ((LetterboxSurfaces) obj).iterator();
                while (it.hasNext()) {
                    SurfaceControl surfaceControl = (SurfaceControl) it.next();
                    this.f$0.getClass();
                    if (surfaceControl != null) {
                        transaction2.setVisibility(surfaceControl, z);
                    }
                }
                return Unit.INSTANCE;
            }
        }, null, 4);
    }
}
