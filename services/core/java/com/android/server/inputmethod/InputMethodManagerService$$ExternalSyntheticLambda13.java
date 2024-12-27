package com.android.server.inputmethod;

import android.view.inputmethod.InputMethodInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class InputMethodManagerService$$ExternalSyntheticLambda13
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InputMethodManagerService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ InputMethodSettings f$3;

    public /* synthetic */ InputMethodManagerService$$ExternalSyntheticLambda13(
            InputMethodManagerService inputMethodManagerService,
            int i,
            int i2,
            InputMethodSettings inputMethodSettings,
            int i3) {
        this.$r8$classId = i3;
        this.f$0 = inputMethodManagerService;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = inputMethodSettings;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                InputMethodManagerService inputMethodManagerService = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                InputMethodSettings inputMethodSettings = this.f$3;
                inputMethodManagerService.getClass();
                return !inputMethodManagerService.canCallerAccessInputMethod(
                        ((InputMethodInfo) obj).getPackageName(), i, i2, inputMethodSettings);
            default:
                InputMethodManagerService inputMethodManagerService2 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                InputMethodSettings inputMethodSettings2 = this.f$3;
                inputMethodManagerService2.getClass();
                return !inputMethodManagerService2.canCallerAccessInputMethod(
                        ((InputMethodInfo) obj).getPackageName(), i3, i4, inputMethodSettings2);
        }
    }
}
