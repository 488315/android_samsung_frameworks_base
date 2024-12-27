package com.android.server.companion.virtual;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class InputController$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InputController f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ String f$4;

    public /* synthetic */ InputController$$ExternalSyntheticLambda1(
            InputController inputController, String str, int i, int i2, String str2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = inputController;
        this.f$1 = str;
        this.f$2 = i;
        this.f$3 = i2;
        this.f$4 = str2;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                InputController inputController = this.f$0;
                String str = this.f$1;
                int i = this.f$2;
                int i2 = this.f$3;
                String str2 = this.f$4;
                inputController.mNativeWrapper.getClass();
                return Long.valueOf(
                        InputController.m352$$Nest$smnativeOpenUinputKeyboard(i, i2, str, str2));
            case 1:
                InputController inputController2 = this.f$0;
                String str3 = this.f$1;
                int i3 = this.f$2;
                int i4 = this.f$3;
                String str4 = this.f$4;
                inputController2.mNativeWrapper.getClass();
                return Long.valueOf(
                        InputController.m353$$Nest$smnativeOpenUinputMouse(i3, i4, str3, str4));
            default:
                InputController inputController3 = this.f$0;
                String str5 = this.f$1;
                int i5 = this.f$2;
                int i6 = this.f$3;
                String str6 = this.f$4;
                inputController3.mNativeWrapper.getClass();
                return Long.valueOf(
                        InputController.m351$$Nest$smnativeOpenUinputDpad(i5, i6, str5, str6));
        }
    }
}
