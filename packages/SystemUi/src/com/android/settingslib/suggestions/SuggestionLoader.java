package com.android.settingslib.suggestions;

import android.content.Context;
import android.util.Log;
import com.android.settingslib.utils.AsyncLoader;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SuggestionLoader extends AsyncLoader {
    public final SuggestionController mSuggestionController;

    public SuggestionLoader(Context context, SuggestionController suggestionController) {
        super(context);
        this.mSuggestionController = suggestionController;
    }

    @Override // android.content.AsyncTaskLoader
    public final Object loadInBackground() {
        List suggestions = this.mSuggestionController.getSuggestions();
        if (suggestions == null) {
            Log.d("SuggestionLoader", "data is null");
        } else {
            Log.d("SuggestionLoader", "data size " + suggestions.size());
        }
        return suggestions;
    }

    @Override // com.android.settingslib.utils.AsyncLoader
    public final /* bridge */ /* synthetic */ void onDiscardResult(Object obj) {
    }
}
