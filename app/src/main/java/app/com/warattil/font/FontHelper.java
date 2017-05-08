package app.com.warattil.font;

import android.graphics.Typeface;
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

    public static String fromType(FontType fontType) {

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

    public static void setFontFace(TextView... textViews) {
        Typeface type = Typeface.createFromAsset(MyApplication.getInstance().getAssets(), FontType.fromType(
                FontType.FONT_MEDIUM));
        for(TextView textView : textViews) {
            textView.setTypeface(type);
        }
    }

    public static void setFontFace(FontType fontType, EditText... editTexts) {
        Typeface type = Typeface.createFromAsset(MyApplication.getInstance().getAssets(), FontType.fromType(fontType));
        for(EditText editText : editTexts) {
            editText.setTypeface(type);
        }
    }

    public static void setFontFace(Button... buttons) {
        Typeface type = Typeface.createFromAsset(MyApplication.getInstance().getAssets(), FontType.fromType(
                FontType.FONT_MEDIUM));
        for(Button button : buttons) {
            button.setTypeface(type);
        }
    }

    public static void setFontFace(RadioButton... radioButtons) {
        Typeface type = Typeface.createFromAsset(MyApplication.getInstance().getAssets(), FontType.fromType(
                FontType.FONT_MEDIUM));
        for(RadioButton radioButton : radioButtons) {
            radioButton.setTypeface(type);
        }
    }
}
