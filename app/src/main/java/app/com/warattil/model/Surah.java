package app.com.warattil.model;


public class Surah {

    private int id;
    private String FirstReciter;
    private String SecondReciter;
    private String titleEnglish;
    private String titleArabic;

    public Surah(String f_name2, int id, String f_name, String titleEnglish, String titleArabic) {
        this.id = id;
        this.SecondReciter = f_name2;
        this.FirstReciter = f_name;
        this.titleEnglish = titleEnglish;
        this.titleArabic = titleArabic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSecondReciter() {
        return SecondReciter;
    }

    public void setSecondReciter(String secondReciter) {
        this.SecondReciter = secondReciter;
    }

    public String getFirstReciter() {
        return FirstReciter;
    }

    public void setFirstReciter(String firstReciter) {
        this.FirstReciter = firstReciter;
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
