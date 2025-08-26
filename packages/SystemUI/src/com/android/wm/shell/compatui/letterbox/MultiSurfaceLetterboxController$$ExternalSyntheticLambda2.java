package com.android.wm.shell.compatui.letterbox;

import android.graphics.Rect;
import android.view.SurfaceControl;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class MultiSurfaceLetterboxController$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ MultiSurfaceLetterboxController f$0;
    public final /* synthetic */ SurfaceControl.Transaction f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ MultiSurfaceLetterboxController$$ExternalSyntheticLambda2(MultiSurfaceLetterboxController multiSurfaceLetterboxController, SurfaceControl.Transaction transaction, Rect rect, Rect rect2) {
        this.f$0 = multiSurfaceLetterboxController;
        this.f$1 = transaction;
        this.f$2 = rect;
        this.f$3 = rect2;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Object obj2 = this.f$3;
        MultiSurfaceLetterboxController multiSurfaceLetterboxController = this.f$0;
        Object obj3 = this.f$2;
        int i = this.$r8$classId;
        SurfaceControl.Transaction transaction = this.f$1;
        switch (i) {
            case 0:
                Rect rect = (Rect) obj3;
                Rect rect2 = (Rect) obj2;
                LetterboxSurfaces letterboxSurfaces = (LetterboxSurfaces) obj;
                String str = MultiSurfaceLetterboxController.TAG;
                multiSurfaceLetterboxController.getClass();
                SurfaceControl surfaceControl = letterboxSurfaces.leftSurface;
                if (surfaceControl != null) {
                    LetterboxUtils$Transactions letterboxUtils$Transactions = LetterboxUtils$Transactions.INSTANCE;
                    Rect rect3 = new Rect(rect.left, rect.top, rect2.left, rect.bottom);
                    letterboxUtils$Transactions.getClass();
                    LetterboxUtils$Transactions.moveAndCrop(rect3, transaction, surfaceControl);
                }
                SurfaceControl surfaceControl2 = letterboxSurfaces.rightSurface;
                if (surfaceControl2 != null) {
                    LetterboxUtils$Transactions letterboxUtils$Transactions2 = LetterboxUtils$Transactions.INSTANCE;
                    Rect rect4 = new Rect(rect2.right, rect.top, rect.right, rect.bottom);
                    letterboxUtils$Transactions2.getClass();
                    LetterboxUtils$Transactions.moveAndCrop(rect4, transaction, surfaceControl2);
                }
                SurfaceControl surfaceControl3 = letterboxSurfaces.topSurface;
                if (surfaceControl3 != null) {
                    LetterboxUtils$Transactions letterboxUtils$Transactions3 = LetterboxUtils$Transactions.INSTANCE;
                    Rect rect5 = new Rect(rect.left, rect.top, rect.right, rect2.top);
                    letterboxUtils$Transactions3.getClass();
                    LetterboxUtils$Transactions.moveAndCrop(rect5, transaction, surfaceControl3);
                }
                SurfaceControl surfaceControl4 = letterboxSurfaces.bottomSurface;
                if (surfaceControl4 != null) {
                    LetterboxUtils$Transactions letterboxUtils$Transactions4 = LetterboxUtils$Transactions.INSTANCE;
                    Rect rect6 = new Rect(rect.left, rect2.bottom, rect.right, rect.bottom);
                    letterboxUtils$Transactions4.getClass();
                    LetterboxUtils$Transactions.moveAndCrop(rect6, transaction, surfaceControl4);
                }
                return Unit.INSTANCE;
            default:
                return LetterboxSurfaceBuilder.createSurface$default(multiSurfaceLetterboxController.letterboxBuilder, transaction, (SurfaceControl) obj3, "ShellLetterboxSurface-" + ((LetterboxKey) obj2) + "-" + ((String) obj), "MultiSurfaceLetterboxController#createLetterboxSurface");
        }
    }

    public /* synthetic */ MultiSurfaceLetterboxController$$ExternalSyntheticLambda2(MultiSurfaceLetterboxController multiSurfaceLetterboxController, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, LetterboxKey letterboxKey) {
        this.f$0 = multiSurfaceLetterboxController;
        this.f$1 = transaction;
        this.f$2 = surfaceControl;
        this.f$3 = letterboxKey;
    }
}
