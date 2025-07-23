package androidx.compose.ui.platform;

import android.content.res.Resources;
import androidx.compose.ui.draganddrop.ComposeDragShadowBuilder;
import androidx.compose.ui.draganddrop.DragAndDropTransferData;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.DensityKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class AndroidComposeView$dragAndDropManager$1 extends FunctionReferenceImpl implements Function3 {
    public AndroidComposeView$dragAndDropManager$1(Object obj) {
        super(3, obj, AndroidComposeView.class, "startDrag", "startDrag-12SF9DM(Landroidx/compose/ui/draganddrop/DragAndDropTransferData;JLkotlin/jvm/functions/Function1;)Z", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public final /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return m698invoke12SF9DM((DragAndDropTransferData) obj, ((Size) obj2).packedValue, (Function1) obj3);
    }

    /* renamed from: invoke-12SF9DM, reason: not valid java name */
    public final Boolean m698invoke12SF9DM(DragAndDropTransferData dragAndDropTransferData, long j, Function1 function1) {
        AndroidComposeView androidComposeView = (AndroidComposeView) this.receiver;
        AndroidComposeView.Companion companion = AndroidComposeView.Companion;
        Resources resources = androidComposeView.getContext().getResources();
        return Boolean.valueOf(AndroidComposeViewStartDragAndDropN.INSTANCE.startDragAndDrop(androidComposeView, dragAndDropTransferData, new ComposeDragShadowBuilder(DensityKt.Density(resources.getDisplayMetrics().density, resources.getConfiguration().fontScale), j, function1, null)));
    }
}
