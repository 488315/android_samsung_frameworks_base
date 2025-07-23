package com.android.wm.shell.draganddrop;

import android.app.RemoteAction;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.service.textclassifier.TextClassifierService;
import android.text.TextUtils;
import android.util.Slog;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationManager;
import com.android.wm.shell.draganddrop.AppResultFactory;
import com.android.wm.shell.draganddrop.ExecutableAppHolder;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TextClassifierResolver extends BaseResolver {
    public int mCallingUserId;

    public TextClassifierResolver(Context context, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList) {
        super(context, multiInstanceBlockList);
    }

    public static Object runOnBlocking(TextClassifierResolver$$ExternalSyntheticLambda0 textClassifierResolver$$ExternalSyntheticLambda0) {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        Object obj = newCachedThreadPool.submit(textClassifierResolver$$ExternalSyntheticLambda0).get(3L, TimeUnit.SECONDS);
        newCachedThreadPool.shutdown();
        return obj;
    }

    public final SingleIntentAppResult getResultFromTextClassification(TextClassification textClassification, AppResultFactory.ResultExtra resultExtra, boolean z) {
        List<RemoteAction> actions = textClassification.getActions();
        if (actions != null && !actions.isEmpty()) {
            RemoteAction remoteAction = actions.get(0);
            Intent intent = remoteAction.getActionIntent().getIntent();
            if (intent != null && !"android.intent.action.TRANSLATE".equals(intent.getAction())) {
                resolveActivities(intent, this.mCallingUserId, this.mTempList, resultExtra);
                if (!this.mTempList.isEmpty()) {
                    String calculateContentType = BaseResolver.calculateContentType(intent);
                    if (calculateContentType == null) {
                        calculateContentType = remoteAction.getContentDescription().toString();
                    }
                    String str = calculateContentType;
                    return new SingleIntentAppResult(intent, this.mTempList, this.mMultiInstanceBlockList, this.mMultiInstanceAllowList, str, true, z ? remoteAction.getIcon() : null);
                }
            }
        }
        return null;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.draganddrop.TextClassifierResolver$$ExternalSyntheticLambda0] */
    @Override // com.android.wm.shell.draganddrop.BaseResolver
    public final Optional makeFrom(final ClipData clipData, int i, final AppResultFactory.ResultExtra resultExtra) {
        String str = this.TAG;
        try {
            return (Optional) runOnBlocking(new Callable() { // from class: com.android.wm.shell.draganddrop.TextClassifierResolver$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    ClipData clipData2 = clipData;
                    TextClassifierResolver textClassifierResolver = TextClassifierResolver.this;
                    textClassifierResolver.getClass();
                    if (clipData2.getItemCount() == 0) {
                        return Optional.empty();
                    }
                    CharSequence text = clipData2.getItemAt(0).getText();
                    if (text == null) {
                        return Optional.empty();
                    }
                    String replaceAll = text.toString().replaceAll("\u0000", "");
                    if (TextUtils.isEmpty(replaceAll)) {
                        return Optional.empty();
                    }
                    textClassifierResolver.mCallingUserId = clipData2.getCallingUserId();
                    TextClassification.Request build = new TextClassification.Request.Builder(replaceAll, 0, replaceAll.length()).build();
                    TextClassification classifyText = TextClassifierService.getDefaultTextClassifierImplementation(textClassifierResolver.mContext).classifyText(build);
                    AppResultFactory.ResultExtra resultExtra2 = resultExtra;
                    SingleIntentAppResult resultFromTextClassification = textClassifierResolver.getResultFromTextClassification(classifyText, resultExtra2, false);
                    String str2 = textClassifierResolver.TAG;
                    if (resultFromTextClassification == null) {
                        resultFromTextClassification = textClassifierResolver.getResultFromTextClassification(((TextClassificationManager) textClassifierResolver.mContext.getSystemService("textclassification")).getTextClassifier(1).classifyText(build), resultExtra2, true);
                        Slog.d(str2, "updateByTextClassifying: Use System type");
                    } else {
                        Slog.d(str2, "updateByTextClassifying: Use Default System type");
                    }
                    return resultFromTextClassification != null ? Optional.of(resultFromTextClassification) : Optional.empty();
                }
            });
        } catch (InterruptedException | ExecutionException e) {
            Slog.d(str, "failed to update from text classifier." + e.getMessage());
            return Optional.empty();
        } catch (TimeoutException unused) {
            Slog.d(str, "failed to update from text classifier due to timeout.");
            return Optional.empty();
        }
    }
}
