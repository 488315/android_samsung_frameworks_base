package com.android.server.input.debug;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FocusEventDebugView$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FocusEventDebugView f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ FocusEventDebugView$$ExternalSyntheticLambda0(
            FocusEventDebugView focusEventDebugView, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = focusEventDebugView;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FocusEventDebugView.$r8$lambda$NOfikC3SqQwvKrlgqWnqfisz50A(this.f$0, this.f$1);
                break;
            default:
                this.f$0.handleUpdateShowRotaryInput(this.f$1);
                break;
        }
    }
}
