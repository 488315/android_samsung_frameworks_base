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

/* loaded from: classes3.dex */
public class TextClassifierResolver extends BaseResolver {
    public int mCallingUserId;

    public TextClassifierResolver(Context context, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList) {
        super(context, multiInstanceBlockList);
    }

    public static Object runOnBlocking(TextClassifierResolver$$ExternalSyntheticLambda0 textClassifierResolver$$ExternalSyntheticLambda0) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorServiceNewCachedThreadPool = Executors.newCachedThreadPool();
        Object obj = executorServiceNewCachedThreadPool.submit(textClassifierResolver$$ExternalSyntheticLambda0).get(3L, TimeUnit.SECONDS);
        executorServiceNewCachedThreadPool.shutdown();
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
                    String strCalculateContentType = BaseResolver.calculateContentType(intent);
                    if (strCalculateContentType == null) {
                        strCalculateContentType = remoteAction.getContentDescription().toString();
                    }
                    String str = strCalculateContentType;
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
                    TextClassifierResolver textClassifierResolver = this.f$0;
                    textClassifierResolver.getClass();
                    if (clipData2.getItemCount() == 0) {
                        return Optional.empty();
                    }
                    CharSequence text = clipData2.getItemAt(0).getText();
                    if (text == null) {
                        return Optional.empty();
                    }
                    String strReplaceAll = text.toString().replaceAll("\u0000", "");
                    if (TextUtils.isEmpty(strReplaceAll)) {
                        return Optional.empty();
                    }
                    textClassifierResolver.mCallingUserId = clipData2.getCallingUserId();
                    TextClassification.Request requestBuild = new TextClassification.Request.Builder(strReplaceAll, 0, strReplaceAll.length()).build();
                    TextClassification textClassificationClassifyText = TextClassifierService.getDefaultTextClassifierImplementation(textClassifierResolver.mContext).classifyText(requestBuild);
                    AppResultFactory.ResultExtra resultExtra2 = resultExtra;
                    SingleIntentAppResult resultFromTextClassification = textClassifierResolver.getResultFromTextClassification(textClassificationClassifyText, resultExtra2, false);
                    String str2 = textClassifierResolver.TAG;
                    if (resultFromTextClassification == null) {
                        resultFromTextClassification = textClassifierResolver.getResultFromTextClassification(((TextClassificationManager) textClassifierResolver.mContext.getSystemService("textclassification")).getTextClassifier(1).classifyText(requestBuild), resultExtra2, true);
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
