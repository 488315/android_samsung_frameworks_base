package com.android.wm.shell.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import com.android.wm.shell.draganddrop.AppResultFactory;
import com.android.wm.shell.draganddrop.ExecutableAppHolder;
import java.util.ArrayList;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class IntentResolver extends BaseResolver {
    public IntentResolver(Context context, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList) {
        super(context, multiInstanceBlockList);
    }

    @Override // com.android.wm.shell.draganddrop.BaseResolver
    public final Optional makeFrom(ClipData clipData, int i, AppResultFactory.ResultExtra resultExtra) {
        CharSequence label;
        if (clipData.getItemCount() <= 0) {
            return Optional.empty();
        }
        Intent intent = clipData.getItemAt(0).getIntent();
        if (intent == null) {
            return Optional.empty();
        }
        ArrayList arrayList = new ArrayList();
        if (intent.getData() != null) {
            arrayList.add(intent.getData());
        }
        if (intent.getClipData() != null) {
            intent.getClipData().collectUris(arrayList);
        }
        if (arrayList.isEmpty() || !intent.hasWebURI()) {
            return Optional.empty();
        }
        if (clipData.getCallingPackageName() == null || intent.getComponent() == null || !clipData.getCallingPackageName().equals(intent.getComponent().getPackageName())) {
            return Optional.empty();
        }
        int callingUserId = clipData.getCallingUserId();
        ClipDescription description = clipData.getDescription();
        if (description == null || (label = description.getLabel()) == null || !"terrace-image-or-link-drag-label".equals(label.toString())) {
            resolveActivities(intent, callingUserId, this.mTempList, resultExtra);
        } else {
            resolveActivitiesForSBrowser(intent, callingUserId, this.mTempList, resultExtra);
        }
        if (this.mTempList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new SingleIntentAppResult(intent, this.mTempList, this.mMultiInstanceBlockList, this.mMultiInstanceAllowList, BaseResolver.calculateContentType(intent), true));
    }
}
