package app.com.warattil.model;


public class SurahBean {

    private int id;
    private String f_name;
    private String f_name2;
    private String titleEnglish;
    private String titleArabic;

    public SurahBean(String f_name2, int id, String f_name, String titleEnglish, String titleArabic) {
        this.id = id;
        this.f_name2 = f_name2;
        this.f_name = f_name;
        this.titleEnglish = titleEnglish;
        this.titleArabic = titleArabic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getF_name2() {
        return f_name2;
    }

    public void setF_name2(String f_name2) {
        this.f_name2 = f_name2;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getTitleArabic() {
        return titleArabic;
    }

    public void setTitleArabic(String titleArabic) {
        this.titleArabic = titleArabic;
    }
}
