package com.android.systemui.media.mediaoutput.compose.ext;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.ext.MultiSequenceString;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import com.android.systemui.util.DeviceType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class CharSequenceExtKt {
    public static final String stringResourceExt(int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1129966573);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.stringResourceExt (CharSequenceExt.kt:25)");
        }
        boolean isTablet = DeviceType.isTablet();
        Boolean valueOf = Boolean.valueOf(isTablet);
        Integer num = null;
        if (!isTablet) {
            valueOf = null;
        }
        if (valueOf != null) {
            if (i == R.string.phone_speaker) {
                num = Integer.valueOf(R.string.tablet_speaker);
            } else if (i == R.string.casting_priority_description) {
                num = Integer.valueOf(R.string.casting_priority_description_tablet);
            } else if (i == R.string.audio_mirroring_priority_description) {
                num = Integer.valueOf(R.string.audio_mirroring_priority_description_tablet);
            } else if (i == R.string.spotify_casting_priority_description) {
                num = Integer.valueOf(R.string.spotify_casting_priority_description_tablet);
            } else if (i == R.string.spotify_audio_mirroring_priority_description) {
                num = Integer.valueOf(R.string.spotify_audio_mirroring_priority_description_tablet);
            }
            if (num != null) {
                i = num.intValue();
            }
        }
        String stringResource = StringResources_androidKt.stringResource(i, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return stringResource;
    }

    public static final String text(CharSequence charSequence, Composer composer) {
        String obj;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-453092630);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.text (CharSequenceExt.kt:12)");
        }
        if (charSequence instanceof ResourceString) {
            composerImpl.startReplaceGroup(-1963272623);
            ResourceString resourceString = (ResourceString) charSequence;
            List list = resourceString.args;
            obj = null;
            if (list == null || list.isEmpty()) {
                list = null;
            }
            composerImpl.startReplaceGroup(-63330050);
            if (list != null) {
                int i = resourceString.resId;
                Object[] array = list.toArray(new Object[0]);
                obj = StringResources_androidKt.stringResource(i, Arrays.copyOf(array, array.length), composerImpl);
            }
            composerImpl.end(false);
            if (obj == null) {
                obj = stringResourceExt(resourceString.resId, composerImpl);
            }
            composerImpl.end(false);
        } else if (charSequence instanceof MultiSequenceString) {
            composerImpl.startReplaceGroup(-1963107269);
            MultiSequenceString multiSequenceString = (MultiSequenceString) charSequence;
            List list2 = multiSequenceString.texts;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(text((CharSequence) it.next(), composerImpl));
            }
            obj = CollectionsKt___CollectionsKt.joinToString$default(arrayList, multiSequenceString.separator, null, null, null, 62);
            composerImpl.end(false);
        } else {
            composerImpl.startReplaceGroup(-63323630);
            composerImpl.end(false);
            obj = charSequence.toString();
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return obj;
    }
}
