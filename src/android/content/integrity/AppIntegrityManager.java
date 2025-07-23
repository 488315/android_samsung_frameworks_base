package android.content.integrity;

import android.annotation.SystemApi;
import android.content.IntentSender;
import android.content.integrity.RuleSet;
import android.content.pm.ParceledListSlice;
import android.os.RemoteException;
import java.util.List;

@SystemApi
/* loaded from: classes.dex */
public class AppIntegrityManager {
    public static final String EXTRA_STATUS = "android.content.integrity.extra.STATUS";
    public static final int STATUS_FAILURE = 1;
    public static final int STATUS_SUCCESS = 0;
    IAppIntegrityManager mManager;

    public AppIntegrityManager(IAppIntegrityManager iAppIntegrityManager) {
        this.mManager = iAppIntegrityManager;
    }

    public void updateRuleSet(RuleSet ruleSet, IntentSender intentSender) {
        try {
            this.mManager.updateRuleSet(ruleSet.getVersion(), new ParceledListSlice<>(ruleSet.getRules()), intentSender);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public String getCurrentRuleSetVersion() {
        try {
            return this.mManager.getCurrentRuleSetVersion();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public String getCurrentRuleSetProvider() {
        try {
            return this.mManager.getCurrentRuleSetProvider();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public RuleSet getCurrentRuleSet() {
        try {
            ParceledListSlice<Rule> currentRules = this.mManager.getCurrentRules();
            return new RuleSet.Builder().setVersion(this.mManager.getCurrentRuleSetVersion()).addRules(currentRules.getList()).build();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public List<String> getWhitelistedRuleProviders() {
        try {
            return this.mManager.getWhitelistedRuleProviders();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }
}
