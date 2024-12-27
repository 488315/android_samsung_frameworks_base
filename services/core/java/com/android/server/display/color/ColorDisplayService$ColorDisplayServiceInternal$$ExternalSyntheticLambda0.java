package com.android.server.display.color;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */
class ColorDisplayService$ColorDisplayServiceInternal$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ColorDisplayService.ColorDisplayServiceInternal f$0;

    public /* synthetic */
    ColorDisplayService$ColorDisplayServiceInternal$$ExternalSyntheticLambda0(
            ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal, int i) {
        this.$r8$classId = i;
        this.f$0 = colorDisplayServiceInternal;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal = this.f$0;
        switch (i) {
            case 0:
                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                ColorDisplayService.m475$$Nest$mapplyTint(
                        colorDisplayService,
                        colorDisplayService.mGlobalSaturationTintController,
                        true);
                break;
            case 1:
                ColorDisplayService colorDisplayService2 = ColorDisplayService.this;
                ColorDisplayService.m475$$Nest$mapplyTint(
                        colorDisplayService2,
                        colorDisplayService2.mGlobalSaturationTintController,
                        true);
                break;
            default:
                ColorDisplayService colorDisplayService3 = ColorDisplayService.this;
                ColorDisplayService.m475$$Nest$mapplyTint(
                        colorDisplayService3,
                        colorDisplayService3.mReduceBrightColorsTintController,
                        false);
                break;
        }
    }
}
