package com.android.server.companion.virtual;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class InputController$$ExternalSyntheticLambda5 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InputController f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ String f$4;
    public final /* synthetic */ int f$5;
    public final /* synthetic */ int f$6;

    public /* synthetic */ InputController$$ExternalSyntheticLambda5(
            InputController inputController,
            String str,
            int i,
            int i2,
            String str2,
            int i3,
            int i4,
            int i5) {
        this.$r8$classId = i5;
        this.f$0 = inputController;
        this.f$1 = str;
        this.f$2 = i;
        this.f$3 = i2;
        this.f$4 = str2;
        this.f$5 = i3;
        this.f$6 = i4;
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
                int i3 = this.f$5;
                int i4 = this.f$6;
                inputController.mNativeWrapper.getClass();
                return Long.valueOf(
                        InputController.m355$$Nest$smnativeOpenUinputTouchscreen(
                                i, i2, i3, str, str2, i4));
            case 1:
                InputController inputController2 = this.f$0;
                String str3 = this.f$1;
                int i5 = this.f$2;
                int i6 = this.f$3;
                String str4 = this.f$4;
                int i7 = this.f$5;
                int i8 = this.f$6;
                inputController2.mNativeWrapper.getClass();
                return Long.valueOf(
                        InputController.m355$$Nest$smnativeOpenUinputTouchscreen(
                                i5, i6, i7, str3, str4, i8));
            default:
                InputController inputController3 = this.f$0;
                String str5 = this.f$1;
                int i9 = this.f$2;
                int i10 = this.f$3;
                String str6 = this.f$4;
                int i11 = this.f$5;
                int i12 = this.f$6;
                inputController3.mNativeWrapper.getClass();
                return Long.valueOf(
                        InputController.m354$$Nest$smnativeOpenUinputStylus(
                                i9, i10, i11, str5, str6, i12));
        }
    }
}
