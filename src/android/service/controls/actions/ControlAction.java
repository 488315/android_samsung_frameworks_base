package android.service.controls.actions;

import android.os.Bundle;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public abstract class ControlAction {
    public static final ControlAction ERROR_ACTION = new ControlAction() { // from class: android.service.controls.actions.ControlAction.1
        @Override // android.service.controls.actions.ControlAction
        public int getActionType() {
            return -1;
        }
    };
    private static final String KEY_ACTION_TYPE = "key_action_type";
    private static final String KEY_CHALLENGE_VALUE = "key_challenge_value";
    private static final String KEY_TEMPLATE_ID = "key_template_id";
    private static final int NUM_RESPONSE_TYPES = 6;
    public static final int RESPONSE_CHALLENGE_ACK = 3;
    public static final int RESPONSE_CHALLENGE_PASSPHRASE = 5;
    public static final int RESPONSE_CHALLENGE_PIN = 4;
    public static final int RESPONSE_FAIL = 2;
    public static final int RESPONSE_OK = 1;
    public static final int RESPONSE_UNKNOWN = 0;
    private static final String TAG = "ControlAction";
    public static final int TYPE_BOOLEAN = 1;
    public static final int TYPE_COMMAND = 5;
    public static final int TYPE_ERROR = -1;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_MODE = 4;
    private final String mChallengeValue;
    private final String mTemplateId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResponseResult {
    }

    public static final boolean isValidResponse(int i) {
        return i >= 0 && i < 6;
    }

    public abstract int getActionType();

    private ControlAction() {
        this.mTemplateId = "";
        this.mChallengeValue = null;
    }

    ControlAction(String str, String str2) {
        Preconditions.checkNotNull(str);
        this.mTemplateId = str;
        this.mChallengeValue = str2;
    }

    ControlAction(Bundle bundle) {
        this.mTemplateId = bundle.getString(KEY_TEMPLATE_ID);
        this.mChallengeValue = bundle.getString(KEY_CHALLENGE_VALUE);
    }

    public String getTemplateId() {
        return this.mTemplateId;
    }

    public String getChallengeValue() {
        return this.mChallengeValue;
    }

    Bundle getDataBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ACTION_TYPE, getActionType());
        bundle.putString(KEY_TEMPLATE_ID, this.mTemplateId);
        bundle.putString(KEY_CHALLENGE_VALUE, this.mChallengeValue);
        return bundle;
    }

    static ControlAction createActionFromBundle(Bundle bundle) {
        if (bundle == null) {
            Log.e(TAG, "Null bundle");
            return ERROR_ACTION;
        }
        int i = bundle.getInt(KEY_ACTION_TYPE, -1);
        try {
            if (i == 1) {
                return new BooleanAction(bundle);
            }
            if (i == 2) {
                return new FloatAction(bundle);
            }
            if (i == 4) {
                return new ModeAction(bundle);
            }
            if (i == 5) {
                return new CommandAction(bundle);
            }
            return ERROR_ACTION;
        } catch (Exception e) {
            Log.e(TAG, "Error creating action", e);
            return ERROR_ACTION;
        }
    }

    public static ControlAction getErrorAction() {
        return ERROR_ACTION;
    }
}
