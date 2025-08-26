package android.text.method;

import android.telephony.PhoneNumberUtils;
import android.text.Spannable;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

/* loaded from: classes4.dex */
public class DialerKeyListener extends NumberKeyListener {
    public static final char[] CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '#', '*', '+', '-', '(', ')', ',', '/', PhoneNumberUtils.WILD, '.', ' ', ';'};
    private static DialerKeyListener sInstance;

    @Override // android.text.method.KeyListener
    public int getInputType() {
        return 3;
    }

    @Override // android.text.method.NumberKeyListener
    protected char[] getAcceptedChars() {
        return CHARACTERS;
    }

    public static DialerKeyListener getInstance() {
        DialerKeyListener dialerKeyListener = sInstance;
        if (dialerKeyListener != null) {
            return dialerKeyListener;
        }
        DialerKeyListener dialerKeyListener2 = new DialerKeyListener();
        sInstance = dialerKeyListener2;
        return dialerKeyListener2;
    }

    @Override // android.text.method.NumberKeyListener
    protected int lookup(KeyEvent keyEvent, Spannable spannable) {
        int metaState = getMetaState(spannable, keyEvent);
        char number = keyEvent.getNumber();
        if ((metaState & 3) != 0 || number == 0) {
            int iLookup = super.lookup(keyEvent, spannable);
            if (iLookup != 0) {
                return iLookup;
            }
            if (metaState != 0) {
                KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
                char[] acceptedChars = getAcceptedChars();
                if (keyEvent.getKeyData(keyData)) {
                    for (int i = 1; i < keyData.meta.length; i++) {
                        if (ok(acceptedChars, keyData.meta[i])) {
                            return keyData.meta[i];
                        }
                    }
                }
            }
        }
        return number;
    }
}
