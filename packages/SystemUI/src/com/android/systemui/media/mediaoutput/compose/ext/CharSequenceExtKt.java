package com.android.systemui.media.mediaoutput.compose.ext;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.media.mediaoutput.ext.MultiSequenceString;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;

public abstract class CharSequenceExtKt {
    public static final String text(CharSequence charSequence, Composer composer) {
        String obj;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-453092630);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (charSequence instanceof ResourceString) {
            composerImpl.startReplaceGroup(829240919);
            ResourceString resourceString = (ResourceString) charSequence;
            List list = resourceString.args;
            obj = null;
            if (list == null || !(!list.isEmpty())) {
                list = null;
            }
            composerImpl.startReplaceGroup(829240953);
            if (list != null) {
                int i = resourceString.resId;
                Object[] array = list.toArray(new Object[0]);
                obj = StringResources_androidKt.stringResource(i, Arrays.copyOf(array, array.length), composerImpl);
            }
            composerImpl.end(false);
            if (obj == null) {
                obj = StringResources_androidKt.stringResource(resourceString.resId, composerImpl);
            }
            composerImpl.end(false);
        } else if (charSequence instanceof MultiSequenceString) {
            composerImpl.startReplaceGroup(829241109);
            List list2 = ((MultiSequenceString) charSequence).texts;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(text((CharSequence) it.next(), composerImpl));
            }
            obj = CollectionsKt___CollectionsKt.joinToString$default(arrayList, " ", null, null, null, 62);
            composerImpl.end(false);
        } else {
            composerImpl.startReplaceGroup(829241146);
            composerImpl.end(false);
            obj = charSequence.toString();
        }
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        composerImpl.end(false);
        return obj;
    }
}
