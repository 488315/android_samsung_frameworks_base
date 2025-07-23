package com.android.wm.shell.draganddrop;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Slog;
import com.android.wm.shell.draganddrop.AppResultFactory;
import com.android.wm.shell.draganddrop.ExecutableAppHolder;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PlainTextResolver extends BaseResolver {
    public PlainTextResolver(Context context, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList) {
        super(context, multiInstanceBlockList);
    }

    @Override // com.android.wm.shell.draganddrop.BaseResolver
    public final Optional makeFrom(ClipData clipData, int i, AppResultFactory.ResultExtra resultExtra) {
        if (clipData.getItemCount() == 0) {
            return Optional.empty();
        }
        CharSequence text = clipData.getItemAt(0).getText();
        if (text == null) {
            return Optional.empty();
        }
        String replaceAll = text.toString().replaceAll("\u0000", "");
        if (TextUtils.isEmpty(replaceAll)) {
            return Optional.empty();
        }
        Intent intent = new Intent();
        Intent type = intent.setAction("android.intent.action.SEND").setType("text/plain");
        if (!TextUtils.isEmpty(replaceAll) && replaceAll.length() >= 100000) {
            Slog.d(this.TAG, "Truncating oversized query (" + replaceAll.length() + ").");
            replaceAll = replaceAll.toString().substring(0, 100000) + "…";
        }
        type.putExtra("android.intent.extra.TEXT", (CharSequence) replaceAll);
        String calculateContentType = BaseResolver.calculateContentType(intent);
        resolveActivities(intent, clipData.getCallingUserId(), this.mTempList, resultExtra);
        return this.mTempList.isEmpty() ? Optional.empty() : Optional.of(new SingleIntentAppResult(intent, this.mTempList, this.mMultiInstanceBlockList, this.mMultiInstanceAllowList, calculateContentType));
    }
}
