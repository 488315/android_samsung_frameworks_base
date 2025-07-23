package com.android.wm.shell.compatui.letterbox;

import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SingleSurfaceLetterboxController implements LetterboxController {
    public static final String TAG;
    public final LetterboxSurfaceBuilder letterboxBuilder;
    public final Map letterboxMap = new LinkedHashMap();

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
        TAG = "SingleSurfaceLetterboxController";
    }

    public SingleSurfaceLetterboxController(LetterboxSurfaceBuilder letterboxSurfaceBuilder) {
        this.letterboxBuilder = letterboxSurfaceBuilder;
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void createLetterboxSurface(final LetterboxKey letterboxKey, final SurfaceControl.Transaction transaction, final SurfaceControl surfaceControl) {
        LetterboxUtils$Maps.runOnItem$default(LetterboxUtils$Maps.INSTANCE, this.letterboxMap, letterboxKey, null, new Function2() { // from class: com.android.wm.shell.compatui.letterbox.SingleSurfaceLetterboxController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                SurfaceControl.Transaction transaction2 = transaction;
                SurfaceControl surfaceControl2 = surfaceControl;
                ((Map) obj2).put((LetterboxKey) obj, LetterboxSurfaceBuilder.createSurface$default(SingleSurfaceLetterboxController.this.letterboxBuilder, transaction2, surfaceControl2, "ShellLetterboxSurface-" + letterboxKey, "LetterboxController-createLetterboxSurface"));
                return Unit.INSTANCE;
            }
        }, 2);
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void destroyLetterboxSurface(LetterboxKey letterboxKey, final SurfaceControl.Transaction transaction) {
        LetterboxUtils$Maps.runOnItem$default(LetterboxUtils$Maps.INSTANCE, this.letterboxMap, letterboxKey, new Function1() { // from class: com.android.wm.shell.compatui.letterbox.SingleSurfaceLetterboxController$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                String str = SingleSurfaceLetterboxController.TAG;
                transaction.remove((SurfaceControl) obj);
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
    public final void updateLetterboxSurfaceBounds(LetterboxKey letterboxKey, final SurfaceControl.Transaction transaction, final Rect rect, Rect rect2) {
        LetterboxUtils$Maps.runOnItem$default(LetterboxUtils$Maps.INSTANCE, this.letterboxMap, letterboxKey, new Function1() { // from class: com.android.wm.shell.compatui.letterbox.SingleSurfaceLetterboxController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                SurfaceControl.Transaction transaction2 = transaction;
                String str = SingleSurfaceLetterboxController.TAG;
                LetterboxUtils$Transactions.INSTANCE.getClass();
                LetterboxUtils$Transactions.moveAndCrop(rect, transaction2, (SurfaceControl) obj);
                return Unit.INSTANCE;
            }
        }, null, 4);
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void updateLetterboxSurfaceVisibility(LetterboxKey letterboxKey, final SurfaceControl.Transaction transaction, final boolean z) {
        LetterboxUtils$Maps.runOnItem$default(LetterboxUtils$Maps.INSTANCE, this.letterboxMap, letterboxKey, new Function1() { // from class: com.android.wm.shell.compatui.letterbox.SingleSurfaceLetterboxController$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                String str = SingleSurfaceLetterboxController.TAG;
                transaction.setVisibility((SurfaceControl) obj, z);
                return Unit.INSTANCE;
            }
        }, null, 4);
    }
}
