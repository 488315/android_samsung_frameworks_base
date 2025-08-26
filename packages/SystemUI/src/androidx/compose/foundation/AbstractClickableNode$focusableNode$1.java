package androidx.compose.foundation;

import androidx.collection.MutableLongObjectMap;
import androidx.compose.foundation.AbstractClickableNode;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes.dex */
final /* synthetic */ class AbstractClickableNode$focusableNode$1 extends FunctionReferenceImpl implements Function1 {
    public AbstractClickableNode$focusableNode$1(Object obj) {
        super(1, obj, AbstractClickableNode.class, "onFocusChange", "onFocusChange(Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
        invoke(((Boolean) obj).booleanValue());
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void invoke(boolean z) {
        AbstractClickableNode abstractClickableNode = (AbstractClickableNode) this.receiver;
        if (z) {
            AbstractClickableNode.TraverseKey traverseKey = AbstractClickableNode.TraverseKey;
            abstractClickableNode.initializeIndicationAndInteractionSourceIfNeeded();
            return;
        }
        MutableInteractionSource mutableInteractionSource = abstractClickableNode.interactionSource;
        MutableLongObjectMap mutableLongObjectMap = abstractClickableNode.currentKeyPressInteractions;
        if (mutableInteractionSource != null) {
            Object[] objArr = mutableLongObjectMap.values;
            long[] jArr = mutableLongObjectMap.metadata;
            int length = jArr.length - 2;
            if (length >= 0) {
                int i = 0;
                while (true) {
                    long j = jArr[i];
                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i2 = 8 - ((~(i - length)) >>> 31);
                        for (int i3 = 0; i3 < i2; i3++) {
                            if ((255 & j) < 128) {
                                BuildersKt.launch$default(abstractClickableNode.getCoroutineScope(), null, null, new AbstractClickableNode$onFocusChange$1$1(abstractClickableNode, (PressInteraction$Press) objArr[(i << 3) + i3], null), 3);
                            }
                            j >>= 8;
                        }
                        if (i2 != 8) {
                            break;
                        } else if (i == length) {
                            break;
                        } else {
                            i++;
                        }
                    }
                }
            }
        }
        mutableLongObjectMap.clear();
        abstractClickableNode.onCancelKeyInput();
    }
}
