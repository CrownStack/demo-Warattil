package app.com.warattil.font;

import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import app.com.warattil.app.MyApplication;

public class FontHelper {

public enum FontType {

    FONT_BOLD("fonts/Roboto-Bold.ttf"),
    FONT_MEDIUM("fonts/Roboto-Medium.ttf"),
    FONT_REGULAR("fonts/Roboto-Regular.ttf");

    private final String type;

    FontType(String type) {
        this.type = type;
    }

    public static String whichFont(FontType fontType) {

        if(fontType != null) {
            for(FontType typeEnum : FontType.values()) {
                if(fontType == typeEnum){
                    return typeEnum.type;
                }
            }
        }

        return null;
    }
}

    public static void setFontFace(FontType fontType, View... views) {
        Typeface type = Typeface.createFromAsset(MyApplication.getInstance().getAssets(), FontType.whichFont(fontType));

        for(View view : views) {
            if(view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTypeface(type);
            }
            if(view instanceof Button) {
                Button button = (Button) view;
                button.setTypeface(type);
            }
            if(view instanceof EditText) {
                EditText editText = (EditText) view;
                editText.setTypeface(type);
            }
            if(view instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) view;
                radioButton.setTypeface(type);
            }
        }
    }
}
