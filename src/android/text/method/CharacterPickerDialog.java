package android.text.method;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class CharacterPickerDialog extends Dialog implements AdapterView.OnItemClickListener, View.OnClickListener {
    private Button mCancelButton;
    private LayoutInflater mInflater;
    private boolean mInsert;
    private String mOptions;
    private Editable mText;
    private View mView;

    public CharacterPickerDialog(Context context, View view, Editable editable, String str, boolean z) {
        super(context, 16973913);
        this.mView = view;
        this.mText = editable;
        this.mOptions = str;
        this.mInsert = z;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) throws Resources.NotFoundException {
        super.onCreate(bundle);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.token = this.mView.getApplicationWindowToken();
        attributes.type = 1003;
        attributes.flags |= 1;
        setContentView(R.layout.character_picker);
        GridView gridView = (GridView) findViewById(R.id.characterPicker);
        gridView.setAdapter((ListAdapter) new OptionsAdapter(getContext()));
        gridView.setOnItemClickListener(this);
        Button button = (Button) findViewById(R.id.cancel);
        this.mCancelButton = button;
        button.setOnClickListener(this);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        replaceCharacterAndClose(String.valueOf(this.mOptions.charAt(i)));
    }

    private void replaceCharacterAndClose(CharSequence charSequence) {
        int selectionEnd = Selection.getSelectionEnd(this.mText);
        if (this.mInsert || selectionEnd == 0) {
            this.mText.insert(selectionEnd, charSequence);
        } else {
            this.mText.replace(selectionEnd - 1, selectionEnd, charSequence);
        }
        dismiss();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mCancelButton) {
            dismiss();
        } else if (view instanceof Button) {
            replaceCharacterAndClose(((Button) view).getText());
        }
    }

    private class OptionsAdapter extends BaseAdapter {
        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        public OptionsAdapter(Context context) {
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            Button button = (Button) CharacterPickerDialog.this.mInflater.inflate(R.layout.character_picker_button, (ViewGroup) null);
            button.lambda$setTextAsync$0(String.valueOf(CharacterPickerDialog.this.mOptions.charAt(i)));
            button.setOnClickListener(CharacterPickerDialog.this);
            return button;
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return CharacterPickerDialog.this.mOptions.length();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return String.valueOf(CharacterPickerDialog.this.mOptions.charAt(i));
        }
    }
}
