package android.app;

import android.content.Context;
import android.os.RemoteException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class GrammaticalInflectionManager {
    public static final Set<Integer> VALID_GRAMMATICAL_GENDER_VALUES = Collections.unmodifiableSet(new HashSet(Arrays.asList(0, 1, 2, 3)));
    private final Context mContext;
    private final IGrammaticalInflectionManager mService;

    public GrammaticalInflectionManager(Context context, IGrammaticalInflectionManager service) {
        this.mContext = context;
        this.mService = service;
    }

    public int getApplicationGrammaticalGender() {
        return this.mContext.getApplicationContext().getResources().getConfiguration().getGrammaticalGender();
    }

    public void setRequestedApplicationGrammaticalGender(int grammaticalGender) {
        if (!VALID_GRAMMATICAL_GENDER_VALUES.contains(Integer.valueOf(grammaticalGender))) {
            throw new IllegalArgumentException("Unknown grammatical gender");
        }
        try {
            this.mService.setRequestedApplicationGrammaticalGender(this.mContext.getPackageName(), this.mContext.getUserId(), grammaticalGender);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSystemWideGrammaticalGender(int grammaticalGender) {
        if (!VALID_GRAMMATICAL_GENDER_VALUES.contains(Integer.valueOf(grammaticalGender))) {
            throw new IllegalArgumentException("Unknown grammatical gender");
        }
        try {
            this.mService.setSystemWideGrammaticalGender(grammaticalGender, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getSystemGrammaticalGender() {
        try {
            return this.mService.getSystemGrammaticalGender(this.mContext.getAttributionSource(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int peekSystemGrammaticalGenderByUserId(int userId) {
        try {
            return this.mService.peekSystemGrammaticalGenderByUserId(this.mContext.getAttributionSource(), userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
